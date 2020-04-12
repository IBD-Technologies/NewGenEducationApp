/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, OperationScopes) {
	//Screen Specific Scope Starts
	$scope.groupID = "";
	$scope.groupDescription = "";
	$scope.circularDescription = "";
	$scope.circularID = "";
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.GroupMappingMaster = [{
		GroupId: "",
		GroupDescription: ""
	}];
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];

	$scope.CircularIdMaster = [{
		CircularId: "",
		CircularDescription: ""
	}];
        $scope.CircularType=Institute.CircularTypeMaster;
	$scope.contentPath = "";
        $scope.message = "";
        $scope.circularDate = "";
        $scope.circularType = "";
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.circularIdreadOnly = true;
	$scope.circularDescriptionreadOnly = true;
	$scope.contentPathreadOnly = true;
        $scope.messagereadOnly = true;
        $scope.circularTypereadOnly = true;
        $scope.circularDatereadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.instituteIdreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
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
	$scope.fnCircularSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Circular';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

	$scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
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
       MenuName = "ECircular";
       selectBypassCount=0;
       window.parent.nokotser=$("#nokotser").val();
       window.parent.Entity="Institute";
       fnDatePickersetDefault('circularDate',fndueDateEventHandler);
       fnsetDateScope();
//       fnECircularpostSelectBoxMaster();
       window.parent.fn_hide_parentspinner(); 
       selectBoxes= ['authStatus'];
        fnGetSelectBoxdata(selectBoxes);
        $("#circularImg").change(function(){ 
        $("#circularimgUpld").submit();

        
});  
$('#circularimgUpld').submit(function(event){
   $("#circular").attr("src","");
  fileUpload('#circular',$('#circularimgUpld')[0],$('#circularimgUpld').attr('action'),"ECircular");
  event.preventDefault();
    return true;
});   
});

function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.circularDate = $.datepicker.formatDate('dd-mm-yy', $("#circularDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.circularDate = $.datepicker.formatDate('dd-mm-yy', $("#circularDate").datepicker("getDate"));
		$scope.$apply();
}



function fnPostImageUpload(id,fileName,UploadID)
{
// $("#circular").attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
   
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       $scope.contentPath="/images/"+UploadID+"/"+fileName;	
       
       $scope.$apply();
//       $scope.ReportPath = response.body.ReportPath;
                
                fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.contentPath);
   
}



function fnECircularpostSelectBoxMaster(){
    
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if ((window.parent.ECircularSummarykey.circularID !=null && window.parent.ECircularSummarykey.circularID !='') )
	{
		var circularID=window.parent.ECircularSummarykey.circularID;
		
		 window.parent.ECircularSummarykey.circularID =null;
		 fnshowSubScreen(circularID);
                 //$ scope.$apply();
		
	}
}




function fnshowSubScreen(circularID)
{
    subScreen = true;
var emptyECircular = {

		instituteID: "",
		instituteName: "",
		month: "",
                year:'Select option',
		holiday:""
	};
	// Screen Specific DataModel Starts							 
	var dataModel = emptyECircular;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //if($scope.instituteID!=null &&$scope.instituteID!="")
         dataModel.instituteID=window.parent.Institute.ID;;
        if(circularID!=null && circularID!="")
	dataModel.circularID = circularID;
     
	// Screen Specific DataModel Ends
	var response = fncallBackend('ECircular', 'View', dataModel,[{
		entityName: "instituteID",
		entityValue: $scope.instituteID
	}],$scope);
	return true;
}

