<%-- 
    Document   : GroupMapping
    Created on : Jul 17, 2019, 5:48:23 PM
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
      <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/angular-route.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
      <script src="/js/js_library/jquery-ui.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
	  <!--Js Library Ends-->
	  <!--Css Framework Starts--->
      <link rel ="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel ="stylesheet" href="/css/utils/operation.css">
      <link rel ="stylesheet" href="/css/utils/search.css">
      <link rel ="stylesheet" href="/css/utils/audit.css">
      <link rel ="stylesheet" href="/css/utils/TableView.css">
      <link rel ="stylesheet" href="/css/utils/SelectBox.css">
      <!--Css Framework Ends-->
	  <!--Js Framework Starts-->
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type ="text/javascript" src="/js/Utils/Operation.js"></script>
      <script type ="text/javascript" src="/js/Utils/search.js"></script>
      <script type ="text/javascript" src="/js/Utils/util.js"></script>
      <script type ="text/javascript" src="/js/Utils/TableView.js"></script>
      <script type ="text/javascript" src="/js/Utils/SelectBox.js"></script>
       <!--Js Framework Ends-->
      <script src="/js/GroupMapping.js"></script>
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
             <h6 align="center"> Group </h6> 
         <div id="operationsection" class="subScreenOperationSection" ng-view>
         </div>
         </div>
         <div id="searchHeader"> 
         </div>    
      </header>
      <div id="subscreenContent" class="subscreenContent">
          <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
         <div id="mastersection" ng-show="!searchShow&&mastershow">
               <div class= "form-group row">
               <label for="iName"  class="col-3 col-form-label">Institute Name</label>
               <div class="col-9">
               <div class=" input-group">
                  <input id="iName" type="text" ng-readonly ="instituteNamereadOnly" ng-model="instituteName" class="form-control">
				  <div class="input-group-append">
                         <button type="button" class="btn btn-primary" ng-disabled="instituteNameSearchreadOnly" ng-click="fnInstituteNameSearch()"><i class="fas fa-search"></i></button>
                     </div>
               </div>
			   
            </div>
			</div>
		             <div class ="form-group row">
               <label for="iID" class="col-3 col-form-label">Institute ID</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="iID" type="text" ng-readonly="instituteIDreadOnly" ng-model="instituteID" class="form-control">
                  </div>
               </div>
            </div>
            <div class ="form-group row">
               <label for="groupID" class="col-3 col-form-label">Group ID</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="groupID" type="text" ng-readonly="groupIDreadOnly" ng-model="groupID" class="form-control">
                     <div class="input-group-append">
                        <button type="button" class="btn btn-primary" ng-disabled="groupIDSearchreadOnly" ng-click="fnGroupingSearch()"><i class="fas fa-search"></i></button>
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
                     <a class="nav-link"  data-toggle="tab" href="#groupTab" role="tab" aria-selected="false">Group</a>
                  </li>
               </ul>
		       <div class="tab-content" id="detailSubTab">
               <div class="tab-pane fade show active" id="general" role="tabpanel" aria-labelledby="generalTab">
		      <div class= "form-group row">
               <label for="description"  class="col-3 col-form-label">Description</label>
               <div class=" input-group-append col-9">
                  <input id="Description" type="text" ng-readonly ="groupDescriptionreadOnly" ng-model="groupDescription" class="form-control">
               </div>
            </div>
		 </div>
		  <div class="tab-pane fade" id="groupTab" role="tabpanel" >
           <div class="text-center contentDetailTab">Group </div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav" >
                     <ul class="pagination pagination-sm   mb-0"> 
                      <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnSvwGetCurrentPage('groupMapping')}}</span></a></li>
						<li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
						<span class="svwRecCount">{{fnSvwGetTotalPage('groupMapping')}}</span></a></li>
						
                     </ul>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnSvwBackward('groupMapping',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnSvwForward('groupMapping',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnSvwAddRow('groupMapping',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnSvwDeleteRow('groupMapping',$event)"   
						class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
            <div ng-show="GroupMappingShowObject" id="groupMapping">
                <div class="form-group row">
                <label for="class" class="col-3 col-form-label">Class</label>
                 <div class=" input-group col-9">
                 <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="GroupMappingRecord.class">
                  <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                  </select>
                                    </div>
                                </div>
           <div class ="form-group row">
               <label for="studName" class="col-3 col-form-label"> Student Name</label>
               <div class="col-9">
                  <div class="input-group">
                     <input id="studName" type="text" ng-readonly="studentNamereadOnly" ng-model="GroupMappingRecord.studentName" class="form-control">
                     <div class="input-group-append">
                        <button type="button" class="btn btn-primary" ng-disabled="studentNamereadOnly" ng-click="fnStudentSearch()"><i class="fas fa-search"></i></button>
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
                                 <label for="date" class="col-3 col-form-label">Authorise</label>
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
            </div>
         </div>
		 <div id="searchBody" ng-controller="StudentNamesearch" >
         </div>
		 <div id="searchBody" ng-controller="GroupIDSearch" >
         </div>
          <div id="searchBody" ng-controller="InstituteNamesearch">	
      </div>
      </div>          
      <footer class="subscreenFooter" ng-show="!searchShow">
         <nav class="nav nav-pills nav-justified">
            <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Master</a>
            <a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Details</a>
            <a id="AuditFooter"  class="cohesiveFooter_navitem footer_color" href="#">Audit</a>
         </nav>
      </footer>
      <div id="snackbar"></div>
      <div id="spinner"></div>
   </body>
</html>
