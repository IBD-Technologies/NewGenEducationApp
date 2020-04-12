/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.transaction;

import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
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
 */
@Local
public interface IDBTransactionService {

    void createRecord(String p_fileName, String p_fileType, int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException;
    
    //Cohesive1_Dev_3 starts here
     void createRecord(CohesiveSession session,String p_fileName, String p_fileType, int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException;
     //Cohesive1_Dev_3 ends here

    void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException;

    void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException;
    
    public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey) throws DBProcessingException, DBValidationException;
    
    public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey,CohesiveSession session) throws DBProcessingException, DBValidationException;

    public void getRelatedFiles(String p_file_name, int p_file_id, int p_table_id) throws DBValidationException, DBProcessingException;
    
    public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException;
    
    public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException;

    public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException;
    
    public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException;
    
    public void lockProcessing(String p_file_name)throws DBProcessingException;
    
    public boolean getImplictRecordLock(String p_file_name,String p_table_name,String p_primary_key)throws DBProcessingException;
    
    public boolean getImplictRecordLock(String p_file_name,String p_table_name,String p_primary_key,CohesiveSession session)throws DBProcessingException;
}
