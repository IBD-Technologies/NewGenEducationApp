/* 
    Author     : Munish Kumar B
	IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var noon = "";
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.mvwAddDeteleDisable = true; // Multiple View
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.dateReadOnly = true;
	$scope.attendanceReadOnly = true;
	$scope.date = "";
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
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
	//Screen Specific Scope Ends 	
	//Multiple View Starts
	$scope.attendanceForeNoonCurPage = 0;
	$scope.attendanceForeNoonTable = null;
	$scope.attendanceForeNoonShowObject = null;
	
	$scope.attendanceAfternoonCurPage = 0;
	$scope.attendanceAfterNoonTable = null;
	$scope.attendanceAfterNoonShowObject = null;
	// Multiple View Ends
	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'attendanceForeNoon') {
			if ($scope.attendanceForeNoonTable != null && $scope.attendanceForeNoonTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
			}
			
		}
		
		else if (tableName == 'attendanceAfterNoon') {
			if ($scope.attendanceAfterNoonTable != null && $scope.attendanceAfterNoonTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);
			}
			
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'attendanceForeNoon') {
			if ($scope.attendanceForeNoonTable != null && $scope.attendanceForeNoonTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
			}
		}
		else if (tableName == 'attendanceAfterNoon') {
			if ($scope.attendanceAfterNoonTable != null && $scope.attendanceAfterNoonTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'attendanceForeNoon') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					 foreNoon:[{periodNumber:1,attendance:"-"},
			                     {periodNumber:2,attendance:"-"},
				                 {periodNumber:3,attendance:"-"},
				                 {periodNumber:4,attendance:"-"}],
				};
				if ($scope.attendanceForeNoonTable == null)
					$scope.attendanceForeNoonTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;
	
			}
			
			else if (tableName == 'attendanceAfterNoon') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					afterNoon:[{periodNumber:5,attendance:"-"},
			                      {periodNumber:6,attendance:"-"},
				                  {periodNumber:7,attendance:"-"}],	
				};
				if ($scope.attendanceAfterNoonTable == null)
					$scope.attendanceAfterNoonTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'attendanceForeNoon') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;
			}
			else if (tableName == 'attendanceAfterNoon') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'attendanceForeNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
			lsvwObject.tableObject = $scope.attendanceForeNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		} 
		else if (tableName == 'attendanceAfterNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
			lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'attendanceForeNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
			lsvwObject.tableObject = $scope.attendanceForeNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
		else if (tableName == 'attendanceAfterNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
			lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'attendanceForeNoon') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.attendanceForeNoonTable);
			
		}
		else if (tableName == 'attendanceAfterNoon') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.attendanceAfterNoonTable);
			
		}
	};
	//Multiple View Scope Function ends 


$scope.fnAttendanceButtonClick = function (id)
{
	
if (!($('#'+id).hasClass("btn-success") ||$('#'+id).hasClass("btn-warning1") ||$('#'+id).hasClass("btn-danger")))
{
	//$(id).removeClass("btn");
	//$('#'+id).addClass("btn-success");
    if (noon=="F"){
	fnSetAttendance(id,'P',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'P',$scope.attendanceAfterNoonShowObject);
	}
	return;
}
if ($('#'+id).hasClass("btn-success"))
{
	//$('#'+id).removeClass("btn-success");
	//$('#'+id).addClass("btn-warning1");
	if (noon=="F"){
	fnSetAttendance(id,'L',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'L',$scope.attendanceAfterNoonShowObject);
	}
	return;
}
if ($('#'+id).hasClass("btn-warning1"))
{
	//$('#'+id).removeClass("btn-warning1");
	//$('#'+id).addClass("btn-danger");
	if (noon=="F"){
	fnSetAttendance(id,'A',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'A',$scope.attendanceAfterNoonShowObject);
	}
	return;
}
if ($('#'+id).hasClass("btn-danger"))
{
	//$('#'+id).removeClass("btn-danger");
	//$(id).addClass("btn");
	if (noon=="F"){
	fnSetAttendance(id,'-',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'-',$scope.attendanceAfterNoonShowObject);
	}
	return;
}	

}
	
});
 function fnSetAttendance(id,attendance,attendanceObject){
	 
	var ID = id.split("-");
	 
	 attendanceObject.forEach(function(value,index,array){
		if(ID[0] == value.periodNumber){
		//{
		//value.period.forEach(function(value,index,array){
		  //  if (index==ID[1])
			//{	
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
			}
			 //return;
			});
        }

// Screen Specific Default Load Starts	
$(document).ready(function () {
	MenuName = "StudentAttendance";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        window.parent.fn_hide_parentspinner();        
	fnDatePickersetDefault('AttendanceDate',fndateEventHandler);
      	fnsetDateScope();

	noon = "";
  $("#foreNoonTab").on('show.bs.tab', function (e) {
  //e.target // newly activated tab
  //e.relatedTarget // previous active tab
	// screen Specific DataModel Ends
	noon = "F";
   //fnAttendanceDefault();
  });
   $("#afterNoonTab").on('show.bs.tab', function (e) {
  //e.target // newly activated tab
  //e.relatedTarget // previous active tab
	// screen Specific DataModel Ends
	noon = "A";
   //fnAttendanceDefault();
  });
        fnStudentAttendancepostSelectBoxMaster();
});


function fnStudentAttendancepostSelectBoxMaster(){
    
  
    
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
     if (window.parent.StudentAttendanceSummarykey.studentID !=null && window.parent.StudentAttendanceSummarykey.studentID !='')
  {
      if (window.parent.StudentAttendanceSummarykey.date !=null && window.parent.StudentAttendanceSummarykey.date !='')
  {
		var studentID=window.parent.StudentAttendanceSummarykey.studentID;
                var date=window.parent.StudentAttendanceSummarykey.date;
		
		 window.parent.StudentAttendanceSummarykey.studentID =null;
                 window.parent.StudentAttendanceSummarykey.date =null;
		  fnshowSubScreen(studentID,date);
		
	}
  }
    }
function fnStudentAttendanceDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    noon='F';
	if (($scope.attendanceForeNoonTable ==null ||$scope.attendanceAfterNoonTable==null)  && parentOperation=='Create')
	{	
       var emptyStudentAttendance = {
		studentID: "",
		studentName: "",
		date: "",
				 foreNoon:[{periodNumber:1,attendance:"-"},
			                     {periodNumber:2,attendance:"-"},
				                 {periodNumber:3,attendance:"-"},
				                 {periodNumber:4,attendance:"-"}],
				      afterNoon:[{periodNumber:5,attendance:"-"},
			                      {periodNumber:6,attendance:"-"},
				                  {periodNumber:7,attendance:"-"},
				                  {periodNumber:8,attendance:"-"}]	
	};
	var dataModel = emptyStudentAttendance;
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
     if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}

			if ($scope.date == '' || $scope.date == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['date']);
				return false;
			}
	var response = fncallBackend('StudentAttendance', 'attendanceDefault', dataModel,[$scope.studentID,$scope.date],$scope);
	
	    //Multiple View Response Starts 
		$scope.attendanceForeNoonTable = fnConvertmvw('attendanceForeNoonTable',response.body.foreNoon);
		$scope.attendanceForeNoonCurPage = 1
		$scope.attendanceForeNoonShowObject=$scope.fnMvwGetCurPageTable('attendanceForeNoon');
		
		$scope.attendanceAfterNoonTable = fnConvertmvw('attendanceAfterNoonTable',response.body.afterNoon);
		$scope.attendanceAfternoonCurPage = 1
		$scope.attendanceAfterNoonShowObject=$scope.fnMvwGetCurPageTable('attendanceAfterNoon');
		//Multiple View Response Ends
                fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
	        fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);		
	    // $scope.$apply();
	 
	 }	

return true;
}
function fnAttendanceButtonClrChange(attendanceObject){
	    attendanceObject.forEach(function(value,index,array){
	     //var PeriodID = value.periodNumber; 
		//value.forEach(function(value,index,array){
         
		 switch(value.attendance){
		 case " ":
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
	
// Screen Specific Load Ends
function fnshowSubScreen(studentID,date)
{
   subScreen = true;
var emptyStudentAttendance = {
		studentID: "",
		studentName: "",
		date: "",
				 foreNoon:[{periodNumber:1,attendance:"-"},
			                     {periodNumber:2,attendance:"-"},
				                 {periodNumber:3,attendance:"-"},
				                 {periodNumber:4,attendance:"-"}],
				      afterNoon:[{periodNumber:5,attendance:"-"},
			                      {periodNumber:6,attendance:"-"},
				                  {periodNumber:7,attendance:"-"},
				                  {periodNumber:8,attendance:"-"}]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(studentID!==null||studentID!==""){
	 dataModel.studentID = studentID;
        
        if(date!==null||date!==""){
	 dataModel.date = date;
        
	// screen Specific DataModel Ends
	var response = fncallBackend('StudentAttendance', 'View', dataModel,[{entityName:"studentID",entityValue:studentID}],$scope);
	
    }
        }
	return true;
}
// Cohesive Query Framework Starts
function fnStudentAttendanceQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.date = "";
	$scope.foreNoon = "";
	$scope.afterNoon = "";
	$scope.studentIDreadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.dateReadOnly = false;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends	
	//Multiple View Starts
	$scope.attendanceForeNoonCurPage = 0;
	$scope.attendanceForeNoonTable = null;
	$scope.attendanceForeNoonShowObject = null;
	
	$scope.attendanceAfternoonCurPage = 0;
	$scope.attendanceAfterNoonTable = null;
	$scope.attendanceAfterNoonShowObject = null;
	// Multiple View Ends
	
	return true;
}
// Cohesive Query Framework Ends
//  Cohesive View Framework Starts	
function fnStudentAttendanceView() {
	var emptyStudentAttendance = {
		studentID: "",
		studentName: "",
		date: null,
				 foreNoon:[{periodNumber:1,attendance:"-"},
			                     {periodNumber:2,attendance:"-"},
				                 {periodNumber:3,attendance:"-"},
				                 {periodNumber:4,attendance:"-"}],
				      afterNoon:[{periodNumber:5,attendance:"-"},
			                      {periodNumber:6,attendance:"-"},
				                  {periodNumber:7,attendance:"-"},
				                  {periodNumber:8,attendance:"-"}]	
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null||$scope.studentID!=""){
	 dataModel.studentID = $scope.studentID;
         dataModel.studentName = $scope.studentName;
        if($scope.date!=null||$scope.date!=""){
	 dataModel.date = $scope.date; 
         
	// screen Specific DataModel Ends
	var response = fncallBackend('StudentAttendance', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope);
	
        }
    }
        return true;
}
// Cohesive Query Framework Starts
// Screen Specific Mandatory Validation Starts      
function fnStudentAttendanceMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//                       if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
			/*if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}*/
            
                     if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
            
            
            
			if ($scope.date == '' || $scope.date == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Date']);
				return false;
			}
			break;

		case 'Save':
			if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}
                        if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
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


