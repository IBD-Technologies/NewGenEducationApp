/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.readbuffer;

import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.io.IPhysicalIOService;
//import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.tempSegment.IDBTempSegmentService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
//import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
//import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.Singleton;
import javax.naming.NamingException;
import com.ibd.cohesive.db.write.IDBWriteBufferService;


/**
 *
 * @author DELL
 */
@Singleton
@ConcurrencyManagement(BEAN)
//@AccessTimeout(value=30000)

public class DBReadBufferService implements IDBReadBufferService{
    DBDependencyInjection dbdi;
//    CohesiveSession session;
//    DBSession dbSession;
    Map<String, DBFileContent> readBufferMap ;
    private Object lock1 = new Object();
//     @Lock(LockType.READ)
    private static int initialCapacity;
    private static float loadFactor;
    private static int concurrencyLevel;
    
    static
    {
     
        try{
        
         CohesiveSession tempSession= new CohesiveSession();
         initialCapacity= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_INITIAL_CAPACITY"));
         loadFactor= Float.parseFloat(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_LOAD_FACTOR"));
         concurrencyLevel= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_CONCURRENCY_LEVEL"));
        
        }catch(Exception ex){
            initialCapacity=0;
        }
    }
    public Map<String, DBFileContent> getReadBufferMap() {
        return readBufferMap;
    }
    DBFileContent head=null;
    DBFileContent end=null;
//    String filterkey_dummy;
//    int maxversion_dummy;
//    Map<String,DBRecord>filtermap_dummy;
    public DBReadBufferService() throws NamingException {
        dbdi = new DBDependencyInjection();
//        session = new CohesiveSession();
//        dbSession = new DBSession(session);
//        readBufferMap=new HashMap();
 //       readBufferMap=new ConcurrentHashMap();
    if(initialCapacity==0){
        readBufferMap=new ConcurrentHashMap();
     }else{
         
         readBufferMap=new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
     }
    }
    //This function returns the map such that Key is TableName and Value is Map of (Key - Primary key,Values - record values)
//  public Map<String, Map<String,ArrayList<String>>>readFullFile(String fileNameKey)throws  DBProcessingException{
//     boolean l_session_created_now=false;
//      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
//        dbg("inside DBRead Buffer---->getFileMap ");
//        dbg("inside DBRead Buffer---->getFileMap--->fileNameKey "+fileNameKey);
//        int capacity=Integer.parseInt(session.getCohesiveproperties().getProperty("DB_READ_BUFFER_CAPACITY"));
//        if(readBufferMap.containsKey(fileNameKey)){
//            DBFileContent fileContent = readBufferMap.get(fileNameKey);
//            remove(fileContent);
//            setHead(fileContent);
//            
//            dbg("end of DBRead Buffer---->getFileMap");
//           Map<String, Map<String,DBRecord>>mapWithDBRecord= fileContent.fileMap;
//           Iterator keyItearator=mapWithDBRecord.keySet().iterator();
//           
//           while(keyItearator.hasNext()){
//               String l_tableName=(String)keyItearator.next();
//               Map<String,DBRecord> dbrecord=mapWithDBRecord.get(l_tableName);
//               
//           }
//           
//           
//        }
//        dbg("end of DBRead Buffer---->getFileMap");
//        return null;  
//        }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
//    }
    
    //This function returns the map such that Key is TableName and Value is Map of (Key - Primary key,Values - DBRecord with position)
//     @Lock(LockType.WRITE)
  public Map<String, Map<String,DBRecord>>readFullFile(String fileName,String p_fileType,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException,DBValidationException{
//     boolean l_session_created_now=false; 
    long startTime=0;
     try{
         startTime=p_dbSession.getIibd_file_util(). getStartTime();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        String   fileNameKey=fileName;
        IPhysicalIOService io=dbdi.getPhysicalIOService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        Map<String, Map<String,DBRecord>>l_clonedFileMap;
        
//      String   fileNameKey=fileName+p_fileType;
          dbg("inside DBRead Buffer---->readFullFile ",p_session);
        dbg("inside DBRead Buffer---->readFullFile--->fileNameKey "+fileNameKey,p_session);
        int capacity=Integer.parseInt(p_session.getCohesiveproperties().getProperty("DB_READ_BUFFER_CAPACITY"));
        Map<String, Map<String,DBRecord>>l_fileMap;
        IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
        
        long startTime5=p_dbSession.getIibd_file_util(). getStartTime();
//       synchronized(lock1){
        l_fileMap=getFileFromBuffer(fileNameKey,p_session,p_dbSession);
        
//        if(l_fileMap!=null){
//        long startTime6=p_dbSession.getIibd_file_util(). getStartTime();
//        
//         l_clonedFileMap=fileUtil.cloneFile(l_fileMap);
//        
//         try{
//               
//           p_dbSession.getIibd_file_util().logWriteBuffer(fileName,null,null,"DBReadBufferService","readFullFile.l_clonedFileMap",p_session,p_dbSession,dbdi,startTime6);
//
//           }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//           }
//       }
//       }
       try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.getFileFromBuffer",p_session,p_dbSession,dbdi,startTime5);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
       
       
        if(l_fileMap!=null){
                
                
                
//        if(readBufferMap.containsKey(fileNameKey)){//
            dbg("inside dbReadBufferService--->readFullFile--->buffer map contains filename",p_session);
//            DBFileContent fileContent = readBufferMap.get(fileNameKey);
//            remove(fileContent);
//            setHead(fileContent);
//            
//            dbg("end of DBRead Buffer---->getFileMap");
//                    dbg("end of DBRead Buffer---->getFileMap");
        dbg("end of DBRead Buffer---->readFullFile ",p_session);
//        long startTime6=p_dbSession.getIibd_file_util(). getStartTime();
//         Map<String, Map<String,DBRecord>>l_clonedFileMap=fileUtil.cloneFile(l_fileMap);
//         try{
//               
//           p_dbSession.getIibd_file_util().logWriteBuffer(fileName,null,null,"DBReadBufferService","readFullFile.l_clonedFileMap",p_session,p_dbSession,dbdi,startTime6);
//
//           }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//           }
                  readFileFromTempBuffer( l_fileMap,fileName,p_session,p_dbSession);
                 // readFromWriteBuffer(l_fileMap,fileName,p_session,p_dbSession);      
              return l_fileMap;
             
        }else{
            dbg("inside dbReadBufferService--->readFullFile--->buffer map doesn't contains filename",p_session);
           long startTime1=p_dbSession.getIibd_file_util(). getStartTime();
		   
           try{
           
           
           l_fileMap= io.physicalIORead(fileName, p_fileType,p_session);
           
           }catch(DBValidationException ex){
               
               if(ex.toString().contains("DB_VAL_000")){
                   
                   
                   dbg("inside readFullFile after physicalIORead DB_VAL_000",p_session);
                    try{
            if(dbdi.getLockService().isSameSessionIOLock(fileName, p_session, p_dbSession)){
                
                dbdi.getLockService().releaseIOLock(fileName, p_session, p_dbSession);
            }
            
            }catch(Exception ex1){
                dbg(ex1,p_session);
                throw new DBProcessingException("Exception" + ex.toString());
            }
                   boolean comeOut=false;
                  
                  while(!comeOut){
                  
                      if(!writeBuffer.checkFileExistenceInWriteBuffer(fileName, p_session)){
                             Thread.sleep(5000);
                             comeOut=true;
                      }
                  
                  }
                   
                  dbg("before second physicalIORead ",p_session);
                  l_fileMap= io.physicalIORead(fileName, p_fileType,p_session);
                  
                  
                   
               }else{
                   
                   throw ex;
               }
           }
           
           
           try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.io.physicalIORead",p_session,p_dbSession,dbdi,startTime1);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           
//            l_fileMap=dbPhysicalRead(fileName,p_fileType,p_session,p_dbSession);
            
            Iterator keyIterator=l_fileMap.keySet().iterator();//iteration of table name
            Map<String,DBRecord>maxVersionMap;
            
            while(keyIterator.hasNext()){
                String l_tableName=(String)keyIterator.next();
                maxVersionMap=  versionFilter(l_fileMap.get(l_tableName),p_fileType,l_tableName,p_session,p_dbSession);
                l_fileMap.replace(l_tableName, maxVersionMap);
            }
            
            long startTime2=p_dbSession.getIibd_file_util(). getStartTime();
            readFromWriteBuffer(l_fileMap,fileName,p_session,p_dbSession);
            
             try{
            if(dbdi.getLockService().isSameSessionIOLock(fileName, p_session, p_dbSession)){
                
                dbdi.getLockService().releaseIOLock(fileName, p_session, p_dbSession);
            }
            
            }catch(Exception ex){
                dbg(ex,p_session);
                throw new DBProcessingException("Exception" + ex.toString());
            }
             try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.readFromWriteBuffer",p_session,p_dbSession,dbdi,startTime2);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            
             
            long startTime3=p_dbSession.getIibd_file_util(). getStartTime(); 
           Map<String, Map<String,DBRecord>>clonedFileMap= fileUtil.cloneFile(l_fileMap);
//            synchronized(lock1){
           setFileToBuffer( fileNameKey,l_fileMap,p_session,p_dbSession);
//            }
            
            try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.setFileToBuffer",p_session,p_dbSession,dbdi,startTime3);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            
        dbg("end of DBRead Buffer---->readFullFile ",p_session);
        
        long startTime4=p_dbSession.getIibd_file_util(). getStartTime();
       readFileFromTempBuffer( clonedFileMap,fileName,p_session,p_dbSession);
       
        try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.readFileFromTempBuffer",p_session,p_dbSession,dbdi,startTime4);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            return clonedFileMap;
        }
//        return null;
        
    } catch(DBValidationException ex){
           throw ex;
    }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
         
         try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile",p_session,p_dbSession,dbdi,startTime);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }  try{
            if(dbdi.getLockService().isSameSessionIOLock(fileName, p_session, p_dbSession)){
                
                dbdi.getLockService().releaseIOLock(fileName, p_session, p_dbSession);
            }
            
            }catch(Exception ex){
                dbg(ex,p_session);
                throw new DBProcessingException("Exception" + ex.toString());
            }
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            filterkey_dummy=null;
//            maxversion_dummy=0;
//            filtermap_dummy=null;
//            }
        }
    }
  
