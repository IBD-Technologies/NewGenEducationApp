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
public class CLASS_EXAM_SCHEDULE_DETAIL {
    String STANDARD;
    String SECTION;
    String EXAM;
    String SUBJECT_ID;
    String DATE;
    String TIME;
    String HALL;
    String VERSION_NUMBER;

    public String getSTANDARD() {
        return STANDARD;
    }

    public void setSTANDARD(String STANDARD) {
        this.STANDARD = STANDARD;
    }

    public String getSECTION() {
        return SECTION;
    }

    public void setSECTION(String SECTION) {
        this.SECTION = SECTION;
    }

    public String getEXAM() {
        return EXAM;
    }

    public void setEXAM(String EXAM) {
        this.EXAM = EXAM;
    }

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getHALL() {
        return HALL;
    }

    public void setHALL(String HALL) {
        this.HALL = HALL;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
    public ArrayList<CLASS_EXAM_SCHEDULE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<CLASS_EXAM_SCHEDULE_DETAIL> CLASS_EXAM_SCHEDULE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_EXAM_SCHEDULE_DETAIL appStatus=new CLASS_EXAM_SCHEDULE_DETAIL();
              
              appStatus.setDATE(record.split("~")[0]);
              appStatus.setEXAM(record.split("~")[1]);
              appStatus.setHALL(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSTANDARD(record.split("~")[4]);
              appStatus.setSUBJECT_ID(record.split("~")[5]);
              appStatus.setTIME(record.split("~")[6]);
              appStatus.setVERSION_NUMBER(record.split("~")[7]);
              
              
              
              
              
              
           CLASS_EXAM_SCHEDULE_DETAILList.add(appStatus);
          }
          
        return CLASS_EXAM_SCHEDULE_DETAILList;
           
      
}
    
    
    
    
}
