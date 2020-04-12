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
public class FEE_BATCH_INDICATOR {
    String FEE_ID;
    String STATUS;

    public String getFEE_ID() {
        return FEE_ID;
    }

    public void setFEE_ID(String FEE_ID) {
        this.FEE_ID = FEE_ID;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
    
   
    public ArrayList<FEE_BATCH_INDICATOR>convertStringToArrayList(String result){
        
          ArrayList<FEE_BATCH_INDICATOR> FEE_BATCH_INDICATORList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              FEE_BATCH_INDICATOR appStatus=new FEE_BATCH_INDICATOR();
              
              appStatus.setFEE_ID(record.split("~")[0]);
              appStatus.setSTATUS(record.split("~")[1]);
              
              
              
              
           FEE_BATCH_INDICATORList.add(appStatus);
          }
          
        return FEE_BATCH_INDICATORList;
           
      
}
   
     
    
   
    
}
