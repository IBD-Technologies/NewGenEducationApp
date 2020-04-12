/* 
    Author     : IBD Technologies
	
*/

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var dateSelect=false;
var backEndQuerydone=false;
var detailClick=false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, OperationScopes) {

	// Specific Screen Scope Starts
	$scope.instituteName = "";
	$scope.instituteID = "";
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.month = "";
	$scope.year = "Select option";
        $scope.holiday="WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
        $( "#Holiday" ).datepicker( "option", "disabled", true );
	$scope.Months = Institute.MonthMaster;
	$scope.Years = Institute.YearMaster;
	$scope.searchShow = false;
	// Specific Screen Scope Ends
	// Generic Field starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service
	$scope.OperationScopes = OperationScopes;
	$scope.operation = '';
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.monthreadOnly = true;
	$scope.yearreadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
	// Screen Specific Scope Ends
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

//-------This is to Load default record Starts--------------------------------------	
$(document).ready(function () {
	MenuName = "HolidayMaintenance";
       
        $('#dummyHoliday').val('WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW');
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "Institute";
	window.parent.fn_hide_parentspinner();
	//fnDatePickersetDefault('Holiday', fnHolidayMaintenanceHandler);
	//();
        selectBoxes = ['notificationMonth', 'HolidayYear'];
        fnGetSelectBoxdata(selectBoxes);
	
	//-------This is to Load default record Ends--------------------------------------

});


function fnHolidayMaintenancepostSelectBoxMaster(){
    
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      $scope.holiday="WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
    if ((window.parent.HolidayMaintenanceSummarykey.month !=null && window.parent.HolidayMaintenanceSummarykey.month !='') && (window.parent.HolidayMaintenanceSummarykey.year !=null && window.parent.HolidayMaintenanceSummarykey.year !=''))
	{
		var month=window.parent.HolidayMaintenanceSummarykey.month;
		var year=window.parent.HolidayMaintenanceSummarykey.year;
		
		 window.parent.HolidayMaintenanceSummarykey.month =null;
		 window.parent.HolidayMaintenanceSummarykey.year =null;
		 fnshowSubScreen(month,year);
                 //$ scope.$apply();
		
	}
}
function fnshowSubScreen(month,year)
{
var emptyHolidayMaintenance = {

		instituteID: "",
		instituteName: "",
		month: "",
                year:'Select option',
		holiday:""
	};
	// Screen Specific DataModel Starts							 
	var dataModel = emptyHolidayMaintenance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //if($scope.instituteID!=null &&$scope.instituteID!="")
         dataModel.instituteID=window.parent.Institute.ID;;
        if(month!=null && month!="")
	dataModel.month = month;
        if(year!=null && year!="")
	dataModel.year = year;
     
	// Screen Specific DataModel Ends
	var response = fncallBackend('HolidayMaintenance', 'View', dataModel,[{
		entityName: "instituteID",
		entityValue: $scope.instituteID
	}],$scope);
	return true;
}
// Cohesive Query Framework Starts
function fnHolidayMaintenanceQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.month = "";
        backEndQuerydone=false;
	$scope.year = "Select option";
	$scope.instituteIDreadOnly = false;
	$scope.instituteNameSearchreadOnly = false;
	$scope.instituteNamereadOnly = false;
	$scope.monthreadOnly = false;
	$scope.yearreadOnly = false;
	// Screen Specific Scope Starts
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        //$( "#Holiday" ).datepicker( "option", "disabled", true );
	$scope.audit = {};
	// Generic Field Ends
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts	
function fnHolidayMaintenanceView() {
	var emptyHolidayMaintenance = {

		instituteID: "",
		instituteName: "",
		month: "",
		year: 'Select option',
		holiday: ""
	};
	// Screen Specific DataModel Starts							 
	var dataModel = emptyHolidayMaintenance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.instituteID!=null &&$scope.instituteID!="")
	   dataModel.instituteID = $scope.instituteID;
        if($scope.month!=null && $scope.month!="" &&$scope.month!="Select option")
	   dataModel.month = $scope.month;
        if($scope.year!=null && $scope.year!="" &&$scope.year!="Select option")
	   dataModel.year = $scope.year;
        
	// Screen Specific DataModel Ends
	var response = fncallBackend('HolidayMaintenance', 'View', dataModel, [{
		entityName: "instituteID",
		entityValue: $scope.instituteID}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts	  
function fnHolidayMaintenanceMandatoryCheck(operation) {
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
			if ($scope.month == '' || $scope.month == null || $scope.month == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Month']);
				return false;
			}
			if ($scope.year == '' || $scope.year == null || $scope.year == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Year']);
				return false;
			}
			break;

		case 'Save':

			if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
			if ($scope.month == '' || $scope.month == null || $scope.month == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Month']);
				return false;
			}
			if ($scope.year == '' || $scope.year == null || $scope.year == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Year']);
				return false;
			}
			break;
	}
	return true;
}

function fnHolidayMaintenanceDefaultandValidate(operation) {
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

function fnHolidayMaintenanceDetailClick($scope)
{
   
    //var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if ($scope.month == '' || $scope.month == null || $scope.month == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Month']);
				return false;
			}
     if ($scope.year == '' || $scope.year == null || $scope.year == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Year']);
				return false;
			}
     if($scope.operation=='View'||$scope.operation=='Deletion')
     {
        if(backEndQuerydone)
        { $scope.mastershow=false; 
          $scope.detailshow=true;
          $scope.auditshow=false;
           }
        else
        {   
         $scope.detailshow=false;
         $scope.mastershow=true; 
         $scope.auditshow=false;
        }
     
        return true;
    }
    if(!dateSelect)
    fnHolidayCalendersetDefault('Holiday',$scope.month,$scope.year,fnHolidayMaintenanceHandler);
   
    if($scope.operation=='Creation' || $scope.operation=="Modification")
    {
        if($scope.operation=='Creation')
          detailClick=true;  
     
       $( "#Holiday" ).datepicker( "option", "disabled", false );
       $scope.detailshow=true;
       return true;
      }
    else
    {
        $( "#Holiday" ).datepicker( "option", "disabled", true );
        if(backEndQuerydone)
         $scope.detailshow=true;
        return true;
    }
    
    
}

function setHolidays(date)
{
  var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      var day = date.getDay();
      var hol;
    String.prototype.replaceAt=function(index, replacement) {
    return this.substr(0, index) + replacement+ this.substr(index + replacement.length);
}	
 
      if(date.getMonth()+1==$scope.month && date.getFullYear()==$scope.year)
      {
      if(day == 0 || day == 6)
      {   
        //if($scope.holiday=="WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW")
        if(!dateSelect)
        {   
        hol= $scope.holiday.replaceAt(date.getDate()-1, "H");
        $scope.holiday=hol;
        $scope.$apply();
        hol="";
          return [ true, 'holiday red', 'WeekEnd!' ];
          }
       }
    }
     if($scope.holiday.charAt(date.getDate()-1)=="W")
     {  
        return [ true, 'workingday green', 'Working Day!' ];
     }
     else if($scope.holiday.charAt(date.getDate()-1)=="H")
     {  
         return [ true, 'holiday red', 'Holiday!' ];
     }
      else if($scope.holiday.charAt(date.getDate()-1)=="F")
     {  
         return [ true, 'holiday yellow', 'HalfDay-ForeNoon!' ];
     }
     
      else if($scope.holiday.charAt(date.getDate()-1)=="A")
     {  
         return [ true, 'holiday blue', 'HalfDay-AfterNoon!' ];
     }
     
     
}

// Cohesive Create Framework Starts
function fnHolidayMaintenanceCreate() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.month = "";
	$scope.year = "Select option";
        dateSelect=false;
	$scope.holiday = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
        $scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
	$scope.instituteNameSearchreadOnly = false;
	$scope.monthreadOnly = false;
	$scope.yearreadOnly = false;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//$scope.audit = {};
	// Generic Field Ends
	return true;
}
// Cohesive Create Framework Ends

// Cohesive Edit Framework Starts
function fnHolidayMaintenanceEdit() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
        dateSelect=true;
	//Generic Field Starts
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.yearreadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Edit Framework Ends
// Cohesive Notification Framework Starts
function fnHolidayMaintenanceDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
        dateSelect=true;
	// Generic Field Ends
	// screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.yearreadOnly = true;
	// screen Specific Scope Ends
	return true;
}
// Cohesive HolidayMaintenance Framework Ends

