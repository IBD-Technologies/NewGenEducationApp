/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.archivalshipping;

import com.ibd.businessViews.IArchShippingStatusUpdate;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Local(IArchShippingStatusUpdate.class)
@Stateless
public class ArchShippingStatusUpdate implements IArchShippingStatusUpdate{
     DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    
    public ArchShippingStatusUpdate() throws NamingException{
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
    
    
    public void statusUpdate(String request)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        String fileName=null;
        IDBTransactionService dbts=null;
        IBDProperties i_db_properties=null;
        String currentDate=null;
        IBDFileUtil fileUtil=null;
        ITransactionControlService tc=null;
        try{
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            i_db_properties=session.getCohesiveproperties();
            dbts=dbdi.getDBTransactionService();
            tc=dbdi.getTransactionControlService();
            fileUtil=dbSession.getIibd_file_util();
            
//            String fileName=request.getString("fileName");
//            String applyStatus=request.getString("applyStatus");
            fileName=request.split("~")[0];
            String applyStatus=request.split("~")[1];
            String[] l_pkey={fileName};
            Map<String,String>p_column_to_update=new HashMap();
            Date date = new Date();
            String dateformat="yyMMdd";
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
            currentDate=formatter.format(date);
            
            if(applyStatus.equals("P")){
              
              p_column_to_update.put("5", "P");
              
              try{
              
                  dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", "ARCH_SHIPPING_STATUS", l_pkey, p_column_to_update, session);
                //  tc.commit(session, dbSession);
              }catch(Exception e){
                dbg(e);
                //tc.rollBack(session, dbSession);
                throw new DBProcessingException("Exception"+e.toString());
              }
              
              
              
            }else{
                String exception=request.split("~")[2];
                p_column_to_update.put("5", "E");
                String exception1=null;
                if(exception.length()>200)
                  exception1=exception.substring(1,200);
                else
                   exception1= exception;
                
                p_column_to_update.put("6", exception1);
                
                try{
                
                    dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", "ARCH_SHIPPING_STATUS", l_pkey, p_column_to_update, session);
                   // tc.commit(session, dbSession);
               }catch(Exception e){
                 dbg(e);
                 //tc.rollBack(session, dbSession);
                 throw new DBProcessingException("Exception"+e.toString());
              }
                
                
            }
            
//        }catch(DBValidationException ex){
//            dbg(ex);
//            String[] l_pkey={fileName};
//            Map<String,String>p_column_to_update=new HashMap();
//            StringWriter sw = new StringWriter();
//            ex.printStackTrace(new PrintWriter(sw));
//            String exceptionAsString = sw.toString();
//            String l_replacedException=fileUtil.getReplacedException(exceptionAsString);
//            p_column_to_update.put("5", "E");
//            p_column_to_update.put("6", l_replacedException);
//            
//            try{
//            
//                dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", "ARCH_SHIPPING_STATUS", l_pkey, p_column_to_update, session);
//                tc.commit(session, dbSession);
//            }catch(Exception e){
//                dbg(e);
//                tc.rollBack(session, dbSession);
//                throw new DBProcessingException("Exception"+e.toString());
//            }
//
////            throw ex;
        }catch(Exception ex){
            dbg(ex);
            String[] l_pkey={fileName};
            Map<String,String>p_column_to_update=new HashMap();
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            String l_replacedException=fileUtil.getReplacedException(exceptionAsString);
            p_column_to_update.put("5", "E");
            
            
            String exception1=null;
                if(l_replacedException.length()>200)
                  exception1=l_replacedException.substring(1,200);
                else
                   exception1= l_replacedException;
                
                p_column_to_update.put("6", exception1);
           // p_column_to_update.put("6", l_replacedException);
            
            try{
            
                dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Primary","ARCH", "ARCH_SHIPPING_STATUS", l_pkey, p_column_to_update, session);
                //tc.commit(session, dbSession);
            }catch(Exception e){
                dbg(e);
                //tc.rollBack(session, dbSession);
            }   
//            throw new DBProcessingException("Exception"+ex.toString());
        }finally{
             try{
                   if(tc!=null)
                   {  
                      // dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", l_pkey, l_column_to_update, session);
                       tc.commit(session, dbSession);
                   }
                    }catch(Exception e){
                       dbg(e);
                       try{
                              tc.rollBack(session, dbSession);
                       }
                       catch(Exception j){
                           dbg(e);
                           
                       }
                      // throw new DBProcessingException("Exception"+e.toString());
                    }
 
            if(l_session_created_now){
                
                session.clearSessionObject();
                dbSession.clearSessionObject();
                
            }
            
        }
        
    }
    
    
//    public void statusUpdate(JsonObject p_request,CohesiveSession session)throws DBValidationException,DBProcessingException
//    {
//    CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        statusUpdate(p_request);
//     }catch(DBValidationException ex){
//         //dbg(ex);        
//        throw ex;
//        
//        }catch(DBProcessingException ex){
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch (Exception ex) {
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());   
//        }finally{
//           this.session=tempSession;
//        }
// }
    
    
    private void dbg(String p_Value) {
        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);

    }
}
