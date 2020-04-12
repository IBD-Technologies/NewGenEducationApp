/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.App;

import com.ibd.businessViews.IAuthenticateService;
import com.ibd.businessViews.IResourceTokenService;
import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.WebDI.DependencyInjection;
import com.ibd.cohesive.web.Gateway.util.WebUtility;
import java.math.BigDecimal;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author DELL
 */
public class AppResource {

    private String ServiceName;
    private CohesiveSession session;
    private DependencyInjection inject;
    private WebUtility webutil;

    /**
     * Creates a new instance of AppResource
     */
    private AppResource(String ServiceName) throws NamingException {
        this.ServiceName = ServiceName;
        session = new CohesiveSession("gateway.properties");
        inject = new DependencyInjection();
        webutil=new WebUtility();
    }

    /**
     * Get instance of the AppResource
     */
    public static AppResource getInstance(String ServiceName) throws NamingException {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of AppResource class.
        return new AppResource(ServiceName);
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.web.Gateway.App.AppResource
     * @return an instance of javax.json.JsonObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AppResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
    public JsonObject AppGateway(JsonObject content) {
//        JsonObject response =null;
        JsonObjectBuilder response=Json.createObjectBuilder();
        try {
            session.createSessionObject();
            dbg("Inside app entity Gateway Session ID:"+session.getI_session_identifier());
            dbg("Request: " + content.toString());
            
            dbg("ServiceName"+ServiceName);
            JsonArray success = Json.createArrayBuilder().add(Json.createObjectBuilder().add("errorCode", "SUCCESS")
                    .add("errorMessage", "SUCCESSFULLY PROCESSED")).build();
            if(ServiceName.equals("LoginAuthenticate")){
             
                String userID=content.getString("userID");
                String password=content.getString("password");
                dbg("userID"+userID);
                dbg("password"+password);
                String[] authTokenArr=null;
                boolean comeOutLoop = false;
            int iteratonCount = 0;
            while (comeOutLoop == false) {
                try {
                    iteratonCount = iteratonCount + 1;
                IAuthenticateService authenticate=inject.getAuthenticateService();
                
                 authTokenArr=authenticate.loginAuthenticate(userID, password);
                
                dbg("authTokenArr"+authTokenArr.length);
                comeOutLoop = true;
                }catch(BSValidationException ex){
                 throw ex;
             }catch(BSProcessingException ex){
                 throw ex;    
//             }catch(DBValidationException ex){
//                 throw ex;
//             }catch(DBProcessingException ex){
//                 throw ex;
                } catch (EJBException ex) {

                    if (iteratonCount <= 10) {

                        inject = null;
                        Thread.sleep(3000);
                        inject = new DependencyInjection();

                    } else {
                        throw ex;
                    }
                } 
//                catch (Exception ex) {
//
//                    if (iteratonCount <= 10) {
//                        inject = null;
//                        Thread.sleep(3000);
//                        inject = new DependencyInjection();
//
//                    } else {
//                        throw ex;
//                    }
//                }

            }
                
                for(String tokens:authTokenArr){
                    dbg("tokens"+tokens);
                    
                }
                
                response.add("token0", authTokenArr[0])
                        .add("token1", authTokenArr[1])
                        .add("token2", authTokenArr[2])
                        .add("userType", authTokenArr[3])
                        .add("language", authTokenArr[4])
                        .add("plan", authTokenArr[5])
                        .add("countryCode", authTokenArr[6])
                        .add("status", "success")
                        .add("error", success);
                
            }else if(ServiceName.equals("ResourceToken")){
                
                String token=content.getString("token");
                String userID=content.getString("userID");
                String instituteID=content.getString("instituteID");
                String secKey=content.getString("secKey");
                String service=content.getString("service");
                
                JWEInput jweInput=new JWEInput(token,userID,instituteID,secKey);
                String resourceToken=null;
                 boolean comeOutLoop = false;
            int iteratonCount = 0;
            while (comeOutLoop == false) {
                try {
                    iteratonCount = iteratonCount + 1;
                
                IResourceTokenService resTokenService=inject.getResourceTokenService();
//                String resourceToken=resTokenService.getWebResourceToken(jweInput, service);
                 resourceToken=resTokenService.getResourceToken(jweInput, service);
                
                if(resourceToken==null){
                    
                    throw new BSProcessingException("Resource token is null");
                }
                comeOutLoop=true;
//                }catch(BSValidationException ex){
//                 throw ex;
             }catch(BSProcessingException ex){
                 throw ex;    
//             }catch(DBValidationException ex){
//                 throw ex;
//             }catch(DBProcessingException ex){
//                 throw ex;
                } catch (EJBException ex) {

                    if (iteratonCount <= 10) {

                        inject = null;
                        Thread.sleep(3000);
                        inject = new DependencyInjection();

                    } else {
                        throw ex;
                    }
                } 
//                catch (Exception ex) {
//
//                    if (iteratonCount <= 10) {
//                        inject = null;
//                        Thread.sleep(3000);
//                        inject = new DependencyInjection();
//
//                    } else {
//                        throw ex;
//                    }
//                }

            }
                response.add("token", token)
                        .add("userID", userID)
                        .add("instituteID", instituteID)
                        .add("secKey",secKey)
                        .add("service", service)
                        .add("expired", jweInput.isExpired())
                        .add("InstituteChanged", jweInput.isInstituteChanged())
                        .add("resourceToken", resourceToken)
                        .add("status", "success")
                        .add("error", success);
            }
            
            dbg("response -->"+response.toString());
            
            
    
            
        }catch(BSValidationException ex){
            JsonObjectBuilder responseBuilder=Json.createObjectBuilder();
          this.buildErrorResponse("BSValidationException", ex, content,responseBuilder);
            JsonObject errorResponse= responseBuilder.build();
          dbg("errorResponse"+errorResponse.toString());
          return errorResponse;
          
         }catch(BSProcessingException ex){
            JsonObjectBuilder responseBuilder=Json.createObjectBuilder();
          this.buildErrorResponse("BSProcessingException", ex, content,responseBuilder);
            JsonObject errorResponse= responseBuilder.build();
          dbg("errorResponse"+errorResponse.toString());
          return errorResponse;
        } catch(NamingException ex) {
           dbg(ex);
           throw new RuntimeException(ex);
           }
          catch(Exception e)
          {
             dbg(e);
           throw new RuntimeException(e); 
          }
          
         finally{
            session.clearSessionObject();
           }         
           return response.build();
    }
    
    
    private void buildErrorResponse(String exceptionName,Exception ex,JsonObject content,JsonObjectBuilder response){
    
    try{
        JsonObject error;
        String errorCode;
        String errorMessage;
        dbg("inside buildErrorResponse");
        dbg("exceptionName"+exceptionName);
        dbg("ex"+ex.toString());
        
        if(exceptionName.equals("BSValidationException")){
            
             errorCode=ex.toString().split("~")[0];
             errorMessage=ex.toString().split("~")[1];
             
        }else{
            
             errorCode="BS_VAL_029";
             errorMessage="There is Fatal Error! Please contact System Adminstrator";
        }
        
        error=Json.createObjectBuilder().add("errorCode", errorCode)
                                        .add("errorMessage", errorMessage).build();
        
        
        
        if(ServiceName.equals("LoginAuthenticate")){
                 String userID=content.getString("userID");
                String password=content.getString("password");
                 
                 response.add("userID", userID)
                         .add("password", password)
                         .add("status", "error")
                         .add("error", error);
             }else if(ServiceName.equals("ResourceToken")){
                 
                 String token=content.getString("token");
                 String userID=content.getString("userID");
                 String instituteID=content.getString("instituteID");
                 String secKey=content.getString("secKey");
                 String service=content.getString("service");
                 
                 response.add("userID", userID)
                         .add("token", token)
                         .add("instituteID", instituteID)
                         .add("secKey", secKey)
                         .add("service", service)
                         .add("status", "error")
                         .add("error", error);
                 
                 
             }
        
        
        
        dbg("error response"+response);
        
//       return response;
    }catch(Exception e){
             dbg(e);
           throw new RuntimeException(e); 
    }
}
    
    
    
    
    
//    public void putJson(JsonObject content) {
//    }

    /**
     * DELETE method for resource AppResource
     */
    @DELETE
    public void delete() {
    }
    private void dbg(String p_value){
        session.getDebug().dbg(p_value);
    }
     private void dbg(Exception ex){
        session.getDebug().exceptionDbg(ex);
    }
    
}
