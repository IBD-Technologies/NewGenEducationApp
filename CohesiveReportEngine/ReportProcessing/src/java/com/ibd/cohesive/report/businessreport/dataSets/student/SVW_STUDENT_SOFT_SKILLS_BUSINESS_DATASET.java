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
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_SOFT_SKILLS_BUSINESS;
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
 * @author ibdtech
 */
public class SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET {
      public ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS>dataSet=new ArrayList();
        try{
        
        dbg("inside SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET--->getTableObject",session);    
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

                      try{
                    
                        String exam=getCompletedExams.get(i);
                        dbg("inside exam iteration-->exam-->"+exam,session);
                        Map<String,DBRecord>markMap=null;
                        
                        
                      
                        
                        
                        markMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+exam,"CLASS","STUDENT_SKILLS", session, dbSession);
                        dbg("markMap size-->"+markMap.size(),session);
                        
                        
                       
                        List<DBRecord> markRecords=markMap.values().stream().filter(rec->rec.getRecord().get(4).trim().equals(p_studentID)).collect(Collectors.toList());
                        dbg("markRecords size-->"+markRecords.size(),session);
                        
                        ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS>studentMarks= convertDBtoReportObject(markRecords,session,inject,dbSession,p_instanceID, appInject) ;
                        dbg("studentMarks size-->"+studentMarks.size(),session); 
                        
                        for(int j=0;j<studentMarks.size();j++){
                            dbg("inside studentMarks iteration",session); 
                            
                            SVW_STUDENT_SOFT_SKILLS_BUSINESS markObj=studentMarks.get(j);

                            dataSet.add(markObj);
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
            
            SVW_STUDENT_SOFT_SKILLS_BUSINESS studentMarks=new SVW_STUDENT_SOFT_SKILLS_BUSINESS();
            studentMarks.setSTUDENT_ID(" ");
            studentMarks.setEXAM(" ");
            studentMarks.setSKILL_ID(" ");
            studentMarks.setCATEGORY(" ");
            studentMarks.setTEACHER_FEEDBACK(" ");
            studentMarks.setVERSION_NUMBER(" ");
            
            dataSet.add(studentMarks);
        }
        
        
         dbg("end of SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET--->getTableObject",session);  
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
    
    
    
    
    private ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS> convertDBtoReportObject(List<DBRecord>p_studentMarksList,CohesiveSession session,ReportDependencyInjection inject,DBSession dbSession,String instituteID,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
    
        ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_SOFT_SKILLS_BUSINESS convertDBtoReportObject",session);
            BusinessService bs=appInject.getBusinessService(session);
            
            if(!(p_studentMarksList.isEmpty())){
                
             
                Iterator<DBRecord> recordIterator=p_studentMarksList.iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_markRecords=recordIterator.next();
                    SVW_STUDENT_SOFT_SKILLS_BUSINESS studentMarks=new SVW_STUDENT_SOFT_SKILLS_BUSINESS();
                    
                    String exam=l_markRecords.getRecord().get(2).trim();
                    String examDescription=bs.getExamDescription(exam, instituteID, session, dbSession, appInject);
                    studentMarks.setEXAM(examDescription);
                    
                    
//                    studentMarks.setSKILL_ID(l_markRecords.getRecord().get(3).trim());
                    
                    String subjectID=l_markRecords.getRecord().get(3).trim();
                    String subjectName=this.getSkillName(subjectID, session);
                    studentMarks.setSKILL_ID(subjectName);
                    
                    studentMarks.setSTUDENT_ID(l_markRecords.getRecord().get(4).trim());
//                    studentMarks.setCATEGORY(l_markRecords.getRecord().get(5).trim());
                    String categoryDescription=this.getCategoryDescription(l_markRecords.getRecord().get(5).trim(), session);
                    studentMarks.setCATEGORY(categoryDescription);
                    studentMarks.setCATEGORY_VALUE(this.getCategoryValue(categoryDescription, session));
                    studentMarks.setTEACHER_FEEDBACK(l_markRecords.getRecord().get(6).trim());
                    studentMarks.setVERSION_NUMBER(l_markRecords.getRecord().get(7).trim());

                    dbg("studentID"+studentMarks.getSTUDENT_ID() ,session);
                    dbg("exam"+studentMarks.getEXAM() ,session);
                    dbg("subjectID"+studentMarks.getSKILL_ID(),session);
                    dbg("grade"+studentMarks.getCATEGORY(),session);
                    dbg("teacherFeedBack"+studentMarks.getTEACHER_FEEDBACK(),session);
                    dbg("versionNumber"+studentMarks.getVERSION_NUMBER(),session);
                    
                    
                    dataset.add(studentMarks);
                    
                }
            }
            dbg("end of SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET convertDBtoReportObject",session);
        }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;    
        }catch(Exception ex){
            dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
       }
        
        return dataset;
        
    }
    
    private String getSkillName(String skillID,CohesiveSession p_session)throws DBProcessingException{
        
        try{
            
            String skillName=new String();
            
            switch(skillID){
                
                case "1":
                    
                    skillName="Communication";
                    break;
                case "2":
                    
                    skillName="Leadership";
                    break;
                case "3":  
                    
                    skillName="Food Habit";
                    break;
                case "4":
                    
                    skillName="Being Social";
                    break;
                case "5":
                    
                    skillName="Team Work";
                    break;
                case "6":
                    
                    skillName="Discipline";
                    break;
                case "7":
                    
                    skillName="Dressing";
                break;
            
            
            }
            
            
            
            
            return skillName;
        }catch(Exception ex){
           dbg(ex,p_session);
           throw new DBProcessingException(ex.toString());
        }
    }
    
    
    private String getCategoryDescription(String category,CohesiveSession p_session)throws DBProcessingException{
        
        try{
            
            dbg("inside getCategoryDescription",p_session);
            String description=new String();
            
            switch(category){
                
                case "1":
                    
                    description="Outstanding";
                    break;
                case "2":
                    
                    description="Good";
                    break;
                case "3":  
                    
                    description="Improvement Required";
                    break;
                
            
            
            }
            
            
            
            
            dbg("end of getCategoryDescription-->description-->"+description,p_session);
            return description;
        }catch(Exception ex){
           dbg(ex,p_session);
           throw new DBProcessingException(ex.toString());
        }
    } 
    
    
    private int getCategoryValue(String category,CohesiveSession p_session)throws DBProcessingException{
        
        try{
            
            dbg("inside getCategoryValue",p_session);
            int  value=0;
            
            switch(category){
                
                case "Outstanding":
                    
                    value=4;
                    break;
                case "Good":
                    
                    value=3;
                    break;
                case "Improvement Required":  
                    
                    value=1;
                    break;
                
            
            
            }
            
            
            
            
            dbg("end of getCategoryValue-->description-->"+value,p_session);
            return value;
        }catch(Exception ex){
           dbg(ex,p_session);
           throw new DBProcessingException(ex.toString());
        }
    } 
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
