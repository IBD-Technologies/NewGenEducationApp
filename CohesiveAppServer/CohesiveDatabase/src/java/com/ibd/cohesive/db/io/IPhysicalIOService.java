/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.io;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 */
@Local
public interface IPhysicalIOService {
    public  Map<String, Map<String,DBRecord>> physicalIORead(String p_fileName,String p_fileType)throws DBValidationException,DBProcessingException;
    public Future<String> physicalIOWrite(String p_fileName,Map<String,Map<String,DBRecord>>p_fileMap,UUID writeSessionID)throws DBValidationException,DBProcessingException;
    public  Map<String, Map<String,DBRecord>> physicalIORead(String p_fileName,String p_fileType,CohesiveSession p_session)throws DBValidationException,DBProcessingException;
    public  Future<String> physicalIOWrite(String p_fileName,Map<String,Map<String,DBRecord>>p_fileMap,CohesiveSession p_session,UUID writeSessionID)throws DBValidationException,DBProcessingException; 
    public boolean physicalIOWriteFromDestroy(String p_fileName,Map<String,Map<String,DBRecord>> p_fileMap,UUID writeSessionID)throws DBValidationException,DBProcessingException;
    public boolean getIOLock(String p_file_name,CohesiveSession session,DBSession dbSession)throws DBProcessingException;


}
