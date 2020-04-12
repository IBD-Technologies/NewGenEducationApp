 //------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	//Generic Field Starts
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field 
	//Screen Specific Scope starts
	$scope.feeType = 'Select option';
	$scope.groupID = "";
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.feeID = "";
        $scope.feeDescription = "";
	$scope.instituteID ="";
        $scope.instituteName ="";
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
	$scope.remarks = "";
	$scope.fees = Institute.FeeMaster;
	$scope.PayMentMaster = Institute.PayMentMaster;
	$scope.feeTypereadOnly = true;
        $scope.feeIDSearchReadOnly = true;
	$scope.feeIDReadOnly = true;
        $scope.feeDescriptionreadOnly=true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#CFDueDate" ).datepicker( "option", "disabled", true );
	$scope.remarksReadonly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	$scope.fnFeeSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
			
		};
	    searchCallInput.mainScope = $scope;
	    searchCallInput.searchType = 'Fee';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	$scope.fnGroupingSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
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
	/*$scope.fnAssigneeSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Assignee';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}*/
	

});
//--------------------------------------------------------------------------------------------------------------

//Defaault Load Record Starts
$(document).ready(function () {
	MenuName = "InstituteFeeManagement";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Institute";
	fnDatePickersetDefault('CFDueDate',fndueDateEventHandler);
        fnsetDateScope();
        selectBoxes= ['feeType'];
        fnGetSelectBoxdata(selectBoxes);
	
 $('.currency').blur(function()
                {
                    $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
                });



});
//Default Load Record Ends

function fnInstituteFeeManagementpostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
    
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.FeeMaster.length>0)
      {   
	 $scope.fees = Institute.FeeMaster;
	  window.parent.fn_hide_parentspinner();
         if (window.parent.InstituteFeeManagementkey.feeID !=null && window.parent.InstituteFeeManagementkey.feeID !='')
	{
		var feeID=window.parent.InstituteFeeManagementkey.feeID;
		
		 window.parent.InstituteFeeManagementkey.feeID =null;
		
		fnshowSubScreen(feeID);
		
	} 
         $scope.$apply();
      }   
  }
}
function fnshowSubScreen(feeID)
{
       subScreen = true;
	var emptyInstituteFeeManagement = {
		feeType: "Select option",
		instituteID: "",
		instituteName: "",
		feeID: "",
                feeDescription:"",
		groupID:"",
		amount: "",
		dueDate: "",
		remarks: ""
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstituteFeeManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.feeID=feeID;
        if($scope.feeID!==null&&$scope.feeID!==""){
            
            dataModel.feeID = $scope.feeID;
            dataModel.instituteID=window.parent.Institute.ID;
            // Screen Specific DataModel Ends
            var response = fncallBackend('InstituteFeeManagement', 'View', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
        }
        return true;
}
//Cohesive Query Framework Starts
function fnInstituteFeeManagementQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.feeType = "Select option";
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.feeID = "";
        $scope.feeDescription = "";
	$scope.groupID = "";
//	$scope.instituteID = "";
//	$scope.instituteName = "";
	$scope.feeIDReadOnly = false;
	$scope.feeTypereadOnly = true;
        $scope.feeIDSearchReadOnly = false;
        $scope.feeDescriptionreadOnly=true;
 	$scope.remarksReadonly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadOnly = true;
        $( "#CFDueDate" ).datepicker( "option", "disabled", true );
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
        //Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
    //Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnInstituteFeeManagementView() {
	var emptyInstituteFeeManagement = {
		feeType: 'Select option',
		instituteID: "",
		instituteName: "",
		feeID: "",
                feeDescription:"",
		groupID: "",
		amount: "",
		dueDate: "",
		remarks: ""
	};
        //Screen Specific DataModel Starts
	var dataModel = emptyInstituteFeeManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.feeID!==null&&$scope.feeID!==""){
             dataModel.feeID = $scope.feeID;
             dataModel.instituteID = $scope.instituteID;
            //Screen Specific DataModel Ends 
            var response = fncallBackend('InstituteFeeManagement', 'View', dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}],$scope);
        
    }
	return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnInstituteFeeManagementMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
	     	if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			if ($scope.feeID == '' || $scope.feeID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee ID']);
				return false;
			}

			/*if ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Type']);
				return false;
			}
				if ($scope.groupID == '' || $scope.groupID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Group ID']);
				return false;
			}*/
			break;

		case 'Save':
		    if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
			if ($scope.feeID == '' || $scope.feeID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee ID']);
				return false;
			}
                        
                        if ($scope.feeDescription == '' || $scope.feeDescription == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Description']);
				return false;
			}

			if ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Type']);
				return false;
			}
			if ($scope.amount == '' || $scope.amount == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Amount']);
				return false;
			}
				if ($scope.groupID == '' || $scope.groupID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Group ID']);
				return false;
			}
			
			if ($scope.dueDate == '' || $scope.dueDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Due Date']);
				return false;
			}
			break;


	}
	return true;
}
//Screen Specific Default Validation Ends
//Screen Specific Default Validation Starts
function fnInstituteFeeManagementDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultFeeId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultFeeId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultFeeId($scope) {
	var availabilty = false;
	return true;
}

