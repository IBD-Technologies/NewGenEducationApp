  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.io;

import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.DBFile;
import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.PositionAndRecord;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.regex.PatternSyntaxException;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.DependsOn;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
@DependsOn({"LockService","DBWaitBuffer","MetaDataService"})
@Stateless
//@Singleton
//@ConcurrencyManagement(BEAN)
public class PhysicalIOService implements IPhysicalIOService {
    
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
//    int instanceID;
//    UUID archivalSeqNo;
    String archivalFileName;
    int archivalFileLogSize;
    FileChannel archivalFC;
    ArrayList<String> archivalFileNames;
    public PhysicalIOService() throws NamingException,DBProcessingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
        
       archivalFileName= null;
      
//        archivalSeqNo=CohesiveSession.dataToUUID();
    }
    
//    @Lock(LockType.READ)
    public  Map<String, Map<String,DBRecord>> physicalIORead(String p_fileName,String p_fileType)throws DBValidationException,DBProcessingException{
         Map<String, Map<String,DBRecord>>l_fileMap=null;
        Scanner l_File_Content = null;
        boolean l_session_created_now=false;
        boolean exceptionRaised=false;
//        long startTime=0;
          try {
        
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler = session.getErrorhandler();
        DBValidation dbv = dbSession.getDbv();
        ILockService lock=dbdi.getLockService();
        IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
//        startTime=dbSession.getIibd_file_util(). getStartTime();
//        IDBTransactionService dbts=dbdi.getDBTransactionService();
         if(getIOLock(p_fileName, session, dbSession)==true){       
             
             if(lock.isSameSessionIOLock(p_fileName, session, dbSession)){
        
                 if(readBuffer.checkFileExistenceInReadBuffer(p_fileName, session)){
                     
                    return readBuffer.getFileFromBuffer(p_fileName, session, dbSession);
                     
                 }
                 
                 
        boolean l_validation_status = true;
        dbg("inside PhysicalIOService--->physicalIORead");
        dbg("inside PhysicalIOService--->physicalIORead--->fileName--->"+p_fileName);
        dbg("inside PhysicalIOService--->physicalIORead--->fileName--->"+p_fileType);
        
        
        
                if (!dbv.specialCharacterValidation(p_fileName, errorhandler)) {
                    l_validation_status = false;
                    dbg("validation status is false");
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_fileName, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.fileTypeValidation(p_fileType, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                
                if (!l_validation_status) {
                    throw new DBValidationException(errorhandler.getSession_error_code().toString());

                }
                
                l_fileMap=new HashMap();
               long startTime1=dbSession.getIibd_file_util(). getStartTime();
                 try{
                    l_fileMap = dbSession.getIibd_file_util().sequentialRead(p_fileName,session,dbdi);
                    
                }catch(FileNotFoundException ex){
                    dbg("Physical IOService file not found exception");
                    StringBuffer single_error_code = new StringBuffer();
                    single_error_code = single_error_code.append("DB_VAL_000");
                    errorhandler.setSingle_err_code(single_error_code);
                    errorhandler.log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }
                 finally{
                     try{
                  dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PHysicalIOService","physicalIORead.sequentialRead",session,dbSession,dbdi,startTime1);

                  }catch(Exception ex){
                       throw new DBProcessingException("Exception".concat(ex.toString()));
                    }
                 }
                
                dbg("printing of physicalIORead starts");  
                Map<String, Map<String,DBRecord>>l_fileMapp= l_fileMap;
                 
                 Iterator<String> tableNameIterator=l_fileMapp.keySet().iterator();
                   while(tableNameIterator.hasNext()) {
                       String tableName=tableNameIterator.next();
                       dbg("    tableName"+tableName);
                       Iterator<String> pkeyIterator=l_fileMapp.get(tableName).keySet().iterator();
                          while(pkeyIterator.hasNext()){
                              String primaryKey=pkeyIterator.next();
                              dbg("      primaryKey"+primaryKey);
                               DBRecord dbrec=  l_fileMapp.get(tableName).get(primaryKey);
                               dbg("        operation"+dbrec.getOperation());
                               dbg("        postion"+dbrec.getPosition());
                               for(String s: dbrec.getRecord()){
                                   dbg("    record values"+s);
                               }
                               
                          }
                   }
                
                dbg("printing of physicalIORead ends");
                
//            }
//            else{
//                throw new DBValidationException("DB-VAL-000");
//            }
            dbg("end of physicalIORead read");
             
             }
         }
         
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            throw new DBProcessingException("ClassCastException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalStateException" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
         /*catch (FileNotFoundException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        }
        } */ 
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
              if(l_session_created_now)
            {    
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
            }
              
            if (l_File_Content != null) {
                l_File_Content.close();
            }
            
            //Lock release moved to read buffer
//            try{
//            if(dbdi.getLockService().isSameSessionIOLock(p_fileName, session, dbSession)){
//                
//                dbdi.getLockService().releaseIOLock(p_fileName, session, dbSession);
//            }
//            
//            }catch(Exception ex){
//                dbg(ex);
//                throw new DBProcessingException("Exception" + ex.toString());
//            }
            
           
        }
          return l_fileMap;

    }
      
    public  Map<String, Map<String,DBRecord>> physicalIORead(String p_fileName,String p_fileType,CohesiveSession p_session)throws DBValidationException,DBProcessingException{
        CohesiveSession tempSession = this.session;
        try{
            this.session=p_session;
            return physicalIORead(p_fileName,p_fileType);
            
        }catch(DBValidationException ex){
            throw ex;
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());    
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        finally{
           this.session=tempSession;
        }
    }
    
@Asynchronous
   public Future<String> physicalIOWrite(String p_fileName,Map<String,Map<String,DBRecord>> p_fileMap,UUID writeSessionID)throws DBValidationException,DBProcessingException{
        
        boolean exceptionRaised=false;
        IBDFileUtil fileUtil =null;//Integration change
        boolean l_session_created_now=false;
        FileChannel fc= null;
        long startTime=0;
        IBDProperties i_db_properties=null;
          try {
        
        session.createSessionObject();
        dbSession.createDBsession(session);
        archivalFileNames =new ArrayList();
        l_session_created_now=session.isI_session_created_now();
        
        IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
        readBuffer.setFromWriteBuffer(p_fileMap, p_fileName, session, dbSession);
        
        if(archivalFileName==null){
           archivalFileName= createArchivalFileName(writeSessionID);
           
        }
        
        archivalFileLogSize= Integer.parseInt(session.getCohesiveproperties().getProperty("ARCH_FILE_SIZE"));
//        ErrorHandler errorhandler = session.getErrorhandler();
//        DBValidation dbv = dbSession.getDbv();
        IMetaDataService mds=dbdi.getMetadataservice();
        startTime=dbSession.getIibd_file_util(). getStartTime();
//        boolean l_validation_status = true;
        dbg("inside physicalIOService--->physical write");
        String l_record;
//        IDBWriteBufferService write=dbdi.getDBWriteService();
        i_db_properties=session.getCohesiveproperties();
        fileUtil=dbSession.getIibd_file_util();//Integration changes
        ILockService lock=dbdi.getLockService();
//       Iterator<String> fileIterator= write.getWriteBuffer().keySet().iterator();
//       Map<String, String> l_emptyMap = new HashMap<>();
        ArrayList<String>l_emptyList=new ArrayList();
//       while(fileIterator.hasNext()){
//           String fileName=fileIterator.next();
          Path parentPath=Paths.get(p_fileName).getParent();
          Files.createDirectories(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+parentPath));
          String l_tempPath=fileUtil.getTempPath(p_fileName);
          long startingtime1=dbSession.getIibd_file_util(). getStartTime();
         
          
          
          
          fileUtil.deleteTempFile(p_fileName);
          
          try{
               
               dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.deleteTempFile call",session,dbSession,dbdi,startingtime1);

          }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
          }
          
           long startingtime2=dbSession.getIibd_file_util(). getStartTime();
           fileUtil.copyFileToTemp(Paths.get(p_fileName),Paths.get(l_tempPath));
          
          try{
               
             dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.copyFileToTemp call",session,dbSession,dbdi,startingtime2);

             }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
             }
          
          long startingtime3=dbSession.getIibd_file_util(). getStartTime();
          Iterator<String>tableIterator= p_fileMap.keySet().iterator();
          while(tableIterator.hasNext()){
               
                Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + l_tempPath + i_db_properties.getProperty("FILE_EXTENSION"));
                if(fc==null||!fc.isOpen()){
                  dbg("opening file channel in create operation for the file name"+p_fileName);  
                fc = FileChannel.open(file, CREATE, WRITE, APPEND);
                }
                String l_tableName=tableIterator.next();
                Iterator<String> pkIterator=p_fileMap.get(l_tableName).keySet().iterator();//iteration for performing create
                while(pkIterator.hasNext()){
                    String l_primaryKey=pkIterator.next();
                    DBRecord dbRec=p_fileMap.get(l_tableName).get(l_primaryKey);
                    l_record = getRecord(p_fileName, l_tableName, dbRec.getRecord());
                    dbg("l_record"+l_record);
                    if(dbRec.getOperation()=='C'){
                        dbg("inside physicalIOWrite operation is create");
                        fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                        dbg("before archival write call");
                        archivalWrite(p_fileName,dbRec.getOperation(),l_record,dbRec.getPosition(),writeSessionID);
                        dbg("after archival write call");
                    }
               }
           }
           if(fc!=null){
               dbg("closing file channel in create operation for the file name"+p_fileName);
               if(fc.isOpen()){
               fc.close();
               }
               fc=null;
           }
           
               try{
               
                 dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.write createRecord call",session,dbSession,dbdi,startingtime3);

               }catch(Exception ex){
                  throw new DBProcessingException("Exception".concat(ex.toString()));
               }
           
           
           long startingtime4=dbSession.getIibd_file_util(). getStartTime();
           Iterator<String>tableIterator1= p_fileMap.keySet().iterator();
           while(tableIterator1.hasNext()){
               Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + l_tempPath + i_db_properties.getProperty("FILE_EXTENSION"));
               if(fc==null||!fc.isOpen()){
                  dbg("opening file channel in update or delete operation for the file name"+p_fileName);
               fc = FileChannel.open(file, CREATE, WRITE);
               }
               String l_tableName=tableIterator1.next();
               Iterator<String> pkIterator=p_fileMap.get(l_tableName).keySet().iterator();//iteration for performing create
               while(pkIterator.hasNext()){
                    String l_primaryKey=pkIterator.next();
                    DBRecord dbRec=p_fileMap.get(l_tableName).get(l_primaryKey);
                    if(dbRec.getOperation()=='D'){
                        l_emptyList=new ArrayList();
                        dbg("inside physicalIOWrite operation is Delete");
                        String fileType= getFileType(p_fileName);
                        int l_size = mds.getTableMetaData(fileType, l_tableName, session).getI_ColumnCollection().size();
                        dbg("in db transaction service->delete record->l_size"+l_size);
                        String[] l_sample = new String[l_size];
                        int   i = 0;
                        while (i < l_size) {
                           int length = mds.getColumnMetaData(fileType, l_tableName, i + 1, session).getI_ColumnLength();
                           dbg("columnName"+mds.getColumnMetaData(fileType, l_tableName, i + 1, session).getI_ColumnName());
                           dbg("column length"+length);
                               int j = 1;
                               while (j <= length) {
                               if (j == 1) {
                                  l_sample[i] = " ";
                                  j++;
                               } else {
                                  l_sample[i] = l_sample[i] + " ";
                                  j++;
                               }
                        }
                   dbg("The sample"+i + l_sample[i] + "is");
                   l_emptyList.add(l_sample[i]);
                   i++;
                   }              
                    l_record = getRecord(p_fileName, l_tableName, l_emptyList); 
                    l_record = l_record.replace("~", " ");
                    dbg("l_record"+l_record);
                    
                    if(dbRec.getPosition()==0){
                       String[]   l_pkeyArr=  l_primaryKey.split("~");
                       Map<String, String> l_dummy_column = new HashMap();
                       String l_table_id = String.valueOf(mds.getTableMetaData(fileType, l_tableName, session).getI_Tableid());
                       PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_tempPath, Integer.parseInt(l_table_id), l_pkeyArr, l_dummy_column, session,dbdi);
                       dbg("in physicalIO service ->write->par.getI_position()" + par.getI_position());
                       long l_position = par.getI_position();
                       dbg("position in delete");
                       fc.position(l_position);
                       fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                       dbg("before archival write call");
                       archivalWrite(p_fileName,dbRec.getOperation(),l_record,l_position,writeSessionID);
                       dbg("after archival write call");   
                    }else{
                       fc.position(dbRec.getPosition());
                       fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                       dbg("before archival write call");
                       archivalWrite(p_fileName,dbRec.getOperation(),l_record,dbRec.getPosition(),writeSessionID);
                       dbg("after archival write call");
                    }
                  }else if(dbRec.getOperation()!='C'){
                      dbg("inside physicalIOWrite operation is"+dbRec.getOperation());
                      dbg("inside physicalIOWrite Position"+dbRec.getPosition());
//                       dbRec=write.getWriteBuffer().get(fileName).get(l_tableName).get(l_primaryKey);
                    l_record = getRecord(p_fileName, l_tableName, dbRec.getRecord());
                    if(dbRec.getPosition()==0&&dbRec.getOperation()=='U'){
                          dbg("operation is update and position is 0");
                          if(fc!=null){
                             dbg("closing file channel in operation is update and position is 0"+p_fileName);
                               if(fc.isOpen()){
                                   fc.close();
                                 }
                          }
                          String fileType= getFileType(p_fileName);
                          dbg("fileType"+fileType);
                          Map<String, String> l_dummy_column = new HashMap();
                          String l_table_id = String.valueOf(mds.getTableMetaData(fileType, l_tableName, session).getI_Tableid());
//                          boolean l_versionAvailability=checkVersionNoAvailability(fileType, l_tableName);
                          String[] l_pkeyArr;
                          
//                          if(l_versionAvailability==true){
//                              
//                            l_pkeyArr=  getPkeyWithVersion(fileType,l_tableName,l_primaryKey,dbRec.getRecord());
//                              
//                          }else{
                           l_pkeyArr=  l_primaryKey.split("~");
//                          }
                         
                          
                          PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_tempPath, Integer.parseInt(l_table_id), l_pkeyArr, l_dummy_column, session,dbdi);
                          dbg("in physicalIO service ->write->par.getI_position()" + par.getI_position());
                          long l_position = par.getI_position();
                          dbg("in physicalIO service ->write->par.getI_Records size()" + par.getI_records().size());
                          par.getI_records().size();
                          dbg("l_record"+l_record);
                          dbg("position"+l_position);
                          
                          
                          
                          if(l_position==0&&par.getI_records().size()==0){
                              if(fc==null||!fc.isOpen()){
                                 fc = FileChannel.open(file, CREATE, WRITE,APPEND);
                             }
                              
                              fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                              dbg("before archival write call in update position 0");
                               archivalWrite(p_fileName,'C',l_record,l_position,writeSessionID);
                               dbg("after archival write call in update position 0");
                              
                          }else{
                              
                              if(fc==null||!fc.isOpen()){
                                 dbg("opening file channel in update or delete operation for the file name"+p_fileName);
                                 fc = FileChannel.open(file, CREATE, WRITE);
                              }
                                 fc.position(l_position);
                                 fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                                  archivalWrite(p_fileName,dbRec.getOperation(),l_record,l_position,writeSessionID);
                          }
                          
                          
                           
                        
//                          dbg("before archival write call");
//                          archivalWrite(p_fileName,dbRec.getOperation(),l_record,l_position);
////                          archivalWrite(p_fileName,dbRec.getOperation(),l_record,dbRec.getPosition());
//                          dbg("after archival write call");
              
                          
                          
                    }else{
                        dbg("operation is U position not zero");
                         if(fc.isOpen()){
                             dbg("closing fileChannel");
                             fc.close();
                           }
                        
                         if(fc==null||!fc.isOpen()){
                             dbg("opening file channel in update  for the file name"+p_fileName);
                             fc = FileChannel.open(file, CREATE, WRITE);
                        }
                        
                        
                          dbg("l_record"+l_record);
                          fc.position(dbRec.getPosition());
                          fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                          dbg("before archival write call");
                          archivalWrite(p_fileName,dbRec.getOperation(),l_record,dbRec.getPosition(),writeSessionID);
                          dbg("after archival write call");
                    }
                    
                  }
      
               }
               
           }
           if(fc!=null){
               dbg("closing file channel in create operation for the file name"+p_fileName);
               if(fc.isOpen()){
               fc.close();
               }
               fc=null;
           }
        dbg("file channel is closed");
        
        
        
        
        
        try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.write update call",session,dbSession,dbdi,startingtime4);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
        
        
        
