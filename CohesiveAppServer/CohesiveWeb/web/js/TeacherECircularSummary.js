/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService, OperationScopes) {
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
        $scope.circularDescription = "";
	$scope.circularID = "";
	$scope.TeacherMaster = [{
		TeacherId: "",
		TeacherName: ""
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
	$scope.teacherIDreadOnly = false;
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameInputReadOnly = true;
	$scope.signStatusreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	
	// multiple View Starts
	$scope.TeacherECircularSummaryCurPage = 0;
	$scope.TeacherECircularSummaryTable = null;
	$scope.TeacherECircularSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'teacherECircularSummary') {
			if ($scope.TeacherECircularSummaryTable != null && $scope.TeacherECircularSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.TeacherECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherECircularSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.TeacherECircularSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherECircularSummaryTable = lsvwObject.tableObject;
				$scope.TeacherECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'teacherECircularSummary') {
			if ($scope.TeacherECircularSummaryTable != null && $scope.TeacherECircularSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.TeacherECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherECircularSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.TeacherECircularSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherECircularSummaryTable = lsvwObject.tableObject;
				$scope.TeacherECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'teacherECircularSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					teacherName: "",
				    teacherID: "",
				    class: "",
				    exam: "",
					date:""
				};
				if ($scope.TeacherECircularSummaryTable == null)
					$scope.TeacherECircularSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherECircularSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.TeacherECircularSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherECircularSummaryTable = lsvwObject.tableObject;
				$scope.TeacherECircularSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'teacherECircularSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherECircularSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.TeacherECircularSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherECircularSummaryTable = lsvwObject.tableObject;
				$scope.TeacherECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'teacherECircularSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherECircularSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherECircularSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherECircularSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'teacherECircularSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherECircularSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherECircularSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherECircularSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'teacherECircularSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.TeacherECircularSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
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
	MenuName = "TeacherECircularSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="TeacherSummaryEntity";
        selectBoxes= ['examType','signStatus','class'];
        fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnTeacherECircularSummarypostSelectBoxMaster()
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

function fnTeacherECircularSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
        $scope.circularDescription = "";
	$scope.circularID = "";
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	$scope.signStatus = "Select option";
	$scope.examtypereadOnly = true;
	$scope.classReadonly = true;
	$scope.standardreadOnly = true;
	$scope.sectionreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameInputReadOnly = true;
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
	lscreenKeyObject.teacherID=$scope.TeacherECircularSummaryTable[$scope.selectedIndex].teacherID;
	lscreenKeyObject.circularID=$scope.TeacherECircularSummaryTable[$scope.selectedIndex].circularID;
	fninvokeDetailScreen('TeacherECircular',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnTeacherECircularSummaryView() {
	var emptyTeacherECircularSummary = {
		filter: {
		    teacherName:"",
			teacherID: "",
                        circularDescription : "",
	                circularID : "",
			class: 'Select option',
			exam: 'Select option',
			signStatus: 'Select option'
		},
		SummaryResult: [{
				teacherName: "",
				teacherID: "",
				exam: "",
				class: "",
				date:"",
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTeacherECircularSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View"; 
        if($scope.teacherID!=null)
	dataModel.filter.teacherID = $scope.teacherID;
        if($scope.teacherName!=null)
	dataModel.filter.teacherName = $scope.teacherName;
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
	var response = fncallBackend('TeacherECircularSummary', 'View', dataModel, [{entityName:"teacherID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnTeacherECircularSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &                 
//						($scope.teacherName == '' || $scope.teacherName == null) &
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

function fnTeacherECircularSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;
                case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.TeacherECircularSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;

	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}
// Screen Specific Mandatory Validation Ends
function fnTeacherECircularSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.teacherName = null;
	    $scope.teacherID = null;
            $scope.circularDescription = null;
	    $scope.circularID = null;
	    $scope.class = 'Select option';
	    $scope.exam = 'Select option';
	    $scope.signStatus = "Select option";
	    
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.TeacherECircularSummaryTable = null;
		$scope.TeacherECircularSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.examtypereadOnly = false;
		$scope.classReadonly = false;
	    $scope.teacherIDreadOnly = false;
	    $scope.teacherNamereadOnly = false;
            $scope.teacherNameInputReadOnly = true;
	    $scope.signStatusreadOnly = false;
	    $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.examtypereadOnly = true;
	    $scope.teacherIDreadOnly = true;
		$scope.classReadonly = true;
	    $scope.teacherNamereadOnly = true;
            $scope.teacherNameInputReadOnly = true;
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
		case 'TeacherECircularSummaryTable':
		   
			var TeacherECircularSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     TeacherECircularSummaryTable[index] = new Object();
					 TeacherECircularSummaryTable[index].idx=index;
					 TeacherECircularSummaryTable[index].checkBox=false;
					 TeacherECircularSummaryTable[index].teacherID=value.teacherID;
					 TeacherECircularSummaryTable[index].teacherName=value.teacherName;
					 TeacherECircularSummaryTable[index].signStatus=value.signStatus;
                                         TeacherECircularSummaryTable[index].circularID=value.circularID;
					}
			return TeacherECircularSummaryTable;
			break ;
		}
	}
	
function fnTeacherECircularSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		     $scope.examtypereadOnly = true;
	         $scope.teacherIDreadOnly = true;
                 $scope.teacherNameInputReadOnly = true;
	         $scope.teacherNamereadOnly = true;
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
                $scope.teacherName =response.body.filter.teacherName;
                $scope.teacherID =response.body.filter.teacherID;
                $scope.circularDescription =response.body.filter.circularDescription;
                $scope.circularID =response.body.filter.circularID;
                $scope.class =response.body.filter.class;
                $scope.exam =response.body.filter.exam;
                
		        $scope.signStatus =response.body.filter.signStatus;
                $scope.TeacherECircularSummaryTable=fnConvertmvw('TeacherECircularSummaryTable',response.body.SummaryResult);
	        	$scope.TeacherECircularSummaryCurPage = 1;
		        $scope.TeacherECircularSummaryShowObject=$scope.fnMvwGetCurPageTable('teacherECircularSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}