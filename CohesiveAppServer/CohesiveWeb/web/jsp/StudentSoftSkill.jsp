<%-- 
    Document   : StudentSoftSkill
    Created on : Jul 18, 2019, 7:57:11 PM
    Author     : IBD Technologies
--%>

    <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
	  <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!--Css Library Starts---->
            <link rel="stylesheet" href="/css/library/bootstrap.min.css">
            <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
            <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
            <!--Css Library Ends---->
            <!-- Js Library Starts-->
            <script src="/js/js_library/angular.min.js"></script>
            <script src="/js/js_library/angular-route.js"></script>
            <script src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script src="/js/js_library/jquery-ui.min.js"></script>
            <script src="/js/js_library/bootstrap.min.js"></script>
            <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script src="/Fontawesome_new/js/all.min.js"></script>
            <script src="/Fontawesome_new/js/brands.min.js"></script>
            <!--Js Library Ends--->
            <!-- Css Framework Starts-->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/TableView.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">

            <!--Css Framework Ends-->
            <!-- Js Framework Starts-->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <!--<script type="text/javascript" src="/js/Utils/StudentView.js"></script>-->
            <c:forEach items="${cookie}" var="currentCookie">  
                          <c:if test="${currentCookie.key=='userType'}">
                <c:if test="${currentCookie.value.value=='P'}">
                    <script type="text/javascript" src="/js/Utils/SignOperation.js"></script>
                </c:if>
                     <c:if test="${currentCookie.value.value=='T'}">
                    <script type="text/javascript" src="/js/Utils/StudentView.js"></script>
                </c:if>
                <c:if test="${currentCookie.value.value=='A'}">
                    <script type="text/javascript" src="/js/Utils/StudentView.js"></script>
                </c:if>
            </c:if> 


    </c:forEach>   
            
            
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/TableView.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <!-- Js Framework Ends---->
            <script type="text/javascript" src="/js/StudentSoftSkill.js"></script>
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
                        <h6 align="center">Soft Skill Assessment</h6>
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
                            <label for="studentName" class="col-3 col-form-label">Name</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <input id="studentName" type="text" ng-readonly="studentNamereadOnly" ng-model="studentName" class="form-control">
                                    <div class="input-group-append">
                                        <button type="button" class="btn btn-primary" ng-disabled="studentNameSeachreadOnly" ng-click="fnStudentSearch()"><i class="fas fa-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="studentID" class="col-3 col-form-label">ID</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <input id="studentName" type="text" ng-readonly="studentIDReadOnly" ng-model="studentID" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="examType" class="col-3 col-form-label">Exam</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <select class="custom-select" id="examType" ng-disabled="examtypereadOnly" ng-model="exam">
                                        <option ng-repeat="x in ExamMaster" value="{{x}}">{{x}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                      
                        
                        
                    </div>
                    <div id="detailSection" ng-show="!searchShow&&detailshow">
                        <!--<ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" data-toggle="tab" href="#markEntry" role="tab" aria-controls="mark-Entry" aria-selected="true">Marks</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#rankTab" role="tab" aria-controls="ranktab" aria-selected="false">Rank</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#gradeDescription" role="tab" aria-controls="grade-Description" aria-selected="false">Grade Description</a>
                            </li>
                        </ul>-->
                        <!--<div class="tab-content" id="myTab">-->
                           <!-- <div class="tab-pane fade show active" id="markEntry" role="tabpanel" aria-labelledby="mark-Entry">-->
                                <div class="text-center contentDetailTab">Student Soft Skill </div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('studentSoftSkill')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnMvwGetTotalPage('studentSoftSkill')}}</span></a>
                                        </li>
                                    </ul>
                                    <div>
                                    </div>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('studentSoftSkill',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('studentSoftSkill',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnMvwAddRow('studentSoftSkill',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('studentSoftSkill',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div class="table-responsive" id="studentSoftSkill">
                                    <table class="table  cohesive_table table-sm  table-bordered ">
                                        <thead align="center">
                                            <tr>
                                                <th scope="col"></th>
                                                <th scope="col">Skill</th>
                                                <th scope="col">Category</th>
                                                <th scope="col">Teacher Feedback</th>
                                            </tr>
                                        </thead>
                                        <tbody id="TTbody" align="center">
                                            <tr ng-repeat="X in StudentSoftSkillShowObject">
                                                <td>
                                                    <input type="checkbox" ng-model="X.checkBox">
                                                </td>
                                                <th scope="col">{{X.skillName}}</th>
                                                <td ng-model="x.category">
                                                    <input type="text" class="form-control-plaintext" ng-model="X.category" ng-readonly="categoryReadOnly">
                                                </td>
                            
                                                <td>
                                                    <input class="form-control-plaintext" ng-model="X.teacherFeedback" ng-readonly="teacherFeedbackReadOnly">
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                 <!--</div>-->
                                <!--<div class="tab-pane fade" id="rankTab" role="tabpanel" aria-labelledby="rankTab">

                                    <div class="form-group row">
                                        <label for="totalMark" class="col-3 col-form-label">Total Mark</label>
                                        <div class=" input-group-append col-9">
                                            <input id="totalMark" type="text" ng-readonly="totalReadOnly" ng-model="total" class="form-control">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="ClassRank" class="col-3 col-form-label">Rank</label>
                                        <div class=" input-group-append col-9">
                                            <input id="ClassRank" type="text" ng-readonly="rankReadOnly" ng-model="rank" class="form-control">
                                        </div>
                                    </div>

                                </div>-->

                                <!--<div class="tab-pane fade" id="gradeDescription" role="tabpanel" aria-labelledby="grade-Description">
                                    <div class="table-responsive">
                                        <table class="table table-bordered  table-hover">
                                            <thead align="center">
                                                <tr>
                                                    <th scope="col">Grade</th>
                                                    <th scope="col">Mark Range</th>
                                                </tr>
                                            </thead>
                                            <tbody id="TTbody" align="center">
                                                <tr ng-repeat="X in gradeDescription">
                                                    <td ng-readonly="gradeReadOnly">{{X.grade}}</td>
                                                    <td ng-readonly="">{{X.description}}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>-->
                            <!--</div>-->
                        </div>
                    
                    <div id="Auditlogsection" ng-show="!searchShow&&auditshow">
                        <div class="cohesive_topmargin">
                            <div class="Auditlogsection">
                                <ul class="nav nav-tabs mb-4" id="auditTab" role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#maker" role="tab" aria-selected="true">Maker</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#checker" role="tab" aria-selected="false">Checker</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-toggle="tab" href="#status" role="tab" aria-selected="false">Status</a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="auditTab">
                                    <div class="tab-pane fade show active" id="maker" role="tabpanel">
                                        <div class="card border-light">
                                            <div class="card-body">
                                                <div class="form-group row">
                                                    <label for="ID" class="col-3 col-form-label">ID</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="makerID" class="form-control" ng-model="audit.MakerID" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="date" class="col-3 col-form-label">Date</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="makerDtStamp" type="text" class="form-control" ng-model="audit.MakerDtStamp" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="remarks" class="col-3 col-form-label">Remarks</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="makerRemarks" type="text" class="form-control" ng-readonly="MakerRemarksReadonly" ng-model="audit.MakerRemarks">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="checker" role="tabpanel">
                                        <div class="card border-light">
                                            <div class="card-body">
                                                <div class="form-group row">
                                                    <label for="ID" class="col-3 col-form-label">ID</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="checkerID" class="form-control" ng-model="audit.CheckerID" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="date" class="col-3 col-form-label">Date</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="checkerDtStamp" type="text" class="form-control" ng-model="audit.CheckerDtStamp" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="remarks" class="col-3 col-form-label">Remarks</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="text" type="text" class="form-control" ng-readonly="CheckerRemarksReadonly" ng-model="audit.CheckerRemarks">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="status" role="tabpanel">
                                        <div class="card border-light">
                                            <div class="card-body">
                                                <div class="form-group row">
                                                    <label for="ID" class="col-3 col-form-label">Record</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="recordStat" type="text" class="form-control" ng-model="audit.RecordStat" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label for="date" class="col-3 col-form-label">Authorize</label>
                                                    <div class=" input-group-append col-9">
                                                        <input id="authStat" type="text" class="form-control" ng-model="audit.AuthStat" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
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