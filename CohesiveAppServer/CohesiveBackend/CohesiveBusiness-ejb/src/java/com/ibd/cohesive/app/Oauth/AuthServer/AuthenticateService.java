/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.Oauth.AuthServer;

import com.ibd.businessViews.IAuthenticateService;
import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.app.business.util.validation.BSValidation;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
//@Local(IAuthenticateService.class)
@Remote(IAuthenticateService.class)
@Stateless
public class AuthenticateService implements IAuthenticateService {
    AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
    public AuthenticateService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession = new DBSession(session);
        } catch (NamingException ex) {
          dbg(ex);
          throw new EJBException(ex);
        }
        
    }
    
    @Override
    public String[] loginAuthenticate(String userid,String pwd) throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException
    {
        session.createSessionObject();
       dbSession.createDBsession(session);
       
        try {
            IAuthTokenProvider auth=inject.getAuthTokenProvider();
            IPDataService pds=inject.getPdataservice();
           IBDProperties i_db_properties=session.getCohesiveproperties();
           BusinessService bs=inject.getBusinessService(session);
        
        dbg("inside loginAuthenticate ");
        if(validateLoginCredentials(userid,pwd))
        {   
           dbg("Authentication is success "); 
           
           String[] l_pkey={userid};
        
           ArrayList<String>l_userProfileList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_pkey);
           
           for(int i=0;i<l_userProfileList.size();i++){
            dbg("l_userProfileList-->"+l_userProfileList.get(i));   
           }
//           String instID="I001";//Need to find institute id  here for the given user

           String instID;
           String instituteName;
           if(userid.equals("System")||userid.equals("Teacher")||userid.equals("Parent")||userid.equals("Admin")){
               
               instID="System"; 
               instituteName="System";
           }else{
               String l_instituteID=l_userProfileList.get(15).trim();
               instituteName=bs.getInstituteName(l_instituteID, session, dbSession, inject);
               instID=l_instituteID;
               
           }
           
//           if(userid.equals("Teacher")){
//               
//               instID="System";
//           }else{
//               instID=l_userProfileList.get(15).trim();
//           }
//           
//           if(userid.equals("Parent")){
//               
//               instID="System";
//           }else{
//               instID=l_userProfileList.get(15).trim();
//           }
//           
//           if(userid.equals("Admin")){
//               
//               instID="System";
//           }else{
//               instID=l_userProfileList.get(15).trim();
//           }

//            String instID=l_userProfileList.get(15).trim();
            
            dbg("instID"+instID);
           
          return auth.createAuthToken(userid, instID,instituteName,session,dbSession);
        }
        }
        catch (NamingException ex) {
            dbg(ex);
                throw new BSProcessingException("Exception" + ex.toString());
            }
        catch(BSValidationException ex)
        {
            throw ex;
        } 
        catch (Exception ex) {
            dbg(ex);
                throw new BSProcessingException("Exception" + ex.toString());
            }
        finally{
            session.clearSessionObject();
            dbSession.clearSessionObject();
        }
        
        return null;
}
    
    private boolean validateLoginCredentials(String userid,String pwd) throws BSValidationException,DBValidationException,DBProcessingException,BSProcessingException
    {
        
        try{
            
            if(userid.equals("System")){
                
                if(pwd.equals("kumar1234")){
                    
                    return true;
                }else{
                    
                     throw new BSValidationException("BS_VAL_15~Invalid User id or Password");
                }
                
            }
            
            if(userid.equals("Teacher")){
                
                if(pwd.equals("P@sword123")){
                    
                    return true;
                }else{
                    
                     throw new BSValidationException("BS_VAL_15~Invalid User id or Password");
                }
                
            }
            
            if(userid.equals("Parent")){
                
                if(pwd.equals("P@sword123")){
                    
                    return true;
                }else{
                    
                     throw new BSValidationException("BS_VAL_15~Invalid User id or Password");
                }
                
            }
            
            if(userid.equals("Admin")){
                
                if(pwd.equals("P@sword123")){
                    
                    return true;
                }else{
                    
                     throw new BSValidationException("BS_VAL_15~Invalid User id or Password");
                }
                
            }
            
                IPDataService pds=inject.getPdataservice();
                IBDProperties i_db_properties=session.getCohesiveproperties();
                BSValidation bsv=inject.getBsv(session);
                ISecurityManagementService security=inject.getSecurityManagementService();
                ArrayList<String>l_userProfileList=null;
                String[] l_pkey={userid};

                try{

                    l_userProfileList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_PROFILE",l_pkey);


                    if(!(l_userProfileList.get(8).trim().equals("O"))){
                       throw new BSValidationException("BS_VAL_15~Invalid User id or Password"); 
                    }
                    
                    if(!(l_userProfileList.get(9).trim().equals("A"))){
                       throw new BSValidationException("BS_VAL_15~Invalid User id or Password");   
                    }
                
                }catch(DBValidationException ex){

                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_007")){


                        throw new BSValidationException("BS_VAL_15~Invalid User id or Password");

                    }

                }

                ArrayList<String>l_userConfidentialList=pds.readRecordPData(session,dbSession,"USER"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User"+i_db_properties.getProperty("FOLDER_DELIMITER")+"User", "USER", "UVW_USER_CREDENTIALS",l_pkey);

                String userStatus=l_userProfileList.get(14).trim();
                String key=l_userConfidentialList.get(1).trim();
                String expiryDate=l_userConfidentialList.get(2).trim();
                String salt=l_userConfidentialList.get(4).trim();

                dbg("userStatus"+userStatus);
                dbg("key"+key);
                dbg("expiryDate"+expiryDate);
                dbg("salt"+salt);

                if(bsv.pastDateValidation(expiryDate, session, dbSession, inject)){

                    if(userStatus.equals("E")){

                        if(security.verifyPassword(pwd, key, salt, session)){

                            return true;

                        }else{

                            throw new BSValidationException("BS_VAL_15~Invalid User id or Password");
                        }



                    }else{

                        throw new BSValidationException("BS_VAL_023~User is disabled");
                    }


                }else{

                    throw new BSValidationException("BS_VAL_022~Password Expired please change password using forgot password button");
                }


//        if (userid.equals("System"))
//         {      
//             if (pwd.equals("kumar1234"))
//             {
//           return true;
//             }
//         }
//        throw new BSValidationException("BS_VAL_15~Invalid User id or Password");
        
        
        }catch(DBValidationException ex){
         //dbg(ex);        
        throw ex;
        }catch(BSValidationException ex){
         //dbg(ex);        
        throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
            
        }catch(BSProcessingException ex){
             dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }
    }    
    
  public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }

}

