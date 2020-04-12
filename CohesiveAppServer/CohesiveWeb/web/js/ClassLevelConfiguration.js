/* 
    Author     : IBD Technologies

 */ 

var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
var selectBypassCount=0;
var subScreen=false;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {	
	// Generic Field Starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
	$scope.svwAddDeteleDisable = true; // single View
	$scope.mvwAddDeteleDisable = true; // Multiple View
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
        $scope.Hour = Institute.HourMaster;
        $scope.Minutes = Institute.MinMaster;
        $scope.AttendanceNoonType=Institute.AttendanceNoonMaster;
        $scope.AttendanceType=Institute.AttendanceMaster;
	// Generic FielD Ends

	// Screen Specfic Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.instituteNamereadOnly = true;
	$scope.instituteSearchreadOnly = true;
	$scope.classReadonly = true;
        $scope.teacherNamereadOnly=true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly=true;
	$scope.periodNumberreadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.noonreadOnly = true;
        $scope.attendancereadOnly = true;
	$scope.instituteName = "";
	$scope.instituteID = "";
        $scope.class="";
        $scope.teacherID="";
        $scope.teacherName="";
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.Hour = Institute.HourMaster;
	$scope.Minutes = Institute.MinMaster;
	// Screen Specific Scope Ends	
	// Single View Starts
	$scope.periodTimingsRecord = {
					idx: 0,
					//class:"",
					periodNumber: "",
                                        noon:"",
					startTime: {hour:"Hour",min:"Min"},
					endTime:{hour:"Hour",min:"Min"}	
	
	};
	$scope.periodTimingsTable = null;
	$scope.periodTimingsCurIndex = 0;
	$scope.periodTimingsShow = false;
	// Single View Ends	
	// multiple View Starts
	$scope.standardMasterCurPage = 0;
	$scope.standardMasterTable = null;
	$scope.standardMasterShowObject = null;
	// Multiple View Ends
	//Scope Level Function Starts
	//Scope Levle Single View functions starts 
	$scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'periodTimings') {
			if ($scope.periodTimingsTable != null && $scope.periodTimingsTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.periodTimingsCurIndex;
				lsvwObject.tableObject = $scope.periodTimingsTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.periodTimingsCurIndex = lsvwObject.curIndex;
				$scope.periodTimingsTable = lsvwObject.tableObject;
				$scope.periodTimingsRecord = $scope.periodTimingsTable[$scope.periodTimingsCurIndex];
				//var SelectedArray= ['startTimeHour','startTimeMin','endTimeHour','endTimeMin'];
				//fnSelectRefresh(SelectedArray);
			}
		}


	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'periodTimings') {
			if ($scope.periodTimingsTable != null && $scope.periodTimingsTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.periodTimingsCurIndex;
				lsvwObject.tableObject = $scope.periodTimingsTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.periodTimingsCurIndex = lsvwObject.curIndex;
				$scope.periodTimingsTable = lsvwObject.tableObject;
				$scope.periodTimingsRecord = $scope.periodTimingsTable[$scope.periodTimingsCurIndex];
				//var SelectedArray= ['startTimeHour','startTimeMin','endTimeHour','endTimeMin'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};


	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'periodTimings') {
				emptyTableRec = {
					idx: 0,
					class: "",
					periodNumber: "",
                                        noon:"",
					startTime: {hour:"Hour",min:"Min"},
					endTime:{hour:"Hour",min:"Min"}
				};
				if ($scope.periodTimingsTable == null)
					$scope.periodTimingsTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.periodTimingsShow;
				lsvwObject.curIndex = $scope.periodTimingsCurIndex;
				lsvwObject.tableObject = $scope.periodTimingsTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.periodTimingsShow = lsvwObject.tableShow;
				$scope.periodTimingsCurIndex = lsvwObject.curIndex;
				$scope.periodTimingsTable = lsvwObject.tableObject;
				$scope.periodTimingsRecord = $scope.periodTimingsTable[$scope.periodTimingsCurIndex];
				//var SelectedArray= ['startTimeHour','startTimeMin','endTimeHour','endTimeMin'];
				//fnSelectRefresh(SelectedArray);
			}
		}

	};
	$scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'periodTimings') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.periodTimingsShow;
				lsvwObject.curIndex = $scope.periodTimingsCurIndex;
				lsvwObject.tableObject = $scope.periodTimingsTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.periodTimingsShow = lsvwObject.tableShow;
				$scope.periodTimingsCurIndex = lsvwObject.curIndex;
				$scope.periodTimingsTable = lsvwObject.tableObject;
				$scope.periodTimingsRecord = $scope.periodTimingsTable[$scope.periodTimingsCurIndex];
				//var SelectedArray= ['startTimeHour','startTimeMin','endTimeHour','endTimeMin'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'periodTimings') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.periodTimingsShow;
			lsvwObject.curIndex = $scope.periodTimingsCurIndex;
			lsvwObject.tableObject = $scope.periodTimingsTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'periodTimings') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.periodTimingsShow;
			lsvwObject.curIndex = $scope.periodTimingsCurIndex;
			lsvwObject.tableObject = $scope.periodTimingsTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		}
	};
	//Scope Level Single View functions Ends 

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'standardMaster') {
			if ($scope.standardMasterTable != null && $scope.standardMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.standardMasterCurPage;
				lsvwObject.tableObject = $scope.standardMasterTable;
				lsvwObject.screenShowObject = $scope.standardMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.standardMasterCurPage = lsvwObject.curPage;
				$scope.standardMasterTable = lsvwObject.tableObject;
				$scope.standardMasterShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'standardMaster') {
			if ($scope.standardMasterTable != null && $scope.standardMasterTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.standardMasterCurPage;
				lsvwObject.tableObject = $scope.standardMasterTable;
				lsvwObject.screenShowObject = $scope.standardMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.standardMasterCurPage = lsvwObject.curPage;
				$scope.standardMasterTable = lsvwObject.tableObject;
				$scope.standardMasterShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'standardMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
				        class:"",
					teacherID: ""
				};
				if ($scope.standardMasterTable == null)
					$scope.standardMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.standardMasterCurPage;
				lsvwObject.tableObject = $scope.standardMasterTable;
				lsvwObject.screenShowObject = $scope.standardMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.standardMasterCurPage = lsvwObject.curPage;
				$scope.standardMasterTable = lsvwObject.tableObject;
				$scope.standardMasterShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'standardMaster') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.standardMasterCurPage;
				lsvwObject.tableObject = $scope.standardMasterTable;
				lsvwObject.screenShowObject = $scope.standardMasterShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.standardMasterCurPage = lsvwObject.curPage;
				$scope.standardMasterTable = lsvwObject.tableObject;
				$scope.standardMasterShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'standardMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.standardMasterCurPage;
			lsvwObject.tableObject = $scope.standardMasterTable;
			lsvwObject.screenShowObject = $scope.standardMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'standardMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.standardMasterCurPage;
			lsvwObject.tableObject = $scope.standardMasterTable;
			lsvwObject.screenShowObject = $scope.standardMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'standardMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.standardMasterTable);
			
		}
	};


	//Multiple View Scope Function ends 
	//Scope Level Function Ends
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
 $scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null

		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	} 
        
        
});
//--------------------------------------------------------------------------------------------------------------

