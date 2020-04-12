/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;

/**
 *
 * @author DELL
 */
public interface IAmazonSMSService {
    public boolean  sendSMS(String message,String phoneNumber,String instituteID) throws BSProcessingException;
    public boolean sendSMS(String message,String mobileNo,CohesiveSession session,String instituteID)throws BSProcessingException;
}
