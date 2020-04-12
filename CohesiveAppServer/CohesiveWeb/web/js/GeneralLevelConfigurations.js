/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
    Author     : Munish Kumar B
	IBD Technologies
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
//var tempoperation = "operation";
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
	// Generic Field Starts
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes=OperationScopes;
    // Generic Field Ends
	// Screen Specific Scope Starts	
	$scope.instituteName = "";
	$scope.instituteID ="";
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.profileImgPath = "";
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.instituteSearchreadOnly = true;
	
        $scope.profileImgPathNamereadOnly = true;
	$scope.notificationTypereadOnly = true;
	$scope.notificationDescriptionreadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.otherLangDescriptionreadOnly=true;
	$scope.fromreadOnly = true;
	$scope.toreadOnly = true;
	$scope.gradereadOnly = true;
	$scope.examTypereadOnly = true;
	$scope.examDescriptionreadOnly = true;
	// Screen Specific Scope Ends

	// multiple View Starts
	$scope.examMasterCurPage = 0;
	$scope.examMasterTable = null;
	$scope.examMasterShowObject = null;

	$scope.gradeMasterCurPage = 0;
	$scope.gradeMasterTable = null;
	$scope.gradeMasterShowObject = null;

	$scope.subjectMasterCurPage = 0;
	$scope.subjectMasterTable = null;
	$scope.subjectMasterShowObject = null;

	$scope.notificationMasterCurPage = 0;
	$scope.notificationMasterTable = null;
	$scope.notificationMasterShowObject = null;

	$scope.feeMasterCurPage = 0;
	$scope.feeMasterTable = null;
	$scope.feeMasterShowObject = null;

	// multiple View Ends
	// Scope Level Multiple View Functions Starts
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'examMaster') {
			if ($scope.examMasterTable != null && $scope.examMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.examMasterCurPage;
				lsvwObject.tableObject = $scope.examMasterTable;
				lsvwObject.screenShowObject = $scope.examMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.examMasterCurPage = lsvwObject.curPage;
				$scope.examMasterTable = lsvwObject.tableObject;
				$scope.examMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'gradeMaster') {
			if ($scope.gradeMasterTable != null && $scope.gradeMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'subjectMaster') {
			if ($scope.subjectMasterTable != null && $scope.subjectMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.subjectMasterCurPage;
				lsvwObject.tableObject = $scope.subjectMasterTable;
				lsvwObject.screenShowObject = $scope.subjectMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.subjectMasterCurPage = lsvwObject.curPage;
				$scope.subjectMasterTable = lsvwObject.tableObject;
				$scope.subjectMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'notificationMaster') {
			if ($scope.notificationMasterTable != null && $scope.notificationMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.notificationMasterCurPage;
				lsvwObject.tableObject = $scope.notificationMasterTable;
				lsvwObject.screenShowObject = $scope.notificationMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.notificationMasterCurPage = lsvwObject.curPage;
				$scope.notificationMasterTable = lsvwObject.tableObject;
				$scope.notificationMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'feeMaster') {
			if ($scope.feeMasterTable != null && $scope.feeMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.feeMasterCurPage;
				lsvwObject.tableObject = $scope.feeMasterTable;
				lsvwObject.screenShowObject = $scope.feeShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.feeMasterCurPage = lsvwObject.curPage;
				$scope.feeTable = lsvwObject.tableObject;
				$scope.feeShowObject = lsvwObject.screenShowObject;
			}
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'examMaster') {
			if ($scope.examMasterTable != null && $scope.examMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.examMasterCurPage;
				lsvwObject.tableObject = $scope.examMasterTable;
				lsvwObject.screenShowObject = $scope.examMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.examMasterCurPage = lsvwObject.curPage;
				$scope.examMasterTable = lsvwObject.tableObject;
				$scope.examMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'gradeMaster') {
			if ($scope.gradeMasterTable != null && $scope.gradeMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'subjectMaster') {
			if ($scope.subjectMasterTable != null && $scope.subjectMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.subjectMasterCurPage;
				lsvwObject.tableObject = $scope.subjectMasterTable;
				lsvwObject.screenShowObject = $scope.subjectMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.subjectMasterCurPage = lsvwObject.curPage;
				$scope.subjectMasterTable = lsvwObject.tableObject;
				$scope.subjectMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'notificationMaster') {
			if ($scope.notificationMasterTable != null && $scope.notificationMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.notificationMasterCurPage;
				lsvwObject.tableObject = $scope.notificationMasterTable;
				lsvwObject.screenShowObject = $scope.notificationMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.notificationMasterCurPage = lsvwObject.curPage;
				$scope.notificationMasterTable = lsvwObject.tableObject;
				$scope.notificationMasterShowObject = lsvwObject.screenShowObject;
			}
		} else if (tableName == 'feeMaster') {
			if ($scope.feeMasterTable != null && $scope.feeMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.feeMasterCurPage;
				lsvwObject.tableObject = $scope.feeMasterTable;
				lsvwObject.screenShowObject = $scope.feeShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.feeMasterCurPage = lsvwObject.curPage;
				$scope.feeMasterTable = lsvwObject.tableObject;
				$scope.feeMasterShowObject = lsvwObject.screenShowObject;
			}
		}

	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'examMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					examType: "",
                                        otherLangDescription:"",
					description: ""
				};
				if ($scope.examMasterTable == null)
					$scope.examMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.examMasterCurPage;
				lsvwObject.tableObject = $scope.examMasterTable;
				lsvwObject.screenShowObject = $scope.examMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.examMasterCurPage = lsvwObject.curPage;
				$scope.examMasterTable = lsvwObject.tableObject;
				$scope.examMasterShowObject = lsvwObject.screenShowObject;

			} else if (tableName == 'gradeMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					from: "",
					to: "",
					grade: ""
				};
				if ($scope.gradeMasterTable == null)
					$scope.gradeMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;

			} else if (tableName == 'subjectMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					subjectID: "",
                                        otherLangDescription:"",
					subjectName: "",
				};
				if ($scope.subjectMasterTable == null)
					$scope.subjectMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.subjectMasterCurPage;
				lsvwObject.tableObject = $scope.subjectMasterTable;
				lsvwObject.screenShowObject = $scope.subjectMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.subjectMasterCurPage = lsvwObject.curPage;
				$scope.subjectMasterTable = lsvwObject.tableObject;
				$scope.subjectMasterShowObject = lsvwObject.screenShowObject;

			} else if (tableName == 'notificationMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					notificationType: "",
                                        otherLangDescription:"",
					description: ""
				};
				if ($scope.notificationMasterTable == null)
					$scope.notificationMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.notificationMasterCurPage;
				lsvwObject.tableObject = $scope.notificationMasterTable;
				lsvwObject.screenShowObject = $scope.notificationMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.notificationMasterCurPage = lsvwObject.curPage;
				$scope.notificationMasterTable = lsvwObject.tableObject;
				$scope.notificationMasterShowObject = lsvwObject.screenShowObject;

			} else if (tableName == 'feeMaster') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					feeType: "",
                                        otherLangDescription:"",
					feeDescription: ""
				};
				if ($scope.feeMasterTable == null)
					$scope.feeMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.feeMasterCurPage;
				lsvwObject.tableObject = $scope.feeMasterTable;
				lsvwObject.screenShowObject = $scope.feeMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.feeMasterCurPage = lsvwObject.curPage;
				$scope.feeMasterTable = lsvwObject.tableObject;
				$scope.feeMasterShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'examMaster') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.examMasterCurPage;
				lsvwObject.tableObject = $scope.examMasterTable;
				lsvwObject.screenShowObject = $scope.examMasterShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.examMasterCurPage = lsvwObject.curPage;
				$scope.examMasterTable = lsvwObject.tableObject;
				$scope.examMasterShowObject = lsvwObject.screenShowObject;
			} else if (tableName == 'gradeMaster') {

				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.gradeMasterCurPage;
				lsvwObject.tableObject = $scope.gradeMasterTable;
				lsvwObject.screenShowObject = $scope.gradeMasterShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.gradeMasterCurPage = lsvwObject.curPage;
				$scope.gradeMasterTable = lsvwObject.tableObject;
				$scope.gradeMasterShowObject = lsvwObject.screenShowObject;
			} else if (tableName == 'subjectMaster') {

				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.subjectMasterCurPage;
				lsvwObject.tableObject = $scope.subjectMasterTable;
				lsvwObject.screenShowObject = $scope.subjectMasterShowObject;

				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.subjectMasterCurPage = lsvwObject.curPage;
				$scope.subjectMasterTable = lsvwObject.tableObject;
				$scope.subjectMasterShowObject = lsvwObject.screenShowObject;
			} else if (tableName == 'notificationMaster') {

				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.notificationMasterCurPage;
				lsvwObject.tableObject = $scope.notificationMasterTable;
				lsvwObject.screenShowObject = $scope.notificationMasterShowObject;

				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.notificationMasterCurPage = lsvwObject.curPage;
				$scope.notificationMasterTable = lsvwObject.tableObject;
				$scope.notificationMasterShowObject = lsvwObject.screenShowObject;
			} else if (tableName == 'feeMaster') {

				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.feeMasterCurPage;
				lsvwObject.tableObject = $scope.feeMasterTable;
				lsvwObject.screenShowObject = $scope.feeMasterShowObject;

				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.feeMasterCurPage = lsvwObject.curPage;
				$scope.feeMasterTable = lsvwObject.tableObject;
				$scope.feeMasterShowObject = lsvwObject.screenShowObject;
			}


		}

	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'examMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.examMasterCurPage;
			lsvwObject.tableObject = $scope.examMasterTable;
			lsvwObject.screenShowObject = $scope.examMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		} else if (tableName == 'gradeMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.gradeMasterCurPage;
			lsvwObject.tableObject = $scope.gradeMasterTable;
			lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);

		} else if (tableName == 'subjectMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.subjectMasterCurPage;
			lsvwObject.tableObject = $scope.subjectMasterTable;
			lsvwObject.screenShowObject = $scope.subjectMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);

		} else if (tableName == 'notificationMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.notificationMasterCurPage;
			lsvwObject.tableObject = $scope.notificationMasterTable;
			lsvwObject.screenShowObject = $scope.notificationMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		} else if (tableName == 'feeMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.feeMasterCurPage;
			lsvwObject.tableObject = $scope.feeMasterTable;
			lsvwObject.screenShowObject = $scope.feeMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}


	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'examMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.examMasterCurPage;
			lsvwObject.tableObject = $scope.examMasterTable;
			lsvwObject.screenShowObject = $scope.examMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		} else if (tableName == 'gradeMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.gradeMasterCurPage;
			lsvwObject.tableObject = $scope.gradeMasterTable;
			lsvwObject.screenShowObject = $scope.gradeMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		} else if (tableName == 'subjectMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.subjectMasterCurPage;
			lsvwObject.tableObject = $scope.subjectMasterTable;
			lsvwObject.screenShowObject = $scope.subjectMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		} else if (tableName == 'notificationMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.notificationMasterCurPage;
			lsvwObject.tableObject = $scope.notificationMasterTable;
			lsvwObject.screenShowObject = $scope.notificationMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		} else if (tableName == 'feeMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.feeMasterCurPage;
			lsvwObject.tableObject = $scope.feeMasterTable;
			lsvwObject.screenShowObject = $scope.feeMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}

	};

	$scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'examMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.examMasterTable);

		} else if (tableName == 'gradeMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.gradeMasterTable);

		}
		else if (tableName == 'subjectMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.subjectMasterTable);

		}
		else if (tableName == 'notificationMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.notificationMasterTable);

		}
		else if (tableName == 'feeMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.feeMasterTable);

		}
	};
	//Single View Scope Function ends 
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


});
//------------------- Scope Level Function Ends-------------------------------------------------------------------------------------------

