//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subscreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.from = "";
	$scope.fromNoon = '';
	$scope.to = "";
	$scope.toNoon = '';
	$scope.type = "";
	$scope.reason = "";
	$scope.remarks = "";
        $scope.leaveStatus="";
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	
	$scope.remarksReadOnly = true;
	$scope.NoonMaster = Institute.NoonMaster;
	$scope.LeaveMaster = Institute.LeaveMaster;
	$scope.statusMaster = Institute.FeeStatus;
	$scope.teacherMaster = [{
		TeacherId: null,
		TeacherName: null
	}];
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	// Generic Field Ends
	//Teacher ID Function Starts
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
});
//--------------------------------------------------------------------------------------------------------------

//-------This is to Load default record--------------------------------------
$(document).ready(function () {
	MenuName = "TeacherLeaveManagement";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Teacher";
        window.parent.fn_hide_parentspinner();
	fnDatePickersetDefault('leaveFrom',fndatePickerfromEventHandler);
	fnDatePickersetDefault('leaveTo',fndatePickertoEventHandler);
        fnsetDateScope();
        selectBoxes= ['leaveType','Status','fromNoon','toNoon'];
        fnGetSelectBoxdata(selectBoxes);
	

	//-------------------------------------------------------------------------	
});

function fnTeacherLeaveManagementpostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
      var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    
    if (window.parent.TeacherLeaveManagementkey.teacherID !=null && window.parent.TeacherLeaveManagementkey.teacherID !='')
	{
            
      if (window.parent.TeacherLeaveManagementkey.from !=null && window.parent.TeacherLeaveManagementkey.from !='')
	{ 
            
       if (window.parent.TeacherLeaveManagementkey.to !=null && window.parent.TeacherLeaveManagementkey.to !='')
	{      
            
		var teacherID=window.parent.TeacherLeaveManagementkey.teacherID;
                var from=window.parent.TeacherLeaveManagementkey.from;
                var to=window.parent.TeacherLeaveManagementkey.to;
		
		 window.parent.TeacherLeaveManagementkey.teacherID =null;
                 window.parent.TeacherLeaveManagementkey.from =null;
                 window.parent.TeacherLeaveManagementkey.to =null;
		
		fnshowSubScreen(teacherID,from,to);
        }
	}	
        
    }
var emptyTeacherLeaveManagement = {
		teacherName: "",
		teacherID: "",
		from: "",
		fromNoon: '',
		to: "",
		toNoon: '',
		type:"",
		reason: "",
		remarks: ""
	};

	var dataModel = emptyTeacherLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null && $scope.teacherID!=""){
	dataModel.teacherID = $scope.teacherID;
	var response = fncallBackend('TeacherLeaveManagement', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);    
    
}
    }
}
function fnshowSubScreen(teacherID,from,to)
{
 subscreen = true;
var emptyTeacherLeaveManagement = {
		teacherName: "",
		teacherID: "",
		from: "",
		fromNoon: '',
		to: "",
		toNoon: '',
		type: "",
		reason: "",
		remarks: ""
	};

    // Screen Specific Data Model Starts
	var dataModel = emptyTeacherLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(teacherID!==null&&teacherID!==''){
            
            if(from!==null&&from!==''){
                
                if(to!==null&&to!==''){
            
                    dataModel.teacherID =teacherID;
                    dataModel.from = from;
                    dataModel.to = to;
	           //Screen Specific Data Model Ends
	           var response = fncallBackend('TeacherLeaveManagement', 'View', dataModel, [{entityName:"teacherID",entityValue:teacherID}], $scope);
	
            }
        }
    }
                
        return true;
}
//Cohesive Query Framework Starts
function fnTeacherLeaveManagementQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //Screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.from = "";
	$scope.fromNoon = '';
	$scope.to = "";
	$scope.toNoon = '';
	$scope.type = "";
	$scope.reason = "";
        $scope.leaveStatus="";
	$scope.remarks = "";
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.fromReadOnly = false;
	$scope.toReadOnly = false;
        $( "#leaveFrom" ).datepicker( "option", "disabled", false );
        $( "#leaveTo" ).datepicker( "option", "disabled", false );
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
	$scope.typeReadOnly = true;
	$scope.reasonreadOnly = true;
	
	$scope.remarksReadOnly = true;
	// Screen Specific Scope Ends
	//Generic Field Starts
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
//Cohesive Query Framework Ends

