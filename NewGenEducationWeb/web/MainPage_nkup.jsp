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

    <!-- Morris Chart Css-->
    <!--<link href="plugins/morrisjs/morris.css" rel="stylesheet" /> -->
   

    <!-- JQuery DataTable Css -->
    <link href="plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.min.css" >

    <!-- Custom Css -->
    <link href="css/style.min.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="css/themes/all-themes.min.css" rel="stylesheet" />
    <!-- custome style for this dashboard -->
    <link href="css/dashboard.min.css" rel="stylesheet" />
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
    <!-- Overlay For Sidebars -->
    <div class="overlay"></div>
    <!-- #END# Overlay For Sidebars -->

    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="javascript:void(0);" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false"></a>
                <a href="javascript:void(0);" class="bars" ></a>
                <a class="navbar-brand" href="/MainPage.min.jsp"><img id="logoImage" src="images/download.png"></a>
            </div>
            <div class="navbar-collapse collapse" id="navbar-collapse" aria-expanded="false">
                <ul class="nav navbar-nav navbar-right">
                 <li class="pull-right"> <img class="appIcon" src="images/app-icon.png"></li>
                 <li class="dropdown pull-right">
                    <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
                        <i class="material-icons helpIcon">help_outline</i>
                    </a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="javascript:void(0);" class=" waves-effect waves-block waves-blue">Help</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<section>
    <!-- Left Sidebar -->
    <aside id="leftsidebar" class="sidebar">
        <!-- User Info -->
        <div class="user-info">
            <div class="image">
                <img src="images/user.png" width="48" height="48" alt="User" />
            </div>
            <div class="info-container">
                <div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Junaid</div>
                <div class="email">junaidtahir22@yahoo.com</div>
                <div class="btn-group user-helper-dropdown">
                    <i class="material-icons" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="javascript:void(0);"><i class="material-icons">person</i>Profile</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="javascript:void(0);"><i class="material-icons">account_balance</i>Change Institute</a></li>
                        <li><a href="javascript:void(0);"><i class="material-icons">vpn_key</i>Change Password</a></li>

                        <li role="separator" class="divider"></li>
                        <li><a href="sign-in.html"><i class="material-icons">input</i>Sign Out</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- #User Info -->
        <!-- Menu -->
        <div class="menu">
            <ul class="list">
                <li  class="menuHeader header">MENU</li>
                
                <li class="active">
                    <a  href="index.html">
                        <i class="material-icons">dashboard</i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0);" class="menu-toggle">
                        <i class="material-icons">account_balance</i>
                        <span>Institute</span>
                    </a>
                    <ul class="ml-menu">
                        <li>
                            <a href="instituteGeneralConfig.html">
                                <i class="material-icons">build</i>
                                <span>General Configuration</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteClassConfig.html">
                                <i class="material-icons">class</i>
                                <span>Class Configuration</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteHoliday.html">
                                <i class="material-icons">emoji_food_beverage</i>
                                <span>Holiday Maintenance</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteGroup.html">
                                <i class="material-icons">group</i>
                                <span>Group</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteFeeManagement.html">
                                <i class="material-icons">monetization_on</i>
                                <span>Fee Management</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteFeePayment.html">
                                <i class="material-icons">payment</i>
                                <span>Fee Payment</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteNotification.html">
                                <i class="material-icons">notifications_active</i>
                                <span>Notification</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteExtraCurr.html">
                                <i class="material-icons">directions_bike
                                </i>
                                <span>Extra Curricular Activity</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteVideo.html">
                                <i class="material-icons">video_library</i>
                                <span>Video Assignment</span>
                            </a>
                        </li>
                        <li>
                            <a href="instituteEcircular.html">
                                <i class="material-icons">autorenew</i>
                                <span>eCircular</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:void(0);" class="menu-toggle">
                        <i class="material-icons">class</i>
                        <span>Class</span>
                    </a>
                    <ul class="ml-menu">
                        <li>
                            <a href="classAttendance.html">
                                <i class="material-icons">emoji_people</i>
                                <span>Attendance</span>
                            </a>
                        </li>
                        <li>
                            <a href="classTimeTable.html">
                                <i class="material-icons">av_timer</i>
                                <span>Time Table</span>
                            </a>
                        </li>
                        <li>
                            <a href="classExamSchedule.html">
                                <i class="material-icons">schedule</i>
                                <span>Exam Schedule</span>
                            </a>
                        </li>
                        <li>
                            <a href="classAssessment.html">
                                <i class="material-icons">spellcheck</i>
                                <span>Exam Assessment</span>
                            </a>
                        </li>
                        <li>
                            <a href="classSoftSkill.html">
                                <i class="material-icons">keyboard_hide</i>
                                <span>Soft Skill Assessment</span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:void(0);" class="menu-toggle">
                        <i class="material-icons">supervisor_account</i>
                        <span>Teacher</span>
                    </a>
                    <ul class="ml-menu">
                       <li>
                        <a href="teacherProfile.html">
                            <i class="material-icons">person</i>
                            <span>Profile</span>
                        </a>
                    </li>   
                    <li>
                        <a href="TeacherTimeTable.html">
                            <i class="material-icons">av_timer</i>
                            <span>Time Table</span>
                        </a>
                    </li>
                    <li>
                        <a href="teacherLeave.html">
                            <i class="material-icons">voice_over_off</i>
                            <span>Leave Management</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:void(0);" class="menu-toggle">
                    <i class="material-icons">school
                    </i>
                    <span>Student</span>
                </a>
                <ul class="ml-menu">
                    <li>
                        <a href="student-profile.html">
                            <i class="material-icons">person</i>
                            <span>Profile</span>
                        </a>
                    </li>   
                    <li>
                        <a href="studentTimeTable.html">
                            <i class="material-icons">av_timer</i>
                            <span>Time Table</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentAttendance.html">
                            <i class="material-icons">emoji_people</i>
                            <span>Attendance</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentVideo.html">
                            <i class="material-icons">assignment</i>
                            <span>Video Assignment</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentExam.html">
                            <i class="material-icons">schedule</i>
                            <span>Exam Schedule</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentProgress.html">
                            <i class="material-icons">card_membership</i>
                            <span>Progress Card</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentSoft.html">
                            <i class="material-icons">keyboard_hide</i>
                            <span>Soft Skill Assessment</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentEcircular.html">
                            <i class="material-icons">autorenew</i>
                            <span>eCircular</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentLeave.html">
                            <i class="material-icons">voice_over_off</i>
                            <span>Leave Management</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentExtraCurr.html">
                            <i class="material-icons">directions_run</i>
                            <span>Extra Curricular Activity</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentNotification.html">
                            <i class="material-icons">notifications_active</i>
                            <span>Notification</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentFee.html">
                            <i class="material-icons">monetization_on</i>
                            <span>Fee</span>
                        </a>
                    </li>
                    <li>
                        <a href="studentPayment.html">
                            <i class="material-icons">payment</i>
                            <span>Payment</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:void(0);" class="menu-toggle">
                    <i class="material-icons">person</i>
                    <span>User</span>
                </a>
                <ul class="ml-menu">
                    <li>
                        <a href="userProfile.html">
                            <i class="material-icons">person_outline</i>
                            <span>Profile</span>
                        </a>
                    </li>
                    <li>
                        <a href="userRole.html">
                            <i class="material-icons">verified_user</i>
                            <span>Role</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:void(0);" class="menu-toggle">
                    <i class="material-icons">notes</i>
                    <span>Report</span>
                </a>
                <ul class="ml-menu">
                    <li>
                        <a href="reportStudent.html">
                            <i class="material-icons">school</i>
                            <span>Student</span>
                        </a>
                    </li>
                    <li>
                        <a href="reportTeacher.html">
                            <i class="material-icons">person</i>
                            <span>Teacher</span>
                        </a>
                    </li>
                    <li>
                        <a href="reportClass.html">
                            <i class="material-icons">class</i>
                            <span>Class</span>
                        </a>
                    </li>
                    <li>
                        <a href="reportInstitute.html">
                            <i class="material-icons">account_balance</i>
                            <span>Institute</span>
                        </a>
                    </li> 
                    <li>
                        <a href="reportTeacherSubtitute.html">
                            <i class="material-icons">group</i>
                            <span>Teacher Substitute</span>
                        </a>
                    </li>
                    <li>
                        <a href="reportFee.html">
                            <i class="material-icons">attach_money</i>
                            <span>Fees</span>
                        </a>
                    </li>
                    <li>
                        <a href="reportPayment.html">
                            <i class="material-icons">payment</i>
                            <span>Payments</span>
                        </a>
                    </li>
                    <li>
                        <a href="reportNotification.html">
                            <i class="material-icons">notifications_none</i>
                            <span>Notification</span>
                        </a>
                    </li>
                    <li>
                        <a href="reportStudentRegister.html">
                            <i class="material-icons">person_add</i>
                            <span>Student Register</span>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <!-- #Menu -->
