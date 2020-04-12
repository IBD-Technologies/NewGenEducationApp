/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.read;

//import com.ibd.cohesive.db.core.DBCoreService; //EJB Integration change
import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.index.IIndexReadService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.db.util.PositionAndRecord;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
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
 *
 * Change Description :cross call is used to indicate that when function of one
 * service calls function of other service through the temporary method where
 * DBDI is assigned and then calls the original method then session object
 * created should not be recreated again in the called method.so to indicate
 * this we use the cross call instance variable. in the called method crosscall
 * field is set to true initially.when it calls the original method the original
 * method identifies whether it is a cross call or not and creates the session
 * object accordingly. Changed by : Suriya Narayanan.L Changed on : 14/05/2018
 * Search Tag : CODEREVIEW_3
 *
 * Change Description :since table name is not unique it alone can not be used
 * to get table meta data we have to include filetype as well in the table name
 * validation. Changed by : Suriya Narayanan.L Changed on : 14/05/2018 Search
 * Tag : CODEREVIEW_6
 *
 *
 * Change Description :i_dummy_pk array instance variable must be changed to
 * local variable since it is used only in record read method. Changed by :
 * Suriya Narayanan.L Changed on : 14/05/2018 Search Tag : CODEREVIEW_10
 *
 *
 * Change Description :In read fullfile the filetype is single not all the
 * filetype .so comment the code that assigns the other filetype. Changed by :
 * Suriya Narayanan.L Changed on : 15/05/2018 Search Tag : CODEREVIEW_11
 *
 * Change Description :In special character validation of primarykey we have to
 * put the l_dbcolumn finding code into if condition because that is needed only
 * when exception is thrown. Changed by : Suriya Narayanan.L Changed on :
 * 15/05/2018 Search Tag : CODEREVIEW_12
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
public class DBReadService implements IDBReadService {

    //Map<String, ArrayList<String>> i_Records = new HashMap();
    //  DBCoreService dbcs;
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    //DBValidation dbv;
    //String i_key=null;//to support lamda expression in recordRead() method
    //int i_col_len; //to support lamda expression in recordRead() method
    //ArrayList<String> i_record = new ArrayList();//to support lamda expression in recordRead() method
    ///int l_tableId;//to support lamda expression in recordRead() method
    //boolean l_index_required;//to support lamda expression in recordRead() method
   // String l_pkey = null;//to support lamda expression in recordRead() method
    //String[] i_Column_Values = {};//to support lamda expression in recordRead() method
    //String[] Column_Values = {};//to support lamda expression in recordRead() method
    //int i = 0;//to support lamda expression in recordRead() method
    //String i_dummy = null;//to support lamda expression in recordRead() method
    //String i_dummy_Pk = null;//to support lamda expression 
    //String i_pk;
    //String[] i_dummy_pk;CODEREVIEW_10
   // String[] i_column_name;
    //int j = 0;
   // boolean crossCall = false;//CODEREVIEW_3

