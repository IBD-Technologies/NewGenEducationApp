<%-- 
    Document   : UserRole
    Created on : Jul 18, 2019, 8:20:57 PM
    Author     : IBD Technologies
--%>

<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- Css Library Starts-->
      <link rel="stylesheet" href="/css/library/bootstrap.min.css">
      <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
	  <link rel="stylesheet" href="/css/library/jquery-ui.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
      <!--Css Library Ends--->
      <!--Js Library starts-->
      <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/angular-route.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
	   <script src="/js/js_library/jquery-ui.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
      <!--Js Library Ends-->
      <!--Css library Starts-->
      <link rel ="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel ="stylesheet" href="/css/utils/operation.css">
      <link rel ="stylesheet" href="/css/utils/search.css">
      <link rel ="stylesheet" href="/css/utils/audit.css">
      <link rel ="stylesheet" href="/css/utils/TableView.css">
	   <link rel ="stylesheet" href="/css/utils/SelectBox.css">
      <!--Css library Ends-->
      <!--Js Framework Starts-->      
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type ="text/javascript" src="/js/Utils/Operation.js"></script>
      <script type ="text/javascript" src="/js/Utils/search.js"></script>
      <script type ="text/javascript" src="/js/Utils/util.js"></script>
      <script type ="text/javascript" src="/js/Utils/TableView.js"></script>
      <script type ="text/javascript" src="/js/Utils/SelectBox.js"></script>
      <!-- Js Framework Ends-->
	  <script src="/js/UserRole.js"></script>
	 
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
             <h6 align="center">User Role</h6> 
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
            <div class ="form-group row">
               <label for="rID" class="col-3 col-form-label">Role ID</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="rID" type="text" ng-readonly="roleIDreadOnly" ng-model="roleID" class="form-control">
                     <div class="input-group-append">
                        <button type="button" class="btn btn-primary" ng-disabled="roleIDSearchreadOnly" ng-click="fnUserRoleSearch()"><i class="fas fa-search"></i></button>
                     </div>
                  </div>
               </div>
            </div>
            <div class= "form-group row">
               <label for="rDescription"  class="col-3 col-form-label">Role Description</label>
               <div class=" input-group-append col-9">
                  <input id="rDescription" type="text" ng-readonly ="roleDescriptionreadOnly" ng-model="roleDescription" class="form-control">
               </div>
            </div>
         </div>
         <div id="detailSection" ng-show="!searchShow&&detailshow">
            <ul class="nav nav-tabs mb-4 " id="myTab" role="tablist">
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link active" id="userRoleTab" data-toggle="tab" href="#userRole" role="tab"  aria-selected="false">User Role</a>
               </li>
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link"  id="descriptionTab" data-toggle="tab" href="#description" role="tab"  aria-selected="false"> Description</a>
               </li>
            </ul>
            <div class="tab-content" id="myTabContent">
               <div class="tab-pane fade show active" id="userRole" role="tabpanel" aria-labelledby="userRoleTab">
                  <div class="text-center contentDetailTab">User Role Configurations</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0"> 
                     <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('UserRoleMaster')}}</span></a></li>
						<li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
						<span class="svwRecCount">{{fnMvwGetTotalPage('UserRoleMaster')}}</span></a></li>
                     </ul>
					 <div>
					 </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                         <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('UserRoleMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('UserRoleMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('UserRoleMaster',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('UserRoleMaster',$event)"   
						class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="UserRoleMaster">
                     <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
						      <th></th>
                              <th scope="col">Function ID</th>
                              <th scope="col">C</th>
                              <th scope="col">V</th>
                              <th scope="col">M</th>
                              <th scope="col">R</th>
                              <th scope="col">D</th>
                              <th scope="col">A</th>
                              <th scope="col">AA</th>
                           </tr>
                        </thead>
                        <tbody  align="center">
						 <tr ng-repeat="X in ClassRoleShowObject">
						 <td><input type="checkbox" ng-model="X.checkBox"></td></td>
                           <td> 
                              <select id="functionID"  ng-disabled ="functionIDreadOnly" ng-model="X.functionID">
                                 <option ng-repeat="x in Screen " value="{{x}}">{{x}}</option>
                              </select>
                           </td>
                           <td><input type="checkbox"  ng-disabled="createreadOnly" ng-model="X.create"></td>
                           <td><input type="checkbox" ng-disabled="viewreadOnly" ng-model="X.view"></td>
                           <td><input type="checkbox" ng-disabled="modifyreadOnly" ng-model="X.modify"></td>
                           <td><input type="checkbox" ng-disabled="rejectreadOnly" ng-model="X.reject"></td>
                           <td><input type="checkbox" ng-disabled="deletereadOnly" ng-model="X.delete"></td>
                           <td><input type="checkbox" ng-disabled="authorizereadOnly" ng-model="X.auth"></td>
                           <td><input type="checkbox" ng-disabled="autoAuthreadOnly" ng-model="X.autoAuth"></td>
						   </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
               <div class="tab-pane fade" id="description" role="tabpanel" aria-labelledby="descriptionTab">
                  <div class="table-responsive">
                     <table class="table table-bordered  table-hover">
                        <thead align="center">
                           <tr>
                              <th scope="col">Label</th>
                              <th scope="col">Description</th>
                           </tr>
                        </thead>
                        <tbody id="TTbody" align="center">
                           <tr ng-repeat="X in UserRoleDescription">
                              <td>{{X.operation}}</td>
                              <td>{{X.description}}</td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
            </div>
         </div>
         <div id="Auditlogsection" ng-show="!searchShow&&auditshow">
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
      <div id="searchBody" ng-controller="UserRoleSearch">
      </div>
           <div id="searchBody" ng-controller="InstituteNamesearch">
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
