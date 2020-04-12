/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.institute;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.birt.report.engine.api.EngineConstants;

/**
 *
 * @author IBD Technologies
 */
public abstract class InstituteDataSource<T> {
    
    
    private Iterator<T> iterator;
    private Map<String,Object> dataSetParamValues;
    private String userID;
    private String service;
    private String loginInstitute;
    private String nokotser;
    private String instituteID;
    private String standard;
    private String section;
    private String feeID;
    private String activityID;
    private String date;
    private String businessDate;
    private String studentID;
    private String fromDate;
    private String toDate;
    private String notificationType;
    private String studentStatus;

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }
    
    

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
    

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }
   

    public String getFeeID() {
        return feeID;
    }

    public void setFeeID(String feeID) {
        this.feeID = feeID;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
    

  
    

    public String getInstituteId() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }
    
    
 
    

    public Iterator<T> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLoginInstitute() {
        return loginInstitute;
    }

    public void setLoginInstitute(String loginInstitute) {
        this.loginInstitute = loginInstitute;
    }

    public String getNokotser() {
        return nokotser;
    }

    public void setNokotser(String nokotser) {
        this.nokotser = nokotser;
    }


        
    public Map<String, Object> getDataSetParamValues() {
        return dataSetParamValues;
    }
	
	public abstract List<T> fetch();
	
	
	public void open(Object appContext,Map<String,Object> dataSetParamValues)
	{
            System.out.print("inside open");
        this.dataSetParamValues = dataSetParamValues;
        Map<String,Object> mur=(Map<String,Object>) appContext;  
             HttpServletRequest request=(HttpServletRequest)mur.get(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST);
//           this.studentId=request.getParameter("studentId");
              
                    
	   this.instituteID=request.getParameter("instituteId");
           this.standard=request.getParameter("standard");
           this.studentID=request.getParameter("studentID");
           
          String l_class=request.getParameter("class");
          if(l_class!=null&&!l_class.isEmpty()){ 
             this.standard=l_class.split("/")[0];
             this.section=l_class.split("/")[1];
          }
             
             this.feeID=request.getParameter("feeID");
             this.activityID=request.getParameter("activityID");
             this.date= request.getParameter("date");   
             this.businessDate= request.getParameter("businessDate");
	   System.out.print("inside institute data source"+date);
           this.nokotser=request.getParameter("nokotser");
           
           this.userID=request.getParameter("userID");
           
           this.loginInstitute=request.getParameter("loginInstitute");
           
           this.service=request.getParameter("service");
           this.fromDate=request.getParameter("fromDate");
           this.toDate=request.getParameter("toDate");
           this.notificationType=request.getParameter("notificationType");
           this.studentStatus=request.getParameter("studentStatus");
           
           if(this.notificationType!=null){
           
           if(this.notificationType.contains("SSPPAA")){
               this.notificationType=this.notificationType.replace("SSPPAA", " ");
           }
           
           
           }
           
           
            
           if(this.loginInstitute==null&&this.userID==null&&this.nokotser==null)
              {
                  String referer=request.getHeader("Referer"); 
                  URL url=null;
                  try
                  {  
                    url = new URL(referer);
                  }
                  catch(MalformedURLException e)
                  {
                    //throw new EJBException();
                      return;
                  }
                          
String queryStr = url.getQuery();

String[] params = queryStr.split("&");
for (String param: params) {
    String key = param.substring(0, param.indexOf('='));
    String val = param.substring(param.indexOf('=') + 1);
    if (key.equals("instituteId"))
    {  
    	   this.instituteID=val;
    }
    if (key.equals("studentID"))
    {  
    	   this.studentID=val;
    }
    if (key.equals("class"))
    {  
    	  l_class=request.getParameter("class");
          
          if(l_class!=null&&!l_class.isEmpty()){
             this.standard=l_class.split("/")[0];
             this.section=l_class.split("/")[1];
          }
    }
    if (key.equals("standard"))
    {
      this.standard=val;
    }  
    
    if (key.equals("feeID"))
    {
      this.feeID=val;
    }  
    if (key.equals("activityID"))
    {
      this.activityID=val;
    } 
    
    if (key.equals("nokotser"))
    {
      this.nokotser=val;
    }      
    if (key.equals("userID"))  
    {    
    this.userID=val;
    }
    if (key.equals("loginInstitute"))
    {   
           this.loginInstitute=val;
    }      
    if(key.equals("service")) 
    {
    this.service=val;
    }
    if(key.equals("date")) 
    {
    this.date=val;
    }
    if(key.equals("businessDate")) 
    {
    this.businessDate=val;
    }
    if(key.equals("fromDate")) 
    {
    this.fromDate=val;
    }
    if(key.equals("todate")) 
    {
    this.toDate=val;
    }
    if(key.equals("studentStatus")) 
    {
    this.toDate=val;
    }
    if(key.equals("notificationType")) 
    {
    this.notificationType=val;
    if(this.notificationType.contains("SSPPAA")){
               this.notificationType=this.notificationType.replace("SSPPAA", " ");
           }
    }
    }
}
           try{    
             
           iterator = fetch().iterator();
		  	
           }catch(Exception ex){
               
               ex.printStackTrace();
           }
	}
	
	public Object next()
	{
            try{
		if(iterator !=null && iterator.hasNext())
		  return iterator.next();
                
              }catch(Exception ex){
               
               ex.printStackTrace();
           }  
                
	     return null;
	}

    public void close()
    {
    	iterator= null;	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    private Iterator<T> iterator;
//private Map<String,Object> dataSetParamValues;
//
//private String instituteId;
//
//
//    public String getInstituteId() {
//        return instituteId;
//    }
//
//        
//    public Map<String, Object> getDataSetParamValues() {
//        return dataSetParamValues;
//    }
//	
//	public abstract List<T> fetch();
//	
//	
//	public void open(Object appContext,Map<String,Object> dataSetParamValues)
//	{
//        this.dataSetParamValues = dataSetParamValues;
//        Map<String,Object> mur=(Map<String,Object>) appContext;  
//             HttpServletRequest request=(HttpServletRequest)mur.get(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST);
//	   this.instituteId=request.getParameter("instituteId");
//	
//           iterator = fetch().iterator();
//		  	
//	}
//	
//	public Object next()
//	{
//		if(iterator !=null && iterator.hasNext())
//		  return iterator.next();
//	     return null;
//	}
//
//    public void close()
//    {
//    	iterator= null;	
//    }
}