/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.app.business.classentity.classtimetable.ClassTimeTable;
import com.ibd.cohesive.app.business.teacher.teachertimetable.Period;
import com.ibd.cohesive.app.business.teacher.teachertimetable.TeacherTimeTable;
import com.ibd.cohesive.app.business.teacher.teachertimetable.TimeTable;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteAvailabilityInSameClass;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.db.transaction.transactioncontol.ITransactionControlService;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteAvailabilityInOtherClass;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
public class SubstituteAvailabilityInSameClass_DataSet {
     
    
    public ArrayList<SubstituteAvailabilityInSameClass> getSubstituteAvailabilityInSameClass_DataSet(String p_leaveTeacher,String p_instituteID,String p_date,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String userID)throws DBProcessingException,DBValidationException{
        ArrayList<SubstituteAvailabilityInSameClass> dataset=new ArrayList();
        try{
  
        dbg("inside getSubstituteAvailabilityInSameClass_DataSet",session);    
        ReportUtil reportUtil=inject.getReportUtil(session);
        String day=reportUtil.getDay(p_date);
        dbg("day"+day,session);
        BusinessService bs=appInject.getBusinessService(session);
        
        ArrayList<String>classesForTheDay=this.getClassesForTheDay(p_leaveTeacher, p_instituteID, day, session, dbSession, inject, appInject);
        dbg("classesForTheDay-->"+classesForTheDay.size(),session);
        
        ArrayList<String> teachersOfTheClass=new ArrayList();
        for(int i=0;i<classesForTheDay.size();i++){
            
            String classs=classesForTheDay.get(i);
            dbg("inside classesForTheDay iteration"+classs,session);
            this.getTeachersOfTheClass(classs, p_instituteID, session, dbSession, inject, appInject,teachersOfTheClass);
            
        }
        
        dbg("teachersOfTheClass "+teachersOfTheClass.size(),session);
        
        for(int j=0;j<teachersOfTheClass.size();j++){
            
            String teacherID=teachersOfTheClass.get(j);
            
            if(!teacherID.equals(p_leaveTeacher)){
            
                dbg("teacherID"+teacherID,session);

                ArrayList<FreeTime>freeTimesOfTeacher=this.getFreeTimingsOfTheTeacher(teacherID, p_instituteID, day, session, dbSession, inject, appInject, p_date);
                dbg("freeTimesOfTeacher size"+freeTimesOfTeacher.size(),session);

                if(!freeTimesOfTeacher.isEmpty()){

                    String teacherName=bs.getTeacherName(teacherID, p_instituteID, session, dbSession, appInject);
                    String classs=bs.getClassOfTheTeacher(p_instituteID, teacherID, session, dbSession, appInject);
                    dbg("teacherName"+teacherName,session);
                    dbg("classs"+classs,session);
                    for(int i=0;i<freeTimesOfTeacher.size();i++){
                         dbg("inside freeTimesOfTeacher iteration",session);
                        String freeStartTime=freeTimesOfTeacher.get(i).getStartTime();
                        String freeEndTime=freeTimesOfTeacher.get(i).getEndTime();
                        int startTimeNo=freeTimesOfTeacher.get(i).getStartTime_no();
                        SubstituteAvailabilityInSameClass sameClass=new SubstituteAvailabilityInSameClass();
                        sameClass.setTeacherID(teacherID);
                        sameClass.setTeacherName(teacherName);
                        sameClass.setFreeStartTime(freeStartTime);
                        sameClass.setFreeEndTime(freeEndTime);
                        sameClass.setStartTimeNo(startTimeNo);
                        sameClass.setClasss(classs);
                        dataset.add(sameClass);

                    }
            
            }
            
        }
        }
        
        dbg("dataset size"+dataset.size(),session);
        this.updateSubstituteRecords(dataset, session, dbSession, inject, userID,p_leaveTeacher);
        if(dataset.isEmpty()){
            
            SubstituteAvailabilityInSameClass sameClass=new SubstituteAvailabilityInSameClass();
            sameClass.setTeacherID(" ");
            sameClass.setTeacherName(" ");
            sameClass.setFreeStartTime(" ");
            sameClass.setFreeEndTime(" ");
            sameClass.setStartTimeNo(0);
            sameClass.setClasss(" ");
            dataset.add(sameClass);
            
            
        }
         dbg("end of sub avail in same cls",session);
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
    
   
    
    
    private void updateSubstituteRecords(ArrayList<SubstituteAvailabilityInSameClass> dataset,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,String userID,String leaveTeacher)throws DBProcessingException,DBValidationException{
        ITransactionControlService tc=null;
     try{
         dbg("inside updateSubstituteRecords",session);
         tc=inject.getTransactionControlService();
         IDBTransactionService dbts=inject.getDBTransactionService();
         IBDProperties i_db_properties=session.getCohesiveproperties();
         IDBReadBufferService readBuffer=inject.getDBReadBufferService();
         
         
         for(int i=0;i<dataset.size();i++){
             
             SubstituteAvailabilityInSameClass sameClass=dataset.get(i);
             String teacherID=sameClass.getTeacherID();
             String teacherName=sameClass.getTeacherName();
             String classs=sameClass.getClasss();
             String standard;
             String section;
             
            if(classs.isEmpty()){
                
                standard=" ";
                section=" ";
                
            }else{
                
                standard=classs.split("/")[0];
                section=classs.split("/")[1];
            }
             
             
             
             
             
             
             
             
             
             String startTime=sameClass.getFreeStartTime();
             String endTime=sameClass.getFreeEndTime();
             
             String[] pkey={teacherID,startTime,endTime,leaveTeacher};
             boolean recordExistence=false;

             try{


                 readBuffer.readRecord("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT", "SUBSTITUTE_AVAIL_SAME_CLASS", pkey, session, dbSession);

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
                 l_columnToUpdate.put("2", teacherName);
                 l_columnToUpdate.put("3", standard);
                 l_columnToUpdate.put("4", section);



                 dbts.updateColumn("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT", "SUBSTITUTE_AVAIL_SAME_CLASS", pkey, l_columnToUpdate, session);

             }else{




              dbts.createRecord(session,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT",330,teacherID,teacherName,standard,section,startTime,endTime,leaveTeacher);


             }
             
             
             
         }
         
         
         
         
         
         tc.commit(session, dbSession);
         dbg("end of updateSubstituteRecords",session);
     }catch(DBProcessingException ex){
          dbg(ex,session);
          tc.rollBack(session, dbSession);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          tc.rollBack(session, dbSession);
          throw ex;
     }catch(Exception ex){
         dbg(ex,session);
         tc.rollBack(session, dbSession);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
    
    }
    
    
    
    
    
    
    
    
    public ArrayList<String>getClassesForTheDay(String p_teacherID,String p_instituteID,String p_day,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        try{
            dbg("inside getClassesForTheDay",session);
            ArrayList<String>classList=new ArrayList();
            TeacherTimeTable teacherTT=this.teacherTimeTableDBView(p_instituteID, p_teacherID, session, dbSession, inject, appInject);
            
            
            for(int i=0;i<teacherTT.timeTable.length;i++){
                
                String l_day=teacherTT.timeTable[i].getDay();
                
                if(l_day.equals(p_day)){
                    
                    for(int j=0;j<teacherTT.timeTable[i].period.length;j++){
                        
                     
                        String standard=teacherTT.timeTable[i].period[j].getStandard();
                        String section=teacherTT.timeTable[i].period[j].getSection();
                        
                        if(!classList.contains(standard+"/"+section)){
                        
                           classList.add(standard+"/"+section);
                        
                        }
                        
                    }
                    
                    
                    
                    
                }
                
                
            }
            
            
            
            dbg("inside getClassesForTheDay--->classList size"+classList.size(),session);
            
            return classList;
            
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
    
    public ArrayList<String>getTeachersOfTheClass(String classs,String p_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,ArrayList<String>teachersList)throws DBProcessingException,DBValidationException{
        
        try{
            dbg("inside getTeachersOfTheClass",session);
            String standard=classs.split("/")[0];
            String section=classs.split("/")[1];
            dbg("standard"+standard,session);
            dbg("section"+section,session);
            
            ClassTimeTable classTimeTable=this.classTimeTableDBView(p_instituteID, standard, section, session, dbSession, inject, appInject);
            
            for(int i=0;i<classTimeTable.timeTable.length;i++){
                
                for(int j=0;j<classTimeTable.timeTable[i].period.length;j++){
                 
                    String teacherID=classTimeTable.timeTable[i].period[j].getTeacherID();
                    
                    if(!teachersList.contains(teacherID)){
                        
                        teachersList.add(teacherID);
                        
                    }
                    
                }
                
                
           
            
            }
            
            
            dbg("end of getTeachersOfTheClass",session);
            return teachersList;
            
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
    
     public ArrayList<FreeTime>getFreeTimingsOfTheTeacher(String p_teacherID,String p_instituteID,String p_day,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String date)throws DBProcessingException,DBValidationException{
        
        try{
            dbg("inside getFreeTimingsOfTheTeacher",session);
            ArrayList<FreeTime>freeTimeList=new ArrayList();
            IPDataService pds=inject.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            BusinessService bs=appInject.getBusinessService(session);
            
            String leaveStatus=this.getTeacherLeaveStatus(p_teacherID, p_instituteID, date, session, dbSession, inject, appInject);
            dbg("leaveStatus"+leaveStatus,session);
            
            if(!leaveStatus.equals("L")){
            
            
            TeacherTimeTable teacherTT=this.teacherTimeTableDBView(p_instituteID, p_teacherID, session, dbSession, inject, appInject);
            
            dbg("after teacherTimeTableDBView",session);
            
            
            for(int i=0;i<teacherTT.timeTable.length;i++){
                
                String l_day=teacherTT.timeTable[i].getDay();
                dbg("inside getFreeTimingsOfTheTeacher day--> "+l_day,session);
                
                if(l_day.equals(p_day)){
                    
                    dbg("day equals",session);
                    dbg("period length"+teacherTT.timeTable[i].period.length,session);
                    
                    for(int j=0;j<teacherTT.timeTable[i].period.length;j++){
                        
                        dbg("j-->"+j,session);
                        
                            
                                String periodNo=teacherTT.timeTable[i].period[j].getPeriodNumber();
                                dbg("periodNo-->"+periodNo,session);
                                String standard=teacherTT.timeTable[i].period[j].getStandard();
                                String section=teacherTT.timeTable[i].period[j].getSection();
                                String lastPeriod=bs.getLastPeriod(p_instituteID, standard, section, session, dbSession, appInject);
                                
                                
                                if(j==0&&!periodNo.equals("1")){
                                    
                                    this.firstCase(p_instituteID, standard, section, periodNo, leaveStatus, freeTimeList, session, dbSession, inject);
                                    
                                    
                                
                                
                                
                                
                                    if(j==teacherTT.timeTable[i].period.length-1&&!periodNo.equals(lastPeriod)){


                                        this.secondCase(p_instituteID, standard, section, periodNo, leaveStatus, lastPeriod, freeTimeList, session, dbSession, inject);



                                    }

                                    return freeTimeList;
                                }
                                if(j==teacherTT.timeTable[i].period.length-1&&!periodNo.equals(lastPeriod)){


                                        this.secondCase(p_instituteID, standard, section, periodNo, leaveStatus, lastPeriod, freeTimeList, session, dbSession, inject);



                                    }
                                else{
                                
                                
                                    String[] pkey={p_instituteID,standard,section,periodNo};
                                    ArrayList<String>periodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey);

                                    String endTimeHour=periodlist.get(6).trim();
                                    String endTimeMin=periodlist.get(7).trim();
                                    String noon=periodlist.get(9).trim();

                                    if(leaveStatus.equals("W")||!noon.equals(leaveStatus)){


                                        int previosPeriodEndTime=Integer.parseInt(endTimeHour+endTimeMin);

                                        String nextperiodNo=teacherTT.timeTable[i].period[j+1].getPeriodNumber();
                                        
                                        
                                        if(Integer.parseInt(nextperiodNo)!=Integer.parseInt(periodNo)+1){
                                        
                                            String nextstandard=teacherTT.timeTable[i].period[j+1].getStandard();
                                            String nextsection=teacherTT.timeTable[i].period[j+1].getSection();

                                            String[] nextpkey={p_instituteID,nextstandard,nextsection,nextperiodNo};
                                            ArrayList<String>nextperiodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",nextpkey);

                                            String nextstartTimeHour=nextperiodlist.get(4).trim();
                                            String nextstartTimeMin=nextperiodlist.get(5).trim();

                                            int nextPeriodStartTime=Integer.parseInt(nextstartTimeHour+nextstartTimeMin);


                                            if(previosPeriodEndTime!=nextPeriodStartTime&&previosPeriodEndTime<nextPeriodStartTime){


                                                String freeStartTime=endTimeHour+":"+endTimeMin;
                                                String freeEndTime=nextstartTimeHour+":"+nextstartTimeHour;
                                                int startTimeNo=previosPeriodEndTime;
                                                FreeTime free=new FreeTime();
                                                free.setStartTime(freeStartTime);
                                                free.setEndTime(freeEndTime);
                                                free.setStartTime_no(startTimeNo);
                                                freeTimeList.add(free);

                                            }


                                        } 
                                        
                                        
                            
                        
                                }
                        
                        
                        }
                        
                        
                        
                        
                        }
                    
                    
                    
                    
                }
                
                
            }
            
            }
            
            
            dbg("end of getFreeTimingsOfTheTeacher",session);
            return freeTimeList;
            
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
    
     
     private void firstCase(String instituteID,String standard,String section,String currentperiodNo,String leaveStatus,ArrayList<FreeTime>freeTimeList,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
         
         try{
             
             dbg("inside firstCase",session);
             
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IPDataService pds=inject.getPdataservice();
             String[] pkey={instituteID,standard,section,"1"};
             ArrayList<String>firstPeriodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey);

             String firstStartHour=firstPeriodlist.get(4).trim();
             String firstStartMin=firstPeriodlist.get(5).trim();
             String noon=firstPeriodlist.get(9).trim();

             String[] pkey1={instituteID,standard,section,currentperiodNo};
             ArrayList<String>periodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey1);

             String currentStartHour=periodlist.get(4).trim();
             String currentStartMin=periodlist.get(5).trim();
             
             if(leaveStatus.equals("W")||!noon.equals(leaveStatus)){


                                    int firstPeriodStartTime=Integer.parseInt(firstStartHour+firstStartMin);


                                    int currentPeriodStartTime=Integer.parseInt(currentStartHour+currentStartMin);


                                    if(firstPeriodStartTime!=currentPeriodStartTime&&firstPeriodStartTime<currentPeriodStartTime){


                                        String freeStartTime=firstStartHour+":"+firstStartMin;
                                        String freeEndTime=currentStartHour+":"+currentStartMin;
                                        int startTimeNo=currentPeriodStartTime;
                                        FreeTime free=new FreeTime();
                                        free.setStartTime(freeStartTime);
                                        free.setEndTime(freeEndTime);
                                        free.setStartTime_no(startTimeNo);
                                        freeTimeList.add(free);

                                    }


                            
                        
                        
                        
                        
                        }
             
             
             
             dbg("end of firstCase",session);
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
     
     
     private void secondCase(String instituteID,String standard,String section,String currentperiodNo,String leaveStatus,String lastPeriod,ArrayList<FreeTime>freeTimeList,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
         
         try{
             
             
             dbg("inside secondCase",session);
             
             IBDProperties i_db_properties=session.getCohesiveproperties();
             IPDataService pds=inject.getPdataservice();
             String[] pkey={instituteID,standard,section,currentperiodNo};
             ArrayList<String>currentPeriodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey);

             String currentEndHour=currentPeriodlist.get(6).trim();
             String currentEndMin=currentPeriodlist.get(7).trim();
             String noon=currentPeriodlist.get(9).trim();

             String[] pkey1={instituteID,standard,section,lastPeriod};
             ArrayList<String>lastPeriodlist= pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID, "INSTITUTE", "IVW_PERIOD_MASTER",pkey1);

             String lastEndHour=lastPeriodlist.get(6).trim();
             String lastEndMin=lastPeriodlist.get(7).trim();
             
             if(leaveStatus.equals("W")||!noon.equals(leaveStatus)){


                                    int currentPeriodEndTime=Integer.parseInt(currentEndHour+currentEndMin);


                                    int nextPeriodStartTime=Integer.parseInt(lastEndHour+lastEndMin);


                                    if(currentPeriodEndTime!=nextPeriodStartTime&&currentPeriodEndTime<nextPeriodStartTime){


                                        String freeStartTime=currentEndHour+":"+currentEndMin;
                                        String freeEndTime=lastEndHour+":"+lastEndMin;
                                        int startTimeNo=currentPeriodEndTime;
                                        FreeTime free=new FreeTime();
                                        free.setStartTime(freeStartTime);
                                        free.setEndTime(freeEndTime);
                                        free.setStartTime_no(startTimeNo);
                                        freeTimeList.add(free);

                                    }


                            
                        
                        
                        
                        
                        }
             
             
             
             dbg("end of secondCase",session);
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
     
     
     
     
    public TeacherTimeTable teacherTimeTableDBView(String l_instituteID,String l_teacherID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside tteacherTimeTableDBView",session);    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        
        //DBRecord timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_TIMETABLE_MASTER", l_pkey, session, dbSession);
        //Map<String,DBRecord>detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_teacherID,"TEACHER", "TVW_TEACHER_TIMETABLE_DETAIL", session, dbSession);
        
        //Map<String,DBRecord> timeTableRec =readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS",session, dbSession);
        Map<String,DBRecord>detailMap;
        try
        {   
          // timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS", "CLASS_TIMETABLE_MASTER", l_pkey1, session, dbSession);
        
            detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TimeTable","INSTITUTE","IVW_SUBJECT_DETAILS",session, dbSession);
        
        }
         catch(DBValidationException ex){
                    dbg("exception in view operation"+ex,session);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013",l_teacherID );
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
     
         if(detailMap.isEmpty())
         {
            session.getErrorhandler().log_app_error("BS_VAL_013",l_teacherID );
              throw new BSValidationException("BSValidationException");
                         
         }   
       TeacherTimeTable teacherTimeTable=  this.teacherTimeTableBuildBOfromDB(detailMap, l_instituteID, l_teacherID, session, dbSession, inject, appInject);;
        
          dbg("end of  completed teacher timeTable--->view",session);        
          return teacherTimeTable;
        }catch(BSValidationException ex){
            throw ex;
        }
         catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,session);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex,session);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
    
    
    
    private TeacherTimeTable teacherTimeTableBuildBOfromDB(Map<String,DBRecord>detailMap,String instituteID,String l_teacherID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection  appInject)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB",session); 
           BusinessService bs=appInject.getBusinessService(session);
           TeacherTimeTable teacherTimeTable =new TeacherTimeTable();
        
           /* ArrayList<String>l_teacherTimeTableList= p_teacherTimeTableRecord.getRecord();
           
           if(l_teacherTimeTableList!=null&&!l_teacherTimeTableList.isEmpty()){
               
            request.getReqAudit().setMakerID(l_teacherTimeTableList.get(1).trim());
            request.getReqAudit().setCheckerID(l_teacherTimeTableList.get(2).trim());
            request.getReqAudit().setMakerDateStamp(l_teacherTimeTableList.get(3).trim());
            request.getReqAudit().setCheckerDateStamp(l_teacherTimeTableList.get(4).trim());
            request.getReqAudit().setRecordStatus(l_teacherTimeTableList.get(5).trim());
            request.getReqAudit().setAuthStatus(l_teacherTimeTableList.get(6).trim());
            request.getReqAudit().setVersionNumber(l_teacherTimeTableList.get(7).trim());
            request.getReqAudit().setMakerRemarks(l_teacherTimeTableList.get(8).trim());
            request.getReqAudit().setCheckerRemarks(l_teacherTimeTableList.get(9).trim());
            }*/
           Map<String,List<DBRecord>>dayWiseGroup = detailMap.values().stream().filter(rec->rec.getRecord().get(5).trim().equals(l_teacherID)).collect(Collectors.groupingBy(rec->rec.getRecord().get(3).trim()));
           
           // Map<String,DBRecord>  sortedMap = detailMap.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new));
                  
            //Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
            teacherTimeTable.timeTable=new TimeTable[dayWiseGroup.keySet().size()];
            Iterator<String> dayIterator=dayWiseGroup.keySet().iterator();
            int i=0;
            
            while(dayIterator.hasNext()){
                String day=dayIterator.next();
                teacherTimeTable.timeTable[i]=new TimeTable();
                teacherTimeTable.timeTable[i].setDay(day);
                dbg("day"+day,session);
                dbg("dayWiseGroup.get(day).size()"+dayWiseGroup.get(day).size(),session);
                teacherTimeTable.timeTable[i].period=new Period[dayWiseGroup.get(day).size()];
                int j=0;
                for(DBRecord periodRecords: dayWiseGroup.get(day)){
                   teacherTimeTable.timeTable[i].period[j]=new Period();
                   teacherTimeTable.timeTable[i].period[j].setPeriodNumber(periodRecords.getRecord().get(2).trim());
                   teacherTimeTable.timeTable[i].period[j].setSubjectID(periodRecords.getRecord().get(4).trim());
                   String l_subjectName=bs.getSubjectName(teacherTimeTable.timeTable[i].period[j].getSubjectID(), instituteID, session, dbSession, appInject);
                   teacherTimeTable.timeTable[i].period[j].setSubjectName(l_subjectName);
                   teacherTimeTable.timeTable[i].period[j].setStandard(periodRecords.getRecord().get(0).trim());
                   teacherTimeTable.timeTable[i].period[j].setSection(periodRecords.getRecord().get(1).trim());
                  j++;
                  dbg("periodArraySize"+j,session);
                }
                Arrays.sort(teacherTimeTable.timeTable[i].getPeriod());
                i++;
            }
               if( teacherTimeTable.timeTable.length==0)
         {
            session.getErrorhandler().log_app_error("BS_VAL_013",l_teacherID );
              throw new BSValidationException("BSValidationException");
                         
         }  
            
          dbg("end of  buildBOfromDB",session); 
        return teacherTimeTable;
       }
    catch(BSValidationException ex){
        dbg(ex,session);
            throw ex;
    }     
    catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,session);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex,session);
            throw new BSProcessingException("Exception" + ex.toString());
        }
 }
    
    
    
    
    
    public ClassTimeTable classTimeTableDBView(String l_instituteID,String l_standard,String l_section,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection  appInject)throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
                
        try{      
        dbg("inside classTimeTableDBView",session);    
        IBDProperties i_db_properties=session.getCohesiveproperties();
        
        IDBReadBufferService readBuffer= inject.getDBReadBufferService();
        String[] l_pkey={l_standard,l_section};
        DBRecord timeTableRec;
        Map<String,DBRecord>detailMap;
         try{
            
        timeTableRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_MASTER", l_pkey, session, dbSession);
        detailMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"CLASS"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_standard+l_section+i_db_properties.getProperty("FOLDER_DELIMITER")+"Timetable","CLASS","CLASS_TIMETABLE_DETAIL", session, dbSession);
         }
         catch(DBValidationException ex){
                    dbg("exception in view operation"+ex,session);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_013",l_standard+l_section );
                        throw new BSValidationException("BSValidationException");
                        
                    }else{
                        
                        throw ex;
                    }
            }
         
       ClassTimeTable classTimeTable= this.classTimeTableBuildBOfromDB(timeTableRec, detailMap, l_instituteID, l_standard, l_section, session, dbSession, inject, appInject);
        
          dbg("end of  completed Class timeTable--->view",session);            
          
          return classTimeTable;
        }catch(BSValidationException ex){
            dbg(ex,session);
            throw ex;
        }   
        catch(DBValidationException ex){
            dbg(ex,session);
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,session);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex,session);
            throw new BSProcessingException("Exception" + ex.toString());
        }
    }
      private ClassTimeTable classTimeTableBuildBOfromDB(DBRecord p_ClassTimeTableRecord, Map<String,DBRecord>detailMap,String l_instituteID,String l_standard,String l_section,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection  appInject)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
    try{
           dbg("inside buildBOfromDB",session); 
           BusinessService bs=appInject.getBusinessService(session);
           ArrayList<String>l_ClassTimeTableList= p_ClassTimeTableRecord.getRecord();
           ClassTimeTable classTimeTable =new ClassTimeTable();
           
      
           String masterVersion= l_ClassTimeTableList.get(8).trim();
            
           
           int versionIndex=bs.getVersionIndexOfTheTable("CLASS_TIMETABLE_DETAIL", "CLASS", session, dbSession, appInject);
           
            Map<String,List<DBRecord>>dayWiseGroup=detailMap.values().stream().filter(rec->rec.getRecord().get(versionIndex).trim().equals(masterVersion)).collect(Collectors.groupingBy(rec->rec.getRecord().get(2).trim()));
            
            classTimeTable.timeTable=new com.ibd.cohesive.app.business.classentity.classtimetable.TimeTable[dayWiseGroup.keySet().size()];
            Iterator<String> dayIterator=dayWiseGroup.keySet().iterator();
            int i=0;
            
            while(dayIterator.hasNext()){
                String day=dayIterator.next();
                classTimeTable.timeTable[i]=new com.ibd.cohesive.app.business.classentity.classtimetable.TimeTable();
                classTimeTable.timeTable[i].setDay(day);
                dbg("day"+day,session);
                dbg("dayWiseGroup.get(day).size()"+dayWiseGroup.get(day).size(),session);
                classTimeTable.timeTable[i].period=new com.ibd.cohesive.app.business.classentity.classtimetable.Period[dayWiseGroup.get(day).size()];
                int j=0;
                for(DBRecord periodRecords: dayWiseGroup.get(day)){
                   classTimeTable.timeTable[i].period[j]=new com.ibd.cohesive.app.business.classentity.classtimetable.Period();
                   classTimeTable.timeTable[i].period[j].setPeriodNumber(periodRecords.getRecord().get(3).trim());
                   classTimeTable.timeTable[i].period[j].setSubjectID(periodRecords.getRecord().get(4).trim());
                   String l_subjectName=bs.getSubjectName(classTimeTable.timeTable[i].period[j].getSubjectID(), l_instituteID, session, dbSession, appInject);
                   classTimeTable.timeTable[i].period[j].setSubjectName(l_subjectName);
                   classTimeTable.timeTable[i].period[j].setTeacherID(periodRecords.getRecord().get(5).trim());
                   String l_teacherName=bs.getTeacherName(classTimeTable.timeTable[i].period[j].getTeacherID(), l_instituteID, session, dbSession, appInject);
                   classTimeTable.timeTable[i].period[j].setTeacherName(l_teacherName);
                   j++;
                  dbg("periodArraySize"+j,session);
                }
                 Arrays.sort(classTimeTable.timeTable[i].getPeriod());
                i++;
            }
            
            
          dbg("end of  buildBOfromDB",session); 
        return classTimeTable;
        }catch(DBValidationException ex){
            dbg(ex,session);
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,session);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex,session);
            throw new BSProcessingException("Exception" + ex.toString());
        }
 }
    
 private String getTeacherLeaveStatus(String teacherID,String instituteID,String date,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection  appInject)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException {
     
     try{
         dbg("inside getTeacherLeaveStatus",session);
         IDBReadBufferService readBuffer=inject.getDBReadBufferService();
         IBDProperties i_db_properties=session.getCohesiveproperties();
         Map<String,DBRecord>teacherLeaveMap=null;
         String dateformat=i_db_properties.getProperty("DATE_FORMAT");
         SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
         
         try{
            
            
            teacherLeaveMap= readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"TEACHER"+i_db_properties.getProperty("FOLDER_DELIMITER")+teacherID+i_db_properties.getProperty("FOLDER_DELIMITER")+"LEAVE"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Leave","LEAVE", "TVW_TEACHER_LEAVE_MANAGEMENT", session, dbSession);
            
            
            }catch(DBValidationException ex){
                    dbg("exception in view operation"+ex,session);
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
                }
         
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
                    Date dateForCheck=formatter.parse(date);
                   
                    if(dateForCheck.compareTo(leaveFromDate)>=0&&dateForCheck.compareTo(leaveToDate)<=0){
                        
                        
                        if(dateForCheck.equals(leaveFromDate)){
                            
                            
                            if(fromNoon.equals("F")){
                                
                                return "F";
                                
                            }else if(fromNoon.equals("A")){
                                
                                 return "A";
                                
                            }else{
                                
                                 return "L";
                                
                            }
                            
                        }
                        
                        
                        
                        if(dateForCheck.equals(leaveToDate)){
                            
                            
                            if(toNoon.equals("F")){
                                
                                return "F";
                                
                            }else if(toNoon.equals("A")){
                                
                                return "A";
                                
                            }else{
                                
                                 return "L";
                                
                            }
                            
                        }
                        
                        
                    }
                    
         
                }
                
         }
         
         return "W";
     }catch(DBValidationException ex){
         dbg(ex,session);
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex,session);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex,session);
            throw new BSProcessingException("Exception" + ex.toString());
        }
 }
      
      
      
      
      
      
    
