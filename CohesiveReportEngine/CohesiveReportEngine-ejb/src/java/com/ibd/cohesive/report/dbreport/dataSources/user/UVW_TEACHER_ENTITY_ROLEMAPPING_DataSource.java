/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.user;

import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_TEACHER_ENTITY_ROLEMAPPING;
import com.ibd.businessViews.IUserDataSet;
import com.ibd.cohesive.report.dbreport.dataModels.user.UVW_TEACHER_ENTITY_ROLEMAPPING;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.report.preprocessor.IPreProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class UVW_TEACHER_ENTITY_ROLEMAPPING_DataSource extends UserDataSource<UVW_TEACHER_ENTITY_ROLEMAPPING>{
 
    String userId;
    
    @Override
      public List<UVW_TEACHER_ENTITY_ROLEMAPPING> fetch()
	{
	
            try
            {
                userId =this.getUserInput();
                
                ArrayList<UVW_TEACHER_ENTITY_ROLEMAPPING> resultset=new ArrayList();
                ReportDependencyInjection inject=new ReportDependencyInjection();
                
                IPreProcessor preProcessor=inject.getPreProcessor();
                
                if( preProcessor.preProcessing(this.getNokotser(), this.getUserID(), this.getLoginInstitute(), this.getService())){
                   IUserDataSet appDataSet=preProcessor.getUserDataset();
                   
                  String result= appDataSet.getUVW_TEACHER_ENTITY_ROLEMAPPING_DataSet(userId);
                  
                  UVW_TEACHER_ENTITY_ROLEMAPPING errorMaster=new UVW_TEACHER_ENTITY_ROLEMAPPING();
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
