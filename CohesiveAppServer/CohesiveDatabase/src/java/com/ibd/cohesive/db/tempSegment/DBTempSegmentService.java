/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.tempSegment;

//import com.ibd.cohesive.db.core.metadata.DBColumn;
//import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.db.write.IDBWriteBufferService;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
//import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
@Singleton
@ConcurrencyManagement(BEAN)
//@AccessTimeout(value=30000)
public class DBTempSegmentService implements IDBTempSegmentService{
    DBDependencyInjection dbdi;
//    CohesiveSession session;
//    DBSession dbSession;
   // private Object lock1 = new Object();;
//    Map<String,Map<String, Map<String,DBRecord>>> tempSegmentMap;
    Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> tempSegmentMap;
//    Map<String, Map<String, Map<String,ArrayList<String>>>> tempSegmentMap;
//    @Lock(LockType.READ)
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
    
    
    
    public Map<String, ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>>> getTempSegmentMap() {
        return tempSegmentMap;
    }
    
    public DBTempSegmentService() throws NamingException {
        dbdi = new DBDependencyInjection();
//        session = new CohesiveSession();
//        dbSession = new DBSession(session);
//        tempSegmentMap=new HashMap();
//       tempSegmentMap=new ConcurrentHashMap();
        if(initialCapacity==0){
          tempSegmentMap=new ConcurrentHashMap();
       }else{
         
         tempSegmentMap=new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
     }
      
    }
//    @Lock(LockType.WRITE)
   public void createRecord(CohesiveSession p_session,DBSession p_dbSession, String p_fileName, String p_fileType, int p_tableID,String p_primaryKey, String... p_record_values) throws DBValidationException, DBProcessingException{
//      boolean l_session_created_now=false;
        try {
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IMetaDataService mds=dbdi.getMetadataservice();
            ErrorHandler errorhandler=p_session.getErrorhandler();
            dbg("inside DBTempSegmentService--->createRecord",p_session);
            dbg("inside DBTempSegmentService--->createRecord--->p_record_values"+p_record_values.length,p_session);
           String l_tableName= mds.getTableMetaData(p_tableID, p_session).getI_TableName();
            ArrayList<String> l_record = new ArrayList();
            
            for(int i=0;i<p_record_values.length;i++){
                l_record.add(p_record_values[i]);
            }
            dbg("inside DBTempSegmentService--->createRecord--->l_record"+l_record.size(),p_session);
//                             int  array_index = 0;
                             
                             
//                                while (array_index < p_record_values.length - 1) {
//                                    l_record.add(p_record_values[array_index + 1]);
////                                    l_recordStringArray[array_index]=p_record_values[array_index + 1];
//                                   dbg("in DBTemp segment->createRecord->l_column" + p_record_values[array_index+1],p_session);
//                                    array_index++;
//
//                                }
                                
                                
                    DBRecord dbrec=new DBRecord(0,l_record,'C'); 
                  long startTime=p_dbSession.getIibd_file_util(). getStartTime();
                  //  synchronized(lock1){
                   setRecordToTempSegment(p_fileName,l_tableName,p_primaryKey,dbrec,p_session);  
                   try{
                    p_dbSession.getIibd_file_util().logWaitBuffer(p_fileName,l_tableName,p_primaryKey,"DBTempSegmentService","createRecord.setRecordToTempSegment call",p_session,p_dbSession,dbdi,startTime);

                  }catch(Exception ex){
                    throw new DBProcessingException("Exception".concat(ex.toString()));
                 }
                   
                    //}
//            if(tempSegmentMap.containsKey(p_fileName)){
//                dbg("inside DBTempSegmentService--->createRecord--->tempSegmentMap contains the fileName",p_session);
//               if( tempSegmentMap.get(p_fileName).containsKey(l_tableName)){
//                     dbg("inside DBTempSegmentService--->createRecord--->tempSegmentMap contains the fileName and tablename",p_session);
//                  if(tempSegmentMap.get(p_fileName).get(l_tableName).containsKey(p_primaryKey)){
//                     dbg("inside DBTempSegmentService--->createRecord--->tempSegmentMap contains the fileName,tablename and primary key",p_session);
//                      StringBuffer single_error_code = new StringBuffer();
//                      single_error_code = single_error_code.append("DB_VAL_009");
//                      errorhandler.setSingle_err_code(single_error_code);
//                      errorhandler.log_error();
//                      throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
//                      
//                   }else{
//                      dbg("inside DBTempSegmentService--->createRecord--->tempSegmentMap contains the fileName,tablename and does not contain primary key",p_session);
//                      DBRecord dbrec=new DBRecord(0,l_record,'C');
//                       synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,l_tableName,p_primaryKey,dbrec,p_session);
//                       }
////                      tempSegmentMap.get(p_fileName).get(l_tableName).putIfAbsent(p_primaryKey, dbrec);
//                  }
//                   
//              }else{
//                   dbg("inside DBTempSegmentService--->createRecord--->tempSegmentMap contains the fileName and does not contain table name",p_session);
////                   tempSegmentMap.get(p_fileName).putIfAbsent(l_tableName,new HashMap());
//                   DBRecord dbrec=new DBRecord(0,l_record,'C');
//                    synchronized(lock1){
//                    setRecordToTempSegment(p_fileName,l_tableName,p_primaryKey,dbrec,p_session);
//                    }
////                   tempSegmentMap.get(p_fileName).get(l_tableName).putIfAbsent(p_primaryKey, dbrec);
//              }
//                
//            }else{
//                dbg("inside DBTempSegmentService--->createRecord--->tempSegmentMap does not contains the fileName ",p_session);
////                tempSegmentMap.putIfAbsent(p_fileName, new HashMap());
////                tempSegmentMap.get(p_fileName).putIfAbsent(l_tableName,new HashMap());
//                DBRecord dbrec=new DBRecord(0,l_record,'C');
//                 synchronized(lock1){
//                 setRecordToTempSegment(p_fileName,l_tableName,p_primaryKey,dbrec,p_session);
//                 }
////                tempSegmentMap.get(p_fileName).get(l_tableName).putIfAbsent(p_primaryKey, dbrec);
//            }
            
            
            
            
            
            dbg("end of DBTempSegmentService--->createRecord",p_session);
        }catch(DBValidationException ex){
           throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());
         }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//           if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
        
        
    }
   
   private synchronized void setRecordToTempSegment(String p_fileName,String p_tableName,String p_primaryKey,DBRecord dbRec,CohesiveSession p_session)throws DBProcessingException,DBValidationException{
//       long startTime=0;
       try{
//            startTime=p_dbSession.getIibd_file_util(). getStartTime();
            ErrorHandler errorhandler=p_session.getErrorhandler();
            dbg("inside setRecordToTempSegment--->fileName"+p_fileName,p_session);
            dbg("inside setRecordToTempSegment--->p_tableName"+p_tableName,p_session);
            dbg("inside setRecordToTempSegment--->p_primaryKey"+p_primaryKey,p_session);
            
            DBSession dbSession= new DBSession(p_session);
            dbSession.createDBsession(p_session);
            IBDFileUtil fileUtil=dbSession.getIibd_file_util();
            
//            String l_pk_without_version=dbSession.getIibd_file_util().getPKwithoutVersion(p_fileName,p_tableName,p_primaryKey,dbdi,p_session);
          if( tempSegmentMap.containsKey(p_fileName)){
              dbg("inside setRecordToTempSegment--->tempSegmentfileMap contains fileName",p_session);
           if(tempSegmentMap.get(p_fileName).containsKey(p_tableName)){
               dbg("inside setRecordToTempSegment--->tempSegmentfileMap contains fileName and table Name",p_session);
               if(tempSegmentMap.get(p_fileName).get(p_tableName).containsKey(p_primaryKey)){
                   dbg("inside setRecordToTempSegment--->tempSegmentfileMap contains fileName and table Name and primary key",p_session);
//               if(tempSegmentMap.get(p_fileName).get(p_tableName).containsKey(p_primaryKey)){
                 dbg("inside setRecordToTempSegment--->operation"+dbRec.getOperation(),p_session);
                   if(dbRec.getOperation()=='C'){
                       
                      StringBuffer single_error_code = new StringBuffer();
                      single_error_code = single_error_code.append("DB_VAL_009");
                      errorhandler.setSingle_err_code(single_error_code);
                      errorhandler.log_error();
                      throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                   }else if(tempSegmentMap.get(p_fileName).get(p_tableName).get(p_primaryKey).getOperation()=='D'){
                           StringBuffer single_error_code = new StringBuffer();
                           String replacedPkey=p_primaryKey.replace("~", "@");
                           single_error_code = single_error_code.append("DB_VAL_016").append(","+p_tableName).append(","+replacedPkey);
                           errorhandler.setSingle_err_code(single_error_code);
                           errorhandler.log_error();
                           throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
                   }else{
                         tempSegmentMap.get(p_fileName).get(p_tableName).replace(p_primaryKey, dbRec);

                   }
                               
               }else{
                   dbg("inside setRecordToTempSegment--->tempSegmentfileMap contains fileName and table Name not contains primary key",p_session);
                   tempSegmentMap.get(p_fileName).get(p_tableName).putIfAbsent(p_primaryKey, dbRec);
               }
           }else{
                dbg("inside setRecordToTempSegment--->tempSegmentfileMap contains fileName not contains table Name",p_session);
//                tempSegmentMap.get(p_fileName).putIfAbsent(p_tableName,new HashMap());
           
                  tempSegmentMap.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createConcurrentRecordMap(p_session));
                tempSegmentMap.get(p_fileName).get(p_tableName).putIfAbsent(p_primaryKey, dbRec);
           }
          
       }
          else{
               dbg("inside setRecordToTempSegment--->tempSegmentfileMap doesn't contains fileName",p_session);
//              tempSegmentMap.putIfAbsent(p_fileName, new HashMap());
                tempSegmentMap.putIfAbsent(p_fileName, fileUtil.createConcurrentTableMap(p_fileName, p_session,"Temp"));
//                tempSegmentMap.get(p_fileName).putIfAbsent(p_tableName,new HashMap());
                tempSegmentMap.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createConcurrentRecordMap(p_session));
                tempSegmentMap.get(p_fileName).get(p_tableName).putIfAbsent(p_primaryKey, dbRec);
           
          }   
           
           
           dbg("end of setRecord to temp segment",p_session);
        }catch(DBValidationException ex){
           throw ex;
         }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }
       
       
   }
    
