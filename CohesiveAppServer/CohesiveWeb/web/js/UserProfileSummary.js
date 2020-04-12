/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.userName = "";
	$scope.userID = "";
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
        $scope.OperationScopes = OperationScopes;
	$scope.selectedIndex =0;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.userNamereadOnly = false;
	$scope.userIDreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends

	// multiple View Starts
	$scope.UserProfileSummaryCurPage = 0;
	$scope.USerProfileSummaryTable = null;
	$scope.UserProfileSummaryShowObject = null;
	// multiple View Ends
	//Multiple View Scope Function Starts 
	
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'userProfileSummary') {
			if ($scope.USerProfileSummaryTable != null && $scope.USerProfileSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.UserProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.USerProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.UserProfileSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.UserProfileSummaryCurPage = lsvwObject.curPage;
				$scope.USerProfileSummaryTable = lsvwObject.tableObject;
				$scope.UserProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'userProfileSummary') {
			if ($scope.USerProfileSummaryTable != null && $scope.USerProfileSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.UserProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.USerProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.UserProfileSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.UserProfileSummaryCurPage = lsvwObject.curPage;
				$scope.USerProfileSummaryTable = lsvwObject.tableObject;
				$scope.UserProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'userProfileSummary') {
				emptyTableRec = {
					idx: 0,
				    userName: "",
				    userID: ""
				};
				if ($scope.USerProfileSummaryTable == null)
					$scope.USerProfileSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.UserProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.USerProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.UserProfileSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.UserProfileSummaryCurPage = lsvwObject.curPage;
				$scope.USerProfileSummaryTable = lsvwObject.tableObject;
				$scope.UserProfileSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'userProfileSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.UserProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.USerProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.UserProfileSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.UserProfileSummaryCurPage = lsvwObject.curPage;
				$scope.USerProfileSummaryTable = lsvwObject.tableObject;
				$scope.UserProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'userProfileSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.UserProfileSummaryCurPage;
			lsvwObject.tableObject = $scope.USerProfileSummaryTable;
			lsvwObject.screenShowObject = $scope.UserProfileSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'userProfileSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.UserProfileSummaryCurPage;
			lsvwObject.tableObject = $scope.USerProfileSummaryTable;
			lsvwObject.screenShowObject = $scope.UserProfileSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'userProfileSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.USerProfileSummaryTable);
			
		}
	};

//Multiple View Scope Function ends 	


$scope.fnUserSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'UserName';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
});
//--------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	MenuName = "UserProfileSummary";
        window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "UserSummaryEntity";
	window.parent.fn_hide_parentspinner();
	selectBoxes= ['authStatus','recordStatus'];
        fnGetSelectBoxdata(selectBoxes);//Integration changes
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts
function fnUserProfileSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.userName = "";
	$scope.userID = "";
	$scope.authStat = "";
	$scope.userNamereadOnly = true;
	$scope.userIDreadOnly = true;
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
	lscreenKeyObject.userID=$scope.USerProfileSummaryTable[$scope.selectedIndex].userID;
	
	fninvokeDetailScreen('UserProfile',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnUserProfileSummaryView() {
    
	var emptyUserProfileSummary = {
		filter: {
		        userName: "",
			userID: "",
			authStat: ""
		},
		SummaryResult: [{
				userName: "",
				userID: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyUserProfileSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        if($scope.userName!=null)
	dataModel.filter.userName = $scope.userName;
        if($scope.userID!=null)
	dataModel.filter.userID = $scope.userID;
         if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
        if($scope.recordStat!=null)
	dataModel.filter.recordStat = $scope.recordStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('UserProfileSummary', 'View', dataModel, [{entityName:"userID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnUserProfileSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.userName == '' || $scope.userName == null || $scope.userName == 'Select option') &                 
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

function fnUserProfileSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultUserId($scope))
				return false;

			break;

//		case 'Save':
//			if (!fnDefaultUserId($scope))
//				return false;
//
//			break;
                    case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

			if (!fngetSelectedIndex($scope.USerProfileSummaryTable, $scope)) //Generic For Summary
				return false;
			return true;
			break;

	}
	return true;
}

function fnDefaultUserId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnUserProfileSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.userName = "";
	    $scope.userID = "";
	    $scope.authStat = "";
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.USerProfileSummaryTable = null;
		$scope.UserProfileSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.userNamereadOnly = false;
		$scope.userIDreadOnly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.userNamereadOnly = true;
	    $scope.userIDreadOnly = true;
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
		case 'USerProfileSummaryTable':
		   
			var USerProfileSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     USerProfileSummaryTable[index] = new Object();
					 USerProfileSummaryTable[index].idx=index;
					 USerProfileSummaryTable[index].checkBox=false;
					 USerProfileSummaryTable[index].userName=value.userName;
					 USerProfileSummaryTable[index].userID=value.userID;
					}
			return USerProfileSummaryTable;
			break ;
		}
		
	}
	
function fnUserProfileSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		  $scope.userNamereadOnly = true;
	          $scope.userIDreadOnly = true;
	          $scope.authStatreadOnly = true;
	          $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.userName = response.body.filter.userName;
		$scope.userID =response.body.filter.userID;
		$scope.authStat =response.body.filter.authStat;
                $scope.USerProfileSummaryTable=fnConvertmvw('USerProfileSummaryTable',response.body.SummaryResult);
		$scope.UserProfileSummaryCurPage = 1;
		$scope.UserProfileSummaryShowObject=$scope.fnMvwGetCurPageTable('userProfileSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}