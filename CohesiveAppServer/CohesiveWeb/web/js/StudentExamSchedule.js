/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
    //Screen specific Scope Starts
	$scope.exam = "Select option";
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.classes = Institute.ClassMaster;
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.Hour = Institute.HourMaster;
	$scope.Minutes = Institute.MinMaster;
	$scope.examtypereadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
        $( "#examDate" ).datepicker( "option", "disabled", true );
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.examHallreadOnly = true;
	//Screen Specific Scope Ends
	//single View Starts
	$scope.SubjectschedulesRecord = {    
			idx: 0,
			subjectID: "",
			subjectName:"Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		};
	
	$scope.SubjectschedulesTable = null;
	$scope.SubjectschedulesCurIndex = 0;
	$scope.SubjectschedulesShowObject = false;
    //single View Ends
	//Generic field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.MakerRemarksReadonly = true;
	$scope.ClassSection = ["Select option"]; // Std/sec
	$scope.svwAddDeteleDisable = true; //Single View
	$scope.operation = '';
    //Generic Field Ends
	
	//Scope Levle Single View functions starts 
	$scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'examSchedule') {
			if ($scope.SubjectschedulesTable != null && $scope.SubjectschedulesTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.SubjectschedulesCurIndex;
				lsvwObject.tableObject = $scope.SubjectschedulesTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.SubjectschedulesCurIndex = lsvwObject.curIndex;
				$scope.SubjectschedulesTable = lsvwObject.tableObject;
				$scope.SubjectschedulesRecord = $scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex];
				//var SelectedArray= ['subject','startTimeHour','startTimeMin','endTimeHour','endTimeMin','examHall'];
				//fnSelectRefresh(SelectedArray);
			}
		}


	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'examSchedule') {
			if ($scope.SubjectschedulesTable != null && $scope.SubjectschedulesTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.SubjectschedulesCurIndex;
				lsvwObject.tableObject = $scope.SubjectschedulesTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.SubjectschedulesCurIndex = lsvwObject.curIndex;
				$scope.SubjectschedulesTable = lsvwObject.tableObject;
				$scope.SubjectschedulesRecord = $scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex];
				//var SelectedArray= ['subject','startTimeHour','startTimeMin','endTimeHour','endTimeMin','examHall'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};


	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'examSchedule') {
				emptyTableRec = {    
			idx: 0,
			subjectID: "",
			subjectName:"Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		};
				if ($scope.SubjectschedulesTable == null)
					$scope.SubjectschedulesTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.SubjectschedulesShowObject;
				lsvwObject.curIndex = $scope.SubjectschedulesCurIndex;
				lsvwObject.tableObject = $scope.SubjectschedulesTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.SubjectschedulesShowObject = lsvwObject.tableShow;
				$scope.SubjectschedulesCurIndex = lsvwObject.curIndex;
				$scope.SubjectschedulesTable = lsvwObject.tableObject;
				$scope.SubjectschedulesRecord = $scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex];
				//var SelectedArray= ['subject','startTimeHour','startTimeMin','endTimeHour','endTimeMin','examHall'];
				//fnSelectRefresh(SelectedArray);//select box change
				fnsetDateScopeforTable();

			}
			
		}

	};
	$scope.fnSvwDeleteRow = 
	function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'examSchedule') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.SubjectschedulesShowObject;
				lsvwObject.curIndex = $scope.SubjectschedulesCurIndex;
				lsvwObject.tableObject = $scope.SubjectschedulesTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.SubjectschedulesShowObject = lsvwObject.tableShow;
				$scope.SubjectschedulesCurIndex = lsvwObject.curIndex;
				$scope.SubjectschedulesTable = lsvwObject.tableObject;
				$scope.SubjectschedulesRecord = $scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex];
				//var SelectedArray= ['subject','startTimeHour','startTimeMin','endTimeHour','endTimeMin','examHall'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'examSchedule') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.SubjectschedulesShowObject;
			lsvwObject.curIndex = $scope.SubjectschedulesCurIndex;
			lsvwObject.tableObject = $scope.SubjectschedulesTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'examSchedule') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.SubjectschedulesShowObject;
			lsvwObject.curIndex = $scope.SubjectschedulesCurIndex;
			lsvwObject.tableObject = $scope.SubjectschedulesTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		}
	};
	//Scope Level Single View functions Ends 	
	
	
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
//Default Load Record Starts
$(document).ready(function () {
	MenuName = "StudentExamSchedule";
        window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "Student";
	fnDatePickersetDefault('examDate',fndatePickerdateEventHandler);
	fnsetDateScope();
	selectBoxes= ['subject','examType','startTimeHour','startTimeMin','endTimeHour','endTimeMin','examHall'];
        fnGetSelectBoxdata(selectBoxes);//Integration changes
	
	
	
});
//Default Load Record Ends
function fnStudentExamSchedulepostSelectBoxMaster()
{
   
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0&& Institute.SubjectMaster.length>0)
      {    
        $scope.classes=Institute.ClassMaster;
	$scope.ExamMaster = Institute.ExamMaster;
        $scope.subjects = Institute.SubjectMaster;
	window.parent.fn_hide_parentspinner();

	if ((window.parent.StudentExamSchedulekey.studentID !=null && window.parent.StudentExamSchedulekey.studentID !='')&&(window.parent.StudentExamSchedulekey.exam !=null && window.parent.StudentExamSchedulekey.exam !=''))
	{
		var studentID=window.parent.StudentExamSchedulekey.studentID;
		var exam=window.parent.StudentExamSchedulekey.exam;
		
		 window.parent.StudentExamSchedulekey.studentID =null;
		 window.parent.StudentExamSchedulekey.exam =null;
		 fnshowSubScreen(studentID,exam);
		
	}
 //         $scope.$apply();
}
}
//Summary Screen Starts
function fnshowSubScreen(studentID,exam)
{
    subScreen = true;
var emptyStudentExamSchedule = {
		exam: "Select option",
	        studentName:"",
		studentID:"",
		Subjectschedules: [{
			idx: 0,
			subjectID: "",
			subjectName: "Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyStudentExamSchedule;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentExamSchedule', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope);
	return true;
}

//Summary Screen Framework Ends
//Cohesive Query Framework Starts
function fnStudentExamScheduleQuery() {
	
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.exam = "Select option";
        $scope.studentID = "";
	$scope.studentName = "";
	$scope.examtypereadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.examDatereadOnly = false;
        $( "#examDate" ).datepicker( "option", "disabled", false );
	$scope.startTimereadOnly = false;
	$scope.endTimereadOnly = false;
	$scope.examHallreadOnly = false;
	//Screen Specific Scope Ends	
	//Single View Starts
	$scope.SubjectschedulesRecord = {    
			idx: 0,
			subjectID: "",
			subjectName:"Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		};
	$scope.SubjectschedulesTable = null;
	$scope.SubjectschedulesCurIndex = 0;
	$scope.SubjectschedulesShowObject = false;
    //Single View Ends	
	//Generic Field Starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.svwAddDeteleDisable = true; //Single View
    //Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnStudentExamScheduleView() {
	var emptyStudentExamSchedule = {
		exam: "Select option",
		studentID: "",
		studentName: "",
		Subjectschedules: [{
			idx: 0,
			subjectID: "",
			subjectName: "Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyStudentExamSchedule;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//	  if($scope.studentID!=null && $scope.studentID!="")
//          {   
	  dataModel.studentID = $scope.studentID;
          dataModel.studentName = $scope.studentName;
          
          if($scope.exam!=null && $scope.exam!="")
          {   
	  dataModel.exam = $scope.exam;
          
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentExamSchedule', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope);
	
        }
        
//    }
            return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnStudentExamScheduleMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//                     if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
		   /* if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false
			}*/
                        if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam Type']);
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
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam Type']);
				return false;
			}
			
			if ($scope.SubjectschedulesTable == null || $scope.SubjectschedulesTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam schedule']);
				return false;
			}
			for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
				if ($scope.SubjectschedulesTable[i].subjectName == '' || $scope.SubjectschedulesTable[i].subjectName == null || $scope.SubjectschedulesTable[i].subjectName == "Select option") {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Subject in Exam Record']);
					return false;
				}
				if ($scope.SubjectschedulesTable[i].date == '' || $scope.SubjectschedulesTable[i].date == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Date in Exam Record ' + 'record ' + (i+1)]);
					return false;
				}
				
				if ($scope.SubjectschedulesTable[i].startTime.hour == '' || $scope.SubjectschedulesTable[i].startTime.hour == null || $scope.SubjectschedulesTable[i].startTime.hour == "Hour") {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Start Time Hour in Exam Record ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.SubjectschedulesTable[i].startTime.min == '' || $scope.SubjectschedulesTable[i].startTime.min == null || $scope.SubjectschedulesTable[i].startTime.min == "Min") {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Start Time Minute in Exam Record ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.SubjectschedulesTable[i].endTime.hour == '' || $scope.SubjectschedulesTable[i].endTime.hour == null || $scope.SubjectschedulesTable[i].endTime.hour == "Hour") {
					fn_Show_Exception_With_Param('FE-VAL-001', ['End Time Hour in Exam Record ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.SubjectschedulesTable[i].endTime.min == '' || $scope.SubjectschedulesTable[i].endTime.min == null || $scope.SubjectschedulesTable[i].endTime.min == "Min") {
					fn_Show_Exception_With_Param('FE-VAL-001', ['End Time Minute in Exam Record ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.SubjectschedulesTable[i].hall == '' || $scope.SubjectschedulesTable[i].hall == null || $scope.SubjectschedulesTable[i].hall == "Select option") {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Hall in Exam Record ' + 'record ' + (i+1)]);
					return false;
				}
			}

			break;


	}
	return true;
}
//screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnStudentExamScheduleDefaultandValidate(operation) {
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
//Screen Specific Default Validation Ends
//Cohesive Create framework Starts
function fnStudentExamScheduleCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Screen Specific Scope Starts
	$scope.exam = "Select option";
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.examtypereadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.examDatereadOnly = false;
	$scope.startTimereadOnly = false;
	$scope.endTimereadOnly = false;
	$scope.examHallreadOnly = false;
	$scope.classes = Institute.ClassMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.Hour = Institute.HourMaster;
	$scope.Minutes = Institute.MinMaster;
	$scope.svwAddDeteleDisable = false; // single view
    //Screen Specific Scope Ends
    //Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//Generic Field Ends
	//Single View Starts
	$scope.SubjectschedulesRecord = {    
			idx: 0,
			subjectID: "",
			subjectName:"Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		};
	$scope.SubjectschedulesTable = null;
	$scope.SubjectschedulesCurIndex = 0;
	$scope.SubjectschedulesShowObject = false;
    //Single View Ends	
	return true;
}
//Cohesive Create framework Ends
//Cohesive Edit framework Starts
function fnStudentExamScheduleEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.svwAddDeteleDisable = false; // single View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.examtypereadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.examDatereadOnly = false;
	$scope.startTimereadOnly = false;
	$scope.endTimereadOnly = false;
	$scope.examHallreadOnly = false;
    //Screen Specific Scope Ends	
	return true;
}
//Cohesive Edit framework Ends
//Cohesive Delete framework Starts
function fnStudentExamScheduleDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
    $scope.operation = 'Deletion';
	//Generic Field Ends
    //screen Specific Scope Starts
	$scope.examtypereadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.examHallreadOnly = true;
    //Screen Specific Scope Ends
	return true;
}
//Cohesive Delete framework Ends
//Cohesive AuthoriZation framework Starts
function fnStudentExamScheduleAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
    //Generic Field Ends
	//Screen Specific Scope Starts
	$$scope.examtypereadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.examHallreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive AuthoriZation framework Ends
//Cohesive Reject framework Starts
function fnStudentExamScheduleReject() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Screen Specific Scope Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	//Screen Specific Scope Ends
    //Generic Field Starts
    $scope.examtypereadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.examHallreadOnly = true;
    //Generic Field Ends	
	return true;
}
//Cohesive Reject framework Ends
//Cohesive Back framework Starts
function fnStudentExamScheduleBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
	    $scope.exam = "Select option";
	    $scope.studentID = "";
	    $scope.studentName = "";
	    $scope.SubjectschedulesShowObject = null;
	    $scope.SubjectschedulesTable = null;
	}
	$scope.examtypereadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
        $( "#examDate" ).datepicker( "option", "disabled", true );
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	
	$scope.examHallreadOnly = true;
	
    //Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
    //Generic Field Ends
}
//Cohesive Reject framework Ends
function fnStudentExamScheduleSave() {
	var emptyStudentExamSchedule = {
		exam: "Select option",
		studentID: "",
		studen: "",
		Subjectschedules: [{
			idx: 0,
			subjectID: "",
			subjectName: "Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		}]
	};

    //screen Specifi DataModel Starts
	var dataModel = emptyStudentExamSchedule;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.exam!=null)
	dataModel.exam = $scope.exam;
       if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
       if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
       if($scope.SubjectschedulesTable!=null)
	dataModel.Subjectschedules = $scope.SubjectschedulesTable;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentExamSchedule', parentOperation, dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope);
	if (response.header.status == 'success') {
		//Screen Specific Scope Starts
    $scope.examtypereadOnly = true;
    $scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
        $( "#examDate" ).datepicker( "option", "disabled", true );
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.examHallreadOnly = true;
		//Screen Specific Scope Starts
		//Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.operation = '';
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.audit = response.body.audit;
		$scope.svwAddDeteleDisable = true; // single View
        //Generic Field Ends
		//Screen Specific Response Scope Starts
		$scope.exam = response.body.exam;
		$scope.studentID = response.body.studentID;
		$scope.studentName = response.body.studentName;
		$scope.SubjectschedulesTable = fnConvertmvw('SubjectschedulesTable',response.body.Subjectschedules);
		$scope.SubjectschedulesCurIndex = 0;
		if ($scope.SubjectschedulesTable != null) {
			$scope.SubjectschedulesRecord = $scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex];
			$scope.SubjectschedulesShowObject = true;
		}

        //Screen Specific Response Scope Ends
		return true;

	} else {
		return false;
	}
	return true;
}
//Cohesive Save Framework Ends
//Date Function Starts
function fndatePickerdateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.SubjectschedulesTable !=null){
	for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
	$scope.SubjectschedulesTable[i].date = $.datepicker.formatDate('dd-mm-yy', $("#examDate").datepicker("getDate"));
	}
	$scope.$apply();
}
}
function fnsetDateScopeforTable()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
if($scope.SubjectschedulesTable !=null){
for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
	$scope.SubjectschedulesTable[i].date = $.datepicker.formatDate('dd-mm-yy', $("#examDate").datepicker("getDate"));
		
}	
}
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
if($scope.SubjectschedulesTable !=null){
for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
	$scope.SubjectschedulesTable[i].date = $.datepicker.formatDate('dd-mm-yy', $("#examDate").datepicker("getDate"));
		
}
$scope.$apply();	
}
}

