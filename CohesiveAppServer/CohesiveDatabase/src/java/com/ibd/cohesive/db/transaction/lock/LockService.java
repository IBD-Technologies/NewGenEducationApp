/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.transaction.lock;

import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.BEAN;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.NamingException;
/**
 *
 * @author IBD Technologies
 */
@Startup
@DependsOn({"DBCoreService","DBWaitBuffer"})
@Singleton
@ConcurrencyManagement(BEAN)


public class LockService implements ILockService{
   // private ConcurrentHashMap<String,UUID> i_Locked_FileNames ;
    //private ConcurrentHashMap<String,UUID> i_rec_lock_map;
    DBDependencyInjection dbdi;
    private Map<String,UUID> i_Locked_FileNames ;
    private Map<String,UUID> i_rec_lock_map;
    private Map<String,UUID> i_io_lock_map;
    private Map<String,UUID> i_archival_lock_map;
    private long archivalSequenceNo=0;
    private static int initialCapacity;
    private static float loadFactor;
    private static int concurrencyLevel;
    private long sequenceNo;
    
    static
    {
     
        try{
        
         CohesiveSession tempSession= new CohesiveSession();
         initialCapacity= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_INITIAL_CAPACITY"));
         loadFactor= Float.parseFloat(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_LOAD_FACTOR"));
         concurrencyLevel= Integer.parseInt(tempSession.getCohesiveproperties().getProperty("FILE_CON_HMAP_CONCURRENCY_LEVEL"));
        
        }catch(Exception ex){
            initialCapacity=0;
        }
    }
    
    //
    public LockService()throws NamingException,DBProcessingException{
        dbdi = new DBDependencyInjection();
        
        if(initialCapacity==0){
            i_Locked_FileNames = new ConcurrentHashMap();
            i_rec_lock_map =new ConcurrentHashMap();
            i_io_lock_map=new ConcurrentHashMap();
        }else{
            i_Locked_FileNames = new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
            i_rec_lock_map =new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
            i_io_lock_map=new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
            
        }
       
       
       i_archival_lock_map=new ConcurrentHashMap(10,0.75f,5);
        archivalSequenceNo=Long.parseLong(getCurrentYearMonth());
        sequenceNo=Long.parseLong(createSequenceNo());
    // i_Locked_FileNames = new HashMap();
      //  i_rec_lock_map =new HashMap();
    }

    public synchronized long getArchivalSequenceNo() {
        return archivalSequenceNo++;
    }

    public long getSequenceNo() {
        return sequenceNo++;
    }
    
    
    
    public synchronized boolean createLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
        //DBDependencyInjection temp_dbdi = p_dbdi;
        UUID l_session_identifier;
        boolean l_islocked =false;
        
        
        try{
             //this.dbdi=p_dbdi;
             l_session_identifier=session.getI_session_identifier();
             dbg("i_Locked_FileNames.size()"+i_Locked_FileNames.size(),session);
              if(!(i_Locked_FileNames.containsKey(p_fileName))){
             i_Locked_FileNames.putIfAbsent(p_fileName, l_session_identifier);  
              }   
              
             if (i_Locked_FileNames.get(p_fileName).equals(l_session_identifier))
                {
             l_islocked = true;
            dbg(p_fileName+"added to i_Locked_FileNames",session);
             dbSession.getIibd_file_util().deleteTempFile(p_fileName);
//             Iterator<String> iterator1=i_Locked_FileNames.keySet().iterator();
//             Iterator<UUID> iterator2=i_Locked_FileNames.values().iterator();
//             while(iterator1.hasNext()&&iterator2.hasNext()){
//                 dbg("filename in lock"+iterator1.next(),p_dbdi);
//                 dbg("session id"+iterator2.next(),p_dbdi);
//             }
             }
//              }
         else
        {
            dbg(p_fileName+"already in the i_Locked_FileNames",session);
          l_islocked = false;              
        }
                         
        }catch (IllegalArgumentException ex) {
            dbg(ex,session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        }catch (UnsupportedOperationException ex) {
            dbg(ex,session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
        finally 
        {
         return  l_islocked;  
        }
        
    }
 
    
    public synchronized boolean createImplictRecordLock(String p_fileName,String p_table_name,String p_primary_key,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
        //DBDependencyInjection temp_dbdi = p_dbdi;
        UUID l_session_identifier;
        boolean l_islocked =false;
       String l_rec_lock_key = p_fileName.concat("@").concat(p_table_name).concat("@").concat(p_primary_key);
        
        try{
             //this.dbdi=p_dbdi;
             l_session_identifier=session.getI_session_identifier();
             dbg("i_rec_lock_map.size()"+i_rec_lock_map.size(),session);
             // if(!(i_rec_lock_map.containsKey(l_rec_lock_key))){
             i_rec_lock_map.putIfAbsent(l_rec_lock_key, l_session_identifier);  
              //}   
              
             if (i_rec_lock_map.get(l_rec_lock_key).equals(l_session_identifier))
                {
             l_islocked = true;
             dbg("record implict Lock is created",session);
            dbg(p_fileName+"added to i_rec_lock_map",session);
            // dbSession.getIibd_file_util().deleteTempFile(p_fileName);
//             Iterator<String> iterator1=i_Locked_FileNames.keySet().iterator();
//             Iterator<UUID> iterator2=i_Locked_FileNames.values().iterator();
//             while(iterator1.hasNext()&&iterator2.hasNext()){
//                 dbg("filename in lock"+iterator1.next(),p_dbdi);
//                 dbg("session id"+iterator2.next(),p_dbdi);
//             }
             }
//              }
         else
        {
            dbg(l_rec_lock_key+"already in the i_rec_lock_map",session);
          l_islocked = false;              
        }
         return  l_islocked;                  
        }catch (IllegalArgumentException ex) {
            dbg(ex,session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        }catch (UnsupportedOperationException ex) {
            dbg(ex,session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
        finally 
        {
         
        }
        
    }
    
 public ArrayList<String> getImplictLockedRecords(CohesiveSession session,DBSession dbSession)throws DBProcessingException{
        //DBDependencyInjection temp_dbdi = p_dbdi;
        UUID l_session_identifier;
        boolean l_islocked =false;
     //  String l_rec_lock_key = p_fileName.concat("~").concat(p_table_id).concat("~").concat(p_primary_key);
       ArrayList<String> l_lockedRecords =new ArrayList<String>();
        dbg("Inside getImplictLockedRecords" ,session);
            
       try{
             //this.dbdi=p_dbdi;
             l_session_identifier=session.getI_session_identifier();
             dbg("i_rec_lock_map.size()"+i_rec_lock_map.size(),session);
             // if(!(i_rec_lock_map.containsKey(l_rec_lock_key))){
             //i_rec_lock_map.putIfAbsent(l_rec_lock_key, l_session_identifier);  
              //}   
              if(i_rec_lock_map.containsValue(l_session_identifier))
              {   
                Iterator<String> lrecIterator=i_rec_lock_map.keySet().iterator();
                   while(lrecIterator.hasNext()){
                       String reckey= lrecIterator.next();
                       if(i_rec_lock_map.get(reckey).equals(l_session_identifier))
                         l_lockedRecords.add(reckey);           
                                    }
                  } 
                
                
                dbg("getImplictLockedRecords No of recorss:"+l_lockedRecords.size(),session);
          //  dbg(p_fileName+"added to i_rec_lock_map",session);
            // dbSession.getIibd_file_util().deleteTempFile(p_fileName);
//             Iterator<String> iterator1=i_Locked_FileNames.keySet().iterator();
//             Iterator<UUID> iterator2=i_Locked_FileNames.values().iterator();
//             while(iterator1.hasNext()&&iterator2.hasNext()){
//                 dbg("filename in lock"+iterator1.next(),p_dbdi);
//                 dbg("session id"+iterator2.next(),p_dbdi);
//             }
            
            //dbg(l_rec_lock_key+"already in the i_rec_lock_map",session);
         
         return  l_lockedRecords;                 
        }catch (IllegalArgumentException ex) {
            dbg(ex,session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        }catch (UnsupportedOperationException ex) {
            dbg(ex,session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
        finally 
        {
          
        }
        
    }
       
    
    
    
    
    
    
public  boolean isLocked(String p_fileName ,CohesiveSession session)throws DBProcessingException{
    
    try{
    if(i_Locked_FileNames.containsKey(p_fileName)){
        
        return true;
        
    }
    else{
        return false;
    }
    } catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
}
public synchronized boolean isSameSessionLock(String p_fileName,CohesiveSession session)throws DBProcessingException{
    
    try{
   UUID l_session_identifier=session.getI_session_identifier();
    if(i_Locked_FileNames.containsKey(p_fileName)){
        dbg("first if is true in isSameSessionLock",session);
         dbg("i_Locked_FileNames.get(p_fileName)"+i_Locked_FileNames.get(p_fileName),session);
         dbg("Integer.toString(l_session_identifier)"+i_Locked_FileNames.get(p_fileName),session);
        
        if (i_Locked_FileNames.get(p_fileName).equals(l_session_identifier))
                {
            dbg("second if is true in isSameSession lock",session);
             return true;    
    }
        
    else{
        return false;
    }
    
    }
  else
    {
        dbg("file name not in the i_Locked_FileNames",session);
        return false;
    }
    }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
    
}

public synchronized boolean isSameSessionRecordLock(String p_fileName,String p_table_Name,String p_primary_key,CohesiveSession session)throws DBProcessingException{
    
    try{
     String l_key = p_fileName.concat("@").concat(p_table_Name).concat("@").concat(p_primary_key);
   UUID l_session_identifier=session.getI_session_identifier();
   dbg("l_key:"+l_key,session);
    if(i_rec_lock_map.containsKey(l_key)){
        dbg("first if is true in isSameSessionRecordLock",session);
         dbg("i_rec_lock_map.get(l_key)"+i_rec_lock_map.get(l_key),session);
         dbg("Integer.toString(l_session_identifier)"+i_rec_lock_map.get(l_key),session);
        
         
//         try{
         
        if (i_rec_lock_map.get(l_key).equals(l_session_identifier))
                {
            dbg("second if is true in isSameSession record lock",session);
             return true;    
    }
        
    else{
        return false;
    }
        
//         }catch (NullPointerException ex) {
//             
//             return false;
//         }
        
    
    }
  else
    {
        dbg("record is not in the i_rec_lock_map",session);
        return false;
    }
    }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
    
}
public synchronized void releaseLock(String p_fileName,CohesiveSession session)throws DBProcessingException{
    
           try{
             
           i_Locked_FileNames.remove(p_fileName,session.getI_session_identifier() );
           dbg("lock released",session);
    
           }catch (UnsupportedOperationException  ex) {
            dbg(ex,session);
            throw new DBProcessingException("OperationException  exception" + ex.toString());
           }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
           }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
           } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
          }
}


public synchronized void releaseRecordLock(String p_fileName,String p_tableName,String p_pkey,CohesiveSession session)throws DBProcessingException{
    
           try{
             String l_key=p_fileName.concat("@").concat(p_tableName).concat("@").concat(p_pkey);
           i_rec_lock_map.remove(l_key,session.getI_session_identifier() );
          
           dbg("lock released",session);
    
           }catch (UnsupportedOperationException  ex) {
            dbg(ex,session);
            throw new DBProcessingException("OperationException  exception" + ex.toString());
           }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
           }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
           } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
          }
}

public synchronized void releaseSessionRecordLock(CohesiveSession session)throws DBProcessingException{
    
           try{
               dbg("inside releaseSessionRecordLock ",session);
//           String l_key=p_fileName.concat("@").concat(p_tableName).concat("@").concat(p_pkey);
            String l_sessionId=session.getI_session_identifier().toString();
            
            if(i_rec_lock_map.containsValue(session.getI_session_identifier())){
            
            Iterator<String>keyIterator=i_rec_lock_map.keySet().iterator();
            
            
                while(keyIterator.hasNext()){

                   String key=keyIterator.next();
                   UUID value=i_rec_lock_map.get(key);
                   
                   if(l_sessionId.equals(value.toString())){
                       
                       i_rec_lock_map.remove(key, value);
                   }
                   

                }
            }

          
           dbg("SessionRecordLock released",session);
    
           }catch (UnsupportedOperationException  ex) {
            dbg(ex,session);
            throw new DBProcessingException("OperationException  exception" + ex.toString());
           }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
           }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
           } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
          }
}

