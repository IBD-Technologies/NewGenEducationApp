/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.subjectName = "Select option";
	$scope.subjectID = "";
	$scope.assignmentID = "";
	$scope.assignmentType = "";
	$scope.AssignmentMaster = [{
		AssignmentId: "",
		AssignmentDescription: ""
	}];
	$scope.dueDate = "";
	$scope.authStat = "";
	$scope.AuthType = Institute.AuthStatusMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.AssignmentType = Institute.AssignmentTypeMaster;
	//$scope.Fee = Institute.FeeStatus;
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
	$scope.subjectNamereadOnly = false;
	$scope.assignmentIDreadOnly = false;
	$scope.assignmentTypereadOnly = false;
	$scope.dueDatereadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends	
	// multiple View Starts
	$scope.instituteAssignmentSummaryCurPage = 0;
	$scope.instituteAssignmentSummaryTable = null;
	$scope.instituteAssignmentSummaryShowObject = null;
	// multiple View Ends
    //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'instituteAssignmentSummary') {
			if ($scope.instituteAssignmentSummaryTable != null && $scope.instituteAssignmentSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.instituteAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteAssignmentSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.instituteAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.instituteAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.instituteAssignmentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'instituteAssignmentSummary') {
			if ($scope.instituteAssignmentSummaryTable != null && $scope.instituteAssignmentSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.instituteAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteAssignmentSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.instituteAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.instituteAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.instituteAssignmentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'instituteAssignmentSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					assignmentID:"",
					assginmentType:"",
				    class:"",
				    subjectID:"",
				    subjectName:"",
				    dueDate: ""
				};
				if ($scope.instituteAssignmentSummaryTable == null)
					$scope.instituteAssignmentSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.instituteAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteAssignmentSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.instituteAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.instituteAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.instituteAssignmentSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'instituteAssignmentSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.instituteAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteAssignmentSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.instituteAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.instituteAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.instituteAssignmentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'instituteAssignmentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.instituteAssignmentSummaryCurPage;
			lsvwObject.tableObject = $scope.instituteAssignmentSummaryTable;
			lsvwObject.screenShowObject = $scope.instituteAssignmentSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'instituteAssignmentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.instituteAssignmentSummaryCurPage;
			lsvwObject.tableObject = $scope.instituteAssignmentSummaryTable;
			lsvwObject.screenShowObject = $scope.instituteAssignmentSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'instituteAssignmentSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.instituteAssignmentSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
	MenuName = "InstituteAssignmentSummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="InstituteSummaryEntity";
        
	fnDatePickersetDefault('assignmentDueDate',fndueDateEventHandler);
	fnsetDateScope();
	selectBoxes= ['assignmentType','subject','authStatus'];
        fnGetSelectBoxdata(selectBoxes);
	
});
//Default Load Record End
// Cohesive Query Framework Starts

function fnInstituteAssignmentSummarypostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   if(Institute.SubjectMaster.length>0)
      {   
     $scope.subjects = Institute.SubjectMaster;
	 window.parent.fn_hide_parentspinner();
          $scope.$apply();
}
}
function fnInstituteAssignmentSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.subjectName = 'Select option';
	$scope.subjectID = "";
	$scope.assignmentID = "";
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.authStat = "";
	
	$scope.subjectNamereadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.dueDatereadOnly = true;
	$scope.authStatreadOnly = true;
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
	lscreenKeyObject.assignmentID=$scope.instituteAssignmentSummaryTable[$scope.selectedIndex].assignmentID;
	
	fninvokeDetailScreen('InstituteAssignment',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnInstituteAssignmentSummaryView() {
	var emptyInstituteAssignmentSummary = {
		filter:{
		    assignmentID: "",
			subjectID: "",
			subjectName: "Select option",
			dueDate: "",
			assignmentType: "",
			authStat: ""},
		SummaryResult: [{
				assignmentID: "",
				assginmentType:"",
				class:"",
				subjectID: "",
				subjectName: "",
				dueDate: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstituteAssignmentSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        
        
       if($scope.subjectName!=null)
	dataModel.filter.subjectName = $scope.subjectName;
      if($scope.dueDate!=null)
	dataModel.filter.dueDate = $scope.dueDate;
         if($scope.subjectID!=null)
	dataModel.filter.subjectID = $scope.subjectName;
        if($scope.assignmentType!=null)
	dataModel.filter.assignmentType = $scope.assignmentType;
       if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('InstituteAssignmentSummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnInstituteAssignmentSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((
						($scope.subjectName == '' || $scope.subjectName == null || $scope.subjectName == 'Select option') &
						($scope.dueDate == '' || $scope.dueDate == null) &
						($scope.assignmentType == '' || $scope.assignmentType == null || $scope.assignmentType == 'Select option') &
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

function fnInstituteAssignmentSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.InstituteAssignmentSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;        
	}
	return true;
}

// Screen Specific Mandatory Validation Ends
function fnInstituteAssignmentSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.subjectName = "Select option";
	    $scope.subjectID = "";
	    $scope.assignmentID = "";
	    $scope.assignmentType = "";
	    $scope.dueDate = "";
	    $scope.authStat = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.instituteAssignmentSummaryTable = null;
		$scope.instituteAssignmentSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.subjectNamereadOnly = false;
	    $scope.assignmentIDreadOnly = false;
	    $scope.assignmentTypereadOnly = false;
	    $scope.dueDatereadOnly = false;
	    $scope.authStatreadOnly = false;
	     $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.subjectNamereadOnly = true;
	    $scope.assignmentIDreadOnly = true;
	    $scope.assignmentTypereadOnly = true;
	    $scope.dueDatereadOnly = true;
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
function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentDueDate").datepicker("getDate"));
		$scope.$apply();
}

function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      $( "#assignmentDueDate" ).datepicker('setDate','');
	  $scope.dueDate = null;
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'instituteAssignmentSummaryTable':
		   
			var instituteAssignmentSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     instituteAssignmentSummaryTable[index] = new Object();
					 instituteAssignmentSummaryTable[index].idx=index;
					 instituteAssignmentSummaryTable[index].checkBox=false;
					// instituteAssignmentSummaryTable[index].assignmentID=value.assignmentID;
					 instituteAssignmentSummaryTable[index].class=value.class;
					// instituteAssignmentSummaryTable[index].section=value.section;
                                        instituteAssignmentSummaryTable[index].assignmentID=value.assignmentID;
					 instituteAssignmentSummaryTable[index].assignee=value.assignee;
					 instituteAssignmentSummaryTable[index].subjectName=value.subjectName;
					 instituteAssignmentSummaryTable[index].assignmentType=value.assignmentType;
					 instituteAssignmentSummaryTable[index].dueDate=value.dueDate;
                                         
					}
			return instituteAssignmentSummaryTable;
			break ;
		}
	}
	
function fnInstituteAssignmentSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.subjectNamereadOnly = true;
	        $scope.assignmentIDreadOnly = true;
	        $scope.assignmentTypereadOnly = true;
	         $scope.dueDatereadOnly = true;
	      $scope.authStatreadOnly = true;
	      $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.subjectName = response.body.filter.subjectName;
	        $scope.subjectID =response.body.filter.subjectID;
              //  $scope.assignmentID =response.body.filter.assignmentID;
                $scope.dueDate =response.body.filter.dueDate;
                $scope.assignmentType =response.body.filter.assignmentType;
                //$scope.dueDate =response.body.filter.dueDate;
		        $scope.authStat =response.body.filter.authStat;
                $scope.instituteAssignmentSummaryTable=fnConvertmvw('instituteAssignmentSummaryTable',response.body.SummaryResult);
		        $scope.instituteAssignmentSummaryCurPage = 1
		        $scope.instituteAssignmentSummaryShowObject=$scope.fnMvwGetCurPageTable('instituteAssignmentSummary');
		//Multiple View Response Ends 
//                $scope.selectedIndex =0;
        }
		return true;

}