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
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.activityID = "";
	$scope.OtherActivityIdMaster = [{
		ActivityId: "",
		ActivityType: "",
	}];

	$scope.activityName = "";
	$scope.activityType = "";
	$scope.level = "";
	$scope.venue = "Select option";
	$scope.date = "";
	$scope.dueDate = "";
        $scope.confirmationDate = "";
	$scope.participate = '';
	$scope.result = "";
	$scope.classes = Institute.ClassMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.levels = Institute.OtherActivityLevelMaster;
	$scope.participation = Institute.ParticipateMaster;
        $scope.ActivityTypeMaster = Institute.ActivityTypeMaster;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
        $scope.activityIDSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
	$scope.resultreadOnly = true;
	$scope.participatereadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	$scope.searchShow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
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
	$scope.fnActivitySearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Activity';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts	
$(document).ready(function () {
	//
	MenuName = "StudentOtherActivity";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        window.parent.fn_hide_parentspinner();
        
	fnDatePickersetDefault('activityDate', fndateEventHandler);
	fnDatePickersetDefault('activityDueDate', fndueDateEventHandler);
        fnDatePickersetDefault('activityConfirmationDate', fnconfirmationDateEventHandler);
	fnsetDateScope();
        selectBoxes = ['activityLevel', 'activityVenue', 'participate','activityType'];
        fnGetSelectBoxdata(selectBoxes);
	


	
});
//Default Load Record Ends
function fnStudentOtherActivitypostSelectBoxMaster()
{
    
     selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    
	if (window.parent.StudentOtheractivitykey.studentID != null && window.parent.StudentOtheractivitykey.studentID != '') {
            
            if (window.parent.StudentOtheractivitykey.activityID != null && window.parent.StudentOtheractivitykey.activityID != '') {
		
              var studentID = window.parent.StudentOtheractivitykey.studentID;
              var activityID = window.parent.StudentOtheractivitykey.activityID;

		window.parent.StudentOtheractivitykey.studentID = null;
                window.parent.StudentOtheractivitykey.activityID = null;

		fnshowSubScreen(studentID,activityID);
 
           }
	}
        var emptyStudentOtherActivity = {

		studentID: "",
		studentName: "",
		activityName: "",
		activityType: "",
		activityID: "",
		dueDate: "",
                confirmationDate : "",
		participate: '',
		level: "",
		venue: "Select option",
		date: "",
		result: ""
	};

	var dataModel = emptyStudentOtherActivity;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null && $scope.studentID!=""){
            dataModel.studentID = $scope.studentID;
            var response = fncallBackend('StudentOtherActivity', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
        }
        
    }
}


