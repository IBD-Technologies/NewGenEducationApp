/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
    Author     : Munish Kumar B
	IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
//var tempoperation = "operation";
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
	// Generic Field Starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes=OperationScopes;
    // Generic Field Ends
	// Screen Specific Scope Starts	
	$scope.instituteName = "";
	$scope.instituteID ="";
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteSearchreadOnly = true;
	
	$scope.skillIDreadOnly = true;
	$scope.skillNamereadOnly = true;
	$scope.gradereadOnly = true;
        $scope.gradeDescriptionOnly = true;
	// Screen Specific Scope Ends

	// multiple View Starts


	$scope.gradeMasterCurPage = 0;
	$scope.gradeMasterTable = null;
	$scope.gradeMasterShowObject = null;

	$scope.skillMasterCurPage = 0;
	$scope.skillMasterTable = null;
	$scope.skillMasterShowObject = null;


	// multiple View Ends
	// Scope Level Multiple View Functions Starts
	$scope.fnMvwBackward = function (tableName, $event) {
 if (tableName == 'gradeMaster') {
			if ($scope.gradeMasterTable != null && $scope.gradeMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'skillMaster') {
			if ($scope.skillMasterTable != null && $scope.skillMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.skillMasterCurPage;
				lsvwObject.tableObject = $scope.skillMasterTable;
				lsvwObject.screenShowObject = $scope.skillMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.skillMasterCurPage = lsvwObject.curPage;
				$scope.skillMasterTable = lsvwObject.tableObject;
				$scope.skillMasterShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		 if (tableName == 'gradeMaster') {
			if ($scope.gradeMasterTable != null && $scope.gradeMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'skillMaster') {
			if ($scope.skillMasterTable != null && $scope.skillMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.skillMasterCurPage;
				lsvwObject.tableObject = $scope.skillMasterTable;
				lsvwObject.screenShowObject = $scope.skillMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.skillMasterCurPage = lsvwObject.curPage;
				$scope.skillMasterTable = lsvwObject.tableObject;
				$scope.skillMasterShowObject = lsvwObject.screenShowObject;
			}
		} 

	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			 if (tableName == 'gradeMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					grade: "",
					gradeDescription: ""
				};
				if ($scope.gradeMasterTable == null)
					$scope.gradeMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;

			} else if (tableName == 'skillMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					skillID: "",
					skillName: "",
				};
				if ($scope.skillMasterTable == null)
					$scope.skillMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.skillMasterCurPage;
				lsvwObject.tableObject = $scope.skillMasterTable;
				lsvwObject.screenShowObject = $scope.skillMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.skillMasterCurPage = lsvwObject.curPage;
				$scope.skillMasterTable = lsvwObject.tableObject;
				$scope.skillMasterShowObject = lsvwObject.screenShowObject;

			} 
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'gradeMaster') {

				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;
			} else if (tableName == 'skillMaster') {

				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.skillMasterCurPage;
				lsvwObject.tableObject = $scope.skillMasterTable;
				lsvwObject.screenShowObject = $scope.skillMasterShowObject;

				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.skillMasterCurPage = lsvwObject.curPage;
				$scope.skillMasterTable = lsvwObject.tableObject;
				$scope.skillMasterShowObject = lsvwObject.screenShowObject;
			} 


		}

	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		 if (tableName == 'gradeMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.gradeMasterCurPage;
			lsvwObject.tableObject = $scope.gradeMasterTable;
			lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);

		} else if (tableName == 'skillMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.skillMasterCurPage;
			lsvwObject.tableObject = $scope.skillMasterTable;
			lsvwObject.screenShowObject = $scope.skillMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);

		} 


	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		 if (tableName == 'gradeMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.gradeMasterCurPage;
			lsvwObject.tableObject = $scope.gradeMasterTable;
			lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		} else if (tableName == 'skillMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.skillMasterCurPage;
			lsvwObject.tableObject = $scope.skillMasterTable;
			lsvwObject.screenShowObject = $scope.skillMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}

	};

	$scope.fnMvwGetCurPageTable = function (tableName) {
		 if (tableName == 'gradeMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.gradeMasterTable);

		}
		else if (tableName == 'skillMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.skillMasterTable);

		}
	};
	//Single View Scope Function ends 
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
//------------------- Scope Level Function Ends-------------------------------------------------------------------------------------------

