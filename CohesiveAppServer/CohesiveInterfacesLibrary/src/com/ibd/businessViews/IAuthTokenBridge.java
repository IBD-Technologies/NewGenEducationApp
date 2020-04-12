/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import javax.crypto.SecretKey;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public interface IAuthTokenBridge {
    public SecretKey getKey() throws NamingException;
     //public JWSSigner getSigner() throws NamingException;
     public byte[] getSharedSecret()throws NamingException;

     public void setKey(SecretKey key) throws NamingException;
     //public void setSigner(JWSSigner signer)throws NamingException;
     public  void setSharedSecret(byte[] sharedsecret)throws NamingException ;

}
