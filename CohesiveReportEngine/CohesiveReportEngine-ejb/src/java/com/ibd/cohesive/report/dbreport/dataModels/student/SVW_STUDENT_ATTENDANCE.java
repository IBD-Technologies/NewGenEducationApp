/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class SVW_STUDENT_ATTENDANCE {
    String STUDENT_ID;
    String YEAR;
    String MONTH;
    String ATTENDANCE;
    
    

    
  
    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getATTENDANCE() {
        return ATTENDANCE;
    }

    public void setATTENDANCE(String ATTENDANCE) {
        this.ATTENDANCE = ATTENDANCE;
    }

   
    
       public ArrayList<SVW_STUDENT_ATTENDANCE>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_ATTENDANCE> SVW_STUDENT_ATTENDANCEList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_ATTENDANCE appStatus=new SVW_STUDENT_ATTENDANCE();
              
              appStatus.setATTENDANCE(record.split("~")[0]);
              appStatus.setMONTH(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setYEAR(record.split("~")[3]);
              
                          
              
              
              
              
           SVW_STUDENT_ATTENDANCEList.add(appStatus);
          }
          
        return SVW_STUDENT_ATTENDANCEList;
           
      
}
    
}
