/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.class = "Select option";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
        $scope.AssignmentMaster = [{
		AssignmentId: "",
		AssignmentDescription: ""
	}];
	$scope.status = "";
	$scope.subjectName = "Select option";
	$scope.subjectID = "";
	$scope.assignmentID = "";
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.completedDate = "";
	$scope.authStat = "";
        $scope.toDate = "";
        $scope.fromDate="";
	$scope.AuthType = Institute.AuthStatusMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.AssignmentType = Institute.AssignmentTypeMaster;
	$scope.Status = Institute.StatusMaster;
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
        $scope.studentNameInputReadOnly = true;
	$scope.classReadonly = false;
	$scope.studentIDreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.assignmentIDreadOnly = false;
	$scope.assignmentTypereadOnly = false;
	$scope.statusReadonly = false;
	$scope.dueDatereadOnly = false;
	$scope.completeDatereadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	// Specific Screen Scope Ends	
	// multiple View Starts
	$scope.studentAssignmentSummaryCurPage = 0;
	$scope.StudentAssignmentSummaryTable = null;
	$scope.StudentAssignmentSummaryShowObject = null;
	// multiple View Ends
    //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'studentAssignmentSummary') {
			if ($scope.StudentAssignmentSummaryTable != null && $scope.StudentAssignmentSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAssignmentSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.StudentAssignmentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'studentAssignmentSummary') {
			if ($scope.StudentAssignmentSummaryTable != null && $scope.StudentAssignmentSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAssignmentSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.StudentAssignmentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentAssignmentSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
				    studentName: "",
				    studentID: "",
				    subjectID: "",
				    subjectName: "",
				    dueDate:"",
				    status: ""
				};
				if ($scope.StudentAssignmentSummaryTable == null)
					$scope.StudentAssignmentSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAssignmentSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.StudentAssignmentSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentAssignmentSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentAssignmentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentAssignmentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentAssignmentSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentAssignmentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentAssignmentSummaryTable = lsvwObject.tableObject;
				$scope.StudentAssignmentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'studentAssignmentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentAssignmentSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentAssignmentSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentAssignmentSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'studentAssignmentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentAssignmentSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentAssignmentSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentAssignmentSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'studentAssignmentSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentAssignmentSummaryTable);
			
		}
	};


	//Multiple View Scope Function ends 
	
	
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
	
        
        
        $scope.fnClassAssignmentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Assignment';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
        
        
        
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
	  MenuName = "StudentAssignmentSummary";
          window.parent.nokotser=$("#nokotser").val();
          window.parent.Entity="StudentSummaryEntity";
          fnDatePickersetDefault('fromDate',fndateEventHandler);
         fnDatePickersetDefault('toDate',fndateEventHandler);
//	 fnDatePickersetDefault('assignmentDueDate',fndatePickerassignmentDueDateEventHandler);
	 fnsetDateScope();
	 selectBoxes= ['subject','assignmentType','Status','authStatus','class'];
         fnGetSelectBoxdata(selectBoxes);
	
});
//Default Load Record End