    public DBReadService() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
        //dbv = dbdi.getDbv();
    }

    @Override
    /*this function reads the entire file whose name is specified by p_FileName
    in param and file type is specified by the p_FileType param
    in param is file name and filetype
    out param is a Map whose key is a combination of table id and primarykey and the value is a array list of the column values*/
    public Map<String, ArrayList<String>> readFullFile(String p_FileName, String p_FileType) throws DBValidationException, DBProcessingException {
       
        Map<String, ArrayList<String>> l_records = new HashMap();
        Scanner l_File_Content = null;
        boolean l_session_created_now=false;
        long startTime=0;
         try {
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler = session.getErrorhandler();
        DBValidation dbv = dbSession.getDbv();
        startTime=dbSession.getIibd_file_util(). getStartTime();
        boolean l_validation_status = true;
        //int l_array_index1;
        //int l_pk_values_length;
        int l_Table_Id;

        String[] l_Column_Values;
        String l_key;
       //String[] l_pk_values;
        String l_PK;
        int l_array_index;
        String[] p_pkey = {};
        String p_table_id = "0";
 dbg("entered readfullfile");
       
            
            dbg("inside try");
            if (fileExistence(p_FileName)) {
                dbg("inside if file existence");
                if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                    dbg("validation status is false");
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
                
                IDBCoreService dbcs = dbdi.getDbcoreservice(); //EJB Integration change
                Map<String,String> l_dummy = new HashMap();
                //Cohesive1_Dev_2 starts here
               // PositionAndRecord par = dbdi.getIibd_file_util().sequentialRead(p_FileName, Integer.parseInt(p_table_id), p_pkey,l_dummy,dbdi);
                
                ILockService lock=dbdi.getLockService();
                 String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_FileName);
                 PositionAndRecord par;
                 long startTime1=dbSession.getIibd_file_util(). getStartTime();
                 try{
                if(lock.isSameSessionLock(p_FileName, session)){
                    
                   par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, Integer.parseInt(p_table_id), p_pkey,l_dummy,session,dbdi);
                    
                }
                else{
                    
                    par = dbSession.getIibd_file_util().sequentialRead(p_FileName, Integer.parseInt(p_table_id), p_pkey,l_dummy,session,dbdi);
                }
                }catch(FileNotFoundException ex){
                    StringBuffer single_error_code = new StringBuffer();
                    single_error_code = single_error_code.append("DB_VAL_000");
                    errorhandler.setSingle_err_code(single_error_code);
                    errorhandler.log_error();
                    dbg("file doesn't exit");
                //throw new DBValidationException("DB_VAL_011");
                throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }finally{
                     try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_FileName,null,null,"DBReadService","readFullFile.sequentialRead Call",session,dbSession,dbdi,startTime1);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
                 }
          
               //Cohesive1_Dev_2 ends here
               
               
               
               int i = 0;
                while (i < par.getI_records().size()) {
                    l_Column_Values = par.getI_records().get(i).split(session.getCohesiveproperties().getProperty("COLUMN_DELIMITER"));
                    l_Table_Id = Integer.parseInt(l_Column_Values[0]);
                    l_array_index = 1;
                    ArrayList<String> l_value = new ArrayList();
                    while (l_array_index <= l_Column_Values.length - 1) {

                        l_value.add(l_Column_Values[l_array_index]);
                        l_array_index++;

                    }
                    for(int k=0;k<l_Column_Values.length;k++){
                        dbg("l_Column_Values   "+l_Column_Values[k]);
                    }
                    l_PK = getPrimaryKey(p_FileType, l_Table_Id, l_Column_Values); //mds.getFileMetaData(p_FileName).getI_TableCollection().get(i_Table_Id).getI_Pkey();

                    l_key = String.valueOf(l_Table_Id).concat("~").concat(l_PK);
                    //dbg("in dbread service->read full file->l_key->" + l_key);
                    //dbg("in dbread service->read full file->l_value->" + l_value);
                    l_records.put(l_key, l_value);
                    i++;
                }

                
                /*  l_File_Content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_FileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));

                l_File_Content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));

                while (l_File_Content.hasNext()) {
                    ArrayList<String> l_value;
                    String samp = l_File_Content.next();
                    dbg("in dbread service->read full file->samp->" + samp);
                    if (samp.contains("~")) {
                        l_array_index = 1;
                        l_value = new ArrayList();
                        // dbg(samp);
                        l_Column_Values = samp.split(dbcs.getI_db_properties().getProperty("COLUMN_DELIMITER"));
                        l_Table_Id = Integer.parseInt(l_Column_Values[0]);

                        while (l_array_index <= l_Column_Values.length - 1) {

                            l_value.add(l_Column_Values[l_array_index]);
                            l_array_index++;

                        }
                        /*CODEREVIEW_11 starts here
                        if (l_Table_Id < 3) {
                            p_FileType = "STUDENT";
                        } else if (l_Table_Id < 5) {
                            p_FileType = "USER";
                        } else if (l_Table_Id < 7) {
                            p_FileType = "TEACHER";
                        } else if (l_Table_Id == 7) {
                            p_FileType = "MANAGEMENT";
                        } else if (l_Table_Id < 15) {
                            p_FileType = "INSTITUTE";
                        }
                        CODEREVIEW_11  ends here*/
 /* dbg("in dbread service->read full file->p_FileType->" + p_FileType);
                        l_PK = getPrimaryKey(p_FileType, l_Table_Id, l_Column_Values); //mds.getFileMetaData(p_FileName).getI_TableCollection().get(i_Table_Id).getI_Pkey();

                        l_key = String.valueOf(l_Table_Id).concat("~").concat(l_PK);
                        dbg("in dbread service->read full file->l_key->" + l_key);
                        dbg("in dbread service->read full file->l_value->" + l_value);
                        l_records.put(l_key, l_value);

                    }*/
 /* The following code displays the l_records
                 */
               /* l_records.forEach((kc, vc) -> {

                    //dbg("inside readFullFile l_records key-->" + kc);
                    vc.forEach((action) -> {
                        dbg("inside readFullFile  \"l_records key\" and \"l_records values\"-->" + kc + "  and  " + action);
                    }
                    );

                });*/
                // }
            }
            else{
                throw new DBValidationException("DB-VAL-000");
            }
            dbg("Executed successfully");
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
        } /*catch (FileNotFoundException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        }
        } */ 
        catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            if (l_File_Content != null) {
                l_File_Content.close();
            }
            try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_FileName,null,null,"DBReadService","readFullFile",session,dbSession,dbdi,startTime);

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
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
        Map<String,ArrayList<String>>trimmedRecordMap=new HashMap();
        
        Iterator<String>keyIterator=l_records.keySet().iterator();
        
        while(keyIterator.hasNext()){
            
            String key=keyIterator.next();
            ArrayList<String>value=l_records.get(key);
            ArrayList<String>trimmedValue=new ArrayList();
            
            for(int i=0;i<value.size();i++){
                
                trimmedValue.add(value.get(i).trim());
                
            }
            
            trimmedRecordMap.put(key, trimmedValue);
        }
        
        
         
