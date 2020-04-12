/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.institute.FeeDetailBusiness;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
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
 * @author ibdtech
 */
public class FeeDetailBusinessDataSet {
    public ArrayList<FeeDetailBusiness> getFeeDetailBusiness(String standard,String section,String p_studentID,String p_feeID,String l_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<FeeDetailBusiness>   dataset=new ArrayList();

        
        try{
        
        dbg("inside FeeDetailBusiness_DataSet",session);     
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=appInject.getBusinessService(session);
        Map<String,DBRecord>instituteFeeMap=null;
        IPDataService pds=inject.getPdataservice();
        int s=1;
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
        
            
            
                   List<DBRecord>filterRecords= this.getFilteredFeeRecords(p_feeID, standard, section, p_studentID, l_instituteID, instituteFeeMap, session, appInject, dbSession);
            

                    for(int i=0;i<filterRecords.size();i++){

                        dbg("listForGroup iteration ",session);
                        ArrayList<String>feeList=filterRecords.get(i).getRecord();
                        String groupID=feeList.get(1).trim();
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
                        List<String>studentsForTheFee=this.getStudentsListForTheFee(groupID, standard, section, p_studentID, l_instituteID, session, appInject, dbSession);
//                        FeeDetailBusiness classFee=new FeeDetailBusiness();
//                        classFee.setFeeID(feeID);
//                        classFee.setFeeType(feeType);
//                        classFee.setFeeDescription(feeDescription);
//                      int studentSize=bs.getStudentsOfTheGroup(l_instituteID, groupID, session, dbSession, appInject).size();
//                      dbg("studentSize"+studentSize,session);
//                        float totalFeeAmount=this.getFeeAmountForTheFeeID(p_studentID, standard, section, groupID, l_instituteID, Float.parseFloat(amount), appInject, session, dbSession);
//                        dbg("totalFeeAmount"+totalFeeAmount,session);
//                        classFee.setFeeAmount(Float.toString(totalFeeAmount));
//                        classFee.setDueDate(dueDate);

//                        float totalPaymentAmount=0;
                        List<String>paidStudents=new ArrayList();
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
                            List<DBRecord>filteredRecords=this.getFilteredPaymentRecords(feeID, standard, section, p_studentID, l_instituteID, paymentMap, session, appInject, dbSession);
                            dbg("paymentMap filteredRecords"+filteredRecords.size(),session);
                            
                            Map<String,List<DBRecord>>studentWiseGroup=filteredRecords.stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(0).trim()));
                            Iterator<String>StudentIterator=studentWiseGroup.keySet().iterator();
                            
                            
//                            for(int j=0;j<filteredRecords.size();j++){

                            while(StudentIterator.hasNext()){

                                   String studentID=StudentIterator.next();
                                   dbg("studentID"+studentID,session);

                                   dbg("studentID existing int the class"+studentID,session);
                                   
                                   List<DBRecord>paymentRecordsForTheStudent=studentWiseGroup.get(studentID);
                                   float totalPaidAmount=0;
                                   for(int k=0;k<paymentRecordsForTheStudent.size();k++){

                                       DBRecord feePaymentRecord=paymentRecordsForTheStudent.get(k);
                                       float paidAmount=Float.parseFloat(feePaymentRecord.getRecord().get(3).trim());
                                       totalPaidAmount=totalPaidAmount+paidAmount;
                                    }       
                                   float balanceAmount=Float.parseFloat(amount)-totalPaidAmount;        
                                   FeeDetailBusiness classFee=new FeeDetailBusiness();
                                   classFee.setFeeID(feeID);
                                   classFee.setFeeType(feeType);
                                   classFee.setFeeDescription(feeDescription);
                                   classFee.setFeeAmount(amount);
                                   classFee.setDueDate(dueDate);
                                   classFee.setPaidAmount(Float.toString(totalPaidAmount));
                                   classFee.setBalanceAmount(Float.toString(balanceAmount));
                                   classFee.setStudentID(studentID);
                                   classFee.setStudentName(bs.getStudentName(studentID, l_instituteID, session, dbSession, appInject));
                                   String[] studentPkey = {studentID};
                                   ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                                   String l_standard = l_studentList.get(2).trim();
                                   String l_section = l_studentList.get(3).trim();
                                   classFee.setStandard(l_standard);
                                   classFee.setSection(l_section);
                                   classFee.setSerialNumber(Integer.toString(s));
                                   s++;
                                    dataset.add(classFee);
                                    paidStudents.add(studentID);
                            }
                            }
                           

