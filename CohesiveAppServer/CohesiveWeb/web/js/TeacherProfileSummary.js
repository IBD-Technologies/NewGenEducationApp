/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.authStat = "";
	
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	$scope.AuthType = Institute.AuthStatusMaster;
	
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
        $scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	//Search Level Scope Starts
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
	//Search Level Scope Ends
	// multiple View Starts
	$scope.TeacherProfileSummaryCurPage = 0;
	$scope.TeacherProfileSummaryTable = null;
	$scope.TeacherProfileSummaryShowObject = null;
	// multiple View Ends
	
		//Multiple View Scope Function Starts 
	    $scope.fnMvwBackward = function (tableName, $event) {
		if (tableName == 'TeacherProfileSummary') {
			if ($scope.TeacherProfileSummaryTable != null && $scope.TeacherProfileSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.TeacherProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherProfileSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.TeacherProfileSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherProfileSummaryTable = lsvwObject.tableObject;
				$scope.TeacherProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'TeacherProfileSummary') {
			if ($scope.TeacherProfileSummaryTable != null && $scope.TeacherProfileSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.TeacherProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherProfileSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.TeacherProfileSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherProfileSummaryTable = lsvwObject.tableObject;
				$scope.TeacherProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherProfileSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					teacherName: "",
				    teacherID: ""
				};
				if ($scope.TeacherProfileSummaryTable == null)
					$scope.TeacherProfileSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherProfileSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.TeacherProfileSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherProfileSummaryTable = lsvwObject.tableObject;
				$scope.TeacherProfileSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'TeacherProfileSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.TeacherProfileSummaryCurPage;
				lsvwObject.tableObject = $scope.TeacherProfileSummaryTable;
				lsvwObject.screenShowObject = $scope.TeacherProfileSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.TeacherProfileSummaryCurPage = lsvwObject.curPage;
				$scope.TeacherProfileSummaryTable = lsvwObject.tableObject;
				$scope.TeacherProfileSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'TeacherProfileSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherProfileSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherProfileSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherProfileSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'TeacherProfileSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.TeacherProfileSummaryCurPage;
			lsvwObject.tableObject = $scope.TeacherProfileSummaryTable;
			lsvwObject.screenShowObject = $scope.TeacherProfileSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'TeacherProfileSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.TeacherProfileSummaryTable);
			
		}
	};

	//Multiple View Scope Function ends 	
});
//--------------------------------------------------------------------------------------------------------------
$(document).ready(function () {
	MenuName = "TeacherProfileSummary";
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="TeacherSummaryEntity";
        window.parent.fn_hide_parentspinner();      
	selectBoxes= ['authStatus'];
        fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts
function fnTeacherProfileSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.authStat = "";
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.authStatreadOnly = true;
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
	lscreenKeyObject.teacherID=$scope.TeacherProfileSummaryTable[$scope.selectedIndex].teacherID;
	
	fninvokeDetailScreen('TeacherProfile',lscreenKeyObject,$scope);
	
	
	return true;
}
// Cohesive Query Framework Ends
// Cohesive View Framework Starts
function fnTeacherProfileSummaryView() {
	var emptyTeacherProfileSummary = {
			filter: {
			teacherName: "",
			teacherID: "",
			date: "",
			authStat: ''
		},
		SummaryResult: [{
				teacherName: "",
				teacherID: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyTeacherProfileSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        if($scope.teacherName!=null)
	dataModel.filter.teacherName = $scope.teacherName;
          if($scope.teacherID!=null)
	dataModel.filter.teacherID = $scope.teacherID;
      if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('TeacherProfileSummary', 'View', dataModel, [{entityName:"teacherID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnTeacherProfileSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.teacherName == '' || $scope.teacherName == null) &                 
						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == "Select option")))
		
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

function fnTeacherProfileSummaryDefaultandValidate(operation) {
	switch (operation) {
		case 'View':
			return true;
			break;

		case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.TeacherProfileSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;


	}
	return true;
}


// Screen Specific Mandatory Validation Ends
function fnTeacherProfileSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
		$scope.teacherName = "";
	    $scope.teacherID = "";
	    $scope.authStat = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.TeacherProfileSummaryTable = null;
		$scope.TeacherProfileSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.teacherNamereadOnly = false;
       	    $scope.teacherIDreadOnly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.teacherNamereadOnly = true;
       	    $scope.teacherIDreadOnly = true;
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
		case 'TeacherProfileSummaryTable':
		   
			var TeacherProfileSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     TeacherProfileSummaryTable[index] = new Object();
					 TeacherProfileSummaryTable[index].idx=index;
					 TeacherProfileSummaryTable[index].checkBox=false;
					 TeacherProfileSummaryTable[index].teacherName=value.teacherName;
					 TeacherProfileSummaryTable[index].teacherID=value.teacherID;
					}
			return TeacherProfileSummaryTable;
			break ;
		}
	}
	
function fnTeacherProfileSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
	         $scope.teacherNamereadOnly = true;
       	         $scope.teacherIDreadOnly = true;
	         $scope.authStatreadOnly = true;
	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.teacherName = response.body.filter.teacherName;
		$scope.teacherID =response.body.filter.teacherID;
		$scope.authStat =response.body.filter.authStat;
		$scope.SummaryResult ={};
		 }
                else
                {
                //Multiple View Response Starts 
		$scope.TeacherProfileSummaryTable=fnConvertmvw('TeacherProfileSummaryTable',response.body.SummaryResult);
		$scope.TeacherProfileSummaryCurPage = 1
		$scope.TeacherProfileSummaryShowObject=$scope.fnMvwGetCurPageTable('TeacherProfileSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;// Summary Field
        }
		return true;

}
}