function fnStudentAssignmentSummarypostSelectBoxMaster()
{
 var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if(Institute.ClassMaster.length>0 && Institute.SubjectMaster.length>0)
      {    
        $scope.subjects = Institute.SubjectMaster;
        $scope.classes=Institute.ClassMaster;
        window.parent.fn_hide_parentspinner();
        $scope.$apply(); 
    }
}
// Cohesive Query Framework Starts
function fnStudentAssignmentSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.status = "";
	$scope.subjectName = "";
	$scope.subjectID = "";
	$scope.class = "Select option";
	$scope.assignmentID = "";
	$scope.assignmentType = "";
	$scope.dueDate = "";
	$scope.completedDate = "";
	$scope.authStat = "";
	$scope.fromDate="";
	$scope.toDate = "";
	$scope.studentNamereadOnly = true;
        $scope.studentNameInputReadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.assignmentIDreadOnly = true;
	$scope.assignmentTypereadOnly = true;
	$scope.dueDatereadOnly = true;
	$scope.completeDatereadOnly = true;
	$scope.classReadonly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.statusReadonly = true;
         $( "#fromDate" ).datepicker( "option", "disabled", true );
        $( "#toDate" ).datepicker( "option", "disabled", true );
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
	lscreenKeyObject.assignmentID=$scope.StudentAssignmentSummaryTable[$scope.selectedIndex].assignmentID;
        lscreenKeyObject.studentID=$scope.StudentAssignmentSummaryTable[$scope.selectedIndex].studentID;
	
	fninvokeDetailScreen('StudentAssignment',lscreenKeyObject,$scope);
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentAssignmentSummaryView() {
	var emptyStudentAssignmentSummary = {
		filter: {
//			assignmentID: "",
//			assignmentType: "",
			studentName: "",
			studentID: "",
//			subjectID: "",
//			class: "Select option",
//			subjectName: "Select option",
//			status:"",
                        toDate : "",
                        fromDate:""
                        
//			authStat: ""
		},
		        SummaryResult: [{
				studentName: "",
				studentID: "",
				subjectID: "",
				subjectName: "",
				dueDate: "",
				status: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentAssignmentSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.assignmentID!=null)
	dataModel.filter.assignmentID = $scope.assignmentID;
        if($scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
        if($scope.studentID!=null) 
	dataModel.filter.studentID = $scope.studentID;
       if($scope.class!=null) 
	dataModel.filter.class = $scope.class;
        if($scope.subjectName!=null) 
	dataModel.filter.subjectName = $scope.subjectName;
        if($scope.subjectID!=null) 
	dataModel.filter.subjectID = $scope.subjectID;
        if($scope.dueDate!=null) 
	dataModel.filter.dueDate = $scope.dueDate;
        if($scope.completedDate!=null) 
	dataModel.filter.completedDate = $scope.completedDate;
        if($scope.assignmentType!=null) 
	dataModel.filter.assignmentType = $scope.assignmentType;
        if($scope.status!=null) 
	dataModel.filter.status = $scope.status;
        if($scope.authStat!=null) 
	dataModel.filter.authStat = $scope.authStat;
        if($scope.recordStat!=null) 
	dataModel.filter.recordStat = $scope.recordStat;
    if($scope.fromDate!=null)
	dataModel.filter.fromDate = $scope.fromDate;
        if($scope.toDate!=null)
	dataModel.filter.toDate = $scope.toDate;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentAssignmentSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnStudentAssignmentSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.studentName == '' || $scope.studentName == null) &
//						($scope.subjectName == '' || $scope.subjectName == null) &
//						($scope.status == '' || $scope.status == null || $scope.status == "Select option") &
//						($scope.class == '' || $scope.class == null || $scope.class == "Select option") &
//						($scope.dueDate == '' || $scope.dueDate == null) &
//						($scope.completedDate == '' || $scope.completedDate == null) &
//						($scope.assignmentType == '' || $scope.assignmentType == null || $scope.assignmentType == "Select option") &
//						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == "Select option")))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
//                                     if ($scope.studentID == '' || $scope.studentID == null) {
//
//                                                                fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
//                                                                return false;
//                                                        }
                                 if ($scope.studentName == '' || $scope.studentName == null) {
                             
                                       fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
			                return false;
                                  }
                                 if ($scope.fromDate == '' || $scope.fromDate == null) {

                                                                fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
                                                                return false;
                                                        }
                              if ($scope.toDate == '' || $scope.toDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['To Date']);
				return false;
			} 
					return true;
	          break;
	
		     case 'Detail':
		           return true;
		           break;
	}
	return true;
}

function fnStudentAssignmentSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.StudentAssignmentSummaryTable,$scope))//Generic For Summary
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
function fnStudentAssignmentSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
		$scope.studentID = "";
	    $scope.studentName = "";
	    $scope.class = "Select option";
	    $scope.status = "";
	    $scope.subjectName = "";
	    $scope.subjectID = "";
	    $scope.assignmentID = "";
	    $scope.assignmentType = "";
	    $scope.dueDate = "";
     	$scope.completedDate = "";
	    $scope.authStat = "";
            $scope.fromDate="";
            $scope.toDate="";
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.StudentAssignmentSummaryTable = null;
		$scope.StudentAssignmentSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	   $scope.studentNamereadOnly = false;
           $scope.studentNameInputReadOnly = true;
	   $scope.studentIDreadOnly = false;
	   $scope.classReadonly = false;
	   $scope.subjectNamereadOnly = false;
	   $scope.assignmentIDreadOnly = false;
           $scope.assignmentTypereadOnly = false;
	   $scope.dueDatereadOnly = false;
	   $scope.completeDatereadOnly = false;
	   $scope.authStatreadOnly = false;
	   $scope.recordStatreadOnly = false;
	   $scope.statusReadonly = false;
	   $scope.mastershow=true;
	   $scope.detailshow=false;
           $( "#fromDate" ).datepicker( "option", "disabled", false );
            $( "#toDate" ).datepicker( "option", "disabled", false );
		}
	    else {
	 $scope.studentNamereadOnly = true;
         $scope.studentNameInputReadOnly = true;
	 $scope.studentIDreadOnly = true;
	 $scope.classReadonly = true;
	 $scope.subjectNamereadOnly = true;
	 $scope.assignmentIDreadOnly = true;
	 $scope.assignmentTypereadOnly = true;
	 $scope.dueDatereadOnly = true;
	 $scope.completeDatereadOnly = true;
	 $scope.authStatreadOnly = true;
 	 $scope.recordStatreadOnly = true;
	 $scope.statusReadonly = true;
         $( "#fromDate" ).datepicker( "option", "disabled", true );
            $( "#toDate" ).datepicker( "option", "disabled", true );
	       }
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
}
// Cohesive Create Framework Ends
//function fndatePickerassignmentDueDateEventHandler() {
//	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
////	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentDueDate").datepicker("getDate"));
//        
//        $scope.fromDate = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate"));
//        $scope.toDate = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate"));
//		$scope.$apply();
//}
///*function fndatePickerassignmentCompleteDateEventHandler() {
//	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//	$scope.completedDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentCompleteDate").datepicker("getDate"));
//		$scope.$apply();
//}*/
//function fnsetDateScope()
//{
//var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//	//$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentDueDate").datepicker("getDate"));
//	//$scope.completedDate = $.datepicker.formatDate('dd-mm-yy', $("#assignmentCompleteDate").datepicker("getDate"));
//		
//      $( "#assignmentDueDate" ).datepicker('setDate','');
//	  $scope.dueDate = null;
//    $scope.$apply();
//}	


