/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.io;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.db.waitmonitor.DBWait;
import com.ibd.cohesive.db.waitmonitor.IDBWaitBuffer;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.File;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class WaitWriteService implements IWaitWriteService {
    
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    
    public WaitWriteService() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
    
    public  void waitWrite()throws DBProcessingException{
         boolean l_session_created_now=false;
         FileChannel fc= null;
//          boolean exceptionRaised=false;
        try{
          session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside WaitWriteService--->waitWrite()");    
        IDBWaitBuffer wait=dbdi.getWaitBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Iterator<String> keyIterator=wait.getWaitBuffer().keySet().iterator();
         Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + "APP"+i_db_properties.getProperty("FOLDER_DELIMITER") + "Wait" + i_db_properties.getProperty("FILE_EXTENSION"));
          if(fc==null||!fc.isOpen()){
               fc = FileChannel.open(file, CREATE, WRITE, APPEND);
          }
//         String filePath = i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER") + "Wait" + i_db_properties.getProperty("FILE_EXTENSION");
//         PrintWriter writer=new PrintWriter(new File(filePath));   
//         PrintWriter writer=new PrintWriter(filePath);   
        while(keyIterator.hasNext()){
            String sessionID=keyIterator.next();
            dbg("inside WaitWriteService--->waitWrite()--->sessionID"+sessionID);
           if( checkSessionCompleted(sessionID)){
               dbg("session completed for "+sessionID);
               ArrayList<DBWait>l_dbWaitList=   wait.getWaitBuffer().get(sessionID);
               
               for(int i=0;i<l_dbWaitList.size();i++){
                   
                   DBWait l_dbWait=l_dbWaitList.get(i);
                   String l_fileName=l_dbWait.getFileName();
                   String l_tableName=l_dbWait.getTableName();
                   String l_primaryKey=l_dbWait.getPrimaryKey();
                   String l_className=l_dbWait.getClassName();
                   String l_methodName=l_dbWait.getMethodName();
                   String l_waitTime=String.valueOf(l_dbWait.getWaitTime());
                   String l_timeStamp=  l_dbWait.getTimeStamp();
                   String readBufferSize=Integer.toString(l_dbWait.getDbReadBufferSize());
                   String tempSegmentSize=Integer.toString(l_dbWait.getDbTempSegmentSize());
                   String writeBufferSize=Integer.toString(l_dbWait.getDbWriteBufferSize());
                   String waitBufferSize=Integer.toString(l_dbWait.getDbWaitBufferSize());
                   String recordTobeWritten=new String();
                   if(l_fileName!=null){
                       recordTobeWritten="#"+sessionID.concat("~").concat(l_fileName);
                   }else{
                       recordTobeWritten="#"+sessionID.concat("~");
                   }
                   if(l_tableName!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(l_tableName);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(l_primaryKey!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(l_primaryKey);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(l_className!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(l_className);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(l_methodName!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(l_methodName);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(l_waitTime!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(l_waitTime);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(l_timeStamp!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(l_timeStamp);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(readBufferSize!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(readBufferSize);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(tempSegmentSize!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(tempSegmentSize);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(writeBufferSize!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(writeBufferSize);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   if(waitBufferSize!=null){
                       recordTobeWritten=recordTobeWritten.concat("~").concat(waitBufferSize);
                   }else{
                       recordTobeWritten=recordTobeWritten.concat("~");
                   }
                   dbg("l_fileName"+l_fileName);
                   dbg("l_tableName"+l_tableName);
                   dbg("l_primaryKey"+l_primaryKey);
                   dbg("l_className"+l_className);
                   dbg("l_methodName"+l_methodName);
                   dbg("l_waitTime"+l_waitTime);
                   dbg("readBufferSize"+readBufferSize);
                   dbg("tempSegmentSize"+tempSegmentSize);
                   dbg("writeBufferSize"+writeBufferSize);
                   dbg("waitBufferSize"+waitBufferSize);
                   
                   dbg("recordTobeWritten"+recordTobeWritten);  
                   fc.write(ByteBuffer.wrap(recordTobeWritten.getBytes(Charset.forName("UTF-8"))));
//                   String filePath = i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER") + "Wait" + i_db_properties.getProperty("FILE_EXTENSION");
//                   dbg("filePath"+filePath);
//                   PrintWriter writer=new PrintWriter(new File(filePath));
//                   writer.println(recordTobeWritten);
//                   writer.flush();
//                   writer.close();
                   dbg("record is writed");
               }        
               wait.removeFromWaitBuffer(sessionID, session);
               
           }
        }    
          if(fc!=null){
             if(fc.isOpen()){
               fc.close();
            }
          }
//        writer.close();
        }catch(Exception ex){
                dbg(ex);
        }
        
        finally{

            if(l_session_created_now){    
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
               
        }
        
    }
    
    private boolean checkSessionCompleted(String p_sessionID)throws DBProcessingException{
        boolean isSessionCompleted=false;
        try{
            dbg("inside checkSession completed--->p_sessionID"+p_sessionID);
         IDBWaitBuffer wait=dbdi.getWaitBufferService();
         ArrayList<DBWait> dbWaitList=  wait.getWaitBuffer().get(p_sessionID);
            for(int i=0;i<dbWaitList.size();i++){
                if(dbWaitList.get(i).isIsSessionCompleted()){
                    isSessionCompleted=true;
                    break;
                }
            }
            dbg("endof  checkSession completed--->p_sessionID--->"+p_sessionID+"isSessionCompleted"+isSessionCompleted);
            
            return isSessionCompleted;
         }catch(Exception ex){
                dbg(ex);
                 throw new DBProcessingException("Exception" + ex.toString());
         }
    }
    
    
    private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
    
}
