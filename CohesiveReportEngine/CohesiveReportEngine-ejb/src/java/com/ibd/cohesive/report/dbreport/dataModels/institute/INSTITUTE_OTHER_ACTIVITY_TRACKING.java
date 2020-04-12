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
public class INSTITUTE_OTHER_ACTIVITY_TRACKING {
    String STUDENT_ID; 
    String PARTICIPATION_STATUS;
    String CONFIRMATION_DATE;
    String RESULT;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getPARTICIPATION_STATUS() {
        return PARTICIPATION_STATUS;
    }

    public void setPARTICIPATION_STATUS(String PARTICIPATION_STATUS) {
        this.PARTICIPATION_STATUS = PARTICIPATION_STATUS;
    }

    public String getCONFIRMATION_DATE() {
        return CONFIRMATION_DATE;
    }

    public void setCONFIRMATION_DATE(String CONFIRMATION_DATE) {
        this.CONFIRMATION_DATE = CONFIRMATION_DATE;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }
    
     public ArrayList<INSTITUTE_OTHER_ACTIVITY_TRACKING>convertStringToArrayList(String result){
        
          ArrayList<INSTITUTE_OTHER_ACTIVITY_TRACKING> INSTITUTE_OTHER_ACTIVITY_TRACKINGList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              INSTITUTE_OTHER_ACTIVITY_TRACKING appStatus=new INSTITUTE_OTHER_ACTIVITY_TRACKING();
              
              appStatus.setCONFIRMATION_DATE(record.split("~")[0]);
              appStatus.setPARTICIPATION_STATUS(record.split("~")[1]);
              appStatus.setRESULT(record.split("~")[2]);
              appStatus.setSTUDENT_ID(record.split("~")[3]);
              
              
              
              
              
           INSTITUTE_OTHER_ACTIVITY_TRACKINGList.add(appStatus);
          }
          
        return INSTITUTE_OTHER_ACTIVITY_TRACKINGList;
           
      
}
    
    
    
    
    
    
}
