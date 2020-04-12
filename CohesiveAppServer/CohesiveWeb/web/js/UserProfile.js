    /* 
    Author     : IBD Technologies
	

*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var tabClick = "General";
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService, OperationScopes) {
	//Screen Specific Single View Starts   
	$scope.parentRoleMappingRecord = {
		idx: 0,
		roleID: "",
		studentName: "",
		studentID: "",
                instituteID:"",
                instituteName:""
	};
	$scope.parentRoleMappingTable = null;
	$scope.parentRoleMappingCurIndex = 0;
	$scope.parentRoleMappingShow = false;

	$scope.studentClassRoleMappingRecord = {
		idx: 0,
		roleID: "",
		class: "Select option",
                instituteID:"",
                instituteName:""
		//section: "Select option"

	};
	$scope.studentClassRoleMappingTable = null;
	$scope.studentClassRoleMappingCurIndex = 0;
	$scope.studentClassRoleMappingShow = false;

	$scope.teacherRoleMappingRecord = {
		idx: 0,
		roleID: "",
		instituteID: "",
		instituteName: "",
		teacherID: "",
		teacherName: "",
	};
	$scope.teacherRoleMappingTable = null;
	$scope.teacherRoleMappingCurIndex = 0;
	$scope.teacherRoleMappingShow = false;

	$scope.instituteRoleMappingRecord = {
		idx: 0,
		roleID: "",
		instituteID: "",
		instituteName: ""
	};
	$scope.instituteRoleMappingTable = null;
	$scope.instituteRoleMappingCurIndex = 0;
	$scope.instituteRoleMappingShow = false;
	//Screen Specific Single View Ends 
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.OperationScopes = OperationScopes;
	$scope.searchShow = false;
	$scope.svwAddDeteleDisable = true; // single View
	//Generic Field Ends
	//Screen Specific Scope starts
	$scope.userNamereadOnly = true;
        $scope.userNameSearchreadOnly = true;
	$scope.userIDreadOnly = true;
	$scope.mobileNoreadOnly = true;
	$scope.emailIDreadOnly = true;
	$scope.passwordreadOnly = true;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.roleIDreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
        $scope.instituteIDreadOnly = true;
        $scope.instituteSearchreadOnly = true;
         $scope.instituteNameSearchreadOnly = true;
	$scope.teacherNamereadOnly = true;
//	$scope.standardreadOnly = true;
	$scope.classReadonly = true;
	$scope.usertypereadOnly = true;
	$scope.userName = "";
	$scope.userID = "";
	$scope.UserMaster = [{
		UserId: "",
		UserName: ""
	}];
	$scope.RoleMaster = [{
		RoleId: "",
		RoleDescription: ""
	}];
	$scope.InstituteMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];
	$scope.TeacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
       $scope.InstituteUserMaster = [{
		InstituteId: "",
		InstituteName: ""
	}];
	$scope.emailID = "";
	$scope.mobileNo = "";
	$scope.password = "";
	$scope.expiryDate = "";
	$scope.userType = "";
        $scope.teacherID = "";
	$scope.status = '';
	$scope.Status = Institute.ProfileStatusMaster;
	$scope.UserTypeMaster = Institute.UserTypeMaster;
	//Screen Specific Scope Ends


	//Scope Levle Single View functions starts 
	$scope.fnSvwBackward = function (tableName, $event) {
		if (tableName == 'parentRole') {
			if ($scope.parentRoleMappingTable != null && $scope.parentRoleMappingTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.parentRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.parentRoleMappingTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.parentRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.parentRoleMappingTable = lsvwObject.tableObject;
				$scope.parentRoleMappingRecord = $scope.parentRoleMappingTable[$scope.parentRoleMappingCurIndex];
			}
		} else if (tableName == 'teacherRole') {
			if ($scope.teacherRoleMappingTable != null && $scope.teacherRoleMappingTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.teacherRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.teacherRoleMappingTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.teacherRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.teacherRoleMappingTable = lsvwObject.tableObject;
				$scope.teacherRoleMappingRecord = $scope.teacherRoleMappingTable[$scope.teacherRoleMappingCurIndex];
			}
		} else if (tableName == 'studentRole') {
			if ($scope.studentClassRoleMappingTable != null && $scope.studentClassRoleMappingTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.studentClassRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.studentClassRoleMappingTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.studentClassRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.studentClassRoleMappingTable = lsvwObject.tableObject;
				$scope.studentClassRoleMappingRecord = $scope.studentClassRoleMappingTable[$scope.studentClassRoleMappingCurIndex];
				//var SelectedArray = ['standardID', 'Section'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'instituteRole') {
			if ($scope.instituteRoleMappingTable != null && $scope.instituteRoleMappingTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.instituteRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.instituteRoleMappingTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.instituteRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.instituteRoleMappingTable = lsvwObject.tableObject;
				$scope.instituteRoleMappingRecord = $scope.instituteRoleMappingTable[$scope.instituteRoleMappingCurIndex];
			}
		}


	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'parentRole') {
			if ($scope.parentRoleMappingTable != null && $scope.parentRoleMappingTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.parentRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.parentRoleMappingTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.parentRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.parentRoleMappingTable = lsvwObject.tableObject;
				$scope.parentRoleMappingRecord = $scope.parentRoleMappingTable[$scope.parentRoleMappingCurIndex];
			}
		} else if (tableName == 'teacherRole') {
			if ($scope.teacherRoleMappingTable != null && $scope.teacherRoleMappingTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.teacherRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.teacherRoleMappingTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.teacherRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.teacherRoleMappingTable = lsvwObject.tableObject;
				$scope.teacherRoleMappingRecord = $scope.teacherRoleMappingTable[$scope.teacherRoleMappingCurIndex];
			}
		} else if (tableName == 'studentRole') {
			if ($scope.studentClassRoleMappingTable != null && $scope.studentClassRoleMappingTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.studentClassRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.studentClassRoleMappingTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.studentClassRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.studentClassRoleMappingTable = lsvwObject.tableObject;
				$scope.studentClassRoleMappingRecord = $scope.studentClassRoleMappingTable[$scope.studentClassRoleMappingCurIndex];
				//var SelectedArray = ['standardID', 'Section'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'instituteRole') {
			if ($scope.instituteRoleMappingTable != null && $scope.instituteRoleMappingTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.instituteRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.instituteRoleMappingTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.instituteRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.instituteRoleMappingTable = lsvwObject.tableObject;
				$scope.instituteRoleMappingRecord = $scope.instituteRoleMappingTable[$scope.instituteRoleMappingCurIndex];
			}
		}

	};


	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'parentRole') {
				emptyTableRec = {
					roleID: "",
					studentName: "",
					studentID: "",
                                        instituteName:"",
                                        instituteID:""
				};
				if ($scope.parentRoleMappingTable == null)
					$scope.parentRoleMappingTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.parentRoleMappingShow;
				lsvwObject.curIndex = $scope.parentRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.parentRoleMappingTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.parentRoleMappingShow = lsvwObject.tableShow;
				$scope.parentRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.parentRoleMappingTable = lsvwObject.tableObject;
				$scope.parentRoleMappingRecord = $scope.parentRoleMappingTable[$scope.parentRoleMappingCurIndex];

			} else if (tableName == 'teacherRole') {
				emptyTableRec = {
					idx: 0,
					roleID: "",
					instituteID: "",
					instituteName: "",
					teacherID: "",
					teacherName: "",
				};
				if ($scope.teacherRoleMappingTable == null)
					$scope.teacherRoleMappingTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.teacherRoleMappingShow;
				lsvwObject.curIndex = $scope.teacherRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.teacherRoleMappingTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.teacherRoleMappingShow = lsvwObject.tableShow;
				$scope.teacherRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.teacherRoleMappingTable = lsvwObject.tableObject;
				$scope.teacherRoleMappingRecord = $scope.teacherRoleMappingTable[$scope.teacherRoleMappingCurIndex];

			} else if (tableName == 'studentRole') {
				emptyTableRec = {
					idx: 0,
					roleID: "",
					class: "Select option",
                                         instituteName:"",
                                        instituteID:""
				};
				if ($scope.studentClassRoleMappingTable == null)
					$scope.studentClassRoleMappingTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.studentClassRoleMappingShow;
				lsvwObject.curIndex = $scope.studentClassRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.studentClassRoleMappingTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.studentClassRoleMappingShow = lsvwObject.tableShow;
				$scope.studentClassRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.studentClassRoleMappingTable = lsvwObject.tableObject;
				$scope.studentClassRoleMappingRecord = $scope.studentClassRoleMappingTable[$scope.studentClassRoleMappingCurIndex];
				//var SelectedArray = ['standardID', 'Section'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'instituteRole') {
				emptyTableRec = {
					idx: 0,
					roleID: "",
					instituteID: "",
					instituteName: ""
				};
				if ($scope.instituteRoleMappingTable == null)
					$scope.instituteRoleMappingTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.instituteRoleMappingShow;
				lsvwObject.curIndex = $scope.instituteRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.instituteRoleMappingTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.instituteRoleMappingShow = lsvwObject.tableShow;
				$scope.instituteRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.instituteRoleMappingTable = lsvwObject.tableObject;
				$scope.instituteRoleMappingRecord = $scope.instituteRoleMappingTable[$scope.instituteRoleMappingCurIndex];

			}

		}

	};
	$scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'parentRole') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.parentRoleMappingShow;
				lsvwObject.curIndex = $scope.parentRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.parentRoleMappingTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.parentRoleMappingShow = lsvwObject.tableShow;
				$scope.parentRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.parentRoleMappingTable = lsvwObject.tableObject;
				$scope.parentRoleMappingRecord = $scope.parentRoleMappingTable[$scope.parentRoleMappingCurIndex];
			} else if (tableName == 'teacherRole') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.teacherRoleMappingShow;
				lsvwObject.curIndex = $scope.teacherRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.teacherRoleMappingTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.teacherRoleMappingShow = lsvwObject.tableShow;
				$scope.teacherRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.teacherRoleMappingTable = lsvwObject.tableObject;
				$scope.teacherRoleMappingRecord = $scope.teacherRoleMappingTable[$scope.teacherRoleMappingCurIndex];
			} else if (tableName == 'studentRole') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.studentClassRoleMappingShow;
				lsvwObject.curIndex = $scope.studentClassRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.studentClassRoleMappingTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.studentClassRoleMappingShow = lsvwObject.tableShow;
				$scope.studentClassRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.studentClassRoleMappingTable = lsvwObject.tableObject;
				$scope.studentClassRoleMappingRecord = $scope.studentClassRoleMappingTable[$scope.studentClassRoleMappingCurIndex];
				//var SelectedArray = ['standardID', 'Section'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'instituteRole') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.instituteRoleMappingShow;
				lsvwObject.curIndex = $scope.instituteRoleMappingCurIndex;
				lsvwObject.tableObject = $scope.instituteRoleMappingTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.instituteRoleMappingShow = lsvwObject.tableShow;
				$scope.instituteRoleMappingCurIndex = lsvwObject.curIndex;
				$scope.instituteRoleMappingTable = lsvwObject.tableObject;
				$scope.instituteRoleMappingRecord = $scope.instituteRoleMappingTable[$scope.instituteRoleMappingCurIndex];
			}


		}
	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'parentRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.parentRoleMappingShow;
			lsvwObject.curIndex = $scope.parentRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.parentRoleMappingTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'teacherRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.teacherRoleMappingShow;
			lsvwObject.curIndex = $scope.teacherRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.teacherRoleMappingTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'studentRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.studentClassRoleMappingShow;
			lsvwObject.curIndex = $scope.studentClassRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.studentClassRoleMappingTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'instituteRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.instituteRoleMappingShow;
			lsvwObject.curIndex = $scope.instituteRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.instituteRoleMappingTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'parentRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.parentRoleMappingShow;
			lsvwObject.curIndex = $scope.parentRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.parentRoleMappingTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'teacherRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.teacherRoleMappingShow;
			lsvwObject.curIndex = $scope.teacherRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.teacherRoleMappingTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'studentRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.studentClassRoleMappingShow;
			lsvwObject.curIndex = $scope.studentClassRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.studentClassRoleMappingTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'instituteRole') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.instituteRoleMappingShow;
			lsvwObject.curIndex = $scope.instituteRoleMappingCurIndex;
			lsvwObject.tableObject = $scope.instituteRoleMappingTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		}
	};
	//Scope Level Single View functions Ends 

	$scope.fnUserSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'UserName';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
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
	$scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Institute';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
        $scope.fnInstituteUserSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'InstituteUser';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	$scope.fnUserRoleSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType: null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'UserRole';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}


});
//--------------------------------------------------------------------------------------------------------------


$(document).ready(function () {
	MenuName = "UserProfile";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="User";
      //  window.parent.fn_hide_parentspinner();
        selectBoxes = ['class', 'userStatus'];
        fnGetSelectBoxdata(selectBoxes);
	tabClick = "General";
	$("#generalTab").on('show.bs.tab', function (e) {
		tabClick = "general";
	});
	$("#parentTab").on('show.bs.tab', function (e) {
		tabClick = "Parent";
	});
	$("#classTab").on('show.bs.tab', function (e) {
		tabClick = "Class";
	});
	$("#teacherTab").on('show.bs.tab', function (e) {
		tabClick = "Teacher";
	});
	$("#instituteTab").on('show.bs.tab', function (e) {
		tabClick = "Institute";
	});   
});

function fnUserProfilepostSelectBoxMaster()
{
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	  if(Institute.ClassMaster.length>0){
              //Institute.ClassMaster.push('ALL/ALL');
         $scope.classes=Institute.ClassMaster;
          $scope.classes = Institute.ClassMaster.slice(0);
          $scope.classes.push('ALL/ALL');
	window.parent.fn_hide_parentspinner();
        
    if (window.parent.UserProfilekey.userID != null && window.parent.UserProfilekey.userID != '') {
		var userID = window.parent.UserProfilekey.userID;
		window.parent.UserProfilekey.userID = null;
		fnshowSubScreen(userID);

	} 
       $scope.$apply(); 
}
    }
}

function fnshowSubScreen(userID) {
	 subScreen = true;
	var emptyUserProfile = {
		userName: "",
		userID: "",
                instituteName:"",
                instituteID:"",
		emailID: "",
		mobileNo: "",
		password: "",
		expiryDate: "",
		userType: "",
                teacherID: "",
                teacherName: "",
		status: '',
		parentRoleMapping: [{
			idx: 0,
			roleID: "",
			studentName: "",
			studentID: "",
                        instituteName:"",
                        instituteID:""
		}],
		studentClassRoleMapping: [{
			idx: 0,
			roleID: "",
			class: "Select option",
			instituteName:"",
                        instituteID:""
		}],
		teacherRoleMapping: [{
			idx: 0,
			roleID: "",
			instituteID: "",
			instituteName: "",
			teacherID: "",
			teacherName: "",
		}],
		instituteRoleMapping: [{
			idx: 0,
			roleID: "",
			instituteID: "",
			instituteName: "",

		}]
	};

	//Screen specific DataModel Starts
	var dataModel = emptyUserProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//        if($scope.userName!=null)
//	dataModel.userName = $scope.userName;
         if (userID!=null||userID!=''){
             dataModel.userID=userID;
            //Screen specific DataModel Ends
            var response = fncallBackend('UserProfile', 'View', dataModel, [{entityName:"userID",entityValue:userID}], $scope);
        }
        return true;
}
//Cohesive query Framework Starts
function fnUserProfileQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Single View Starts   
	$scope.parentRoleMappingRecord = {
		idx: 0,
		roleID: "",
		studentName: "",
		studentID: "",
                instituteName:"",
                instituteID:""
	};
	$scope.parentRoleMappingTable = null;
	$scope.parentRoleMappingCurIndex = 0;
	$scope.parentRoleMappingShow = false;

	$scope.studentClassRoleMappingRecord = {
		idx: 0,
		roleID: "",
		class: "Select option",
		//section: "Select option"
                instituteName:"",
                instituteID:""
	};
	$scope.studentClassRoleMappingTable = null;
	$scope.studentClassRoleMappingCurIndex = 0;
	$scope.studentClassRoleMappingShow = false;

	$scope.teacherRoleMappingRecord = {
		idx: 0,
		roleID: "",
		instituteID: "",
		instituteName: "",
		teacherID: "",
		teacherName: "",
	};
	$scope.teacherRoleMappingTable = null;
	$scope.teacherRoleMappingCurIndex = 0;
	$scope.teacherRoleMappingShow = false;

	$scope.instituteRoleMappingRecord = {
		idx: 0,
		roleID: "",
		instituteID: "",
		instituteName: ""
	};
	$scope.instituteRoleMappingTable = null;
	$scope.instituteRoleMappingCurIndex = 0;
	$scope.instituteRoleMappingShow = false;
	//Screen Specific Single View Ends
	//Screen Specific Scope Starts
	$scope.userNamereadOnly = true;
        $scope.userNameSearchreadOnly = false;
          $scope.instituteSearchreadOnly = false;
	$scope.userIDreadOnly = false;
	$scope.mobileNoreadOnly = true;
	$scope.emailIDreadOnly = true;
	$scope.passwordreadOnly = true;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.roleIDreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
//	$scope.standardreadOnly = true;
//	$scope.sectionreadOnly = true;
        $scope.classReadonly = true;
	$scope.teacherIDreadOnly = true;
	$scope.instituteIDreadOnly = false;
	$scope.instituteNamereadOnly = false;
	$scope.teacherNamereadOnly = true;
	$scope.usertypereadOnly = true;
	$scope.userName = "";
	$scope.userID = "";
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
//Cohesive query Framework Ends
//Cohesive View Framework Starts
function fnUserProfileView() {
	var emptyUserProfile = {
		userName: "",
		userID: "",
                 instituteName:"",
                instituteID:"",
		emailID: "",
		mobileNo: "",
		password: "",
		expiryDate: "",
		userType: "",
                teacherID: "",
                teacherName: "",
		status: '',
		parentRoleMapping: [{
			idx: 0,
			roleID: "",
			studentName: "",
			studentID: "",
                        instituteName:"",
                        instituteID:""
		}],
		studentClassRoleMapping: [{
			idx: 0,
			roleID: "",
			class: "Select option",
			instituteName:"",
                        instituteID:""
		}],
		teacherRoleMapping: [{
			idx: 0,
			roleID: "",
			instituteID: "",
			instituteName: "",
			teacherID: "",
			teacherName: ""
		}],
		instituteRoleMapping: [{
			idx: 0,
			roleID: "",
			instituteID: "",
			instituteName: "",
		}]
	};

	//Screen specific DataModel Starts
	var dataModel = emptyUserProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.userID!=null)
	dataModel.userID = $scope.userID;
	//Screen specific DataModel Ends
	var response = fncallBackend('UserProfile', 'View', dataModel, [{entityName:"userID",entityValue:$scope.userID}], $scope);
	return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
function fnUserProfileMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.userID == '' || $scope.userID == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['User ID']);
				return false;
			}
//                        if ($scope.instituteID == '' || $scope.instituteID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
//				return false;
//			}
			break;

		case 'Save':
			if ($scope.userName == '' || $scope.userName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['User Name']);
				return false;
			}
//                         if ($scope.instituteName == '' || $scope.instituteName == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name']);
//				return false;
//			}
//                         if ($scope.instituteID == '' || $scope.instituteID == null) {
//				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute ID']);
//				return false;
//			}
			if ($scope.password == '' || $scope.password == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Password in General Tab']);
				return false;
			}
			if ($scope.userType == '' || $scope.userType == null) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['User Type in General Tab']);
				return false;
			}
			if ($scope.status == '' || $scope.status == null || $scope.status == 'Select option') {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Status in General Tab']);
				return false;
			}

                       if($scope.userType =='P'){

			if ($scope.parentRoleMappingTable == null || $scope.parentRoleMappingTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Parent Role Mapping in Parent Tab']);
				return false;
			}
			for (i = 0; i < $scope.parentRoleMappingTable.length; i++) {
				if ($scope.parentRoleMappingTable[i].roleID == '' || $scope.parentRoleMappingTable[i].roleID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Role ID Parent Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.parentRoleMappingTable[i].studentName == '' || $scope.parentRoleMappingTable[i].studentName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['student Name Parent Tab ' + 'record ' + (i + 1)]);
					return false;
				}
                                if ($scope.parentRoleMappingTable[i].studentID == '' || $scope.parentRoleMappingTable[i].studentID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['student ID Parent Tab ' + 'record ' + (i + 1)]);
					return false;
				}
                                if ($scope.parentRoleMappingTable[i].instituteName == '' || $scope.parentRoleMappingTable[i].instituteName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name Parent Tab ' + 'record ' + (i + 1)]);
					return false;
				}
			}
                      }
               
                     
                     if($scope.userType =='T'||$scope.userType =='A'){
                     
			if ($scope.studentClassRoleMappingTable == null || $scope.studentClassRoleMappingTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Student Role in Class Tab']);
				return false;
			}
			for (i = 0; i < $scope.studentClassRoleMappingTable.length; i++) {
				if ($scope.studentClassRoleMappingTable[i].roleID == '' || $scope.studentClassRoleMappingTable[i].roleID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Role ID Class Tab ' + 'record' + (i + 1)]);
					return false;
				}
				if ($scope.studentClassRoleMappingTable[i].class == '' || $scope.studentClassRoleMappingTable[i].class == null || $scope.studentClassRoleMappingTable[i].class == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Class in Class Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.studentClassRoleMappingTable[i].instituteName == '' || $scope.studentClassRoleMappingTable[i].instituteName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Instutute Name in Class Tab ' + 'record ' + (i + 1)]);
					return false;
				}
			}
                        
                     }  

                    if($scope.userType =='T'||$scope.userType =='A'){

			if ($scope.teacherRoleMappingTable == null || $scope.teacherRoleMappingTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Role in Teacher Tab']);
				return false;
			}
			for (i = 0; i < $scope.teacherRoleMappingTable.length; i++) {
				if ($scope.teacherRoleMappingTable[i].roleID == '' || $scope.teacherRoleMappingTable[i].roleID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Role ID Teacher Tab']);
					return false;
				}
				if ($scope.teacherRoleMappingTable[i].instituteName == '' || $scope.teacherRoleMappingTable[i].instituteName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name Teacher Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.teacherRoleMappingTable[i].teacherID == '' || $scope.teacherRoleMappingTable[i].teacherID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher ID Teacher Tab ' + 'record ' + (i + 1)]);
					return false;
				}
			}
                        
                    } 
                    
                    
                    if($scope.userType =='A'){
			if ($scope.instituteRoleMappingTable == null || $scope.instituteRoleMappingTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Role in Institute Tab']);
				return false;
			}
			for (i = 0; i < $scope.instituteRoleMappingTable.length; i++) {
				if ($scope.instituteRoleMappingTable[i].roleID == '' || $scope.instituteRoleMappingTable[i].roleID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Role in Institute Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.instituteRoleMappingTable[i].instituteName == '' || $scope.instituteRoleMappingTable[i].instituteName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Name in Institute Tab ' + 'record ' + (i + 1)]);
					return false;
				}
				if ($scope.instituteRoleMappingTable[i].roleID == '' || $scope.instituteRoleMappingTable[i].roleID == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Institute Role in Institute Tab ' + 'record ' + (i + 1)]);
					return false;
				}
			}
                        
                      }
			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnUserProfileDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultUserId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultUserId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultUserId($scope) {
	var availabilty = false;
	return true;
}

//Screen Specific Default Validation Ends
//Cohesive Create Framework Starts
function fnUserProfileCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	//Single View Starts
	$scope.studentClassRoleMappingRecord = {
		idx: 0,
		roleID: "",
		class: "Select option",
                instituteName:"",
                instituteID:""

	};
	$scope.studentClassRoleMappingTable = null;
	$scope.studentClassRoleMappingCurIndex = 0;
	$scope.studentClassRoleMappingShow = false;

	$scope.parentRoleMappingRecord = null;
	$scope.parentRoleMappingTable = null;
	$scope.parentRoleMappingCurIndex = 0;
	$scope.parentRoleMappingShow = false;

	$scope.teacherRoleMappingRecord = null;
	$scope.teacherRoleMappingTable = null;
	$scope.teacherRoleMappingCurIndex = 0;
	$scope.teacherRoleMappingShow = false;

	$scope.instituteRoleMappingRecord = null;
	$scope.instituteRoleMappingTable = null;
	$scope.instituteRoleMappingCurIndex = 0;
	$scope.instituteRoleMappingShow = false;
	//Single View Ends
	//Screen Specific Scope Starts
	$scope.userName = "";
	$scope.userID = "";
	$scope.emailID = "";
	$scope.mobileNo = "";
	$scope.password = "";
	$scope.expiryDate = "";
	$scope.userType = "";
	$scope.status = '';
        $scope.teacherID= "";
        $scope.teacherName= "";
         $scope.instituteID = window.parent.Institute.ID;
	$scope.instituteName = window.parent.Institute.Name;
	$scope.userNamereadOnly = false;
        $scope.userNameSearchreadOnly = true;
          $scope.instituteSearchreadOnly = false;
          $scope.instituteNameSearchreadOnly = false;
	$scope.userIDreadOnly = false;
	$scope.mobileNoreadOnly = false;
	$scope.emailIDreadOnly = false;
	$scope.passwordreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = false;
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = false;
	$scope.usertypereadOnly = false;
	$scope.roleIDreadOnly = false;
	$scope.instituteIDreadOnly = false;
//	$scope.standardreadOnly = false;
//	$scope.sectionreadOnly = false;
        $scope.classReadonly = false;
	$scope.instituteNamereadOnly = false;
	$scope.teacherNamereadOnly = false;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = false; // single view
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	//Generic Field Ends	
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnUserProfileEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.svwAddDeteleDisable = false; // single view
	//Generic Field Ends
	//Screen Specific scope Starts
	$scope.userNamereadOnly = false;
        $scope.userNameSearchreadOnly = true;
	$scope.userIDreadOnly = true;
	$scope.mobileNoreadOnly = false;
	$scope.passwordreadOnly = true;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = false;
	$scope.usertypereadOnly = false;
	$scope.roleIDreadOnly = false;
//	$scope.standardreadOnly = false;
//	$scope.sectionreadOnly = false;
        $scope.classReadonly = false;
	$scope.instituteIDreadOnly = true;
	$scope.teacherIDreadOnly = false;
	$scope.emailIDreadOnly = false;
	$scope.studentNamereadOnly = false;
	$scope.studentIDreadOnly = true;
	$scope.instituteNamereadOnly = false;
	$scope.teacherNamereadOnly = false;
          $scope.instituteSearchreadOnly = false;
          $scope.instituteNameSearchreadOnly = false;
	//Screen Specific Scope Starts
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnUserProfileDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.svwAddDeteleDisable = true; // single view
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.userNamereadOnly = true;
        $scope.userNameSearchreadOnly = true;
	$scope.userIDreadOnly = true;
	$scope.mobileNoreadOnly = true;
	$scope.passwordreadOnly = true;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.usertypereadOnly = true;
//	$scope.standardreadOnly = true;
//	$scope.sectionreadOnly = true;
        $scope.classReadonly = true;
	$scope.instituteIDreadOnly = true;
	$scope.roleIDreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.emailIDreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.teacherNamereadOnly = true;
        $scope.instituteNameSearchreadOnly = true;
	//Screen Specific Scope Ends	
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework starts
function fnUserProfileAuth() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.userNamereadOnly = true;
        $scope.userNameSearchreadOnly = true;
	$scope.userIDreadOnly = true;
	$scope.mobileNoreadOnly = true;
	$scope.passwordreadOnly = true;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.usertypereadOnly = true;
	$scope.instituteIDreadOnly = true;
	$scope.teacherIDreadOnly = true;
//	$scope.standardreadOnly = true;
//	$scope.sectionreadOnly = true;
        $scope.classReadonly = true;
	$scope.emailIDreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
          $scope.instituteSearchreadOnly = true;
          $scope.instituteNameSearchreadOnly = true;
	//screen Specific Scope Ends
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnUserProfileReject() {
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
	$scope.userNamereadOnly = true;
        $scope.userNameSearchreadOnly = true;
	$scope.userIDreadOnly = true;
	$scope.mobileNoreadOnly = true;
	$scope.passwordreadOnly = true;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.usertypereadOnly = true;
//	$scope.standardreadOnly = true;
//	$scope.sectionreadOnly = true;
        $scope.classReadonly = true;
	$scope.instituteIDreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.emailIDreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.teacherNamereadOnly = true;
          $scope.instituteSearchreadOnly = true;
	//Screen Specific Scope Ends	
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnUserProfileBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.userName = "";
		$scope.userID = "";
                $scope.instituteName = "";
                 $scope.instituteID = "";
		$scope.emailID = "";
		$scope.mobileNo = "";
		$scope.password = "";
		$scope.expiryDate = "";
		$scope.userType = "";
		$scope.status = '';
                $scope.teacherID= "";
                $scope.teacherName= "";
		$scope.studentClassRoleMappingTable = null;
		$scope.studentClassRoleMappingRecord = null,
			$scope.parentRoleMappingTable = null;
		$scope.parentRoleMappingRecord = null;
		$scope.teacherRoleMappingTable = null;
		$scope.teacherRoleMappingRecord = null;
		$scope.instituteRoleMappingTable = null;
		$scope.instituteRoleMappingRecord = null;
	}

	$scope.userNamereadOnly = true;
        $scope.userNameSearchreadOnly = true;
	$scope.userIDreadOnly = true;
	$scope.mobileNoreadOnly = true;
	$scope.passwordreadOnly = true;
	$scope.emailIDreadOnly = true;
	$scope.expiryDatereadOnly = true;
	$scope.statusReadonly = true;
	$scope.usertypereadOnly = true;
//	$scope.standardreadOnly = true;
//	$scope.sectionreadOnly = true;
        $scope.classReadonly = true;
	$scope.instituteIDreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.roleIDreadOnly = true;
	$scope.studentNamereadOnly = true;
	$scope.studentIDreadOnly = true;
	$scope.instituteNamereadOnly = true;
	$scope.teacherNamereadOnly = true;
         $scope.instituteSearchreadOnly = true; 
         $scope.instituteNameSearchreadOnly = true;

	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
          $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.svwAddDeteleDisable = true; // single view
	//Generic Field Ends
}
//Cohesive Back Framework Ends
//Cohesive Save Framework Starts
function fnUserProfileSave() {
	var emptyUserProfile = {
		userName: "",
		userID: "",
                instituteName:"",
                instituteID:"",
		emailID: "",
		mobileNo:"",
		password: "",
		expiryDate: "",
		userType: "",
                teacherID: "",
                teacherName: "",
		status: '',
		parentRoleMapping: [{
			idx: 0,
			roleID: "",
                        instituteName:"",
                        instituteID:"",
                        studentID:""
		}],
		studentClassRoleMapping: [{
			idx: 0,
			roleID: "",
			class: "Select option",
			instituteName:"",
                        instituteID:""
		}],
		teacherRoleMapping: [{
			idx: 0,
			roleID: "",
			instituteID: "",
                        instituteName:"",
			teacherID: ""
		}],
		instituteRoleMapping: [{
			idx: 0,
			roleID: "",
                        instituteName:"",
			instituteID: ""
		}]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyUserProfile;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.userName!=null)
	dataModel.userName = $scope.userName;
        if($scope.userID!=null)
	dataModel.userID = $scope.userID;
        if($scope.instituteName!=null)
	dataModel.instituteName = $scope.instituteName;
        if($scope.instituteID!=null)
	dataModel.instituteID = $scope.instituteID;
        if($scope.emailID!=null)
	dataModel.emailID = $scope.emailID;
        if($scope.mobileNo!=null)
	dataModel.mobileNo = $scope.mobileNo;
        if($scope.password!=null)
	dataModel.password = $scope.password;
         if($scope.expiryDate!=null)
	dataModel.expiryDate = $scope.expiryDate;
          if($scope.userType!=null)
	dataModel.userType = $scope.userType;
    if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
       if($scope.status!=null)
	dataModel.status = $scope.status;
       if($scope.studentClassRoleMappingTable!=null){
           
           if($scope.studentClassRoleMappingTable.length!=0){
           
        	dataModel.studentClassRoleMapping = $scope.studentClassRoleMappingTable;
            }else{
                dataModel.studentClassRoleMapping=[];
            }
       }else{
           dataModel.studentClassRoleMapping=[];
       }
    
        if($scope.parentRoleMappingTable!=null){
            
            if($scope.parentRoleMappingTable.length!=0){
                
        	dataModel.parentRoleMapping = $scope.parentRoleMappingTable;
            }else{
                
                dataModel.parentRoleMapping=[];
            }
        }else{
            
            dataModel.parentRoleMapping=[];
        }
    
//         if($scope.teacherRoleMappingTable!=null)
//	dataModel.teacherRoleMapping = $scope.teacherRoleMappingTable;
        if($scope.teacherRoleMappingTable!=null){
            
            if($scope.teacherRoleMappingTable.length!=0){
                
        	dataModel.teacherRoleMapping = $scope.teacherRoleMappingTable;
            }else{
                
                dataModel.teacherRoleMapping=[];
            }
        }else{
            
            dataModel.teacherRoleMapping=[];
        }
    
//        if($scope.instituteRoleMappingTable!=null)
//	dataModel.instituteRoleMapping = $scope.instituteRoleMappingTable;
        if($scope.instituteRoleMappingTable!=null){
            
            if($scope.instituteRoleMappingTable.length!=0){
                
        	dataModel.instituteRoleMapping = $scope.instituteRoleMappingTable;
            }else{
                
                dataModel.instituteRoleMapping=[];
            }
        }else{
            
            dataModel.instituteRoleMapping=[];
        }
	//Screen specific DataModel Ends
	var response = fncallBackend('UserProfile', parentOperation, dataModel, [{entityName:"userID",entityValue:$scope.userID}], $scope);
	return true;
}

function fnConvertmvw(tableName, responseObject) {
	switch (tableName) {

		case 'parentRoleMappingTable':

			var parentRoleMappingTable = new Array();
			responseObject.forEach(fnConvert1);

			function fnConvert1(value, index, array) {
				parentRoleMappingTable[index] = new Object();
				parentRoleMappingTable[index].idx = index;
				parentRoleMappingTable[index].checkBox = false;
				parentRoleMappingTable[index].roleID = value.roleID;
                                parentRoleMappingTable[index].instituteName = value.instituteName;
                                parentRoleMappingTable[index].instituteID = value.instituteID;
                                parentRoleMappingTable[index].studentID=value.studentID;
			}
			return parentRoleMappingTable;
			break;
//		case 'studentClassRoleMappingTable':

		case 'studentClassRoleMappingTable':

			var studentClassRoleMappingTable = new Array();
			responseObject.forEach(fnConvert2);

			function fnConvert2(value, index, array) {
				studentClassRoleMappingTable[index] = new Object();
				studentClassRoleMappingTable[index].idx = index;
				studentClassRoleMappingTable[index].checkBox = false;
				studentClassRoleMappingTable[index].roleID = value.roleID;
				studentClassRoleMappingTable[index].class = value.class;
				studentClassRoleMappingTable[index].instituteName = value.instituteName;
                                studentClassRoleMappingTable[index].instituteID = value.instituteID;
			}
			return studentClassRoleMappingTable;
			break;
		case 'teacherRoleMappingTable':

			var teacherRoleMappingTable = new Array();
			responseObject.forEach(fnConvert4);

			function fnConvert4(value, index, array) {
				teacherRoleMappingTable[index] = new Object();
				teacherRoleMappingTable[index].idx = index;
				teacherRoleMappingTable[index].checkBox = false;
				teacherRoleMappingTable[index].roleID = value.roleID;
				teacherRoleMappingTable[index].instituteID = value.instituteID;
				teacherRoleMappingTable[index].instituteName = value.instituteName;
				teacherRoleMappingTable[index].teacherID = value.teacherID;
				teacherRoleMappingTable[index].teacherName = value.teacherName;
			}
			return teacherRoleMappingTable;
			break;
                        
                        case 'instituteRoleMappingTable':
                        
			var instituteRoleMappingTable = new Array();
			responseObject.forEach(fnConvert5);

			function fnConvert5(value, index, array) {
				instituteRoleMappingTable[index] = new Object();
				instituteRoleMappingTable[index].idx = index;
				instituteRoleMappingTable[index].checkBox = false;
				instituteRoleMappingTable[index].roleID = value.roleID;
				instituteRoleMappingTable[index].instituteID = value.instituteID;
				instituteRoleMappingTable[index].instituteName = value.instituteName;
			}
			return instituteRoleMappingTable;
			break;
	}
}



function fnUserProfilepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
           
		// Specific Screen Scope Starts
                $scope.MakerRemarksReadonly = true;
	        $scope.CheckerRemarksReadonly = true;
		$scope.userNamereadOnly = true;
                $scope.userNameSearchreadOnly = true;
		$scope.userIDreadOnly = true;
		$scope.mobileNoreadOnly = true;
		$scope.passwordreadOnly = true;
		$scope.mobileNoreadOnly = true;
		$scope.expiryDatereadOnly = true;
		$scope.statusReadonly = true;
		$scope.usertypereadOnly = true;
//		$scope.standardreadOnly = true;
		$scope.classReadonly = true;
		$scope.instituteIDreadOnly = true;
		$scope.teacherIDreadOnly = true;
		$scope.roleIDreadOnly = true;
		$scope.emailIDreadOnly = true;
		$scope.studentNamereadOnly = true;
		$scope.studentIDreadOnly = true;
		$scope.instituteNamereadOnly = true;
		$scope.teacherNamereadOnly = true;
                  $scope.instituteSearchreadOnly = true;
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
                $scope.userName = "";
		$scope.userID ="";
		$scope.mobileNo ="";
                $scope.emailID ="";
                $scope.expiryDate ="";
                $scope.status ="";
                $scope.password ="";
                $scope.userType ="";
                $scope.teacherID ="";
                $scope.teacherName ="";
		$scope.studentClassRoleMapping ={};
		$scope.parentRoleMapping ={};
		$scope.teacherRoleMapping = {};
		$scope.instituteRoleMapping ={};
                $scope.studentClassRoleMappingShow=null;
                $scope.parentRoleMappingShow=null;
                $scope.teacherRoleMappingShow=null;
                $scope.examMasterShowObject=null;
                $scope.instituteRoleMappingShow=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.userName = response.body.userName;
		$scope.userID = response.body.userID;
                $scope.instituteID = response.body.instituteID;
                $scope.instituteName = response.body.instituteName;
		$scope.mobileNo = response.body.mobileNo;
		$scope.emailID = response.body.emailID;
		$scope.expiryDate = response.body.expiryDate;
		$scope.status = response.body.status;
		$scope.password = response.body.password;
		$scope.userType = response.body.userType;
                $scope.teacherID =response.body.teacherID;
                $scope.teacherName =response.body.teacherName;
                if(response.body.studentClassRoleMapping.length!=0){
                
		   $scope.studentClassRoleMappingTable = fnConvertmvw('studentClassRoleMappingTable', response.body.studentClassRoleMapping);
               }else{
                   $scope.studentClassRoleMappingTable=null;
               }
            
               $scope.studentClassRoleMappingCurIndex = 0;
		if ($scope.studentClassRoleMappingTable != null) {
			$scope.studentClassRoleMappingRecord = $scope.studentClassRoleMappingTable[$scope.studentClassRoleMappingCurIndex];
			$scope.studentClassRoleMappingShow = true;
		}

                if(response.body.parentRoleMapping.length!=0){
    
		   $scope.parentRoleMappingTable = fnConvertmvw('parentRoleMappingTable', response.body.parentRoleMapping);
                }else{
                    
                   $scope.parentRoleMappingTable=null; 
                }
            
                $scope.parentRoleMappingCurIndex = 0;
		if ($scope.parentRoleMappingTable != null) {
			$scope.parentRoleMappingRecord = $scope.parentRoleMappingTable[$scope.parentRoleMappingCurIndex];
			$scope.parentRoleMappingShow = true;
		}

             if(response.body.teacherRoleMapping.length!=0){

		$scope.teacherRoleMappingTable = fnConvertmvw('teacherRoleMappingTable', response.body.teacherRoleMapping);
             }else{
                 $scope.teacherRoleMappingTable=null;
             }
                
                $scope.teacherRoleMappingCurIndex = 0;
		if ($scope.teacherRoleMappingTable != null) {
			$scope.teacherRoleMappingRecord = $scope.teacherRoleMappingTable[$scope.teacherRoleMappingCurIndex];
			$scope.teacherRoleMappingShow = true;
		}
 
            if(response.body.instituteRoleMapping.length!=0){
		$scope.instituteRoleMappingTable = fnConvertmvw('instituteRoleMappingTable', response.body.instituteRoleMapping);
            }else{
                $scope.instituteRoleMappingTable=null;
            }
            
            
                $scope.instituteRoleMappingCurIndex = 0;
		if ($scope.instituteRoleMappingTable != null) {
			$scope.instituteRoleMappingRecord = $scope.instituteRoleMappingTable[$scope.instituteRoleMappingCurIndex];
			$scope.instituteRoleMappingShow = true;
		}
                $scope.audit = response.audit;
		//Screen Specific Response Scope Ends
 
        }
          if (subScreen){
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	   $operationScope.fnPostdetailLoad();
            subScreen=false;
            }
		return true;

}

}