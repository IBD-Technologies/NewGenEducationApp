/* 
    Author     :IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.class = "Select option";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.feeID = "";
	$scope.feeType = "Select option";
	$scope.FeeMaster = [{
		FeeId:"",
		FeeType:""
	}];
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.paidDate = "";
	$scope.status = "";
	$scope.authStat = "";
	$scope.fees = Institute.FeeMaster;
	$scope.statusMaster = Institute.FeeStatus;
	$scope.AuthType = Institute.AuthStatusMaster;
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
        $scope.studentNameReadOnly = false;
        $scope.studentNameInputReadOnly=true;
        $scope.classReadonly = false;
	$scope.feeTypereadOnly = false;
	$scope.dueDateReadonly = false;
	$scope.paidDateReadonly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	$scope.statusReadonly = false;
	// Specific Screen Scope Ends
	
	// multiple View Starts
	$scope.StudentFeeManagementSummaryCurPage = 0;
	$scope.StudentFeeManagementSummaryTable = null;
	$scope.StudentFeeManagementSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'SFMSummary') {
			if ($scope.StudentFeeManagementSummaryTable != null && $scope.StudentFeeManagementSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentFeeManagementSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentFeeManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'SFMSummary') {
			if ($scope.StudentFeeManagementSummaryTable != null && $scope.StudentFeeManagementSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentFeeManagementSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentFeeManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'SFMSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
			        studentID:"",
			        class:"",
		            paidDate: "",
		            status: ""
				};
				if ($scope.StudentFeeManagementSummaryTable == null)
					$scope.StudentFeeManagementSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentFeeManagementSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentFeeManagementSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'SFMSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentFeeManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentFeeManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentFeeManagementSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentFeeManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentFeeManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentFeeManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'SFMSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentFeeManagementSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentFeeManagementSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentFeeManagementSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'SFMSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentFeeManagementSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentFeeManagementSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentFeeManagementSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'SFMSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentFeeManagementSummaryTable);
			
		}
	};
    

	//Multiple View Scope Function ends 
	
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


$(document).ready(function () {
	MenuName = "StudentFeeManagementSummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="StudentSummaryEntity";
      
	fnDatePickersetDefault('dueDate', fndueDateEventHandler);
	fnDatePickersetDefault('paidDate', fnpaidDateEventHandler);
        fnsetDateScope();
	selectBoxes= ['feeType','Status','authStatus','class'];
          fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnStudentFeeManagementSummarypostSelectBoxMaster()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
  if(Institute.FeeMaster.length>0 && Institute.ClassMaster.length>0){
     $scope.classes=Institute.ClassMaster;
     $scope.fees = Institute.FeeMaster;
     window.parent.fn_hide_parentspinner(); 
       $scope.$apply(); 

}
}
function fnStudentFeeManagementSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.feeID = "";
	$scope.feeType = "Select option";
	$scope.class = "Select option";
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.paidDate = "";
	$scope.status = "";
	$scope.authStat = "";
	
	$scope.studentNameReadOnly = true;
	$scope.classReadonly = true;
	$scope.feeTypereadOnly = true;
	$scope.dueDateReadonly = true;
	$scope.paidDateReadonly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.statusReadonly = true;
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
	lscreenKeyObject.feeID=$scope.StudentFeeManagementSummaryTable[$scope.selectedIndex].feeID;
        lscreenKeyObject.studentID=$scope.StudentFeeManagementSummaryTable[$scope.selectedIndex].studentID;
	
	fninvokeDetailScreen('StudentFeeManagement',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentFeeManagementSummaryView() {
//	var emptyStudentFeeManagementSummary = {
//		filter: {
//			studentName: "",
//			studentID: "",
//			feeID: "",
//			class: "Select option",
//			feeType: "Select option",
//			dueDate: "",
//		    paidDate: "",
//		    status: "",
//			authStat: ""
//		},
//		SummaryResult: [{
//				studentName: "",
//			    studentID:"",
//		        class: "",
//		        paidDate: "",
//		        status: ""
//			}]
//		
//	};
	var emptyStudentFeeManagementSummary = {
		filter: {
			studentName: "",
			studentID: "",
			feeType: "Select option",
		        status: "",
		},
		SummaryResult: [{
		        feeID: "",
		        dueDate:"",
		        feeAmount: "",
		        paidAmount: ""
			}]
		
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentFeeManagementSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        if($scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
         if($scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
        if($scope.feeID!=null)
	dataModel.filter.feeType = $scope.feeType;
        if($scope.dueDate!=null)
	dataModel.filter.status = $scope.status;

	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentFeeManagementSummary', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentFeeManagementSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.studentName == '' || $scope.studentName == null) &                 
//					    ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') &
//						($scope.dueDate == '' || $scope.dueDate == null) &
//						($scope.paidDate == '' || $scope.paidDate == null) &
//						($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
//						($scope.status == '' || $scope.status == null || $scope.status == 'Select option') &
//						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
                                        if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
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

function fnStudentFeeManagementSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultFeeId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultFeeId($scope))
				return false;

			break;
               case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.StudentFeeManagementSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}

function fnDefaultFeeId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnStudentFeeManagementSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
            $scope.studentName = "";
            $scope.studentID = "";
	    $scope.feeID = "";
	    $scope.feeType = "Select option";
	    $scope.amount = "";
	    $scope.dueDate = "";
	    $scope.paidDate = "";
	    $scope.class = "Select option";
	    $scope.status = "";
		$scope.authStat = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.StudentFeeManagementSummaryTable = null;
		$scope.StudentFeeManagementSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	   $scope.studentNameReadOnly = false;
	   $scope.classReadonly = false;
	   $scope.feeTypereadOnly = false;
	   $scope.dueDateReadonly = false;
	   $scope.paidDateReadonly = false;
	   $scope.authStatreadOnly = false;
	   $scope.recordStatreadOnly = false;
	   $scope.statusReadonly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.studentNameReadOnly = false;
            $scope.studentIDreadOnly=false;
	    $scope.classReadonly = true;
	    $scope.feeTypereadOnly = false;
	    $scope.dueDateReadonly = true;
	    $scope.paidDateReadonly = true;
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
	    $scope.statusReadonly = false;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
}
// Cohesive Create Framework Ends
function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#dueDate").datepicker("getDate"));
	$scope.$apply();
}

function fnpaidDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paidDate = $.datepicker.formatDate('dd-mm-yy', $("#dueDate").datepicker("getDate"));
	$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.paidDate = $.datepicker.formatDate('dd-mm-yy', $("#paidDate").datepicker("getDate"));
	//$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#dueDate").datepicker("getDate"));
	
  $( "#paidDate" ).datepicker('setDate','');
	  $scope.paidDate = null;
	   $( "#dueDate" ).datepicker('setDate','');
	  $scope.dueDate = null;
		$scope.$apply();
}	
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'StudentFeeManagementSummaryTable':
		   
			var StudentFeeManagementSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentFeeManagementSummaryTable[index] = new Object();
					 StudentFeeManagementSummaryTable[index].idx=index;
					 StudentFeeManagementSummaryTable[index].checkBox=false;
//					 StudentFeeManagementSummaryTable[index].studentID=value.studentID;
//					 StudentFeeManagementSummaryTable[index].studentName=value.studentName;
//					 StudentFeeManagementSummaryTable[index].class=value.class;
//					 StudentFeeManagementSummaryTable[index].paidDate=value.paidDate;
//					 StudentFeeManagementSummaryTable[index].status=value.status;
                                         StudentFeeManagementSummaryTable[index].feeID=value.feeID;
					 StudentFeeManagementSummaryTable[index].dueDate=value.dueDate;
					 StudentFeeManagementSummaryTable[index].feeAmount=value.feeAmount;
					 StudentFeeManagementSummaryTable[index].paidAmount=value.paidAmount;
                                         StudentFeeManagementSummaryTable[index].studentID=value.studentID;
//					 StudentFeeManagementSummaryTable[index].status=value.status;
					}
			return StudentFeeManagementSummaryTable;
			break ;
		}
	}
	
function fnStudentFeeManagementSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.studentNameReadOnly = true;
                 $scope.studentIDreadOnly=true;
	         $scope.feeTypereadOnly = true;
	         $scope.classReadonly = true;
	         $scope.dueDateReadonly = true;
	         $scope.paidDateReadonly = true;
	         $scope.authStatreadOnly = true;
	         $scope.recordStatreadOnly = true;
	         $scope.statusReadonly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts
                $scope.studentName = response.body.filter.studentName;
	        $scope.studentID =response.body.filter.studentID;
                $scope.feeType =response.body.filter.feeType;
//                $scope.feeID =response.body.filter.feeID;
//                $scope.class =response.body.filter.class;
//                $scope.paidDate = response.body.filter.paidDate;
                $scope.status = response.body.filter.status;
	         	$scope.authStat =response.body.filter.authStat;
                $scope.StudentFeeManagementSummaryTable=fnConvertmvw('StudentFeeManagementSummaryTable',response.body.SummaryResult);
		        $scope.StudentFeeManagementSummaryCurPage = 1;
		        $scope.StudentFeeManagementSummaryShowObject=$scope.fnMvwGetCurPageTable('SFMSummary');
		//Multiple View Response Ends 
        }
		return true;
}