//   @Lock(LockType.WRITE) 
//  public  void createRecord(CohesiveSession session,String p_fileName, String p_fileType, int p_tableID,String p_primaryKey, String... p_record_values) throws DBValidationException, DBProcessingException{
//        CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        
//        
//        createRecord(p_fileName,p_fileType,p_tableID,p_primaryKey,p_record_values);
//        
//        
//    } catch(DBValidationException ex){
//           throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//           this.session=tempSession;
//     }
//    }
    
   public void updateColumn(String p_fileName, String p_fileType,String  p_table_name,String[] p_pkey,Map<String,String> p_column_to_update,CohesiveSession p_session,DBSession p_dbSession) throws DBValidationException, DBProcessingException{
//      boolean l_session_created_now=false;
        try {
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IMetaDataService mds=dbdi.getMetadataservice();
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            ErrorHandler errorhandler=p_session.getErrorhandler();
            String l_primaryKey=p_dbSession.getIibd_file_util().formingPrimaryKey(p_pkey);
            IDBWriteBufferService writeBuffer=dbdi.getDBWriteService();
            dbg("inside DBTempSegmentService--->updateColumn",p_session);
//           String l_tableName= mds.getTableMetaData(p_tableID, session).getI_TableName();
//            ArrayList<String> l_record = new ArrayList();
//                             int  array_index = 0;
//                                while (array_index < p_record_values.length - 1) {
//                                    l_record.add(p_record_values[array_index + 1]);
//                                    l_recordStringArray[array_index]=p_record_values[array_index + 1];
//                                   dbg("in DBTemp segment->createRecord->l_column" + p_record_values[array_index+1]);
//                                    array_index++;
//
//                                }
          DBRecord dbrec=null;
          try{         

           dbrec= readBuffer.readRecord(p_fileName, p_fileType, p_table_name, p_pkey, p_session,p_dbSession);
        
          }catch(DBValidationException ex){
              
              if(ex.toString().contains("DB_VAL_000")){
                  p_session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                  
                  boolean comeOut=false;
                  
                  while(!comeOut){
                  
                      if(!writeBuffer.checkFileExistenceInWriteBuffer(p_fileName, p_session)){
                             Thread.sleep(2000);
                          dbrec= readBuffer.readRecord(p_fileName, p_fileType, p_table_name, p_pkey, p_session,p_dbSession);

                          comeOut=true;
                      }
                  
                  }
                  
              }else{
                  
                  throw ex;
              }
              
              
          }
          Iterator<String> keyIterator= p_column_to_update.keySet().iterator();
//           Iterator<String> valueIterator= p_column_to_update.values().iterator();
           while(keyIterator.hasNext()){
           String columnID= keyIterator.next();
           dbg("inside DBTempSegmentService--->columnID"+columnID,p_session);
           String value=p_column_to_update.get(columnID);
           dbg("inside DBTempSegmentService--->value"+value,p_session);
           dbrec.getRecord().set(Integer.parseInt(columnID)-1, value);
//           if(dbrec.getOperation()!='C'){
           dbrec.setOperation('U');
           
//           }
         }
            dbg("inside DBTempSegmentService--->operation"+dbrec.getOperation(),p_session);
            dbg("inside DBTempSegmentService--->position"+dbrec.getPosition(),p_session);
           //synchronized(lock1){
                     long startTime=p_dbSession.getIibd_file_util(). getStartTime();
                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
                      
                      try{
                    p_dbSession.getIibd_file_util().logWaitBuffer(p_fileName,p_table_name,l_primaryKey,"DBTempSegmentService","updateColumn.setRecordToTempSegment call",p_session,p_dbSession,dbdi,startTime);

                  }catch(Exception ex){
                    throw new DBProcessingException("Exception".concat(ex.toString()));
                 }
             //       }
           
//            if(tempSegmentMap.containsKey(p_fileName)){
//                dbg("inside DBTempSegmentService--->updateColumn-->tempsegmentmap contains file name",p_session);
//               if( tempSegmentMap.get(p_fileName).containsKey(p_table_name)){
//                dbg("inside DBTempSegmentService--->updateColumn-->tempsegmentmap contains file name and table name",p_session);
//                  if(tempSegmentMap.get(p_fileName).get(p_table_name).containsKey(l_primaryKey)){
////                      StringBuffer single_error_code = new StringBuffer();
////                      single_error_code = single_error_code.append("DB_VAL_009");
////                      errorhandler.setSingle_err_code(single_error_code);
////                      errorhandler.log_error();
////                      throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
////                      
////                   }else{
////                      DBRecord dbrec=new DBRecord(0,l_record,'C');
//                dbg("inside DBTempSegmentService--->updateColumn-->tempsegmentmap contains file name,table name and primary key",p_session);
//                   synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                       }
////                      tempSegmentMap.get(p_fileName).get(p_table_name).replace(l_primaryKey, dbrec);
//                  }else{
//                     dbg("inside DBTempSegmentService--->updateColumn-->tempsegmentmap contains file name,table name and doesn't contains primary key",p_session);
//                    synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                     }
////                      tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//                  }
//                   
//              }else{
//                  dbg("inside DBTempSegmentService--->updateColumn-->tempsegmentmap contains file name and doesn't contains ,table name",p_session);
//                  synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                       }
////                  tempSegmentMap.get(p_fileName).putIfAbsent(p_table_name,new HashMap());
////                   tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//              }
//            }else{
//                dbg("inside DBTempSegmentService--->updateColumn-->tempsegmentmap doesn't contains the file name",p_session);
//                  synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                    }
////                tempSegmentMap.put(p_fileName, new HashMap());
////                tempSegmentMap.get(p_fileName).putIfAbsent(p_table_name,new HashMap());
//////                DBRecord dbrec=new DBRecord(0,l_record,'C');
////                tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//            }
            
            
            
            dbg("end of DBTempSegmentService--->update column",p_session);
            
            
        }catch(DBValidationException ex){
           throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());
         }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//           if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
        
        
    }
    
