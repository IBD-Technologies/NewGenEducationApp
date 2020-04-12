/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.util;

//import com.ibd.cohesive.util.exception.BSProcessingException;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.JsonObject;

/**
 *
 * @author IBD Technologies
 */
public interface IIBDFileUtil {

    public PositionAndRecord sequentialRead(String p_file_name, int p_table_id, String[] p_pkey, Map<String, String> p_filter_column, CohesiveSession session,DBDependencyInjection dbdi) throws DBValidationException, DBProcessingException,FileNotFoundException;

    public String randomRead(String p_file_name, String p_table_name, int p_capacity_of_buffer, long p_position,CohesiveSession session,DBDependencyInjection dbdi) throws DBValidationException, DBProcessingException,FileNotFoundException;

    public void randomWrite(String p_file_name, long p_position, String p_record_to_be_written) throws DBValidationException, DBProcessingException;

    public void sequentialWrite(String p_file_name, String p_record_to_be_written) throws DBValidationException, DBProcessingException;

    public long getLastPositionOfaFile(String p_file_name) throws DBValidationException, DBProcessingException;
    
    public void copyFileToTemp(Path p_source_path,Path p_dest_path)throws DBProcessingException;
    
    public String getTempPath(String p_file_name)throws DBProcessingException;
    
    public void deleteTempFile(String p_file_name)throws DBProcessingException;
    
    public String formingPrimaryKey(String[] p_pkey);
    
//    public void printTempBuffer(Map<String,Map<String, Map<String,DBRecord>>> bufferMap)throws DBProcessingException;
    
    public void printTempBuffer(Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> bufferMap)throws DBProcessingException ;
    
    public void printDBBuffer( Map<String, Map<String,DBRecord>>tableMap)throws DBProcessingException;
    
    public String getPKwithoutVersion(String p_fileName,String p_table_name,String p_primaryKey,DBDependencyInjection p_dbdi,CohesiveSession p_session)throws DBProcessingException,DBValidationException;
    
     public String getFileType(String p_fileName)throws DBProcessingException;
     
     public DBRecord cloneRecord(DBRecord original)throws DBProcessingException ;
     
     public Map<String, DBRecord>cloneTable(Map<String, DBRecord> original)throws DBProcessingException;
     
     public  Map<String, Map<String, DBRecord>>cloneFile( ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>> original)throws DBProcessingException ;
     
     public String getCurrentTime()throws DBProcessingException;
     
      public long getStartTime()throws DBProcessingException;
      
      public void logWaitBuffer(String p_fileName,String p_tablName,String p_primaryKey,String className,String methodName,CohesiveSession session,DBSession dbSession,DBDependencyInjection dbdi,long startTime)throws DBProcessingException;
    //public JsonObject getJsonObject(String p_request)throws BSProcessingException;
      
//      public void printWriteBuffer(Map<String,Map<String, Map<String,DBRecord>>> bufferMap)throws DBProcessingException ;
    public void printWriteBuffer(Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> bufferMap)throws DBProcessingException;
    
    public ConcurrentHashMap<String,ArrayList<String>> createPdataConcurrentRecordMap(CohesiveSession session)throws DBProcessingException;
      
}
