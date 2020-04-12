/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.app;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.birt.report.engine.api.EngineConstants;

/**
 *
 * @author IBD Technologies
 */
public abstract class AppDataSource<T> {
private Iterator<T> iterator;
private Map<String,Object> dataSetParamValues;

//private String studentId;
private String instituteId;
private String userID;
private String service;
private String loginInstitute;
private String nokotser;
private String currdate;
private String businessDate;
private String businessReport;

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public AppDataSource() {
        
        
    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCurrdate() {
        return currdate;
    }

    public void setCurrdate(String currdate) {
        this.currdate = currdate;
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

    public String getBusinessReport() {
        return businessReport;
    }

    public void setBusinessReport(String businessReport) {
        this.businessReport = businessReport;
    }


//
//    public String getStudentId() {
//        return studentId;
//    }
//
    public String getInstituteId() {
        return instituteId;
    }

        
    public Map<String, Object> getDataSetParamValues() {
        return dataSetParamValues;
    }
	
	public abstract List<T> fetch();
	
	
	public void open(Object appContext,Map<String,Object> dataSetParamValues)
	{
        this.dataSetParamValues = dataSetParamValues;
        Map<String,Object> mur=(Map<String,Object>) appContext;  
             HttpServletRequest request=(HttpServletRequest)mur.get(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST);
//           this.studentId=request.getParameter("studentId");
              
                    
	   this.instituteId=request.getParameter("instituteId");
	   
           this.nokotser=request.getParameter("nokotser");
           
           this.userID=request.getParameter("userID");
           
           this.businessReport=request.getParameter("businessReport");
           
           this.loginInstitute=request.getParameter("loginInstitute");
           
           this.service=request.getParameter("service");
           
           this.currdate=request.getParameter("currdate");
           this.businessDate=request.getParameter("businessDate");
            
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
               // throw new EJBException();  
                    return;
                  }
                          
String queryStr = url.getQuery();

String[] params = queryStr.split("&");
for (String param: params) {
    String key = param.substring(0, param.indexOf('='));
    String val = param.substring(param.indexOf('=') + 1);
    if (key.equals("instituteId"))
    {  
    	   this.instituteId=val;
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
     if(key.equals("businessDate")) 
    {
    this.businessDate=val;
    
    }
     if(key.equals("businessReport")) 
    {
    this.businessReport=val;
    
    }
     if(key.equals("currdate")) 
    {
    this.currdate=val;
    }
}
              }              
             
           iterator = fetch().iterator();
		  	
	}
	
	public Object next()
	{
		if(iterator !=null && iterator.hasNext())
		  return iterator.next();
	     return null;
	}

    public void close()
    {
    	iterator= null;	
    }
}
