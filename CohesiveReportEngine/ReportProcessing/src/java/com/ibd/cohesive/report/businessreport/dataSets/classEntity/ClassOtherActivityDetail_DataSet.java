/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassOtherActivityDetail;
import com.ibd.cohesive.report.dbreport.dataModels.classEntity.CLASS_STUDENT_MAPPING;
import com.ibd.cohesive.report.dbreport.dataModels.student.SVW_STUDENT_OTHER_ACTIVITY;
import com.ibd.businessViews.IClassDataSet;
import com.ibd.businessViews.IStudentDataSet;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author DELL
 */
public class ClassOtherActivityDetail_DataSet {
     
    
    
    public ArrayList<ClassOtherActivityDetail> getClassOtherActivity(String p_standard,String p_section,String l_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        
        ArrayList<ClassOtherActivityDetail> dataset=new ArrayList();
        try{
        
        dbg("inside getClassOtherActivity--->getTableObject",session);  
        
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=appInject.getBusinessService(session);
        Map<String,DBRecord>instituteFeeMap=null;
      
        try{
        
             instituteFeeMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", session,dbSession);
       
        }catch(DBValidationException ex){
                                dbg("exception in view operation"+ex,session);
                                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
        //                            session.getErrorhandler().log_app_error("BS_VAL_013", l_feeID);
        //                            throw new BSValidationException("BSValidationException");

                                }else{

                                    throw ex;
                                }
                            }
        
        if(instituteFeeMap!=null){
        
            Map<String,List<DBRecord>>groupMap=instituteFeeMap.values().stream().filter(rec->rec.getRecord().get(13).trim().equals("O")&&rec.getRecord().get(14).trim().equals("A")).collect(Collectors.groupingBy(rec->rec.getRecord().get(1).trim()));
            Iterator<String>keyIterator=groupMap.keySet().iterator();

            while(keyIterator.hasNext()){

                String groupID=keyIterator.next();

                if(bs.checkClassExistenceInTheGroup(l_instituteID, p_standard, p_section, groupID, session, dbSession, appInject)){

                    List<DBRecord>listForGroup=groupMap.get(groupID);

                    for(int i=0;i<listForGroup.size();i++){

                        ArrayList<String>activityList=listForGroup.get(i).getRecord();
                        String activityID=activityList.get(2).trim();
                        String activityName=activityList.get(3).trim();
                        String activityType=activityList.get(4).trim();
                        String level=activityList.get(5).trim();
                        String venue=activityList.get(6).trim();
                        String date=activityList.get(7).trim();



                        Map<String,DBRecord>activityMap=null;

                        try{


                         activityMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+activityID+i_db_properties.getProperty("FOLDER_DELIMITER")+activityID,"OTHER_ACTIVITY", "INSTITUTE_OTHER_ACTIVITY_TRACKING", session, dbSession);

                        }catch(DBValidationException ex){
                                    dbg("exception in view operation"+ex,session);
                                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
            //                            session.getErrorhandler().log_app_error("BS_VAL_013", l_feeID);
            //                            throw new BSValidationException("BSValidationException");

                                    }else{

                                        throw ex;
                                    }
                                }


                        if(activityMap!=null){


                            List<DBRecord>filteredRecords=activityMap.values().stream().collect(Collectors.toList());

                            //check student existence in the class
                            for(int j=0;j<filteredRecords.size();j++){

                                   String studentID=filteredRecords.get(j).getRecord().get(0).trim();

                                   if(bs.checkStudentExistenceInTheClass(l_instituteID, p_standard, p_section, studentID, session, dbSession, appInject)){


                                     String result=filteredRecords.get(j).getRecord().get(3).trim();

                                     ClassOtherActivityDetail classActivity=new ClassOtherActivityDetail();
    //              
                                     classActivity.setACTIVITY_NAME(activityName);
                                     classActivity.setACTIVITY_TYPE(activityType);
                                     classActivity.setDATE(date);
                                     classActivity.setLEVEL(level);
                                     classActivity.setVENUE(venue);
                                     classActivity.setSTUDENT_ID(studentID);
                                     classActivity.setRESULT(result);
                                     dataset.add(classActivity);

                                   }

                            }



                        }


                    }

                }



            }
        
        }
        
        
        
        if(dataset.isEmpty()){
            
            
            ClassOtherActivityDetail classActivity=new ClassOtherActivityDetail();
    //              
                                     classActivity.setACTIVITY_NAME(" ");
                                     classActivity.setACTIVITY_TYPE(" ");
                                     classActivity.setDATE(" ");
                                     classActivity.setLEVEL(" ");
                                     classActivity.setVENUE(" ");
                                     classActivity.setSTUDENT_ID(" ");
                                     classActivity.setRESULT(" ");
                                     dataset.add(classActivity);
            
        }
        
        
        
        
    dbg("end of getClassOtherActivity--->getTableObject",session);  
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
       return dataset;
    }
    
    
    
    
   
    
     public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