//-------This is to Load default record Starts--------------------------------------
$(document).ready(function () {

MenuName = "GeneralLevelConfigurations";
        
window.parent.nokotser=$("#nokotser").val();

window.parent.Entity="Institute";
//fn_hide_parentspinner();
window.parent.fn_hide_parentspinner();



if(/Android/.test(navigator.appVersion)) {

   window.addEventListener("resize", function() {

     if(document.activeElement.tagName=="INPUT" || document.activeElement.tagName=="TEXTAREA") {

       document.activeElement.scrollIntoView();

     }

  })

}



$("#profileImg").change(function(){
   
   $("#imgUpld").submit();
   
});

$('#imgUpld').submit(function(event){
   //var src={fileName:"",uploadID:""};
   $("#logo").attr("src","");
  fileUpload('#imgUpld',$('#imgUpld')[0],$('#imgUpld').attr('action'),"GeneralLevelConfiguration");
  event.preventDefault();
    return true;
});



});
//-------This is to Load default record Ends--------------------------------------

function fnPostImageUpload(id,fileName,UploadID)
{
    
 $("#logo").attr("src","/CohesiveUpload/images/"+UploadID+"/"+fileName);
   
       var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
       $scope.profileImgPath="/CohesiveUpload/images/"+UploadID+"/"+fileName;	
       $scope.$apply();
   
}


