/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.db.io.IPhysicalIOService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.db.write.IDBWriteBufferService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.EJBException;
import javax.ejb.Lock;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Singleton
@ConcurrencyManagement(BEAN)
public class DBWriteWorkerBean implements IDBWriteWorkerBean {
    private AtomicBoolean busy = new AtomicBoolean(false);
    DBDependencyInjection dbdi;
    Map<String,String>wipFileNames;
    private Object lock1 = new Object();
//    CohesiveSession session;
//    DBSession dbSession;
    
    public DBWriteWorkerBean() throws NamingException {
        dbdi = new DBDependencyInjection();
        wipFileNames=new ConcurrentHashMap();
//        session = new CohesiveSession();
//        dbSession = new DBSession(session);
    }
    @Lock(WRITE)
    public synchronized void doTimerWork(CohesiveSession p_session,DBSession p_dbSession) throws DBProcessingException,DBValidationException {
//       boolean l_session_created_now=false;
       Future<String> Result1 = null;
       Future<String> Result2 = null;
       Future<String> Result3 = null;
       Future<String> Result4 = null;
       String l_sessionIdentifier=null;
        try{
//         session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();

        synchronized(lock1){
            if (!busy.compareAndSet(false, true)) {
               return;
           }
        }
        l_sessionIdentifier=p_session.getI_session_identifier().toString();
        
        IDBWriteBufferService write=dbdi.getDBWriteService();
        IPhysicalIOService io=dbdi.getPhysicalIOService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        Map<Integer,WorkDispatch>dispatchedFileNames=new HashMap();
//        Map<String, Map<String,DBRecord>>l_fileMap=null;  
        Map<String, Map<String,DBRecord>>l_fileMap1=null;
        Map<String, Map<String,DBRecord>>l_fileMap2=null;
        Map<String, Map<String,DBRecord>>l_fileMap3=null;
        Map<String, Map<String,DBRecord>>l_fileMap4=null;
        String l_fileName=null;
        boolean dispatchFailed=false;
//            dbg("Timer work started",p_session);
             Iterator<String> fileIterator= write.getWriteBuffer().keySet().iterator();
//            Iterator<String> fileIterator= write.getWriteBuffer().keySet().iterator();
            int iteratorCount=0;
            while(fileIterator.hasNext()){
                if(iteratorCount>3)
                    break;
//                dbg("iteratorCount"+iteratorCount,p_session);
//                iteratorCount++;
               l_fileName=fileIterator.next();
                dbg("filename in worker bean"+l_fileName,p_session);
                System.out.println("filename dispatched for writing--->"+l_fileName+"for session-->"+p_session.getI_session_identifier()+"at--->"+fileUtil.getCurrentTime());
              if(l_fileName!=null){
                  
                   if(!(wipFileNames.containsKey(l_fileName)))  {
                       
//                if(!(checkDispatchedFileName(l_fileName,p_session)))  {
                  
               try{
                   
//                l_fileMap  =  write.getClonedFileFromWriteBuffer(l_fileName, p_session, p_dbSession);
                switch(iteratorCount){
                    
                     case 0:
                     l_fileMap1  =  write.getClonedFileFromWriteBuffer(l_fileName, p_session, p_dbSession);
                     Result1= (Future<String>)io.physicalIOWrite(l_fileName,l_fileMap1,p_session.getI_session_identifier());
                     break;
                    case 1:
                     l_fileMap2  =  write.getClonedFileFromWriteBuffer(l_fileName, p_session, p_dbSession);   
                     Result2= (Future<String>)io.physicalIOWrite(l_fileName,l_fileMap2,p_session.getI_session_identifier());
                     break;
                    case 2:
                    l_fileMap3  =  write.getClonedFileFromWriteBuffer(l_fileName, p_session, p_dbSession);
                     Result3= (Future<String>)io.physicalIOWrite(l_fileName,l_fileMap3,p_session.getI_session_identifier());
                     break;
                    case 3:
                    l_fileMap4  =  write.getClonedFileFromWriteBuffer(l_fileName, p_session, p_dbSession);
                     Result4= (Future<String>)io.physicalIOWrite(l_fileName,l_fileMap4,p_session.getI_session_identifier());
                     break;
                    
                }   
           
               } catch (EJBException ex) {
                   dispatchFailed=true;
              // Asynchronous method never dispatched, handle exception
              }
               
               iteratorCount++;   
              if( !dispatchFailed){
                  
                  switch(iteratorCount){
                      
                      case 1:   
                       WorkDispatch dispatch1=new WorkDispatch(l_fileName,"WIP",l_fileMap1);
                       dispatchedFileNames.put(iteratorCount, dispatch1);
                       wipFileNames.putIfAbsent(l_fileName, l_sessionIdentifier);
                       break;   
                      case 2:   
                       WorkDispatch dispatch2=new WorkDispatch(l_fileName,"WIP",l_fileMap2);
                       dispatchedFileNames.put(iteratorCount, dispatch2);
                       wipFileNames.putIfAbsent(l_fileName, l_sessionIdentifier);
                       break;
                      case 3:   
                       WorkDispatch dispatch3=new WorkDispatch(l_fileName,"WIP",l_fileMap3);
                       dispatchedFileNames.put(iteratorCount, dispatch3);
                       wipFileNames.putIfAbsent(l_fileName, l_sessionIdentifier);
                       break; 
                       case 4:   
                       WorkDispatch dispatch4=new WorkDispatch(l_fileName,"WIP",l_fileMap4);
                       dispatchedFileNames.put(iteratorCount, dispatch4);
                       wipFileNames.putIfAbsent(l_fileName, l_sessionIdentifier);
                       break;
                  }
                  
//               dispatchedFileNames.put(iteratorCount, dispatch);
              }
              l_fileName=null;
              dispatchFailed=false;
              }
              }
            }  
              
            
            if(wipFileNames.containsValue(l_sessionIdentifier)){
            
              if(dispatchedFileNames.size()>0){
                   
                  dispatchedFileNames.forEach((Integer i,WorkDispatch dis)->{
                       dbg("iterator  count"+i,p_session);
                       dbg("filename"+dis.getFileName(),p_session);
                       dbg("result"+dis.getResult(),p_session);
                       
              }
                  );
                  
               int threadCount=0;
               
               boolean result1CheckDone=false;
               boolean result2CheckDone=false;
               boolean result3CheckDone=false;
               boolean result4CheckDone=false;
               
               boolean comeOutLoop=false;
               
               while(comeOutLoop==false){
                   dbg("inside  while(threadCount<=dispatchedFileNames.size())",p_session);
                   dbg("dispatchedFileNames.size()"+dispatchedFileNames.size(),p_session);
                   dbg("thread count"+threadCount,p_session);
                 Iterator<Integer> keyIterator=  dispatchedFileNames.keySet().iterator();
                   WorkDispatch dispatch=null;
                   
                 while(keyIterator.hasNext()){
                     dbg("inside keyIterator.hasNext()",p_session);
                    Integer key= keyIterator.next();
                    dbg("key"+key,p_session);
                    switch(key){
                        case 1:
                            dbg("inside case1",p_session);
                            if(result1CheckDone==false){
                                dbg("result1CheckDone",p_session);
                            if(Result1.isDone()){
                                dbg("Result1.isDone()",p_session);
                                try {
                                    
                                  dispatch=  dispatchedFileNames.get(key);
                                  String result=Result1.get();
                                  dbg("Result1.get()"+result,p_session);
                                  dispatch.setResult(result);
                                
                                } catch (ExecutionException ex) {
                                    dbg(ex,p_session);
                                    dbg("inside ExecutionException in case1",p_session);
                                  dispatch.setResult("Fail");
                                    
////                                     Throwable theCause = ex.getCause();
////                                     if (theCause instanceof EJBException) {
////                                             // Handle the EJBException that might be wrapping a system exception
////                                               // which occurred during the asynchronous method execution.
////                                        Throwable theRootCause = theCause.getCause();
////                                        if (theRootCause != null) {
////                                           // Handle the system exception
////                                        }
////                                     } else ... // handle other causes 
//                               } catch (...) {
//                                              // handle other exceptions
                                }catch(Exception ex){
                                    dbg(ex,p_session);
                                    dbg("inside Exception in case1",p_session);
                                    dispatch.setResult("Fail");
                                }
                                threadCount++;
                                result1CheckDone=true;
                            }
                            }
                        break;
                        
                        case 2:
                            dbg("inside case2",p_session);
                            if(result2CheckDone==false){
                                dbg("result2CheckDone",p_session);
                            if(Result2.isDone()){
                                dbg("Result2.isDone()",p_session);
                                try {
                                  dispatch=  dispatchedFileNames.get(key);
                                  String result=Result2.get();
                                  dbg("Result2.get()"+result,p_session);
                                  dispatch.setResult(result);
                                
                                } catch (ExecutionException ex) {
                                    dbg(ex,p_session);
                                    dbg("inside ExecutionException in case2",p_session);
                                  dispatch.setResult("Fail");
                                
                                 }catch(Exception ex){
                                    dbg(ex,p_session);
                                    dbg("inside Exception in case2",p_session);
                                    dispatch.setResult("Fail");
                                }
                                threadCount++;
                                result2CheckDone=true;
                            }
                            }
                        break;
                        case 3:
                            dbg("inside case3",p_session);
                            if(result3CheckDone==false){
                                dbg("result3CheckDone",p_session);
                            if(Result3.isDone()){
                                dbg("Result3.isDone()",p_session);
                              try {
                                  dispatch=  dispatchedFileNames.get(key);
                                  String result=Result3.get();
                                  dbg("Result3.get()"+result,p_session);
                                  dispatch.setResult(result);
                                
                                } catch (ExecutionException ex) {
                                    dbg(ex,p_session);
                                    dbg("inside ExecutionException in case3",p_session);
                                  dispatch.setResult("Fail");
                                
                                }catch(Exception ex){
                                    dbg(ex,p_session);
                                    dbg("inside Exception in case3",p_session);
                                    dispatch.setResult("Fail");
                                }
                                threadCount++;
                                result3CheckDone=true;
                            }
                            }
                        break;
                        case 4:
                            dbg("inside case4",p_session);
                            if(result4CheckDone==false){
                                 dbg("result4CheckDone",p_session);
                            if(Result4.isDone()){
                                dbg("Result4.isDone()",p_session);
                                try {
                                  dispatch=  dispatchedFileNames.get(key);
                                  String result=Result4.get();
                                    dbg("Result4.get()"+result,p_session);
                                  dispatch.setResult(result);
                                
                                } catch (ExecutionException ex) {
                                    dbg(ex,p_session);
                                    dbg("inside ExecutionException in case4",p_session);
                                  dispatch.setResult("Fail");
                                
                                 }catch(Exception ex){
                                    dbg(ex,p_session);
                                    dbg("inside Exception in case4",p_session);
                                    dispatch.setResult("Fail");
                                }
                                threadCount++;
                                result4CheckDone=true;
                            }
                            }
                        break;
                        
                    }
                 }
                   
                 if(threadCount==dispatchedFileNames.size()){
                     comeOutLoop=true;
                 }
                 
               }
               
              
             Iterator<Integer> keyIterator=  dispatchedFileNames.keySet().iterator();
             while(keyIterator.hasNext()){
                 Integer key=keyIterator.next();
               
                 
                if(dispatchedFileNames.get(key).getResult().equals("Success")){
//                     write.removeFileFromWriteBuffer(dispatchedFileNames.get(key).getFileMap(),dispatchedFileNames.get(key).getFileName(), p_session);
                     String l_tempPath=fileUtil.getTempPath(dispatchedFileNames.get(key).getFileName());
                     
                     fileUtil.copyFileToActual(l_tempPath,dispatchedFileNames.get(key).getFileName(),p_session,p_dbSession,dbdi);
                     write.removeFileFromWriteBuffer(dispatchedFileNames.get(key).getFileMap(),dispatchedFileNames.get(key).getFileName(), p_session);
                    
                     fileUtil.deleteTempFile(dispatchedFileNames.get(key).getFileName());//Integartion changes
                     System.out.println("completed-->"+dispatchedFileNames.get(key).getFileName()+"for the session"+p_session.getI_session_identifier()+"at--->"+fileUtil.getCurrentTime());
                 
                
                }
                
                
                
             }
             
             ILockService lock=dbdi.getLockService();
             
             ArrayList<String>archivalFileNames=lock.getArchivalFileNames(p_session.getI_session_identifier());
             dbg("archivalFileNames size"+archivalFileNames.size(),p_session);
             
             IBDProperties i_db_properties=p_session.getCohesiveproperties();
             
             if(archivalFileNames!=null){
                 
                 for(int i=0;i<archivalFileNames.size();i++){
                     
                     String archivalFileName=archivalFileNames.get(i);
                     dbg("archivalFileName"+archivalFileName,p_session);
                    // Path temp = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                     //Path actual=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                     Path temp = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName );//+ i_db_properties.getProperty("FILE_EXTENSION"));
                     Path actual=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName );//+ i_db_properties.getProperty("FILE_EXTENSION"));
                     
                     dbg("temp path"+temp.toString(),p_session);
                     dbg("actual path"+actual.toString(),p_session);
                     
                     if(Files.exists(temp)){
                 
                         Files.copy(temp, actual,REPLACE_EXISTING);
                         dbg(temp+"  copied to  "+actual,p_session);
                     }
                    
                     if(Files.exists(temp)){
                         
                         Files.delete(temp);
                         dbg(temp+"  is deleted",p_session);
                     }
                     lock.releaseArchivalLock(archivalFileName, p_session, p_dbSession);
                 }
                 
                 
             }
             
             
              }
            }
            
            
          
            
            
//            dbg("Timer work done",p_session);
  
        
        }catch(DBValidationException ex){
            dbg(ex,p_session);
//            throw ex;
        }catch (DBProcessingException ex) {
            dbg(ex,p_session);
//            throw new DBProcessingException("Exception" + ex.toString());    
        }catch (Exception ex) {
            dbg(ex,p_session);
//            throw new DBProcessingException("Exception" + ex.toString());
       

        } finally {
              removeFileFromWIpFileNames(l_sessionIdentifier,p_session);
           busy.set(false);
        }  
        
    }
    

    public void removeFileFromWIpFileNames(String p_sessionId,CohesiveSession p_session){
        
       try{ 
        
        dbg("l_sessionIdentifier"+p_sessionId,p_session);  
          Iterator<String> fileNameIterator=  wipFileNames.keySet().iterator();
            while(fileNameIterator.hasNext()){
                String fileName=fileNameIterator.next();
                dbg("filename in remove"+fileName,p_session);
                dbg("session id for the filename"+wipFileNames.get(fileName),p_session);
                if(wipFileNames.get(fileName).equals(p_sessionId)){
                
                    wipFileNames.remove(fileName);
                  dbg(fileName+"removed from wipFileNames",p_session);
                }
            }
            
       }catch (Exception ex) {
            dbg(ex,p_session);

        }
    }
    
    
    
    private void dbg(String p_Value,CohesiveSession p_session) {
        p_session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex,CohesiveSession p_session) {
        p_session.getDebug().exceptionDbg(ex);

    }
    }
