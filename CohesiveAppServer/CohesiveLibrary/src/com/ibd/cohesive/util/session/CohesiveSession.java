/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util.session;

import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.debugger.CohesiveLogger;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;




/**
 *
 * @author IBD Technologies
 */
public class CohesiveSession {
private ErrorHandler errorhandler;
private String userID;
private ArrayList<String>filenames_to_be_commited;
private static Object lock1 = new Object();

//private ArrayList filenames_to_be_commited;

    public ArrayList<String> getFilenames_to_be_commited() {
        return filenames_to_be_commited;
    }

    public void setFilenames_to_be_commited(ArrayList<String> filenames_to_be_commited) {
        this.filenames_to_be_commited = filenames_to_be_commited;
    }

    /*public ArrayList getFileNames_To_Be_Commited(){
        return filenames_to_be_commited;
    }*/
   /* 
    public void setFileNames_To_Be_Commited(String fileName){
        if(!filenames_to_be_commited.contains(fileName))
        filenames_to_be_commited.add(fileName);
        dbg("file name added in commit/rollback"+fileName);
    }
    
    public void deleteFileName_in_commit(String fileName){
        filenames_to_be_commited.remove(fileName);
        dbg(fileName+"removed from commit/rollback");
    }
    */
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    private Debug dbg;
    //private Random rand;
    //private ArrayList filenames_to_be_commited=new ArrayList();
    
    IBDProperties Cohesiveproperties;
        public IBDProperties getCohesiveproperties() {
        return Cohesiveproperties;
    }

    public void setCohesiveproperties(IBDProperties properties) {
        this.Cohesiveproperties = properties;
    }
    String i_debugOption;
    String i_debugRequired;
    String i_debugPath;
    UUID i_session_identifier;
    boolean timer_dbg_required;
    boolean arch_dbg_required;
    boolean util_dbg_required;
//    boolean dbWrite_dbg_required;
//    boolean waitWrite_dbg_required;
    
private boolean i_session_created_now;
  
  public CohesiveSession() {
        try {
            Cohesiveproperties = new IBDProperties();
            Cohesiveproperties.loadProperties("DBProperties.properties");
            
            i_debugOption = Cohesiveproperties.getProperty("DEBUG_OPTION");
            i_debugRequired = Cohesiveproperties.getProperty("DEBUG_REQUIRED");
            i_debugPath = Cohesiveproperties.getProperty("DEBUG_PATH");
            
            if(Cohesiveproperties.getProperty("TIMER_BEAN_DEBUG").equals("NO")){
                timer_dbg_required=false;
            }else{
                timer_dbg_required=true;
            }
            if(Cohesiveproperties.getProperty("ARCH_DEBUG").equals("NO")){
                arch_dbg_required=false;
            }else{
                arch_dbg_required=true;
            }
            if(Cohesiveproperties.getProperty("UTIL_DEBUG").equals("NO")){
                util_dbg_required=false;
            }else{
                util_dbg_required=true;
            }
            
//             if(Cohesiveproperties.getProperty("DBWRITE_DEBUG").equals("NO")){
//                dbWrite_dbg_required=false;
//            }else{
//                dbWrite_dbg_required=true;
//            }
//            if(Cohesiveproperties.getProperty("WAITWRITE_DEBUG").equals("NO")){
//                waitWrite_dbg_required=false;
//            }else{
//                waitWrite_dbg_required=true;
//            }
            
            if(i_debugOption.equals("terminal"))
            {   
              CohesiveLogger.WRITE_TO_FILE="N";
             // CohesiveLogger.DONT_WRITE_TO_FILE="Y";
            }
            else 
            {   
              CohesiveLogger.WRITE_TO_FILE="Y";
              //CohesiveLogger.DONT_WRITE_TO_FILE="N"; 
            }
            
            if (CohesiveLogger.LOG_FOLDER_SEPERATOR ==null || CohesiveLogger.LOG_FOLDER_SEPERATOR.equals(""))
            {
            CohesiveLogger.LOG_FOLDER_SEPERATOR=Cohesiveproperties.getProperty("FOLDER_DELIMITER");
            CohesiveLogger.LOG_FILE_PATH =Cohesiveproperties.getProperty("LOG_FILE_PATH");
            //CohesiveLogger.IF_DEFAULT_FILE_EXTENSION =Cohesiveproperties.getProperty("IF_DEFAULT_FILE_EXTENSION");
            CohesiveLogger.LOG_CONETENT_DATE_DISPLAY_FORMAT =Cohesiveproperties.getProperty("LOG_CONETENT_DATE_DISPLAY_FORMAT");
            CohesiveLogger.LOG_FILE_DATE_DISPLAY_FORMAT =Cohesiveproperties.getProperty("LOG_FILE_DATE_DISPLAY_FORMAT");
            CohesiveLogger.LOG_BUFFER_SIZE=Integer.parseInt(Cohesiveproperties.getProperty("LOG_BUFFER_SIZE"));    
            CohesiveLogger.LOG_FILE_SIZE=Integer.parseInt(Cohesiveproperties.getProperty("LOG_FILE_SIZE"));    
            }
            
            
            
            
        } catch (FileNotFoundException ex) {
            //dbg(ex);
        } catch (IOException ex) {
            dbg(ex);

        }
    }
    
