/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherMarksDetail;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherMarksSummary;
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
 * @author IBD Technologies
 */
public class TeacherMarksSummary_DataSet {
    
    public ArrayList<TeacherMarksSummary> getTeacherMarksSummaryObject(String p_teacherID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        ArrayList<TeacherMarksSummary> dataset=new ArrayList();
        try{
        
        dbg("inside getTeacherMarksSummaryObject",session);   
        
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        DecimalFormat df = new DecimalFormat("###.##");
        BusinessService bs=appInject.getBusinessService(session);
//        TeacherDataSetBusiness teacherDataSet=inject.getTeacherDatasetBusiness();
        TeacherMarksDetail_DataSet teacherMarks=inject.getTeacherMarksDetail();
        
        List<TeacherMarksDetail>teacherMarksDetailList=teacherMarks.getTeacherMarksDetailDataSet(p_teacherID, p_instanceID, session, dbSession, inject,appInject);
              
        Map<String,List<TeacherMarksDetail>>classFilteredMap=  teacherMarksDetailList.stream().collect(Collectors.groupingBy(rec->rec.getSTANDARD()+"s"+rec.getSECTION()));
        
        Iterator<String>classIterator=classFilteredMap.keySet().iterator();
        
        while(classIterator.hasNext()){
          
          String className=classIterator.next();
          String standard=className.split("s")[0];
          String section=className.split("s")[1];
          int totalStudents=bs.getNoOfStudentsOfTheClass(p_instanceID, standard, section, session, dbSession, appInject);
            
          List<TeacherMarksDetail>classWiseList=classFilteredMap.get(className);
          
          Map<String,List<TeacherMarksDetail>> examWiseGroup=classWiseList.stream().collect(Collectors.groupingBy(rec->rec.getEXAM()));
          Iterator<String>examIterator=examWiseGroup.keySet().iterator();
         
        while(examIterator.hasNext()){
            String exam=examIterator.next();
            dbg("exam"+exam,session);
            List<TeacherMarksDetail>examList=examWiseGroup.get(exam);
            Map<String,List<TeacherMarksDetail>>subjectWiseGroup=examList.stream().collect(Collectors.groupingBy(rec->rec.getSUBJECT_ID()));
            Iterator<String>subjectIterator=subjectWiseGroup.keySet().iterator();
            
            while(subjectIterator.hasNext()){
                String subjectID=subjectIterator.next();
                dbg("subjectID"+subjectID,session);
                List<TeacherMarksDetail>subjectList=subjectWiseGroup.get(subjectID);
                Double averageMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).average().getAsDouble();
                int highestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).max().getAsInt();
                int lowestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).min().getAsInt();
                dbg("averageMark"+averageMark,session);
                dbg("highestMark"+highestMark,session);
                dbg("lowestMark"+lowestMark,session);
                
                Map<String,List<TeacherMarksDetail>>gradeWiseGroup=subjectList.stream().collect(Collectors.groupingBy(rec->rec.getGRADE()));
                Iterator<String> gradeIterator=gradeWiseGroup.keySet().iterator();

                while(gradeIterator.hasNext()){
                    String grade=gradeIterator.next();
                    String no_of_studentsWithTheGrade=Integer.toString(gradeWiseGroup.get(grade).size());
                    TeacherMarksSummary markSummary=new TeacherMarksSummary();
                    markSummary.setStandard(standard);
                    markSummary.setSection(section);
                    markSummary.setExam(exam);
                    markSummary.setSubjectID(subjectID);
                    markSummary.setSubjectName(bs.getSubjectName(subjectID, p_instanceID, session, dbSession, appInject));
                    markSummary.setAverageMark(df.format(Math.round(averageMark)));
                    markSummary.setTopMark(Integer.toString(highestMark));
                    markSummary.setLowMark(Integer.toString(lowestMark));
                    markSummary.setGrade(grade);
                    markSummary.setNo_of_Students(no_of_studentsWithTheGrade);
                    
                    float percentage=((float)(Integer.parseInt(no_of_studentsWithTheGrade))/(float)totalStudents)*100;
                    dbg("percentage"+percentage,session);
                    
                    markSummary.setGradePercentage(df.format(Math.round(percentage)));
                    dataset.add(markSummary);
                }
                
                
                
                
            }
        } 
         
        }
        
        Map<String,List<TeacherMarksSummary>>examWiseGroup=dataset.stream().collect(Collectors.groupingBy(rec->rec.getExam()+"~"+rec.getStandard()+"~"+rec.getSection()));
        dbg("examWiseGroup size"+examWiseGroup.size(),session);
        
        
        Iterator<String>examIterator=examWiseGroup.keySet().iterator();
        Map<String,Integer>examDateMap=new HashMap();
        
        while(examIterator.hasNext()){
            
            String key=examIterator.next();
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
                        
                        TeacherMarksSummary markSummary=dataset.get(j);
                        
                        if(markSummary.getExam().equals(exam)){
                        
                            if(!orderNoIncreased){
                                orderNo=orderNo+1;
                                orderNoIncreased=true;
                            
                             }
                            
                            markSummary.setOrderNumber(orderNo);
                            
                        }
                        
                    }
                    
                }
                
            }
        
        }
//        for(int i=0;i<dataset.size();i++){
//            
//            TeacherMarksSummary markSummary=dataset.get(i);
//            
//            String exam=markSummary.getExam();
//            Stri
//            
//            bs.getStartDateofTheExam(p_instanceID, exam, exam, exam, session, dbSession, appInject)
//            
//        }
        
        
        
        
        
        
        
        dbg("inside getTeacherMarksSummaryObject",session);
        
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
