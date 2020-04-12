/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.AuthServer;

import com.ibd.businessViews.IAuthTokenBridge;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.crypto.SecretKey;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
@Remote(IAuthTokenBridge.class)
@Stateless
public class AuthTokenBridge implements IAuthTokenBridge {
    AppDependencyInjection inject;
    CohesiveSession session;
       public AuthTokenBridge(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            //dbSession = new DBSession(session);
            //provider=new ResourceTokenProvider();
        } catch (NamingException ex) {
          //dbg(ex);
          throw new EJBException(ex);
        }
    }
 
    @Override
    public SecretKey getKey() throws NamingException {
        
        IAuthTokenProvider tokenProvider = inject.getAuthTokenProvider();
        
        return tokenProvider.getKey();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    @Override
    public JWSSigner getSigner() throws NamingException{
       IResourceTokenProvider tokenProvider = inject.getTokenProvider();
        
       return tokenProvider.getSigner();

         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
  
    @Override
    public byte[] getSharedSecret() throws NamingException {
        IAuthTokenProvider tokenProvider = inject.getAuthTokenProvider();
        return tokenProvider.getSharedSecret();


          //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setKey(SecretKey key) throws NamingException{
        IAuthTokenProvider tokenProvider = inject.getAuthTokenProvider();
        tokenProvider.setKey(key);


         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/*
    @Override
    public void setSigner(JWSSigner signer) throws NamingException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    IResourceTokenProvider tokenProvider = inject.getTokenProvider();
        tokenProvider.setSigner(signer);

    
    }
*/
    @Override
    public void setSharedSecret(byte[] sharedsecret) throws NamingException{
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    IAuthTokenProvider tokenProvider = inject.getAuthTokenProvider();
        tokenProvider.setSharedSecret(sharedsecret);

    
    }
 public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    } 
    
}