function fnInstituteFeeManagementDefaultandValidate(operation) {
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
//Screen Specific Default Validation Ends
//Cohesive Query Framework Starts
function fnInstituteFeeManagementCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Start
	$scope.feeType = 'Select option';
	$scope.amount = "";
	$scope.feeID = "";
        $scope.feeDescription = "";
	$scope.remarks = "";
	$scope.dueDate = "";
	$scope.groupID = "";
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.fees = Institute.FeeMaster;
	$scope.feeTypereadOnly = false;
	$scope.feeIDReadOnly = false;
        $scope.feeIDSearchReadOnly = true;
        $scope.feeDescriptionreadOnly=false;  
	$scope.amountReadOnly = false;
	$scope.dueDateReadonly = false;
        $( "#CFDueDate" ).datepicker( "option", "disabled", false );
	$scope.remarksReadonly = false;
	$scope.groupIDReadOnly = false;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	$scope.ClassSection = ['Select option']; // Std/sec
        var emptyInstituteFeeManagement = {
		feeType: 'Select option',
		instituteID: "",
		instituteName: "",
		feeID: "",
                feeDescription:"",
		amount: "",
		dueDate: "",
		remarks: "",
		groupID:""
	};
        
        
        var dataModel = emptyInstituteFeeManagement;
	
        var response = fncallBackend('InstituteFeeManagement', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
        //Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive Edit Framework Starts
function fnInstituteFeeManagementEdit() {
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
	$scope.feeTypereadOnly = false;
	$scope.amountReadOnly = false;
	$scope.dueDateReadonly = false;
        $( "#CFDueDate" ).datepicker( "option", "disabled", false );
	$scope.feePaidReadOnly = false;
	$scope.feeIDReadOnly = true;
        $scope.feeDescriptionreadOnly=false;
	$scope.remarksReadonly = false;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
          $scope.feeIDSearchReadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
function fnInstituteFeeManagementDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
        //Generic Field Ends
	//Screen Specific Scope Starts
	$scope.feeTypereadOnly = true;
	$scope.feeIDReadOnly = true;
	$scope.amountReadOnly = true;
	$scope.remarksReadonly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
        $scope.feeIDSearchReadOnly = true;
        $scope.feeDescriptionreadOnly=true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit framework Ends
//Cohesive AuthoriZation framework Starts
function fnInstituteFeeManagementAuth() {

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
	$scope.feeTypereadOnly = true;
	$scope.feeIDReadOnly = true;
        $scope.feeDescriptionreadOnly=true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#CFDueDate" ).datepicker( "option", "disabled", true );
	$scope.remarksReadonly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
        $scope.feeIDSearchReadOnly = true;
        $scope.amount=$('#feeAmount').toNumber({region: 'en-IN'}).val();
	//Screen Specific Scope Ends
	return true;
}
//Cohesive AuthoriZation framework Ends
//Cohesive Reject framework Starts
function fnInstituteFeeManagementReject() {
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
	$scope.feeTypereadOnly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#CFDueDate" ).datepicker( "option", "disabled", true );
	$scope.feeIDReadonly = true;
        $scope.feeDescriptionreadOnly=true;
	$scope.remarksReadonly = true;
	$scope.groupIDReadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
          $scope.feeIDSearchReadOnly = true;
          $scope.amount=$('#feeAmount').toNumber({region: 'en-IN'}).val();
    //Screen Specific Scope Ends
	return true;
}
//Cohesive Reject framework Ends
//Cohesive Back framework Starts
function fnInstituteFeeManagementBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
                $scope.audit = {};
	        $scope.feeType = 'Select option';
	        $scope.amount = "";
	        $scope.feeID = "";
                $scope.feeDescription = "";
	        $scope.remarks = "";
	        $scope.dueDate = "";
	        $scope.groupID = "";
	        $scope.instituteID = "";
	        $scope.instituteName = "";
	}
		$scope.feeTypereadOnly = true;
		$scope.amountReadOnly = true;
		$scope.dueDateReadonly = true;
                $( "#CFDueDate" ).datepicker( "option", "disabled", true );
		$scope.remarksReadonly = true;
		$scope.feeIDReadOnly = true;
                $scope.feeDescriptionreadOnly=true;
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.groupIDReadOnly = true;
		$scope.instituteIDreadOnly = true;
	        $scope.instituteNamereadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
                $scope.feeIDSearchReadOnly = true;
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
//Cohesive Back framework Ends
//Cohesive Save framework Starts
function fnInstituteFeeManagementSave() {
	var emptyInstituteFeeManagement = {
		feeType: 'Select option',
		instituteID: "",
		instituteName: "",
		feeID: "",
                feeDescription:"",
		amount: "",
		dueDate: "",
		remarks: "",
		groupID:""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyInstituteFeeManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.feeID!=null)
	dataModel.feeID = $scope.feeID;
       if($scope.feeDescription!=null)
	dataModel.feeDescription = $scope.feeDescription;
        if($scope.groupID!=null)
	dataModel.groupID = $scope.groupID;
        if($scope.feeType!=null)
	dataModel.feeType = $scope.feeType;
        if($scope.amount!=null)
            dataModel.amount = $scope.amount;
        if($scope.dueDate!=null)
	dataModel.dueDate = $scope.dueDate;
        if($scope.remarks!=null)
	dataModel.remarks = $scope.remarks;
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
	//Screen Specific DataModel Ends
        
        dataModel.amount=$('#feeAmount').toNumber({region: 'en-IN'}).val();
         
        
	var response = fncallBackend('InstituteFeeManagement', parentOperation, dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}],$scope);
	return true;
}
//Cohesive Save framework Ends
function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#CFDueDate").datepicker("getDate")),
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#CFDueDate").datepicker("getDate"));
		$scope.$apply();
}	

function fnInstituteFeeManagementpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            if(response.header.operation == 'Create-Default')
		
		{
		  $scope.feeID = response.body.feeID;
                 if(response.body.instituteId!=null && response.body.instituteId!="")
                  $scope.instituteId= response.body.instituteId;
                 if(response.body.instituteName!=null && response.body.instituteName!="")
                  $scope.instituteName = response.body.instituteName;
		  return true;
		}
		else
		{
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.feeTypereadOnly = true;
		$scope.amountReadOnly = true;
		$scope.dueDateReadonly = true;
                $( "#CFDueDate" ).datepicker( "option", "disabled", true );
		$scope.remarksReadonly = true;
		$scope.feeIDReadOnly = true;
		$scope.groupIDReadOnly = true;
		$scope.instituteIDreadOnly = true;
	        $scope.instituteNamereadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
                $scope.feeIDSearchReadOnly = true;
                $scope.feeDescriptionreadOnly=true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.instituteID = "";
		$scope.instituteName ="";
		$scope.feeType ="";
                $scope.groupID ="";
                $scope.feeID ="";
                $scope.feeDescription = "";
                $scope.amount ="";
                $scope.dueDate ="";
                $scope.remarks ="";
                $scope.status ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.feeID = response.body.feeID;
		$scope.groupID = response.body.groupID;
		$scope.feeType = response.body.feeType;
                $scope.feeDescription = response.body.feeDescription;
		//$scope.amount = response.body.amount;
                
                $('#feeAmount').val(response.body.amount);
                $('#feeAmount').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.amount=$('#feeAmount').val();
                
                
                
                
                $scope.dueDate = response.body.dueDate;
		$scope.remarks = response.body.remarks;
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.status = response.body.status;
                $scope.audit=response.audit;
		//Screen Specific Response Scope Ends
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

}