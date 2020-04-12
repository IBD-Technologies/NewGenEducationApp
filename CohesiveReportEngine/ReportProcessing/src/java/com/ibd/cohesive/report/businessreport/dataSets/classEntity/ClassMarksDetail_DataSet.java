/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksDetail;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_MARKS;
import com.ibd.businessViews.IClassDataSet;
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
import java.util.Map;

/**
 *
 * @author DELL
 */
public class ClassMarksDetail_DataSet {
    
    
    
    public ArrayList<ClassMarksDetail> getClassMarksDetail(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        ArrayList<ClassMarksDetail> dataset=new ArrayList();
        try{
        
        dbg("inside getClassMarksDetail",session);    
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();      
        BusinessService bs=appInject.getBusinessService(session);
        
        try{
        
        
            ArrayList<String>getCompletedExams=bs.getCompletedExams(p_instanceID, p_standard, p_section, session, dbSession, appInject);
            dbg(" getCompletedExams size"+getCompletedExams.size(),session);
            for(int j=0;j<getCompletedExams.size();j++){

                String l_exam=getCompletedExams.get(j);
                dbg("inside examIteration",session);
                Map<String,DBRecord>markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+l_exam,"CLASS","STUDENT_MARKS", session, dbSession);
                Iterator<DBRecord>valueIterator=markMap.values().iterator();
                while(valueIterator.hasNext()){

                    dbg("inside mark value iteration",session);
                    ClassMarksDetail classMarkDetail=new ClassMarksDetail();
                    ArrayList<String> studentMarksList= valueIterator.next().getRecord();
                    classMarkDetail.setSTANDARD(p_standard);
                    classMarkDetail.setSECTION(p_section);
                    classMarkDetail.setEXAM(l_exam);
                    classMarkDetail.setSUBJECT_ID(studentMarksList.get(3).trim());
                    dbg("subjectID-->"+classMarkDetail.getSUBJECT_ID(),session);
                    classMarkDetail.setSubjectName(bs.getSubjectName(classMarkDetail.getSUBJECT_ID(), p_instanceID, session, dbSession, appInject));
                    dbg("subjectName-->"+classMarkDetail.getSubjectName(),session);
                    classMarkDetail.setSTUDENT_ID(studentMarksList.get(4).trim());
                    dbg("studentID-->"+classMarkDetail.getSTUDENT_ID(),session);
                    classMarkDetail.setStudentName(bs.getStudentName(classMarkDetail.getSTUDENT_ID(), p_instanceID, session, dbSession, appInject));
                    dbg("studentName-->"+classMarkDetail.getStudentName(),session);
                    classMarkDetail.setMARK(studentMarksList.get(6).trim());
                    dbg("mark-->"+classMarkDetail.getMARK(),session);
                    classMarkDetail.setGRADE(bs.getGrade(classMarkDetail.getMARK(), p_instanceID, session, dbSession, appInject));
                    dbg("grade-->"+classMarkDetail.getGRADE(),session);
                    dataset.add(classMarkDetail);


                }        
            }     
        
        
        }catch(DBValidationException ex){
            dbg("exception in view operation"+ex,session);
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

            }else{

                throw ex;
            }
       }
        
        
        
        
         if(dataset.isEmpty()){
             
             ClassMarksDetail teacherMarks=new ClassMarksDetail();
             teacherMarks.setSTANDARD(" ");
             teacherMarks.setSECTION(" ");
             teacherMarks.setEXAM(" ");
             teacherMarks.setSUBJECT_ID(" ");
             teacherMarks.setSTUDENT_ID(" ");
             teacherMarks.setStudentName(" ");
             teacherMarks.setSubjectName(" ");
             teacherMarks.setMARK(" ");
             teacherMarks.setGRADE(" ");
             dataset.add(teacherMarks);
             
             
         }
    dbg("end of getClassMarksDetail",session);
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
