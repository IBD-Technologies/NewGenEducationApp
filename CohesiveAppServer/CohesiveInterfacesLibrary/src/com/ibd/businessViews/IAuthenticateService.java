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
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
@Remote
public interface IAuthenticateService {
    public String[] loginAuthenticate(String userid,String pwd) throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException;
   
}
