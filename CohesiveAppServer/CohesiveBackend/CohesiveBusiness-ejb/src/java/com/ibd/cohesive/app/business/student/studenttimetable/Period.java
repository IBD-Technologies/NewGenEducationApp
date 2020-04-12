/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.student.studenttimetable;

/**
 *
 * @author DELL
 */
public class Period implements Comparable<Period> {
    String periodNumber;
    String subjectID;
    String subjectName;
    String teacherName;
    String teacherID;
    String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    
    
    
    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    
   public int compareTo(Period comparePeriod) {
	
		int comparePeriodNumber = Integer.parseInt(((Period) comparePeriod).getPeriodNumber()); 
		
		//ascending order
		return Integer.parseInt(this.periodNumber) - comparePeriodNumber;
		
		//descending order
		//return compareQuantity - this.quantity;
		
	}
    
    public Period(){
        
    }
}
