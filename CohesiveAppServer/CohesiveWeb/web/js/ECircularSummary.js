/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
        $scope.circularType = "";
	$scope.authStat = "";
        $scope.toDate = "";
        $scope.fromDate="";
	$scope.AuthType = Institute.AuthStatusMaster;
        $scope.CircularType= Institute.CircularTypeMaster;
	//$scope.Fee = Institute.FeeStatus;
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
	$scope.authStatreadOnly = false;
        $scope.circularTypereadOnly=false;
        $scope.fromDatereadOnly = false;
        $scope.toDatereadOnly = false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	// Specific Screen Scope Ends	
	// multiple View Starts
	$scope.instituteECircularSummaryCurPage = 0;
	$scope.instituteECircularSummaryTable = null;
	$scope.instituteECircularSummaryShowObject = null;
	// multiple View Ends
    //Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'instituteECircularSummary') {
			if ($scope.instituteECircularSummaryTable != null && $scope.instituteECircularSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.instituteECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteECircularSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.instituteECircularSummaryCurPage = lsvwObject.curPage;
				$scope.instituteECircularSummaryTable = lsvwObject.tableObject;
				$scope.instituteECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'instituteECircularSummary') {
			if ($scope.instituteECircularSummaryTable != null && $scope.instituteECircularSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.instituteECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteECircularSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.instituteECircularSummaryCurPage = lsvwObject.curPage;
				$scope.instituteECircularSummaryTable = lsvwObject.tableObject;
				$scope.instituteECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'instituteECircularSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					assignmentID:"",
					assginmentType:"",
				    class:"",
				    subjectID:"",
				    subjectName:"",
				    dueDate: ""
				};
				if ($scope.instituteECircularSummaryTable == null)
					$scope.instituteECircularSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.instituteECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteECircularSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.instituteECircularSummaryCurPage = lsvwObject.curPage;
				$scope.instituteECircularSummaryTable = lsvwObject.tableObject;
				$scope.instituteECircularSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'instituteECircularSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.instituteECircularSummaryCurPage;
				lsvwObject.tableObject = $scope.instituteECircularSummaryTable;
				lsvwObject.screenShowObject = $scope.instituteECircularSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.instituteECircularSummaryCurPage = lsvwObject.curPage;
				$scope.instituteECircularSummaryTable = lsvwObject.tableObject;
				$scope.instituteECircularSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'instituteECircularSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.instituteECircularSummaryCurPage;
			lsvwObject.tableObject = $scope.instituteECircularSummaryTable;
			lsvwObject.screenShowObject = $scope.instituteECircularSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'instituteECircularSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.instituteECircularSummaryCurPage;
			lsvwObject.tableObject = $scope.instituteECircularSummaryTable;
			lsvwObject.screenShowObject = $scope.instituteECircularSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'instituteECircularSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.instituteECircularSummaryTable);
			
		}
	};
	//Multiple View Scope Function ends 
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
	MenuName = "ECircularSummary";
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="InstituteSummaryEntity";
        
	fnDatePickersetDefault('fromDate',fninstantEventHandler);
        fnDatePickersetDefault('toDate',fninstantEventHandler);
	fnsetDateScope();
	selectBoxes= ['assignmentType','subject','authStatus'];
        fnGetSelectBoxdata(selectBoxes);
	
});
//Default Load Record End
// Cohesive Query Framework Starts

