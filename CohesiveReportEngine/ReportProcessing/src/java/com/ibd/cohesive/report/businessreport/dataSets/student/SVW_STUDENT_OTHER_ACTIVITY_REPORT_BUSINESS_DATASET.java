/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentOtherActivityDetail;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET {
    
    static String dateFormat;
    
    
    public ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS> getTableObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS>dataSet=new ArrayList();
        
        try{
        
        dbg("inside SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET--->getTableObject",session);    
        dateFormat=session.getCohesiveproperties().getProperty("DATE_FORMAT");

        try{
        
        
//                StudentDataSetBusiness studentDataSet=inject.getStudentDatasetBusiness();
                 StudentOtherActivityDetail_DataSet studentOtherActivityDetail=inject.getOtherActivityDetail();
                ArrayList<StudentOtherActivityDetail>studentOtherActivityList=studentOtherActivityDetail.getStudentOtherActivityDetailObject(p_studentID, p_instanceID, session, dbSession, inject);
                dbg("studentOtherActivityList"+studentOtherActivityList.size(),session);
             
                if(studentOtherActivityList.size()==1&&studentOtherActivityList.get(0).getActivityID().equals(" ")){
                    
                    dbg("There is no record in studentOtherActivityList",session);
                    
                }else{
                
                
                
                Map<String,List<StudentOtherActivityDetail>>yearWiseMap=studentOtherActivityList.stream().collect(Collectors.groupingBy(SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET::getYear));

                dbg("yearWiseMap"+yearWiseMap.size(),session);

                Iterator<String>yearIterator=yearWiseMap.keySet().iterator();

                  while(yearIterator.hasNext()){

                      String year=yearIterator.next();
                      dbg("year"+year,session);

                      List<StudentOtherActivityDetail>yearList=yearWiseMap.get(year);
                      dbg("yearList for"+year+" "+yearList.size(),session);
                      Map<String,List<StudentOtherActivityDetail>>levelWiseMap=yearList.stream().collect(Collectors.groupingBy(rec->rec.getLevel()));

                      dbg("levelWiseMap"+levelWiseMap.size(),session);

                      Iterator<String>levelIterator=levelWiseMap.keySet().iterator();

                      while(levelIterator.hasNext()){

                          String level=levelIterator.next();
                          dbg("level"+level,session);
                          List<StudentOtherActivityDetail>levelList=levelWiseMap.get(level);
                          dbg("levelList"+levelList.size(),session);

                          Map<String,List<StudentOtherActivityDetail>>resultWiseMap=levelList.stream().collect(Collectors.groupingBy(rec->rec.getResult()));

                          dbg("resultWiseMap"+resultWiseMap.size(),session);

                          Iterator<String>resultIterator=resultWiseMap.keySet().iterator();

                          while(resultIterator.hasNext()){

                              String result=resultIterator.next();
                              dbg("result"+result,session);
                              List<StudentOtherActivityDetail>resultList=resultWiseMap.get(result);
                              String count=Integer.toString(resultList.size());
                              dbg("count"+count,session  );
                              SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS report=new SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS();
                              report.setYEAR(year);
                              report.setCOUNT(count);
                              report.setLEVEL(level);
                              report.setRESULT_TYPE(result);
                              dataSet.add(report);
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
        
        
        if(dataSet.isEmpty()){
            
            SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS studentCalender=new SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS();
            studentCalender.setSTUDENT_ID(" ");
            studentCalender.setYEAR(" ");
            studentCalender.setLEVEL(" ");
            studentCalender.setRESULT_TYPE(" ");
            studentCalender.setCOUNT(" ");

            dataSet.add(studentCalender);
        }
        
        
        
        
       return dataSet;
    
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }finally{
            dateFormat=null;
        }
        
        
    }
    
    public static String getYear(StudentOtherActivityDetail studentOtherActivity){
      
        try{ 
         
       String  date=  studentOtherActivity.getDate();
       
       
       Date date1=new SimpleDateFormat(dateFormat).parse(date);  
       LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       int year=localDate.getYear();
      
       String yearStr=Integer.toString(year);
       
          return yearStr;
        }catch(ParseException ex){
           return null;        
        }
      
      
  }
    
    
    private ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS> convertDBtoReportObject(Map<String,DBRecord>p_attendanceMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS>dataset=new ArrayList();
        try{
            
            
            dbg("inside SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS convertDBtoReportObject",session);
            
            if(!(p_attendanceMap.isEmpty())){
                
             
                Iterator<DBRecord> recordIterator=p_attendanceMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    
                    DBRecord l_attendanceRecords=recordIterator.next();
                    SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS studentCalender=new SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS();
                    studentCalender.setSTUDENT_ID(l_attendanceRecords.getRecord().get(0).trim());
                    studentCalender.setYEAR(l_attendanceRecords.getRecord().get(1).trim());
                    studentCalender.setLEVEL(l_attendanceRecords.getRecord().get(2).trim());
                    studentCalender.setRESULT_TYPE(l_attendanceRecords.getRecord().get(3).trim());
                    studentCalender.setCOUNT(l_attendanceRecords.getRecord().get(4).trim());
                    
                    dbg("studentID"+studentCalender.getSTUDENT_ID() ,session);
                    dbg("year"+studentCalender.getYEAR() ,session);
                    
                    dataset.add(studentCalender);
                    
                }
            
            }
            dbg("end of SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET convertDBtoReportObject",session);
            
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
