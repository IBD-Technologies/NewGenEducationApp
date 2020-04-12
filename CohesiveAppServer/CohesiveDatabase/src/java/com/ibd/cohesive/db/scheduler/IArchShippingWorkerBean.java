/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.util.exceptions.ArchShippingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Local;

/**
 *
 * @author DELL
 */
@Local
public interface IArchShippingWorkerBean {
    public void doTimerWork(CohesiveSession p_session) throws ArchShippingException;
}
