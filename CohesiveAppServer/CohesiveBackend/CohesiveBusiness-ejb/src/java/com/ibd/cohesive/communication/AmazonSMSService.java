/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package test;

package com.ibd.cohesive.communication;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.*;
import com.ibd.businessViews.IAmazonSMSService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.NamingException;


/**
 *
 * @author IBD Technologies
 */
@Local(IAmazonSMSService.class)
@Stateless
public class AmazonSMSService implements IAmazonSMSService{
   AppDependencyInjection inject;
    CohesiveSession session;
    DBSession dbSession;
   public AmazonSMSService(){
        try {
            inject=new AppDependencyInjection();
            session = new CohesiveSession();
            dbSession= new DBSession(session);
        } catch (NamingException ex) {
            dbg(ex);
            throw new EJBException(ex);
        }
        
    }
   
   
public boolean  sendSMS(String message,String phoneNumber,String instituteID) throws BSProcessingException{     
    boolean l_session_created_now=false;
    
    try{
    
        session.createSessionObject();
        l_session_created_now=session.isI_session_created_now();
        dbSession.createDBsession(session);
        dbg("inside AmazonSMSService-->sendSMS");
        IBDProperties i_db_properties=session.getCohesiveproperties();
        IPDataService pds=inject.getPdataservice();
        String smsCredentialKey=session.getCohesiveproperties().getProperty("AWS_SMS_CREDENTIAL_KEY");
        String smsCredentialValue=session.getCohesiveproperties().getProperty("AWS_SMS_CREDENTIAL_VALUE");
        String region=session.getCohesiveproperties().getProperty("SMS_REGION");
        dbg("region"+region);
        dbg("smasCredentialKey"+smsCredentialKey);
        dbg("smsCredentialValue"+smsCredentialValue);
        
        
        if(!phoneNumber.contains("+")){
            
            String temp=phoneNumber;
            
              String[] pkey={instituteID};
            ArrayList<String>contractList=pds.readRecordPData(session,dbSession,"APP"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive"+i_db_properties.getProperty("FOLDER_DELIMITER")+"Cohesive", "APP", "CONTRACT_MASTER",pkey);
            
            String countryCode=contractList.get(13).trim();
            if(countryCode.contains("IN")){
                
                phoneNumber="+91"+temp;
                
            }else if(countryCode.contains("UK")){
                
                phoneNumber="+44"+temp;
            }

            
        }
        
        
        
        
        AWSCredentials awsCredentials = new BasicAWSCredentials(smsCredentialKey,smsCredentialValue);
        final AmazonSNSClient client = new AmazonSNSClient(awsCredentials);
        client.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        AmazonSNSClient snsClient = new AmazonSNSClient(awsCredentials);
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
        //<set SMS attributes>
        //sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
        sendSMSMessage1(smsCredentialKey,smsCredentialValue,region,message,phoneNumber);

        dbg("end of AmazonSMSService-->sendSMS");
        return true;
    } catch (Exception ex) {
       dbg(ex); 
       throw new BSProcessingException("Amazon SMS exception"+ex.toString());
    }finally{
        
        if(l_session_created_now){
            
            session.clearSessionObject();;
        }
        
        
    }


}


@Override
    public boolean sendSMS(String message,String mobileNo,CohesiveSession session,String instituteID)throws BSProcessingException
    {
    CohesiveSession tempSession = this.session;
    try{
        this.session=session;
       return sendSMS(message,mobileNo,instituteID);
     
            
        }catch(BSProcessingException ex){
             dbg(ex);
            throw new BSProcessingException("BSProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());   
        }finally{
           this.session=tempSession;
        }
 }

//public  void sendSMSMessage(AmazonSNSClient snsClient, String message, 
//    String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
//    PublishResult result = snsClient.publish(new PublishRequest()
//                    .withMessage(message)
//                    .withPhoneNumber(phoneNumber)
//                    .withMessageAttributes(smsAttributes));
//    System.out.println(result); // Prints the message ID.
//}

public  void sendSMSMessage1(String smsCredentialKey,String smsCredentialValue,String region,String message,String mobileNo)throws BSProcessingException{
    
    try{
       
        dbg("inside sendSMSMessage1"); 
    
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(smsCredentialKey, smsCredentialValue);
        AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                             .withRegion(Regions.fromName(region))
                               //.withRegion(Regions.AP_SOUTH_1)  
             .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

        Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SenderID",new MessageAttributeValue().withStringValue("Cohesive").withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.SMSType",new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));

        PublishRequest request = new PublishRequest();
        request.withMessage(message)
        .withPhoneNumber(mobileNo)
        .withMessageAttributes(smsAttributes);
        PublishResult result=snsClient.publish(request);
        dbg("MessageId: " + result.getMessageId());
        
        
      }catch (Exception ex) {
        dbg(ex); 
        throw new BSProcessingException("SMS exception"+ex.toString());
      }
   
}  



    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }


    }

