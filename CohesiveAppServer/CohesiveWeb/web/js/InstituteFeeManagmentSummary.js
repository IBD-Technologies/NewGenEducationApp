/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller---------------------------------------
var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	//Screen Specific Scope starts
	$scope.feeType = 'Select option';
	$scope.feeID = "",
	$scope.FeeMaster = [{
		FeeId:"",
		FeeType:""
	}];
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.authStat = '';
	$scope.fees = Institute.FeeMaster;
	$scope.AuthType = Institute.AuthStatusMaster;
	$scope.feeTypereadOnly = false;
	$scope.feeIDReadOnly = false;
	$scope.amountReadOnly = false;
	$scope.dueDateReadonly = false;
	$scope.remarksReadonly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	//Screen Specific Scope Ends
	$scope.mvwAddDeteleDisable = true; //Multiple View
	//$scope.ClassSection = ['Select option']; // Std/sec
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes = OperationScopes;
	$scope.selectedIndex =0;
	// Generic Field Ends
	// multiple View Starts
	$scope.InstituteFeeManagementSummaryCurPage = 0;
	$scope.InstituteFeeManagementSummaryTable = null;
	$scope.InstituteFeeManagementSummaryShowObject = null;
	// multiple View Ends
        //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'CFMSummary') {
			if ($scope.InstituteFeeManagementSummaryTable != null && $scope.InstituteFeeManagementSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.InstituteFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.InstituteFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.InstituteFeeManagementSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.InstituteFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.InstituteFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.InstituteFeeManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'CFMSummary') {
			if ($scope.InstituteFeeManagementSummaryTable != null && $scope.InstituteFeeManagementSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.InstituteFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.InstituteFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.InstituteFeeManagementSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.InstituteFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.InstituteFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.InstituteFeeManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'CFMSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
				 
				    feeType: "",
				    dueDate: "",
				    amount: "",
                                     assignee: ""
				};
				if ($scope.InstituteFeeManagementSummaryTable == null)
					$scope.InstituteFeeManagementSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.InstituteFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.InstituteFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.InstituteFeeManagementSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.InstituteFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.InstituteFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.InstituteFeeManagementSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'CFMSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.InstituteFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.InstituteFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.InstituteFeeManagementSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.InstituteFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.InstituteFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.InstituteFeeManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'CFMSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.InstituteFeeManagementSummaryCurPage;
			lsvwObject.tableObject = $scope.InstituteFeeManagementSummaryTable;
			lsvwObject.screenShowObject = $scope.InstituteFeeManagementSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'CFMSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.InstituteFeeManagementSummaryCurPage;
			lsvwObject.tableObject = $scope.InstituteFeeManagementSummaryTable;
			lsvwObject.screenShowObject = $scope.InstituteFeeManagementSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'CFMSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.InstituteFeeManagementSummaryTable);
			
		}
	};

	//Multiple View Scope Function ends 	
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
	MenuName = "InstituteFeeManagementSummary";
        window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "InstituteSummaryEntity";
	
	fnDatePickersetDefault('CFDueDate',fndueDateEventHandler);
	fnsetDateScope();
    selectBoxes= ['authStatus','feeType'];
    fnGetSelectBoxdata(selectBoxes);
});
//Default Load Record Ends