// Cohesive Authorisation Framework Starts
function fnHolidayMaintenanceAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// generic field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
        backEndQuerydone=false;
	$scope.operation = 'Authorisation';
        dateSelect=true;
	// Generic field Ends

	// screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.yearreadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Authorisation Framework Ends

// Cohesive Reject Framework Starts
function fnHolidayMaintenanceReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
        dateSelect=true;
	// Generic Field Ends

	//Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.monthreadOnly = true;
	$scope.yearreadOnly = true;
	//Screen Specific Scope Starts
	return true;
}
// Cohesive Reject Framework Ends

// Cohesive Back Framework Starts
function fnHolidayMaintenanceBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        dateSelect=false;
	// Screen Specific Scope Starts
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
                backEndQuerydone=false; 
		$scope.instituteID = "";
		$scope.instituteName = "";
		$scope.month = "";
		$scope.year = "Select option";
		$scope.holiday = "";
	}
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
	$scope.monthreadOnly = true;
	$scope.yearreadOnly = true;
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
// Cohesive save Framework Starts
function fnHolidayMaintenanceSave() {
	var emptyHolidayMaintenance = {

		instituteID: "",
		instituteName: "",
		month: "",
		year: 'Select option',
		holiday: ""
	};
	// Screen Specific DataModel Starts									 
	var dataModel = emptyHolidayMaintenance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
       if($scope.month!=null)
	dataModel.month = $scope.month;
         if($scope.year!=null)
	dataModel.year = $scope.year;
        if($scope.holiday!=null)
          dataModel.holiday = $scope.holiday;  
	// Screen Specific DataModel Ends
          if(!detailClick && $scope.operation=="Creation")
          {
              fn_Show_Exception('FE-VAL-035');
				return false;
			
          }
          else
          {
           dateSelect=true;
       }
	var response = fncallBackend('HolidayMaintenance', parentOperation, dataModel, [{
		entityName: "instituteID",
		entityValue: $scope.instituteID}
           ], $scope);
	return true;
}