  public Map<String, Map<String,DBRecord>>readFullFile(String fileName,String p_fileType,CohesiveSession p_session,DBSession p_dbSession,boolean maxVersionRequired)throws  DBProcessingException,DBValidationException{
//     boolean l_session_created_now=false; 
    long startTime=0;
     try{
         startTime=p_dbSession.getIibd_file_util(). getStartTime();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        String   fileNameKey=fileName;
        IPhysicalIOService io=dbdi.getPhysicalIOService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        Map<String, Map<String,DBRecord>>l_clonedFileMap;
        
//      String   fileNameKey=fileName+p_fileType;
          dbg("inside DBRead Buffer---->readFullFile ",p_session);
        dbg("inside DBRead Buffer---->readFullFile--->fileNameKey "+fileNameKey,p_session);
        int capacity=Integer.parseInt(p_session.getCohesiveproperties().getProperty("DB_READ_BUFFER_CAPACITY"));
        Map<String, Map<String,DBRecord>>l_fileMap;
        
        long startTime5=p_dbSession.getIibd_file_util(). getStartTime();
//       synchronized(lock1){
        l_fileMap=getFileFromBuffer(fileNameKey,p_session,p_dbSession);
        
//        if(l_fileMap!=null){
//        long startTime6=p_dbSession.getIibd_file_util(). getStartTime();
//        
//         l_clonedFileMap=fileUtil.cloneFile(l_fileMap);
//        
//         try{
//               
//           p_dbSession.getIibd_file_util().logWriteBuffer(fileName,null,null,"DBReadBufferService","readFullFile.l_clonedFileMap",p_session,p_dbSession,dbdi,startTime6);
//
//           }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//           }
//       }
//       }
       try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.getFileFromBuffer",p_session,p_dbSession,dbdi,startTime5);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
       
       
        if(l_fileMap!=null){
                
                
                
//        if(readBufferMap.containsKey(fileNameKey)){//
            dbg("inside dbReadBufferService--->readFullFile--->buffer map contains filename",p_session);
//            DBFileContent fileContent = readBufferMap.get(fileNameKey);
//            remove(fileContent);
//            setHead(fileContent);
//            
//            dbg("end of DBRead Buffer---->getFileMap");
//                    dbg("end of DBRead Buffer---->getFileMap");
        dbg("end of DBRead Buffer---->readFullFile ",p_session);
//        long startTime6=p_dbSession.getIibd_file_util(). getStartTime();
//         Map<String, Map<String,DBRecord>>l_clonedFileMap=fileUtil.cloneFile(l_fileMap);
//         try{
//               
//           p_dbSession.getIibd_file_util().logWriteBuffer(fileName,null,null,"DBReadBufferService","readFullFile.l_clonedFileMap",p_session,p_dbSession,dbdi,startTime6);
//
//           }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//           }
 readFileFromTempBuffer( l_fileMap,fileName,p_session,p_dbSession);
              return l_fileMap;
             
        }else{
            dbg("inside dbReadBufferService--->readFullFile--->buffer map doesn't contains filename",p_session);
           long startTime1=p_dbSession.getIibd_file_util(). getStartTime();
		   
           l_fileMap= io.physicalIORead(fileName, p_fileType,p_session);
           
           
           try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.io.physicalIORead",p_session,p_dbSession,dbdi,startTime1);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           
//           if(maxVersionRequired==false){
//           
//               setFileToBuffer( fileNameKey,l_fileMap,p_session,p_dbSession);
//               Map<String, Map<String,DBRecord>>clonedFileMap= fileUtil.cloneFile(l_fileMap);
//               return clonedFileMap;
//           }
//            l_fileMap=dbPhysicalRead(fileName,p_fileType,p_session,p_dbSession);
            if(maxVersionRequired==true){
                Iterator keyIterator=l_fileMap.keySet().iterator();//iteration of table name
                Map<String,DBRecord>maxVersionMap;

                while(keyIterator.hasNext()){
                    String l_tableName=(String)keyIterator.next();
                    maxVersionMap=  versionFilter(l_fileMap.get(l_tableName),p_fileType,l_tableName,p_session,p_dbSession);
                    l_fileMap.replace(l_tableName, maxVersionMap);
                }
            } 
            long startTime2=p_dbSession.getIibd_file_util(). getStartTime();
            readFromWriteBuffer(l_fileMap,fileName,p_session,p_dbSession);
            
              try{
            if(dbdi.getLockService().isSameSessionIOLock(fileName, p_session, p_dbSession)){
                
                dbdi.getLockService().releaseIOLock(fileName, p_session, p_dbSession);
            }
            
            }catch(Exception ex){
                dbg(ex,p_session);
                throw new DBProcessingException("Exception" + ex.toString());
            }
            
            
             try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.readFromWriteBuffer",p_session,p_dbSession,dbdi,startTime2);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            
             
            long startTime3=p_dbSession.getIibd_file_util(). getStartTime(); 
           Map<String, Map<String,DBRecord>>clonedFileMap= fileUtil.cloneFile(l_fileMap);
//            synchronized(lock1){
           setFileToBuffer( fileNameKey,l_fileMap,p_session,p_dbSession);
//            }
            
            try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.setFileToBuffer",p_session,p_dbSession,dbdi,startTime3);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            
        dbg("end of DBRead Buffer---->readFullFile ",p_session);
        
        long startTime4=p_dbSession.getIibd_file_util(). getStartTime();
       readFileFromTempBuffer( clonedFileMap,fileName,p_session,p_dbSession);
       
        try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile.readFileFromTempBuffer",p_session,p_dbSession,dbdi,startTime4);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            return clonedFileMap;
        }
//        return null;
        
    } catch(DBValidationException ex){
           throw ex;
    }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
         
         try{
               
           p_dbSession.getIibd_file_util().logWaitBuffer(fileName,null,null,"DBReadBufferService","readFullFile",p_session,p_dbSession,dbdi,startTime);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           try{
            if(dbdi.getLockService().isSameSessionIOLock(fileName, p_session, p_dbSession)){
                
                dbdi.getLockService().releaseIOLock(fileName, p_session, p_dbSession);
            }
            
            }catch(Exception ex){
                dbg(ex,p_session);
                throw new DBProcessingException("Exception" + ex.toString());
            }
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            filterkey_dummy=null;
//            maxversion_dummy=0;
//            filtermap_dummy=null;
//            }
        }
    }
//   @Lock(LockType.WRITE)
//  public Map<String, Map<String,DBRecord>>readFullFile(String fileNameKey,String p_fileType,CohesiveSession session)throws  DBProcessingException,DBValidationException{
//      CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        return readFullFile(fileNameKey,p_fileType);
//     } catch(DBValidationException ex){
//           throw ex;
//    }catch(DBProcessingException ex){
//        dbg(ex);
//        throw new DBProcessingException("DBProcessingException" + ex.toString());
//        }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//           this.session=tempSession;
//     }
//  }
//   @Lock(LockType.WRITE)
  public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException,DBValidationException{
//      boolean l_session_created_now=false;
      Map<String,DBRecord> l_tableMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler=p_session.getErrorhandler();
        dbg("inside dbReadBufferService--->readTable",p_session);
        String   l_fileNameKey=fileName;
//        String l_fileNameKey=fileName+p_fileType;
//        l_tableMap=new HashMap();
        Map<String, Map<String,DBRecord>>l_fileMap=readFullFile(fileName,p_fileType,p_session,p_dbSession);
        
        l_tableMap=l_fileMap.get(p_tableName);
        
        if(l_tableMap==null){
            StringBuffer single_error_code = new StringBuffer();
                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
                errorhandler.setSingle_err_code(single_error_code);
                errorhandler.log_error();
               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
        }
//        if(readBufferMap.containsKey(l_fileNameKey)){
//        dbg("inside dbReadBufferService--->readTable--->buffer map contains file name",p_session);
//            l_fileMap=getFileFromBuffer(l_fileNameKey,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//             dbg("inside dbReadBufferService--->readTable--->buffer map contains file name and table name",p_session);
//                   l_tableMap=  l_fileMap.get(p_tableName);
//            }else{
//             dbg("inside dbReadBufferService--->readTable--->buffer map contains file name and does not contain tablename",p_session);
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }else{
//         dbg("inside dbReadBufferService--->readTable--->buffer map not contains file name ",p_session);
//            l_fileMap=dbPhysicalRead(l_fileNameKey,p_fileType,p_session,p_dbSession);
//            Iterator keyIterator=l_fileMap.keySet().iterator();//iteration of table name
//            Map<String,DBRecord>maxVersionMap;
//            while(keyIterator.hasNext()){
//                String l_tableName=(String)keyIterator.next();
//                maxVersionMap=  versionFilter(l_fileMap.get(l_tableName),p_fileType,l_tableName,p_session,p_dbSession);
//                l_fileMap.replace(l_tableName, maxVersionMap);
//            }
//            setFileToBuffer( l_fileNameKey,l_fileMap,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//             dbg("inside dbReadBufferService--->readTable--->physical read map contains table name ",p_session);
//                   l_tableMap=  l_fileMap.get(p_tableName);
//            }else{
//             dbg("inside dbReadBufferService--->readTable--->physical read map doesn't contains table name ",p_session);
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }
        
        
        
        
       dbg("end of dbReadBufferService--->readTable",p_session); 
      } catch(DBValidationException ex){
           throw ex;
      }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
      }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
      }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            filterkey_dummy=null;
//            maxversion_dummy=0;
//            filtermap_dummy=null;
//            }
        
        }
      return l_tableMap;
  }
  public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession p_session,DBSession p_dbSession,boolean maxVersionRequired)throws  DBProcessingException,DBValidationException{
//      boolean l_session_created_now=false;
      Map<String,DBRecord> l_tableMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler=p_session.getErrorhandler();
        dbg("inside dbReadBufferService--->readTable",p_session);
        String   l_fileNameKey=fileName;
//        String l_fileNameKey=fileName+p_fileType;
//        l_tableMap=new HashMap();
        Map<String, Map<String,DBRecord>>l_fileMap=readFullFile(fileName,p_fileType,p_session,p_dbSession,maxVersionRequired);
        
        l_tableMap=l_fileMap.get(p_tableName);
        
        if(l_tableMap==null){
            StringBuffer single_error_code = new StringBuffer();
                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
                errorhandler.setSingle_err_code(single_error_code);
                errorhandler.log_error();
               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
        }
//        if(readBufferMap.containsKey(l_fileNameKey)){
//        dbg("inside dbReadBufferService--->readTable--->buffer map contains file name",p_session);
//            l_fileMap=getFileFromBuffer(l_fileNameKey,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//             dbg("inside dbReadBufferService--->readTable--->buffer map contains file name and table name",p_session);
//                   l_tableMap=  l_fileMap.get(p_tableName);
//            }else{
//             dbg("inside dbReadBufferService--->readTable--->buffer map contains file name and does not contain tablename",p_session);
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }else{
//         dbg("inside dbReadBufferService--->readTable--->buffer map not contains file name ",p_session);
//            l_fileMap=dbPhysicalRead(l_fileNameKey,p_fileType,p_session,p_dbSession);
//            Iterator keyIterator=l_fileMap.keySet().iterator();//iteration of table name
//            Map<String,DBRecord>maxVersionMap;
//            while(keyIterator.hasNext()){
//                String l_tableName=(String)keyIterator.next();
//                maxVersionMap=  versionFilter(l_fileMap.get(l_tableName),p_fileType,l_tableName,p_session,p_dbSession);
//                l_fileMap.replace(l_tableName, maxVersionMap);
//            }
//            setFileToBuffer( l_fileNameKey,l_fileMap,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//             dbg("inside dbReadBufferService--->readTable--->physical read map contains table name ",p_session);
//                   l_tableMap=  l_fileMap.get(p_tableName);
//            }else{
//             dbg("inside dbReadBufferService--->readTable--->physical read map doesn't contains table name ",p_session);
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }
        
        
        
        
       dbg("end of dbReadBufferService--->readTable",p_session); 
      } catch(DBValidationException ex){
           throw ex;
      }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
      }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
      }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            filterkey_dummy=null;
