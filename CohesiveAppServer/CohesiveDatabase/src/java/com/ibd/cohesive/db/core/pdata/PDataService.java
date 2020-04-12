/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.pdata;

import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.db.tempSegment.IDBTempSegmentService;
import com.ibd.cohesive.db.util.IBDFileUtil;
import com.ibd.cohesive.db.util.dependencyInjection.DBDependencyInjection;
import com.ibd.cohesive.util.errorhandling.ErrorHandler;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.db.util.validation.DBValidation;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import javax.ejb.DependsOn;
import javax.ejb.EJBException;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.NamingException;

/**
 *
 * @author IBD Technologies
 *
 * Created by : Isac
 * Created-on : 17-08-2018
 * Create-Phase : Cohesive_Development
 * Create-Description :This java file contains caching of files,tables and records.
 
 *  Change Description :calling methods instead directly getting from map.
 *  Changed by   :Isacmanojkumar
 *  Changed on   :1/3/2019
 *  Search Tag   :Cohesive1_UnitTest_9
 
  */
@Startup
@DependsOn({"DBCoreService","LockService"})
@Singleton
@ConcurrencyManagement(CONTAINER)
@Lock(LockType.READ)
public  class PDataService implements IPDataService{
    
   
    DBDependencyInjection dbdi; 
    CohesiveSession session;
    DBSession dbSession;
//    private  Map<String, Map<String, Map<String,ArrayList<String>>>> P_data_map;
    private  Map<String, ConcurrentHashMap<String, ConcurrentHashMap<String,ArrayList<String>>>> P_data_map;
    private static int initialCapacity;
    private static float loadFactor;
    private static int concurrencyLevel;
    
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
    
//  private static  Map<String, Map<String,ArrayList<String>>> P_data_map;
//   int maxversion_dummy;//dummy for lambda expression
//    Map<String,ArrayList<String>>filtermap_dummy;
//    String filterkey_dummy; 
    public PDataService() throws NamingException {
        dbdi = new DBDependencyInjection();   
        
        if(initialCapacity==0){
         P_data_map=new ConcurrentHashMap();
        }else{
            P_data_map=new ConcurrentHashMap(initialCapacity,loadFactor,concurrencyLevel);
        }
//        P_data_map = new HashMap();
        session = new CohesiveSession();
        dbSession=new DBSession(session);
}  
    
         
   //This method creates the parameterized data
    @PostConstruct
    public void createPData()throws  EJBException {
        boolean l_session_created_now =false;
        DirectoryStream<Path> stream=null;
    try
    {
        session.createSessionObject();
        dbSession.createDBsession(session);
         l_session_created_now= session.isI_session_created_now();
        dbg("inside Pdata service--->createPData");
        String folderDelimiter=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
        String dbHomePath=session.getCohesiveproperties().getProperty("DATABASE_HOME_PATH");
        String[] caching_required_files=null;
        ArrayList<String>cachingFilesList=new ArrayList();
        
        if (session.getCohesiveproperties().getProperty("CACHING_REQUIRED_FILES")!=null)
        {
           caching_required_files=  session.getCohesiveproperties().getProperty("CACHING_REQUIRED_FILES").split("~");
           
           for(String s:caching_required_files){
              
              cachingFilesList.add(s);
          }
           Path folderPath=Paths.get(dbHomePath+"INSTITUTE");
           stream = Files.newDirectoryStream(folderPath);
           try
           {   
           for (Path file: stream) {
                
                String fileNameAndType="INSTITUTE"+folderDelimiter+file.getFileName()+folderDelimiter+file.getFileName()+"&"+"INSTITUTE";
                if(!cachingFilesList.contains(fileNameAndType))
                cachingFilesList.add(fileNameAndType);
          }
           }
           finally
           { if (stream!=null)
               try
               { stream.close();
           
               }
               catch (Exception ex)
               {
                   
               } 
           }  
           for(int i=0;i<cachingFilesList.size();i++){
               dbg("cachingFilesList-->"+cachingFilesList.get(i));
           }
           
           
           for(int i=0;i<cachingFilesList.size();i++){
           dbg("Entering into for loop");
           
           try{
           
           IDBReadService dbrs=dbdi.getDbreadservice();
           dbg("read service started");
           
            Map<String,ArrayList<String>>files = new HashMap();
            //files= dbrs.readFullFile(caching_required_files[i],"INSTITUTE",this.dbdi);
            String[] caching_required_fileNames_fileTypes=cachingFilesList.get(i).split("&");
            String caching_required_fileName=caching_required_fileNames_fileTypes[0];
            String caching_required_fileType=caching_required_fileNames_fileTypes[1];
            
            //files= dbrs.readFullFile(caching_required_files[i],"INSTITUTE");
            dbg("caching_required_fileName"+caching_required_fileName);
            dbg("caching_required_fileType"+caching_required_fileType);
            
            
           
            
            
            files= dbrs.readFullFile(caching_required_fileName,caching_required_fileType,this.session);
            
            
            
            
            dbg("read full file method called");
              
              files.forEach((String k1,ArrayList<String> v1)->{
              dbg("key"+k1);
              v1.forEach((String l)->{
               dbg("values"+l);
              });
           });
       
            
            
            
            Map<String,ArrayList<String>>filtermap_dummy=versionFilter(files,caching_required_fileType,caching_required_fileName);
               
//            Map<String, Map<String,ArrayList<String>>> subMap= getSubMap(filtermap_dummy);
              ConcurrentHashMap<String, ConcurrentHashMap<String,ArrayList<String>>> subMap= getSubMap(filtermap_dummy);
//          dbg("Sub map");
//           subMap.forEach((String k,Map<String, ArrayList<String>> v)->{
//              dbg("table id"+k);
//              v.forEach((String k1,ArrayList<String> v1)->{
//              dbg("primary key"+k1);
//              v1.forEach((String l)->{
//               dbg("values"+l);
//              });
//           });
//        });
          
           
            P_data_map.put(caching_required_fileName,subMap);
                 
            
            }catch(DBValidationException ex){
                    dbg("exception in pdata read"+ex);
                    if(ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        
                    }else{
                        
                        throw ex;
                    }
                }
           }
            
       }
        
         dbg("PData printin starts");
           P_data_map.forEach((String fileNameKey,ConcurrentHashMap<String, ConcurrentHashMap<String,ArrayList<String>>> filemap )->{
               dbg("fileName"+fileNameKey);
                filemap.forEach((String k,Map<String, ArrayList<String>> v)->{
              dbg("table name"+k);
              v.forEach((String k1,ArrayList<String> v1)->{
              dbg("primary key"+k1);
              v1.forEach((String l)->{
               dbg("values"+l);
              });
           });
        });
           
          });
            dbg("PData printin ends");
      dbg("end of Pdata service--->createPdata");       
    }
    catch (DBProcessingException ex) {
            dbg(ex);
            throw new EJBException("DBProcessingException" + ex.toString());
        } catch (IndexOutOfBoundsException ex) {
            dbg(ex);
            throw new EJBException("IndexOutOfBoundsException" + ex.toString());
         }catch (NullPointerException ex) {
            dbg(ex);
            throw new EJBException("Null pointer exception" + ex.toString());
         }catch (ClassCastException ex) {
            dbg(ex);
            throw new EJBException("Classcast exception" + ex.toString());
         }catch (UnsupportedOperationException ex) {
            dbg(ex);
            throw new EJBException("UnsupportedOperationException" + ex.toString());
        
        } catch (IllegalArgumentException ex) {
            dbg(ex);
            throw new EJBException("IllegalArgumentException" + ex.toString());
        }catch (ConcurrentModificationException ex) {
            dbg(ex);
            throw new EJBException("ConcurrentModification exception" + ex.toString());  
        }catch(DBValidationException ex){
            throw new EJBException("DBValidationException" + ex.toString());
        }
        catch (Exception ex) {
            dbg(ex);
            throw new EJBException("Exception" + ex.toString());   
        }finally{
        if(l_session_created_now)
        {  
//            this.filterkey_dummy =null;
//            this.filtermap_dummy = null;
//            this.maxversion_dummy = 0;
          session.clearSessionObject();
          dbSession.clearSessionObject();
        }
    }
      
}
    