// Cohesive Query Framework Starts
function fnGeneralLevelConfigurationsQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Specific Scope Screen Starts
	$scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.profileImgPath = "";
	$scope.NotificationMaster = null;
	$scope.FeeTypeMaster = null;
	$scope.GradeMaster = null;
	$scope.ExamMaster = null;
	$scope.SubjectMaster = null;
        $("#logo").attr("src","");
        $("#profileImg").val(""); 
	$scope.instituteIDreadOnly = false;//Integration changes
	$scope.instituteNamereadOnly = false;
        $scope.instituteSearchreadOnly = false;
        
	$scope.notificationTypereadOnly = true;
	$scope.notificationDescriptionreadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.otherLangDescriptionreadOnly=true;
	$scope.fromreadOnly = true;
	$scope.toreadOnly = true;
	$scope.gradereadOnly = true;
	$scope.examTypereadOnly = true;
	$scope.examDescriptionreadOnly = true;
	$scope.profileImgPathNamereadOnly = true;
	// Specific Scope Screen Ends

	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.audit = {};
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	// Generic Field Ends	
	// multiple View Starts
	$scope.examMasterCurPage = 0;
	$scope.examMasterTable = null;
	$scope.examMasterShowObject = null;

	$scope.gradeMasterCurPage = 0;
	$scope.gradeMasterTable = null;
	$scope.gradeMasterShowObject = null;

	$scope.subjectMasterCurPage = 0;
	$scope.subjectMasterTable = null;
	$scope.subjectMasterShowObject = null;

	$scope.notificationMasterCurPage = 0;
	$scope.notificationMasterTable = null;
	$scope.notificationMasterShowObject = null;
	
	$scope.feeMasterCurPage = 0;
	$scope.feeMasterTable = null;
	$scope.feeMasterShowObject = null;

	// multiple View Ends

	return true;
}
// Cohesive Query Framework Ends