  public CohesiveSession(String gatewayProperties) {
        try {
            Cohesiveproperties = new IBDProperties();
            Cohesiveproperties.loadProperties(gatewayProperties);
            i_debugOption = Cohesiveproperties.getProperty("DEBUG_OPTION");
            i_debugRequired = Cohesiveproperties.getProperty("DEBUG_REQUIRED");
            i_debugPath = Cohesiveproperties.getProperty("DEBUG_PATH");
            
            if(i_debugOption.equals("terminal"))
            {   
              CohesiveLogger.WRITE_TO_FILE="N";
             // CohesiveLogger.DONT_WRITE_TO_FILE="Y";
            }
            else 
            {   
              CohesiveLogger.WRITE_TO_FILE="Y";
              //CohesiveLogger.DONT_WRITE_TO_FILE="N"; 
            }
            
            if (CohesiveLogger.LOG_FOLDER_SEPERATOR ==null || CohesiveLogger.LOG_FOLDER_SEPERATOR.equals(""))
            {
            CohesiveLogger.LOG_FOLDER_SEPERATOR=Cohesiveproperties.getProperty("FOLDER_DELIMITER");
            CohesiveLogger.LOG_FILE_PATH =Cohesiveproperties.getProperty("LOG_FILE_PATH");
            //CohesiveLogger.IF_DEFAULT_FILE_EXTENSION =Cohesiveproperties.getProperty("IF_DEFAULT_FILE_EXTENSION");
            CohesiveLogger.LOG_CONETENT_DATE_DISPLAY_FORMAT =Cohesiveproperties.getProperty("LOG_CONETENT_DATE_DISPLAY_FORMAT");
            CohesiveLogger.LOG_FILE_DATE_DISPLAY_FORMAT =Cohesiveproperties.getProperty("LOG_FILE_DATE_DISPLAY_FORMAT");
            CohesiveLogger.LOG_BUFFER_SIZE=Integer.parseInt(Cohesiveproperties.getProperty("LOG_BUFFER_SIZE"));    
            CohesiveLogger.LOG_FILE_SIZE=Integer.parseInt(Cohesiveproperties.getProperty("LOG_FILE_SIZE"));    
            }
            
            
            
            
        } catch (FileNotFoundException ex) {
            //dbg(ex);
        } catch (IOException ex) {
            dbg(ex);

        }
    }
  
  
    public static UUID dataToUUID(String... params) {
//      StringBuilder builder = new StringBuilder();
//      for (String param : params) {
//        builder.append(param);
    synchronized(lock1){
      return UUID.randomUUID();
     }
//    return UUID.nameUUIDFromBytes(builder.toString().getBytes(StandardCharsets.UTF_8));
}
public static UUID dataToUUID() {
//      StringBuilder builder = new StringBuilder();
//      for (String param : params) {
//        builder.append(param);
//     }
 synchronized(lock1){
      return UUID.randomUUID();
 }
//    return UUID.nameUUIDFromBytes(builder.toString().getBytes(StandardCharsets.UTF_8));
}
    public boolean isI_session_created_now() {
        return i_session_created_now;
    }
    

    public void setI_session_created_now(boolean i_session_created_now) {
        this.i_session_created_now = i_session_created_now;
    }
   
    public ErrorHandler getErrorhandler() {
        if (errorhandler == null) {
            errorhandler = new ErrorHandler(this.dbg);
        }

        return errorhandler;
    }