//Cohesive View Framework Ends
function fnTeacherLeaveManagementView() {
	var emptyTeacherLeaveManagement = {
		teacherName: "",
		teacherID: "",
		from: "",
		fromNoon:'',
		to: "",
		toNoon: '',
		type: "",
		reason: "",
	};

    // Screen Specific Data Model Starts
	var dataModel = emptyTeacherLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!==null||$scope.teacherID!==''){
            
          if($scope.from!==null||$scope.from!==''){  
              
           if($scope.to!==null||$scope.to!==''){     
            
            dataModel.teacherID = $scope.teacherID;
            dataModel.from = $scope.from;
            dataModel.to = $scope.to;
            //Screen Specific Data Model Ends
            var response = fncallBackend('TeacherLeaveManagement', 'View', dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
        
           }
        }
    }
        
        return true;
}
//Cohesive View Framework Ends

function fnTeacherLeaveManagementMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			/*if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}*/
                        if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			if ($scope.from == '' || $scope.from == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
				return false;
			}
//			if ($scope.fromNoon == '' || $scope.fromNoon == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', [' From Noon Session']);
//				return false;
//			}
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
			if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}
                          if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			if ($scope.from == '' || $scope.from == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
				return false;
			}
			if ($scope.fromNoon == '' || $scope.fromNoon == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', [' From Noon Session']);
				return false;
			}
			if ($scope.to == '' || $scope.to == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['To Date']);
				return false;
			}
//			if ($scope.toNoon == '' || $scope.toNoon == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['To noon session']);
//				return false;
//			}
			if ($scope.type == '' || $scope.type == null || $scope.type == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Leave type']);
				return false;
			}
			if ($scope.reason == '' || $scope.reason == null || $scope.reason == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Leave Reason']);
				return false;
			}
			
			break;


	}
	return true;
}
//Function Level Default And Validation Starts
function fnTeacherLeaveManagementDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;
			
			if (!fnDatevalidation($scope))
				return false;
			break;


	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}


function fnDatevalidation($scope){
var leaveCurrentDate = new Date();
var leaveStartDate = $('#from').datepicker('getDate');
var leaveEndDate = $('#to').datepicker('getDate');
if (leaveStartDate<leaveCurrentDate)
{
	return fn_Show_Exception_With_Param('FE-VAL-025', ['Leave Management']);
	//return false;
}
if(leaveStartDate>leaveEndDate){
		return fn_Show_Exception_With_Param('FE-VAL-022', ['From date']);
		//return false;
}	
if(leaveEndDate<leaveStartDate){
	return fn_Show_Exception_With_Param('FE-VAL-023',['To Date']);
	//return false;
}
return true;
}
//Function Level Default Validation Ends

//Cohesive Create Framework Starts
function fnTeacherLeaveManagementCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.from = "";
	$scope.fromNoon = '';
	$scope.to = "";
	$scope.toNoon = '';
	$scope.type = '';
	$scope.reason = "";
        $scope.leaveStatus="";
	$scope.remarks = "";
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.fromReadOnly = false;
	$scope.toReadOnly = false;
        $( "#leaveFrom" ).datepicker( "option", "disabled", false );
        $( "#leaveTo" ).datepicker( "option", "disabled", false );
        $scope.fromNoonReadOnly = false;
	$scope.toNoonReadOnly = false;
	$scope.typeReadOnly = false;
	$scope.reasonReadOnly = false;
	
	$scope.remarksReadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Creation';
	//Generic Field Ends
	return true;
}
//Cohesive Create Framework Ends