//Date Function Ends

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'SubjectschedulesTable':
		var SubjectschedulesTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     SubjectschedulesTable[index] = new Object();
					 SubjectschedulesTable[index].idx=index;
					 SubjectschedulesTable[index].checkBox=false;
					   
                                         if(value.subjectID=="") 
                                         SubjectschedulesTable[index].subjectID="Select option";
                                         else
                                         SubjectschedulesTable[index].subjectID=value.subjectID;
                                      
//					 SubjectschedulesTable[index].subjectName=value.subjectName;
					 SubjectschedulesTable[index].date=value.date;
					 SubjectschedulesTable[index].hall=value.hall;
                                         SubjectschedulesTable[index].startTime=value.startTime;
                                         SubjectschedulesTable[index].endTime=value.endTime;
				
					/* SubjectschedulesTable[index].startTime=new Object();
					 SubjectschedulesTable[index].endTime=new Object();
					 SubjectschedulesTable[index].startTime.hour=value.startTime.hour;
					 SubjectschedulesTable[index].startTime.min=value.startTime.min;
					 SubjectschedulesTable[index].endTime.hour=value.endTime.hour;
					 SubjectschedulesTable[index].endTime.min=value.endTime.min;*/
					}
			return SubjectschedulesTable;
			break;
			
	}
}


function fnStudentExamSchedulepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.classReadonly = true;
		$scope.dobreadOnly = true;
		$scope.profileImgPathNamereadOnly = true;
		$scope.bloodreadOnly = true;
		$scope.notereadOnly = true;
		$scope.contactpersonreadOnly = true;
		$scope.relationshipreadOnly = true;
		$scope.occupationreadOnly = true;
		$scope.cpEmailidreadOnly = true;
		$scope.cpImgPathreadOnly = true;
		$scope.medicalDetailreadonly = true;
		$scope.cpContactnumberreadOnly = true;
		$scope.streetreadOnly = true;
		$scope.doornumberreadOnly = true;
		$scope.pincodereadOnly = true;
		$scope.cityreadOnly = true;
		$scope.memberNamereadOnly = true;
		$scope.memberRelationshipreadOnly = true;
		$scope.memberOccupationreadOnly = true;
		$scope.memberEmailIDreadOnly = true;
		$scope.memberContactNoreadOnly = true;
                $scope.examtypereadOnly = true;
                $scope.studentNamereadOnly = true;
                $scope.studentIDreadOnly = true;
                $scope.subjectNamereadOnly = true;
                $scope.examDatereadOnly = true;
                $( "#examDate" ).datepicker( "option", "disabled", true );
                $scope.startTimereadOnly = true;
                $scope.endTimereadOnly = true;
                $scope.examHallreadOnly = true;
		//Screen Specific Scope Ends
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.svwAddDeteleDisable = true; // single View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.exam = "";
		$scope.studentID ="";
                $scope.studentName ="";
		$scope.Subjectschedules ={};
                $scope.SubjectschedulesShowObject=null;
                $scope.audit = {};
		 }
                else
                {
                $scope.exam = response.body.exam;
		$scope.studentID = response.body.studentID;
		$scope.studentName = response.body.studentName;
		$scope.SubjectschedulesTable = fnConvertmvw('SubjectschedulesTable',response.body.Subjectschedules);
		$scope.SubjectschedulesCurIndex = 0;
		if ($scope.SubjectschedulesTable != null) {
			$scope.SubjectschedulesRecord = $scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex];
			$scope.SubjectschedulesShowObject = true;
		}
            }
		
         if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
	 }
           
         
         return true;

}

}



