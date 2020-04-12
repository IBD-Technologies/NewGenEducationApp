/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.defragmentation;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class FileDefragmentationProcessing implements IFileDefragmentationProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public FileDefragmentationProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
  public void processing (String fileName,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       
       try{
             session.createSessionObject();
             dbSession.createDBsession(session);
             l_session_created_now=session.isI_session_created_now();   
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=inject.getDBTransactionService();
             BusinessService bs=inject.getBusinessService(session);
             batchUtil=inject.getBatchUtil(session);
             ITransactionControlService tc=inject.getTransactionControlService();
             dbg("FileDefragmentationProcessing");
                    String startTime=bs.getCurrentDateTime();
                    String[] l_primaryKey={fileName,businessDate};
                    Map<String,String>column_to_Update=new HashMap();
                    column_to_Update.put("4", startTime);
                    dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+businessDate, "BATCH", "DEFRAGMENTATION_BATCH_STATUS", l_primaryKey, column_to_Update,session); 
                    tc.commit(session, dbSession);
                    dbg("start time update complete");
             
             dbg("defragmentation process starts");      

             try{
                 copyOrgToWIP(fileName);
                 ArrayList<String>records=getRecords(fileName);
                 writeTempFile(fileName,records);
                 copyOrgToHistory(fileName);
                 copyTempToOriginal(fileName);
                 deleteWIPFiles(fileName);
             }catch(Exception ex){
                 
                  deleteWIPFiles(fileName);
                 throw ex;
             }
             
             dbg("end of FileDefragmentationProcessing");
        batchUtil.defragmentationProcessingSuccessHandler(businessDate, fileName, inject, session, dbSession);
        }catch(DBValidationException ex){
          batchUtil.defragmentationProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
//        }catch(BSValidationException ex){
//         batchUtil.userProfileArchProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.defragmentationProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.defragmentationProcessingErrorHandler(businessDate, fileName, ex, inject, session, dbSession);
        }finally{
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
      
  }

  public ArrayList<String> getRecords(String fileNameWithPath)throws BSProcessingException{
      Scanner l_file_content = null;
      try{
          dbg("inside file defragmentation--->getrecords");
          dbg("fileNameWithPath"+fileNameWithPath);
          ArrayList<String>fileRecords=new ArrayList();
          IBDProperties i_db_properties=session.getCohesiveproperties();
          Path wipFilePath=getWipFilePath(fileNameWithPath);
          
          l_file_content = new Scanner(new BufferedReader(new FileReader(wipFilePath.toString())));
          l_file_content.useDelimiter(i_db_properties.getProperty("RECORD_DELIMITER"));

            while (l_file_content.hasNext()) {
                dbg("l_file_content.hasNaext");
                String samp = l_file_content.next();
                 
                dbg("samp"+samp);
                if (samp.contains("~")) {
                    
                    dbg("samp.contains ~");
                    fileRecords.add(samp);
                }
          
            }
          dbg("fileRecords size"+fileRecords.size());
          dbg("end of file defragmentation--->getrecords");
          return fileRecords;
      }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }finally{
         if(l_file_content!=null){
                l_file_content.close();
            }
      }
  }
  
  
  private void writeTempFile(String fileNameWithPath,ArrayList<String> fileRecords)throws BSProcessingException{
      FileChannel fc=null;
      try{
          dbg("inside writeTempFile");
          dbg("fileNameWithPath"+fileNameWithPath);
          dbg("fileRecords"+fileRecords.size());
          
             Path tempFilePath=getTempFilePath(fileNameWithPath);
             if(fc==null||!fc.isOpen()){
                  fc = FileChannel.open(tempFilePath, CREATE, WRITE, APPEND);
              }
          
             for(int i=0;i<fileRecords.size();i++){
                 
                 String record="#"+fileRecords.get(i);
                 fc.write(ByteBuffer.wrap(record.getBytes(Charset.forName("UTF-8"))));  
                 
             }
          
          dbg("end of writeTempFile");
      }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }finally{
          
          try{
           if(fc!=null){
                if(fc.isOpen()){
                    fc.close();
                }
            }
           
         }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
      }
  }
  
  
  
  
  private Path getOriginalFilePath(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside getOriginalFilePath");
            dbg("getOriginalFilePath--->p_fileNameWithPath"+p_fileNameWithPath);
            Path originalPath=Paths.get(p_fileNameWithPath);
            
            dbg("end of getOriginalFilePath--->wipPath"+originalPath.toString());
            return originalPath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        
        
    }
  
   private Path getWipFilePath(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside getWipFilePath");
            dbg("getWipFilePath--->p_fileNameWithPath"+p_fileNameWithPath);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path orgPath=getOriginalFilePath(p_fileNameWithPath);
            Path parentPath=orgPath.getParent();
            Path wipFolderPath=Paths.get(parentPath.toString()+i_db_properties.getProperty("FOLDER_DELIMITER")+"WIP");
            String fileNameWithoutPath=orgPath.getFileName().toString();
            
            if(Files.notExists(wipFolderPath)){
                
                 Files.createDirectory(wipFolderPath);
                
            }
            
            Path wipFilePath=Paths.get(wipFolderPath.toString()+i_db_properties.getProperty("FOLDER_DELIMITER")+fileNameWithoutPath);
            dbg("end of getWipFilePath--->wipPath"+wipFilePath.toString());
            return wipFilePath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        
        
    }
   
   private Path getHistoryFilePath(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside getHistoryFilePath");
            dbg("getHistoryFilePath--->p_fileNameWithPath"+p_fileNameWithPath);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path orgPath=getOriginalFilePath(p_fileNameWithPath);
            Path parentPath=orgPath.getParent();
            Path historyFolderPath=Paths.get(parentPath.toString()+i_db_properties.getProperty("FOLDER_DELIMITER")+"HISTORY");
            String fileNameWithoutPath=orgPath.getFileName().toString();
            
            if(Files.notExists(historyFolderPath)){
                
                 Files.createDirectory(historyFolderPath);
                
            }
            
            Path historyFileFilePath=Paths.get(historyFolderPath.toString()+i_db_properties.getProperty("FOLDER_DELIMITER")+fileNameWithoutPath);
            dbg("end of getHistoryFilePath--->wipPath"+historyFileFilePath.toString());
            return historyFileFilePath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        
        
    }
   
   private Path getTempFilePath(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside getTempFileFilePath");
            dbg("getTempFileFilePath--->p_fileNameWithPath"+p_fileNameWithPath);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String fileExtension= i_db_properties.getProperty("FILE_EXTENSION");
            
            Path orgPath=getOriginalFilePath(p_fileNameWithPath);
            Path parentPath=orgPath.getParent();
            String fileNameWithoutPath=orgPath.getFileName().toString();
            String subStr= fileNameWithoutPath.substring(0, fileExtension.length());
            String tempFileName=subStr+"_temp";
            
            Path wipFolderPath=Paths.get(parentPath.toString()+i_db_properties.getProperty("FOLDER_DELIMITER")+"WIP");
            
            if(Files.notExists(wipFolderPath)){
                
                 Files.createDirectory(wipFolderPath);
                
            }
            
            
            
            
            Path tempFilePath=Paths.get(wipFolderPath.toString()+i_db_properties.getProperty("FOLDER_DELIMITER")+tempFileName+i_db_properties.getProperty("FILE_EXTENSION"));
            dbg("end of getTempFileFilePath--->tempFilePath"+tempFilePath.toString());
            return tempFilePath;
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());
        }
        
        
    }
   private void copyOrgToWIP(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside copyOrgToWIP");
            dbg("copyOrgToWIP--->p_fileNameWithPath"+p_fileNameWithPath);
            
            Path orgFilePath=getOriginalFilePath(p_fileNameWithPath);
            Path wipFilePath=getWipFilePath(p_fileNameWithPath);
            
                Files.copy(orgFilePath, wipFilePath,REPLACE_EXISTING);
                
            dbg("end of copyOrgToWIP");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
        
    } 
   
   
   private void copyOrgToHistory(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside copyOrgToHistory");
            dbg("copyOrgToWIP--->p_fileNameWithPath"+p_fileNameWithPath);
            
            Path orgFilePath=getOriginalFilePath(p_fileNameWithPath);
            Path historyFilePath=getHistoryFilePath(p_fileNameWithPath);
            
                Files.copy(orgFilePath, historyFilePath,REPLACE_EXISTING);
                
            dbg("end of copyOrgToHistory");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
        
        
    }
   private void deleteWIPFiles(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside deleteWIPFile");
            dbg("deleteWIPFile--->fileName"+p_fileNameWithPath);
            
            Path wipFilePath=getWipFilePath(p_fileNameWithPath);
            
            if(Files.exists(wipFilePath)){
                dbg("deleteWIPFile--->FilesExists");
                Files.delete(wipFilePath);
                dbg("file is deleted");
            }
            
            Path tempFilePath=getTempFilePath(p_fileNameWithPath);
            
            if(Files.exists(tempFilePath)){
                dbg("tempFilePath--->FilesExists");
                Files.delete(tempFilePath);
                dbg("tempFilePath is deleted");
            }
            
            
            dbg("end of deleteWIPFile");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }
        
    }
   
   private void copyTempToOriginal(String p_fileNameWithPath)throws BSProcessingException{
        
        try{
            dbg("inside copyTempToOriginal");
            dbg("copyTempToOriginal--->p_fileNameWithPath"+p_fileNameWithPath);
            
            Path tempFilePath=getTempFilePath(p_fileNameWithPath);
            Path orgFilePath=getOriginalFilePath(p_fileNameWithPath);
            
                Files.copy(tempFilePath, orgFilePath,REPLACE_EXISTING);
                
            dbg("end of copyTempToOriginal");
        }catch(Exception ex){
            dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
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
   @Asynchronous
   public Future<String> parallelProcessing(String fileName,String businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(fileName,businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
  
  public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
