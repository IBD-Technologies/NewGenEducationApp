/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class CLASS_ATTENDANCE_DETAIL {
     String REFERENCE_NO;
    String STUDENT_ID;
    String ATTENDANCE;

    public String getREFERENCE_NO() {
        return REFERENCE_NO;
    }

    public void setREFERENCE_NO(String REFERENCE_NO) {
        this.REFERENCE_NO = REFERENCE_NO;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getATTENDANCE() {
        return ATTENDANCE;
    }

    public void setATTENDANCE(String ATTENDANCE) {
        this.ATTENDANCE = ATTENDANCE;
    }
    

  
    
    public ArrayList<CLASS_ATTENDANCE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<CLASS_ATTENDANCE_DETAIL> CLASS_ATTENDANCE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_ATTENDANCE_DETAIL appStatus=new CLASS_ATTENDANCE_DETAIL();
              
              appStatus.setATTENDANCE(record.split("~")[0]);
              appStatus.setREFERENCE_NO(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              
              
              
              
              
              
              
           CLASS_ATTENDANCE_DETAILList.add(appStatus);
          }
          
        return CLASS_ATTENDANCE_DETAILList;
           
      
}
    
    
    
   
    
}
