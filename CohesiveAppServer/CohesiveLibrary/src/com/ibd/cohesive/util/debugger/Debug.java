/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util.debugger;
import java.util.UUID;
import com.ibd.cohesive.util.IBDProperties;
import java.io.FileNotFoundException;


/**
 *
 * @author IBD Technologies
 */
public class Debug {

     String debugOption;
     String debugRequired;
     String debugPath;
     String UserId;
     String Module;
     UUID Sid;
     CohesiveLogger debugObject;
     boolean timer_dbg_required=false;
     boolean arch_dbg_required=false;
     boolean util_debug_required=false;
//     boolean dbWrite_dbg_required=false;
//     boolean waitWrite_dbg_required=false;

    public CohesiveLogger getDebugObject() {
        return debugObject;
    }
     CohesiveLogger errorObject;

    public CohesiveLogger getErrorObject() {
        return errorObject;
    }

    public Debug() {
        
    }
     

    public Debug(String debugOption , String debugRequired , String debugPath,String userID,UUID sid,String module) {
        this.debugOption = debugOption;
        this.debugRequired = debugRequired;
        this.debugPath = debugPath;
        this.Sid=sid;
        this.UserId=userID;
        this.Module=module;
                
    }
    public Debug(boolean timer_dbg_required,boolean arch_dbg_required,boolean util_dbg_required,String debugOption , String debugRequired , String debugPath,String userID,UUID sid,String module) {
        this.debugOption = debugOption;
        this.debugRequired = debugRequired;
        this.debugPath = debugPath;
        this.Sid=sid;
        this.UserId=userID;
        this.Module=module;
        this.timer_dbg_required=  timer_dbg_required;      
        this.arch_dbg_required=arch_dbg_required;
        this.util_debug_required= util_dbg_required;
//        this.dbWrite_dbg_required=dbWrite_dbg_required;
//        this.waitWrite_dbg_required=waitWrite_dbg_required;
    }
  /* void getParameters(){
         try {
             IBDProperties i_db_properties = new IBDProperties();
             String l_debugOption;
             String l_debugRequired;
             String l_debugPath;
             // MetaDataService mds = new MetaDataService();
             
             i_db_properties.loadProperties("DBProperties.properties");
             l_debugOption = i_db_properties.getProperty("DEBUG_OPTION");
             l_debugRequired = i_db_properties.getProperty("DEBUG_REQUIRED");
             l_debugPath = i_db_properties.getProperty("DEBUG_PATH");
             g_dbDebug = new Debug(l_debugOption, l_debugRequired, l_debugPath);
         } catch (IOException ex) {
             Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
         }
   }*/

    public void dbg(String l_value) {
        String debugreq =null ;
        if(debugObject ==null && debugOption.equals("file"))
        {      
            try     
            {
                IBDProperties Userproperties = new IBDProperties();
                Userproperties.loadProperties(this.UserId+".properties");
                 debugreq=Userproperties.getProperty("DEBUG_REQUIRED");
                        
                if(debugreq.equals("NO"))
                 return;
                if(debugreq.equals("YES"))
                   if (debugOption == "file")  
                       if(this.Module.contains("TimerBean")){ 
//                      if(this.Module.contains("TimerBean")){      
                             if(this.timer_dbg_required) {
                                  debugObject = new CohesiveLogger(this.UserId,"Y",this.Module,this.Sid.toString(),false);
                              }
                             
                      }else if(this.Module.contains("Arch")){
                          
                          if(this.arch_dbg_required){
                              debugObject = new CohesiveLogger(this.UserId,"Y",this.Module,this.Sid.toString(),false);
                          }
                       
                      }
                      else{
                                  debugObject = new CohesiveLogger(this.UserId,"Y",this.Module,this.Sid.toString(),false);
                       }
                }  
                catch(FileNotFoundException ex)
                {   
                  //return;
                  debugObject=null;
                } 
                catch(Exception ex)
                {   
                  //return;
                  debugObject=null;
                } 
        
    try{ 
        if(debugRequired.equals("YES")){
         if (debugOption.equals("file")) 
          if(debugObject ==null)
            {    
             if(this.Module.contains("TimerBean")){      
                  if(this.timer_dbg_required) {   
                        debugObject = new CohesiveLogger(this.UserId,"Y",this.Module,this.Sid.toString(),false);
                  }
           }else if(this.Module.contains("Arch")){
                          
                   if(this.arch_dbg_required){
                         debugObject = new CohesiveLogger(this.UserId,"Y",this.Module,this.Sid.toString(),false);
                    }
             
           } else{
               debugObject = new CohesiveLogger(this.UserId,"Y",this.Module,this.Sid.toString(),false);
              }
        }    
        else if(debugRequired.equals("NO")){
            return;
        }
      }
    }
    catch(Exception ex)
                {   
                  //return;
                  debugObject=null;
                }
        }   
    
    try
       {   
        if((debugreq != null && debugreq.equals("YES")) || debugRequired.equals("YES"))
        {    
         if (debugOption.equals("terminal")) {
            System.out.println(l_value);
           return; 
           }
        if(debugObject !=null)
            {      
              String callerClassName = new Exception().getStackTrace()[1].getClassName();
              String callerMethodName = new Exception().getStackTrace()[1].getMethodName();
              if(this.util_debug_required )
              {
                  
              debugObject.println(callerClassName, callerMethodName, l_value);
              }
              else
              {
                if(!(callerClassName.contains("MetaDataService")||callerClassName.contains("PDataService")||callerClassName.contains("IBDFileUtil")))
                {      
                  debugObject.println(callerClassName, callerMethodName, l_value);
              
                }
              }  
         } 
            }
            }
    catch(Exception ex)
    {
       //System.out.println(ex.printStackTrace());
        System.out.println(ex.toString());
       
     }
}
    public void exceptionDbg(Exception ex) {
       try{ 
        //if(debugRequired.equals("YES")){
        if (debugOption.equals("terminal")) {
            ex.printStackTrace();

        }
        if (debugOption.equals("file")) {
            if(errorObject ==null)
             errorObject = new CohesiveLogger(this.UserId,"Y","Module",this.Sid.toString(),true);
           String callerClassName = new Exception().getStackTrace()[1].getClassName();
           String callerMethodName = new Exception().getStackTrace()[1].getMethodName();
        
            
            errorObject.printStackTrace(callerClassName, callerMethodName, ex);
        }
    
        
       }
      catch(Exception e)
      {
        System.out.println(e.toString());
       
      }
       }
    

}
