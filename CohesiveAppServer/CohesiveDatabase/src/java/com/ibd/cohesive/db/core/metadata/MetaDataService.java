/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.metadata;

import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import javax.ejb.DependsOn;
import javax.ejb.Stateless;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 *
 * Change Description :removal of i_dbfile field .since declared but not used.
 * Changed by : Suriya Narayanan.L 
 * Changed on : 14/05/2018 
 * Search Tag : CODEREVIEW_2
 *
 *
 * Change Description :cross call is used to indicate that when function of one service calls function of other service through the temporary method where DBDI is assigned and then calls the original method then session object
    created should not be recreated again in the called method.so to indicate this we use the cross call instance variable.
    in the called method crosscall field is set to true initially.when it calls the original method the original method identifies 
    whether it is  a cross call or not and creates the session object accordingly.      
 * Changed by : Suriya Narayanan.L
 * Changed on : 14/05/2018
 * Search Tag : CODEREVIEW_3
 *
 * Change Description :validation exception must be thrown collectively not for
 * each validation.and l_validation_exception boolean variable must be created.
 * Changed by : Suriya Narayanan.L 
 * Changed on : 14/05/2018 
 * Search Tag : CODEREVIEW_4
 *
 *
 * Change Description :since table name is not unique it alone can not be used
 * to get table meta data. 
 * Changed by : Suriya Narayanan.L 
 * Changed on :14/05/2018 
 * Search Tag : CODEREVIEW_5
 *
 * Change Description :since table name is not unique it alone can not be used
 * to get table meta data we have to include filetype as well in the table name
 * validation. 
 * Changed by : Suriya Narayanan.L 
 * Changed on : 14/05/2018
 * Search Tag : CODEREVIEW_6
 *
 * Change Description :To validate the columnname we have to include the
 * filetype and the tablename.and in metadata we don't have the column value
 * only column name 
 * Changed by : Suriya Narayanan.L 
 * Changed on : 14/05/2018
 * Search Tag : CODEREVIEW_7
 * 
 * Change Description :Cross call functions is added 
 * Changed by   :Isac
 * Changed on   :05/09/2018
 * Search Tag   :Cohesive1_Dev_3
 */
@DependsOn({"DBCoreService"})
@Stateless
public class MetaDataService implements IMetaDataService {

    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    
    
    public MetaDataService() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession= new DBSession(session);
    }
    
    /*This is a cross call function that is other services' method calls this function it will call the actuall function 
    in param is filetype and DBDependency injection.
    out param is DBFile*/
    @Override
    public DBFile getFileMetaData(String p_FileType, CohesiveSession session) throws DBValidationException, DBProcessingException {
        CohesiveSession tempSession = this.session;
        DBFile l_dbfile;
        try {
           // this.crossCall = true;
            this.session = session;
            dbg("in Metadataservice ->getFileMetaData->p_FileType" + p_FileType);
            dbg("in Metadataservice ->getFileMetaData->session" + session);

            l_dbfile = getFileMetaData(p_FileType);
            dbg("in Metadataservice ->getFileMetaData->dbfile" + l_dbfile.getI_FileType());

        } catch (Exception ex) {

            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            this.session = tempSession;
           // this.crossCall = false;
            
        }
        return l_dbfile;
    }
    
    /* This function returns the DBFile by traversing the dbcoremap.
    in param is filetype .
    outparam is DBFile*/

    @Override
    public DBFile getFileMetaData(String p_FileType) throws DBValidationException, DBProcessingException {
            DBFile l_dbfile=null;
             boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            DBValidation dbv = dbSession.getDbv();
            boolean l_validation_status = true;//CODEREVIEW_4. l_validation_exception variable is created to help to throw the validation exception collectively
            ErrorHandler errorhandler = session.getErrorhandler();
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                l_validation_status = false;//CODEREVIEW_4.to help in throwing validation exception collectively this variable is created
                errorhandler.log_error();
                //throw new DBValidationException(errorhandler.getSession_error_code().toString()); //CODEREVIEW_4.validation exception must be thrown collectively not for individual validation

            }

            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                l_validation_status = false;//CODEREVIEW_4.to help in throwing validation exception collectively this variable is created
                errorhandler.log_error();
                //throw new DBValidationException(errorhandler.getSession_error_code().toString());//CODEREVIEW_4.validation exception must be thrown collectively not for individual validation

            }
            /*CODEREVIEW_4. validation exception must be thrown collectively not for individual validation. so this if statement is created*/
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }

            Map<String, DBFile> dbcoremap ;

            //dbcore = new DBCoreService();
            dbg("inside getFileMetaData-->p_FileType--> " + p_FileType);
            IDBCoreService idbcs = dbdi.getDbcoreservice(); //EJB Integration change
            dbcoremap = idbcs.getG_dbMetaData();
            dbg("inside getFileMetaData->dbcoremap.size() " + dbcoremap.size());
            //Set <String> dbcoreMapKeySet=dbcoremap.keySet();

            Iterator  iterator1=dbcoremap.keySet().iterator();
            Iterator  iterator2=dbcoremap.values().iterator();
            while(iterator1.hasNext()&iterator2.hasNext()){
                String l_sample=(String)iterator1.next();
                DBFile l_dbfile1=(DBFile)iterator2.next(); 
                dbg("inside getFileMetaData->l_sample " + l_sample);
                if((l_sample).equals(p_FileType)){
                    dbg("inside getFileMetaData->inside if->");
                    l_dbfile=l_dbfile1;
                    dbg("inside getFileMetaData->inside if->l_dbfile.getI_FileType()"+l_dbfile.getI_FileType());
            }
            }
            
           /* dbcoremap.forEach((String k, DBFile v) -> {
                dbg("inside getFileMetaData dbcoremap.forEach((k, v) k value-> " + k);
                if (k.equals(p_FileType)) {
                    dbfile = v;

                    dbg("inside getFileMetaData dbcoremap.forEach((k, v)  v.getI_FileType()->" + v.getI_FileType());

                }
            });*/
            
      
            

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException");
        }catch(DBValidationException ex){
            throw ex;
        } 
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if (l_session_created_now) {
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
           
        }
        
        return l_dbfile;

    }
