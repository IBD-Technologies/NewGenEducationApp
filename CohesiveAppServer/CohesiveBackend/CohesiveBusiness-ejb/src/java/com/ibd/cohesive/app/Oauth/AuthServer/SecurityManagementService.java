/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.AuthServer;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.Local;
import javax.ejb.Singleton;

/**
 *
 * @author DELL
 */
@Local(ISecurityManagementService.class)
@Singleton
@ConcurrencyManagement(BEAN)

public class SecurityManagementService implements ISecurityManagementService{
   
    
    private static final SecureRandom RAND = new SecureRandom();

  public synchronized  Optional<String> generateSalt (final int length,CohesiveSession session) throws BSValidationException{

    if (length < 1) {
      session.getErrorhandler().log_app_error("BS_VAL_021", null);
      throw new BSValidationException("BSValidationException");
//      return Optional.empty();
    }

    byte[] salt = new byte[length];
    RAND.nextBytes(salt);

    return Optional.of(Base64.getEncoder().encodeToString(salt));
  }
  
  
   private static final int ITERATIONS = 65536;
  private static final int KEY_LENGTH = 512;
  private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

  public synchronized  Optional<String> hashPassword (String password, String salt,CohesiveSession session)throws BSValidationException {

      dbg("inside hashPassword",session);
      dbg("salt--->"+salt,session);
      
    char[] chars = password.toCharArray();
    byte[] bytes = salt.getBytes();

    PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

    Arrays.fill(chars, Character.MIN_VALUE);

    try {
      SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
      byte[] securePassword = fac.generateSecret(spec).getEncoded();
      return Optional.of(Base64.getEncoder().encodeToString(securePassword));

    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      session.getErrorhandler().log_app_error("BS_VAL_021", null);
      throw new BSValidationException("BSValidationException");

    } finally {
      spec.clearPassword();
    }
  }

  
  
  public  boolean verifyPassword (String password, String key, String salt,CohesiveSession session)throws BSValidationException,BSProcessingException {
  Optional<String> optEncrypted;
      try{
      
       dbg("inside verifyPassword ",session);   
       dbg("password"+password,session);   
       dbg("salt"+salt,session);   
       
      optEncrypted = hashPassword(password, salt,session);
      
      dbg("optEncrypted"+optEncrypted,session);
      dbg("end of verifyPassword ",session);
      }catch(BSValidationException ex){
            throw ex;
      } catch (Exception ex) {
          dbg(ex,session);
            throw new BSProcessingException("Exception".concat(ex.toString()));
      }
    if (!optEncrypted.isPresent()) return false;
        return optEncrypted.get().equals(key);
  }
  
  
  
   public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