//        return l_records;
          return trimmedRecordMap;
    }
    //This function returns the map such that Key is TableName and Value is Map of (Key - Primary key,Values - DBRecord with position)
    public Map<String, Map<String,DBRecord>> dbPhysicalRead(String p_FileName, String p_FileType) throws DBValidationException, DBProcessingException {
        Map<String, Map<String,DBRecord>>l_fileMap;
        Scanner l_File_Content = null;
        boolean l_session_created_now=false;
        
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        ErrorHandler errorhandler = session.getErrorhandler();
        DBValidation dbv = dbSession.getDbv();
        boolean l_validation_status = true;

 dbg("entered readfullfile");
        try {
            dbg("inside try");
            if (fileExistence(p_FileName)) {
                dbg("inside if file existence");
                if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                    dbg("validation status is false");
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
                    l_fileMap = dbSession.getIibd_file_util().sequentialRead(p_FileName,session,dbdi);
                    
                }catch(FileNotFoundException ex){
                    StringBuffer single_error_code = new StringBuffer();
                    single_error_code = single_error_code.append("DB_VAL_000");
                    errorhandler.setSingle_err_code(single_error_code);
                    errorhandler.log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }

            }
            else{
                throw new DBValidationException("DB-VAL-000");
            }
            dbg("Executed successfully");
                                return l_fileMap;

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
        } /*catch (FileNotFoundException ex) {
            dbg(ex);
            throw new DBProcessingException("FileNotFoundException" + ex.toString());
        }
        } */ 
        catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            if (l_File_Content != null) {
                l_File_Content.close();
            }
            if(l_session_created_now)
            {    
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
            }
        }

    }
    public Map<String, Map<String,DBRecord>> dbPhysicalRead(String p_FileName, String p_FileType,CohesiveSession session) throws DBValidationException, DBProcessingException {
    CohesiveSession tempSession = this.session;
    
    try{
        
        this.session=session;
        return dbPhysicalRead(p_FileName,p_FileType);
    } catch(DBValidationException ex){
            throw ex;
    } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
       }finally{
           this.session=tempSession;
        }
    }
    //Cohesive1_Dev_3 starts here
    //This is a cross call function for readFullFile
    public Map<String, ArrayList<String>> readFullFile(String p_FileName, String p_FileType,CohesiveSession session) throws DBValidationException, DBProcessingException {
         CohesiveSession tempSession = this.session;
         Map<String, ArrayList<String>> l_records = new HashMap();
         
         try{
        this.session=session;
        l_records=readFullFile(p_FileName,p_FileType);
        
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
        }  catch(DBValidationException ex){
            throw ex;
        }  catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally{
           this.session=tempSession;
        }
        
        return l_records;
    }
    //Cohesive1_Dev_3 ends here

    /*This function reads a table and returns a map whose key is primarykey and value is a arraylist of particular record's column values.This column values do not contain table id*/
    public Map<String, ArrayList<String>> readTable(String p_FileName, String p_FileType, String p_TableName) throws DBValidationException, DBProcessingException {
        Map<String, ArrayList<String>> l_records = new HashMap();
        boolean l_session_created_now=false;
        Scanner l_File_Content = null;
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        dbg("in dbread service->inside read table->p_FileName" + p_FileName);
        dbg("in dbread service->inside read table->p_FileType" + p_FileType);
        dbg("in dbread service->inside read table->p_TableName" + p_TableName);
        try {

            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errorhandler = session.getErrorhandler();
//            if (fileExistence(p_FileName)) {
                dbg("inside file existence of read service");
                boolean l_validation_status = true;
                if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }
//                if (!dbv.fileNameValidation(p_FileName, errorhandler,session,dbdi)) {
//                    l_validation_status = false;
//                    errorhandler.log_error();
//
//                }
                if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                //if (!dbv.tableNameValidation(p_TableName, errorhandler)) {//CODEREVIEW_6
                if (!dbv.tableNameValidation(p_FileType, p_TableName, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }
                if (!l_validation_status) {
                    throw new DBValidationException(errorhandler.getSession_error_code().toString());
                }

                IMetaDataService mds = dbdi.getMetadataservice();

                ArrayList<String> l_value;

                int l_Table_Id;
                String[] l_Column_Values;
               // String l_PK;
               // String[] l_pk_values;
                String l_key = null;
               // int l_pk_array_index1 = 0;
                int l_value_array_index = 1;
                //MetaDataService mds = new MetaDataService();
                l_Table_Id = mds.getTableMetaData(p_FileType, p_TableName, this.session).getI_Tableid();
                dbg("l_Table_Id -->" + l_Table_Id);
                String[] p_pkey = {};
                //l_records.clear();
                //dbg(p_pkey.length);
                //IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
                Map<String,String> l_dummy = new HashMap();
                //Cohesive1_Dev_2 starts here
               // PositionAndRecord par = dbdi.getIibd_file_util().sequentialRead(p_FileName, l_Table_Id, p_pkey,l_dummy,dbdi);
                 ILockService lock=dbdi.getLockService();
                 //IDBTransactionService dbts=dbdi.getDBTransactionService();
                 String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_FileName);
                 PositionAndRecord par;
                 dbg("before checking of same session");
                 try{
                if(lock.isSameSessionLock(p_FileName, session)){
                    dbg("same session lock");
                   par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, l_Table_Id, p_pkey,l_dummy,session,dbdi);
                    
                }
                else{
                    dbg("another session lock");
                    par = dbSession.getIibd_file_util().sequentialRead(p_FileName, l_Table_Id, p_pkey,l_dummy,session,dbdi);
                }
                }catch(FileNotFoundException ex){
                    StringBuffer single_error_code = new StringBuffer();
                    single_error_code = single_error_code.append("DB_VAL_000");
                    errorhandler.setSingle_err_code(single_error_code);
                    errorhandler.log_error();
                    dbg("file doesn't exit");
                //throw new DBValidationException("DB_VAL_011");
                throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }

                //Cohesive1_Dev_2 ends here
                int i = 0;
                while (i < par.getI_records().size()) {
                    l_Column_Values = par.getI_records().get(i).split("~");
                    for(String s:l_Column_Values){
                         dbg("l_Column_Valuess"+s);
                    }
                   
                    l_value = new ArrayList();
                    l_value.clear();

                    //dbg("l_Column_Values[0] -->" + l_Column_Values[0]);
                    if (String.valueOf(l_Table_Id).equals(l_Column_Values[0])) {
                        // dbg("readTable ->" + samp);
                        l_key = getPrimaryKey(p_FileType, l_Table_Id, l_Column_Values);
                        dbg("in dbread service->inside read table->l_key" + l_key);
                        l_value_array_index = 1;
                        while ((l_Column_Values.length - 1) >= l_value_array_index) {

                            l_value.add(l_Column_Values[l_value_array_index]);
                            // System.out.println(tokens);
                            dbg("l_Column_Values[l_value_array_index]" + l_Column_Values[l_value_array_index]);
                            l_value_array_index++;

                        }
                        // }
                        l_records.put(l_key, l_value);
                    }
                    i++;
                }
                //dbcs.start();
                /* l_File_Content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_FileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
                l_File_Content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));
                while (l_File_Content.hasNext()) {
                    String samp = l_File_Content.next();
                    if (samp.contains("~")) {
                        l_Column_Values = samp.split("~");

                        l_value = new ArrayList();
                        l_value.clear();

                        //dbg("l_Column_Values[0] -->" + l_Column_Values[0]);
                        if (String.valueOf(l_Table_Id).equals(l_Column_Values[0])) {
                            dbg("readTable ->" + samp);
                            l_key = getPrimaryKey(p_FileType, l_Table_Id, l_Column_Values);
                            dbg("in dbread service->inside read table->l_key" + l_key);
                            l_value_array_index = 1;
                            while ((l_Column_Values.length - 1) >= l_value_array_index) {

                                l_value.add(l_Column_Values[l_value_array_index]);
                                // System.out.println(tokens);
                                dbg("l_Column_Values[l_value_array_index]" + l_Column_Values[l_value_array_index]);
                                l_value_array_index++;

                            }
                            // }
                            l_records.put(l_key, l_value);
                        }

                    }
                }*/
 /*//This piece of code prints the readtable method  output
                l_records.forEach((String kc , ArrayList vc)->{
                    dbg("\n");
                vc.forEach((action)->{
                  dbg("key and l_records values"+kc+"     "+ action);  
                });
                });*/
