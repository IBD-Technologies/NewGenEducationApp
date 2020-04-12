/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util.errorhandling;

import com.ibd.cohesive.util.debugger.Debug;
import java.util.ArrayList;

/**
 *
 * @author IBD Technologies
 */
public class ErrorHandler {

    StringBuffer session_error_code; //This is to track DB bvalidation
    StringBuffer single_err_code;////This is to track DB bvalidation
    ArrayList<Errors> error_list;
    Debug dbg;
    public ArrayList<Errors> getError_list() {
        return error_list;
    }
    Errors error;

    public ErrorHandler(Debug dbg) {
        session_error_code = new StringBuffer();//This is to track DB bvalidation
        single_err_code = new StringBuffer();////This is to track DB bvalidation
        error_list=new ArrayList();//This is to track BS validation 
        this.dbg=dbg;
    }

    public StringBuffer getSession_error_code() {
        return session_error_code;
    }

    public StringBuffer getSingle_err_code() {
        return single_err_code;
    }

    public void setSession_error_code(StringBuffer session_error_code) {
        this.session_error_code = session_error_code;
    }

    public void setSingle_err_code(StringBuffer single_err_code) {
        this.single_err_code = single_err_code;
    }
/*This function is called from validation part of each function.in each validation if validation fails 
    (i.e error occurs) then error code is set in the single error code.and then  this log_error() method is called to check whether
    single error code is set.if so session error code is appended with single error code and single error code is cleared 
    for next validation.if all the validation are performed then finally session error code is passed to the DBValidation
    exception
    */
    public void log_error() {
        
        if (this.single_err_code.length() > 0) {
            dbg.dbg("DB Error Logged:"+this.single_err_code);
            if (session_error_code.length() == 0) {
               
                session_error_code = session_error_code.append(this.single_err_code);
            } else {
                session_error_code = session_error_code.append("~").append(this.single_err_code);
            }
            this.single_err_code = new StringBuffer();
        }
    }


    public void log_app_error(String error_code,String error_param){
         dbg.dbg("App Error Logged:"+error_code);
         dbg.dbg("App Error Param:"+error_param);
         
         error=new Errors();
         error.setError_code(error_code);
         error.setError_param(error_param);
         error_list.add(error);



}
    
    public boolean removeSessionErrCode(String error_code)
    {
        
       //String l_sessionErrorCode=session_er;
              dbg.dbg("error_code"+error_code);
              
              String[] l_errorArray=session_error_code.toString().split("~");
              session_error_code = new StringBuffer();
              for(int i=0;i<l_errorArray.length;i++){
              if(!l_errorArray[i].contains(error_code))
              {    
              if (session_error_code.length() == 0) {
               //this.single_err_code = new StringBuffer();
               
  
                session_error_code = session_error_code.append(l_errorArray[i]);
                  } 
              else {
                session_error_code = session_error_code.append("~").append(l_errorArray[i]);
                   }
              }
              }
              
        return true;
    }       
//    public JsonObject buildJsonError(){
//        JsonObjectBuilder errorBuilder;
//        for(int i=0;i<error_list.size();i++){
//            Errors error=error_list.get(i);
//            String error_code=error.getError_code();
//            String error_param=error.getError_param();
//            errorBuilder=Json.createObjectBuilder();
//        }
}