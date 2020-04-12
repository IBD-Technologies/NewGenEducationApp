/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 

var app = angular.module('SubScreen', ['BackEnd', 'Summaryoperation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.paymentID = "";
	$scope.paymentDate ="";
	$scope.paymentPaid = "";
	$scope.authStat = "";
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
	// Screen Specific Scope Start
	$scope.paymentDatereadOnly = false;
	$scope.paymentPaidreadOnly = false;
	$scope.feeIDreadOnly = false;
	$scope.paymentPaidreadOnly = false;
	$scope.feeAmountreadOnly = false;
	$scope.authStatreadOnly = false;
	$scope.recordStatreadOnly = false;
	// Specific Screen Scope Ends
	
	// multiple View Starts
	$scope.PaymentSummaryCurPage = 0;
	$scope.PaymentSummaryTable = null;
	$scope.paymentSummaryShowObject = null;
	// multiple View Ends
	
	
	
		//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'paymentSummary') {
			if ($scope.PaymentSummaryTable != null && $scope.PaymentSummaryTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.PaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.PaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.paymentSummaryShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.PaymentSummaryCurPage = lsvwObject.curPage;
				$scope.PaymentSummaryTable = lsvwObject.tableObject;
				$scope.paymentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'paymentSummary') {
			if ($scope.PaymentSummaryTable != null && $scope.PaymentSummaryTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.PaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.PaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.paymentSummaryShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.PaymentSummaryCurPage = lsvwObject.curPage;
				$scope.PaymentSummaryTable = lsvwObject.tableObject;
				$scope.paymentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'paymentSummary') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
					studentID: "",
					paymentID: "",
					paymentDate: "",
					feeAmount: "",
					paymentPaid: ""
				};
				if ($scope.PaymentSummaryTable == null)
					$scope.PaymentSummaryTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.PaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.PaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.paymentSummaryShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.PaymentSummaryCurPage = lsvwObject.curPage;
				$scope.PaymentSummaryTable = lsvwObject.tableObject;
				$scope.paymentSummaryShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'paymentSummary') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.PaymentSummaryCurPage;
				lsvwObject.tableObject = $scope.PaymentSummaryTable;
				lsvwObject.screenShowObject = $scope.paymentSummaryShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.PaymentSummaryCurPage = lsvwObject.curPage;
				$scope.PaymentSummaryTable = lsvwObject.tableObject;
				$scope.paymentSummaryShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'paymentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.PaymentSummaryCurPage;
			lsvwObject.tableObject = $scope.PaymentSummaryTable;
			lsvwObject.screenShowObject = $scope.paymentSummaryShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'paymentSummary') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.PaymentSummaryCurPage;
			lsvwObject.tableObject = $scope.PaymentSummaryTable;
			lsvwObject.screenShowObject = $scope.paymentSummaryShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'paymentSummary') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.PaymentSummaryTable);
			
		}
	};


	//Multiple View Scope Function ends 
	
	
});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "InstitutePaymentSummary";
        
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="InstituteSummaryEntity";
        //window.parent.fn_hide_parentspinner();   
    	fnDatePickersetDefault('paymentDate',fnpaymentDateEventHandler);
    	fnsetDateScope();
        selectBoxes= ['authStatus'];
        fnGetSelectBoxdata(selectBoxes);
        
         $('.currency').blur(function()
                {
                    $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
                });
	
	//-----------------------  screen Specific Default Recors      --------------------------------------------------	
});
// Cohesive Query Framework Starts

