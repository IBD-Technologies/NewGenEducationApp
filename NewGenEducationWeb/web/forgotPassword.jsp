<%-- Document : forgotPassword Created on : 15 May, 2020, 12:33:14 AM Author : IBD Technologies --%>
<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
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
        <link href="plugins/node-waves/waves.min.css" rel="stylesheet" />
        <!-- Animation Css -->
        <link href="plugins/animate-css/animate.min.css" rel="stylesheet" />
        <!-- Sweet Alert  -->
        <link href="plugins/sweetalert/sweetalert.min.css" rel="stylesheet">
        <!-- Custom Css -->
        <link href="css/style.min.css" rel="stylesheet">
        <link href="css/Utils/spinner.min.css" rel="stylesheet"/>
        <link href="css/login.css" rel="stylesheet" />
    </head>
    <body id="MainCtrl" class="theme-red" ng-app="Main" ng-controller="MainCtrl">
        <%
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setHeader("Content-Security-Policy", "default-src 'self';font-src 'self' https://fonts.gstatic.com/ data: fonts.gstatic.com ;script-src 'self';style-src 'self' https://fonts.googleapis.com 'unsafe-inline';base-uri 'none';form-action 'self';frame-ancestors 'self';frame-src 'self'");
        %>

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
        <nav class="navbar ">
            <div class="customeHeader">
                <center><img class="logoImage" src="images/logo04.png"></center>
            </div>
        </nav>
        <div class="container-fluid">


            <div class="row clearfix">

                <center>
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 marginforgotTop">
                        <div class="card">
                            <div class='header'>
                                Resetting Password
                                <ul class="header-dropdown m-r--5">

                                    <li class="dropdown">
                                        <a href="/Login.min.jsp">
                                            <i class="material-icons crossIcon">clear</i>
                                        </a>
                                    </li>
                                </ul> 
                            </div>   


                            <div class="body">

                                <form id="forgot_password">
                                    <div id="Step1body" ng-show="step1Show">
                                        <div class="msg form-group">
                                            <p  class="font-bold col-orange">
                                                Enter your email address or Mobile Number that you used to login. We'll send you an OTP to authenticate reseting password     
                                            </p>
                                        </div>
                                        <!--   <div class="msg alert alert-danger" id="error1" ng-show="errorShow" ng-bind="errMessage"></div> -->

                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="material-icons">person</i>
                                            </span>
                                            <div class="form-line">
                                                <input type="text" class="form-control" name="email" placeholder="Email/Mobile Number" required autofocus ng-model="mobile">
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <div class="form-line"> 
                                                <button id="step1Button" type="button" class="btn btn-block btn-lg bg-pink waves-effect" >Continue</button>
                                            </div>    
                                        </div>

                                    </div>

                                    <div id="Step2body" ng-show="step2Show">
                                        <div class="msg form-group" >
                                            <p  class="font-bold col-orange">
                                                If we have an account for the Mobile Number/email you provided, we have sent a OTP to reset your password.Please enter the same and continue.</p></div>
                                        <!-- <div class="msg alert alert-danger" id="error2" ng-show="errorShow" ng-bind="errMessage"></div> -->
                                        <div class="form-group"> 
                                            <div class="form-line">
                                                <input type="text" class="form-control" name="email" placeholder="Enter OTP sent" required autofocus ng-model="OTP">
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-6 p-t-5">
                                                <button id="resend" type="button" class="btn btn-block btn-lg bg-pink waves-effect">Resend</button>
                                            </div>
                                            <div class="col-xs-6 p-t-5">  
                                                <button id="step2Button" type="button" class="btn btn-block btn-lg bg-pink waves-effect" >Continue</button>
                                            </div>    
                                        </div>
                                    </div>
                                    <div id="Step3body" ng-show="step3Show">
                                        <div class="msg form-group" >
                                            <p  class="font-bold col-orange"> Please enter new password and press continue</p></div>
                                        <!--   <div class="msg alert alert-danger" id="error3" ng-show="errorShow" ng-bind="errMessage"></div> -->

                                        <div class="row">
                                            <div class="form-group">     
                                                <div class="form-line">
                                                    <input type="password" class="form-control" name="pwd" placeholder="Enter new password" required autofocus ng-model="newPwd">
                                                </div>
                                            </div>
                                        </div>    
                                        <div class="row">   
                                            <div class="form-group">   
                                                <div class="form-line">
                                                    <input type="password" class="form-control" name="newpwd" placeholder="Reenter new password" required autofocus ng-model="newConfirmPwd">
                                                </div>
                                            </div> 
                                        </div> 
                                        <div class="row"> 
                                            <div class="form-group">  
                                                <div class="form-line">  
                                                    <button id="step3Button" type="button" class="btn btn-block btn-lg bg-pink waves-effect" >Continue</button>
                                                </div>  
                                            </div>
                                        </div>    
                                    </div> 
                                    <div id="Step4body" ng-show="step4Show">
                                        <div class="msg form-group" role="alert">
                                            <p  class="font-bold col-orange">Password is changed ,please login again</p></div>
                                        <!-- <div class="msg alert alert-danger" id="error4" ng-show="errorShow" ng-bind="errMessage"></div> -->
                                        <div class="row">
                                            <div class="form-group">  
                                                <div class="form-line">  
                                                    <a href="/Login.min.jsp"><button class="btn btn-block bg-pink waves-effect" type="button">Ok</button></a>
                                                </div>  
                                            </div>   

                                        </div>
                                    </div>

                                </form>
                            </div>
                        </div>
                        </form>    
                    </div>
            </div>
        </div>
    </center>       
</div>
</div>    
<div id="snackbar"></div>
<div id="spinner"></div>	
<nav class="bottomFooter">
    <div class="customeFooter">
        <center>
            <p class="footerText">Copyrights <i class="material-icons">copyright</i> NewGen Education App 2.0 2018-2020,</p>
            <p class="footerText"><strong>IBD Technologies Pvt Ltd</strong> All rights reserved</p>
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
<script src="js/Utils/ChangePwd.min.js"></script>
</body>
</html>