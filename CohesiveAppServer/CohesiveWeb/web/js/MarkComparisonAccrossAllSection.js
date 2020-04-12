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
    $scope.standard = "Select option";
	$scope.section = "Select option";
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.selectedIndex =0;
	 $scope.Class = Institute.StandardMaster;
	 $scope.ClassSection = ["Select option"]; // Std/sec
	 fnSelectResponseHandler(selectBoxes); //Select Box change
	// Generic Field Ends
	// Screen Specific Scope Start
	 	$scope.standardreadOnly = false;
		$scope.sectionreadOnly = false;
	// Screen Specific Scope Ends 
	//Search Level Scope Starts
	//Search Level Scope Ends
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "ComparisonAccrossAllSection";
	selectBoxes= ['standardID','Section'];
    fnSelectBoxDefault(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive View Framework Starts
function fnComparisonAccrossAllSectionView() {
	var emptyComparisonAccrossAllSection = {
		Master: {
		    standard: "Select option",
			section: "Select option"
			},
		Report: [{
				Content:""
			}],
		error: {}
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyComparisonAccrossAllSection;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel.standard = $scope.standard;
	dataModel.section = $scope.section;
	dataModel.content = $scope.content;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ComparisonAccrossAllExam', 'View', dataModel, [$scope.standard,$scope.section], $scope);
	if (response.header.status == 'success') {
		// Screen Specific Scope Starts		
		$scope.standardreadOnly = true;
		$scope.sectionreadOnly = true;
		// Screen Specific Scope Ends	
		// Generic Field Starts	
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.operation = "View";
		 fnSelectResponseHandler(selectBoxes); //Select Box change
		// Generic Field Ends	
		return true;
	} else {
		return false;
	}
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnComparisonAccrossAllSectionMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					case 'View':
			if ($scope.standard == '' || $scope.standard == null || $scope.standard == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Standard']);
				return false;
			}
			if ($scope.section == '' || $scope.section == null || $scope.section == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Section']);
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
function fnComparisonAccrossAllSectionDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			return true
			break;

		case 'Save':
				return true;

			break;


	}
	return true;
}

//Screen Specific Default Validation Ends
// Screen Specific Mandatory Validation Ends
function fnComparisonAccrossAllSectionBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.standard = "Select option";
		$scope.section = "Select option";
		if($scope.operation== "View")
		{	
	   	$scope.standardreadOnly = false;
		$scope.sectionreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	     $scope.standardreadOnly = true;
		 $scope.sectionreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
        // Generic Scope Ends	
		
}
// Cohesive Create Framework Ends