function fnInstituteFeeManagementSummarypostSelectBoxMaster()
{
   
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if(Institute.FeeMaster.length>0)
      {   
	 $scope.fees = Institute.FeeMaster;
	 window.parent.fn_hide_parentspinner();
          $scope.$apply();
    
}
}
// Cohesive Query Framework Starts
function fnInstituteFeeManagementSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.feeID = "";
	$scope.feeType = 'Select option';
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.authStat = '';
	
	$scope.feeTypereadOnly = true;
	$scope.feeIDReadOnly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
	$scope.remarksReadonly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	//$scope.ClassSection = null; // Std/sec
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
//	$scope.selectedIndex =0;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.feeID=$scope.InstituteFeeManagementSummaryTable[$scope.selectedIndex].feeID;
	
	fninvokeDetailScreen('InstituteFeeManagement',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends




// Cohesive View Framework Starts
function fnInstituteFeeManagementSummaryView() {
	var emptyInstituteFeeManagementSummary = {
		filter: {
			feeID: "",
			feeType: 'Select option',
			dueDate: "",
			authStat: ''
		},
		SummaryResult: [{
				
				feeType: "",
				dueDate: "",
				amount: "",
                assignee: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstituteFeeManagementSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        
       if($scope.feeID!=null)
	dataModel.filter.feeID = $scope.feeID;
       if($scope.feeType!=null)
	dataModel.filter.feeType = $scope.feeType;
       if($scope.dueDate!=null)
	dataModel.filter.dueDate = $scope.dueDate;
       if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('InstituteFeeManagementSummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnInstituteFeeManagementSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((
						($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') &
						($scope.dueDate == '' || $scope.dueDate == null) &
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

function fnInstituteFeeManagementSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
				return true;

			break;

		case 'Save':
				return true;

			break;
               case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.InstituteFeeManagementSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}
// Screen Specific Mandatory Validation Ends
//Cohesive back Framework Starts
function fnInstituteFeeManagementSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		 // Screen Specific Scope Starts
	     $scope.feeID = "";
	     $scope.feeType = 'Select option';
	     $scope.amount = "";
	     $scope.dueDate = "";
	     $scope.authStat = '';
	     
	     $scope.mvwAddDeteleDisable = true; //Multiple Vie
	     $scope.InstituteFeeManagementSummaryTable = null;
	     $scope.InstituteFeeManagementSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.feeTypereadOnly = false;
     	    $scope.feeIDReadOnly = false;
    	    $scope.amountReadOnly = false;
	    $scope.dueDateReadonly = false;
	    $scope.remarksReadonly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
		}
	    else {
	    $scope.feeTypereadOnly = true;
     	    $scope.feeIDReadOnly = true;
    	    $scope.amountReadOnly = true;
	    $scope.dueDateReadonly = true;
	    $scope.remarksReadonly = true;
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
	       }
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
}
// Cohesive Back Framework Ends
function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#CFDueDate").datepicker("getDate"));
		$scope.$apply();
}

function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	 $( "#CFDueDate" ).datepicker('setDate','');
	  $scope.dueDate = null;
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'InstituteFeeManagementSummaryTable':
		   
			var InstituteFeeManagementSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     InstituteFeeManagementSummaryTable[index] = new Object();
					 InstituteFeeManagementSummaryTable[index].idx=index;
					 InstituteFeeManagementSummaryTable[index].checkBox=false;
					 InstituteFeeManagementSummaryTable[index].feeID=value.feeID;
					 InstituteFeeManagementSummaryTable[index].feeType=value.feeType;
					 InstituteFeeManagementSummaryTable[index].amount=value.amount;
					 InstituteFeeManagementSummaryTable[index].dueDate=value.dueDate;
                                          InstituteFeeManagementSummaryTable[index].assignee=value.assignee;
					}
			return InstituteFeeManagementSummaryTable;
			break ;
		}
	}
	
function fnInstituteFeeManagementSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.feeTypereadOnly = true;
     	        $scope.feeIDReadOnly = true;
    	        $scope.amountReadOnly = true;
	        $scope.dueDateReadonly = true;
	        $scope.remarksReadonly = true;
	        $scope.authStatreadOnly = true;
	        $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
	        	$scope.feeType =response.body.filter.feeType;
                $scope.dueDate =response.body.filter.dueDate;
		        $scope.authStat =response.body.filter.authStat;
               //Multiple View Response Starts 
		$scope.InstituteFeeManagementSummaryTable=fnConvertmvw('InstituteFeeManagementSummaryTable',response.body.SummaryResult);
		$scope.InstituteFeeManagementSummaryCurPage = 1;
		$scope.InstituteFeeManagementSummaryShowObject=$scope.fnMvwGetCurPageTable('CFMSummary');
		//Multiple View Response Ends 
        }
		return true;

}