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
public class CLASS_FEE_MANAGEMENT {
    String STANDARD;
    String SECTION;
    String FEE_ID;
    String FEE_TYPE;
    String AMOUNT;
    String DUE_DATE;
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
    
      public ArrayList<CLASS_FEE_MANAGEMENT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_FEE_MANAGEMENT> CLASS_FEE_MANAGEMENTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_FEE_MANAGEMENT appStatus=new CLASS_FEE_MANAGEMENT();
              
              appStatus.setAMOUNT(record.split("~")[0]);
              appStatus.setAUTH_STATUS(record.split("~")[1]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[2]);
              appStatus.setCHECKER_ID(record.split("~")[3]);
              appStatus.setCHECKER_REMARKS(record.split("~")[4]);
              appStatus.setDUE_DATE(record.split("~")[5]);
              appStatus.setFEE_ID(record.split("~")[6]);
              appStatus.setFEE_TYPE(record.split("~")[7]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[8]);
              appStatus.setMAKER_ID(record.split("~")[9]);
              appStatus.setMAKER_REMARKS(record.split("~")[10]);
              appStatus.setRECORD_STATUS(record.split("~")[11]);
              appStatus.setSECTION(record.split("~")[12]);
              appStatus.setSTANDARD(record.split("~")[13]);
              appStatus.setVERSION_NUMBER(record.split("~")[14]);
              
              
              
              
              
           CLASS_FEE_MANAGEMENTList.add(appStatus);
          }
          
        return CLASS_FEE_MANAGEMENTList;
           
      
}
    
    
    
    

}
