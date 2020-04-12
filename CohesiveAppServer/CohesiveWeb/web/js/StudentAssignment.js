/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.status = "";
	$scope.assignmentDescription = "";
	$scope.subjectName = "Select option";
	$scope.content = "";
	$scope.subjectID = "";
	$scope.assignmentID = "";
	$scope.AssignmentMaster = [{
		AssignmentId: "",
		AssignmentDescription: ""
	}];
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.completedDate = "";
	$scope.teacherComments = "";
	$scope.parentComment = "";
	$scope.classes = Institute.ClassMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.StatusMaster = Institute.StatusMaster;
	$scope.Status = Institute.StatusMaster;
	$scope.AssignmentType = Institute.AssignmentTypeMaster;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.assignmentDescriptionreadOnly = true;
	$scope.contentreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.completeDatereadOnly = true;
	$scope.teacherCommentreadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.statusReadonly = true;
        $scope.urlreadOnly = true;
	$scope.contentTypereadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.tableOperationShow = true;
	$scope.subReadonly = true;
	$scope.operation = '';
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
	
	$scope.fnClassAssignmentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Assignment';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	

});
//--------------------------------------------------------------------------------------------------------------

//-------Default Load record Starts -------------------------------------
$(document).ready(function () {
	  MenuName = "StudentAssignment";
          window.parent.nokotser=$("#nokotser").val();
          window.parent.Entity="Student";
     
	  fnDatePickersetDefault('assignmentDueDate',fndatePickerassignmentDueDateEventHandler);
//	  fnDatePickersetDefault('assignmentCompleteDate',fndatePickerassignmentCompleteDateEventHandler);
          fnsetDateScope();
		   selectBoxes= ['subject','assignmentType','Status'];
          fnGetSelectBoxdata(selectBoxes);
	 
          
          
//         $("#StudAssignmentFile").change(function(){ 
//         $("#StudAssignmentUpld").submit();
//        
//}); 
//  $("#StudAssignmentUrl").change(function(){ 
//        $("#StudAssignmentUrlUpld").submit();
//        
//});    

$( "#InstituteAssignmentURL" ).keydown(function() {
  $("#Institute").attr("src",$("#InstituteAssignmentURL").val());
});
         
});
// Default Load Record Ends


function fnPostImageUpload(id,fileName,UploadID)
{
    
       $(id).attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope(); 
       if (id =='#studAssignment')
         $scope.contentType="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       else if(id =='#video') 
         $scope.url="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       $scope.$apply();
   
}

function fnStudentAssignmentpostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.SubjectMaster.length>0)
      {    
      // $scope.Types = Institute.NotificationMaster;
	$scope.subjects = Institute.SubjectMaster;
	window.parent.fn_hide_parentspinner();
	  if (window.parent.StudentAssignmentkey.assignmentID !=null && window.parent.StudentAssignmentkey.assignmentID !='')
	{
            
             if (window.parent.StudentAssignmentkey.studentID !=null && window.parent.StudentAssignmentkey.studentID !='')
	{
		var assignmentID=window.parent.StudentAssignmentkey.assignmentID;
		var studentID=window.parent.StudentAssignmentkey.studentID;
		 window.parent.StudentAssignmentkey.assignmentID =null;
                 window.parent.StudentAssignmentkey.studentID =null;
		
		fnshowSubScreen(assignmentID,studentID);
		
            }     
                
	}
    /*var emptyStudentAssignment = {
		studentName: "",
		studentID: "",
		subjectID: "",
		subjectName: "Select option",
		assignmentID: "",
		assignmentDescription: "",
		assignmentType: "",
		dueDate: "",
		completedDate: "",
		teacherComments: "",
		parentComment: "",
		contentType: "",
                url:""
	};
         //Screen Specific DataModel Starts
	var dataModel = emptyStudentAssignment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null && $scope.studentID!=""){
	dataModel.studentID = $scope.studentID;
	var response = fncallBackend('StudentAssignment', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
}*/
$scope.$apply();
}
}
function fnshowSubScreen(assignmentID,studentID)
{
        subScreen = true;
	var emptyStudentAssignment = {
		studentName: "",
		studentID: "",
		subjectID: "",
		subjectName: "Select option",
		assignmentID: "",
		assignmentDescription: "",
		assignmentType: "",
		dueDate: "",
		completedDate: "",
		teacherComments: "",
		parentComment: "",
		contentType: "",
                url:""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentAssignment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.assignmentID=assignmentID;
        $scope.studentID=studentID;
        
        if($scope.assignmentID!=null && $scope.assignmentID!="")
        {
        if($scope.studentID!=null && $scope.studentID!="")
        {    
            
            
	dataModel.assignmentID = $scope.assignmentID;
        dataModel.studentID = $scope.studentID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentAssignment', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	
    }
}
return true;
}
// Cohesive Screen Specific Query Starts
function fnStudentAssignmentQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.subjectID = "";
	$scope.subjectName = "Select option";
	$scope.assignmentID = "";
	$scope.assignmentType = "";
	$scope.assignmentDescription = "";
	$scope.dueDate = "";
	$scope.completedDate = "";
	$scope.content = "";
	$scope.teacherComments = "";
	$scope.parentComment = "";
        $("#Institute").attr("src","");
//           $("#studAssignment").attr("src","");
//         $("#StudentAssignmentFile").val(""); 
//            $("#video").attr("src","");
//         $("#StudAssignmentUrl").val(""); 
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.subjectNamereadOnly = true;
	$scope.assignmentIDreadOnly = false;
	$scope.assignmentTypereadOnly = true;
	$scope.assignmentDescriptionreadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.contentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = false;
        $scope.urlreadOnly = false;
	$scope.contentTypereadOnly = false;
	$scope.teacherCommentreadOnly = true;
	$scope.parentCommentreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit ={};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
    //Generic Field Ends
	return true;
}
// Cohesive Screen Specific Query Ends

// Cohesive Screen Specific View Starts
function fnStudentAssignmentView() {
	var emptyStudentAssignment = {
		studentName: "",
		studentID: "",
		subjectID: "",
		subjectName: "Select option",
		assignmentID: "",
		assignmentDescription: "",
		assignmentType: "",
		dueDate: "",
		completedDate: "",
		teacherComments: "",
		status: "",
		parentComment: "",
		contentType: "",
                url:""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentAssignment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        dataModel.studentName = $scope.studentName;
        if($scope.assignmentID!=null)
	dataModel.assignmentID = $scope.assignmentID;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentAssignment', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
        
        
}
// Cohesive Screen Specific View Ends
//Screen Specific Mandatory Validation Starts
function fnStudentAssignmentMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//			if ($scope.studentID == '' || $scope.studentID == null) {
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
                        if ($scope.assignmentID == '' || $scope.assignmentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment ID']);
				return false;
			}
			/*if ($scope.dueDate == '' || $scope.dueDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment Due Date']);
				return false;
			}*/

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
			if ($scope.assignmentID == '' || $scope.assignmentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment ID']);
				return false;
			}
                        if ($scope.assignmentType == '' || $scope.assignmentType == null || $scope.assignmentType == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment Type in General Tab']);
				return false;
			}
                        
			if ($scope.assignmentDescription == '' || $scope.assignmentDescription == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment Description']);
				return false;
			}
			if ($scope.subjectName == '' || $scope.subjectName == null || $scope.subjectName == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Name in General Tab']);
				return false;
			}
			if ($scope.dueDate == '' || $scope.dueDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment Due Date']);
				return false;
			}
			if ($scope.teacherComments == '' || $scope.teacherComments == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Comments in General Tab']);
				return false;
			}

			break;


	}
	return true;
}

function fnStudentAssignmentDefaultandValidate(operation) {
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

function fnStudentAssignmentDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultAssignmentId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultAssignmentId($scope))
				return false;

			break;
	}
	return true;
}
function fnDefaultAssignmentId($scope) {
	var availabilty = false;
	return true;
}

