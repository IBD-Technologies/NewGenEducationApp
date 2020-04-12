/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.transaction;

import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.DBFile;
import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;

import com.ibd.cohesive.db.index.IIndexCoreService;
import com.ibd.cohesive.db.index.IIndexReadService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.tempSegment.IDBTempSegmentService;

import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.db.util.IIBDFileUtil;
import com.ibd.cohesive.db.util.PositionAndRecord;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.db.waitmonitor.DBWait;
import com.ibd.cohesive.db.waitmonitor.IDBWaitBuffer;
import com.ibd.cohesive.db.write.IDBWriteBufferService;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies 
 * Change Description : New Unit creation Changed by : Suriya
 * Narayanan.L Changed on : 13/05/2018
 *
 *
 * Change Description :since table name is not unique it alone can not be used
 * to get table meta data we have to include filetype as well in the table name
 * validation. Change Description : : Suriya Narayanan.L Changed on : 14/05/2018 Search
 * Tag : CODEREVIEW_6
 *
 * Change Description :original file path is replaced with temp file path in order to handle transaction control
 * Changed by   :Isac
 * Changed on   :04/09/2018
 * Search Tag   :Cohesive1_Dev_2
 * 
 * Change Description :Cross call functions is added 
 * Changed by   :Isac
 * Changed on   :05/09/2018
 * Search Tag   :Cohesive1_Dev_3
 */
@Stateless
public class DBTransactionService implements IDBTransactionService {

    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    
    String flag = "UPDATE";//this is to help updateRecord function to understand that a call is from deleteRecord function
    //int i = 0;//this is to help the help the lamda expression in update record
    //String i_PK = null;
    //String i_tableName;//this is to help the help the lamda expression
    //int i_size_difference = 0, j = 0;//this is to help the help the lamda expression
    //int i = 0;//this is to help the lamda expression
    //String i_record = null;//this is to help the help the lamda expression
    //String[] tokens = {};
    //Map<String, DBColumn> i_column_collection = new HashMap();
    // DBValidation dbv;
    //String l_table_id = null;//this is to help the  lamda expression in update record function
    //String i_table_name;//this is to help the help the lamda expression
    //String i_primary_key = null;
   // long i_position = -1;
    //long i_length_of_the_record = 0;
    //boolean i_index_required = false;
    //String i_dummy_pk;
    //String l_primary_key2 = null;//this is to help the help the lamda expression
    //boolean indicator = false;//this is to help the help the lamda expression
    //int i_length_difference;//this is to help the help the lamda expression
    //FileChannel fc = null;//this is to help the help the lamda expression
   // long l_position = 0;//this is to help the help the lamda expression
    //String col_name = null;//this is to help the help the lamda expression
    //int l_column_length;//this is to help the help the lamda expression
   // int total_file_names = 1;//this is to help the help the lamda expression
    //String[] i_original_related_file_names;//this is to help the help the lamda expression
    //int k;////this is to help the help the lamda expression
   // int m = 1;//this is to help the help the lamda expression
   // int i_col_len;//this is to help the help the lamda expression
    private String l_DBTable;

