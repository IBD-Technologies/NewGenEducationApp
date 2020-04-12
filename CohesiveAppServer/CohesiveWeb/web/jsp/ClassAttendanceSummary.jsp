 <%-- 
   Document   : ClassAttendanceSummary
   Created on : Jul 30, 2019, 1:25:54 PM
   Author     : IBD Technologies
   --%>
<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Css Library Starts----->
      <link rel="stylesheet" href="/css/library/bootstrap.min.css">
      <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
      <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
      <!-- Css Library Ends----->
      <!-- Js Library Starts----->
      <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/angular-route.js"></script>
      <script src="/js/js_library/angular-sanitize.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/jquery-ui.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
      <!-- Js Library Ends--->
      <!-- Css Framework Library Starts--->
      <link rel ="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel ="stylesheet" href="/css/utils/operation.css">
      <link rel ="stylesheet" href="/css/utils/search.css">
      <link rel ="stylesheet" href="/css/utils/audit.css">
      <link rel ="stylesheet" href="/css/utils/TableView.css">
      <link rel ="stylesheet" href="/css/utils/SelectBox.css">
      <!-- Css Framework Library Ends--->
      <!-- Js Framework Library Starts--->	  
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type ="text/javascript" src="/js/Utils/SummaryOperation.js"></script>
      <script type ="text/javascript" src="/js/Utils/search.js"></script>
      <script type ="text/javascript" src="/js/Utils/util.js"></script>
      <script type ="text/javascript" src="/js/Utils/TableView.js"></script>
      <script type ="text/javascript" src="/js/Utils/SelectBox.js"></script>
      <script type ="text/javascript" src="/js/Utils/date_picker.js"></script>
      <script type ="text/javascript" src="/js/Utils/SummaryBridge.js"></script>
      <!--Js Framework Library Ends--->
      <script type ="text/javascript" src="/js/ClassAttendanceSummary.js"></script>
   </head>
   <body id="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
    <%
         response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
         response.setHeader("Pragma","no-cache"); //Http 1.0
         response.setHeader("Expires", "-1"); //Proxies
         response.setHeader("X-XSS-Protection","1;mode=block");
         response.setHeader("X-Frame-Options","SAMEORIGIN");
         response.setHeader("Content-Security-Policy","default-src 'self';img-src 'self' data:;script-src  'self';style-src 'unsafe-inline'  'self';base-uri 'none';form-action 'none';frame-ancestors 'self'");
         %>
      <header id="subscreenHeader" class="subscreenHeader mb-3">
         <div id="subscreenHeading" class="ssHeading" ng-show="!searchShow">
            <h6 align="center">Attendance Summary</h6>
            <div id="operationsection" class="subScreenOperationSection" ng-view>
            </div>
         </div>
         <div id="searchHeader">
         </div>
      </header>
      <div id="subscreenContent" class="subscreenContent">
         <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
         <div id="mastersection" ng-show="!searchShow&&mastershow">
                       <div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Class</label>
                                    <div class=" input-group col-9">
                                        <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>
                                    </div>
                                </div>
            <!--<div class ="form-group row">
               <label for="AttendanceDate" class="col-3 col-form-label">Date</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="AttendanceDate" ng-readonly="dateReadOnly" ng-model="date" class="form-control">
                     <div class="input-group-append">
                        <button type="button" id="AttendanceDateShow" ng-disabled="dateReadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                     </div>
                  </div>
               </div>
            </div>-->
            <div class ="form-group row">
            <label for="fromDate" class="col-3 col-form-label">From Date</label>
            <div class="col-9">
                           <div class="input-group">
                              <input   ng-readonly="fromDatereadOnly" id="fromDate" ng-model="fromDate" class="form-control">
                              <div class="input-group-append">
                                 <button type="button" ng-disabled="fromDatereadOnly" id="fromDateShow" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                              </div>
                           </div>
                        </div>
            </div>
             
            <div class ="form-group row">
            <label for="toDate" class="col-3 col-form-label">To Date</label>
            <div class="col-9">
                           <div class="input-group">
                              <input   ng-readonly="toDatereadonly" id="toDate" ng-model="toDate" class="form-control">
                              <div class="input-group-append">
                                 <button type="button" ng-disabled="toDatereadonly" id="toDateShow" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                              </div
                           </div>
                        </div>
            </div>
            
         </div>
            <div class="form-group row">
               <label for="authStatus" class="col-3 col-form-label">Auth Status</label>
               <div class="col-9">
                  <div class="input-group">
                     <select class="custom-select" id="authStatus" ng-disabled ="authStatreadOnly" ng-model="authStat">
                        <option ng-repeat="x in AuthType"  value="{{x.value}}">{{x.name}}</option>
                     </select>
                  </div>
               </div>
            </div>
         </div>
         <div id="detailSection" ng-show="!searchShow&&detailshow">
            <div class="text-center contentDetailTab">Class Attendance</div>
            <nav class="navbar navbar-light bg-light contentDetailTabNav">
               <ul class="pagination pagination-sm   mb-0">
                  <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('ClassAttendanceSummary')}}</span></a></li>
                  <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                  <li class="page-item"><a class="page-link page_style svwRecCount">
                     <span class="svwRecCount">{{fnMvwGetTotalPage('ClassAttendanceSummary')}}</span></a>
                  </li>
               </ul>
               <div>
               </div>
               <ul class="pagination pagination-sm justify-content-end mb-0">
                  <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('ClassAttendanceSummary',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                  <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('ClassAttendanceSummary',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                  <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('ClassAttendanceSummary',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                  <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('ClassAttendanceSummary',$event)"   
                     class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
               </ul>
            </nav>
            <div class="table-responsive" id="ClassAttendanceSummary">
               <table class="table table-striped table-sm table-bordered ">
                  <thead align="center">
                     <tr>
                        <th scope="col"></th>
                        <th scope="col">Class</th>
                        <th scope="col">Date (DD/MM/YYYY)</th>
                     </tr>
                  </thead>
                  <tbody  align="center">
                     <tr ng-repeat="X in ClassAttendanceSummaryShowObject">
                        <td>
                           <input type="checkbox" ng-model="X.checkBox">
                        </td>
                        <td>{{X.class}}
                        </td>
                        <td>{{X.date}}
                        </td>
                     </tr>
               </table>
            </div>
         </div>
         <div id="searchBody">
         </div>
      </div>
      <footer class="subscreenFooter" ng-show="!searchShow">
         <nav class="nav nav-pills nav-justified">
            <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Filter</a>
            <a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Summary</a>
         </nav>
      </footer>
      <div id="snackbar"></div>
      <div id="spinner"></div>
   </body>
</html>