                            for(String studentID:studentsForTheFee){
                                
                              if(!paidStudents.contains(studentID))  {
                                
                                float totalPaidAmount=0;
                                float balanceAmount=Float.parseFloat(amount)-totalPaidAmount;        
                                FeeDetailBusiness classFee=new FeeDetailBusiness();
                                
                                classFee.setStudentID(studentID);
                                classFee.setStudentName(bs.getStudentName(studentID, l_instituteID, session, dbSession, appInject));
                                classFee.setFeeID(feeID);
                                classFee.setFeeType(feeType);
                                classFee.setFeeDescription(feeDescription);
                                classFee.setFeeAmount(amount);
                                classFee.setDueDate(dueDate);
                                classFee.setPaidAmount(Float.toString(totalPaidAmount));
                                classFee.setBalanceAmount(Float.toString(balanceAmount));
                                String[] studentPkey = {studentID};
                                ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                                String l_standard = l_studentList.get(2).trim();
                                String l_section = l_studentList.get(3).trim();
                                classFee.setStandard(l_standard);
                                classFee.setSection(l_section);
                                classFee.setSerialNumber(Integer.toString(s));
                                   s++;
                                dataset.add(classFee);
                            }

                            }

                


            }
        
        }
        
        if(dataset.isEmpty()){
            
            FeeDetailBusiness classFee=new FeeDetailBusiness();
            classFee.setFeeID(" ");
            classFee.setFeeType(" ");
            classFee.setFeeDescription(" ");
            classFee.setFeeAmount(" ");
            classFee.setDueDate(" ");
            classFee.setPaidAmount(" ");
            classFee.setBalanceAmount(" ");
            classFee.setStudentID(" ");
            classFee.setStudentName(" ");
            classFee.setStandard(" ");
            classFee.setSection(" ");
            classFee.setSerialNumber(" ");
            dataset.add(classFee);
        }
        
        
        
        dbg("end of FeeDetailBusiness_DataSet",session); 
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
    
    
    
    
    
    
    
    
    
    
    
    private List<DBRecord>getFilteredFeeRecords(String feeID,String standard,String section,String studentID,String instituteID,Map<String,DBRecord>instituteFeeMap,CohesiveSession session,AppDependencyInjection appInject,DBSession dbSession)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
        
        try{
            
            BusinessService bs=appInject.getBusinessService(session);
            List<DBRecord>feeRecords=instituteFeeMap.values().stream().filter(rec->rec.getRecord().get(11).trim().equals("O")&&rec.getRecord().get(12).trim().equals("A")).collect(Collectors.toList());
            
            
            
            
            if(feeID!=null&&!feeID.isEmpty()){
            
             List<DBRecord>  l_studentList=  feeRecords.stream().filter(rec->rec.getRecord().get(2).trim().equals(feeID)).collect(Collectors.toList());
             feeRecords = new ArrayList<DBRecord>(l_studentList);
             dbg("l_feeID filter feeRecords size"+feeRecords.size(),session);
           }
            
            
            Iterator<DBRecord>feeRecordIterator=feeRecords.iterator();
            List<DBRecord> filteredRecords=new ArrayList();
            
            
            while(feeRecordIterator.hasNext()){
                
                
                DBRecord feeRecord=feeRecordIterator.next();
                String groupID=feeRecord.getRecord().get(1).trim();
                
                if(studentID!=null&&!studentID.isEmpty()){
                    
                    if(bs.checkStudentExistenceInTheGroup(instituteID, studentID, groupID, session, dbSession, appInject)){

                        filteredRecords.add(feeRecord);
                        
                        
                    }
                    
                    
                }else   if(standard!=null&&!standard.isEmpty()&&section!=null&&!section.isEmpty()){
                    
                    
                    if(bs.checkClassExistenceInTheGroup(instituteID, standard, section, groupID, session, dbSession, appInject)){

                        filteredRecords.add(feeRecord);
                        
                        
                    }
                    
                }else {
                    
                    filteredRecords.add(feeRecord);
                }
                
                
            }
            
            
            
            
            
            
            
            
            return filteredRecords;
            
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
        
    }
    
    private List<String>getStudentsListForTheFee(String groupID,String standard,String section,String studentID,String instituteID,CohesiveSession session,AppDependencyInjection appInject,DBSession dbSession)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
        
        try{
            
            BusinessService bs=appInject.getBusinessService(session);
            IPDataService pds=appInject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            
 
            List<String> studentsList=new ArrayList();
            
            
       
                
                if(studentID!=null&&!studentID.isEmpty()){
                    

                        studentsList.add(studentID);
                        
                        
                    
                    
                }else   if(standard!=null&&!standard.isEmpty()&&section!=null&&!section.isEmpty()){
                    
                    
                      Map<String,ArrayList<String>>studentMasterMap=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
        Map<String,List<ArrayList<String>>>l_studentGroup=studentMasterMap.values().stream().filter(rec->rec.get(2).trim().equals(standard)&&rec.get(3).trim().equals(section)&&rec.get(8).trim().equals("O")).collect(Collectors.groupingBy(rec->rec.get(0).trim()));
        Iterator<String>studentIterator=l_studentGroup.keySet().iterator();
                        
                        while(studentIterator.hasNext()){
                            
                            studentsList.add(studentIterator.next());
                        }
                        
                    
                }else {
                    
                    studentsList=bs.getStudentsOfTheGroup(instituteID, groupID, session, dbSession, appInject);
                }
                
                
            
            
            
            
            
            
            
            
            return studentsList;
            
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
        
    }
    
    
    
    private float getFeeAmountForTheFeeID(String studentID,String standard,String section,String groupID,String instituteID,float feeAmount,AppDependencyInjection appInject,CohesiveSession session,DBSession dbSession)throws BSProcessingException{
        float totalFeeAmount=0;
        try{
            BusinessService bs=appInject.getBusinessService(session);
            
            if(studentID!=null&&!studentID.isEmpty()){
                
                totalFeeAmount=feeAmount;
                
            }else if(standard!=null&&!standard.isEmpty()&&section!=null&&!section.isEmpty()){
                
               int noOfStudents= bs.getNoOfStudentsOfTheClass(instituteID, standard, section, session, dbSession, appInject);
                
                totalFeeAmount=feeAmount*noOfStudents;
            }else{
                int noOfStudents= bs.getStudentsOfTheGroup(instituteID, groupID, session, dbSession, appInject).size();
                
                totalFeeAmount=feeAmount*noOfStudents;
                
            }
            
            return totalFeeAmount;
            
        }catch(Exception ex){
         dbg(ex,session);
         throw new BSProcessingException("BSProcessingException"+ex.toString());
     }
        
        
    }
    
    
    
    private List<DBRecord>getFilteredPaymentRecords(String feeID,String standard,String section,String studentID,String instituteID,Map<String,DBRecord>institutePaymentMap,CohesiveSession session,AppDependencyInjection appInject,DBSession dbSession)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
        
        try{
            dbg("inside getFilteredPaymentRecords",session);
            BusinessService bs=appInject.getBusinessService(session);
            List<DBRecord>paymentRecords=institutePaymentMap.values().stream().collect(Collectors.toList());
            IPDataService pds=appInject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            
            
            Iterator<DBRecord>paymentRecordIterator=paymentRecords.iterator();
            List<DBRecord> filteredRecords=new ArrayList();
            dbg("studentID"+studentID,session);
            dbg("standard"+standard,session);
            dbg("section"+section,session);
            
            while(paymentRecordIterator.hasNext()){
                
                
                DBRecord paymentRecord=paymentRecordIterator.next();
                String l_studentID=paymentRecord.getRecord().get(0).trim();
                dbg("l_studentID"+l_studentID,session);
                
                if(studentID!=null&&!studentID.isEmpty()){
                    
                    if(l_studentID.equals(studentID)){

                        filteredRecords.add(paymentRecord);
                        
                        
                    }
                    
                    
                }else if(standard!=null&&!standard.isEmpty()&&section!=null&&!section.isEmpty()){
                    

                    String[] studentPkey = {l_studentID};
                    ArrayList<String> l_studentList = pds.readRecordPData(session, dbSession, "INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", studentPkey);
                    String l_standard = l_studentList.get(2).trim();
                    String l_section = l_studentList.get(3).trim();
                    
                    if(l_standard.equals(standard)&&l_section.equals(section)){
                    
                        filteredRecords.add(paymentRecord);
                        
                        
                    }
                    
                }else{
                    
                    filteredRecords.add(paymentRecord);
                }
                
                
            }
            
            
            
            
            
            
            
            dbg("end of getFilteredPaymentRecords",session);
            return filteredRecords;
            
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
        
    }
    
    
    
   
    
   
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
