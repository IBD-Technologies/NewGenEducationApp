/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;

/**
 *
 * @author IBD Technologies
 */
public interface ITokenValidationService {
   public Boolean validateAuthToken(JWEInput jweip) throws BSProcessingException;
    public Boolean validateRestToken(JWEInput jweip,String service) throws BSProcessingException;
   public Boolean validateRestToken(JWEInput jweip,String service,CohesiveSession session) throws BSProcessingException;
   public Boolean validateAuthToken(JWEInput jweip,CohesiveSession session) throws BSProcessingException;
   //public Boolean validateRestAuthToken(JWEInput jweip,CohesiveSession session) throws BSProcessingException;
   
}
