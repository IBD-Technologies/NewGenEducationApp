<%-- Document : StudentPayment Created on : Jul 18, 2019, 8:11:17 PM Author : IBD Technologies --%>
	<%@page session="false" contentType="text/html" pageEncoding="UTF-8" %>
	 <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
                        <link rel ="stylesheet" href="/css/utils/TableView.css">
			<!-- Css Framework Library Ends--->
			<!-- Js Framework Library Starts--->
			<script type="text/javascript" src="/js/Utils/config.js"></script>
			<script type="text/javascript" src="/js/Utils/Exception.js"></script>
			<script type="text/javascript" src="/js/Utils/backEnd.js"></script>
                        <script type="text/javascript" src="/js/Utils/StudentView.js"></script>
                     <%--<c:forEach items="${cookie}" var="currentCookie">  
                          <c:if test="${currentCookie.key=='userType'}">
                <c:if test="${currentCookie.value.value=='P'}">
                    <script type="text/javascript" src="/js/Utils/StudentView.js"></script>
                </c:if>
                     <c:if test="${currentCookie.value.value=='T'}">
                    <script type="text/javascript" src="/js/Utils/Operation.js"></script>
                </c:if>
                <c:if test="${currentCookie.value.value=='A'}">
                    <script type="text/javascript" src="/js/Utils/Operation.js"></script>
                </c:if>
            </c:if> 


    </c:forEach>  --%>
    
			
			<script type="text/javascript" src="/js/Utils/search.js"></script>
			<script type="text/javascript" src="/js/Utils/util.js"></script>
			<script type="text/javascript" src="/js/Utils/SelectBox.js"></script>
                        <script type ="text/javascript" src="/js/Utils/TableView.js"></script>
			<script src="/js/Utils/date_picker.js"></script>
			<!--Js Framework Library Ends--->
			<script src="/js/StudentPayment.js"></script>
		</head>

		<body id="SubScreenCtrl" class="cohesive_body" ng-app="SubScreen" ng-Init="searchShow=false" ng-controller="SubScreenCtrl">
			<% response.setHeader( "Cache-Control", "no-cache,no-store,must-revalidate"); response.setHeader( "Pragma", "no-cache"); //Http 1.0 response.setHeader( "Expires", "-1"); //Proxies response.setHeader( "X-XSS-Protection", "1;mode=block"); response.setHeader( "X-Frame-Options", "SAMEORIGIN"); response.setHeader( "Content-Security-Policy", "default-src 'self';img-src 'self' data:;script-src  'self';style-src 'unsafe-inline'  'self';base-uri 'none';form-action 'none';frame-ancestors 'self'"); %>
				<header id="subscreenHeader" class="subscreenHeader mb-3">
					<div id="subscreenHeading" class="ssHeading" ng-show="!searchShow">
						<h6 align="center">Payment</h6>
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
							<label for="studID" class="col-3 col-form-label">ID</label>
							<div class="col-9">
								<div class="input-group">
									<input id="studName" type="text" ng-readonly="studentIDreadOnly" ng-model="studentID" class="form-control">
								</div>
							</div>
						</div>
                                            <div class="form-group row">
							<label for="studPayment" class="col-3 col-form-label">Payment ID</label>
							<div class="col-9">
								<div class="input-group">
									<input id="studPayment" type="text" ng-readonly="paymentIDreadOnly" ng-model="paymentID" class="form-control">
									<div class="input-group-append">
										<button type="button" class="btn btn-primary" ng-disabled="paymentIDreadOnly" ng-click="fnPaymentSearch()"><i class="fas fa-search"></i>
										</button>
									</div>
								</div>
							</div>
						</div>
                                            
                                             <div class="form-group row">
                            <label for="paymentDate" class="col-3 col-form-label">Date</label>
                            <div class=" input-group col-9">
                                <input id="paymentDate" ng-readonly="paymentDatereadOnly" ng-model="paymentDate" class="form-control">
                                <div class="input-group-append">
                                    <button type="button" id="paymentDateShow" ng-disabled="paymentDatereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i></button>
                                </div>
                            </div>
                        </div>
                                            
                     <div class="form-group row">
                            <label for="feePaid" class="col-3 col-form-label">Payment Amount</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><i class="fas fa-rupee-sign"></i></span>
                                    </div>
                                    <input id="feePaid" ng-readonly="paymentPaidreadOnly" ng-model="paymentPaid" class="form-control currency">
                                </div>
                            </div>
                        </div>       
                            
                            
                            <div class="form-group row">
                            <label for="paymentmode" class="col-3 col-form-label">Payment Mode</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <select class="custom-select" ng-disabled="paymentModereadOnly" id="paymentMode" ng-model="paymentMode">
                                        <option ng-repeat="x in payment" value="{{x.value}}">{{x.name}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                            
                            
                            
                            <div class="form-group row">
                            <label for="balanceAmount" class="col-3 col-form-label">Balance Amount</label>
                            <div class="col-9">
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><i class="fas fa-rupee-sign"></i></span>
                                    </div>
                                    <input id="balanceAmount"  ng-model="balanceAmount" class="form-control currency" readonly>
                                </div>
                            </div>
                        </div>
                                            
                                            
                                            
					</div>
					<div id="detailSection" ng-show="!searchShow&&detailshow">
						<!--<ul class="nav nav-tabs mb-4 " id="detailSubTab" role="tablist">
							<li class="nav-item cohesiveTabPadding"> <a class="nav-link active" id="feeTab" data-toggle="tab" href="#fee" role="tab" aria-selected="true">Fee</a>
							</li>
							<li class="nav-item cohesiveTabPadding"> <a class="nav-link" id="paymentTab" data-toggle="tab" href="#payment" role="tab" aria-selected="false">Payment</a>
							</li>
						</ul>-->
						<!--<div class="tab-content" id="detailSubTab">
							<div class="tab-pane fade show active " id="fee" role="tabpanel" aria-labelledby="feeTab">
								<div class="form-group row">
									<label for="paymentID" class="col-3 col-form-label">Fee ID</label>
									<div class="col-9">
										<div class="input-group">
											<input id="paymentID" type="text" ng-readonly="feeIDreadOnly" ng-model="feeID" class="form-control">
											<div class="input-group-append">
												<button type="button" ng-disabled="feeIDreadOnly" class="btn btn-primary"  ng-click="fnFeeSearch()"><i class="fas fa-search"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="feeType" class="col-3 col-form-label">Fee Type</label>
									<div class="col-9">
										<div class="input-group">
											<select class="custom-select" ng-disabled="feeTypereadOnly" id="feeType" ng-model="feeType">
												<option ng-repeat="x in fees" value="{{x}}">{{x}}</option>
											</select>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="feeAmount" class="col-3 col-form-label">Fee Amount</label>
									<div class="col-9">
										<div class="input-group">
											<div class="input-group-prepend"> <span class="input-group-text"><i class="fas fa-rupee-sign"></i></span>
											</div>
											<input id="feeAmount" ng-readonly="amountreadOnly" ng-model="amount" class="form-control">
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane fade " id="payment" role="tabpanel">
								<div class="form-group row">
									<label for="paymentDate" class="col-3 col-form-label">Date</label>
									<div class=" input-group col-9">
										<input id="paymentDate" ng-readonly="paymentDatereadOnly" ng-model="paymentDate" class="form-control">
										<div class="input-group-append">
											<button type="button" id="paymentDateShow" ng-disabled="paymentDatereadOnly" class="btn btn-primary"><i class="far fa-calendar-alt"></i>
											</button>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="feePaid" class="col-3 col-form-label">Payment Amount</label>
									<div class="col-9">
										<div class="input-group">
											<div class="input-group-prepend"> <span class="input-group-text"><i class="fas fa-rupee-sign"></i></span>
											</div>
											<input id="feePaid" ng-readonly="paymentPaidreadOnly" ng-model="paymentPaid" class="form-control">
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label for="feeOutstanding" class="col-3 col-form-label">Balance Fee</label>
									<div class="col-9">
									<div class=" input-group">
										<div class="input-group-prepend"> <span class="input-group-text" id="basic-addon1"><i class="fas fa-rupee-sign"></i></span>
										</div>
										<input id="feeOutstanding" ng-readonly="outStandingreadOnly" ng-model="outStanding" class="form-control">
									</div>
								</div>
								</div>
								<div class="form-group row">
									<label for="paymentMode" class="col-3 col-form-label">Payment Mode</label>
									<div class="col-9">
										<div class="input-group">
											<select class="custom-select" ng-disabled="paymentModereadOnly" id="paymentMode" ng-model="paymentMode">
												<option ng-repeat="x in payment" value="{{x.value}}">{{x.name}}</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>-->
                                                
                                                
                                                <div class="text-center contentDetailTab">Payment Details</div>
                  <nav class="navbar navbar-light bg-light contentDetailTabNav">
                     <ul class="pagination pagination-sm   mb-0">
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">{{fnMvwGetCurrentPage('Payment')}}</span></a></li>
                        <li class="page-item"><a  class="page-link page_style svwRecCount"><span class="svwRecCount">of</a></li>
                        <li class="page-item"><a class="page-link page_style svwRecCount">
                           <span class="svwRecCount">{{fnMvwGetTotalPage('Payment')}}</span></a>
                        </li>
                     </ul>
                     <div>
                     </div>
                     <ul class="pagination pagination-sm justify-content-end mb-0">
                        <li class="page-item"><a id="backwardButton" ng-click="fnMvwBackward('Payment',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-left"></i></span></a></li>
                        <li class="page-item"><a id="forwardButton" ng-click="fnMvwForward('Payment',$event)" class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-chevron-right"></i></span></a></li>
                        <li class="page-item"><a id="addButton" ng-click="fnMvwAddRow('Payment',$event)" class="page-link page_style"> <span class="badge badge-light cohesive_badge"><i class="fas fa-plus"></i></span></a></li>
                        <li class="page-item"><a id="deleteButton" ng-click="fnMvwDeleteRow('Payment',$event)"   
                           class="page-link page_style"><span class="badge badge-light cohesive_badge"><i class="fas fa-minus"></i></span></a></li>
                     </ul>
                  </nav>
                  <div class="table-responsive" id="markEntry">
                   <table class="table  cohesive_table table-sm  table-bordered ">
                        <thead align="center">
                           <tr>
						     <th scope="col"></th>
                               
                              <th scope="col">Description</th>
                              <th scope="col">Due Date</th>
                              <th scope="col">Due</th>
                              <th scope="col">Payment Amount</th>
                              
                           </tr>
                        </thead>
						   <tbody id="TTbody" align="center">
                           <tr ng-repeat="X in PaymentShowObject">
						     <td>
							  <input type="checkbox" ng-model="X.checkBox">
							   </td>
                              
                              <td>						  
                                 <input class="form-control-plaintext" ng-model="X.feeDescription" ng-readonly="feeDescriptionreadOnly">
                              </td>
                              <td>
                                 <input  type="text" class="form-control-plaintext" ng-model="X.dueDate"  ng-readonly="dueDatereadOnly" > 
                              </td>
                              
                              
							  <td>
                                 <input  type="text" class="form-control-plaintext" ng-model="X.outStanding"  ng-readonly="outStandingreadOnly" > 
                              </td>
                              <td>
                   
                                  <input  type="text" class="form-control-plaintext" ng-model="X.paymentForFee"  ng-readonly="paymentForTheFeereadOnly" > 
                              </td>
                              
                           </tr>
                        </tbody>
                     </table>
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
					<div id="searchBody" ng-controller="paymentIDsearch"></div>
                                          <div id="searchBody" ng-controller="feeIDsearch">
                    </div>
				</div>
				<footer class="subscreenFooter" ng-show="!searchShow">
					<nav class="nav nav-pills nav-justified"> <a id="masterFooter" class="cohesiveFooter_navitem  footer_color" href="#">Master</a>
						<a id="detailFooter" class="cohesiveFooter_navitem footer_color" href="#">Details</a>
						<a id="AuditFooter" class="cohesiveFooter_navitem footer_color" href="#">Audit</a>
					</nav>
				</footer>
				<div id="snackbar"></div>
				<div id="spinner"></div>
		</body>

		</html>