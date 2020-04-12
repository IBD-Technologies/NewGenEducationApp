/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.tempSegment;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Local;

/**
 *
 * @author DELL
 */
@Local
public interface IDBTempSegmentService {
    
    void createRecord(CohesiveSession p_session,DBSession p_dbSession,String p_fileName, String p_fileType, int p_tableID,String p_primaryKey, String... p_record_values) throws DBValidationException, DBProcessingException;
//    void createRecord(CohesiveSession session,String p_fileName, String p_fileType, int p_tableID,String p_primaryKey, String... p_record_values) throws DBValidationException, DBProcessingException;
    public void updateColumn(String p_fileName, String p_fileType,String  p_table_name,String[] p_pkey,Map<String,String> p_column_to_update,CohesiveSession p_session,DBSession p_dbSession) throws DBValidationException, DBProcessingException;
//    public void updateColumn(String p_fileName, String p_fileType,String  p_table_name,String[] p_pkey,Map<String,String> p_column_to_update,CohesiveSession session) throws DBValidationException, DBProcessingException;
    public void updateRecord(String p_fileName, String p_fileType, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession p_session,DBSession p_dbSession) throws DBProcessingException, DBValidationException;
//    public void updateRecord(String p_fileName, String p_fileType,String  p_table_name,String[] p_pkey,Map<String,String> p_column_to_update,CohesiveSession session) throws DBValidationException, DBProcessingException;
    public void deleteRecord(String p_fileName, String p_fileType, String p_table_name, String[] p_pkey,CohesiveSession p_session,DBSession p_dbSession) throws DBProcessingException, DBValidationException;
//    public void deleteRecord(String p_fileName, String p_fileType, String p_table_name, String[] p_pkey,CohesiveSession session) throws DBProcessingException, DBValidationException;
//    public Map<String, Map<String,DBRecord>>getFileFromTempSegment(String fileNameKey,CohesiveSession session)throws  DBProcessingException;
//    public Map<String, Map<String,DBRecord>>getFileFromTempSegment(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException;
    public ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>getFileFromTempSegment(String fileNameKey,CohesiveSession p_session,DBSession p_dBSession)throws  DBProcessingException;
//    public Map<String, Map<String, Map<String, DBRecord>>> getTempSegmentMap() ;
    public Map<String, ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>>> getTempSegmentMap();
//    public Map<String, Map<String,ArrayList<String>>>getFileFromTempSegment(String fileNameKey)throws  DBProcessingException;
//    public void setFileToTempSegment(String fileNameKey,Map<String, Map<String,ArrayList<String>>>fileMap)throws  DBProcessingException;
//    public Map<String, Map<String,ArrayList<String>>>getFileFromTempSegment(String fileNameKey,CohesiveSession session)throws  DBProcessingException;
//    public void setFileToTempSegment(String fileNameKey,Map<String, Map<String,ArrayList<String>>>fileMap,CohesiveSession session)throws  DBProcessingException;

    public DBRecord getRecordFromTempSegment(String fileNameKey, String pTableName, String pKey, CohesiveSession p_session, DBSession p_dBSession) throws DBProcessingException;

    public void removeRecordFromTempSegment(String fileNameKey, String pTableName, String pKey, CohesiveSession p_session, DBSession p_dBSession) throws DBProcessingException,DBValidationException;
    
    public int getTempSegmentSize(CohesiveSession p_session)throws DBProcessingException;

    public boolean checkFileExistenceInTempBuffer(String fileName,CohesiveSession p_session)throws DBProcessingException;

}
