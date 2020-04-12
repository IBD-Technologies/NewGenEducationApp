//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen',['BackEnd','operation','search']);
   
  app.controller('SubScreenCtrl', function($scope,$compile,$timeout,appServerCallService,searchCallService,SeacrchScopeTransfer,OperationScopes) {
    //Screen Specific Scope Starts
    $scope.teacherName = "";
    $scope.teacherID = "";
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
        $scope.month="";
        $scope.year="Select option";
	$scope.path="";
	$scope.audit={};
	$scope.teacherMaster=[{TeacherId:null,TeacherName:null}];
	$scope.appServerCall=appServerCallService.backendCall;//This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
        $scope.Months = Institute.MonthMaster;
        $scope.Years = Institute.YearMaster;
	$scope.searchShow=false;
	//Generic Field Starts
	$scope.mastershow=true;
	$scope.detailshow=false;
	$scope.auditshow=false;
	$scope.teacherNamereadOnly=true;
        $scope.teacherNameSearchreadOnly=true;
	$scope.teacherIDreadOnly=true;
	$scope.monthreadOnly=true;
	$scope.yearreadOnly=true;
	$scope.pathreadOnly=true;
	$scope.MakerRemarksReadonly=true;
	$scope.CheckerRemarksReadonly=true;
	$scope.operation='';
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

	
$(document).ready(function(){
//
MenuName = "Payroll";	
window.parent.nokotser=$("#nokotser").val();
window.parent.Entity="Teacher";
window.parent.fn_hide_parentspinner();
 selectBoxes = ['notificationMonth', 'HolidayYear'];
 fnGetSelectBoxdata(selectBoxes);
	
//-------This is to Load default record--------------------------------------
});

function fnStudentAssignmentpostSelectBoxMaster(){
	
	 var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     var emptyPayroll = {
			
			   teacherID:"",
		           teacherName:"",
		           month:"",
                           year:"Select option",
		           path:""
			};

	var dataModel=emptyPayroll;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       //Screen Specific DataModel Starts
        if($scope.teacherID!=null)
        dataModel.teacherID =$scope.teacherID;
       //Screen Specific DataModel Ends
	var response =fncallBackend('Payroll','View',dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}],$scope);
}
//Query Framework Starts
function fnPayrollQuery()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
 	//Screen Specific Scope Starts
	$scope.teacherName ="";
        $scope.teacherID= "";
	$scope.month="";
        $scope.year="Select option";
	$scope.path="";
	$scope.teacherNamereadOnly=false;
        $scope.teacherNameSearchreadOnly=false;
	$scope.teacherIDreadOnly=false;
	$scope.monthreadOnly=false;
	$scope.yearreadOnly=false;
	$scope.pathreadOnly=false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.audit={};
	$scope.MakerRemarksReadonly=true;
	$scope.CheckerRemarksReadonly=true;	
	$scope.operation='View';
	$scope.mastershow=true;
	$scope.detailshow=false;
	$scope.auditshow=false;
        //Generic Field Ends
	return true;	
	}	
//Cohesive Query Frame work Ends	
//Cohesive View Framework starts
function fnPayrollView()
{
  var emptyPayroll = {
			
			   teacherID:"",
		           teacherName:"",
		           month:"Select option",
                           year:"Select option",
		           path:""
			};


        var dataModel=emptyPayroll;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null)
        dataModel.teacherID =$scope.teacherID;
        var response =fncallBackend('Payroll','View',dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}],$scope);
        return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory validation Starts      
function fnPayrollMandatoryCheck(operation)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    	
   switch(operation)
   {
     case 'View':   
              if ($scope.teacherID =='' || $scope.teacherID==null)
		      { 
                  fn_Show_Exception_With_Param('FE-VAL-001',['Teacher ID']);
			      return false;
			  }  
	         if ($scope.month =='' || $scope.month==null || $scope.month=='Select option')
		      { 
                  fn_Show_Exception_With_Param('FE-VAL-001',['Month']);
			      return false;
			  }  
                  if ($scope.year =='' || $scope.year==null || $scope.year=='Select option')
		      { 
                  fn_Show_Exception_With_Param('FE-VAL-001',['Year']);
			      return false;
			  }  
			  break;
	 
    case 'Save':   
       
			  
			  return true
			  break;   
   
   } 
 return true;    
 }
 //Screen Specific Mandartory Validation Ends
 //Screen Specific Default Validation Starts
