/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.institute.summary.Institutepayment;

/**
 *
 * @author DELL
 */
public class InstitutePaymentBO {
    InstitutePaymentFilter filter;
    InstitutePaymentResult result[];

    public InstitutePaymentFilter getFilter() {
        return filter;
    }

    public void setFilter(InstitutePaymentFilter filter) {
        this.filter = filter;
    }

    public InstitutePaymentResult[] getResult() {
        return result;
    }

    public void setResult(InstitutePaymentResult[] result) {
        this.result = result;
    }
    
}
