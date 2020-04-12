/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassDetails;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class ClassDetails_DataSet {
    
    public ArrayList<ClassDetails> getClassOtherActivity(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<ClassDetails>classDetailsDataSet=new ArrayList();
        
        try{
            
            try{
            
               dbg("inside getClassOtherActivity",session);
               BusinessService bs=appInject.getBusinessService(session);
               IPDataService pds=inject.getPdataservice();
               IBDProperties i_db_properties=session.getCohesiveproperties();
               int noOfStudents=bs.getNoOfStudentsOfTheClass(p_instanceID, p_standard, p_section, session, dbSession, appInject);
               dbg("noOfStudents"+noOfStudents,session);
               ClassDetails classFee=new ClassDetails();
               classFee.setClasss(p_standard+p_section);
               classFee.setTotalNoOfStudents(Integer.toString(noOfStudents));
               String[] l_pkey={p_instanceID,p_standard,p_section};
               ArrayList<String> classConfigMasterRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID+i_db_properties.getProperty("FOLDER_DELIMITER")+p_instanceID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
                    
               String teacherID=classConfigMasterRecord.get(3).trim();
               dbg("teacherID"+teacherID,session);
               String teacherName=bs.getTeacherName(teacherID, p_instanceID, session, dbSession, appInject);
               dbg("teacherName"+teacherName,session);
               classFee.setClassTeacher(teacherName);
               classDetailsDataSet.add(classFee);
               
         }catch(DBValidationException ex){
            
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
            }else{
                throw ex;
            }
            
        }
            
            if(classDetailsDataSet.isEmpty()){
                
                ClassDetails classDetail=new ClassDetails();
                classDetail.setClasss(p_section+p_section);
                classDetail.setClassTeacher(" ");
                classDetail.setTotalNoOfStudents(" ");
                
            }
            
            
        
               dbg("end of getClassOtherActivity",session);
       return classDetailsDataSet;
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
        
    }
    
    
    
    
    
    public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
