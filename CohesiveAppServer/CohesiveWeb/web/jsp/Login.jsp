<%-- 
    Document   : Login
    Created on : 9 Jun, 2019, 2:44:42 PM
    Author     : IBD Technologies
--%>

<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <title>Cohesive</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Css Library Starts--->
      <link rel="stylesheet" href="/css/library/bootstrap.min.css">
      <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
      <!-- Css Library Ends--->
      <!-- Js Libarary Starts----->
      <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/angular-route.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
      <!-- Js Libarary Ends----->
      <!-- Css Framework Library Ends----->
      <link rel ="stylesheet" href="/css/LoginScreen.css">
      <!-- Css Framework Library Ends----->
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/spinner.js"></script>
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/ChangePwd.js"></script>
      <script type ="text/javascript" src="/js/LoginPage.js"></script>

   <body id="MainCtrl" class="cohesive_body" ng-app="Main" ng-controller="MainCtrl">
        <%
          response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
        response.setHeader("Pragma","no-cache"); //Http 1.0
        response.setHeader("Expires", "-1"); //Proxies
       response.setHeader("X-XSS-Protection","1;mode=block");
       response.setHeader("X-Frame-Options","SAMEORIGIN");
      response.setHeader("Content-Security-Policy","default-src 'self';script-src 'self';style-src 'unsafe-inline' 'self';base-uri 'none';form-action 'self';frame-ancestors 'self'");
      //response.setHeader("Content-Security-Policy","default-src blob:https://cohesive.ibdtechnologies.com ;script-src 'self'; style-src 'unsafe-inline' 'self';base-uri 'none';form-action 'self';frame-ancestors 'none';frame-src blob:https://cohesive.ibdtechnologies.com");
      %>
       
      <header class="LoginscreenHeader">
         <nav class="navbar cohesive_navbar">
            <a class="navbar-brand cohesive_brand navMenuColor">
               <img src="/img/CohesiveLogo.png" class="d-inline-block align-top"  alt="logo">
               <footer class="blockquote-footer navMenuColor">United & Working together effectively</cite></footer>
            </a>
         </nav>
      </header>
      <div id="appContent" class="LoginscreenContent">
      <div>
          
      <div class="card-group">
         <div class="card coheivelogincard">
           <form id="LoginForm" action ="/LoginServlet" ng-controller="MainCtrl"  method="post" >
            <input type="hidden" id="errMessage" ng-model="eMsg" value="${errorMessage}">
             <div class="input-group mb-3">
               <div class="input-group-prepend">
                  <span class="input-group-text cohesiveGroupText"><i class="fa fa-user"></i></span>
               </div>
               <input type="text" class="form-control" placeholder="Username" ng-model="userName" id="wov" name="wov" value="${wov}">
            </div>
            <div class="input-group mb-3">
               <div class="input-group-prepend">
                  <span class="input-group-text cohesiveGroupText"><i class="fa fa-lock"></i></span>
               </div>
               <input type="password" class="form-control" placeholder="Password"  ng-model="loginPassword" id="gls" value="${gls}" name="gls">
            </div>
            <div class="alert alert-warning" role="alert">
                By clicking Login, you agree to our <a href="https://ibdtechnologies.com/html/Terms.html" class="alert-link">Terms of Use Policy</a>. Learn how we collect, use and share your data in our<a href="https://ibdtechnologies.com/html/Legal.html" class="alert-link"> Privacy Policy</a> and how we use cookies and similar technology in our <a href="https://ibdtechnologies.com/html/cookies.html" class="alert-link">Cookie Policy</a>. You may receive SMS notifications from us and can opt out at any time.
             </div>
            <!--<div class=" form-group text-center">
               <!--<a href="#" class="btn btn-primary">Login</a> -->
              <!-- <input type="submit" value="Login" class="btn btn-primary">
            </div> -->
            <div class="form-group text-center">
              <!--<a href="#"   class="outline-primary cohesiveForgotUserName">Forgot username?</a> -->
             <!--  <a href="#"   class="outline-primary cohesiveForgotPassword">Forgot password?</a> -->
             <span>
               <input type="submit" class="btn btn-sm btn-primary" value="Login" >  
               <a href="#"  id="forgotPwd" class="outline-primary cohesiveForgotPassword">Forgot password?</a>
             
             
             </span>
            </div>
           </form> 
         </div>
         <div class="card coheivelogincard">
			<div class="card-header cohesiveCardHeader">
    <h6 class= "mainScreenContentColor mb-3" align="center">Smart school at unbelievably low price </h6>
     
                            <span><img src="/img/gear.svg" class="loginpageImage">
   <b>Features</b></span>	
	</div>
	              <div class="card-body">   
				  <div class="row">
                        <div class="col-6">
                              <p class="text-left">E-circular</p><hr>
                              <p class="text-left">Student and Teacher Performance Reports</p><hr>
                              <p class="text-left">Workflow</p><hr>
                              <p class="text-left">Statistical Analytics</p>
                        </div>
                        <div class="col-6">
                              <p>Fee & Leave management</p><hr>
                              <p>Time table & Exam schedule</p><hr>
                              <p>Digital report cards</p><hr>
                              <p>Teacher substitute  </p>

                        </div>
                     </div>
                  </div>
            </div>
         </div>
      
      <div class="card-group">
         <div class="card coheivelogincard">
            <div class="card-header cohesiveCardHeader">
   <span><img src="/img/customer-service.svg"  class="loginpageImage">
   <b>Supports</b></span>	
	</div>
            <div class="card-body">
               <div class="row">
                  <div class="col-6">
                        <p class="text-left">Customization</p><hr>
                        <p class="text-left">Smooth implementation</p><hr>
                        <p class="text-left">24/7 Support</p><hr>
                  </div>
                  <div class="col-6">
                        <p>Low cost with Global standard</p><hr>
                        <p>Dedicated Business Partner</p><hr>
                        <p>Regular visit by Relation Manager</p>
                  </div>
               </div>
            </div>
         </div>
         <div class="card coheivelogincard">
             <div class="card-header cohesiveCardHeader">
   <span><img src="/img/star.svg"  class="loginpageImage">
   <b>Business  Benefits</b></span>	
	</div>
            <div class="card-body">
               <div class="row">
                  <div class="col-6">
                     <p class="text-left">Free Trail period before Subscription</p><hr>
                     <p class="text-left">Market competitive</p><hr>
                     <p class="text-left">Reputation</p><hr>
					  <p class="text-left">Save money on Webpage and SMS</p><hr>
                     
                  </div>
                  <div class="col-6">
                        <p>Paperless electronic environment</p><hr>
                        <p>Parent relationship</p><hr>
                        <p>Teacher Productivity</p><hr>
                        <p>Decision making</p><hr>
                        <p>Single application for Multiple Institute</p>
                  </div>
               </div>
            </div>
         </div>
      </div>
      </div>
     <div id="snackbar"></div>
     <div id="spinner"></div>
    
       <div class="modal modal left fade " id="ForgotPwdModal" tabindex="-1" role="dialog">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title ChangeInstituteColor">Forgot Your Password?</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body" id="Step1body" ng-show="step1Show">
                                           <div class="alert alert-info" role="alert" ng-show="!errorShow">
                                              Enter the username you use to sign into your Cohesive Account, which is usually your Mobile Number.
                                            </div>
                                            <div class="alert alert-danger" role="alert" id="error1" ng-show="errorShow" ng-bind="errMessage">
                                                 
                                            </div>
                                             <div class="d-flex justify-content-center" ng-show="spinnerShow">
                                                 <!--<h5>Request is in Progress!</h5>-->
                                                <div class="spinner-border text-primary" role="status">
                                                    <span class="sr-only">Loading...</span>
                                                </div>
                                             </div>
                                            
                                          <input class="form-control" type="text" placeholder="Enter Your Mobile Number" ng-model="mobile">                      
                                        </div> 
                                        <div class="modal-footer" id="Step1footer" ng-show="step1Show">
                                            <!--<button type="button" id="changeInstituteOk" class="btn btn-info btn-lg" data-dismiss="modal">Ok</button> -->
                                            <!--<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>--->
                                        <button id="step1Button" type="button" class="btn btn-primary btn-lg btn-block">Continue</button>
                                        </div>
                                    
                                        <div class="modal-body" id="Step2body" ng-show="step2Show">
                                           <div class="alert alert-info" role="alert" ng-show="!errorShow">
                                             If we have an account for the Mobile Number you provided, we have sent a OTP via SMS to reset your password.Please enter the same and continue.
                                           </div>
                                          <div class="alert alert-danger" role="alert" id="error2" ng-show="errorShow" ng-bind="errMessage">
                                                 
                                           </div>
                                           <div class="d-flex justify-content-center" ng-show="spinnerShow">
                                               <!--<h5>Request is in Progress!</h5>-->
                                                <div class="spinner-border text-primary" role="status">
                                                    <span class="sr-only">Loading...</span>
                                                </div>
                                            </div> 
                                            <input class="form-control" type="text" placeholder="Enter OTP Sent Here" ng-model="OTP"> 
                                        
                                        </div>  
                                        
                                         <div id="Step2footer" class="modal-footer" ng-show="step2Show">
                                            <!--<button type="button" id="changeInstituteOk" class="btn btn-info btn-lg" data-dismiss="modal">Ok</button> -->
                                            <!--<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>--->
                                           <button id="resend" type="button" class="btn btn-secondary btn-lg">Resend</button>
                                           <button id="step2Button" type="button" class="btn btn-primary btn-lg btn-block">Continue</button>
                                        </div>
                                       
                                         <div class="modal-body" id="Step3body" ng-show="step3Show">
                                           <div class="alert alert-info" role="alert" ng-show="!errorShow">
                                             Please Enter New Password  and Press Continue  
                                           </div>
                                           <div class="alert alert-danger" role="alert" id="error3" ng-show="errorShow" ng-bind="errMessage">
                                                 
                                            </div>
                                            <div class="d-flex justify-content-center" ng-show="spinnerShow">
                                                <div class="spinner-border text-primary" role="status">
                                                    <!--<h5>Request is in Progress!</h5>-->
                                                    <span class="sr-only">Loading...</span>
                                                </div>
                                             </div>  
                                           <input class="form-control" type="password" placeholder="Enter New Password" ng-model="newPwd"> 
                                           <input class="form-control" type="password" placeholder="Reenter New Password" ng-model="newConfirmPwd"> 
                                        </div>  
                                        
                                         <div id="Step3footer" class="modal-footer" ng-show="step3Show">
                                            <!--<button type="button" id="changeInstituteOk" class="btn btn-info btn-lg" data-dismiss="modal">Ok</button> -->
                                            <!--<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>--->
                                           <button id="step3Button" type="button" class="btn btn-primary btn-lg btn-block">Continue</button>
                                        </div>
                                       
                                         <div class="modal-body" id="Step4body" ng-show="step4Show">
                                           <div class="alert alert-info" role="alert" ng-show="!errorShow">
                                             Password is changed ,Please Login again  
                                           </div>
                                           <div class="alert alert-danger" role="alert" id="error4" ng-show="errorShow" ng-bind="errMessage">
                                                 
                                            </div>
                                            <div class="d-flex justify-content-center" ng-show="spinnerShow">
                                                <div class="spinner-border text-primary" role="status">
                                                    <span class="sr-only">Loading...</span>
                                                </div>
                                             </div>  
                                             
                                           </div>  
                                        
                                         <div id="Step4footer" class="modal-footer" ng-show="step4Show">
                                            <button type="button" id="pwdOk" class="btn btn-info btn-lg" data-dismiss="modal">Ok</button>
                                            <!--<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>--->
                                         </div>
                                        
                                        <div class="modal-body" id="spinnerFpwd"></div>
                                        
                                        </div>
                                        
                                       
                                     </div>
                                </div>
                            </div>
                          
     
      
     <footer class="LoginscreenFooter">
         <p class="text-center cohesiveFooter"> 
            Copy rights &copy; Cohesive 1.0 2018,<br> <strong>IBD Technologies </strong>,All rights reserved</b>
         </p>
      </footer>
      
            
   </body>
   
</html>