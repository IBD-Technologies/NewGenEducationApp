/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.report.businessreport.dataSets.institute.MarkComparison_DataSet;
import com.ibd.businessViews.IClassDataSetBusiness;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassAttendanceDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassAttendanceSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassDetails;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeAmountSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassMarksSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivityDetail;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivitySummary;
import com.ibd.cohesive.report.businessreport.dataModels.institute.MarkComparison;
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
@Remote(IClassDataSetBusiness.class)
@Stateless
public class ClassDataSetBusiness implements IClassDataSetBusiness {
    ReportDependencyInjection inject;
    AppDependencyInjection appInject;
    CohesiveSession session;
    DBSession dbSession;
    
    public ClassDataSetBusiness(){
        try {
            
            inject=new ReportDependencyInjection("CLASS_BU");
            appInject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
      public String getClassDetails_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
       
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getClassDetails_DataSet");
          
          
          ClassDetails_DataSet classOtherActivity=inject.getClassDetailsDataSet();
          
          
         
         ArrayList<ClassDetails>ClassDetails_DataSet=  classOtherActivity.getClassOtherActivity(p_standard, p_section, p_instanceID, session, dbSession, inject, appInject);
         String result=this.convertClassDetailsListToString(ClassDetails_DataSet, session);
         dbg("result"+result);
         dbg("end of geCLASS_ASSIGNMENT_DataSet");
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
    
      
      public String convertClassDetailsListToString(ArrayList<ClassDetails>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassDetails appEod=appEodList.get(i);
                
                String record=appEod.getClassTeacher()+"~"+
                              appEod.getClasss()+"~"+
                              appEod.getTotalNoOfStudents();
                        
                              
                
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
      

     public String getClassOtherActivitySummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
       
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getClassOtherActivitySummary_DataSet");
          
          
          ClassOtherActivitySummary_DataSet classOtherActivity=inject.getClassOtherActivitySummaryDataSet();
          
          
          dbg("end of geCLASS_ASSIGNMENT_DataSet");
         ArrayList<ClassOtherActivitySummary>ClassOtherActivitySummary_DataSet=  classOtherActivity.getClassOtherActivitySummaryObject(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassOtherActivitySummaryListToString(ClassOtherActivitySummary_DataSet, session);
         dbg("end of getClassOtherActivitySummary_DataSet--->result-->"+result);
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
    
   
    
     
    
    
    public String getClassAttendanceSummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          
          dbg("inside getClassAttendanceSummary_DataSet");
          
          ClassAttendanceSummary_DataSet classAttendance=inject.getClassAttendanceSummary();
          
          
         ArrayList<ClassAttendanceSummary>ClassAttendanceSummary_DataSet=  classAttendance.getClassAttendanceSummaryObject(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassAttendanceSummaryListToString(ClassAttendanceSummary_DataSet, session);
         dbg("result"+result);
         dbg("end of getClassAttendanceSummary_DataSet");
         
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
    
    
    
     
      public String ClassMarksSummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside ClassMarksSummary_DataSet");
          
          
          ClassMarksSummary_DataSet classMarkSummary=inject.getClassMarkSummary();
          
         ArrayList<ClassMarksSummary>ClassMarksSummary_DataSet=  classMarkSummary.getClassMarksSummaryObject(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassMarksSummaryListToString(ClassMarksSummary_DataSet, session);
         dbg("end of ClassMarksSummary_DataSet-->result-->"+result);
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
      
    
    
   

      public String getClassFeeAmountSummary_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getClassFeeAmountSummary_DataSet");
          
          
          ClassFeeAmountSummary_DataSet classFeeAmountSummary=inject.getClassFeeAmountSummary();
          
          ArrayList<ClassFeeAmountSummary>ClassFeeAmountSummary_DataSet=  classFeeAmountSummary.getClassFeeAmountSummaryObject(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassFeeAmountSummaryListToString(ClassFeeAmountSummary_DataSet, session);
         dbg("end of getClassFeeAmountSummary_DataSet--->result"+result);
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
    
      
      public String  getClassOtherActivityDetail_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getClassOtherActivityDetail_DataSet");
          
          ClassOtherActivityDetail_DataSet classOtherActivity=inject.getClassOtherActivityDetail();
          
          
          dbg("end of geCLASS_ASSIGNMENT_DataSet");
         ArrayList<ClassOtherActivityDetail>ClassOtherActivityDetail_DataSet=  classOtherActivity.getClassOtherActivity(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassOtherActivityDetailListToString(ClassOtherActivityDetail_DataSet, session);
         dbg("end of getClassOtherActivityDetail_DataSet-->"+result);
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
      
      
     public String getClassAttendanceDetail_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        
          boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          
          
          ClassAttendanceDetail_DataSet classAttendance=inject.getClassAttendanceBusinessDataSet();
          
          
          ArrayList<ClassAttendanceDetail>ClassAttendanceDetail_DataSet=  classAttendance.getClassAttendanceDetailObject(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassAttendanceDetailListToString(ClassAttendanceDetail_DataSet, session);
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
      
      
      public String  getClassMarksDetail(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          dbg("inside getClassMarksDetail");
          
          ClassMarksDetail_DataSet classMarkDetail=inject.getClassMarksDetail();
          
          
         ArrayList<ClassMarksDetail>ClassMarksDetail=  classMarkDetail.getClassMarksDetail(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassMarksDetailListToString(ClassMarksDetail, session);
         
         dbg("end of getClassMarksDetail-->result-->"+result);
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
     
     
       public String getClassFeeDetail_DataSet(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          
          
          ClassFeeDetail_DataSet classDetail=inject.getClassFeeDetail();
          
         ArrayList<ClassFeeDetail>ClassFeeDetail_DataSet=  classDetail.getClassOtherActivity(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
         String result=this.convertClassFeeDetailListToString(ClassFeeDetail_DataSet, session);
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
      
      
     public ArrayList<ClassOtherActivityDetail>getClassOtherActivityDetail_DataSetList(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
         boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          
          
          ClassOtherActivityDetail_DataSet classOtherActivity=inject.getClassOtherActivityDetail();
          
          
          dbg("end of geCLASS_ASSIGNMENT_DataSet");
         return  classOtherActivity.getClassOtherActivity(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
          
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
     
     
     
    
    public ArrayList<ClassOtherActivityDetail>getClassOtherActivityDetail_DataSetList(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getClassOtherActivityDetail_DataSetList(p_standard,p_section,p_instanceID);
            
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
      
      
     public ArrayList<ClassAttendanceDetail>getClassAttendanceDetail_DataSetList(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        
          boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          
          
          ClassAttendanceDetail_DataSet classAttendance=inject.getClassAttendanceBusinessDataSet();
          
          
         return  classAttendance.getClassAttendanceDetailObject(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
          
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
    
    public ArrayList<ClassAttendanceDetail>getClassAttendanceDetail_DataSetList(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getClassAttendanceDetail_DataSetList(p_standard,p_section,p_instanceID);
            
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
    
    
     public ArrayList<ClassMarksDetail>getClassMarksDetailList(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          
          
          ClassMarksDetail_DataSet classMarkDetail=inject.getClassMarksDetail();
          
          
         return  classMarkDetail.getClassMarksDetail(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
          
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
    
    public ArrayList<ClassMarksDetail>getClassMarksDetailList(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getClassMarksDetailList(p_standard,p_section,p_instanceID);
            
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
    
    
     public ArrayList<ClassFeeDetail>getClassFeeDetail_DataSetList(String p_standard,String p_section,String p_instanceID)throws DBProcessingException,DBValidationException{
        boolean l_session_created_now=false;
         try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          l_session_created_now=session.isI_session_created_now();
          
          
          ClassFeeDetail_DataSet classDetail=inject.getClassFeeDetail();
          
         return  classDetail.getClassOtherActivity(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
          
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
    
    public ArrayList<ClassFeeDetail>getClassFeeDetail_DataSetList(String p_standard,String p_section,String p_instanceID,CohesiveSession session)throws DBProcessingException,DBValidationException{
        
        CohesiveSession tempSession = this.session;
        try{
            
            this.session=session;
            return getClassFeeDetail_DataSetList(p_standard,p_section,p_instanceID);
            
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
    
    
    
     public String convertClassOtherActivitySummaryListToString(ArrayList<ClassOtherActivitySummary>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassOtherActivitySummary appEod=appEodList.get(i);
                
                String record=appEod.getActivityType()+"~"+
                              appEod.getMonth()+"~"+
                              appEod.getNo_of_StudentsOntheResult()+"~"+
                              appEod.getNo_of_students_Participated()+"~"+
                              appEod.getResultType()+"~"+
                              appEod.getYear();
                        
                              
                
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
     
     
     
     
    public String convertClassOtherActivityDetailListToString(ArrayList<ClassOtherActivityDetail>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassOtherActivityDetail appEod=appEodList.get(i);
                
                String record=appEod.getACTIVITY_NAME()+"~"+
                              appEod.getACTIVITY_TYPE()+"~"+
                              appEod.getDATE()+"~"+
                              appEod.getLEVEL()+"~"+
                              appEod.getRESULT()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getVENUE();
                              
                
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
    
    
     public String convertClassAttendanceDetailListToString(ArrayList<ClassAttendanceDetail>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassAttendanceDetail appEod=appEodList.get(i);
                
                String record=appEod.getMonth()+"~"+
                              appEod.getNo_ofDaysLeave()+"~"+
                              appEod.getNo_of_DaysAbsent()+"~"+
                              appEod.getNo_of_DaysPresent()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getStudentID()+"~"+
                              appEod.getYear()+"~"+
                              appEod.getStudentName()+"~"+
                              appEod.getMonthString()+"~"+
                              appEod.getNo_of_workingDays()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
     
     
      public String convertClassAttendanceSummaryListToString(ArrayList<ClassAttendanceSummary>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassAttendanceSummary appEod=appEodList.get(i);
                
                String record=appEod.getAbsentAverage()+"~"+
                              appEod.getAbsentPercentage()+"~"+
                              appEod.getLeaveAverage()+"~"+
                              appEod.getLeavePercentage()+"~"+
                              appEod.getMonth()+"~"+
                              appEod.getPresentAverage()+"~"+
                              appEod.getPresentPercentage()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getYear()+"~"+
                              appEod.getMonthString();
                              
                        
                              
                
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
      
      
      public String convertClassMarksDetailListToString(ArrayList<ClassMarksDetail>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassMarksDetail appEod=appEodList.get(i);
                
                String record=appEod.getEXAM()+"~"+
                              appEod.getGRADE()+"~"+
                              appEod.getMARK()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getSUBJECT_ID()+"~"+
                              appEod.getStudentName()+"~"+
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
      
       public String convertClassMarksSummaryListToString(ArrayList<ClassMarksSummary>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassMarksSummary appEod=appEodList.get(i);
                
                String record=appEod.getAverageMark()+"~"+
                              appEod.getExam()+"~"+
                              appEod.getLowMark()+"~"+
                              appEod.getSection()+"~"+
                              appEod.getStandard()+"~"+
                              appEod.getSubjectID()+"~"+
                              appEod.getTopMark()+"~"+
                              appEod.getGrade()+"~"+
                              appEod.getNo_of_Students()+"~"+
                              appEod.getOrderNo()+"~"+
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
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
      
       
       public String convertClassFeeDetailListToString(ArrayList<ClassFeeDetail>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassFeeDetail appEod=appEodList.get(i);
                
                String record=appEod.getBalanceAmount()+"~"+
                              appEod.getDueDate()+"~"+
                              appEod.getFeeAmount()+"~"+
                              appEod.getFeeID()+"~"+
                              appEod.getFeeType()+"~"+
                              appEod.getPaidAmount()+"~"+
                              appEod.getFeeDescription();
                              
                                                                               
                                                      
                        
                         
                
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
       
       
       
       public String convertClassFeeAmountSummaryListToString(ArrayList<ClassFeeAmountSummary>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                ClassFeeAmountSummary appEod=appEodList.get(i);
                
                String record=appEod.getFeeType()+"~"+
                              appEod.getTotalBalanceAmount()+"~"+
                              appEod.getTotalFeeAmount()+"~"+
                              appEod.getTotalPaidAmount();
                                                         
                                                      
                        
                         
                
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
