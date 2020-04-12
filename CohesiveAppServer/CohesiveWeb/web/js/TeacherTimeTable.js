/* 
    Author     :IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var day = "Mon";
var tabClick = "General";
var subScreen = false;
var selectBypassCount=0;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
	//Screen Specific Scope Starts
	$scope.teacherName = "";
	$scope.teacherID = "";
	$scope.teacherMaster = [{
		TeacherId: "",
		TeacherName: ""
	}];
	$scope.classes = Institute.ClassMaster;
	$scope.subjects = Institute.SubjectMaster;
	$scope.period = Institute.PeriodMaster;
	
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberReadonly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes = OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = '';
	$scope.svwAddDeteleDisable = true; // single View
	$scope.ClassPeriod = ["Select option"]; // Std/sec
	$scope.period = Institute.PeriodMaster;
	//Generic Field Ends
	//Search Level Scope Starts
	$scope.fnTeacherSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'Teacher';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
	}
	//Search Level Scope Ends
	//Single View Starts
	$scope.MondayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};	
	$scope.MondayTable = null;
	$scope.MondayCurIndex = 0;
	$scope.MondayShowObject = false;

	$scope.TuesdayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.TuesdayTable = null;
	$scope.TuesdayCurIndex = 0;
	$scope.TuesdayShowObject = false;

	$scope.WednesdayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.WednesdayTable = null;
	$scope.WednesdayCurIndex = 0;
	$scope.WednesdayShowObject = false;

	$scope.ThursdayRecord = 
	{
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.ThursdayTable = null;
	$scope.ThursdayCurIndex = 0;
	$scope.ThursdayShowObject = false;

	$scope.FridayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.FridayTable = null;
	$scope.FridayCurIndex = 0;
	$scope.FridayShowObject = false;
	
	/*$scope.MondayRecord.period = [{PeriodNumber:"Select option",StartTimeHour:"Hour",StartTimeMin:"Min",EndTimeHour:"Hour",EndTimeMin:"Min"}];
	$scope.TuesdayRecord.period = [{PeriodNumber:"Select option",StartTimeHour:"Hour",StartTimeMin:"Min",EndTimeHour:"Hour",EndTimeMin:"Min"}];
	$scope.WednesdayRecord.period = [{PeriodNumber:"Select option",StartTimeHour:"Hour",StartTimeMin:"Min",EndTimeHour:"Hour",EndTimeMin:"Min"}];
	$scope.ThursdayRecord.period = [{PeriodNumber:"Select option",StartTimeHour:"Hour",StartTimeMin:"Min",EndTimeHour:"Hour",EndTimeMin:"Min"}];
	$scope.FridayRecord.period = [{PeriodNumber:"Select option",StartTimeHour:"Hour",StartTimeMin:"Min",EndTimeHour:"Hour",EndTimeMin:"Min"}];*/
	
	// Single View Ends	
	
	
	//Scope Levle Single View functions starts 
	$scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'monday') {
			if ($scope.MondayTable != null && $scope.MondayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.MondayCurIndex;
				lsvwObject.tableObject = $scope.MondayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.MondayCurIndex = lsvwObject.curIndex;
				$scope.MondayTable = lsvwObject.tableObject;
				$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
				//var SelectedArray= ['class1','periodNumber1','subject1'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'tuesday') {
			if ($scope.TuesdayTable != null && $scope.TuesdayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.TuesdayCurIndex;
				lsvwObject.tableObject = $scope.TuesdayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.TuesdayCurIndex = lsvwObject.curIndex;
				$scope.TuesdayTable = lsvwObject.tableObject;
				$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
				//var SelectedArray= ['class2','periodNumber2','subject2'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'wednesday') {
			if ($scope.WednesdayTable != null && $scope.WednesdayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.WednesdayCurIndex;
				lsvwObject.tableObject = $scope.WednesdayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.WednesdayCurIndex = lsvwObject.curIndex;
				$scope.WednesdayTable = lsvwObject.tableObject;
				$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
				//var SelectedArray= ['class3','periodNumber3','subject3'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'thursday') {
			if ($scope.ThursdayTable != null && $scope.ThursdayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.ThursdayCurIndex;
				lsvwObject.tableObject = $scope.ThursdayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.ThursdayCurIndex = lsvwObject.curIndex;
				$scope.ThursdayTable = lsvwObject.tableObject;
				$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['class4','periodNumber4','subject4'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'friday') {
			if ($scope.FridayTable != null && $scope.FridayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.FridayCurIndex;
				lsvwObject.tableObject = $scope.FridayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.FridayCurIndex = lsvwObject.curIndex;
				$scope.FridayTable = lsvwObject.tableObject;
				$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
				//var SelectedArray= ['class5','periodNumber5','subject5'];
				//fnSelectRefresh(SelectedArray);
			}
		}


	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'monday') {
			if ($scope.MondayTable != null && $scope.MondayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.MondayCurIndex;
				lsvwObject.tableObject = $scope.MondayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.MondayCurIndex = lsvwObject.curIndex;
				$scope.MondayTable = lsvwObject.tableObject;
				$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
				//var SelectedArray= ['class1','periodNumber1','subject1'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'tuesday') {
			if ($scope.TuesdayTable != null && $scope.TuesdayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.TuesdayCurIndex;
				lsvwObject.tableObject = $scope.TuesdayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.TuesdayCurIndex = lsvwObject.curIndex;
				$scope.TuesdayTable = lsvwObject.tableObject;
				$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
				//var SelectedArray= ['class2','periodNumber2','subject2'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'wednesday') {
			if ($scope.WednesdayTable != null && $scope.WednesdayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.WednesdayCurIndex;
				lsvwObject.tableObject = $scope.WednesdayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.WednesdayCurIndex = lsvwObject.curIndex;
				$scope.WednesdayTable = lsvwObject.tableObject;
				$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
				//var SelectedArray= ['class3','periodNumber3','subject3'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'thursday') {
			if ($scope.ThursdayTable != null && $scope.ThursdayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.ThursdayCurIndex;
				lsvwObject.tableObject = $scope.ThursdayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.ThursdayCurIndex = lsvwObject.curIndex;
				$scope.ThursdayTable = lsvwObject.tableObject;
				$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['class4','periodNumber4','subject4'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'friday') {
			if ($scope.FridayTable != null && $scope.FridayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.FridayCurIndex;
				lsvwObject.tableObject = $scope.FridayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.FridayCurIndex = lsvwObject.curIndex;
				$scope.FridayTable = lsvwObject.tableObject;
				$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
				//var SelectedArray= ['class5','periodNumber5','subject5'];
				//fnSelectRefresh(SelectedArray);
			}
		}

	};


	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'monday') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				};
				if ($scope.MondayTable == null)
					$scope.MondayTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.MondayShowObject;
				lsvwObject.curIndex = $scope.MondayCurIndex;
				lsvwObject.tableObject = $scope.MondayTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.MondayShowObject = lsvwObject.tableShow;
				$scope.MondayCurIndex = lsvwObject.curIndex;
				$scope.MondayTable = lsvwObject.tableObject;
				$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
				//var SelectedArray= ['class1','periodNumber1','subject1'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'tuesday') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				};
				if ($scope.TuesdayTable == null)
					$scope.TuesdayTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.TuesdayShowObject;
				lsvwObject.curIndex = $scope.TuesdayCurIndex;
				lsvwObject.tableObject = $scope.TuesdayTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.TuesdayShowObject = lsvwObject.tableShow;
				$scope.TuesdayCurIndex = lsvwObject.curIndex;
				$scope.TuesdayTable = lsvwObject.tableObject;
				$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
				//var SelectedArray= ['class2','periodNumber2','subject2'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'wednesday') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				};
				if ($scope.WednesdayTable == null)
					$scope.WednesdayTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.WednesdayShowObject;
				lsvwObject.curIndex = $scope.WednesdayCurIndex;
				lsvwObject.tableObject = $scope.WednesdayTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.WednesdayShowObject = lsvwObject.tableShow;
				$scope.WednesdayCurIndex = lsvwObject.curIndex;
				$scope.WednesdayTable = lsvwObject.tableObject;
				$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
				//var SelectedArray= ['class3','periodNumber3','subject3'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'thursday') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				};
				if ($scope.ThursdayTable == null)
					$scope.ThursdayTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.ThursdayShowObject;
				lsvwObject.curIndex = $scope.ThursdayCurIndex;
				lsvwObject.tableObject = $scope.ThursdayTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.ThursdayShowObject = lsvwObject.tableShow;
				$scope.ThursdayCurIndex = lsvwObject.curIndex;
				$scope.ThursdayTable = lsvwObject.tableObject;
				$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['class4','periodNumber4','subject4'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'friday') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				};
				if ($scope.FridayTable == null)
					$scope.FridayTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.FridayShowObject;
				lsvwObject.curIndex = $scope.FridayCurIndex;
				lsvwObject.tableObject = $scope.FridayTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.FridayShowObject = lsvwObject.tableShow;
				$scope.FridayCurIndex = lsvwObject.curIndex;
				$scope.FridayTable = lsvwObject.tableObject;
				$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
				//var SelectedArray= ['class5','periodNumber5','subject5'];
				//fnSelectRefresh(SelectedArray);

			}

		}

	};
	$scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'monday') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.MondayShowObject;
				lsvwObject.curIndex = $scope.MondayCurIndex;
				lsvwObject.tableObject = $scope.MondayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.MondayShowObject = lsvwObject.tableShow;
				$scope.MondayCurIndex = lsvwObject.curIndex;
				$scope.MondayTable = lsvwObject.tableObject;
				$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
				//var SelectedArray= ['class1','periodNumber1','subject1'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'tuesday') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.TuesdayShowObject;
				lsvwObject.curIndex = $scope.TuesdayCurIndex;
				lsvwObject.tableObject = $scope.TuesdayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.TuesdayShowObject = lsvwObject.tableShow;
				$scope.TuesdayCurIndex = lsvwObject.curIndex;
				$scope.TuesdayTable = lsvwObject.tableObject;
				$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
				//var SelectedArray= ['class2','periodNumber2','subject2'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'wednesday') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.WednesdayShowObject;
				lsvwObject.curIndex = $scope.WednesdayCurIndex;
				lsvwObject.tableObject = $scope.WednesdayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.WednesdayShowObject = lsvwObject.tableShow;
				$scope.WednesdayCurIndex = lsvwObject.curIndex;
				$scope.WednesdayTable = lsvwObject.tableObject;
				$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
				//var SelectedArray= ['class3','periodNumber3','subject3'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'thursday') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.ThursdayShowObject;
				lsvwObject.curIndex = $scope.ThursdayCurIndex;
				lsvwObject.tableObject = $scope.ThursdayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.ThursdayShowObject = lsvwObject.tableShow;
				$scope.ThursdayCurIndex = lsvwObject.curIndex;
				$scope.ThursdayTable = lsvwObject.tableObject;
				$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['class4','periodNumber4','subject4'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'friday') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.FridayShowObject;
				lsvwObject.curIndex = $scope.FridayCurIndex;
				lsvwObject.tableObject = $scope.FridayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.FridayShowObject = lsvwObject.tableShow;
				$scope.FridayCurIndex = lsvwObject.curIndex;
				$scope.FridayTable = lsvwObject.tableObject;
				$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
				//var SelectedArray= ['class5','periodNumber5','subject5'];
				//fnSelectRefresh(SelectedArray);
			}
		}

	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'monday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.MondayShowObject;
			lsvwObject.curIndex = $scope.MondayCurIndex;
			lsvwObject.tableObject = $scope.MondayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'tuesday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.TuesdayShowObject;
			lsvwObject.curIndex = $scope.TuesdayCurIndex;
			lsvwObject.tableObject = $scope.TuesdayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'wednesday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.WednesdayShowObject;
			lsvwObject.curIndex = $scope.WednesdayCurIndex;
			lsvwObject.tableObject = $scope.WednesdayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'thursday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.ThursdayShowObject;
			lsvwObject.curIndex = $scope.ThursdayCurIndex;
			lsvwObject.tableObject = $scope.ThursdayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'friday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.FridayShowObject;
			lsvwObject.curIndex = $scope.FridayCurIndex;
			lsvwObject.tableObject = $scope.FridayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'monday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.MondayShowObject;
			lsvwObject.curIndex = $scope.MondayCurIndex;
			lsvwObject.tableObject = $scope.MondayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);
		} else if (tableName == 'tuesday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.TuesdayShowObject;
			lsvwObject.curIndex = $scope.TuesdayCurIndex;
			lsvwObject.tableObject = $scope.TuesdayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);
		} else if (tableName == 'wednesday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.WednesdayShowObject;
			lsvwObject.curIndex = $scope.WednesdayCurIndex;
			lsvwObject.tableObject = $scope.WednesdayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);
		} else if (tableName == 'thursday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.ThursdayShowObject;
			lsvwObject.curIndex = $scope.ThursdayCurIndex;
			lsvwObject.tableObject = $scope.ThursdayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);
		} else if (tableName == 'friday') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.FridayShowObject;
			lsvwObject.curIndex = $scope.FridayCurIndex;
			lsvwObject.tableObject = $scope.FridayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);
		}
	};
	//Scope Level Single View functions Ends .
	

});
//Default Load Record Starts
$(document).ready(function () {
	MenuName = "TeacherTimeTable";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Teacher";
        selectBoxes= ['class','subject','periodNumber','hour','min'];
        fnGetSelectBoxdata(selectBoxes);
	
	day = "Mon";
  $("#mondayTab").on('show.bs.tab', function (e) {
	day = "Mon";
	});
   $("#tuesdayTab").on('show.bs.tab', function (e) {
	day = "Tue";
  });
  $("#wednesdayTab").on('show.bs.tab', function (e) {
	day = "Wed";
  });
	 $("#thursdayTab").on('show.bs.tab', function (e) {
	day = "Thu";
  });
	 $("#fridayTab").on('show.bs.tab', function (e) {
	day = "Fri";
  });
/*
  
  $('#periodNumber1Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('periodNumber1', $scope.periodNumberReadonly);
	});
	 $('#periodNumber2Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('periodNumber2', $scope.periodNumberReadonly);
	});
	 $('#periodNumber3Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('periodNumber3', $scope.periodNumberReadonly);
	});
	 $('#periodNumber4Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('periodNumber4', $scope.periodNumberReadonly);
	});
	 $('#periodNumber5Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('periodNumber5', $scope.periodNumberReadonly);
	});
	$('#subject1Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('subject1', $scope.subjectNamereadOnly);
	});
	$('#subject2Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('subject2', $scope.subjectNamereadOnly);
	});
	$('#subject3Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('subject3', $scope.subjectNamereadOnly);
	});
	$('#subject4Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('subject4', $scope.subjectNamereadOnly);
	});
	$('#subject5Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('subject5', $scope.subjectNamereadOnly);
	});
	$('#class1Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('class1', $scope.classReadonly);
	});
    $('#class2Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('class2', $scope.classReadonly);
	});
	$('#class3Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('class3', $scope.classReadonly);
	});
	$('#class4Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('class4', $scope.classReadonly);
	});
	$('#class5Button').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('class5', $scope.classReadonly);
	});
	$("#class1").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('class1');
		}
	});
	$("#class2").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('class2');
		}
	});
	$("#class3").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('class3');
		}
	});
	$("#class4").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('class4');
		}
	});
	$("#class5").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('class5');
		}
	});
	$("#periodNumber1").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('periodNumber1');
		}
	});
	$("#periodNumber2").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('periodNumber2');
		}
	});
	$("#periodNumber3").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('periodNumber3');
		}
	});
	$("#periodNumber4").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('periodNumber4');
		}
	});
	$("#periodNumber5").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('periodNumber5');
		}
	});
	$("#subject1").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('subject1');
		}
	});
	$("#subject2").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('subject2');
		}
	});
	$("#subject3").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('subject3');
		}
	});
	$("#subject4").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('subject4');
		}
	});
	$("#subject5").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('subject5');
		}
	});
  */
	var emptyTecherTimeTable = {
		teacherName: "",
		teacherID: "",
		timeTable: [{
				day: "",
				dayNumber: "",
				period:[{
				periodNumber:"Select option",
                                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				}]
		          }]
	};

	var dataModel = emptyTecherTimeTable;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if( $scope.teacherID!=null && $scope.teacherID!="")
        {
	dataModel.teacherID = $scope.teacherID;
	var response = fncallBackend('TeacherTimeTable', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
    }
});
//Default Load Record Ends


function fnTeacherTimeTablepostSelectBoxMaster()
{
     selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(Institute.ClassMaster.length>0 && Institute.PeriodMaster.length>0 && Institute.SubjectMaster.length>0){
    $scope.classes=Institute.ClassMaster;
    $scope.period = Institute.PeriodMaster;
    $scope.subjects = Institute.SubjectMaster;
      window.parent.fn_hide_parentspinner();
       $scope.$apply();
    }
    }
}

