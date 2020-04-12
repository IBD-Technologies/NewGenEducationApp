/* 
    Author : IBD Technologies
	
*/

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
	// Screen Specific Scope Starts 
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.profileImgPath = "";
	$scope.shortName = "";
	$scope.general = {};
        $scope.general.gender="";
//	$scope.emergency = "";
        $scope.emergency={
			existingMedicalDetails: "",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}]

		};
	$scope.classes = Institute.ClassMaster;
        $scope.genders = Institute.GenderMaster;
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.qualificationreadOnly = true;
        $scope.genderReadonly= true;
	$scope.classReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.emailIDreadOnly = true;
	$scope.contactNoreadOnly = true;
	$scope.bloodreadOnly = true;
	$scope.cpImgPathreadOnly = true;
	$scope.shortNamereadOnly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.teacherNameSearchreadOnly = true;
	// Screen Specfic Scope Ends	

	// Single View Starts
	$scope.contactPersonRecord = null;
	$scope.contactPersonTable = null;
	$scope.contactPersonCurIndex = 0;
	$scope.contactPersonShow = false;
	// Single View Ends 	
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	$scope.searchShow = false;
	$scope.operation = '';
	$scope.svwAddDeteleDisable = true; // single View
	$scope.audit = {};
	$scope.teacherMaster = [{
		TeacherId: null,
		TeacherName: null
	}];
	// Generic Field Ends


	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null

		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}


	$scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'ContactPersonDetails') {
			if ($scope.contactPersonTable != null && $scope.contactPersonTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.contactPersonCurIndex;
				lsvwObject.tableObject = $scope.contactPersonTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.contactPersonCurIndex = lsvwObject.curIndex;
				$scope.contactPersonTable = lsvwObject.tableObject;
				$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
			}
		}


	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'ContactPersonDetails') {
			if ($scope.contactPersonTable != null && $scope.contactPersonTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.contactPersonCurIndex;
				lsvwObject.tableObject = $scope.contactPersonTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.contactPersonCurIndex = lsvwObject.curIndex;
				$scope.contactPersonTable = lsvwObject.tableObject;
				$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
			}
		}
	};


	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ContactPersonDetails') {
				emptyTableRec = {
					idx: 0,
					cp_Name: "",
					cp_relationship: "",
					cp_occupation: "",
					cp_emailID: "",
					cp_contactNo: "",
					cp_imgPath: ""
				};

				if ($scope.contactPersonTable == null)
					$scope.contactPersonTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.contactPersonShow;
				lsvwObject.curIndex = $scope.contactPersonCurIndex;
				lsvwObject.tableObject = $scope.contactPersonTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.contactPersonShow = lsvwObject.tableShow;
				$scope.contactPersonCurIndex = lsvwObject.curIndex;
				$scope.contactPersonTable = lsvwObject.tableObject;
				$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];

			}
		}

	};
	$scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'ContactPersonDetails') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.contactPersonShow;
				lsvwObject.curIndex = $scope.contactPersonCurIndex;
				lsvwObject.tableObject = $scope.contactPersonTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.contactPersonShow = lsvwObject.tableShow;
				$scope.contactPersonCurIndex = lsvwObject.curIndex;
				$scope.contactPersonTable = lsvwObject.tableObject;
				$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
			}
		}
	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'ContactPersonDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.contactPersonShow;
			lsvwObject.curIndex = $scope.contactPersonCurIndex;
			lsvwObject.tableObject = $scope.contactPersonTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'ContactPersonDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.contactPersonShow;
			lsvwObject.curIndex = $scope.contactPersonCurIndex;
			lsvwObject.tableObject = $scope.contactPersonTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		}
	};
	//Scope Level Single View functions Ends 


});
//--------------------------------------------------------------------------------------------------------------

$(document).ready(function () {
	
	MenuName = "TeacherProfile";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Teacher";
        
	fnDatePickersetDefault('dateOfBirth', fndobDateEventHandler);
	fnsetDateScopeforTable();
	fnsetDateScope();
	selectBoxes = ['class','gender'];
       fnGetSelectBoxdata(selectBoxes);
      

$("#TeacherprofileImg").change(function(){
   $("#TeacherimgUpld").submit();   
});
$("#ContactprofileImg").change(function(){
   $("#ContactimgUpld").submit();   
});

$('#TeacherimgUpld').submit(function(event){
   $("#teacher").attr("src","");
  fileUpload('#teacher',$('#TeacherimgUpld')[0],$('#TeacherimgUpld').attr('action'),"TeacherProfile");
  event.preventDefault();
    return true;
});  

$('#ContactimgUpld').submit(function(event){
   $("#Contact").attr("src","");
  fileUpload('#Contact',$('#ContactimgUpld')[0],$('#ContactimgUpld').attr('action'),"TeacherProfile");
  event.preventDefault();
    return true;
}); 
});

