/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.teacher;

import com.ibd.businessViews.ITeacherDataSet;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivityDetail;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_CONTACT_PERSON_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_EXISTING_MEDICAL_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_PAYROLL;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_ATTENDANCE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_ATTENDANCE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_CALENDER;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_LEAVE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_PROFILE;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_TIMETABLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_TEACHER_TIMETABLE_MASTER;
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
@Remote(ITeacherDataSet.class)
@Stateless
public class TeacherDataSet implements ITeacherDataSet{
    ReportDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
    public TeacherDataSet(){
        try {
            inject=new ReportDependencyInjection("TEACHER");
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    public String getTVW_CONTACT_PERSON_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTVW_CONTACT_PERSON_DETAILS_DataSet");
          
          
          
          TVW_CONTACT_PERSON_DETAILS_DATASET teacherContactPerson=inject.getTeacherContactPersonDataSet();
          
          
          dbg("end of getTVW_CONTACT_PERSON_DETAILS_DataSet");
         ArrayList<TVW_CONTACT_PERSON_DETAILS>contPersondetails=  teacherContactPerson.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertTVW_CONTACT_PERSON_DETAILSListToString(contPersondetails, session);
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
    
    
    public String convertTVW_CONTACT_PERSON_DETAILSListToString(ArrayList<TVW_CONTACT_PERSON_DETAILS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TVW_CONTACT_PERSON_DETAILS appEod=appEodList.get(i);
                
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    
    
    
    
     
//    public ArrayList<TVW_EXISTING_MEDICAL_DETAILS>getTVW_EXISTING_MEDICAL_DETAILS_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getTVW_EXISTING_MEDICAL_DETAILS_DataSet");
//          
//          
//          
//          TVW_EXISTING_MEDICAL_DETAILS_DATASET teacherExistingMedicalDetail=inject.getTeacherExistingMedicalDetailsDataSet();
//          
//          dbg("end of getTVW_EXISTING_MEDICAL_DETAILS_DataSet");
//         return  teacherExistingMedicalDetail.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
//          
//       }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        finally{
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//        }
//        
//    }
//    
//    
//      public String convertTVW_EXISTING_MEDICAL_DETAILSListToString(ArrayList<TVW_EXISTING_MEDICAL_DETAILS>appEodList,CohesiveSession p_session) throws DBProcessingException{
//       String result=new String();
//        
//        
//        try{
//            
//            for(int i=0;i<appEodList.size();i++){
//                TVW_EXISTING_MEDICAL_DETAILS appEod=appEodList.get(i);
//                
//                String record=appEod.getMEDICAL_DETAILS()+"~"+
//                              appEod.getMEDICAL_DETAIL_ID()+"~"+
//                              appEod.getTEACHER_ID()+"~"+
//                              appEod.getVERSION_NUMBER();
//                              
//                              
//                              
//                
//                if(i==0){
//                
//                   result=record;
//                }else{
//                    
//                    result=result+"#"+record;
//                    
//                }
//                
//            }
//            
//            if(appEodList.size()==1)
//                result=result+"#";
//            
//            return result;
////     }catch(DBProcessingException ex){
////          dbg(ex);
////          throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        
//    }
//    
//  
    
      
//    public ArrayList<TVW_PAYROLL>getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getTVW_PAYROLL_DataSet");
//          
//          
//          
//          TVW_PAYROLL_DATASET teacherPayroll=inject.getTeacherPayRollDataSet();
//          
//          dbg("end of getTVW_PAYROLL_DataSet");
//         return  teacherPayroll.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
//          
//       }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        finally{
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//        }
//        
//    } 
//    
//    
//     public String convertTVW_PAYROLLListToString(ArrayList<TVW_PAYROLL>appEodList,CohesiveSession p_session) throws DBProcessingException{
//       String result=new String();
//        
//        
//        try{
//            
//            for(int i=0;i<appEodList.size();i++){
//                TVW_PAYROLL appEod=appEodList.get(i);
//                
//                String record=appEod.getAUTH_STATUS()+"~"+
//                              appEod.getCHECKER_DATE_STAMP()+"~"+
//                              appEod.getCHECKER_ID()+"~"+
//                              appEod.getCHECKER_REMARKS()+"~"+
//                              appEod.getMAKER_DATE_STAMP()+"~"+
//                              appEod.g()+"~"+
//                              appEod.getAUTH_STATUS()+"~"+
//                              appEod.getCHECKER_DATE_STAMP()+"~"+
//                              appEod.getCHECKER_ID()+"~"+
//                              appEod.getAUTH_STATUS()+"~"+
//                              appEod.getCHECKER_DATE_STAMP()+"~"+
//                              appEod.getCHECKER_ID()+"~"+
//                              appEod.getVERSION_NUMBER();
//                              
//                              
//                              
//                
//                if(i==0){
//                
//                   result=record;
//                }else{
//                    
//                    result=result+"#"+record;
//                    
//                }
//                
//            }
//            
//            if(appEodList.size()==1)
//                result=result+"#";
//            
//            return result;
////     }catch(DBProcessingException ex){
////          dbg(ex);
////          throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        
//    }
    
  
    
    
    
//    public ArrayList<TVW_TEACHER_ATTENDANCE_DETAIL>getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet");
//          
//          
//          
//          TVW_TEACHER_ATTENDANCE_DETAIL_DATASET teacherAttendanceDetail=inject.getTeacherAttendanceDetailDataSet();
//          
//          dbg("end of getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet");
//         return  teacherAttendanceDetail.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
//          
//       }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        finally{
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//        }
//        
//    } 
//    
//     public ArrayList<TVW_TEACHER_ATTENDANCE_DETAIL>getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
//        
//        CohesiveSession tempSession = this.session;
//        try{
//            
//            this.session=session;
//            return getTVW_TEACHER_ATTENDANCE_DETAIL_DataSet(p_fileName,p_instanceID);
//            
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//       }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }
//        finally{
//           this.session=tempSession;
//        }
//        
//    } 
//    
//    
//     
//    public ArrayList<TVW_TEACHER_ATTENDANCE_MASTER>getTVW_TEACHER_ATTENDANCE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getTVW_TEACHER_ATTENDANCE_MASTER_DataSet");
//          
//          
//          
//          TVW_TEACHER_ATTENDANCE_MASTER_DATASET teacherAttendanceMaster=inject.getTeacherAttendanceMasterDataSet();
//          
//          dbg("end of getTVW_TEACHER_ATTENDANCE_MASTER_DataSet");
//         return  teacherAttendanceMaster.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
//          
//       }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        finally{
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//        }
//        
//    }
//    
//    public ArrayList<TVW_TEACHER_CALENDER>getTVW_TEACHER_CALENDER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getTVW_TEACHER_CALENDER_DataSet");
//          
//          
//          
//          TVW_TEACHER_CALENDER_DATASET teacherCalender=inject.getTeacherCalenderDataSet();
//          
//          dbg("end of getTVW_TEACHER_CALENDER_DataSet");
//         return  teacherCalender.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
//          
//       }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        finally{
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//        }
//        
//    }
//    
    public String getTVW_TEACHER_LEAVE_MANAGEMENT_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTVW_TEACHER_LEAVE_MANAGEMENT_DataSet");
          
          
          
          TVW_TEACHER_LEAVE_MANAGEMENT_DATASET teacherLeaveManagement=inject.getTeacherLeaveManagementDataSet();
          
          dbg("end of geTVW_TEACHER_LEAVE_MANAGEMENT_DataSet");
         ArrayList<TVW_TEACHER_LEAVE_MANAGEMENT>teacherLeaveManagementList=  teacherLeaveManagement.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertTVW_TEACHER_LEAVE_MANAGEMENTListToString(teacherLeaveManagementList, session);
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
    
     public String convertTVW_TEACHER_LEAVE_MANAGEMENTListToString(ArrayList<TVW_TEACHER_LEAVE_MANAGEMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TVW_TEACHER_LEAVE_MANAGEMENT appEod=appEodList.get(i);
                
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
                              appEod.getTEACHER_ID()+"~"+
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
   
    
    
    
    
    public String getTVW_TEACHER_PROFILE_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTVW_TEACHER_PROFILE_DataSet");
          
          
          
          TVW_TEACHER_PROFILE_DATASET teacherProfile=inject.getTeacherProfileDataSet();
          
          dbg("end of geTVW_TEACHER_PROFILE_DataSet");
         ArrayList<TVW_TEACHER_PROFILE>teacherProfileList=  teacherProfile.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
          String result=this.convertTVW_TEACHER_PROFILEListToString(teacherProfileList, session);
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
    
    
        
    
    
    public String convertTVW_TEACHER_PROFILEListToString(ArrayList<TVW_TEACHER_PROFILE>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TVW_TEACHER_PROFILE appEod=appEodList.get(i);
                
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
                              appEod.getSECTION()+"~"+
                              appEod.getSHORT_NAME()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getTEACHER_ID()+"~"+
                              appEod.getTEACHER_NAME()+"~"+
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
    
    
    
    
    
    
    
    
    
    
    
     
//    public ArrayList<TVW_TEACHER_TIMETABLE_DETAIL>getTVW_TEACHER_TIMETABLE_DETAIL_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getTVW_TEACHER_TIMETABLE_DETAIL_DataSet");
//          
//          
//          
//          TVW_TEACHER_TIMETABLE_DETAIL_DATASET teacherTimeTable=inject.getTeacherTimeTableDetailDataSet();
//          
//          dbg("end of geTVW_TEACHER_TIMETABLE_DETAIL_DataSet");
//         return  teacherTimeTable.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
//          
//       }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        finally{
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//        }
//        
//    }
//    
//    
//    public String convertTVW_TEACHER_TIMETABLE_DETAILListToString(ArrayList<TVW_TEACHER_TIMETABLE_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
//       String result=new String();
//        
//        
//        try{
//            
//            for(int i=0;i<appEodList.size();i++){
//                TVW_TEACHER_TIMETABLE_DETAIL appEod=appEodList.get(i);
//                
//                String record=appEod.getDAY()+"~"+
//                              appEod.getDAY_NUMBER()+"~"+
//                              appEod.getPERIOD_NO()+"~"+
//                              appEod.getSECTION()+"~"+
//                              appEod.getSTANDARD()+"~"+
//                              appEod.getSUBJECT_ID()+"~"+
//                              appEod.getTEACHER_ID()+"~"+
//                              appEod.getVERSION_NUMBER();
//                              
//                              
//                              
//                
//                if(i==0){
//                
//                   result=record;
//                }else{
//                    
//                    result=result+"#"+record;
//                    
//                }
//                
//            }
//            
//            if(appEodList.size()==1)
//                result=result+"#";
//            
//            return result;
////     }catch(DBProcessingException ex){
////          dbg(ex);
////          throw new DBProcessingException("DBProcessingException"+ex.toString());
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
//    
//    
//    
//    
//    
//      public ArrayList<TVW_TEACHER_TIMETABLE_DETAIL>getTVW_TEACHER_TIMETABLE_DETAIL_DataSet(String p_fileName,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
//        
//        CohesiveSession tempSession = this.session;
//        try{
//            
//            this.session=session;
//            return getTVW_TEACHER_TIMETABLE_DETAIL_DataSet(p_fileName,p_instanceID);
//            
//        }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//       }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//        }
//        finally{
//           this.session=tempSession;
//        }
//        
//    } 
//    
//    
//      
//    public ArrayList<TVW_TEACHER_TIMETABLE_MASTER>getTVW_TEACHER_TIMETABLE_MASTER_DataSet(String p_fileName,String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getTVW_TEACHER_TIMETABLE_MASTER_DataSet");
//          
//          
//          
//          TVW_TEACHER_TIMETABLE_MASTER_DATASET teacherTimeTable=inject.getTeacherTimeTableMasterDataSet();
//          
//          dbg("end of geTVW_TEACHER_TIMETABLE_MASTER_DataSet");
//         return  teacherTimeTable.getTableObject(p_fileName, p_instanceID, session, dbSession, inject);
//          
//       }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
//      }catch(DBValidationException ex){
//          dbg(ex);
//          throw ex;
//     }catch(Exception ex){
//         throw new DBProcessingException("DBProcessingException"+ex.toString());
//     }
//        finally{
//            session.clearSessionObject();
//            dbSession.clearSessionObject();
//        }
//        
//    }
//    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
