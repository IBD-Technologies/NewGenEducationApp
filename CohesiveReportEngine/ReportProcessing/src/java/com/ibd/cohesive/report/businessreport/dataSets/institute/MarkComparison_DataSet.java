/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksDetail;
import com.ibd.cohesive.report.businessreport.dataModels.institute.MarkComparison;
import com.ibd.cohesive.report.businessreport.dataSets.classEntity.ClassMarksDetail_DataSet;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class MarkComparison_DataSet {
     public ArrayList<MarkComparison> getMarkComparisonObject(String p_instanceID,String p_standard,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String userID)throws DBProcessingException,DBValidationException{
        ITransactionControlService tc=null;
        ArrayList<MarkComparison>dataset=new ArrayList();
        try{
        
            
        dbg("inside getMarkComparisonObject",session);    
        dbg("p_instanceID"+p_instanceID,session);
        dbg("p_standard"+p_standard,session);
        DecimalFormat df = new DecimalFormat("###.##");
        BusinessService bs=appInject.getBusinessService(session);
        tc=inject.getTransactionControlService();
        try{
        
//        ArrayList<String>classList=bs.getClassesOfTheInstitute(p_instanceID, session, dbSession, appInject);
        ClassMarksDetail_DataSet classMarkDetail=inject.getClassMarksDetail();
         ArrayList<String>sectionList=bs.getSectionsOfTheStandard(p_instanceID, p_standard, session, dbSession, appInject);
        
        for(int i=0;i<sectionList.size();i++){
        
        
//            ClassDataSetBusiness classDataSetBusiness= inject.getClassDatasetBusiness();
            String p_section=sectionList.get(i); 
            int totalStudents=bs.getNoOfStudentsOfTheClass(p_instanceID, p_standard, p_section, session, dbSession, appInject);
            
            dbg("p_standard"+p_standard,session);
            dbg("p_section"+p_section,session);
            ArrayList<ClassMarksDetail>classMarkDetailList=  classMarkDetail.getClassMarksDetail(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
            dbg("classMarkDetailList size"+classMarkDetailList.size(),session);
            Map<String,List<ClassMarksDetail>> examWiseGroup=classMarkDetailList.stream().collect(Collectors.groupingBy(rec->rec.getEXAM()));
            dbg("examWiseGroup size"+examWiseGroup.size(),session);
            Iterator<String>examIterator=examWiseGroup.keySet().iterator();

            while(examIterator.hasNext()){
                
                String exam=examIterator.next();
                dbg("inside exam iteration-->exam-->"+exam,session);
                
                List<ClassMarksDetail>examList=examWiseGroup.get(exam);
                dbg("examList size-->"+examList.size(),session);
                Map<String,List<ClassMarksDetail>>subjectWiseGroup=examList.stream().collect(Collectors.groupingBy(rec->rec.getSUBJECT_ID()));
                dbg("subjectWiseGroup size-->"+subjectWiseGroup.size(),session);
                Iterator<String>subjectIterator=subjectWiseGroup.keySet().iterator();

                while(subjectIterator.hasNext()){
                    
                    String subjectID=subjectIterator.next();
                    dbg("inside subject iteration-->subject"+subjectID,session);
                    List<ClassMarksDetail>subjectList=subjectWiseGroup.get(subjectID);
                    dbg("subjectWiseGroup size"+subjectWiseGroup.size(),session);
                    
                    this.updateMarkRecords(p_standard, p_section, exam, subjectID, p_instanceID, subjectList, session, dbSession, inject, appInject,userID);
                    
//                    Double averageMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).average().getAsDouble();
//                    int highestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).max().getAsInt();
//                    int lowestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).min().getAsInt();
                    
                    Map<String,List<ClassMarksDetail>>gradeWiseGroup=subjectList.stream().collect(Collectors.groupingBy(rec->rec.getGRADE()));
                    Iterator<String> gradeIterator=gradeWiseGroup.keySet().iterator();

                    while(gradeIterator.hasNext()){
                        String grade=gradeIterator.next();
                        String no_of_studentsWithTheGrade=Integer.toString(gradeWiseGroup.get(grade).size());
                        dbg("inside grade iteration-->grade"+grade,session);
                        dbg("inside grade iteration-->no_of_studentsWithTheGrade"+no_of_studentsWithTheGrade,session);
                        MarkComparison summary=new MarkComparison();
                        
                        summary.setStandard(p_standard);
                        summary.setSection(p_section);
                        summary.setExam(exam);
                        summary.setSubjectID(subjectID);
                        summary.setSubjectName(bs.getSubjectName(subjectID, p_instanceID, session, dbSession, appInject));
                        summary.setAverageMark(" ");
                        summary.setTopMark(" ");
                        summary.setLowMark(" ");
                        summary.setGrade(grade);
                        summary.setNo_of_Students(no_of_studentsWithTheGrade);
                        float percentage=((float)(Integer.parseInt(no_of_studentsWithTheGrade))/(float)totalStudents)*100;
                        dbg("percentage"+percentage,session);
                    
                        summary.setGradePercentage(df.format(Math.round(percentage)));
//                        summary.setOrderNo(i);
                        dataset.add(summary);
                    }

                }
            } 
         
        }
        
         Map<String,List<MarkComparison>>examGroup=dataset.stream().collect(Collectors.groupingBy(rec->rec.getExam()+"~"+rec.getStandard()+"~"+rec.getSection()));
        dbg("examWiseGroup size"+examGroup.size(),session);
        
        
        Iterator<String>examsIterator=examGroup.keySet().iterator();
        Map<String,Integer>examDateMap=new HashMap();
        
        while(examsIterator.hasNext()){
            
            String key=examsIterator.next();
            dbg("key"+key,session);
            String exam=key.split("~")[0];
            String standard=key.split("~")[1];
            String section=key.split("~")[2];
            dbg("exam"+exam,session);
            dbg("standard"+standard,session);
            dbg("section"+section,session);
            
            int startsDate=bs.getStartDateofTheExam(p_instanceID, standard, section, exam, session, dbSession, appInject);
            
            if(examDateMap.containsKey(exam)){
                
                if(examDateMap.get(exam)>startsDate){
                    
                    examDateMap.put(exam, startsDate);
                    
                }
                
            }else{
                
                examDateMap.put(exam, startsDate);
            }

            
        }
        
        List<Integer> sortedKeys=new ArrayList(examDateMap.values());
        Collections.sort(sortedKeys); 
        int orderNo=0;
        
        for(int i=0;i<sortedKeys.size();i++){
         
            int date=sortedKeys.get(i);
            
            Iterator<String>keyIterator=examDateMap.keySet().iterator();
            
            while(keyIterator.hasNext()){
                
                String exam=keyIterator.next();
                Integer value=examDateMap.get(exam);
                
                if(value==date){
                    
                    boolean orderNoIncreased=false;
                    
                    for(int j=0;j<dataset.size();j++){
                        
                        MarkComparison markSummary=dataset.get(j);
                        
                        if(markSummary.getExam().equals(exam)){
                        
                            if(!orderNoIncreased){
                                orderNo=orderNo+1;
                                orderNoIncreased=true;
                            
                             }
                            
                            markSummary.setOrderNo(orderNo);
                            
                        }
                        
                    }
                    
                }
                
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
            
            
            MarkComparison summary=new MarkComparison();
                        
            summary.setStandard(" ");
            summary.setSection(" ");
            summary.setExam(" ");
            summary.setSubjectID(" ");
            summary.setAverageMark(" ");
            summary.setTopMark(" ");
            summary.setLowMark(" ");
            summary.setGrade(" ");
            summary.setNo_of_Students(" ");
            summary.setSubjectName(" ");
            summary.setGradePercentage(" ");
            summary.setOrderNo(0);
            dataset.add(summary);
        
        }
        
        
           tc.commit(session, dbSession);
           
        dbg("end of getMarkComparisonObject",session);
    }catch(DBProcessingException ex){
          dbg(ex,session);
          tc.rollBack(session, dbSession);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          tc.rollBack(session, dbSession);
          throw ex;
     }catch(Exception ex){
         tc.rollBack(session, dbSession);
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
       return dataset;
    }
    
     
     
     private void updateMarkRecords(String p_standard,String p_section,String exam,String subjectID,String p_instanceID,List<ClassMarksDetail>subjectList,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String userID)throws DBProcessingException,DBValidationException{
        
     try{
         IDBTransactionService dbts=inject.getDBTransactionService();
         IBDProperties i_db_properties=session.getCohesiveproperties();
         IDBReadBufferService readBuffer=inject.getDBReadBufferService();
         
         Double averageMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).average().getAsDouble();
         int highestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).max().getAsInt();
         int lowestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).min().getAsInt();
         String[] pkey={p_standard,p_section,subjectID,exam};
         boolean recordExistence=false;
         
         try{
             
             
             readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT", "CLASS_MARK_REPORT", pkey, session, dbSession);
             
             recordExistence=true;
         }catch(DBValidationException ex){

                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                    recordExistence=false;
                }else{

                    throw ex;
                }


            }  
         
         
         
         if(recordExistence){
             
             Map<String,String>l_columnToUpdate=new HashMap();
             l_columnToUpdate.put("5", Double.toString(averageMark));
             l_columnToUpdate.put("6", Integer.toString(highestMark));
             l_columnToUpdate.put("7", Integer.toString(lowestMark));
             
             
             
             dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT", "CLASS_MARK_REPORT", pkey, l_columnToUpdate, session);
             
         }else{
         
         
         
         
          dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT",276,p_standard,p_section,subjectID,exam,Double.toString(averageMark),Integer.toString(highestMark),Integer.toString(lowestMark));
         
         
         }
         
         
         
         
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