//            maxversion_dummy=0;
//            filtermap_dummy=null;
//            }
        
        }
      return l_tableMap;
  }
  public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession p_session,DBSession p_dbSession,String notAppendError)throws  DBProcessingException,DBValidationException{
      Map<String,DBRecord> l_tableMap;
      try{
        dbg("inside dbReadBufferService--->readTable-->notAppendError",p_session);
        Map<String, Map<String,DBRecord>>l_fileMap=readFullFile(fileName,p_fileType,p_session,p_dbSession);
        
        l_tableMap=l_fileMap.get(p_tableName);
        
        if(l_tableMap==null){
            throw new DBValidationException("DB_VAL_011"); 
        }

        
       dbg("end of dbReadBufferService--->readTable-->notAppendError",p_session); 
      } catch(DBValidationException ex){
           throw ex;
      }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
      }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
      }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            filterkey_dummy=null;
//            maxversion_dummy=0;
//            filtermap_dummy=null;
//            }
        
        }
      return l_tableMap;
  }
// @Lock(LockType.WRITE)
//  public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession session)throws  DBProcessingException,DBValidationException{
//      CohesiveSession tempSession = this.session;
//      try{
//           this.session=session;
//          return  readTable(fileName,p_fileType,p_tableName);
//          
//     } catch(DBValidationException ex){
//           throw ex;
//     }catch(DBProcessingException ex){
//        dbg(ex);
//        throw new DBProcessingException("DBProcessingException" + ex.toString());
//     }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
//           this.session=tempSession;
//     }
//      
//      
//      
//  }
//  @Lock(LockType.WRITE)
  public DBRecord readRecord(String fileName,String p_fileType,String p_tableName,String[] p_pkey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException,DBValidationException{
//      boolean l_session_created_now=false;
       DBRecord dbRec=null;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler=p_session.getErrorhandler();
        String   l_fileNameKey=fileName;
        String l_primaryKey=p_dbSession.getIibd_file_util().formingPrimaryKey(p_pkey);
//        Map<String, Map<String,DBRecord>>l_fileMap;
        dbg("inside dbReadBufferService--->readRecord--->fileName"+fileName,p_session);
        dbg("p_tableName"+p_tableName,p_session);
        for(String s:p_pkey){
            dbg("p_pkey"+s,p_session);
        }
        
       dbRec= readTable(fileName,p_fileType,p_tableName,p_session,p_dbSession).get(l_primaryKey);
       
       if(dbRec==null){
           dbg("inside dbReadBufferService--->readRecord--->dbRec is null",p_session);
           StringBuffer single_error_code = new StringBuffer();
                String replacedPkey=l_primaryKey.replace("~", "@");
                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName).concat("," +replacedPkey ));
                errorhandler.setSingle_err_code(single_error_code);
                errorhandler.log_error();
               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
           
       }
        
//        if(readBufferMap.containsKey(l_fileNameKey)){
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name",p_session);
//            l_fileMap=getFileFromBuffer(l_fileNameKey,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name and table name ",p_session);
//                 if(l_fileMap.get(p_tableName).containsKey(l_primaryKey)){
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name and table name nad primary key ",p_session);
//                  return  l_fileMap.get(p_tableName).get( l_primaryKey);
//               }else{
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name and table name doesn't contains primary key ",p_session);
//                StringBuffer single_error_code = new StringBuffer();
//                String replacedPkey=l_primaryKey.replace("~", "@");
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName).concat("," + replacedPkey));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//          }else{
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }else{
//            dbg("inside dbReadBufferService--->readRecord--->buffer map does not contains file name",p_session);
//           l_fileMap=dbPhysicalRead(l_fileNameKey,p_fileType,p_session,p_dbSession);
//           Iterator keyIterator=l_fileMap.keySet().iterator();//iteration of table name
//            Map<String,DBRecord>maxVersionMap;
//            while(keyIterator.hasNext()){
//                String l_tableName=(String)keyIterator.next();
//                maxVersionMap=  versionFilter(l_fileMap.get(l_tableName),p_fileType,l_tableName,p_session,p_dbSession);
//                l_fileMap.replace(l_tableName, maxVersionMap);
//            }
//            setFileToBuffer( l_fileNameKey,l_fileMap,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//                 dbg("table name exist in the file map",p_session);
//                  if(l_fileMap.get(p_tableName).containsKey(l_primaryKey)){
//                     
//                  return  l_fileMap.get(p_tableName).get( l_primaryKey);
//               }else{
//                StringBuffer single_error_code = new StringBuffer();
//                
//                String replacedPkey=l_primaryKey.replace("~", "@");
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName).concat("," +replacedPkey ));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//            }else{
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }
        
        
        
        
        
      } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
////            dbSession.clearSessionObject();
//            filterkey_dummy=null;
//            maxversion_dummy=0;
//            filtermap_dummy=null;
//            }
        }
      return dbRec;
  }
  
  public DBRecord readRecord(String fileName,String p_fileType,String p_tableName,String[] p_pkey,CohesiveSession p_session,DBSession p_dbSession,boolean p_notAppendError)throws  DBProcessingException,DBValidationException{
//      boolean l_session_created_now=false;
       DBRecord dbRec=null;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler=p_session.getErrorhandler();
        String   l_fileNameKey=fileName;
        String l_primaryKey=p_dbSession.getIibd_file_util().formingPrimaryKey(p_pkey);
//        Map<String, Map<String,DBRecord>>l_fileMap;
        dbg("inside dbReadBufferService--->readRecord--->notAppendError--->fileName"+fileName,p_session);
        dbg("p_tableName"+p_tableName,p_session);
        for(String s:p_pkey){
            dbg("p_pkey"+s,p_session);
        }
        
       dbRec= readTable(fileName,p_fileType,p_tableName,p_session,p_dbSession,"True").get(l_primaryKey);
       
       if(dbRec==null){
           dbg("inside dbReadBufferService--->readRecord--->dbRec is null",p_session);
//           StringBuffer single_error_code = new StringBuffer();
//                String replacedPkey=l_primaryKey.replace("~", "@");
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName).concat("," +replacedPkey ));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
             if(p_notAppendError==true)
               throw new DBValidationException("DB_VAL_011"); 
           
       }
        
//        if(readBufferMap.containsKey(l_fileNameKey)){
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name",p_session);
//            l_fileMap=getFileFromBuffer(l_fileNameKey,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name and table name ",p_session);
//                 if(l_fileMap.get(p_tableName).containsKey(l_primaryKey)){
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name and table name nad primary key ",p_session);
//                  return  l_fileMap.get(p_tableName).get( l_primaryKey);
//               }else{
//        dbg("inside dbReadBufferService--->readRecord--->buffer map contains file name and table name doesn't contains primary key ",p_session);
//                StringBuffer single_error_code = new StringBuffer();
//                String replacedPkey=l_primaryKey.replace("~", "@");
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName).concat("," + replacedPkey));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//          }else{
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }else{
//            dbg("inside dbReadBufferService--->readRecord--->buffer map does not contains file name",p_session);
//           l_fileMap=dbPhysicalRead(l_fileNameKey,p_fileType,p_session,p_dbSession);
//           Iterator keyIterator=l_fileMap.keySet().iterator();//iteration of table name
//            Map<String,DBRecord>maxVersionMap;
//            while(keyIterator.hasNext()){
//                String l_tableName=(String)keyIterator.next();
//                maxVersionMap=  versionFilter(l_fileMap.get(l_tableName),p_fileType,l_tableName,p_session,p_dbSession);
//                l_fileMap.replace(l_tableName, maxVersionMap);
//            }
//            setFileToBuffer( l_fileNameKey,l_fileMap,p_session,p_dbSession);
//            if(l_fileMap.containsKey(p_tableName)){
//                 dbg("table name exist in the file map",p_session);
//                  if(l_fileMap.get(p_tableName).containsKey(l_primaryKey)){
//                     
//                  return  l_fileMap.get(p_tableName).get( l_primaryKey);
//               }else{
//                StringBuffer single_error_code = new StringBuffer();
//                
//                String replacedPkey=l_primaryKey.replace("~", "@");
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName).concat("," +replacedPkey ));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//            }else{
//                StringBuffer single_error_code = new StringBuffer();
//                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_tableName));
//                errorhandler.setSingle_err_code(single_error_code);
//                errorhandler.log_error();
//               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//            }
//        }
        
        
        
        
        
      } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
////            dbSession.clearSessionObject();
//            filterkey_dummy=null;
//            maxversion_dummy=0;
//            filtermap_dummy=null;
//            }
        }
      return dbRec;
  }
