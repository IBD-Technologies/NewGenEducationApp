/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.institute.BusinessReportParams;
import com.ibd.cohesive.report.businessreport.dataModels.institute.FeeDetailBusiness;
import com.ibd.cohesive.report.businessreport.dataModels.institute.FeePaymentBusiness;
import com.ibd.cohesive.report.businessreport.dataModels.institute.FeeSummaryBusiness;
import com.ibd.cohesive.report.businessreport.dataModels.institute.MarkComparison;
import com.ibd.cohesive.report.businessreport.dataModels.institute.NotificationDetailBusiness;
import com.ibd.cohesive.report.businessreport.dataModels.institute.StudentDetails;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_MARK_REPORT;
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
@Remote(IInstituteDataSetBusiness.class)
@Stateless


public class InstituteDataSetBusiness implements IInstituteDataSetBusiness {
    ReportDependencyInjection inject;
    AppDependencyInjection appInject;
    CohesiveSession session;
    DBSession dbSession;
    
    public InstituteDataSetBusiness(){
        try {
            
            inject=new ReportDependencyInjection("INSTITUTE_BU");
            appInject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    
    
     public String MarkComparison_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside MarkComparison_DataSet");
          
          
          MarkComparison_DataSet classMarkSummary=inject.getMarkComparison();
          
          ArrayList<MarkComparison>MarkComparison_DataSet=  classMarkSummary.getMarkComparisonObject( p_instanceID,standard, session, dbSession, inject,appInject,userID);
         String result=this.convertMarkComparisonListToString(MarkComparison_DataSet, session);
         dbg("end of MarkComparison_DataSet-->result-->"+result);
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
    
    
    
     public String convertMarkComparisonListToString(ArrayList<MarkComparison>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                MarkComparison appEod=appEodList.get(i);
                
                String record=appEod.getAverageMark()+"~"+
                              appEod.getExam()+"~"+
                              appEod.getLowMark()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSubjectID()+"~"+
                              appEod.getTopMark()+"~"+
                              appEod.getGrade()+"~"+
                              appEod.getGradePercentage()+"~"+
                              appEod.getNo_of_Students()+"~"+
                              appEod.getOrderNo()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    public String CLASS_MARK_REPORT_DataSet(String p_instanceID,String standard,String userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside CLASS_MARK_REPORT_DataSet");
          
          
          CLASS_MARK_REPORT_DATASET classMarkSummary=inject.getClassMark();
          
          ArrayList<CLASS_MARK_REPORT>CLASS_MARK_REPORT_DataSet=  classMarkSummary.getTableObject(p_instanceID, p_instanceID, session, dbSession, inject, appInject, userID);
         String result=this.convertCLASS_MARK_REPORTListToString(CLASS_MARK_REPORT_DataSet, session);
         dbg("end of CLASS_MARK_REPORT_DataSet-->result-->"+result);
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
    
    
    
     public String convertCLASS_MARK_REPORTListToString(ArrayList<CLASS_MARK_REPORT>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                CLASS_MARK_REPORT appEod=appEodList.get(i);
                
                String record=appEod.getAveragreMark()+"~"+
                              appEod.getExam()+"~"+
                              appEod.getLowMark()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSubjectID()+"~"+
                              appEod.getTopMark()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
     public String FeeDetailBusinessDataSet(String standard,String section,String studentID,String feeID,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside FeeDetailBusinessDataSet");
          
          
          FeeDetailBusinessDataSet classMarkSummary=inject.getFeeDetailBusinessDataSet();
          
          ArrayList<FeeDetailBusiness>FeeDetailBusinessDataSet=  classMarkSummary.getFeeDetailBusiness(standard, section, studentID, feeID, p_instanceID, session, dbSession, inject, appInject);
         String result=this.convertFeeDetailBusinessListToString(FeeDetailBusinessDataSet, session);
         dbg("end of FeeDetailBusinessDataSet-->result-->"+result);
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
    
    
    
     public String convertFeeDetailBusinessListToString(ArrayList<FeeDetailBusiness>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FeeDetailBusiness appEod=appEodList.get(i);
                
                String record=appEod.getBalanceAmount()+"~"+
                              appEod.getDueDate()+"~"+
                              appEod.getFeeAmount()+"~"+
                              appEod.getFeeDescription()+"~"+
                              appEod.getFeeID()+"~"+
                              appEod.getFeeType()+"~"+
                              appEod.getPaidAmount()+"~"+
                              appEod.getStudentID()+"~"+
                              appEod.getStudentName()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getSerialNumber();
                              
                                                      
                              
                              
                        
                         
                
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
     public String getFeeSummaryBusinessDataSet(String p_standard,String p_section,String p_studentID,String p_feeID,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFeeSummaryBusinessDataSet");
          
          
          FeeSummaryBusinessDataSet FeeSummaryBusiness=inject.getFeeSummaryBusinessDataSet();
          
          ArrayList<FeeSummaryBusiness>FeeSummaryBusinessDataSet=  FeeSummaryBusiness.getFeeSummaryBusinessObject(p_standard,p_section,p_studentID,p_feeID, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertFeeSummaryBusinessListToString(FeeSummaryBusinessDataSet, session);
         dbg("end of getFeeSummaryBusinessDataSet--->result"+result);
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
     public String convertFeeSummaryBusinessListToString(ArrayList<FeeSummaryBusiness>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FeeSummaryBusiness appEod=appEodList.get(i);
                
                String record=appEod.getFeeType()+"~"+
                              appEod.getTotalBalanceAmount()+"~"+
                              appEod.getTotalFeeAmount()+"~"+
                              appEod.getTotalPaidAmount()+"~"+
                              appEod.getDueDate()+"~"+
                              appEod.getFeeDescription()+"~"+
                              appEod.getFeeID()+"~"+
                              appEod.getSerialNumber();
                                                         
                                                      
                        
                         
                
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
     
     
     public String getFeePaymentBusinessDataSet(String p_standard,String p_section,String studentID,String feeID,String fromDate,String toDate,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getFeePaymentBusinessDataSet");
          
          
          FeePaymentBusinessDataSet FeePaymentBusiness=inject.getFeePaymentBusinessDataSet();
          
          ArrayList<FeePaymentBusiness>FeePaymentBusinessDataSet=  FeePaymentBusiness.getFeePaymentBusiness(p_standard, p_section,studentID, feeID, fromDate, toDate, p_instanceID, session, dbSession, inject, appInject);
         String result=this.convertFeePaymentBusinessListToString(FeePaymentBusinessDataSet, session);
         dbg("end of getFeePaymentBusinessDataSet--->result"+result);
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
     public String convertFeePaymentBusinessListToString(ArrayList<FeePaymentBusiness>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                FeePaymentBusiness appEod=appEodList.get(i);
                
                String record=appEod.getFeeID()+"~"+
                              appEod.getPaymentAmount()+"~"+
                              appEod.getPaymentDate()+"~"+
                              appEod.getPaymentID()+"~"+
                              appEod.getStudentID()+"~"+
                              appEod.getStudentName()+"~"+
                              appEod.getDueDate()+"~"+
                              appEod.getFeeAmount()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getSerialNumber();
                                                         
                                                      
                        
                         
                
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
     
     public String NotificationDetailBusinessDataSet(String p_instanceID,String standard,String section,String studentID,String fromDate,String toDate,String notificationType)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside NotificationDetailBusinessDataSet");
          
          
          NotificationDetailBusinessDataSet classMarkSummary=inject.getNotificationDetailBusinessDataSet();
          
          ArrayList<NotificationDetailBusiness>NotificationDetailBusinessDataSet=  classMarkSummary.getNotificationDetailBusiness(standard, section, studentID, p_instanceID, notificationType, fromDate, toDate, session, dbSession, inject, appInject);
         String result=this.convertNotificationDetailBusinessListToString(NotificationDetailBusinessDataSet, session);
         dbg("end of NotificationDetailBusinessDataSet-->result-->"+result);
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
    
    
    
     public String convertNotificationDetailBusinessListToString(ArrayList<NotificationDetailBusiness>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                NotificationDetailBusiness appEod=appEodList.get(i);
                
                String record=appEod.getDate()+"~"+
                              appEod.getMessage()+"~"+
                              appEod.getNotificationType()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getStatus()+"~"+
                              appEod.getStudentID()+"~"+
                              appEod.getStudentName()+"~"+
                              appEod.getSerialNumber();
                              
                                                      
                              
                              
                        
                         
                
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
     
     public String StudentDetailsDataSet(String p_instanceID,String status,String standard,String section,String fromDate,String toDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside StudentDetailsDataSet");
          
          
          StudentDetailsDataSet classMarkSummary=inject.getStudentDetailsDataSet();
          
          ArrayList<StudentDetails>StudentDetailsDataSet=  classMarkSummary.getStudentDetails(standard,section,status, p_instanceID, session, dbSession, inject, appInject,fromDate,toDate);
          String result=this.convertStudentDetailsListToString(StudentDetailsDataSet, session);
          dbg("end of StudentDetailsDataSet-->result-->"+result);
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
    
    
    
     public String convertStudentDetailsListToString(ArrayList<StudentDetails>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                StudentDetails appEod=appEodList.get(i);
                
                String record=appEod.getContactNumber()+"~"+
                              appEod.getContactPersonName()+"~"+
                              appEod.getCreationDate()+"~"+
                              appEod.getDeletedDate()+"~"+
                              appEod.getEmailID()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getStudentID()+"~"+
                              appEod.getStudentName()+"~"+
                              appEod.getSerialNumber();
                              
                                                      
                              
                              
                        
                         
                
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
     
     public String BusinessReportParamsDataSet(String studentID,String standard,String section,String fromDate,String toDate,String feeID,String notificationType,String studentStatus,String instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside BusinessReportParamsDataSet");
          
          
          BusinessReportParamsDataSet classMarkSummary=inject.getBusinessReportParams();
          
          ArrayList<BusinessReportParams>BusinessReportParamsDataSet=  classMarkSummary.getBusinessReportParams_DataSet(studentID,standard,section, fromDate,toDate,feeID,notificationType,studentStatus,instituteID, session, dbSession, inject, appInject);
          String result=this.convertBusinessReportParamsListToString(BusinessReportParamsDataSet, session);
          dbg("end of BusinessReportParamsDataSet-->result-->"+result);
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
    
    
    
     public String convertBusinessReportParamsListToString(ArrayList<BusinessReportParams>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                BusinessReportParams appEod=appEodList.get(i);
                
                String record=appEod.getFeeID()+"~"+
                              appEod.getFromDate()+"~"+
                              appEod.getNotificationType()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getStudentID()+"~"+
                              appEod.getStudentName()+"~"+
                              appEod.getStudentStatus()+"~"+
                              appEod.getToDate();
                              
                                                      
                              
                              
                        
                         
                
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