function fnStudentAttendanceDefaultandValidate(operation) {
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


	}
	return true;
}

function fnDefaultStudentId($scope) {
	var availabilty = false;
	
	return true;
}

// Screen Specific Mandatory Validation Ends
// Cohesive Create Framework Starts
function fnStudentAttendanceCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.date = "";
	$scope.attendanceForeNoonTable=null;
	$scope.attendanceForeNoonShowObject=null;
	$scope.attendanceAfterNoonTable=null;
	$scope.attendanceAfterNoonShowObject=null;
	$scope.studentIDreadOnly = false;
        $scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.dateReadOnly = false;
	$scope.attendanceReadOnly = false;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	
	return true;
}
// Cohesive Screen create Framework Ends

// Cohesive Edit Framework Starts
function fnStudentAttendanceEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope()
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
        $scope.studentIDreadOnly = true;
        $scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = true;
	$scope.dateReadOnly = false;
	$scope.attendanceReadOnly = false;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnStudentAttendanceDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
        $scope.studentNamereadOnly = true;
	$scope.dateReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnStudentAttendanceAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
        $scope.studentNamereadOnly = true;
	$scope.dateReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnStudentAttendanceReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
        $scope.studentNamereadOnly = true;
	$scope.dateReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends

//Cohesive Back Framework Starts
function fnStudentAttendanceBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();	
	// Screen Specific Scope Starts
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	$scope.audit = {};
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.date = "";
	$scope.attendanceForeNoonTable="";
	$scope.attendanceForeNoonShowObject="";
	$scope.attendanceAfterNoonTable="";
	$scope.attendanceAfterNoonShowObject="";
	}
	$scope.studentIDreadOnly = true;
        $scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.dateReadOnly = true;
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
//Cohesive save Framework Starts
function fnStudentAttendanceSave() {
		var emptyStudentAttendance = {
		studentID: "",
		studentName: "",
		date: "",
				 foreNoon:[{periodNumber:1,attendance:"-"},
			                     {periodNumber:2,attendance:"-"},
				                 {periodNumber:3,attendance:"-"},
				                 {periodNumber:4,attendance:"-"}],
				      afterNoon:[{periodNumber:"5",attendance:"-"},
			                      {periodNumber:6,attendance:"-"},
				                  {periodNumber:7,attendance:"-"},
				                  {periodNumber:8,attendance:"-"}]	
	};
	var dataModel = emptyStudentAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific DataModel Starts
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.date!=null)
	dataModel.date = $scope.date;
        if($scope.attendanceForeNoonTable!=null)
	dataModel.foreNoon = $scope.attendanceForeNoonTable;
        if($scope.attendanceAfterNoonTable!=null)
	dataModel.afterNoon = $scope.attendanceAfterNoonTable;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentAttendance', parentOperation, dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