//  @Lock(LockType.WRITE)
//  public DBRecord readRecord(String fileName,String p_fileType,String p_tableName,String[] p_pkey,CohesiveSession session)throws  DBProcessingException,DBValidationException{
//      CohesiveSession tempSession = this.session;
//      try{
//           this.session=session;
//          return  readRecord(fileName,p_fileType,p_tableName,p_pkey);
//          
//     } catch(DBValidationException ex){
//           throw ex;
//     }catch(DBProcessingException ex){
//        dbg(ex);
//        throw new DBProcessingException("DBProcessingException" + ex.toString());
//     }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
//           this.session=tempSession;
//     }
//      
//      
//      
//  }
  
  private Map<String, Map<String,DBRecord>> dbPhysicalRead(String p_FileName, String p_FileType,CohesiveSession p_session,DBSession p_dbSession) throws DBValidationException, DBProcessingException {
        Map<String, Map<String,DBRecord>>l_fileMap;
        Scanner l_File_Content = null;
        //boolean l_session_created_now=false;
        
        //session.createSessionObject();
        //dbSession.createDBsession(session);
        //l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler = p_session.getErrorhandler();
        DBValidation dbv = p_dbSession.getDbv();
        boolean l_validation_status = true;
        dbg("inside dbreadbuffer--->dbPhySicalRead",p_session);
        dbg("DBReadBuffer--->dbPhysicalRead--->fileName--->p_FileName",p_session);
        
        try {
            dbg("inside try",p_session);
//            if (fileExistence(p_FileName)) {
//                dbg("inside if file existence");
                if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                    dbg("validation status is false",p_session);
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }

                if (!l_validation_status) {
                    throw new DBValidationException(errorhandler.getSession_error_code().toString());

                }
                
                l_fileMap=new HashMap();
                 try{
                    l_fileMap = p_dbSession.getIibd_file_util().sequentialRead(p_FileName,p_session,dbdi);
                    
                }catch(FileNotFoundException ex){
                    StringBuffer single_error_code = new StringBuffer();
                    single_error_code = single_error_code.append("DB_VAL_000");
                    errorhandler.setSingle_err_code(single_error_code);
                    errorhandler.log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }
                
                dbg("printing of physical read map starts",p_session);  
                Map<String, Map<String,DBRecord>>l_fileMapp= l_fileMap;
                 
                 Iterator<String> tableNameIterator=l_fileMapp.keySet().iterator();
                   while(tableNameIterator.hasNext()) {
                       String tableName=tableNameIterator.next();
                       dbg("    tableName"+tableName,p_session);
                       Iterator<String> pkeyIterator=l_fileMapp.get(tableName).keySet().iterator();
                          while(pkeyIterator.hasNext()){
                              String primaryKey=pkeyIterator.next();
                              dbg("      primaryKey"+primaryKey,p_session);
                               DBRecord dbrec=  l_fileMapp.get(tableName).get(primaryKey);
                               dbg("        operation"+dbrec.getOperation(),p_session);
                               dbg("        postion"+dbrec.getPosition(),p_session);
                               for(String s: dbrec.getRecord()){
                                   dbg("    record values"+s,p_session);
                               }
                               
                          }
                   }
                
                dbg("printing of physical read map ends",p_session);
                
//            }
//            else{
//                throw new DBValidationException("DB-VAL-000");
//            }
            dbg("end of dbPhysical read",p_session);
                                return l_fileMap;

        } catch (PatternSyntaxException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("ClassCastException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IllegalStateException" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
        } /*catch (FileNotFoundException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        }
        } */ 
        catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            if (l_File_Content != null) {
                l_File_Content.close();
            }
           
        }

    }
  private boolean fileExistence(String p_file_name,CohesiveSession p_session) throws DBProcessingException {
        boolean l_existence = false;
        try {

            IDBCoreService dbcs = dbdi.getDbcoreservice(); //EJB Integration change

            //dbg("In DBReadservice ->file existence"+Files.exists(Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH")+p_file_name+dbcs.getI_db_properties().getProperty("FILE_EXTENSION")))); 
            l_existence = Files.exists(Paths.get(p_session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + p_file_name + p_session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
        } catch (SecurityException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("SecurityException" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("InvalidPathException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        return l_existence;
    }
//  @Lock(LockType.WRITE)
  public synchronized Map<String, Map<String,DBRecord>>getFileFromBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException{
//     boolean l_session_created_now=false;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBRead Buffer---->getFileMap ",p_session);
        dbg("inside DBRead Buffer---->getFileMap--->fileNameKey "+fileNameKey,p_session);
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        Map<String, Map<String,DBRecord>>l_clonedFileMap;
//        int capacity=Integer.parseInt(session.getCohesiveproperties().getProperty("CAPACITY"));
        if(readBufferMap.containsKey(fileNameKey)){
            DBFileContent fileContent = readBufferMap.get(fileNameKey);
            readFromWriteBuffer(fileContent.fileMap,fileNameKey,p_session,p_dbSession);
            l_clonedFileMap=  fileUtil.cloneFile(fileContent.fileMap);
            
            remove(fileContent,p_session);
            setHead(fileContent,p_session);
            
            
            
            dbg("end of DBRead Buffer---->getFileMap",p_session);
            return l_clonedFileMap;
//            return fileContent.fileMap;
        }
        dbg("end of DBRead Buffer---->getFileMap",p_session);
        return null;  
//        } catch(DBValidationException ex){
//           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    }
  }
  public synchronized void removeFileFromBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException{
      try{
        dbg("inside DBRead Buffer---->getFileMap ",p_session);
        dbg("inside DBRead Buffer---->getFileMap--->fileNameKey "+fileNameKey,p_session);
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        Map<String, Map<String,DBRecord>>l_clonedFileMap;
//        int capacity=Integer.parseInt(session.getCohesiveproperties().getProperty("CAPACITY"));
        if(readBufferMap.containsKey(fileNameKey)){
            DBFileContent fileContent = readBufferMap.get(fileNameKey);
           // l_clonedFileMap=  fileUtil.cloneFile(fileContent.fileMap);
            remove(fileContent,p_session);
           // setHead(fileContent,p_session);
            
            
            
            dbg("end of DBRead Buffer---->getFileMap",p_session);
           // return l_clonedFileMap;
        }
        dbg("end of DBRead Buffer---->getFileMap",p_session);
      //  return null;  
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    }
  }
//  public  DBRecord getRecordFromBuffer(String fileNameKey,String tableName,String pkey,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException{
//      try{
//        dbg("inside DBRead Buffer---->getFileMap ",p_session);
//        dbg("inside DBRead Buffer---->getFileMap--->getRecordFromBuffer "+fileNameKey,p_session);
//        dbg("inside DBRead Buffer---->getFileMap--->getRecordFromBuffer "+tableName,p_session);
//        dbg("inside DBRead Buffer---->getFileMap--->getRecordFromBuffer "+pkey,p_session);
//        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
//        if(readBufferMap.containsKey(fileNameKey)){
//            
//            if(readBufferMap.get(fileNameKey))
//            
//            
//            dbg("end of DBRead Buffer---->getFileMap",p_session);
//            return l_clonedFileMap;
////            return fileContent.fileMap;
//        }
//        dbg("end of DBRead Buffer---->getFileMap",p_session);
//        return null;  
////        } catch(DBValidationException ex){
////           throw ex;
//     }catch(DBProcessingException ex){
//        dbg(ex,p_session);
//        throw new DBProcessingException("DBProcessingException" + ex.toString());
//     }catch (Exception ex) {
//          dbg(ex,p_session);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
////        if(l_session_created_now)
////            {    
////            session.clearSessionObject();
////            dbSession.clearSessionObject();
////            
////            }
////        }
//    }
//  }
  
  
  
  
  
  private synchronized ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>getFileFromBufferWithoutClone(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException{
//     boolean l_session_created_now=false;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBRead Buffer---->getFileMap ",p_session);
        dbg("inside DBRead Buffer---->getFileMap--->fileNameKey "+fileNameKey,p_session);
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
//        Map<String, Map<String,DBRecord>>l_clonedFileMap;
//        int capacity=Integer.parseInt(session.getCohesiveproperties().getProperty("CAPACITY"));
        if(readBufferMap.containsKey(fileNameKey)){
            DBFileContent fileContent = readBufferMap.get(fileNameKey);
//            l_clonedFileMap=  fileUtil.cloneFile(fileContent.fileMap);
            remove(fileContent,p_session);
            setHead(fileContent,p_session);
            
            
            
            dbg("end of DBRead Buffer---->getFileMap",p_session);
//            return l_clonedFileMap;
            return fileContent.fileMap;
        }
        dbg("end of DBRead Buffer---->getFileMap",p_session);
        return null;  
//        } catch(DBValidationException ex){
//           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    }
  }
//  @Lock(LockType.WRITE)
//  public Map<String, Map<String,DBRecord>>getFileFromBuffer(String fileNameKey,CohesiveSession session)throws DBValidationException, DBProcessingException{
//      CohesiveSession tempSession = this.session;
//      try{
//           this.session=session;
//          return  getFileFromBuffer(fileNameKey);
//          
//     } catch(DBValidationException ex){
//           throw ex;
//     }catch(DBProcessingException ex){
//        dbg(ex);
//        throw new DBProcessingException("DBProcessingException" + ex.toString());
//     }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
//           this.session=tempSession;
//     }
//      
//  }
  private synchronized void setFileToBuffer(String fileNameKey,Map<String, Map<String,DBRecord>> fileMap,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException,DBValidationException{
//     boolean l_session_created_now=false;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBRead Buffer---->setFileMap ",p_session);
        dbg("inside DBRead Buffer---->setFileMap--->fileNameKey "+fileNameKey,p_session);
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        int capacity=Integer.parseInt(p_session.getCohesiveproperties().getProperty("DB_READ_BUFFER_CAPACITY"));
        if(readBufferMap.containsKey(fileNameKey)){
            DBFileContent fileContent = readBufferMap.get(fileNameKey);
            fileContent.fileMap=fileUtil.createConcurrentFileMap(fileMap,fileNameKey,p_session,"Read");
//            fileContent.fileMap=fileMap;
            remove(fileContent,p_session);
            setHead(fileContent,p_session);
        }else{
//            DBFileContent fileContent=new DBFileContent(fileNameKey,fileMap);
            DBFileContent fileContent=new DBFileContent(fileNameKey,fileUtil.createConcurrentFileMap(fileMap,fileNameKey,p_session,"Read"));
            if(readBufferMap.size()>=capacity){
                readBufferMap.remove(end.fileNameKey);
                remove(end,p_session);
                setHead(fileContent,p_session);
            }else{
                setHead(fileContent,p_session);
            }
          readBufferMap.putIfAbsent(fileNameKey, fileContent);
            
        }
        dbg("end of DBRead Buffer---->setFileMap",p_session);
//      } catch(DBValidationException ex){
//           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }finally{
//      if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
      }
  }
//  private void setFileToBuffer(String fileNameKey,Map<String, Map<String,DBRecord>> fileMap,CohesiveSession session)throws  DBProcessingException,DBValidationException{
//      CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        setFileToBuffer(fileNameKey,fileMap);
//     } catch(DBValidationException ex){
//           throw ex;
//     }catch(DBProcessingException ex){
//        dbg(ex);
//        throw new DBProcessingException("DBProcessingException" + ex.toString());
//     }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
//           this.session=tempSession;
//     }
//  }
  @PreDestroy
    public void destroyBuffer() {
        readBufferMap = null;
        //dbf = null;
    }
  
  private  void remove(DBFileContent fileContent,CohesiveSession p_session)throws DBProcessingException{
      
      try{
          
        if(fileContent.pre!=null){
            fileContent.pre.next = fileContent.next;
        }else{
            head = fileContent.next;
        }
 
        if(fileContent.next!=null){
            fileContent.next.pre = fileContent.pre;
        }else{
            end = fileContent.pre;
        }
          
      }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
      
  }
  private void setHead(DBFileContent fileContent,CohesiveSession p_session)throws DBProcessingException{
      
      try{
        fileContent.next = head;
        fileContent.pre = null;
 
        if(head!=null)
            head.pre = fileContent;
 
        head = fileContent;
 
        if(end ==null)
            end = head;
        
          
      }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
      
  }
  private Map<String,DBRecord>versionFilter(Map<String,DBRecord>p_tableMap,String p_fileType,String p_tableName,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException{
    Map<String,String>l_tableVersion=new HashMap();
    ArrayList<String>l_filerList=new ArrayList();
    
    try{
        dbg("inside DBReadBuffer service--->versionFilter",p_session);
        dbg("inside DBReadBuffer service--->versionFilter--->p_tableMap.size"+p_tableMap.size(),p_session);
        dbg("inside DBReadBuffer service--->versionFilter--->p_fileType"+p_fileType,p_session);
        dbg("inside DBReadBuffer service--->versionFilter--->tableName"+p_tableName,p_session);
        IMetaDataService mds =dbdi.getMetadataservice();
        Map<String,DBRecord>filtermap_dummy=new HashMap();
        
        
//        IDBReadService dbrs=dbdi.getDbreadservice();

//         Iterator<String> iterator2=p_tableMap.keySet().iterator();

//         while(iterator2.hasNext()){
//             String key=iterator2.next();
//             dbg("key"+key,p_session);
//             ArrayList<String>value=p_tableMap.get(key).getRecord();
//             for(int j=0;j<value.size();j++){
//                    
//                    dbg("values"+value.get(j).trim(),p_session);
//                }
//         }




        Iterator<String> iterator1=p_tableMap.keySet().iterator();
        
        
//        String filterkey=null;
//        StringBuffer l_filteredBuffer=new StringBuffer();
        while(iterator1.hasNext()){
          String l_key=iterator1.next();
          dbg("version filter key"+l_key,p_session);
          String[] l_tablePKey=l_key.split("~");
//          String l_tableID=l_tableIDAndPKey[0];
          String l_versionID;
          boolean l_versionStatus=false;
         
          if(!(l_tableVersion.containsKey(p_tableName))){
          
          Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(p_fileType,p_tableName, p_session).getI_ColumnCollection();
          Iterator columnIterator=l_columnCollection.values().iterator();
          while(columnIterator.hasNext()){
             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
             if(l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")){
                 l_versionStatus=true;
                 dbg("version status is true for the table"+p_tableName,p_session);
             }else{
                 l_versionStatus=false;
                 dbg("version status is false for the table"+p_tableName,p_session);
             }
             if(l_versionStatus==true)
                 break;
         }
          dbg("column iteration completed",p_session);
         if(l_versionStatus){
//             String l_tableName=mds.getTableMetaData(Integer.parseInt(l_tableID), session).getI_TableName();
             l_versionID=Integer.toString(mds.getColumnMetaData(p_fileType, p_tableName, "VERSION_NUMBER",p_session).getI_ColumnID());
             l_tableVersion.put(p_tableName, l_versionID);
         }else{
             l_tableVersion.put(p_tableName, "0");
         }
          }
//          try{
//            String l_tableName=mds.getTableMetaData(Integer.parseInt(l_tableID), dbdi).getI_TableName();
//             dbg("versionFilter--->tableID"+l_tableID);
//             dbg("versionFilter--->tableName"+l_tableName);
//             dbg("versionFilter--->filetype"+p_fileType);
//            l_versionID=Integer.toString(mds.getColumnMetaData(p_fileType, l_tableName, "VERSION_NUMBER",dbdi).getI_ColumnID());
//            l_tableVersion.put(l_tableID, l_versionID);
//           }catch(DBValidationException ex){
//               dbg("exception in versionfilter"+ex.toString());
//              if(ex.toString().contains("DB_VAL_005")){
//                  l_versionID="0";
//                  l_tableVersion.put(l_tableID, l_versionID);
//              }else{
//                  dbg("exception in versionfilter"+ex.toString());
//                  throw ex;
//              }
//           }
        //Finding the index of version number column and removing the version number from the primary key
        
         if(!(l_tableVersion.get(p_tableName).equals("0"))){
                String filterkey_dummy;
                 int maxversion_dummy;
                StringBuffer l_filteredBuffer=new StringBuffer();
                String[] l_pkey=mds.getTableMetaData(p_fileType, p_tableName, p_session).getI_Pkey().split("~");
                int l_versionIndex=Arrays.asList(l_pkey).indexOf(l_tableVersion.get(p_tableName));
                for(int i=0;i<l_tablePKey.length;i++){
                    if(i!=l_versionIndex){
//                    if(i!=l_versionIndex+1){
                            l_filteredBuffer.append(l_tablePKey[i]).append("~");
                        }                       
                }
                
           if( l_filteredBuffer.charAt(l_filteredBuffer.length()-1)=='~'){
               filterkey_dummy=l_filteredBuffer.substring(0, l_filteredBuffer.length()-1);
           }else{
              filterkey_dummy=l_filteredBuffer.toString();
           }
           dbg("filter string"+filterkey_dummy,p_session);
           if(!(l_filerList.contains(filterkey_dummy))){
            l_filerList.add(filterkey_dummy); 
             String l_filterKey=filterkey_dummy;
             
             Stream<String> l_filteredStream=p_tableMap.keySet().stream().filter(rec->rec.trim().substring(0,rec.lastIndexOf("~")).equals(filterkey_dummy));
//           Stream<String> l_filteredStream=p_tableMap.keySet().stream().filter(rec->rec.trim().contains(filterkey_dummy));
//           l_filteredStream.forEach(s->dbg("Stream values"+s));
           
           maxversion_dummy=l_filteredStream.mapToInt(rec->Integer.parseInt(rec.trim().substring(rec.lastIndexOf("~")+1,rec.length()))).max().getAsInt();
           dbg("filter map--->maxversion_dummy"+maxversion_dummy,p_session);
//           l_filteredBuffer=null;
//           l_filteredBuffer=new StringBuffer();
//           for(int i=0;i<l_tableIDAndPKey.length;i++){
//                        if(i==l_tableIDAndPKey.length-1){
//                            l_filteredBuffer.append(l_tableIDAndPKey[i]);
//                        }else{
//                            l_filteredBuffer.append(l_tableIDAndPKey[i]).append("~");
//                        }
//                       
//                }
//            l_filteredStream=p_tableMap.keySet().stream().filter(rec->rec.trim().substring(0,rec.lastIndexOf("~")).equals(filterkey_dummy));
//            l_filteredStream.forEach(rec->dbg("records in filterd stream"+rec,p_session));
//            
//            l_filteredStream=p_tableMap.keySet().stream().filter(rec->rec.trim().substring(0,rec.lastIndexOf("~")).equals(filterkey_dummy));
//            
//            l_filteredStream.filter(rec->Integer.parseInt(rec.trim().substring(rec.lastIndexOf("~")+1,rec.length()))==maxversion_dummy)
//                                      .forEach(rec->dbg("records in filterd stream after max version"+rec,p_session));


//           filterkey_dummy=l_filteredBuffer.toString();
//           filtermap_dummy.put(l_filterKey, p_fileMap.get(filterkey_dummy));
//           l_filteredStream.filter(rec->Integer.parseInt(rec.trim().substring(.forEach(rec->filtermap_dummy.put(rec, p_fileMap.get(rec)));
             dbg("re initialization of l_filteredStream",p_session);
              l_filteredStream=p_tableMap.keySet().stream().filter(rec->rec.trim().substring(0,rec.lastIndexOf("~")).equals(filterkey_dummy));
              
//             l_filteredStream=p_tableMap.keySet().stream().filter(rec->rec.trim().contains(filterkey_dummy));
              int i=0;
              l_filteredStream.filter(rec->Integer.parseInt(rec.trim().substring(rec.lastIndexOf("~")+1,rec.length()))==maxversion_dummy)
                   .forEach(rec->filtermap_dummy.put(filterkey_dummy, p_tableMap.get(rec)));
//           if(filterkey_dummy.equals("S008")){
//               dbg("filterkey_dummy is equal to the S008");
//               if(filtermap_dummy.containsKey("S001~Term I")){
//                   
//                   ArrayList<String>l_valueList=filtermap_dummy.get("S001~Term I").getRecord();
//                   
//                   for(String s:l_valueList){
//                       dbg("value for Term1"+s,p_session);
//                   }
//               }
//           }
           
           }
         }else{
             filtermap_dummy.put(l_key, p_tableMap.get(l_key));
           
         
        }
        }
         filtermap_dummy.forEach((String k,DBRecord v)->{
              dbg("filtered map key"+k,p_session);
              v.record.forEach((String l)->{
                  dbg("filtered map value"+l,p_session);
              });
           });
         dbg("end of pdataservice--->version filter",p_session);
    return filtermap_dummy;
        }catch (DBValidationException ex) {
            throw ex;  
        }catch (DBProcessingException ex) {
            dbg(ex,p_session);
            throw ex;     
        }catch (Exception ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
} 
  
 private static void insertRecordToMap(String key,DBRecord dbRec){
     
     
     
 } 
  
  
  
  
//@Lock(LockType.WRITE)  
public synchronized void modifyBufferFromTempSegment(String p_fileName,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
    
//        boolean l_session_created_now=false; 
     try{
//        dbSession.createDBsession(session);
//        l_session_created_ now=session.isI_session_created_now();
        dbg("inside DBReadBufferService--->modifyBufferFromTempSegment",p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        
         Map<String, Map<String,DBRecord>>l_readBuffer_fileMap=getFileFromBuffer(p_fileName,p_session,p_dbSession);
           if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_readBuffer_fileMap contains the file name",p_session);
//          Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
          ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
            if(l_tempSegFileMap!=null){   
           dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->temp segment filemap contains the file name",p_session);  
           Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
               while(tempSegTableIterator.hasNext()){
                 String l_tableName=tempSegTableIterator.next();
           dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->tableName"+l_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(l_tableName)){
               dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_readBuffer_fileMap contains the table name",p_session);  
                      Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      while(tempSegRecordIterator.hasNext()){
                          String l_tempSegRecPKey=tempSegRecordIterator.next();
                          dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+l_tempSegRecPKey,p_session);
                           DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                          if(l_readBuffer_fileMap.get(l_tableName).containsKey(l_tempSegRecPKey)){
                  dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->operation"+l_tempSegDBRecord.getOperation(),p_session);                                   
                              if(l_tempSegDBRecord.getOperation()=='D'){
                                   l_readBuffer_fileMap.get(l_tableName).remove(l_tempSegRecPKey);
                              }else if(l_tempSegDBRecord.getOperation()=='U'){
                                   l_readBuffer_fileMap.get(l_tableName).replace(l_tempSegRecPKey,l_tempSegDBRecord);
                              }  
                          }else if(l_tempSegDBRecord.getOperation()=='C'){
                              l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempSegRecPKey, l_tempSegDBRecord);
                              
                          }
                          
                      }
                    }else {
                       l_readBuffer_fileMap.putIfAbsent(l_tableName, new HashMap());
                        Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                      
                      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      while(tempSegRecordIterator.hasNext()){
                       String l_tempSegRecPKey=tempSegRecordIterator.next();
                       DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                       if(l_tempSegDBRecord.getOperation()!='D')
                       l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempSegRecPKey, l_tempSegDBRecord);
                      }
                   }
                   
               }
            }
        
           }
        
        
        
     } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    
     }
}
  
//@Lock(LockType.WRITE)
//  public void modifyBufferFromTempSegment(String p_fileName,CohesiveSession session)throws DBProcessingException{
//      CohesiveSession tempSession = this.session;
//      try{
//           this.session=session;
//            modifyBufferFromTempSegment(p_fileName);
//          
//     }catch (Exception ex){
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
//           this.session=tempSession;
//     }
//      
//  }

public synchronized void SetRecordfromTempseg(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
    
//        boolean l_session_created_now=false; 
     try{
//        dbSession.createDBsession(session);
//        l_session_created_ now=session.isI_session_created_now();
        dbg("inside DBReadBufferService--->SetRecordfromTempseg",p_session);
        dbg("inside DBReadBufferService--->SetRecordfromTempseg--->fileName"+p_fileName,p_session);
        dbg("inside DBReadBufferService--->SetRecordfromTempseg--->p_tableName"+p_tableName,p_session);
        dbg("inside DBReadBufferService--->SetRecordfromTempseg--->pKey"+p_tableName,p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_filetype=fileUtil.getFileType(p_fileName);
//         Map<String, Map<String,DBRecord>>l_readBuffer_fileMap;
           ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_readBuffer_fileMap;
//         synchronized(lock1){
       
             l_readBuffer_fileMap=getFileFromBufferWithoutClone(p_fileName,p_session,p_dbSession);
             
//          l_readBuffer_fileMap=getFileFromBuffer(p_fileName,p_session,p_dbSession);
//         }
         if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->SetRecordfromTempseg--->l_readBuffer_fileMap contains the file name",p_session);
          //Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
         DBRecord l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
          if(l_tempSegRec!=null){   
           dbg("inside DBReadBufferService--->SetRecordfromTempseg--->l_tempSegRec not null",p_session);  
          // Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
            //   while(tempSegTableIterator.hasNext()){
              //   String l_tableName=tempSegTableIterator.next();
           dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->tableName"+p_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(p_tableName)){
               dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_readBuffer_fileMap contains the table name",p_session);  
                     // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                      //Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      //while(tempSegRecordIterator.hasNext()){
                        //  String l_tempSegRecPKey=tempSegRecordIterator.next();
                          dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+pKey,p_session);
                          // DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                          if(l_readBuffer_fileMap.get(p_tableName).containsKey(pKey)){
                  dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->operation"+l_tempSegRec.getOperation(),p_session);                                   
                             
                              if(l_tempSegRec.getOperation()=='D'){
                                   l_readBuffer_fileMap.get(p_tableName).remove(pKey);
                              }else if(l_tempSegRec.getOperation()=='U'){
                                   l_readBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
                              }
                              else if(l_tempSegRec.getOperation()=='C'){
                             //check version     
                               if(fileUtil.checkVersionAvailability(l_filetype, p_tableName, p_session, dbdi)){
                                   
                                   int existingVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_readBuffer_fileMap.get(p_tableName).get(pKey).getRecord(), p_session, dbdi);
                                   int newVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
                                   dbg("new version number"+newVersionNumber,p_session);
                                   dbg("existing version number"+existingVersionNumber,p_session);
                                   if(newVersionNumber==existingVersionNumber+1){
                                       dbg("newVersionNumber==existingVersionNumber+1",p_session);
                                        l_readBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
                                       
                                   }else{
                                       StringBuffer single_error_code = new StringBuffer();
                                       single_error_code = single_error_code.append("DB_VAL_009");
                                       p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                       p_session.getErrorhandler().log_error();
                                       throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                   }
                               }else{
                             
                               StringBuffer single_error_code = new StringBuffer();
                               single_error_code = single_error_code.append("DB_VAL_009");
                               p_session.getErrorhandler().setSingle_err_code(single_error_code);
                               p_session.getErrorhandler().log_error();
                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                               }
                              }
                          
                          }  
                          else if(l_tempSegRec.getOperation()!='D'){
                              l_readBuffer_fileMap.get(p_tableName).putIfAbsent(pKey, l_tempSegRec);
                          } 
//                              else {
//                             StringBuffer single_error_code = new StringBuffer();
//                               String replacedPkey=pKey.replace("~", "@");
//                           single_error_code = single_error_code.append("DB_VAL_018").append(","+p_fileName).append(","+p_tableName).append(","+replacedPkey);
//                           p_session.getErrorhandler().setSingle_err_code(single_error_code);
//                           p_session.getErrorhandler().log_error();
//                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
//                          
//                          
//                          }
                          
                      }
                 
                    else {
//                       l_readBuffer_fileMap.putIfAbsent(p_tableName, new HashMap());
                       l_readBuffer_fileMap.putIfAbsent(p_tableName, fileUtil.createConcurrentRecordMap(p_session));
                       // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(p_tableName);
                      
                     // Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                     // while(tempSegRecordIterator.hasNext()){
                       //String l_tempSegRecPKey=tempSegRecordIterator.next();
                       //DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                       if(l_tempSegRec.getOperation()!='D')
                       l_readBuffer_fileMap.get(p_tableName).putIfAbsent(pKey, l_tempSegRec);
//                       else{
//                            StringBuffer single_error_code = new StringBuffer();
//                               String replacedPkey=pKey.replace("~", "@");
//                           single_error_code = single_error_code.append("DB_VAL_018").append(","+p_fileName).append(","+p_tableName).append(","+replacedPkey);
//                           p_session.getErrorhandler().setSingle_err_code(single_error_code);
//                           p_session.getErrorhandler().log_error();
//                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
//                          
//                                 }  
                           }
                   }
                   
               }
            
         
           
//         }
        dbg("end of setRecordFromTempSegment",p_session);
        
     } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    
     }
}


