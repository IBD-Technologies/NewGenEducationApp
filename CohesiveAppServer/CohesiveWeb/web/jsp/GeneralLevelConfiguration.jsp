<%-- 
    Document   : GeneralLevelConfigurations
    Created on : 9 Jun, 2019, 5:01:59 PM
    Author     : IBD Technologies
--%>

<%@page session="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<!--
   To change this license header, choose License Headers in Project Properties.
   To change this template file, choose Tools | Templates
   and open the template in the editor.
   -->
<html>
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!--Css Library Starts------>
      <link rel="stylesheet" href="/css/library/bootstrap.min.css">
      <link rel="stylesheet" href="/css/library/bootstrap-grid.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/fontawesome.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/all.min.css">
      <link rel="stylesheet" href="/Fontawesome_new/css/brands.min.css">
      <!--Css Library Ends---->
      <!-- Js Library Starts----->
       <script src="/js/js_library/angular.min.js"></script>
      <script src="/js/js_library/angular-route.js"></script>
      <script src="/js/js_library/jquery-3.3.1.min.js"></script>
      <script src="/js/js_library/bootstrap.min.js"></script>
      <script src="/Fontawesome_new/js/fontawesome.min.js"></script>
      <script src="/Fontawesome_new/js/all.min.js"></script>
      <script src="/Fontawesome_new/js/brands.min.js"></script>
      <!--Js Library Ends--->
      <!-- Css Framework Starts-->
      <link rel ="stylesheet" href="/css/utils/ScreenTemplate.css">
      <link rel ="stylesheet" href="/css/utils/operation.css">
      <link rel ="stylesheet" href="/css/utils/search.css">
      <link rel ="stylesheet" href="/css/utils/audit.css">
      <link rel ="stylesheet" href="/css/utils/TableView.css">
      <link rel ="stylesheet" href="/css/utils/SelectBox.css">
      <!-- Css Framework Ends-->
      <!-- Js Framework Starts--->     
     <!--<script type ="text/javascript" src="/js/Utils/session.js"></script>-->
      <script type ="text/javascript" src="/js/Utils/config.js"></script>
      <script type ="text/javascript" src="/js/Utils/Exception.js"></script>
      <script type ="text/javascript" src="/js/Utils/backEnd.js"></script>
      <script type ="text/javascript" src="/js/Utils/Operation.js"></script>
      <script type ="text/javascript" src="/js/Utils/search.js"></script>
      <script type ="text/javascript" src="/js/Utils/util.js"></script>
      <script type ="text/javascript" src="/js/Utils/TableView.js"></script>
      <script type ="text/javascript" src="/js/Utils/SelectBox.js"></script>
     <!-- <script type ="text/javascript" src="/js/Utils/spinner.js"></script> -->
           <!-- Js Framework Ends--->
      <script src="/js/GeneralLevelConfigurations.js"></script>
	  


   </head>
   <body id ="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
       <%
         response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
        response.setHeader("Pragma","no-cache"); //Http 1.0
        response.setHeader("Expires", "-1"); //Proxies
       response.setHeader("X-XSS-Protection","1;mode=block");
       response.setHeader("X-Frame-Options","SAMEORIGIN");
       response.setHeader("Content-Security-Policy","default-src 'self';script-src 'self';style-src 'unsafe-inline' 'self';base-uri 'none';form-action 'none';frame-ancestors 'self'");
       %>
       <header id="subscreenHeader" class="subscreenHeader mb-3">
         <div id="subscreenHeading" class="ssHeading" ng-show="!searchShow">
             <h6 align="center">Institute Configuration</h6> 
         <div id="operationsection" class="subScreenOperationSection" ng-view>
         </div>
         </div>
         <div id="searchHeader"> 
         </div>    
            <!-- </div> -->
        <!-- <div id="subscreenHeading">
            <button type="button" class="btn btn-lg btn-block operation_button" disabled><b>Institute General Configuration</b> </button>
         </div> -->
      </header>
      <div id="subscreenContent" class="subscreenContent">
          <input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
     
          <div id="mastersection" ng-show="!searchShow&&mastershow">
		 <div class= "form-group row">
               <label for="iName"  class="col-3 col-form-label">Institute Name</label>
               <div class="col-9">
               <div class="input-group">
                  <input id="iName" type="text" ng-readonly ="instituteNamereadOnly" ng-model="instituteName" class="form-control">
	             <div class="input-group-append">
                       <button type="button" class="btn btn-primary" ng-disabled="instituteSearchreadOnly" ng-click="fnInstituteNameSearch()"><i class="fas fa-search"></i></button>
                     </div></div>
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

              <c:if test="${testEnv=='YES'}">
              
                  <form id="imgUpld" action="https://cohesivetest.ibdtechnologies.com/CohesiveGateway/image/instituteLogo"  method="post" enctype="multipart/form-data">
                  
                  
              </c:if>  
              
              
         
                  <c:if test="${testEnv=='NO'}">
                  
                  <form id="imgUpld" action="https://cohesive.ibdtechnologies.com/CohesiveGateway/image/instituteLogo"  method="post" enctype="multipart/form-data">
                  
                  </c:if>
                  
                   
                  <div class= "form-group row">
               
                <label for="profileImg"  class="col-3 col-form-label">Institute Logo</label>
               <div class=" input-group col-9">
                 <input id="profileImg" name="profileImg" type="file" ng-disabled ="profileImgPathNamereadOnly" ng-model="profileImgPath" class="form-control-plaintext"> 
               
               
               </div> 
              
             </div>
             <div class= "form-group row"> 
                <label for="Logo" class="col-3"> </label> 
                <div class="col-9">
                 <div class="imgcontainer text-center">   
                 <img id="logo" src="" class="img-fluid img-thumbnail" alt="Logo Image">
                 </div>
                </div>   
            </div>
                 
              </form> 
         </div>
         <div id="detailSection" ng-show="!searchShow&&detailshow">
            <ul class="nav nav-tabs mb-4 " id="myTab" role="tablist">
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link active ContentTabNavlink" id="examTab" data-toggle="tab" href="#exam" role="tab"  aria-selected="false">Exam</a>
               </li>
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link ContentTabNavlink"  id="gradeTab" data-toggle="tab" href="#grade" role="tab"  aria-selected="false">Grade</a>
               </li>
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link ContentTabNavlink"  id="subjectTab" data-toggle="tab" href="#subject" role="tab" aria-selected="false">Subject</a>
               </li>
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link ContentTabNavlink" id="notifcationTab" data-toggle="tab" href="#notification" role="tab" aria-selected="true">Notification </a>
               </li>
               <li class="nav-item cohesiveTabPadding">
                  <a class="nav-link ContentTabNavlink"  id="feeMasterTab" data-toggle="tab" href="#FeeDetails" role="tab" aria-selected="false">Fee</a>
               </li>
            </ul>
            <div class="tab-content" id="myTabContent">
               <div class="tab-pane fade show active" id="exam" role="tabpanel" aria-labelledby="examTab">
                  <div class="text-center contentDetailTab">Exam Configuration</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0">
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('examMaster')}}</span></a></li>
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
                           <span class="svwRecCount">{{fnMvwGetTotalPage('examMaster')}}</span></a>
                        </li>
                     </ul>
                     <div>
                     </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('examMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('examMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('examMaster',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('examMaster',$event)"   
                           class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="examMaster">
                     <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
                              <th scope="col"></th>
                              <th scope="col">Type</th>
                              <th scope="col">Description</th>
                              <th scope="col">Other Language Description</th>
                           </tr>
                        </thead>
                        <tbody  align="center">
                           <tr ng-repeat="X in examMasterShowObject">
                              <td><input type="checkbox" ng-model="X.checkBox"></td>
                              <td><input type="text" ng-readonly="examTypereadOnly" class="form-control-plaintext" ng-model="X.examType"></td>
                              <td><textarea type="text" ng-readonly="examDescriptionreadOnly" class="form-control-plaintext" ng-model="X.description"> </textarea></td>
                              <td><textarea type="text" ng-readonly="otherLangDescriptionreadOnly" class="form-control-plaintext" ng-model="X.otherLangDescription"> </textarea></td>
                           </tr>
                     </table>
                  </div>
               </div>
               <div class="tab-pane fade " id="subject" role="tabpanel" aria-labelledby="subjectTab">
                  <div class="text-center contentDetailTab">Subject Configuration</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0">
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('subjectMaster')}}</span></a></li>
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
                           <span class="svwRecCount">{{fnMvwGetTotalPage('subjectMaster')}}</span></a>
                        </li>
                     </ul>
                     <div>
                     </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('subjectMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('subjectMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('subjectMaster',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('subjectMaster',$event)"   
                           class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="subjectMaster">
                     <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
                              <th scope="col"></th>
                              <th scope="col">ID</th>
                              <th scope="col">Name</th>
                              <th scope="col">Other Language Description</th>
                           </tr>
                        </thead>
                        <tbody  align="center">
                           <tr ng-repeat="X in subjectMasterShowObject">
                              <td><input type="checkbox" ng-model="X.checkBox"></td>
                              <td><input type="text"  class="form-control-plaintext" ng-readonly="subjectIDreadOnly" ng-model="X.subjectID"></td>
                              <td><input type="text"  class="form-control-plaintext" ng-readonly="subjectNamereadOnly" ng-model="X.subjectName"></td>
                              <td><textarea type="text" ng-readonly="otherLangDescriptionreadOnly" class="form-control-plaintext" ng-model="X.otherLangDescription"> </textarea></td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
               <div class="tab-pane fade" id="grade" role="tabpanel" aria-labelledby="gradeTab">
                  <div class="text-center contentDetailTab">Grade Configuration</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0">
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('gradeMaster')}}</span></a></li>
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
                           <span class="svwRecCount">{{fnMvwGetTotalPage('gradeMaster')}}</span></a>
                        </li>
                     </ul>
                     <div>
                     </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('gradeMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('gradeMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('gradeMaster',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('gradeMaster',$event)"   
                           class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="gradeMaster">
                     <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
                              <th scope="col"></th>
                              <th scope="col">From</th>
                              <th scope="col">To</th>
                              <th scope="col">Grade</th>
                           </tr>
                        </thead>
                        <tbody  align="center">
                           <tr ng-repeat="X in gradeMasterShowObject">
                              <td><input type="checkbox" ng-model="X.checkBox"></td>
                              <td><input type="text"  class="form-control-plaintext" ng-readonly="fromreadOnly" ng-model="X.from" ></td>
                              <td><input type="text"  class="form-control-plaintext" ng-readonly="toreadOnly" ng-model="X.to"></td>
                              <td><input type="text"  class="form-control-plaintext" ng-readonly="gradereadOnly" ng-model="X.grade"></td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
               <div class="tab-pane fade " id="notification" role="tabpanel" aria-labelledby="notifcationTab">
                  <div class="text-center contentDetailTab">Notification Configuration</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0">
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('notificationMaster')}}</span></a></li>
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
                           <span class="svwRecCount">{{fnMvwGetTotalPage('notificationMaster')}}</span></a>
                        </li>
                     </ul>
                     <div>
                     </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('notificationMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('notificationMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('notificationMaster',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('notificationMaster',$event)"   
                           class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="notificationMaster">
                     <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
                              <th scope="col"></th>
                              <th scope="col"> Type</th>
                              <th scope="col"> Description</th>
                              <th scope="col">Other Language Description</th>
                           </tr>
                        </thead>
                        <tbody id="notificationBody" align="center">
                           <tr ng-repeat="X in notificationMasterShowObject">
                              <td><input type="checkbox" ng-model="X.checkBox"></td>
                              <td><input type="text" ng-readonly="notificationTypereadOnly" class="form-control-plaintext" ng-model="X.notificationType"></textarea></td>
                              <td><textarea type="text" ng-readonly="notificationDescriptionreadOnly" class="form-control-plaintext" ng-model="X.description"></textarea></td>
                              <td><textarea type="text" ng-readonly="otherLangDescriptionreadOnly" class="form-control-plaintext" ng-model="X.otherLangDescription"> </textarea></td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </div>
               <div class="tab-pane fade" id="FeeDetails" role="tabpanel" aria-labelledby="feeMasterTab">
                  <div class="text-center contentDetailTab">Fee Configuration</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0">
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('feeMaster')}}</span></a></li>
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
                           <span class="svwRecCount">{{fnMvwGetTotalPage('feeMaster')}}</span></a>
                        </li>
                     </ul>
                     <div>
                     </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('feeMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('feeMaster',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('feeMaster',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('feeMaster',$event)"   
                           class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="feeMaster">
                     <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
                              <th scope="col"></th>
                              <th scope="col">Type</th>
                              <th scope="col">Description</th>
                              <th scope="col">Other Language Description</th>
                           </tr>
                        </thead>
                        <tbody id="feeBody" align="center">
                           <tr ng-repeat="X in feeMasterShowObject">
                              <td><input type="checkbox" ng-model="X.checkBox"></td>
                              <td><input type="text" ng-readonly="feeTypereadOnly" class="form-control-plaintext" ng-model="X.feeType"></td>
                              <td><textarea type="text" ng-readonly="feeDescriptionreadOnly" class="form-control-plaintext" ng-model="X.feeDescription"></textarea></td>
                              <td><textarea type="text" ng-readonly="otherLangDescriptionreadOnly" class="form-control-plaintext" ng-model="X.otherLangDescription"> </textarea></td>
                        </tbody>
                     </table>
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