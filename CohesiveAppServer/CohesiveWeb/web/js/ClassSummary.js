/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.class = 'Select option';
	//$scope.date = "";
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
        $scope.classReadonly = false;
	$scope.authStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.ClassSummaryCurPage = 0;
	$scope.ClassSummaryTable = null;
	$scope.ClassShowObject = null;
	// multiple View Ends
       //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'ClassSummary') {
			if ($scope.ClassSummaryTable != null && $scope.ClassSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.ClassSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.ClassSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSummaryTable = lsvwObject.tableObject;
				$scope.ClassSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'ClassSummary') {
			if ($scope.ClassSummaryTable != null && $scope.ClassSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.ClassSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.ClassSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSummaryTable = lsvwObject.tableObject;
				$scope.ClassSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					class: "",
				//	date: "",
					authStat: "",
				//	recordStat: ""
				};
				if ($scope.ClassSummaryTable == null)
					$scope.ClassSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.ClassSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSummaryTable = lsvwObject.tableObject;
				$scope.ClassSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ClassSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassSummaryCurPage;
				lsvwObject.tableObject = $scope.ClassSummaryTable;
				lsvwObject.screenShowObject = $scope.ClassSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.ClassSummaryCurPage = lsvwObject.curPage;
				$scope.ClassSummaryTable = lsvwObject.tableObject;
				$scope.ClassSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'ClassSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'ClassSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassSummaryCurPage;
			lsvwObject.tableObject = $scope.ClassSummaryTable;
			lsvwObject.screenShowObject = $scope.ClassSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'ClassSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.ClassSummaryTable);
			
		}
	};

	//Multiple View Scope Function ends 	
});
//--------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	MenuName = "ClassSummary";
	  window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="ClassSummaryEnity";
	//fnDatePickersetDefault('AttendanceDate',fndateEventHandler);
	fnsetDateScope();
	selectBoxes= ['class','authStatus'];
        fnGetSelectBoxdata(selectBoxes);
      
       
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnClassSummarypostSelectBoxMaster()
{
 var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   if(Institute.ClassMaster.length>0)
      {    
         $scope.classes=Institute.ClassMaster;
         $scope.class = 'Select option';
         window.parent.fn_hide_parentspinner();
         //window.parent.fn_hide_parentspinner();
         $scope.$apply();
}
}
function fnClassSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.class = 'Select option';
	//$scope.date = "";
	$scope.authStat = "";
	
	$scope.classReadonly = true;
	//$scope.dateReadOnly = true;
	$scope.authStatreadOnly = true;
	//$scope.recordStatreadOnly = true;
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
	lscreenKeyObject.class=$scope.ClassSummaryTable[$scope.selectedIndex].class;
	//lscreenKeyObject.date=$scope.ClassSummaryTable[$scope.selectedIndex].date;
	fninvokeDetailScreen('Class',lscreenKeyObject,$scope);		
	return true;
}
// Cohesive Query Framework Ends
// Cohesive View Framework Starts
function fnClassSummaryView() {
	var emptyClassSummary = {
		filter: {
			Class: 'Select option',
			//date: "",
			authStat: 'Select option',
			//recordStat: 'Select option'
		},
		SummaryResult: [{
				Class: "",
				teacherID:"",
                                teacherName:""
				//authStat: ""
				//recordStat: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyClassSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.class!=null)
	dataModel.filter.Class = $scope.class;
        /*if($scope.date!=null)
	dataModel.filter.date = $scope.date;*/
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
      //  if($scope.recordStat!=null)
	//dataModel.filter.recordStat = $scope.recordStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ClassSummary', 'View', dataModel, [{entityName:"Class",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnClassSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					    if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &                 
						//($scope.date == '' || $scope.date == null) &
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
function fnClassSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.ClassSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation Ends
function fnClassSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.class = 'Select option';
	    //$scope.date = "";
	    $scope.authStat = "";
    	    
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.ClassSummaryTable = null;
	    $scope.ClassSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.classReadonly = false;
    	   // $scope.dateReadOnly = false;
	    $scope.authStatreadOnly = false;
	    //$scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
		}
	    else {
            $scope.classReadonly = false;
            //$scope.dateReadOnly = true;
	    $scope.authStatreadOnly = true;
	    //$scope.recordStatreadOnly = true;
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
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#AttendanceDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#AttendanceDate").datepicker("getDate"));
      $( "#AttendanceDate" ).datepicker('setDate','');
	  $scope.date = null;
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'ClassSummaryTable':
		   
			var ClassSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				         ClassSummaryTable[index] = new Object();
					 ClassSummaryTable[index].idx=index;
					 ClassSummaryTable[index].checkBox=false;
					 ClassSummaryTable[index].class=value.Class;
					 ClassSummaryTable[index].teacherID=value.teacherID;
					 ClassSummaryTable[index].teacherName=value.teacherName;
					 
                                          //ClassSummaryTable[index].date=value.date;
					// ClassSummaryTable[index].authStat=value.authStat;
					 //ClassSummaryTable[index].recordStat=value.recordStat;
					}
			return ClassSummaryTable;
			break ;
		}
	}
        
        
function fnClassSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.classReadonly = true;
               //  $scope.dateReadOnly = true;
	         $scope.authStatreadOnly = true;
	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.class = response.body.filter.Class;
              //  $scope.date = response.body.date;
		$scope.authStat = response.body.filter.authStat;
                //$scope.recordStat = response.body.recordStat;
                //Multiple View Response Starts 
		$scope.ClassSummaryTable=fnConvertmvw('ClassSummaryTable',response.body.SummaryResult);
		$scope.ClassSummaryCurPage = 1;
		$scope.ClassSummaryShowObject=$scope.fnMvwGetCurPageTable('ClassSummary');
		//Multiple View Response Ends 
                // Specific Screen Scope Response Ends	
		return true;

}
}
	
