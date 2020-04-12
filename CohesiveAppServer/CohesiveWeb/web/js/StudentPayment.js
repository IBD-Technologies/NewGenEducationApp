/* 
    Created on : Feb 15, 2019, 4:47:33 PM
    Author     : Munish Kumar .B
	Company : IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subscreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Screen Specific Scope Starts
	$scope.paymentID = "";
	$scope.PaymentIDMaster = [{
		PaymentId: ""
	}];
	$scope.paymentDate = "";
	$scope.feeID = "";
	$scope.feeType = "Select option";
	$scope.amount = "";
	$scope.outStanding = "";
	$scope.paymentMode = "";
	$scope.paymentDetails = "";
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.payment = Institute.PayMentMaster;
	$scope.fees = Institute.FeeMaster;
	$scope.paymentIDreadOnly = true;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentPaidreadOnly = true;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.feeIDreadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.amountreadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentTypereadOnly = true;
	$scope.paymentDetailsreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.remarksreadOnly = true;
	$scope.datereadOnly = true;
	// Screen Specfic Scope Ends
	// Generic Field Starts	
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.operation = '';
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service
         $scope.OperationScopes=OperationScopes;
	// Generic Field Ends
	$scope.fnStudentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Student';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}

	$scope.fnPaymentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Payment';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
        $scope.fnFeeSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
			
		};
		searchCallInput.mainScope = $scope;
	    searchCallInput.searchType = 'Fee';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}


$scope.PaymentCurPage = 0;
	$scope.PaymentTable = null;
	$scope.PaymentShowObject = null;
	// Multiple View Ends

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'Payment') {
			if ($scope.PaymentTable != null && $scope.PaymentTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.PaymentCurPage;
				lsvwObject.tableObject = $scope.PaymentTable;
				lsvwObject.screenShowObject = $scope.PaymentShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.PaymentCurPage = lsvwObject.curPage;
				$scope.PaymentTable = lsvwObject.tableObject;
				$scope.PaymentShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'Payment') {
			if ($scope.PaymentTable != null && $scope.PaymentTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.PaymentCurPage;
				lsvwObject.tableObject = $scope.PaymentTable;
				lsvwObject.screenShowObject = $scope.PaymentShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.PaymentCurPage = lsvwObject.curPage;
				$scope.PaymentTable = lsvwObject.tableObject;
				$scope.PaymentShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'Payment') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					feeID: "",
				    feeDescription: "",
				    outStanding: "",
				    paymentPaid: ""
				};
				if ($scope.PaymentTable == null)
					$scope.PaymentTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.PaymentCurPage;
				lsvwObject.tableObject = $scope.PaymentTable;
				lsvwObject.screenShowObject = $scope.PaymentShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.PaymentCurPage = lsvwObject.curPage;
				$scope.PaymentTable = lsvwObject.tableObject;
				$scope.PaymentShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'Payment') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.PaymentCurPage;
				lsvwObject.tableObject = $scope.PaymentTable;
				lsvwObject.screenShowObject = $scope.PaymentShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.PaymentCurPage = lsvwObject.curPage;
				$scope.PaymentTable = lsvwObject.tableObject;
				$scope.PaymentShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'Payment') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.PaymentCurPage;
			lsvwObject.tableObject = $scope.PaymentTable;
			lsvwObject.screenShowObject = $scope.PaymentShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'Payment') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.PaymentCurPage;
			lsvwObject.tableObject = $scope.PaymentTable;
			lsvwObject.screenShowObject = $scope.PaymentShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'Payment') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.PaymentTable);

		}
	};
        





});
//--------------------------------------------------------------------------------------------------------------	
//Default load Record Starts
$(document).ready(function () {
	MenuName = "StudentPayment";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        //fn_hide_parentspinner();
       // window.parent.fn_hide_parentspinner();
       var CurrentDate = new Date();
	fnDatePickersetDefault('paymentDate', fnpaymentDateEventHandler);
//          $("#"+paymentDate).datepicker( "setDate", CurrentDate);
	fnsetDateScope();
	selectBoxes = ['paymentMode', 'feeType'];
        fnGetSelectBoxdata(selectBoxes);
});
//Default Load Record Ends
function fnStudentPaymentpostSelectBoxMaster()
{
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
	 var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(Institute.FeeMaster.length>0){
	$scope.fees = Institute.FeeMaster;
	window.parent.fn_hide_parentspinner();    
	if (window.parent.StudentPaymentkey.paymentID != null && window.parent.StudentPaymentkey.paymentID != '') {
            
            if (window.parent.StudentPaymentkey.studentID != null && window.parent.StudentPaymentkey.studentID != '') {
                
                if (window.parent.StudentPaymentkey.paymentDate != null && window.parent.StudentPaymentkey.paymentDate != '') {
            
		var paymentID = window.parent.StudentPaymentkey.paymentID;

		window.parent.StudentPaymentkey.paymentID = null;
                
                var studentID = window.parent.StudentPaymentkey.studentID;

		window.parent.StudentPaymentkey.studentID = null;
                
                var paymentDate = window.parent.StudentPaymentkey.paymentDate;

		window.parent.StudentPaymentkey.paymentDate = null;

		fnshowSubScreen(paymentID,studentID,paymentDate);
            }
        
	}
        var emptyStudentPayment = {

		paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
		feeID: "",
		feeType: "Select option",
		amount: "",
		outStanding: "",
		paymentMode: "",
		remarks: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentPayment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null && $scope.studentID!="")
        {
	dataModel.studentID = $scope.studentID;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentPayment', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
    }
}
$scope.$apply();
}
    }
}
//Summary Screen Framework Starts
function fnshowSubScreen(paymentID,studentID,paymentDate) {
        subscreen = true;
	var emptyStudentPayment = {

		paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
		feeID: "",
		feeType: "Select option",
		amount: "",
		outStanding: "",
		paymentMode: "",
		remarks: ""
	};
	// Specfic Screen Data Model Starts									 
	var dataModel = emptyStudentPayment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(studentID!==null||studentID!==""){
            
           if(paymentID!==null||paymentID!==""){ 
               
               if(paymentDate!==null||paymentDate!==""){ 
           
            dataModel.studentID = studentID;
            dataModel.paymentID = paymentID;
            dataModel.paymentDate = paymentDate;
            // Specfic Screen Data Model Ends
            var response = fncallBackend('StudentPayment', 'View', dataModel, [{entityName:"studentID",entityValue:studentID}], $scope);
           }
       }
       }
        return true;
   }
       


//Summary Screen Framework Ends
// Cohesive Query Framework Starts 
function fnStudentPaymentQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specfic Scope Starts
	$scope.paymentID = "";
	$scope.paymentDate = "";
	$scope.paymentPaid = "";
	$scope.feeID = "";
	$scope.feeType = "Select option";
	$scope.date = "";
	$scope.amount = "";
	$scope.outStanding = "",
	$scope.paymentMode = "",
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.remarks = "";
	$scope.paymentIDreadOnly = false;
	$scope.paymentDatereadOnly = false;
        $( "#paymentDate" ).datepicker( "option", "disabled", false );
	$scope.feeIDreadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.amountreadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.paymentDetailsreadOnly = true;
	$scope.paymentPaidreadOnly = true;
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.remarksreadOnly = true;
        $scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	// Screen Specific Scope Ends
	// Generic Framework Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	// Generic Frame Work Ends	
	return true;
}
// Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnStudentPaymentView() {
	var emptyStudentPayment = {
		paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
                paymentMode:"",
		Payments: [{
				feeID: "",
				feeDescription: "",
				outStanding: "",
				paymentForFee: ""
			},
			{
				feeID: "",
				feeDescription: "",
				outStanding: "",
				paymentForFee: ""
			}
		]
	};
	// Specfic Screen Data Model Starts									 
	var dataModel = emptyStudentPayment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!==null||$scope.studentID!==""){
           dataModel.studentID = $scope.studentID;
           dataModel.studentName = $scope.studentName;
        
        if($scope.paymentID!==null||$scope.paymentID!==""){
            dataModel.paymentID = $scope.paymentID;
            dataModel.paymentDate = $scope.paymentDate;
        
	// Specfic Screen Data Model Ends
	var response = fncallBackend('StudentPayment', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	
        }
    }
        return true;
    
       
}
// Cohesive View Framework Ends
// Screen Specific Mandatory Validation Starts     
function fnStudentPaymentMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//                      if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
                    /* if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}*/
            
                        if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
			if ($scope.paymentID == '' || $scope.paymentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment ID']);
				return false;
			}
                       
			break;

		case 'Save':
                    if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}
                        if ($scope.studentID == '' || $scope.studentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
				return false;
			}
			if ($scope.paymentID == '' || $scope.paymentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment ID']);
				return false;
			}
                        if ($scope.feeID == '' || $scope.feeID == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee ID in Fee Tab']);
				return false;
			}
                        if ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == "Select option") {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Type in Fee Section']);
				return false;
			}
                        if ($scope.amount == '' || $scope.amount == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Amount in Fee Section']);
				return false;
			}
			if ($scope.paymentDate == '' || $scope.paymentDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Date']);
				return false;
			}
			if ($scope.paymentPaid == '' || $scope.paymentPaid == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['paid Amount in Payment Tab']);
				return false;
			}
			if ($scope.paymentMode == '' || $scope.paymentMode == null || $scope.paymentMode == "Select option") {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Mode in Detail Section']);
				return false;
			}
			break;
	}
	return true;
}
// Screen Specfic Mandatory Validation Ends