//-------This is to Load default record Starts--------------------------------------
$(document).ready(function () {

MenuName = "SoftSkillConfigurations";
        
window.parent.nokotser=$("#nokotser").val();

window.parent.Entity="Institute";
//fn_hide_parentspinner();
window.parent.fn_hide_parentspinner();



if(/Android/.test(navigator.appVersion)) {

   window.addEventListener("resize", function() {

     if(document.activeElement.tagName=="INPUT" || document.activeElement.tagName=="TEXTAREA") {

       document.activeElement.scrollIntoView();

     }

  })

}





});
//-------This is to Load default record Ends--------------------------------------




// Cohesive Query Framework Starts
function fnSoftSkillConfigurationsQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Scope Screen Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.GradeMaster = null;
	$scope.SkillMaster = null;
	$scope.instituteIDreadOnly = false;//Integration changes
	$scope.instituteNamereadOnly = false;
        $scope.instituteSearchreadOnly = false;
	$scope.skillIDreadOnly = true;
	$scope.skillNamereadOnly = true;
	$scope.gradereadOnly = true;
	$scope.gradeDescriptionreadOnly = true;
	// Specific Scope Screen Ends

	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.audit = {};
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	// Generic Field Ends	
	// multiple View Starts


	$scope.gradeMasterCurPage = 0;
	$scope.gradeMasterTable = null;
	$scope.gradeMasterShowObject = null;

	$scope.skillMasterCurPage = 0;
	$scope.skillMasterTable = null;
	$scope.skillMasterShowObject = null;


	// multiple View Ends

	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnSoftSkillConfigurationsView() {
	var emptySoftSkillConfigurations = {
		instituteName: "",
		instituteID: "",
		SkillMaster: [{
			idx: 0,
			checkBox: false,
			skillID: "",
			skillName: "",
		}],
		GradeMaster: [{
			idx: 0,
			checkBox: false,
			from: "",
			to: "",
			grade: ""
		}],
		
		/*audit: {},
		error: {}*/
	};
	// Specific Screen DataModel Starts
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel = emptySoftSkillConfigurations;
	dataModel.instituteID = $scope.instituteID;
	//dataModel.instituteName = $scope.instituteName;
	//dataModel.profileImgPath = $scope.profileImgPath;
	//dataModel.NotificationMaster = $scope.notificationMasterTable;
	//dataModel.FeeTypeMaster = $scope.feeMasterTable;
	//dataModel.ExamMaster = $scope.examMasterTable;
	//dataModel.GradeMaster = $scope.gradeMasterTable;
	//dataModel.SkillMaster = $scope.SkillMaster;
	//dataModel.audit = $scope.audit; //Integration changes
	// Specific Screen DataModel Ends

	var response = fncallBackend('SoftSkillConfiguration', 'View', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	/*if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.profileImgPathreadOnly = true;
		$scope.notificationTypereadOnly = true;
		$scope.notificationDescriptionreadOnly = true;
		$scope.skillIDreadOnly = true;
		$scope.skillNamereadOnly = true;
		$scope.feeTypereadOnly = true;
		$scope.feeDescriptionreadOnly = true;
		$scope.fromreadOnly = true;
		$scope.toreadOnly = true;
		$scope.gradereadOnly = true;
		$scope.examTypereadOnly = true;
		$scope.examDescriptionreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.profileImgPath = response.body.profileImgPath;
		$scope.NotificationMaster = response.body.NotificationMaster;
		$scope.FeeTypeMaster = response.body.FeeTypeMaster;
		$scope.GradeMaster = response.body.GradeMaster;
		$scope.SkillMaster = response.body.SkillMaster;
		$scope.ExamMaster = response.body.ExamMaster;
		$scope.audit = response.body.audit;
		// Specific Screen Scope Response Ends 

		//Multiple View Response Starts 
		$scope.examMasterTable = fnConvertmvw('examMasterTable',response.body.ExamMaster);
		$scope.examMasterCurPage = 1;
		$scope.examMasterShowObject = $scope.fnMvwGetCurPageTable('examMaster');

		$scope.gradeMasterTable = fnConvertmvw('gradeMasterTable',response.body.GradeMaster);
		$scope.gradeMasterCurPage = 1;
		$scope.gradeMasterShowObject = $scope.fnMvwGetCurPageTable('gradeMaster');

		$scope.skillMasterTable = fnConvertmvw('skillMasterTable',response.body.SkillMaster);
		$scope.skillMasterCurPage = 1;
		$scope.skillMasterShowObject = $scope.fnMvwGetCurPageTable('skillMaster');

		$scope.notificationMasterTable = fnConvertmvw('notificationMasterTable',response.body.NotificationMaster);
		$scope.notificationMasterCurPage = 1;
		$scope.notificationMasterShowObject = $scope.fnMvwGetCurPageTable('notificationMaster');

		$scope.feeMasterTable = fnConvertmvw('feeMasterTable',response.body.FeeTypeMaster);
		$scope.feeMasterCurPage = 1;
		$scope.feeMasterShowObject = $scope.fnMvwGetCurPageTable('feeMaster');

		//Multiple View Response Ends 

		return true;
	} else {
		return false;
	}*/
	return true;
}
// Cohesive View Framework Ends
// Cohesive Mandatory Validation Starts   
function fnSoftSkillConfigurationsMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
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

			


			if ($scope.gradeMasterTable == null || $scope.gradeMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Grade Master']);
				return false;
			}
			for (i = 0; i < $scope.gradeMasterTable.length; i++) {
				if ($scope.gradeMasterTable[i].grade == '' || $scope.gradeMasterTable[i].grade == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Grade ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.gradeMasterTable[i].gradeDescription == '' || $scope.gradeMasterTable[i].gradeDescription == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['To Mark ' + 'record ' + (i + 1)]);
					return false;
				}

			}


			if ($scope.skillMasterTable == null || $scope.skillMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Master']);
				return false;
			}
			for (i = 0; i < $scope.skillMasterTable.length; i++) {
				if ($scope.skillMasterTable[i].skillID == '' || $scope.skillMasterTable[i].skillID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Subject ID' + 'record ' + (i + 1)]);
					return false;
				}

				if ($scope.skillMasterTable[i].skillName == '' || $scope.skillMasterTable[i].skillName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Name' + 'record ' + (i + 1)]);
					return false;
				}
			}

			

			break;
	}
	return true;
}
// Screen Specific Mandatory Validation Ends
//Default Validation Starts
function fnSoftSkillConfigurationsDefaultandValidate(operation) {
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
	/*angular.forEach(Institute.InstituteMaster, function (value, key) {
		if (this[key].InstituteName == $scope.instituteName) {
			$scope.instituteID = this[key].InstituteId;
			availabilty = true;
			return true;
		}
	}, Institute.InstituteMaster);
	if (!availabilty) {
		fn_Show_Exception_With_Param('FE-VAL-002', ['Institute Name']);
		return false;
	}*/
	return true;
}
//Default Validation Ends
// Cohesive Create Framework Starts
function fnSoftSkillConfigurationsCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts  
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = false;
        $scope.instituteSearchreadOnly = true;

	$scope.skillIDreadOnly = false;
	$scope.skillNamereadOnly = false;
	$scope.gradeDescriptionreadOnly = false;
	$scope.gradereadOnly = false;

	// Screen Specific Scope Ends

	// Generic Field Starts	
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	// Generic Field End

	// multiple View Starts


	$scope.gradeMasterCurPage = 0;
	$scope.gradeMasterTable = null;
	$scope.gradeMasterShowObject = null;


	$scope.skillMasterCurPage = 0;
	$scope.skillMasterTable = null;
	$scope.skillMasterShowObject = null;

        
        var emptySoftSkillConfigurations = {
		instituteName: "",
		instituteID: "",
		profileImgPath: "",
		SkillMaster: [{
			idx: 0,
			checkBox: false,
			skillID: "",
			skillName: "",
		}],
		GradeMaster: [{
			idx: 0,
			checkBox: false,
			from: "",
			to: "",
			grade: ""
		}]
		/*audit: {},
		error: {}*/ 
	};
	// Screen Specific Datamodel Scope  Starts
	var dataModel = emptySoftSkillConfigurations;
	
