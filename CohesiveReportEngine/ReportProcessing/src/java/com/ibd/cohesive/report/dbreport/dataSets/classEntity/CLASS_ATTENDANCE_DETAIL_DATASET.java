/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.classEntity;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_DETAIL;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author IBD Technologies
 */
public class CLASS_ATTENDANCE_DETAIL_DATASET {
//    ArrayList<CLASS_ATTENDANCE_DETAIL> dataset;
    
    
    public ArrayList<CLASS_ATTENDANCE_DETAIL> getTableObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        
        
        try{
        
        dbg("inside CLASS_ATTENDANCE_DETAIL_DATASET--->getTableObject",session);    
        IDBReadBufferService readBuffer =inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        Map<String,DBRecord>l_classAttendanceMap=null;
        try
        {
        
        l_classAttendanceMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section+i_db_properties.getProperty("FOLDER_DELIMITER")+p_standard+p_section,"CLASS", "CLASS_ATTENDANCE_DETAIL", session, dbSession);
        }
        
        
          catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                
               
            }else{
                
                throw ex;
            }
        
        }
      
         
         dbg("end of CLASS_ATTENDANCE_DETAIL_DATASET--->getTableObject",session);  
        return   convertDBtoReportObject(l_classAttendanceMap,session) ;
        
        
        
        
        
       
    
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
        
    }
    
    
    
    
    private ArrayList<CLASS_ATTENDANCE_DETAIL> convertDBtoReportObject(Map<String,DBRecord>p_attendanceMap,CohesiveSession session)throws DBProcessingException{
    
        ArrayList<CLASS_ATTENDANCE_DETAIL>dataset=new ArrayList();
        try{
            
            
            dbg("inside CLASS_ATTENDANCE_DETAIL_DATASET convertDBtoReportObject",session);
            
            if(p_attendanceMap!=null&&!p_attendanceMap.isEmpty()){
                
             
                Iterator<DBRecord> recordIterator=p_attendanceMap.values().iterator();
                
                while(recordIterator.hasNext()){
                    DBRecord l_assignmentRecords=recordIterator.next();
                    CLASS_ATTENDANCE_DETAIL classAttendance=new CLASS_ATTENDANCE_DETAIL();
                    classAttendance.setREFERENCE_NO(l_assignmentRecords.getRecord().get(0).trim());
                    classAttendance.setSTUDENT_ID(l_assignmentRecords.getRecord().get(1).trim());
                    classAttendance.setATTENDANCE(l_assignmentRecords.getRecord().get(2).trim());
                
//                    String attendance=l_assignmentRecords.getRecord().get(4).trim();
                    
//                    Map<Integer ,Map<Integer,String>>attendanceMap= attendanceParsing(attendance,session);
//                    
//                    Iterator<Integer> dayIterator=attendanceMap.keySet().iterator();
//                    
//                    while(dayIterator.hasNext()){
//                        
//                        String day=Integer.toString(dayIterator.next());
//                       classAttendance.setDAY(day);
//                       
//                       Map<Integer,String>periodMap=attendanceMap.get(day);
//                       DayAttendance dayAtt=calculateAttendanceForaDay(periodMap,session);
//                        
//                       
//                       
//                    }
                   
                    dataset.add(classAttendance);
                    
                }
            
            }
                 else
            {
                CLASS_ATTENDANCE_DETAIL service=new CLASS_ATTENDANCE_DETAIL();
                 
                    service.setREFERENCE_NO(" ");
                    service.setSTUDENT_ID(" ");
                    service.setATTENDANCE(" ");
                 
                                                   
                    
                    
                    
                    
                    
 dataset.add(service);
                
            }
            
            
            dbg("end of CLASS_ATTENDANCE_DETAIL_DATASET convertDBtoReportObject",session);
            
        }catch(Exception ex){
            dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
       }
        
        return dataset;
        
    }
    
    
    private Map<Integer ,Map<Integer,String>> attendanceParsing(String p_attendance,CohesiveSession session)throws DBProcessingException{
        
        try{
         
         Map<Integer ,Map<Integer,String>> attendanceMap=new HashMap(); //outerMap key-->dayNymber   InnerMAp key--->periodNumber   value--->Attendance 
         String[] dayArray=   p_attendance.split("d");
            
         for(int i=0;i<dayArray.length;i++){
             
            String[] periodArray=  dayArray[i].split("p");
            Integer day= Integer.parseInt(periodArray[0]) ;  
            
            
            Map<Integer,String>periodMap=new HashMap();
           for(int j=1;j<periodArray.length;j++){
               
               Integer periodNumber=Integer.parseInt(periodArray[i].substring(0, 1));
               String attendance=periodArray[i].substring(1);
               
               periodMap.put(periodNumber, attendance);
               
           }
            attendanceMap.put(day, periodMap);
           
         }
         
            
            return attendanceMap;
         }catch(Exception ex){
            dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
       }
        
        
    }
    
    
    private DayAttendance calculateAttendanceForaDay(Map<Integer,String>p_periodMap,CohesiveSession session)throws DBProcessingException{
        
        try{
            
         DayAttendance dayAtt=new DayAttendance();   
         int halfDayAttendanceNoOfPeroids = Integer.parseInt( session.getCohesiveproperties().getProperty("HALFDAY_ATTENDANCE_NO_OF_PERIODS")) ;
         int fullDayAttendanceNoOfPeroids = Integer.parseInt( session.getCohesiveproperties().getProperty("FULLDAY_ATTENDANCE_NO_OF_PERIODS")) ;
         

          
          Map<String, List<String>>attendanceGroup=  p_periodMap.values().stream().collect(Collectors.groupingBy(rec->rec));
          
          Iterator<String> attendanceIterator=attendanceGroup.keySet().iterator();
          
          int presentPeriodCount=0;
          int absentPeriodCount=0;
          int leavePeriodCount=0;
          
          while(attendanceIterator.hasNext()){
              
              String attendance=attendanceIterator.next();
              
              if(attendance.equals("P")){
                  
                  presentPeriodCount++;
              }else if(attendance.equals("A")){
                  
                  absentPeriodCount++;
              }else if(attendance.equals("L")){
                  
                  leavePeriodCount++;
              }
   
          }
          
          
          if(leavePeriodCount>halfDayAttendanceNoOfPeroids){
              
              dayAtt.setAbsent(0);
              dayAtt.setLeave(1);
              dayAtt.setPresent(0);
              
              return dayAtt;
          }
          
          if(absentPeriodCount>halfDayAttendanceNoOfPeroids){
              
              dayAtt.setAbsent(1);
              dayAtt.setLeave(0);
              dayAtt.setPresent(0);
              return dayAtt;
              
          }
          
          if(presentPeriodCount==fullDayAttendanceNoOfPeroids){
              
              dayAtt.setAbsent(0);
              dayAtt.setLeave(0);
              dayAtt.setPresent(1);
              
              return dayAtt;
          }
          
          
          if(presentPeriodCount>=halfDayAttendanceNoOfPeroids){
              
            dayAtt.setPresent(0.5f);
              
              if(absentPeriodCount<halfDayAttendanceNoOfPeroids){
                  
                  dayAtt.setAbsent(0.5f);
                  dayAtt.setLeave(0);
                  
                  
                  return dayAtt;
              }else if(leavePeriodCount<halfDayAttendanceNoOfPeroids){
                  
                  dayAtt.setAbsent(0);
                  dayAtt.setLeave(0.5f);
                  
                  return dayAtt;
              }
              
          }
         
          
          
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
