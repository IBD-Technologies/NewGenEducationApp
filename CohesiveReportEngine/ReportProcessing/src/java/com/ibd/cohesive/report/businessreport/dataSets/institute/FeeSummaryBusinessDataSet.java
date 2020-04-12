/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.classEntity.ClassFeeDetail;
import com.ibd.cohesive.report.businessreport.dataModels.institute.FeeDetailBusiness;
import com.ibd.cohesive.report.businessreport.dataModels.institute.FeeSummaryBusiness;
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
 * @author ibdtech
 */
public class FeeSummaryBusinessDataSet {
    public ArrayList<FeeSummaryBusiness> getFeeSummaryBusinessObject(String p_standard,String p_section,String p_studentID,String p_feeID,String p_instanceID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws DBProcessingException,DBValidationException{
        ArrayList<FeeSummaryBusiness>dataset=new ArrayList();
        
        try{
        
        dbg("inside getFeeSummaryBusinessObject",session);    
//        ClassDataSetBusiness classDataSetBusiness= inject.getClassDatasetBusiness();
        FeeDetailBusinessDataSet classDetails=inject.getFeeDetailBusinessDataSet();
        ArrayList<FeeDetailBusiness>classFeeList=  classDetails.getFeeDetailBusiness(p_standard, p_section, p_studentID, p_feeID, p_instanceID, session, dbSession, inject, appInject);
        dbg("classFeeList size"+classFeeList.size(),session);
        
        int k=1;
        if(classFeeList.size()==1&&classFeeList.get(0).getFeeID().equals(" ")){
            
            dbg("classFeeList is empty",session);
            
        }else{
        
        
        Map<String,List<FeeDetailBusiness>> feeIDGroup=classFeeList.stream().collect(Collectors.groupingBy(rec->rec.getFeeID()));
        dbg("feeIDGroup size"+feeIDGroup.size(),session);
        Iterator<String>feeIDIterator=feeIDGroup.keySet().iterator();
         
        while(feeIDIterator.hasNext()){
            dbg("inside feeTypeIteration",session);
            String feeID=feeIDIterator.next();
            
            dbg("feeID"+feeID,session);
            FeeSummaryBusiness feeSummary=new FeeSummaryBusiness();
            feeSummary.setFeeID(feeID);
            List<FeeDetailBusiness>feeList=feeIDGroup.get(feeID);
            dbg("feeList"+feeList.size(),session);
            String feeType=feeList.get(0).getFeeType();
            String feeDescription=feeList.get(0).getFeeDescription();
            String dueDate=feeList.get(0).getDueDate();
            
            float totalFeeAmount=0;
            float totalPaidAount=0;
            for(int i=0;i<feeList.size();i++){
                FeeDetailBusiness classDetail=feeList.get(i);
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
            feeSummary.setFeeDescription(feeDescription);
            feeSummary.setDueDate(dueDate);
            feeSummary.setFeeType(feeType);
            feeSummary.setSerialNumber(Integer.toString(k));
            dataset.add(feeSummary);
            k++;
        }

        }
        
        
        
        if(dataset.isEmpty()){
            
             FeeSummaryBusiness feeSummary=new FeeSummaryBusiness();
            feeSummary.setFeeType(" ");
            feeSummary.setTotalFeeAmount(" ");
            feeSummary.setTotalPaidAmount(" ");
            feeSummary.setTotalBalanceAmount(" ");
            feeSummary.setFeeID(" ");
            feeSummary.setFeeDescription(" ");
            feeSummary.setDueDate(" ");
            feeSummary.setSerialNumber(" ");
            dataset.add(feeSummary);
            
        }
        
        
        dbg("end of getFeeSummaryBusinessObject",session);
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
