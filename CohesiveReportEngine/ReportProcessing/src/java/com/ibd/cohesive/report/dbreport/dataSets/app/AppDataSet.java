/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSets.app;

import com.ibd.businessViews.IAppDataSet;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dbreport.dataModels.app.APP_RETENTION_PERIOD;
import com.ibd.cohesive.report.dbreport.dataModels.app.APP_SUPPORT;
import com.ibd.cohesive.report.dbreport.dataModels.app.ARCH_APPLY_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.app.ARCH_SHIPPING_STATUS;
import com.ibd.cohesive.report.dbreport.dataModels.app.BATCH_CONFIG;
import com.ibd.cohesive.report.dbreport.dataModels.app.BATCH_SERVICES;
import com.ibd.cohesive.report.dbreport.dataModels.app.CONTRACT_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.app.ERROR_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.app.INSTITUTE_MASTER;
import com.ibd.cohesive.report.dbreport.dataModels.app.SERVICE_TYPE_MASTER;
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
@Remote(IAppDataSet.class)
@Stateless
public class AppDataSet implements IAppDataSet{
    ReportDependencyInjection inject;
    AppDependencyInjection appInject;
    CohesiveSession session;
    DBSession dbSession;
    
    public AppDataSet(){
        try {
            inject=new ReportDependencyInjection("APP");
            appInject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    public String getERROR_MASTER_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getERROR_MASTER_DataSet");
          
          
          
          ERROR_MASTER_DATASET error=inject.getErrorMasterDataSet();
          
          
          dbg("end of getERROR_MASTER_DataSet");
         ArrayList<ERROR_MASTER>errorMaster=  error.getTableObject(p_instituteID, session, dbSession, inject);
          
         String result=this.convertERROR_MASTERArrayListToString(errorMaster, session);
         
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
    
    
    public String convertERROR_MASTERArrayListToString(ArrayList<ERROR_MASTER>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                ERROR_MASTER instituteMaster=instituteMasterList.get(i);
                
                String errorCode=instituteMaster.getERROR_CODE();
                String errorMessage=instituteMaster.getERROR_MESSAGE();
                
                String record=errorCode+"~"+errorMessage;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    public String getAPP_RETENTION_PERIOD_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getERROR_MASTER_DataSet");
          
          
          
          APP_RETENTION_PERIOD_DATASET error=inject.getAppRetentionPeriodDataSet();
          
          
          dbg("end of getERROR_MASTER_DataSet");
         ArrayList<APP_RETENTION_PERIOD>errorMaster=  error.getTableObject(p_instituteID, session, dbSession, inject);
          
         String result=this.convertAPP_RETENTION_PERIODArrayListToString(errorMaster, session);
         
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
    
    
    public String convertAPP_RETENTION_PERIODArrayListToString(ArrayList<APP_RETENTION_PERIOD>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                APP_RETENTION_PERIOD instituteMaster=instituteMasterList.get(i);
                
                String days=instituteMaster.getDAYS();
                String featureName=instituteMaster.getFEATURE_NAME();
                
                String record=days+"~"+featureName;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
//    public ArrayList<INSTITUTE_MASTER>getINSTITUTE_MASTER_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException{
//        try{
//            
//          session.createSessionObject();
//          dbSession.createDBsession(session);
//          dbg("inside getINSTITUTE_MASTER_DataSet");
//          
//          
//          
//          INSTITUTE_MASTER_DATASET institute=inject.getInstituteMasterDataSet();
//          
//          
//          dbg("end of getINSTITUTE_MASTER_DataSet");
//         return  institute.getTableObject(p_instituteID, session, dbSession, inject);
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
    
    
    
    public String getINSTITUTE_MASTER_DataSet(String p_instituteID,String businessReport)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getINSTITUTE_MASTER_DataSet");
          
          
          
          INSTITUTE_MASTER_DATASET institute=inject.getInstituteMasterDataSet();
          
          
         
          ArrayList<INSTITUTE_MASTER>instituteMaster  = institute.getTableObject(p_instituteID, session, dbSession, inject,businessReport);
          
          String result=  convertINSTITUTE_MASTERArrayListToString(instituteMaster,session);
           dbg("end of getINSTITUTE_MASTER_DataSet-->result-->"+result);
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

    public String convertINSTITUTE_MASTERArrayListToString(ArrayList<INSTITUTE_MASTER>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                INSTITUTE_MASTER instituteMaster=instituteMasterList.get(i);
                
                String instituteID=instituteMaster.getINSTITUTE_ID();
                String instituteName=instituteMaster.getINSTITUTE_NAME();
                String imagePath=instituteMaster.getIMAGE_PATH();
                
                String record=instituteID+"~"+instituteName+"~"+imagePath;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    public String getAPP_SUPPORT_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getAPP_SUPPORT_DataSet");
          
          
          
          APP_SUPPORT_DATASET institute=inject.getAppSupportDataSet();
          
          
          dbg("end of getAPP_SUPPORT_DataSet");
          ArrayList<APP_SUPPORT>instituteMaster  = institute.getTableObject(p_instituteID, session, dbSession, inject);
          
          String result=  convertAPP_SUPPORTArrayListToString(instituteMaster,session);
          
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

    public String convertAPP_SUPPORTArrayListToString(ArrayList<APP_SUPPORT>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                APP_SUPPORT instituteMaster=instituteMasterList.get(i);
                
                String emailID=instituteMaster.getEMAIL_ID();
                String mobileNo=instituteMaster.getMOBILE_NO();
                String name=instituteMaster.getNAME();
                String record=emailID+"~"+mobileNo+"~"+name;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    
    
    
    public String getSERVICE_TYPE_MASTER_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getSERVICE_TYPE_MASTER_DataSet");
          
          
          
          SERVICE_TYPE_MASTER_DATASET service=inject.getServiceMasterDataSet();
          
          
          dbg("end of getSERVICE_TYPE_MASTER_DataSet");
         ArrayList<SERVICE_TYPE_MASTER>serviceTypeMaster=  service.getTableObject(p_instituteID, session, dbSession, inject);
         String result=this.convertSERVICE_TYPE_MASTERArrayListToString(serviceTypeMaster, session);
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
    
    
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
    
    
      public String convertSERVICE_TYPE_MASTERArrayListToString(ArrayList<SERVICE_TYPE_MASTER>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                SERVICE_TYPE_MASTER instituteMaster=instituteMasterList.get(i);
                
                String serviceName=instituteMaster.getSERVICE_NAME();
                String serviceType=instituteMaster.getSERVICE_TYPE();
                
                String record=serviceName+"~"+serviceType;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
  
     public String getARCH_SHIPPING_STATUS_DataSet(String p_instituteID,String p_date)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getERROR_MASTER_DataSet");
          
          
          
          ARCH_SHIPPING_STATUS_DATASET error=inject.getAppShippingStatus();
          
          
          dbg("end of getERROR_MASTER_DataSet");
         ArrayList<ARCH_SHIPPING_STATUS>errorMaster=  error.getTableObject(p_instituteID,p_date, session, dbSession, inject,appInject);
          
         String result=this.convertARCH_SHIPPING_STATUSArrayListToString(errorMaster, session);
         
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
    
    
    public String convertARCH_SHIPPING_STATUSArrayListToString(ArrayList<ARCH_SHIPPING_STATUS>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                ARCH_SHIPPING_STATUS instituteMaster=instituteMasterList.get(i);
                
                String applyStatus=instituteMaster.getAPPLY_STATUS();
                String checkSumStatus=instituteMaster.getCHECKSUM_STATUS();
                String error=instituteMaster.getERROR();
                String fileName=instituteMaster.getFILE_NAME();
                String sentStatus=instituteMaster.getSENT_STATUS();
                String sequenceNo=instituteMaster.getSEQUENCE_NO();
                
                String record=applyStatus+"~"+checkSumStatus+"~"+error+"~"+fileName+"~"+sentStatus+"~"+sequenceNo;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    
    
    
     public String getARCH_APPLY_STATUS_DataSet(String p_instituteID,String p_date)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getERROR_MASTER_DataSet");
          
          
          
          ARCH_APPLY_STATUS_DATASET error=inject.getAppApplyStatusDataset();
          
          
          dbg("end of getERROR_MASTER_DataSet");
         ArrayList<ARCH_APPLY_STATUS>errorMaster=  error.getTableObject(p_instituteID,p_date, session, dbSession, inject);
          
         String result=this.convertARCH_APPLY_STATUSArrayListToString(errorMaster, session);
         
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
    
    
    public String convertARCH_APPLY_STATUSArrayListToString(ArrayList<ARCH_APPLY_STATUS>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                ARCH_APPLY_STATUS instituteMaster=instituteMasterList.get(i);
                
                String applyStatus=instituteMaster.getAPPLY_STATUS();
                String checkSumStatus=instituteMaster.getCHECKSUM_STATUS();
                String error=instituteMaster.getERROR();
                String fileName=instituteMaster.getFILE_NAME();
                String receiveStatus=instituteMaster.getRECEIVE_STATUS();
                String sequenceNo=instituteMaster.getSEQUENCE_NO();
                
                String record=applyStatus+"~"+checkSumStatus+"~"+error+"~"+fileName+"~"+receiveStatus+"~"+sequenceNo;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
     
     public String getBATCH_CONFIG_DataSet(String p_instituteID,String p_businessDate)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getERROR_MASTER_DataSet");
          
          
                    BATCH_CONFIG_DATASET error=inject.getBatchConfigDataset();
          
          
          dbg("end of getERROR_MASTER_DataSet");
         ArrayList<BATCH_CONFIG>errorMaster=  error.getTableObject(p_instituteID,p_businessDate,session, dbSession, inject);
          
         String result=this.convertBATCH_CONFIGArrayListToString(errorMaster, session);
         
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
    
    
    public String convertBATCH_CONFIGArrayListToString(ArrayList<BATCH_CONFIG>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                BATCH_CONFIG instituteMaster=instituteMasterList.get(i);
                
                String authstatus=instituteMaster.getAUTH_STATUS();
                String batchName=instituteMaster.getBATCH_NAME();
                String checkerDateStamp=instituteMaster.getCHECKER_DATE_STAMP();
                String checkerId=instituteMaster.getCHECKER_ID();
                String checkerRemarks=instituteMaster.getCHECKER_REMARKS();
                String childNoOfThreads=instituteMaster.getCHILD_NO_OF_THREADS();
                String eod=instituteMaster.getEOD();
                String executionFrequency=instituteMaster.getEXECUTION_FREQUENCY();
                String intraDayBatch=instituteMaster.getINTRA_DAY_BATCH();
                String layer=instituteMaster.getLAYER();
                String makerDatestamp=instituteMaster.getMAKER_DATE_STAMP();
                String makerId=instituteMaster.getMAKER_ID();
                String makerRemark=instituteMaster.getMAKER_REMARKS();
                String noOfThreads=instituteMaster.getNO_OF_THREADS();
                String recordStatus=instituteMaster.getRECORD_STATUS();
                String recFail=instituteMaster.getREC_FAIL();
                String startTime=instituteMaster.getSTART_TIME();
                String versionNum=instituteMaster.getVERSION_NUMBER();
                
                
                
                String record=authstatus+"~"+batchName+"~"+checkerDateStamp+"~"+checkerId+"~"+checkerRemarks+"~"+childNoOfThreads+"~"+eod+"~"+executionFrequency+"~"+intraDayBatch+"~"+layer+"~"+makerDatestamp+"~"+makerId+"~"+makerRemark+"~"+noOfThreads+"~"+recordStatus+"~"+recFail+"~"+startTime+"~"+versionNum;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
       
     public String getBATCH_SERVICES_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getERROR_MASTER_DataSet");
          
          
          
          BATCH_SERVICES_DATASET error=inject.getBatchServiceDataset();
          
          
          dbg("end of getERROR_MASTER_DataSet");
         ArrayList<BATCH_SERVICES>errorMaster=  error.getTableObject(p_instituteID, session, dbSession, inject);
          
         String result=this.convertBATCH_SERVICESArrayListToString(errorMaster, session);
         
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
    
    
    public String convertBATCH_SERVICESArrayListToString(ArrayList<BATCH_SERVICES>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                BATCH_SERVICES instituteMaster=instituteMasterList.get(i);
                
                String batchDescription=instituteMaster.getBATCH_DESCRIPTION();
                String batchName=instituteMaster.getBATCH_NAME();
               
                
                
                String record=batchDescription+"~"+batchName;
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    public String getCONTRACT_MASTER_DataSet(String p_instituteID)throws DBProcessingException,DBValidationException{
        try{
            
          session.createSessionObject();
          dbSession.createDBsession(session);
          dbg("inside getERROR_MASTER_DataSet");
          
          
          
          CONTRACT_MASTER_DATASET error=inject.getContractMasterDataset();
          
          
          dbg("end of getERROR_MASTER_DataSet");
         ArrayList<CONTRACT_MASTER>errorMaster=  error.getTableObject(p_instituteID, session, dbSession, inject);
          
         String result=this.convertCONTRACT_MASTERArrayListToString(errorMaster, session);
         
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
    
    
    public String convertCONTRACT_MASTERArrayListToString(ArrayList<CONTRACT_MASTER>instituteMasterList,CohesiveSession p_session) throws DBProcessingException{
       String result=new String();
        
        
        try{
            
            for(int i=0;i<instituteMasterList.size();i++){
                CONTRACT_MASTER instituteMaster=instituteMasterList.get(i);
                
                String communicationLang=instituteMaster.getCOMMUNICATION_LANGUAGE();
                String communicationMode=instituteMaster.getCOMMUNICATION_MODE();
                String contactEmail=instituteMaster.getCONTACT_EMAIL();
                String contactMobileNo=instituteMaster.getCONTACT_MOBILE_NO();
                String contractId=instituteMaster.getCONTRACT_ID();
                String countryCode=instituteMaster.getCOUNTRY_CODE();
                String emailId=instituteMaster.getEMAIL_ID();
                String emailLimit=instituteMaster.getEMAIL_LIMIT();
                String emailUsed=instituteMaster.getEMAIL_USED();
                String emailVendor=instituteMaster.getEMAIL_VENDOR();
                String instituteID=instituteMaster.getINSTITUTE_ID();
                String plan=instituteMaster.getPLAN();
                String smsLimit=instituteMaster.getSMS_LIMIT();
                String smsUsed=instituteMaster.getSMS_USED();
                String smsVendor=instituteMaster.getSMS_VENDOR();
                
                
                
                
                String record=communicationLang+"~"+communicationMode+"~"+contactEmail+"~"+contactMobileNo+"~"+contractId+"~"+countryCode+"~"+emailId+"~"+emailLimit+"~"+emailUsed+"~"+emailVendor+"~"+instituteID+"~"+plan+"~"+smsLimit+"~"+smsUsed+"~"+smsVendor;
                
                
                
                
                
                if(i==0){
                
                   result=record;
                }else{
                    
                    result=result+"#"+record;
                    
                }
                
            }
            
            return result;
//     }catch(DBProcessingException ex){
//          dbg(ex);
//          throw new DBProcessingException("DBProcessingException"+ex.toString());
     }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
    }
    
    
    
    
}
