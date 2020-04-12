/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class INSTITUTE_MASTER  {
   // private static final long serialVersionUID = 1234567L;
    String INSTITUTE_ID;
    String INSTITUTE_NAME;
    String IMAGE_PATH;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getINSTITUTE_NAME() {
        return INSTITUTE_NAME;
    }

    public void setINSTITUTE_NAME(String INSTITUTE_NAME) {
        this.INSTITUTE_NAME = INSTITUTE_NAME;
    }

    public String getIMAGE_PATH() {
        return IMAGE_PATH;
    }

    public void setIMAGE_PATH(String IMAGE_PATH) {
        this.IMAGE_PATH = IMAGE_PATH;
    }
    
      public ArrayList<INSTITUTE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<INSTITUTE_MASTER> instituteMasterList=new ArrayList();
          
//          try{
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];
              String instituteID=record.split("~")[0];
              String instituteName=record.split("~")[1];
              String imagePath=record.split("~")[2];
              
              INSTITUTE_MASTER insMaster=new INSTITUTE_MASTER();
              insMaster.setINSTITUTE_ID(instituteID);
              insMaster.setINSTITUTE_NAME(instituteName);
              insMaster.setIMAGE_PATH(imagePath);
              
              instituteMasterList.add(insMaster);
          }
          
          
          return instituteMasterList;
//      }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
      }
  
    
    
    
}
