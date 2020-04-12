/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.db.io.IWaitWriteService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Singleton
public class WaitWriteWorkerBean implements IWaitWriteWorkerBean {
     private AtomicBoolean busy = new AtomicBoolean(false);
    DBDependencyInjection dbdi;
    
    public WaitWriteWorkerBean() throws NamingException {
        dbdi = new DBDependencyInjection();
    }
    
    @Lock(READ)
    public void doTimerWork(CohesiveSession p_session) throws DBProcessingException,DBValidationException {
        
        try{
            if (!busy.compareAndSet(false, true)) {
            return;
           }
            IWaitWriteService waitWrite=dbdi.getWaitWriteService();
            
            dbg("waitWriteWorker started",p_session);
            waitWrite.waitWrite();
            dbg("waitWriteWorker completed",p_session);
//         busy.set(false);   
        }catch (DBProcessingException ex) {
            dbg(ex,p_session);
//            throw new DBProcessingException("Exception" + ex.toString());    
        }catch (Exception ex) {
            dbg(ex,p_session);
//            throw new DBProcessingException("Exception" + ex.toString());
       

        } finally {
           busy.set(false);
        }  
        
        
    }
    
     private void dbg(String p_Value,CohesiveSession p_session) {
        p_session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex,CohesiveSession p_session) {
        p_session.getDebug().exceptionDbg(ex);

    }
}
