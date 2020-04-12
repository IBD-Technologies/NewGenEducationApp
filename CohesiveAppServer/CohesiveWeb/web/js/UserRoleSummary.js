/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.roleID = "";
	$scope.RoleMaster = [{
		RoleId: "",
		RoleDescription: ""
	}];
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
	// Screen Specific Scope Start
	$scope.roleIDreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.UserRoleSummaryCurPage = 0;
	$scope.UserRoleSummaryTable = null;
	$scope.UserRoleSummaryShowObject = null;
	// multiple View Ends
       //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'UserRoleSummary') {
			if ($scope.UserRoleSummaryTable != null && $scope.UserRoleSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.UserRoleSummaryCurPage;
				lsvwObject.tableObject = $scope.UserRoleSummaryTable;
				lsvwObject.screenShowObject = $scope.UserRoleSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.UserRoleSummaryCurPage = lsvwObject.curPage;
				$scope.UserRoleSummaryTable = lsvwObject.tableObject;
				$scope.UserRoleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'UserRoleSummary') {
			if ($scope.UserRoleSummaryTable != null && $scope.UserRoleSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.UserRoleSummaryCurPage;
				lsvwObject.tableObject = $scope.UserRoleSummaryTable;
				lsvwObject.screenShowObject = $scope.UserRoleSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.UserRoleSummaryCurPage = lsvwObject.curPage;
				$scope.UserRoleSummaryTable = lsvwObject.tableObject;
				$scope.UserRoleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'UserRoleSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					roleID:"",
				    functionID: ""
				};
				if ($scope.UserRoleSummaryTable == null)
					$scope.UserRoleSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.UserRoleSummaryCurPage;
				lsvwObject.tableObject = $scope.UserRoleSummaryTable;
				lsvwObject.screenShowObject = $scope.UserRoleSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.UserRoleSummaryCurPage = lsvwObject.curPage;
				$scope.UserRoleSummaryTable = lsvwObject.tableObject;
				$scope.UserRoleSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'UserRoleSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.UserRoleSummaryCurPage;
				lsvwObject.tableObject = $scope.UserRoleSummaryTable;
				lsvwObject.screenShowObject = $scope.UserRoleSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.UserRoleSummaryCurPage = lsvwObject.curPage;
				$scope.UserRoleSummaryTable = lsvwObject.tableObject;
				$scope.UserRoleSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'UserRoleSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.UserRoleSummaryCurPage;
			lsvwObject.tableObject = $scope.UserRoleSummaryTable;
			lsvwObject.screenShowObject = $scope.UserRoleSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'UserRoleSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.UserRoleSummaryCurPage;
			lsvwObject.tableObject = $scope.UserRoleSummaryTable;
			lsvwObject.screenShowObject = $scope.UserRoleSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'UserRoleSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.UserRoleSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
	$scope.fnUserRoleSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'UserRole';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "UserRoleSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="UserSummaryEntity";
        window.parent.fn_hide_parentspinner();
        fnGetSelectBoxdata(selectBoxes);
	selectBoxes= ['authStatus'];
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts
function fnUserRoleSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.roleID = "";
	$scope.authStat = "";
	
	$scope.roleIDreadOnly = true;
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
        
       
        
	lscreenKeyObject.roleID=$scope.UserRoleSummaryTable[$scope.selectedIndex].roleID;
	
	fninvokeDetailScreen('UserRole',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnUserRoleSummaryView() {
   
	var emptyUserRoleSummary = {
		filter: {
		        roleID: "",
			authStat: ""
		},
		SummaryResult: [{
				roleID:"",
				functionID: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyUserRoleSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
         $scope.operation='View';
        if($scope.roleID!=null)
	dataModel.filter.roleID = $scope.roleID;
       if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('UserRoleSummary', 'View', dataModel,  [{entityName:"roleID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnUserRoleSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.roleID == '' || $scope.roleID == null) &                 
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

function fnUserRoleSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultRoleId($scope))
				return false;

			break;

//		case 'Save':
//			if (!fnDefaultRoleId($scope))
//				return false;
//
//			break;
                      case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

			if (!fngetSelectedIndex($scope.UserRoleSummaryTable, $scope)) //Generic For Summary
				return false;
			return true;
			break;


	}
	return true;
}

function fnDefaultRoleId($scope) {
	var availabilty = false;
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnUserRoleSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
		$scope.roleID = "";
	    $scope.authStat = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.UserRoleSummaryTable = null;
		$scope.UserRoleSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.roleIDreadOnly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.roleIDreadOnly = true;
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
function fnpaymentDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate"));
		$scope.$apply();
}

function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate"));
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'UserRoleSummaryTable':
		   
			var UserRoleSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     UserRoleSummaryTable[index] = new Object();
					 UserRoleSummaryTable[index].idx=index;
					 UserRoleSummaryTable[index].checkBox=false;
					 UserRoleSummaryTable[index].roleID=value.roleID;
                                         UserRoleSummaryTable[index].roleDescription=value.roleDescription;
					}
			return UserRoleSummaryTable;
			break ;
		}
	}
        
        
function fnUserRoleSummarypostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status === 'success') {
		// Specific Screen Scope Starts
		  $scope.roleIDreadOnly = true;
	          $scope.authStatreadOnly = true;
	          $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Start
                $scope.roleID = response.body.filter.roleID;
                $scope.authStat = response.body.filter.authStat;
		//Multiple View Response Starts 
		$scope.UserRoleSummaryTable=fnConvertmvw('UserRoleSummaryTable',response.body.SummaryResult);
		$scope.UserRoleSummaryCurPage = 1;
		$scope.UserRoleSummaryShowObject=$scope.fnMvwGetCurPageTable('UserRoleSummary');
		//Multiple View Response Ends 
            }    
		return true;

}



	