//    @Lock(LockType.WRITE)
//  public  void updateColumn(String p_fileName, String p_fileType,String  p_table_name,String[] p_pkey,Map<String,String> p_column_to_update,CohesiveSession session) throws DBValidationException, DBProcessingException{
//        CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        
//        
//        updateColumn(p_fileName,p_fileType,p_table_name,p_pkey,p_column_to_update);
//        
//        
//    } catch(DBValidationException ex){
//           throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//           this.session=tempSession;
//     }
//    } 
//    @Lock(LockType.WRITE)
    public void updateRecord(String p_fileName, String p_fileType, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession p_session,DBSession p_dbSession) throws DBProcessingException, DBValidationException {
        
//        boolean l_session_created_now=false;
        try {
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IMetaDataService mds=dbdi.getMetadataservice();
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
            ErrorHandler errorhandler=p_session.getErrorhandler();
            String l_primaryKey=p_dbSession.getIibd_file_util().formingPrimaryKey(p_pkey);
            dbg("inside DBTempSegmentService--->updateRecord",p_session);
            
            DBRecord dbrec= readBuffer.readRecord(p_fileName, p_fileType, p_table_name, p_pkey, p_session,p_dbSession);
        
          Iterator<String> keyIterator= p_column_to_update.keySet().iterator();
//           Iterator<String> valueIterator= p_column_to_update.values().iterator();
           while(keyIterator.hasNext()){
           String columnID= keyIterator.next();
           String value=p_column_to_update.get(columnID);
           dbrec.getRecord().set(Integer.parseInt(columnID)-1, value);
//           if(dbrec.getOperation()!='C'){
           dbrec.setOperation('U');
           
//           }

         }
                    long  startTime=p_dbSession.getIibd_file_util(). getStartTime();
          // synchronized(lock1){
                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
                      try{
                       p_dbSession.getIibd_file_util().logWaitBuffer(p_fileName,p_table_name,l_primaryKey,"DBTempSegmentService","updateRecord.setRecordToTempSegment call",p_session,p_dbSession,dbdi,startTime);

                    }catch(Exception ex){
                      throw new DBProcessingException("Exception".concat(ex.toString()));
                    }
            //        }
           
           
           
//            if(tempSegmentMap.containsKey(p_fileName)){
//                dbg("inside DBTempSegmentService--->updateRecord-->tempsegmentmap contains file name",p_session);
//               if( tempSegmentMap.get(p_fileName).containsKey(p_table_name)){
//                dbg("inside DBTempSegmentService--->updateRecord-->tempsegmentmap contains file name and table name",p_session);
//                  if(tempSegmentMap.get(p_fileName).get(p_table_name).containsKey(l_primaryKey)){
//                dbg("inside DBTempSegmentService--->updateRecord-->tempsegmentmap contains file name,table name and  primary key",p_session);
////                      StringBuffer single_error_code = new StringBuffer();
////                      single_error_code = single_error_code.append("DB_VAL_009");
////                      errorhandler.setSingle_err_code(single_error_code);
////                      errorhandler.log_error();
////                      throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
////                      
////                   }else{
////                      DBRecord dbrec=new DBRecord(0,l_record,'C');
//                   synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                       }
////                      tempSegmentMap.get(p_fileName).get(p_table_name).replace(l_primaryKey, dbrec);
//                  }else{
//                dbg("inside DBTempSegmentService--->updateRecord-->tempsegmentmap contains file name,table name and doesn't contains the  primary key",p_session);
//                synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                 }
////                      tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//                  }
//                   
//              }else{
//                dbg("inside DBTempSegmentService--->updateRecord-->tempsegmentmap contains file name and doesn't contains the  table name",p_session);
//                synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                 }
////                  tempSegmentMap.get(p_fileName).putIfAbsent(p_table_name,new HashMap());
////                   tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//              }
//                
//            }else{
//                dbg("inside DBTempSegmentService--->updateRecord-->tempsegmentmap doesn't contains file name ",p_session);
//                
//                    synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                       }
////                tempSegmentMap.putIfAbsent(p_fileName, new HashMap());
////                tempSegmentMap.get(p_fileName).putIfAbsent(p_table_name,new HashMap());
//////                DBRecord dbrec=new DBRecord(0,l_record,'C');
////                tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//            }
            
            
            
            dbg("end of DBTempSegmentService--->update Record",p_session);
               
        }catch(DBValidationException ex){
           throw ex;
         }catch(DBProcessingException ex){
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());
         }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//           if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
    
    }
