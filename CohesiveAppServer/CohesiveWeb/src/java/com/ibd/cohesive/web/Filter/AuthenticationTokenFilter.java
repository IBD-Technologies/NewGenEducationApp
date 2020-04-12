/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Filter;

import com.ibd.businessViews.IResourceTokenService;
import com.ibd.businessViews.ITokenValidationService;
import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.WebDI.DependencyInjection;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author IBD Technologies
 */
public class AuthenticationTokenFilter implements Filter {
    private CohesiveSession session;
    private DependencyInjection inject;
    private FilterConfig filterConfig = null;
    String test;
    //Object obj=new Object();
    
    public AuthenticationTokenFilter() throws ServletException, NamingException {
          session = new CohesiveSession("gateway.properties");
      //  try {
            inject = new DependencyInjection();
        //} catch (NamingException ex) {
          //  throw new ServletException(ex.toString());
        //}
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        
            dbg("AuthenticationTokenFilter:DoBeforeProcessing");
        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        
            dbg("AuthenticationTokenFilter:DoAfterProcessing");
        

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
         HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
       
    Cookie[] cookies=httpRequest.getCookies();
  if (cookies != null) {
    for (int i=0; i < cookies.length; i++) {
       cookies[i].setHttpOnly(true);
        httpResponse.addCookie(cookies[i]);
     
    }
  }
 
    
    }

private String getServiceName(String URI)
{
    
    String serviceName=URI.substring(URI.lastIndexOf('/')+1,URI.lastIndexOf('.'));
    dbg("URI.lastIndexOf('/')"+URI.lastIndexOf('/'));
    dbg("URI.lastIndexOf('.')"+URI.lastIndexOf('.'));
    
    dbg("ServiceName-->"+serviceName);
   return  serviceName;
}       
        
        
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        Throwable problem = null;
        try
        {
         session.createSessionObject();
         
         dbg("Inside AuthenticationTokenFilter");
       
        
            dbg("AuthenticationTokenFilter:doFilter()");
        
      /*      synchronized (obj) {
        try {
            obj.wait(10000);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }*/
        doBeforeProcessing(request, response);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
         String loginURI = httpRequest.getContextPath();
           dbg("loginURI-->"+loginURI);
           dbg("httpRequest.getRequestURI()-->"+httpRequest.getRequestURI());
           String ServiceName =null;
           String URI=httpRequest.getRequestURI();
           if(URI!=null && !httpRequest.getRequestURI().isEmpty()) 
           {
               if(!(URI.contains("Login") ||URI.equals("/") ))
                ServiceName=getServiceName(URI); 
               
           }  
        boolean isLoginRequest=false;
        boolean isSkiptokenValidate=false;
        if(httpRequest.getRequestURI().equals(loginURI+"/"))
        {
            isLoginRequest=true;
        }
        else if(httpRequest.getRequestURI().endsWith("Cohesive/"))
                {  
                    isLoginRequest=true;
                }
        else if(httpRequest.getRequestURI().endsWith("/Cohesive"))
                {  
                    isLoginRequest=true;
                }
        
        if(httpRequest.getRequestURI().endsWith(".js") ||httpRequest.getRequestURI().endsWith(".css"))
        {
            isSkiptokenValidate=true;
        }
        else if(request.getAttribute("uhtuliak")!=null)
        {
            isSkiptokenValidate=true;
//            try
//            {  
//            dbg("uhtuliak-->"+request.getAttribute("uhtuliak"));
//            isSkiptokenValidate=true;
//           
//            //IResourceTokenService restok = inject.getResourceTokenService();
//            IResourceTokenService restok = inject.getResourceTokenService();
//             String ivas=request.getAttribute("ivas").toString();
//             String nekot=request.getAttribute("nekot").toString();
//             
//            dbg("nekot-->"+nekot);
//            dbg("ivas-->"+ivas);
//            
//            //dbg("x-uhtuliak-->"+);
//            //JWEObject jweObject = JWEObject.parse(jweString);
//            JWEInput jweinput =null;
//            String uhtuliak=null;
//             uhtuliak=request.getAttribute("uhtuliak").toString();
//           
//            dbg("uhtuliak-->"+uhtuliak);
//           
//            if (uhtuliak!=null)
//            {  
//                jweinput = new JWEInput(ivas,nekot.split("~")[0],nekot.split("~")[1],uhtuliak);
//                    
//            //if(service.validateAuthToken(jweinput))
//            //{ 
//                httpRequest.setAttribute("nokotser",restok.getWebResourceToken(jweinput, "CohesiveMain"));
//                
//              chain.doFilter(request, response); 
//            }
//              else 
//            throw (new BSValidationException("Token Validation is not success")); 
//               }
//           
//         catch (Throwable t) {
//            // If an exception is thrown somewhere down the filter chain,
//            // we still want to execute our after processing, and then
//            // rethrow the problem after that.
//            problem = t;
//          //  t.printStackTrace();
//        }
            
        }   
        boolean isLoginPage  = httpRequest.getRequestURI().endsWith("Login.jsp");
        
        
        
        try {
            if (isLoginRequest ||isLoginPage)
            {
                Cookie c=inject.getWebUtil(session).getCookie("ivas", httpRequest);
                
                if (c!=null)
                {    
                c.setMaxAge(0);
                httpResponse.addCookie(c);
                Cookie c2=inject.getWebUtil(session).getCookie("nekot", httpRequest);
                 if (c2!=null)
                 {
                   c2.setMaxAge(0);
                httpResponse.addCookie(c2);
                 }   
                
                
                }
                
               chain.doFilter(request, response); 
            }
            else if(isSkiptokenValidate)
            {   
            chain.doFilter(request, response); 
            }
            else
            { 
          /*  Hashtable<String,String> props = new Hashtable<String,String>();
        // setup the ejb: namespace URL factory
            //props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            props.put("java.naming.factory.initial", "org.jboss.as.naming.InitialContextFactory");
            // create the InitialContext
           Context context = new javax.naming.InitialContext(props);
             ITokenValidationService service=(ITokenValidationService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/TokenValidateService!com.ibd.businessViews.ITokenValidationService"); */    
            int iteratonCount=0;
            boolean comeOutLoop=false;
            while(comeOutLoop==false){ 
             
              try{  
                iteratonCount=iteratonCount+1;
                ITokenValidationService service=inject.getTokenValidationService();
            IResourceTokenService restok = inject.getResourceTokenService();
             Cookie c=inject.getWebUtil(session).getCookie("ivas", httpRequest);
            if (c!=null)
              {        
            dbg("httpOnly-->"+c.isHttpOnly());
            dbg("ivas-->"+c.getValue());
            
            Cookie c1=inject.getWebUtil(session).getCookie("nekot", httpRequest);
            if (c1!=null)
            {dbg("nekot-->"+c1.getValue());
             }
            else
            {
                throw (new BSValidationException("Cookie is not present")); 
            }   
            dbg("x-uhtuliak-->"+httpRequest.getHeader("x-uhtuliak"));
            //JWEObject jweObject = JWEObject.parse(jweString);
            JWEInput jweinput =null;
            String uhtuliak=null;
            String nrb=null;
            if (httpRequest.getHeader("X-uhtuliak") !=null)           
            {
                uhtuliak=httpRequest.getHeader("X-uhtuliak");
            }  
            else if(httpRequest.getParameter("X-uhtuliak") !=null)
           {  
               uhtuliak=httpRequest.getParameter("X-uhtuliak").toString();    
           }
            dbg("uhtuliak-->"+uhtuliak);
            if(httpRequest.getParameter("nrb") !=null)
            {
                nrb=httpRequest.getParameter("nrb");
            }  
            dbg("uhtuliak-->"+nrb);
            if (uhtuliak!=null)
            {  
              /* if(nrb!=null)
                jweinput = new JWEInput(c.getValue(),c1.getValue().split("~")[0],nrb,uhtuliak);
               else */
                jweinput = new JWEInput(c.getValue(),c1.getValue().split("~")[0],c1.getValue().split("~")[1],uhtuliak);
                    
            if(service.validateAuthToken(jweinput))
            { 
               if(nrb!=null) 
               jweinput.setInstid(nrb);
               httpRequest.setAttribute("nokotser",restok.getWebResourceToken(jweinput, ServiceName));
                httpRequest.setAttribute("testEnv",test);
              chain.doFilter(request, response); 
            }
            else
             throw (new BSValidationException("Token Validation is not success")); 
              }
            else 
            throw (new BSValidationException("Token Validation is not success")); 
               }
            else
            {
              throw (new BSValidationException("Cookie is not present"));  
            }   
            
            
            comeOutLoop=true;
            }catch(BSValidationException ex){
                 throw ex;
             }catch(BSProcessingException ex){
                 throw ex;    
//             }catch(DBValidationException ex){
//                 throw ex;
//             }catch(DBProcessingException ex){
//                 throw ex;
             }catch(EJBException ex){

              if(iteratonCount<=10){


                 //                 if(ex.toString().contains("ClosedChannelException")){
                     
                     inject=null;
                     Thread.sleep(3000);
                     inject = new DependencyInjection();

              }else{
                  throw ex;
              }
             }catch(Exception ex){
                 
                     
               if(iteratonCount<=10){      
                     inject=null;
                     Thread.sleep(3000);
                     inject = new DependencyInjection();
                   
               }else{
                   throw ex;
               }
             }
            }
            }
            
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
          //  t.printStackTrace();
        }
        
        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                dbg((Exception)problem);
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                dbg((Exception)problem);
                throw (IOException) problem;
            }
           /* if (problem instanceof BSValidationException)
            {
                   /*HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.sendRedirect("Login.jsp");*/
               /*RequestDispatcher rd=request.getRequestDispatcher("Login.jsp");
               rd.forward(request, response);
                
            }   
            
           else*/
            {     
             sendProcessingError(problem, response);
            }   
        }
        }
        catch(ServletException ex)
        {
          dbg(ex);
           throw ex;
        }   
        catch(Exception ex)
        {
             dbg(ex);
             throw (ServletException)ex;
        }   
        
       finally{
            if(session.isI_session_created_now())
            {    
            dbg("Session is cleared");
            session.clearSessionObject();
            }
           }
     }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) throws ServletException {        
        this.filterConfig = filterConfig;
        test=session.getCohesiveproperties().getProperty("TEST");
        
        
       }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationTokenFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationTokenFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) throws ServletException {
        String stackTrace = getStackTrace(t);  
        dbg("Inside sendProcessingError");
        dbg((Exception)t);
        //if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                 HttpServletResponse httpResponse = (HttpServletResponse) response;
        
          httpResponse.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
        httpResponse.setHeader("Pragma","no-cache"); //Http 1.0
        httpResponse.setHeader("Expires", "-1"); //Proxies
       httpResponse.setHeader("X-XSS-Protection","1;mode=block");
      //response.setHeader("X-Frame-Options","ALLOW-FROM blob:https://cohesive.ibdtechnologies.com");
       httpResponse.setHeader("X-Frame-Options","SAMEORIGIN");
      
       httpResponse.setHeader("Content-Security-Policy","default-src 'self';script-src 'self';style-src 'unsafe-inline' 'self';base-uri 'none';form-action 'self';frame-ancestors 'self';frame-src 'self'");
      
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                /*pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
               // pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N*/
                String msg="<!-- \n" +
"    Document   : Error\n" +
"    Created on : 9 Jun, 2019, 5:01:59 PM\n" +
"    Author     : IBD Technologies\n" +
"-->\n" +
"\n" +
"<!DOCTYPE html>\n" +
"<!--\n" +
"   To change this license header, choose License Headers in Project Properties.\n" +
"   To change this template file, choose Tools | Templates\n" +
"   and open the template in the editor.\n" +
"   -->\n" +
"<html>\n" +
"   <head>\n" +
"      <meta charset=\"UTF-8\">\n" +
"      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"<script src=\"/js/js_library/jquery-3.3.1.min.js\"></script> \n" +
"<script src=\"/js/js_library/bootstrap.min.js\"></script> \n" +
"<script type =\"text/javascript\" src=\"/js/Utils/Exception.js\"></script> \n"+
//"<script src=\"/js/Utils/spinner.js\"></script>\n" +
      
"       <script src=\"/js/Utils/error.js\"></script>\n" +
"	  \n" +
"   </head>\n" +
"   <body id =\\\"SubScreenCtrl\\\" class=\\\"cohesive_body\\\" ng-app=\\\"SubScreen\\\"  ng-controller=\\\"SubScreenCtrl\\\">\n" +
"       <header class=\\\"subscreenHeader mb-3\\\">\n" +
"         <div id=\\\"operationsection\\\" class=\\\"subScreenOperationSection\\\">\n" +
"         </div>\n" +
"         <div id=\\\"subscreenHeading\\\">\n" +
"            <button type=\\\"button\\\" class=\\\"btn btn-lg btn-block operation_button\\\" disabled><b>Server Error</b> </button>\n" +
"         </div>\n" +
"      </header>\n" +
"      <div id=\\\"appContent\\\" class=\\\"subscreenContent\\\">\n" +
"	 <!--   <h6>The resource did not process correctly</h6>\n" +
"		<h6>Please contact System Adminstrator</h6> -->\n" ;
  if(t instanceof BSValidationException)
  {    
     msg=msg+" <input type=\"hidden\" id=\"errMessage\" value=\"BS_VAL_100~Auth Valiation Failed\">\n";                       
  }
  else
   {
     msg=msg+" <input type=\"hidden\" id=\"errMessage\" value=\"BS_VAL_101~There is Fatal Error! Please contact System Adminstrator\">\n";   
        }
       
  msg=msg+"      </div>\n" +
"      <footer class=\\\"subscreenFooter\\\">\n" +
"         \n" +
"      </footer>\n" +
"      <div id=\\\"snackbar\\\"></div>\n" +
"   </body>\n" +
"</html>";
               pw.print(msg);       
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
             
                throw (ServletException) ex ;
            }
        /*} else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }*/
        
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    private void dbg(String p_value){
        session.getDebug().dbg(p_value);
    }
     private void dbg(Exception ex){
        session.getDebug().exceptionDbg(ex);
    }

    
}
