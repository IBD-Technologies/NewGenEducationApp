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
public class StudentDetails {
    String standard;
    String section;
    String studentID;
    String studentName;
    String contactPersonName;
    String contactNumber;
    String emailID;
    String creationDate;
    String deletedDate;
String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(String deletedDate) {
        this.deletedDate = deletedDate;
    }
    
    public ArrayList<StudentDetails>convertStringToArrayList(String result){
        
          ArrayList<StudentDetails> StudentDetailsList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              StudentDetails appStatus=new StudentDetails();
              
              appStatus.setContactNumber(record.split("~")[0]);
              appStatus.setContactPersonName(record.split("~")[1]);
              appStatus.setCreationDate(record.split("~")[2]);
              appStatus.setDeletedDate(record.split("~")[3]);
              appStatus.setEmailID(record.split("~")[4]);
              appStatus.setSection(record.split("~")[5]);
              appStatus.setStandard(record.split("~")[6]);
              appStatus.setStudentID(record.split("~")[7]);
              appStatus.setStudentName(record.split("~")[8]);
              appStatus.setSerialNumber(record.split("~")[9]);
           StudentDetailsList.add(appStatus);
          }
          
        return StudentDetailsList;
           
      
}
    
}
