/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.class = 'Select option';
//	$scope.date = "";
        $scope.fromDate = "";
        $scope.toDate = "";
	$scope.authStat = "";
	
	$scope.feeType = "Select option";
	$scope.ClassSection = ['Select option']; // Std/sec
	$scope.AuthType = Institute.AuthStatusMaster;
	$scope.mvwAddDeteleDisable = true; //Multiple View
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
	// Screen Specific Scope Start
        $scope.classReadonly = false;
//	$scope.dateReadOnly = false;
        $scope.fromDatereadOnly = false;
        $scope.toDatereadOnly = false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.ClassAttendanceSummaryCurPage = 0;
	$scope.ClassAttendanceSummaryTable = null;
	$scope.ClassAttendanceSummaryShowObject = null;
	// multiple View Ends
       //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'ClassAttendanceSummary') {
			if ($scope.ClassAttendanceSummaryTable != null && $scope.ClassAttendanceSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.ClassAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassAttendanceSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.ClassAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.ClassAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.ClassAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'ClassAttendanceSummary') {
			if ($scope.ClassAttendanceSummaryTable != null && $scope.ClassAttendanceSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.ClassAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassAttendanceSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.ClassAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.ClassAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.ClassAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassAttendanceSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					class: "",
					date: "",
					authStat: "",
					recordStat: ""
				};
				if ($scope.ClassAttendanceSummaryTable == null)
					$scope.ClassAttendanceSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassAttendanceSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.ClassAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.ClassAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.ClassAttendanceSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassAttendanceSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassAttendanceSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.ClassAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.ClassAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.ClassAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'ClassAttendanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassAttendanceSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassAttendanceSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassAttendanceSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'ClassAttendanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassAttendanceSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassAttendanceSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassAttendanceSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'ClassAttendanceSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.ClassAttendanceSummaryTable);
			
		}
	};

	//Multiple View Scope Function ends 	
});
//--------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	MenuName = "ClassAttendanceSummary";
	  window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="ClassSummaryEnity";
	fnDatePickersetDefault('fromDate',fninstantEventHandler);
        fnDatePickersetDefault('toDate',fninstantEventHandler);
        fnsetDateScope();
	selectBoxes= ['class','authStatus'];
        fnGetSelectBoxdata(selectBoxes);
      
       
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnClassAttendanceSummarypostSelectBoxMaster()
{
 var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   if(Institute.ClassMaster.length>0)
      {    
         $scope.classes=Institute.ClassMaster;
	 window.parent.fn_hide_parentspinner();
         window.parent.fn_hide_parentspinner();
         $scope.$apply();
}
}
function fnClassAttendanceSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.class = 'Select option';
//	$scope.date = "";
        $scope.fromDate = "";
        $scope.toDate = "";
	$scope.authStat = "";
	
	$scope.classReadonly = true;
