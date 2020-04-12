/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.userrole;

/**
 *
 * @author IBD Technologies
 */
public class Functions {
    String functionID;
    String create;
    String modify;
    String delete;
    String view;
    String auth;
    String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAutoAuth() {
        return autoAuth;
    }

    public void setAutoAuth(String autoAuth) {
        this.autoAuth = autoAuth;
    }
    String autoAuth;
    String reject;
//    String defaultandValidate;

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public String getFunctionID() {
        return functionID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

   

    public String getModify() {
        return modify;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

    

    

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
