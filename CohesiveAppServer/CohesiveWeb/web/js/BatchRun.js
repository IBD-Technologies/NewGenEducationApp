/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'BatchRunOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.instituteName = "";
	$scope.instituteID = "";
	$scope.batchName = "";
	$scope.batchDescription = "";
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
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

	$scope.BatchNameMaster = [{
		BatchName: "",
		BatchDescription: ""
	}];
	$scope.fnBatchNameSearch = function () {
		var searchCallInput = {
			mainScope: "",
			searchType: ""
		};


		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Batch';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
          $scope.OperationScopes = OperationScopes;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
	// Screen Specific Scope Ends 


});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "BatchRun";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Batch";
        window.parent.fn_hide_parentspinner();
	//-----------------------screen Specific Default Records--------------------------------------------------	
});
// Cohesive View Framework Starts
function fnBatchRun() {
	var emptyBatchRun = {
		instituteID: "",
		instituteName: "",
		batchName: "",
		batchDescription: "",
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyBatchRun;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('Run', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnBatchRunMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {

		case 'Run':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
			if ($scope.batchName == '' || $scope.batchName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Batch Name']);
				return false;
			}
			break;
		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnBatchRunDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'Run':
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

function fnBatchRunDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'Run':
			if (!fnDefaultBatchName($scope))
				return false;

			break;

		case 'Save':
			return true;
			break;
	}
	return true;
}

function fnDefaultBatchName($scope) {
	var availabilty = false;
	return true;
}
function fnRunconfirmation($scope,BatchRunOperation)
{
	fn_show_confirmation('FE-VAL-031',$scope,'Run');
	//if (!confirmation)
	//return false;
return true;	
}


function fnBatchRunpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.batchNameReadOnly = true;
		$scope.batchDescriptionreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.batchName = response.body.batchName;
		$scope.batchDescription = response.body.batchDescription;
		return true;

	}
}