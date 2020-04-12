<%-- 
    Document   : StudentAttendance
    Created on : Jul 18, 2019, 7:39:48 PM
    Author     : IBD Technologies
--%>

<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Css Library Starts--->
      <link rel="stylesheet" href="/css/library/bootstrap.min.css">
      <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
      <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
      <!-- Css Library Ends--->
      <!-- Js Libarary Starts----->
      <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/angular-route.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
      <script src="/js/js_library/jquery-ui.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
      <!-- Js Libarary Ends----->
      <!--Css FrameWork Starts-->
      <link rel ="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel ="stylesheet" href="/css/utils/operation.css">
      <link rel ="stylesheet" href="/css/utils/search.css">
      <link rel ="stylesheet" href="/css/utils/audit.css">
	   <link rel ="stylesheet" href="/css/utils/TableView.css">
	   <link rel ="stylesheet" href="/css/utils/SelectBox.css">
      <!--Css FrameWork Ends---->
      <!-- Js FrameWork Library Starts-->
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type="text/javascript" src="/js/Utils/StudentView.js"></script>
	   <%--<c:forEach items="${cookie}" var="currentCookie">  
                          <c:if test="${currentCookie.key=='userType'}">
                <c:if test="${currentCookie.value.value=='P'}">
                    <script type="text/javascript" src="/js/Utils/StudentView.js"></script>
                </c:if>
                     <c:if test="${currentCookie.value.value=='T'}">
                    <script type="text/javascript" src="/js/Utils/Operation.js"></script>
                </c:if>
                <c:if test="${currentCookie.value.value=='A'}">
                    <script type="text/javascript" src="/js/Utils/Operation.js"></script>
                </c:if>
            </c:if> 


    </c:forEach>  --%>
      <script type ="text/javascript" src="/js/Utils/search.js"></script>
      <script type ="text/javascript" src="/js/Utils/util.js"></script>
	  <script type ="text/javascript" src="/js/Utils/TableView.js"></script>
	  <script type ="text/javascript" src="/js/Utils/SelectBox.js"></script>
	    <script src="/js/Utils/date_picker.js"></script>
      <!-- Js FrameWork Library Ends-->
      <script type ="text/javascript" src="/js/StudentAttendance.js"></script>
   </head>
   <body id ="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
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
             <h6 align="center">Student Attendance</h6> 
         <div id="operationsection" class="subScreenOperationSection" ng-view>
         </div>
         </div>
         <div id="searchHeader"> 
         </div>    
      </header>
      <div id="subscreenContent" class="subscreenContent">
          <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
         <div id="mastersection" ng-show="!searchShow&&mastershow">
            <div class ="form-group row">
               <label for="studName" class="col-3 col-form-label">Name</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="studName" type="text" ng-readonly="studentNamereadOnly" ng-model="studentName" class="form-control">
                     <div class="input-group-append">
                        <button type="button" class="btn btn-primary" ng-disabled="studentNameSearchreadOnly" ng-click="fnStudentSearch()"><i class="fas fa-search"></i></button>
                     </div>
                  </div>
               </div>
            </div>
            <div class= "form-group row">
               <label for="studId"  class="col-3 col-form-label">ID</label>
               <div class=" input-group-append col-9">
                  <input id="staffId" type="text" ng-readonly ="studentIDreadOnly" ng-model="studentID" class="form-control" readonly>
               </div>
            </div>
			<div class ="form-group row">
               <label for="AttendanceDate" class="col-3 col-form-label">Date</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="AttendanceDate" ng-readonly="dateReadOnly" ng-model="date" class="form-control">
                     <div class="input-group-append">
                        <button type="button" id="AttendanceDateShow" ng-disabled="dateReadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                     </div>
                  </div>
               </div>
            </div>
         </div>
                <div id="detailSection" ng-show="!searchShow&&detailshow">
		    <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
             <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link active ContentTabNavlink" id="foreNoonTab" data-toggle="tab" href="#ForeNoon" role="tab" aria-selected="true">Fore Noon</a>
               </li>
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link ContentTabNavlink"  id="afterNoonTab" data-toggle="tab" href="#afterNoon" role="tab" aria-selected="false">After Noon</a>
               </li>
            </ul>
		  <div class="tab-content" id="myTab">
          <div class="tab-pane fade show active" id="ForeNoon" role="tabpanel" aria-labelledby="foreNoonTab">
		   <div class="text-center contentDetailTab">Fore Noon Attendance</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0"> 
                     <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('attendanceForeNoon')}}</span></a></li>
						<li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
						<span class="svwRecCount">{{fnMvwGetTotalPage('attendanceForeNoon')}}</span></a></li>
                     </ul>
					 <div>
					 </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                         <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('attendanceForeNoon',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('attendanceForeNoon',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('attendanceForeNoon',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('attendanceForeNoon',$event)"   
						class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
				     <div class="table-responsive" id="attendanceForeNoon">
                     <table class="table table-sm table-bordered ">
                        <thead align="center">
                           <tr>
						      <!--<th></th>--->
                              <th scope="col">Period</th>
                              <th scope="col">Attendance</th>
                           </tr>
                        </thead>
                        <tbody align="center">
						 <tr ng-repeat="X in attendanceForeNoonShowObject">
						<!-- <td><input type="checkbox" ng-model="X.checkBox"></td>-->
						<td>
							{{X.periodNumber}}
							</td>
						     <td>
                            <button id="{{X.periodNumber}}-{{$index}}-F" type="button"  ng-click="fnAttendanceButtonClick(X.periodNumber +'-'+$index+ '-F')"    ng-disabled="attendanceReadOnly" style="" class="btn-lg" ng-class="X.attendenceButtonClass">{{X.attendance}}</button>
						</td>
						
						   </tr>
                        </tbody>
                     </table>
                  </div>
		  </div>
		   <div class="tab-pane fade" id="afterNoon" role="tabpanel" aria-labelledby="afterNoonTab">
		   <div class="text-center contentDetailTab">After Noon Attendance</div>
				   <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0"> 
                     <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('attendanceAfterNoon')}}</span></a></li>
						<li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
						<span class="svwRecCount">{{fnMvwGetTotalPage('attendanceAfterNoon')}}</span></a></li>
                     </ul>
					 <div>
					 </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                         <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('attendanceAfterNoon',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('attendanceAfterNoon',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('attendanceAfterNoon',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('attendanceAfterNoon',$event)"   
						class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="attendanceAfterNoon">
                     <table class="table table-sm table-bordered">
                        <thead align="center">
                           <tr>
                              <th scope="col">Period</th>
                              <th scope="col">Attendance</th>
                           </tr>
                        </thead>
                        <tbody  align="center">
                           <tr ng-repeat="X in attendanceAfterNoonShowObject">
						   <td>
							{{X.periodNumber}}
							</td>
						    <td>
                            <button id="{{X.periodNumber}}-{{$index}}-A" type="button"  ng-click="fnAttendanceButtonClick(X.periodNumber + '-' + $index+'-A')"    ng-disabled="attendanceReadOnly" class= "btn-lg" ng-class="X.attendenceButtonClass">{{X.attendance}}</button>
							</td>
							
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
		   </div>
         </div>
         <div id="Auditlogsection" ng-show="!searchShow&&auditshow">
            <div class="cohesive_topmargin">
               <div class="Auditlogsection">
                  <ul class="nav nav-tabs mb-4" id="auditTab" role="tablist">
                     <li class="nav-item">
                        <a class="nav-link active"  data-toggle="tab" href="#maker" role="tab"  aria-selected="true">Maker</a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#checker" role="tab"  aria-selected="false">Checker</a>
                     </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#status" role="tab" aria-selected="false">Status</a>
                     </li>
                  </ul>
                  <div class="tab-content" id="auditTab">
                     <div class="tab-pane fade show active" id="maker" role="tabpanel" >
                        <div class="card border-light" >
                           <div class="card-body" >
                              <div class= "form-group row">
                                 <label for="ID" class="col-3 col-form-label">ID</label>
                                 <div class=" input-group-append col-9">
                                    <input id="makerID" class="form-control" ng-model="audit.MakerID" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="date" class="col-3 col-form-label">Date</label>
                                 <div class=" input-group-append col-9">
                                    <input id="makerDtStamp" type="text" class="form-control" ng-model="audit.MakerDtStamp" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="remarks" class="col-3 col-form-label">Remarks</label>
                                 <div class=" input-group-append col-9">
                                    <input id="makerRemarks" type="text" class="form-control" ng-readonly="MakerRemarksReadonly" ng-model="audit.MakerRemarks"> 
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="tab-pane fade" id="checker" role="tabpanel" >
                        <div class="card border-light">
                           <div class="card-body" >
                              <div class= "form-group row">
                                 <label for="ID" class="col-3 col-form-label">ID</label>
                                 <div class=" input-group-append col-9">
                                    <input id="checkerID" class="form-control" ng-model="audit.CheckerID" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="date" class="col-3 col-form-label">Date</label>
                                 <div class=" input-group-append col-9">
                                    <input id="checkerDtStamp" type="text" class="form-control" ng-model="audit.CheckerDtStamp" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="remarks" class="col-3 col-form-label">Remarks</label>
                                 <div class=" input-group-append col-9">
                                    <input id="text" type="text" class="form-control" ng-readonly="CheckerRemarksReadonly" ng-model="audit.CheckerRemarks" >
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                     <div class="tab-pane fade" id="status" role="tabpanel" >
                        <div class="card border-light">
                           <div class="card-body" >
                              <div class= "form-group row">
                                 <label for="ID" class="col-3 col-form-label">Record</label>
                                 <div class=" input-group-append col-9">
                                    <input id="recordStat" type="text" class="form-control" ng-model="audit.RecordStat" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="date" class="col-3 col-form-label">Authorize</label>
                                 <div class=" input-group-append col-9">
                                    <input id="authStat" type="text" class="form-control" ng-model="audit.AuthStat" readonly>
                                 </div>
                              </div>
                              <div class= "form-group row">
                                 <label for="remarks" class="col-3 col-form-label">Version</label>
                                 <div class=" input-group-append col-9">
                                    <input id="version" type="text" class="form-control" ng-model="audit.Version" readonly>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
         <div id="searchBody" ng-controller="StudentNamesearch">
         </div>
      </div>  
      <footer class="subscreenFooter" ng-show="!searchShow">
         <nav class="nav nav-pills nav-justified">
            <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Master</a>
            <a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Details</a>
            <a id="AuditFooter" class="cohesiveFooter_navitem footer_color" href="#">Audit</a>
         </nav>
      </footer>
      <div id="snackbar"></div>
       <div id="spinner"></div>
   </body>
</html>
