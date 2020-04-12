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
                        <script type="text/javascript" src="/js/Utils/EnrollOperation.js"></script>
			<script type="text/javascript" src="/js/Utils/search.js"></script>
			<script type="text/javascript" src="/js/Utils/util.js"></script>
			<script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
			<script src="/js/Utils/date_picker.js"></script>
			<!--Js Framework Ends-->
			<script src="/js/EndPointVerification.js"></script>
		</head>

		<body id="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
			<% response.setHeader( "Cache-Control", "no-cache,no-store,must-revalidate"); response.setHeader( "Pragma", "no-cache"); //Http 1.0 response.setHeader( "Expires", "-1"); //Proxies response.setHeader( "X-XSS-Protection", "1;mode=block"); response.setHeader( "X-Frame-Options", "SAMEORIGIN"); response.setHeader( "Content-Security-Policy", "default-src 'self';img-src 'self' data:;script-src  'self';style-src 'unsafe-inline'  'self';base-uri 'none';form-action 'none';frame-ancestors 'self'"); %>
				<header id="subscreenHeader" class="subscreenHeader mb-3">
					<div id="subscreenHeading" class="ssHeading" ng-show="!searchShow">
						<h6 align="center">End Point verification</h6>
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
									<label for="endPoint" class="col-3 col-form-label">End Point</label>
									<div class="col-9">
										<div class="input-group">
											<input id="endPoint" type="text" ng-readonly="endPointreadOnly" ng-model="endPoint" class="form-control">
										</div>
									</div>
								</div>
					</div>
					
						
						<div id="searchBody" ng-controller="StudentEndPointsearch"></div>
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