    public DBTransactionService() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession= new DBSession(session);
        // dbv = dbdi.getDbv();
    }

    /*This function creates a record in the specified file given by the p_fileName param 
    in param is filename filetype table id and record values*/
    public void createRecord1(String p_fileName, String p_fileType, int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException {
        // StringBuffer l_err_code = new StringBuffer();
        //StringBuffer l_error_code = new StringBuffer();
        //l_error_code=null;
        //l_err_code=null;
        // dbg("l_error_code"+l_error_code);
        //dbg("l_error_code"+l_error_code.capacity());
        // dbg("l_error_code"+l_error_code.length()+"l_error_code");
//        dbg("inside create record of transaction service");
       ITransactionControlService tc;
        boolean l_session_created_now=false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            //Cohesive1_Dev_2 change starts 
            lockProcessing(p_fileName);
            String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_fileName);
            //Cohesive1_Dev_2 change ends 
            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errhandler = session.getErrorhandler();
            tc=dbdi.getTransactionControlService();
            boolean indicator = false;

            boolean l_validation_status = true;
            boolean l_tableId_validation_status = true;
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            String l_record;
           

            //BufferedWriter writer = null;
            //SeekableByteChannel sbc = null;
            long l_index_position;
            Map<String, String> l_inner_map = new HashMap();
            String l_index_map_key = null;
            int i = 0;
            dbg("inside create record of transaction service");
            for(String s:p_record_values){
                dbg("p_record_values"+s);
            }

            /* if (!dbv.fileNameValidation(p_fileName, errhandler.getSingle_err_code())) {
                l_validation_status = false;
                errhandler.log_error();

            }*/
 /*DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_Tableid() == p_tableID) {
                        i_table_name = v.getI_TableName();
                    }
                });
            });*/
            IMetaDataService mds = dbdi.getMetadataservice();
            DBTable l_dbtable = mds.getTableMetaData(p_tableID, this.session);
            String l_table_name = l_dbtable.getI_TableName();

            i = 0;
            while (i < p_record_values.length) {
                if(p_record_values[i].length()>0)//Integration changes
                {   
                if (!dbv.specialCharacterValidation(p_record_values[i], errhandler)) {
                    dbg("in createRecord->i_tableName" + l_table_name);
                    DBColumn l_dbcolumn = mds.getColumnMetaData(p_fileType, l_table_name, (i + 1));
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_fileName + "," + l_table_name + "," + l_dbcolumn.getI_ColumnName());
                    errhandler.log_error();
                }
                }//Integration changes
                i++;
            }
          
            if (!dbv.specialCharacterValidation(p_fileName, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_fileType, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_tableID, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            i = 0;
            while (i < p_record_values.length) {
                if (!dbv.columnLengthValidation(l_table_name, p_record_values[i], i + 1, errhandler,dbdi)) {
                    l_validation_status = false;
                    dbg("l_validation_status false in column length validation");
                    errhandler.log_error();
                }
                i++;
            }
            

            i=0;
            while (i < p_record_values.length) {
                if (!dbv.columnDataTypeValidation(l_table_name, i + 1, p_record_values[i], errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();
                }
                i++;
            }
         
            /* if (!dbv.fileNameValidation(p_fileName, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }*/
            if (!dbv.fileTypeValidation(p_fileType, errhandler,dbdi)) {
                l_validation_status = false;

                errhandler.log_error();

            }
          
            if (!dbv.tableIDValidation(p_tableID, errhandler,dbdi)) {
                l_validation_status = false;
                l_tableId_validation_status = false;

                errhandler.log_error();

            }
        
            //throw new DBValidationException("p_tableID validation failed in createRecord() function in DBTransactionService");
            if (l_tableId_validation_status) {
                //dbg("dbv.recordValuesValidation(errhandler, p_tableID, p_record_values)"+dbv.recordValuesValidation(errhandler, p_tableID, p_record_values));
                if (!dbv.recordValuesValidation(errhandler,dbdi, p_tableID, p_record_values)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
            }
      
            //DBTable l_dbtable = mds.getTableMetaData(p_tableID);

            i = 0;
            String l_pk = l_dbtable.getI_Pkey();
            dbg("l_pkk"+l_pk);
            String[] l_pk_ids = l_pk.split("~");
            String[] l_PK = new String[l_pk_ids.length];
            while (i < l_pk_ids.length) {

                l_PK[i] = p_record_values[(Integer.parseInt(l_pk_ids[i]) - 1)];
                i++;
            }
            //boolean file_status = Files.exists(Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_fileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION")));
           //Cohesive1_Dev_2 
           dbg("before file status check");
           boolean file_status = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + l_temp_path + session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
            if (file_status == true) {
                //Cohesive1_Dev_2
                dbg("file status is true");
                boolean status = this.duplicateRecordAvailability(l_temp_path, p_fileType, l_table_name, l_PK);
                dbg("in create record ->status->" + status);
                if (status == true) {
                    StringBuffer err = new StringBuffer("DB_VAL_009");
                    l_validation_status = false;
                    errhandler.setSingle_err_code(err);
                    errhandler.log_error();
                }
            }
            if (!l_validation_status) {

                //  dbg("Error code "+errhandler.getSession_error_code());
                throw new DBValidationException(errhandler.getSession_error_code().toString());//converting string buffer into string using substring(0)
            }

            dbg("in DBTransactionService ->p_fileName" + p_fileName);
            dbg("in DBTransactionService ->p_tableID" + p_tableID);
            // dbg("in DBTransactionService ->p_record_values" + p_record_values[0]+p_record_values[1]+p_record_values[2]);
            // PrintWriter pw;

            /*  IIndexCoreService ics = DBDependencyInjection.getIndexcoreservice();
            ics.getIndex_map().forEach((String kc, Map<String, String> vc) -> {
                tokens = kc.split("~");
                if (Integer.parseInt(tokens[0]) == p_tableID) {
                    vc.forEach((String ki, String vi) -> {
                        i_PK = ki;
                    });
                }
            });*/
             //Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_fileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
            //FileChannel fc = FileChannel.open(file, APPEND, CREATE);

            String str = getPrimaryKey(p_tableID, p_record_values);
           // dbg("i_PK and str " + i_PK + str);
            //if (!i_PK.equals(str)) {
            //sbc = Files.newByteChannel(file, StandardOpenOption.APPEND, StandardOpenOption.CREATE);

            try {
                /* try (BufferedWriter writer = Files.newBufferedWriter(file, charset, APPEND, CREATE)) {
            writer.write(l_record, 0, l_record.length());
            
            } catch (IOException x) {
            dbg(x.getMessage());
            }*/
                //sbc = Files.newByteChannel(file, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                boolean l_index_required = mds.isIndexRequired(p_fileType, this.session);
                if (l_index_required) {
                    indicator = true;
                    String l_index_file_value = null;
                    Path file1 = Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + "index" + session.getCohesiveproperties().getProperty("FILE_EXTENSION"));
                    long l_position = dbSession.getIibd_file_util().getLastPositionOfaFile(p_fileName);
                    if (file1.toFile().length() == 0) {
                        //dbg("in DBTransactionService ->p_record_values" + p_record_values[0]+p_record_values[1]+p_record_values[2]);
                        //l_index_file_value = "#".concat(String.valueOf(p_tableID)).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(sbc.position())).concat("#");
                        l_index_file_value = "#".concat(String.valueOf(p_tableID)).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(l_position)).concat("#");
                    } else {
                        // l_index_file_value = String.valueOf(p_tableID).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(sbc.position())).concat("#");
                        l_index_file_value = String.valueOf(p_tableID).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(l_position)).concat("#");
                    }
                    //l_index_position = sbc.position();
                    //l_index_position = fc.position();
                    l_index_position =l_position ;
                    dbg("in create record ->indexvalue is ->" + l_index_file_value);
                    
                    dbSession.getIibd_file_util().sequentialWrite("index", l_index_file_value);
                    //Charset charset = Charset.forName(System.getProperty("file.encoding"));
                    //writer = Files.newBufferedWriter(file1, charset, APPEND, CREATE);
                   // writer.write(l_index_file_value, 0, l_index_file_value.length());
                    l_index_map_key = String.valueOf(p_tableID).concat("~").concat(p_fileName);
                    IIndexCoreService ics = dbdi.getIndexcoreservice();
                    l_inner_map.put(str, String.valueOf(l_index_position));
                    ics.getIndex_map().put(l_index_map_key, l_inner_map);
                }

                l_record = getRecord(p_fileType, p_tableID, p_record_values);
                //ByteBuffer bfsrc = ByteBuffer.wrap(l_record.getBytes());
                //Cohesive1_Dev_2
                dbg("l_record"+l_record);
                dbSession.getIibd_file_util().sequentialWrite(l_temp_path, l_record);
                dbg("file is writed in create record");
                // sbc.write(bfsrc);
               // fc.write(bfsrc);
                // display(ics);
                //dbg("seekable byte channel position"+sbc.position());
//                dbg("size of file names to be commited"+dbSession.getFileNames_To_Be_Commited().size());
//                dbSession.setFileNames_To_Be_Commited(p_fileName);
//                dbg("set file names to be commited");
//                DBTable l_DBTable=mds.getTableMetaData(p_tableID);
//                String l_Relationship=l_DBTable.getI_Relationship();
//                String l_online_change=l_DBTable.getI_online_change();
//                if(!(l_Relationship.equals("null"))){
//                        IRelationshipService rs=dbdi.getRelationshipService();
//                        rs.createRecord(this.dbdi,p_fileName,p_fileType,p_tableID,p_record_values);
////                        ITransactionControlService tc=dbdi.getTransactionControlService();
////                        tc.commit(dbdi);
//                        dbg("createRecord method is calledd");
//                    
//                }
                 
                  
                   
                    
            } catch (IllegalArgumentException ex) {
                dbg(ex);
                if(l_session_created_now){
                      tc.rollBack(session,dbSession);
                }
                throw new DBProcessingException("Exception".concat(ex.toString()));
            } catch (UnsupportedOperationException ex) {
                dbg(ex);
                if(l_session_created_now){
                      tc.rollBack(session,dbSession);
                }
                 throw new DBProcessingException("Exception".concat(ex.toString()));
            } catch (SecurityException ex) {
                dbg(ex);
                if(l_session_created_now){
                      tc.rollBack(session,dbSession);
                }
                 throw new DBProcessingException("Exception".concat(ex.toString()));
            } finally {
                

            }
            //} //else {
            // throw new DBProcessingException("Record Already Exist");
            //}
          if(l_session_created_now){
                tc.commit(session,dbSession);
            }
        }
        catch (UnsupportedCharsetException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               
           throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
           if(l_session_created_now){
                   tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (InvalidPathException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           } if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalArgumentException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
                tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }catch(DBValidationException ex){
            if(l_session_created_now){
                try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }tc.rollBack(session,dbSession);
            }
            throw ex;
        }catch (NoSuchElementException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
         }catch (Exception ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } finally {
//             dbSession.setFileNames_To_Be_Commited(p_fileName);
            if(l_session_created_now)
            {    
             dbSession.clearSessionObject();
             session.clearSessionObject();
            }
            
        }
        
    }
    
    //Cohesive1_Dev_3 starts here
   
    
    public void createRecord(String p_fileName, String p_fileType, int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException {
        // StringBuffer l_err_code = new StringBuffer();
        //StringBuffer l_error_code = new StringBuffer();
        //l_error_code=null;
        //l_err_code=null;
        // dbg("l_error_code"+l_error_code);
        //dbg("l_error_code"+l_error_code.capacity());
        // dbg("l_error_code"+l_error_code.length()+"l_error_code");
//        dbg("inside create record of transaction service");

       ITransactionControlService tc;
        boolean l_session_created_now=false;
        String l_table_name=null;
        long startTime=0;
        try {
          
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            String folderDelimiter=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
            
            
            
            
             startTime=dbSession.getIibd_file_util(). getStartTime();
             IMetaDataService mds = dbdi.getMetadataservice();
            DBTable l_dbtable = mds.getTableMetaData(p_tableID, this.session);
            l_table_name = l_dbtable.getI_TableName();
            //Cohesive1_Dev_2 change starts 
//            lockProcessing(p_fileName);
//            String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_fileName);
            //Cohesive1_Dev_2 change ends 
            DBValidation dbv = dbSession.getDbv();
            ILockService lock=dbdi.getLockService();
            ErrorHandler errhandler = session.getErrorhandler();
            tc=dbdi.getTransactionControlService();
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);
            
            try{
               
            dbSession.getIibd_file_util().logWaitBuffer(p_fileName,l_table_name,null,"DBTransactionService","createRecord",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,false);
            }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            boolean indicator = false;

            boolean l_validation_status = true;
            boolean l_tableId_validation_status = true;
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            String l_record;
           

            //BufferedWriter writer = null;
            //SeekableByteChannel sbc = null;
            long l_index_position;
            Map<String, String> l_inner_map = new HashMap();
            String l_index_map_key = null;
            int i = 0;
            dbg("inside create record of transaction service");
            for(String s:p_record_values){
                dbg("p_record_values"+s);
            }

            /* if (!dbv.fileNameValidation(p_fileName, errhandler.getSingle_err_code())) {
                l_validation_status = false;
                errhandler.log_error();

            }*/
 /*DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_Tableid() == p_tableID) {
                        i_table_name = v.getI_TableName();
                    }
                });
            });*/
//            IMetaDataService mds = dbdi.getMetadataservice();
//            DBTable l_dbtable = mds.getTableMetaData(p_tableID, this.session);
//            l_table_name = l_dbtable.getI_TableName();

//           if(!(p_fileName.contains("LOG"))){
           if(!(p_tableID==27||p_tableID==28||p_tableID==299||p_tableID==300||p_tableID==276||p_tableID==330)){
//             if(!dbv.reportingDBValidation(session)){
              if(!dbv.reportingDBValidation(session,p_fileName)){
                 l_validation_status = false;
             }
           }  

            i = 0;
            while (i < p_record_values.length) {
                if(p_record_values[i].length()>0)//Integration change
                {
                   //if(!(p_fileName.contains("LOG") && p_tableID==28)){ //Integration change
            
                if (!dbv.specialCharacterValidation(p_record_values[i], errhandler)) {
                    dbg("in createRecord->i_tableName" + l_table_name);
//                    DBColumn l_dbcolumn = mds.getColumnMetaData(p_fileType, l_table_name, (i + 1));
                     DBColumn l_dbcolumn = mds.getColumnMetaData(p_fileType, l_table_name, (i + 1),session);
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_fileName + "," + l_table_name + "," + l_dbcolumn.getI_ColumnName());
                    errhandler.log_error();
                }
                   //}//Integration change
            }//Integration change
                i++;
            }
          
            
            if(p_fileName.contains("Primary")||p_fileName.contains("Standby")){
                
                Path filePath=Paths.get(p_fileName);
                String subPath=filePath.subpath(0, 2).toString();
                if(!subPath.equals("DB"+folderDelimiter+"ARCH")){
                    StringBuffer err = new StringBuffer("DB_VAL_000");
                    l_validation_status = false;
                    errhandler.setSingle_err_code(err);
                    errhandler.log_error();
                }
            }
            
            if(p_fileName.contains("_LOG")){
                
                Path filePath=Paths.get(p_fileName);
                String subPath=filePath.subpath(0, 1).toString();
                if(!subPath.equals("USER")){
                    StringBuffer err = new StringBuffer("DB_VAL_000");
                    l_validation_status = false;
                    errhandler.setSingle_err_code(err);
                    errhandler.log_error();
                }
            }
            
            
            if (!dbv.specialCharacterValidation(p_fileName, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_fileType, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_tableID, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            
            i = 0;
            while (i < p_record_values.length) {
                if (!dbv.columnLengthValidation(l_table_name, p_record_values[i], i + 1, errhandler,dbdi)) {
                    l_validation_status = false;
                    dbg("l_validation_status false in column length validation");
                    errhandler.log_error();
                }
                i++;
            }
            

            i=0;
            while (i < p_record_values.length) {
                if (!dbv.columnDataTypeValidation(l_table_name, i + 1, p_record_values[i], errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();
                }
                i++;
            }
         
            /* if (!dbv.fileNameValidation(p_fileName, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }*/
            if (!dbv.fileTypeValidation(p_fileType, errhandler,dbdi)) {
                l_validation_status = false;

                errhandler.log_error();

            }
          
            if (!dbv.tableIDValidation(p_tableID, errhandler,dbdi)) {
                l_validation_status = false;
                l_tableId_validation_status = false;

                errhandler.log_error();

            }
        
            //throw new DBValidationException("p_tableID validation failed in createRecord() function in DBTransactionService");
            if (l_tableId_validation_status) {
                //dbg("dbv.recordValuesValidation(errhandler, p_tableID, p_record_values)"+dbv.recordValuesValidation(errhandler, p_tableID, p_record_values));
                if (!dbv.recordValuesValidation(errhandler,dbdi, p_tableID, p_record_values)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
            }
      
            //DBTable l_dbtable = mds.getTableMetaData(p_tableID);

            i = 0;
            String l_pk = l_dbtable.getI_Pkey();
            dbg("l_pkk"+l_pk);
            String[] l_pk_ids = l_pk.split("~");
            String[] l_PK = new String[l_pk_ids.length-1];
             while (i < l_pk_ids.length-1) {//Not considering version number from the primary key
//            while (i < l_pk_ids.length) {

                l_PK[i] = p_record_values[(Integer.parseInt(l_pk_ids[i]) - 1)];
                i++;
            }
            //boolean file_status = Files.exists(Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_fileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION")));
           //Cohesive1_Dev_2 
           String l_primaryKey = getPrimaryKey(p_tableID, p_record_values);
           String l_pk_without_version=dbSession.getIibd_file_util().getPKwithoutVersion(p_fileName,l_table_name,l_primaryKey,dbdi,session);
           dbg("before file status check");
            boolean file_status = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") +p_fileName+ session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
//           boolean file_status = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + l_temp_path + session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
            if (file_status == true) {
                //Cohesive1_Dev_2
                dbg("file status is true");
                boolean status=false;
               if(! checkVersionAvailability(p_fileType,l_table_name)){
                    status = this.duplicateRecordAvailability(p_fileName, p_fileType, l_table_name, l_PK);
               }else{
                 int versionNo=  getVersionNumberFromTheRecord(p_fileType,l_table_name,p_record_values);
                    status = this.duplicateRecordAvailability(p_fileName, p_fileType, l_table_name,versionNo,"C", l_PK);
               }
//                boolean status = this.duplicateRecordAvailability(p_fileName, p_fileType, l_table_name, l_PK);
//                boolean status = this.duplicateRecordAvailability(l_temp_path, p_fileType, l_table_name, l_PK);
                dbg("in create record ->status->" + status);
                if (status == true) {
                    String replacedPkey=l_pk_without_version.replace("~", "@");
                    StringBuffer err = new StringBuffer("DB_VAL_009".concat(",").concat(l_table_name).concat(",").concat(replacedPkey));
                    l_validation_status = false;
                    errhandler.setSingle_err_code(err);
                    errhandler.log_error();
                }
            }
            if (!l_validation_status) {

                //  dbg("Error code "+errhandler.getSession_error_code());
                throw new DBValidationException(errhandler.getSession_error_code().toString());//converting string buffer into string using substring(0)
            }

            dbg("in DBTransactionService ->p_fileName" + p_fileName);
            dbg("in DBTransactionService ->p_tableID" + p_tableID);
            // dbg("in DBTransactionService ->p_record_values" + p_record_values[0]+p_record_values[1]+p_record_values[2]);
            // PrintWriter pw;

            /*  IIndexCoreService ics = DBDependencyInjection.getIndexcoreservice();
            ics.getIndex_map().forEach((String kc, Map<String, String> vc) -> {
                tokens = kc.split("~");
                if (Integer.parseInt(tokens[0]) == p_tableID) {
                    vc.forEach((String ki, String vi) -> {
                        i_PK = ki;
                    });
                }
            });*/
             //Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_fileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
            //FileChannel fc = FileChannel.open(file, APPEND, CREATE);

//            String l_primaryKey = getPrimaryKey(p_tableID, p_record_values);
           // dbg("i_PK and str " + i_PK + str);
            //if (!i_PK.equals(str)) {
            //sbc = Files.newByteChannel(file, StandardOpenOption.APPEND, StandardOpenOption.CREATE);

            try {
//                /* try (BufferedWriter writer = Files.newBufferedWriter(file, charset, APPEND, CREATE)) {
//            writer.write(l_record, 0, l_record.length());
//            
//            } catch (IOException x) {
//            dbg(x.getMessage());
//            }*/
//                //sbc = Files.newByteChannel(file, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
//                boolean l_index_required = mds.isIndexRequired(p_fileType, this.session);
//                if (l_index_required) {
//                    indicator = true;
//                    String l_index_file_value = null;
//                    Path file1 = Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + "index" + session.getCohesiveproperties().getProperty("FILE_EXTENSION"));
//                    long l_position = dbSession.getIibd_file_util().getLastPositionOfaFile(p_fileName);
//                    if (file1.toFile().length() == 0) {
//                        //dbg("in DBTransactionService ->p_record_values" + p_record_values[0]+p_record_values[1]+p_record_values[2]);
//                        //l_index_file_value = "#".concat(String.valueOf(p_tableID)).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(sbc.position())).concat("#");
//                        l_index_file_value = "#".concat(String.valueOf(p_tableID)).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(l_position)).concat("#");
//                    } else {
//                        // l_index_file_value = String.valueOf(p_tableID).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(sbc.position())).concat("#");
//                        l_index_file_value = String.valueOf(p_tableID).concat("~").concat(p_fileName).concat("~").concat(str).concat("~").concat(String.valueOf(l_position)).concat("#");
//                    }
//                    //l_index_position = sbc.position();
//                    //l_index_position = fc.position();
//                    l_index_position =l_position ;
//                    dbg("in create record ->indexvalue is ->" + l_index_file_value);
//                    
//                    dbSession.getIibd_file_util().sequentialWrite("index", l_index_file_value);
//                    //Charset charset = Charset.forName(System.getProperty("file.encoding"));
//                    //writer = Files.newBufferedWriter(file1, charset, APPEND, CREATE);
//                   // writer.write(l_index_file_value, 0, l_index_file_value.length());
//                    l_index_map_key = String.valueOf(p_tableID).concat("~").concat(p_fileName);
//                    IIndexCoreService ics = dbdi.getIndexcoreservice();
//                    l_inner_map.put(str, String.valueOf(l_index_position));
//                    ics.getIndex_map().put(l_index_map_key, l_inner_map);
//                }
//
//                l_record = getRecord(p_fileType, p_tableID, p_record_values);
//                //ByteBuffer bfsrc = ByteBuffer.wrap(l_record.getBytes());
//                //Cohesive1_Dev_2
//                dbg("l_record"+l_record);
//                dbSession.getIibd_file_util().sequentialWrite(l_temp_path, l_record);
//                dbg("file is writed in create record");
//                // sbc.write(bfsrc);
//               // fc.write(bfsrc);
//                // display(ics);
//                //dbg("seekable byte channel position"+sbc.position());
//                dbg("size of file names to be commited"+dbSession.getFileNames_To_Be_Commited().size());
               
//               String l_pk_without_version=dbSession.getIibd_file_util().getPKwithoutVersion(p_fileName,l_table_name,l_primaryKey,dbdi,session);

//                 IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();

               //if(getLock(p_fileName)==true){
                if(getImplictRecordLock(p_fileName,l_table_name,l_pk_without_version)==true){ 
               if( lock.isSameSessionRecordLock(p_fileName,l_table_name,l_pk_without_version, this.session)){    

                     tempSegment.createRecord(session,dbSession,p_fileName, p_fileType, p_tableID, l_pk_without_version, p_record_values);
                      
                      
                }
               }
               
               
               
//                if(getImplictRecordLock(p_fileName,l_table_name,l_primaryKey)==true){ 
//               if( lock.isSameSessionRecordLock(p_fileName,l_table_name,l_primaryKey, this.session)){    
//
//                     tempSegment.createRecord(session,dbSession,p_fileName, p_fileType, p_tableID, l_primaryKey, p_record_values);
//                      
//                      
//                }
//               }
//                dbSession.setFileNames_To_Be_Commited(p_fileName);
//                dbg("set file names to be commited");
//                DBTable l_DBTable=mds.getTableMetaData(p_tableID);
//                String l_Relationship=l_DBTable.getI_Relationship();
//                String l_online_change=l_DBTable.getI_online_change();
//                if(!(l_Relationship.equals("null"))){
//                        IRelationshipService rs=dbdi.getRelationshipService();
//                        rs.createRecord(this.dbdi,p_fileName,p_fileType,p_tableID,p_record_values);
////                        ITransactionControlService tc=dbdi.getTransactionControlService();
////                        tc.commit(dbdi);
//                        dbg("createRecord method is calledd");
//                    
//                }
                 
                  
                   
                    
            } catch (IllegalArgumentException ex) {
                dbg(ex);
                if(l_session_created_now){
                      tc.rollBack(session,dbSession);
                }
                throw new DBProcessingException("Exception".concat(ex.toString()));
            } catch (UnsupportedOperationException ex) {
                dbg(ex);
                if(l_session_created_now){
                      tc.rollBack(session,dbSession);
                }
                 throw new DBProcessingException("Exception".concat(ex.toString()));
            } catch (SecurityException ex) {
                dbg(ex);
                if(l_session_created_now){
                      tc.rollBack(session,dbSession);
                }
                 throw new DBProcessingException("Exception".concat(ex.toString()));
            } finally {
                

            }
            //} //else {
            // throw new DBProcessingException("Record Already Exist");
            //}
          if(l_session_created_now){
                tc.commit(session,dbSession);
            }
        }
        catch (UnsupportedCharsetException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               
           throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
           if(l_session_created_now){
                   tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (InvalidPathException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           } if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalArgumentException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
                tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }catch(DBValidationException ex){
            if(l_session_created_now){
                try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }tc.rollBack(session,dbSession);
            }
            throw ex;
        }catch (NoSuchElementException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
         }catch (Exception ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } finally {
//             dbSession.setFileNames_To_Be_Commited(p_fileName);
//           long endTime= System.currentTimeMillis();
           try{
               IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);
           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,l_table_name,null,"DBTransactionService","createRecord",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,true);
//           IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
//           String timeStamp=dbSession.getIibd_file_util().getCurrentTime();
//           DBWait dbWait=new DBWait(p_fileName,l_table_name,null,"createRecord","DBTransactioService",session.getI_session_identifier().toString(),endTime-startTime,timeStamp);
//           waitBuffer.putRecordToWaitBuffer(dbWait.getSessionID(), dbWait, session);
           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           finally{
               if(l_session_created_now)
            {    
             dbSession.clearSessionObject();
             session.clearSessionObject();
            }
           }
//           if(l_session_created_now)
//            {    
//             dbSession.clearSessionObject();
//             session.clearSessionObject();
//            }
            
        }
        
    }
    
   public void createRecord(CohesiveSession session,String p_fileName, String p_fileType, int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException{
        CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           createRecord(p_fileName,p_fileType,p_tableID,p_record_values);
       } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }finally {
           this.session=tempSession;
            
        } 
        
        
    }
   //Cohesive1_Dev_3 ends here

    /*  this function forms the primarykey for the given record values of a particular table
    in param is table id and record values
    out param is a primarykey
     */
    private String getPrimaryKey(int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException {
        String l_primaryKey = null;
        try {
            dbg("inside DBTransactionService getPrimaryKey");
            dbg("p_tableID"+p_tableID);
            IMetaDataService mds = dbdi.getMetadataservice();
            DBTable dbtable = mds.getTableMetaData(p_tableID, this.session);
            String l_pk = dbtable.getI_Pkey();
            String[] l_pk_ids = l_pk.split("~");
            l_primaryKey = p_record_values[Integer.parseInt(l_pk_ids[0]) - 1];
            //dbg("p_record_values[Integer.parseInt(l_pk_ids[0])-1]" + p_record_values[Integer.parseInt(l_pk_ids[0]) - 1] + "is");
            int counter = l_pk_ids.length - 1;//already one value is assigned to l_primaryKey
            int i = 1;
            while (counter > 0) {
                l_primaryKey = l_primaryKey.concat("~").concat(p_record_values[Integer.parseInt(l_pk_ids[i]) - 1]);
                counter--;
                i++;
            }

            //l_primaryKey.trim();
            dbg("l_primaryKey" + l_primaryKey + "is");

        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }

        return l_primaryKey;
    }

    /*this function creates the actual record  that is each column has the exact column length as specified in the DBConstants file
if column length is smaller than actual size then empty chatacter is added .    
    in param is filetype tableid and record values*/
    private String getRecord(String p_fileType, int p_tableID, String... p_record_values) throws DBProcessingException, DBValidationException {
        String l_record = null;
        try {
             
            String[] l_record_values = new String[p_record_values.length];
            Map<String, DBColumn> l_column_collection = new HashMap();
            for (int i = 0; i < p_record_values.length; i++) {
                l_record_values[i] = p_record_values[i];
            }

            dbg("in DBTransactionService ->getData->p_fileName" + p_fileType);
            dbg("in DBTransactionService ->getData->p_tableID" + p_tableID);
            // dbg("in DBTransactionService ->getData->p_record_values" + l_record_values[0]);

            // DBDependencyInjection dbdi = new DBDependencyInjection();
            l_record = "#";
            l_record = l_record.concat(String.valueOf(p_tableID));
            //System.out.println("in DBTransactionService ->getData->i_record"+i_record);
            IDBCoreService l_dbcs = dbdi.getDbcoreservice();//EJB Integration change
            
            Iterator iterator1=l_dbcs.getG_dbMetaData().keySet().iterator();
            Iterator iterator2=l_dbcs.getG_dbMetaData().values().iterator();
            while(iterator1.hasNext()&&iterator2.hasNext()){
                String l_key=(String)iterator1.next();
                DBFile l_dbfile = (DBFile) iterator2.next();
                
                if(l_key.equals(p_fileType)){
                   
                    Iterator iterator3=l_dbfile.getI_TableCollection().keySet().iterator();
                    Iterator iterator4=l_dbfile.getI_TableCollection().values().iterator();
                    while(iterator3.hasNext()&&iterator4.hasNext()){
                        String l_temp_table_id=(String)iterator3.next();
                        DBTable l_dbtable = (DBTable)iterator4.next();
                        if(l_dbtable.getI_Tableid()==p_tableID){
                            l_column_collection=l_dbtable.getI_ColumnCollection();
                        }
                        
                    }
                    
                }
                
            }
            
            
           /* l_dbcs.getG_dbMetaData().forEach((String k, DBFile v) -> {

                if (k.equals(p_fileType)) {
                    v.getI_TableCollection().forEach((String kc, DBTable vc) -> {

                        if (vc.getI_Tableid() == p_tableID) {
                            dbg("in DBTransactionService ->getData->kc" + kc);
                            //i_tableName = vc.getI_TableName();
                            i_column_collection = vc.getI_ColumnCollection();
                            /* vc.getI_ColumnCollection().forEach((String kj, DBColumn vj) -> {

                        
                        for(i=0;i<p_record_values.length;i++){
                            i_size_difference = vj.getI_ColumnLength() - p_record_values[i].length();
                        for (j = 1; j <= i_size_difference; j++) {
                            p_record_values[i] = p_record_values[i].concat(" ");
                        }
                        
                        i_record = i_record.concat("~").concat(p_record_values[i]);
                        }
                    });
                        }
                    });
                }
            });*/
          
            int l_size_difference = 0;
//           Iterator iterator5=l_column_collection.keySet().iterator();
//           Iterator iterator6=l_column_collection.values().iterator();
//           dbg("l_column_collection.size"+l_column_collection.size());
//           while(iterator5.hasNext()&&iterator6.hasNext()){
//               
//               String l_temp_column_id=(String)iterator5.next();
//               DBColumn l_temp_db_column=(DBColumn)iterator6.next();
//               dbg("l_temp_column_id"+l_temp_column_id);
//               dbg("l_temp_db_column.getI_ColumnName()"+l_temp_db_column.getI_ColumnName());
//               l_size_difference=l_temp_db_column.getI_ColumnLength()-l_record_values[Integer.parseInt(l_temp_column_id) - 1].length();
//      
//               for (int j = 1; j <= l_size_difference; j++) {
//                    l_record_values[Integer.parseInt(l_temp_column_id) - 1] = l_record_values[Integer.parseInt(l_temp_column_id) - 1].concat(" ");
//                }
//               l_record = l_record.concat("~").concat(l_record_values[Integer.parseInt(l_temp_column_id) - 1]);
//           }
           
           
               for(int i=0;i<l_column_collection.size();i++){
               String l_temp_column_id=Integer.toString(i+1);
               DBColumn l_temp_db_column=l_column_collection.get(l_temp_column_id);
               dbg("l_temp_column_id"+l_temp_column_id);
               dbg("l_temp_db_column.getI_ColumnName()"+l_temp_db_column.getI_ColumnName());
               l_size_difference=l_temp_db_column.getI_ColumnLength()-l_record_values[Integer.parseInt(l_temp_column_id) - 1].length();
      
               for (int j = 1; j <= l_size_difference; j++) {
                    l_record_values[Integer.parseInt(l_temp_column_id) - 1] = l_record_values[Integer.parseInt(l_temp_column_id) - 1].concat(" ");
                }
               l_record = l_record.concat("~").concat(l_record_values[Integer.parseInt(l_temp_column_id) - 1]);
               }
           /* i_column_collection.forEach((String k, DBColumn v) -> {
                // for(i=0;i<p_record_values.length;i++){
                //dbg("I value is"+i);
                //int i=0;
                i_size_difference = v.getI_ColumnLength() - l_record_values[Integer.parseInt(k) - 1].length();
                dbg("i_size_difference" + i_size_difference);
                for (j = 1; j <= i_size_difference; j++) {
                    l_record_values[Integer.parseInt(k) - 1] = l_record_values[Integer.parseInt(k) - 1].concat(" ");
                }

                i_record = i_record.concat("~").concat(l_record_values[Integer.parseInt(k) - 1]);

            });*/
     

            dbg("in DBTransactionService ->getData->->final->i_record" + l_record);
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }finally{
                   
        }
        return l_record;

    }

    public void updateColumn1(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
        //Scanner l_file_content = null;
        
        boolean l_session_created_now=false;
        ITransactionControlService tc;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
//            dbg("session object created inside update column");
            l_session_created_now=session.isI_session_created_now();
            //Cohesive1_Dev_2
            lockProcessing(p_file_name);
            
            String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_file_name);
            ErrorHandler errhandler = session.getErrorhandler();
            tc=dbdi.getTransactionControlService();
            DBValidation dbv = dbSession.getDbv();
            
            boolean l_validation_status = true;
            int i = 0;
            dbg("in dbtransaction service->updateColumn->p_file_name" + p_file_name);
            dbg("in dbtransaction service->updateColumn->p_file_type" + p_file_type);
            dbg("in dbtransaction service->updateColumn->p_table_name" + p_table_name);
            // dbg("in dbtransaction service->p_pkey[0]" + p_pkey[0]);
            //dbg("in dbtransaction service->p_column_to_update[0]" + p_column_to_update);
           // IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
           // IIndexCoreService ics = DBDependencyInjection.getIndexcoreservice();
            //String l_record = null;
           // String[] l_record_to_be_changed;
           // int l_index = 0;
             String col_name = null;

            //l.kSeekableByteChannel sbc = null;
            String l_record_to_be_written = null;
           // String[] l_column_values = {};
           // String[] l_column = {};
           // String l_PK = null, l_pkey = null;
          //  int array_index = 0;
            int l_col_len=0;
           // int indicator = 0;

            if (!dbv.specialCharacterValidation(p_file_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            dbg("l_validation_status"+l_validation_status);
            if (!dbv.specialCharacterValidation(p_file_type, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            dbg("l_validation_status"+l_validation_status);
            if (!dbv.specialCharacterValidation(p_table_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            dbg("l_validation_status"+l_validation_status);
            /*DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_TableName().equals(p_table_name)) {
                        i_dummy_pk = v.getI_Pkey();
                    }
                });
            });*/
            IMetaDataService mds = dbdi.getMetadataservice();
            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.session);
            String i_dummy_pk = l_dbtable.getI_Pkey();
            String[] l_dummy_pk = i_dummy_pk.split("~");

            i = 0;
            while (i < l_dummy_pk.length) {
                dbg("in updateColumn->->l_dummy_pk value is ->" + l_dummy_pk[i]);
                String l_column_name = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_dummy_pk[i]), this.session).getI_ColumnName();
                dbg("in update column before validation");
                
                if (!dbv.specialCharacterValidation(p_pkey[i], errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_column_name);
                    errhandler.log_error();

                }
                i++;
            }
            i = 0;
            dbg("in updateColumn->->l_dummy_pk.length->" + l_dummy_pk.length);
            dbg("in updateColumn->->p_pkey.length->" + p_pkey.length);

            while (i < l_dummy_pk.length) {
                dbg("in updateColumn->->i value is ->" + i);
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt(l_dummy_pk[i]), p_pkey[i], errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            i = 0;
            while (i < l_dummy_pk.length) {
                // dbg("in updateRecord->i value is ->" + i);
                if (!dbv.columnLengthValidation(p_table_name, p_pkey[i], Integer.parseInt(l_dummy_pk[i]), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            Iterator iterator = p_column_to_update.values().iterator();
            Iterator iterator1 = p_column_to_update.keySet().iterator();
            // i = 1;
            while (iterator.hasNext()) {
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt((String) iterator1.next()), (String) iterator.next(), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                // i++;
            }

            iterator = p_column_to_update.values().iterator();
            iterator1 = p_column_to_update.keySet().iterator();
            //i = 1;
            while (iterator.hasNext() && iterator1.hasNext()) {
                if (!dbv.columnLengthValidation(p_table_name, (String) iterator.next(), Integer.parseInt((String) iterator1.next()), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                //  i++;
            }

            iterator = p_column_to_update.keySet().iterator();

            while (iterator.hasNext()) {
                String l_temp = (String) iterator.next();
                dbg("in db transaction service ->updateColumn->columnIDValidation->l_temp" + l_temp);
                if (!dbv.columnIDValidation(p_file_type, p_table_name, Integer.parseInt(l_temp), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }

            }
            iterator1=p_column_to_update.keySet().iterator();
            iterator = p_column_to_update.values().iterator();
            i = 1;
            while (iterator1.hasNext()) {
                DBColumn l_dbcolumn = mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt((String)iterator1.next()), this.session);
                String l_variable = (String) iterator.next();
                dbg("l_variable"+l_variable);
                if(l_variable.length()>0)//Integration chnages
                {    
                if (!dbv.specialCharacterValidation(l_variable, errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_dbcolumn.getI_ColumnName());
                    errhandler.log_error();

                }
                }//Integration changes
                i++;
            }

            if (!dbv.fileNameValidation(p_file_name, errhandler,session,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }

            if (!dbv.fileTypeValidation(p_file_type, errhandler,dbdi)) {
                l_validation_status = false;

                errhandler.log_error();

            }

            //if (!dbv.tableNameValidation(p_table_name, errhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_file_type, p_table_name, errhandler,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            /* String l_pk = mds.getTableMetaData(p_file_type, p_table_name, this.dbdi).getI_Pkey();
            String[] l_pk_ids = l_pk.split("~");
            //dbg("in update record ->l_pk_ids->" + l_pk_ids[0]);
            String[] l_prime_key = new String[l_pk_ids.length];
            i = 0;
            p_column_to_update.forEach((String kc, String vc) -> {
                dbg("kc value " + kc + "is");
                if (i < l_pk_ids.length) {
                    if (l_pk_ids[i].equals(kc)) {
                        l_prime_key[i] = vc;
                        dbg("vc is " + vc + "is");
                    }
                }
                i++;

            });
            String l_primary_key = null;
            i = 0;
            while (i < l_prime_key.length) {
                if (i == 0) {
                    l_primary_key = l_prime_key[i];
                    dbg("in transaction service->update column->l_primary_key->"+l_primary_key);
                } else {
                    l_primary_key = l_primary_key.concat("~").concat(l_prime_key[i]);
                    dbg("in transaction service->update column->l_primary_key->"+l_primary_key);
                }
                //dbg("the prime_key" + l_prime_key[i] + "is");
                i++;
            }*/
 /*boolean status = this.duplicateRecordAvailability(p_file_name, p_file_type, p_table_name, l_prime_key);
            dbg("in dbtransacton service-> updateColumn->status" + status);
            if (status == true) {
                StringBuffer err = new StringBuffer("DB_VAL_009");
                l_validation_status = false;
                errhandler.setSingle_err_code(err);
                errhandler.log_error();
            }*/

            if (!l_validation_status) {
                //  dbg("Error code "+errhandler.getSession_error_code());
                throw new DBValidationException(errhandler.getSession_error_code().substring(0));//converting string buffer into string using substring(0)
            }
            long l_position=0;
            String l_primary_key = formingPrimaryKey(p_pkey);
            dbg("in transaction service ->update column->l_primary_key" + l_primary_key);
            boolean l_index_status = mds.isIndexRequired(p_file_type, session);
            dbg("in transaction service ->update column->l_index_status" + l_index_status);
            int l_TableId = mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid();
            dbg("in transaction service ->update column->l_TableId" + l_TableId);
            if (l_index_status) {
                IIndexReadService irs = dbdi.getIndexreadservice();
                l_position = irs.readIndex(p_file_name, String.valueOf(l_TableId), l_primary_key, session);
                dbg("in transaction service ->update column->l_position" + l_position);
                //l_position+=1;

            }
            if ((!l_index_status) || l_position == -1) {

                Map<String, String> l_dummy_column = new HashMap();
                String l_table_id = String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid());
                dbg("in transaction service ->update column->p_file_name+l_table_id+p_pkey[0]" + p_file_name + l_table_id + "   " + p_pkey[0]);
                 //Cohesive1_Dev_2
                PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, Integer.parseInt(l_table_id), p_pkey, l_dummy_column, session,dbdi);
                dbg("in transaction service ->update column->par.getI_position()" + par.getI_position());
                l_position = par.getI_position();

                i = 1;
            }
//            int l_sizeofmap=mds.getTableMetaData(p_file_type, p_table_name, dbdi).getI_ColumnCollection().size();
//            for(int j=0;j<l_sizeofmap;j++){
//                DBColumn l_dbcolumn=mds.getTableMetaData(p_file_type, p_table_name, dbdi).getI_ColumnCollection().get(Integer.toString(j+1));
//                l_col_len = l_col_len + l_dbcolumn.getI_ColumnLength();
//            }
            Iterator iterator2=mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().values().iterator();
                while(iterator2.hasNext()){
                    DBColumn l_dbcolumn=(DBColumn) iterator2.next();
                    l_col_len = l_col_len + l_dbcolumn.getI_ColumnLength();
                }
            
            
            /*mds.getTableMetaData(p_file_type, p_table_name, dbdi).getI_ColumnCollection().forEach((String k, DBColumn v) -> {
                i_col_len = i_col_len + v.getI_ColumnLength();
            });*/
            ByteBuffer copy = ByteBuffer.allocate(l_col_len + 1 + String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid()).length()
                    + mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().size());
            //Cohesive1_Dev_2
            dbg("l_temp_path"+l_temp_path);
            dbg("p_table_name"+p_table_name);
            dbg("copy.capacity()"+copy.capacity());
               dbg("l_position"+l_position);
            String converted = dbSession.getIibd_file_util().randomRead(l_temp_path, p_table_name, copy.capacity(), l_position,session,dbdi);
            String[] l_temp_column_values = converted.split("~");
            dbg("in transaction service ->update column->converted" + converted);
            
             
            Iterator iterator3=p_column_to_update.keySet().iterator();
            Iterator iterator4=p_column_to_update.values().iterator();
            
//            for(int j=0;j<p_column_to_update.size();j++){
//                    String l_key =Integer.toString(j+1);
//                    dbg("l_key"+l_key);
//               
//                    String l_value=p_column_to_update.get(Integer.toString(j+1));
//                         dbg("l_value"+l_value);
//                     int l_column_length=mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_key), dbdi).getI_ColumnLength();
//                
//                int l_length_difference = l_column_length - l_value.length();
//                    dbg("in transaction service ->update column->i_length_difference " + l_length_difference);
//                    col_name = l_value;
//                    while (l_length_difference != 0) {
//                        col_name = col_name + " ";
//                        --l_length_difference;
//                    }
//                    l_temp_column_values[j] = col_name;
//                }
            while(iterator3.hasNext()&&iterator4.hasNext()){
                String l_key = (String)iterator3.next();
                String l_value = (String)iterator4.next();
                dbg("l_key"+l_key);
                dbg("l_value"+l_value);
                int l_column_length=mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_key), session).getI_ColumnLength();
                
                int l_length_difference = l_column_length - l_value.length();
                    dbg("in transaction service ->update column->i_length_difference " + l_length_difference);
                    col_name = l_value;
                    while (l_length_difference != 0) {
                        col_name = col_name + " ";
                        --l_length_difference;
                    }
                    l_temp_column_values[Integer.parseInt(l_key)] = col_name;
                
            }
            
            

            /*p_column_to_update.forEach((String cid, String cvalue) -> {
                try {
                    // l_Column_Collection.forEach((String k, DBColumn v) -> {

                    //if (Integer.parseInt(cid)==i) {
                    //dbg("in transaction service ->update column->cid and i " + cid + "   " + i);
                    //try{
                    l_column_length = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(cid), dbdi).getI_ColumnLength();
                    //}catch(DBValidationException ex){
                    //   dbg(ex);
                    //throw new DBValidationException("DBValidationException"+ex.toString());
                    //}catch(DBProcessingException ex){
                    //    dbg(ex);
                    //throw new DBProcessingException("DBValidationException"+ex.toString());  
                    // }
                    i_length_difference = l_column_length - cvalue.length();
                    dbg("in transaction service ->update column->i_length_difference " + i_length_difference);
                    col_name = cvalue;
                    while (i_length_difference != 0) {
                        col_name = col_name + " ";
                        --i_length_difference;
                    }
                    l_temp_column_values[Integer.parseInt(cid)] = col_name;
                    //j=1;
                    //i=1;
                    /* while (i <= Integer.parseInt(cid)) {
                                if (i == 1) {
                                    l_position = l_position + 2+String.valueOf(mds.getTableMetaData(p_file_type, p_table_name,dbdi).getI_Tableid()).length();
                                } else {
                                    //j = 1;
                                    //  while (j < i) {
                                    l_position = l_position +mds.getColumnMetaData(p_file_type, p_table_name, (i - 1), dbdi).getI_ColumnLength() + 1;
                                    // j++;
                                    // }

                                }
                                i++;
                            }
                    dbg("in transaction service ->update column->col_name" + col_name + "is");

                } catch (DBValidationException ex) {
                    dbg(ex);

                } catch (DBProcessingException ex) {
                    dbg(ex);

                }

            });*/
            l_record_to_be_written = "#".concat(String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid())).concat("~").concat(l_temp_column_values[1]);
             i = 2;
            while (i < l_temp_column_values.length) {
                l_record_to_be_written = l_record_to_be_written.concat("~").concat(l_temp_column_values[i]);
                i++;
            }
            //try {
                //Cohesive1_Dev_2
                dbSession.getIibd_file_util().randomWrite(l_temp_path, l_position, l_record_to_be_written);
                
//                dbSession.setFileNames_To_Be_Commited(p_file_name);
                
                /*Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                fc = FileChannel.open(file, READ, WRITE);
                fc.position(l_position);
                fc.write(ByteBuffer.wrap(l_record_to_be_written.getBytes(Charset.forName("UTF-8"))));

                dbg("in transaction service ->update column->l_position after increment->" + l_position);*/

            //} catch (IOException ex) {
             //   dbg(ex);

            //}

            /*  p_column_to_update.forEach((String cid, String cvalue) -> {
                        try {
                            // l_Column_Collection.forEach((String k, DBColumn v) -> {

                            //if (Integer.parseInt(cid)==i) {
                            dbg("in transaction service ->update column->cid and i " + cid + "   " + i);
                            //try{
                            l_column_length = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(cid), dbdi).getI_ColumnLength();
                            //}catch(DBValidationException ex){
                            //   dbg(ex);
                            //throw new DBValidationException("DBValidationException"+ex.toString());
                            //}catch(DBProcessingException ex){
                            //    dbg(ex);
                            //throw new DBProcessingException("DBValidationException"+ex.toString());  
                            // }
                            i_length_difference = l_column_length - cvalue.length();
                            dbg("in transaction service ->update column->i_length_difference " + i_length_difference);
                            col_name = cvalue;
                            while (i_length_difference != 0) {
                                col_name = col_name + " ";
                                --i_length_difference;
                            }
                            l_temp_column_values[Integer.parseInt(cid)] = col_name;
                            //j=1;
                            //i=1;
                            while (i <= Integer.parseInt(cid)) {
                                if (i == 1) {
                                    l_position = l_position + 2+String.valueOf(mds.getTableMetaData(p_file_type, p_table_name,dbdi).getI_Tableid()).length();
                                } else {
                                    //j = 1;
                                    //  while (j < i) {
                                    l_position = l_position +mds.getColumnMetaData(p_file_type, p_table_name, (i - 1), dbdi).getI_ColumnLength() + 1;
                                    // j++;
                                    // }

                                }
                                i++;
                            }
                            dbg("in transaction service ->update column->col_name" + col_name + "is");
                            
                            
                        } catch (DBValidationException ex) {
                            dbg(ex);
                           
                        } catch (DBProcessingException ex) {
                            dbg(ex);
                             
                        }
                        
                    });
                     l_record_to_be_written = "#".concat(String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, dbdi).getI_Tableid())).concat("~").concat(l_temp_column_values[1]);
                     int i=2;
                     while(i<l_temp_column_values.length){
                         l_record_to_be_written=l_record_to_be_written.concat("~").concat(l_temp_column_values[i]);
                         i++;
                     }
                    try {
                               
                                Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                                fc = FileChannel.open(file, READ, WRITE);
                                fc.position(l_position);
                                fc.write(ByteBuffer.wrap(l_record_to_be_written.getBytes(Charset.forName("UTF-8"))));
                                
                                dbg("in transaction service ->update column->l_position after increment->" + l_position);

                            } catch (IOException ex) {
                                dbg(ex);
                                
                            }*/
 /* p_column_to_update.forEach((String cid, String cvalue) -> {
                    try {

                        dbg("in transaction service ->update column->cid and cvalue " + cid + "   " + cvalue);

                        l_column_length = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(cid), dbdi).getI_ColumnLength();

                        i_length_difference = l_column_length - cvalue.length();
                        dbg("in transaction service ->update column->i_length_difference " + i_length_difference);
                        col_name = cvalue;
                        while (i_length_difference != 0) {
                            col_name = col_name + " ";
                            --i_length_difference;
                        }
                        //dbg("in transaction service ->update column->col_name"+col_name);
                        while (i <= Integer.parseInt(cid)) {
                            if (i == 1) {
                                l_position = l_position + l_table_id.length() + 2;
                            } else {

                                l_position = l_position + mds.getColumnMetaData(p_file_type, p_table_name, (i - 1), dbdi).getI_ColumnLength() + 1;

                            }
                            i++;
                        }
                        dbg("in transaction service ->update column->col_name" + col_name + "is");
                        try {
                            //if(cid.equals("1"))
                            // l_position=l_position+3;//first column of any record will have #,tableid and ~ so l_position is 3
                            //else
                            //   l_position=l_position+1;
                            dbg("in transaction service ->update column->l_position before fc->" + l_position);
                            Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                            fc = FileChannel.open(file, READ, WRITE);
                            fc.position(l_position);
                            fc.write(ByteBuffer.wrap(col_name.getBytes(Charset.forName("UTF-8"))));
                            // l_position =l_position+l_column_length+1;
                            //fc.position(l_position);
                            dbg("in transaction service ->update column->l_position after increment->" + l_position);

                        } catch (IOException ex) {
                            dbg(ex);

                        }

                    } catch (DBValidationException ex) {
                        dbg(ex);

                    } catch (DBProcessingException ex) {
                        dbg(ex);

                    }
                });*/

 /* 
                dbg("in dbtransaction service-> update column->l_table_id" + l_table_id);
                l_pkey = formingPrimaryKey(p_pkey);
                l_file_content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
                l_file_content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));

                while (l_file_content.hasNext()) {
                    dbg("in dbtransaction service->update column->insidewhile");

                    String samp = l_file_content.next();
                    if (samp.contains("~")) {
                        l_column_values = samp.split(dbcs.getI_db_properties().getProperty("COLUMN_DELIMITER"));
                        dbg("in dbtransaction service->update column->l_column_values[0]" + l_column_values[0]);
                        if (l_table_id.equals(l_column_values[0])) {
                            dbg("in dbtransaction service->update column->inside IF");
                            l_column = new String[l_column_values.length - 1];
                            while (array_index < l_column_values.length - 1) {
                                l_column[array_index] = l_column_values[array_index + 1];
                                dbg("in dbtransaction service->update column->l_column" + l_column[array_index]);
                                array_index++;

                            }
                            //dbg("in dbtransaction service->l_column" + l_pkey);
                            //dbg("in dbtransaction service->l_table_id"+l_table_id);
                            //dbg("in dbtransaction service->l_PK"+getPrimaryKey(Integer.parseInt(l_table_id), l_column));
                            l_PK = getPrimaryKey(Integer.parseInt(l_table_id), l_column);
                            l_PK = triming(l_PK);
                            dbg("in dbtransaction service->update column->l_pkey" + l_pkey);
                            dbg("in dbtransaction service->update column->l_PK" + l_PK);
                            //l_pkey = getPrimaryKey(Integer.parseInt(l_table_id), p_record);
                            if (l_PK.equals(l_pkey)) {
                                // indicator = 1;
                                //  l_record_to_be_written = recordToUpdate(p_file_type, l_table_id, l_column, p_column_to_update);
                                //  dbg("in dbtransaction service->update column->->l_record_to_be_written" + l_record_to_be_written);
                                
                                dbg("in dbtransaction service->update column->l_position" + l_position);
                               // fc.position(l_position);
                                dbg("in dbtransaction service->update column->l_position" + l_position);

                               // Map<String,DBColumn> l_Column_Collection = mds.getTableMetaData(l_TableId, dbdi).getI_ColumnCollection();
                               
                                i=1;
                                
                                p_column_to_update.forEach((String cid, String cvalue) -> {
                        try {
                           
                            dbg("in transaction service ->update column->cid and i " + cid + "   " + i);
                           
                            l_column_length = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(cid), dbdi).getI_ColumnLength();
                            
                            i_length_difference = l_column_length - cvalue.length();
                            dbg("in transaction service ->update column->i_length_difference " + i_length_difference);
                            col_name = cvalue;
                            while (i_length_difference != 0) {
                                col_name = col_name + " ";
                                --i_length_difference;
                            }
                            
                            while (i <= Integer.parseInt(cid)) {
                                if (i == 1) {
                                    l_position = l_position + 3;
                                } else {
                                    
                                        l_position = l_position + mds.getColumnMetaData(p_file_type, p_table_name, (i-1), dbdi).getI_ColumnLength() + 1;
                                      
                                    
                                }
                                i++;
                            }
                            dbg("in transaction service ->update column->col_name" + col_name + "is");
                            try {
                                //if(cid.equals("1"))
                                // l_position=l_position+3;//first column of any record will have #,tableid and ~ so l_position is 3
                                //else
                                //   l_position=l_position+1;
                                Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                                fc = FileChannel.open(file, READ, WRITE);
                                fc.position(l_position);
                                fc.write(ByteBuffer.wrap(col_name.getBytes(Charset.forName("UTF-8"))));
                                // l_position =l_position+l_column_length+1;
                                //fc.position(l_position);
                                dbg("in transaction service ->update column->l_position after increment->" + l_position);

                            } catch (IOException ex) {
                                dbg(ex);
                               
                            }
                            
                        } catch (DBValidationException ex) {
                            dbg(ex);
                            
                        } catch (DBProcessingException ex) {
                            dbg(ex);
                            
                        }
                    });

                                
                                
                                
                                
                                
                              
                            } else {
                                l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]))+2;//2 is for # and tableid
                            }
                        } else {
                            dbg("in db transaction service->update column->legth of the record" + lengthOfTheRecord(Integer.parseInt(l_column_values[0])));
                            l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]))+2;//2 is for # and tableid
                        }
                    } else {
                        l_position = l_position + lengthOfTheRecord(Integer.parseInt(samp.trim()))+2;//2 is for # and tableid
                    }
                }*/
               int l_tableID =mds.getTableMetaData(p_file_type,p_table_name, this.session).getI_Tableid();
//                DBTable l_DBTable=mds.getTableMetaData(l_tableID);
//                String l_Relationship=l_DBTable.getI_Relationship();
//                dbg("update column completed inside transaction service");
//                 if(!(l_Relationship.equals("null"))){
//                IRelationshipService rs=dbdi.getRelationshipService();
//                rs.updateColumn(p_file_name, p_file_type, p_table_name, p_pkey, p_column_to_update,this.dbdi);
//                 
//                 }              
 
 
               if(l_session_created_now){
                     
                     tc.commit(session,dbSession);
                 }
        } catch (IllegalStateException ex) {
            dbg(ex);
            if(l_session_created_now){
                try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
                tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
               try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
           }
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        }catch(DBValidationException ex){
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw ex;
        }catch (Exception ex) {
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
           // dbSession.setFileNames_To_Be_Commited(p_file_name);
            if(l_session_created_now)
            {    
            dbSession.clearSessionObject();
            session.clearSessionObject();
            }
        }    
            
    }        
    
    
    public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
        //Scanner l_file_content = null;
        
        boolean l_session_created_now=false;
        ITransactionControlService tc;
        long startTime=0;
        String l_pk=null;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            startTime=dbSession.getIibd_file_util(). getStartTime();
            //Cohesive1_Dev_2
//            lockProcessing(p_file_name);
            
            String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_file_name);
            ErrorHandler errhandler = session.getErrorhandler();
            tc=dbdi.getTransactionControlService();
             l_pk=formingPrimaryKey(p_pkey);
              IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);
			
			try{
            dbSession.getIibd_file_util().logWaitBuffer(p_file_name,p_table_name,l_pk,"DBTransactionService","updateColumn",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,false);
            }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
             
            DBValidation dbv = dbSession.getDbv();
            
            boolean l_validation_status = true;
            int i = 0;
            dbg("in dbtransaction service->updateColumn->p_file_name" + p_file_name);
            dbg("in dbtransaction service->updateColumn->p_file_type" + p_file_type);
            dbg("in dbtransaction service->updateColumn->p_table_name" + p_table_name);
            
             String col_name = null;

            String l_record_to_be_written = null;
           
            int l_col_len=0;

            if(!(p_table_name.equals("ARCH_APPLY_STATUS")||p_table_name.equals("ARCH_SHIPPING_STATUS")||p_table_name.equals("CLASS_MARK_REPORT")||p_table_name.equals("SUBSTITUTE_AVAIL_SAME_CLASS"))){
                if(!dbv.reportingDBValidation(session,p_file_name)){
                 l_validation_status = false;
                 }
             }
            
            
            if (!dbv.specialCharacterValidation(p_file_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            dbg("l_validation_status"+l_validation_status);
            if (!dbv.specialCharacterValidation(p_file_type, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            dbg("l_validation_status"+l_validation_status);
            if (!dbv.specialCharacterValidation(p_table_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            dbg("l_validation_status"+l_validation_status);
            
            IMetaDataService mds = dbdi.getMetadataservice();
            
            String[] l_dummy_pk=getPKcolumnswithoutVersion(p_file_type,p_table_name);
//            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.session);
//            String i_dummy_pk = l_dbtable.getI_Pkey();
//            String[] l_dummy_pk = i_dummy_pk.split("~");

            
            
            i = 0;
            while (i < l_dummy_pk.length) {
                dbg("in updateColumn->->l_dummy_pk value is ->" + l_dummy_pk[i]);
                String l_column_name = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_dummy_pk[i]), this.session).getI_ColumnName();
                dbg("in update column before validation");
                
                if (!dbv.specialCharacterValidation(p_pkey[i], errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_column_name);
                    errhandler.log_error();

                }
                i++;
            }
            i = 0;
            dbg("in updateColumn->->l_dummy_pk.length->" + l_dummy_pk.length);
            dbg("in updateColumn->->p_pkey.length->" + p_pkey.length);

            while (i < l_dummy_pk.length) {
                dbg("in updateColumn->->i value is ->" + i);
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt(l_dummy_pk[i]), p_pkey[i], errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            i = 0;
            while (i < l_dummy_pk.length) {
                if (!dbv.columnLengthValidation(p_table_name, p_pkey[i], Integer.parseInt(l_dummy_pk[i]), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            Iterator iterator = p_column_to_update.values().iterator();
            Iterator iterator1 = p_column_to_update.keySet().iterator();
            while (iterator.hasNext()) {
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt((String) iterator1.next()), (String) iterator.next(), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
            }

            iterator = p_column_to_update.values().iterator();
            iterator1 = p_column_to_update.keySet().iterator();
            while (iterator.hasNext() && iterator1.hasNext()) {
                if (!dbv.columnLengthValidation(p_table_name, (String) iterator.next(), Integer.parseInt((String) iterator1.next()), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
            }

            iterator = p_column_to_update.keySet().iterator();

            while (iterator.hasNext()) {
                String l_temp = (String) iterator.next();
                dbg("in db transaction service ->updateColumn->columnIDValidation->l_temp" + l_temp);
                if (!dbv.columnIDValidation(p_file_type, p_table_name, Integer.parseInt(l_temp), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }

            }
            iterator1=p_column_to_update.keySet().iterator();
            iterator = p_column_to_update.values().iterator();
            i = 1;
            while (iterator1.hasNext()) {
                DBColumn l_dbcolumn = mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt((String)iterator1.next()), this.session);
                String l_variable = (String) iterator.next();
                dbg("l_variable"+l_variable);
                 if(l_variable.length()>0) //Integrarion changes
                 {   
                if (!dbv.specialCharacterValidation(l_variable, errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_dbcolumn.getI_ColumnName());
                    errhandler.log_error();

                }
                 }//Integration changes
                i++;
            }

            if (!dbv.fileNameValidation(p_file_name, errhandler,session,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }

            if (!dbv.fileTypeValidation(p_file_type, errhandler,dbdi)) {
                l_validation_status = false;

                errhandler.log_error();

            }

            if (!dbv.tableNameValidation(p_file_type, p_table_name, errhandler,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            
            dbg("before duplicate record availability check");
            String l_pkArr[]=mds.getTableMetaData(p_file_type, p_table_name, this.session).getI_Pkey().split("~");
            String[] l_prime_key=new String[l_pkArr.length-1];//-1 for not considering versionNumber
            boolean isdupCheckNeeded=false;
            
            for(String s:l_pkArr){
                dbg("l_pkArr"+s);
            }
            for(String s:p_pkey){
                dbg("p_pkey"+s);
            }
            p_column_to_update.forEach((String k,String v)->{
                dbg("p_column_to_update--->key"+k);
                dbg("p_column_to_update--->value"+v);
            }
            );
            
            for(int j=0;j<l_pkArr.length-1;j++){ //l_pkArr has column ids of primary key
                
            if( p_column_to_update.containsKey(l_pkArr[j])){
                  l_prime_key[j]=p_column_to_update.get(l_pkArr[j]);
                   dbg("column to update contains primary key column");
                if(  p_column_to_update.get(l_pkArr[j]).equals(p_pkey[j])){//p_pkey is the primary comes from the parameter
                     
                    if(isdupCheckNeeded!=true){
                     isdupCheckNeeded=false;//value in p_column_to_update is equal to the primary key
                    }
                 }else{
                     isdupCheckNeeded=true;//value in p_column_to_update is not equal to the primary  key
                 }
                 
            }
            l_prime_key[j]=p_pkey[j];
            }
            
            dbg("isdupCheckNeeded"+isdupCheckNeeded);
            
//            boolean status=false;
            if(isdupCheckNeeded==true){
//            if(! checkVersionAvailability(p_file_type,p_table_name)){
//                    status = this.duplicateRecordAvailability(p_file_name, p_file_type, p_table_name, l_prime_key);
//               }else{
//                 int versionNo=  getVersionNumberFromTheRecord(p_file_type,p_table_name,p_record_values);
//                    status = this.duplicateRecordAvailability(p_file_name, p_file_type, p_table_name,versionNo,"C", l_PK);
//               }
                boolean status = this.duplicateRecordAvailability(p_file_name, p_file_type, p_table_name, l_prime_key);
            
            dbg("in dbtransacton service-> update Record - >status" + status);
            if (status == true) {
                StringBuffer err = new StringBuffer("DB_VAL_009");
                l_validation_status = false;
                errhandler.setSingle_err_code(err);
                errhandler.log_error();
            }
            }
            
            
            
            if (!l_validation_status) {
                throw new DBValidationException(errhandler.getSession_error_code().substring(0));//converting string buffer into string using substring(0)
            }
//            long l_position=0;
//            String l_primary_key = formingPrimaryKey(p_pkey);
//            dbg("in transaction service ->update column->l_primary_key" + l_primary_key);
//            boolean l_index_status = mds.isIndexRequired(p_file_type, session);
//            dbg("in transaction service ->update column->l_index_status" + l_index_status);
//            int l_TableId = mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid();
//            dbg("in transaction service ->update column->l_TableId" + l_TableId);
//            if (l_index_status) {
//                IIndexReadService irs = dbdi.getIndexreadservice();
//                l_position = irs.readIndex(p_file_name, String.valueOf(l_TableId), l_primary_key, session);
//                dbg("in transaction service ->update column->l_position" + l_position);
//
//            }
//            if ((!l_index_status) || l_position == -1) {
//
//                Map<String, String> l_dummy_column = new HashMap();
//                String l_table_id = String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid());
//                dbg("in transaction service ->update column->p_file_name+l_table_id+p_pkey[0]" + p_file_name + l_table_id + "   " + p_pkey[0]);
//                 //Cohesive1_Dev_2
//                PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, Integer.parseInt(l_table_id), p_pkey, l_dummy_column, session,dbdi);
//                dbg("in transaction service ->update column->par.getI_position()" + par.getI_position());
//                l_position = par.getI_position();
//
//                i = 1;
//            }
////           
//            Iterator iterator2=mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().values().iterator();
//                while(iterator2.hasNext()){
//                    DBColumn l_dbcolumn=(DBColumn) iterator2.next();
//                    l_col_len = l_col_len + l_dbcolumn.getI_ColumnLength();
//                }
//            
//            
//            
//            ByteBuffer copy = ByteBuffer.allocate(l_col_len + 1 + String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid()).length()
//                    + mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().size());
//            //Cohesive1_Dev_2
//            dbg("l_temp_path"+l_temp_path);
//            dbg("p_table_name"+p_table_name);
//            dbg("copy.capacity()"+copy.capacity());
//               dbg("l_position"+l_position);
//            String converted = dbSession.getIibd_file_util().randomRead(l_temp_path, p_table_name, copy.capacity(), l_position,session,dbdi);
//            String[] l_temp_column_values = converted.split("~");
//            dbg("in transaction service ->update column->converted" + converted);
//            
//             
//            Iterator iterator3=p_column_to_update.keySet().iterator();
//            Iterator iterator4=p_column_to_update.values().iterator();
//            
//            while(iterator3.hasNext()&&iterator4.hasNext()){
//                String l_key = (String)iterator3.next();
//                String l_value = (String)iterator4.next();
//                dbg("l_key"+l_key);
//                dbg("l_value"+l_value);
//                int l_column_length=mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_key), session).getI_ColumnLength();
//                
//                int l_length_difference = l_column_length - l_value.length();
//                    dbg("in transaction service ->update column->i_length_difference " + l_length_difference);
//                    col_name = l_value;
//                    while (l_length_difference != 0) {
//                        col_name = col_name + " ";
//                        --l_length_difference;
//                    }
//                    l_temp_column_values[Integer.parseInt(l_key)] = col_name;
//                
//            }
//            
//           
//            l_record_to_be_written = "#".concat(String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid())).concat("~").concat(l_temp_column_values[1]);
//             i = 2;
//            while (i < l_temp_column_values.length) {
//                l_record_to_be_written = l_record_to_be_written.concat("~").concat(l_temp_column_values[i]);
//                i++;
//            }
//           
//                dbSession.getIibd_file_util().randomWrite(l_temp_path, l_position, l_record_to_be_written);
//                
//                IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
                ILockService lock=dbdi.getLockService();
               //if(getLock(p_file_name)==true){
                 // if( lock.isSameSessionLock(p_file_name, this.session)){ 
              
           l_pk=formingPrimaryKey(p_pkey);
if(getImplictRecordLock(p_file_name,p_table_name,l_pk)==true){ 
               if( lock.isSameSessionRecordLock(p_file_name,p_table_name,l_pk, this.session)){    

                     tempSegment.updateColumn(p_file_name, p_file_type, p_table_name, p_pkey, p_column_to_update, session,dbSession);
                      
                      
                }
               }


//                dbSession.setFileNames_To_Be_Commited(p_file_name);
                
                  
 
 
               if(l_session_created_now){
                     
                     tc.commit(session,dbSession);
                 }
        } catch (IllegalStateException ex) {
            dbg(ex);
            if(l_session_created_now){
                try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }
                tc.rollBack(session,dbSession);
            }
           
            
//
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
               try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
           }
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
           try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
        }catch(DBValidationException ex){
            try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw ex;
        }catch (Exception ex) {
             try {
               tc=dbdi.getTransactionControlService();
           } catch (NamingException ex1) {
               throw new DBProcessingException("Exception".concat(ex1.toString()).concat(ex.toString()));
  
           }dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
//            dbSession.setFileNames_To_Be_Commited(p_file_name);
          try{
           IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);    
           dbSession.getIibd_file_util().logWaitBuffer(p_file_name,p_table_name,l_pk,"DBTransactionService","updateColumn",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,true);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           finally{
               if(l_session_created_now)
            {    
             dbSession.clearSessionObject();
             session.clearSessionObject();
            }
           }
//            if(l_session_created_now)
//            {    
//            dbSession.clearSessionObject();
//            session.clearSessionObject();
//            }
        }    
            
    }   
        
    private String[] getPKcolumnswithoutVersion(String p_file_type,String p_table_name)throws DBProcessingException,DBValidationException{
        
        try{
            boolean l_versionStatus=false;
            IMetaDataService mds=dbdi.getMetadataservice();
            dbg("inside getPKwithoutVersion--->");
            
            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.session);
            String l_pkeyID = l_dbtable.getI_Pkey();
           // String l_pkIds =null;
            Map<String, DBColumn>l_columnCollection= l_dbtable.getI_ColumnCollection();
            Iterator columnIterator=l_columnCollection.values().iterator();
         String[] l_pkIds=null;
            while(columnIterator.hasNext()){
             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
             if(l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")){
                 l_versionStatus=true;
                 dbg("version status is true");
             }else{
                 l_versionStatus=false;
                 dbg("version status is false");
             }
             if(l_versionStatus==true)
                 break;
            }
            
            if(l_versionStatus){
              
                if(l_pkeyID.contains("~")){
                
                  l_pkIds = l_pkeyID.substring(0, l_pkeyID.lastIndexOf("~")).split("~");
              
                }else{
                    
                    l_pkIds=  l_pkeyID.split("~");
                }
              
              
            }else{
              l_pkIds=  l_pkeyID.split("~");
            }
            
//            if(l_versionStatus){
//                
//              l_pkIds = l_pkeyID.substring(0, l_pkeyID.lastIndexOf("~")).split("~");
//            }else{
//              l_pkIds=  l_pkeyID.split("~");
//            }
            
            return l_pkIds;
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
    }
    
     public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException{
         CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           dbg("inside cross call of update column");
           updateColumn(p_file_name, p_file_type,p_table_name,p_pkey,p_column_to_update);
       }catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }finally {
           this.session=tempSession;
            
        }
     }
  

    public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
            boolean l_session_created_now=false;
            
            ITransactionControlService tc;
        try {
            tc = dbdi.getTransactionControlService();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }
        try {
            
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            //Cohesive1_Dev_2
            lockProcessing(p_file_name);
            String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_file_name);
            ErrorHandler errhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            
            boolean l_validation_status = true;
            int i=0;
            dbg("in dbtransaction service->updateColumnWithFilter->p_file_name" + p_file_name);
            dbg("in dbtransaction service->updateColumnWithFilter->p_file_type" + p_file_type);
            dbg("in dbtransaction service->updateColumnWithFilter->p_table_name" + p_table_name);
            // dbg("in dbtransaction service->p_pkey[0]" + p_pkey[0]);
            //dbg("in dbtransaction service->p_column_to_update[0]" + p_column_to_update);
           // IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
            // IIndexCoreService ics = DBDependencyInjection.getIndexcoreservice();
            IMetaDataService mds = dbdi.getMetadataservice();
            //String l_record = null;
            //String[] l_record_to_be_changed;
            //int l_index = 0;

            //l.kSeekableByteChannel sbc = null;
            //String l_record_to_be_written = null;
            //String[] l_column_values = {};
            //String[] l_column = {};
            //String l_PK = null, l_pkey = null;
            //int array_index = 0;
            //int indicator = 0;
            if (!dbv.specialCharacterValidation(p_file_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_file_type, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_table_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            Iterator iterator = p_filter_column.keySet().iterator();
            Iterator iterator1 = p_filter_column.values().iterator();
            while (iterator1.hasNext()) {
                if (!dbv.specialCharacterValidation((String) iterator1.next(), errhandler)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }

            }
            while (iterator.hasNext()) {
                  String l_temp = (String) iterator.next();
                  
                dbg("in db transaction service ->updateColumnWithFilter->columnIDValidation->l_temp" + l_temp);    
                dbg("p_file_type"+p_file_type);
                dbg("p_table_name"+p_table_name);
                
                if (!dbv.columnIDValidation(p_file_type, p_table_name, Integer.parseInt(l_temp), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();
                }
            }
            iterator = p_filter_column.keySet().iterator();
            iterator1 = p_filter_column.values().iterator();
            while (iterator.hasNext()) {
                 String id=(String) iterator.next();
                 String value=(String) iterator1.next();
                 dbg("id"+id);
                 dbg("value"+value);
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt(id),value , errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();
                }
            }
            iterator = p_filter_column.keySet().iterator();
            iterator1 = p_filter_column.values().iterator();
            while (iterator.hasNext()) {
                if (!dbv.columnLengthValidation(p_table_name, (String) iterator1.next(), Integer.parseInt((String) iterator.next()), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();
                }
            }

            iterator = p_column_to_update.values().iterator();
            iterator1 = p_column_to_update.keySet().iterator();
            // i = 1;
            while (iterator.hasNext()) {
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt((String) iterator1.next()), (String) iterator.next(), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                // i++;
            }

            iterator = p_column_to_update.values().iterator();
            iterator1 = p_column_to_update.keySet().iterator();
            //i = 1;
            while (iterator.hasNext() && iterator1.hasNext()) {
                if (!dbv.columnLengthValidation(p_table_name, (String) iterator.next(), Integer.parseInt((String) iterator1.next()), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                //  i++;
            }

            iterator = p_column_to_update.keySet().iterator();

            while (iterator.hasNext()) {
                String l_temp = (String) iterator.next();
                dbg("in db transaction service ->updateColumnWithFilter->columnIDValidation->l_temp" + l_temp);
                if (!dbv.columnIDValidation(p_file_type, p_table_name, Integer.parseInt(l_temp), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }

            }
            iterator1=p_column_to_update.keySet().iterator();
            iterator = p_column_to_update.values().iterator();
            i = 1;
            while (iterator1.hasNext()) {
                DBColumn l_dbcolumn = mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt((String)iterator1.next()), this.session);
                String l_variable = (String) iterator.next();
                if(l_variable.length()>0)//Integration changes
                {    
                if (!dbv.specialCharacterValidation(l_variable, errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_dbcolumn.getI_ColumnName());
                    errhandler.log_error();

                }
                }//Integration changes
                i++;
            }

            if (!dbv.fileNameValidation(p_file_name, errhandler,session,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }

            if (!dbv.fileTypeValidation(p_file_type, errhandler,dbdi)) {
                l_validation_status = false;

                errhandler.log_error();

            }

            //if (!dbv.tableNameValidation(p_table_name, errhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_file_type, p_table_name, errhandler,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!l_validation_status) {
                throw new DBValidationException(errhandler.getSession_error_code().toString());
            }
            int l_col_len=0;
           // FileChannel fc1 = null;
            long l_position = 0;
            String[] l_pk = {};
            String l_record_to_be_written = null;
            String col_name=null;
            //String l_filter_from_records = null;

//in the following sequentialRead method we set tableid as zero
            //Cohesive1_Dev_2
            PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, 0, l_pk, p_filter_column, session,dbdi);

            //Iterator iterator4 = par.getI_columnId_and_position().keySet().iterator();
            Iterator iterator5 = par.getI_position_of_filtered_records().values().iterator();
            //Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
            //FileChannel fc1=FileChannel.open(file, READ, WRITE);
            while (iterator5.hasNext()) {
                //long l = Long.parseLong((String)iterator5.next());
                //fc1.position((l));
                //i = 1;
                // while(i<=l_Column_Collection.size()){
                i = 1;
                // while(i<=l_Column_Collection.size()){
                l_position = Long.parseLong((String) iterator5.next());
                
                Iterator iterator2=mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().values().iterator();
                while(iterator2.hasNext()){
                    DBColumn l_dbcolumn=(DBColumn) iterator2.next();
                    l_col_len = l_col_len + l_dbcolumn.getI_ColumnLength();
                }
                
                

                /*mds.getTableMetaData(p_file_type, p_table_name, dbdi).getI_ColumnCollection().forEach((String k, DBColumn v) -> {
                    i_col_len = i_col_len + v.getI_ColumnLength();
                });*/
                ByteBuffer copy = ByteBuffer.allocate(l_col_len + 1 + String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid()).length()
                        + mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().size());
                //Cohesive1_Dev_2
                String converted = dbSession.getIibd_file_util().randomRead(l_temp_path, p_table_name, copy.capacity(), l_position,session,dbdi);
                String[] l_temp_column_values = converted.split("~");
                dbg("in transaction service ->update column->converted" + converted);
                
                for(int j=0;j<p_column_to_update.size();j++){
                    String l_key =Integer.toString(j+1);
                    String l_value=p_column_to_update.get(Integer.toString(j+1));
                     int l_column_length=mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_key), session).getI_ColumnLength();
                
                int l_length_difference = l_column_length - l_value.length();
                    dbg("in transaction service ->update column->i_length_difference " + l_length_difference);
                    col_name = l_value;
                    while (l_length_difference != 0) {
                        col_name = col_name + " ";
                        --l_length_difference;
                    }
                    l_temp_column_values[Integer.parseInt(l_key)] = col_name;
                }
//                Iterator iterator3=p_column_to_update.keySet().iterator();
//            Iterator iterator4=p_column_to_update.values().iterator();
//            while(iterator3.hasNext()&&iterator4.hasNext()){
//                String l_key = (String)iterator3.next();
//                String l_value = (String)iterator4.next();
//                int l_column_length=mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_key), dbdi).getI_ColumnLength();
//                
//                int l_length_difference = l_column_length - l_value.length();
//                    dbg("in transaction service ->update column->i_length_difference " + l_length_difference);
//                    col_name = l_value;
//                    while (l_length_difference != 0) {
//                        col_name = col_name + " ";
//                        --l_length_difference;
//                    }
//                    l_temp_column_values[Integer.parseInt(l_key)] = col_name;
//                
//            }
            

               /* p_column_to_update.forEach((String cid, String cvalue) -> {
                    try {
                        // l_Column_Collection.forEach((String k, DBColumn v) -> {

                        //if (Integer.parseInt(cid)==i) {
//                        dbg("in transaction service ->update column->cid and i " + cid + "   " + i);
                        //try{
                        l_column_length = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(cid), dbdi).getI_ColumnLength();
                        //}catch(DBValidationException ex){
                        //   dbg(ex);
                        //throw new DBValidationException("DBValidationException"+ex.toString());
                        //}catch(DBProcessingException ex){
                        //    dbg(ex);
                        //throw new DBProcessingException("DBValidationException"+ex.toString());  
                        // }
                        i_length_difference = l_column_length - cvalue.length();
                        dbg("in transaction service ->update column->i_length_difference " + i_length_difference);
                        col_name = cvalue;
                        while (i_length_difference != 0) {
                            col_name = col_name + " ";
                            --i_length_difference;
                        }
                        l_temp_column_values[Integer.parseInt(cid)] = col_name;
                        //j=1;
                        //i=1;
                        /* while (i <= Integer.parseInt(cid)) {
                                if (i == 1) {
                                    l_position = l_position + 2+String.valueOf(mds.getTableMetaData(p_file_type, p_table_name,dbdi).getI_Tableid()).length();
                                } else {
                                    //j = 1;
                                    //  while (j < i) {
                                    l_position = l_position +mds.getColumnMetaData(p_file_type, p_table_name, (i - 1), dbdi).getI_ColumnLength() + 1;
                                    // j++;
                                    // }

                                }
                                i++;
                            }
                        dbg("in transaction service ->update column->col_name" + col_name + "is");

                   } catch (DBValidationException ex) {
                        dbg(ex);

                   } catch (DBProcessingException ex) {
                        dbg(ex);

                   }

                });*/
                l_record_to_be_written = "#".concat(String.valueOf(mds.getTableMetaData(p_file_type, p_table_name,session).getI_Tableid())).concat("~").concat(l_temp_column_values[1]);
                 i = 2;
                while (i < l_temp_column_values.length) {
                    l_record_to_be_written = l_record_to_be_written.concat("~").concat(l_temp_column_values[i]);
                    i++;
                }
                //Cohesive1_Dev_2
                dbSession.getIibd_file_util().randomWrite(l_temp_path, l_position, l_record_to_be_written);
//                dbSession.setFileNames_To_Be_Commited(p_file_name);
                
                
                
                /*try {
                    
                    Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                    fc1 = FileChannel.open(file, READ, WRITE);
                    fc1.position(l_position);
                    fc1.write(ByteBuffer.wrap(l_record_to_be_written.getBytes(Charset.forName("UTF-8"))));

                    dbg("in transaction service ->update column->l_position after increment->" + l_position);

                } catch (IOException ex) {
                    dbg(ex);

                }*/

                /*   p_column_to_update.forEach((String cid, String cvalue) -> {
                        try {
                            // l_Column_Collection.forEach((String k, DBColumn v) -> {

                            //if (Integer.parseInt(cid)==i) {
                            dbg("in transaction service ->update column->cid and i " + cid + "   " + i);
                            //try{
                            l_column_length = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(cid), dbdi).getI_ColumnLength();
                            //}catch(DBValidationException ex){
                            //   dbg(ex);
                            //throw new DBValidationException("DBValidationException"+ex.toString());
                            //}catch(DBProcessingException ex){
                            //    dbg(ex);
                            //throw new DBProcessingException("DBValidationException"+ex.toString());  
                            // }
                            i_length_difference = l_column_length - cvalue.length();
                            dbg("in transaction service ->update column->i_length_difference " + i_length_difference);
                            col_name = cvalue;
                            while (i_length_difference != 0) {
                                col_name = col_name + " ";
                                --i_length_difference;
                            }
                            //j=1;
                            //i=1;
                            while (i <= Integer.parseInt(cid)) {
                                if (i == 1) {
                                    l_position = l_position + 2+String.valueOf(mds.getTableMetaData(p_file_type,p_table_name,dbdi).getI_Tableid()).length();
                                } else {
                                    //j = 1;
                                    //  while (j < i) {
                                    l_position = l_position + mds.getColumnMetaData(p_file_type, p_table_name, (i - 1), dbdi).getI_ColumnLength() + 1;
                                    // j++;
                                    // }

                                }
                                i++;
                            }
                            dbg("in transaction service ->update column->col_name" + col_name + "is");
                            try {
                                
                                
                                fc1.position(l_position);
                                fc1.write(ByteBuffer.wrap(col_name.getBytes(Charset.forName("UTF-8"))));
                                
                                dbg("in transaction service ->update column->l_position after increment->" + l_position);

                            } catch (IOException ex) {
                                dbg(ex);
                                //throw new DBProcessingException("IOEXCEPTION"+ex.toString());
                            }
                            
                        } catch (DBValidationException ex) {
                            dbg(ex);
                           
                        } catch (DBProcessingException ex) {
                            dbg(ex);
                              
                        }
                    });*/
            }
            if(l_session_created_now){
                     
                     tc.commit(session,dbSession);
                 }
        } catch (InvalidPathException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedCharsetException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        }finally{
//             dbSession.setFileNames_To_Be_Commited(p_file_name);
            if(l_session_created_now)
                {
                   dbSession.clearSessionObject();
                   session.clearSessionObject();
                }
            
            
        }
    }
    
    public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException{
        CohesiveSession tempSession = this.session;
       try{
           updateColumnWithFilter(p_file_name,p_file_type,p_table_name,p_filter_column,p_column_to_update);
           this.session=session;
           
           
       }catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally {
           this.session=tempSession;
            
        }
        
    }

    /* this function updates the record specified by the p_pkey param with the values given by the map p_column_to_update
    in param are filename filetype tablename primary key and new column values*/
    public void updateRecord1(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
        Scanner l_file_content = null;
        boolean l_session_created_now=false;
        ITransactionControlService tc;
        try {
            tc = dbdi.getTransactionControlService();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }// FileChannel fc = null;
        try {

            session.createSessionObject();
            dbSession.createDBsession(session);
            
            l_session_created_now=session.isI_session_created_now();
            //Cohesive1_Dev_2
            lockProcessing(p_file_name);
            String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_file_name);
            ErrorHandler errhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            dbg("inside update recordd");
            int i=0;
            boolean l_validation_status = true;
            dbg("in dbtransaction service->p_file_name" + p_file_name);
            dbg("in dbtransaction service->p_file_type" + p_file_type);
            dbg("in dbtransaction service->p_table_name" + p_table_name);
            // dbg("in dbtransaction service->p_pkey[0]" + p_pkey[0]);
            //dbg("in dbtransaction service->p_column_to_update[0]" + p_column_to_update);
           // IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
           // IIndexCoreService ics = DBDependencyInjection.getIndexcoreservice();
            String l_record = null;
            String[] l_record_to_be_changed;
            int l_index = 0;

            //l.kSeekableByteChannel sbc = null;
            long l_position = 0;
            String l_record_to_be_written = null;
            String[] l_column_values = {};
            String[] l_column = {};
           // String l_PK = null, l_pkey = null;
            int array_index = 0;

          //  int indicator = 0;

            if (!dbv.specialCharacterValidation(p_file_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_file_type, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_table_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            /*DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_TableName().equals(p_table_name)) {
                        i_dummy_pk = v.getI_Pkey();
                    }
                });
            });*/
            IMetaDataService mds = dbdi.getMetadataservice();
            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.session);
            String i_dummy_pk = l_dbtable.getI_Pkey();
            String[] l_dummy_pk = i_dummy_pk.split("~");

            i = 0;
            while (i < l_dummy_pk.length) {
                dbg("in updateRecord->l_dummy_pk value is ->" + l_dummy_pk[i]);
                String l_column_name = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_dummy_pk[i]), this.session).getI_ColumnName();
                if (!dbv.specialCharacterValidation(p_pkey[i], errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_column_name);
                    errhandler.log_error();

                }
                i++;
            }
            i = 0;
            dbg("in updateRecord->l_dummy_pk.length->" + l_dummy_pk.length);
            dbg("in updateRecord->p_pkey.length->" + p_pkey.length);

            while (i < l_dummy_pk.length) {
                dbg("in updateRecord->i value is ->" + i);
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt(l_dummy_pk[i]), p_pkey[i], errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            i = 0;
            while (i < l_dummy_pk.length) {
                // dbg("in updateRecord->i value is ->" + i);
                if (!dbv.columnLengthValidation(p_table_name, p_pkey[i], Integer.parseInt(l_dummy_pk[i]), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            Iterator iterator = p_column_to_update.values().iterator();
            i = 1;
            while (iterator.hasNext()) {
                if (!dbv.columnDataTypeValidation(p_table_name, i, (String) iterator.next(), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }
            
            for(i=0;i<p_column_to_update.size();i++){
                if (!dbv.columnLengthValidation(p_table_name,p_column_to_update.get(Integer.toString(i+1)) , i+1, errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                
            }

//            iterator = p_column_to_update.values().iterator();
//            i = 1;
//            while (iterator.hasNext()) {
//                if (!dbv.columnLengthValidation(p_table_name, (String) iterator.next(), i, errhandler)) {
//                    l_validation_status = false;
//                    errhandler.log_error();
//
//                }
//                i++;
//            }
            
            iterator = p_column_to_update.keySet().iterator();

            while (iterator.hasNext()) {
                String l_temp = (String) iterator.next();
                dbg("in db transaction service ->update record ->columnIDValidation->l_temp" + l_temp);
                if (!dbv.columnIDValidation(p_file_type, p_table_name, Integer.parseInt(l_temp), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }

            }
           Iterator  iterator3=p_column_to_update.keySet().iterator();
            iterator = p_column_to_update.values().iterator();
            i = 1;
            while (iterator3.hasNext()) {
                DBColumn l_dbcolumn = mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt((String)iterator3.next()), this.session);
                String l_variable = (String) iterator.next();
                if(l_variable.length()>0)//Integration changes
                {    
                if (!dbv.specialCharacterValidation(l_variable, errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_dbcolumn.getI_ColumnName());
                    errhandler.log_error();

                }
                }//Integration changes
                i++;
            }

            if (!dbv.fileNameValidation(p_file_name, errhandler,session,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }

            if (!dbv.fileTypeValidation(p_file_type, errhandler,dbdi)) {
                l_validation_status = false;

                errhandler.log_error();

            }

            //if (!dbv.tableNameValidation(p_table_name, errhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_file_type, p_table_name, errhandler,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            String l_pk = mds.getTableMetaData(p_file_type, p_table_name, this.session).getI_Pkey();
            String[] l_pk_ids = l_pk.split("~");
            //dbg("in update record ->l_pk_ids->" + l_pk_ids[0]);
            String[] l_prime_key = new String[l_pk_ids.length];
            for(int j=0;j<l_pk_ids.length;j++){
              l_prime_key[j]=p_column_to_update.get(l_pk_ids[j]);
            }
//            i = 0;
//            Iterator iterator1=p_column_to_update.keySet().iterator();
//             Iterator iterator2=p_column_to_update.values().iterator();
//             while(iterator1.hasNext()&&iterator2.hasNext()){
//                 String l_key=(String)iterator1.next();
//                 dbg("l_key"+l_key);
//                 String l_value=(String)iterator2.next();
//                 dbg("l_value"+l_value);
//                 if(i < l_pk_ids.length){
//                    if (l_pk_ids[i].equals(l_key)) {
//                        l_prime_key[i] = l_value;
//                       
//                    } 
//                 }
//                 i++;
//              
//             }
            
           /* p_column_to_update.forEach((String kc, String vc) -> {
                dbg("kc value " + kc + "is");
                if (i < l_pk_ids.length) {
                    if (l_pk_ids[i].equals(kc)) {
                        l_prime_key[i] = vc;
                        dbg("vc is " + vc + "is");
                    }
                }
                i++;

            });*/
            i = 0;
            while (i < l_prime_key.length) {
                dbg("the prime_key" + l_prime_key[i] + "is");
                i++;
            }
            //dbg("while loop completed");
            //Cohesive1_Dev_2
            dbg("before duplicate record availability check");
            
            boolean status = this.duplicateRecordAvailability(l_temp_path, p_file_type, p_table_name, l_prime_key);
            dbg("in dbtransacton service-> update Record - >status" + status);
            if (status == true) {
                StringBuffer err = new StringBuffer("DB_VAL_009");
                l_validation_status = false;
                errhandler.setSingle_err_code(err);
                errhandler.log_error();
            }
            if (!l_validation_status) {
                //  dbg("Error code "+errhandler.getSession_error_code());
                throw new DBValidationException(errhandler.getSession_error_code().substring(0));//converting string buffer into string using substring(0)
            }

            /* dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                if (vc.isI_index_required()) {
                    i_index_required = true;
                } else {
                    i_index_required = false;
                }
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_TableName().equals(p_table_name)) {
                        l_table_id = k;
                    }
                });
            });*/
            boolean l_index_required = mds.isIndexRequired(p_file_type, this.session);

            String l_table_id = String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, this.session).getI_Tableid());
            dbg("in Transaction sevice->update record->i_index_required->" + l_index_required);
            if (l_index_required) {
                dbg("in dbtransaction service->update record->p_column_to_update.size->" + p_column_to_update.size());
                l_record_to_be_changed = new String[p_column_to_update.size()];
                iterator = p_column_to_update.values().iterator();
                while (iterator.hasNext()) {

                    l_record_to_be_changed[l_index] = (String) iterator.next();

                    l_index++;
                }
                l_record = getRecord(p_file_type, Integer.parseInt(l_table_id), l_record_to_be_changed);
                dbg("in dbtransaction service->update record->l_record" + l_record);

             // String i_primary_key = getPrimaryKey(Integer.parseInt(l_table_id), l_record_to_be_changed);
              String i_primary_key = formingPrimaryKey(p_pkey);//getPrimaryKey(Integer.parseInt(l_table_id), l_record_to_be_changed);

                /*String l_index_map_key = l_table_id.concat("~").concat(p_file_name);
                dbg("in dbtransaction service->update record->i_primary_key" + i_primary_key);
                ics.getIndex_map().forEach((String kc, Map<String, String> vc) -> {
                    if (kc.equals(l_index_map_key)) {
                        vc.forEach((String k, String v) -> {

                            i_position = (long) Integer.parseInt(v);

                        });
                    }
                });*/
                l_position = dbdi.getIndexreadservice().readIndex(p_file_name, l_table_id, i_primary_key, session);
                dbg("in transaction service->update record->i_position->" + l_position);
                if (l_position != -1) {
                    // Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                    // fc = FileChannel.open(file, READ, WRITE);
                    dbg("in dbtransaction service->update record->i_position" + l_position);
                    //  fc.position(l_position);
                    dbg("in dbtransaction service->update record->i_position" + l_position);
                    // fc.write(l_record);

                    // public static Charset charset = Charset.forName("UTF-8");
                    //  ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8")));
                    if (flag.equals("DELETE")) {//this flag shows call is from deleterecord method.
                        l_record = l_record.replace("~", " ");
                        dbg("in dbtransaction service->l_record->" + l_record);
                    }
                    // fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
                }
                //return;
            } // FileChannel fc = null;
            //IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
            //l_record_to_be_written = getRecord(p_file_type, Integer.parseInt(l_table_id), p_record);
            /*dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                    vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                        if (v.getI_TableName().equals(p_table_name)) {
                            l_table_id = k;
                        }
                    });
                });*/
            if (l_position == -1 || !l_index_required) {
                Map<String, String> l_dummy = new HashMap();
                l_table_id = String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid());
                //Cohesive1_Dev_2
                PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, Integer.parseInt(l_table_id), p_pkey, l_dummy, session,dbdi);
                dbg("in dbtransaction service->par.getI_records().get(0)" + par.getI_records().get(0));
                l_column_values = par.getI_records().get(0).split("~");
                l_position = par.getI_position();
                l_column = new String[par.getI_records().get(0).split("~").length - 1];
                array_index = 0;
                while (array_index < par.getI_records().get(0).split("~").length - 1) {
                    l_column[array_index] = l_column_values[array_index + 1];
                    dbg("in dbtransaction service->l_column" + l_column[array_index]);
                    array_index++;

                }
                dbg("p_file_typeee"+p_file_type);
                dbg("l_table_id"+l_table_id);
                dbg("l_column"+l_column);
                l_record = recordToUpdate(p_file_type, l_table_id, l_column, p_column_to_update);
                //Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                //fc = FileChannel.open(file, READ, WRITE);
                
                dbg("in dbtransaction service->update record->l_position" + l_position);
                //fc.position(l_position);
                dbg("in dbtransaction service->update record->l_position" + l_position);
                // fc.write(l_record);

                // public static Charset charset = Charset.forName("UTF-8");
                //  ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8")));
                if (flag.equals("DELETE")) {
                    l_record = l_record.replace("~", " ");
                    dbg("in dbtransaction service->l_record_to_be_written->" + l_record_to_be_written);
                }
                // fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));

                /*dbg("in dbtransaction service->l_table_id" + l_table_id);
                l_pkey = formingPrimaryKey(p_pkey);
                l_file_content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
                l_file_content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));

                while (l_file_content.hasNext() && indicator == 0) {
                    dbg("in dbtransaction service->insidewhile");

                    String samp = l_file_content.next();
                    if (samp.contains("~")) {
                        l_column_values = samp.split(dbcs.getI_db_properties().getProperty("COLUMN_DELIMITER"));
                        dbg("in dbtransaction service->l_column_values[0]" + l_column_values[0]);
                        if (l_table_id.equals(l_column_values[0])) {
                            dbg("in dbtransaction service->inside IF");
                            l_column = new String[l_column_values.length - 1];
                            while (array_index < l_column_values.length - 1) {
                                l_column[array_index] = l_column_values[array_index + 1];
                                dbg("in dbtransaction service->l_column" + l_column[array_index]);
                                array_index++;

                            }
                            //dbg("in dbtransaction service->l_column" + l_pkey);
                            //dbg("in dbtransaction service->l_table_id"+l_table_id);
                            //dbg("in dbtransaction service->l_PK"+getPrimaryKey(Integer.parseInt(l_table_id), l_column));
                            l_PK = getPrimaryKey(Integer.parseInt(l_table_id), l_column);
                            l_PK = triming(l_PK);
                            dbg("in dbtransaction service->l_pkey" + l_pkey);
                            dbg("in dbtransaction service->l_PK" + l_PK);
                            //l_pkey = getPrimaryKey(Integer.parseInt(l_table_id), p_record);
                            if (l_PK.equals(l_pkey)) {
                                indicator = 1;
                                l_record_to_be_written = recordToUpdate(p_file_type, l_table_id, l_column, p_column_to_update);
                                dbg("in dbtransaction service->update record->l_record_to_be_written" + l_record_to_be_written);
                                Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
                                fc = FileChannel.open(file, READ, WRITE);
                                dbg("in dbtransaction service->update record->l_position" + l_position);
                                fc.position(l_position);
                                dbg("in dbtransaction service->update record->l_position" + l_position);
                                // fc.write(l_record);

                                // public static Charset charset = Charset.forName("UTF-8");
                                //  ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8")));
                                if (flag.equals("DELETE")) {
                                    l_record_to_be_written = l_record_to_be_written.replace("~", " ");
                                    dbg("in dbtransaction service->l_record_to_be_written->" + l_record_to_be_written);
                                }
                                fc.write(ByteBuffer.wrap(l_record_to_be_written.getBytes(Charset.forName("UTF-8"))));

                            } else {
                                l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]));
                            }
                        } else {
                            dbg("in db transaction service->legth of the record" + lengthOfTheRecord(Integer.parseInt(l_column_values[0])));
                            l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]));
                        }
                    } else {
                        l_position = l_position + lengthOfTheRecord(Integer.parseInt(samp.trim()));
                    }
                }*/
            }
            //Cohesive1_Dev_2
            dbSession.getIibd_file_util().randomWrite(l_temp_path, l_position, l_record);
           /* Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
            fc = FileChannel.open(file, READ, WRITE);
            fc.position(l_position);

            fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));*/
           dbg("update record completedd");
//           dbSession.setFileNames_To_Be_Commited(p_file_name);
           
//           IRelationshipService rs=dbdi.getRelationshipService();
//           
//           rs.updateRecord(p_file_name, p_file_type, p_table_name, p_pkey, p_column_to_update,this.dbdi);
           
           
           
           
           
          if(l_session_created_now){
                     
                     tc.commit(session,dbSession);
                 }
        } catch (DBProcessingException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (IllegalStateException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (PatternSyntaxException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (InvalidPathException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NumberFormatException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (IllegalArgumentException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (SecurityException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (UnsupportedOperationException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NoSuchElementException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NullPointerException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }catch (ArrayIndexOutOfBoundsException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
         
        }catch (ConcurrentModificationException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }catch(DBValidationException ex){
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
//            dbSession.setFileNames_To_Be_Commited(p_file_name);
            if (l_file_content != null) {
                l_file_content.close();
            }
            if(l_session_created_now){
                
                   dbSession.clearSessionObject(); 
                }
            /*if (fc.isOpen()) {
                try {
                    fc.close();
                } catch (IOException ex) {
                    dbg(ex);
                    throw new DBProcessingException("IOException" + ex.toString());
                }
                dbdi.clearSessionObject();
            }*/

        }

    }
     public void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
        Scanner l_file_content = null;
        boolean l_session_created_now=false;
        ITransactionControlService tc;
         long startTime=0;
         String l_key=null;
        try {
            tc = dbdi.getTransactionControlService();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }// FileChannel fc = null;
        try {

            session.createSessionObject();
            dbSession.createDBsession(session);
            
            l_session_created_now=session.isI_session_created_now();
            l_key=formingPrimaryKey(p_pkey);
            startTime=dbSession.getIibd_file_util(). getStartTime();
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);
			
			try{
            dbSession.getIibd_file_util().logWaitBuffer(p_file_name,p_table_name,l_key,"DBTransactionService","updateRecord",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,false);
            }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
			
            //Cohesive1_Dev_2
//            lockProcessing(p_file_name);
//            String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_file_name);
            ErrorHandler errhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            dbg("inside update recordd");
            int i=0;
            boolean l_validation_status = true;
            dbg("in dbtransaction service->p_file_name" + p_file_name);
            dbg("in dbtransaction service->p_file_type" + p_file_type);
            dbg("in dbtransaction service->p_table_name" + p_table_name);
            // dbg("in dbtransaction service->p_pkey[0]" + p_pkey[0]);
            //dbg("in dbtransaction service->p_column_to_update[0]" + p_column_to_update);
           // IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
           // IIndexCoreService ics = DBDependencyInjection.getIndexcoreservice();
            String l_record = null;
            String[] l_record_to_be_changed;
            int l_index = 0;

            //l.kSeekableByteChannel sbc = null;
            long l_position = 0;
            String l_record_to_be_written = null;
            String[] l_column_values = {};
            String[] l_column = {};
           // String l_PK = null, l_pkey = null;
            int array_index = 0;

          //  int indicator = 0;

             if(!dbv.reportingDBValidation(session,p_file_name)){
                 l_validation_status = false;
             }
           
            if (!dbv.specialCharacterValidation(p_file_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_file_type, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_table_name, errhandler)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            /*DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_TableName().equals(p_table_name)) {
                        i_dummy_pk = v.getI_Pkey();
                    }
                });
            });*/
            IMetaDataService mds = dbdi.getMetadataservice();
            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.session);
            String i_dummy_pk = l_dbtable.getI_Pkey();
            String[] l_dummy_pk = i_dummy_pk.split("~");

            i = 0;
            while (i < l_dummy_pk.length) {
                dbg("in updateRecord->l_dummy_pk value is ->" + l_dummy_pk[i]);
                String l_column_name = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_dummy_pk[i]), this.session).getI_ColumnName();
                if (!dbv.specialCharacterValidation(p_pkey[i], errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_column_name);
                    errhandler.log_error();

                }
                i++;
            }
            i = 0;
            dbg("in updateRecord->l_dummy_pk.length->" + l_dummy_pk.length);
            dbg("in updateRecord->p_pkey.length->" + p_pkey.length);

            while (i < l_dummy_pk.length) {
                dbg("in updateRecord->i value is ->" + i);
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt(l_dummy_pk[i]), p_pkey[i], errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            i = 0;
            while (i < l_dummy_pk.length) {
                // dbg("in updateRecord->i value is ->" + i);
                if (!dbv.columnLengthValidation(p_table_name, p_pkey[i], Integer.parseInt(l_dummy_pk[i]), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }

            Iterator iterator = p_column_to_update.values().iterator();
            i = 1;
            while (iterator.hasNext()) {
                if (!dbv.columnDataTypeValidation(p_table_name, i, (String) iterator.next(), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                i++;
            }
            
            for(i=0;i<p_column_to_update.size();i++){
                if (!dbv.columnLengthValidation(p_table_name,p_column_to_update.get(Integer.toString(i+1)) , i+1, errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }
                
            }

//            iterator = p_column_to_update.values().iterator();
//            i = 1;
//            while (iterator.hasNext()) {
//                if (!dbv.columnLengthValidation(p_table_name, (String) iterator.next(), i, errhandler)) {
//                    l_validation_status = false;
//                    errhandler.log_error();
//
//                }
//                i++;
//            }
            
            iterator = p_column_to_update.keySet().iterator();

            while (iterator.hasNext()) {
                String l_temp = (String) iterator.next();
                dbg("in db transaction service ->update record ->columnIDValidation->l_temp" + l_temp);
                if (!dbv.columnIDValidation(p_file_type, p_table_name, Integer.parseInt(l_temp), errhandler,dbdi)) {
                    l_validation_status = false;
                    errhandler.log_error();

                }

            }
           Iterator  iterator3=p_column_to_update.keySet().iterator();
            iterator = p_column_to_update.values().iterator();
            i = 1;
            while (iterator3.hasNext()) {
                DBColumn l_dbcolumn = mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt((String)iterator3.next()), this.session);
                String l_variable = (String) iterator.next();
                if(l_variable.length()>0)//Integration changes
                { 
                if (!dbv.specialCharacterValidation(l_variable, errhandler)) {
                    l_validation_status = false;
                    errhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_dbcolumn.getI_ColumnName());
                    errhandler.log_error();

                }
                }//Integration changes
                i++;
            }

            if (!dbv.fileNameValidation(p_file_name, errhandler,session,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }

            if (!dbv.fileTypeValidation(p_file_type, errhandler,dbdi)) {
                l_validation_status = false;

                errhandler.log_error();

            }

            //if (!dbv.tableNameValidation(p_table_name, errhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_file_type, p_table_name, errhandler,dbdi)) {
                l_validation_status = false;
                errhandler.log_error();

            }
            String l_pk = mds.getTableMetaData(p_file_type, p_table_name, this.session).getI_Pkey();
            String[] l_pk_ids = l_pk.split("~");
            //dbg("in update record ->l_pk_ids->" + l_pk_ids[0]);
            String[] l_prime_key = new String[l_pk_ids.length-1];//-1 is for not considering version number
            for(int j=0;j<l_pk_ids.length-1;j++){
              l_prime_key[j]=p_column_to_update.get(l_pk_ids[j]);
            }
//            i = 0;
//            Iterator iterator1=p_column_to_update.keySet().iterator();
//             Iterator iterator2=p_column_to_update.values().iterator();
//             while(iterator1.hasNext()&&iterator2.hasNext()){
//                 String l_key=(String)iterator1.next();
//                 dbg("l_key"+l_key);
//                 String l_value=(String)iterator2.next();
//                 dbg("l_value"+l_value);
//                 if(i < l_pk_ids.length){
//                    if (l_pk_ids[i].equals(l_key)) {
//                        l_prime_key[i] = l_value;
//                       
//                    } 
//                 }
//                 i++;
//              
//             }
            
           /* p_column_to_update.forEach((String kc, String vc) -> {
                dbg("kc value " + kc + "is");
                if (i < l_pk_ids.length) {
                    if (l_pk_ids[i].equals(kc)) {
                        l_prime_key[i] = vc;
                        dbg("vc is " + vc + "is");
                    }
                }
                i++;

            });*/
            i = 0;
            while (i < l_prime_key.length) {
                dbg("the prime_key" + l_prime_key[i] + "is");
                i++;
            }
            //dbg("while loop completed");
            //Cohesive1_Dev_2
            
            dbg("before duplicate record availability check");
            String l_pkArr[]=mds.getTableMetaData(p_file_type, p_table_name, this.session).getI_Pkey().split("~");
            
            for(String s:l_pkArr){
                dbg("l_pkArr"+s);
            }
            for(String s:p_pkey){
                dbg("p_pkey"+s);
            }
            p_column_to_update.forEach((String k,String v)->{
                dbg("p_column_to_update--->key"+k);
                dbg("p_column_to_update--->value"+v);
            }
            );
            
            
            boolean isdupCheckNeeded=true;
            for(int j=0;j<l_pkArr.length-1;j++){ //l_pkArr has column ids of primary key
                
            if( p_column_to_update.containsKey(l_pkArr[j])){
                   dbg("column to update contains primary key column");
                if(  p_column_to_update.get(l_pkArr[j]).equals(p_pkey[j])){//p_pkey is the primary comes from the parameter
                     
                     isdupCheckNeeded=false;//value in p_column_to_update is equal to the primary key
                }else{
                     isdupCheckNeeded=true;//value in p_column_to_update is not equal to the primary  key
                 }
                 if(isdupCheckNeeded==true)
                 break;
            }
            }
            
            dbg("isdupCheckNeeded"+isdupCheckNeeded);
            
            
            if(isdupCheckNeeded==true){
            boolean status = this.duplicateRecordAvailability(p_file_name, p_file_type, p_table_name, l_prime_key);
            
            dbg("in dbtransacton service-> update Record - >status" + status);
            if (status == true) {
                StringBuffer err = new StringBuffer("DB_VAL_009");
                l_validation_status = false;
                errhandler.setSingle_err_code(err);
                errhandler.log_error();
            }
            }
            
            if (!l_validation_status) {
                //  dbg("Error code "+errhandler.getSession_error_code());
                throw new DBValidationException(errhandler.getSession_error_code().substring(0));//converting string buffer into string using substring(0)
            }

            /* dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                if (vc.isI_index_required()) {
                    i_index_required = true;
                } else {
                    i_index_required = false;
                }
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_TableName().equals(p_table_name)) {
                        l_table_id = k;
                    }
                });
            });*/
//            boolean l_index_required = mds.isIndexRequired(p_file_type, this.session);
//
//            String l_table_id = String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, this.session).getI_Tableid());
//            dbg("in Transaction sevice->update record->i_index_required->" + l_index_required);
//            if (l_index_required) {
//                dbg("in dbtransaction service->update record->p_column_to_update.size->" + p_column_to_update.size());
//                l_record_to_be_changed = new String[p_column_to_update.size()];
//                iterator = p_column_to_update.values().iterator();
//                while (iterator.hasNext()) {
//
//                    l_record_to_be_changed[l_index] = (String) iterator.next();
//
//                    l_index++;
//                }
//                l_record = getRecord(p_file_type, Integer.parseInt(l_table_id), l_record_to_be_changed);
//                dbg("in dbtransaction service->update record->l_record" + l_record);
//
//             // String i_primary_key = getPrimaryKey(Integer.parseInt(l_table_id), l_record_to_be_changed);
//              String i_primary_key = formingPrimaryKey(p_pkey);//getPrimaryKey(Integer.parseInt(l_table_id), l_record_to_be_changed);
//
//                /*String l_index_map_key = l_table_id.concat("~").concat(p_file_name);
//                dbg("in dbtransaction service->update record->i_primary_key" + i_primary_key);
//                ics.getIndex_map().forEach((String kc, Map<String, String> vc) -> {
//                    if (kc.equals(l_index_map_key)) {
//                        vc.forEach((String k, String v) -> {
//
//                            i_position = (long) Integer.parseInt(v);
//
//                        });
//                    }
//                });*/
//                l_position = dbdi.getIndexreadservice().readIndex(p_file_name, l_table_id, i_primary_key, session);
//                dbg("in transaction service->update record->i_position->" + l_position);
//                if (l_position != -1) {
//                    // Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
//                    // fc = FileChannel.open(file, READ, WRITE);
//                    dbg("in dbtransaction service->update record->i_position" + l_position);
//                    //  fc.position(l_position);
//                    dbg("in dbtransaction service->update record->i_position" + l_position);
//                    // fc.write(l_record);
//
//                    // public static Charset charset = Charset.forName("UTF-8");
//                    //  ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8")));
//                    if (flag.equals("DELETE")) {//this flag shows call is from deleterecord method.
//                        l_record = l_record.replace("~", " ");
//                        dbg("in dbtransaction service->l_record->" + l_record);
//                    }
//                    // fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
//                }
//                //return;
//            } // FileChannel fc = null;
//            //IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
//            //l_record_to_be_written = getRecord(p_file_type, Integer.parseInt(l_table_id), p_record);
//            /*dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
//                    vc.getI_TableCollection().forEach((String k, DBTable v) -> {
//                        if (v.getI_TableName().equals(p_table_name)) {
//                            l_table_id = k;
//                        }
//                    });
//                });*/
//            if (l_position == -1 || !l_index_required) {
//                Map<String, String> l_dummy = new HashMap();
//                l_table_id = String.valueOf(mds.getTableMetaData(p_file_type, p_table_name, session).getI_Tableid());
//                //Cohesive1_Dev_2
//                PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, Integer.parseInt(l_table_id), p_pkey, l_dummy, session,dbdi);
//                dbg("in dbtransaction service->par.getI_records().get(0)" + par.getI_records().get(0));
//                l_column_values = par.getI_records().get(0).split("~");
//                l_position = par.getI_position();
//                l_column = new String[par.getI_records().get(0).split("~").length - 1];
//                array_index = 0;
//                while (array_index < par.getI_records().get(0).split("~").length - 1) {
//                    l_column[array_index] = l_column_values[array_index + 1];
//                    dbg("in dbtransaction service->l_column" + l_column[array_index]);
//                    array_index++;
//
//                }
//                dbg("p_file_typeee"+p_file_type);
//                dbg("l_table_id"+l_table_id);
//                dbg("l_column"+l_column);
//                l_record = recordToUpdate(p_file_type, l_table_id, l_column, p_column_to_update);
//                //Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
//                //fc = FileChannel.open(file, READ, WRITE);
//                
//                dbg("in dbtransaction service->update record->l_position" + l_position);
//                //fc.position(l_position);
//                dbg("in dbtransaction service->update record->l_position" + l_position);
//                // fc.write(l_record);
//
//                // public static Charset charset = Charset.forName("UTF-8");
//                //  ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8")));
//                if (flag.equals("DELETE")) {
//                    l_record = l_record.replace("~", " ");
//                    dbg("in dbtransaction service->l_record_to_be_written->" + l_record_to_be_written);
//                }
//                // fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));
//
//                /*dbg("in dbtransaction service->l_table_id" + l_table_id);
//                l_pkey = formingPrimaryKey(p_pkey);
//                l_file_content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
//                l_file_content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));
//
//                while (l_file_content.hasNext() && indicator == 0) {
//                    dbg("in dbtransaction service->insidewhile");
//
//                    String samp = l_file_content.next();
//                    if (samp.contains("~")) {
//                        l_column_values = samp.split(dbcs.getI_db_properties().getProperty("COLUMN_DELIMITER"));
//                        dbg("in dbtransaction service->l_column_values[0]" + l_column_values[0]);
//                        if (l_table_id.equals(l_column_values[0])) {
//                            dbg("in dbtransaction service->inside IF");
//                            l_column = new String[l_column_values.length - 1];
//                            while (array_index < l_column_values.length - 1) {
//                                l_column[array_index] = l_column_values[array_index + 1];
//                                dbg("in dbtransaction service->l_column" + l_column[array_index]);
//                                array_index++;
//
//                            }
//                            //dbg("in dbtransaction service->l_column" + l_pkey);
//                            //dbg("in dbtransaction service->l_table_id"+l_table_id);
//                            //dbg("in dbtransaction service->l_PK"+getPrimaryKey(Integer.parseInt(l_table_id), l_column));
//                            l_PK = getPrimaryKey(Integer.parseInt(l_table_id), l_column);
//                            l_PK = triming(l_PK);
//                            dbg("in dbtransaction service->l_pkey" + l_pkey);
//                            dbg("in dbtransaction service->l_PK" + l_PK);
//                            //l_pkey = getPrimaryKey(Integer.parseInt(l_table_id), p_record);
//                            if (l_PK.equals(l_pkey)) {
//                                indicator = 1;
//                                l_record_to_be_written = recordToUpdate(p_file_type, l_table_id, l_column, p_column_to_update);
//                                dbg("in dbtransaction service->update record->l_record_to_be_written" + l_record_to_be_written);
//                                Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
//                                fc = FileChannel.open(file, READ, WRITE);
//                                dbg("in dbtransaction service->update record->l_position" + l_position);
//                                fc.position(l_position);
//                                dbg("in dbtransaction service->update record->l_position" + l_position);
//                                // fc.write(l_record);
//
//                                // public static Charset charset = Charset.forName("UTF-8");
//                                //  ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8")));
//                                if (flag.equals("DELETE")) {
//                                    l_record_to_be_written = l_record_to_be_written.replace("~", " ");
//                                    dbg("in dbtransaction service->l_record_to_be_written->" + l_record_to_be_written);
//                                }
//                                fc.write(ByteBuffer.wrap(l_record_to_be_written.getBytes(Charset.forName("UTF-8"))));
//
//                            } else {
//                                l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]));
//                            }
//                        } else {
//                            dbg("in db transaction service->legth of the record" + lengthOfTheRecord(Integer.parseInt(l_column_values[0])));
//                            l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]));
//                        }
//                    } else {
//                        l_position = l_position + lengthOfTheRecord(Integer.parseInt(samp.trim()));
//                    }
//                }*/
//            }
//            //Cohesive1_Dev_2
//            dbSession.getIibd_file_util().randomWrite(l_temp_path, l_position, l_record);
//           /* Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));
//            fc = FileChannel.open(file, READ, WRITE);
//            fc.position(l_position);
//
//            fc.write(ByteBuffer.wrap(l_record.getBytes(Charset.forName("UTF-8"))));*/
//           dbg("update record completedd");
//             IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
                ILockService lock=dbdi.getLockService();
               //if(getLock(p_file_name)==true){
                  //if( lock.isSameSessionLock(p_file_name, this.session)){    
   l_key=formingPrimaryKey(p_pkey);
if(getImplictRecordLock(p_file_name,p_table_name,l_key)==true){ 
               if( lock.isSameSessionRecordLock(p_file_name,p_table_name,l_key, this.session)){    

        
                     tempSegment.updateRecord(p_file_name, p_file_type, p_table_name, p_pkey, p_column_to_update, session,dbSession);
                      
                      
                }
               }
           //dbSession.setFileNames_To_Be_Commited(p_file_name);
           
//           IRelationshipService rs=dbdi.getRelationshipService();
//           
//           rs.updateRecord(p_file_name, p_file_type, p_table_name, p_pkey, p_column_to_update,this.dbdi);
           
           
           
           
           
          if(l_session_created_now){
                     
                     tc.commit(session,dbSession);
                 }
        } catch (DBProcessingException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (IllegalStateException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (PatternSyntaxException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (InvalidPathException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NumberFormatException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (IllegalArgumentException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (SecurityException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (UnsupportedOperationException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NoSuchElementException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NullPointerException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }catch (ArrayIndexOutOfBoundsException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());
         
        }catch (ConcurrentModificationException ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        }catch(DBValidationException ex){
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
             if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
//            dbSession.setFileNames_To_Be_Commited(p_file_name);
            if (l_file_content != null) {
                l_file_content.close();
            }
            try{
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);   
           dbSession.getIibd_file_util().logWaitBuffer(p_file_name,p_table_name,l_key,"DBTransactionService","updateRecord",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,true);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           finally{
               if(l_session_created_now)
            {    
             dbSession.clearSessionObject();
             session.clearSessionObject();
            }
           }
//            if(l_session_created_now){
//                   session.clearSessionObject();
//                   dbSession.clearSessionObject(); 
//                }
            /*if (fc.isOpen()) {
                try {
                    fc.close();
                } catch (IOException ex) {
                    dbg(ex);
                    throw new DBProcessingException("IOException" + ex.toString());
                }
                dbdi.clearSessionObject();
            }*/

        }

    }
   public void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException{
       CohesiveSession tempSession = this.session;
       try{
           
           this.session=session;
           updateRecord(p_file_name,p_file_type,p_table_name,p_pkey, p_column_to_update);
           
       }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }catch (ArrayIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }finally {
           this.session=tempSession;
            
        }
       
    }

    public void ChangeToRelatedFiles_Update(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBValidationException, DBProcessingException {

    }

    /*
     This function retrives files to be updated based on the foreign key relation of the given file id and table id.
    Algorithm : 
    
    i) Meta data of the Table id will be fetched to get the foreign key relationship 
    ii) Based on the Foreign key relationship , file names will be arrived
     */
    public void getRelatedFiles(String p_file_name, int p_file_id, int p_table_id) throws DBValidationException, DBProcessingException {
           boolean l_session_created_now=false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            boolean l_validation_status = true;

            IMetaDataService mds = dbdi.getMetadataservice();
            //String[][] l_original_file_names;
            dbg("in dbtransaction->getRelatedFiles->p_file_name->" + p_file_name);
            dbg("in dbtransaction->getRelatedFiles->p_file_id->" + p_file_id);
            dbg("in dbtransaction->getRelatedFiles->p_table_id->" + p_table_id);
            if (!dbv.specialCharacterValidation(p_file_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_file_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_table_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileNameValidation(p_file_name, errorhandler,session,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileIDValidation(p_file_id, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.tableIDValidation(p_table_id, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            dbg("in dbtransaction->getRelatedFiles->before mds.getTableMetaData");
            DBTable l_dbtable = mds.getTableMetaData(p_file_id, p_table_id, this.session);

            dbg("in dbtransaction->getRelatedFiles->l_dbtable.getI_TableName()->" + l_dbtable.getI_TableName());
            String l_online_change = l_dbtable.getI_online_change();
            dbg("in dbtransaction->getRelatedFiles->l_online_change->" + l_online_change);
            String[] l_online_change_array = l_online_change.split("%");
            dbg("in dbtransaction->getRelatedFiles->l_online_change_array->" + l_online_change_array[0]);
            /*    String l_file_names_to_change = l_dbtable.getI_file_names_for_the_change();
            String[] l_file_names_to_change_array = l_file_names_to_change.split("%"); */
            String[] l_file_names_to_change_array = mds.getFileNamesToChange(p_file_id, p_table_id, this.session);
            dbg("in dbtransaction->getRelatedFiles->l_file_names_to_change_array->" + l_file_names_to_change_array[0]);
            int i = 0;
            String[][] l_original_file_names = new String[l_online_change_array.length][];
            while (i < l_online_change_array.length) {
                if (l_online_change_array[i].equals("YES")) {

                    //String[] l_column_names = l_file_names_to_change_array[i].split(","); 
                    l_original_file_names[i] = getFileNames(p_file_name, l_file_names_to_change_array[i]);
                    /*int j = 0;
                    while (j < l_original_file_names.length) {
                        dbg("in dbtransaction->ChangeToRelatedFiles->l_original_file_names->"+l_original_file_names[j]);
                        j++;
                    }*/
                }
                i++;
            }

            for (int l = 0; l < l_original_file_names.length; l++) {

                dbg(l_file_names_to_change_array[l]);
                for (int m = 0; m < l_original_file_names[l].length; m++) {
                    dbg(l_original_file_names[l][m]);
                }

            }
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if(l_session_created_now)
            {    
            dbSession.clearSessionObject();
            session.clearSessionObject();
            
            }
        }

    }

    /*
    This function builds list of file names based on forign key
    
    Forign key  - file_id~table_id~column_id&file_id~table_id~columid 
    For eg : File id for student - 1
              table_id for Inst profile - 1
              column id for institution id - 1
              
            File id for student - 1
              table id  for subject master - 2
              column id for teacher id - 3
    
    So when we do chnage for the student profile table , it should do the changes on related teachers file.
    
    Teachers file name will be identified based on above relationship .In this scenario , 
    Institute id and teacher id column distinct value records will form the file names.
    If there are multiple teacher id in the subject master table , all those teachers table have to be changed . 
    So it will retrieve all teacher file names.
    
    
     */
    private String[] getFileNames(String p_file_name, String p_file_names_to_change) throws DBValidationException, DBProcessingException {
        // String[] l_original_file_names = {};
        //  Scanner l_file_content = null;
        String[] l_original_related_file_names;
        try {
            int l_total_file_names = 1;
            
            //IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
            /*ErrorHandler errorhandler = dbdi.getErrorhandler();
            DBValidation dbv = dbdi.getDbv();
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_file_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
             
           // p_file_names_to_change arguement comes with the special character only so it is commented
                if (!dbv.specialCharacterValidation(p_file_names_to_change, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileNameValidation(p_file_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }*/
            dbg("in DBtransaction->in get filenames->p_file_names_to_change" + p_file_names_to_change);
            //dbg("in DBtransaction->in get filenames->p_file_names_to_change.matches(\",\")"+p_file_names_to_change.matches("&"));
            String[] l_file_names_to_change = p_file_names_to_change.split("&");
            //dbg("in DBtransaction->in get filenames->l_file_names_to_change[0]"+l_file_names_to_change[0]);
            //dbg("in DBtransaction->in get filenames->l_file_names_to_change[1]"+l_file_names_to_change[1]);
            int i = 0;
            //String samp = null;
            // ArrayList<String> l_file_names = new ArrayList<String>();
            ArrayList<String> l_record_colvals = new ArrayList<String>();
            Map<String, ArrayList<String>> l_file_names = new HashMap<String, ArrayList<String>>();
            // l_original_file_names = new String[column1.size()];
            dbg("in DBtransaction->in get filenames->l_file_names_to_change" + l_file_names_to_change.length);
            while (i < l_file_names_to_change.length) {
                String[] l_file_table_column_ids = l_file_names_to_change[i].split("~");
                dbg("in DBtransaction->in get filenames->l_file_table_column_ids" + l_file_table_column_ids[0]);
                dbg("in DBtransaction->in get filenames->l_file_table_column_ids" + l_file_table_column_ids[1]);
                dbg("in DBtransaction->in get filenames->l_file_table_column_ids" + l_file_table_column_ids[2]);
                String[] p_pkey = {};
                Map<String, String> l_dummy = new HashMap();
                PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(p_file_name, Integer.parseInt(l_file_table_column_ids[1]), p_pkey, l_dummy, session,dbdi);
              //  String l_file_name;
                if (par.getI_records().size() != 0) {
                    int j = 0;
                    while (j < par.getI_records().size()) {
                        String[] l_column_values = par.getI_records().get(j).split("~");
                        /*if (i == 0) {
                            dbg("in DBtransaction->in get filenames->column1 values" + l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                           // column1.add(l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                          l_file_name
// dbg(column1.get(0));
                        } else {
                            column2.add(l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                            dbg("in DBtransaction->in get filenames->column2 values" + l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                        }*/
                        l_record_colvals.add(l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                        j++;
                    }
                }
                l_file_names.put(l_file_names_to_change[i], l_record_colvals);
                /*l_file_content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
                l_file_content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));
                while (l_file_content.hasNext()) {
                    samp = l_file_content.next();
                    if (samp.contains("~")) {
                        String[] l_column_values = samp.split("~");
                        if (l_column_values[0].equals(l_file_table_column_ids[1])) {
                            if (i == 0) {
                                dbg("in DBtransaction->in get filenames->column1 values" + l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                                column1.add(l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                                // dbg(column1.get(0));
                            } else {
                                column2.add(l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                                dbg("in DBtransaction->in get filenames->column2 values" + l_column_values[Integer.parseInt(l_file_table_column_ids[2])]);
                            }
                        }
                    }
                }*/
                i++;
            }
            // dbg("in DBtransaction->in get filenames->column1.size()" + column1.size());
            
           // Iterator iterator1=l_file_names.keySet().iterator();
            Iterator iterator2=l_file_names.values().iterator();
            while(iterator2.hasNext()){
                ArrayList<String> l_map_value=(ArrayList<String>)iterator2.next();
              l_total_file_names = l_total_file_names * l_map_value.size();  
            }
            
            
            

            /*l_file_names.forEach((String kc, ArrayList<String> vc) -> {
                total_file_names = total_file_names * vc.size();

            });*/
            
            int m=1;
            
            l_original_related_file_names = new String[l_total_file_names];
            Iterator iterator = l_file_names.values().iterator();
            while (iterator.hasNext()) {
                ArrayList<String> l_value = (ArrayList<String>) iterator.next();
                for (int k = 0; k < l_total_file_names / l_value.size(); k++) {
                    Iterator iterator3 = l_value.iterator();
                    while (iterator3.hasNext()) {
                        String l_array_list_value = (String) iterator3.next();
                        if (m == 1) {
                            l_original_related_file_names[k] = l_array_list_value;
                        }
                        else {
                            l_original_related_file_names[k] = l_original_related_file_names[k].concat(l_array_list_value);
                        }
                    }

                }
                m++;
            }
            

           /* l_file_names.forEach((String kc, ArrayList<String> vc) -> {
                for (k = 0; k < l_total_file_names / vc.size(); k++) {
                    vc.forEach((String v) -> {
                        if (m == 1) {
                            i_original_related_file_names[k] = v;
                        } else {
                            i_original_related_file_names[k] = i_original_related_file_names[k].concat(v);
                        }
                    });
                }
                m++;

            });*/

        } catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalStateException" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            //if (l_file_content != null) {
            //    l_file_content.close();
            //}
            
           
            
        }
        return l_original_related_file_names;
    }

    /* This function checks whether a record already exist or not in a file
    in param filename, filetype, tablename and primarykey 
    out param is a boolean value.if it returns true it means duplicate record available.false means no duplicate record*/
    private boolean duplicateRecordAvailability1(String p_file_name, String p_file_type, String p_table_name, String... p_PK) throws DBProcessingException, DBValidationException {
       // Scanner l_file_content = null;
        boolean indicator = false;
        try {
           
            String l_primary_key2 = null;
            dbg("inside duplicate record p_file_name"+p_file_name);
            dbg("p_table_name"+p_table_name);
            for(String s:p_PK){
                dbg("p_PK"+s);
            }
            //dbdi.createSessionObject();
            /*ErrorHandler errorhandler = dbdi.getErrorhandler();
            DBValidation dbv = dbdi.getDbv();
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_file_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_file_type, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_table_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }

            IMetaDataService mds = dbdi.getMetadataservice();
            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.dbdi);
            i_dummy_pk = l_dbtable.getI_Pkey();
            String[] l_dummy_pk = i_dummy_pk.split("~");

            int i = 0;
            while (i < l_dummy_pk.length) {

                String l_column_name = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_dummy_pk[i]), this.dbdi).getI_ColumnName();
                if (!dbv.specialCharacterValidation(p_PK[i], errorhandler)) {
                    l_validation_status = false;
                    errorhandler.getSingle_err_code().append("," + p_file_name + "," + p_table_name + "," + l_column_name);
                    errorhandler.log_error();

                }
                i++;
            }

            i = 0;
            while (i < l_dummy_pk.length) {

                String l_column_name = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_dummy_pk[i]), this.dbdi).getI_ColumnName();
                if (!dbv.columnDataTypeValidation(p_table_name, Integer.parseInt(l_dummy_pk[i]), p_PK[i], errorhandler)) {
                    l_validation_status = false;

                    errorhandler.log_error();

                }
                i++;
            }

            i = 0;
            while (i < l_dummy_pk.length) {

                String l_column_name = mds.getColumnMetaData(p_file_type, p_table_name, Integer.parseInt(l_dummy_pk[i]), this.dbdi).getI_ColumnName();
                if (!dbv.columnLengthValidation(p_table_name, p_PK[i], Integer.parseInt(l_dummy_pk[i]), errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                i++;
            }

            if (!dbv.fileNameValidation(p_file_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileTypeValidation(p_file_type, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            //if (!dbv.tableNameValidation(p_table_name, errorhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_file_type, p_table_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }*/
            IMetaDataService mds = dbdi.getMetadataservice();
            dbg("mds object created");
            DBTable l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.session);
            dbg("l_dbtable created");
            String i_dummy_pk = l_dbtable.getI_Pkey();
            dbg("i_dummy_pk"+i_dummy_pk);
            String[] l_dummy_pk = i_dummy_pk.split("~");
            int i = 0;
            String l_primary_key1 = null;
            int l_table_id = l_dbtable.getI_Tableid();
            while (i < p_PK.length) {
                if (i == 0) {
                    l_primary_key2 = p_PK[i];
                    i++;
                } else {
                    l_primary_key2 = l_primary_key2.concat("~").concat(p_PK[i]);
                    i++;
                }
            }
           
            l_dbtable = mds.getTableMetaData(p_file_type, p_table_name, this.session);
            String l_key;
            l_key = String.valueOf((l_dbtable.getI_Tableid())).concat("~").concat(p_file_name);
            // dbg("mds.getFileMetaData(p_file_type,this.dbdi);"+mds.getFileMetaData(p_file_type,this.dbdi));
            DBFile l_dbfile = mds.getFileMetaData(p_file_type, this.session);
            dbg("in duplicateRecordAvailability ->l_dbfile.isI_index_required()->" + l_dbfile.isI_index_required());
            if (l_dbfile.isI_index_required()) {
                IIndexCoreService ics = dbdi.getIndexcoreservice();
                
                Iterator iterator1=ics.getIndex_map().keySet().iterator();
                Iterator iterator2=ics.getIndex_map().values().iterator();
                while(iterator1.hasNext()&&iterator2.hasNext()){
                    String l_outer_map_key = (String)iterator1.next();
                    Map l_inner_map = (HashMap)iterator2.next();
                    dbg("in duplicateRecordAvailability->l_outer_map_key"+l_outer_map_key);
                    dbg("in duplicateRecordAvailability->l_key"+l_key);
                    if(l_outer_map_key.equals(l_key)){
                        Iterator iterator3=l_inner_map.keySet().iterator();
                        Iterator iterator4=l_inner_map.values().iterator();
                        while(iterator3.hasNext()&&iterator4.hasNext()){
                            String l_inner_map_key =(String)iterator3.next();
                           // String l_inner_map_value =(String)iterator4.next();
                            if (l_inner_map_key.equals(l_primary_key2)) {
                                indicator = true;
                            }
                        }
                    }
                    
                }
                
                
                
                /*ics.getIndex_map().forEach((String k, Map<String, String> v) -> {
                    dbg("in duplicate record availability->getIndex_map()->k" + k);
                    dbg("in duplicate record availability->getIndex_map()->l_key" + l_key);

                    if (k.equals(l_key)) {
                        v.forEach((String kc, String vc) -> {
                            dbg("in duplicate record availability->getIndex_map()->kc" + kc);
                            dbg("in duplicate record availability->getIndex_map()->l_primary_key2" + l_primary_key2);
                            if (kc.equals(l_primary_key2)) {
                                indicator = true;
                            }
                        });
                    }
                });*/
            } else {

                IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
                /*l_file_content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_file_name + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
                l_file_content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));
                while (l_file_content.hasNext()) {
                    i = 0;
                    String l_samp = l_file_content.next();
                    if (l_samp.contains("~")) {
                        dbg("in DBTransaction service - >in duplicateRecordAvailability->" + l_samp);
                       // String[] l_column_values = l_samp.split(dbcs.getI_db_properties().getProperty("COLUMN_DELIMITER"));

                        if (Integer.parseInt(l_column_values[0]) == l_table_id) {*/
                           ErrorHandler errorhandler = session.getErrorhandler();
                           PositionAndRecord par;
                
                            i=0;
                            String[] l_dummy={};
                            HashMap<String,String> p_filter_column=new HashMap();
                            try{
                             par = dbSession.getIibd_file_util().sequentialRead(p_file_name, l_table_id, l_dummy, p_filter_column, session,dbdi);
                            }catch(FileNotFoundException ex){
                               StringBuffer single_error_code = new StringBuffer();
                               single_error_code = single_error_code.append("DB_VAL_000");
                               errorhandler.setSingle_err_code(single_error_code);
                               errorhandler.log_error();
                               dbg("file doesn't exit");
                               throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                            }

                            Iterator iterator=par.getI_records().iterator();
                            dbg("par"+par);
                            while(iterator.hasNext()){
                                String samp=(String)iterator.next();
                                dbg("in transaction service->duplicate record availability->samp->"+samp);
                             String[] l_column_values =samp.split(session.getCohesiveproperties().getProperty("COLUMN_DELIMITER"));   
                             i=0;
                            while (i < l_dummy_pk.length) {
                                if (i == 0) {
                                    l_primary_key1 = l_column_values[Integer.parseInt(l_dummy_pk[i])].trim();
                                    //l_primary_key2 = p_PK[i];
                                    i++;
                                } else {
                                    l_primary_key1 = l_primary_key1.trim().concat("~").concat(l_column_values[Integer.parseInt(l_dummy_pk[i])].trim());
                                    //l_primary_key2 =l_primary_key2.concat("~").concat(p_PK[i]);
                                    i++;
                                }

                            }
                            dbg("l_primary_key1  l_primary_key2" + l_primary_key1 + l_primary_key2);
                            if (l_primary_key1.equals(l_primary_key2)) {
                                dbg("PRIMARY KEY EQUAL");
                                indicator = true;

                            }
                        }
                       // }
                        l_primary_key1 = null;
                        //i=0;
                    //}
                //}

            }

        }  catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        }
        catch(NamingException ex)
        { 
            dbg(ex);
            throw new DBProcessingException("NamingException" + ex.toString());
        }
       catch(DBValidationException ex){
            throw ex;
        } 
       catch(Exception ex){
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } 
            
        finally {
            
            
            
        }
        return indicator;
    }
   
    
    private boolean duplicateRecordAvailability(String p_file_name, String p_file_type, String p_table_name, String... p_PK) throws DBProcessingException, DBValidationException {
        boolean indicator = false;
        try {
           
            dbg("inside duplicate record p_file_name"+p_file_name);
            dbg("p_table_name"+p_table_name);
            
            for(String s:p_PK){
                dbg("p_PK"+s);
            }
            
           String substring= p_file_name.substring(p_file_name.length()-4, p_file_name.length());
           
            if(substring.equals("_LOG")){
                indicator=false;
            }else{
            DBRecord dbRec=null;
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            try{
             dbg("before readRecord call duplicate record availability ");   
            dbRec=readBuffer.readRecord(p_file_name, p_file_type, p_table_name, p_PK, session,dbSession,true);
            dbg("after readRecord call duplicate record availability ");   
            }catch(DBValidationException ex){
                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    indicator = false;
                    
                    
                }else{
                    throw ex;
                }
                    
            }
            if(dbRec==null){
                indicator = false;
            }else{
                indicator = true;
            }
            
            
            }
              dbg("end of duplicate record availability");
        }  catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        }
        catch(NamingException ex)
        { 
            dbg(ex);
            throw new DBProcessingException("NamingException" + ex.toString());
        }
       catch(DBValidationException ex){
            throw ex;
        } 
       catch(Exception ex){
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } 
            
        finally {
            
            
            
        }
        return indicator;
    }
    
    private boolean duplicateRecordAvailability(String p_file_name, String p_file_type, String p_table_name,int newVersion,String p_operation, String... p_PK) throws DBProcessingException, DBValidationException {
        boolean indicator = false;
        try {
           
            dbg("inside duplicateRecordAvailability haiving versionNo p_file_name"+p_file_name);
            dbg("p_table_name"+p_table_name);
            dbg("operation"+p_operation);
            dbg("newVersion"+newVersion);
            IMetaDataService mds=dbdi.getMetadataservice();
            for(String s:p_PK){
                dbg("p_PK"+s);
            }
            
            
           int versionColId= mds.getColumnMetaData(p_file_type, p_table_name, "VERSION_NUMBER",this.session).getI_ColumnID();
           dbg("versionColId"+versionColId);
           String substring= p_file_name.substring(p_file_name.length()-4, p_file_name.length());
           
            if(substring.equals("_LOG")){
                indicator = false;
            }else{
            DBRecord dbRec=null;
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            try{
             dbg("before readRecord call duplicate record availability ");   
            dbRec=readBuffer.readRecord(p_file_name, p_file_type, p_table_name, p_PK, session,dbSession,true);
            dbg("after readRecord call duplicate record availability ");   
            for(String s:dbRec.getRecord()){
                dbg("recordValues"+s);
            }
              int existingVersion= Integer.parseInt(dbRec.getRecord().get(versionColId-1).trim()) ;
              dbg("existing version"+existingVersion);
              if(p_operation.equals("C")){
                  if(newVersion<=existingVersion){
                      dbg("newVersion<=existingVersion");
                       indicator = true;
                  }else{
                      indicator = false;
                  }
              }
              else   if(p_operation.equals("U")){
                  if(newVersion!=existingVersion){
                      dbg("newVersion!=existingVersion");
                       indicator = true;
                  }else{
                      indicator = false;
                  }
              }
            
            
            
            }catch(DBValidationException ex){
                if(ex.toString().contains("DB_VAL_011")){
                    indicator = false;
                    
                }else{
                    throw ex;
                }
                    
            }
            
            
            
            }
              dbg("end of duplicate record availability");
        }  catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        }
        catch(NamingException ex)
        { 
            dbg(ex);
            throw new DBProcessingException("NamingException" + ex.toString());
        }
       catch(DBValidationException ex){
            throw ex;
        } 
       catch(Exception ex){
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        } 
            
        finally {
            
            
            
        }
        return indicator;
    }
    /*this method deletes the record specified using the primary key
    in param is filename filetype table name and primarykey
     */
    public void deleteRecord1(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey) throws DBProcessingException, DBValidationException {
        boolean l_session_created_now=false;
       ITransactionControlService tc;
        try {
            tc = dbdi.getTransactionControlService();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
           
            boolean l_validation_status = true;
            IMetaDataService mds = dbdi.getMetadataservice();
            flag = "DELETE";//this shows update function that it is a call from deleterecord method
            Map<String, String> l_emptyMap = new HashMap<>();
            if (!dbv.specialCharacterValidation(p_file_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_file_type, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_table_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            int i = 0;
            while (i < p_pkey.length) {
                if (!dbv.specialCharacterValidation(p_pkey[i], errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }
                i++;
            }
            if (!dbv.fileTypeValidation(p_file_type, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileNameValidation(p_file_name, errorhandler,session,dbdi)) {
                
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.tableNameValidation(p_file_type, p_table_name, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            int l_size = mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().size();
            dbg("in db transaction service->delete record->l_size"+l_size);
            String[] l_sample = new String[l_size];
            i = 0;
            while (i < l_size) {
                int length = mds.getColumnMetaData(p_file_type, p_table_name, i + 1, session).getI_ColumnLength();
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
                l_emptyMap.put(String.valueOf(i + 1), l_sample[i]);
                i++;

            }
            dbg("before update record call inside delete record");
            updateRecord(p_file_name, p_file_type, p_table_name, p_pkey, l_emptyMap);
            dbg("update record called inside delete record");
            
            
//            IRelationshipService rs=dbdi.getRelationshipService();
//            rs.deleteRecord(p_file_name, p_file_type, p_table_name, p_pkey,this.dbdi);
//           
            
            if(l_session_created_now){
                     
                     tc.commit(session,dbSession);
                 }
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } finally {
//             dbSession.setFileNames_To_Be_Commited(p_file_name);
            if(l_session_created_now)
            {    
            dbSession.clearSessionObject();
            session.clearSessionObject();
            }
        }
    }
    public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey) throws DBProcessingException, DBValidationException {
        boolean l_session_created_now=false;
       ITransactionControlService tc;
       long startTime=0;
       String l_key=null;
        try {
            tc = dbdi.getTransactionControlService();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            l_key=formingPrimaryKey(p_pkey);
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);
			
	try{
            dbSession.getIibd_file_util().logWaitBuffer(p_file_name,p_table_name,l_key,"DBTransactionService","updateRecord",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,false);
            }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            
            boolean l_validation_status = true;
            IMetaDataService mds = dbdi.getMetadataservice();
            flag = "DELETE";//this shows update function that it is a call from deleterecord method
            Map<String, String> l_emptyMap = new HashMap<>();
            if(!(p_table_name.equals("ARCH_APPLY_STATUS")||p_table_name.equals("ARCH_SHIPPING_STATUS"))){
            
            if(!dbv.reportingDBValidation(session,p_file_name)){
                 l_validation_status = false;
             }
             }
            if (!dbv.specialCharacterValidation(p_file_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_file_type, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_table_name, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            int i = 0;
            while (i < p_pkey.length) {
                if (!dbv.specialCharacterValidation(p_pkey[i], errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }
                i++;
            }
            if (!dbv.fileTypeValidation(p_file_type, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileNameValidation(p_file_name, errorhandler,session,dbdi)) {
                
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.tableNameValidation(p_file_type, p_table_name, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
//            int l_size = mds.getTableMetaData(p_file_type, p_table_name, session).getI_ColumnCollection().size();
//            dbg("in db transaction service->delete record->l_size"+l_size);
//            String[] l_sample = new String[l_size];
//            i = 0;
//            while (i < l_size) {
//                int length = mds.getColumnMetaData(p_file_type, p_table_name, i + 1, session).getI_ColumnLength();
//                int j = 1;
//                while (j <= length) {
//                    if (j == 1) {
//                        l_sample[i] = " ";
//                        j++;
//                    } else {
//                        l_sample[i] = l_sample[i] + " ";
//                        j++;
//                    }
//                }
//                dbg("The sample[i]" + l_sample[i] + "is");
//                l_emptyMap.put(String.valueOf(i + 1), l_sample[i]);
//                i++;
//
//            }
//            dbg("before update record call inside delete record");
//            updateRecord(p_file_name, p_file_type, p_table_name, p_pkey, l_emptyMap);
//              IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
                ILockService lock=dbdi.getLockService();
              // if(getLock(p_file_name)==true){
                //  if( lock.isSameSessionLock(p_file_name, this.session)){    
 l_key=formingPrimaryKey(p_pkey);
if(getImplictRecordLock(p_file_name,p_table_name,l_key)==true){ 
               if( lock.isSameSessionRecordLock(p_file_name,p_table_name,l_key, this.session)){    

                     tempSegment.deleteRecord(p_file_name, p_file_type, p_table_name, p_pkey,  session,dbSession);
                      
                      
                }
               }

   

            dbg("update record called inside delete record");
            
            
//            IRelationshipService rs=dbdi.getRelationshipService();
//            rs.deleteRecord(p_file_name, p_file_type, p_table_name, p_pkey,this.dbdi);
//           
            
            if(l_session_created_now){
                     
                     tc.commit(session,dbSession);
                 }
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            if(l_session_created_now){
               tc.rollBack(session,dbSession);
            }
            throw new DBProcessingException(ex.toString());
        } finally {
//             dbSession.setFileNames_To_Be_Commited(p_file_name);
           try{
           IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
            
            int readBufferSize=readBuffer.getReadBufferSize(session);
            int tempSegmentSize=tempSegment.getTempSegmentSize(session);
            int writeBufferSize=writeBuffer.getWriteBufferSize(session);
            int waitBufferSize=waitBuffer.getWaitBufferSize(session);    
           dbSession.getIibd_file_util().logWaitBuffer(p_file_name,p_table_name,l_key,"DBTransactionService","deleteRecord",session,dbSession,dbdi,startTime,readBufferSize,tempSegmentSize,writeBufferSize,waitBufferSize,true);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           finally{
               if(l_session_created_now)
            {    
             dbSession.clearSessionObject();
             session.clearSessionObject();
            }
           }
//            if(l_session_created_now)
//            {    
//            dbSession.clearSessionObject();
//            session.clearSessionObject();
//            }
        }
    }
    public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey,CohesiveSession session) throws DBProcessingException, DBValidationException{
        CohesiveSession tempSession = this.session;
       try{
           
           this.session=session;
           deleteRecord(p_file_name, p_file_type,p_table_name, p_pkey);
           
       }catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally {
           this.session=tempSession;
            
        }
    }

    /*This function forms the primarykey in the form of record from the array form of primarykey*/
    private String formingPrimaryKey(String[] p_pkey) throws DBProcessingException {
        String l_pkey = null;
        try {
            int l_length_of_the_p_pkey = p_pkey.length;

            int array_index_of_p_pkey = 0;

            while (l_length_of_the_p_pkey != 0) {
                if (array_index_of_p_pkey == 0) {
                    l_pkey = p_pkey[array_index_of_p_pkey];
                    array_index_of_p_pkey++;
                    l_length_of_the_p_pkey--;
                } else {
                    l_pkey = l_pkey.concat("~").concat(p_pkey[array_index_of_p_pkey]);
                    array_index_of_p_pkey++;
                    l_length_of_the_p_pkey--;
                }

            }

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }
        return l_pkey;
    }

    /*this function removes the empty character from the record*/
    private String triming(String kc) throws DBProcessingException {
        try {
            String[] l_kc = kc.split("~");
            String l_trimmed = null;
            int l_array_index = 0;
            while (l_array_index < l_kc.length) {
                if (l_array_index == 0) {
                    l_trimmed = l_kc[l_array_index].trim();
                    l_array_index++;
                } else {
                    l_trimmed = l_trimmed.concat("~").concat(l_kc[l_array_index].trim());
                    l_array_index++;
                }
            }

            return l_trimmed;
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
    }

    private String recordToUpdate(String p_file_type, String p_table_id, String[] p_column_to_be_updated, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
        try {
            p_column_to_update.forEach((String k, String v) -> {
                p_column_to_be_updated[Integer.parseInt(k) - 1] = v;
            });
            //String l_record_to_update = "#".concat(p_table_id);
            String l_record_to_update = null;
            //int l_length = p_column_to_be_updated.length;
            //int array_index = 0;
            /*while (l_length > 0) {
            if (array_index == 0) {
                l_record_to_update = p_column_to_be_updated[array_index];
                array_index++;
                l_length--;
            } else {
                l_record_to_update = l_record_to_update.concat("~").concat(p_column_to_be_updated[array_index]);
                array_index++;
                l_length--;
            }
        }*/
            l_record_to_update = getRecord(p_file_type, Integer.parseInt(p_table_id), p_column_to_be_updated);
            /*IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
        dbcs.getG_dbMetaData().forEach((String kc , DBFile vc)->{
            if(vc.getI_FileName().equals(p_file_type)){
                
            }
        });*/
            return l_record_to_update;
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }

    }

    /*Finds the length of the record of a particular table*/
   /* private long lengthOfTheRecord(int p_tableid) throws DBProcessingException {
        long l_length_of_the_record = 0;
        try {
            
            
            IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
            
            Iterator iterator1 =dbcs.getG_dbMetaData().keySet().iterator();
            Iterator iterator2 =dbcs.getG_dbMetaData().values().iterator();
            while(iterator1.hasNext()&&iterator2.hasNext()){
               // String l_key = (String)iterator1.next();
               DBFile l_dbfile= (DBFile)iterator2.next();
               Iterator iterator3=l_dbfile.getI_TableCollection().keySet().iterator();
               Iterator iterator4=l_dbfile.getI_TableCollection().values().iterator();
               while(iterator3.hasNext()&&iterator4.hasNext()){
                   // String l_dbtable_key = (String)iterator1.next();
                   DBTable l_dbtable = (DBTable)iterator4.next();
                    if (l_dbtable.getI_Tableid() == p_tableid) {
                       Iterator iterator5= l_dbtable.getI_ColumnCollection().keySet().iterator();
                       Iterator iterator6= l_dbtable.getI_ColumnCollection().values().iterator();
                       while(iterator5.hasNext()&&iterator6.hasNext()){
                          // String l_dbcolumn_key = (String)iterator1.next();
                           DBColumn l_dbcolumn = (DBColumn)iterator4.next();
                           l_length_of_the_record = l_length_of_the_record + l_dbcolumn.getI_ColumnLength();
                           
                       }
                      l_length_of_the_record += l_dbtable.getI_ColumnCollection().size(); 
                    }
                   
               }
                
                
            }
           /* dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_Tableid() == p_tableid) {
                        v.getI_ColumnCollection().forEach((String l, DBColumn m) -> {
                            l_length_of_the_record = l_length_of_the_record + m.getI_ColumnLength();
                        });
                        l_length_of_the_record += v.getI_ColumnCollection().size();//v.getI_ColumnCollection().size() indicates no of tilda separators
                    }
                });
            });
            //i_length_of_the_record += 1;//1 is for #
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally{
           
        }
        return l_length_of_the_record;
    }*/
    
    private void updateIndexFile(String p_file_name,String p_table_id,String l_primary_key){
        
        
    }
    
    
    
    

    public void display(IIndexCoreService ics) {
        //dbdi.createSessionObject();
        
        ics.getIndex_map().forEach((String kc, Map<String, String> vc) -> {
            dbg("index map key->" + kc);
            vc.forEach((String k, String v) -> {
                dbg("inner map key->" + k);
                dbg("inner map value->" + v);
            });
        });
        //dbdi.clearSessionObject();
    }

    /*public void findFileNames(String p_file_name,int p_fileid,int p_tableid){
        String[] l_filenames=new String[DBConstants.FILE_NAMES_TO_BE_CHANGED.length];
        int k=0;
        String l_search_item = String.valueOf(p_fileid).concat("~").concat(String.valueOf(p_tableid));
        for(int i=0;i<DBConstants.TABLE_LEVEL_RELATIONSHIP.length;i++){
            for(int j=0;j<DBConstants.TABLE_LEVEL_RELATIONSHIP[0].length;j++){
                if(l_search_item.equals(DBConstants.TABLE_LEVEL_RELATIONSHIP[i][j])){
                    l_filenames[k] = DBConstants.FILE_NAMES_TO_BE_CHANGED[i][j];
                    k++;
                }
            }
            
        }
        int m=0;                  
       while(m<=k){
           String[] l_column_names=l_filenames[m].split("^");
       }
        
    }*/
    
   public void lockProcessing(String p_file_name)throws DBProcessingException{
       
       
       try{
       IIBDFileUtil iibd=dbSession.getIibd_file_util();
       IBDProperties i_db_properties=session.getCohesiveproperties();
       ILockService lock=dbdi.getLockService();
       Path l_file_path=Paths.get(p_file_name);
       Path l_temp_path=Paths.get(dbSession.getIibd_file_util().getTempPath(p_file_name));
       
       if(getLock(p_file_name)==true){
       if( lock.isSameSessionLock(p_file_name, this.session)){
           dbg("same session is locked");
           iibd.copyFileToTemp(l_file_path,l_temp_path);
           
       } 
       }
       
      }catch(InvalidPathException ex){
       dbg(ex);
            throw new DBProcessingException("InvalidPathException exception" + ex.toString());  
      }catch(IllegalArgumentException ex){
       dbg(ex);
            throw new DBProcessingException("IllegalArgumentException exception" + ex.toString());  
      }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }   
          
    
   }
   
   
       

      private boolean getLock(String p_file_name)throws DBProcessingException{
        ILockService lock;
        try {
            lock=dbdi.getLockService();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }
        try{
        while(!lock.createLock(p_file_name,this.session,this.dbSession)){
            
            Thread.sleep(1); 
            
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
    public boolean getImplictRecordLock(String p_file_name,String p_table_name,String p_primary_key)throws DBProcessingException{
        ILockService lock;
        long startTime=0;
        try {
            startTime=dbSession.getIibd_file_util(). getStartTime();
            lock=dbdi.getLockService();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }
        try{
        while(!lock.createImplictRecordLock(p_file_name,p_table_name,p_primary_key,this.session,this.dbSession)){
            
            Thread.sleep(1); 
            
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
        finally{
            try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_file_name,p_table_name,p_primary_key,"DBTransactionService","getImplictRecordLock",session,dbSession,dbdi,startTime);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
            
            
        }
        
    }
    
    
     public boolean getImplictRecordLock(String p_file_name,String p_table_name,String p_primary_key,CohesiveSession session)throws DBProcessingException{
        CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           return getImplictRecordLock(p_file_name,p_table_name,p_primary_key);
       } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
//        }catch(DBValidationException ex){
//            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }finally {
           this.session=tempSession;
            
        } 
        
        
    }
    
   private boolean checkVersionAvailability(String p_fileType,String p_table_name) throws DBProcessingException,DBValidationException{
       boolean l_versionStatus=false;
     try{  
         dbg("inside check version availability");
         dbg("check version availability-->fileType"+p_fileType);
         dbg("check version availability-->p_table_name"+p_table_name);
         IMetaDataService mds=dbdi.getMetadataservice();
         Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(p_fileType, p_table_name,session).getI_ColumnCollection();
            Iterator columnIterator=l_columnCollection.values().iterator();
            while(columnIterator.hasNext()){
             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
             if(l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")){
                 l_versionStatus=true;
                 dbg("version status is true");
             }else{
                 l_versionStatus=false;
                 dbg("version status is false");
             }
             if(l_versionStatus==true)
                 break;
            }
            dbg("inside check version availability"+l_versionStatus);
     }catch(DBValidationException ex){
         throw ex;
     }catch(DBProcessingException ex){
         dbg(ex);
       throw new DBProcessingException("DBProcessingException".concat(ex.toString()));
     }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
      } 
     return l_versionStatus;
   }
   
   private int getVersionNumberFromTheRecord(String p_fileType,String p_table_name,String[] p_records)throws DBProcessingException,DBValidationException{
       
       try{
           dbg("inside getVersionNumberFromTheRecord");
           IMetaDataService mds=dbdi.getMetadataservice();
           int versionColId= mds.getColumnMetaData(p_fileType, p_table_name, "VERSION_NUMBER",session).getI_ColumnID();

           dbg("version number return from getVersionNumberFromTheRecord"+p_records[versionColId-1]);
         return Integer.parseInt(p_records[versionColId-1].trim()) ;
           
      }catch(DBValidationException ex){
         throw ex;
     }catch(DBProcessingException ex){
         dbg(ex);
       throw new DBProcessingException("DBProcessingException".concat(ex.toString()));
     }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
      } 
       
   }
   
   
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }

}
