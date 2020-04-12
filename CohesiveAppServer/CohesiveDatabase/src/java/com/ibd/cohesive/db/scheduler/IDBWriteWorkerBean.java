/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Local;

/**
 *
 * @author DELL
 */
@Local
public interface IDBWriteWorkerBean {
//    public void doTimerWork() throws DBProcessingException,DBValidationException;
//    public void doTimerWork(CohesiveSession session) throws DBProcessingException,DBValidationException;
    public void doTimerWork(CohesiveSession p_session,DBSession p_dbSession) throws DBProcessingException,DBValidationException ;
}
