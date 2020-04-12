/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.authStat = "";
	
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	$scope.AuthType = Institute.AuthStatusMaster;
	
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service
	$scope.OperationScopes = OperationScopes;
	$scope.selectedIndex = 0;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	//Search Level Scope Starts
	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	//Search Level Scope Ends
	// multiple View Starts
	$scope.TeacherTimeTableSummaryCurPage = 0;
	$scope.TeacherTimeTableSummaryTable = null;
	$scope.TeacherTimeTableSummaryShowObject = null;
	// multiple View Ends

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {
		if (tableName == 'TeacherTimeTableSummary') {
			if ($scope.TeacherTimeTableSummaryTable != null && $scope.TeacherTimeTableSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.TeacherTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherTimeTableSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.TeacherTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.TeacherTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'TeacherTimeTableSummary') {
			if ($scope.TeacherTimeTableSummaryTable != null && $scope.TeacherTimeTableSummaryTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.TeacherTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherTimeTableSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.TeacherTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.TeacherTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherTimeTableSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					teacherName: "",
					teacherID: ""
				};
				if ($scope.TeacherTimeTableSummaryTable == null)
					$scope.TeacherTimeTableSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherTimeTableSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.TeacherTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.TeacherTimeTableSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherTimeTableSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherTimeTableSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.TeacherTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.TeacherTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'TeacherTimeTableSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherTimeTableSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherTimeTableSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherTimeTableSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'TeacherTimeTableSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherTimeTableSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherTimeTableSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherTimeTableSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'TeacherTimeTableSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.TeacherTimeTableSummaryTable);

		}
	};

	//Multiple View Scope Function ends 	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "TeacherTimeTableSummary";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "TeacherSummaryEntity";
	window.parent.fn_hide_parentspinner();
	selectBoxes = ['authStatus'];
	fnGetSelectBoxdata(selectBoxes); //Integration changes
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts
function fnTeacherTimeTableSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.authStat = "";
	
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
	$scope.selectedIndex = 0;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.teacherID = $scope.TeacherTimeTableSummaryTable[$scope.selectedIndex].teacherID;

	fninvokeDetailScreen('TeacherTimeTable', lscreenKeyObject, $scope);

	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnTeacherTimeTableSummaryView() {
	var emptyTeacherTimeTableSummary = {
		filter: {
			teacherName: "",
			teacherID: "",
			date: "",
			authStat: ""
		},
		SummaryResult: [{
			teacherName: "",
			teacherID: ""
		}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTeacherTimeTableSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.teacherName != null)
		dataModel.filter.teacherName = $scope.teacherName;
	if ($scope.teacherID != null)
		dataModel.filter.teacherID = $scope.teacherID;
	if ($scope.authStat != null)
		dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('TeacherTimeTableSummary', 'View', dataModel, [{
		entityName: "teacherID",
		entityValue: ""
	}], $scope);
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnTeacherTimeTableSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ((($scope.teacherName == '' || $scope.teacherName == null) &
					($scope.authStat == '' || $scope.authStat == null || $scope.authStat == "Select option") &
					($scope.recordStat == '' || $scope.recordStat == null || $scope.recordStat == "Select option")))

			{
				fn_Show_Exception('FE-VAL-028');
				return false;
			}
			return true;
			break;

		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnTeacherTimeTableSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

			if (!fngetSelectedIndex($scope.TeacherTimeTableSummaryTable, $scope)) //Generic For Summary
				return false;
			return true;
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnTeacherTimeTableSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.authStat = "";
	
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.TeacherTimeTableSummaryTable = null;
	$scope.TeacherTimeTableSummaryShowObject = null;
	if ($scope.operation == "View") {
		$scope.teacherNamereadOnly = false;
		$scope.teacherIDreadOnly = false;
		$scope.authStatreadOnly = false;
		$scope.recordStatreadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
	} else {
		$scope.teacherNamereadOnly = true;
		$scope.teacherIDreadOnly = true;
		$scope.authStatreadOnly = true;
		$scope.recordStatreadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	$scope.selectedIndex = 0; // Summary Field
	// Generic Scope Ends	
}
// Cohesive Back Framework Ends
function fnConvertmvw(tableName, responseObject) {
	switch (tableName) {
		case 'TeacherTimeTableSummaryTable':

			var TeacherTimeTableSummaryTable = new Array();
			responseObject.forEach(fnConvert);


			function fnConvert(value, index, array) {
				TeacherTimeTableSummaryTable[index] = new Object();
				TeacherTimeTableSummaryTable[index].idx = index;
				TeacherTimeTableSummaryTable[index].checkBox = false;
				TeacherTimeTableSummaryTable[index].teacherName = value.teacherName;
				TeacherTimeTableSummaryTable[index].teacherID = value.teacherID;
			}
			return TeacherTimeTableSummaryTable;
			break;
	}
}

function fnTeacherTimeTableSummarypostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.teacherNamereadOnly = true;
		$scope.teacherIDreadOnly = true;
		$scope.authStatreadOnly = true;
		$scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts
		$scope.teacherName = response.body.filter.teacherName;
		$scope.teacherID = response.body.filter.teacherID;
		$scope.authStat = response.body.filter.authStat;
		//Multiple View Response Starts 
		$scope.TeacherTimeTableSummaryTable = fnConvertmvw('TeacherTimeTableSummaryTable', response.body.SummaryResult);
		$scope.TeacherTimeTableSummaryCurPage = 1;
		$scope.TeacherTimeTableSummaryShowObject = $scope.fnMvwGetCurPageTable('TeacherTimeTableSummary');
		//Multiple View Response Ends 
		$scope.selectedIndex = 0; // Summary Field
	}
	return true;

}