//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subscreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,OperationScopes) {
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.feeID = "";
	$scope.FeeMaster = [{
		FeeId:"",
		FeeType:""
	}];
	$scope.feeType = "Select option";
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.feePaid = "";
	$scope.outStanding = "";
	$scope.paidDate = "";
	$scope.status = "";
	$scope.fees = Institute.FeeMaster;
	$scope.statusMaster = Institute.FeeStatus;
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
        $scope.feeIDSearchreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.feeIDReadonly = true;
	$scope.feeTypereadOnly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#dueDate" ).datepicker( "option", "disabled", true );
	$scope.feePaidReadOnly = true;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = true;
        $( "#paidDate" ).datepicker( "option", "disabled", true );
	$scope.paymentModeReadOnly = true;
	$scope.statusReadonly = true;
	//Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.MakerRemarksReadonly = true;
	$scope.operation = '';
	//Generic Field Ends
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

});
//--------------------------------------------------------------------------------------------------------------

//Default load Record Starts
$(document).ready(function () {
	MenuName = "StudentFeeManagement";
        selectBypassCount=0;
        fnDatePickersetDefault('dueDate', fndueDateEventHandler);
	fnDatePickersetDefault('paidDate', fnpaidDateEventHandler);
        fnsetDateScope();
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        //fn_hide_parentspinner();
        fnsetDateScope();
	 // Select Box change starts
     
	selectBoxes= ['feeType','Status'];
           fnGetSelectBoxdata(selectBoxes);
           
            $('.currency').blur(function()
                {
                    $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
                });
});
//Default Load Record Ends
function fnStudentFeeManagementpostSelectBoxMaster()
{
   selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {   
    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.FeeMaster.length>0){
		$scope.fees = Institute.FeeMaster;
		window.parent.fn_hide_parentspinner();
    if (window.parent.StudentFeeManagementkey.feeID !=null && window.parent.StudentFeeManagementkey.feeID !='')
	{
            
     if (window.parent.StudentFeeManagementkey.studentID !=null && window.parent.StudentFeeManagementkey.studentID !='')
	{       
            
		var feeID=window.parent.StudentFeeManagementkey.feeID;
		
		 window.parent.StudentFeeManagementkey.feeID =null;
                 
                 var studentID=window.parent.StudentFeeManagementkey.studentID;
		
		 window.parent.StudentFeeManagementkey.studentID =null;
		
		fnshowSubScreen(feeID,studentID);
		
	}
        
        }
    
    var emptyStudentFeeManagement = {
		studentName: "",
		studentID: "",
		feeID: "",
		feeType: "Select option",
		amount: "",
		dueDate: "",
		feePaid: "",
		outStanding: "",
		paidDate: "",
		status: ""
	};
	var dataModel = emptyStudentFeeManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if( $scope.studentID!=null && $scope.studentID!="")
        {
	dataModel.studentID = $scope.studentID;
        
        if( $scope.feeID!=null && $scope.feeID!="")
        {
                dataModel.feeID = $scope.feeID;
                var response = fncallBackend('StudentFeeManagement', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
       }
      }
  $scope.$apply(); 
}
}
}
//summary Screen Framework starts
function fnshowSubScreen(feeID,studentID)
{
    subscreen = true;
var emptyStudentFeeManagement = {
		studentName: "",
		studentID: "",
		feeID: "",
		feeType: "Select option",
		amount: "",
		dueDate: "",
		feePaid: "",
		outStanding: "",
		paidDate: "",
		status: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentFeeManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if(studentID!=null&&studentID!=""){
            dataModel.studentID = studentID;
        if(feeID!=null&&feeID!=""){
            dataModel.feeID = feeID;    
            
            //Screen Specific DataModel Ends
            var response = fncallBackend('StudentFeeManagement', 'View', dataModel, [{entityName:"studentID",entityValue:dataModel.studentID}], $scope);
        }
    }
        
        return true;
}
//Summary Screen Framework Ends
//Cohesive Query framework Starts
function fnStudentFeeManagementQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.feeID = "";
	$scope.feeType = "Select option";
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.feePaid = "";
	$scope.outStanding = "";
	$scope.paidDate = "";
	$scope.status = "";
	$scope.studentIDReadOnly = false;
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.feeIDReadonly = false;
	$scope.feeTypereadOnly = false;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#dueDate" ).datepicker( "option", "disabled", true );
	$scope.feePaidReadOnly = true;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = true;
        $( "#paidDate" ).datepicker( "option", "disabled", true );
	$scope.paymentModeReadOnly = true;
	$scope.statusReadonly = true;
        $scope.feeIDSearchreadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	//Generic Field Ends
	return true;
}
//Cohesive Query framework Ends
//Cohesive View framework Starts
function fnStudentFeeManagementView() {
	var emptyStudentFeeManagement = {
		studentName: "",
		studentID: "",
		feeID: "",
		feeType: "Select option",
		amount: "",
		dueDate: "",
		feePaid: "",
		outStanding: "",
		paidDate: "",
		status: ""
		
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentFeeManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null||$scope.studentID!=""){
	dataModel.studentID = $scope.studentID;
        dataModel.studentName = $scope.studentName;
        
        if($scope.feeID!=null&&$scope.feeID!=""){
	dataModel.feeID = $scope.feeID;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentFeeManagement', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	
        }
    }
        return true;
        
        
}
//Cohesive View framework Ends
//Screen Specific Mandatory validation Starts
function fnStudentFeeManagementMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//			if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
			/*if ($scope.studentName == '' || $scope.studentName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
				return false;
			}*/
                        if ($scope.studentID == '' || $scope.studentID == null) {
                         
                         if ($scope.studentName == '' || $scope.studentName == null) {
                             
                             fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID or Student Name']);
			return false;
                         }
                         
                     }
                        if ($scope.feeID == '' || $scope.feeID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee ID']);
				return false;
			}
			/*if ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Type']);
				return false;
			}
                       */ 

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
			if ($scope.feeID == '' || $scope.feeID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee ID']);
				return false;
			}
			/*if ($scope.feeType == '' || $scope.feeType == null || $scope.feeType == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Type']);
				return false;
			}*/

			
			if ($scope.amount == '' || $scope.amount == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Amount in Amount Tab']);
				return false;
			}
                        if ($scope.feePaid == '' || $scope.feePaid == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Total Amount Paid in Amount Tab']);
				return false;
			}
			if ($scope.dueDate == '' || $scope.dueDate == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', [' Fee Due Date in Dates Tab']);
				return false;
			}
			if ($scope.status == '' || $scope.status == null || $scope.status == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Status']);
				return false;
			}
			break;


	}
	return true;
}
//Screen Specific Mandatory validation Ends
//Screen Specific Default validation Ends
function fnStudentFeeManagementDefaultandValidate(operation) {
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


function fnStudentFeeManagementDefaultandValidate(operation) {
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



//screen Specific Default Validation Ends
//Cohesive Create Framework Starts
function fnStudentFeeManagementCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.feeID = "";
	$scope.feeType = "Select option";
	$scope.amount = "";
	$scope.dueDate = "";
	$scope.feePaid = "";
	$scope.outStanding = "";
	$scope.status = "";
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = true;
         $scope.feeIDSearchreadOnly = false;
	$scope.studentIDReadOnly = false;
	$scope.feeTypereadOnly = false;
	$scope.feeIDReadonly = false;
	$scope.amountReadOnly = false;
	$scope.dueDateReadonly = false;
        $( "#dueDate" ).datepicker( "option", "disabled", false );
	$scope.feePaidReadOnly = false;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = false;
        $( "#paidDate" ).datepicker( "option", "disabled", false );
	$scope.paymentModeReadOnly = false;
	$scope.statusReadonly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.operation = 'Creation';
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	//Generic Field Ends
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnStudentFeeManagementEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.feeTypereadOnly = false;
	$scope.feeIDReadonly = true;
	$scope.amountReadOnly = false;
	$scope.dueDateReadonly = false;
        $( "#dueDate" ).datepicker( "option", "disabled", false );
	$scope.feePaidReadOnly = false;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = false;
        $( "#paidDate" ).datepicker( "option", "disabled", false );
	$scope.paymentModeReadOnly = false;
	$scope.statusReadonly = false;
         $scope.feeIDSearchreadOnly = false;
	//Screen Specific Scope Ends

	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnStudentFeeManagementDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeIDReadonly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#dueDate" ).datepicker( "option", "disabled", true );
	$scope.feePaidReadOnly = true;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = true;
        $( "#paidDate" ).datepicker( "option", "disabled", true );
	$scope.paymentModeReadOnly = true;
	$scope.statusReadonly = true;
         $scope.feeIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnStudentFeeManagementAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeIDReadonly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#dueDate" ).datepicker( "option", "disabled", true );
	$scope.feePaidReadOnly = true;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = true;
        $( "#paidDate" ).datepicker( "option", "disabled", true );
	$scope.paymentModeReadOnly = true;
	$scope.statusReadonly = true;
         $scope.feeIDSearchreadOnly = true;
	//Screen Specific Scope Ends
        
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnStudentFeeManagementReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeIDReadonly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#dueDate" ).datepicker( "option", "disabled", true );
	$scope.feePaidReadOnly = true;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = true;
        $( "#paidDate" ).datepicker( "option", "disabled", true );
	$scope.paymentModeReadOnly = true;
	$scope.statusReadonly = true;
         $scope.feeIDSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnStudentFeeManagementBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.studentID = "";
		$scope.studentName = "";
		$scope.feeID = "";
		$scope.feeType = "Select option";
		$scope.amount = "";
		$scope.dueDate = "";
		$scope.feePaid = "";
		$scope.outStanding = "";
		$scope.status = "";
	}
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDReadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeIDReadonly = true;
	$scope.amountReadOnly = true;
	$scope.dueDateReadonly = true;
        $( "#dueDate" ).datepicker( "option", "disabled", true );
	$scope.feePaidReadOnly = true;
	$scope.outStandingReadOnly = true;
	$scope.paidDateReadonly = true;
        $( "#paidDate" ).datepicker( "option", "disabled", true );
	$scope.paymentModeReadOnly = true;
	$scope.statusReadonly = true;
         $scope.feeIDSearchreadOnly = true;
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
//Cohesive Back Framework Ends
//Cohesive Save Framework Starts
function fnStudentFeeManagementSave() {
	var emptyStudentFeeManagement = {
		studentName: "",
		studentID: "",
		feeID: "",
		feeType: "Select option",
		amount: "",
		dueDate: "",
		feePaid: "",
		outStanding: "",
		paidDate: "",
		paymentMode: "",
		status: ""
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentFeeManagement;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
        if($scope.feeID!=null)
	dataModel.feeID = $scope.feeID;
        if($scope.feeType!=null)
	dataModel.feeType = $scope.feeType;
        if($scope.amount!=null)
	dataModel.amount = $scope.amount;
        if($scope.dueDate!=null)
	dataModel.dueDate = $scope.dueDate;
        if($scope.feePaid!=null)
	dataModel.feePaid = $scope.feePaid;
        if($scope.outStanding!=null)
	dataModel.outStanding = $scope.outStanding;
         if($scope.paidDate!=null)
	dataModel.paidDate = $scope.paidDate;
        if($scope.paymentMode!=null)
	dataModel.paymentMode = $scope.paymentMode;
        if($scope.status!=null)
	dataModel.status = $scope.status;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentFeeManagement', parentOperation, dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
//Cohesive Save Framework Ends
//Date Picker function Starts
function fndueDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.dueDate = $.datepicker.formatDate('dd-mm-yy', $("#dueDate").datepicker("getDate"));
	$scope.$apply();
}

function fnpaidDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paidDate = $.datepicker.formatDate('dd-mm-yy', $("#paidDate").datepicker("getDate"));
	$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.paidDate = $.datepicker.formatDate('dd-mm-yy', $("#paidDate").datepicker("getDate"));
		$scope.$apply();
}	
//Date Picker function Ends

function fnStudentFeeManagementpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDReadOnly = true;
		$scope.feeTypereadOnly = true;
		$scope.feeIDReadonly = true;
		$scope.amountReadOnly = true;
		$scope.dueDateReadonly = true;
                $( "#dueDate" ).datepicker( "option", "disabled", true );
		$scope.feePaidReadOnly = true;
		$scope.outStandingReadOnly = true;
		$scope.paidDateReadonly = true;
                $( "#paidDate" ).datepicker( "option", "disabled", true );
		$scope.paymentModeReadOnly = true;
		$scope.statusReadonly = true;
                 $scope.feeIDSearchreadOnly = true; 
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.studentID = "";
		        $scope.studentName ="";
		        $scope.feeID ="";
                $scope.feeType ="";
                $scope.amount ="";
                $scope.dueDate ="";
                $scope.feePaid ="";
                $scope.outStanding ="";
                $scope.paidDate ="";
                $scope.paymentMode ="";
                $scope.status ="";
                $scope.audit = {};
		 }
                else
                {
                $scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
		$scope.feeID = response.body.feeID;
		$scope.feeType = response.body.feeType;
//		$scope.amount = response.body.amount;
		$scope.dueDate = response.body.dueDate;
//		$scope.feePaid = response.body.feePaid;
//		$scope.outStanding = response.body.outStanding;
		$scope.paidDate = response.body.paidDate;
		$scope.paymentMode = response.body.paymentMode;
		$scope.status = response.body.status;
                $scope.audit =response.audit;
                $('#feeAmount').val(response.body.amount);
                $('#feeAmount').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.amount=$('#feeAmount').val();
                
                $('#fee_Paid').val(response.body.feePaid);
                $('#fee_Paid').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.feePaid=$('#fee_Paid').val();
                
                $('#feeOutstanding').val(response.body.outStanding);
                $('#feeOutstanding').formatCurrency({ colorize:true,region: 'en-IN' })
		$scope.outStanding=$('#feeOutstanding').val();
		//Screen Specific Response Scope Ends
                }
                  if (subscreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
           subScreen=false;
      }
      	//Multiple View Response Ends 
		return true;

}

}


