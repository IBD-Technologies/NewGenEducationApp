/* 
    Author     :IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller---------------------------------------
var subScreen = false;
var saveDone=false;

var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Specific Screen Scope Starts
	$scope.paymentID = "";
        $scope.instituteName = "";
        $scope.instituteID = "";
	$scope.PaymentIDMaster = [{
		PaymentId:""
	}];
	$scope.paymentDate = "";
	$scope.paymentPaid = "";
        $scope.balanceAmount = "";
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.feeID = "";
	$scope.FeeMaster = [{
		FeeId:"",
		FeeType:""
	}];
        $scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
//	$scope.feeType = "Select option";
        $scope.feeDescription = "";
	$scope.amount = "";
	$scope.outStanding = "";
	$scope.date = "";
	$scope.paymentMode = "";
	$scope.paymentDetails = "";
	$scope.payment = Institute.PayMentMaster;
	$scope.fees = Institute.FeeMaster;
	// specific Screen Scope Ends
	// Generic Field starts
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
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.paymentSearchreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.paymentIDreadOnly = true;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentPaidreadOnly = true;
        $scope.paymentForTheFeereadOnly = true;
        
	$scope.feeIDreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
//	$scope.feeTypereadOnly = true;
        $scope.feeDescriptionreadOnly = true;
        $scope.dueDatereadOnly = true;
	$scope.amountreadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.paymentDetailsreadOnly = true;
	$scope.remarksreadOnly = true;
	$scope.datereadOnly = true;
	// Specific Screen Scope Ends
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
	$scope.fnPaymentSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
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
        $scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Institute';
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

//Default Load Record Starts
$(document).ready(function () {
	MenuName = "InstitutePayment";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Institute";
        fnDatePickersetDefault('paymentDate',fnpaymentDateEventHandler);
        fnsetDateScope();
        selectBoxes= ['feeType','paymentMode'];
        fnGetSelectBoxdata(selectBoxes);
        
         $('.currency').blur(function()
                {
                    $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
                });
	// Select Box change starts
	   //Select Box change Ends	
});
//Default Load Record Ends

function fnInstitutePaymentpostSelectBoxMaster()
{
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope()
       if(Institute.FeeMaster.length>0)
      {   
	$scope.fees = Institute.FeeMaster;
	 window.parent.fn_hide_parentspinner();
         
         if (window.parent.InstituteFeePaymentkey.paymentID !=null && window.parent.InstituteFeePaymentkey.paymentID !='')
	{
            
            if (window.parent.InstituteFeePaymentkey.paymentDate !=null && window.parent.InstituteFeePaymentkey.paymentDate !='')
	{
		var paymentID=window.parent.InstituteFeePaymentkey.paymentID;
                
                var paymentDate=window.parent.InstituteFeePaymentkey.paymentDate;
		
		 window.parent.InstituteFeePaymentkey.paymentID =null;
                 
                 window.parent.InstituteFeePaymentkey.paymentDate =null;
		
		fnshowSubScreen(paymentID,paymentDate);
		
	} 
         
         
        $scope.$apply();
} 
    }
    }
}
// Cohesive Query Framework Starts
function fnshowSubScreen(paymentID,paymentDate)
{
    subScreen = true;
var emptyInstitutePayment = {  
		instituteName: "",
        instituteID: "",
        paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
                paymentMode: "",
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
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstitutePayment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentID=paymentID;
        $scope.paymentDate=paymentDate;
	//dataModel.paymentDate = $scope.paymentDate;
        if($scope.paymentID!==null&&$scope.paymentID!==""){
            
            dataModel.paymentID = $scope.paymentID;
            
        if($scope.paymentDate!==null&&$scope.paymentDate!==""){
            
            dataModel.paymentDate = $scope.paymentDate;    
            dataModel.instituteID=window.parent.Institute.ID;
            // Screen Specific DataModel Ends
            var response = fncallBackend('InstitutePayment', 'View', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	
        }
    }
        return true;
}



function fnInstitutePaymentDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (parentOperation=='Create'&&saveDone==false)
	{	
var emptyInstitutePayment = {
		instituteName: "",
        instituteID: "",
        paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
		Payments: [{
				feeID: "",
				feeDescription: "",
				outStanding: "",
				paidAmount: ""
			},
			{
				feeID: "",
				feeDescription: "",
				outStanding: "",
				paidAmount: ""
			}
		]
	};
    //Screen Specific DataModel Starts
	var dataModel = emptyInstitutePayment;
//	$scope.paymentID=paymentID;
//    $scope.paymentDate=paymentDate;
//	$scope.paidAmount=paidAmount;

                 if ($scope.instituteID == '' || $scope.instituteID== null || $scope.instituteID == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
                 if ($scope.paymentID == '' || $scope.paymentID== null || $scope.paymentID == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment ID']);
				return false;
			}
                  if ($scope.paymentDate == '' || $scope.paymentDate== null || $scope.paymentDate == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Date']);
				return false;
			}
//                  if ($scope.paymentPaid == '' || $scope.paymentPaid== null || $scope.paymentPaid == "Select option") {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Paid']);
//				return false;
//			}
//                  if ($scope.studentID == '' || $scope.studentID== null || $scope.studentID == "Select option") {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
                   if ($scope.paymentMode == '' || $scope.paymentMode== null || $scope.paymentMode == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Mode']);
				return false;
			}


   if($scope.paymentID!==null&&$scope.paymentID!==""){
            
            dataModel.paymentID = $scope.paymentID;
            
        if($scope.paymentDate!==null&&$scope.paymentDate!==""){
            
            dataModel.paymentDate = $scope.paymentDate;  

//           if($scope.paymentPaid!==null&&$scope.paymentPaid!==""){
//            
            
            
            if($scope.paymentMode!==null&&$scope.paymentMode!==""){
            
            dataModel.paymentMode = $scope.paymentMode;
            
            if($scope.studentID!==null&&$scope.studentID!=="")
              dataModel.studentID = $scope.studentID;
            if($scope.studentName!==null&&$scope.studentName!=="")
              dataModel.studentName = $scope.studentName;  
              
              
            dataModel.instituteID = $scope.instituteID;  
            
            if($scope.paymentPaid!==null&&$scope.paymentPaid!=="")
             dataModel.paymentPaid = $scope.paymentPaid; 
              dataModel.paymentPaid=$('#feePaid').toNumber({region: 'en-IN'}).val();

	    var response = fncallBackend('InstitutePayment', 'Payment-Default', dataModel,[{entityName:"instituteID",entityValue:$scope.instituteID}],$scope);
		$scope.mvwAddDeteleDisable = true; //Multiple View

//		   }
		}
//        }
    }
	 }	


}
return true;
}
function fnInstitutePaymentQuery() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.paymentID = "";
	$scope.paymentDate = "";
	$scope.feeID = "";
	$scope.amount = "";
	$scope.outStanding = "";
	$scope.paymentMode = "";
	$scope.feeDescription = "";
	$scope.paymentPaid = "";
        $scope.balanceAmount = "";
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.paymentIDreadOnly = false;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.feeIDreadOnly = true;
	$scope.feeDescriptionreadOnly = true;
	$scope.amountreadOnly = true;
        $scope.dueDatereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
        $scope.paymentSearchreadOnly = false;
        
	$scope.paymentModereadOnly = true;
	$scope.paymentPaidreadOnly = true;
        $scope.paymentForTheFeereadOnly = true;
	$scope.paymentDetailsreadOnly = true;
        
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	// Screen Specific Scope Ends
	// Generic Field starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	// Generic Field Ends	
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnInstitutePaymentView() {
	var emptyInstitutePayment = {
		instituteName: "",
        instituteID: "",
        paymentID: "",
		paymentDate: "",
		paymentPaid: "",
                paymentMode:"",
		studentName: "",
		studentID: "",
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
	// Screen Specific DataModel Starts									
	var dataModel = emptyInstitutePayment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if( $scope.paymentID!==null&&$scope.paymentID!==""){
            
            dataModel.paymentID = $scope.paymentID;
            
        if( $scope.paymentDate!==null&&$scope.paymentDate!==""){
            
            dataModel.paymentDate = $scope.paymentDate;  
            
        if( $scope.instituteID!==null&&$scope.instituteID!==""){
            
            dataModel.instituteID = $scope.instituteID;      
            
            var response = fncallBackend('InstitutePayment', 'View', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
        }
       }
      }
        return true;
}
// Cohesive View Framework Ends

// Screen Specific Mandatory Validation Starts      
function fnInstitutePaymentMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			if ($scope.paymentID == '' || $scope.paymentID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment ID']);
				return false;
			}
			break;

		case 'Save':
//		    if ($scope.instituteName == '' || $scope.instituteName == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
//				return false;
//			}
//                        if ($scope.instituteID == '' || $scope.instituteID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
//				return false;
//			}
//			/*if ($scope.paymentID == '' || $scope.paymentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment ID']);
//				return false;
//			}*/
//			if ($scope.studentName == '' || $scope.studentName == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name in Student Tab']);
//				return false;
//			}
//			if ($scope.feeID == '' || $scope.feeID == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee ID in Fee Tab']);
//				return false;
//			}
////			if ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') {
////				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Type in Fee Tab']);
////				return false;
////			}
////                        if ($scope.amount == '' || $scope.amount == null) {
////
////				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Amount in Student Tab']);
////				return false;
////			}
//                        if ($scope.paymentDate == '' || $scope.paymentDate == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Date in Student Tab']);
//				return false;
//			}
//			if ($scope.paymentPaid == '' || $scope.paymentPaid == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Paid in Payment Tab']);
//				return false;
//			}
//			if ($scope.paymentMode == '' || $scope.paymentMode == null || $scope.paymentMode == "Select option") {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Mode in Payment Tab']);
//				return false;
//			}

                        if ($scope.instituteID == '' || $scope.instituteID== null || $scope.instituteID == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
                 if ($scope.paymentID == '' || $scope.paymentID== null || $scope.paymentID == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment ID']);
				return false;
			}
                  if ($scope.paymentDate == '' || $scope.paymentDate== null || $scope.paymentDate == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Date']);
				return false;
			}
                  if ($scope.paymentPaid == '' || $scope.paymentPaid== null || $scope.paymentPaid == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Amount']);
				return false;
			}
//                  if ($scope.studentID == '' || $scope.studentID== null || $scope.studentID == "Select option") {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
                        if ($scope.paymentMode == '' || $scope.paymentMode== null || $scope.paymentMode == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Payment Mode']);
				return false;
			}
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation ends

function fnInstitutePaymentDefaultandValidate(operation) {
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
 
 function fnInstitutePaymentDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultInstituteId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultInstituteId($scope))
				return false;

			break;
	}
	return true;
}
function fnDefaultInstituteId($scope) {
	var availabilty = false;
	return true;
}