//    @Lock(LockType.WRITE)
//    public  void updateRecord(String p_fileName, String p_fileType,String  p_table_name,String[] p_pkey,Map<String,String> p_column_to_update,CohesiveSession session) throws DBValidationException, DBProcessingException{
//        CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        
//        
//        updateColumn(p_fileName,p_fileType,p_table_name,p_pkey,p_column_to_update);
//        
//        
//    } catch(DBValidationException ex){
//           throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//           this.session=tempSession;
//     }
//    } 
    
//    @Lock(LockType.WRITE)
    public void deleteRecord(String p_fileName, String p_fileType, String p_table_name, String[] p_pkey,CohesiveSession p_session,DBSession p_dbSession) throws DBProcessingException, DBValidationException{
//        boolean l_session_created_now=false;
        try {
//            session.createSessionObject();
//            dbSession.createDBsession(session);
//            l_session_created_now=session.isI_session_created_now();
            IMetaDataService mds=dbdi.getMetadataservice();
            IDBReadBufferService readBuffer=dbdi.getDBReadBufferService();
//            ErrorHandler errorhandler=session.getErrorhandler();
            String l_primaryKey=p_dbSession.getIibd_file_util().formingPrimaryKey(p_pkey);
        
             
            DBRecord dbrec= readBuffer.readRecord(p_fileName, p_fileType, p_table_name, p_pkey, p_session,p_dbSession);
        
//          Iterator<String> keyIterator= p_column_to_update.keySet().iterator();
//           Iterator<String> valueIterator= p_column_to_update.values().iterator();
//           while(keyIterator.hasNext()){
//           String columnID= keyIterator.next();
         //  String value=p_column_to_update.get(columnID);
          // dbrec.getRecord().set(Integer.parseInt(columnID)-1, value);
//           if(dbrec.getOperation()!='C'){
           dbrec.setOperation('D');
           
//           }
//         }
                      long startTime=p_dbSession.getIibd_file_util(). getStartTime();
             //synchronized(lock1){
                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
               //     }
                        try{
                       p_dbSession.getIibd_file_util().logWaitBuffer(p_fileName,p_table_name,l_primaryKey,"DBTempSegmentService","deleteRecord.setRecordToTempSegment call",p_session,p_dbSession,dbdi,startTime);

                    }catch(Exception ex){
                      throw new DBProcessingException("Exception".concat(ex.toString()));
                    }
//            if(tempSegmentMap.containsKey(p_fileName)){
//                dbg("inside DBTempSegmentService--->deleteRecord-->tempsegmentmap contains file name",p_session);
//               if( tempSegmentMap.get(p_fileName).containsKey(p_table_name)){
//                dbg("inside DBTempSegmentService--->deleteRecord-->tempsegmentmap contains file name and table name",p_session);
//                  if(tempSegmentMap.get(p_fileName).get(p_table_name).containsKey(l_primaryKey)){
//                dbg("inside DBTempSegmentService--->deleteRecord-->tempsegmentmap contains file name and table name and primary key",p_session);
////                      StringBuffer single_error_code = new StringBuffer();
////                      single_error_code = single_error_code.append("DB_VAL_009");
////                      errorhandler.setSingle_err_code(single_error_code);
////                      errorhandler.log_error();
////                      throw new DBValidationException(errorhandler.getSession_error_code().toString()); 
////                      
////                   }else{
////                      DBRecord dbrec=new DBRecord(0,l_record,'C');
//                    synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                       }
////                      tempSegmentMap.get(p_fileName).get(p_table_name).replace(l_primaryKey, dbrec);
//                  }else{
//                dbg("inside DBTempSegmentService--->deleteRecord-->tempsegmentmap contains file name and table name  doesn't contains  primary key",p_session);
//                synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                }
////                      tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//                  }
//                   
//              }else{
//                dbg("inside DBTempSegmentService--->deleteRecord-->tempsegmentmap contains file name doesn't contains table name",p_session);
//                synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                }
////                  tempSegmentMap.get(p_fileName).putIfAbsent(p_table_name,new HashMap());
////                   tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//              }
//                
//            }else{
//                dbg("inside DBTempSegmentService--->deleteRecord-->tempsegmentmap doesn't contains file name ",p_session);
//                synchronized(lock1){
//                      setRecordToTempSegment(p_fileName,p_table_name,l_primaryKey,dbrec,p_session);
//                       }
////                tempSegmentMap.putIfAbsent(p_fileName, new HashMap());
////                tempSegmentMap.get(p_fileName).putIfAbsent(p_table_name,new HashMap());
//////                DBRecord dbrec=new DBRecord(0,l_record,'C');
////                tempSegmentMap.get(p_fileName).get(p_table_name).putIfAbsent(l_primaryKey, dbrec);
//            }
            
            
            
            
            
            dbg("end of DBTempSegmentService--->delete record",p_session);
               
        }catch(DBValidationException ex){
           throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());
         }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//           if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
        }
    
    
        
    }