// Screen Specific Default Validation Starts

$(document).ready(function () {
	MenuName = "ClassLevelConfiguration";
        selectBypassCount=0;
	  window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Institute";
	/*if(/Android/.test(navigator.appVersion)) {

   window.addEventListener("resize", function() {

     if(document.activeElement.tagName=="INPUT" || document.activeElement.tagName=="TEXTAREA") {

       document.activeElement.scrollIntoView();

     }

  })

}	*/
        selectBoxes= ['hour','min'];
        fnGetSelectBoxdata(selectBoxes);
      
       

}
        );
// Screen Specific Default Validation Ends

function fnClassLevelConfigurationpostSelectBoxMaster()
{
   selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	 window.parent.fn_hide_parentspinner();
    if (window.parent.Classkey.class != null && window.parent.Classkey.class != '') {
		var Class = window.parent.Classkey.class;

		window.parent.Classkey.class = null;

		fnshowSubScreen(Class);

	}     
         
    }
    }
function fnshowSubScreen(Class) {
       subScreen = true;
	var emptyClassLevelConfiguration = {
		instituteName: "",
		instituteID: "",
                Class:"",
                teacherID:"",
                teacherName:"",
                attendance:"",
               /*StandardMaster: [{
			idx: 0,
			checkBox: false,
			class:"",
			teacherID: ""
		}],*/
		periodTimings: [{
			idx: 0,
			class:"",
			periodNumber: "",
                        noon:"",
			startTime: {hour:"Hour",min:"Min"},
			endTime: {hour:"Hour",min:"Min"}
		}]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyClassLevelConfiguration;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if(Class!=null||Class!=""){
            dataModel.Class = Class;
            dataModel.instituteID=window.parent.Institute.ID;
            //Screen Specific DataModel Ends
            var response = fncallBackend('ClassLevelConfiguration', 'View', dataModel, [{entityName:"class",entityValue:Class}], $scope);
        }
        return true;
}    
    
    
// Cohesive Generic query Frame Work Starts---------------------------------------------------------- 
function fnClassLevelConfigurationQuery() {
	
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// single View starts
	$scope.periodTimingsRecord = null;
	$scope.periodTimingsTable = null;
	$scope.periodTimingsCurIndex = 0;
	$scope.periodTimingsShow = false;
	// single View Ends
	// multiple View Starts
	$scope.standardMasterCurPage = 0;
	$scope.standardMasterTable = null;
	$scope.standardMasterShowObject = null;
	// Multiple View Ends
	//Screen Specific Scope Starts
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
        $scope.class="";
        $scope.teacherID="";
        $scope.teacherName="";
    
        $scope.StandardMaster = null;
        $scope.periodTimings = null;
	$scope.instituteIDreadOnly = false; // Integeration Changes
	$scope.instituteNamereadOnly = false;
        $scope.instituteSearchreadOnly = false;
	$scope.classReadonly = false;
	$scope.teacherNamereadOnly=true;
        $scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly=true;
        $scope.attendancereadOnly=true; 
         
	$scope.markRangereadOnly = true;
	$scope.gradereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberreadOnly = true;
        $scope.noonreadOnly=true;
	//Screen Specifc Scope Ends
	// Generic Field Starts 	
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = true; // single View
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	// Generic Field Ends 
	return true;
}
// View Frame Work Starts
function fnClassLevelConfigurationView() {
	var emptyClassLevelConfiguration = {
		instituteName: "",
		instituteID: "",
                Class:"",
                teacherID:"",
                teacherName:"",
                attendance:"",
               /*StandardMaster: [{
			idx: 0,
			checkBox: false,
			class:"",
			teacherID: ""
		}],*/
		periodTimings: [{
			idx: 0,
			class:"",
			periodNumber: "",
                        noon:"",
			startTime: {hour:"Hour",min:"Min"},
			endTime: {hour:"Hour",min:"Min"}
		}]
	};
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	var dataModel = emptyClassLevelConfiguration;
	//Screen Specific DataModel Starts
        if($scope.instituteID!=null&&$scope.instituteID!=""){
            dataModel.instituteID = $scope.instituteID;
        
         if($scope.class!=null&& $scope.class!=""){
            dataModel.Class = $scope.class;
        
        
         // Screen Specific DataModel Ends
            var response = fncallBackend('ClassLevelConfiguration', 'View', dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}],$scope);
	
        
        }
    }
       return true;

}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts    
function fnClassLevelConfigurationMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
                        if ($scope.class == '' || $scope.class == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
                        
			break;

		case 'Save':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
                        
                       if ($scope.class == '' || $scope.class == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
                       if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['teacherID']);
				return false;
			}
                        if ($scope.attendance == '' || $scope.attendance == null||$scope.attendance == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['attendance']);
				return false;
			}
                        
			/*if ($scope.standardMasterTable == null || $scope.standardMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['StandardMaster']);
				return false;
			}
			for (i = 0; i < $scope.standardMasterTable.length; i++) {
				if ($scope.standardMasterTable[i].class == '' || $scope.standardMasterTable[i].class == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Class in Class Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.standardMasterTable[i].teacherID == '' || $scope.standardMasterTable[i].teacherID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID in Standard Tab ' + 'record ' + (i + 1)]);
					return false;
				}

			}*/
			if ($scope.periodTimingsTable == null || $scope.periodTimingsTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Period Timings in Period Tab']);
				return false;
			}
			for (i = 0; i < $scope.periodTimingsTable.length; i++) {
				/*if ($scope.periodTimingsTable[i].class == '' || $scope.periodTimingsTable[i].class == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Class in Period Tab ' + 'record ' + (i + 1)]);
					return false;
				}*/
				if ($scope.periodTimingsTable[i].periodNumber == '' || $scope.periodTimingsTable[i].periodNumber == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Period Number in period Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.periodTimingsTable[i].startTime.hour == '' || $scope.periodTimingsTable[i].startTime.hour == null|| $scope.periodTimingsTable[i].startTime.hour == 'Hour') {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Start Time hour  in Period Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.periodTimingsTable[i].startTime.min == '' || $scope.periodTimingsTable[i].startTime.min == null || $scope.periodTimingsTable[i].startTime.min == 'Min') {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Start Time minutes  in Period Tab ' + 'record ' + (i + 1)]);
					return false;
				}

				if ($scope.periodTimingsTable[i].endTime.hour == '' || $scope.periodTimingsTable[i].endTime.hour == null || $scope.periodTimingsTable[i].endTime.hour == 'Hour' ) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['End Time hour in Period Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.periodTimingsTable[i].endTime.min == '' || $scope.periodTimingsTable[i].endTime.min == null || $scope.periodTimingsTable[i].endTime.min == 'Min' ) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['End Time minutes in Period Tab ' + 'record ' + (i + 1)]);
					return false;
				}
                                
                                if ($scope.periodTimingsTable[i].noon == '' || $scope.periodTimingsTable[i].noon == null||$scope.periodTimingsTable[i].noon == "Select option") {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Noon in period Tab ' + 'record ' + (i + 1)]);
					return false;
				}
			}

			break;
	}
	return true;
}