//Default Load Record Ends
//Cohesive query Framework Starts
function fnECircularQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.groupID = "";
	$scope.groupDescription = "";
	$scope.circularID = "";
	$scope.circularDescription = "";
	$scope.contentPath = "";
        $scope.message = "";
        $scope.circularDate = "";
        $scope.circularType = "";
        $("#circular").attr("src","");
         $("#circularImg").val(""); 
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.circularIdreadOnly = false;
	$scope.circularDescriptionreadOnly = true;
	$scope.contentPathreadOnly = true;
        $scope.messagereadOnly = true;
        $scope.circularTypereadOnly = true;
        $scope.circularDatereadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.instituteIdreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
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
function fnECircularView() {
	var emptyECircular = {
		instituteID: "",
		instituteName: "",
		groupID: "",
		groupDescription:"",
		circularID: "",
		circularDescription: "",
		contentPath: "",
                message: "",
                circularDate:"",
                circularType:""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyECircular;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.instituteID!==null&&$scope.instituteID!==""){
            
         if($scope.circularID!==null&&$scope.circularID!==""){    
           dataModel.instituteID = $scope.instituteID;  
	   dataModel.circularID = $scope.circularID;
  	   var response = fncallBackend('ECircular', 'View', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
       }
        
    }
        return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnECircularMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
//			if ($scope.groupID == '' || $scope.groupID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Group ID']);
//				return false;
//			}
			if ($scope.circularID == '' || $scope.circularID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Circular ID']);
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
//			if ($scope.groupID == '' || $scope.groupID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Group ID']);
//				return false;
//			}
//			if ($scope.groupDescription == '' || $scope.groupDescription == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Group Description']);
//				return false;
//			}
			if ($scope.circularID == '' || $scope.circularID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Circular ID']);
				return false;
			}
			if ($scope.circularDescription == '' || $scope.circularDescription == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Circular Description']);
				return false;
			}
			break;
	}
	return true;
}
//Screen Specific Mandatory Validation Ends
//Screen specific Default Validation Starts
function fnECircularDefaultandValidate(operation) {
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

function fnECircularDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultInstituteId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultInstituteId($scope))
				return false;

			break;
	}
	return true;
}

function fnDefaultInstituteId($scope) {
	var availabilty = false;
	return true;
}
function fnECircularDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultCircularId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultCircularId($scope))
				return false;

			break;
	}
	return true;
}
function fnDefaultCircularId($scope) {
	var availabilty = false;
	return true;
}