//            }
           
            if (l_records==null||l_records.isEmpty()) {
                dbg("record is empty");
                StringBuffer single_error_code = new StringBuffer();
                single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_TableName).concat("," +" " ));//Integration changes 
                errorhandler.setSingle_err_code(single_error_code);
                errorhandler.log_error();
                //throw new DBValidationException("DB_VAL_011");
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
 
            }
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
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
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalStateException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if (l_File_Content != null) {
                l_File_Content.close();
            }
            // dbdi.setErrorhandlerdummy();
            if(l_session_created_now)
            {    
            session.clearSessionObject();
            dbSession.clearSessionObject();
            }
        }
        Map<String,ArrayList<String>>trimmedRecordMap=new HashMap();
        
        Iterator<String>keyIterator=l_records.keySet().iterator();
        
        while(keyIterator.hasNext()){
            
            String key=keyIterator.next();
            ArrayList<String>value=l_records.get(key);
            ArrayList<String>trimmedValue=new ArrayList();
            
            for(int i=0;i<value.size();i++){
                
                trimmedValue.add(value.get(i).trim());
                
            }
            
            trimmedRecordMap.put(key, trimmedValue);
        }

        return trimmedRecordMap;
    }
    
    //Cohesive1_Dev_3 starts here
    public Map<String, ArrayList<String>> readTable(String p_FileName, String p_FileType, String p_TableName,CohesiveSession session) throws DBValidationException, DBProcessingException{
       Map<String, ArrayList<String>> l_records = new HashMap(); 
       CohesiveSession tempSession = this.session;
        try{
        this.session=session;
        l_records=readTable(p_FileName,p_FileType,p_TableName);
        
         }catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
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
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalStateException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }finally{
           this.session=tempSession;
        }
        
     return l_records;   
    }
   //Cohesive1_Dev_3 ends here
    private String getPrimaryKey(String p_FileType, int p_Table_Id, String[] p_Column_Values) throws DBValidationException, DBProcessingException {
        IMetaDataService mds ;
        
        try {
            mds = dbdi.getMetadataservice();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }
        
        String l_PK_Value = null;
        dbg("inside dbread service->in getprimarykey->p_FileType" + p_FileType);
        dbg("inside dbread service->in getprimarykey->p_Table_Id" + p_Table_Id);
        dbg("inside dbread service->in getprimarykey->p_Table_Id" + p_Column_Values[0]);
        try {

            String l_PK = mds.getFileMetaData(p_FileType, this.session).getI_TableCollection().get(String.valueOf(p_Table_Id)).getI_Pkey();
            dbg("getPrimaryKey1 getPrimaryKey1 getPrimaryKey1" + l_PK);
            String[] l_PK_Column_Values = l_PK.split("~");
            int l_PK_Column_Values_Size = l_PK_Column_Values.length;
            int array_Index = 0;
            while (l_PK_Column_Values_Size != 0) {
                if (array_Index == 0) {
                    l_PK_Value = p_Column_Values[Integer.parseInt(l_PK_Column_Values[array_Index])];
                    //dbg("l_PK_Value l_PK_Value array_Index==0"+l_PK_Value);
                    l_PK_Value.trim();
                    array_Index++;
                    l_PK_Column_Values_Size--;
                } else {
                    l_PK_Value = (l_PK_Value.trim()).concat("~").concat(p_Column_Values[Integer.parseInt(l_PK_Column_Values[array_Index])].trim());
                    array_Index++;
                    l_PK_Column_Values_Size--;
                }

            }
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        return l_PK_Value;
    }

    /*This function is going to read particular record based on the primarykey values.
     @inparam filename , tablename, MetaDataService,primarykey values
    out param is a column values of a particular record*/
    public String[] recordRead(String p_FileName, String p_FileType, String p_TableName, String... p_primary_key) throws DBValidationException, DBProcessingException {

        FileChannel fc = null;
        String[] l_Column_Value = {};
        boolean l_session_created_now=false;
        //Scanner l_File_Content = null;

        try {
            if (fileExistence(p_FileName)) {
                
                boolean l_index_required;
                String l_pkey = null;
                int l_col_len=0;
                session.createSessionObject();
                dbSession.createDBsession(session);
                l_session_created_now=session.isI_session_created_now();
                //Cohesive1_Dev_2 starts here
                ILockService lock=dbdi.getLockService();
                //IDBTransactionService dbts=dbdi.getDBTransactionService();
                String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_FileName);
                //Cohesive1_Dev_2 ends here
                DBValidation dbv = dbSession.getDbv();
                ErrorHandler errorhandler = session.getErrorhandler();
               // IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
                String[] i_dummy_pk;//CODEREVIEW_10

                IMetaDataService mds = dbdi.getMetadataservice();
                int l_Table_Id;
                String[] l_Column_Values = {};
               // String[] l_column = {};
               // String l_key = null;
               // int l_value_array_index;

               // String l_PK = null;

                boolean l_validation_status = true;
              //  int l_primary_key_length = p_primary_key.length;
              //  int l_counter = 0;

                IMetaDataService ims = dbdi.getMetadataservice();
                IIndexReadService indx = dbdi.getIndexreadservice();
               // int var_args_array_index;

                ByteBuffer copy;
                int l_position;
              //  int l_nread;
                int i = 0;
               // int j = 0;
                /* DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String k,DBFile v)->{
                   
                    if(v.getI_FileName().equals(p_FileType)){
                        dbg("v.getI_FileName()+p_FileType"+v.getI_FileName()+p_FileType);
                        v.getI_TableCollection().forEach((String kc,DBTable vc)->{
                             dbg("in->recordRead->inside ");
                             dbg("in->recordRead->vc.getI_TableName()"+vc.getI_TableName());
                            if(vc.getI_TableName().equals(p_TableName)){
                                i_dummy_pk=vc.getI_Pkey().split("~");
                                dbg("in->recordRead->i_dummy_pk.length"+i_dummy_pk.length);
                                
                            }
                            vc.getI_ColumnCollection().forEach((String l,DBColumn m)->{
                                while(j<i_dummy_pk.length){
                                    if(Integer.parseInt(i_dummy_pk[j])==m.getI_ColumnID()){
                                        i_column_name[j]=m.getI_ColumnName();
                                    }
                                    j++;
                                }
                            });
                            
                        });
                    }
                });*/

                DBTable l_dbtable = mds.getTableMetaData(p_FileType, p_TableName, this.session);
                i_dummy_pk = l_dbtable.getI_Pkey().split("~");
                while (i < p_primary_key.length) {
                    //DBColumn l_dbcolumn = mds.getColumnMetaData(p_FileType, p_TableName, Integer.parseInt(i_dummy_pk[i]), this.dbdi);CODEREVIEW_12
                    if (!dbv.specialCharacterValidation(p_primary_key[i], errorhandler)) {
                        l_validation_status = false;

                        DBColumn l_dbcolumn = mds.getColumnMetaData(p_FileType, p_TableName, Integer.parseInt(i_dummy_pk[i]), this.session);
                        errorhandler.getSingle_err_code().append("^" + p_FileName + "^" + p_TableName + "^" + l_dbcolumn.getI_ColumnName());
                        errorhandler.log_error();

                    }
                    i++;
                }
                /*Cohesive_Dev_1 starts here
                DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                    vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                        if (v.getI_TableName().equals(p_TableName)) {
                            i_dummy_Pk = v.getI_Pkey();
                        }
                    });
                });*/
                //String[] l_dummy_Pk = i_dummy_Pk.split("~");
                
                if((i_dummy_pk.length!=p_primary_key.length)){
                     StringBuffer l_single_error_code = new StringBuffer();
                     l_single_error_code = l_single_error_code.append("DB_VAL_010");
                     errorhandler.setSingle_err_code(l_single_error_code);
                     l_validation_status = false;
                     errorhandler.log_error();
                 }
                 else{/*If the above "if" condition is  satisfied the below validation will raise ArrayInbound exception.
                     So that below condition should be executed if the above condition fails*/
                 i=0;
                 
                while (i < i_dummy_pk.length) {
                    if (!dbv.columnDataTypeValidation(p_TableName, Integer.parseInt(i_dummy_pk[i]), p_primary_key[i], errorhandler,dbdi)) {
                        l_validation_status = false;
                        errorhandler.log_error();

                    }
                    i++;
                }
                i = 0;
                while (i < i_dummy_pk.length) {
                    if (!dbv.columnLengthValidation(p_TableName, p_primary_key[i], Integer.parseInt(i_dummy_pk[i]), errorhandler,dbdi)) {
                        l_validation_status = false;
                        errorhandler.log_error();

                    }
                    i++;
                }
                 }
                /*Cohesive_Dev_2 starts here
                while (i < i_dummy_pk.length) {
                    if (!dbv.columnDataTypeValidation(p_TableName, Integer.parseInt(i_dummy_pk[i]), p_primary_key[i], errorhandler)) {
                        l_validation_status = false;
                        errorhandler.log_error();

                    }
                    i++;
                }
                i = 0;
                while (i < i_dummy_pk.length) {
                    if (!dbv.columnLengthValidation(p_TableName, p_primary_key[i], Integer.parseInt(i_dummy_pk[i]), errorhandler)) {
                        l_validation_status = false;
                        errorhandler.log_error();

                    }
                    i++;
                }*/

                if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.fileNameValidation(p_FileName, errorhandler,session,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                //if (!dbv.tableNameValidation(p_TableName, errorhandler)) {//CODEREVIEW_6
                if (!dbv.tableNameValidation(p_FileType, p_TableName, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }

                if (l_validation_status == false) {
                    throw new DBValidationException(errorhandler.getSession_error_code().toString());
                }
                /*  dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                    dbg("kc --->" + kc);
                    dbg("p_FileName -->" + p_FileName);
                    if (kc.equals(p_FileType)) {

                        l_index_required = vc.isI_index_required();
                        return;
                    }
               
                
                
                   /*vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                        if (v.getI_TableName().equals(p_TableName)) {
                            l_tableId = v.getI_Tableid();
                            return;
                        }
                    });   });*/

                l_index_required = mds.isIndexRequired(p_FileType);
                dbg("l_index_required" + l_index_required);
                /*while (l_primary_key_length > 0) {
                    if (l_counter == 0) {
                        l_PK = p_primary_key[l_counter];
                        l_counter++;
                        l_primary_key_length--;
                    } else {
                        l_PK = l_PK.concat("~").concat(p_primary_key[l_counter]);
                        l_counter++;
                        l_primary_key_length--;
                    }
                }*/

                /*if (!dbv.pkValidation(l_tableId, l_PK, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }*/
                //dbcs.start();
                String encoding = System.getProperty("file.encoding");
                dbg("inside recordRead file name ->" + p_FileName);
                dbg("inside recordRead table name ->" + p_TableName);
                //dbg("inside recordRead primary key[0] ->" + p_primary_key[0]);
                dbg("inside recordRead encoding->" + encoding);
                for (i = 0; i <= p_primary_key.length - 1; i++) {
                    if (i == 0) {
                        l_pkey = p_primary_key[0];
                    } else {
                        l_pkey = l_pkey.concat("~").concat(p_primary_key[i]);
                    }
                }
                
                Iterator iterator=ims.getTableMetaData(p_FileType, p_TableName, session).getI_ColumnCollection().values().iterator();
                while(iterator.hasNext()){
                    DBColumn l_dbcolumn=(DBColumn) iterator.next();
                    l_col_len = l_col_len + l_dbcolumn.getI_ColumnLength();
                }
                /*ims.getTableMetaData(p_FileType, p_TableName, dbdi).getI_ColumnCollection().forEach((String k, DBColumn v) -> {
                    i_col_len = i_col_len + v.getI_ColumnLength();
                });*/
                dbg("record read -->PKEY" + l_pkey);
                //dbg("in DBRead service->table id->"+String.valueOf(ims.getTableMetaData(p_FileType, p_TableName, dbdi).getI_Tableid()));
                //dbg("record read -->new index->"+indx.readIndex("suriy", "8", "ins1", this.dbdi));
                if (l_index_required) {
                    l_position = indx.readIndex(p_FileName, String.valueOf(ims.getTableMetaData(p_FileType, p_TableName, session).getI_Tableid()), l_pkey, this.session);
                    dbg("inside recordRead i_col_len -> " + l_col_len);
                    dbg("inside recordRead l_position -> " + l_position);
                } else {
                    l_position = -1;
                }
                if (l_position != -1) {
                    

                    copy = ByteBuffer.allocate(l_col_len + 1 + String.valueOf(ims.getTableMetaData(p_FileType, p_TableName, session).getI_Tableid()).length()
                            + ims.getTableMetaData(p_FileType, p_TableName, session).getI_ColumnCollection().size());
                    dbg("inside recordRead buffer capacity -> " + copy.capacity());
                    //String p_record_to_be_written =null;
                    //Cohesive1_Dev_2 starts here
                    //String converted=dbdi.getIibd_file_util().randomRead( p_FileName, p_TableName, copy.capacity(), l_position);
                     String converted;
                     try{
                     if(lock.isSameSessionLock(p_FileName, session)){
                    
                      converted=dbSession.getIibd_file_util().randomRead( l_temp_path, p_TableName, copy.capacity(), l_position,session,dbdi);
                    
                     }
                    else{
                    converted=dbSession.getIibd_file_util().randomRead( p_FileName, p_TableName, copy.capacity(), l_position,session,dbdi);
                     }
                   }catch(FileNotFoundException ex){
                      StringBuffer single_error_code = new StringBuffer();
                      single_error_code = single_error_code.append("DB_VAL_000");
                      errorhandler.setSingle_err_code(single_error_code);
                      errorhandler.log_error();
                      dbg("file doesn't exit");
                     //throw new DBValidationException("DB_VAL_011");
                     throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }

                          /*  Path file = Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_FileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"));

                    fc = (FileChannel.open(file, READ, WRITE));
                    fc.position(l_position);
                    
                    

                    do {

                        l_nread = fc.read(copy);
                    } while (l_nread != -1 && copy.hasRemaining());
                    //dbg("inside DBreadservice->recordread->"+copy);
                    String converted = new String(copy.array(), Charset.forName(encoding));

                    dbg("inside recordRead converted ->" + converted);*/

                    converted = converted.substring(1 + String.valueOf(ims.getTableMetaData(p_FileType, p_TableName, session).getI_Tableid()).length());
                    l_Column_Value = converted.split("~");

                } else {

                    //MetaDataService mds = new MetaDataService();
                    l_Table_Id = mds.getTableMetaData(p_FileType, p_TableName, this.session).getI_Tableid();
                    dbg("in dbread service->record read->l_Table_Id -->" + l_Table_Id);
                    Map<String,String> l_dummy = new HashMap();
                    //Cohesive1_Dev_2 starts here
                    //PositionAndRecord par = dbdi.getIibd_file_util().sequentialRead(p_FileName, l_Table_Id, p_primary_key,l_dummy,dbdi);
                    
                    PositionAndRecord par;
                    try{
                    if(lock.isSameSessionLock(p_FileName, session)){
                    
                      par = dbSession.getIibd_file_util().sequentialRead(l_temp_path, l_Table_Id, p_primary_key,l_dummy,session,dbdi);
                    
                     }
                    else{
                    par = dbSession.getIibd_file_util().sequentialRead(p_FileName, l_Table_Id, p_primary_key,l_dummy,session,dbdi);
                     }
                     }catch(FileNotFoundException ex){
                      StringBuffer single_error_code = new StringBuffer();
                      single_error_code = single_error_code.append("DB_VAL_000");
                      errorhandler.setSingle_err_code(single_error_code);
                      errorhandler.log_error();
                      dbg("file doesn't exit");
                     //throw new DBValidationException("DB_VAL_011");
                     throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }
                    //Cohesive1_Dev_2 ends here










                    //dbg(par.getI_records().get(0));
                    l_Column_Values = par.getI_records().get(0).split("~");
                    l_Column_Value = new String[l_Column_Values.length - 1];
                    int array_index = 0;
                    while (array_index < l_Column_Values.length - 1) {
                        l_Column_Value[array_index] = l_Column_Values[array_index + 1];

                        array_index++;
                    }

                    //l_records.clear();
                    //dbcs.start();
                    /* l_File_Content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_FileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
                    l_File_Content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));
                    while (l_File_Content.hasNext()) {
                        // l_value = new ArrayList();
                        // l_value.clear();
                        String samp = l_File_Content.next();
                        if (samp.contains("~")) {
                            l_Column_Values = samp.split(dbcs.getI_db_properties().getProperty("COLUMN_DELIMITER"));

                            //dbg("l_Column_Values[0] -->" + l_Column_Values[0]);
                            if (String.valueOf(l_Table_Id).equals(l_Column_Values[0])) {
                                // dbg("readTable ->" + samp);
                                dbg("in dbread service -> l_PK" + l_PK);
                                l_key = getPrimaryKey(p_FileType, l_Table_Id, l_Column_Values);
                                dbg("in dbread service -> l_key" + l_key);
                                if ((l_key.trim()).equals(l_PK.trim())) {
                                    //dbg("in dbread service->inside read table->l_key" + l_key);
                                    dbg("in DBRead service->record read->dummy");
                                    l_value_array_index = 1;
                                    // dbg("in DBRead service->record read->l_Column_Values[1]"+l_Column_Values[1]);
                                    i_Column_Values = new String[l_Column_Values.length - 1];
                                    while ((l_Column_Values.length - 1) >= l_value_array_index) {

                                        i_Column_Values[(l_value_array_index - 1)] = l_Column_Values[l_value_array_index];
                                        // System.out.println(tokens);
                                        dbg("in dbread service->l_Column_Values[l_value_array_index]" + l_Column_Values[l_value_array_index]);
                                        l_value_array_index++;

                                    }

                                    // dbg("in dbread service->record read->"+i_Column_Values[0]);
                                }

                            }
                        }
                    }*/
                    //    Map<String, ArrayList<String>> l_records = readTable(p_FileName, p_FileType, p_TableName);
                    // dbg("l_records.size()" + l_records.size());

                    /* i=0;
                for (Map.Entry<String,ArrayList<String>> entry : l_records.entrySet())
                    
             if(triming(entry.getKey()).equals(l_pkey)){
                 
                ArrayList<String> vc=entry.getValue();
                              while (i < vc.size()) {
                            l_Column_Values[i] = vc.get(i);
                            i++;
                        }
             }*/
 /*if (l_records.size() != 0) {
                        i = 0;

                        l_records.forEach((String kc, ArrayList<String> vc) -> {
                            dbg("inside dbreadservice->vc size" + vc.get(0));
                            dbg("inside dbreadservice->l_pkey value" + l_pkey);
                            //dbg("inside dbreadservice->triming(i_dummy)"+triming(kc));
                            //i_dummy=kc;
                            try {
                                if (triming(kc).equals(l_pkey)) {
                                    //dbg("inside dbreadservice->kc value"+kc);
                                    // dbg("vc.size()"+vc.size());
                                    /* while (i < vc.size()) {
                            l_Column_Values[i] = vc.get(i+1);
                            dbg("l_Column_Values[i]  vc.get(i)->"+l_Column_Values[i]+ vc.get(i));
                            i++;
                        }
                                    //return;
                                    i_Column_Values = new String[vc.size()];
                                    Iterator<String> iterator = vc.iterator();
                                    while (iterator.hasNext()) {
                                        //dbg("inside dbreadservice->i_Column_Values[i] " );
                                        i_Column_Values[i] = iterator.next();
                                        //dbg("inside dbreadservice->i_Column_Values[i] "+i_Column_Values[i] );
                                        i++;
                                    }
                                }
                            } catch (DBProcessingException ex) {
                                dbg(ex);

                            }
                        });

                        //dbg("i_Column_Values"+i_Column_Values);  
                    } else {
                        throw new DBValidationException("DB_VAL_011");

                    }*/
                }
                if (l_Column_Value.length == 0) {
                    throw new DBValidationException("DB_VAL_011");
                }
            }

        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
        } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedCharsetException" + ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalCharsetNameException" + ex.toString());
        }  catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("InvalidPathException" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
                //if (l_File_Content != null) {
                //   l_File_Content.close();
                // }
            } catch (IOException ex) {
                dbg(ex);
                throw new DBProcessingException("IOException" + ex.toString());
            }
            if(l_session_created_now)
            {    
            session.clearSessionObject();
            dbSession.clearSessionObject();
            }
        }
        String[] trimmedValues=new String[l_Column_Value.length];
        
        for(int i=0;i<l_Column_Value.length;i++){
            
            trimmedValues[i]=l_Column_Value[i];
        }
        
        
