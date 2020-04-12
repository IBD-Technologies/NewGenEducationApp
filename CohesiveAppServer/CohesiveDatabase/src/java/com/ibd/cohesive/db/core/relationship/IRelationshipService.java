/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.relationship;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.Map;

/**
 *
 * @author IBD Technologies
 * 
 * Change Description :Cross call functions is added 
 * Changed by   :Isac
 * Changed on   :05/09/2018
 * Search Tag   :Cohesive1_Dev_3
 */
public interface IRelationshipService {
    
    public void createRecord(String p_fileName, String p_fileType, int p_tableID, String... p_record_values)throws DBValidationException,DBProcessingException; 
    
    //Cohesive1_Dev_3 starts here
    public void createRecord(CohesiveSession session,String p_fileName, String p_fileType, int p_tableID, String... p_record_values)throws DBValidationException,DBProcessingException; 
    //Cohesive1_Dev_3 ends here
      
    public void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException;

    public void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException;

    public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException;

    public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException;

    public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey) throws DBProcessingException, DBValidationException; 

    public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey,CohesiveSession session) throws DBProcessingException, DBValidationException;
    
//    public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException;
//    
//    public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update,DBDependencyInjection p_dbdi) throws DBProcessingException, DBValidationException;
}