// Cohesive View Framework Starts
function fnGeneralLevelConfigurationsView() {
	var emptyGeneralLevelConfigurations = {
		instituteName: "",
		instituteID: "",
		profileImgPath: "",
		SubjectMaster: [{
			idx: 0,
			checkBox: false,
			subjectID: "",
			subjectName: "",
                        otherLangDescription:"",
		}],
		GradeMaster: [{
			idx: 0,
			checkBox: false,
			from: "",
			to: "",
			grade: ""
                        
		}],
		ExamMaster: [{
			idx: 0,
			checkBox: false,
			examType: "",
                        otherLangDescription:"",
			description: ""
		}],
		NotificationMaster: [{
			idx: 0,
			checkBox: false,
			notificationType: "",
                        otherLangDescription:"",
			description: ""
		}],
		FeeTypeMaster: [{
			idx: 0,
			checkBox: false,
			feeType: "",
                        otherLangDescription:"",
			feeDescription: ""
		}]
		/*audit: {},
		error: {}*/
	};
	// Specific Screen DataModel Starts
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel = emptyGeneralLevelConfigurations;
	dataModel.instituteID = $scope.instituteID;
	//dataModel.instituteName = $scope.instituteName;
	//dataModel.profileImgPath = $scope.profileImgPath;
	//dataModel.NotificationMaster = $scope.notificationMasterTable;
	//dataModel.FeeTypeMaster = $scope.feeMasterTable;
	//dataModel.ExamMaster = $scope.examMasterTable;
	//dataModel.GradeMaster = $scope.gradeMasterTable;
	//dataModel.SubjectMaster = $scope.SubjectMaster;
	//dataModel.audit = $scope.audit; //Integration changes
	// Specific Screen DataModel Ends

	var response = fncallBackend('GeneralLevelConfiguration', 'View', dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	/*if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.profileImgPathreadOnly = true;
		$scope.notificationTypereadOnly = true;
		$scope.notificationDescriptionreadOnly = true;
		$scope.subjectIDreadOnly = true;
		$scope.subjectNamereadOnly = true;
		$scope.feeTypereadOnly = true;
		$scope.feeDescriptionreadOnly = true;
		$scope.fromreadOnly = true;
		$scope.toreadOnly = true;
		$scope.gradereadOnly = true;
		$scope.examTypereadOnly = true;
		$scope.examDescriptionreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		$scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.profileImgPath = response.body.profileImgPath;
		$scope.NotificationMaster = response.body.NotificationMaster;
		$scope.FeeTypeMaster = response.body.FeeTypeMaster;
		$scope.GradeMaster = response.body.GradeMaster;
		$scope.SubjectMaster = response.body.SubjectMaster;
		$scope.ExamMaster = response.body.ExamMaster;
		$scope.audit = response.body.audit;
		// Specific Screen Scope Response Ends 

		//Multiple View Response Starts 
		$scope.examMasterTable = fnConvertmvw('examMasterTable',response.body.ExamMaster);
		$scope.examMasterCurPage = 1;
		$scope.examMasterShowObject = $scope.fnMvwGetCurPageTable('examMaster');

		$scope.gradeMasterTable = fnConvertmvw('gradeMasterTable',response.body.GradeMaster);
		$scope.gradeMasterCurPage = 1;
		$scope.gradeMasterShowObject = $scope.fnMvwGetCurPageTable('gradeMaster');

		$scope.subjectMasterTable = fnConvertmvw('subjectMasterTable',response.body.SubjectMaster);
		$scope.subjectMasterCurPage = 1;
		$scope.subjectMasterShowObject = $scope.fnMvwGetCurPageTable('subjectMaster');

		$scope.notificationMasterTable = fnConvertmvw('notificationMasterTable',response.body.NotificationMaster);
		$scope.notificationMasterCurPage = 1;
		$scope.notificationMasterShowObject = $scope.fnMvwGetCurPageTable('notificationMaster');

		$scope.feeMasterTable = fnConvertmvw('feeMasterTable',response.body.FeeTypeMaster);
		$scope.feeMasterCurPage = 1;
		$scope.feeMasterShowObject = $scope.fnMvwGetCurPageTable('feeMaster');

		//Multiple View Response Ends 

		return true;
	} else {
		return false;
	}*/
	return true;
}
// Cohesive View Framework Ends
// Cohesive Mandatory Validation Starts   
function fnGeneralLevelConfigurationsMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}
			break;

		case 'Save':
			if ($scope.instituteName == '' || $scope.instituteName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
				return false;
			}
                        if ($scope.instituteID == '' || $scope.instituteID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
				return false;
			}

			if ($scope.examMasterTable == null || $scope.examMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam Master']);
				return false;
			}
			for (i = 0; i < $scope.examMasterTable.length; i++) {
				if ($scope.examMasterTable[i].examType == '' || $scope.examMasterTable[i].examType == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Exam Type in Exam Tab ' + 'record ' + (i + 1)]);
					return false;
				}

				if ($scope.examMasterTable[i].description == '' || $scope.examMasterTable[i].description == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Exam Description in Exam Tab' + 'record ' + (i + 1)]);
					return false;
				}
			}


			if ($scope.gradeMasterTable == null || $scope.gradeMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Grade Master']);
				return false;
			}
			for (i = 0; i < $scope.gradeMasterTable.length; i++) {
				if ($scope.gradeMasterTable[i].from == '' || $scope.gradeMasterTable[i].from == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['From Mark ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.gradeMasterTable[i].to == '' || $scope.gradeMasterTable[i].to == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['To Mark ' + 'record ' + (i + 1)]);
					return false;
				}

				if ($scope.gradeMasterTable[i].grade == '' || $scope.gradeMasterTable[i].grade == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Grade ' + 'record ' + (i + 1)]);
					return false;
				}
			}


			if ($scope.subjectMasterTable == null || $scope.subjectMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Master']);
				return false;
			}
			for (i = 0; i < $scope.subjectMasterTable.length; i++) {
				if ($scope.subjectMasterTable[i].subjectID == '' || $scope.subjectMasterTable[i].subjectID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Subject ID' + 'record ' + (i + 1)]);
					return false;
				}

				if ($scope.subjectMasterTable[i].subjectName == '' || $scope.subjectMasterTable[i].subjectName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Subject Name' + 'record ' + (i + 1)]);
					return false;
				}
			}

			if ($scope.notificationMasterTable == null || $scope.notificationMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Notification  Master']);
				return false;
			}
			for (i = 0; i < $scope.notificationMasterTable.length; i++) {
				if ($scope.notificationMasterTable[i].notificationType == '' || $scope.notificationMasterTable[i].notificationType == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Noti0fication Type ' + 'record ' + (i + 1)]);
					return false;
				}

				if ($scope.notificationMasterTable[i].description == '' || $scope.notificationMasterTable[i].description == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Notifcation Description ' + 'record ' + (i + 1)]);
					return false;
				}
			}


			if ($scope.feeMasterTable == null || $scope.feeMasterTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Master']);
				return false;
			}
			for (i = 0; i < $scope.feeMasterTable.length; i++) {
				if ($scope.feeMasterTable[i].feeType == '' || $scope.feeMasterTable[i].feeType == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Type ' + 'record ' + (i + 1)]);
					return false;
				}

				if ($scope.feeMasterTable[i].feeDescription == '' || $scope.feeMasterTable[i].feeDescription == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Fee Description ' + 'record ' + (i + 1)]);
					return false;
				}
			}

			break;
	}
	return true;
}
// Screen Specific Mandatory Validation Ends
//Default Validation Starts
function fnGeneralLevelConfigurationsDefaultandValidate(operation) {
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
	/*angular.forEach(Institute.InstituteMaster, function (value, key) {
		if (this[key].InstituteName == $scope.instituteName) {
			$scope.instituteID = this[key].InstituteId;
			availabilty = true;
			return true;
		}
	}, Institute.InstituteMaster);
	if (!availabilty) {
		fn_Show_Exception_With_Param('FE-VAL-002', ['Institute Name']);
		return false;
	}*/
	return true;
}
//Default Validation Ends
// Cohesive Create Framework Starts
function fnGeneralLevelConfigurationsCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts  
	$scope.instituteID = "";
	$scope.instituteName = "";
	$scope.profileImgPath = "";
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = false;
        $scope.instituteSearchreadOnly = true;
        
	$scope.notificationTypereadOnly = false;
	$scope.notificationDescriptionreadOnly = false;
	$scope.subjectIDreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.feeTypereadOnly = false;
	$scope.feeDescriptionreadOnly = false;
        $scope.otherLangDescriptionreadOnly=false;
	$scope.fromreadOnly = false;
	$scope.toreadOnly = false;
	$scope.gradereadOnly = false;
	$scope.examTypereadOnly = false;
	$scope.examDescriptionreadOnly = false;
	$scope.profileImgPathNamereadOnly = false;
        $("#logo").attr("src","");
        $("#profileImg").val(""); 
	// Screen Specific Scope Ends

	// Generic Field Starts	
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	// Generic Field End

	// multiple View Starts
	$scope.examMasterCurPage = 0;
	$scope.examMasterTable = null;
	$scope.examMasterShowObject = null;

	$scope.gradeMasterCurPage = 0;
	$scope.gradeMasterTable = null;
	$scope.gradeMasterShowObject = null;


	$scope.subjectMasterCurPage = 0;
	$scope.subjectMasterTable = null;
	$scope.subjectMasterShowObject = null;

	$scope.notificationMasterCurPage = 0;
	$scope.notificationMasterTable = null;
	$scope.notificationMasterShowObject = null;

	$scope.feeMasterCurPage = 0;
	$scope.feeMasterTable = null;
	$scope.feeMasterShowObject = null;
        
        var emptyGeneralLevelConfigurations = {
		instituteName: "",
		instituteID: "",
		profileImgPath: "",
		SubjectMaster: [{
			idx: 0,
			checkBox: false,
			subjectID: "",
                        otherLangDescription:"",
			subjectName: "",
		}],
		GradeMaster: [{
			idx: 0,
			checkBox: false,
			from: "",
			to: "",
			grade: ""
		}],
		ExamMaster: [{
			idx: 0,
			checkBox: false,
			examType: "",
                        otherLangDescription:"",
			description: ""
		}],
		NotificationMaster: [{
			idx: 0,
			checkBox: false,
			notificationType: "",
                        otherLangDescription:"",
			description: ""
		}],
		FeeTypeMaster: [{
			idx: 0,
			checkBox: false,
			feeType: "",
                        otherLangDescription:"",
			feeDescription: ""
		}]
		/*audit: {},
		error: {}*/ 
	};
	// Screen Specific Datamodel Scope  Starts
	var dataModel = emptyGeneralLevelConfigurations;
	
