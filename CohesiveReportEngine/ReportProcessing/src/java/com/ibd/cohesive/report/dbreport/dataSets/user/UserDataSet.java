/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.user;

import com.ibd.businessViews.IUserDataSet;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.teacher.TVW_CONTACT_PERSON_DETAILS;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_CLASS_ENTITY_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_INSTITUTE_ENTITY_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_IN_LOG;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_OUT_LOG;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_PARENT_STUDENT_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_TEACHER_ENTITY_ROLEMAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_CREDENTIALS;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_PROFILE;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_ROLE_DETAIL;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_ROLE_INSTITUTE;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_ROLE_MASTER;
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
@Remote(IUserDataSet.class)
@Stateless
public class UserDataSet implements IUserDataSet {
    ReportDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    
    public UserDataSet(){
        try {
            inject=new ReportDependencyInjection("USER");
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    public String getUVW_INSTITUTE_ENTITY_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_INSTITUTE_ENTITY_ROLEMAPPING_DataSet");
          
          
          
          UVW_INSTITUTE_ENTITY_ROLEMAPPING_DATASET instituteEntity=inject.getInstituteRoleMappingDataSet();
          
          
          dbg("end of getUVW_INSTITUTE_ENTITY_ROLEMAPPING_DataSet");
          ArrayList<UVW_INSTITUTE_ENTITY_ROLEMAPPING>instEntityRoleMapping=  instituteEntity.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_INSTITUTE_ENTITY_ROLEMAPPINGListToString(instEntityRoleMapping, session);
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
     
    public String convertUVW_INSTITUTE_ENTITY_ROLEMAPPINGListToString(ArrayList<UVW_INSTITUTE_ENTITY_ROLEMAPPING>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_INSTITUTE_ENTITY_ROLEMAPPING appEod=appEodList.get(i);
                
                String record=appEod.getINSTITUTE_ID()+"~"+
                              appEod.getROLE_ID()+"~"+
                              appEod.getUSER_ID()+"~"+
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
    
    
    
    
    
    
    
    
    
    public String getUVW_IN_LOG_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_IN_LOG_DataSet");
          
          
          
          UVW_IN_LOG_DATASET inLog=inject.getInLogDataSet();
          
          
          dbg("end of getUVW_IN_LOG_DataSet");
         ArrayList<UVW_IN_LOG>inlog=  inLog.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_IN_LOGListToString(inlog, session);
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
    
     public String convertUVW_IN_LOGListToString(ArrayList<UVW_IN_LOG>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_IN_LOG appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_ENTITY()+"~"+
                              appEod.getMESSAGE_ID()+"~"+
                              appEod.getOPERATION()+"~"+
                              appEod.getREQUEST_JSON()+"~"+
                              appEod.getSERVICE()+"~"+
                              appEod.getSOURCE()+"~"+
                              appEod.getTIME_STAMP();
                              
                              
                
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
   
    
    
    
    
    
    public String getUVW_OUT_LOG_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_OUT_LOG_DataSet");
          
          
          
          UVW_OUT_LOG_DATASET outLog=inject.getOutLogDataSet();
          
          
          dbg("end of getUVW_OUT_LOG_DataSet");
         ArrayList<UVW_OUT_LOG>outLogList=  outLog.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_OUT_LOGListToString(outLogList, session);
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
    
      public String convertUVW_OUT_LOGListToString(ArrayList<UVW_OUT_LOG>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_OUT_LOG appEod=appEodList.get(i);
                
                String record=appEod.getBUSINESS_ENTITY()+"~"+
                              appEod.getCORRELATION_ID()+"~"+
                              appEod.getMESSAGE_ID()+"~"+
                              appEod.getOPERATION()+"~"+
                              appEod.getRESPONSE_JSON()+"~"+
                              appEod.getSERVICE()+"~"+
                              appEod.getSOURCE()+"~"+
                              appEod.getSTATUS()+"~"+
                              appEod.getTIME_STAMP();
                             
                              
                              
                
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
   
   
    
    
    
     
    public String getUVW_PARENT_STUDENT_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_PARENT_STUDENT_ROLEMAPPING_DataSet");
          
          
          
          UVW_PARENT_STUDENT_ROLEMAPPING_DATASET parentRoleMapping=inject.getParentRoleMappingDataSet();
          
          
          dbg("end of getUVW_PARENT_STUDENT_ROLEMAPPING_DataSet");
         ArrayList<UVW_PARENT_STUDENT_ROLEMAPPING>parentStuRoleMapping=  parentRoleMapping.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_PARENT_STUDENT_ROLEMAPPINGListToString(parentStuRoleMapping, session);
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
    
    public String convertUVW_PARENT_STUDENT_ROLEMAPPINGListToString(ArrayList<UVW_PARENT_STUDENT_ROLEMAPPING>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_PARENT_STUDENT_ROLEMAPPING appEod=appEodList.get(i);
                
                String record=appEod.getINSTITUTE_ID()+"~"+
                              appEod.getROLE_ID()+"~"+
                              appEod.getSTUDENT_ID()+"~"+
                              appEod.getUSER_ID()+"~"+
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
   
    
    
    
     
    public String getUVW_TEACHER_ENTITY_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_TEACHER_ENTITY_ROLEMAPPING_DataSet");
          
          
          
          UVW_TEACHER_ENTITY_ROLEMAPPING_DATASET teacherRoleMapping=inject.getTeacherRoleMappingDataSet();
          
          
          dbg("end of getUVW_TEACHER_ENTITY_ROLEMAPPING_DataSet");
         ArrayList<UVW_TEACHER_ENTITY_ROLEMAPPING>userTeachEntityRoleMapping=  teacherRoleMapping.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_TEACHER_ENTITY_ROLEMAPPINGListToString(userTeachEntityRoleMapping, session);
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
    
       public String convertUVW_TEACHER_ENTITY_ROLEMAPPINGListToString(ArrayList<UVW_TEACHER_ENTITY_ROLEMAPPING>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_TEACHER_ENTITY_ROLEMAPPING appEod=appEodList.get(i);
                
                String record=appEod.getINSTITUTE_ID()+"~"+
                              appEod.getROLE_ID()+"~"+
                              appEod.getTEACHER_ID()+"~"+
                              appEod.getUSER_ID()+"~"+
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
   
    
 
    
    
     
    public String getUVW_USER_CREDENTIALS_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_USER_CREDENTIALS_DataSet");
          
          
          
          UVW_USER_CREDENTIALS_DATASET userCredentials=inject.getUserCredentialsDataSet();
          
          
          dbg("end of getUVW_USER_CREDENTIALS_DataSet");
         ArrayList<UVW_USER_CREDENTIALS>userCredentialsList=  userCredentials.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_USER_CREDENTIALSListToString(userCredentialsList, session);
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
    
    
     public String convertUVW_USER_CREDENTIALSListToString(ArrayList<UVW_USER_CREDENTIALS>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_USER_CREDENTIALS appEod=appEodList.get(i);
                
                String record=appEod.getEXPIRY_DATE()+"~"+
                              appEod.getPASSWORD()+"~"+
                              appEod.getUSER_ID()+"~"+
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
    
    
     
    public String getUVW_USER_PROFILE_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_USER_PROFILE_DataSet");
          
          
          
          UVW_USER_PROFILE_DATASET userProfile=inject.getUserProfileDataSet();
          
          
          dbg("end of getUVW_USER_PROFILE_DataSet");
         ArrayList<UVW_USER_PROFILE>userProfileList=  userProfile.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_USER_PROFILEListToString(userProfileList, session);
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
    
    
     public String convertUVW_USER_PROFILEListToString(ArrayList<UVW_USER_PROFILE>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_USER_PROFILE appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getEMAIL_ID()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getMOBILE_NO()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getUSER_ID()+"~"+
                              appEod.getUSER_NAME()+"~"+
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
    
    
     
    public String getUVW_USER_ROLE_DETAIL_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_USER_ROLE_DETAIL_DataSet");
          
          
          
          UVW_USER_ROLE_DETAIL_DATASET userRole=inject.getUserRoleDetailDataSet();
          
          
          dbg("end of getUVW_USER_ROLE_DETAIL_DataSet");
         ArrayList<UVW_USER_ROLE_DETAIL>userRoleDetail=  userRole.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_USER_ROLE_DETAILListToString(userRoleDetail, session);
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
    
       public String convertUVW_USER_ROLE_DETAILListToString(ArrayList<UVW_USER_ROLE_DETAIL>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_USER_ROLE_DETAIL appEod=appEodList.get(i);
                
                String record=appEod.getAUTH()+"~"+
                              appEod.getAUTOAUTH()+"~"+
                              appEod.getCREATE()+"~"+
                              appEod.getDELETE()+"~"+
                              appEod.getFUNCTION_ID()+"~"+
                              appEod.getMODIFY()+"~"+
                              appEod.getREJECT()+"~"+
                              appEod.getROLE_ID()+"~"+
                              appEod.getVERSION_NUMBER()+"~"+
                              appEod.getVIEW();
                                                                                        
                              
                              
                
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
    
  
    
    
      
    public String getUVW_USER_ROLE_MASTER_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_USER_ROLE_MASTER_DataSet");
          
          
          
          UVW_USER_ROLE_MASTER_DATASET userRole=inject.getUserRoleMasterDataSet();
          
          
          dbg("end of getUVW_USER_ROLE_MASTER_DataSet");
         ArrayList<UVW_USER_ROLE_MASTER>userRolemaster=  userRole.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_USER_ROLE_MASTERListToString(userRolemaster, session);
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
    
    
        public String convertUVW_USER_ROLE_MASTERListToString(ArrayList<UVW_USER_ROLE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_USER_ROLE_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getAUTH_STATUS()+"~"+
                              appEod.getCHECKER_DATE_STAMP()+"~"+
                              appEod.getCHECKER_ID()+"~"+
                              appEod.getCHECKER_REMARKS()+"~"+
                              appEod.getMAKER_DATE_STAMP()+"~"+
                              appEod.getMAKER_ID()+"~"+
                              appEod.getMAKER_REMARKS()+"~"+
                              appEod.getRECORD_STATUS()+"~"+
                              appEod.getROLE_DESCRIPTION()+"~"+
                              appEod.getROLE_ID()+"~"+
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
   
    
     
    public String getUVW_CLASS_ENTITY_ROLEMAPPING_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_CLASS_ENTITY_ROLEMAPPING_DataSet");
          
          
          
          UVW_CLASS_ENTITY_ROLEMAPPING_DATASET studentContactPerson=inject.getClassRoleMappingDataSet();
          
          
          dbg("end of getUVW_CLASS_ENTITY_ROLEMAPPING_DataSet");
         ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING>classEntityRoleMapping=  studentContactPerson.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_CLASS_ENTITY_ROLEMAPPINGListToString(classEntityRoleMapping, session);
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
    
    
      public String convertUVW_CLASS_ENTITY_ROLEMAPPINGListToString(ArrayList<UVW_CLASS_ENTITY_ROLEMAPPING>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_CLASS_ENTITY_ROLEMAPPING appEod=appEodList.get(i);
                
                String record=appEod.getINSTITUTE_ID()+"~"+
                              appEod.getROLE_ID()+"~"+
                              appEod.getSECTION()+"~"+
                              appEod.getSTANDARD()+"~"+
                              appEod.getUSER_ID()+"~"+
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
   
    
      public String getUVW_USER_CONTRACT_MASTER_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_USER_CONTRACT_MASTER_DataSet");
          
          
          
         UVW_USER_CONTRACT_MASTER_DATASET studentContactPerson=inject.getUserContractMaster();
          
          
          dbg("end of getUVW_USER_CONTRACT_MASTER_DataSet");
         ArrayList<UVW_USER_CONTRACT_MASTER>userContractMaster=  studentContactPerson.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_USER_CONTRACT_MASTERListToString(userContractMaster, session);
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
      
      
 public String convertUVW_USER_CONTRACT_MASTERListToString(ArrayList<UVW_USER_CONTRACT_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_USER_CONTRACT_MASTER appEod=appEodList.get(i);
                
                String record=appEod.getCONTRACT_ID()+"~"+
                              appEod.getUSER_ID();
                                                            
                              
                
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
   
         
      
      
        public String getUVW_USER_ROLE_INSTITUTE_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getUVW_USER_ROLE_INSTITUTE_DataSet");
          
          
          
          UVW_USER_ROLE_INSTITUTE_DATASET studentContactPerson=inject.getUserRoleInstituteDataset();
          
          
          dbg("end of getUVW_USER_ROLE_INSTITUTE_DataSet");
         ArrayList<UVW_USER_ROLE_INSTITUTE>userroleInst=  studentContactPerson.getTableObject(p_userID, session, dbSession, inject);
          String result=this.convertUVW_USER_ROLE_INSTITUTEListToString(userroleInst, session);
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
        
  public String convertUVW_USER_ROLE_INSTITUTEListToString(ArrayList<UVW_USER_ROLE_INSTITUTE>appEodList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<appEodList.size();i++){
                UVW_USER_ROLE_INSTITUTE appEod=appEodList.get(i);
                
                String record=appEod.getINSTITUTE_ID()+"~"+
                              appEod.getUSER_ID()+"~"+
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
   
           
        
        
        
        
//   public String getUVW_USER_ROLE_MASTER_DataSet(String p_userID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getUVW_USER_ROLE_MASTER_DataSet");
//          
//          
//          
//        UVW_USER_ROLE_MASTER_DATASET studentContactPerson=inject.getUserRoleMaster();
//          
//          
//          dbg("end of getUVW_USER_ROLE_MASTER_DataSet");
//         ArrayList<UVW_USER_ROLE_MASTER>userRoleMaster=  studentContactPerson.getTableObject(p_userID, session, dbSession, inject);
//          String result=this.convertUVW_USER_ROLE_MASTERListToString(userRoleMaster, session);
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
//    public String convertUVW_USER_ROLE_MASTERListToString(ArrayList<UVW_USER_ROLE_MASTER>appEodList,CohesiveSession p_session) throws DBProcessingException{
//       String result=new String();
//        
//        
//        try{
//            
//            for(int i=0;i<appEodList.size();i++){
//                UVW_USER_ROLE_MASTER appEod=appEodList.get(i);
//                
//                String record=appEod.getAUTH_STATUS()+"~"+
//                              appEod.getCHECKER_DATE_STAMP()+"~"+
//                              appEod.getCHECKER_ID()+"~"+
//                              appEod.getCHECKER_REMARKS()+"~"+
//                              appEod.getMAKER_DATE_STAMP()+"~"+
//                              appEod.getMAKER_ID()+"~"+
//                              appEod.getMAKER_REMARKS()+"~"+
//                              appEod.getRECORD_STATUS()+"~"+
//                              appEod.getROLE_DESCRIPTION()+"~"+
//                              appEod.getROLE_ID()+"~"+
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
 
   
    
    
    
    
    
    
    
    
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
