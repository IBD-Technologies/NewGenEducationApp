/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.ResourceServer;

import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author IBD Technologies
 */
@Local(IResourceTokenProvider.class)
@Startup
@Singleton
@ConcurrencyManagement(BEAN)

public class ResourceTokenProvider implements IResourceTokenProvider {
    
    JWSSigner signer;
    SecretKey key;
    int jweCounter;
    byte[] sharedSecret = new byte[64];

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(byte[] sharedSecret) {
        this.sharedSecret = sharedSecret;
    }
     private CohesiveSession session;
   
    
  public ResourceTokenProvider() throws KeyLengthException,EJBException
  {
    session =new CohesiveSession();
    try {
//HMAC JWT genration starts 
  // Generate random 256-bit (32-byte) shared secret
  session.createSessionObject();
SecureRandom random = new SecureRandom();


random.nextBytes(sharedSecret);

        
            // Create HMAC signer
            signer = new MACSigner(sharedSecret);
   
            key=createKey();
            
            
    
    
    } catch (KeyLengthException ex) {
            dbg(ex,session);
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw (new EJBException());
        }
  finally{
        session.clearSessionObject();
    }
      
  }

    
    public JWSSigner getSigner() {
        return signer;
    }
    
    public SecretKey getKey() {
        return key;
}
    public void setKey(SecretKey key) {
        this.key=key;
}
    public void setSigner(JWSSigner signer) {
        this.signer = signer;
    }
    
private SecretKey createKey() throws NoSuchAlgorithmException
    {
    // Generate symmetric 128 bit AES key
KeyGenerator keyGen = KeyGenerator.getInstance("AES");
keyGen.init(128);
SecretKey lkey = keyGen.generateKey();
      return  lkey;
    }
    
private  synchronized SecretKey getSymetricKey() throws NoSuchAlgorithmException
    {
        jweCounter++;
        
     if(jweCounter<= Math.pow(2, 32)) {
     return key;    
     }
     else
     {
      key=createKey();
      return key;
     }
    }


    public String createResourceToken(JWEInput jweip,String service,CohesiveSession session) throws KeyLengthException, JOSEException, NoSuchAlgorithmException 
        {
      // Prepare JWT with claims set
        Date now = new Date();
      boolean l_session_created_now=false;
      try
      {
      //session.createSessionObject();
      //l_session_created_now=session.isI_session_created_now();
      JWTClaimsSet claimsSet=null;
          
          if(service.equals("CohesiveMain")&&(jweip.getUserid().equals("System")||jweip.getUserid().equals("Admin")||jweip.getUserid().equals("Parent")||jweip.getUserid().equals("Teacher")))
           {
              claimsSet = new JWTClaimsSet.Builder()
    .subject("ResourceToken")
    .issueTime(now)    
    .issuer("https://www.ibdtechnologies.com")
    .claim("UserId",jweip.getUserid())
    //.claim("InstituteID", jweip.getInstid())
    .claim("InstituteID", "System")                  
    .claim("Service","InstituteSearchService")
    .claim("token", jweip.getToken())
    .claim("seckey",jweip.getSecKey())    
    .expirationTime(new Date(new Date().getTime() + 60 * 1000*1440))//24 hours valid
    .notBeforeTime(now)
    .build(); 

    }else if(service.equals("CohesiveMain")&&!(jweip.getUserid().equals("System")||jweip.getUserid().equals("Admin")||jweip.getUserid().equals("Parent")||jweip.getUserid().equals("Teacher"))){       
     
        claimsSet = new JWTClaimsSet.Builder()
    .subject("ResourceToken")
    .issueTime(now)    
    .issuer("https://www.ibdtechnologies.com")
    .claim("UserId",jweip.getUserid())
    .claim("InstituteID", jweip.getInstid())
//    .claim("InstituteID", "System")                  
    .claim("Service","InstituteSearchService")
    .claim("token", jweip.getToken())
    .claim("seckey",jweip.getSecKey())    
    .expirationTime(new Date(new Date().getTime() + 60 * 1000*1440))//24 hours valid
    .notBeforeTime(now)
    .build(); 
        
        
    }else  
          {  
claimsSet = new JWTClaimsSet.Builder()
    .subject("ResourceToken")
    .issueTime(now)    
    .issuer("https://www.ibdtechnologies.com")
    .claim("UserId",jweip.getUserid())
    .claim("InstituteID", jweip.getInstid())
    .claim("Service",service)
    .claim("token", jweip.getToken())
    .claim("seckey",jweip.getSecKey())    
    .expirationTime(new Date(new Date().getTime() + 60 * 1000*30))//30 Mins valid
    .notBeforeTime(now)
    .build();
         }
SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);
// Apply the HMAC protection
signedJWT.sign(signer);

// Serialize to compact form, produces something like
// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
String s = signedJWT.serialize();
  dbg("JWT:"+s,session);
  //HMAC JWT Generation Ends*/
  
  // Create the header
JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);

// Set the plain text
Payload payload = new Payload(s);

// Create the JWE object and encrypt it
JWEObject jweObject = new JWEObject(header, payload);
    jweObject.encrypt(new DirectEncrypter(getSymetricKey()));


// Serialise to compact JOSE form...
String jweString = jweObject.serialize();

 dbg("JWT:"+jweString,session);
  return jweString;
        }
    finally
    {
            //if(l_session_created_now){
            //session.clearSessionObject();
            
            }
            
            }
        

  /*  @Override
    public String createResourceToken(JWEInput jweip,String service,CohesiveSession session) throws KeyLengthException, JOSEException, NoSuchAlgorithmException{
    CohesiveSession tempSession = this.session;
    String token;
    try{
        this.session=session;
        token =createResourceToken(jweip,service);
     }  
      finally{
           this.session=tempSession;
        }
 return token;
    }*/

    
    
private void dbg(String p_Value,CohesiveSession session){

        session.getDebug().dbg(p_Value);

    }

    private void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }



}
