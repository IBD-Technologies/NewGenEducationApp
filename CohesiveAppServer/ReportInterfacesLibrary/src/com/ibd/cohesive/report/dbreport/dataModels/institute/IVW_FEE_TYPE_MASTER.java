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
public class IVW_FEE_TYPE_MASTER {
    String FEE_TYPE;

    public String getFEE_TYPE() {
        return FEE_TYPE;
    }

    public void setFEE_TYPE(String FEE_TYPE) {
        this.FEE_TYPE = FEE_TYPE;
    }
    
     public ArrayList<IVW_FEE_TYPE_MASTER>convertStringToArrayList(String result){
        
          ArrayList<IVW_FEE_TYPE_MASTER> IVW_FEE_TYPE_MASTERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              IVW_FEE_TYPE_MASTER appStatus=new IVW_FEE_TYPE_MASTER();
              
              appStatus.setFEE_TYPE(record.split("~")[0]);
              
              
              
              
              
           IVW_FEE_TYPE_MASTERList.add(appStatus);
          }
          
        return IVW_FEE_TYPE_MASTERList;
           
      
}
    
    
}
