/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.archival.timetable;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.concurrent.Future;

/**
 *
 * @author DELL
 */
public interface ICTimeTableArchProcessing {
    public void processing (String standard,String section,String instituteID,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
    public void processing(String standard,String section,String instituteID,String businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
    public Future<String> parallelProcessing(String standard,String section,String instituteID,String businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
}
