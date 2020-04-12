/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
    Author     : Munish Kumar B
	IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.class = "Select option";
	$scope.exam = "Select option";
	$scope.subjectName = "Select option";
	$scope.subjectID = "Select option";
	$scope.authStat = "";
	$scope.classes=Institute.ClassMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.SubjectMaster = Institute.SubjectMaster;
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
	// Screen Specific Scope Start
	$scope.classReadonly = false;
	$scope.sectionreadOnly = false;
	$scope.examtypereadOnly = false;
	$scope.subjectIDReadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	
	// multiple View Starts
	$scope.ClassMarkEntrySummaryCurPage = 0;
	$scope.ClassMarkEntrySummaryTable = null;
	$scope.ClassMarkEntrySummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'ClassMarkEntrySummary') {
			if ($scope.ClassMarkEntrySummaryTable != null && $scope.ClassMarkEntrySummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.ClassMarkEntrySummaryCurPage;
				lsvwObject.tableObject = $scope.ClassMarkEntrySummaryTable;
				lsvwObject.screenShowObject = $scope.ClassMarkEntrySummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.ClassMarkEntrySummaryCurPage = lsvwObject.curPage;
				$scope.ClassMarkEntrySummaryTable = lsvwObject.tableObject;
				$scope.ClassMarkEntrySummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'ClassMarkEntrySummary') {
			if ($scope.ClassMarkEntrySummaryTable != null && $scope.ClassMarkEntrySummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.ClassMarkEntrySummaryCurPage;
				lsvwObject.tableObject = $scope.ClassMarkEntrySummaryTable;
				lsvwObject.screenShowObject = $scope.ClassMarkEntrySummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.ClassMarkEntrySummaryCurPage = lsvwObject.curPage;
				$scope.ClassMarkEntrySummaryTable = lsvwObject.tableObject;
				$scope.ClassMarkEntrySummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassMarkEntrySummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					class: "",
				    exam:"",
				    subjectName:"",
				    subjectID: ""
				};
				if ($scope.ClassMarkEntrySummaryTable == null)
					$scope.ClassMarkEntrySummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassMarkEntrySummaryCurPage;
				lsvwObject.tableObject = $scope.ClassMarkEntrySummaryTable;
				lsvwObject.screenShowObject = $scope.ClassMarkEntrySummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.ClassMarkEntrySummaryCurPage = lsvwObject.curPage;
				$scope.ClassMarkEntrySummaryTable = lsvwObject.tableObject;
				$scope.ClassMarkEntrySummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassMarkEntrySummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassMarkEntrySummaryCurPage;
				lsvwObject.tableObject = $scope.ClassMarkEntrySummaryTable;
				lsvwObject.screenShowObject = $scope.ClassMarkEntrySummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.ClassMarkEntrySummaryCurPage = lsvwObject.curPage;
				$scope.ClassMarkEntrySummaryTable = lsvwObject.tableObject;
				$scope.ClassMarkEntrySummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'ClassMarkEntrySummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassMarkEntrySummaryCurPage;
			lsvwObject.tableObject = $scope.ClassMarkEntrySummaryTable;
			lsvwObject.screenShowObject = $scope.ClassMarkEntrySummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'ClassMarkEntrySummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassMarkEntrySummaryCurPage;
			lsvwObject.tableObject = $scope.ClassMarkEntrySummaryTable;
			lsvwObject.screenShowObject = $scope.ClassMarkEntrySummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'ClassMarkEntrySummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.ClassMarkEntrySummaryTable);
			
		}
	};


	//Multiple View Scope Function ends 
	
	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "ClassMarkSummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="ClassSummaryEnity";
	selectBoxes= ['examType','SubjectName','class','authStatus'];
         fnGetSelectBoxdata(selectBoxes);
	
     
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});

