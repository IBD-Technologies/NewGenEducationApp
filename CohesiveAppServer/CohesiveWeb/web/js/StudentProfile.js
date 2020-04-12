/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
	//Screen Specific Scope Starts
	$scope.studentName = "";
	$scope.studentID = "";
	$scope.StudentMaster = [{
		StudentId:"",
		StudentName: ""
	}];
	$scope.profileImgPath = "";
	$scope.note = "";
	$scope.general = {};
        $scope.general.gender="";
	$scope.family = "";
//	$scope.emergency = "";
        $scope.emergency={existingMedicalDetails:""};
	//$scope.classes = ["Select option"];
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
//	$scope.sectionreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
//	$scope.standardreadOnly = true;
	$scope.classReadonly = true;
        $scope.genderReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.bloodreadOnly = true;
        $scope.nationalIDreadonly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
        $scope.notificationRequiredreadOnly = true;
        $scope.languageReadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.cpimgPathreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.notereadOnly = true;
	$scope.memberNamereadOnly = true;
	$scope.memberRelationshipreadOnly = true;
	$scope.memberOccupationreadOnly = true;
	$scope.memberEmailIDreadOnly = true;
	$scope.memberContactNoreadOnly = true;
	//Screen Specific Scope Ends
	// Single View Starts
	$scope.familyRecord = null;
	$scope.familyTable = null;
	$scope.familycurIndex = 0;
	$scope.familyShow = false;

	$scope.contactPersonRecord = null;
	$scope.contactPersonTable = null;
	$scope.contactPersoncurIndex = 0;
	$scope.contactPersonShow = false;
	//Single View Ends

	//Generic Field Ends
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.svwAddDeteleDisable = true; // single View
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	//Generic Field Ends


	$scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'familyDetails') {
			if ($scope.familyTable != null && $scope.familyTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.familycurIndex;
				lsvwObject.tableObject = $scope.familyTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.familycurIndex = lsvwObject.curIndex;
				$scope.familyTable = lsvwObject.tableObject;
				$scope.familyRecord = $scope.familyTable[$scope.familycurIndex];
			        $("#family").attr("src",$scope.familyRecord.memberImgPath);
                            }
		} else if (tableName == 'ContactPersonDetails') {
			if ($scope.contactPersonTable != null && $scope.contactPersonTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.contactPersonCurIndex;
				lsvwObject.tableObject = $scope.contactPersonTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.contactPersonCurIndex = lsvwObject.curIndex;
				$scope.contactPersonTable = lsvwObject.tableObject;
				$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
			         $("#ContactPerson").attr("src",$scope.contactPersonRecord.cp_imgPath); 
                                }
		}


	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'familyDetails') {
			if ($scope.familyTable != null && $scope.familyTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.familycurIndex;
				lsvwObject.tableObject = $scope.familyTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.familycurIndex = lsvwObject.curIndex;
				$scope.familyTable = lsvwObject.tableObject;
				$scope.familyRecord = $scope.familyTable[$scope.familycurIndex];
			        $("#family").attr("src",$scope.familyRecord.memberImgPath);    
                                }
		} else if (tableName == 'ContactPersonDetails') {
			if ($scope.contactPersonTable != null && $scope.contactPersonTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.contactPersonCurIndex;
				lsvwObject.tableObject = $scope.contactPersonTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.contactPersonCurIndex = lsvwObject.curIndex;
				$scope.contactPersonTable = lsvwObject.tableObject;
				$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
		                 $("#ContactPerson").attr("src",$scope.contactPersonRecord.cp_imgPath); 	
                }
		}
	};

	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'familyDetails') {
				emptyTableRec = {
					idx: 0,
					memberName: "",
                                        memberID: "",
					memberRelationship: "",
					memberOccupation: "",
					memberEmailID: "",
					memberContactNo: "",
                                        notificationRequired: "",
                                        language: "",
                                        memberImgPath:""
				};

				if ($scope.familyTable == null)
					$scope.familyTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.familyShow;
				lsvwObject.curIndex = $scope.familycurIndex;
				lsvwObject.tableObject = $scope.familyTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.familyShow = lsvwObject.tableShow;
				$scope.familycurIndex = lsvwObject.curIndex;
				$scope.familyTable = lsvwObject.tableObject;
				$scope.familyRecord = $scope.familyTable[$scope.familycurIndex];
                                $("#family").attr("src",$scope.familyRecord.memberImgPath);    
                                   
			} else if (tableName == 'ContactPersonDetails') {
				emptyTableRec = {
					idx: 0,
					cp_Name: "",
                                        cp_ID: "",
					cp_relationship: "",
					cp_occupation: "",
					cp_emailID: "",
					cp_contactNo: "",
                                        notificationRequired: "",
                                        language: "",
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
                                 $("#ContactPerson").attr("src",$scope.contactPersonRecord.cp_imgPath); 
			}


		}

	};
	$scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'familyDetails') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.familyShow;
				lsvwObject.curIndex = $scope.familycurIndex;
				lsvwObject.tableObject = $scope.familyTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.familyShow = lsvwObject.tableShow;
				$scope.familycurIndex = lsvwObject.curIndex;
				$scope.familyTable = lsvwObject.tableObject;
				$scope.familyRecord = $scope.familyTable[$scope.familycurIndex];
		                 $("#family").attr("src",$scope.familyRecord.memberImgPath);    
                               	
                } else if (tableName == 'ContactPersonDetails') {
				var lsvwObject = new Object();
				lsvwObject.tableShow = $scope.contactPersonShow;
				lsvwObject.curIndex = $scope.contactPersonCurIndex;
				lsvwObject.tableObject = $scope.contactPersonTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.contactPersonShow = lsvwObject.tableShow;
				$scope.contactPersonCurIndex = lsvwObject.curIndex;
				$scope.contactPersonTable = lsvwObject.tableObject;
				$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
			        $("#ContactPerson").attr("src",$scope.contactPersonRecord.cp_imgPath);    
                      }
		}
	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'familyDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.familyShow;
			lsvwObject.curIndex = $scope.familycurIndex;
			lsvwObject.tableObject = $scope.familyTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'ContactPersonDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.contactPersonShow;
			lsvwObject.curIndex = $scope.contactPersoncurIndex;
			lsvwObject.tableObject = $scope.contactPersonTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'familyDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.familyShow;
			lsvwObject.curIndex = $scope.familycurIndex;
			lsvwObject.tableObject = $scope.familyTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'ContactPersonDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.contactPersonShow;
			lsvwObject.curIndex = $scope.contactPersoncurIndex;
			lsvwObject.tableObject = $scope.contactPersonTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


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


});
//--------------------------------------------------------------------------------------------------------------

