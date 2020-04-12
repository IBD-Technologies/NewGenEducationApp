/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.paymentID = "";
	$scope.paymentDate = "";
	$scope.studentName = "";
	$scope.feeType = "";
        $scope.class = "Select option";
	$scope.feeID = "";
	$scope.amount = "";
	$scope.paymentPaid = "";
	$scope.paymentMode = "";
	$scope.authStat = "";
	$scope.toDate = "";
        $scope.fromDate="";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.fees = Institute.FeeMaster;
	$scope.AuthType = Institute.AuthStatusMaster;
	
	$scope.payment=Institute.PayMentMaster;
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
	$scope.paymentIDreadOnly = false;
	$scope.paymentDatereadOnly = false;
	$scope.paymentPaidreadOnly = false;
	$scope.paymentModereadOnly=false;
	$scope.studentNamereadOnly = false;
        $scope.studentNameInputReadOnly=true;
	$scope.feeTypereadOnly = false;
	$scope.feeAmountreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
        $scope.fromDatereadOnly = false;
        $scope.toDatereadOnly = false;
        $( "#fromDate" ).datepicker( "option", "disabled", false );
        $( "#toDate" ).datepicker( "option", "disabled", false );
	// Specific Screen Scope Ends

	// multiple View Starts
	$scope.StudentPaymentSummaryCurPage = 0;
	$scope.StudentPaymentSummaryTable = null;
	$scope.StudentpaymentSummaryShowObject = null;
	// multiple View Ends
	//Multiple View Scope Function Starts 
	
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'studentPaymentSummary') {
			if ($scope.StudentPaymentSummaryTable != null && $scope.StudentPaymentSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.StudentPaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentPaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentpaymentSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.StudentPaymentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentPaymentSummaryTable = lsvwObject.tableObject;
				$scope.StudentpaymentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'studentPaymentSummary') {
			if ($scope.StudentPaymentSummaryTable != null && $scope.StudentPaymentSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.StudentPaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentPaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentpaymentSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.StudentPaymentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentPaymentSummaryTable = lsvwObject.tableObject;
				$scope.StudentpaymentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentPaymentSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					paymentID: "",
                                        class: "",
				    paymentDate: "",
				    studentID: "",
				    studentName: "",
				    amount:"",
			    	    feeType: "",
				    feeID: "",
				    paymentMode: "",
				    paymentPaid: ""
				};
				if ($scope.StudentPaymentSummaryTable == null)
					$scope.StudentPaymentSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentPaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentPaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentpaymentSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.StudentPaymentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentPaymentSummaryTable = lsvwObject.tableObject;
				$scope.StudentpaymentSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'studentPaymentSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.StudentPaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.StudentPaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.StudentpaymentSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.StudentPaymentSummaryCurPage = lsvwObject.curPage;
				$scope.StudentPaymentSummaryTable = lsvwObject.tableObject;
				$scope.StudentpaymentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'studentPaymentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentPaymentSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentPaymentSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentpaymentSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'studentPaymentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.StudentPaymentSummaryCurPage;
			lsvwObject.tableObject = $scope.StudentPaymentSummaryTable;
			lsvwObject.screenShowObject = $scope.StudentpaymentSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'studentPaymentSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.StudentPaymentSummaryTable);
			
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
    MenuName = "StudentPayMentSummary";
    window.parent.nokotser=$("#nokotser").val();
    window.parent.Entity="StudentSummaryEntity";
  
    fnDatePickersetDefault('fromDate',fninstantEventHandler);
    fnDatePickersetDefault('toDate',fninstantEventHandler);
    fnsetDateScope();  
    selectBoxes= ['paymentMode','feeType','authStatus','class'];
	 fnGetSelectBoxdata(selectBoxes);
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnStudentPayMentSummarypostSelectBoxMaster()
{
    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if(Institute.ClassMaster.length>0){
    $scope.classes=Institute.ClassMaster;
    $scope.fees = Institute.FeeMaster;
    window.parent.fn_hide_parentspinner();    
     $scope.$apply();
    
}
}
function fnStudentPayMentSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.paymentID = "";
	$scope.paymentDate = "";
	$scope.studentName = "";
	$scope.feeType = "";
        $scope.class = "Select option";
	$scope.feeID = "";
	$scope.amount = "";
	$scope.paymentPaid = "";
	$scope.paymentMode = "";
	$scope.authStat = "";
        $scope.toDate = "";
        $scope.fromDate="";
	$scope.paymentIDreadOnly = true;
	$scope.paymentDatereadOnly = true;
        $scope.fromDatereadOnly = true;
        $scope.toDatereadOnly = true;
        $( "#fromDate" ).datepicker( "option", "disabled", true );
        $( "#toDate" ).datepicker( "option", "disabled", true );
	$scope.paymentPaidreadOnly = true;
	$scope.feeIDreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
        $scope.studentNameInputReadOnly=true;
	$scope.feeTypereadOnly = true;
	$scope.feeAmountreadOnly = true;
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
	lscreenKeyObject.paymentID=$scope.StudentPaymentSummaryTable[$scope.selectedIndex].paymentID;
        lscreenKeyObject.studentID=$scope.StudentPaymentSummaryTable[$scope.selectedIndex].studentID;
	lscreenKeyObject.paymentDate=$scope.StudentPaymentSummaryTable[$scope.selectedIndex].paymentDate;
	fninvokeDetailScreen('StudentPayment',lscreenKeyObject,$scope);
	
	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentPayMentSummaryView() {
	var emptyStudentPayMentSummary = {
		filter: {
//			paymentID: "",
			fromDate : "",
		        toDate: "",
			studentName: "",
			studentID: "",
//			feeID: "",
//                        class: "Select option",
//			feeType: ""
//			paymentMode: "",
//			authStat: ""
		},
		SummaryResult: [{
				paymentID: "",
				paymentDate: "",
				studentID: "",
				amount:"",
				feeType: "",
				paymentPaid: ""
			}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyStudentPayMentSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation="View";
        if($scope.paymentDate!=null)
	dataModel.filter.paymentDate = $scope.paymentDate;
        if($scope.paymentID!=null)
	dataModel.filter.paymentID = $scope.paymentID;
        if($scope.feeType!=null)
	dataModel.filter.feeType = $scope.feeType;
       if($scope.class!=null)
	dataModel.filter.class = $scope.class;
        if($scope.feeID!=null)
	dataModel.filter.feeID = $scope.feeID;
        if($scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
        if($scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
        if($scope.paymentMode!=null)
	dataModel.filter.paymentMode = $scope.paymentMode;
        if($scope.paymentMode!=null)
	dataModel.filter.authStat = $scope.authStat;
        if($scope.fromDate!=null)
	dataModel.filter.fromDate = $scope.fromDate;
       if($scope.toDate!=null)
	dataModel.filter.toDate = $scope.toDate;
	// Screen Specific DataModel Ends
//	var response = fncallBackend('StudentPayMentSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
        var response = fncallBackend('StudentPaymentSummary', 'View', dataModel, [{entityName:"studentID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnStudentPayMentSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
//					if ((($scope.studentName == '' || $scope.studentName == null) &                 
//					    ($scope.fromDate == '' || $scope.fromDate == null) &
//                                            ($scope.toDate == '' || $scope.toDate == null) &
//			    		    ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') &
//					    ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') &
//                                            ($scope.paymentMode == '' || $scope.paymentMode == null || $scope.paymentMode == 'Select option') &
//					    ($scope.authStat == '' || $scope.authStat == null || $scope.authStat == 'Select option')))
//		
//					{
//						fn_Show_Exception('FE-VAL-028');
//						return false;
//					}
                                        
//                                        if ($scope.studentName == '' || $scope.studentName == null) {
//		  		               fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
//				               return false;
//			                 }
//                                         if ($scope.studentID == '' || $scope.studentID == null) {
//		  		                fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				                return false;
//			                  }
//                                        
                                        if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
			                return false;
                        }
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

function fnStudentPayMentSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.StudentPaymentSummaryTable,$scope))//Generic For Summary
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
function fnStudentPayMentSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.paymentID = "";
	    $scope.paymentDate = "";
	    $scope.studentName = "";
	    $scope.feeType = "";
	    $scope.feeID = "";
	    $scope.amount = "";
	    $scope.paymentPaid = "";
            $scope.class = "Select option";
	    $scope.paymentMode = "";
	    $scope.authStat = "";
            $scope.toDate = "";
            $scope.fromDate="";
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.StudentPaymentSummaryTable = null;
		$scope.StudentpaymentSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.paymentIDreadOnly = false;
	    $scope.paymentDatereadOnly = false;
	    $scope.paymentPaidreadOnly = false;
    	$scope.feeIDreadOnly = false;
	    $scope.studentIDreadOnly = false;
	    $scope.studentNamereadOnly = false;
            $scope.studentNameInputReadOnly=true;
	    $scope.feeTypereadOnly = false;
	    $scope.feeAmountreadOnly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
	    $scope.mastershow=true;
	    $scope.detailshow=false;
            $scope.fromDatereadOnly = false;
            $scope.toDatereadOnly = false;           
            $( "#fromDate" ).datepicker( "option", "disabled", false );
            $( "#toDate" ).datepicker( "option", "disabled", false );
		}
	    else {
	    $scope.paymentIDreadOnly = true;
	    $scope.paymentDatereadOnly = true;
	    $scope.paymentPaidreadOnly = true;
    	$scope.feeIDreadOnly = true;
	    $scope.studentIDreadOnly = true;
	    $scope.studentNamereadOnly = true;
            $scope.studentNameInputReadOnly=true;
	    $scope.feeTypereadOnly = true;
	    $scope.feeAmountreadOnly = true;
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
            
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
		case 'StudentPaymentSummaryTable':
		   
			var StudentPaymentSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     StudentPaymentSummaryTable[index] = new Object();
					 StudentPaymentSummaryTable[index].idx=index;
					 StudentPaymentSummaryTable[index].checkBox=false;
					 StudentPaymentSummaryTable[index].studentID=value.studentID;
					 StudentPaymentSummaryTable[index].studentName=value.studentName;
					 StudentPaymentSummaryTable[index].paymentID=value.paymentID;
                                         StudentPaymentSummaryTable[index].paymentDate=value.paymentDate;
					 StudentPaymentSummaryTable[index].paymentPaid=value.paymentPaid;
					}
			return StudentPaymentSummaryTable;
			break ;
		}
		
	}
	
 function fnStudentPayMentSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
                  $scope.fromDatereadOnly = true;
                  $scope.toDatereadOnly = true;
                  $( "#fromDate" ).datepicker( "option", "disabled", true );
                  $( "#toDate" ).datepicker( "option", "disabled", true );
	          $scope.paymentIDreadOnly = true;
	          $scope.paymentDatereadOnly = true;
	          $scope.paymentPaidreadOnly = true;
    	          $scope.feeIDreadOnly = true;
	          $scope.studentIDreadOnly = true;
	          $scope.studentNamereadOnly = true;
                  $scope.studentNameInputReadOnly=true;
	          $scope.feeTypereadOnly = true;
	          $scope.feeAmountreadOnly = true;
	          $scope.authStatreadOnly = true;
	          $scope.recordStatreadOnly = true;
		      // Specific Screen Scope Ends
		      // Generic Field Starts
		      $scope.mastershow = false;
		      $scope.detailshow = true;
		      $scope.mvwAddDeteleDisable = true; //Multiple View
		       // Generic Field Ends
		       // Specific Screen Scope Response Starts	
                $scope.studentID = response.body.filter.studentID;
                $scope.studentName =response.body.filter.studentName;
                $scope.feeType=response.body.filter.feeType;
                $scope.fromDate =response.body.filter.fromDate;
               $scope.toDate =response.body.filter.toDate;
//              $scope.paymentID = response.body.filter.paymentID;
//              $scope.paymentMode =response.body.filter.paymentMode;;
//              $scope.paymentDate =response.body.filter.paymentDate;;
//              $scope.class = response.body.filter.class;
	        $scope.authStat =response.body.filter.authStat;
                $scope.StudentFeeManagementSummaryShowObject = null;
		        $scope.SummaryResult ={};
                $scope.StudentPaymentSummaryTable=fnConvertmvw('StudentPaymentSummaryTable',response.body.SummaryResult);
		        $scope.StudentPaymentSummaryCurPage = 1;
		        $scope.StudentpaymentSummaryShowObject=$scope.fnMvwGetCurPageTable('studentPaymentSummary');
		       //Multiple View Response Ends 
                 $scope.selectedIndex =0;// Summary Field
        }
		return true;
}
