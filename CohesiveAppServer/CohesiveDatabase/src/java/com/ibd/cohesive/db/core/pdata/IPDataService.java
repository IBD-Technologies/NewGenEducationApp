/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.pdata;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Local;

/**
 *
 * @author IBD Technologies
 * 
 * Created by : Isac
 * Created-on : 17-08-2018
 * Create-Phase : Cohesive_Development
 * Create-Description :This java interfaces contains caching of files.
 
 
 */
@Local
public interface IPDataService {


   public  void createPData()throws DBValidationException, DBProcessingException,FileNotFoundException;
   public void destroyPData(); 
   
//   public Map<String, ArrayList<String>> readFilePData(String p_FileName,String p_FileType)throws DBValidationException, DBProcessingException;
//   public Map<String, ArrayList<String>> readFilePData(String p_FileName,String p_FileType,CohesiveSession session)throws DBValidationException, DBProcessingException;
//   public Map<String,Map<String, ArrayList<String>>> readFilePData(String p_FileName,String p_FileType,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException;
  public ConcurrentHashMap<String,ConcurrentHashMap<String, ArrayList<String>>> readFilePData(String p_FileName,String p_FileType,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException;
 
//   public Map<String,Map<String, ArrayList<String>>> readFilePData(String p_FileName,String p_FileType,CohesiveSession session)throws DBValidationException, DBProcessingException;
   
   
   public Map<String, ArrayList<String>> readTablePData(String p_FileName,String p_FileType, String p_TableName,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException;
   
//   public Map<String, ArrayList<String>> readTablePData(String p_FileName,String p_FileType, String p_TableName,CohesiveSession session)throws DBValidationException, DBProcessingException;
    
   public ArrayList<String> readRecordPData(CohesiveSession p_session,DBSession p_dbSession,String p_FileName,String p_FileType,String p_TableName, String... p_primary_key)throws DBValidationException, DBProcessingException;
  
//    public ArrayList<String> readRecordPData(CohesiveSession session,String p_FileName,String p_FileType,String p_TableName, String... p_primary_key)throws DBValidationException, DBProcessingException;
   
   public void SetRecordfromTempseg(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;

   public void SetRecordfromArchivalWrite(String p_fileName,String p_tableName,String pKey,DBRecord l_tempSegRec,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;

    public boolean validatePData(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException;
}
