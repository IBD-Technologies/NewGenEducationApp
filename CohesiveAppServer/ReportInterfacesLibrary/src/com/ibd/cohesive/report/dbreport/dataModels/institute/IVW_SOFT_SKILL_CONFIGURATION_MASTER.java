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
public class IVW_SOFT_SKILL_CONFIGURATION_MASTER {
    String INSTITUTE_ID;
    String MAKER_ID;
    String CHECKER_ID;
    String MAKER_DATE_STAMP;        
    String CHECKER_DATE_STAMP;       
    String RECORD_STATUS;       
    String AUTH_STATUS;       
    String VERSION_NUMBER;       
    String MAKER_REMARKS;       
    String CHECKER_REMARKS;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
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
    
    
            
       public ArrayList<IVW_SOFT_SKILL_CONFIGURATION_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_SOFT_SKILL_CONFIGURATION_MASTER> IVW_SOFT_SKILL_CONFIGURATION_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_SOFT_SKILL_CONFIGURATION_MASTER appStatus=new IVW_SOFT_SKILL_CONFIGURATION_MASTER();
              
              appStatus.setAUTH_STATUS(record.split("~")[0]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[1]);
              appStatus.setCHECKER_ID(record.split("~")[2]);
              appStatus.setCHECKER_REMARKS(record.split("~")[3]);
              appStatus.setINSTITUTE_ID(record.split("~")[4]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[5]);
              appStatus.setMAKER_ID(record.split("~")[6]);
              appStatus.setMAKER_REMARKS(record.split("~")[7]);
              appStatus.setRECORD_STATUS(record.split("~")[8]);
              appStatus.setVERSION_NUMBER(record.split("~")[9]);
              
              
              
           IVW_SOFT_SKILL_CONFIGURATION_MASTERList.add(appStatus);
          }
          
        return IVW_SOFT_SKILL_CONFIGURATION_MASTERList;
           
      
}
    
              
            
            
            
}

