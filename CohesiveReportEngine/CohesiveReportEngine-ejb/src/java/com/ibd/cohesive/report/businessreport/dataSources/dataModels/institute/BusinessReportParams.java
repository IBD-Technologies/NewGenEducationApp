/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSources.dataModels.institute;

import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class BusinessReportParams {
    String studentID;
    String studentName;
    String standard;
    String section;
    String feeID;
    String notificationType;
    String fromDate;
    String toDate;
    String studentStatus;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }
    
    
    public ArrayList<BusinessReportParams>convertStringToArrayList(String result){
        
          ArrayList<BusinessReportParams> BusinessReportParamsList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              BusinessReportParams appStatus=new BusinessReportParams();
              
              appStatus.setFeeID(record.split("~")[0]);
              appStatus.setFromDate(record.split("~")[1]);
              appStatus.setNotificationType(record.split("~")[2]);
              appStatus.setSection(record.split("~")[3]);
              appStatus.setStandard(record.split("~")[4]);
              appStatus.setStudentID(record.split("~")[5]);
              appStatus.setStudentName(record.split("~")[6]);
              
              if(record.split("~")[7].equals("O")){
                  
                  appStatus.setStudentStatus("Studying");
              }else if(record.split("~")[7].equals("D")){
                  
                  appStatus.setStudentStatus("Studied");
              }else{
                  appStatus.setStudentStatus(" ");
              }
              
//              appStatus.setStudentStatus(record.split("~")[7]);
              appStatus.setToDate(record.split("~")[8]);
           BusinessReportParamsList.add(appStatus);
          }
          
        return BusinessReportParamsList;
           
      
}
}
