/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.from = "";
	$scope.to = "";
	$scope.leaveStatus = "";
        $scope.class = "Select option";
	$scope.statusMaster = Institute.FeeStatus;
	$scope.AuthType = Institute.AuthStatusMaster;
	
	$scope.LeaveMaster = Institute.LeaveMaster;
	$scope.statusMaster = Institute.LeaveMasterStatus;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
         $scope.OperationScopes=OperationScopes;
	$scope.selectedIndex =0;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.studentNamereadOnly = false;
         $scope.studentNameInputReadOnly=true;
        $scope.classReadonly = false;
        $scope.studentIDreadOnly = false;
	$scope.fromReadOnly = false;
	$scope.toReadOnly = false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	$scope.typeReadOnly = false;
	$scope.statusreadOnly = false;
	$scope.leaveStatusReadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	// multiple View Starts
	$scope.StudentLeaveManagementSummaryCurPage = 0;
	$scope.StudentLeaveManagementSummaryTable = null;
	$scope.StudentLeaveManagementSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'StudLeaveManagementSummary') {
			if ($scope.StudentLeaveManagementSummaryTable != null && $scope.StudentLeaveManagementSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentLeaveManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentLeaveManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentLeaveManagementSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentLeaveManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentLeaveManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentLeaveManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'StudLeaveManagementSummary') {
			if ($scope.StudentLeaveManagementSummaryTable != null && $scope.StudentLeaveManagementSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentLeaveManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentLeaveManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentLeaveManagementSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentLeaveManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentLeaveManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentLeaveManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudLeaveManagementSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
			        studentID:"",
			        class:"",
			        from: "",
			        to: "",
		            type: "",
				};
				if ($scope.StudentLeaveManagementSummaryTable == null)
					$scope.StudentLeaveManagementSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentLeaveManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentLeaveManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentLeaveManagementSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentLeaveManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentLeaveManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentLeaveManagementSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'StudLeaveManagementSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentLeaveManagementSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentLeaveManagementSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentLeaveManagementSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentLeaveManagementSummaryCurPage = lsvwObject.curPage;
				$scope.StudentLeaveManagementSummaryTable = lsvwObject.tableObject;
				$scope.StudentLeaveManagementSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'StudLeaveManagementSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentLeaveManagementSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentLeaveManagementSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentLeaveManagementSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'StudLeaveManagementSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentLeaveManagementSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentLeaveManagementSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentLeaveManagementSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'StudLeaveManagementSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentLeaveManagementSummaryTable);
			
		}
	};
	
	
	$scope.fnStudentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Student';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

	//Multiple View Scope Function ends 	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "StudentleaveManagementSummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="StudentSummaryEntity";
         window.parent.fn_hide_parentspinner();   
	fnDatePickersetDefault('fromDate',fndatePickerfromEventHandler);
        fnDatePickersetDefault('toDate',fndatePickertoEventHandler);
	fnsetDateScope();
        selectBoxes= ['leaveType','authStatus','class'];
        fnGetSelectBoxdata(selectBoxes);
	
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});


function fnStudentleaveManagementSummarypostSelectBoxMaster()
{
    
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if(Institute.ClassMaster.length>0)
      {  
        $scope.classes=Institute.ClassMaster;	
	//$scope.ExamMaster =Institute.ExamMaster;
	window.parent.fn_hide_parentspinner();
          $scope.$apply();
}


}


// Cohesive Query Framework Starts
function fnStudentleaveManagementSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
        $scope.class = "Select option";
	$scope.from = "";
	$scope.to = "";
	$scope.leaveStatus = "";
	$scope.studentNamereadOnly = true;
        $scope.studentNameInputReadOnly=true;
	$scope.studentIDreadOnly = true;
	$scope.fromReadOnly = true;
	$scope.toReadOnly = true;
        $( "#fromDate" ).datepicker( "option", "disabled", true );
        $( "#toDate" ).datepicker( "option", "disabled", true );
	$scope.typeReadOnly = true;
	$scope.leaveStatusReadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
