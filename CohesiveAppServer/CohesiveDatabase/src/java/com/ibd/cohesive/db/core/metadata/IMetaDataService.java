/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package com.ibd.cohesive.db.core.metadata;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 * 
 * Change Description:some methods defined in the
 * Metadatadataservice are not included in the IMetadataservice.which are
 * included in this change. Change by :Suriya Narayanan.L Change on :15/05/2018
 * Search Tag:CODEREVIEW_8
 * 
 * Change Description :Cross call functions is added 
 * Changed by   :Isac
 * Changed on   :05/09/2018
 * Search Tag   :Cohesive1_Dev_3
 */
@Local
public interface IMetaDataService {

    public DBFile getFileMetaData(String p_FileType, CohesiveSession session) throws DBValidationException, DBProcessingException;

    public DBFile getFileMetaData(String p_FileType) throws DBValidationException, DBProcessingException;

    //Cohesive1_Dev_3 starts here
    public DBFile getFileMetaData(int p_Fileid,CohesiveSession session) throws DBValidationException, DBProcessingException;
    //Cohesive1_Dev_3 ends here
    
    public DBFile getFileMetaData(int p_Fileid) throws DBValidationException, DBProcessingException;//CODEREVIEW_8

    //public DBFile getFileMetaData(int p_Fileid) throws DBValidationException, DBProcessingException;
    public DBTable getTableMetaData(int p_Fileid, int p_Tableid, CohesiveSession session) throws DBValidationException, DBProcessingException;

    public DBTable getTableMetaData(int p_Fileid, int p_Tableid) throws DBValidationException, DBProcessingException;//CODEREVIEW_8

    public DBTable getTableMetaData(String p_FileType, String p_TableName) throws DBValidationException, DBProcessingException;

    public DBTable getTableMetaData(String p_FileType, String p_TableName, CohesiveSession session) throws DBValidationException, DBProcessingException;

    // public DBTable getTableMetaData(int p_Fileid, int p_Tableid) throws DBValidationException, DBProcessingException;
    public DBTable getTableMetaData(int p_Tableid) throws DBValidationException, DBProcessingException;

    //public DBTable getTableMetaData(String p_TableName, DBDependencyInjection p_dbdi) throws DBValidationException, DBProcessingException;
    public DBTable getTableMetaData(int p_Tableid, CohesiveSession session) throws DBValidationException, DBProcessingException;

    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, String p_ColumnName) throws DBValidationException, DBProcessingException;
    
    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, String p_ColumnName, CohesiveSession session) throws DBValidationException, DBProcessingException ;

    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, int p_column_id) throws DBValidationException, DBProcessingException;

    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, int p_column_id, CohesiveSession session) throws DBValidationException, DBProcessingException;

    public DBColumn getColumnMetaData(String p_FileType, int p_TableId, int p_column_id) throws DBValidationException, DBProcessingException;
    
    //Cohesive1_Dev_3 starts here
    public DBColumn getColumnMetaData(String p_FileType, int p_TableId, int p_column_id,CohesiveSession session) throws DBValidationException, DBProcessingException;
    //Cohesive1_Dev_3 ends here
    public String[] getFileNamesToChange(int p_file_id, int p_table_id) throws DBValidationException, DBProcessingException;

    public String[] getFileNamesToChange(int p_file_id, int p_table_id, CohesiveSession session) throws DBValidationException, DBProcessingException;

    //public DBColumn getColumnMetaData(String p_TableName, int p_column_id) throws DBValidationException, DBProcessingException;
    // public DBColumn getColumnMetaData(int p_TableId, int p_column_id) throws DBValidationException, DBProcessingException;
    //public DBColumn getColumnMetaData(String p_TableName, String p_ColumnName) throws DBValidationException, DBProcessingException;
    public boolean isIndexRequired(String p_file_type) throws DBProcessingException, DBValidationException;

    public boolean isIndexRequired(String p_file_type, CohesiveSession session) throws DBProcessingException, DBValidationException;

    public int getColumnCount(String p_FileType, String p_TableName) throws DBValidationException, DBProcessingException;//CODEREVIEW_8

    public int getTableCount(String p_FileType) throws DBValidationException, DBProcessingException;//CODEREVIEW_8
    
    public String getPrimaryKey(int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException;
    
    public String getPrimaryKey(CohesiveSession session,int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException;
}
