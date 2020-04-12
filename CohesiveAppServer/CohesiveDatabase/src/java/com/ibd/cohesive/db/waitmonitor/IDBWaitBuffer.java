/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.waitmonitor;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 */
@Local
public interface IDBWaitBuffer {
    public void putRecordToWaitBuffer(String p_sessionID,DBWait dbWait,CohesiveSession p_session)throws DBProcessingException;
    public int getWaitBufferSize(CohesiveSession p_session)throws DBProcessingException;
    public Map<String,ArrayList<DBWait>> getWaitBuffer();
    public void removeFromWaitBuffer(String sessionID,CohesiveSession p_session)throws DBProcessingException;
}