function fnECircularSummarypostSelectBoxMaster()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   if(Institute.SubjectMaster.length>0)
      {   
     $scope.subjects = Institute.SubjectMaster;
	 window.parent.fn_hide_parentspinner();
          $scope.$apply();
}
}
function fnECircularSummaryDetail() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.authStat = "";
	$scope.circularType = "";
        $scope.toDate = "";
        $scope.fromDate="";
	$scope.authStatreadOnly = true;
        $scope.circularTypereadOnly=true;
        $scope.fromDatereadOnly = true;
        $scope.toDatereadOnly = true;
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
	lscreenKeyObject.circularID=$scope.instituteECircularSummaryTable[$scope.selectedIndex].circularID;
	
	fninvokeDetailScreen('ECircular',lscreenKeyObject,$scope);
	
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnECircularSummaryView() {
	var emptyECircularSummary = {
		filter:{
		    circularType: "",
			fromDate: "",
			toDate: "",
			authStat: ""},
		SummaryResult: [{
				circularID: "",
				circularDescription:""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyECircularSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation='View';
        
        
       if($scope.circularType!=null)
	dataModel.filter.circularType = $scope.circularType;
      if($scope.fromDate!=null)
	dataModel.filter.fromDate = $scope.fromDate;
         if($scope.toDate!=null)
	dataModel.filter.toDate = $scope.toDate;
       if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ECircularSummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts      
function fnECircularSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((
//						($scope.circularType == '' || $scope.circularType == null ) &
//						($scope.fromDate == '' || $scope.fromDate == null) &
//						($scope.toDate == '' || $scope.toDate == null ) &
//						($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
                                           if($scope.fromDate == '' || $scope.fromDate == null){
                                            
                                               fn_Show_Exception_With_Param('FE-VAL-001', ['From Date']);
				               return false;
                                        
                                         }
                                        
                                        if($scope.toDate == '' || $scope.toDate == null){
                                            
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

function fnECircularSummaryDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			
				return true;

			break;

		case 'Save':
			
				return true;

			break;
                        
                case 'Detail':
			var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
			 
			 if(!fngetSelectedIndex($scope.instituteECircularSummaryTable,$scope))//Generic For Summary
			   return false;
			 return true;  
			break;        
	}
	return true;
}

// Screen Specific Mandatory Validation Ends
function fnECircularSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.circularType = "";
	    $scope.fromDate = "";
            $scope.toDate = "";
	    $scope.authStat = "";
	    
		$scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.instituteECircularSummaryTable = null;
		$scope.instituteECircularSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
            $scope.circularTypereadOnly=false;        
	    $scope.authStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
                 $scope.fromDatereadOnly = false;
            $scope.toDatereadOnly = false;           
            $( "#fromDate" ).datepicker( "option", "disabled", false );
            $( "#toDate" ).datepicker( "option", "disabled", false );
		}
	    else {
            $scope.circularTypereadOnly=true;    
	    $scope.authStatreadOnly = true;
             $scope.fromDatereadOnly = false;
            $scope.toDatereadOnly = false;           
            $( "#fromDate" ).datepicker( "option", "disabled", false );
            $( "#toDate" ).datepicker( "option", "disabled", false );
	       }
		
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
}
// Cohesive Create Framework Ends
function fninstantEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.fromDate = $.datepicker.formatDate('dd-mm-yy', $("#fromDate").datepicker("getDate")),
	$scope.toDate = $.datepicker.formatDate('dd-mm-yy', $("#toDate").datepicker("getDate")),
		
            $scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.instant = $.datepicker.formatDate('dd-mm-yy', $("#instantDate").datepicker("getDate"));
         $( "#fromDate" ).datepicker('setDate','');
          $( "#toDate" ).datepicker('setDate','');
	  $scope.instant = null;
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'instituteECircularSummaryTable':
		   
			var instituteECircularSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     instituteECircularSummaryTable[index] = new Object();
					 instituteECircularSummaryTable[index].idx=index;
					 instituteECircularSummaryTable[index].checkBox=false;
					// instituteECircularSummaryTable[index].assignmentID=value.assignmentID;
					// instituteECircularSummaryTable[index].section=value.section;
					 instituteECircularSummaryTable[index].circularID=value.circularID;
					 instituteECircularSummaryTable[index].description=value.description;
                                         instituteECircularSummaryTable[index].circularType=value.circularType;
                                         instituteECircularSummaryTable[index].circularDate=value.circularDate;
					}
			return instituteECircularSummaryTable;
			break ;
		}
	}
	
function fnECircularSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
	      $scope.authStatreadOnly = true;
              $scope.circularTypereadOnly=false;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
              //  $scope.assignmentID =response.body.filter.assignmentID;
                //$scope.dueDate =response.body.filter.dueDate;
		        $scope.authStat =response.body.filter.authStat;
                        $scope.circularType = response.body.filter.circularType;
                $scope.instituteECircularSummaryTable=fnConvertmvw('instituteECircularSummaryTable',response.body.SummaryResult);
		        $scope.instituteECircularSummaryCurPage = 1
		        $scope.instituteECircularSummaryShowObject=$scope.fnMvwGetCurPageTable('instituteECircularSummary');
		//Multiple View Response Ends 
                $scope.selectedIndex =0;
        }
		return true;

}