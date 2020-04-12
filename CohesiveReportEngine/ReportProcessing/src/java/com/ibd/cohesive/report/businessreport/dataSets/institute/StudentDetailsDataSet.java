/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.businessreport.dataSets.institute;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.DBColumn;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.read.IDBReadService;
import com.ibd.cohesive.db.readbuffer.DBRecord;
import com.ibd.cohesive.db.readbuffer.IDBReadBufferService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.businessreport.dataModels.institute.StudentDetails;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author ibdtech
 */
public class StudentDetailsDataSet {
    public ArrayList<StudentDetails> getStudentDetails(String p_standard,String p_section,String status,String l_instituteID,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject,String fromDate,String toDate)throws DBProcessingException,DBValidationException{
        ArrayList<StudentDetails>   dataset=new ArrayList();

        
        try{
        
        dbg("inside StudentDetails_DataSet",session);     
        IDBReadBufferService readBuffer=inject.getDBReadBufferService();
        IBDProperties i_db_properties=session.getCohesiveproperties();
        BusinessService bs=appInject.getBusinessService(session);
        IPDataService pds=inject.getPdataservice();
        Map<String, ArrayList<String>> l_studentMap=null;
        IDBReadService dbrs=inject.getDbreadservice();
         Map<String, ArrayList<String>> files=null;
         String dateFormat=session.getCohesiveproperties().getProperty("DATE_FORMAT");
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try{
        
//        l_studentMap = pds.readTablePData("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session, dbSession);
        files= dbrs.readFullFile("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE",session);
        
//        l_studentMap =dbrs.readTable("INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID, "INSTITUTE", "IVW_STUDENT_MASTER", session);
        
        
        }catch(DBValidationException ex){
            if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                session.getErrorhandler().removeSessionErrCode("DB_VAL_000");

            }else{

                throw ex;
            }
        }
        
        
        if(files!=null){
            
            Map<String,ArrayList<String>>filtermap_dummy=versionFilter(files,"INSTITUTE","INSTITUTE" + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID + i_db_properties.getProperty("FOLDER_DELIMITER") + l_instituteID,session,appInject);
               
              ConcurrentHashMap<String, ConcurrentHashMap<String,ArrayList<String>>> subMap= getSubMap(filtermap_dummy,session,appInject);
            l_studentMap=subMap.get("IVW_STUDENT_MASTER");
           
            
            if(l_studentMap!=null){
            
            
            
            
        
         List<ArrayList<String>>studentsList=  l_studentMap.values().stream().collect(Collectors.toList());
            
            dbg("status"+status,session);
         if(status!=null&&!status.isEmpty()){   
            
            
            studentsList=studentsList.stream().filter(rec->rec.get(8).trim().equals(status)).collect(Collectors.toList());
            dbg("studentsList"+studentsList.size(),session);
            
            
            
        
         }
         dbg("p_standard"+p_standard,session);
         dbg("p_section"+p_section,session);
         
         if(p_standard!=null&&!p_standard.isEmpty()&&p_section!=null&&!p_section.isEmpty()){   
            
            
            studentsList=studentsList.stream().filter(rec->rec.get(2).trim().equals(p_standard)&&rec.get(3).trim().equals(p_section)).collect(Collectors.toList());
            dbg("studentsList"+studentsList.size(),session);
        
         }
         
        
        Iterator<ArrayList<String>>studentListIterator=studentsList.iterator();
        int i=1;
        while(studentListIterator.hasNext()){
            
            
            ArrayList<String>studentList=studentListIterator.next();
            String studentID=studentList.get(0).trim();
            dbg("inside student iteration-->studentID"+studentID,session);
            String studentName=studentList.get(1).trim();
            String standard=studentList.get(2).trim();
            String section=studentList.get(3).trim();
            String recordStatus=studentList.get(8).trim();
            String makerDateStamp=studentList.get(6).trim();
            String versionNumber=studentList.get(10).trim();
            String creationDate=" ";
            String deletedDate=" ";
            dbg("recordStatus"+recordStatus,session);
            
            
            if(versionNumber.equals("1")){
                
                creationDate=makerDateStamp;
            }else{
                
                String[] l_pkey={studentID,"1"};
                
                String[] studentRec=dbrs.recordRead(session, "INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_PROFILE", l_pkey);
                
                creationDate=studentRec[15];
                dbg("studentRec[15]"+studentRec[15],session);
                
                if(recordStatus.equals("D")){
                    
                    deletedDate=makerDateStamp;
                }
                
            }
            
            boolean isReqiuired=false;
            if(status!=null&&status.equals("D")){
                
                
               Date l_deletedDate=formatter.parse(deletedDate);
                Date L_fromDate=formatter.parse(fromDate);
                Date l_toDate=formatter.parse(toDate);
                
                if(l_deletedDate.compareTo(L_fromDate)>=0){
                    dbg("from date suceess",session);
                    
                    if(l_deletedDate.compareTo(l_toDate)<=0){
                    dbg("to date suceess",session);    
                    
                      isReqiuired=true;
                    
                    }
                }
                
                
            }else{
                isReqiuired=true;
            }
            
            dbg("isReqiuired"+isReqiuired,session);
            
            if(isReqiuired){
            
            
            Map<String,DBRecord>l_contactPersonMap=readBuffer.readTable("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_FAMILY_DETAILS", session, dbSession);
            
            String[] studentPkey={studentID};
            
            DBRecord profileRec=readBuffer.readRecord("INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+"STUDENT"+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID+i_db_properties.getProperty("FOLDER_DELIMITER")+studentID,"STUDENT", "SVW_STUDENT_PROFILE", studentPkey, session, dbSession);
            
            String masterVersion=profileRec.getRecord().get(19).trim();
            
            List<DBRecord>filteredRecordsList=l_contactPersonMap.values().stream().filter(rec->rec.getRecord().get(8).trim().equals(masterVersion)&&rec.getRecord().get(9).trim().equals("Y")).collect(Collectors.toList());
            
            DBRecord contactPersonRecord=filteredRecordsList.get(0);
            
            String contactPersonName=contactPersonRecord.getRecord().get(2).trim();
            String contactNo=contactPersonRecord.getRecord().get(6).trim();
            String mailID=contactPersonRecord.getRecord().get(5).trim();
            if(mailID.contains("AATT;")){
              mailID=mailID.replace("AATT;", "@");
            }
            
            StudentDetails stuDetail=new StudentDetails();
            stuDetail.setStudentID(studentID);
            stuDetail.setStudentName(studentName);
            stuDetail.setStandard(standard);
            stuDetail.setSection(section);
            stuDetail.setContactPersonName(contactPersonName);
            stuDetail.setContactNumber(contactNo);
            stuDetail.setEmailID(mailID);
            stuDetail.setCreationDate(creationDate);
            stuDetail.setDeletedDate(deletedDate);
            stuDetail.setSerialNumber(Integer.toString(i));
            dataset.add(stuDetail);
            i++;
            
            
            }
        }
        
        }else{
                dbg("student map is null",session);
            }
        }else{
             dbg("file map is null",session);
        }
        
        if(dataset.isEmpty()){
            
            StudentDetails stuDetail=new StudentDetails();
            stuDetail.setStudentID(" ");
            stuDetail.setStudentName(" ");
            stuDetail.setStandard(" ");
            stuDetail.setSection(" ");
            stuDetail.setContactPersonName(" ");
            stuDetail.setContactNumber(" ");
            stuDetail.setEmailID(" ");
            stuDetail.setCreationDate(" ");
            stuDetail.setDeletedDate(" ");
            stuDetail.setSerialNumber(" ");
            dataset.add(stuDetail);
        }
        
        
        
        dbg("end of StudentDetails_DataSet",session); 
    }catch(DBProcessingException ex){
          dbg(ex,session);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
      }catch(DBValidationException ex){
          dbg(ex,session);
          throw ex;
     }catch(Exception ex){
         dbg(ex,session);
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
       return dataset;
    }
    
    
    private Map<String, ArrayList<String>>versionFilter(Map<String, ArrayList<String>>p_fileMap,String p_fileType,String p_fileName,CohesiveSession session,AppDependencyInjection appInject)throws DBValidationException, DBProcessingException{
    Map<String,String>l_tableVersion=new HashMap();
    ArrayList<String>l_filerList=new ArrayList();
    try{
        dbg("inside Pdata service--->versionFilter",session);
        dbg("inside Pdata service--->versionFilter--->p_fileMap.size"+p_fileMap.size(),session);
        dbg("inside Pdata service--->versionFilter--->p_fileType"+p_fileType,session);
        dbg("inside Pdata service--->versionFilter--->p_fileName"+p_fileName,session);
        IMetaDataService mds =appInject.getMetadataservice();
//        IDBReadService dbrs=dbdi.getDbreadservice();
        Iterator<String> iterator1=p_fileMap.keySet().iterator();
        Map<String,ArrayList<String>>filtermap_dummy=new HashMap();
        while(iterator1.hasNext()){
          String l_key=iterator1.next();
          dbg("version filter key"+l_key,session);
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
                 dbg("version status is true",session);
             }else{
                 l_versionStatus=false;
                 dbg("version status is false",session);
             }
             if(l_versionStatus==true)
                 break;
         }
          dbg("column iteration completed",session);
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
           dbg("filter string"+filterkey_dummy,session);
           if(!(l_filerList.contains(filterkey_dummy))){
            l_filerList.add(filterkey_dummy); 
             String l_filterKey=filterkey_dummy;
//           Stream<String> l_filteredStream=p_fileMap.keySet().stream().filter(rec->rec.trim().contains(filterkey_dummy));
             Stream<String> l_filteredStream=p_fileMap.keySet().stream().filter(rec->rec.trim().substring(0,rec.lastIndexOf("~")).equals(filterkey_dummy));
//           l_filteredStream.forEach(s->dbg("Stream values"+s));
           
           maxversion_dummy=l_filteredStream.mapToInt(rec->Integer.parseInt(rec.trim().substring(rec.lastIndexOf("~")+1,rec.length()))).max().getAsInt();
           dbg("filter map--->maxversion_dummy"+maxversion_dummy,session);
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
           }
         }else{
//             dbg("l_key in versionfilter"+l_key);
//             for(String s:p_fileMap.get(l_key)){
//             dbg("value put to map"+s);
//         }
             filtermap_dummy.put(l_key, p_fileMap.get(l_key));
         }  
         
         
         
        }
         filtermap_dummy.forEach((String k,ArrayList<String> v)->{
              dbg("filtered map key"+k,session);
              v.forEach((String l)->{
                  dbg("filtered map value"+l,session);
              });
           });
         dbg("end of pdataservice--->version filter",session);
    return filtermap_dummy;
        }catch (DBValidationException ex) {
            throw ex;  
        }catch (DBProcessingException ex) {
            dbg(ex,session);
            throw ex;     
        }catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
} 
    