//--------------------------------  Cohesive Default Load Record Ends --------------------------------------	

function fnTeacherProfilepostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {    
  var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(Institute.ClassMaster.length>0){
    $scope.classes=Institute.ClassMaster;
    $scope.gender=Institute.GenderMaster;
	window.parent.fn_hide_parentspinner();
    if (window.parent.TeacherProfilekey.teacherID != null && window.parent.TeacherProfilekey.teacherID != '') {
		var teacherID = window.parent.TeacherProfilekey.teacherID;

		window.parent.TeacherProfilekey.teacherID = null;

		fnshowSubScreen(teacherID);

	}
        
        
      var emptyTeacherProfile = {
		teacherName: "",
		teacherID: "",
		profileImgPath: "",
		general: {
			qualification: "",
			class: "Select option",
                        gender: "",
			dob: "",
			contactNo: "",
			emailID: "",
			shortName: "",
			bloodGroup: "",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: ""
			}
		},

		emergency: {
			existingMedicalDetails: "",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}]

		}
	};
	var dataModel = emptyTeacherProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null && $scope.teacherID!="") 
		{
	dataModel.teacherID = $scope.teacherID;
	// Screen Data Model Ends	
	var response = fncallBackend('TeacherProfile', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);  
}
$scope.$apply();
}
    }
}
function fnPostImageUpload(id,fileName,UploadID)
{
       $(id).attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       if (id="teacher")
       $scope.profileImgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       else if (id="Contact")
       $scope.contactPersonRecord.cp_imgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;	
       $scope.$apply();
}
function fnshowSubScreen(teacherID) {
       subScreen = true;
	var emptyTeacherProfile = {
		teacherName: "",
		teacherID: "",
		profileImgPath: "",
		general: {
			qualification: "",
			class: "Select option",
                        gender: "",
			dob: "",
			contactNo: "",
			emailID: "",
			shortName: "",
			bloodGroup: "",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: ""
			}
		},

		emergency: {
			existingMedicalDetails: "",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}]

		}
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyTeacherProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if(teacherID!=null||teacherID!=""){
            dataModel.teacherID = teacherID;
            //Screen Specific DataModel Ends
            var response = fncallBackend('TeacherProfile', 'View', dataModel, [{entityName:"teacherID",entityValue:teacherID}], $scope);
        }
        return true;
}
// Cohesive Generic query Frame Work Starts---------------------------------------------------------- 
function fnTeacherProfileQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.profileImgPath = "";
        $("#teacher").attr("src","");
	$scope.general = "";
