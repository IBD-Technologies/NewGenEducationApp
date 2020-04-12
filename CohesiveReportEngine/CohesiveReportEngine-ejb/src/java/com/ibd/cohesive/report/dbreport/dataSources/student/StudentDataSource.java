/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSources.student;

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
public abstract class StudentDataSource<T> {
    
    private Iterator<T> iterator;
    private Map<String,Object> dataSetParamValues;
    private String studentId;
    private String userID;
    private String service;
    private String loginInstitute;
    private String nokotser;
    private String instituteID;

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
        this.dataSetParamValues = dataSetParamValues;
        Map<String,Object> mur=(Map<String,Object>) appContext;  
             HttpServletRequest request=(HttpServletRequest)mur.get(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST);
//           this.studentId=request.getParameter("studentId");
              
                    
	   this.studentId=request.getParameter("studentId");
           this.instituteID=request.getParameter("instituteID");
	   
           this.nokotser=request.getParameter("nokotser");
           
           this.userID=request.getParameter("userID");
           
           this.loginInstitute=request.getParameter("loginInstitute");
           
           this.service=request.getParameter("service");
            
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
    if (key.equals("studentId"))
    {  
    	   this.studentId=val;
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
     if (key.equals("instituteID"))
    {   
           this.instituteID=val;
    }  

    
    if(key.equals("service")) 
    {
    this.service=val;
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
    
    
    
    
    
    
//private Iterator<T> iterator;
//private Map<String,Object> dataSetParamValues;
//
//private String studentId;
//private String instituteId;
//
//    public String getStudentId() {
//        return studentId;
//    }
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
//           this.studentId=request.getParameter("studentId");
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
