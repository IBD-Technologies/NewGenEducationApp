/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core;

import javax.ejb.Singleton;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.DBConstants;
import com.ibd.cohesive.db.core.metadata.DBFile;
import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.EJBException;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Startup;
//import javax.ejb.TimerService;

/**
 *
 * @author IBD Technologies
 * 
 *  Change Description :public modifier  is changed to private modifier for the g_dbMetaData field
 *  Changed by : Suriyanarayanan.L  
 *  Changed on : 14/05/2018
 *  Search Tag : CODEREVIEW_1
 * 
 *  Changed by :Isac
 * Changed-on:05-12-2018
 * Change-Reason:Development
 * Change-Description: Lock service instance member created 
 * Search Tag: Cohesive1_Dev_5
 *
 
 *  Changed by :Rajkumar Velusamy
 * Changed-on:14-12-2018
 * Change-Reason:Development
 * Change-Description: EJB Integration
 * Search Tag: EJB Integration change 
 *
 
 
 */
@Startup
@Singleton
@ConcurrencyManagement(CONTAINER)
@Lock(LockType.READ)
public class DBCoreService implements IDBCoreService {

    private Map<String, DBFile> g_dbMetaData ;//This contains meta data for DB
    //private LockService i_lockService=new LockService(); //EJB Integration change    
    private CohesiveSession session;
    
    private static int initialCapacity;
    private static float loadFactor;
    private static int concurrencyLevel;
    
