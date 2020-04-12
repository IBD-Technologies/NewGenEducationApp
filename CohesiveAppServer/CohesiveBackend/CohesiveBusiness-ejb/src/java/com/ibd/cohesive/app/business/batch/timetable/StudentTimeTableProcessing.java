/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.timetable;

import com.ibd.businessViews.IStudentTimeTableService;
import com.ibd.cohesive.app.business.util.BatchUtil;
import com.ibd.cohesive.app.business.util.BusinessService;
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
public class StudentTimeTableProcessing implements IStudentTimeTableProcessing{
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
     public StudentTimeTableProcessing() throws NamingException {
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
        dbg("inside student timeTable processing ");
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
          dbts.updateColumn("BATCH"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate+i_db_properties.getProperty("FOLDER_DELIMITER")+l_businessDate, "BATCH", "STUDENT_TIMETABLE_BATCH_STATUS", l_pkey, column_to_Update,session); 
          tc.commit(session, dbSession);
          //start time update ends
          DBRecord studentTimeTableRec;
          String versionNumber=null;
          boolean recordExistence=true;
          
          try{
        
            String[]l_primaryKey={studentID};  
            studentTimeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_TIMETABLE_MASTER", l_primaryKey, session, dbSession,true);
            versionNumber=studentTimeTableRec.getRecord().get(4).trim();
            dbg("versionNumber"+versionNumber);
         }catch(DBValidationException ex){
            if(ex.toString().contains("DB_VAL_011")){
                
                dbg("printing error inside student fee processing"+ex.toString());
                recordExistence=false;
                
            }else{
                throw ex;
            }
         }catch(Exception ex){
             
             dbg("printing error inside student fee processing"+ex.toString());
             if(ex.toString().contains("DB_VAL_000")){
                
                recordExistence=false;
                
            }else{
                throw ex;
            }
             
             
         }
          
         dbg("recordExistence"+recordExistence); 
          if(!recordExistence){
              
              buildRequestAndCallStudentTimeTable(studentID,standard,section,1,instituteID);
          }else{
              
              buildRequestAndCallStudentTimeTable(studentID,standard,section,Integer.parseInt(versionNumber)+1,instituteID);
          }
          

         batchUtil.studentTimeTableProcessingSuccessHandler(instituteID, l_businessDate, standard,section, studentID, inject, session, dbSession);
        
         dbg("end of student timeTable processing");
        }catch(DBValidationException ex){
          batchUtil.studentTimeTableProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
        }catch(BSValidationException ex){
          batchUtil.studentTimeTableProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
      
        }catch(DBProcessingException ex){
          dbg(ex);
          batchUtil.studentTimeTableProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
        }catch(Exception ex){
           dbg(ex);
           batchUtil.studentTimeTableProcessingErrorHandler(instituteID, l_businessDate, standard,section, studentID, ex, inject, session, dbSession);
     }finally{
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
    
    
   
   
    private void buildRequestAndCallStudentTimeTable(String l_studentID,String p_standard,String p_section,int p_versionNumber,String instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{  
     
        try{
        dbg("inside buildRequestAndCallStudentTimeTable") ;   
        dbg("versionNumber"+p_versionNumber);
        IStudentTimeTableService stt=inject.getStudentTimeTableService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String l_instituteID=instituteID;
        JsonObject studentFee;
        String l_msgID="";
        String l_correlationID="";
        String l_service="StudentTimeTable";
        String l_operation="AutoAuth";
        JsonArray l_businessEntity=Json.createArrayBuilder().add(Json.createObjectBuilder().add("entityName", "studentID")
                                                                                             .add("entityValue", l_studentID)).build();
        String l_userID="system";
        String l_source="cohesive_backend";
        String l_status=" ";
        
        String[] l_pkey={p_standard,p_section};
        DBRecord timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","CLASS_TIMETABLE_MASTER", l_pkey, session, dbSession);
        Map<String,DBRecord>detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS","CLASS_TIMETABLE_DETAIL", session, dbSession);
        
        ClassTimeTable classTimeTable=getBOfromDB(timeTableRec,detailMap);
        
        String l_recordStatus=classTimeTable.getRecordStatus();
        String l_authStatus=classTimeTable.getAuthStatus();
        String l_makerRemarks=classTimeTable.getMakerRemarks();
        String l_checkerRemarks=classTimeTable.getCheckerRemarks();

        JsonObject body=getBodyFromBO(classTimeTable,l_studentID,p_versionNumber);
       


          studentFee=Json.createObjectBuilder().add("header", Json.createObjectBuilder()
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
          
          dbg("studentFeerequest"+studentFee.toString());
          dbg("before  studentFee call");
          stt.processing(studentFee, session);
          dbg("after studentFee call");
          
          
          
          
          
          
        dbg("end of buildRequestAndCallStudentTimeTable");  
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
    
    
   
    
    
    private ClassTimeTable getBOfromDB(DBRecord p_ClassTimeTableRecord, Map<String,DBRecord>detailMap)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB"); 
           ClassTimeTable ClassTimeTable =new ClassTimeTable();
           ArrayList<String>l_ClassTimeTableList= p_ClassTimeTableRecord.getRecord();
           
           if(l_ClassTimeTableList!=null&&!l_ClassTimeTableList.isEmpty()){
               
              ClassTimeTable.setRecordStatus(l_ClassTimeTableList.get(6).trim());
              ClassTimeTable.setAuthStatus(l_ClassTimeTableList.get(7).trim());
              ClassTimeTable.setMakerRemarks(l_ClassTimeTableList.get(9).trim());
              ClassTimeTable.setCheckerRemarks(l_ClassTimeTableList.get(10).trim());
            }
           
            Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
            ClassTimeTable.timeTable=new TimeTable[dayWiseGroup.keySet().size()];
            Iterator<String> dayIterator=dayWiseGroup.keySet().iterator();
            int i=0;
            
            while(dayIterator.hasNext()){
                String day=dayIterator.next();
                ClassTimeTable.timeTable[i]=new TimeTable();
                ClassTimeTable.timeTable[i].setDay(day);
                dbg("day"+day);
                dbg("dayWiseGroup.get(day).size()"+dayWiseGroup.get(day).size());
                ClassTimeTable.timeTable[i].period=new Period[dayWiseGroup.get(day).size()];
                int j=0;
                for(DBRecord periodRecords: dayWiseGroup.get(day)){
                   ClassTimeTable.timeTable[i].period[j]=new Period();
                   ClassTimeTable.timeTable[i].period[j].setPeriodNumber(periodRecords.getRecord().get(3).trim());
                   ClassTimeTable.timeTable[i].period[j].setSubjectID(periodRecords.getRecord().get(4).trim());
                   ClassTimeTable.timeTable[i].period[j].setTeacherID(periodRecords.getRecord().get(5).trim());
                  j++;
                  dbg("periodArraySize"+j);
                }
                
                i++;
            }
            
            
          dbg("end of  buildBOfromDB"); 
        return ClassTimeTable;
        }catch(Exception ex){
           dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
   
    private JsonObject getBodyFromBO(ClassTimeTable classTimeTable,String studentID,int p_versionNumber)throws BSProcessingException{
        JsonObject body;
        try{
        dbg("inside getBodyAndAudiFromBO");
        BusinessService bs=inject.getBusinessService(session);
        JsonArrayBuilder period=Json.createArrayBuilder();
        JsonArrayBuilder timeTable=Json.createArrayBuilder();
        JsonObject audit;
        dbg("Time table size"+classTimeTable.timeTable.length);
        
           for(int i=0;i<classTimeTable.timeTable.length;i++){
               dbg("ClassTimeTable.timeTable[i].period"+classTimeTable.timeTable[i]);
               dbg("period size"+classTimeTable.timeTable[i].period.length);
               for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                   
                   
                   period.add(Json.createObjectBuilder().add("periodNumber", classTimeTable.timeTable[i].period[j].getPeriodNumber())
                                                        .add("subjectID", classTimeTable.timeTable[i].period[j].getSubjectID())
                                                        .add("teacherID", classTimeTable.timeTable[i].period[j].getTeacherID()));
                                                     
                }
                timeTable.add(Json.createObjectBuilder().add("day", classTimeTable.timeTable[i].getDay())
                                                        .add("period",period));
           }
              

              
                 
            body=Json.createObjectBuilder()
                                           .add("studentID", studentID)
                                           .add("timeTable", timeTable)
                                           .build();
           
           
            
              dbg(body.toString());  
           dbg("end of getBodyAndAudiFromBO");       
           }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
        return body;
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