function fnPayrollDefaultandValidate(operation) {
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
//Screen Specific Default Validation Ends	
//Cohesive Create Framework Starts
function fnPayrollCreate()
{
  
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Screen Specific Scope Starts
        $scope.teacherName ="";
        $scope.teacherID="";
	$scope.path="";  
        $scope.month="";
        $scope.year="Select option";
	$scope.teacherNamereadOnly=false;
        $scope.teacherNameSearchreadOnly=false;
	$scope.teacherIDreadOnly=false;
        $scope.pathreadOnly=false;
	$scope.monthreadOnly=false;
	$scope.yearreadOnly=false;
        //Screen Specific Scope Ends
        //Generic Field Starts	
	$scope.MakerRemarksReadonly=false;
	$scope.CheckerRemarksReadonly=true;
	$scope.mastershow=true;
	$scope.detailshow=false;
	$scope.auditshow=false;
	$scope.operation='Creation';
	//Generic Field Ends
return true;
}	
//Create framework Ends
//Edit Framework Starts
function fnPayrollEdit()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //Generic Field Starts
	$scope.MakerRemarksReadonly=false;
	$scope.CheckerRemarksReadonly=true;
	$scope.mastershow=true;
	$scope.detailshow=false;
 	$scope.auditshow=false;
        //Generic Field Ends	
	//Screen Specific Scope Starts
	$scope.teacherName=false;
	$scope.teacherID=true;
        $scope.teacherNameSearchreadOnly=true;
        $scope.monthreadOnly=false;
	$scope.yearreadOnly=false;
	$scope.pathreadOnly=false;
        $scope.operation='Modification';
    //Screen Specific Scope Ends
	return true;
}
// Edit Framework Ends
//Delete Framework Starts
function fnPayrollDelete()
{
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly=false;
	$scope.CheckerRemarksReadonly=true;
	$scope.mastershow=true;
	$scope.detailshow=false;
	$scope.auditshow=false;
        $scope.operation='Deletion';
	//Generic Field Ends
        //Screen Specific Scope Starts	
	$scope.teacherName=true;
        $scope.teacherNameSearchreadOnly=true;
	$scope.teacherID=true;
        $scope.monthreadOnly=true;
	$scope.yearreadOnly=true;
	$scope.pathreadOnly=true;
	 //Screen Specific Scope Ends	

return true;
}
//Delete Framework Ends
//Authorisation Framework Starts
function fnPayrollAuth()
{
   
	  var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
  
        //Generic Field Starts
	$scope.MakerRemarksReadonly=true;
	$scope.CheckerRemarksReadonly=false;
	$scope.mastershow=true;
	$scope.detailshow=false;
	$scope.auditshow=false;
	$scope.operation='Authorisation';
 	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.teacherNamereadOnly=true;
        $scope.teacherNameSearchreadOnly=true;
	$scope.teacherIDreadOnly=true;
        $scope.monthreadOnly=true;
	$scope.yearreadOnly=true;
	$scope.pathreadOnly=true;
	//screen Specific Scope ends 	
return true;
}
//Authorisation Framework Ends
//Reject Framework Starts
function fnPayrollReject()
{
  var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    //Generic Field Starts
	$scope.MakerRemarksReadonly=true;
	$scope.CheckerRemarksReadonly=false;
	$scope.mastershow=true;
	$scope.detailshow=false;
	$scope.auditshow=false;
	$scope.operation='Rejection';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.teacherNamereadOnly=true;
        $scope.teacherNameSearchreadOnly=true;
	$scope.teacherIDreadOnly=true;
        $scope.monthreadOnly=true;
	$scope.yearreadOnly=true;
	$scope.pathreadOnly=true;
	//Screen Specific Scope Ends	
return true;
}
//Reject Framework Ends
//Back Framework Starts
function fnPayrollBack()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	        $scope.audit = {};
		$scope.teacherName ="";
                $scope.teacherID="";
	        $scope.path="";  
                $scope.month="";
                $scope.year="Select option";
	}
	       // Screen Specific Scope Starts
		$scope.teacherNamereadOnly=true;
                $scope.teacherNameSearchreadOnly=true;
        	$scope.teacherIDreadOnly=true;
                $scope.monthreadOnly=true;
	        $scope.yearreadOnly=true;
	        $scope.pathreadOnly=true;
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.mastershow = true;
	        $scope.detailshow = false;
                  $scope.auditshow = false;
        // Generic Scope Ends	
}
//Back Framework Ends
//Save Framework Starts
function fnPayrollSave()
{
var emptyPayroll = {
			
			   teacherID:"",
		           teacherName:"",
		           month:"Select option",
                           year:"Select option",
		           path:""
		};
        var dataModel=emptyPayroll;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific DataModel Starts
        if($scope.teacherName!=null)
        dataModel.teacherName =$scope.teacherName;
        if($scope.teacherID!=null)
	dataModel.teacherID =$scope.teacherID;
        if($scope.month!=null)
	dataModel.month=$scope.month;
        if($scope.year!=null)
	dataModel.year=$scope.year;
        if($scope.path!=null)
	dataModel.path=$scope.path;
        //Screen specific DataModel Ends   
        var response =fncallBackend('Payroll',parentOperation,dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}],$scope);
return true;
}
//Save Framework Ends
function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#payRoleDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#payRoleDate").datepicker("getDate"));
		$scope.$apply();
}	


function fnPayrollpostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
         
		// Specific Screen Scope Starts
		$scope.teacherIDreadOnly=true;
	        $scope.teacherNamereadOnly=true;
                $scope.teacherNameSearchreadOnly=true;
	        $scope.monthreadOnly=true;
	        $scope.yearreadOnly=true;
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
		$scope.date ="";
                $scope.path ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.teacherName = response.body.teacherName;
		$scope.teacherID = response.body.teacherID;
		$scope.month = response.body.month;
                $scope.year = response.body.year;
		$scope.path = response.body.path;
		// Specific Screen Scope Response Ends 
                }
		return true;
}
}
