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
	$scope.skills = "";
	$scope.gradeDescription = "";
	$scope.Class = Institute.StandardMaster;
	$scope.skills = Institute.SkillMaster;
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
	$scope.categoryReadOnly = true;
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
    $scope.studentSoftSkillCurPage = 0;
	$scope.StudentSoftSkillRoleTable = null;
	$scope.StudentSoftSkillShowObject = null;
	// Multiple View Ends

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'studentSoftSkill') {
			if ($scope.StudentSoftSkillRoleTable != null && $scope.StudentSoftSkillRoleTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.studentSoftSkillCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillRoleTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.studentSoftSkillCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillRoleTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'studentSoftSkill') {
			if ($scope.StudentSoftSkillRoleTable != null && $scope.StudentSoftSkillRoleTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.studentSoftSkillCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillRoleTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.studentSoftSkillCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillRoleTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentSoftSkill') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					skillID: "",
				    skillName: "",
				    grade: "",
				    category: "",
				    teacherFeedback: ""
				};
				if ($scope.StudentSoftSkillRoleTable == null)
					$scope.StudentSoftSkillRoleTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.studentSoftSkillCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillRoleTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.studentSoftSkillCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillRoleTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentSoftSkill') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.studentSoftSkillCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillRoleTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.studentSoftSkillCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillRoleTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'studentSoftSkill') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.studentSoftSkillCurPage;
			lsvwObject.tableObject = $scope.StudentSoftSkillRoleTable;
			lsvwObject.screenShowObject = $scope.StudentSoftSkillShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'studentSoftSkill') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.studentSoftSkillCurPage;
			lsvwObject.tableObject = $scope.StudentSoftSkillRoleTable;
			lsvwObject.screenShowObject = $scope.StudentSoftSkillShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'studentSoftSkill') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentSoftSkillRoleTable);

		}
	};
	//Multiple View Scope Function ends	
	

});

