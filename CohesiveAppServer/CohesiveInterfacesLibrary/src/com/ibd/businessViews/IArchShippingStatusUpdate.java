/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import javax.json.JsonObject;

/**
 *
 * @author DELL
 */
public interface IArchShippingStatusUpdate {
    
    
    public void statusUpdate(String request)throws DBProcessingException,DBValidationException;
}