/*This function returns the DBFile by traversing the dbcoremap.
    in param is fileid .
    outparam is DBFile*/
    @Override
    public DBFile getFileMetaData(int p_Fileid) throws DBValidationException, DBProcessingException {
        DBFile l_dbfile=null;
        boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errorhandler = session.getErrorhandler();
            boolean l_validation_status = true;//CODEREVIEW_4. l_validation_exception variable is created to help to throw the validation exception collectively

            if (!dbv.specialCharacterValidation(p_Fileid, errorhandler)) {
                l_validation_status = false;//CODEREVIEW_4.to help in throwing validation exception collectively this variable is created
                errorhandler.log_error();
                // throw new DBValidationException(errorhandler.getSession_error_code().toString());//CODEREVIEW_4.validation exception must be thrown collectively not for individual validation

            }

            if (!dbv.fileIDValidation(p_Fileid, errorhandler,dbdi)) {
                l_validation_status = false;//CODEREVIEW_4.to help in throwing validation exception collectively this variable is created
                errorhandler.log_error();
                // throw new DBValidationException(errorhandler.getSession_error_code().toString());//CODEREVIEW_4.validation exception must be thrown collectively not for individual validation so this statement is commented

            }
            /*CODEREVIEW_4. validation exception must be thrown collectively not for individual validation. so this if statement is created*/
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            Map<String, DBFile> dbcoremap;
             

            //dbcore = new DBCoreService();
            //dbg("inside getFileMetaData " +p_FileType);
            
            IDBCoreService idbcs = dbdi.getDbcoreservice(); //EJB Integration change
            dbcoremap = idbcs.getG_dbMetaData();
            dbg("in metadata service->inside getFileMetaData->dbcoremap.size()-> " +dbcoremap.size());
            //Set <String> dbcoreMapKeySet=dbcoremap.keySet();
            
           // Iterator  iterator1=dbcoremap.keySet().iterator();
            Iterator  iterator2=dbcoremap.values().iterator();
            while(iterator2.hasNext()){
                DBFile l_sample_dbfile=(DBFile)iterator2.next();
               if(l_sample_dbfile.getI_Fileid()==p_Fileid){
                    l_dbfile=l_sample_dbfile;
               }
            }

            /*dbcoremap.forEach((String k, DBFile v) -> {
                if (v.getI_Fileid() == p_Fileid) {
                    dbfile = v;

                }

            });*/
             dbg("in metadata service->inside getFileMetaData->dbfile-> " +l_dbfile.getI_FileType());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        finally {
            if(l_session_created_now)
            {     
            session.clearSessionObject();
            dbSession.clearSessionObject();
            }
        }
       return l_dbfile;
    }
     //Cohesive1_Dev_3 starts here
    @Override
     public DBFile getFileMetaData(int p_Fileid,CohesiveSession session) throws DBValidationException, DBProcessingException{
         CohesiveSession tempSession = this.session;
         DBFile l_dbfile=null;
        
        try {
            this.session = session;
            
            l_dbfile=getFileMetaData(p_Fileid);
            
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            this.session = tempSession; 
        }
          
     return l_dbfile;
     }
    
    //Cohesive1_Dev_3 ends here
     public DBTable getTableMetaData(int p_Fileid, int p_Tableid,CohesiveSession session) throws DBValidationException, DBProcessingException{
         CohesiveSession tempSession = this.session;
        DBTable l_dbtable;
        
        try {
           // this.crossCall = true;
            this.session = session;
            
            dbg("in metadata service->in getTableMetaData with dbdi->");
            l_dbtable = getTableMetaData(p_Fileid,  p_Tableid);
           dbg("in Metadataservice ->getTableMetaData->l_dbtable" + l_dbtable.getI_TableName());

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            this.session = tempSession;
          //  this.crossCall = false;
           
        }
        return l_dbtable;
     }
    
    
