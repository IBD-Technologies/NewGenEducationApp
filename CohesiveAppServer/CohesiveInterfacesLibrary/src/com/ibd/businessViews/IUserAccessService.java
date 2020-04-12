/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;


import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author IBD Technologies
 */
public interface IUserAccessService {
   public Map<String,ArrayList<String>> entityAccessValidation(String p_userID,String p_service,String p_operation,Map<String,String> p_businessEntity,String p_instituteID)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException;      
   public Map<String,ArrayList<String>> entityAccessValidation(String p_userID,String p_service,String p_operation,Map<String,String> p_businessEntity,String p_instituteID, CohesiveSession session)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException;
   public boolean operationAccessValidation(String p_service,String p_operation,String p_instituteID, Map<String,ArrayList<String>>p_roleMap)throws BSProcessingException,DBValidationException,DBProcessingException;
   public boolean operationAccessValidation(String p_service,String p_operation,String p_instituteID, Map<String,ArrayList<String>>p_roleMap, CohesiveSession session)throws BSProcessingException,DBValidationException,DBProcessingException;
}