var response = fncallBackend('SoftSkillConfiguration', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	
	// multiple View Ends
	return true;
}
// Cohesive Create Framework Ends

// Cohesive Edit Framework Starts
function fnSoftSkillConfigurationsEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Framework Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	// Generic Framework Ends	
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = false;
	$scope.instituteSearchreadOnly = true;
	$scope.skillIDreadOnly = false;
	$scope.skillNamereadOnly = false;
	$scope.gradeDescriptionreadOnly = false;
	$scope.gradereadOnly = false;
	// Screen Specific Scope Endss

	return true;
}
// Cohesive Edit Framework Ends

// Cohesive Delete Framework Starts
function fnSoftSkillConfigurationsDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	// Generic Field Ends

	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;

	$scope.skillIDreadOnly = true;
	$scope.skillNamereadOnly = true;
	$scope.gradeDescriptionreadOnly = true;
	$scope.gradereadOnly = true;
	// Screen Specific Scope Ends

	return true;
}
// Cohesive Delete Framework Ends

// Cohesive Authorization Framework Starts
function fnSoftSkillConfigurationsAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// Generic Field Starts
	$scope.operation = 'Authorisation';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	//Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
        
	$scope.skillIDreadOnly = true;
	$scope.skillNamereadOnly = true;
	$scope.gradeDescriptionreadOnly = true;
	$scope.gradereadOnly = true;
	//Screen Specific Scope Ends
