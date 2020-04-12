/* 	
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Screen Specific Scope Starts								 
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.class = "Select option";
	$scope.exam = "Select option";
	$scope.total = "";
	$scope.rank = "";
	$scope.marks = "";
	$scope.gradeDescription = "";
	$scope.Class = Institute.StandardMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.studentNamereadOnly = true;
	$scope.studentNameSeachreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.standardreadOnly = true;
	$scope.sectionreadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.teacherFeedbackReadOnly = true;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	//Screen Specific Scope Ends
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.markReadOnly = true;
	$scope.operation = '';
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	  $scope.OperationScopes=OperationScopes;
     $scope.searchShow = false;
	$scope.ClassSection = null; // Std/sec
		$scope.mvwAddDeteleDisable = true; //Multiple View
	//Generic Field Ends
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
	
	
	
	// Multiple View Starts
	$scope.studentProgressCardCurPage = 0;
	$scope.StudentProgressCardRoleTable = null;
	$scope.StudentProgressCardShowObject = null;
	// Multiple View Ends

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'studentProgressCard') {
			if ($scope.StudentProgressCardRoleTable != null && $scope.StudentProgressCardRoleTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.studentProgressCardCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardRoleTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.studentProgressCardCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardRoleTable = lsvwObject.tableObject;
				$scope.StudentProgressCardShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'studentProgressCard') {
			if ($scope.StudentProgressCardRoleTable != null && $scope.StudentProgressCardRoleTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.studentProgressCardCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardRoleTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.studentProgressCardCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardRoleTable = lsvwObject.tableObject;
				$scope.StudentProgressCardShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentProgressCard') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					subjectID: "",
				    subjectName: "",
				    grade: "",
				    mark: "",
				    teacherFeedback: ""
				};
				if ($scope.StudentProgressCardRoleTable == null)
					$scope.StudentProgressCardRoleTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.studentProgressCardCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardRoleTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.studentProgressCardCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardRoleTable = lsvwObject.tableObject;
				$scope.StudentProgressCardShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentProgressCard') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.studentProgressCardCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardRoleTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.studentProgressCardCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardRoleTable = lsvwObject.tableObject;
				$scope.StudentProgressCardShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'studentProgressCard') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.studentProgressCardCurPage;
			lsvwObject.tableObject = $scope.StudentProgressCardRoleTable;
			lsvwObject.screenShowObject = $scope.StudentProgressCardShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'studentProgressCard') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.studentProgressCardCurPage;
			lsvwObject.tableObject = $scope.StudentProgressCardRoleTable;
			lsvwObject.screenShowObject = $scope.StudentProgressCardShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'studentProgressCard') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentProgressCardRoleTable);

		}
	};
	//Multiple View Scope Function ends	
	

});

// Default Load Record Starts
$(document).ready(function () {
	MenuName = "StudentProgressCard";
        selectBypassCount=0;
	window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        selectBoxes= ['examType','subject'];
        fnGetSelectBoxdata(selectBoxes);      
});
function fnStudentProgressCardpostSelectBoxMaster()
{
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       if(Institute.SubjectMaster.length>0 && Institute.ExamMaster.length>0){
           $scope.subjects = Institute.SubjectMaster;
	   $scope.ExamMaster = Institute.ExamMaster; 
           window.parent.fn_hide_parentspinner();  
     if ((window.parent.StudentProgressCardkey.studentID !=null && window.parent.StudentProgressCardkey.studentID !='') && (window.parent.StudentProgressCardkey.exam !=null && window.parent.StudentProgressCardkey.exam !=''))
	{
		var studentID=window.parent.StudentProgressCardkey.studentID;
		var exam=window.parent.StudentProgressCardkey.exam;
		
		 window.parent.StudentProgressCardkey.studentID =null;
                  window.parent.StudentProgressCardkey.exam =null;
		
		fnshowSubScreen(studentID,exam);
		
	}
    
//    var emptyStudentProgressCard = {
//		studentName: "",
//		studentID: "",
//		standard: "Select option",
//		section: "Select option",
//		exam: "Select option",
//		total: "",
//		rank: "",
//		marks: [{
//				subjectID: "",
//				subjectName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				subjectID: "",
//				subjectName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				subjectID: "",
//				subjectName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				subjectID: "",
//				subjectName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			    {
//				subjectID: "",
//				subjectName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				subjectID: "",
//				subjectName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			}
//		],
//		gradeDescription: [{
//			grade: "",
//			description: ""
//		}, {
//			grade: "",
//			description: ""
//		}, {
//			grade: "",
//			description: ""
//		}, {
//			grade: "",
//			description: ""
//		}, {
//			grade: "",
//			description: ""
//		}, {
//			grade: "",
//			description: ""
//		}]
//	};
			
//       var dataModel = emptyStudentProgressCard;
//	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//        if($scope.studentID!=null && $scope.studentID!="") {
//	dataModel.studentID = $scope.studentID;
//	var response = fncallBackend('StudentProgressCard', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
//}
 $scope.$apply();
}
    }
}
function fnshowSubScreen(studentID,exam)
{
    subScreen= true;
var emptyStudentProgressCard = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank: "",
		marks: [{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		],
		gradeDescription: [{
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyStudentProgressCard;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(studentID!==null&&studentID!==null){
            
          if(exam!==null&&exam!==null){  
            
   	   dataModel.studentID = studentID;
           dataModel.exam = exam;
//	   var response = fncallBackend('StudentProgressCard', 'View',[{entityName:"studentID",entityValue:studentID}],dataModel, $scope);
           var response = fncallBackend('StudentProgressCard', 'View',dataModel,[{entityName:"studentID",entityValue:studentID}], $scope);

          }
        }
        return true;
}

function fnStudentProgressCardDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (($scope.StudentProgressCardRoleTable ==null)  && parentOperation=='Create')
	{	
	var emptyStudentProgressCard = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank:"",
		marks: [{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		],
		gradeDescription: [{
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyStudentProgressCard;
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['StudentName']);
				return false;
			}
	var response = fncallBackend('StudentProgressCard', 'progressCardDefault', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope);
		$scope.mvwAddDeteleDisable = true; //Multiple View
	   //Multiple View Response Starts 
		$scope.StudentProgressCardRoleTable = fnConvertmvw('StudentProgressCardRoleTable',response.body.marks);
		$scope.studentProgressCardCurPage = 1;
		$scope.StudentProgressCardShowObject = $scope.fnMvwGetCurPageTable('studentProgressCard');
		//Multiple View Response Ends 	
	    // $scope.$apply();
	 
	 }	

return true;
}
//Default Load Record Ends
//Cohesive Query Framework Starts
	function fnStudentProgressCardQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.exam = "Select option";
	$scope.rank = "";
	$scope.total = "";
	$scope.marks = "";
	$scope.studentNamereadOnly = false;
        $scope.studentNameSeachreadOnly = false;
	$scope.studentIDReadOnly = false;
	$scope.examtypereadOnly = false;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	$scope.markReadOnly = true;
	$scope.teacherFeedbackReadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
    $scope.audit = {};
	//Generic Field Ends

	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnStudentProgressCardView() {
	var emptyStudentProgressCard = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank: "",
		marks: [{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		],
		gradeDescription: [{
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}, {
			grade: "",
			description: ""
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyStudentProgressCard;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//        if( $scope.studentID!=null&&$scope.studentID!=''){
        
        if( $scope.exam!=null&&$scope.exam!=''){
	  dataModel.exam = $scope.exam;
    	  dataModel.studentID = $scope.studentID;
          dataModel.studentName = $scope.studentName;
         
	   var response = fncallBackend('StudentProgressCard', 'View',dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
         }
//        }
         return true;
}
//Cohesive View Framework Starts
//Screen Specific Mandatory  Validation Starts
function fnStudentProgressCardMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
//                    if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
//			if ($scope.studentName == '' || $scope.studentName == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
//				return false;
//			}

                       if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
			if ($scope.exam == '' || $scope.exam == null|| $scope.exam == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['exam']);
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
				fn_Show_Exception_With_Param('FE-VAL-001', ['exam']);
				return false;
			}

			break;


	}
	return true;
}
//Screen Specific Mandatory  Validation Ends

function fnStudentProgressCardDefaultandValidate(operation) {
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
//Cohesive Create Framework Starts 
function fnStudentProgressCardCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.exam = "Select option";
	$scope.total = "";
	$scope.rank = "";
	$scope.marks = "";
	$scope.subjects = Institute.SubjectMaster;
	$scope.studentNamereadOnly = false;
        $scope.studentNameSeachreadOnly = false;
	$scope.studentIDReadOnly = false;
	$scope.rankreadOnly = false;
	$scope.totalreadOnly = false;
	$scope.examtypereadOnly = false;
	$scope.markReadOnly = false;
	$scope.teacherFeedbackReadOnly = false;
	$scope.gradeReadOnly = true;
	//Screen Speciic Scope Ends
    //Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
    $scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
		$scope.mvwAddDeteleDisable = true; //Multiple View
	//Generic Field Ends
		$scope.studentProgressCardCurPage = 0;
	$scope.StudentProgressCardRoleTable = null;
	$scope.StudentProgressCardShowObject = null;
	
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnStudentProgressCardEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts  
    $scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSeachreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	$scope.examtypereadOnly = false;
	$scope.markReadOnly = false;
	$scope.gradeReadOnly = true;
	$scope.teacherFeedbackReadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends

//Cohesive Delete Framework Starts
function fnStudentProgressCardDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
    //Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentIDReadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.markReadOnly = true;
	$scope.teacherFeedbackReadOnly = true;
	$scope.gradeReadOnly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
    //Screen Specific Scope Ends	
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorization Framework Starts
function fnStudentProgressCardAuth() {
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
	$scope.studentIDReadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	$scope.examtypereadOnly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
	//Screen Specific Scope Ends
	return true;
}



function fnStudentProgressCardEnroll() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
        enroll=true;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
    //Generic Field Ends	
	//Screen Specific Scope Starts
	$scope.studentIDReadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	$scope.examtypereadOnly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
	//Screen Specific Scope Ends
	return true;
}






//Cohesive Authorization Framework Ends
//Cohesive Reject Framework Starts
function fnStudentProgressCardReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentIDReadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	$scope.examtypereadOnly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnStudentProgressCardBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.studentName= "";
		$scope.studentID= "";
		$scope.exam= "Select option";
		$scope.total= "";
		$scope.rank= "";
		$scope.marks= "";
	}
	    $scope.studentNamereadOnly = true;
                $scope.studentNameSeachreadOnly = true;
		$scope.studentIDReadOnly = true;
		$scope.totalReadOnly = true;
		$scope.rankReadOnly = true;
		$scope.examtypereadOnly = true;
		$scope.markReadOnly = true;
		$scope.teacherFeedbackReadOnly = true;
		$scope.gradeReadOnly = true;
		//Screen Specific Scope Ends
		// Generic Field Starts
                $scope.operation = '';
		$scope.mastershow = true;
    	        $scope.detailshow = false;
                 $scope.auditshow = false;
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
			$scope.mvwAddDeteleDisable = true; //Multiple View
	    //Generic Field Ends
}
//Cohesive Back Framework Ends
//Cohesive Back Framework Starts
function fnStudentProgressCardSave() {
	var emptyStudentProgressCard = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank: "",
		marks: [{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			},
			{
				subjectID: "",
				subjectName: "",
				grade: "",
				mark: "",
				teacherFeedback: ""
			}
		]
	};

	var dataModel = emptyStudentProgressCard;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.teacherID =User.Id;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
         if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.exam!=null)
	dataModel.exam = $scope.exam;
        if($scope.total!=null)
	dataModel.total = $scope.total;
        if($scope.rank!=null)
	dataModel.rank = $scope.rank;
        if($scope.marks!=null)
	dataModel.marks = $scope.marks;
	dataModel.gradeDescription = $scope.gradeDescription;
	var response = fncallBackend('StudentProgressCard', parentOperation, dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	if (response.header.status == 'success') {
		//Screen Specific Scope Starts
		$scope.studentNamereadOnly = true;
		$scope.studentIDReadOnly = true;
		$scope.totalReadOnly = true;
		$scope.rankReadOnly = true;
		$scope.examtypereadOnly = true;
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.markReadOnly = true;
		$scope.teacherFeedbackReadOnly = true;
		$scope.gradeReadOnly = true;
		//Screen Specific Scope Ends
		//Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.operation = '';
		$scope.audit = response.body.audit;
		//Generic Field Ends

		return true;

	} else {
		return false;
	}
	return true;
}

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'StudentProgressCardRoleTable':
		   
			var StudentProgressCardRoleTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     StudentProgressCardRoleTable[index] = new Object();
					 StudentProgressCardRoleTable[index].idx=index;
					 StudentProgressCardRoleTable[index].checkBox=false;
					 StudentProgressCardRoleTable[index].subjectID=value.subjectID;
					 StudentProgressCardRoleTable[index].subjectName=value.subjectName;
					 StudentProgressCardRoleTable[index].grade=value.grade;
					 StudentProgressCardRoleTable[index].mark=value.mark;
					 StudentProgressCardRoleTable[index].teacherFeedback=value.teacherFeedback;
					}
			return StudentProgressCardRoleTable;
		}
	}
function fnStudentProgressCardpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

   if(enroll){
        
     parentOperation="Auth";
    
    }

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDReadOnly = true;
		$scope.totalReadOnly = true;
		$scope.rankReadOnly = true;
		$scope.examtypereadOnly = true;
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.markReadOnly = true;
		$scope.teacherFeedbackReadOnly = true;
		$scope.gradeReadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.studentID ="";
                $scope.exam ="";
                $scope.studentName ="";
                $scope.total ="";
                $scope.rank ="";
                $scope.marks ={};
                $scope.parentComment ="";
                $scope.StudentProgressCardShowObject =null;
                $scope.audit = {};
		 }
                else
                {
              		//Screen Specific Response scope Starts
		$scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
		$scope.total = response.body.total;
		$scope.rank = response.body.rank;
		$scope.exam = response.body.exam;
		$scope.marks = response.body.marks;
                $scope.audit=response.audit;
                $scope.gradeDescription=response.body.gradeDescription;
//		fnSelectResponseHandler(selectBoxes);//Select Box
		//Screen Specific Response scope Ends
		$scope.studentProgressCardCurPage = 0;
	        $scope.StudentProgressCardRoleTable = null;
	       $scope.StudentProgressCardShowObject = null;
		//Multiple View Response Starts 
                
                
                if(response.body.marks!=null){
                
		$scope.StudentProgressCardRoleTable = fnConvertmvw('StudentProgressCardRoleTable',response.body.marks);
		
                }else{
                    
                    $scope.StudentProgressCardRoleTable=null;
                }
            
                $scope.studentProgressCardCurPage = 1;
		$scope.StudentProgressCardShowObject = $scope.fnMvwGetCurPageTable('studentProgressCard');
		
			$scope.mvwAddDeteleDisable = true; //Multiple View
		//Multiple View Response Ends 
 
        }
         if (subScreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
           subScreen=false;
      }
		return true;

}

}