//	$scope.dateReadOnly = true;
        $scope.fromDatereadOnly = true;
        $scope.toDatereadOnly = true;
        $( "#fromDate" ).datepicker( "option", "disabled", true );
        $( "#toDate" ).datepicker( "option", "disabled", true );
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
	lscreenKeyObject.class=$scope.ClassAttendanceSummaryTable[$scope.selectedIndex].class;
	lscreenKeyObject.date=$scope.ClassAttendanceSummaryTable[$scope.selectedIndex].date;
	fninvokeDetailScreen('ClassAttendance',lscreenKeyObject,$scope);		
	return true;
}
// Cohesive Query Framework Ends
// Cohesive View Framework Starts
function fnClassAttendanceSummaryView() {
	var emptyClassAttendanceSummary = {
		filter: {
			class: 'Select option',
			fromDate: "",
                        toDate:"",
			authStat: 'Select option',
			recordStat: 'Select option'
		},
		SummaryResult: [{
				class: "",
				date:"",
				authStat: "",
				recordStat: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyClassAttendanceSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View";
        if($scope.class!=null)
	dataModel.filter.class = $scope.class;
        if($scope.fromDate!=null)
	dataModel.filter.fromDate = $scope.fromDate;
        if($scope.toDate!=null)
	dataModel.filter.toDate = $scope.toDate;
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
        if($scope.recordStat!=null)
	dataModel.filter.recordStat = $scope.recordStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ClassAttendanceSummary', 'View', dataModel, [{entityName:"class",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnClassAttendanceSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					    if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &                 
						($scope.fromDate == '' || $scope.fromDate == null) &
                                                ($scope.toDate == '' || $scope.toDate == null) &
						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
		
					{
						fn_Show_Exception('FE-VAL-028');
						return false;
					}
                                        
                                        if($scope.fromDate == '' || $scope.fromDate == null){
                                            
                                               fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
				               return false;
                                        
                                         }
                                        
                                        if($scope.toDate == '' || $scope.toDate == null){
                                            
                                               fn_Show_Exception_With_Param('FE-VAL-001', ['To Date']);
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
function fnClassAttendanceSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.ClassAttendanceSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation Ends
function fnClassAttendanceSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.class = 'Select option';
//	    $scope.date = "";
            $scope.fromDate = "";
            $scope.toDate = "";
	    $scope.authStat = "";
    	    
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.ClassAttendanceSummaryTable = null;
	    $scope.ClassAttendanceSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.classReadonly = false;
//    	    $scope.dateReadOnly = false;
            $scope.fromDatereadOnly = false;
            $scope.toDatereadOnly = false;
            $( "#fromDate" ).datepicker( "option", "disabled", false );
            $( "#toDate" ).datepicker( "option", "disabled", false );
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
		}
	    else {
            $scope.classReadonly = false;
//            $scope.dateReadOnly = true;
            $scope.fromDatereadOnly = true;
            $scope.toDatereadOnly = true;
            $( "#fromDate" ).datepicker( "option", "disabled", true );
            $( "#toDate" ).datepicker( "option", "disabled", true );
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
	       }
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
                // Generic Scope Ends	
		
}
// Cohesive Create Framework Ends
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

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'ClassAttendanceSummaryTable':
		   
			var ClassAttendanceSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				         ClassAttendanceSummaryTable[index] = new Object();
					 ClassAttendanceSummaryTable[index].idx=index;
					 ClassAttendanceSummaryTable[index].checkBox=false;
					 ClassAttendanceSummaryTable[index].class=value.class;
					 ClassAttendanceSummaryTable[index].date=value.date;
					 ClassAttendanceSummaryTable[index].authStat=value.authStat;
					 ClassAttendanceSummaryTable[index].recordStat=value.recordStat;
					}
			return ClassAttendanceSummaryTable;
			break ;
		}
	}
        
        
function fnClassAttendanceSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.classReadonly = true;
//                 $scope.dateReadOnly = true;
                 $scope.fromDatereadOnly = true;
                 $scope.toDatereadOnly = true;
                 $( "#fromDate" ).datepicker( "option", "disabled", true );
                 $( "#toDate" ).datepicker( "option", "disabled", true );
	         $scope.authStatreadOnly = true;
	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.class = response.body.class;
                $scope.fromDate = response.body.fromDate;
                $scope.toDate = response.body.toDate;
		$scope.authStat = response.body.authStat;
                $scope.recordStat = response.body.recordStat;
                //Multiple View Response Starts 
		$scope.ClassAttendanceSummaryTable=fnConvertmvw('ClassAttendanceSummaryTable',response.body.SummaryResult);
		$scope.ClassAttendanceSummaryCurPage = 1;
		$scope.ClassAttendanceSummaryShowObject=$scope.fnMvwGetCurPageTable('ClassAttendanceSummary');
		//Multiple View Response Ends 
                // Specific Screen Scope Response Ends	
		return true;

}
}
	
