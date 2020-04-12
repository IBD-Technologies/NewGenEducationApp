/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
        $scope.teacherName = "";
	$scope.teacherID = "";
	$scope.TeacherMaster = [{
		TeacherId: "",
		TeacherName: ""
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
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = false;
	  $scope.ReportPath="";
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
	MenuName = "Teacher360DegreeReport";
        window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "TeacherReport";
	window.parent.fn_hide_parentspinner();
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive View Framework Starts
function fnTeacher360DegreeReportView() {
	var emptyTeacher360DegreeReport = {
		Master: {
		        teacherName: "",
			    teacherID: ""
			},
		ReportPath:"" 
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTeacher360DegreeReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if($scope.teacherID!=null)
	dataModel.Master.teacherID = $scope.teacherID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('Teacher360DegreeReport', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
 //if ($scope.queryParam!=null && $scope.queryParam!="")
//     var frameSrc=ReportSrc+"Student360Degree"+".rptdesign"+"&"+"studentId="+$scope.studentID+"&"+"nokotser="+window.parent.nokotser+"&"+"userID="+window.parent.User.Id+"&"+"loginInstitute="+window.parent.Institute.ID+"&"+"service="+"Student360DegreeReport";
   // else
//    $("#reportFrame").attr("src",frameSrc);
//    $scope.mastershow = false;
//    $scope.detailshow = true;
		
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnTeacher360DegreeReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
		        case 'View':
                            if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			/*if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}*/
			break;
		     case 'Detail':
		           return true;
		           break;
	}
	return true;
}

//Screen Specific Default Validation Starts
function fnTeacher360DegreeReportDefaultandValidate(operation) {
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
//Screen Specific Back framework Starts
function fnTeacher360DegreeReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	        $scope.teacherID = "";
		$scope.teacherName = "";
		if($scope.operation== "View")
		{	
	        $scope.teacherNamereadOnly = false;
	        $scope.teacherIDreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	        $scope.teacherNamereadOnly = true;
	        $scope.teacherIDreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
        // Generic Scope Ends	
       $("#reportFrame").attr("src","");
}
//Screen Specific Back framework Ends

function fnTeacher360DegreeReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.teacherNamereadOnly = true;
	        $scope.teacherIDreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.teacherName = response.body.teacherName;
		$scope.teacherID = response.body.teacherID;
		$scope.ReportPath = response.body.ReportPath;
		  fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.ReportPath);
		return true;

	}
}