/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.util;

import com.ibd.cohesive.app.business.util.BusinessService;
import com.ibd.cohesive.app.business.util.ConvertedDate;
import com.ibd.cohesive.app.business.util.dependencyInjection.AppDependencyInjection;
import com.ibd.cohesive.db.core.metadata.IMetaDataService;
import com.ibd.cohesive.db.core.pdata.IPDataService;
import com.ibd.cohesive.db.session.DBSession;
import com.ibd.cohesive.report.dependencyinjection.ReportDependencyInjection;
import com.ibd.cohesive.util.IBDProperties;
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author IBD Technologies
 */
public class ReportUtil {
    Debug debug;
    IBDProperties i_db_properties;

    public Debug getDebug() {
        return debug;
    }

    public void setDebug(Debug debug) {
        this.debug = debug;
    }

    public IBDProperties getI_db_properties() {
        return i_db_properties;
    }

    public void setI_db_properties(IBDProperties i_db_properties) {
        this.i_db_properties = i_db_properties;
    }
    
    
    public int getNo_of_workingDays(String year,String month)throws DBProcessingException{
        
        try{
            
            
                        
             Calendar calendar = Calendar.getInstance();
             int date = 1;
             calendar.set(Integer.parseInt(year),Integer.parseInt(month) , date);
             int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            return days;
        }catch(Exception ex){
         dbg(ex);   
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
        
        
    }
    
     public  String getDay(String p_date)throws DBProcessingException{
      
        try{ 
         
       String dateFormat=i_db_properties.getProperty("DATE_FORMAT");
       Date date1=new SimpleDateFormat(dateFormat).parse(p_date);  
       LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       String day=localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
       
       return day;
       }catch(Exception ex){
         dbg(ex);  
         throw new DBProcessingException("DBProcessingException"+ex.toString());
     }
      
      
  }  
public Integer getDayNumber(String day)throws BSProcessingException{
      
      try{
         dbg("inside getDayNumber"+day);
         
         
         switch(day){
             
             case "Mon":
                 
                 return 1;
             case "Tue":
                 
                 return 2;
             case "Wed":
                 
                 return 3;
             case "Thu":
                 
                 return 4;
             case "Fri":
                 
                 return 5;  
             case "Sat":
                 
                 return 6; 
             case "Sun":
                 
                 return 7;
               
         }
      
          
         dbg("end of getDayNumber"); 
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }  
      return null;
  }
   
 public float getNoOfWorkingDaysInMonth(String l_instituteID,String year,String month,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject,AppDependencyInjection appInject)throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{
    
       try{
           dbg("inside getNoOfWorkingDaysInMonth");
           dbg("inside l_instituteID-->"+l_instituteID);
           dbg("inside year-->"+year);
           dbg("inside month-->"+month);
           IPDataService pds=inject.getPdataservice();
           BusinessService bs=appInject.getBusinessService(session);
           float workingDayCount=0.0f;
           Calendar calendar = Calendar.getInstance();
           calendar.set(Calendar.YEAR, Integer.parseInt(year));
           calendar.set(Calendar.MONTH,Integer.parseInt(month) );
           int numDays = calendar.getActualMaximum(Calendar.DATE);
           String currentDate=bs.getCurrentDate();
           ConvertedDate converted=bs.getYearMonthandDay(currentDate);
           String day=converted.getDay();
           String currentMonth=converted.getMonth();
           String currentYear=converted.getYear();

           if(Integer.parseInt(month)<10){
                   
                   month="0"+month;
                   
               }
           
           String[] l_pkey={l_instituteID,year,month};
           ArrayList<String> holidayMaintanenceRecord=null;
               
            try{
           

                holidayMaintanenceRecord=pds.readRecordPData( session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+l_instituteID,"INSTITUTE","IVW_HOLIDAY_MAINTANENCE", l_pkey);

               }catch(DBValidationException ex){
                    if(ex.toString().contains("DB_VAL_011")||ex.toString().contains("DB_VAL_000")){
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_011");
                        session.getErrorhandler().removeSessionErrCode("DB_VAL_000");
                        session.getErrorhandler().log_app_error("BS_VAL_060", null);
                        throw new BSValidationException("BSValidationException");
                    }else{
                        
                        throw ex;
                    }
           } 
                
                
                
                 if(holidayMaintanenceRecord!=null){
                     dbg("holidayMaintanenceRecord not null ");
                     String holidayString=holidayMaintanenceRecord.get(3);
                     dbg("holidayString"+holidayString);
                     char[] holidayArr=holidayString.toCharArray();
                     int iterationCount=0;
                     
                     if(currentYear.equals(year)&&currentMonth.equals(month)){
                         
                         
                         iterationCount=Integer.parseInt(day);
                         
                     }else{
                         
                         iterationCount=numDays;
                     }
                     
                     dbg("iterationCount"+iterationCount);
                     for(int j=0;j<iterationCount;j++){//iteration for no of days in the month
                         
                         
                         if(j<numDays){
                         
                             char holidayChar=holidayArr[j];
                             dbg("holidayChar"+holidayChar);

                             if(holidayChar=='W'){

                                 workingDayCount=workingDayCount+1;
                             }else if(holidayChar=='F'||holidayChar=='A'){

                                 workingDayCount=workingDayCount+0.5f;
                             }
                         
                         }
                     }
                     
                 }
                
            dbg("end of getNoOfWorkingDaysInMonth--->workingDayCount-->"+workingDayCount);
           return workingDayCount;
       }catch(BSValidationException ex){
            throw ex;    
       }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
            dbg(ex);
            throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }
}
     
