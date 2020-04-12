<%-- 
    Document   : ClassExamSchedule
    Created on : Jul 17, 2019, 8:06:26 PM
    Author     : IBD Technologies
--%>

    <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Css Library Starts---->
            <link rel="stylesheet" href="/css/library/bootstrap.min.css">
            <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
            <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
            <!--Css Library Ends------>
            <!--js library Ends-->
            <script type="text/javascript" src="/js/js_library/angular.min.js"></script>
            <script type="text/javascript" src="/js/js_library/angular-route.js"></script>
            <script type="text/javascript" src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script type="text/javascript" src="/js/js_library/bootstrap.min.js"></script>
            <script type="text/javascript" src="/js/js_library/jquery-ui.min.js"></script>
            <script type="text/javascript" src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script type="text/javascript" src="/Fontawesome_new/js/all.min.js"></script>
            <script type="text/javascript" src="/Fontawesome_new/js/brands.min.js"></script>

            <!--Js Library Ends-->
            <!--Css Framework Starts--->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/TableView.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!--Css Framework Ends-->
            <!--Js Framework Starts-->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/Operation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/TableView.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <script type="text/javascript" src="/js/Utils/date_picker.js"></script>
            <!--Js Framework Ends-->
            <script type="text/javascript" src="/js/ClassExamSchedule.js"></script>
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
                        <h6 align="center">Class Exam Schedule</h6>
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
                            <label for="examType" class="col-3 col-form-label">Exam</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <select class="custom-select" id="examType" ng-disabled="examtypereadOnly" ng-model="exam">
                                        <option ng-repeat="x in ExamMaster" value="{{x}}">{{x}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                      <div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Class</label>
                                    <div class=" input-group col-9">
                                        <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>
                                    </div>
                                </div>
                    </div>
                    <div id="detailSection" ng-show="detailshow">
                        <div class="text-center contentDetailTab">Schedule Table</div>
                        <nav class="navbar navbar-light bg-light contentDetailTabNav">
                            <ul class="pagination pagination-sm   mb-0">
                                <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('examSchedule')}}</span></a></li>
                                <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                <li class="page-item">
                                    <a class="page-link page_style svwRecCount">
                                        <span class="svwRecCount">{{fnSvwGetTotalPage('examSchedule')}}</span></a>
                                </li>

                            </ul>
                            <ul class="pagination pagination-sm justify-content-end mb-0">
                                <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('examSchedule',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('examSchedule',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                <li class="page-item">
                                    <a id="addButton" ng-click="fnSvwAddRow('examSchedule',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                </li>
                                <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('examSchedule',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                            </ul>
                        </nav>
                        <div ng-show="SubjectschedulesShowObject" id="examSchedule">
                            <div class="form-group row ">
                                <label for="subject" class="col-3 col-form-label">Subject</label>
                                <div class=" input-group col-9">
                                    <select class="custom-select" id="subject" ng-disabled="subjectNamereadOnly" ng-model="SubjectschedulesRecord.subjectID">
                                        <option ng-repeat="x in subjects" value="{{x.id}}">{{x.name}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="date" class="col-3 col-form-label">Date</label>
                                <div class=" input-group col-9">
                                    <input id="examDate" class="form-control" ng-readonly="examDatereadOnly" ng-model="SubjectschedulesRecord.date">
                                    <div class="input-group-append">
                                        <button type="button" id="examDateShow" ng-disabled="examDatereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="from" class="col-3 col-form-label">Start Time</label>
                                <div class="input-group-prepend col-9">
                                    <select class="custom-select" id="startTimeHour" ng-disabled="startTimereadOnly" ng-model="SubjectschedulesRecord.startTime.hour">
                                        <option ng-repeat="x in Hour" value="{{x}}">{{x}}</option>
                                    </select>
                                    <select class="custom-select" id="startTimeMin" ng-disabled="startTimereadOnly" ng-model="SubjectschedulesRecord.startTime.min">
                                        <option ng-repeat="x in Minutes" value="{{x}}">{{x}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="from" class="col-3 col-form-label">End Time</label>
                                <div class="input-group-prepend col-9">
                                    <select class="custom-select" id="endTimeHour" ng-disabled="endTimereadOnly" ng-model="SubjectschedulesRecord.endTime.hour">
                                        <option ng-repeat="x in Hour" value="{{x}}">{{x}}</option>
                                    </select>
                                    <select id="endTimeMin" class="custom-select" ng-disabled="endTimereadOnly" ng-model="SubjectschedulesRecord.endTime.min">
                                        <option ng-repeat="x in Minutes" value="{{x}}">{{x}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="hall" class="col-3 col-form-label">Hall</label>
                                <div class=" input-group col-9">
                                    <select class="custom-select" id="examHall" ng-disabled="examHallreadOnly" ng-model="SubjectschedulesRecord.hall">
                                        <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="Auditlogsection" ng-show="auditshow">
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