function fnClassLevelConfigurationDefaultandValidate(operation) {
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
// Specific Screen Mandatory Validation Ends
// cohesive Create Frame Work Starts
function fnClassLevelConfigurationCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// single View starts
	$scope.periodTimingsRecord = {
					idx: 0,
					//class:"",
					periodNumber: "",
                                        noon:"",
					startTime: {hour:"Hour",min:"Min"},
					endTime:{hour:"Hour",min:"Min"}	
	
	};
	$scope.periodTimingsTable = null;
	$scope.periodTimingsCurIndex = 0;
	$scope.periodTimingsShow = false;
	// single View Ends
	//Multiple View starts
	$scope.standardMasterTable=null;
	$scope.standardMasterCurPage = 0;
	$scope.standardMasterShowObject = null;
	//Multiple View ends
	// Generic Frame Work Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = false; // single view
	$scope.mvwAddDeteleDisable = false; //Multiple View
	$scope.operation = 'Creation';
	// Generic Frame Work Ends	
	// screen Specfic Scope Starts  
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
        $scope.class="";
        $scope.teacherID="";
        $scope.teacherName="";
        $scope.attendance="";
        
	$scope.instituteIDreadOnly = false;
        $scope.instituteSearchreadOnly = false;
        $scope.classReadonly = false;
        $scope.teacherNamereadOnly=false;
        $scope.teacherIDreadOnly = false;
        $scope.teacherNameSearchreadOnly=false;
        $scope.attendancereadOnly=false;
        
	$scope.instituteNamereadOnly = false;
	$scope.classReadonly = false;
	$scope.teacherIDreadOnly = false;
	$scope.periodNumberreadOnly = false;
	$scope.startTimereadOnly = false;
	$scope.endTimereadOnly = false;
        $scope.noonreadOnly=false;
	// screen Specfic Scope Ends  
	/*var emptyClassLevelConfiguration = {
		instituteName: "",
		instituteID: "",
		StandardMaster: [{
			idx: 0,
			checkBox: false,
			class:"",
			teacherID: ""
		}],
		periodTimings: [{
			idx: 0,
			class:"",
			periodNumber: "",
			startTime: {hour:"Hour",min:"Min"},
			endTime: {hour:"Hour",min:"Min"}
		}]
	};
	var dataModel = emptyClassLevelConfiguration;
	var response = fncallBackend('ClassLevelConfiguration', 'Create-Default', dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);*/	
	return true;
}
// Cohesive Create Frame Work Ends