//        return l_Column_Value;
       return trimmedValues;  

    }
    
   public  String[] recordRead(CohesiveSession session,String p_FileName,String p_FileType,String p_TableName, String... p_primary_key) throws DBValidationException, DBProcessingException{
        CohesiveSession tempSession = this.session;
        String[] l_Column_Value = {};
        try{
            this.session=session;
            l_Column_Value=recordRead(p_FileName,p_FileType,p_TableName,p_primary_key);
            
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
        } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedCharsetException" + ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalCharsetNameException" + ex.toString());
        }  catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("InvalidPathException" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }finally{
           this.session=tempSession;
        }
       return l_Column_Value;
    }
    
    
    
    
    
/*This function reads a file sequentially and returns records which are containing the filter parameter in their column pos
        */
    public ArrayList<String[]> recordReadWithFilter(String p_FileName, String p_FileType, String p_TableName, Map<String, String> p_filter) throws DBValidationException, DBProcessingException {
         ArrayList<String[]> l_columns_of_filtered_records = new ArrayList<String[]>();
        try {
            
            session.createSessionObject();
            dbSession.createDBsession(session);
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileNameValidation(p_FileName, errorhandler,session,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.tableNameValidation(p_FileType, p_TableName, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            Iterator iterator = p_filter.keySet().iterator();
            if (iterator.hasNext()) {
                if (!dbv.columnIDValidation(p_FileType, p_TableName, Integer.parseInt((String) iterator.next()), errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }
            }

            iterator = p_filter.keySet().iterator();
            Iterator iterator1 = p_filter.values().iterator();
            if (iterator.hasNext() && iterator1.hasNext()) {
                if (!dbv.columnDataTypeValidation(p_TableName, Integer.parseInt((String) iterator.next()), (String) iterator1.next(), errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }
            }

            iterator = p_filter.keySet().iterator();
            iterator1 = p_filter.values().iterator();
            if (iterator.hasNext() && iterator1.hasNext()) {
                if (!dbv.columnLengthValidation(p_TableName, (String) iterator1.next(), Integer.parseInt((String) iterator.next()), errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                }
            }
            
            if(!l_validation_status){
                throw new DBProcessingException(errorhandler.getSession_error_code().toString());                
            }
           IMetaDataService mds=dbdi.getMetadataservice();
           int l_Table_Id=mds.getTableMetaData(p_FileType, p_TableName, session).getI_Tableid();
           String[] l_primary_key={};
           String[] l_column_values;
           String l_filter_from_parameter=null;
           iterator1 = p_filter.values().iterator();
           l_filter_from_parameter =(String) iterator1.next();
           while(iterator1.hasNext()){
           l_filter_from_parameter=l_filter_from_parameter.concat("~").concat((String)iterator1.next());
           }
           
           dbg("in DBReadService->recordReadWithFilter->p_FileName"+p_FileName);
           dbg("in DBReadService->recordReadWithFilter->p_FileType"+p_FileType);
           dbg("in DBReadService->recordReadWithFilter->p_TableName"+p_TableName);
           dbg("in DBReadService->recordReadWithFilter->l_Table_Id"+l_Table_Id);
           Map<String,String> l_dummy1 = new HashMap();
                    //Cohesive1_Dev_2 stars here
                    //PositionAndRecord par =dbdi.getIibd_file_util().sequentialRead(p_FileName, l_Table_Id, l_primary_key,l_dummy1,dbdi);  
                    ILockService lock=dbdi.getLockService();
                    //IDBTransactionService dbts=dbdi.getDBTransactionService();
                    String l_temp_path=dbSession.getIibd_file_util().getTempPath(p_FileName);
                    PositionAndRecord par;
                    try{
                    if(lock.isSameSessionLock(p_FileName, session)){
                    
                      par =dbSession.getIibd_file_util().sequentialRead(l_temp_path, l_Table_Id, l_primary_key,l_dummy1,session,dbdi);  
                    
                     }
                    else{
                    par =dbSession.getIibd_file_util().sequentialRead(p_FileName, l_Table_Id, l_primary_key,l_dummy1,session,dbdi);  
                     }
                   }catch(FileNotFoundException ex){
                    StringBuffer single_error_code = new StringBuffer();
                    single_error_code = single_error_code.append("DB_VAL_000");
                    errorhandler.setSingle_err_code(single_error_code);
                    errorhandler.log_error();
                    dbg("file doesn't exit");
                //throw new DBValidationException("DB_VAL_011");
                throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                 }

                    //Cohesive1_Dev_2 ends here
           

          










           // dbg("in DBReadService->recordReadWithFilter->after sequential read");
           Iterator iterator2 = par.getI_records().iterator();
           while(iterator2.hasNext()){
               String l_dummy = (String)iterator2.next();
               l_column_values=l_dummy.split("~");
               iterator = p_filter.keySet().iterator();
               String l_filter_from_records = l_column_values[Integer.parseInt((String)iterator.next())].trim();
               while(iterator.hasNext()){
                   l_filter_from_records=l_filter_from_records.trim().concat("~").concat(l_column_values[Integer.parseInt((String)iterator.next())].trim());
               }
               dbg("in DBReadService->recordReadWithFilter->l_filter_from_parameter->l_filter_from_records"+l_filter_from_parameter+ "    "+l_filter_from_records);
               if(l_filter_from_parameter.equals(l_filter_from_records)){
                   String[] l_columns=new String[l_column_values.length-1];
                   int i=0;
                   while(i<l_columns.length){
                       l_columns[i]=l_column_values[i+1];
                       i++;
                   }
                   l_columns_of_filtered_records.add(l_columns);
               }
               
           }

        }catch(DBValidationException ex){
            throw ex;
        }
        catch(Exception ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }
        dbg("size"+l_columns_of_filtered_records.size());
        
        return l_columns_of_filtered_records;
    }

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

    private boolean fileExistence(String p_file_name) throws DBProcessingException {
        boolean l_existence = false;
        try {

            IDBCoreService dbcs = dbdi.getDbcoreservice(); //EJB Integration change

            //dbg("In DBReadservice ->file existence"+Files.exists(Paths.get(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH")+p_file_name+dbcs.getI_db_properties().getProperty("FILE_EXTENSION")))); 
            l_existence = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + p_file_name + session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("SecurityException" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("InvalidPathException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        return l_existence;
    }

    private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
}
