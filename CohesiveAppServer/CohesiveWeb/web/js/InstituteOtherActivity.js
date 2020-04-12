/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen =false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//Screen Specific Scope Starts
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.groupID = "";
	$scope.GroupMappingMaster = [{
		GroupId: "",
		GroupDescription: ""
	}];
	$scope.activityName = "";
	$scope.activityType = "";
	$scope.activityLevel = "";
	$scope.activityID = "";
	OtherActivityMaster: [{
		ActivityID:"",
		ActivityType: ""

	}];
	$scope.level = "";
	$scope.venue = "";
	$scope.date = "";
	$scope.dueDate ="";
	$scope.classes = Institute.ClassMaster;
	$scope.levels = Institute.OtherActivityLevelMaster;
	$scope.ActivityTypeMaster=Institute.ActivityTypeMaster;
	$scope.activityNamereadOnly = true;
        $scope.activityIDSearchreadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activitydueDate" ).datepicker( "option", "disabled", true );
	$scope.groupIDreadOnly = true;
	$scope.activityIDreadOnly = true;
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
	$scope.fnGroupingSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Group';
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

//Default Load Record Starts	
$(document).ready(function () {
	MenuName = "InstituteOtherActivity";
        selectBypassCount=0;
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "Institute";
	
	fnDatePickersetDefault('activityDate', fndateEventHandler);
	fnDatePickersetDefault('activitydueDate', fndueDateEventHandler);
	fnsetDateScope();
    selectBoxes = ['activityLevel', 'class'];
    fnGetSelectBoxdata(selectBoxes);     
});

function fnInstituteOtherActivitypostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
    
    
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
          if(Institute.ClassMaster.length>0)
      {   
      $scope.classes=Institute.ClassMaster;
	window.parent.fn_hide_parentspinner();
      
//	  if ((window.parent.InstituteOtheractivitySummarykey.activityLevel != null && window.parent.InstituteOtheractivitySummarykey.activityLevel != '') && (window.parent.InstituteOtheractivitySummarykey.activityName != null && window.parent.InstituteOtheractivitySummarykey.activityName != '') && (window.parent.InstituteOtheractivitySummarykey.activityType != null && window.parent.InstituteOtheractivitySummarykey.activityType != '')) {
//		var activityLevel = window.parent.InstituteOtheractivitySummarykey.activityLevel;
//		var activityName = window.parent.InstituteOtheractivitySummarykey.activityName;
//		var activityType = window.parent.InstituteOtheractivitySummarykey.activityType;
//
//		window.parent.InstituteOtheractivitySummarykey.activityLevel = null;
//		window.parent.InstituteOtheractivitySummarykey.activityName = null;
//		window.parent.InstituteOtheractivitySummarykey.activityType = null;
//
//		fnshowSubScreen(activityLevel, activityName, activityType);
//            }

             if ((window.parent.InstituteOtheractivitySummarykey.activityID !== null && window.parent.InstituteOtheractivitySummarykey.activityID !== '')) {
		var activityID = window.parent.InstituteOtheractivitySummarykey.activityID;

		window.parent.InstituteOtheractivitySummarykey.activityID = null;

		fnshowSubScreen(activityID);
            }
     $scope.$apply();
    
      }
}
}

//Default Load Record Ends
//Cohesive Summary Screen Starts
function fnshowSubScreen(activityID) {
    
    subScreen=true;
	var emptyInstituteOtherActivity = {

		instituteID: "",
                instituteName: "",
                groupID: "",
		activityID: "",
		activityName: "",
		activityType: "",
		level: "",
		venue: "",
		date: "",
		dueDate: ""
	};


	var dataModel = emptyInstituteOtherActivity;
	//Screen Specific DataModel Starts
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.activityID=activityID;
        
        if($scope.activityID!==null&&$scope.activityID!==""){
            dataModel.activityID = $scope.activityID;
            dataModel.instituteID=window.parent.Institute.ID;
            //Screen Specific DataModel Ends
            var response = fncallBackend('InstituteOtherActivity', 'View', dataModel, [{entityName:"instituteID",entityValue:dataModel.instituteID}], $scope);
        }
        
        return true;
}
//Cohesive Summary Screen Ends