  public String getMaxVersionOfTheClass(String instituteID,String standard,String section,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBValidationException,DBProcessingException,BSProcessingException{
     
     try{
         
         dbg("inside getMaxVersionOfTheClass");
         
         IPDataService pds=inject.getPdataservice();
         
         String[] l_pkey={instituteID,standard,section};
         
         ArrayList<String> classConfigMasterRecord=pds.readRecordPData(session,dbSession,"INSTITUTE"+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID+i_db_properties.getProperty("FOLDER_DELIMITER")+instituteID,"INSTITUTE","IVW_STANDARD_MASTER", l_pkey);
                   
         String masterVersionNumber=classConfigMasterRecord.get(4).trim();
         
         dbg("inside getMaxVersionOfTheClass--->masterVersionNumber--->"+masterVersionNumber);
         
         return masterVersionNumber;
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
     
    public int getVersionIndexOfTheTable(String tableName,String fileType,CohesiveSession session,DBSession dbSession,ReportDependencyInjection inject)throws DBValidationException,DBProcessingException,BSProcessingException{
     
     try{
         
         dbg("inside getVersionNumberFromTheRecord");
         
        IMetaDataService mds=inject.getMetadataservice();
         
        int columnID= mds.getColumnMetaData(fileType, tableName, "VERSION_NUMBER", session).getI_ColumnID();
         
         int versionIndex=columnID-1;
        
        
         
         return versionIndex;
        }catch(DBValidationException ex){
            throw ex;
        }catch(DBProcessingException ex){
          dbg(ex);
          throw new DBProcessingException("DBProcessingException"+ex.toString());
        }catch(Exception ex){
            dbg(ex);
           throw new BSProcessingException(ex.toString());
        }
 }
    
    
   public String getMonthValueInString(String month)throws BSProcessingException{
      
      try{
         dbg("inside getMonthValueInString"+month);
         
         
         switch(month){
             
             case "1":
                 
                 return "JAN";
             case "2":
                 
                 return "FEB";
             case "3":
                 
                 return "MAR";
             case "4":
                 
                 return "APR";
             case "5":
                 
                 return "MAY";  
             case "6":
                 
                 return "JUN"; 
             case "7":
                 
                 return "JUL";
             case "8":
                 
                 return "AUG";
             case "9":
                 
                 return "SEP";
             case "10":
                 
                 return "OCT"; 
             case "11":
                 
                 return "NOV";    
             case "12":
                 
                 return "DEC";    
         }
      
          
         dbg("end of getMonthValueInString"); 
       }catch (Exception ex) {
            dbg(ex);
            throw new BSProcessingException("Exception" + ex.toString());
        }  
      return null;
  }
    
    
    
     public void dbg(String p_Value) {

        this.debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        this.debug.exceptionDbg(ex);

    }
}
