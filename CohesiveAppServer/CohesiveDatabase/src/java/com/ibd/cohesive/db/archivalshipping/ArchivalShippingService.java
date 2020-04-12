/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.archivalshipping;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.SFTPUtill;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.Email;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.ArchShippingException;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
//import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
//import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
@Stateless
public class ArchivalShippingService implements IArchivalShippingService {
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    SFTPUtill sftp;
    boolean archivalErrorSent;
    
    public ArchivalShippingService() throws NamingException, Exception{
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
        sftp = new SFTPUtill(session);  
        archivalErrorSent=false;
    }
    
    public void ArchShippingProcessing()throws ArchShippingException {
        boolean l_session_created_now=false;
        DirectoryStream<Path> stream =null;
        try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IMetaDataService mds=dbdi.getMetadataservice();
        IDBTransactionService dbts=dbdi.getDBTransactionService();
        IBDFileUtil fileUtil=dbSession.getIibd_file_util();
        ILockService lock=dbdi.getLockService();
        IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
        ITransactionControlService tc=dbdi.getTransactionControlService();
        dbg("inside ArchShippingProcessing");
        Date date = new Date();
        String dateformat="yyMMdd";
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
        String currentDate=formatter.format(date);
        dbg("currentDate"+currentDate);
        String previousDate;
        Map<String,DBRecord>shipingMap=null;
        String fileExtension=i_db_properties.getProperty("FILE_EXTENSION");
        Path todayPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary"+fileExtension);
        dbg("todayPath"+todayPath);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        previousDate=formatter.format(cal.getTime());
        dbg("previousDate"+previousDate);
        
        Path previousDayPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+previousDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary"+fileExtension);
        
        List<DBRecord>errorRecords=null;
        
        try{
        
            if(Files.exists(todayPath)){
                dbg("today path exists");
                shipingMap= readBuffer.readTable("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary", "ARCH", "ARCH_SHIPPING_STATUS", session, dbSession);
                dbg("read completes");
            }else if(Files.exists(previousDayPath)){
                dbg("today path not exists");
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DATE, -1);
//                previousDate=formatter.format(cal.getTime());
//                dbg("previousDate"+previousDate);
                shipingMap= readBuffer.readTable("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+previousDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary", "ARCH", "ARCH_SHIPPING_STATUS", session, dbSession);
                dbg("read completes");
            }
            
            if(shipingMap!=null){
            
                errorRecords=shipingMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("E")).collect(Collectors.toList());
                dbg("errorRecords"+errorRecords.size());
            
            }
            
        }catch(DBValidationException ex){
            dbg("exception occurs in shipping map ex"+ex.toString());
            if(!(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000"))){
               
                throw ex;
            }
        }
        
        dbg("processing starts");
        
        if(errorRecords!=null){
            
            dbg("error records size"+errorRecords.size());
        }else{
            
            dbg("error records null");
        }

        if(errorRecords!=null&&!errorRecords.isEmpty()){
            
            String message="Archival shipping error";
            
            try{
            
             if(!archivalErrorSent){      
                
                 fileUtil.sendArchivalError(message, session, dbSession, dbdi);
                 archivalErrorSent=true;
             }
            
            }catch(DBProcessingException ex){
                
                dbg(ex);
                
            }
            
        }
        List<Long> sortedKeys=null;
        
        Map<Long, Path>archFileMap=new HashMap();
        if(errorRecords==null||errorRecords.isEmpty()){
         Path ArchFolderPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER"));
            stream = Files.newDirectoryStream(ArchFolderPath);
            
           
            try{
            
            for (Path file: stream) {
                
                if(file.getFileName().toString().endsWith(fileExtension)){ 
                
                
                String l_fileName= file.getFileName().toString();;
                dbg("l_fileName"+l_fileName);
                String[] fileArr=l_fileName.split("_");
                String nameString=fileArr[fileArr.length-1];
                dbg("nameString"+nameString);
//                Integer sequenceNo;
                Long sequenceNo;
                if(nameString.contains(fileExtension)){
                    
                  String sequence=  nameString.substring(0,nameString.indexOf("."));
                  dbg("sequence"+sequence);
                  sequenceNo=Long.parseLong(sequence);
                }else{
                    
                  sequenceNo=Long.parseLong(nameString);
                }
                dbg("sequenceNo"+sequenceNo);
                archFileMap.put(sequenceNo, file);
            }
            
            }
            sortedKeys=new ArrayList(archFileMap.keySet());
              Collections.sort(sortedKeys);
            
            }catch(Exception ex){
                dbg(ex);
                sortedKeys=null;
                
            }finally{
                
                 if (stream != null)  
                  try {
                      stream.close();
                    } catch (IOException ex) {
                    dbg(ex);
                    //throw new ArchShippingException("ArchShippingException"+ex.toString());
                    }
                
            }
            
            
            
            
            long sequenceNo=0;
            int tableId=0;
//            for (Path file: stream) {

           for(long key:sortedKeys){
             String l_fileName=null;   
         try{   
             
            Path file= archFileMap.get(key);
             
             if(file.getFileName().toString().endsWith(fileExtension)){
             
               l_fileName= file.getFileName().toString();
               dbg("fileName"+l_fileName);
               String sentStatus;
               String checkSumStatus;
//               sequenceNo=lock.getArchivalSequenceNo();
               sequenceNo=key;
               dbg("sequenceNo"+sequenceNo);
              if(lock.getArchivalLock(l_fileName, session, dbSession)){
                  
                tableId=mds.getTableMetaData("ARCH", "ARCH_SHIPPING_STATUS", session).getI_Tableid();
             
              /*  try{
                
                    dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", tableId, l_fileName,Long.toString(sequenceNo)," "," ","U"," ");
                    tc.commit(session, dbSession);
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
                    throw new DBProcessingException("Exception"+e.toString());
                }  */
//               if(lock.isSameSessionArchivalLock(l_fileName, session, dbSession)){
               dbg("inside file iteration filePath"+file.toString()) ;
               MessageDigest md = MessageDigest.getInstance("SHA-256"); //SHA, MD2, MD5, SHA-256, SHA-384...
               String checkSum = fileUtil.checksum(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName, md);
               
               dbg("fileMovement starts");
               copyFileTOWIP(l_fileName);
               copyFileToArchApply(l_fileName);
               dbg("fileMovement ends");
               
//               if(checkAvailabilityinDest(l_fileName)){
//                   copyFileToHistory(l_fileName);
//                   deleteWipFile(l_fileName);
//                   deleteArchSrcFile(l_fileName);
               dbg("inside ArchShippingProcessing updation starts");    
                   
//                   IArchApplyStatusUpdate update=dbdi.getIArchApplyStatusUpdate();
                   //                   if(applyCheckSum.equals(checkSum)){
                      
                   


                   String request=l_fileName+"~"+sequenceNo+"~"+checkSum;
                   
                   
//                   JsonObject request=Json.createObjectBuilder().add("fileName", l_fileName)
//                                                                .add("sequenceNo", Long.toString(sequenceNo))
//                                                                .add("checkSum", checkSum).build();
                   dbg("before calling status update");
                   
                   
                   
//                   String response=update.statusUpdate(request);
                try{

                   String response=fileUtil.restInvocation(request, "ArchApplyStatusUpdate", session);
              
                   
                   dbg("response"+response);
                   checkSumStatus=response.split("~")[0];
                   sentStatus=response.split("~")[1];
                   dbg("sentStatus"+sentStatus);
                   dbg("sentStatus"+sentStatus);
                }
                catch(Exception ex)
                {
                     dbg(ex);
                     StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                String exception=fileUtil.getReplacedException(exceptionAsString);
                    try{
                     
                    dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", tableId, l_fileName,Long.toString(sequenceNo),"","","E",exception);
                    tc.commit(session, dbSession);
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
                    throw new DBProcessingException("Exception"+e.toString());
                }  
                       
                       
                       
                       throw new ArchShippingException("ArchShippingException"+l_fileName+" Source and destination files differs");
                   
                    
                } 
                  
                   
//                   }else{
//                       
//                       checkSumStatus="N";
//                   }
                   Map<String,String>l_column_to_update=new HashMap();
                   String[] l_pkey={l_fileName};
                   
                   if(checkSumStatus.equals("Y")&&sentStatus.equals("Y")){
                   
                       
                      /*l_column_to_update.put("3", checkSumStatus);
                       l_column_to_update.put("4", sentStatus);
                       
                       try{
                       
                           dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", "ARCH_SHIPPING_STATUS", l_pkey, l_column_to_update, session);
                           tc.commit(session, dbSession);
                       }catch(Exception e){
                          dbg(e);
                          tc.rollBack(session, dbSession);
                          throw new DBProcessingException("Exception"+e.toString());
                       } */
                        try{
                
                    dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", tableId, l_fileName,Long.toString(sequenceNo),checkSumStatus,sentStatus,"U"," ");
                    tc.commit(session, dbSession);
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
                    throw new DBProcessingException("Exception"+e.toString());
                }  
                       
                       
                       copyFileToHistory(l_fileName);
                       deleteWipFile(l_fileName);
                       deleteArchSrcFile(l_fileName);
                   }else{
//                       dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", tableId, l_fileName,Long.toString(sequenceNo),checkSumStatus,sentStatus,"E"," ");
                      /* l_column_to_update.put("3", checkSumStatus);
                       l_column_to_update.put("4", sentStatus);
                       l_column_to_update.put("5", "E");
                       
                       try{                       
                       
                           dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", "ARCH_SHIPPING_STATUS", l_pkey, l_column_to_update, session);
                           tc.commit(session, dbSession);
                       }catch(Exception e){
                           dbg(e);
                           tc.rollBack(session, dbSession);
                           throw new DBProcessingException("Exception"+e.toString());
                       } */
                       try{
                
                    dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", tableId, l_fileName,Long.toString(sequenceNo),checkSumStatus,sentStatus,"E"," ");
                    tc.commit(session, dbSession);
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
                    throw new DBProcessingException("Exception"+e.toString());
                }  
                       
                       
                       
                       throw new ArchShippingException("ArchShippingException"+l_fileName+" Source and destination files differs");
                   }
                   
//               }else{
//                   sentStatus="E";
//                   throw new ArchShippingException("ArchShippingException"+l_fileName+" Source and destination files differs");
//               }
               
//             lock.releaseArchivalLock(l_fileName, session, dbSession);
//               }
              }
            dbg("success completes");
         }
           }catch(ArchShippingException ex){
               dbg(ex);
               
               deleteWipFile(l_fileName);
//               deleteArchApplyFile(l_fileName);
               deleteHistoryFile(l_fileName);
            }catch(Exception ex){
               dbg(ex);
               
               deleteWipFile(l_fileName);
//               deleteArchApplyFile(l_fileName);
               deleteHistoryFile(l_fileName); 
               
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                String exception=fileUtil.getReplacedException(exceptionAsString);
                    try{
                     
                    dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", tableId, l_fileName,Long.toString(sequenceNo),"","","E",exception);
                    tc.commit(session, dbSession);
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
                    throw new DBProcessingException("Exception"+e.toString());
                }  
                    
               //throw new ArchShippingException("ArchShippingException"+ex.toString());
           }finally{
             lock.releaseArchivalLock(l_fileName, session, dbSession);
           }
           
         }
            
        }
        }catch(Exception ex){
            dbg(ex);
            //throw new ArchShippingException("ArchShippingException"+ex.toString());
        }
        finally{
            
            if (stream != null)  
                  try {
                      stream.close();
            } catch (IOException ex) {
            dbg(ex);
            //throw new ArchShippingException("ArchShippingException"+ex.toString());
            }
            
            if(l_session_created_now){    
               session.clearSessionObject();
               dbSession.clearSessionObject();
            }
        }
        
        
    }
    
    private boolean checkAvailabilityinDest(String p_fileName)throws ArchShippingException{
        try{
          dbg("inside checkAvailabilityinDest");
          dbg("checkAvailabilityinDest--->p_fileName"+p_fileName);
          //Path archApplyFilePath=getArchApplyFilePath(p_fileName);
          String archApplyFilePath=session.getCohesiveproperties().getProperty("ARCH_APPLY_FOLDER")+p_fileName;
          long archSrcFileSize=getArchSrcFileSize(p_fileName);
        //  long archApplyFileSize=getArchApplyFileSize(p_fileName);
          long archApplyFileSize=sftp.CheckFileAvailability(archApplyFilePath);
          dbg("checkAvailabilityinDest--->archSrcFileSize"+archSrcFileSize);
          dbg("checkAvailabilityinDest--->archApplyFileSize"+archApplyFileSize);
          //if(Files.notExists(archApplyFilePath)){
          if(archApplyFileSize==0)
          {   
              dbg("file not exist");
              return false;
              
          }else{
             if(archSrcFileSize!=archApplyFileSize){
                  dbg("file exist size not equal");
                  return false;
             }else{
                 dbg("file exist size equal");
                 return true;
             }  
              
          }
        
        }
      /*  catch(NoSuchFileException ex){
          
         return  checkDestinationHistory(p_fileName);
          
        }*/catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"checkAvailabilityinDest");
        }
    }
    
    private void copyFileTOWIP(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside copyFile to WIP");
            dbg("copyFile to WIP--->fileName--->"+p_fileName);
            Path archSrcPath=getArchSrcFilePath(p_fileName);
            Path wipPath=getWipFilePath(p_fileName);
            if(Files.exists(archSrcPath)){
                dbg("copyFile to WIP--->fileExists"); 
            Files.copy(archSrcPath, wipPath);
            }
            dbg("end of copyFile to WIP");
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"copyFileTOWIP");
        }
        
    }
    
     private void copyFileToArchApply(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside copyFileToArchApply");
            dbg("copyFileToArchApply fileName"+p_fileName);
            
           Path wipPath= getWipFilePath(p_fileName);
           
           //Path archApplyFilePath= getArchApplyFilePath(p_fileName);
           String  archApplyFilePath=session.getCohesiveproperties().getProperty("ARCH_APPLY_FOLDER"); 
           dbg("archApplyFilePath-->"+archApplyFilePath);
           dbg("wipPath.toString()-->"+wipPath.toString());
           
           if(Files.exists(wipPath)){
              dbg("copyFileToArchApply--->fileExists");
           dbg("wipPath.toString()-->"+wipPath.toString());
              
              //Files.copy(wipPath, archApplyFilePath);
              
//              try{
//              
//              sftp.doSftpTransfer(session,wipPath.toString(), archApplyFilePath);
//              
//              }catch(Exception ex){
//                  
//                  
//                  Thread.sleep(60000);
//                  
//                  try{
//                  
//                  sftp.destroySession();
//                  
//                  }catch(Exception e){
//                      
//                      sftp=null;
//                      
//                  }
//                  sftp=null;
//                  
//                  sftp = new SFTPUtill(session);  
//                  sftp.doSftpTransfer(session,wipPath.toString(), archApplyFilePath);
//              }
              
                boolean comeOutLoop=false;
                int iteratonCount=0;
               
                while(comeOutLoop==false){
                    
                    try{
                        
                        Thread.sleep(2000);
                        iteratonCount=iteratonCount+1;
                        sftp.destroyAndCreateSession();
                        sftp.doSftpTransfer(session,wipPath.toString(), archApplyFilePath);
                        comeOutLoop=true;
                    }catch(Exception ex){
                        if(iteratonCount>100){
                            throw ex;
                        }
                    }
                    
                }


           }
           dbg("end of copyFileToArchApply");
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"copyFileToArchApply");
        }
        
        
    }
    
    private void copyFileToHistory(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside copyFileToHistory");
            dbg("copyFileToHistory fileName"+p_fileName);
            
           Path wipPath= getWipFilePath(p_fileName);
           Path historyFilePath= gethistoryFilePath(p_fileName);
            
           if(Files.exists(wipPath)&&Files.notExists(historyFilePath)){
              dbg("copyFileToHistory--->fileExists"); 
              Files.copy(wipPath, historyFilePath);
           }
           dbg("end of copyFileToHistory");
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"copyFileToHistory");
        }
        
        
    } 
     
    private Path getWipFilePath(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside getWipFilePAth");
            dbg("getArchSrcFilePath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path wipPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_SHIPING"+i_db_properties.getProperty("FOLDER_DELIMITER")+"WIP"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_fileName);
            
            dbg("end of getWipFilePath--->wipPath"+wipPath.toString());
            return wipPath;
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"getWipFilePath");
        }
        
        
    }
    
    private Path getArchSrcFilePath(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside getArchSrcFilePath");
            dbg("getArchSrcFilePath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path ArchPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_fileName);            
            dbg("end of getArchSrcFilePath--->ArchPath"+ArchPath.toString());
            return ArchPath;
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"getArchFilePath");
        }
        
        
    }
     private Path getArchApplyFilePath(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside getArchApplyFilePath");
            dbg("getArchApplyFilePath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path ArchApplyPath=Paths.get(i_db_properties.getProperty("ARCH_APPLY_FOLDER")+p_fileName);            
            dbg("end of getArchApplyFilePath--->ArchApplyPath"+ArchApplyPath.toString());
            return ArchApplyPath;
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"getArchApplyFilePath");
        }
        
        
    }
     
     private Path gethistoryFilePath(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside gethistoryFilePath");
            dbg("gethistoryFilePath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            String currentDate=dbSession.getIibd_file_util().getCurrentDate();
            if(Files.notExists(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_SHIPING"+i_db_properties.getProperty("FOLDER_DELIMITER")+"HISTORY"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate))){
                
                  Files.createDirectory(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_SHIPING"+i_db_properties.getProperty("FOLDER_DELIMITER")+"HISTORY"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate));
            }
            
            
            
            Path historyPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH_SHIPING"+i_db_properties.getProperty("FOLDER_DELIMITER")+"HISTORY"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+p_fileName);      
            dbg("end of gethistoryFilePath--->historyPath"+historyPath.toString());
            return historyPath;
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"gethistoryFilePath");
        }
        
        
    }
     
    private void deleteWipFile(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside deleteWopFile");
            dbg("deletewipFile--->fileName"+p_fileName);
            
            Path wipPath=getWipFilePath(p_fileName);
            
            if(Files.exists(wipPath)){
                dbg("deleteWipFile--->FilesExists");
                Files.delete(wipPath);
                dbg("file is deleted");
            }
            dbg("end of deletewipFile");
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"deleteWipFile");
        }
        
        
        
    } 
   private void deleteArchSrcFile(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside deleteArchSrcFile");
            dbg("deleteArchSrcFile--->fileName"+p_fileName);
            
            Path archSrcPath=getArchSrcFilePath(p_fileName);
            
            if(Files.exists(archSrcPath)){
                dbg("deleteArchSrcFile--->FilesExists");
                Files.delete(archSrcPath);
                dbg("file is deleted");
            }
            dbg("end of deleteArchSrcFile");
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"deleteArchFile");
        }
        
        
        
    }
    
   private void deleteArchApplyFile(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside deleteArchApplyFile");
            dbg("deleteArchApplyFile--->fileName"+p_fileName);
            
            Path archApplyPath=getArchApplyFilePath(p_fileName);
            
            if(Files.exists(archApplyPath)){
                dbg("deleteArchApplyFile--->FilesExists");
                Files.delete(archApplyPath);
                dbg("file is deleted");
            }
            dbg("end of deleteArchApplyFile");
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"deleteArchApplyFile");
        }
        
        
        
    }
   private void deleteHistoryFile(String p_fileName)throws ArchShippingException{
        
        try{
            dbg("inside deleteHistoryFile");
            dbg("deleteHistoryFile--->fileName"+p_fileName);
            
            Path historyPath=gethistoryFilePath(p_fileName);
            
            if(Files.exists(historyPath)){
                dbg("deleteHistoryFile--->FilesExists");
                Files.delete(historyPath);
                dbg("file is deleted");
            }
            dbg("end of deleteHistoryFile");
        }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"deleteArchApplyFile");
        }
        
        
        
    }
   
   private long getArchSrcFileSize(String p_fileName)throws ArchShippingException{
       try{
        dbg("inside getArchSrcFileSize--->fileName"+p_fileName) ;  
        Path archSrcFilePath=getArchSrcFilePath(p_fileName);   
         
         long fileSize=Files.size(archSrcFilePath);
         
         dbg("end of getArchSrcFileSize--->fileSize "+fileSize);
        return fileSize;
       }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"getArchSrcFileSize");
        }
       
       
   }
   private long getArchApplyFileSize(String p_fileName)throws ArchShippingException,NoSuchFileException{
       try{
        dbg("inside getArchApplyFileSize--->fileName"+p_fileName) ;  
        Path archSrcFilePath=getArchApplyFilePath(p_fileName);   
         
         long fileSize=Files.size(archSrcFilePath);
         
         dbg("end of getArchApplyFileSize--->fileSize "+fileSize);
        return fileSize;
        
       }catch(NoSuchFileException ex){
        
           throw ex;
       }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"getArchApplyFileSize");
        }
       
       
   }
   
   private boolean checkDestinationHistory(String p_fileName)throws ArchShippingException{
       try{
        dbg("inside getArchSrcFileSize--->fileName"+p_fileName) ;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Path ArchApplyHistoryPath=Paths.get(i_db_properties.getProperty("ARCH_APPLY_HISTORY")+p_fileName);
        
        Thread.sleep(10000);
        
        if(Files.exists(ArchApplyHistoryPath)){
            
            return true;
            
        }else{
            
            return false;
        }
        
        
         
       }catch(Exception ex){
            dbg(ex);
            throw new ArchShippingException("ArchShippingException"+p_fileName+"checkDestinationHistory");
        }
       
       
   }
   
   
   
   private void sendArchivalError(String message)throws ArchShippingException{
       
       try{
       
       IPDataService pds= dbdi.getPdataservice();
       IBDProperties i_db_properties=session.getCohesiveproperties();
       IAmazonSMSService smsService=dbdi.getAmazonSMSService();
       IAmazonEmailService emailService=dbdi.getAmazonEmailService();
       ArrayList<String>mobileNoList=new ArrayList();
       ArrayList<String>emailList=new ArrayList();
       
       Map<String,ArrayList<String>>appSupportMap=pds.readTablePData("APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP","APP_SUPPORT", session, dbSession);
       
       Iterator<ArrayList<String>>valueIterator=appSupportMap.values().iterator();
       
       while(valueIterator.hasNext()){
           
           ArrayList<String>value=valueIterator.next();
           
           String email=value.get(1).trim();
           
           emailList.add(email);
           
           String mobileNo=value.get(2).trim();
           
           mobileNoList.add(mobileNo);
           
       }
       
       
       for(int i=0;i<emailList.size();i++){
           
           String email=emailList.get(i);
           Email emailObj=getEmailObject(message,email);
           emailService.sendEmail(emailObj, session);
           
       }
       
       
       for(int i=0;i<mobileNoList.size();i++){
           
           String mobileNo=mobileNoList.get(i);
           smsService.sendSMS(message, mobileNo, session,"System");
           
       }
       
       
       
       }catch(Exception ex){
            dbg(ex);
//            throw new ArchShippingException("ArchShippingException"+p_fileName+"checkDestinationHistory");
        }
       
   }
   
   private Email getEmailObject(String message,String toEmail)throws BSProcessingException,BSValidationException{
         try{
             
           dbg("inside NotificationService--->getEmailObject");
           dbg("message"+message);

           
           String fromEmail="info@ibdtechnologies.com";
           String subject="Email Notification from "+"Cohesive";
           String textBody="This is Email Notification from "+"Cohesive";
           String htmlBody="<h1> Email Notification from "+"Cohesive"+"</h1>"
                          + "<p>"+message
                              +"</p>" 
                              +"<p> <u>This is Auto generated email , please do not reply</u></p>"
                              ;
                
            Email email=new  Email();
            
            email.setFromEmail(fromEmail);
            email.setToEmail(toEmail);
            email.setHtmlBody(htmlBody);
            email.setSubject(subject);
            email.setTextBody(textBody);
                
            
        dbg("End of NotificationService--->getEmailObject");
        
           return email;
         }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
   } 
   
   
   @PreDestroy
   
   public void destroySftpSession(){
       
       
       sftp.destroySession();
       
       
       
   }
   
    private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
}
