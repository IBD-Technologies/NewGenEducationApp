/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeDetail;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
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
 * @author DELL
 */
public class ClassFeeDetail_DataSet {
    
     public ArrayList<ClassFeeDetail> getClassOtherActivity(String standard,String section,String l_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<ClassFeeDetail>   dataset=new ArrayList();

        
        try{
        
        dbg("inside ClassFeeDetail_DataSet",session);     
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=appInject.getBusinessService(session);
        Map<String,DBRecord>instituteFeeMap=null;
        
        try{
        
        instituteFeeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"Fee","INSTITUTE","INSTITUTE_FEE_MANAGEMENT", session,dbSession);
   
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
        
        if(instituteFeeMap!=null){
        
        
            Map<String,List<DBRecord>>groupMap=instituteFeeMap.values().stream().filter(rec->rec.getRecord().get(11).trim().equals("O")&&rec.getRecord().get(12).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
            dbg("groupMap size"+groupMap.size(),session);
            
            Iterator<String>keyIterator=groupMap.keySet().iterator();

            while(keyIterator.hasNext()){

                String groupID=keyIterator.next();
                dbg("groupID"+groupID,session);

                if(bs.checkClassExistenceInTheGroup(l_instituteID, standard, section, groupID, session, dbSession, appInject)){

                    List<DBRecord>listForGroup=groupMap.get(groupID);

                    for(int i=0;i<listForGroup.size();i++){

                        dbg("listForGroup iteration ",session);
                        ArrayList<String>feeList=listForGroup.get(i).getRecord();
                        String feeID=feeList.get(2).trim();
                        String feeDescription=feeList.get(3).trim();
                        String feeType=feeList.get(4).trim();
                        String amount=feeList.get(5).trim();
                        String dueDate=feeList.get(6).trim();
                        dbg("feeID"+feeID,session);
                        dbg("feeDescription"+feeDescription,session);
                        dbg("feeType"+feeType,session);
                        dbg("amount"+amount,session);
                        dbg("dueDate"+dueDate,session);

                        ClassFeeDetail classFee=new ClassFeeDetail();
                        classFee.setFeeID(feeID);
                        classFee.setFeeType(feeType);
                        classFee.setFeeDescription(feeDescription);
                        int studentSize=bs.getStudentsOfTheGroup(l_instituteID, groupID, session, dbSession, appInject).size();
                        dbg("studentSize"+studentSize,session);
                        float totalFeeAmount=studentSize*Float.parseFloat(amount);
                        dbg("totalFeeAmount"+totalFeeAmount,session);
                        classFee.setFeeAmount(Float.toString(totalFeeAmount));
                        classFee.setDueDate(dueDate);

                        float totalPaymentAmount=0;
                        Map<String,DBRecord>paymentMap=null;

                        try{


                         paymentMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"FEE"+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID+i_db_properties.getProperty("FOLDER_DELIMITER")+feeID,"FEE", "INSTITITUTE_FEE_PAYMENT", session, dbSession);

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

                            dbg("paymentMap not null",session);
                            List<DBRecord>filteredRecords=paymentMap.values().stream().collect(Collectors.toList());
                            dbg("paymentMap filteredRecords"+filteredRecords.size(),session);
                            //check student existence in the class
                            for(int j=0;j<filteredRecords.size();j++){

                                   String studentID=filteredRecords.get(j).getRecord().get(0).trim();
                                   dbg("studentID"+studentID,session);
                                   if(bs.checkStudentExistenceInTheClass(l_instituteID, standard, section, studentID, session, dbSession, appInject)){

                                     dbg("studentID existing int the class"+studentID,session);
                                     float paidAmount=Float.parseFloat(filteredRecords.get(j).getRecord().get(3).trim());

                                     totalPaymentAmount=totalPaymentAmount+paidAmount;

                                   }

                            }
                            }
                            dbg("totalPaymentAmount"+totalPaymentAmount,session);
                            float balanceAmount=Float.parseFloat(classFee.getFeeAmount())-totalPaymentAmount;
                            dbg("balanceAmount"+balanceAmount,session);
                            classFee.setPaidAmount(Float.toString(totalPaymentAmount));
                            classFee.setBalanceAmount(Float.toString(balanceAmount));
                            dataset.add(classFee);

//                        }


                    }


                }


            }
        
        }
        
        if(dataset.isEmpty()){
            
            ClassFeeDetail classFee=new ClassFeeDetail();
            classFee.setFeeID(" ");
            classFee.setFeeType(" ");
            classFee.setFeeDescription(" ");
            classFee.setFeeAmount(" ");
            classFee.setDueDate(" ");
            classFee.setPaidAmount(" ");
            classFee.setBalanceAmount(" ");
            dataset.add(classFee);
        }
        
        
        
        dbg("end of ClassFeeDetail_DataSet",session); 
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
