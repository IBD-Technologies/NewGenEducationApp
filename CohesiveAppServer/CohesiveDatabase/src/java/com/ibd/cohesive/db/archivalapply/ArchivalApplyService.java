/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.archivalapply;

//import com.ibd.cohesive.db.archivalshipping.ArchRecords;
import com.ibd.businessViews.IArchShippingStatusUpdate;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.db.util.IBDFileUtil;
//import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.ArchApplyException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
//import com.ibd.cohesive.util.exceptions.ArchShippingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.nio.file.StandardOpenOption.READ;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */

@Stateless
public class ArchivalApplyService implements IArchivalApplyService{
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    boolean archivalErrorSent;
    
    public ArchivalApplyService() throws NamingException{
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
        archivalErrorSent=false;
    }
    
    public void ArchApplyProcessing()throws ArchApplyException{
        boolean l_session_created_now=false;
        Scanner l_file_content = null;
        String l_fileName=null;
        DirectoryStream<Path> stream=null;
        try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<Long, String>archFileMap=new HashMap();
        IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
        IDBTransactionService dbts=dbdi.getDBTransactionService();
        IArchShippingStatusUpdate shipingUpdate=dbdi.getIArchShippingStatusUpdate();
        IBDFileUtil fileUtil=dbSession.getIibd_file_util();
        ITransactionControlService tc=dbdi.getTransactionControlService();
        IMetaDataService mds=dbdi.getMetadataservice();
        IPDataService pds=dbdi.getPdataservice();
//        ILockService lock=dbdi.getLockService();
        dbg("inside ArchApplyProcessing");
//        shipingUpdate.statusUpdate(Json.createObjectBuilder().add("fileName", "issac").build());
//        dbg("shipping update called");
         Date date = new Date();
        String dateformat="yyMMdd";
        SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
        String currentDate=formatter.format(date);
        String previousDate;
        Map<String,DBRecord>shipingMap=null;
        List<DBRecord>errorRecords=null;
        String fileExtension=i_db_properties.getProperty("FILE_EXTENSION");
        //dbg("arch apply folder"+i_db_properties.getProperty("ARCH_APPLY_FOLDER"));
        
        Path todayPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby"+fileExtension);
        dbg("todayPath"+todayPath);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        previousDate=formatter.format(cal.getTime());
        dbg("previousDate"+previousDate);
        
        Path previousDayPath=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+previousDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby"+fileExtension);
        
        
        
        
        try{
        
            if(Files.exists(todayPath)){

                shipingMap= readBuffer.readTable("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby", "ARCH", "ARCH_APPLY_STATUS", session, dbSession);
            }else if(Files.exists(previousDayPath)){
                
//                Files.createDirectory(todayPath);
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DATE, -1);
//                previousDate=formatter.format(cal.getTime());

                shipingMap= readBuffer.readTable("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+previousDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby", "ARCH", "ARCH_APPLY_STATUS", session, dbSession);

            }
            
         if(shipingMap!=null){   
            
            errorRecords=shipingMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals("E")).collect(Collectors.toList());
         }
        
        }catch(DBValidationException ex){
            
            if(!(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000"))){
                
                throw ex;
            }
        }
        
        if(errorRecords!=null&&!errorRecords.isEmpty()){
            
            String message="Archival apply error";
            
            try{
            
             if(!archivalErrorSent){   
                
                fileUtil.sendArchivalError(message, session, dbSession, dbdi);
                archivalErrorSent=true;
            
             }
            
            }catch(DBProcessingException ex){
                
                dbg(ex);
                
            }
            
        }
        
        if(errorRecords==null||errorRecords.isEmpty()){
            dbg("inside errorRecords null");
        
            List<DBRecord>successRecords=null;
            try{

                Map<String,DBRecord>archAppyTable=readBuffer.readTable("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", session, dbSession,"true");
                successRecords= archAppyTable.values().stream().filter(rec->rec.getRecord().get(2).trim().equals("Y")&&rec.getRecord().get(3).trim().equals("Y")&&rec.getRecord().get(4).trim().equals("U")).collect(Collectors.toList());

            }catch(DBValidationException ex){
                if(!ex.toString().contains("DB_VAL_011")&&!ex.toString().contains("DB_VAL_000")){
                    throw ex;
                }
            }
        
//         Path ArchApplyFolderPath=Paths.get(i_db_properties.getProperty("ARCH_APPLY_FOLDER"));
         
         
         
//         dbg("fileExtension"+fileExtension);
//         DirectoryStream<Path> stream = Files.newDirectoryStream(ArchApplyFolderPath);
            
            
//            for (Path file: stream) {
//                l_fileName= file.getFileName().toString();
//                dbg("l_fileName"+l_fileName);
//                String[] fileArr=l_fileName.split("_");
//                dbg("fileArr[fileArr.length-1]"+fileArr[fileArr.length-1]);
//                Integer sequenceNo=Integer.parseInt(fileArr[fileArr.length-1]);
//                dbg("sequenceNo"+sequenceNo);
//                archFileMap.put(sequenceNo, l_fileName);
//            }
               if(successRecords==null){
                   dbg("success records null");
               }          


            if(successRecords!=null&&!successRecords.isEmpty()){

               for (DBRecord file: successRecords) {
                l_fileName= file.getRecord().get(0).trim();
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
                archFileMap.put(sequenceNo, l_fileName);
            }
            List<Long> sortedKeys=new ArrayList(archFileMap.keySet());
            Collections.sort(sortedKeys);
           for(Long key : sortedKeys) {
  
//            for (Path file: stream) {
                
                  try{
//                l_fileName= file.getFileName().toString();
                l_fileName= archFileMap.get(key);
//                System.out.println("Archival apply started for-->"+l_fileName+"");
                dbg("inside ArchApplyProcessing--->fileName-->"+l_fileName);
                Path archFilePath=getArchFilePath(l_fileName);
                l_file_content = new Scanner(new BufferedReader(new FileReader(archFilePath.toFile())));
                l_file_content.useDelimiter(i_db_properties.getProperty("RECORD_DELIMITER")); 
                dbg("before while loop");
              while (l_file_content.hasNext()) {
                   dbg("in ArchivalApplyService->readArchFile->insidewhile");
                   String samp = l_file_content.next();
                   if (samp.contains("~")) {
                       
     
                      String[]   l_column_values = samp.split(i_db_properties.getProperty("COLUMN_DELIMITER"));
                      String l_fileNameforWrite=l_column_values[0];
                      String l_operation=l_column_values[1];
                      long l_position=Long.parseLong(l_column_values[2]);
                 
                      dbg("in ArchivalApplyService->readArchFile-->fileName"+l_fileNameforWrite);
                      dbg("in ArchivalApplyService->readArchFile-->operation"+l_operation);
                      dbg("in ArchivalApplyService->readArchFile-->position"+l_position);
                 
                      int firstIdx=samp.indexOf("~");
                      int secondIdx=samp.indexOf("~",firstIdx+1);
                      int thirdIdx=samp.indexOf("~",secondIdx+1);
                  
                      String record="#".concat(samp.substring(thirdIdx+1, samp.length()));
                 
                      dbg("in ArchivalApplyService->readArchFile-->record"+record);
                 
                      try{
                        String deletedRecord= writeArchFile(l_fileNameforWrite,l_operation,l_position,record);
                        int tableID;
                      if(!l_operation.equals("D")){
                      
                        tableID=Integer.parseInt(record.substring(1, record.indexOf("~")));
                        
                        
                      }else{
                          
                          tableID=Integer.parseInt(record.substring(1, record.indexOf(" ")));
                          record=deletedRecord;
                          
                      }
                      String tableName=mds.getTableMetaData(tableID, session).getI_TableName();
                      
                      
                      String[] recordValues=record.substring(record.indexOf("~")+1).split("~");
                      String[] recordArray=new String[recordValues.length];
                      ArrayList<String>recordList=new ArrayList();
                      dbg("recordValues length"+recordValues.length);
                      
                      for(int i=0;i<recordValues.length;i++){
                          
                          dbg("recordValues[i]"+recordValues[i]);
                          recordList.add(recordValues[i].trim());
                          recordArray[i]=recordValues[i].trim();
                      }
                      
                      String pkey=mds.getPrimaryKey(session,tableID, recordArray);
                      DBRecord dbRec=new DBRecord(l_position,recordList,l_operation.charAt(0));
                      dbg("pkey"+pkey);
//                      String pkeyWithoutVersion=fileUtil.getPKwithoutVersion(l_fileName, tableName, pkey, dbdi, session);
                      
                      String pkeyWithoutVersion=fileUtil.getPKwithoutVersion(l_fileNameforWrite, tableName, pkey, dbdi, session);
                      pds.SetRecordfromArchivalWrite(l_fileNameforWrite, tableName, pkeyWithoutVersion, dbRec, session, dbSession);
                      readBuffer.SetRecordfromArchivalWrite(l_fileNameforWrite, tableName, pkeyWithoutVersion, dbRec, session, dbSession);
               
                      copyFileFromTempToActual(l_fileNameforWrite);
                      }catch(Exception ex){
                          dbg(ex);
                         deleteTempFile(l_fileNameforWrite);
                         throw new Exception("ArchApplyException"+ex.toString());
                     }       
                   
                   }    
                   
              }
              if(l_file_content!=null){
                l_file_content.close();
            }
              copyFileToHistory(l_fileName);
              deleteArchFile(l_fileName);
              
              String[] l_pkey={l_fileName};
              Map<String,String>p_column_to_update=new HashMap();
              p_column_to_update.put("5", "P");
              
              try{
              
                  dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", l_pkey, p_column_to_update, session);
    //              JsonObject request=Json.createObjectBuilder().add("fileName", l_fileName).add("applyStatus", "P").build();
                  tc.commit(session, dbSession);
              
              }catch(Exception e){
                dbg(e);
                tc.rollBack(session, dbSession);
                throw new Exception("Exception"+e.toString());
              } 
              
              
            try{
              
              String request=l_fileName+"~"+"P"+"~"+" ";
              fileUtil.restInvocation(request, "ArchShippingStatusUpdate", session);
              
            }catch(Exception ex){
                dbg(ex);
                
                String[] l_pkey1={l_fileName};
                Map<String,String>p_column_to_update1=new HashMap();
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                String exception=fileUtil.getReplacedException(exceptionAsString);
                p_column_to_update1.put("5", "E");
                p_column_to_update1.put("6",  exception.substring(1,200));
                
                try{
                
                    dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", l_pkey1, p_column_to_update1, session);
                    tc.commit(session, dbSession);
                
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
//                    throw new DBProcessingException("Exception"+e.toString());
                }
               throw new Exception("ArchApplyException"+ex.toString());

            }
              
              
//              shipingUpdate.statusUpdate(request);

             }catch(ArchApplyException ex){
                dbg(ex);
                
              throw new ArchApplyException("ArchApplyException"+ex.toString());

            }catch(Exception ex){
                String[] l_pkey={l_fileName};
                Map<String,String>p_column_to_update=new HashMap();
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                String exception=fileUtil.getReplacedException(exceptionAsString);
                p_column_to_update.put("5", "E");
                
                String exception1=null;
                if(exception.length()>200)
                  exception1=exception.substring(1,200);
                else
                   exception1= exception;
                
                p_column_to_update.put("6", exception1);
                
                //p_column_to_update.put("6", exception.substring(1, 200));
                
                try{
                
                    dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", l_pkey, p_column_to_update, session);
                    tc.commit(session, dbSession);
                
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
//                    throw new DBProcessingException("Exception"+e.toString());
                } 
//                JsonObject request=Json.createObjectBuilder().add("fileName", l_fileName).add("applyStatus", "E").add("exception", "exception").build();
                
                String request=l_fileName+"~"+"E"+"~"+exception1;
                
//                shipingUpdate.statusUpdate(request);
                fileUtil.restInvocation(request, "ArchShippingStatusUpdate", session);
                moveFileFromHistoryToArch(l_fileName);
                deleteHistoryFile(l_fileName);
              //  throw new Exception("ArchApplyException"+ex.toString());
                
                
            }
                  finally{
                   if (stream != null)  
                  stream.close();
                   if(l_file_content!=null){
                        l_file_content.close();
                     }
                  }
         }
            }
        }
                
                
          }catch(Exception ex){
            dbg(ex);
            //throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        finally{
            if(l_session_created_now){    
               session.clearSessionObject();
               dbSession.clearSessionObject();
            }
            if(l_file_content!=null){
                l_file_content.close();
            }
             if (stream != null)  
                  try {
                      stream.close();
            } catch (IOException ex) {
            dbg(ex);
           // throw new ArchApplyException("ArchApplyException"+ex.toString());
            }
                  
        }
    }
        
   
    private String writeArchFile(String p_fileName,String p_operation,long p_position,String p_record)throws ArchApplyException{
        FileChannel fc=null;
        String deltedrecord=null;
        try{
            dbg("inside writeArchFile");
            dbg("inside writeArchFile--->p_fileName"+p_fileName);
            dbg("inside writeArchFile--->p_operation"+p_operation);
            dbg("inside writeArchFile--->p_operation"+p_operation);
            dbg("inside writeArchFile--->p_position"+p_position);
            Path l_parent_path=Paths.get(p_fileName).getParent();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            
            
            Files.createDirectories(Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME")+l_parent_path));
            
            if(Files.notExists(Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME")+l_parent_path))){
               Files.createDirectory(Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME")+l_parent_path));
            }
      
        if(!Files.exists(Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"))){
           Files.createDirectory(Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"));
              dbg("temp path created");
         }
            deleteTempFile(p_fileName);
            copyFileFromActualToTemp(p_fileName);
             Path file = getTempPath(p_fileName);
//            Path file = Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME") + p_fileName+i_db_properties.getProperty("FILE_EXTENSION"));
            
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
                  
                  fc = FileChannel.open(file, CREATE, READ,WRITE);
                  fc.position();
                }
                
               fc.position(p_position);
               
               ByteBuffer copy;
               //try (FileChannel fcRead = (FileChannel.open(file, READ, WRITE))) {
                  copy = ByteBuffer.allocate(p_record.length());
                  //fcRead.position(p_position);
                 // byte data[] = s.getBytes();
                   int nread;
                    do {
                        nread = fc.read(copy);
                    } while (nread != -1 && copy.hasRemaining());

                 //  }
                
                //fc.read(copy);
                
                deltedrecord = new String( copy.array(), Charset.forName("UTF-8"));
                dbg("deltedrecord"+deltedrecord);
                fc.position();
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
            
            return deltedrecord;
//        copyFileFromTempToActual(p_fileName);
        }catch(Exception ex){
            dbg(ex);
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }finally{
            if(fc!=null){
                if(fc.isOpen()){
                    dbg("closing file channel after update operarion");
                    try{
                    fc.close();
                    }catch(Exception ex){
                        throw new ArchApplyException("ArchApplyException"+ex.toString());
                    }
                }
            }
        }
    
    }
      
            
     private Path getArchFilePath(String p_fileName)throws ArchApplyException{
        
        try{
            dbg("inside getArchApplyFilePath");
            dbg("getArchApplyFilePath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path ArchApplyPath=Paths.get(i_db_properties.getProperty("ARCH_APPLY_FOLDER")+p_fileName);            
            dbg("end of getArchApplyFilePath--->ArchApplyPath"+ArchApplyPath.toString());
            return ArchApplyPath;
        }catch(Exception ex){
            dbg(ex);
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
        
    }
     private Path getHistoryFilePath(String p_fileName)throws ArchApplyException{
        
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
        
    }
     
     private Path getTempPath(String p_fileName)throws ArchApplyException{
        
        try{
            dbg("inside getTempPath");
            dbg("getTempPath--->p_fileName"+p_fileName);
             Path l_parent_path=Paths.get(p_fileName).getParent();
              dbg("getTempPath--->l_parent_path"+l_parent_path);
             Path l_fileName=Paths.get(p_fileName).getFileName();
             
         
             
             
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path tempPath=Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME") + l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileName+i_db_properties.getProperty("FILE_EXTENSION"));            
            dbg("end of getTempPath--->tempPath"+tempPath.toString());
            return tempPath;
        }catch(Exception ex){
            dbg(ex);
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
        
    }
     private Path getActualPath(String p_fileName)throws ArchApplyException{
        
        try{
            dbg("inside getActualPath");
            dbg("getActualPath--->fileName"+p_fileName);
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Path tempPath=Paths.get(i_db_properties.getProperty("STANDBY_DB_HOME") + p_fileName+i_db_properties.getProperty("FILE_EXTENSION"));            
            dbg("end of Actual--->tempPath"+tempPath.toString());
            return tempPath;
        }catch(Exception ex){
            dbg(ex);
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
        
    }
     private void deleteTempFile(String p_fileName)throws ArchApplyException{
        
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
    }
     private void deleteHistoryFile(String p_fileName)throws ArchApplyException{
        
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
    }
     private void deleteArchFile(String p_fileName)throws ArchApplyException{
        
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
    }
  
    private void copyFileToHistory(String p_fileName)throws ArchApplyException{
        
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
        
        
    } 
    
     private void moveFileFromHistoryToArch(String p_fileName)throws ArchApplyException{
        
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
        
        
        
    } 
     
     private void copyFileFromTempToActual(String p_fileName)throws ArchApplyException{
         
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
     }
     
     private void copyFileFromActualToTemp(String p_fileName)throws ArchApplyException{
         
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
            throw new ArchApplyException("ArchApplyException"+ex.toString());
        }
     }
     
      private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
    
}