//-------Default Load record Starts--------------------------------------
$(document).ready(function () {
	MenuName = "StudentProfile";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
        //fn_hide_parentspinner();
           
        $("#StudentprofileImg").change(function(){ 
        $("#StudentimgUpld").submit();
        
});

        $("#familyprofileImg").change(function(){ 
        $("#FamilyimgUpld").submit();
        
});
 
        $("#ContactprofileImg").change(function(){ 
        $("#ContactimgUpld").submit();
        
});

$('#StudentimgUpld').submit(function(event){
   //var src={fileName:"",uploadID:""};
   $("#student").attr("src","");
  fileUpload('#student',$('#StudentimgUpld')[0],$('#StudentimgUpld').attr('action'),"StudentProfile");
  event.preventDefault();
    return true;
});   
$('#FamilyimgUpld').submit(function(event){
   //var src={fileName:"",uploadID:""};
   $("#family").attr("src","");
  fileUpload('#family',$('#FamilyimgUpld')[0],$('#FamilyimgUpld').attr('action'),"StudentProfile");
  event.preventDefault();
    return true;
}); 
$('#ContactimgUpld').submit(function(event){
   //var src={fileName:"",uploadID:""};
   $("#ContactPerson").attr("src","");
  fileUpload('#ContactPerson',$('#ContactimgUpld')[0],$('#ContactimgUpld').attr('action'),"StudentProfile");
  event.preventDefault();
    return true;
}); 


//fnGetSelectBoxdata();

	fnDatePickersetDefault('dateOfBirth',fndobDateEventHandler);
	fnsetDateScopeforTable();
    fnsetDateScope();
	selectBoxes= ['class','gender'];
        fnGetSelectBoxdata(selectBoxes);//Integration changes
   /*     fnSelectBoxDefault(selectBoxes);
	
	/*if (window.parent.StudentProfilekey.studentID !=null && window.parent.StudentProfilekey.studentID !='')
	{
		var studentID=window.parent.StudentProfilekey.studentID;
		
		 window.parent.StudentProfilekey.studentID =null;
		
		fnshowSubScreen(studentID);
		
	}*/
	
	/*var emptyStudentProfile = {
		studentName: "",
		studentID: "",
		profileImgPath: "",
		note: "",
		general: {
			class: "Select option",
			dob: "",
			bloodGroup: "",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: ""
			}
		},

		emergency: {
			existingMedicalDetails:null,
			contactPerson: [{
				idx:0,
				cp_Name: "",
                                cp_ID: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}],

		},
		family: [{
			idx:0,
			memberName: "",
                        memberID: "",
			memberRelationship: "",
			memberOccupation: "",
			memberEmailID: "",
			memberContactNo: ""
		}]


	};
        var dataModel=emptyStudentProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //Screen Specific DataModel Starts  
        dataModel.studentID =$scope.studentID;
	//Screen Specific DataModel Ends
        var response =fncallBackend('StudentProfile','View',dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope);
*/
});

