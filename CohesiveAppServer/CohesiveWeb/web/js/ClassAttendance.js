/* 
    Author     : IBD Technologies
	
*/
//------------------------------To Instantiate Angular App and controller--------------------------------------- 
var noon = "";
var subScreen = false;
var app = angular.module('SubScreen', ['BackEnd', 'operation', 'search','TableView']);
var selectBypassCount=0;
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer,TableViewCallService,OperationScopes) {
	// Generic Field Starts
	$scope.audit = {};
	$scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
        $scope.OperationScopes=OperationScopes;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = '';
	$scope.mvwAddDeteleDisable = true; // Multiple View
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.dateReadOnly = true;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", true );
	$scope.periodNumberReadOnly = true;
	$scope.attendanceReadOnly = true;
	$scope.class = "Select option";
	$scope.date = null;
	//Screen Specific Scope Ends 	
	//Multiple View Starts
	$scope.attendanceForeNoonCurPage = 0;
	$scope.attendanceForeNoonTable = null;
	$scope.attendanceForeNoonShowObject = null;
	
	$scope.attendanceAfternoonCurPage = 0;
	$scope.attendanceAfterNoonTable = null;
	$scope.attendanceAfterNoonShowObject = null;
	// Multiple View Ends
	//Multiple View Scope Function Starts 
	$scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'attendanceForeNoon') {
			if ($scope.attendanceForeNoonTable != null && $scope.attendanceForeNoonTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
			}
			
		}
		
		else if (tableName == 'attendanceAfterNoon') {
			if ($scope.attendanceAfterNoonTable != null && $scope.attendanceAfterNoonTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);
			}
			
		}


	};

	$scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'attendanceForeNoon') {
			if ($scope.attendanceForeNoonTable != null && $scope.attendanceForeNoonTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
			}
		}
		else if (tableName == 'attendanceAfterNoon') {
			if ($scope.attendanceAfterNoonTable != null && $scope.attendanceAfterNoonTable.length != 0) {
				var lsvwObject = new Object();

			
				lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;
				fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);
			}
		}
	};


	$scope.fnMvwAddRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'attendanceForeNoon') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					foreNoon:[{studentName:"",studentID:"",
		                    period:[{periodNumber:1,attendance:"-"},
				                    {periodNumber:2,attendance:"-" },
						            {periodNumber:3,attendance:"-"},
						            {periodNumber:4,attendance:"-"}]
		                   }],  
				};
				if ($scope.attendanceForeNoonTable == null)
					$scope.attendanceForeNoonTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;

			}
			
			else if (tableName == 'attendanceAfterNoon') {
				emptyTableRec = {
					idx: 0,
					checkBox: false,
					afterNoon:[{studentName:"",studentID:"",
		                    period:[{periodNumber:1,attendance:"-"},
				                    {periodNumber:2,attendance:"-" },
						            {periodNumber:3,attendance:"-"}]
		                   }],  
				};
				if ($scope.attendanceAfterNoonTable == null)
					$scope.attendanceAfterNoonTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;

			}
		}

	};
	$scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'attendanceForeNoon') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
				lsvwObject.tableObject = $scope.attendanceForeNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.attendanceForeNoonCurPage = lsvwObject.curPage;
				$scope.attendanceForeNoonTable = lsvwObject.tableObject;
				$scope.attendanceForeNoonShowObject = lsvwObject.screenShowObject;
			}
			else if (tableName == 'attendanceAfterNoon') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
				lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
				lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.attendanceAfternoonCurPage = lsvwObject.curPage;
				$scope.attendanceAfterNoonTable = lsvwObject.tableObject;
				$scope.attendanceAfterNoonShowObject = lsvwObject.screenShowObject;
			}
		}
	};

	$scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'attendanceForeNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
			lsvwObject.tableObject = $scope.attendanceForeNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		} 
		else if (tableName == 'attendanceAfterNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
			lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
	};

	$scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'attendanceForeNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceForeNoonCurPage;
			lsvwObject.tableObject = $scope.attendanceForeNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceForeNoonShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
		else if (tableName == 'attendanceAfterNoon') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.attendanceAfternoonCurPage;
			lsvwObject.tableObject = $scope.attendanceAfterNoonTable;
			lsvwObject.screenShowObject = $scope.attendanceAfterNoonShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
	};


    $scope.fnMvwGetCurPageTable = function (tableName)
	{
		if (tableName == 'attendanceForeNoon') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.attendanceForeNoonTable);
			
		}
		else if (tableName == 'attendanceAfterNoon') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.attendanceAfterNoonTable);
			
		}
	};
	//Multiple View Scope Function ends 