//Cohesive Edit Framework Starts
function fnTeacherLeaveManagementEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    // Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        $scope.operation = 'Modification';
	// Generic Field Ends
    // Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
        $scope.fromNoonReadOnly = false;
	$scope.toNoonReadOnly = false;
	$scope.typeReadOnly = false;
	$scope.reasonReadOnly = false;
	$scope.remarksReadonly = false;
	
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnTeacherLeaveManagementDelete() {
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
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	$scope.remarksReadonly = true;
	
	//Screen Specific Scope Ends
	return true;
}
// Cohesive Delete Framework Ends
// Cohesive Authorisation Framework Starts
function fnTeacherLeaveManagementAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        $scope.operation = 'Authorisation';
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	$scope.remarksReadonly = false;
	
   	//Screen Specific Scope Ends
	return true;
}
// Cohesive Authorisation Framework Ends
// Cohesive Reject Framework Starts
function fnTeacherLeaveManagementReject() {
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
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $( "#leaveFrom" ).datepicker( "option", "disabled", true );
        $( "#leaveTo" ).datepicker( "option", "disabled", true );
        $scope.fromNoonReadOnly = true;
	$scope.toNoonReadOnly = true;
	$scope.typeReadOnly = true;
	$scope.reasonReadOnly = true;
	$scope.remarksReadonly = true;
	
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnTeacherLeaveManagementBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.teacherName= "";
		$scope.teacherID= "";
		$scope.from= "";
		$scope.fromNoon= '';
		$scope.to= "";
		$scope.toNoon= '';
		$scope.type= '';
		$scope.reason= "";
                $scope.leaveStatus="";
		$scope.remarks= "";
	}
	        $scope.teacherNamereadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
		$scope.teacherIDreadOnly = true;
		$scope.fromReadOnly = true;
		$scope.toReadOnly = true;
                $( "#leaveFrom" ).datepicker( "option", "disabled", true );
                $( "#leaveTo" ).datepicker( "option", "disabled", true );
                $scope.fromNoonReadOnly = true;
	        $scope.toNoonReadOnly = true;
		$scope.reasonReadOnly = true;
		$scope.typeReadOnly = true;
		$scope.remarksReadOnly = true;
		
		//Screen Specific Scope Ends
		// Generic Field Starts
                $scope.operation = '';
		$scope.mastershow = true;
	         $scope.detailshow = false;
                   $scope.auditshow = false;
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
	        //Generic Field Ends
}
//Cohesive Back Framework Ends
//cohesive Save Framework Starts
function fnTeacherLeaveManagementSave() {
	var emptyTeacherLeaveManagement = {
		teacherName: "",
		teacherID: "",
		from: null,
		fromNoon: '',
		to: null,
		toNoon: '',
		type: '',
		reason: null,
		remarks: null
	};
    //Screen Specifc DataModel Starts
	var dataModel = emptyTeacherLeaveManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
        if($scope.from!=null)
	dataModel.from = $scope.from;
        if($scope.fromNoon!=null)
	dataModel.fromNoon = $scope.fromNoon;
        if($scope.to!=null)
	dataModel.to = $scope.to;
        if($scope.toNoon!=null)
	dataModel.toNoon = $scope.toNoon;
        if($scope.teacherName!=null)
	dataModel.teacherName = $scope.teacherName;
        if($scope.type!=null)
	dataModel.type = $scope.type;
        if($scope.reason!=null)
	dataModel.reason = $scope.reason;

	//Screen Specifc DataModel Ends
	var response = fncallBackend('TeacherLeaveManagement', parentOperation, dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
//cohesive Save Framework Starts
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
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.from = $.datepicker.formatDate('dd-mm-yy', $("#leaveFrom").datepicker("getDate"));
	$scope.to = $.datepicker.formatDate('dd-mm-yy', $("#leaveTo").datepicker("getDate"));
        $scope.$apply();
}


function fnTeacherLeaveManagementpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.teacherNamereadOnly = true;
		$scope.teacherIDreadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
		$scope.fromReadOnly = true;
		$scope.toReadOnly = true;
                $( "#leaveFrom" ).datepicker( "option", "disabled", true );
                $( "#leaveTo" ).datepicker( "option", "disabled", true );
                $scope.fromNoonReadOnly = true;
	        $scope.toNoonReadOnly = true;
		$scope.reasonReadOnly = true;
		$scope.typeReadOnly = true;
		$scope.remarksReadOnly = true;
		
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.teacherName = "";
		$scope.teacherID ="";
		$scope.from ="";
                $scope.fromNoon ="";
                $scope.to ="";
                $scope.toNoon ="";
                $scope.reason ="";
                $scope.leaveStatus="";
                $scope.type="";
                $scope.remarks ="";
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                 $scope.teacherName = response.body.teacherName;
		$scope.teacherID = response.body.teacherID;
		$scope.from = response.body.from;
		$scope.fromNoon = response.body.fromNoon;
		$scope.to = response.body.to;
		$scope.toNoon = response.body.toNoon;
		$scope.type = response.body.type;
		$scope.reason = response.body.reason;
                $scope.audit = response.audit;
                
                if(response.audit.AuthStat==='Unauthorised'){
                
                    $scope.leaveStatus="Pending";
                }else{
                    
                    $scope.leaveStatus=response.audit.AuthStat;
                }
                
		// Specific Screen Scope Response Ends 
            }
            if (subscreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
           subScreen=false;
      }
		return true;

}

}