public synchronized boolean validateDBReadBuffer(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
    
//        boolean l_session_created_now=false; 
     try{
//        dbSession.createDBsession(session);
//        l_session_created_ now=session.isI_session_created_now();
        dbg("inside DBReadBufferService--->validateDBReadBuffer",p_session);
        dbg("inside DBReadBufferService--->validateDBReadBuffer--->fileName"+p_fileName,p_session);
        dbg("inside DBReadBufferService--->validateDBReadBuffer--->p_tableName"+p_tableName,p_session);
        dbg("inside DBReadBufferService--->validateDBReadBuffer--->pKey"+p_tableName,p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_filetype=fileUtil.getFileType(p_fileName);
//         Map<String, Map<String,DBRecord>>l_readBuffer_fileMap;
           ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_readBuffer_fileMap;
//         synchronized(lock1){
       
             l_readBuffer_fileMap=getFileFromBufferWithoutClone(p_fileName,p_session,p_dbSession);
             
//          l_readBuffer_fileMap=getFileFromBuffer(p_fileName,p_session,p_dbSession);
//         }
         if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->validateDBReadBuffer--->l_readBuffer_fileMap contains the file name",p_session);
          //Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
        // DBRecord l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
         
           dbg("inside DBReadBufferService--->validateDBReadBuffer--->l_tempSegRec not null",p_session);  
          // Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
            //   while(tempSegTableIterator.hasNext()){
              //   String l_tableName=tempSegTableIterator.next();
           dbg("inside DBReadBufferService--->validateDBReadBuffer--->tableName"+p_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(p_tableName)){
               
           DBRecord  l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
            if(l_tempSegRec!=null){   
                dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_readBuffer_fileMap contains the table name",p_session);  
                     // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                      //Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      //while(tempSegRecordIterator.hasNext()){
                        //  String l_tempSegRecPKey=tempSegRecordIterator.next();
                          dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+pKey,p_session);
                          // DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                          if(l_readBuffer_fileMap.get(p_tableName).containsKey(pKey)){
                  dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->operation"+l_tempSegRec.getOperation(),p_session);                                   
                             
                              if(l_tempSegRec.getOperation()=='D'){
//                                   l_readBuffer_fileMap.get(p_tableName).remove(pKey);
                              }else if(l_tempSegRec.getOperation()=='U'){
//                                   l_readBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
                              }
                              else if(l_tempSegRec.getOperation()=='C'){
                             //check version     
                               if(fileUtil.checkVersionAvailability(l_filetype, p_tableName, p_session, dbdi)){
                                   
                                   int existingVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_readBuffer_fileMap.get(p_tableName).get(pKey).getRecord(), p_session, dbdi);
                                   int newVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
                                   dbg("new version number"+newVersionNumber,p_session);
                                   dbg("existing version number"+existingVersionNumber,p_session);
                                   if(newVersionNumber==existingVersionNumber+1){
                                       dbg("newVersionNumber==existingVersionNumber+1",p_session);
//                                        l_readBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
                                       
                                   }else{
                                       StringBuffer single_error_code = new StringBuffer();
                                       single_error_code = single_error_code.append("DB_VAL_009");
                                       p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                       p_session.getErrorhandler().log_error();
                                       throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                   }
                               }else{
                             
                               StringBuffer single_error_code = new StringBuffer();
                               single_error_code = single_error_code.append("DB_VAL_009");
                               p_session.getErrorhandler().setSingle_err_code(single_error_code);
                               p_session.getErrorhandler().log_error();
                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                               }
                              }
                          
                          }  
                          else if(l_tempSegRec.getOperation()!='D'){
//                              l_readBuffer_fileMap.get(p_tableName).putIfAbsent(pKey, l_tempSegRec);
                          } 
//                              else {
//                             StringBuffer single_error_code = new StringBuffer();
//                               String replacedPkey=pKey.replace("~", "@");
//                           single_error_code = single_error_code.append("DB_VAL_018").append(","+p_fileName).append(","+p_tableName).append(","+replacedPkey);
//                           p_session.getErrorhandler().setSingle_err_code(single_error_code);
//                           p_session.getErrorhandler().log_error();
//                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
//                          
//                          
//                          }
                          
                      }
                 
                    else {
//                       l_readBuffer_fileMap.putIfAbsent(p_tableName, new HashMap());
//                       l_readBuffer_fileMap.putIfAbsent(p_tableName, fileUtil.createConcurrentRecordMap(p_session));
                       // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(p_tableName);
                      
                     // Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                     // while(tempSegRecordIterator.hasNext()){
                       //String l_tempSegRecPKey=tempSegRecordIterator.next();
                       //DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                     //  if(l_tempSegRec.getOperation()!='D'){
//                       l_readBuffer_fileMap.get(p_tableName).putIfAbsent(pKey, l_tempSegRec);
                       //}
//                       else{
//                            StringBuffer single_error_code = new StringBuffer();
//                               String replacedPkey=pKey.replace("~", "@");
//                           single_error_code = single_error_code.append("DB_VAL_018").append(","+p_fileName).append(","+p_tableName).append(","+replacedPkey);
//                           p_session.getErrorhandler().setSingle_err_code(single_error_code);
//                           p_session.getErrorhandler().log_error();
//                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
//                          
//                                 }  
                           }
                   }
                   
               }
            
         
           
