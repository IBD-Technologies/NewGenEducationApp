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
public class CLASS_ASSIGNMENT {
    String STANDARD;
    String SECTION;
    String SUBJECT_ID;
    String ASSIGNMENT_ID;
    String ASSIGNMENT_TOPIC;
    String DUE_DATE;
    String TEACHER_COMMENT;
    String CONTENT_TYPE;
    String CONTENT_PATH;
    String MAKER_ID;
    String  CHECKER_ID;
    String MAKER_DATE_STAMP;
    String CHECKER_DATE_STAMP;
    String RECORD_STATUS;
    String AUTH_STATUS;
    String VERSION_NUMBER;
    String MAKER_REMARKS;
    String CHECKER_REMARKS;

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

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }

    public String getASSIGNMENT_ID() {
        return ASSIGNMENT_ID;
    }

    public void setASSIGNMENT_ID(String ASSIGNMENT_ID) {
        this.ASSIGNMENT_ID = ASSIGNMENT_ID;
    }

    public String getASSIGNMENT_TOPIC() {
        return ASSIGNMENT_TOPIC;
    }

    public void setASSIGNMENT_TOPIC(String ASSIGNMENT_TOPIC) {
        this.ASSIGNMENT_TOPIC = ASSIGNMENT_TOPIC;
    }

    public String getDUE_DATE() {
        return DUE_DATE;
    }

    public void setDUE_DATE(String DUE_DATE) {
        this.DUE_DATE = DUE_DATE;
    }

    public String getTEACHER_COMMENT() {
        return TEACHER_COMMENT;
    }

    public void setTEACHER_COMMENT(String TEACHER_COMMENT) {
        this.TEACHER_COMMENT = TEACHER_COMMENT;
    }

    public String getCONTENT_TYPE() {
        return CONTENT_TYPE;
    }

    public void setCONTENT_TYPE(String CONTENT_TYPE) {
        this.CONTENT_TYPE = CONTENT_TYPE;
    }

    public String getCONTENT_PATH() {
        return CONTENT_PATH;
    }

    public void setCONTENT_PATH(String CONTENT_PATH) {
        this.CONTENT_PATH = CONTENT_PATH;
    }

    public String getMAKER_ID() {
        return MAKER_ID;
    }

    public void setMAKER_ID(String MAKER_ID) {
        this.MAKER_ID = MAKER_ID;
    }

    public String getCHECKER_ID() {
        return CHECKER_ID;
    }

    public void setCHECKER_ID(String CHECKER_ID) {
        this.CHECKER_ID = CHECKER_ID;
    }

    public String getMAKER_DATE_STAMP() {
        return MAKER_DATE_STAMP;
    }

    public void setMAKER_DATE_STAMP(String MAKER_DATE_STAMP) {
        this.MAKER_DATE_STAMP = MAKER_DATE_STAMP;
    }

    public String getCHECKER_DATE_STAMP() {
        return CHECKER_DATE_STAMP;
    }

    public void setCHECKER_DATE_STAMP(String CHECKER_DATE_STAMP) {
        this.CHECKER_DATE_STAMP = CHECKER_DATE_STAMP;
    }

    public String getRECORD_STATUS() {
        return RECORD_STATUS;
    }

    public void setRECORD_STATUS(String RECORD_STATUS) {
        this.RECORD_STATUS = RECORD_STATUS;
    }

    public String getAUTH_STATUS() {
        return AUTH_STATUS;
    }

    public void setAUTH_STATUS(String AUTH_STATUS) {
        this.AUTH_STATUS = AUTH_STATUS;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }

    public String getMAKER_REMARKS() {
        return MAKER_REMARKS;
    }

    public void setMAKER_REMARKS(String MAKER_REMARKS) {
        this.MAKER_REMARKS = MAKER_REMARKS;
    }

    public String getCHECKER_REMARKS() {
        return CHECKER_REMARKS;
    }

    public void setCHECKER_REMARKS(String CHECKER_REMARKS) {
        this.CHECKER_REMARKS = CHECKER_REMARKS;
    }
    
    public ArrayList<CLASS_ASSIGNMENT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_ASSIGNMENT> CLASS_ASSIGNMENTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_ASSIGNMENT appStatus=new CLASS_ASSIGNMENT();
              
              appStatus.setASSIGNMENT_ID(record.split("~")[0]);
              appStatus.setASSIGNMENT_TOPIC(record.split("~")[1]);
              appStatus.setAUTH_STATUS(record.split("~")[2]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[3]);
              appStatus.setCHECKER_ID(record.split("~")[4]);
              appStatus.setCHECKER_REMARKS(record.split("~")[5]);
              appStatus.setCONTENT_PATH(record.split("~")[6]);
              appStatus.setCONTENT_TYPE(record.split("~")[7]);
              appStatus.setDUE_DATE(record.split("~")[8]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[9]);
              appStatus.setMAKER_ID(record.split("~")[10]);
              appStatus.setMAKER_REMARKS(record.split("~")[11]);
              appStatus.setRECORD_STATUS(record.split("~")[12]);
              appStatus.setSECTION(record.split("~")[13]);
              appStatus.setSTANDARD(record.split("~")[14]);
              appStatus.setSUBJECT_ID(record.split("~")[15]);
              appStatus.setTEACHER_COMMENT(record.split("~")[16]);
              appStatus.setVERSION_NUMBER(record.split("~")[17]);
              
              
              
              
              
           CLASS_ASSIGNMENTList.add(appStatus);
          }
          
        return CLASS_ASSIGNMENTList;
           
      
}
    
    
    
}
