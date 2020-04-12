/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.institute;

import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.institute.CLASS_LEAVE_MANAGEMENT;
import com.ibd.cohesive.report.dbreport.dataModels.institute.FEE_BATCH_INDICATOR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITITUTE_FEE_PAYMENT;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_CURRENT_DATE;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_OTHER_ACTIVITY_TRACKING;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITUTE_OTP_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_CONTENT_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_E_CIRCULAR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_FEE_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_GROUP_MAPPING_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_GROUP_MAPPING_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_HOLIDAY_MAINTANENCE;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_INSTITUTE_EXAM_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_LEAVE_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_NOTIFICATION_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_NOTIFICATION_TYPE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_PERIOD_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SKILL_GRADE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SKILL_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SOFT_SKILL_CONFIGURATION_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STANDARD_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STUDENT_LEAVE_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_STUDENT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SUBJECT_GRADE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_SUBJECT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_TEACHER_LEAVE_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_TEACHER_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.IVW_UNAUTH_RECORDS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.NOTIFICATION_BATCH_INDICATOR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.OTHER_ACTIVITY_BATCH_INDICATOR;
import com.ibd.cohesive.report.dbreport.dataModels.institute.PAYMENT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.institute.RETENTION_PERIOD;
import com.ibd.cohesive.report.dbreport.dataModels.institute.STUDENT_ASSIGNMENT_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.STUDENT_OTP_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.institute.TODAY_NOTIFICATION;
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

@Remote(IInstituteDataSet.class)
@Stateless
public class InstituteDataSet implements IInstituteDataSet{
    ReportDependencyInjection inject;
    AppDependencyInjection appInject;
    CohesiveSession session;
    DBSession dbSession;
    private String p_date;
    
