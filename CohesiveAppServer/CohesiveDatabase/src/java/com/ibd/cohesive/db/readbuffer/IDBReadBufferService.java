/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.readbuffer;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author DELL
 */
@Local
public interface IDBReadBufferService {
    
    public Map<String, Map<String,DBRecord>>readFullFile(String fileName,String p_fileType,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException,DBValidationException;
//    public Map<String, Map<String,DBRecord>>readFullFile(String fileNameKey,String p_fileType,CohesiveSession session)throws  DBProcessingException,DBValidationException;
    public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException,DBValidationException;
//    public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession session)throws  DBProcessingException,DBValidationException;
//    public DBRecord readRecord(String fileName,String p_fileType,String p_tableName,String[] p_pkey,CohesiveSession session)throws  DBProcessingException,DBValidationException;
    public DBRecord readRecord(String fileName,String p_fileType,String p_tableName,String[] p_pkey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException,DBValidationException;
    public DBRecord readRecord(String fileName,String p_fileType,String p_tableName,String[] p_pkey,CohesiveSession p_session,DBSession p_dbSession,boolean p_notAppendError)throws  DBProcessingException,DBValidationException;
//    public Map<String, Map<String,DBRecord>>getFileFromBuffer(String fileNameKey,CohesiveSession session)throws DBValidationException, DBProcessingException;
     public Map<String, Map<String,DBRecord>>getFileFromBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException;
     public Map<String, DBFileContent> getReadBufferMap();
     //public void modifyBufferFromTempSegment(String p_fileName,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
//      public void modifyBufferFromTempSegment(String p_fileName,CohesiveSession session)throws DBProcessingException;
     public void SetRecordfromTempseg(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
     public int getReadBufferSize(CohesiveSession p_session)throws DBProcessingException;
     public Map<String, Map<String,DBRecord>>readFullFile(String fileName,String p_fileType,CohesiveSession p_session,DBSession p_dbSession,boolean maxVersionRequired)throws  DBProcessingException,DBValidationException;
     public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession p_session,DBSession p_dbSession,boolean maxVersionRequired)throws  DBProcessingException,DBValidationException;
     public Map<String,DBRecord>readTable(String fileName,String p_fileType,String p_tableName,CohesiveSession p_session,DBSession p_dbSession,String notAppendError)throws  DBProcessingException,DBValidationException;
     public void SetRecordfromArchivalWrite(String p_fileName,String p_tableName,String pKey,DBRecord l_tempSegRec,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
     public boolean validateDBReadBuffer(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
     public  void removeFileFromBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException;
     public  void setFromWriteBuffer(Map<String, Map<String,DBRecord>>l_writeBufferFileMap,String p_fileName,CohesiveSession p_session,DBSession p_dbSession )throws DBProcessingException;
     public boolean checkFileExistenceInReadBuffer(String fileName,CohesiveSession p_session)throws DBProcessingException;
}
