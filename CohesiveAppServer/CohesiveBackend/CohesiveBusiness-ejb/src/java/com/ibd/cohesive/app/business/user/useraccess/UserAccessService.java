/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.user.useraccess;

import com.ibd.businessViews.IUserAccessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJBException;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class UserAccessService implements IUserAccessService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    static int keyIterator=0;
    String studentID_dummy;
    String standard_dummy;
    String section_dummy;
    String teacherID_dummy;
    public UserAccessService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
    }
    
  public Map<String,ArrayList<String>> entityAccessValidation(String p_userID,String p_service,String p_operation,Map<String,String> p_businessEntity,String p_instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
      boolean status=true;
      boolean l_session_created_now=false;
      Map<String,ArrayList<String>>l_filterMap=new HashMap();
      try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
//       BSValidation bsv=inject.getBsv(session);
       IBDProperties i_db_properties=session.getCohesiveproperties();
       IPDataService pds=inject.getPdataservice();
       dbg("inside entity access validation");
       dbg("p_userID"+p_userID);
       dbg("p_service"+p_service);
       dbg("p_instituteID"+p_instituteID);
       
       String[] l_pkey={p_userID};
       ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_pkey);
       String l_userType=l_userList.get(13).trim();
      
       String[] l_servicePKey={p_service};
       ArrayList<String>l_serviceList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive","APP", "SERVICE_TYPE_MASTER",l_servicePKey);
       String l_serviceType=l_serviceList.get(1).trim();
       dbg("l_userType"+l_userType);
       dbg("l_serviceType"+l_serviceType);
       
       if(l_userType.equals("P")){
          Iterator<String> keyIterator=p_businessEntity.keySet().iterator();
          while(keyIterator.hasNext()){
                String l_entityName=keyIterator.next();
                if(l_entityName.equals("studentID")){
                    studentID_dummy=p_businessEntity.get(l_entityName);
                }
           }
           dbg("userType P--->studentID_dummy"+studentID_dummy);
           String[] l_userPKey={p_userID,studentID_dummy};
           ArrayList<String> l_parentRoleMappingList=new ArrayList();
           try{
            l_parentRoleMappingList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID,"INSTITUTE","UVW_PARENT_STUDENT_ROLEMAPPING",l_userPKey);
           
           }catch(DBValidationException ex){
               if(ex.toString().contains("DB_VAL_011")){
                   status=false;
               }
           }
           
           if(status==true){
               
               l_filterMap.put(p_userID, l_parentRoleMappingList);
           }
           
           //remove student id from student profile and student 
