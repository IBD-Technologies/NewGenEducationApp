//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//Generic Field Starts
	
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field 
	//Screen Specific Scope starts
	$scope.batchName = "";
	$scope.batchDescription = "";
	$scope.layer = 'Select option';
	$scope.executionFrequency = 'Select option';
	$scope.eod = "Select option";
	$scope.intraDay = "";
	$scope.numberOfThreads = "";
	$scope.ChildNumberOfThreads = "";
	$scope.Frequency = Institute.NotificationFrequency;
	$scope.Batch = Institute.BatchMaster;
	$scope.batchNameReadOnly = true;
	$scope.batchDescriptionreadOnly = true;
	$scope.executionFrequencyreadOnly = true;
	$scope.layerreadOnly = true;
	$scope.eodReadOnly = true;
	$scope.intraDayReadOnly = true;
	$scope.numOfThreadsReadOnly = true;
	$scope.ChildNumOfThreadsReadOnly = true;
        $scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes = OperationScopes;
	//Screen Specific Scope Ends
	
	
	$scope.BatchNameMaster = [{
		BatchName: "",
		BatchDescription: ""
	}];
	$scope.fnBatchNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		
		
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Batch';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

});
//--------------------------------------------------------------------------------------------------------------

//Defaault Load Record Starts
$(document).ready(function () {
	MenuName = "BatchConfiguration";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Batch";
        window.parent.fn_hide_parentspinner();
        selectBoxes = ['batchLayer', 'ExecutionFrequency'];
          fnGetSelectBoxdata(selectBoxes);
});
//Default Load Record Ends

//Cohesive Query Framework Starts
function fnBatchConfigurationQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.batchName = "";
	$scope.batchDescription = "";
	$scope.layer = '';
	$scope.executionFrequency = "";
	$scope.eod = "";
	$scope.intraDay = "";
	$scope.numberOfThreads = "";
	$scope.ChildNumberOfThreads = "";
	$scope.PayMentMaster = Institute.PayMentMaster;
	$scope.batchNameReadOnly = false;
	$scope.batchDescriptionreadOnly = true;
	$scope.layerreadOnly = true;
	$scope.executionFrequencyreadOnly = true;
	$scope.eodReadOnly = true;
	$scope.intraDayReadOnly = true;
	$scope.numOfThreadsReadOnly = true;
	$scope.ChildNumOfThreadsReadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	//Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnBatchConfigurationView() {
	var emptyBatchConfiguration = {
		batchName: "",
		batchDescription: "",
		layer: '',
		executionFrequency: "",
		eod: "",
		intraDay: "",
		numberOfThreads: "",
		ChildNumberOfThreads: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyBatchConfiguration;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.batchName!=null)
	dataModel.batchName = $scope.batchName;
	//Screen Specific DataModel Ends 
	var response = fncallBackend('BatchConfiguration', 'View', dataModel, [{entityName:"batchName",entityValue:$scope.batchName}], $scope);
	return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnBatchConfigurationMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.batchName == '' || $scope.batchName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Batch Name']);
				return false;
			}
			break;

		case 'Save':
			if ($scope.batchName == '' || $scope.batchName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Batch Name']);
				return false;
			}
			if ($scope.batchDescription == '' || $scope.batchDescription == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Batch Description']);
				return false;
			}

			if ($scope.layer == '' || $scope.layer == null || $scope.layer == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Layer']);
				return false;
			}
			if ($scope.executionFrequency == '' || $scope.executionFrequency == null || $scope.executionFrequency == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Execution Frequecy']);
				return false;
			}
			if ($scope.eod == '' || $scope.eod == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['EOD']);
				return false;
			}
			if ($scope.intraDay == '' || $scope.intraDay == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Intra Day']);
				return false;
			}
			if ($scope.numberOfThreads == '' || $scope.numberOfThreads == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Number of Threads']);
				return false;
			}
			if ($scope.ChildNumberOfThreads == '' || $scope.ChildNumberOfThreads == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', [' Child Number of Threads']);
				return false;
			}
			break;
	}
	return true;
}
//Screen Specific Default Validation Ends
//Screen Specific Default Validation Starts
function fnBatchConfigurationDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			return true;

			break;

		case 'Save':

			return true;

			break;


	}
	return true;
}




function fnBatchConfigurationDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
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

//Screen Specific Default Validation Ends
//Cohesive Query Framework Starts
function fnBatchConfigurationCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Start
	$scope.batchName = "";
	$scope.batchDescription = "";
	$scope.layer = "";
	$scope.executionFrequency = "";
	$scope.eod = "";
	$scope.intraDay = "";
	$scope.numberOfThreads = "";
	$scope.ChildNumberOfThreads = "";
	$scope.batchNameReadOnly = false;
	$scope.batchDescriptionreadOnly = false;
	$scope.executionFrequencyreadOnly = false;
	$scope.layerreadOnly = false;
	$scope.eodReadOnly = false;
	$scope.intraDayReadOnly = false;
	$scope.numOfThreadsReadOnly = false;
	$scope.ChildNumOfThreadsReadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive Edit Framework Starts
function fnBatchConfigurationEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.batchNameReadOnly = false;
	$scope.batchDescriptionreadOnly = false;
	$scope.executionFrequencyreadOnly = false;
	$scope.layerreadOnly = false;
	$scope.eodReadOnly = false;
	$scope.intraDayReadOnly = false;
	$scope.numOfThreadsReadOnly = false;
	$scope.ChildNumOfThreadsReadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
function fnBatchConfigurationDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.batchNameReadOnly = true;
	$scope.batchDescriptionreadOnly = true;
	$scope.executionFrequencyreadOnly = true;
	$scope.layerreadOnly = true;
	$scope.eodReadOnly = true;
	$scope.intraDayReadOnly = true;
	$scope.numOfThreadsReadOnly = true;
	$scope.ChildNumOfThreadsReadOnly = true;
	//Screen Specific Scope End
	return true;
}
//Cohesive Edit framework Ends
//Cohesive AuthoriZation framework Starts
function fnBatchConfigurationAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.batchNameReadOnly = true;
	$scope.executionFrequencyreadOnly = true;
	$scope.batchDescriptionreadOnly = true;
	$scope.layerreadOnly = true;
	$scope.eodReadOnly = true;
	$scope.intraDayReadOnly = true;
	$scope.numOfThreadsReadOnly = true;
	$scope.ChildNumOfThreadsReadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive AuthoriZation framework Ends
//Cohesive Reject framework Starts
function fnBatchConfigurationReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.batchNameReadOnly = true;
	$scope.batchDescriptionreadOnly = true;
	$scope.layerreadOnly = true;
	$scope.executionFrequencyreadOnly = true;
	$scope.eodReadOnly = true;
	$scope.intraDayReadOnly = true;
	$scope.numOfThreadsReadOnly = true;
	$scope.ChildNumOfThreadsReadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject framework Ends
//Cohesive Back framework Starts
function fnBatchConfigurationBack() {
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.batchName = "";
	        $scope.batchDescription = "";
	        $scope.layer = "";
	$scope.executionFrequency = "";
	$scope.eod = "";
	$scope.intraDay = "";
	$scope.numberOfThreads = "";
	$scope.ChildNumberOfThreads = "";
	}
	$scope.batchNameReadOnly = true;
	$scope.batchDescriptionreadOnly = true;
	$scope.layerreadOnly = true;
	$scope.executionFrequencyreadOnly = true;
	$scope.eodReadOnly = true;
	$scope.intraDayReadOnly = true;
	$scope.numOfThreadsReadOnly = true;
	$scope.ChildNumOfThreadsReadOnly = true;
	//Screen Specific Scope Ends
	// Generic Field Starts
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
}
//Cohesive Back framework Ends
//Cohesive Save framework Starts
function fnBatchConfigurationSave() {
	var emptyBatchConfiguration = {
		batchName: "",
		batchDescription: "",
		layer: '',
		executionFrequency: "",
		eod: "",
		intraDay: "",
		numberOfThreads: "",
		ChildNumberOfThreads: "",
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyBatchConfiguration;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.batchName!=null)
	dataModel.batchName = $scope.batchName;
        if($scope.batchDescription!=null)
	dataModel.batchDescription = $scope.batchDescription;
        if($scope.layer!=null)
	dataModel.layer = $scope.layer;
        if($scope.executionFrequency!=null)
	dataModel.executionFrequency = $scope.executionFrequency;
        if($scope.eod!=null)
	dataModel.eod = $scope.eod;
        if($scope.intraDay!=null)
	dataModel.intraDay = $scope.intraDay;
        if($scope.numberOfThreads!=null)
	dataModel.numberOfThreads = $scope.numberOfThreads;
        if($scope.ChildNumberOfThreads!=null)
	dataModel.ChildNumberOfThreads = $scope.ChildNumberOfThreads;
	//Screen Specific DataModel Ends
	var response = fncallBackend('BatchConfiguration', parentOperation, dataModel, [{entityName:"batchName",entityValue:$scope.batchName}], $scope);
	return true;
}
//Cohesive Save framework Ends

function fnBatchConfigurationpostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.batchNameReadOnly = true;
	        $scope.batchDescriptionreadOnly = true;
	        $scope.layerreadOnly = true;
	        $scope.executionFrequencyreadOnly = true;
	        $scope.eodReadOnly = true;
	        $scope.intraDayReadOnly = true;
	        $scope.numOfThreadsReadOnly = true;
	        $scope.ChildNumOfThreadsReadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.batchName = "";
		$scope.batchDescription ="";
		$scope.layer ="";
		$scope.eod ="";
		$scope.intraDay ="";
                $scope.ChildNumberOfThreads="";
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                 $scope.batchName = response.body.batchName;
		$scope.batchDescription = response.body.batchDescription;
		$scope.layer = response.body.layer;
		$scope.eod = response.body.eod;
		$scope.intraDay = response.body.intraDay;
		$scope.numberOfThreads = response.body.numberOfThreads;
		$scope.ChildNumberOfThreads = response.body.ChildNumberOfThreads;
                }
		return true;

}
}





