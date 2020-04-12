/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util.exception;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.EJBException;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class ExceptionHandler {
    private Debug debug;

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }
    public JsonObject exceptionHandler(boolean iscurrentSession,CohesiveSession session,DBSession dbSession,AppDependencyInjection inject,Request request,JsonObject requestJson,String exceptionName) throws DBProcessingException
    {  
      BusinessService bs;
      JsonObject jsonResponse =null;
      ITransactionControlService tc =null;
      if (iscurrentSession)
      {    
      bs=inject.getBusinessService(session); 
       try {
           //bs=inject.getBusinessService(session);
           jsonResponse=bs.buildErrorResponse(request,requestJson,exceptionName,inject,session,dbSession) ;
//            jsonResponse=bs.buildErrorResponse(request,requestJson,"BSProcessingException",inject,session) ; 
             

           tc = inject.getTransactionControlService();
            } 
            catch (NamingException ex1) {
                   dbg(ex1);
                   throw new EJBException(ex1);
                   }
                   catch (Exception ex2) {
                   dbg(ex2);
                   throw new EJBException(ex2);
                   }
                 finally{
                  try
                  {   
                  tc.rollBack(session,dbSession);
                  }
                 catch(DBProcessingException e)
                 {
                  dbg(e);
                   throw (e);   
                 }       
                    }
      }
      
       return jsonResponse;       
    }
   
    public void dbg(String p_Value) {

        this.debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        this.debug.exceptionDbg(ex);

    }
    
}
    