//Cohesive Summary Screen Starts
function fnshowSubScreen(studentID,activityID) {
    subscreen = true;
	var emptyStudentOtherActivity = {

		studentID: "",
		studentName: "",
		activityName: "",
		activityType: "",
		activityID: "",
		level: "",
		venue: "Select option",
		date: "",
		dueDate: "",
                confirmationDate: "",
		participate: '',
		result: ""
	};


	var dataModel = emptyStudentOtherActivity;
	//Screen Specific DataModel Starts
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(studentID!==null&&studentID!==""){
            dataModel.studentID =studentID;
            
        if(activityID!==null&&activityID!==""){
            dataModel.activityID =activityID;    
            //Screen Specific DataModel Ends
            var response = fncallBackend('StudentOtherActivity', 'View', dataModel, [{entityName:"studentID",entityValue:dataModel.studentID}], $scope);
        }
    }
        return true;
}
//Cohesive Summary Screen Starts
//Cohesive Query Framework Starts
function fnStudentOtherActivityQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.activityName = "";
	$scope.activityType = "";
	$scope.activityID = "";
	$scope.participate = '';
	$scope.level = "";
	$scope.venue = "Select option";
	$scope.date = "";
	$scope.dueDate = "";
        $scope.confirmationDate = "";
	$scope.result = "";
	$scope.studentNamereadOnly = false;
    $scope.activityIDSearchreadOnly = false;
    $scope.studentNameSearchreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
	$scope.resultreadOnly = true;
	$scope.participatereadOnly = true;
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
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
function fnStudentOtherActivityView() {
	var emptyStudentOtherActivity = {
		studentID:"",
		studentName: "",
		activityName: "",
		activityType: "",
		activityID: "",
		participate: '',
		level: "",
		venue: "Select option",
		date: "",
		dueDate: "",
                confirmationDate :"",
		result: ""
	};


	var dataModel = emptyStudentOtherActivity;
	//Screen Specific DataModel Starts
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!==null&&$scope.studentID!==null){
            
            if($scope.activityID!==null&&$scope.activityID!==null){
            
                dataModel.studentID = $scope.studentID;
                dataModel.studentName = $scope.studentName;
                dataModel.activityID = $scope.activityID;
                //Screen Specific DataModel Ends
                var response = fncallBackend('StudentOtherActivity', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	
        }
    }
    return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts      
function fnStudentOtherActivityMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//                      if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
			/*if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}*/
                     
                        if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
			if ($scope.activityID == '' || $scope.activityID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity ID']);
				return false;
			}
			break;

		case 'Save':
			if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['studentName']);
				return false;
			}
                          if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
				return false;
			}
			if ($scope.activityID == '' || $scope.activityID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity ID']);
				return false;
			}
			if ($scope.activityName == '' || $scope.activityName == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity Name']);
				return false;
			}
			if ($scope.activityType == '' || $scope.activityType == null || $scope.activityType == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity Type']);
				return false;
			}
                        if ($scope.participate == '' || $scope.participate == null || $scope.participate == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Participate In General Tabs']);
				return false;
			}
                        if ($scope.result == '' || $scope.result == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity Result in General Tab']);
				return false;
			}
			if ($scope.date == '' || $scope.date == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity Date in Others Tab']);
				return false;
			}
			if ($scope.dueDate == '' || $scope.dueDate == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity  Due Date In Others Tab']);
				return false;
			}
			if ($scope.level == '' || $scope.level == null || $scope.level == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity Level']);
				return false;
			}
			if ($scope.venue == '' || $scope.venue == null || $scope.venue == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['venue']);
				return false;
			}
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends  
function fnStudentOtherActivityDefaultandValidate(operation) {
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
function fnStudentOtherActivityCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.activityName = "";
	$scope.activityType = "";
	$scope.activityID = "";
	$scope.participate = '';
	$scope.level = "";
	$scope.venue = "Select option";
	$scope.date = "";
	$scope.dueDate = "";
        $scope.confirmationDate = "";
	$scope.result = "";
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
         $scope.activityIDSearchreadOnly = true;
	$scope.studentIDreadOnly = false;
	$scope.activityNamereadOnly = false;
	$scope.activityTypereadOnly = false;
	$scope.levelreadOnly = false;
	$scope.venuereadOnly = false;
	$scope.datereadOnly = false;
        $( "#activityDate" ).datepicker( "option", "disabled", false );
        $( "#activityDueDate" ).datepicker( "option", "disabled", false );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", false );
	$scope.resultreadOnly = false;
	$scope.participatereadOnly = false;
	$scope.classes = Institute.ClassMaster;
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
function fnStudentOtherActivityEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	//Generic Field Ends
	//Screen Specific Scope Starts	
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
        $scope.activityIDSearchreadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
	$scope.resultreadOnly = false;
	$scope.participatereadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnStudentOtherActivityDelete() {
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
	$scope.activityTypereadOnly = true;
         $scope.activityIDSearchreadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
	$scope.resultreadOnly = true;
	$scope.participatereadOnly = true;
	//Screen Specific Scope Ends


	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnStudentOtherActivityAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
         $scope.activityIDSearchreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
	$scope.resultreadOnly = true;
	$scope.participatereadOnly = true;
	//Screen Specific Scope Ends
	return true;
}


function fnStudentOtherActivityEnroll() {
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
         $scope.activityIDSearchreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
	$scope.resultreadOnly = true;
	$scope.participatereadOnly = true;
	//Screen Specific Scope Ends
	return true;
}


//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnStudentOtherActivityReject() {
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
         $scope.activityIDSearchreadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
	$scope.resultreadOnly = true;
	$scope.participatereadOnly = true;
	//Screen Specific Scope Ends

	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnStudentOtherActivityBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.studentID = "";
		$scope.studentName = "";
		$scope.activityName = "";
		$scope.activityType = "";
		$scope.activityID = "";
		$scope.participate = '';
		$scope.level = "";
		$scope.venue = "Select option";
		$scope.date = "";
		$scope.dueDate = "";
                $scope.confirmationDate = "";
		$scope.result = "";
	}
	$scope.studentNamereadOnly = true;
         $scope.studentNameSearchreadOnly = true; 
	$scope.studentIDreadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
         $scope.activityIDSearchreadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
        
        
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	$( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
        $scope.resultreadOnly = true;
	$scope.participatereadOnly = true;
	//Screen Specific Scope Ends	
	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
}
//Cohesive Back Framework Ends
//Cohesive Save Framework Starts
function fnStudentOtherActivitySave() {
	var emptyStudentOtherActivity = {

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
	var dataModel = emptyStudentOtherActivity;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.activityName!=null)
	dataModel.activityName = $scope.activityName;
        if($scope.activityType!=null)
	dataModel.activityType = $scope.activityType;
        if($scope.activityID!=null)
	dataModel.activityID = $scope.activityID;
        if($scope.venue!=null)
	dataModel.venue = $scope.venue;
        if($scope.date!=null)
	dataModel.date = $scope.date;
        if($scope.dueDate!=null)
	dataModel.dueDate = $scope.dueDate;
          if($scope.level!=null)
	dataModel.level = $scope.level;
          if($scope.result!=null)
	dataModel.result = $scope.result;
         if($scope.participate!=null)
	dataModel.participate = $scope.participate;
	//Screen Specific DataModel Starts
        
//        if(enroll){
//            parentOperation="Enroll";
//            var response = fncallBackend('StudentOtherActivity', "Edit", dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
//            
//        }else{
        
        
	var response = fncallBackend('StudentOtherActivity',parentOperation, dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	
//    }
    return true;
}
//Cohesive Save Framework Ends
function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#activityDate").datepicker("getDate"));
	$scope.$apply();
}

