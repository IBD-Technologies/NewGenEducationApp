/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class SVW_STUDENT_NOTIFICATION {
    String STUDENT_ID;
    String NOTIFICATION_ID;
    String DATE;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }
    
     public ArrayList<SVW_STUDENT_NOTIFICATION>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_NOTIFICATION> SVW_STUDENT_NOTIFICATIONList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_NOTIFICATION appStatus=new SVW_STUDENT_NOTIFICATION();
              
              appStatus.setDATE(record.split("~")[0]);
              appStatus.setNOTIFICATION_ID(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
                      
              
              
              
              
              
           SVW_STUDENT_NOTIFICATIONList.add(appStatus);
          }
          
        return SVW_STUDENT_NOTIFICATIONList;
           
      
}
             
    
    
    
    
    
    
    
    
    
    
    
    
}
