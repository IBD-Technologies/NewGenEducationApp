/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archivalRecovery;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class ArchivalRecoveryProcessing implements IArchivalRecoveryProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public ArchivalRecoveryProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
  public void processing (String fileName,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       Scanner l_file_content = null;
       try{
             session.createSessionObject();
             dbSession.createDBsession(session);
             l_session_created_now=session.isI_session_created_now();   
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             BusinessService bs=inject.getBusinessService(session);
             batchUtil=inject.getBatchUtil(session);
             ITransactionControlService tc=inject.getTransactionControlService();
             dbg("FileArchivalRecoveryProcessing");
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={fileName};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("3", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "DEFRAGMENTATION_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
                    dbg("start time update complete");
             
             dbg("archivalRecovery process starts");      
             
             try{
             
            dbg("inside ArchivalRecoveryProcessing--->fileName-->"+fileName);
                Path archFilePath=getArchFilePath(fileName);
                l_file_content = new Scanner(new BufferedReader(new FileReader(archFilePath.toFile())));
                l_file_content.useDelimiter(i_db_properties.getProperty("RECORD_DELIMITER")); 
                dbg("before while loop");
              while (l_file_content.hasNext()) {
                   dbg("in ArchivalRecoveryProcessing->readArchFile->insidewhile");
                   String samp = l_file_content.next();
                   if (samp.contains("~")) {
                       
     
                      String[]   l_column_values = samp.split(i_db_properties.getProperty("COLUMN_DELIMITER"));
                      String l_fileNameforWrite=l_column_values[0];
                      String l_operation=l_column_values[1];
                      long l_position=Long.parseLong(l_column_values[2]);
                 
                      dbg("in ArchivalRecoveryProcessing->readArchFile-->fileName"+l_fileNameforWrite);
                      dbg("in ArchivalRecoveryProcessing->readArchFile-->operation"+l_operation);
                      dbg("in ArchivalRecoveryProcessing->readArchFile-->position"+l_position);
                 
                      int firstIdx=samp.indexOf("~");
                      int secondIdx=samp.indexOf("~",firstIdx+1);
                      int thirdIdx=samp.indexOf("~",secondIdx+1);
                  
                      String record="#".concat(samp.substring(thirdIdx+1, samp.length()));
                 
                      dbg("in ArchivalRecoveryProcessing->readArchFile-->record"+record);
                 
                      try{
                      writeArchFile(l_fileNameforWrite,l_operation,l_position,record);
                      copyFileFromTempToActual(l_fileNameforWrite);
                     }catch(Exception ex){
                         deleteTempFile(l_fileNameforWrite);
                     }       
                   
                   }    
                   
              }
              if(l_file_content!=null){
                l_file_content.close();
            }
              copyFileToHistory(fileName);
              deleteArchFile(fileName);
              
             }catch(Exception ex){
                 moveFileFromHistoryToArch(fileName);
                 deleteHistoryFile(fileName);
                
                 throw ex;
             }
              
             
             dbg("end of FileArchivalRecoveryProcessing");
        batchUtil.archivalRecoveryProcessingSuccessHandler(businessDate, fileName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.archivalRecoveryProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.userProfileArchProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.archivalRecoveryProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.archivalRecoveryProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
               if(l_file_content!=null){
                l_file_content.close();
            }
           }
      
  }

  private void writeArchFile(String p_fileName,String p_operation,long p_position,String p_record)throws BSProcessingException{
        FileChannel fc=null;
        try{
            dbg("inside writeArchFile");
            dbg("inside writeArchFile--->p_fileName"+p_fileName);
            dbg("inside writeArchFile--->p_operation"+p_operation);
            dbg("inside writeArchFile--->p_operation"+p_operation);
            dbg("inside writeArchFile--->p_position"+p_position);
            Path l_parent_path=Paths.get(p_fileName).getParent();
            
            
            IBDProperties i_db_properties=session.getCohesiveproperties();
            if(Files.notExists(Paths.get(i_db_properties.getProperty("RECOVERY_HOME")+l_parent_path))){
               Files.createDirectory(Paths.get(i_db_properties.getProperty("RECOVERY_HOME")+l_parent_path));
            }
      
        if(!Files.exists(Paths.get(i_db_properties.getProperty("RECOVERY_HOME")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"))){
           Files.createDirectory(Paths.get(i_db_properties.getProperty("RECOVERY_HOME")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"));
              dbg("temp path created");
         }
            deleteTempFile(p_fileName);
            copyFileFromActualToTemp(p_fileName);
             Path file = getTempPath(p_fileName);
//            Path file = Paths.get(i_db_properties.getProperty("RECOVERY_HOME") + p_fileName+i_db_properties.getProperty("FILE_EXTENSION"));
            
            dbg("inside writeArchFile--->file"+file.toString());
            
            if(p_operation.equals("C")){
                if(fc==null||!fc.isOpen()){
                  dbg("inside writeArchFile--->opening file channel in create operation"+p_fileName);  
                  fc = FileChannel.open(file, CREATE, WRITE, APPEND);
                }
                fc.write(ByteBuffer.wrap(p_record.getBytes(Charset.forName("UTF-8"))));    
                dbg("file is writed for create operation");
            if(fc!=null){
                if(fc.isOpen()){
                    dbg("closing file channel after create operarion");
                    fc.close();
                }
            }

          }else if(p_operation.equals("U")){
                
                if(fc==null||!fc.isOpen()){
                  dbg("inside writeArchFile--->opening file channel in update operation"+p_fileName); 
                  
                  fc = FileChannel.open(file, CREATE, WRITE);
                  fc.position();
                }
                fc.position(p_position);
                fc.write(ByteBuffer.wrap(p_record.getBytes(Charset.forName("UTF-8"))));    
                dbg("file is writed for update operation");
            if(fc!=null){
                if(fc.isOpen()){
                    dbg("closing file channel after update operarion");
                    fc.close();
                }
            }
                
          }else if(p_operation.equals("D")){
                
                if(fc==null||!fc.isOpen()){
                  dbg("inside writeArchFile--->opening file channel in update operation"+p_fileName); 
                  
                  fc = FileChannel.open(file, CREATE, WRITE);
                  fc.position();
                }
                fc.position(p_position);
                fc.write(ByteBuffer.wrap(p_record.getBytes(Charset.forName("UTF-8"))));    
                dbg("file is writed for update operation");
            if(fc!=null){
                if(fc.isOpen()){
                    dbg("closing file channel after update operarion");
                    fc.close();
                }
            }
                
          }
            
            
//        copyFileFromTempToActual(p_fileName);
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }finally{
            if(fc!=null){
                if(fc.isOpen()){
                    dbg("closing file channel after update operarion");
                    try{
                      fc.close();
                    }catch(Exception ex){
                        throw new BSProcessingException("BSProcessingException"+ex.toString());
                    }
                }
            }
        }
    
    }
      
            
     private Path getArchFilePath(String p_fileName)throws BSProcessingException{
            
        try{
            dbg("inside getArchApplyFilePath");
            dbg("getArchApplyFilePath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path ArchApplyPath=Paths.get(i_db_properties.getProperty("ARCH_APPLY_FOLDER")+p_fileName);            
            dbg("end of getArchApplyFilePath--->ArchApplyPath"+ArchApplyPath.toString());
            return ArchApplyPath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
    }
     private Path getHistoryFilePath(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside getHistoryFilePath");
            dbg("getHistoryFilePath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String currentDate=dbSession.getIibd_file_util().getCurrentDate();
            
            if(Files.notExists(Paths.get(i_db_properties.getProperty("ARCH_APPLY_HISTORY")+currentDate))){
                
                  Files.createDirectory(Paths.get(i_db_properties.getProperty("ARCH_APPLY_HISTORY")+currentDate));
            }
            
            
            
            Path ArchApplyPath=Paths.get(i_db_properties.getProperty("ARCH_APPLY_HISTORY")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_fileName);            
            dbg("end of getHistoryFilePath--->ArchApplyPath"+ArchApplyPath.toString());
            return ArchApplyPath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
    }
     
     private Path getTempPath(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside getTempPath");
            dbg("getTempPath--->p_fileName"+p_fileName);
             Path l_parent_path=Paths.get(p_fileName).getParent();
              dbg("getTempPath--->l_parent_path"+l_parent_path);
             Path l_fileName=Paths.get(p_fileName).getFileName();
             
         
             
             
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path tempPath=Paths.get(i_db_properties.getProperty("RECOVERY_HOME") + l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FILE_EXTENSION"));            
            dbg("end of getTempPath--->tempPath"+tempPath.toString());
            return tempPath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
    }
     private Path getActualPath(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside getActualPath");
            dbg("getActualPath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path tempPath=Paths.get(i_db_properties.getProperty("RECOVERY_HOME") + p_fileName+i_db_properties.getProperty("FILE_EXTENSION"));            
            dbg("end of Actual--->tempPath"+tempPath.toString());
            return tempPath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
    }
     private void deleteTempFile(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside deleteTempFile");
            dbg("deleteTempFile--->fileName"+p_fileName);
            
            Path tempPath=getTempPath(p_fileName);
            
            if(Files.exists(tempPath)){
                dbg("deleteTempFile--->FilesExists");
                Files.delete(tempPath);
                dbg("file is deleted");
            }
            dbg("end of deleteTempFile");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
    }
     private void deleteHistoryFile(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside deleteHistoryFile");
            dbg("deleteHistoryFile--->fileName"+p_fileName);
            
            Path historyPath=getHistoryFilePath(p_fileName);
            
            if(Files.exists(historyPath)){
                dbg("deleteHistoryFile--->FilesExists");
                Files.delete(historyPath);
                dbg("file is deleted");
            }
            dbg("end of deleteHistoryFile");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
    }
     private void deleteArchFile(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside deleteArchFile");
            dbg("deleteArchFile--->fileName"+p_fileName);
            
            Path archFilePath=getArchFilePath(p_fileName);
            
            if(Files.exists(archFilePath)){
                dbg("deleteArchFile--->FilesExists");
                Files.delete(archFilePath);
                dbg("file is deleted");
            }
            dbg("end of deleteHistoryFile");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
    }
  
    private void copyFileToHistory(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside moveFileToHistory");
            dbg("moveFileToHistory--->fileName"+p_fileName);
            
            Path archSrcPath=getArchFilePath(p_fileName);
            Path historyFilePath=getHistoryFilePath(p_fileName);
            
            if(Files.exists(archSrcPath)&&Files.notExists(historyFilePath)){
                dbg("moveFileToHistory--->FilesExists");
                Files.copy(archSrcPath, historyFilePath);
                dbg("file is moved");
            }
            dbg("end of moveFileToHistory");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
        
    } 
    
     private void moveFileFromHistoryToArch(String p_fileName)throws BSProcessingException{
        
        try{
            dbg("inside moveFileFromHistoryToArch");
            dbg("moveFileFromHistoryToArch--->fileName"+p_fileName);
            
            Path archSrcPath=getArchFilePath(p_fileName);
            Path historyFilePath=getHistoryFilePath(p_fileName);
            
            if(Files.exists(historyFilePath)&&Files.notExists(archSrcPath)){
                dbg("moveFileFromHistoryToArch--->FilesExists");
                Files.move(archSrcPath, historyFilePath);
                dbg("file is moved");
            }
            dbg("end of moveFileFromHistoryToArch");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
        
    } 
     
     private void copyFileFromTempToActual(String p_fileName)throws BSProcessingException{
         
         try{
         
            dbg("inside copyFileFromTempToActual") ;
             
           Path tempPath=  getTempPath(p_fileName);
           Path actualPath=getActualPath(p_fileName)  ;
             
        if(  Files.exists(tempPath)){
            dbg("copyFileFromTempToActual fileExists");
            Files.copy(tempPath, actualPath,REPLACE_EXISTING);
            dbg("file is copied");
        }
          dbg("end of copyFileFromTempToActual");
         }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
     }
     
     private void copyFileFromActualToTemp(String p_fileName)throws BSProcessingException{
         
         try{
         
            dbg("inside copyFileFromActualToTemp") ;
             
           Path tempPath=  getTempPath(p_fileName);
           Path actualPath=getActualPath(p_fileName)  ;
             
        if(  Files.exists(actualPath)){
            dbg("copyFileFromActualToTemp fileExists");
            Files.copy(actualPath, tempPath,REPLACE_EXISTING);
            dbg("file is copied");
        }
          dbg("end of copyFileFromActualToTemp");
         }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("Exception"+ex.toString());
        }
     }
  
 public void processing(String fileName,String businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(fileName,businessDate);
       
      }catch(DBValidationException ex){
          throw ex;
      }catch(BSValidationException ex){
          throw ex;     
      }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }finally {
           this.session=tempSession;
            
        } 
   }
   
  
  public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