function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.fromDate = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate"));
        $scope.toDate = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	 $( "#fromDate" ).datepicker('setDate','');
	  $scope.fromDate = null;
          $( "#toDate" ).datepicker('setDate','');
	  $scope.toDate = null;
	  //$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#activityDate").datepicker("getDate"));
		$scope.$apply();
}

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'StudentAssignmentSummaryTable':
		   
			var StudentAssignmentSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				         StudentAssignmentSummaryTable[index] = new Object();
					 StudentAssignmentSummaryTable[index].idx=index;
					 StudentAssignmentSummaryTable[index].checkBox=false;
					 StudentAssignmentSummaryTable[index].assignmentID=value.assignmentID;
					 StudentAssignmentSummaryTable[index].studentName=value.studentName;
					 StudentAssignmentSummaryTable[index].studentID=value.studentID;
//					 StudentAssignmentSummaryTable[index].subjectName=value.subjectName;
//					 StudentAssignmentSummaryTable[index].subjectID=value.subjectID;
					 StudentAssignmentSummaryTable[index].assignmentType=value.assignmentType;
					 StudentAssignmentSummaryTable[index].dueDate=value.dueDate;
//					 StudentAssignmentSummaryTable[index].status=value.status;
//					 StudentAssignmentSummaryTable[index].completedDate=value.completedDate;
					}
			return StudentAssignmentSummaryTable;
			break ;
		}
	}
function fnStudentAssignmentSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.studentNamereadOnly = true;
                 $scope.studentNameInputReadOnly = true;
	         $scope.studentIDreadOnly = true;
	         $scope.subjectNamereadOnly = true;
        	 $scope.assignmentIDreadOnly = true;
	         $scope.assignmentTypereadOnly = true;
             $scope.dueDatereadOnly = true;
		     $scope.classReadonly = true;
	         $scope.completeDatereadOnly = true;
             $scope.authStatreadOnly = true;
	         $scope.recordStatreadOnly = true;
	         $scope.statusReadonly = true;
		 // Specific Screen Scope Ends
		 // Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
                $( "#fromDate" ).datepicker( "option", "disabled", true );
                $( "#toDate" ).datepicker( "option", "disabled", true );
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.studentID = response.body.filter.studentID;
                $scope.studentName =response.body.filter.studentName;
                $scope.class =response.body.filter.class;
				$scope.authStat =response.body.filter.authStat;
				$scope.assignmentID =response.body.filter.assignmentID;
                $scope.subjectName = response.body.filter.subjectName;
                $scope.subjectID = response.body.filter.subjectID;
                $scope.assignmentType = response.body.filter.assignmentType;
                $scope.status =response.body.filter.status;
                $scope.fromDate =  response.body.filter.fromDate;
                $scope.toDate =  response.body.filter.toDate;
               $scope.StudentAssignmentSummaryTable=fnConvertmvw('StudentAssignmentSummaryTable',response.body.SummaryResult);
	           $scope.StudentAssignmentSummaryCurPage = 1;
	           $scope.StudentAssignmentSummaryShowObject=$scope.fnMvwGetCurPageTable('studentAssignmentSummary');
		     //Multiple View Response Ends
//                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}
        
	
