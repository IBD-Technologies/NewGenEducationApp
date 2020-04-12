/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.lock;

import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Singleton
@ConcurrencyManagement(BEAN)
public class BusinessLockService implements IBusinessLockService {
    Map<String,String>businessLockMap;
    CohesiveSession session;
    DBSession dbSession;
    public BusinessLockService() throws NamingException {
        businessLockMap=new ConcurrentHashMap();
    }
    
//    @Lock(LockType.READ)
    public boolean getBusinessLock(Request request,String primaryKey,CohesiveSession p_session)throws BSProcessingException{
        
        try{
          boolean status=false;  
          dbg("Inside getBusinessLock",p_session);
          String l_operation=request.getReqHeader().getOperation();
          
          if(l_operation.equals("View")){
              
              
              return true;
          }
          
          String l_serviceName= request.getReqHeader().getService();
          String l_userID=request.getReqHeader().getUserID();
          String l_key=l_serviceName.concat("~").concat(primaryKey);
          dbg("l_serviceName"+l_serviceName,p_session);
          dbg("l_userID"+l_userID,p_session);
          dbg("l_key"+l_key,p_session);
          dbg("primaryKey"+primaryKey,p_session);
          ErrorHandler errorHandler=p_session.getErrorhandler();
          
          if(businessLockMap.containsKey(l_key)){
              dbg("businessLockMap already contains"+l_key,p_session);
              status=false;
              errorHandler.log_app_error("BS_VAL_016", businessLockMap.get(l_key));
          }else{
              status=true;
              dbg("businessLockMap doesn't contains"+l_key,p_session);
//              businessLockMap.putIfAbsent(l_key, l_userID);
             if(!businessLockMap.containsKey(l_key)) {
                businessLockMap.put(l_key, l_userID);
             }
          }
         dbg("businessLockMap size"+businessLockMap.size(),p_session);   
         dbg("End of getBusinessLock",p_session); 
         return   status;
        
        }catch(Exception ex){
            dbg(ex,p_session);
            throw ex;
        }
        
        
        
    }
//    @Lock(LockType.READ)
    public void removeBusinessLock(Request request,String primaryKey,CohesiveSession p_session)throws BSProcessingException{
        
         try{
          dbg("Inside removeBusinessLock",p_session);
          String l_operation=request.getReqHeader().getOperation();
          if(l_operation.equals("View")){
              
              return ;
          }
          
          String l_serviceName= request.getReqHeader().getService();
          String l_userID=request.getReqHeader().getUserID();
          String l_key=l_serviceName.concat("~").concat(primaryKey);
          dbg("l_serviceName"+l_serviceName,p_session);
          dbg("l_userID"+l_userID,p_session);
          dbg("l_key"+l_key,p_session);
          dbg("primaryKey"+primaryKey,p_session);
      
              businessLockMap.remove(l_key, l_userID);
          dbg("businessLockMap size"+businessLockMap.size(),p_session);     
          dbg("End of removeBusinessLock",p_session);
        }catch(Exception ex){
            dbg(ex,p_session);
            throw ex;
        }
        
    }
    
    @PreDestroy
    public void destroyLock(){

           
       System.out.println("inside destroyBusiness lock map");
       businessLockMap=null;

       
    }
    
    private void dbg(String p_Value,CohesiveSession p_session) {
        p_session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex,CohesiveSession p_session) {
        p_session.getDebug().exceptionDbg(ex);

    }
    
}
