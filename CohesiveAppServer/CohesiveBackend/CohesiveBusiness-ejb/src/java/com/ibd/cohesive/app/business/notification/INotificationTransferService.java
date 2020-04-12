/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.notification;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;

/**
 *
 * @author DELL
 */
public interface INotificationTransferService {
    public void emailNotificationProcessing(String emailID,String message,String notificationID,String studentID)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException ;
//     public void emailNotificationProcessing(String emailID,String message,String notificationID,String studentID,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException ;
    public void smsNotificationProcessing(String mobileNo,String message,String notificationID,String studentID)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException ;
//   public void smsNotificationProcessing(String mobileNo,String message,String notificationID,String studentID,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException ;    
}