//         }
        dbg("end of setRecordFromTempSegment",p_session);
        return true;
     } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    
     }
}




public  void SetRecordfromArchivalWrite(String p_fileName,String p_tableName,String pKey,DBRecord l_tempSegRec,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
     ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_readBuffer_fileMap=null;
       
     try{
        dbg("inside DBReadBufferService--->SetRecordfromTempseg",p_session);
        dbg("inside DBReadBufferService--->SetRecordfromTempseg--->fileName"+p_fileName,p_session);
        dbg("inside DBReadBufferService--->SetRecordfromTempseg--->p_tableName"+p_tableName,p_session);
        dbg("inside DBReadBufferService--->SetRecordfromTempseg--->pKey"+p_tableName,p_session);
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_filetype=fileUtil.getFileType(p_fileName);
        l_readBuffer_fileMap=getFileFromBufferWithoutClone(p_fileName,p_session,p_dbSession);
         
            if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->SetRecordfromTempseg--->l_readBuffer_fileMap contains the file name",p_session);
          if(l_tempSegRec!=null){   
           dbg("inside DBReadBufferService--->SetRecordfromTempseg--->l_tempSegRec not null",p_session);  
           dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->tableName"+p_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(p_tableName)){
               
               dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_readBuffer_fileMap contains the table name",p_session);  
                          dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+pKey,p_session);
                          if(l_readBuffer_fileMap.get(p_tableName).containsKey(pKey)){
                  dbg("inside DBReadBufferService--->modifyBufferFromTempSegment--->operation"+l_tempSegRec.getOperation(),p_session);                                   
                             
                              if(l_tempSegRec.getOperation()=='D'){
                                   l_readBuffer_fileMap.get(p_tableName).remove(pKey);
                              }else if(l_tempSegRec.getOperation()=='U'){
                                   l_readBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
                              }
                              else if(l_tempSegRec.getOperation()=='C'){
                               if(fileUtil.checkVersionAvailability(l_filetype, p_tableName, p_session, dbdi)){
                                   
                                   int existingVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_readBuffer_fileMap.get(p_tableName).get(pKey).getRecord(), p_session, dbdi);
                                   int newVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
                                   dbg("new version number"+newVersionNumber,p_session);
                                   dbg("existing version number"+existingVersionNumber,p_session);
                                   if(newVersionNumber==existingVersionNumber+1){
                                       dbg("newVersionNumber==existingVersionNumber+1",p_session);
                                        l_readBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
                                       
                                   }else{
                                       StringBuffer single_error_code = new StringBuffer();
                                       single_error_code = single_error_code.append("DB_VAL_009");
                                       p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                       p_session.getErrorhandler().log_error();
                                       throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                   }
                               }else{
                             
                               StringBuffer single_error_code = new StringBuffer();
                               single_error_code = single_error_code.append("DB_VAL_009");
                               p_session.getErrorhandler().setSingle_err_code(single_error_code);
                               p_session.getErrorhandler().log_error();
                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                               }
                              }
                          
                          }  
                          else if(l_tempSegRec.getOperation()!='D'){
                              l_readBuffer_fileMap.get(p_tableName).putIfAbsent(pKey, l_tempSegRec);
                          } 

                          
                      }
                 
                    else {
                       l_readBuffer_fileMap.putIfAbsent(p_tableName, fileUtil.createConcurrentRecordMap(p_session));

                       if(l_tempSegRec.getOperation()!='D')
                       l_readBuffer_fileMap.get(p_tableName).putIfAbsent(pKey, l_tempSegRec);

                           }
                   }
                   
               }
            
         
           
        dbg("end of setRecordFromTempSegment",p_session);
     } catch(NullPointerException ex){
         if ( l_readBuffer_fileMap  == null)  
         {
             return;
         }else if(l_readBuffer_fileMap.get(p_tableName)==null)  {
             
             return;
         }else if(l_readBuffer_fileMap.get(p_tableName).get(pKey)==null)  {
             
             return;
         }else{
             
             throw ex;
         }
             
     } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    finally{

    
     }
}




