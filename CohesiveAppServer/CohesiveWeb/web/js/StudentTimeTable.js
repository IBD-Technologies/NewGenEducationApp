//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var tabClick = "General";
var selectBypassCount=0;
var day = "Mon";
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search', 'TableView']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer, TableViewCallService,OperationScopes) {
	//Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.searchShow = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.svwAddDeteleDisable = true; //Single View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.subjects = Institute.SubjectMaster;
	$scope.period = Institute.PeriodMaster;
	$scope.studentNamereadOnly = true;
        $scope.studentIDreadOnly = true;
          $scope.studentNameSearchreadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberReadonly = true;
	$scope.teacherNameReadonly = true;
	$scope.subjectNamereadOnly = true;
	$scope.class = 'Select option';
	//Screen Specific Scope Ends
	//Search Level Scope Starts
	/*$scope.StudentMaster = [{
		StudentId: "",
		StudentName: ""
	}];*/
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
	
	//Search Level Scope Ends
	//single View Starts
	$scope.MondayRecord =
	{    
	            periodNumber:"Select option",
                    subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.MondayTable = null;
	$scope.MondayCurIndex = 0;
	$scope.MondayShowObject = false;

	$scope.TuesdayRecord = 
	{           
	            periodNumber:"Select option",
                subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  	
	};
	$scope.TuesdayTable = null;
	$scope.TuesdayCurIndex = 0;
	$scope.TuesdayShowObject = false;

	$scope.WednesdayRecord = {
	            periodNumber:"Select option",
                subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.WednesdayTable = null;0
	$scope.WednesdayCurIndex = 0;
	$scope.WednesdayShowObject = false;

	$scope.ThursdayRecord = {
	            periodNumber:"Select option",
                subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	}
	$scope.ThursdayTable = null;
	$scope.ThursdayCurIndex = 0;
	$scope.ThursdayShowObject = false;

	$scope.FridayRecord = {
	            periodNumber:"Select option",
                subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	}
	$scope.FridayTable = null;
	$scope.FridayCurIndex = 0;
	$scope.FridayShowObject = false;
	
	//single View Ends
	//Scope Levle Single View functions starts 
	$scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'mondayTable') {
			if ($scope.MondayTable != null && $scope.MondayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.MondayCurIndex;
				lsvwObject.tableObject = $scope.MondayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.MondayCurIndex = lsvwObject.curIndex;
				$scope.MondayTable = lsvwObject.tableObject;
				$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
				//var SelectedArray= ['subject1','periodNumber1'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'tuesdayTable') {
			if ($scope.TuesdayTable != null && $scope.TuesdayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.TuesdayCurIndex;
				lsvwObject.tableObject = $scope.TuesdayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.TuesdayCurIndex = lsvwObject.curIndex;
				$scope.TuesdayTable = lsvwObject.tableObject;
				$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
				//var SelectedArray= ['subject2','periodNumber2'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'wednesdayTable') {
			if ($scope.WednesdayTable != null && $scope.WednesdayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.WednesdayCurIndex;
				lsvwObject.tableObject = $scope.WednesdayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.WednesdayCurIndex = lsvwObject.curIndex;
				$scope.WednesdayTable = lsvwObject.tableObject;
				$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
				//var SelectedArray= ['subject3','periodNumber3'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'thursdayTable') {
			if ($scope.ThursdayTable != null && $scope.ThursdayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.ThursdayCurIndex;
				lsvwObject.tableObject = $scope.ThursdayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.ThursdayCurIndex = lsvwObject.curIndex;
				$scope.ThursdayTable = lsvwObject.tableObject;
				$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['subject4','periodNumber4'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'fridayTable') {
			if ($scope.FridayTable != null && $scope.FridayTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.FridayCurIndex;
				lsvwObject.tableObject = $scope.FridayTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.FridayCurIndex = lsvwObject.curIndex;
				$scope.FridayTable = lsvwObject.tableObject;
				$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
				//var SelectedArray= ['subject5','periodNumber5'];
				//fnSelectRefresh(SelectedArray);
			}
		}

	};

	$scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'mondayTable') {
			if ($scope.MondayTable != null && $scope.MondayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.MondayCurIndex;
				lsvwObject.tableObject = $scope.MondayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.MondayCurIndex = lsvwObject.curIndex;
				$scope.MondayTable = lsvwObject.tableObject;
				$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
				//var SelectedArray= ['subject1','periodNumber1'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'tuesdayTable') {
			if ($scope.TuesdayTable != null && $scope.TuesdayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.TuesdayCurIndex;
				lsvwObject.tableObject = $scope.TuesdayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.TuesdayCurIndex = lsvwObject.curIndex;
				$scope.TuesdayTable = lsvwObject.tableObject;
				$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
				//var SelectedArray= ['subject2','periodNumber2'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'wednesdayTable') {
			if ($scope.WednesdayTable != null && $scope.WednesdayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.WednesdayCurIndex;
				lsvwObject.tableObject = $scope.WednesdayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.WednesdayCurIndex = lsvwObject.curIndex;
				$scope.WednesdayTable = lsvwObject.tableObject;
				$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
				//var SelectedArray= ['subject3','periodNumber3'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'thursdayTable') {
			if ($scope.ThursdayTable != null && $scope.ThursdayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.ThursdayCurIndex;
				lsvwObject.tableObject = $scope.ThursdayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.ThursdayCurIndex = lsvwObject.curIndex;
				$scope.ThursdayTable = lsvwObject.tableObject;
				$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['subject4','periodNumber4'];
				//fnSelectRefresh(SelectedArray);
			}
		} else if (tableName == 'fridayTable') {
			if ($scope.FridayTable != null && $scope.FridayTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.FridayCurIndex;
				lsvwObject.tableObject = $scope.FridayTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.FridayCurIndex = lsvwObject.curIndex;
				$scope.FridayTable = lsvwObject.tableObject;
				$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
				//var SelectedArray= ['subject5','periodNumber5'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};


	$scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'mondayTable') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
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
				//var SelectedArray= ['subject1','periodNumber1'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'tuesdayTable') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                              subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
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
				//var SelectedArray= ['subject2','periodNumber2'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'wednesdayTable') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                            subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
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
				//var SelectedArray= ['subject3','periodNumber3'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'thursdayTable') {
				emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                            subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
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
				$scope.ThursdayRecord = $scope.TuesdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['subject4','periodNumber4'];
				//fnSelectRefresh(SelectedArray);

			} else if (tableName == 'fridayTable') {
			   emptyTableRec = {
				idx:0,
				periodNumber:"Select option",
                subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
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
				//var SelectedArray= ['subject5','periodNumber5'];
				//fnSelectRefresh(SelectedArray);

			}


		}

	};
	$scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'mondayTable') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.MondayShowObject;
				lsvwObject.curIndex = $scope.MondayCurIndex;
				lsvwObject.tableObject = $scope.MondayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.MondayShowObject = lsvwObject.tableShow;
				$scope.MondayCurIndex = lsvwObject.curIndex;
				$scope.MondayTable = lsvwObject.tableObject;
				$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
				//var SelectedArray= ['subject1','periodNumber1'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'tuesdayTable') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.TuesdayShowObject;
				lsvwObject.curIndex = $scope.TuesdayCurIndex;
				lsvwObject.tableObject = $scope.TuesdayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.TuesdayShowObject = lsvwObject.tableShow;
				$scope.TuesdayCurIndex = lsvwObject.curIndex;
				$scope.TuesdayTable = lsvwObject.tableObject;
				$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
				//var SelectedArray= ['subject2','periodNumber2'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'wednesdayTable') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.WednesdayShowObject;
				lsvwObject.curIndex = $scope.WednesdayCurIndex;
				lsvwObject.tableObject = $scope.WednesdayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.WednesdayShowObject = lsvwObject.tableShow;
				$scope.WednesdayCurIndex = lsvwObject.curIndex;
				$scope.WednesdayTable = lsvwObject.tableObject;
				$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
				//var SelectedArray= ['subject3','periodNumber3'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'thursdayTable') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.ThursdayShowObject;
				lsvwObject.curIndex = $scope.ThursdayCurIndex;
				lsvwObject.tableObject = $scope.ThursdayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.ThursdayShowObject = lsvwObject.tableShow;
				$scope.ThursdayCurIndex = lsvwObject.curIndex;
				$scope.ThursdayTable = lsvwObject.tableObject;
				$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
				//var SelectedArray= ['subject4','periodNumber4'];
				//fnSelectRefresh(SelectedArray);
			} else if (tableName == 'fridayTable') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.FridayShowObject;
				lsvwObject.curIndex = $scope.FridayCurIndex;
				lsvwObject.tableObject = $scope.FridayTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.FridayShowObject = lsvwObject.tableShow;
				$scope.FridayCurIndex = lsvwObject.curIndex;
				$scope.FridayTable = lsvwObject.tableObject;
				$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
				//var SelectedArray= ['subject5','periodNumber5'];
				//fnSelectRefresh(SelectedArray);
			}
		}
	};

	$scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'mondayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.MondayShowObject;
			lsvwObject.curIndex = $scope.MondayCurIndex;
			lsvwObject.tableObject = $scope.MondayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} 
		  else if (tableName == 'tuesdayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.TuesdayShowObject;
			lsvwObject.curIndex = $scope.TuesdayCurIndex;
			lsvwObject.tableObject = $scope.TuesdayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
		   else if (tableName == 'wednesdayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.WednesdayShowObject;
			lsvwObject.curIndex = $scope.WednesdayCurIndex;
			lsvwObject.tableObject = $scope.WednesdayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'thursdayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.ThursdayShowObject;
			lsvwObject.curIndex = $scope.ThursdayCurIndex;
			lsvwObject.tableObject = $scope.ThursdayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		} else if (tableName == 'fridayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.FridayShowObject;
			lsvwObject.curIndex = $scope.FridayCurIndex;
			lsvwObject.tableObject = $scope.FridayTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
	};

	$scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'mondayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.MondayShowObject;
			lsvwObject.curIndex = $scope.MondayCurIndex;
			lsvwObject.tableObject = $scope.MondayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'tuesdayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.TuesdayShowObject;
			lsvwObject.curIndex = $scope.TuesdayCurIndex;
			lsvwObject.tableObject = $scope.TuesdayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'wednesdayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.WednesdayShowObject;
			lsvwObject.curIndex = $scope.WednesdayCurIndex;
			lsvwObject.tableObject = $scope.WednesdayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'thursdayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.ThursdayShowObject;
			lsvwObject.curIndex = $scope.ThursdayCurIndex;
			lsvwObject.tableObject = $scope.ThursdayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		} else if (tableName == 'fridayTable') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.FridayShowObject;
			lsvwObject.curIndex = $scope.FridayCurIndex;
			lsvwObject.tableObject = $scope.FridayTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		}
	};


});
//--------------------------------------------------------------------------------------------------------------

