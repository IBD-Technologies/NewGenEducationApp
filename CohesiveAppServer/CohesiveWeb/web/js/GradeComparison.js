/* 
    Author     :IBD Technologies
	             
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.exam = 'Select option';
	$scope.class = '';
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.Report = Institute.ReportStandard;
	$scope.ExamMaster = Institute.ExamMaster;
          $scope.classes=Institute.ClassMaster;  
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
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.classReadonly = false;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
	$scope.examtypereadOnly = false;
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
	MenuName = "GradeComparison";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "InstituteReport";
	selectBoxes = ['class', 'examType'];
	fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});


function fnGradeComparisonpostSelectBoxMaster(){
    
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0){
    $scope.classes=Institute.ClassMaster;  
    $scope.ExamMaster = Institute.ExamMaster;
    window.parent.fn_hide_parentspinner();
    $scope.$apply();
}
}
// Cohesive View Framework Starts
function fnGradeComparisonView() {
	var emptyGradeComparison = {
		Master: {
			class: 'Select option',
//			exam: "Select option",
			instituteID: "",
			instituteName: ""
		},
		Report: [{
			Content: ""
		}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyGradeComparison;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.class != null)
		dataModel.Master.class = $scope.class;
	if ($scope.instituteID != null)
        dataModel.Master.instituteID = $scope.instituteID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('GradeComparison', 'View', dataModel, [{
		entityName: "instituteID",
		entityValue: $scope.instituteID
	}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnGradeComparisonMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
                    if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
//			if ($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam']);
//				return false;
//			}
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

function fnGradeComparisonDefaultandValidate(operation) {
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
function fnGradeComparisonBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	if ($scope.operation == "View") {
		$scope.classReadonly = false;
		$scope.sectionreadOnly = false;
		$scope.instituteIDreadOnly = false;
		$scope.instituteNamereadOnly = false;
		$scope.examtypereadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
	} else {
		$scope.classReadonly = true;
		$scope.sectionreadOnly = true;
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.examtypereadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	// Generic Scope Ends	

}
// Cohesive Back Framework Ends\

function fnGradeComparisonpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.classReadonly = true;
		$scope.sectionreadOnly = true;
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.examtypereadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.class = response.body.class;
		//$scope.section = response.body.section;
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
                $scope.ReportPath = response.body.ReportPath;
        fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.ReportPath);
//		$scope.content = response.body.content;
		return true;

	}
}