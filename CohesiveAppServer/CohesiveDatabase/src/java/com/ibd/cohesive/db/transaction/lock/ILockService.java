/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.transaction.lock;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.UUID;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 */
@Local
public interface ILockService {
   
    public  boolean createLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public  boolean isLocked(String p_fileName,CohesiveSession session)throws DBProcessingException;
    
    public  boolean isSameSessionLock(String p_fileName,CohesiveSession session)throws DBProcessingException;
    
    public void releaseLock(String p_fileName,CohesiveSession session)throws DBProcessingException;
    
    public boolean createImplictRecordLock(String p_fileName,String p_table_id,String p_primary_key,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public boolean isSameSessionRecordLock(String p_fileName,String p_table_Name,String p_primary_key,CohesiveSession session)throws DBProcessingException;
    
    public ArrayList<String> getImplictLockedRecords(CohesiveSession session,DBSession dbSession)throws DBProcessingException;
 
    public void releaseRecordLock(String p_fileName,String p_tableName,String p_pkey,CohesiveSession session)throws DBProcessingException;

    public boolean createIOLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
//    public boolean getIOLock(String p_file_name,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public boolean isSameSessionIOLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public void releaseIOLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public boolean createArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public boolean getArchivalLock(String p_file_name,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public void releaseArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public boolean isSameSessionArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
    public  long getArchivalSequenceNo(); 
    
    public long getSequenceNo();
    
    public void releaseSessionRecordLock(CohesiveSession session)throws DBProcessingException;
    
    public  boolean createArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession,UUID writeSessionIdentifier)throws DBProcessingException;
    
    public ArrayList<String> getArchivalFileNames(UUID p_sessionId)throws DBProcessingException;
}
