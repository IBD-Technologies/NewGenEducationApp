/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.authStat = "";
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
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.GroupingSummaryCurPage = 0;
	$scope.GroupingSummaryTable = null;
	$scope.GroupingSummaryShowObject = null;
	// multiple View Ends
	
	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'groupingSummary') {
			if ($scope.GroupingSummaryTable != null && $scope.GroupingSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.GroupingSummaryCurPage;
				lsvwObject.tableObject = $scope.GroupingSummaryTable;
				lsvwObject.screenShowObject = $scope.GroupingSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.GroupingSummaryCurPage = lsvwObject.curPage;
				$scope.GroupingSummaryTable = lsvwObject.tableObject;
				$scope.GroupingSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'groupingSummary') {
			if ($scope.GroupingSummaryTable != null && $scope.GroupingSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.GroupingSummaryCurPage;
				lsvwObject.tableObject = $scope.GroupingSummaryTable;
				lsvwObject.screenShowObject = $scope.GroupingSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.GroupingSummaryCurPage = lsvwObject.curPage;
				$scope.GroupingSummaryTable = lsvwObject.tableObject;
				$scope.GroupingSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'groupingSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					groupID: "",
					groupDescription:""
				};
				if ($scope.GroupingSummaryTable == null)
					$scope.GroupingSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.GroupingSummaryCurPage;
				lsvwObject.tableObject = $scope.GroupingSummaryTable;
				lsvwObject.screenShowObject = $scope.GroupingSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.GroupingSummaryCurPage = lsvwObject.curPage;
				$scope.GroupingSummaryTable = lsvwObject.tableObject;
				$scope.GroupingSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'groupingSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.GroupingSummaryCurPage;
				lsvwObject.tableObject = $scope.GroupingSummaryTable;
				lsvwObject.screenShowObject = $scope.GroupingSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.GroupingSummaryCurPage = lsvwObject.curPage;
				$scope.GroupingSummaryTable = lsvwObject.tableObject;
				$scope.GroupingSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'groupingSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.GroupingSummaryCurPage;
			lsvwObject.tableObject = $scope.GroupingSummaryTable;
			lsvwObject.screenShowObject = $scope.GroupingSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'groupingSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.GroupingSummaryCurPage;
			lsvwObject.tableObject = $scope.GroupingSummaryTable;
			lsvwObject.screenShowObject = $scope.GroupingSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'groupingSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.GroupingSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
});
//--------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	MenuName = "GroupMappingSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="InstituteSummaryEntity";
        window.parent.fn_hide_parentspinner(); 
        selectBoxes= ['authStatus'];
        fnGetSelectBoxdata(selectBoxes);//Integration changes
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts
function fnGroupMappingSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.groupID = "";
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
	lscreenKeyObject.groupID=$scope.GroupingSummaryTable[$scope.selectedIndex].groupID;
	//lscreenKeyObject.recordStat=$scope.GroupingSummaryTable[$scope.selectedIndex].recordStat;
	
	fninvokeDetailScreen('GroupMaping',lscreenKeyObject,$scope);
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnGroupMappingSummaryView() {
	var emptyGroupMappingSummary = {
		filter:{
		authStat: ""
		},
		SummaryResult:
		[{studentID:"",studentName:"",class:""}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyGroupMappingSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('GroupMappingSummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnGroupMappingSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
		
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

function fnGroupMappingSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.GroupingSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}

// Screen Specific Mandatory Validation Ends
function fnGroupMappingSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	        $scope.authStat = "";
		$scope.mvwAddDeteleDisable = true; //Multiple View
	        $scope.GroupingSummaryTable = null;
		$scope.GroupingSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	        $scope.authStatreadOnly = false;
	        $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
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
		case 'GroupingSummaryTable':
		   
			var GroupingSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     GroupingSummaryTable[index] = new Object();
					 GroupingSummaryTable[index].idx=index;
					 GroupingSummaryTable[index].checkBox=false;
					  GroupingSummaryTable[index].groupID=value.groupID;
//					 GroupingSummaryTable[index].studentID=value.studentID;
					 GroupingSummaryTable[index].groupDescription=value.groupDescription;
//					 GroupingSummaryTable[index].assignee=value.assignee;
//					 GroupingSummaryTable[index].class=value.class;
					}
			return GroupingSummaryTable;
			break ;
		}
	}
	
function fnGroupMappingSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
	         $scope.authStatreadOnly = true;
	        // $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.authStat =response.body.filter.authStat;
               //Multiple View Response Starts 
		$scope.GroupingSummaryTable=fnConvertmvw('GroupingSummaryTable',response.body.SummaryResult);
		$scope.GroupingSummaryCurPage = 1;
		$scope.GroupingSummaryShowObject=$scope.fnMvwGetCurPageTable('groupingSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
          }
		return true;
}