//    @Lock(LockType.WRITE)
//    public void deleteRecord(String p_fileName, String p_fileType, String p_table_name, String[] p_pkey,CohesiveSession session) throws DBProcessingException, DBValidationException{
//        
//        CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        
//        
//        deleteRecord(p_fileName,p_fileType,p_table_name,p_pkey);
//        
//        
//    } catch(DBValidationException ex){
//           throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//           this.session=tempSession;
//     }
//        
//        
//        
//    }
    
//    private boolean capacityCheck()throws DBProcessingException{
//        
//        try{
//            
//            
//            
//            
//        
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }
//        
//        
//        
//        
//    }
    
//    @Lock(LockType.READ)
//    public Map<String, Map<String,DBRecord>>getFileFromTempSegment(String fileNameKey,CohesiveSession p_session,DBSession p_dBSession)throws  DBProcessingException{
     public  ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>getFileFromTempSegment(String fileNameKey,CohesiveSession p_session,DBSession p_dBSession)throws  DBProcessingException{

//      boolean l_session_created_now=false;
      ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> fileMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBTempSegment---->getFileFromTempSegment ",p_session);
        dbg("inside DBTempSegment---->getFileFromTempSegment--->fileNameKey "+fileNameKey,p_session);
        IBDFileUtil fileUtil=p_dBSession.getIibd_file_util();
//        Map<String,DBTempSegment>l_mapwithPosition=new HashMap();
        if(tempSegmentMap.containsKey(fileNameKey)){
        dbg("inside DBTempSegment---->getFileFromTempSegment--->temp segement map contains  fileNameKey",p_session);
            fileMap=tempSegmentMap.get(fileNameKey);
            
//           l_mapwithPosition= tempSegmentMap.get(fileNameKey);//Get the file map
//           
//           Iterator keyIterator=l_mapwithPosition.keySet().iterator();
//           
//           while(keyIterator.hasNext()){ //Iterating for each table Name
//               
//               
//               String l_tableName=(String)keyIterator.next();
//               DBTempSegment tempSegment=l_mapwithPosition.get(l_tableName);
//               fileMap.put(l_tableName, tempSegment.getRecord());
//           }
           
           
//           fileMap= tempSegmentMap.get(fileNameKey);
//          return fileUtil.cloneFile(fileMap);
           return fileMap;
        }else{
        dbg("inside DBTempSegment---->getFileFromTempSegment--->temp segement map doesn't contains  fileNameKey",p_session);
            return null;
        }
        
        
        
         
    }
      catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    }
    }
     
 public  DBRecord getRecordFromTempSegment(String fileNameKey,String pTableName,String pKey,CohesiveSession p_session,DBSession p_dBSession)throws  DBProcessingException{
//      boolean l_session_created_now=false;
    //  Map<String, Map<String,DBRecord>> fileMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBTempSegment---->getFileFromTempSegment ",p_session);
        dbg("inside DBTempSegment---->getFileFromTempSegment--->fileNameKey "+fileNameKey,p_session);
        dbg("inside DBTempSegment---->getFileFromTempSegment--->tableName"+pTableName,p_session);
        dbg("inside DBTempSegment---->getFileFromTempSegment--->pKey"+pKey,p_session);
//        Map<String,DBTempSegment>l_mapwithPosition=new HashMap();
        if(tempSegmentMap.containsKey(fileNameKey)){
        dbg("inside DBTempSegment---->getFileFromTempSegment--->temp segement map contains  fileNameKey",p_session);
            DBRecord dbrecOriginal=tempSegmentMap.get(fileNameKey).get(pTableName).get(pKey);
            DBRecord  dbrec= p_dBSession.getIibd_file_util().cloneRecord(dbrecOriginal);
//           l_mapwithPosition= tempSegmentMap.get(fileNameKey);//Get the file map
//           
//           Iterator keyIterator=l_mapwithPosition.keySet().iterator();
//           
//           while(keyIterator.hasNext()){ //Iterating for each table Name
//               
//               
//               String l_tableName=(String)keyIterator.next();
//               DBTempSegment tempSegment=l_mapwithPosition.get(l_tableName);
//               fileMap.put(l_tableName, tempSegment.getRecord());
//           }
           
           
//           fileMap= tempSegmentMap.get(fileNameKey);

           return dbrec;
        }else{
        dbg("inside DBTempSegment---->getFileFromTempSegment--->temp segement map doesn't contains  fileNameKey",p_session);
            return null;
        }
        
        
        
         
    }
      catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    }
 }     