// Default Load Record Starts
$(document).ready(function () {
	MenuName = "StudentSoftSkill";
        selectBypassCount=0;
	window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        selectBoxes= ['examType','skill'];
        fnGetSelectBoxdata(selectBoxes);      
});
function fnStudentSoftSkillpostSelectBoxMaster()
{
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       if(Institute.ExamMaster.length>0){
           $scope.skills = Institute.SkillMaster;
	   $scope.ExamMaster = Institute.ExamMaster; 
           window.parent.fn_hide_parentspinner();  
     if ((window.parent.StudentSoftSkillkey.studentID !=null && window.parent.StudentSoftSkillkey.studentID !='') && (window.parent.StudentSoftSkillkey.exam !=null && window.parent.StudentSoftSkillkey.exam !=''))
	{
		var studentID=window.parent.StudentSoftSkillkey.studentID;
		var exam=window.parent.StudentSoftSkillkey.exam;
		
		 window.parent.StudentSoftSkillkey.studentID =null;
                  window.parent.StudentSoftSkillkey.exam =null;
		
		fnshowSubScreen(studentID,exam);
		
	}
    
//    var emptyStudentSoftSkill = {
//		studentName: "",
//		studentID: "",
//		standard: "Select option",
//		section: "Select option",
//		exam: "Select option",
//		total: "",
//		rank: "",
//		marks: [{
//				skillID: "",
//				skillName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				skillID: "",
//				skillName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				skillID: "",
//				skillName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				skillID: "",
//				skillName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			    {
//				skillID: "",
//				skillName: "",
//				grade: "",
//				mark: "",
//				teacherFeedback: ""
//			},
//			{
//				skillID: "",
//				skillName: "",
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
			
//       var dataModel = emptyStudentSoftSkill;
//	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//        if($scope.studentID!=null && $scope.studentID!="") {
//	dataModel.studentID = $scope.studentID;
//	var response = fncallBackend('StudentSoftSkill', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
//}
 $scope.$apply();
}
    }
}
function fnshowSubScreen(studentID,exam)
{
    subScreen= true;
var emptyStudentSoftSkill = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank: "",
		skills: [{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
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
	var dataModel = emptyStudentSoftSkill;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(studentID!==null&&studentID!==null){
            
          if(exam!==null&&exam!==null){  
            
   	   dataModel.studentID = studentID;
           dataModel.exam = exam;
//	   var response = fncallBackend('StudentSoftSkill', 'View',[{entityName:"studentID",entityValue:studentID}],dataModel, $scope);
           var response = fncallBackend('StudentSoftSkill', 'View',dataModel,[{entityName:"studentID",entityValue:studentID}], $scope);

          }
        }
        return true;
}

function fnStudentSoftSkillDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (($scope.StudentSoftSkillRoleTable ==null)  && parentOperation=='Create')
	{	
	var emptyStudentSoftSkill = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank:"",
		skills: [{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
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
	var dataModel = emptyStudentSoftSkill;
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['StudentName']);
				return false;
			}
	var response = fncallBackend('StudentSoftSkill', 'softSkillDefault', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope);
		$scope.mvwAddDeteleDisable = true; //Multiple View
	   //Multiple View Response Starts 
		$scope.StudentSoftSkillRoleTable = fnConvertmvw('StudentSoftSkillRoleTable',response.body.skills);
		$scope.studentSoftSkillCurPage = 1;
		$scope.StudentSoftSkillShowObject = $scope.fnMvwGetCurPageTable('studentSoftSkill');
		//Multiple View Response Ends 	
	    // $scope.$apply();
	 
	 }	

return true;
}
//Default Load Record Ends
//Cohesive Query Framework Starts
	function fnStudentSoftSkillQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.exam = "Select option";
	$scope.rank = "";
	$scope.total = "";
	$scope.skills = "";
	$scope.studentNamereadOnly = false;
        $scope.studentNameSeachreadOnly = false;
	$scope.studentIDReadOnly = false;
	$scope.examtypereadOnly = false;
	$scope.totalReadOnly = true;
	$scope.rankReadOnly = true;
	$scope.categoryReadOnly = true;
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
function fnStudentSoftSkillView() {
	var emptyStudentSoftSkill = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank: "",
		skills: [{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				grade: "",
				category: "",
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
	var dataModel = emptyStudentSoftSkill;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if( $scope.studentID!=null&&$scope.studentID!=''){
        
        if( $scope.exam!=null&&$scope.exam!=''){
	  dataModel.exam = $scope.exam;
    	  dataModel.studentID = $scope.studentID;
         
	   var response = fncallBackend('StudentSoftSkill', 'View',dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
         }
        }
         return true;
}
//Cohesive View Framework Starts
//Screen Specific Mandatory  Validation Starts
function fnStudentSoftSkillMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
                    if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
				return false;
			}
			if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}
			if ($scope.exam == '' || $scope.exam == null) {
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

function fnStudentSoftSkillDefaultandValidate(operation) {
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
function fnStudentSoftSkillCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.exam = "Select option";
	$scope.total = "";
	$scope.rank = "";
	$scope.skills = "";
	$scope.skills = Institute.SkillMaster;
	$scope.studentNamereadOnly = false;
        $scope.studentNameSeachreadOnly = false;
	$scope.studentIDReadOnly = false;
	$scope.rankreadOnly = false;
	$scope.totalreadOnly = false;
	$scope.examtypereadOnly = false;
	$scope.categoryReadOnly = false;
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
		$scope.studentSoftSkillCurPage = 0;
	$scope.StudentSoftSkillRoleTable = null;
	$scope.StudentSoftSkillShowObject = null;
	
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnStudentSoftSkillEdit() {
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
	$scope.categoryReadOnly = false;
	$scope.gradeReadOnly = true;
	$scope.teacherFeedbackReadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends

//Cohesive Delete Framework Starts
function fnStudentSoftSkillDelete() {
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
	$scope.categoryReadOnly = true;
	$scope.teacherFeedbackReadOnly = true;
	$scope.gradeReadOnly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
    //Screen Specific Scope Ends	
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorization Framework Starts
function fnStudentSoftSkillAuth() {
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



function fnStudentSoftSkillEnroll() {
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
function fnStudentSoftSkillReject() {
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
function fnStudentSoftSkillBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.studentName= "";
		$scope.studentID= "";
		$scope.exam= "Select option";
		$scope.total= "";
		$scope.rank= "";
		$scope.skills= "";
	}
	    $scope.studentNamereadOnly = true;
                $scope.studentNameSeachreadOnly = true;
		$scope.studentIDReadOnly = true;
		$scope.totalReadOnly = true;
		$scope.rankReadOnly = true;
		$scope.examtypereadOnly = true;
		$scope.categoryReadOnly = true;
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
function fnStudentSoftSkillSave() {
	var emptyStudentSoftSkill = {
		studentName: "",
		studentID: "",
		exam: "Select option",
		total: "",
		rank: "",
		skills: [{
				skillID: "",
				skillName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				skillID: "",
				skillName: "",
				category: "",
				teacherFeedback: ""
			}
		]
	};

	var dataModel = emptyStudentSoftSkill;
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
        if($scope.skills!=null)
	dataModel.skills = $scope.skills;
	dataModel.gradeDescription = $scope.gradeDescription;
	var response = fncallBackend('StudentSoftSkill', parentOperation, dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	if (response.header.status == 'success') {
		//Screen Specific Scope Starts
		$scope.studentNamereadOnly = true;
		$scope.studentIDReadOnly = true;
		$scope.totalReadOnly = true;
		$scope.rankReadOnly = true;
		$scope.examtypereadOnly = true;
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.categoryReadOnly = true;
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
		case 'StudentSoftSkillRoleTable':
		   
			var StudentSoftSkillRoleTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     StudentSoftSkillRoleTable[index] = new Object();
					 StudentSoftSkillRoleTable[index].idx=index;
					 StudentSoftSkillRoleTable[index].checkBox=false;
					 StudentSoftSkillRoleTable[index].skillID=value.skillID;
					 StudentSoftSkillRoleTable[index].skillName=value.skillName;
					 StudentSoftSkillRoleTable[index].category=value.category;
					 StudentSoftSkillRoleTable[index].teacherFeedback=value.teacherFeedback;
					}
			return StudentSoftSkillRoleTable;
		}
	}
function fnStudentSoftSkillpostBackendCall(response)
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
		$scope.categoryReadOnly = true;
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
                $scope.skills ={};
                $scope.parentComment ="";
                $scope.StudentSoftSkillShowObject =null;
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
		$scope.skills = response.body.skills;
                $scope.audit=response.audit;
                $scope.gradeDescription=response.body.gradeDescription;
//		fnSelectResponseHandler(selectBoxes);//Select Box
		//Screen Specific Response scope Ends
		$scope.studentSoftSkillCurPage = 0;
	        $scope.StudentSoftSkillRoleTable = null;
	       $scope.StudentSoftSkillShowObject = null;
		//Multiple View Response Starts 
                
                
                if(response.body.skills!=null){
                
		$scope.StudentSoftSkillRoleTable = fnConvertmvw('StudentSoftSkillRoleTable',response.body.skills);
		
                }else{
                    
                    $scope.StudentSoftSkillRoleTable=null;
                }
            
                $scope.studentSoftSkillCurPage = 1;
		$scope.StudentSoftSkillShowObject = $scope.fnMvwGetCurPageTable('studentSoftSkill');
		
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



