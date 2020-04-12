/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.util.validation;

import com.ibd.businessViews.ITokenValidationService;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class ReportValidation {
    Debug debug;
    IBDProperties i_db_properties;

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    public IBDProperties getI_db_properties() {
        return i_db_properties;
    }

    public void setI_db_properties(IBDProperties i_db_properties) {
        this.i_db_properties = i_db_properties;
    }
    
    
    public boolean dateValidation(String p_date)throws DBProcessingException{
        try{
        String dateFormat=  i_db_properties.getProperty("DOB_FORMAT");
        SimpleDateFormat formatter=new SimpleDateFormat(dateFormat);
        Date dateForSubstituteAvailability= formatter.parse(p_date);
        
        Date date1 = new Date();     
        String l_currentDateString=formatter.format(date1);
        Date l_currentDate=formatter.parse(l_currentDateString);
        
        if(dateForSubstituteAvailability.compareTo(l_currentDate)<1){
            
            return false;
            
        } else{
            
            return true;
        }
            
            
         }catch(Exception ex){
         throw new DBProcessingException("DBProcessingException"+ex.toString());
        }
        
    }
    
    
     public boolean ResourceTokenValidation(String token,String userid,String instid,String service,ReportDependencyInjection inject )throws BSProcessingException,BSValidationException {
    boolean status=false;
    
    try{
        JWEInput jweinput = new JWEInput(token,userid,instid,"");
            ITokenValidationService tknval=inject.getTokenValidationService();
            
            if(tknval.validateRestToken(jweinput,service))
              status=true;
            else
                if(jweinput.isExpired()){
                    
                    throw new BSValidationException("BS_VAL_026");
                    
                }else{
               throw new BSValidationException("BS_VAL_101");  
                }
    }   catch (BSValidationException ex) {
            dbg(ex);
            throw ex;   
    }   catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());

        }
        
        return status; 
} 
     
     
     public void dbg(String p_Value) {

        this.debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        this.debug.exceptionDbg(ex);

    }
}