// Cohesive Edit Frame Work Starts
function fnClassLevelConfigurationEdit() {
	
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Frame Work Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = false; // single view
	$scope.mvwAddDeteleDisable = false; //Multiple View
	$scope.operation = 'Modification';
	// Generic Frame Work Ends	

	// Screen  Specific Scope Starts 
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
	$scope.teacherNamereadOnly=false;
        $scope.teacherNameSearchreadOnly=false;
        $scope.attendancereadOnly=false;
        
        $scope.classReadonly = true;
	
        $scope.teacherIDreadOnly = false;
        
	$scope.startTimereadOnly = false;
	$scope.endTimereadOnly = false;
	$scope.periodNumberreadOnly = false;
        $scope.noonreadOnly=false;
	//Screen Specific Scope Ends
	return true;
}
// Cohesive Edit Frame Work Ends

// Cohesive Delete Frame Work Starts
function fnClassLevelConfigurationDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = true; // single view
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.operation = 'Deletion';
	// Generic Field Ends	
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
	$scope.classReadonly = true;
	$scope.teacherNamereadOnly=true;
        $scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly=true;  
        $scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberreadOnly = true;
        $scope.noonreadOnly=true;
        $scope.attendancereadOnly=true;
	// Screen Specific Scope Ends		             	
	return true;
}
// Cohesive Delete Frame Work Ends
// Cohesive Authorisation Frame Work Starts
function fnClassLevelConfigurationAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// Generic Frame Work Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = true; // single view
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.operation = 'Authorisation';
	// Generic Frame Work Ends
	// Screen specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
	$scope.classReadonly = true;
        $scope.teacherNamereadOnly=true;
        $scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly=true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberreadOnly = true;
        $scope.noonreadOnly=true;
        $scope.attendancereadOnly=true;
	// Screen Specific Scope Ends
	return true; // Integeration Changes
}
// Cohesive Authorisation Frame Work Ends
// Cohesive Reject Frame Work Starts
function fnClassLevelConfigurationReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// Generic  Frame Work Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = true; // single view
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.operation = 'Rejection';
	// Generic FrameWork Ends
	// Screen Specfic Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
          $scope.instituteSearchreadOnly = true;
	$scope.classReadonly = true;
        $scope.teacherNamereadOnly=true;