//           try{
//            ArrayList<String> l_studentLoginList=pds.readRecordPData(dbdi,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID,"INSTITUTE","IVW_STUDENT_LOGIN_MAPPING",l_userPKey);
//           
//           }catch(DBValidationException ex){
//               if(ex.toString().equals("DB_VAL_011")){
//                   status=false;
//               }
//           }
//            dbg("status after student login mapping check"+status);
//            if(status){
//               Map<String,ArrayList<String>>l_parentRoleMap= pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_PARENT_ROLEMAPPING",dbdi);  
//               l_filterMap=l_parentRoleMap.values().stream().collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
//               dbg("user type--->P   l_filterMap"+l_filterMap.size());           
//            }
//            if(l_filterMap.isEmpty()){
//                errhandler.log_app_error("BS_VAL_014",studentID_dummy );
//            }
       }
       
       if(l_userType.equals("T")){
           
            if(l_serviceType.equals("C")){
               
                Iterator<String> keyIterator=p_businessEntity.keySet().iterator();
                  while(keyIterator.hasNext()){
                     String l_entityName=keyIterator.next();
                        if(l_entityName.equals("standard")){
                          standard_dummy=p_businessEntity.get(l_entityName);
                         }
                        if(l_entityName.equals("section")){
                           section_dummy=p_businessEntity.get(l_entityName);
                         }
                    }
               dbg("userType T--->serviceType--->C--->standard_dummy"+standard_dummy);
               dbg("userType T--->serviceType--->C--->section_dummy"+section_dummy);
               l_filterMap= getFilterMap(p_userID,l_serviceType,p_instituteID) ;
               if(l_filterMap.isEmpty()){
                  errhandler.log_app_error("BS_VAL_014",standard_dummy+section_dummy );
                }
         }
           
           if(l_serviceType.equals("S")){
               Iterator<String> keyIterator=p_businessEntity.keySet().iterator();
                  while(keyIterator.hasNext()){
                     String l_entityName=keyIterator.next();
                       if(l_entityName.equals("studentID")){
                          studentID_dummy=p_businessEntity.get(l_entityName);
                }
             }
                  
              dbg("userType T--->serviceType--->S--->studentID_dummy"+studentID_dummy);
               String[] pkey={studentID_dummy};
               ArrayList<String>l_studentList=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER",pkey);
               standard_dummy=l_studentList.get(2).trim();
               section_dummy=l_studentList.get(3).trim();
               
               dbg("userType T--->serviceType--->S--->standard_dummy"+standard_dummy);
               dbg("userType T--->serviceType--->S--->section_dummy"+section_dummy);
               
               l_filterMap= getFilterMap(p_userID,l_serviceType,p_instituteID) ;
               
              
               
               if(l_filterMap.isEmpty()){
                   errhandler.log_app_error("BS_VAL_014",studentID_dummy );
               }
           }
           
           if(l_serviceType.equals("T")){
               Iterator<String> keyIterator=p_businessEntity.keySet().iterator();
                  while(keyIterator.hasNext()){
                     String l_entityName=keyIterator.next();
                       if(l_entityName.equals("teacherID")){
                          teacherID_dummy=p_businessEntity.get(l_entityName);
                        }
                    }
           dbg("userType T--->serviceType--->T--->teacherID_dummy"+teacherID_dummy);
           
             
           l_filterMap= getFilterMap(p_userID,l_serviceType,p_instituteID) ;
               
             
             if(l_filterMap.isEmpty()){
                 errhandler.log_app_error("BS_VAL_014",teacherID_dummy );
             }
             
               
           }
          
         if(l_serviceType.equals("I")){
             l_filterMap= getFilterMap(p_userID,l_serviceType,p_instituteID) ; 
             
             if(l_filterMap.isEmpty()){
                 errhandler.log_app_error("BS_VAL_014",p_instituteID );
             }
           
        }
       }
       dbg("end of entity access validation p_filterMap sizel_filterMap"+l_filterMap.size());
         }catch(DBValidationException ex){
                 
           throw ex;
        }catch(BSValidationException ex){
                 
        throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
           if(l_session_created_now){
            session.clearSessionObject();
            dbSession.clearSessionObject();
           }
        }
    return  l_filterMap;
  }
  
  public Map<String,ArrayList<String>> entityAccessValidation(String p_userID,String p_service,String p_operation,Map<String,String> p_businessEntity,String p_instituteID,CohesiveSession session)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
      CohesiveSession tempSession = this.session;
    try{
        this.session=session;
       return entityAccessValidation(p_userID, p_service,p_operation,p_businessEntity,p_instituteID);
       
     }catch(DBValidationException ex){
        throw ex;
     }catch(DBProcessingException ex){
        dbg(ex);
        throw new DBProcessingException("DBProcessingException"+ex.toString());
            
     }catch(BSProcessingException ex){
         dbg(ex);
         throw new BSProcessingException("BSProcessingException"+ex.toString());
     }catch(BSValidationException ex){
         throw ex;
     }catch (Exception ex) {
          dbg(ex);
          throw new BSProcessingException("Exception" + ex.toString());   
      }finally{
           this.session=tempSession;
        }
  }
  private static String getKey(ArrayList<String>l_valueList){
      String l_key;
      
          l_key=l_valueList.get(0).trim()+keyIterator;
          keyIterator++;
          
          
      
      return l_key;
  }
   
  
  private static ArrayList<String> getValue(ArrayList<String>l_valueList){
      
      
      return l_valueList;
  }
  
  private Map<String,ArrayList<String>> getFilterMap(String p_userID,String p_serviceType,String p_instituteID)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException{
      Map<String,ArrayList<String>> l_filterMap=new HashMap(); 
      try{
//       ErrorHandler errhandler = session.getErrorhandler();
       IBDProperties i_db_properties=session.getCohesiveproperties();
       IPDataService pds=inject.getPdataservice();
       Map<String,ArrayList<String>>l_userFilterMap=new HashMap(); 
          
       
       if(p_serviceType.equals("S")||p_serviceType.equals("C")){
            /*
                Algorithm used to filter the class student map
             
          1.Checking the exact match of standardSection.If found retun the filter map.
          2.If exact match of standardSection not found go to the standard validation
          3.Inside standard validation 
                  i.Checking the exact match of standard.If found retun the filter map.
                 ii.If exact match of standard not found check "ALL" in standard.retun the filter map.
          4.If the filter map return by standard validation is empty retun the filter map.
          5.If the filter map return by standard validation is not empty go to section validation
                  i.Checking the exact match of section..If found retun the filter map.
                 ii.If exact match of standard not found check "ALL" in standard.retun the filter map.
          6.The filter map return by getFilterMap is empty throw error is in the calling place getFilterMap. 
          */
         Map<String,ArrayList<String>> l_classStudenMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_STUDENT_CLASS_ROLEMAPPING",session,dbSession);    
          l_userFilterMap=l_classStudenMap.values().stream().filter(rec->rec.get(0).trim().equals(p_userID)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
          keyIterator=0;
         
          dbg("getFilterMap--->filtration by userID--->l_userFilterMap.size"+l_userFilterMap.size());
               l_filterMap= l_userFilterMap.values().stream().filter(rec->rec.get(2).trim().equals(standard_dummy)&&rec.get(3).trim().equals(section_dummy)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
               keyIterator=0;
          dbg("getFilterMap--->filtration by exact match of standard and section--->l_filterMap.size"+l_filterMap.size());     
               
          if(l_filterMap.isEmpty()){
              
              l_filterMap=  standardValidation(l_userFilterMap);
              if(l_filterMap.isEmpty()){
                 return l_filterMap;
              }
              else{
                 l_filterMap= sectionValidation(l_filterMap); 
                 return l_filterMap;
              }
          }else{
              return l_filterMap;
          }
       } 
       
       if(p_serviceType.equals("T")){
           
         Map<String,ArrayList<String>> l_teacherRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_TEACHER_ROLEMAPPING",session,dbSession);    
         l_userFilterMap=l_teacherRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(p_userID)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                 keyIterator=0;
                  dbg("userType T--->serviceType--->T--->fitration by userID--->p_filterMap.size"+l_userFilterMap.size());
                  
            l_filterMap= l_userFilterMap.values().stream().filter(rec->rec.get(2).trim().equals(p_instituteID)&&rec.get(3).trim().equals(teacherID_dummy)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
               keyIterator=0;
                 dbg("getFilterMap--->filtration by exact match of instituteID and teacherID--->l_filterMap.size"+l_filterMap.size()); 
                 
               if(l_filterMap.isEmpty()){
              
              l_filterMap=  instituteValidation(l_userFilterMap,p_instituteID);
              if(l_filterMap.isEmpty()){
                 return l_filterMap;
              }
              else{
                 l_filterMap= teacherValidation(l_filterMap); 
                 return l_filterMap;
              }
          }else{
              return l_filterMap;
          }
           
           
       }
       
      if(p_serviceType.equals("I")){
          
          Map<String,ArrayList<String>> l_instituteRoleMap=pds.readTablePData("USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User","USER","UVW_INSTITUTE_ROLEMAPPING",session,dbSession); 
          l_userFilterMap=l_instituteRoleMap.values().stream().filter(rec->rec.get(0).trim().equals(p_userID)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                 keyIterator=0;
                  dbg("userType T--->serviceType--->I--->fitration by userID--->p_filterMap.size"+l_userFilterMap.size());
         return instituteValidation(l_userFilterMap,p_instituteID);
          
      } 
       
        }catch(DBValidationException ex){
                 
        throw ex;
        
        }catch(DBProcessingException ex){
            dbg(ex);
        throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
      return l_filterMap;
  }
  
  private Map<String,ArrayList<String>> standardValidation(Map<String,ArrayList<String>>p_userFilterMap)throws BSProcessingException{
      Map<String,ArrayList<String>>l_filterMap=new HashMap();
      try{
                  l_filterMap=p_userFilterMap.values().stream().filter(rec->rec.get(2).trim().equals(standard_dummy)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                  keyIterator=0;
                  
          dbg("standardValidation--->filtration by standard--->l_filterMap.size"+l_filterMap.size());   
          if(l_filterMap.isEmpty()){//There is no exact match for standard so we need to look for 'ALL' option in the standard 
                   
                      l_filterMap=p_userFilterMap.values().stream().filter(rec->rec.get(2).trim().equals("ALL")).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                      keyIterator=0;
         dbg("standardValidation--->filtration by standard ALL--->l_filterMap.size"+l_filterMap.size());  

        }  
          
          
          
      }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
     return l_filterMap;
  }
  
  private Map<String,ArrayList<String>> sectionValidation(Map<String,ArrayList<String>>p_standardFilterMap)throws BSProcessingException{
      Map<String,ArrayList<String>>l_filterMap=new HashMap();
      try{
          l_filterMap=p_standardFilterMap.values().stream().filter(rec->rec.get(3).trim().equals(section_dummy)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                   keyIterator=0;
                   
            dbg("sectionValidation--->filtration by section--->l_filterMap.size"+l_filterMap.size());         
                   if(l_filterMap.isEmpty()){
                       l_filterMap=p_standardFilterMap.values().stream().filter(rec->rec.get(3).trim().equals("ALL")).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                       keyIterator=0;    
            dbg("sectionValidation--->filtration by section ALL--->l_filterMap.size"+l_filterMap.size());  

                   }
          
      }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
     return l_filterMap;
  }
  private Map<String,ArrayList<String>> instituteValidation(Map<String,ArrayList<String>>p_userFilterMap,String p_instituteID)throws BSProcessingException{
      Map<String,ArrayList<String>>l_filterMap=new HashMap();
      try{
          l_filterMap= p_userFilterMap.values().stream().filter(rec->rec.get(2).trim().equals(p_instituteID)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
               keyIterator=0;    
               dbg("instituteValidation--->fitration by instituteID--->p_filterMap.size"+l_filterMap.size());
               
               if(l_filterMap.isEmpty()){
                   
                   l_filterMap= p_userFilterMap.values().stream().filter(rec->rec.get(2).trim().equals("ALL")).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                   keyIterator=0;
                   dbg("instituteValidation--->fitration by instituteID ALL--->p_filterMap.size"+l_filterMap.size());
                   
               }
          
          
          
      }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
     return l_filterMap;
  }
  
  private Map<String,ArrayList<String>> teacherValidation(Map<String,ArrayList<String>>p_instituteFilterMap)throws BSProcessingException{
      Map<String,ArrayList<String>>l_filterMap=new HashMap();
      try{
          l_filterMap=p_instituteFilterMap.values().stream().filter(rec->rec.get(3).trim().equals(teacherID_dummy)).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                   keyIterator=0;
                   
            dbg("teacherValidation--->filtration by teacherID--->l_filterMap.size"+l_filterMap.size());         
                   if(l_filterMap.isEmpty()){
                       l_filterMap=p_instituteFilterMap.values().stream().filter(rec->rec.get(3).trim().equals("ALL")).collect(Collectors.toMap(UserAccessService::getKey,UserAccessService::getValue));
                       keyIterator=0;    
            dbg("teacherValidation--->filtration by teacherID ALL--->l_filterMap.size"+l_filterMap.size());  

                   }
          
      }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
     return l_filterMap;
  }
  public boolean operationAccessValidation(String p_service,String p_operation,String p_instituteID, Map<String,ArrayList<String>>p_roleMap)throws BSProcessingException,DBValidationException,DBProcessingException{
      boolean status=true;
      boolean l_session_created_now=false;
      
      try{
       session.createSessionObject();
       dbSession.createDBsession(session);
       l_session_created_now=session.isI_session_created_now();
       ErrorHandler errhandler = session.getErrorhandler();
       IBDProperties i_db_properties=session.getCohesiveproperties();
       IPDataService pds=inject.getPdataservice(); 
       IMetaDataService mds=inject.getMetadataservice();
       dbg("inside operation access validation");
       Iterator<ArrayList<String>> valueIterator=p_roleMap.values().iterator();
       while(valueIterator.hasNext()){
           ArrayList<String> l_roleFunctionsList=new ArrayList();
           ArrayList<String>roleMapList=valueIterator.next();
           String roleID=roleMapList.get(1).trim();
           dbg("roleID"+roleID);
           try{
           String[] l_pkey={roleID,p_service};    
           l_roleFunctionsList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER","UVW_USER_ROLE_DETAIL",l_pkey);
           status=true;
           
           }catch(DBValidationException ex){
               if(ex.toString().contains("DB_VAL_011")){
                   
                   try{
                   String[] l_pkey={roleID,"ALL"};    
                   l_roleFunctionsList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER","UVW_USER_ROLE_DETAIL",l_pkey);
                   status=true;
                   }catch(DBValidationException ex1){
                     if(ex1.toString().contains("DB_VAL_011")){  
                          status=false;
                          dbg("service not in role");
                         errhandler.log_app_error("BS_VAL_014", p_service);
                     }
                   }
                   
               }else{
                   throw ex;
               }
           }
           
           
           
           if(status){
              dbg("l_roleFunctionsList size"+l_roleFunctionsList.size());
              int l_opearationColID=mds.getColumnMetaData("USER", "UVW_USER_ROLE_DETAIL", p_operation.toUpperCase(),session).getI_ColumnID();
              dbg("l_opearationColID"+l_opearationColID);
              for(int i=0;i<l_roleFunctionsList.size();i++){
                    dbg("l_roleFunctionsList"+l_roleFunctionsList.get(i));   
               }   
            if(!(l_roleFunctionsList.get(l_opearationColID-1).trim().equals("true"))){
                status=false;
                dbg("operation not in role");
                errhandler.log_app_error("BS_VAL_014", p_operation);
            }
           }
       }
       
       }catch(DBValidationException ex){
                 
        throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
           if(l_session_created_now){
            session.clearSessionObject();
            dbSession.clearSessionObject();
           }
        }
      return status;
  }
  
  public boolean operationAccessValidation(String p_service,String p_operation,String p_instituteID, Map<String,ArrayList<String>>p_roleMap,CohesiveSession session)throws BSProcessingException,DBValidationException,DBProcessingException{
       CohesiveSession tempSession = this.session;
    try{
        this.session=session;
       return operationAccessValidation(p_service, p_operation,p_instituteID,p_roleMap);
     }catch(DBValidationException ex){
                 
        throw ex;
     }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch(BSProcessingException ex){
             dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
            
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
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
