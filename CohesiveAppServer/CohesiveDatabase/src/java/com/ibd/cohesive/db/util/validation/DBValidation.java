/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.util.validation;

import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.DBFile;
import com.ibd.cohesive.db.core.metadata.DBTable;
//import com.ibd.cohesive.db.index.IIndexCoreService; EJB Integration chage starts 
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.db.write.IDBWriteBufferService;

import com.ibd.cohesive.db.tempSegment.IDBTempSegmentService;

import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 *
 * Change Description:since there can be more than one table with same name,we
 * have to include file type as well in the table name validation function and
 * logic in that function is changed to include filetype checking 
 * Changeby:Suriya Narayanan.L 
 * Change on:14/05/2018
 * Search Tag:CODEREVIEW_6
 * 
 * 
 * Change Description:In getColumnMetaData function of Metadata Service the columnValue validation is changed to columnName validation so columnName validation function is included. 
 * Change by:Suriya Narayanan.L 
 * Change on:14/05/2018
 * Search Tag:CODEREVIEW_7
 *
 */
public class DBValidation {

    /*This function verifies  whether the filename contains special characters ~ , # , space or not.and also
  checks whether this application has the specified filename or not */
   // boolean status = false;
           // boolean file_status = false;

    /*IDBCoreService dbcs;
    IIndexCoreService ics;*/
    //String i_Pkey;//to help lambda expression in recordValuesValidation() function
    //String i_pk = null;//to help lambda expression in recordValuesValidation() function
    //String[] tokens = {};//to help lambda expression in recordValuesValidation() function
    //boolean indicator1 = true, indicator2 = true, indicator3 = true, indicator4 = true;//to help lambda expression in recordValuesValidation() function
   // int column_size = 0;
    //String i_file_name;
    //String i_column_name;
    // DBDependencyInjection dbdi;
    Debug debug; // Instance member to handle Helper class 
    //DBDependencyInjection dbdi; 
    public DBValidation() throws NamingException {

       // dbdi = new DBDependencyInjection();
        /*dbcs = DBDependencyInjection.getDbcoreservice();
        ics = DBDependencyInjection.getIndexcoreservice();*/
    }

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    /*public DBValidation(IIndexCoreService ics) {
        //DBDependencyInjection dbdi = new DBDependencyInjection();
        dbcs = DBDependencyInjection.getDbcoreservice();
        this.ics = ics;
    }*/
    public boolean fileNameValidation(String p_fileName, ErrorHandler errhandler,CohesiveSession session,DBDependencyInjection dbdi) throws DBProcessingException {
        boolean file_status = false;
        try {
            // dbdi.createSessionObject();
            
            StringBuffer single_error_code = new StringBuffer();
            //status = true;

            IDBCoreService dbcs = dbdi.getDbcoreservice(); //EJB Integration change
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            IDBTempSegmentService tempBuffer=dbdi.getDBTempSegmentService();
            
            file_status = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + p_fileName + session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
            dbg("file_status after physical check-->"+file_status);
            if(file_status==false){
                
                   file_status=tempBuffer.checkFileExistenceInTempBuffer(p_fileName, session);
                 
                    dbg("file_status after temp buffer second physical check-->"+file_status);
               
                if(file_status==false){
                    
                file_status=writeBuffer.checkFileExistenceInWriteBuffer(p_fileName, session);
                
                dbg("file_status after writeBuffer check-->"+file_status);
                 
                 }
          
                
              /*  if(file_status==false){
                    
                    file_status = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + p_fileName + session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
                    
                    dbg("file_status after second physical check-->"+file_status);
                }*/
          
                if(file_status==false){
                    
                    file_status = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + p_fileName + session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
                    
                    dbg("file_status after second physical check-->"+file_status);
                }
           
                if(file_status==false){
                    
                    Thread.sleep(2000);
                    
                    file_status = Files.exists(Paths.get(session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH") + p_fileName + session.getCohesiveproperties().getProperty("FILE_EXTENSION")));
                    
                    dbg("file_status after wait physical check-->"+file_status);
                }
                
            }
            
            
            dbg("in fileNameValidation->file_status-> " + file_status);
            /*if ((p_fileName == null) && p_fileName.length() == 0) {
                status = false;
            }
            if (p_fileName.contains("~")) {
                status = false;

            } else if (p_fileName.contains("#")) {
                status = false;

            } else if (p_fileName.contains(" ")) {
                status = false;
            }*/
            //file_status = true;
            if (file_status == false) {
                single_error_code = single_error_code.append("DB_VAL_000");
                errhandler.setSingle_err_code(single_error_code);
            }

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
        //dbdi.clearSessionObject();
        return file_status;
    }

    public boolean fileTypeValidation(String p_fileType, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {//This function actually checks whether file type is "STUDENT", "USER", "TEACHER", or "INSTITUTE"
         boolean status = false;
        try {
            //dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            Map<String, DBFile> l_dbMetaData;
            status = true;
            /* if ((p_fileName == null) && p_fileName.length() == 0) {
                status = false;
            }
            if (p_fileName.contains("~")) {
                status = false;

            } else if (p_fileName.contains("#")) {
                status = false;

            } else if (p_fileName.contains(" ")) {
                status = false;
            }*/
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            if (status) {
                status = false;
                l_dbMetaData = dbcs.getG_dbMetaData();
                Iterator iterator=l_dbMetaData.keySet().iterator();
                while(iterator.hasNext()){
                    String l_key_file_type = (String)iterator.next();
                    if (l_key_file_type.equals(p_fileType)) {
                        status = true;
                    }
                }
                
                /*l_dbMetaData.forEach((String kc, DBFile vc) -> {
                    if (kc.equals(p_fileType)) {
                        status = true;
                    }
                });*/
            }
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_001");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        // dbdi.clearSessionObject();
        return status;
    }

    public boolean fileIDValidation(int p_fileId, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {
         boolean status = false;
        try {
            // dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            Map<String, DBFile> l_dbMetaData;
            status = true;
            //String l_fileId = String.valueOf(p_fileId);
            /* if (l_fileId == null && l_fileId.length() == 0) {
                status = false;
            }

            if (l_fileId.contains("~")) {
                status = false;
            } else if (l_fileId.contains("#")) {
                status = false;
            } else if (l_fileId.contains(" ")) {
                status = false;
            }*/
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            if (status) {
                status = false;
                l_dbMetaData = dbcs.getG_dbMetaData();
                Iterator iterator=l_dbMetaData.values().iterator();
                while(iterator.hasNext()){
                    DBFile l_value_dbfile =(DBFile)iterator.next();
                    if(l_value_dbfile.getI_Fileid()==p_fileId){
                        status = true;
                    }
                }
                
                
                /*l_dbMetaData.forEach((String kc, DBFile vc) -> {
                    if (vc.getI_Fileid() == p_fileId) {
                        status = true;
                    }
                });*/
            }
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_002");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        //dbdi.clearSessionObject();
        return status;
    }

    //public boolean tableNameValidation(String p_tableName, ErrorHandler errhandler) throws DBProcessingException {//CODEREVIEW_6
    public boolean tableNameValidation(String p_file_type, String p_tableName, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {
         boolean status = false;
        try {
            //dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            Map<String, DBFile> l_dbMetaData;
            status = true;
            /*if (p_tableName == null && p_tableName.length() == 0) {
                status = false;
            }
            if (p_tableName.contains("~")) {
                status = false;
            } else if (p_tableName.contains("#")) {
                status = false;
            } else if (p_tableName.contains(" ")) {
                status = false;
            }*/
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            if (status) {
                l_dbMetaData = dbcs.getG_dbMetaData();
                status = false;
                Iterator iterator = l_dbMetaData.values().iterator();
                while (iterator.hasNext()) {
                    DBFile l_value_dbfile = (DBFile) iterator.next();
                    if (l_value_dbfile.getI_FileType().equals(p_file_type)) {
                        Iterator l_key_dbtable_iterator = l_value_dbfile.getI_TableCollection().values().iterator();
                        while (l_key_dbtable_iterator.hasNext()) {
                            DBTable l_dbtable = (DBTable) l_key_dbtable_iterator.next();
                            if (l_dbtable.getI_TableName().equals(p_tableName)) {
                                status = true;
                            }

                        }
                    }
                }

                /*l_dbMetaData.forEach((String kc, DBFile vc) -> {
                    if (vc.getI_FileType().equals(p_file_type)) {//CODEREVIEW_6
                        vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                            if (v.getI_TableName().equals(p_tableName)) {
                                status = true;
                            }
                        });
                    }//CODEREVIEW_6
                });*/
            }
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_003");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        // dbdi.clearSessionObject();
        return status;
    }

    public boolean tableIDValidation(int p_tableId, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {
         boolean status = false;
        try {
            // dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            Map<String, DBFile> l_dbMetaData;
           // String l_tableId = String.valueOf(p_tableId);
            status = true;
            // dbg("l_tableId.length()"+l_tableId.length());
            /*  if (l_tableId == null) {
                status = false;
            }

            if (l_tableId.contains("~")) {
                status = false;
            } else if (l_tableId.contains("#")) {
                status = false;
            } else if (l_tableId.contains(" ")) {
                status = false;
            }*/
            // dbg("status"+status);
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            if (status) {
                l_dbMetaData = dbcs.getG_dbMetaData();
                status = false;
                Iterator iterator1=l_dbMetaData.values().iterator();
                while(iterator1.hasNext()){
                    DBFile l_dbfile =(DBFile)iterator1.next();
                    Iterator iterator2=l_dbfile.getI_TableCollection().values().iterator();
                    while(iterator2.hasNext()){
                        DBTable l_dbtable=(DBTable)iterator2.next();
                        if(l_dbtable.getI_Tableid()==p_tableId){
                            status = true;
                        }
                        
                    }
                }
                
                
                
                /*l_dbMetaData.forEach((String kc, DBFile vc) -> {
                    vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                        dbg("v.getI_Tableid()" + v.getI_Tableid());
                        if (v.getI_Tableid() == p_tableId) {
                            status = true;
                        }
                    });
                });*/
            }
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_004");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        //dbdi.clearSessionObject();
        return status;
    }
     /*CODEREVIEW_7 starts here*/
    public boolean columnNameValidation(String p_file_type, String p_table_name, String p_columnName, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {
         boolean status = false;
        try {
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change

            Map<String, DBFile> l_dbMetaData;
            l_dbMetaData = dbcs.getG_dbMetaData();
            status = false;
            Iterator iterator = l_dbMetaData.values().iterator();
            while (iterator.hasNext()) {
                DBFile l_dbfile = (DBFile) iterator.next();
                if (l_dbfile.getI_FileType().equals(p_file_type)) {
                    Iterator l_dbtable_iterator = l_dbfile.getI_TableCollection().values().iterator();
                    while (l_dbtable_iterator.hasNext()) {
                        DBTable l_dbtable = (DBTable) l_dbtable_iterator.next();
                        if (l_dbtable.getI_TableName().equals(p_table_name)) {
                            Iterator iterator1 = l_dbtable.getI_ColumnCollection().values().iterator();
                            while (iterator1.hasNext()) {
                                DBColumn l_dbcolumn = (DBColumn) iterator1.next();
                                if (l_dbcolumn.getI_ColumnName().equals(p_columnName)) {
                                    status = true;
                                }
                            }
                        }
                    }
                }
            }
            
           /* l_dbMetaData.forEach((String kc, DBFile vc) -> {
                if (vc.getI_FileType().equals(p_file_type)) {

                    vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                        if (v.getI_TableName().equals(p_table_name)) {
                            v.getI_ColumnCollection().forEach((String ki, DBColumn vi) -> {
                                if (vi.getI_ColumnName().equals(p_columnName)) {
                                    status = true;
                                }
                            });

                        }
                    });

                }
            });*/

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        return status;
    }
 /*CODEREVIEW_7 ends here*/
    
    
  /*  public boolean columnValueValidation(String p_columnValue, ErrorHandler errhandler) throws DBProcessingException {
        try {
            // dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            //  Map<String, DBFile> l_dbMetaData;
            status = true;
            /* if (p_columnValue == null && p_columnValue.length() == 0) {
                status = false;
            }
            if (p_columnValue.contains("~")) {
                status = false;
            } else if (p_columnValue.contains("#")) {
                status = false;
            } else if (p_columnValue.contains(" ")) {
                status = false;
            }*/
 /*if (status) {
            l_dbMetaData = dbcs.getG_dbMetaData();
            status = false;
            l_dbMetaData.forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    v.getI_ColumnCollection().forEach((String ki, DBColumn vi) -> {
                        if (vi.getI_ColumnName().equals(p_columnName)) {
                            status = true;
                        }
                    });
                });
            });
        }
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_005");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        //dbdi.clearSessionObject();
        return status;
    }*/

    public boolean columnIDValidation(String p_file_type, String p_table_name, int p_columnId, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {
         boolean status = false;
        try {
            //dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            Map<String, DBFile> l_dbMetaData;
            status = true;
            //String l_columnId = String.valueOf(p_columnId);
            /*if (l_columnId == null && l_columnId.length() == 0) {
                status = false;
            }
            if (l_columnId.contains("~")) {
                status = false;
            } else if (l_columnId.contains("#")) {
                status = false;
            } else if (l_columnId.contains(" ")) {
                status = false;
            }*/
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            if (status) {
                l_dbMetaData = dbcs.getG_dbMetaData();
                status = false;
                
                Iterator iterator = l_dbMetaData.values().iterator();
            while (iterator.hasNext()) {
                DBFile l_dbfile = (DBFile) iterator.next();
                if (l_dbfile.getI_FileType().equals(p_file_type)) {
                    Iterator l_dbtable_iterator = l_dbfile.getI_TableCollection().values().iterator();
                    while (l_dbtable_iterator.hasNext()) {
                        DBTable l_dbtable = (DBTable) l_dbtable_iterator.next();
                        if (l_dbtable.getI_TableName().equals(p_table_name)) {
                            Iterator iterator1 = l_dbtable.getI_ColumnCollection().values().iterator();
                            while (iterator1.hasNext()) {
                                DBColumn l_dbcolumn = (DBColumn) iterator1.next();
                                if (l_dbcolumn.getI_ColumnID() == p_columnId) {
                                    status = true;
                                }
                            }
                        }
                    }
                }
            }
                
                
                
                
                
                
               /*l_dbMetaData.forEach((String kc, DBFile vc) -> {
                    if (vc.getI_FileType().equals(p_file_type)) {
                        dbg("" + vc);
                        vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                            if (v.getI_TableName().equals(p_table_name)) {
                                v.getI_ColumnCollection().forEach((String ki, DBColumn vi) -> {
                                    if (vi.getI_ColumnID() == p_columnId) {
                                        status = true;
                                    }
                                });

                            }
                        });

                    }
                });*/
            }
            dbg("in columnid validation->p_columnId->status" + p_columnId + "  " + status);
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_006");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        // dbdi.clearSessionObject();
        return status;
    }

    public boolean recordValuesValidation(ErrorHandler errhandler, DBDependencyInjection dbdi,int p_tableID, String... p_record_values) throws DBProcessingException {
        boolean indicator1 = true, indicator2 = true, indicator3 = true, indicator4 = true;
         boolean status = false;
        try {
            
            //dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
           // String[] l_pkey_columnids;
            status = true;
            String l_Pkey;
            int column_size = 0;
            //dbg("p_record_values[0]"+p_record_values[0]); 
            //Each column Value Length validation
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            dbg("p_tableID"+p_tableID);
            
            Iterator iterator=dbcs.getG_dbMetaData().values().iterator();
            while(iterator.hasNext()){
                DBFile l_dbfile = (DBFile)iterator.next();
                Iterator iterator1=l_dbfile.getI_TableCollection().values().iterator();
                while(iterator1.hasNext()){
                    DBTable l_dbtable=(DBTable)iterator1.next();
                    if(l_dbtable.getI_Tableid()==p_tableID){
                      l_Pkey = l_dbtable.getI_Pkey();
                      column_size = l_dbtable.getI_ColumnCollection().size(); 
//                      Iterator iterator2=l_dbtable.getI_ColumnCollection().values().iterator();
                     
//                     while(iterator2.hasNext()){
                          //DBColumn l_dbcolumn=(DBColumn)iterator2.next();
                          for(int i=0;i<p_record_values.length;i++){
                              
                               DBColumn l_dbcolumn=l_dbtable.getI_ColumnCollection().get(Integer.toString(i+1));
                               dbg("p_record_values[i]"+p_record_values[i]);
                              dbg("_dbcolumn.getI_ColumnName()"+l_dbcolumn.getI_ColumnName());
                              
                             
                              if (p_record_values[i].length() > l_dbcolumn.getI_ColumnLength()){
                                dbg("indicator1 is false");
                                  indicator1 = false;  
                              }
                          }
                          
//                      }
                       
                    }
                }
                
            }
            
            /*dbcs.getG_dbMetaData().forEach((String k, DBFile v) -> {
                v.getI_TableCollection().forEach((String kc, DBTable vc) -> {
                    if (vc.getI_Tableid() == p_tableID) {
                        i_Pkey = vc.getI_Pkey();
                        column_size = vc.getI_ColumnCollection().size();
                        vc.getI_ColumnCollection().forEach((String ki, DBColumn vi) -> {
                            // for(int i=0;i<p_record_values.length;i++){
                            int j = 0;
                            if (p_record_values[j].length() > vi.getI_ColumnLength()) {
                                indicator1 = false;

                            }
                            j++;
                            //}
                        });
                    }

                });
            });*/
            //dbg("in recordValuesValidation metadata" + i_Pkey);
            dbg("column size and record valuess length" + column_size + p_record_values.length);
            /*if (column_size == p_record_values.length) {
                l_pkey_columnids = i_Pkey.split("~");
                for (int i = 0; i < l_pkey_columnids.length; i++) {
                    if (i == 0) {
                        i_pk = p_record_values[Integer.parseInt(l_pkey_columnids[i]) - 1];
                    } else {
                        i_pk = i_pk.concat("~").concat(p_record_values[Integer.parseInt(l_pkey_columnids[i]) - 1]);
                    }
                }
                dbg("in recordValuesValidation PK value" + i_pk);
                if (ics.getIndex_map() != null) {

                    ics.getIndex_map().forEach((String k, Map<String, String> v) -> {
                        tokens = k.split("~");
                        if (Integer.parseInt(tokens[0]) == p_tableID) {
                            v.forEach((String kc, String vc) -> {
                                if (kc.equals(i_pk)) {
                                    dbg("in recordValuesValidation kc" + kc + i_pk);
                                    indicator2 = false;

                                }

                            });
                        }
                    });
                }
            } else {
                indicator3 = false;

            }*/
            if (column_size != p_record_values.length) {
                indicator3 = false;
            }
            /*for (int i = 0; i < p_record_values.length; i++) {
                //StringBuffer p_err1_code = new StringBuffer();
                //dbg("p_record_values[i]"+p_record_values[i]);
                //dbg("columnValueValidation(p_record_values[i],errhandler)"+columnValueValidation(p_record_values[i],errhandler));
                //if (!columnValueValidation(p_record_values[i], errhandler)) {
                  //  indicator4 = false;

              //  }

            }*/

            if (indicator1 == false) {
                status = false;
                single_error_code = single_error_code.append("DB_VAL_007");
                errhandler.setSingle_err_code(single_error_code);

            }
            if (indicator2 == false) {
                status = false;
                if (single_error_code.length() > 0) {
                    single_error_code = single_error_code.append("~").append("DB_VAL_009");
                    errhandler.setSingle_err_code(single_error_code);
                } else {
                    single_error_code = single_error_code.append("DB_VAL_009");
                    errhandler.setSingle_err_code(single_error_code);
                }

            }
            if (indicator3 == false) {
                status = false;
                if (single_error_code.length() > 0) {
                    single_error_code = single_error_code.append("~").append("DB_VAL_008");
                    errhandler.setSingle_err_code(single_error_code);
                } else {
                    single_error_code = single_error_code.append("DB_VAL_008");
                    errhandler.setSingle_err_code(single_error_code);
                }

            }
            if (indicator4 == false) {
                status = false;
                if (single_error_code.length() > 0) {
                    single_error_code = single_error_code.append("~").append("DB_VAL_005");
                    errhandler.setSingle_err_code(single_error_code);
                }
                single_error_code = single_error_code.append("DB_VAL_005");
                errhandler.setSingle_err_code(single_error_code);

            }
            /* if(status==false){
         errhandler.setSingle_err_code(single_error_code);
        }*/
 /*if (status == false) {
            if (p_err_code != null) {
                p_err_code = p_err_code.append("~").append("DB_VAL_009");
            } else {
                p_err_code = p_err_code.append("DB_VAL_009");
            }
        }*/
            // dbg("status is "+status);
            // dbg("status"+status);
            // dbdi.clearSessionObject();
            return status;
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());

        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }

    }

    public boolean pkValidation(int p_tableId, String p_Pkey, ErrorHandler errorhandler) throws DBProcessingException {
         boolean status = false;
        try {
            //dbdi.createSessionObject();
            status = true;
            StringBuffer single_err_code = new StringBuffer();
            String l_tableId = String.valueOf(p_tableId);
            dbg("pkValidation p_tableId" + p_tableId);
            dbg("pkValidation p_Pkey" + p_Pkey);
            dbg("pkValidation l_tableId" + l_tableId);

            if (p_Pkey == null && p_Pkey.length() == 0) {
                status = false;
            }
            //IndexCoreService ics = new IndexCoreService();
            /* EJB integration change starts
               IIndexCoreService ics = DBDependencyInjection.getIndexcoreservice();
            
            Iterator iterator=ics.getIndex_map().keySet().iterator();
            Iterator iterator1=ics.getIndex_map().values().iterator();
            while(iterator.hasNext()&&iterator1.hasNext()){
                String l_key = (String)iterator.next();
                Map<String, String> l_value=(Map<String, String>)iterator1.next();
                
                if(l_key.equals(l_tableId)){
                   status = false; 
                   Iterator iterator2=l_value.keySet().iterator();
                   Iterator iterator3=l_value.values().iterator();
                   while(iterator2.hasNext()&&iterator3.hasNext()){
                       String l_map_key = (String)iterator2.next();
                      // String l_map_value = (String)iterator3.next();
                       if(l_map_key.equals(p_Pkey)){
                          status = true; 
                       }
                   }
                   
                }
                
            }
            */
            
            /*ics.getIndex_map().forEach((String kc, Map<String, String> vc) -> {
                if (kc.equals(l_tableId)) {
                    status = false;
                    vc.forEach((String k, String v) -> {
                        if (k.equals(p_Pkey)) {
                            status = true;
                        }
                    });
                }

            });*/
            if (status == false) {
                single_err_code = single_err_code.append("DB_VAL_010");
                errorhandler.setSingle_err_code(single_err_code);
            }
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        //dbdi.clearSessionObject();
        return status;
    }

    public boolean specialCharacterValidation(String p_column_name, ErrorHandler errhandler) throws DBProcessingException {
        boolean status = false;
        try {
            //dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            status = true;
            if ((p_column_name == null) || p_column_name.length() == 0) {
                dbg("column name is null");
                status = false;
            }
            if (p_column_name.contains("~")) {
                status = false;

            } else if (p_column_name.contains("#")) {
                status = false;

            }
            else if (p_column_name.contains("@")) {
                status = false;

            }
            /*else if (p_column_name.contains(" ")) {
                status = false;
            }*/
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_012");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        //dbdi.clearSessionObject();
        return status;
    }

    public boolean specialCharacterValidation(int p_column_Id, ErrorHandler errhandler) throws DBProcessingException {
         boolean status = false;
        try {
            //  dbdi.createSessionObject();
            StringBuffer single_error_code = new StringBuffer();
            status = true;
            String l_column_Id = String.valueOf(p_column_Id);
            if ((l_column_Id == null) || l_column_Id.length() == 0) {
                status = false;
            }
            if (l_column_Id.contains("~")) {
                status = false;

            } else if (l_column_Id.contains("#")) {
                status = false;

            } else if (l_column_Id.contains(" ")) {
                status = false;
            }else if (l_column_Id.contains("@")) {
                status = false;
            }
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_012");
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        // dbdi.clearSessionObject();
        return status;
    }

    public boolean columnDataTypeValidation(String p_table_name, int column_id, String column_value, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {
        StringBuffer single_error_code = new StringBuffer();
         boolean status = false;
        status = false;
        String l_file_name=null;
        String l_column_name=null;
        try {
            // dbdi.createSessionObject();
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
           Iterator iterator1=dbcs.getG_dbMetaData().keySet().iterator();
           Iterator iterator2=dbcs.getG_dbMetaData().values().iterator();
           while(iterator1.hasNext()&&iterator2.hasNext()){
               DBFile l_dbfile=(DBFile)iterator2.next();
               Iterator iterator3=l_dbfile.getI_TableCollection().values().iterator();
               while(iterator3.hasNext()){
                   DBTable l_dbtable=(DBTable)iterator3.next();
                   if(l_dbtable.getI_TableName().equals(p_table_name)){
                       l_file_name = l_dbfile.getI_FileType();
                       Iterator iterator4=l_dbtable.getI_ColumnCollection().values().iterator();
                       while(iterator4.hasNext()){
                           DBColumn l_dbcolumn=(DBColumn)iterator4.next();
                           if(l_dbcolumn.getI_ColumnID()==column_id){
                             l_column_name = l_dbcolumn.getI_ColumnName();
                             
                              if (l_dbcolumn.getI_ColumnDataType().equals("float")) {
                                    Float.parseFloat(column_value);
                                    status = true;
                                } else if (l_dbcolumn.getI_ColumnDataType().equals("DATE")) {

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    LocalDate date = LocalDate.parse(column_value, formatter);
                                    status = true;

                                } else if (l_dbcolumn.getI_ColumnDataType().equals("string")) {

                                    status = true;

                                }

                             
                           }
                       }
                   }
                   
               }
               
           }
            
            
            
            
           /* dbcs.getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                vc.getI_TableCollection().forEach((String k, DBTable v) -> {
                    if (v.getI_TableName().equals(p_table_name)) {
                        i_file_name = vc.getI_FileType();
                        v.getI_ColumnCollection().forEach((String l, DBColumn m) -> {
                            if (m.getI_ColumnID() == column_id) {
                                i_column_name = m.getI_ColumnName();
                                if (m.getI_ColumnDataType().equals("float")) {
                                    Float.parseFloat(column_value);
                                    status = true;
                                } else if (m.getI_ColumnDataType().equals("DATE")) {

                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    LocalDate date = LocalDate.parse(column_value, formatter);
                                    status = true;

                                } else if (m.getI_ColumnDataType().equals("string")) {

                                    status = true;

                                }

                            }
                        });
                    }
                });
            });*/
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_013".concat("^" + l_file_name).concat("^" + p_table_name).concat("^" + l_column_name));
                errhandler.setSingle_err_code(single_error_code);
            }
        } catch (DateTimeParseException ex) {
            dbg("DateTimeParseException");
            single_error_code = single_error_code.append("DB_VAL_013".concat("^" + l_file_name).concat("^" + p_table_name).concat("^" + l_column_name));
            errhandler.setSingle_err_code(single_error_code);
            return false;
            //dbg(ex);
            // throw new DBProcessingException("Exception" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NumberFormatException ex) {
            single_error_code = single_error_code.append("DB_VAL_013".concat("^" + l_file_name).concat("^" + p_table_name).concat("^" + l_column_name));
            errhandler.setSingle_err_code(single_error_code);
            return false;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        // dbdi.clearSessionObject();
        return status;
    }

    public boolean columnLengthValidation(String p_table_name, String p_column_value, int p_column_id, ErrorHandler errhandler,DBDependencyInjection dbdi) throws DBProcessingException {
        boolean status = false;
        try {
            String l_file_name=null;
            String l_column_name=null;
            //  dbdi.createSessionObject();
            dbg("in columnLengthValidation->p_table_name " + p_table_name);
            dbg("in columnLengthValidation->p_column_value " + p_column_value);
            dbg("in columnLengthValidation->p_column_id " + p_column_id);
            StringBuffer single_error_code = new StringBuffer();

            status = true;
            
            
            Iterator iterator=dbdi.getDbcoreservice().getG_dbMetaData().values().iterator();
            while(iterator.hasNext()){
                DBFile l_dbfile=(DBFile)iterator.next();
                Iterator iterator1=l_dbfile.getI_TableCollection().values().iterator();
                while(iterator1.hasNext()){
                    DBTable l_dbtable=(DBTable)iterator1.next();
                    if(l_dbtable.getI_TableName().equals(p_table_name)){
                       l_file_name= l_dbfile.getI_FileType();
                       Iterator iterator2=l_dbtable.getI_ColumnCollection().values().iterator();
                       while(iterator2.hasNext()){
                    
                           DBColumn l_dbcolumn =(DBColumn)iterator2.next();
                         
                           if (l_dbcolumn.getI_ColumnID() == p_column_id) {
                                 dbg("l_dbcolumn.getI_ColumnID()"+l_dbcolumn.getI_ColumnID());
                           dbg("p_column_id"+p_column_id);
                                l_column_name = l_dbcolumn.getI_ColumnName();
                                dbg("l_dbcolumn.getI_ColumnLength()"+l_dbcolumn.getI_ColumnLength());
                                dbg("p_column_value.length()"+p_column_value.length());
                                
                                ByteBuffer copy=ByteBuffer.wrap(p_column_value.getBytes(Charset.forName("UTF-8")));
                               dbg("p_column_value byte buffer limit"+copy.limit());
                                
//                                if (l_dbcolumn.getI_ColumnLength() < p_column_value.length()) {
                                  if (l_dbcolumn.getI_ColumnLength() < copy.limit()) {
                                    status = false;
                                    dbg("status false in column length validation");
                                }
                            }
                           

                       }
                        
                    }
                    
                }
                
                
            }
            
            
           /* DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String k, DBFile v) -> {
                v.getI_TableCollection().forEach((String kc, DBTable vc) -> {
                    if (vc.getI_TableName().equals(p_table_name)) {
                        i_file_name = v.getI_FileType();
                        vc.getI_ColumnCollection().forEach((String l, DBColumn m) -> {
                            if (m.getI_ColumnID() == p_column_id) {
                                i_column_name = m.getI_ColumnName();
                                if (m.getI_ColumnLength() < p_column_value.length()) {
                                    status = false;
                                    dbg("inside column check");
                                }
                            }
                        });
                    }
                });
            });*/
            if (status == false) {
                single_error_code = single_error_code.append("DB_VAL_007".concat(
                        "," + l_file_name).concat("," + p_table_name).concat("," + l_column_name));
                errhandler.setSingle_err_code(single_error_code);
            }

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        //dbdi.clearSessionObject();
        return status;
    }

    public boolean reportingDBValidation(CohesiveSession p_session,String p_fileName )throws DBProcessingException{
         boolean status = false;
        try{
            
        IBDProperties i_db_properties=p_session.getCohesiveproperties();
        
        String subStr=p_fileName.substring(0,6);
        dbg("subStr"+subStr);
        if(subStr.equals("REPORT")){
            return true;
        }
        
        String l_reportingDB=i_db_properties.getProperty("REPORTING_DB");
        if(l_reportingDB.equals("NO")){
            
            status=true;
        }   
            
        dbg("inside reporting DBValidation--->status"+status);
                 if(status==false){
                      StringBuffer single_error_code = new StringBuffer();
                      single_error_code = single_error_code.append("DB_VAL_019");
                      p_session.getErrorhandler().setSingle_err_code(single_error_code);
                      p_session.getErrorhandler().log_error();
        
                  }
                 
       } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        return status;
    }
        
        
        
    
    
    
    
    public void dbg(String p_Value) {

        this.debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        this.debug.exceptionDbg(ex);

    }
}
