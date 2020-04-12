
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.AuthServer;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
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
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
@Local(IAuthTokenProvider.class)
@Startup
@Singleton
@ConcurrencyManagement(BEAN)
public class AuthTokenProvider implements IAuthTokenProvider {
    
    JWSSigner signer;
   byte[] sharedSecret;
    SecretKey key;
    int jweCounter;
    
private CohesiveSession session;
 DBSession dbSession;
   AppDependencyInjection inject;
    
  public AuthTokenProvider() throws KeyLengthException,EJBException, NamingException
  {
    session =new CohesiveSession();
    dbSession = new DBSession(session);
    session.createSessionObject();
    inject=new AppDependencyInjection();
    //dbSession.createDBsession(session);
     
    
    try {
//HMAC JWT genration starts 
  // Generate random 256-bit (32-byte) shared secret
SecureRandom random = new SecureRandom();

sharedSecret = new byte[64];
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
    finally
    {
    session.clearSessionObject();
        
    } 
      
  }

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(byte[] sharedSecret) {
        this.sharedSecret = sharedSecret;
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

public String[] createAuthToken(String UserId,String InstituteID,String instituteName,CohesiveSession session,DBSession dbSession) throws KeyLengthException, JOSEException, NoSuchAlgorithmException,NamingException,DBValidationException,DBProcessingException
    {
      // Prepare JWT with claims set
   try
   {  
     //session.createSessionObject();
//  dbSession.createDBsession(session);
       String[] token=new String[7]; //i
   Date now = new Date();
   JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
    .subject("AuthToken")
    .issueTime(now)    
    .issuer("https://www.ibdtechnologies.com")
    .claim("UserId",UserId)
    .claim("InstituteID", InstituteID)
    .expirationTime(new Date(new Date().getTime() + 60 * 1000*1440))//24 Hours valid
    .notBeforeTime(now)
    .build();
SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);
// Apply the HMAC protection
signedJWT.sign(signer);

// Serialize to compact form, produces something like
// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
String s = signedJWT.serialize();
  dbg("JWT:"+s,session);

  String sign= s.split("\\.")[2];
  
  dbg("Signature:"+sign,session);
  
  dbg("Token:"+s.split("\\.")[0]+s.split("\\.")[1],session);

    token[0]=s.split("\\.")[0]+"."+s.split("\\.")[1];
    token[1]=sign;
  //HMAC JWT Generation Ends*/
  
  
  // Create the header
JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);

// Set the plain text
Payload payload = new Payload(token[0]);

// Create the JWE object and encrypt it
JWEObject jweObject = new JWEObject(header, payload);
    jweObject.encrypt(new DirectEncrypter(getSymetricKey()));


// Serialise to compact JOSE form...
String jweString = jweObject.serialize();
token[0]=jweString;
  dbg("JWT:"+jweString,session);
token[2]=UserId+"~"+InstituteID+"~"+instituteName;  
       IBDProperties i_db_properties=session.getCohesiveproperties();
       IPDataService pds=inject.getPdataservice();
       String[] l_pkey={UserId};
       ArrayList<String>l_userList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_pkey);
       token[3]=l_userList.get(13).trim();

       dbg("user type-->"+token[3],session);
       
       
       if(!UserId.equals("System")){
       
       
       String[] l_contactPkey={InstituteID};
       
       
       ArrayList<String>l_contactList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",l_contactPkey);

       String language=l_contactList.get(12).trim();
       String plan=l_contactList.get(14).trim();
       String countryCode=l_contactList.get(13).trim();        
       dbg("language-->"+language,session);
       dbg("plan-->"+plan,session);
       dbg("countryCode-->"+countryCode,session);
      
       
       token[4]=language;
       token[5]=plan;
       token[6]=countryCode;
       
       }else{
           
           token[4]="EN";
           token[5]="S";
           token[6]="IN";
           
       }
       
       
  return token;
    }catch(DBValidationException ex){
            throw ex;
      }catch(DBProcessingException ex){
            dbg(ex,session);
            throw ex;
   }
   catch(Exception e)
   {
       dbg(e,session);
       throw e;
   }  
   
    finally{
         //   session.clearSessionObject();
//            dbSession.clearSessionObject();
        }
    }
    
public String createCSRFToken(String UserId,String InstituteID,CohesiveSession session) throws KeyLengthException, JOSEException, NoSuchAlgorithmException
    {
      // Prepare JWT with claims set
   try
   {  
     //session.createSessionObject();
  //dbSession.createDBsession(session);
   Date now = new Date();
   JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
    .subject("CSRFToken")
    .issueTime(now)    
    .issuer("https://www.ibdtechnologies.com")
    .claim("UserId",UserId)
    .claim("InstituteID", InstituteID)
    .expirationTime(new Date(new Date().getTime() + 60 * 1000*1440))//24 Hours valid
    .notBeforeTime(now)
    .build();
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
   catch(Exception e)
   {
       dbg(e,session);
       throw e;
   }  
   
    finally{
            //session.clearSessionObject();
            //dbSession.clearSessionObject();
        }
    }

    
    
    public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }


}
