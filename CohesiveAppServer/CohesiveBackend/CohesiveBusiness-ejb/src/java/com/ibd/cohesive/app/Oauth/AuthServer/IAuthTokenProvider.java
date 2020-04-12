/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.AuthServer;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public interface IAuthTokenProvider {
    public String[] createAuthToken(String UserId,String InstituteID,String instituteName,CohesiveSession session,DBSession dbSession) throws KeyLengthException, JOSEException, NoSuchAlgorithmException,NamingException,DBValidationException,DBProcessingException;
    public SecretKey getKey();
    public JWSSigner getSigner();
    public byte[] getSharedSecret();
     public void setSharedSecret(byte[] sharedSecret);
     public void setKey(SecretKey key);
     public void setSigner(JWSSigner signer);
}
