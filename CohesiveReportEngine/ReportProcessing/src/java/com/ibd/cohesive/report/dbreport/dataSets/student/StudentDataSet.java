/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.student;

import com.ibd.businessViews.IStudentDataSet;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_PERIOD_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.STUDENT_OTP_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.student.STUDENT_NOTIFICATION_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_CONTACT_PERSON_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_FAMILY_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_ASSIGNMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_ATTENDANCE;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_CALENDER;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_EXAM_SCHEDULE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_EXAM_SCHEDULE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_E_CIRCULAR;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_FEE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_LEAVE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_MARKS;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_NOTIFICATION;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PAYMENT;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PROFILE;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_PRORESS_CARD;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_TIMETABLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_TIMETABLE_MASTER;
import com.ibd.cohesive.report.dbreport.dataSets.institute.STUDENT_OTP_STATUS_DATASET;
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
 * @author IBD TEchnologies
 */
@Remote(IStudentDataSet.class)
@Stateless
public class StudentDataSet implements IStudentDataSet {
    ReportDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
    public StudentDataSet(){
        try {
            inject=new ReportDependencyInjection("STUDENT");
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    
    public String getSVW_CONTACT_PERSON_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_CONTACT_PERSON_DETAILS_DataSet");
          
          
          
          SVW_CONTACT_PERSON_DETAILS_DATASET studentContactPerson=inject.getStudentContactPersonDataSet();
          
          
          dbg("end of getSVW_CONTACT_PERSON_DETAILS_DataSet");
         ArrayList<SVW_CONTACT_PERSON_DETAILS>svwContPersonDetail=  studentContactPerson.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_CONTACT_PERSON_DETAILSListToString(svwContPersonDetail, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
    public String convertSVW_CONTACT_PERSON_DETAILSListToString(ArrayList<SVW_CONTACT_PERSON_DETAILS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_CONTACT_PERSON_DETAILS appEod=appEodList.get(i);
                
                String record=appEod.getCP_CONTACTNO()+"~"+
                              appEod.getCP_ID()+"~"+
                              appEod.getCP_MAILID()+"~"+
                              appEod.getCP_NAME()+"~"+
                              appEod.getCP_OCCUPATION()+"~"+
                              appEod.getCP_RELATIONSHIP()+"~"+
                              appEod.getIMAGE_PATH()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
     
    
    public String getSTUDENT_NOTIFICATION_STATUS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_CONTACT_PERSON_DETAILS_DataSet");
          
          
          
          STUDENT_NOTIFICATION_STATUS_DATASET studentContactPerson=inject.getStudentNotificationDataset();
          
          
          dbg("end of getSTUDENT_NOTIFICATION_STATUS_DataSet");
         ArrayList<STUDENT_NOTIFICATION_STATUS>svwContPersonDetail=  studentContactPerson.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSTUDENT_NOTIFICATION_STATUSListToString(svwContPersonDetail, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
    public String convertSTUDENT_NOTIFICATION_STATUSListToString(ArrayList<STUDENT_NOTIFICATION_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_NOTIFICATION_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_POINT()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getNOTIFICATION_ID()+"~"+
                              appEod.getSTATUS()+"~"+
                              appEod.getSTUDENT_ID();
                
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
     
    
    public String getSVW_STUDENT_E_CIRCULAR_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_E_CIRCULAR_DataSet");
          
          
          
          SVW_STUDENT_E_CIRCULAR_DATASET studentContactPerson=inject.getStudentEcircularDataset();
          
          
          dbg("end of getSVW_STUDENT_E_CIRCULAR_DataSet");
         ArrayList<SVW_STUDENT_E_CIRCULAR>svwContPersonDetail=  studentContactPerson.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_E_CIRCULARListToString(svwContPersonDetail, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
    public String convertSVW_STUDENT_E_CIRCULARListToString(ArrayList<SVW_STUDENT_E_CIRCULAR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_E_CIRCULAR appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getCONTENT_PATH()+"~"+
                              appEod.getDESCRIPTION()+"~"+
                              appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getURL()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
      public String getSVW_STUDENT_NOTIFICATION_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_NOTIFICATION_DataSet");
          
          
          
         SVW_STUDENT_NOTIFICATION_DATASET studentContactPerson=inject.getSvwStudentNotificationDataset();
          
          
          dbg("end of getSVW_STUDENT_NOTIFICATION_DataSet");
         ArrayList<SVW_STUDENT_NOTIFICATION>svwContPersonDetail=  studentContactPerson.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_NOTIFICATIONListToString(svwContPersonDetail, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
    public String convertSVW_STUDENT_NOTIFICATIONListToString(ArrayList<SVW_STUDENT_NOTIFICATION>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_NOTIFICATION appEod=appEodList.get(i);
                
                String record=appEod.getDATE()+"~"+
                              appEod.getNOTIFICATION_ID()+"~"+
                              appEod.getSTUDENT_ID();
                              
                
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
     public String getSVW_STUDENT_OTHER_ACTIVITY_REPORT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_OTHER_ACTIVITY_REPORT_DataSet");
          
          
          
         SVW_STUDENT_OTHER_ACTIVITY_REPORT_DATASET studentContactPerson=inject.getStudentOtherActivityReport();
          
          
          dbg("end of getSVW_STUDENT_OTHER_ACTIVITY_REPORT_DataSet");
         ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT>svwContPersonDetail=  studentContactPerson.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_OTHER_ACTIVITY_REPORTListToString(svwContPersonDetail, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
    public String convertSVW_STUDENT_OTHER_ACTIVITY_REPORTListToString(ArrayList<SVW_STUDENT_OTHER_ACTIVITY_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_OTHER_ACTIVITY_REPORT appEod=appEodList.get(i);
                
                String record=appEod.getCOUNT()+"~"+
                              appEod.getLEVEL()+"~"+
                              appEod.getRESULT_TYPE()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getYEAR();
                              
                
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
  
    
    public String getSVW_FAMILY_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_FAMILY_DETAILS_DataSet");
          
          
          
          SVW_FAMILY_DETAILS_DATASET studentFamilyDetail=inject.getStudentFamilyDetailsDataSet();
          
          
          dbg("end of getSVW_FAMILY_DETAILS_DataSet");
         ArrayList<SVW_FAMILY_DETAILS>svwFamilyDetails=  studentFamilyDetail.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_FAMILY_DETAILSListToString(svwFamilyDetails, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    public String convertSVW_FAMILY_DETAILSListToString(ArrayList<SVW_FAMILY_DETAILS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_FAMILY_DETAILS appEod=appEodList.get(i);
                
                String record=appEod.getIMAGE_PATH()+"~"+
                              appEod.getMEMBER_CONTACTNO()+"~"+
                              appEod.getMEMBER_EMAILID()+"~"+
                              appEod.getMEMBER_ID()+"~"+
                              appEod.getMEMBER_NAME()+"~"+
                              appEod.getMEMBER_OCCUPATION()+"~"+
                              appEod.getMEMBER_RELATIONSHIP()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
     
    
    
    
    
    public String getSVW_STUDENT_ASSIGNMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_ASSIGNMENT_DataSet");
          
          
          
          SVW_STUDENT_ASSIGNMENT_DATASET studentAssignment=inject.getStudentAssignmentDataSet();
          
          
          dbg("end of getSVW_STUDENT_ASSIGNMENT_DataSet");
         ArrayList<SVW_STUDENT_ASSIGNMENT>svwStuAssignment=  studentAssignment.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_ASSIGNMENTListToString(svwStuAssignment, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
       public String convertSVW_STUDENT_ASSIGNMENTListToString(ArrayList<SVW_STUDENT_ASSIGNMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_ASSIGNMENT appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_DESCRIPTION()+"~"+
                              appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getCOMPLETED_DATE()+"~"+
                              appEod.getDUE_DATE()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getPARENT_COMMENTS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSTATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getTEACHER_COMMENTS()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
    public String getSVW_STUDENT_ATTENDANCE_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_ATTENDANCE_DataSet");
          
          
          
          SVW_STUDENT_ATTENDANCE_DATASET studentAttendance=inject.getStudentAttendanceDataSet();
          
          
          dbg("end of getSVW_STUDENT_ATTENDANCE_DataSet");
         ArrayList<SVW_STUDENT_ATTENDANCE>svwStuAttendance=  studentAttendance.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_ATTENDANCEListToString(svwStuAttendance, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
         public String convertSVW_STUDENT_ATTENDANCEListToString(ArrayList<SVW_STUDENT_ATTENDANCE>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_ATTENDANCE appEod=appEodList.get(i);
                
                String record=appEod.getATTENDANCE()+"~"+
                              appEod.getMONTH()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getYEAR();
                              
                
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
    
    
    
    public String getSVW_STUDENT_ATTENDANCE_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getSVW_STUDENT_ATTENDANCE_DataSet(p_fileName,p_instanceID);
            
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
       }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        finally{
           this.session=tempSession;
        }
        
    }  
    
    public String getSVW_STUDENT_CALENDER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_CALENDERE_DataSet");
          
          
          
          SVW_STUDENT_CALENDER_DATASET studentCalender=inject.getStudentCalenderDataSet();
          
          
          dbg("end of getSVW_STUDENT_CALENDER_DataSet");
          ArrayList<SVW_STUDENT_CALENDER>svwStuCalender=  studentCalender.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_CALENDERListToString(svwStuCalender, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
      public String convertSVW_STUDENT_CALENDERListToString(ArrayList<SVW_STUDENT_CALENDER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_CALENDER appEod=appEodList.get(i);
                
                String record=appEod.getEVENTS()+"~"+
                              appEod.getMONTH()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getYEAR();
                              
                
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
    
    public String getSVW_STUDENT_EXAM_SCHEDULE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_EXAM_SCHEDULE_DETAIL_DataSet");
          
          
          
          SVW_STUDENT_EXAM_SCHEDULE_DETAIL_DATASET studentExamSchedule=inject.getStudentExamScheduleDetailDataSet();
          
          
          dbg("end of getSVW_STUDENT_EXAM_SCHEDULE_DETAIL_DataSet");
         ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL>stuExamSchedDetail=  studentExamSchedule.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_EXAM_SCHEDULE_DETAILListToString(stuExamSchedDetail, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    public String convertSVW_STUDENT_EXAM_SCHEDULE_DETAILListToString(ArrayList<SVW_STUDENT_EXAM_SCHEDULE_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_EXAM_SCHEDULE_DETAIL appEod=appEodList.get(i);
                
                String record=appEod.getDATE()+"~"+
                              appEod.getEND_TIME_HOUR()+"~"+
                              appEod.getEND_TIME_MIN()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getHALL()+"~"+
                              appEod.getSTART_TIME_HOUR()+"~"+
                              appEod.getSTART_TIME_MIN()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
    
    
    
    public String getSVW_STUDENT_EXAM_SCHEDULE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_EXAM_SCHEDULE_MASTER_DataSet");
          
          
          
          SVW_STUDENT_EXAM_SCHEDULE_MASTER_DATASET studentExamSchedule=inject.getStudentExamScheduleMasterDataSet();
          
          
          dbg("end of getSVW_STUDENT_EXAM_SCHEDULE_MASTER_DataSet");
         ArrayList<SVW_STUDENT_EXAM_SCHEDULE_MASTER>stuExamSchedMaster=  studentExamSchedule.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_EXAM_SCHEDULE_MASTER_DETAILListToString(stuExamSchedMaster, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
  
    
    public String convertSVW_STUDENT_EXAM_SCHEDULE_MASTER_DETAILListToString(ArrayList<SVW_STUDENT_EXAM_SCHEDULE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_EXAM_SCHEDULE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
    
    public String getSVW_STUDENT_FEE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_FEE_MANAGEMENT_DATASET_DataSet");
          
          
          
          SVW_STUDENT_FEE_MANAGEMENT_DATASET studentFeeManagement=inject.getStudentFeeManagementDataSet();
          
          
          dbg("end of getSVW_STUDENT_FEE_MANAGEMENT_DataSet");
         ArrayList<SVW_STUDENT_FEE_MANAGEMENT>stuFeeManagement=  studentFeeManagement.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_FEE_MANAGEMENT_DETAILListToString(stuFeeManagement, session);
          return result;
          
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
      public String convertSVW_STUDENT_FEE_MANAGEMENT_DETAILListToString(ArrayList<SVW_STUDENT_FEE_MANAGEMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_FEE_MANAGEMENT appEod=appEodList.get(i);
                
                String record=appEod.getAMOUNT()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getDUE_DATE()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getFEE_PAID()+"~"+
                              appEod.getFEE_TYPE()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getOUTSTANDING()+"~"+
                              appEod.getPAID_DATE()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSTATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
    
    
    public String getSVW_STUDENT_FEE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getSVW_STUDENT_FEE_MANAGEMENT_DataSet(p_fileName,p_instanceID);
            
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
       }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        finally{
           this.session=tempSession;
        }
        
    }
      
    public String getSVW_STUDENT_LEAVE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSSVW_STUDENT_LEAVE_MANAGEMENT_DATASET_DataSet");
          
          
          
          SVW_STUDENT_LEAVE_MANAGEMENT_DATASET studentLeaveManagement=inject.getStudentLeaveManagementDataSet();
          
          
          dbg("end of getSVW_STUDENT_LEAVE_MANAGEMENT_DataSet");
         ArrayList<SVW_STUDENT_LEAVE_MANAGEMENT>stuLeaveManagement=  studentLeaveManagement.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_LEAVE_MANAGEMENTListToString(stuLeaveManagement, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
    
    
       public String convertSVW_STUDENT_LEAVE_MANAGEMENTListToString(ArrayList<SVW_STUDENT_LEAVE_MANAGEMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_LEAVE_MANAGEMENT appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getFROM()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getREASON()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getREMARKS()+"~"+
                              appEod.getSTATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getTO()+"~"+
                              appEod.getTYPE()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
   
    
    
    
    
     public String getSVW_STUDENT_MARKS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_MARKS_DATASET_DataSet");
          
          
          
          SVW_STUDENT_MARKS_DATASET studentMarks=inject.getStudentMarksDataSet();
          
          
          dbg("end of getSVW_STUDENT_MARKS_DataSet");
         ArrayList<SVW_STUDENT_MARKS>stuMarks=  studentMarks.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_MARKSListToString(stuMarks, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
     
     
      public String convertSVW_STUDENT_MARKSListToString(ArrayList<SVW_STUDENT_MARKS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_MARKS appEod=appEodList.get(i);
                
                String record=appEod.getEXAM()+"~"+
                              appEod.getGRADE()+"~"+
                              appEod.getMARK()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getTEACHER_FEEDBACK()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
   
     
     
      public String getSVW_STUDENT_OTHER_ACTIVITY_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_OTHER_ACTIVITY_DATASET_DataSet");
          
          
          
          SVW_STUDENT_OTHER_ACTIVITY_DATASET studentOtherActivity=inject.getStudentOtherActivityDataSet();
          
          
          dbg("end of getSVW_STUDENT_OTHER_ACTIVITY_DataSet");
         ArrayList<SVW_STUDENT_OTHER_ACTIVITY>stuOtherActivity=  studentOtherActivity.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_OTHER_ACTIVITYListToString(stuOtherActivity, session);
          return result;
          
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
      
      
       public String convertSVW_STUDENT_OTHER_ACTIVITYListToString(ArrayList<SVW_STUDENT_OTHER_ACTIVITY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_OTHER_ACTIVITY appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_ID()+"~"+
                              appEod.getACTIVITY_NAME()+"~"+
                              appEod.getACTIVITY_TYPE()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getDATE()+"~"+
                              appEod.getINTREST()+"~"+
                              appEod.getLEVEL()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getRESULT()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getVENUE()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
   
      
      
      public String getSVW_STUDENT_OTHER_ACTIVITY_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getSVW_STUDENT_OTHER_ACTIVITY_DataSet(p_fileName,p_instanceID);
            
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
       }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        finally{
           this.session=tempSession;
        }
        
    }  
      
      
      
      public String getSVW_STUDENT_PAYMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_PAYMENT_DATASET_DataSet");
          
          
          
          SVW_STUDENT_PAYMENT_DATASET studentPayment=inject.getStudentPaymentDataSet();
          
          
          dbg("end of getSVW_STUDENT_PAYMENT_DataSet");
         ArrayList<SVW_STUDENT_PAYMENT>stuPayment=  studentPayment.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_PAYMENTListToString(stuPayment, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
      
      
      
      public String convertSVW_STUDENT_PAYMENTListToString(ArrayList<SVW_STUDENT_PAYMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_PAYMENT appEod=appEodList.get(i);
                
                String record=appEod.getAMOUNT()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getFEE_TYPE()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getOUT_STANDING()+"~"+
                              appEod.getPAYMENT_DATE()+"~"+
                              appEod.getPAYMENT_ID()+"~"+
                              appEod.getPAYMENT_MODE()+"~"+
                              appEod.getPAYMENT_PAID()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getREMARKS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
      
      
      
       public String getSVW_STUDENT_PAYMENT_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getSVW_STUDENT_PAYMENT_DataSet(p_fileName,p_instanceID);
            
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
       }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        finally{
           this.session=tempSession;
        }
        
    }  
      
        public String getSVW_STUDENT_PROFILE_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_PROFILE_DATASET_DataSet");
          
          
          
          SVW_STUDENT_PROFILE_DATASET studentProfile=inject.getStudentProfileDataSet();
          
          
          dbg("end of getSVW_STUDENT_PROFILE_DataSet");
         ArrayList<SVW_STUDENT_PROFILE>stuProfile=  studentProfile.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_PROFILEListToString(stuProfile, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
        
        
         public String convertSVW_STUDENT_PROFILEListToString(ArrayList<SVW_STUDENT_PROFILE>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_PROFILE appEod=appEodList.get(i);
                
                String record=appEod.getADDRESSLINE1()+"~"+
                              appEod.getADDRESSLINE2()+"~"+
                              appEod.getADDRESSLINE3()+"~"+
                              appEod.getADDRESSLINE4()+"~"+
                              appEod.getADDRESSLINE5()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getBLOODGROUP()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getDOB()+"~"+
                              appEod.getEXISTING_MEDICAL_DETAIL()+"~"+
                              appEod.getIMAGE_PATH()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getNOTES()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSTUDENT_NAME()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
        
        
     
        
    public String getSVW_STUDENT_PRORESS_CARD_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_PRORESS_CARD_DATASET_DataSet");
          
          
          
          SVW_STUDENT_PRORESS_CARD_DATASET studentProgress=inject.getStudentProgressCardDataSet();
          
          
          dbg("end of getSVW_STUDENT_PRORESS_CARD_DataSet");
         ArrayList<SVW_STUDENT_PRORESS_CARD>stuProgressCard=  studentProgress.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_PRORESS_CARDListToString(stuProgressCard, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    } 
        
       public String convertSVW_STUDENT_PRORESS_CARDListToString(ArrayList<SVW_STUDENT_PRORESS_CARD>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_PRORESS_CARD appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRANK()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getTOTAL()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
 
    
    
    
        public String getSVW_STUDENT_TIMETABLE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_TIMETABLE_DETAIL_DATASET_DataSet");
          
          
          
          SVW_STUDENT_TIMETABLE_DETAIL_DATASET studentTimeTableDetail=inject.getStudentTimeTableDetailDataSet();
          
          
          dbg("end of getSVW_STUDENT_TIMETABLE_DETAIL_DataSet");
         ArrayList<SVW_STUDENT_TIMETABLE_DETAIL>stuTimeTableDetail=  studentTimeTableDetail.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_TIMETABLE_DETAILListToString(stuTimeTableDetail, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    } 
        
        
         public String convertSVW_STUDENT_TIMETABLE_DETAILListToString(ArrayList<SVW_STUDENT_TIMETABLE_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_TIMETABLE_DETAIL appEod=appEodList.get(i);
                
                String record=appEod.getDAY()+"~"+
                              appEod.getPERIOD_NO()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
        
        
         
         
         
        
     public String getSVW_STUDENT_TIMETABLE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSVW_STUDENT_TIMETABLE_MASTER_DATASET_DataSet");
          
          
          
         SVW_STUDENT_TIMETABLE_MASTER_DATASET studentTimeTableMaster=inject.getStudentTimeTableMasterDataSet();
          
          
          dbg("end of getSVW_STUDENT_TIMETABLE_MASTER_DataSet");
         ArrayList<SVW_STUDENT_TIMETABLE_MASTER>stuTimeTableMaster=  studentTimeTableMaster.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertSVW_STUDENT_TIMETABLE_MASTERListToString(stuTimeTableMaster, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    }
     
     
         public String convertSVW_STUDENT_TIMETABLE_MASTERListToString(ArrayList<SVW_STUDENT_TIMETABLE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                SVW_STUDENT_TIMETABLE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
        
     public String getSTUDENT_OTP_STATUS_DataSet(String p_instanceID,String p_studentID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
         STUDENT_OTP_STATUS_DATASET teacher=inject.getStudentOtpStatus();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
         ArrayList<STUDENT_OTP_STATUS>studentOtpStatus= teacher.getTableObject(p_instanceID,p_studentID, session, dbSession, inject);
          String result=this.convertSTUDENT_OTP_STATUSListToString(studentOtpStatus, session);
          return result;
       }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex);
          throw ex;
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
    } 
     




public String convertSTUDENT_OTP_STATUSListToString(ArrayList<STUDENT_OTP_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_OTP_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getEND_POINT()+"~"+
                              appEod.getOTP()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getVERIFICATION_STATUS();
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
