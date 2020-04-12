/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.ResourceServer;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import com.ibd.businessViews.IResourceTokenService;
import com.ibd.cohesive.app.Oauth.AuthServer.TokenValidateService;
import com.ibd.cohesive.util.JWEInput;
import javax.ejb.Remote;

/**
 *
 * @author IBD Technologies
 */
//@Local(IResourceTokenService.class)
@Remote(IResourceTokenService.class)
@Stateless
public class ResourceTokenService implements IResourceTokenService{
   //ResourceTokenProvider provider;
   AppDependencyInjection inject;
   CohesiveSession session;
   //DBSession dbSession;
    public ResourceTokenService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            //dbSession = new DBSession(session);
            //provider=new ResourceTokenProvider();
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
    }
   
    public String getResourceToken(JWEInput jweip,String service) throws BSProcessingException{
    String token =null;
        try
        {
              session.createSessionObject();
//            ITokenValidationService tvs=inject.getTokenValidationService();
            TokenValidateService tvs=inject.getTokenValidationServiceClass();
              if (tvs.validateAuthToken(jweip,session))
              {   
              IResourceTokenProvider provider=inject.getTokenProvider();
              token=  provider.createResourceToken(jweip,service,session);
              }
          }
    catch(Exception ex) {
            dbg(ex);
                throw new BSProcessingException("Exception" + ex.toString());
            }
        finally{
            session.clearSessionObject();
            
        }
       
    return token;
    }
 
    public String getWebResourceToken(JWEInput jweip,String service) throws BSProcessingException{
    String token =null;
        try
        {
              session.createSessionObject();
            //ITokenValidationService tvs=inject.getTokenValidationService();
              //if (tvs.validateAuthToken(jweip,session))
              //{   
              IResourceTokenProvider provider=inject.getTokenProvider();
              token=  provider.createResourceToken(jweip,service,session);
              //}
          }
    catch(Exception ex) {
            dbg(ex);
                throw new BSProcessingException("Exception" + ex.toString());
            }
        finally{
           session.clearSessionObject();
            
        }
       
    return token;
    }
 
    
   public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
    
}