var response = fncallBackend('GeneralLevelConfiguration', 'Create-Default', dataModel, [{entityName:"instituteID",entityValue:""}], $scope);
	
	// multiple View Ends
	return true;
}
// Cohesive Create Framework Ends

// Cohesive Edit Framework Starts
function fnGeneralLevelConfigurationsEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Framework Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	// Generic Framework Ends	
	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = false;
	$scope.instituteSearchreadOnly = true;
	$scope.notificationTypereadOnly = false;
	$scope.notificationDescriptionreadOnly = false;
	$scope.subjectIDreadOnly = false;
	$scope.subjectNamereadOnly = false;
	$scope.feeTypereadOnly = false;
	$scope.feeDescriptionreadOnly = false;
        $scope.otherLangDescriptionreadOnly=false;
	$scope.fromreadOnly = false;
	$scope.toreadOnly = false;
	$scope.gradereadOnly = false;
	$scope.examTypereadOnly = false;
	$scope.examDescriptionreadOnly = false;
        $scope.profileImgPathNamereadOnly = false;
	// Screen Specific Scope Endss

	return true;
}
// Cohesive Edit Framework Ends

// Cohesive Delete Framework Starts
function fnGeneralLevelConfigurationsDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.mvwAddDeteleDisable = false; //Multiple View
	// Generic Field Ends

	// Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
        
	$scope.notificationTypereadOnly = true;
	$scope.notificationDescriptionreadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.otherLangDescriptionreadOnly=true;
	$scope.fromreadOnly = true;
	$scope.toreadOnly = true;
	$scope.gradereadOnly = true;
	$scope.examTypereadOnly = true;
	$scope.examDescriptionreadOnly = true;
	// Screen Specific Scope Ends

	return true;
}
// Cohesive Delete Framework Ends

