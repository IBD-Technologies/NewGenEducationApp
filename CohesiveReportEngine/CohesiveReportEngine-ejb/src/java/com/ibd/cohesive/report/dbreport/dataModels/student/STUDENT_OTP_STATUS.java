/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.student;

//import com.ibd.cohesive.report.dbreport.dataModels.user.STUDENT_OTP_STATUS;
import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class STUDENT_OTP_STATUS {
    String STUDENT_ID;
    String END_POINT;
    String OTP;
    String VERIFICATION_STATUS;

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

    public String getVERIFICATION_STATUS() {
        return VERIFICATION_STATUS;
    }

    public void setVERIFICATION_STATUS(String VERIFICATION_STATUS) {
        this.VERIFICATION_STATUS = VERIFICATION_STATUS;
    }
    
     public ArrayList<STUDENT_OTP_STATUS>convertStringToArrayList(String result){
        
          ArrayList<STUDENT_OTP_STATUS> STUDENT_OTP_STATUSList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              STUDENT_OTP_STATUS appStatus=new STUDENT_OTP_STATUS();
              
              appStatus.setEND_POINT(record.split("~")[0]);
              appStatus.setOTP(record.split("~")[1]);
              appStatus.setSTUDENT_ID(record.split("~")[2]);
              appStatus.setVERIFICATION_STATUS(record.split("~")[3]);
             
              
              
              
           STUDENT_OTP_STATUSList.add(appStatus);
          }
          
        return STUDENT_OTP_STATUSList;
           
      
}
    
    
    
    
    
}
