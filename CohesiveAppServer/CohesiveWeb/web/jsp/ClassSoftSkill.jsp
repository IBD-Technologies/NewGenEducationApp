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
      <!-- Css Library Ends-->
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
      <link rel ="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel ="stylesheet" href="/css/utils/operation.css">
      <link rel ="stylesheet" href="/css/utils/search.css">
      <link rel ="stylesheet" href="/css/utils/audit.css">
	  <link rel ="stylesheet" href="/css/utils/SelectBox.css">
	   <link rel ="stylesheet" href="/css/utils/TableView.css">
      <!--Css Framework Ends-->
      <!--Js Framework Starts-->
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type ="text/javascript" src="/js/Utils/Operation.js"></script>
      <script type ="text/javascript" src="/js/Utils/search.js"></script>
      <script type ="text/javascript" src="/js/Utils/util.js"></script>
	  <script type ="text/javascript" src="/js/Utils/SelectBox.js"></script>
	   <script type ="text/javascript" src="/js/Utils/TableView.js"></script>
      <!--Js Framework Ends-->
      <script src="/js/ClassSoftSkill.js"></script>
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
             <h6 align="center">Soft Skill Assessment</h6> 
         <div id="operationsection" class="subScreenOperationSection" ng-view>
         </div>
         </div>
         <div id="searchHeader"> 
         </div>    
      </header>
      <div id="subscreenContent" class="subscreenContent">
          <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
         <div id="mastersection" ng-show="mastershow">
                       <div class="form-group row">
                                    <label for="class" class="col-3 col-form-label">Class</label>
                                    <div class=" input-group col-9">
                                        <select class="custom-select" id="class" ng-disabled="classReadonly" ng-model="class">
                                            <option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
                                        </select>
                                    </div>
                                </div>
            <div class="form-group row">
               <label for="examType" class="col-3 col-form-label">Exam</label>
               <div class="col-9">
                  <div class="input-group">
                     <select class="custom-select" id="examType" ng-disabled ="examtypereadOnly" ng-model="exam">
                        <option ng-repeat="x in ExamMaster"  value="{{x}}">{{x}}</option>
                     </select>
                  </div>
               </div>
            </div>
            <div class= "form-group row ">
                  <label for="skill" class="col-3 col-form-label">Skill</label>
                  <div class=" input-group col-9">
                     <select class="custom-select" id="skill" ng-disabled="skillNamereadOnly" ng-model="skillID">
                        <option ng-repeat="x in skills" value="{{x.value}}">{{x.name}}</option>
                     </select>
                  </div>
               </div>
         </div>
         <div id="detailSection" ng-show="!searchShow&&detailshow">
            <!--<ul class="nav nav-tabs mb-4" id="myTab" role="tablist">
               <li class="nav-item">
                  <a class="nav-link active"  data-toggle="tab" href="#skillEntry" role="tab" aria-controls="skill-Entry" aria-selected="true">Skill Entry</a>
               </li>
               <!--<li class="nav-item">
                  <a class="nav-link"  data-toggle="tab" href="#gradeDescription" role="tab" aria-controls="grade-Description" aria-selected="false">Grade Description</a>
               </li>-->
            <!--</ul>-->
            <!--<div class="tab-content" id="myTab">-->
               <!--<div class="tab-pane fade show active" id="skillEntry" role="tabpanel" aria-labelledby="skill-Entry">-->
			   		    <div class="text-center contentDetailTab">Skill Entry </div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0">
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('Skill')}}</span></a></li>
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
                           <span class="svwRecCount">{{fnMvwGetTotalPage('Skill')}}</span></a>
                        </li>
                     </ul>
                     <div>
                     </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('Skill',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('Skill',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('Skill',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('Skill',$event)"   
                           class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="skillEntry">
                   <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
						     <th scope="col"></th>
                                                     <th scope="col">Name</th>
                              <th scope="col">Category</th>
                              <th scope="col">Teacher Feedback</th>
                              
                           </tr>
                        </thead>
						   <tbody id="TTbody" align="center">
                           <tr ng-repeat="X in SkillShowObject">
						     <td>
							  <input type="checkbox" ng-model="X.checkBox">
							   </td>
                              <th scope="col">{{X.studentName}}</th>
                              
                              
                               <td>
                                 <!--<input  type="text" class="form-control-plaintext" ng-model="X.grade"  ng-readonly="gradeReadOnly" > -->
                                 <select class="custom-select" id="category" ng-disabled="categoryReadOnly" ng-model="X.category">
                                    <option ng-repeat="x in categories" value="{{x.value}}">{{x.name}}</option>
                                 </select>
                                 
                                 
                              </td>
                              <td>						  
                                 <input class="form-control-plaintext" ng-model="X.teacherFeedback" ng-readonly="teacherFeedbackReadOnly">
                              </td>
							 
                              
                              
                           </tr>
                        </tbody>
                     </table>
                  </div>
                  <!--</div>-->				  
               <!--<div class="tab-pane fade" id="gradeDescription" role="tabpanel" aria-labelledby="grade-Description">
                  <div class="table-responsive">
                     <table class="table table-bordered  table-hover">
                        <thead align="center">
                           <tr>
                              <th scope="col">Grade</th>
                              <th scope="col">Description</th>
                           </tr>
                        </thead>
                        <tbody id="TTbody" align="center">
                           <tr ng-repeat="X in GradeDescription">
                              <td ng-readonly="gradeReadOnly">{{X.grade}}</td>
                              <td ng-readonly="gradeReadOnly">{{X.description}}</td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>-->
            <!--</div>-->
         </div>
         <div id="Auditlogsection" ng-show="auditshow">
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
                                 <label for="reskills" class="col-3 col-form-label">Remarks</label>
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
                                 <label for="reskills" class="col-3 col-form-label">Remarks</label>
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
                                 <label for="reskills" class="col-3 col-form-label">Version</label>
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
      <footer class="subscreenFooter">
         <nav class="nav nav-pills nav-justified">
            <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Master</a>
            <a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Details</a>
            <a id="AuditFooter" class="cohesiveFooter_navitem footer_color" href="#">Audit</a>
         </nav>
      </footer>
      <div id="snackbar"></div>
   </body>
</html>   