//     private Map<String,ArrayList<String>>getAvailableTeachersForThePeriods(String p_standard,String p_section,String p_instituteID ,String p_day,CohesiveSession session,ReportDependencyInjection inject,String periodNo)throws DBProcessingException,DBValidationException{
//        
//        try{
//            Map<String,ArrayList<String>>teacherAndAvailablePeriods=new HashMap();
//            ClassDataSet classDataSet=inject.getClassDataSet(); 
//            ArrayList<CLASS_TIMETABLE_DETAIL>classTimeTableList= classDataSet.getCLASS_TIMETABLE_DETAIL_DataSet(p_standard, p_section, p_instituteID, session);
//            
//            for(int i=0;i<classTimeTableList.size();i++){
//                
//                CLASS_TIMETABLE_DETAIL classTimeTable=classTimeTableList.get(i);
//                String teacherID=classTimeTable.getTEACHER_ID();
//                ArrayList<String>availablePeroids=getAvailablePeroids(teacherID,p_instituteID,p_day,session);
//                teacherAndAvailablePeriods.put(teacherID, availablePeroids);
//                
//            }
//
//            
//            return teacherAndAvailablePeriods;
//            
//      }catch(DBProcessingException ex){
//          dbg(ex,session);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex,session);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        
//    }
//    
//    
//    
//   
//    
//   private ArrayList<String>getAvailablePeroids(String p_teacherID,String p_instituteID,String p_day,CohesiveSession session,ReportDependencyInjection inject)throws DBProcessingException{
//       
//       try{
//           ArrayList<String>availablePeriodsList=new ArrayList();
//           TeacherDataSetBusiness teacherDataSet=inject.getTeacherDatasetBusiness(); 
//            ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>teacherTimeTableList= teacherDataSet.getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSetList(p_teacherID, p_instituteID);
//            Map<String,List<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>>dayGroup= teacherTimeTableList.stream().collect(Collectors.groupingBy(rec->rec.getDAY()));
//            
//            List<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS> dayList=dayGroup.get(p_day);
//            
//            for(int i=0;i<dayList.size();i++){
//                TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS teacherTimeTable=teacherTimeTableList.get(i);
//                
//                String l_periodNumber=teacherTimeTable.getPERIOD_NO();
//                
//                if(teacherTimeTable.getSTANDARD()==null&&teacherTimeTable.getSECTION()==null&&teacherTimeTable.getSUBJECT_ID()==null){
//                    
//                    availablePeriodsList.add(l_periodNumber);
//                    
//                }
//                
//                
//            }
//            
//
//           return availablePeriodsList;
//       }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//       
//       
//       
//   }
   
   
   
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
