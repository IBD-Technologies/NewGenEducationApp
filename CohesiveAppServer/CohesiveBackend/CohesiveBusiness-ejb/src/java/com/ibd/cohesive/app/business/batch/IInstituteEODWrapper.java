/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.batch;

import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.session.DBSession;
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
public interface IInstituteEODWrapper {
    public void batchWrapper(String instituteID)throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
    public Future<String> parellelBatchWrapper(String instituteID) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
    public void batchWrapper(String instituteID,CohesiveSession session) throws DBProcessingException,DBValidationException,BSProcessingException,BSValidationException;
}