public synchronized void removeRecordFromTempSegment(String fileNameKey,String pTableName,String pKey,CohesiveSession p_session,DBSession p_dBSession)throws  DBProcessingException,DBValidationException{
//      boolean l_session_created_now=false;
    //  Map<String, Map<String,DBRecord>> fileMap;
      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside DBTempSegment---->removeRecordFromTempSegment ",p_session);
        dbg("inside DBTempSegment---->removeRecordFromTempSegment--->fileNameKey "+fileNameKey,p_session);
        dbg("inside DBTempSegment---->removeRecordFromTempSegment--->pTableName "+pTableName,p_session);
        dbg("inside DBTempSegment---->removeRecordFromTempSegment--->pKey "+pKey,p_session);
//        Map<String,DBTempSegment>l_mapwithPosition=new HashMap();

          if(tempSegmentMap.containsKey(fileNameKey)){
              
              if(tempSegmentMap.get(fileNameKey).containsKey(pTableName)){
                  
                  if(tempSegmentMap.get(fileNameKey).get(pTableName).containsKey(pKey)){
                      
                      tempSegmentMap.get(fileNameKey).get(pTableName).remove(pKey);
                      
                      if(tempSegmentMap.get(fileNameKey).get(pTableName).isEmpty()){
                          
                          tempSegmentMap.get(fileNameKey).remove(pTableName);
                      }
                      
                      if(tempSegmentMap.get(fileNameKey).isEmpty()){
                          
                          tempSegmentMap.remove(fileNameKey);
                      }
                      
                  }else{
                      StringBuffer single_error_code = new StringBuffer();
                      String replacedPkey=pKey.replace("~", "@");
                      single_error_code = single_error_code.append("DB_VAL_018").append(","+fileNameKey).append(","+pTableName).append(","+replacedPkey);
                      //p_session.getErrorhandler().setSingle_err_code(single_error_code);//Integration changes
                      //p_session.getErrorhandler().log_error();
                      Exception ex= new Exception(single_error_code.toString());//Integration changes
                      dbg(ex,p_session);
                     // throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                      
                  }
                  
              }else{
                      StringBuffer single_error_code = new StringBuffer();
                      String replacedPkey=pKey.replace("~", "@");
                      single_error_code = single_error_code.append("DB_VAL_018").append(","+fileNameKey).append(","+pTableName);
                     // p_session.getErrorhandler().setSingle_err_code(single_error_code);
                      //p_session.getErrorhandler().log_error();
                      Exception ex= new Exception(single_error_code.toString());//Integration changes
                      dbg(ex,p_session);
                     // throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString());
              }
              
              
              
          }else{
                      StringBuffer single_error_code = new StringBuffer();
                      String replacedPkey=pKey.replace("~", "@");
                      single_error_code = single_error_code.append("DB_VAL_018").append(","+fileNameKey);
                     // p_session.getErrorhandler().setSingle_err_code(single_error_code);
                      //p_session.getErrorhandler().log_error();
                      //throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString());
                       Exception ex= new Exception(single_error_code.toString());//Integration changes
                       dbg(ex,p_session);    
          }

//        if(tempSegmentMap.get(fileNameKey).get(pTableName).containsKey(pKey)){
//        dbg("inside DBTempSegment---->removeRecordFromTempSegment--->temp segement map contains  fileNameKey",p_session);
//           
//            tempSegmentMap.get(fileNameKey).get(pTableName).remove(pKey);
//          
//            
//            
//        }else{
//        dbg("inside DBTempSegment---->removeRecordFromTempSegment--->temp segement map doesn't contains  fileNameKey",p_session);
//           
//        StringBuffer single_error_code = new StringBuffer();
//                               String replacedPkey=pKey.replace("~", "@");
//                           single_error_code = single_error_code.append("DB_VAL_018").append(","+fileNameKey).append(","+pTableName).append(","+replacedPkey);
//                           p_session.getErrorhandler().log_error();
//                               throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
//                          
//        
//        
//        }
        
        
        
         
    }
      /*catch(DBValidationException ex)
      {
          throw ex;
      }*/ //Integration changes
      catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
    }
      
      
      
