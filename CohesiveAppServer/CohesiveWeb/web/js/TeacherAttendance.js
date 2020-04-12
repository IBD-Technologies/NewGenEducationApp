/* 
    Author     : Munish Kumar B
	IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subscreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.date = "";
	$scope.schedule = "";
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	// Generic Field Starts
	$scope.audit = {};
	$scope.operation = '';
	$scope.searchShow = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.mvwAddDeteleDisable = true; // Multiple View
	// Generic Field Ends	
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.datereadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends	

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
	
	//Multiple View Starts
	$scope.TeacherAttendanceCurPage = 0;
	$scope.TeacherAttendanceTable = null;
	$scope.TeacherAttendanceShowObject = null;
	// Multiple View Ends
	
	
	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'TeacherAttendance') {
			if ($scope.TeacherAttendanceTable != null && $scope.TeacherAttendanceTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.TeacherAttendanceCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.TeacherAttendanceCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.TeacherAttendanceShowObject);
			}
			
		}

	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'TeacherAttendance') {
			if ($scope.TeacherAttendanceTable != null && $scope.TeacherAttendanceTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.TeacherAttendanceCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.TeacherAttendanceCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.TeacherAttendanceShowObject);
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherAttendance') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
                    schedule: [{
				    subjectID: "",
				    subjectName: "",
				    class: "",
				    periodNumber: "",
				    attendance: ""
			}],
				};
				if ($scope.TeacherAttendanceTable == null)
					$scope.TeacherAttendanceTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherAttendanceCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.TeacherAttendanceCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherAttendance') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherAttendanceCurPage;
				lsvwObject.tableObject = $scope.TeacherAttendanceTable;
				lsvwObject.screenShowObject = $scope.TeacherAttendanceShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.TeacherAttendanceCurPage = lsvwObject.curPage;
				$scope.TeacherAttendanceTable = lsvwObject.tableObject;
				$scope.TeacherAttendanceShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'TeacherAttendance') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherAttendanceCurPage;
			lsvwObject.tableObject = $scope.TeacherAttendanceTable;
			lsvwObject.screenShowObject = $scope.TeacherAttendanceShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		} 
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'TeacherAttendance') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherAttendanceCurPage;
			lsvwObject.tableObject = $scope.TeacherAttendanceTable;
			lsvwObject.screenShowObject = $scope.TeacherAttendanceShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'TeacherAttendance') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.TeacherAttendanceTable);
			
		}
	};
	//Multiple View Scope Function ends 	
$scope.fnAttendanceButtonClick = function (id)
{
	
if (!($('#'+id).hasClass("btn-success") ||$('#'+id).hasClass("btn-warning1") ||$('#'+id).hasClass("btn-danger")))
{
	fnSetAttendance(id,'P',$scope.TeacherAttendanceShowObject);
}
if ($('#'+id).hasClass("btn-success"))
{
	fnSetAttendance(id,'L',$scope.TeacherAttendanceShowObject);
}
if ($('#'+id).hasClass("btn-warning1"))
{
	fnSetAttendance(id,'A',$scope.TeacherAttendanceShowObject);
}
if ($('#'+id).hasClass("btn-danger"))
{
	fnSetAttendance(id,'-',$scope.TeacherAttendanceShowObject);
}	
	
}
});
 function fnSetAttendance(id,attendance,attendanceObject){
	 
	 var ID = id.split("-");
	 
	 attendanceObject.forEach(function(value,index,array){
		if(ID[0] == value.periodNumber)
		{
		//value.period.forEach(function(value,index,array){
		    if (index==ID[1])
			{	
			 value.attendance=attendance;
			 switch(attendance)
			 {
				 case "P":
			     value.attendenceButtonClass='btn-success';
				 break;
				 case "L":
			     value.attendenceButtonClass='btn-warning1';
				 break;
				 case "A":
			     value.attendenceButtonClass='btn-danger';
				 break;
				 case "-":
			     value.attendenceButtonClass='';
				 break;
			 }
			 return;
			}
		//});
        }
		return;
	 });
 }
//--------------------------------------------------------------------------------------------------------------

// Default  Record Load 
$(document).ready(function () {
	MenuName = "TeacherAttendance";
        window.parent.nokotser=$("#nokotser").val();
	window.parent.Entity="Teacher";
	window.parent.fn_hide_parentspinner();
	fnDatePickersetDefault('attendanceDate',fndateEventHandler);
	fnsetDateScope();
	
});
// Default Load Record Ends
function fnTeacherAttendancepostSelectBoxMaster()
{
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if ((window.parent.TeacherAttendancekey.teacherID !=null && window.parent.TeacherAttendancekey.teacherID !='') && (window.parent.TeacherAttendancekey.date !=null && window.parent.TeacherAttendancekey.date !=''))	
  {
		var teacherID=window.parent.TeacherAttendancekey.teacherID;
		var date=window.parent.TeacherAttendancekey.date;
		
		 window.parent.TeacherAttendancekey.teacherID =null;
		 window.parent.TeacherAttendancekey.date =null;
		
		  fnshowSubScreen(teacherID,date);
		
	}
        var emptyTeacherAttendance = {
		teacherName: "",
		teacherID: "",
		date: "",
		schedule: [{
				subjectID: "",
				subjectName: "",
				standard: "",
				section: "",
				periodNumber: "",
				attendance: ""
			}]
	};
	// Screen Specific Data Model Starts
	var dataModel = emptyTeacherAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.teacherID =User.Id;
        if($scope.teacherID!=null && $scope.teacherID!=""){
	dataModel.teacherID = $scope.teacherID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('TeacherAttendance', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
}
}
function fnTeacherAttendanceDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (($scope.TeacherAttendanceTable ==null)  && parentOperation=='Create')
	{	
	var emptyTeacherAttendance = {
		teacherName: "",
		teacherID: "",
		date: "",
		schedule: [{
				subjectID: "",
				subjectName: "",
				standard: "",
				section: "",
				periodNumber: "",
				attendance: ""
			}]
	};
	var dataModel = emptyTeacherAttendance;
	dataModel.teacherName = $scope.teacherName;
	dataModel.teacherID = $scope.teacherID;
     if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
	 }
	var response = fncallBackend('TeacherAttendance', 'attendanceDefault', dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}],$scope);
	
	   //Multiple View Response Starts 
		$scope.TeacherAttendanceTable = fnConvertmvw('TeacherAttendanceTable',response.body.schedule);
		$scope.TeacherAttendanceCurPage = 1
		$scope.TeacherAttendanceShowObject=$scope.fnMvwGetCurPageTable('TeacherAttendance');
		//Multiple View Response Ends 
		// Screen Specific Scope Response Ends
	    fnAttendanceButtonClrChange($scope.TeacherAttendanceShowObject);		
	    // $scope.$apply();
	 
	 }	

return true;
}
function fnAttendanceButtonClrChange(attendanceObject){
	    attendanceObject.forEach(function(value,index,array){
	     switch(value.attendance){
		 case "-":
		     value.attendenceButtonClass ='';
		 break; 
		 case "P":
		    value.attendenceButtonClass ='btn-success';
		 break;
		 case "L":
		    value.attendenceButtonClass ='btn-warning1';
		 break;
		case "A":
		     value.attendenceButtonClass ='btn-danger';   
		 break;
	     }	
     });
	}
function fnshowSubScreen(teacherID,date)
{
subscreen = true;
var emptyTeacherAttendance = {
		teacherName: "",
		teacherID: "",
		date: "",
		attendance: [{
				subjectID: "",
				subjectName: "",
				standard: "",
				section: "",
				periodNumber: "",
				signature: ""
			}]
	};
	// Screen Specific DataModel Starts
	var dataModel = emptyTeacherAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.teacherID=teacherID;
	if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
	//Screen Specific Datamodel Ends
	var response = fncallBackend('TeacherAttendance', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
// Cohesive Query Framework Starts
function fnTeacherAttendanceQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.date = "";
	$scope.attendance = "";
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.datereadOnly = false;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.operation = 'View';
	$scope.audit = {};
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	// Generic Field Ends
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnTeacherAttendanceView() {
	var emptyTeacherAttendance = {
		teacherName: "",
		teacherID: "",
		date: "",
		attendance: [{
				subjectID: "",
				subjectName: "",
				standard: "",
				section: "",
				periodNumber: "",
				signature: ""
			}]
	};
	// Screen Specific DataModel Starts
	var dataModel = emptyTeacherAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.teacherID =User.Id;
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
	//Screen Specific Datamodel Ends
	var response = fncallBackend('TeacherAttendance', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}

// Cohesive View Framework Ends 
// screen Specific Mandatory Validation Starts     
function fnTeacherAttendanceMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
                     if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			/*if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}*/
			if ($scope.date == '' || $scope.date == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Date']);
				return false;
			}
			break;

		case 'Save':
                    if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}
			if ($scope.date == '' || $scope.date == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Date']);
				return false;
			}
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation Ends