/*this method returns the DBTable by traversing the DBFile.
  in param is fileid and tableid
    out param is DBTable*/
    @Override
    public DBTable getTableMetaData(int p_Fileid, int p_Tableid) throws DBValidationException, DBProcessingException {
            DBTable l_dbtable=null;
            boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            dbg("inside get table metadata");
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            boolean l_validation_status = true;

            if (!dbv.specialCharacterValidation(p_Fileid, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_Tableid, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.fileIDValidation(p_Fileid, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.tableIDValidation(p_Tableid, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
             dbg("validation completed in getTableMetaData");
            // dbg("inside getTableMetaData " + p_FileType    +   p_TableName);
            //DBCore dbcore =new DBCoreService();
            //dbcore.start();
            DBFile dbf = getFileMetaData(p_Fileid);
            dbg("inside getTableMetaData dbf.getI_FileType()" + dbf.getI_FileType());
            DBTable l_sample_dbtable=null;
             Iterator iterator1=dbf.getI_TableCollection().values().iterator();
             while(iterator1.hasNext()){
                 l_sample_dbtable=(DBTable)iterator1.next();
                 if(l_sample_dbtable.getI_Tableid()==p_Tableid){
                     l_dbtable=l_sample_dbtable;
                 }
                 
             }

           /* dbf.getI_TableCollection().forEach((k, v) -> {

                if (v.getI_Tableid() == p_Tableid) {

                    dbg("inside getTableMetaData v.getI_TableName() " + v.getI_TableName());
                    dbtable = v;

                }

            });*/
            dbg("inside getTableMetaData dbtable.getI_TableName() " + l_dbtable.getI_TableName());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        } 
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if(l_session_created_now)
            {    
            session.clearSessionObject();
            dbSession.clearSessionObject();
            }
        }
        return l_dbtable;
    }

    @Override
    public DBTable getTableMetaData(int p_Tableid, CohesiveSession session) throws DBValidationException, DBProcessingException {
        CohesiveSession tempSession = this.session;
        DBTable l_dbtable=null;
        try {
            //this.crossCall = true;
            this.session = session;
             l_dbtable = getTableMetaData(p_Tableid);

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            this.session = tempSession;
           // this.crossCall = false;
            
        }
        return l_dbtable;
    }
/* This function returns the DBTable by traversing the dbcoremap
    in param is tableid.
    outparam is DBTable.*/
    @Override
    public DBTable getTableMetaData(int p_Tableid) throws DBValidationException, DBProcessingException {
        DBTable l_dbtable=null;
        boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            boolean l_validation_status = true;//CODEREVIEW_4. l_validation_exception variable is created to help to throw the validation exception collectively
            if (!dbv.specialCharacterValidation(p_Tableid, errorhandler)) {
                errorhandler.log_error();
                l_validation_status = false;//CODEREVIEW_4.to help in throwing validation exception collectively this variable is created
                //throw new DBValidationException(errorhandler.getSession_error_code().toString());//CODEREVIEW_4.validation exception must be thrown collectively not for individual validation

            }

            if (!dbv.tableIDValidation(p_Tableid, errorhandler,dbdi)) {
                l_validation_status = false;//CODEREVIEW_4.to help in throwing validation exception collectively this variable is created
                errorhandler.log_error();
                //throw new DBValidationException(errorhandler.getSession_error_code().toString());//CODEREVIEW_4.validation exception must be thrown collectively not for individual validation

            }
            /*CODEREVIEW_4. validation exception must be thrown collectively not for individual validation. so this if statement is created*/
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }

            Map<String, DBFile> dbcoremap;
            

            IDBCoreService idbcs = dbdi.getDbcoreservice(); //EJB Integration change
            dbcoremap = idbcs.getG_dbMetaData();
            Iterator iterator1=dbcoremap.values().iterator();
            while(iterator1.hasNext()){
                DBFile l_dbfile=(DBFile)iterator1.next();
                Iterator iterator2=l_dbfile.getI_TableCollection().values().iterator();
                while(iterator2.hasNext()){
                    DBTable l_dbtable1=(DBTable)iterator2.next();
                    if(l_dbtable1.getI_Tableid()==p_Tableid){
                     l_dbtable  =  l_dbtable1; 
                    }
                }
                
            }
            
            /*dbcoremap.forEach((String k, DBFile v) -> {
                v.getI_TableCollection().forEach((String kc, DBTable vc) -> {
                    if (vc.getI_Tableid() == p_Tableid) {
                        dbtable = vc;
                    }
                });
            });*/

        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        } 
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            if (l_session_created_now) {
                session.clearSessionObject();
                dbSession.clearSessionObject();
                
            }
            
        }
        return l_dbtable;
    }
