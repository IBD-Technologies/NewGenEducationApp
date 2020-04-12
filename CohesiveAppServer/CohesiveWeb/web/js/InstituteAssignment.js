//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//Screen Specific Scope Starts
	$scope.subjectID = "Select option";
	$scope.assignmentID = "";
	$scope.groupID = "";
	$scope.assignmentDescription = "";
	$scope.AssignmentMaster = [{
		AssignmentId: "",
		AssignmentDescription: ""
	}];
	$scope.FeeMaster = [{
		FeeId:"",
		FeeType:""
	}];
	$scope.GroupMappingMaster = [{
		GroupId: "",
		GroupDescription: ""
	}];
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.teacherComments = "";
	$scope.URL = "";
	$scope.contentPath = "";
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.subjects = Institute.SubjectMaster;
	$scope.AssignmentType = Institute.AssignmentTypeMaster;
	$scope.subjectNamereadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.assignmentDescriptionreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.teacherCommentsreadOnly = true;
	$scope.URLreadOnly = true;
	$scope.contentPathreadOnly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
        $scope.assignmentIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.searchShow=false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.ClassSection = ["Select option"]; // Std/sec
    //Generic Field Ends
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
	$scope.fnGroupingSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Group';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	$scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Institute';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	

});
//--------------------------------------------------------------------------------------------------------------
//Default Load Record Starts
$(document).ready(function () {
	MenuName = "InstituteAssignment";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Institute";
	fnDatePickersetDefault('assignmentDueDate',fndueDateEventHandler);
        fnsetDateScope();
	selectBoxes= ['assignmentType','SubjectName'];
        fnGetSelectBoxdata(selectBoxes);
       
        $("#InstituteAssignmentFile").change(function(){ 
        $("#InstituteAssignmentUpld").submit();
        
});
        $( "#InstituteAssignmentURL" ).keydown(function() {
  $("#Institute").attr("src",$("#InstituteAssignmentURL").val());
});
            
         $( "#InstituteAssignmentURL" ).on('paste',function(e) {
             var clibBoard=e.originalEvent.clipboardData.getData('Text');
            if(clibBoard.toLowerCase().includes("youtube") || clibBoard.toLowerCase().includes("youtu.be")) 
               var url=frameYoutubeURL(clibBoard);
            else
            {  
              fn_Show_Exception_With_Param('FE-VAL-035', ['Institute ID']);
				return false; 
                            }
              $("#Institute").attr("src",url);               
  
});           
   //         ); 
 /* $("#InstituteAssignmentUrl").change(function(){ 
        $("#InstituteAssignmentUrlUpld").submit();
        
}); */

$('#InstituteAssignmentUpld').submit(function(event){
   $("#Institute").attr("src","");
  fileUpload('#Institute',$('#InstituteAssignmentUpld')[0],$('#InstituteAssignmentUpld').attr('action'),"InstituteAssignment");
  event.preventDefault();
    return true;
}); 

/*$('#InstituteAssignmentUrlUpld').submit(function(event){
   $("#video").attr("src","");
  fileUpload('#video',$('#InstituteAssignmentUrlUpld')[0],$('#InstituteAssignmentUrlUpld').attr('action'),"InstituteAssignment");
  event.preventDefault();
    return true;
});*/     
});
function frameYoutubeURL(actualURL)
{
    if(actualURL.toLowerCase().includes("youtube") || actualURL.toLowerCase().includes("youtu.be"))
    {
     var startPosofID = actualURL.lastIndexOf("/");
     var ID=actualURL.substr(startPosofID+1,actualURL.length-startPosofID);
     var dummy ="https://www.youtube.com/embed/";
     var youtubeURL=dummy.concat(ID);
     return youtubeURL;
     }
   return actualURL;
        
         
}
function fnPostImageUpload(id,fileName,UploadID)
{
    
       $(id).attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope(); 
       if (id =='#Institute')
         $scope.URL="/CohesiveUpload/images/"+UploadID+"/"+fileName;
      /* else if(id =='#video') 
         $scope.url="/CohesiveUpload/images/"+UploadID+"/"+fileName;*/
       $scope.$apply();
   
}
//Default Load Record Ends

function fnInstituteAssignmentpostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
    
    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.SubjectMaster.length>0)
      {   
     $scope.subjects = Institute.SubjectMaster;
	  window.parent.fn_hide_parentspinner();
   
    if (window.parent.InstituteAssignmentkey.assignmentID !=null && window.parent.InstituteAssignmentkey.assignmentID !='')
	{
		var assignmentID=window.parent.InstituteAssignmentkey.assignmentID;
		
		 window.parent.InstituteAssignmentkey.assignmentID =null;
		
		fnshowSubScreen(assignmentID);
		
	} 
    $scope.$apply();
      }
}
}

function fnshowSubScreen(assignmentID)
{
        subScreen = true;
	var emptyInstituteAssignment = {
		instituteID: "",
		instituteName: "",
		assignmentID: "",
		assignmentDescription: "",
		groupID: "",
		subjectID: "",
		subjectID: "Select option",
		assignmentType: "",
		dueDate: "",
		teacherComments: "",
		URL: "",
		contentPath: ""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstituteAssignment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        
        if(assignmentID!=null&&assignmentID!=""){
        
            $scope.assignmentID=assignmentID;
            dataModel.assignmentID = $scope.assignmentID;
            dataModel.instituteID=window.parent.Institute.ID;
            // Screen Specific DataModel Ends
            var response = fncallBackend('InstituteAssignment', 'View', dataModel, [{entityName:"instituteID",entityValue:dataModel.instituteID}], $scope);
        }
    
    
    
    return true;
}

//Cohesive query Framework Starts
function fnInstituteAssignmentQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	//$scope.instituteID = "";
	//$scope.instituteName = "";
       
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
        
	$scope.subjectID = "";
	$scope.subjectID = "Select option";
	$scope.assignmentID = "";
	$scope.groupID = "";
	$scope.assignmentDescription = "";
	$scope.assignmentTopic = "Select option";
	$scope.dueDate = "";
	$scope.teacherComments = "";
	$scope.URL = "";
	$scope.url = "";
         $("#Institute").attr("src","");
         $("#InstituteAssignmentFile").val(""); 
         $("#video").attr("src","");
         $("#InstituteAssignmentUrl").val(""); 
	$scope.subjectNamereadOnly = true;
	$scope.assignmentIDreadOnly = false;
	$scope.assignmentDescriptionreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.teacherCommentsreadOnly = true;
	$scope.urlreadOnly = true;
	$scope.URLreadOnly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
        $scope.assignmentIDSearchreadOnly = false;
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
//Cohesive View Framework starts
function fnInstituteAssignmentView() {
	var emptyInstituteAssignment = {
		instituteID: "",
		instituteName: "",
		assignmentID: "",
		groupID: "",
		assignmentDescription: "",
		subjectID: "",
		subjectID: "Select option",
		assignmentType: "",
		dueDate: "",
		teacherComments:"",
		URL: "",
		url: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyInstituteAssignment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        
        if($scope.assignmentID!=null||$scope.assignmentID!=""){
        
            dataModel.assignmentID = $scope.assignmentID;
            dataModel.instituteID = $scope.instituteID;
            
            var response = fncallBackend('InstituteAssignment', 'View', dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
        }	
    
    
    return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnInstituteAssignmentMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
                        if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
			if ($scope.assignemtID == '' || $scope.assignmentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment ID']);
				return false;
			}
			break;

		case 'Save':
		    if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
                         if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			if ($scope.assignemtID == '' || $scope.assignmentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignement ID']);
				return false;
			}
			if ($scope.assignmentType == '' || $scope.assignmentType == null || $scope.assignmentType == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignment Type in General Tab']);
				return false;
			}
                        if ($scope.assignmentDescription == '' || $scope.assignmentDescription == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignemt Description in General Tab']);
				return false;
			}
                        if ($scope.subjectID == '' || $scope.subjectID == null || $scope.subjectID == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Name in General Tab']);
				return false;
			}
                        if ($scope.groupID == '' || $scope.groupID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Assignee in General Tab']);
				return false;
			}
                        if ($scope.URL == '' || $scope.URL == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['content file in Content  Tab']);
				return false;
			}
//                        if ($scope.teacherComments == '' || $scope.teacherComments == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Comments in Others Tab']);
//				return false;
//			}
			if ($scope.dueDate == '' || $scope.dueDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Due Date in Others Tab']);
				return false;
			}
			
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends
//Screen specific Default Validation Starts
function fnInstituteAssignmentDefaultandValidate(operation) {
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


function fnInstituteAssignmentDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultGroupId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultGroupId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultGroupId($scope) {
	var availabilty = false;
	return true;
}




//Screen specific Default Validation Ends
//Cohesive Create Framework Starts
function fnInstituteAssignmentCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //Screen Specific Scope Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
        
	$scope.subjectID = "";
	$scope.subjectID = "Select option";
	$scope.assignmentID = "";
	$scope.groupID = "";
	$scope.assignmentDescription = "";
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.teacherComments = "";
	$scope.URL = "";
	$scope.contentPath = "";
         $("#Institute").attr("src","");
         $("#InstituteAssignmentFile").val(""); 
         $("#video").attr("src","");
         $("#InstituteAssignmentUrl").val(""); 
	$scope.subjects = Institute.SubjectMaster;
	$scope.subjectNamereadOnly = true;
	$scope.subjectNamereadOnly = false;
	$scope.assignmentTypereadOnly = false;
	$scope.assignmentIDreadOnly = false;
	$scope.assignmentDescriptionreadOnly = false;
	$scope.dueDatereadOnly = false;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", false );
	$scope.teacherCommentsreadOnly = false;
	$scope.contentPathreadOnly = false;
	$scope.groupIDReadOnly = false;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
         $scope.assignmentIDSearchreadOnly = true;
		$scope.URLreadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
        $scope.operation = 'Creation';
    //Generic Field Ends
        var emptyInstituteAssignment = {
		instituteID: "",
		instituteName: "",
		groupID: "",
		subjectID: "",
		subjectID: "Select option",
		assignmentID: "",
		assignmentType: "",
		assignmentDescription:"",
		dueDate: "",
		teacherComments: "",
		URL: "",
		contenturl: ""
	};
         var dataModel = emptyInstituteAssignment;
	
var response = fncallBackend('InstituteAssignment', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
    
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnInstituteAssignmentEdit() {
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
	$scope.assignmentDescriptionreadOnly = false;
	$scope.sectionreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.assignmentIDreadOnly = true;
        $scope.assignmentTypereadOnly = false;
	$scope.dueDatereadOnly = false;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", false );
	$scope.teacherCommentsreadOnly = false;
	$scope.urlreadOnly = false;
        $scope.URLreadOnly=false;
	$scope.groupIDReadOnly = false;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
         $scope.assignmentIDSearchreadOnly = true;
    //screen Specific Scope Ends	
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnInstituteAssignmentDelete() {
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
	$scope.subjectNamereadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.assignmentDescriptionreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.contentPathreadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.urlreadOnly = true;
	$scope.teacherCommentsreadOnly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
         $scope.assignmentIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorization Framework Starts
function fnInstituteAssignmentAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
        //General Field Ends
	//Screen Specific Scope Starts
	$scope.subjectNamereadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.assignmentIDreadOnly = true;
	$scope.assignmentDescriptionreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.teacherCommentsreadOnly = true;
	$scope.urlreadOnly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
         $scope.assignmentIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Authorization Framework Ends
//Cohesive Reject Framework Starts
function fnInstituteAssignmentReject() {
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
	$scope.subjectNamereadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.assgnmentTopicreadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.assignmentDescriptionreadOnly = true;
	$scope.teacherCommentsreadOnly = true;
	$scope.dueDatereadOnly = true;
        $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
	$scope.urlreadOnly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
         $scope.assignmentIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework  Starts
function fnInstituteAssignmentBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
	$scope.audit = {};
	$scope.groupID = "";
	$scope.subjectID = "";
	$scope.subjectID = "Select option";
	$scope.assignmentID = "";
	$scope.assignmentDescription = "";
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.teacherComments = "";
	$scope.URL = "";
	$scope.url = "";
	$scope.instituteID = "";
	$scope.instituteName = "";
         $("#Institute").attr("src","");
         $("#InstituteAssignmentFile").val(""); 
         $("#video").attr("src","");
         $("#InstituteAssignmentUrl").val(""); 
	}
	//Screen Specific Scope Starts
		$scope.subjectNamereadOnly = true;
		$scope.subjectNamereadOnly = true;
		$scope.assgnmentTopicreadOnly = true;
		$scope.assignmentIDreadOnly = true;
		$scope.assignmentDescriptionreadOnly = true;
		$scope.teacherCommentsreadOnly = true;
		$scope.assignmentTypereadOnly = true;
		$scope.dueDatereadOnly = true;
                $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
		$scope.urlreadOnly = true;
		$scope.groupIDReadOnly = true;
		$scope.instituteIDreadOnly = true;
	        $scope.instituteNamereadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
                 $scope.assignmentIDSearchreadOnly = true;
	    //screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
        $scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
        $scope.auditshow = false;
	//Generic Field Ends
}
//Cohesive Back Framework  Ends
//Cohesive Save Framework  Starts
function fnInstituteAssignmentSave() {
	var emptyInstituteAssignment = {
		instituteID: "",
		instituteName: "",
		groupID: "",
		subjectID: "",
		subjectID: "Select option",
		assignmentID: "",
		assignmentType: "",
		assignmentDescription:"",
		dueDate: "",
		teacherComments: "",
		URL: "",
		contenturl: ""
	};
    //Screen Specific DataModel Starts
	var dataModel = emptyInstituteAssignment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.groupID!=null)
	dataModel.groupID = $scope.groupID;
        if($scope.subjectID!=null)	
	dataModel.subjectID = $scope.subjectID;
        if($scope.subjectID!=null)	
	dataModel.subjectID = $scope.subjectID;
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
        if($scope.instituteID!=null)	
	dataModel.instituteID = $scope.instituteID;
        if($scope.instituteName!=null)	
	dataModel.instituteName = $scope.instituteName;
        if($scope.URL!=null)	
	dataModel.URL = $scope.URL;
	//Screen Specific DataModel Ends
	var response = fncallBackend('InstituteAssignment', parentOperation, dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	return true;
}
//Cohesive Save Framework  Ends
function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentDueDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentDueDate").datepicker("getDate"));
		$scope.$apply();
}

function fnInstituteAssignmentpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {  
		// Specific Screen Scope Starts
                
                if(response.header.operation == 'Create-Default')
		
		{
		  $scope.assignmentID = response.body.assignmentID;
                   if(response.body.instituteId!=null && response.body.instituteId!="")
                  $scope.instituteId= response.body.instituteId;
                 if(response.body.instituteName!=null && response.body.instituteName!="")
                  $scope.instituteName = response.body.instituteName;
		  return true;
		}
		else
		{
                
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.subjectNamereadOnly = true;
		$scope.subjectNamereadOnly = true;
		$scope.assignmentTypereadOnly = true;
		$scope.assignmentIDreadOnly = true;
		$scope.assignmentDescriptionreadOnly = true;
		$scope.teacherCommentsreadOnly = true;
                 $scope.assignmentIDSearchreadOnly = true;
		$scope.dueDatereadOnly = true;
                $( "#assignmentDueDate" ).datepicker( "option", "disabled", true );
		$scope.urlreadOnly = true;
		$scope.groupIDReadOnly = true;
		$scope.instituteIDreadOnly = true;
	        $scope.instituteNamereadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
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
                $scope.instituteID = "";
		$scope.instituteName ="";
		$scope.dueDate ="";
                $scope.assignmentID ="";
                $scope.assignmentType ="";
                $scope.assignmentDescription ="";
                $scope.teacherComments ="";
                $scope.subjectID ="";
                $scope.url ="";
                $scope.URL ="";
                $scope.groupID="";
            
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.dueDate = response.body.dueDate;
		$scope.assignmentID = response.body.assignmentID;
		$scope.assignmentType = response.body.assignmentType;
		$scope.assignmentDescription = response.body.assignmentDescription;
		$scope.teacherComments = response.body.teacherComments;
		$scope.url = response.body.url;
                $scope.URL = response.body.URL;
		$scope.subjectID = response.body.subjectID;
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.subjectID = response.body.subjectID;
                $scope.groupID = response.body.groupID;
                $scope.audit=response.audit;
                }
         if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen=false;
         }  
         if($scope.URL!=null)
         $("#Institute").attr("src",$scope.URL);    
         if($scope.url!=null)
         $("#video").attr("src",$scope.url);    
		return true;
        }
}

}