// Cohesive save Framework Ends
function fnHolidayMaintenanceHandler() {
	//var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.month = $.datepicker.formatDate('dd-mm-yy', $("#Holiday").datepicker("getDate"));
	//$scope.$apply();
      dateSelect=true;  
    fnsetDateScope();
    
    return true;
}

function fnsetDateScope() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//var d = $.datepicker.formatDate('dd-mm-yy', $("#Holiday").datepicker("getDate"));
var d = $("#Holiday").datepicker("getDate");
var hol;
    String.prototype.replaceAt=function(index, replacement) {
    return this.substr(0, index) + replacement+ this.substr(index + replacement.length);
}	
    if($scope.holiday.charAt(d.getDate()-1)=="W")
        {
       hol= $scope.holiday.replaceAt(d.getDate()-1, "H");  
        }
     else if($scope.holiday.charAt(d.getDate()-1)=="H")
     {
       hol= $scope.holiday.replaceAt(d.getDate()-1, "F");  
        }
     else if($scope.holiday.charAt(d.getDate()-1)=="F")
     {
       hol= $scope.holiday.replaceAt(d.getDate()-1, "A");  
        }
      else if($scope.holiday.charAt(d.getDate()-1)=="A")
     {
       hol= $scope.holiday.replaceAt(d.getDate()-1, "W");  
        }   
       
    $scope.holiday=hol;
        $scope.$apply();
}

function fnHolidayMaintenancepostBackendCall(response) {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   dateSelect=true;
   detailClick=false;
	if (response.header.status == 'success') {

               
		// Specific Screen Scope Starts
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.instituteNameSearchreadOnly = true;
		$scope.monthreadOnly = true;
		$scope.yearreadOnly = true;
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if (parentOperation == "Delete") {
			$scope.instituteID = "";
			$scope.instituteName = "";
			$scope.month = "";
			$scope.year = "Select option";
                        $scope.mastershow = true;
		        $scope.detailshow = false;
                        $scope.holiday="";
                        //$('#Holiday').empty();
                      //  $("#Holiday").datepicker("hide");
			$scope.audit = {};
		} else {
			$scope.instituteID = response.body.instituteID;
			$scope.instituteName = response.body.instituteName;
			$scope.month = response.body.month;
			$scope.year = response.body.year;
                        $scope.holiday=response.body.holiday;
                        $scope.audit =response.audit;
			// Screen Specific scope Response Ends
		}
                backEndQuerydone=true;
                 fnHolidayCalendersetDefault('Holiday',$scope.month,$scope.year,fnHolidayMaintenanceHandler);
   
         if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen=false;
	 }
                
		return true;

	}

}