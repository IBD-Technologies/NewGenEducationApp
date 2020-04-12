//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService, OperationScopes) {
	//Screen specific Scope Starts
	$scope.instituteName = "";
	$scope.instituteID = "";
	$scope.groupID = "";
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteNameSearchreadOnly = true;
        $scope.groupIDSearchreadOnly = true;
	$scope.GroupDescription = "";
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.classReadonly = true;
	//$scope.Class = Institute.StandardMaster;
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.GroupMappingMaster = [{
		GroupId: "",
		GroupDescription: ""
	}];
	//Screen Specific Scope Ends
	//single View Starts
	$scope.GroupMappingRecord = {
		idx: 0,
		class: "Select option",
		studentName: "",
		studentID: ""
	};
	$scope.GroupMappingTable = null;
	$scope.GroupMappingCurIndex = 0;
	$scope.GroupMappingShowObject = false;
	//single View Ends
	//Generic field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.MakerRemarksReadonly = true;
	$scope.svwAddDeteleDisable = true; //Single View
	$scope.operation = '';
	//Generic Field Ends
	//Scope Levle Single View functions starts 
	$scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'groupMapping') {
			if ($scope.GroupMappingTable != null && $scope.GroupMappingTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.GroupMappingCurIndex;
				lsvwObject.tableObject = $scope.GroupMappingTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.GroupMappingCurIndex = lsvwObject.curIndex;
				$scope.GroupMappingTable = lsvwObject.tableObject;
				$scope.GroupMappingRecord = $scope.GroupMappingTable[$scope.GroupMappingCurIndex];
				//var SelectedArray = ['Standard', 'section'];
				//fnSelectRefresh(SelectedArray);
			}
		}


	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'groupMapping') {
			if ($scope.GroupMappingTable != null && $scope.GroupMappingTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.GroupMappingCurIndex;
				lsvwObject.tableObject = $scope.GroupMappingTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.GroupMappingCurIndex = lsvwObject.curIndex;
				$scope.GroupMappingTable = lsvwObject.tableObject;
				$scope.GroupMappingRecord = $scope.GroupMappingTable[$scope.GroupMappingCurIndex];
				//var SelectedArray = ['Standard', 'section'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};


	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'groupMapping') {
				emptyTableRec = {
					idx: 0,
					class: 'Select option',
					studentName: "",
					studentID: ""
				};
				if ($scope.GroupMappingTable == null)
					$scope.GroupMappingTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.GroupMappingShowObject;
				lsvwObject.curIndex = $scope.GroupMappingCurIndex;
				lsvwObject.tableObject = $scope.GroupMappingTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.GroupMappingShowObject = lsvwObject.tableShow;
				$scope.GroupMappingCurIndex = lsvwObject.curIndex;
				$scope.GroupMappingTable = lsvwObject.tableObject;
				$scope.GroupMappingRecord = $scope.GroupMappingTable[$scope.GroupMappingCurIndex];
				//var SelectedArray = ['Standard', 'section'];
				//fnSelectRefresh(SelectedArray);

			}

		}

	};
	$scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'groupMapping') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.GroupMappingShowObject;
				lsvwObject.curIndex = $scope.GroupMappingCurIndex;
				lsvwObject.tableObject = $scope.GroupMappingTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.GroupMappingShowObject = lsvwObject.tableShow;
				$scope.GroupMappingCurIndex = lsvwObject.curIndex;
				$scope.GroupMappingTable = lsvwObject.tableObject;
				$scope.GroupMappingRecord = $scope.GroupMappingTable[$scope.GroupMappingCurIndex];
				//var SelectedArray = ['Standard', 'section'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'groupMapping') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.GroupMappingShowObject;
			lsvwObject.curIndex = $scope.GroupMappingCurIndex;
			lsvwObject.tableObject = $scope.GroupMappingTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'groupMapping') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.GroupMappingShowObject;
			lsvwObject.curIndex = $scope.GroupMappingCurIndex;
			lsvwObject.tableObject = $scope.GroupMappingTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		}
	};
	//Scope Level Single View functions Ends 	

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
//--------------------------------------------------------------------------------------------------------------
//Default Load Record Starts
$(document).ready(function () {
	MenuName = "GroupMapping";
        selectBypassCount=0;
	window.parent.nokotser = $("#nokotser").val();
	window.parent.Entity = "Institute";
	selectBoxes = ['class'];
        fnGetSelectBoxdata(selectBoxes);//Integration changes


});
//Default Load Record Ends
function fnGroupMappingpostSelectBoxMaster()
{
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
         if(Institute.ClassMaster.length>0)
      {   
           $scope.classes=Institute.ClassMaster;
	   window.parent.fn_hide_parentspinner();
    	if ((window.parent.GroupMapingSummarykey.groupID !=null && window.parent.GroupMapingSummarykey.groupID !=''))
	{
		var groupID=window.parent.GroupMapingSummarykey.groupID;
		var recordStat=window.parent.GroupMapingSummarykey.recordStat;		
		 window.parent.GroupMapingSummarykey.groupID =null;
		 fnshowSubScreen(groupID);

	}  
   $scope.$apply(); 
}
    }
}

