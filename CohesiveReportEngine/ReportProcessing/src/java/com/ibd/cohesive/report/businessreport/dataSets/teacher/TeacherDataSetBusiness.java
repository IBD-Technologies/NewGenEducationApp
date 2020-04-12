/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.teacher;

import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteAvailabilityInOtherClass;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteAvailabilityInSameClass;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteReportParam;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TVW_CONTACT_PERSON_DETAILS_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TVW_TEACHER_PROFILE_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherAttendanceSummary;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherMarksDetail;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherMarksSummary;
import com.ibd.cohesive.report.businessreport.dataModels.teacher.TeacherTimeTableReport;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Remote(ITeacherDataSetBusiness.class)
@Stateless
public class TeacherDataSetBusiness implements ITeacherDataSetBusiness{
   ReportDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    AppDependencyInjection appInject;
    
    public TeacherDataSetBusiness(){
        try {
            inject=new ReportDependencyInjection("TEACHER_BU");
            session = new CohesiveSession();
            dbSession = new DBSession(session);
            appInject=new AppDependencyInjection();
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }  
    
     public String getTeacherMarksDetail_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
         
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTeacherMarksDetail_DataSet");
          
          TeacherMarksDetail_DataSet teacherMarks=inject.getTeacherMarksDetail();
          
         ArrayList<TeacherMarksDetail>teacherMarksDetail=  teacherMarks.getTeacherMarksDetailDataSet(p_teacherID, p_instanceID, session, dbSession, inject,appInject);
          
         String result=this.convertTeacherMarksDetailListToString(teacherMarksDetail, session);
         
         dbg("end of getTeacherMarksDetail_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
             
          if(l_session_created_now){   
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }        
        }
        
    }
     
     
      public String convertTeacherMarksDetailListToString(ArrayList<TeacherMarksDetail>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TeacherMarksDetail appEod=appEodList.get(i);
                
                String record=appEod.getEXAM()+"~"+
                              appEod.getGRADE()+"~"+
                              appEod.getMARK()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getTEACHERID()+"~"+
                              appEod.getSubjectName()+"~"+
                              appEod.getStudentName();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
     
     
    
    public String getTeacherMarksDetail_DataSet(String p_teacherID,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getTeacherMarksDetail_DataSet(p_teacherID,p_instanceID);
            
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
       }catch(Exception ex){
           dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        finally{
           this.session=tempSession;
        }
        
    }
    
    public String getTeacherAttendanceSummary_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
       boolean l_session_created_now=false;
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTeacherAttendanceSummary_DataSet");
          
          TeacherAttendanceSummary_DataSet teacherAttendanceSummary=inject.getTeacherAttendanceDataset();
          
          
          ArrayList<TeacherAttendanceSummary>attendanceSummary=  teacherAttendanceSummary.getTeacherAttendanceSummary_DataSetObject(p_teacherID, p_instanceID, session, dbSession, inject,appInject);
          
         
          String result=this.convertTeacherAttendanceSummaryListToString(attendanceSummary, session);
          dbg("end of getTeacherAttendanceSummary_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){   
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }
        }
        
    }
    
    public String convertTeacherAttendanceSummaryListToString(ArrayList<TeacherAttendanceSummary>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TeacherAttendanceSummary appEod=appEodList.get(i);
                
                String record=appEod.getMonth()+"~"+
                              appEod.getNo_of_Days_Leave()+"~"+
                              appEod.getNo_of_Days_Present()+"~"+
                              appEod.getTeacherID()+"~"+
                              appEod.getYear()+"~"+
                              appEod.getNo_of_WorkingDays()+"~"+
                              appEod.getPercentage();
                            
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
    
    
    
    
 
    
    
    
    
    
    
    public String getTeacherMarksSummary_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTeacherMarksSummary_DataSet");
          
          TeacherMarksSummary_DataSet teacherMarksSummary=inject.getTeacherMarksSummary();
          
         ArrayList<TeacherMarksSummary>markSummary=  teacherMarksSummary.getTeacherMarksSummaryObject(p_teacherID, p_instanceID, session, dbSession, inject,appInject);
          
         
         String result=this.convertTeacherMarksSummaryListToString(markSummary, session);
         dbg("end of getTeacherMarksSummary_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){   
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }
        }
        
    }
    
    
    public String convertTeacherMarksSummaryListToString(ArrayList<TeacherMarksSummary>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TeacherMarksSummary appEod=appEodList.get(i);
                
                String record=appEod.getAverageMark()+"~"+
                              appEod.getExam()+"~"+
                              appEod.getLowMark()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSubjectID()+"~"+
                              appEod.getTeacherID()+"~"+
                              appEod.getTopMark()+"~"+
                              appEod.getGrade()+"~"+
                              appEod.getNo_of_Students()+"~"+
                              appEod.getOrderNumber()+"~"+
                              appEod.getGradePercentage()+"~"+
                              appEod.getSubjectName();
                        
                              
                            
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
    
    
    
    public String getSubstituteAvailabilityInOtherClass_DataSet(String p_teacherID,String p_instanceID,String p_date,String userID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSubstituteAvailabilityInOtherClass_DataSet");
          
          SubstituteAvailabilityInOtherClass_DataSet substituteAvailability=inject.getSubstituteAvailabilityInOtherClass();
          
         ArrayList<SubstituteAvailabilityInOtherClass>otherClass=  substituteAvailability.getSubstituteAvailabilityInOtherClass_DataSet(p_teacherID, p_instanceID,p_date, session, dbSession, inject,appInject,userID);
          
         
         String result=this.convertSubstituteAvailabilityInOtherClassListToString(otherClass, session);
         dbg("end of getSubstituteAvailabilityInOtherClass_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){   
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }
        }
        
    }
    
    
     public String convertSubstituteAvailabilityInOtherClassListToString(ArrayList<SubstituteAvailabilityInOtherClass>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SubstituteAvailabilityInOtherClass appEod=appEodList.get(i);
                
                String record=appEod.getClasss()+"~"+
                              appEod.getFreeEndTime()+"~"+
                              appEod.getFreeStartTime()+"~"+
                              appEod.getStartTimeNo()+"~"+
                              appEod.getTeacherID()+"~"+
                              appEod.getTeacherName();
                              
                        
                              
                            
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
    
    
    
     public String getSubstituteAvailabilityInSameClass_DataSet(String p_teacherID,String p_instanceID,String p_date,String userID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSubstituteAvailabilityInSameClass_DataSet");
          
          SubstituteAvailabilityInSameClass_DataSet substituteAvailability=inject.getSubstituteAvailabilityInSameClass();
          
         ArrayList<SubstituteAvailabilityInSameClass>sameClass=  substituteAvailability.getSubstituteAvailabilityInSameClass_DataSet(p_teacherID, p_instanceID,p_date, session, dbSession, inject,appInject,userID);
          
         
         String result=this.convertSubstituteAvailabilityInSameClassListToString(sameClass, session);
         dbg("end of getSubstituteAvailabilityInSameClass_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){   
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }
        }
        
    }
     
     
     
      public String convertSubstituteAvailabilityInSameClassListToString(ArrayList<SubstituteAvailabilityInSameClass>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SubstituteAvailabilityInSameClass appEod=appEodList.get(i);
                
                 String record=appEod.getClasss()+"~"+
                              appEod.getFreeEndTime()+"~"+
                              appEod.getFreeStartTime()+"~"+
                              appEod.getStartTimeNo()+"~"+
                              appEod.getTeacherID()+"~"+
                              appEod.getTeacherName();
                              
                        
                              
                            
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
     
     
     
     
     
     public String getTVW_TEACHER_PROFILE_BUSINESS_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTVW_TEACHER_PROFILE_BUSINESS_DataSet");
          
          TVW_TEACHER_PROFILE_BUSINESS_DATASET substituteAvailability=inject.getTeacherProfileBusiness();
          
         ArrayList<TVW_TEACHER_PROFILE_BUSINESS>teacherProfile=  substituteAvailability.getTableObject(p_teacherID, p_instanceID, session, dbSession, inject);
          
         
         String result=this.convertTVW_TEACHER_PROFILE_BUSINESSListToString(teacherProfile, session);
         dbg("end of getTVW_TEACHER_PROFILE_BUSINESS_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
           }
        }
        
    }
    
     
     public String convertTVW_TEACHER_PROFILE_BUSINESSListToString(ArrayList<TVW_TEACHER_PROFILE_BUSINESS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TVW_TEACHER_PROFILE_BUSINESS appEod=appEodList.get(i);
                
                String record=appEod.getADDRESSLINE1()+"~"+
                              appEod.getADDRESSLINE2()+"~"+
                              appEod.getADDRESSLINE3()+"~"+
                              appEod.getADDRESSLINE4()+"~"+
                              appEod.getADDRESSLINE5()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getBLOOD_GROUP()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getCONTACT_NO()+"~"+
                              appEod.getDOB()+"~"+
                              appEod.getEMAIL_ID()+"~"+
                              appEod.getIMAGE_PATH()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getQUALIFICATION()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSHORT_NAME()+"~"+
                              appEod.getTEACHER_ID()+"~"+
                              appEod.getTEACHER_NAME()+"~"+
                              appEod.getVERSION_NUMBER()+"~"+
                              appEod.getEXISTING_MEDICAL_DETAIL()+"~"+
                              appEod.getCLASS();
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
     
     
     
     
     
     
    public String getTVW_CONTACT_PERSON_DETAILS_BUSINESS_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTVW_CONTACT_PERSON_DETAILS_BUSINESS_DataSet");
          
          TVW_CONTACT_PERSON_DETAILS_BUSINESS_DATASET substituteAvailability=inject.getTeacherContactPersoonBusiness();
          
         ArrayList<TVW_CONTACT_PERSON_DETAILS_BUSINESS>teacherContact=  substituteAvailability.getTableObject(p_teacherID, p_instanceID, session, dbSession, inject);
          
         
         String result=this.convertTVW_CONTACT_PERSON_DETAILS_BUSINESSListToString(teacherContact, session);
         dbg("end of getTVW_CONTACT_PERSON_DETAILS_BUSINESS_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
           }
        }
        
    } 
    
    
    
     public String convertTVW_CONTACT_PERSON_DETAILS_BUSINESSListToString(ArrayList<TVW_CONTACT_PERSON_DETAILS_BUSINESS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TVW_CONTACT_PERSON_DETAILS_BUSINESS appEod=appEodList.get(i);
                
                String record=appEod.getCP_CONTACTNO()+"~"+
                              appEod.getCP_ID()+"~"+
                              appEod.getCP_MAILID()+"~"+
                              appEod.getCP_NAME()+"~"+
                              appEod.getCP_OCCUPATION()+"~"+
                              appEod.getCP_RELATIONSHIP()+"~"+
                              appEod.getIMAGE_PATH()+"~"+
                              appEod.getTEACHER_ID()+"~"+
                              appEod.getVERSION_NUMBER();
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
    
    
    
    
    
    
    public String getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet");
          
          TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET substituteAvailability=inject.getTeacherTimeTableDetail();
          
         ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>teacherTimeTable=  substituteAvailability.getTableObject(p_teacherID, p_instanceID, session, dbSession, inject,appInject);
          
         
         
         String result=this.convertTVW_TEACHER_TIMETABLE_DETAIL_BUSINESSListToString(teacherTimeTable, session);
         dbg("end of getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
           }
        }
        
    } 
     public ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS> getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSetList(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet");
          
          TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DATASET substituteAvailability=inject.getTeacherTimeTableDetail();
          
         return substituteAvailability.getTableObject(p_teacherID, p_instanceID, session, dbSession, inject,appInject);
          
         
         
//         String result=this.convertTVW_TEACHER_TIMETABLE_DETAIL_BUSINESSListToString(teacherTimeTable, session);
//         dbg("end of getTVW_TEACHER_TIMETABLE_DETAIL_BUSINESS_DataSet-->result-->"+result);
//         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
           }
        }
        
    } 
    
    public String convertTVW_TEACHER_TIMETABLE_DETAIL_BUSINESSListToString(ArrayList<TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TVW_TEACHER_TIMETABLE_DETAIL_BUSINESS appEod=appEodList.get(i);
                
                String record=appEod.getDAY()+"~"+
                              appEod.getDAY_NUMBER()+"~"+
                              appEod.getEndTime()+"~"+
                              appEod.getPERIOD_NO()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getStartTime()+"~"+
                              appEod.getTEACHER_ID()+"~"+
                              appEod.getVERSION_NUMBER();
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
    
    
    
      public ArrayList<TeacherMarksDetail> getTeacherMarksDetailList_DataSet(String p_teacherID,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
         
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTeacherMarksDetail_DataSet");
          
          TeacherMarksDetail_DataSet teacherMarks=inject.getTeacherMarksDetail();
          
        return  teacherMarks.getTeacherMarksDetailDataSet(p_teacherID, p_instanceID, session, dbSession, inject,appInject);
          
         
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
             
          if(l_session_created_now){   
            session.clearSessionObject();
            dbSession.clearSessionObject();
          }        
        }
        
    }
     
     
     
     
     
    
    public ArrayList<TeacherMarksDetail> getTeacherMarksDetailList_DataSet(String p_teacherID,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getTeacherMarksDetailList_DataSet(p_teacherID,p_instanceID);
            
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
       }catch(Exception ex){
           dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        finally{
           this.session=tempSession;
        }
        
    }
    
    
    
    public String getTeacherTimeTableReport_DataSet(String p_teacherID,String p_instanceID,String p_date,String userID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getTeacherTimeTableReport_DataSet");
          
          TeacherTimeTableReport_DataSet substituteAvailability=inject.getTeacherTimeTableReport();
          
         ArrayList<TeacherTimeTableReport>teacherTimeTable=  substituteAvailability.getTeacherTimeTableReport_DataSet(p_teacherID, p_instanceID, p_date, session, dbSession, inject, appInject, userID);
          
         
         
         String result=this.convertTeacherTimeTableReportListToString(teacherTimeTable, session);
         dbg("end of getTeacherTimeTableReport_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
           }
        }
        
    } 
	
	
	
	
	
	public String convertTeacherTimeTableReportListToString(ArrayList<TeacherTimeTableReport>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TeacherTimeTableReport appEod=appEodList.get(i);
                
                String record=appEod.getDAY()+"~"+
                              appEod.getEndTime()+"~"+
                              appEod.getPERIOD_NO()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSUBJECT_NAME()+"~"+
                              appEod.getStartTime();
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
    
    
     public String getSubstituteReportParam_DataSet(String p_teacherID,String p_instanceID,String p_date)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSubstituteReportParam_DataSet");
          
          SubstituteReportParam_DataSet substituteAvailability=inject.getSubstituteReportparam();
          
         ArrayList<SubstituteReportParam>teacherTimeTable=  substituteAvailability.getSubstituteReportParam_DataSet(p_teacherID, p_date, p_instanceID, session, dbSession, inject, appInject);
          
         
         
         String result=this.convertSubstituteReportParamListToString(teacherTimeTable, session);
         dbg("end of getSubstituteReportParam_DataSet-->result-->"+result);
         return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            if(l_session_created_now){  
            session.clearSessionObject();
            dbSession.clearSessionObject();
            
           }
        }
        
    } 
	
	
	
	
	
	public String convertSubstituteReportParamListToString(ArrayList<SubstituteReportParam>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SubstituteReportParam appEod=appEodList.get(i);
                
                String record=appEod.getDate()+"~"+
                              appEod.getTeacherID()+"~"+
                              appEod.getTeacherName();
                              
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(appEodList.size()==1)
                result=result+"#";
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         dbg(ex);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
     
        
    }
    
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
    
    
}