// Screen Specfic  Default and Validation Starts
function fnStudentPaymentDefaultandValidate(operation) {
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


	}
	return true;
}

function fnDefaultStudentId($scope) {
	var availabilty = false;
	return true;
}

function fnStudentPaymentDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultPaymentId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultPaymentId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultPaymentId($scope) {
	var availabilty = false;
	return true;
}

// Screen Specific Default Validation Ends
// Cohesive Create Framework Starts
function fnStudentPaymentCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Screen Scope Starts  
	$scope.paymentID = "";
	$scope.paymentDate = "";
	$scope.paymentPaid = "";
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.amount = "";
	$scope.feeID = "";
	$scope.feeType = "Select option";
	$scope.outStanding = "";
	$scope.paymentMode = "";
	$scope.remarks = "";
	$scope.paymentDatereadOnly = false;
        $( "#paymentDate" ).datepicker( "option", "disabled", false );
	$scope.paymentIDreadOnly = false;
	$scope.paymentPaidreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.feeIDreadOnly = false;
	$scope.feeTypereadOnly = false;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = false;
	$scope.amountreadOnly = false;
	// Screen Specfic Scope Ends
	// Generic Framework Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.remarksreadOnly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Creation';
	// Generic FrameWork Ends	
	return true;
}
// Cohesive Create Framework Ends
// Cohesive Edit Framework Starts
function fnStudentPaymentEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Framework Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	// Generic Framework Ends
	// Specific screen Scope Starts
	$scope.paymentDatereadOnly = false;
        $( "#paymentDate" ).datepicker( "option", "disabled", false );
	$scope.paymentIDreadOnly = false;
	$scope.paymentPaidreadOnly = false;
	$scope.feeIDreadOnly = false;
	$scope.feeTypereadOnly = false;
	$scope.remarksreadOnly = false;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = false;
	$scope.paymentDetailsreadOnly = false;
	$scope.amountreadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	//Specific Screen Scope Ends
	return true;
}
// Cohesive Edit Framework Ends
// Cohesive Delete Framework Starts
function fnStudentPaymentDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	// Generic field Ends
	// Screen Specific Scope Starts	
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
        $scope.studentNameSearchreadOnly = true;
	$scope.paymentIDreadOnly = true;
	$scope.paymentPaidreadOnly = true;
	$scope.datereadOnly = true;
	$scope.feeIDreadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.amountreadOnly = true;
	$scope.remarksreadOnly = true;
	// Screen Specfic Scope Ends
	return true;
}
// Cohesive delete Framework Ends
// Cohesive Authorisation Frame Work Starts
function fnStudentPaymentAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic FrameWork starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	// Generic FrameWork Ends
	// Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.amountreadOnly = true;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentPaidreadOnly = true;
	$scope.remarksreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.feeIDreadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
