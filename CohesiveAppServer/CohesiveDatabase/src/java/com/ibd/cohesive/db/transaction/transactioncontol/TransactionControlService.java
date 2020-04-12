/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.transaction.transactioncontol;

//import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.tempSegment.IDBTempSegmentService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import com.ibd.cohesive.db.write.IDBWriteBufferService;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import java.util.HashMap;

/**
 *
 * @author IBD Technologies
 */
@Stateless
public class TransactionControlService implements ITransactionControlService {
    
    DBDependencyInjection dbdi;
    
    public TransactionControlService() throws NamingException
    {      
        dbdi=new DBDependencyInjection();    
    }       
    
    
    public void commit(CohesiveSession session,DBSession dbSession)throws DBProcessingException,DBValidationException{
//        IBDProperties i_db_properties=session.getCohesiveproperties();
        try{
            dbg("inside TransactionControlService--->commit method",session);
            ILockService lock=dbdi.getLockService();
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService write=dbdi.getDBWriteService();
            IPDataService pds=dbdi.getPdataservice();
//            Map<String,Map<String,Map<String,DBRecord>>>tempMap=new HashMap();
//            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            //ArrayList<String> l_filenames_to_be_commited=dbSession.getFileNames_To_Be_Commited();
           ArrayList<String> l_records_to_be_commited =lock.getImplictLockedRecords(session, dbSession);
            int l_size=l_records_to_be_commited.size();
            dbg("l_filenames_to_be_commited size in commit method"+l_size,session);      
//            dbg("Temp segment printing: starts ",session);
//            dbSession.getIibd_file_util().printTempBuffer(tempSegment.getTempSegmentMap());
//            dbg("Temp segment printing: ends ",session);
//           for(int i=0;i<l_size;i++){
//               
//                String[] l_recKey = l_records_to_be_commited.get(i).split("@");
//                String fileName=l_recKey[0];
//                String tableName=l_recKey[1];
//                String pkey=l_recKey[2];
//                dbg("filename in commit"+l_recKey[0],session);
//                dbg("tableName in commit"+l_recKey[1],session);
//                dbg("Pkey in commit"+l_recKey[2],session);
//                DBRecord tempRec=tempSegment.getRecordFromTempSegment(fileName, tableName, pkey, session, dbSession);
//               
//               
//               if(tempMap.containsKey(fileName)){
//                   
//                   if(tempMap.get(fileName).containsKey(tableName)){
//                       
//                       if(!tempMap.get(fileName).get(tableName).containsKey(pkey)){
//                           
//                           tempMap.get(fileName).get(tableName).put(pkey, tempRec);
//                       }
//                       
//                       
//                       
//                   }else{
//                       
//                       tempMap.get(fileName).put(tableName, new HashMap());
//                   
//                       tempMap.get(fileName).get(tableName).put(pkey, tempRec);
//                   }
//                   
//                   
//               }else{
//                   
//                   tempMap.put(fileName, new HashMap());
//                   
//                   tempMap.get(fileName).put(tableName, new HashMap());
//                   
//                   tempMap.get(fileName).get(tableName).put(pkey, tempRec);
//               }
//               
//               
//           }            
 
           
//           duplicateValidation(l_records_to_be_commited,session,dbSession,dbdi);
           
           
         
           
           for(int i=0;i<l_size;i++){
               
                String[] l_recKey = l_records_to_be_commited.get(i).split("@");
                String fileName=l_recKey[0];
                String tableName=l_recKey[1];
                String pkey=l_recKey[2];
                dbg("filename in commit"+l_recKey[0],session);
                dbg("tableName in commit"+l_recKey[1],session);
                dbg("Pkey in commit"+l_recKey[2],session);
               
                
                long startTime=dbSession.getIibd_file_util(). getStartTime();
                   
                
                
                if(readBuffer.validateDBReadBuffer(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession)){
                  
                  
                   readBuffer.SetRecordfromTempseg(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
                   
                   
                    try{
                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.readBuffer.SetRecordfromTempseg call",session,dbSession,dbdi,startTime);

                   }catch(Exception ex){
                       
                     throw new DBProcessingException("Exception".concat(ex.toString()));
                  }
                 
                }
//                    startTime=dbSession.getIibd_file_util(). getStartTime();
//                   pds.SetRecordfromTempseg(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
//                    try{
//                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.pdataservice.SetRecordfromTempseg call",session,dbSession,dbdi,startTime);
//
//                   }catch(Exception ex){
//                     throw new DBProcessingException("Exception".concat(ex.toString()));
//                  }
               
           }
           
           for(int i=0;i<l_size;i++){
               
                String[] l_recKey = l_records_to_be_commited.get(i).split("@");
                String fileName=l_recKey[0];
                String tableName=l_recKey[1];
                String pkey=l_recKey[2];
                dbg("filename in commit"+l_recKey[0],session);
                dbg("tableName in commit"+l_recKey[1],session);
                dbg("Pkey in commit"+l_recKey[2],session);
               
         
                    
                   long startTime=dbSession.getIibd_file_util(). getStartTime();
                   
                   if(pds.validatePData(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession)){
                   
                   
                   pds.SetRecordfromTempseg(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
                    try{
                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.pdataservice.SetRecordfromTempseg call",session,dbSession,dbdi,startTime);

                   }catch(Exception ex){
                     throw new DBProcessingException("Exception".concat(ex.toString()));
                  }
               
                    
                   }
           }
           

            
            for(int i=0;i<l_size;i++){
//            for(int i=0;i<l_filenames_to_be_commited.size();i++){
                String[] l_recKey = l_records_to_be_commited.get(i).split("@");
                
                
                String callerClassName = new Exception().getStackTrace()[1].getClassName();
                dbg("callerClassName in commit"+callerClassName,session);
                dbg("filename in commit"+l_recKey[0],session);
                dbg("tableName in commit"+l_recKey[1],session);
                dbg("Pkey in commit"+l_recKey[2],session);
                               
//            Path l_file_path = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+l_fileName+i_db_properties.getProperty("FILE_EXTENSION")); 
//            Path l_temp_file_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+dbSession.getIibd_file_util().getTempPath(l_fileName)+i_db_properties.getProperty("FILE_EXTENSION"));
//Map<String, Map<String,DBRecord>>l_readBuffer_fileMap=new HashMap();

                   //readBuffer.modifyBufferFromTempSegment(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
                   long startTime=dbSession.getIibd_file_util(). getStartTime();
                   if(write.validateWriteBuffer(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession)){
                   
                   
                   write.SetRecordfromTempseg(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
                   try{
                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.writeBuffer.SetRecordfromTempseg call",session,dbSession,dbdi,startTime);

                   }catch(Exception ex){
                     throw new DBProcessingException("Exception".concat(ex.toString()));
                  }
                   
                   }
                   
                   
                   
//                   startTime=dbSession.getIibd_file_util(). getStartTime();
//                   readBuffer.SetRecordfromTempseg(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
//                    try{
//                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.readBuffer.SetRecordfromTempseg call",session,dbSession,dbdi,startTime);
//
//                   }catch(Exception ex){
//                     throw new DBProcessingException("Exception".concat(ex.toString()));
//                  }
//                    
//                    startTime=dbSession.getIibd_file_util(). getStartTime();
//                   pds.SetRecordfromTempseg(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
//                    try{
//                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.pdataservice.SetRecordfromTempseg call",session,dbSession,dbdi,startTime);
//
//                   }catch(Exception ex){
//                     throw new DBProcessingException("Exception".concat(ex.toString()));
//                  }
                    
                    
                  // write.modifyBufferFromTempSegment(l_fileName, session,dbSession);
                  
//                  startTime=dbSession.getIibd_file_util(). getStartTime();
//                   write.SetRecordfromTempseg(l_recKey[0],l_recKey[1],l_recKey[2],session,dbSession);
//                   try{
//                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.writeBuffer.SetRecordfromTempseg call",session,dbSession,dbdi,startTime);
//
//                   }catch(Exception ex){
//                     throw new DBProcessingException("Exception".concat(ex.toString()));
//                  }
//            Map<String, Map<String,DBRecord>>l_readBuffer_fileMap=readBuffer.getFileFromBuffer(l_fileName, session);
//           if(l_readBuffer_fileMap!=null){
//               
//               
//               Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(l_fileName,session);
//            if(l_tempSegFileMap!=null){   
//               Iterator<String> tempSegTableIterator=l_tempSegFileMap.keySet().iterator();
//               
//               while(tempSegTableIterator.hasNext()){
//                   String l_tableName=tempSegTableIterator.next();
//                   if(l_readBuffer_fileMap.containsKey(l_tableName)){
//                      Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
//                      
//                      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
//                      while(tempSegRecordIterator.hasNext()){
//                          String l_tempSegRecPKey=tempSegRecordIterator.next();
//                           DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
//                          if(l_readBuffer_fileMap.get(l_tableName).containsKey(l_tempSegRecPKey)){
//                            if(l_tempSegDBRecord.getOperation()=='D'){
//                              l_readBuffer_fileMap.get(l_tableName).remove(l_tempSegRecPKey);
//                              }else if(l_tempSegDBRecord.getOperation()=='U'){
//                                  l_readBuffer_fileMap.get(l_tableName).replace(l_tempSegRecPKey,l_tempSegDBRecord);
//                              }
//                          }else if(l_tempSegDBRecord.getOperation()=='C'){
//                              l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempSegRecPKey, l_tempSegDBRecord);
//                              
//                          }
//                          
//                      }
//                   }else {
//                       l_readBuffer_fileMap.putIfAbsent(l_tableName, new HashMap());
//                        Map<String,DBRecord>l_tempSegTableMap= l_tempSegFileMap.get(l_tableName);
//                      
//                      Iterator<String> tempSegRecordIterator=l_tempSegTableMap.keySet().iterator();
//                      while(tempSegRecordIterator.hasNext()){
//                       String l_tempSegRecPKey=tempSegRecordIterator.next();
//                       DBRecord l_tempSegDBRecord=l_tempSegTableMap.get(l_tempSegRecPKey);
//                       if(l_tempSegDBRecord.getOperation()!='D')
//                       l_readBuffer_fileMap.get(l_tableName).putIfAbsent(l_tempSegRecPKey, l_tempSegDBRecord);
//                      }
//                   }
//                   
//               }
//            }
////            Files.copy(l_temp_file_path, l_file_path,REPLACE_EXISTING);
//           }
            //lock.releaseLock(l_filenames_to_be_commited.get(i).toString(), session);
            
           // dbSession.deleteFileName_in_commit(l_filenames_to_be_commited.get(i).toString());
           startTime=dbSession.getIibd_file_util(). getStartTime();
            tempSegment.removeRecordFromTempSegment(l_recKey[0], l_recKey[1], l_recKey[2], session, dbSession);
            try{
                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.tempSegment.removeRecordFromTempSegment call",session,dbSession,dbdi,startTime);

                   }catch(Exception ex){
                     throw new DBProcessingException("Exception".concat(ex.toString()));
                  }
            
            startTime=dbSession.getIibd_file_util(). getStartTime();
            lock.releaseRecordLock(l_recKey[0], l_recKey[1], l_recKey[2], session);
             try{
                     dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactionControlService","commit.lock.releaseRecordLock call",session,dbSession,dbdi,startTime);

                   }catch(Exception ex){
                     throw new DBProcessingException("Exception".concat(ex.toString()));
                  }
            // tempSegment.getTempSegmentMap().remove(l_fileName);
           //  tempSegment.getTempSegmentMap().get(l_recKey[0]).get(l_recKey[1]).remove(l_recKey[2]);
           
//           Iterator<String> fileNameIterator= tempSegment.getTempSegmentMap().keySet().iterator();
//           while(fileNameIterator.hasNext()){
//               String fileName=fileNameIterator.next();
//                      dbg("File Name;"+fileName,session);
//                    Iterator<String> tableNameIterator=tempSegment.getTempSegmentMap().get(fileName).keySet().iterator();
//                   while(tableNameIterator.hasNext()) {
//                       String tableName=tableNameIterator.next();
//                       dbg("    tableName"+tableNameIterator.next(),session);
//                       Iterator<String> pkeyIterator=tempSegment.getTempSegmentMap().get(fileName).get(tableName).keySet().iterator();
//                          while(pkeyIterator.hasNext()){
//                              String primaryKey=pkeyIterator.next();
//                              dbg("      primaryKey"+primaryKey,session);
//                               DBRecord dbrec=  tempSegment.getTempSegmentMap().get(fileName).get(tableName).get(primaryKey);
//                               dbg("        operation"+dbrec.getOperation(),session);
//                               dbg("        postion"+dbrec.getPosition(),session);
//                               for(String s: dbrec.getRecord()){
//                                   dbg("    record values"+s,session);
//                               }
//                               
//                          }
//                   }
//               
//               
//           }
            
            }
//          Iterator<String>fileNameIterator= readBuffer.getReadBufferMap().keySet().iterator();
//             dbg("DBBuffer printing: starts ",session);
//            while(fileNameIterator.hasNext()){
//                String fileName=fileNameIterator.next();
//                dbg("fileName"+fileName,session);
//            dbSession.getIibd_file_util().printDBBuffer( readBuffer.getFileFromBuffer(fileName, session,dbSession));
//            
//            }
//             dbg("DBBuffer printing: ends ",session);
            
             
//             dbg("Write buffer printing starts",session);
//             dbSession.getIibd_file_util().printWriteBuffer(write.getWriteBuffer());
//             dbg("Write buffer printing ends",session);
             
            
         dbg("end of TransactionControlService--->commit method",session);   
        }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
           }catch(InvalidPathException  ex)
           {
           dbg(ex,session);
           throw new DBProcessingException("InvalidPathException  " + ex.toString()); 
//        }catch(FileAlreadyExistsException ex)
//           {
//           dbg(ex,session);
//           throw new DBProcessingException("FileAlreadyExistsException " + ex.toString()); 
//        }catch(DirectoryNotEmptyException ex)
//           {
//           dbg(ex,session);
//           throw new DBProcessingException("DirectoryNotEmptyException" + ex.toString());  
//        }catch(IOException ex)
//           {
//           dbg(ex,session);
//           throw new DBProcessingException("IOException" + ex.toString());  
        }catch(SecurityException ex)
           {
           dbg(ex,session);
           throw new DBProcessingException("SecurityException" + ex.toString());  
        }catch(UnsupportedOperationException  ex)
           {
           dbg(ex,session);
           throw new DBProcessingException("UnsupportedOperationException " + ex.toString());  
        }
        catch(NamingException ex)
           {
           dbg(ex,session);
           throw new DBProcessingException("UnsupportedOperationException " + ex.toString());  
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
           dbg(ex,session);
           throw new DBProcessingException("Exception" + ex.toString());
        }finally{
            
        }
    
}
    
 public void rollBack(CohesiveSession session,DBSession dbSession)throws DBProcessingException{
      IBDProperties i_db_properties=session.getCohesiveproperties();
      
      try{
         ILockService lock=dbdi.getLockService();
       dbg("inside TransactionControlService--->rollback method",session);
//      Path l_temp_file_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+p_dbdi.getIibd_file_util().getTempPath(p_fileName)+i_db_properties.getProperty("FILE_EXTENSION"));
//     
//     
//          if(Files.exists(l_temp_file_path)){
//              Files.delete(l_temp_file_path);
//              lock.releaseLock(p_fileName, p_dbdi);
//          }
       ArrayList<String> l_records_to_be_commited =lock.getImplictLockedRecords(session, dbSession);
//       ArrayList<String> l_filenames_to_be_commited=dbSession.getFileNames_To_Be_Commited();
          int l_size=l_records_to_be_commited.size();
//       int l_size=l_filenames_to_be_commited.size();
       dbg("l_records_to_be_commited size in rollback method"+l_size,session); 
       for(int i=0;i<l_size;i++){
           
           String[] l_recKey = l_records_to_be_commited.get(i).split("@");
                
                
                String callerClassName = new Exception().getStackTrace()[1].getClassName();
                dbg("callerClassName in rollback"+callerClassName,session);
                dbg("filename in rollback"+l_recKey[0],session);
                dbg("tableName in rollback"+l_recKey[1],session);
                dbg("Pkey in rollback"+l_recKey[2],session);
           
           
//       for(int i=0;i<l_filenames_to_be_commited.size();i++){
//String l_fileName=l_filenames_to_be_commited.get(i);
//        dbg("filename in rollback"+l_filenames_to_be_commited.get(i),session);
//       Path l_temp_file_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+dbSession.getIibd_file_util().getTempPath(l_filenames_to_be_commited.get(i).toString())+i_db_properties.getProperty("FILE_EXTENSION"));
                   IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
                   IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
                    long startTime=dbSession.getIibd_file_util(). getStartTime();
//                    readBuffer.removeFileFromBuffer(l_recKey[0], session, dbSession);

                  try{

                    tempSegment.removeRecordFromTempSegment(l_recKey[0], l_recKey[1], l_recKey[2], session, dbSession);
                 
                   }catch(DBValidationException ex){
                       if(ex.toString().contains("DB_VAL_018")){
                           
                           session.getErrorhandler().removeSessionErrCode("DB_VAL_018");
                           
                       }else{
                           throw ex;
                       }
                   } 
                    
                    
                   try{
                      dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactioncontrolService","rollBack.tempSegment.removeRecordFromTempSegment",session,dbSession,dbdi,startTime);

                   }catch(Exception ex){
                       throw new DBProcessingException("Exception".concat(ex.toString()));
                   }
                   
                   
                   startTime=dbSession.getIibd_file_util(). getStartTime();
                   
                   lock.releaseRecordLock(l_recKey[0], l_recKey[1], l_recKey[2], session);
                   
                   try{
                      dbSession.getIibd_file_util().logWaitBuffer(l_recKey[0],l_recKey[1],l_recKey[2],"TransactioncontrolService","rollBack.lock.releaseRecordLock",session,dbSession,dbdi,startTime);

                   }catch(Exception ex){
                       throw new DBProcessingException("Exception".concat(ex.toString()));
                   }
//      Map<String, Map<String,DBRecord>>l_tempSegFileMap=tempSegment.getFileFromTempSegment(l_fileName,session,dbSession);
//          if(l_tempSegFileMap!=null){ 
////              Files.delete(l_temp_file_path);
//                l_tempSegFileMap.remove(l_fileName);
//              lock.releaseLock(l_filenames_to_be_commited.get(i).toString(), session);
//          }
//          dbSession.deleteFileName_in_commit(l_filenames_to_be_commited.get(i).toString());
       }
       
       lock.releaseSessionRecordLock(session);
       dbg("end of TransactionControlService--->rollback method",session); 
       }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
//           }catch(NoSuchFileException  ex)
//            {
//            dbg(ex,session);
//            throw new DBProcessingException("NoSuchFileException  " + ex.toString()); 
        }catch(InvalidPathException  ex)
            {
            dbg(ex,session);
            throw new DBProcessingException("InvalidPathException  " + ex.toString()); 
//        }catch(FileAlreadyExistsException ex)
//            {
//            dbg(ex,session);
//            throw new DBProcessingException("FileAlreadyExistsException " + ex.toString()); 
//        }catch(DirectoryNotEmptyException ex)
//            {
//            dbg(ex,session);
//            throw new DBProcessingException("DirectoryNotEmptyException" + ex.toString());  
//        }catch(IOException ex)
//            {
//            dbg(ex,session);
//            throw new DBProcessingException("IOException" + ex.toString());  
        }catch(SecurityException ex)
            {
            dbg(ex,session);
            throw new DBProcessingException("SecurityException" + ex.toString());  
        }catch(UnsupportedOperationException  ex)
            {
            dbg(ex,session);
            throw new DBProcessingException("UnsupportedOperationException " + ex.toString());  
        
      
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());
        }
     
     
     
 }   
    
    
    
    
    public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
    
    
    
