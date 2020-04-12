/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.write;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Local;

/**
 *
 * @author DELL
 */
@Local
public interface IDBWriteBufferService {
   // public void modifyBufferFromTempSegment(String p_fileName,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
//     public void modifyBufferFromTempSegment(String p_fileName,CohesiveSession session)throws DBProcessingException;
// public Map<String, Map<String, Map<String, DBRecord>>> getWriteBuffer();
    public  Map<String,ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>> getWriteBuffer() ;
// public Map<String, Map<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException;
//    public ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>getFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException;
 public void SetRecordfromTempseg(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
 public void removeFileFromWriteBuffer(Map<String,Map<String,DBRecord>> l_fileMap,String p_fileName,CohesiveSession p_session)throws DBProcessingException;
 public int getWriteBufferSize(CohesiveSession p_session)throws DBProcessingException;
 public Map<String, Map<String,DBRecord>>getClonedFileFromWriteBuffer(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException;
 public void destroyWriteBuffer();
public boolean checkFileExistenceInWriteBuffer(String fileName,CohesiveSession p_session)throws DBProcessingException;
public boolean validateWriteBuffer(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
public  ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>>getClonedFileFromWriteBufferSync(String fileNameKey,CohesiveSession p_session,DBSession p_dbSession)throws  DBProcessingException;


}
