/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.util;

import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.DBFile;
import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.io.IPhysicalIOService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.db.waitmonitor.DBWait;
import com.ibd.cohesive.db.waitmonitor.IDBWaitBuffer;
import com.ibd.cohesive.util.Email;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.PatternSyntaxException;
import javax.naming.NamingException;



/**
 *
 * @author DELL
 */
 /*Changed by :Isac
  Changed-on:1-11-2018
  Change-Reason:Testing
  Search Tag: Cohesive1_Dev_3
*/
public class IBDFileUtil implements IIBDFileUtil {

    //DBDependencyInjection dbdi;
    // long i_length_of_the_record = 0L;
    Debug debug; // Instance member to handle Helper class 
//    ErrorHandler errorhandler;// Instance member to handle Helper class 
    IBDProperties i_db_properties;// Instance member to handle Helper class 

    public IBDFileUtil() throws NamingException {
        //dbdi = new DBDependencyInjection();
    }

    public IBDProperties getI_db_properties() {
        return i_db_properties;
    }

    public void setI_db_properties(IBDProperties i_db_properties) {
        this.i_db_properties = i_db_properties;
    }

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

//    public ErrorHandler getErrorhandler() {
//        return errorhandler;
//    }
//
//    public void setErrorhandler(ErrorHandler errorhandler) {
//        this.errorhandler = errorhandler;
//    }

// public static void main(String args[]) throws FileNotFoundException{
/*in this read method tableid must be sent as zero if call comes from read full file method of DBReadService.if p_filter_column is not empty then we haveto return  position of each record that has the filter column values in their respective position.i_position_of_filtered_records map's value is set as position*/
    public PositionAndRecord sequentialRead(String p_file_name, int p_table_id, String[] p_pkey, Map<String, String> p_filter_column, CohesiveSession session,DBDependencyInjection dbdi) throws DBValidationException, DBProcessingException,FileNotFoundException {
        PositionAndRecord par = new PositionAndRecord();
         Scanner l_file_content = null;
        try {
            //  p_dbdi.createSessionObject();
            //ErrorHandler errorhandler = p_dbdi.getErrorhandler();
            //DBValidation dbv = p_dbdi.getDbv();
            //boolean l_validation_status = true;
           
            //IDBCoreService dbcs = DBDependencyInjection.getDbcoreservice();
            long l_position = 0L;
            String[] l_column_values;
            String[] l_column;
            int array_index = 0;
            String l_PK;
            ArrayList<String> l_records = new ArrayList<>();

            String l_pkey;
            /*   if(!dbv.specialCharacterValidation(p_file_name, errorhandler)){
                l_validation_status=false;
                errorhandler.log_error();
            }
            if(!dbv.specialCharacterValidation(p_table_id, errorhandler)){
                l_validation_status=false;
                errorhandler.log_error(); 
            }
            for(int i=0;i<p_pkey.length;i++){
                if(!dbv.specialCharacterValidation(p_pkey[i], errorhandler)){
                    l_validation_status=false;
                    errorhandler.log_error();
                }
            }
            if(!dbv.fileNameValidation(p_file_name, errorhandler)){
                l_validation_status=false;
                errorhandler.log_error();
            }
            if(!dbv.tableIDValidation(p_table_id, errorhandler)){
                
              l_validation_status=false;
                errorhandler.log_error();
                if(p_table_id==0){//this is because we haveto send tableid value as zero from readfullfile method of dbreadservice
                    l_validation_status=true;
                }
            }
            /*for(int i=0;i<p_pkey.length;i++){
                if(!dbv.columnValueValidation(p_pkey[i], errorhandler)){
                    l_validation_status=false;
                    errorhandler.log_error();
                }
            }
            if(!l_validation_status){
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
             */ 
            dbg("inside sequential read ->p_file_name"+p_file_name);
             dbg("inside sequential read ->p_table_id"+p_table_id);
             for(String s:p_pkey){
                  dbg("p_pkeyyy"+s);
             }
//             Iterator iterator3=p_filter_column.keySet().iterator();
//            Iterator iterator2=p_filter_column.values().iterator(); 
//            while(iterator3.hasNext()&&iterator2.hasNext()){
//                dbg("iterator3"+iterator3.next().toString());
//                dbg("iterator2"+iterator2.next().toString());
//            }
            
            
            
            
            
            
            
            
            dbg("in dbtransaction servicee p_file_name"+p_file_name);
            Map<String, String> l_position_of_filtered_records = new HashMap();
            l_file_content = new Scanner(new BufferedReader(new FileReader(i_db_properties.getProperty("DATABASE_HOME_PATH") + p_file_name + i_db_properties.getProperty("FILE_EXTENSION"))));
            l_file_content.useDelimiter(i_db_properties.getProperty("RECORD_DELIMITER"));

            while (l_file_content.hasNext()) {
                dbg("in IBDFileUtil->sequentialRead->insidewhile");

                String samp = l_file_content.next();
                dbg("samp"+samp);
                if (samp.contains("~")) {
                    l_column_values = samp.split(i_db_properties.getProperty("COLUMN_DELIMITER"));
                    dbg("in IBDFileUtil->sequentialRead->l_column_values[0]" + l_column_values[0]);
                    if (p_filter_column==null||p_filter_column.isEmpty()) {
                        if (p_pkey.length == 0 && p_table_id == 0) {
                            l_records.add(samp);
                            par.setI_position(-1);
                        } else if (String.valueOf(p_table_id).equals(l_column_values[0])) {
                            dbg("in IBDFileUtil->sequentialRead->inside IF");

                            if (p_pkey.length == 0) {
                                l_records.add(samp);
                                par.setI_position(-1);
                                //par.setI_records(l_records);
                                dbg("in IBDFileUtil->sequentialRead->p_pkey.length" + p_pkey.length);

                            } else {
                                l_column = new String[l_column_values.length - 1];
                                array_index = 0;
                                while (array_index < l_column_values.length - 1) {
                                    l_column[array_index] = l_column_values[array_index + 1];
                                    dbg("in IBDFileUtil->sequentialRead->l_column" + l_column[array_index]);
                                    array_index++;

                                }
                                //dbg("in dbtransaction service->l_column" + l_pkey);
                                //dbg("in dbtransaction service->l_table_id"+l_table_id);
                                //dbg("in dbtransaction service->l_PK"+getPrimaryKey(Integer.parseInt(l_table_id), l_column));
                                dbg("in dbtransaction servicee p_table_id"+p_table_id);
                                l_PK = getPrimaryKey(p_table_id, dbdi,session, l_column);// This will get the primary key values from column values list l_column
                                l_PK = triming(l_PK);
                                l_pkey = formingPrimaryKey(p_pkey);// This will convert the Staring array variable p_pkey into ~ seperated string values
                                dbg("in IBDFileUtil->sequentialRead->l_pkey" + l_pkey);
                                dbg("in IBDFileUtil->sequentialRead->l_PK" + l_PK);

                                //l_pkey = getPrimaryKey(Integer.parseInt(l_table_id), p_record);
                                //Cohesive1_Dev_3 starts
                                if(l_PK==null){
                                    l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;
                                }else{
                                    if (l_PK.equals(l_pkey)) {
                                    dbg("inin IBDFileUtil->sequentialRead->l_position" + l_position);
                                    par.setI_position(l_position);
                                    l_records.add(samp);
                                    par.setI_records(l_records);

                                } else {
                                    l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;//2 is for # and tableid
                                }
                                    
                                }
//                                if (l_PK.equals(l_pkey)) {
//                                    dbg("inin IBDFileUtil->sequentialRead->l_position" + l_position);
//                                    par.setI_position(l_position);
//                                    l_records.add(samp);
//                                    par.setI_records(l_records);
//
//                                } else {
//                                    l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0])) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;//2 is for # and tableid
//                                }
                                //Cohesive1_Dev_3 ends
                            }
                            /*if (p_pkey.length == 0) {
                            par.setI_records(l_records);

                            par.setI_position(-1);
                        }*/
                        } else {
                            dbg("in IBDFileUtil->sequentialRead->length of the record" + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi));
                            l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;//1 is for # 
                        }
                    } else {
                        Iterator iterator = p_filter_column.values().iterator();
                        String l_filter_from_parameter = (String) iterator.next();
                        String l_filter_from_records = null;
                        while (iterator.hasNext()) {
                            l_filter_from_parameter = l_filter_from_parameter.concat("~").concat((String) iterator.next());
                        }
                        // Iterator iterator2 = par.getI_records().iterator();
                        // while (iterator2.hasNext()) {
                        //  String l_samp = (String) iterator2.next();
                        //l_column_values = samp.split("~");
                        Iterator iterator1 = p_filter_column.keySet().iterator();
                        l_filter_from_records = l_column_values[Integer.parseInt((String) iterator1.next())].trim();
                        String l_column_id = null;
                        while (iterator1.hasNext()) {
                            l_column_id = (String) iterator1.next();
                            l_filter_from_records = l_filter_from_records.trim().concat("~").concat(l_column_values[Integer.parseInt(l_column_id)].trim());

                        }
                        dbg("in IBDFileUtil->sequentialRead->l_filter_from_records" + l_filter_from_records);
                        dbg("in IBDFileUtil->sequentialRead->l_filter_from_parameter" + l_filter_from_parameter);

                        if (l_filter_from_parameter.equals(l_filter_from_records)) {
                            dbg("in IBDFileUtil->sequentialRead->l_column_values[0]->" + l_column_values[0]);
                            dbg("in IBDFileUtil->sequentialRead->l_position->" + l_position);
                            dbg("in IBDFileUtil->sequentialRead->l_column_id" + l_column_id);
                            l_position_of_filtered_records.put(l_column_values[0].concat(String.valueOf(l_position)), String.valueOf(l_position));

                            par.setI_position(-1);
                            par.setI_records(null);
                            par.setI_position_of_filtered_records(l_position_of_filtered_records);
                            l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;//1 is for #  
                        } else {
                            dbg("in IBDFileUtil->sequentialRead->l_position->->" + l_position);
                            l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;//1 is for #  
                        }
                    }
                } else {
                    l_position = l_position + lengthOfTheRecord(Integer.parseInt(samp.trim()),dbdi) + String.valueOf(Integer.parseInt(samp.trim())).length() + 1;//1 is for # 
                }
            }
            /*if(p_pkey.length == 0&&p_table_id==0){
            
            par.setI_position(-1);
            }*/

            
            
            
            
            
            par.setI_records(l_records);

            
//            dbg("in IBDFileUtil->sequentialRead->par.getI_records().get(0)"+par.getI_records().get(0));
            return par;
        }catch(FileNotFoundException ex){
            dbg(ex);
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("EXCEPTION" + ex.toString());
        } finally {
            if(l_file_content!=null){
                l_file_content.close();
            }
        }

    }