//	$scope.emergency = "";
        $scope.emergency={
			existingMedicalDetails: "",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}]

		};
	$scope.teacherNamereadOnly = false;
	$scope.teacherIDreadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
        
	$scope.profileImgPathNamereadOnly = true;
	$scope.qualificationreadOnly = true;
	$scope.classReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.emailIDreadOnly = true;
	$scope.contactNoreadOnly = true;
	$scope.shortNamereadOnly = true;
	$scope.bloodreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.cpImgPathreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	//Screen Specifc Scope Ends	

	// single View starts
	$scope.contactPersonRecord = null;
	$scope.contactPersonTable = null;
	$scope.contactPersonCurIndex = 0;
	$scope.contactPersonShow = false;
	// sngle view Ends	

	// Generic Field Starts 
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.svwAddDeteleDisable = true; // single View
	//Generic Field Ends

	return true;
}
// Cohesive Query Frame Work Ends	
// Cohesive View Frame Work Starts
function fnTeacherProfileView() {
	var emptyTeacherProfile = {
		teacherName: "",
		teacherID: "",
		profileImgPath: "",
		general: {
			qualification: "",
			class: "Select option",
                        gender: "",
			dob: "",
			contactNo: "",
			emailID: "",
			shortName: "",
			bloodGroup: "",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: ""
			}
		},

		emergency: {
			existingMedicalDetails: "",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}]

		}

	};
	//Screen Specific DataModel Starts
	var dataModel = emptyTeacherProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null||$scope.teacherID!=""){
            dataModel.teacherID = $scope.teacherID;
            //Screen Specific DataModel Ends
            var response = fncallBackend('TeacherProfile', 'View', dataModel,[{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
       }
        return true;
}
// Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnTeacherProfileMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
			break;

		case 'Save':
			if ($scope.teacherName == '' || $scope.teacherName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name']);
				return false;
			}
			if ($scope.teacherID == '' || $scope.teacherID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID']);
				return false;
			}
//			if ($scope.general.qualification == '' || $scope.general.qualification == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Qualification in General Tab']);
//				return false;
//			}
//			if ($scope.general.gender == '' || $scope.general.gender == null || $scope.general.gender == 'Select option') {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['gender in General Tab']);
//				return false;
//			}
//			if ($scope.general.dob == '' || $scope.general.dob == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Date Of Birth in General Tab']);
//				return false;
//			}
//                        if ($scope.general.bloodGroup == '' || $scope.general.bloodGroup == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Blood Group in Others Tab']);
//				return false;
//			}
			
			/*if ($scope.general.shortName == '' || $scope.general.shortName == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Short Name']);
				return false;
			}*/
//                        if ($scope.general.address == '' || $scope.general.address == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Address']);
//				return false;
//			}
//			if ($scope.general.address.addressLine1 == '' || $scope.general.address.addressLine1 == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Door Number in Address Tab']);
//				return false;
//			}
//			if ($scope.general.address.addressLine2 == '' || $scope.general.address.addressLine2 == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Street in Address Tab']);
//				return false;
//			}
//			if ($scope.general.address.addressLine3 == '' || $scope.general.address.addressLine3 == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['City in Address Tab']);
//				return false;
//			}
//			if ($scope.general.address.addressLine4 == '' || $scope.general.address.addressLine4 == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Pin Code in Address Tab']);
//				return false;
//			}
			
//			if ($scope.contactPersonTable == null || $scope.contactPersonTable.length == 0) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Contact Person Details']);
//				return false;
//			}
//			for (i = 0; i < $scope.contactPersonTable.length; i++) {
//				if ($scope.contactPersonTable[i].cp_Name == '' || $scope.contactPersonTable[i].cp_Name == null) {
//					fn_Show_Exception_With_Param('FE-VAL-001', ['Contact Person Name  in Profile Tab' + 'record ' + (i + 1)]);
//					return false;
//				}
//				if ($scope.contactPersonTable[i].cp_relationship == '' || $scope.contactPersonTable[i].cp_relationship == null) {
//					fn_Show_Exception_With_Param('FE-VAL-001', ['Contact Person Relationship in Profile Tab ' + 'record ' + (i + 1)]);
//					return false;
//				}
//				if ($scope.contactPersonTable[i].cp_emailID == '' || $scope.contactPersonTable[i].cp_emailID == null) {
//					fn_Show_Exception_With_Param('FE-VAL-001', ['Contact Person Mail ID in Contact Tab ' + 'record ' + (i + 1)]);
//					return false;
//				}
//				if ($scope.contactPersonTable[i].cp_contactNo == '' || $scope.contactPersonTable[i].cp_contactNo == null) {
//					fn_Show_Exception_With_Param('FE-VAL-001', ['Contact Person Contact Number in Contact Tab ' + 'record ' + (i + 1)]);
//					return false;
//				}
//			}
//                       if ($scope.general.contactNo == '' || $scope.general.contactNo == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Contact Number in the Other Tab']);
//				return false;
//			}
//			if ($scope.general.emailID == '' || $scope.general.emailID == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Email ID in the Other Tab']);
//				return false;
//			}
			break;


	}
	return true;
}
// Screen Specific Mandatory Ends

function fnTeacherProfileDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			break;

		case 'Save':
			if (!fnEmailvalidation($scope))
				return false;

			if (!fnPhoneNumberValidation($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}
// Specific Field Validation Starts
function fnEmailvalidation($scope) {
	var filter = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

     if($scope.general.emailID!==null&&$scope.general.emailID!=''){
    
	if (!filter.test($scope.general.emailID)) {
		fn_Show_Exception_With_Param('FE-VAL-002', ['Email ID in Others Tab']);
		return false;
	}
    }
        
      if($scope.contactPersonTable!=null){  
        
	for (i = 0; i < $scope.contactPersonTable.length; i++) {
            
            if($scope.contactPersonTable[i].cp_emailID!==null&&$scope.contactPersonTable[i].cp_emailID!=''){
            
		if (!filter.test($scope.contactPersonTable[i].cp_emailID)) {
			fn_Show_Exception_With_Param('FE-VAL-002', ['Family Tab Email Id ' + 'record ' + (i + 1)]);
			return false;
		}
            }
	}
        
    }
	return true;

}


function fnPhoneNumberValidation($scope) {
//	var filter = /^\d{10}$/;
//        var filter=/^\+(?:[0-9] ?){6,14}[0-9]$/;
//	if (!filter.test($scope.general.contactNo)) {
//		fn_Show_Exception_With_Param('FE-VAL-002', ['Contact Number In General Tab']);
//		return false;
//	}
//	for (i = 0; i < $scope.contactPersonTable.length; i++) {
//		if (!filter.test($scope.contactPersonTable[i].cp_contactNo)) {
//			fn_Show_Exception_With_Param('FE-VAL-002', ['Emergency Tab Contact Number ' + 'record ' + (i + 1)]);
//			return false;
//		}
//	}
	return true;
}
// Specific field Validation Ends

// cohesive Create Frame Work Starts
function fnTeacherProfileCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// screen Specfic Scope Starts  
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.profileImgPath = "";
	$scope.profileImgPath = "";
        $("#teacher").attr("src","");
        $("#TeacherprofileImg").val(""); 
        $("#Contact").attr("src","");
        $("#ContactprofileImg").val(""); 
	$scope.general = new Object();
	$scope.general.qualification = "",
	$scope.general.class = 'Select option',
        $scope.general.gender = '',
	$scope.general.dob = "";
	$scope.general.contactNo = "";
	$scope.general.emailID = "";
	$scope.general.bloodGroup = "";
	$scope.general.shortName = "",
	$scope.emergency = new Object();
	$scope.emergency.existingMedicalDetails = null;
	$scope.contactPersonTable = new Array();
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = false;
	$scope.profileImgPathNamereadOnly = false;
	$scope.qualificationreadOnly = false;
        $scope.genderReadonly= false;
	$scope.classReadonly = true;
	$scope.dobreadOnly = false;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", false );
	$scope.emailIDreadOnly = false;
	$scope.bloodreadOnly = false;
	$scope.cpImgPathreadOnly = false;
	$scope.shortNamereadOnly = false;
	$scope.contactNoreadOnly = false;
	$scope.streetreadOnly = false;
	$scope.doornumberreadOnly = false;
	$scope.pincodereadOnly = false;
	$scope.cityreadOnly = false;
	$scope.medicalDetailreadonly = false;
	$scope.tableOperationShow = true;
	$scope.contactpersonreadOnly = false;
	$scope.relationshipreadOnly = false;
	$scope.occupationreadOnly = false;
	$scope.cpEmailidreadOnly = false;
	$scope.cpContactnumberreadOnly = false;
	$scope.classes = Institute.ClassMaster;
	$scope.subjects = Institute.SubjectMaster;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	$scope.svwAddDeteleDisable = false; // single View
	//$scope.searchShow = true;
	// Generic Field Ends

	// Single View Starts
	$scope.contactPersonRecord = null;
	$scope.contactPersonTable = null;
	$scope.contactPersonCurIndex = 0;
	$scope.contactPersonShow = false;
	// Single View Ends 

	return true;
}
// Cohesive Create Frame Work Ends

// Cohesive Edit Frame Work Starts
function fnTeacherProfileEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Frame Work Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.svwAddDeteleDisable = false; // single View

	// Generic frame Work Ends	

	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.profileImgPathNamereadOnly = false;
	$scope.qualificationreadOnly = false;
        $scope.genderReadonly= false;
	$scope.shortNamereadOnly = false;
	$scope.classReadonly = true;
	$scope.dobreadOnly = false;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", false );
	$scope.bloodreadOnly = false;
	$scope.emailIDreadOnly = false;
	$scope.contactNoreadOnly = false;
	$scope.medicalDetailreadonly = false;
	$scope.streetreadOnly = false;
	$scope.doornumberreadOnly = false;
	$scope.pincodereadOnly = false;
	$scope.cityreadOnly = false;
	$scope.cpImgPathreadOnly = false;
	$scope.contactpersonreadOnly = false;
	$scope.relationshipreadOnly = false;
	$scope.occupationreadOnly = false;
	$scope.cpEmailidreadOnly = false;
	$scope.cpContactnumberreadOnly = false;
	// Screen Specific Scope Ends
	return true;
}
// cohesive Edit Frame Work Ends

