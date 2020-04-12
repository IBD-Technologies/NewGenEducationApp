/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.db.archivalshipping.IArchivalShippingService;
import com.ibd.cohesive.db.io.IWaitWriteService;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.ArchShippingException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
@Singleton
public class ArchShippingWorkerBean implements IArchShippingWorkerBean{
    private AtomicBoolean busy = new AtomicBoolean(false);
    DBDependencyInjection dbdi;
    
    public ArchShippingWorkerBean() throws NamingException {
        dbdi = new DBDependencyInjection();
    }
    
     @Lock(READ)
    public void doTimerWork(CohesiveSession p_session) throws ArchShippingException{
        Properties batch=null;
        try{
            if (!busy.compareAndSet(false, true)) {
            return;
           }
            IArchivalShippingService archShipping=dbdi.getArchivalShippingService();
             batch  = new Properties();
            batch.load(new FileInputStream("/cohesive/dbhome/BATCH/batch.properties"));
            if (batch.getProperty("ARCH_SHIPPING_STOP").equals("NO"))
           {
            
            
            
            
            dbg("ArchivalShippingWorker started",p_session);
            archShipping.ArchShippingProcessing();
            dbg("ArchivalShippingWorker completed",p_session);
            
            
            
            
           }
//         busy.set(false);   
        }catch (ArchShippingException ex) {
            dbg(ex,p_session);
//            throw new ArchShippingException("ArchShippingException" + ex.toString());    
        }catch (Exception ex) {
            dbg(ex,p_session);
//            throw new ArchShippingException("ArchShippingException" + ex.toString());
       

        } finally {
            batch.clear();
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