function fnStudentProfilepostSelectBoxMaster()
{
   selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(Institute.ClassMaster.length>0){
    $scope.classes=Institute.ClassMaster;
    $scope.genders=Institute.GenderMaster;
    $scope.languages=Institute.LanguageMaster;
    $scope.bloodGroups=Institute.BloodGroupMaster;
    $scope.relationships=Institute.RelationshipMaster;
    window.parent.fn_hide_parentspinner();    
    //fnSelectBoxDefault(selectBoxes);
	
	if (window.parent.StudentProfilekey.studentID !=null && window.parent.StudentProfilekey.studentID !='')
	{
		var studentID=window.parent.StudentProfilekey.studentID;
		
		 window.parent.StudentProfilekey.studentID =null;
		
		fnshowSubScreen(studentID);
		
	}
	
	var emptyStudentProfile = {
		studentName: "",
		studentID: "",
		profileImgPath: "",
		note: "",
		general: {
			class: "Select option",
                        gender: "",
			dob: "",
			bloodGroup: "",
                        nationalID: "",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: ""
			}
		},

		emergency: {
			existingMedicalDetails:null,
			contactPerson: [{
				idx:0,
				cp_Name: "",
                                cp_ID: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
                                
				cp_imgPath: ""
			}],

		},
		family: [{
			idx:0,
			memberName: "",
                        memberID: "",
			memberRelationship: "",
			memberOccupation: "",
			memberEmailID: "",
                        notificationRequired: "",
                        language: "",
			memberContactNo: ""
		}]


	};
        var dataModel=emptyStudentProfile;
	//var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        //Screen Specific DataModel Starts 
	//Screen Specific DataModel Ends
      if ($scope.studentID!=null && $scope.studentID!=""){
           dataModel.studentID =$scope.studentID;
        var response =fncallBackend('StudentProfile','View',dataModel,[{entityName:"studentID",entityValue:$scope.studentID}],$scope); 
     
}
  $scope.$apply();
}
    }
}
function fnPostImageUpload(id,fileName,UploadID)
{
    
       $(id).attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      
       if (id =='#student')
      // $("#family").attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
         $scope.profileImgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       else if(id =='#family') 
         $scope.familyRecord.memberImgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       else if(id =='#ContactPerson') 
        $scope.contactPersonRecord.cp_imgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;
           
    // $scope.memberImgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       
       $scope.$apply();
   
}

/*function fnPostImageUpload(fileName,UploadID)
{
    
       $("#family").attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       $scope.memberImgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       $scope.$apply();
   
}

function fnPostImageUpload(fileName,UploadID)
{
    
       $("#ContactPerson").attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       $scope.ContactprofileImg="/CohesiveUpload/images/"+UploadID+"/"+fileName;
       $scope.$apply();
   
}
*/
// Default Record Load Ends
//Cohesive Query Framework Starts

function fnshowSubScreen(studentID)
{
    subScreen=true;
var emptyStudentProfile = {
		studentName: "",
		studentID: null,
		profileImgPath: null,
		note: "",
		general: {
			class: "Select option",
                        gender: "",
			dob: "",
			bloodGroup: "",
                        nationalID: "",
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
			}],

		},
		family: [{
			idx: 0,
			memberName: "",
			memberRelationship: "",
			memberOccupation: "",
			memberEmailID: "",
                        notificationRequired: "",
                        language: "",
			memberContactNo: ""
		}]


		/*audit: {},
		error: {}*/
	};
	//Screen Specific Datamodel Starts
	var dataModel = emptyStudentProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//	$scope.studentID=studentID;
//        if($scope.studentID!=null)
//	dataModel.studentID = $scope.studentID;
//        if($scope.studentName!=null)
//         dataModel.studentName = $scope.studentName;
//	//Screen Specific Data Model Ends
//	var response = fncallBackend('StudentProfile', 'View', dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
        
        
        if(studentID!=null||studentID!=""){
            
            dataModel.studentID=studentID;
            var response = fncallBackend('StudentProfile', 'View', dataModel,[{entityName:"studentID",entityValue:studentID}], $scope);
        }
	/*if (response.header.status == 'success') {
		//Screen Specific Scope Starts
		$scope.studentNamereadOnly = true;
                $scope.studentNameSearchreadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.profileImgPathNamereadOnly = true;
		$scope.classReadonly = true;
		$scope.dobreadOnly = true;
		$scope.standardreadOnly = true;
		$scope.sectionreadOnly = true;
		$scope.notereadOnly = true;
		$scope.bloodreadOnly = true;
		$scope.contactpersonreadOnly = true;
		$scope.relationshipreadOnly = true;
		$scope.occupationreadOnly = true;
		$scope.cpImgPathreadOnly = true;
		$scope.cpEmailidreadOnly = true;
		$scope.medicalDetailreadonly = true;
		$scope.cpContactnumberreadOnly = true;
		$scope.streetreadOnly = true;
		$scope.doornumberreadOnly = true;
		$scope.pincodereadOnly = true;
		$scope.cityreadOnly = true;
		$scope.memberNamereadOnly = true;
		$scope.memberRelationshipreadOnly = true;
		$scope.memberOccupationreadOnly = true;
		$scope.memberEmailIDreadOnly = true;
		$scope.memberContactNoreadOnly = true;
		//Screen Specific Scope Ends
		//Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.audit = response.body.audit;
		$scope.svwAddDeteleDisable = true; // single View
		//Generic Field Ends
		//Screen Specific Response Scope Starts
		$scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
		$scope.general = response.body.general;
		$scope.emergency = response.body.emergency;
		$scope.emergency.existingMedicalDetails = response.body.emergency.existingMedicalDetails;
		$scope.emergency.contactPerson = response.body.emergency.contactPerson;
		$scope.family = response.body.family;
		//Screen Specific Response Scope Ends
		//Single View Response Starts
		$scope.familyTable = fnConvertmvw('familyTable',response.body.family);
		$scope.familycurIndex = 0;
		if ($scope.familyTable != null) {
			$scope.familyRecord = $scope.familyTable[$scope.familycurIndex];
			$scope.familyShow = true;
		}
		$scope.contactPersonTable = fnConvertmvw('contactPersonTable',response.body.emergency.contactPerson);
		$scope.contactPersonCurIndex = 0;
		if ($scope.contactPersonTable != null) {
			$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
			$scope.contactPersonShow = true;
		}
		fnSelectResponseHandler(selectBoxes);//Select Box
		//Single View Response Scope Ends
		$scope.$apply();
		
		var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
		
		return true;
	} else {
		return false;
	}*/
	return true;
}

function fnStudentProfileQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Single View Starts
	$scope.familyRecord = null;
	$scope.familyTable = null;
	$scope.familycurIndex = 0;
	$scope.familyShow = false;

	$scope.contactPersonRecord = null;
	$scope.contactPersonTable = null;
	$scope.contactPersoncurIndex = 0;
	$scope.contactPersonShow = false;
	//Single View Ends
	// Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName ="";
	$scope.general = "";
	$scope.contactPerson = "";
	$scope.family = "";
	$scope.profileImgPath = "";
	$scope.note = "";
	$scope.general = "";
//	$scope.emergency = "";
        $scope.emergency={existingMedicalDetails: ""};
	$scope.family = "";
         $("#logo").attr("src","");
         $("#profileImg").val(""); 
         $("#family").attr("src","");
         $("#familyprofileImg").val(""); 
         $("#ContactPerson").attr("src","");
         $("#ContactprofileImg").val(""); 
         
          $("#student").attr("src","");    
        
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.profileImgPathNamereadOnly = true;
	$scope.classReadonly = true;
        $scope.genderReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
//	$scope.standardreadOnly = true;
//	$scope.sectionreadOnly = true;
	$scope.bloodreadOnly = true;
        $scope.nationalIDreadonly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
        $scope.notificationRequiredreadOnly=true;
        $scope.languageReadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpImgPathreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.notereadOnly = true;
	$scope.memberNamereadOnly = true;
	$scope.memberRelationshipreadOnly = true;
	$scope.memberOccupationreadOnly = true;
	$scope.memberEmailIDreadOnly = true;
	$scope.memberContactNoreadOnly = true;
	// Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.svwAddDeteleDisable = true; // single View
	//fnSelectResponseHandler(selectBoxes);//Select Box
	//Generic Field Ends
	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnStudentProfileView() {
	var emptyStudentProfile = {
		studentName: "",
		studentID: "",
		profileImgPath: "",
		note: "",
		general: {
			class: "Select option",
                        gender: "",
			dob: "",
			bloodGroup: "",
                        nationalID: "",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: "",
                                addressLine5: ""
			}
		},

		emergency: {
			existingMedicalDetails: "",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
                                cp_ID: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}],

		},
		family: [{
			idx: 0,
			memberName: "",
                        memberID: "",
			memberRelationship: "",
			memberOccupation: "",
			memberEmailID: "",
			memberContactNo: "",
                        notificationRequired: "",
                        language: "",
                        memberImgPath:""
		}]
	};
	//Screen Specific Datamodel Starts
	var dataModel = emptyStudentProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.studentID !=null||$scope.studentID ==""){
             dataModel.studentID = $scope.studentID;
             dataModel.studentName = $scope.studentName;
            //Screen Specific Data Model Ends
            var response = fncallBackend('StudentProfile', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
        }
        
        return true;
}

//Cohesive Query Framework Ends
//Screen Specific Mandatory Validation Starts
function fnStudentProfileMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
//			if ($scope.studentID == '' || $scope.studentID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student ID']);
//				return false;
//			}
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
			if ($scope.general.class == '' || $scope.general.class == null || $scope.general.class == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Class in General Tab']);
				return false;
			}
//                        if ($scope.general.gender == '' || $scope.general.gender == null || $scope.general.gender == 'Select option') {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Gender in General Tab']);
//				return false;
//			}
//			if ($scope.general.dob == '' || $scope.general.dob == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Date Of Birth in General Tab']);
//				return false;
//			}
			
			/*if ($scope.general.note == '' || $scope.general.note == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Note in General Tab']);
				return false;
			}*/
			if ($scope.familyTable == null || $scope.familyTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Family Member Details']);
				return false;
			}
			for (i = 0; i < $scope.familyTable.length; i++) {
				if ($scope.familyTable[i].memberName == '' || $scope.familyTable[i].memberName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Family Member Name In Family Tab " + "record " +(i + 1)]);
					return false;
				}
				if ($scope.familyTable[i].memberRelationship == '' || $scope.familyTable[i].memberRelationship == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Family Member Relationship in Family Tab " + "record " + (i + 1)]);
					return false;
				}
//				if ($scope.familyTable[i].memberOccupation == '' || $scope.familyTable[i].memberOccupation == null) {
//					fn_Show_Exception_With_Param('FE-VAL-001', ["Family Member Occupation in Family Tab " + "record " + (i + 1)]);
//					return false;
//				}
//				if ($scope.familyTable[i].memberEmailID == '' || $scope.familyTable[i].memberEmailID == null) {
//					fn_Show_Exception_With_Param('FE-VAL-001', ["Family Member Mail Id in Family Tab " + "record " + (i + 1)]);
//					return false;
//				}
				if ($scope.familyTable[i].memberContactNo == '' || $scope.familyTable[i].memberContactNo == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Family Member Contact Number in Family Tab " + "record " + (i + 1)]);
					return false;
				}
			}
