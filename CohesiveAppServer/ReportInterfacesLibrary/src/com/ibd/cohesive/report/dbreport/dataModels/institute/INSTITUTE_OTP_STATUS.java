/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

//import com.ibd.cohesive.report.dbreport.dataModels.user.INSTITUTE_OTP_STATUS;
import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class INSTITUTE_OTP_STATUS {
    String INSTITUTE_ID;
    String STUDENT_ID;
    String END_POINT;
    String OTP;
    String TIME;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getEND_POINT() {
        return END_POINT;
    }

    public void setEND_POINT(String END_POINT) {
        this.END_POINT = END_POINT;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
    
    
     public ArrayList<INSTITUTE_OTP_STATUS>convertStringToArrayList(String result){
        
          ArrayList<INSTITUTE_OTP_STATUS> INSTITUTE_OTP_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              INSTITUTE_OTP_STATUS appStatus=new INSTITUTE_OTP_STATUS();
              
              appStatus.setEND_POINT(record.split("~")[0]);
              appStatus.setINSTITUTE_ID(record.split("~")[1]);
              appStatus.setOTP(record.split("~")[2]);
              appStatus.setSTUDENT_ID(record.split("~")[3]);
              appStatus.setTIME(record.split("~")[4]);
              
              
              
              
              
           INSTITUTE_OTP_STATUSList.add(appStatus);
          }
          
        return INSTITUTE_OTP_STATUSList;
           
      
}
    
    
    
    
}
