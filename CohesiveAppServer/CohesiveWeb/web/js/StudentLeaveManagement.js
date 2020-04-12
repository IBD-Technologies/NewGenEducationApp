/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subscreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, OperationScopes) {
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.from = "";
	$scope.fromNoon = '';
	$scope.to = "";
	$scope.toNoon = '';
	$scope.type = "";
	$scope.reason = "";
	$scope.remarks ="";
        $scope.leaveStatus="";
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
        
        
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	$scope.statusreadOnly = true;
	$scope.remarksReadOnly = true;
	$scope.statusreadOnly = true;
	$scope.NoonMaster = Institute.NoonMaster;
	$scope.LeaveMaster = Institute.LeaveMaster;
	$scope.statusMaster = Institute.LeaveMasterStatus;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
	//Generic Field Ends	
	$scope.fnStudentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Student';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
});
//--------------------------------------------------------------------------------------------------------------
//Default load Record Starts
$(document).ready(function () {
	MenuName = "StudentLeaveManagement";
        selectBypassCount=0;
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "Student";
	window.parent.fn_hide_parentspinner();
	fnDatePickersetDefault('leaveFrom', fndatePickerfromEventHandler);
	fnDatePickersetDefault('leaveTo', fndatePickertoEventHandler);
	fnsetDateScope();
        selectBoxes = ['leaveType', 'studentStatus', 'toNoon', 'fromNoon'];
        fnGetSelectBoxdata(selectBoxes);
	
	

	
	
});

function fnStudentLeaveManagementpostSelectBoxMaster()
{
    
     selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        
       if (window.parent.StudentLeaveManagementkey.studentID !=null && window.parent.StudentLeaveManagementkey.studentID !='')
	{
            
            if (window.parent.StudentLeaveManagementkey.from !=null && window.parent.StudentLeaveManagementkey.from !='')
	  {
              
             if (window.parent.StudentLeaveManagementkey.to !=null && window.parent.StudentLeaveManagementkey.to !='')
	  {  
		var studentID=window.parent.StudentLeaveManagementkey.studentID;
                var from=window.parent.StudentLeaveManagementkey.from;
                var to=window.parent.StudentLeaveManagementkey.to;
		
		 window.parent.StudentLeaveManagementkey.studentID =null;
                 window.parent.StudentLeaveManagementkey.from =null;
                 window.parent.StudentLeaveManagementkey.to =null;
		
		fnshowSubScreen(studentID,from,to);
            }
	} 
    }
        var emptyStudentLeaveManagement = {
		studentName: "",
		studentID: "",
		from: "",
		fromNoon: 'Select option',
		to: "",
		toNoon: '',
		type: "",
		reason: "",
	};


	var dataModel = emptyStudentLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null && $scope.studentID!="")
        {
	dataModel.studentID = $scope.studentID;
	var response = fncallBackend('StudentLeaveManagement', 'View', dataModel, [{
		entityName: "studentID",
		entityValue: $scope.studentID
	}], $scope);
    
}
    }
}
function fnshowSubScreen(studentID,from,to) {
    subscreen = true;
	var emptyStudentLeaveManagement = {
		studentName: "",
		studentID: "",
		from: "",
		fromNoon: '',
		to: "",
		toNoon: '',
		type: "",
		reason: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(studentID!==null&&studentID!==''){
            
            if(from!==null&&from!==''){
                
                if(to!==null&&to!==''){
            
                    dataModel.studentID =studentID;
                    dataModel.from = from;
                    dataModel.to = to;
                    //Screen Specific DataModel Ends
                    var response = fncallBackend('StudentLeaveManagement', 'View', dataModel, [{entityName:"studentID",entityValue:dataModel.studentID}], $scope);
            }
            
        }
    }
        
        return true;
}
//Default Load Record Ends	
// Cohesive Query Framework Starts
function fnStudentLeaveManagementQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.from = "";
	$scope.fromNoon = '';
	$scope.to = "";
	$scope.toNoon = '';
	$scope.type = "";
	$scope.reason = "";
        $scope.leaveStatus="";
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.fromReadOnly = false;
	$scope.toReadOnly = false;
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", false );
        $( "#leaveTo" ).datepicker( "option", "disabled", false );
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	//Screen Specific Scope Ends
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	// Generic Field Ends
	return true;
}
// Cohesive Query Framework Ends
// Cohesive View Framework starts
function fnStudentLeaveManagementView() {
	var emptyStudentLeaveManagement = {
		studentName: "",
		studentID: "",
		from: "",
		fromNoon: '',
		to: "",
		toNoon: '',
		type: "",
		reason: "",
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//        if($scope.studentID!==null&&$scope.studentID!==''){
            
           if($scope.from!==null&&$scope.from!==''){
               
               if($scope.to!==null&&$scope.to!==''){

                dataModel.studentID = $scope.studentID;
                dataModel.studentName = $scope.studentName;
                dataModel.from = $scope.from;
                dataModel.to = $scope.to;
                //Screen Specific DataModel Ends
                var response = fncallBackend('StudentLeaveManagement', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
       }
           } 
//    }
        
        return true;
}
// Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnStudentLeaveManagementMandatoryCheck(operation) {
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
//			if ($scope.fromNoon == '' || $scope.fromNoon == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', [' From Noon Session']);
//				return false;
//			}
                        if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
			if ($scope.to == '' || $scope.to == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['To Date']);
				return false;
			}
//			if ($scope.toNoon == '' || $scope.toNoon == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['To noon session']);
//				return false;
//			}
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
			if ($scope.from == '' || $scope.from == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
				return false;
			}
			if ($scope.fromNoon == '' || $scope.fromNoon == null || $scope.fromNoon == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['From Session']);
				return false;
			}
			if ($scope.to == '' || $scope.to == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['To Date']);
				return false;
			}
