/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentName ="";
        $scope.studentID = "";
        $scope.class = "Select option";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName:""
	}];
    $scope.activityName="";
	$scope.activityType="";
	$scope.level="";
        $scope.result="";
	$scope.venue="Select option";
//	$scope.date="";
        $scope.toDate = "";
        $scope.fromDate="";
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
          $scope.OperationScopes=OperationScopes;
	$scope.selectedIndex =0;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.studentNamereadOnly=false;
        $scope.studentNameInputReadOnly=true;
	$scope.activityNamereadOnly=false;
	 $scope.classReadonly = false;
	$scope.activityTypereadOnly=false;
	$scope.levelreadOnly=false;
        $scope.resultreadOnly=false;
	$scope.venuereadOnly=false;
//	$scope.datereadOnly=false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.OtherActivitySummaryCurPage = 0;
	$scope.OtherActivitySummaryTable = null;
	$scope.OtherActivitySummaryShowObject = null;
	// multiple View Ends
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'otherActivitySummary') {
			if ($scope.OtherActivitySummaryTable != null && $scope.OtherActivitySummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.OtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.OtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.OtherActivitySummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.OtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.OtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.OtherActivitySummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'otherActivitySummary') {
			if ($scope.OtherActivitySummaryTable != null && $scope.OtherActivitySummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.OtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.OtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.OtherActivitySummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.OtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.OtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.OtherActivitySummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'otherActivitySummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentID: "",
				    studentName: "",
				    activityName:"",
				    class:"",
				    activityType: "",
				    date: "",
				    level: "",
				    venue: "",
				    result: ""
				};
				if ($scope.OtherActivitySummaryTable == null)
					$scope.OtherActivitySummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.OtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.OtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.OtherActivitySummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.OtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.OtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.OtherActivitySummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'otherActivitySummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.OtherActivitySummaryCurPage;
				lsvwObject.tableObject = $scope.OtherActivitySummaryTable;
				lsvwObject.screenShowObject = $scope.OtherActivitySummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.OtherActivitySummaryCurPage = lsvwObject.curPage;
				$scope.OtherActivitySummaryTable = lsvwObject.tableObject;
				$scope.OtherActivitySummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'otherActivitySummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.OtherActivitySummaryCurPage;
			lsvwObject.tableObject = $scope.OtherActivitySummaryTable;
			lsvwObject.screenShowObject = $scope.OtherActivitySummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'otherActivitySummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.OtherActivitySummaryCurPage;
			lsvwObject.tableObject = $scope.OtherActivitySummaryTable;
			lsvwObject.screenShowObject = $scope.OtherActivitySummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'otherActivitySummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.OtherActivitySummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
	$scope.fnStudentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Student';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

	
	
});
//--------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	MenuName = "StudentOtherActivitySummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="StudentSummaryEntity";
       	 fnDatePickersetDefault('fromDate',fndateEventHandler);
         fnDatePickersetDefault('toDate',fndateEventHandler);
         fnsetDateScope();
        
         selectBoxes= ['activityLevel','authStatus','activityType','Class'];		 
         fnGetSelectBoxdata(selectBoxes);
	
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnStudentOtherActivitySummarypostSelectBoxMaster()
{
  var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   if(Institute.ClassMaster.length>0){
         $scope.classes=Institute.ClassMaster;
	 window.parent.fn_hide_parentspinner(); 
          $scope.$apply();
}
}
function fnStudentOtherActivitySummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.studentName ="";
    $scope.studentID = "";
    $scope.activityName="";
	$scope.activityType="";
	$scope.level= "";
        $scope.result= "";
	$scope.venue="Select option";