// Screen Specific Default Validation Starts
function fnTeacherAttendanceDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}
// Screen Specific Default Validation Ends

// Cohesive Create Framework Starts
function fnTeacherAttendanceCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts  
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.date = "";
	$scope.schedule = "";
	$scope.TeacherAttendanceTable=null;
	$scope.TeacherAttendanceShowObject=null;
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.datereadOnly = false;
	$scope.attendanceReadOnly = false;
	// Screen Specific Scope Ends  
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	$scope.mvwAddDeteleDisable = true; // Multiple View
	
	// Generic Field Ends	
	return true;
}
// Cohesive Create FrameWork Starts
// Cohesive Edit FrameWork Starts
function fnTeacherAttendanceEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Fied Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        $scope.operation = 'Modification';
	$scope.mvwAddDeteleDisable = true; // Multiple View
	//Generic Field Ends
	// Screen Specific Scope Starts	
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = true;
	$scope.datereadOnly = false;
	$scope.attendanceReadOnly = false;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Edit Framework Ends
// Cohesive Delete Framework Starts
function fnTeacherAttendanceDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = 'Deletion';
	$scope.auditshow = false;
	// Generic Field Ends
	// Screen Specific  Scope Starts	
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.datereadOnly = true;
	//Screen Specific Scope Ends

	return true;
}
// Cohesive Delete Framework Ends


