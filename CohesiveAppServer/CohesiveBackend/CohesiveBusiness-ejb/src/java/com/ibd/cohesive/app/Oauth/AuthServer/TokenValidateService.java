/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.AuthServer;

import com.ibd.cohesive.util.JWEInput;
import com.ibd.businessViews.ITokenValidationService;
import com.ibd.cohesive.app.Oauth.ResourceServer.IResourceTokenProvider;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local(ITokenValidationService.class)
@Remote(ITokenValidationService.class)

@Stateless
public class TokenValidateService implements ITokenValidationService {

AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    public TokenValidateService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }


    public Boolean validateRestToken(JWEInput jweip,String service) throws BSProcessingException{
    Boolean l_session_created_now =false;
        
        try {   
        
        session.createSessionObject();
        dbg("Inside validateRestToken"); 
        l_session_created_now=session.isI_session_created_now();
        IResourceTokenProvider auth=inject.getTokenProvider();
        JWEObject jweObject = JWEObject.parse(jweip.getToken());
        jweObject.decrypt(new DirectDecrypter(auth.getKey()));
        Payload payload = jweObject.getPayload();
        SignedJWT signedJWT = SignedJWT.parse(payload.toString());
        JWSVerifier verifier = new MACVerifier(auth.getSharedSecret()); 
        
        if(signedJWT.verify(verifier))
        {
            
            if(signedJWT.getJWTClaimsSet().getSubject().equals("ResourceToken"))
            {
               dbg("Inside ResourceToken");    
                if(signedJWT.getJWTClaimsSet().getIssuer().equals("https://www.ibdtechnologies.com"))
                   {
                   if(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()))
                   {
                       dbg("Inside ResourceToken not Expired"); 
                    if(new Date().after(signedJWT.getJWTClaimsSet().getNotBeforeTime()))
                    {  
                      if(signedJWT.getJWTClaimsSet().getClaims().get("UserId").toString().equals(jweip.getUserid()))
                      {
                          dbg("Inside ResourceToken valid userid");
                          dbg("getJWTClaimsSet InstituteID"+signedJWT.getJWTClaimsSet().getClaims().get("InstituteID").toString());
                          dbg("jweip.getInstid()"+jweip.getInstid());
                        if(signedJWT.getJWTClaimsSet().getClaims().get("InstituteID").toString().equals(jweip.getInstid()))
                        {
                            
                            dbg("Inside ResourceToken valid InstituteID");
                        if(service.contains("Search")||service.contains("SelectBoxMaster")||service.equals("DashBoardService"))
                         {
                             //if((signedJWT.getJWTClaimsSet().getClaims().get("Service").toString().equals("CohesiveMain")))
                             //{
                                 dbg("Inside ResourceToken valid Search Service");
                         JWEInput jwe=new JWEInput(signedJWT.getJWTClaimsSet().getClaims().get("token").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("UserId").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("InstituteID").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("seckey").toString());
                                    
                          if(validateAuthToken(jwe,session)) 
                          return true;  
                          else if (jwe.isInstituteChanged())
                           return true; 
                            // }  
                         }   
                       else
                        {    
                        if(signedJWT.getJWTClaimsSet().getClaims().get("Service").toString().equals(service))
                        {
                             dbg("Inside ResourceToken valid Service");
                         JWEInput jwe=new JWEInput(signedJWT.getJWTClaimsSet().getClaims().get("token").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("UserId").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("InstituteID").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("seckey").toString());
                                    
                          if(validateAuthToken(jwe,session)) 
                          return true;
                          else if (jwe.isInstituteChanged())
                           return true;    
                        }
                        }
                        } 
                        else
                        {
                                dbg("Inside ResourceToken valid InstituteID");
                        if(service.contains("SelectBoxMaster")||service.contains("Search")||service.equals("DashBoardService"))
                         {
                             //if((signedJWT.getJWTClaimsSet().getClaims().get("Service").toString().equals("CohesiveMain")))
                             //{
                                 dbg("Inside ResourceToken valid Search Service");
                         JWEInput jwe=new JWEInput(signedJWT.getJWTClaimsSet().getClaims().get("token").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("UserId").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("InstituteID").toString(),
                                                   signedJWT.getJWTClaimsSet().getClaims().get("seckey").toString());
                                    
                          if(validateAuthToken(jwe,session)) 
                          return true;  
                          else if (jwe.isInstituteChanged())
                           return true; 
                            // }  
                         }   
                       
                            
                        }
         
                    }
                   }
                  else
                    {
                        jweip.setExpired(true);
                       // throw new BSValidationException("BS_VAL_103~Resource Token is Expi"); 
    
                    } 
        }else{
                       jweip.setExpired(true);
                   }
                   }
        }
        }
    } catch (ParseException ex) {
        dbg(ex);
    throw new BSProcessingException(ex.toString()); 
    } catch (JOSEException ex) {
        dbg(ex);
    throw new BSProcessingException(ex.toString()); 
     
    } catch (NamingException ex) {
        dbg(ex);
   throw new BSProcessingException(ex.toString()); 
     }
    finally
    {
         if(l_session_created_now){
          
       session.clearSessionObject(); 
         }
         }  
        return false;
        }
    
 @Override
    public Boolean validateRestToken(JWEInput jweip,String service,CohesiveSession session) throws BSProcessingException{
    CohesiveSession tempSession = this.session;
    boolean flag=false;
    try{
        this.session=session;
        flag =validateRestToken(jweip,service);
     }  
      finally{
           this.session=tempSession;
        }
 return flag;
    }
    

