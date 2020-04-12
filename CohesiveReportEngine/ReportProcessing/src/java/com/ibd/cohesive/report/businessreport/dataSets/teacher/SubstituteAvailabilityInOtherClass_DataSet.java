/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.cohesive.app.business.teacher.teachertimetable.TeacherTimeTable;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteAvailabilityInOtherClass;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteAvailabilityInSameClass;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.util.ReportUtil;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class SubstituteAvailabilityInOtherClass_DataSet {
    
    public ArrayList<SubstituteAvailabilityInOtherClass> getSubstituteAvailabilityInOtherClass_DataSet(String p_leaveTeacher,String p_instituteID,String p_date,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String userID)throws DBProcessingException,DBValidationException{
        ArrayList<SubstituteAvailabilityInOtherClass>dataset=new ArrayList();
        
        try{

        ReportUtil reportUtil=inject.getReportUtil(session);
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        String day=reportUtil.getDay(p_date);
        BusinessService bs=appInject.getBusinessService(session);
        SubstituteAvailabilityInSameClass_DataSet sameClassObj=inject.getSubstituteAvailabilityInSameClass();
        ArrayList<String>classesForTheDay=this.getOtherClasses(p_leaveTeacher, p_instituteID, day, session, dbSession, inject, appInject);
        
        ArrayList<String> teachersOfTheClass=new ArrayList();
        for(int i=0;i<classesForTheDay.size();i++){
            
            String classs=classesForTheDay.get(i);
            sameClassObj.getTeachersOfTheClass(classs, p_instituteID, session, dbSession, inject, appInject,teachersOfTheClass);
            
        }
        
        Map<String,DBRecord>sameClassMap= readBuffer.readTable("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+userID+i_db_properties.getProperty("FOLDER_DELIMITER")+"REPORT"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport"+i_db_properties.getProperty("FOLDER_DELIMITER")+"MarkReport","REPORT", "SUBSTITUTE_AVAIL_SAME_CLASS", session, dbSession);
        
        Map<String,List<DBRecord>>teacherGroup=sameClassMap.values().stream().collect(Collectors.groupingBy(rec->rec.getRecord().get(0).trim()));
        
        ArrayList<String>filteredTeachersList=new ArrayList();
        for(int i=0;i<teachersOfTheClass.size();i++){
            
            String teacherID=teachersOfTheClass.get(i);
            if(!teacherGroup.containsKey(teacherID)&&!p_leaveTeacher.equals(teacherID)){
                
                filteredTeachersList.add(teacherID);
                
            }
            
        }
        
        for(int j=0;j<filteredTeachersList.size();j++){
            
            String teacherID=filteredTeachersList.get(j);
            
            
            ArrayList<FreeTime>freeTimesOfTeacher=sameClassObj.getFreeTimingsOfTheTeacher(teacherID, p_instituteID, day, session, dbSession, inject, appInject, p_date);
      
            if(!freeTimesOfTeacher.isEmpty()){
            
                String teacherName=bs.getTeacherName(teacherID, p_instituteID, session, dbSession, appInject);
                String classs=bs.getClassOfTheTeacher(p_instituteID, teacherID, session, dbSession, appInject);
                
                for(int i=0;i<freeTimesOfTeacher.size();i++){

                    String freeStartTime=freeTimesOfTeacher.get(i).getStartTime();
                    String freeEndTime=freeTimesOfTeacher.get(i).getEndTime();
                    int startTimeNo=freeTimesOfTeacher.get(i).getStartTime_no();
                    SubstituteAvailabilityInOtherClass sameClass=new SubstituteAvailabilityInOtherClass();
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
            
            
        
        if(dataset.isEmpty()){
            
            SubstituteAvailabilityInOtherClass sameClass=new SubstituteAvailabilityInOtherClass();
            sameClass.setTeacherID(" ");
            sameClass.setTeacherName(" ");
            sameClass.setFreeStartTime(" ");
            sameClass.setFreeEndTime(" ");
            sameClass.setStartTimeNo(0);
            sameClass.setClasss(" ");
            dataset.add(sameClass);
            
            
        }
        
        
            

     }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
       return dataset;
    }
    
    
    
    
    public ArrayList<String>getOtherClasses(String p_teacherID,String p_instituteID,String p_day,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        try{
            ArrayList<String>classList=new ArrayList();
            BusinessService bs=appInject.getBusinessService(session);
            SubstituteAvailabilityInSameClass_DataSet sameClassObj=inject.getSubstituteAvailabilityInSameClass();
            TeacherTimeTable teacherTT=sameClassObj.teacherTimeTableDBView(p_instituteID, p_teacherID, session, dbSession, inject, appInject);
            
            
            for(int i=0;i<teacherTT.timeTable.length;i++){
                
                String l_day=teacherTT.timeTable[i].getDay();
                
                if(l_day.equals(p_day)){
                    
                    for(int j=0;j<teacherTT.timeTable[i].period.length;j++){
                        
                     
                        String standard=teacherTT.timeTable[i].period[j].getStandard();
                        String sameSection=teacherTT.timeTable[i].period[j].getSection();
                        
                        ArrayList<String>sections=bs.getSectionsOfTheStandard(p_instituteID, standard, session, dbSession, appInject);
                        
                        
                        for(int k=0;k<sections.size();k++){
                        
                            String section=sections.get(k);
                            
                            if(!sameSection.equals(section)){
                            
                                if(!classList.contains(standard+"/"+section)){

                                   classList.add(standard+"/"+section);

                                }
                                
                            } 
                                
                        
                        }
                        
                        
                        
                    }
                    
                    
                    
                    
                }
                
                
            }
            
            
            
            
            
            return classList;
            
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
    
    
    
    
    
    
    
    
    
//     private ArrayList<String>getClassesForTheDay(String p_teacherID,String p_instituteID,String p_day,CohesiveSession session)throws DBProcessingException,DBValidationException{
//        
//        try{
//            ArrayList<String>classAndSubjectList=new ArrayList();
//            ITeacherDataSet teacherDataSet=ReportDependencyInjection.getTeacherDataset(); 
//            ArrayList<TVW_TEACHER_TIMETABLE_DETAIL>teacherTimeTableList= teacherDataSet.getTVW_TEACHER_TIMETABLE_DETAIL_DataSet(p_teacherID, p_instituteID,session);
//            
//            Map<String,List<TVW_TEACHER_TIMETABLE_DETAIL>>dayGroup= teacherTimeTableList.stream().collect(Collectors.groupingBy(rec->rec.getDAY()));
//            
//            List<TVW_TEACHER_TIMETABLE_DETAIL> dayList=dayGroup.get(p_day);
//            
//            for(int i=0;i<dayList.size();i++){
//                TVW_TEACHER_TIMETABLE_DETAIL teacherTimeTable=teacherTimeTableList.get(i);
//                String standard=teacherTimeTable.getSTANDARD();
//                String sameSection= teacherTimeTable.getSECTION();
//                
//                sameClassTeachers= getTeachersOfTheClass(standard,sameSection,p_instituteID,session);
//                
//                IInstituteDataSet instituteDataSet=ReportDependencyInjection.getInstituteDataset();
//                ArrayList<IVW_STANDARD_MASTER>standardMasterFromTable= instituteDataSet.getIVW_STANDARD_MASTER_DataSet(p_instituteID, session);
//                Map<String,List<IVW_STANDARD_MASTER>>standardWiseGroup=standardMasterFromTable.stream().collect(Collectors.groupingBy(rec->rec.getSTANDARD()));
//                List<IVW_STANDARD_MASTER>listForTheStandard=standardWiseGroup.get(standard);
//                
//                for(int j=0;j<listForTheStandard.size();j++){
//                    IVW_STANDARD_MASTER standardMaster=listForTheStandard.get(j);
//                    String section=standardMaster.getSECTION();
//                    
//                    if(!section.equals(sameSection)){
//                    
//                    String classs=standard.concat("^").concat(section);
//                
//                    if(!classAndSubjectList.contains(classs)){
//                        classAndSubjectList.add(classs);
//                    }
//                    
//                    }
//                    
//                }
//                
//                
//            }
//            
//            
//            
//            return classAndSubjectList;
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
//     private Map<String,ArrayList<String>>getTeachersAndAvailablePeriods(String p_standard,String p_section,String p_instituteID ,String p_day,CohesiveSession session)throws DBProcessingException,DBValidationException{
//        
//        try{
//            Map<String,ArrayList<String>>teacherAndAvailablePeriods=new HashMap();
//            IClassDataSet classDataSet=ReportDependencyInjection.getClassDataset(); 
//            ArrayList<CLASS_TIMETABLE_DETAIL>classTimeTableList= classDataSet.getCLASS_TIMETABLE_DETAIL_DataSet(p_standard, p_section, p_instituteID, session);
//            
//            for(int i=0;i<classTimeTableList.size();i++){
//                
//                CLASS_TIMETABLE_DETAIL classTimeTable=classTimeTableList.get(i);
//                String teacherID=classTimeTable.getTEACHER_ID();
//                
//                if(!sameClassTeachers.contains(teacherID)){
//                    ArrayList<String>availablePeroids=getAvailablePeroids(teacherID,p_instituteID,p_day,session);
//                    teacherAndAvailablePeriods.put(teacherID, availablePeroids);
//                }
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
//      private ArrayList<String>getAvailablePeroids(String p_teacherID,String p_instituteID,String p_day,CohesiveSession session)throws DBProcessingException{
//       
//       try{
//           ArrayList<String>availablePeriodsList=new ArrayList();
//           ITeacherDataSet teacherDataSet=ReportDependencyInjection.getTeacherDataset(); 
//            ArrayList<TVW_TEACHER_TIMETABLE_DETAIL>teacherTimeTableList= teacherDataSet.getTVW_TEACHER_TIMETABLE_DETAIL_DataSet(p_teacherID, p_instituteID,session);
//            
//            Map<String,List<TVW_TEACHER_TIMETABLE_DETAIL>>dayGroup= teacherTimeTableList.stream().collect(Collectors.groupingBy(rec->rec.getDAY()));
//            
//            List<TVW_TEACHER_TIMETABLE_DETAIL> dayList=dayGroup.get(p_day);
//            
//            for(int i=0;i<dayList.size();i++){
//                TVW_TEACHER_TIMETABLE_DETAIL teacherTimeTable=teacherTimeTableList.get(i);
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
//     
//     
//    
//     private ArrayList<String>getTeachersOfTheClass(String p_standard,String p_section,String p_instituteID ,CohesiveSession session)throws DBProcessingException,DBValidationException{
//        
//        try{
//            ArrayList<String>classTeachersList=new ArrayList();
//            IClassDataSet classDataSet=ReportDependencyInjection.getClassDataset(); 
//            ArrayList<CLASS_TIMETABLE_DETAIL>classTimeTableList= classDataSet.getCLASS_TIMETABLE_DETAIL_DataSet(p_standard, p_section, p_instituteID, session);
//            
//            for(int i=0;i<classTimeTableList.size();i++){
//                
//                CLASS_TIMETABLE_DETAIL classTimeTable=classTimeTableList.get(i);
//                String teacherID=classTimeTable.getTEACHER_ID();
//                
//                    
//                    classTeachersList.add(teacherID);
//                    
//                
//            }
//            
//            
//            
//            return classTeachersList;
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

    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