    static
    {
     
        try{
        
         CohesiveSession tempSession= new CohesiveSession();
         initialCapacity= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_INITIAL_CAPACITY"));
         loadFactor= Float.parseFloat(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_LOAD_FACTOR"));
         concurrencyLevel= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_CONCURRENCY_LEVEL"));
        
        }catch(Exception ex){
            initialCapacity=0;
        }
    }
    
    
//    public static int timeout;
//
//    public  int getTimeout() {
//        return timeout;
//    }
//
//    private static void setTimeout(int timeout) {
//        DBCoreService.timeout = timeout;
//    }
    public DBCoreService() {
        
        if(initialCapacity==0){
        
           g_dbMetaData = new ConcurrentHashMap();
        }else{
            
            g_dbMetaData = new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
        }   
           
       // i_lockService =new LockService();
        session = new CohesiveSession();
    }
   /* 
    public  LockService getI_lockService() { //EJB Integration change
        return i_lockService;
    }

    public  void setI_lockService(LockService lockService) { //EJB Integration change
        i_lockService = lockService;
    }
*/
 @Lock(LockType.READ)
    public Map<String, DBFile> getG_dbMetaData() {
        return g_dbMetaData;
    }

    public void setG_dbMetaData(Map<String, DBFile> g_dbMetaData) {
        this.g_dbMetaData = g_dbMetaData;
    }
    /*
    This function creates the g_dbMetaData Map using createMetaData() functions.
     */
    @PostConstruct //EJB Integration change
    @Override
    
    public void start() throws EJBException{
        boolean l_session_created_now=false;
        try {
            DBFile l_dbf = new DBFile();
            session.createSessionObject();
            l_session_created_now=session.isI_session_created_now();  
            DBConstants.setDBConstants();
            for (int i = 0; i < DBConstants.FILE_DETAILS[0].length; i++) {
                for (int j = 0; j < DBConstants.FILE_NO_OF_PROPERTY;) {
                    String l_filename = DBConstants.FILE_DETAILS[j][i];
                    j++;
                    int l_fileid = Integer.parseInt(DBConstants.FILE_DETAILS[j][i]);
                    j++;
                    boolean index_required = false;
                    if (DBConstants.FILE_DETAILS[j][i].equals("YES"))
                    {
                        index_required =true;
                    }   
                    else
                      index_required = false;
                    
                    j++;
                     boolean online_indexing_required = false;
                    if (DBConstants.FILE_DETAILS[j][i].equals("YES"))
                    {
                        online_indexing_required =true;
                    }   
                    else
                      online_indexing_required = false;
                    
                    j++;
                    dbg("inside DBCoreService start-->filename" + l_filename);
                    dbg("inside DBCoreService start-->fileid" + l_fileid);
                    dbg("inside DBCoreService start-->index_required"+index_required);
                    l_dbf = createFileMetaData(l_filename, l_fileid, index_required,online_indexing_required);
                    //dbg("inside DBCoreService start-->filename" + l_dbf.getI_FileName());
                        dbg("l_dbf.getI_FileName()"+l_dbf.getI_FileType());
                    g_dbMetaData.put(l_filename, l_dbf);
                    

                }
            }
            dbg("inside DBCoreService start -->g_dbMetaData.size()-->" + g_dbMetaData.size());
            setG_dbMetaData(g_dbMetaData);
//            TimerService timerService=new TimerService();
//            setTimeout(Integer.parseInt(session.getCohesiveproperties().getProperty("BUFFER_TIME_OUT")));
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new EJBException(ex);
        } catch (ClassCastException ex) {
            dbg(ex);
           throw new EJBException(ex);
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new EJBException(ex);
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new EJBException(ex);
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new EJBException(ex);
        } catch (Exception ex) {
            dbg(ex);
          throw new EJBException(ex);
        }finally{
            DBConstants.resetDBConstants();
            if(l_session_created_now){ 
                session.clearSessionObject();
            }
        }

       
    }

    /*
       This function constructs the DBFile using the createTableMetaData() function 
     */
    public DBFile createFileMetaData(String p_FileType, int p_Fileid, boolean index_required , boolean online_indexing_required) throws DBProcessingException {

        try {
           DBFile l_dbfile = new DBFile();
            
            l_dbfile.setI_FileType(p_FileType);
            l_dbfile.setI_Fileid(p_Fileid);
            l_dbfile.setI_index_required(index_required);
            l_dbfile.setI_online_indexing_required(online_indexing_required);
            l_dbfile.setI_TableCollection(createTableMetaData(p_FileType));
            
            
            return l_dbfile;
        
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }
       
    }

    /*
       This function constructs the Table collection using the createTableMetaData() function 
    in param is file type.out param is a map whose key is table id and value is DBTable
     */
    public Map<String, DBTable> createTableMetaData(String p_FileType) throws DBProcessingException {

        String[][] l_TableDetails = {};
        String[][] l_ColumnDetails = {};
        Map<String, DBTable> l_Tablecollection = new HashMap<>();
        try {
            dbg("Inside createTableMetaData --> p_FileName -->" + p_FileType);
            //  dbg("Inside createFileMetaData --> fileid -->" + p_Fileid);

            //this.setI_FileName(p_FileName);
            //this.setI_Fileid(p_Fileid);
            //  try {
            Object l_Obj1 = null;

            l_TableDetails = (String[][]) DBConstants.class.getField(p_FileType).get(l_Obj1);
            dbg("Dynamic static variable l_TableDetails[0][0]--> " + l_TableDetails[0][0]);

            //} catch (NoSuchFieldException | SecurityException ex) {
            //  Logger.getLogger(DBFile.class.getName()).log(Level.SEVERE, null, ex);
            //}
            for (int i = 0; i < l_TableDetails[0].length; i++) {
                DBTable l_DBTable = new DBTable();

                for (int j = 0; j < DBConstants.TABLE_NO_OF_PROPERTY;) {

                    //l_DBTable.setI_TableName(l_TableDetails[j][i]);
                    // DBTable l_DBTable = new DBTable();
                    Map<String, DBColumn> l_columnCollection = new HashMap<>();

                    //try {
                    Object l_Obj2 = null;

                    l_ColumnDetails = (String[][]) DBConstants.class.getField(l_TableDetails[j][i]).get(l_Obj2);
                    //dbg("Dynamic static variable  l_ColumnDetails[0][0] --> " + l_ColumnDetails[0][0]);
                    //} catch (NoSuchFieldException | SecurityException ex) {
                    //    Logger.getLogger(DBFile.class.getName()).log(Level.SEVERE, null, ex);
                    //}
                    l_DBTable.setI_TableName(l_TableDetails[j][i]);
                    dbg("Inside  createtableMetaData --> table name-->" + l_TableDetails[j][i]);
                    j++;

                    l_DBTable.setI_Tableid(Integer.parseInt(l_TableDetails[j][i]));
                    dbg("Inside createtableMetaData --> table id-->" + l_TableDetails[j][i]);

                    j++;

                    l_DBTable.setI_Pkey(l_TableDetails[j][i]);
                    dbg("Inside createtableMetaData --> Pkey -->" + l_TableDetails[j][i]);
                    j++;
                    l_DBTable.setI_Relationship(l_TableDetails[j][i]);
                    j++;
                    l_DBTable.setI_file_names_for_the_change(l_TableDetails[j][i]);
                    j++;
                    l_DBTable.setI_online_change(l_TableDetails[j][i]);
                    j++;
                    l_columnCollection = createColumnMetaData(l_ColumnDetails, l_ColumnDetails[0].length, DBConstants.COLUMN_NO_OF_PROPERTY);
                    l_DBTable.setI_ColumnCollection(l_columnCollection);
                    l_Tablecollection.put(String.valueOf(l_DBTable.getI_Tableid()), l_DBTable);
                }
            }

        } catch (DBProcessingException ex) {
            dbg(ex);
            throw ex;
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException | ClassCastException | NullPointerException" + ex.toString());
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalAccessException | IllegalArgumentException" + ex.toString());
        } catch (NoSuchFieldException | SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchFieldException | SecurityException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }

        return l_Tablecollection;
    }
/* this method creates the colunmn collection map 
    in param is */
    private Map<String, DBColumn> createColumnMetaData(String[][] l_ColumnDetail, int l_collist_array_row_length, int l_collist_array_column_length) throws DBProcessingException {
//    public Map<String, DBColumn> createColumnMetaData(String[][] l_ColumnDetail, int l_collist_array_row_length, int l_collist_array_column_length) throws DBProcessingException {
        Map<String, DBColumn> l_columnCollection = new HashMap<>();
        try {

            dbg("Inside creatingColumnMetaData --> lcollist_array_row_length -->" + l_collist_array_row_length);
            dbg("Inside creatingColumnMetaData --> lcollist_array_column_length -->" + l_collist_array_column_length);

            for (int i = 0; i < l_collist_array_row_length; i++) {

                DBColumn l_dbcolumn = new DBColumn();

                for (int j = 0; j < l_collist_array_column_length;) {

                    l_dbcolumn.setI_ColumnName(l_ColumnDetail[j][i]);

                    j++;

                    l_dbcolumn.setI_ColumnID(Integer.parseInt(l_ColumnDetail[j][i]));

                    j++;
                    l_dbcolumn.setI_ColumnDataType(l_ColumnDetail[j][i]);

                    j++;
                    l_dbcolumn.setI_ColumnLength(Integer.parseInt(l_ColumnDetail[j][i]));

                    j++;
                    l_dbcolumn.setI_ColumnLevelRelationship(l_ColumnDetail[j][i]);
                     
                    j++;
                    dbg("Inside creatingColumnMetaData --> Column   Name-->" + l_dbcolumn.getI_ColumnName());
                    dbg("Inside creatingColumnMetaData --> Column   ID-->" + l_dbcolumn.getI_ColumnID());
                    dbg("Inside creatingColumnMetaData --> Column   Length-->" + l_dbcolumn.getI_ColumnLength());
                    dbg("Inside creatingColumnMetaData --> Column   Data type-->" + l_dbcolumn.getI_ColumnDataType());
                    l_columnCollection.put(String.valueOf(l_dbcolumn.getI_ColumnID()), l_dbcolumn);

                }

            }
            //l_DBTable1.setI_ColumnCollection(columncollection);

        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            throw new DBProcessingException("ClassCastException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        } catch (Exception ex) {

            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        return l_columnCollection;
    }

     public void displayTableMap(DBFile dbfile) {
      try{ 
          

        dbfile.getI_TableCollection().forEach((k, v) -> {
            dbg("Inside displayTableMap Table Id -->" + v.getI_Tableid());
            dbg("Inside displayTableMap Table Name -->" + v.getI_TableName());
            dbg("Inside displayTableMap Table Primary Key -->" + v.getI_Pkey());
            v.getI_ColumnCollection().forEach((kc, vc) -> {
                dbg("Inside displayTableMap  column retrival colum Id-->" + vc.getI_ColumnID());
                dbg("Inside displayTableMap  column retrival colum Name-->" + vc.getI_ColumnName());
                dbg("Inside displayTableMap  column retrival colum Datatype-->" + vc.getI_ColumnDataType());
                dbg("Inside displayTableMap  column retrival colum Length-->" + vc.getI_ColumnLength());
            }
            );

        });
      }
      catch(Exception e){
         dbg(e.getMessage()); 
      }
    } 
 @PreDestroy
    public void stop() {
        g_dbMetaData = null;
        //dbf = null;
    }

    /* 
    This fumction prints the content of the file metadata G_dbMetaData
     */

  public void displayFileMap() {
        try {


            getG_dbMetaData().forEach((k, v) -> {
                dbg("Inside displayFileMap File Name -->" + k);
                dbg("Inside displayFileMap File id -->" + v.getI_Fileid());
               displayTableMap(v);
            });
        } catch (Exception e) {
            dbg(e.getLocalizedMessage());
        }
    }
  public void displayMap(){
      g_dbMetaData.forEach((String k,DBFile v)->{
          dbg("file name->k"+k);
          dbg("file name->"+v.getI_FileType());
          dbg("file Id->"+v.getI_Fileid());
          dbg("isI_index_required->"+v.isI_index_required());
          dbg("isI_online_indexing_required->"+v.isI_online_indexing_required());
          v.getI_TableCollection().forEach((String kc,DBTable vc)->{
              dbg("table name->kc"+kc);
              dbg("table name->vc"+vc.getI_TableName());
              dbg("table id->vc"+vc.getI_Tableid());
              dbg("table getI_Pkey->vc"+vc.getI_Pkey());
              vc.getI_ColumnCollection().forEach((String l,DBColumn m)->{
                  dbg("column name->l"+l);
                  dbg("column name->m"+m.getI_ColumnName());
                  dbg("column id->m"+m.getI_ColumnID());
                  dbg("column getI_ColumnDataType->m"+m.getI_ColumnDataType());
                  dbg("column getI_ColumnLength->m"+m.getI_ColumnLength());
              });
              
          });
          
      });
  }
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
    
