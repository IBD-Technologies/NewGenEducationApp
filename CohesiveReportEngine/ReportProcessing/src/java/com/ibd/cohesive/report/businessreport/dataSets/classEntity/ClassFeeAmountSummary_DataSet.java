/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.classEntity;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeAmountSummary;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeDetail;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
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
public class ClassFeeAmountSummary_DataSet {
    
    public ArrayList<ClassFeeAmountSummary> getClassFeeAmountSummaryObject(String p_standard,String p_section,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<ClassFeeAmountSummary>dataset=new ArrayList();
        
        try{
        
        dbg("inside getClassFeeAmountSummaryObject",session);    
//        ClassDataSetBusiness classDataSetBusiness= inject.getClassDatasetBusiness();
        ClassFeeDetail_DataSet classDetails=inject.getClassFeeDetail();
        ArrayList<ClassFeeDetail>classFeeList=  classDetails.getClassOtherActivity(p_standard,p_section, p_instanceID, session, dbSession, inject,appInject);
        dbg("classFeeList size"+classFeeList.size(),session);
        
        
        if(classFeeList.size()==1&&classFeeList.get(0).getFeeID().equals(" ")){
            
            dbg("classFeeList is empty",session);
            
        }else{
        
        
        Map<String,List<ClassFeeDetail>> feeTypeGroup=classFeeList.stream().collect(Collectors.groupingBy(rec->rec.getFeeType()));
        dbg("feeTypeGroup size"+feeTypeGroup.size(),session);
        Iterator<String>feeTypeIterator=feeTypeGroup.keySet().iterator();
         
        while(feeTypeIterator.hasNext()){
            dbg("inside feeTypeIteration",session);
            String feeType=feeTypeIterator.next();
            dbg("feeType"+feeType,session);
            ClassFeeAmountSummary feeSummary=new ClassFeeAmountSummary();
            feeSummary.setFeeType(feeType);
            List<ClassFeeDetail>feeList=feeTypeGroup.get(feeType);
            dbg("feeList"+feeList.size(),session);
            
            float totalFeeAmount=0;
            float totalPaidAount=0;
            for(int i=0;i<feeList.size();i++){
                ClassFeeDetail classDetail=feeList.get(i);
                float feeAmount=Float.parseFloat(classDetail.getFeeAmount());
                float paidAmount=Float.parseFloat(classDetail.getPaidAmount());
                totalFeeAmount=totalFeeAmount+feeAmount;
                totalPaidAount=totalPaidAount+paidAmount;
            }
            dbg("totalPaidAount"+totalPaidAount,session);
            dbg("totalFeeAmount"+totalFeeAmount,session);
            float totalBalanceAmount=totalFeeAmount-totalPaidAount;
            dbg("totalBalanceAmount"+totalBalanceAmount,session);
            feeSummary.setTotalFeeAmount(Float.toString(totalFeeAmount));
            feeSummary.setTotalPaidAmount(Float.toString(totalPaidAount));
            feeSummary.setTotalBalanceAmount(Float.toString(totalBalanceAmount));
            dataset.add(feeSummary);
        }

        }
        
        
        
        if(dataset.isEmpty()){
            
             ClassFeeAmountSummary feeSummary=new ClassFeeAmountSummary();
            feeSummary.setFeeType(" ");
            feeSummary.setTotalFeeAmount(" ");
            feeSummary.setTotalPaidAmount(" ");
            feeSummary.setTotalBalanceAmount(" ");
            dataset.add(feeSummary);
            
        }
        
        
        dbg("end of getClassFeeAmountSummaryObject",session);
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