// Cohesive Authorisation Framework Starts
function fnTeacherAttendanceAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Framework Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Framework Ends
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.datereadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Authorisation Framework Ends
// Cohesive Reject Framework Starts
function fnTeacherAttendanceReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	// Generic Framework Ends	
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.datereadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends

	return true;
}
// Cohesive Reject Framework Ends


// Cohesive Back Framework Starts
function fnTeacherAttendanceBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();	
	// Screen Specific Scope Starts
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	$scope.audit = {};
	$scope.date = "";
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.TeacherAttendanceTable=null;
	$scope.TeacherAttendanceShowObject=null;
	}
	
    $scope.teacherNamereadOnly = true;
    $scope.teacherIDreadOnly = true;
    $scope.teacherNameSearchreadOnly = true;
    $scope.datereadOnly = true;
    $scope.periodNumberReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
}
// Cohesive Back Framework Ends

// Cohesive Save Framework Starts
function fnTeacherAttendanceSave() {
	var emptyTeacherAttendance = {
		teacherName:"",
		teacherID: "",
		date: "",
		schedule: [{
				subjectID: "",
				subjectName: "",
				standard: "",
				section: "",
				periodNumber: "",
				attendance: ""
			}]
	};

	// Screen Specific DataModel Starts
	var dataModel = emptyTeacherAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
       if($scope.date!=null)
	dataModel.date = $scope.date; 
        if($scope.TeacherAttendanceTable!=null)
	dataModel.schedule = $scope.TeacherAttendanceTable;
      if($scope.teacherName!=null)
	dataModel.teacherName = $scope.teacherName;
	// Screen Specific DataModel Starts
	var response = fncallBackend('TeacherAttendance', parentOperation, dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
// Cohesive Save Framework Ends

function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#attendanceDate").datepicker("getDate")),
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#attendanceDate").datepicker("getDate"));
		$scope.$apply();
}	
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'TeacherAttendanceTable':
		   
			var TeacherAttendanceTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     TeacherAttendanceTable[index] = new Object();
					 TeacherAttendanceTable[index].idx=index;
					 TeacherAttendanceTable[index].checkBox=false;
					 TeacherAttendanceTable[index].subjectID=value.subjectID;
					 TeacherAttendanceTable[index].subjectName=value.subjectName;
					 TeacherAttendanceTable[index].class=value.class;
					 TeacherAttendanceTable[index].periodNumber=value.periodNumber;
					 TeacherAttendanceTable[index].attendance=value.attendance;
					}
			return TeacherAttendanceTable;
		}
	}
        
 function fnTeacherAttendancepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.teacherNamereadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
		$scope.teacherIDreadOnly = true;
		$scope.datereadOnly = true;
		$scope.attendanceReadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.teacherID = "";
		$scope.teacherName ="";
		$scope.date ="";
		$scope.schedule ={};
                $scope.TeacherAttendanceShowObject=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.teacherName = response.body.teacherName;
		$scope.teacherID = response.body.teacherID;
		$scope.date = response.body.date;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
                     
		 //Multiple View Response Starts 
		$scope.TeacherAttendanceTable = fnConvertmvw('TeacherAttendanceTable',response.body.schedule);
		$scope.TeacherAttendanceCurPage = 1
		$scope.TeacherAttendanceShowObject=$scope.fnMvwGetCurPageTable('TeacherAttendance');
		fnAttendanceButtonClrChange($scope.TeacherAttendanceShowObject);
		//Multiple View Response Ends 
            }
            if (subscreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
      }
             return true;

}

}       
        
        