public Map<String, Map<String,DBRecord>> sequentialRead(String p_file_name,CohesiveSession session,DBDependencyInjection dbdi) throws DBValidationException, DBProcessingException,FileNotFoundException {
//        PositionAndRecord par = new PositionAndRecord();
          Map<String, Map<String,DBRecord>>l_map=new HashMap();
         Scanner l_file_content = null;
        try {
            
            long l_position = 0L;
            String[] l_column_values;
           
            ArrayList<String> l_record;
            int array_index = 0;
            String l_PK;
            String[] l_recordStringArray;
            Map<String,String>l_tableMap=new HashMap();//key-tableId  value-tableName
            Map<String,DBRecord>l_tabledummy=new HashMap();
            DBRecord l_dbRecord;
            String l_pkey;
            
            dbg("inside sequential read ->p_file_name"+p_file_name);
            ErrorHandler errorHandler=session.getErrorhandler();
            dbg("in dbtransaction servicee p_file_name"+p_file_name);
            l_file_content = new Scanner(new BufferedReader(new FileReader(i_db_properties.getProperty("DATABASE_HOME_PATH") + p_file_name + i_db_properties.getProperty("FILE_EXTENSION"))));
            l_file_content.useDelimiter(i_db_properties.getProperty("RECORD_DELIMITER"));

            while (l_file_content.hasNext()) {
                dbg("in IBDFileUtil->sequentialRead->insidewhile");

                String samp = l_file_content.next();
                if (samp.contains("~")) {
                    l_column_values = samp.split(i_db_properties.getProperty("COLUMN_DELIMITER"));
                    dbg("in IBDFileUtil->sequentialRead->tableid" + l_column_values[0]);
                    String l_tableName;
                    if(!(l_tableMap.containsKey(l_column_values[0]))){
                    IMetaDataService mds=dbdi.getMetadataservice();
                    l_tableName=mds.getTableMetaData(Integer.parseInt(l_column_values[0]), session).getI_TableName();
                    l_tableMap.put(l_column_values[0], l_tableName);
                    }else{
                    l_tableName=  l_tableMap.get(l_column_values[0]);
                    }
                                l_record = new ArrayList();
                                l_recordStringArray=new String[l_column_values.length - 1];
                                array_index = 0;
                                while (array_index < l_column_values.length - 1) {
                                    l_record.add(l_column_values[array_index + 1]);
                                    l_recordStringArray[array_index]=l_column_values[array_index + 1];
                                    dbg("in IBDFileUtil->sequentialRead->l_column" + l_column_values[array_index+1]);
                                    array_index++;

                                }
                                dbg("in dbtransaction servicee p_table_id"+l_column_values[0]);
                                l_PK = getPrimaryKey(Integer.parseInt(l_column_values[0]), dbdi,session, l_recordStringArray);// This will get the primary key values from column values list l_column
                                l_PK = triming(l_PK);
                             
                                dbg("in IBDFileUtil->sequentialRead->l_PK" + l_PK);

                                //Cohesive1_Dev_3 starts
                                if(l_PK==null){
                                    l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;
                                }else{
                                    dbg("inin IBDFileUtil->sequentialRead->l_position" + l_position);
                                    ArrayList<String>trimmedRecords=new ArrayList();
                                    for(int i=0;i<l_record.size();i++){
                                        
                                       trimmedRecords.add(l_record.get(i).trim()) ;
                                        
                                    }
                                    
                                    
                                    
//                                    l_dbRecord=new DBRecord(l_position,l_record);
                                      l_dbRecord=new DBRecord(l_position,trimmedRecords);

                                     if(l_map.containsKey(l_tableName)){
                                         
                                         if(l_map.get(l_tableName).containsKey(l_PK)){
                                             StringBuffer single_error_code = new StringBuffer();
                                             single_error_code = single_error_code.append("DB_VAL_015".concat("," + l_tableName).concat("," + l_PK));
                                             errorHandler.setSingle_err_code(single_error_code);
                                             errorHandler.log_error();
                                             throw new DBValidationException(errorHandler.getSession_error_code().toString()); 
                                             
                                         }else{
                                             l_map.get(l_tableName).putIfAbsent(l_PK, l_dbRecord);
                                             
                                         }
                                         
                                     }else{
                                         
                                         l_map.put(l_tableName, new HashMap());
                                         l_map.get(l_tableName).putIfAbsent(l_PK, l_dbRecord);
                                     }
                                     
                                     
                                 l_position = l_position + lengthOfTheRecord(Integer.parseInt(l_column_values[0]),dbdi) + String.valueOf(Integer.parseInt(l_column_values[0])).length() + 1;    
                                }
                   }
                                else {
                    l_position = l_position + lengthOfTheRecord(Integer.parseInt(samp.trim()),dbdi) + String.valueOf(Integer.parseInt(samp.trim())).length() + 1;//1 is for # 
                  }
                
            }

//            dbg("in IBDFileUtil->sequentialRead->par.getI_records().get(0)"+par.getI_records().get(0));
            return l_map;
        }catch(FileNotFoundException ex){
//            dbg(ex);
            throw ex;
        }catch(DBValidationException ex){
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("EXCEPTION" + ex.toString());
        } finally {
            if(l_file_content!=null){
                l_file_content.close();
            }
        }

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

    private long lengthOfTheRecord(int p_tableid,DBDependencyInjection dbdi) throws NamingException {
        long l_length_of_the_record = 0;
        IDBCoreService dbcs = dbdi.getDbcoreservice();
        Iterator iterator1 = dbcs.getG_dbMetaData().keySet().iterator();
        Iterator iterator2 = dbcs.getG_dbMetaData().values().iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
           // String l_outer_map_key = (String) iterator1.next();
            DBFile l_dbfile = (DBFile) iterator2.next();
            Iterator iterator3 = l_dbfile.getI_TableCollection().keySet().iterator();
            Iterator iterator4 = l_dbfile.getI_TableCollection().values().iterator();
            while (iterator3.hasNext() && iterator4.hasNext()) {
               // String l_inner_map_key = (String) iterator3.next();
                DBTable l_dbtable = (DBTable) iterator4.next();
                if (l_dbtable.getI_Tableid() == p_tableid) {
                    Iterator iterator5 = l_dbtable.getI_ColumnCollection().keySet().iterator();
                    Iterator iterator6 = l_dbtable.getI_ColumnCollection().values().iterator();
                    while (iterator5.hasNext() && iterator6.hasNext()) {
                       // String l_inner_most_map_key = (String) iterator5.next();
                        DBColumn l_dbcolumn = (DBColumn) iterator6.next();
                        l_length_of_the_record = l_length_of_the_record + l_dbcolumn.getI_ColumnLength();
                    }
                    l_length_of_the_record = l_length_of_the_record + l_dbtable.getI_ColumnCollection().size();
                }
            }

        }
        /*dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
            vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                if (v.getI_Tableid() == p_tableid) {
                    v.getI_ColumnCollection().forEach((String l, DBColumn m) -> {
                        i_length_of_the_record = i_length_of_the_record + m.getI_ColumnLength();
                    });
                    i_length_of_the_record += v.getI_ColumnCollection().size();//v.getI_ColumnCollection().size() indicates no of tilda separators
                }
            });
        });*/
        //i_length_of_the_record += 1;//1 is for #
        return l_length_of_the_record;
    
       }

    public String formingPrimaryKey(String[] p_pkey) {
        int l_length_of_the_p_pkey = p_pkey.length;
        int array_index_of_p_pkey = 0;
        String l_pkey = null;
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
        return l_pkey;
    }

    private String getPrimaryKey(int p_tableID, DBDependencyInjection dbdi,CohesiveSession session, String... p_record_values) throws DBValidationException, DBProcessingException {
        String l_primaryKey = null;
        try {
            IMetaDataService mds = dbdi.getMetadataservice();
            DBTable dbtable = mds.getTableMetaData(p_tableID, session);
            String l_pk = dbtable.getI_Pkey();
            
            String[] l_pk_ids = l_pk.split("~");
            l_primaryKey = p_record_values[Integer.parseInt(l_pk_ids[0]) - 1].trim();
            dbg("in IBDFileutil->getPrimaryKey->" + l_primaryKey);
            //dbg("p_record_values[Integer.parseInt(l_pk_ids[0])-1]" + p_record_values[Integer.parseInt(l_pk_ids[0]) - 1] + "is");
            int counter = l_pk_ids.length - 1;//already one value is assigned to l_primaryKey
            int i = 1;
            while (counter > 0) {
                l_primaryKey = l_primaryKey.concat("~").concat(p_record_values[Integer.parseInt(l_pk_ids[i]) - 1].trim());
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
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }

        return l_primaryKey;
    }

    public String randomRead(String p_file_name, String p_table_name, int p_capacity_of_buffer, long p_position,CohesiveSession session,DBDependencyInjection dbdi) throws DBValidationException, DBProcessingException,FileNotFoundException {
        String converted = null;
        FileChannel fc = null;
        try {
            int l_nread;
            ByteBuffer copy;
            IDBCoreService dbcs = dbdi.getDbcoreservice();//getIibd_file_util
            Path file = Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + p_file_name + session.getCohesiveproperties().getProperty("FILE_EXTENSION"));
            fc = FileChannel.open(file, CREATE, READ, WRITE);
            fc.position(p_position);
            String encoding = System.getProperty("file.encoding");
            copy = ByteBuffer.allocate(p_capacity_of_buffer);
            do {

                l_nread = fc.read(copy);
            } while (l_nread != -1 && copy.hasRemaining());

            converted = new String(copy.array(), Charset.forName(encoding));
        }catch(FileNotFoundException ex){
            dbg(ex);
            throw ex;
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalCharsetNameException" + ex.toString());

        } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedCharsetException" + ex.toString());

        } catch (ReadOnlyBufferException ex) {
            dbg(ex);
            throw new DBProcessingException("ReadOnlyBufferException" + ex.toString());

        } catch (ClosedByInterruptException ex) {
            dbg(ex);
            throw new DBProcessingException("ClosedByInterruptException" + ex.toString());

        } catch (AsynchronousCloseException ex) {
            dbg(ex);
            throw new DBProcessingException("AsynchronousCloseException" + ex.toString());

        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("SecurityException" + ex.toString());

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ClosedChannelException ex) {
            dbg(ex);
            throw new DBProcessingException("ClosedChannelException" + ex.toString());

        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("InvalidPathException" + ex.toString());

        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());

        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());

        } catch (IOException ex) {
            dbg(ex);
            throw new DBProcessingException("IOException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            try {
                fc.close();
            } catch (IOException ex) {
                throw new DBProcessingException(ex.toString());
            }
        }
        return converted;
    }

    public void randomWrite(String p_file_name, long p_position, String p_record_to_be_written) throws DBValidationException, DBProcessingException {
        FileChannel fc = null;
        try {
            Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + p_file_name + i_db_properties.getProperty("FILE_EXTENSION"));
            fc = FileChannel.open(file, READ, WRITE);
            fc.position(p_position);
         
            
            fc.write(ByteBuffer.wrap(p_record_to_be_written.getBytes(Charset.forName("UTF-8"))));
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }
        finally {
            try {
                fc.close();
            } catch (IOException ex) {
                throw new DBProcessingException(ex.toString());
            }
        }

    }

    public void sequentialWrite(String p_file_name, String p_record_to_be_written) throws DBValidationException, DBProcessingException {
       FileChannel fc = null;
        try {
                     
            Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + p_file_name + i_db_properties.getProperty("FILE_EXTENSION"));
            
                
                fc = FileChannel.open(file, CREATE, WRITE, APPEND);
                fc.write(ByteBuffer.wrap(p_record_to_be_written.getBytes(Charset.forName("UTF-8"))));
                
                
                
                
            } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally {
            try {
                
                fc.close();
            } catch (IOException ex) {
                throw new DBProcessingException(ex.toString());
            }
        }
    }

    public long getLastPositionOfaFile(String p_file_name) throws DBValidationException, DBProcessingException {
        FileChannel fc = null;
        
        try {
            
            Path file = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH") + p_file_name + i_db_properties.getProperty("FILE_EXTENSION"));
            fc = FileChannel.open(file, CREATE, WRITE, APPEND);
            return fc.position();
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally {
            try {
                fc.close();
            } catch (IOException ex) {
                throw new DBProcessingException(ex.toString());
            }
        }

    }
    public void copyFileToTemp(Path p_file_path,Path p_temp_path)throws DBProcessingException{
       
        try{ 
            dbg("inside copyFileToTemp---->p_file_path"+p_file_path);
            dbg("inside copyFileToTemp---->p_temp_path"+p_temp_path);
        Path l_src_path = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+p_file_path+i_db_properties.getProperty("FILE_EXTENSION"));
        Path l_dest_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+p_temp_path+i_db_properties.getProperty("FILE_EXTENSION"));
        
        if(Files.exists(l_src_path)&&Files.notExists(l_dest_path)){
            dbg("file is copied from actual path to temp path");
           Files.copy(l_src_path, l_dest_path);
        }
        
           dbg("end of copyFileToTemp---->p_file_path");
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
    
    public String getTempPath(String p_file_name)throws DBProcessingException{
       Path l_temp_path;
      
       try{
       dbg("inside get tempPath: p_file_name->"+p_file_name);
       Path l_file_path=Paths.get(p_file_name);
       Path l_parent_path=l_file_path.getParent();
       
      /* if(!Files.exists(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"))){
           Files.createDirectory(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"));
       }*/
      
      if(Files.notExists(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+l_parent_path))){
          Files.createDirectory(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+l_parent_path));
      }
      
      if(!Files.exists(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"))){
           Files.createDirectory(Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"));
           dbg("temp path created");
       }
      
       
//       l_temp_path=Paths.get(l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_file_path.getFileName());
       l_temp_path=Paths.get(l_parent_path+i_db_properties.getProperty("FOLDER_DELIMITER")+"temp"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_file_path.getFileName());
        }catch(FileAlreadyExistsException ex)
            {
            dbg(ex);
            throw new DBProcessingException("FileAlreadyExistsException " + ex.toString()); 
            }catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }catch(IOException ex)
            {
            dbg(ex);
            throw new DBProcessingException("IOException" + ex.toString());  
        }catch(SecurityException  ex){
            dbg(ex);
            throw new DBProcessingException("SecurityException  " + ex.toString()); 
        }catch(InvalidPathException  ex){
            dbg(ex);
            throw new DBProcessingException("InvalidPathException  " + ex.toString()); 
        }catch(IllegalArgumentException ex){
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException exception" + ex.toString());  
       }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
       } 
       
      
       return l_temp_path.toString();
   }

    
    public void deleteTempFile(String p_file_name)throws DBProcessingException{
        
        try{
         dbg("inside deleteTempFile--->fileName"+p_file_name); 
         Path l_temp_file_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+getTempPath(p_file_name)+i_db_properties.getProperty("FILE_EXTENSION"));
//        Path l_temp_file_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+getTempPath(p_file_name)+i_db_properties.getProperty("FOLDER_DELIMITER")+i_db_properties.getProperty("FILE_EXTENSION"));
        dbg("inside deleteTempFile--->l_temp_file_path--->"+l_temp_file_path);
        if(Files.exists(l_temp_file_path)){
            
            Files.delete(l_temp_file_path);
            dbg("file is deleted");
        }
        
        }catch (DirectoryNotEmptyException  ex) {
            dbg(ex);
            throw new DBProcessingException("DirectoryNotEmptyException " + ex.toString());
        }catch (NoSuchFileException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchFileException" + ex.toString());
        }catch(IOException ex)
            {
            dbg(ex);
            throw new DBProcessingException("IOException" + ex.toString());  
        }catch(SecurityException ex)
            {
            dbg(ex);
            throw new DBProcessingException("SecurityException" + ex.toString());  
        }catch(InvalidPathException  ex){
            dbg(ex);
            throw new DBProcessingException("InvalidPathException  " + ex.toString()); 
        }catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        }catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
    }  
        
    }
    
 /*   public JsonObject getJsonObject(String p_request)throws BSProcessingException{
        
        try(InputStream is = new ByteArrayInputStream(p_request.getBytes(Charset.forName("UTF-8")));
            JsonReader jsonReader = Json.createReader(is);)    
                
                {
        
        
        JsonObject jsonObject = jsonReader.readObject();
        
        return  jsonObject;  
        
        }catch(IOException ex){
             
            throw new BSProcessingException("IOException"+ex.toString());
        }
        }
*/
   public static UUID dataToUUID(String... params) {
      StringBuilder builder = new StringBuilder();
      for (String param : params) {
        builder.append(param);
     }
    return UUID.nameUUIDFromBytes(builder.toString().getBytes(StandardCharsets.UTF_8));
}
        
