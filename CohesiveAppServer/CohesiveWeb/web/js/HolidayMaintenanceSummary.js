/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
        $scope.month= "";
	$scope.year= "Select option";
	$scope.authStat = "";
	$scope.AuthType = Institute.AuthStatusMaster;
	$scope.Months = Institute.MonthMaster;
	$scope.Years = Institute.YearMaster;
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
	$scope.monthreadOnly = false;
	$scope.yearreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.HolidayMaintanenceSummaryCurPage = 0;
	$scope.HolidayMaintanenceSummaryTable = null;
	$scope.HolidayMaintanenceSummaryShowObject = null;
	// multiple View Ends
        //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'HolidayMaintenanceSummary') {
			if ($scope.HolidayMaintanenceSummaryTable != null && $scope.HolidayMaintanenceSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.HolidayMaintanenceSummaryCurPage;
				lsvwObject.tableObject = $scope.HolidayMaintanenceSummaryTable;
				lsvwObject.screenShowObject = $scope.HolidayMaintanenceSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.HolidayMaintanenceSummaryCurPage = lsvwObject.curPage;
				$scope.HolidayMaintanenceSummaryTable = lsvwObject.tableObject;
				$scope.HolidayMaintanenceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'HolidayMaintenanceSummary') {
			if ($scope.HolidayMaintanenceSummaryTable != null && $scope.HolidayMaintanenceSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.HolidayMaintanenceSummaryCurPage;
				lsvwObject.tableObject = $scope.HolidayMaintanenceSummaryTable;
				lsvwObject.screenShowObject = $scope.HolidayMaintanenceSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.HolidayMaintanenceSummaryCurPage = lsvwObject.curPage;
				$scope.HolidayMaintanenceSummaryTable = lsvwObject.tableObject;
				$scope.HolidayMaintanenceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'HolidayMaintenanceSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					month:"",
				    year: ""
				};
				if ($scope.HolidayMaintanenceSummaryTable == null)
				$scope.HolidayMaintanenceSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.HolidayMaintanenceSummaryCurPage;
				lsvwObject.tableObject = $scope.HolidayMaintanenceSummaryTable;
				lsvwObject.screenShowObject = $scope.HolidayMaintanenceSummaryShowObject;

				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);
                                
				$scope.HolidayMaintanenceSummaryCurPage = lsvwObject.curPage;
				$scope.HolidayMaintanenceSummaryTable = lsvwObject.tableObject;
				$scope.HolidayMaintanenceSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'HolidayMaintenanceSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.HolidayMaintanenceSummaryCurPage;
				lsvwObject.tableObject = $scope.HolidayMaintanenceSummaryTable;
				lsvwObject.screenShowObject = $scope.HolidayMaintanenceSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.HolidayMaintanenceSummaryCurPage = lsvwObject.curPage;
				$scope.HolidayMaintanenceSummaryTable = lsvwObject.tableObject;
				$scope.HolidayMaintanenceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'HolidayMaintenanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.HolidayMaintanenceSummaryCurPage;
			lsvwObject.tableObject = $scope.HolidayMaintanenceSummaryTable;
			lsvwObject.screenShowObject = $scope.HolidayMaintanenceSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'HolidayMaintenanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.HolidayMaintanenceSummaryCurPage;
			lsvwObject.tableObject = $scope.HolidayMaintanenceSummaryTable;
			lsvwObject.screenShowObject = $scope.HolidayMaintanenceSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'HolidayMaintenanceSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.HolidayMaintanenceSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
//	 MenuName = "HolidayMaintaneceSummary";
         MenuName = "HolidayMaintenanceSummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="InstituteSummaryEntity";
         window.parent.fn_hide_parentspinner();   
	 selectBoxes= ['notificationMonth','HolidayYear','authStatus'];
        fnGetSelectBoxdata(selectBoxes);
});
//Default Load Record Ends
// Cohesive Query Framework Starts
function fnHolidayMaintenanceSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.month = "";
	$scope.year= "Select option";
	$scope.authStat = "";
	$scope.monthreadOnly = true;
	$scope.yearreadOnly = true;
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
	lscreenKeyObject.month=$scope.HolidayMaintanenceSummaryTable[$scope.selectedIndex].month;
	lscreenKeyObject.year=$scope.HolidayMaintanenceSummaryTable[$scope.selectedIndex].year;
	fninvokeDetailScreen('HolidayMaintaneceSummary',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnHolidayMaintenanceSummaryView() {
	var emptyHolidayMaintaneceSummary = {
	filter: {
		    month: '',
		    year: 'Select option',
			authStat: ''
		},
		SummaryResult: [{
				month:"",
				year: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyHolidayMaintaneceSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        if($scope.month!=null)
	dataModel.filter.month = $scope.month;
         if($scope.year!=null)
	dataModel.filter.year = $scope.year;
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('HolidayMaintenanceSummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnHolidayMaintenanceSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.month == '' || $scope.month == null || $scope.month == 'Select option') &                 
					    ($scope.year == '' || $scope.year == null || $scope.year == 'Select option') &
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

function fnHolidayMaintenanceSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.HolidayMaintanenceSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnHolidayMaintenanceSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	             $scope.month = "";
    	        $scope.year= "Select option";
	        $scope.authStat = "";
		$scope.mvwAddDeteleDisable = true; //Multiple View
	        $scope.HolidayMaintanenceSummaryTable = null;
		$scope.HolidayMaintanenceSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.monthreadOnly = false;
	    $scope.yearreadOnly = false;
            $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
		}
	    else {
	    $scope.monthreadOnly = true;
	    $scope.yearreadOnly = true;
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
}
// Cohesive save Framework Ends
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'HolidayMaintanenceSummaryTable':
		   
			var HolidayMaintanenceSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     HolidayMaintanenceSummaryTable[index] = new Object();
					 HolidayMaintanenceSummaryTable[index].idx=index;
					 HolidayMaintanenceSummaryTable[index].checkBox=false;
					 HolidayMaintanenceSummaryTable[index].month=value.month;
					 HolidayMaintanenceSummaryTable[index].year=value.year;
					}
			return HolidayMaintanenceSummaryTable;
			break ;
		}
	}
               
function fnHolidayMaintenanceSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.monthreadOnly = true;
	         $scope.yearreadOnly = true;
	         $scope.authStatreadOnly = true;
	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.month = response.body.filter.month;
		$scope.year =response.body.filter.year;
		$scope.authStat =response.body.filter.authStat;
                $scope.recordStat =response.body.filter.recordStat;
              //Multiple View Response Starts 
		$scope.HolidayMaintanenceSummaryTable=fnConvertmvw('HolidayMaintanenceSummaryTable',response.body.SummaryResult);
		$scope.HolidayMaintanenceSummaryCurPage = 1;
		$scope.HolidayMaintanenceSummaryShowObject=$scope.fnMvwGetCurPageTable('HolidayMaintenanceSummary');
		//Multiple View Response Ends 
        }
		return true;

}
	
