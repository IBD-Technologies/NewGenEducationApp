/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.app;

import com.ibd.businessViews.IAppDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.app.INSTITUTE_MASTER;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local
//@Stateless
public class INSTITUTE_MASTER_DataSource  extends AppDataSource<INSTITUTE_MASTER>{

    String instanceID;
    
    @Override
      public List<INSTITUTE_MASTER> fetch()
	{
	
            try
            {
                //parseParameters();
                instanceID =this.getLoginInstitute();
                ArrayList<INSTITUTE_MASTER> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IAppDataSet appDataSet=preProcessor.getAppDataset();
                   
                  String result= appDataSet.getINSTITUTE_MASTER_DataSet(instanceID,this.getBusinessReport());
                 
                
                INSTITUTE_MASTER instituteMaster=new INSTITUTE_MASTER(); 
                 resultset= instituteMaster.convertStringToArrayList(result);
                  
 
                   return resultset;
                }else{
                    return null;
                }
            
            
       } catch(NamingException ex){
           return null;
//            throw new EJBException(ex.toString());
       } catch(Exception ex){
           return null;
//              throw new EJBException(ex.toString());
    }
        }     
      
      
      
      
      
    
}