//                         if ($scope.emergency.existingMedicalDetails == '' || $scope.emergency.existingMedicalDetails == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Medical Details in Medical Tab']);
//				return false;
//			}
//                        if ($scope.general.bloodGroup == '' || $scope.general.bloodGroup == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Blood Group in Medical Tab']);
//				return false;
//			}
//                        if ($scope.general.nationalID == '' || $scope.general.nationalID == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['National ID in Medical Tab']);
//				return false;
//			}

			/*if ($scope.contactPersonTable == null || $scope.contactPersonTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Contact person Details in Emergency Tab']);
				return false;
			}
			for (i = 0; i < $scope.contactPersonTable.length; i++) {
				if ($scope.contactPersonTable[i].cp_Name == '' || $scope.contactPersonTable[i].cp_Name == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Contact Person Name in Emergency Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.contactPersonTable[i].cp_relationship == '' || $scope.contactPersonTable[i].cp_relationship == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Contact Person Relationship in Emergency Tab" + "record " + (i + 1)]);
					return false;
				}
				if ($scope.contactPersonTable[i].cp_emailID == '' || $scope.contactPersonTable[i].cp_emailID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Contact Person Email Id in Emergency Tab" + "record " + (i + 1)]);
					return false;
				}
				if ($scope.contactPersonTable[i].cp_occupation == '' || $scope.contactPersonTable[i].cp_occupation == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Contact Person Occupation In Emergency Tab" + "record " + (i + 1)]);
					return false;
				}

				if ($scope.contactPersonTable[i].cp_contactNo == '' || $scope.contactPersonTable[i].cp_contactNo == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Contact Person Contact Number in Emergency Tab " + "record " + (i + 1)]);
					return false;
				}
			}*/

//			if ($scope.general.address == '' || $scope.general.address == null) {
//
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Address in Address Tab']);
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

			break;


	}
	return true;
}
//Screen Specific Mandatory validation Ends
// Screen Specific Default Validation Starts
function fnStudentProfileDefaultandValidate(operation) {
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

function fnEmailvalidation($scope) {
	var filter = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	for (i = 0; i < $scope.familyTable.length; i++) {
            
            if($scope.familyTable[i].memberEmailID!=null&&$scope.familyTable[i].memberEmailID!=''){
            
		if (!filter.test($scope.familyTable[i].memberEmailID)) {
			fn_Show_Exception_With_Param('FE-VAL-002', ['Family Tab Email Id ' + 'record ' + (i + 1)]);
			return false;
		}
            }
                
	}
	for (i = 0; i < $scope.contactPersonTable.length; i++) {
            
            if($scope.contactPersonTable[i].cp_emailID!=null&&$scope.contactPersonTable[i].cp_emailID!=''){
            
		if (!filter.test($scope.contactPersonTable[i].cp_emailID)) {
			fn_Show_Exception_With_Param('FE-VAL-002', ['Emergency Tab Email Id ' + 'record ' + (i + 1)]);
			return false;
		}
            }
                
	}
        
        
	return true;

}


function fnPhoneNumberValidation($scope) {
//	var filter = /^\d{10}$/;
//	for (i = 0; i < $scope.familyTable.length; i++) {
//		if (!filter.test($scope.familyTable[i].memberContactNo)) {
//			fn_Show_Exception_With_Param('FE-VAL-002', ['Family Tab Contact Number ' + 'record ' + (i + 1)]);
//			return false;
//		}
//	}
//	for (i = 0; i < $scope.contactPersonTable.length; i++) {
//		if (!filter.test($scope.contactPersonTable[i].cp_contactNo)) {
//			fn_Show_Exception_With_Param('FE-VAL-002', ['Emergency Tab Contact Number ' + 'record ' + (i + 1)]);
//			return false;
//		}
//	}
	return true;
}
// Screen Specific Default Validation Ends
// Cohesive Create Framework Starts
function fnStudentProfileCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.studentID = "";
	$scope.studentName = "";
	$scope.profileImgPath = "";
	$scope.note = "";
        $("#logo").attr("src","");
        $("#profileImg").val(""); 
        $("#family").attr("src","");
        $("#familyprofileImg").val(""); 
        $("#ContactPerson").attr("src","");
        $("#ContactprofileImg").val(""); 
        $("#student").attr("src","");
	$scope.general = new Object();
        //$scope.classes=["Select option"];	
        $scope.general.class = 'Select option';
        $scope.general.gender = '';
		$scope.general.dob = "";
		$scope.general.emailID = "";
		$scope.general.bloodGroup = "";
                $scope.general.nationalID = "";
		$scope.emergency = new Object();
	$scope.emergency.existingMedicalDetails = "";
	$scope.contactPersonTable = new Array();
	$scope.familyTable = new Array();
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = false;
	$scope.classReadonly = false;
        $scope.genderReadonly = false;
	$scope.profileImgPathNamereadOnly = false;
	$scope.dobreadOnly = false;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", false );
//	$scope.standardreadOnly = false;
//	$scope.sectionreadOnly = false;
	$scope.bloodreadOnly = false;
        $scope.nationalIDreadonly = false;
	$scope.notereadOnly = false;
	$scope.contactpersonreadOnly = false;
	$scope.relationshipreadOnly = false;
        $scope.notificationRequiredreadOnly=false;
        $scope.languageReadOnly = false;
	$scope.occupationreadOnly = false;
	$scope.cpEmailidreadOnly = false;
	$scope.cpImgPathreadOnly = false;
	$scope.medicalDetailreadonly = false;
	$scope.cpContactnumberreadOnly = false;
	$scope.streetreadOnly = false;
	$scope.doornumberreadOnly = false;
	$scope.pincodereadOnly = false;
	$scope.cityreadOnly = false;
	$scope.memberNamereadOnly = false;
	$scope.memberRelationshipreadOnly = false;
	$scope.memberOccupationreadOnly = false;
	$scope.memberEmailIDreadOnly = false;
	$scope.memberContactNoreadOnly = false;
	//screen Specific Scope Ends	
	//Single View Starts
	$scope.familyRecord = null;
	$scope.familyTable = null;
	$scope.familycurIndex = 0;
	$scope.familyShow = false;

	$scope.contactPersonRecord = null;
	$scope.contactPersonTable = null;
	$scope.contactPersoncurIndex = 0;
	$scope.contactPersonShow = false;
	//single View Ends

	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Creation';
	$scope.svwAddDeteleDisable = false; // single View
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
	//fnSelectResponseHandler(selectBoxes);//Select Box
	return true;
}
// Cohesive Create Framework Ends
// Cohesive Edit Framework starts
function fnStudentProfileEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.svwAddDeteleDisable = false; // single View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = false;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.classReadonly = false;
        $scope.genderReadonly = false;
	$scope.profileImgPathNamereadOnly = false;
	$scope.dobreadOnly = false;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", false );
	$scope.notereadOnly = false;
