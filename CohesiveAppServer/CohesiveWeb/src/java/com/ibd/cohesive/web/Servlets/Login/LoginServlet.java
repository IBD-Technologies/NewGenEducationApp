/*
 * To change this license header, choose License Headers in Project Properties.
 * To change thstandalone.xmlis template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Servlets.Login;

import com.ibd.businessViews.IAuthenticateService;
import com.ibd.businessViews.IResourceTokenService;
import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.WebDI.DependencyInjection;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import javax.ejb.EJBException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author IBD Technologies
 */
public class LoginServlet extends HttpServlet {
    private CohesiveSession session;
    private DependencyInjection inject;
    //Object obj = new Object();
    

  public void init(ServletConfig config) throws ServletException {
    try {
      session = new CohesiveSession("gateway.properties");
        inject = new DependencyInjection();
    }
    catch (Exception e) {
     throw new ServletException(e);
    }
  }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {  
        session.createGatewaySessionObject();
         dbg("Inside LoginServlet");
        
         String user = request.getParameter("wov");
         String pwd = request.getParameter("gls");
		
         dbg("user:"+user);
         
      /*   synchronized (obj) {
        try {
            obj.wait(10000);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }*/
         
         if(user==null ||pwd==null||user.isEmpty()||pwd.isEmpty())
         {
           throw new BSValidationException("BS_VAL_16~Please Enter Userid and password before submit");
         }
         
         if(user !=null)
         {
             if (user.contains("<") || user.contains(">")||user.contains("{")||user.contains("}"))
             {
                throw new BSValidationException("BS_VAL_17~User ID contains invalid charecters"); 
             }   
         }
         if(pwd !=null)
         {
             if (pwd.contains("<") || pwd.contains(">")||pwd.contains("{")||pwd.contains("}"))
             {
                throw new BSValidationException("BS_VAL_18~Password contains invalid charecters"); 
             }   
         }
         
            //   IAuthenticateService service=(IAuthenticateService) context.lookup("java:global/CohesiveBackend/CohesiveBusiness-ejb/AuthenticateService!com.ibd.businessViews.IAuthenticateService");     
            String token[]=null; 
            boolean comeOutLoop=false;
            int iteratonCount=0;
            while(comeOutLoop==false){    
            try{
                
                
                iteratonCount=iteratonCount+1;
            IAuthenticateService service =inject.getAuthenticateService();
// invoke on the bean
             
             token=  service.loginAuthenticate(user,pwd);
             comeOutLoop=true;
              
             }catch(BSValidationException ex){
                 throw ex;
             }catch(BSProcessingException ex){
                 throw ex;    
             }catch(DBValidationException ex){
                 throw ex;
             }catch(DBProcessingException ex){
                 throw ex;  
             }catch(EJBException ex){

              if(iteratonCount<=10){


                 //                 if(ex.toString().contains("ClosedChannelException")){
                     
                     inject=null;
                     Thread.sleep(3000);
                     inject = new DependencyInjection();
                     
//                      IAuthenticateService service =inject.getAuthenticateService();
// invoke on the bean
             
//               token=  service.loginAuthenticate(user,pwd);
//                 }
              }else{
                  throw ex;
              }
             }catch(Exception ex){
                 
//                 if(ex.toString().contains("ClosedChannelException")){
                     
               if(iteratonCount<=10){      
                     inject=null;
                     Thread.sleep(3000);
                     inject = new DependencyInjection();
                     
//                      IAuthenticateService service =inject.getAuthenticateService();
//// invoke on the bean
//             
//               token=  service.loginAuthenticate(user,pwd);
//                 }
               }else{
                   throw ex;
               }
             }
            
            }
              
               //response.setContentType("text/html;charset=UTF-8");
               Cookie c=new Cookie("ivas",token[0]);
               dbg("Max Age:"+c.getMaxAge());
               dbg("Path:"+c.getPath());
               dbg("domain:"+c.getDomain());
               dbg("Secure:"+c.getSecure());
               c.setHttpOnly(true);
               c.setMaxAge(-1);
               response.addCookie(c);
                                           
             Cookie c2=new Cookie("nekot",token[2]);
               dbg("Max Age1:"+c2.getMaxAge());
               dbg("Path1:"+c2.getPath());
               dbg("domain1:"+c2.getDomain());
               dbg("Secure1:"+c2.getSecure());
               c2.setHttpOnly(true);
               c2.setMaxAge(-1);
               response.addCookie(c2);
              Cookie c3=new Cookie("userType",token[3]);
               dbg("Max Age1:"+c3.getMaxAge());
               dbg("Path1:"+c3.getPath());
               dbg("domain1:"+c3.getDomain());
               dbg("Secure1:"+c3.getSecure());
               c3.setHttpOnly(true);
               c3.setMaxAge(-1);
               response.addCookie(c3);
              
request.setAttribute("uhtuliak",token[1]);
request.setAttribute("User",user);
request.setAttribute("Institute",token[2].split("~")[1]);
request.setAttribute("InstituteName",token[2].split("~")[2]);
dbg("User Type-->"+token[3]);
request.setAttribute("userType",token[3]);
request.setAttribute("language",token[4]);
request.setAttribute("plan",token[5]);
request.setAttribute("countryCode",token[6]);

//request.setAttribute("ivas",token[0]);
//request.setAttribute("nekot",token[2]);
try
            {  
           // dbg("uhtuliak-->"+request.getAttribute("uhtuliak"));
            
           
            //IResourceTokenService restok = inject.getResourceTokenService();
            IResourceTokenService restok = inject.getResourceTokenService();
             //String ivas=request.getAttribute("ivas").toString();
             //String nekot=request.getAttribute("nekot").toString();
             
            dbg("nekot-->"+token[2]);
            dbg("ivas-->"+token[0]);
            
            //dbg("x-uhtuliak-->"+);
            //JWEObject jweObject = JWEObject.parse(jweString);
            JWEInput jweinput =null;
            //String uhtuliak=null;
             //uhtuliak=request.getAttribute("uhtuliak").toString();
           
            dbg("uhtuliak-->"+token[1]);
           
            if (token[1]!=null)
            {  
                jweinput = new JWEInput(token[0],token[2].split("~")[0],token[2].split("~")[1],token[1]);
                    
            //if(service.validateAuthToken(jweinput))
            //{ 
            
            
            
            try{
            
            
                request.setAttribute("nokotser",restok.getWebResourceToken(jweinput, "CohesiveMain"));
                
                
              }catch(EJBException ex){
                 
                 if(ex.toString().contains("ClosedChannelException")){
                     
                     
                     inject=null;
                     inject = new DependencyInjection();
                      restok = inject.getResourceTokenService();
                     
                      request.setAttribute("nokotser",restok.getWebResourceToken(jweinput, "CohesiveMain"));
                 }
                 
             }  
                
                
                
              //chain.doFilter(request, response); 
            }
              else 
            throw (new BSValidationException("Ressource Token Fetching failed")); 
               }
           
         catch (Exception e) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            dbg(e);  
      throw new ServletException(e.toString());
          //  t.printStackTrace();
        }

request.getRequestDispatcher("/jsp/CohesiveMainPage.jsp").forward(request, response);
        }
    
    catch(BSValidationException e)    
    {
   if( e.toString().contains("BS_VAL_17") ||e.toString().contains("BS_VAL_18"))
   {        
request.setAttribute("wov","");
request.setAttribute("gls","");
   }
   else
   {
     dbg("request.getParameter(\"wov\")-->"+request.getParameter("wov"));
     dbg("request.getParameter(\"gls\")-->"+request.getParameter("gls"));
     
     request.setAttribute("wov",request.getParameter("wov"));
     request.setAttribute("gls", request.getParameter("gls"));
  
   }
request.setAttribute("errorMessage", e.toString());
request.getRequestDispatcher("/jsp/Login.jsp").forward(request, response);
//        response.sendError(response.SC_FORBIDDEN,e.toString());
    }   
    catch (Exception e)
    { 
      dbg(e);  
      throw new ServletException(e.toString());
    }
    finally{
            //if(session.isI_session_created_now())
             dbg("session is cleared");  
            session.clearGatewaySessionObject();
           }       
    }
    

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void dbg(String p_value){
        session.getDebug().dbg(p_value);
    }
     private void dbg(Exception ex){
        session.getDebug().exceptionDbg(ex);
    }

    
    
}
