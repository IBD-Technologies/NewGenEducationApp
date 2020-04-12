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
public class INSTITITUTE_FEE_PAYMENT {
    String STUDENT_ID;
    String PAYMENT_ID;
    String DATE;
    String AMOUNT;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getPAYMENT_ID() {
        return PAYMENT_ID;
    }

    public void setPAYMENT_ID(String PAYMENT_ID) {
        this.PAYMENT_ID = PAYMENT_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }
    
     public ArrayList<INSTITITUTE_FEE_PAYMENT>convertStringToArrayList(String result){
        
          ArrayList<INSTITITUTE_FEE_PAYMENT> INSTITITUTE_FEE_PAYMENTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              INSTITITUTE_FEE_PAYMENT appStatus=new INSTITITUTE_FEE_PAYMENT();
              
              appStatus.setAMOUNT(record.split("~")[0]);
              appStatus.setDATE(record.split("~")[1]);
              appStatus.setPAYMENT_ID(record.split("~")[2]);
              appStatus.setSTUDENT_ID(record.split("~")[3]);
              
              
              
              
              
           INSTITITUTE_FEE_PAYMENTList.add(appStatus);
          }
          
        return INSTITITUTE_FEE_PAYMENTList;
           
      
}
    
    
    
    
    
    
    
    
    
    
    
    
}
