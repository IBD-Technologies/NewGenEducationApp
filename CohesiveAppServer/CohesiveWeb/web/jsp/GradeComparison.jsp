<%-- 
    Document   : GradeComparison
    Created on : Aug 4, 2019, 11:40:03 AM
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
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!-- Css Framework Library Ends--->
            <!-- Js Framework Library Starts--->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/ReportOperation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <!--Js Framework Library Ends--->
            <script type="text/javascript" src="/js/GradeComparison.js"></script>

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
                        <h6 align="center">Institute Exam Performance Report</h6>
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
                                        <button type="button" class="btn btn-primary" ng-disabled="instituteNamereadOnly" ng-click="fnInstituteNameSearch()"><i class="fas fa-search"></i></button>
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
                        <!--<div class="form-group row">
                            <label for="examType" class="col-3 col-form-label">Exam</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <select class="custom-select" id="examType" ng-disabled="examtypereadOnly" ng-model="exam">
                                        <option ng-repeat="x in ExamMaster" value="{{x}}">{{x}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>-->
						  <div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Standard</label>
                                    <div class=" input-group col-9">
                                        
                                        <input id="class" type="text" ng-readonly="classReadonly" ng-model="class" class="form-control">
                                        <!--<select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>-->
                                    </div>
                                </div>
                    </div>
         <div id="detailSection" ng-show="!searchShow&&detailshow">
          <div id="reportDiv" class="embed-responsive cohesive-embed-responsive embed-responsive-1by1">
                  <iframe id ="reportFrame" class="embed-responsive-item" >
                  </iframe>
             
         </div>
                    </div>
                    <div id="searchBody" ng-controller="InstituteNamesearch">
                    </div>
                </div>
     <footer id="ReportFooter" class="subscreenFooter" ng-show="!searchShow">
                    <nav class="nav nav-pills nav-justified">
                        <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Master</a>
                        <a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Report</a>
                    </nav>
                </footer>
                <div id="snackbar"></div>
                <div id="spinner"></div>
        </body>

        </html>
        
        