//	$scope.standardreadOnly = false;
//	$scope.sectionreadOnly = false;
	$scope.bloodreadOnly = false;
        $scope.nationalIDreadonly = false;
	$scope.contactpersonreadOnly = false;
	$scope.relationshipreadOnly = false;
        $scope.notificationRequiredreadOnly=false;
        $scope.languageReadOnly = false;
	$scope.occupationreadOnly = false;
	$scope.cpEmailidreadOnly = false;
	$scope.medicalDetailreadonly = false;
	$scope.cpContactnumberreadOnly = false;
	$scope.cpImgPathreadOnly = false;
	$scope.streetreadOnly = false;
	$scope.doornumberreadOnly = false;
	$scope.pincodereadOnly = false;
	$scope.cityreadOnly = false;
	$scope.memberNamereadOnly = false;
	$scope.memberRelationshipreadOnly = false;
	$scope.memberOccupationreadOnly = false;
	$scope.memberEmailIDreadOnly = false;
	$scope.memberContactNoreadOnly = false;
	//Screen Specific Scope Ends	
	return true;
}
//Cohesive Edit Framework Ends
// Cohesive Delete Framework Starts
function fnStudentProfileDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.svwAddDeteleDisable = true; // single View
	//Generic Field Starts
	//screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.classReadonly = true;
        $scope.genderReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.notereadOnly = true;
	$scope.bloodreadOnly = true;
        $scope.nationalIDreadonly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
        $scope.notificationRequiredreadOnly=true;
        $scope.languageReadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpImgPathreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.memberNamereadOnly = true;
	$scope.memberRelationshipreadOnly = true;
	$scope.memberOccupationreadOnly = true;
	$scope.memberEmailIDreadOnly = true;
	$scope.memberContactNoreadOnly = true;
	//Screen Specific Delete Framework Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive AuthoriZation Framework Starts
function fnStudentProfileAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	$scope.svwAddDeteleDisable = true; // single View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.classReadonly = true;
        $scope.genderReadonly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.notereadOnly = true;
	$scope.bloodreadOnly = true;
        $scope.nationalIDreadonly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
        $scope.notificationRequiredreadOnly=true;
        $scope.languageReadOnly = true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.cpimgPathreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.memberNamereadOnly = true;
	$scope.memberRelationshipreadOnly = true;
	$scope.memberOccupationreadOnly = true;
	$scope.memberEmailIDreadOnly = true;
	$scope.memberContactNoreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive AuthoriZation Framework Ends
//Cohesive Reject Framework Starts
function fnStudentProfileReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.operation = 'Rejection';
	$scope.svwAddDeteleDisable = true; // single View
	//Generic Field Ends
	//Screen Specific Scope Starts 
	$scope.studentNamereadOnly = true;
        $scope.studentNameSearchreadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.classReadonly = true;
        $scope.genderReadonly = true;
	$scope.profileImgPathNamereadOnly = true;
	$scope.dobreadOnly = true;
        $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
	$scope.notereadOnly = true;
	$scope.bloodreadOnly = true;
        $scope.nationalIDreadonly = true;
	$scope.contactpersonreadOnly = true;
	$scope.relationshipreadOnly = true;
        $scope.notificationRequiredreadOnly=true;
	$scope.occupationreadOnly = true;
	$scope.cpEmailidreadOnly = true;
	$scope.medicalDetailreadonly = true;
	$scope.cpContactnumberreadOnly = true;
	$scope.streetreadOnly = true;
	$scope.doornumberreadOnly = true;
	$scope.pincodereadOnly = true;
	$scope.cityreadOnly = true;
	$scope.cpimgPathreadOnly = true;
	$scope.memberNamereadOnly = true;
	$scope.memberRelationshipreadOnly = true;
	$scope.memberOccupationreadOnly = true;
	$scope.memberEmailIDreadOnly = true;
	$scope.memberContactNoreadOnly = true;
	// Screen Specific Scope Ends

	return true;
}
// Cohesive reject Framework Ends
// Cohesive Back Framework Starts




function fnStudentProfileBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	// Screen Specific Scope Starts
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.studentName = "";
		$scope.studentID = "";
		$scope.profileImgPath = "";
		$scope.general = null;
		$scope.emergency =null;
		$scope.family = null;
                $("#logo").attr("src","");
                $("#profileImg").val(""); 
                $("#family").attr("src","");
                $("#familyprofileImg").val(""); 
                $("#ContactPerson").attr("src","");
                $("#ContactprofileImg").val(""); 
                $("#student").attr("src","");

	}
	
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
                $scope.studentNameSearchreadOnly = true;
		$scope.classReadonly = true;
                $scope.genderReadonly = true;
		$scope.dobreadOnly = true;
                $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
		$scope.profileImgPathNamereadOnly = true;
		$scope.bloodreadOnly = true;
                $scope.nationalIDreadonly = true;
		$scope.notereadOnly = true;
		$scope.contactpersonreadOnly = true;
		$scope.relationshipreadOnly = true;
                $scope.notificationRequiredreadOnly=true;
		$scope.occupationreadOnly = true;
		$scope.cpEmailidreadOnly = true;
		$scope.cpImgPathreadOnly = true;
		$scope.medicalDetailreadonly = true;
		$scope.cpContactnumberreadOnly = true;
		$scope.streetreadOnly = true;
		$scope.doornumberreadOnly = true;
		$scope.pincodereadOnly = true;
		$scope.cityreadOnly = true;
		$scope.memberNamereadOnly = true;
		$scope.memberRelationshipreadOnly = true;
		$scope.memberOccupationreadOnly = true;
		$scope.memberEmailIDreadOnly = true;
		$scope.memberContactNoreadOnly = true;
		
		//Screen Specific Scope Ends
		//Generic Field Starts
	         $scope.operation = '';
		 $scope.mastershow = true;
	         $scope.detailshow = false;
                   $scope.auditshow = false;
		//fnSelectResponseHandler(selectBoxes);//Select Box
      //Generic Field Ends

}
// Cohesive Back Framework Ends
function fnStudentProfileSave() {

	var emptyStudentProfile = {
		studentName: "",
		studentID: "",
		profileImgPath: "",
		note: "",
		general: {
			class: "Select option",
                        gender: "",
			dob: "",
			bloodGroup: "",
                        nationalID:"",
			address: {
				addressLine1: "",
				addressLine2: "",
				addressLine3: "",
				addressLine4: "",
                                addressLine5: ""
			}
		},

		emergency: {
			existingMedicalDetails: "",
			contactPerson: [{
				idx: 0,
				cp_Name: "",
                                cp_ID: "",
				cp_relationship: "",
				cp_occupation: "",
				cp_emailID: "",
				cp_contactNo: "",
				cp_imgPath: ""
			}],

		},
		family: [{
			idx: 0,
			memberName: "",
                        memberID: "",
			memberRelationship: "",
			memberOccupation: "",
			memberEmailID: "",
			memberContactNo: "",
                        notificationRequired: false,
                        language: "",
                        memberImgPath: ""
		}]
	};
	var dataModel = emptyStudentProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.studentID!=null)
        dataModel.studentID = $scope.studentID;
       if($scope.studentName!=null)
	dataModel.studentName = $scope.studentName;
       if($scope.profileImgPath!=null)
         dataModel.profileImgPath = $scope.profileImgPath;
       if($scope.note!=null)
         dataModel.note = $scope.note;
     if($scope.emergency!=null)
          dataModel.emergency = $scope.emergency;
     if($scope.emergency.existingMedicalDetails!=null)
        dataModel.emergency.existingMedicalDetails = $scope.emergency.existingMedicalDetails;
     if($scope.contactPersonTable!=null)
          dataModel.emergency.contactPerson = $scope.contactPersonTable;
      
	 if($scope.familyTable!=null){
             
           $scope.familyTable.forEach(fnConvert1);
			 
			 
			 function fnConvert1(value,index,array){
                             
                             if(value.notificationRequired==''){
                                 
                                 value.notificationRequired=false;
                             }
                         }
          dataModel.family = $scope.familyTable;
          
      }
         if($scope.general!=null){
//          dataModel.general = $scope.general;
          dataModel.general.class = $scope.general.class;
          dataModel.general.gender = $scope.general.gender;
          dataModel.general.dob = $scope.general.dob;
          dataModel.general.bloodGroup = $scope.general.bloodGroup;
          dataModel.general.nationalID = $scope.general.nationalID;
          
          if(typeof $scope.general.address!=='undefined'){
            
        
            dataModel.general.address.addressLine1 = $scope.general.address.addressLine1;
            dataModel.general.address.addressLine2 = $scope.general.address.addressLine2;
            dataModel.general.address.addressLine3 = $scope.general.address.addressLine3;
            dataModel.general.address.addressLine4 = $scope.general.address.addressLine4;
        
          }
          
         }
          
	//dataModel.audit = $scope.audit;
	var response = fncallBackend('StudentProfile', parentOperation, dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
//cohesive Save Framework Ends
function fndobDateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.general !=null){
//	for (i = 0; i < $scope.general.length; i++) {
	$scope.general.dob = $.datepicker.formatDate('dd-mm-yy', $("#dateOfBirth").datepicker("getDate"));
//	}
	$scope.$apply();
}
}
function fnsetDateScopeforTable()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
if($scope.general !=null){
//for (i = 0; i < $scope.general.length; i++) {
	$scope.general.dob = $.datepicker.formatDate('dd-mm-yy', $("#dateOfBirth").datepicker("getDate"));
		
//}	
}
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
if($scope.general !=null){
//for (i = 0; i < $scope.general.length; i++) {
	$scope.general.dob = $.datepicker.formatDate('dd-mm-yy', $("#dateOfBirth").datepicker("getDate"));
		
//}
$scope.$apply();	
}
}
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		
			case 'contactPersonTable':
		   
			var contactPersonTable = new Array();
			 responseObject.forEach(fnConvert1);
			 
			 
			 function fnConvert1(value,index,array){
				     contactPersonTable[index] = new Object();
					 contactPersonTable[index].idx=index;
					 contactPersonTable[index].checkBox=false;
					 contactPersonTable[index].cp_Name=value.cp_Name;
                                          contactPersonTable[index].cp_ID=value.cp_ID;
					 contactPersonTable[index].cp_relationship=value.cp_relationship;
					 contactPersonTable[index].cp_occupation=value.cp_occupation;
					 contactPersonTable[index].cp_emailID=value.cp_emailID;
					 contactPersonTable[index].cp_contactNo=value.cp_contactNo;
					 contactPersonTable[index].cp_imgPath=value.cp_imgPath;
					}
			return contactPersonTable;
			break ;
			case 'familyTable':
		   
			var familyTable = new Array();
			 responseObject.forEach(fnConvert2);
			 
			 
			 function fnConvert2(value,index,array){
				     familyTable[index] = new Object();
					 familyTable[index].idx=index;
					 familyTable[index].checkBox=false;
					 familyTable[index].memberName=value.memberName;
                                         familyTable[index].memberID=value.memberID;
					 familyTable[index].memberRelationship=value.memberRelationship;
					 familyTable[index].memberOccupation=value.memberOccupation;
					 familyTable[index].memberEmailID=value.memberEmailID;
					 familyTable[index].memberContactNo=value.memberContactNo;
                                         familyTable[index].memberImgPath=value.memberImgPath;
                                         familyTable[index].notificationRequired=value.notificationRequired;
                                         familyTable[index].language=value.language;
					}
			return familyTable;
			break ;
			
		}
	}
        
 function fnStudentProfilepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.classReadonly = true;
                $scope.genderReadonly = true;
		$scope.dobreadOnly = true;
                $( "#dateOfBirth" ).datepicker( "option", "disabled", true );
		$scope.profileImgPathNamereadOnly = true;
		$scope.bloodreadOnly = true;
                $scope.nationalIDreadonly = true;
		$scope.notereadOnly = true;
		$scope.contactpersonreadOnly = true;
		$scope.relationshipreadOnly = true;
                $scope.notificationRequiredreadOnly=true;
		$scope.occupationreadOnly = true;
		$scope.cpEmailidreadOnly = true;
		$scope.cpImgPathreadOnly = true;
		$scope.medicalDetailreadonly = true;
		$scope.cpContactnumberreadOnly = true;
		$scope.streetreadOnly = true;
		$scope.doornumberreadOnly = true;
		$scope.pincodereadOnly = true;
		$scope.cityreadOnly = true;
		$scope.memberNamereadOnly = true;
		$scope.memberRelationshipreadOnly = true;
		$scope.memberOccupationreadOnly = true;
		$scope.memberEmailIDreadOnly = true;
		$scope.memberContactNoreadOnly = true;
		//Screen Specific Scope Ends
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
                $scope.studentName = "";
		$scope.studentID ="";
		$scope.profileImgPath ="";
		$scope.general ={};
		$scope.emergency ={};
		$scope.family = {};
                $scope.contactPersonShow=null;
                $scope.familyShow=null;
                $scope.audit = {};
		 }
                else
                {
                $scope.studentName = response.body.studentName;
		$scope.studentID = response.body.studentID;
                $scope.profileImgPath=response.body.profileImgPath;
		$scope.note = response.body.note;
		$scope.general = response.body.general;
                $scope.emergency.existingMedicalDetails = response.body.emergency.existingMedicalDetails;
                $scope.audit = response.audit;
//		$scope.contactPersonTable = fnConvertmvw('contactPersonTable',response.body.emergency.contactPerson);
//                $scope.contactPersonCurIndex = 0;
//		if ($scope.contactPersonTable != null) {
//			$scope.contactPersonRecord = $scope.contactPersonTable[$scope.contactPersonCurIndex];
//			$scope.contactPersonShow = true;
//		}
                
                if(response.body.family.length!=0){
                
		  $scope.familyTable = fnConvertmvw('familyTable',response.body.family);
                }else{
                    
                  $scope.familyTable=null;  
                }
             
		$scope.familycurIndex = 0;
		if ($scope.familyTable != null) {
			$scope.familyRecord = $scope.familyTable[$scope.familycurIndex];
			$scope.familyShow = true;
		}
		
		//Screen Specific Response Scope Ends
                $scope.general.class=response.body.general.class;
                $scope.general.gender=response.body.general.gender;
		//fnSelectResponseHandler(selectBoxes);//Select Box
            }
                  $("#student").attr("src",$scope.profileImgPath);    
                  $("#family").attr("src",$scope.familyRecord.memberImgPath);    
//                  $("#ContactPerson").attr("src",$scope.contactPersonRecord.cp_imgPath);    
		
         if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen=false;
	 }
           
         
         return true;

}

}