function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#activityDueDate").datepicker("getDate"));
	$scope.$apply();
}

function fnconfirmationDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.confirmationDate = $.datepicker.formatDate('dd-mm-yy', $("#activityConfirmationDate").datepicker("getDate"));
	$scope.$apply();
}

function fnsetDateScope() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#activityDate").datepicker("getDate"));
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#activityDueDate").datepicker("getDate"));
        $scope.confirmationDate = $.datepicker.formatDate('dd-mm-yy', $("#activityConfirmationDate").datepicker("getDate"));
	$scope.$apply();
}



function fnStudentOtherActivitypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(enroll){
        
     parentOperation="Auth";
    
    }
     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.studentNamereadOnly = true;
                 $scope.activityIDSearchreadOnly = true;
                $scope.studentNameSearchreadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.activityNamereadOnly = true;
		$scope.activityTypereadOnly = true;
		$scope.levelreadOnly = true;
		$scope.venuereadOnly = true;
		$scope.datereadOnly = true;
                $( "#activityDate" ).datepicker( "option", "disabled", true );
                $( "#activityDueDate" ).datepicker( "option", "disabled", true );
	        $( "#activityConfirmationDate" ).datepicker( "option", "disabled", true );
		$scope.resultreadOnly = true;
		$scope.participatereadOnly = true;
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
		$scope.activityName ="";
                $scope.activityType = "";
		$scope.activityID ="";
	  	$scope.participate ="";
                $scope.venue = "Select option";
		$scope.date ="";
                $scope.dueDate ="";
                $scope.confirmationDate ="";
                $scope.level ="";
                //$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.studentID = response.body.studentID;
		$scope.studentName = response.body.studentName;
		$scope.activityName = response.body.activityName;
		$scope.activityType = response.body.activityType;
		$scope.activityID = response.body.activityID;
		$scope.participate = response.body.participate;
		$scope.venue = response.body.venue;
		$scope.date = response.body.date;
		$scope.dueDate = response.body.dueDate;
                $scope.confirmationDate =response.body.confirmationDate;
		$scope.level = response.body.level;
                $scope.result=response.body.result;
                $scope.audit = response.audit;;
            }
       if (subscreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
           subscreen=false;
      }
                 
		return true;

}

}