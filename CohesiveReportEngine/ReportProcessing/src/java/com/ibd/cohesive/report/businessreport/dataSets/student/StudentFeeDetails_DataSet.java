/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentFeeDetails;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author IBD Technologies
 */
public class StudentFeeDetails_DataSet {
    
    
    
    public ArrayList<StudentFeeDetails> getStudentFeeDetailsObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        ArrayList<StudentFeeDetails> dataset;
        
        try{
        dbg("inside getStudentFeeDetailsObject",session);
        dataset=new ArrayList();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IMetaDataService mds=inject.getMetadataservice();
        int recStatusColId=mds.getColumnMetaData("FEE", "INSTITUTE_FEE_MANAGEMENT", "RECORD_STATUS", session).getI_ColumnID()-1;
        int authStatusColId=mds.getColumnMetaData("FEE", "INSTITUTE_FEE_MANAGEMENT", "AUTH_STATUS", session).getI_ColumnID()-1;
        
        try{
        
            
            
                  
                Map<String,DBRecord>feeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","FEE", "STUDENT_FEE_MANAGEMENT", session, dbSession);
                 Map<String,DBRecord>instituteFeeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", session, dbSession);
                
                Map<String,List<DBRecord>>aauthorizedRecords=instituteFeeMap.values().stream().filter(rec->rec.getRecord().get(recStatusColId).trim().equals("O")&&rec.getRecord().get(authStatusColId).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
                
                Iterator<DBRecord>valueIterator=feeMap.values().iterator();
                while(valueIterator.hasNext()){

                    ArrayList<String>feeList=valueIterator.next().getRecord();
                    String feeID=feeList.get(1).trim();
                    
                    if(aauthorizedRecords.containsKey(feeID)){
                    
                    String[] l_pkey={feeID};
                    DBRecord feeManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", l_pkey, session,dbSession);
                    StudentFeeDetails feeDetail=new StudentFeeDetails();
                    feeDetail.setStudentID(p_studentID);
                    feeDetail.setFeeID(feeID);
                    feeDetail.setFeeType(feeManagementRecord.getRecord().get(4).trim());
                    feeDetail.setFeeAmount(feeManagementRecord.getRecord().get(5).trim());
                    feeDetail.setDueDate(feeManagementRecord.getRecord().get(6).trim());
                    float totalPaymentAmount=0;
                    Map<String,DBRecord>paymentMap=null;

                    try{


                     paymentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"PAYMENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Payment","PAYMENT", "STUDENT_PAYMENT", session, dbSession);

                    }catch(DBValidationException ex){
                                dbg("exception in view operation"+ex,session);
                                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
        //                            session.getErrorhandler().log_app_error("BS_VAL_013", l_feeID);
        //                            throw new BSValidationException("BSValidationException");

                                }else{

                                    throw ex;
                                }
                            }

                    if(paymentMap!=null){


                        List<DBRecord>filteredRecords=paymentMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals(feeID)).collect(Collectors.toList());


                        for(int i=0;i<filteredRecords.size();i++){

                                float paidAmount=Float.parseFloat(filteredRecords.get(i).getRecord().get(4).trim());

                                totalPaymentAmount=totalPaymentAmount+paidAmount;


                        }
                        }

                        float balanceAmount=Float.parseFloat(feeDetail.getFeeAmount())-totalPaymentAmount;

                        feeDetail.setPaidAmount(Float.toString(totalPaymentAmount));
                        feeDetail.setBalanceAmount(Float.toString(balanceAmount));
                        dataset.add(feeDetail);
                        
                    }

                }
         }catch(DBValidationException ex){

                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");

                }else{

                    throw ex;
                }


            }  
       
        
        if(dataset.isEmpty()){
            
            StudentFeeDetails feeDetail=new StudentFeeDetails();
            feeDetail.setStudentID(" ");
            feeDetail.setFeeID(" ");
            feeDetail.setFeeType(" ");
            feeDetail.setFeeAmount(" ");
            feeDetail.setDueDate(" ");
            feeDetail.setPaidAmount(" ");
            feeDetail.setBalanceAmount(" ");
            dataset.add(feeDetail);
        }
        
        

     dbg("end of getStudentFeeDetailsObject",session);
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
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
