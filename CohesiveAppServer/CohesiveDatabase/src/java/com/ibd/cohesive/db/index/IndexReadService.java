/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.index;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies 
 * Change Description :cross call is used to indicate that when function of one service calls function of other service through the temporary method where DBDI is assigned and then calls the original method then session object
    created should not be recreated again in the called method.so to indicate this we use the cross call instance variable.
    in the called method crosscall field is set to true initially.when it calls the original method the original method identifies 
    whether it is  a cross call or not and creates the session object accordingly.      
 * Changed by : Suriya Narayanan.L
 * Changed on : 14/05/2018
 * Search Tag : CODEREVIEW_3
 * 
 * 
 * Change Description:i_indexcore instance variable must be changed
 * to local variable. 
 * Change by :Suriya Narayanan.L 
 * Change on:15/05/2018 Search
 * Tag:CODEREVIEW_9
 * 
 *  
 */
public class IndexReadService implements IIndexReadService {

    //IIndexCoreService i_indexcore;//CODEREVIEW_9
    //int i_dummy;//To help in lambda expression
    //boolean crossCall = false;//CODEREVIEW_3
    //DBValidation dbv;
    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;

    public IndexReadService() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession = new DBSession(session);

        // i_indexcore = DBDependencyInjection.getIndexcoreservice();//CODEREVIEW_9
        //dbv = new DBValidation(i_indexcore);
        // dbv = dbdi.getDbv();
    }

    /*this is a cross call function which returns the start position of the record whose primary key is given by p_pkey param. the file name given by file name in param.
   in param is filename tableid , primary key and DBDependencyInjection
   out param is position of the record*/
    public int readIndex(String p_fileName, String p_tableid, String p_pkey, CohesiveSession session) throws DBValidationException, DBProcessingException {
        CohesiveSession tempSession = this.session;
        int l_index;
        try {

            //this.crossCall = true;
            this.session = session;
            l_index = readIndex(p_fileName, p_tableid, p_pkey);

        } catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());
        } finally {
            //this.crossCall = false;
            this.session = tempSession;
        }
        return l_index;
    }

    /*
    This function reads the index collection Map and returns the position for the particular pkey
    this function returns the start position of the record whose primary key is given by p_pkey param. the file name given by file name in param.
    it does so by the help of the index core map
   in param is filename tableid and primary key 
   out param is position of the record
     */
    @Override
    public int readIndex(String p_fileName, String p_tableid, String p_pkey) throws DBValidationException, DBProcessingException {
        int l_dummy=-1;
        boolean l_session_created_now =false;
        try {
            IIndexCoreService i_indexcore = dbdi.getIndexcoreservice();//CODEREVIEW_9
            session.createSessionObject();
            dbSession.createDBsession(session);
            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errorhandler = session.getErrorhandler();
            l_session_created_now= session.isI_session_created_now();

            boolean l_validation_status = true;
            
            if (!dbv.specialCharacterValidation(p_fileName, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if(!dbv.specialCharacterValidation(Integer.parseInt(p_tableid), errorhandler)){
                l_validation_status = false;
                errorhandler.log_error();
            }
            if (!dbv.tableIDValidation(Integer.parseInt(p_tableid), errorhandler,dbdi)) {
                l_validation_status = false;
                errorhandler.log_error();

            }
            if(!dbv.fileNameValidation(p_fileName, errorhandler,session,dbdi)){
                l_validation_status = false;
                errorhandler.log_error();
                
            }
            /*if (!dbv.pkValidation(Integer.parseInt(p_tableid), p_pkey, errorhandler)) {
                l_validation_status = false;
                errorhandler.log_error();
            }*/
            if (!l_validation_status) {
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            Iterator iterator=i_indexcore.getIndex_map().keySet().iterator();
            Iterator iterator1=i_indexcore.getIndex_map().values().iterator();
            while(iterator.hasNext()&iterator1.hasNext()){
                Map<String,String> l_inner_map =(HashMap) iterator1.next();
                String l_outer_map_temp_key=(String)iterator.next();
                if(l_outer_map_temp_key.equals(p_tableid.concat("~").concat(p_fileName))){
                   Iterator iterator2= l_inner_map.keySet().iterator();
                   Iterator iterator3= l_inner_map.values().iterator();
                   while(iterator2.hasNext()&iterator3.hasNext()){
                       String l_temp_inner_map_key=(String)iterator2.next();
                       String l_temp_inner_map_values=(String)iterator3.next();
                       if(l_temp_inner_map_key.equals(p_pkey)){
                           l_dummy=Integer.parseInt(l_temp_inner_map_values);
                       }
                       
                   }
                   
                    
                }
                
            }
            

          /*  i_indexcore.getIndex_map().forEach((String k, Map<String, String> v) -> {
                dbg("index_map key" + k);
                if (k.equals(p_tableid.concat("~").concat(p_fileName))) {
                    v.forEach((String ki, String vi) -> {
                        dbg("inner_map key" + ki);
                        if (ki.equals(p_pkey)) {
                            dbg("inner_map value" + vi);
                            i_dummy = Integer.parseInt(vi);
                            return;
                        }

                    });
                }

            });*/

        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new DBProcessingException("ConcurrentModificationException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
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
        return l_dummy;
    }

    public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }

}