 public synchronized boolean createIOLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
        UUID l_session_identifier;
        boolean l_islocked =false;
        
        try{
             l_session_identifier=session.getI_session_identifier();
             dbg("i_io_lock_map.size()"+i_io_lock_map.size(),session);
             i_io_lock_map.putIfAbsent(p_fileName, l_session_identifier);  
              
             if (i_io_lock_map.get(p_fileName).equals(l_session_identifier))
                {
             l_islocked = true;
             dbg("io Lock is created",session);
            dbg(p_fileName+"added to i_io_lock_map",session);
           
             }
         else
        {
            dbg(p_fileName+"already in the i_rec_lock_map",session);
          l_islocked = false;              
        }
         return  l_islocked;                  
        }catch (IllegalArgumentException ex) {
            dbg(ex,session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        }catch (UnsupportedOperationException ex) {
            dbg(ex,session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
        finally 
        {
         
        }
        
    }

//public boolean getIOLock(String p_file_name,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
//       
//        try{
//        long startTime=dbSession.getIibd_file_util(). getStartTime();   
//        while(!createIOLock(p_file_name,session,dbSession)){
//            
//            Thread.sleep(10); 
//            
//        }
//        
//        try{
//               
//           dbSession.getIibd_file_util().logWriteBuffer(p_file_name,null,null,"LockService","getIOLock",session,dbSession,dbdi,startTime);
//
//           }catch(Exception ex){
//               throw new DBProcessingException("Exception".concat(ex.toString()));
//           }
//        
//        return true;
//        
//        }catch(InterruptedException ex)
//            {
//            dbg(ex,session);
//            throw new DBProcessingException("InterruptedException exception" + ex.toString());  
//        }catch(IllegalArgumentException ex)
//            {
//            dbg(ex,session);
//            throw new DBProcessingException("InterruptedException exception" + ex.toString());
//       }catch (Exception ex) {
//            dbg(ex,session);
//            throw new DBProcessingException("Exception" + ex.toString());
//        }
//        
//        
//    }

public synchronized boolean isSameSessionIOLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
    
    try{
   UUID l_session_identifier=session.getI_session_identifier();
   dbg("p_fileName:"+p_fileName,session);
    if(i_io_lock_map.containsKey(p_fileName)){
         dbg("Integer.toString(l_session_identifier)"+i_io_lock_map.get(p_fileName),session);
        
        if (i_io_lock_map.get(p_fileName).equals(l_session_identifier))
                {
            dbg("second if is true in isSameSession io lock",session);
             return true;    
    }
        
    else{
        return false;
    }
    
    }
  else
    {
        dbg("record is not in the i_io_lock_map",session);
        return false;
    }
    }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
    
}

public synchronized void releaseIOLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
    
           try{
           long startTime=dbSession.getIibd_file_util(). getStartTime();    
           i_io_lock_map.remove(p_fileName,session.getI_session_identifier() );
           dbg("io lock released for "+p_fileName,session);
          
            try{
               
           dbSession.getIibd_file_util().logWaitBuffer(p_fileName,null,null,"LockService","releaseIOLock",session,dbSession,dbdi,startTime);

           }catch(Exception ex){
               throw new DBProcessingException("Exception".concat(ex.toString()));
           }
           
           
           
           }catch (UnsupportedOperationException  ex) {
            dbg(ex,session);
            throw new DBProcessingException("OperationException  exception" + ex.toString());
           }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
           }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
           } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
          }
}