    @PreDestroy
    public void destroyPData() {
        P_data_map = null;
        //dbf = null;
    }
    private Map<String, ArrayList<String>>versionFilter(Map<String, ArrayList<String>>p_fileMap,String p_fileType,String p_fileName)throws DBValidationException, DBProcessingException{
    Map<String,String>l_tableVersion=new HashMap();
    ArrayList<String>l_filerList=new ArrayList();
    try{
        dbg("inside Pdata service--->versionFilter");
        dbg("inside Pdata service--->versionFilter--->p_fileMap.size"+p_fileMap.size());
        dbg("inside Pdata service--->versionFilter--->p_fileType"+p_fileType);
        dbg("inside Pdata service--->versionFilter--->p_fileName"+p_fileName);
        IMetaDataService mds =dbdi.getMetadataservice();
//        IDBReadService dbrs=dbdi.getDbreadservice();
        Iterator<String> iterator1=p_fileMap.keySet().iterator();
        Map<String,ArrayList<String>>filtermap_dummy=new HashMap();
        while(iterator1.hasNext()){
          String l_key=iterator1.next();
          dbg("version filter key"+l_key);
          String[] l_tableIDAndPKey=l_key.split("~");
          String l_tableID=l_tableIDAndPKey[0];
          String l_versionID;
          boolean l_versionStatus=false;
          Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(Integer.parseInt(l_tableID), session).getI_ColumnCollection();
          Iterator columnIterator=l_columnCollection.values().iterator();
          while(columnIterator.hasNext()){
             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
             if(l_dbcolumn.getI_ColumnName().equals("VERSION_NUMBER")){
                 l_versionStatus=true;
                 dbg("version status is true");
             }else{
                 l_versionStatus=false;
                 dbg("version status is false");
             }
             if(l_versionStatus==true)
                 break;
         }
          dbg("column iteration completed");
         if(l_versionStatus){
             String l_tableName=mds.getTableMetaData(Integer.parseInt(l_tableID), session).getI_TableName();
             l_versionID=Integer.toString(mds.getColumnMetaData(p_fileType, l_tableName, "VERSION_NUMBER",session).getI_ColumnID());
             l_tableVersion.put(l_tableID, l_versionID);
         }else{
             l_tableVersion.put(l_tableID, "0");
         }
//          try{
//            String l_tableName=mds.getTableMetaData(Integer.parseInt(l_tableID), dbdi).getI_TableName();
//             dbg("versionFilter--->tableID"+l_tableID);
//             dbg("versionFilter--->tableName"+l_tableName);
//             dbg("versionFilter--->filetype"+p_fileType);
//            l_versionID=Integer.toString(mds.getColumnMetaData(p_fileType, l_tableName, "VERSION_NUMBER",dbdi).getI_ColumnID());
//            l_tableVersion.put(l_tableID, l_versionID);
//           }catch(DBValidationException ex){
//               dbg("exception in versionfilter"+ex.toString());
//              if(ex.toString().contains("DB_VAL_005")){
//                  l_versionID="0";
//                  l_tableVersion.put(l_tableID, l_versionID);
//              }else{
//                  dbg("exception in versionfilter"+ex.toString());
//                  throw ex;
//              }
//           }
        
         if(!(l_tableVersion.get(l_tableID).equals("0"))){     
                 String filterkey_dummy;
                 int maxversion_dummy;
                String[] l_pkey=mds.getTableMetaData(Integer.parseInt(l_tableID), session).getI_Pkey().split("~");
                int l_versionIndex=Arrays.asList(l_pkey).indexOf(l_tableVersion.get(l_tableID));

//                ArrayList<String> l_pkList=new ArrayList();
//                for(int i=0;i<l_pkey.length;i++){
//                   l_pkList.add(i, l_pkey[i]);
//                 }
//                int l_versionIndex=l_pkList.indexOf(l_tableVersion.get(l_tableID));
                
                
                StringBuffer l_filteredBuffer=new StringBuffer();
//                StringBuffer l_filterKeyBuffer=new StringBuffer();
                
                for(int i=0;i<l_tableIDAndPKey.length;i++){
                    if(i!=l_versionIndex+1){
                            l_filteredBuffer.append(l_tableIDAndPKey[i]).append("~");
                        }                       
                }
                
           if( l_filteredBuffer.charAt(l_filteredBuffer.length()-1)=='~'){
               filterkey_dummy=l_filteredBuffer.substring(0, l_filteredBuffer.length()-1);
           }else{
              filterkey_dummy=l_filteredBuffer.toString();
           }
                
                
                
//                for(int i=0;i<l_tableIDAndPKey.length;i++){
//                    if(i!=l_versionIndex+1){
//                        if(i==l_tableIDAndPKey.length-1){
//                            if(i!=0){
//                              l_filterKeyBuffer.append(l_tableIDAndPKey[i]);
//                            }
//                            
//                            l_filteredBuffer.append(l_tableIDAndPKey[i]);
//                        }else{
//                             if(i!=0){
//                              l_filterKeyBuffer.append(l_tableIDAndPKey[i]);
//                            }
//                            l_filteredBuffer.append(l_tableIDAndPKey[i]).append("~");
//                        }
//                       
//                    }
//                }
                
//           filterkey_dummy=l_filteredBuffer.toString();
           dbg("filter string"+filterkey_dummy);
           if(!(l_filerList.contains(filterkey_dummy))){
            l_filerList.add(filterkey_dummy); 
             String l_filterKey=filterkey_dummy;
//           Stream<String> l_filteredStream=p_fileMap.keySet().stream().filter(rec->rec.trim().contains(filterkey_dummy));
             Stream<String> l_filteredStream=p_fileMap.keySet().stream().filter(rec->rec.trim().substring(0,rec.lastIndexOf("~")).equals(filterkey_dummy));
//           l_filteredStream.forEach(s->dbg("Stream values"+s));
           
           maxversion_dummy=l_filteredStream.mapToInt(rec->Integer.parseInt(rec.trim().substring(rec.lastIndexOf("~")+1,rec.length()))).max().getAsInt();
           dbg("filter map--->maxversion_dummy"+maxversion_dummy);
//           l_filteredBuffer=null;
//           l_filteredBuffer=new StringBuffer();
//           for(int i=0;i<l_tableIDAndPKey.length;i++){
//                        if(i==l_tableIDAndPKey.length-1){
//                            l_filteredBuffer.append(l_tableIDAndPKey[i]);
//                        }else{
//                            l_filteredBuffer.append(l_tableIDAndPKey[i]).append("~");
//                        }
//                       
//                }
//           filterkey_dummy=l_filteredBuffer.toString();
//           filtermap_dummy.put(l_filterKey, p_fileMap.get(filterkey_dummy));
//           l_filteredStream.filter(rec->Integer.parseInt(rec.trim().substring(.forEach(rec->filtermap_dummy.put(rec, p_fileMap.get(rec)));
//             l_filteredStream=p_fileMap.keySet().stream().filter(rec->rec.trim().contains(filterkey_dummy));
                l_filteredStream=p_fileMap.keySet().stream().filter(rec->rec.trim().substring(0,rec.lastIndexOf("~")).equals(filterkey_dummy));
           l_filteredStream.filter(rec->Integer.parseInt(rec.trim().substring(rec.lastIndexOf("~")+1,rec.length()))==maxversion_dummy).forEach(rec->filtermap_dummy.put(filterkey_dummy, p_fileMap.get(rec)));
           removeClosedRecords(filterkey_dummy,p_fileType,l_tableID,filtermap_dummy);
           }
         }else{
//             dbg("l_key in versionfilter"+l_key);
//             for(String s:p_fileMap.get(l_key)){
//             dbg("value put to map"+s);
//         }
             filtermap_dummy.put(l_key, p_fileMap.get(l_key));
             removeClosedRecords(l_key,p_fileType,l_tableID,filtermap_dummy);
         }  
         
         
         
        }
         filtermap_dummy.forEach((String k,ArrayList<String> v)->{
              dbg("filtered map key"+k);
              v.forEach((String l)->{
                  dbg("filtered map value"+l);
              });
           });
         dbg("end of pdataservice--->version filter");
    return filtermap_dummy;
        }catch (DBValidationException ex) {
            throw ex;  
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw ex;     
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
} 
    
private ConcurrentHashMap<String,ConcurrentHashMap<String,ArrayList<String>>>getSubMap(Map<String,ArrayList<String>>l_fileMap)throws DBValidationException, DBProcessingException{
//    Map<String,Map<String,ArrayList<String>>>l_tableFilterdMap=new HashMap();
     ConcurrentHashMap<String,ConcurrentHashMap<String,ArrayList<String>>>l_tableFilterdMap=null;
    ArrayList<String>l_listForTableCheck=new ArrayList();
    try{
        dbg("inside getsub Map");
           float tableloadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("TABLE_CON_HMAP_LOAD_FACTOR"));
           int tableconcurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("TABLE_CON_HMAP_CONCURRENCY_LEVEL"));
//            float recloadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("REC_CON_HMAP_LOAD_FACTOR"));
//           int recconcurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("REC_CON_HMAP_CONCURRENCY_LEVEL"));
            
            l_tableFilterdMap=new ConcurrentHashMap(l_fileMap.size(),tableloadFactor,tableconcurrencyLevel);
            IMetaDataService mds =dbdi.getMetadataservice();
            Iterator keyIterator=l_fileMap.keySet().iterator();
            ConcurrentHashMap<String,ArrayList<String>>l_recordfilteredMap;
            while(keyIterator.hasNext() ){
                String l_key=(String)keyIterator.next();
                String[] l_tabID_Pkey=l_key.trim().split("~");
                String l_tableID= l_tabID_Pkey[0];
                String l_tableName=mds.getTableMetaData(Integer.parseInt(l_tableID), session).getI_TableName();
                
                
                   if(!(l_listForTableCheck.contains(l_tableID))){ 
                       l_recordfilteredMap= getRecordFilterMap(l_tableID,l_fileMap);
                        l_recordfilteredMap= getRecordFilterMap(l_tableID,l_fileMap);
                       l_tableFilterdMap.put(l_tableName, l_recordfilteredMap);
                       l_listForTableCheck.add(l_tableID);
                   }  
               
               
               
            }
        
      dbg("end of getsub Map");  
        
    }catch (DBValidationException ex) {
            throw ex;  
        }catch (DBProcessingException ex) {
            dbg(ex);
            throw ex;     
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
    return l_tableFilterdMap;
}    

private ConcurrentHashMap<String,ArrayList<String>>getRecordFilterMap(String p_tableID,Map<String,ArrayList<String>>l_fileMap)throws DBValidationException, DBProcessingException{
    ConcurrentHashMap<String,ArrayList<String>> l_recordFilteredMap=null;
    try{
            dbg("inside getRecordFilterMap");
            float recloadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("REC_CON_HMAP_LOAD_FACTOR"));
           int recconcurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("REC_CON_HMAP_CONCURRENCY_LEVEL"));
           l_recordFilteredMap=new ConcurrentHashMap(l_fileMap.keySet().size(),recloadFactor,recconcurrencyLevel);
            Iterator keyIterator=l_fileMap.keySet().iterator();
            Iterator valueIterator=l_fileMap.values().iterator();
            Map<String,ArrayList<String>>l_table_map=new HashMap();
   