//  public void printTempBuffer(Map<String,Map<String, Map<String,DBRecord>>> bufferMap)throws DBProcessingException {
   public void printTempBuffer(Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> bufferMap)throws DBProcessingException {

      try{
          
          Iterator<String> fileNameIterator= bufferMap.keySet().iterator();
           while(fileNameIterator.hasNext()){
               String fileName=fileNameIterator.next();
                      dbg("File Name;"+fileName);
                    Iterator<String> tableNameIterator=bufferMap.get(fileName).keySet().iterator();
                   while(tableNameIterator.hasNext()) {
                       String tableName=tableNameIterator.next();
                       dbg("    tableName"+tableName);
                       Iterator<String> pkeyIterator=bufferMap.get(fileName).get(tableName).keySet().iterator();
                          while(pkeyIterator.hasNext()){
                              String primaryKey=pkeyIterator.next();
                              dbg("      primaryKey"+primaryKey);
                               DBRecord dbrec=  bufferMap.get(fileName).get(tableName).get(primaryKey);
                              if(dbrec!=null){
                              
                                   dbg("        operation"+dbrec.getOperation());
                                   dbg("        postion"+dbrec.getPosition());
                                   for(String s: dbrec.getRecord()){
                                       dbg("    record values"+s);
                                   }
                               
                              }
                          }
                   }
               
               
           }
          
          
          
          
      }catch (Exception ex) {
           dbg(ex);
           throw new DBProcessingException("Exception" + ex.toString());
        }
      
      
  }
   
      public void printWriteBuffer(Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> bufferMap)throws DBProcessingException {

   
//   public void printWriteBuffer(Map<String,Map<String, Map<String,DBRecord>>> bufferMap)throws DBProcessingException {

      try{
          
          Iterator<String> fileNameIterator= bufferMap.keySet().iterator();
           while(fileNameIterator.hasNext()){
               String fileName=fileNameIterator.next();
                      dbg("File Name;"+fileName);
                    Iterator<String> tableNameIterator=bufferMap.get(fileName).keySet().iterator();
                   while(tableNameIterator.hasNext()) {
                       String tableName=tableNameIterator.next();
                       dbg("    tableName"+tableName);
                       Iterator<String> pkeyIterator=bufferMap.get(fileName).get(tableName).keySet().iterator();
                          while(pkeyIterator.hasNext()){
                              String primaryKey=pkeyIterator.next();
                              dbg("      primaryKey"+primaryKey);
                               DBRecord dbrec=  bufferMap.get(fileName).get(tableName).get(primaryKey);
                               
                               if(dbrec!=null){
                               
                               dbg("        operation"+dbrec.getOperation());
                               dbg("        postion"+dbrec.getPosition());
                               for(String s: dbrec.getRecord()){
                                   dbg("    record values"+s);
                               }
                               }
                          }
                   }
               
               
           }
          
          
          
          
      }catch (Exception ex) {
           dbg(ex);
           throw new DBProcessingException("Exception" + ex.toString());
        }
      
      
  }
   
       public void printDBBuffer( Map<String, Map<String,DBRecord>>tableMap)throws DBProcessingException{
           
           try{
           Iterator<String> tableNameIterator=tableMap.keySet().iterator();
                   while(tableNameIterator.hasNext()) {
                       String tableName=tableNameIterator.next();
                       dbg("    tableName"+tableName);
                       Iterator<String> pkeyIterator=tableMap.get(tableName).keySet().iterator();
                          while(pkeyIterator.hasNext()){
                              String primaryKey=pkeyIterator.next();
                              dbg("      primaryKey"+primaryKey);
                               DBRecord dbrec=  tableMap.get(tableName).get(primaryKey);
                               dbg("        operation"+dbrec.getOperation());
                               dbg("        postion"+dbrec.getPosition());
                               for(String s: dbrec.getRecord()){
                                   dbg("    record values"+s);
                               }
                               
                          }
                   }
           
           }catch (Exception ex) {
           dbg(ex);
           throw new DBProcessingException("Exception" + ex.toString());
        }
           
       }
       
    public String getPKwithoutVersion(String p_fileName,String p_table_name,String p_primaryKey,DBDependencyInjection p_dbdi,CohesiveSession p_session)throws DBProcessingException,DBValidationException{
        try{
            boolean l_versionStatus=false;
            IMetaDataService mds=p_dbdi.getMetadataservice();
            dbg("inside getPKwithoutVersion--->");
            String l_pk_without_version;
            String l_fileType=getFileType(p_fileName);
            Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(l_fileType, p_table_name,p_session).getI_ColumnCollection();
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
            
            if(l_versionStatus){
               
              if(p_primaryKey.contains("~")){  
                
                  l_pk_without_version = p_primaryKey.substring(0, p_primaryKey.lastIndexOf("~"));
              
              }else{
                  
                  l_pk_without_version=  p_primaryKey;
              }
            }else{
              l_pk_without_version=  p_primaryKey;
            }
            
//            if(l_versionStatus){
//                
//              l_pk_without_version = p_primaryKey.substring(0, p_primaryKey.lastIndexOf("~"));
//              
//            }else{
//              l_pk_without_version=  p_primaryKey;
//            }
            dbg("end of getPKwithoutVersion--->l_pk_without_version"+l_pk_without_version);
            return l_pk_without_version;
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
    }   
    
    public String getFileType(String p_fileName)throws DBProcessingException{
       
    try{    
        dbg("inside fileytil--->getFileType--->fileName--->"+p_fileName);
        File file=new File(p_fileName);
            File parentFile=new File(file.getParent());
            String fileType=new File(parentFile.getParent()).getName();
            
            dbg("inside tempSegment--->getFileType--->"+fileType);
            return fileType;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
       
            
    }
    
    public Map<String, Map<String, Map<String, DBRecord>>>cloneBuffer( Map<String, Map<String, Map<String, DBRecord>>> original)throws DBProcessingException {
	
        try{
         dbg("inside clonebuffer");   
        Map<String, Map<String, Map<String, DBRecord>>> copy = new HashMap<>();
        
        Iterator<String> fileNameIterator=original.keySet().iterator();
        
        while(fileNameIterator.hasNext()){
            
            String fileName=fileNameIterator.next();
            Map<String, Map<String, DBRecord>> fileMap=original.get(fileName);
            
            Iterator<String> tableNameNameIterator=fileMap.keySet().iterator();
            
            copy.put(fileName, new HashMap());
            
            while(tableNameNameIterator.hasNext()){
                
                String l_tableName=tableNameNameIterator.next();
                
                Map<String, DBRecord> l_tableMap=original.get(fileName).get(l_tableName);
                
                copy.get(fileName).put(l_tableName,new HashMap());
                
//                copy.get(fileName).get(l_tableName).putAll(l_tableMap);
                
                Iterator<String>keyIterator=l_tableMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    
                    copy.get(fileName).get(l_tableName).put(key, l_tableMap.get(key));
                }
                
                
            }
            
        }
        dbg("end of clone buffer");
//	copy.putAll(original);
	return copy;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
}
    
    public  Map<String, Map<String, DBRecord>>cloneFile( ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>> original)throws DBProcessingException {
	
        try{
            
          Map<String, Map<String, DBRecord>> copy = new HashMap<>();  
        Iterator<String> tableIterator= original.keySet().iterator();
        
        while(tableIterator.hasNext()){
            
            String l_tableName=tableIterator.next();
            dbg("table name in cloning"+l_tableName);
            Map<String, DBRecord> l_primaryKeyMap=original.get(l_tableName);
            
            copy.put(l_tableName,new HashMap());
//            copy.get(l_tableName).putAll(l_primaryKeyMap);
            
            Iterator<String>keyIterator=l_primaryKeyMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    String key =keyIterator.next();
//                     key=keyIterator.next();
                    
                    copy.get(l_tableName).put(key,cloneRecord(l_primaryKeyMap.get(key)));
                }
            dbg("values putted for"+l_tableName);
        }
        
//        Map<String, Map<String, DBRecord>> copy = new HashMap<>();
//	copy.putAll(original);
        
        
//        dbg("printing of copy starts");
//        
//        Iterator<String> tableNameIterator=copy.keySet().iterator();
//                   while(tableNameIterator.hasNext()) {
//                       String tableName=tableNameIterator.next();
//                       dbg("    tableName"+tableName);
//                       Iterator<String> pkeyIterator=copy.get(tableName).keySet().iterator();
//                          while(pkeyIterator.hasNext()){
//                              String primaryKey=pkeyIterator.next();
//                              dbg("      primaryKey"+primaryKey);
//                               DBRecord dbrec=  copy.get(tableName).get(primaryKey);
//                               dbg("        operation"+dbrec.getOperation());
//                               dbg("        postion"+dbrec.getPosition());
//                               for(String s: dbrec.getRecord()){
//                                   dbg("    record values"+s);
//                               }
//                               
//                          }
//                   }
//        dbg("printing of copy ends");
	return copy;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
        
        
        
}
//   public  ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>>cloneConcurrentFile( ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>> original)throws DBProcessingException {
//	
//        try{
//            
//          ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>> copy = new HashMap<>();  
//        Iterator<String> tableIterator= original.keySet().iterator();
//        
//        while(tableIterator.hasNext()){
//            
//            String l_tableName=tableIterator.next();
//            dbg("table name in cloning"+l_tableName);
//            Map<String, DBRecord> l_primaryKeyMap=original.get(l_tableName);
//            
//            copy.put(l_tableName,new HashMap());
////            copy.get(l_tableName).putAll(l_primaryKeyMap);
//            
//            Iterator<String>keyIterator=l_primaryKeyMap.keySet().iterator();
//                
//                while(keyIterator.hasNext()){
//                    String key =keyIterator.next();
////                     key=keyIterator.next();
//                    
//                    copy.get(l_tableName).put(key,cloneRecord(l_primaryKeyMap.get(key)));
//                }
//            dbg("values putted for"+l_tableName);
//        }
//        
////        Map<String, Map<String, DBRecord>> copy = new HashMap<>();
////	copy.putAll(original);
//        
//        
////        dbg("printing of copy starts");
////        
////        Iterator<String> tableNameIterator=copy.keySet().iterator();
////                   while(tableNameIterator.hasNext()) {
////                       String tableName=tableNameIterator.next();
////                       dbg("    tableName"+tableName);
////                       Iterator<String> pkeyIterator=copy.get(tableName).keySet().iterator();
////                          while(pkeyIterator.hasNext()){
////                              String primaryKey=pkeyIterator.next();
////                              dbg("      primaryKey"+primaryKey);
////                               DBRecord dbrec=  copy.get(tableName).get(primaryKey);
////                               dbg("        operation"+dbrec.getOperation());
////                               dbg("        postion"+dbrec.getPosition());
////                               for(String s: dbrec.getRecord()){
////                                   dbg("    record values"+s);
////                               }
////                               
////                          }
////                   }
////        dbg("printing of copy ends");
//	return copy;
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//
//        } 
//        
//        
//        
//} 
    public  Map<String, Map<String, DBRecord>>cloneFile( Map<String, Map<String, DBRecord>> original)throws DBProcessingException {
	
        try{
            
          Map<String, Map<String, DBRecord>> copy = new HashMap<>();  
        Iterator<String> tableIterator= original.keySet().iterator();
        
        while(tableIterator.hasNext()){
            
            String l_tableName=tableIterator.next();
            dbg("table name in cloning"+l_tableName);
            Map<String, DBRecord> l_primaryKeyMap=original.get(l_tableName);
            
            copy.put(l_tableName,new HashMap());
//            copy.get(l_tableName).putAll(l_primaryKeyMap);
       
            Iterator<String>keyIterator=l_primaryKeyMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
//                    String key=new String();
                    
                   String key=keyIterator.next();
                    
                    copy.get(l_tableName).put(key, cloneRecord(l_primaryKeyMap.get(key)));
                }
            
            dbg("values putted for"+l_tableName);
        }
        
//        Map<String, Map<String, DBRecord>> copy = new HashMap<>();
//	copy.putAll(original);
        
        
//        dbg("printing of copy starts");
//        
//        Iterator<String> tableNameIterator=copy.keySet().iterator();
//                   while(tableNameIterator.hasNext()) {
//                       String tableName=tableNameIterator.next();
//                       dbg("    tableName"+tableName);
//                       Iterator<String> pkeyIterator=copy.get(tableName).keySet().iterator();
//                          while(pkeyIterator.hasNext()){
//                              String primaryKey=pkeyIterator.next();
//                              dbg("      primaryKey"+primaryKey);
//                               DBRecord dbrec=  copy.get(tableName).get(primaryKey);
//                               dbg("        operation"+dbrec.getOperation());
//                               dbg("        postion"+dbrec.getPosition());
//                               for(String s: dbrec.getRecord()){
//                                   dbg("    record values"+s);
//                               }
//                               
//                          }
//                   }
//        dbg("printing of copy ends");
	return copy;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
}
    
    public  ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>>createConcurrentFileMap( Map<String, Map<String, DBRecord>> original,String p_fileName,CohesiveSession p_session,String p_mapType)throws DBProcessingException {
	
        try{
            
            
            
          ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>> copy = createConcurrentTableMap(p_fileName,p_session,p_mapType) ;  
        Iterator<String> tableIterator= original.keySet().iterator();
        
        while(tableIterator.hasNext()){
            
            String l_tableName=tableIterator.next();
            dbg("table name in cloning"+l_tableName);
            Map<String, DBRecord> l_primaryKeyMap=original.get(l_tableName);
            
            copy.put(l_tableName,createConcurrentRecordMap(p_session));
    //     copy.get(l_tableName).putAll(l_primaryKeyMap);
 
            Iterator<String>keyIterator=l_primaryKeyMap.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    
                    copy.get(l_tableName).put(key, l_primaryKeyMap.get(key));
                }
            
            dbg("values putted for"+l_tableName);
        }
        
//        Map<String, Map<String, DBRecord>> copy = new HashMap<>();
//	copy.putAll(original);
        
        
//        dbg("printing of copy starts");
//        
//        Iterator<String> tableNameIterator=copy.keySet().iterator();
//                   while(tableNameIterator.hasNext()) {
//                       String tableName=tableNameIterator.next();
//                       dbg("    tableName"+tableName);
//                       Iterator<String> pkeyIterator=copy.get(tableName).keySet().iterator();
//                          while(pkeyIterator.hasNext()){
//                              String primaryKey=pkeyIterator.next();
//                              dbg("      primaryKey"+primaryKey);
//                               DBRecord dbrec=  copy.get(tableName).get(primaryKey);
//                               dbg("        operation"+dbrec.getOperation());
//                               dbg("        postion"+dbrec.getPosition());
//                               for(String s: dbrec.getRecord()){
//                                   dbg("    record values"+s);
//                               }
//                               
//                          }
//                   }
//        dbg("printing of copy ends");
	return copy;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
}
   
 
    public Map<String, DBRecord>cloneTable(Map<String, DBRecord> original)throws DBProcessingException {
	
        try{
        Map<String, DBRecord> copy = new HashMap<>();
//	copy.putAll(original);

        Iterator<String>keyIterator=original.keySet().iterator();
                
                while(keyIterator.hasNext()){
                    
                    String key=keyIterator.next();
                    
                    copy.put(key, cloneRecord(original.get(key)));
                }
	return copy;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
}
    
    public DBRecord cloneRecord(DBRecord original)throws DBProcessingException {
	
        try{
            
          ArrayList<String>copyArrayList=  new ArrayList();
            
            for(int i=0;i<original.getRecord().size();i++){
                
                copyArrayList.add(i, original.getRecord().get(i));
                
            }
            
            
            
        DBRecord copy = new DBRecord(original.getPosition(),copyArrayList,original.getOperation());
//	copy.putAll(original);
	return copy;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
}
    
  public String getCurrentTime()throws DBProcessingException{
      
      try{
     String dateformat=getI_db_properties().getProperty("DATE_TIME_FORMAT");
     SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
     Date date = new Date();
     String l_dateStamp=formatter.format(date);
    
     return l_dateStamp;
      }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } 
     
  }
  
  public void logWaitBuffer(String p_fileName,String p_tablName,String p_primaryKey,String className,String methodName,CohesiveSession session,DBSession dbSession,DBDependencyInjection dbdi,long startTime,int readBufferSize,int tempSegmentSize,int writeBuffersize,int waitBufferSize,boolean isSessionCompleted)throws DBProcessingException{
          
           long endTime= System.currentTimeMillis();
           try{
           IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
           String timeStamp=dbSession.getIibd_file_util().getCurrentTime();
           DBWait dbWait=new DBWait(p_fileName,p_tablName,p_primaryKey,methodName,className,session.getI_session_identifier().toString(),endTime-startTime,timeStamp,readBufferSize,tempSegmentSize,writeBuffersize,waitBufferSize,isSessionCompleted);
           waitBuffer.putRecordToWaitBuffer(dbWait.getSessionID(), dbWait, session);
           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
  }
   public void logWaitBuffer(String p_fileName,String p_tablName,String p_primaryKey,String className,String methodName,CohesiveSession session,DBSession dbSession,DBDependencyInjection dbdi,long startTime)throws DBProcessingException{
          
           long endTime= System.currentTimeMillis();
           try{
           IDBWaitBuffer waitBuffer=dbdi.getWaitBufferService();
           String timeStamp=dbSession.getIibd_file_util().getCurrentTime();
           DBWait dbWait=new DBWait(p_fileName,p_tablName,p_primaryKey,methodName,className,session.getI_session_identifier().toString(),endTime-startTime,timeStamp);
           waitBuffer.putRecordToWaitBuffer(dbWait.getSessionID(), dbWait, session);
           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
  }
  public long getStartTime()throws DBProcessingException{
      try{
          
          return System.currentTimeMillis();
      }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
  }
  public String getPkeyWithVersion(String p_fileName,String p_tableName,String p_pkey,ArrayList<String>p_records,CohesiveSession session,DBSession dbSession,DBDependencyInjection dbdi)throws DBProcessingException{
    try{
        dbg(" inside IBDFileUtil--->get pkey with version");
        String pkeyWithVersion=null;
        String l_fileType=getFileType(p_fileName);
        
        
        if(checkVersionAvailability(l_fileType,p_tableName,session,dbdi)){
            
         int versionNo=   getVersionNumberFromTheRecord(l_fileType,p_tableName,p_records,session,dbdi);
         
            pkeyWithVersion=p_pkey.concat("~").concat(Integer.toString(versionNo));
         
         
        }else{
            pkeyWithVersion=p_pkey;
        }
        
        dbg("end of IBDFileUtil--->get pkey with version--->pkeyWithVersion"+pkeyWithVersion);
        return  pkeyWithVersion;
        
    }catch (Exception ex) {
        dbg(ex);
        throw new DBProcessingException("Exception" + ex.toString());
    }
}
  
  
  
  public boolean checkVersionAvailability(String p_fileType,String p_table_name,CohesiveSession session,DBDependencyInjection dbdi) throws DBProcessingException,DBValidationException{
       boolean l_versionStatus=false;
     try{  
         dbg("inside IBDFileUtil--->check version availability");
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
            dbg("end of IBDFileUtil---> check version availability"+l_versionStatus);
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
   
   public int getVersionNumberFromTheRecord(String p_fileType,String p_table_name,ArrayList<String> p_records,CohesiveSession session,DBDependencyInjection dbdi)throws DBProcessingException,DBValidationException{
       
       try{
           dbg("IBDFileUtil--->inside getVersionNumberFromTheRecord");
           IMetaDataService mds=dbdi.getMetadataservice();
           int versionColId= mds.getColumnMetaData(p_fileType, p_table_name, "VERSION_NUMBER",session).getI_ColumnID();

           dbg("IBDFileUtil--->version number return from getVersionNumberFromTheRecord"+p_records.get(versionColId-1));
         return Integer.parseInt(p_records.get(versionColId-1).trim()) ;
           
      }catch(DBValidationException ex){
         throw ex;
     }catch(DBProcessingException ex){
         dbg(ex);
       throw new DBProcessingException("DBProcessingException".concat(ex.toString()));
     }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
      } 
       
   }
   
   public void copyFileToActual(String p_src,String p_dest,CohesiveSession session,DBSession dbSession,DBDependencyInjection dbdi)throws DBProcessingException{
        
        try{
            dbg("inside copy file to actual");
            dbg("p_src"+p_src);
            dbg("p_dest"+p_dest);
            ILockService lock=dbdi.getLockService();
            IPhysicalIOService io=dbdi.getPhysicalIOService();
            
            if(io.getIOLock(p_dest, session, dbSession)==true){       
             
             if(lock.isSameSessionIOLock(p_dest, session, dbSession)){  
            
             Path l_src_path = Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+p_src+i_db_properties.getProperty("FILE_EXTENSION"));
             Path l_dest_path=Paths.get(i_db_properties.getProperty("DATABASE_HOME_PATH")+p_dest+i_db_properties.getProperty("FILE_EXTENSION"));
            
             if(Files.exists(l_src_path)){
                 dbg("inside file existence check in copy file to actual");
                 Files.copy(l_src_path, l_dest_path,REPLACE_EXISTING);
                 dbg("file is copied from temp path to actual path");
             }
             
             }
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
        }finally{
            
            try{
            if(dbdi.getLockService().isSameSessionIOLock(p_dest, session, dbSession)){
                
                dbdi.getLockService().releaseIOLock(p_dest, session, dbSession);
            }
            
            }catch(Exception ex){
                dbg(ex);
                throw new DBProcessingException("Exception" + ex.toString());
            }
        }
        
        
    }
   
   public ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> createConcurrentTableMap(String p_fileName,CohesiveSession session,String mapType)throws DBProcessingException{
       
       try{
           
           String l_fileType=getFileType(p_fileName);
           
          float  loadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("TABLE_CON_HMAP_LOAD_FACTOR"));
          int  concurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("TABLE_CON_HMAP_CONCURRENCY_LEVEL"));
          int initialCapacity=0; 
          
          if(mapType.equals("Read")){
              
          
           switch(l_fileType){
               
               case "STUDENT":
                   
                   initialCapacity=16;
                   
                   break;
               case "TEACHER":
                   
                   initialCapacity=8;
                   
                   
                   break;   
                
               case "CLASS": 
                  
                    initialCapacity=11;
                    
                    break;
                    
               case "INSTITUTE":
                   
                   initialCapacity=21;
                   
                   break;
                   
               case "USER":  
                   
                   initialCapacity=11;
                   
                   break;
               
               case "APP":
                   
                   initialCapacity=3;
                   
                   break;
               
               case "BATCH":
                   
                   initialCapacity=16;
                   
                   break;
                   
           }
           
          }else{
              
              initialCapacity=5;
              
          }
           
           
           return new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
           
       }  catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
       
       
   }
   
   
   public ConcurrentHashMap<String,DBRecord> createConcurrentRecordMap(CohesiveSession session)throws DBProcessingException{
       
       try{
           
//           String l_fileType=getFileType(p_fileName);
           
          float  loadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("REC_CON_HMAP_LOAD_FACTOR"));
          int  concurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("REC_CON_HMAP_CONCURRENCY_LEVEL"));
          int initialCapacity=Integer.parseInt(session.getCohesiveproperties().getProperty("REC_CON_HMAP_INITIAL_CAPACITY")); 
          
          
           
           
           
           return new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
           
       }  catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
       
       
   }
   
   public ConcurrentHashMap<String,ArrayList<String>> createPdataConcurrentRecordMap(CohesiveSession session)throws DBProcessingException{
       
       try{
           
//           String l_fileType=getFileType(p_fileName);
           
          float  loadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("REC_CON_HMAP_LOAD_FACTOR"));
          int  concurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("REC_CON_HMAP_CONCURRENCY_LEVEL"));
          int initialCapacity=Integer.parseInt(session.getCohesiveproperties().getProperty("REC_CON_HMAP_INITIAL_CAPACITY")); 
          
          
           
           
           
           return new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
           
       }  catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
       
       
   }
   public  String getCurrentDate()throws DBProcessingException{
      
       try{ 
       dbg("inside IDBFileUtil--->getCurrentDate");    
       Date date = new Date();
       String dateformat=i_db_properties.getProperty("DATE_FORMAT");
       SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
       String l_dateStamp=formatter.format(date);
       
       
       dbg("yearAndMonth"+l_dateStamp);
       dbg("end of IDBFileUtil--->getCurrentDate");
       return l_dateStamp;
      }catch(Exception ex){
           dbg(ex);
           throw new DBProcessingException(ex.toString());
     }
      
      
  }  
   
     public  String checksum(String filepath, MessageDigest md) throws DBProcessingException {

        // file hashing with DigestInputStream
        
        try{
            String fileExtension=i_db_properties.getProperty("FILE_EXTENSION");
            dbg("inside check sum filepath"+filepath);
            try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), md)) {
                while (dis.read() != -1) ; //empty loop to clear the data
                md = dis.getMessageDigest();
            }
            // bytes to hex
            StringBuilder result = new StringBuilder();
            for (byte b : md.digest()) {
                result.append(String.format("%02x", b));
            }
        
        dbg("end of check sum result"+result);
        return result.toString();
        
      }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }

    }
  public String getReplacedException(String exception)throws DBProcessingException{
        
        try{
            dbg("getReplacedException"+exception);
//            String replacedException=null;
            if(exception.contains("~")){
                
              exception=  exception.replace("~", " ");
            }
            if(exception.contains("#")){
                
              exception=  exception.replace("#", " ");
            }
            if(exception.contains("@")){
                
              exception=  exception.replace("@", " ");
            }
           
            
           /* if(exception.length()>2000){
                
                exception=exception.substring(0,1999);
            }*/
            if(exception.length()>200){
                
                exception=exception.substring(0,200);
            }
            dbg("end of getReplacedException"+exception);
            return exception;
        }catch(Exception ex){
           dbg(ex);
           throw new DBProcessingException(ex.toString());
        }
        
        
    }
  
  public String restInvocation(String input,String serviceName,CohesiveSession session)throws DBProcessingException{
      HttpURLConnection conn=null;
      OutputStream os=null;
      BufferedReader br =null;
      try {
          dbg("inside restInvocation");
          dbg("input"+input);
          dbg("serviceName"+serviceName);
          
          String entityName=null;
          String url1=null;
          
          if(serviceName.equals("ArchApplyStatusUpdate")){
              
              entityName="Archival";
              url1=session.getCohesiveproperties().getProperty("REPORT_URL");
              
          }else if(serviceName.equals("ArchShippingStatusUpdate")){
              
              entityName="Archival";
              url1=session.getCohesiveproperties().getProperty("APP_URL");
          }
          
          dbg("entityName"+entityName);
          dbg("url"+url1);
          
//            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
            
            URL url=new URL(url1+"/"+"webresources"+"/"+entityName+"/"+serviceName);
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "text/plain");

//            String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

            os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (!(conn.getResponseCode() == HttpURLConnection.HTTP_CREATED||conn.getResponseCode() == HttpURLConnection.HTTP_OK||conn.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED)) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

            String output=new String();
            String response=new String();
            dbg("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                  response=response+output;
            }

//            conn.disconnect();
             dbg("response in restInvocation --->"+response);
            return response;
	  } catch (MalformedURLException ex) {
              dbg(ex);
              throw new DBProcessingException(ex.toString());
	  } catch (IOException ex) {
              dbg(ex);
 	     throw new DBProcessingException(ex.toString());
	  }catch (Exception ex) {
              dbg(ex);
 	     throw new DBProcessingException(ex.toString());
	  }finally{
          
          if(conn!=null){
              
              conn.disconnect();
          }
          
          try{
          
              if(os!=null){

                  os.close();
              }
              if(br!=null){
                  br.close();
              }
          }catch(Exception ex){
              dbg(ex);
              throw new DBProcessingException(ex.toString());
         }
      }

	
  } 
  
  public void sendArchivalError(String message,CohesiveSession session,DBSession dbSession,DBDependencyInjection dbdi)throws DBProcessingException{
       
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
       
       
       
       }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
       
   }
   
   private Email getEmailObject(String message,String toEmail)throws DBProcessingException,BSValidationException{
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
            throw new DBProcessingException("Exception" + ex.toString());

        }
        
   }
  
  
  
   
  public void dbg(String p_Value) {

        debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        debug.exceptionDbg(ex);

    }
}
//}

