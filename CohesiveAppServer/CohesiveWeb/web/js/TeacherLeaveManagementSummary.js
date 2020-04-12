/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	$scope.from = "";
	$scope.fromNoon = '';
	$scope.to = "";
	$scope.toNoon = '';
	$scope.type = "";
	$scope.reason = "";
	$scope.leaveStatus = "";
	$scope.NoonMaster = Institute.NoonMaster;
	$scope.LeaveMaster = Institute.LeaveMaster;
	$scope.statusMaster = Institute.LeaveMasterStatus;
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
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameInputReadOnly=true;
	$scope.fromReadOnly = false;
	$scope.toReadOnly = false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	$scope.typeReadOnly = false;
	$scope.statusReadonly = false;
	$scope.leaveStatusReadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.TLMSummaryCurPage = 0;
	$scope.TLMSummaryTable = null;
	$scope.TLMSummaryShowObject = null;
	// multiple View Ends
	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'LeaveManagementSummary') {
			if ($scope.TLMSummaryTable != null && $scope.TLMSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.TLMSummaryCurPage;
				lsvwObject.tableObject = $scope.TLMSummaryTable;
				lsvwObject.screenShowObject = $scope.TLMSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.TLMSummaryCurPage = lsvwObject.curPage;
				$scope.TLMSummaryTable = lsvwObject.tableObject;
				$scope.TLMSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'LeaveManagementSummary') {
			if ($scope.TLMSummaryTable != null && $scope.TLMSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.TLMSummaryCurPage;
				lsvwObject.tableObject = $scope.TLMSummaryTable;
				lsvwObject.screenShowObject = $scope.TLMSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.TLMSummaryCurPage = lsvwObject.curPage;
				$scope.TLMSummaryTable = lsvwObject.tableObject;
				$scope.TLMSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'LeaveManagementSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					teacherID: "",
				    teacherName: "",
				    from:"",
				    to: "",
				    leaveType: ""
				};
				if ($scope.TLMSummaryTable == null)
					$scope.TLMSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TLMSummaryCurPage;
				lsvwObject.tableObject = $scope.TLMSummaryTable;
				lsvwObject.screenShowObject = $scope.TLMSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.TLMSummaryCurPage = lsvwObject.curPage;
				$scope.TLMSummaryTable = lsvwObject.tableObject;
				$scope.TLMSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'LeaveManagementSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TLMSummaryCurPage;
				lsvwObject.tableObject = $scope.TLMSummaryTable;
				lsvwObject.screenShowObject = $scope.TLMSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.TLMSummaryCurPage = lsvwObject.curPage;
				$scope.TLMSummaryTable = lsvwObject.tableObject;
				$scope.TLMSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'LeaveManagementSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TLMSummaryCurPage;
			lsvwObject.tableObject = $scope.TLMSummaryTable;
			lsvwObject.screenShowObject = $scope.TLMSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'LeaveManagementSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TLMSummaryCurPage;
			lsvwObject.tableObject = $scope.TLMSummaryTable;
			lsvwObject.screenShowObject = $scope.TLMSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'LeaveManagementSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.TLMSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
	
});
$(document).ready(function () {
	MenuName = "TeacherLeaveManagementSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="TeacherSummaryEntity";
        window.parent.fn_hide_parentspinner();      
	    fnDatePickersetDefault('fromDate',fndatePickerfromEventHandler);
        fnDatePickersetDefault('toDate',fndatePickertoEventHandler);
        fnsetDateScope();
        selectBoxes= ['authStatus'];
        fnGetSelectBoxdata(selectBoxes);
	
	
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts
function fnTeacherLeaveManagementSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.from = "";
	$scope.to = "";
	$scope.reason = "";
	$scope.leaveStatus = "";
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameInputReadOnly=true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $( "#fromDate" ).datepicker( "option", "disabled", true );
        $( "#toDate" ).datepicker( "option", "disabled", true );
	$scope.typeReadOnly = true;
	$scope.statusReadonly = true;
	$scope.leaveStatusReadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.teacherID=$scope.TLMSummaryTable[$scope.selectedIndex].teacherID;
        lscreenKeyObject.from=$scope.TLMSummaryTable[$scope.selectedIndex].from;
        lscreenKeyObject.to=$scope.TLMSummaryTable[$scope.selectedIndex].to;
	
	fninvokeDetailScreen('TeacherLeaveManagement',lscreenKeyObject,$scope);
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnTeacherLeaveManagementSummaryView() {
	var emptyTeacherLeaveManagementSummary = {
		filter: {
			teacherName: "",
			teacherID: "",
			from: "",
			to: "",
//			leaveType: "",
			leaveStatus: ""
		},
		SummaryResult: [{
				teacherID: "",
				teacherName: "",
				from:"",
				to: "",
				leaveType: "",
                                leaveStatus:""
			}],
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTeacherLeaveManagementSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View";
        if($scope.teacherID!=null)
	dataModel.filter.teacherID = $scope.teacherID;
        if($scope.teacherName!=null)
	dataModel.filter.teacherName = $scope.teacherName;
        if($scope.leaveType!=null)
	dataModel.filter.leaveType = $scope.leaveType;
        if($scope.from!=null)
	dataModel.filter.from = $scope.from;
        if($scope.to!=null)
	dataModel.filter.to = $scope.to;
        if($scope.leaveStatus!=null)
	dataModel.filter.leaveStatus = $scope.leaveStatus;
	// Screen Specific DataModel Ends
	var response = fncallBackend('TeacherLeaveManagementSummary', 'View', dataModel, [{entityName:"teacherID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnTeacherLeaveManagementSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.teacherName == '' || $scope.teacherName == null) &                 
//					    ($scope.from == '' || $scope.from == null) &
//						($scope.to == '' || $scope.to == null) &
//						($scope.leaveType == '' || $scope.leaveType == null || $scope.leaveType == 'Select option') &
//						($scope.leaveStatus == '' || $scope.leaveStatus == null || $scope.leaveStatus == 'Select option')))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
                                if ($scope.from == '' || $scope.from == null) {

                                                                fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
                                                                return false;
                                                        }
                              if ($scope.to == '' || $scope.to == null) {
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

function fnTeacherLeaveManagementSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;
                case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.TLMSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnTeacherLeaveManagementSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
		$scope.teacherName = "";
	    $scope.teacherID = "";
	    $scope.from = "";
	    $scope.to = "";
	    $scope.reason = "";
	    $scope.leaveStatus = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.TLMSummaryTable = "";
		$scope.TLMSummaryShowObject = "";  
		if($scope.operation== "View")
		{	
	     $scope.teacherNamereadOnly = false;
             $scope.teacherNameInputReadOnly=true;
	     $scope.fromReadOnly = false;
	     $scope.toReadOnly = false;
             $( "#fromDate" ).datepicker( "option", "disabled", false );
             $( "#toDate" ).datepicker( "option", "disabled", false );
	     $scope.typeReadOnly = false;
	     $scope.statusReadonly = false;
	     $scope.leaveStatusReadOnly = false;
	     $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
             $scope.teacherNamereadOnly = true;
             $scope.teacherNameInputReadOnly=true;
	     $scope.fromReadOnly = true;
	     $scope.toReadOnly = true;
             $( "#fromDate" ).datepicker( "option", "disabled", true );
             $( "#toDate" ).datepicker( "option", "disabled", true );
	     $scope.typeReadOnly = true;
	     $scope.statusReadonly = true;
	     $scope.leaveStatusReadOnly = true;
	     $scope.recordStatreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
         // Generic Scope Ends	
}
// Cohesive Create Framework Ends
function fndatePickerfromEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.from = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate"));
		$scope.$apply();
}		
function fndatePickertoEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.to = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate"));
		$scope.$apply();
}	
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();


        $( "#fromDate" ).datepicker('setDate','');
	     $scope.from = null;

        $( "#toDate" ).datepicker('setDate','');
	    $scope.to = null;

	/*$scope.from = $.datepicker.formatDate('dd-mm-yy', $("#leaveFrom").datepicker("getDate"));
	$scope.to = $.datepicker.formatDate('dd-mm-yy', $("#leaveTo").datepicker("getDate"));*/
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'TLMSummaryTable':
		   
			var TLMSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     TLMSummaryTable[index] = new Object();
					 TLMSummaryTable[index].idx=index;
					 TLMSummaryTable[index].checkBox=false;
					 TLMSummaryTable[index].teacherID=value.teacherID;
                                         TLMSummaryTable[index].teacherName=value.teacherName;
					 TLMSummaryTable[index].from=value.from;
					 TLMSummaryTable[index].to=value.to;
					 TLMSummaryTable[index].leaveType=value.type;
                                         TLMSummaryTable[index].leaveStatus=value.leaveStatus;
					}
			return TLMSummaryTable;
			break ;
		}
	}
	
function fnTeacherLeaveManagementSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
	     $scope.teacherNamereadOnly = true;
             $scope.teacherNameInputReadOnly=true;
	     $scope.fromReadOnly = true;
	     $scope.toReadOnly = true;
             $( "#fromDate" ).datepicker( "option", "disabled", true );
             $( "#toDate" ).datepicker( "option", "disabled", true );
	     $scope.typeReadOnly = true;
	     $scope.statusReadonly = true;
	     $scope.leaveStatusReadOnly = true;
	     $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.teacherName = response.body.filter.teacherName;
		$scope.teacherID =response.body.filter.teacherID;
                $scope.from = response.body.filter.from;
                $scope.to =response.body.filter.to;
		$scope.leaveStatus =response.body.filter.leaveStatus;
                //Multiple View Response Starts 
		$scope.TLMSummaryTable=fnConvertmvw('TLMSummaryTable',response.body.SummaryResult);
		$scope.TLMSummaryCurPage = 1;
		$scope.TLMSummaryShowObject=$scope.fnMvwGetCurPageTable('LeaveManagementSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}