</aside>
<!-- #END# Left Sidebar -->
<!-- Right Sidebar -->
<!--<aside id="rightsidebar" class="right-sidebar">
    <ul class="nav nav-tabs tab-nav-right" role="tablist">
        <li role="presentation" class="active"><a href="#skins" data-toggle="tab">SKINS</a></li>
        <li role="presentation"><a href="#settings" data-toggle="tab">SETTINGS</a></li>
    </ul>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade in active in active" id="skins">
            <ul class="demo-choose-skin">
                <li data-theme="red" class="active">
                    <div class="red"></div>
                    <span>Red</span>
                </li>
                <li data-theme="pink">
                    <div class="pink"></div>
                    <span>Pink</span>
                </li>
                <li data-theme="purple">
                    <div class="purple"></div>
                    <span>Purple</span>
                </li>
                <li data-theme="deep-purple">
                    <div class="deep-purple"></div>
                    <span>Deep Purple</span>
                </li>
                <li data-theme="indigo">
                    <div class="indigo"></div>
                    <span>Indigo</span>
                </li>
                <li data-theme="blue">
                    <div class="blue"></div>
                    <span>Blue</span>
                </li>
                <li data-theme="light-blue">
                    <div class="light-blue"></div>
                    <span>Light Blue</span>
                </li>
                <li data-theme="cyan">
                    <div class="cyan"></div>
                    <span>Cyan</span>
                </li>
                <li data-theme="teal">
                    <div class="teal"></div>
                    <span>Teal</span>
                </li>
                <li data-theme="green">
                    <div class="green"></div>
                    <span>Green</span>
                </li>
                <li data-theme="light-green">
                    <div class="light-green"></div>
                    <span>Light Green</span>
                </li>
                <li data-theme="lime">
                    <div class="lime"></div>
                    <span>Lime</span>
                </li>
                <li data-theme="yellow">
                    <div class="yellow"></div>
                    <span>Yellow</span>
                </li>
                <li data-theme="amber">
                    <div class="amber"></div>
                    <span>Amber</span>
                </li>
                <li data-theme="orange">
                    <div class="orange"></div>
                    <span>Orange</span>
                </li>
                <li data-theme="deep-orange">
                    <div class="deep-orange"></div>
                    <span>Deep Orange</span>
                </li>
                <li data-theme="brown">
                    <div class="brown"></div>
                    <span>Brown</span>
                </li>
                <li data-theme="grey">
                    <div class="grey"></div>
                    <span>Grey</span>
                </li>
                <li data-theme="blue-grey">
                    <div class="blue-grey"></div>
                    <span>Blue Grey</span>
                </li>
                <li data-theme="black">
                    <div class="black"></div>
                    <span>Black</span>
                </li>
            </ul>
        </div>
        <div role="tabpanel" class="tab-pane fade" id="settings">
            <div class="demo-settings">
                <p>GENERAL SETTINGS</p>
                <ul class="setting-list">
                    <li>
                        <span>Report Panel Usage</span>
                        <div class="switch">
                            <label><input type="checkbox" checked><span class="lever"></span></label>
                        </div>
                    </li>
                    <li>
                        <span>Email Redirect</span>
                        <div class="switch">
                            <label><input type="checkbox"><span class="lever"></span></label>
                        </div>
                    </li>
                </ul>
                <p>SYSTEM SETTINGS</p>
                <ul class="setting-list">
                    <li>
                        <span>Notifications</span>
                        <div class="switch">
                            <label><input type="checkbox" checked><span class="lever"></span></label>
                        </div>
                    </li>
                    <li>
                        <span>Auto Updates</span>
                        <div class="switch">
                            <label><input type="checkbox" checked><span class="lever"></span></label>
                        </div>
                    </li>
                </ul>
                <p>ACCOUNT SETTINGS</p>
                <ul class="setting-list">
                    <li>
                        <span>Offline</span>
                        <div class="switch">
                            <label><input type="checkbox"><span class="lever"></span></label>
                        </div>
                    </li>
                    <li>
                        <span>Location Permission</span>
                        <div class="switch">
                            <label><input type="checkbox" checked><span class="lever"></span></label>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</aside> -->
<!-- #END# Right Sidebar -->
</section>

<section class="content">

<!--
 <div class="block-header">
 </div>
 <div>
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
 </div> 
 -->
 <div class="embed-responsive cohesive-embed-responsive embed-responsive-1by1 ">
            <iframe id="frame" class="embed-responsive-item " src="/DashBoard.min.jsp">
			
			
			</iframe>
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
                                <script src="plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
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
                                <!-- <script src="js/slimScrollCustom.min.js"></script> -->
                                <script src="js/admin.min.js"></script>

                                <script src="js/pages/tables/jquery-datatable.min.js"></script>
                                <script src="js/pages/cards/colored.min.js"></script>
                                <script src="js/pages/index.min.js"></script>
                                <script src="plugins/slim-scroll/slimscroll.min.js"></script>
                              

                                <!-- Demo Js -->
                                <script src="js/demo.min.js"></script>

                 
                            </body>
                            </html>
