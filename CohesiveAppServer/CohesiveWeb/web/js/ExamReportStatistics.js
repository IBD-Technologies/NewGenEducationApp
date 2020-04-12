/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
    Author     : Munish Kumar B
	IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService) {
	// Specific Screen Scope Starts
    $scope.teacherName = null;
	$scope.teacherID = null;
	$scope.TeacherMaster = [{
		TeacherId: null,
		TeacherName: null
	}];
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.selectedIndex =0;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = true;
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
	MenuName = "ExamReportStatistics";
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive View Framework Starts
function fnExamReportStatisticsView() {
	var emptyExamReportStatistics = {
		Master: {
		    teacherName: null,
			teacherID: null
			},
		Report: [{
				Content:""
			}],
		error: {}
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyExamReportStatistics;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel.teacherName = $scope.teacherName;
	dataModel.teacherID = $scope.teacherID;
	dataModel.content = $scope.content;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ExamReportStatistics', 'View', dataModel, [$scope.teacherID,$scope.teacherName], $scope);
	if (response.header.status == 'success') {
		// Screen Specific Scope Starts		
	    $scope.teacherNamereadOnly = true;
	    $scope.teacherIDreadOnly = true;
		// Screen Specific Scope Ends	
		// Generic Field Starts	
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.operation = "View";
		// Generic Field Ends	
		return true;
	} else {
		return false;
	}
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnExamReportStatisticsMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					case 'View':
			if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
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
function fnExamReportStatisticsDefaultandValidate(operation) {
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
	angular.forEach(Institute.TeacherMaster, function (value, key) {
		if (this[key].TeacherName == $scope.teacherName) {
			$scope.teacherID = this[key].TeacherId;
			availabilty = true;
			return true;
		}
	}, Institute.TeacherMaster);
	if (!availabilty) {
		fn_Show_Exception_With_Param('FE-VAL-002', ['Teacher Name']);
		return false;
	}
	return true;
}
//Screen Specific Default Validation Ends
// Screen Specific Mandatory Validation Ends
function fnExamReportStatisticsBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.teacherID = null;
		$scope.teacherName = null;
		if($scope.operation== "View")
		{	
	    $scope.teacherNamereadOnly = false;
	    $scope.teacherIDreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $$scope.teacherNamereadOnly = true;
	    $scope.teacherIDreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
        // Generic Scope Ends	
		
}
// Cohesive Create Framework Ends