// Cohesive Delete Frame Work Starts
function fnTeacherProfileDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.svwAddDeteleDisable = true; // single View
	// Generic field Ends
	// Screen Specfic Scope Starts	
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.qualificationreadOnly = true;
        $scope.genderReadonly= true;
	$scope.classReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.emailIDreadOnly = true;
	$scope.cpImgPathreadOnly = true;
	$scope.contactNoreadOnly = true;
	$scope.bloodreadOnly = true;
	$scope.shortNamereadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpContactnumberreadOnly = true;
	// screen Specific scope Ends
	return true;
}
// cohesive Authorize Frame Work Starts
function fnTeacherProfileAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	$scope.svwAddDeteleDisable = true; // single View
	// Generic Field Ends	
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.qualificationreadOnly = true;
        $scope.genderReadonly= true;
	$scope.classReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.emailIDreadOnly = true;
	$scope.contactNoreadOnly = true;
	$scope.bloodreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpContactnumberreadOnly = true;
	// screen Specific Scope Ends
	return true;
}
// cohesive Authorize Framework Ends
// Cohesive Reject Frame Work Starts 
function fnTeacherProfileReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope()
	// generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	$scope.svwAddDeteleDisable = true; // single View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.qualificationreadOnly = true;
        $scope.genderReadonly= true;
	$scope.classReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.bloodreadOnly = true;
	$scope.emailIDreadOnly = true;
	$scope.contactNoreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpContactnumberreadOnly = true;
	// Screen Specfic Scope Ends

	return true;
}
// Cohesive Reject Frame Work Ends

// cohesive Back Frame Work Starts
function fnTeacherProfileBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specfic Scope Starts
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.teacherName = null;
		$scope.teacherID = null;
		$scope.profileImgPath = null;
		$scope.shortName = null;
		$scope.general = null;
		$scope.emergency = null;
		$scope.contactPersonTable = null;
                $("#teacher").attr("src","");
                $("#TeacherprofileImg").val(""); 
                $("#Contact").attr("src","");
                $("#ContactprofileImg").val(""); 
	}

	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.qualificationreadOnly = true;
        $scope.genderReadonly= true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.classReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.emailIDreadOnly = true;
	$scope.shortNamereadOnly = true;
	$scope.contactNoreadOnly = true;
	$scope.bloodreadOnly = true;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.medicalDetailreadonly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	// Screen Specfic Scope Ends

	// Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	// Generic Field Ends
}
// Cohesive Save Frame Work Starts
function fnTeacherProfileSave() {
	var emptyTeacherProfile = {
		teacherName: "",
		teacherID: "",
		profileImgPath: "",
		general: {
			qualification: "",
			class: "Select option",
                        gender: "",
			dob: "",
			contactNo: "",
			emailID: "",
			shortName: "",
			bloodGroup: "",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: ""
			}
		},

		emergency: {
//			existingMedicalDetails: [{
//				medicalDetail: ""
//			}, {
//				medicalDetail: ""
//			}],
                        existingMedicalDetails:"",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}]
		}
	};
	// Screen Specific DataModel Starts
	var dataModel = emptyTeacherProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
        if($scope.teacherName!=null)
	dataModel.teacherName = $scope.teacherName;
        if($scope.profileImgPath!=null)
	dataModel.profileImgPath = $scope.profileImgPath;
        if($scope.general!=null){
//	dataModel.general = $scope.general;
        dataModel.general.qualification = $scope.general.qualification;
        dataModel.general.class = $scope.general.class;
        dataModel.general.gender = $scope.general.gender;
        dataModel.general.dob = $scope.general.dob;
        dataModel.general.contactNo = $scope.general.contactNo;
        dataModel.general.emailID = $scope.general.emailID;
        dataModel.general.shortName = $scope.general.shortName;
        dataModel.general.bloodGroup = $scope.general.bloodGroup;
        dataModel.general.contactNo = $scope.general.contactNo;
        
        if(typeof $scope.general.address!=='undefined'){
            
        
            dataModel.general.address.addressLine1 = $scope.general.address.addressLine1;
            dataModel.general.address.addressLine2 = $scope.general.address.addressLine2;
            dataModel.general.address.addressLine3 = $scope.general.address.addressLine3;
            dataModel.general.address.addressLine4 = $scope.general.address.addressLine4;
        
        }
        
    }
        if($scope.contactPersonTable!=null)
	dataModel.emergency.contactPerson = $scope.contactPersonTable;
        if($scope.emergency.existingMedicalDetails!=null)
	dataModel.emergency.existingMedicalDetails = $scope.emergency.existingMedicalDetails;
	//Screen Specific DataModel Ends
	var response = fncallBackend('TeacherProfile', parentOperation, dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	return true;
}
// Screen Specific Save Framework Ends
function fndobDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.general != null) {
//		for (i = 0; i < $scope.general.length; i++) {
			$scope.general.dob = $.datepicker.formatDate('dd-mm-yy', $("#dateOfBirth").datepicker("getDate"));
//		}
		$scope.$apply();
	}
}

