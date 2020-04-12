/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class STUDENT_ASSIGNMENT_STATUS {
    String STUDENT_ID;
    String COMPLETED_DATE;
    String STATUS;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getCOMPLETED_DATE() {
        return COMPLETED_DATE;
    }

    public void setCOMPLETED_DATE(String COMPLETED_DATE) {
        this.COMPLETED_DATE = COMPLETED_DATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
    
     public ArrayList<STUDENT_ASSIGNMENT_STATUS>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_ASSIGNMENT_STATUS> STUDENT_ASSIGNMENT_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_ASSIGNMENT_STATUS appStatus=new STUDENT_ASSIGNMENT_STATUS();
              
              appStatus.setCOMPLETED_DATE(record.split("~")[0]);
              appStatus.setSTATUS(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              
              
              
              
              
              
           STUDENT_ASSIGNMENT_STATUSList.add(appStatus);
          }
          
        return STUDENT_ASSIGNMENT_STATUSList;
           
      
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