private ConcurrentHashMap<String,ConcurrentHashMap<String,ArrayList<String>>>getSubMap(Map<String,ArrayList<String>>l_fileMap,CohesiveSession session,AppDependencyInjection appInject)throws DBValidationException, DBProcessingException{
//    Map<String,Map<String,ArrayList<String>>>l_tableFilterdMap=new HashMap();
     ConcurrentHashMap<String,ConcurrentHashMap<String,ArrayList<String>>>l_tableFilterdMap=null;
    ArrayList<String>l_listForTableCheck=new ArrayList();
    try{
        dbg("inside getsub Map",session);
           float tableloadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("TABLE_CON_HMAP_LOAD_FACTOR"));
           int tableconcurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("TABLE_CON_HMAP_CONCURRENCY_LEVEL"));
//            float recloadFactor= Float.parseFloat(session.getCohesiveproperties().getProperty("REC_CON_HMAP_LOAD_FACTOR"));
//           int recconcurrencyLevel= Integer.parseInt(session.getCohesiveproperties().getProperty("REC_CON_HMAP_CONCURRENCY_LEVEL"));
            
            l_tableFilterdMap=new ConcurrentHashMap(l_fileMap.size(),tableloadFactor,tableconcurrencyLevel);
            IMetaDataService mds =appInject.getMetadataservice();
            Iterator keyIterator=l_fileMap.keySet().iterator();
            ConcurrentHashMap<String,ArrayList<String>>l_recordfilteredMap;
            while(keyIterator.hasNext() ){
                String l_key=(String)keyIterator.next();
                String[] l_tabID_Pkey=l_key.trim().split("~");
                String l_tableID= l_tabID_Pkey[0];
                String l_tableName=mds.getTableMetaData(Integer.parseInt(l_tableID), session).getI_TableName();
                
                
                   if(!(l_listForTableCheck.contains(l_tableID))){ 
                       l_recordfilteredMap= getRecordFilterMap(l_tableID,l_fileMap,session);
                        l_recordfilteredMap= getRecordFilterMap(l_tableID,l_fileMap,session);
                       l_tableFilterdMap.put(l_tableName, l_recordfilteredMap);
                       l_listForTableCheck.add(l_tableID);
                   }  
               
               
               
            }
        
      dbg("end of getsub Map",session);  
        
    }catch (DBValidationException ex) {
            throw ex;  
        }catch (DBProcessingException ex) {
            dbg(ex,session);
            throw ex;     
        }catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
    return l_tableFilterdMap;
}    
    private ConcurrentHashMap<String,ArrayList<String>>getRecordFilterMap(String p_tableID,Map<String,ArrayList<String>>l_fileMap,CohesiveSession session)throws DBValidationException, DBProcessingException{
    ConcurrentHashMap<String,ArrayList<String>> l_recordFilteredMap=null;
    try{
            dbg("inside getRecordFilterMap",session);
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
                dbg("l_key inside get getRecordFilterMap "+l_key,session);
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
    
     dbg("end of getRecordFilterMap",session);
        }catch (Exception ex) {
            dbg(ex,session);
            throw new DBProcessingException("Exception" + ex.toString());   
     }
    
    return l_recordFilteredMap;
}
    
    public void dbg(String p_Value,CohesiveSession session) {

        session.getDebug().dbg(p_Value);

    }

    public void dbg(Exception ex,CohesiveSession session) {

        session.getDebug().exceptionDbg(ex);

    }
}
