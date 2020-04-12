/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.classEntity;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class CLASS_EXAM_RANK {
    String STUDENT_ID;
    String EXAM;
    String TOTAL;
    String RANK;

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getEXAM() {
        return EXAM;
    }

    public void setEXAM(String EXAM) {
        this.EXAM = EXAM;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getRANK() {
        return RANK;
    }

    public void setRANK(String RANK) {
        this.RANK = RANK;
    }
    
    
    
     public ArrayList<CLASS_EXAM_RANK>convertStringToArrayList(String result){
        
          ArrayList<CLASS_EXAM_RANK> CLASS_EXAM_RANKList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              CLASS_EXAM_RANK appStatus=new CLASS_EXAM_RANK();
              
              appStatus.setEXAM(record.split("~")[0]);
              appStatus.setRANK(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setTOTAL(record.split("~")[3]);
                          
              
              
              
              
              
           CLASS_EXAM_RANKList.add(appStatus);
          }
          
        return CLASS_EXAM_RANKList;
           
      
}
    
    
    
    
    
}
