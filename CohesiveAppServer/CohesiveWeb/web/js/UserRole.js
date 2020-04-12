/* 
    Author     :IBD Technologies
	

*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
	//Generic Field Starts
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
         $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.mvwAddDeteleDisable = true; // Multiple View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.roleID = "";
	$scope.roleDescription = "";
	$scope.RoleMaster = [{
		RoleId: "",
		RoleDescription: ""
	}];
        $scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.functions = "";
        $scope.instituteName = "";
        $scope.instituteID = "";
	$scope.UserRoleDescription = "";
	$scope.Screen = Institute.FeatureMaster;
	$scope.roleIDreadOnly = true;
	$scope.roleDescriptionreadOnly = true;
        $scope.roleIDSearchreadOnly = true;
	$scope.functionIDreadOnly = true;
	$scope.createreadOnly = true;
	$scope.modifyreadOnly = true;
	$scope.rejectreadOnly = true;
	$scope.deletereadOnly = true;
	$scope.authreadOnly = true;
	$scope.viewreadOnly = true;
	$scope.autoAuthreadOnly = true;
	$scope.authorizereadOnly = true;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
         $scope.instituteSearchreadOnly = true;
	//Screen Specific Scope Ends	

	// Multiple View Starts
	$scope.ClassRoleCurPage = 0;
	$scope.ClassRoleTable = null;
	$scope.ClassRoleShowObject = null;
	// Multiple View Ends

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'UserRoleMaster') {
			if ($scope.ClassRoleTable != null && $scope.ClassRoleTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.ClassRoleCurPage;
				lsvwObject.tableObject = $scope.ClassRoleTable;
				lsvwObject.screenShowObject = $scope.ClassRoleShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.ClassRoleCurPage = lsvwObject.curPage;
				$scope.ClassRoleTable = lsvwObject.tableObject;
				$scope.ClassRoleShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'UserRoleMaster') {
			if ($scope.ClassRoleTable != null && $scope.ClassRoleTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.ClassRoleCurPage;
				lsvwObject.tableObject = $scope.ClassRoleTable;
				lsvwObject.screenShowObject = $scope.ClassRoleShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.ClassRoleCurPage = lsvwObject.curPage;
				$scope.ClassRoleTable = lsvwObject.tableObject;
				$scope.ClassRoleShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'UserRoleMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					functionID: "Select option",
					create: "",
					view: "",
					modify: "",
					reject: "",
					delete: "",
					autoAuth: "",
					auth: "",
				};
				if ($scope.ClassRoleTable == null)
					$scope.ClassRoleTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassRoleCurPage;
				lsvwObject.tableObject = $scope.ClassRoleTable;
				lsvwObject.screenShowObject = $scope.ClassRoleShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.ClassRoleCurPage = lsvwObject.curPage;
				$scope.ClassRoleTable = lsvwObject.tableObject;
				$scope.ClassRoleShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'UserRoleMaster') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.ClassRoleCurPage;
				lsvwObject.tableObject = $scope.ClassRoleTable;
				lsvwObject.screenShowObject = $scope.ClassRoleShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.ClassRoleCurPage = lsvwObject.curPage;
				$scope.ClassRoleTable = lsvwObject.tableObject;
				$scope.ClassRoleShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'UserRoleMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassRoleCurPage;
			lsvwObject.tableObject = $scope.ClassRoleTable;
			lsvwObject.screenShowObject = $scope.ClassRoleShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'UserRoleMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.ClassRoleCurPage;
			lsvwObject.tableObject = $scope.ClassRoleTable;
			lsvwObject.screenShowObject = $scope.ClassRoleShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'UserRoleMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.ClassRoleTable);

		}
	};
	//Multiple View Scope Function ends 
	
	$scope.fnUserRoleSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'UserRole';
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
	MenuName = "UserRole";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="User";
        window.parent.fn_hide_parentspinner();
        selectBoxes= ['functionID'];
        fnGetSelectBoxdata(selectBoxes);
	
});
//Default Load Record Ends
function fnUserRolepostSelectBoxMaster()
{
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {    
	 var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (window.parent.UserRolekey.roleID !=null && window.parent.UserRolekey.roleID !='')
	{
		var roleID=window.parent.UserRolekey.roleID;
		 window.parent.UserRolekey.roleID =null;
		fnshowSubScreen(roleID);
		
	}
        
        var emptyUserRole = {
		UserRoleDescription: [{
			operation: "C",
			description: "Create"
		}, {
			operation: "M",
			description: "Modify"
		}, {
			operation: "D",
			description: "Delete"
		}, {
			operation: "V",
			description: "View"
		}, {
			operation: "AA",
			description: "Auto Authorisation"
		}, {
			operation: "R",
			description: "Reject"
		}]
	};
	var dataModel = emptyUserRole;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.roleID!==null&&$scope.roleID!=""){
            dataModel.roleID = $scope.roleID;
            var response = fncallBackend('UserRole', 'View', dataModel, [{entityName:"roleID",entityValue:$scope.roleID}], $scope);      
            return true;
        }
        }
return true;
    
}

function fnshowSubScreen(roleID)
{
subScreen = true;
var emptyUserRole = {
		roleID: "",
		roleDescription: "",
                instituteName:"",
                instituteID:"",
		functions: [{
			idx: 0,
			checkBox: false,
			functionID: "Select option",
			create: "",
			modify: "",
			autoAuth: "",
			delete: "",
			reject: "",
			view: "",
			auth: "",
		}],
		UserRoleDescription: [{
			operation: "C",
			description: "Create"
		}, {
			operation: "M",
			description: "Modify"
		}, {
			operation: "D",
			description: "Delete"
		}, {
			operation: "V",
			description: "View"
		}, {
			operation: "AA",
			description: "Auto Authorisation"
		}, {
			operation: "R",
			description: "Reject"
		}]
	};
	var dataModel = emptyUserRole;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(roleID!=null&&roleID!=''){
            dataModel.roleID = roleID;
            dataModel.instituteID=window.parent.Institute.ID;
            var response = fncallBackend('UserRole', 'View', dataModel, [{entityName:"roleID",entityValue:roleID}], $scope);
        }
    return true;
}


//Cohesive Query Franework Starts
function fnUserRoleQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.roleID = "";
         //$scope.instituteName = "";
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
        //$scope.instituteID = "";
	$scope.roleDescription = "";
	$scope.functions = "";
	$scope.roleIDreadOnly = false;
        $scope.roleIDSearchreadOnly = false;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
	$scope.roleDescriptionreadOnly = true;
	$scope.functionIDreadOnly = true;
	$scope.createreadOnly = true;
	$scope.modifyreadOnly = true;
	$scope.deletereadOnly = true;
	$scope.rejectreadOnly = true;
	$scope.viewreadOnly = true;
	$scope.autoAuthreadOnly = true;
	$scope.authorizereadOnly = true;
          $scope.instituteSearchreadOnly = true;

	//Screen Specific Scope Ends	
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.mvwAddDeteleDisable = true; //Multiple View
	//Generic Field Ends
	// Multiple View Starts
	$scope.ClassRoleCurPage = 0;
	$scope.ClassRoleTable = null;
	$scope.ClassRoleShowObject = null;
	// Multiple View Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnUserRoleView() {
	var emptyUserRole = {
		roleID: "",
		roleDescription: "",
                instituteName:'',
                instituteID:"",
		functions: [{
			idx: 0,
			checkBox: false,
			functionID: "Select option",
			create: "",
			modify: "",
			autoAuth: "",
			delete: "",
			reject: "",
			view: "",
			auth: "",
		}],
		UserRoleDescription: [{
			operation: "C",
			description: "Create"
		}, {
			operation: "M",
			description: "Modify"
		}, {
			operation: "D",
			description: "Delete"
		}, {
			operation: "V",
			description: "View"
		}, {
			operation: "AA",
			description: "Auto Authorisation"
		}, {
			operation: "R",
			description: "Reject"
		}]
	};
	var dataModel = emptyUserRole;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.roleID!=null)
	dataModel.roleID = $scope.roleID;
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
         if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
    
	var response = fncallBackend('UserRole', 'View', dataModel, [{entityName:"roleID",entityValue:$scope.roleID}], $scope);
	
    return true;
}
//Cohesive View Framework Ends
//screen Specific Mandatory Validation Starts
function fnUserRoleMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
                        if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			if ($scope.roleID == '' || $scope.roleID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Role ID']);
				return false;
			}
                        
			break;

		case 'Save':
//                       if ($scope.instituteName == '' || $scope.instituteName == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
//				return false;
//			}
                       if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			if ($scope.roleID == '' || $scope.roleID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Role ID']);
				return false;
			}
			if ($scope.roleDescription == '' || $scope.roleDescription == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Role Description']);
				return false;
			}


			if ($scope.ClassRoleTable == null || $scope.ClassRoleTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Role ' + 'record ' + (i+1)]);
				return false;
			}
			break;


	}
	return true;
}
//screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnUserRoleDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultRoleId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultRoleId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultRoleId($scope) {
	var availabilty = false;
	return true;
}
//Screen specific Default Validation Ends
//Cohesive Create Framework Starts
function fnUserRoleCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Creation';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.roleID = "";
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.roleDescription = "";
	$scope.functions = "";
	$scope.roleIDreadOnly = false;
        $scope.roleIDSearchreadOnly = false;
	$scope.roleDescriptionreadOnly = false;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
	$scope.functionIDreadOnly = false;
	$scope.createreadOnly = false;
	$scope.modifyreadOnly = false;
	$scope.deletereadOnly = false;
	$scope.rejectreadOnly = false;
	$scope.viewreadOnly = false;
	$scope.autoAuthreadOnly = false;
	$scope.authorizereadOnly = false;
          $scope.instituteSearchreadOnly = true;
	//Screen Specific Scope Ends
	// Multiple View Starts
	$scope.ClassRoleCurPage = 0;
	$scope.ClassRoleTable = null;
	$scope.ClassRoleShowObject = null;
	// Multiple View Ends
        $scope.UserRoleDescription=[{
			operation: "C",
			description: "Create"
		}, {
			operation: "M",
			description: "Modify"
		}, {
			operation: "D",
			description: "Delete"
		}, {
			operation: "V",
			description: "View"
		}, {
			operation: "AA",
			description: "Auto Authorisation"
		}, {
			operation: "A",
			description: "Authorisation"
		},{
			operation: "R",
			description: "Reject"
		}];
	return true;
}
//Cohesive create framework Ends
//Cohesive Edit framework Starts
function fnUserRoleEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.roleIDreadOnly = true;
        $scope.roleIDSearchreadOnly = true;
	$scope.roleDescriptionreadOnly = false;
	$scope.functionIDreadOnly = false;
	$scope.createreadOnly = false;
	$scope.modifyreadOnly = false;
	$scope.deletereadOnly = false;
	$scope.viewreadOnly = false;
	$scope.autoAuthreadOnly = false;
	$scope.rejectreadOnly = false;
	$scope.authorizereadOnly = false;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
          $scope.instituteSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject framework Ends
//Cohesive Delete framework Starts
function fnUserRoleDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.roleIDreadOnly = true;
        $scope.roleIDSearchreadOnly = true;
	$scope.roleDescriptionreadOnly = true;
	$scope.functionIDreadOnly = true;
	$scope.createreadOnly = true;
	$scope.modifyreadOnly = true;
	$scope.deletereadOnly = true;
	$scope.viewreadOnly = true;
	$scope.rejectreadOnly = true;
	$scope.autoAuthreadOnly = true;
	$scope.authorizereadOnly = true;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
          $scope.instituteSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Delete framework Ends
//Cohesive Authorization framework Starts
function fnUserRoleAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	//Generic Field Ends
	//Screen Specific Scope starts
	$scope.roleIDreadOnly = true;
        $scope.roleIDSearchreadOnly = true;
	$scope.roleDescriptionreadOnly = true;
	$scope.functionIDreadOnly = true;
	$scope.createreadOnly = true;
	$scope.modifyreadOnly = true;
	$scope.deletereadOnly = true;
	$scope.viewreadOnly = true;
	$scope.rejectreadOnly = true;
	$scope.autoAuthreadOnly = true;
	$scope.authorizereadOnly = true;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
          $scope.instituteSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Authorization framework Ends
//Cohesive Reject framework Starts
function fnUserRoleReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.roleIDreadOnly = true;
        $scope.roleIDSearchreadOnly = true;
	$scope.roleDescriptionreadOnly = true;
	$scope.functionIDreadOnly = true;
	$scope.createreadOnly = true;
	$scope.modifyreadOnly = true;
	$scope.deletereadOnly = true;
	$scope.viewreadOnly = true;
	$scope.rejectreadOnly = true;
	$scope.autoAuthreadOnly = true;
	$scope.authorizereadOnly = true;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
          $scope.instituteSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Authorization framework Ends
//Cohesive Back framework Starts
function fnUserRoleBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.roleID = "";
                  $scope.instituteName = "";
                $scope.instituteID = ""; 
		$scope.roleDescription = "";
		$scope.ClassRoleShowObject = "";
		$scope.ClassRoleTable = "";
	}
	$scope.roleIDreadOnly = true;
        $scope.roleIDSearchreadOnly = true;
	$scope.roleDescriptionreadOnly = true;
	$scope.functionIDreadOnly = true;
	$scope.modifyreadOnly = true;
	$scope.deletereadOnly = true;
	$scope.viewreadOnly = true;
	$scope.rejectreadOnly = true;
	$scope.autoAuthreadOnly = true;
	$scope.authorizereadOnly = true;
	$scope.createreadOnly = true;
        $scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
          $scope.instituteSearchreadOnly = true;
	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = false; //Multiple View
	//Generic Field End

}
//Cohesive Back Framework Ends
//Cohesive Save Framework Starts
function fnUserRoleSave() {
	var emptyUserRole = {
		roleID: "",
		roleDescription: "",
                instituteName:"",
                instituteID:"",
		functions: [{
			idx: 0,
			checkBox: false,
			functionID: "Select option",
			create: "",
			modify: "",
			delete: "",
			reject: "",
			view: "",
                        auth:"",
			autoAuth: "",
		}],
		UserRoleDescription: [{
			operation: "C",
			description: "Create"
		}, {
			operation: "M",
			description: "Modify"
		}, {
			operation: "D",
			description: "Delete"
		}, {
			operation: "V",
			description: "View"
		}, {
			operation: "AA",
			description: "Auto Authorisation"
		}, {
			operation: "A",
			description: "Authorisation"
		},{
			operation: "R",
			description: "Reject"
		}]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyUserRole;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.roleID!=null)
	dataModel.roleID = $scope.roleID;
        if($scope.roleDescription!=null)
	dataModel.roleDescription = $scope.roleDescription;
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
         if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
        if($scope.UserRoleDescription!=null)
	dataModel.UserRoleDescription = $scope.UserRoleDescription;
           if($scope.ClassRoleTable!=null)
           {     
	dataModel.functions = $scope.ClassRoleTable;
        dataModel.functions.forEach(fnConvert2);
			 function fnConvert2(value,index,array){
				    if(value.create =="")
                                       value.create=false; 
                                    if(value.modify =="")
                                       value.modify=false;    
                                    if(value.delete =="")
                                       value.delete=false; 
                                    if(value.reject =="")
                                       value.reject=false;
                                    if(value.view =="")
                                       value.view=false;
                                    if(value.autoAuth =="")
                                       value.autoAuth=false;
                                   if(value.auth =="")
                                       value.auth=false;
           }
	//Screen Specific DataModel Ends
	var response = fncallBackend('UserRole', parentOperation, dataModel,[{entityName:"roleID",entityValue:$scope.roleID}], $scope);
	return true;
    }
}
//Cohesive Save Framework Ends
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'ClassRoleTable':
		   
			var ClassRoleTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     ClassRoleTable[index] = new Object();
					 ClassRoleTable[index].idx=index;
					 ClassRoleTable[index].checkBox=false;
//					 ClassRoleTable[index].roleID=value.roleID;
//					 ClassRoleTable[index].roleDescription=value.roleDescription;
//					 ClassRoleTable[index].functions=value.functions;
                                         ClassRoleTable[index].functionID=value.functionID;
					 ClassRoleTable[index].create=value.create;
					 ClassRoleTable[index].modify=value.modify;
                                         ClassRoleTable[index].delete=value.delete;
                                         ClassRoleTable[index].reject=value.reject;
                                         ClassRoleTable[index].view=value.view;
                                         ClassRoleTable[index].autoAuth=value.autoAuth;
                                         ClassRoleTable[index].auth=value.auth;
					}
			return ClassRoleTable;
		}
	}

function fnUserRolepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status === 'success') {
            
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.roleIDreadOnly = true;
                $scope.roleIDSearchreadOnly = true;
                $scope.roleIDSearchreadOnly = true;
		$scope.roleDescriptionreadOnly = true;
		$scope.functionIDreadOnly = true;
		$scope.createreadOnly = true;
		$scope.modifyreadOnly = true;
		$scope.deletereadOnly = true;
		$scope.viewreadOnly = true;
		$scope.rejectreadOnly = true;
		$scope.autoAuthreadOnly = true;
		$scope.authorizereadOnly = true;
                $scope.instituteNamereadOnly = true;
                $scope.instituteIDreadOnly = true;
                  $scope.instituteSearchreadOnly = true;
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
                $scope.roleID = "";
                $scope.instituteName = "";
                $scope.instituteID = "";
		$scope.roleDescription ="";
		$scope.functions ={};
                $scope.ClassRoleShowObject=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteName = response.body.instituteName;
                $scope.instituteID = response.body.instituteID;
                $scope.roleID = response.body.roleID;
		$scope.roleDescription = response.body.roleDescription;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
                     
		//Multiple View Response Starts 
		$scope.ClassRoleTable = fnConvertmvw('ClassRoleTable',response.body.functions);
		$scope.ClassRoleCurPage = 1
		$scope.ClassRoleShowObject = $scope.fnMvwGetCurPageTable('UserRoleMaster');
                $scope.UserRoleDescription=[{
			operation: "C",
			description: "Create"
		}, {
			operation: "M",
			description: "Modify"
		}, {
			operation: "D",
			description: "Delete"
		}, {
			operation: "V",
			description: "View"
		}, {
			operation: "AA",
			description: "Auto Authorisation"
		}, {
			operation: "A",
			description: "Authorisation"
		},{
			operation: "R",
			description: "Reject"
		}];
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






