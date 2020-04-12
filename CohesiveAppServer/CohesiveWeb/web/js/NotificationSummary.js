/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.notificationID = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "";
	$scope.mediaCommunication = "";
	$scope.authStat = "";
	$scope.instant = "";
	$scope.AuthType = Institute.AuthStatusMaster;
	
	$scope.Types = Institute.NotificationMaster;
	$scope.frequencies = Institute.NotificationFrequency;
	$scope.Communications = Institute.MediaCommunication;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.selectedIndex =0;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.notificationIDreadOnly = false;
	$scope.notificationTypereadOnly = false;
    $scope.instantreadOnly = false;
	$scope.notificationFrequencyreadOnly = false;
	$scope.mediaCommunicationreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.NotificationSummaryCurPage = 0;
	$scope.NotificationSummaryTable = null;
	$scope.NotificationSummaryShowObject = null;
	// multiple View Ends
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'notificationSummary') {
			if ($scope.NotificationSummaryTable != null && $scope.NotificationSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.NotificationSummaryCurPage;
				lsvwObject.tableObject = $scope.NotificationSummaryTable;
				lsvwObject.screenShowObject = $scope.NotificationSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.NotificationSummaryCurPage = lsvwObject.curPage;
				$scope.NotificationSummaryTable = lsvwObject.tableObject;
				$scope.NotificationSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'notificationSummary') {
			if ($scope.NotificationSummaryTable != null && $scope.NotificationSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.NotificationSummaryCurPage;
				lsvwObject.tableObject = $scope.NotificationSummaryTable;
				lsvwObject.screenShowObject = $scope.NotificationSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.NotificationSummaryCurPage = lsvwObject.curPage;
				$scope.NotificationSummaryTable = lsvwObject.tableObject;
				$scope.NotificationSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'notificationSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					notificationType: "",
					instant: ""
				};
				if ($scope.NotificationSummaryTable == null)
					$scope.NotificationSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.NotificationSummaryCurPage;
				lsvwObject.tableObject = $scope.NotificationSummaryTable;
				lsvwObject.screenShowObject = $scope.NotificationSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.NotificationSummaryCurPage = lsvwObject.curPage;
				$scope.NotificationSummaryTable = lsvwObject.tableObject;
				$scope.NotificationSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'notificationSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.NotificationSummaryCurPage;
				lsvwObject.tableObject = $scope.NotificationSummaryTable;
				lsvwObject.screenShowObject = $scope.NotificationSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.NotificationSummaryCurPage = lsvwObject.curPage;
				$scope.NotificationSummaryTable = lsvwObject.tableObject;
				$scope.NotificationSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'notificationSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.NotificationSummaryCurPage;
			lsvwObject.tableObject = $scope.NotificationSummaryTable;
			lsvwObject.screenShowObject = $scope.NotificationSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'notificationSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.NotificationSummaryCurPage;
			lsvwObject.tableObject = $scope.NotificationSummaryTable;
			lsvwObject.screenShowObject = $scope.NotificationSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


        $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'notificationSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.NotificationSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
	MenuName = "NotificationSummary";
	    window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="InstituteSummaryEntity";
	    fnDatePickersetDefault('instantDate',fninstantEventHandler);
        fnsetDateScope();
        selectBoxes= ['notificationTypes','frequency','communication','authStatus'];
		fnGetSelectBoxdata(selectBoxes);
        
});

function fnNotificationSummarypostSelectBoxMaster(){
    
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.NotificationMaster.length>0)
      {    
       $scope.Types = Institute.NotificationMaster;
	  window.parent.fn_hide_parentspinner();    
          $scope.$apply();
}
}
//Default Load Record Ends
// Cohesive Query Framework Starts
function fnNotificationSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.notificationID = "";
	$scope.notificationType = "Select option";
	$scope.notificationFrequency = "";
	$scope.mediaCommunication = "";
	$scope.authStat = "";
	$scope.instant = "";
	$scope.notificationIDreadOnly = true;
	$scope.instantreadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationFrequencyreadOnly = true;
	$scope.mediaCommunicationreadOnly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
