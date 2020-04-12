
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.student.studentattendanceservice.AuditDetails;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassAttendanceDetail;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.DayAttendance;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.EducationPeriod;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.report.dbreport.dataSets.classEntity.CLASS_ATTENDANCE_DETAIL_DATASET;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class ClassAttendanceDetail_DataSet {
    
    
    public ArrayList<ClassAttendanceDetail> getClassAttendanceDetailObject(String standard,String section,String l_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<ClassAttendanceDetail> dataset=new ArrayList();
        
        try{
        
        dbg("inside getClassAttendanceDetailObject",session);    
        DecimalFormat df = new DecimalFormat("###.##");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        ReportUtil reportUtil=inject.getReportUtil(session);
//        ClassDataSet classDataSet=inject.getClassDataSet();
        CLASS_ATTENDANCE_DETAIL_DATASET classAttendance=inject.getClassAttendanceDetailDataSet();
        IPDataService pds=inject.getPdataservice();
        dbg("standard"+standard,session);
        dbg("section"+section,session);
        String[] l_pkey={l_instituteID,standard,section};
        ArrayList<String>classConfigList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
        String attendancetype=classConfigList.get(13).trim();
        BusinessService bs=appInject.getBusinessService(session);
        EducationPeriod eduPeriod=bs.getEducationPeriod(l_instituteID, session, dbSession, appInject);
        String fromDate=eduPeriod.getFromDate();
        String toDate=eduPeriod.getToDate();
        ConvertedDate from=bs.getYearMonthandDay(fromDate);
        int fromYear=Integer.parseInt(from.getYear());
        int fromMonth=Integer.parseInt(from.getMonth());
        ConvertedDate to=bs.getYearMonthandDay(toDate);
        int toYear=Integer.parseInt(to.getYear());
        int toMonth=Integer.parseInt(to.getMonth());
        dbg("fromYear"+fromYear,session);
        dbg("fromMonth"+fromYear,session);
        dbg("toYear"+fromYear,session);
        dbg("toMonth"+fromYear,session);
        ArrayList<CLASS_ATTENDANCE_DETAIL>classAttendanceDetail=null;
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        
          try{

           classAttendanceDetail=classAttendance.getTableObject(standard, section, l_instituteID, session,dbSession,inject);

          }catch(DBValidationException ex){

                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");

                }else{

                    throw ex;
                }


            }  
        
       if(classAttendanceDetail!=null){
          
          
                List<CLASS_ATTENDANCE_DETAIL>studentFilteredList=classAttendanceDetail.stream().collect(Collectors.toList());
                dbg("studentFilteredList size",session);

                for(int i=0;i<studentFilteredList.size();i++){

                    dbg("inside iteration",session);
                    CLASS_ATTENDANCE_DETAIL attendanceTableObject=studentFilteredList.get(i);
                    
                    String refNo=attendanceTableObject.getREFERENCE_NO().replace("*", "~");
                    String YEAR=refNo.split("~")[2];
                    String MONTH=refNo.split("~")[3];
                    
                    
//                    String[] l_masterPkey={standard,section,YEAR,MONTH};
//                    DBRecord masterRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section+i_db_properties.getProperty("FOLDER_DELIMITER")+standard+section,"CLASS", "CLASS_ATTENDANCE_MASTER", l_masterPkey, session, dbSession);
//    
//                    AuditDetails auditDetails=bs.getClassAuditDetails(masterRecord.getRecord().get(4).trim(), MONTH);
//            
//            if(auditDetails.getAuthStatus().equals("A")&&auditDetails.getRecordStatus().equals("O")){
                    
                    
                    int attYear=Integer.parseInt(YEAR);
                    int attMonth=Integer.parseInt(MONTH);
                    dbg("attYear-->"+attYear,session);
                    dbg("attMonth-->"+attMonth,session);

                     if(attYear>=fromYear&&attYear<=toYear){
                        dbg("first if satisfied",session);

                   if((fromYear==attYear&&attMonth>=fromMonth)||(toYear==attYear&&attMonth<=toMonth)){  
                        dbg("second if satisfied",session);
                        ClassAttendanceDetail summary=new ClassAttendanceDetail();
                        summary.setStudentID(attendanceTableObject.getSTUDENT_ID());
                        summary.setStudentName(bs.getStudentName(summary.getStudentID(), l_instituteID, session, dbSession, appInject));
                        summary.setYear(YEAR);
                        summary.setMonth(MONTH);
                        summary.setMonthString(reportUtil.getMonthValueInString(summary.getMonth()));

                        String attendance=attendanceTableObject.getATTENDANCE();

                        float no_OfDaysPresent=0f;
                        float no_ofDaysAbsent=0f;
                        float no_ofDaysLeave=0f;
                        Map<String ,Map<String,String>>attendanceMap= attendanceParsing(attendance,attendancetype,session);

                           Iterator<String> dayIterator=attendanceMap.keySet().iterator();

                                while(dayIterator.hasNext()){

                                    String day=dayIterator.next();
                                    Map<String,String>periodMap=attendanceMap.get(day);
                                    DayAttendance dayAtt=calculateAttendanceForaDay(l_instituteID,standard,section,attendancetype,periodMap,session,dbSession,inject);

                                    no_OfDaysPresent=no_OfDaysPresent+ dayAtt.getPresent();
                                    no_ofDaysAbsent=no_ofDaysAbsent+dayAtt.getAbsent();
                                    no_ofDaysLeave=no_ofDaysLeave+dayAtt.getLeave();

                                }
                            summary.setNo_of_DaysPresent(df.format(no_OfDaysPresent));
                            summary.setNo_of_DaysAbsent(df.format(no_ofDaysAbsent));
                            summary.setNo_ofDaysLeave(df.format(no_ofDaysLeave));
                            float workingDays=reportUtil.getNoOfWorkingDaysInMonth(l_instituteID, YEAR, MONTH, session, dbSession, inject,appInject);
                            float percentage=(no_OfDaysPresent/workingDays)*100;
                            summary.setNo_of_workingDays(df.format(workingDays));
                            summary.setPercentage(df.format(Math.round(percentage)));
                            dbg("no_OfDaysPresent"+no_OfDaysPresent,session);
                            dbg("no_ofDaysAbsent"+no_ofDaysAbsent,session);  
                            dbg("no_ofDaysLeave"+no_ofDaysLeave,session);  
                            dbg("workingDays"+workingDays,session);  
                            dbg("percentage"+percentage,session);
                            dataset.add(summary);
                    }
                    }
//                }
                }
        
       }
        
       
       if(dataset.isEmpty()){
           
           ClassAttendanceDetail summary=new ClassAttendanceDetail();
           summary.setStudentID(" ");
           summary.setYear(" ");
           summary.setMonth(" ");
           summary.setNo_of_DaysPresent(" ");
           summary.setNo_of_DaysAbsent(" ");
           summary.setNo_ofDaysLeave(" ");
           summary.setNo_of_workingDays(" ");
           summary.setPercentage(" ");
           dataset.add(summary);
       }
       
           
            
       
       dbg("end of getClassAttendanceDetailObject",session);
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
    
    
  private Map<String ,Map<String,String>> attendanceParsing(String p_attendance,String attendanceType,CohesiveSession session)throws DBProcessingException{
        
        try{
         
         dbg("inside attendanceParsing",session); 
         dbg("attendance type"+attendanceType,session);
         dbg("p_attendance"+p_attendance,session);
         Map<String ,Map<String,String>> attendanceMap=new HashMap(); //outerMap key-->dayNymber   InnerMAp key--->periodNumber   value--->Attendance 
         String[] dayArray=   p_attendance.split("d");
         
         for(int i=1;i<dayArray.length;i++){
             
                String dayRecord=dayArray[i];
                dbg("dayRecord"+dayRecord,session);
                String l_day=null;
                
                String dayAttendance=dayRecord.split(",")[0];
         
                if(dayAttendance.contains(" ")){
                    
                    l_day=dayAttendance.split(" ")[0];
                }else if(dayAttendance.contains("p")){
                    
                    l_day=dayAttendance.split("p")[0];
                }
                dbg("l_day"+l_day,session);
                Map<String,String>versionFilteredMap=null;
             
                try{
                
                   versionFilteredMap=getMaxVersionAttendanceOftheDay (p_attendance,l_day,session) ; 
              
                }catch(BSValidationException ex){
                    
                    if(ex.toString().contains("BS_VAL_013")){
                        
                        
                    }else{
                        throw ex;
                    }
                }
                 
                if(versionFilteredMap!=null){
                
                
                String max_version_Attendance=versionFilteredMap.get(l_day);
                dbg("max_version_Attendance"+max_version_Attendance,session);
                String[] dayAttArray=max_version_Attendance.split(",");
                String attendanceRecord= dayAttArray[0];
                dbg("attendanceRecord"+attendanceRecord,session);
                String l_foreNoonAttendance=attendanceRecord.split("n")[0];
                String l_afterNoonAttendance=attendanceRecord.split("n")[1];
                dbg("l_foreNoonAttendance"+l_foreNoonAttendance,session);
                dbg("l_afterNoonAttendance"+l_afterNoonAttendance,session);
                String[] foreNoonArray=  l_foreNoonAttendance.split("p");
                String[] afterNoonArray=  l_afterNoonAttendance.split("p");
                
                Map<String,String>periodMap=new HashMap();
                
                if(attendanceType.equals("P")){
                
                
                    for(int j=1;j<foreNoonArray.length;j++){

                       String periodNumber=foreNoonArray[j].substring(0, 1);
                       String attendance=foreNoonArray[j].substring(1);
                       dbg("inside foreNoon iteration-->periodNumber"+periodNumber,session);
                       dbg("inside foreNoon iteration-->attendance"+attendance,session);
                       periodMap.put(periodNumber, attendance);

                    }


                    for(int j=1;j<afterNoonArray.length;j++){

                       String periodNumber=afterNoonArray[j].substring(0, 1);
                       String attendance=afterNoonArray[j].substring(1);
                       dbg("inside afterNoon iteration-->periodNumber"+periodNumber,session);
                       dbg("inside afterNoon iteration-->attendance"+attendance,session);
                       periodMap.put(periodNumber, attendance);

                    }
           
                  dbg("attendance type P-->periodMap size"+periodMap.size(),session);
                }else if(attendanceType.equals("N")){
                    
                    for(int j=1;j<foreNoonArray.length;j++){

                       String attendance=foreNoonArray[j].substring(1);
                       dbg("inside foreNoon iteration-->attendance"+attendance,session);

                       periodMap.put("F", attendance);

                    }


                    for(int j=1;j<afterNoonArray.length;j++){

                               String attendance=afterNoonArray[j].substring(1);
                               dbg("inside afterNoon iteration-->attendance"+attendance,session);
                               periodMap.put("A", attendance);

                    }
                    
                    
                    
                }else {if(attendanceType.equals("D")){
                    
                     for(int j=1;j<foreNoonArray.length;j++){

                       String attendance=foreNoonArray[j].substring(1);
                       dbg("inside foreNoon iteration-->attendance"+attendance,session);
                       periodMap.put("D", attendance);

                    }

                    
                }
            
           
         }
                attendanceMap.put(l_day, periodMap);
                
                } 
                
         }
            dbg("attendanceMap key size"+attendanceMap.keySet().size(),session);
            dbg("attendanceMap value size"+attendanceMap.values().size(),session);
            dbg("end of attendanceParsing",session);
            return attendanceMap;
         }catch(Exception ex){
            dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
       }
        
        
    }
     public Map<String,String> getMaxVersionAttendanceOftheDay(String p_monthAttendance,String p_day,CohesiveSession session)throws DBProcessingException,BSValidationException{
        
        try{
            dbg("inside getMaxVersionAttendanceOftheDay",session);
            dbg("p_monthAttendance"+p_monthAttendance,session);
            dbg("p_day"+p_day,session);
            String[] attendanceArray=p_monthAttendance.split("d");
            dbg("attendanceArray"+attendanceArray.length,session);
            ArrayList<String>recordsFor_a_day=new ArrayList();
            String filterkey_dummy=p_day;
            for(int i=1;i<attendanceArray.length;i++){
                String dayRecord=attendanceArray[i];
                dbg("dayRecord"+dayRecord,session);
                String l_day=null;
                
                String dayAttendance=dayRecord.split(",")[0];
         
                if(dayAttendance.contains(" ")){
                    
                    l_day=dayAttendance.split(" ")[0];
                }else if(dayAttendance.contains("p")){
                    
                    l_day=dayAttendance.split("p")[0];
                }
                
                dbg("l_day"+l_day,session);
                if(l_day.equals(p_day)){
                    
                    dbg("l_day.equals(p_day)",session);
                   
                    if(dayRecord.contains(",")){
                    
                       recordsFor_a_day.add(dayRecord);
                    
                    }
                }
                
            }
            dbg("recordsFor_a_day size"+recordsFor_a_day.size(),session);
            
            if(recordsFor_a_day.isEmpty()){
                
                session.getErrorhandler().log_app_error("BS_VAL_013", null);
                throw new BSValidationException("BS_VAL_013");
            }
            
            
            Map<String,String>filtermap_dummy=new HashMap();
            if(recordsFor_a_day.size()>1){
                int max_vesion=recordsFor_a_day.stream().mapToInt(rec->Integer.parseInt(rec.split(",")[1])).max().getAsInt();
                recordsFor_a_day.stream().filter(rec->Integer.parseInt(rec.split(",")[1])==max_vesion).forEach(rec->filtermap_dummy.put(filterkey_dummy, rec));           
                dbg("max_vesion"+max_vesion,session);
            }else{
                
                filtermap_dummy.put(p_day, recordsFor_a_day.get(0).trim());
            }
            
            dbg("end of  getMaxVersionAttendanceOftheDay",session);
            return filtermap_dummy;
            
        }catch (BSValidationException ex) {
            throw ex;

        }catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());

        }
        
    }
    
    private DayAttendance calculateAttendanceForaDay(String l_instituteID,String l_standard,String l_section,String attendanceType,Map<String,String>p_periodMap,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException{
        
        try{
         ReportUtil reportUtil=inject.getReportUtil(session);   
         IPDataService pds=inject.getPdataservice();
         IBDProperties i_db_properties=session.getCohesiveproperties();
         DayAttendance dayAtt=new DayAttendance(); 
         float presentCount=0;
         float absentCount=0;
         float leaveCount=0;
         
         if(attendanceType.equals("P")){
         
                 Map<String,ArrayList<String>>l_periodList=pds.readTablePData("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",session,dbSession);
                 dbg("l_periodList size"+l_periodList.size(),session);
                 String masterVersion=reportUtil.getMaxVersionOfTheClass(l_instituteID, l_standard, l_section, session, dbSession, inject);
                 int versionIndex=reportUtil.getVersionIndexOfTheTable("IVW_PERIOD_MASTER", "INSTITUTE", session, dbSession, inject);

                 Map<String,List<ArrayList<String>>>foreNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(9).trim().equals("F")&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
                 dbg("foreNoonPeriodList size"+foreNoonPeriodList.size(),session);
                 Map<String,List<ArrayList<String>>>afterNoonPeriodList=l_periodList.values().stream().filter(rec->rec.get(1).trim().equals(l_standard)&&rec.get(2).trim().equals(l_section)&&rec.get(9).trim().equals("A")&&rec.get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.get(3).trim()));
                 dbg("afterNoonPeriodList size"+afterNoonPeriodList.size(),session);


                 Iterator<String>foreNoonIterator=foreNoonPeriodList.keySet().iterator();


                 boolean absentExistence=false;
                 boolean leaveExistence=false;
                 while(foreNoonIterator.hasNext()){

                     String periodNumber=foreNoonIterator.next();
                     String periodAttendance=p_periodMap.get(periodNumber);

                     if(periodAttendance.equals("A")){

                         absentExistence=true;
                     }
                     if(periodAttendance.equals("L")){

                         leaveExistence=true;
                     }
                 }
                 if(absentExistence){

                     absentCount=absentCount+0.5f;
                 }else if(leaveExistence){

                     leaveCount=leaveCount+0.5f;
                 }else{
                     presentCount=presentCount+0.5f;
                 }

                 Iterator<String>afterNoonIterator=afterNoonPeriodList.keySet().iterator();
                 absentExistence=false;
                 leaveExistence=false;
                 while(afterNoonIterator.hasNext()){

                     String periodNumber=afterNoonIterator.next();
                     String periodAttendance=p_periodMap.get(periodNumber);

                     if(periodAttendance.equals("A")){

                         absentExistence=true;
                     }
                     if(periodAttendance.equals("L")){

                         leaveExistence=true;
                     }
                 }

                 if(absentExistence){

                     absentCount=absentCount+0.5f;
                 }else if(leaveExistence){

                     leaveCount=leaveCount+0.5f;
                 }else{
                     presentCount=presentCount+0.5f;
                 }
         
         
         } if(attendanceType.equals("N")){
             
             
             String foreNoonAtt=p_periodMap.get("F");
             
             if(foreNoonAtt.equals("A")){
                 
                 absentCount=absentCount+0.5f;
             }else if(foreNoonAtt.equals("L")){
                 
                 leaveCount=leaveCount+0.5f;
             }else{
                 
                 presentCount=presentCount+0.5f;
             }
             
             
             String afterNoonAtt=p_periodMap.get("A");
             
             if(afterNoonAtt.equals("A")){
                 
                 absentCount=absentCount+0.5f;
             }else if(afterNoonAtt.equals("L")){
                 
                 leaveCount=leaveCount+0.5f;
             }else{
                 
                 presentCount=presentCount+0.5f;
             }
             
         }if(attendanceType.equals("D")){
             
             
             String dayAttendance=p_periodMap.get("D");
             
             if(dayAttendance.equals("A")){
                 
                 absentCount=absentCount+1;
             }else if(dayAttendance.equals("L")){
                 
                 leaveCount=leaveCount+1;
             }else{
                 
                 presentCount=presentCount+1;
             }
             
         }
         
         
        dayAtt.setAbsent(absentCount);
        dayAtt.setLeave(leaveCount);
        dayAtt.setPresent(presentCount);
          
          
                  return dayAtt;

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