            while(keyIterator.hasNext() && valueIterator.hasNext() ){
                String l_key=(String)keyIterator.next();
                
                  if(l_key.trim().split("~")[0].equals(p_tableID)){
                    l_table_map.put(l_key.trim(), (ArrayList<String>)valueIterator.next());
                            
                }
                else
                {
                    valueIterator.next();  
                }
            }
        
       Iterator<String> itearator1= l_table_map.keySet().iterator();
      
        
        while(itearator1.hasNext()){
                String l_key=(String)itearator1.next();
                dbg("l_key inside get getRecordFilterMap "+l_key);
                String[] l_tabID_Pkey=l_key.trim().split("~");
                StringBuffer l_PKbuffer=new StringBuffer();
                for(int i=1;i<l_tabID_Pkey.length;i++){
                         
                        if(i==l_tabID_Pkey.length-1){
                            l_PKbuffer=l_PKbuffer.append(l_tabID_Pkey[i]);
                        }else{
                         l_PKbuffer=l_PKbuffer.append(l_tabID_Pkey[i]).append("~");

                        }
                    
                }
                String l_primaryKey=l_PKbuffer.toString();
                ArrayList<String> l_value=l_table_map.get(l_key);
                l_recordFilteredMap.put(l_primaryKey, l_value);
                
        }
    
     dbg("end of getRecordFilterMap");
        }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
    
    return l_recordFilteredMap;
}


