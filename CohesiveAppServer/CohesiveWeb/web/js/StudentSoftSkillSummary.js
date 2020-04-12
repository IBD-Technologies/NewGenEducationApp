/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	$scope.signStatus = "Select option";
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.AuthType = Institute.AuthStatusMaster;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// specific Screen Scope Ends
	// Generic Field starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
        $scope.selectedIndex =0;
	// Generic Field Ends
	// Screen Specific Scope Start
	$scope.examtypereadOnly = false;
	$scope.standardreadOnly = false;
	$scope.classReadonly = false;
	$scope.sectionreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.studentNamereadOnly = false;
        $scope.studentNameInputReadOnly = true;
	$scope.signStatusreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	
	// multiple View Starts
	$scope.StudentSoftSkillSummaryCurPage = 0;
	$scope.StudentSoftSkillSummaryTable = null;
	$scope.StudentSoftSkillSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'studentSoftSkillSummary') {
			if ($scope.StudentSoftSkillSummaryTable != null && $scope.StudentSoftSkillSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'studentSoftSkillSummary') {
			if ($scope.StudentSoftSkillSummaryTable != null && $scope.StudentSoftSkillSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentSoftSkillSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
				    studentID: "",
				    class: "",
				    exam: "",
					date:""
				};
				if ($scope.StudentSoftSkillSummaryTable == null)
					$scope.StudentSoftSkillSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentSoftSkillSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentSoftSkillSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentSoftSkillSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentSoftSkillSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentSoftSkillSummaryCurPage = lsvwObject.curPage;
				$scope.StudentSoftSkillSummaryTable = lsvwObject.tableObject;
				$scope.StudentSoftSkillSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'studentSoftSkillSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentSoftSkillSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentSoftSkillSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentSoftSkillSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'studentSoftSkillSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentSoftSkillSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentSoftSkillSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentSoftSkillSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'studentSoftSkillSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentSoftSkillSummaryTable);
			
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
	
	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "StudentSoftSkillSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="StudentSummaryEntity";
        selectBoxes= ['examType','signStatus','class'];
        fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnStudentSoftSkillSummarypostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(Institute.SubjectMaster.length>0 && Institute.ExamMaster.length>0){
         $scope.classes=Institute.ClassMaster;	
         $scope.ExamMaster = Institute.ExamMaster;
         $scope.signature=Institute.ParticipateMaster;
	 window.parent.fn_hide_parentspinner(); 
         $scope.$apply();
    }
}

function fnStudentSoftSkillSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	$scope.signStatus = "Select option";
	$scope.examtypereadOnly = true;
	$scope.classReadonly = true;
	$scope.standardreadOnly = true;
	$scope.sectionreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
        $scope.studentNameInputReadOnly = true;
	$scope.signStatusreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.studentID=$scope.StudentSoftSkillSummaryTable[$scope.selectedIndex].studentID;
	lscreenKeyObject.exam=$scope.StudentSoftSkillSummaryTable[$scope.selectedIndex].exam;
	fninvokeDetailScreen('StudentSoftSkill',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentSoftSkillSummaryView() {
	var emptyStudentSoftSkillSummary = {
		filter: {
		    studentName:"",
			studentID: "",
			class: 'Select option',
			exam: 'Select option',
			signStatus: 'Select option'
		},
		SummaryResult: [{
				studentName: "",
				studentID: "",
				exam: "",
				class: "",
				date:"",
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentSoftSkillSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View"; 
        if($scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
        if($scope.exam!=null)
	dataModel.filter.exam = $scope.exam;
        if($scope.class!=null)
	dataModel.filter.class = $scope.class;
        if($scope.signStatus!=null)
	dataModel.filter.signStatus = $scope.signStatus;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentSoftSkillSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentSoftSkillSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &                 
//						($scope.studentName == '' || $scope.studentName == null) &
//						($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') &
//						($scope.signStatus == '' || $scope.signStatus == null || $scope.signStatus == 'Select option')))
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

function fnStudentSoftSkillSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.StudentSoftSkillSummaryTable,$scope))//Generic For Summary
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
function fnStudentSoftSkillSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
		$scope.studentName = null;
	    $scope.studentID = null;
	    $scope.class = 'Select option';
	    $scope.exam = 'Select option';
	    $scope.signStatus = "Select option";
	    
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.StudentSoftSkillSummaryTable = null;
		$scope.StudentSoftSkillSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.examtypereadOnly = false;
		$scope.classReadonly = false;
	    $scope.studentIDreadOnly = false;
	    $scope.studentNamereadOnly = false;
            $scope.studentNameInputReadOnly = true;
	    $scope.signStatusreadOnly = false;
	    $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.examtypereadOnly = true;
	    $scope.studentIDreadOnly = true;
		$scope.classReadonly = true;
	    $scope.studentNamereadOnly = true;
            $scope.studentNameInputReadOnly = true;
	    $scope.signStatusreadOnly = true;
	    $scope.recordStatreadOnly = true;

	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
		
}
// Cohesive Create Framework Ends
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'StudentSoftSkillSummaryTable':
		   
			var StudentSoftSkillSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentSoftSkillSummaryTable[index] = new Object();
					 StudentSoftSkillSummaryTable[index].idx=index;
					 StudentSoftSkillSummaryTable[index].checkBox=false;
					 StudentSoftSkillSummaryTable[index].studentID=value.studentID;
					 StudentSoftSkillSummaryTable[index].studentName=value.studentName;
					 StudentSoftSkillSummaryTable[index].signStatus=value.signStatus;
                                         StudentSoftSkillSummaryTable[index].exam=value.exam;
					}
			return StudentSoftSkillSummaryTable;
			break ;
		}
	}
	
function fnStudentSoftSkillSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		     $scope.examtypereadOnly = true;
	         $scope.studentIDreadOnly = true;
                 $scope.studentNameInputReadOnly = true;
	         $scope.studentNamereadOnly = true;
	         $scope.signStatusreadOnly = true;
			 $scope.classReadonly = true;
	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts
                $scope.studentName =response.body.filter.studentName;
                $scope.studentID =response.body.filter.studentID;
                $scope.class =response.body.filter.class;
                $scope.exam =response.body.filter.exam;
		        $scope.signStatus =response.body.filter.signStatus;
                $scope.StudentSoftSkillSummaryTable=fnConvertmvw('StudentSoftSkillSummaryTable',response.body.SummaryResult);
	        	$scope.StudentSoftSkillSummaryCurPage = 1;
		        $scope.StudentSoftSkillSummaryShowObject=$scope.fnMvwGetCurPageTable('studentSoftSkillSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}