//Summary Screen Starts
function fnshowSubScreen(groupID)
{
    subScreen = true;
var emptyGroupMapping = {
	    instituteID:"",
		instituteName: "",
		groupID: "",
		groupDescription: "",
		group: [{
			idx: 0,
			class: "Select option",
			studentName:"",
			studentID:""
		}]
	};

    //Screen Specific DataModel Starts
	var dataModel = emptyGroupMapping;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(groupID!==null&&groupID!==''){
	dataModel.groupID = groupID;
         dataModel.instituteID=window.parent.Institute.ID;
	//Screen Specific DataModel Ends
	var response = fncallBackend('GroupMapping', 'View', dataModel,[{
		entityName: "instituteID",
		entityValue: dataModel.instituteID
	}],$scope);
    
    }
	return true;
}
//Summary Screen Framework Ends
//Cohesive Query Framework Starts
function fnGroupMappingQuery() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.groupID = "";
	$scope.GroupDescription = "";
	$scope.assignee = "";
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.groupIDreadOnly = false;
	$scope.groupDescriptionreadOnly = true;
	$scope.classReadonly = true;
	$scope.studentNamereadOnly = true;
        $scope.groupIDSearchreadOnly = false;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
	$scope.instituteNameSearchreadOnly = false;
	//Screen Specific Scope Ends	
	//Single View Starts
	$scope.GroupMappingRecord = {
		idx: 0,
		class: "Select option",
		studentName: "",
		studentID: ""
	};
	$scope.GroupMappingTable = null;
	$scope.GroupMappingCurIndex = 0;
	$scope.GroupMappingShowObject = false;
	//Single View Ends	
	//Generic Field Starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.svwAddDeteleDisable = true; //Single View
	//Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnGroupMappingView() {
	var emptyGroupMapping = {
		instituteID: "",
		instituteName:"",
		groupID: "",
		groupDescription: "",
		group: [{
			idx: 0,
			class: "Select option",
			studentName: "",
			studentID: ""
		}]
	};

	//Screen Specific DataModel Starts
	var dataModel = emptyGroupMapping;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//	dataModel.groupID = $scope.groupID;
        
         if($scope.instituteID!=null&&$scope.instituteID!=""){
            dataModel.instituteID = $scope.instituteID;
        
         if($scope.groupID!=null&& $scope.groupID!=""){
            dataModel.groupID = $scope.groupID;
        
	//Screen Specific DataModel Ends
	var response = fncallBackend('GroupMapping', 'View', dataModel, [{
		entityName: "instituteID",
		entityValue: $scope.instituteID
	}], $scope);
	
         }
         
    }
	return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnGroupMappingMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false
			}
                        if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false
			}

			if ($scope.groupID == '' || $scope.groupID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Group ID']);
				return false;
			}

			break;

		case 'Save':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false
			}
                         if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false
			}
			if ($scope.groupID == '' || $scope.groupID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Group ID']);
				return false;
			}
			if ($scope.groupDescription == '' || $scope.groupDescription == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Group Description']);
				return false
			}
			if ($scope.GroupMappingTable == null || $scope.GroupMappingTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Group Mapping Table']);
				return false;
			}
//			for (i = 0; i < $scope.GroupMappingTable.length; i++) {
//				if ($scope.GroupMappingTable[i].class == '' || $scope.GroupMappingTable[i].class == null || $scope.GroupMappingTable[i].class == "Select option") {
//					fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
//					return false;
//				}
//			}
			break;


	}
	return true;
}
//screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnGroupMappingDefaultandValidate(operation) {
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

function fnGroupMappingDefaultandValidate(operation) {
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
//Screen Specific Default Validation Ends
//Cohesive Create framework Starts
function fnGroupMappingCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
	$scope.groupID = "";
	$scope.groupDescription = "";
	$scope.groupIDreadOnly = false;
        $scope.groupIDSearchreadOnly = true;
	$scope.groupDescriptionreadOnly = false;
        $scope.GroupMappingTable = new Array();
        $scope.studentNamereadOnly=false;
	//$scope.sectionreadOnly = false;
	$scope.classReadonly = false;
	$scope.svwAddDeteleDisable = false; // single view
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//Generic Field Ends
	//Single View Starts
	$scope.GroupMappingRecord = {
		idx: 0,
		class: "Select option",
		studentName: "",
		studentID: ""
	};
	$scope.GroupMappingTable = null;
	$scope.GroupMappingCurIndex = 0;
	$scope.GroupMappingShowObject = false;
	//Single View Ends	
        
        var emptyGroupMapping = {
		instituteID: "",
		instituteName: "",
		groupID: "",
		groupDescription: "",
		group: [{
			idx: 0,
			class: "Select option",
			studentName: "",
			studentID: ""
		}]
	};
        
        var dataModel = emptyGroupMapping;
        var response = fncallBackend('GroupMapping', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
        
	return true;
}
//Cohesive Create framework Ends
//Cohesive Edit framework Starts
function fnGroupMappingEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.svwAddDeteleDisable = false; // single View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = true;
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = false;
	$scope.classReadonly = false;
	$scope.studentNamereadOnly = false;
	//Screen Specific Scope Ends	
	return true;
}
//Cohesive Edit framework Ends
//Cohesive Delete framework Starts
function fnGroupMappingDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	//Generic Field Ends
	//screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.classReadonly = true;
	$scope.studentNamereadOnly = true;
            $scope.groupIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Delete framework Ends
