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
public class TODAY_NOTIFICATION {
    String STUDENT_ID;
    String END_POINT;
    String NOTIFICATION_TYPE;
    String MESSAGE;
    String STATUS;
    String TITLE;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getEND_POINT() {
        return END_POINT;
    }

    public void setEND_POINT(String END_POINT) {
        this.END_POINT = END_POINT;
    }

    public String getNOTIFICATION_TYPE() {
        return NOTIFICATION_TYPE;
    }

    public void setNOTIFICATION_TYPE(String NOTIFICATION_TYPE) {
        this.NOTIFICATION_TYPE = NOTIFICATION_TYPE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }
    
    
    
    
      public ArrayList<TODAY_NOTIFICATION>convertStringToArrayList(String result){
        
          ArrayList<TODAY_NOTIFICATION> TODAY_NOTIFICATIONList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TODAY_NOTIFICATION appStatus=new TODAY_NOTIFICATION();
              
              appStatus.setEND_POINT(record.split("~")[0]);
              appStatus.setMESSAGE(record.split("~")[1]);
              appStatus.setNOTIFICATION_TYPE(record.split("~")[2]);
              appStatus.setSTATUS(record.split("~")[3]);
              appStatus.setSTUDENT_ID(record.split("~")[4]);
              appStatus.setTITLE(record.split("~")[5]);
            
              
              
              
              
           TODAY_NOTIFICATIONList.add(appStatus);
          }
          
        return TODAY_NOTIFICATIONList;
           
      
}
   
    
}
