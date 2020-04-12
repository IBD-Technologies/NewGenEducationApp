/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.batch;

import com.ibd.businessViews.IBatchDataset;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.APP_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.APP_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.APP_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ASSIGNMENT_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ASSIGNMENT_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ASSIGNMENT_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ATTENDANCE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ATTENDANCE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.ATTENDANCE_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.EXAM_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.EXAM_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.EXAM_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.E_CIRCULAR_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.E_CIRCULAR_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.E_CIRCULAR_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.FEE_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.FEE_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.FEE_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.FEE_NOTIFICATION_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.FEE_NOTIFICATION_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.FEE_NOTIFICATION_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.INSTITUTE_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.MARK_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.MARK_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.MARK_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.NOTIFICATION_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.NOTIFICATION_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.NOTIFICATION_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.OTHER_ACTIVITY_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.OTHER_ACTIVITY_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.OTHER_ACTIVITY_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ATTENDANCE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ATTENDANCE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EVENT_NOTIFICATION_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EXAM_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EXAM_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_EXAM_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_E_CIRCULAR_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_E_CIRCULAR_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_FEE_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_FEE_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_FEE_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_FEE_NOTIFICATION_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_MARK_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_MARK_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_MARK_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_NOTIFICATION_EMAIL_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_NOTIFICATION_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_NOTIFICATION_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_NOTIFICATION_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_NOTIFICATION_SMS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_OTHER_ACTIVITY_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_TIMETABLE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_TIMETABLE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.STUDENT_TIMETABLE_BATCH_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TEACHER_E_CIRCULAR_EOD_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TEACHER_E_CIRCULAR_EOD_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS_ERROR;
import com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch.TIMETABLE_BATCH_STATUS_HISTORY;
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
@Remote(IBatchDataset.class)
@Stateless
public class BatchDataset implements IBatchDataset{
    ReportDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
    public BatchDataset(){
        try {
            inject=new ReportDependencyInjection("BATCH");
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
//    public ArrayList<APP_EOD_STATUS>getAPP_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getAPP_EOD_STATUS_DataSet");
//          
//          
//          
//          APP_EOD_STATUS_DATASET batch=inject.getAppEodDatset();
//          
//          
//          dbg("end of getAPP_EOD_STATUS_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    
    public String getAPP_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getAPP_EOD_STATUS_DataSet");
          
          
          
          APP_EOD_STATUS_DATASET appEod=inject.getAppEodDatset();
          
          
          dbg("end of getAPP_EOD_STATUS_DataSet");
          ArrayList<APP_EOD_STATUS>instituteMaster  = appEod.getTableObject(p_businessDate, session, dbSession, inject);
          
          String result=  convertAPP_EOD_STATUSListToString(instituteMaster,session);
          dbg(" getAPP_EOD_STATUS_DataSet--->result--->"+result);
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

    public String convertAPP_EOD_STATUSListToString(ArrayList<APP_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                APP_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getSTART_TIME()+"~"+appEod.getEND_TIME()+"~"+appEod.getEOD_STATUS()+"~"+appEod.getERROR()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getNO_OF_FAILURES()+"~"+appEod.getSEQUENCE_NO();
                
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
    public String getAPP_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getAPP_EOD_STATUS_ERROR_DataSet");
          
          
          
          APP_EOD_STATUS_ERROR_DATASET appEod=inject.getAppEodErrorDataset();
          
          
          dbg("end of getAPP_EOD_STATUS_ERROR_DataSet");
          ArrayList<APP_EOD_STATUS_ERROR>instituteMaster  = appEod.getTableObject(p_businessDate, session, dbSession, inject);
          
          String result=  convertAPP_EOD_STATUS_ERRORListToString(instituteMaster,session);
          
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

    public String convertAPP_EOD_STATUS_ERRORListToString(ArrayList<APP_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                APP_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getERROR();
                
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
    
    
//    public ArrayList<APP_EOD_STATUS_ERROR>getAPP_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getAPP_EOD_STATUS_ERROR_DataSet");
//          
//          
//          
//          APP_EOD_STATUS_ERROR_DATASET batch=inject.getAppEodErrorDataset();
//          
//          
//          dbg("end of getAPP_EOD_STATUS_ERROR_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    
    
    
    
    
    
    
//    public ArrayList<APP_EOD_STATUS_HISTORY>getAPP_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getAPP_EOD_STATUS_HISTORY_DataSet");
//          
//          
//          
//          APP_EOD_STATUS_HISTORY_DATASET batch=inject.getAppEodHistoryDataset();
//          
//          
//          dbg("end of getAPP_EOD_STATUS_HISTORY_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    public String getAPP_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getAPP_EOD_STATUS_HISTORY_DataSet");
          
          
          
          APP_EOD_STATUS_HISTORY_DATASET appEod=inject.getAppEodHistoryDataset();
          
          
          dbg("end of getAPP_EOD_STATUS_HISTORY_DataSet");
          ArrayList<APP_EOD_STATUS_HISTORY>instituteMaster  = appEod.getTableObject(p_businessDate, session, dbSession, inject);
          
          String result=  convertAPP_EOD_STATUS_HISTORYListToString(instituteMaster,session);
          
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

    public String convertAPP_EOD_STATUS_HISTORYListToString(ArrayList<APP_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                APP_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getEOD_STATUS()+"~"+appEod.getERROR()+"~"+appEod.getNO_OF_FAILURES()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getSEQUENCE_NO()+"~"+appEod.getSTART_TIME();
                
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
    
   
    
//    public ArrayList<BATCH_CONFIG>getBATCH_CONFIG_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getBATCH_CONFIG_DataSet");
//          
//          
//          
//          BATCH_CONFIG_DATASET batch=inject();
//          
//          
//          dbg("end of geBATCH_CONFIG_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
//    public ArrayList<BATCH_STATUS>getBATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getBATCH_STATUS_DataSet");
//          
//          
//          
//          BATCH_STATUS_DATASET batch=inject.getBatchEodDataset();
//          
//          
//          dbg("end of geBATCH_STATUS_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    
    public String getBATCH_STATUS_DataSet(String p_businessDate,String p_instituteID,String batchName)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getBATCH_STATUS_DataSet");
          dbg("batchName"+batchName);
          
          
          BATCH_STATUS_DATASET appEod=inject.getBatchEodDataset();
          
          
          dbg("end of getBATCH_STATUS_DataSet");
          ArrayList<BATCH_STATUS>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject,batchName);
          
          String result=  convertBATCH_STATUSListToString(instituteMaster,session);
          dbg(" getBATCH_STATUS_DataSet--->result--->"+result);
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

    public String convertBATCH_STATUSListToString(ArrayList<BATCH_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                BATCH_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBATCH_NAME()+"~"+appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getEOD_STATUS()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNO_FAILURES()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getSEQUENCE_NO()+"~"+appEod.getSTART_TIME();
                
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
    
    
    
//    public ArrayList<BATCH_STATUS_ERROR>getBATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getBATCH_STATUS_ERROR_DataSet");
//          
//          
//          
//          BATCH_STATUS_ERROR_DATASET batch=inject.getBatchErrorDataset();
//          
//          
//          dbg("end of geBATCH_STATUS_ERROR_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    public String getBATCH_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID,String batchName)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getBATCH_STATUS_ERROR_DataSet");
           dbg("batchName"+batchName);
          
          
          BATCH_STATUS_ERROR_DATASET appEod=inject.getBatchErrorDataset();
          
          
          dbg("end of getBATCH_STATUS_ERROR_DataSet");
          ArrayList<BATCH_STATUS_ERROR>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject,batchName);
          
          String result=  convertBATCH_STATUS_ERRORListToString(instituteMaster,session);
          
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

    public String convertBATCH_STATUS_ERRORListToString(ArrayList<BATCH_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                BATCH_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBATCH_NAME()+"~"+appEod.getBUSINESS_DATE()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID();
                
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
//    public ArrayList<BATCH_STATUS_HISTORY>getBATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getBATCH_STATUS_HISTORY_DataSet");
//          
//          
//          
//          BATCH_STATUS_HISTORY_DATASET batch=inject.getBatchHistoryDataset();
//          
//          
//          dbg("end of geBATCH_STATUS_HISTORY_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    
    public String getBATCH_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID,String batchName)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getBATCH_STATUS_HISTORY_DataSet");
           dbg("batchName"+batchName);
          
          
          BATCH_STATUS_HISTORY_DATASET appEod=inject.getBatchHistoryDataset();
          
          
          dbg("end of getBATCH_STATUS_HISTORY_DataSet");
          ArrayList<BATCH_STATUS_HISTORY>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject,batchName);
          
          String result=  convertBATCH_STATUS_HISTORYListToString(instituteMaster,session);
          
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

    public String convertBATCH_STATUS_HISTORYListToString(ArrayList<BATCH_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                BATCH_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBATCH_NAME()+"~"+appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getEOD_STATUS()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNO_FAILURES()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getSEQUENCE_NO()+"~"+appEod.getSTART_TIME();
                
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
    
    
//    public ArrayList<INSTITUTE_EOD_STATUS>getINSTITUTE_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getINSTITUTE_EOD_STATUS_DataSet");
//          
//          
//          
//          INSTITUTE_EOD_STATUS_DATASET batch=inject.getInstituteEodDataset();
//          
//          
//          dbg("end of geINSTITUTE_EOD_STATUS_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    
    public String getINSTITUTE_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_EOD_STATUS_DataSet");
          
          
          
          INSTITUTE_EOD_STATUS_DATASET appEod=inject.getInstituteEodDataset();
          
          
          dbg("end of getINSTITUTE_EOD_STATUS_DataSet");
          ArrayList<INSTITUTE_EOD_STATUS>instituteMaster  = appEod.getTableObject(p_businessDate, session, dbSession, inject);
          
          String result=  convertINSTITUTE_EOD_STATUSListToString(instituteMaster,session);
          dbg("getINSTITUTE_EOD_STATUS_DataSet---->result-->"+result);
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

    public String convertINSTITUTE_EOD_STATUSListToString(ArrayList<INSTITUTE_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                INSTITUTE_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getEOD_STATUS()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNO_OF_FAILURES()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getSEQUENCE_NO()+"~"+appEod.getSTART_TIME();
                
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
    
    
//    public ArrayList<INSTITUTE_EOD_STATUS_ERROR>getINSTITUTE_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getINSTITUTE_EOD_STATUS_ERROR_DataSet");
//          
//          
//          
//          INSTITUTE_EOD_STATUS_ERROR_DATASET batch=inject.getInstituteErrorDataset();
//          
//          
//          dbg("end of geINSTITUTE_EOD_STATUS_ERROR_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
     public String getINSTITUTE_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_EOD_STATUS_ERROR_DataSet");
          
          
          
          INSTITUTE_EOD_STATUS_ERROR_DATASET appEod=inject.getInstituteErrorDataset();
          
          
          dbg("end of getINSTITUTE_EOD_STATUS_ERROR_DataSet");
          ArrayList<INSTITUTE_EOD_STATUS_ERROR>instituteMaster  = appEod.getTableObject(p_businessDate, session, dbSession, inject);
          
          String result=  convertINSTITUTE_EOD_STATUS_ERRORListToString(instituteMaster,session);
          
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

    public String convertINSTITUTE_EOD_STATUS_ERRORListToString(ArrayList<INSTITUTE_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                INSTITUTE_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID();
                
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
    
    
//    public ArrayList<INSTITUTE_EOD_STATUS_HISTORY>getINSTITUTE_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getINSTITUTE_EOD_STATUS_HISTORY_DataSet");
//          
//          
//          
//          INSTITUTE_EOD_STATUS_HISTORY_DATASET batch=inject.getInstituteEodHistoryDataset();
//          
//          
//          dbg("end of geINSTITUTE_EOD_STATUS_HISTORY_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
      public String getINSTITUTE_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_EOD_STATUS_HISTORY_DataSet");
          
          
          
          INSTITUTE_EOD_STATUS_HISTORY_DATASET appEod=inject.getInstituteEodHistoryDataset();
          
          
          dbg("end of getINSTITUTE_EOD_STATUS_HISTORY_DataSet");
          ArrayList<INSTITUTE_EOD_STATUS_HISTORY>instituteMaster  = appEod.getTableObject(p_businessDate, session, dbSession, inject);
          
          String result=  convertINSTITUTE_EOD_STATUS_HISTORYListToString(instituteMaster,session);
          
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

    public String convertINSTITUTE_EOD_STATUS_HISTORYListToString(ArrayList<INSTITUTE_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                INSTITUTE_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getEOD_STATUS()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNO_OF_FAILURES()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getSEQUENCE_NO()+"~"+appEod.getSTART_TIME();
                
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
    
    public String getOTHER_ACTIVITY_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getOTHER_ACTIVITY_EOD_STATUS_DataSet");
          
          
          
          OTHER_ACTIVITY_EOD_STATUS_DATASET batch=inject.getOtherActivityBatchDataSet();
          
          
          dbg("end of getOTHER_ACTIVITY_EOD_STATUS_DataSet");
         ArrayList<OTHER_ACTIVITY_EOD_STATUS>eodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         String result= convertOTHER_ACTIVITY_EOD_STATUSListToString(eodList,session);
         
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
    
    
    public String convertOTHER_ACTIVITY_EOD_STATUSListToString(ArrayList<OTHER_ACTIVITY_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                OTHER_ACTIVITY_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
      public String getASSIGNMENT_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getASSIGNMENT_EOD_STATUS_DataSet");
          
          
          
          ASSIGNMENT_EOD_STATUS_DATASET batch=inject.getAssignmentEodDataSet();
          
          
          
         ArrayList<ASSIGNMENT_EOD_STATUS>eodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         String result= convertASSIGNMENT_EOD_STATUSListToString(eodList,session);
         dbg("end of getASSIGNMENT_EOD_STATUS_DataSet--->result-->"+result);
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
    
    
    public String convertASSIGNMENT_EOD_STATUSListToString(ArrayList<ASSIGNMENT_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ASSIGNMENT_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSTART_TIME()+"~"+
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

    
    
    
    

    
    public String getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet");
          
          
          
          OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET batch=inject.getOtherActivityBatchErrorDataSet();
          
          
          dbg("end of getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet");
          ArrayList<OTHER_ACTIVITY_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
          String result= convertOTHER_ACTIVITY_EOD_STATUS_ERRORListToString(eodList,session);
         
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
    
    public String convertOTHER_ACTIVITY_EOD_STATUS_ERRORListToString(ArrayList<OTHER_ACTIVITY_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                OTHER_ACTIVITY_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
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
    
    
      public String getASSIGNMENT_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getASSIGNMENT_EOD_STATUS_ERROR_DataSet");
          
          
          
          ASSIGNMENT_EOD_STATUS_ERROR_DATASET batch=inject.getAssignmentEodErrorDataset();
          
          
          ArrayList<ASSIGNMENT_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
          String result= convertASSIGNMENT_EOD_STATUS_ERRORListToString(eodList,session);
         
          dbg("end of getASSIGNMENT_EOD_STATUS_ERROR_DataSet--->result-->"+result);
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
    
    public String convertASSIGNMENT_EOD_STATUS_ERRORListToString(ArrayList<ASSIGNMENT_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ASSIGNMENT_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
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
  
    
      public String getE_CIRCULAR_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getE_CIRCULAR_EOD_STATUS_DataSet");
          
          
          
          E_CIRCULAR_EOD_STATUS_DATASET batch=inject.geteCircularEodDataSet();
          
          
          
         ArrayList<E_CIRCULAR_EOD_STATUS>eodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         String result= convertE_CIRCULAR_EOD_STATUSListToString(eodList,session);
         dbg("end of getE_CIRCULAR_EOD_STATUS_DataSet--->result-->"+result);
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
    
    
    public String convertE_CIRCULAR_EOD_STATUSListToString(ArrayList<E_CIRCULAR_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                E_CIRCULAR_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSTART_TIME()+"~"+
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

    
    
    
    

    
//    public String getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet");
//          
//          
//          
//          OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET batch=inject.getOtherActivityBatchErrorDataSet();
//          
//          
//          dbg("end of getOTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet");
//          ArrayList<OTHER_ACTIVITY_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
//          
//          String result= convertOTHER_ACTIVITY_EOD_STATUS_ERRORListToString(eodList,session);
//         
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
//    public String convertOTHER_ACTIVITY_EOD_STATUS_ERRORListToString(ArrayList<OTHER_ACTIVITY_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
//       String result=new String();
//        
//        
//        try{
//            
//            for(int i=0;i<appEodList.size();i++){
//                OTHER_ACTIVITY_EOD_STATUS_ERROR appEod=appEodList.get(i);
//                
//                String record=appEod.getACTIVITY_ID()+"~"+
//                              appEod.getBUSINESS_DATE()+"~"+
//                              appEod.getERROR()+"~"+
//                              appEod.getINSTITUTE_ID();
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
    
    
      public String getE_CIRCULAR_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getE_CIRCULAR_EOD_STATUS_ERROR_DataSet");
          
          
          
          E_CIRCULAR_EOD_STATUS_ERROR_DATASET batch=inject.geteCircularEodErrorDataset();
          
          
          ArrayList<E_CIRCULAR_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
          String result= convertE_CIRCULAR_EOD_STATUS_ERRORListToString(eodList,session);
         
          dbg("end of getE_CIRCULAR_EOD_STATUS_ERROR_DataSet--->result-->"+result);
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
    
    public String convertE_CIRCULAR_EOD_STATUS_ERRORListToString(ArrayList<E_CIRCULAR_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                E_CIRCULAR_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
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
  
    
    
    
    
    
    
    
    public String getOTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getOTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet");
          
          
          
         OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET batch=inject.getOtherActivityBatchHistoryDataSet();
          
          
         dbg("end of getOTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet");
         ArrayList<OTHER_ACTIVITY_EOD_STATUS_HISTORY>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
         String result=convertOTHER_ACTIVITY_EOD_STATUS_HISTORYListToString(eodList,session);
         
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
	
    
    public String convertOTHER_ACTIVITY_EOD_STATUS_HISTORYListToString(ArrayList<OTHER_ACTIVITY_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                OTHER_ACTIVITY_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
       public String getASSIGNMENT_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getASSIGNMENT_EOD_STATUS_HISTORY_DataSet");
          
          
          
         ASSIGNMENT_EOD_STATUS_HISTORY_DATASET batch=inject.getAssignmentEodHistoryDataset();
          
          
         ArrayList<ASSIGNMENT_EOD_STATUS_HISTORY>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
         String result=convertASSIGNMENT_EOD_STATUS_HISTORYListToString(eodList,session);
         dbg("end of getASSIGNMENT_EOD_STATUS_HISTORY_DataSet--->result-->"+result);
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
	
    
    public String convertASSIGNMENT_EOD_STATUS_HISTORYListToString(ArrayList<ASSIGNMENT_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ASSIGNMENT_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
 
    
    
    public String getE_CIRCULAR_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getE_CIRCULAR_EOD_STATUS_HISTORY_DataSet");
          
          
          
         E_CIRCULAR_EOD_STATUS_HISTORY_DATASET batch=inject.geteCircularEodHistoryDataset();
          
          
         ArrayList<E_CIRCULAR_EOD_STATUS_HISTORY>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
         String result=convertE_CIRCULAR_EOD_STATUS_HISTORYListToString(eodList,session);
         dbg("end of getE_CIRCULAR_EOD_STATUS_HISTORY_DataSet--->result-->"+result);
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
	
    
    public String convertE_CIRCULAR_EOD_STATUS_HISTORYListToString(ArrayList<E_CIRCULAR_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                E_CIRCULAR_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
    
    
    
    
    
    
    
    public String getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_DataSet");
          
          
          
          STUDENT_OTHER_ACTIVITY_EOD_STATUS_DATASET batch=inject.getStudentOtherActivityBatchDataSet();
          
          
          dbg("end of getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_DataSet");
         ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS> eodList= batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_OTHER_ACTIVITY_EOD_STATUSListToStringListToString(eodList,session);
         
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
    
    
    
    public String convertSTUDENT_OTHER_ACTIVITY_EOD_STATUSListToStringListToString(ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_OTHER_ACTIVITY_EOD_STATUS  appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
       public String getSTUDENT_ASSIGNMENT_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ASSIGNMENT_EOD_STATUS_DataSet");
          
          
          
          STUDENT_ASSIGNMENT_EOD_STATUS_DATASET batch=inject.getStudentAssignmentEodDatset();
          
          
         ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS> eodList= batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_ASSIGNMENT_EOD_STATUSListToStringListToString(eodList,session);
         
         dbg("end of getSTUDENT_ASSIGNMENT_EOD_STATUS_DataSet-->result-->"+result);

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
    
    
    
    public String convertSTUDENT_ASSIGNMENT_EOD_STATUSListToStringListToString(ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_ASSIGNMENT_EOD_STATUS  appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
     public String getSTUDENT_E_CIRCULAR_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_E_CIRCULAR_EOD_STATUS_DataSet");
          
          
          
          STUDENT_E_CIRCULAR_EOD_STATUS_DATASET batch=inject.getStudentECircularEodDatset();
          
          
         ArrayList<STUDENT_E_CIRCULAR_EOD_STATUS> eodList= batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_E_CIRCULAR_EOD_STATUSListToStringListToString(eodList,session);
         
         dbg("end of getSTUDENT_E_CIRCULAR_EOD_STATUS_DataSet-->result-->"+result);

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
    
    
    
    public String convertSTUDENT_E_CIRCULAR_EOD_STATUSListToStringListToString(ArrayList<STUDENT_E_CIRCULAR_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_E_CIRCULAR_EOD_STATUS  appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
    public String getTEACHER_E_CIRCULAR_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTEACHER_E_CIRCULAR_EOD_STATUS_DataSet");
          
          
          
          TEACHER_E_CIRCULAR_EOD_STATUS_DATASET batch=inject.getTeacherECircularEodDatset();
          
          
         ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS> eodList= batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertTEACHER_E_CIRCULAR_EOD_STATUSListToStringListToString(eodList,session);
         
         dbg("end of getTEACHER_E_CIRCULAR_EOD_STATUS_DataSet-->result-->"+result);

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
    
    
    
    public String convertTEACHER_E_CIRCULAR_EOD_STATUSListToStringListToString(ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TEACHER_E_CIRCULAR_EOD_STATUS  appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
                              appEod.getSTATUS()+"~"+
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
    
     
    public String getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DATASET batch=inject.getStudentOtherActivityBatchErrorDataSet();
          
          
          dbg("end of getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet");
         ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERRORListToStringListToString(eodList,session);
         
         
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
    
    
    public String convertSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERRORListToStringListToString(ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR  appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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
    public String getSTUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DATASET batch=inject.getStudentAssignmentErrorDataset();
          
          
         ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_ASSIGNMENT_EOD_STATUS_ERRORListToStringListToString(eodList,session);
         dbg("end of getSTUDENT_ASSIGNMENT_EOD_STATUS_ERROR_DataSet-->result-->"+result);
         
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
    
    
    public String convertSTUDENT_ASSIGNMENT_EOD_STATUS_ERRORListToStringListToString(ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_ASSIGNMENT_EOD_STATUS_ERROR  appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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
    
    
    
    public String getSTUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DATASET batch=inject.getStudentECircularErrorDataset();
          
          
         ArrayList<STUDENT_E_CIRCULAR_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_E_CIRCULAR_EOD_STATUS_ERRORListToStringListToString(eodList,session);
         dbg("end of getSTUDENT_E_CIRCULAR_EOD_STATUS_ERROR_DataSet-->result-->"+result);
         
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
    
    
    public String convertSTUDENT_E_CIRCULAR_EOD_STATUS_ERRORListToStringListToString(ArrayList<STUDENT_E_CIRCULAR_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_E_CIRCULAR_EOD_STATUS_ERROR  appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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
    
    
    public String getTEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DataSet");
          
          
          
          TEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DATASET batch=inject.getTeacherECircularErrorDataset();
          
          
         ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_ERROR>eodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertTEACHER_E_CIRCULAR_EOD_STATUS_ERRORListToStringListToString(eodList,session);
         dbg("end of getTEACHER_E_CIRCULAR_EOD_STATUS_ERROR_DataSet-->result-->"+result);
         
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
    
    
    public String convertTEACHER_E_CIRCULAR_EOD_STATUS_ERRORListToStringListToString(ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TEACHER_E_CIRCULAR_EOD_STATUS_ERROR  appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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
    
    
    
    
    public String getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY_DATASET batch=inject.getStudentOtherActivityBAtchhistoryDataSet();
          
          
          dbg("end of getSTUDENT_OTHER_ACTIVITY_EOD_STATUS_ERROR_DataSet");
          ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
          
          String result=convertSTUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORYListToStringListToString(eodList,session);
       
                  
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
    
    
    
    public String convertSTUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORYListToStringListToString(ArrayList<STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_OTHER_ACTIVITY_EOD_STATUS_HISTORY  appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+ 
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
      
    public String getSTUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DATASET batch=inject.getStudentAssignmentHistoryDataSet();
          
          
          ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
          
          String result=convertSTUDENT_ASSIGNMENT_EOD_STATUS_HISTORYListToStringListToString(eodList,session);
       
                  dbg("end of getSTUDENT_ASSIGNMENT_EOD_STATUS_HISTORY_DataSet-->result-->"+result);
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
    
    
    
    public String convertSTUDENT_ASSIGNMENT_EOD_STATUS_HISTORYListToStringListToString(ArrayList<STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_ASSIGNMENT_EOD_STATUS_HISTORY  appEod=appEodList.get(i);
                
                String record=appEod.getASSIGNMENT_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+ 
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
    
    
    public String getSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET batch=inject.getStudentECircularHistoryDataSet();
          
          
          ArrayList<STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
          
          String result=convertSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORYListToStringListToString(eodList,session);
       
                  dbg("end of getSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet-->result-->"+result);
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
    
    
    
    public String convertSTUDENT_E_CIRCULAR_EOD_STATUS_HISTORYListToStringListToString(ArrayList<STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_E_CIRCULAR_EOD_STATUS_HISTORY  appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+ 
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
    
    
    
    public String getTEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet");
          
          
          
          TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DATASET batch=inject.getTeacherECircularHistoryDataSet();
          
          
          ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY>eodList=  batch.getTableObject(p_businessDate, p_instituteID,session, dbSession, inject);
          
          
          String result=convertTEACHER_E_CIRCULAR_EOD_STATUS_HISTORYListToStringListToString(eodList,session);
       
                  dbg("end of getTEACHER_E_CIRCULAR_EOD_STATUS_HISTORY_DataSet-->result-->"+result);
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
    
    
    
    public String convertTEACHER_E_CIRCULAR_EOD_STATUS_HISTORYListToStringListToString(ArrayList<TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                TEACHER_E_CIRCULAR_EOD_STATUS_HISTORY  appEod=appEodList.get(i);
                
                String record=appEod.getE_CIRCULAR_ID()+"~"+
                              appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+ 
                              appEod.getERROR()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
                              appEod.getSTATUS()+"~"+
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
    
    
    
    
    
    
    
    
    public String getFEE_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFEE_EOD_STATUS_DataSet");
          
          
          
          FEE_EOD_STATUS_DATASET batch=inject.getFeeBatchDataSet();
          
          
          dbg("end of getFEE_EOD_STATUS_DataSet");
         ArrayList<FEE_EOD_STATUS>feeEodDataSet=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertFEE_EOD_STATUSListToString(feeEodDataSet,session);
         
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
    
    
    public String convertFEE_EOD_STATUSListToString(ArrayList<FEE_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FEE_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
     public String getFEE_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFEE_NOTIFICATION_EOD_STATUS_DataSet");
          
          
          
          FEE_NOTIFICATION_EOD_STATUS_DATASET batch=inject.getFeeNotificationEodStatusDataset();
          
          
          dbg("end of getFEE_EOD_STATUS_DataSet");
         ArrayList<FEE_NOTIFICATION_EOD_STATUS>feeEodDataSet=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertFEE_NOTIFICATION_EOD_STATUSListToString(feeEodDataSet,session);
         
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
    
    
    public String convertFEE_NOTIFICATION_EOD_STATUSListToString(ArrayList<FEE_NOTIFICATION_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FEE_NOTIFICATION_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
    public String getFEE_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFEE_EOD_STATUS_ERROR_DataSet");
          
          
          
          FEE_EOD_STATUS_ERROR_DATASET batch=inject.getFeeBatchErrorDataSet();
          
          
          dbg("end of getFEE_EOD_STATUS_ERROR_DataSet");
         ArrayList<FEE_EOD_STATUS_ERROR>feeEodDataSet=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertFEE_EOD_STATUS_ERRORListToStringListToString(feeEodDataSet,session);
         
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
    
    
    public String convertFEE_EOD_STATUS_ERRORListToStringListToString(ArrayList<FEE_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FEE_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
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
    
    public String getFEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
          
          
          
          FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET batch=inject.getFeeNotificationEodStatusErrorDataset();
          
          
          dbg("end of getFEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
         ArrayList<FEE_NOTIFICATION_EOD_STATUS_ERROR>feeEodDataSet=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertFEE_NOTIFICATION_EOD_STATUS_ERRORListToStringListToString(feeEodDataSet,session);
         
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
    
    
    public String convertFEE_NOTIFICATION_EOD_STATUS_ERRORListToStringListToString(ArrayList<FEE_NOTIFICATION_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FEE_NOTIFICATION_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
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
    
    
    public String getFEE_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFEE_EOD_STATUS_HISTORY_DataSet");
          
          
          
          FEE_EOD_STATUS_HISTORY_DATASET batch=inject.getFeeBatchHistoryDataSet();
          
          
          dbg("end of getFEE_EOD_STATUS_HISTORY_DataSet");
         ArrayList<FEE_EOD_STATUS_HISTORY> feeHistoryList= batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         String result=convertFEE_EOD_STATUS_HISTORYListToString(feeHistoryList,session);
         
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
    
    
    public String convertFEE_EOD_STATUS_HISTORYListToString(ArrayList<FEE_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FEE_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
      public String getFEE_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFEE_EOD_STATUS_HISTORY_DataSet");
          
          
          
          FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET batch=inject.getFeeNotificationEodStatusHistoryDataset();
          
          
          dbg("end of getFEE_NOTIFICATION_EOD_STATUS_HISTORY_DataSet");
         ArrayList<FEE_NOTIFICATION_EOD_STATUS_HISTORY> feeHistoryList= batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         String result=convertFEE_NOTIFICATION_EOD_STATUS_HISTORYListToString(feeHistoryList,session);
         
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
    
    
    public String convertFEE_NOTIFICATION_EOD_STATUS_HISTORYListToString(ArrayList<FEE_NOTIFICATION_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FEE_NOTIFICATION_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getGROUP_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
  
    
    
    
	
    public String getSTUDENT_FEE_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_FEE_EOD_STATUS_DataSet");
          
          
          
          STUDENT_FEE_EOD_STATUS_DATASET batch=inject.getStudentFeeBatchDataSet();
          
          
          dbg("end of getSTUDENT_FEE_EOD_STATUS_DataSet");
         ArrayList<STUDENT_FEE_EOD_STATUS>studentFeeEodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_FEE_EOD_STATUSListToString(studentFeeEodList,session);
         
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
     
    
    
    public String convertSTUDENT_FEE_EOD_STATUSListToString(ArrayList<STUDENT_FEE_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_FEE_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
     public String getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_DataSet");
          
          
          
          STUDENT_FEE_NOTIFICATION_EOD_STATUS_DATASET batch=inject.getStudentFeeNotificationEodStatus();
          
          
          dbg("end of getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_DataSet");
         ArrayList<STUDENT_FEE_NOTIFICATION_EOD_STATUS>studentFeeEodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_FEE_NOTIFICATION_EOD_STATUSListToString(studentFeeEodList,session);
         
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
     
    
    
    public String convertSTUDENT_FEE_NOTIFICATION_EOD_STATUSListToString(ArrayList<STUDENT_FEE_NOTIFICATION_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_FEE_NOTIFICATION_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
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

    
    public String getSTUDENT_FEE_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_FEE_EOD_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_FEE_EOD_STATUS_ERROR_DATASET batch=inject.getStudentFeeBatchErrorDataSet();
          
          
          dbg("end of getSTUDENT_FEE_EOD_STATUS_ERROR_DataSet");
         ArrayList<STUDENT_FEE_EOD_STATUS_ERROR>studentFeeList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_FEE_EOD_STATUS_ERRORListToStringListToString(studentFeeList,session);
         
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
      
    
    public String convertSTUDENT_FEE_EOD_STATUS_ERRORListToStringListToString(ArrayList<STUDENT_FEE_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_FEE_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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
       public String getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DATASET batch=inject.getStudentFeeNotificationEodStatusError();
          
          
          dbg("end of getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
         ArrayList<STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR>studentFeeList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_FEE_NOTIFICATION_EOD_STATUS_ERRORListToStringListToString(studentFeeList,session);
         
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
      
    
    public String convertSTUDENT_FEE_NOTIFICATION_EOD_STATUS_ERRORListToStringListToString(ArrayList<STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_FEE_NOTIFICATION_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
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
 
    
    public String getSTUDENT_FEE_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_FEE_EOD_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_FEE_EOD_STATUS_HISTORY_DATASET batch=inject.getStudentFeeBatchHistoryDataSet();
          
          
          dbg("end of getSTUDENT_FEE_EOD_STATUS_ERROR_DataSet");
         ArrayList<STUDENT_FEE_EOD_STATUS_HISTORY>studentFeeList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         String result=convertSTUDENT_FEE_EOD_STATUS_HISTORYListToStringListToString(studentFeeList,session);
         
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
    
    
   public String convertSTUDENT_FEE_EOD_STATUS_HISTORYListToStringListToString(ArrayList<STUDENT_FEE_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_FEE_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
    
      public String getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DATASET batch=inject.getStudentFeeNotificationEodStatusHistory();
          
          
          dbg("end of getSTUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY_DataSet");
         ArrayList<STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY>studentFeeList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         String result=convertSTUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORYListToStringListToString(studentFeeList,session);
         
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
    
    
   public String convertSTUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORYListToStringListToString(ArrayList<STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_FEE_NOTIFICATION_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getFEE_ID()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
 
    
//    public ArrayList<NOTIFICATION_EOD_STATUS>getNOTIFICATION_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getNOTIFICATION_EOD_STATUS_DataSet");
//          
//          
//          
//          NOTIFICATION_EOD_STATUS_DATASET batch=inject.getNotificationBatchDataSet();
//          
//          
//          dbg("end of getNOTIFICATION_EOD_STATUS_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
     public String getNOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getNOTIFICATION_EOD_STATUS_DataSet");
          
          
          
          NOTIFICATION_EOD_STATUS_DATASET appEod=inject.getNotificationBatchDataSet();
          
          
          dbg("end of getNOTIFICATION_EOD_STATUS_DataSet");
          ArrayList<NOTIFICATION_EOD_STATUS>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertNOTIFICATION_EOD_STATUSListToString(instituteMaster,session);
          
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

    public String convertNOTIFICATION_EOD_STATUSListToString(ArrayList<NOTIFICATION_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                NOTIFICATION_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getERROR()+"~"+appEod.getGROUP_ID()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNOTIFICATION_ID()+"~"+appEod.getNO_FAILURES()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getSTART_TIME()+"~"+appEod.getSTATUS();
                
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
    
    
    
//    public ArrayList<NOTIFICATION_EOD_STATUS_ERROR>getNOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getNOTIFICATION_EOD_STATUS_ERROR_DataSet");
//          
//          
//          
//          NOTIFICATION_EOD_STATUS_ERROR_DATASET batch=inject.getNotificationBatcherrorDataSet();
//          
//          
//          dbg("end of geNOTIFICATION_EOD_STATUS_ERROR_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    
     public String getNOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getNOTIFICATION_EOD_STATUS_ERROR_DataSet");
          
          
          
          NOTIFICATION_EOD_STATUS_ERROR_DATASET appEod=inject.getNotificationBatcherrorDataSet();
          
          
          dbg("end of getNOTIFICATION_EOD_STATUS_ERROR_DataSet");
          ArrayList<NOTIFICATION_EOD_STATUS_ERROR>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertNOTIFICATION_EOD_STATUS_ERRORListToString(instituteMaster,session);
          
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

    public String convertNOTIFICATION_EOD_STATUS_ERRORListToString(ArrayList<NOTIFICATION_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                NOTIFICATION_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNOTIFICATION_ID();
                
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
    
//    public ArrayList<NOTIFICATION_EOD_STATUS_HISTORY>getNOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getNOTIFICATION_EOD_STATUS_HISTORY_DataSet");
//          
//          
//          
//          NOTIFICATION_EOD_STATUS_HISTORY_DATASET batch=inject.getNotificationBatchHistoryDataSet();
//          
//          
//          dbg("end of geNOTIFICATION_EOD_STATUS_HISTORY_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
	
     public String getNOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getNOTIFICATION_EOD_STATUS_HISTORY_DataSet");
          
          
          
          NOTIFICATION_EOD_STATUS_HISTORY_DATASET appEod=inject.getNotificationBatchHistoryDataSet();
          
          
          dbg("end of getNOTIFICATION_EOD_STATUS_HISTORY_DataSet");
          ArrayList<NOTIFICATION_EOD_STATUS_HISTORY>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertNOTIFICATION_EOD_STATUS_HISTORYListToString(instituteMaster,session);
          
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

    public String convertNOTIFICATION_EOD_STATUS_HISTORYListToString(ArrayList<NOTIFICATION_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                NOTIFICATION_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getERROR()+"~"+appEod.getGROUP_ID()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNOTIFICATION_ID()+"~"+appEod.getNO_FAILURES()+"~"+appEod.getNO_OF_SUCCESS()+"~"+appEod.getSEQUENCE_NO()+"~"+appEod.getSTART_TIME()+"~"+appEod.getSTATUS();
                
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
    
    
//    public ArrayList<STUDENT_NOTIFICATION_EOD_STATUS>getSTUDENT_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getSTUDENT_NOTIFICATION_EOD_STATUS_DataSet");
//          
//          
//          
//          STUDENT_NOTIFICATION_EOD_STATUS_DATASET batch=inject.getSttudentNotificationBatchDataSet();
//          
//          
//          dbg("end of geSTUDENT_NOTIFICATION_EOD_STATUS_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
      public String getSTUDENT_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_NOTIFICATION_EOD_STATUS_DataSet");
          
          
          
          STUDENT_NOTIFICATION_EOD_STATUS_DATASET appEod=inject.getSttudentNotificationBatchDataSet();
          
          
          dbg("end of getSTUDENT_NOTIFICATION_EOD_STATUS_DataSet");
          ArrayList<STUDENT_NOTIFICATION_EOD_STATUS>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertSTUDENT_NOTIFICATION_EOD_STATUSListToString(instituteMaster,session);
          
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

    public String convertSTUDENT_NOTIFICATION_EOD_STATUSListToString(ArrayList<STUDENT_NOTIFICATION_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_NOTIFICATION_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNOTIFICATION_ID()+"~"+appEod.getSTART_TIME()+"~"+appEod.getSTATUS()+"~"+appEod.getSTUDENT_ID();
                
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
//    public ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_ERROR>getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
//          
//          
//          
//          STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET batch=inject.getStudentNotificationBatchErrorDataSet();
//          
//          
//          dbg("end of geSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
      
    
     public String getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET appEod=inject.getStudentNotificationBatchErrorDataSet();
          
          
          dbg("end of getSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
          ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_ERROR>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertSTUDENT_NOTIFICATION_EOD_STATUS_ERRORListToString(instituteMaster,session);
          
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

    public String convertSTUDENT_NOTIFICATION_EOD_STATUS_ERRORListToString(ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_NOTIFICATION_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNOTIFICATION_ID()+"~"+appEod.getSTUDENT_ID();
                
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
    
//    public ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_HISTORY>getSTUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getSTUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet");
//          
//          
//          
//          STUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET batch=inject.getStudentNotificationBatchHistoryDataSet();
//          
//          
//          dbg("end of geSTUDENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
//         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
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
    
    
     public String getSTUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET appEod=inject.getStudentNotificationBatchHistoryDataSet();
          
          
          dbg("end of getSTUDENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet");
          ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_HISTORY>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertSTUDENT_NOTIFICATION_EOD_STATUS_HISTORYListToString(instituteMaster,session);
          
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

    public String convertSTUDENT_NOTIFICATION_EOD_STATUS_HISTORYListToString(ArrayList<STUDENT_NOTIFICATION_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_NOTIFICATION_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEND_TIME()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNOTIFICATION_ID()+"~"+appEod.getSEQUENCE_NO()+"~"+appEod.getSTART_TIME()+"~"+appEod.getSTATUS()+"~"+appEod.getSTUDENT_ID();
                
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
    
    
     public String getSTUDENT_NOTIFICATION_EMAIL_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_NOTIFICATION_EMAIL_ERROR_DataSet");
          
          
          
          STUDENT_NOTIFICATION_EMAIL_ERROR_DATASET appEod=inject.getStudentNotificationEmailDataSet();
          
          
          dbg("end of getSTUDENT_NOTIFICATION_EMAIL_ERROR_DataSet");
          ArrayList<STUDENT_NOTIFICATION_EMAIL_ERROR>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertSTUDENT_NOTIFICATION_EMAIL_ERRORListToString(instituteMaster,session);
          
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

    public String convertSTUDENT_NOTIFICATION_EMAIL_ERRORListToString(ArrayList<STUDENT_NOTIFICATION_EMAIL_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_NOTIFICATION_EMAIL_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getEMAIL_ID()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getNOTIFICATION_ID()+"~"+appEod.getSTUDENT_ID();
                
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
    
     public String getSTUDENT_NOTIFICATION_SMS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_NOTIFICATION_SMS_ERROR_DataSet");
          
          
          
          STUDENT_NOTIFICATION_SMS_ERROR_DATASET appEod=inject.getStudentNotificationSmsDataSet();
          
          
          dbg("end of getSTUDENT_NOTIFICATION_SMS_ERROR_DataSet");
          ArrayList<STUDENT_NOTIFICATION_SMS_ERROR>instituteMaster  = appEod.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
          String result=  convertSTUDENT_NOTIFICATION_SMS_ERRORListToString(instituteMaster,session);
          
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

    public String convertSTUDENT_NOTIFICATION_SMS_ERRORListToString(ArrayList<STUDENT_NOTIFICATION_SMS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_NOTIFICATION_SMS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+appEod.getERROR()+"~"+appEod.getINSTITUTE_ID()+"~"+appEod.getMOBILE_NO()+"~"+appEod.getNOTIFICATION_ID()+"~"+appEod.getSTUDENT_ID();
                
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
    
    public ArrayList<TIMETABLE_BATCH_STATUS>getTIMETABLE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTIMETABLE_BATCH_STATUS_DataSet");
          
          
          
          TIMETABLE_BATCH_STATUS_DATASET batch=inject.getTimeTableBatchDataSet();
          
          
          dbg("end of getTIMETABLE_BATCH_STATUS_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<TIMETABLE_BATCH_STATUS_ERROR>getTIMETABLE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTIMETABLE_BATCH_STATUS_ERROR_DataSet");
          
          
          
          TIMETABLE_BATCH_STATUS_ERROR_DATASET batch=inject.getTimeTableBatchErrorDataSet();
          
          
          dbg("end of getTIMETABLE_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<TIMETABLE_BATCH_STATUS_HISTORY>getTIMETABLE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getTIMETABLE_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          TIMETABLE_BATCH_STATUS_HISTORY_DATASET batch=inject.getTimeTableBatchHistoryDataSet();
          
          
          dbg("end of getTIMETABLE_BATCH_STATUS_HISTORY_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
	
    public ArrayList<STUDENT_TIMETABLE_BATCH_STATUS>getSTUDENT_TIMETABLE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_TIMETABLE_BATCH_STATUS_DataSet");
          
          
          
          STUDENT_TIMETABLE_BATCH_STATUS_DATASET batch=inject.getStudentTimeTableBatchDataSet();
          
          
          dbg("end of getSTUDENT_TIMETABLE_BATCH_STATUS_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
     
    public ArrayList<STUDENT_TIMETABLE_BATCH_STATUS_ERROR>getSTUDENT_TIMETABLE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_TIMETABLE_BATCH_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_TIMETABLE_BATCH_STATUS_ERROR_DATASET batch=inject.getStudentTimeTableBatchErrorDataSet();
          
          
          dbg("end of getSTUDENT_TIMETABLE_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
      
    public ArrayList<STUDENT_TIMETABLE_BATCH_STATUS_HISTORY>getSTUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_TIMETABLE_BATCH_STATUS_HISTORY_DATASET batch=inject.getStudentTimeTableBatchHistoryDataSet();
          
          
          dbg("end of getSTUDENT_TIMETABLE_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<ATTENDANCE_BATCH_STATUS>getATTENDANCE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getATTENDANCE_BATCH_STATUS_DataSet");
          
          
          
          ATTENDANCE_BATCH_STATUS_DATASET batch=inject.getAttendanceBatchDataSet();
          
          
          dbg("end of getATTENDANCE_BATCH_STATUS_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<ATTENDANCE_BATCH_STATUS_ERROR>getATTENDANCE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getATTENDANCE_BATCH_STATUS_ERROR_DataSet");
          
          
          
          ATTENDANCE_BATCH_STATUS_ERROR_DATASET batch=inject.getAttendanceBatchErrorDataSet();
          
          
          dbg("end of getATTENDANCE_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<ATTENDANCE_BATCH_STATUS_HISTORY>getATTENDANCE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getATTENDANCE_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          ATTENDANCE_BATCH_STATUS_HISTORY_DATASET batch=inject.getAttendanceBatchStatusHistoryDataSet();
          
          
          dbg("end of getATTENDANCE_BATCH_STATUS_HISTORY_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
	
    public ArrayList<STUDENT_ATTENDANCE_BATCH_STATUS>getSTUDENT_ATTENDANCE_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ATTENDANCE_BATCH_STATUS_DataSet");
          
          
          
          STUDENT_ATTENDANCE_BATCH_STATUS_DATASET batch=inject.getStudentAttBatchDataSet();
          
          
          dbg("end of getSTUDENT_ATTENDANCE_BATCH_STATUS_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
     
    public ArrayList<STUDENT_ATTENDANCE_BATCH_STATUS_ERROR>getSTUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DATASET batch=inject.getStudentAttendanceBatchErrorDataSet();
          
          
          dbg("end of getSTUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
      
    public ArrayList<STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY>getSTUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_ATTENDANCE_BATCH_STATUS_HISTORY_DATASET batch=inject.getStudentAttendanceBatchHistoryDataSet();
          
          
          dbg("end of getSTUDENT_ATTENDANCE_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<EXAM_BATCH_STATUS>getEXAM_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getEXAM_BATCH_STATUS_DataSet");
          
          
          
          EXAM_BATCH_STATUS_DATASET batch=inject.getExamBatchDataSet();
          
          
          dbg("end of getEXAM_BATCH_STATUS_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<EXAM_BATCH_STATUS_ERROR>getEXAM_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getEXAM_BATCH_STATUS_ERROR_DataSet");
          
          
          
          EXAM_BATCH_STATUS_ERROR_DATASET batch=inject.getExamBatchErrorDataSet();
          
          
          dbg("end of getEXAM_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<EXAM_BATCH_STATUS_HISTORY>getEXAM_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getEXAM_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          EXAM_BATCH_STATUS_HISTORY_DATASET batch=inject.getExamBatchHistoryDataSet();
          
          
          dbg("end of getEXAM_BATCH_STATUS_HISTORY_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
	
    public ArrayList<STUDENT_EXAM_BATCH_STATUS>getSTUDENT_EXAM_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_EXAM_BATCH_STATUS_DataSet");
          
          
          
          STUDENT_EXAM_BATCH_STATUS_DATASET batch=inject.getStudentExamBatchDataSet();
          
          
          dbg("end of getSTUDENT_EXAM_BATCH_STATUS_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
     
    public ArrayList<STUDENT_EXAM_BATCH_STATUS_ERROR>getSTUDENT_EXAM_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_EXAM_BATCH_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_EXAM_BATCH_STATUS_ERROR_DATASET batch=inject.getStudentExamBatchErrorDataSet();
          
          
          dbg("end of getSTUDENT_EXAM_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
      
    public ArrayList<STUDENT_EXAM_BATCH_STATUS_HISTORY>getSTUDENT_EXAM_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_EXAM_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_EXAM_BATCH_STATUS_HISTORY_DATASET batch=inject.getStudentExamBatchHsitoryDataSet();
          
          
          dbg("end of getSTUDENT_EXAM_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    
    public String getMARK_BATCH_STATUS_DataSet(String p_businessDate,String instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getMARK_BATCH_STATUS_DataSet");
          
          
          
          MARK_BATCH_STATUS_DATASET batch=inject.getMarkBatchDataSet();
          
          
          dbg("end of getMARK_BATCH_STATUS_DataSet");
         ArrayList<MARK_BATCH_STATUS>markBatchStatus=  batch.getTableObject(p_businessDate,instituteID, session, dbSession, inject);
          
         String result=convertMARK_BATCH_STATUSListToString(markBatchStatus,session);
         
         
         
         
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
    
    
    public String convertMARK_BATCH_STATUSListToString(ArrayList<MARK_BATCH_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                MARK_BATCH_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
    
    public String getMARK_BATCH_STATUS_ERROR_DataSet(String p_businessDate,String instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getMARK_BATCH_STATUS_ERROR_DataSet");
          
          
          
          MARK_BATCH_STATUS_ERROR_DATASET batch=inject.getMarkBatchErrorDataSet();
          
          
          dbg("end of getMARK_BATCH_STATUS_ERROR_DataSet");
         ArrayList<MARK_BATCH_STATUS_ERROR>batchError=  batch.getTableObject(p_businessDate,instituteID, session, dbSession, inject);
          
         
         String result=convertMARK_BATCH_STATUS_ERRORListToString(batchError,session);
         
         
         
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
    
    public String convertMARK_BATCH_STATUS_ERRORListToString(ArrayList<MARK_BATCH_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                MARK_BATCH_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getERROR()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD();
                              
                
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
    
    public String getMARK_BATCH_STATUS_HISTORY_DataSet(String p_businessDate,String instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getMARK_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          MARK_BATCH_STATUS_HISTORY_DATASET batch=inject.getMarkBatchHistoryDataSet();
          
          
          dbg("end of getMARK_BATCH_STATUS_HISTORY_DataSet");
         ArrayList<MARK_BATCH_STATUS_HISTORY>markHistory=  batch.getTableObject(p_businessDate,instituteID, session, dbSession, inject);
          
         String result=convertMARK_BATCH_STATUS_HISTORYListToString(markHistory,session);
         
         
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
	
    
    
    public String convertMARK_BATCH_STATUS_HISTORYListToString(ArrayList<MARK_BATCH_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                MARK_BATCH_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getEXAM()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getNO_FAILURES()+"~"+
                              appEod.getNO_OF_SUCCESS()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSEQUENCE_NO()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
	
    public ArrayList<STUDENT_MARK_BATCH_STATUS>getSTUDENT_MARK_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_MARK_BATCH_STATUS_DataSet");
          
          
          
          STUDENT_MARK_BATCH_STATUS_DATASET batch=inject.getStudentMarkBatchDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
     
    public ArrayList<STUDENT_MARK_BATCH_STATUS_ERROR>getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_MARK_BATCH_STATUS_ERROR_DATASET batch=inject.getStudentMarkBatchErrorDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
      
    public ArrayList<STUDENT_MARK_BATCH_STATUS_HISTORY>getSTUDENT_MARK_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_MARK_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_MARK_BATCH_STATUS_HISTORY_DATASET batch=inject.getStudentMarkBatchHistoryDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS>getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DataSet");
          
          
          
          STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET batch=inject.getStudentAssignmentArchDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY>getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET batch=inject.getStudentAssignmentArchHistoryDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR>getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET batch=inject.getStudentAssignmentArchErrorDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS>getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DataSet");
          
          
          
          INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_DATASET batch=inject.getInstituteAssignmentArchDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY>getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DataSet");
          
          
          
          INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_HISTORY_DATASET batch=inject.getInstituteAssignmentArchHistoryDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    public ArrayList<INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR>getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DataSet(String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DataSet");
          
          
          
          INSTITUTE_ASSIGNMENT_ARCH_BATCH_STATUS_ERROR_DATASET batch=inject.getInstituteAssignmentArchErrorDataSet();
          
          
          dbg("end of getSTUDENT_MARK_BATCH_STATUS_ERROR_DataSet");
         return  batch.getTableObject(p_businessDate, session, dbSession, inject);
          
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
    
    
        public String getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_DataSet");
          
          
          
          STUDENT_EVENT_NOTIFICATION_EOD_STATUS_DATASET batch=inject.getStudentEventNotificationEodStatus();
          
          
          
         ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS>studentFeeEodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_EVENT_NOTIFICATION_EOD_STATUSListToString(studentFeeEodList,session);
         dbg("end of getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_DataSet--result-->"+result);
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
     
    
    
    public String convertSTUDENT_EVENT_NOTIFICATION_EOD_STATUSListToString(ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_EVENT_NOTIFICATION_EOD_STATUS appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
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
    
    
   public String getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet");
          
          
          
          STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DATASET batch=inject.getStudentEventNotificationEodStatusHistory();
          
          
          
         ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY>studentFeeEodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORYListToString(studentFeeEodList,session);
         dbg("end of getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY_DataSet--result-->"+result);
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
     
    
    
    public String convertSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORYListToString(ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_EVENT_NOTIFICATION_EOD_STATUS_HISTORY appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getEND_TIME()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTART_TIME()+"~"+
                              appEod.getSTATUS()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSEQUENCE_NO();
                
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
    
    public String getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet(String p_businessDate,String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet");
          
          
          
          STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DATASET batch=inject.getStudentEventNotificationEodStatusError();
          
          
          
         ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR>studentFeeEodList=  batch.getTableObject(p_businessDate,p_instituteID, session, dbSession, inject);
          
         
         String result=convertSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERRORListToString(studentFeeEodList,session);
         dbg("end of getSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR_DataSet--result-->"+result);
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
     
    
    
    public String convertSTUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERRORListToString(ArrayList<STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                STUDENT_EVENT_NOTIFICATION_EOD_STATUS_ERROR appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_DATE()+"~"+
                              appEod.getINSTITUTE_ID()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getERROR();
                
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
