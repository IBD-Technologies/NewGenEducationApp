/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.student;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PAYMENT;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author IBD Technologies
 */
public class SVW_STUDENT_PAYMENT_DATASET {
//     ArrayList<SVW_STUDENT_PAYMENT> dataset;
    
    
    public ArrayList<SVW_STUDENT_PAYMENT> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside SVW_STUDENT_PAYMENT_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
         boolean ismaxVersionRequired=false;
        
        String maxVersionProperty=i_db_properties.getProperty("MAX_VERSION_REQUIRED");
        
        if(maxVersionProperty.equals("YES")){
            
            ismaxVersionRequired=true;
            
        }
        
        Map<String,DBRecord>l_studentPaymentsMap=null;
        try{
        l_studentPaymentsMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID,"STUDENT", "SVW_STUDENT_PAYMENT", session, dbSession,ismaxVersionRequired);
        }
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
        
         dbg("end of SVW_STUDENT_PAYMENT_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_studentPaymentsMap,session) ;
        
        
        
        
        
       
    
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
        
    }
    
    
    
    
    private ArrayList<SVW_STUDENT_PAYMENT> convertDBtoReportObject(Map<String,DBRecord>p_studentPaymentMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_PAYMENT>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_PAYMENT convertDBtoReportObject",session);
            
            if(p_studentPaymentMap!=null&&!p_studentPaymentMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_studentPaymentMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_paymentRecords=recordIterator.next();
                    SVW_STUDENT_PAYMENT studentPayment=new SVW_STUDENT_PAYMENT();
                    studentPayment.setSTUDENT_ID(l_paymentRecords.getRecord().get(0).trim());
                    studentPayment.setPAYMENT_ID(l_paymentRecords.getRecord().get(1).trim());
                    studentPayment.setPAYMENT_MODE(l_paymentRecords.getRecord().get(2).trim());
                    studentPayment.setPAYMENT_PAID(l_paymentRecords.getRecord().get(3).trim());
                    studentPayment.setPAYMENT_DATE(l_paymentRecords.getRecord().get(4).trim());
                    studentPayment.setFEE_ID(l_paymentRecords.getRecord().get(5).trim());
                    studentPayment.setFEE_TYPE(l_paymentRecords.getRecord().get(6).trim());
                    studentPayment.setAMOUNT(l_paymentRecords.getRecord().get(7).trim());
                    studentPayment.setOUT_STANDING(l_paymentRecords.getRecord().get(8).trim());
                    studentPayment.setREMARKS(l_paymentRecords.getRecord().get(9).trim());
                    studentPayment.setMAKER_ID(l_paymentRecords.getRecord().get(10).trim());
                    studentPayment.setCHECKER_ID(l_paymentRecords.getRecord().get(11).trim());
                    studentPayment.setMAKER_DATE_STAMP(l_paymentRecords.getRecord().get(12).trim());
                    studentPayment.setCHECKER_DATE_STAMP(l_paymentRecords.getRecord().get(13).trim());
                    studentPayment.setRECORD_STATUS(l_paymentRecords.getRecord().get(14).trim());
                    studentPayment.setAUTH_STATUS(l_paymentRecords.getRecord().get(15).trim());
                    studentPayment.setVERSION_NUMBER(l_paymentRecords.getRecord().get(16).trim());
                    studentPayment.setMAKER_REMARKS(l_paymentRecords.getRecord().get(17).trim());
                    studentPayment.setCHECKER_REMARKS(l_paymentRecords.getRecord().get(18).trim()); 
                    
              
                    dataset.add(studentPayment);
                    
                }
            
            }
            
                else
            {
                SVW_STUDENT_PAYMENT service=new SVW_STUDENT_PAYMENT();
                 
                    service.setSTUDENT_ID(" ");
                    service.setPAYMENT_ID(" ");
                    service.setPAYMENT_MODE(" ");
                    service.setPAYMENT_PAID(" ");
                    service.setPAYMENT_DATE(" ");
                    service.setFEE_ID(" ");
                    service.setFEE_TYPE(" ");
                    service.setAMOUNT(" ");
                    service.setOUT_STANDING(" ");
                    service.setREMARKS(" ");
                    service.setMAKER_ID(" ");
                    service.setCHECKER_ID(" ");
                    service.setMAKER_DATE_STAMP(" ");
                    service.setCHECKER_DATE_STAMP(" ");
                    service.setRECORD_STATUS(" ");
                    service.setAUTH_STATUS(" ");
                    service.setVERSION_NUMBER(" ");
                    service.setMAKER_REMARKS(" ");
                    service.setCHECKER_REMARKS(" ");
                                  
                                  
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of SVW_STUDENT_PAYMENT_DATASET convertDBtoReportObject",session);
            
        }catch(Exception ex){
            dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
       }
        
        return dataset;
        
    }
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
