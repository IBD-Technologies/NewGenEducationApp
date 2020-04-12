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
public class CLASS_MARK_REPORT {
    String STANDARD; 
    String SECTION;
    String SUBJECT_ID;
    String EXAM;
    String AVERAGE_MARK;
    String TOP_MARK;
    String LOW_MARK;

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

    public String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public void setSUBJECT_ID(String SUBJECT_ID) {
        this.SUBJECT_ID = SUBJECT_ID;
    }

    public String getEXAM() {
        return EXAM;
    }

    public void setEXAM(String EXAM) {
        this.EXAM = EXAM;
    }

    public String getAVERAGE_MARK() {
        return AVERAGE_MARK;
    }

    public void setAVERAGE_MARK(String AVERAGE_MARK) {
        this.AVERAGE_MARK = AVERAGE_MARK;
    }

    public String getTOP_MARK() {
        return TOP_MARK;
    }

    public void setTOP_MARK(String TOP_MARK) {
        this.TOP_MARK = TOP_MARK;
    }

    public String getLOW_MARK() {
        return LOW_MARK;
    }

    public void setLOW_MARK(String LOW_MARK) {
        this.LOW_MARK = LOW_MARK;
    }
    
    public ArrayList<CLASS_MARK_REPORT>convertStringToArrayList(String result){
        
          ArrayList<CLASS_MARK_REPORT> CLASS_MARK_REPORTList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_MARK_REPORT appStatus=new CLASS_MARK_REPORT();
              
              appStatus.setAVERAGE_MARK(record.split("~")[0]);
              appStatus.setEXAM(record.split("~")[1]);
              appStatus.setLOW_MARK(record.split("~")[2]);
              appStatus.setSECTION(record.split("~")[3]);
              appStatus.setSTANDARD(record.split("~")[4]);
              appStatus.setSUBJECT_ID(record.split("~")[5]);
              appStatus.setTOP_MARK(record.split("~")[6]);
              
              
              
              
              
              
              
           CLASS_MARK_REPORTList.add(appStatus);
          }
          
        return CLASS_MARK_REPORTList;
           
      
}
    
    
    
    
}
