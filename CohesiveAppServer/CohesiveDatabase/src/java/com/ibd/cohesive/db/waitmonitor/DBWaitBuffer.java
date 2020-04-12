/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.waitmonitor;

//import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
@Singleton
@ConcurrencyManagement(BEAN)
public class DBWaitBuffer implements IDBWaitBuffer {
    Map<String,ArrayList<DBWait>> waitBuffer;
    DBDependencyInjection dbdi;
     private static int initialCapacity;
    private static float loadFactor;
    private static int concurrencyLevel;
    
     public Map<String,ArrayList<DBWait>> getWaitBuffer() {
        return waitBuffer;
    }
     
     static
    {
     
        try{
        
         CohesiveSession tempSession= new CohesiveSession();
         String l_waitRequired=tempSession.getCohesiveproperties().getProperty("WAIT_REQUIRED");
         
         if(l_waitRequired.equals("YES")){
             initialCapacity= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_INITIAL_CAPACITY"));
             loadFactor= Float.parseFloat(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_LOAD_FACTOR"));
             concurrencyLevel= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_CONCURRENCY_LEVEL"));
         }else{
             initialCapacity=1;
             loadFactor=0.75f;
             concurrencyLevel=1;
             
         }
        }catch(Exception ex){
            initialCapacity=0;
        }
    }
     
      public DBWaitBuffer() throws NamingException {
        dbdi = new DBDependencyInjection();
        
        if(initialCapacity==0){
            waitBuffer=new ConcurrentHashMap();
        }else{
        
           waitBuffer=new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
           
        }   
    }
    
      public void putRecordToWaitBuffer(String p_sessionID,DBWait dbWait,CohesiveSession p_session)throws DBProcessingException{
          
          try{
             IBDProperties i_db_properties=p_session.getCohesiveproperties();
             String l_waitRequired=i_db_properties.getProperty("WAIT_REQUIRED");
        
             if(l_waitRequired.equals("YES")){
              
              dbg("inside put record to waitBuffer",p_session);
              dbg("inside put record to waitBuffer--->p_sessionID"+p_sessionID,p_session);
             if( waitBuffer.containsKey(p_sessionID)){
                 dbg("waitbuffer contains p_sessionID",p_session);
                 waitBuffer.get(p_sessionID).add(dbWait);
                 dbg("dbWait added to waitBuffer",p_session);
             }else{
                 dbg("waitbuffer not contains p_sessionID",p_session);
                 waitBuffer.putIfAbsent(p_sessionID, new ArrayList<DBWait>());
                 waitBuffer.get(p_sessionID).add(dbWait);
                 dbg("dbWait added to waitBuffer",p_session);
             }
              
              
           dbg("end of put record to waitBuffer",p_session);
             }
          }catch (Exception ex) {
           dbg(ex,p_session);
           throw new DBProcessingException("Exception" + ex.toString());
         }
          
      } 
      
   public int getWaitBufferSize(CohesiveSession p_session)throws DBProcessingException{
    
    try{
        dbg("inside   getWaitBufferSize--->size"+waitBuffer.size(),p_session);
        return waitBuffer.size();
        
    }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
}
      public void removeFromWaitBuffer(String sessionID,CohesiveSession p_session)throws DBProcessingException{
          
          try{
              IBDProperties i_db_properties=p_session.getCohesiveproperties();
              String l_waitRequired=i_db_properties.getProperty("WAIT_REQUIRED");
        
              if(l_waitRequired.equals("YES")){
              
              dbg("inside removeFromWaitBuffer",p_session);
             waitBuffer.remove(sessionID);
              dbg(sessionID+"is removed fromwait buffer",p_session);
              
              }
              
         }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
          
      }
      
     private void dbg(String p_Value,CohesiveSession p_session) {
        p_session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex,CohesiveSession p_session) {
        p_session.getDebug().exceptionDbg(ex);

    }  
}