public synchronized boolean createArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
        UUID l_session_identifier;
        boolean l_islocked =false;
        
        try{
            dbg("inside createArchivalLock",session);
            String fileNameWithExtension=getFileNameWithExtension(p_fileName,session);
            
             l_session_identifier=session.getI_session_identifier();
             dbg("l_session_identifier"+l_session_identifier,session);
             
             dbg("i_archival_lock_map.size() "+i_archival_lock_map.size(),session);
             
             if(i_archival_lock_map.containsKey(fileNameWithExtension)){
                 
                 l_islocked = false;      
                 
             }else{
                 
                 i_archival_lock_map.putIfAbsent(fileNameWithExtension, l_session_identifier);
                 l_islocked = true;
             }
                 
             dbg("l_islocked"+l_islocked,session);    
             
             dbg("i_archival_lock_map.size() after adding"+i_archival_lock_map.size(),session);
              
              
//             if (i_archival_lock_map.get(fileNameWithExtension).equals(l_session_identifier))
//                {
//             l_islocked = true;
//             dbg("archival Lock is created",session);
//            dbg(fileNameWithExtension+"added to i_archival_lock_map",session);
//           
//             }
//         else
//        {
//            dbg(fileNameWithExtension+"already in the i_archival_lock_map",session);
//          l_islocked = false;              
//        }
         return  l_islocked;                  
        }catch (IllegalArgumentException ex) {
            dbg(ex,session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        }catch (UnsupportedOperationException ex) {
            dbg(ex,session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
        finally 
        {
         
        }
        
    }

public synchronized boolean createArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession,UUID writeSessionIdentifier)throws DBProcessingException{
        UUID l_session_identifier;
        boolean l_islocked =false;
        
        try{
            dbg("inside createArchivalLock",session);
            String fileNameWithExtension=getFileNameWithExtension(p_fileName,session);
            
//             l_session_identifier=session.getI_session_identifier();
             l_session_identifier=writeSessionIdentifier;
             dbg("l_session_identifier"+l_session_identifier,session);
             
             dbg("i_archival_lock_map.size() "+i_archival_lock_map.size(),session);
             
             if(i_archival_lock_map.containsKey(fileNameWithExtension)){
                 
                 l_islocked = false;      
                 
             }else{
                 
                 i_archival_lock_map.putIfAbsent(fileNameWithExtension, l_session_identifier);
                 l_islocked = true;
             }
                 
             dbg("l_islocked"+l_islocked,session);    
             
             dbg("i_archival_lock_map.size() after adding"+i_archival_lock_map.size(),session);
              
              
//             if (i_archival_lock_map.get(fileNameWithExtension).equals(l_session_identifier))
//                {
//             l_islocked = true;
//             dbg("archival Lock is created",session);
//            dbg(fileNameWithExtension+"added to i_archival_lock_map",session);
//           
//             }
//         else
//        {
//            dbg(fileNameWithExtension+"already in the i_archival_lock_map",session);
//          l_islocked = false;              
//        }
         return  l_islocked;                  
        }catch (IllegalArgumentException ex) {
            dbg(ex,session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        }catch (UnsupportedOperationException ex) {
            dbg(ex,session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
        finally 
        {
         
        }
        
    }

public ArrayList<String> getArchivalFileNames(UUID p_sessionId)throws DBProcessingException{
    
    try{
        ArrayList<String>archivalFileNames=new ArrayList();
        Iterator<String>keyIterator=i_archival_lock_map.keySet().iterator();
        
        while(keyIterator.hasNext()){
            
            String fileName=keyIterator.next();
            UUID value=i_archival_lock_map.get(fileName);
            
           // if(value.toString().equals(p_sessionId.toString())){
             if(value.equals(p_sessionId))
             {  
                archivalFileNames.add(fileName);
            }
        }
        
        return archivalFileNames;
    }catch (Exception ex) {
//            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());
    }
}


public boolean getArchivalLock(String p_file_name,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
        
        try{
//        while(!createArchivalLock(p_file_name,session,dbSession)){
//            
//            Thread.sleep(1); 
//            
//        }
          dbg("inside getArchivalLock"+p_file_name,session);
         boolean  lock=   createArchivalLock(p_file_name,session,dbSession);
         dbg("lock"+lock,session);
        
         dbg("end of getArchivalLock",session);
        return lock;
        
//        }catch(InterruptedException ex)
//            {
//            dbg(ex,session);
//            throw new DBProcessingException("InterruptedException exception" + ex.toString());  
        }catch(IllegalArgumentException ex)
            {
            dbg(ex,session);
            throw new DBProcessingException("InterruptedException exception" + ex.toString());
       }catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());
        }
//        finally{
//            return false;
//            
//        }
    }

public synchronized void releaseArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
    
           try{
            
               if(p_fileName!=null){
               
                   dbg("inside releaseArchivalLock ",session);
                   String fileNameWithExtension=getFileNameWithExtension(p_fileName,session);

                   dbg("i_archival_lock_map size before removing"+fileNameWithExtension+"  "+i_archival_lock_map.size(),session);

    //           i_archival_lock_map.remove(fileNameWithExtension,session.getI_session_identifier() );
                 i_archival_lock_map.remove(fileNameWithExtension,session.getI_session_identifier());
                 dbg("archival lock released for "+fileNameWithExtension,session);

                 dbg("i_archival_lock_map size after removing"+fileNameWithExtension+"  "+i_archival_lock_map.size(),session);

               }
           }catch (UnsupportedOperationException  ex) {
            dbg(ex,session);
            throw new DBProcessingException("OperationException  exception" + ex.toString());
           }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
           }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
           } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
          }
}
public synchronized boolean isSameSessionArchivalLock(String p_fileName,CohesiveSession session,DBSession dbSession)throws DBProcessingException{
    
    try{
   UUID l_session_identifier=session.getI_session_identifier();
   //get extension add .
   // if last substring
   String fileNameWithExtension=getFileNameWithExtension(p_fileName,session);
   dbg("fileNameWithExtension:"+fileNameWithExtension,session);
    if(i_archival_lock_map.containsKey(fileNameWithExtension)){
         dbg("Integer.toString(l_session_identifier)"+i_archival_lock_map.get(fileNameWithExtension),session);
        
        if (i_archival_lock_map.get(fileNameWithExtension).equals(l_session_identifier))
                {
            dbg("second if is true in isSameSession archival lock",session);
             return true;    
    }
        
    else{
        return false;
    }
    
    }
  else
    {
        dbg("record is not in the i_archival_lock_map",session);
        return false;
    }
    }catch (NullPointerException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
        }catch (ClassCastException ex) {
            dbg(ex,session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
        } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
    
}

private String getFileNameWithExtension(String p_fileName,CohesiveSession session)throws DBProcessingException{
    
    try{
        dbg("inside getFileNameWithExtension file name"+p_fileName,session);
        String fileExtension=session.getCohesiveproperties().getProperty("FILE_EXTENSION");
        String substring= p_fileName.substring(p_fileName.length()-4, p_fileName.length());
        String fileNameWithExtension;
        
        dbg("fileExtension"+fileExtension,session);
        dbg("substring"+substring,session);
        
        if(fileExtension.equals(substring)){
            
            fileNameWithExtension=p_fileName;
        }else{
            fileNameWithExtension=p_fileName.concat(fileExtension);
        }
        
        dbg("fileNameWithExtension"+fileNameWithExtension,session);
        return fileNameWithExtension;
    } catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException(ex.toString());
    }
    
    
    
    
}
private  String getCurrentYearMonth()throws DBProcessingException{
      
       try{ 
       Date date = new Date();
       String dateformat="yyMMddHHmmss";
       SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
       String l_dateStamp=formatter.format(date);
//       Date currentDate=formatter.parse(l_dateStamp);
//       LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//       String month = Integer.toString(localDate.getMonthValue());
//       String year= Integer.toString(localDate.getYear());
//       String day=Integer.toString(localDate.getDayOfMonth());
       
       String yearAndMonth=l_dateStamp+"000000";
      return yearAndMonth;
      }catch(Exception ex){
          throw new DBProcessingException(ex.toString());
//       return null;     
     }
      
  }  


private  String createSequenceNo()throws DBProcessingException{
      
       try{ 
       Date date = new Date();
       String dateformat="DDDyy";
       SimpleDateFormat formatter = new SimpleDateFormat(dateformat);  
       String l_dateStamp=formatter.format(date);
          


       String sequence=l_dateStamp+"0000";
      return sequence;
      }catch(Exception ex){
          throw new DBProcessingException(ex.toString());
//       return null;     
     }
      
  } 


//private  int createSequenceNo()throws DBProcessingException{
//      
//       try{ 
//           
//           return 0000;
//           
//      }catch(Exception ex){
//          throw new DBProcessingException(ex.toString());
////       return null;     
//     }
//      
//  } 

public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}