//         if(getIOLock(p_fileName, session, dbSession)==true){       
//              dbg("get lock is true");
//             if(lock.isSameSessionIOLock(p_fileName, session, dbSession)){
//                 dbg("same session lock is true");
//                 
//                 long startingtime5=dbSession.getIibd_file_util(). getStartTime();
//               copyFileToActual(l_tempPath,p_fileName);
//               try{
//               
//           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite copyFileToActual call",session,dbSession,dbdi,startingtime5);
//
//           }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//           }
//             }
//         }
        
        
         dbg("end physicalIOService--->physical write");
         
        return new AsyncResult<String>("Success");
         }catch (DBProcessingException ex) {
            dbg(ex);
            dbg("inside dbProcessing exception");
            exceptionRaised=true;
        }catch (Exception ex) {
            dbg(ex);
            dbg("inside exception");
            exceptionRaised=true;
        }
          
   finally{
          try{
             if(archivalFC!=null) {
               if(archivalFC.isOpen()){
                   
                  archivalFC.close();
                  ILockService lock=dbdi.getLockService();
//                  lock.releaseArchivalLock(archivalFileName, session, dbSession);
                  //archivalFileName=null;
               }
             }
             archivalFileName=null;
            // fileUtil.deleteTempFile(p_fileName); //Integration changes
             if(fc!=null){
              if(fc.isOpen()){
                  fc.close();
              }
             }
         }catch (Exception ex) {
            dbg(ex);
            dbg("inside exception");
            exceptionRaised=true;  
         }     
              
         try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite",session,dbSession,dbdi,startTime);

         }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
         }finally{
              try{
                  
                if(dbdi.getLockService().isSameSessionIOLock(p_fileName, session, dbSession)){
                      dbdi.getLockService().releaseIOLock(p_fileName, session, dbSession);
                 }
            }catch(Exception ex){
                dbg(ex);
                dbg("inside exception releaseIOLock");
                exceptionRaised=true;
            }
           dbg("end of finally block");
        }   
            if(exceptionRaised){
                dbg("returning fail ");
//                 if(l_session_created_now){ 
//                     
//                         session.clearSessionObject();
//                         dbSession.clearSessionObject();
//                 }
                 
                   for(int i=0;i<archivalFileNames.size();i++){
                     
                     String archivalFileName=archivalFileNames.get(i);
                     dbg("archivalFileName"+archivalFileName);
                     Path temp = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                     
                     dbg("temp path"+temp.toString());
                    
                     if(Files.exists(temp)){
                         
                         try{
                         
                         Files.delete(temp);
                         
                         }catch(IOException ex){
                             
                         }
                         dbg(temp+"  is deleted");
                     }
//                     lock.releaseArchivalLock(archivalFileName, session, dbSession);
                 }
                 
                 
                 
                 archivalFileNames=null;
                 if(l_session_created_now){ 
                     
                         session.clearSessionObject();
                         dbSession.clearSessionObject();
                 }
            return new AsyncResult<String>("Fail");
           }else{
                dbg("returning Success");
                if(l_session_created_now)    {    
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
            }
                archivalFileNames=null;
               return new AsyncResult<String>("Success");
           }
            
            
          }  
            
    }
    
