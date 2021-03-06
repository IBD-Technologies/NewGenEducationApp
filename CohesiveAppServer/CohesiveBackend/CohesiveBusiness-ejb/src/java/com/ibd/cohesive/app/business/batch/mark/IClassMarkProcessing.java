/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.mark;

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
public interface IClassMarkProcessing {
    public void processing(String instituteID,String standard,String section,String exam,String l_businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
public void processing (String instituteID,String standard,String section,String exam,String l_businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
public Future<String> parallelProcessing(String instituteID,String standard,String section,String exam,String l_businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;

}
