<%-- 
    Document   : UserProfile
    Created on : Jul 18, 2019, 8:23:49 PM
    Author     : IBD Technologies
--%>

    <%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!--Css Library Starts-->
            <link rel="stylesheet" href="/css/library/bootstrap.min.css">
            <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
            <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
            <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
            <!--Css Library Ends-->
            <!--Js Library Starts-->
            <script src="/js/js_library/angular.min.js"></script>
            <script src="/js/js_library/angular-route.js"></script>
            <script src="/js/js_library/jquery-3.3.1.min.js"></script>
            <script src="/js/js_library/bootstrap.min.js"></script>
            <script src="/js/js_library/jquery-ui.min.js"></script>
            <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
            <script src="/Fontawesome_new/js/all.min.js"></script>
            <script src="/Fontawesome_new/js/brands.min.js"></script>
            <!--Js Library Ends-->
            <!--Css Framework Starts-->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/TableView.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">
            <!--Css Framework Ends-->
            <!--js Framework Starts-->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/Operation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/TableView.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <!--Js Framework Ends-->
            <script src="/js/UserProfile.js"></script>

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
                        <h6 align="center">User Profile</h6>
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
                            <label for="uName" class="col-3 col-form-label">User Name</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <input id="uName" type="text" ng-readonly="userNamereadOnly" ng-model="userName" class="form-control">
                                    <div class="input-group-append">
                                        <button type="button" ng-disabled="userNameSearchreadOnly" class="btn btn-primary" ng-click="fnUserSearch()"><i class="fas fa-search"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="uID" class="col-3 col-form-label">ID</label>
                            <div class=" input-group col-9">
                                <input id="uID" type="text" ng-readonly="userIDreadOnly" ng-model="userID" class="form-control">
                            </div>
                        </div>
                              <!--          <div class="form-group row">
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
                        </div>  -->
                    </div> 
                    <div id="detailSection" ng-show="!searchShow&&detailshow">
                        <ul class="nav nav-tabs mb-4 " id="myTab" role="tablist">
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link active ContentTabNavlink" id="generalTab" data-toggle="tab" href="#general" role="tab" aria-selected="true">General</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink" id="parentTab" data-toggle="tab" href="#parent" role="tab" aria-selected="false">Parent</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink" id="classTab" data-toggle="tab" href="#ClassMenu" role="tab" aria-selected="false">Class</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink" id="teacherTab" data-toggle="tab" href="#teacher" role="tab" aria-selected="false">Teacher</a>
                            </li>
                            <li class="nav-item cohesiveTabPadding">
                                <a class="nav-link ContentTabNavlink" id="instituteTab" data-toggle="tab" href="#institute" role="tab" aria-selected="false">Institute</a>
                            </li>
                        </ul>
                        <div class="tab-content" id="myTabContent">
                            <!--General Tab Starts-->
                            <div class="tab-pane fade show active" id="general" role="tabpanel" aria-labelledby="generalTab">
                                <div class="form-group row">
                                    <label for="userPassword" class="col-3 col-form-label">Password</label>
                                    <div class=" input-group col-9">
                                        <input id="userPassword" type="password" ng-readonly="passwordreadOnly" ng-model="password" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="PwdExpiry" class="col-3 col-form-label">Expiry Date</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input id="PwdExpiry" type="text" ng-readonly="expiryDatereadOnly" ng-model="expiryDate" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                 <div class="form-group row">
                            <label for="userType" class="col-3 col-form-label">User Type & Home Institute</label>
                            <div class="col-4">
                                <div class="input-group">
                                    <select class="custom-select"  ng-disabled="usertypereadOnly" ng-model="userType">
                                        <option ng-repeat="x in UserTypeMaster" value="{{x.value}}">{{x.name}}</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="col-5">
               <div class=" input-group">
                  <input id="iName" type="text" ng-readonly ="instituteNamereadOnly" ng-model="instituteName" class="form-control">
				  <div class="input-group-append">
                         <button type="button" class="btn btn-primary" ng-disabled="instituteNameSearchreadOnly" ng-click="fnInstituteUserSearch()"><i class="fas fa-search"></i></button>
                     </div>
               </div>       
               </div>        
                        </div>  
                                <div class="form-group row">
                                    <label for="userStatus" class="col-3 col-form-label">Status</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <select class="custom-select" id="userStatus" ng-disabled="statusReadonly" ng-model="status">
                                                <option ng-repeat="x in Status" value="{{x.value}}">{{x.name}}</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                        <label for="staffName" class="col-3 col-form-label">Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="staffName" type="text" ng-readonly="teacherNamereadOnly" ng-model="teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNamereadOnly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                
                                
                                
                                
                                
                            </div>
                            
                            
                             <!--<div class="form-group row">
                                        <label for="staffName" class="col-3 col-form-label">Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="staffName" type="text" ng-readonly="teacherNamereadOnly" ng-model="teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNamereadOnly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>-->
                            
                            
                            
                            <!--General Tab Ends-->
                            <!--Parent Tab Starts-->
                            <div class="tab-pane fade" id="parent" role="tabpanel" aria-labelledby="parentTab">
                                <div class="text-center contentDetailTab">Parent Student Role Mapping</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('parentRole')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('parentRole')}}</span></a>
                                        </li>

                                    </ul>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('parentRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('parentRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('parentRole',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('parentRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="parentRoleMappingShow" id="parentRole">
                                    <div class="form-group row">
                                        <label for="rollID" class="col-3 col-form-label">Role ID</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="rollID" type="text" ng-readonly="roleIDreadOnly" ng-model="parentRoleMappingRecord.roleID" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="roleIDreadOnly" ng-click="fnUserRoleSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group row">
                                        <label for="StudName" class="col-3 col-form-label">Student Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="studName" type="text" ng-readonly="studentNamereadOnly" ng-model="parentRoleMappingRecord.studentName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="studentNamereadOnly" ng-click="fnStudentSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="StudID" class="col-3 col-form-label">Student ID</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="studID" type="text" ng-readonly="studentIDreadOnly" ng-model="parentRoleMappingRecord.studentID" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                     <div class="form-group row">
                                        <label for="ParentiName" class="col-3 col-form-label">Institute Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="ParentiName" type="text" ng-readonly="instituteNamereadOnly" ng-model="parentRoleMappingRecord.instituteName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="instituteNamereadOnly" ng-click="fnInstituteUserSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--parent tab Ends-->
                            <!--Teacher Tab Starts-->
                            <div class="tab-pane fade" id="teacher" role="tabpanel" aria-labelledby="teacherTab">
                                <div class="text-center contentDetailTab">Teacher Entity Access Role</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('teacherRole')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('teacherRole')}}</span></a>
                                        </li>

                                    </ul>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('teacherRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('teacherRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('teacherRole',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('teacherRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="teacherRoleMappingShow" id="teacherRole">
                                    <div class="form-group row">
                                        <label for="TeacherrID" class="col-3 col-form-label">Role ID</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="TeacherrID" type="text" ng-readonly="roleIDreadOnly" ng-model="teacherRoleMappingRecord.roleID" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="roleIDreadOnly" ng-click="fnUserRoleSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="TeacheriName" class="col-3 col-form-label">Institute Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="TeacheriName" type="text" ng-readonly="instituteNamereadOnly" ng-model="teacherRoleMappingRecord.instituteName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="instituteNamereadOnly" ng-click="fnInstituteUserSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="staffName" class="col-3 col-form-label">Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="staffName" type="text" ng-readonly="teacherNamereadOnly" ng-model="teacherRoleMappingRecord.teacherName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="teacherNamereadOnly" ng-click="fnTeacherSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="staffId" class="col-3 col-form-label">ID</label>
                                        <div class=" input-group-append col-9">
                                            <input id="staffId" type="text" ng-readonly="teacherIDreadOnly" ng-model="teacherRoleMappingRecord.teacherID" class="form-control" readonly>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="ClassMenu" role="tabpanel" aria-labelledby="classTab">
                                <div class="text-center contentDetailTab">Class Entity Access Role</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('studentRole')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('studentRole')}}</span></a>
                                        </li>

                                    </ul>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('studentRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('studentRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('studentRole',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('studentRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="studentClassRoleMappingShow" id="studentRole">
                                    <div class="form-group row">
                                        <label for="StudentrID" class="col-3 col-form-label">Role ID</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="StudentrID" type="text" ng-readonly="roleIDreadOnly" ng-model="studentClassRoleMappingRecord.roleID" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="roleIDreadOnly" ng-click="fnUserRoleSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                          <div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Class</label>
                                    <div class=" input-group col-9">
                                        <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="studentClassRoleMappingRecord.class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>
                                     </div>
                                </div>
                                     <div class="form-group row">
                                        <label for="ClassiName" class="col-3 col-form-label">Institute Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="ClassiName" type="text" ng-readonly="instituteNamereadOnly" ng-model="studentClassRoleMappingRecord.instituteName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="instituteNamereadOnly" ng-click="fnInstituteUserSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--student Tab Ends-->
                            <!--Institute Tab Starts-->
                            <div class="tab-pane fade" id="institute" role="tabpanel" aria-labelledby="instituteTab">
                                <div class="text-center contentDetailTab">Institute Entity Access Role</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('instituteRole')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('instituteRole')}}</span></a>
                                        </li>

                                    </ul>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('instituteRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('instituteRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('instituteRole',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('instituteRole',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                <div ng-show="instituteRoleMappingShow" id="instituteRole">
                                    <div class="form-group row">
                                        <label for="InstituteiName" class="col-3 col-form-label">Institute Name</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="InstituteiName" type="text" ng-readonly="instituteNamereadOnly" ng-model="instituteRoleMappingRecord.instituteName" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="instituteNamereadOnly" ng-click="fnInstituteUserSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="InstituterID" class="col-3 col-form-label">Role ID</label>
                                        <div class="col-9">
                                            <div class="input-group">
                                                <input id="InstituterID" type="text" ng-readonly="roleIDreadOnly" ng-model="instituteRoleMappingRecord.roleID" class="form-control">
                                                <div class="input-group-append">
                                                    <button type="button" class="btn btn-primary" ng-disabled="roleIDreadOnly" ng-click="fnUserRoleSearch()"><i class="fas fa-search"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--institute Tab Ends-->
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
                    <div id="searchBody" ng-controller="UserNamesearch">
                    </div>
                    <div id="searchBody" ng-controller="StudentNamesearch">
                    </div>
                    <div id="searchBody" ng-controller="TeacherNameSerach">
                    </div>
                    <div id="searchBody" ng-controller="InstituteNamesearch">
                    </div>
                    <div id="searchBody" ng-controller="UserRoleSearch">
                    </div>
                     <div id="searchBody" ng-controller="InstituteUsersearch">
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