function fnInstitutePaymentSummarypostSelectBoxMaster()
{
   
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   if(Institute.FeeMaster.length>0)
      {   
	$scope.fees = Institute.FeeMaster;
        window.parent.fn_hide_parentspinner(); 
        $scope.$apply();
    
}
}
function fnInstitutePaymentSummaryDetail() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts
	$scope.paymentID = "";
	$scope.paymentDate = "";
	$scope.paymentPaid = "";
	$scope.authStat = "";
	
	$scope.paymentIDreadOnly = true;
	$scope.paymentDatereadOnly = true;
	$scope.paymentPaidreadOnly = true;
	$scope.authStatreadOnly = true;
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
	lscreenKeyObject.paymentID=$scope.PaymentSummaryTable[$scope.selectedIndex].paymentID;
        
        lscreenKeyObject.paymentDate=$scope.PaymentSummaryTable[$scope.selectedIndex].paymentDate;
	
	fninvokeDetailScreen('InstituteFeePayment',lscreenKeyObject,$scope);
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnInstitutePaymentSummaryView() {
	var emptyInstitutePaymentSummary = {
		filter:{
		paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		authStat: ""},
		SummaryResult:
		[{paymentID:"",paymentPaid:"",paymentDate:""}]
	};
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstitutePaymentSummary;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.operation= "View";
        $scope.paymentPaid=$('#feePaid').toNumber({region: 'en-IN'}).val();
        
        if($scope.paymentPaid!=null)
	dataModel.filter.paymentPaid = $scope.paymentPaid;
        if($scope.paymentDate!=null)
	dataModel.filter.paymentDate = $scope.paymentDate;
        if($scope.studentName!=null)
	dataModel.filter.studentName = $scope.studentName;
         if($scope.studentID!=null)
	dataModel.filter.studentID = $scope.studentID;
       
        if($scope.authStat!=null)
	dataModel.filter.authStat = $scope.authStat;
	// Screen Specific DataModel Ends
	var response = fncallBackend('InstitutePaymentSummary', 'View', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnInstitutePaymentSummaryMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	switch (operation) {
				case 'View':
					if ((($scope.paymentID == '' || $scope.paymentID == null) &                 
					    ($scope.paymentDate == '' || $scope.paymentDate == null) &
						($scope.paymentPaid == '' || $scope.paymentPaid == null) &
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

function fnInstitutePaymentSummaryDefaultandValidate(operation) {
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
			 
			 if(!fngetSelectedIndex($scope.PaymentSummaryTable,$scope))//Generic For Summary
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
function fnInstitutePaymentSummaryBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		// Screen Specific Scope Starts
	    $scope.paymentID = "";
	    $scope.paymentDate = "";
	    $scope.paymentPaid = "";
	    $scope.authStat = "";
	    $scope.mvwAddDeteleDisable = true; //Multiple View
	    $scope.PaymentSummaryTable = null;
		$scope.paymentSummaryShowObject = null;  
		if($scope.operation== "View")
		{	
	    $scope.paymentIDreadOnly = false;
	    $scope.paymentDatereadOnly = false;
	    $scope.paymentPaidreadOnly = false;
	    $scope.authStatreadOnly = false;
	    $scope.recordStatreadOnly = false;
		$scope.mastershow=true;
		$scope.detailshow=false;
		}
	    else {
	    $scope.paymentIDreadOnly = true;
	    $scope.paymentDatereadOnly = true;
	    $scope.paymentPaidreadOnly = true;
	    $scope.studentIDreadOnly = true;
	    $scope.studentNamereadOnly = true;
	    $scope.authStatreadOnly = true;
	    $scope.recordStatreadOnly = true;
	       }
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.selectedIndex =0;// Summary Field
        // Generic Scope Ends	
		
}
// Cohesive Create Framework Ends
function fnpaymentDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate"));
		$scope.$apply();
}

function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      $( "#paymentDate" ).datepicker('setDate','');
	  $scope.paymentDate = null;
		$scope.$apply();
}	

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'PaymentSummaryTable':
		   
			var PaymentSummaryTable = new Array();
			 responseObject.forEach(fnConvert);
			 
			 
			 function fnConvert(value,index,array){
				     PaymentSummaryTable[index] = new Object();
					 PaymentSummaryTable[index].idx=index;
					 PaymentSummaryTable[index].checkBox=false;
					 PaymentSummaryTable[index].paymentID=value.paymentID;
//					 PaymentSummaryTable[index].paymentDate=value.paymentDate;
					 PaymentSummaryTable[index].studentID=value.studentID;
					 PaymentSummaryTable[index].studentName=value.studentName;
//					 PaymentSummaryTable[index].feeID=value.feeID;
					 PaymentSummaryTable[index].paymentPaid=value.paymentPaid;
                                         PaymentSummaryTable[index].paymentDate=value.paymentDate;
					}
			return PaymentSummaryTable;
			break ;
		}
	}
	
function fnInstitutePaymentSummarypostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		 $scope.paymentIDreadOnly = true;
	         $scope.paymentDatereadOnly = true;
	         $scope.paymentPaidreadOnly = true;
	         $scope.studentIDreadOnly = true;
	         $scope.studentNamereadOnly = true;
	         $scope.authStatreadOnly = true;
	         $scope.recordStatreadOnly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = false;
		$scope.detailshow = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
                //$scope.paymentID = response.body.paymentID;
	        $scope.paymentDate =response.body.filter.paymentDate;
              //  $scope.paymentPaid =response.body.filter.paymentPaid;
                $scope.feeType =response.body.filter.feeType;
                //$scope.feeID =response.body.feeID;
                $scope.feeAmount =response.body.filter.feeAmount;
                //$scope.paymentPaid =response.body.paymentPaid;
                
                $('#feePaid').val(response.body.filter.paymentPaid);
                $('#feePaid').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.paymentPaid=$('#feePaid').val();
                
                
		$scope.authStat =response.body.filter.authStat;
                $scope.PaymentSummaryTable=fnConvertmvw('PaymentSummaryTable',response.body.SummaryResult);
		$scope.PaymentSummaryCurPage = 1;
		$scope.paymentSummaryShowObject=$scope.fnMvwGetCurPageTable('paymentSummary');
		//Multiple View Response Ends 
        }
		return true;

}
