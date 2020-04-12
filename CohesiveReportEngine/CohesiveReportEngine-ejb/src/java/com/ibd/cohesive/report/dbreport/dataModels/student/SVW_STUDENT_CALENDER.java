/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.student;

import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class SVW_STUDENT_CALENDER {
String   STUDENT_ID;
String   YEAR;
String   MONTH;
String   EVENTS;


    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

  

    public String getEVENTS() {
        return EVENTS;
    }

    public void setEVENTS(String EVENTS) {
        this.EVENTS = EVENTS;
    }

       public ArrayList<SVW_STUDENT_CALENDER>convertStringToArrayList(String result){
        
          ArrayList<SVW_STUDENT_CALENDER> SVW_STUDENT_CALENDERList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              SVW_STUDENT_CALENDER appStatus=new SVW_STUDENT_CALENDER();
              
              appStatus.setEVENTS(record.split("~")[0]);
              appStatus.setMONTH(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setYEAR(record.split("~")[3]);
              
                          
              
              
              
              
           SVW_STUDENT_CALENDERList.add(appStatus);
          }
          
        return SVW_STUDENT_CALENDERList;
           
      
}

}
