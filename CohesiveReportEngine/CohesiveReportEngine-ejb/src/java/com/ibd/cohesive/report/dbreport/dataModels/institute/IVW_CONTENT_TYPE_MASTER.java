/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class IVW_CONTENT_TYPE_MASTER {
    String CONTENT_TYPE;
    String DESCRIPTION;

    public String getCONTENT_TYPE() {
        return CONTENT_TYPE;
    }

    public void setCONTENT_TYPE(String CONTENT_TYPE) {
        this.CONTENT_TYPE = CONTENT_TYPE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }
    
      public ArrayList<IVW_CONTENT_TYPE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_CONTENT_TYPE_MASTER> IVW_CONTENT_TYPE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_CONTENT_TYPE_MASTER appStatus=new IVW_CONTENT_TYPE_MASTER();
              
              appStatus.setCONTENT_TYPE(record.split("~")[0]);
              appStatus.setDESCRIPTION(record.split("~")[1]);
              
              
              
              
           IVW_CONTENT_TYPE_MASTERList.add(appStatus);
          }
          
        return IVW_CONTENT_TYPE_MASTERList;
           
      
}
      
       
    
}
