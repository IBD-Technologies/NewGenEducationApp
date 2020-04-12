/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subscreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);

app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, OperationScopes) {
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.endPoint = "";
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	//Generic Field Ends
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
	

});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts	
$(document).ready(function () {
	//
	MenuName = "EndPointVerfication";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Institute";
        window.parent.fn_hide_parentspinner();
        
	
});

function fnEndPointVerificationQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.endPointreadOnly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	//Generic Field Starts

	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnEndPointVerificationView() {
	var emptyEndPointVerification = {
		studentID:"",
		studentName: "",
		endPoint: ""

	};


	var dataModel = emptyEndPointVerification;
	//Screen Specific DataModel Starts
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!==null&&$scope.studentID!==null){
            
            if($scope.endPoint!==null&&$scope.endPoint!==null){
            
                dataModel.studentID = $scope.studentID;
                dataModel.endPoint = $scope.endPoint;
                //Screen Specific DataModel Ends
                var response = fncallBackend('EndPointVerification', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	
        }
    }
    return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts      
function fnEndPointVerificationMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
                      if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
				return false;
			}
			/*if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}*/
			if ($scope.endPoint == '' || $scope.endPoint == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity ID']);
				return false;
			}
			break;

		


	}
	return true;
}
//Screen Specific Mandatory Validation Ends  
function fnEndPointVerificationDefaultandValidate(operation) {
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
//Cohesive Create Framework Starts
function fnEndPointVerificationCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.endPoint = "";
	
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.endPointreadOnly = false;
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
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnEndPointVerificationEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	//Generic Field Ends
	//Screen Specific Scope Starts	
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.endPointreadOnly = true;

	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnEndPointVerificationDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.endPointreadOnly = true;
	
	//Screen Specific Scope Ends


	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnEndPointVerificationAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
         $scope.endPointreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	
	//Screen Specific Scope Ends
	return true;
}


function fnEndPointVerificationEnroll() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
        enroll=true;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.endPointreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;

	//Screen Specific Scope Ends
	return true;
}


//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnEndPointVerificationReject() {
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
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
        $scope.endPointreadOnly = true;

	//Screen Specific Scope Ends

	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnEndPointVerificationBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.studentID = "";
		$scope.studentName = "";
		$scope.endPoint = "";

	}
	$scope.studentNamereadOnly = true;
         $scope.studentNameSearchreadOnly = true; 
	$scope.studentIDreadOnly = true;
	$scope.endPointreadOnly = true;
	
	//Screen Specific Scope Ends	
	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;

	//Generic Field Ends
}
//Cohesive Back Framework Ends
//Cohesive Save Framework Starts
function fnEndPointVerificationSave() {
	var emptyEndPointVerification = {

		studentID: "",
		studentName: "",
		activityName: "",
		activityType: "",
		participate: '',
		activityID: "",
		level: "",
		venue: "Select option",
		date: "",
		dueDate: "",
                confirmationDate : "",
		result: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyEndPointVerification;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.endPoint!=null)
	dataModel.endPoint = $scope.endPoint;
        
        
	var response = fncallBackend('EndPointVerification',parentOperation, dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	
//    }
    return true;
}
//Cohesive Save Framework Ends



function fnEndPointVerificationpostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(enroll){
        
     parentOperation="Auth";
    
    }
     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
		$scope.studentNamereadOnly = true;
                 $scope.endPointreadOnly = true;
                $scope.studentNameSearchreadOnly = true;
		$scope.studentIDreadOnly = true;
		
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.studentID = "";
		$scope.studentName ="";
		$scope.endpoint ="";
                
                //$scope.audit = response.body.audit;//Integration changes
		 }
                else
                {
                $scope.studentID = response.body.studentID;
		$scope.studentName = response.body.studentName;
		$scope.endPoint = response.body.activityName;

            }
       if (subscreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
           subscreen=false;
      }
                 
		return true;

}

}