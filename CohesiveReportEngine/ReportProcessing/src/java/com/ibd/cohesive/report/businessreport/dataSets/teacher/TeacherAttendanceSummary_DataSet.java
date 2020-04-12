/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherAttendanceSummary;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.EducationPeriod;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class TeacherAttendanceSummary_DataSet {
    
    public ArrayList<TeacherAttendanceSummary> getTeacherAttendanceSummary_DataSetObject(String p_teacherID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<TeacherAttendanceSummary> dataset=new ArrayList();
        
        try{
            dbg("inside getTeacherAttendanceSummary_DataSetObject",session);
            DecimalFormat df = new DecimalFormat("###.##");
            BusinessService bs=appInject.getBusinessService(session);
            ReportUtil reportUtil=inject.getReportUtil(session);
            EducationPeriod eduPeriod=bs.getEducationPeriod(p_instanceID, session, dbSession, appInject);
            String fromDate=eduPeriod.getFromDate();
            String toDate=bs.getCurrentDate();
            dbg("fromDate-->"+fromDate,session);
            dbg("toDate-->"+toDate,session);
            
            try{
            
            
                Map<String,Float>leaveCountMap=this.getLeaveCount(p_teacherID, p_instanceID, session, dbSession, inject, appInject);
                ConvertedDate from=bs.getYearMonthandDay(fromDate);
                ConvertedDate to=bs.getYearMonthandDay(toDate);
                int fromYear=Integer.parseInt(from.getYear());
                int fromMonth=Integer.parseInt(from.getMonth());
                int toYear=Integer.parseInt(to.getYear());
                int toMonth=Integer.parseInt(to.getMonth());
                int iteration=0;
                dbg("fromYear"+fromYear,session);
                dbg("fromMonth"+fromMonth,session);
                dbg("toYear"+toYear,session);
                dbg("toMonth"+toMonth,session);

                if(fromYear<toYear){

                  iteration= 12-fromMonth+toMonth;


               }else if(fromYear==toYear){

                   iteration= toMonth-fromMonth;
               }

               dbg("iteration"+iteration,session);

               int month=0;
               int year=0;

               for(int i=0;i<iteration;i++){

    //               String l_date=formatter.format(dateList.get(i));
    //               ConvertedDate convertedDate=this.getYearMonthandDay(l_date);

                   if(i==0){

                       month=fromMonth;
                       year=fromYear;
                   }else{

                       month=month+1;

                       if(month>12){

                           month=1;
                           year=year+1;
                       }
                   }


                   dbg("year-->"+year,session);
                   dbg("month-->"+month,session);

                   float workingDays=reportUtil.getNoOfWorkingDaysInMonth(p_instanceID, Integer.toString(year),  Integer.toString(month), session, dbSession, inject,appInject);
                   float leaveDays=0.0f;

                   if(leaveCountMap.containsKey(Integer.toString(year)+"~"+Integer.toString(month))){


                       leaveDays=leaveCountMap.get(Integer.toString(year)+"~"+Integer.toString(month));

                   }

                   float presentDays=workingDays-leaveDays;
                   float percentage=(presentDays/workingDays)*100;
                   dbg("workingDays"+workingDays,session);
                   dbg("leaveDays"+leaveDays,session);
                   dbg("presentDays"+presentDays,session);
                   dbg("percentage"+percentage,session);

                   TeacherAttendanceSummary summary=new TeacherAttendanceSummary();
                   summary.setYear(Integer.toString(year));
                   summary.setMonth(Integer.toString(month));
                   summary.setTeacherID(p_teacherID);
                   summary.setNo_of_WorkingDays(df.format(workingDays));
                   summary.setNo_of_Days_Present(df.format(presentDays));
                   summary.setNo_of_Days_Leave(df.format(leaveDays));
                   summary.setPercentage(df.format(percentage));
                   dataset.add(summary);
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
            
               TeacherAttendanceSummary summary=new TeacherAttendanceSummary();
               summary.setYear(" ");
               summary.setMonth(" ");
               summary.setTeacherID(" ");
               summary.setNo_of_WorkingDays(" ");
               summary.setNo_of_Days_Present(" ");
               summary.setNo_of_Days_Leave(" ");
               summary.setPercentage(" ");
               dataset.add(summary);
               
               
               
           }
            
            
       

       dbg("end of getTeacherAttendanceSummary_DataSetObject",session);
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
    
   private Map<String,Float> getLeaveCount(String l_teacherID,String instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
    
    
        try{ 
            dbg("inside getTeacherLeaveCount",session);
            BusinessService bs=appInject.getBusinessService(session);
            IDBReadBufferService readBuffer=inject.getDBReadBufferService();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            Map<String,DBRecord>teacherLeaveMap=null;
            EducationPeriod eduPeriod=bs.getEducationPeriod(instituteID, session, dbSession, appInject);
            String dateformat=i_db_properties.getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
            Date fromDate=formatter.parse(eduPeriod.getFromDate());
            Date toDate=formatter.parse(bs.getCurrentDate());
            
            try{
            
            
            teacherLeaveMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", session, dbSession);
            
            
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex,session);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
                }
            Map<String,Float>leaveMap=new HashMap();
            
            if(teacherLeaveMap!=null){
                
                List<DBRecord>leaveRecords=teacherLeaveMap.values().stream().filter(rec->rec.getRecord().get(9).trim().equals("O")&&rec.getRecord().get(10).trim().equals("A")).collect(Collectors.toList());
                
                
                
                
                Iterator<DBRecord>valueIterator=leaveRecords.iterator();
                
                
                while(valueIterator.hasNext()){
                    
                    ArrayList<String>value=valueIterator.next().getRecord();
                    String leaveFrom=value.get(1).trim();
                    String leaveTo=value.get(2).trim();
                    String fromNoon=value.get(14).trim();
                    String toNoon=value.get(15).trim();
                    dbg("leaveFrom"+leaveFrom,session);
                    dbg("leaveTo"+leaveTo,session);
                    dbg("fromNoon"+fromNoon,session);
                    dbg("toNoon"+toNoon,session);
                    
                    Date leaveFromDate=formatter.parse(leaveFrom);
                    Date leaveToDate=formatter.parse(leaveTo);
                    
                    ArrayList<Date>leaveList=null;
                    
                    if(leaveFromDate.compareTo(fromDate)>=0){
                    
                      leaveList=bs.getLeaveDates(leaveFrom, leaveTo, session, dbSession, appInject);
                    
                    
                    }
                    
                    if(leaveList!=null){
                    
                    for(int i=0;i<leaveList.size();i++){
                        
                        Date leaveDate=leaveList.get(i);
                        dbg("leaveDate"+leaveDate,session);
                        
                        if(leaveDate.compareTo(fromDate)>=0&&leaveDate.compareTo(toDate)<=0){
                            float leaveCount=0.0f;
                            dbg("leave date inside from date and to date",session);
                            
                            String leaveDateString=formatter.format(leaveDate);
                            char holidayChar=bs.getHolidayCharOfTheDay(instituteID, leaveDateString, session, dbSession, appInject);
                            dbg("holidayChar"+holidayChar,session);
                            
                            if(holidayChar=='W'){
                                
                                if(leaveDateString.equals(leaveFrom)) {
                                    
                                    if(fromNoon.equals("F")){
                                           
                                           leaveCount=leaveCount+0.5f;
                                    }else if(fromNoon.equals("A")){
                                        
                                           leaveCount=leaveCount+0.5f;
                                    }else{
                                            
                                          leaveCount=leaveCount+1;
                                        
                                     }
                                    
                                }else if(leaveDateString.equals(leaveTo)) {
                                    
                                    if(toNoon.equals("F")){
                                           
                                           leaveCount=leaveCount+0.5f;
                                    }else if(toNoon.equals("A")){
                                        
                                           leaveCount=leaveCount+0.5f;
                                    }else{
                                            
                                          leaveCount=leaveCount+1;
                                        
                                     }
                                }else{
                                    
                                    leaveCount=leaveCount+1;
                                }
                    
                               
                           }else if(holidayChar!='H'){
                            
                              if(leaveDateString.equals(leaveFrom)) {
                                  
                                   if (holidayChar=='F'){
                                       
                                       if(fromNoon.equals("D")){ //Full day Leave
                                            
                                          leaveCount=leaveCount+0.5f;
                                       }
                                       if(fromNoon.equals("A")){//After Noon Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
 
                                   }else if (holidayChar=='A'){
                                       
                                       if(fromNoon.equals("D")){//Full Day Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                         if(fromNoon.equals("F")){//Fore noon Leave 
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
                                   }
        
                                   
                              }else if(leaveDateString.equals(leaveTo)) {
                               
                                   if (holidayChar=='F'){
                                       
                                       if(toNoon.equals("D")){ //Full day Leave
                                            
                                          leaveCount=leaveCount+0.5f;
                                       }
                                       if(toNoon.equals("A")){//After Noon Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
 
                                   }else if (holidayChar=='A'){
                                       
                                       if(toNoon.equals("D")){//Full Day Leave
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                         if(toNoon.equals("F")){//Fore noon Leave 
                                           
                                           leaveCount=leaveCount+0.5f;
                                       }
                                       
                                       
                                   }
                              }else{
                                  
                                  leaveCount=leaveCount+0.5f;
                              }
                            
                           }
                         ConvertedDate leaveConvert=bs.getYearMonthandDay(leaveDateString);
                         String year= leaveConvert.getYear();
                         String month=leaveConvert.getMonth();
                            
                         if(leaveMap.containsKey(year+"~"+month)){
                         
                             leaveCount=leaveCount+leaveMap.get(year+"~"+month);
                             
                             leaveMap.put(year+"~"+month, leaveCount);
                            
                         }else{
                             
                             leaveMap.put(year+"~"+month, leaveCount);
                         }
                         
                        }
                        
                        
                    }
                    
                    }
                    
                }
                
                
                
            }
            
            
           dbg("end of getTeacherLeaveCount-->leaveMap size-->"+leaveMap.size(),session); 
        return leaveMap;
    }catch(DBValidationException ex){
           dbg(ex,session);
            throw ex;
//        }catch(BSValidationException ex){
//            throw ex;    
        }catch(DBProcessingException ex){
            dbg(ex,session);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex,session);
            throw new BSProcessingException("Exception" + ex.toString());
        }
}
    
    
  
     
   
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