//	$scope.date="";
        $scope.toDate = "";
        $scope.fromDate="";
	$scope.authStat = "";
	
	$scope.studentNamereadOnly=true;
        $scope.studentNameInputReadOnly=true;
	 $scope.classReadonly = true;
	$scope.activityNamereadOnly=true;
	$scope.activityTypereadOnly=true;
	$scope.levelreadOnly=true;
        $scope.resultreadOnly=true;
	$scope.venuereadOnly=true;
	$scope.datereadOnly=true
        $( "#fromDate" ).datepicker( "option", "disabled", true );
        $( "#toDate" ).datepicker( "option", "disabled", true );
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
	lscreenKeyObject.studentID=$scope.OtherActivitySummaryTable[$scope.selectedIndex].studentID;
        lscreenKeyObject.activityID=$scope.OtherActivitySummaryTable[$scope.selectedIndex].activityID;
	fninvokeDetailScreen('StudentOtheractivity',lscreenKeyObject,$scope);
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentOtherActivitySummaryView() {
	var emptyStudentOtherActivitySummary = {
		filter: {
			studentName: "",
			studentID: "",
//			activityName: "",
//			activityType: "",
//			date: "",
                        toDate : "",
                        fromDate:"",
			result: "",
//			venue: "Select option",
//			authStat: ""
		},
		SummaryResult: [{
				studentID: "",
                                activityID: "",
//				studentName: "",
				activityName:"",
				activityType: "",
				result: "",
				date: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentOtherActivitySummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View";
        if($scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
        if($scope.studentName!=null)
            dataModel.filter.studentName = $scope.studentName;
        if($scope.activityType!=null)
	dataModel.filter.activityType = $scope.activityType;
        if($scope.fromDate!=null)
	dataModel.filter.fromDate = $scope.fromDate;
        if($scope.toDate!=null)
	dataModel.filter.toDate = $scope.toDate;
        if($scope.level!=null)
	dataModel.filter.level = $scope.level;
    if($scope.result!=null)
	dataModel.filter.result = $scope.result;
   if($scope.class!=null)
	dataModel.filter.class = $scope.class;
        if($scope.venue!=null)
	dataModel.filter.venue = $scope.venue;
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentOtherActivitySummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentOtherActivitySummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.studentName == '' || $scope.studentName == null) &                 
//					    ($scope.activityName == '' || $scope.activityName == null) &
//						($scope.activityType == '' || $scope.activityType == null || $scope.activityType == 'Select option') &
//						($scope.fromDate == '' || $scope.fromDate == null) &
//                                                ($scope.toDate == '' || $scope.toDate == null) &
//                                                ($scope.result == '' || $scope.result == null) &
//						($scope.class == '' || $scope.class == null || $scope.class == "Select option") &
//						($scope.level == '' || $scope.level == null || $scope.level == "Select option") &
//						($scope.venue == '' || $scope.venue == null || $scope.venue == "Select option") &
//						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == "Select option")))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
//                                  if ($scope.studentID == '' || $scope.studentID == null) {
//
//                                                                fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
//                                                                return false;
//                                                        }
                                 if ($scope.fromDate == '' || $scope.fromDate == null) {

                                                                fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
                                                                return false;
                                                        }
                              if ($scope.toDate == '' || $scope.toDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['To Date']);
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

function fnStudentOtherActivitySummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultStudentId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultStudentId($scope))
				return false;

			break;
                 case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.OtherActivitySummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}

function fnDefaultStudentId($scope) {
	var availabilty = false;
	return true;
}

// Screen Specific Mandatory Validation Ends
function fnStudentOtherActivitySummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
		$scope.studentName ="";
        $scope.studentID = "";
        $scope.activityName="";
	    $scope.activityType="";
	    $scope.level= "";
            $scope.result= "";
	    $scope.venue= "Select option";
	    $scope.fromDate="";
            $scope.toDate="";
	    $scope.authStat = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.OtherActivitySummaryTable = null;
		$scope.OtherActivitySummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.studentNamereadOnly=false;
            $scope.studentNameInputReadOnly=true;
	    $scope.activityNamereadOnly=false;
		 $scope.classReadonly = false;
	    $scope.activityTypereadOnly=false;
	    $scope.levelreadOnly=false;
            $scope.resultreadOnly=false;
	    $scope.venuereadOnly=false;
	    $scope.datereadOnly=false;
            $( "#fromDate" ).datepicker( "option", "disabled", false );
            $( "#toDate" ).datepicker( "option", "disabled", false );
	    $scope.authStatreadOnly = false;
    	$scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
		$scope.studentNamereadOnly=true;
                $scope.studentNameInputReadOnly=true;
	    $scope.activityNamereadOnly=true;
	    $scope.activityTypereadOnly=true;
	    $scope.levelreadOnly=true;
            $scope.resultreadOnly=true;
	    $scope.venuereadOnly=true;
	    $scope.datereadOnly=true;
            $( "#fromDate" ).datepicker( "option", "disabled", true );
            $( "#toDate" ).datepicker( "option", "disabled", true );
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
	$scope.fromDate = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate"));
        $scope.toDate = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	 $( "#fromDate" ).datepicker('setDate','');
	  $scope.fromDate = null;
          $( "#toDate" ).datepicker('setDate','');
	  $scope.toDate = null;
	  //$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#activityDate").datepicker("getDate"));
		$scope.$apply();
}	


function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'OtherActivitySummaryTable':
		   
			var OtherActivitySummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     OtherActivitySummaryTable[index] = new Object();
					 OtherActivitySummaryTable[index].idx=index;
					 OtherActivitySummaryTable[index].checkBox=false;
					 OtherActivitySummaryTable[index].studentID=value.studentID;
					 OtherActivitySummaryTable[index].activityID=value.activityID;
                                         OtherActivitySummaryTable[index].activityType=value.activityType;
					 OtherActivitySummaryTable[index].activityName=value.activityName;
					 OtherActivitySummaryTable[index].result=value.result;
					 OtherActivitySummaryTable[index].date=value.date;
					 
					
					}
			return OtherActivitySummaryTable;
			break ;
		}
	}
	
function fnStudentOtherActivitySummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.studentNamereadOnly=true;
                $scope.studentNameInputReadOnly=true;
	        $scope.activityNamereadOnly=true;
	        $scope.activityTypereadOnly=true;
			 $scope.classReadonly = true;
	        $scope.levelreadOnly=true;
                $scope.resultreadOnly=true;
	        $scope.venuereadOnly=true;
	        $scope.datereadOnly=true;
                $( "#fromDate" ).datepicker( "option", "disabled", true );
                $( "#toDate" ).datepicker( "option", "disabled", true );
	        $scope.authStatreadOnly = true;
    	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.studentName = response.body.filter.studentName;
		$scope.studentID = response.body.filter.studentID;
//                $scope.activityType =  response.body.filter.activityType;
                $scope.result = response.body.filter.result;
                //$scope.venue =  response.body.venue;
                $scope.fromDate =  response.body.filter.fromDate;
                $scope.toDate =  response.body.filter.toDate;
//                $scope.class =  response.body.class;
//		        $scope.authStat =  response.body.filter.authStat;
                //$scope.recordStat = response.body.recordStat;
                $scope.OtherActivitySummaryTable=fnConvertmvw('OtherActivitySummaryTable',response.body.SummaryResult);
		        $scope.OtherActivitySummaryCurPage = 1;
		        $scope.OtherActivitySummaryShowObject=$scope.fnMvwGetCurPageTable('otherActivitySummary');
		//Multiple View Response Ends 
        }
		return true;

}