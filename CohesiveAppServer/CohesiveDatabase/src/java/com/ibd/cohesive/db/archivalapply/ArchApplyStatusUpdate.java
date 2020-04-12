/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.archivalapply;

import com.ibd.businessViews.IArchApplyStatusUpdate;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
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
@Local(IArchApplyStatusUpdate.class)
@Stateless
public class ArchApplyStatusUpdate implements IArchApplyStatusUpdate{
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    
    public ArchApplyStatusUpdate() throws NamingException{
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
    
    
//    public JsonObject statusUpdate(JsonObject request)throws DBProcessingException,DBValidationException{
    public String statusUpdate(String request)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        ITransactionControlService tc=null;
        try{
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            dbg("inside ArchApplyStatusUpdate");
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBTransactionService dbts=dbdi.getDBTransactionService();
            IBDFileUtil fileUtil=dbSession.getIibd_file_util();
             tc=dbdi.getTransactionControlService();
            String receivedStatus;
            String checkSumStatus;
            IMetaDataService mds=dbdi.getMetadataservice();
            Date date = new Date();
            String dateformat="yyMMdd";
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
            String currentDate=formatter.format(date);
            int tableId=mds.getTableMetaData("ARCH", "ARCH_APPLY_STATUS", session).getI_Tableid();
            
            
            String fileName=request.split("~")[0];
            String sequenceNo=request.split("~")[1];
            String shippingCheckSum=request.split("~")[2];
            
          /*  try{
            
                dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", tableId, fileName,sequenceNo," "," ","I"," ");
                //tc.commit(session, dbSession);
            }catch(Exception e){
                dbg(e);
                //tc.rollBack(session, dbSession);
                throw new DBProcessingException("Exception"+e.toString());
            }*/
            
            
            Path ArchApplyFolderPath=Paths.get(i_db_properties.getProperty("ARCH_APPLY_FOLDER")+i_db_properties.getProperty("FOLDER_DELIMITER")+fileName);
            if(Files.exists(ArchApplyFolderPath)){
                
                receivedStatus="Y";
            }else{
                receivedStatus="N";
            }
            dbg("inside ArchApplyStatusUpdate-->receivedStatus"+receivedStatus);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256"); //SHA, MD2, MD5, SHA-256, SHA-384...
            String applyCheckSum = fileUtil.checksum(i_db_properties.getProperty("ARCH_APPLY_FOLDER")+i_db_properties.getProperty("FOLDER_DELIMITER")+fileName, md);
            if(shippingCheckSum.equals(applyCheckSum)){
                
                checkSumStatus="Y";
            }else{
                
                checkSumStatus="N";
            }
            dbg("inside ArchApplyStatusUpdate-->checkSumStatus"+checkSumStatus);
            Map<String,String>l_column_to_update=new HashMap();
            String[] l_pkey={fileName};
            
            if(checkSumStatus.equals("Y")&&receivedStatus.equals("Y")){
            
//                    dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", tableId, fileName,sequenceNo,checkSumStatus,receivedStatus,"U"," ");
                 /*  l_column_to_update.put("3", checkSumStatus);
                   l_column_to_update.put("4", receivedStatus);
                   l_column_to_update.put("5", "U");
                   
                  /*  try{
                   
                       dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", l_pkey, l_column_to_update, session);
                      // tc.commit(session, dbSession);
                    }catch(Exception e){
                       dbg(e);
                      // tc.rollBack(session, dbSession);
                       throw new DBProcessingException("Exception"+e.toString());
                    }*/
                  
              try{
            
                dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", tableId, fileName,sequenceNo,checkSumStatus,receivedStatus,"U"," ");
                //tc.commit(session, dbSession);
            }catch(Exception e){
                dbg(e);
                //tc.rollBack(session, dbSession);
                throw new DBProcessingException("Exception"+e.toString());
            }
               
                 
                   
                   
            }else{
                    
               /* l_column_to_update.put("3", checkSumStatus);
                l_column_to_update.put("4", receivedStatus);
                l_column_to_update.put("5", "E");
                
                try{
                
                    dbts.updateColumn("DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", "ARCH_APPLY_STATUS", l_pkey, l_column_to_update, session);
                   // tc.commit(session, dbSession);
                }catch(Exception e){
                    dbg(e);
                    tc.rollBack(session, dbSession);
                    
                    throw new DBProcessingException("Exception"+e.toString());
                } */
               
                      try{
            
                dbts.createRecord(session,"DB"+i_db_properties.getProperty("FOLDER_DELIMITER")+"ARCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+currentDate+i_db_properties.getProperty("FOLDER_DELIMITER")+"Standby","ARCH", tableId, fileName,sequenceNo,checkSumStatus,receivedStatus,"E"," ");
                //tc.commit(session, dbSession);
            }catch(Exception e){
                dbg(e);
                //tc.rollBack(session, dbSession);
                throw new DBProcessingException("Exception"+e.toString());
            }
               
            }
            
//            JsonObject response=Json.createObjectBuilder().add("checkSumStatus", checkSumStatus).add("fileReceivedStatus", receivedStatus).build();
            
            String response=checkSumStatus+"~"+receivedStatus;
            
            dbg("end of ArchApplyStatusUpdate");
          return response;
        }catch(DBValidationException ex){
            dbg(ex);
             return "E"+"~"+"E";
            
//            throw ex;
        }catch(Exception ex){
            dbg(ex);
            return "E"+"~"+"E";
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
    
    
    
//    public JsonObject statusUpdate(JsonObject p_request,CohesiveSession session)throws DBValidationException,DBProcessingException
//    {
//    CohesiveSession tempSession = this.session;
//    try{
//        this.session=session;
//        return statusUpdate(p_request);
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
