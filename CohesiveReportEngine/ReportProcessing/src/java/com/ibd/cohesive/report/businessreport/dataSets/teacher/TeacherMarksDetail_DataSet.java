/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherMarksDetail;
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
 * @author IBD Technologies
 */
public class TeacherMarksDetail_DataSet {
    
    
    public ArrayList<TeacherMarksDetail> getTeacherMarksDetailDataSet(String p_teacherID,String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<TeacherMarksDetail> dataset=new ArrayList();
        
        try{
        
         dbg("inside getTeacherMarksDetailDataSet",session);   
         IDBReadBufferService readBuffer=inject.getDBReadBufferService();
         IBDProperties i_db_properties=session.getCohesiveproperties();  
         BusinessService bs=appInject.getBusinessService(session);
            
         
         
         
                 ArrayList<String>classesAndSubjectList=  getClassesAndSubjects(p_teacherID,p_instituteID,session,dbSession,inject);
                 for(int i=0;i<classesAndSubjectList.size();i++){

                     try{
                     
                     
                    String classAndSubject=classesAndSubjectList.get(i);
                    String standard=classAndSubject.split("~")[0];
                    String section=classAndSubject.split("~")[1];
                    String subjectID=classAndSubject.split("~")[2];
                    dbg("inside class and subject iteration--->standard--->"+standard,session);
                    dbg("inside class and subject iteration--->section--->"+section,session);
                    dbg("inside class and subject iteration--->subjectID--->"+subjectID,session);

                    ArrayList<String>getCompletedExams=bs.getCompletedExams(p_instituteID, standard, section, session, dbSession, appInject);


                    for(int j=0;j<getCompletedExams.size();j++){

                         String l_exam=getCompletedExams.get(j);
                         dbg("completed exam iteration-->l_exam"+l_exam,session);
                         Map<String,DBRecord>markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_MARKS", session, dbSession);

                         List<DBRecord>subjectList=markMap.values().stream().filter(rec->rec.getRecord().get(3).trim().equals(subjectID)).collect(Collectors.toList());
                         dbg("subjectList size"+subjectList.size(),session);
                         
                         
                         for(int k=0;k<subjectList.size();k++){

                                DBRecord studentWiseMarks=subjectList.get(k);
                                TeacherMarksDetail teacherMarks=new TeacherMarksDetail();
                                teacherMarks.setSTANDARD(standard);
                                teacherMarks.setSECTION(section);
                                teacherMarks.setEXAM(l_exam);
                                teacherMarks.setSUBJECT_ID(subjectID);
                                teacherMarks.setSubjectName(bs.getSubjectName(subjectID, p_instituteID, session, dbSession, appInject));
                                
                                teacherMarks.setSTUDENT_ID(studentWiseMarks.getRecord().get(4).trim());
                                teacherMarks.setStudentName(bs.getStudentName(teacherMarks.getSTUDENT_ID(), p_instituteID, session, dbSession, appInject));
                                teacherMarks.setMARK(studentWiseMarks.getRecord().get(6).trim());
                                teacherMarks.setGRADE(bs.getGrade(teacherMarks.getMARK(), p_instituteID, session, dbSession, appInject));
                                dataset.add(teacherMarks);
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
                    
                    
                    
                    
                    
                 }
                     
                     
                     
                     
                     
                     
           
         
        
         
         
         if(dataset.isEmpty()){
             
             TeacherMarksDetail teacherMarks=new TeacherMarksDetail();
             teacherMarks.setSTANDARD(" ");
             teacherMarks.setSECTION(" ");
             teacherMarks.setEXAM(" ");
             teacherMarks.setSUBJECT_ID(" ");
             teacherMarks.setSTUDENT_ID(" ");
             teacherMarks.setMARK(" ");
             teacherMarks.setGRADE(" ");
             teacherMarks.setStudentName(" ");
             teacherMarks.setSubjectName(" ");
             dataset.add(teacherMarks);
             
             
         }
         
         
         
         
    dbg("end of getTeacherMarksDetailDataSet",session);   
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
    
    private ArrayList<String>getClassesAndSubjects(String p_teacherID,String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        try{
            
            dbg("inside getClassesAndSubjects",session);
            
            ArrayList<String>classAndSubjectList=new ArrayList();
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            
            Map<String,DBRecord>detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS",session, dbSession);
            
            Iterator<DBRecord>valueIterator=detailMap.values().iterator();
            
            while(valueIterator.hasNext()){
                
                dbg("value"+valueIterator.next().getRecord(),session);
                
            }
            
            
            List<DBRecord>teacherRecords=detailMap.values().stream().filter(rec->rec.getRecord().get(5).trim().equals(p_teacherID)).collect(Collectors.toList());
            
            for(int i=0;i<teacherRecords.size();i++){
                
                ArrayList<String>value=teacherRecords.get(i).getRecord();
                String standard=value.get(0).trim();
                String section=value.get(1).trim();
                String subjectID=value.get(4).trim();
                dbg("standard"+standard,session);
                dbg("section"+section,session);
                dbg("subjectID"+subjectID,session);
                
                if(!classAndSubjectList.contains(standard+"~"+section+"~"+subjectID)){
                    
                    dbg("classAndSubjectList not contains",session);
                    
                    classAndSubjectList.add(standard+"~"+section+"~"+subjectID);
                    
                }
                
                
            }
            
            
            
            
            dbg("end of classAndSubjectList",session);
            return classAndSubjectList;
            
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
    
    
   
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
