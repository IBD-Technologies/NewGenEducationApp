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

var app = angular.module('SubScreen', ['BackEnd', 'BatchOperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService) {
	// Specific Screen Scope Starts
	$scope.instituteName = null;
	$scope.instituteID = null;
	$scope.batchName = null;
	$scope.batchDescription = null;
	$scope.InstituteMaster = [{
		InstituteId: null,
		InstituteName: null
	}];
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = false;
	// Screen Specific Scope Ends 
	// multiple View Starts
	$scope.BatchMonitoringCurPage = 0;
	$scope.BatchMonitoringTable = null;
	$scope.BatchMonitoringShowObject = null;
	// multiple View Ends
	$scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Institute';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
	$scope.BatchNameMaster = [{
		BatchName: null,
		BatchDescription: null
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

	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'BatchMonitoring') {
			if ($scope.BatchMonitoringTable != null && $scope.BatchMonitoringTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.BatchMonitoringCurPage;
				lsvwObject.tableObject = $scope.BatchMonitoringTable;
				lsvwObject.screenShowObject = $scope.BatchMonitoringShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.BatchMonitoringCurPage = lsvwObject.curPage;
				$scope.BatchMonitoringTable = lsvwObject.tableObject;
				$scope.BatchMonitoringShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'BatchMonitoring') {
			if ($scope.BatchMonitoringTable != null && $scope.BatchMonitoringTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.BatchMonitoringCurPage;
				lsvwObject.tableObject = $scope.BatchMonitoringTable;
				lsvwObject.screenShowObject = $scope.BatchMonitoringShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.BatchMonitoringCurPage = lsvwObject.curPage;
				$scope.BatchMonitoringTable = lsvwObject.tableObject;
				$scope.BatchMonitoringShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'BatchMonitoring') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					id:"",
				    total:"",
				    success: "",
				    error:"",
				    message:""
				};
				if ($scope.BatchMonitoringTable == null)
					$scope.BatchMonitoringTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.BatchMonitoringCurPage;
				lsvwObject.tableObject = $scope.BatchMonitoringTable;
				lsvwObject.screenShowObject = $scope.BatchMonitoringShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.BatchMonitoringCurPage = lsvwObject.curPage;
				$scope.BatchMonitoringTable = lsvwObject.tableObject;
				$scope.BatchMonitoringShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'BatchMonitoring') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.BatchMonitoringCurPage;
				lsvwObject.tableObject = $scope.BatchMonitoringTable;
				lsvwObject.screenShowObject = $scope.BatchMonitoringShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.BatchMonitoringCurPage = lsvwObject.curPage;
				$scope.BatchMonitoringTable = lsvwObject.tableObject;
				$scope.BatchMonitoringShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'BatchMonitoring') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.BatchMonitoringCurPage;
			lsvwObject.tableObject = $scope.BatchMonitoringTable;
			lsvwObject.screenShowObject = $scope.BatchMonitoringShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'BatchMonitoring') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.BatchMonitoringCurPage;
			lsvwObject.tableObject = $scope.BatchMonitoringTable;
			lsvwObject.screenShowObject = $scope.BatchMonitoringShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'BatchMonitoring') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.BatchMonitoringTable);
			
		}
	};
	//Multiple View Scope Function ends 
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "BatchMonitoring";
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive View Framework Starts
function fnBatchMonitoringView() {
	var emptyBatchMonitoring = {
		    instituteID: null,
			instituteName: null,
			batchName: null,
			batchDescription: null,
		    batchStatus: [{
				id:"",
				total:"",
				success:"",
				error:"",
				message:""
			}],
		error: {}
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyBatchMonitoring;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel.instituteID = $scope.instituteID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('BatchMonitoring', 'View', dataModel, [$scope.instituteID], $scope);
	if (response.header.status == 'success') {
		// Screen Specific Scope Starts		
	    $scope.instituteIDreadOnly = true;
	    $scope.instituteNamereadOnly = false;
	    $scope.batchNameReadOnly = false;
	    $scope.batchDescriptionreadOnly = true;
		
		// Screen Specific Scope Ends	
		// Generic Field Starts	
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.operation = "View";
		$scope.selectedIndex =0;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		
		//Multiple View Response Starts 
		$scope.BatchMonitoringTable=fnConvertmvw('BatchMonitoringTable',response.body.batchStatus);
		$scope.BatchMonitoringCurPage = 1
		$scope.BatchMonitoringShowObject=$scope.fnMvwGetCurPageTable('BatchMonitoring');
		//Multiple View Response Ends 
		
		
		
		
		// Generic Field Ends	
		return true;
	} else {
		return false;
	}
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnBatchMonitoringCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					case 'View':
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

function fnBatchMonitoringDefaultandValidate(operation) {
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
	angular.forEach(Institute.InstituteMaster, function (value, key) {
		if (this[key].InstituteName == $scope.instituteName) {
			$scope.instituteID = this[key].InstituteId;
			availabilty = true;
			return true;
		}
	}, Institute.InstituteMaster);
	if (!availabilty) {
		fn_Show_Exception_With_Param('FE-VAL-002', ['Institute Name']);
		return false;
	}
	return true;
}

function fnBatchMonitoringDefaultandValidate(operation) {
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
	angular.forEach(Institute.BatchNameMaster, function (value, key) {
		if (this[key].BatchName == $scope.batchName) {
			$scope.BatchDescription = this[key].batchDescription;
			availabilty = true;
			return true;
		}
	}, Institute.BatchNameMaster);
	if (!availabilty) {
		fn_Show_Exception_With_Param('FE-VAL-002', ['Batch Name']);
		return false;
	}
	return true;
}

// Screen Specific Mandatory Validation Ends
function fnBatchMonitoringBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.instituteID = null;
		$scope.instituteName = null;
		$scope.batchName = null;
		$scope.batchDescription = null;
		if($scope.operation== "View")
		{	
	    $scope.instituteIDreadOnly = false;
	    $scope.instituteNamereadOnly = false;
	    $scope.batchNameReadOnly = false;
	    $scope.batchDescriptionreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.instituteIDreadOnly = true;
	    $scope.instituteNamereadOnly = true;
	    $scope.batchNameReadOnly = true;
	    $scope.batchDescriptionreadOnly = true;
	       }
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
        // Generic Scope Ends	
}
// Cohesive Create Framework Ends


function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'BatchMonitoringTable':
		   
			var BatchMonitoringTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     BatchMonitoringTable[index] = new Object();
					 BatchMonitoringTable[index].idx=index;
					 BatchMonitoringTable[index].checkBox=false;
					 BatchMonitoringTable[index].id=value.id;
					 BatchMonitoringTable[index].total=value.total;
					 BatchMonitoringTable[index].success=value.success;
					 BatchMonitoringTable[index].error=value.error;
					 BatchMonitoringTable[index].message=value.message;
					}
			return BatchMonitoringTable;
			break ;
		}
	}






