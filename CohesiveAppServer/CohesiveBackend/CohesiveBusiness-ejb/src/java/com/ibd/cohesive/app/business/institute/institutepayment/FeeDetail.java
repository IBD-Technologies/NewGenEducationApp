/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.institutepayment;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class FeeDetail implements Comparable<FeeDetail>{
    String feeID;
    Date dueDate;

    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    
    public int compareTo(FeeDetail compare) {
	
		return getDueDate().compareTo(compare.getDueDate());
		
		//descending order
		//return compareQuantity - this.quantity;
		
	}
}