    public InstituteDataSet(){
        try {
            inject=new ReportDependencyInjection("INSTITUTE");
            appInject=new AppDependencyInjection(); 
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    
     public String getIVW_CONTENT_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_CONTENT_TYPE_MASTER_DataSet");
          
          
          
          IVW_CONTENT_TYPE_MASTER_DATASET contentType=inject.getContentTypeMasterDataSet();
          
          
          dbg("end of getIVW_CONTENT_TYPE_MASTER_DataSet");
         ArrayList<IVW_CONTENT_TYPE_MASTER>contentTypeMaster=  contentType.getTableObject(p_instanceID, session, dbSession, inject);
          
         String result=this.convertIVW_CONTENT_TYPE_MASTERListToString(contentTypeMaster, session);
         
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
    
     
      public String convertIVW_CONTENT_TYPE_MASTERListToString(ArrayList<IVW_CONTENT_TYPE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_CONTENT_TYPE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getCONTENT_TYPE()+"~"+
                              appEod.getDESCRIPTION();
                              
                
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
     
     
     
     
     
     
     
    public String getIVW_FEE_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_FEE_TYPE_MASTER_DataSet");
          
          
          
          IVW_FEE_TYPE_MASTER_DATASET feeType=inject.getFeeTypeMasterDataSet();
          
          
          dbg("end of getIVW_FEE_TYPE_MASTER_DataSet");
         ArrayList<IVW_FEE_TYPE_MASTER>feeTypeMaster=  feeType.getTableObject(p_instanceID, session, dbSession, inject);
         String result=this.convertIVW_FEE_TYPE_MASTERListToString(feeTypeMaster, session);
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
  
   
      public String convertIVW_FEE_TYPE_MASTERListToString(ArrayList<IVW_FEE_TYPE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_FEE_TYPE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getFEE_TYPE();
                              
                              
                
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
    
    
    
    
    public String getIVW_INSTITUTE_EXAM_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_INSTITUTE_EXAM_MASTER_DataSet");
          
          
          
          IVW_INSTITUTE_EXAM_MASTER_DATASET examMaster=inject.getExamTypeMasterDataSet();
          
          
          dbg("end of getIVW_INSTITUTE_EXAM_MASTER_DataSet");
         ArrayList<IVW_INSTITUTE_EXAM_MASTER>instituteExamMaster=  examMaster.getTableObject(p_instanceID, session, dbSession, inject);
         String result=this.convertIVW_INSTITUTE_EXAM_MASTERListToString(instituteExamMaster, session);
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
    
    
      public String convertIVW_INSTITUTE_EXAM_MASTERListToString(ArrayList<IVW_INSTITUTE_EXAM_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_INSTITUTE_EXAM_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getEXAM();
                              
                              
                
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
    
    
    
     public String getIVW_LEAVE_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_LEAVE_TYPE_MASTER_DataSet");
          
          
          
          IVW_LEAVE_TYPE_MASTER_DATASET leaveType=inject.getLeaveTypeMasterDataSet();
          
          
          dbg("end of getIVW_LEAVE_TYPE_MASTER_DataSet");
         ArrayList<IVW_LEAVE_TYPE_MASTER>leaveTypeMaster=  leaveType.getTableObject(p_instanceID, session, dbSession, inject);
         String result=this.convertIVW_LEAVE_TYPE_MASTERListToString(leaveTypeMaster, session);
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
     
     
      public String convertIVW_LEAVE_TYPE_MASTERListToString(ArrayList<IVW_LEAVE_TYPE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_LEAVE_TYPE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getDESCRIPTION()+"~"+
                              appEod.getLEAVE_TYPE();
                              
                              
                
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
    
     
     
      public String getIVW_NOTIFICATION_TYPE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_NOTIFICATION_TYPE_MASTER_DataSet");
          
          
          
          IVW_NOTIFICATION_TYPE_MASTER_DATASET notificationType=inject.getNotificationTypeMasterDataSet();
          
          
          dbg("end of getIVW_NOTIFICATION_TYPE_MASTER_DataSet");
         ArrayList<IVW_NOTIFICATION_TYPE_MASTER>notificationTypeMaster=  notificationType.getTableObject(p_instanceID, session, dbSession, inject);
         String result=this.convertIVW_NOTIFICATION_TYPE_MASTERListToString(notificationTypeMaster, session);
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
      
      public String convertIVW_NOTIFICATION_TYPE_MASTERListToString(ArrayList<IVW_NOTIFICATION_TYPE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_NOTIFICATION_TYPE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getNOTIFICATION_TYPE();
                              
                              
                              
                
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
      
      
      
      
     public String getIVW_PERIOD_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_PERIOD_MASTER_DataSet");
          
          
          
          IVW_PERIOD_MASTER_DATASET periodType=inject.getPeriodMasterDataSet();
          
          
          dbg("end of getIVW_PERIOD_MASTER_DataSet");
         ArrayList<IVW_PERIOD_MASTER>periodMaster=  periodType.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_PERIOD_MASTERListToString(periodMaster, session);
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
     
     
     
     public String convertIVW_PERIOD_MASTERListToString(ArrayList<IVW_PERIOD_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_PERIOD_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getENDING_TIME()+"~"+
                              appEod.getPERIOD_NUMBER()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSTARTING_TIME();
                
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
      
     
     
     
     public String getIVW_STANDARD_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_STANDARD_MASTER_DataSet");
          
          
          
          IVW_STANDARD_MASTER_DATASET standard=inject.getStandardMasterDataSet();
          
          
          dbg("end of getIVW_STANDARD_MASTER_DataSet");
         ArrayList<IVW_STANDARD_MASTER>standardMaster=  standard.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_STANDARD_MASTERListToString(standardMaster, session);
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
     
     
     public String convertIVW_STANDARD_MASTERListToString(ArrayList<IVW_STANDARD_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_STANDARD_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getTEACHER_ID();
                              
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
     
     
     
     
     
     
     public String getIVW_STANDARD_MASTER_DataSet(String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getIVW_STANDARD_MASTER_DataSet(p_instanceID);
            
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
     
     
     
     
     
     
     public String getIVW_STUDENT_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_STUDENT_MASTER_DataSet");
          
          
          
          IVW_STUDENT_MASTER_DATASET student=inject.getStudentMasterDataSet();
          
          
          dbg("end of getIVW_STUDENT_MASTER_DataSet");
         ArrayList<IVW_STUDENT_MASTER>studentMaster=  student.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_STUDENT_MASTERListToString(studentMaster, session);
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
     
     
      public String convertIVW_STUDENT_MASTERListToString(ArrayList<IVW_STUDENT_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_STUDENT_MASTER appEod=appEodList.get(i);
                
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
     
     
     
    public String getIVW_SUBJECT_GRADE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_SUBJECT_GRADE_MASTER_DataSet");
          
          
          
          IVW_SUBJECT_GRADE_MASTER_DATASET subjectGrade=inject.getGradeMasterDataSet();
          
          
          dbg("end of getIVW_SUBJECT_GRADE_MASTER_DataSet");
         ArrayList<IVW_SUBJECT_GRADE_MASTER>subjectGradeMaster=  subjectGrade.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_SUBJECT_GRADE_MASTERListToString(subjectGradeMaster, session);
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
    
    
    
      public String convertIVW_SUBJECT_GRADE_MASTERListToString(ArrayList<IVW_SUBJECT_GRADE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_SUBJECT_GRADE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getDESCRIPTION()+"~"+
                              appEod.getGRADE()+"~"+
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
    
    
    
     public String getIVW_SUBJECT_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_SUBJECT_MASTER_DataSet");
          
          
          
          IVW_SUBJECT_MASTER_DATASET subject=inject.getSubjectMasterDataSet();
          
          
          dbg("end of getIVW_SUBJECT_MASTER_DataSet");
         ArrayList<IVW_SUBJECT_MASTER>subjectMaster=  subject.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_SUBJECT_MASTERListToString(subjectMaster, session);
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
    
     
      public String convertIVW_SUBJECT_MASTERListToString(ArrayList<IVW_SUBJECT_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_SUBJECT_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getSUBJECT_ID()+"~"+
                              appEod.getSUBJECT_NAME();
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
    
     
     
     
     
     public String getIVW_TEACHER_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
          IVW_TEACHER_MASTER_DATASET teacher=inject.getTeacherMasterDataSet();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
         ArrayList<IVW_TEACHER_MASTER>teacherMaster=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_TEACHER_MASTERListToString(teacherMaster, session);
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
     




public String convertIVW_TEACHER_MASTERListToString(ArrayList<IVW_TEACHER_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_TEACHER_MASTER appEod=appEodList.get(i);
                
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
                              appEod.getTEACHER_ID()+"~"+
                              appEod.getTEACHER_NAME()+"~"+
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


public String getINSTITUTE_OTP_STATUS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
          INSTITUTE_OTP_STATUS_DATASET teacher=inject.getInstituteOtpStatus();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
         ArrayList<INSTITUTE_OTP_STATUS>instituteotpStatus=  teacher.getTableObject(p_instanceID,p_date, session, dbSession, inject,appInject);
          String result=this.convertINSTITUTE_OTP_STATUSListToString(instituteotpStatus, session);
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
     




public String convertINSTITUTE_OTP_STATUSListToString(ArrayList<INSTITUTE_OTP_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                INSTITUTE_OTP_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getEND_POINT()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getOTP()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getTIME();
                
                
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


 public String getCLASS_LEAVE_MANAGEMENT_DataSet(String p_instanceID,String p_standard,String p_section)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
          CLASS_LEAVE_MANAGEMENT_DATASET teacher=inject.getClassLeaveManagementDataset();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
         ArrayList<CLASS_LEAVE_MANAGEMENT>classLeaveManagement=  teacher.getTableObject(p_instanceID,p_standard,p_section, session, dbSession, inject);
          String result=this.convertCLASS_LEAVE_MANAGEMENTListToString(classLeaveManagement, session);
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
     




public String convertCLASS_LEAVE_MANAGEMENTListToString(ArrayList<CLASS_LEAVE_MANAGEMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_LEAVE_MANAGEMENT appEod=appEodList.get(i);
                
                String record=appEod.getFROM()+"~"+
                              appEod.getFROM_NOON()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getTO()+"~"+
                              appEod.getTO_NOON();
                              
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
       
 public String getINSTITUTE_OTHER_ACTIVITY_TRACKING_DataSet(String p_instanceID,String p_activityID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
          INSTITUTE_OTHER_ACTIVITY_TRACKING_DATASET teacher=inject.getInstituteOtherActivityTrackingDataset();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
          ArrayList<INSTITUTE_OTHER_ACTIVITY_TRACKING>instituteOtherActivityTracking=  teacher.getTableObject(p_instanceID,p_activityID,session, dbSession, inject);
          String result=this.convertINSTITUTE_OTHER_ACTIVITY_TRACKINGListToString(instituteOtherActivityTracking, session);
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
     




public String convertINSTITUTE_OTHER_ACTIVITY_TRACKINGListToString(ArrayList<INSTITUTE_OTHER_ACTIVITY_TRACKING>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                INSTITUTE_OTHER_ACTIVITY_TRACKING appEod=appEodList.get(i);
                
                String record=appEod.getCONFIRMATION_DATE()+"~"+
                              appEod.getPARTICIPATION_STATUS()+"~"+
                              appEod.getRESULT()+"~"+
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

public String getSTUDENT_ASSIGNMENT_STATUS_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
          STUDENT_ASSIGNMENT_STATUS_DATASET teacher=inject.getStudentAssignmentstatus();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
         ArrayList<STUDENT_ASSIGNMENT_STATUS>studentAssignmentStatus=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertSTUDENT_ASSIGNMENT_STATUSListToString(studentAssignmentStatus, session);
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
     




public String convertSTUDENT_ASSIGNMENT_STATUSListToString(ArrayList<STUDENT_ASSIGNMENT_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_ASSIGNMENT_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getCOMPLETED_DATE()+"~"+
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

public String getINSTITITUTE_FEE_PAYMENT_DataSet(String p_instanceID,String p_feeID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
          INSTITITUTE_FEE_PAYMENT_DATASET teacher=inject.getInstituteFeePaymentDataset();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
          ArrayList<INSTITITUTE_FEE_PAYMENT>instituteFeePayment=  teacher.getTableObject(p_instanceID,p_feeID, session, dbSession, inject);
          String result=this.convertINSTITITUTE_FEE_PAYMENTListToString(instituteFeePayment, session);
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
     




public String convertINSTITITUTE_FEE_PAYMENTListToString(ArrayList<INSTITITUTE_FEE_PAYMENT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                INSTITITUTE_FEE_PAYMENT appEod=appEodList.get(i);
                
                String record=appEod.getAMOUNT()+"~"+
                              appEod.getDATE()+"~"+
                              appEod.getPAYMENT_ID()+"~"+
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


public String getPAYMENT_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_MASTER_DataSet");
          
          
          
          PAYMENT_MASTER_DATASET teacher=inject.getPaymentMasterDataset();
          
          
          dbg("end of getIVW_TEACHER_MASTER_DataSet");
         ArrayList<PAYMENT_MASTER>paymentMaster=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.getPAYMENT_MASTER_DataSet(p_instanceID);
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
     




public String convertPAYMENT_MASTERListToString(ArrayList<PAYMENT_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                PAYMENT_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getPAYMENT_DATE()+"~"+
                              appEod.getPAYMENT_ID();
                              
                              
                              
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
       
public String getFEE_BATCH_INDICATOR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFEE_BATCH_INDICATOR_DataSet");
          
          
          
         FEE_BATCH_INDICATOR_DATASET teacher=inject.getFeeBatchIndicatorDataset();
          
          
          dbg("end of getFEE_BATCH_INDICATOR_DataSet");
         ArrayList<FEE_BATCH_INDICATOR>studentAssignmentStatus=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertFEE_BATCH_INDICATORListToString(studentAssignmentStatus, session);
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
     




public String convertFEE_BATCH_INDICATORListToString(ArrayList<FEE_BATCH_INDICATOR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FEE_BATCH_INDICATOR appEod=appEodList.get(i);
                
                String record=appEod.getFEE_ID()+"~"+
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


public String getINSTITUTE_CURRENT_DATE_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_CURRENT_DATE_DataSet");
          
          
          
          INSTITUTE_CURRENT_DATE_DATASET teacher=inject.getInstituteCurrentDateDataset();
          
          
          dbg("end of getINSTITUTE_CURRENT_DATE_DataSet");
          ArrayList<INSTITUTE_CURRENT_DATE>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertINSTITUTE_CURRENT_DATEListToString(instituteFeePayment, session);
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
     




public String convertINSTITUTE_CURRENT_DATEListToString(ArrayList<INSTITUTE_CURRENT_DATE>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                INSTITUTE_CURRENT_DATE appEod=appEodList.get(i);
                
                String record=appEod.getCURRENT_DATE()+"~"+
                              appEod.getINSTITUTE_ID();
                             
                              
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


public String getIVW_E_CIRCULAR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_E_CIRCULAR_DataSet");
          
          
          
          IVW_E_CIRCULAR_DATASET teacher=inject.getIvwEcircularDataset();
          
          
          dbg("end of getIVW_E_CIRCULAR_DataSet");
          ArrayList<IVW_E_CIRCULAR>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_E_CIRCULARListToString(instituteFeePayment, session);
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
     




public String convertIVW_E_CIRCULARListToString(ArrayList<IVW_E_CIRCULAR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_E_CIRCULAR appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getCONTENT_PATH()+"~"+
                              appEod.getDESCRIPTION()+"~"+
                              appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
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


public String getIVW_GROUP_MAPPING_DETAIL_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_GROUP_MAPPING_DETAIL_DataSet");
          
          
          
          IVW_GROUP_MAPPING_DETAIL_DATASET teacher=inject.getIvwGroupMappingDetailDataset();
          
          
          dbg("end of getIVW_GROUP_MAPPING_DETAIL_DataSet");
          ArrayList<IVW_GROUP_MAPPING_DETAIL>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_GROUP_MAPPING_DETAILListToString(instituteFeePayment, session);
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
     




public String convertIVW_GROUP_MAPPING_DETAILListToString(ArrayList<IVW_GROUP_MAPPING_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_GROUP_MAPPING_DETAIL appEod=appEodList.get(i);
                
                String record=appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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

//public String getIIVW_GROUP_MAPPING_DETAIL_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getIVW_GROUP_MAPPING_DETAIL_DataSet");
//          
//          
//          
//          IVW_GROUP_MAPPING_DETAIL_DATASET teacher=inject.getTeacherMasterDataSet();
//          
//          
//          dbg("end of getIVW_GROUP_MAPPING_DETAIL_DataSet");
//          ArrayList<IVW_GROUP_MAPPING_DETAIL>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
//          String result=this.convertIVW_GROUP_MAPPING_DETAILListToString(instituteFeePayment, session);
//          return result;
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
//
//
//
//public String convertIVW_GROUP_MAPPING_DETAILListToString(ArrayList<IVW_GROUP_MAPPING_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
//       String result=new String();
//        
//        
//        try{
//            
//            for(int i=0;i<appEodList.size();i++){
//                IVW_GROUP_MAPPING_DETAIL appEod=appEodList.get(i);
//                
//                String record=appEod.getGROUP_ID()+"~"+
//                              appEod.getINSTITUTE_ID()+"~"+
//                              appEod.getSECTION()+"~"+
//                              appEod.getSTANDARD()+"~"+
//                              appEod.getSTUDENT_ID()+"~"+
//                              appEod.getVERSION_NUMBER();
//                             
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
public String getIVW_GROUP_MAPPING_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_GROUP_MAPPING_MASTER_DataSet");
          
          
          
          IVW_GROUP_MAPPING_MASTER_DATASET teacher=inject.getIvwGroupMappingMasterDataset();
          
          
          dbg("end of getIVW_GROUP_MAPPING_MASTER_DataSet");
          ArrayList<IVW_GROUP_MAPPING_MASTER>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_GROUP_MAPPING_MASTERListToString(instituteFeePayment, session);
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
     




public String convertIVW_GROUP_MAPPING_MASTERListToString(ArrayList<IVW_GROUP_MAPPING_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_GROUP_MAPPING_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getGROUP_DESCRIPTION()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
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

public String getIVW_HOLIDAY_MAINTANENCE_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_HOLIDAY_MAINTANENCE_DataSet");
          
          
          
         IVW_HOLIDAY_MAINTANENCE_DATASET teacher=inject.getIvwHolidayMaintanenceDataset();
          
          
          dbg("end of getIVW_HOLIDAY_MAINTANENCE_DataSet");
          ArrayList<IVW_HOLIDAY_MAINTANENCE>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_HOLIDAY_MAINTANENCEListToString(instituteFeePayment, session);
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
     




public String convertIVW_HOLIDAY_MAINTANENCEListToString(ArrayList<IVW_HOLIDAY_MAINTANENCE>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_HOLIDAY_MAINTANENCE appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getHOLIDAY()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getMONTH()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getVERSION_NUMBER()+"~"+
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

public String getIVW_NOTIFICATION_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_NOTIFICATION_MASTER_DataSet");
          
          
          
         IVW_NOTIFICATION_MASTER_DATASET teacher=inject.getIvwNotificationMasterDataset();        
          dbg("end of getIVW_NOTIFICATION_MASTER_DataSet");
          ArrayList<IVW_NOTIFICATION_MASTER>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_NOTIFICATION_MASTERListToString(instituteFeePayment, session);
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
     




public String convertIVW_NOTIFICATION_MASTERListToString(ArrayList<IVW_NOTIFICATION_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_NOTIFICATION_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNEE()+"~"+
                              appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getDATE()+"~"+
                              appEod.getDAY()+"~"+
                              appEod.getEMAIL()+"~"+
                              appEod.getINSTANT()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getMEDIA_COMMUNICATION()+"~"+
                              appEod.getMESSAGE()+"~"+
                              appEod.getMOBILE_NO()+"~"+
                              appEod.getMONTH()+"~"+
                              appEod.getNOTIFICATION_FREQUENCY()+"~"+
                              appEod.getNOTIFICATION_ID()+"~"+
                              appEod.getNOTIFICATION_TYPE()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
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


public String getIVW_SKILL_GRADE_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_SKILL_GRADE_MASTER_DataSet");
          
          
          
         IVW_SKILL_GRADE_MASTER_DATASET teacher=inject.getIvwSkillGradeMasterDataset();
          
          
          dbg("end of getIVW_SKILL_GRADE_MASTER_DataSet");
          ArrayList<IVW_SKILL_GRADE_MASTER>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_SKILL_GRADE_MASTERListToString(instituteFeePayment, session);
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
     




public String convertIVW_SKILL_GRADE_MASTERListToString(ArrayList<IVW_SKILL_GRADE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_SKILL_GRADE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getDESCRIPTION()+"~"+
                              appEod.getGRADE()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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

public String getIVW_SKILL_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_SKILL_MASTER_DataSet");
          
          
          
         IVW_SKILL_MASTER_DATASET teacher=inject.getIvwSkillMasterDataset();
          
          
          dbg("end of getIVW_SKILL_MASTER_DataSet");
          ArrayList<IVW_SKILL_MASTER>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_SKILL_MASTERListToString(instituteFeePayment, session);
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
     




public String convertIVW_SKILL_MASTERListToString(ArrayList<IVW_SKILL_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_SKILL_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSKILL_ID()+"~"+
                              appEod.getSKILL_NAME()+"~"+
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

public String getIVW_SOFT_SKILL_CONFIGURATION_MASTER_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_SKILL_MASTER_DataSet");
          
          
          
         IVW_SOFT_SKILL_CONFIGURATION_MASTER_DATASET teacher=inject.getIvwSoftSkillConfigurationMasterDataSet();
          
          
          dbg("end of getIVW_SOFT_SKILL_CONFIGURATION_MASTER_DataSet");
          ArrayList<IVW_SOFT_SKILL_CONFIGURATION_MASTER>instituteFeePayment=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertIVW_SOFT_SKILL_CONFIGURATION_MASTERListToString(instituteFeePayment, session);
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
     




public String convertIVW_SOFT_SKILL_CONFIGURATION_MASTERListToString(ArrayList<IVW_SOFT_SKILL_CONFIGURATION_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_SOFT_SKILL_CONFIGURATION_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
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

    public String getIVW_STUDENT_LEAVE_DETAILS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_STUDENT_LEAVE_DETAILS_DataSet");
          
          
          
          IVW_STUDENT_LEAVE_DETAILS_DATASET student=inject.getIvwStudentLeaveDetailsDataset();          
          
          dbg("end of getIVW_STUDENT_LEAVE_DETAILS_DataSet");
         ArrayList<IVW_STUDENT_LEAVE_DETAILS>studentMaster=  student.getTableObject(p_instanceID,p_date, session, dbSession, inject,appInject);
          String result=this.convertIVW_STUDENT_LEAVE_DETAILSListToString(studentMaster, session);
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
     
     
      public String convertIVW_STUDENT_LEAVE_DETAILSListToString(ArrayList<IVW_STUDENT_LEAVE_DETAILS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_STUDENT_LEAVE_DETAILS appEod=appEodList.get(i);
                
                String record=appEod.getFULL_DAY()+"~"+
                              appEod.getNOON()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
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
 
public String getIVW_TEACHER_LEAVE_DETAILS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_TEACHER_LEAVE_DETAILS_DataSet");
          
          
          
         IVW_TEACHER_LEAVE_DETAILS_DATASET teacher=inject.getIvwTeacherLeaveDetailDataset();
          
          
          dbg("end of getIVW_TEACHER_LEAVE_DETAILS_DataSet");
          ArrayList<IVW_TEACHER_LEAVE_DETAILS>instituteFeePayment=  teacher.getTableObject(p_instanceID,p_date, session, dbSession, inject,appInject);
          String result=this.convertIVW_TEACHER_LEAVE_DETAILSListToString(instituteFeePayment, session);
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
     




public String convertIVW_TEACHER_LEAVE_DETAILSListToString(ArrayList<IVW_TEACHER_LEAVE_DETAILS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_TEACHER_LEAVE_DETAILS appEod=appEodList.get(i);
                
                String record=appEod.getFULL_DAY()+"~"+
                              appEod.getNOON()+"~"+
                              appEod.getTEACHER_ID();
                              
                                                         
                              
                             
                              
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
public String getIVW_UNAUTH_RECORDS_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getIVW_UNAUTH_RECORDS_DataSet");
          
          
          
         IVW_UNAUTH_RECORDS_DATASET teacher=inject.getIvwUnauthRecords();
          
          
          dbg("end of getIVW_UNAUTH_RECORDS_DataSet");
         ArrayList<IVW_UNAUTH_RECORDS>teacherMaster=  teacher.getTableObject(p_instanceID,p_date, session, dbSession, inject,appInject);
          String result=this.convertIVW_UNAUTH_RECORDSListToString(teacherMaster, session);
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
     




public String convertIVW_UNAUTH_RECORDSListToString(ArrayList<IVW_UNAUTH_RECORDS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                IVW_UNAUTH_RECORDS appEod=appEodList.get(i);
                
                String record=appEod.getENTITY_NAME()+"~"+
                              appEod.getENTITY_VALUE()+"~"+
                              appEod.getOPERATION()+"~"+
                              appEod.getPRIMARY_KEY()+"~"+
                              appEod.getSERVICE();
                             
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

public String getNOTIFICATION_BATCH_INDICATOR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getNOTIFICATION_BATCH_INDICATOR_DataSet");
          
          
          
         NOTIFICATION_BATCH_INDICATOR_DATASET teacher=inject.getNotificationBatchIndicatorDataset();
          
          
          dbg("end of getIVW_UNAUTH_RECORDS_DataSet");
         ArrayList<NOTIFICATION_BATCH_INDICATOR>teacherMaster=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertNOTIFICATION_BATCH_INDICATORListToString(teacherMaster, session);
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
     




public String convertNOTIFICATION_BATCH_INDICATORListToString(ArrayList<NOTIFICATION_BATCH_INDICATOR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                NOTIFICATION_BATCH_INDICATOR appEod=appEodList.get(i);
                
                String record=appEod.getNOTIFICATION_ID()+"~"+
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


public String getOTHER_ACTIVITY_BATCH_INDICATOR_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getOTHER_ACTIVITY_BATCH_INDICATOR_DataSet");
          
          
          
         OTHER_ACTIVITY_BATCH_INDICATOR_DATASET teacher=inject.getOtherActivityBatchIndicatorDataset();
          
          
          dbg("end of getOTHER_ACTIVITY_BATCH_INDICATOR_DataSet");
         ArrayList<OTHER_ACTIVITY_BATCH_INDICATOR>teacherMaster=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertOTHER_ACTIVITY_BATCH_INDICATORListToString(teacherMaster, session);
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
     




public String convertOTHER_ACTIVITY_BATCH_INDICATORListToString(ArrayList<OTHER_ACTIVITY_BATCH_INDICATOR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                OTHER_ACTIVITY_BATCH_INDICATOR appEod=appEodList.get(i);
                
                String record=appEod.getNOTIFICATION_ID()+"~"+
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


public String getRETENTION_PERIOD_DataSet(String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getRETENTION_PERIOD_DataSet");
          
          
          
        RETENTION_PERIOD_DATASET teacher=inject.getRetentionPeriodDataset();
          
          
          dbg("end of getRETENTION_PERIOD_DataSet");
         ArrayList<RETENTION_PERIOD>studentAssignmentStatus=  teacher.getTableObject(p_instanceID, session, dbSession, inject);
          String result=this.convertRETENTION_PERIODListToString(studentAssignmentStatus, session);
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
     




public String convertRETENTION_PERIODListToString(ArrayList<RETENTION_PERIOD>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                RETENTION_PERIOD appEod=appEodList.get(i);
                
                String record=appEod.getDAYS()+"~"+
                              appEod.getFEATURE_NAME()+"~"+
                              appEod.getINSTITUTE_ID();
                        
                              
                              
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


public String getTODAY_NOTIFICATION_DataSet(String p_instanceID,String p_date)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTODAY_NOTIFICATION_DataSet");
          
          
          
        TODAY_NOTIFICATION_DATASET teacher=inject.getTodaynotificationDataset();
          
          
          dbg("end of getTODAY_NOTIFICATION_DataSet");
         ArrayList<TODAY_NOTIFICATION>todayNotification= teacher.getTableObject(p_instanceID, p_date, session, dbSession, inject);
          String result=this.convertTODAY_NOTIFICATIONListToString(todayNotification, session);
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
     




public String convertTODAY_NOTIFICATIONListToString(ArrayList<TODAY_NOTIFICATION>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TODAY_NOTIFICATION appEod=appEodList.get(i);
                
                String record=appEod.getEND_POINT()+"~"+
                              appEod.getMESSAGE()+"~"+
                              appEod.getNOTIFICATION_TYPE()+"~"+
                              appEod.getSTATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getTITLE();
                        
                              
                              
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