//Cohesive AuthoriZation framework Starts
function fnGroupMappingAuth() {

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
	$scope.groupIDreadOnly = false;
        $scope.instituteNameSearchreadOnly = true;
	$scope.groupDescriptionreadOnly = false;
	$scope.classReadonly = false;
	$scope.studentNamereadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
            $scope.groupIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive AuthoriZation framework Ends
//Cohesive Reject framework Starts
function fnGroupMappingReject() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	//Screen Specific Scope Ends
	//Generic Field Starts
         $scope.groupIDSearchreadOnly = true;
	$scope.groupIDreadOnly = true;
	$scope.groupDescriptionreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.classReadonly = true;
	$scope.studentNamereadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	//Generic Field Ends	
	return true;
}
//Cohesive Reject framework Ends
//Cohesive Back framework Starts
function fnGroupMappingBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.groupID = "";
                $scope.instituteID = "";
                $scope.instituteName = "";
		$scope.groupDescription = "";
		$scope.assignee = "";
		$scope.GroupMappingShowObject = "";
		$scope.GroupMappingTable = "";
                $scope.GroupMappingRecord = "";
                
	}
	$scope.groupIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
            $scope.groupIDSearchreadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.groupDescriptionreadOnly = true;
	$scope.classReadonly = true;
	$scope.studentNamereadOnly = true;
	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
        $scope.auditshow = false;
	//Generic Field Ends
}
//Cohesive Reject framework Ends
function fnGroupMappingSave() {
	var emptyGroupMapping = {
		instituteID: "",
		instituteName: "",
		groupID: "",
		groupDescription: "",
		group: [{
			idx: 0,
			class: "Select option",
			studentName: "",
			studentID: ""
		}]
	};

	//screen Specifi DataModel Starts
	var dataModel = emptyGroupMapping;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
        
    if($scope.groupID!=null)
	dataModel.groupID = $scope.groupID;
        
     if($scope.groupDescription!=null)
	dataModel.groupDescription = $scope.groupDescription;
     if($scope.GroupMappingTable!=null)
	dataModel.group = $scope.GroupMappingTable;
	//Screen Specific DataModel Ends
	var response = fncallBackend('GroupMapping', parentOperation, dataModel,  [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	return true;
        
    
}
//Cohesive Save Framework Ends
function fnConvertmvw(tableName, responseObject) {
	switch (tableName) {
		case 'GroupMappingTable':
			var GroupMappingTable = new Array();
			responseObject.forEach(fnConvert1);

			function fnConvert1(value, index, array) {
				GroupMappingTable[index] = new Object();
				GroupMappingTable[index].idx = index;
				GroupMappingTable[index].checkBox = false;
				GroupMappingTable[index].class = value.class;
				GroupMappingTable[index].studentID = value.studentID;
				GroupMappingTable[index].studentName = value.studentName;
			}
			return GroupMappingTable;
			break;

	}
}


function fnGroupMappingpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            if(response.header.operation == 'Create-Default')
		
		{
		  $scope.groupID = response.body.groupID;
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
		$scope.groupIDreadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
		$scope.groupDescriptionreadOnly = true;
		$scope.classReadonly = true;
		$scope.studentNamereadOnly = true;
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
                    $scope.groupIDSearchreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.svwAddDeteleDisable = true; // single View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.instituteID = "";
		$scope.instituteName ="";
		$scope.groupID ="";
                $scope.groupDescription ="";
		$scope.group ={};
                $scope.GroupMappingShowObject=null;
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName; 
                $scope.groupID = response.body.groupID;
		$scope.groupDescription = response.body.groupDescription;
                $scope.audit=response.audit;
                
                
                if( response.body.group!=null){
                
	            $scope.GroupMappingTable = fnConvertmvw('GroupMappingTable', response.body.group);
                
                }else{
                    
                    $scope.GroupMappingTable=null;
                }
		$scope.GroupMappingCurIndex = 0;
		if ($scope.GroupMappingTable != null) {
			$scope.GroupMappingRecord = $scope.GroupMappingTable[$scope.GroupMappingCurIndex];
			$scope.GroupMappingShowObject = true;
		}
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
