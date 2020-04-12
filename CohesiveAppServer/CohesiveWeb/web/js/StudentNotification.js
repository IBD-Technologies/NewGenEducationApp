/* 
    Author     :IBD Technologies
	
*/

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.notificationID = "";
        $scope.notificationDate = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "";
	$scope.date = "Select option";
	$scope.day = "";
	$scope.month = "";
	$scope.message = "";
	$scope.mediaCommunication = "";
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
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.notificationIDSearchreadOnly = true;
	$scope.notificationIDreadOnly = true;
        $scope.notificationDatereadOnly = true;
        $( "#notificationDate" ).datepicker( "option", "disabled", true );
        $scope.instantreadOnly = true;
        $( "#instantDate" ).datepicker( "option", "disabled", true );
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.messagereadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
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
//	$scope.fnStudentSearch = function () {
//		var searchCallInput = {
//			mainScope: null,
//			searchType:null
//		};
//		searchCallInput.mainScope = $scope;
//		searchCallInput.searchType = 'Group';
//		SeacrchScopeTransfer.setMainScope($scope);
//		searchCallService.searchLaunch(searchCallInput);
//	}
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
    $scope.notificationStatusCurPage = 0;
	$scope.notificationStatusTable = null;
	$scope.notificationStatusShowObject = null;    
//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'notificationStatus') {
			if ($scope.notificationStatusTable != null && $scope.notificationStatusTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.notificationStatusCurPage;
				lsvwObject.tableObject = $scope.notificationStatusTable;
				lsvwObject.screenShowObject = $scope.notificationStatusShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.notificationStatusCurPage = lsvwObject.curPage;
				$scope.notificationStatusTable = lsvwObject.tableObject;
				$scope.notificationStatusShowObject = lsvwObject.screenShowObject;
				
			}
			
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'notificationStatus') {
			if ($scope.notificationStatusTable != null && $scope.notificationStatusTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.notificationStatusCurPage;
				lsvwObject.tableObject = $scope.notificationStatusTable;
				lsvwObject.screenShowObject = $scope.notificationStatusShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.notificationStatusCurPage = lsvwObject.curPage;
				$scope.notificationStatusTable = lsvwObject.tableObject;
				$scope.notificationStatusShowObject = lsvwObject.screenShowObject;
				
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
					
			if (tableName == 'notificationStatus') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					status:[{date:"",endPoint:"",status:"",error:""}]
			                      	
				};
				if ($scope.notificationStatusTable == null)
					$scope.notificationStatusTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.notificationStatusCurPage;
				lsvwObject.tableObject = $scope.notificationStatusTable;
				lsvwObject.screenShowObject = $scope.notificationStatusShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.notificationStatusCurPage = lsvwObject.curPage;
				$scope.notificationStatusTable = lsvwObject.tableObject;
				$scope.notificationStatusShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'notificationStatus') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.notificationStatusCurPage;
				lsvwObject.tableObject = $scope.notificationStatusTable;
				lsvwObject.screenShowObject = $scope.notificationStatusShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.notificationStatusCurPage = lsvwObject.curPage;
				$scope.notificationStatusTable = lsvwObject.tableObject;
				$scope.notificationStatusShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {
		if (tableName == 'notificationStatus') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.notificationStatusCurPage;
			lsvwObject.tableObject = $scope.notificationStatusTable;
			lsvwObject.screenShowObject = $scope.notificationStatusShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {
      if (tableName == 'notificationStatus') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.notificationStatusCurPage;
			lsvwObject.tableObject = $scope.notificationStatusTable;
			lsvwObject.screenShowObject = $scope.notificationStatusShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		
	   if (tableName == 'notificationStatus') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.notificationStatusTable);
			
		}
	};
	//Multiple View Scope Function ends 

       
});
//--------------------------------------------------------------------------------------------------------------

//-------This is to Load default record Starts--------------------------------------	
$(document).ready(function () {
	
	 MenuName = "StudentNotification"; 
         selectBypassCount=0;
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="Student";
        
         selectBoxes= ['notificationTypes','frequency','notificationMonth','notificationDays','communication'];
	 
    fnGetSelectBoxdata(selectBoxes);
    fnDatePickersetDefault('instantDate',fninstantEventHandler);
    fnDatePickersetDefault('notificationDate',fnnotificationDateEventHandler);
    fnsetDateScope();
        $( "#notificationDate" ).datepicker( "option", "disabled", true );
        $( "#instantDate" ).datepicker( "option", "disabled", true );
	//-------This is to Load default record Ends--------------------------------------

});

function fnStudentNotificationpostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.NotificationMaster.length>0)
      {    
       $scope.Types = Institute.NotificationMaster;
     
	  window.parent.fn_hide_parentspinner();   
    
    if (window.parent.StudentNotificationSummarykey.notificationID !==null && window.parent.StudentNotificationSummarykey.notificationID !=='')
	{
         if (window.parent.StudentNotificationSummarykey.studentID !==null && window.parent.StudentNotificationSummarykey.studentID !=='')
	{   
            if (window.parent.StudentNotificationSummarykey.date !==null && window.parent.StudentNotificationSummarykey.date !=='')
	{
		var notificationID=window.parent.StudentNotificationSummarykey.notificationID;
		var studentID=window.parent.StudentNotificationSummarykey.studentID;
                var notificationDate=window.parent.StudentNotificationSummarykey.date;
		window.parent.StudentNotificationSummarykey.notificationID =null;
                window.parent.StudentNotificationSummarykey.studentID =null;
                window.parent.StudentNotificationSummarykey.date =null;
		fnshowSubScreen(notificationID,studentID,notificationDate);
            }
        }
	}  
         $scope.$apply();
    }  
    }
}



function fnshowSubScreen(notificationID,studentID,notificationDate)
{
    subScreen = true;
var emptyNotification = {

		studentID: "",
		studentName: "",
		notificationID: "",
                notificationDate:"",
		notificationType: "Select option",
		notificationFrequency: "",
		date: "Select option",
                instant:"",
		day: "",
		month: "",
		message: "",
		mediaCommunication: "",
		status :[
			     {date:"",endPoint:"",status:"",error:""}
			     ]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyNotification;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(notificationID!==null&&notificationID!==""){
            
            if(studentID!==null&&studentID!==""){
                
                if(notificationDate!==null&&notificationDate!==""){
                
                     dataModel.notificationID = notificationID;
                     dataModel.studentID=studentID;
                     dataModel.notificationDate=notificationDate;
             
                // Screen Specific DataModel Ends
                var response = fncallBackend('StudentNotification', 'View', dataModel, [{entityName:"studentID",entityValue:dataModel.studentID}], $scope);
               }
        }
    }
        return true;
}
// Cohesive Query Framework Starts
function fnStudentNotificationQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentID ="";
	$scope.studentName ="";
	$scope.notificationID = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "";
	$scope.date = "Select option";
	$scope.day = "";
	$scope.month = "";
	$scope.message = "";
	$scope.mediaCommunication = "";
	$scope.instant = "";
    $scope.studentIDreadOnly = false;
    $scope.instantreadOnly = true;
    $( "#instantDate" ).datepicker( "option", "disabled", true );
    $scope.studentNameSearchreadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.notificationIDreadOnly = false;
	$scope.notificationTypereadOnly = true;
        $scope.notificationDatereadOnly=false;
        $( "#notificationDate" ).datepicker( "option", "disabled", false );
	$scope.notificationFrequencyreadOnly = true;
        
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
	$scope.messagereadOnly = true;
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
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	
	//Multiple View Starts
	$scope.notificationStatusCurPage = 0;
	$scope.notificationStatusTable = null;
	$scope.notificationStatusShowObject = null;
	// Multiple View Ends
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts	
function fnStudentNotificationView() {
	var emptyNotification = {
		studentID: "",
		studentName: "",
		notificationID: "",
                notificationDate:"",
		notificationType: "Select option",
		notificationFrequency: "",
		date: "Select option",
                instant:"",
		day: "",
		month: "",
		message: "",
		mediaCommunication: "",
		status :[
			     {date:"",endPoint:"",status:"",error:""}
			     ]
	};
	// Screen Specific DataModel Starts							 
	var dataModel = emptyNotification;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//      if($scope.studentID!==null&&$scope.studentID!==""){
	dataModel.studentID = $scope.studentID;
        dataModel.studentName = $scope.studentName;
    
      if($scope.notificationID!==null&&$scope.notificationID!==""){
	dataModel.notificationID = $scope.notificationID;
        
      if($scope.notificationDate!==null&&$scope.notificationDate!==""){
	dataModel.notificationDate = $scope.notificationDate;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentNotification', 'View', dataModel,[{entityName:"studentID",entityValue:dataModel.studentID}],$scope);
	
        }
    }
//    }
       return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts	  
function fnStudentNotificationMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//                     if ($scope.studentID == '' || $scope.studentID == null) {
//		  		fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
//		       if ($scope.studentName == '' || $scope.studentName == null) {
//		 		fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
//				return false;
//			}
                        
                        if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
			if ($scope.notificationID == '' || $scope.notificationID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification ID']);
				return false;
			}
			
                        if ($scope.notificationDate == '' || $scope.notificationDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification Date']);
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

			if ($scope.notificationID == '' || $scope.notificationID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification ID']);
				return false;
			}
			if ($scope.notificationType == '' || $scope.notificationType == null || $scope.notificationType == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification Type']);
				return false;
			}
            if ($scope.message == '' || $scope.message == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification Message in General Tab']);
				return false;
			}
            if ($scope.mediaCommunication == '' || $scope.mediaCommunication == null || $scope.mediaCommunication == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Mode in Frequency Tab']);
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
				fn_Show_Exception_With_Param('FE-VAL-001', ['Instant Date in General Tab']);
				return false;
			}
                        
                       
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends

function fnStudentNotificationDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			/*if (!fnDefaultNotifificationID($scope))
				return false; */

			break;

		case 'Save':
			if (!fnDefaultNotifificationID($scope))
				return false;

			break;


	}
	return true;
}


// Cohesive Create Framework Starts
function fnStudentNotificationCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.notificationID = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "";
	$scope.date = "Select option";
	$scope.day = "";
	$scope.month = "";
	$scope.message = "";
	$scope.mediaCommunication = "";
	$scope.instant = "";
    $scope.Types = Institute.NotificationMaster;
	$scope.frequencies = Institute.NotificationFrequency;
	$scope.Communications = Institute.MediaCommunication;
	$scope.notificationIDreadOnly = false;
	$scope.studentIDreadOnly = false;
    $scope.instantreadOnly = false;
    $( "#instantDate" ).datepicker( "option", "disabled", false );
	$scope.studentNamereadOnly = false;
    $scope.studentNameSearchreadOnly = false;
    $scope.notificationIDSearchreadOnly = true;
	$scope.notificationTypereadOnly = false;
	$scope.notificationFrequencyreadOnly = false;
	$scope.datereadOnly = false;
	$scope.dayreadOnly = false;
	$scope.monthreadOnly = false;
	$scope.messagereadOnly = false;
	$scope.mediaCommunicationreadOnly = false;
    // Screen Specific Scope Ends
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	// Generic Field Ends
    $scope.mvwAddDeteleDisable = true; 
	$scope.notificationStatusTable=null;
	$scope.notificationStatusShowObject=null;
    return true;
}
// Cohesive Create Framework Ends

