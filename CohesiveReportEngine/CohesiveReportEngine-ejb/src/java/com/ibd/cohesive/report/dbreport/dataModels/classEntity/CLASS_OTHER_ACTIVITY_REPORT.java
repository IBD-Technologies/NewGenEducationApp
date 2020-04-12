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
public class CLASS_OTHER_ACTIVITY_REPORT {
    String STANDARD; 
    String SECTION;
    String YEAR;
    String NO_OF_EVENTS_CONDUCTED;
    String NO_OF_STUDENTS_PARTICIPATED;
    String LEVEL;
    String RESULT_TYPE;
    String COUNT;

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

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getNO_OF_EVENTS_CONDUCTED() {
        return NO_OF_EVENTS_CONDUCTED;
    }

    public void setNO_OF_EVENTS_CONDUCTED(String NO_OF_EVENTS_CONDUCTED) {
        this.NO_OF_EVENTS_CONDUCTED = NO_OF_EVENTS_CONDUCTED;
    }

    public String getNO_OF_STUDENTS_PARTICIPATED() {
        return NO_OF_STUDENTS_PARTICIPATED;
    }

    public void setNO_OF_STUDENTS_PARTICIPATED(String NO_OF_STUDENTS_PARTICIPATED) {
        this.NO_OF_STUDENTS_PARTICIPATED = NO_OF_STUDENTS_PARTICIPATED;
    }

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getRESULT_TYPE() {
        return RESULT_TYPE;
    }

    public void setRESULT_TYPE(String RESULT_TYPE) {
        this.RESULT_TYPE = RESULT_TYPE;
    }

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }
    
      public ArrayList<CLASS_OTHER_ACTIVITY_REPORT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_OTHER_ACTIVITY_REPORT> CLASS_OTHER_ACTIVITY_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_OTHER_ACTIVITY_REPORT appStatus=new CLASS_OTHER_ACTIVITY_REPORT();
              
              appStatus.setCOUNT(record.split("~")[0]);
              appStatus.setLEVEL(record.split("~")[1]);
              appStatus.setNO_OF_EVENTS_CONDUCTED(record.split("~")[2]);
              appStatus.setNO_OF_STUDENTS_PARTICIPATED(record.split("~")[3]);
              appStatus.setRESULT_TYPE(record.split("~")[4]);
              appStatus.setSECTION(record.split("~")[5]);
              appStatus.setSTANDARD(record.split("~")[6]);
              appStatus.setYEAR(record.split("~")[7]);
              
              
              
              
              
              
           CLASS_OTHER_ACTIVITY_REPORTList.add(appStatus);
          }
          
        return CLASS_OTHER_ACTIVITY_REPORTList;
           
      
}
    
    
    
    
    
    
    
}
