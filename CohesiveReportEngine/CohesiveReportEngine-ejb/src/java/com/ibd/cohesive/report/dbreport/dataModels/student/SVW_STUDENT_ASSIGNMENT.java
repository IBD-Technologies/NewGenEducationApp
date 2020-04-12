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
public class SVW_STUDENT_ASSIGNMENT {
    String STUDENT_ID;
    String SUBJECT_ID;
    String ASSIGNMENT_ID;
    String ASSIGNMENT_DESCRIPTION;
    String DUE_DATE;
    String COMPLETED_DATE;
    String STATUS;
    String TEACHER_COMMENTS;
    String PARENT_COMMENTS;
    String MAKER_ID;
    String  CHECKER_ID;
    String MAKER_DATE_STAMP;
    String CHECKER_DATE_STAMP;
    String RECORD_STATUS;
    String AUTH_STATUS;
    String VERSION_NUMBER;
    String MAKER_REMARKS;
    String CHECKER_REMARKS;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
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


    public String getDUE_DATE() {
        return DUE_DATE;
    }

    public void setDUE_DATE(String DUE_DATE) {
        this.DUE_DATE = DUE_DATE;
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

    public String getTEACHER_COMMENTS() {
        return TEACHER_COMMENTS;
    }

    public void setTEACHER_COMMENTS(String TEACHER_COMMENTS) {
        this.TEACHER_COMMENTS = TEACHER_COMMENTS;
    }

    public String getPARENT_COMMENTS() {
        return PARENT_COMMENTS;
    }

    public void setPARENT_COMMENTS(String PARENT_COMMENTS) {
        this.PARENT_COMMENTS = PARENT_COMMENTS;
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

    public String getASSIGNMENT_DESCRIPTION() {
        return ASSIGNMENT_DESCRIPTION;
    }

    public void setASSIGNMENT_DESCRIPTION(String ASSIGNMENT_DESCRIPTION) {
        this.ASSIGNMENT_DESCRIPTION = ASSIGNMENT_DESCRIPTION;
    }
    
    
     public ArrayList<SVW_STUDENT_ASSIGNMENT>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_ASSIGNMENT> SVW_STUDENT_ASSIGNMENTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_ASSIGNMENT appStatus=new SVW_STUDENT_ASSIGNMENT();
              
              appStatus.setASSIGNMENT_DESCRIPTION(record.split("~")[0]);
              appStatus.setASSIGNMENT_ID(record.split("~")[1]);
              appStatus.setAUTH_STATUS(record.split("~")[2]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[3]);
              appStatus.setCHECKER_ID(record.split("~")[4]);
              appStatus.setCHECKER_REMARKS(record.split("~")[5]);
              appStatus.setCOMPLETED_DATE(record.split("~")[6]);
              appStatus.setDUE_DATE(record.split("~")[7]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[8]);
              appStatus.setMAKER_ID(record.split("~")[9]);
              appStatus.setMAKER_REMARKS(record.split("~")[10]);
              appStatus.setPARENT_COMMENTS(record.split("~")[11]);
              appStatus.setRECORD_STATUS(record.split("~")[12]);
              appStatus.setSTATUS(record.split("~")[13]);
              appStatus.setSTUDENT_ID(record.split("~")[14]);
              appStatus.setSTUDENT_ID(record.split("~")[15]);
              appStatus.setSUBJECT_ID(record.split("~")[16]);
              appStatus.setTEACHER_COMMENTS(record.split("~")[17]);
              appStatus.setVERSION_NUMBER(record.split("~")[18]);
             
                          
              
              
              
              
           SVW_STUDENT_ASSIGNMENTList.add(appStatus);
          }
          
        return SVW_STUDENT_ASSIGNMENTList;
           
      
}
    
    
    
}
