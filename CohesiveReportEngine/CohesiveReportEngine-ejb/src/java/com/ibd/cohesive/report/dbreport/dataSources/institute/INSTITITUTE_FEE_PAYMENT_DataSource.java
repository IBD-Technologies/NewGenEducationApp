/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.institute;

import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.institute.INSTITITUTE_FEE_PAYMENT;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class INSTITITUTE_FEE_PAYMENT_DataSource extends InstituteDataSource<INSTITITUTE_FEE_PAYMENT>{
 
//    String fileName;
    //String tableName;
    String instanceID;
//    String feeID;

//    public String getFeeID() {
//        return feeID;
//    }
//
//    public void setFeeID(String feeID) {
//        this.feeID = feeID;
//    }

  
    
    @Override
      public List<INSTITITUTE_FEE_PAYMENT> fetch()
	{
	
            try
            {
                //parseParameters();
                
                instanceID =this.getInstituteId();
                String feeID=this.getFeeID();
                ArrayList<INSTITITUTE_FEE_PAYMENT> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IInstituteDataSet appDataSet=preProcessor.getInstituteDataset();
                   
                  String result= appDataSet.getINSTITITUTE_FEE_PAYMENT_DataSet(instanceID,feeID);
                  
                  INSTITITUTE_FEE_PAYMENT errorMaster=new INSTITITUTE_FEE_PAYMENT();
                 resultset= errorMaster.convertStringToArrayList(result);
        
                
                   return resultset;
                }else{
                    return null;
                } 
            
            
            
       } catch(NamingException ex){
           return null;
       } catch(Exception ex){
           return null;
              
    }
        }     
    
}
