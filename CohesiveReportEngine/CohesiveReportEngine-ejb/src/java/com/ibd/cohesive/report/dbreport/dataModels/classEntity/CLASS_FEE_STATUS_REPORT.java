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
public class CLASS_FEE_STATUS_REPORT {
    String STANDARD;
    String SECTION;
    String FEE_TYPE;
    String STATUS;
    String NO_OF_STUDENTS;

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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getNO_OF_STUDENTS() {
        return NO_OF_STUDENTS;
    }

    public void setNO_OF_STUDENTS(String NO_OF_STUDENTS) {
        this.NO_OF_STUDENTS = NO_OF_STUDENTS;
    }
    
     public ArrayList<CLASS_FEE_STATUS_REPORT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_FEE_STATUS_REPORT> CLASS_FEE_STATUS_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_FEE_STATUS_REPORT appStatus=new CLASS_FEE_STATUS_REPORT();
              
              appStatus.setFEE_TYPE(record.split("~")[0]);
              appStatus.setNO_OF_STUDENTS(record.split("~")[1]);
              appStatus.setSECTION(record.split("~")[2]);
              appStatus.setSTANDARD(record.split("~")[3]);
              appStatus.setSTATUS(record.split("~")[4]);
              
       
              
              
              
              
              
              
           CLASS_FEE_STATUS_REPORTList.add(appStatus);
          }
          
        return CLASS_FEE_STATUS_REPORTList;
           
      
}
    
    
    
    
    
    
    
}