function fnInstitutePaymentDefaultandValidate(operation) {
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


function fnInstitutePaymentDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultFeeId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultFeeId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultFeeId($scope) {
	var availabilty = false;
	return true;
}
//Cohesive Create Framework Starts
function fnInstitutePaymentCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
        fnDatePickersetDefault('paymentDate',fnpaymentDateEventHandler);
//        fnsetDateScope();
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate"));
        $scope.PaymentTable =null
	$scope.paymentID = "";
//	$scope.paymentDate = "";
	$scope.paymentPaid = "";
        $scope.balanceAmount = "";
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.amount = "";
	$scope.feeID = "";
	$scope.feeDescription = "";
	$scope.outStanding = "";
	$scope.paymentMode = "";
        $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	// Screen Specific Scope Ends
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
        $scope.instituteNameSearchreadOnly = false;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentIDreadOnly = false;
	$scope.paymentSearchreadOnly = true;
	$scope.paymentPaidreadOnly = false;
        $scope.paymentForTheFeereadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.feeIDreadOnly = false;
	$scope.feeDescriptionreadOnly = true;
        $scope.dueDatereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = false;
	$scope.amountreadOnly = true;
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
        saveDone=false;
	// Generic Field Ends
	var emptyInstitutePayment = {
		instituteName: "",
		instituteID: "",
		paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
		feeID: "",
		feeDescription: "Select option",
		amount: "",
		outStanding: "",
		paymentMode: ""
	};
        
        var dataModel = emptyInstitutePayment;
        var response = fncallBackend('InstitutePayment', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
        
	return true;
}
//Cohesive Create Framework Ends

//Cohesive Edit Framework Starts
function fnInstitutePaymentEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        // Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = false;
        $scope.paymentSearchreadOnly = false;
	$scope.paymentIDreadOnly = true;
	$scope.paymentPaidreadOnly = false;
        $scope.paymentForTheFeereadOnly = false;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.feeIDreadOnly = false;
	$scope.feeDescriptionreadOnly = true;
        $scope.dueDatereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = false;
	$scope.paymentDetailsreadOnly = false;
	$scope.amountreadOnly = true;
	$scope.studentNamereadOnly = false;
        $scope.studentIDreadOnly = false;
	// Screen Specific Scope Ends	
	return true;
}
//Cohesive Edit Framework Starts

