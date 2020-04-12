/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.index;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;

/**
 *
 * @author IBD Technologies
 */
public interface IIndexReadService {
    
  int readIndex(String p_fileName ,String p_tableid, String p_pkey)throws DBValidationException ,DBProcessingException ;  //It helps to read the position of particual table record from index 
  int readIndex(String p_fileName ,String p_tableid, String p_pkey , CohesiveSession session)throws DBValidationException ,DBProcessingException ;  //It helps to read the position of particual table record from index 
    
}