//	$scope.selectedIndex =0;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.studentID=$scope.StudentLeaveManagementSummaryTable[$scope.selectedIndex].studentID;
        lscreenKeyObject.from=$scope.StudentLeaveManagementSummaryTable[$scope.selectedIndex].from;
	lscreenKeyObject.to=$scope.StudentLeaveManagementSummaryTable[$scope.selectedIndex].to;
        
        
	fninvokeDetailScreen('StudentLeaveManagement',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentleaveManagementSummaryView() {
	var emptyStudentleaveManagementSummary = {
		filter: {
			studentName: "",
			studentID: "",
			from: "",
			to: "",
			leaveStatus: "Select option"
		},
		SummaryResult: [{
			    studentID:"",
			    from: "",
			    to: "",
		            type: "",
                            leaveStatus:""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentleaveManagementSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View";
        if($scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
        if($scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
        if($scope.class!=null)
	dataModel.filter.class= $scope.class;
        if($scope.from!=null)
	dataModel.filter.from = $scope.from;
        if($scope.to!=null)
	dataModel.filter.to = $scope.to;
         if($scope.leaveType!=null)
	dataModel.filter.leaveType = $scope.leaveType;
        if($scope.leaveStatus!=null)
	dataModel.filter.leaveStatus = $scope.leaveStatus;
	// Screen Specific DataModel Ends
//	var response = fncallBackend('StudentleaveManagementSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	var response = fncallBackend('StudentLeaveManagementSummary', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
        return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnStudentleaveManagementSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.studentName == '' || $scope.studentName == null) &                 
//					    ($scope.from == '' || $scope.from == null) &
//						($scope.to == '' || $scope.to == null) &
//                                                ($scope.class == '' || $scope.class == null || $scope.class == "Select option") &
//						($scope.type == '' || $scope.type == null || $scope.type == "Select option") &
//						($scope.leaveStatus == '' || $scope.leaveStatus == null || $scope.leaveStatus == "Select option")))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
					return true;
	          break;
	
		     case 'Detail':
		           return true;
		           break;
	}
	return true;
}

function fnStudentleaveManagementSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultStudentId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultStudentId($scope))
				return false;

			break;
                case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.StudentLeaveManagementSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}

function fnDefaultStudentId($scope) {
	var availabilty = false;
	return true;
}

// Screen Specific Mandatory Validation Ends
function fnStudentleaveManagementSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.studentName = "";
	    $scope.studentID = "";
              $scope.class = "Select option";
	    $scope.from = "";
	    $scope.to = "";
	    $scope.type = "Select option";
	    $scope.leaveStatus = "";
	    
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.StudentLeaveManagementSummaryTable = null;
	    $scope.StudentLeaveManagementSummaryShowObject = null;  
	    if($scope.operation== "View")
		{	
	    $scope.studentNamereadOnly = false;
             $scope.studentNameInputReadOnly=true;
	    $scope.studentIDreadOnly = false;
	    $scope.fromReadOnly = false;
	    $scope.toReadOnly = false;
            $( "#fromDate" ).datepicker( "option", "disabled", false );
            $( "#toDate" ).datepicker( "option", "disabled", false );
    	    $scope.typeReadOnly = false;
	    $scope.leaveStatusReadOnly = false;
	    $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.studentNamereadOnly = true;
             $scope.studentNameInputReadOnly=true;
	    $scope.studentIDreadOnly = true;
	    $scope.fromReadOnly = true;
	    $scope.toReadOnly = true;
            $( "#fromDate" ).datepicker( "option", "disabled", true );
            $( "#toDate" ).datepicker( "option", "disabled", true );
    	    $scope.typeReadOnly = true;
	    $scope.leaveStatusReadOnly = true;
	    $scope.recordStatreadOnly = true;
	       }
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
}
// Cohesive Create Framework Ends
function fndatePickerfromEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.from = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate"));
		$scope.$apply();
}		
function fndatePickertoEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.to = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate"));
		$scope.$apply();
}	
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();


        $( "#fromDate" ).datepicker('setDate','');
	     $scope.from = null;

        $( "#toDate" ).datepicker('setDate','');
	    $scope.to = null;

	/*$scope.from = $.datepicker.formatDate('dd-mm-yy', $("#leaveFrom").datepicker("getDate"));
	$scope.to = $.datepicker.formatDate('dd-mm-yy', $("#leaveTo").datepicker("getDate"));*/
		$scope.$apply();
}		
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'StudentLeaveManagementSummaryTable':
		   
			var StudentLeaveManagementSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentLeaveManagementSummaryTable[index] = new Object();
					 StudentLeaveManagementSummaryTable[index].idx=index;
					 StudentLeaveManagementSummaryTable[index].checkBox=false;
					 StudentLeaveManagementSummaryTable[index].studentID=value.studentID;
					 StudentLeaveManagementSummaryTable[index].from=value.from;
					 StudentLeaveManagementSummaryTable[index].to=value.to;
					  StudentLeaveManagementSummaryTable[index].type=value.type;
                                          StudentLeaveManagementSummaryTable[index].leaveStatus=value.leaveStatus;
					}
			return StudentLeaveManagementSummaryTable;
			break ;
		}
	}
	
function fnStudentleaveManagementSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.studentNamereadOnly = true;
                  $scope.studentNameInputReadOnly=true;
	         $scope.studentIDreadOnly = true;
	         $scope.fromReadOnly = true;
	         $scope.toReadOnly = true;
                 $( "#fromDate" ).datepicker( "option", "disabled", true );
                 $( "#toDate" ).datepicker( "option", "disabled", true );
    	         $scope.typeReadOnly = true;
	         $scope.statusreadOnly = true;
	         $scope.leaveStatusReadOnly = true;
	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.studentName = response.body.filter.studentName;
                $scope.studentID =response.body.filter.studentID;
                $scope.from =response.body.filter.from;
                $scope.to = response.body.filter.to;
	        $scope.leaveStatus =response.body.filter.leaveStatus;
                $scope.StudentLeaveManagementSummaryTable=fnConvertmvw('StudentLeaveManagementSummaryTable',response.body.SummaryResult);
		$scope.StudentLeaveManagementSummaryCurPage = 1;
		$scope.StudentLeaveManagementSummaryShowObject=$scope.fnMvwGetCurPageTable('StudLeaveManagementSummary');
		//Multiple View Response Ends 
        }
		return true;

}