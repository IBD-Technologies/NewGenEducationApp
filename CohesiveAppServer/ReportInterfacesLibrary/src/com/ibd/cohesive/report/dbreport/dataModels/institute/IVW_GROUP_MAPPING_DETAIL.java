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
public class IVW_GROUP_MAPPING_DETAIL {
    String INSTITUTE_ID;
    String GROUP_ID;
    String STANDARD;
    String SECTION;
    String STUDENT_ID;
    String VERSION_NUMBER;

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

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getVERSION_NUMBER() {
        return VERSION_NUMBER;
    }

    public void setVERSION_NUMBER(String VERSION_NUMBER) {
        this.VERSION_NUMBER = VERSION_NUMBER;
    }
    
     public ArrayList<IVW_GROUP_MAPPING_DETAIL>convertStringToArrayList(String result){
        
          ArrayList<IVW_GROUP_MAPPING_DETAIL> IVW_GROUP_MAPPING_DETAILList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_GROUP_MAPPING_DETAIL appStatus=new IVW_GROUP_MAPPING_DETAIL();
              
              appStatus.setGROUP_ID(record.split("~")[0]);
              appStatus.setINSTITUTE_ID(record.split("~")[1]);
              appStatus.setSECTION(record.split("~")[2]);
              appStatus.setSTANDARD(record.split("~")[3]);
              appStatus.setSTUDENT_ID(record.split("~")[4]);
              appStatus.setVERSION_NUMBER(record.split("~")[5]);
              
              
              
              
           IVW_GROUP_MAPPING_DETAILList.add(appStatus);
          }
          
        return IVW_GROUP_MAPPING_DETAILList;
           
      
}
    
    
    
    
    
}