//	$scope.selectedIndex =0;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.notificationID=$scope.NotificationSummaryTable[$scope.selectedIndex].notificationID;
	
        
	fninvokeDetailScreen('NotificationSummary',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends
// Cohesive View Framework Starts
function fnNotificationSummaryView() {
	var emptyINotificationSummary = {
		filter:{
		notificationID: "",
		notificationType: "",
		notificationFrequency: "",
		mediaCommunication : "",
		instant : "",
		authStat: ""},
		SummaryResult:
		[{notificationID:"",notificationType:"",notificationFrequency:""}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyINotificationSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        if($scope.notificationID!=null)
	dataModel.filter.notificationID = $scope.notificationID;
        if($scope.notificationType!=null)
	dataModel.filter.notificationType = $scope.notificationType;
        if($scope.instant!=null)
	dataModel.filter.instant = $scope.instant;
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
        if($scope.mediaCommunication!=null)
	dataModel.filter.mediaCommunication = $scope.mediaCommunication;
         
	// Screen Specific DataModel Ends
	var response = fncallBackend('NotificationSummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnNotificationSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.notificationID == '' || $scope.notificationID == null) &                 
					    ($scope.notificationType == '' || $scope.notificationType == null || $scope.notificationType == 'Select option') &
						($scope.instant == '' || $scope.instant == null) &
						($scope.mediaCommunication == '' || $scope.mediaCommunication == null || $scope.mediaCommunication == 'Select option') &
						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
		
					{
						fn_Show_Exception('FE-VAL-028');
						return false;
					}
					return true;
	          break;
	
		     case 'Detail':
		           return true;
		           break;
	}
	return true;
}

function fnNotificationSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.NotificationSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnNotificationSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.notificationID = "";
	    $scope.notificationType = "Select option";
	    $scope.notificationFrequency = "";
	    $scope.mediaCommunication = "";
	    $scope.authStat = "";
	    $scope.instant = "";
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.NotificationSummaryTable = null;
	    $scope.NotificationSummaryShowObject = null;  
	    if($scope.operation== "View")
		{	
	    $scope.notificationIDreadOnly = false;
		  $scope.instantreadOnly = false;
	    $scope.notificationTypereadOnly = false;
	    $scope.notificationFrequencyreadOnly = false;
	    $scope.mediaCommunicationreadOnly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
		}
	    else {
	    $scope.notificationIDreadOnly = true;
		  $scope.instantreadOnly = true;
	    $scope.notificationTypereadOnly = true;
	    $scope.notificationFrequencyreadOnly = true;
	    $scope.mediaCommunicationreadOnly = true;
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
	       }
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
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
	//$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate"));
         $( "#instantDate" ).datepicker('setDate','');
	  $scope.instant = null;
		$scope.$apply();
}
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'NotificationSummaryTable':
		   
			var NotificationSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     NotificationSummaryTable[index] = new Object();
					 NotificationSummaryTable[index].idx=index;
					 NotificationSummaryTable[index].checkBox=false;
					 NotificationSummaryTable[index].notificationID=value.notificationID;
					 NotificationSummaryTable[index].notificationType=value.notificationType;
					 NotificationSummaryTable[index].instant=value.instant;
					 NotificationSummaryTable[index].mode=value.mode;
					 NotificationSummaryTable[index].mediaCommunication=value.mediaCommunication;
					}
			return NotificationSummaryTable;
			break ;
		}
	}
	
function fnNotificationSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		    $scope.notificationIDreadOnly = true;
			$scope.instantreadOnly = true;
	        $scope.notificationTypereadOnly = true;
	        $scope.notificationFrequencyreadOnly = true;
	        $scope.mediaCommunicationreadOnly = true;
	        $scope.authStatreadOnly = true;
	        $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                //$scope.notificationID = response.body.notificationID;
		$scope.notificationType =response.body.filter.notificationType;
                $scope.instant =response.body.filter.instant;
                $scope.mediaCommunication =response.body.filter.mediaCommunication;
		$scope.authStat =response.body.filter.authStat;
               // $scope.recordStat =response.body.recordStat;
               //Multiple View Response Starts 
		$scope.NotificationSummaryTable=fnConvertmvw('NotificationSummaryTable',response.body.SummaryResult);
		$scope.NotificationSummaryCurPage = 1;
		$scope.NotificationSummaryShowObject=$scope.fnMvwGetCurPageTable('notificationSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}