    public void setErrorhandlerdummy() {
        if (errorhandler != null) {
            errorhandler = null;
        }

    }
  /*  
    public ArrayList getFileNames_To_Be_Commited(){
        return filenames_to_be_commited;
    }
    
    public void setFileNames_To_Be_Commited(String fileName){
        if(!filenames_to_be_commited.contains(fileName))
        filenames_to_be_commited.add(fileName);
        dbg("file name added in commit/rollback"+fileName);
    }
    
    public void deleteFileName_in_commit(String fileName){
        filenames_to_be_commited.remove(fileName);
        dbg(fileName+"removed from commit/rollback");
    }
*/
    public void createSessionObject() {
            i_session_created_now = false;
           if(i_session_identifier==null)
            {
               
            //dbg= new Debug(i_debugOption, i_debugRequired, i_debugPath);
             String callerClassName = new Exception().getStackTrace()[1].getClassName();
             i_session_identifier = dataToUUID("sessionID",callerClassName);
             dbg= new Debug(timer_dbg_required,arch_dbg_required,util_dbg_required,i_debugOption, i_debugRequired, i_debugPath,userID,i_session_identifier,callerClassName);
//             dbg= new Debug(i_debugOption, i_debugRequired, i_debugPath,userID,i_session_identifier,callerClassName);
             filenames_to_be_commited=new ArrayList();
             dbg("Caller ClassName:"+callerClassName);
             dbg("SessionID :" +i_session_identifier);
             
             errorhandler = new ErrorHandler(dbg);
             i_session_created_now = true;
           // filenames_to_be_commited = new ArrayList();
            }else{
         //   dbg("already created session id"+i_session_identifier);
           }
         
       
    }
    
    public void createGatewaySessionObject() {
            i_session_created_now = false;
           if(i_session_identifier==null)
            {
               
            //dbg= new Debug(i_debugOption, i_debugRequired, i_debugPath);
             String callerClassName = new Exception().getStackTrace()[1].getClassName();
             i_session_identifier = dataToUUID("sessionID",callerClassName);
             dbg= new Debug(i_debugOption, i_debugRequired, i_debugPath,userID,i_session_identifier,callerClassName);
             dbg("Caller ClassName:"+callerClassName);
             dbg("SessionID :" +i_session_identifier);
             
             //errorhandler = new ErrorHandler();
             //i_session_created_now = true;
            //filenames_to_be_commited = new ArrayList();
            }else{
            //dbg("already created session id"+i_session_identifier);
           }
         
       
    }
    
    public UUID getI_session_identifier(){
        return i_session_identifier;
    }

    public void clearSessionObject() {
        
        errorhandler = null;
        if (dbg.getDebugObject()!=null)
        {    
        dbg.getDebugObject().flushLogger();
        dbg.getDebugObject().closeLogger();
        
        }
        if (dbg.getErrorObject()!=null)
        {    
        dbg.getErrorObject().flushLogger();
        dbg.getErrorObject().closeLogger();
        
        }
                
        dbg = null;
        i_session_identifier =null;
        if(filenames_to_be_commited!=null){
        filenames_to_be_commited.clear();
        filenames_to_be_commited=null;
        }
        setUserID(null); 
    }
    
    
    public void clearGatewaySessionObject() {
        
        //errorhandler = null;
        if (dbg.getDebugObject()!=null)
        {    
        dbg.getDebugObject().flushLogger();
        dbg.getDebugObject().closeLogger();
        }
        if (dbg.getErrorObject()!=null)
        {    
        dbg.getErrorObject().flushLogger();
        dbg.getErrorObject().closeLogger();
        
        }
                
        dbg = null;
        i_session_identifier =null;
        //filenames_to_be_commited.clear();
        //filenames_to_be_commited=null;
        setUserID(null); 
    }
    
    public Debug getDebug() {
        return dbg;
    }
    
    private void dbg(String p_value){
//        String l_reportingDB=Cohesiveproperties.getProperty("REPORTING_DB");
//        String l_reportingDB=i_db_properties.getProperty("REPORTING_DB");
        
//        if(l_reportingDB.equals("NO")){
          dbg.dbg(p_value);
//        }
    }
     private void dbg(Exception ex){
        dbg.exceptionDbg(ex);
        if (dbg.getErrorObject()!=null)
        {    
        dbg.getErrorObject().flushLogger();
        dbg.getErrorObject().closeLogger();
        
        }
         
    }
    
}
