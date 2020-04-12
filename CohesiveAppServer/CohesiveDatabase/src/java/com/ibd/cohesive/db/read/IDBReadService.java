/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.read;

import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.metadata.MetaDataService;
import com.ibd.cohesive.db.index.IIndexReadService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 *
 * Change Description :Cross call functions is added 
 * Changed by   :Isac
 * Changed on   :05/09/2018
 * Search Tag   :Cohesive1_Dev_3
 * 
 * */
@Local
public interface IDBReadService {

    Map<String, ArrayList<String>> readFullFile(String p_FileName,String p_FileType) throws DBValidationException, DBProcessingException;
   
    //Cohesive1_Dev_3 starts here
    Map<String, ArrayList<String>> readFullFile(String p_FileName, String p_FileType,CohesiveSession session) throws DBValidationException, DBProcessingException;
    //Cohesive1_Dev_3 ends here
    
    Map<String, ArrayList<String>> readTable(String p_FileName,String p_FileType, String p_TableName) throws DBValidationException, DBProcessingException;
    
    //Cohesive1_Dev_3 stars here
    Map<String, ArrayList<String>> readTable(String p_FileName,String p_FileType, String p_TableName,CohesiveSession session) throws DBValidationException, DBProcessingException;
    //Cohesive1_Dev_3 ends here
    
    String[] recordRead(String p_FileName,String p_FileType,String p_TableName, String... p_primary_key) throws DBValidationException, DBProcessingException;
    
    String[] recordRead(CohesiveSession session,String p_FileName,String p_FileType,String p_TableName, String... p_primary_key) throws DBValidationException, DBProcessingException;
    
    Map<String, Map<String,DBRecord>> dbPhysicalRead(String p_FileName, String p_FileType) throws DBValidationException, DBProcessingException;
    Map<String, Map<String,DBRecord>> dbPhysicalRead(String p_FileName, String p_FileType,CohesiveSession session) throws DBValidationException, DBProcessingException;
}
