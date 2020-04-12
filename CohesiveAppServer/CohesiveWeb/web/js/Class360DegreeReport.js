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

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.class = 'Select option';
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.Class = Institute.StandardMaster;
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	  $scope.ReportPath="";
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.classReadonly = false;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
	$scope.ClassSection = ["Select option"]; // Std/sec
	// Screen Specific Scope Ends
	$scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Institute';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}


});
//--------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	MenuName = "Class360DegreeReport";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "ClassReport";
	
    selectBoxes = ['class'];
    fnGetSelectBoxdata(selectBoxes);

	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});


function fnClass360DegreeReportpostSelectBoxMaster(){
    
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    $scope.classes=Institute.ClassMaster;  
	window.parent.fn_hide_parentspinner();
        $scope.$apply()
    
    
}




// Cohesive View Framework Starts
function fnClass360DegreeReportView() {
	var emptyClass360DegreeReport = {
		Master: {
			class: 'Select option',
			instituteID: "",
			instituteName: ""
		},
	ReportPath:""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyClass360DegreeReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.class != null)
		dataModel.Master.class = $scope.class;
	if ($scope.instituteID != null)
		dataModel.Master.instituteID = $scope.instituteID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('Class360DegreeReport', 'View', dataModel, [{
		entityName: "class",
		entityValue: $scope.class
	}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnClass360DegreeReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
			if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
			break;
		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnClass360DegreeReportDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultInstituteId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultInstituteId($scope))
				return false;

			break;
	}
	return true;
}

function fnDefaultInstituteId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
//Cohesive Back Framework Starts
function fnClass360DegreeReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.class = 'Select option';
	if ($scope.operation == "View") {
		$scope.classReadonly = false;
		$scope.instituteIDreadOnly = false;
		$scope.instituteNamereadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
	} else {
		$scope.classReadonly = true;
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	// Generic Scope Ends	

}
// Cohesive Back Framework Ends
function fnClass360DegreeReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.classReadonly = true;
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.class = response.body.class;
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
	    $scope.ReportPath = response.body.ReportPath;
        fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.ReportPath);
		return true;

	}
}