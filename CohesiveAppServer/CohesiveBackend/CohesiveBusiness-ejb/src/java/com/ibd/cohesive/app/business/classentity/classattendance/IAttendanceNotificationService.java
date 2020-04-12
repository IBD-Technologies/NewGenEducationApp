/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.classentity.classattendance;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 *
 * @author DELL
 */
public interface IAttendanceNotificationService {
    
     public void notificationProcessing (String date,String instituteID,ArrayList<String>studentList)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException;
}