$scope.fnAttendanceButtonClick = function (id)
{
	
if (!($('#'+id).hasClass("btn-success") ||$('#'+id).hasClass("btn-warning1") ||$('#'+id).hasClass("btn-danger")))
{
	//$(id).removeClass("btn");
	//$('#'+id).addClass("btn-success");
    if (noon=="F"){
	fnSetAttendance(id,'P',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'P',$scope.attendanceAfterNoonShowObject);
	}
	return;
}
if ($('#'+id).hasClass("btn-success"))
{
	//$('#'+id).removeClass("btn-success");
	//$('#'+id).addClass("btn-warning1");
	if (noon=="F"){
	fnSetAttendance(id,'L',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'L',$scope.attendanceAfterNoonShowObject);
	}
	return;
}
if ($('#'+id).hasClass("btn-warning1"))
{
	//$('#'+id).removeClass("btn-warning1");
	//$('#'+id).addClass("btn-danger");
	if (noon=="F"){
	fnSetAttendance(id,'A',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'A',$scope.attendanceAfterNoonShowObject);
	}
	return;
}
if ($('#'+id).hasClass("btn-danger"))
{
	//$('#'+id).removeClass("btn-danger");
	//$(id).addClass("btn");
	if (noon=="F"){
	fnSetAttendance(id,'-',$scope.attendanceForeNoonShowObject);
	}
	else if (noon == "A"){
	fnSetAttendance(id,'-',$scope.attendanceAfterNoonShowObject);
	}
	return;
}	

}
	
});
 function fnSetAttendance(id,attendance,attendanceObject){
	 
	 var ID = id.split("-");
	 
	 attendanceObject.forEach(function(value,index,array){
		if(ID[0] == value.studentID)
		{
		value.period.forEach(function(value,index,array){
		    if (index==ID[1])
			{	
			 value.attendance=attendance;
			 switch(attendance)
			 {
				 case "P":
			     value.attendenceButtonClass='btn-success';
				 break;
				 case "L":
			     value.attendenceButtonClass='btn-warning1';
				 break;
				 case "A":
			     value.attendenceButtonClass='btn-danger';
				 break;
				 case "-":
			     value.attendenceButtonClass='';
				 break;
			 }
			 return;
			}
		});
        }
		return;
	 });
 }


// Screen Specific Default Load Starts	
$(document).ready(function () {
	MenuName = "ClassAttendance";
        selectBypassCount=0;
        window.parent.nokotser=$("#nokotser").val();
        window.parent.Entity="ClassEntity";
	fnDatePickersetDefault('AttendanceDate',fndateEventHandler);
	fnsetDateScope();
        
	selectBoxes= ['class'];
        fnGetSelectBoxdata(selectBoxes);
	noon = "";
  $("#foreNoonTab").on('show.bs.tab', function (e) {
  //e.target // newly activated tab
  //e.relatedTarget // previous active tab
	// screen Specific DataModel Ends
	noon = "F";
   //fnAttendanceDefault();
  });
   $("#afterNoonTab").on('show.bs.tab', function (e) {
  //e.target // newly activated tab
  //e.relatedTarget // previous active tab
	// screen Specific DataModel Ends
	noon = "A";
   //fnAttendanceDefault();
  });
  
  
        
      
       
 
});


