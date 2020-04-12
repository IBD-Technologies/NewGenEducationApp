/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.teacher;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class TVW_TEACHER_PROFILE_BUSINESS {
    String TEACHER_ID;
    String TEACHER_NAME;
    String QUALIFICATION;
    String SHORT_NAME;
    String DOB;
    String CONTACT_NO;
    String EMAIL_ID;
    String BLOOD_GROUP;
    String ADDRESSLINE1;
    String ADDRESSLINE2;
    String ADDRESSLINE3;
    String ADDRESSLINE4;
    String ADDRESSLINE5;
    String IMAGE_PATH;
    String MAKER_ID;
    String CHECKER_ID;
    String MAKER_DATE_STAMP;
    String CHECKER_DATE_STAMP;
    String RECORD_STATUS;
    String AUTH_STATUS;
    String VERSION_NUMBER;
    String MAKER_REMARKS;
    String CHECKER_REMARKS;
    String EXISTING_MEDICAL_DETAIL;
    String CLASS;

    public String getCLASS() {
        return CLASS;
    }

    public void setCLASS(String CLASS) {
        this.CLASS = CLASS;
    }
    

    public String getEXISTING_MEDICAL_DETAIL() {
        return EXISTING_MEDICAL_DETAIL;
    }

    public void setEXISTING_MEDICAL_DETAIL(String EXISTING_MEDICAL_DETAIL) {
        this.EXISTING_MEDICAL_DETAIL = EXISTING_MEDICAL_DETAIL;
    }

    public String getTEACHER_ID() {
        return TEACHER_ID;
    }

    public void setTEACHER_ID(String TEACHER_ID) {
        this.TEACHER_ID = TEACHER_ID;
    }

    public String getTEACHER_NAME() {
        return TEACHER_NAME;
    }

    public void setTEACHER_NAME(String TEACHER_NAME) {
        this.TEACHER_NAME = TEACHER_NAME;
    }

    public String getQUALIFICATION() {
        return QUALIFICATION;
    }

    public void setQUALIFICATION(String QUALIFICATION) {
        this.QUALIFICATION = QUALIFICATION;
    }

  

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getCONTACT_NO() {
        return CONTACT_NO;
    }

    public void setCONTACT_NO(String CONTACT_NO) {
        this.CONTACT_NO = CONTACT_NO;
    }

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }

    public String getBLOOD_GROUP() {
        return BLOOD_GROUP;
    }

    public void setBLOOD_GROUP(String BLOOD_GROUP) {
        this.BLOOD_GROUP = BLOOD_GROUP;
    }

    public String getADDRESSLINE1() {
        return ADDRESSLINE1;
    }

    public void setADDRESSLINE1(String ADDRESSLINE1) {
        this.ADDRESSLINE1 = ADDRESSLINE1;
    }

    public String getADDRESSLINE2() {
        return ADDRESSLINE2;
    }

    public void setADDRESSLINE2(String ADDRESSLINE2) {
        this.ADDRESSLINE2 = ADDRESSLINE2;
    }

    public String getADDRESSLINE3() {
        return ADDRESSLINE3;
    }

    public void setADDRESSLINE3(String ADDRESSLINE3) {
        this.ADDRESSLINE3 = ADDRESSLINE3;
    }

    public String getADDRESSLINE4() {
        return ADDRESSLINE4;
    }

    public void setADDRESSLINE4(String ADDRESSLINE4) {
        this.ADDRESSLINE4 = ADDRESSLINE4;
    }

    public String getADDRESSLINE5() {
        return ADDRESSLINE5;
    }

    public void setADDRESSLINE5(String ADDRESSLINE5) {
        this.ADDRESSLINE5 = ADDRESSLINE5;
    }

    public String getIMAGE_PATH() {
        return IMAGE_PATH;
    }

    public void setIMAGE_PATH(String IMAGE_PATH) {
        this.IMAGE_PATH = IMAGE_PATH;
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

    public String getSHORT_NAME() {
        return SHORT_NAME;
    }

    public void setSHORT_NAME(String SHORT_NAME) {
        this.SHORT_NAME = SHORT_NAME;
    }
    

    
    public ArrayList<TVW_TEACHER_PROFILE_BUSINESS>convertStringToArrayList(String result){
        
          ArrayList<TVW_TEACHER_PROFILE_BUSINESS> TVW_TEACHER_PROFILE_BUSINESSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              TVW_TEACHER_PROFILE_BUSINESS appStatus=new TVW_TEACHER_PROFILE_BUSINESS();
              
              appStatus.setADDRESSLINE1(record.split("~")[0]);
              appStatus.setADDRESSLINE2(record.split("~")[1]);
              appStatus.setADDRESSLINE3(record.split("~")[2]);
              appStatus.setADDRESSLINE4(record.split("~")[3]);
              appStatus.setADDRESSLINE5(record.split("~")[4]);
              appStatus.setAUTH_STATUS(record.split("~")[5]);
              appStatus.setBLOOD_GROUP(record.split("~")[6]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[7]);
              appStatus.setCHECKER_ID(record.split("~")[8]);
              appStatus.setCHECKER_REMARKS(record.split("~")[9]);
              appStatus.setCONTACT_NO(record.split("~")[10]);
              appStatus.setDOB(record.split("~")[11]);
              appStatus.setEMAIL_ID(record.split("~")[12]);
              appStatus.setIMAGE_PATH(record.split("~")[13]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[14]);
              appStatus.setMAKER_ID(record.split("~")[15]);
              appStatus.setMAKER_REMARKS(record.split("~")[16]);
              appStatus.setQUALIFICATION(record.split("~")[17]);
              appStatus.setRECORD_STATUS(record.split("~")[18]);
              appStatus.setSHORT_NAME(record.split("~")[19]);
              appStatus.setTEACHER_ID(record.split("~")[20]);
              appStatus.setTEACHER_NAME(record.split("~")[21]);
              appStatus.setVERSION_NUMBER(record.split("~")[22]);
              appStatus.setEXISTING_MEDICAL_DETAIL(record.split("~")[23]);
              appStatus.setCLASS(record.split("~")[24]);
              
           TVW_TEACHER_PROFILE_BUSINESSList.add(appStatus);
          }
          
        return TVW_TEACHER_PROFILE_BUSINESSList;
           
      
}
    
    
}
