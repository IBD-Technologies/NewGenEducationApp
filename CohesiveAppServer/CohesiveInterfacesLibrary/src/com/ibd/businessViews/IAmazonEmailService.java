/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.Email;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;

/**
 *
 * @author DELL
 */
public interface IAmazonEmailService {
    public boolean  sendEmail(Email emailObj) throws BSProcessingException;
    public boolean sendEmail(Email emailObj,CohesiveSession session)throws BSProcessingException;
}
