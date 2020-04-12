/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.relationship;

import com.ibd.cohesive.db.core.metadata.DBTable;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.transaction.IDBTransactionService;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 * 
 * Change Description :Cross call functions is added 
 * Changed by   :Isac
 * Changed on   :05/09/2018
 * Search Tag   :Cohesive1_Dev_3
 */
public class RelationshipService implements IRelationshipService{
    
    
    DBDependencyInjection dbdi;
    TableRMD[] i_TableRMD;
    CohesiveSession session;
    DBSession dbSession;
    public RelationshipService() throws NamingException{
        dbdi=new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);
    }
    
    public void createRecord(String p_fileName, String p_fileType, int p_tableID, String... p_record_values)throws DBValidationException,DBProcessingException{
       
        boolean l_session_created_now=false;
        String l_fileType;
        IBDProperties i_db_properties=session.getCohesiveproperties();
        try{
           
        session.createSessionObject();
        dbSession.createDBsession(session);
        l_session_created_now=session.isI_session_created_now();
        dbg("inside create record of RelationshipService");
        relationshipProcessing(p_fileName,p_fileType,p_tableID,p_record_values);   
        IDBTransactionService dbts=dbdi.getDBTransactionService();
        IPDataService pds=dbdi.getPdataservice();
        IMetaDataService mds=dbdi.getMetadataservice();
         String[] l_fileNameArr=p_fileName.split(i_db_properties.getProperty("FOLDER_DELIMITER"));
            String instID=l_fileNameArr[1];
            dbg("instID"+instID);
         //dbg("filetype"+mds.getFileMetaData(Integer.parseInt(i_TableRMD[0].i_fileId)).getI_FileType());
        for(int i=0;i<i_TableRMD.length;i++){
            
            for(int j=0;j<i_TableRMD[i].i_relationshipfilenames.length;j++){  
                //for(int k=0;k<i_TableRMD[i].i_relationshipcolumnvalues.length;k++){
                     
             l_fileType=mds.getFileMetaData(Integer.parseInt(i_TableRMD[i].i_fileId),this.session).getI_FileType(); 
             dbg("fileType of create record inside relationshipservice"+l_fileType);
            dbg("p_fileName"+p_fileName);
           
             dbts.createRecord(this.session,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileType+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j]+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j], l_fileType,Integer.parseInt(i_TableRMD[i].i_tableId),i_TableRMD[i].i_relationshipcolumnvalues);
            // }   
           }    
            
        }     
            
            
         } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch(DBValidationException ex){
            throw ex;
        }catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
         }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }finally{
            if(l_session_created_now){
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
        }
        
        
    }
   
   // Cohesive1_Dev_3 starts here
  public void createRecord(CohesiveSession session,String p_fileName, String p_fileType, int p_tableID, String... p_record_values)throws DBValidationException,DBProcessingException{
      
      CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           createRecord(p_fileName,p_fileType,p_tableID,p_record_values);
       } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch(DBValidationException ex){
            throw ex;
        }catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
         }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }finally {
           this.session=tempSession;
            
        }  
    
  }
    
   //Cohesive1_Dev_3 ends here 
     public void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
         boolean l_session_created_now=false;
         int l_tableId;
         String[] l_record_values;
          String l_fileType;
          String l_pkey;
          String[] l_pkey_vals;
          Map<String, String> l_column_to_update;
          try{
           
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            dbg("inside update record of relationship service");
            IMetaDataService mds=dbdi.getMetadataservice();
            IDBReadService dbrs=dbdi.getDbreadservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
            IDBTransactionService dbts=dbdi.getDBTransactionService();
            IPDataService pds=dbdi.getPdataservice();
            dbg("objecs of other services created");
            l_tableId=mds.getTableMetaData(p_file_type, p_table_name,this.session).getI_Tableid();
            dbg("l_tableId"+l_tableId);
            l_record_values=dbrs.recordRead(session, p_file_name, p_file_type, p_table_name, p_pkey);
            
            dbg("record read completed inside updated record");
            relationshipProcessing(p_file_name,p_file_type,l_tableId,l_record_values);   
            dbg("relationship processing completed inside update record");
                         Iterator iterator1=p_column_to_update.keySet().iterator();
                         Iterator iterator2=p_column_to_update.values().iterator();
                         l_column_to_update=new HashMap();
                         while(iterator1.hasNext()&&iterator2.hasNext()){
                         String l_col_id=   (String)iterator1.next();  
                         
                         String l_col_val=(String)iterator2.next();
                         dbg("l_col_id"+l_col_id);
                         dbg("l_col_val"+l_col_val);
                         dbg("p_file_type"+p_file_type);
                         dbg("p_table_name"+p_table_name);
                         if(!mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt(l_col_id),this.session).getI_ColumnLevelRelationship().equals("null")){
                             
                             l_column_to_update.put(mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt(l_col_id),this.session).getI_ColumnLevelRelationship(), l_col_val);
                         }
                                                 
                         }
                         dbg("l_column_to_update map is created");
                         Iterator iterator3=l_column_to_update.keySet().iterator();
                         Iterator iterator4=l_column_to_update.values().iterator();
                         while(iterator3.hasNext()&&iterator4.hasNext()){
                             dbg("l_column_to_update->key"+(String)iterator3.next());
                             dbg("l_column_to_update->value"+(String)iterator4.next());
                         }
            if(i_TableRMD!=null){
            for(int i=0;i<i_TableRMD.length;i++){
                  dbg("inside first for loop  of update record in rs");
                 // if(i_TableRMD[i]!=null){
                    
                for(int j=0;j<i_TableRMD[i].i_relationshipfilenames.length;j++){  
                    dbg("inside second for loop  of update record in rs");
                         l_fileType=mds.getFileMetaData(Integer.parseInt(i_TableRMD[i].i_fileId),this.session).getI_FileType();
                         
                         l_pkey=mds.getPrimaryKey(session, Integer.parseInt(i_TableRMD[i].i_tableId),i_TableRMD[i].i_relationshipcolumnvalues );
                         dbg("l_pkey"+l_pkey);
                         l_pkey_vals=l_pkey.split("~");
                         dbg("before update record call");
                         dbg("i_TableRMD[i].i_relationshipfilenames[j]"+i_TableRMD[i].i_relationshipfilenames[j]);
                          dbg("l_fileType"+l_fileType);
                          String[] l_fileNameArr=p_file_name.split(i_db_properties.getProperty("FOLDER_DELIMITER"));
                          String instID=l_fileNameArr[1];
                         dbts.updateRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileType+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j]+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j], l_fileType,mds.getTableMetaData(Integer.parseInt(i_TableRMD[i].i_tableId),session).getI_TableName(),l_pkey_vals,l_column_to_update,session);
                }    
                // } 
            }
            }
            dbg("update record completed inside relationship servie");
         }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }catch (ArrayIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }finally{
            if(l_session_created_now){
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
        }
    
     }
     
      public void updateRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException{
       CohesiveSession tempSession = this.session;
       try{
           
           this.session=session;
           updateRecord(p_file_name,p_file_type,p_table_name,p_pkey,  p_column_to_update);
          
            
           
       }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }  catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }catch (ArrayIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }finally {
           this.session=tempSession;
            
        }
       
    }
      
      public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException {
          boolean l_session_created_now=false;
         int l_tableId;
         String[] l_record_values;
          String l_fileType;
          String l_pkey;
          String[] l_pkey_vals;
          Map<String,String> l_column_to_update;
         try{
           
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            IMetaDataService mds=dbdi.getMetadataservice();
            IDBReadService dbrs=dbdi.getDbreadservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=dbdi.getDBTransactionService();
             IPDataService pds=dbdi.getPdataservice();
//             dbg("objects of other services created");
            l_tableId=mds.getTableMetaData(p_file_type, p_table_name,this.session).getI_Tableid();
            l_record_values=dbrs.recordRead(session, p_file_name, p_file_type, p_table_name, p_pkey);
            relationshipProcessing(p_file_name,p_file_type,l_tableId,l_record_values);   
//            dbg("relationship processing completed inside update column");
             Iterator iterator1=p_column_to_update.keySet().iterator();
                         Iterator iterator2=p_column_to_update.values().iterator();
                         l_column_to_update=new HashMap();
                         while(iterator1.hasNext()&&iterator2.hasNext()){
                         String l_col_id=   (String)iterator1.next();  
                         
                         String l_col_val=(String)iterator2.next();
//                         dbg("l_col_id"+l_col_id);
//                         dbg("l_col_val"+l_col_val);
//                         dbg("p_file_type"+p_file_type);
//                         dbg("p_table_name"+p_table_name);
                         if(!mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt(l_col_id),this.session).getI_ColumnLevelRelationship().equals("null")){
                             
                             l_column_to_update.put(mds.getColumnMetaData(p_file_type, p_table_name,Integer.parseInt(l_col_id),this.session).getI_ColumnLevelRelationship(), l_col_val);
                         }
                                                 
                         }
                          dbg("l_column_to_update map is created");
//                         Iterator iterator3=l_column_to_update.keySet().iterator();
//                         Iterator iterator4=l_column_to_update.values().iterator();
//                         while(iterator3.hasNext()&&iterator4.hasNext()){
//                             dbg("l_column_to_update->key"+(String)iterator3.next());
//                             dbg("l_column_to_update->value"+(String)iterator4.next());
//                         }
               
             if(i_TableRMD!=null){
            for(int i=0;i<i_TableRMD.length;i++){
                        
                for(int j=0;j<i_TableRMD[i].i_relationshipfilenames.length;j++){  
                         l_fileType=mds.getFileMetaData(Integer.parseInt(i_TableRMD[i].i_fileId),this.session).getI_FileType();
                         l_pkey=mds.getPrimaryKey(session, Integer.parseInt(i_TableRMD[i].i_tableId),i_TableRMD[i].i_relationshipcolumnvalues);
                         dbg("inside update column l_pkey"+l_pkey);
                         l_pkey_vals=l_pkey.split("~");
                         dbg("filname"+i_TableRMD[i].i_relationshipfilenames[j]);
                        String l_tableName= mds.getTableMetaData(Integer.parseInt(i_TableRMD[i].i_tableId),session).getI_TableName();
                        String[] l_fileNameArr=p_file_name.split(i_db_properties.getProperty("FOLDER_DELIMITER"));
                          String instID=l_fileNameArr[1]; 
                         dbts.updateColumn("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileType+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j]+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j], l_fileType,l_tableName,l_pkey_vals,l_column_to_update,session);
                         dbg("update column completed for"+i_TableRMD[i].i_relationshipfilenames[j]); 
            }
             }
             }
            dbg("update column completed inside relationship service");
         }catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }finally{
            if(l_session_created_now){
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
        }
      }
      
     public void updateColumn(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey, Map<String, String> p_column_to_update,CohesiveSession session) throws DBProcessingException, DBValidationException{
         CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           updateColumn(p_file_name, p_file_type,p_table_name,p_pkey,p_column_to_update);
       }catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (StringIndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        }finally {
           this.session=tempSession;
            
        }
     } 
    
     public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey) throws DBProcessingException, DBValidationException {
          boolean l_session_created_now=false;
         int l_tableId;
         String[] l_record_values;
          String l_fileType;
          String l_pkey;
          String[] l_pkey_vals;
         try{
           
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            IMetaDataService mds=dbdi.getMetadataservice();
            IDBReadService dbrs=dbdi.getDbreadservice();
            IPDataService pds=dbdi.getPdataservice();
            IBDProperties i_db_properties=session.getCohesiveproperties();
             IDBTransactionService dbts=dbdi.getDBTransactionService();
            l_tableId=mds.getTableMetaData(p_file_type, p_table_name,this.session).getI_Tableid();
            l_record_values=dbrs.recordRead(session, p_file_name, p_file_type, p_table_name, p_pkey);
            relationshipProcessing(p_file_name,p_file_type,l_tableId,l_record_values); 
            dbg("relationship processing completed inside delete record");
            if(i_TableRMD!=null){
            for(int i=0;i<i_TableRMD.length;i++){
            
                for(int j=0;j<i_TableRMD[i].i_relationshipfilenames.length;j++){  
                         l_fileType=mds.getFileMetaData(Integer.parseInt(i_TableRMD[i].i_fileId),this.session).getI_FileType();
                         l_pkey=mds.getPrimaryKey(session, Integer.parseInt(i_TableRMD[i].i_tableId), l_record_values);
                         l_pkey_vals=l_pkey.split("~");
                         for(String s:l_pkey_vals)
                             dbg("inside delete record l_pkey_vals"+s);
                         String[] l_fileNameArr=p_file_name.split(i_db_properties.getProperty("FOLDER_DELIMITER"));
                          String instID=l_fileNameArr[1];
                         dbg("inside delete record i_TableRMD[i].i_relationshipfilenames[j]"+i_TableRMD[i].i_relationshipfilenames[j]);
                         dbts.deleteRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_fileType+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j]+i_db_properties.getProperty("FOLDER_DELIMITER")+i_TableRMD[i].i_relationshipfilenames[j], l_fileType,mds.getTableMetaData(Integer.parseInt(i_TableRMD[i].i_tableId),session).getI_TableName(),l_pkey_vals,this.session);
             
                 } 
            }
            }
            dbg("delete record completed inside relationship service");
         }catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally{
            if(l_session_created_now){
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
        }
     }
    
     public void deleteRecord(String p_file_name, String p_file_type, String p_table_name, String[] p_pkey,CohesiveSession session) throws DBProcessingException, DBValidationException{
        CohesiveSession tempSession = this.session;
       try{
           
           this.session=session;
           deleteRecord(p_file_name, p_file_type,p_table_name, p_pkey);
           
       }catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally {
           this.session=tempSession;
            
        }
    }
    
    
  /*   public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update) throws DBProcessingException, DBValidationException{
        boolean l_session_created_now=false;
         int l_tableId;
         String[] l_record_values;
          String l_fileType;
          
         try{
             dbdi.createSessionObject();
            l_session_created_now=dbdi.isI_session_created_now();
            IMetaDataService mds=dbdi.getMetadataservice();
            IDBReadService dbrs=dbdi.getDbreadservice();
            IBDProperties i_db_properties=dbdi.getI_db_properties();
            IDBTransactionService dbts=dbdi.getDBTransactionService();
            dbg("objecs of other services created");
            l_tableId=mds.getTableMetaData(p_file_type, p_table_name,this.dbdi).getI_Tableid();

            
            


            
        }catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }
         catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally {
           if(l_session_created_now){
                dbdi.clearSessionObject();
            }
            
        }
     } 
    
     
     public void updateColumnWithFilter(String p_file_name, String p_file_type, String p_table_name, Map<String, String> p_filter_column, Map<String, String> p_column_to_update,DBDependencyInjection p_dbdi) throws DBProcessingException, DBValidationException{
        DBDependencyInjection temp_dbdi = this.dbdi;
       try{
           updateColumnWithFilter(p_file_name,p_file_type,p_table_name,p_filter_column,p_column_to_update);
           this.dbdi=p_dbdi;
           
           
       }catch (InvalidPathException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (IllegalCharsetNameException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedCharsetException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (SecurityException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException(ex.toString());
        }finally {
           this.dbdi=temp_dbdi;
            
        }
        
    }*/
    
    
    
    private void relationshipProcessing(String p_fileName, String p_fileType, int p_tableID, String... p_record_values)throws DBValidationException,DBProcessingException{
      
        Map<String,ArrayList<String>> l_relationship_filenames;
        ArrayList<String>l_rel_val;
        String[] l_mergedfilenames;
        String[] l_Rel_Col_Values;
        
        try{
            
            dbg("inside relationshipProcessing");
            createRelationshipMetaData(p_tableID); 
            if(i_TableRMD!=null){
            for(int i=0;i<i_TableRMD.length;i++){
            l_relationship_filenames= getRelationshipFileNames(i_TableRMD[i],p_fileName,p_fileType,p_record_values);
            
           Iterator iterator1=l_relationship_filenames.keySet().iterator();
            Iterator iterator2=l_relationship_filenames.values().iterator();
            while(iterator1.hasNext() && iterator2.hasNext() ){
                dbg("Relationship file name map key"+iterator1.next());
              
              
               dbg("Relationship file name value");
               
               l_rel_val=(ArrayList<String>)iterator2.next();
               for(String s:l_rel_val){
                   dbg(s);
               }
                
            }
            
           if(l_relationship_filenames.size()>0){
            l_mergedfilenames=mergeRelationshipFileNames(l_relationship_filenames);
            i_TableRMD[i].i_relationshipfilenames=l_mergedfilenames;
           }
           
          l_Rel_Col_Values=getRelationshipColumnValues(i_TableRMD[i],p_fileType,p_tableID, p_record_values);
          
          for(String s:l_Rel_Col_Values){
              
              dbg("l_Rel_Col_Values"+s);
          }
          
          i_TableRMD[i].i_relationshipcolumnvalues=l_Rel_Col_Values;
            dbg("relationship processing completed");
            } 
            }  
        } catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
         }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }
    
        }
    
    private void createRelationshipMetaData(int p_tableID)throws DBValidationException, DBProcessingException{
        
        try{
             IMetaDataService mds=dbdi.getMetadataservice();
             DBTable l_DBTable=mds.getTableMetaData(p_tableID,this.session);
             dbg("Inside createRelationshipMetaData");
             String[] l_Relationship=l_DBTable.getI_Relationship().split("%");
             dbg("l_Relationship.length"+l_Relationship.length);
             dbg("l_Relationship"+l_Relationship[0]);
             if(l_Relationship.length==1 && l_Relationship[0].equals("null")){
                 dbg("There is no relationship");
             }
             else
             {
             String[] l_OnlineChange=l_DBTable.getI_online_change().split("%");
             i_TableRMD  = new TableRMD[l_Relationship.length];
             
             String[] l_Rel_file=l_DBTable.getI_file_names_for_the_change().split("%");
                 
                         
            for(int i=0;i<l_Relationship.length;i++){
               dbg("inside for loop");
               
               String[] l_Fid_Tid=l_Relationship[i].split("~");
              dbg("fileid and tableid values are splitted");
              dbg("l_Fid_Tid[i]"+l_Fid_Tid[0]);
//             if(!l_Fid_Tid[0].equals("null")){
               i_TableRMD[i] =new TableRMD();
               i_TableRMD[i].i_fileId=l_Fid_Tid[0];
               
               i_TableRMD[i].i_tableId=l_Fid_Tid[1];
               i_TableRMD[i].i_online_change=l_OnlineChange[i];
        
             
            if(l_Rel_file[i].contains("&")){
                dbg("if condition is true");
              String[] l_Change_Tid_ColId=l_Rel_file[i].split("&");
              for(String s:l_Change_Tid_ColId){
                  dbg("l_Change_Tid_ColId"+s);
              }
              
              
              
              i_TableRMD[i].i_key_relationshipfilenames=new String[l_Change_Tid_ColId.length];
              for (int j=0;j<l_Change_Tid_ColId.length;j++)
              {   
                  //i_TableRMD[i].i_key_relationshipfilenames[j]=new String();
                  i_TableRMD[i].i_key_relationshipfilenames[j] = l_Change_Tid_ColId[j];
              
            }
            }
            else{
                dbg("if condition is false");
             i_TableRMD[i].i_key_relationshipfilenames=new String[1];   
              i_TableRMD[i].i_key_relationshipfilenames[0]= l_Rel_file[i];
            }
//             }
        }
             }
//            for(int i=0;i<i_TableRMD.length;i++){
//                dbg("fileid"+i_TableRMD[i].i_fileId);
//                dbg("tableid"+i_TableRMD[i].i_tableId);
//                dbg("online change"+i_TableRMD[i].i_online_change);
//                  for(int j=0;j<i_TableRMD[i].i_key_relationshipfilenames.length;j++){
//                      dbg("relationship file name"+i_TableRMD[i].i_key_relationshipfilenames[j]);
//                  }
//                
//            }
     
        
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }
    }
        
       private  Map<String,ArrayList<String>> getRelationshipFileNames(TableRMD tab,String p_fileName, String p_fileType,String... p_record_values)throws DBValidationException, DBProcessingException{
           boolean status=true;
           String l_tableId;
           String[] l_one_to_many;
           String l_tablename;
           int l_fileId;
           String[] l_col_Ids;
           
           Map<String, ArrayList<String>>l_table;
           ArrayList<String>l_record=new ArrayList<String>();;
          
           Map<String,ArrayList<String>>l_one_to_many_filenames=new HashMap<String,ArrayList<String>>();
           Map<String,ArrayList<String>>l_one_to_one_filenames= new HashMap<String,ArrayList<String>>();
           dbg("Inside getRelationshipFileNames");
           try{
               dbg(" inside try");
           IMetaDataService mds=dbdi.getMetadataservice();
           IDBReadService dbrs=dbdi.getDbreadservice();
           l_fileId=mds.getFileMetaData(p_fileType,this.session).getI_Fileid();
           dbg(""+l_fileId);
           for(int i=0;i<tab.i_key_relationshipfilenames.length;i++){
                ArrayList<String> l_cols_array =new  ArrayList<String>(); 
               if(tab.i_key_relationshipfilenames[i].contains("~")) {
                dbg("This is one to many relationship case");     
               l_one_to_many=tab.i_key_relationshipfilenames[i].split("~");
               l_tableId=l_one_to_many[0];
               dbg("l_one_to_many[0]"+l_one_to_many[0]);
               l_tablename=mds.getTableMetaData(l_fileId,Integer.parseInt(l_tableId),this.session).getI_TableName();
               try{
               dbg("before readtable inside get Relationship filenames");  
               dbg(p_fileName);
               dbg(p_fileType);
               dbg(l_tablename);
               l_table=dbrs.readTable(p_fileName, p_fileType, l_tablename,this.session);
               
               Iterator iterator1=l_table.values().iterator(); // To get all records of  required table
               while(iterator1.hasNext() ){
               l_record=(ArrayList<String>) iterator1.next(); 
               
               for(String s:l_record){
                   dbg("l_record"+s);
               }
               
              l_col_Ids=l_one_to_many[1].split(",");
              String l_rel_filenames =new String();
               for(int j=0;j<l_col_Ids.length;j++){
                   l_rel_filenames=l_rel_filenames.concat(l_record.get(Integer.parseInt(l_col_Ids[j])-1).trim());
               }
               dbg("rel filename is"+l_rel_filenames);
               if(l_cols_array.size() == 0){
                   l_cols_array.add(l_rel_filenames);
               }
               if (!(l_cols_array.contains(l_rel_filenames)))
                       {
               l_cols_array.add(l_rel_filenames);
                       
                       }
              
                // To indetentify the velue of required column
               /*if (l_cols_array.size() == 0) 
               {   
               l_cols_array.add(l_record.get(Integer.parseInt(l_one_to_many[1])-1));
               }
               if (!(l_cols_array.contains(l_record.get(Integer.parseInt(l_one_to_many[1])-1))))
                       {
               l_cols_array.add(l_record.get(Integer.parseInt(l_one_to_many[1])-1));
                       
                       }*/
               
               
               }
               l_one_to_many_filenames.put(tab.i_key_relationshipfilenames[i],l_cols_array);
               } catch(DBValidationException ex){
                   if(ex.toString().equals("DB_VAL_011")){
                       dbg("DB_VAL_011 is handled");
                   }
               }              
               }
           else{
                   dbg("This is one to one relationship case");
                   l_col_Ids=tab.i_key_relationshipfilenames[i].split(",");
                  String l_rel_filenames =new String();
                   for(int j=0;j<l_col_Ids.length;j++){
                   l_rel_filenames=l_rel_filenames.concat(p_record_values[Integer.parseInt(l_col_Ids[j])-1].trim());
                   }
                   
                   if(l_cols_array.size() == 0){
                   l_cols_array.add(l_rel_filenames);
                   }
                   
                   if (!(l_cols_array.contains(l_rel_filenames)))
                       {
                   l_cols_array.add(l_rel_filenames);
                       
                       }
                   for(String s:l_cols_array){
                       dbg("l_cols_array"+s);
                   }
                 //To indetentify the velue of required column
                /*l_cols_array.add(p_record_values[Integer.parseInt(tab.i_key_relationshipfilenames[i])-1]);*/
                
               l_one_to_one_filenames.put(tab.i_key_relationshipfilenames[i],l_cols_array);
               status=false;
              }
            }
           }
          
       catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }
           if(status){
           return l_one_to_many_filenames;
       }else{
           return l_one_to_one_filenames;
           }
        
        
       } 
       
       private String[] mergeRelationshipFileNames(Map<String,ArrayList<String>> p_relationship_filenames)throws DBProcessingException{
           String[] l_mergedFileNames;
           try{
   //        IBDProperties ibd=dbdi.getI_db_properties();
          
           ArrayList<String>l_FirstList=new ArrayList<String>();
           ArrayList<String>l_SecondList;
           ArrayList<String>l_MergedList=new ArrayList<String>();
          // String l_file_extension=ibd.getProperty("FILE_EXTENSION");
           
           dbg("inside mergerelationshipFileNames ");
           if(p_relationship_filenames.size()>0){
          // Iterator iterator1=p_relationship_filenames.keySet().iterator();
           Iterator iterator2=p_relationship_filenames.values().iterator();
           
               if(p_relationship_filenames.values().size()==1){
                   l_MergedList=(ArrayList<String>)iterator2.next();
                   
               }
               else{
               l_FirstList=(ArrayList<String>)iterator2.next();
                      
               while(iterator2.hasNext()){
                  l_SecondList=new ArrayList<String>();
                  l_SecondList=(ArrayList<String>)iterator2.next();
                  for(int i=0;i<l_FirstList.size();i++){
                      for(int j=0;j<l_SecondList.size();j++){
                          
                          //l_MergedList.add(l_FirstList.get(i).concat(l_SecondList.get(j).concat(l_file_extension)));
                          l_MergedList.add(l_FirstList.get(i).concat(l_SecondList.get(j)));
                      }
                  } 
                   
                  l_FirstList= l_MergedList;
                   
               }
           }
           
          l_mergedFileNames=new String[l_MergedList.size()];
          for(int i=0;i<l_MergedList.size();i++){
              l_mergedFileNames[i]=l_MergedList.get(i);
          }
           return l_mergedFileNames;
           } 
           }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
         }catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }
        return null;
        
       }
        
    private String[] getRelationshipColumnValues(TableRMD tab,String p_fileType,int p_tableID, String... p_record_values)throws DBValidationException ,DBProcessingException{
        
        IMetaDataService mds;
        try {
            mds=dbdi.getMetadataservice();
        } catch (NamingException ex) {
          throw new DBProcessingException("Exception".concat(ex.toString()));          
        }
        dbg("inside getRelationshipColumnValues");
        
        Map<String,String> l_col_Id_Values=new HashMap<String,String>();
        String[] l_Rel_Col_Values;
                
        try{
        for(int i=0;i<p_record_values.length;i++){
            
             if(!(mds.getColumnMetaData(p_fileType, p_tableID,(i+1),this.session).getI_ColumnLevelRelationship().equals("null")))
             l_col_Id_Values.put(mds.getColumnMetaData(p_fileType, p_tableID,(i+1),this.session).getI_ColumnLevelRelationship(), p_record_values[i]);
            
        }
        
   
          /* while(iterator1.hasNext() && iterator2.hasNext() ){
                
                dbg("key"+iterator1.next());
                dbg("value"+iterator2.next());
            }*/
        
        
        
        
        dbg("l_col_Id_Values map is created");
       
        String l_tableName=mds.getTableMetaData(Integer.parseInt(tab.i_tableId),this.session).getI_TableName();
        String l_fileType=mds.getFileMetaData(Integer.parseInt(tab.i_fileId),this.session).getI_FileType();
        l_Rel_Col_Values=new String[mds.getColumnCount(l_fileType, l_tableName)];
        
            
            for(int i=0;i<l_Rel_Col_Values.length;i++){
                Iterator iterator1=l_col_Id_Values.keySet().iterator();
                Iterator iterator2=l_col_Id_Values.values().iterator();
             while(iterator1.hasNext() && iterator2.hasNext()){
                if((i+1) ==Integer.parseInt((String)iterator1.next())){
                
                    l_Rel_Col_Values[i]=(String)iterator2.next();
                }
                else{
                    iterator2.next();
                }
                          
            
            }
            
            }
            
            
        
         
        /*while(iterator1.hasNext() && iterator2.hasNext()){
            for(int i=0;i<l_Rel_Col_Values.length;i++){
                for(int j=0;j<l_col_Ids.length;j++){
                    
            if(((String)iterator1.next()).equals(l_col_Ids[j])){
                  l_Rel_Col_Values[i]=(String)iterator2.next();
            }
            
            else{
                iterator2.next();
            }
                  
                }   
             }
            
        }*/
        
        
        
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception".concat(ex.toString()));
        }
        
        return l_Rel_Col_Values;
        
    }
    
    
    
    
    
     public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
    
}
