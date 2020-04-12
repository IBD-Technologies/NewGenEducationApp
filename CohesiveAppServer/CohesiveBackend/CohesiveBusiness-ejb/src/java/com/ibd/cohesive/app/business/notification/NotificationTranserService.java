/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.notification;

import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Remote(INotificationTransferService.class)
@Stateless
public class NotificationTranserService implements INotificationTransferService{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
    public NotificationTranserService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession= new DBSession(session);
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }
        
    }
  
    
    public void emailNotificationProcessing(String emailID,String message,String notificationID,String studentID)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException {
        boolean l_session_created_now=false;
        try{
             session.createSessionObject();
             dbSession.createDBsession(session);
             l_session_created_now=session.isI_session_created_now();
             BusinessService bs=inject.getBusinessService(session);
             IDBTransactionService dbts=inject.getDBTransactionService();
             String folderDelimit=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
             String currentDate=bs.getCurrentDate();
             IMetaDataService mds=inject.getMetadataservice();
             ITransactionControlService tc=inject.getTransactionControlService();
             BatchUtil batch=inject.getBatchUtil(session);
             StringWriter sw = new StringWriter();
             
             dbg("inside emailNotificationProcessing");
             dbg("emailID"+emailID);
             dbg("message"+message);
             dbg("notificationID"+notificationID);
             dbg("studentID"+studentID);
             String currentDateTime=bs.getCurrentDateTime();

             int tableID=mds.getTableMetaData("NOTIFICATION", "EMAIL_NOTIFICATION_STATUS", session).getI_Tableid();
             dbts.createRecord(session,"NOTIFICATION"+folderDelimit+currentDate+folderDelimit+currentDate,"NOTIFICATION",tableID,notificationID,studentID,emailID,currentDateTime,"U"," ");             
             tc.commit(session, dbSession);
             
             
             
             try{
             
//                 sendEmail(emailID,message);

                 Map<String,String>l_column_to_update=new HashMap();
                 l_column_to_update.put("5", "P");
                 String[] l_pkey={notificationID,studentID};
                 dbts.updateColumn("NOTIFICATION"+folderDelimit+currentDate+folderDelimit+currentDate,"NOTIFICATION", "EMAIL_NOTIFICATION_STATUS", l_pkey, l_column_to_update);
             
                 tc.commit(session, dbSession);
             }catch(Exception ex){
                 ex.printStackTrace(new PrintWriter(sw));
                 String exceptionAsString = sw.toString();
                 String exception=batch.getReplacedException(exceptionAsString);
                 Map<String,String>l_column_to_update=new HashMap();
                 l_column_to_update.put("5", "E");
                 l_column_to_update.put("6", exception);
                 String[] l_pkey={notificationID,studentID};
                 dbts.updateColumn("NOTIFICATION"+folderDelimit+currentDate+folderDelimit+currentDate,"NOTIFICATION", "EMAIL_NOTIFICATION_STATUS", l_pkey, l_column_to_update);
                 tc.commit(session, dbSession);
             }
             
        }catch(DBValidationException ex){
                 
        throw ex;
        
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(BSProcessingException ex){
             dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
            
            if(l_session_created_now){
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
            
            
        }

    }
        
        public void smsNotificationProcessing(String mobileNo,String message,String notificationID,String studentID)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException {
        boolean l_session_created_now=false;
        try{
             session.createSessionObject();
             dbSession.createDBsession(session);
             l_session_created_now=session.isI_session_created_now();
             BusinessService bs=inject.getBusinessService(session);
             IDBTransactionService dbts=inject.getDBTransactionService();
             String folderDelimit=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
             String currentDate=bs.getCurrentDate();
             IMetaDataService mds=inject.getMetadataservice();
             ITransactionControlService tc=inject.getTransactionControlService();
             BatchUtil batch=inject.getBatchUtil(session);
             StringWriter sw = new StringWriter();
             
             dbg("inside smsNotificationProcessing");
             dbg("mobileNo"+mobileNo);
             dbg("message"+message);
             dbg("notificationID"+notificationID);
             dbg("studentID"+studentID);
            String currentDateTime=bs.getCurrentDateTime();

             int tableID=mds.getTableMetaData("NOTIFICATION", "SMS_NOTIFICATION_STATUS", session).getI_Tableid();
             dbts.createRecord(session,"NOTIFICATION"+folderDelimit+currentDate+folderDelimit+currentDate,"NOTIFICATION",tableID,notificationID,studentID,mobileNo,currentDateTime,"P"," ");             
             tc.commit(session, dbSession);
             
             
             
             try{
             
//                 sendSMS(mobileNo,message);

                 Map<String,String>l_column_to_update=new HashMap();
                 l_column_to_update.put("5", "P");
                 String[] l_pkey={notificationID,studentID};
                 dbts.updateColumn("NOTIFICATION"+folderDelimit+currentDate+folderDelimit+currentDate,"NOTIFICATION", "SMS_NOTIFICATION_STATUS", l_pkey, l_column_to_update);
             
                 tc.commit(session, dbSession);
             }catch(Exception ex){
                 ex.printStackTrace(new PrintWriter(sw));
                 String exceptionAsString = sw.toString();
                 String exception=batch.getReplacedException(exceptionAsString);
                 Map<String,String>l_column_to_update=new HashMap();
                 l_column_to_update.put("5", "E");
                 l_column_to_update.put("6", exception);
                 String[] l_pkey={notificationID,studentID};
                 dbts.updateColumn("NOTIFICATION"+folderDelimit+currentDate+folderDelimit+currentDate,"NOTIFICATION", "SMS_NOTIFICATION_STATUS", l_pkey, l_column_to_update);
                 tc.commit(session, dbSession);
             }
             
        }catch(DBValidationException ex){
                 
        throw ex;
        
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(BSProcessingException ex){
             dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
            
            if(l_session_created_now){
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
            
            
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }  
}