//			if ($scope.toNoon == '' || $scope.toNoon == null || $scope.toNoon == 'Select option') {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['To Session']);
//				return false;
//			}
			if ($scope.type == '' || $scope.type == null || $scope.type == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Leave type']);
				return false;
			}
			if ($scope.reason == '' || $scope.reason == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Leave Reason']);
				return false;
			}
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends	

function fnStudentLeaveManagementDefaultandValidate(operation) {
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
function fnStudentLeaveManagementCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.from = "";
	$scope.fromNoon = '';
	$scope.to = "";
	$scope.toNoon = '';
	$scope.type = "";
	$scope.reason = "";
        $scope.leaveStatus="";
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.fromReadOnly = false;
	$scope.toReadOnly = false;
        $scope.fromNoonReadOnly = false;
	$scope.toNoonReadOnly = false;
        $( "#leaveFrom" ).datepicker( "option", "disabled", false );
        $( "#leaveTo" ).datepicker( "option", "disabled", false );
	$scope.typeReadOnly = false;
	$scope.reasonReadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//Generic Field Ends
	return true;
}
//Cohesive Create Framework Ends	
//Cohesive Edit Framework Starts	
function fnStudentLeaveManagementEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic  Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	//Generic  Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
         $scope.fromNoonReadOnly = false;
	$scope.toNoonReadOnly = false;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
	$scope.typeReadOnly = false;
	$scope.reasonReadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnStudentLeaveManagementDelete() {
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
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework starts
function fnStudentLeaveManagementAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	//Screen Specific scope Ends
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnStudentLeaveManagementReject() {
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
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnStudentLeaveManagementBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.studentName = "";
		$scope.studentID = "";
		$scope.from = "";
		$scope.fromNoon = '';
		$scope.to = "";
		$scope.toNoon = '';
		$scope.type = "";
		$scope.reason = "";
                $scope.leaveStatus="";
	}
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
}
//Cohesive Back Framework Ends
//Cohesive Save Framework Starts
function fnStudentLeaveManagementSave() {
	var emptyStudentLeaveManagement = {
		studentName: "",
		studentID: "",
		from: "",
		fromNoon: '',
		to: "",
		toNoon: '',
		type: "",
		reason: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.from!=null)
	dataModel.from = $scope.from;
        if($scope.fromNoon!=null)
	dataModel.fromNoon = $scope.fromNoon;
        if($scope.to!=null)
	dataModel.to = $scope.to;
        if($scope.toNoon!=null)
	dataModel.toNoon = $scope.toNoon;
        if($scope.type!=null)
	dataModel.type = $scope.type;
        if($scope.reason!=null)
	dataModel.reason = $scope.reason;
	//Screen Specific DataModel Starts
	var response = fncallBackend('StudentLeaveManagement', parentOperation, dataModel,  [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}

function fndatePickerfromEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.from = $.datepicker.formatDate('dd-mm-yy', $("#leaveFrom").datepicker("getDate"));
	$scope.$apply();
}

function fndatePickertoEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.to = $.datepicker.formatDate('dd-mm-yy', $("#leaveTo").datepicker("getDate"));
	$scope.$apply();
}

function fnsetDateScope() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.from = $.datepicker.formatDate('dd-mm-yy', $("#leaveFrom").datepicker("getDate"));
	$scope.to = $.datepicker.formatDate('dd-mm-yy', $("#leaveTo").datepicker("getDate"));
	$scope.$apply();
}


function fnStudentLeaveManagementpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.studentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.fromReadOnly = true;
		$scope.toReadOnly = true;
                $scope.fromNoonReadOnly = true;
	        $scope.toNoonReadOnly = true;
                $( "#leaveFrom" ).datepicker( "option", "disabled", true );
                $( "#leaveTo" ).datepicker( "option", "disabled", true );
		$scope.typeReadOnly = true;
		$scope.reasonReadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.studentID = "";
		$scope.studentName ="";
		$scope.from ="";
                $scope.fromNoon ="";
                $scope.to ="";
                $scope.toNoon ="";
                $scope.type ="";
                $scope.reason ="";
		$scope.leaveStatus="";
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
		$scope.from = response.body.from;
		$scope.fromNoon = response.body.fromNoon;
		$scope.to = response.body.to;
		$scope.toNoon = response.body.toNoon;
		$scope.type = response.body.type;
		$scope.reason = response.body.reason;
                $scope.audit=response.audit;
                
                if(response.audit.AuthStat==='Unauthorised'){
                
                    $scope.leaveStatus="Pending";
                }else{
                    
                    $scope.leaveStatus=response.audit.AuthStat;
                }
                }
                if (subscreen){
                 var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	         $operationScope.fnPostdetailLoad();
                 subscreen=false;
      }
		return true;

}

}