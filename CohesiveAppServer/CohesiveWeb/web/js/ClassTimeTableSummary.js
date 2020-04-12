/* 
    Author     :IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	//Screen Specific Scope starts
        $scope.class = 'Select option';
	$scope.authStat = '';
	
	$scope.AuthType = Institute.AuthStatusMaster;
	$scope.classReadonly = false;
	$scope.authStatreadOnly = false;
	//Screen Specific Scope Ends
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
	// multiple View Starts
	$scope.ClassTimeTableSummaryCurPage = 0;
	$scope.ClassTimeTableSummaryTable = null;
	$scope.ClassTimeTableSummaryShowObject = null;
	// multiple View Ends
        //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'CTBSummary') {
			if ($scope.ClassTimeTableSummaryTable != null && $scope.ClassTimeTableSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.ClassTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassTimeTableSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.ClassTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.ClassTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.ClassTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'CTBSummary') {
			if ($scope.ClassTimeTableSummaryTable != null && $scope.ClassTimeTableSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.ClassTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassTimeTableSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.ClassTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.ClassTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.ClassTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'CTBSummary') {
				emptyTableRec = {
					idx: 0,
					 checkBox: false,
					 class: ""
				};
				if ($scope.ClassTimeTableSummaryTable == null)
					$scope.ClassTimeTableSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassTimeTableSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.ClassTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.ClassTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.ClassTimeTableSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'CTBSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassTimeTableSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.ClassTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.ClassTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.ClassTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'CTBSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassTimeTableSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassTimeTableSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassTimeTableSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'CTBSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassTimeTableSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassTimeTableSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassTimeTableSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'CTBSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.ClassTimeTableSummaryTable);
			
		}
	};

	//Multiple View Scope Function ends 	
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
    MenuName = "ClassTimeTableSummary";
    window.parent.nokotser = $("#nokotser").val();
    window.parent.Entity = "ClassSummaryEnity";
    selectBoxes= ['class','authStatus'];
    fnGetSelectBoxdata(selectBoxes);
});
//Default Load Record Ends
function fnClassTimeTableSummarypostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
         if(Institute.ClassMaster.length>0)
      {   
          $scope.classes=Institute.ClassMaster;
	   window.parent.fn_hide_parentspinner();
            $scope.$apply();
}

}
// Cohesive Query Framework Starts
function fnClassTimeTableSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
        $scope.class = 'Select option';
	$scope.authStat = 'Select option';
	$scope.classReadonly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.ClassSection = null; // Std/sec
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
	//$scope.selectedIndex =0;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.class=$scope.ClassTimeTableSummaryTable[$scope.selectedIndex].class;
	
	fninvokeDetailScreen('ClassTimeTable',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnClassTimeTableSummaryView() {
	var emptyClassTimeTableSummary = {
		filter: {
			class: 'Select option',
			authStat: 'Select option'
		},
		SummaryResult: [{
				class: ""
			}]
	};
	// Screen Specific DataModel Starts	
        
	var dataModel = emptyClassTimeTableSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     $scope.operation= "View";    
    if( $scope.class!=null)
	dataModel.filter.class = $scope.class;
        if( $scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ClassTimeTableSummary', 'View', dataModel, [{entityName:"class",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnClassTimeTableSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
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

function fnClassTimeTableSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 if(!fngetSelectedIndex($scope.ClassTimeTableSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation Ends
//Cohesive back Framework Starts
function fnClassTimeTableSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		 // Screen Specific Scope Starts
	     $scope.class = 'Select option';
	     $scope.authStat = '';
	     $scope.mvwAddDeteleDisable = true; //Multiple View
	     $scope.ClassTimeTableSummaryTable = null;
	     $scope.ClassTimeTableSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.classReadonly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
		}
	    else {
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
// Cohesive Back Framework Ends
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'ClassTimeTableSummaryTable':
		   
			var ClassTimeTableSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     ClassTimeTableSummaryTable[index] = new Object();
					 ClassTimeTableSummaryTable[index].idx=index;
					 ClassTimeTableSummaryTable[index].checkBox=false;
					 ClassTimeTableSummaryTable[index].class=value.class;
					}
			return ClassTimeTableSummaryTable;
			break ;
		}
	}
	
function fnClassTimeTableSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		 // Specific Screen Scope Starts
	        $scope.classReadonly = true;
	        $scope.authStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
        $scope.class = response.body.filter.class;
		$scope.authStat = response.body.filter.authStat;
        //Multiple View Response Starts 
		$scope.ClassTimeTableSummaryTable=fnConvertmvw('ClassTimeTableSummaryTable',response.body.SummaryResult);
		$scope.ClassTimeTableSummaryCurPage = 1;
		$scope.ClassTimeTableSummaryShowObject=$scope.fnMvwGetCurPageTable('CTBSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
            }
		return true;

}