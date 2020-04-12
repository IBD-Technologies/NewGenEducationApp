/* 
    Author     :IBD Technologies
	
*/

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	
	// Specific Screen Scope Starts
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.notificationID = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "I";
	$scope.date = "Select option";
	$scope.day = "";
	$scope.month = "";
	$scope.message = "";
        $scope.otherLanguageMessage = "";
	$scope.mediaCommunication = "";
        
	$scope.assignee = "";
        $scope.instant = "";
        $scope.email="";
        $scope.mobileNo="";
	$scope.Types = Institute.NotificationMaster;
        $scope.DateMaster = Institute.DateMaster;
	$scope.frequencies = Institute.NotificationFrequency;
	$scope.Communications = Institute.MediaCommunication;
	$scope.Days = Institute.DayMaster;
	$scope.Months = Institute.MonthMaster;
	$scope.searchShow = false;
	// Specific Screen Scope Ends
	// Generic Field starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.operation = '';
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
         $scope.notificationIDSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.notificationIDreadOnly = true;
        $scope.instantreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.messagereadOnly = true;
        $scope.otherLangMessagereadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
	$scope.assigneereadOnly = true;
        $scope.emailreadOnly=true;
        $scope.mobileNoreadOnly=true;
        
	// Screen Specific Scope Ends
	$scope.fnNotificationSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Notification';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	$scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Institute';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
        $scope.fnGroupingSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Group';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
        
        
        
});
//--------------------------------------------------------------------------------------------------------------

//-------This is to Load default record Starts--------------------------------------	
$(document).ready(function () {
	
	 MenuName = "Notification"; 
         selectBypassCount=0;
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="Institute";
        
         selectBoxes= ['notificationTypes','frequency','notificationMonth','notificationDays','communication'];
	 
    fnGetSelectBoxdata(selectBoxes);
    fnDatePickersetDefault('instantDate',fninstantEventHandler);
    fnsetDateScope();
	//-------This is to Load default record Ends--------------------------------------

});

function fnNotificationpostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.NotificationMaster.length>0)
      {    
       $scope.Types = Institute.NotificationMaster;
     
	  window.parent.fn_hide_parentspinner();   
    
    if (window.parent.NotificationSummarykey.notificationID !==null && window.parent.NotificationSummarykey.notificationID !=='')
	{
		var notificationID=window.parent.NotificationSummarykey.notificationID;
		 window.parent.NotificationSummarykey.notificationID =null;
		fnshowSubScreen(notificationID);
		
	}  
         $scope.$apply();
    }  
    }
}



function fnshowSubScreen(notificationID)
{
    subScreen = true;
var emptyNotification = {

		instituteID: "",
		instituteName: "",
		notificationID: "",
		notificationType: "Select option",
		notificationFrequency: "I",
		date: "Select option",
                instant:"",
		day: "",
		month: "",
		message: "",
                otherLanguageMessage: "",
		mediaCommunication: "",
		assignee: "",
                email:"",
                mobileNo:""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyNotification;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(notificationID!==null&&notificationID!==""){
	 dataModel.notificationID = notificationID;
         dataModel.instituteID=window.parent.Institute.ID;;
	// Screen Specific DataModel Ends
	var response = fncallBackend('Notification', 'View', dataModel, [{entityName:"instituteID",entityValue:dataModel.instituteID}], $scope);
	
    }
        return true;
}
// Cohesive Query Framework Starts
function fnNotificationQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.notificationID = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "I";
	$scope.date = "Select option";
	$scope.day = "";
	$scope.month = "";
	$scope.message = "";
        $scope.otherLanguageMessage = "";
	$scope.mediaCommunication = "";
	$scope.assignee = "";
        $scope.instant = "";
        $scope.email="";
        $scope.mobileNo="";
	$scope.instituteIDreadOnly = false;
        $scope.instantreadOnly = true;
        $scope.instituteNameSearchreadOnly = false;
	$scope.instituteNamereadOnly = false;
	$scope.notificationIDreadOnly = false;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
	$scope.messagereadOnly = true;
        $scope.otherLangMessagereadOnly = true;
	$scope.assigneereadOnly = true;
        $scope.emailreadOnly=true;
        $scope.mobileNoreadOnly=true;
        
        $scope.notificationIDSearchreadOnly = false;
	// Screen Specific Scope Starts
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	// Generic Field Ends
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts	
function fnNotificationView() {
	var emptyNotification = {

		instituteID: "",
		instituteName: "",
		notificationID: "",
		notificationType: "Select option",
		notificationFrequency: "I",
		date: "Select option",
                instant: "",
		day: "Select option",
		month: "",
		message:"",
                otherLanguageMessage : "",
		mediaCommunication: "",
		assignee: "",
                email:"",
                mobileNo:""
	};
	// Screen Specific DataModel Starts							 
	var dataModel = emptyNotification;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.instituteID!==null&&$scope.instituteID!==""){
	dataModel.instituteID = $scope.instituteID;
    
      if($scope.notificationID!==null&&$scope.notificationID!==""){
	dataModel.notificationID = $scope.notificationID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('Notification', 'View', dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}],$scope);
	
        }
    }
       return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts	  
