/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	// Specific Screen Scope Starts
        $scope.class = '';
	$scope.studentName = "";
	$scope.studentID = "";
        $scope.feeID = "";
        $scope.feeID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
        $scope.Class = Institute.StandardMaster;
    
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
        //$scope.reportShow=true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.studentIDreadOnly = false;
        $scope.feeIDreadOnly = false;
	$scope.studentNamereadOnly = false;
        $scope.classReadonly = false;
        $scope.ReportPath="";
        	// Screen Specific Scope Ends 
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
        $scope.fnFeeSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
			
		};
	    searchCallInput.mainScope = $scope;
	    searchCallInput.searchType = 'Fee';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "FeeBusinessReport";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "StudentReport";
//	window.parent.fn_hide_parentspinner();
	selectBoxes = ['class'];
    fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});


function fnFeeBusinessReportpostSelectBoxMaster(){
    
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    $scope.classes=Institute.ClassMaster;  
	window.parent.fn_hide_parentspinner();
        $scope.$apply()
    
    
}
// Cohesive View Framework Starts
function fnFeeBusinessReportView() {
	var emptyFeeBusinessReport = {
		Master: {
                       class: '',
			studentName: "",
			studentID: "",
                        feeID:""
		},
		ReportPath:""
	};
        
	// Screen Specific DataModel Starts									
	var dataModel = emptyFeeBusinessReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.operation="View";
        if ($scope.class != null)
		dataModel.Master.class = $scope.class;
        if ($scope.studentID != null)
		dataModel.Master.studentID = $scope.studentID;
            if ($scope.feeID != null)
		dataModel.Master.feeID = $scope.feeID;
//	// Screen Specific DataModel Ends
	var response = fncallBackend('FeeBusinessReport', 'View', dataModel, [{
		entityName: "studentID",
		entityValue: $scope.studentID
	}], $scope);
 //if ($scope.queryParam!=null && $scope.queryParam!="")
//     var frameSrc=ReportSrc+"FeeBusiness"+".rptdesign"+"&"+"studentId="+$scope.studentID+"&"+"nokotser="+window.parent.nokotser+"&"+"userID="+window.parent.User.Id+"&"+"loginInstitute="+window.parent.Institute.ID+"&"+"service="+"FeeBusinessReport";
   // else
     //var frameSrc=ReportSrc+$scope.tableName+".rptdesign"+"&"+"nokotser="+window.parent.nokotser+"&"+"userID="+window.parent.User.Id+"&"+"loginInstitute="+window.parent.Institute.ID+"&"+"service="+"TableReport";
         
//    $("#reportFrame").attr("src",frameSrc);
//    $scope.mastershow = false;
//    $scope.detailshow = true;
		
//	return true;
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnFeeBusinessReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
	       case 'View':
//                   if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
                   if ((($scope.studentID == '' || $scope.studentID == null || $scope.studentID == 'Select option') &                 
					    ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
						($scope.feeID == '' || $scope.feeID == null || $scope.feeID == 'Select option')))
		
					{
						fn_Show_Exception('FE-VAL-028');
						return false;
					}
			
                          
			break;
		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnFeeBusinessReportDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultStudentId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultStudentId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultStudentId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnFeeBusinessReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
        $scope.feeID = "";
        $scope.class = '';
        
	if ($scope.operation == "View") {
		$scope.studentIDreadOnly = false;
                $scope.feeIDreadOnly = false;
		$scope.studentNamereadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
                $scope.classReadonly = false;
                
	} else {
                $scope.classReadonly = true;
                $scope.classReadonly = true;
		$scope.studentIDreadOnly = true;
                $scope.feeIDreadOnly = true;
		$scope.studentNamereadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	// Generic Scope Ends	
       $("#reportFrame").attr("src","");
}
// Cohesive Create Framework Ends

function fnFeeBusinessReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.studentIDreadOnly = true;
                $scope.feeIDreadOnly = true;
		$scope.studentNamereadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
                //$scope.reportshow = false;
                
                
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		//$scope.studentName = response.body.studentName;
		//$scope.studentID = response.body.studentID;
		$scope.ReportPath = response.body.ReportPath;
                
                fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.ReportPath);
	        
                return true;

	}
        
}