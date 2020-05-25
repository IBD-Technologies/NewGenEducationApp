<%-- 
    Document   : MainPage
    Created on : 14 May, 2020, 3:29:31 AM
    Author     : IBD Technologies
--%>

<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
   <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>NewGen Education App</title>
    <!-- Favicon-->
    <link rel="icon" href="favicon.ico" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&amp;subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

    <!-- Bootstrap Core Css -->
    <link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="plugins/node-waves/waves.min.css" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="plugins/animate-css/animate.min.css" rel="stylesheet" />

    <!-- JQuery DataTable Css -->
    <link href="plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.min.css" >

    <!-- Custom Css -->
    <link href="css/style.min.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="css/themes/all-themes.min.css" rel="stylesheet" />
    <!-- custome style for this dashboard -->
    <link href="css/dashboard.min.css" rel="stylesheet" />
	<link href="css/Utils/mainScreen.min.css" rel="stylesheet" />
	
	
</head>

<body id="MainCtrl" class="theme-red" ng-app="Main" ng-controller="MainCtrl" ng-Init="searchShow=false">
      <!--start of navbar using logo and brand name ---><%
         response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
         response.setHeader("Pragma","no-cache"); //Http 1.0
         response.setHeader("Expires", "-1"); //Proxies
         response.setHeader("X-XSS-Protection","1;mode=block");
         //response.setHeader("X-Frame-Options","ALLOW-FROM blob:https://cohesive.ibdtechnologies.com");
         response.setHeader("X-Frame-Options","SAMEORIGIN");
         
         response.setHeader("Content-Security-Policy","default-src 'self';font-src 'self' data: fonts.gstatic.com;script-src 'self';style-src 'unsafe-inline' 'self' https://fonts.googleapis.com;base-uri 'none';form-action 'self';frame-ancestors 'self';frame-src 'self'");
         %>

    <!-- Page Loader -->
   <!-- <div class="page-loader-wrapper">
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
    </div> -->

    <section class="content">
    
      <div class="row clearfix">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="card">
                <div class="header">
                    <h2>DASHBOARD</h2>
                    <ul class="header-dropdown m-r--5">
                        <li class="dropdown">
                            <a href="javascript:void(0);" data-toggle="cardloading" data-loading-effect="timer" data-loading-color="lightBlue">
                                <i class="material-icons">loop</i>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="body">
                    <div class="bottomMargin row clearfix">
                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                            <div class="card">
                                <div class="info-box bg-pink hover-zoom-effect">
                                    <div class="icon">
                                        <i class="material-icons">emoji_people</i>
                                    </div>
                                    <div class="content">
                                        <div class="font-32  align-center">Attendance</div>
                                    </div>

                                </div>
                                <div class="body">
                                    <div class="list-group">
                                        <a href="javascript:void(0);" class="list-group-item">
                                            <span class="badge bg-pink">5</span>Total No Of Teachers</a>
                                            <a href="javascript:void(0);" class="list-group-item">
                                                <span class="badge bg-cyan">0</span>No of teachers leave taken today</a>
                                                <a href="javascript:void(0);" class="list-group-item">
                                                    <span class="badge bg-teal">42</span>Total no of students
                                                </a>
                                                <a href="javascript:void(0);" class="list-group-item">
                                                    <span class="badge bg-orange">0</span>No of students leave taken today
                                                </a>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                                    <div class="card smsCard">
                                                        <div class="info-box bg-green hover-zoom-effect">
                                                            <div class="icon">
                                                                <i class="material-icons">sms</i>
                                                            </div>
                                                            <div class="content">
                                                                <div class="font-32  align-center">SMS</div>
                                                            </div>

                                                        </div>
                                                        <div  class="body">
                                                            <div class="list-group">
                                                                <a href="javascript:void(0);" class="list-group-item">
                                                                    <span class="badge bg-pink">900</span>SMS Limit</a>
                                                                    <a href="javascript:void(0);" class="list-group-item">
                                                                        <span class="badge bg-cyan">544</span>Current SMS Balance</a>
                                                                        <div class="progress">
                                                                            <div class="progress-bar bg-pink progress-bar-striped active" role="progressbar" aria-valuenow="544" aria-valuemin="0" aria-valuemax="900">
                                                                                Progress
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row clearfix">
                                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                    <div class="card">
                                        <div class="info-box bg-indigo hover-zoom-effect">
                                            <div class="icon">
                                                <i class="material-icons">monetization_on</i>
                                            </div>
                                            <div class="content">
                                                <div class="font-32  align-center">FEE</div>
                                            </div>

                                        </div>
                                        <div class="body">
                                            <div class="table-responsive">
                                                <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                                    <thead>
                                                        <tr>
                                                            <th>Total Fee</th>
                                                            <th>Received</th>
                                                            <th>Pending</th>
                                                            <th>Overdue</th>
                                                        </tr>
                                                    </thead>
                                                    <tfoot>
                                                        <tr>
                                                            <th>Total Fee</th>
                                                            <th>Received</th>
                                                            <th>Pending</th>
                                                            <th>Overdue</th>
                                                        </tr>
                                                    </tfoot>
                                                    <tbody>
                                                        <tr>
                                                            <td>159,000.00</td>
                                                            <td>1,000.00</td>
                                                            <td>158,000.00</td>
                                                            <td>0.00</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                            <div class="card">
                                                <div class="info-box bg-teal  hover-zoom-effect">
                                                    <div class="icon">
                                                        <i class="material-icons">autorenew</i>
                                                    </div>
                                                    <div class="content">
                                                        <div class="font-32  align-center">Queue</div>
                                                    </div>

                                                </div>
                                                <div class="body">
                                                    <div class="table-responsive">
                                                <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                                                    <thead>
                                                        <tr>
                                                            <th>Menu</th>
                                                            <th>Action</th>
                                                            <th>Count</th>
                                                        </tr>
                                                    </thead>
                                                    <tfoot>
                                                        <tr>
                                                            <th>Menu</th>
                                                            <th>Action</th>
                                                            <th>Count</th>
                                                        </tr>
                                                    </tfoot>
                                                    <tbody>
                                                        <tr>
                                                            <td>159,000.00</td>
                                                            <td>1,000.00</td>
                                                            <td>1,000.00</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                                    </div>
                                                </div>
                                                
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
            </div>
	<section>
    <section class="footer">
     <div class="row clearfix">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="card">
                <div class="header">
				 <nav>
                    <ul class="pager">
                       <li class="previous">
                       <a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>
					     </li>
                          <li class="next">
                          <a href="javascript:void(0);" tabindex="3" class="waves-effect">Next <span aria-hidden="true">→</span></a>
                          </li>
                    </ul>
                 </nav>
	           </div>
			</div>
		</div>
	 </div>	
    </section>

<button onclick="topFunction()" class="scrollTop" title="Go to top">
    <i class="material-icons">keyboard_arrow_up</i>
</button>

<!-- Jquery Core Js -->
                                <script src="plugins/jquery/jquery.min.js"></script>
                                <!-- Bootstrap Core Js -->
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
                                                          

                                <!-- Custom Js -->
                                <script src="js/Utils/scrollToTop.min.js"></script>
                                <!-- <script src="js/slimScrollCustom.js"></script> -->
                                <script src="js/admin.min.js"></script>

                                <script src="js/pages/tables/jquery-datatable.min.js"></script>
                                 <script src="plugins/slim-scroll/slimscroll.min.js"></script>
                              

                                <!-- Demo Js -->
                                <script src="js/demo.min.js"></script>

                 
                            </body>
                            </html>
            