// Cohesive Edit Framework Starts
function fnStudentNotificationEdit() {
	
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.mvwAddDeteleDisable = true; 
	//Generic Field Starts

	// Screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
    $scope.instantreadOnly = false;
    $( "#instantDate" ).datepicker( "option", "disabled", false );
    $scope.notificationIDSearchreadOnly = false;
	$scope.studentNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = false;
    $scope.studentNameSearchreadOnly = true;
	$scope.notificationFrequencyreadOnly = false;
	$scope.datereadOnly = false;
	$scope.dayreadOnly = false;
	$scope.monthreadOnly = false;
	$scope.mediaCommunicationreadOnly = false;
	$scope.messagereadOnly = false;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Edit Framework Ends


// Cohesive Notification Framework Starts
function fnStudentNotificationDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.mvwAddDeteleDisable = true; 
	// Generic Field Ends
	// screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
    $scope.studentNamereadOnly = true;
	$scope.studentNameSearchreadOnly = true;
    $scope.notificationIDSearchreadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
    $scope.instantreadOnly = true;
    $( "#instantDate" ).datepicker( "option", "disabled", true );
	// screen Specific Scope Ends
	return true;
}
// Cohesive Notification Framework Ends

// Cohesive Authorisation Framework Starts
function fnStudentNotificationAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// generic field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	$scope.mvwAddDeteleDisable = true;
	// Generic field Ends

	// screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
    $scope.studentNameSearchreadOnly = true;
    $scope.notificationIDSearchreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
    $scope.instantreadOnly = true;
    $( "#instantDate" ).datepicker( "option", "disabled", true );
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Authorisation Framework Ends

// Cohesive Reject Framework Starts
function fnStudentNotificationReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	$scope.mvwAddDeteleDisable = true;
	// Generic Field Ends

	//Screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
    $scope.studentNameSearchreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
    $scope.notificationIDSearchreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
    $scope.instantreadOnly = true;
    $( "#instantDate" ).datepicker( "option", "disabled", true );
	$scope.mediaCommunicationreadOnly = true;
	//Screen Specific Scope Starts
	return true;
}
// Cohesive Reject Framework Ends

