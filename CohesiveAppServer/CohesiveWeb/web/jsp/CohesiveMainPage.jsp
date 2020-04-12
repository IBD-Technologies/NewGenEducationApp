<%-- 
   Document   : CohesiveMainPage
   Created on : 9 Jun, 2019, 3:56:21 PM
   Author     : IBD Technologies
   --%><%@page session="false" contentType="text/html" pageEncoding="UTF-8"%><%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
   <head>
      <title>Cohesive</title>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Css Library Starts--->
      <link rel="stylesheet" href="/css/library/bootstrap.min.css">
      <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
      <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
      <!-- <link rel="stylesheet" href="/css/library/animate.css"> -->
      <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
      <!-- Css Library Ends--->
      <!-- Js Libarary Starts----->
      <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
      <script src="/js/js_library/jquery-ui.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
      <script type="text/javascript" src="/js/js_library/jquery.formatCurrency-1.4.0/jquery.formatCurrency-1.4.0.js"></script>
      <!-- Js Libarary Ends----->
      <!-- Css Framework Library Ends----->
      <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel="stylesheet" href="/css/utils/search.css">
      <link rel="stylesheet" href="/css/utils/TableView.css">
      <link rel="stylesheet" href="/css/utils/SelectBox.css">
      <link rel="stylesheet" href="/css/utils/DashBoard.css">
      <!-- Css Framework Library Ends----->
      <!--Js Framework library starts-->
      <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type="text/javascript" src="/js/Utils/config.js"></script>
      <script type="text/javascript" src="/js/Utils/search.js"></script>
      <script type="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type="text/javascript" src="/js/Utils/TableView.js"></script>
      <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
      <script type="text/javascript" src="/js/Utils/session.js"></script>
      <script type="text/javascript" src="/js/Utils/spinner.js"></script>
      <script type="text/javascript" src="/js/Utils/ChangePwd.js"></script>
      <script type="text/javascript" src="/js/Utils/DashBoard.js"></script>
      <!-- Js Framework Library Ends-->
      <link rel="stylesheet" href="/css/cohesivemainpage.css">
      <script type="text/javascript" src="/js/CohesiveMainPage.js"></script>
   <body id="MainCtrl" class="cohesive_body" ng-app="Main" ng-controller="MainCtrl" ng-Init="searchShow=false">
      <!--start of navbar using logo and brand name ---><%
         response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
         response.setHeader("Pragma","no-cache"); //Http 1.0
         response.setHeader("Expires", "-1"); //Proxies
         response.setHeader("X-XSS-Protection","1;mode=block");
         //response.setHeader("X-Frame-Options","ALLOW-FROM blob:https://cohesive.ibdtechnologies.com");
         response.setHeader("X-Frame-Options","SAMEORIGIN");
         
         response.setHeader("Content-Security-Policy","default-src 'self';script-src 'self';style-src 'unsafe-inline' 'self';base-uri 'none';form-action 'self';frame-ancestors 'self';frame-src 'self'");
         %>
      <header class="mainscreenHeader MainPageHeader">
         <nav class="navbar cohesive_navbar">
            <a class="navbar-brand cohesive_brand navMenuColor userName1" style="color:white">
            <img src="/img/App-icon.png" class="d-inline-block align-top" alt="logo">
            <strong>${InstituteName}</strong>
            </a>
         </nav>
         <nav class="iconNavMenu d-flex-lg" id="navMenu">
            <ul class="nav nav-pills" id="MenuPills" role="tablist">
               <li class="nav-item p-1 flex-fill">
                  <a class="nav-link cohesiveNavLink" id="MenuTab" data-toggle="pill" href="#Content" role="tab" aria-selected="false">
                  <i class="fas fa-bars"></i>
                  </a>
               </li>
               <c:if test="${plan!='B' || userType=='P'}">
                  <!--<li class="nav-item p-1 flex-fill"><a class="nav-link" id="NotificationTab" data-toggle="pill" href="#Notifications" role="tab" aria-selected="true"><i class="far fa-bell"></i></a></li>-->
                  <li class="nav-item p-1 flex-fill">
                     <a class="nav-link" id="LogoTab" data-toggle="pill" href="#Logo" role="tab" aria-hidden="true">
                     <i class="fas fa-university"></i>
                     </a>
                  </li>
               </c:if>
               <li class="nav-item p-1 flex-fill">
                  <a class="nav-link" id="SettingsTab" data-toggle="pill" href="#Settings" role="tab" aria-hidden="true">
                  <i class="fas fa-cog"></i>
                  </a>
               </li>
               <%--
                  <c:if test="${plan!='B' || userType=='P'}">
                  	<li class="nav-item p-1 flex-fill">
                  		<a class="nav-link" id="GiftTab" data-toggle="pill" href="#Gift" role="tab" aria-hidden="true">
                  			<i class="fas fa-gift"></i>
                  		</a>
                  	</li>
                  </c:if>--%><%--
                  <c:if test="${plan=='B'}">--%>
               <li class="nav-item p-1 flex-fill">
                  <a class="nav-link userName" id="UserIDTab" data-toggle="pill" href="" role="tab" aria-hidden="true">
                  <strong>${User}</strong>
                  </a>
               </li>
               <%--
                  </c:if>--%>
            </ul>
         </nav>
      </header>
      <div id="appContent" class="mainscreenContent">
         <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
         <input type="hidden" id="uhtuliak" ng-model="uhtuliak" value="${uhtuliak}">
         <input type="hidden" id="UIDServer" ng-model="UIDServer" value="${User}">
         <input type="hidden" id="InstituteServer" ng-model="InstituteServer" value="${Institute}">
         <input type="hidden" id="InstituteNameServer" ng-model="InstituteNameServer" value="${InstituteName}">
         <input type="hidden" id="userType" ng-model="userType" value="${userType}">
         <input type="hidden" id="language" ng-model="language" value="${language}">
         <input type="hidden" id="plan" ng-model="plan" value="${plan}">
         <input type="hidden" id="countryCode" ng-model="countryCode" value="${countryCode}">
         <input type="hidden" id="dummyAmount" value="">
         <div class="tab-content" id="pills-tabContent">
            <div class="tab-pane fade cohesiveContent" id="Content" role="tabpanel">
                <div class="imgcenter">
                  <img class="imgHeader" src="/img/Statistics1.jpg" alt="Card image cap">
                </div>    
               <!-- <h4 class="card-header cohesiveCardHeader">Attendance & Fee</h4>-->
               <c:if test="${userType=='P'}">
                  <h4 class="card-header cohesiveCardHeader">Attendance & Fee</h4>
                  <div class="form-group row">
                     <label for="studName" class="col-3 col-form-label"> Student Name</label>
                     <div class="col-9">
                        <div class="input-group">
                           <input id="studName" type="text" ng-readonly="studentNamereadOnly" ng-model="studentName" class="form-control">
                           <div class="input-group-append">
                              <button type="button" class="btn btn-primary" ng-disabled="studentNameSearchreadOnly" ng-click="fnStudentSearch()">
                              <i class="fas fa-search"></i>
                              </button>
                           </div>
                        </div>
                     </div>
                  </div>
               </c:if>
               <c:if test="${userType!='P'}">
                  <nav ng-show="dashBoardProcessDone">
                     <div class="nav nav-tabs" id="nav-tab" role="tablist">
                        <a class="nav-item dash nav-link active" id="nav-attendance-tab" data-toggle="tab" href="#attendance" role="tab" aria-controls="nav-home" aria-selected="true">Attendance</a>
                        <c:if test="${userType=='A'}">
                           <a class="nav-item dash nav-link" id="nav-fee-tab" data-toggle="tab" href="#fee" role="tab" aria-controls="nav-profile" aria-selected="false">Fee</a>
                        </c:if>
                        <a class="nav-item dash nav-link" id="nav-queue-tab" data-toggle="tab" href="#queue" role="tab" aria-controls="nav-profile" aria-selected="false">Queue</a>
                        <c:if test="${userType=='A'}">
                           <a class="nav-item dash nav-link" id="nav-others-tab" data-toggle="tab" href="#others" role="tab" aria-controls="nav-contact" aria-selected="false">SMS</a>
                        </c:if>
                     </div>
                  </nav>
                  <div ng-show="!dashBoardProcessDone" class="alert alert-warning" role="alert" id="dashBoardAlert">
                     Dashboard is under processing , Please wait!.. You can do other task in application until it gets completed
                  </div>
                  <div ng-show="dashBoardProcessDone" class="tab-content" id="myTabContent">
                     <div class="tab-pane fade show active" id="attendance" role="tabpanel" aria-labelledby="attendance-tab">
                        <c:if test="${userType=='A'}">
                           <ul class="list-group list-group-flush">
                              <li class="list-group-item">Total no of teachers
                                 <span class="badge badge-secondary">{{totalTeachers}}</span>
                              </li>
                              <li class="list-group-item">No of teachers leave taken today
                                 <span class="badge badge-secondary"> {{teacherAttendance}}</span>
                              </li>
                              <li class="list-group-item">Total no of students
                                 <span class="badge badge-secondary">{{totalStudents}}</span>
                              </li>
                              <li class="list-group-item">No of students leave taken today
                                 <span class="badge badge-secondary"> {{studentAttendance}}</span>
                              </li>
                           </ul>
                        </c:if>
                        <c:if test="${userType=='T'}">
                           <ul class="list-group list-group-flush">
                              <li class="list-group-item">Institute working days 
                                 <span class="badge badge-secondary">{{teacherWorkingDays}}</span>
                              </li>
                              <li class="list-group-item">No of days Leave taken 
                                 <span class="badge badge-secondary">{{teacherLeaveDays}}</span>
                              </li>
                              <li class="list-group-item">Total Students in the class {{classOfTheTeacher}} 
                                 <span class="badge badge-secondary">{{totalStudents}}</span>
                              </li>
                              <li class="list-group-item">Total Students Leave taken today
                                 <span class="badge badge-secondary"> {{studentAttendance}}</span>
                              </li>
                           </ul>
                        </c:if>
                     </div>
                     <div class="tab-pane fade" id="fee" role="tabpanel" aria-labelledby="fee-tab">
                        <c:if test="${userType=='A'}">
                           <!-- <ul class="list-group list-group-flush"><li class="list-group-item">Total Fee for this year<span id="totalFee" class="badge badge-secondary currency" ng-model="totalFee"></span></li><li class="list-group-item">Collected Amount<span id="collectedAmount" class="badge badge-secondary currency" ng-model="collectedAmount"></span></li><li class="list-group-item">Amount yet to be collected <span id="pendingAmount" class="badge badge-secondary currency" ng-model="pendingAmount"></span></li><li class="list-group-item">Amount Overdue<span id="overDueAmount" class="badge badge-secondary currency" ng-model="overDueAmount"></span></li></ul>-->
                           <!--<div class="table-responsive" id="institutFeeDetails">-->
                           <div class="text-center contentDetailTab">
                              <strong>Fee Details - {{instituteFeeDetailsRecord.feeType}}</strong>
                           </div>
                           <nav class="navbar navbar-light bg-light contentDetailTabNav">
                              <ul class="pagination pagination-sm   mb-0">
                                 <li class="page-item">
                                    <a class="page-link page_style svwRecCount">
                                    <span class="svwRecCount">{{fnSvwGetCurrentPage('institutFeeDetails')}}</span>
                                    </a>
                                 </li>
                                 <li class="page-item">
                                    <a class="page-link page_style svwRecCount">
                                    <span class="svwRecCount">of
                                    </a>
                                 </li>
                                 <li class="page-item">
                                    <a class="page-link page_style svwRecCount">
                                    <span class="svwRecCount">{{fnSvwGetTotalPage('institutFeeDetails')}}</span>
                                    </a>
                                 </li>
                              </ul>
                              <ul class="pagination pagination-sm justify-content-end mb-0">
                                 <li class="page-item">
                                    <a id="backwardButton" ng-click="fnSvwBackward('institutFeeDetails',$event)" class="page-link page_style">
                                    <span class="badge1 badge-light cohesive_badge">
                                    <i class="fas fa-chevron-left"></i>
                                    </span>
                                    </a>
                                 </li>
                                 <li class="page-item">
                                    <a id="forwardButton" ng-click="fnSvwForward('institutFeeDetails',$event)" class="page-link page_style">
                                    <span class="badge1 badge-light cohesive_badge">
                                    <i class="fas fa-chevron-right"></i>
                                    </span>
                                    </a>
                                 </li>
                                 <li class="page-item">
                                    <a id="addButton" ng-click="fnSvwAddRow('institutFeeDetails',$event)" class="page-link page_style">
                                    <span class="badge1 badge-light cohesive_badge">
                                    <i class="fas fa-plus"></i>
                                    </span>
                                    </a>
                                 </li>
                                 <li class="page-item">
                                    <a id="deleteButton" ng-click="fnSvwDeleteRow('institutFeeDetails',$event)" class="page-link page_style">
                                    <span class="badge1 badge-light cohesive_badge">
                                    <i class="fas fa-minus"></i>
                                    </span>
                                    </a>
                                 </li>
                              </ul>
                           </nav>
                           <div ng-show="instituteFeeDetailsShow" id="institutFeeDetails">
                              <!--<div class="form-group row"><label for="feeType" class="col-3 col-form-label">Fee Type</label><div class="col-9"><div class="input-group"><input id="feeType" name="feeType" type="text"  ng-model="instituteFeeDetailsRecord.feeType" class="form-control currency" readonly></div></div></div> -->
                              <div class="form-group row">
                                 <label for="totalFee" class="col-3 col-form-label">Total Fee</label>
                                 <div class="col-9">
                                    <div class="input-group">
                                       <div class="input-group-prepend">
                                          <span class="input-group-text">
                                          <i class="fas fa-rupee-sign"></i>
                                          </span>
                                       </div>
                                       <input id="totalFee" name="totalFee" type="text" ng-model="instituteFeeDetailsRecord.totalFee" class="form-control  currency" readonly>
                                    </div>
                                 </div>
                              </div>
                              <div class="form-group row">
                                 <label for="collectedAmount" class="col-3 col-form-label">Received</label>
                                 <div class="col-9">
                                    <div class="input-group">
                                       <div class="input-group-prepend">
                                          <span class="input-group-text">
                                          <i class="fas fa-rupee-sign"></i>
                                          </span>
                                       </div>
                                       <input id="collectedAmount" name="collectedAmount" type="text" ng-model="instituteFeeDetailsRecord.collectedAmount " class="form-control  currency" readonly>
                                    </div>
                                 </div>
                              </div>
                              <div class="form-group row">
                                 <label for="pendingAmount" class="col-3 col-form-label">Pending</label>
                                 <div class="col-9">
                                    <div class="input-group">
                                       <div class="input-group-prepend">
                                          <span class="input-group-text">
                                          <i class="fas fa-rupee-sign"></i>
                                          </span>
                                       </div>
                                       <input id="pendingAmount" name="pendingAmount" type="text" ng-model="instituteFeeDetailsRecord.pendingAmount " class="form-control  currency" readonly>
                                    </div>
                                 </div>
                              </div>
                              <div class="form-group row">
                                 <label for="overDueAmount" class="col-3 col-form-label">Overdue</label>
                                 <div class="col-9">
                                    <div class="input-group">
                                       <div class="input-group-prepend">
                                          <span class="input-group-text">
                                          <i class="fas fa-rupee-sign"></i>
                                          </span>
                                       </div>
                                       <input id="overDueAmount" name="overDueAmount" type="text" ng-model="instituteFeeDetailsRecord.overDueAmount " class="form-control  currency" readonly>
                                    </div>
                                 </div>
                              </div>
                           </div>
                           <!--</div>-->
                        </c:if>
                        <c:if test="${userType=='T'}">
                           <!--<ul class="list-group list-group-flush"><li class="list-group-item">Amount Overdue<span id="classOverDueAmount" class="badge badge-secondary currency" ng-model="classOverDueAmount"></span></li></ul>-->
                           <div class="table-responsive" id="classFeeDetails">
                              <table class="table  cohesive_table table-sm table-success table-striped  table-bordered ">
                                 <thead align="center">
                                    <tr>
                                       <th scope="col">Fee type</th>
                                       <th scope="col">Amount Overdue</th>
                                    </tr>
                                 </thead>
                                 <tbody align="center">
                                    <tr ng-repeat="X in classFeeDetails">
                                       <td>
                                          <input type="text" class="form-control-plaintext" ng-model="X.feeType" readOnly>
                                       </td>
                                       <td>
                                          <input type="text" class="form-control-plaintext" ng-model="X.classOverDueAmount" readOnly>
                                       </td>
                                    </tr>
                                 </tbody>
                              </table>
                           </div>
                        </c:if>
                     </div>
                     <c:if test="${userType=='A'}">
                        <div class="tab-pane fade" id="others" role="tabpanel" aria-labelledby="others-tab">
                           <ul class="list-group list-group-flush">
                              <li class="list-group-item">SMS Limit
                                 <span id="smsLimit" class="badge badge-secondary ">{{smsLimit}}</span>
                              </li>
                              <li class="list-group-item">Current SMS Balance
                                 <span id="currentSMSBalance" class="badge badge-secondary ">{{currentSMSBalance}}</span>
                              </li>
                           </ul>
                        </div>
                     </c:if>
                     <div class="tab-pane fade" id="queue" role="tabpanel" aria-labelledby="queue-tab">
                        <div class="text-center contentDetailTab">
                           <strong>Authorization pending queue</strong>
                        </div>
                        <nav class="navbar navbar-light bg-light contentDetailTabNav">
                           <ul class="pagination pagination-sm   mb-0">
                              <li class="page-item">
                                 <a class="page-link page_style svwRecCount">
                                 <span class="svwRecCount">{{fnMvwGetCurrentPage('pendingQueueMaster')}}</span>
                                 </a>
                              </li>
                              <li class="page-item">
                                 <a class="page-link page_style svwRecCount">
                                 <span class="svwRecCount">of
                                 </a>
                              </li>
                              <li class="page-item">
                                 <a class="page-link page_style svwRecCount">
                                 <span class="svwRecCount">{{fnMvwGetTotalPage('pendingQueueMaster')}}</span>
                                 </a>
                              </li>
                           </ul>
                           <ul class="pagination pagination-sm justify-content-end mb-0">
                              <li class="page-item">
                                 <a id="backwardButton" ng-click="fnMvwBackward('pendingQueueMaster',$event)" class="page-link page_style">
                                 <span class="badge1 badge-light cohesive_badge">
                                 <i class="fas fa-chevron-left"></i>
                                 </span>
                                 </a>
                              </li>
                              <li class="page-item">
                                 <a id="forwardButton" ng-click="fnMvwForward('pendingQueueMaster',$event)" class="page-link page_style">
                                 <span class="badge1 badge-light cohesive_badge">
                                 <i class="fas fa-chevron-right"></i>
                                 </span>
                                 </a>
                              </li>
                              <li class="page-item">
                                 <a id="addButton" ng-click="fnMvwAddRow('pendingQueueMaster',$event)" class="page-link page_style">
                                 <span class="badge1 badge-light cohesive_badge">
                                 <i class="fas fa-plus"></i>
                                 </span>
                                 </a>
                              </li>
                              <li class="page-item">
                                 <a id="deleteButton" ng-click="fnMvwDeleteRow('pendingQueueMaster',$event)" class="page-link page_style">
                                 <span class="badge1 badge-light cohesive_badge">
                                 <i class="fas fa-minus"></i>
                                 </span>
                                 </a>
                              </li>
                           </ul>
                        </nav>
                        <div class="table-responsive" id="pendingQueueMaster">
                           <table class="table  cohesive_table table-sm  table-bordered table-success table-striped ">
                              <thead align="center">
                                 <tr>
                                    <th scope="col">Service</th>
                                    <th scope="col">Operation</th>
                                    <th scope="col">Count</th>
                                 </tr>
                              </thead>
                              <tbody align="center">
                                 <tr ng-repeat="X in pendingQueueMasterShowObject">
                                    <td>
                                       <input type="text" class="form-control-plaintext dashboradtd" ng-model="X.service" readonly>
                                    </td>
                                    <td>
                                       <input type="text" class="form-control-plaintext" ng-model="X.operation" readonly>
                                    </td>
                                    <td>
                                       <input type="text" class="form-control-plaintext" ng-model="X.count" readonly>
                                    </td>
                                 </tr>
                           </table>
                        </div>
                     </div>
                     <!--<div class="card cohesiveCard"><div class="card-body"><h4 class="card-title">Pending events</h4><h6 class="card-text">There are no events</h6><p class="card-text"></p></div>
                        <!-- <div class="card-footer cohesiveCardHeader"><a id="cardFooter" data-toggle="collapse" data-target="#CardSubmenu"  aria-expanded="false"  aria-controls="studentSubmenu">
                        Show More	</a><div id="CardSubmenu" class="collapse"><p> Hello World </p></div></div> -->
                     <!--  </div>-->
                  </div>
               </c:if>
            </div>
            <div class="tab-pane fade" id="Notifications" role="tabpanel">
               <div class="card">
                  <a href="#" class="btn btn-light cohesive_textbutton card-title">
                     <b>Model Exam</b>
                     <small class="cohesive_small">One day Ago</small>
                     <p> Coming wednesday is pooja Holidays But special class will be conducted on +1 nd +2 Students</p>
                  </a>
               </div>
               <div class="card">
                  <a href="#" class="btn btn-light cohesive_textbutton card-title">
                     <b>Fee Due</b>
                     <small class="cohesive_small">One day Ago</small>
                     <p>Coming Monday is the last date of Second term</p>
                  </a>
               </div>
               <div class="card">
                  <a href="#" class="btn btn-light cohesive_textbutton card-title">
                     <b>Model Exam</b>
                     <small class="cohesive_small">One day Ago</small>
                     <p>Coming Monday is the last date of Second term</p>
                  </a>
               </div>
               <div class="card">
                  <a href="#" class="btn btn-light cohesive_textbutton card-title">
                     <b>Model Exam</b>
                     <small class="cohesive_small">One day Ago</small>
                     <p class="card-text">Coming Monday is the last date of Second term</p>
                  </a>
               </div>
               <div class="card">
                  <a href="#" class="btn btn-light cohesive_textbutton card-title">
                     <b>Coaching Class</b>
                     <small class="cohesive_small">10 hours ago</small>
                     <p>Coming wednesday is pooja Holidays But special class will be conducted on +1 nd +2 Students</p>
                  </a>
               </div>
               <div class="card">
                  <a href="#" class="btn btn-light cohesive_textbutton card-title">
                     <b>Model Exam</b>
                     <small class="cohesive_small">One day Ago</small>
                     <p class="card-text">Coming Wednesday 7th hour model Chemistry Model Exam</p>
                  </a>
               </div>
               <div class="card ">
                  <a href="#" class="btn btn-light cohesive_textbutton card-title">
                     <b>Alert</b>
                     <small class="cohesive_small">5 mins Ago</small>
                     <p class="card-text">This saturday Regular working Day</p>
                  </a>
               </div>
            </div>
            <div class="tab-pane fade" id="Logo" role="tabpanel">
               <div class="embed-responsive cohesive-embed-responsive embed-responsive-1by1 ">
                  <iframe id="frame1" class="embed-responsive-item " src=""></iframe>
               </div>
            </div>
            <div class="tab-pane fade" id="Settings" role="tabpanel">
               <div class="list-group">
                  <a href="#" id="chIns" class="btn btn-light">Change Institute</a>
                  <a href="#" id="forgotPwd" class="btn btn-light">Change Password</a>
                  <a href="#" id="logOut" class="btn btn-light">Sign Out</a>
               </div>
            </div>
            <div class="tab-pane fade" id="Gift" role="tabpanel">
               <div class="card">
                  <div class="card-body Cohesive_cardbody cohesive_topmargin">
                     <div class="card-header Cohesive_cardbody">
                        <div class="btn-toolbar justify-content-center" role="toolbar" aria-label="Toolbar with button groups">
                           <div class="btn-group" role="group" aria-label="First group">
                              <button type="button" class="btn btn-light">Stationary</button>
                              <button type="button" class="btn btn-light">Books</button>
                              <button type="button" class="btn btn-light">Others</button>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
               <div class=" card">
                  <div class="card-header Cohesive_cardbody">
                     <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search product" aria-label="Recipient's username" aria-describedby="basic-addon2">
                        <div class="input-group-append">
                           <span class="input-group-text" id="basic-addon2">
                           <i class="fas fa-search"></i>
                           </span>
                        </div>
                     </div>
                  </div>
               </div>
               <div class="media shadow-sm p-3   rounded">
                  <img class=" align-self-center mr-3 media_image" src="/img/mathematics.jpg" alt="Generic placeholder image">
                  <div class="media-body">
                     <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-book"></i>
                           </span>Mathematics
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-rupee-sign"></i>
                           </span>200
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-store"></i>
                           </span>Kitskart store
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-tag"></i>
                           </span>Technical publications
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-map-marker-alt"></i>
                           </span>BAB Chambers ,Chintadripet, Chennai - 600002
                        </li>
                     </ul>
                  </div>
               </div>
               <div class="media shadow-sm p-3   rounded">
                  <img class=" align-self-center mr-3 media_image" src="/img/mathematics1.jpg" alt="Generic placeholder image">
                  <div class="media-body">
                     <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-book"></i>
                           </span>Mathematics
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-rupee-sign"></i>
                           </span>200
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-store"></i>
                           </span>Kitskart store
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-tag"></i>
                           </span>Eagle publications
                        </li>
                        <li class="list-group-item">
                           <span class="badge badge-light badge-pill">
                           <i class="fas fa-map-marker-alt"></i>
                           </span>BAB Chambers ,Chintadripet, Chennai - 600002
                        </li>
                     </ul>
                  </div>
               </div>
            </div>
         </div>
         <div class="embed-responsive cohesive-embed-responsive embed-responsive-1by1 ">
            <iframe id="frame" class="embed-responsive-item " src=""></iframe>
         </div>
         <div class="modal animated bounceInDown" id="InstituteModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
               <div class="modal-content">
                  <div class="modal-header" ng-show="!searchShow">
                     <h5 class="modal-title ChangeInstituteColor">Change Institute</h5>
                     <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                     </button>
                  </div>
                  <div class="modal-body" ng-show="!searchShow">
                     <div class="form-group row">
                        <label for="iName" class="col-3 col-form-label">Institute Name</label>
                        <div class="col-9">
                           <div class=" input-group">
                              <input id="iName" type="text" ng-readonly="true" ng-model="instituteName" class="form-control">
                              <div class="input-group-append">
                                 <button type="button" class="btn btn-primary" ng-disabled="false" ng-click="fnInstituteNameSearch()">
                                 <i class="fas fa-search"></i>
                                 </button>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="iID" class="col-3 col-form-label">Institute ID</label>
                        <div class="col-9">
                           <div class="input-group">
                              <input id="iID" type="text" ng-readonly="true" ng-model="instituteID" class="form-control">
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="modal-footer" ng-show="!searchShow">
                     <button type="button" id="changeInstituteOk" class="btn btn-info btn-lg" data-dismiss="modal">Ok</button>
                     <!--<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>--->
                  </div>
                  <div class="modal-body" id="searchHeader"></div>
                  <div class="modal-body" id="searchBody" ng-controller="InstituteNamesearch"></div>
               </div>
            </div>
         </div>
         <!--  <div class="modal animated fadeInLeft" id="MenuModel" tabindex="-1" role="dialog" aria-labelledby="MenuModelTitle" aria-hidden="true"> -->
         <div class="modal left fade" id="MenuModel" tabindex="-1" role="dialog" aria-labelledby="MenuModelTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable" role="document">
               <div class="modal-content">
                  <div class="modal-header cohesiveModelHeader">
                     <h5 class="modal-title" id="exampleModalLongTitle">Menu</h5>
                     <button type="button" class="close cohesiveclose" data-dismiss="modal" aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                     </button>
                  </div>
                  <div class="modal-body">
                     <c:if test="${userType=='A'}">
                        <a class="mainmenu btn btn-light" id="institute" data-toggle="collapse" data-target="#instituteSubmenu" aria-expanded="false" aria-controls="institutesubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-school"></i>
                        </span>Institute
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="#" class="submenu btn btn-light" id="GLConfigurations">General Configuration
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="instituteSubmenu" class="submenu collapse" data-parent="#institute"><a href="#" class="submenu btn btn-light" id="CLconfigurations">Class Configuration<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="instituteSubmenu" class="mainmenu1 collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" id="classes" data-toggle="collapse" data-target="#classesSubmenu" aria-expanded="false" aria-controls="classesSubmenu">
                           Class Configuration 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classesSubmenu" class="submenu1 collapse" data-parent="#classesSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="classesSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classesSubmenu" class="submenu1 collapse" data-parent="#classesSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="classesDetail">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#HolidayMaintenanceSubMenu" aria-expanded="false" aria-controls="HolidayMaintenanceSubMenu">
                           Holiday Maintenance 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="HolidayMaintenanceSubMenu" class="submenu1 collapse" data-parent="#HolidayMaintenanceSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="holidayMainSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="HolidayMaintenanceSubMenu" class="submenu1 collapse" data-parent="#HolidayMaintenanceSubMenu">
                           <a href="#" class="submenu btn btn-light" id="HolidayMaintenance">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#GroupingSubMenu" aria-expanded="false" aria-controls="HolidayMaintenanceSubMenu">
                           Group 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="GroupingSubMenu" class="submenu1 collapse" data-parent="#GroupingSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="GroupingSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="GroupingSubMenu" class="submenu1 collapse" data-parent="#GroupingSubMenu">
                           <a href="#" class="submenu btn btn-light" id="Grouping">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#feeManagentSubMenu" aria-expanded="false" aria-controls="feeManagentSubMenu">
                           Fee Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="feeManagentSubMenu" class="submenu1 collapse" data-parent="#feeManagentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="feeManagementSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="feeManagentSubMenu" class="submenu1 collapse" data-parent="#feeManagentSubMenu">
                           <a href="#" class="submenu btn btn-light" id="ClassFM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="mainmenu1 collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" id="feePayment" data-toggle="collapse" data-target="#feePaymentSubmenu" aria-expanded="false" aria-controls="feePaymentSubmenu">
                           Fee Payment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="feePaymentSubmenu" class="submenu1 collapse" data-parent="#feePaymentSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="feePaymentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="feePaymentSubmenu" class="submenu1 collapse" data-parent="#feePaymentSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="feePaymentDetail">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#notificationSubMenu" aria-expanded="false" aria-controls="notificationSubMenu">
                           Notification 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="notificationSubMenu" class="submenu1 collapse" data-parent="#notificationSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="notificationSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="notificationSubMenu" class="submenu1 collapse" data-parent="#notificationSubMenu">
                           <a href="#" class="submenu btn btn-light" id="notification">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#insOtherActivity" aria-expanded="false" aria-controls="insOtherActivity">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="insOtherActivity" class="submenu1 collapse" data-parent="#insOtherActivity">
                           <a href="#" class="submenu1 btn btn-light" id="InsOtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="insOtherActivity" class="submenu1 collapse" data-parent="#insOtherActivity">
                           <a href="#" class="submenu btn btn-light" id="InsOtherActivity">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#assignmentSubMenu" aria-expanded="false" aria-controls="assignmentSubMenu">
                           Video Assignment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="assignmentSubMenu" class="submenu1 collapse" data-parent="#assignmentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="assignmentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="assignmentSubMenu" class="submenu1 collapse" data-parent="#assignmentSubMenu">
                           <a href="#" class="btn btn-light" id="ClassAssignment">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#ecircularSubMenu" aria-expanded="false" aria-controls="ecircularSubMenu">
                           eCircular 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ecircularSubMenu" class="submenu1 collapse" data-parent="#ecircularSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="ecircularSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ecircularSubMenu" class="submenu1 collapse" data-parent="#ecircularSubMenu">
                           <a href="#" class="btn btn-light" id="ClassEcircular">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        <a class="mainmenu btn btn-light" id="classId" data-toggle="collapse" data-target="#classSsubmenu" aria-expanded="false" aria-controls="classSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-warehouse"></i>
                        </span>Class
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#ClassAttendnceSubMenu" aria-expanded="false" aria-controls="ClassAttendnceSubMenu">
                           Attendance 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassAttendnceSubMenu" class="submenu1 collapse" data-parent="#ClassAttendnceSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="ClassAttendanceSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassAttendnceSubMenu" class="submenu1 collapse" data-parent="#ClassAttendnceSubMenu">
                           <a href="#" class="btn btn-light" id="ClassAttendance">Detail  
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#CTBSummary" aria-expanded="false" aria-controls="CTBSummary">
                           Time Table 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="CTBSummary" class="submenu1 collapse" data-parent="#CTBSummary">
                           <a href="#" class="submenu1 btn btn-light" id="ClassTimeTableSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="CTBSummary" class="submenu1 collapse" data-parent="#CTBSummary">
                           <a href="#" class="submenu btn btn-light" id="ClassTimeTable"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#ClassExamScheduleSubMenu" aria-expanded="false" aria-controls="CTBSummary">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassExamScheduleSubMenu" class="submenu1 collapse" data-parent="#ClassExamScheduleSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="ClassExamScheduleSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassExamScheduleSubMenu" class="submenu1 collapse" data-parent="#ClassExamScheduleSubMenu">
                           <a href="" id="ClassExamSchedule" class="submenu btn btn-light">
                           Detail 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#MarkEntrySubMenu" aria-expanded="false" aria-controls="MarkEntrySubMenu">
                           Exam Assessment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="MarkEntrySubMenu" class="submenu1 collapse" data-parent="#MarkEntrySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="MarkEntrySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="MarkEntrySubMenu" class="submenu1 collapse" data-parent="#MarkEntrySubMenu">
                           <a href="#progressCardEntry" class="submenu btn btn-light" id="progressCardEntry"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#SoftSkillEntrySubMenu" aria-expanded="false" aria-controls="SoftSkillEntrySubMenu">
                           Soft Skill Assessment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="SoftSkillEntrySubMenu" class="submenu1 collapse" data-parent="#SoftSkillEntrySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="SoftSkillEntrySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="SoftSkillEntrySubMenu" class="submenu1 collapse" data-parent="#SoftSkillEntrySubMenu">
                           <a href="#softSkillEntry" class="submenu btn btn-light" id="softSkillEntry"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <a class="mainmenu btn btn-light" id="teacher" data-toggle="collapse" data-target="#teacherSubmenu" aria-expanded="false" aria-controls="teacherSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-chalkboard-teacher"></i>
                        </span>Teacher 
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherProfileSubmenu" aria-expanded="false" aria-controls="TeacherProfileSubmenu">
                           Profile 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherProfileSubmenu" class="submenu1 collapse" data-parent="#TeacherProfileSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="TeacherProfileSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherProfileSubmenu" class="submenu1 collapse" data-parent="#TeacherProfileSubmenu">
                           <a href="#" class="btn btn-light" id="teacherProfile"> Detail  
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!-- <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherAttendanceSubmenu" aria-expanded="false" aria-controls="TeacherAttendanceSubmenu">
                           Attendance <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div><div id="TeacherAttendanceSubmenu" class="submenu1 collapse" data-parent="#TeacherAttendanceSubmenu"><a href="#" class="submenu1 btn btn-light" id="TeacherAttendanceSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="TeacherAttendanceSubmenu" class="submenu1 collapse" data-parent="#TeacherAttendanceSubmenu"><a href="#TeacherAttendance" class="btn btn-light" id="TeacherAttendance"> Detail <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="#" class="btn btn-light" id="TeacherCalender"> Calender 
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!-- <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherTimeTableSubmenu" aria-expanded="false" aria-controls="TeacherTimeTableSubmenu">
                           Time Table <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div><div id="TeacherTimeTableSubmenu" class="submenu1 collapse" data-parent="#TeacherTimeTableSubmenu"><a href="#" class="submenu1 btn btn-light" id="TeacherTimeTableSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="TeacherTimeTableSubmenu" class="submenu1 collapse" data-parent="#TeacherTimeTableSubmenu"><a href="#TeacherTimeTable" class="submenu btn btn-light" id="TeacherTimeTable">Detail 
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div> -->
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher">
                           <a href="#" class="btn btn-light" id="TeacherTimeTable"> TimeTable 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TLMsubMenu" aria-expanded="false" aria-controls="TLMsubMenu">
                           Leave Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TLMsubMenu" class="submenu1 collapse" data-parent="#TLMsubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="TLMSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TLMsubMenu" class="submenu1 collapse" data-parent="#TLMsubMenu">
                           <a href="#TeacherLM" class="submenu btn btn-light" id="TeacherLM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#Teacher">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherECircularSubMenu" aria-expanded="false" aria-controls="TeacherECircularSubMenu">
                           eCircular
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherECircularSubMenu" class="submenu1 collapse" data-parent="#TeacherECircularSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="TeacherECircularSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherECircularSubMenu" class="submenu1 collapse" data-parent="#TeacherECircularSubMenu">
                           <a href="#teachECircular" class="submenu btn btn-light " id="teachECircular"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        <!--<div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="" class="mainmenu1 btn btn-light" id="payRole" data-toggle="collapse" aria-expanded="false">
                           Payslip <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <a class="mainmenu btn btn-light" id="Student" data-toggle="collapse" data-target="#studentSubmenu" aria-expanded="false" aria-controls="studentSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-graduation-cap"></i>
                        </span>Student
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentProfileSubMenu" aria-expanded="false" aria-controls="StudentProfileSubMenu">
                           Profile 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProfileSubMenu" class="submenu1 collapse" data-parent="#StudentProfileSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentProfileSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProfileSubMenu" class="submenu1 collapse" data-parent="#StudentProfileSubMenu">
                           <a href="#" class="btn btn-light" id="studentProfile">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!-- <div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="btn btn-light" id="studentAttendance">
                           Attendance <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudTT">
                           Time Table 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudAttendanceSubMenu" aria-expanded="false" aria-controls="StudAssignmentSubMenu">
                           Attendance 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudAttendanceSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu">
                           <a href="#" class="btn btn-light" id="studentAttendance"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <%--
                           <c:if test="${plan!='B'}">--%>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudAssignment">
                           Assignment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        
                        
                        
                        
 <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudAssignmentSubMenu" aria-expanded="false" aria-controls="StudAssignmentSubMenu">
                           Video Assignment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAssignmentSubMenu" class="submenu1 collapse" data-parent="#StudAssignmentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudAssignmentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAssignmentSubMenu" class="submenu1 collapse" data-parent="#StudAssignmentSubMenu">
                           <a href="#" class="btn btn-light" id="StudAssignment"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>

                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        <%--
                           </c:if>--%>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudExam">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="btn btn-light" id="StudentCalender"> Calender
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#studProgressCard" class="btn btn-light " id="studProgressCard">
                           Progress Card 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentProgressCardSubMenu" aria-expanded="false" aria-controls="StudentProgressCardSubMenu">
                           Progress Card 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentProgressCardSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu">
                           <a href="#studProgressCard" class="submenu btn btn-light " id="studProgressCard"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentSoftSkillSubMenu" aria-expanded="false" aria-controls="StudentSoftSkillSubMenu">
                           Soft Skill Assessment
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentSoftSkillSubMenu" class="submenu1 collapse" data-parent="#StudentSoftSkillSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentSoftSkillSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentSoftSkillSubMenu" class="submenu1 collapse" data-parent="#StudentSoftSkillSubMenu">
                           <a href="#studSoftSkill" class="submenu btn btn-light " id="studSoftSkill"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentECircularSubMenu" aria-expanded="false" aria-controls="StudentECircularSubMenu">
                           eCircular
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentECircularSubMenu" class="submenu1 collapse" data-parent="#StudentECircularSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentECircularSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentECircularSubMenu" class="submenu1 collapse" data-parent="#StudentECircularSubMenu">
                           <a href="#studECircular" class="submenu btn btn-light " id="studECircular"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#StudentLM" class="submenu btn btn-light" id="StudentLM">
                           Leave Management <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudleaveManagementSubMenu" aria-expanded="false" aria-controls="StudleaveManagementSubMenu">
                           Leave Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="studentLeaveManagementSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu">
                           <a href="#StudentLM" class="submenu btn btn-light" id="StudentLM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#StudentFM" class="submenu btn btn-light" id="StudentFM"> 
                           Fee Management <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="submenu btn btn-light" id="StudPayment">
                           Payment <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#OtherActivitySubMenu" aria-expanded="false" aria-controls="OtherActivitySubMenu">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="OtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu">
                           <a class="btn btn-light" href="#" id="otherActivity" data-toggle="collapse" data-target="#collapseten" aria-expanded="false" aria-controls="collapseten"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentOtherActivitySubMenu" aria-expanded="false" aria-controls="StudentOtherActivitySubMenu">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentOtherActivitySubMenu" class="submenu1 collapse" data-parent="#StudentOtherActivitySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="OtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentOtherActivitySubMenu" class="submenu1 collapse" data-parent="#StudentOtherActivitySubMenu">
                           <a href="#" class="btn btn-light" id="otherActivity">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentNotificationSubMenu" aria-expanded="false" aria-controls="StudentNotificationSubMenu">
                           Notification 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentNotificationSubMenu" class="submenu1 collapse" data-parent="#StudentNotificationSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentNotificationSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentNotificationSubMenu" class="submenu1 collapse" data-parent="#StudentNotificationSubMenu">
                           <a href="#" class="btn btn-light" id="StudentNotification">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentFeeSubMenu" aria-expanded="false" aria-controls="StudentFeeSubMenu">
                           Fee 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentFeeSubMenu" class="submenu1 collapse" data-parent="#StudentFeeSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentFeeSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentFeeSubMenu" class="submenu1 collapse" data-parent="#StudentFeeSubMenu">
                           <a href="#" class="btn btn-light" id="StudentFee">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentPaymentSubMenu" aria-expanded="false" aria-controls="StudentPaymentSubMenu">
                           Payment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentPaymentSubMenu" class="submenu1 collapse" data-parent="#StudentPaymentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentPaymentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentPaymentSubMenu" class="submenu1 collapse" data-parent="#StudentPaymentSubMenu">
                           <a href="#" class="btn btn-light" id="StudentPayment">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="submenu btn btn-light" id="otherActivity">
                           Other Activity <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <a class="mainmenu btn btn-light" id="User" data-toggle="collapse" data-target="#UserSubmenu" aria-expanded="false" aria-controls="UserSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-user-tag"></i>
                        </span>User
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="UserSubmenu" class="submenu collapse" data-parent="#User">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#userProfileSubmenu" aria-expanded="false" aria-controls="userProfileSubmenu">
                           Profile 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="userProfileSubmenu" class="submenu1 collapse" data-parent="#userProfileSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="userProfileSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="userProfileSubmenu" class="submenu1 collapse" data-parent="#userProfileSubmenu">
                           <a href="#" class="submenu btn btn-light" id="userProfile">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="UserSubmenu" class="submenu collapse" data-parent="#User">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#userRoleSubmenu" aria-expanded="false" aria-controls="userRoleSubmenu">
                           Role 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="userRoleSubmenu" class="submenu1 collapse" data-parent="#userRoleSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="userRoleSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="userRoleSubmenu" class="submenu1 collapse" data-parent="#userRoleSubmenu">
                           <a href="#" class="submenu btn btn-light" id="userRole">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <a class="mainmenu btn btn-light" id="Report" data-toggle="collapse" data-target="#ReportSubmenu" aria-expanded="false" aria-controls="ReportSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-file-export"></i>
                        </span>Report
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="studentReport">Student
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="teacherReport">Teacher
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="ClassReport">Class
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="comparison">Institute
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="SubsAvailReport">Teacher Substitute
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="FeeReport">Fees 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="PaymentReport">Payments 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="NotificationReport">Notification 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="StudentDetailsReport">Students Register
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <c:if test="${User=='System'}">
                           <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                              <a href="#" class="submenu btn btn-light" id="TableReport">Table Report
                              <span class="badge badge-primary badge-pill cohesiveBadge">
                              <i class="fas fa-arrow-circle-right"></i>
                              </span>
                              </a>
                           </div>
                        </c:if>
                        <!--<a class="mainmenu btn btn-light" id="Batch" data-toggle="collapse" data-target="#BatchSubmenu" aria-expanded="false" aria-controls="Batchsubmenu"><span class="badge-pill cohesiveModalScreenIcon"><i class="fas fa-ribbon"></i></span>Batch<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a>-->
                        <!--<div id="BatchSubmenu" class="submenu collapse" data-parent="#Batch"><a href="#" class="submenu btn btn-light" id="BatchConfiguration">Configuration<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="BatchSubmenu" class="submenu collapse" data-parent="#Batch"><a href="#" class="submenu btn btn-light" id="Batchrun">Run<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="BatchSubmenu" class="submenu collapse" data-parent="#Batch"><a href="#" class="submenu btn btn-light" id="BatchMonitor">Monitor<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                     </c:if>
                     <c:if test="${userType=='T'}">
                        <a class="mainmenu btn btn-light" id="institute" data-toggle="collapse" data-target="#instituteSubmenu" aria-expanded="false" aria-controls="institutesubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-school"></i>
                        </span>Institute
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#notificationSubMenu" aria-expanded="false" aria-controls="notificationSubMenu">
                           Notification 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="notificationSubMenu" class="submenu1 collapse" data-parent="#notificationSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="notificationSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="notificationSubMenu" class="submenu1 collapse" data-parent="#notificationSubMenu">
                           <a href="#" class="submenu btn btn-light" id="notification">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#insOtherActivity" aria-expanded="false" aria-controls="insOtherActivity">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="insOtherActivity" class="submenu1 collapse" data-parent="#insOtherActivity">
                           <a href="#" class="submenu1 btn btn-light" id="InsOtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="insOtherActivity" class="submenu1 collapse" data-parent="#insOtherActivity">
                           <a href="#" class="submenu btn btn-light" id="InsOtherActivity">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="instituteSubmenu" class="submenu collapse" data-parent="#institute">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#assignmentSubMenu" aria-expanded="false" aria-controls="assignmentSubMenu">
                           Video Assignment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="assignmentSubMenu" class="submenu1 collapse" data-parent="#assignmentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="assignmentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="assignmentSubMenu" class="submenu1 collapse" data-parent="#assignmentSubMenu">
                           <a href="#" class="btn btn-light" id="ClassAssignment">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <a class="mainmenu btn btn-light" id="classId" data-toggle="collapse" data-target="#classSsubmenu" aria-expanded="false" aria-controls="classSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-warehouse"></i>
                        </span>Class
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#ClassAttendnceSubMenu" aria-expanded="false" aria-controls="ClassAttendnceSubMenu">
                           Attendance 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassAttendnceSubMenu" class="submenu1 collapse" data-parent="#ClassAttendnceSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="ClassAttendanceSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassAttendnceSubMenu" class="submenu1 collapse" data-parent="#ClassAttendnceSubMenu">
                           <a href="#" class="btn btn-light" id="ClassAttendance">Detail  
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#CTBSummary" aria-expanded="false" aria-controls="CTBSummary">
                           Time Table 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="CTBSummary" class="submenu1 collapse" data-parent="#CTBSummary">
                           <a href="#" class="submenu1 btn btn-light" id="ClassTimeTableSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="CTBSummary" class="submenu1 collapse" data-parent="#CTBSummary">
                           <a href="#" class="submenu btn btn-light" id="ClassTimeTable"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#ClassExamScheduleSubMenu" aria-expanded="false" aria-controls="CTBSummary">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassExamScheduleSubMenu" class="submenu1 collapse" data-parent="#ClassExamScheduleSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="ClassExamScheduleSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ClassExamScheduleSubMenu" class="submenu1 collapse" data-parent="#ClassExamScheduleSubMenu">
                           <a href="" id="ClassExamSchedule" class="submenu btn btn-light">
                           Detail 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#MarkEntrySubMenu" aria-expanded="false" aria-controls="MarkEntrySubMenu">
                           Exam Assessment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="MarkEntrySubMenu" class="submenu1 collapse" data-parent="#MarkEntrySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="MarkEntrySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="MarkEntrySubMenu" class="submenu1 collapse" data-parent="#MarkEntrySubMenu">
                           <a href="#progressCardEntry" class="submenu btn btn-light" id="progressCardEntry"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        <div id="classSsubmenu" class="submenu collapse" data-parent="#classId">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#SoftSkillEntrySubMenu" aria-expanded="false" aria-controls="SoftSkillEntrySubMenu">
                           SoftSkill Assessment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="SoftSkillEntrySubMenu" class="submenu1 collapse" data-parent="#SoftSkillEntrySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="SoftSkillEntrySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="SoftSkillEntrySubMenu" class="submenu1 collapse" data-parent="#SoftSkillEntrySubMenu">
                           <a href="#softSkillEntry" class="submenu btn btn-light" id="softSkillEntry"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        <a class="mainmenu btn btn-light" id="teacher" data-toggle="collapse" data-target="#teacherSubmenu" aria-expanded="false" aria-controls="teacherSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-chalkboard-teacher"></i>
                        </span>Teacher 
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherProfileSubmenu" aria-expanded="false" aria-controls="TeacherProfileSubmenu">
                           Teacher Profile 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherProfileSubmenu" class="submenu1 collapse" data-parent="#TeacherProfileSubmenu">
                           <a href="#" class="submenu1 btn btn-light" id="TeacherProfileSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherProfileSubmenu" class="submenu1 collapse" data-parent="#TeacherProfileSubmenu">
                           <a href="#" class="btn btn-light" id="teacherProfile"> Detail  
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherAttendanceSubmenu" aria-expanded="false" aria-controls="TeacherAttendanceSubmenu">
                           Teacher Attendance <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div><div id="TeacherAttendanceSubmenu" class="submenu1 collapse" data-parent="#TeacherAttendanceSubmenu"><a href="#" class="submenu1 btn btn-light" id="TeacherAttendanceSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="TeacherAttendanceSubmenu" class="submenu1 collapse" data-parent="#TeacherAttendanceSubmenu"><a href="#TeacherAttendance" class="btn btn-light" id="TeacherAttendance"> Detail <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="#" class="btn btn-light" id="TeacherCalender"> Calender 
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!-- <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherTimeTableSubmenu" aria-expanded="false" aria-controls="TeacherTimeTableSubmenu">
                           Teacher Time Table <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div><div id="TeacherTimeTableSubmenu" class="submenu1 collapse" data-parent="#TeacherTimeTableSubmenu"><a href="#" class="submenu1 btn btn-light" id="TeacherTimeTableSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="TeacherTimeTableSubmenu" class="submenu1 collapse" data-parent="#TeacherTimeTableSubmenu"><a href="#TeacherTimeTable" class="submenu btn btn-light" id="TeacherTimeTable">Detail 
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div> -->
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher">
                           <a href="#" class="btn btn-light" id="TeacherTimeTable"> TimeTable 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TLMsubMenu" aria-expanded="false" aria-controls="TLMsubMenu">
                           Leave Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TLMsubMenu" class="submenu1 collapse" data-parent="#TLMsubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="TLMSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TLMsubMenu" class="submenu1 collapse" data-parent="#TLMsubMenu">
                           <a href="#TeacherLM" class="submenu btn btn-light" id="TeacherLM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#TeacherECircularSubMenu" aria-expanded="false" aria-controls="TeacherECircularSubMenu">
                           eCircular
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherECircularSubMenu" class="submenu1 collapse" data-parent="#TeacherECircularSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="TeacherECircularSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="TeacherECircularSubMenu" class="submenu1 collapse" data-parent="#TeacherECircularSubMenu">
                           <a href="#teachECircular" class="submenu btn btn-light " id="teachECircular"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        
                        <!--        <div id="teacherSubmenu" class="submenu collapse" data-parent="#teacher"><a href="" class="mainmenu1 btn btn-light" id="payRole" data-toggle="collapse" aria-expanded="false">
                           Pay Role <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div>-->
                        <a class="mainmenu btn btn-light" id="Student" data-toggle="collapse" data-target="#studentSubmenu" aria-expanded="false" aria-controls="studentSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-graduation-cap"></i>
                        </span>Student
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentProfileSubMenu" aria-expanded="false" aria-controls="StudentProfileSubMenu">
                           Profile 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProfileSubMenu" class="submenu1 collapse" data-parent="#StudentProfileSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentProfileSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProfileSubMenu" class="submenu1 collapse" data-parent="#StudentProfileSubMenu">
                           <a href="#" class="btn btn-light" id="studentProfile">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudTT">
                           Time Table 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudAttendanceSubMenu" aria-expanded="false" aria-controls="StudAssignmentSubMenu">
                           Attendance 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudAttendanceSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu">
                           <a href="#" class="btn btn-light" id="studentAttendance"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <%--
                           <c:if test="${plan!='B'}">--%>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudAssignmentSubMenu" aria-expanded="false" aria-controls="StudAssignmentSubMenu">
                           Video Assignment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAssignmentSubMenu" class="submenu1 collapse" data-parent="#StudAssignmentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudAssignmentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAssignmentSubMenu" class="submenu1 collapse" data-parent="#StudAssignmentSubMenu">
                           <a href="#" class="btn btn-light" id="StudAssignment"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <%--
                           </c:if>--%>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudExamSubMenu" aria-expanded="false" aria-controls="StudExamSubMenu">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudExamSubMenu" class="submenu1 collapse" data-parent="#StudExamSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudExamSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudExamSubMenu" class="submenu1 collapse" data-parent="#StudExamSubMenu">
                           <a href="#" class="btn btn-light" id="StudExam"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudExam">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        
                        
                        
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StuTTSubMenu" aria-expanded="false" aria-controls="StuTTSubMenu">
                           Time Table <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div><div id="StuTTSubMenu" class="submenu1 collapse" data-parent="#StuTTSubMenu"><a href="#" class="submenu1 btn btn-light" id="StudTTSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="StuTTSubMenu" class="submenu1 collapse" data-parent="#StuTTSubMenu"><a href="#" class="btn btn-light" id="StudTT"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="btn btn-light" id="StudentCalender"> Calender
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentProgressCardSubMenu" aria-expanded="false" aria-controls="StudentProgressCardSubMenu">
                           Progress Card 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentProgressCardSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu">
                           <a href="#studProgressCard" class="submenu btn btn-light " id="studProgressCard"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentSoftSkillSubMenu" aria-expanded="false" aria-controls="StudentSoftSkillSubMenu">
                           Soft Skill Assessment
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentSoftSkillSubMenu" class="submenu1 collapse" data-parent="#StudentSoftSkillSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentSoftSkillSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentSoftSkillSubMenu" class="submenu1 collapse" data-parent="#StudentSoftSkillSubMenu">
                           <a href="#studSoftSkill" class="submenu btn btn-light " id="studSoftSkill"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentECircularSubMenu" aria-expanded="false" aria-controls="StudentECircularSubMenu">
                           eCircular
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentECircularSubMenu" class="submenu1 collapse" data-parent="#StudentECircularSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentECircularSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentECircularSubMenu" class="submenu1 collapse" data-parent="#StudentECircularSubMenu">
                           <a href="#studECircular" class="submenu btn btn-light " id="studECircular"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudleaveManagementSubMenu" aria-expanded="false" aria-controls="StudleaveManagementSubMenu">
                           Leave Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="studentLeaveManagementSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu">
                           <a href="#StudentLM" class="submenu btn btn-light" id="StudentLM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#OtherActivitySubMenu" aria-expanded="false" aria-controls="OtherActivitySubMenu">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="OtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu">
                           <a class="mainmenu btn btn-light" href="#otherActivity" id="otherActivity" data-toggle="collapse" data-target="#collapseten" aria-expanded="false" aria-controls="collapseten"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentOtherActivitySubMenu" aria-expanded="false" aria-controls="StudentOtherActivitySubMenu">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentOtherActivitySubMenu" class="submenu1 collapse" data-parent="#StudentOtherActivitySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="OtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentOtherActivitySubMenu" class="submenu1 collapse" data-parent="#StudentOtherActivitySubMenu">
                           <a href="#" class="btn btn-light" id="otherActivity">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentNotificationSubMenu" aria-expanded="false" aria-controls="StudentNotificationSubMenu">
                           Notification 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentNotificationSubMenu" class="submenu1 collapse" data-parent="#StudentNotificationSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentNotificationSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentNotificationSubMenu" class="submenu1 collapse" data-parent="#StudentNotificationSubMenu">
                           <a href="#" class="btn btn-light" id="StudentNotification">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudFeeManagementSubMenu" aria-expanded="false" aria-controls="StudFeeManagementSubMenu">
                           Fee Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudFeeManagementSubMenu" class="submenu1 collapse" data-parent="#StudFeeManagementSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="studentFMSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudFeeManagementSubMenu" class="submenu1 collapse" data-parent="#StudFeeManagementSubMenu">
                           <a href="#StudentFM" class="submenu btn btn-light" id="StudentFM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudPaymentSubMenu" aria-expanded="false" aria-controls="StudPaymentSubMenu">
                           Payment <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div><div id="StudPaymentSubMenu" class="submenu1 collapse" data-parent="#StudPaymentSubMenu"><a href="#" class="submenu1 btn btn-light" id="studPaymentSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="StudPaymentSubMenu" class="submenu1 collapse" data-parent="#StudPaymentSubMenu"><a href="#" class="submenu btn btn-light" id="StudPayment">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentPaymentSubMenu" aria-expanded="false" aria-controls="StudentPaymentSubMenu">
                           Payment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentPaymentSubMenu" class="submenu1 collapse" data-parent="#StudentPaymentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentPaymentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentPaymentSubMenu" class="submenu1 collapse" data-parent="#StudentPaymentSubMenu">
                           <a href="#" class="btn btn-light" id="StudentPayment">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <a class="mainmenu btn btn-light" id="Report" data-toggle="collapse" data-target="#ReportSubmenu" aria-expanded="false" aria-controls="ReportSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-file-export"></i>
                        </span>Report
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="studentReport">Student
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="teacherReport">Teacher
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="ClassReport">Class
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="mainmenu1 collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="comparison">Institute
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="SubsAvailReport">Teacher Substitute
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="NotificationReport">Notification 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                     </c:if>
                     <c:if test="${userType=='P'}">
                        <a class="mainmenu btn btn-light" id="Student" data-toggle="collapse" data-target="#studentSubmenu" aria-expanded="false" aria-controls="studentSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-graduation-cap"></i>
                        </span>Student
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="studentProfile">
                           Profile 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudTT">
                           Time Table 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!-- <div id="StudentProfileSubMenu" class="submenu1 collapse" data-parent="#StudentProfileSubMenu"><a href="#" class="submenu1 btn btn-light" id="StudentProfileSummary" >Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>---->
                        <!--<div id="StudentProfileSubMenu" class="submenu1 collapse" data-parent="#StudentProfileSubMenu"><a href="#" class="btn btn-light" id="studentProfile">Detail<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>---->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="btn btn-light" id="studentAttendance">
                           Attendance <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudAttendanceSubMenu" aria-expanded="false" aria-controls="StudAssignmentSubMenu">
                           Attendance 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudAttendanceSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu">
                           <a href="#" class="btn btn-light" id="studentAttendance"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--- <div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu"><a href="#" class="submenu1 btn btn-light" id="StudAttendanceSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>---->
                        <!---<div id="StudAttendanceSubMenu" class="submenu1 collapse" data-parent="#StudAttendanceSubMenu"><a href="#" class="btn btn-light" id="studentAttendance"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>---->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudAssignment">
                           Video Assignment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        <!---<div id="StudAssignmentSubMenu" class="submenu1 collapse" data-parent="#StudAssignmentSubMenu"><a href="#" class="submenu1 btn btn-light" id="StudAssignmentSummary" >Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>----->
                        <!--<div id="StudAssignmentSubMenu" class="submenu1 collapse" data-parent="#StudAssignmentSubMenu"><a href="#" class="btn btn-light" id="StudAssignment"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>--->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudExam">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="btn btn-light" id="StudentCalender"> Calender
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#studProgressCard" class="submenu btn btn-light " id="studProgressCard">
                           Progress Card 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        <!---<div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu"><a href="#" class="submenu1 btn btn-light" id="StudentProgressCardSummary" >Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>----->
                        <!---<div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu"><a href="#studProgressCard" class="submenu btn btn-light " id="studProgressCard"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>----->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#StudentLM" class="submenu btn btn-light" id="StudentLM">
                           Leave Management <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                      
                        
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudExamSubMenu" aria-expanded="false" aria-controls="StudExamSubMenu">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudExamSubMenu" class="submenu1 collapse" data-parent="#StudExamSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudExamSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudExamSubMenu" class="submenu1 collapse" data-parent="#StudExamSubMenu">
                           <a href="#" class="btn btn-light" id="StudExam"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="#" class="btn btn-light" id="StudExam">
                           Exam Schedule 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StuTTSubMenu" aria-expanded="false" aria-controls="StuTTSubMenu">
                           Time Table <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-chevron-down"></i></span></a></div><div id="StuTTSubMenu" class="submenu1 collapse" data-parent="#StuTTSubMenu"><a href="#" class="submenu1 btn btn-light" id="StudTTSummary">Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="StuTTSubMenu" class="submenu1 collapse" data-parent="#StuTTSubMenu"><a href="#" class="btn btn-light" id="StudTT"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="btn btn-light" id="StudentCalender"> Calender
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentProgressCardSubMenu" aria-expanded="false" aria-controls="StudentProgressCardSubMenu">
                           Progress Card 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentProgressCardSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentProgressCardSubMenu" class="submenu1 collapse" data-parent="#StudentProgressCardSubMenu">
                           <a href="#studProgressCard" class="submenu btn btn-light " id="studProgressCard"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentSoftSkillSubMenu" aria-expanded="false" aria-controls="StudentSoftSkillSubMenu">
                           Soft Skill Assessment
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentSoftSkillSubMenu" class="submenu1 collapse" data-parent="#StudentSoftSkillSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentSoftSkillSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentSoftSkillSubMenu" class="submenu1 collapse" data-parent="#StudentSoftSkillSubMenu">
                           <a href="#studSoftSkill" class="submenu btn btn-light " id="studSoftSkill"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentECircularSubMenu" aria-expanded="false" aria-controls="StudentECircularSubMenu">
                           eCircular
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentECircularSubMenu" class="submenu1 collapse" data-parent="#StudentECircularSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentECircularSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentECircularSubMenu" class="submenu1 collapse" data-parent="#StudentECircularSubMenu">
                           <a href="#studECircular" class="submenu btn btn-light " id="studECircular"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudleaveManagementSubMenu" aria-expanded="false" aria-controls="StudleaveManagementSubMenu">
                           Leave Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="studentLeaveManagementSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu">
                           <a href="#StudentLM" class="submenu btn btn-light" id="StudentLM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu"><a href="#" class="submenu1 btn btn-light" id="studentLeaveManagementSummary" >Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>----->
                        <!---<div id="StudleaveManagementSubMenu" class="submenu1 collapse" data-parent="#StudleaveManagementSubMenu"><a href="#StudentLM" class="submenu btn btn-light" id="StudentLM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>----->
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#StudentFM" class="submenu btn btn-light" id="StudentFM"> 
                           Fee Management <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="StudFeeManagementSubMenu" class="submenu1 collapse" data-parent="#StudFeeManagementSubMenu"><a href="#" class="submenu1 btn btn-light" id="studentFMSummary" >Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="StudFeeManagementSubMenu" class="submenu1 collapse" data-parent="#StudFeeManagementSubMenu"><a href="#StudentFM" class="submenu btn btn-light" id="StudentFM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                       
                        
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#OtherActivitySubMenu" aria-expanded="false" aria-controls="OtherActivitySubMenu">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="OtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu">
                           <a class="mainmenu btn btn-light" href="#otherActivity" id="otherActivity" data-toggle="collapse" data-target="#collapseten" aria-expanded="false" aria-controls="collapseten"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>-->
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentOtherActivitySubMenu" aria-expanded="false" aria-controls="StudentOtherActivitySubMenu">
                           Extra Curricular Activity 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentOtherActivitySubMenu" class="submenu1 collapse" data-parent="#StudentOtherActivitySubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="OtherActivitySummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentOtherActivitySubMenu" class="submenu1 collapse" data-parent="#StudentOtherActivitySubMenu">
                           <a href="#" class="btn btn-light" id="otherActivity">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        
                        
                        
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentNotificationSubMenu" aria-expanded="false" aria-controls="StudentNotificationSubMenu">
                           Notification 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentNotificationSubMenu" class="submenu1 collapse" data-parent="#StudentNotificationSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentNotificationSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentNotificationSubMenu" class="submenu1 collapse" data-parent="#StudentNotificationSubMenu">
                           <a href="#" class="btn btn-light" id="StudentNotification">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudFeeManagementSubMenu" aria-expanded="false" aria-controls="StudFeeManagementSubMenu">
                           Fee Management 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudFeeManagementSubMenu" class="submenu1 collapse" data-parent="#StudFeeManagementSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="studentFMSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudFeeManagementSubMenu" class="submenu1 collapse" data-parent="#StudFeeManagementSubMenu">
                           <a href="#StudentFM" class="submenu btn btn-light" id="StudentFM"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a href="#" class="submenu btn btn-light" id="StudPayment">
                           Payment <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="StudPaymentSubMenu" class="submenu1 collapse" data-parent="#StudPaymentSubMenu"><a href="#" class="submenu1 btn btn-light" id="studPaymentSummary" >Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div><div id="StudPaymentSubMenu" class="submenu1 collapse" data-parent="#StudPaymentSubMenu"><a href="#" class="submenu btn btn-light" id="StudPayment">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <div id="studentSubmenu" class="submenu collapse" data-parent="#Student">
                           <a href="" class="mainmenu1 btn btn-light" data-toggle="collapse" data-target="#StudentPaymentSubMenu" aria-expanded="false" aria-controls="StudentPaymentSubMenu">
                           Payment 
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-chevron-down"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentPaymentSubMenu" class="submenu1 collapse" data-parent="#StudentPaymentSubMenu">
                           <a href="#" class="submenu1 btn btn-light" id="StudentPaymentSummary">Summary
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <div id="StudentPaymentSubMenu" class="submenu1 collapse" data-parent="#StudentPaymentSubMenu">
                           <a href="#" class="btn btn-light" id="StudentPayment">Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                        <!--<div id="studentSubmenu" class="submenu collapse" data-parent="#Student"><a class="submenu btn btn-light" id="otherActivity">
                           Other Activity <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>-->
                        <!---<div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu"><a href="#" class="submenu1 btn btn-light" id="OtherActivitySummary" >Summary<span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>----->
                        <!---<div id="OtherActivitySubMenu" class="submenu1 collapse" data-parent="#OtherActivitySubMenu"><a class="mainmenu btn btn-light" id="otherActivity"> Detail
                           <span class="badge badge-primary badge-pill cohesiveBadge"><i class="fas fa-arrow-circle-right"></i></span></a></div>---->
                        <a class="mainmenu btn btn-light" id="Report" data-toggle="collapse" data-target="#ReportSubmenu" aria-expanded="false" aria-controls="ReportSubmenu">
                        <span class="badge-pill cohesiveModalScreenIcon">
                        <i class="fas fa-file-export"></i>
                        </span>Report
                        <span class="badge badge-primary badge-pill cohesiveBadge">
                        <i class="fas fa-chevron-down"></i>
                        </span>
                        </a>
                        <div id="ReportSubmenu" class="submenu collapse" data-parent="#Report">
                           <a href="#" class="submenu btn btn-light" id="studentReport">Student
                           <span class="badge badge-primary badge-pill cohesiveBadge">
                           <i class="fas fa-arrow-circle-right"></i>
                           </span>
                           </a>
                        </div>
                     </c:if>
                  </div>
               </div>
            </div>
         </div>
         <div id="snackbar"></div>
         <div id="spinner"></div>
         <div class="modal fade bd-example-modal-sm" id="ForgotPwdModal" tabindex="-1" role="dialog">
            <div class="modal-dialog model-dialog-centered modal-sm" role="document">
               <div class="modal-content">
                  <div class="modal-header">
                     <h5 class="modal-title ChangeInstituteColor">Change Your Password?</h5>
                     <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                     <span aria-hidden="true">&times;</span>
                     </button>
                  </div>
                  <div class="modal-body" id="Step3body" ng-show="step3Show">
                     <div class="alert alert-info" role="alert" ng-show="!errorShow">
                        Please Enter New Password and Press Continue
                     </div>
                     <div class="alert alert-danger" role="alert" id="error3" ng-show="errorShow" ng-bind="errMessage"></div>
                     <div class="d-flex justify-content-center" ng-show="spinnerShow">
                        <div class="spinner-border text-primary" role="status">
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
                     <div class="alert alert-danger" role="alert" id="error4" ng-show="errorShow" ng-bind="errMessage"></div>
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
   </body>
</html>