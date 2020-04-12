/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
        $scope.feeID = "";
        $scope.toDate = "";
        $scope.fromDate="";
        $scope.class = '';
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
        $scope.fromDatereadOnly = false;
        $scope.toDatereadOnly = false;
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
	MenuName = "PaymentBusinessReport";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "StudentReport";
        fnDatePickersetDefault('fromDate',fninstantEventHandler);
        fnDatePickersetDefault('toDate',fninstantEventHandler);
        fnsetDateScope();
	window.parent.fn_hide_parentspinner();
        selectBoxes = ['class'];
    fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});

function fnPaymentBusinessReportpostSelectBoxMaster(){
    
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    $scope.classes=Institute.ClassMaster;  
	window.parent.fn_hide_parentspinner();
        $scope.$apply()
    
    
}
function fninstantEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.fromDate = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate")),
	$scope.toDate = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate")),
		
            $scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate"));
         $( "#fromDate" ).datepicker('setDate','');
          $( "#toDate" ).datepicker('setDate','');
	  $scope.instant = null;
		$scope.$apply();
}


// Cohesive View Framework Starts
function fnPaymentBusinessReportView() {
	var emptyPaymentBusinessReport = {
		Master: {
                        class: '',
			studentName: "",
			studentID: "",
                        feeID:"",
                        toDate : "",
                        fromDate:""
		},
		ReportPath:""
	};
        
	// Screen Specific DataModel Starts									
	var dataModel = emptyPaymentBusinessReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.operation="View";
        if ($scope.class != null)
		dataModel.Master.class = $scope.class;
        if ($scope.studentID != null)
		dataModel.Master.studentID = $scope.studentID;
            if ($scope.feeID != null)
		dataModel.Master.feeID = $scope.feeID;
            if ($scope.fromDate != null)
		dataModel.Master.fromDate = $scope.fromDate;
            if ($scope.toDate != null)
		dataModel.Master.toDate = $scope.toDate;
//	// Screen Specific DataModel Ends
	var response = fncallBackend('PaymentBusinessReport', 'View', dataModel, [{
		entityName: "studentID",
		entityValue: $scope.studentID
	}], $scope);
 //if ($scope.queryParam!=null && $scope.queryParam!="")
//     var frameSrc=ReportSrc+"PaymentBusiness"+".rptdesign"+"&"+"studentId="+$scope.studentID+"&"+"nokotser="+window.parent.nokotser+"&"+"userID="+window.parent.User.Id+"&"+"loginInstitute="+window.parent.Institute.ID+"&"+"service="+"PaymentBusinessReport";
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
function fnPaymentBusinessReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
	       case 'View':
                   if ($scope.fromDate == '' || $scope.fromDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
				return false;
			}
                   
			if ($scope.toDate == '' || $scope.toDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['To Date']);
				return false;
			}
                          
			break;
		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnPaymentBusinessReportDefaultandValidate(operation) {
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
function fnPaymentBusinessReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
        $scope.feeID = "";
        $scope.fromDate = "";
        $scope.toDate = "";
        $scope.class = '';
	if ($scope.operation == "View") {
		$scope.studentIDreadOnly = false;
                $scope.feeIDreadOnly = false;
                $scope.fromDatereadOnly = false;
                $scope.toDatereadOnly = false;
		$scope.studentNamereadOnly = false;
		$scope.mastershow = true;
		$scope.detailshow = false;
                $scope.classReadonly = false;
	} else {
                $scope.classReadonly = true;
		$scope.studentIDreadOnly = true;
                $scope.feeIDreadOnly = true;
                $scope.fromDatereadOnly = true;
                $scope.toDatereadOnly = true;
		$scope.studentNamereadOnly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	// Generic Scope Ends	
       $("#reportFrame").attr("src","");
}
// Cohesive Create Framework Ends

function fnPaymentBusinessReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.studentIDreadOnly = true;
                $scope.feeIDreadOnly = true;
		$scope.studentNamereadOnly = true;
                $scope.fromDatereadOnly = true;
                $scope.toDatereadOnly = true;
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