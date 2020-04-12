/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.student;

import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.student.StudentOtherActivityDetail;
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
public class StudentOtherActivityDetail_DataSet {
     public ArrayList<StudentOtherActivityDetail> getStudentOtherActivityDetailObject(String p_studentID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBProcessingException,DBValidationException{
        ArrayList<StudentOtherActivityDetail> dataset;
        
        try{
        dbg("inside getStudentOtherActivityDetailObject",session);
        dataset=new ArrayList();
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IMetaDataService mds=inject.getMetadataservice();
        int recStatusColId=mds.getColumnMetaData("OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", "RECORD_STATUS", session).getI_ColumnID()-1;
        int authStatusColId=mds.getColumnMetaData("OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", "AUTH_STATUS", session).getI_ColumnID()-1;
        try{
        
                Map<String,DBRecord>activityMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY", "SVW_STUDENT_OTHER_ACTIVITY", session, dbSession);
                
                List<DBRecord>authorizedRecords=activityMap.values().stream().filter(rec->rec.getRecord().get(recStatusColId).trim().equals("O")&&rec.getRecord().get(authStatusColId).trim().equals("A")).collect(Collectors.toList());
                
                Iterator<DBRecord>valueIterator=authorizedRecords.iterator();
                while(valueIterator.hasNext()){

                    ArrayList<String>activityList=valueIterator.next().getRecord();
                    String activityID=activityList.get(1).trim();
                    String participationStatus=activityList.get(2).trim();
                    String result=activityList.get(4).trim();
                    
                    if(result.isEmpty()||result.equals(" ")){
                        
                        result="Not declared";
                    }
                    if(participationStatus.equals("Y")){
                    
                        String[] l_pkey={activityID};
                        DBRecord activityManagementRecord=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+"OTHER_ACTIVITY"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity"+i_db_properties.getProperty("FOLDER_DELIMITER")+"OtherActivity","OTHER_ACTIVITY","IVW_OTHER_ACTIVITY", l_pkey, session,dbSession);
                        StudentOtherActivityDetail activityDetail=new StudentOtherActivityDetail();
                        activityDetail.setActivityID(activityID);
                        activityDetail.setActivityName(activityManagementRecord.getRecord().get(3).trim());
                        String type=activityManagementRecord.getRecord().get(4).trim();
                        String activityType;
                        if(type.equals("S")){
                            
                         activityType="Sports";
                            
                        }else {
                            
                            activityType="Culturals";
                        }
                        
                        activityDetail.setActivityType(activityType);
                        
                        String level=activityManagementRecord.getRecord().get(5).trim();
                        String activityLevel;
                        
                        if(level.equals("S")){
                            
                            activityLevel="State";
                        }else if(level.equals("D")){
                            
                            activityLevel="District";
                        }else if(level.equals("I")){
                            
                            activityLevel="International";
                        }else {
                            
                            activityLevel="Internal";
                        }
                        
                        
                        activityDetail.setLevel(activityLevel);
                        activityDetail.setVenue(activityManagementRecord.getRecord().get(6).trim());
                        activityDetail.setDate(activityManagementRecord.getRecord().get(7).trim());
                        activityDetail.setResult(result);
                        
                        
                        dataset.add(activityDetail);


                }
                }
         }catch(DBValidationException ex){

                if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){

                    session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                    session.getErrorhandler().removeSessionErrCode("DB_VAL_011");

                }else{

                    throw ex;
                }


            }  
       
        
        if(dataset.isEmpty()){
            
            StudentOtherActivityDetail activityDetail=new StudentOtherActivityDetail();
                        activityDetail.setActivityID(" ");
                        activityDetail.setActivityName(" ");
                        activityDetail.setActivityType(" ");
                        activityDetail.setLevel(" ");
                        activityDetail.setVenue(" ");
                        activityDetail.setDate(" ");
                        activityDetail.setResult(" ");
                        dataset.add(activityDetail);
        }
        
        

     dbg("end of getStudentOtherActivityDetailObject",session);
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
