<%-- Document : OtherActivity Created on : Jul 18, 2019, 8:15:18 PM Author : IBD Technologies --%>
	<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
	  <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
			<!-- Css Framework starts-->
			<link rel="stylesheet" href="/css/utils/ScreenTemplate.css">
			<link rel="stylesheet" href="/css/utils/operation.css">
			<link rel="stylesheet" href="/css/utils/search.css">
			<link rel="stylesheet" href="/css/utils/audit.css">
			<link rel="stylesheet" href="/css/utils/SelectBox.css">
			<!-- Css Framework Ends-->
			<!--Js Framework Starts-->
			<script type="text/javascript" src="/js/Utils/config.js"></script>
			<script type="text/javascript" src="/js/Utils/Exception.js"></script>
			<script type="text/javascript" src="/js/Utils/backEnd.js"></script>
			  <c:forEach items="${cookie}" var="currentCookie">  
                          <c:if test="${currentCookie.key=='userType'}">
                <c:if test="${currentCookie.value.value=='P'}">
                    <script type="text/javascript" src="/js/Utils/EnrollOperation.js"></script>
                </c:if>
                     <c:if test="${currentCookie.value.value=='T'}">
                    <script type="text/javascript" src="/js/Utils/Operation.js"></script>
                </c:if>
                <c:if test="${currentCookie.value.value=='A'}">
                    <script type="text/javascript" src="/js/Utils/Operation.js"></script>
                </c:if>
            </c:if> 


    </c:forEach>  
			<script type="text/javascript" src="/js/Utils/search.js"></script>
			<script type="text/javascript" src="/js/Utils/util.js"></script>
			<script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
			<script src="/js/Utils/date_picker.js"></script>
			<!--Js Framework Ends-->
			<script src="/js/StudentOtherActivity.js"></script>
		</head>

		<body id="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
			<% response.setHeader( "Cache-Control", "no-cache,no-store,must-revalidate"); response.setHeader( "Pragma", "no-cache"); //Http 1.0 response.setHeader( "Expires", "-1"); //Proxies response.setHeader( "X-XSS-Protection", "1;mode=block"); response.setHeader( "X-Frame-Options", "SAMEORIGIN"); response.setHeader( "Content-Security-Policy", "default-src 'self';img-src 'self' data:;script-src  'self';style-src 'unsafe-inline'  'self';base-uri 'none';form-action 'none';frame-ancestors 'self'"); %>
				<header id="subscreenHeader" class="subscreenHeader mb-3">
					<div id="subscreenHeading" class="ssHeading" ng-show="!searchShow">
						<h6 align="center">Student Other Activity</h6>
						<div id="operationsection" class="subScreenOperationSection" ng-view></div>
					</div>
					<div id="searchHeader"></div>
				</header>
				<div id="subscreenContent" class="subscreenContent">
					<input type="hidden" id="nokotser" ng-model="nokotser" value="${nokotser}">
					<div id="mastersection" ng-show="!searchShow&&mastershow">
						<div class="form-group row">
							<label for="studName" class="col-3 col-form-label">Name</label>
							<div class="col-9">
								<div class="input-group">
									<input id="studName" type="text" ng-readonly="studentNamereadOnly" ng-model="studentName" class="form-control">
									<div class="input-group-append">
										<button type="button" class="btn btn-primary" ng-disabled="studentNameSearchreadOnly" ng-click="fnStudentSearch()"><i class="fas fa-search"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label for="studentID" class="col-3 col-form-label">ID</label>
							<div class=" input-group col-9">
								<input id="studentID" type="text" ng-readonly="studentIDreadOnly" ng-model="studentID" class="form-control">
							</div>
						</div>
						<div class="form-group row">
							<label for="activityID" class="col-3 col-form-label">Activity ID</label>
							<div class="col-9">
								<div class="input-group">
									<input id="activityID" type="text" ng-readonly="studentNamereadOnly" ng-model="activityID" class="form-control">
									<div class="input-group-append">
										<button type="button" class="btn btn-primary" ng-disabled="studentNamereadOnly" ng-click="fnActivitySearch()"><i class="fas fa-search"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
                                            
                                                <div class="form-group row">
									<label for="activity" class="col-3 col-form-label">Activity Description</label>
									<div class="col-9">
										<div class="input-group">
											<input id="activity" type="text" ng-readonly="activityNamereadOnly" ng-model="activityName" class="form-control">
										</div>
									</div>
								</div>
					</div>
					<div id="detailSection" ng-show="!searchShow&&detailshow">
						<ul class="nav nav-tabs mb-4 " id="myTab" role="tablist">
							<li class="nav-item cohesiveTabPadding"> <a class="nav-link active" id="generalTab" data-toggle="tab" href="#subGeneral" role="tab" aria-selected="true">General</a>
							</li>
							<li class="nav-item cohesiveTabPadding"> <a class="nav-link" id="subOthersTab" data-toggle="tab" href="#subOthers" role="tab" aria-selected="false">Others</a>
							</li>
						</ul>
						<div class="tab-content" id="myTabContent">
							<div class="tab-pane fade show active" id="subGeneral" role="tabpanel" aria-labelledby="generalTab">
								
								<div class="form-group row">
									<label for="type" class="col-3 col-form-label">Activity Type</label>
									<div class="col-9">
										<div class="input-group">
											<select class="custom-select" ng-disabled="activityTypereadOnly" ng-model="activityType">
												<option ng-repeat="x in ActivityTypeMaster" value="{{x.value}}">{{x.name}}</option>
											</select>
										</div>
									</div>
								</div>
                                                            
                                                                <div class="form-group row">
								<label for="activityLevel" class="col-3 col-form-label">Level</label>
								<div class="col-9">
									<div class=" input-group">
										<select class="custom-select" id="activityLevel" ng-disabled="levelreadOnly" ng-model="level">
											<option ng-repeat="x in levels" value="{{x.value}}">{{x.name}}</option>
										</select>
									</div>
								</div>
							</div>
                                                            
								<div class="form-group row">
									<label for="participate" class="col-3 col-form-label">Participation</label>
									<div class="col-9">
										<div class="input-group">
											<select class="custom-select" ng-disabled="participatereadOnly" id="participate" ng-model="participate">
												<option ng-repeat="x in participation" value="{{x.value}}">{{x.name}}</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="activityResult" class="col-3 col-form-label">Result</label>
									<div class=" input-group col-9">
										<input id="activityResult" ng-readonly="resultreadOnly" ng-model="result" class="form-control">
									</div>
								</div>
							</div>
							<div class="tab-pane fade" id="subOthers" role="tabpanel" aria-labelledby="subOthers">
							<div class="form-group row">
								<label for="activityDate" class="col-3 col-form-label">Event Date</label>
								<div class="col-9">
									<div class="input-group">
										<input id="activityDate" ng-readonly="datereadOnly" ng-model="date" class="form-control">
										<div class="input-group-append">
											<button type="button" id="activityDateShow" ng-disabled="datereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i>
											</button>
										</div>
									</div>
								</div>
							</div>
                                                            
                                                            
                                                            
							<div class="form-group row">
								<label for="activityDueDate" class="col-3 col-form-label">Participation Due Date</label>
								<div class="col-9">
									<div class="input-group">
										<input id="activityDueDate" ng-readonly="datereadOnly" ng-model="dueDate" class="form-control">
										<div class="input-group-append">
											<button type="button" id="activityDueDateShow" ng-disabled="datereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i>
											</button>
										</div>
									</div>
								</div>
							</div>
							
                                                         <div class="form-group row">
								<label for="activityConfirmationDate" class="col-3 col-form-label">Confirmation Date</label>
								<div class="col-9">
									<div class="input-group">
										<input id="activityConfirmationDate" ng-readonly="datereadOnly" ng-model="confirmationDate" class="form-control">
										<div class="input-group-append">
											<button type="button" id="activityConfirmationDateShow" ng-disabled="datereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i>
											</button>
										</div>
									</div>
								</div>
							</div>   
                                                            
							<div class="form-group row">
								<label for="activityVenue" class="col-3 col-form-label">Venue</label>
								<div class="col-9">
									<div class="input-group">
										<!--<select class="custom-select" ng-disabled="venuereadOnly" id="activityVenue" ng-model="venue">
											<option ng-repeat="x in classes" value="{{x}}">{{x}}</option>
										</select>-->
                                                                                
                                        <textarea class="form-control" ng-disabled="venuereadOnly" id="class" ng-model="venue"></textarea>
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
										<li class="nav-item"> <a class="nav-link active" data-toggle="tab" href="#maker" role="tab" aria-selected="true">Maker</a>
										</li>
										<li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#checker" role="tab" aria-selected="false">Checker</a>
										</li>
										<li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#status" role="tab" aria-selected="false">Status</a>
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
						<div id="searchBody" ng-controller="StudentNamesearch"></div>
						<div id="searchBody" ng-controller="OtherActivitySearch"></div>
					</div>
					<footer class="subscreenFooter" ng-show="!searchShow">
						<nav class="nav nav-pills nav-justified"> <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Master</a>
							<a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Details</a>
							<a id="AuditFooter" class="cohesiveFooter_navitem footer_color" href="#">Audit</a>
						</nav>
					</footer>
					<div id="snackbar">
						<div id="spinner"></div>
					</div>
		</body>

		</html>