// Cohesive Back Framework Starts
function fnStudentNotificationBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	        $scope.studentName= "";
                $scope.studentID= "";
                $scope.notificationID= "";
                $scope.notificationDate= "";
		$scope.notificationType= "Select option";
		$scope.notificationFrequency= "";
		$scope.date= "Select option";
		$scope.day= "";
		$scope.month= "";
		$scope.message = "";
                $scope.instant = "";
		$scope.mediaCommunication= "";
		$scope.notificationStatusTable="";
	        $scope.notificationStatusShowObject="";
		}
     		
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.notificationIDreadOnly = true;
        $scope.notificationDatereadOnly = true;
        $( "#notificationDate" ).datepicker( "option", "disabled", true );
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.datereadOnly = true;
	$scope.dayreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.messagereadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
    $scope.notificationIDSearchreadOnly = true;
    $scope.instantreadOnly = true;
    $( "#instantDate" ).datepicker( "option", "disabled", true );	
	// Screen Specific Scope Ends
	
	
	// Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
    $scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = true;
	// Generic Field Ends

}
// Cohesive save Framework Starts
function fnStudentNotificationSave() {
	var emptyNotification = {
		studentID: "",
		studentName: "",
		notificationID: "",
		notificationType: "Select option",
		notificationFrequency: "",
		date: "Select option",
                instant:"",
		day: "",
		month: "",
		message: "",
		mediaCommunication: "",
		status :[
			     {date:"",endPoint:"",status:"",error:""}
			     ]
	};
	// Screen Specific DataModel Starts									 
	var dataModel = emptyNotification;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
         if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
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
        if($scope.instant!=null)
	dataModel.instant = $scope.instant;
         if($scope.mediaCommunication!=null)
	dataModel.mediaCommunication = $scope.mediaCommunication;
    //dataModel.audit = $scope.audit;
	// Screen Specific DataModel Ends

	var response = fncallBackend('StudentNotification', parentOperation, dataModel, [{entityName:"studentID",entityValue:dataModel.studentID}], $scope);
	return true;
}

// Cohesive save Framework Ends


function fninstantEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate")),
		$scope.$apply();
}
function fnnotificationDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.notificationDate = $.datepicker.formatDate('dd-mm-yy', $("#notificationDate").datepicker("getDate")),
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate"));
        $scope.notificationDate = $.datepicker.formatDate('dd-mm-yy', $("#notificationDate").datepicker("getDate"));
		$scope.$apply();
}

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		
			case 'notificationStatusTable':
		   
			var notificationStatusTable = new Array();
			 responseObject.forEach(fnConvert2);
			 function fnConvert2(value,index,array){
				     notificationStatusTable[index] = new Object();
					 notificationStatusTable[index].idx=index;
					 notificationStatusTable[index].checkBox=false;
					 notificationStatusTable[index].date=value.date;
					 notificationStatusTable[index].endPoint=value.endPoint;
					 notificationStatusTable[index].status=value.status;
					 notificationStatusTable[index].error=value.error;
					 
					}
			return notificationStatusTable;
			break ;
		}
	}




function fnStudentNotificationpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    
     if (response.header.status == 'success') {
     
               
		// Specific Screen Scope Starts
            $scope.MakerRemarksReadonly = true;
	    $scope.CheckerRemarksReadonly = true;
            $scope.studentIDreadOnly = true;
     	    $scope.studentNamereadOnly = true;
            $scope.studentNameSearchreadOnly = true;
	    $scope.notificationIDreadOnly = true;
            $scope.notificationDatereadOnly = true;
            $( "#notificationDate" ).datepicker( "option", "disabled", true );
	    $scope.notificationTypereadOnly = true;
            $scope.notificationFrequencyreadOnly = true;
            $scope.notificationIDSearchreadOnly = true;
	    $scope.datereadOnly = true;
	    $scope.dayreadOnly = true;
            $scope.monthreadOnly = true;
            $scope.messagereadOnly = true;
            $scope.mediaCommunicationreadOnly = true;
		
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.studentID = "";
		        $scope.studentName ="";
		        $scope.notificationID ="";
                $scope.notificationType ="Select option";
                $scope.notificationFrequency ="";
                $scope.date ="";
                $scope.day ="";
                $scope.month ="";
                $scope.message ="";
                $scope.mediaCommunication ="";
				 $scope.notificationStatusShowObject=null;
                $scope.audit = {};
		 }
                else
                {
                $scope.studentID = response.body.studentID;
		$scope.studentName = response.body.studentName;
		$scope.notificationID = response.body.notificationID;
                $scope.notificationDate = response.body.notificationDate;
		$scope.notificationType = response.body.notificationType;
		$scope.notificationFrequency = response.body.notificationFrequency;
		$scope.date = response.body.date;
		$scope.day = response.body.day;
		$scope.month = response.body.month;
		$scope.message = response.body.message;
                $scope.instant = response.body.instant;
		$scope.mediaCommunication = response.body.mediaCommunication;
                
                if(response.body.status!=null){
                
	        	$scope.notificationStatusTable = fnConvertmvw('notificationStatusTable',response.body.status);
		
                 }else{
                     
                     $scope.notificationStatusTable=null;
                 }
            
                $scope.notificationStatusCurPage = 1
		$scope.notificationStatusShowObject=$scope.fnMvwGetCurPageTable('notificationStatus');
                $scope.audit=response.audit;
		
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

