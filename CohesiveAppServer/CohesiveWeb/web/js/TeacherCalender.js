/* 
    Author     : IBD Technologies
	
*/

//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);

app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
    //Screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.month = "";
	$scope.events = "";
	$scope.audit = {};
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
        //Screen Specific Scope Ends
	//Generic Field Starts
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
        //Generic Field Ends	
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
	MenuName = "TeacherCalender";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="Teacher";
         window.parent.fn_hide_parentspinner();
	 fnCalendersetDefault('teacherCalender', fnTeacherCalenderHandler);
	 fnsetDateScope();
	
     
});
//----------------------   Default Load Record Ends ---------------------------------------------------	


function fnTeacherCalenderpostSelectBoxMaster(){
  var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
 var emptyTeacherCalender = {
		teacherName: "",
		teacherID: "",
		date: "",
		month: "",
		events: ""
	};
	var dataModel = emptyTeacherCalender;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null && $scope.teacherID!=""){
	dataModel.teacherID = $scope.teacherID;
	var response = fncallBackend('TeacherCalender', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);

        }
}
//Cohesive query Framework Starts
function fnTeacherCalenderQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.date = "";
	$scope.month = "";
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	//screen Specific scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        //Generic Field Ends
	return true;
}
//Cohesive query Framework Ends
//Cohesive View Framework Starts
function fnTeacherCalenderView() {
	var emptyTeacherCalender = {
		teacherName: "",
		teacherID: "",
		date: "",
		month: "",
		events: ""
	};
    //Screen Specific DataModel Starts
	var dataModel = emptyTeacherCalender;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;	
	//Screen Specific DataModel Ends
	var response = fncallBackend('TeacherCalender', 'View', dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnTeacherCalenderMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
                       if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			/*if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}*/
                       
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


			break;


	}
	return true;
}
//Screen Specific Default Valifation Starts
function fnTeacherCalenderDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}
//Screen Specific Default Valifation Starts
//Cohesive Create Framework Starts
function fnTeacherCalenderCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.events = "";
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
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
//Cohesive Edit Framework starts
function fnTeacherCalenderEdit(){
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
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnTeacherCalenderDelete() {
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
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
        //Screen Specific Scope Ends	
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnTeacherCalenderAuth() {

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
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
        //Screen Specific Scope Ends	
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnTeacherCalenderReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        //Generic Field Ends
	//Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.operation = 'Rejection';
        //Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnTeacherCalenderBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	$scope.audit = {};
	$scope.teacherName = "";
	$scope.teacherID = "";
	}
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	// Screen Specific Scope Ends
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
function fnTeacherCalenderSave() {
	var emptyTeacherCalender = {
		teacherName: "",
		teacherID: "",
		date: "",
		month: "",
		events: ""
	};
    //Screen Specific DataModel Starts
	var dataModel = emptyTeacherCalender;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel.teacherID = $scope.teacherID;
        dataModel.teacherName = $scope.teacherName;
	dataModel.events = $scope.events;
	//Screen Specific DataModel Ends
	var response = fncallBackend('TeacherCalender', parentOperation, dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
//Cohesive Save Framework Ends
function fnTeacherCalenderHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.month = $.datepicker.formatDate('dd-mm-yy', $("#teacherCalender").datepicker("getDate"));
	$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.month = $.datepicker.formatDate('dd-mm-yy', $("#teacherCalender").datepicker("getDate"));
		$scope.$apply();
}	
function fnTeacherCalenderpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
		$scope.teacherNamereadOnly = true;
		$scope.teacherIDreadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
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
		$scope.month ="";
                $scope.events ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.teacheName = response.body.teacheName;
		$scope.teacherID = response.body.teacherID;
		$scope.events = response.body.events;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
                }
		return true;

}
}