//    @Lock(LockType.READ)
//     public Map<String, Map<String,DBRecord>>getFileFromTempSegment(String fileNameKey,CohesiveSession session)throws  DBProcessingException{
//         
//         CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        
//        
//      return   getFileFromTempSegment(fileNameKey);
//        
//        
////    } catch(DBValidationException ex){
////           throw ex;
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
//         }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//           this.session=tempSession;
//     }
//         
//         
//         
//         
//         
     }
//    
//    public Map<String, Map<String,ArrayList<String>>>getFileFromTempSegment(String fileNameKey,CohesiveSession session)throws  DBProcessingException{
//        CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        return getFileFromTempSegment(fileNameKey);
//    }catch (Exception ex){
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
//           this.session=tempSession;
//     }
//    }
//    public void setFileToTempSegment(String fileNameKey,Map<String, Map<String,ArrayList<String>>>fileMap)throws  DBProcessingException{
//      boolean l_session_created_now=false;
//      try{
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
//        dbg("inside DBTempSegment---->setFileToTempSegment ");
//        dbg("inside DBTempSegment---->setFileToTempSegment--->fileNameKey "+fileNameKey);
//        
//        tempSegmentMap.put(fileNameKey, fileMap);
//        
//        
//       }catch (Exception ex) {
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//        }finally{
//        if(l_session_created_now)
//            {    
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//            
//            }
//        }
//    }
//    
//    public void setFileToTempSegment(String fileNameKey,Map<String, Map<String,ArrayList<String>>>fileMap,CohesiveSession session)throws  DBProcessingException{
//        CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        setFileToTempSegment(fileNameKey,fileMap);
//    }catch (Exception ex){
//          dbg(ex);
//          throw new DBProcessingException("Exception" + ex.toString());
//     }finally{
//           this.session=tempSession;
//     }
//    }

