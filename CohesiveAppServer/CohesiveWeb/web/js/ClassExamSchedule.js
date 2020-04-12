//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
    //Screen specific Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
        $scope.classes=Institute.ClassMaster;
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.Hour = Institute.HourMaster;
	$scope.Minutes = Institute.MinMaster;
	$scope.examtypereadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.examHallreadOnly = true;
	//Screen Specific Scope Ends
	//single View Starts
	$scope.SubjectschedulesRecord = {    
			idx: 0,
			 subjectID:"Select option",
			//subjectName:"Select option",
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
//	$scope.ClassSection = ["Select option"]; // Std/sec
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
			subjectID:"Select option",
			//subjectName:"Select option",
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
	
});
//--------------------------------------------------------------------------------------------------------------
//Default Load Record Starts
$(document).ready(function () {
    selectBypassCount=0;
	MenuName = "ClassExamSchedule";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="ClassEntity";
	fnDatePickersetDefault('examDate',fndatePickerdateEventHandler);
	fnsetDateScope();
        selectBoxes= ['class','subject','examType','startTimeHour','startTimeMin','endTimeHour','endTimeMin','examHall'];
         fnGetSelectBoxdata(selectBoxes);//Integration changes
	
       
	
	
	
});
//Default Load Record Ends
function fnClassExamSchedulepostSelectBoxMaster(){
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0 && Institute.SubjectMaster.length>0)
      {   
      $scope.classes=Institute.ClassMaster;
      $scope.ExamMaster = Institute.ExamMaster;
      $scope.subjects = Institute.SubjectMaster;
      window.parent.fn_hide_parentspinner();
    if ((window.parent.ClassExamSchedulekey.class !=null && window.parent.ClassExamSchedulekey.class !='')&&(window.parent.ClassExamSchedulekey.exam !=null && window.parent.ClassExamSchedulekey.exam !=''))
	{
		var class1=window.parent.ClassExamSchedulekey.class;
		var exam=window.parent.ClassExamSchedulekey.exam;
		
		 window.parent.ClassExamSchedulekey.class=null;
		 window.parent.ClassExamSchedulekey.exam =null;
		
		fnshowSubScreen(class1,exam);
		
	}
       // $scope.$apply();
}
    }
}
//Summary Screen Starts
function fnshowSubScreen(class1,exam)
{
subScreen=true;    
var emptyClassExamSchedule = {
		exam: "Select option",
		class: "Select option",
		Subjectschedules: [{
			idx: 0,
			 subjectID:"Select option",
			//subjectName: "Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyClassExamSchedule;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(exam!=null && exam!="")
        {   
	dataModel.exam = exam;
        if(class1!=null && class1!="")
        {    
	dataModel.class = class1;
	//Screen Specific DataModel Ends
	var response = fncallBackend('ClassExamSchedule', 'View', dataModel,[{entityName:"class",entityValue:class1}],$scope);
	return true;
         }
    }
}

//Summary Screen Framework Ends
//Cohesive Query Framework Starts
function fnClassExamScheduleQuery() {
	
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
	$scope.examtypereadOnly = false;
	$scope.classReadonly = false;
	$scope.subjectNamereadOnly = false;
	$scope.examDatereadOnly = false;
	$scope.startTimereadOnly = false;
	$scope.endTimereadOnly = false;
	$scope.examHallreadOnly = false;
	//Screen Specific Scope Ends	
	//Single View Starts
	$scope.SubjectschedulesRecord = {    
			idx: 0,
			 subjectID:"Select option",
			//subjectName:"Select option",
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
	//$scope.ClassSection = ["Select option"]; // Std/sec
	$scope.svwAddDeteleDisable = true; //Single View
    //Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnClassExamScheduleView() {
	var emptyClassExamSchedule = {
		exam: "Select option",
		class: "Select option",
		Subjectschedules: [{
			idx: 0,
			 subjectID:"Select option",
			//subjectName: "Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyClassExamSchedule;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.exam!=null &&$scope.exam!="")
        {    
	dataModel.exam = $scope.exam;
        if($scope.class!=null && $scope.class!="")
        {   
	dataModel.class = $scope.class;
	//Screen Specific DataModel Ends
	var response = fncallBackend('ClassExamSchedule', 'View', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
        }
        }
        return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnClassExamScheduleMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam Type']);
				return false;
			}
			if ($scope.class == '' || $scope.class == null || $scope.class == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false
			}

			break;

		case 'Save':
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam Type']);
				return false;
			}
			if ($scope.class == '' || $scope.class == null || $scope.class == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
			if ($scope.SubjectschedulesTable == null || $scope.SubjectschedulesTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam schedule']);
				return false;
			}
			for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
				if ($scope.SubjectschedulesTable[i].subjectID == '' || $scope.SubjectschedulesTable[i].subjectID == null || $scope.SubjectschedulesTable[i].subjectID == "Select option") {
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
function fnClassExamScheduleDefaultandValidate(operation)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
switch(operation)
   {
     case 'View':   
                        
                      return true;   
			   
  			  break;
	 
	 case 'Save':   
                      return true;   
			   
  			  break;
	 
   
   }		
 return true; 		
}
//Screen Specific Default Validation Ends
//Cohesive Create framework Starts
function fnClassExamScheduleCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Screen Specific Scope Starts
	$scope.exam = "Select option";
	$scope.class = "Select option";
	//$scope.section = "Select option";
	$scope.examtypereadOnly = false;
	$scope.classReadonly = false;
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
			 subjectID:"Select option",
			//subjectName:"Select option",
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
function fnClassExamScheduleEdit() {
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
	$scope.examtypereadOnly = true;
	$scope.classReadonly = true;
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
function fnClassExamScheduleDelete() {
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
	$scope.classReadonly = true;
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
function fnClassExamScheduleAuth() {

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
	$scope.examtypereadOnly = true;
	$scope.classReadonly = true;
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
function fnClassExamScheduleReject() {

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
	$scope.classReadonly = true;
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
function fnClassExamScheduleBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
	    $scope.audit = {};
	    $scope.exam = "Select option";
	    $scope.class = "Select option";
	    $scope.SubjectschedulesShowObject = null;
	    $scope.SubjectschedulesTable = null;
	}
	$scope.examtypereadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.examDatereadOnly = true;
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
function fnClassExamScheduleSave() {
	var emptyClassExamSchedule = {
		exam: "Select option",
		class: "Select option",
		Subjectschedules: [{
			idx: 0,
			 subjectID:"Select option",
			//subjectName: "Select option",
			date: "",
			startTime:{hour:"Hour",min:"Min"},
			endTime:{hour:"Hour",min:"Min"},
			hall: "Select option"
		}]
	};

    //screen Specifi DataModel Starts
	var dataModel = emptyClassExamSchedule;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.exam!=null)
	dataModel.exam = $scope.exam;
        if($scope.class!=null)
	dataModel.class = $scope.class;
        if($scope.SubjectschedulesTable!=null)
	dataModel.Subjectschedules = $scope.SubjectschedulesTable;
	dataModel.audit = $scope.audit;
	//Screen Specific DataModel Ends
	var response = fncallBackend('ClassExamSchedule', parentOperation, dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
	return true;
}
//Cohesive Save Framework Ends
//Date Function Starts
function fndatePickerdateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.SubjectschedulesTable !=null){
	//for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
            
	$scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex].date = $.datepicker.formatDate('dd-mm-yy', $("#examDate").datepicker("getDate"));
	//}
	$scope.$apply();
}
}
function fnsetDateScopeforTable()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
if($scope.SubjectschedulesTable !=null){
//for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
	$scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex].date = $.datepicker.formatDate('dd-mm-yy', $("#examDate").datepicker("getDate"));
		
//}	
}
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
if($scope.SubjectschedulesTable !=null){
//for (i = 0; i < $scope.SubjectschedulesTable.length; i++) {
	$scope.SubjectschedulesTable[$scope.SubjectschedulesCurIndex].date = $.datepicker.formatDate('dd-mm-yy', $($("#examDate")).datepicker("getDate"));
		
//}
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
                                        //SubjectschedulesTable[index].subjectID=value.subjectID;
					//SubjectschedulesTable[index].subjectName=value.subjectName;
					 SubjectschedulesTable[index].date=value.date;
					 SubjectschedulesTable[index].hall=value.hall;
                                         SubjectschedulesTable[index].startTime=value.startTime;
                                         SubjectschedulesTable[index].endTime=value.endTime;
					 /*SubjectschedulesTable[index].startTime=new Object();
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

function fnClassExamSchedulepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.examtypereadOnly = true;
	         $scope.classReadonly = true; 
	         $scope.subjectNamereadOnly = true;
	         $scope.examDatereadOnly = true;
	         $scope.startTimereadOnly = true;
	         $scope.endTimereadOnly = true;
	         $scope.examHallreadOnly = true;
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
                $scope.class = "";
                $scope.exam="";
                $scope.SubjectschedulesShowObject=null;
                $scope.Subjectschedules=null;
                $scope.audit = {};
		 }
                else
                {
                 $scope.audit=response.audit;
               //Screen Specific Response Scope Starts
		$scope.exam = response.body.exam;
		$scope.class = response.body.class;
                
                
                if(response.body.Subjectschedules!==null){
                
	           $scope.SubjectschedulesTable = fnConvertmvw('SubjectschedulesTable',response.body.Subjectschedules);
		
                }else{
                    
                    $scope.SubjectschedulesTable =null;
                }
                
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
            subScreen=false;
	 }
		return true;

}

}