//Cohesive Delete Framework Starts
function fnInstitutePaymentDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentIDreadOnly = true;
	$scope.paymentPaidreadOnly = true;
        $scope.paymentForTheFeereadOnly = true;
	$scope.datereadOnly = true;
	$scope.feeIDreadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.dueDatereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.amountreadOnly = true;
        $scope.paymentSearchreadOnly = true;
	$scope.operation = 'Deletion';
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends

//Cohesive Authorisation Framework Starts
function fnInstitutePaymentAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	// Generic Field Ends	

	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteNamereadOnly = true
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.studentPaidreadOnly = true;
	$scope.amountreadOnly = true;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentModereadOnly = true;
        $scope.paymentSearchreadOnly = true;
	$scope.feeIDreadOnly = true;
        
        $scope.amount=$('#feeAmount').toNumber({region: 'en-IN'}).val();
 
        $scope.paymentPaid=$('#feePaid').toNumber({region: 'en-IN'}).val();
        
        $scope.outStanding=$('#feeOutstanding').toNumber({region: 'en-IN'}).val();
        
        
	// Screen Specific Scope Ends	

	return true;
}
//Cohesive Authorisation Framework Ends

//Cohesive Reject Framework Starts
function fnInstitutePaymentReject() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	// Generic Field Ends
	// Screen Specific Scope Starts
        $scope.instituteNameSearchreadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.paymentDatereadOnly = true;
        $( "#paymentDate" ).datepicker( "option", "disabled", true );
	$scope.paymentIDreadOnly = true;
	$scope.paymentPaidreadOnly = true;
        $scope.paymentForTheFeereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.feeIDreadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.dueDatereadOnly = true;
	$scope.outStandingreadOnly = true;
	$scope.paymentModereadOnly = true;
	$scope.paymentDetailsreadOnly = true;
	$scope.amountreadOnly = true;
           $scope.paymentSearchreadOnly = true;
           
        $scope.amount=$('#feeAmount').toNumber({region: 'en-IN'}).val();
 
        $scope.paymentPaid=$('#feePaid').toNumber({region: 'en-IN'}).val();
        
        $scope.outStanding=$('#feeOutstanding').toNumber({region: 'en-IN'}).val();   
           
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends

// Cohesive Create Framework Starts
function fnInstitutePaymentBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	 
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	    $scope.audit = {};
		$scope.instituteName= "";
                $scope.instituteID= "";
                $scope.paymentID= "";
		$scope.paymentDate= "";
		$scope.paymentPaid= "";
                $scope.balanceAmount = "";
		$scope.studentName= "";
		$scope.studentID= "";
		$scope.feeID= "";
		$scope.feeDescription= "";
		$scope.amount= "";
		$scope.outStanding= "";
		$scope.paymentMode= "";
                $scope.PaymentTable=null;
                $scope.PaymentShowObject = null;
	}
	       // Screen Specific Scope Starts
		$scope.instituteIDreadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
                $scope.paymentSearchreadOnly = true;
	        $scope.instituteNamereadOnly = true;
		$scope.paymentDatereadOnly = true;
		$scope.paymentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.paymentDatereadOnly = true;
                $( "#paymentDate" ).datepicker( "option", "disabled", true );
		$scope.paymentPaidreadOnly = true;
                $scope.paymentForTheFeereadOnly = true;
		$scope.feeIDreadOnly = true;
		$scope.feeDescriptionreadOnly = true;
                $scope.dueDatereadOnly = true;
		$scope.outStandingreadOnly = true;
		$scope.paymentModereadOnly = true;
		$scope.amountreadOnly = true;
		
		// Screen Specific Scope Ends
		// Generic Scope Starts
		$scope.operation = '';
		$scope.mastershow = true;
	        $scope.detailshow = false;
                  $scope.auditshow = false;
                  saveDone=false;
        // Generic Scope Ends	
}
// Cohesive Create Framework Ends

//Cohesive Save Framework Starts
function fnInstitutePaymentSave() {
	var emptyInstitutePayment = {
		instituteName: "",
        instituteID: "",
        paymentID: "",
		paymentDate: "",
		paymentPaid: "",
		studentName: "",
		studentID: "",
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
	// Screen Specific DataModel Starts
	var dataModel = emptyInstitutePayment;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
        if($scope.paymentID!=null)
	dataModel.paymentID = $scope.paymentID;
        if($scope.paymentDate!=null)
	dataModel.paymentDate = $scope.paymentDate;
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.paymentMode!=null)
	dataModel.paymentMode = $scope.paymentMode;
        if($scope.paymentPaid!=null)
	dataModel.paymentPaid = $scope.paymentPaid;
       
        
        if($scope.PaymentTable!=null)
	  dataModel.Payments = $scope.PaymentTable;
        else
          dataModel.Payments =new Array();
         
	// Screen Specific DataModel Ends

        dataModel.amount=$('#feeAmount').toNumber({region: 'en-IN'}).val();
 
        dataModel.paymentPaid=$('#feePaid').toNumber({region: 'en-IN'}).val();
        
        dataModel.outStanding=$('#feeOutstanding').toNumber({region: 'en-IN'}).val();

	var response = fncallBackend('InstitutePayment', parentOperation, dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	return true;
}
//Cohesive Save Framework Ends
function fnpaymentDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate")),
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paymentDate = $.datepicker.formatDate('dd-mm-yy', $("#paymentDate").datepicker("getDate"));
		$scope.$apply();
}	


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


function fnInstitutePaymentpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            if(response.header.operation == 'Create-Default')
		
		{
		  $scope.paymentID = response.body.paymentID;
                if(response.body.instituteId!=null && response.body.instituteId!="")
                  $scope.instituteId= response.body.instituteId;
                 if(response.body.instituteName!=null && response.body.instituteName!="")
                  $scope.instituteName = response.body.instituteName;
		  return true;
		} 
		else
		{
                    
                    
                    
                    
                    
		// Specific Screen Scope Starts
                
	        $scope.CheckerRemarksReadonly = true;
		$scope.instituteIDreadOnly = true;
	        $scope.instituteNamereadOnly = true;
                $scope.instituteNameSearchreadOnly = true;
		$scope.paymentDatereadOnly = true;
                $( "#paymentDate" ).datepicker( "option", "disabled", true );
		$scope.paymentIDreadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.paymentPaidreadOnly = true;
		$scope.feeIDreadOnly = true;
		$scope.feeDescriptionreadOnly = true;
                $scope.dueDatereadOnly = true;
		$scope.outStandingreadOnly = true;
                $scope.dueDatereadOnly = true;
		$scope.paymentModereadOnly = true;
//		$scope.amountreadOnly = true;
//                $scope.paymentSearchreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
                if(response.header.operation=="Payment-Default"){
                 
//	         $scope.attendanceReadOnly = false;
                 $scope.mastershow = false;
	         $scope.detailshow = true;
	         $scope.auditshow = false;
                 $scope.MakerRemarksReadonly = false;
                 $scope.paymentForTheFeereadOnly =false;
                 
                $scope.studentNamereadOnly = false;
		$scope.studentIDreadOnly = false;
		$scope.paymentPaidreadOnly = false;
		$scope.paymentModereadOnly = false;
                
                
                }else{
                $scope.MakerRemarksReadonly = true;
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
                $scope.paymentForTheFeereadOnly = true;
                
                
                
                
                
                
            }
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.instituteID = "";
		$scope.instituteName ="";
		$scope.paymentID ="";
                $scope.paymentDate ="";
                $scope.studentName ="";
                $scope.studentID ="";
                $scope.paymentPaid ="";
                $scope.balanceAmount = "";
                $scope.date ="";
                $scope.amount ="";
                $scope.paymentType ="";
                $scope.paymentMode ="";
                $scope.paymentDetails ="";
                $scope.feeID ="";
                $scope.feeDescription ="";
                $scope.outStanding ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.paymentID = response.body.paymentID;
		$scope.paymentDate = response.body.paymentDate;
		$scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
//		$scope.paymentPaid = response.body.paymentPaid;
		$scope.date = response.body.date;
//		$scope.amount = response.body.amount;
		$scope.paymentType = response.body.paymentType;
                $scope.paymentMode = response.body.paymentMode;
		$scope.paymentDetails = response.body.paymentDetails;
		$scope.feeID = response.body.feeID;
		$scope.feeDescription = response.body.feeDescription;
                $scope.balanceAmount = response.body.balanceAmount;
//		$scope.outStanding = response.body.outStanding;
                $scope.audit =response.audit;
                
                if(response.body.Payments!==null){
                
		$scope.PaymentTable = fnConvertmvw('PaymentTable',response.body.Payments);
                
               }else{
                   
                   $scope.PaymentTable=null;
               }
                
                $scope.PaymentCurPage = 1
		$scope.PaymentShowObject = $scope.fnMvwGetCurPageTable('Payment');
		//Multiple View Response Ends 
		$scope.mvwAddDeteleDisable = true;
                
                
                $('#feeAmount').val(response.body.amount);
                $('#feeAmount').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.amount=$('#feeAmount').val();
                
                $('#feePaid').val(response.body.paymentPaid);
                $('#feePaid').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.paymentPaid=$('#feePaid').val();
                $('#balanceAmount').val(response.body.balanceAmount);
                $('#balanceAmount').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.balanceAmount=$('#balanceAmount').val();
                
                $('#feeOutstanding').val(response.body.outStanding);
                $('#feeOutstanding').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.outStanding=$('#feeOutstanding').val();
                
                
            }
          if(response.header.operation != 'Payment-Default'){  
            
            
            saveDone=true;
        }
          if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen=false;
	 }
                 
		return true;
                }
}

}