// Cohesive Authorization Framework Starts
function fnGeneralLevelConfigurationsAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	// Generic Field Starts
	$scope.operation = 'Authorisation';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	//Screen Specific Scope Starts
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
        
	$scope.notificationTypereadOnly = true;
	$scope.notificationDescriptionreadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.otherLangDescriptionreadOnly=true;
	$scope.fromreadOnly = true;
	$scope.toreadOnly = true;
	$scope.gradereadOnly = true;
	$scope.examTypereadOnly = true;
	$scope.examDescriptionreadOnly = true;
	//Screen Specific Scope Ends
return true;//Integration changes
}
// Cohesive Authorization Framework Ends

// Cohesive Reject Framework Starts
function fnGeneralLevelConfigurationsReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	$scope.operation = 'Rejection';
	// Generic field Ends
	// Screen Specific Scope Starts	
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
        
	$scope.notificationTypereadOnly = true;
	$scope.notificationDescriptionreadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.otherLangDescriptionreadOnly=true;
	$scope.fromreadOnly = true;
	$scope.toreadOnly = true;
	$scope.gradereadOnly = true;
	$scope.examTypereadOnly = true;
	$scope.examDescriptionreadOnly = true;
	// screen Specific Scope Ends
	return true;
}
// Cohesive Reject Framework Ends
// Cohesive Back Framework Starts
function fnGeneralLevelConfigurationsBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		// Screen Specific Scope Starts
		$scope.audit = {};
		$scope.instituteName = "";
		$scope.instituteID = "";
		$scope.profileImgPath = "";
		$("#logo").attr("src","");
                $("#profileImg").val(""); 
	
                $scope.examMasterTable = null;
		$scope.gradeMasterTable = null;
		$scope.feeMasterTable = null;
		$scope.subjectMasterTable = null;
		$scope.notificationMasterTable = null;
		$scope.examMasterShowObject = null;
		$scope.gradeMasterShowObject = null;
		$scope.subjectMasterShowObject = null;
		$scope.feeMasterShowObject = null;
		$scope.notificationMasterShowObject = null;
	}
	$scope.instituteIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteSearchreadOnly = true;
        
	$scope.notificationTypereadOnly = true;
	$scope.notificationDescriptionreadOnly = true;
	$scope.subjectIDreadOnly = true;
	$scope.subjectNamereadOnly = true;
	$scope.feeTypereadOnly = true;
	$scope.feeDescriptionreadOnly = true;
        $scope.otherLangDescriptionreadOnly=true;
	$scope.fromreadOnly = true;
	$scope.toreadOnly = true;
	$scope.gradereadOnly = true;
	$scope.examTypereadOnly = true;
	$scope.examDescriptionreadOnly = true;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
        $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	// Generic Field Ends
}
// Cohesive Back Framework Ends

