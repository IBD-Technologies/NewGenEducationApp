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
public class NotificationDetailBusiness {
  String date;
  String notificationType;
  String studentID;
  String standard;
  String section;
  String message;
  String status;
  String studentName;
String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
  
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
  
  

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
  
  
  
  public ArrayList<NotificationDetailBusiness>convertStringToArrayList(String result){
        
          ArrayList<NotificationDetailBusiness> NotificationDetailBusinessList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              NotificationDetailBusiness appStatus=new NotificationDetailBusiness();
              
              appStatus.setDate(record.split("~")[0]);
              appStatus.setMessage(record.split("~")[1]);
              appStatus.setNotificationType(record.split("~")[2]);
              appStatus.setSection(record.split("~")[3]);
              appStatus.setStandard(record.split("~")[4]);
              appStatus.setStatus(record.split("~")[5]);
              appStatus.setStudentID(record.split("~")[6]);
              appStatus.setStudentName(record.split("~")[7]);
              appStatus.setSerialNumber(record.split("~")[8]);
           NotificationDetailBusinessList.add(appStatus);
          }
          
        return NotificationDetailBusinessList;
           
      
}

}