//Default Validation Starts
$(document).ready(function () {
	MenuName = "StudentTimeTable";
        selectBypassCount=0;
         window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="Student";
	//selectBoxes= ['subject1','subject2','subject3','subject4','subject5','periodNumber1','periodNumber2','periodNumber3','periodNumber4','periodNumber5'];
        selectBoxes= ['class','subject','periodNumber','hour','min'];
        fnGetSelectBoxdata(selectBoxes);
          tabClick = "General";
	$("#mondayTab").on('show.bs.tab', function (e) {
	day = "Mon";
	tabClick= "Monday";
	});
   $("#tuesdayTab").on('show.bs.tab', function (e) {
	day = "Tue";
	tabClick= "Tuesday";
	});
  $("#wednesdayTab").on('show.bs.tab', function (e) {
	day = "Wed";
	tabClick= "Wednesday";
	});
	 $("#thursdayTab").on('show.bs.tab', function (e) {
	day = "Thu";
	tabClick= "Thursday";
  });
	 $("#fridayTab").on('show.bs.tab', function (e) {
	day = "Fri";
	tabClick= "Friday";
  });
});

//Default Validation Ends

function fnStudentTimeTablepostSelectBoxMaster(){
     var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {  
    if(Institute.SubjectMaster.length>0 && Institute.PeriodMaster.length>0){
	 $scope.subjects = Institute.SubjectMaster;
	 $scope.period = Institute.PeriodMaster;
	  window.parent.fn_hide_parentspinner();
        if ((window.parent.StudenTimeTablekey.studentID !=null) && (window.parent.StudenTimeTablekey.studentID !=''))
	{
		 var studentID =window.parent.StudenTimeTablekey.studentID;
		 window.parent.StudentTimeTablekey.class =null;
		 fnshowSubScreen(studentID);
	}
        $scope.$apply()
}
    }
}
function fnshowSubScreen(studentID)
{
var emptyStudentTimeTable = {
        class: "Select option",
		timeTable: [{
				day: "",
				dayNumber: "",
				period:[{
				periodNumber:"Select option",
                                subjectName:"Select option",
			        teacherName: "",
			        teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				}]
		}]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentTimeTable;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        if($scope.studentID!=null)
	dataModel.studentID = $scope.studentID;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentTimeTable', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}

//Cohesive Query Framework Starts
function fnStudentTimeTableQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.studentName = "";
        $scope.studentID = "";
	$scope.studentNamereadOnly = false;
        $scope.studentIDreadOnly = false;
        $scope.studentNameSearchreadOnly = false;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberReadonly = true;
	$scope.teacherNameReadonly = true;
	$scope.subjectNamereadOnly = true;
	//Screen Specific Scope Ends
	//Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.shortNameReadonly = true;
	$scope.subReadonly = true;
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	  //Generic Field Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive Edit Framework Starts
function fnStudentTimeTableView() {
		var emptyStudentTimeTable = {
                studentID:"",
                studentName:"",
		timeTable: [{
				day: "",
				dayNumber: "",
				period:[{
				periodNumber:"Select option",
                            subjectID:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				}]
		}] 
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentTimeTable;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//        if($scope.studentID!=null && $scope.studentID!='')
//	{
         dataModel.studentID = $scope.studentID;
         dataModel.studentName = $scope.studentName;
	//Screen Specific DataModel Ends
	var response = fncallBackend('StudentTimeTable', 'View', dataModel, [{entityName:"studentID",entityValue:$scope.studentID}], $scope);
//        }
        return true;
}
//Cohesive view Framework Ends
//Screen Specific Mandatory Validation Starts
function fnStudentTimeTableMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
//                      if ($scope.studentID == '' || $scope.studentID == null) {
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
			if ($scope.MondayTable == null || $scope.MondayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Monday Time Table']);
				return false;
			}
			for (i = 0; i < $scope.MondayTable.length; i++) {
				if ($scope.MondayTable[i].periodNumber == '' || $scope.MondayTable[i].periodNumber == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Monday Period Number ' +  'record ' + (i+1)]);
					return false;
				}
				if ($scope.MondayTable[i].subjectName == '' || $scope.MondayTable[i].subjectName == null || $scope.MondayTable[i].subjectName == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Monday subjectName ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.MondayTable[i].teacherName == '' || $scope.MondayTable[i].teacherName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name ' + 'record ' + (i+1)]);
					return false;
				}
			}
			
			if ($scope.TuesdayTable == null || $scope.TuesdayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Tuesday TimeTable']);
				return false;
			}
			for (i = 0; i < $scope.TuesdayTable.length; i++) {
				if ($scope.TuesdayTable[i].periodNumber == '' || $scope.TuesdayTable[i].periodNumber == null || $scope.TuesdayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Period Number ' +  'record ' + (i+1)]);
					return false;
				}
				if ($scope.TuesdayTable[i].subjectName == '' || $scope.TuesdayTable[i].subjectName == null || $scope.TuesdayTable[i].subjectName == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['subjectName ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.TuesdayTable[i].teacherName == '' || $scope.TuesdayTable[i].teacherName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name ' + 'record ' + (i+1)]);
					return false;
				}
			}
			if ($scope.WednesdayTable == null || $scope.WednesdayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Wednesday Timetable']);
				return false;
			}
			for (i = 0; i < $scope.WednesdayTable.length; i++) {
				if ($scope.WednesdayTable[i].periodNumber == '' || $scope.WednesdayTable[i].periodNumber == null || $scope.WednesdayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Period Number ' +  'record ' + (i+1)]);
					return false;
				}
				if ($scope.WednesdayTable[i].subjectName == '' || $scope.WednesdayTable[i].subjectName == null || $scope.WednesdayTable[i].subjectName == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['subjectName ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.WednesdayTable[i].teacherName == '' || $scope.WednesdayTable[i].teacherName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name ' + 'record ' + (i+1)]);
					return false;
				}
			}
			if ($scope.ThursdayTable == null || $scope.ThursdayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam schedule']);
				return false;
			}
			for (i = 0; i < $scope.ThursdayTable.length; i++) {
				if ($scope.ThursdayTable[i].periodNumber == '' || $scope.ThursdayTable[i].periodNumber == null || $scope.ThursdayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Period Number ' +  'record ' + (i+1)]);
					return false;
				}
				if ($scope.ThursdayTable[i].subjectName == '' || $scope.ThursdayTable[i].subjectName == null || $scope.ThursdayTable[i].subjectName == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['subjectName ' + 'record ' + (i+1)]);
					return false;
				}
				if ($scope.ThursdayTable[i].teacherName == '' || $scope.ThursdayTable[i].teacherName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name ' + 'record ' + (i+1)]);
					return false;
				}
			}
			if ($scope.FridayTable == null || $scope.FridayTable.length == 0) {

				fn_Show_Exception_With_Param('FE-VAL-001', ['Exam schedule']);
				return false;
			}
			for (i = 0; i < $scope.FridayTable.length; i++) {
				if ($scope.FridayTable[i].periodNumber == '' || $scope.FridayTable[i].periodNumber == null || $scope.FridayTable[i].periodNumber == 'Select option') {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Period Number ' +  'record ' + (i+1)]);
					return false;
				}
				if ($scope.FridayTable[i].subjectName == '' || $scope.FridayTable[i].subjectName == null || $scope.FridayTable[i].subjectName == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['subjectName ' + 'record ' + (i+1)]);
					return false;
				}
					if ($scope.FridayTable[i].teacherName == '' || $scope.FridayTable[i].teacherName == null) {
					fn_Show_Exception_With_Param('FE-VAL-001', ['Teacher Name ' + 'record ' + (i+1)]);
					return false;
				}
			}

			break;


	}
	return true;
}
//Screen Specific Mandatory Validation Ends
//Screen Specific Default Validation Starts
function fnStudentTimeTableDefaultandValidate(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	switch (operation) {
		case 'View':
			//if (!fnDefaultTeacherId($scope))
				return true;

			break;

		case 'Save':
			//if (!fnDefaultTeacherId($scope))
				return true;

			break;


	}
	return true;
}
//Screen Specific Default Validation Ends
//Cohesive Query Framework Starts
function fnStudentTimeTableCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.studentName = '';
        $scope.studentID = '';
        $scope.studentNamereadOnly = false;
        $scope.studentIDreadOnly = false;
	$scope.studentNameSearchreadOnly = false;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberReadonly = false;
	$scope.teacherNameReadonly = false;
	$scope.subjectNamereadOnly = false;
	$scope.operation = 'Creation';
	//Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.svwAddDeteleDisable = false; //Single View
	//Generic Field Ends	
	
    //single View Starts
	$scope.MondayRecord =
	{           
	            periodNumber:"Select option",
                    subjectName:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.MondayTable = null;
	$scope.MondayCurIndex = 0;
	$scope.MondayShowObject = false;

	$scope.TuesdayRecord = 
	{    
	            periodNumber:"Select option",
                subjectName:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  	
	};
	$scope.TuesdayTable = null;
	$scope.TuesdayCurIndex = 0;
	$scope.TuesdayShowObject = false;

	$scope.WednesdayRecord = {
	            periodNumber:"Select option",
                subjectName:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	};
	$scope.WednesdayTable = null;
	$scope.WednesdayCurIndex = 0;
	$scope.WednesdayShowObject = false;

	$scope.ThursdayRecord = {
	            periodNumber:"Select option",
                subjectName:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	}
	$scope.ThursdayTable = null;
	$scope.ThursdayCurIndex = 0;
	$scope.ThursdayShowObject = false;

	$scope.FridayRecord = {
	            periodNumber:"Select option",
                subjectName:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
	}
	$scope.FridayTable = null;
	$scope.FridayCurIndex = 0;
	$scope.FridayShowObject = false;
	//single View Ends
	return true;
}
//Cohesive Query Framework Ends
//Cohesive Edit Framework Starts
function fnStudentTimeTableEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.svwAddDeteleDisable = false; //Single View
	//Generic Field Ends
	//Screen Specific Scope Starts
	$scope.studentNamereadOnly = false;
        $scope.studentIDreadOnly = false;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberReadonly = false;
	$scope.teacherNameReadonly = false;
	$scope.subjectNamereadOnly = false;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnStudentTimeTableDelete() {
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
        $scope.studentIDreadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberReadonly = true;
	$scope.teacherNameReadonly = true;
          $scope.studentNameSearchreadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends
//Cohesive Authorisation Framework Starts
function fnStudentTimeTableAuth() {

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
        $scope.studentIDreadOnly = true;
	$scope.startTimereadOnly = true;
	$scope.endTimereadOnly = true;
	$scope.periodNumberReadonly = true;
	$scope.teacherNameReadonly = true;
          $scope.studentNameSearchreadOnly = true;
	$scope.subjectNamereadOnly = true;
	//Screen Specific Scope Ends	
	return true;
}
//Cohesive Authorisation Framework Ends
//Cohesive Reject Framework Starts
function fnStudentTimeTableReject() {
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
        $scope.studentIDreadOnly = true;
          $scope.studentNameSearchreadOnly = true;
	$scope.startTimeNameReadonly = true;
	$scope.endTimeNameReadonly = true;
	$scope.periodNumberReadonly = true;
	$scope.teacherNameReadonly = true;
	$scope.subjectNamereadOnly = true;
	//Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends
//Cohesive Back Framework Starts
function fnStudentTimeTableBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	if ($scope.operation == 'Creation' || $scope.operation == 'View') {
		//Screen Specific Scope Starts
		$scope.audit = {};
		$scope.studentName = '';
                $scope.studentID = '';
		$scope.MondayShowObject = null;
		$scope.MondayTable = null;
		$scope.TuesdayShowObject = null;
		$scope.TuesdayTable = null;
		$scope.WednesdayShowObject = null;
		$scope.WednesdayTable = null;
		$scope.ThursdayShowObject = null;
		$scope.ThursdayTable = null;
		$scope.FridayShowObject = null;
		$scope.FridayTable = null;
	}
	        $scope.studentNamereadOnly = true;
                  $scope.studentNameSearchreadOnly = true;
                $scope.studentIDreadOnly = true;
		$scope.startTimereadOnly = true;
		$scope.endTimereadOnly = true;
		$scope.periodNumberReadonly = true;
		$scope.teacherNameReadonly = true;
		$scope.subjectNamereadOnly = true;
		//Generic Field Starts
		 $scope.operation = '';
		 $scope.mastershow = true;
	     $scope.detailshow = false;
               $scope.auditshow = false;
	     $scope.MakerRemarksReadonly = true;
	     $scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
}
//Cohesive SaveFramework Starts
function fnStudentTimeTableSave() {
	var emptyStudentTimeTable = {
        class:'Select option',
		timeTable: [{
				day: "",
				dayNumber: "",
				period:[{
				periodNumber:"Select option",
                subjectName:"Select option",
			    teacherName: "",
			    teacherID: "",
				startTime:{hour:"Hour",min:"Min"},
				endTime:{hour:"Hour",min:"Min"}  
				}]
		}]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyStudentTimeTable;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	dataModel.studentID = $scope.studentID;
        dataModel.studentName = $scope.studentName;
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
	var response = fncallBackend('StudentTimeTable', parentOperation, dataModel,[{entityName:"studentID",entityValue:$scope.studentID}], $scope);
	return true;
}
//Cohesive Saveframwork Ends

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
					 MondayTable[index].periodNumber=value.periodNumber;
					 MondayTable[index].subjectID=value.subjectID;
					 MondayTable[index].teacherName=value.teacherName;
					 MondayTable[index].teacherID=value.teacherID;
					 MondayTable[index].startTime=value.startTime;
					 MondayTable[index].endTime=value.endTime;
					 //MondayTable[index].startTime.hour="";
					 //MondayTable[index].startTime.min="";
					 //MondayTable[index].endTime.hour= "";
					 //MondayTable[index].endTime.min= "";
					}
			return MondayTable;
			break ;
		    case 'TuesdayTable':
		
			var TuesdayTable = new Array();
			 responseObject.forEach(fnConvert2);
			 
			 
			 function fnConvert2(value,index,array){
				     TuesdayTable[index] = new Object();
					 TuesdayTable[index].idx=index;
					 TuesdayTable[index].periodNumber=value.periodNumber;
					 TuesdayTable[index].subjectID=value.subjectID;
					 TuesdayTable[index].teacherName=value.teacherName;
					 TuesdayTable[index].teacherID=value.teacherID;
					 TuesdayTable[index].startTime=value.startTime;
					 TuesdayTable[index].endTime=value.endTime;
					 //TuesdayTable[index].startTime.hour= "";
					 //TuesdayTable[index].startTime.min= "";
					 //TuesdayTable[index].endTime.hour= "";
					 //TuesdayTable[index].endTime.min= "";
					}
			return TuesdayTable;
			break ;
			 case 'WednesdayTable':
		
			var WednesdayTable = new Array();
			 responseObject.forEach(fnConvert3);
			 
			 
			 function fnConvert3(value,index,array){
				     WednesdayTable[index] = new Object();
					 WednesdayTable[index].idx=index;
					 WednesdayTable[index].periodNumber=value.periodNumber;
					 WednesdayTable[index].subjectID=value.subjectID;
					 WednesdayTable[index].teacherName=value.teacherName;
					 WednesdayTable[index].teacherID=value.teacherID;
                                         WednesdayTable[index].startTime=value.startTime;
                                         WednesdayTable[index].endTime=value.endTime;
					/* WednesdayTable[index].startTime=new Object();
					 WednesdayTable[index].endTime=new Object();
					 WednesdayTable[index].startTime.hour= "";
					 WednesdayTable[index].startTime.min= "";
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
					 ThursdayTable[index].periodNumber=value.periodNumber;
					 ThursdayTable[index].subjectID=value.subjectID;
					 ThursdayTable[index].teacherName=value.teacherName;
					 ThursdayTable[index].teacherID=value.teacherID;
                                         ThursdayTable[index].startTime=value.startTime;
                                         ThursdayTable[index].endTime=value.endTime;
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
					 FridayTable[index].periodNumber=value.periodNumber;
					 FridayTable[index].subjectID=value.subjectID;
					 FridayTable[index].teacherName=value.teacherName;
					 FridayTable[index].teacherID=value.teacherID;
                                          FridayTable[index].startTime=value.startTime;
                                         FridayTable[index].endTime=value.endTime;
					 /*FridayTable[index].startTime=new Object();
					 FridayTable[index].endTime=new Object();
					 FridayTable[index].startTime.hour= "";
					 FridayTable[index].startTime.min= "";
					 FridayTable[index].endTime.hour= "";
					 FridayTable[index].endTime.min= "";*/
					}
			return FridayTable;
			break ;
			
		}
	}
	
	
	
	