//CODEREVIEW_5 starts here. Table name alone can not be used to get table meta data since table name is not unique.so this part of the code is commented

    /*public DBTable getTableMetaData(String p_TableName, DBDependencyInjection p_dbdi) throws DBValidationException, DBProcessingException {

        DBDependencyInjection temp_dbdi = this.dbdi;
        try {
            this.crossCall = true;
            this.dbdi = p_dbdi;
            DBTable dbtable = getTableMetaData(p_TableName);

        } catch (Exception ex) {
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            this.crossCall = false;
            this.dbdi = temp_dbdi;
        }
        return dbtable;
    }*/

 /* public DBTable getTableMetaData(String p_TableName) throws DBValidationException, DBProcessingException {
        try {
            dbdi.createSessionObject();
            ErrorHandler errorhandler = dbdi.getErrorhandler();
            DBValidation dbv = dbdi.getDbv();
            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                errorhandler.log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString());

            }

            if (!dbv.tableNameValidation(p_TableName, errorhandler)) {
                errorhandler.log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString());

            }

            Map<String, DBFile> dbcoremap = new HashMap();

            IDBCoreService idbcs = DBDependencyInjection.getDbcoreservice();
            dbcoremap = idbcs.getG_dbMetaData();
            dbcoremap.forEach((String k, DBFile v) -> {
                v.getI_TableCollection().forEach((String kc, DBTable vc) -> {
                    if (vc.getI_TableName().equals(p_TableName)) {
                        dbtable = vc;
                    }
                });
            });

        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            if (this.crossCall == false) {
                dbdi.clearSessionObject();
            }
        }
        return dbtable;
    }CODEREVIEW_5 starts here*/
    
    /*This is a cross call function it calls the actual getTableMetaData function.
    inparam is filetype and DBDependency injection.
    out param is DBTable.*/
    @Override
    public DBTable getTableMetaData(String p_FileType, String p_TableName, CohesiveSession session) throws DBValidationException, DBProcessingException {
        DBTable l_dbtable=null;
        CohesiveSession tempSession = this.session;
        try {

            //this.crossCall = true;
            this.session = session;
             l_dbtable = getTableMetaData(p_FileType, p_TableName);

        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            //this.crossCall = false;
            this.session = tempSession;
        }
        return l_dbtable;
    }
/*This is function returns the DBTable by traversing the DBFile.
    inparam is filetype and DBDependency injection.
    out param is DBTable.*/
    public DBTable getTableMetaData(String p_FileType, String p_TableName) throws DBValidationException, DBProcessingException {
       DBTable l_dbtable=null;
       boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();

            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                // throw new DBValidationException("In getTableMetaData(String p_FileType,String p_TableName) function FileName is not valid ");
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                // throw new DBValidationException("In getTableMetaData(String p_FileType,String p_TableName) function FileName is not valid ");
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                // throw new DBValidationException("In getTableMetaData(String p_FileType,String p_TableName) function FileName is not valid ");
                l_validation_status = false;
                errorhandler.log_error();
            }
            // if (!dbv.tableNameValidation(p_TableName, errorhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_FileType, p_TableName, errorhandler,dbdi)) {
                //throw new DBValidationException("In getTableMetaData(String p_FileType,String p_TableName) function p_TableName is not valid ");
                l_validation_status = false;
                errorhandler.log_error();
            }

            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }

            dbg("inside getTableMetaData " + p_FileType + p_TableName);
            //DBCore dbcore =new DBCoreService();
            //dbcore.start();

            DBFile dbf = getFileMetaData(p_FileType);
            dbg("inside getTableMetaData dbf.getI_FileType()" + dbf.getI_FileType());
           Iterator iterator1=dbf.getI_TableCollection().values().iterator();
           while(iterator1.hasNext()){
               DBTable l_dbtable1=(DBTable)iterator1.next();
               if(l_dbtable1.getI_TableName().equals(p_TableName)){
                   l_dbtable =l_dbtable1;
               }
           }

            /*dbf.getI_TableCollection().forEach((String k, DBTable v) -> {

                if (v.getI_TableName().equals(p_TableName)) {

                    dbg("inside getTableMetaData v.getI_TableName() " + v.getI_TableName());
                    dbtable = v;

                }

            });*/
            
            dbg("inside getTableMetaData dbtable.getI_TableName() " + l_dbtable.getI_TableName());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if (l_session_created_now) {
                session.clearSessionObject();
                dbSession.clearSessionObject();
                
            }
            
        }
        return l_dbtable;
    }
