<%-- 
    Document   : ClassTimeTable
    Created on : Jul 17, 2019, 7:48:56 PM
    Author     : IBD Technologies
--%>

    <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!--Screen Specific Scope Starts--->
            <link rel="stylesheet" href="/css/library/bootstrap.min.css">
            <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
            <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
            <!--Screen Specific Scope Ends-->
            <!--Js Library Starts-->
            <script src="/js/js_library/angular.min.js"></script>
            <script src="/js/js_library/angular-route.js"></script>
            <script src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script src="/js/js_library/bootstrap.min.js"></script>
            <script src="/js/js_library/jquery-ui.min.js"></script>
            <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script src="/Fontawesome_new/js/all.min.js"></script>
            <script src="/Fontawesome_new/js/brands.min.js"></script>

            <!--Js Library Ends--->
            <!--Css Framework Starts-->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/TableView.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!--Css Framework Ends--->
            <!--Js Framework Starts-->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/Operation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/TableView.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <!--Js Framework Ends-->
            <script type="text/javascript" src="/js/ClassTimeTable.js"></script>
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
                        <h6 align="center"> Class Time Table</h6>
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
                      
                        
                    </div>
                    <div id="detailSection" ng-show="!searchShow&&detailshow">
                        <ul class="nav nav-tabs mb-4 " id="myTab" role="tablist">
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link active ContentTabNavlink1" id="mondayTab" data-toggle="tab" href="#monday" role="tab" aria-selected="false">Mon</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink1" id="tuesdayTab" data-toggle="tab" href="#tuesday" role="tab" aria-selected="false">Tue</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink1" id="wednesdayTab" data-toggle="tab" href="#wednesday" role="tab" aria-selected="false">Wed</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink1" id="thursdayTab" data-toggle="tab" href="#thursday" role="tab" aria-selected="true">Thu </a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink1" id="fridayTab" data-toggle="tab" href="#friday" role="tab" aria-selected="false">Fri</a>
                            </li>
                        </ul>
                        <div class="tab-content" id="myTabContent">
                            <div class="tab-pane fade show active" id="monday" role="tabpanel" aria-labelledby="mondayTab">
                                <div class="text-center contentDetailTab">Monday Time Table</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('mondayTable')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('mondayTable')}}</span></a>
                                        </li>
                                    </ul>
                                    <div>
                                    </div>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('mondayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('mondayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('mondayTable',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('mondayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="MondayShowObject" id="mondayTable">
                                    <div class="form-group row">
                                        <label for="periodNumber" class="col-3 col-form-label">Period Number</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <select class="custom-select" id="periodNumber1" ng-disabled="periodNumberReadonly" ng-model="MondayRecord.periodNumber">
                                                    <option ng-repeat="x in period" value="{{x}}">{{x}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row ">
                                        <label for="subject" class="col-3 col-form-label">Subject</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="subject1" ng-disabled="subjectNamereadOnly" ng-model="MondayRecord.subjectID">
                                                    <option ng-repeat="x in subjects" value="{{x.id}}">{{x.name}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="teacherName" class="col-3 col-form-label">Staff Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="staffName" type="text" ng-readonly="teacherNameInputReadonly" ng-model="MondayRecord.teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNameReadonly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">Start Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="MondayRecord.startTime.hour" ng-readonly="startTimereadOnly" class="form-control">
                                                <input type="text" ng-model="MondayRecord.startTime.min" ng-readonly="startTimereadOnly" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">End Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="MondayRecord.endTime.hour" ng-readonly="endTimereadOnly" class="form-control">
                                                <input type="text" ng-model="MondayRecord.endTime.min" ng-readonly="endTimereadOnly" class="form-control">
                                            </div>

                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                            <div class="tab-pane fade " id="tuesday" role="tabpanel" aria-labelledby="tuesdayTab">
                                <div class="text-center contentDetailTab">Tuesday Time Table</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('tuesdayTable')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('tuesdayTable')}}</span></a>
                                        </li>
                                    </ul>
                                    <div>
                                    </div>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('tuesdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('tuesdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('tuesdayTable',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('tuesdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="TuesdayShowObject" id="tuesdayTable">
                                    <div class="form-group row">
                                        <label for="periodNumber" class="col-3 col-form-label">Period Number</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="periodNumber2" ng-disabled="periodNumberReadonly" ng-model="TuesdayRecord.periodNumber">
                                                    <option ng-repeat="x in period" value="{{x}}">{{x}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row ">
                                        <label for="subject" class="col-3 col-form-label">Subject</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="subject2" ng-disabled="subjectNamereadOnly" ng-model="TuesdayRecord.subjectID">
                                                    <option ng-repeat="x in subjects" value="{{x.id}}">{{x.name}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                     <div class="form-group row">
                                        <label for="teacherName" class="col-3 col-form-label">Staff Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="staffName" type="text" ng-readonly="teacherNameInputReadonly" ng-model="TuesdayRecord.teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNameReadonly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">Start Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="TuesdayRecord.startTime.hour" ng-readonly="startTimereadOnly" class="form-control">
                                                <input type="text" ng-model="TuesdayRecord.startTime.min" ng-readonly="startTimereadOnly" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">End Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="TuesdayRecord.endTime.hour" ng-readonly="endTimereadOnly" class="form-control">
                                                <input type="text" ng-model="TuesdayRecord.endTime.min" ng-readonly="endTimereadOnly" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                   
                                </div>
                            </div>
                            <div class="tab-pane fade" id="wednesday" role="tabpanel" aria-labelledby="wednesdayTab">
                                <div class="text-center contentDetailTab">Wednesday Time Table</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('wednesdayTable')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('wednesdayTable')}}</span></a>
                                        </li>
                                    </ul>
                                    <div>
                                    </div>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('wednesdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('wednesdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('wednesdayTable',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('wednesdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="WednesdayShowObject" id="wednesdayTable">
                                    <div class="form-group row">
                                        <label for="periodNumber" class="col-3 col-form-label">Period Number</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="periodNumber3" ng-disabled="periodNumberReadonly" ng-model="WednesdayRecord.periodNumber">
                                                    <option ng-repeat="x in period" value="{{x}}">{{x}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row ">
                                        <label for="subject" class="col-3 col-form-label">Subject</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="subject3" ng-disabled="subjectNamereadOnly" ng-model="WednesdayRecord.subjectID">
                                                    <option ng-repeat="x in subjects" value="{{x.id}}">{{x.name}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="teacherName" class="col-3 col-form-label">Staff Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="staffName" type="text" ng-readonly="teacherNameInputReadonly" ng-model="WednesdayRecord.teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNameReadonly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">Start Time</label>
                                        <div class="col-9">
                                            <div class="input-group">

                                                <input type="text" ng-model="WednesdayRecord.startTime.hour" ng-readonly="startTimereadOnly" class="form-control">
                                                <input type="text" ng-model="WednesdayRecord.startTime.min" ng-readonly="startTimereadOnly" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">End Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="WednesdayRecord.endTime.hour" ng-readonly="endTimereadOnly" class="form-control">
                                                <input type="text" ng-model="WednesdayRecord.endTime.min" ng-readonly="endTimereadOnly" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                            <div class="tab-pane fade " id="thursday" role="tabpanel" aria-labelledby="thursdayTab">
                                <div class="text-center contentDetailTab">Thursday Time Table</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('thursdayTable')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('thursdayTable')}}</span></a>
                                        </li>
                                    </ul>
                                    <div>
                                    </div>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('thursdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('thursdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('thursdayTable',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('thursdayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="ThursdayShowObject" id="thursdayTable">
                                    <div class="form-group row">
                                        <label for="periodNumber" class="col-3 col-form-label">Period Number</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="periodNumber4" ng-disabled="periodNumberReadonly" ng-model="ThursdayRecord.periodNumber">
                                                    <option ng-repeat="x in period" value="{{x}}">{{x}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row ">
                                        <label for="subject" class="col-3 col-form-label">Subject</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="subject4" ng-disabled="subjectNamereadOnly" ng-model="ThursdayRecord.subjectID">
                                                    <option ng-repeat="x in subjects" value="{{x.id}}">{{x.name}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                                                        <div class="form-group row">
                                        <label for="teacherName" class="col-3 col-form-label">Staff Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="staffName" type="text" ng-readonly="teacherNameInputReadonly" ng-model="ThursdayRecord.teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNameReadonly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">Start Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="ThursdayRecord.startTime.hour" ng-readonly="startTimereadOnly" class="form-control">
                                                <input type="text" ng-model="ThursdayRecord.startTime.min" ng-readonly="startTimereadOnly" class="form-control">

                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">End Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="ThursdayRecord.endTime.hour" ng-readonly="endTimereadOnly" class="form-control">
                                                <input type="text" ng-model="ThursdayRecord.endTime.min" ng-readonly="endTimereadOnly" class="form-control">
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="tab-pane fade" id="friday" role="tabpanel" aria-labelledby="fridayTab">
                                <div class="text-center contentDetailTab">Friday Time Table</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('fridayTable')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('fridayTable')}}</span></a>
                                        </li>
                                    </ul>
                                    <div>
                                    </div>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('fridayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('fridayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('fridayTable',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('fridayTable',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="FridayShowObject" id="fridayTable">
                                    <div class="form-group row">
                                        <label for="periodNumber" class="col-3 col-form-label">Period Number</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <select class="custom-select" id="periodNumber5" ng-disabled="periodNumberReadonly" ng-model="FridayRecord.periodNumber">
                                                    <option ng-repeat="x in period" value="{{x}}">{{x}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row ">
                                        <label for="subject" class="col-3 col-form-label">Subject</label>
                                        <div class="col-9">
                                            <div class=" input-group">
                                                <select class="custom-select" id="subject5" ng-disabled="subjectNamereadOnly" ng-model="FridayRecord.subjectID">
                                                    <option ng-repeat="x in subjects" value="{{x.id}}">{{x.name}}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                                                        <div class="form-group row">
                                        <label for="teacherName" class="col-3 col-form-label">Staff Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="teacherName" type="text" ng-readonly="teacherNameInputReadonly" ng-model="FridayRecord.teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNameReadonly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">Start Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="FridayRecord.startTime.hour" ng-readonly="startTimereadOnly" class="form-control">
                                                <input type="text" ng-model="FridayRecord.startTime.min" ng-readonly="startTimereadOnly" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="from" class="col-3 col-form-label">End Time</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input type="text" ng-model="FridayRecord.endTime.hour" ng-readonly="endTimereadOnly" class="form-control">
                                                <input type="text" ng-model="FridayRecord.endTime.min" ng-readonly="endTimereadOnly" class="form-control">
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