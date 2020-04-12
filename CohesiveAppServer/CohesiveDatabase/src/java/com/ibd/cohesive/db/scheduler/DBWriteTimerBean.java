/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.EJB;
import javax.ejb.Lock;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Singleton
@ConcurrencyManagement(BEAN)
public class DBWriteTimerBean {
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    private IDBWriteWorkerBean workerBean;
   
     public DBWriteTimerBean() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
    
    @Lock(WRITE)
    @Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
    public void atSchedule() throws DBProcessingException,DBValidationException {
        boolean l_session_created_now=false;
        try{ 
        
         session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        String l_reportingDB=i_db_properties.getProperty("REPORTING_DB");
        
//        if(l_reportingDB.equals("NO")){
//           dbg("inside timer bean ");
           workerBean=dbdi.getDBWriteWorkBeanService();
           workerBean.doTimerWork(session,dbSession);
//           dbg("end of timer bean");
//        }
    }catch(DBValidationException ex){
           dbg(ex);
//            throw ex;
        }catch (DBProcessingException ex) {
            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());    
        }catch (Exception ex) {
            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());
       

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
