/* 
    Author     :IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	//Screen Specific Scope starts
        $scope.studentName = "";
        $scope.studentID = "";
        $scope.class = 'Select option';
	$scope.authStat = '';
	
	$scope.AuthType = Institute.AuthStatusMaster;
	
	$scope.authStatreadOnly = false;
	//Screen Specific Scope Ends
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
	// multiple View Starts
	$scope.StudentTimeTableSummaryCurPage = 0;
	$scope.StudentTimeTableSummaryTable = null;
	$scope.StudentTimeTableSummaryShowObject = null;
	// multiple View Ends
        //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'CTBSummary') {
			if ($scope.StudentTimeTableSummaryTable != null && $scope.StudentTimeTableSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentTimeTableSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.StudentTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.StudentTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'CTBSummary') {
			if ($scope.StudentTimeTableSummaryTable != null && $scope.StudentTimeTableSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentTimeTableSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.StudentTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.StudentTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'CTBSummary') {
				emptyTableRec = {
					idx: 0,
					 checkBox: false,
					 class: "",
				     authStat: "",
				     recordStat: ""
				};
				if ($scope.StudentTimeTableSummaryTable == null)
					$scope.StudentTimeTableSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentTimeTableSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.StudentTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.StudentTimeTableSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'CTBSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentTimeTableSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentTimeTableSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentTimeTableSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentTimeTableSummaryCurPage = lsvwObject.curPage;
				$scope.StudentTimeTableSummaryTable = lsvwObject.tableObject;
				$scope.StudentTimeTableSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'CTBSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentTimeTableSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentTimeTableSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentTimeTableSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'CTBSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentTimeTableSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentTimeTableSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentTimeTableSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'CTBSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentTimeTableSummaryTable);
			
		}
	};

	//Multiple View Scope Function ends 	
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
    MenuName = "StudentTimeTableSummary";
    window.parent.nokotser = $("#nokotser").val();
    window.parent.Entity = "StudentSummaryEntity";
    selectBoxes= ['authStatus','class'];
    fnGetSelectBoxdata(selectBoxes);
});
//Default Load Record Ends

function fnStudentTimeTableSummarypostSelectBoxMaster()
{
   
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(Institute.ClassMaster.length>0){
    $scope.classes=Institute.ClassMaster;
       window.parent.fn_hide_parentspinner();
       $scope.$apply();
 }
}
// Cohesive Query Framework Starts
function fnStudentTimeTableSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
        $scope.studentName = '';
        $scope.studentID = '';
        $scope.class = 'Select option';
	$scope.authStat = '';
	
	$scope.studentNamereadOnly = true;
        $scope.studentIDreadOnly = true;
	$scope.authStatreadOnly = true;
	$scope.recordStatreadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.searchShow = false;
	$scope.selectedIndex =0;
	// Generic Field Ends	
	var lscreenKeyObject = new Object();
	lscreenKeyObject.studentID=$scope.StudentTimeTableSummaryTable[$scope.selectedIndex].studentID;
	
	fninvokeDetailScreen('StudentTimeTable',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentTimeTableSummaryView() {
	var emptyStudentTimeTableSummary = {
		filter: {
			studentID: '',
                        studentName: '',
                        class:"Select option",
			authStat: 'Select option',
			recordStat: 'Select option'
		},
		SummaryResult: [{
				studentName: "",
                                studentID: "",
				authStat: "",
				recordStat: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentTimeTableSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if( $scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
         if( $scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
        if( $scope.class!=null)
	dataModel.filter.class = $scope.class;
        if( $scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('StudentTimeTableSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentTimeTableSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.studentName == '' || $scope.studentName == null) &
                                                ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
		
					{
						fn_Show_Exception('FE-VAL-028');
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

function fnStudentTimeTableSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 if(!fngetSelectedIndex($scope.StudentTimeTableSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation Ends
//Cohesive back Framework Starts
function fnStudentTimeTableSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		 // Screen Specific Scope Starts
	     $scope.studentName = '';
             $scope.studentID = '';
              $scope.class = 'Select option';
	     $scope.authStat = '';
	     
	     $scope.mvwAddDeteleDisable = true; //Multiple View
	     $scope.StudentTimeTableSummaryTable = null;
	     $scope.StudentTimeTableSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.studentNamereadOnly = false;
            $scope.studentIDreadOnly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
		}
	    else {
	   $scope.studentNamereadOnly = true;
           $scope.studentIDreadOnly = true;
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
}
// Cohesive Back Framework Ends
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'StudentTimeTableSummaryTable':
		   
			var StudentTimeTableSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentTimeTableSummaryTable[index] = new Object();
					 StudentTimeTableSummaryTable[index].idx=index;
					 StudentTimeTableSummaryTable[index].checkBox=false;
					 StudentTimeTableSummaryTable[index].studentName=value.studentName;
                                          StudentTimeTableSummaryTable[index].studentID=value.studentID;
                                           StudentTimeTableSummaryTable[index].class=value.class;
					 StudentTimeTableSummaryTable[index].authStat=value.authStat;
					 StudentTimeTableSummaryTable[index].recordStat=value.recordStat;
					}
			return StudentTimeTableSummaryTable;
			break ;
		}
	}
	
function fnStudentTimeTableSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		 // Specific Screen Scope Starts
	        $scope.studentNamereadOnly = true;
                $scope.studentIDreadOnly = true;
	        $scope.authStatreadOnly = true;
	        $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                $scope.studentName = response.body.studentName;
                $scope.studentID = response.body.studentID;
                  $scope.classs = response.body.class;
		$scope.authStat = response.body.authStat;
                //Multiple View Response Starts 
		$scope.StudentTimeTableSummaryTable=fnConvertmvw('StudentTimeTableSummaryTable',response.body.SummaryResult);
		$scope.StudentTimeTableSummaryCurPage = 1;
		$scope.StudentTimeTableSummaryShowObject=$scope.fnMvwGetCurPageTable('CTBSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
            }
		return true;

}