//This method removes the closed or deleted records from the filter map 
private void removeClosedRecords(String key,String p_fileType,String p_tableID,Map<String,ArrayList<String>>filtermap_dummy)throws DBProcessingException,DBValidationException{
    
    try{
      dbg("inside remove closed records");
      int l_recordStatusId; 
      boolean recordStatusExitence=false;
      IMetaDataService mds=dbdi.getMetadataservice();
      
      //checking the existence of recordStatus column in the table
       Map<String, DBColumn>l_columnCollection= mds.getTableMetaData(Integer.parseInt(p_tableID), session).getI_ColumnCollection();
       Iterator columnIterator=l_columnCollection.values().iterator();
          while(columnIterator.hasNext()){
             DBColumn l_dbcolumn = (DBColumn)columnIterator.next();
             if(l_dbcolumn.getI_ColumnName().equals("RECORD_STATUS")){
                 recordStatusExitence=true;
                 dbg("recordStatusExitence is true");
             }else{
                 recordStatusExitence=false;
                 dbg("recordStatusExitence is false");
             }
             if(recordStatusExitence==true)
                 break;
         }
      
         dbg("inside remove closed records--->recordStatusExitence"+recordStatusExitence); 
          
         
      if(recordStatusExitence){
            String l_tableName=mds.getTableMetaData(Integer.parseInt(p_tableID), session).getI_TableName();
            l_recordStatusId=mds.getColumnMetaData(p_fileType, l_tableName, "RECORD_STATUS",session).getI_ColumnID();//finding the record status 
            dbg("inside remove closed records--->l_tableName"+l_tableName);
            dbg("inside remove closed records--->l_recordStatusId"+l_recordStatusId);
            dbg("filtermap_dummy.get(key).get(l_recordStatusId-1)"+filtermap_dummy.get(key).get(l_recordStatusId-1));
            String l_recordStatus=filtermap_dummy.get(key).get(l_recordStatusId-1).trim();
           if(l_recordStatus.equals("D")){
         
              filtermap_dummy.remove(key);
              dbg("record removed");
           }
      
      }    
   
         dbg("end of remove closed records");
     }catch (DBProcessingException ex) {
          dbg(ex);
          throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch(DBValidationException ex){
            throw ex;
          
     }catch (Exception ex) {
            dbg(ex);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
        
    }
    
    





//This method returns a Map that contains file according to the specified file name and specified file type.
    //This Return map key is tableid~pkeycolval1~pkeycol2val2 , value is record values
@Lock(LockType.READ)
//  public Map<String,Map<String, ArrayList<String>>> readFilePData(String p_FileName,String p_FileType,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException{
  public ConcurrentHashMap<String,ConcurrentHashMap<String, ArrayList<String>>> readFilePData(String p_FileName,String p_FileType,CohesiveSession p_session,DBSession p_dbSession)throws DBValidationException, DBProcessingException{

        boolean l_validation_status=true;
//        boolean l_session_created_now =false;
        ConcurrentHashMap<String,ConcurrentHashMap<String, ArrayList<String>>> l_file_map=null;
        try
       {
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now= session.isI_session_created_now();
        dbg("inside pdataservice-->readFilepdata",p_session);
        dbg("pdataservice--->readFilePdata--->p_fileName"+p_FileName,p_session);
        dbg("pdataservice--->readFilePdata--->p_FileType"+p_FileType,p_session);
        DBValidation dbv = p_dbSession.getDbv();
        ErrorHandler errorhandler = p_session.getErrorhandler();
        
           
                  if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                    dbg("validation status is false",p_session);
                    errorhandler.log_error();

                }
                  if(!dbv.fileNameValidation(p_FileName,errorhandler,p_session,dbdi)){
                  l_validation_status = false;
                   errorhandler.log_error();    
                  
                  }
                if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }

                if (!l_validation_status) {
                    throw new DBValidationException(errorhandler.getSession_error_code().toString());

                }
            dbg("validation inside PFileData is completed",p_session);
            l_file_map=P_data_map.get(p_FileName);
           StringBuffer single_error_code=new StringBuffer();
           if(l_file_map==null||l_file_map.isEmpty()){
                 single_error_code = single_error_code.append("DB_VAL_011");
                errorhandler.setSingle_err_code(single_error_code);
                p_session.getErrorhandler().log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
            //createPData();
//            Iterator iterator1=P_data_map.keySet().iterator();
//            Iterator iterator2=P_data_map.values().iterator(); 
//            dbg("iterator objects created in PFileData");
//            while(iterator1.hasNext() && iterator2.hasNext() ){
//                dbg("inside while loop of PFiledata");
//                String l_fileName=(String)iterator1.next();
//                dbg("PdataService--->readFilePdata--->l_fileName"+l_fileName);
//                dbg("PdataService--->readFilePdata--->p_fileName"+p_FileName);
//                if(p_FileName.equals(l_fileName)){
//                    dbg("if condition is true");
//                   l_file_map=(Map<String, ArrayList<String>>) iterator2.next();
//                   break;
//                }
//                else
//                {
//                    dbg("if condition is false") ;   
//                    iterator2.next();
//                }
//            }
//                 Iterator iterator3=l_file_map.keySet().iterator();
//            ArrayList<String>pk=new ArrayList<String>();
//            while(iterator3.hasNext()){
//                
//                pk.add((String)iterator3.next());
//            }
//            for(String s:pk){
//               
//                dbg("pk check inside filemap"+s);
//            }
//            
            dbg("end of pdataservice--->readFilePdata",p_session);
        }
      catch (DBProcessingException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (IndexOutOfBoundsException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch (NullPointerException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (ClassCastException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
         }catch (UnsupportedOperationException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        
        } catch (IllegalArgumentException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (ConcurrentModificationException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("ConcurrentModification exception" + ex.toString());  
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());   
        }finally{
//         if (l_session_created_now) {
//                session.clearSessionObject();
//                dbSession.clearSessionObject();
//            }
    }
        return l_file_map;
  
    
}
/*This is a cross call function 
  If other service's method calls this function it will call the actual function.
  If the method inside the service it will call this function as dependency injection as an argument.
  */
//  @Lock(LockType.READ)
//  public Map<String,Map<String, ArrayList<String>>> readFilePData(String p_FileName,String p_FileType,CohesiveSession session)throws DBValidationException, DBProcessingException{  
//    CohesiveSession tempSession = this.session;
//    Map<String,Map<String, ArrayList<String>>> l_file_map;
//    
//    try{
//        this.session=session;
//        l_file_map=readFilePData(p_FileName,p_FileType);
//        }
//          catch (DBProcessingException ex) {
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException" + ex.toString());
//        } catch (IndexOutOfBoundsException ex) {
//            dbg(ex);
//            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
//         }catch (NullPointerException ex) {
//            dbg(ex);
//            throw new DBProcessingException("Null pointer exception" + ex.toString());
//         }catch (ClassCastException ex) {
//            dbg(ex);
//            throw new DBProcessingException("Classcast exception" + ex.toString());
//         }catch (UnsupportedOperationException ex) {
//            dbg(ex);
//            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
//        
//        } catch (IllegalArgumentException ex) {
//            dbg(ex);
//            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
//        }catch (ConcurrentModificationException ex) {
//            dbg(ex);
//            throw new DBProcessingException("ConcurrentModification exception" + ex.toString());  
//        }catch(DBValidationException ex){
//            throw ex;
//        }
//        catch (Exception ex) {
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());   
//        }finally{
//           this.session=tempSession;
//    }
//        return l_file_map; 
//    
//    
//  }
  //This method returns a map that contains table data according to the specified filename,filetype and table name.
  @Lock(LockType.READ)
   public Map<String, ArrayList<String>> readTablePData(String p_FileName,String p_FileType, String p_TableName,CohesiveSession p_session,DBSession p_dbSession )throws DBValidationException, DBProcessingException{
        boolean l_validation_status=true;
//        boolean l_session_created_now=false;
        Map<String,ArrayList<String>> l_table_map=null;
        int l_Table_Id;
        
        try
       {
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside PdataService--->readTablePdata",p_session);
        dbg("PdataService--->readTablePdata--->p_FileName"+p_FileName,p_session);
        dbg("PdataService--->readTablePdata--->p_FileType"+p_FileType,p_session);
        dbg("PdataService--->readTablePdata--->p_TableName"+p_TableName,p_session);
        DBValidation dbv = p_dbSession.getDbv();
        ErrorHandler errorhandler = p_session.getErrorhandler();
        IMetaDataService mds=dbdi.getMetadataservice();
               if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                    dbg("validation status is false",p_session);
                    errorhandler.log_error();

                }
                  if(!dbv.fileNameValidation(p_FileName,errorhandler,p_session,dbdi)){
                  l_validation_status = false;
                   errorhandler.log_error();    
                  
                  }
                if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                
                }
                 if (!dbv.tableNameValidation(p_FileType,p_TableName, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!l_validation_status) {
                    throw new DBValidationException(errorhandler.getSession_error_code().toString());

                }
            dbg("validation completed in table data",p_session);
            //Cohesive1_UnitTest_9 starts here
            l_table_map=readFilePData(p_FileName,p_FileType,p_session,p_dbSession).get(p_TableName);
//            l_table_map=P_data_map.get(p_FileName).get(p_TableName);
            //Cohesive1_UnitTest_9 ends here
            
            StringBuffer single_error_code=new StringBuffer();
           if(l_table_map==null||l_table_map.isEmpty()){
                 single_error_code = single_error_code.append("DB_VAL_011");
                errorhandler.setSingle_err_code(single_error_code);
                p_session.getErrorhandler().log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
//            Map<String,ArrayList<String>> l_file_map;
//            l_file_map=readFilePData(p_FileName,p_FileType,session);
//            dbg("pdataservice--->readtablepdata--->l_file_map size--->"+l_file_map.size());
//            l_Table_Id = mds.getTableMetaData(p_FileType, p_TableName, this.session).getI_Tableid();
//            dbg("table id is"+l_Table_Id);
//            Iterator iterator1=l_file_map.keySet().iterator();
//            Iterator iterator2=l_file_map.values().iterator();
//            
//   
//            while(iterator1.hasNext() && iterator2.hasNext() ){
//                dbg("inside while loop of table data");
//                String l_str=(String)iterator1.next();
//                String tab[] = l_str.split("~");
//                
//                   
//              
//                  if(Integer.parseInt(tab[0])==l_Table_Id){
//                       dbg("if condition is true");
//                    l_table_map.put(l_str, (ArrayList<String>)iterator2.next());
//                            
//                    //break;
//                }
//                else
//                {
//                  dbg("if condition is false") ;
//                    iterator2.next();  
//                }
//            
//   
//            }
       dbg("end of pdataservice--->readTablePdata--->readtablepdata",p_session);
   }catch (DBProcessingException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (IndexOutOfBoundsException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch (NullPointerException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (ClassCastException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
         }catch (UnsupportedOperationException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        
        } catch (IllegalArgumentException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (ConcurrentModificationException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("ConcurrentModification exception" + ex.toString());  
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());   
        }finally{
//          if(l_session_created_now)
//          {   
//          session.clearSessionObject();
//          dbSession.clearSessionObject();
//          }
          
    }
        return l_table_map;
  
    
   }
//   @Lock(LockType.READ)
//   public Map<String, ArrayList<String>> readTablePData(String p_FileName,String p_FileType, String p_TableName,CohesiveSession session)throws DBValidationException, DBProcessingException{
//       CohesiveSession tempSession = this.session;
//    Map<String,ArrayList<String>> l_table_map=new HashMap();
//    
//    try{
//        this.session=session;
//        l_table_map=readTablePData(p_FileName,p_FileType,p_TableName);
//        
//        }catch (DBProcessingException ex) {
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException" + ex.toString());
//        } catch (IndexOutOfBoundsException ex) {
//            dbg(ex);
//            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
//         }catch (NullPointerException ex) {
//            dbg(ex);
//            throw new DBProcessingException("Null pointer exception" + ex.toString());
//         }catch (ClassCastException ex) {
//            dbg(ex);
//            throw new DBProcessingException("Classcast exception" + ex.toString());
//         }catch (UnsupportedOperationException ex) {
//            dbg(ex);
//            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
//        
//        } catch (IllegalArgumentException ex) {
//            dbg(ex);
//            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
//        }catch (ConcurrentModificationException ex) {
//            dbg(ex);
//            throw new DBProcessingException("ConcurrentModification exception" + ex.toString());  
//        }catch(DBValidationException ex){
//            throw ex;
//        }
//        catch (Exception ex) {
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());   
//        }finally{ 
//        this.session=tempSession;
//          
//    }
//        return l_table_map;
//  
//    
//   }
       
       
       
   
   //this method returns an arraylist that contains record data according to the specified filename,filetype,tablename and primarykey 
   @Lock(LockType.READ)
   public ArrayList<String> readRecordPData(CohesiveSession p_session,DBSession p_dbSession,String p_FileName,String p_FileType,String p_TableName, String... p_primary_key)throws DBValidationException, DBProcessingException{ 
          
       boolean l_validation_status=true;
//       boolean l_session_created_now=false;
       ArrayList l_records=null;
       int l_Table_Id;
   
       try
       {
//        session.createSessionObject();
//        dbSession.createDBsession(session);
//        l_session_created_now=session.isI_session_created_now();
        dbg("inside read recordPdata",p_session);
        dbg("recordPdata--->I/P--->p_FileName"+p_FileName,p_session);
        dbg("recordPdata--->I/P--->p_FileType"+p_FileType,p_session);
        for(String s:p_primary_key){
            dbg("recordPdata--->I/P--->p_primary_key"+s,p_session);
        }
        DBValidation dbv = p_dbSession.getDbv();
        ErrorHandler errorhandler = p_session.getErrorhandler();
        IMetaDataService mds=dbdi.getMetadataservice();
        l_Table_Id=mds.getTableMetaData(p_FileType, p_TableName, p_session).getI_Tableid();
        String[] l_dummy_pk;
        
           
                  if (!dbv.specialCharacterValidation(p_FileName, errorhandler)) {
                    l_validation_status = false;
                  
                    errorhandler.log_error();

                  }
                  if(!dbv.fileNameValidation(p_FileName,errorhandler,p_session,dbdi)){
                  l_validation_status = false;
                   errorhandler.log_error();    
                  
                  }
                if (!dbv.specialCharacterValidation(p_FileType, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.fileTypeValidation(p_FileType, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();

                }
                if (!dbv.specialCharacterValidation(p_TableName, errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                
                }
                 if (!dbv.tableNameValidation(p_FileType,p_TableName, errorhandler,dbdi)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                 }
                 int i=0;
                 while(i<p_primary_key.length){
                   if (!dbv.specialCharacterValidation(p_primary_key[i], errorhandler)) {
                    l_validation_status = false;
                    errorhandler.log_error();
                
                }  
                 
                 /*if(!dbv.pkValidation(l_Table_Id, p_primary_key[i], errorhandler)){
                    l_validation_status = false;
                    errorhandler.log_error();
                 }*/
                 i++;
                 }
                 l_dummy_pk=mds.getTableMetaData(p_FileType, p_TableName, p_session).getI_Pkey().split("~");
                 i = 0;
                /* for(String s:l_dummy_pk){
                     dbg("l dummy pk values"+s);
                 }
                 for(String s1:p_primary_key){
                     dbg("l  pk values"+s1);
                 }*/
                 if((l_dummy_pk.length<p_primary_key.length)){
//                 if((l_dummy_pk.length!=p_primary_key.length)){
                     StringBuffer l_single_error_code = new StringBuffer();
                     l_single_error_code = l_single_error_code.append("DB_VAL_010");
                     errorhandler.setSingle_err_code(l_single_error_code);
                     l_validation_status = false;
                     errorhandler.log_error();
                 }
                 else{/*If the above "if" condition is  satisfied the below validation will raise ArrayInbound exception.
                     So that below condition should be executed if the above condition fails*/
                 i=0;
                 

                while (i < l_dummy_pk.length) {
                    String l_columnName=mds.getColumnMetaData(p_FileType, p_TableName,Integer.parseInt(l_dummy_pk[i]), p_session).getI_ColumnName();
                 if(!(l_columnName.equals("VERSION_NUMBER"))){
                    if (!dbv.columnDataTypeValidation(p_TableName, Integer.parseInt(l_dummy_pk[i]), p_primary_key[i], errorhandler,dbdi)) {
                        l_validation_status = false;
                        errorhandler.log_error();

                    }
                }
                    i++;
                }
                i = 0;
                while (i < l_dummy_pk.length) {
               String l_columnName=mds.getColumnMetaData(p_FileType, p_TableName,Integer.parseInt(l_dummy_pk[i]), p_session).getI_ColumnName();
                    if(!(l_columnName.equals("VERSION_NUMBER"))){

                    if (!dbv.columnLengthValidation(p_TableName, p_primary_key[i], Integer.parseInt(l_dummy_pk[i]), errorhandler,dbdi
                    )) {
                        l_validation_status = false;
                        errorhandler.log_error();

                    }
                    }
                    i++;
                }
                 }
                if (!l_validation_status) {
                    throw new DBValidationException(errorhandler.getSession_error_code().toString());

                }
            dbg("validation completed in record pdataaa",p_session);
            
            String l_pkey=formingPrimaryKey(p_primary_key,p_session);
            dbg("l_pkey"+l_pkey,p_session);
            if(P_data_map==null){
                dbg("P_data_map is null",p_session);
            }else{
                dbg("P_data_map is not null",p_session);
                dbg("size of P_dataMap"+P_data_map.size(),p_session);
            }
             //Cohesive1_UnitTest_9 starts here
            l_records=readTablePData(p_FileName,p_FileType,p_TableName,p_session,p_dbSession).get(l_pkey);
           // l_records=P_data_map.get(p_FileName).get(p_TableName).get(l_pkey);
            //Cohesive1_UnitTest_9 ends here 
           
            dbg("l_records get from PDataMap",p_session);
            StringBuffer single_error_code=new StringBuffer();
           if(l_records==null){
                 single_error_code = single_error_code.append("DB_VAL_011".concat("," + p_TableName).concat("," +l_pkey.replace("~",",") ));//Integration chnages
                errorhandler.setSingle_err_code(single_error_code);
                p_session.getErrorhandler().log_error();
                throw new DBValidationException(errorhandler.getSession_error_code().toString());
            }
//            Map<String, ArrayList<String>>l_table_map;
//            dbg("p_FileName"+p_FileName);
//            dbg("p_FileType"+p_FileType);
//            dbg("p_TableName"+p_TableName);
//            l_table_map=readTablePData(p_FileName,p_FileType,p_TableName);
//            dbg("pdata service--->readRecordPData--->l_table_map size"+l_table_map.size());
//            dbg("tablepdata called inside recordpdata");
//            Iterator iterator1=l_table_map.keySet().iterator();
//            Iterator iterator2=l_table_map.values().iterator();
//            //Iterator iterator3=l_table_map.keySet().iterator();
//            boolean status=false;
//            dbg("iterator objects created inside record pdata");
//           
//            while(iterator1.hasNext() && iterator2.hasNext() ){
//            dbg("inside while loop of record pdata");
//            String l_str=(String)(iterator1.next());
//            //dbg("l_str ->"+l_str);
//            String[] l_PKey=l_str.split("~");
//            for(String s:l_PKey){
//                
//                dbg("l_PKeys"+s);
//            }
//            
//            for(String s:p_primary_key){
//                dbg("p_primary_key"+s);
//            }
//            if(l_PKey.length-1==p_primary_key.length){
//                dbg("valid primary key");
//                 for(int j=1;j<l_PKey.length;j++){
//                     dbg("l_PKey[j] ->"+l_PKey[j]);
//                     dbg("p_primary_key[j-1] ->"+p_primary_key[j-1]);
//                            if((l_PKey[j].trim()).equals(p_primary_key[j-1])){
//                                status=true;
//                                
//                                dbg("status is true");
//                            }
//                                else
//                            {
//                                dbg("Primary key is not matched");  
//                                status=false;
//                            break;
//                                   
//                            }
//                           
//                                
//                     
//            }
//                 if (status == true) 
//                         {    
//                            l_records=(ArrayList)( iterator2.next()); 
//                         }    
//                else
//                 {
//                 iterator2.next();
//                 }
//            }else{
//                   throw new DBValidationException("DB_VAL_010") ;
//                    }
//            
//            }
//       if(l_records.size()==0){
//                  dbg("inside record count check",p_session);
//                 // l_records=(ArrayList)( iterator2.next());
//                throw new DBValidationException("DB_VAL_011") ;
//            } 
       dbg("End of read record Pdataa",p_session);
       }catch (DBProcessingException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("DBProcessingException" + ex.toString());
        } catch (IndexOutOfBoundsException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
         }catch (NullPointerException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Null pointer exception" + ex.toString());
         }catch (ClassCastException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Classcast exception" + ex.toString());
         }catch (UnsupportedOperationException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
        
        } catch (IllegalArgumentException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
        }catch (ConcurrentModificationException ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("ConcurrentModification exception" + ex.toString());  
        }catch(DBValidationException ex){
            throw ex;
        }
        catch (Exception ex) {
            dbg(ex,p_session);
            throw new DBProcessingException("Exception" + ex.toString());   
        }finally{
//          if(l_session_created_now)
//          {   
//          session.clearSessionObject();
//          dbSession.clearSessionObject();
//          }
    }
        
  return l_records;
    
   }
    private String formingPrimaryKey(String[] p_pkey,CohesiveSession p_session) throws DBProcessingException {
        String l_pkey = null;
        try {
            int l_length_of_the_p_pkey = p_pkey.length;

            int array_index_of_p_pkey = 0;

            while (l_length_of_the_p_pkey != 0) {
                if (array_index_of_p_pkey == 0) {
                    l_pkey = p_pkey[array_index_of_p_pkey];
                    array_index_of_p_pkey++;
                    l_length_of_the_p_pkey--;
                } else {
                    l_pkey = l_pkey.concat("~").concat(p_pkey[array_index_of_p_pkey]);
                    array_index_of_p_pkey++;
                    l_length_of_the_p_pkey--;
                }

            }

        } catch (Exception ex) {
            dbg(ex,p_session);
            throw new DBProcessingException(ex.toString());
        }
        return l_pkey;
    }
//    @Lock(LockType.READ)
//  public ArrayList<String> readRecordPData(CohesiveSession session,String p_FileName,String p_FileType,String p_TableName, String... p_primary_key)throws DBValidationException, DBProcessingException{
//      
//      CohesiveSession tempSession = this.session;
//      ArrayList l_records=new ArrayList();
//    
//    try{
//        this.session=session;
//        l_records=readRecordPData(p_FileName,p_FileType,p_TableName,p_primary_key);
//        
//        }
//         catch (DBProcessingException ex) {
//            dbg(ex);
//            throw new DBProcessingException("DBProcessingException" + ex.toString());
//        } catch (IndexOutOfBoundsException ex) {
//            dbg(ex);
//            throw new DBProcessingException("IndexOutOfBoundsException" + ex.toString());
//         }catch (NullPointerException ex) {
//            dbg(ex);
//            throw new DBProcessingException("Null pointer exception" + ex.toString());
//         }catch (ClassCastException ex) {
//            dbg(ex);
//            throw new DBProcessingException("Classcast exception" + ex.toString());
//         }catch (UnsupportedOperationException ex) {
//            dbg(ex);
//            throw new DBProcessingException("UnsupportedOperationException" + ex.toString());
//        
//        } catch (IllegalArgumentException ex) {
//            dbg(ex);
//            throw new DBProcessingException("IllegalArgumentException" + ex.toString());
//        }catch (ConcurrentModificationException ex) {
//            dbg(ex);
//            throw new DBProcessingException("ConcurrentModification exception" + ex.toString());  
//        }catch(DBValidationException ex){
//            throw ex;
//        }
//        catch (Exception ex) {
//            dbg(ex);
//            throw new DBProcessingException("Exception" + ex.toString());   
//        }finally{
//           this.session=tempSession;
//    }
//        
//  return l_records;
//    
//   }
   
   
   
   public void dbg(String p_Value,CohesiveSession p_session) {

        p_session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession p_session) {

        p_session.getDebug().exceptionDbg(ex);

    }
   
    
    //This method adds the record from tempsegmnet to pdata map
   public void SetRecordfromTempseg(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
    
     try{
        dbg("inside PDataService--->SetRecordfromTempseg",p_session);
        dbg("inside PDataService--->SetRecordfromTempseg--->fileName"+p_fileName,p_session);
        dbg("inside PDataService--->SetRecordfromTempseg--->p_tableName"+p_tableName,p_session);
        dbg("inside PDataService--->SetRecordfromTempseg--->pKey"+p_tableName,p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_filetype=fileUtil.getFileType(p_fileName);
       
        if(P_data_map.get(p_fileName)!=null){
           dbg("inside PDataService--->SetRecordfromTempseg--->pdatamap contains the file name",p_session);
           DBRecord l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
           if(l_tempSegRec!=null){   
              dbg("inside PDataService--->SetRecordfromTempseg--->l_tempSegRec not null",p_session);  
              dbg("inside PDataService--->SetRecordfromTempseg--->tableName"+p_tableName,p_session); 
              if(P_data_map.get(p_fileName).containsKey(p_tableName)){
                    dbg("inside PDataService--->SetRecordfromTempseg--->pdatamap contains the table name",p_session);  
                    dbg("inside PDataService--->SetRecordfromTempseg--->l_tempSegRecPKey"+pKey,p_session);
                          if(P_data_map.get(p_fileName).get(p_tableName).containsKey(pKey)){
                          dbg("inside PDataService--->SetRecordfromTempseg--->operation"+l_tempSegRec.getOperation(),p_session);                                   
                             
                              if(l_tempSegRec.getOperation()=='D'){
                                   P_data_map.get(p_fileName).get(p_tableName).remove(pKey);
                              }else if(l_tempSegRec.getOperation()=='U'){
                                   P_data_map.get(p_fileName).get(p_tableName).replace(pKey,l_tempSegRec.getRecord());
                              }else if(l_tempSegRec.getOperation()=='C'){
                                   if(fileUtil.checkVersionAvailability(l_filetype, p_tableName, p_session, dbdi)){
                                         int existingVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, P_data_map.get(p_fileName).get(p_tableName).get(pKey), p_session, dbdi);
                                         int newVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
                                         dbg("new version number"+newVersionNumber,p_session);
                                         dbg("existing version number"+existingVersionNumber,p_session);
                                            if(newVersionNumber==existingVersionNumber+1){
                                                dbg("newVersionNumber==existingVersionNumber+1",p_session);
                                                P_data_map.get(p_fileName).get(p_tableName).replace(pKey,l_tempSegRec.getRecord());
                                       
                                           }else{
                                                dbg("newVersionNumber!=existingVersionNumber+1");
                                                StringBuffer single_error_code = new StringBuffer();
                                                single_error_code = single_error_code.append("DB_VAL_009");
                                                p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                                p_session.getErrorhandler().log_error();
                                                throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                            }
                                    }else{
                                        dbg("operation is create--->record doesn't contain version number",p_session);
                                        StringBuffer single_error_code = new StringBuffer();
                                        single_error_code = single_error_code.append("DB_VAL_009");
                                        p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                        p_session.getErrorhandler().log_error();
                                        throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                    }
                                }
                          
                            }else if(l_tempSegRec.getOperation()!='D'){
                              dbg("pdata map contains file,table not contains primary key--->inserting record if operation not equal to D",p_session);  
                              P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                          } 
                          
                      }else {
                        dbg("pdata map contains file not contains table",p_session);
//                        P_data_map.get(p_fileName).putIfAbsent(p_tableName, new HashMap());
                          P_data_map.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createPdataConcurrentRecordMap(p_session));
                        if(l_tempSegRec.getOperation()!='D')
                            P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                        }
                    }
               }else if(l_filetype.equals("INSTITUTE")){
                   
                   

                   String folderdelimiter=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");

                   String fileArr[]=p_fileName.split(folderdelimiter);
                   dbg("fileArr length"+fileArr.length,p_session);
                   
                   if(fileArr[1].equals(fileArr[2])){
                   
                       dbg("Pdata map doesn't contains file name and file type is INSTITUTE",p_session);
                       DBRecord l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
                       P_data_map.putIfAbsent(p_fileName, new ConcurrentHashMap());
                       P_data_map.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createPdataConcurrentRecordMap(p_session));

                         if(l_tempSegRec.getOperation()!='D')
                                P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());


                     
                   }
               }
            
        dbg("end of pdataservice--->setRecordFromTempSegment",p_session);
        
     } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    
}
   
   
  public boolean validatePData(String p_fileName,String p_tableName,String pKey,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
    
     try{
        dbg("inside PDataService--->SetRecordfromTempseg",p_session);
        dbg("inside PDataService--->SetRecordfromTempseg--->fileName"+p_fileName,p_session);
        dbg("inside PDataService--->SetRecordfromTempseg--->p_tableName"+p_tableName,p_session);
        dbg("inside PDataService--->SetRecordfromTempseg--->pKey"+p_tableName,p_session);
        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_filetype=fileUtil.getFileType(p_fileName);
       
        if(P_data_map.get(p_fileName)!=null){
           dbg("inside PDataService--->SetRecordfromTempseg--->pdatamap contains the file name",p_session);
           DBRecord l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
           if(l_tempSegRec!=null){   
              dbg("inside PDataService--->SetRecordfromTempseg--->l_tempSegRec not null",p_session);  
              dbg("inside PDataService--->SetRecordfromTempseg--->tableName"+p_tableName,p_session); 
              if(P_data_map.get(p_fileName).containsKey(p_tableName)){
                    dbg("inside PDataService--->SetRecordfromTempseg--->pdatamap contains the table name",p_session);  
                    dbg("inside PDataService--->SetRecordfromTempseg--->l_tempSegRecPKey"+pKey,p_session);
                          if(P_data_map.get(p_fileName).get(p_tableName).containsKey(pKey)){
                          dbg("inside PDataService--->SetRecordfromTempseg--->operation"+l_tempSegRec.getOperation(),p_session);                                   
                             
                              if(l_tempSegRec.getOperation()=='D'){
//                                   P_data_map.get(p_fileName).get(p_tableName).remove(pKey);
                              }else if(l_tempSegRec.getOperation()=='U'){
//                                   P_data_map.get(p_fileName).get(p_tableName).replace(pKey,l_tempSegRec.getRecord());
                              }else if(l_tempSegRec.getOperation()=='C'){
                                   if(fileUtil.checkVersionAvailability(l_filetype, p_tableName, p_session, dbdi)){
                                         int existingVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, P_data_map.get(p_fileName).get(p_tableName).get(pKey), p_session, dbdi);
                                         int newVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
                                         dbg("new version number"+newVersionNumber,p_session);
                                         dbg("existing version number"+existingVersionNumber,p_session);
                                            if(newVersionNumber==existingVersionNumber+1){
                                                dbg("newVersionNumber==existingVersionNumber+1",p_session);
//                                                P_data_map.get(p_fileName).get(p_tableName).replace(pKey,l_tempSegRec.getRecord());
                                       
                                           }else{
                                                dbg("newVersionNumber!=existingVersionNumber+1");
                                                StringBuffer single_error_code = new StringBuffer();
                                                single_error_code = single_error_code.append("DB_VAL_009");
                                                p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                                p_session.getErrorhandler().log_error();
                                                throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                            }
                                    }else{
                                        dbg("operation is create--->record doesn't contain version number",p_session);
                                        StringBuffer single_error_code = new StringBuffer();
                                        single_error_code = single_error_code.append("DB_VAL_009");
                                        p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                        p_session.getErrorhandler().log_error();
                                        throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                    }
                                }
                          
                            }else if(l_tempSegRec.getOperation()!='D'){
                              dbg("pdata map contains file,table not contains primary key--->inserting record if operation not equal to D",p_session);  
//                              P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                          } 
                          
                      }else {
                        dbg("pdata map contains file not contains table",p_session);
//                        P_data_map.get(p_fileName).putIfAbsent(p_tableName, new HashMap());
//                          P_data_map.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createPdataConcurrentRecordMap(p_session));
                        if(l_tempSegRec.getOperation()!='D'){
//                            P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                        }
                        }
                    }
               }else if(l_filetype.equals("INSTITUTE")){
                   
                   dbg("Pdata map doesn't contains file name and file type is INSTITUTE",p_session);
                   DBRecord l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
//                   P_data_map.putIfAbsent(p_fileName, new ConcurrentHashMap());
//                   P_data_map.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createPdataConcurrentRecordMap(p_session));
             
                     if(l_tempSegRec.getOperation()!='D'){
//                            P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                     }
                   
               }
            
        dbg("end of pdataservice--->setRecordFromTempSegment",p_session);
        return true;
     } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    
}  
public void SetRecordfromArchivalWrite(String p_fileName,String p_tableName,String pKey,DBRecord l_tempSegRec,CohesiveSession p_session,DBSession p_dbSession)throws DBProcessingException,DBValidationException{
    
     try{
        dbg("inside PDataService--->SetRecordfromArchivalWrite",p_session);
        dbg("inside PDataService--->SetRecordfromArchivalWrite--->fileName"+p_fileName,p_session);
        dbg("inside PDataService--->SetRecordfromArchivalWrite--->p_tableName"+p_tableName,p_session);
        dbg("inside PDataService--->SetRecordfromArchivalWrite--->pKey"+pKey,p_session);
//        IDBTempSegmentService tempSegment=dbdi.getDBTempSegmentService();
        IBDFileUtil fileUtil=p_dbSession.getIibd_file_util();
        String l_filetype=fileUtil.getFileType(p_fileName);
       
        if(P_data_map.get(p_fileName)!=null){
           dbg("inside PDataService--->SetRecordfromArchivalWrite--->pdatamap contains the file name",p_session);
//           DBRecord l_tempSegRec =  tempSegment.getRecordFromTempSegment(p_fileName, p_tableName, pKey, p_session, p_dbSession);
           if(l_tempSegRec!=null){   
              dbg("inside PDataService--->SetRecordfromArchivalWrite--->l_tempSegRec not null",p_session);  
              dbg("inside PDataService--->SetRecordfromArchivalWrite--->tableName"+p_tableName,p_session); 
              if(P_data_map.get(p_fileName).containsKey(p_tableName)){
                    dbg("inside PDataService--->SetRecordfromArchivalWrite--->pdatamap contains the table name",p_session);  
                    dbg("inside PDataService--->SetRecordfromArchivalWrite--->l_tempSegRecPKey"+pKey,p_session);
                          if(P_data_map.get(p_fileName).get(p_tableName).containsKey(pKey)){
                          dbg("inside PDataService--->SetRecordfromArchivalWrite--->operation"+l_tempSegRec.getOperation(),p_session);                                   
                             
                              if(l_tempSegRec.getOperation()=='D'){
                                   P_data_map.get(p_fileName).get(p_tableName).remove(pKey);
                              }else if(l_tempSegRec.getOperation()=='U'){
                                   P_data_map.get(p_fileName).get(p_tableName).replace(pKey,l_tempSegRec.getRecord());
                              }else if(l_tempSegRec.getOperation()=='C'){
                                   if(fileUtil.checkVersionAvailability(l_filetype, p_tableName, p_session, dbdi)){
                                         int existingVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, P_data_map.get(p_fileName).get(p_tableName).get(pKey), p_session, dbdi);
                                         int newVersionNumber=fileUtil.getVersionNumberFromTheRecord(l_filetype, p_tableName, l_tempSegRec.getRecord(), p_session, dbdi);
                                         dbg("new version number"+newVersionNumber,p_session);
                                         dbg("existing version number"+existingVersionNumber,p_session);
                                            if(newVersionNumber==existingVersionNumber+1){
                                                dbg("newVersionNumber==existingVersionNumber+1",p_session);
                                                P_data_map.get(p_fileName).get(p_tableName).replace(pKey,l_tempSegRec.getRecord());
                                       
                                           }else{
                                                dbg("newVersionNumber!=existingVersionNumber+1",p_session);
                                                StringBuffer single_error_code = new StringBuffer();
                                                single_error_code = single_error_code.append("DB_VAL_009");
                                                p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                                p_session.getErrorhandler().log_error();
                                                throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                            }
                                    }else{
                                        dbg("operation is create--->record doesn't contain version number",p_session);
                                        StringBuffer single_error_code = new StringBuffer();
                                        single_error_code = single_error_code.append("DB_VAL_009");
                                        p_session.getErrorhandler().setSingle_err_code(single_error_code);
                                        p_session.getErrorhandler().log_error();
                                        throw new DBValidationException(p_session.getErrorhandler().getSession_error_code().toString()); 
                                    }
                                }
                          
                            }else if(l_tempSegRec.getOperation()!='D'){
                              dbg("pdata map contains file,table not contains primary key--->inserting record if operation not equal to D",p_session);  
                              P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                          } 
                          
                      }else {
                        dbg("pdata map contains file not contains table",p_session);
//                        P_data_map.get(p_fileName).putIfAbsent(p_tableName, new HashMap());
                          P_data_map.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createPdataConcurrentRecordMap(p_session));
                        if(l_tempSegRec.getOperation()!='D')
                            P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                        }
                    }
               }else if(l_filetype.equals("INSTITUTE")){
                   
                   dbg("Pdata map doesn't contains file name and file type is INSTITUTE",p_session);
                   P_data_map.putIfAbsent(p_fileName, new ConcurrentHashMap());
                   P_data_map.get(p_fileName).putIfAbsent(p_tableName,fileUtil.createPdataConcurrentRecordMap(p_session));
             
                     if(l_tempSegRec.getOperation()!='D')
                            P_data_map.get(p_fileName).get(p_tableName).putIfAbsent(pKey, l_tempSegRec.getRecord());
                   
                   
               }
            
        dbg("end of pdataservice--->SetRecordfromArchivalWrite",p_session);
        
     } catch(DBValidationException ex){
           throw ex;
     }catch(DBProcessingException ex){
        dbg(ex,p_session);
        throw new DBProcessingException("DBProcessingException" + ex.toString());
     }catch (Exception ex) {
          dbg(ex,p_session);
          throw new DBProcessingException("Exception" + ex.toString());
     }
    
}   
   
   
   
   public void dbg(String p_Value) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex) {

        session.getDebug().exceptionDbg(ex);

    }

   
    
}



  