function fnClassAttendancepostSelectBoxMaster()
{
    selectBypassCount=selectBypassCount+1;
    if (selectBypassCount==selectBoxes.length)
    {
    
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
      if(Institute.ClassMaster.length>0)
      {    
        
       
         
            $scope.classes=Institute.ClassMaster;
	 window.parent.fn_hide_parentspinner();
    
   if ((window.parent.ClassAttendancekey.class !=null && window.parent.ClassAttendancekey.class !='')&& (window.parent.ClassAttendancekey.date !=null && window.parent.ClassAttendancekey.date !=''))	
  {
		var date=window.parent.ClassAttendancekey.date;
		 window.parent.ClassAttendancekey.date =null;
                 var Class=window.parent.ClassAttendancekey.class;
		 window.parent.ClassAttendancekey.class =null;
		
		  fnshowSubScreen(date,Class);
		
	} 
    $scope.$apply();
    
        }
}
}
function fnClassAttendanceDetailClick($scope){
	// var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    noon='F';
	if (($scope.attendanceForeNoonTable ==null )  && parentOperation=='Create')
	{	
	var emptyClassAttendance = {
		class: 'Select option',
		date: null,
				foreNoon:[{studentName:"",studentID:"",
		                    period:[{periodNumber:"",attendance:"-"}],
		                   }], 
					afterNoon:[{studentName:"",studentID:"",
		                     period:[{periodNumber: "",attendance:"-"}],
		                   }]
		/*audit: {},
		error: {}*/
	};
	var dataModel = emptyClassAttendance;
        if($scope.class!=null)
	dataModel.class = $scope.class;
     if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
                                $scope.mastershow = true;
	                        $scope.detailshow = false;
	                        $scope.auditshow = false;
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
                        
         if($scope.date!=null)
	dataModel.date = $scope.date;
     if ($scope.date == '' || $scope.date == null ) {
                                $scope.mastershow = true;
	                        $scope.detailshow = false;
	                        $scope.auditshow = false;
				fn_Show_Exception_With_Param('FE-VAL-001', ['Date']);
				return false;
			}               
	var response = fncallBackend('ClassAttendance', 'Create-Default', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
	
	    //Multiple View Response Starts 
//		$scope.attendanceForeNoonTable = fnConvertmvw('attendanceForeNoonTable',response.body.foreNoon);
//		$scope.attendanceForeNoonCurPage = 1
//		$scope.attendanceForeNoonShowObject=$scope.fnMvwGetCurPageTable('attendanceForeNoon');
//		
//		$scope.attendanceAfterNoonTable = fnConvertmvw('attendanceAfterNoonTable',response.body.afterNoon);
//		$scope.attendanceAfternoonCurPage = 1
//		$scope.attendanceAfterNoonShowObject=$scope.fnMvwGetCurPageTable('attendanceAfterNoon');
		//Multiple View Response Ends
//               fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
//	       fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);		
	    // $scope.$apply();
	 
	 }	

return true;
}
function fnAttendanceButtonClrChange(attendanceObject){
	    attendanceObject.forEach(function(value,index,array){
		var studentID = value.studentID; 
		value.period.forEach(function(value,index,array){
         
		 switch(value.attendance){
		 case " ":
	       //$('#'+studentID+'-'+index).removeClass("btn-success");
		   //$('#'+studentID+'-'+index).removeClass("btn-warning1");
		   //$('#'+studentID+'-'+index).removeClass("btn-danger");
		  // $('#'+studentID+index).addClass("btn-secondary");
		     value.attendenceButtonClass ='';
		 break; 
		 case "P":
		   //$('#'+studentID+index).removeClass("btn-secondary");
		   //$('#'+studentID+'-'+index).removeClass("btn-warning1");
		   //$('#'+studentID+'-'+index).removeClass("btn-danger");
		   //$('#'+studentID+'-'+index).addClass("btn-success");
		    value.attendenceButtonClass ='btn-success';
		   
		   
		 break;
		 case "L":
	   	  // $('#'+studentID+index).removeClass("btn-secondary");
                   //$('#'+studentID+'-'+index).removeClass("btn-warning1");
		   //$('#'+studentID+'-'+index).removeClass("btn-danger");
		   //$('#'+studentID+'-'+index).addClass("btn-warning1");
		    value.attendenceButtonClass ='btn-warning1';
		 break;
		case "A":
		  // $('#'+studentID+index).removeClass("btn-secondary");
		   //$('#'+studentID+'-'+index).removeClass("btn-warning1");
		   //$('#'+studentID+'-'+index).removeClass("btn-success");
		   //$('#'+studentID+'-'+index).addClass("btn-danger");
		     value.attendenceButtonClass ='btn-danger';
		   
		 break;
	     }	
     });
	});
	
}
	
// Screen Specific Load Ends
function fnshowSubScreen(date,Class)
{
subScreen=true;
var emptyClassAttendance = {
		class: 'Select option',
		date: null,
				foreNoon:[{studentName:"",studentID:"",
		                    period:[{periodNumber:1,attendance:"-"},
				                    {periodNumber:2,attendance:"-" },
						            {periodNumber:3,attendance:"-"},
						            {periodNumber:4,attendance:"-"}]
		                   }], 
					afterNoon:[{studentName:"",studentID:"",
		                     period:[{periodNumber: 1,attendance:"-"},
				                    {periodNumber: 2,attendance:"-"},
						            {periodNumber: 3,attendance:"-"}]
		                   }]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyClassAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//$scope.class=class;
	$scope.date=date;
        $scope.class=Class;
	if($scope.class!=null&& $scope.class!=""){
            dataModel.class = $scope.class;
          if($scope.date!=null&&$scope.date!=""){
             dataModel.date = $scope.date;
	// screen Specific DataModel Ends
	   var response = fncallBackend('ClassAttendance', 'View', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
	
        }
        
    }
        
	return true;
}

// Cohesive Query Framework Starts
function fnClassAttendanceQuery() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific Scope Starts
	$scope.class = 'Select option';
	$scope.date = "";
	$scope.foreNoon = null;
	$scope.afterNoon = null;
	$scope.classReadonly = false;
	$scope.dateReadOnly = false;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", false );
	$scope.startTimeReadOnly = false;
	$scope.endTimeReadOnly = false;
	$scope.periodNumberReadOnly = false;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.operation = 'View';
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.audit = {};
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends	
	//Multiple View Starts
	$scope.attendanceForeNoonCurPage = 0;
	$scope.attendanceForeNoonTable = null;
	$scope.attendanceForeNoonShowObject = null;
	
	$scope.attendanceAfternoonCurPage = 0;
	$scope.attendanceAfterNoonTable = null;
	$scope.attendanceAfterNoonShowObject = null;
	// Multiple View Ends
	
	return true;
}
// Cohesive Query Framework Ends
//  Cohesive View Framework Starts	
function fnClassAttendanceView() {
	var emptyClassAttendance = {
		class: 'Select option',
		date: "",
				foreNoon:[{studentName:"",studentID:"",
		                    period:[{periodNumber:1,attendance:"-"},
				                    {periodNumber:2,attendance:"-" },
						            {periodNumber:3,attendance:"-"},
						            {periodNumber:4,attendance:"-"}]
		                   }], 
					afterNoon:[{studentName:"",studentID:"",
		                     period:[{periodNumber: 1,attendance:"-"},
				                    {periodNumber: 2,attendance:"-"},
						            {periodNumber: 3,attendance:"-"}]
		                   }]
	};
	//Screen Specific DataModel Starts
	var dataModel = emptyClassAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        
         if($scope.class!=null&& $scope.class!=""){
            dataModel.class = $scope.class;
          if($scope.date!=null&&$scope.date!=""){
             dataModel.date = $scope.date;   
            
	     // screen Specific DataModel Ends
        	var response = fncallBackend('ClassAttendance', 'View', dataModel,[{entityName:"class",entityValue:$scope.class}],$scope);
	
        }
    }
    
    return true;
        
}
// Cohesive Query Framework Starts
// Screen Specific Mandatory Validation Starts      
function fnClassAttendanceMandatoryCheck(operation) {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

	switch (operation) {
		case 'View':
			if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
			}
			if ($scope.date == '' || $scope.date == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Date']);
				return false;
			}
			break;

		case 'Save':
			if ($scope.class == '' || $scope.class == null || $scope.class == 'Select option') {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
				return false;
                            }
			if ($scope.date == '' || $scope.date == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['Date']);
				return false;
			}
			break;


	}
	return true;
}
// Screen Specific Mandatory Validation Ends
// Cohesive Create Framework Starts
function fnClassAttendanceCreate() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	//Screen Specific Scope Starts
	$scope.class = 'Select option';