//Cohesive Query Framework Starts
function fnInstituteOtherActivityQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	 $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.groupID ="";
	$scope.activityID = "";
	$scope.activityName ="";
	$scope.activityType = "";
	$scope.level = "";
	$scope.venue = "";
	$scope.date = "";
	$scope.dueDate = "";
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.activityIDSearchreadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadonly = true;
	$scope.datereadonly = true;
	$scope.Duedatereadonly = true;
	$scope.dueDatereadonly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activitydueDate" ).datepicker( "option", "disabled", true );
	$scope.groupIDreadOnly = true;
	$scope.activityIDreadOnly = false;
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
function fnInstituteOtherActivityView() {
	var emptyInstituteOtherActivity = {

		instituteID: "",
		instituteName: "",
		groupID: "",
		activityID: "",
		activityName: "",
		activityType: "",
		level: "",
		venue: "",
		date: "",
		dueDate: ""
	};


	var dataModel = emptyInstituteOtherActivity;
	//Screen Specific DataModel Starts
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        
      if($scope.activityID!==null&&$scope.activityID!==""){
        
	dataModel.activityID = $scope.activityID;
        dataModel.instituteID = $scope.instituteID;
	//Screen Specific DataModel Ends
	var response = fncallBackend('InstituteOtherActivity', 'View', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	
    }
        
        return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts      
function fnInstituteOtherActivityMandatoryCheck(operation) {
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
                        
			  if ($scope.activityID == '' || $scope.activityID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity ID']);
				return false;
			}
			
			break;

		case 'Save':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
                         if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
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
                        if ($scope.groupID == '' || $scope.groupID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Group ID']);
				return false;
			}
			if ($scope.date == '' || $scope.date == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity Date']);
				return false;
			}
			if ($scope.dueDate == '' || $scope.dueDate == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Due Date']);
				return false;
			}
			if ($scope.level == '' || $scope.level == null || $scope.level == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Activity Level']);
				return false;
			}
			if ($scope.venue == '' || $scope.venue == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['venue']);
				return false;
			}


			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends  
function fnInstituteOtherActivityDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultGroupId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultGroupId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultGroupId($scope) {
	var availabilty = false;
	return true;
}

function fnInstituteOtherActivityDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultActivityID($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultActivityID($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultActivityID($scope) {
	var availabilty = false;
	return true;
}

function fnInstituteOtherActivityDefaultandValidate(operation) {
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
//Cohesive Create Framework Starts
function fnInstituteOtherActivityCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.groupID = "";
	$scope.activityName = "";
	$scope.activityType = "";
	$scope.activityID = "";
	$scope.level = "";
	$scope.venue = "";
	$scope.date = null;
	$scope.dueDate = null;
	$scope.activityNamereadOnly = false;
	$scope.activityTypereadOnly = false;
	$scope.levelreadOnly = false;
	$scope.venuereadOnly = false;
	$scope.datereadOnly = false;
	$scope.dueDatereadOnly = false;
        $( "#activityDate" ).datepicker( "option", "disabled", false );
        $( "#activitydueDate" ).datepicker( "option", "disabled", false );
	$scope.groupIDreadOnly = false;
	$scope.activityIDreadOnly = false;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
        $scope.activityIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//Generic Field Ends
        var emptyInstituteOtherActivity = {

		instituteID: "",
		instituteName: "",
                activityID:"",
		groupID: "",
		activityName: "",
		activityType: "",
		level: "",
		venue: "",
		date: "",
		dueDate: ""
	};
        
        var dataModel = emptyInstituteOtherActivity;
	
        var response = fncallBackend('InstituteOtherActivity', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
        
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnInstituteOtherActivityEdit() {
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
	$scope.activityNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = true; 
	$scope.activityTypereadOnly = false;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.levelreadOnly = false;
	$scope.venuereadOnly = false;
	$scope.datereadOnly = false;
	$scope.dueDatereadOnly = false;
        $( "#activityDate" ).datepicker( "option", "disabled", false );
        $( "#activitydueDate" ).datepicker( "option", "disabled", false );
	$scope.groupIDreadOnly = false;
	$scope.activityIDreadOnly = true;
        $scope.activityIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnInstituteOtherActivityDelete() {
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
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activitydueDate" ).datepicker( "option", "disabled", true );
	$scope.groupIDreadOnly = true;
	$scope.activityIDreadOnly = true;
        $scope.activityIDSearchreadOnly = true;
	//Screen Specific Scope Ends


	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnInstituteOtherActivityAuth() {
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
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true; 
	$scope.instituteNamereadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activitydueDate" ).datepicker( "option", "disabled", true );
	$scope.groupIDreadOnly = true;
	$scope.activityIDreadOnly = true;
          $scope.activityIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnInstituteOtherActivityReject() {
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
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true; 
	$scope.instituteNamereadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activitydueDate" ).datepicker( "option", "disabled", true );
	$scope.groupIDreadOnly = true;
	$scope.activityIDreadOnly = true;
          $scope.activityIDSearchreadOnly = true;
	//Screen Specific Scope Ends

	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnInstituteOtherActivityBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.instituteName = "";
                $scope.instituteID = "";
                $scope.activityID = "";
                $scope.groupID = "";
		$scope.activityName = "";
		$scope.activityType = "";
		$scope.level = "";
		$scope.venue = "";
		$scope.date = "";
		$scope.dueDate = "";
	}
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true; 
	$scope.instituteNamereadOnly = true;
	$scope.activityNamereadOnly = true;
	$scope.activityTypereadOnly = true;
	$scope.levelreadOnly = true;
	$scope.venuereadOnly = true;
	$scope.datereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#activityDate" ).datepicker( "option", "disabled", true );
        $( "#activitydueDate" ).datepicker( "option", "disabled", true );
	$scope.groupIDreadOnly = true;
	$scope.activityIDreadOnly = true;
        $scope.activityIDSearchreadOnly = true;
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
function fnInstituteOtherActivitySave() {
	var emptyInstituteOtherActivity = {

		instituteID: "",
		instituteName: "",
		groupID: "",
		activityName: "",
		activityType: "",
		level: "",
		venue: "",
		date: "",
		dueDate: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyInstituteOtherActivity;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.groupID!=null)
	dataModel.groupID = $scope.groupID;
        if($scope.activityName!=null)
	dataModel.activityName = $scope.activityName;
         if($scope.activityType!=null)
	dataModel.activityType = $scope.activityType;
         if($scope.venue!=null)
	dataModel.venue = $scope.venue;
        if($scope.date!=null)
	dataModel.date = $scope.date;
        if($scope.activityID!=null)
	dataModel.activityID = $scope.activityID;
         if($scope.level!=null)
	dataModel.level = $scope.level;
         if($scope.dueDate!=null)
	dataModel.dueDate = $scope.dueDate;
         if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
         if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
	//Screen Specific DataModel Starts
	var response = fncallBackend('InstituteOtherActivity', parentOperation, dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
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
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#activitydueDate").datepicker("getDate"));
	$scope.$apply();
}

function fnsetDateScope() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#activityDate").datepicker("getDate"));
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#activitydueDate").datepicker("getDate"));
	$scope.$apply();
}


function fnInstituteOtherActivitypostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            if(response.header.operation == 'Create-Default')
		
		{
		  $scope.activityID = response.body.activityID;
                    if(response.body.instituteId!=null && response.body.instituteId!="")
                  $scope.instituteId= response.body.instituteId;
                 if(response.body.instituteName!=null && response.body.instituteName!="")
                  $scope.instituteName = response.body.instituteName;
		  return true;
		}
		else
		{
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.activityNamereadOnly = true;
		$scope.activityTypereadOnly = true;
		$scope.levelreadOnly = true;
		$scope.venuereadOnly = true;
		$scope.datereadOnly = true;
		$scope.dueDatereadOnly = true;
                $( "#activityDate" ).datepicker( "option", "disabled", true );
                $( "#activitydueDate" ).datepicker( "option", "disabled", true );
		$scope.groupIDreadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
                $scope.instituteNamereadOnly = true;
                  $scope.activityIDSearchreadOnly = true;
                  $scope.instituteIDreadOnly = true;
                  $scope.activityIDreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.instituteID = "";
		$scope.instituteName ="";
		$scope.groupID ="";
                $scope.activityID ="";
                $scope.activityName ="";
                $scope.activityType ="";
                $scope.venue ="";
                $scope.date ="";
                $scope.dueDate ="";
                $scope.level ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.groupID = response.body.groupID;
		$scope.activityID = response.body.activityID;
		$scope.activityName = response.body.activityName;
		$scope.activityType = response.body.activityType;
		$scope.venue = response.body.venue;
		$scope.date = response.body.date;
		$scope.dueDate = response.body.dueDate;
		$scope.level = response.body.level;
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
                $scope.audit = response.audit;
            }
         if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen=false;
	 }
 
                 
		return true;
                }
}

}