// Screen Specific Mandatory Validation Ends
// Cohesive Create Framework Starts 
function fnStudentAssignmentCreate() { 
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.subjectID = "";
	$scope.subjectName = "Select option";
	$scope.assignmentID = "";
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.completedDate = "";
	$scope.teacherComments = "";
	$scope.parentComment = "";
        $scope.url = "";
        $scope.contentType = "";
	$scope.assignmentDescription = "";
          $("#studAssignment").attr("src","");
         $("#StudentAssignmentFile").val(""); 
            $("#video").attr("src","");
         $("#StudAssignmentUrl").val(""); 
	$scope.studentIDreadOnly = false;
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.subjectIDreadOnly = false;
	$scope.assignmentTypereadOnly = false;
	$scope.assignmentIDreadOnly = false;
	$scope.dueDatereadOnly = false;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", false );
	$scope.teacherCommentreadOnly = false;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = false;
	$scope.statusReadonly = false;
	$scope.assignmentDescriptionreadOnly = false;
        $scope.urlreadOnly = false;
	$scope.contentTypereadOnly = false;
        //Screen Specific Scope Starts
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.subjects = Institute.SubjectMaster;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
        //Generic Field Ends
	return true;
}
// Cohesive Create Framework Ends
// Cohesive Edit Framework Starts
function fnStudentAssignmentEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
         //Generic field Ends
	//Screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
         $scope.studentNameSearchreadOnly = true;
	$scope.studentNamereadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.subjectIDreadOnly = false;
	$scope.assignmentTypereadOnly = false;
	$scope.assignmentIDreadOnly = false;
	$scope.assignmentDescriptionreadOnly = false;
	$scope.dueDatereadOnly = false;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", false );
	$scope.teacherCommentreadOnly = false;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = false;
	$scope.statusReadonly = false;
        $scope.urlreadOnly = false;
	$scope.contentTypereadOnly = false;
    //Screen Specific Scope Ends	
	return true;
}
// Cohesive Edit Framework Ends
// Cohesive Delete Framework Starts
function fnStudentAssignmentDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	// Generic Field Ends
    // screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
         $scope.studentNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.teacherCommentreadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.assignmentDescriptionreadOnly = true;
        $scope.urlreadOnly = true;
	$scope.contentTypereadOnly = true;
	 // screen Specific Scope Ends	
	return true;
}
// Cohesive Delete Framework Ends
// Cohesive Authorization Framework Starts
function fnStudentAssignmentAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
    //Generic Field Ends
	// Screen Specific Scope Starts
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.teacherCommentreadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.assignmentDescriptionreadOnly = true;
        $scope.urlreadOnly = true;
	$scope.contentTypereadOnly = true;
	// Screen Specific Scope Ends
	
	return true;
}
// Cohesive Authorization Framework Ends
// Cohesive Reject Framework Starts
function fnStudentAssignmentReject() {

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
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
         $scope.studentNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.teacherCommentreadOnly = true;
	$scope.parentCommentreadOnly = true;
	$scope.completedDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.assignmentDescriptionreadOnly = true;
        $scope.urlreadOnly = true;
	$scope.contentTypereadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
// Cohesive Reject Framework Ends
// Cohesive Back Framework Starts
function fnStudentAssignmentBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specfic Scope Starts
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.studentName = "";
		$scope.studentID = "";
		$scope.subjectID = "";
		$scope.subjectName = "Select option";
		$scope.assignmentID = "";
		$scope.assignmentDescription = "";
		$scope.assignmentType = "";
		$scope.dueDate ="";
		$scope.completedDate ="";
		$scope.teacherComments ="";
		$scope.parentComment ="";
                $scope.contentType ="";
                $scope.url ="";
                $("#Institute").attr("src","");
//                $("#studAssignment").attr("src","");
//                $("#StudentAssignmentFile").val(""); 
//                $("#video").attr("src","");
//                $("#StudAssignmentUrl").val(""); 
	}
	        //Screen Specific Scope Starts
		$scope.studentIDreadOnly = true;
                $scope.studentNameSearchreadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.subjectNamereadOnly = true;
		$scope.subjectIDreadOnly = true;
		$scope.assignmentTypereadOnly = true;
		$scope.assignmentIDreadOnly = true;
		$scope.dueDatereadOnly = true;
                $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
		$scope.teacherCommentreadOnly = true;
		$scope.parentCommentreadOnly = true;
		$scope.completedDatereadOnly = true;
		$scope.statusReadonly = true;
                $scope.urlreadOnly = true;
	        $scope.contentTypereadOnly = true;
		$scope.assignmentDescriptionreadOnly = true;
		// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	// Generic Field Ends
}
// Cohesive Back Framework Ends
// Cohesive Save Framework Starts
function fnStudentAssignmentSave() {
	var emptyStudentAssignment = {
		studentName: "",
		studentID: "",
		subjectID: "",
		subjectName: "Select option",
		assignmentDescription:"",
		assignmentID: "",
		assignmentType: "",
		dueDate: "",
		completedDate: "",
		teacherComments: "",
		status: "",
		parentComment: "",
                contentType:"",
                url:""
	};
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentAssignment;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.subjectID!=null)
	dataModel.subjectID = $scope.subjectID;
        if($scope.subjectName!=null)
	dataModel.subjectName = $scope.subjectName;
        if($scope.assignmentID!=null)
	dataModel.assignmentID = $scope.assignmentID;
        if($scope.assignmentDescription!=null)
	dataModel.assignmentDescription = $scope.assignmentDescription;
        if($scope.assignmentType!=null)
	dataModel.assignmentType = $scope.assignmentType;
        if($scope.dueDate!=null)
	dataModel.dueDate = $scope.dueDate;
        if($scope.teacherComments!=null)
	dataModel.teacherComments = $scope.teacherComments;
        if($scope.completedDate!=null)
	dataModel.completedDate = $scope.completedDate;
        if($scope.parentComment!=null)
	dataModel.parentComment = $scope.parentComment;
        if($scope.contentType!=null)
	dataModel.contentType = $scope.contentType;
       if($scope.url!=null)
	dataModel.url = $scope.url;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentAssignment', parentOperation, dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
