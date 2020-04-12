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
public class IVW_GROUP_MAPPING_MASTER {
    String INSTITUTE_ID;
    String GROUP_ID;
    String GROUP_DESCRIPTION;
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

    public String getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(String GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
    }

    public String getGROUP_DESCRIPTION() {
        return GROUP_DESCRIPTION;
    }

    public void setGROUP_DESCRIPTION(String GROUP_DESCRIPTION) {
        this.GROUP_DESCRIPTION = GROUP_DESCRIPTION;
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
    
    public ArrayList<IVW_GROUP_MAPPING_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_GROUP_MAPPING_MASTER> IVW_GROUP_MAPPING_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_GROUP_MAPPING_MASTER appStatus=new IVW_GROUP_MAPPING_MASTER();
              
              appStatus.setAUTH_STATUS(record.split("~")[0]);
              appStatus.setCHECKER_DATE_STAMP(record.split("~")[1]);
              appStatus.setCHECKER_ID(record.split("~")[2]);
              appStatus.setCHECKER_REMARKS(record.split("~")[3]);
              appStatus.setGROUP_DESCRIPTION(record.split("~")[4]);
              appStatus.setGROUP_ID(record.split("~")[5]);
              appStatus.setINSTITUTE_ID(record.split("~")[6]);
              appStatus.setMAKER_DATE_STAMP(record.split("~")[7]);
              appStatus.setMAKER_ID(record.split("~")[8]);
              appStatus.setMAKER_REMARKS(record.split("~")[9]);
              appStatus.setRECORD_STATUS(record.split("~")[10]);
              appStatus.setVERSION_NUMBER(record.split("~")[11]);
              
              
              
              
           IVW_GROUP_MAPPING_MASTERList.add(appStatus);
          }
          
        return IVW_GROUP_MAPPING_MASTERList;
           
      
}
   
    
}
