/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.classEntity;

import com.ibd.businessViews.IClassDataSet;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ASSIGNMENT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_SCHEDULE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_ATTENDANCE_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_RANK;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_EXAM_SCHEDULE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_FEE_AMOUNT_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_FEE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_FEE_STATUS_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_GRADE_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_ENTRY;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_OTHER_ACTIVITY_REPORT;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_SKILL_ENTRY;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_STUDENT_MAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_TIMETABLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_TIMETABLE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_MARKS;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.STUDENT_SKILLS;
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

@Remote(IClassDataSet.class)
@Stateless
public class ClassDataSet implements IClassDataSet {
    ReportDependencyInjection inject;
    AppDependencyInjection appInject;
    CohesiveSession session;
    DBSession dbSession;
    
    public ClassDataSet(){
        try {
            inject=new ReportDependencyInjection("CLASS");
            appInject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    public String getCLASS_ASSIGNMENT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_ASSIGNMENT_DataSet");
          
          
          
          CLASS_ASSIGNMENT_DATASET classAssignment=inject.getClassAssignmentDataSet();
          
          
          dbg("end of geCLASS_ASSIGNMENT_DataSet");
         ArrayList<CLASS_ASSIGNMENT>classAss=  classAssignment.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_ASSIGNMENTListToString(classAss, session);
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
    public String convertCLASS_ASSIGNMENTListToString(ArrayList<CLASS_ASSIGNMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_ASSIGNMENT appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getASSIGNMENT_TOPIC()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getCONTENT_PATH()+"~"+
                              appEod.getCONTENT_TYPE()+"~"+
                              appEod.getDUE_DATE()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getTEACHER_COMMENT()+"~"+
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
    
    public String getCLASS_ATTENDANCE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_ATTENDANCE_DETAIL_DataSet");
          
          
          
          CLASS_ATTENDANCE_DETAIL_DATASET classAttendance=inject.getClassAttendanceDetailDataSet();
          
          
          dbg("end of geCLASS_ATTENDANCE_DETAIL_DataSet");
         ArrayList<CLASS_ATTENDANCE_DETAIL>classAttend=  classAttendance.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_ATTENDANCE_DETAILListToString(classAttend, session);
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
    
     public String convertCLASS_ATTENDANCE_DETAILListToString(ArrayList<CLASS_ATTENDANCE_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_ATTENDANCE_DETAIL appEod=appEodList.get(i);
                
                String record=appEod.getATTENDANCE()+"~"+
                              appEod.getREFERENCE_NO()+"~"+
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
   
    
    
    public String getCLASS_ATTENDANCE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getCLASS_ATTENDANCE_DETAIL_DataSet(p_standard,p_section,p_instanceID);
            
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
     
    
    
    
    
    
    
    
    
    
      public String getCLASS_ATTENDANCE_MASTER_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_ATTENDANCE_MASTER_DataSet");
          
          
          
          CLASS_ATTENDANCE_MASTER_DATASET classAttendance=inject.getClassAttendanceMasterDataSet();
          
          
          dbg("end of geCLASS_ATTENDANCE_MASTER_DataSet");
         ArrayList<CLASS_ATTENDANCE_MASTER>classAttendMaster=  classAttendance.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_ATTENDANCE_MASTERListToString(classAttendMaster, session);
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
      
      public String convertCLASS_ATTENDANCE_MASTERListToString(ArrayList<CLASS_ATTENDANCE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_ATTENDANCE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUDIT_DETAILS()+"~"+
                              appEod.getMONTH()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
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
   
      
      
      
      
    public String getCLASS_EXAM_SCHEDULE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_EXAM_SCHEDULE_DETAIL_DataSet");
          
          
          
          CLASS_EXAM_SCHEDULE_DETAIL_DATASET classExamSchedule=inject.getClassExamScheduleDetail();
          
          
          dbg("end of geCLASS_EXAM_SCHEDULE_DETAIL_DataSet");
          ArrayList<CLASS_EXAM_SCHEDULE_DETAIL>classExamSchedDetail=  classExamSchedule.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_EXAM_SCHEDULE_DETAILListToString(classExamSchedDetail, session);
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
    
    
       public String convertCLASS_EXAM_SCHEDULE_DETAILListToString(ArrayList<CLASS_EXAM_SCHEDULE_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_EXAM_SCHEDULE_DETAIL appEod=appEodList.get(i);
                
                String record=appEod.getDATE()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getHALL()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getTIME()+"~"+
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
   
   
    
    
    
      public String getCLASS_EXAM_SCHEDULE_MASTER_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_EXAM_SCHEDULE_MASTER_DataSet");
          
          
          
          CLASS_EXAM_SCHEDULE_MASTER_DATASET classExamSchedule=inject.getClassExamScheduleMaster();
          
          
          dbg("end of geCLASS_EXAM_SCHEDULE_MASTER_DataSet");
         ArrayList<CLASS_EXAM_SCHEDULE_MASTER>classExamSchedMaster=  classExamSchedule.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_EXAM_SCHEDULE_MASTERListToString(classExamSchedMaster, session);
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
      
      
      
       public String convertCLASS_EXAM_SCHEDULE_MASTERListToString(ArrayList<CLASS_EXAM_SCHEDULE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_EXAM_SCHEDULE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
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
   
      
      
      
      
      
     public String getCLASS_FEE_MANAGEMENT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_FEE_MANAGEMENT_DataSet");
          
          
          
          CLASS_FEE_MANAGEMENT_DATASET classFeeManagement=inject.getClassFeeManagementDataSet();
          
          
          dbg("end of geCLASS_FEE_MANAGEMENT_DataSet");
         ArrayList<CLASS_FEE_MANAGEMENT>classFeeManage=  classFeeManagement.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_FEE_MANAGEMENTListToString(classFeeManage, session);
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
     
      public String convertCLASS_FEE_MANAGEMENTListToString(ArrayList<CLASS_FEE_MANAGEMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_FEE_MANAGEMENT appEod=appEodList.get(i);
                
                String record=appEod.getAMOUNT()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getDUE_DATE()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getFEE_TYPE()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
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
     
     
     
      
      public String getCLASS_MARK_ENTRY_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_MARK_ENTRY_DataSet");
          
          
          
          CLASS_MARK_ENTRY_DATASET classMArkEntery=inject.getClassMarkEntryDataSet();
          
          
          dbg("end of geCLASS_MARK_ENTRY_DataSet");
         ArrayList<CLASS_MARK_ENTRY>classMarkEnt=  classMArkEntery.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_MARK_ENTRYListToString(classMarkEnt, session);
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
      
      
       public String convertCLASS_MARK_ENTRYListToString(ArrayList<CLASS_MARK_ENTRY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_MARK_ENTRY appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
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
     
     
      
       public String getCLASS_STUDENT_MAPPING_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_STUDENT_MAPPING_DataSet");
          
          
          
          CLASS_STUDENT_MAPPING_DATASET classStudentMap=inject.getClassStudentMappingDataSet();
          
          
          dbg("end of geCLASS_STUDENT_MAPPING_DataSet");
        ArrayList<CLASS_STUDENT_MAPPING>classStuMap=  classStudentMap.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_STUDENT_MAPPINGListToString(classStuMap, session);
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
       
        public String convertCLASS_STUDENT_MAPPINGListToString(ArrayList<CLASS_STUDENT_MAPPING>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_STUDENT_MAPPING appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
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
     
       
       
       
       
       
       
     public String getCLASS_STUDENT_MAPPING_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getCLASS_STUDENT_MAPPING_DataSet(p_standard,p_section,p_instanceID);
           
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
       
       
       
       
     public String getCLASS_TIMETABLE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_TIMETABLE_DETAIL_DataSet");
          
          
          
          CLASS_TIMETABLE_DETAIL_DATASET classTimeTable=inject.getClassTimeTableDetailDataset();
          
          
          dbg("end of geCLASS_TIMETABLE_DETAIL_DataSet");
         ArrayList<CLASS_TIMETABLE_DETAIL>classTimeTab=  classTimeTable.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_TIMETABLE_DETAIListToString(classTimeTab, session);
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
     
          public String convertCLASS_TIMETABLE_DETAIListToString(ArrayList<CLASS_TIMETABLE_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_TIMETABLE_DETAIL appEod=appEodList.get(i);
                
                String record=appEod.getDAY()+"~"+
                              appEod.getDAY_NUMBER()+"~"+
                              appEod.getPERIOD_NO()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getTEACHER_ID()+"~"+
                              appEod.getTEACHER_SHORT_NAME()+"~"+
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
   
     
     
     
    public String getCLASS_TIMETABLE_DETAIL_DataSet(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getCLASS_TIMETABLE_DETAIL_DataSet(p_standard,p_section,p_instanceID);
            
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
     
     public String getCLASS_TIMETABLE_MASTER_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_TIMETABLE_MASTER_DataSet");
          
          
          
          CLASS_TIMETABLE_MASTER_DATASET classTimeTable=inject.getClassTimeTableMasterDataSet();
          
          
          dbg("end of geCLASS_TIMETABLE_MASTER_DataSet");
         ArrayList<CLASS_TIMETABLE_MASTER>classTimeTabMaster=  classTimeTable.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_TIMETABLE_MASTERListToString(classTimeTabMaster, session);
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
     
     
      public String convertCLASS_TIMETABLE_MASTERListToString(ArrayList<CLASS_TIMETABLE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_TIMETABLE_MASTER appEod=appEodList.get(i);
                
                 String record=appEod.getAUTH_STATUS()+"~"+
                               appEod.getCHECKER_DATE_STAMP()+"~"+
                               appEod.getCHECKER_ID()+"~"+
                               appEod.getCHECKER_REMARKS()+"~"+
                               appEod.getMAKER_DATE_STAMP()+"~"+
                               appEod.getMAKER_ID()+"~"+
                               appEod.getMAKER_REMARKS()+"~"+
                               appEod.getRECORD_STATUS()+"~"+
                               appEod.getSECTION()+"~"+
                               appEod.getSTANDARD()+"~"+
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
   
     
     
     
     
     
      public String getSTUDENT_MARKS_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_MARKS_DataSet");
          
          
          
          STUDENT_MARKS_DATASET studentMarks=inject.getStudentMarks();
          
          
          dbg("end of geSTUDENT_MARKS_DataSet");
         ArrayList<STUDENT_MARKS>studmarks=  studentMarks.getTableObject(p_standard,p_section, p_instanceID,p_exam, session, dbSession, inject);
          String result=this.convertSTUDENT_MARKSListToString(studmarks, session);
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
    
      
        public String convertSTUDENT_MARKSListToString(ArrayList<STUDENT_MARKS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_MARKS appEod=appEodList.get(i);
                
                 String record=appEod.getEXAM()+"~"+
                               appEod.getFEEDBACK()+"~"+
                               appEod.getGRADE()+"~"+
                               appEod.getMARK()+"~"+
                               appEod.getSECTION()+"~"+
                               appEod.getSTANDARD()+"~"+
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
   
      
      
      
      
      
       public String getSTUDENT_MARKS_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getSTUDENT_MARKS_DataSet(p_standard,p_section,p_instanceID,p_exam);
            
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
       
        public String getCLASS_ATTENDANCE_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_MARKS_DataSet");
          
          
          
          CLASS_ATTENDANCE_REPORT_DATASET studentMarks=inject.getClassAttendanceReportDataset();
          
          
          dbg("end of geSTUDENT_MARKS_DataSet");
         ArrayList<CLASS_ATTENDANCE_REPORT>studmarks=  studentMarks.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_ATTENDANCE_REPORTListToString(studmarks, session);
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
    
      
        public String convertCLASS_ATTENDANCE_REPORTListToString(ArrayList<CLASS_ATTENDANCE_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_ATTENDANCE_REPORT appEod=appEodList.get(i);
                
                 String record=appEod.getABSENT_AVERAGE()+"~"+
                               appEod.getABSENT_PERCENTAGE()+"~"+
                               appEod.getLEAVE_AVERAGE()+"~"+
                               appEod.getLEAVE_PERCENTAGE()+"~"+
                               appEod.getMONTH()+"~"+
                               appEod.getPRESENT_AVERAGE()+"~"+
                               appEod.getPRESENT_PERCENTAGE()+"~"+
                               appEod.getSECTION()+"~"+
                               appEod.getSTANDARD()+"~"+
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
   
       public String getCLASS_EXAM_RANK_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_MARKS_DataSet");
          
          
          
          CLASS_EXAM_RANK_DATASET studentMarks=inject.getClassExamRankDataset();
          
          
          dbg("end of geSTUDENT_MARKS_DataSet");
         ArrayList<CLASS_EXAM_RANK>studmarks=  studentMarks.getTableObject(p_standard, p_section, p_instanceID,p_exam, session, dbSession, inject, appInject);
          String result=this.convertCLASS_EXAM_RANKListToString(studmarks, session);
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
    
      
        public String convertCLASS_EXAM_RANKListToString(ArrayList<CLASS_EXAM_RANK>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_EXAM_RANK appEod=appEodList.get(i);
                
                 String record=appEod.getEXAM()+"~"+
                               appEod.getRANK()+"~"+
                               appEod.getSTUDENT_ID()+"~"+
                               appEod.getTOTAL();
                              
                              
                              
                              
                              
                              
                
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
   
        
        
         public String getCLASS_FEE_AMOUNT_REPORT_DataSet(String p_standard,String p_section,String p_instanceID,String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_FEE_AMOUNT_REPORT_DataSet");
          
          
          
          CLASS_FEE_AMOUNT_REPORT_DATASET classFeeManagement=inject.getClassFeeAmountReportDataset();
          
          
          dbg("end of geCLASS_FEE_MANAGEMENT_DataSet");
         ArrayList<CLASS_FEE_AMOUNT_REPORT>classFeeManage=  classFeeManagement.getTableObject(p_standard,p_section, p_instanceID, p_userID,session, dbSession, inject);
          String result=this.convertCLASS_FEE_AMOUNT_REPORTListToString(classFeeManage, session);
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
     
      public String convertCLASS_FEE_AMOUNT_REPORTListToString(ArrayList<CLASS_FEE_AMOUNT_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_FEE_AMOUNT_REPORT appEod=appEodList.get(i);
                
                String record=appEod.getFEE_TYPE()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getTOTAL_BALANCE_AMOUNT()+"~"+
                              appEod.getTOTAL_FEE_AMOUNT()+"~"+
                              appEod.getTOTAL_PAID_AMOUNT();
                              
                              
                              
                              
                              
                
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
    
         public String getCLASS_FEE_STATUS_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_FEE_STATUS_REPORT_DataSet");
          
          
          
          CLASS_FEE_STATUS_REPORT_DATASET classFeeManagement=inject.getClassFeeStatusReport();
          
          
          dbg("end of getCLASS_FEE_STATUS_REPORT_DataSet");
         ArrayList<CLASS_FEE_STATUS_REPORT>classFeeManage=  classFeeManagement.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_FEE_STATUS_REPORTListToString(classFeeManage, session);
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
     
      public String convertCLASS_FEE_STATUS_REPORTListToString(ArrayList<CLASS_FEE_STATUS_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_FEE_STATUS_REPORT appEod=appEodList.get(i);
                
                String record=appEod.getFEE_TYPE()+"~"+
                              appEod.getNO_OF_STUDENTS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSTATUS();
                              
                              
                              
                              
                              
                              
                
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
    
       
         public String getCLASS_GRADE_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_GRADE_REPORT_DataSet");
          
          
          
          CLASS_GRADE_REPORT_DATASET classFeeManagement=inject.getClassGradeReportDataset();
          
          
          dbg("end of getCLASS_GRADE_REPORT_DataSet");
         ArrayList<CLASS_GRADE_REPORT>classFeeManage=  classFeeManagement.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_GRADE_REPORTListToString(classFeeManage, session);
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
     
      public String convertCLASS_GRADE_REPORTListToString(ArrayList<CLASS_GRADE_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_GRADE_REPORT appEod=appEodList.get(i);
                
                String record=appEod.getEXAM()+"~"+
                              appEod.getGRADE()+"~"+
                              appEod.getNO_OF_STUDENTS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSUBJECT_ID();
                                                      
                              
                              
                              
                              
                              
                
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
    
         
    
      
          public String getCLASS_OTHER_ACTIVITY_REPORT_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_OTHER_ACTIVITY_REPORT_DataSet");
          
          
          
          CLASS_OTHER_ACTIVITY_REPORT_DATASET classFeeManagement=inject.getClassOtherActivityReportDataset();
          
          
          dbg("end of getCLASS_OTHER_ACTIVITY_REPORT_DataSet");
         ArrayList<CLASS_OTHER_ACTIVITY_REPORT>classFeeManage=  classFeeManagement.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_OTHER_ACTIVITY_REPORTListToString(classFeeManage, session);
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
     
      public String convertCLASS_OTHER_ACTIVITY_REPORTListToString(ArrayList<CLASS_OTHER_ACTIVITY_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_OTHER_ACTIVITY_REPORT appEod=appEodList.get(i);
                
                String record=appEod.getCOUNT()+"~"+
                              appEod.getLEVEL()+"~"+
                              appEod.getNO_OF_EVENTS_CONDUCTED()+"~"+
                              appEod.getNO_OF_STUDENTS_PARTICIPATED()+"~"+
                              appEod.getRESULT_TYPE()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
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
      
      
      public String getCLASS_SKILL_ENTRY_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getCLASS_SKILL_ENTRY_DataSet");
          
          
          
          CLASS_SKILL_ENTRY_DATASET classFeeManagement=inject.getClassSkillEntryDataset();
          
          
          dbg("end of getCLASS_SKILL_ENTRY_DataSet");
         ArrayList<CLASS_SKILL_ENTRY>classFeeManage=  classFeeManagement.getTableObject(p_standard,p_section, p_instanceID, session, dbSession, inject);
          String result=this.convertCLASS_SKILL_ENTRYListToString(classFeeManage, session);
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
     
      public String convertCLASS_SKILL_ENTRYListToString(ArrayList<CLASS_SKILL_ENTRY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_SKILL_ENTRY appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSKILL_ID()+"~"+
                              appEod.getSTANDARD()+"~"+
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
    
      public String getSTUDENT_SKILLS_DataSet(String p_standard,String p_section,String p_instanceID,String p_exam)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_SKILLS_DataSet");
          
          
          
          STUDENT_SKILLS_DATASET studentMarks=inject.getStudentSkillDataset();
          
          
          dbg("end of getSTUDENT_SKILLS_DataSet");
         ArrayList<STUDENT_SKILLS>studmarks=  studentMarks.getTableObject(p_standard,p_section,p_instanceID,p_exam,session,dbSession,inject,appInject);
          String result=this.convertSTUDENT_SKILLSListToString(studmarks, session);
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
    
      
        public String convertSTUDENT_SKILLSListToString(ArrayList<STUDENT_SKILLS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_SKILLS appEod=appEodList.get(i);
                
                 String record=appEod.getEXAM()+"~"+
                               appEod.getFEEDBACK()+"~"+
                               appEod.getGRADE()+"~"+
                               appEod.getSECTION()+"~"+
                               appEod.getSKILL_ID()+"~"+
                               appEod.getSTANDARD()+"~"+
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
       
       
       
       
       
       
      
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
