/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'ReportOperation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	// Specific Screen Scope Starts
        $scope.studentStatus = '';
        $scope.toDate = "";
        $scope.fromDate="";
        $scope.class = 'Select option';
        $scope.StudentStatus = Institute.StudentStatusMaster;
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
        $scope.studentStatusreadOnly = false;
        $scope.classReadonly = false;
        $scope.ReportPath="";
        	// Screen Specific Scope Ends 
	

});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "StudentDetailsReport";
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "StudentReport";
        fnDatePickersetDefault('fromDate',fninstantEventHandler);
        fnDatePickersetDefault('toDate',fninstantEventHandler);
//	window.parent.fn_hide_parentspinner();
	selectBoxes = ['studentStatus','class'];
    fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});


function fnStudentDetailsReportpostSelectBoxMaster(){
    
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    $scope.StudentStatus = Institute.StudentStatusMaster;
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
function fnStudentDetailsReportView() {
	var emptyStudentDetailsReport = {
		Master: {
                       class: '',
                       toDate : '',
                        fromDate:'',
                       studentStatus: ''
		},
		ReportPath:""
	};
        
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentDetailsReport;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.operation="View";
        if ($scope.class != null)
		dataModel.Master.class = $scope.class;
        if ($scope.studentStatus != null)
		dataModel.Master.studentStatus = $scope.studentStatus;
        if ($scope.fromDate != null)
		dataModel.Master.fromDate = $scope.fromDate;
            if ($scope.toDate != null)
		dataModel.Master.toDate = $scope.toDate;
//	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentDetailsReport', 'View', dataModel, [{
		entityName: "studentID",
		entityValue: ""
	}], $scope);
 //if ($scope.queryParam!=null && $scope.queryParam!="")
//     var frameSrc=ReportSrc+"FeeBusiness"+".rptdesign"+"&"+"studentId="+$scope.studentID+"&"+"nokotser="+window.parent.nokotser+"&"+"userID="+window.parent.User.Id+"&"+"loginInstitute="+window.parent.Institute.ID+"&"+"service="+"StudentDetailsReport";
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
function fnStudentDetailsReportMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
	       case 'View':
//                   if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
                  if (($scope.studentStatus == '' || $scope.studentStatus == null|| $scope.studentStatus == 'Select option')&&($scope.class == '' || $scope.class == null|| $scope.class == 'Select option')) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class or Student Status']);
				return false;
			}
			
                          
			break;
		case 'Detail':
			return true;
			break;
	}
	return true;
}

function fnStudentDetailsReportDefaultandValidate(operation) {
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
function fnStudentDetailsReportBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
        $scope.studentStatus = '';
        $scope.class = '';
        $scope.fromDate = "";
        $scope.toDate = "";
	if ($scope.operation == "View") {
		$scope.mastershow = true;
		$scope.detailshow = false;
                $scope.studentStatusreadOnly = false;
                $scope.classReadonly = false;
                
	} else {
                $scope.studentStatusreadOnly = true;
                $scope.classReadonly = true;
	}
	// Screen Specific Scope Ends
	// Generic Scope Starts
	$scope.operation = '';
	// Generic Scope Ends	
       $("#reportFrame").attr("src","");
}
// Cohesive Create Framework Ends

function fnStudentDetailsReportpostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
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