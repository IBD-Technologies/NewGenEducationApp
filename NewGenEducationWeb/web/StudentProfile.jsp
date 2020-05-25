<%-- 
    Document   : StudentProfile
    Created on : 14 May, 2020, 3:29:31 AM
    Author     : IBD Technologies
--%>

<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta charset="UTF-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&amp;subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

        <!-- Bootstrap Core Css -->
        <link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Waves Effect Css -->
        <link href="plugins/node-waves/waves.min.css" rel="stylesheet">

        <!-- Animation Css -->
        <link href="plugins/animate-css/animate.min.css" rel="stylesheet">
        <!-- Bootstrap Material Datetime Picker Css -->

        <!-- Bootstrap Material Datetime Picker Css -->
        <link href="plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.min.css" rel="stylesheet">

        <!-- Bootstrap DatePicker Css -->
        <link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet">
        <!-- Wait Me Css -->
        <link href="plugins/waitme/waitMe.min.css" rel="stylesheet">
        <!-- Bootstrap Select Css -->
        <link href="plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
        <!-- Custom Css -->
        <!-- JQuery DataTable Css -->
        <link href="plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.min.css">
        <link href="css/style.min.css" rel="stylesheet">

        <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
        <link href="css/themes/all-themes.min.css" rel="stylesheet">


        <!-- Bs Stepper css -->
        <link rel="stylesheet" href="css/bs-stepper.min.css">
        <link href="css/Utils/spinner.min.css" rel="stylesheet">
        <!-- for custome style changes -->
        <link rel="stylesheet" type="text/css" href="css/custom.min.css">


    </head>

    <body id="SubScreenCtrl" class="theme-red subScreen" ng-app="SubScreen" ng-controller="SubScreenCtrl" ng-Init="searchShow=true">
      <%
          response.setHeader("X-Frame-Options","SAMEORIGIN");  
          response.setHeader("Content-Security-Policy", "default-src 'self';font-src 'self' https://fonts.gstatic.com/ data: fonts.gstatic.com ;script-src 'self' 'unsafe-inline' 'unsafe-eval';style-src 'self' https://fonts.googleapis.com 'unsafe-inline';base-uri 'none';form-action 'self';frame-ancestors 'self';frame-src 'self'");
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
             
        <section class="content">

            <div class="container-fluid">


                <div class="bottomMargin row clearfix">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="card subScreenwrapper">
                            <div id="screenHeader" class="header subScreenHeader">
                                <ol class="breadcrumb">
                                    <li><a href="#"><i class="material-icons">school</i>  Student</a></li>
                                    <li><a href="#"><i class="material-icons">person</i> Profile</a></li>

                                </ol>

                                <ul class="header-dropdown m-r--5">
                                    <li class="dropdown">
                                        <a class='indexLink' href="#">
                                            <i class="material-icons crossIcon">clear</i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div class="body subScreenBody">
                                <div class="row clearfix subScreenRow">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 ">
                                        <div id="stepper" class="bs-stepper">
                                            <div id="stepperHeader" class="bs-stepper-header" role="tablist"></div>
                                            <div id="stepperContent" class="bs-stepper-content"></div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                             <footer class="subScreenFooter" ng-show='ShowScreenFooter'>
                               <div class="btn-group btn-group-justified" role="group" aria-label="Justified button group">
                                   <a id="previous" href='#' role="button" class="btn bg-teal waves-effect" ng-show="ShowPrevButton">
                                    <i class="material-icons">arrow_back</i>
                                    <span class="subScrFooterButton" >Previous</span>
                                </a>
                                <a id="next" href='#' role="button" class="btn bg-teal waves-effect" ng-show="ShowNextButton">
                                     <span class="subScrFooterButton">Next</span>
                                    <i class="material-icons">arrow_forward</i>
                                </a>
                                   
                                </div>
                             </footer>
                            
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <div id="snackbar"></div>
        <div id="spinner"></div>
       <!-- <button id="goTop" onclick="topFunction()" class="scrollTop" title="Go to top">
            <i class="material-icons">keyboard_arrow_up</i>
        </button>-->
        <!--Angular Core JS --------->
        <script src="js/js_library/angular.min.js"></script>
        <script src="js/js_library/angular-route.js"></script>  
        <!-- Jquery Core Js -->
        <script src="plugins/jquery/jquery.min.js"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
        <!-- Jquery DataTable Plugin Js -->
        <script src="plugins/jquery-datatable/jquery.dataTables.min.js"></script>
        <script src="plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.min.js"></script>
        <script src="plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
        <script src="plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
        <script src="plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
        <script src="plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
        <script src="plugins/jquery-datatable/extensions/export/vfs_fonts.min.js"></script>
        <script src="plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
        <script src="plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>

        <!-- Select Plugin Js -->
        <script src="plugins/bootstrap-select/js/bootstrap-select.min.js"></script>

        <!-- Slimscroll Plugin Js -->
        <script src="plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
        <!-- Waves Effect Plugin Js -->
        <script src="plugins/node-waves/waves.min.js"></script>
        <!-- Autosize Plugin Js -->
        <script src="plugins/autosize/autosize.min.js"></script>
        <!-- Moment Plugin Js -->
        <script src="plugins/momentjs/moment.min.js"></script>
        <!-- Bootstrap Material Datetime Picker Plugin Js -->
        <script src="plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
        <!-- Bootstrap Datepicker Plugin Js -->
        <script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script> 
        <!--File upload style library  -->
        <script type="text/javascript" src="plugins/bootstrap-filestyle-1.2.3/src/bootstrap-filestyle.min.js"></script>
        <!-- BSB Custom -->
        <script src="js/Utils/scrollToTop.min.js"></script>
        <script src="js/admin.min.js"></script>
        <script src="js/pages/forms/basic-form-elements.min.js"></script>
        <!-- <script src="js/pages/index.js"></script> -->
        <script src="js/pages/ui/tooltips-popovers.min.js"></script>
       

                <!-- Custom Js -->
        <script src="js/Utils/backEnd.min.js"></script>
        <script src="js/Utils/Exception.min.js"></script>
        <script src="js/Utils/spinner.min.js"></script>
        <script src="js/Utils/config.min.js"></script>
        <script src="js/Utils/session.min.js"></script>
        <script src="js/Utils/search.min.js"></script> 
        <script src="js/Utils/audit.min.js"></script>
        <script src="js/Utils/stepperOneTemplate.min.js"></script>
        <script src="js/Utils/defaultConfig.min.js"></script>
        <script src="js/Utils/stepper.min.js"></script>
        <script src="js/Utils/stepperAction.min.js"></script>
        <script type="text/javascript" src="js/Utils/ScreenUtils.min.js"></script>
        <script src="js/config/student/profile/studentProfileCreateConfig.min.js"></script>
        <script src="js/config/student/profile/studentProfileQueryConfig.min.js"></script>
        <script src="js/config/student/profile/studentProfileModificationConfig.min.js"></script>
        <script src="js/config/student/profile/studentProfileDeletionConfig.min.js"></script>
        <script src="js/config/student/profile/studentProfileAuthConfig.min.js"></script>
        <script src="js/config/student/profile/studentProfile.min.js"></script>

  
        
    </body>
</html>