function fnshowSubScreen(teacherID)
{
    subScreen= true;
var emptyTecherTimeTable = {
		teacherName: "",
		teacherID: "",
		timeTable: [{
				day: "",
				dayNumber: "",
				period:[{
				periodNumber:"Select option",
                                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				}]
		          }]
	};
	var dataModel = emptyTecherTimeTable;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.teacherID!=null)
	dataModel.teacherID = $scope.teacherID;
	var response = fncallBackend('TeacherTimeTable', 'View', dataModel, [$scope.teacherID], $scope);
	return true;
}
//Cohesive Query Framework Starts
function fnTeacherTimeTableQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.timeTable = "";
	$scope.MondayTable ="";
	$scope.MondayRecord = "";
	$scope.MondayShowObject = false;
	$scope.TuesdayTable = "";
	$scope.TuesdayRecord = "";
	$scope.TuesdayShowObject = false;
	$scope.WednesdayTable = "";
	$scope.WednesdayRecord = "";
	$scope.WednesdayShowObject = false;
	$scope.ThursdayTable = "";
	$scope.ThursdayRecord = "";
	$scope.ThursdayShowObject = false;
	$scope.FridayTable = "";
	$scope.FridayRecord = "";
	$scope.FridayShowObject = false;
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.periodNumberReadonly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.audit = {};
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.svwAddDeteleDisable = true; // single View
	//Generic Field Ends
	return true;
}
//Cohesive query Framework Ends
function fnTeacherTimeTableView() {
	var emptyTecherTimeTable = {
		teacherName: "",
		teacherID: "",
		timeTable: [{
				day: "",
				dayNumber: "",
				period:[{
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				}]
		          }]
	};
	var dataModel = emptyTecherTimeTable;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if($scope.teacherID!=null && $scope.teacherID!='')
        {    
          dataModel.teacherID = $scope.teacherID;
	  var response = fncallBackend('TeacherTimeTable', 'View', dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
         }
        return true;
}
//Cohesive View Framework Ends
//Screen Specific Mandatory Validation Starts
//Screen Specific Mandatory Validation Starts
function fnTeacherTimeTableMandatoryCheck(operation) {
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
			if ($scope.MondayTable == null || $scope.MondayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Monday Time Table']);
				return false;
			}
			for (i = 0; i < $scope.MondayTable.length; i++) {
				if ($scope.MondayTable[i].class == '' || $scope.MondayTable[i].class == null || $scope.MondayTable[i].class == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Class in Monday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.MondayTable[i].periodNumber == '' || $scope.MondayTable[i].periodNumber == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Period Number in Monday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.MondayTable[i].subjectName == '' || $scope.MondayTable[i].subjectName == null || $scope.MondayTable[i].subjectName == "Select option") {
					fn_Show_Exception_With_Param('FE-VAL-001', ["subjectName in Monday Tab " + "record " + (i + 1)]);
					return false;
				}
			}
			if ($scope.TuesdayTable == null || $scope.TuesdayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Tuesday Time Table']);
				return false;
			}
			for (i = 0; i < $scope.TuesdayTable.length; i++) {
				if ($scope.TuesdayTable[i].class == '' || $scope.TuesdayTable[i].class == null || $scope.TuesdayTable[i].class == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Class in Tuesday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.TuesdayTable[i].periodNumber == '' || $scope.TuesdayTable[i].periodNumber == null || $scope.TuesdayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Period Number in Tuesday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.TuesdayTable[i].subjectName == '' || $scope.TuesdayTable[i].subjectName == null || $scope.TuesdayTable[i].subjectName == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["subjectName in Tuesday Tab " + "record " + (i + 1)]);
					return false;
				}
			}
			if ($scope.WednesdayTable == null || $scope.WednesdayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Wednesday Time Table']);
				return false;
			}
			for (i = 0; i < $scope.WednesdayTable.length; i++) {
				if ($scope.WednesdayTable[i].class == '' || $scope.WednesdayTable[i].class == null || $scope.WednesdayTable[i].class == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Class in Wednesday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.WednesdayTable[i].periodNumber == '' || $scope.WednesdayTable[i].periodNumber == null || $scope.WednesdayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Period Number in Wednesday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.WednesdayTable[i].subjectName == '' || $scope.WednesdayTable[i].subjectName == null || $scope.WednesdayTable[i].subjectName == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["subjectName in Wednesday Tab " + "record " + (i + 1)]);
					return false;
				}
			}
			
			if ($scope.ThursdayTable == null || $scope.ThursdayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Thursday Time Table']);
				return false;
			}
			for (i = 0; i < $scope.ThursdayTable.length; i++) {
				if ($scope.ThursdayTable[i].class == '' || $scope.ThursdayTable[i].class == null || $scope.ThursdayTable[i].class == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Class in Thursday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.ThursdayTable[i].periodNumber == '' || $scope.ThursdayTable[i].periodNumber == null || $scope.ThursdayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Period Number in Thursday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.ThursdayTable[i].subjectName == '' || $scope.ThursdayTable[i].subjectName == null || $scope.ThursdayTable[i].subjectName == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["subjectName in Thursday Tab " + "record " + (i + 1)]);
					return false;
				}
			}
		
			if ($scope.FridayTable == null || $scope.FridayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Friday Time Table']);
				return false;
			}
			for (i = 0; i < $scope.FridayTable.length; i++) {
				if ($scope.FridayTable[i].class == '' || $scope.FridayTable[i].class == null || $scope.FridayTable[i].class == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Class in Friday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.FridayTable[i].periodNumber == '' || $scope.FridayTable[i].periodNumber == null || $scope.FridayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["Period Number in Friday Tab " + "record " + (i + 1)]);
					return false;
				}
				if ($scope.FridayTable[i].subjectName == '' || $scope.FridayTable[i].subjectName == null || $scope.FridayTable[i].subjectName == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ["subjectName in Friday Tab " + "record " + (i + 1)]);
					return false;
			}
			}
			break;

        
	}
	return true;
}

//Screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnTeacherTimeTableDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;

		case 'Save':
			if (!fnDefaultTeacherId($scope))
				return false;

			break;


	}
	return true;
}

function fnDefaultTeacherId($scope) {
	var availabilty = false;
	return true;
}
//Screen Specific Default Validation Ends
//Cohesive Create Framework Starts
function fnTeacherTimeTableCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.teacherID = "";
	$scope.teacherName = "";
	$scope.teacherNamereadOnly = false;
        $scope.teacherNameSearchreadOnly = false;
	$scope.teacherIDreadOnly = false;
	$scope.classReadonly = false;
	$scope.subjectNamereadOnly = false;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.periodNumberReadonly = false;
	//Screen Specfic Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Creation';
	$scope.svwAddDeteleDisable = false; // single View
	//Generic Field Ends
	//Single View Starts
	$scope.MondayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};	
	$scope.MondayTable = null;
	$scope.MondayCurIndex = 0;
	$scope.MondayShowObject = false;

	$scope.TuesdayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.TuesdayTable = null;
	$scope.TuesdayCurIndex = 0;
	$scope.TuesdayShowObject = false;

	$scope.WednesdayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.WednesdayTable = null;
	$scope.WednesdayCurIndex = 0;
	$scope.WednesdayShowObject = false;

	$scope.ThursdayRecord = 
	{
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.ThursdayTable = null;
	$scope.ThursdayCurIndex = 0;
	$scope.ThursdayShowObject = false;

	$scope.FridayRecord = {
	            idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.FridayTable = null;
	$scope.FridayCurIndex = 0;
	$scope.FridayShowObject = false;
	// Single View Ends	
	return true;
}
//Cohesive Create Framework Ends
//Cohesive Edit Framework Starts
function fnTeacherTimeTableEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	//Generic Field Ends
	//Screen Specfic Scope Starts
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.classReadonly = false;
	$scope.subjectNamereadOnly = false;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.periodNumberReadonly = false;
	//Screen Specific Scope Endss
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.svwAddDeteleDisable = false; // single View	
	return true;
}
//Cohsive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnTeacherTimeTableDelete() {
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
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.periodNumberReadonly = true;
	//Screen Specific Scope Ends	
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnTeacherTimeTableAuth() {
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
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.periodNumberReadonly = true;
	//Screen Specific Scope Ends	
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnTeacherTimeTableReject() {
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
	$scope.teacherNamereadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.teacherIDreadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.periodNumberReadonly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnTeacherTimeTableBack() {
	
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		$scope.audit = {};
		$scope.teacherID = "";
		$scope.teacherName = "";
		$scope.MondayTable = "";
		$scope.MondayShowObject = "";
		$scope.TuesdayTable = "";
		$scope.TuesdayShowObject = "";
		$scope.WednesdayShowObject = "";
		$scope.WednesdayTable = "";
		$scope.ThursdayTable = "";
		$scope.ThursdayShowObject = "";
		$scope.FridayShowObject = "";
		$scope.FridayTable = "";
	}
	$scope.teacherNamereadOnly = true;
	$scope.teacherIDreadOnly = true;
        $scope.teacherNameSearchreadOnly = true;
	$scope.classReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
        $scope.periodNumberReadonly = true;
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
function fnTeacherTimeTableSave() {
	var emptyTecherTimeTable = {
		teacherName: "",
		teacherID: "",
		timeTable: [{
				day: "",
				dayNumber: "",
				period:[{
				periodNumber:"Select option",
                                subjectID:"Select option",
				class:"Select option",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				}]
		          }]
	};
	//Screen Specific dataModel Starts
	var dataModel = emptyTecherTimeTable;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel.teacherID = $scope.teacherID;
	dataModel.teacherName = $scope.teacherName;
	dataModel.timeTable = new Array();
	dataModel.timeTable[0]=new Object();
	dataModel.timeTable[0].day="Monday";
	dataModel.timeTable[0].dayNumber=1;
	dataModel.timeTable[0].period=$scope.MondayTable;
	dataModel.timeTable[1]=new Object();
	dataModel.timeTable[1].day="Tuesday";
	dataModel.timeTable[1].dayNumber=2;
	dataModel.timeTable[1].period=$scope.TuesdayTable;
	dataModel.timeTable[2]=new Object();
	dataModel.timeTable[2].day="Wednesday";
	dataModel.timeTable[2].dayNumber=3;
	dataModel.timeTable[2].period=$scope.WednesdayTable;
	dataModel.timeTable[3]=new Object();
	dataModel.timeTable[3].day="Thursday";
	dataModel.timeTable[3].dayNumber=4;
	dataModel.timeTable[3].period=$scope.ThursdayTable;
	dataModel.timeTable[4]=new Object();
	dataModel.timeTable[4].day="Friday";
	dataModel.timeTable[4].dayNumber=5;
	dataModel.timeTable[4].period=$scope.FridayTable;
	dataModel.audit = $scope.audit;
	//Screen Specific DataModel Ends
	var response = fncallBackend('TeacherTimeTable', parentOperation, dataModel, [{entityName:"teacherID",entityValue:$scope.teacherID}], $scope);
	
	return true;
}

function fnGetDayPeriodTable(timeTable,day){
			 var done= false;
			 var period;
			 timeTable.forEach(fnGet);
			 function fnGet(value,index,array){
				if(!done){ 				 
				if(value.day ==day){
				 period=value.period;
				 done=true;
				 
				                    }  
				}								
				
				                           }
return period;										   
}
//Cohesive Save framework Ends

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'MondayTable':
		
			var MondayTable = new Array();
			 responseObject.forEach(fnConvert1);
			 
			 function fnConvert1(value,index,array){
				     MondayTable[index] = new Object();
					 MondayTable[index].idx=index;
					 //MondayTable[index].day=value.day;
					 MondayTable[index].periodNumber=value.periodNumber;
					 MondayTable[index].subjectID=value.subjectID;
					 MondayTable[index].class=value.class;
					 MondayTable[index].startTime=value.startTime;
					 MondayTable[index].endTime=value.endTime;
					
                                         //MondayTable[index].MondayRecord=new Object();
					 /*MondayTable[index].startTime=new Object();
					 MondayTable[index].endTime=new Object();
					 MondayTable[index].startTime.hour= "";
					 MondayTable[index].startTime.min= "";
					 MondayTable[index].endTime.hour= "";
					 MondayTable[index].endTime.min= "";*/
					}
			return MondayTable;
			break ;
		    case 'TuesdayTable':
		
			var TuesdayTable = new Array();
			 responseObject.forEach(fnConvert2);
			 
			 
			 function fnConvert2(value,index,array){
				     TuesdayTable[index] = new Object();
					 TuesdayTable[index].idx=index;
					 //TuesdayTable[index].day=value.day;
					 TuesdayTable[index].periodNumber=value.periodNumber;
					 
					 TuesdayTable[index].subjectID=value.subjectID;
					 TuesdayTable[index].class=value.class;
					 TuesdayTable[index].startTime=value.startTime;
					 TuesdayTable[index].endTime=value.endTime;
					
                                        //TuesdayTable[index].TuesdayRecord =new Object();
					/* TuesdayTable[index].startTime=new Object();
					 TuesdayTable[index].endTime=new Object();
					 TuesdayTable[index].startTime.hour="";
					 TuesdayTable[index].startTime.min= "";
					 TuesdayTable[index].endTime.hour= "";
					 TuesdayTable[index].endTime.min= "";*/
					}
			return TuesdayTable;
			break ;
			 case 'WednesdayTable':
		
			var WednesdayTable = new Array();
			 responseObject.forEach(fnConvert3);
			 
			 
			 function fnConvert3(value,index,array){
				     WednesdayTable[index] = new Object();
					 WednesdayTable[index].idx=index;
					 //WednesdayTable[index].day=value.day;
					 WednesdayTable[index].periodNumber=value.periodNumber;
					 WednesdayTable[index].subjectID=value.subjectID;
					 WednesdayTable[index].class=value.class;
					 WednesdayTable[index].startTime=value.startTime;
                                         WednesdayTable[index].endTime=value.endTime;
					//WednesdayTable[index].WednesdayRecord=new Object();
					/* WednesdayTable[index].startTime=new Object();
					 WednesdayTable[index].endTime=new Object();
					 WednesdayTable[index].startTime.hour="";
					 WednesdayTable[index].startTime.min="";
					 WednesdayTable[index].endTime.hour= "";
					 WednesdayTable[index].endTime.min= "";*/
					}
			return WednesdayTable;
			break ;
			case 'ThursdayTable':
			var ThursdayTable = new Array();
			 responseObject.forEach(fnConvert4);
			 function fnConvert4(value,index,array){
				     ThursdayTable[index] = new Object();
					 ThursdayTable[index].idx=index;
					 //ThursdayTable[index].day=value.day;
					  ThursdayTable[index].class=value.class;
					 ThursdayTable[index].periodNumber=value.periodNumber;
					 ThursdayTable[index].subjectID=value.subjectID;
					 ThursdayTable[index].startTime=value.startTime;
                                         ThursdayTable[index].endTime=value.endTime;
					
                                        //ThursdayTable[index].ThursdayRecord= new Object();
					/* ThursdayTable[index].startTime=new Object();
					 ThursdayTable[index].endTime=new Object();
					 ThursdayTable[index].startTime.hour= "";
					 ThursdayTable[index].startTime.min= "";
					 ThursdayTable[index].endTime.hour= "";
					 ThursdayTable[index].endTime.min= "";*/
					}
			return ThursdayTable;
			break;
			case 'FridayTable':
			var FridayTable = new Array();
			 responseObject.forEach(fnConvert5);
			 function fnConvert5(value,index,array){
				     FridayTable[index] = new Object();
					 FridayTable[index].idx=index;
					 //FridayTable[index].day=value.day;
					 FridayTable[index].periodNumber=value.periodNumber;
					 FridayTable[index].subjectID=value.subjectName;
					 FridayTable[index].class=value.class;
					 FridayTable[index].startTime=value.startTime;
                                         FridayTable[index].endTime=value.endTime;
				
                                          //FridayTable[index].FridayRecord= new Object();
					/* FridayTable[index].startTime=new Object();
					 FridayTable[index].endTime=new Object();
					 FridayTable[index].startTime.hour="";
					 FridayTable[index].startTime.min= "";
					 FridayTable[index].endTime.hour= "";
					 FridayTable[index].endTime.min= "";*/
					}
			return FridayTable;
			break ;
			
		}
	}
	
	/*
function fnTeacherTimeTableSelectChooseEventHandler($scope,id){
switch(day){
case "Mon":
if (id == 'class1'){
$scope.MondayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
//fnClassSelectHandler($scope.MondayRecord.class,'Mon',$scope);
}
else if(id =='subject1'){
$scope.MondayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber1'){
$scope.MondayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.MondayRecord.periodNumber,'Mon',$scope);
}
break;
case "Tue":
if (id == 'class2'){
$scope.TuesdayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
//fnClassSelectHandler($scope.TuesdayRecord.class,'Tue',$scope);
}
else if(id =='subject2'){
$scope.TuesdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber2'){
$scope.TuesdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.TuesdayRecord.periodNumber,'Tue',$scope);
}
break;
case "Wed":
if (id == 'class3'){
$scope.WednesdayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
//fnClassSelectHandler($scope.WednesdayRecord.class,'Wed',$scope);
}
else if(id =='subject3'){
$scope.WednesdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber3'){
$scope.WednesdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.WednesdayRecord.periodNumber,'Wed',$scope);
}
break;
case "Thu":
if (id == 'class4'){
$scope.ThursdayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
//fnClassSelectHandler($scope.ThursdayRecord.class,'Thu',$scope);
}
else if(id =='subject4'){
$scope.ThursdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber4'){
$scope.ThursdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.ThursdayRecord.periodNumber,'Thu',$scope);
}
break;
case "Fri":
if (id == 'class5'){
$scope.FridayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
//fnClassSelectHandler($scope.FridayRecord.class,'Fri',$scope);
}
else if(id =='subject5'){
$scope.FridayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber5'){
$scope.FridayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.FridayRecord.periodNumber,'Fri',$scope);
break;
}

}
}	
*/
/*	
function fnClassSelectHandler(selectedClass,dayTab,$scope)
{
try{
	               // $scope.per=Institute.PeriodMaster.;
				   				Institute.PeriodMaster.forEach(function(value,index,array){
						                    
												 if(value.class ==selectedClass)
											 { 
										 switch(dayTab){
											 case 'Mon':
											   $scope.MondayRecord.period=value.period;
											   $scope.MondayRecord.periodNumber = "Select option";
											   break;
											   case 'Tue':
											   $scope.TuesdayRecord.period=value.period;
											   $scope.TuesdayRecord.periodNumber = "Select option";
											   break;
											   case 'Wed':
											   $scope.WednesdayRecord.period=value.period;
											   $scope.WednesdayRecord.periodNumber = "Select option";
											   break;
											   case 'Thu':
											   $scope.ThursdayRecord.period=value.period;
											   $scope.ThursdayRecord.periodNumber = "Select option";
											   break;
											   case 'Fri':
											   $scope.FridayRecord.period=value.period;
											   $scope.FridayRecord.periodNumber = "Select option";
							                   break;
										 }
											   throw "done";
											 }
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 }*/
				 
		 
function fnPeriodSelectHandler(selectedPeriod,dayTab,$scope)
{
try{
	               // $scope.per=Institute.PeriodMaster.;
				   			$scope.period.forEach(function(value,index,array){
						                    
												 if(value.PeriodNumber ==selectedPeriod)
											 { 
										 switch(dayTab){
											 case 'Mon':
											   $scope.MondayRecord.startTime.hour = value.StartTimeHour;
											   $scope.MondayRecord.startTime.min = value.StartTimeMin;
											   $scope.MondayRecord.endTime.hour = value.EndTimeHour;
											   $scope.MondayRecord.endTime.min = value.EndTimeMin;
											   break;
											   case 'Tue':
											   $scope.TuesdayRecord.startTime.hour = value.StartTimeHour;
											   $scope.TuesdayRecord.startTime.min = value.StartTimeMin;
											   $scope.TuesdayRecord.endTime.hour = value.EndTimeHour;
											   $scope.TuesdayRecord.endTime.min = value.EndTimeMin;
											   break;
											   case 'Wed':
											   $scope.WednesdayRecord.startTime.hour = value.StartTimeHour;
											   $scope.WednesdayRecord.startTime.min = value.StartTimeMin;
											   $scope.WednesdayRecord.endTime.hour = value.EndTimeHour;
											   $scope.WednesdayRecord.endTime.min = value.EndTimeMin;
											   break;
											   case 'Thu':
											   $scope.ThursdayRecord.startTime.hour = value.StartTimeHour;
											   $scope.ThursdayRecord.startTime.min = value.StartTimeMin;
											   $scope.ThursdayRecord.endTime.hour = value.EndTimeHour;
											   $scope.ThursdayRecord.endTime.min = value.EndTimeMin;
											   break;
											   case 'Fri':
											   $scope.FridayRecord.startTime.hour = value.StartTimeHour;
											   $scope.FridayRecord.startTime.min = value.StartTimeMin;
											   $scope.FridayRecord.endTime.hour = value.EndTimeHour;
											   $scope.FridayRecord.endTime.min = value.EndTimeMin;
							                   break;
										 }
											   throw "done";
											 }
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 }			 		 
				 /*
function fnTeacherTimeTableSelectRefresh($scope,id){
switch(day){
case "Mon":
if (id == 'class1'){
$('#' + id).val($scope.MondayRecord.class);
$('#' + id).selectmenu('refresh', true);
//fnClassRefresh($scope.MondayRecord.class,'Mon',$scope);
}
else if(id =='subject1'){
$('#' + id).val($scope.MondayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber1'){
$('#' + id).val($scope.MondayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
fnPeriodSelectHandler($scope.MondayRecord.periodNumber,'Mon',$scope);

}
break;
case "Tue":
if (id == 'class2'){
$('#' + id).val($scope.TuesdayRecord.class);
$('#' + id).selectmenu('refresh', true);
//fnClassRefresh($scope.TuesdayRecord.class,'Tue',$scope);
}
else if(id =='subject2'){
$('#' + id).val($scope.TuesdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber2'){
$('#' + id).val($scope.TuesdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
fnPeriodSelectHandler($scope.TuesdayRecord.periodNumber,'Tue',$scope);
}
break;
case "Wed":
if (id == 'class3'){
$('#' + id).val($scope.WednesdayRecord.class);
$('#' + id).selectmenu('refresh', true);
//fnClassRefresh($scope.WednesdayRecord.class,'Wed',$scope);
}
else if(id =='subject3'){
$('#' + id).val($scope.WednesdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber3'){
$('#' + id).val($scope.WednesdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
fnPeriodSelectHandler($scope.WednesdayRecord.periodNumber,'Wed',$scope);
}
break;
case "Thu":
if (id == 'class4'){
$('#' + id).val($scope.ThursdayRecord.class);
$('#' + id).selectmenu('refresh', true);
//fnClassRefresh($scope.ThursdayRecord.class,'Thu',$scope);
}
else if(id =='subject4'){
$('#' + id).val($scope.ThursdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber4'){
$('#' + id).val($scope.ThursdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
fnPeriodSelectHandler($scope.ThursdayRecord.periodNumber,'Thu',$scope);
}
break;
case "Fri":
if (id == 'class5'){
$('#' + id).val($scope.FridayRecord.class);
$('#' + id).selectmenu('refresh', true);
//fnClassRefresh($scope.FridayRecord.class,'Fri',$scope);

}
else if(id =='subject5'){
$('#' + id).val($scope.FridayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber5'){
$('#' + id).val($scope.FridayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
fnPeriodSelectHandler($scope.FridayRecord.periodNumber,'Fri',$scope);
}


}
}	
					 
function fnTeacherTimeTableResponseHandler($scope,id){
if (id == 'class1'){
$('#' + id).val($scope.MondayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject1'){
$('#' + id).val($scope.MondayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber1'){
$('#' + id).val($scope.MondayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class2'){
$('#' + id).val($scope.TuesdayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject2'){
$('#' + id).val($scope.TuesdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber2'){
$('#' + id).val($scope.TuesdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class3'){
$('#' + id).val($scope.WednesdayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject3'){
$('#' + id).val($scope.WednesdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber3'){
$('#' + id).val($scope.WednesdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class4'){
$('#' + id).val($scope.ThursdayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject4'){
$('#' + id).val($scope.ThursdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber4'){
$('#' + id).val($scope.ThursdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class5'){
$('#' + id).val($scope.FridayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject5'){
$('#' + id).val($scope.FridayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber5'){
$('#' + id).val($scope.FridayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
}				 	 
function fnTeacherTimeTableSelectEventHandle($scope,id){
if (id == 'class1'){
fnSelectEventHandle($scope.periodNumberReadonly, id);
}
else if(id =='subject1'){
$('#' + id).val($scope.MondayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber1'){
$('#' + id).val($scope.MondayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class2'){
$('#' + id).val($scope.TuesdayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject2'){
$('#' + id).val($scope.TuesdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber2'){
$('#' + id).val($scope.TuesdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class3'){
$('#' + id).val($scope.WednesdayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject3'){
$('#' + id).val($scope.WednesdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber3'){
$('#' + id).val($scope.WednesdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class4'){
$('#' + id).val($scope.ThursdayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject4'){
$('#' + id).val($scope.ThursdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber4'){
$('#' + id).val($scope.ThursdayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
else if (id == 'class5'){
$('#' + id).val($scope.FridayRecord.class);
$('#' + id).selectmenu('refresh', true);
}
else if(id =='subject5'){
$('#' + id).val($scope.FridayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber5'){
$('#' + id).val($scope.FridayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
}*/
/*
function fnClassViewHandler(){
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	       try {        // $scope.Class=Institute.StandardMaster;
					Institute.PeriodMaster.forEach(function(value,index,array){
						
						                     if(value.class ==$scope.MondayRecord.class)
											 { 
						                      $scope.MondayRecord.period=value.period;
											   /*if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											  /* throw "done";
											 }
											   });
								}
				        catch(e){if(e!="done") throw e;}
				 try {
				 Institute.PeriodMaster.forEach(function(value,index,array){
						
						                     if(value.class ==$scope.TuesdayRecord.class)
											 { 
						                       $scope.TuesdayRecord.period=value.period;
											  /* if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
										/*	   throw "done";
											 }
											   });
				 }
								
				        catch(e){if(e!="done") throw e;}
				 
				 try {
				  Institute.PeriodMaster.forEach(function(value,index,array){
						
						                     if(value.class ==$scope.WednesdayRecord.class)
											 { 
						                       $scope.WednesdayRecord.period=value.period;
											  /* if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
										/*	   throw "done";
											 }
											   });
				 }
								
				        catch(e){if(e!="done") throw e;}
				 	try {
				 Institute.PeriodMaster.forEach(function(value,index,array){
					
						                     if(value.class ==$scope.ThursdayRecord.class)
											 { 
						                       $scope.ThursdayRecord.period=value.period;
											  /* if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											/*   throw "done";
											 }
											   });
					}
				        catch(e){if(e!="done") throw e;}
				 try {
				 Institute.PeriodMaster.forEach(function(value,index,array){
						
						                     if(value.class ==$scope.FridayRecord.class)
											 { 
						                       $scope.FridayRecord.period=value.period;
											  /* if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											/*   throw "done";
											 }
											   });
				 }
								
				        catch(e){if(e!="done") throw e;}


}
*/
function fnPeriodViewHandler(){

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	try {
	               // $scope.Class=Institute.StandardMaster;
					$scope.period.forEach(function(value,index,array){
						
						                     if(value.PeriodNumber ==$scope.MondayRecord.periodNumber)
											 { 
						                       $scope.MondayRecord.startTime.hour = value.StartTimeHour;
											   $scope.MondayRecord.startTime.min = value.StartTimeMin;
											   $scope.MondayRecord.endTime.hour = value.EndTimeHour;
											   $scope.MondayRecord.endTime.min = value.EndTimeMin;
											  /* if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											  throw "done";
											 }
											   });
	}
				        catch(e){if(e!="done") throw e;}
				 
				 try {
				 $scope.period.forEach(function(value,index,array){
						
						                     if(value.PeriodNumber ==$scope.TuesdayRecord.periodNumber)
											 { 
						                       $scope.TuesdayRecord.startTime.hour = value.StartTimeHour;
											   $scope.TuesdayRecord.startTime.min = value.StartTimeMin;
											   $scope.TuesdayRecord.endTime.hour = value.EndTimeHour;
											   $scope.TuesdayRecord.endTime.min = value.EndTimeMin;
											   /*if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											  throw "done";
											 }
											   });
				 }
								
				        catch(e){if(e!="done") throw e;}
				 
				 	try {
				  $scope.period.forEach(function(value,index,array){
					
						                     if(value.PeriodNumber ==$scope.WednesdayRecord.periodNumber)
											 { 
						                       $scope.WednesdayRecord.startTime.hour = value.StartTimeHour;
											   $scope.WednesdayRecord.startTime.min = value.StartTimeMin;
											   $scope.WednesdayRecord.endTime.hour = value.EndTimeHour;
											   $scope.WednesdayRecord.endTime.min = value.EndTimeMin;
											   /*if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											   throw "done";
											 }
											   });
					}
				        catch(e){if(e!="done") throw e;}
				 
				 	try {
				 $scope.period.forEach(function(value,index,array){
					
						                     if(value.PeriodNumber ==$scope.ThursdayRecord.periodNumber)
											 { 
                                               $scope.ThursdayRecord.startTime.hour = value.StartTimeHour;
											   $scope.ThursdayRecord.startTime.min = value.StartTimeMin;
											   $scope.ThursdayRecord.endTime.hour = value.EndTimeHour;
											   $scope.ThursdayRecord.endTime.min = value.EndTimeMin;
											  /* if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											  throw "done";
											 }
											   });
								
								
					}
				        catch(e){if(e!="done") throw e;}
				 
				 try {
				 $scope.period.forEach(function(value,index,array){
						
						                     if(value.PeriodNumber ==$scope.FridayRecord.periodNumber)
											 { 
						                       $scope.FridayRecord.startTime.hour = value.StartTimeHour;
											   $scope.FridayRecord.startTime.min = value.StartTimeMin;
											   $scope.FridayRecord.endTime.hour = value.EndTimeHour;
											   $scope.FridayRecord.endTime.min = value.EndTimeMin;
											   /*if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }*/
											   throw "done";
											 }
											   });
				 }
				        catch(e){if(e!="done") throw e;}
				 

}
/*
function fnClassRefresh(selectedClass,dayTab,$scope)
{
try{
	               // $scope.per=Institute.PeriodMaster.;
				   				Institute.PeriodMaster.forEach(function(value,index,array){
						                    
												 if(value.class ==selectedClass)
											 { 
										 switch(dayTab){
											 case 'Mon':
											   $scope.MondayRecord.period=value.period;
											   break;
											   case 'Tue':
											   $scope.TuesdayRecord.period=value.period;
											   break;
											   case 'Wed':
											   $scope.WednesdayRecord.period=value.period;
											   break;
											   case 'Thu':
											   $scope.ThursdayRecord.period=value.period;
											   break;
											   case 'Fri':
											   $scope.FridayRecord.period=value.period;
							                   break;
										 }
											   throw "done";
											 }
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 }
	*/
/*
	function fnSelectChooseEvent(id) {
	if (!responseShow) {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		switch (id) {
			case 'periodNumber1':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);				
				break;
				case 'periodNumber2':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);				
				break;
				case 'periodNumber3':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);				
				break;
				case 'periodNumber4':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);				
				break;
				case 'periodNumber5':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);				
				break;
				
				case 'subject1':
				 var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);
				break;
				case 'subject2':
				 var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);
				break;
				case 'subject3':
				 var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);
				break;
				case 'subject4':
				 var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);
				break;
				case 'subject5':
				 var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);
				break;
				case 'class1':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
				case 'class2':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
				case 'class3':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
				case 'class4':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
				case 'class5':
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
		             }
				$scope.$apply();
				if (id=='class1' && MenuName=='TeacherTimeTable') {
				$('#periodNumber1').selectmenu('refresh', true);
				}
				else if (id=='class2' && MenuName=='TeacherTimeTable') {
				$('#periodNumber2').selectmenu('refresh', true);
				}
				else if (id=='class3' && MenuName=='TeacherTimeTable') {
				$('#periodNumber3').selectmenu('refresh', true);
				}
				else if (id=='class4' && MenuName=='TeacherTimeTable') {
				$('#periodNumber4').selectmenu('refresh', true);
				}
				else if (id=='class5' && MenuName=='TeacherTimeTable') {
				$('#periodNumber5').selectmenu('refresh', true);
				}
				}
				 else {
		responseShow = false;
	}

    }

	function fnSelectRefresh(id) {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();


	id.forEach(fnRefresh);

	function fnRefresh(value, index, array) {
		switch (value) {
				case 'subject1':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject2':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject3':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject4':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject5':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber1':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber2':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber3':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber4':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber5':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class1':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class2':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class3':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class4':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class5':
				responseShow = true;
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,value);	
				break;
		}
		}
	responseShow = false;
}

function fnSelectResponseHandler(id) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();


	id.forEach(fnResponseHandle);

	function fnResponseHandle(value, index, array) {
		switch (value) {
			case 'subject1':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject2':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject3':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject4':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'subject5':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber1':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber2':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber3':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber4':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'periodNumber5':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class1':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class2':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class3':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class4':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				case 'class5':
				responseShow = true;
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,value);	
				break;
				
				
		}
	}
	responseShow = false;
}

function fnSelectBoxEventHandler(id) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	id.forEach(fneventHandler);

	function fneventHandler(value, index, array) {

		switch (value) {
			case 'periodNumber1':
				fnSelectEventHandle($scope.periodNumberReadonly, value);
				break;
				case 'periodNumber2':
				fnSelectEventHandle($scope.periodNumberReadonly, value);
				break;
				case 'periodNumber3':
				fnSelectEventHandle($scope.periodNumberReadonly, value);
				break;
				case 'periodNumber4':
				fnSelectEventHandle($scope.periodNumberReadonly, value);
				break;
				case 'periodNumber5':
				fnSelectEventHandle($scope.periodNumberReadonly, value);
				break;
				case 'class1':
				fnSelectEventHandle($scope.classReadonly, value);
				break;
				case 'class2':
				fnSelectEventHandle($scope.classReadonly, value);
				break;
				case 'class3':
				fnSelectEventHandle($scope.classReadonly, value);
				break;
				case 'class4':
				fnSelectEventHandle($scope.classReadonly, value);
				break;
				case 'class5':
				fnSelectEventHandle($scope.classReadonly, value);
				break;
				case 'subject1':
				fnSelectEventHandle($scope.subjectNamereadOnly, value);
				break;
				case 'subject2':
				fnSelectEventHandle($scope.subjectNamereadOnly, value);
				break;
				case 'subject3':
				fnSelectEventHandle($scope.subjectNamereadOnly, value);
				break;
				case 'subject4':
				fnSelectEventHandle($scope.subjectNamereadOnly, value);
				break;
				case 'subject5':
				fnSelectEventHandle($scope.subjectNamereadOnly, value);
				break;
					}

	}
}
*/

function fnTeacherTimeTablepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
		// Specific Screen Scope Starts
		$scope.teacherNamereadOnly = true;
	        $scope.teacherIDreadOnly = true;
                $scope.teacherNameSearchreadOnly = true;
	        $scope.classReadonly = true;
	        $scope.subjectNamereadOnly = true;
	        $scope.startTimereadOnly = true;
	        $scope.endTimereadOnly = true;
                $scope.periodNumberReadonly = true;
		// Specific Screen Scope Ends
		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.svwAddDeteleDisable = false; // single View	
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.teacherName = "";
		$scope.teacherID  ="";
                $scope.MondayShowObject="";
                $scope.TuesdayShowObject="";
                $scope.WednesdayShowObject="";
                $scope.ThursdayShowObject="";
                $scope.FridayShowObject="";
                $scope.audit = {};
		 }
                else
                {
                     $scope.audit = response.audit;
 		//Screen Specific Response Scope Starts
		$scope.teacherName = response.body.teacherName;
		$scope.teacherID = response.body.teacherID;
		// single View Response Starts
		$scope.MondayTable =fnConvertmvw('MondayTable',fnGetDayPeriodTable(response.body.timeTable,'Mon'));
		$scope.MondayCurIndex = 0;
		if ($scope.MondayTable != null) {
			
			$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
			$scope.MondayShowObject = true;
		}
		$scope.TuesdayTable = fnConvertmvw('TuesdayTable',fnGetDayPeriodTable(response.body.timeTable,'Tue'));
		$scope.TuesdayCurIndex = 0;
		if ($scope.TuesdayTable != null) {
			$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
			$scope.TuesdayShowObject = true;
		}
		$scope.WednesdayTable = fnConvertmvw('WednesdayTable',fnGetDayPeriodTable(response.body.timeTable,'Wed'));
		$scope.WednesdayCurIndex = 0;
		if ($scope.WednesdayTable != null) {
			$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
			$scope.WednesdayShowObject = true;
		}
		$scope.ThursdayTable = fnConvertmvw('ThursdayTable',fnGetDayPeriodTable(response.body.timeTable,'Thu'));
		$scope.ThursdayCurIndex = 0;
		if ($scope.ThursdayTable != null) {
			$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
			$scope.ThursdayShowObject = true;
		}
		$scope.FridayTable = fnConvertmvw('FridayTable',fnGetDayPeriodTable(response.body.timeTable,'Fri'));
		$scope.FridayCurIndex = 0;
		if ($scope.FridayTable != null) {
			$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
			$scope.FridayShowObject = true;
		}
		// single View Response Ends
		//fnPeriodViewHandler();
          if (subScreen)
         {
          var $operationScope = angular.element(document.getElementById('operationsection')).scope();
	    $operationScope.fnPostdetailLoad();
            subScreen =false;
	 }
            }
		return true;

}

}