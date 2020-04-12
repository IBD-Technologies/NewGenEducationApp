<%-- 
   Document   : ClassLevelConfiguration
   Created on : Jul 17, 2019, 11:31:11 AM
   Author     : IBD Technologies
   --%>
  <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
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
            <!--Css Library Ends----->
            <!-- Js Library Starts----->
            <script src="/js/js_library/angular.min.js"></script>
            <script src="/js/js_library/angular-route.js"></script>
            <script src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script src="/js/js_library/jquery-ui.min.js"></script>
            <script src="/js/js_library/bootstrap.min.js"></script>
            <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script src="/Fontawesome_new/js/all.min.js"></script>
            <script src="/Fontawesome_new/js/brands.min.js"></script>
            <!-- Js Library Ends----->
            <!-- Css Frame Work Starts----->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/TableView.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!-- Css Framework Ends------->
            <!--Js Framework Starts---------->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/Operation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/TableView.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <!-- Js Framework Ends------>
            <script type="text/javascript" src="/js/ClassLevelConfiguration.js"></script>
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
                        <h6 align="center">Class Configuration</h6>
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
                            <label for="iName" class="col-3 col-form-label">Institute Name</label>
                            <div class="col-9">
                                <div class=" input-group">
                                    <input id="iName" type="text" ng-readonly="instituteNamereadOnly" ng-model="instituteName" class="form-control">
                                    <div class="input-group-append">
                                        <button type="button" class="btn btn-primary CohesivePrimaryButton" ng-disabled="instituteSearchreadOnly" ng-click="fnInstituteNameSearch()"><i class="fas fa-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="iID" class="col-3 col-form-label">Institute ID</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <input id="iID" type="text" ng-readonly="instituteIDreadOnly" ng-model="instituteID" class="form-control">
                                </div>
                            </div>
                        </div>
                         <div class="form-group row">
                            <label for="ClassID" class="col-3 col-form-label">Class</label>
                            <div class="col-9">
                                <div class="input-group">
                                        <input id ="ClassID" type="text" ng-readonly="classReadonly" class="form-control" ng-model="class">
                                               
                                </div>
                            </div>
                        </div>
                        
                    </div>



                    <div id="detailSection" ng-show="!searchShow&&detailshow">
                        <ul class="nav nav-tabs mb-4 " id="myTab" role="tablist">
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link active" id="standardTab" data-toggle="tab" href="#standard" role="tab" aria-selected="true">General</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link" id="periodTab" data-toggle="tab" href="#period" role="tab" aria-selected="false">Period</a>
                            </li>
                        </ul>
                        <div class="tab-content" id="myTabContent">
                            <div class="tab-pane fade show active" id="standard" role="tabpanel" aria-labelledby="standardTab">
                                <!--<div class="text-center contentDetailTab">Class Configuration</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('standardMaster')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnMvwGetTotalPage('standardMaster')}}</span></a>
                                        </li>
                                    </ul>
                                    <div>
                                    </div>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('standardMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('standardMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnMvwAddRow('standardMaster',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('standardMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div class="table-responsive" id="standardMaster">
                                    <table class="table  cohesive_table table-sm  table-bordered ">
                                        <thead align="center">
                                            <tr>
                                                <th scope="col"></th>
                                                <th scope="col">Class</th>
                                                <th scope="col">Teacher ID</th>
                                            </tr>
                                        </thead>
                                        <tbody id="CESbody" align="center">
                                            <tr ng-repeat="X in standardMasterShowObject">
                                                <td>
                                                    <input type="checkbox" ng-model="X.checkBox">
                                                </td>
                                                <td>
                                                    <input type="text" ng-readonly="classReadonly" class="form-control-plaintext" ng-model="X.class">
                                                </td>
                                                <td>
                                                    <input type="text" ng-readonly="teacherIDreadOnly" class="form-control-plaintext" ng-model="X.teacherID">
                                                </td>
                                            </tr>
                                    </table>
                                </div>
                                 -->
                          <div class="form-group row">
                            <label for="staffName" class="col-3 col-form-label">Teacher Name</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <input id="staffName" type="text" ng-readonly="teacherNamereadOnly" ng-model="teacherName" class="form-control">
                                    <div class="input-group-append">
                                        <button type="button" class="btn btn-primary" ng-disabled="teacherNameSearchreadOnly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="teacherID" class="col-3 col-form-label">Teacher ID</label>
                            <div class=" input-group-append col-9">
                                <input id="teacherID" type="text" ng-readonly="teacherIDreadOnly" ng-model="teacherID" class="form-control" readonly>
                            </div>
                        </div>
                                 
                        <div class="form-group row">
               <label for="attendance" class="col-3 col-form-label">Attendance</label>
               <div class="col-9">
                  <div class="input-group">
                     <select class="custom-select" id="attendance" ng-disabled ="attendancereadOnly" ng-model="attendance">
                        <option ng-repeat="x in AttendanceType"  value="{{x.value}}">{{x.name}}</option>
                     </select>
                  </div>
               </div>
            </div>         
 
                            </div>
                               
                            <div class="tab-pane fade" id="period" role="tabpanel" aria-labelledby="periodTab">
                                <div class="text-center contentDetailTab">Period Configuration</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('periodTimings')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('periodTimings')}}</span></a>
                                        </li>
                                    </ul>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('periodTimings',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('periodTimings',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('periodTimings',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('periodTimings',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="periodTimingsShow" id="periodTimings">
                                   <!-- <div class="form-group row">
                                        <label for="CLCstandard" class="col-3 col-form-label">Class</label>
                                        <div class="col-9">
                                            <input id="CLCstandard" type="text" ng-readonly="classReadonly" ng-model="periodTimingsRecord.class" class="form-control">
                                        </div>
                                    </div> -->
                                    <div class="form-group row">
                                        <label for="CLCperiodNumber" class="col-3 col-form-label">Period Number</label>
                                        <div class="col-9">
                                            <input id="CLCperiodNumber" type="text" ng-readonly="periodNumberreadOnly" ng-model="periodTimingsRecord.periodNumber" class="form-control">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">Start Time</label>
                                        <div class="input-group-prepend col-9">
                                            <select class="custom-select" id="startTimeHour" ng-disabled="startTimereadOnly" ng-model="periodTimingsRecord.startTime.hour">
                                                <option ng-repeat="x in Hour" value="{{x}}">{{x}}</option>
                                            </select>
                                            <select id="startTimeMin" class="custom-select" ng-disabled="startTimereadOnly" ng-model="periodTimingsRecord.startTime.min">
                                                <option ng-repeat="x in Minutes" value="{{x}}">{{x}}</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">End Time</label>
                                        <div class="input-group-prepend col-9">
                                            <select id="endTimeHour" class="custom-select" ng-disabled="endTimereadOnly" ng-model="periodTimingsRecord.endTime.hour">
                                                <option ng-repeat="x in Hour" value="{{x}}">{{x}}</option>
                                            </select>
                                            <select class="custom-select" id="endTimeMin" ng-disabled="endTimereadOnly" ng-model="periodTimingsRecord.endTime.min">
                                                <option  ng-repeat="x in Minutes" value="{{x}}">{{x}}</option>
                                            </select>
                                        </div>
                                    </div>
                                   
                                     <div class="form-group row">
               <label for="noon" class="col-3 col-form-label">Noon</label>
               <div class="col-9">
                  <div class="input-group">
                     <select class="custom-select" id="noon" ng-disabled ="noonreadOnly" ng-model="periodTimingsRecord.noon">
                        <option ng-repeat="x in AttendanceNoonType"  value="{{x.value}}">{{x.name}}</option>
                     </select>
                  </div>
               </div>
            </div>
                                </div>
                            </div>
                        </div>
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
                    <div id="searchBody" ng-controller="InstituteNamesearch">
                    </div>
                    <div id="searchBody" ng-controller="TeacherNameSerach">
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