/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes,TableViewCallService) {
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
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
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = false;
        $scope.ReportPath="";
        	// Screen Specific Scope Ends 
	$scope.fnStudentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Student';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}


});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "Student360DegreeReport";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "StudentReport";
	window.parent.fn_hide_parentspinner();
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive View Framework Starts
function fnStudent360DegreeReportView() {
	var emptyStudent360DegreeReport = {
		Master: {
			studentName: "",
			studentID: ""
		},
		ReportPath:""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudent360DegreeReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.studentID != null)
		dataModel.studentID = $scope.studentID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('Student360DegreeReport', 'View', dataModel, [{
		entityName: "studentID",
		entityValue: $scope.studentID
	}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudent360DegreeReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
	       case 'View':
                   if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
				return false;
			}   
			/*if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}*/
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
			if (!fnDefaultStudentId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultStudentId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultStudentId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnStudent360DegreeReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	if ($scope.operation == "View") {
		$scope.studentIDreadOnly = false;
		$scope.studentNamereadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
	} else {
		$scope.studentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	// Generic Scope Ends	

}
// Cohesive Create Framework Ends

function fnStudent360DegreeReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.studentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
		$scope.ReportPath = response.body.ReportPath;
                
                fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.ReportPath);
	        
                return true;

	}
        
}