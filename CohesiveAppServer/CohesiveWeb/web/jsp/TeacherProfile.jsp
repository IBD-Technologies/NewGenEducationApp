<%-- 
    Document   : TeacherProfile
    Created on : Jul 18, 2019, 1:40:58 PM
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
            <!-- Css Library Ends--->
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
            <!-- Css Framework Library Starts-->
            <link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
            <link rel="stylesheet" href="/css/utils/operation.css">
            <link rel="stylesheet" href="/css/utils/search.css">
            <link rel="stylesheet" href="/css/utils/audit.css">
            <link rel="stylesheet" href="/css/utils/TableView.css">
            <link rel="stylesheet" href="/css/utils/SelectBox.css">

            <!-- Js Library Ends--->
            <!--Js Framework Library Starts--->
            <script type="text/javascript" src="/js/Utils/config.js"></script>
            <script type="text/javascript" src="/js/Utils/Exception.js"></script>
            <script type="text/javascript" src="/js/Utils/backEnd.js"></script>
            <script type="text/javascript" src="/js/Utils/Operation.js"></script>
            <script type="text/javascript" src="/js/Utils/search.js"></script>
            <script type="text/javascript" src="/js/Utils/util.js"></script>
            <script type="text/javascript" src="/js/Utils/TableView.js"></script>
            <script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
            <script src="/js/Utils/date_picker.js"></script>
            <!--Js Framework Library Ends--->
            <script src="/js/TeacherProfile.js"></script>

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
                        <h6 align="center">Teacher Profile</h6>
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
                            <label for="staffName" class="col-3 col-form-label">Name</label>
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
                            <label for="staffId" class="col-3 col-form-label">ID</label>
                            <div class=" input-group-append col-9">
                                <input id="staffId" type="text" ng-readonly="teacherIDreadOnly" ng-model="teacherID" class="form-control" readonly>
                            </div>
                        </div>
                        
                        <c:if test="${testEnv=='YES'}">
                        
                            <form id="TeacherimgUpld" action="https://cohesivetest.ibdtechnologies.com/CohesiveGateway/image/TeacherPhoto" method="post" enctype="multipart/form-data">
                        
                        </c:if>
                        
                        
                        <c:if test="${testEnv=='NO'}">
                        
                        <form id="TeacherimgUpld" action="https://cohesive.ibdtechnologies.com/CohesiveGateway/image/TeacherPhoto" method="post" enctype="multipart/form-data">
                        
                            </c:if> 
                            
                            
                            <div class="form-group row">
                                <label for="TeacherprofileImg" class="col-3 col-form-label">Profile Photo</label>
                                <div class=" input-group col-9">
                                    <input id="TeacherprofileImg" name="profileImg" type="file" ng-disabled="profileImgPathNamereadOnly" ng-model="profileImgPath" class="form-control-plaintext">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="teacher" class="col-3"> </label>
                                <div class="col-9">
                                    <div class="imgcontainer text-center">
                                        <img id="teacher" src="" class="img-fluid img-thumbnail" alt="Teacher Photo">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="detailSection" ng-show="!searchShow&&detailshow">
                        <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active " data-toggle="tab" href="#General" role="tab" aria-controls="General" aria-selected="true">General</a>
                            </li>
                             <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#address" role="tab" aria-controls="address" aria-selected="false">Address</a>
                            </li>
                               <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#tabContact" role="tab" aria-controls="address" aria-selected="false">Family</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#Emergency" role="tab" aria-controls="Emergency" aria-selected="false">Others</a>
                            </li>
                          
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane fade show active " id="General" role="tabpanel">
                                <div class="form-group row">
                                    <label for="qualification" class="col-3 col-form-label">Qualification</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input id="qualification" type="text" ng-model="general.qualification" ng-readonly="qualificationreadOnly" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Class</label>
                                    <div class=" input-group col-9">
                                        <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="general.class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="dateOfBirth" class="col-3 col-form-label">DOB</label>
                                    <div class=" input-group col-9">
                                        <input id="dateOfBirth" ng-model="general.dob" class="form-control" ng-readonly="dobreadOnly">
                                        <div class="input-group-append">
                                            <button type="button" ng-disabled="dobreadOnly" id="dateOfBirthShow" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                        </div>
                                    </div>
                                </div>
                                 <div class="form-group row">
                                        <label for="gender" class="col-3 col-form-label">Gender</label>
                                        <div class=" input-group col-9">
                                            <select class="custom-select" id="gender" ng-disabled="genderReadonly" ng-model="general.gender">
                                                <option ng-repeat="x in genders" value="{{x.value}}">{{x.name}}</option>
                                            </select>

                                        </div>
                                    </div>
                               
                            </div>
                            <div class="tab-pane fade" id="Emergency" role="tabpanel">
                                     <div class="form-group row">
                                    <label for="mobileNumber" class="col-3 col-form-label">Contact Number</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input id="Contact" type="text" id="mobileNumber" ng-model="general.contactNo" ng-readonly="contactNoreadOnly" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="mail" class="col-3 col-form-label">Mail</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input type="text" id="mail" ng-model="general.emailID" ng-readonly="emailIDreadOnly" class="form-control" readonly>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row mb-3 ">
                                    <label for="Blood" class="col-3 col-form-label">Blood Group</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input id="Blood" type="text" ng-model="general.bloodGroup" ng-readonly="bloodreadOnly" class="form-control" readonly>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="existingMedicalDetail" class="col-3 col-form-label">Existing Medical Details</label>
                                     <div class="col-9">
                                        <div class="input-group">
                                    <textarea class="form-control" ng-readonly="medicalDetailreadonly" ng-model="emergency.existingMedicalDetails"></textarea>
                                </div>
                            </div>
                                </div>
                            </div>
                            <div class="tab-pane fade " id="address" role="tabpanel">
                                <div class="form-group row">
                                    <label for="qualification" class="col-3 col-form-label">Address Line 1</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input class="form-control" ng-model="general.address.addressLine1" ng-readonly="doornumberreadOnly">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="street" class="col-3 col-form-label">Address Line 2</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <textarea class="form-control" ng-model="general.address.addressLine2" ng-readonly="streetreadOnly"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="street" class="col-3 col-form-label">Address Line 3</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input class="form-control" ng-model="general.address.addressLine3" ng-readonly="cityreadOnly">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="street" class="col-3 col-form-label">Address Line 4</label>
                                    <div class="col-9">
                                        <div class="input-group">
                                            <input class="form-control" type="text" ng-model="general.address.addressLine4" ng-readonly="pincodereadOnly">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="tab-pane fade " id="tabContact" role="tabpanel">

                                 <div class="text-center contentDetailTab">Family Details</div>
                                <nav class="navbar navbar-light bg-light contentDetailTabNav">
                                    <ul class="pagination pagination-sm   mb-0">
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('ContactPersonDetails')}}</span></a></li>
                                        <li class="page-item"><a class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                                        <li class="page-item">
                                            <a class="page-link page_style svwRecCount">
                                                <span class="svwRecCount">{{fnSvwGetTotalPage('ContactPersonDetails')}}</span></a>
                                        </li>

                                    </ul>
                                    <ul class="pagination pagination-sm justify-content-end mb-0">
                                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('ContactPersonDetails',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('ContactPersonDetails',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                                        <li class="page-item">
                                            <a id="addButton" ng-click="fnSvwAddRow('ContactPersonDetails',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a>
                                        </li>
                                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('ContactPersonDetails',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                                    </ul>
                                </nav>
                                 <div ng-show="contactPersonShow" id="ContactPersonDetails">
                           <ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active " data-toggle="tab" href="#subProfile" role="tab" aria-controls="General" aria-selected="true">Profile</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" data-toggle="tab" href="#subContact" role="tab" aria-controls="Emergency" aria-selected="false">Contact</a>
                            </li>
                            </ul>  
                                     
                            <div class="tab-content">
                            <div class="tab-pane fade show active " id="subProfile" role="tabpanel">     
                                            <div class="form-group row">
                                            <label for="contactPersonName" class="col-3 col-form-label">Name</label>
                                            <div class="col-9">
                                                <div class="input-group">
                                                    <input type="text" class="form-control"  ng-model="contactPersonRecord.cp_Name" ng-readonly="contactpersonreadOnly">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="relationShip" class="col-3 col-form-label">Relationship</label>
                                            <div class="col-9">
                                                <div class="input-group">
                                                    <input type="text" class="form-control"  ng-model="contactPersonRecord.cp_relationship" ng-readonly="relationshipreadOnly">
                                                </div>
                                            </div>
                                        </div>
                                
                                   </div>
                                
                                <div class="tab-pane fade" id="subContact" role="tabpanel">     
                                             <div class="form-group row">
                                            <label for="Email" class="col-3 col-form-label">E-Mail</label>
                                            <div class="col-9">
                                                <div class="input-group">
                                                    <input type="text" id="mail" class="form-control"  ng-model="contactPersonRecord.cp_emailID" ng-readonly="cpEmailidreadOnly">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="contact" class="col-3 col-form-label">Contact Number</label>
                                            <div class="col-9">
                                                <div class="input-group">
                                                    <input type="text" class="form-control" id="mobileNumber"  ng-model="contactPersonRecord.cp_contactNo" ng-readonly="cpContactnumberreadOnly">
                                                </div>
                                            </div>
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