private String getSafeToken() throws NamingException {
    //SecureRandom random = new SecureRandom();
    //byte bytes[] = new byte[20];
    //random.nextBytes(bytes);
    IAuthTokenProvider auth=inject.getAuthTokenProvider();
    Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    String token = encoder.encodeToString(auth.getSharedSecret());
    return token;
}     
    
    
    
public Boolean validateAuthToken(JWEInput jweip) throws BSProcessingException{
     Boolean l_session_created_now =false;
        
        try {   
        
        session.createSessionObject();
        l_session_created_now=session.isI_session_created_now();
      dbg("Inside Validate Auth Token");
       IAuthTokenProvider auth=inject.getAuthTokenProvider();
        JWEObject jweObject = JWEObject.parse(jweip.getToken());
        jweObject.decrypt(new DirectDecrypter(auth.getKey()));
        Payload payload = jweObject.getPayload();
        SignedJWT signedJWT = SignedJWT.parse(payload.toString()+"."+jweip.getSecKey());
       // Base64.Decoder decoder = Base64.getDecoder();
        JWSVerifier verifier = new MACVerifier(auth.getSharedSecret());
       // JWSVerifier verifier = new MACVerifier(decoder.decode(jweip.getSecKey()));
        if(signedJWT.verify(verifier))
        {
            if(signedJWT.getJWTClaimsSet().getSubject().equals("AuthToken"))
            {
                dbg("Subject is valid");
                if(signedJWT.getJWTClaimsSet().getIssuer().equals("https://www.ibdtechnologies.com"))
                   {
                   dbg("Issuer is valid");
                
                       if(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()))
                   {
                     dbg("Expire Time is valid");
                    
                    if(new Date().after(signedJWT.getJWTClaimsSet().getNotBeforeTime()))
                    {  
                      if(signedJWT.getJWTClaimsSet().getClaims().get("UserId").toString().equals(jweip.getUserid()))
                      {
                          dbg("User ID is valid");
                          
                          dbg("signedJWT.getJWTClaimsSet().getClaims() instituteID"+signedJWT.getJWTClaimsSet().getClaims().get("InstituteID").toString());
                          
                          
                        if(signedJWT.getJWTClaimsSet().getClaims().get("InstituteID").toString().equals(jweip.getInstid()))
                        {
                          
                            dbg("Inst Id is valid");
                            return true;  
                        }
                        else
                            dbg("Inst Id is changed");
                          jweip.setInstituteChanged(true);
                        }
                        
                        //return true;
         
                    }
                   }
                  else
                    {
                        jweip.setExpired(true);
                    
                    } 
        }   }
        }
      
    } catch (ParseException ex) {
        dbg(ex);
    throw new BSProcessingException(ex.toString()); 
           
    } catch (JOSEException ex) {
        dbg(ex);
    throw new BSProcessingException(ex.toString()); 
     
    } catch (NamingException ex) {
        dbg(ex);
   throw new BSProcessingException(ex.toString()); 
      }
    catch(Exception ex)
    {
        dbg(ex);
   throw new BSProcessingException(ex.toString()); 
        }    
     finally
    {
        if(l_session_created_now)
       session.clearSessionObject(); 
    }  
        return false;
        }


@Override
    public Boolean validateAuthToken(JWEInput jweip,CohesiveSession session) throws BSProcessingException{
    CohesiveSession tempSession = this.session;
    boolean flag=false;
    try{
        this.session=session;
        flag =validateAuthToken(jweip);
     }  
      finally{
           this.session=tempSession;
        }
 return flag;
    }
 


    
  public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
