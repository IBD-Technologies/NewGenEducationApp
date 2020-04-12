/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.attendance;

import com.ibd.businessViews.IStudentAttendanceService;
import com.ibd.cohesive.app.business.batch.attendance.IStudentAttendanceProcessing;
import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.app.business.student.studentattendanceservice.PeriodAttendance;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Stateless
public class StudentAttendanceProcessing implements IStudentAttendanceProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    Map<String,String>filtermap_dummy;
    String filterkey_dummy;
    Map<String,String>audit_filtermap_dummy;
    String audit_filterkey_dummy;
    
     public StudentAttendanceProcessing() throws NamingException {
        inject = new AppDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
     
      public void processing (String instituteID,String studentID,String standard,String section,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
       BatchUtil batchUtil=null;
       boolean l_session_created_now=false;
       ITransactionControlService tc=null;
       try{
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();  
        dbg("inside student attendance processing ");
        dbg("instituteID"+instituteID);
        dbg("standard"+standard);
        dbg("section"+section);
        dbg("l_businessDate"+l_businessDate);
        dbg("studentID"+studentID);
        
        tc=inject.getTransactionControlService();
        BusinessService bs=inject.getBusinessService(session);
        String startTime=bs.getCurrentDateTime();
        batchUtil=inject.getBatchUtil(session);
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBTransactionService dbts=inject.getDBTransactionService();
            
                 //start time update starts
          Map<String,String>column_to_Update=new HashMap();
          column_to_Update.put("7", startTime);
          String[]l_pkey={instituteID,standard,section,studentID,l_businessDate};
          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_ATTENDANCE_BATCH_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          //start time update ends
          DBRecord studentAttendanceRec;
          boolean recordExistence=true;
          ConvertedDate convertedDate=bs.getYearMonthandDay(l_businessDate);
          String year=convertedDate.getYear();
          String month=convertedDate.getMonth();
          String day=convertedDate.getDay();
          
          try{
        
            String[]l_primaryKey={studentID,year,month};  
            studentAttendanceRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_ATTENDANCE", l_primaryKey, session, dbSession,true);
         }catch(DBValidationException ex){
            if(ex.toString().contains("DB_VAL_011")){
                
                dbg("printing error inside student attendance processing"+ex.toString());
                recordExistence=false;
                
            }else{
                throw ex;
            }
         }catch(Exception ex){
             
             dbg("printing error inside student attendance processing"+ex.toString());
             if(ex.toString().contains("DB_VAL_000")){
                
                recordExistence=false;
                
            }else{
                throw ex;
            }
             
             
         }
          
         dbg("recordExistence"+recordExistence); 
          if(!recordExistence){
              
              buildRequestAndCallStudentAttendance(studentID,standard,section,instituteID,year,month,day,l_businessDate,"C");
          }else{
              
              buildRequestAndCallStudentAttendance(studentID,standard,section,instituteID,year,month,day,l_businessDate,"U");
          }
          

         batchUtil.studentAttendanceProcessingSuccessHandler(instituteID, l_businessDate, standard,section, studentID, inject, session, dbSession);
        
         dbg("end of student attendance processing");
        }catch(DBValidationException ex){
          batchUtil.studentAttendanceProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentAttendanceProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentAttendanceProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentAttendanceProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
     }finally{
           filtermap_dummy=null;
           filterkey_dummy=null;
           audit_filtermap_dummy=null;
           audit_filterkey_dummy=null;
               if(l_session_created_now){    
                  dbSession.clearSessionObject();
                  session.clearSessionObject();
               }
           }
}

    public void processing(String instituteID,String studentID,String p_standard,String p_section,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           processing(instituteID,studentID,p_standard,p_section,l_businessDate);
       
      }catch(DBValidationException ex){
          throw ex;
      }catch(BSValidationException ex){
          throw ex;     
      }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(BSProcessingException ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
     }finally {
           this.session=tempSession;
            
        } 
   }
    
    
   
   
    private void buildRequestAndCallStudentAttendance(String l_studentID,String p_standard,String p_section,String instituteID,String year,String month, String day,String p_businessDate,String operation)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside buildRequestAndCallStudentAttendance") ;   
        IStudentAttendanceService stt=inject.getStudentAttendanceService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=instituteID;
        JsonObject studentAttendance;
        String l_msgID="";
        String l_correlationID="";
        String l_service="StudentAttendance";
        String l_operation="AutoAuth";
        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
                                                                                             .add("entityValue", l_studentID)).build();
        String l_userID="system";
        String l_source="cohesive_backend";
        String l_status=" ";
        
        String[] l_pkey={p_standard,p_section,year,month}; 
        DBRecord masterRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","CLASS_ATTENDANCE_MASTER", l_pkey, session, dbSession);

        String refID=p_standard+"*"+p_section+"*"+year+"*"+month;
        String[] l_primaryKey={refID,l_studentID}; 
        
        DBRecord detailRec= readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","CLASS_ATTENDANCE_DETAIL",l_primaryKey, session, dbSession);
        
        ClassAttendance classAttendance=getBOfromDB(masterRec,detailRec,day);
        
        String l_recordStatus=classAttendance.getRecordStatus();
        String l_authStatus=classAttendance.getAuthStatus();
        String l_makerRemarks=classAttendance.getMakerRemarks();
        String l_checkerRemarks=classAttendance.getCheckerRemarks();
        int p_versionNumber;
        if(operation.equals("C")){
            p_versionNumber=1 ;
        }else{
            p_versionNumber=Integer.parseInt(classAttendance.getVersionNo());
        }
        

        JsonObject body=getBodyFromBO(classAttendance,l_studentID,p_businessDate);
       


          studentAttendance=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
                                                        .add("msgID",l_msgID)
                                                        .add("correlationID", l_correlationID)
                                                        .add("service", l_service)
                                                        .add("operation", l_operation)
                                                        .add("businessEntity", l_businessEntity)
                                                        .add("instituteID", l_instituteID)
                                                        .add("status", l_status)
                                                        .add("source", l_source)
                                                        .add("userID",l_userID))
                                                        .add("body",body)
                                                        .add("auditLog",  Json.createObjectBuilder()
                                                        .add("makerID", "")
                                                        .add("checkerID", "")
                                                        .add("makerDateStamp", "")
                                                        .add("checkerDateStamp", "")
                                                        .add("recordStatus", l_recordStatus)
                                                        .add("authStatus", l_authStatus)
                                                        .add("versionNumber", Integer.toString(p_versionNumber))
                                                        .add("makerRemarks", l_makerRemarks)
                                                        .add("checkerRemarks", l_checkerRemarks)).build();
          
          dbg("studentAttendancerequest"+studentAttendance.toString());
          dbg("before  studentAttendance call");
          stt.processing(studentAttendance, session);
          dbg("after studentAttendance call");
          
          
          
          
          
          
        dbg("end of buildRequestAndCallStudentAttendance");  
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch(BSProcessingException ex){
            dbg(ex);
            throw new BSProcessingException(ex.toString());    
        }catch(BSValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
          
 }
    
    
   
    
    
    private ClassAttendance getBOfromDB(DBRecord p_ClassAttendanceRecord, DBRecord detailRecord,String day)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           
           ClassAttendance classAttendance=new ClassAttendance();
           
           
           String l_monthAudit=p_ClassAttendanceRecord.getRecord().get(4).trim();
           AuditDetails audit=getAuditDetails(l_monthAudit,day);
           classAttendance.setRecordStatus(audit.getRecordStatus());
           classAttendance.setAuthStatus(audit.getAuthStatus());
           classAttendance.setVersionNo(audit.getVersionNo());
           classAttendance.setMakerRemarks(audit.getMakerRemarks());
           classAttendance.setCheckerRemarks(audit.getCheckerRemarks());
            
           String monthAttendance=detailRecord.getRecord().get(2).trim();
           
                    getMaxVersionAttendanceOftheDay (monthAttendance,day) ; 
                    String max_version_Attendance=filtermap_dummy.get(day);
                    dbg("max_version_Attendance"+max_version_Attendance);
                    String[] dayArray=max_version_Attendance.split(",");
                    String attendanceRecord= dayArray[0];
                    dbg("attendanceRecord"+attendanceRecord);
                    String l_foreNoonAttendance=attendanceRecord.split("n")[0];
                    dbg("l_foreNoonAttendance"+l_foreNoonAttendance);
                    String l_afterNoonAttendance=attendanceRecord.split("n")[1];
                    dbg("l_afterNoonAttendance"+l_afterNoonAttendance);
                    String[] foreNoonArray=  l_foreNoonAttendance.split("p");
                    String[] afterNoonArray=  l_afterNoonAttendance.split("p");
                    
                    classAttendance.foreNoonAttendance=new PeriodAttendance[foreNoonArray.length-1];
                    
                    dbg("fore noon iterartion starts");
                    for(int i=1;i<foreNoonArray.length;i++){
                        
                         classAttendance.foreNoonAttendance[i-1]=new PeriodAttendance();
                         String periodNumber=foreNoonArray[i].substring(0, 1);
                         String attendance=foreNoonArray[i].substring(1);
                         dbg("periodNumber"+periodNumber);
                         dbg("attendance"+attendance);
                         classAttendance.foreNoonAttendance[i-1].setPeriodNumber(periodNumber);
                         classAttendance.foreNoonAttendance[i-1].setAttendance(attendance);
                    }
                    dbg("fore noon iterartion ends");
                    classAttendance.afterNoonAttendance=new PeriodAttendance[afterNoonArray.length-1];
                    dbg("after noon iterartion starts");
                    for(int i=1;i<afterNoonArray.length;i++){
                        
                         classAttendance.afterNoonAttendance[i-1]=new PeriodAttendance();
                         String periodNumber=afterNoonArray[i].substring(0, 1);
                         String attendance=afterNoonArray[i].substring(1);
                         dbg("periodNumber"+periodNumber);
                         dbg("attendance"+attendance);
                         classAttendance.afterNoonAttendance[i-1].setPeriodNumber(periodNumber);
                         classAttendance.afterNoonAttendance[i-1].setAttendance(attendance);
                    }
                    dbg("after noon iterartion ends");
                    
            
          dbg("end of  buildBOfromDB"); 
        return classAttendance;
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
   
    private JsonObject getBodyFromBO(ClassAttendance classAttendance,String studentID,String p_businessDate)throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside getBodyAndAudiFromBO");
        JsonArrayBuilder foreNoon=Json.createArrayBuilder();
        JsonArrayBuilder afterNoon=Json.createArrayBuilder();
        
           for(int i=0;i<classAttendance.foreNoonAttendance.length;i++){
                   
                   
                   foreNoon.add(Json.createObjectBuilder().add("periodNumber", classAttendance.foreNoonAttendance[i].getPeriodNumber())
                                                        .add("attendance", classAttendance.foreNoonAttendance[i].getAttendance()));
                                                     
           }
              
           for(int i=0;i<classAttendance.afterNoonAttendance.length;i++){
                   
                   
                   afterNoon.add(Json.createObjectBuilder().add("periodNumber", classAttendance.afterNoonAttendance[i].getPeriodNumber())
                                                        .add("attendance", classAttendance.afterNoonAttendance[i].getAttendance()));
                                                     
           }
              
                 
            body=Json.createObjectBuilder()
                                           .add("studentID", studentID)
                                           .add("date", p_businessDate)
                                           .add("foreNoon", foreNoon)
                                           .add("afterNoon", afterNoon)
                                           .build();
           
           
            
              dbg(body.toString());  
           dbg("end of getBodyAndAudiFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
    }
    
    private AuditDetails getAuditDetails(String monthAudit,String p_day)throws BSProcessingException{
    
    try{
      dbg("inside getAuditDetails");
      getMaxVersionAuditOftheDay(monthAudit,p_day);
      String existingDayAudit=audit_filtermap_dummy.get(p_day);
      String[] attArr=  existingDayAudit.split(",");
      AuditDetails audit =new AuditDetails();
      audit.setMakerID(attArr[1]);
      audit.setCheckerID(attArr[2]);
      audit.setMakerDateStamp(attArr[3]);
      audit.setCheckerDateStamp(attArr[4]);
      audit.setRecordStatus(attArr[5]);
      audit.setAuthStatus(attArr[6]);
      audit.setVersionNo(attArr[7]);
      audit.setMakerRemarks(attArr[8]);
      audit.setCheckerRemarks(attArr[9]);
        
      dbg("makerID"+audit.getMakerID());
      dbg("checkerID"+audit.getCheckerID());
      dbg("makerDateStamp"+audit.getMakerDateStamp());
      dbg("checkerDateStamp"+audit.getCheckerDateStamp());
      dbg("RecordStatus"+audit.getRecordStatus());
      dbg("AuthStatus"+audit.getAuthStatus());
      dbg("versionNumber"+audit.getVersionNo());
      dbg("makerRemarks"+audit.getMakerRemarks());
      dbg("checkerRemarks"+audit.getCheckerRemarks());
      
      dbg("end of getAuditDetails");
      return audit;
    }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException("BSProcessingException"+ex.toString());
    }
    
    
    
}
    private Map<String,String> getMaxVersionAuditOftheDay(String p_monthAudit,String p_day)throws BSProcessingException{
        
        try{
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_monthAttendance"+p_monthAudit);
            dbg("p_day"+p_day);
            String[] attendanceArray=p_monthAudit.split("d");
            dbg("attendanceArray"+attendanceArray.length);
            ArrayList<String>recordsFor_a_day=new ArrayList();
            for(int i=1;i<attendanceArray.length;i++){
                String dayRecord=attendanceArray[i];
                dbg("dayRecord"+dayRecord);
                
                String l_day=dayRecord.split(",")[0];
         
                dbg("l_day"+l_day);
                if(l_day.equals(p_day)){
                    
                    dbg("l_day.equals(p_day)");
                    recordsFor_a_day.add(dayRecord);
                }
                
            }
            dbg("recordsFor_a_day size"+recordsFor_a_day.size());
            audit_filtermap_dummy=new HashMap();
            if(recordsFor_a_day.size()>1){
                audit_filterkey_dummy=p_day;
                audit_filtermap_dummy=new HashMap();
                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[7])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[7])==max_vesion).forEach(rec->audit_filtermap_dummy.put(audit_filterkey_dummy, rec));           
                dbg("max_vesion"+max_vesion);
            }else{
                
                audit_filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
            }
            
            dbg("end of  getMaxVersionAttendanceOftheDay");
            return filtermap_dummy;
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
    }
    
    private Map<String,String> getMaxVersionAttendanceOftheDay(String p_monthAttendance,String p_day)throws BSProcessingException{
        
        try{
            dbg("inside getMaxVersionAttendanceOftheDay");
            dbg("p_monthAttendance"+p_monthAttendance);
            dbg("p_day"+p_day);
            String[] attendanceArray=p_monthAttendance.split("d");
            dbg("attendanceArray"+attendanceArray.length);
            ArrayList<String>recordsFor_a_day=new ArrayList();
            for(int i=1;i<attendanceArray.length;i++){
                String dayRecord=attendanceArray[i];
                dbg("dayRecord"+dayRecord);
                String l_day=null;
                
                String dayAttendance=dayRecord.split(",")[0];
         
                if(dayAttendance.contains(" ")){
                    
                    l_day=dayAttendance.split(" ")[0];
                }else if(dayAttendance.contains("p")){
                    
                    l_day=dayAttendance.split("p")[0];
                }
                
                dbg("l_day"+l_day);
                if(l_day.equals(p_day)){
                    
                    dbg("l_day.equals(p_day)");
                    recordsFor_a_day.add(dayRecord);
                }
                
            }
            dbg("recordsFor_a_day size"+recordsFor_a_day.size());
            filtermap_dummy=new HashMap();
            if(recordsFor_a_day.size()>1){
                filterkey_dummy=p_day;
                filtermap_dummy=new HashMap();
                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[1])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[1])==max_vesion).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));           
                dbg("max_vesion"+max_vesion);
            }else{
                
                filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
            }
            
            dbg("end of  getMaxVersionAttendanceOftheDay");
            return filtermap_dummy;
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
    }
    
    @Asynchronous
   public Future<String> parallelProcessing(String instituteID,String studentID,String standard,String section,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
   
    try{   
    
        processing(instituteID,studentID,standard,section,l_businessDate);
        
              return new AsyncResult<String>("Success");

       
        }catch(Exception ex){
           dbg(ex);
           return new AsyncResult<String>("Fail");
     }
    
}
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
}