// Cohesive Save Framework Starts
function fnGeneralLevelConfigurationsSave() {
	var emptyGeneralLevelConfigurations = {
		instituteName: "",
		instituteID: "",
		profileImgPath: "",
		SubjectMaster: [{
			idx: 0,
			checkBox: false,
			subjectID: "",
                        otherLangDescription:"",
			subjectName: "",
		}],
		GradeMaster: [{
			idx: 0,
			checkBox: false,
			from: "",
			to: "",
			grade: ""
		}],
		ExamMaster: [{
			idx: 0,
			checkBox: false,
			examType: "",
                        otherLangDescription:"",
			description: ""
		}],
		NotificationMaster: [{
			idx: 0,
			checkBox: false,
			notificationType: "",
                        otherLangDescription:"",
			description: ""
		}],
		FeeTypeMaster: [{
			idx: 0,
			checkBox: false,
			feeType: "",
                        otherLangDescription:"",
			feeDescription: ""
		}]
		/*audit: {},
		error: {}*/ 
	};
	// Screen Specific Datamodel Scope  Starts
	var dataModel = emptyGeneralLevelConfigurations;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
        dataModel.instituteName = $scope.instituteName;
	dataModel.instituteID = $scope.instituteID;
        dataModel.profileImgPath=$scope.profileImgPath;
        
	dataModel.NotificationMaster = $scope.notificationMasterTable;
	dataModel.FeeTypeMaster = $scope.feeMasterTable;
	dataModel.ExamMaster = $scope.examMasterTable;
	dataModel.SubjectMaster = $scope.subjectMasterTable;
	dataModel.GradeMaster = $scope.gradeMasterTable;
	//dataModel.audit = $scope.audit;
	// Screen Specific Datamodel Scope  Ends
	var response = fncallBackend('GeneralLevelConfiguration', parentOperation, dataModel, [{entityName:"instituteID",entityValue:$scope.instituteID}], $scope);
	/*if (response.header.status == 'success') {
		// Screen Specific Scope Starts
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.profileImgPathNamereadOnly = true;
		$scope.notificationTypereadOnly = true;
		$scope.notificationDescriptionreadOnly = true;
		$scope.subjectIDreadOnly = true;
		$scope.subjectNamereadOnly = true;
		$scope.feeTypereadOnly = true;
		$scope.feeDescriptionreadOnly = true;
		$scope.fromreadOnly = true;
		$scope.toreadOnly = true;
		$scope.gradereadOnly = true;
		$scope.examDescriptionreadOnly = true;
		$scope.examTypereadOnly = true;
		// Screen Specific Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.operation = '';
		$scope.MakerRemarksReadonly = true;
		$scope.CheckerRemarksReadonly = true;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Screen Specific Response Scope Starts
		$scope.instituteName = response.body.instituteName;
		$scope.instituteID = response.body.instituteID;
		$scope.profileImgPath = response.body.profileImgPath;
		$scope.audit = response.body.audit;
		// Screen Specific Response Scope Ends
		//Multiple View Response Starts 
		$scope.examMasterTable = fnConvertmvw('examMasterTable',response.body.ExamMaster);
		$scope.examMasterCurPage = 1;
		$scope.examMasterShowObject = $scope.fnMvwGetCurPageTable('examMaster');

		$scope.gradeMasterTable = fnConvertmvw('gradeMasterTable',response.body.GradeMaster);
		$scope.gradeMasterCurPage = 1;
		$scope.gradeMasterShowObject = $scope.fnMvwGetCurPageTable('gradeMaster');

		$scope.subjectMasterTable = fnConvertmvw('subjectMasterTable',response.body.SubjectMaster);
		$scope.subjectMasterCurPage = 1;
		$scope.subjectMasterShowObject = $scope.fnMvwGetCurPageTable('subjectMaster');

		$scope.notificationMasterTable = fnConvertmvw('notificationMasterTable',response.body.NotificationMaster);
		$scope.notificationMasterCurPage = 1;
		$scope.notificationMasterShowObject = $scope.fnMvwGetCurPageTable('notificationMaster');

		$scope.feeMasterTable = fnConvertmvw('feeMasterTable',response.body.FeeTypeMaster);
		$scope.feeMasterCurPage = 1;
		$scope.feeMasterShowObject = $scope.fnMvwGetCurPageTable('feeMaster');

		//Multiple View Response Ends 

		return true;

	} else {
		return false;
	}*/
	return true;
}

// Cohesive Save Framework Ends  


