/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName:""
	}];
	$scope.studentName = "";
	$scope.studentID = "";
//	$scope.date = "";
        $scope.toDate = "";
        $scope.fromDate="";
	$scope.class = "Select option";
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
//	$scope.datereadOnly = false;
        $scope.fromDatereadOnly = false;
        $scope.toDatereadOnly = false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	$scope.studentNamereadOnly = false;
        $scope.studentNameInputReadOnly=true;
	$scope.classReadonly = false;
	$scope.studentIDreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.StudentAttendanceSummaryCurPage = 0;
	$scope.StudentAttendanceSummaryTable = null;
	$scope.StudentAttendanceSummaryShowObject = null;
	// multiple View Ends
    //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'StudentAttendanceSummary') {
			if ($scope.StudentAttendanceSummaryTable != null && $scope.StudentAttendanceSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAttendanceSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.StudentAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'StudentAttendanceSummary') {
			if ($scope.StudentAttendanceSummaryTable != null && $scope.StudentAttendanceSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAttendanceSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.StudentAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudentAttendanceSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentID: "",
					studentName: "",
					class:"",
				    date: ""
				};
				if ($scope.StudentAttendanceSummaryTable == null)
					$scope.StudentAttendanceSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAttendanceSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.StudentAttendanceSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudentAttendanceSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentAttendanceSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAttendanceSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAttendanceSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentAttendanceSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAttendanceSummaryTable = lsvwObject.tableObject;
				$scope.StudentAttendanceSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'StudentAttendanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentAttendanceSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentAttendanceSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentAttendanceSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'StudentAttendanceSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentAttendanceSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentAttendanceSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentAttendanceSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'StudentAttendanceSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentAttendanceSummaryTable);
			
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
//Default Load Record Starts
$(document).ready(function () {
	MenuName = "StudentAttendanceSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="StudentSummaryEntity";
	fnDatePickersetDefault('fromDate',fninstantEventHandler);
        fnDatePickersetDefault('toDate',fninstantEventHandler);
        fnsetDateScope();
	selectBoxes= ['authStatus','recordStatus','class'];
         fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts


function fnStudentAttendanceSummarypostSelectBoxMaster()
{
 var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if(Institute.ClassMaster.length>0)
      {    
        $scope.classes=Institute.ClassMaster;
        window.parent.fn_hide_parentspinner();
        $scope.$apply();
    }
}

function fnStudentAttendanceSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
//	$scope.date = "";
        $scope.toDate = "";
        $scope.fromDate="";
	$scope.authStat = "";
	$scope.feeType = "Select option";
	$scope.class = "Select option";
//	$scope.datereadOnly = true;
        $scope.fromDatereadOnly = true;
        $scope.toDatereadOnly = true;
        $( "#fromDate" ).datepicker( "option", "disabled", true );
        $( "#toDate" ).datepicker( "option", "disabled", true );
	$scope.studentNamereadOnly = true;
        $scope.studentNameInputReadOnly=true;
	$scope.classReadonly = true;
	$scope.studentIDreadOnly = true;
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
	lscreenKeyObject.studentID=$scope.StudentAttendanceSummaryTable[$scope.selectedIndex].studentID;
        lscreenKeyObject.date=$scope.StudentAttendanceSummaryTable[$scope.selectedIndex].date;
	fninvokeDetailScreen('StudentAttendance',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends
// Cohesive View Framework Starts
function fnStudentAttendanceSummaryView() {
	var emptyStudentAttendanceSummary = {
		filter: {
			studentName: "",
			studentID: "",
//			date: "",
                        toDate : "",
                        fromDate:"",
			class: "",
			authStat: 'Select option'
		},
		SummaryResult: [{
				studentName: "",
				studentID:"",
				date: "",
				attendance: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentAttendanceSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View";
        if( $scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
        if( $scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
       if( $scope.class!=null)
	dataModel.filter.class = $scope.class;
        if( $scope.fromDate!=null)
	dataModel.filter.fromDate = $scope.fromDate;
if( $scope.toDate!=null)
	dataModel.filter.toDate = $scope.toDate;
        if( $scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentAttendanceSummary', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentAttendanceSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.StudentName == '' || $scope.studentName == null) &  
//                                            ($scope.fromDate == '' || $scope.fromDate == null) &
//                                            ($scope.toDate == '' || $scope.toDate == null) &    
////					    ($scope.date == '' || $scope.date == null) &
//					    ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
//						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
                                        
                                        if ($scope.studentName == '' || $scope.studentName == null) {
		  		               fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				               return false;
			                 }
//                                         if ($scope.studentID == '' || $scope.studentID == null) {
//		  		                fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				                return false;
//			                  }
                                        
                                        
                                        if($scope.fromDate == '' || $scope.fromDate == null){
                                            
                                               fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
				               return false;
                                        
                                         }
                                        
                                        if($scope.toDate == '' || $scope.toDate == null){
                                            
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

function fnStudentAttendanceSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.StudentAttendanceSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnStudentAttendanceSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	   $scope.studentID = null;
	   $scope.studentName = null;
	   $scope.class = 'Select option';
//	   $scope.date = null;
           $scope.fromDate = null;
           $scope.toDate = null;
	   $scope.authStat = "";
	   $scope.mvwAddDeteleDisable = true; //Multiple View
	   $scope.StudentAttendanceSummaryTable = null;
	   $scope.StudentAttendanceSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	        $scope.studentNamereadOnly = false;
                $scope.studentNameInputReadOnly=true;
		$scope.studentIDreadOnly = false;
		$scope.classReadonly = false;
//		$scope.datereadOnly = false;
                $scope.fromDatereadOnly = false;
                $scope.toDatereadOnly = false;
                $( "#fromDate" ).datepicker( "option", "disabled", false );
                $( "#toDate" ).datepicker( "option", "disabled", false );
	        $scope.authStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
		$scope.studentNamereadOnly = true;
                $scope.studentNameInputReadOnly=true;
		$scope.studentIDreadOnly = true;
		$scope.classReadonly = true;
//		$scope.datereadOnly = true;
                $scope.fromDatereadOnly = true;
                $scope.toDatereadOnly = true;
                $( "#fromDate" ).datepicker( "option", "disabled", true );
                $( "#toDate" ).datepicker( "option", "disabled", true );
	        $scope.authStatreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
		
}
// Cohesive Create Framework Ends
function fninstantEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.fromDate = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate")),
	$scope.toDate = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate")),
		
            $scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate"));
         $( "#fromDate" ).datepicker('setDate','');
          $( "#toDate" ).datepicker('setDate','');
	  $scope.instant = null;
		$scope.$apply();
}


function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'StudentAttendanceSummaryTable':
		   
			var StudentAttendanceSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentAttendanceSummaryTable[index] = new Object();
					 StudentAttendanceSummaryTable[index].idx=index;
					 StudentAttendanceSummaryTable[index].checkBox=false;
					 StudentAttendanceSummaryTable[index].studentID=value.studentID;
					 StudentAttendanceSummaryTable[index].studentName=value.studentName;
					 StudentAttendanceSummaryTable[index].attendance=value.attendance;
					 StudentAttendanceSummaryTable[index].date=value.date;
					}
			return StudentAttendanceSummaryTable;
			break ;
		}
	}
	
function fnStudentAttendanceSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.studentNamereadOnly = true;
                $scope.studentNameInputReadOnly=true;
		$scope.studentIDreadOnly = true;
//		$scope.datereadOnly = true;
                $scope.fromDatereadOnly = true;
                $scope.toDatereadOnly = true;
                $( "#fromDate" ).datepicker( "option", "disabled", true );
                $( "#toDate" ).datepicker( "option", "disabled", true );
	        $scope.authStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.studentName =response.body.filter.studentName;
                $scope.studentID =response.body.filter.studentID;
                $scope.class =response.body.filter.class;
                $scope.fromdate =response.body.filter.toDate;
		        $scope.authStat =response.body.filter.authStat;
                $scope.StudentAttendanceSummaryTable=fnConvertmvw('StudentAttendanceSummaryTable',response.body.SummaryResult);
		        $scope.StudentAttendanceSummaryCurPage = 1;
		        $scope.StudentAttendanceSummaryShowObject=$scope.fnMvwGetCurPageTable('StudentAttendanceSummary');
		//Multiple View Response Ends 
        }
		return true;

}