// Cohesive Authorisation Frame Work Ends
// Cohesive Reject Frame Work Starts
function fnStudentPaymentReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	// Generic Field Ends
	// Screen Specfic Scope Starts
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentIDreadOnly = true;
	$scope.paymentPaidreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.feeIDreadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.paymentDetailsreadOnly = true;
	$scope.amountreadOnly = true;
	$scope.remarksreadOnly = true;
	// Screen Specfic Scope Ends
	return true;
}
// Cohesive Reject Frame Work Ends
// Cohesive Back Frame Work starts
function fnStudentPaymentBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific  Scope Starts
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.paymentID = "";
		$scope.paymentDate = "";
		$scope.paymentPaid = "";
		$scope.studentID = "";
		$scope.studentName = "";
		$scope.amount = "";
		$scope.feeID = "";
		$scope.feeType = "Select option";
		$scope.outStanding = "";
		$scope.paymentMode = "";
		$scope.remarks = "";
	}
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentIDreadOnly = true;
	$scope.paymentPaidreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.feeIDreadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.amountreadOnly = true;
	$scope.remarksreadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.operation = '';
        $scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends

}
// Cohesive Back Frame Work Ends
// Cohesive Save Frame Work Starts
function fnStudentPaymentSave() {
	var emptyStudentPayment = {

		paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
		feeID: "",
		feeType: "Select option",
		amount: "",
		outStanding: "",
		paymentMode: "",
		remarks: ""
	};
	// Screen specfic Data Model Starts
	var dataModel = emptyStudentPayment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.paymentID!=null)
	dataModel.paymentID = $scope.paymentID;
        if($scope.paymentDate!=null)
	dataModel.paymentDate = $scope.paymentDate;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.feeID!=null)
	dataModel.feeID = $scope.feeID;
        if($scope.feeType!=null)
	dataModel.feeType = $scope.feeType;
        if($scope.outStanding!=null)
	dataModel.outStanding = $scope.outStanding;
        if($scope.paymentMode!=null)
	dataModel.paymentMode = $scope.paymentMode;
         if($scope.paymentPaid!=null)
	dataModel.paymentPaid = $scope.paymentPaid;
        if($scope.remarks!=null)
	dataModel.remarks = $scope.remarks;
         if($scope.amount!=null)
	dataModel.amount = $scope.amount;
	// Screen Specfic Data Model Ends
	var response = fncallBackend('StudentPayment', parentOperation, dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
// Cohesive Save Frame Work Ends
//DatePicker Function Starts
function fnpaymentDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate")),
		$scope.$apply();
}