//	$scope.date = "";
//        $scope.attendanceForeNoonTable=null;
//        $scope.attendanceAfterNoonTable = null;
        $scope.attendanceForeNoonCurPage = 0;
	$scope.attendanceForeNoonTable = null;
	$scope.attendanceForeNoonShowObject = null;
	
	$scope.attendanceAfternoonCurPage = 0;
	$scope.attendanceAfterNoonTable = null;
	$scope.attendanceAfterNoonShowObject = null;
	$scope.foreNoon = null;
	$scope.afterNoon = null;
	$scope.classReadonly = false;
	$scope.dateReadOnly = false;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", false );
	$scope.startTimeReadOnly = false;
	$scope.endTimeReadOnly = false;
        $scope.periodNumberReadOnly = false;
	$scope.attendanceReadOnly = false;
	// Screen Specific Scope Ends
	// Generic Field Starts
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.operation = 'Creation';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	
	return true;
}
// Cohesive Screen create Framework Ends

// Cohesive Edit Framework Starts
function fnClassAttendanceEdit() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope()
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Modification';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.dateReadOnly = true;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", true );
	$scope.startTimeReadOnly = false;
	$scope.endTimeReadOnly = false;
        $scope.periodNumberReadOnly = false;
	$scope.attendanceReadOnly = false;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Edit Framework Ends
