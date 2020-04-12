/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentRank;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_RANK;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_EXAM_RANK_DATASET;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class StudentRankDataSet {
    
    public ArrayList<StudentRank> getStudentRankDetailsObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        
        try{
            
        dbg("inside getStudentRankDetailsObject",session);    
            
        ArrayList<StudentRank>dataset=new ArrayList();
        IPDataService pds=inject.getPdataservice();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=appInject.getBusinessService(session);
//        ClassDataSet classDataSet=  inject.getClassDataSet();
//        String l_exam="";
        
        try{
                String[] studentPkey={p_studentID}; 
                ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID, "INSTITUTE", "IVW_STUDENT_MASTER",studentPkey);
                String standard=l_studentList.get(2).trim();
                String section=l_studentList.get(3).trim();
                CLASS_EXAM_RANK_DATASET classRank=inject.getClassRank();
                 ArrayList<String>completedExams=bs.getCompletedExams(p_instanceID, standard, section, session, dbSession, appInject);
        
            for(int k=0;k<completedExams.size();k++){

               String l_exam= completedExams.get(k);


                    ArrayList<CLASS_EXAM_RANK>classRankList=classRank.getTableObject(standard, section, p_instanceID, l_exam,session, dbSession, inject, appInject);
                    List<CLASS_EXAM_RANK>studentFilteredList=classRankList.stream().filter(rec->rec.getSTUDENT_ID().equals(p_studentID)).collect(Collectors.toList());

                    for(int i=0;i<studentFilteredList.size();i++){

                        CLASS_EXAM_RANK classRankObject=studentFilteredList.get(i);
                        StudentRank studentRank=new StudentRank();

                        String exam=classRankObject.getEXAM();
                        String examDescription=bs.getExamDescription(exam, p_instanceID, session, dbSession, appInject);
                        studentRank.setExam(examDescription);
                        studentRank.setRank(classRankObject.getRANK());
                        studentRank.setTotal(classRankObject.getTOTAL());
                        studentRank.setStudentID(p_studentID);

                        dataset.add(studentRank);
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
            
            StudentRank studentRank=new StudentRank();
            studentRank.setExam(" ");
            studentRank.setRank(" ");
            studentRank.setTotal(" ");
            studentRank.setStudentID(" ");
            dataset.add(studentRank);
            
        }
        
        
        
        
        
        dbg("end of getStudentRankDetailsObject",session);
        return dataset;

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
