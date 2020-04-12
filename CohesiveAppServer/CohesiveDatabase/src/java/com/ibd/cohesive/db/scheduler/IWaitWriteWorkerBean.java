
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;

/**
 *
 * @author DELL
 */
public interface IWaitWriteWorkerBean {
     public void doTimerWork(CohesiveSession session) throws DBProcessingException,DBValidationException;
}
