/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.io;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import java.util.concurrent.Future;

/**
 *
 * @author DELL
 */
public interface IWaitWriteService {
    
    public  void waitWrite()throws DBProcessingException;
}
