/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
	$scope.authStat = "";
	//
	$scope.ExamMaster = Institute.ExamMaster;
        $scope.classes=Institute.ClassMaster;
	$scope.AuthType = Institute.AuthStatusMaster;
	//
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
	$scope.examtypereadOnly = false;
	$scope.classReadonly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.ClassExamScheduleSummaryCurPage = 0;
	$scope.ClassExamScheduleSummaryTable = null;
	$scope.ClassExamScheduleSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'ClassExamScheduleSummary') {
			if ($scope.ClassExamScheduleSummaryTable != null && $scope.ClassExamScheduleSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.ClassExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassExamScheduleSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.ClassExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.ClassExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.ClassExamScheduleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'ClassExamScheduleSummary') {
			if ($scope.ClassExamScheduleSummaryTable != null && $scope.ClassExamScheduleSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.ClassExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassExamScheduleSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.ClassExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.ClassExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.ClassExamScheduleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassExamScheduleSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					class: "",
				        exam: "",
				        authStat: ""
				};
				if ($scope.ClassExamScheduleSummaryTable == null)
					$scope.ClassExamScheduleSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassExamScheduleSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.ClassExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.ClassExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.ClassExamScheduleSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassExamScheduleSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassExamScheduleSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassExamScheduleSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassExamScheduleSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.ClassExamScheduleSummaryCurPage = lsvwObject.curPage;
				$scope.ClassExamScheduleSummaryTable = lsvwObject.tableObject;
				$scope.ClassExamScheduleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'ClassExamScheduleSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassExamScheduleSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassExamScheduleSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassExamScheduleSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'ClassExamScheduleSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassExamScheduleSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassExamScheduleSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassExamScheduleSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'ClassExamScheduleSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.ClassExamScheduleSummaryTable);
			
		}
	};


	//Multiple View Scope Function ends 
	
	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "ClassExamScheduleSummary";
	
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="ClassSummaryEnity";
        selectBoxes= ['class','examType','authStatus'];
        fnGetSelectBoxdata(selectBoxes);//Integration changes
	
	
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts



function fnClassExamScheduleSummarypostSelectBoxMaster(){
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0)
      {   
      $scope.classes=Institute.ClassMaster;
      $scope.ExamMaster = Institute.ExamMaster;
      window.parent.fn_hide_parentspinner();
      $scope.$apply();
}
}
function fnClassExamScheduleSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
	$scope.authStat = "";
	//
	$scope.examtypereadOnly = true;
	$scope.classReadonly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
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
	lscreenKeyObject.class=$scope.ClassExamScheduleSummaryTable[$scope.selectedIndex].class;
	lscreenKeyObject.exam=$scope.ClassExamScheduleSummaryTable[$scope.selectedIndex].exam;
	
	fninvokeDetailScreen('ClassExamSchedule',lscreenKeyObject,$scope);
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnClassExamScheduleSummaryView() {
	var emptyClassExamScheduleSummary = {
		filter: {
			class: 'Select option',
			exam: 'Select option',
			authStat: 'Select option',
			recordStat: 'Select option'
		},
		SummaryResult: [{
				class: "",
				exam: "",
				authStat: "",
				recordStat: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyClassExamScheduleSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        if($scope.class!=null)
	dataModel.filter.class = $scope.class;
        if($scope.exam!=null)
	dataModel.filter.exam = $scope.exam;
     if($scope.class!=null)
	dataModel.filter.recordStat = $scope.class;
    if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
        
	// Screen Specific DataModel Ends
	var response = fncallBackend('ClassExamScheduleSummary', 'View', dataModel, [{entityName:"class",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnClassExamScheduleSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &                 
						($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') &
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

function fnClassExamScheduleSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.ClassExamScheduleSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnClassExamScheduleSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.exam = "Select option";
	    $scope.class = "Select option";
	    $scope.authStat = "";
	    //
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.ClassExamScheduleSummaryTable = null;
		$scope.ClassExamScheduleSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	   $scope.examtypereadOnly = false;
	 $scope.classReadonly = false;
          $scope.authStatreadOnly = false;
	   $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.examtypereadOnly = true;
	   $scope.classReadonly = true;
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
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'ClassExamScheduleSummaryTable':
		   
			var ClassExamScheduleSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     ClassExamScheduleSummaryTable[index] = new Object();
					 ClassExamScheduleSummaryTable[index].idx=index;
					 ClassExamScheduleSummaryTable[index].checkBox=false;
					 ClassExamScheduleSummaryTable[index].class=value.class;
					 ClassExamScheduleSummaryTable[index].exam=value.exam;
					 ClassExamScheduleSummaryTable[index].authStat=value.authStat;
					 ClassExamScheduleSummaryTable[index].recordStat=value.recordStat;
					}
			return ClassExamScheduleSummaryTable;
			break ;
		}
	}
              
function fnClassExamScheduleSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
	        $scope.classReadonly = true;
	        $scope.authStatreadOnly = true;
	        $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
        $scope.class = response.body.filter.class;
        $scope.exam = response.body.filter.exam;
        $scope.authStat = response.body.filter.authStat;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                //Multiple View Response Starts 
		$scope.ClassExamScheduleSummaryTable=fnConvertmvw('ClassExamScheduleSummaryTable',response.body.SummaryResult);
		$scope.ClassExamScheduleSummaryCurPage = 1;
		$scope.ClassExamScheduleSummaryShowObject=$scope.fnMvwGetCurPageTable('ClassExamScheduleSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}
	