// Cohesive Save Framework Starts
function fndatePickerassignmentDueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentDueDate").datepicker("getDate"));
		$scope.$apply();
}
function fndatePickerassignmentCompleteDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.completedDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentCompleteDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentDueDate").datepicker("getDate"));
//	$scope.completedDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentCompleteDate").datepicker("getDate"));
		$scope.$apply();
}	

function fnStudentAssignmentpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.studentIDreadOnly = true;
                $scope.studentNameSearchreadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.assignmentTypereadOnly = true;
		$scope.assignmentIDreadOnly = true;
		$scope.assignmentDescriptionreadOnly = true;
		$scope.dueDatereadOnly = true;
                $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
		$scope.teacherCommentreadOnly = true;
		$scope.parentCommentreadOnly = true;
		$scope.completedDatereadOnly = true;
		$scope.statusReadonly = true;
                $scope.urlreadOnly = true;
	        $scope.contentTypereadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.assignmentID = "";
		$scope.assignmentDescription ="";
		$scope.assignmentType ="";
                $scope.studentID ="";
                $scope.subjectName ="";
                $scope.studentName ="";
                $scope.dueDate ="";
                $scope.completedDate ="";
                $scope.teacherComments ="";
                $scope.parentComment ="";
                $scope.subjectID ="";
                 $scope.contentType ="";
                  $scope.url ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.assignmentID = response.body.assignmentID;
		$scope.assignmentDescription = response.body.assignmentDescription;
		$scope.assignmentType = response.body.assignmentType;
		$scope.studentID = response.body.studentID;
		$scope.subjectName = response.body.subjectName;
		$scope.studentName = response.body.studentName;
		$scope.dueDate = response.body.dueDate;
		$scope.completedDate = response.body.completedDate;
		$scope.teacherComments = response.body.teacherComments;
		$scope.parentComment = response.body.parentComment;
		$scope.subjectID = response.body.subjectID;
                $scope.contentType = response.body.contentType;
                    $scope.url = response.body.url;
		//Screen Specific Response Scope Ends
 
        }
         if (subScreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
      }
//         if($scope.contentType!=null)
//         $("#studAssignment").attr("src",$scope.contentType);    
//         if($scope.url!=null)
//         $("#video").attr("src",$scope.url);    
         if($scope.url!=null)
         $("#Institute").attr("src",$scope.url); 
      
      
      
      
		return true;

}

}