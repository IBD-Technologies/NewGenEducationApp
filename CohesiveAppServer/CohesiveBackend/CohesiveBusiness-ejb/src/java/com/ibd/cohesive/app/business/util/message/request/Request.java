/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util.message.request;

/**
 *
 * @author IBD Technologies
 */
public class Request {
   Header reqHeader;
   RequestBody reqBody; 
   AuditLog reqAudit;
   String autoAuthAccess;
    /**
     *
     */
    public Request()
   {       
       this.reqHeader=new Header();
       //this.reqBody=new Object();
       this.reqAudit=new AuditLog();
   }
    public Header getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(Header reqHeader) {
        this.reqHeader = reqHeader;
    }

    public RequestBody getReqBody() {
        return this.reqBody;
    }

    public void setReqBody(RequestBody reqBody) {
        this.reqBody = reqBody;
    }

    public AuditLog getReqAudit() {
        return reqAudit;
    }

    public void setReqAudit(AuditLog reqAudit) {
        this.reqAudit = reqAudit;
    }

    public String getAutoAuthAccess() {
        return autoAuthAccess;
    }

    public void setAutoAuthAccess(String autoAuthAccess) {
        this.autoAuthAccess = autoAuthAccess;
    }
    
    
   
}