function fnClassMarkSummarypostSelectBoxMaster(){
    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
         if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0 && Institute.SubjectMaster.length>0)
      {   
      $scope.classes=Institute.ClassMaster;
      $scope.ExamMaster = Institute.ExamMaster;
      $scope.subjects = Institute.SubjectMaster;
	window.parent.fn_hide_parentspinner();     
        $scope.$apply();
}
}
// Cohesive Query Framework Starts
function fnClassMarkSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.class = "Select option";
	$scope.exam = "Select option";
	$scope.subjectName = "Select option";
	$scope.subjectID = "";
	$scope.authStat = "";
    $scope.classReadonly = true;
	$scope.examtypereadOnly = true;
	$scope.subjectIDReadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.authStatreadOnly = true;
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
	lscreenKeyObject.class=$scope.ClassMarkEntrySummaryTable[$scope.selectedIndex].class;
	lscreenKeyObject.exam=$scope.ClassMarkEntrySummaryTable[$scope.selectedIndex].exam;
        lscreenKeyObject.subjectID=$scope.ClassMarkEntrySummaryTable[$scope.selectedIndex].subjectID;
	
	fninvokeDetailScreen('ClassMark',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnClassMarkSummaryView() {
	var emptyClassMarkEntry = {
	filter: {
			class: 'Select option',
			exam: 'Select option',
			subjectName: 'Select option',
			subjectID: "",
			authStat: ''
		},
		SummaryResult: [{
				class: "",
				exam:"",
				subjectName:"",
				subjectID: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyClassMarkEntry;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
	if($scope.class!=null)
	dataModel.filter.class = $scope.class;
     if($scope.exam!=null)
	dataModel.filter.exam = $scope.exam;
    if($scope.subjectName!=null)
	dataModel.filter.subjectName = $scope.subjectName;
    if($scope.subjectID!=null)
	dataModel.filter.subjectID = $scope.subjectID;
    if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ClassMarkSummary', 'View', dataModel, [{entityName:"class",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnClassMarkSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &             
						($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') &
						($scope.subjectID == '' || $scope.subjectID == null || $scope.subjectID == 'Select option') &
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

function fnClassMarkSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.ClassMarkEntrySummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnClassMarkSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.class = "Select option"
	    $scope.exam = "Select option";
    	$scope.subjectName = "Select option";
	    $scope.subjectID = "";
	    $scope.authStat = "";
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.PaymentSummaryTable = null;
		$scope.paymentSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	      $scope.classReadonly = false;
          $scope.examtypereadOnly = false;
	      $scope.subjectIDReadOnly = false;
	      $scope.subjectNamereadOnly = false;
	      $scope.authStatreadOnly = false;
	      $scope.recordStatreadOnly = false;
		  $scope.mastershow=true;
		  $scope.detailshow=false;
		}
	    else {
	   $scope.classReadonly = false;
       $scope.examtypereadOnly = false;
	   $scope.subjectIDReadOnly = false;
	   $scope.subjectNamereadOnly = false;
	   $scope.authStatreadOnly = false;
	   $scope.recordStatreadOnly = false;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
		
}
// Cohesive Save Framework Ends
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'ClassMarkEntrySummaryTable':
		   
			var ClassMarkEntrySummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     ClassMarkEntrySummaryTable[index] = new Object();
					 ClassMarkEntrySummaryTable[index].idx=index;
					 ClassMarkEntrySummaryTable[index].checkBox=false;
					 ClassMarkEntrySummaryTable[index].class=value.class;
					 ClassMarkEntrySummaryTable[index].exam=value.exam;
                                         ClassMarkEntrySummaryTable[index].subjectID=value.subjectID;
					 ClassMarkEntrySummaryTable[index].subjectName=value.subjectName;
					}
			return ClassMarkEntrySummaryTable;
			break ;
		}
	}
	
	function fnClassMarkSummarypostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
	   $scope.classReadonly = true;
       $scope.examtypereadOnly = true;
	   $scope.subjectIDReadOnly = true;
	   $scope.subjectNamereadOnly = true;
	   $scope.authStatreadOnly = true;
	   $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.class = response.body.filter.class;
		$scope.exam = response.body.filter.exam;
		$scope.subjectName = response.body.filter.subjectName;
		$scope.authStat = response.body.filter.authStat;
		$scope.SummaryResult = {};
		//Multiple View Response Starts 
			$scope.ClassMarkEntrySummaryTable=fnConvertmvw('ClassMarkEntrySummaryTable',response.body.SummaryResult);
		$scope.ClassMarkEntrySummaryCurPage = 1
		$scope.ClassMarkEntrySummaryShowObject=$scope.fnMvwGetCurPageTable('ClassMarkEntrySummary');
		//Multiple View Response Ends 
		$scope.selectedIndex = 0; // Summary Field
	}
	return true;

}

	
