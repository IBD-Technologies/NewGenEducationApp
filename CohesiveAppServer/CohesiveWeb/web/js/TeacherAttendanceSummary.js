/* 
    Author     : Munish Kumar B
	IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
    $scope.teacherName = "";
	$scope.teacherID = "";
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	$scope.date = "";
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
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.datereadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.TeacherAttendanceSummaryCurPage = 0;
	$scope.TeacherAttendanceSummaryTable = null;
	$scope.TeacherAttendanceSummaryShowObject = null;
	// multiple View Ends
        //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'TeacherAttendanceSummary') {
			if ($scope.TeacherAttendanceSummaryTable != null && $scope.TeacherAttendanceSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.TeacherAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.TeacherAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'TeacherAttendanceSummary') {
			if ($scope.TeacherAttendanceSummaryTable != null && $scope.TeacherAttendanceSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.TeacherAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.TeacherAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherAttendanceSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					teacherName: "",
					teacherID: "",
				    date: ""
				};
				if ($scope.TeacherAttendanceSummaryTable == null)
					$scope.TeacherAttendanceSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.TeacherAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherAttendanceSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.TeacherAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'TeacherAttendanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherAttendanceSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherAttendanceSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherAttendanceSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'TeacherAttendanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherAttendanceSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherAttendanceSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherAttendanceSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'TeacherAttendanceSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.TeacherAttendanceSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
});
//Default Load Record Starts
$(document).ready(function () {
	MenuName = "TeacherAttendanceSummary";
        window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "TeacherSummaryEntity";
	window.parent.fn_hide_parentspinner();
	fnDatePickersetDefault('attendanceDate',fndateEventHandler);
	fnsetDateScope();
	selectBoxes= ['authStatus'];
         fnGetSelectBoxdata(selectBoxes);//Integration changes
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts
function fnTeacherAttendanceSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.date = "";
	$scope.authStat = "";
	$scope.feeType = "Select option";
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.datereadOnly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
	$scope.selectedIndex =0;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.teacherID=$scope.TeacherAttendanceSummaryTable[$scope.selectedIndex].teacherID;
	lscreenKeyObject.date=$scope.TeacherAttendanceSummaryTable[$scope.selectedIndex].date;

	fninvokeDetailScreen('TeacherAttendance',lscreenKeyObject,$scope);
	
	
	
	
	return true;
}
// Cohesive Query Framework Ends
// Cohesive View Framework Starts
function fnTeacherAttendanceSummaryView() {
	var emptyTeacherAttendanceSummary = {
		filter: {
			teacherName: "",
			teacherID: "",
			date: "",
			authStat: ''
		},
		SummaryResult: [{
				teacherName: "",
				teacherID:"",
				date: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTeacherAttendanceSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherName!=null)
	dataModel.filter.teacherName = $scope.teacherName;
         if($scope.teacherID!=null)
	dataModel.filter.teacherID = $scope.teacherID;
       if($scope.date!=null)
	dataModel.filter.date = $scope.date;
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('TeacherAttendanceSummary', 'View', dataModel, [{entityName:"teacherID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnTeacherAttendanceSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.teacherName == '' || $scope.teacherName == null) &                 
					    ($scope.date == '' || $scope.date == null) &
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

function fnTeacherAttendanceSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.TeacherAttendanceSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnTeacherAttendanceSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	   $scope.teacherName = "";
	   $scope.teacherID = "";
	   $scope.date = "";
	   $scope.authStat = "";
	   $scope.mvwAddDeteleDisable = true; //Multiple View
	   $scope.TeacherAttendanceSummaryTable = null;
	   $scope.TeacherAttendanceSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	        $scope.teacherNamereadOnly = false;
		$scope.teacherIDreadOnly = false;
		$scope.datereadOnly = false;
	        $scope.authStatreadOnly = false;
	        $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
		 $scope.teacherNamereadOnly = true;
		$scope.teacherIDreadOnly = true;
		$scope.datereadOnly = true;
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
function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#attendanceDate").datepicker("getDate")),
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      $( "#attendanceDate" ).datepicker('setDate','');
	  $scope.date = null;
	//$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#attendanceDate").datepicker("getDate"));
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'TeacherAttendanceSummaryTable':
		   
			var TeacherAttendanceSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     TeacherAttendanceSummaryTable[index] = new Object();
					 TeacherAttendanceSummaryTable[index].idx=index;
					 TeacherAttendanceSummaryTable[index].checkBox=false;
					 TeacherAttendanceSummaryTable[index].teacherID=value.teacherID;
					 TeacherAttendanceSummaryTable[index].teacherName=value.teacherName;
					 TeacherAttendanceSummaryTable[index].date=value.date;
					}
			return TeacherAttendanceSummaryTable;
			break ;
		}
	}
	
function fnTeacherAttendanceSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		   $scope.teacherNamereadOnly = true;
		   $scope.teacherIDreadOnly = true;
		   $scope.datereadOnly = true;
	           $scope.authStatreadOnly = true;
	           $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.teacherID = response.body.filter.teacherID;
		$scope.teacherName = response.body.filter.teacherName;
                $scope.date = response.body.filter.date;
		$scope.authStat = response.body.filter.authStat;
		$scope.SummaryResult ={};
		 }
                else
                {
                //Multiple View Response Starts 
		$scope.TeacherAttendanceSummaryTable=fnConvertmvw('TeacherAttendanceSummaryTable',response.body.SummaryResult);
		$scope.TeacherAttendanceSummaryCurPage = 1;
		$scope.TeacherAttendanceSummaryShowObject=$scope.fnMvwGetCurPageTable('TeacherAttendanceSummary');
		//Multiple View Response Ends 
            }
		return true;

}
}