function fnStudentTimeTableSelectChooseEventHandler($scope,id){
switch(day){
case "Mon":
if(id =='subject1'){
$scope.MondayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber1'){
$scope.MondayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.MondayRecord.periodNumber,'Mon',$scope);
}
break;
case "Tue":
 if(id =='subject2'){
$scope.TuesdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber2'){
$scope.TuesdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.TuesdayRecord.periodNumber,'Tue',$scope);
}
break;
case "Wed":
if(id =='subject3'){
$scope.WednesdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber3'){
$scope.WednesdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.WednesdayRecord.periodNumber,'Wed',$scope);
}
break;
case "Thu":
 if(id =='subject4'){
$scope.ThursdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber4'){
$scope.ThursdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.ThursdayRecord.periodNumber,'Thu',$scope);
}
break;
case "Fri":
if(id =='subject5'){
$scope.FridayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
}
else if(id == 'periodNumber5'){
$scope.FridayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
fnPeriodSelectHandler($scope.FridayRecord.periodNumber,'Fri',$scope);
break;
}


}
}	

				 
		 
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
				 
function fnStudentTimeTableSelectRefresh($scope,id){
switch(day){
case "Mon":
 if(id =='subject1'){
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
 if(id =='subject2'){
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
 if(id =='subject3'){
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
 if(id =='subject4'){
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
 if(id =='subject5'){
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
					 
function fnStudentTimeTableResponseHandler($scope,id){
if(id =='subject1'){
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
else if(id =='subject5'){
$('#' + id).val($scope.FridayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber5'){
$('#' + id).val($scope.FridayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
}				 	 
function fnStudentTimeTableSelectEventHandle($scope,id){
if(id =='subject1'){
$('#' + id).val($scope.MondayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber1'){
$('#' + id).val($scope.MondayRecord.periodNumber);
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
else if(id =='subject3'){
$('#' + id).val($scope.WednesdayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber3'){
$('#' + id).val($scope.WednesdayRecord.periodNumber);
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
else if(id =='subject5'){
$('#' + id).val($scope.FridayRecord.subjectName);
$('#' + id).selectmenu('refresh', true);
}
else if(id == 'periodNumber5'){
$('#' + id).val($scope.FridayRecord.periodNumber);
$('#' + id).selectmenu('refresh', true);
}
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
				case 'standardID':
                $scope.standard = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
                fnStdSelectHandler($scope);
				break;
                case 'Section':
                 $scope.section = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 break;
		             }
				$scope.$apply();
				if (id == 'standardID') {
			$('#Section').selectmenu('refresh', true);
		          }
				
				}
				 else {
		responseShow = false;
	}

    }
*/
/*
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
				case 'standardID':
				responseShow = true;
				$('#' + value).val($scope.standard);
				$('#' + value).selectmenu('refresh', true);
				break;
			    case 'Section':
				responseShow = true;
				$('#' + value).val($scope.section);
				$('#' + value).selectmenu('refresh', true);
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
				case 'standardID':
				responseShow = true;
				$('#' + value).val($scope.standard);
				$('#' + value).selectmenu('refresh', true);
				break;
			    case 'Section':
				responseShow = true;
				$('#' + value).val($scope.section);
				$('#' + value).selectmenu('refresh', true);
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
				case 'standardID':
				fnSelectEventHandle($scope.standardreadOnly, value);
				break;
				case 'Section':
				fnSelectEventHandle($scope.sectionreadOnly, value);
				break;
					}

	}
}
*/

function fnStudentTimeTablepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
		 $scope.studentNamereadOnly = true;
                 $scope.studentIDreadOnly = true;
                   $scope.studentNameSearchreadOnly = false;
		$scope.startTimereadOnly = true;
		$scope.endTimereadOnly = true;
		$scope.periodNumberReadonly = true;
		$scope.teacherNameReadonly = true;
		$scope.subjectNamereadOnly = true;
		// Specific Screen Scope Ends

		// Generic Field Starts
		$scope.mastershow = true;
		$scope.detailshow = false;
		$scope.auditshow = false;
		$scope.svwAddDeteleDisable = true; //Single View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
                $scope.studentName = "";
                $scope.studentID = "";
                $scope.MondayShowObject=null;
                $scope.TuesdayShowObject=null;
                $scope.WednesdayShowObject=null;
                $scope.ThursdayShowObject=null;
                $scope.FridayShowObject=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.audit = response.audit;
               		//Screen Specific Repsonse Scope Starts
		$scope.studentName = response.body.studentName;
                $scope.studentID = response.body.studentID;
		$scope.MondayTable =fnConvertmvw('MondayTable',fnGetDayPeriodTable(response.body.timeTable,'Mon'));
		$scope.MondayCurIndex = 0;
		if ($scope.MondayTable != null) {
			$scope.MondayRecord = $scope.MondayTable[$scope.MondayCurIndex];
			$scope.MondayShowObject = true;
		}
		$scope.TuesdayTable =fnConvertmvw('TuesdayTable',fnGetDayPeriodTable(response.body.timeTable,'Tue'));
		$scope.TuesdayCurIndex = 0;
		if ($scope.TuesdayTable != null) {
			$scope.TuesdayRecord = $scope.TuesdayTable[$scope.TuesdayCurIndex];
			$scope.TuesdayShowObject = true;
		}
		$scope.WednesdayTable =fnConvertmvw('WednesdayTable',fnGetDayPeriodTable(response.body.timeTable,'Wed'));
		$scope.WednesdayCurIndex = 0;
		if ($scope.WednesdayTable != null) {
			$scope.WednesdayRecord = $scope.WednesdayTable[$scope.WednesdayCurIndex];
			$scope.WednesdayShowObject = true;
		}
		$scope.ThursdayTable =fnConvertmvw('ThursdayTable',fnGetDayPeriodTable(response.body.timeTable,'Thu'));
		$scope.ThursdayCurIndex = 0;
		if ($scope.ThursdayTable != null) {
			$scope.ThursdayRecord = $scope.ThursdayTable[$scope.ThursdayCurIndex];
			$scope.ThursdayShowObject = true;
		}
		$scope.FridayTable =fnConvertmvw('FridayTable',fnGetDayPeriodTable(response.body.timeTable,'Fri'));
		$scope.FridayCurIndex = 0;
		if ($scope.FridayTable != null) {
			$scope.FridayRecord = $scope.FridayTable[$scope.FridayCurIndex];
			$scope.FridayShowObject = true;
		}
		//fnPeriodViewHandler();
                
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