function fnNotificationMandatoryCheck(operation) {
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
			if ($scope.notificationID == '' || $scope.notificationID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification ID']);
				return false;
			}
			if ($scope.notificationType == '' || $scope.notificationType == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification Type']);
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

			if ($scope.notificationID == '' || $scope.notificationID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notificationn ID']);
				return false;
			}
			if ($scope.notificationType == '' || $scope.notificationType == null || $scope.notificationType == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification Type']);
				return false;
			}
                        if ($scope.assignee == '' || $scope.assignee == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignee in General Tab']);
				return false;
			}
                       	if (($scope.message == '' || $scope.message == null)&&($scope.otherLanguageMessage == '' || $scope.otherLanguageMessage == null)) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Either Notification Message in English or other language in Message Tab']);
				return false;
			}
                        if ($scope.mediaCommunication == '' || $scope.mediaCommunication == null || $scope.mediaCommunication == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Mode in General Tab']);
				return false;
			}
//                        if ($scope.notificationFrequency == '' || $scope.notificationFrequency == null || $scope.notificationFrequency == "Select option") {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification Frequency in Frequency Tab']);
//				return false;
//			}
//                        if ($scope.month == '' || $scope.month == null || $scope.month == "Select option") {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Month in General Tab']);
//				return false;
//			}
//                          if ($scope.day == '' || $scope.day == null || $scope.day == 'Select option' ) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Day in General Tab']);
//				return false;
//			}
//			if ($scope.date == '' || $scope.date == null || $scope.date == 'Select option' ) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Date in General Tab']);
//				return false;
//			}
		      if ($scope.instant == '' || $scope.instant == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Delivery Date in Message Tab']);
				return false;
			}
                        
//                        if ($scope.email == '' || $scope.email == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Email in Test Tab']);
//				return false;
//			}
//                        
//                        if ($scope.mobileNo == '' || $scope.mobileNo == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['MobileNo in Test Tab']);
//				return false;
//			}
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends

function fnNotificationDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultNotifificationID($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultNotifificationID($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultNotifificationID($scope) {
	var availabilty = false;
	return true;
}


function fnNotificationDefaultandValidate(operation) {
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

function fnNotificationDefaultandValidate(operation) {
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



// Cohesive Create Framework Starts
function fnNotificationCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.notificationID = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "I";
	$scope.date = "Select option";
	$scope.day = "";
	$scope.month = "";
	$scope.message = "";
        $scope.otherLanguageMessage = "";
	$scope.mediaCommunication = "";
	$scope.assignee = "";
        $scope.instant = "";
        $scope.email="";
        $scope.mobileNo="";
	$scope.Types = Institute.NotificationMaster;
	$scope.frequencies = Institute.NotificationFrequency;
	$scope.Communications = Institute.MediaCommunication;
	$scope.notificationIDreadOnly = false;
	$scope.instituteIDreadOnly = false;
        $scope.instantreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
            $scope.notificationIDSearchreadOnly = true;
	$scope.notificationTypereadOnly = false;
	$scope.notificationFrequencyreadOnly = false;
	$scope.datereadOnly = false;
	$scope.dayreadOnly = false;
	$scope.monthreadOnly = false;
	$scope.messagereadOnly = false;
        $scope.otherLangMessagereadOnly = false;
	$scope.mediaCommunicationreadOnly = false;
	$scope.assigneereadOnly = false;
        $scope.emailreadOnly=false;
        $scope.mobileNoreadOnly=false;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	// Generic Field Ends
        
        var emptyNotification = {

		notificationID: "",
                instituteName:"",
                instituteID: "",
		notificationType: "Select option",
		notificationFrequency: "I",
		date: "Select option",
		day: "",
		month: "",
		message:"",
                otherLanguageMessage : "",
                instant:"",
		mediaCommunication: "",
		assignee: "",
                email:"",
                mobileNo:""
	};
        
        var dataModel = emptyNotification;
	
         var response = fncallBackend('Notification', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);


	return true;
}
// Cohesive Create Framework Ends

// Cohesive Edit Framework Starts
function fnNotificationEdit() {
	
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	//Generic Field Starts

	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
          $scope.instantreadOnly = false;
           $scope.notificationIDSearchreadOnly = false;
	$scope.instituteNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = false;
        $scope.instituteNameSearchreadOnly = true;
	$scope.notificationFrequencyreadOnly = false;
	$scope.datereadOnly = false;
	$scope.dayreadOnly = false;
	$scope.monthreadOnly = false;
	$scope.mediaCommunicationreadOnly = false;
	$scope.messagereadOnly = false;
        $scope.otherLangMessagereadOnly = false;
	$scope.assigneereadOnly = false;
        $scope.emailreadOnly=false;
        $scope.mobileNoreadOnly=false;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Edit Framework Ends


// Cohesive Notification Framework Starts
function fnNotificationDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	// Generic Field Ends
	// screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
         $scope.notificationIDSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
          $scope.instantreadOnly = true;
	$scope.assigneereadOnly = true;
        $scope.emailreadOnly=true;
        $scope.mobileNoreadOnly=true;
	// screen Specific Scope Ends
	return true;
}
// Cohesive Notification Framework Ends

// Cohesive Authorisation Framework Starts
function fnNotificationAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// generic field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	// Generic field Ends

	// screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
         $scope.notificationIDSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
          $scope.instantreadOnly = true;
	$scope.assigneereadOnly = true;
        $scope.emailreadOnly=true;
        $scope.mobileNoreadOnly=true;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Authorisation Framework Ends

// Cohesive Reject Framework Starts
function fnNotificationReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	// Generic Field Ends

	//Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
         $scope.notificationIDSearchreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
          $scope.instantreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
	$scope.assigneereadOnly = true;
         $scope.emailreadOnly=true;
        $scope.mobileNoreadOnly=true;
	//Screen Specific Scope Starts
	return true;
}
// Cohesive Reject Framework Ends

// Cohesive Back Framework Starts
function fnNotificationBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	        $scope.instituteName= "";
                 $scope.instituteID= "";
                  $scope.notificationID= "";
		$scope.notificationType= "Select option";
		$scope.notificationFrequency= "I";
		$scope.date= "Select option";
		$scope.day= "";
		$scope.month= "";
		$scope.message = "";
                $scope.otherLanguageMessage = "";
                $scope.instant = "";
		$scope.mediaCommunication= "";
		$scope.assignee="";
                $scope.email="";
                $scope.mobileNo="";
	}
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.messagereadOnly = true;
        $scope.otherLangMessagereadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
         $scope.notificationIDSearchreadOnly = true;
          $scope.instantreadOnly = true;
	$scope.assigneereadOnly = true;
        $scope.emailreadOnly=true;
        $scope.mobileNoreadOnly=true;
	// Screen Specific Scope Ends
	
	
	// Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
    $scope.CheckerRemarksReadonly = true;
	// Generic Field Ends

}
// Cohesive save Framework Starts
function fnNotificationSave() {
	var emptyNotification = {

		notificationID: "",
                instituteName:"",
                instituteID: "",
		notificationType: "Select option",
		notificationFrequency: "I",
		date: "Select option",
		day: "",
		month: "",
		message:"",
                otherLanguageMessage : "",
                instant:"",
		mediaCommunication: "",
		assignee: "",
                email:"",
                mobileNo:""
	};
	// Screen Specific DataModel Starts									 
	var dataModel = emptyNotification;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
         if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
        if($scope.notificationID!=null)
	dataModel.notificationID = $scope.notificationID;
        if($scope.notificationType!=null)
	dataModel.notificationType = $scope.notificationType;
        if($scope.notificationFrequency!=null)
	dataModel.notificationFrequency = $scope.notificationFrequency;
        if($scope.date!=null)
	dataModel.date = $scope.date;
         if($scope.day!=null)
	dataModel.day = $scope.day;
        if($scope.month!=null)
	dataModel.month = $scope.month;
          if($scope.message!=null)
	dataModel.message = $scope.message;
    if($scope.otherLanguageMessage!=null)
	dataModel.otherLanguageMessage = $scope.otherLanguageMessage;
        if($scope.instant!=null)
	dataModel.instant = $scope.instant;
         if($scope.mediaCommunication!=null)
	dataModel.mediaCommunication = $scope.mediaCommunication;
        if($scope.assignee!=null)
	dataModel.assignee = $scope.assignee;
       if($scope.email!=null)
	dataModel.email = $scope.email;
       if($scope.mobileNo!=null)
	dataModel.mobileNo = $scope.mobileNo;
	//dataModel.audit = $scope.audit;
	// Screen Specific DataModel Ends

	var response = fncallBackend('Notification', parentOperation, dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	return true;
}

// Cohesive save Framework Ends


function fninstantEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate")),
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate"));
		$scope.$apply();
}






function fnNotificationpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    
     if (response.header.status == 'success') {
     
               if(response.header.operation == 'Create-Default')
              
		{
		  $scope.notificationID = response.body.notificationID;
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
		$scope.instituteIDreadOnly = true;
     	        $scope.instituteNamereadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
		$scope.notificationIDreadOnly = true;
		$scope.notificationTypereadOnly = true;
		$scope.notificationFrequencyreadOnly = true;
                 $scope.notificationIDSearchreadOnly = true;
		$scope.datereadOnly = true;
		$scope.dayreadOnly = true;
		$scope.monthreadOnly = true;
		$scope.messagereadOnly = true;
                $scope.otherLangMessagereadOnly = true;
		$scope.mediaCommunicationreadOnly = true;
		$scope.assigneereadOnly = true;
                $scope.instantreadOnly = true;
                $scope.emailreadOnly=true;
                $scope.mobileNoreadOnly=true;
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
		$scope.notificationID ="";
                $scope.notificationType ="Select option";
                $scope.notificationFrequency ="I";
                $scope.date ="";
                $scope.day ="";
                $scope.month ="";
                $scope.message ="";
                $scope.otherLanguageMessage ="";
                $scope.mediaCommunication ="";
                $scope.assignee ="";
                $scope.email="";
                $scope.mobileNo="";
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.notificationID = response.body.notificationID;
		$scope.notificationType = response.body.notificationType;
		$scope.notificationFrequency = response.body.notificationFrequency;
		$scope.date = response.body.date;
		$scope.day = response.body.day;
		$scope.month = response.body.month;
		$scope.message = response.body.message;
                $scope.otherLanguageMessage = response.body.otherLanguageMessage;
                $scope.instant = response.body.instant;
		$scope.mediaCommunication = response.body.mediaCommunication;
                $scope.audit=response.audit;
		$scope.assignee = response.body.assignee;
                $scope.email=response.body.email;
                $scope.mobileNo=response.body.mobileNo;
		// Screen Specific scope Response Ends
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