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
	$scope.signStatus = "";
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
	$scope.StudentProgressCardSummaryCurPage = 0;
	$scope.StudentProgressCardSummaryTable = null;
	$scope.StudentProgressCardSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'studentProgressCardSummary') {
			if ($scope.StudentProgressCardSummaryTable != null && $scope.StudentProgressCardSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentProgressCardSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentProgressCardSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardSummaryTable = lsvwObject.tableObject;
				$scope.StudentProgressCardSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'studentProgressCardSummary') {
			if ($scope.StudentProgressCardSummaryTable != null && $scope.StudentProgressCardSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentProgressCardSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentProgressCardSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardSummaryTable = lsvwObject.tableObject;
				$scope.StudentProgressCardSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentProgressCardSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
				    studentID: "",
				    class: "",
				    exam: "",
					date:""
				};
				if ($scope.StudentProgressCardSummaryTable == null)
					$scope.StudentProgressCardSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentProgressCardSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentProgressCardSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardSummaryTable = lsvwObject.tableObject;
				$scope.StudentProgressCardSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentProgressCardSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentProgressCardSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentProgressCardSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentProgressCardSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentProgressCardSummaryCurPage = lsvwObject.curPage;
				$scope.StudentProgressCardSummaryTable = lsvwObject.tableObject;
				$scope.StudentProgressCardSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'studentProgressCardSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentProgressCardSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentProgressCardSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentProgressCardSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'studentProgressCardSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentProgressCardSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentProgressCardSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentProgressCardSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'studentProgressCardSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentProgressCardSummaryTable);
			
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
	MenuName = "StudentProgressCardSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="StudentSummaryEntity";
        selectBoxes= ['examType','signStatus','class'];
        fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnStudentProgressCardSummarypostSelectBoxMaster()
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

function fnStudentProgressCardSummaryDetail() {

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
	lscreenKeyObject.studentID=$scope.StudentProgressCardSummaryTable[$scope.selectedIndex].studentID;
	lscreenKeyObject.exam=$scope.StudentProgressCardSummaryTable[$scope.selectedIndex].exam;
	fninvokeDetailScreen('StudentProgressCard',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentProgressCardSummaryView() {
	var emptyStudentProgressCardSummary = {
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
	var dataModel = emptyStudentProgressCardSummary;
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
	var response = fncallBackend('StudentProgressCardSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentProgressCardSummaryMandatoryCheck(operation) {
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

function fnStudentProgressCardSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.StudentProgressCardSummaryTable,$scope))//Generic For Summary
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
function fnStudentProgressCardSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
		$scope.studentName = null;
	    $scope.studentID = null;
	    $scope.class = 'Select option';
	    $scope.exam = 'Select option';
	    $scope.signStatus = "Select option";
	    
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.StudentProgressCardSummaryTable = null;
		$scope.StudentProgressCardSummaryShowObject = null;  
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
		case 'StudentProgressCardSummaryTable':
		   
			var StudentProgressCardSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentProgressCardSummaryTable[index] = new Object();
					 StudentProgressCardSummaryTable[index].idx=index;
					 StudentProgressCardSummaryTable[index].checkBox=false;
					 StudentProgressCardSummaryTable[index].studentID=value.studentID;
					 StudentProgressCardSummaryTable[index].studentName=value.studentName;
					 StudentProgressCardSummaryTable[index].signStatus=value.signStatus;
                                         StudentProgressCardSummaryTable[index].exam=value.exam;
					}
			return StudentProgressCardSummaryTable;
			break ;
		}
	}
	
function fnStudentProgressCardSummarypostBackendCall(response)
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
                $scope.StudentProgressCardSummaryTable=fnConvertmvw('StudentProgressCardSummaryTable',response.body.SummaryResult);
	        	$scope.StudentProgressCardSummaryCurPage = 1;
		        $scope.StudentProgressCardSummaryShowObject=$scope.fnMvwGetCurPageTable('studentProgressCardSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}