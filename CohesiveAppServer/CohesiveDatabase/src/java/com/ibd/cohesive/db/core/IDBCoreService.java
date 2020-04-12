/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
*  Changed by :Rajkumar Velusamy
 * Changed-on:14-12-2018
 * Change-Reason:Development
 * Change-Description: EJB Integration
 * Search Tag: EJB Integration change 
 *
*/
package com.ibd.cohesive.db.core;

import javax.ejb.Local;
import com.ibd.cohesive.db.core.metadata.DBFile;
import com.ibd.cohesive.db.transaction.lock.ILockService;
import java.util.Map;
import javax.ejb.EJBException;

/**
 *
 * @author IBD Technologies
 */
@Local //EJB Integration
public interface IDBCoreService {

    void start() throws EJBException;

    void stop();

    public Map<String, DBFile> getG_dbMetaData();
//    public  int getTimeout();
//    public void setG_dbMetaData(Map<String, DBFile> g_dbMetaData);

    //public  ILockService getI_lockService();
 
    
    
}