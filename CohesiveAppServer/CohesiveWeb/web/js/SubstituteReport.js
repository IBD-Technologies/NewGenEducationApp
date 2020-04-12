/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, OperationScopes) {
	// Specific Screen Scope Starts
    $scope.teacherName = "";
	$scope.teacherID = "";
	$scope.date = "";
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	  $scope.ReportPath="";
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.selectedIndex =0;	$scope.OperationScopes = OperationScopes;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.datereadOnly = false;
	// Screen Specific Scope Ends 
	//Search Level Scope Starts
	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	//Search Level Scope Ends
});




//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "SubstituteReport";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="TeacherReport";
        window.parent.fn_hide_parentspinner();
	 fnDatePickersetDefault('date',fndateEventHandler);
         fnsetDateScope();
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Ends
// Cohesive View Framework Starts
function fnSubstituteReportView() {
	var emptySubstituteReport = {
		Master: {
		        teacherName: "",
			teacherID: "",
			date:""
			},
		Report:""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptySubstituteReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.teacherID!=null)
	dataModel.Master.teacherID = $scope.teacherID;
        if($scope.date!=null)
	dataModel.Master.date = $scope.date;
	// Screen Specific DataModel Ends
	var response = fncallBackend('SubstituteReport', 'View', dataModel,  [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnSubstituteReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					case 'View':
			if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			if ($scope.date == '' || $scope.date == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Date']);
				return false;
			}
			break;
		     case 'Detail':
		           return true;
		           break;
	}
	return true;
}

//Screen Specific Default Validation Starts
function fnSubstituteReportDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}
//Screen Specific Default Validation Ends
// Screen Specific Mandatory Validation Ends
function fnSubstituteReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.teacherID = "";
		$scope.teacherName = "";
		$scope.date = "";
		if($scope.operation== "View")
		{	
	    $scope.teacherNamereadOnly = false;
	    $scope.teacherIDreadOnly = true;
		$scope.datereadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.teacherNamereadOnly = true;
	    $scope.teacherIDreadOnly = true;
		$scope.datereadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
        // Generic Scope Ends	
		
}
// Cohesive Create Framework Ends
//DatePicker Function Starts
function fndateEventHandler()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
$scope.date =$.datepicker.formatDate('dd-mm-yy',$( "#date" ).datepicker( "getDate" )),
$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#date").datepicker("getDate"));
		$scope.$apply();
}	
//DatePicker Function  Ends


function fnSubstituteReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.teacherNamereadOnly = true;
	        $scope.teacherIDreadOnly = true;
		$scope.datereadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.teacherName = response.body.teacherName;
		$scope.teacherID = response.body.teacherID;
                $scope.date = response.body.date;
	        $scope.ReportPath = response.body.ReportPath;
		  fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.ReportPath);
		return true;

	}
}