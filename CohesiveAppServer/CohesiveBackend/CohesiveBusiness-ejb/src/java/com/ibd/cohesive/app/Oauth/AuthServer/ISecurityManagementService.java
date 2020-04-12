/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.AuthServer;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Optional;

/**
 *
 * @author DELL
 */
public interface ISecurityManagementService {
    public   Optional<String> generateSalt (final int length,CohesiveSession session) throws BSValidationException;
    public   Optional<String> hashPassword (String password, String salt,CohesiveSession session)throws BSValidationException ;
    public  boolean verifyPassword (String password, String key, String salt,CohesiveSession session)throws BSValidationException,BSProcessingException;
}
