/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.tableName = "";
	$scope.queryParam = "";
	/*$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];*/
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.queryParamreadOnly = false;
	$scope.tableNamereadOnly = false;
        $scope.ReportPath="";
        	// Screen Specific Scope Ends 
	$scope.fnTableSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Table';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}


});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "TableReport";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "TableReport";
	window.parent.fn_hide_parentspinner();
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive View Framework Starts
function fnTableReportView() {
	var emptyTableReport = {
		Master: {
			tableName: "",
			queryParam: ""
		},
		ReportPath:""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTableReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.tableName != null)
		dataModel.Master.tableName = $scope.tableName;
        if ($scope.queryParam != null)
		dataModel.Master.queryParam = $scope.queryParam;
	    
	// Screen Specific DataModel Ends
	/*var response = fncallBackend('TableReport', 'View', dataModel, [{
		entityName: "TableName",
		entityValue: $scope.tableName
	}], $scope);*/
    if ($scope.queryParam!=null && $scope.queryParam!="")
     var frameSrc=ReportSrc+$scope.tableName+".rptdesign"+"&"+$scope.queryParam+"&"+"nokotser="+window.parent.nokotser+"&"+"userID="+window.parent.User.Id+"&"+"loginInstitute="+window.parent.Institute.ID+"&"+"service="+"TableReport";
    else
     var frameSrc=ReportSrc+$scope.tableName+".rptdesign"+"&"+"nokotser="+window.parent.nokotser+"&"+"userID="+window.parent.User.Id+"&"+"loginInstitute="+window.parent.Institute.ID+"&"+"service="+"TableReport";
         
    $("#reportFrame").attr("src",frameSrc);
    $scope.mastershow = false;
    $scope.detailshow = true;
		
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnTableReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
	       case 'View':
			if ($scope.tableName == '' || $scope.tableName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Table Name']);
				return false;
			}
			break;
		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnStudent360DegreeReportDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			/*if (!fnDefaultStudentId($scope))
				return false;*/

			break;

		case 'Save':
			/*if (!fnDefaultStudentId($scope))
				return false;*/

			break;


	}
	return true;
}



// Screen Specific Mandatory Validation Ends
function fnTableReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.tableName = "";
	$scope.queryParam = "";
	if ($scope.operation == "View") {
		$scope.queryParamreadOnly = false;
		$scope.tableNamereadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
	} else {
		$scope.queryParamreadOnly = true;
		$scope.tableNamereadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	// Generic Scope Ends	
$("#reportFrame").attr("src","");
}
// Cohesive Create Framework Ends

function fnTableReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.queryParamreadOnly = true;
		$scope.tableNamereadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.tableName = response.body.studentName;
		$scope.queryParam = response.body.studentID;
		$scope.ReportPath = response.body.ReportPath;
                $("#reportFrame").attr("src","/CohesiveUpload"+$scope.ReportPath);
                //fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.ReportPath);
	        
                return true;

	}
        
}