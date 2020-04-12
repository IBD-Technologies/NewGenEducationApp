/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.session;

import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.session.CohesiveSession;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 */
public class DBSession {
    
//  private ArrayList filenames_to_be_commited=new ArrayList();  
//private ArrayList filenames_to_be_commited;
private DBValidation dbv;
private IBDFileUtil iibd_file_util;
private Debug dbg;
//private boolean reportingDB;
//private  static final int bufferTimeOut;
//
//    public static int getBufferTimeOut() {
//        return bufferTimeOut;
//    }
public DBSession(CohesiveSession session) throws NamingException {

    if (dbv == null) {
        dbv = new DBValidation();
       }
//    if(bufferTimeOut==0){
//       bufferTimeOut= Integer.parseInt(session.getCohesiveproperties().getProperty("BUFFER_TIME_OUT"));
//    }
    this.dbv.setDebug(dbg);
    
    if (iibd_file_util == null) {
        iibd_file_util = new IBDFileUtil();
        
        iibd_file_util.setI_db_properties(session.getCohesiveproperties());
    }
//    if(session.getCohesiveproperties().getProperty("REPORTING_DB").equals("YES")){
//        reportingDB=true;
//    }else{
//        reportingDB=false;
//    }

}

public void createDBsession(CohesiveSession session){

  dbg =session.getDebug();
  this.dbv.setDebug(dbg);
  this.iibd_file_util.setDebug(dbg);
  
//  this.filenames_to_be_commited =session.getFilenames_to_be_commited();
//  dbg("object of filenames_to_be_commited is created");
//if(session.getCohesiveproperties().getProperty("REPORTING_DB").equals("NO")){
//  dbg("inside create dbsession ");
//}
 //this.filenames_to_be_commited =session.getFileNames_To_Be_Commited();   
}
public void clearSessionObject() {
        
        //errorhandler = null;
//        if(!reportingDB){
       // dbg("inside clear session object ");
//        }
        dbg = null;
        this.dbv.setDebug(dbg);
//        if(filenames_to_be_commited!=null){
//        filenames_to_be_commited.clear();
//        filenames_to_be_commited=null;
//        }
        }
    

//  public ArrayList getFileNames_To_Be_Commited(){
//      dbg("inside get method of filenames_to_be_commited");
//      dbg("filenames_to_be_commited size in get method"+filenames_to_be_commited.size());
//        return filenames_to_be_commited;
//    }
    
//    public void setFileNames_To_Be_Commited(String fileName){
//        if(!(filenames_to_be_commited.contains(fileName))){
//            
//          dbg("file names already in filenames_to_be_commited"+filenames_to_be_commited);  
//            
//        filenames_to_be_commited.add(fileName);
//        dbg("file name added in commit/rollback"+fileName);
//        dbg("filenames_to_be_commited size after adding"+fileName+"--->"+filenames_to_be_commited.size());
//        }
//    }
//    
//    public void deleteFileName_in_commit(String fileName){
//        filenames_to_be_commited.remove(fileName);
//        dbg(fileName+"removed from commit/rollback");
//    }
  
  public DBValidation getDbv() {
        //dbv = new DBValidation();
        return dbv;
    }
    public IBDFileUtil getIibd_file_util() { //EJB Ingtegration change
       
        return iibd_file_util;
    }
 private void dbg(String p_value){
     
        dbg.dbg(p_value);
    }
     private void dbg(Exception ex){
        dbg.exceptionDbg(ex);
    }


}