//private String getFileType(String p_fileName,CohesiveSession p_session)throws DBProcessingException{
//       
//    try{    
//        dbg("inside tempSegment--->getFileType--->fileName--->"+p_fileName,p_session);
//        File file=new File(p_fileName);
//            File parentFile=new File(file.getParent());
//            String fileType=new File(parentFile.getParent()).getName();
//            
//            dbg("inside tempSegment--->getFileType--->"+fileType,p_session);
//            return fileType;
//        }catch (Exception ex) {
//            dbg(ex,p_session);
//            throw new DBProcessingException("Exception" + ex.toString());
//
//        } 
//       
//            
//    }
//
//private String getPKwithoutVersion(String p_fileName,String p_table_name,String p_primaryKey,CohesiveSession p_session)throws DBProcessingException,DBValidationException{
//        try{
//            boolean l_versionStatus=false;
//            IMetaDataService mds=dbdi.getMetadataservice();
//            dbg("inside getPKwithoutVersion--->",p_session);
//            String l_pk_without_version;
//            String l_fileType=getFileType(p_fileName,p_session);
//            Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(l_fileType, p_table_name,p_session).getI_ColumnCollection();
//            Iterator columnIterator=l_columnCollection.values().iterator();
//            while(columnIterator.hasNext()){
//             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
//             if(l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")){
//                 l_versionStatus=true;
//                 dbg("version status is true",p_session);
//             }else{
//                 l_versionStatus=false;
//                 dbg("version status is false",p_session);
//             }
//             if(l_versionStatus==true)
//                 break;
//            }
//            
//            if(l_versionStatus){
//              l_pk_without_version = p_primaryKey.substring(0, p_primaryKey.lastIndexOf("~"));
//            }else{
//              l_pk_without_version=  p_primaryKey;
//            }
//            dbg("end of getPKwithoutVersion--->l_pk_without_version"+l_pk_without_version,p_session);
//            return l_pk_without_version;
//        }catch(DBValidationException ex){
//            throw ex;
//        }catch (Exception ex) {
//            dbg(ex,p_session);
//            throw new DBProcessingException("Exception" + ex.toString());
//
//        }
//    }

public int getTempSegmentSize(CohesiveSession p_session)throws DBProcessingException{
    
    try{
    return tempSegmentMap.size();
    }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
}

public boolean checkFileExistenceInTempBuffer(String fileName,CohesiveSession p_session)throws DBProcessingException{
     boolean status=false;
     try{
         dbg("inside checkFileExistenceInWriteBuffer",p_session);
         if(tempSegmentMap.containsKey(fileName)){
             
             status=true;
         }
         
         
         dbg("end of checkFileExistenceInWriteBuffer--->status--->"+status,p_session);
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
     return status;
 }
 


     private void dbg(String p_Value,CohesiveSession p_session) {
        p_session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex,CohesiveSession p_session) {
        p_session.getDebug().exceptionDbg(ex);

    }
    
    
//    private void dbg(String p_Value) {
//        session.getDebug().dbg(p_Value);
//
//    }
//
//    private void dbg(Exception ex) {
//        session.getDebug().exceptionDbg(ex);
//
//    }
}
