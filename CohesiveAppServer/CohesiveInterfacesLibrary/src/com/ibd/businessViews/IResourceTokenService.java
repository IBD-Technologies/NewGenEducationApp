/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.exceptions.BSProcessingException;

/**
 *
 * @author IBD Technologies
 */

public interface IResourceTokenService {
    public String getResourceToken(JWEInput jweip,String Service) throws BSProcessingException;
    public String getWebResourceToken(JWEInput jweip,String Service) throws BSProcessingException;
 
}