//Screen specific Default Validation Ends
//Cohesive Create Framework Starts
function fnECircularCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
//	$scope.instituteId = "";
//	$scope.instituteName = "";
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.circularDescription = "";
	$scope.circularID = "";
	$scope.groupDescription = "";
	$scope.groupID = "";
	$scope.contentPath = "";
        $scope.message = "";
        $scope.circularDate = "";
        $scope.circularType = "";
	$scope.groupIDreadOnly = false;
	$scope.groupDescriptionreadOnly = false;
	$scope.circularIdreadOnly = false;
	$scope.circularDescriptionreadOnly = false;
	$scope.contentPathreadOnly = false;
        $scope.messagereadOnly = false;
        $scope.circularTypereadOnly = false;
        $scope.circularDatereadOnly = false;
        $( "#circularDate" ).datepicker( "option", "disabled", false );
	$scope.instituteIdreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//Generic Field Ends	
        
        var emptyECircular = {
		groupID: "",
		groupDescription: "",
		circularDescription: "",
		circularID: "",
		circularDescription: "",
		instituteID: "",
		instituteName: "",
		contentPath: "",
                message: "",
                circularDate: "",
                circularType: ""
	};
        var dataModel = emptyECircular;
        
        var response = fncallBackend('ECircular', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnECircularEdit() {
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
	$scope.groupIDreadOnly = false;
	$scope.groupDescriptionreadOnly = false;
	$scope.circularIdreadOnly = true;
	$scope.circularDescriptionreadOnly = false;
	$scope.contentPathreadOnly = false;
        $scope.messagereadOnly = false;
        $scope.circularTypereadOnly = false;
        $scope.circularDatereadOnly = false;
        $( "#circularDate" ).datepicker( "option", "disabled", false );
	$scope.instituteIdreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	//screen Specific Scope Ends	
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnECircularDelete() {
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

	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.circularIdreadOnly = true;
	$scope.circularDescriptionreadOnly = true;
	$scope.contentPathreadOnly = true;
        $scope.messagereadOnly = true;
        $scope.circularTypereadOnly = true;
        $scope.circularDatereadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.instituteIdreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorization Framework Starts
function fnECircularAuth() {
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
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.circularIdreadOnly = true;
	$scope.circularDescriptionreadOnly = true;
	$scope.contentPathreadOnly = true;
        $scope.messagereadOnly = true;
        $scope.circularTypereadOnly = true;
        $scope.circularDatereadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.instituteIdreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Authorization Framework Ends
//Cohesive Reject Framework Starts
function fnECircularReject() {
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
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.circularIdreadOnly = true;
	$scope.circularDescriptionreadOnly = true;
	$scope.contentPathreadOnly = true;
        $scope.messagereadOnly = true;
        $scope.circularTypereadOnly = true;
        $scope.circularDatereadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.instituteIdreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework  Starts
function fnECircularBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.groupID = "";
		$scope.groupDescription = "";
		$scope.instituteID = "";
		$scope.instituteName = "";
		$scope.circularDescription = "";
		$scope.circularID = "";
		$scope.contentPath = "";
                $scope.message = "";
                 $scope.circularDate = "";
                 $scope.circularType = "";
                $("#circular").attr("src","");
                $("#circularImg").val(""); 
	}
	//Screen Specific Scope Starts
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.circularIdreadOnly = true;
	$scope.circularDescriptionreadOnly = true;
	$scope.contentPathreadOnly = true;
        $scope.messagereadOnly = true;
        $scope.circularTypereadOnly = true;
        $scope.circularDatereadOnly = true;
        $( "#circularDate" ).datepicker( "option", "disabled", true );
	$scope.instituteIdreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
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
function fnECircularSave() {
	var emptyECircular = {
		groupID: "",
		groupDescription: "",
		circularDescription: "",
		circularID: "",
		instituteID: "",
		instituteName: "",
		contentPath: "",
                message: "",
                circularDate:"",
                circularType:""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyECircular;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.groupID!=null)
	dataModel.groupID = $scope.groupID;
        if($scope.groupDescription!=null)
	dataModel.groupDescription = $scope.groupDescription;
        if($scope.circularDescription!=null)
	dataModel.circularDescription = $scope.circularDescription;
        if($scope.circularID!=null)
	dataModel.circularID = $scope.circularID;
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
         if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
       if($scope.contentPath!=null)
	dataModel.contentPath = $scope.contentPath;
     if($scope.message!=null)
	dataModel.message = $scope.message;
    if($scope.circularDate!=null)
	dataModel.circularDate = $scope.circularDate;
    if($scope.circularType!=null)
	dataModel.circularType = $scope.circularType;
	//Screen Specific DataModel Ends
	var response = fncallBackend('ECircular', parentOperation, dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	return true;
}

function fnECircularpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            if(response.header.operation == 'Create-Default')
		
		{
		  $scope.circularID = response.body.circularID;
//                  $scope.instituteId= response.body.instituteId;
//                  $scope.instituteName = response.body.instituteName;
		  return true;
		}
		else
		{
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.groupIDreadOnly = true;
		$scope.groupDescriptionreadOnly = true;
		$scope.circularIdreadOnly = true;
		$scope.circularDescriptionreadOnly = true;
		$scope.contentPathreadOnly = true;
                $scope.messagereadOnly = true;
                $scope.circularTypereadOnly = true;
                $scope.circularDatereadOnly = true;
                $( "#circularDate" ).datepicker( "option", "disabled", true );
		$scope.instituteIdreadOnly = true;
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
		$scope.groupID ="";
		$scope.groupDescription ="";
                $scope.circularID ="";
                $scope.circularDescription ="";
                $scope.contentPath ="";
                $scope.message ="";
                $scope.circularDate ="";
                $scope.circularType ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.groupID = response.body.groupID;
		$scope.groupDescription = response.body.groupDescription;
		$scope.circularID = response.body.circularID;
		$scope.circularDescription = response.body.circularDescription;
		$scope.contentPath = response.body.contentPath;
                $scope.message = response.body.message;
                $scope.circularDate = response.body.circularDate;
                $scope.circularType = response.body.circularType;
                
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = response.audit;
                
                if($scope.contentPath!=null&&$scope.contentPath!=''){
                
                 fnShowReport("/web/viewer.html?file="+"/CohesiveUpload"+$scope.contentPath);
                
                
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

}
