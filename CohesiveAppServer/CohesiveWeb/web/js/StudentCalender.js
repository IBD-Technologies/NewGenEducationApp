/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.date = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.events = "";
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
    //Screen Specific Scope Ends
	//Generic Field Starts	
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
    //Generic Field Ends
    $scope.fnStudentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
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
	MenuName = "StudentCalender";	
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        window.parent.fn_hide_parentspinner();
	fnCalendersetDefault('StudentCalender', fnStudentCalender);
	fnsetDateScope();
	var emptyStudentCalender = {
		studentID: "",
		studentName: "",
                date:"",
	    events:{eventType:null,key:null,eventAttributes:[{attrName:"",attrValue:""},
		                                                                       {attrName:"",attrValue:""}]} 	
		/*audit: {},
		error: {}*/
	};

	var dataModel = emptyStudentCalender;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null && $scope.studentID!="")
        {  
	dataModel.studentID = $scope.studentID;
	var response = fncallBackend('StudentCalender', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	//-------------------------------------------------------------------------	
    }
});

function fnStudentCalenderQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	$scope.studentID = "";
	$scope.studentName = "";
	$scope.date = "";
	$scope.events = "";
	$scope.studentIDreadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	return true;
}

function fnStudentCalenderView() {
	var emptyStudentCalender = {
		studentID: "",
		studentName: "",
		date: "",
		events:{eventType:null,key:null,eventAttributes:[{attrName:"",attrValue:""},{attrName:"",attrValue:""}]}	
		                                                                      
	};


	var dataModel = emptyStudentCalender;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.teacherID =User.Id;
        if( $scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
	var response = fncallBackend('StudentCalender', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}


function fnStudentCalenderMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
                       if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
				return false;
			}
			if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}
			break;

		case 'Save':
                    
			if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}
                         if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
				return false;
			}
			break;


	}
	return true;
}
function fnStudentCalenderDefaultandValidate(operation) {
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
function fnStudentCalenderCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.events = "";
	$scope.date = "";
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts	
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Creation';
	//Generic Field Ends
	return true;
}

function fnStudentCalenderEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
    //Generic Field  Ends
	//Screen Specific Scope Starts
	$scope.studentIDreadOnly = false;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.operation = 'Modification';
	//Screen Specific Scope Ends
	return true;
}

function fnStudentCalenderDelete() {
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
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	//Screen Specific scope Ends
	return true;
}

function fnStudentCalenderAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        $scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific scope Starts
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.eventsreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}

function fnStudentCalenderReject() {

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
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.eventsreadonly = true;
	//Screen Specific Scope Ends
	return true;
}

function fStudentCalenderBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.studentName = "";
		$scope.studentID = "";
		$scope.events = "";
		$scope.date="";
	}
	  	$scope.studentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
                  $scope.studentNameSearchreadOnly = true;
		//Generic Field Starts
		 $scope.operation = '';
		 $scope.mastershow = true;
	     $scope.detailshow = false;
               $scope.auditshow = false;
	     $scope.MakerRemarksReadonly = true;
	     $scope.CheckerRemarksReadonly = true;
	     //Generic Field Ends
}

function fnStudentCalenderSave() {
	var emptyStudentCalender = {
		studentID: "",
		studentName: "",
		date:"",
		events:{eventType:null,key:null,eventAttributes:[{attrName:"",attrValue:""},{attrName:"",attrValue:""}]}	
	};


	var dataModel = emptyStudentCalender;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
         if($scope.date!=null)
	dataModel.date = $scope.date;
         if($scope.events!=null)
	dataModel.events = $scope.events;
	var response = fncallBackend('StudentCalender ', parentOperation, dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}

function fnStudentCalender() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#StudentCalender").datepicker("getDate"));
	$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#StudentCalender").datepicker("getDate"));
		$scope.$apply();
}	


function fnStudentCalenderpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
		$scope.studentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
                $scope.studentNameSearchreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.studentName = "";
		$scope.studentID ="";
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
            }
 
                 
		return true;

}

}