/*This function returns the DBColumn by traversing the DBTable.
    in param is file type ,table name and column name
    out param is DBColumn*/
    @Override
    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, String p_ColumnName) throws DBValidationException, DBProcessingException {
        DBColumn l_dbcolumn=null;
        boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            dbg("inside getColumnMetaData p_FileType p_TableName p_ColumnName" + p_FileType + p_TableName + p_ColumnName);
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_ColumnName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            // if (!dbv.tableNameValidation(p_TableName, errorhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_FileType, p_TableName, errorhandler,dbdi)) {
                l_validation_status = false;

                errorhandler.log_error();
            }
            //if (!dbv.columnValueValidation(p_ColumnName, errorhandler)) {//CODEREVIEW_7
            if (!dbv.columnNameValidation(p_FileType, p_TableName, p_ColumnName, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }

            DBTable dbtable = getTableMetaData(p_FileType, p_TableName);
            dbg("inside getColumnMetaData dbtable.getI_TableName() " + dbtable.getI_TableName());
            Map<String, DBColumn> l_columnCollection = dbtable.getI_ColumnCollection();
            Iterator iterator=l_columnCollection.values().iterator();
            while(iterator.hasNext()){
                DBColumn l_dbcolumn1 = (DBColumn)iterator.next();
                if(l_dbcolumn1.getI_ColumnName().equals(p_ColumnName)){
                    l_dbcolumn=l_dbcolumn1;
                }
                
            }

          /*  l_columnCollection.forEach((String k, DBColumn v) -> {
                  if (v.getI_ColumnName().equals(p_ColumnName)) {
                    dbcolumn = v;
                }

            });*/
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if(l_session_created_now)
            {     
             session.clearSessionObject();
             dbSession.clearSessionObject();
            }
        }
        return l_dbcolumn;
    }

    @Override
    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, String p_ColumnName,CohesiveSession session) throws DBValidationException, DBProcessingException {
        CohesiveSession tempSession = this.session;
        DBColumn l_dbcolumn;
        try {

            //this.crossCall = true;
            this.session = session;
            l_dbcolumn = getColumnMetaData(p_FileType, p_TableName, p_ColumnName);

        }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            //this.crossCall = false;
            this.session = tempSession;
        }
        return l_dbcolumn;
    }
    /*public DBColumn getColumnMetaData(String p_TableName, String p_ColumnName) throws DBValidationException, DBProcessingException {
        try {
            dbdi.createSessionObject();
            ErrorHandler errorhandler = dbdi.getErrorhandler();
            DBValidation dbv = dbdi.getDbv();

            boolean l_validation_status = true;

            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_ColumnName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }

            if (!dbv.tableNameValidation(p_TableName, errorhandler)) {
                l_validation_status = false;

                errorhandler.log_error();
            }
            if (!dbv.columnValueValidation(p_ColumnName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }

            //dbg("inside getColumnMetaData p_FileType p_TableName p_ColumnName" + p_FileType + p_TableName + p_ColumnName);
            DBTable dbtable = getTableMetaData(p_TableName);
            dbg("inside getColumnMetaData dbtable.getI_TableName() " + dbtable.getI_TableName());
            Map<String, DBColumn> l_columnCollection = dbtable.getI_ColumnCollection();

            l_columnCollection.forEach((String k, DBColumn v) -> {
                /* if(v.getI_ColumnName().equals(p_ColumnName)){
              dbcolumn=v;
              
          }
                if (v.getI_ColumnName().equals(p_ColumnName)) {
                    dbcolumn = v;
                }

            });
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            dbdi.clearSessionObject();
        }
        return dbcolumn;
    }*/
    
    /*This function is a cross call function it calls the actual getColumnMetaData function.
    in param is file type,table name ,column id and dbdependency injection
    outparam is DBColumn*/
    @Override
    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, int p_column_id, CohesiveSession session) throws DBValidationException, DBProcessingException {
        CohesiveSession tempSession = this.session;
        DBColumn l_dbcolumn;
        try {

            //this.crossCall = true;
            this.session = session;
            l_dbcolumn = getColumnMetaData(p_FileType, p_TableName, p_column_id);

        }catch(DBValidationException ex){
            throw ex;
        } 
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            //this.crossCall = false;
            this.session = tempSession;
        }
        return l_dbcolumn;
    }
 /*  this function returns the DBColumn by traversing the DBTable
    in param is filetype,table name,and column id
    out param is DBColumn.*/
    @Override
    public DBColumn getColumnMetaData(String p_FileType, String p_TableName, int p_column_id) throws DBValidationException, DBProcessingException {
        DBColumn l_dbcolumn=null;
        boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_column_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }

            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            // if (!dbv.tableNameValidation(p_TableName, errorhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_FileType, p_TableName, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.columnIDValidation(p_FileType, p_TableName, p_column_id, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            DBTable l_dbtable = getTableMetaData(p_FileType, p_TableName);
           Iterator iterator1= l_dbtable.getI_ColumnCollection().values().iterator();
           while(iterator1.hasNext()){
               
               DBColumn l_dbcolumn1 = (DBColumn)iterator1.next();
               if(l_dbcolumn1.getI_ColumnID()==p_column_id){
                   l_dbcolumn=l_dbcolumn1;
               }
               
           }
            
           /* l_dbtable.getI_ColumnCollection().forEach((String k, DBColumn v) -> {
                if (v.getI_ColumnID() == p_column_id) {
                    dbcolumn = v;
                }
            });*/
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if (l_session_created_now) {
                session.clearSessionObject();
                dbSession.clearSessionObject();
                
            }
            
        }
        return l_dbcolumn;
    }