function fnsetDateScopeforTable() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.general != null) {
//		for (i = 0; i < $scope.general.length; i++) {
			$scope.general.dob = $.datepicker.formatDate('dd-mm-yy', $("#dateOfBirth").datepicker("getDate"));

//		}
	}
}

function fnsetDateScope() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.general != null) {
//		for (i = 0; i < $scope.general.length; i++) {
			$scope.general.dob = $.datepicker.formatDate('dd-mm-yy', $("#dateOfBirth").datepicker("getDate"));

//		}
		$scope.$apply();
	}
}

function fnConvertmvw(tableName, responseObject) {
	switch (tableName) {
		case 'contactPersonTable':
			var contactPersonTable = new Array();
			responseObject.forEach(fnConvert1);

			function fnConvert1(value, index, array) {
				contactPersonTable[index] = new Object();
				contactPersonTable[index].idx = index;
				contactPersonTable[index].checkBox = false;
				contactPersonTable[index].cp_Name = value.cp_Name;
				contactPersonTable[index].cp_relationship = value.cp_relationship;
				contactPersonTable[index].cp_occupation = value.cp_occupation;
				contactPersonTable[index].cp_emailID = value.cp_emailID;
				contactPersonTable[index].cp_contactNo = value.cp_contactNo;
				contactPersonTable[index].cp_imgPath = value.cp_imgPath;
			}
			return contactPersonTable;
			break;

	}
}
function fnTeacherProfilepostBackendCall(response)
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.teacherNamereadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
		$scope.teacherIDreadOnly = true;
		$scope.qualificationreadOnly = true;
                $scope.genderReadonly= true;
		$scope.profileImgPathNamereadOnly = true;
		$scope.classReadonly = true;
		$scope.dobreadOnly = true;
                $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
		$scope.emailIDreadOnly = true;
		$scope.contactNoreadOnly = true;
		$scope.bloodreadOnly = true;
		$scope.medicalDetailreadonly = true;
		$scope.contactpersonreadOnly = true;
		$scope.relationshipreadOnly = true;
		$scope.occupationreadOnly = true;
		$scope.cpEmailidreadOnly = true;
		$scope.cpContactnumberreadOnly = true;
		$scope.streetreadOnly = true;
		$scope.doornumberreadOnly = true;
		$scope.pincodereadOnly = true;
		$scope.cityreadOnly = true;
		$scope.shortNamereadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.svwAddDeteleDisable = true; // single View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.teacherID = "";
		$scope.teacherName ="";
		$scope.profileImgPath ="";
		$scope.general ={};
		$scope.emergency ={};
                $scope.contactPersonShow=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.teacherName = response.body.teacherName;
		$scope.teacherID = response.body.teacherID;
		$scope.profileImgPath = response.body.profileImgPath;
		$scope.general = response.body.general;
		$scope.emergency.existingMedicalDetails = response.body.emergency.existingMedicalDetails;
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends      
		//Single View Response Starts 
                
                if(response.body.emergency.contactPerson.length!=0){
                
		    $scope.contactPersonTable = fnConvertmvw('contactPersonTable', response.body.emergency.contactPerson);
                }else{
                    $scope.contactPersonTable=null;
                }
            
            
                $scope.contactPersonCurIndex = 0;
		if ($scope.contactPersonTable != null) {
			$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
			$scope.contactPersonShow = true;
		}
		//Screen Specific Single View Ends	
                }
		//Single View Response Ends 
                
           if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
             subScreen=false;
	 }  
               $("#teacher").attr("src",$scope.profileImgPath);
               $("#Contact").attr("src",$scope.ContactprofileImg);
		return true;

}
}