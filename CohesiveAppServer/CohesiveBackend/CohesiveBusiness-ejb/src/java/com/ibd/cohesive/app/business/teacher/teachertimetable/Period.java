/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.teacher.teachertimetable;

/**
 *
 * @author DELL
 */
 public class Period implements Comparable<Period> {
    String periodNumber;
    String standard;
    String section;
    String subjectID;
    String operation;
    String subjectName;

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
    
    
    
    public Period(){
        
    }
    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }
    
    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

   
    public String getPeriodNumber() {
        return periodNumber;
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
    
     public int compareTo(Period comparePeriod) {
	
		int comparePeriodNumber = Integer.parseInt(((Period) comparePeriod).getPeriodNumber()); 
		
		//ascending order
		return Integer.parseInt(this.periodNumber) - comparePeriodNumber;
		
		//descending order
		//return compareQuantity - this.quantity;
		
	}
    
}