//Cohesive save Framework Ends

function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#AttendanceDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#AttendanceDate").datepicker("getDate"));
		$scope.$apply();
}	
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'attendanceForeNoonTable':
		   
			var attendanceForeNoonTable = new Array();
			 responseObject.forEach(fnConvert1);
			 
			 
			 function fnConvert1(value,index,array){
				     attendanceForeNoonTable[index] = new Object();
					 attendanceForeNoonTable[index].idx=index;
					 attendanceForeNoonTable[index].checkBox=false;
					 attendanceForeNoonTable[index].periodNumber=value.periodNumber;
					 attendanceForeNoonTable[index].attendance=value.attendance;
					}
			return attendanceForeNoonTable;
			break ;
			case 'attendanceAfterNoonTable':
		   
			var attendanceAfterNoonTable = new Array();
			 responseObject.forEach(fnConvert2);
			 function fnConvert2(value,index,array){
				     attendanceAfterNoonTable[index] = new Object();
					 attendanceAfterNoonTable[index].idx=index;
					 attendanceAfterNoonTable[index].checkBox=false;
					 attendanceAfterNoonTable[index].periodNumber=value.periodNumber;
					 attendanceAfterNoonTable[index].attendance=value.attendance;
					}
			return attendanceAfterNoonTable;
			break ;
		}
	}


function fnStudentAttendancepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.studentIDreadOnly = true;
                $scope.studentNamereadOnly = true;
                $scope.studentNameSearchreadOnly = true;
	        $scope.dateReadOnly = true;
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
                $scope.studentID = "";
		$scope.studentName ="";
                $scope.date ="";
		$scope.foreNoon ={};
		$scope.afterNoon ={};
                $scope.attendanceForeNoonShowObject=null;
                $scope.attendanceAfterNoonShowObject=null;
                $scope.audit = {};
		 }
                else
                {
                $scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
                $scope.date= response.body.date;
		$scope.foreNoon = response.body.foreNoon;
		$scope.afterNoon = response.body.afterNoon;
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
                $scope.attendanceForeNoonTable = fnConvertmvw('attendanceForeNoonTable',response.body.foreNoon);
		$scope.attendanceForeNoonCurPage = 1
		$scope.attendanceForeNoonShowObject =$scope.fnMvwGetCurPageTable('attendanceForeNoon');
		
		$scope.attendanceAfterNoonTable = fnConvertmvw('attendanceAfterNoonTable',response.body.afterNoon);
		$scope.attendanceAfternoonCurPage = 1
		$scope.attendanceAfterNoonShowObject=$scope.fnMvwGetCurPageTable('attendanceAfterNoon');
		//Multiple View Response Ends 
		// Screen Specific Response Scope Ends
		fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
	         fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);		     
		
                }
                //Multiple View Response Ends 
//		fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
//	        fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);	
                  if (subScreen){
                var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	        $operationScope.fnPostdetailLoad();
                subScreen = false;
            }
		return true;

}

}

