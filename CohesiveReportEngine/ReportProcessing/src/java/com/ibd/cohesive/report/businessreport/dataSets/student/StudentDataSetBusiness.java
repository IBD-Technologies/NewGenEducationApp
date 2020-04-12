/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.businessViews.IStudentDataSetBusiness;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_FAMILY_DETAILS_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_MARKS_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_PROFILE_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_SOFT_SKILLS_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.student.SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentAttendanceSummary;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentFeeDetails;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentOtherActivityDetail;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentRank;
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
 * @author IBD Technologies
 */

@Remote(IStudentDataSetBusiness.class)
@Stateless
public class StudentDataSetBusiness implements IStudentDataSetBusiness{
    
    ReportDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    AppDependencyInjection appInject;
    
    public StudentDataSetBusiness(){
        try {
            inject=new ReportDependencyInjection("STUDENT_BU");
            session = new CohesiveSession();
            dbSession = new DBSession(session);
            appInject=new AppDependencyInjection();
            
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    
     public String getStudentAttendanceSummary_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          
          dbg("inside getStudentAttendanceSummary_DataSet");
          
          StudentAttendanceSummary_DataSet studentContactPerson=inject.getStudentAttendanceSummary();
          
          
         ArrayList<StudentAttendanceSummary>studentAttenance=  studentContactPerson.getStudentAttendanceSummaryObject(p_fileName, p_instanceID, session, dbSession, inject,appInject);
          
         String result=this.convertStudentAttendanceSummaryListToString(studentAttenance, session);
         
         dbg("end of getStudentAttendanceSummary_DataSet-->result-->"+result);
         
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
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
     
     
     public String convertStudentAttendanceSummaryListToString(ArrayList<StudentAttendanceSummary>StudentAttendance,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<StudentAttendance.size();i++){
                StudentAttendanceSummary StudentFeeDetail=StudentAttendance.get(i);
                
                String record=StudentFeeDetail.getMonth()+"~"+
                              StudentFeeDetail.getNo_of_Days_Absent()+"~"+
                              StudentFeeDetail.getNo_of_Days_Leave()+"~"+
                              StudentFeeDetail.getNo_of_Days_Present()+"~"+
                              StudentFeeDetail.getNo_of_working_Days()+"~"+
                              StudentFeeDetail.getPercentage()+"~"+
                              StudentFeeDetail.getStudentID()+"~"+
                              StudentFeeDetail.getYear()+"~"+
                              StudentFeeDetail.getMonthNumber();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(StudentAttendance.size()==1)
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
    
     public String getStudentFeeDetails_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getStudentFeeDetails_DataSet");
          
          
          StudentFeeDetails_DataSet studentFeeDetail=inject.getStudentFeeDetails();
          
          
          ArrayList<StudentFeeDetails>studentFeeDetailList=  studentFeeDetail.getStudentFeeDetailsObject(p_fileName, p_instanceID, session, dbSession, inject);
          
         
          String result=this.convertStudentFeeDetailsListToString(studentFeeDetailList, session);
         
          
          dbg("end of getStudentFeeDetails_DataSet-->result-->"+result);
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
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
     
     
     public String convertStudentFeeDetailsListToString(ArrayList<StudentFeeDetails>StudentFee,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<StudentFee.size();i++){
                StudentFeeDetails StudentFeeDetail=StudentFee.get(i);
                
                String record=StudentFeeDetail.getBalanceAmount()+"~"+
                              StudentFeeDetail.getDueDate()+"~"+
                              StudentFeeDetail.getFeeAmount()+"~"+
                              StudentFeeDetail.getFeeID()+"~"+
                              StudentFeeDetail.getFeeType()+"~"+
                              StudentFeeDetail.getPaidAmount()+"~"+
                              StudentFeeDetail.getStudentID();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(StudentFee.size()==1)
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
     
    
     public String getStudentFeeDetails_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getStudentFeeDetails_DataSet(p_fileName,p_instanceID);
            
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
     
     
      public String getSVW_FAMILY_DETAILS_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSVW_FAMILY_DETAILS_BUSINESS_DataSet");
          
          SVW_FAMILY_DETAILS_BUSINESS_DATASET studentProfileBusiness=inject.getStudentFamilyDetailsBusiness();
          
          
          ArrayList<SVW_FAMILY_DETAILS_BUSINESS>studentFamilyDetails=  studentProfileBusiness.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          
         
          String result=this.convertSVW_FAMILY_DETAILS_BUSINESSListToString(studentFamilyDetails, session);
         
          
          dbg("end of getSVW_FAMILY_DETAILS_BUSINESS_DataSet-->result-->"+result);
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
      public String convertSVW_FAMILY_DETAILS_BUSINESSListToString(ArrayList<SVW_FAMILY_DETAILS_BUSINESS>studentFamily,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<studentFamily.size();i++){
                SVW_FAMILY_DETAILS_BUSINESS studentFamilyDetail=studentFamily.get(i);
                
                String record=studentFamilyDetail.getIMAGE_PATH()+"~"+
                              studentFamilyDetail.getMEMBER_CONTACTNO()+"~"+
                              studentFamilyDetail.getMEMBER_EMAILID()+"~"+
                              studentFamilyDetail.getMEMBER_ID()+"~"+
                              studentFamilyDetail.getMEMBER_NAME()+"~"+
                              studentFamilyDetail.getMEMBER_OCCUPATION()+"~"+
                              studentFamilyDetail.getMEMBER_RELATIONSHIP()+"~"+
                              studentFamilyDetail.getSTUDENT_ID()+"~"+
                              studentFamilyDetail.getVERSION_NUMBER();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(studentFamily.size()==1)
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
      
     public String getSVW_STUDENT_MARKS_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSVW_STUDENT_MARKS_BUSINESS_DataSet");
          
          SVW_STUDENT_MARKS_BUSINESS_DATASET studentProfileBusiness=inject.getStudentMarksBusiness();
          
           
          ArrayList<SVW_STUDENT_MARKS_BUSINESS>studentMarkBusiness=  studentProfileBusiness.getTableObject(p_fileName, p_instanceID, session, dbSession, inject, appInject);
          
          String result=this.convertSVW_STUDENT_MARKS_BUSINESSListToString(studentMarkBusiness, session);
          
          dbg("end of getSVW_STUDENT_MARKS_BUSINESS_DataSet--->result--->"+result);
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
     
     public String convertSVW_STUDENT_MARKS_BUSINESSListToString(ArrayList<SVW_STUDENT_MARKS_BUSINESS>studentFamily,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<studentFamily.size();i++){
                SVW_STUDENT_MARKS_BUSINESS studentFamilyDetail=studentFamily.get(i);
                
                String record=studentFamilyDetail.getEXAM()+"~"+
                              studentFamilyDetail.getGRADE()+"~"+
                              studentFamilyDetail.getMARK()+"~"+
                              studentFamilyDetail.getSTUDENT_ID()+"~"+
                              studentFamilyDetail.getSUBJECT_ID()+"~"+
                              studentFamilyDetail.getTEACHER_FEEDBACK()+"~"+
                              studentFamilyDetail.getVERSION_NUMBER();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(studentFamily.size()==1)
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
     
     public String getSVW_STUDENT_SOFT_SKILLS_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSVW_STUDENT_SOFT_SKILLS_BUSINESS_DataSet");
          
          SVW_STUDENT_SOFT_SKILLS_BUSINESS_DATASET studentProfileBusiness=inject.getStudentSoftSkillsBusiness();
          
           
          ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS>studentMarkBusiness=  studentProfileBusiness.getTableObject(p_fileName, p_instanceID, session, dbSession, inject, appInject);
          
          String result=this.convertSVW_STUDENT_SOFT_SKILLS_BUSINESSListToString(studentMarkBusiness, session);
          
          dbg("end of getSVW_STUDENT_SOFT_SKILLS_BUSINESS_DataSet--->result--->"+result);
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
     
     public String convertSVW_STUDENT_SOFT_SKILLS_BUSINESSListToString(ArrayList<SVW_STUDENT_SOFT_SKILLS_BUSINESS>studentFamily,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<studentFamily.size();i++){
                SVW_STUDENT_SOFT_SKILLS_BUSINESS studentFamilyDetail=studentFamily.get(i);
                
                String record=studentFamilyDetail.getEXAM()+"~"+
                              studentFamilyDetail.getCATEGORY()+"~"+
                              studentFamilyDetail.getSTUDENT_ID()+"~"+
                              studentFamilyDetail.getSKILL_ID()+"~"+
                              studentFamilyDetail.getTEACHER_FEEDBACK()+"~"+
                              studentFamilyDetail.getVERSION_NUMBER()+"~"+
                              studentFamilyDetail.getCATEGORY_VALUE();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(studentFamily.size()==1)
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
      
     public String getSVW_STUDENT_PROFILE_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSVW_STUDENT_PROFILE_BUSINESS_DataSet");
          
          
          SVW_STUDENT_PROFILE_BUSINESS_DATASET studentProfileBusiness=inject.getStudentProfileBusiness();
          dbg("inside after getting studentProfileBusiness");
          
          ArrayList<SVW_STUDENT_PROFILE_BUSINESS>profileDataSet=  studentProfileBusiness.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          dbg(" after getting profileDataSet");
         
          String result=this.convertSVW_STUDENT_PROFILE_BUSINESSListToString(profileDataSet, session);
         
          dbg("end getSVW_STUDENT_PROFILE_BUSINESS_DataSet-->result-->"+result);
          
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
     
     public String convertSVW_STUDENT_PROFILE_BUSINESSListToString(ArrayList<SVW_STUDENT_PROFILE_BUSINESS>studentFamily,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<studentFamily.size();i++){
                SVW_STUDENT_PROFILE_BUSINESS studentFamilyDetail=studentFamily.get(i);
                
                String record=studentFamilyDetail.getADDRESSLINE1()+"~"+
                              studentFamilyDetail.getADDRESSLINE2()+"~"+
                              studentFamilyDetail.getADDRESSLINE3()+"~"+
                              studentFamilyDetail.getADDRESSLINE4()+"~"+
                              studentFamilyDetail.getADDRESSLINE5()+"~"+
                              studentFamilyDetail.getAUTH_STATUS()+"~"+
                              studentFamilyDetail.getBLOODGROUP()+"~"+
                              studentFamilyDetail.getCHECKER_DATE_STAMP()+"~"+
                              studentFamilyDetail.getCHECKER_ID()+"~"+
                              studentFamilyDetail.getCHECKER_REMARKS()+"~"+
                              studentFamilyDetail.getDOB()+"~"+
                              studentFamilyDetail.getEXISTING_MEDICAL_DETAIL()+"~"+
                              studentFamilyDetail.getIMAGE_PATH()+"~"+
                              studentFamilyDetail.getMAKER_DATE_STAMP()+"~"+
                              studentFamilyDetail.getMAKER_ID()+"~"+
                              studentFamilyDetail.getMAKER_REMARKS()+"~"+
                              studentFamilyDetail.getNOTES()+"~"+
                              studentFamilyDetail.getRECORD_STATUS()+"~"+
                              studentFamilyDetail.getSECTION()+"~"+
                              studentFamilyDetail.getSTANDARD()+"~"+
                              studentFamilyDetail.getSTUDENT_ID()+"~"+
                              studentFamilyDetail.getSTUDENT_NAME()+"~"+
                              studentFamilyDetail.getVERSION_NUMBER();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(studentFamily.size()==1)
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
     
     
    public String getSVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DataSet");
          
          SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DATASET studentProfileBusiness=inject.getStudentTimeTableBusiness();
          
    
          ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>timeTable=  studentProfileBusiness.getTableObject(p_fileName, p_instanceID, session, dbSession, inject, appInject);
          
          String result=this.convertSVW_STUDENT_TIMETABLE_DETAIL_BUSINESSListToString(timeTable, session);
         
          dbg("end of getSVW_STUDENT_TIMETABLE_DETAIL_BUSINESS_DataSet-->result--->"+result);
          
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
    
    
     public String convertSVW_STUDENT_TIMETABLE_DETAIL_BUSINESSListToString(ArrayList<SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS>studentFamily,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<studentFamily.size();i++){
                SVW_STUDENT_TIMETABLE_DETAIL_BUSINESS studentFamilyDetail=studentFamily.get(i);
                
                String record=studentFamilyDetail.getDAY()+"~"+
                              studentFamilyDetail.getEndTime()+"~"+
                              studentFamilyDetail.getPERIOD_NO()+"~"+
                              studentFamilyDetail.getSTUDENT_ID()+"~"+
                              studentFamilyDetail.getSUBJECT_ID()+"~"+
                              studentFamilyDetail.getStartTime()+"~"+
                              studentFamilyDetail.getTEACHER_ID()+"~"+
                              studentFamilyDetail.getVERSION_NUMBER();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(studentFamily.size()==1)
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
    
    
     public String getSVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getSVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DataSet");
          
          SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DATASET studentProfileBusiness=inject.getStudentOtherActivityBusiness();
          
          ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS>otherActivity=  studentProfileBusiness.getTableObject(p_fileName, p_instanceID, session, dbSession, inject, appInject);
         
          String result=this.convertSVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESSListToString(otherActivity, session);
         
          dbg("end of getSVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS_DataSet-->result-->"+result);
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
     
     
      public String convertSVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESSListToString(ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS>studentFamily,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<studentFamily.size();i++){
                SVW_STUDENT_OTHER_ACTIVITY_REPORT_BUSINESS studentFamilyDetail=studentFamily.get(i);
                
                String record=studentFamilyDetail.getCOUNT()+"~"+
                              studentFamilyDetail.getLEVEL()+"~"+
                              studentFamilyDetail.getRESULT_TYPE()+"~"+
                              studentFamilyDetail.getSTUDENT_ID()+"~"+
                              studentFamilyDetail.getYEAR();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(studentFamily.size()==1)
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
     
     public String getStudentRank_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getStudentRank_DATASET_DataSet");
          
         StudentRankDataSet studentRank=inject.getStudentRank();
          
         ArrayList<StudentRank>studentRankList=  studentRank.getStudentRankDetailsObject(p_fileName, p_instanceID, session, dbSession, inject,appInject);
          
         String result=this.convertStudentRankListToString(studentRankList, session);
         
          dbg("end of getStudentRank_DataSet-->result-->"+result);
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
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
     
     public String convertStudentRankListToString(ArrayList<StudentRank>studentFamily,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<studentFamily.size();i++){
                StudentRank studentFamilyDetail=studentFamily.get(i);
                
                String record=studentFamilyDetail.getExam()+"~"+
                              studentFamilyDetail.getRank()+"~"+
                              studentFamilyDetail.getStudentID()+"~"+
                              studentFamilyDetail.getTotal();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(studentFamily.size()==1)
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
     
     
     
   public String getStudentOtherActivityDetail_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
       boolean l_session_created_now=false;
       try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getStudentOtherActivityDetail_DataSet");
          
          
          StudentOtherActivityDetail_DataSet studentFeeDetail=inject.getOtherActivityDetail();
          
          
          ArrayList<StudentOtherActivityDetail>studentFeeDetailList=  studentFeeDetail.getStudentOtherActivityDetailObject(p_fileName, p_instanceID, session, dbSession, inject);
          
         
          String result=this.convertStudentOtherActivityDetailListToString(studentFeeDetailList, session);
         
          
          dbg("end of getStudentOtherActivityDetail_DataSet-->result-->"+result);
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
     
    public String getStudentOtherActivityDetail_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getStudentOtherActivityDetail_DataSet(p_fileName,p_instanceID);
            
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
     
     public String convertStudentOtherActivityDetailListToString(ArrayList<StudentOtherActivityDetail>StudentFee,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<StudentFee.size();i++){
                StudentOtherActivityDetail StudentFeeDetail=StudentFee.get(i);
                
                String record=StudentFeeDetail.getActivityID()+"~"+
                              StudentFeeDetail.getActivityName()+"~"+
                              StudentFeeDetail.getActivityType()+"~"+
                              StudentFeeDetail.getDate()+"~"+
                              StudentFeeDetail.getLevel()+"~"+
                              StudentFeeDetail.getResult()+"~"+
                              StudentFeeDetail.getVenue();
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            if(StudentFee.size()==1)
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
     
     
     public ArrayList<StudentOtherActivityDetail> getStudentOtherActivityDetail_DataSetList(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
       boolean l_session_created_now=false;
       try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getStudentOtherActivityDetail_DataSet");
          
          
          StudentOtherActivityDetail_DataSet studentFeeDetail=inject.getOtherActivityDetail();
          
          
          return  studentFeeDetail.getStudentOtherActivityDetailObject(p_fileName, p_instanceID, session, dbSession, inject);
          
         
//          String result=this.convertStudentOtherActivityDetailListToString(studentFeeDetailList, session);
         
          
//          dbg("end of getStudentOtherActivityDetail_DataSet-->result-->"+result);
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
      public ArrayList<StudentOtherActivityDetail> getStudentOtherActivityDetail_DataSetList(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getStudentOtherActivityDetail_DataSetList(p_fileName,p_instanceID);
            
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
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
    
}
