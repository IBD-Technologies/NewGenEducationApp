<%-- 
    Document   : Login
    Created on : 13 May, 2020, 12:44:07 AM
    Author     : IBD Technologies
--%>

<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>NewGen Education App</title>
    <!-- Favicon-->
    <link rel="icon" href="images/favicon.ico" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&amp;subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

    <!-- Bootstrap Core Css -->
    <link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="plugins/node-waves/waves.min.css" rel="stylesheet">

    <!-- Animation Css -->
    <link href="plugins/animate-css/animate.min.css" rel="stylesheet">
    <!-- Sweet Alert  -->
    <link href="plugins/sweetalert/sweetalert.min.css" rel="stylesheet">

    <!-- Custom Css -->
    <link href="css/style.min.css" rel="stylesheet">
    <link href="css/login.min.css" rel="stylesheet">
    <link href="css/Utils/spinner.min.css" rel="stylesheet">
           
</head>

<body id="MainCtrl" class="theme-red" ng-app="Main" ng-controller="MainCtrl">
     <% 
          response.setHeader("X-Frame-Options","SAMEORIGIN");  
          response.setHeader("Content-Security-Policy", "default-src 'self';font-src 'self' https://fonts.gstatic.com/ data: fonts.gstatic.com ;script-src 'self' 'unsafe-inline' 'unsafe-eval';style-src 'self' https://fonts.googleapis.com 'unsafe-inline';base-uri 'none';form-action 'self';frame-ancestors 'self';frame-src 'self'");
        %>
    
    <nav class="navbar ">
        <div class="customeHeader bg-teal">
            <center><img class="logoImage" src="images/logo04.png"></center>
        </div>
    </nav>  
    <div class="container-fluid">
     <div class="row clearfix">
        <center>
            <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 marginTop">
            <div class="card">
               <center><img class="appIcon" src="images/app-icon.png"></center>
                <div class="body">
			   <form id="LoginForm" action ="/LoginServlet" ng-controller="MainCtrl"  method="post" >
                     <input type="hidden" id="errMessage" ng-model="eMsg" value="${errorMessage}">
                    <div class="msg form-group">Sign in to start your session</div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" placeholder="Username" required autofocus ng-model="userName" id="wov" name="wov" value="${wov}">
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control"  placeholder="Password" required ng-model="loginPassword" id="gls" value="${gls}" name="gls">
                        </div>
                    </div>
                    <div class="form-group ">
                        <div class="bg-yellow">
                       <!-- <input type="checkbox" name="terms" id="terms" class="filled-in chk-col-pink">-->
                        <p  class="col-black" for="terms">By clicking Login, you agree to our <a class="font-bold col-cyan" href="https://ibdtechnologies.com/html/Terms.html"><b>Terms of Use Policy</b></a>. Learn how we collect, use and share your data in our <a class="font-bold col-cyan" href="https://ibdtechnologies.com/html/Legal.html"><b>Privacy Policy</b></a> and how we use cookies and similar technology in our <a class="font-bold col-cyan" href="https://ibdtechnologies.com/html/cookies.html"><b>Cookie Policy</b></a>. You may receive SMS/Email/Push notifications from us and can opt out at any time.</p>
                        </div>  
                    </div>
                    <!-- <div class="row">
                        <a  tabindex="3" href="index.html"><button class="btn btn-block bg-blue waves-effect" type="submit">Login</button></a><br><br>
                        <a  tabindex="4" href="/forgotPassword.min.jsp"><span class="font-bold col-teal">Forgot Password ?</span></a>
                    </div> -->   
                   <div class="row">
                        <div class="col-xs-8 p-t-5">
                            <!--<input type="checkbox" name="rememberme" id="rememberme" class="filled-in chk-col-pink"> -->
                           <a class="col-pink" href="/forgotPassword.min.jsp">Forgot Password?</a>
                        </div>
                        <div class="col-xs-4">
                            <a href="#"><button id="loginButton" class="btn btn-block bg-pink waves-effect" type="button">Login</button></a>
                        </div>
                    </div>
                    
                </form>
            </div>
        </div>
    </div>
        </center>
    </div>
    </div>                    
    
	<!-- Page Loader -->
   <div class="page-loader-wrapper">
        <div class="loader">
            <div class="preloader">
                <div class="spinner-layer pl-red">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
            <p>Please wait...</p>
        </div>
    </div>
        
    <!-- #END# Page Loader -->
<div id="snackbar"></div>
<div id="spinner"></div>	
	
 <nav class="bottomFooter">
        <div class="customeFooter bg-teal">
            
            <center>
                <p class="footerText">Copyrights <i class="material-icons">copyright</i> NewGen Education App 2.0 2018-2020,</p>
                <p class="footerText"><strong>IBD Technologies Pvt Ltd,</strong> All rights reserved</p>

            </center>
        </div>
    </nav>	
	
	
	
	
	
	
	
	
   <!--Angular Core JS --------->
       <script src="js/js_library/angular.min.js"></script>
       <script src="js/js_library/angular-route.js"></script>  
    	<!-- Jquery Core Js -->
    <script src="plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="plugins/bootstrap/js/bootstrap.min.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="plugins/node-waves/waves.min.js"></script>

    <!-- Validation Plugin Js -->
    <script src="plugins/jquery-validation/jquery.validate.min.js"></script>
    <!--Sweet Alert JS ---->
    <script src="plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Custom Js -->
    <script src="js/admin.min.js"></script>
        <script src="js/Utils/backEnd.min.js"></script>
        <script src="js/Utils/Exception.min.js"></script>
	<script src="js/Utils/spinner.min.js"></script>
	<script src="js/Utils/config.min.js"></script>
	<script src="js/config/Login/LoginPage.min.js"></script>
	
</body>
</html>