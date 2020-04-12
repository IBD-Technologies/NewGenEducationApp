 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.ResourceServer;

import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;

/**
 *
 * @author IBD Technologies
 */
public interface IResourceTokenProvider {
//     public String createResourceToken(JWEInput jweip,String Service) throws KeyLengthException, JOSEException, NoSuchAlgorithmException;
     public String createResourceToken(JWEInput jweip,String service,CohesiveSession session) throws KeyLengthException, JOSEException, NoSuchAlgorithmException;
     public SecretKey getKey();
    public JWSSigner getSigner();
   public byte[] getSharedSecret();
     public void setSharedSecret(byte[] sharedSecret);
     public void setKey(SecretKey key);
     public void setSigner(JWSSigner signer);
}
