/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import javax.json.JsonObject;

/**
 *
 * @author IBD Technologies
 */
public interface BusinessEJBTemplate {
    
    
     public  void create()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
        
     public  void authUpdate()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
    
     public  void fullUpdate()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;   
        
     public  void delete()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
     
     public  void view()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
     
     public JsonObject buildJsonResFromBO()throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
     
     public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException;
     
     public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException;
     
//     default public void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{
//         return;
//     }
}
