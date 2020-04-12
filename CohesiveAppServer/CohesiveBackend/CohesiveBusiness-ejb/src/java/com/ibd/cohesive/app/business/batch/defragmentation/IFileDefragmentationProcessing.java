/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch.defragmentation;

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
public interface IFileDefragmentationProcessing {
    public void processing (String userID,String businessDate)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
    public void processing(String userID,String businessDate,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
    public Future<String> parallelProcessing(String userID,String businessDate) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
}