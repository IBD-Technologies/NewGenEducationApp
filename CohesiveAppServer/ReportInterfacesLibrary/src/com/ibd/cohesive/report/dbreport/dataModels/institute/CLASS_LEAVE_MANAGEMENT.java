/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

//import com.ibd.cohesive.report.dbreport.dataModels.user.CLASS_LEAVE_MANAGEMENT;
import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class CLASS_LEAVE_MANAGEMENT {
    String STUDENT_ID;
    String FROM;
    String TO;
    String FROM_NOON;
    String TO_NOON;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getFROM() {
        return FROM;
    }

    public void setFROM(String FROM) {
        this.FROM = FROM;
    }

    public String getTO() {
        return TO;
    }

    public void setTO(String TO) {
        this.TO = TO;
    }

    public String getFROM_NOON() {
        return FROM_NOON;
    }

    public void setFROM_NOON(String FROM_NOON) {
        this.FROM_NOON = FROM_NOON;
    }

    public String getTO_NOON() {
        return TO_NOON;
    }

    public void setTO_NOON(String TO_NOON) {
        this.TO_NOON = TO_NOON;
    }
    
     public ArrayList<CLASS_LEAVE_MANAGEMENT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_LEAVE_MANAGEMENT> CLASS_LEAVE_MANAGEMENTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_LEAVE_MANAGEMENT appStatus=new CLASS_LEAVE_MANAGEMENT();
              
              appStatus.setFROM(record.split("~")[0]);
              appStatus.setFROM_NOON(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setTO(record.split("~")[3]);
              appStatus.setTO_NOON(record.split("~")[4]);
              
              
              
              
              
           CLASS_LEAVE_MANAGEMENTList.add(appStatus);
          }
          
        return CLASS_LEAVE_MANAGEMENTList;
           
      
}
    
    
    
    
    
    
    
    
}
