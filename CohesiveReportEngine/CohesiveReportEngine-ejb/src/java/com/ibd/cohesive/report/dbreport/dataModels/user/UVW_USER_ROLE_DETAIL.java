/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.user;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class UVW_USER_ROLE_DETAIL {
    String ROLE_ID;
    String FUNCTION_ID;
    String CREATE;
    String MODIFY;
    String DELETE;
    String VIEW;
    String AUTH;
    String AUTOAUTH;
    String REJECT;
    String VERSION_NUMBER;

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public String getFUNCTION_ID() {
        return FUNCTION_ID;
    }

    public void setFUNCTION_ID(String FUNCTION_ID) {
        this.FUNCTION_ID = FUNCTION_ID;
    }

    public String getCREATE() {
        return CREATE;
    }

    public void setCREATE(String CREATE) {
        this.CREATE = CREATE;
    }

    public String getMODIFY() {
        return MODIFY;
    }

    public void setMODIFY(String MODIFY) {
        this.MODIFY = MODIFY;
    }

    public String getDELETE() {
        return DELETE;
    }

    public void setDELETE(String DELETE) {
        this.DELETE = DELETE;
    }

    public String getVIEW() {
        return VIEW;
    }

    public void setVIEW(String VIEW) {
        this.VIEW = VIEW;
    }

    public String getAUTH() {
        return AUTH;
    }

    public void setAUTH(String AUTH) {
        this.AUTH = AUTH;
    }

    public String getAUTOAUTH() {
        return AUTOAUTH;
    }

    public void setAUTOAUTH(String AUTOAUTH) {
        this.AUTOAUTH = AUTOAUTH;
    }

    public String getREJECT() {
        return REJECT;
    }

    public void setREJECT(String REJECT) {
        this.REJECT = REJECT;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
    
      public ArrayList<UVW_USER_ROLE_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<UVW_USER_ROLE_DETAIL> UVW_USER_ROLE_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              UVW_USER_ROLE_DETAIL appStatus=new UVW_USER_ROLE_DETAIL();
              
              appStatus.setAUTH(record.split("~")[0]);
              appStatus.setAUTOAUTH(record.split("~")[1]);
              appStatus.setCREATE(record.split("~")[2]);
              appStatus.setDELETE(record.split("~")[3]);
              appStatus.setFUNCTION_ID(record.split("~")[4]);
              appStatus.setMODIFY(record.split("~")[5]);
              appStatus.setREJECT(record.split("~")[6]);
              appStatus.setROLE_ID(record.split("~")[7]);
              appStatus.setVERSION_NUMBER(record.split("~")[8]);
              appStatus.setVIEW(record.split("~")[5]);
              
              
              
              
              
              
           UVW_USER_ROLE_DETAILList.add(appStatus);
          }
          
        return UVW_USER_ROLE_DETAILList;
           
      
}

}
