/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.ArchApplyException;
import com.ibd.cohesive.util.exceptions.ArchShippingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author IBD Tecnologies
 */
    @Singleton
public class ArchApplyTimerBean {
 DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    private IArchApplyWorkerBean workerBean;
    
    public ArchApplyTimerBean() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
     @Lock(READ)
    @Schedule(second = "*/2", minute = "*", hour = "*", persistent = false)
     public void atSchedule() throws ArchApplyException {
        boolean l_session_created_now=false;
        try{ 
        
         session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_reportingDB=i_db_properties.getProperty("REPORTING_DB");
        String l_archivalRequired= i_db_properties.getProperty("ARCHIVAL_REQUIRED");
        
        if(l_archivalRequired.equals("YES")){
       
        
            if(!l_reportingDB.equals("NO")){
               dbg("inside arch apply timer bean ");
               workerBean=dbdi.getArchApplyWorkerBean();
               workerBean.doTimerWork(session);
               dbg("end of arch apply timer bean");
            }
    
        }
        }catch (ArchApplyException ex) {
            dbg(ex);
//            throw new ArchApplyException("ArchApplyException" + ex.toString());    
        }catch (Exception ex) {
            dbg(ex);
//            throw new ArchApplyException("ArchApplyException" + ex.toString());

        } finally {
              if(l_session_created_now){
                
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
            }
        }  
    }
    
    private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
}
