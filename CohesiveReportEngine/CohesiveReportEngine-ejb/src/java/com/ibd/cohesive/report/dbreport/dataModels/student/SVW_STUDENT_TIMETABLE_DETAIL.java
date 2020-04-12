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
public class SVW_STUDENT_TIMETABLE_DETAIL {
    String STUDENT_ID;
    String DAY;
    String PERIOD_NO;
    String SUBJECT_ID;
    String TEACHER_ID;
    String VERSION_NUMBER;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getDAY() {
        return DAY;
    }

    public void setDAY(String DAY) {
        this.DAY = DAY;
    }

    public String getPERIOD_NO() {
        return PERIOD_NO;
    }

    public void setPERIOD_NO(String PERIOD_NO) {
        this.PERIOD_NO = PERIOD_NO;
    }

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }


    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

  public ArrayList<SVW_STUDENT_TIMETABLE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_TIMETABLE_DETAIL> SVW_STUDENT_TIMETABLE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_TIMETABLE_DETAIL appStatus=new SVW_STUDENT_TIMETABLE_DETAIL();
              
              appStatus.setDAY(record.split("~")[0]);
              appStatus.setPERIOD_NO(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setSUBJECT_ID(record.split("~")[3]);
              appStatus.setTEACHER_ID(record.split("~")[4]);
              appStatus.setVERSION_NUMBER(record.split("~")[5]);
              
                          
              
              
              
              
           SVW_STUDENT_TIMETABLE_DETAILList.add(appStatus);
          }
          
        return SVW_STUDENT_TIMETABLE_DETAILList;
           
      
}
      
    
}
