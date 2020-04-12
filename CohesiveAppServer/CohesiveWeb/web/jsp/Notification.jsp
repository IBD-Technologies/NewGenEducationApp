<%-- 
   Document   : Notification
   Created on : Jul 17, 2019, 2:11:54 PM
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
            <!-- Css Library Ends---->
            <!-- Js Library Starts--->
            <script src="/js/js_library/angular.min.js"></script>
            <script src="/js/js_library/angular-route.js"></script>
            <script src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script src="/js/js_library/jquery-ui.min.js"></script>
            <script src="/js/js_library/bootstrap.min.js"></script>
            <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script src="/Fontawesome_new/js/all.min.js"></script>
            <script src="/Fontawesome_new/js/brands.min.js"></script>
            <!-- Js Library Ends--->
            <!-- Css Framework Library Starts--->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!--Css Framework Library Ends---->
            <!-- Js Framework Library Starts-->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/Operation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/date_picker.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <!-- Js Framework Library Ends-->
            <script src="/js/Notification.js"></script>
        </head>

        <body id="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">

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
                            <h6 align="center"> Notification </h6>
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
                                            <button type="button" class="btn btn-primary CohesivePrimaryButton" ng-disabled="instituteNameSearchreadOnly" ng-click="fnInstituteNameSearch()"><i class="fas fa-search"></i></button>
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
                                <label for="notificationId" class="col-3 col-form-label">Notification ID</label>
                                <div class="col-9">
                                    <div class="input-group">
                                        <input id="notificationId" type="text" ng-readonly="notificationIDreadOnly" ng-model="notificationID" class="form-control">
                                        <div class="input-group-append">
                                            <button type="button" class="btn btn-primary" ng-disabled="notificationIDSearchreadOnly" ng-click="fnNotificationSearch()"><i class="fas fa-search"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div id="detailSection" ng-show="!searchShow&&detailshow">
                            <ul class="nav nav-tabs mb-4 " id="detailSubTab" role="tablist">
                                <li class="nav-item cohesiveTabPadding">
                                    <a class="nav-link active" id="generalTab" data-toggle="tab" href="#general" role="tab" aria-selected="true">General</a>
                                </li>
                                <li class="nav-item cohesiveTabPadding">
                                    <a class="nav-link" id="frequencyTab" data-toggle="tab" href="#frequency" role="tab" aria-selected="false">Message</a>
                                </li>
                                <li class="nav-item cohesiveTabPadding">
                                    <a class="nav-link" id="testTab" data-toggle="tab" href="#test" role="tab" aria-selected="false">Test</a>
                                </li>
                            </ul>
                            <div class="tab-content" id="detailSubTab">
                                <div class="tab-pane fade show active" id="general" role="tabpanel" aria-labelledby="generalTab">

                                    <div class="form-group row">
                                        <label for="notificationTypes" class="col-3 col-form-label">Notification Type</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <select class="custom-select" id="notificationTypes" ng-disabled="notificationTypereadOnly" ng-model="notificationType">
                                                    <option ng-repeat="x in Types" value="{{x}}">{{x}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="notificationAssignee" class="col-3 col-form-label">Assignee</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="notificationAssignee" type="text" ng-readonly="assigneereadOnly" ng-model="assignee" class="form-control">

                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="assigneereadOnly" ng-click="fnGroupingSearch()"><i class="fas fa-search"></i></button>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <!--<div class="form-group row">
                                        <label for="notificationMessage" class="col-3 col-form-label">Message</label>
                                        <div class=" input-group col-9">
                                            <textarea id="notificationMessage" ng-readonly="messagereadOnly" ng-model="message" class="form-control"></textarea>
                                        </div>
                                    </div>-->
                                    <div class="form-group row">
                                        <label for="communication" class="col-3 col-form-label">Mode</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <select class="custom-select" id="communication" ng-disabled="mediaCommunicationreadOnly" ng-model="mediaCommunication">
                                                    <option ng-repeat="x in Communications" value="{{x.value}}">{{x.name}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="frequency" role="tabpanel" aria-labelledby="frequencyTab">
                                    <!--<div class="form-group row">
                        <label for="frequency" class="col-3 col-form-label">Frequency</label>
                        <div class="col-9">
                           <div class="input-group">
                               <select class="custom-select" id="frequency"  ng-model="notificationFrequency" disabled>
                                 <option ng-repeat="x in frequencies" value="{{x.value}}">{{x.name}}</option>
                              </select>
                           </div>
                        </div>
                     </div>
                    <div class="form-group row">
                        <label for="notificationMonth" class="col-3 col-form-label">Month</label>
                        <div class="col-9">
                           <div class="input-group">
                              <!--<select class="custom-select" id="notificationMonth" ng-disabled="monthreadOnly" ng-model="month">
                              <select class="custom-select" id="notificationMonth" ng-model="month" disabled>
                              <option ng-repeat="x in Months" value="{{x.value}}">{{x.name}}</option>
                              </select>
                           </div>
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="date" class="col-3 col-form-label">On</label>
                        <div class="col-5">
                           <div class="input-group">
                              <!--<select class="custom-select" id="notificationDays" ng-disabled="dayreadOnly" ng-model="day">
                              <select class="custom-select" id="notificationDays" ng-model="day" disabled> 
                                <option ng-repeat="x in Days" value="{{x.value}}">{{x.name}}</option>
                             </select>
                           </div>
                        </div>-->
                                    <div class="form-group row">
                                        <label for="notificationMessage" class="col-3 col-form-label">In English</label>
                                        <div class=" input-group col-9">
                                            <textarea id="notificationMessage" ng-readonly="messagereadOnly" ng-model="message" class="form-control"></textarea>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="otherLangMessage" class="col-3 col-form-label">In Other Language</label>
                                        <div class=" input-group col-9">
                                            <textarea id="otherLangMessage" ng-readonly="otherLangMessagereadOnly" ng-model="otherLanguageMessage" class="form-control"></textarea>
                                        </div>
                                    </div>

                                    <!--<div class="col-4">
                                        <!--<select class="custom-select"  ng-disabled="dayreadOnly" ng-model="date">-->
                                        <!--<select class="custom-select" ng-model="date" disabled>
                                            <option ng-repeat="x in DateMaster" value="{{x}}">{{x}}</option>
                                        </select>
                                    </div>-->
                                        
                                    <div class="form-group row">
                                    <label for="instantDate" class="col-3 col-form-label">Delivery Date</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input ng-readonly="instantreadOnly" id="instantDate" ng-model="instant" class="form-control">
                                            <div class="input-group-append">
                                                <button type="button" ng-disabled="instantreadOnly" id="instantDateShow" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>    
                                </div>
                                
                            

                            <div class="tab-pane fade" id="test" role="tabpanel" aria-labelledby="testTab">
                                <div class="form-group row">
                                    <label for="email" class="col-3 col-form-label">Test Email ID</label>
                                    <div class="col-9">
                                        <div class="input-group">

                                            <input id="email" type="text" ng-readonly="emailreadOnly" ng-model="email" class="form-control">

                                        </div>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <label for="mobileNo" class="col-3 col-form-label">Test Mobile No</label>
                                    <div class="col-9">
                                        <div class="input-group">

                                            <input id="mobileNo" type="text" ng-readonly="mobileNoreadOnly" ng-model="mobileNo" class="form-control">

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
                    <div id="searchBody" ng-controller="NotificationSearch">
                    </div>
                    <div id="searchBody" ng-controller="InstituteNamesearch">
                    </div>
                    <div id="searchBody" ng-controller="GroupIDSearch">
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