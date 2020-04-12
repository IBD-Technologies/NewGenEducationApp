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
	$scope.skillName = "Select option";
	$scope.skillID = "";
	$scope.authStat = "";
	$scope.classes=Institute.ClassMaster;
	$scope.skills = Institute.SkillMaster;
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.SkillMaster = Institute.SkillMaster;
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
	$scope.skillIDReadOnly = false;
	$scope.skillNamereadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	
	// multiple View Starts
	$scope.ClassSoftSkillSummaryCurPage = 0;
	$scope.ClassSoftSkillSummaryTable = null;
	$scope.ClassSoftSkillSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'ClassSoftSkillSummary') {
			if ($scope.ClassSoftSkillSummaryTable != null && $scope.ClassSoftSkillSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.ClassSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSoftSkillSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.ClassSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.ClassSoftSkillSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'ClassSoftSkillSummary') {
			if ($scope.ClassSoftSkillSummaryTable != null && $scope.ClassSoftSkillSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.ClassSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSoftSkillSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.ClassSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.ClassSoftSkillSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassSoftSkillSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					class: "",
				    exam:"",
				    skillName:"",
				    skillID: ""
				};
				if ($scope.ClassSoftSkillSummaryTable == null)
					$scope.ClassSoftSkillSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSoftSkillSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.ClassSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.ClassSoftSkillSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassSoftSkillSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSoftSkillSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.ClassSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.ClassSoftSkillSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'ClassSoftSkillSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassSoftSkillSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassSoftSkillSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassSoftSkillSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'ClassSoftSkillSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassSoftSkillSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassSoftSkillSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassSoftSkillSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'ClassSoftSkillSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.ClassSoftSkillSummaryTable);
			
		}
	};


	//Multiple View Scope Function ends 
	
	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "ClassSoftSkillSummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="ClassSummaryEnity";
	selectBoxes= ['examType','SkillName','class','authStatus'];
         fnGetSelectBoxdata(selectBoxes);
	
     
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});

function fnClassSoftSkillSummarypostSelectBoxMaster(){
    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
         if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0 && Institute.SkillMaster.length>0)
      {   
      $scope.classes=Institute.ClassMaster;
      $scope.ExamMaster = Institute.ExamMaster;
      $scope.skills = Institute.SkillMaster;
	window.parent.fn_hide_parentspinner();     
        $scope.$apply();
}
}
// Cohesive Query Framework Starts
function fnClassSoftSkillSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.class = "Select option";
	$scope.exam = "Select option";
	$scope.skillName = "Select option";
	$scope.skillID = "";
	$scope.authStat = "";
    $scope.classReadonly = true;
	$scope.examtypereadOnly = true;
	$scope.skillIDReadOnly = true;
	$scope.skillNamereadOnly = true;
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
	lscreenKeyObject.class=$scope.ClassSoftSkillSummaryTable[$scope.selectedIndex].class;
	lscreenKeyObject.exam=$scope.ClassSoftSkillSummaryTable[$scope.selectedIndex].exam;
        lscreenKeyObject.skillID=$scope.ClassSoftSkillSummaryTable[$scope.selectedIndex].skillID;
	
	fninvokeDetailScreen('ClassSoftSkill',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnClassSoftSkillSummaryView() {
	var emptyClassSoftSkill = {
	filter: {
			class: 'Select option',
			exam: 'Select option',
			skillName: 'Select option',
			skillID: "",
			authStat: ''
		},
		SummaryResult: [{
				class: "",
				exam:"",
				skillName:"",
				skillID: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyClassSoftSkill;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
	if($scope.class!=null)
	dataModel.filter.class = $scope.class;
     if($scope.exam!=null)
	dataModel.filter.exam = $scope.exam;
    if($scope.skillName!=null)
	dataModel.filter.skillName = $scope.skillName;
    if($scope.skillID!=null)
	dataModel.filter.skillID = $scope.skillID;
    if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ClassSoftSkillSummary', 'View', dataModel, [{entityName:"class",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnClassSoftSkillSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &             
						($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') &
						($scope.skillID == '' || $scope.skillID == null || $scope.skillID == 'Select option') &
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

function fnClassSoftSkillSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.ClassSoftSkillSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnClassSoftSkillSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.class = "Select option"
	    $scope.exam = "Select option";
    	$scope.skillName = "Select option";
	    $scope.skillID = "";
	    $scope.authStat = "";
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.PaymentSummaryTable = null;
		$scope.paymentSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	      $scope.classReadonly = false;
          $scope.examtypereadOnly = false;
	      $scope.skillIDReadOnly = false;
	      $scope.skillNamereadOnly = false;
	      $scope.authStatreadOnly = false;
	      $scope.recordStatreadOnly = false;
		  $scope.mastershow=true;
		  $scope.detailshow=false;
		}
	    else {
	   $scope.classReadonly = false;
       $scope.examtypereadOnly = false;
	   $scope.skillIDReadOnly = false;
	   $scope.skillNamereadOnly = false;
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
		case 'ClassSoftSkillSummaryTable':
		   
			var ClassSoftSkillSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     ClassSoftSkillSummaryTable[index] = new Object();
					 ClassSoftSkillSummaryTable[index].idx=index;
					 ClassSoftSkillSummaryTable[index].checkBox=false;
					 ClassSoftSkillSummaryTable[index].class=value.class;
					 ClassSoftSkillSummaryTable[index].exam=value.exam;
                                         ClassSoftSkillSummaryTable[index].skillID=value.skillID;
					 ClassSoftSkillSummaryTable[index].skillName=value.skillName;
					}
			return ClassSoftSkillSummaryTable;
			break ;
		}
	}
	
	function fnClassSoftSkillSummarypostBackendCall(response) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (response.header.status == 'success') {
		// Specific Screen Scope Starts
	   $scope.classReadonly = true;
       $scope.examtypereadOnly = true;
	   $scope.skillIDReadOnly = true;
	   $scope.skillNamereadOnly = true;
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
		$scope.skillName = response.body.filter.skillName;
		$scope.authStat = response.body.filter.authStat;
		$scope.SummaryResult = {};
		//Multiple View Response Starts 
			$scope.ClassSoftSkillSummaryTable=fnConvertmvw('ClassSoftSkillSummaryTable',response.body.SummaryResult);
		$scope.ClassSoftSkillSummaryCurPage = 1
		$scope.ClassSoftSkillSummaryShowObject=$scope.fnMvwGetCurPageTable('ClassSoftSkillSummary');
		//Multiple View Response Ends 
		$scope.selectedIndex = 0; // Summary Field
	}
	return true;

}

	
