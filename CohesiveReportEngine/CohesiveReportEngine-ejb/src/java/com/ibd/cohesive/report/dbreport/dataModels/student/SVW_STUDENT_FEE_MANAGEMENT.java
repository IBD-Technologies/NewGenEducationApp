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
public class SVW_STUDENT_FEE_MANAGEMENT {
    String STUDENT_ID;
    String FEE_ID;
    String FEE_TYPE;
    String AMOUNT;
    String DUE_DATE;
    String STATUS;
    String MAKER_ID;
    String  CHECKER_ID;
    String MAKER_DATE_STAMP;
    String CHECKER_DATE_STAMP;
    String RECORD_STATUS;
    String AUTH_STATUS;
    String VERSION_NUMBER;
    String MAKER_REMARKS;
    String CHECKER_REMARKS;
    String FEE_PAID;
    String OUTSTANDING;
    String PAID_DATE;
    

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getFEE_ID() {
        return FEE_ID;
    }

    public void setFEE_ID(String FEE_ID) {
        this.FEE_ID = FEE_ID;
    }

    public String getFEE_TYPE() {
        return FEE_TYPE;
    }

    public void setFEE_TYPE(String FEE_TYPE) {
        this.FEE_TYPE = FEE_TYPE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getDUE_DATE() {
        return DUE_DATE;
    }

    public void setDUE_DATE(String DUE_DATE) {
        this.DUE_DATE = DUE_DATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
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

    public String getFEE_PAID() {
        return FEE_PAID;
    }

    public void setFEE_PAID(String FEE_PAID) {
        this.FEE_PAID = FEE_PAID;
    }

    public String getOUTSTANDING() {
        return OUTSTANDING;
    }

    public void setOUTSTANDING(String OUTSTANDING) {
        this.OUTSTANDING = OUTSTANDING;
    }

    public String getPAID_DATE() {
        return PAID_DATE;
    }

    public void setPAID_DATE(String PAID_DATE) {
        this.PAID_DATE = PAID_DATE;
    }
    
    
       public ArrayList<SVW_STUDENT_FEE_MANAGEMENT>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_FEE_MANAGEMENT> SVW_STUDENT_FEE_MANAGEMENTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_FEE_MANAGEMENT appStatus=new SVW_STUDENT_FEE_MANAGEMENT();
              
              appStatus.setAMOUNT(record.split("~")[0]);
              appStatus.setAUTH_STATUS(record.split("~")[1]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[2]);
              appStatus.setCHECKER_ID(record.split("~")[3]);
              appStatus.setCHECKER_REMARKS(record.split("~")[4]);
              appStatus.setDUE_DATE(record.split("~")[5]);
              appStatus.setFEE_ID(record.split("~")[6]);
              appStatus.setFEE_PAID(record.split("~")[7]);
              appStatus.setFEE_TYPE(record.split("~")[8]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[9]);
              appStatus.setMAKER_ID(record.split("~")[10]);
              appStatus.setMAKER_REMARKS(record.split("~")[11]);
              appStatus.setOUTSTANDING(record.split("~")[12]);
              appStatus.setPAID_DATE(record.split("~")[13]);
              appStatus.setRECORD_STATUS(record.split("~")[14]);
              appStatus.setSTATUS(record.split("~")[15]);
              appStatus.setSTUDENT_ID(record.split("~")[16]);
              appStatus.setVERSION_NUMBER(record.split("~")[17]);
             
                          
              
              
              
              
           SVW_STUDENT_FEE_MANAGEMENTList.add(appStatus);
          }
          
        return SVW_STUDENT_FEE_MANAGEMENTList;
           
      
}
    
}