return true;//Integration changes
}
// Cohesive Authorization Framework Ends

// Cohesive Reject Framework Starts
function fnSoftSkillConfigurationsReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.operation = 'Rejection';
	// Generic field Ends
	// Screen Specific Scope Starts	
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
	$scope.skillIDreadOnly = true;
	$scope.skillNamereadOnly = true;
	$scope.gradeDescriptionreadOnly = true;
	$scope.gradereadOnly = true;
	// screen Specific Scope Ends
	return true;
}
// Cohesive Reject Framework Ends
// Cohesive Back Framework Starts
function fnSoftSkillConfigurationsBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		// Screen Specific Scope Starts
		$scope.audit = {};
		$scope.instituteName = "";
		$scope.instituteID = "";
	
		$scope.gradeMasterTable = null;
		$scope.skillMasterTable = null;
		$scope.gradeMasterShowObject = null;
		$scope.skillMasterShowObject = null;
	}
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;

	$scope.skillIDreadOnly = true;
	$scope.skillNamereadOnly = true;

	$scope.gradeDescriptionreadOnly = true;
	$scope.gradereadOnly = true;

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
function fnSoftSkillConfigurationsSave() {
	var emptySoftSkillConfigurations = {
		instituteName: "",
		instituteID: "",
		SkillMaster: [{
			idx: 0,
			checkBox: false,
			skillID: "",
			skillName: "",
		}],
		GradeMaster: [{
			idx: 0,
			checkBox: false,
			from: "",
			to: "",
			grade: ""
		}]
		
		/*audit: {},
		error: {}*/ 
	};
	// Screen Specific Datamodel Scope  Starts
	var dataModel = emptySoftSkillConfigurations;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
        dataModel.instituteName = $scope.instituteName;
	dataModel.instituteID = $scope.instituteID;
        
	dataModel.SkillMaster = $scope.skillMasterTable;
	dataModel.GradeMaster = $scope.gradeMasterTable;
	//dataModel.audit = $scope.audit;
	// Screen Specific Datamodel Scope  Ends
	var response = fncallBackend('SoftSkillConfiguration', parentOperation, dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	/*if (response.header.status == 'success') {
		// Screen Specific Scope Starts
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.profileImgPathNamereadOnly = true;
		$scope.notificationTypereadOnly = true;
		$scope.notificationDescriptionreadOnly = true;
		$scope.skillIDreadOnly = true;
		$scope.skillNamereadOnly = true;
		$scope.feeTypereadOnly = true;
		$scope.feeDescriptionreadOnly = true;
		$scope.fromreadOnly = true;
		$scope.toreadOnly = true;
		$scope.gradereadOnly = true;
		$scope.examDescriptionreadOnly = true;
		$scope.examTypereadOnly = true;
		// Screen Specific Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.operation = '';
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Screen Specific Response Scope Starts
		$scope.instituteName = response.body.instituteName;
		$scope.instituteID = response.body.instituteID;
		$scope.profileImgPath = response.body.profileImgPath;
		$scope.audit = response.body.audit;
		// Screen Specific Response Scope Ends
		//Multiple View Response Starts 
		$scope.examMasterTable = fnConvertmvw('examMasterTable',response.body.ExamMaster);
		$scope.examMasterCurPage = 1;
		$scope.examMasterShowObject = $scope.fnMvwGetCurPageTable('examMaster');

		$scope.gradeMasterTable = fnConvertmvw('gradeMasterTable',response.body.GradeMaster);
		$scope.gradeMasterCurPage = 1;
		$scope.gradeMasterShowObject = $scope.fnMvwGetCurPageTable('gradeMaster');

		$scope.skillMasterTable = fnConvertmvw('skillMasterTable',response.body.SkillMaster);
		$scope.skillMasterCurPage = 1;
		$scope.skillMasterShowObject = $scope.fnMvwGetCurPageTable('skillMaster');

		$scope.notificationMasterTable = fnConvertmvw('notificationMasterTable',response.body.NotificationMaster);
		$scope.notificationMasterCurPage = 1;
		$scope.notificationMasterShowObject = $scope.fnMvwGetCurPageTable('notificationMaster');

		$scope.feeMasterTable = fnConvertmvw('feeMasterTable',response.body.FeeTypeMaster);
		$scope.feeMasterCurPage = 1;
		$scope.feeMasterShowObject = $scope.fnMvwGetCurPageTable('feeMaster');

		//Multiple View Response Ends 

		return true;

	} else {
		return false;
	}*/
	return true;
}

// Cohesive Save Framework Ends  


function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		
			case 'gradeMasterTable':
		   
			var gradeMasterTable = new Array();
			 responseObject.forEach(fnConvert2);
			 function fnConvert2(value,index,array){
				     gradeMasterTable[index] = new Object();
					 gradeMasterTable[index].idx=index;
					 gradeMasterTable[index].checkBox=false;
					 gradeMasterTable[index].gradeDescription=value.gradeDescription;
					 gradeMasterTable[index].grade=value.grade;
					}
			return gradeMasterTable;
			break ;
			case 'skillMasterTable':
		   
			var skillMasterTable = new Array();
			 responseObject.forEach(fnConvert3);
			 function fnConvert3(value,index,array){
				     skillMasterTable[index] = new Object();
					 skillMasterTable[index].idx=index;
					 skillMasterTable[index].checkBox=false;
					 skillMasterTable[index].skillID=value.skillID;
					 skillMasterTable[index].skillName=value.skillName;
					}
			return skillMasterTable;
			break ;
			
			
		}
	}

function fnSoftSkillConfigurationspostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	
	
	
        if (response.header.status == 'success') {
			
		if(response.header.operation == 'Create-Default')
		
		{
		  $scope.instituteID = response.body.instituteID;
		  return true;
		}
		else
		{	
			
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
                $scope.instituteSearchreadOnly = true;
                
		$scope.skillIDreadOnly = true;
		$scope.skillNamereadOnly = true;
		$scope.gradeDescriptionreadOnly = true;
		$scope.gradereadOnly = true;

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
		$scope.profileImgPath ="";
		$scope.GradeMaster = {};
		$scope.SkillMaster ={};
                $scope.gradeMasterShowObject=null;
                $scope.skillMasterShowObject=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.profileImgPath = response.body.profileImgPath;
		$scope.GradeMaster = response.body.GradeMaster;
		$scope.SkillMaster = response.body.SkillMaster;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
                     
		//Multiple View Response Starts 


		$scope.gradeMasterTable = fnConvertmvw('gradeMasterTable',response.body.GradeMaster);
		$scope.gradeMasterCurPage = 1;
		$scope.gradeMasterShowObject = $scope.fnMvwGetCurPageTable('gradeMaster');

		$scope.skillMasterTable = fnConvertmvw('skillMasterTable',response.body.SkillMaster);
		$scope.skillMasterCurPage = 1;
		$scope.skillMasterShowObject = $scope.fnMvwGetCurPageTable('skillMaster');

		
                }	
		//Multiple View Response Ends 
                // $scope.$apply();
 
                 
		return true;

}

}
}
