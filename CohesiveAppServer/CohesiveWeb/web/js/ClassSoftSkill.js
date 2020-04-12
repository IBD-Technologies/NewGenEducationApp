/* 	
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	//Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
    $scope.OperationScopes=OperationScopes;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
    //Generic Field Ends
	//Screen Specific Scope Starts
	$scope.class = "Select option";
	$scope.exam = 'Select option';
	$scope.skillID = "";
	$scope.skillName = "Select option";
//	$scope.marks = "";
        $scope.SkillTable=null;
	$scope.skills = Institute.SkillMaster;
	$scope.ExamMaster = Institute.ExamMaster;
	$scope.classes=Institute.ClassMaster;
        $scope.categories=Institute.CategoryMaster;
	$scope.GradeDescription=null;
	$scope.examtypereadOnly = true;
        $scope.classReadonly = true;
	$scope.skillIDReadOnly = true;
	$scope.skillNamereadOnly = true;
        $scope.categoryReadOnly = true;
	$scope.teacherFeedbackReadOnly = true;
	//Screen Specific Scope Ends
	
	
	
	// Multiple View Starts
	$scope.SkillCurPage = 0;
	$scope.SkillTable = null;
	$scope.SkillShowObject = null;
	// Multiple View Ends

	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'Skill') {
			if ($scope.SkillTable != null && $scope.SkillTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.SkillCurPage;
				lsvwObject.tableObject = $scope.SkillTable;
				lsvwObject.screenShowObject = $scope.SkillShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.SkillCurPage = lsvwObject.curPage;
				$scope.SkillTable = lsvwObject.tableObject;
				$scope.SkillShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'Skill') {
			if ($scope.SkillTable != null && $scope.SkillTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.SkillCurPage;
				lsvwObject.tableObject = $scope.SkillTable;
				lsvwObject.screenShowObject = $scope.SkillShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.SkillCurPage = lsvwObject.curPage;
				$scope.SkillTable = lsvwObject.tableObject;
				$scope.SkillShowObject = lsvwObject.screenShowObject;
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'Skill') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					studentName: "",
				    studentID: "",
				    category: "",
				    teacherFeedback: ""
				};
				if ($scope.SkillTable == null)
					$scope.SkillTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.SkillCurPage;
				lsvwObject.tableObject = $scope.SkillTable;
				lsvwObject.screenShowObject = $scope.SkillShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.SkillCurPage = lsvwObject.curPage;
				$scope.SkillTable = lsvwObject.tableObject;
				$scope.SkillShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'Skill') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.SkillCurPage;
				lsvwObject.tableObject = $scope.SkillTable;
				lsvwObject.screenShowObject = $scope.SkillShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.SkillCurPage = lsvwObject.curPage;
				$scope.SkillTable = lsvwObject.tableObject;
				$scope.SkillShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'Skill') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.SkillCurPage;
			lsvwObject.tableObject = $scope.SkillTable;
			lsvwObject.screenShowObject = $scope.SkillShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'Skill') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.SkillCurPage;
			lsvwObject.tableObject = $scope.SkillTable;
			lsvwObject.screenShowObject = $scope.SkillShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'Skill') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.SkillTable);

		}
	};
	
	
	
	
	
	
	
	
	
});
//--------------------------------------------------------------------------------------------------------------

//Default Load Record Starts
$(document).ready(function () {
	MenuName = "ClassSoftSkill";
        selectBypassCount=0;
         window.parent.nokotser=$("#nokotser").val();
         window.parent.Entity="ClassEntity";
	 selectBoxes= ['class','examType','skill'];
        fnGetSelectBoxdata(selectBoxes);

	
	
});
//Default Load Record Ends
function fnClassSoftSkillpostSelectBoxMaster(){
    
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {
    
    
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       if(Institute.ClassMaster.length>0 && Institute.ExamMaster.length>0 )
      {   
      $scope.classes=Institute.ClassMaster;
      $scope.ExamMaster = Institute.ExamMaster;
      $scope.skills = Institute.SkillMaster;
      $scope.categories=Institute.CategoryMaster;
      window.parent.fn_hide_parentspinner();  
    
   if ((window.parent.ClassSoftSkillkey.class !=null && window.parent.ClassSoftSkillkey.class !='')&&(window.parent.ClassSoftSkillkey.exam !=null && window.parent.ClassSoftSkillkey.exam !='')&&(window.parent.ClassSoftSkillkey.skillID !=null && window.parent.ClassSoftSkillkey.skillID !=''))
	{
		var class1=window.parent.ClassSoftSkillkey.class;
		var exam=window.parent.ClassSoftSkillkey.exam;
                var skillID=window.parent.ClassSoftSkillkey.skillID;
		
		 window.parent.ClassSoftSkillkey.class =null;
		 window.parent.ClassSoftSkillkey.exam =null;
                 window.parent.ClassSoftSkillkey.skillID =null;
		
		fnshowSubScreen(class1,exam,skillID);
		
	}
        $scope.$apply();
}
}
}

function fnClassSoftSkillDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if (($scope.SkillTable ==null)  && parentOperation=='Create')
	{	
var emptyClassSoftSkill = {
		class: "Select option",
		exam: 'Select option',
		skillID: "",
		skillName: 'Select option',
		skills: [{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			}
		]
	};
    //Screen Specific DataModel Starts
	var dataModel = emptyClassSoftSkill;
    if($scope.class!=null)
	dataModel.class = $scope.class;
       dataModel.exam = $scope.exam;
       dataModel.skillID = $scope.skillID;
       dataModel.skillName = $scope.skillName;
     if ($scope.class == '' || $scope.class== null || $scope.class == "Select option") {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
//			 if ($scope.skillName == '' || $scope.skillName == null || $scope.skillName == "Select option") {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Skill Name']);
//				return false;
//			}
	    var response = fncallBackend('ClassSoftSkill', 'Create-Default', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
		$scope.mvwAddDeteleDisable = true; //Multiple View
	   //Multiple View Response Starts 
//		$scope.MarkTable = fnConvertmvw('MarkTable',response.body.marks);
//		$scope.MarkCurPage = 1
//		$scope.MarkShowObject = $scope.fnMvwGetCurPageTable('Mark');
		//Multiple View Response Ends 	
	    // $scope.$apply();
	 
	 }	

return true;
}


function fnshowSubScreen(class1,exam,skillID)
{
subScreen = true;
var emptyClassSoftSkill = {
		class: "Select option",
		exam: 'Select option',
		skillID: "",
		skillName: 'Select option',
		skills: [{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			}
		]
	};

    //screen Specific DataModel Starts
	var dataModel = emptyClassSoftSkill;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if(class1!==null&&class1!==null){
            
          if(exam!==null&&exam!==null){ 
              
             if(skillID!==null&&skillID!==null){ 
              
            
                  dataModel.class =class1;
                  dataModel.exam =exam;
                  dataModel.skillID =skillID;

                //Screen Specific DataModel Ends
                  var response = fncallBackend('ClassSoftSkill', 'View', dataModel,[{entityName:"class",entityValue:class1}],$scope);
	
          }
    }
        }
        return true;
}
//Cohesive Query Framework Starts
function fnClassSoftSkillQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	$scope.skillID = "";
	$scope.skillName = 'Select option';
//	$scope.marks = "";
	$scope.SkillTable=null;
	$scope.classReadonly = false;
	$scope.examtypereadOnly = false;
	$scope.skillIDReadOnly = false;
	$scope.skillNamereadOnly = false;
	$scope.markReadOnly = false;
        $scope.categoryReadOnly = false;
	$scope.teacherFeedbackReadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.mvwAddDeteleDisable = true; //Multiple View
    //Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive View Framework Starts
function fnClassSoftSkillView() {
	var emptyClassSoftSkill = {
		class: 'Select option',
		exam: 'Select option',
		skillID: "",
		skillName: 'Select option',
		skills: [{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			}
		]
	};

    //screen Specific DataModel Starts
	var dataModel = emptyClassSoftSkill;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	  if($scope.class!==null&&$scope.class!==""){
              
              if($scope.skillID!==null&&$scope.skillID!==""){
                  
                  
                  if($scope.exam!==null&&$scope.exam!==""){
                  
                    dataModel.class = $scope.class;
                    dataModel.skillID = $scope.skillID;
                    dataModel.exam = $scope.exam;
                    //Screen Specific DataModel Ends
                    var response = fncallBackend('ClassSoftSkill', 'View', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);

                  }
              }
    
    }
 //	
//	if (response.header.status == 'success') {
//		//Screen Specific Scope Starts
//		$scope.classReadonly = true;
//		$scope.examtypereadOnly = true;
//		$scope.skillIDReadOnly = true;
//		$scope.skillNamereadOnly = true;
//		$scope.markReadOnly = true;
//                $scope.categoryReadOnly = true;
//		$scope.teacherFeedbackReadOnly = true;
//		//Screen Specific Scope Ends
//		//Generic Field Starts
//		$scope.MakerRemarksReadonly = true;
//		$scope.CheckerRemarksReadonly = true;
//		$scope.mastershow = true;
//		$scope.detailshow = false;
//		$scope.auditshow = false;
//		$scope.mvwAddDeteleDisable = true; //Multiple View
//		//Generic Field Ends
//		//Screen Specific Response Scope Starts 
//                $scope.class = response.body.class;
//		$scope.skillID = response.body.skillID;
//		$scope.skillName = response.body.skillName;
//		$scope.exam = response.body.exam;
////		$scope.marks = response.body.marks;
//		//Screen Specific Response Scope Ends
//		// Multiple View Starts
//	    $scope.MarkCurPage = 0;
//	    $scope.MarkTable = null;
//	    $scope.MarkShowObject = null;
//    	// Multiple View Ends
//	    //Multiple View Response Starts 
//		$scope.MarkTable = fnConvertmvw('MarkTable',response.body.marks);
//		$scope.MarkCurPage = 1;
//		$scope.MarkShowObject = $scope.fnMvwGetCurPageTable('Mark');
//		//Multiple View Response Ends 
//		return true;
//	} else {
//		return false;
//	}
	return true;
}
//Cohesive View Framework Ends
//Cohesive Mandatory Validation Starts
function fnClassSoftSkillMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001' ['Exam']);
				return false;
			}
			if ($scope.skillID == '' || $scope.skillID == null || $scope.skillID == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001' ['SkillName']);
				return false;
			}

			break;

		case 'Save':
			if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
			if ($scope.exam == '' || $scope.exam == null || $scope.exam == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['exam']);
				return false;
			}
			if ($scope.skillID == '' || $scope.skillID == null || $scope.skillID == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['SkillName']);
				return false;
			}
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnClassSoftSkillDefaultandValidate(operation)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
switch(operation)
   {
     case 'View':   
                            
                      return true;   
			   
  			  break;
	 
	 case 'Save':   
                      return true;   
			   
  			  break;
	 
   
   }		
 return true; 		
}
//Screen Specific Default Validation Ends
//Cohesive Create Framework Starts
function fnClassSoftSkillCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts	
	$scope.class = 'Select option';
	$scope.exam = 'Select option';
	$scope.skillID = "";
	$scope.skillName = 'Select option';
//	$scope.marks = "";
        $scope.SkillTable=null;
	$scope.classReadonly = false;
	$scope.examtypereadOnly = false;
	$scope.skillNamereadOnly = false;
	$scope.markReadOnly = false;
        $scope.categoryReadOnly = false;
	$scope.teacherFeedbackReadOnly = false;
         $scope.classes=Institute.ClassMaster;
        $scope.skills = Institute.SkillMaster;
        $scope.categories=Institute.CategoryMaster;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
       $scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
         $scope.operation = 'Creation';	
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Multiple View Starts
	$scope.SkillCurPage = 0;
	$scope.SkillTable = null;
	$scope.SkillShowObject = null;
	// Multiple View Ends
	//Generic Field Ends
	return true;
}
////Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnClassSoftSkillEdit() {
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
	$scope.classReadonly = true;
	$scope.skillNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.markReadOnly = false;
        $scope.categoryReadOnly = false;
	$scope.teacherFeedbackReadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnClassSoftSkillDelete() {
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
	$scope.classReadonly = true;
	$scope.skillNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.markReadOnly = true;
        $scope.categoryReadOnly = true;
	$scope.teacherFeedbackReadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
    //Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnClassSoftSkillAuth() {
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
	$scope.classReadonly = true;
	$scope.skillNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
    //Screen Specific Scope Ends	
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnClassSoftSkillReject() {

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
	$scope.classReadonly = true;
	$scope.skillNamereadOnly = true;
	$scope.examtypereadOnly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	//screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework starts
function fnClassSoftSkillBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.class= 'Select option';
		$scope.exam= 'Select option';
		$scope.skillID= "";
		$scope.skillName= 'Select option';
//		$scope.marks= "";
                $scope.SkillTable=null;
	}
	   $scope.classReadonly = true;
	    $scope.skillNamereadOnly = true;
	    $scope.examtypereadOnly = true;
		$scope.markReadOnly = true;
                $scope.categoryReadOnly = true;
	    $scope.teacherFeedbackReadOnly = true;
		//Screen Specific Scope Ends
		// Generic Field Starts
                $scope.operation = '';
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		$scope.mastershow = true;
	        $scope.detailshow = false;
                $scope.auditshow = false;
	    //Generic Field Ends
}
//Cohesive BaackFramework Ends
//Cohesive Save Framework Starts
function fnClassSoftSkillSave() {
	var emptyClassSoftSkill = {
		class: 'Select option',
		exam: 'Select option',
		skillID: "",
		skillName: 'Select option',
		skills: [{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			},
			{
				studentID: "",
				studentName: "",
				category: "",
				teacherFeedback: ""
			}
		]
	};

	var dataModel = emptyClassSoftSkill;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if($scope.class!=null)
	dataModel.class = $scope.class;
     if($scope.exam!=null)
	dataModel.exam = $scope.exam;
     if($scope.skillName!=null)
	dataModel.skillName = $scope.skillName;
     if($scope.SkillTable!=null)
	dataModel.skills = $scope.SkillTable;
    if($scope.skillID!=null)
	dataModel.skillID = $scope.skillID;
	var response = fncallBackend('ClassSoftSkill', parentOperation, dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
	return true;
}
//Cohesive Save Framework Ends

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'SkillTable':
		   
			var SkillTable = new Array();
			 responseObject.forEach(fnConvert1);
			 function fnConvert1(value,index,array){
				     SkillTable[index] = new Object();
					 SkillTable[index].idx=index;
					 SkillTable[index].checkBox=false;
					 SkillTable[index].studentID=value.studentID;
					 SkillTable[index].studentName=value.studentName;
					 SkillTable[index].category=value.category;
					 SkillTable[index].teacherFeedback=value.teacherFeedback;
					}
			return SkillTable;
		}
	}




function fnClassSoftSkillpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                 $scope.MakerRemarksReadonly = true;
	         $scope.CheckerRemarksReadonly = true;
		$scope.classReadonly = true;
		
                $scope.categoryReadOnly = true;
		//Screen Specific Scope Ends
		// Specific Screen Scope Ends
		// Generic Field Starts
//		$scope.mastershow = true;
//		$scope.detailshow = false;
//		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
                //
                if(response.header.operation=="Create-Default"){
                 
//	         $scope.attendanceReadOnly = false;
                 $scope.mastershow = false;
	         $scope.detailshow = true;
	         $scope.auditshow = false;
	 	 $scope.markReadOnly = false;
                 $scope.categoryReadOnly = false;
		 $scope.teacherFeedbackReadOnly = false;
                 $scope.examtypereadOnly = false;
	    	 $scope.skillNamereadOnly = false;
                 
                }else{
                    
//                    $scope.attendanceReadOnly = true;
                    $scope.mastershow = true;
		    $scope.detailshow = false;
		    $scope.auditshow = false;
	            $scope.markReadOnly = true;
		    $scope.teacherFeedbackReadOnly = true;
                    $scope.examtypereadOnly = true;
	    	    $scope.skillNamereadOnly = true;
                }
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
//                $scope.marks = "";
                $scope.SkillTable=null;
		$scope.class ="";
		$scope.exam ="";
                $scope.skillID ="";
		$scope.skillName ="";
        $scope.SkillShowObject=null;
        $scope.audit = {};
		 }
                else
                {
//	        $scope.marks = response.body.marks;
		$scope.class = response.body.class;
		$scope.exam = response.body.exam;
		$scope.skillID = response.body.skillID;
//		$scope.marks = response.body.marks;
                $scope.audit=response.audit;
                $scope.GradeDescription=response.body.GradeDescription;
		//Multiple View Response Starts 
                
                if(response.body.skills!==null){
                
		$scope.SkillTable = fnConvertmvw('SkillTable',response.body.skills);
                
               }else{
                   
                   $scope.SkillTable=null;
               }
                
		$scope.SkillCurPage = 1
		$scope.SkillShowObject = $scope.fnMvwGetCurPageTable('Skill');
		//Multiple View Response Ends 
		$scope.mvwAddDeteleDisable = true; //Multiple View
            }
         if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen = false;
	 }
           
         
         return true;

}

}



