<%-- 
    Document   : StudentAssignment
    Created on : Jul 18, 2019, 7:46:40 PM
    Author     : IBD Technologies
--%>

    <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!--Css Library Starts--->
            <link rel="stylesheet" href="/css/library/bootstrap.min.css">
            <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
            <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
            <!-- Css Library Ends---->
            <!--Js Library Starts--->
            <script src="/js/js_library/angular.min.js"></script>
            <script src="/js/js_library/angular-route.js"></script>
            <script src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script src="/js/js_library/jquery-ui.min.js"></script>
            <script src="/js/js_library/bootstrap.min.js"></script>
            <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script src="/Fontawesome_new/js/all.min.js"></script>
            <script src="/Fontawesome_new/js/brands.min.js"></script>
            <!--Js Library Ends-->
            <!-- Css Framework Starts-->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!-- Css Framework Ends-->
            <!-- Js Framework Starts--->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
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


    </c:forEach> --%> 
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <script src="/js/Utils/date_picker.js"></script>
            <!--Js Framework Ends-->
            <script type="text/javascript" src="/js/StudentAssignment.js"></script>
        </head>

        <body id="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
           <%
         response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
         response.setHeader("Pragma","no-cache"); //Http 1.0
         response.setHeader("Expires", "-1"); //Proxies
         response.setHeader("X-XSS-Protection","1;mode=block");
         response.setHeader("X-Frame-Options","SAMEORIGIN");
         response.setHeader("Content-Security-Policy","default-src 'self';img-src 'self' data:;script-src  'self';style-src 'unsafe-inline'  'self';base-uri 'none';form-action 'none';frame-src 'self' https://www.youtube.com/ https://m.youtube.com/ https://youtu.be/;frame-ancestors 'self'");
         %>
                <header id="subscreenHeader" class="subscreenHeader mb-3">
                    <div id="subscreenHeading" class="ssHeading" ng-show="!searchShow">
                        <h6 align="center">Student Assignment</h6>
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
                        <div class="form-group row">
                            <label for="studID" class="col-3 col-form-label">ID</label>
                            <div class=" input-group-append col-9">
                                <input id="studID" type="text" ng-readonly="studentIDreadOnly" ng-model="studentID" class="form-control">
                            </div>
                        </div>
						   <div class="form-group row">
                            <label for="assignID" class="col-3 col-form-label">Assignment ID</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <input id="assignID" type="text" ng-readonly="assignmentIDreadOnly" ng-model="assignmentID" class="form-control">
                                    <div class="input-group-append">
                                        <button type="button" class="btn btn-primary" ng-disabled="assignmentIDreadOnly" ng-click="fnClassAssignmentSearch()"><i class="fas fa-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div id="detailSection" ng-show="!searchShow&&detailshow">
                        <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active " data-toggle="tab" href="#General" role="tab" aria-controls="General" aria-selected="true">General</a>
                            </li>
                            
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#content" role="tab" aria-controls="content" aria-selected="false">Content</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#other" role="tab" aria-controls="other" aria-selected="false">Other</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane fade show active " id="General" role="tabpanel">
                               
                                <div class="form-group row">
                                    <label for="assignmentType" class="col-4 col-form-label">Assignment Type</label>
                                    <div class="col-8">
                                        <div class="input-group">
                                            <select class="custom-select" id="assignmentType" ng-disabled="assignmentTypereadOnly" ng-model="assignmentType">
                                                <option ng-repeat="x in AssignmentType" value="{{x.value}}">{{x.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
								 <div class="form-group row">
                            <label for="assignDescription" class="col-4 col-form-label">Description</label>
                            <div class="col-8">
                                <div class="input-group">
                                    <textarea id="assignDescription" type="text" ng-readonly="assignmentDescriptionreadOnly" ng-model="assignmentDescription" class="form-control"></textarea>
                                </div>
                            </div>
                              </div>
							   <div class="form-group row">
                                    <label for="subject" class="col-4 col-form-label">Subject</label>
                                    <div class="col-8">
                                        <div class="input-group">
                                            <select class="custom-select" id="subject" ng-disabled="subjectNamereadOnly" ng-model="subjectID">
                                                <option ng-repeat="x in subjects" value="{{x.id}}">{{x.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
								</div>
                            
                            
                            
                            <div class="tab-pane fade " id="content" role="tabpanel">
                                
                            
                                <div class="form-group row">
                               <label for="InstituteAssignmentURL" class="col-3 col-form-label">URL</label>
                                <div class=" input-group col-9">
                                    <input id="InstituteAssignmentURL" name="InstituteAssignmentFile" type="text" ng-disabled="urlreadOnly" ng-model="url" class="form-control">
                                </div>
                                </div>
                               
                                
                            <div class="form-group row">
                               <!-- <label for="Institute" class="col-3"> </label> -->
                                <div class="col-12">
                                    
                                        <div id="contentDiv" class="embed-responsive embed-responsive-16by9 border">
                                                  <iframe id ="Institute" src="" class="embed-responsive-item" >
                                                  </iframe>
                                        </div>   
                                    
                                    
                                </div>
                            </div>    
                                
                                
                                
                                
                        <!----   <form id="InstituteAssignmentUrlUpld" action="https://cohesive.ibdtechnologies.com/CohesiveGateway/image/InstituteAssignment" method="post" enctype="multipart/form-data">
                            <div class="form-group row">
                                <label for="InstituteAssignmentFile" class="col-3 col-form-label">URL</label>
                                <div class=" input-group col-9">
                                <input id="InstituteAssignmentUrl" name="InstituteAssignmentUrl" type="text" ng-readonly="urlreadOnly" ng-model="url" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="video" class="col-3"> </label>
                                <div class="col-9">
                                   <div  class="embed-responsive embed-responsive-16by9">
                                   <iframe id="video" class="embed-responsive-item" src="" allowfullscreen></iframe>
                                  </div>
                                </div>
                            </div>
                        </form>---->    
                            </div>
                            
                            
                            
                            
								 <div class="tab-pane fade" id="other" role="tabpanel">
							<div class="form-group row">
                            <label for="assignmentDate" class="col-4 col-form-label">Assignment Date</label>
                            <div class="col-8">
                                <div class="input-group">
                                    <input id="assignmentDueDate" ng-readonly="dueDatereadOnly" ng-model="dueDate" class="form-control">
                                    <div class="input-group-append">
                                        <button type="button" id="assignmentDueDateShow" ng-disabled="dueDatereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                    </div>
                                </div>
                            </div>
                              </div>
                                <div class="form-group row">
                                    <label for="assignmentComments" class="col-4 col-form-label">Teacher Comment</label>
                                    <div class="col-8">
                                        <div class="input-group">
                                            <textarea id="assignmentComments" type="text" ng-readonly="teacherCommentreadOnly" ng-model="teacherComments" class="form-control"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <!--<div class="form-group row">
                                    <label for="assignmentDate" class="col-4 col-form-label">Completed Date</label>
                                    <div class="col-8">
                                        <div class="input-group">
                                            <input id="assignmentCompleteDate" type="text" ng-readonly="completeDatereadOnly" ng-model="completedDate" class="form-control">
                                            <div class="input-group-append">
                                                <button type="button" id="assignmentCompleteDateShow" ng-disabled="completeDatereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="parentComments" class="col-4 col-form-label"> Parent Comment</label>
                                    <div class="col-8">
                                        <div class="input-group">
                                            <textarea id="parentComments" type="text" ng-readonly="parentCommentreadOnly" ng-model="parentComment" class="form-control"></textarea>
                                        </div>
                                    </div>
                                </div>-->
                            </div>
							</div>
                                 <!--- <form id="InstituteAssignmentUpld" action="https://cohesive.ibdtechnologies.com/CohesiveGateway/image/StudentAssignment" method="post" enctype="multipart/form-data">
                            <div class="form-group row">
                                <label for="StudentAssignmentFile" class="col-3 col-form-label">Upload Assignment</label>
                                <div class=" input-group col-9">
                                    <input id="StudentAssignmentFile" name="StudentAssignmentFile" type="file" ng-disabled="contentTypereadOnly" ng-model="contentType" class="form-control-plaintext">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="studAssignment" class="col-3"> </label>
                                <div class="col-9">
                                    <div class="imgcontainer text-center">
                                        <img id="studAssignment" src="" class="img-fluid img-thumbnail" alt="Upload Assignment">
                                    </div>
                                </div>
                            </div>
                        </form>    
                           <form id="StudAssignmentUrlUpld" action="https://cohesive.ibdtechnologies.com/CohesiveGateway/image/StudentAssignment" method="post" enctype="multipart/form-data">
                            <div class="form-group row">
                                <label for="StudAssignmentFile" class="col-3 col-form-label">URL</label>
                                <div class=" input-group col-9">
                                <input id="StudAssignmentUrl" name="StudAssignmentUrl" type="text" ng-readonly="urlreadOnly" ng-model="url" class="form-control">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="video" class="col-3"> </label>
                                <div class="col-9">
                                   <div  class="embed-responsive embed-responsive-16by9">
                                   <iframe id="video" class="embed-responsive-item" src="" allowfullscreen></iframe>
                                  </div>
                                </div>
                            </div>
                        </form>  ----->  
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
                                                    <label for="date" class="col-3 col-form-label">Authorise</label>
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
                    <div id="searchBody" ng-controller="ClassAssignmentsearch">
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