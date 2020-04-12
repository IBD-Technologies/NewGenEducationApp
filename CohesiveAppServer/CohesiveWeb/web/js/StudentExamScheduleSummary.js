/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.authStat = "";
	
	$scope.ExamMaster = Institute.ExamMaster;
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
	$scope.examtypereadOnly = false;
	$scope.classReadonly = false;
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.StudentExamScheduleSummaryCurPage = 0;
	$scope.StudentExamScheduleSummaryTable = null;
	$scope.StudentExamScheduleSummaryShowObject = null;
	// multiple View Ends


	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'StudentExamScheduleSummary') {
			if ($scope.StudentExamScheduleSummaryTable != null && $scope.StudentExamScheduleSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentExamScheduleSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.StudentExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.StudentExamScheduleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'StudentExamScheduleSummary') {
			if ($scope.StudentExamScheduleSummaryTable != null && $scope.StudentExamScheduleSummaryTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.StudentExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentExamScheduleSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.StudentExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.StudentExamScheduleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudentExamScheduleSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					class: "",
					exam: "",
					authStat: "",
					recordStat: ""
				};
				if ($scope.StudentExamScheduleSummaryTable == null)
					$scope.StudentExamScheduleSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentExamScheduleSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.StudentExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.StudentExamScheduleSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudentExamScheduleSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentExamScheduleSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.StudentExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.StudentExamScheduleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'StudentExamScheduleSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentExamScheduleSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentExamScheduleSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentExamScheduleSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'StudentExamScheduleSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentExamScheduleSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentExamScheduleSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentExamScheduleSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'StudentExamScheduleSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentExamScheduleSummaryTable);

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
	MenuName = "StudentExamScheduleSummary";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "StudentSummaryEntity";
	selectBoxes = ['examType', 'authStatus','class'];
        fnGetSelectBoxdata(selectBoxes);//Integration changes


	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});


function fnStudentExamScheduleSummarypostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0)
      {  
        $scope.classes=Institute.ClassMaster;	
	$scope.ExamMaster =Institute.ExamMaster;
	window.parent.fn_hide_parentspinner();
          $scope.$apply();
}
}
// Cohesive Query Framework Starts
function fnStudentExamScheduleSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.authStat = "";
	
	$scope.examtypereadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.classReadonly = true;
	$scope.studentIDreadOnly = true;
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
	lscreenKeyObject.studentID = $scope.StudentExamScheduleSummaryTable[$scope.selectedIndex].studentID;
	lscreenKeyObject.exam = $scope.StudentExamScheduleSummaryTable[$scope.selectedIndex].exam;

	fninvokeDetailScreen('StudentExamSchedule', lscreenKeyObject, $scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentExamScheduleSummaryView() {
	var emptyStudentExamScheduleSummary = {
		filter: {
			studentID: "",
			studentName: "",
			class: 'Select option',
			exam: 'Select option',
			authStat: 'Select option',
			recordStat: 'Select option'
		},
		SummaryResult: [{
			studentName: "",
			studentID: "",
			exam: "",
			class:"",
			authStat: "",
			recordStat: ""
		}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentExamScheduleSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.studentID != null)
		dataModel.filter.studentID = $scope.studentID;
	if ($scope.studentName != null)
		dataModel.filter.studentName = $scope.studentName;
	if ($scope.class!= null)
		dataModel.filter.class = $scope.class;
	if ($scope.exam != null)
		dataModel.filter.exam = $scope.exam;
	if ($scope.authStat != null)
		dataModel.filter.authStat = $scope.authStat;
	if ($scope.recordStat != null)
		dataModel.filter.recordStat = $scope.recordStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentExamScheduleSummary', 'View', dataModel, [{
		entityName: "studentID",
		entityValue: ""
	}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentExamScheduleSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ((($scope.studentName == '' || $scope.studentName == null) &
					($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
					($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option') &
					($scope.recordStat == '' || $scope.recordStat == null || $scope.recordStat == 'Select option')))

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

function fnStudentExamScheduleSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

			if (!fngetSelectedIndex($scope.StudentExamScheduleSummaryTable, $scope)) //Generic For Summary
				return false;
			return true;
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnStudentExamScheduleSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.authStat = "";
	
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.StudentExamScheduleSummaryTable = null;
	$scope.StudentExamScheduleSummaryShowObject = null;
	if ($scope.operation == "View") {
		$scope.examtypereadOnly = false;
		$scope.classReadonly = false;
		$scope.studentNamereadOnly = false;
		$scope.studentIDreadOnly = false;
		$scope.authStatreadOnly = false;
		$scope.recordStatreadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
	} else {
		$scope.examtypereadOnly = true;
		$scope.classReadonly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.authStatreadOnly = true;
		$scope.recordStatreadOnly = true;
	}


	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	$scope.selectedIndex = 0; // Summary Field
	// Generic Scope Ends	

}
// Cohesive Create Framework Ends
function fnConvertmvw(tableName, responseObject) {
	switch (tableName) {
		case 'StudentExamScheduleSummaryTable':

			var StudentExamScheduleSummaryTable = new Array();
			responseObject.forEach(fnConvert);


			function fnConvert(value, index, array) {
				StudentExamScheduleSummaryTable[index] = new Object();
				StudentExamScheduleSummaryTable[index].idx = index;
				StudentExamScheduleSummaryTable[index].checkBox = false;
				StudentExamScheduleSummaryTable[index].studentName = value.studentName;
				StudentExamScheduleSummaryTable[index].studentID = value.studentID;
				StudentExamScheduleSummaryTable[index].class = value.class;
				StudentExamScheduleSummaryTable[index].exam = value.exam;
				StudentExamScheduleSummaryTable[index].authStat = value.authStat;
				StudentExamScheduleSummaryTable[index].recordStat = value.recordStat;
			}
			return StudentExamScheduleSummaryTable;
			break;
	}
}

function fnStudentExamScheduleSummarypostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.examtypereadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.authStatreadOnly = true;
		$scope.recordStatreadOnly = true;
		$scope.classReadonly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
		$scope.class= response.body.class;
		$scope.exam = response.body.exam;
		$scope.authStat = response.body.authStat;
		$scope.recordStat = response.body.recordStat;
		//Multiple View Response Starts 
		$scope.StudentExamScheduleSummaryTable = fnConvertmvw('StudentExamScheduleSummaryTable', response.body.SummaryResult);
		$scope.StudentExamScheduleSummaryCurPage = 1;
		$scope.StudentExamScheduleSummaryShowObject = $scope.fnMvwGetCurPageTable('StudentExamScheduleSummary');
		//Multiple View Response Ends 
	}
	return true;

}