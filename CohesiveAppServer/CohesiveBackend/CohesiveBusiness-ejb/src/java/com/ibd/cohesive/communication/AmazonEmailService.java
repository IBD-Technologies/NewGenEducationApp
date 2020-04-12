/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.communication;
import com.ibd.cohesive.util.Email;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest; 
import com.ibd.businessViews.IAmazonEmailService;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 *
 * @author IBD Technologies
 */
@Local(IAmazonEmailService.class)
@Stateless
public class AmazonEmailService implements IAmazonEmailService{
    CohesiveSession session;
  // Replace sender@example.com with your "From" address.
  // This address must be verified with Amazon SES.
//  static final String FROM = "rajkumarvelusamy@ibdtechnologies.com";

  // Replace recipient@example.com with a "To" address. If your account
  // is still in the sandbox, this address must be verified.
//  static final String TO = "rajkumarvelusamy@gmail.com";

  // The configuration set to use for this email. If you do not want to use a
  // configuration set, comment the following variable and the 
  // .withConfigurationSetName(CONFIGSET); argument below.
  //static final String CONFIGSET = "ConfigSet";

  // The subject line for the email.
//  static final String SUBJECT ="Notification from ABC Schools ";
  
  // The HTML body for the email.
//  static final String HTMLBODY = "<h1> Email Notification from ABC Schools</h1>"
//      + "<p>Please help student to comeplete the assignment by logging into"
//          + "<a href='https://cohesive.ibdtechnologies.com'>"+
//          "https://cohesive.ibdtechnologies.com"+"</a>"
//          +"</p>" 
//          +"<p> <u>This is Auto generated email , please do not reply</u></p>"
//          ;
      
  // The email body for recipients with non-HTML email clients.
//  static final String TEXTBODY = "This Email Notification from ABC Schools."
//      + "Please help student to complete the assignment by logging into https://cohesive.ibdtechnologies.com'";

  public boolean  sendEmail(Email emailObj) throws BSProcessingException{
    boolean l_session_created_now=false;
    try {
        session.createSessionObject();
        l_session_created_now=session.isI_session_created_now();
        dbg("inside AmazonEmailService-->sendEmail");
        String TO=emailObj.getToEmail();
        String FROM=emailObj.getFromEmail();
//        String TO="rajkumarvelusamy@gmail.com";
//        String FROM="rajkumarvelusamy@ibdtechnologies.com";
        String HTMLBODY=emailObj.getHtmlBody();
        String TEXTBODY=emailObj.getTextBody();
        String SUBJECT=emailObj.getSubject();
        String emailCredentialKey=session.getCohesiveproperties().getProperty("AWS_EMAIL_CREDENTIAL_KEY");
        String emailCredentialValue=session.getCohesiveproperties().getProperty("AWS_EMAIL_CREDENTIAL_VALUE");
        String region=session.getCohesiveproperties().getProperty("EMAIL_REGION");
        
        dbg("TO"+TO);
        dbg("FROM"+FROM);
        dbg("HTMLBODY"+HTMLBODY);
        dbg("TEXTBODY"+TEXTBODY);
        dbg("SUBJECT"+SUBJECT);
        dbg("emailCredentialKey"+emailCredentialKey);
        dbg("emailCredentialValue"+emailCredentialValue);
        dbg("region"+region);
        
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(emailCredentialKey, emailCredentialValue);  
      
        AmazonSimpleEmailService client =null;
        
        if(region.equals("EU_WEST_1")){
        
        client = 
        AmazonSimpleEmailServiceClientBuilder.standard()
          // Replace US_WEST_2 with the AWS Region you're using for
          // Amazon SES.
            .withRegion(Regions.EU_WEST_1)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))      
                  .build();
        
       }
        
      SendEmailRequest request = new SendEmailRequest()
          .withDestination(
              new Destination().withToAddresses(TO))
          .withMessage(new Message()
              .withBody(new Body()
                  .withHtml(new Content()
                      .withCharset("UTF-8").withData(HTMLBODY))
                  .withText(new Content()
                      .withCharset("UTF-8").withData(TEXTBODY)))
              .withSubject(new Content()
                  .withCharset("UTF-8").withData(SUBJECT)))
          .withSource(FROM);
          // Comment or remove the next line if you are not using a
          // configuration set
          //.withConfigurationSetName(CONFIGSET);
          
          
          
      client.sendEmail(request);
      
      
      
      
      dbg("Email sent!");
      return true;
      
    } catch (Exception ex) {
       dbg(ex); 
       throw new BSProcessingException("Amazon Email exception"+ex.toString());
    }finally{
        
        if(l_session_created_now){
            
            session.clearSessionObject();;
        }
        
        
    }
  }
  
  
  
  @Override
    public boolean sendEmail(Email emailObj,CohesiveSession session)throws BSProcessingException
    {
    CohesiveSession tempSession = this.session;
    try{
        this.session=session;
       return sendEmail(emailObj);
     
            
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
  
  
    
    
    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
  
}
    
    
    

