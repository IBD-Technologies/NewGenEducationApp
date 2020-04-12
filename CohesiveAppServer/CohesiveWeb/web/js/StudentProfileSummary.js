/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.class = "Select option";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.authStat = "";
	
	$scope.fees = Institute.FeeMaster;
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
	// Screen Specific Scope Start
	$scope.studentIDreadOnly = false;
	$scope.classReadonly = false;
	$scope.studentNamereadOnly = false;
        $scope.studentNameInputReadOnly = false;
	$scope.studentNameSearchreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.StudentProfileSummaryCurPage = 0;
	$scope.StudentProfileSummaryTable = null;
	$scope.StudentProfileSummaryShowObject = null;
	// multiple View Ends
	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'StudentProfileSummary') {
			if ($scope.StudentProfileSummaryTable != null && $scope.StudentProfileSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProfileSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentProfileSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProfileSummaryTable = lsvwObject.tableObject;
				$scope.StudentProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'StudentProfileSummary') {
			if ($scope.StudentProfileSummaryTable != null && $scope.StudentProfileSummaryTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.StudentProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProfileSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentProfileSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProfileSummaryTable = lsvwObject.tableObject;
				$scope.StudentProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudentProfileSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
					studentID: "",
					authStat: "",
					recordStat: ""
				};
				if ($scope.StudentProfileSummaryTable == null)
					$scope.StudentProfileSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProfileSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentProfileSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProfileSummaryTable = lsvwObject.tableObject;
				$scope.StudentProfileSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudentProfileSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProfileSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentProfileSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProfileSummaryTable = lsvwObject.tableObject;
				$scope.StudentProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'StudentProfileSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentProfileSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentProfileSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentProfileSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'StudentProfileSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentProfileSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentProfileSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentProfileSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'StudentProfileSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentProfileSummaryTable);

		}
	};

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


	//Multiple View Scope Function ends 
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "StudentProfileSummary";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "StudentSummaryEntity";
        selectBoxes = ['authStatus','class'];
	fnGetSelectBoxdata(selectBoxes);
	
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnStudentProfileSummarypostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if(Institute.ClassMaster.length>0){
    $scope.classes=Institute.ClassMaster;	
   window.parent.fn_hide_parentspinner();
        $scope.$apply();
}
}
function fnStudentProfileSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.authStat = "";
	
	$scope.studentNamereadOnly = true;
        $scope.studentNameInputReadOnly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.studentNameSearchreadOnly = true;
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
	lscreenKeyObject.studentID = $scope.StudentProfileSummaryTable[$scope.selectedIndex].studentID;
	fninvokeDetailScreen('StudentProfile', lscreenKeyObject, $scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentProfileSummaryView() {
	var emptyStudentProfileSummary = {
		filter: {
			studentName: "",
			studentID: "",
			class: "Select option",
			authStat: "Select option",
			recordStat: "Select option"
		},
		SummaryResult: [{
			studentID: "",
			studentName: "",
			class: "",
			authStat: "",
			recordStat: ""
		}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentProfileSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
	if ($scope.studentID != null)
		dataModel.filter.studentID = $scope.studentID;
            if ($scope.studentName != null)
		dataModel.filter.studentName = $scope.studentName;
	if ($scope.class!= null)
		dataModel.filter.class = $scope.class;
	if ($scope.authStat != null)
		dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentProfileSummary', 'View', dataModel, [{
		entityName: "StudentID",
		entityValue: $scope.studentID
	}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentProfileSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//			if ((($scope.studentName == '' || $scope.studentName == null) &
//					($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
//					($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
//
//			{
//				fn_Show_Exception('FE-VAL-028');
//				return false;
//			}
			return true;
			break;

		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnStudentProfileSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultStudentId($scope))
				return false;

			break;

//		case 'Save':
//			if (!fnDefaultStudentId($scope))
//				return false;
//
//			break;
                    case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.StudentProfileSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}

function fnDefaultStudentId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnStudentProfileSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.class = "Select option";
	$scope.authStat = "";
	
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.StudentProfileSummaryTable = null;
	$scope.StudentProfileSummaryShowObject = null;
	if ($scope.operation == "View") {
		$scope.studentIDreadOnly = false;
		$scope.studentNamereadOnly = false;
                $scope.studentNameInputReadOnly = false;
		$scope.classReadonly = false;
		$scope.studentNameSearchreadOnly = false;
		$scope.authStatreadOnly = false;
		$scope.recordStatreadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
	} else {
		$scope.studentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
                $scope.studentNameInputReadOnly = true;
		$scope.classReadonly = true;
		$scope.studentNameSearchreadOnly = true;
		$scope.authStatreadOnly = true;
		$scope.recordStatreadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	$scope.selectedIndex = 0; // Summary Field
	// Generic Scope Ends	

}
// Cohesive Create Framework End
function fnConvertmvw(tableName, responseObject) {
	switch (tableName) {
		case 'StudentProfileSummaryTable':

			var StudentProfileSummaryTable = new Array();
			responseObject.forEach(fnConvert);


			function fnConvert(value, index, array) {
				StudentProfileSummaryTable[index] = new Object();
				StudentProfileSummaryTable[index].idx = index;
				StudentProfileSummaryTable[index].checkBox = false;
				StudentProfileSummaryTable[index].studentID = value.studentID;
				StudentProfileSummaryTable[index].studentName = value.studentName;
				StudentProfileSummaryTable[index].class = value.class;
				StudentProfileSummaryTable[index].authStat = value.authStat;
				StudentProfileSummaryTable[index].recordStat = value.recordStat;
			}
			return StudentProfileSummaryTable;
			break;
	}
}

function fnStudentProfileSummarypostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.studentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
                $scope.studentNameInputReadOnly = true;
		$scope.classReadonly = true;
		$scope.authStatreadOnly = true;
		$scope.recordStatreadOnly = true;
		$scope.studentNameSearchreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.studentID = response.body.studentID;
		$scope.studentName = response.body.studentName;
		$scope.class = response.body.class;
		$scope.authStat = response.body.authStat;
		$scope.recordStat = response.body.recordStat;
		$scope.SummaryResult = {};
		//Multiple View Response Starts 
		$scope.StudentProfileSummaryTable = fnConvertmvw('StudentProfileSummaryTable', response.body.SummaryResult);
		$scope.StudentProfileSummaryCurPage = 1;
		$scope.StudentProfileSummaryShowObject = $scope.fnMvwGetCurPageTable('StudentProfileSummary');
		//Multiple View Response Ends 
		$scope.selectedIndex = 0; // Summary Field
	}
	return true;

}
