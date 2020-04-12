/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Date;
import javax.json.JsonObject;

/**
 *
 * @author DELL
 */
public interface IHolidayMaintanenceService extends BusinessEJBTemplate{
    public boolean checkWorkingDay(Date date,String l_instituteID,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException;
    public String EJBprocessing(String p_request) ;
    public JsonObject EJBprocessing(JsonObject p_request) ;
    public JsonObject processing(JsonObject p_request,CohesiveSession session)throws BSProcessingException,BSValidationException,DBValidationException,DBProcessingException ;
}
