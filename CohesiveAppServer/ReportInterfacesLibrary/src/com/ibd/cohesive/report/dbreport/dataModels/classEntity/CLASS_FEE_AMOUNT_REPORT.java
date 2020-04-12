/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class CLASS_FEE_AMOUNT_REPORT {
    String STANDARD;
    String SECTION;
    String FEE_TYPE;
    String TOTAL_FEE_AMOUNT;
    String TOTAL_PAID_AMOUNT;
    String TOTAL_BALANCE_AMOUNT;

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

    public String getFEE_TYPE() {
        return FEE_TYPE;
    }

    public void setFEE_TYPE(String FEE_TYPE) {
        this.FEE_TYPE = FEE_TYPE;
    }

    public String getTOTAL_FEE_AMOUNT() {
        return TOTAL_FEE_AMOUNT;
    }

    public void setTOTAL_FEE_AMOUNT(String TOTAL_FEE_AMOUNT) {
        this.TOTAL_FEE_AMOUNT = TOTAL_FEE_AMOUNT;
    }

    public String getTOTAL_PAID_AMOUNT() {
        return TOTAL_PAID_AMOUNT;
    }

    public void setTOTAL_PAID_AMOUNT(String TOTAL_PAID_AMOUNT) {
        this.TOTAL_PAID_AMOUNT = TOTAL_PAID_AMOUNT;
    }

    public String getTOTAL_BALANCE_AMOUNT() {
        return TOTAL_BALANCE_AMOUNT;
    }

    public void setTOTAL_BALANCE_AMOUNT(String TOTAL_BALANCE_AMOUNT) {
        this.TOTAL_BALANCE_AMOUNT = TOTAL_BALANCE_AMOUNT;
    }
    
    public ArrayList<CLASS_FEE_AMOUNT_REPORT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_FEE_AMOUNT_REPORT> CLASS_FEE_AMOUNT_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_FEE_AMOUNT_REPORT appStatus=new CLASS_FEE_AMOUNT_REPORT();
              
              appStatus.setFEE_TYPE(record.split("~")[0]);
              appStatus.setSECTION(record.split("~")[1]);
              appStatus.setSTANDARD(record.split("~")[2]);
              appStatus.setTOTAL_BALANCE_AMOUNT(record.split("~")[3]);
              appStatus.setTOTAL_FEE_AMOUNT(record.split("~")[4]);
              appStatus.setTOTAL_PAID_AMOUNT(record.split("~")[5]);
       
              
              
              
              
              
              
           CLASS_FEE_AMOUNT_REPORTList.add(appStatus);
          }
          
        return CLASS_FEE_AMOUNT_REPORTList;
           
      
}
    
    
    
    
    
    
    
}