//Cohesive Delete Framework Starts
function fnClassAttendanceDelete() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = false;
	$scope.CheckerRemarksReadonly = true;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Deletion';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.dateReadOnly = true;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", true );
	$scope.startTimeReadOnly = true;
	$scope.endTimeReadOnly = true;
        $scope.periodNumberReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Delete Framework Ends


//Cohesive Authorisation Framework Starts
function fnClassAttendanceAuth() {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Authorisation';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.dateReadOnly = true;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", true );
	$scope.startTimeReadOnly = true;
	$scope.endTimeReadOnly = true;
        $scope.periodNumberReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Authorisation Framework Ends


//Cohesive Reject Framework Starts
function fnClassAttendanceReject() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Generic Field Starts
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = false;
	$scope.mastershow = true;
	$scope.detailshow = false;
	$scope.auditshow = false;
	$scope.operation = 'Rejection';
	$scope.mvwAddDeteleDisable = true; //Multiple View
	// Generic Field Ends
	// Screen Specific Scope Starts
	$scope.classReadonly = true;
	$scope.dateReadOnly = true;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", true );
	$scope.startTimeReadOnly = true;
	$scope.endTimeReadOnly = true;
        $scope.periodNumberReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	return true;
}
//Cohesive Reject Framework Ends

//Cohesive Back Framework Starts
function fnClassAttendanceBack() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();	
	// Screen Specific Scope Starts
	if ($scope.operation=='Creation' || $scope.operation =='View')
	{
	$scope.audit = {};
	$scope.class = 'Select option';
	$scope.date = "";
	$scope.attendanceForeNoonTable=null;
	$scope.attendanceForeNoonShowObject=null;
	$scope.attendanceAfterNoonTable=null;
	$scope.attendanceAfterNoonShowObject=null;
	}
	$scope.classReadonly = true;
	$scope.dateReadOnly = true;
        $( "#AttendanceDate" ).datepicker( "option", "disabled", true );
	$scope.startTimeReadOnly = true;
	$scope.endTimeReadOnly = true;
        $scope.periodNumberReadOnly = true;
	$scope.attendanceReadOnly = true;
	// Screen Specific Scope Ends
	//Generic Field Starts
	$scope.operation = '';
	$scope.mastershow = true;
	$scope.detailshow = false;
        $scope.auditshow = false;
	$scope.MakerRemarksReadonly = true;
	$scope.CheckerRemarksReadonly = true;
	//Generic Field Ends
}
// Cohesive Back Framework Ends
//Cohesive save Framework Starts
function fnClassAttendanceSave() {
		var emptyClassAttendance = {
		class: 'Select option',
		date: "",
				foreNoon:[{studentName:"",studentID:"",
		                    period:[{periodNumber:"",attendance:"-"}]
		                   }], 
					afterNoon:[]
	};
	var dataModel = emptyClassAttendance;
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	// Screen Specific DataModel Starts
        if($scope.class!=null)
	dataModel.class = $scope.class;
        if($scope.date!=null)
	dataModel.date = $scope.date;
        if($scope.attendanceForeNoonTable!=null)
	dataModel.foreNoon = $scope.attendanceForeNoonTable;
        if($scope.attendanceAfterNoonTable!=null)
	dataModel.afterNoon = $scope.attendanceAfterNoonTable;
	// Screen Specific DataModel Ends
	var response = fncallBackend('ClassAttendance', parentOperation, dataModel, [{entityName:"class",entityValue:$scope.class}], $scope);
	return true;
}
//Cohesive save Framework Ends

