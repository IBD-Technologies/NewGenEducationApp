
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.write;

//import com.ibd.cohesive.db.core.metadata.DBColumn;
//import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.io.IPhysicalIOService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.tempSegment.IDBTempSegmentService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
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
import javax.annotation.PreDestroy;
//import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
//import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.DependsOn;
//import javax.ejb.Lock;
//import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Singleton
@ConcurrencyManagement(BEAN)
@DependsOn({"PhysicalIOService"})
//@AccessTimeout(value=30000)

public class DBWriteBufferService implements IDBWriteBufferService {
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
//    Map<String,Map<String, Map<String,DBRecord>>> writeBuffer;
        Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> writeBuffer;
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
    
//    public Map<String, Map<String, Map<String, DBRecord>>> getWriteBuffer() {
//        return writeBuffer;
//    }
    public  Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> getWriteBuffer() {
        return writeBuffer;
    }
    
    
    public DBWriteBufferService() throws NamingException {
        dbdi = new DBDependencyInjection();
//        session = new CohesiveSession();
//        dbSession = new DBSession(session);
//        writeBuffer=new HashMap();
//        writeBuffer=new ConcurrentHashMap();
   if(initialCapacity==0){
          writeBuffer=new ConcurrentHashMap();
       }else{
         
         writeBuffer=new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
     }
    }
    
//    @Lock(LockType.WRITE)  
public synchronized void modifyBufferFromTempSegment(String p_fileName,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
    
//        boolean l_session_created_now=false; 
     try{
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBwriteService--->modifyBufferFromTempSegment",p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        ErrorHandler errorhandler=p_session.getErrorhandler();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
//         Map<String, Map<String,DBRecord>>l_writeBuffer_fileMap=getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);
           ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_writeBuffer_fileMap=getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);
          if(l_writeBuffer_fileMap!=null){
           dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_write_fileMap contains the file name",p_session);
//          Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
            ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);

            if(l_tempSegFileMap!=null){   
           dbg("inside DBwriteService--->modifyBufferFromTempSegment--->temp segment filemap contains the file name",p_session);  
           Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
               while(tempSegTableIterator.hasNext()){
                 String l_tableName=tempSegTableIterator.next();
           dbg("inside DBwriteService--->modifyBufferFromTempSegment--->tableName"+l_tableName,p_session); 
           if(l_writeBuffer_fileMap.containsKey(l_tableName)){
               dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_temp_fileMap contains filename and the table name",p_session);  
                      Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      while(tempSegRecordIterator.hasNext()){
                          String l_tempSegRecPKey=tempSegRecordIterator.next();
                          dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+l_tempSegRecPKey,p_session);
                           DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                          if(l_writeBuffer_fileMap.get(l_tableName).containsKey(l_tempSegRecPKey)){
                  dbg("inside DBwriteService--->modifyBufferFromTempSegment--->operation"+l_tempSegDBRecord.getOperation(),p_session);     
                              if(l_tempSegDBRecord.getOperation()=='D'){
                                  
                                   l_writeBuffer_fileMap.get(l_tableName).replace(l_tempSegRecPKey,l_tempSegDBRecord);
                              }else if(l_tempSegDBRecord.getOperation()=='U'){
                                 if( l_writeBuffer_fileMap.get(l_tableName).get(l_tempSegRecPKey).getOperation()=='D'){
                                     StringBuffer single_error_code = new StringBuffer();
                                     String replacedPkey=l_tempSegRecPKey.replace("~", "@");
                                     single_error_code = single_error_code.append("DB_VAL_016".concat("," + l_tableName).concat("," + replacedPkey));
                                     errorhandler.setSingle_err_code(single_error_code);
                                     errorhandler.log_error();
                                     throw new DBValidationException(errorhandler.getSession_error_code().toString());
                                     
                                      }else if( l_writeBuffer_fileMap.get(l_tableName).get(l_tempSegRecPKey).getOperation()=='C'){
                                          
                                          l_tempSegDBRecord.setOperation('C');
                                          l_writeBuffer_fileMap.get(l_tableName).replace(l_tempSegRecPKey,l_tempSegDBRecord);
                                          
                                      }else{
                                          l_writeBuffer_fileMap.get(l_tableName).replace(l_tempSegRecPKey,l_tempSegDBRecord);
                                      }
//                                     
//                                     
//                                   l_writeBuffer_fileMap.get(l_tableName).replace(l_tempSegRecPKey,l_tempSegDBRecord);
//                                 
//                                 
//                                 }
                                     
                                     
                                     
                                     
//                                 }else {
//                                     
//                                     
//                                   l_writeBuffer_fileMap.get(l_tableName).replace(l_tempSegRecPKey,l_tempSegDBRecord);
//                                 
//                                 
//                                 }                            
                                 }else if(l_tempSegDBRecord.getOperation()=='C'){
                                     StringBuffer single_error_code = new StringBuffer();
                                     String replacedPkey=l_tempSegRecPKey.replace("~", "@");
                                     single_error_code = single_error_code.append("DB_VAL_017".concat("," + l_tableName).concat("," + replacedPkey));
                                     errorhandler.setSingle_err_code(single_error_code);
                                     errorhandler.log_error();
                                     throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                                 }  
                          }else if(l_tempSegDBRecord.getOperation()=='C'){
                              
                              l_writeBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempSegRecPKey, l_tempSegDBRecord);
                              
                          }
                          
                      }
                    }else {
                      dbg("dbwrite--->modifyfromtemp segment--->writebuffermap contains the filename doesn't contains the tablename",p_session);
//                       l_writeBuffer_fileMap.putIfAbsent(l_tableName, new HashMap());
                          l_writeBuffer_fileMap.putIfAbsent(l_tableName,fileUtil.createConcurrentRecordMap(p_session));
                        Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                      
                      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      while(tempSegRecordIterator.hasNext()){
                       String l_tempSegRecPKey=tempSegRecordIterator.next();
                       DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
//                       if(l_tempSegDBRecord.getOperation()!='D')
                       l_writeBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempSegRecPKey, l_tempSegDBRecord);
                      }
                   }
                   
               }
            }
        
           }else{
               dbg("dbwrite--->modifyFromTempSegment--->fileName not in the l_write_fileMap ",p_session);
//               Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
               ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
//               writeBuffer.putIfAbsent(p_fileName, new HashMap());
                writeBuffer.putIfAbsent(p_fileName, fileUtil.createConcurrentTableMap(p_fileName, p_session,"Write"));
               Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
               while(tempSegTableIterator.hasNext()){
                 String l_tableName=tempSegTableIterator.next();
//               writeBuffer.get(p_fileName).putIfAbsent(l_tableName, new HashMap());
                 writeBuffer.get(p_fileName).putIfAbsent(l_tableName, fileUtil.createConcurrentRecordMap(p_session));
               Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      while(tempSegRecordIterator.hasNext()){
                          String l_tempSegRecPKey=tempSegRecordIterator.next();
                          dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+l_tempSegRecPKey,p_session);
                           DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                           writeBuffer.get(p_fileName).get(l_tableName).putIfAbsent(l_tempSegRecPKey, l_tempSegDBRecord);
               
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
        }
    
    
}

 public synchronized void SetRecordfromTempseg(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException
 {
//        boolean l_session_created_now=false; 
     try{
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBwriteService--->SetRecordfromTempseg",p_session);
        dbg("p_fileName"+p_fileName,p_session);
        dbg("p_tableName"+p_tableName,p_session);
        dbg("pKey"+pKey,p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        ErrorHandler errorhandler=p_session.getErrorhandler();
        IBDFileUtil fileutil=p_dbSession.getIibd_file_util();
//      Map<String, Map<String,DBRecord>>l_writeBuffer_fileMap=getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);
        ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_writeBuffer_fileMap=getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);

        DBRecord l_tempSegRec=tempSegment.getRecordFromTempSegment(p_fileName,p_tableName,pKey,p_session,p_dbSession);
        String l_fileType=fileutil.getFileType(p_fileName);
       
        String l_pkWithVersion= fileutil.getPkeyWithVersion(p_fileName,p_tableName,pKey,l_tempSegRec.getRecord(),p_session,p_dbSession,dbdi);
        dbg("inside writeBuffer--->SetRecordfromTempseg--->l_pkWithVersion"+l_pkWithVersion,p_session); 
         if(l_writeBuffer_fileMap!=null){
           dbg("inside DBwriteService--->SetRecordfromTempseg--->l_write_fileMap not null",p_session);
            if(l_tempSegRec!=null){   
           dbg("inside DBwriteService--->SetRecordfromTempseg--->l_tempSegRec is not null",p_session);  
          // Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
            //   while(tempSegTableIterator.hasNext()){
              //   String l_tableName=tempSegTableIterator.next();
//           dbg("inside DBwriteService--->modifyBufferFromTempSegment--->tableName"+p_tableName,p_session); 
           if(l_writeBuffer_fileMap.containsKey(p_tableName)){
               dbg("inside DBwriteService--->SetRecordfromTempseg--->l_writeBuffer_fileMap contains filename and the table name",p_session);  
                     // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(p_tableName);
                      //Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      //while(tempSegRecordIterator.hasNext()){
                        //  String l_tempSegRecPKey=tempSegRecordIterator.next();
//                          dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+pKey,p_session);
                          // DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
                          if(l_writeBuffer_fileMap.get(p_tableName).containsKey(l_pkWithVersion)){
                   dbg("inside DBwriteService--->SetRecordfromTempseg--->l_writeBuffer_fileMap contains filename and the table name and pkey",p_session);            
                  dbg("inside DBwriteService--->modifyBufferFromTempSegment--->operation"+l_tempSegRec.getOperation(),p_session);     
                              if(l_tempSegRec.getOperation()=='D'){
                                   dbg("l_tempSegRec.getOperation()=='D'",p_session);
                                   l_writeBuffer_fileMap.get(p_tableName).replace(l_pkWithVersion,l_tempSegRec);
                              }else if(l_tempSegRec.getOperation()=='U'){
                                  dbg("l_tempSegRec.getOperation()=='U'",p_session);
                                 if( l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getOperation()=='D'){
                                     dbg("temp seg operation is U existing operation D",p_session);
                                     StringBuffer single_error_code = new StringBuffer();
                                     String replacedPkey=l_pkWithVersion.replace("~", "@");
                                     single_error_code = single_error_code.append("DB_VAL_016".concat("," + p_tableName).concat("," + replacedPkey));
                                     errorhandler.setSingle_err_code(single_error_code);
                                     errorhandler.log_error();
                                     throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                                  }else if( l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getOperation()=='C'){
                                         dbg("temp seg operation is U existing operation D",p_session);
                                         dbg("setting operation c if existing is c",p_session);
                                          l_tempSegRec.setOperation('C');
                                          l_writeBuffer_fileMap.get(p_tableName).replace(l_pkWithVersion,l_tempSegRec);
                                          
                                      }else if( l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getOperation()=='U'){
                                          dbg("temp seg operation is U existing operation U",p_session);
                                          l_writeBuffer_fileMap.get(p_tableName).replace(l_pkWithVersion,l_tempSegRec);
                                          
                                          ArrayList<String>tempRecord=l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getRecord();
                                          
                                          for(int i=0;i<tempRecord.size();i++){
                                              
                                             dbg("tempRecord-->"+tempRecord.get(i),p_session);
                                              
                                              
                                          }
                                      }
//                                 }else{
//                                   l_writeBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
//                                 }                            
                                 }else if(l_tempSegRec.getOperation()=='C'){
                                     dbg("operation is create",p_session);
//                                     dbg("temp seg operation is create",p_session);
//                                     
//                                     if(fileutil.checkVersionAvailability(l_fileType, p_tableName, p_session, dbdi)){
//                                   
//                                   int existingVersionNumber=fileutil.getVersionNumberFromTheRecord(l_fileType, p_tableName, l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getRecord(), p_session, dbdi);
//                                   int newVersionNumber=fileutil.getVersionNumberFromTheRecord(l_fileType, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
//                                   
//                                   dbg("new version number"+newVersionNumber,p_session);
//                                   dbg("existing version number"+existingVersionNumber,p_session);
//                                   
//                                   if(newVersionNumber==existingVersionNumber+1){
//                                       dbg("newVersionNumber==existingVersionNumber+1",p_session);
//                                        l_writeBuffer_fileMap.get(p_tableName).putIfAbsent(l_pkWithVersion,l_tempSegRec);
//                                       
//                                   }else{
//                                    StringBuffer single_error_code = new StringBuffer();
//                                     String replacedPkey=l_pkWithVersion.replace("~", "@");
//                                     single_error_code = single_error_code.append("DB_VAL_017".concat("," + p_tableName).concat("," + replacedPkey));
//                                     errorhandler.setSingle_err_code(single_error_code);
//                                     errorhandler.log_error();
//                                     throw new DBValidationException(errorhandler.getSession_error_code().toString());
//                                   }
//                               }else{                           
                                     
                                     StringBuffer single_error_code = new StringBuffer();
                                     String replacedPkey=l_pkWithVersion.replace("~", "@");
                                     single_error_code = single_error_code.append("DB_VAL_017".concat("," + p_tableName).concat("," + replacedPkey));
                                     errorhandler.setSingle_err_code(single_error_code);
                                     errorhandler.log_error();
                                     throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//                                 }  
                              }
//                          }else if(l_tempSegRec.getOperation()=='C'){
                              }else{
                              dbg("write buffer doesn't contains primary key",p_session);
                              l_writeBuffer_fileMap.get(p_tableName).putIfAbsent(l_pkWithVersion, l_tempSegRec);
                              
                          }
                          
                      
                    }else {
                      dbg("dbwrite--->modifyfromtemp segment--->writebuffermap contains the filename doesn't contains the tablename",p_session);
                       l_writeBuffer_fileMap.putIfAbsent(p_tableName, fileutil.createConcurrentRecordMap(p_session));
                       // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(p_tableName);
                      
                     // Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                     // while(tempSegRecordIterator.hasNext()){
                       //String l_tempSegRecPKey=tempSegRecordIterator.next();
                       //DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
//                       if(l_tempSegDBRecord.getOperation()!='D')
                       l_writeBuffer_fileMap.get(p_tableName).putIfAbsent(l_pkWithVersion, l_tempSegRec);
                      }
                   }
                   
               
            
        
           }else{
               dbg("dbwrite--->modifyFromTempSegment--->fileName not in the l_write_fileMap ",p_session);
              // Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
//               writeBuffer.putIfAbsent(p_fileName, new HashMap());
                 writeBuffer.putIfAbsent(p_fileName, fileutil.createConcurrentTableMap(p_fileName, p_session,"Write"));
               //Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
               //while(tempSegTableIterator.hasNext()){
                 //String l_tableName=tempSegTableIterator.next();
//               writeBuffer.get(p_fileName).putIfAbsent(p_tableName, new HashMap());
                 writeBuffer.get(p_fileName).putIfAbsent(p_tableName, fileutil.createConcurrentRecordMap(p_session));
              // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                //      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                  //    while(tempSegRecordIterator.hasNext()){
                    //      String l_tempSegRecPKey=tempSegRecordIterator.next();
                          dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+l_pkWithVersion,p_session);
                          // DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(pKey);
                           writeBuffer.get(p_fileName).get(p_tableName).putIfAbsent(l_pkWithVersion, l_tempSegRec);
               
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
        }
    
    
}
 
  public synchronized boolean validateWriteBuffer(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException
 {
//        boolean l_session_created_now=false; 
     try{
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBwriteService--->SetRecordfromTempseg",p_session);
        dbg("p_fileName"+p_fileName,p_session);
        dbg("p_tableName"+p_tableName,p_session);
        dbg("pKey"+pKey,p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        ErrorHandler errorhandler=p_session.getErrorhandler();
        IBDFileUtil fileutil=p_dbSession.getIibd_file_util();
//      Map<String, Map<String,DBRecord>>l_writeBuffer_fileMap=getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);
        ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>l_writeBuffer_fileMap=getFileFromWriteBuffer(p_fileName,p_session,p_dbSession);

        DBRecord l_tempSegRec;
        String l_fileType=fileutil.getFileType(p_fileName);
       

         if(l_writeBuffer_fileMap!=null){
           dbg("inside DBwriteService--->SetRecordfromTempseg--->l_write_fileMap not null",p_session);
          
           
          
           dbg("inside DBwriteService--->SetRecordfromTempseg--->l_tempSegRec is not null",p_session);  
          // Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
            //   while(tempSegTableIterator.hasNext()){
              //   String l_tableName=tempSegTableIterator.next();
//           dbg("inside DBwriteService--->modifyBufferFromTempSegment--->tableName"+p_tableName,p_session); 
           if(l_writeBuffer_fileMap.containsKey(p_tableName)){
               
                  
               dbg("inside DBwriteService--->SetRecordfromTempseg--->l_writeBuffer_fileMap contains filename and the table name",p_session);  
                     // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(p_tableName);
                      //Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                      //while(tempSegRecordIterator.hasNext()){
                        //  String l_tempSegRecPKey=tempSegRecordIterator.next();
//                          dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+pKey,p_session);
                          // DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
             l_tempSegRec=tempSegment.getRecordFromTempSegment(p_fileName,p_tableName,pKey,p_session,p_dbSession);
              if(l_tempSegRec!=null){      
                          String l_pkWithVersion= fileutil.getPkeyWithVersion(p_fileName,p_tableName,pKey,l_tempSegRec.getRecord(),p_session,p_dbSession,dbdi);
               dbg("inside writeBuffer--->SetRecordfromTempseg--->l_pkWithVersion"+l_pkWithVersion,p_session); 
                 if(l_writeBuffer_fileMap.get(p_tableName).containsKey(l_pkWithVersion)){
                 
                               
                              
                      dbg("inside DBwriteService--->SetRecordfromTempseg--->l_writeBuffer_fileMap contains filename and the table name and pkey",p_session);            
                  dbg("inside DBwriteService--->modifyBufferFromTempSegment--->operation"+l_tempSegRec.getOperation(),p_session);     
                       
                  if(l_tempSegRec.getOperation()=='D'){
                                   dbg("l_tempSegRec.getOperation()=='D'",p_session);
//                                   l_writeBuffer_fileMap.get(p_tableName).replace(l_pkWithVersion,l_tempSegRec);
                              }else if(l_tempSegRec.getOperation()=='U'){
                                  dbg("l_tempSegRec.getOperation()=='U'",p_session);
                                 if( l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getOperation()=='D'){
                                     dbg("temp seg operation is U existing operation D",p_session);
                                     StringBuffer single_error_code = new StringBuffer();
                                     String replacedPkey=l_pkWithVersion.replace("~", "@");
                                     single_error_code = single_error_code.append("DB_VAL_016".concat("," + p_tableName).concat("," + replacedPkey));
                                     errorhandler.setSingle_err_code(single_error_code);
                                     errorhandler.log_error();
                                     throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                                  }else if( l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getOperation()=='C'){
                                         dbg("temp seg operation is U existing operation D",p_session);
                                         dbg("setting operation c if existing is c",p_session);
                                          l_tempSegRec.setOperation('C');
//                                          l_writeBuffer_fileMap.get(p_tableName).replace(l_pkWithVersion,l_tempSegRec);
                                          
                                      }else if( l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getOperation()=='U'){
                                          dbg("temp seg operation is U existing operation U",p_session);
//                                          l_writeBuffer_fileMap.get(p_tableName).replace(l_pkWithVersion,l_tempSegRec);
                                      }
//                                 }else{
//                                   l_writeBuffer_fileMap.get(p_tableName).replace(pKey,l_tempSegRec);
//                                 }                            
                                 }else if(l_tempSegRec.getOperation()=='C'){
                                     dbg("operation is create",p_session);
//                                     dbg("temp seg operation is create",p_session);
//                                     
//                                     if(fileutil.checkVersionAvailability(l_fileType, p_tableName, p_session, dbdi)){
//                                   
//                                   int existingVersionNumber=fileutil.getVersionNumberFromTheRecord(l_fileType, p_tableName, l_writeBuffer_fileMap.get(p_tableName).get(l_pkWithVersion).getRecord(), p_session, dbdi);
//                                   int newVersionNumber=fileutil.getVersionNumberFromTheRecord(l_fileType, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
//                                   
//                                   dbg("new version number"+newVersionNumber,p_session);
//                                   dbg("existing version number"+existingVersionNumber,p_session);
//                                   
//                                   if(newVersionNumber==existingVersionNumber+1){
//                                       dbg("newVersionNumber==existingVersionNumber+1",p_session);
//                                        l_writeBuffer_fileMap.get(p_tableName).putIfAbsent(l_pkWithVersion,l_tempSegRec);
//                                       
//                                   }else{
//                                    StringBuffer single_error_code = new StringBuffer();
//                                     String replacedPkey=l_pkWithVersion.replace("~", "@");
//                                     single_error_code = single_error_code.append("DB_VAL_017".concat("," + p_tableName).concat("," + replacedPkey));
//                                     errorhandler.setSingle_err_code(single_error_code);
//                                     errorhandler.log_error();
//                                     throw new DBValidationException(errorhandler.getSession_error_code().toString());
//                                   }
//                               }else{                           
                                     
                                     StringBuffer single_error_code = new StringBuffer();
                                     String replacedPkey=l_pkWithVersion.replace("~", "@");
                                     single_error_code = single_error_code.append("DB_VAL_017".concat("," + p_tableName).concat("," + replacedPkey));
                                     errorhandler.setSingle_err_code(single_error_code);
                                     errorhandler.log_error();
                                     throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//                                 }  
                              }
//                          }else if(l_tempSegRec.getOperation()=='C'){
                              }else{
                              dbg("write buffer doesn't contains primary key",p_session);
//                              l_writeBuffer_fileMap.get(p_tableName).putIfAbsent(l_pkWithVersion, l_tempSegRec);
                              
                          }
                          
                      
                    }else {
                      dbg("dbwrite--->modifyfromtemp segment--->writebuffermap contains the filename doesn't contains the tablename",p_session);
//                       l_writeBuffer_fileMap.putIfAbsent(p_tableName, fileutil.createConcurrentRecordMap(p_session));
                       // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(p_tableName);
                      
                     // Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                     // while(tempSegRecordIterator.hasNext()){
                       //String l_tempSegRecPKey=tempSegRecordIterator.next();
                       //DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
//                       if(l_tempSegDBRecord.getOperation()!='D')
//                       l_writeBuffer_fileMap.get(p_tableName).putIfAbsent(l_pkWithVersion, l_tempSegRec);
                      }
                   }
                   
               
            
        
           }else{
               dbg("dbwrite--->modifyFromTempSegment--->fileName not in the l_write_fileMap ",p_session);
              // Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(p_fileName,p_session,p_dbSession);
//               writeBuffer.putIfAbsent(p_fileName, new HashMap());
//                 writeBuffer.putIfAbsent(p_fileName, fileutil.createConcurrentTableMap(p_fileName, p_session,"Write"));
               //Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
               //while(tempSegTableIterator.hasNext()){
                 //String l_tableName=tempSegTableIterator.next();
//               writeBuffer.get(p_fileName).putIfAbsent(p_tableName, new HashMap());
//                 writeBuffer.get(p_fileName).putIfAbsent(p_tableName, fileutil.createConcurrentRecordMap(p_session));
              // Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
                //      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
                  //    while(tempSegRecordIterator.hasNext()){
                    //      String l_tempSegRecPKey=tempSegRecordIterator.next();
//                          dbg("inside DBwriteService--->modifyBufferFromTempSegment--->l_tempSegRecPKey"+l_pkWithVersion,p_session);
                          // DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(pKey);
//                           writeBuffer.get(p_fileName).get(p_tableName).putIfAbsent(l_pkWithVersion, l_tempSegRec);
               
               }  
               
        
           
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
        }
    
    
}
 
 public synchronized void removeFileFromWriteBuffer(Map<String,Map<String,DBRecord>> l_fileMap,String fileName,CohesiveSession p_session)throws DBProcessingException {
    
    try{
        
        dbg("inside remove file from write buffer--->fileName"+fileName,p_session);
        
        if(writeBuffer.containsKey(fileName)){
            dbg("writeBuffer contains fileName",p_session);
              Iterator<String> tableIterator= l_fileMap.keySet().iterator();
               while(tableIterator.hasNext()){
                   String tableName=tableIterator.next();
                   dbg("tableName"+tableName,p_session);
                   if(writeBuffer.get(fileName).containsKey(tableName)){
                       dbg("writeBuffer contains tableName",p_session);
                       Iterator<String> primaryKeyIterator= l_fileMap.get(tableName).keySet().iterator();
                       while(primaryKeyIterator.hasNext()){
                          String primarykey=primaryKeyIterator.next();
                          dbg("primarykey"+primarykey,p_session);
                          if(writeBuffer.get(fileName).get(tableName).containsKey(primarykey)){
                              dbg("writeBuffer contains primarykey",p_session);
                              DBRecord writeBufferRecord=writeBuffer.get(fileName).get(tableName).get(primarykey);
                              DBRecord cloneRecord=l_fileMap.get(tableName).get(primarykey);
                              
                              switch(cloneRecord.getOperation()){
                                  
                                  case 'C':
                                        
                                       switch(writeBufferRecord.getOperation()){
                                           
                                           case 'C':
//                                               writeBuffer.get(fileName).get(tableName).remove(primarykey);
//                                               System.out.println("Clone operation C write operation C"+fileName);
//                                               System.out.println("fileName"+fileName);
//                                               System.out.println("tableName"+tableName);
//                                               System.out.println("primarykey"+primarykey);
                                               
                                               ArrayList<String>cloneRecordValues=cloneRecord.getRecord();
                                               ArrayList<String>writeBufferRecordValus=writeBufferRecord.getRecord();
                                               boolean status=true;
                                               for(int i=0;i<cloneRecordValues.size();i++){
                                                   
                                                   dbg("cloneRecordValues"+cloneRecordValues.get(i),p_session);
                                                   dbg("writeBufferRecordValus"+writeBufferRecordValus.get(i),p_session);
//                                                   System.out.println("cloneRecordValues"+cloneRecordValues.get(i));
//                                                   System.out.println("writeBufferRecordValus"+writeBufferRecordValus.get(i));
                                                   if(!(cloneRecordValues.get(i).equals(writeBufferRecordValus.get(i)))){
                                                       
                                                       status=false;
                                                   }
                                                  
                                                   if(status==false)
                                                       
                                                   break;
                                                   
                                               }
                                               
                                               if(status){
                                                   writeBuffer.get(fileName).get(tableName).remove(primarykey);
                                               }else{
                                                   writeBuffer.get(fileName).get(tableName).get(primarykey).setOperation('U');
                                               }
                                               
                                               
                                               break;
                                               
                                           case 'U':
                                               break;
                                           case 'D':
                                               break;
                                           
                                           
                                       }
                                  
                                        break;
                                  case 'U':
                                       switch(writeBufferRecord.getOperation()){
                                          
                                               
                                           case 'U':
//                                                System.out.println("Clone operation U write operation U"+fileName);
//                                               System.out.println("fileName"+fileName);
//                                               System.out.println("tableName"+tableName);
//                                               System.out.println("primarykey"+primarykey);
                                               ArrayList<String>cloneRecordValues=cloneRecord.getRecord();
                                               ArrayList<String>writeBufferRecordValus=writeBufferRecord.getRecord();
                                               boolean status=true;
                                               for(int i=0;i<cloneRecordValues.size();i++){
                                                   
                                                   dbg("cloneRecordValues"+cloneRecordValues.get(i),p_session);
                                                   dbg("writeBufferRecordValus"+writeBufferRecordValus.get(i),p_session);
//                                                   System.out.println("cloneRecordValues"+cloneRecordValues.get(i));
//                                                   System.out.println("writeBufferRecordValus"+writeBufferRecordValus.get(i));
                                                   
                                                   if(!(cloneRecordValues.get(i).equals(writeBufferRecordValus.get(i)))){
                                                       
                                                       status=false;
                                                   }
                                                  
                                                   if(status==false)
                                                       
                                                   break;
                                                   
                                               }
                                               
                                               if(status){
                                                   writeBuffer.get(fileName).get(tableName).remove(primarykey);
                                               }
                                               
                                               
                                               //comparison of write buffer
                                               //If equals remove
                                                    
                                               break;
                                           case 'D':
                                               break;
                                               
                                           
                                           
                                       }
                                  
                                        break;
                                  
                                case 'D':
                                    writeBuffer.get(fileName).get(tableName).remove(primarykey);
                                    break;
                              }
                              
//                              DBRecord dbRec=l_fileMap.get(tableName).get(primarykey);
//                              writeBuffer.get(fileName).get(tableName).remove(primarykey);
                              dbg("record for the primarykey"+primarykey+"removed primary key removed from buffer",p_session);
                       }
                       
                   }
                   
                   if(writeBuffer.get(fileName).get(tableName).isEmpty()||writeBuffer.get(fileName).get(tableName).size()==0){    
                          writeBuffer.get(fileName).remove(tableName);
                   }
               }  
        
           }
               
               if(writeBuffer.get(fileName).isEmpty()||writeBuffer.get(fileName).size()==0){
               
                    writeBuffer.remove(fileName);
               }
        }  
        
//        writeBuffer.remove(p_fileName);
        dbg("end of remove file from write buffer",p_session);
        
   
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    
    
    
}
 
 public boolean checkFileExistenceInWriteBuffer(String fileName,CohesiveSession p_session)throws DBProcessingException{
     boolean status=false;
     try{
         dbg("inside checkFileExistenceInWriteBuffer",p_session);
         if(writeBuffer.containsKey(fileName)){
             
             status=true;
         }
         
         
         dbg("end of checkFileExistenceInWriteBuffer--->status--->"+status,p_session);
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
     return status;
 }
 
 

//public synchronized void removeFileFromWriteBuffer(String p_fileName,CohesiveSession p_session)throws DBProcessingException {
//    
//    try{
//        
//        dbg("inside remove file from write buffer--->fileName"+p_fileName,p_session);
//        writeBuffer.remove(p_fileName);
//        dbg("end of remove file from write buffer",p_session);
//        
//   
//     }catch (Exception ex) {
//          dbg(ex,p_session);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }
//    
//    
//    
//}

public int getWriteBufferSize(CohesiveSession p_session)throws DBProcessingException{
    
    try{
    return writeBuffer.size();
    }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
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
  
//  @Lock(LockType.READ)
//public Map<String, Map<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException{
 private synchronized ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException{

//    private Map<String, Map<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException{
//      boolean l_session_created_now=false;
//      Map<String, Map<String,DBRecord>> fileMap;
        ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> fileMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBTempSegment---->getFileFromWriteBuffer ",p_session);
        dbg("inside DBTempSegment---->getFileWriteBuffer--->fileNameKey "+fileNameKey,p_session);
        
//        Map<String,DBTempSegment>l_mapwithPosition=new HashMap();
        if(writeBuffer.containsKey(fileNameKey)){
        dbg("inside DBTempSegment---->getFileFromTempSegment--->WriteBuffer map contains  fileNameKey",p_session);
            fileMap=writeBuffer.get(fileNameKey);
            

           return fileMap;
        }else{
        dbg("inside DBTempSegment---->getFileFromTempSegment--->WriteBuffer map doesn't contains  fileNameKey",p_session);
            return null;
        }
        
        
        
    }
      catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
    }
public  Map<String, Map<String,DBRecord>>getClonedFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException{
//    private Map<String, Map<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException{
//      boolean l_session_created_now=false;
      ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> fileMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
       IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer ",p_session);
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer--->fileNameKey "+fileNameKey,p_session);
        
//        Map<String,DBTempSegment>l_mapwithPosition=new HashMap();
        if(writeBuffer.containsKey(fileNameKey)){
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer--->WriteBuffer map contains  fileNameKey",p_session);
            fileMap=writeBuffer.get(fileNameKey);
            

           return fileUtil.cloneFile(fileMap);
        }else{
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer--->WriteBuffer map doesn't contains  fileNameKey",p_session);
            return null;
        }
        
        
        
    }
      catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
    }
public synchronized ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>getClonedFileFromWriteBufferSync(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException{
//    private Map<String, Map<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException{
//      boolean l_session_created_now=false;
      ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> fileMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
       IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer ",p_session);
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer--->fileNameKey "+fileNameKey,p_session);
        
//        Map<String,DBTempSegment>l_mapwithPosition=new HashMap();
        if(writeBuffer.containsKey(fileNameKey)){
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer--->WriteBuffer map contains  fileNameKey",p_session);
            fileMap=writeBuffer.get(fileNameKey);
            
            
             ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> copy = new ConcurrentHashMap<>();  
        Iterator<String> tableIterator= fileMap.keySet().iterator();
        
        while(tableIterator.hasNext()){
            
            String l_tableName=tableIterator.next();
            dbg("table name in cloning"+l_tableName,p_session);
            Map<String, DBRecord> l_primaryKeyMap=fileMap.get(l_tableName);
            
            copy.put(l_tableName,new ConcurrentHashMap());
//            copy.get(l_tableName).putAll(l_primaryKeyMap);
            
            Iterator<String>keyIterator=l_primaryKeyMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    String key =keyIterator.next();
//                     key=keyIterator.next();
                    
                    copy.get(l_tableName).put(key,fileUtil.cloneRecord(l_primaryKeyMap.get(key)));
                }
            dbg("values putted for"+l_tableName,p_session);
        }
            
            

           return copy;
        }else{
        dbg("inside DBTempSegment---->getClonedFileFromWriteBuffer--->WriteBuffer map doesn't contains  fileNameKey",p_session);
            return null;
        }
        
        
        
    }
      catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
    }
@PreDestroy
    public void destroyWriteBuffer(){
        boolean l_session_created_now=false;
        try{
       System.out.println("inside destroyWriteBuffer");
       session=new CohesiveSession();   
       dbSession=new DBSession(session);    
       session.createSessionObject();
         l_session_created_now=session.isI_session_created_now();
        dbSession.createDBsession(session);
//        dbg("inside destroyWriteBuffer",session);    
        IPhysicalIOService io=dbdi.getPhysicalIOService();
        Map<String, Map<String,DBRecord>>l_fileMap=null; 
//        writeBuffer = null;
        Iterator<String> fileIterator= writeBuffer.keySet().iterator();
        while(fileIterator.hasNext()){
            String   l_fileName=fileIterator.next();
            l_fileMap  = getClonedFileFromWriteBuffer(l_fileName, session, dbSession);
            io.physicalIOWriteFromDestroy(l_fileName,l_fileMap,session.getI_session_identifier());
        }
        
        ILockService lock=dbdi.getLockService();
             
             ArrayList<String>archivalFileNames=lock.getArchivalFileNames(session.getI_session_identifier());
             dbg("archivalFileNames size"+archivalFileNames.size(),session);
             
             IBDProperties i_db_properties=session.getCohesiveproperties();
             
             if(archivalFileNames!=null){
                 
                 for(int i=0;i<archivalFileNames.size();i++){
                     
                     String archivalFileName=archivalFileNames.get(i);
                     dbg("archivalFileName"+archivalFileName,session);
                     Path temp = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                     Path actual=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                     
                     dbg("temp path"+temp.toString(),session);
                     dbg("actual path"+actual.toString(),session);
                     
                     if(Files.exists(temp)){
                 
                         Files.copy(temp, actual,REPLACE_EXISTING);
                         dbg(temp+"  copied to  "+actual,session);
                     }
                    
                     if(Files.exists(temp)){
                         
                         Files.delete(temp);
                         dbg(temp+"  is deleted",session);
                     }
                     lock.releaseArchivalLock(archivalFileName, session, dbSession);
                 }
                 
                 
             }
//         dbg("end of destroyWriteBuffer",session);    
        writeBuffer = null;
        System.out.println("end of destroyWriteBuffer");
        }catch (DBValidationException ex) {
          dbg(ex,session);
        }catch (DBProcessingException ex) {
          dbg(ex,session);
        }catch (Exception ex) {
          dbg(ex,session);
       }finally{
           if(l_session_created_now){
               session.clearSessionObject();
               dbSession.clearSessionObject();
           }
       }
    }
    
//    @Lock(LockType.READ)
//     public Map<String, Map<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession session)throws  DBProcessingException{
//         
//         CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        
//        
//      return   getFileFromWriteBuffer(fileNameKey);
//        
//        
////    } catch(DBValidationException ex){
////           throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//           this.session=tempSession;
//     }
//         
//         
//         
//         
//         
//     }

//private String getPkeyWithVersion(String p_fileName,String p_tableName,String p_pkey,ArrayList<String>p_records,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException{
//    try{
//        dbg(" inside get pkey with version",p_session);
//        String pkeyWithVersion=null;
//        IBDFileUtil fileutil=p_dbSession.getIibd_file_util();
//        String l_fileType=fileutil.getFileType(p_fileName);
//        
//        
//        if(checkVersionAvailability(l_fileType,p_tableName,p_session)){
//            
//         int versionNo=   getVersionNumberFromTheRecord(l_fileType,p_tableName,p_records,p_session);
//         
//            pkeyWithVersion=p_pkey.concat("~").concat(Integer.toString(versionNo));
//         
//         
//        }else{
//            pkeyWithVersion=p_pkey;
//        }
//        
//        
//        return  pkeyWithVersion;
//        
//    }catch (Exception ex) {
//        dbg(ex,p_session);
//        throw new DBProcessingException("Exception" + ex.toString());
//    }
//}
//
//private boolean checkVersionAvailability(String p_fileType,String p_table_name,CohesiveSession p_session) throws DBProcessingException,DBValidationException{
//       boolean l_versionStatus=false;
//     try{  
//         dbg("inside check version availability",p_session);
//         dbg("check version availability-->fileType"+p_fileType,p_session);
//         dbg("check version availability-->p_table_name"+p_table_name,p_session);
//         IMetaDataService mds=dbdi.getMetadataservice();
//         Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(p_fileType, p_table_name,p_session).getI_ColumnCollection();
//            Iterator columnIterator=l_columnCollection.values().iterator();
//            while(columnIterator.hasNext()){
//             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
//             if(l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")){
//                 l_versionStatus=true;
//                 dbg("version status is true",p_session);
//             }else{
//                 l_versionStatus=false;
//                 dbg("version status is false",p_session);
//             }
//             if(l_versionStatus==true)
//                 break;
//            }
//            dbg("inside check version availability"+l_versionStatus,p_session);
//     }catch(DBValidationException ex){
//         throw ex;
//     }catch(DBProcessingException ex){
//         dbg(ex,p_session);
//       throw new DBProcessingException("DBProcessingException".concat(ex.toString()));
//     }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//      } 
//     return l_versionStatus;
//   }
//   
//   private int getVersionNumberFromTheRecord(String p_fileType,String p_table_name,ArrayList<String> p_records,CohesiveSession p_session)throws DBProcessingException,DBValidationException{
//       
//       try{
//           dbg("inside getVersionNumberFromTheRecord",p_session);
//           IMetaDataService mds=dbdi.getMetadataservice();
//           int versionColId= mds.getColumnMetaData(p_fileType, p_table_name, "VERSION_NUMBER").getI_ColumnID();
//
//           dbg("version number return from getVersionNumberFromTheRecord"+p_records.get(versionColId-1),p_session);
//         return Integer.parseInt(p_records.get(versionColId-1)) ;
//           
//      }catch(DBValidationException ex){
//         throw ex;
//     }catch(DBProcessingException ex){
//         dbg(ex,p_session);
//       throw new DBProcessingException("DBProcessingException".concat(ex.toString()));
//     }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//      } 
//       
//   }
    
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
