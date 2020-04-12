/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.mark;

/**
 *
 * @author DELL
 */
public class StudentRank implements Comparable<StudentRank>{
    String studentID;
    int total;
    int rank;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
     public int compareTo(StudentRank compare) {
	
		int compareTotal = ((StudentRank) compare).getTotal(); 
		
		//ascending order
		return  compareTotal-this.total ;
		
		//descending order
		//return compareQuantity - this.quantity;
		
	}
    
}
