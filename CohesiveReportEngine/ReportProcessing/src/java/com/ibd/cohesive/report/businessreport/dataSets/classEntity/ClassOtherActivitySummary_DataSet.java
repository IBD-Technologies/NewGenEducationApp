/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivityDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivitySummary;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class ClassOtherActivitySummary_DataSet {
      
      static String dateFormat;
    
    public ArrayList<ClassOtherActivitySummary> getClassOtherActivitySummaryObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<ClassOtherActivitySummary>  dataset=new ArrayList();
        
        try{
        
        dbg("inside getClassOtherActivitySummaryObject",session);    
//        ClassDataSetBusiness classDataSetBusiness= inject.getClassDatasetBusiness();
        dateFormat=session.getCohesiveproperties().getProperty("DATE_FORMAT");
         ClassOtherActivityDetail_DataSet classOtherActivity=inject.getClassOtherActivityDetail();
        
        ArrayList<ClassOtherActivityDetail>otherActivityDetailList=  classOtherActivity.getClassOtherActivity(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
        dbg("otherActivityDetailList"+otherActivityDetailList.size(),session);

        
        if(otherActivityDetailList.size()==1&&otherActivityDetailList.get(0).getACTIVITY_TYPE().equals(" ")){
            
            dbg("class other activity details list is empty",session);
        
        }else{
         ConcurrentMap<String,List<ClassOtherActivityDetail>> otherActivityYearMonthwiseGroup=otherActivityDetailList.parallelStream().collect(Collectors.groupingByConcurrent(ClassOtherActivitySummary_DataSet::getYearandMonth));
       dbg("otherActivityYearMonthwiseGroup"+otherActivityYearMonthwiseGroup.size(),session);
         Iterator<String> yearMonthIterator=otherActivityYearMonthwiseGroup.keySet().iterator();
         
         while(yearMonthIterator.hasNext()){
             
             dbg("inside year month iteration",session);
             String year_and_Month=yearMonthIterator.next();
             String year=year_and_Month.split("y")[0];
             String month=year_and_Month.split("y")[1];
             dbg("year_and_Month"+year_and_Month,session);
             
             List<ClassOtherActivityDetail>yearMonthList=otherActivityYearMonthwiseGroup.get(year_and_Month);
             dbg("yearMonthList size"+yearMonthList.size(),session);
             
             
             ConcurrentMap<String,List<ClassOtherActivityDetail>> otherActivityEventwiseGroup=yearMonthList.parallelStream().collect(Collectors.groupingByConcurrent(rec->rec.getACTIVITY_TYPE()));
             dbg("otherActivityEventwiseGroup size"+otherActivityEventwiseGroup.size(),session);
             //int no_of_events_Conducted=otherActivityEventwiseGroup.keySet().size();
             //int no_of_studentsParticipated=otherActivityEventwiseGroup.values().size();
             ///summary.setNo_of_events_Conducted(Integer.toString(no_of_events_Conducted));
             //summary.setNo_of_students_Participated(Integer.toString(no_of_studentsParticipated));
             
             Iterator<String> eventWiseIterator=otherActivityEventwiseGroup.keySet().iterator();
             
             while(eventWiseIterator.hasNext()){
                 dbg("inside eventWise iterator",session);
                 String activityType=eventWiseIterator.next();
                 dbg("activityType"+activityType,session);
                 List<ClassOtherActivityDetail>studentActivityList= otherActivityEventwiseGroup.get(activityType);
                 dbg("studentActivityList"+studentActivityList.size(),session);
                 ConcurrentMap<String,List<ClassOtherActivityDetail>> otherActivityResultwiseGroup=studentActivityList.parallelStream().collect(Collectors.groupingByConcurrent(rec->rec.getRESULT()));
                 dbg("otherActivityResultwiseGroup"+otherActivityResultwiseGroup.size(),session);
                 Iterator<String>resultIterator=otherActivityResultwiseGroup.keySet().iterator();
                 
                 while(resultIterator.hasNext()){
                     
                     String result=resultIterator.next();
                     dbg("insidee result iteration result",session);
                     ClassOtherActivitySummary summary=new ClassOtherActivitySummary();
                     summary.setYear(year);
                     summary.setMonth(month);
                     summary.setResultType(result);
                     summary.setActivityType(activityType);
                     summary.setNo_of_students_Participated(Integer.toString(studentActivityList.size()));
                     summary.setNo_of_StudentsOntheResult(Integer.toString( otherActivityResultwiseGroup.get(result).size()));  
                     dbg("nof of students participated"+summary.getNo_of_students_Participated(),session);
                     dbg("no of student on the result"+summary.getNo_of_StudentsOntheResult(),session);
                     dataset.add(summary);
                     
                     
                 }
             }
             

         }
         
         
         
        }
         
        
        if(dataset.isEmpty()){
             ClassOtherActivitySummary summary=new ClassOtherActivitySummary();
                     summary.setYear(" ");
                     summary.setMonth(" ");
                     summary.setResultType(" ");
                     summary.setActivityType(" ");
                     summary.setNo_of_students_Participated(" ");
                     summary.setNo_of_StudentsOntheResult(" ");  
                     dataset.add(summary);
            
            
        }
        
         
        
        dbg("end of getClassOtherActivitySummaryObject",session);
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
    
    
     private static String getYearandMonth(ClassOtherActivityDetail activityDetail){
      
        try{ 
         
       String  date=  activityDetail.getDATE();
       
       
       Date date1=new SimpleDateFormat(dateFormat).parse(date);  
       LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       int month = localDate.getMonthValue();
       int year=localDate.getYear();
      
       String year_and_month=Integer.toString(year).concat("y").concat(Integer.toString(month));
       
          return year_and_month;
        }catch(ParseException ex){
           return null;        
        }
      
      
  }
    
   
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