    private void duplicateValidation(ArrayList<String>l_records_to_be_commited,CohesiveSession session,DBSession dbSession,DBDependencyInjection dbdi)throws DBProcessingException{
        try{
            dbg("inside duplicateValidation",session);
           IDBReadBufferService readBuffer= dbdi.getDBReadBufferService();
           IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            
            for(int i=0;i<l_records_to_be_commited.size();i++){
                
                String[] l_recKey = l_records_to_be_commited.get(i).split("@");
                String fileName=l_recKey[0];
                String tableName=l_recKey[1];
                String pkey=l_recKey[2];
                dbg("fileName"+fileName,session);
                dbg("tableName"+tableName,session);
                dbg("pkey"+pkey,session);
                String fileType=dbSession.getIibd_file_util().getFileType(fileName);
                boolean duplicateExistence=false;
                DBRecord tempRec=tempSegment.getRecordFromTempSegment(fileName, tableName, pkey, session, dbSession);
                
                if(tempRec.getOperation()=='C'){
                
                try{
                
                  String substring= fileName.substring(fileName.length()-4, fileName.length());  
                 dbg("substring"+substring,session);
                  if(!substring.equals("_LOG")){
                  
                    readBuffer.readRecord(fileName, fileType, tableName, pkey.split("~"), session, dbSession);
                 
                  }
                  duplicateExistence=false;
                }catch(DBValidationException ex){
                    
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        duplicateExistence=false;
                    }else{
                        
                        duplicateExistence=true;
                    }
                    
                    
                }
                
               if(duplicateExistence==true){
                   
                   StringBuffer single_error_code = new StringBuffer();
                   String replacedPkey=pkey.replace("~", "@");
                   single_error_code = single_error_code.append("DB_VAL_017".concat("," + tableName).concat("," + replacedPkey));
                   session.getErrorhandler().setSingle_err_code(single_error_code);
                   session.getErrorhandler().log_error();
                   throw new DBValidationException(session.getErrorhandler().getSession_error_code().toString()); 
                   
               }
             }
            }
            
            dbg("end of duplicateValidation",session);
        } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());
        }
    }
    
    
}