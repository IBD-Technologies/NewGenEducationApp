/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.lock;

import com.ibd.cohesive.app.business.util.message.request.Request;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Local;

/**
 *
 * @author DELL
 */
@Local
public interface IBusinessLockService {
    
    public boolean getBusinessLock(Request request,String primaryKey,CohesiveSession p_session)throws BSProcessingException;
    public void removeBusinessLock(Request request,String primaryKey,CohesiveSession p_session)throws BSProcessingException;
    public void destroyLock();}
