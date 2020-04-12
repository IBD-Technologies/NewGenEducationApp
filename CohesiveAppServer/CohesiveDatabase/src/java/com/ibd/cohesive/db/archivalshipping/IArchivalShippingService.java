/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.archivalshipping;

import com.ibd.cohesive.util.exceptions.ArchShippingException;
import javax.ejb.Local;

/**
 *
 * @author DELL
 */
@Local
public interface IArchivalShippingService {
    public void ArchShippingProcessing()throws ArchShippingException;
}