function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'examMasterTable':
		   
			var examMasterTable = new Array();
			 responseObject.forEach(fnConvert1);
			 
			 
			 function fnConvert1(value,index,array){
				     examMasterTable[index] = new Object();
					 examMasterTable[index].idx=index;
					 examMasterTable[index].checkBox=false;
					 examMasterTable[index].examType=value.examType;
					 examMasterTable[index].otherLangDescription=value.otherLangDescription;
                                          examMasterTable[index].description=value.description;
					}
			return examMasterTable;
			break ;
			case 'gradeMasterTable':
		   
			var gradeMasterTable = new Array();
			 responseObject.forEach(fnConvert2);
			 function fnConvert2(value,index,array){
				     gradeMasterTable[index] = new Object();
					 gradeMasterTable[index].idx=index;
					 gradeMasterTable[index].checkBox=false;
					 gradeMasterTable[index].from=value.from;
					 gradeMasterTable[index].to=value.to;
					 gradeMasterTable[index].grade=value.grade;
					}
			return gradeMasterTable;
			break ;
			case 'subjectMasterTable':
		   
			var subjectMasterTable = new Array();
			 responseObject.forEach(fnConvert3);
			 function fnConvert3(value,index,array){
				     subjectMasterTable[index] = new Object();
					 subjectMasterTable[index].idx=index;
					 subjectMasterTable[index].checkBox=false;
					 subjectMasterTable[index].subjectID=value.subjectID;
					 subjectMasterTable[index].subjectName=value.subjectName;
                                         subjectMasterTable[index].otherLangDescription=value.otherLangDescription;
					}
			return subjectMasterTable;
			break ;
			case 'notificationMasterTable':
		   
			var notificationMasterTable = new Array();
			 responseObject.forEach(fnConvert4);
			 function fnConvert4(value,index,array){
				     notificationMasterTable[index] = new Object();
					 notificationMasterTable[index].idx=index;
					 notificationMasterTable[index].checkBox=false;
					 notificationMasterTable[index].notificationType=value.notificationType;
					 notificationMasterTable[index].description=value.description;
                                         notificationMasterTable[index].otherLangDescription=value.otherLangDescription;
					}
			return notificationMasterTable;
			break ;
			case 'feeMasterTable':
		   
			var feeMasterTable = new Array();
			 responseObject.forEach(fnConvert5);
			 function fnConvert5(value,index,array){
				     feeMasterTable[index] = new Object();
					 feeMasterTable[index].idx=index;
					 feeMasterTable[index].checkBox=false;
					 feeMasterTable[index].feeType=value.feeType;
					 feeMasterTable[index].feeDescription=value.feeDescription;
                                         feeMasterTable[index].otherLangDescription=value.otherLangDescription;
					}
			return feeMasterTable;
			break ;
		}
	}

function fnGeneralLevelConfigurationspostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	
	
	
        if (response.header.status == 'success') {
			
		if(response.header.operation == 'Create-Default')
		
		{
		  $scope.instituteID = response.body.instituteID;
		  return true;
		}
		else
		{	
			
		$scope.instituteIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
                $scope.instituteSearchreadOnly = true;
                
		$scope.profileImgPathreadOnly = true;
		$scope.notificationTypereadOnly = true;
		$scope.notificationDescriptionreadOnly = true;
		$scope.subjectIDreadOnly = true;
		$scope.subjectNamereadOnly = true;
		$scope.feeTypereadOnly = true;
		$scope.feeDescriptionreadOnly = true;
                $scope.otherLangDescriptionreadOnly=true;
		$scope.fromreadOnly = true;
		$scope.toreadOnly = true;
		$scope.gradereadOnly = true;
		$scope.examTypereadOnly = true;
		$scope.examDescriptionreadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.instituteID = "";
		$scope.instituteName ="";
		$scope.profileImgPath ="";
		$scope.NotificationMaster ={};
		$scope.FeeTypeMaster ={};
		$scope.GradeMaster = {};
		$scope.SubjectMaster ={};
		$scope.ExamMaster = {};
                $scope.gradeMasterShowObject=null;
                $scope.notificationMasterShowObject=null;
                $scope.subjectMasterShowObject=null;
                $scope.examMasterShowObject=null;
                $scope.feeMasterShowObject=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.instituteID = response.body.instituteID;
		$scope.instituteName = response.body.instituteName;
		$scope.profileImgPath = response.body.profileImgPath;
		$scope.NotificationMaster = response.body.NotificationMaster;
		$scope.FeeTypeMaster = response.body.FeeTypeMaster;
		$scope.GradeMaster = response.body.GradeMaster;
		$scope.SubjectMaster = response.body.SubjectMaster;
		$scope.ExamMaster = response.body.ExamMaster;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
                     
		//Multiple View Response Starts 
		$scope.examMasterTable = fnConvertmvw('examMasterTable',response.body.ExamMaster);
		$scope.examMasterCurPage = 1;
		$scope.examMasterShowObject = $scope.fnMvwGetCurPageTable('examMaster');

		$scope.gradeMasterTable = fnConvertmvw('gradeMasterTable',response.body.GradeMaster);
		$scope.gradeMasterCurPage = 1;
		$scope.gradeMasterShowObject = $scope.fnMvwGetCurPageTable('gradeMaster');

		$scope.subjectMasterTable = fnConvertmvw('subjectMasterTable',response.body.SubjectMaster);
		$scope.subjectMasterCurPage = 1;
		$scope.subjectMasterShowObject = $scope.fnMvwGetCurPageTable('subjectMaster');

		$scope.notificationMasterTable = fnConvertmvw('notificationMasterTable',response.body.NotificationMaster);
		$scope.notificationMasterCurPage = 1;
		$scope.notificationMasterShowObject = $scope.fnMvwGetCurPageTable('notificationMaster');

		$scope.feeMasterTable = fnConvertmvw('feeMasterTable',response.body.FeeTypeMaster);
		$scope.feeMasterCurPage = 1;
		$scope.feeMasterShowObject = $scope.fnMvwGetCurPageTable('feeMaster');
                }	
		//Multiple View Response Ends 
                // $scope.$apply();
      $("#logo").attr("src",$scope.profileImgPath);
 
                 
		return true;

}

}
}