function fnsetDateScope() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate"));
	$scope.$apply();
}

//DatePicker Function  Ends


function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'PaymentTable':
		   
			var PaymentTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     PaymentTable[index] = new Object();
					 PaymentTable[index].idx=index;
					 PaymentTable[index].checkBox=false;
					 PaymentTable[index].feeID=value.feeID;
					 PaymentTable[index].feeDescription=value.feeDescription;
					 PaymentTable[index].outStanding=value.outStanding;
                                         PaymentTable[index].dueDate=value.dueDate;
					 PaymentTable[index].paymentForFee=value.paymentForFee;
                                         
                                         
                                         
                                         
					}
			return PaymentTable;
		}
	}





function fnStudentPaymentpostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.paymentDatereadOnly = true;
                $( "#paymentDate" ).datepicker( "option", "disabled", true );
		$scope.paymentIDreadOnly = true;
		$scope.paymentPaidreadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.feeIDreadOnly = true;
		$scope.feeTypereadOnly = true;
		$scope.outStandingreadOnly = true;
		$scope.paymentModereadOnly = true;
		$scope.amountreadOnly = true;
		$scope.remarksreadOnly = true;
                $scope.feeDescriptionreadOnly = true;
                $scope.dueDatereadOnly = true;
		$scope.outStandingreadOnly = true;
                $scope.dueDatereadOnly = true;
                 $scope.paymentForTheFeereadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.paymentID = "";
		$scope.paymentDate ="";
		$scope.amount ="";
                $scope.paymentType ="";
                $scope.paymentDetails ="";
                $scope.feeID ="";
                $scope.feeType ="";
                $scope.remarks ="";
                $scope.outStanding ="";
                $scope.studentName ="";
                $scope.studentID ="";
                $scope.audit = {};
		 }
                else
                {
                // Screen Specific Response Scope Starts
		$scope.paymentID = response.body.paymentID;
		$scope.paymentDate = response.body.paymentDate;
		$scope.amount = response.body.amount;
		$scope.paymentMode = response.body.paymentMode;
		$scope.paymentDetails = response.body.paymentDetails;
		$scope.feeID = response.body.feeID;
		$scope.feeType = response.body.feeType;
		$scope.remarks = response.body.remarks;
		$scope.outStanding = response.body.outStanding;
		$scope.paymentPaid = response.body.paymentPaid;
		$scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
                $scope.balanceAmount = response.body.balanceAmount;
                $scope.audit=response.audit;
                if(response.body.Payments!==null){
                
		$scope.PaymentTable = fnConvertmvw('PaymentTable',response.body.Payments);
                
               }else{
                   
                   $scope.PaymentTable=null;
               }
                
                $scope.PaymentCurPage = 1
		$scope.PaymentShowObject = $scope.fnMvwGetCurPageTable('Payment');
		//Multiple View Response Ends 
		$scope.mvwAddDeteleDisable = true;
                
		// Screen Specific Response Scope Ends
            }
            if (subscreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
           subScreen=false;
             }
		return true;

}

}
