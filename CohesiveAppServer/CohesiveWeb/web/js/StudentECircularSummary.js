/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
        $scope.circularDescription = "";
	$scope.circularID = "";
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
	$scope.StudentECircularSummaryCurPage = 0;
	$scope.StudentECircularSummaryTable = null;
	$scope.StudentECircularSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'studentECircularSummary') {
			if ($scope.StudentECircularSummaryTable != null && $scope.StudentECircularSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentECircularSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentECircularSummaryCurPage = lsvwObject.curPage;
				$scope.StudentECircularSummaryTable = lsvwObject.tableObject;
				$scope.StudentECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'studentECircularSummary') {
			if ($scope.StudentECircularSummaryTable != null && $scope.StudentECircularSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentECircularSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentECircularSummaryCurPage = lsvwObject.curPage;
				$scope.StudentECircularSummaryTable = lsvwObject.tableObject;
				$scope.StudentECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentECircularSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
				    studentID: "",
				    class: "",
				    exam: "",
					date:""
				};
				if ($scope.StudentECircularSummaryTable == null)
					$scope.StudentECircularSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentECircularSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentECircularSummaryCurPage = lsvwObject.curPage;
				$scope.StudentECircularSummaryTable = lsvwObject.tableObject;
				$scope.StudentECircularSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentECircularSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentECircularSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentECircularSummaryCurPage = lsvwObject.curPage;
				$scope.StudentECircularSummaryTable = lsvwObject.tableObject;
				$scope.StudentECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'studentECircularSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentECircularSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentECircularSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentECircularSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'studentECircularSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentECircularSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentECircularSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentECircularSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'studentECircularSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentECircularSummaryTable);
			
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
        $scope.fnCircularSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Circular';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	
	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "StudentECircularSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="StudentSummaryEntity";
        selectBoxes= ['examType','signStatus','class'];
        fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnStudentECircularSummarypostSelectBoxMaster()
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

function fnStudentECircularSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
        $scope.circularDescription = "";
	$scope.circularID = "";
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
	lscreenKeyObject.studentID=$scope.StudentECircularSummaryTable[$scope.selectedIndex].studentID;
	lscreenKeyObject.circularID=$scope.StudentECircularSummaryTable[$scope.selectedIndex].circularID;
	fninvokeDetailScreen('StudentECircular',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentECircularSummaryView() {
	var emptyStudentECircularSummary = {
		filter: {
		    studentName:"",
			studentID: "",
                        circularDescription : "",
	                circularID : "",
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
	var dataModel = emptyStudentECircularSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View"; 
        if($scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
        if($scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
        if($scope.circularID!=null)
	dataModel.filter.circularID = $scope.circularID;
        if($scope.circularDescription!=null)
	dataModel.filter.circularDescription = $scope.circularDescription;
        if($scope.exam!=null)
	dataModel.filter.exam = $scope.exam;
        if($scope.class!=null)
	dataModel.filter.class = $scope.class;
        if($scope.signStatus!=null)
	dataModel.filter.signStatus = $scope.signStatus;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentECircularSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentECircularSummaryMandatoryCheck(operation) {
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

function fnStudentECircularSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.StudentECircularSummaryTable,$scope))//Generic For Summary
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
function fnStudentECircularSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.studentName = null;
	    $scope.studentID = null;
            $scope.circularDescription = null;
	    $scope.circularID = null;
	    $scope.class = 'Select option';
	    $scope.exam = 'Select option';
	    $scope.signStatus = "Select option";
	    
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.StudentECircularSummaryTable = null;
		$scope.StudentECircularSummaryShowObject = null;  
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
		case 'StudentECircularSummaryTable':
		   
			var StudentECircularSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentECircularSummaryTable[index] = new Object();
					 StudentECircularSummaryTable[index].idx=index;
					 StudentECircularSummaryTable[index].checkBox=false;
					 StudentECircularSummaryTable[index].studentID=value.studentID;
					 StudentECircularSummaryTable[index].studentName=value.studentName;
					 StudentECircularSummaryTable[index].signStatus=value.signStatus;
                                         StudentECircularSummaryTable[index].circularID=value.circularID;
					}
			return StudentECircularSummaryTable;
			break ;
		}
	}
	
function fnStudentECircularSummarypostBackendCall(response)
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
                $scope.circularDescription =response.body.filter.circularDescription;
                $scope.circularID =response.body.filter.circularID;
                $scope.class =response.body.filter.class;
                $scope.exam =response.body.filter.exam;
                
		        $scope.signStatus =response.body.filter.signStatus;
                $scope.StudentECircularSummaryTable=fnConvertmvw('StudentECircularSummaryTable',response.body.SummaryResult);
	        	$scope.StudentECircularSummaryCurPage = 1;
		        $scope.StudentECircularSummaryShowObject=$scope.fnMvwGetCurPageTable('studentECircularSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}