private void readFromWriteBuffer(Map<String, Map<String,DBRecord>>l_readBuffer_fileMap,String p_fileName,CohesiveSession p_session,DBSession p_dbSession )throws DBProcessingException{
    
    try{
        
        IDBWriteBufferService write=dbdi.getDBWriteService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_fileType=fileUtil.getFileType(p_fileName);
//    Map<String, Map<String,DBRecord>>l_readBuffer_fileMap=getFileFromBuffer(p_fileName,p_session,p_dbSession);
           if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_readBuffer_fileMap contains the file name",p_session);
//          Map<String, Map<String,DBRecord>>l_writeBufferFileMap=write.getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);
            ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_writeBufferFileMap=write.getClonedFileFromWriteBufferSync(p_fileName,p_session,p_dbSession);

            if(l_writeBufferFileMap!=null){   
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->writ buffer filemap contains the file name",p_session);  
           Iterator<String> writeBufferTableIterator=l_writeBufferFileMap.keySet().iterator();
               while(writeBufferTableIterator.hasNext()){
                 String l_tableName=writeBufferTableIterator.next();
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->tableName"+l_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(l_tableName)){
               dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_readBuffer_fileMap contains the table name",p_session);  
//                      Map<String,DBRecord>l_writBufferTableMap= l_writeBufferFileMap.get(l_tableName);
                   Map<String,DBRecord> l_writBufferTableMap=versionFilter(l_writeBufferFileMap.get(l_tableName),l_fileType,l_tableName,p_session,p_dbSession);
                      Iterator<String> writeBufferRecordIterator=l_writBufferTableMap.keySet().iterator();
                      while(writeBufferRecordIterator.hasNext()){
                          String l_writeBufferRecPKey=writeBufferRecordIterator.next();
                          dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_writeBufferRecPKey"+l_writeBufferRecPKey,p_session);
                          //Getthe Max version
                          
                          DBRecord l_writBufferDBRecord=l_writBufferTableMap.get(l_writeBufferRecPKey);
                           
                          
                          
                          if(l_readBuffer_fileMap.get(l_tableName).containsKey(l_writeBufferRecPKey)){
                  dbg("inside DBReadBufferService--->readFromWriteBuffer--->operation"+l_writBufferDBRecord.getOperation(),p_session);            
                 
                             // if(l_writBufferDBRecord.getOperation()=='D'){
                               //    l_readBuffer_fileMap.get(l_tableName).remove(l_writeBufferRecPKey);
                         //   }else
                              if(l_writBufferDBRecord.getOperation()=='U'){
                                   l_readBuffer_fileMap.get(l_tableName).replace(l_writeBufferRecPKey,l_writBufferDBRecord);
                              }  
                              
                          else if(l_writBufferDBRecord.getOperation()=='C'){
                              l_readBuffer_fileMap.get(l_tableName).replace(l_writeBufferRecPKey, l_writBufferDBRecord);
                            //  l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBufferDBRecord);
                              
                          }
                          }else{
                              if(l_writBufferDBRecord.getOperation()!='D'){
                                 l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBufferDBRecord);
                              }
                          }

                     }
                    }else {
                       l_readBuffer_fileMap.putIfAbsent(l_tableName, new HashMap());
                        Map<String,DBRecord>l_writBufferTableMap=versionFilter(l_writeBufferFileMap.get(l_tableName),l_fileType,l_tableName,p_session,p_dbSession);
                      Iterator<String> writeBufferRecordIterator=l_writBufferTableMap.keySet().iterator();
                      while(writeBufferRecordIterator.hasNext()){
                       String l_writeBufferRecPKey=writeBufferRecordIterator.next();
                       DBRecord l_writBuffferDBRecord=l_writBufferTableMap.get(l_writeBufferRecPKey);
                       if(l_writBuffferDBRecord.getOperation()!='D')
                       l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBuffferDBRecord);
                      }
                   }
                   
               }
            }
        
           }
//    } catch(DBValidationException ex){
//           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
}

    public synchronized void setFromWriteBuffer(Map<String, Map<String,DBRecord>>l_writeBufferFileMap,String p_fileName,CohesiveSession p_session,DBSession p_dbSession )throws DBProcessingException{

    try{
        
        IDBWriteBufferService write=dbdi.getDBWriteService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_fileType=fileUtil.getFileType(p_fileName);
//    Map<String, Map<String,DBRecord>>l_readBuffer_fileMap=getFileFromBuffer(p_fileName,p_session,p_dbSession);
      Map<String, Map<String,DBRecord>>l_readBuffer_fileMap=getFileFromBuffer(p_fileName,p_session,p_dbSession);
        if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_readBuffer_fileMap contains the file name",p_session);
//          Map<String, Map<String,DBRecord>>l_writeBufferFileMap=write.getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);
//            ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_writeBufferFileMap=write.getClonedFileFromWriteBufferSync(p_fileName,p_session,p_dbSession);

            if(l_writeBufferFileMap!=null){   
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->writ buffer filemap contains the file name",p_session);  
           Iterator<String> writeBufferTableIterator=l_writeBufferFileMap.keySet().iterator();
               while(writeBufferTableIterator.hasNext()){
                 String l_tableName=writeBufferTableIterator.next();
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->tableName"+l_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(l_tableName)){
               dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_readBuffer_fileMap contains the table name",p_session);  
//                      Map<String,DBRecord>l_writBufferTableMap= l_writeBufferFileMap.get(l_tableName);
                   Map<String,DBRecord> l_writBufferTableMap=versionFilter(l_writeBufferFileMap.get(l_tableName),l_fileType,l_tableName,p_session,p_dbSession);
                      Iterator<String> writeBufferRecordIterator=l_writBufferTableMap.keySet().iterator();
                      while(writeBufferRecordIterator.hasNext()){
                          String l_writeBufferRecPKey=writeBufferRecordIterator.next();
                          dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_writeBufferRecPKey"+l_writeBufferRecPKey,p_session);
                          //Getthe Max version
                          
                          DBRecord l_writBufferDBRecord=l_writBufferTableMap.get(l_writeBufferRecPKey);
                           
                          
                          
                          if(l_readBuffer_fileMap.get(l_tableName).containsKey(l_writeBufferRecPKey)){
                  dbg("inside DBReadBufferService--->readFromWriteBuffer--->operation"+l_writBufferDBRecord.getOperation(),p_session);            
                 
                             // if(l_writBufferDBRecord.getOperation()=='D'){
                               //    l_readBuffer_fileMap.get(l_tableName).remove(l_writeBufferRecPKey);
                         //   }else
                              if(l_writBufferDBRecord.getOperation()=='U'){
                                   l_readBuffer_fileMap.get(l_tableName).replace(l_writeBufferRecPKey,l_writBufferDBRecord);
                              }  
                              
                          else if(l_writBufferDBRecord.getOperation()=='C'){
                              l_readBuffer_fileMap.get(l_tableName).replace(l_writeBufferRecPKey, l_writBufferDBRecord);
                            //  l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBufferDBRecord);
                              
                          }
                          }else{
                              if(l_writBufferDBRecord.getOperation()!='D'){
                                 l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBufferDBRecord);
                              }
                          }

                     }
                    }else {
                       l_readBuffer_fileMap.putIfAbsent(l_tableName, new HashMap());
                        Map<String,DBRecord>l_writBufferTableMap=versionFilter(l_writeBufferFileMap.get(l_tableName),l_fileType,l_tableName,p_session,p_dbSession);
                      Iterator<String> writeBufferRecordIterator=l_writBufferTableMap.keySet().iterator();
                      while(writeBufferRecordIterator.hasNext()){
                       String l_writeBufferRecPKey=writeBufferRecordIterator.next();
                       DBRecord l_writBuffferDBRecord=l_writBufferTableMap.get(l_writeBufferRecPKey);
                       if(l_writBuffferDBRecord.getOperation()!='D')
                       l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBuffferDBRecord);
                      }
                   }
                   
               }
            }
        
           }
