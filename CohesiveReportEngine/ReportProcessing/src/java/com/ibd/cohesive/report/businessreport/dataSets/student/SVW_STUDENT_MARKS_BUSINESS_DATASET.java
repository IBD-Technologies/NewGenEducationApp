/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_MARKS_BUSINESS;
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
public class SVW_STUDENT_MARKS_BUSINESS_DATASET {
     public ArrayList<SVW_STUDENT_MARKS_BUSINESS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        ArrayList<SVW_STUDENT_MARKS_BUSINESS>dataSet=new ArrayList();
        try{
        
        dbg("inside SVW_STUDENT_MARKS_BUSINESS_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=appInject.getBusinessService(session);

        try{
        
        
                String[] studentPkey={p_studentID}; 
                ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPkey);
                String standard=l_studentList.get(2).trim();
                String section=l_studentList.get(3).trim();
                dbg("standard"+standard,session);
                dbg("section"+section,session);
                ArrayList<String>getCompletedExams=bs.getCompletedExams(p_instanceID, standard, section, session, dbSession, appInject);
                for(int i=0;i<getCompletedExams.size();i++){

                        String exam=getCompletedExams.get(i);
                        dbg("inside exam iteration-->exam-->"+exam,session);
                        Map<String,DBRecord>markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+exam,"CLASS","STUDENT_MARKS", session, dbSession);
                        dbg("markMap size-->"+markMap.size(),session);    
                        
                        List<DBRecord> markRecords=markMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals(p_studentID)).collect(Collectors.toList());
                        dbg("markRecords size-->"+markRecords.size(),session);
                        
                        ArrayList<SVW_STUDENT_MARKS_BUSINESS>studentMarks= convertDBtoReportObject(markRecords,session,inject,dbSession,p_instanceID, appInject) ;
                        dbg("studentMarks size-->"+studentMarks.size(),session); 
                        
                        for(int j=0;j<studentMarks.size();j++){
                            dbg("inside studentMarks iteration",session); 
                            
                            SVW_STUDENT_MARKS_BUSINESS markObj=studentMarks.get(j);

                            dataSet.add(markObj);
                        }

                }
             dbg("try finished",session);
         }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
               
            }else{
                
                throw ex;
            }
            
            
        }
        
        
        
        if(dataSet.isEmpty()){
            
            SVW_STUDENT_MARKS_BUSINESS studentMarks=new SVW_STUDENT_MARKS_BUSINESS();
            studentMarks.setSTUDENT_ID(" ");
            studentMarks.setEXAM(" ");
            studentMarks.setSUBJECT_ID(" ");
            studentMarks.setMARK(" ");
            studentMarks.setGRADE(" ");
            studentMarks.setTEACHER_FEEDBACK(" ");
            studentMarks.setVERSION_NUMBER(" ");
            
            dataSet.add(studentMarks);
        }
        
        
         dbg("end of SVW_STUDENT_MARKS_BUSINESS_DATASET--->getTableObject",session);  
        return   dataSet;
        
        
        
        
        
       
    
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
    
    
    
    
    private ArrayList<SVW_STUDENT_MARKS_BUSINESS> convertDBtoReportObject(List<DBRecord>p_studentMarksList,CohesiveSession session,ReportDependencyInjection inject,DBSession dbSession,String instituteID,AppDependencyInjection appInject)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_MARKS_BUSINESS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_MARKS_BUSINESS convertDBtoReportObject",session);
            BusinessService bs=appInject.getBusinessService(session);
            
            if(!(p_studentMarksList.isEmpty())){
                
             
                Iterator<DBRecord> recordIterator=p_studentMarksList.iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_markRecords=recordIterator.next();
                    SVW_STUDENT_MARKS_BUSINESS studentMarks=new SVW_STUDENT_MARKS_BUSINESS();
                    
                    String exam=l_markRecords.getRecord().get(2).trim();
                    String examDescription=bs.getExamDescription(exam, instituteID, session, dbSession, appInject);
                    studentMarks.setEXAM(examDescription);
                    
                    
//                    studentMarks.setSUBJECT_ID(l_markRecords.getRecord().get(3).trim());
                    
                    String subjectID=l_markRecords.getRecord().get(3).trim();
                    String subjectName=bs.getSubjectName(subjectID, instituteID, session, dbSession, appInject);
                    studentMarks.setSUBJECT_ID(subjectName);
                    
                    studentMarks.setSTUDENT_ID(l_markRecords.getRecord().get(4).trim());
                    studentMarks.setMARK(l_markRecords.getRecord().get(6).trim());
                    String grade=bs.getGrade(studentMarks.getMARK(), instituteID, session, dbSession, appInject);
                    studentMarks.setGRADE(grade);
                    studentMarks.setTEACHER_FEEDBACK(l_markRecords.getRecord().get(7).trim());
                    studentMarks.setVERSION_NUMBER(l_markRecords.getRecord().get(8).trim());

                    dbg("studentID"+studentMarks.getSTUDENT_ID() ,session);
                    dbg("exam"+studentMarks.getEXAM() ,session);
                    dbg("subjectID"+studentMarks.getSUBJECT_ID(),session);
                    dbg("grade"+studentMarks.getGRADE(),session);
                    dbg("mark"+studentMarks.getMARK(),session);
                    dbg("teacherFeedBack"+studentMarks.getTEACHER_FEEDBACK(),session);
                    dbg("versionNumber"+studentMarks.getVERSION_NUMBER(),session);
                    
                    
                    dataset.add(studentMarks);
                    
                }
            }
            dbg("end of SVW_STUDENT_MARKS_BUSINESS_DATASET convertDBtoReportObject",session);
            
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
