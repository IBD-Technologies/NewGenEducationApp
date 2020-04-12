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
public class SVW_STUDENT_EXAM_SCHEDULE_DETAIL {
String  STUDENT_ID;
String  EXAM;
String  SUBJECT_ID;
String  DATE;
String  HALL;
String  VERSION_NUMBER;
String  START_TIME_HOUR;
String  START_TIME_MIN;
String  END_TIME_HOUR;
String  END_TIME_MIN;


    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
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

    public String getSTART_TIME_HOUR() {
        return START_TIME_HOUR;
    }

    public void setSTART_TIME_HOUR(String START_TIME_HOUR) {
        this.START_TIME_HOUR = START_TIME_HOUR;
    }

    public String getSTART_TIME_MIN() {
        return START_TIME_MIN;
    }

    public void setSTART_TIME_MIN(String START_TIME_MIN) {
        this.START_TIME_MIN = START_TIME_MIN;
    }

    public String getEND_TIME_HOUR() {
        return END_TIME_HOUR;
    }

    public void setEND_TIME_HOUR(String END_TIME_HOUR) {
        this.END_TIME_HOUR = END_TIME_HOUR;
    }

    public String getEND_TIME_MIN() {
        return END_TIME_MIN;
    }

    public void setEND_TIME_MIN(String END_TIME_MIN) {
        this.END_TIME_MIN = END_TIME_MIN;
    }
    
    
       public ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL> SVW_STUDENT_EXAM_SCHEDULE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_EXAM_SCHEDULE_DETAIL appStatus=new SVW_STUDENT_EXAM_SCHEDULE_DETAIL();
              
              appStatus.setDATE(record.split("~")[0]);
              appStatus.setEND_TIME_HOUR(record.split("~")[1]);
              appStatus.setEND_TIME_MIN(record.split("~")[2]);
              appStatus.setEXAM(record.split("~")[3]);
              appStatus.setHALL(record.split("~")[4]);
              appStatus.setSTART_TIME_HOUR(record.split("~")[5]);
              appStatus.setSTART_TIME_MIN(record.split("~")[6]);
              appStatus.setSTUDENT_ID(record.split("~")[7]);
              appStatus.setSUBJECT_ID(record.split("~")[8]);
              appStatus.setVERSION_NUMBER(record.split("~")[9]);

             
                          
              
              
              
              
           SVW_STUDENT_EXAM_SCHEDULE_DETAILList.add(appStatus);
          }
          
        return SVW_STUDENT_EXAM_SCHEDULE_DETAILList;
           
      
}
    
}
