/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.index;

import com.ibd.cohesive.db.core.IDBCoreService;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.metadata.MetaDataService;//EJB Integration change
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.PositionAndRecord;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class IndexCoreService implements IIndexCoreService {

    DBDependencyInjection dbdi;
    CohesiveSession session;
    DBSession dbSession;
    
    //DBValidation dbv;
    private Map<String, Map<String, String>> index_map = new HashMap<String, Map<String, String>>();// This is Index collection for Each table Id.
    //Each table id contains the inner Collection which has key as primary Key value and value as position

    //public Map<String,String> inner_map = new HashMap();
    public IndexCoreService() throws NamingException {
        dbdi = new DBDependencyInjection();
        session = new CohesiveSession();
        dbSession=new DBSession(session);
        //dbv = dbdi.getDbv();

    }

    public Map<String, Map<String, String>> getIndex_map() {
        return index_map;
    }

    public void setIndex_map(Map<String, Map<String, String>> index_map) {
        this.index_map = index_map;
    }

    /*This function creates Map whose key is a combination of table id and filename and value is another map which is called as inner map.
    inner_map's key is primary key value and innermap value is start position of the record containing this 
    primary key value.
    
    @in_param index file's name. here it is index.
     */
    public void start(String p_index_fileName) throws DBValidationException, DBProcessingException {
       // Scanner l_File_Content = null;
        boolean l_session_created_now=false;
        try {
            
            //dbg("dbdi.toString()"+dbdi.toString());
            session.createSessionObject();
            dbSession.createDBsession(session);
            l_session_created_now=session.isI_session_created_now();
            DBValidation dbv = dbSession.getDbv();
            ErrorHandler errorhandler = session.getErrorhandler();
            dbg("in indexcore start ->p_index_fileName->" + p_index_fileName);
            // dbg("dbv"+dbv);
            if (!dbv.specialCharacterValidation(p_index_fileName, errorhandler)) {
                errorhandler.log_error();
                //dbg("errorhandler.getSession_error_code().toString()" + errorhandler.getSession_error_code().toString());
                throw new DBValidationException(errorhandler.getSession_error_code().toString());

            }
            /*if (!dbv.fileNameValidation(p_index_fileName, errorhandler)) {
                errorhandler.log_error();
                dbg("errorhandler.getSession_error_code().toString()" + errorhandler.getSession_error_code().toString());
                throw new DBValidationException(errorhandler.getSession_error_code().toString());

            }*/

            dbg("inside ->IndexCoreService ->start->p_index_fileName " + p_index_fileName);
            //dbg("inside ->IndexCoreService ->start->dbcs.getClass() " +dbcs.getClass());

            String[] column_values;
            Map<String, String> l_inner_map = new HashMap();

            int array_index = 0;
            String l_PK=null;
            String[] l_PK_Column;
            int l_PK_Column_Size;
            String inner_map_key;
            String samp=null;
            IDBCoreService dbcs = dbdi.getDbcoreservice();//EJB Integration change
            //DBDependencyInjection dbdi = new DBDependencyInjection();
            IMetaDataService mds = dbdi.getMetadataservice(); //EJB Integration change

           // l_File_Content = new Scanner(new BufferedReader(new FileReader(dbcs.getI_db_properties().getProperty("DATABASE_HOME_PATH") + p_index_fileName + dbcs.getI_db_properties().getProperty("FILE_EXTENSION"))));
           // l_File_Content.useDelimiter(dbcs.getI_db_properties().getProperty("RECORD_DELIMITER"));
           // while (l_File_Content.hasNext()) {

           //     column_values = l_File_Content.next().split(dbcs.getI_db_properties().getProperty("COLUMN_DELIMITER"));
                String[] l_dummy_pk={};
                Map<String,String> l_dummy_map=new HashMap();
                PositionAndRecord par = dbSession.getIibd_file_util().sequentialRead(p_index_fileName, 0,l_dummy_pk, l_dummy_map, session,dbdi);
                Iterator iterator = par.getI_records().iterator();
                while(iterator.hasNext()){
                    samp=(String)iterator.next();
                    column_values=samp.split(session.getCohesiveproperties().getProperty("COLUMN_DELIMITER"));
                //dbg("inside indexcore service ->column_values[0]" + column_values[0]);
                //dbg("inside index core service" + mds.toString());
                l_PK = mds.getTableMetaData(Integer.parseInt(column_values[0]), this.session).getI_Pkey();
                dbg("inside IndexCoreService l_PK -> primary key" + l_PK);
                l_PK_Column = l_PK.split(session.getCohesiveproperties().getProperty("COLUMN_DELIMITER"));
                l_PK_Column_Size = l_PK_Column.length;
                array_index = 2;
                inner_map_key = column_values[array_index];
                --l_PK_Column_Size;
                ++array_index;
                while (l_PK_Column_Size > 0) {
                    inner_map_key = inner_map_key.concat(session.getCohesiveproperties().getProperty("COLUMN_DELIMITER")).concat(column_values[array_index]);
                    l_PK_Column_Size--;
                    ++array_index;
                }
                if (index_map.containsKey(column_values[0].concat("~").concat(column_values[1]))) {
                    index_map.get(column_values[0].concat("~").concat(column_values[1])).put(inner_map_key, column_values[array_index]);
                } else {
                    l_inner_map.put(inner_map_key, column_values[array_index]);
                    index_map.put((column_values[0].concat("~").concat(column_values[1])), l_inner_map);
                    l_inner_map = new HashMap();
                    //index_map.get(column_values[1]).put(inner_map_key, column_values[array_index]);
                }
//inner_map.put(inner_map_key,column_values[array_index] );
                // index_map.put(column_values[1], inner_map);
            }
            
            
        } catch (NumberFormatException ex) {
            dbg(ex);
            throw new DBProcessingException("NumberFormatException" + ex.toString());
        } catch (PatternSyntaxException ex) {
            dbg(ex);
            throw new DBProcessingException("PatternSyntaxException" + ex.toString());
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        } catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        } catch (NullPointerException ex) {
            dbg(ex);
            throw new DBProcessingException("NullPointerException" + ex.toString());
        } catch (ClassCastException ex) {
            dbg(ex);
            throw new DBProcessingException("ClassCastException" + ex.toString());
        } catch (NoSuchElementException ex) {
            dbg(ex);
            throw new DBProcessingException("NoSuchElementException" + ex.toString());
        } catch (IllegalStateException ex) {
            dbg(ex);
            throw new DBProcessingException("IllegalStateException" + ex.toString());
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
        //displayMap(index_map);
        
    }

    public void dbg(String p_value) {
        session.getDebug().dbg(p_value);
    }

    public void dbg(Exception ex) {
        session.getDebug().exceptionDbg(ex);
    }

    /* private void displayMap(Map<String, Map<String, String>> index_map) {
        index_map.forEach((k, v) -> {
            dbg("index_map key" + k);
            v.forEach((ki, vi) -> {
                dbg("inner_map key" + ki);
                dbg("inner_map value" + vi);
            });

        });
    }*/
}
