/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
    $scope.activityName="";
	$scope.activityType="";
	$scope.level="";
	$scope.date="";
	$scope.dueDate="";
	$scope.authStat = "";
	$scope.classes=Institute.ClassMaster;
	$scope.levels=Institute.OtherActivityLevelMaster;
	$scope.ActivityTypeMaster=Institute.ActivityTypeMaster;
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
	$scope.activityNamereadOnly=false;
	$scope.activityTypereadOnly=false;
	$scope.levelreadOnly=false;
	$scope.venuereadOnly=false;
	$scope.datereadOnly=false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.InsOtherActivitySummaryCurPage = 0;
	$scope.InsOtherActivitySummaryTable = null;
	$scope.InsOtherActivitySummaryShowObject = null;
	// multiple View Ends
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'InsotherActivitySummary') {
			if ($scope.InsOtherActivitySummaryTable != null && $scope.InsOtherActivitySummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.InsOtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.InsOtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.InsOtherActivitySummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.InsOtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.InsOtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.InsOtherActivitySummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'InsotherActivitySummary') {
			if ($scope.InsOtherActivitySummaryTable != null && $scope.InsOtherActivitySummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.InsOtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.InsOtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.InsOtherActivitySummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.InsOtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.InsOtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.InsOtherActivitySummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'InsotherActivitySummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
				    activityName:"",
				    activityType: "",
				    date: "",
				    dueDate: "",
				    level: "",
				    result: ""
				};
				if ($scope.InsOtherActivitySummaryTable == null)
					$scope.InsOtherActivitySummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.InsOtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.InsOtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.InsOtherActivitySummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.InsOtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.InsOtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.InsOtherActivitySummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'InsotherActivitySummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.InsOtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.InsOtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.InsOtherActivitySummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.InsOtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.InsOtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.InsOtherActivitySummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'InsotherActivitySummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.InsOtherActivitySummaryCurPage;
			lsvwObject.tableObject = $scope.InsOtherActivitySummaryTable;
			lsvwObject.screenShowObject = $scope.InsOtherActivitySummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'InsotherActivitySummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.InsOtherActivitySummaryCurPage;
			lsvwObject.tableObject = $scope.InsOtherActivitySummaryTable;
			lsvwObject.screenShowObject = $scope.InsOtherActivitySummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'InsotherActivitySummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.InsOtherActivitySummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "InstituteOtherActivitySummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="InstituteSummaryEntity";
        
//	     fnDatePickersetDefault('activityDate',fndateEventHandler);
	    //fnDatePickersetDefault('activitydueDate',fndueDateEventHandler);
//         fnsetDateScope();
	    selectBoxes= ['activityLevel','class','authStatus','activityType'];
        fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts


function fnInstituteOtherActivitySummarypostSelectBoxMaster()
{
   
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
         if(Institute.ClassMaster.length>0)
      {   
      $scope.classes=Institute.ClassMaster;
	 window.parent.fn_hide_parentspinner();  
          $scope.$apply();
	
        }
        }

function fnInstituteOtherActivitySummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
        $scope.activityName="";
	$scope.activityType="";
	$scope.level= "";
	$scope.date="";
	$scope.dueDate="";
	$scope.authStat = "";
	
	$scope.studentNamereadOnly=true;
	$scope.activityNamereadOnly=true;
	$scope.activityTypereadOnly=true;
	$scope.levelreadOnly=true;
	$scope.venuereadOnly=true;
	$scope.datereadOnly=true;
	$scope.dueDatereadOnly=true;
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
	lscreenKeyObject.activityID=$scope.InsOtherActivitySummaryTable[$scope.selectedIndex].activityID;
//	lscreenKeyObject.activityType=$scope.InsOtherActivitySummaryTable[$scope.selectedIndex].activityType;
//	lscreenKeyObject.activityLevel=$scope.InsOtherActivitySummaryTable[$scope.selectedIndex].activityLevel;
	fninvokeDetailScreen('InstituteOtheractivity',lscreenKeyObject,$scope);
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnInstituteOtherActivitySummaryView() {
	var emptyInstituteOtherActivitySummary = {
		filter: {
		     
//			activityName: "",
			activityType: "",
//			date: "",
//			dueDate: "",
			level: "",
			authStat: ""
		},
		SummaryResult: [{
			        activityID:"",
				activityName:"",
				activityType: "",
				date: "",
//				dueDate: "",
				level: "",
//				result: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstituteOtherActivitySummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View";
        if($scope.activityID!=null)
	dataModel.filter.activityID = $scope.activityID;
        if($scope.activityName!=null)
	dataModel.filter.activityName = $scope.activityName;
        if($scope.activityType!=null)
	dataModel.filter.activityType = $scope.activityType;
         if($scope.date!=null)
	dataModel.filter.date = $scope.date;
        if($scope.dueDate!=null)
	dataModel.filter.dueDate = $scope.dueDate;
       if($scope.level!=null)
	dataModel.filter.level = $scope.level;
       if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
       if($scope.recordStat!=null)
	dataModel.filter.recordStat = $scope.recordStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('InstituteOtherActivitySummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	/*if (response.header.status == 'success') {
		// Screen Specific Scope Starts		
	    $scope.activityNamereadOnly=true;
	    $scope.activityTypereadOnly=true;
	    $scope.levelreadOnly=true;
	    $scope.venuereadOnly=true;
	    $scope.datereadOnly=true;
	    $scope.dueDatereadOnly=true;
	    $scope.authStatreadOnly = true;
    	    $scope.recordStatreadOnly = true;
		 $scope.mvwAddDeteleDisable = true; //Multiple View
		// Screen Specific Scope Ends	
		// Generic Field Starts	
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.operation = "View";
		$scope.selectedIndex =0;
		// Generic Field Ends		
		//Multiple View Response Starts 
		$scope.InsOtherActivitySummaryTable=fnConvertmvw('InsOtherActivitySummaryTable',response.body.SummaryResult);
		$scope.InsOtherActivitySummaryCurPage = 1
		$scope.InsOtherActivitySummaryShowObject=$scope.fnMvwGetCurPageTable('InsotherActivitySummary');
		//Multiple View Response Ends 
		
		return true;
	} else {
		return false;
	}*/
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnInstituteOtherActivitySummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.activityName == '' || $scope.activityName == null) &
						($scope.activityType == '' || $scope.activityType == null || $scope.activityType == 'Select option') &
						($scope.date == '' || $scope.date == null) & 
						($scope.dueDate == '' || $scope.dueDate == null) &
						($scope.level == '' || $scope.level == null || $scope.level == "Select option") &
						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == "Select option")))
		
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

function fnInstituteOtherActivitySummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.InsOtherActivitySummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation Ends
function fnInstituteOtherActivitySummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Start
        $scope.activityName="";
	    $scope.activityType="";
	    $scope.level= "";
	    $scope.date="";
	    $scope.dueDate="";
	    $scope.authStat = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.InsOtherActivitySummaryTable = null;
		$scope.InsOtherActivitySummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.activityNamereadOnly=false;
	    $scope.activityTypereadOnly=false;
	    $scope.levelreadOnly=false;
	    $scope.venuereadOnly=false;
	    $scope.datereadOnly=false;
	    $scope.dueDatereadOnly=false;
	    $scope.authStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.activityNamereadOnly=true;
	    $scope.activityTypereadOnly=true;
	    $scope.levelreadOnly=true;
	    $scope.venuereadOnly=true;
	    $scope.datereadOnly=true;
	    $scope.dueDatereadOnly=true;
	    $scope.authStatreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
               // Generic Scope Ends	
}
// Cohesive Create Framework Ends
function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#activityDate").datepicker("getDate"));
		$scope.$apply();
}
/*
function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#activitydueDate").datepicker("getDate"));
		$scope.$apply();
}*/
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#activityDate").datepicker("getDate"));
	 $( "#activityDate" ).datepicker('setDate','');
	  $scope.date = null;
		$scope.$apply();
}	
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'InsOtherActivitySummaryTable':
		   
			var InsOtherActivitySummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     InsOtherActivitySummaryTable[index] = new Object();
					 InsOtherActivitySummaryTable[index].idx=index;
					 InsOtherActivitySummaryTable[index].checkBox=false;
					 InsOtherActivitySummaryTable[index].activityID=value.activityID;
					 InsOtherActivitySummaryTable[index].activityType=value.activityType;
					 InsOtherActivitySummaryTable[index].activityName=value.activityName;
					 InsOtherActivitySummaryTable[index].level=value.level;
					 InsOtherActivitySummaryTable[index].date=value.date;
//					 InsOtherActivitySummaryTable[index].dueDate=value.dueDate;
					 
					
					}
			return InsOtherActivitySummaryTable;
			break ;
		}
	}
	
function fnInstituteOtherActivitySummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		  $scope.activityNamereadOnly=true;
	          $scope.activityTypereadOnly=true;
	          $scope.levelreadOnly=true;
	          $scope.venuereadOnly=true;
	          $scope.datereadOnly=true;
	          $scope.dueDatereadOnly=true;
	          $scope.authStatreadOnly = true;
    	          $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
//                $scope.activityName =response.body.filter.activityName;
	        $scope.activityType =response.body.filter.activityType;
//                $scope.date =response.body.level.filter.date;
                $scope.level =response.body.filter.level;
                //$scope.dueDate =response.body.dueDate;
		        $scope.authStat =response.body.filter.authStat;
                //Multiple View Response Starts 
		$scope.InsOtherActivitySummaryTable=fnConvertmvw('InsOtherActivitySummaryTable',response.body.SummaryResult);
		$scope.InsOtherActivitySummaryCurPage = 1;
		$scope.InsOtherActivitySummaryShowObject=$scope.fnMvwGetCurPageTable('InsotherActivitySummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;
        }
		return true;

}