$scope.teacherIDreadOnly = true;
$scope.teacherNameSearchreadOnly=true;
	$scope.markRangereadOnly = true;
	$scope.gradereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.noonreadOnly=true;
        $scope.attendancereadOnly=true;
	// Screen Specfic Scope Ends
	return true;
}
// Cohesive Reject Framework Ends

function fnClassLevelConfigurationBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	$scope.audit = {};
	$scope.instituteName = "";
	$scope.instituteID = "";
        $scope.class="";
        $scope.teacherID="";
        $scope.teacherName="";
        $scope.attendance="";
	$scope.standardMasterTable=null;
	$scope.periodTimingsTable=null;
	$scope.standardMasterShowObject = null;
	$scope.periodTimingsRecord = null;
	}
	// Generic Field Starts
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	// Generic Field Ends
    // Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
        $scope.teacherNamereadOnly=true;
$scope.teacherIDreadOnly = true;
$scope.teacherNameSearchreadOnly=true;
//	$scope.standardreadOnly = true;
//	$scope.sectionreadOnly = true;
        $scope.classReadonly=true;
	$scope.markRangereadOnly = true;
	$scope.periodNumberreadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.svwAddDeteleDisable = true; // single view
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
          $scope.noonreadOnly=true;
        $scope.attendancereadOnly=true;
	// Screen Specific Scope Ends
}
// Cohesive Save Framework Starts
function fnClassLevelConfigurationSave() {
	var emptyClassLevelConfiguration = {
		instituteName: "",
		instituteID: "",
                Class:"",
                teacherID:"",
                teacherName:"",
                attendance:"",
		/*StandardMaster: [{
			idx: 0,
			checkBox: false,
			class:"",
			teacherID: ""
		}],*/
		periodTimings: [{
			idx: 0,
			//class:"",
			periodNumber: "",
                        noon:"",
			startTime: {hour:"Hour",min:"Min"},
			endTime: {hour:"Hour",min:"Min"}
		}]
	};
	var dataModel = emptyClassLevelConfiguration;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//screen Specific DataModel Starts
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
        if($scope.class!=null)
	dataModel.Class = $scope.class;
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
         if($scope.teacherName!=null)
	dataModel.teacherName = $scope.teacherName;
        if($scope.attendance!=null)
	dataModel.attendance = $scope.attendance;
    
        /*if($scope.standardMasterTable!=null)
	dataModel.StandardMaster = $scope.standardMasterTable;*/
        if($scope.periodTimingsTable!=null)
	dataModel.periodTimings = $scope.periodTimingsTable;
        //Screen Specific DataModel Ends
	var response = fncallBackend('ClassLevelConfiguration', parentOperation, dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	return true;
}
// Cohesive Save Frame Work Ends
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		    case 'standardMasterTable':
		   
			var standardMasterTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     standardMasterTable[index] = new Object();
					 standardMasterTable[index].idx=index;
					 standardMasterTable[index].checkBox=false;
					 standardMasterTable[index].class=value.class;
					 standardMasterTable[index].teacherID=value.teacherID;
                                         
					}
			return standardMasterTable;
			break;
			case 'periodTimingsTable':
		   
			var periodTimingsTable = new Array();
			 responseObject.forEach(fnConvert2);
			 function fnConvert2(value,index,array){
				     periodTimingsTable[index] = new Object();
					 periodTimingsTable[index].idx=index;
					 periodTimingsTable[index].checkBox=false;
					// periodTimingsTable[index].class=value.class;
					 periodTimingsTable[index].periodNumber=value.periodNumber;
					 periodTimingsTable[index].startTime=new Object();
					 periodTimingsTable[index].endTime=new Object();
					 periodTimingsTable[index].startTime.hour=value.startTime.hour;
					 periodTimingsTable[index].startTime.min=value.startTime.min;
					 periodTimingsTable[index].endTime.hour=value.endTime.hour;
					 periodTimingsTable[index].endTime.min=value.endTime.min;
                                         periodTimingsTable[index].noon=value.noon;
					}
			return periodTimingsTable;
			break;
			
	}
}


function fnClassLevelConfigurationpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status === 'success') {
		 
		 if(response.header.operation == 'Create-Default')
           		{
		  $scope.instituteID = response.body.instituteID;
		  $scope.instituteName = response.body.instituteName;
		  return true;
		} 
            else {
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.classReadonly = true;
                $scope.teacherNamereadOnly=true;
                $scope.teacherIDreadOnly = true;
                $scope.teacherNameSearchreadOnly=true;

		$scope.markRangereadOnly = true;
		$scope.periodNumberreadOnly = true;
 		$scope.startTimereadOnly = true;
		$scope.endTimereadOnly = true;
		$scope.teacherIDreadOnly = true;
                $scope.attendancereadOnly = true;
                $scope.noonreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.svwAddDeteleDisable = true; // single view
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.instituteID = "";
		$scope.instituteName ="";
                $scope.class="";
                $scope.teacherID="";
                $scope.teacherName="";
                $scope.attendance="";
                $scope.StandardMaster ={};
		$scope.periodTimings ={};
                $scope.standardMasterShowObject=null;
                $scope.periodTimingsShow=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
                $scope.class=response.body.Class;
                $scope.teacherID=response.body.teacherID;
                $scope.teacherName=response.body.teacherName;
                $scope.attendance=response.body.attendance;
		//$scope.StandardMaster = response.body.StandardMaster;
		$scope.periodTimings = response.body.periodTimings;
                $scope.audit = response.audit;
		//$scope.audit = response.body.audit;//Integration changes
		// Specific Screen Scope Response Ends 
                     
		// Single View Starts  
                
                if(response.body.periodTimings.length!=0){
                
		  $scope.periodTimingsTable = fnConvertmvw('periodTimingsTable',response.body.periodTimings);
		
                }else{
                    
                   $scope.periodTimingsTable=null; 
                }
                
                $scope.periodTimingsCurIndex = 0;
		if ($scope.periodTimingsTable != null) {

			$scope.periodTimingsRecord = $scope.periodTimingsTable[$scope.periodTimingsCurIndex];
			$scope.periodTimingsShow = true;
		}
		// Single View Ends


		//Multiple View Response Starts 
                
             /*   if(response.body.StandardMaster.length!=0){
                
		   $scope.standardMasterTable = fnConvertmvw('standardMasterTable',response.body.StandardMaster);
                }else{
                    
                    $scope.standardMasterTable=null;
                }*/
                
                
                /*$scope.standardMasterCurPage = 1;
		$scope.standardMasterShowObject=$scope.fnMvwGetCurPageTable('standardMaster');
              */
                $scope.svwAddDeteleDisable = true; // single view
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
}
