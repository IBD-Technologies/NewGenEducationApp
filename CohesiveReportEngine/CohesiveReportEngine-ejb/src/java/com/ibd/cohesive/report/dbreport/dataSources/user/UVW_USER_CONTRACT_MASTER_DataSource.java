/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.user;

import com.ibd.businessViews.IUserDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_USER_CONTRACT_MASTER;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ibdtech
 */
public class UVW_USER_CONTRACT_MASTER_DataSource extends UserDataSource<UVW_USER_CONTRACT_MASTER>{
 
    String userId;
    
    @Override
      public List<UVW_USER_CONTRACT_MASTER> fetch()
	{
	
            try
            {
              userId =this.getUserInput();
                
                ArrayList<UVW_USER_CONTRACT_MASTER> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IUserDataSet appDataSet=preProcessor.getUserDataset();
                   
                  String result= appDataSet.getUVW_USER_CONTRACT_MASTER_DataSet(userId);
                  
                  UVW_USER_CONTRACT_MASTER errorMaster=new UVW_USER_CONTRACT_MASTER();
                 resultset= errorMaster.convertStringToArrayList(result);
        
                
                   return resultset;
                }else{
                    return null;
                } 
            
       } catch(NamingException ex){
           return null;
//             throw new EJBException();
       } catch(Exception ex){
           return null;
              
    }
        }     
    
}
