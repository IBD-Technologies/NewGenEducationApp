/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.transaction.transactioncontol;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 */
@Local
public interface ITransactionControlService {
    
     public void commit(CohesiveSession session,DBSession dbSession)throws DBProcessingException,DBValidationException;
   
     public void rollBack(CohesiveSession session,DBSession dbSession)throws DBProcessingException;
    
}
