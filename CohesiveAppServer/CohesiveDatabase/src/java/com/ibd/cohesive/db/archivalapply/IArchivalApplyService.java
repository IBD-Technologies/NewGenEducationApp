/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.archivalapply;

import com.ibd.cohesive.util.exceptions.ArchApplyException;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 */
@Local
public interface IArchivalApplyService {
    
    public void ArchApplyProcessing()throws ArchApplyException;
}