function fndateEventHandler() {
	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#AttendanceDate").datepicker("getDate"));
		$scope.$apply();
}
function fnsetDateScope()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	$scope.date = $.datepicker.formatDate('dd-mm-yy', $("#AttendanceDate").datepicker("getDate"));
	
		$scope.$apply();
}	
function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'attendanceForeNoonTable':
		   
			var attendanceForeNoonTable = new Array();
			 responseObject.forEach(fnConvert1);
			 
			 
			 function fnConvert1(value,index,array){
				     attendanceForeNoonTable[index] = new Object();
					 attendanceForeNoonTable[index].idx=index;
					 attendanceForeNoonTable[index].checkBox=false;
					 attendanceForeNoonTable[index].studentID=value.studentID;
					 attendanceForeNoonTable[index].studentName=value.studentName;
					 attendanceForeNoonTable[index].period=value.period;
					}
			return attendanceForeNoonTable;
			break ;
			case 'attendanceAfterNoonTable':
		   
			var attendanceAfterNoonTable = new Array();
			 responseObject.forEach(fnConvert2);
			 function fnConvert2(value,index,array){
				     attendanceAfterNoonTable[index] = new Object();
					 attendanceAfterNoonTable[index].idx=index;
					 attendanceAfterNoonTable[index].checkBox=false;
					 attendanceAfterNoonTable[index].studentID=value.studentID;
					 attendanceAfterNoonTable[index].studentName=value.studentName;
					 attendanceAfterNoonTable[index].period=value.period;
					}
			return attendanceAfterNoonTable;
			break ;
		}
	}




function fnClassAttendancepostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

     if (response.header.status == 'success') {
            
            
		// Specific Screen Scope Starts
                 $scope.MakerRemarksReadonly = true;
	         $scope.CheckerRemarksReadonly = true;
		 $scope.classReadonly = true;
	         $scope.dateReadOnly = true;
                 $( "#AttendanceDate" ).datepicker( "option", "disabled", true );
	         $scope.startTimeReadOnly = true;
	         $scope.endTimeReadOnly = true;
                 $scope.periodNumberReadOnly = true;
                 
                 if(response.header.operation=="Create-Default"){
                 
	         $scope.attendanceReadOnly = false;
                 $scope.mastershow = false;
	         $scope.detailshow = true;
	         $scope.auditshow = false;
                }else{
                    
                    $scope.attendanceReadOnly = true;
                    $scope.mastershow = true;
		    $scope.detailshow = false;
		    $scope.auditshow = false;
                }
                 
		// Specific Screen Scope Ends

		// Generic Field Starts
		
		$scope.mvwAddDeteleDisable = true; //Multiple View
		// Generic Field Ends
		// Specific Screen Scope Response Starts	
		if(parentOperation=="Delete")
                {
//                $scope.instituteID = "";
//		$scope.instituteName ="";
//		$scope.profileImgPath ="";
                $scope.class = "";
                $scope.date = "";
		$scope.foreNoon ={};
		$scope.afterNoon ={};
                $scope.attendanceForeNoonShowObject=null;
                $scope.notificationMasterShowObject=null;
                $scope.subjectMasterShowObject=null;
                $scope.attendanceAfterNoonShowObject=null;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = {};
		 }
                else
                {
                $scope.class = response.body.class;
                $scope.date = response.body.date;
		$scope.foreNoon = response.body.foreNoon;
		$scope.afterNoon = response.body.afterNoon;
		//$scope.audit = response.body.audit;//Integration changes
                $scope.audit = response.audit;
		// Specific Screen Scope Response Ends 
                     
		//Multiple View Response Starts 
                
                if(response.body.foreNoon.length!=0){
                
		   $scope.attendanceForeNoonTable = fnConvertmvw('attendanceForeNoonTable',response.body.foreNoon);
                }else{
                   
                   $scope.attendanceForeNoonTable=null;
                }
            
                $scope.attendanceForeNoonCurPage = 1;
		$scope.attendanceForeNoonShowObject=$scope.fnMvwGetCurPageTable('attendanceForeNoon');
		
                
                if(response.body.afterNoon.length!=0){
                
		   $scope.attendanceAfterNoonTable = fnConvertmvw('attendanceAfterNoonTable',response.body.afterNoon);
		
                }else{
                    
                   $scope.attendanceAfterNoonTable=null; 
                }
                
                $scope.attendanceAfternoonCurPage = 1;
                
                if($scope.attendanceAfterNoonTable!=null){
                
		   $scope.attendanceAfterNoonShowObject=$scope.fnMvwGetCurPageTable('attendanceAfterNoon');
		
              }
            
               //Multiple View Response Ends 
		fnAttendanceButtonClrChange($scope.attendanceForeNoonShowObject);
                
                if($scope.attendanceAfterNoonTable!=null){
                
	        fnAttendanceButtonClrChange($scope.attendanceAfterNoonShowObject);
                
                
            }
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