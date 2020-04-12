/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.index;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import java.util.Map;

/**
 *
 * @author IBD Technologies
 */
public interface IIndexCoreService {
    public Map<String, Map<String, String>> getIndex_map();
    public void start(String p_index_fileName)throws DBValidationException,DBProcessingException;
}
