/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class STUDENT_NOTIFICATION_STATUS {
    String STUDENT_ID;
    String NOTIFICATION_ID;
    String BUSINESS_DATE;
    String END_POINT;
    String STATUS;
    String ERROR;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
    }

    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
    }

    public String getEND_POINT() {
        return END_POINT;
    }

    public void setEND_POINT(String END_POINT) {
        this.END_POINT = END_POINT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }
    
    
    public ArrayList<STUDENT_NOTIFICATION_STATUS>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_NOTIFICATION_STATUS> STUDENT_NOTIFICATION_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_NOTIFICATION_STATUS appStatus=new STUDENT_NOTIFICATION_STATUS();
              
              appStatus.setBUSINESS_DATE(record.split("~")[0]);
              appStatus.setEND_POINT(record.split("~")[1]);
              appStatus.setERROR(record.split("~")[2]);
              appStatus.setNOTIFICATION_ID(record.split("~")[3]);
              appStatus.setSTATUS(record.split("~")[4]);
              appStatus.setSTUDENT_ID(record.split("~")[5]);
                          
              
              
              
              
           STUDENT_NOTIFICATION_STATUSList.add(appStatus);
          }
          
        return STUDENT_NOTIFICATION_STATUSList;
           
      
}
            

    
    
    
    
    
    
    
    
    
    
    
    
    
}