//    } catch(DBValidationException ex){
//           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
}
private void readFromWriteBuffer(ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_readBuffer_fileMap,String p_fileName,CohesiveSession p_session,DBSession p_dbSession )throws DBProcessingException{
    
    try{
        
        IDBWriteBufferService write=dbdi.getDBWriteService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_fileType=fileUtil.getFileType(p_fileName);
//    Map<String, Map<String,DBRecord>>l_readBuffer_fileMap=getFileFromBuffer(p_fileName,p_session,p_dbSession);
           if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_readBuffer_fileMap contains the file name",p_session);
//          Map<String, Map<String,DBRecord>>l_writeBufferFileMap=write.getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);
            ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_writeBufferFileMap=write.getClonedFileFromWriteBufferSync(p_fileName,p_session,p_dbSession);

            if(l_writeBufferFileMap!=null){   
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->writ buffer filemap contains the file name",p_session);  
           Iterator<String> writeBufferTableIterator=l_writeBufferFileMap.keySet().iterator();
               while(writeBufferTableIterator.hasNext()){
                 String l_tableName=writeBufferTableIterator.next();
           dbg("inside DBReadBufferService--->readFromWriteBuffer--->tableName"+l_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(l_tableName)){
               dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_readBuffer_fileMap contains the table name",p_session);  
//                      Map<String,DBRecord>l_writBufferTableMap= l_writeBufferFileMap.get(l_tableName);
                   Map<String,DBRecord> l_writBufferTableMap=versionFilter(l_writeBufferFileMap.get(l_tableName),l_fileType,l_tableName,p_session,p_dbSession);
                      Iterator<String> writeBufferRecordIterator=l_writBufferTableMap.keySet().iterator();
                      while(writeBufferRecordIterator.hasNext()){
                          String l_writeBufferRecPKey=writeBufferRecordIterator.next();
                          dbg("inside DBReadBufferService--->readFromWriteBuffer--->l_writeBufferRecPKey"+l_writeBufferRecPKey,p_session);
                          //Getthe Max version
                          
                          DBRecord l_writBufferDBRecord=l_writBufferTableMap.get(l_writeBufferRecPKey);
                           
                          
                          
                          if(l_readBuffer_fileMap.get(l_tableName).containsKey(l_writeBufferRecPKey)){
                  dbg("inside DBReadBufferService--->readFromWriteBuffer--->operation"+l_writBufferDBRecord.getOperation(),p_session);            
                 
                             // if(l_writBufferDBRecord.getOperation()=='D'){
                               //    l_readBuffer_fileMap.get(l_tableName).remove(l_writeBufferRecPKey);
                         //   }else
                              if(l_writBufferDBRecord.getOperation()=='U'){
                                   l_readBuffer_fileMap.get(l_tableName).replace(l_writeBufferRecPKey,l_writBufferDBRecord);
                              }  
                              
                          else if(l_writBufferDBRecord.getOperation()=='C'){
                              l_readBuffer_fileMap.get(l_tableName).replace(l_writeBufferRecPKey, l_writBufferDBRecord);
                            //  l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBufferDBRecord);
                              
                          }
                          }else{
                              if(l_writBufferDBRecord.getOperation()!='D'){
                                 l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBufferDBRecord);
                              }
                          }

                     }
                    }else {
                       l_readBuffer_fileMap.putIfAbsent(l_tableName, new ConcurrentHashMap());
                        Map<String,DBRecord>l_writBufferTableMap=versionFilter(l_writeBufferFileMap.get(l_tableName),l_fileType,l_tableName,p_session,p_dbSession);
                      Iterator<String> writeBufferRecordIterator=l_writBufferTableMap.keySet().iterator();
                      while(writeBufferRecordIterator.hasNext()){
                       String l_writeBufferRecPKey=writeBufferRecordIterator.next();
                       DBRecord l_writBuffferDBRecord=l_writBufferTableMap.get(l_writeBufferRecPKey);
                       if(l_writBuffferDBRecord.getOperation()!='D')
                       l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_writeBufferRecPKey, l_writBuffferDBRecord);
                      }
                   }
                   
               }
            }
        
           }
//    } catch(DBValidationException ex){
//           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
}

private void readFileFromTempBuffer(Map<String, Map<String,DBRecord>>l_readBuffer_fileMap,String p_fileName,CohesiveSession p_session,DBSession p_dbSession )throws DBProcessingException{
    
    try{
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        ILockService lock=dbdi.getLockService();
           if(l_readBuffer_fileMap!=null){
           dbg("inside DBReadBufferService--->readFromTempBuffer--->l_readBuffer_fileMap contains the file name",p_session);
//          Map<String, Map<String,DBRecord>>l_tempSegmentFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
          ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_tempSegmentFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
          if(l_tempSegmentFileMap!=null){   
           dbg("inside DBReadBufferService--->readFromTempBuffer--->temp segment filemap contains the file name",p_session);  
           Iterator<String> tempBufferTableIterator=l_tempSegmentFileMap.keySet().iterator();
               while(tempBufferTableIterator.hasNext()){
                 String l_tableName=tempBufferTableIterator.next();
           dbg("inside DBReadBufferService--->readFromTempBuffer--->tableName"+l_tableName,p_session); 
           if(l_readBuffer_fileMap.containsKey(l_tableName)){
               dbg("inside DBReadBufferService--->readFromTempBuffer--->l_readBuffer_fileMap contains the table name",p_session);  
                      Map<String,DBRecord>l_tempBufferTableMap= l_tempSegmentFileMap.get(l_tableName);
                      
                      
                      if(l_tempBufferTableMap!=null){
                      
                      Iterator<String> tempBufferRecordIterator=l_tempBufferTableMap.keySet().iterator();
                      while(tempBufferRecordIterator.hasNext()){
                          
                         String l_tempBufferRecPKey=new String();
                          l_tempBufferRecPKey=tempBufferRecordIterator.next();
                          
                          if(l_tempBufferRecPKey!=null){
                          
                          dbg("inside DBReadBufferService--->readFromTempBuffer--->l_writeBufferRecPKey"+l_tempBufferRecPKey,p_session);
                          
                          if( lock.isSameSessionRecordLock(p_fileName,l_tableName,l_tempBufferRecPKey,p_session)){
                              
//                              DBRecord l_tempBufferDBRecord=l_tempBufferTableMap.get(l_tempBufferRecPKey);
                                DBRecord l_tempBufferDBRecord= tempSegment.getRecordFromTempSegment(p_fileName, l_tableName, l_tempBufferRecPKey, p_session, p_dbSession);
                           
                                if(l_tempBufferDBRecord!=null){
                                
                          if(l_readBuffer_fileMap.get(l_tableName).containsKey(l_tempBufferRecPKey)){
                  dbg("inside DBReadBufferService--->readFromtemoBuffer--->operation"+l_tempBufferDBRecord.getOperation(),p_session);            
                 
                              if(l_tempBufferDBRecord.getOperation()=='D'){
                                   l_readBuffer_fileMap.get(l_tableName).remove(l_tempBufferRecPKey);
                              }else if(l_tempBufferDBRecord.getOperation()=='U'){
                                   l_readBuffer_fileMap.get(l_tableName).replace(l_tempBufferRecPKey,l_tempBufferDBRecord);
                              }  
                          }else if(l_tempBufferDBRecord.getOperation()=='C'){
                              l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempBufferRecPKey, l_tempBufferDBRecord);
                              
                          }
                          
                          }
                          
                          }
                      }

                     }
                      
                      }
                      
                      
                    }else {
                       l_readBuffer_fileMap.putIfAbsent(l_tableName, new HashMap());
                        Map<String,DBRecord>l_tempBufferTableMap= l_tempSegmentFileMap.get(l_tableName);
                    if(l_tempBufferTableMap!=null){  
                      Iterator<String> tempBufferRecordIterator=l_tempBufferTableMap.keySet().iterator();
                      while(tempBufferRecordIterator.hasNext()){
                       String l_tempBufferRecPKey=new String();   
                        l_tempBufferRecPKey=tempBufferRecordIterator.next();
                        if( lock.isSameSessionRecordLock(p_fileName,l_tableName,l_tempBufferRecPKey,p_session)){
                       
//                            DBRecord l_tempBuffferDBRecord=l_tempBufferTableMap.get(l_tempBufferRecPKey);
                              DBRecord l_tempBuffferDBRecord= tempSegment.getRecordFromTempSegment(p_fileName, l_tableName, l_tempBufferRecPKey, p_session, p_dbSession);
                           if(l_tempBuffferDBRecord!=null){
                              
                              if(l_tempBuffferDBRecord.getOperation()!='D')
                               l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempBufferRecPKey, l_tempBuffferDBRecord);
                           }
                       
                      }
                      }
                      
                 }
                      
                   }
                   
               }
            }
        
           }
           dbg("end of DBReadBufferService--->readFromTempBuffer",p_session);
//           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
}

public boolean checkFileExistenceInReadBuffer(String fileName,CohesiveSession p_session)throws DBProcessingException{
     boolean status=false;
     try{
         dbg("inside checkFileExistenceInWriteBuffer",p_session);
         if(readBufferMap.containsKey(fileName)){
             
             status=true;
         }
         
         
         dbg("end of checkFileExistenceInWriteBuffer--->status--->"+status,p_session);
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
     return status;
 }

public int getReadBufferSize(CohesiveSession p_session)throws DBProcessingException{
    
    try{
    return readBufferMap.size();
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
//  private void dbg(String p_Value) {
//        session.getDebug().dbg(p_Value);
//
//    }
//
//    private void dbg(Exception ex) {
//        session.getDebug().exceptionDbg(ex);
//
//    }
}