//   @Asynchronous
   public  Future<String> physicalIOWrite(String p_fileName,Map<String,Map<String,DBRecord>> p_fileMap,CohesiveSession p_session,UUID writeSessionID)throws DBValidationException,DBProcessingException{
        CohesiveSession tempSession = this.session;
        try{
            this.session=p_session;
          return  physicalIOWrite(p_fileName,p_fileMap,writeSessionID);
            
        }catch(DBValidationException ex){
            throw ex;
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());    
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        finally{
           this.session=tempSession;
        }
    }
    
    private String getRecord(String p_fileName,String p_tableName,ArrayList<String>p_recordValues)throws DBProcessingException{
        String l_record = null;
        try{
           String[] l_record_values = new String[p_recordValues.size()];
            Map<String, DBColumn> l_column_collection = new HashMap();
            for (int i = 0; i < p_recordValues.size(); i++) {
                l_record_values[i] = p_recordValues.get(i);
            }
            IMetaDataService mds=dbdi.getMetadataservice();
            dbg("in PhysicalIOService ->getRecord->p_fileName" + p_fileName);
            dbg("in PhysicalIOService ->getRecord->p_tableName" + p_tableName);
            dbg("in PhysicalIOService ->getRecord->p_recordValues.size"+p_recordValues.size());
            dbg("in PhysicalIOService ->getRecord->p_recordValues.size"+l_record_values.length);
            
            for(int i=0;i<p_recordValues.size();i++){
                
                dbg("p_recordValues"+i+p_recordValues.get(i));
                
            }
            
            for(int i=0;i<l_record_values.length;i++){
                dbg("l_record_values"+i+l_record_values[i]);
            }
            
           String fileType= getFileType(p_fileName);
//            File file=new File(p_fileName);
//            File parentFile=new File(file.getParent());
//            String fileType=new File(parentFile.getParent()).getName();
            
            dbg("fileType"+fileType);
            
            int l_tableID=mds.getTableMetaData(fileType, p_tableName, session).getI_Tableid();
            
            l_record = "#";
            l_record = l_record.concat(String.valueOf(l_tableID));
            IDBCoreService l_dbcs = dbdi.getDbcoreservice();//EJB Integration change
            
            Iterator iterator1=l_dbcs.getG_dbMetaData().keySet().iterator();
            Iterator iterator2=l_dbcs.getG_dbMetaData().values().iterator();
            while(iterator1.hasNext()&&iterator2.hasNext()){
                String l_key=(String)iterator1.next();
                DBFile l_dbfile = (DBFile) iterator2.next();
                
                if(l_key.equals(fileType)){
                   
                    Iterator iterator3=l_dbfile.getI_TableCollection().keySet().iterator();
                    Iterator iterator4=l_dbfile.getI_TableCollection().values().iterator();
                    while(iterator3.hasNext()&&iterator4.hasNext()){
                        String l_temp_table_id=(String)iterator3.next();
                        DBTable l_dbtable = (DBTable)iterator4.next();
                        if(l_dbtable.getI_Tableid()==l_tableID){
                            l_column_collection=l_dbtable.getI_ColumnCollection();
                        }
                        
                    }
                    
                }
                
            }
            String substring= p_fileName.substring(p_fileName.length()-4, p_fileName.length());
            
            
            
            int l_size_difference = 0;
            dbg("inside getRecord--->l_column_collection.size()"+l_column_collection.size());
               for(int i=0;i<l_column_collection.size();i++){
                   
               String l_temp_column_id=Integer.toString(i+1);
               if(substring.equals("_LOG")){
                l_record = l_record.concat("~").concat(l_record_values[Integer.parseInt(l_temp_column_id) - 1]);
             }else{
               DBColumn l_temp_db_column=l_column_collection.get(l_temp_column_id);
               dbg("l_temp_column_id"+l_temp_column_id);
               dbg("l_temp_db_column.getI_ColumnName()"+l_temp_db_column.getI_ColumnName());
               dbg("l_record_values[Integer.parseInt(l_temp_column_id) - 1]"+l_record_values[Integer.parseInt(l_temp_column_id) - 1]);
               
               ByteBuffer copy=ByteBuffer.wrap(l_record_values[Integer.parseInt(l_temp_column_id) - 1].getBytes(Charset.forName("UTF-8")));
               dbg("copy.limit()"+copy.limit());
               dbg("l_temp_db_column.getI_ColumnLength()"+l_temp_db_column.getI_ColumnLength());
//               l_size_difference=l_temp_db_column.getI_ColumnLength()-l_record_values[Integer.parseInt(l_temp_column_id) - 1].length();
               l_size_difference=l_temp_db_column.getI_ColumnLength()-copy.limit();
               
               dbg("PhysicalIO--->getRecord--->l_size_difference "+l_size_difference);
               for (int j = 1; j <= l_size_difference; j++) {
                    l_record_values[Integer.parseInt(l_temp_column_id) - 1] = l_record_values[Integer.parseInt(l_temp_column_id) - 1].concat(" ");
                }
               l_record = l_record.concat("~").concat(l_record_values[Integer.parseInt(l_temp_column_id) - 1]);
               dbg("l_record after "+l_temp_db_column.getI_ColumnName()+"--->"+l_record);
               }
               }
            dbg("in PhysicalIOService ->getRecord->->final->i_record" + l_record);
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
       
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
      return   l_record;
    }
    
    
    private String getFileType(String p_fileName)throws DBProcessingException{
       
    try{    
        File file=new File(p_fileName);
            File parentFile=new File(file.getParent());
            String fileType=new File(parentFile.getParent()).getName();
            
            
            return fileType;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
       
            
    }
    
    private void copyFileToActual(String p_src,String p_dest)throws DBProcessingException{
        
        try{
            dbg("inside copy file to actual");
            dbg("p_src"+p_src);
            dbg("p_dest"+p_dest);
            IBDProperties i_db_properties=session.getCohesiveproperties();
             Path l_src_path = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+p_src+i_db_properties.getProperty("FILE_EXTENSION"));
             Path l_dest_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+p_dest+i_db_properties.getProperty("FILE_EXTENSION"));
            
             if(Files.exists(l_src_path)){
                 dbg("inside file existence check in copy file to actual");
                 Files.copy(l_src_path, l_dest_path,REPLACE_EXISTING);
                 dbg("file is copied from temp path to actual path");
             }
             if(Files.exists(l_src_path)){
                 
                 Files.delete(l_src_path);
                 
             }
            dbg("end of copyFileToActual");
        }catch(InvalidPathException  ex)
            {
            dbg(ex);
            throw new DBProcessingException("InvalidPathException  " + ex.toString()); 
        }catch(FileAlreadyExistsException ex)
            {
            dbg(ex);
            throw new DBProcessingException("FileAlreadyExistsException " + ex.toString()); 
        }catch(DirectoryNotEmptyException ex)
            {
            dbg(ex);
            throw new DBProcessingException("DirectoryNotEmptyException" + ex.toString());  
        }catch(IOException ex)
            {
            dbg(ex);
            throw new DBProcessingException("IOException" + ex.toString());  
        }catch(SecurityException ex)
            {
            dbg(ex);
            throw new DBProcessingException("SecurityException" + ex.toString());  
        }catch(UnsupportedOperationException  ex)
            {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException " + ex.toString());  
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        
        
    }
    
    
    private boolean checkVersionNoAvailability(String p_fileType,String p_tableName)throws DBValidationException,DBProcessingException{
        
        try{
        dbg("inside checkVersionAvailability");
        boolean l_versionStatus=false;    
        IMetaDataService mds=dbdi.getMetadataservice();
        Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(p_fileType,p_tableName,session).getI_ColumnCollection();
          Iterator columnIterator=l_columnCollection.values().iterator();
          while(columnIterator.hasNext()){
             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
             if(l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")){
                 l_versionStatus=true;
                 dbg("version status is true for the table"+p_tableName);
             }else{
                 l_versionStatus=false;
                 dbg("version status is false for the table"+p_tableName);
             }
             if(l_versionStatus==true)
                 break;
         }
          dbg("end of versionAvailability"+l_versionStatus);
                  return l_versionStatus;
        }catch(DBValidationException ex){
            throw ex;
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());      
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
    }
    
   private String[] getPkeyWithVersion(String p_fileType,String p_tableName,String p_primaryKey,ArrayList<String> recordValues)throws DBValidationException,DBProcessingException{
       try{
           dbg("inside get pkey with version");
           IMetaDataService mds=dbdi.getMetadataservice();
           int l_versionNoColID=mds.getColumnMetaData(p_fileType, p_tableName, "VERSION_NUMBER",session).getI_ColumnID();  
           dbg("l_versionNoColID"+l_versionNoColID);
           String l_versionNumber=recordValues.get(l_versionNoColID-1);
           dbg("versionNumber"+l_versionNumber);
           
           String l_pkey=p_primaryKey.concat("~").concat(l_versionNumber);
           
           dbg("end of get pkey with version"+l_pkey);
           return l_pkey.split("~");
           
       }catch(DBValidationException ex){
            throw ex;
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());      
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
   }
    public boolean getIOLock(String p_file_name,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
       
        try{
        ILockService lock=dbdi.getLockService();
        long startTime=dbSession.getIibd_file_util(). getStartTime();   
        while(!lock.createIOLock(p_file_name,session,dbSession)){
            
            Thread.sleep(1); 
            
        }
        
        try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_file_name,null,null,"DBTransactionService","getIOLock",session,dbSession,dbdi,startTime);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
        
        return true;
        
        }catch(InterruptedException ex)
            {
            dbg(ex);
            throw new DBProcessingException("InterruptedException exception" + ex.toString());  
        }catch(IllegalArgumentException ex)
            {
            dbg(ex);
            throw new DBProcessingException("InterruptedException exception" + ex.toString());
       }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        
        
    }
    private boolean checkArchivalFileSize(FileChannel fc)throws DBProcessingException{
        try{
            
            
            if(fc.size()<=archivalFileLogSize){
                return true;
            }else{
                return false;
            }
            
            
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        
        
    }
    
    
//    private String getArchivalFileName(FileChannel fc)throws DBProcessingException{
//        String l_fileName;
//        try{
//        java.text.SimpleDateFormat l_sdf;
//        archivalFileLogSize= Integer.parseInt(session.getCohesiveproperties().getProperty("ARCH_FILE_SIZE"));
//        String DATE_DISPLAY_FORMAT= session.getCohesiveproperties().getProperty("LOG_FILE_DATE_DISPLAY_FORMAT");
//        l_sdf = new java.text.SimpleDateFormat(DATE_DISPLAY_FORMAT);
//        UUID archivalSeqNo=CohesiveSession.dataToUUID();
//        
//        
//        l_fileName="ARCH".concat(l_sdf.format(Calendar.getInstance().getTime())
//                        .toUpperCase()).concat(archivalSeqNo.toString());
//        if(fc.size()>=archivalFileLog){
//            
//        }
//        archivalFileName=l_fileName;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//        }
//        return l_fileName;
//    }
    
    private String createArchivalFileName(UUID writeSessionID)throws DBProcessingException{
        String l_fileName;
        try{
            dbg("inside archival file name");
        java.text.SimpleDateFormat l_sdf;
        ILockService lock=dbdi.getLockService();
//        int archivalFileLog= Integer.parseInt(session.getCohesiveproperties().getProperty("ARCH_FILE_SIZE"));
        String DATE_DISPLAY_FORMAT= session.getCohesiveproperties().getProperty("LOG_FILE_DATE_DISPLAY_FORMAT");
        l_sdf = new java.text.SimpleDateFormat(DATE_DISPLAY_FORMAT);
        //UUID archivalSeqNo=CohesiveSession.dataToUUID();
        long archivalSeqNo=lock.getArchivalSequenceNo();
        
        l_fileName="ARCH".concat(l_sdf.format(Calendar.getInstance().getTime())
                        .toUpperCase()).concat("_").concat(Long.toString(archivalSeqNo)).replace(" ", "_");
        

//           lock.getArchivalLock(l_fileName, session, dbSession);
              lock.createArchivalLock(l_fileName, session, dbSession,writeSessionID);
              archivalFileNames.add(l_fileName);
//        archivalFileName=l_fileName;
        dbg("end of archival file name --->l_fileName"+l_fileName);
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        return l_fileName;
    }
    
    private void archivalWrite(String p_fileName,char operation,String record,long position,UUID writeSessionID)throws DBProcessingException{
        
        try{
            dbg("inside archivalwrite");
            dbg("p_fileName"+p_fileName);
            
            if(p_fileName.contains("_LOG")||p_fileName.contains("Primary")||p_fileName.contains("Standby")||p_fileName.contains("REPORT")){
                
                return ;
            }
            
             Path file;
             String recordWithoutHash=record.substring(1);
             ILockService lock=dbdi.getLockService();
             
            String archivalRecord="#".concat(p_fileName).concat("~").concat(String.valueOf(operation)).concat("~").concat(String.valueOf(position)).concat("~").concat(recordWithoutHash);
            dbg("archivalRecord"+archivalRecord);
            IBDProperties i_db_properties=session.getCohesiveproperties();
           if(archivalFC==null||!archivalFC.isOpen()) {
                 dbg("opening file channel");
                file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                archivalFC = FileChannel.open(file, CREATE, WRITE, APPEND);
           }
           
            if(archivalFC.size()<=archivalFileLogSize){
                dbg("archivalFC.size()<=archivalFileLogSize");
                archivalFC.write(ByteBuffer.wrap(archivalRecord.getBytes(Charset.forName("UTF-8"))));
                dbg("archival file is writed");
            }else{
                dbg("file channel size greater than archivalFileLogSize");
               if(archivalFC!=null) { 
                if(archivalFC.isOpen()){
                    archivalFC.close();
//                    lock.releaseArchivalLock(archivalFileName, session, dbSession);
                    //release lock
                }
               
                archivalFileName=createArchivalFileName(writeSessionID);
                
                file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+ i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                archivalFC = FileChannel.open(file, CREATE, WRITE, APPEND);
                archivalFC.write(ByteBuffer.wrap(archivalRecord.getBytes(Charset.forName("UTF-8"))));
                dbg("archival file is writed");
            }
           }
            
           dbg("end of archival file write") ;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        
    }
    
    
    
    public boolean physicalIOWriteFromDestroy(String p_fileName,Map<String,Map<String,DBRecord>> p_fileMap,UUID writeSessionID)throws DBValidationException,DBProcessingException{
        
        boolean exceptionRaised=false;
        boolean l_session_created_now=false;
        IBDFileUtil fileUtil =null;
        FileChannel fc= null;
        long startTime=0;
        IBDProperties i_db_properties=null; 
          try {
        
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        archivalFileNames=new ArrayList();
        if(archivalFileName==null){
           archivalFileName= createArchivalFileName(writeSessionID);
           
        }
        archivalFileLogSize= Integer.parseInt(session.getCohesiveproperties().getProperty("ARCH_FILE_SIZE"));
        IMetaDataService mds=dbdi.getMetadataservice();
        startTime=dbSession.getIibd_file_util(). getStartTime();
        dbg("inside physicalIOService--->physicalIOWriteFromDestroy");
        String l_record;
        i_db_properties=session.getCohesiveproperties();
        fileUtil=dbSession.getIibd_file_util();//Integration change
        ILockService lock=dbdi.getLockService();
        ArrayList<String>l_emptyList=new ArrayList();
        String l_tempPath=fileUtil. getTempPath(p_fileName);
        long startingtime1=dbSession.getIibd_file_util(). getStartTime();
        fileUtil.deleteTempFile(p_fileName);
          try{
               dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.deleteTempFile call",session,dbSession,dbdi,startingtime1);
          }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
          }
          
           long startingtime2=dbSession.getIibd_file_util(). getStartTime();
           fileUtil.copyFileToTemp(Paths.get(p_fileName),Paths.get(l_tempPath));
          
          try{
               
             dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.copyFileToTemp call",session,dbSession,dbdi,startingtime2);

             }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
             }
          
          long startingtime3=dbSession.getIibd_file_util(). getStartTime();
          Iterator<String>tableIterator= p_fileMap.keySet().iterator();
          while(tableIterator.hasNext()){
               
                Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + l_tempPath + i_db_properties.getProperty("FILE_EXTENSION"));
                if(fc==null||!fc.isOpen()){
                  dbg("opening file channel in create operation for the file name"+p_fileName);  
                fc = FileChannel.open(file, CREATE, WRITE, APPEND);
                }
                String l_tableName=tableIterator.next();
                Iterator<String> pkIterator=p_fileMap.get(l_tableName).keySet().iterator();//iteration for performing create
                while(pkIterator.hasNext()){
                    String l_primaryKey=pkIterator.next();
                    DBRecord dbRec=p_fileMap.get(l_tableName).get(l_primaryKey);
                    l_record = getRecord(p_fileName, l_tableName, dbRec.getRecord());
                    dbg("l_record"+l_record);
                    if(dbRec.getOperation()=='C'){
                        dbg("inside physicalIOWriteFromDestroy operation is create");
                        fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                        dbg("before archival write call");
                        archivalWrite(p_fileName,dbRec.getOperation(),l_record,dbRec.getPosition(),writeSessionID);
                        dbg("after archival write call");
                    }
               }
           }
           if(fc!=null){
               dbg("closing file channel in create operation for the file name"+p_fileName);
               if(fc.isOpen()){
               fc.close();
               }
               fc=null;
           }
           
               try{
               
                 dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.write createRecord call",session,dbSession,dbdi,startingtime3);

               }catch(Exception ex){
                  throw new DBProcessingException("Exception".concat(ex.toString()));
               }
           long startingtime4=dbSession.getIibd_file_util(). getStartTime();
           Iterator<String>tableIterator1= p_fileMap.keySet().iterator();
           while(tableIterator1.hasNext()){
               Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + l_tempPath + i_db_properties.getProperty("FILE_EXTENSION"));
               if(fc==null||!fc.isOpen()){
                  dbg("opening file channel in update or delete operation for the file name"+p_fileName);
               fc = FileChannel.open(file, CREATE, WRITE);
               }
               String l_tableName=tableIterator1.next();
               Iterator<String> pkIterator=p_fileMap.get(l_tableName).keySet().iterator();//iteration for performing create
               while(pkIterator.hasNext()){
                    String l_primaryKey=pkIterator.next();
                    DBRecord dbRec=p_fileMap.get(l_tableName).get(l_primaryKey);
                    if(dbRec.getOperation()=='D'){
                        dbg("inside physicalIOWriteFromDestroy operation is Delete");
                        String fileType= getFileType(p_fileName);
                        int l_size = mds.getTableMetaData(fileType, l_tableName, session).getI_ColumnCollection().size();
                        dbg("in db transaction service->delete record->l_size"+l_size);
                        String[] l_sample = new String[l_size];
                        int   i = 0;
                        while (i < l_size) {
                           int length = mds.getColumnMetaData(fileType, l_tableName, i + 1, session).getI_ColumnLength();
                               int j = 1;
                               while (j <= length) {
                               if (j == 1) {
                                  l_sample[i] = " ";
                                  j++;
                               } else {
                                  l_sample[i] = l_sample[i] + " ";
                                  j++;
                               }
                        }
                   dbg("The sample[i]" + l_sample[i] + "is");
                   l_emptyList.add(l_sample[i]);
                   i++;
                   }              
                    l_record = getRecord(p_fileName, l_tableName, l_emptyList); 
                    l_record = l_record.replace("~", " ");
                    dbg("l_record"+l_record);
                    fc.position(dbRec.getPosition());
                    fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                    dbg("before archival write call");
                    archivalWrite(p_fileName,dbRec.getOperation(),l_record,dbRec.getPosition(),writeSessionID);
                    dbg("after archival write call");
                  }else if(dbRec.getOperation()!='C'){
                      dbg("inside physicalIOWriteFromDestroy operation is"+dbRec.getOperation());
                      dbg("inside physicalIOWriteFromDestroy Position"+dbRec.getPosition());
//                       dbRec=write.getWriteBuffer().get(fileName).get(l_tableName).get(l_primaryKey);
                    l_record = getRecord(p_fileName, l_tableName, dbRec.getRecord());
                    if(dbRec.getPosition()==0&&dbRec.getOperation()=='U'){
                          dbg("operation is update and position is 0");
                          if(fc!=null){
                             dbg("closing file channel in operation is update and position is 0"+p_fileName);
                               if(fc.isOpen()){
                                   fc.close();
                                 }
                          }
                          String fileType= getFileType(p_fileName);
                          dbg("fileType"+fileType);
                          Map<String, String> l_dummy_column = new HashMap();
                          String l_table_id = String.valueOf(mds.getTableMetaData(fileType, l_tableName, session).getI_Tableid());
                          String[] l_pkeyArr;
                          l_pkeyArr=  l_primaryKey.split("~");
                          PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_tempPath, Integer.parseInt(l_table_id), l_pkeyArr, l_dummy_column, session,dbdi);
                          dbg("in physicalIO service ->write->par.getI_position()" + par.getI_position());
                          long l_position = par.getI_position();
                          dbg("l_record"+l_record);
                          dbg("position"+l_position);
                           if(fc==null||!fc.isOpen()){
                              dbg("opening file channel in update or delete operation for the file name"+p_fileName);
                               fc = FileChannel.open(file, CREATE, WRITE);
                           }
                             fc.position(l_position);
                          fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                        
                          dbg("before archival write call");
                          archivalWrite(p_fileName,dbRec.getOperation(),l_record,l_position,writeSessionID);
                          dbg("after archival write call");
                    }else{
                          dbg("l_record"+l_record);
                          fc.position(dbRec.getPosition());
                          fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                          dbg("before archival write call");
                          archivalWrite(p_fileName,dbRec.getOperation(),l_record,dbRec.getPosition(),writeSessionID);
                          dbg("after archival write call");
                    }
                    
                  }
      
               }
               
           }
           if(fc!=null){
               dbg("closing file channel in create operation for the file name"+p_fileName);
               if(fc.isOpen()){
               fc.close();
               }
               fc=null;
           }
        dbg("file channel is closed");
        
        try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite.write update call",session,dbSession,dbdi,startingtime4);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
        
        
        
         if(getIOLock(p_fileName, session, dbSession)==true){       
              dbg("get lock is true");
             if(lock.isSameSessionIOLock(p_fileName, session, dbSession)){
                 dbg("same session lock is true");
                 
                 long startingtime5=dbSession.getIibd_file_util(). getStartTime();
               copyFileToActual(l_tempPath,p_fileName);
               try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite copyFileToActual call",session,dbSession,dbdi,startingtime5);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
             }
         }
        
        
         dbg("end physicalIOService--->physical write");
        return true;
         }catch (DBProcessingException ex) {
            dbg(ex);
            dbg("inside dbProcessing exception");
            exceptionRaised=true;
        }catch (Exception ex) {
            dbg(ex);
            dbg("inside exception");
            exceptionRaised=true;
        }
          
   finally{
          try{
             if(archivalFC!=null) {
               if(archivalFC.isOpen()){
                  archivalFC.close();
               }
             }  
             if(fc!=null){
              if(fc.isOpen()){
                  fc.close();
              }
             }
         }catch (Exception ex) {
            dbg(ex);
            dbg("inside exception");
            exceptionRaised=true;  
         }     
              
         try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"PhysicalIOService","physicalIOWrite",session,dbSession,dbdi,startTime);

         }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
         }finally{
             fileUtil.deleteTempFile(p_fileName);
              try{
                 if(dbdi.getLockService().isSameSessionIOLock(p_fileName, session, dbSession)){
                      dbdi.getLockService().releaseIOLock(p_fileName, session, dbSession);
                    }
                 }catch(Exception ex){
                     dbg(ex);
                     dbg("inside exception releaseIOLock");
                     exceptionRaised=true;
                 }
           dbg("end of finally block");
        }   
            if(exceptionRaised){
                dbg("returning fail ");
                
                for(int i=0;i<archivalFileNames.size();i++){
                     
                     String archivalFileName=archivalFileNames.get(i);
                     dbg("archivalFileName"+archivalFileName);
                     Path temp = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+archivalFileName + i_db_properties.getProperty("FILE_EXTENSION"));
                     
                     dbg("temp path"+temp.toString());
                    
                     if(Files.exists(temp)){
                         
                         try{
                         
                         Files.delete(temp);
                         
                         }catch(IOException ex){
                             
                         }
                         dbg(temp+"  is deleted");
                     }
//                     lock.releaseArchivalLock(archivalFileName, session, dbSession);
                 }
                
                
                
                  if(l_session_created_now){    
                         session.clearSessionObject();
                         dbSession.clearSessionObject();
                 }
                  
    
                  archivalFileNames=null;
            return false;
           }else{
                dbg("returning Success");
                if(l_session_created_now)    {    
                   session.clearSessionObject();
                   dbSession.clearSessionObject();
            }
                archivalFileNames=null;
               return true;
           }
            
          }  
    }
    
    private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
        
        
    }
