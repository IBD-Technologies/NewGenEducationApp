/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksSummary;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
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
public class ClassMarksSummary_DataSet {
    
    public ArrayList<ClassMarksSummary> getClassMarksSummaryObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        ArrayList<ClassMarksSummary>dataset=new ArrayList();
        try{
        
            
        dbg("inside getClassMarksSummaryObject",session);    
        
        BusinessService bs=appInject.getBusinessService(session);
        int totalStudents=bs.getNoOfStudentsOfTheClass(p_instanceID, p_standard, p_section, session, dbSession, appInject);
        DecimalFormat df = new DecimalFormat("###.##");
//        ClassDataSetBusiness classDataSetBusiness= inject.getClassDatasetBusiness();
        ClassMarksDetail_DataSet classMarkDetail=inject.getClassMarksDetail();
        ArrayList<ClassMarksDetail>classMarkDetailList=  classMarkDetail.getClassMarksDetail(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
        Map<String,List<ClassMarksDetail>> examWiseGroup=classMarkDetailList.stream().collect(Collectors.groupingBy(rec->rec.getEXAM()));
        Iterator<String>examIterator=examWiseGroup.keySet().iterator();
         
        while(examIterator.hasNext()){
            
            String exam=examIterator.next();
           
            List<ClassMarksDetail>examList=examWiseGroup.get(exam);
            Map<String,List<ClassMarksDetail>>subjectWiseGroup=examList.stream().collect(Collectors.groupingBy(rec->rec.getSUBJECT_ID()));
            Iterator<String>subjectIterator=subjectWiseGroup.keySet().iterator();
            
            while(subjectIterator.hasNext()){
                String subjectID=subjectIterator.next();
                
                List<ClassMarksDetail>subjectList=subjectWiseGroup.get(subjectID);
                Double averageMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).average().getAsDouble();
                int highestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).max().getAsInt();
                int lowestMark=subjectList.stream().mapToInt(rec->Integer.parseInt(rec.getMARK())).min().getAsInt();
                
                
                
                Map<String,List<ClassMarksDetail>>gradeWiseGroup=subjectList.stream().collect(Collectors.groupingBy(rec->rec.getGRADE()));
                Iterator<String> gradeIterator=gradeWiseGroup.keySet().iterator();
                
                while(gradeIterator.hasNext()){
                    String grade=gradeIterator.next();
                    String no_of_studentsWithTheGrade=Integer.toString(gradeWiseGroup.get(grade).size());
                    ClassMarksSummary summary=new ClassMarksSummary();
                    summary.setExam(exam);
                    summary.setStandard(p_standard);
                    summary.setSection(p_section);
                    summary.setSubjectID(subjectID);
                    summary.setSubjectName(bs.getSubjectName(subjectID, p_instanceID, session, dbSession, appInject));
                    summary.setAverageMark(df.format(Math.round(averageMark)));
                    summary.setTopMark(Integer.toString(highestMark));
                    summary.setLowMark(Integer.toString(lowestMark));
                    summary.setGrade(grade);
                    summary.setNo_of_Students(no_of_studentsWithTheGrade);
                    float percentage=((float)(Integer.parseInt(no_of_studentsWithTheGrade))/(float)totalStudents)*100;
                    dbg("percentage"+percentage,session);
                    
                    summary.setGradePercentage(df.format(Math.round(percentage)));
                    dataset.add(summary);
                }
                
            }
        } 
         
        
        Map<String,List<ClassMarksSummary>>examGroup=dataset.stream().collect(Collectors.groupingBy(rec->rec.getExam()+"~"+rec.getStandard()+"~"+rec.getSection()));
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
                        
                        ClassMarksSummary markSummary=dataset.get(j);
                        
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
        dbg("end of getClassMarksSummaryObject",session);
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