/*  This function returns the DBColumn by traversing the DBTable.
    in param is file type,tableid and column id
    out param is DBColumn*/
    @Override
    public DBColumn getColumnMetaData(String p_FileType, int p_TableId, int p_column_id) throws DBValidationException, DBProcessingException {
         DBColumn l_dbcolumn1=null,l_dbcolumn=null;
         boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv = dbSession.getDbv();
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_TableId, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_column_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }

            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.tableIDValidation(p_TableId, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            String l_table_name = this.getTableMetaData(p_TableId).getI_TableName();
            if (!dbv.columnIDValidation(p_FileType, l_table_name, p_column_id, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            
            DBTable l_dbtable = getTableMetaData(p_TableId);
           Iterator iterator1= l_dbtable.getI_ColumnCollection().values().iterator();
           while(iterator1.hasNext()){
                l_dbcolumn1=(DBColumn)iterator1.next();
                if(l_dbcolumn1.getI_ColumnID()==p_column_id){
                    l_dbcolumn=l_dbcolumn1;
                }
           }
            /*l_dbtable.getI_ColumnCollection().forEach((String k, DBColumn v) -> {
                if (v.getI_ColumnID() == p_column_id) {
                    dbcolumn = v;
                }
            });*/
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if(l_session_created_now)
            {   
            session.clearSessionObject();
            dbSession.clearSessionObject();
            }
        }
        return l_dbcolumn;
    }
    
    //Cohesive1_Dev_3 starts here
    @Override
     public DBColumn getColumnMetaData(String p_FileType, int p_TableId, int p_column_id,CohesiveSession session) throws DBValidationException, DBProcessingException{
       DBColumn  l_dbcolumn=null;
       CohesiveSession tempSession = this.session;
       
       try{
           
           this.session=session;
           l_dbcolumn=getColumnMetaData(p_FileType,p_TableId,p_column_id);
       }catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
           this.session=tempSession;
            
        }
         
       
       return l_dbcolumn;
     }
    //Cohesive1_Dev_3 ends here
     
    @Override
    public String[] getFileNamesToChange(int p_file_id, int p_table_id,CohesiveSession session) throws DBValidationException, DBProcessingException{
      
       String[] l_array_of_file_names_to_change={};
       CohesiveSession tempSession = this.session;
        try{
          
     // this.crossCall=true;
      
       this.session=session;
      l_array_of_file_names_to_change=getFileNamesToChange(p_file_id,p_table_id);
      }catch(DBValidationException ex){
            throw ex;
        }
      catch(Exception ex){
          dbg(ex);
          throw new DBProcessingException("Exception"+ex.toString());
          
      }finally{
          //crossCall=false;
          this.session=tempSession;
          
      }
       return l_array_of_file_names_to_change;
       
    }
    
    
    
    
    public String[] getFileNamesToChange(int p_file_id, int p_table_id) throws DBValidationException, DBProcessingException {
        String[] l_array_of_file_names_to_change = {};
        boolean l_session_created_now =false;
        try{
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            ErrorHandler errorhandler = session.getErrorhandler();
            DBValidation dbv=dbSession.getDbv();
            boolean l_Validation_status = true;
            if(!dbv.specialCharacterValidation(p_file_id, errorhandler)){
                l_Validation_status=false;
                errorhandler.log_error();
            }
            if(!dbv.specialCharacterValidation(p_table_id, errorhandler)){
                l_Validation_status=false;
                errorhandler.log_error();
            }
            if(!dbv.fileIDValidation(p_file_id, errorhandler,dbdi)){
                l_Validation_status=false;
                errorhandler.log_error();
            }
            if(!dbv.tableIDValidation(p_table_id, errorhandler,dbdi)){
                l_Validation_status=false;
                errorhandler.log_error();
            }
            if(!l_Validation_status){
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            
       
        String l_aggregation_of_file_names_to_change = getTableMetaData(p_file_id, p_table_id).getI_file_names_for_the_change();

        l_array_of_file_names_to_change = l_aggregation_of_file_names_to_change.split("%");

        return l_array_of_file_names_to_change;
        }
        catch(PatternSyntaxException ex){
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException"+ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }
        catch(Exception ex){
            dbg(ex);
            throw new DBProcessingException("Exception"+ex.toString());
        }finally{
            if(l_session_created_now){
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
           
        }
    }

    /* public DBColumn getColumnMetaData(String p_file_type,String p_TableName, int p_column_id) throws DBValidationException, DBProcessingException {
        try {
            dbdi.createSessionObject();
            DBValidation dbv = dbdi.getDbv();
            ErrorHandler errorhandler = dbdi.getErrorhandler();
            boolean l_validation_status = true;

            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_column_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }

            if (!dbv.tableNameValidation(p_TableName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.columnIDValidation(p_column_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            DBTable l_dbtable = getTableMetaData(p_TableName);
            l_dbtable.getI_ColumnCollection().forEach((String k, DBColumn v) -> {
                if (v.getI_ColumnID() == p_column_id) {
                    dbcolumn = v;
                }
            });
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            dbdi.clearSessionObject();
        }
        return dbcolumn;
    }*/

 /*  public DBColumn getColumnMetaData(int p_TableId, int p_column_id) throws DBValidationException, DBProcessingException {
        try {
            dbdi.createSessionObject();
            ErrorHandler errorhandler = dbdi.getErrorhandler();
            DBValidation dbv = dbdi.getDbv();
            boolean l_validation_status = true;

            if (!dbv.specialCharacterValidation(p_TableId, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.specialCharacterValidation(p_column_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }

            if (!dbv.tableIDValidation(p_TableId, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.columnIDValidation(p_column_id, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            DBTable l_dbtable = getTableMetaData(p_TableId);
            l_dbtable.getI_ColumnCollection().forEach((String k, DBColumn v) -> {
                if (v.getI_ColumnID() == p_column_id) {
                    dbcolumn = v;
                }
            });
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            dbdi.clearSessionObject();
        }
        return dbcolumn;
    }*/
    /*this function is a cross call function it calls the actual isIndexRequired function.
    in param is filetype and DBDependencyInjection.
    outparam is boolean*/
    @Override
    public boolean isIndexRequired(String p_file_type, CohesiveSession session) throws DBProcessingException, DBValidationException {
        CohesiveSession tempSession = this.session;
         boolean l_index_required = false;
        try {
            //this.crossCall = true;
            this.session = session;
            l_index_required = isIndexRequired(p_file_type);
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            this.session = tempSession;
           // this.crossCall = false;
        }
        return l_index_required;
    }
/*This function traverse the DBFile and returns the boolean value for the specified file type
    in param is file type and out param is boolean*/
    public boolean isIndexRequired(String p_file_type) throws DBProcessingException, DBValidationException {
       DBFile l_dbfile1=null;
       boolean l_index_required=false;
       boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errorhandler = session.getErrorhandler();
            boolean l_validation_status = true;
            if (!dbv.fileTypeValidation(p_file_type, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            Iterator iterator=dbdi.getDbcoreservice().getG_dbMetaData().values().iterator();
            while(iterator.hasNext()){
                l_dbfile1=(DBFile)iterator.next();
                if(l_dbfile1.getI_FileType().equals(p_file_type)){
                    l_index_required=l_dbfile1.isI_index_required();
                }
            
        }
           /* DBDependencyInjection.getDbcoreservice().getG_dbMetaData().forEach((String kc, DBFile vc) -> {
                if (vc.getI_FileType().equals(p_file_type)) {
                    i_index_required = vc.isI_index_required();
                }
            });*/
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if (l_session_created_now) {
                session.clearSessionObject();
                dbSession.clearSessionObject();
            }
            
        }
        return l_index_required;
    }
/*  This function counts the no of columns by calculating the size of the column collection field of the DBTable.
    in param is filetype and table name.
    out param is size of the column collection*/
    @Override
    public int getColumnCount(String p_FileType, String p_TableName) throws DBValidationException, DBProcessingException {
        DBTable l_dbtable=null;
        boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();
            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errorhandler = session.getErrorhandler();
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            //if (!dbv.tableNameValidation(p_TableName, errorhandler)) {//CODEREVIEW_6
            if (!dbv.tableNameValidation(p_FileType, p_TableName, errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            dbg("in MetadataService->getColumnCount->p_FileType"+p_FileType);
            dbg("in MetadataService->getColumnCount->p_TableName"+p_TableName);
            l_dbtable = getTableMetaData(p_FileType, p_TableName);
            dbg("in MetadataService->getColumnCount->l_dbtable"+l_dbtable.getI_TableName());
            
        }catch(DBValidationException ex){
            throw ex;
        } 
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            if(l_session_created_now)
            {     
            session.clearSessionObject();
            dbSession.clearSessionObject();
            }
        }
        return (l_dbtable.getI_ColumnCollection().size());

    }
/* This function counts the no of tables by calculating the size of the table collection field of the DBFile.
   in param is file type and out  param is  size of the table collection*/
    @Override
    public int getTableCount(String p_FileType) throws DBValidationException, DBProcessingException {
         DBFile l_dbfile=null;
         boolean l_session_created_now =false;
        try {
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now= session.isI_session_created_now();

            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errorhandler = session.getErrorhandler();
            boolean l_validation_status = true;
            if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                errorhandler.log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
                //throw new DBValidationException("DB_VAL_001");
            }
            if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                errorhandler.log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
                //throw new DBValidationException("DB_VAL_001");
            }
             l_dbfile = getFileMetaData(p_FileType);
        }catch(DBValidationException ex){
            throw ex;
        } 
        catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());

        } finally {
            if(l_session_created_now)
            {     
            session.clearSessionObject();
            dbSession.clearSessionObject();
            }
        }
        return (l_dbfile.getI_TableCollection().size());

    }
    
    @Override
    public String getPrimaryKey(int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException {
        String l_primaryKey = null;
        try {
           // IMetaDataService mds = dbdi.getMetadataservice();
            DBTable dbtable = this.getTableMetaData(p_tableID, this.session);
            String l_pk = dbtable.getI_Pkey();
            String[] l_pk_ids = l_pk.split("~");
            l_primaryKey = p_record_values[Integer.parseInt(l_pk_ids[0]) - 1].trim();
            //dbg("p_record_values[Integer.parseInt(l_pk_ids[0])-1]" + p_record_values[Integer.parseInt(l_pk_ids[0]) - 1] + "is");
            int counter = l_pk_ids.length - 1;//already one value is assigned to l_primaryKey
            int i = 1;
            while (counter > 0) {
                l_primaryKey = l_primaryKey.concat("~").concat(p_record_values[Integer.parseInt(l_pk_ids[i]) - 1].trim());
                counter--;
                i++;
            }

            //l_primaryKey.trim();
            dbg("l_primaryKey" + l_primaryKey + "is");

        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }

        return l_primaryKey;
    }
    
    @Override
    public String getPrimaryKey(CohesiveSession session,int p_tableID, String... p_record_values) throws DBValidationException, DBProcessingException{
        CohesiveSession tempSession = this.session;
        String l_primaryKey = null;
        try {
            
            this.session = session;
            l_primaryKey=getPrimaryKey(p_tableID,p_record_values);
        }catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        }catch(DBValidationException ex){
            throw ex;
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        }finally {
            this.session = tempSession;
        }
        return l_primaryKey;
    }

    public void dbg(String p_Value) {
        
        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }
}
