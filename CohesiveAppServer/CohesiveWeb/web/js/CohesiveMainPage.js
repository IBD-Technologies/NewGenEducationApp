/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
var app = angular.module('Main', []);

app.controller('MainCtrl', function ($scope) {
	$scope.MainPageInstituteMaster = Institute.MainPageInstituteMaster;
	$scope.instituteID = "Select option";
	$scope.instituteIdreadOnly = false;

});
*/
var MenuName = 'cohesiveMainPage';
//var searchCallInputType;
var app = angular.module('Main', ['search','BackEnd', 'TableView']);
app.controller('MainCtrl', function($scope,searchCallService,SeacrchScopeTransfer,appServerCallService,TableViewCallService) {
    $scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
	$scope.searchShow = false;
	$scope.instituteID = null;
        $scope.instituteName = null;
        $scope.InstituteMaster = [{
	InstituteId: null,
	InstituteName: null
	}];
	$scope.newPwd="";
$scope.newConfirmPwd="";
$scope.errMessage="";
$scope.step3Show = true;
$scope.step4Show = false;
$scope.errorShow=false;
$scope.audit={};
$scope.fnInstituteNameSearch = function () {
		var searchCallInput = {
			mainScope: null,
			searchType:null
		};
		//cohesiveMainPage = true;
		searchCallInput.mainScope = $scope;
		searchCallInput.searchType = 'InstituteChange';
		SeacrchScopeTransfer.setMainScope($scope);
		searchCallService.searchLaunch(searchCallInput);
}
	//$scope.MainPageInstituteMaster = Institute.MainPageInstituteMaster;
	//$scope.instituteID = "Select option";
	 $scope.instituteIdreadOnly = true;
	// cohesiveMainPage = false;
        
        $scope.teacherAttendance="";
        $scope.studentAttendance="";
        $scope.teacherWorkingDays="";
        $scope.teacherLeaveDays="";
        $scope.classOfTheTeacher="";
//        $scope.totalFee="";
//        $scope.collectedAmount="";
//        $scope.pendingAmount="";
//        $scope.overDueAmount="";
//        $scope.classOverDueAmount="";
        
        $scope.pendingQueueMaster=null;
        $scope.smsLimit="";
        $scope.currentSMSBalance="";
        $scope.totalTeachers="";
        $scope.totalStudents="";
        $scope.dashBoardProcessDone=false;
        $scope.institutFeeDetails= null;
        $scope.classFeeDetails=null;
        
        $scope.pendingQueueMasterCurPage = 0;
	$scope.pendingQueueMasterTable = null;
	$scope.pendingQueueMasterShowObject = null;
        
        $scope.svwAddDeteleDisable = true;
        $scope.mvwAddDeteleDisable = true;
        
        $scope.fnMvwBackward = function (tableName, $event) {

		if (tableName == 'pendingQueueMaster') {
			if ($scope.pendingQueueMasterTable != null && $scope.pendingQueueMasterTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curPage = $scope.pendingQueueMasterCurPage;
				lsvwObject.tableObject = $scope.pendingQueueMasterTable;
				lsvwObject.screenShowObject = $scope.pendingQueueMasterShowObject;

				TableViewCallService.backwardMvwClick(lsvwObject);
				$scope.pendingQueueMasterCurPage = lsvwObject.curPage;
				$scope.pendingQueueMasterTable = lsvwObject.tableObject;
				$scope.pendingQueueMasterShowObject = lsvwObject.screenShowObject;
			}
                     //   $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
		
              /*$('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
        $('.currency').trigger('input');
        $('.currency').trigger('change');*/
            
            }
                
		}
        
        $scope.fnMvwForward = function (tableName, $event) {
		if (tableName == 'pendingQueueMaster') {
			if ($scope.pendingQueueMasterTable != null && $scope.pendingQueueMasterTable.length != 0) {
				var lsvwObject = new Object();


				lsvwObject.curPage = $scope.pendingQueueMasterCurPage;
				lsvwObject.tableObject = $scope.pendingQueueMasterTable;
				lsvwObject.screenShowObject = $scope.pendingQueueMasterShowObject;

				TableViewCallService.forwardMvwClick(lsvwObject);
				$scope.pendingQueueMasterCurPage = lsvwObject.curPage;
				$scope.pendingQueueMasterTable = lsvwObject.tableObject;
				$scope.pendingQueueMasterShowObject = lsvwObject.screenShowObject;
			}
                      //   $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
	 /* $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
        $('.currency').trigger('input');
        $('.currency').trigger('change');*/
            }
		}
        
        $scope.fnMvwAddRow = function (tableName, $event) {
            
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'pendingQueueMaster') {
				emptyTableRec = {
					idx: 0,
					service: "",
					operation: "",
					count: ""
				};
				if ($scope.pendingQueueMasterTable == null)
					$scope.pendingQueueMasterTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.pendingQueueMasterCurPage;
				lsvwObject.tableObject = $scope.pendingQueueMasterTable;
				lsvwObject.screenShowObject = $scope.pendingQueueMasterShowObject;


				TableViewCallService.addMvwRowClick(emptyTableRec, lsvwObject);

				$scope.pendingQueueMasterCurPage = lsvwObject.curPage;
				$scope.pendingQueueMasterTable = lsvwObject.tableObject;
				$scope.pendingQueueMasterShowObject = lsvwObject.screenShowObject;

			}
			}
			}
        $scope.fnMvwDeleteRow = function (tableName, $event) {
		if ($scope.mvwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'pendingQueueMaster') {
				var lsvwObject = new Object();

				lsvwObject.curPage = $scope.pendingQueueMasterCurPage;
				lsvwObject.tableObject = $scope.pendingQueueMasterTable;
				lsvwObject.screenShowObject = $scope.pendingQueueMasterShowObject;


				TableViewCallService.deleteMvwRowClick(lsvwObject)
				$scope.pendingQueueMasterCurPage = lsvwObject.curPage;
				$scope.pendingQueueMasterTable = lsvwObject.tableObject;
				$scope.pendingQueueMasterShowObject = lsvwObject.screenShowObject;
			}
                    }
                }
          $scope.fnMvwGetCurrentPage = function (tableName) {

		if (tableName == 'pendingQueueMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.pendingQueueMasterCurPage;
			lsvwObject.tableObject = $scope.pendingQueueMasterTable;
			lsvwObject.screenShowObject = $scope.pendingQueueMasterShowObject;

			return TableViewCallService.MvwGetCurPage(lsvwObject);


		}
            }
            
            $scope.fnMvwGetTotalPage = function (tableName) {

		if (tableName == 'pendingQueueMaster') {
			var lsvwObject = new Object();

			lsvwObject.curPage = $scope.pendingQueueMasterCurPage;
			lsvwObject.tableObject = $scope.pendingQueueMasterTable;
			lsvwObject.screenShowObject = $scope.pendingQueueMasterShowObject;

			return TableViewCallService.MvwGetTotalPage(lsvwObject);
		}
            }
            
            $scope.fnMvwGetCurPageTable = function (tableName) {
		if (tableName == 'pendingQueueMaster') {
			return TableViewCallService.MvwGetCurPageTable(1, $scope.pendingQueueMasterTable);

		}
            }
            
            
            
            $scope.instituteFeeDetailsRecord = null;
	    $scope.instituteFeeDetailsTable = null;
	    $scope.instituteFeeDetailscurIndex = 0;
  	    $scope.instituteFeeDetailsShow = false;
            
            $scope.fnSvwBackward = function (tableName, $event) {

		if (tableName == 'institutFeeDetails') {
			if ($scope.instituteFeeDetailsTable != null && $scope.instituteFeeDetailsTable.length != 0) {
				var lsvwObject = new Object();
				lsvwObject.curIndex = $scope.instituteFeeDetailscurIndex;
				lsvwObject.tableObject = $scope.instituteFeeDetailsTable;

				TableViewCallService.backwardSvwClick(lsvwObject);
				$scope.instituteFeeDetailscurIndex = lsvwObject.curIndex;
				$scope.instituteFeeDetailsTable = lsvwObject.tableObject;
				$scope.instituteFeeDetailsRecord = $scope.instituteFeeDetailsTable[$scope.instituteFeeDetailscurIndex];
                            }
		}
         }
         
         $scope.fnSvwForward = function (tableName, $event) {
		if (tableName == 'institutFeeDetails') {
			if ($scope.instituteFeeDetailsTable != null && $scope.instituteFeeDetailsTable.length != 0) {
				var lsvwObject = new Object();

				lsvwObject.curIndex = $scope.instituteFeeDetailscurIndex;
				lsvwObject.tableObject = $scope.instituteFeeDetailsTable;

				TableViewCallService.forwardSvwClick(lsvwObject);
				$scope.instituteFeeDetailscurIndex = lsvwObject.curIndex;
				$scope.instituteFeeDetailsTable = lsvwObject.tableObject;
				$scope.instituteFeeDetailsRecord = $scope.instituteFeeDetailsTable[$scope.instituteFeeDetailscurIndex];
                                }
		} 
	};
        
        $scope.fnSvwAddRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'institutFeeDetails') {
				emptyTableRec = {
					idx: 0,
                                        feeType: "",
					totalFee: "",
                                        collectedAmount: "",
					pendingAmount: "",
					overDueAmount: ""
				};

				if ($scope.instituteFeeDetailsTable == null)
					$scope.instituteFeeDetailsTable = new Array();
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.instituteFeeDetailsShow;
				lsvwObject.curIndex = $scope.instituteFeeDetailscurIndex;
				lsvwObject.tableObject = $scope.instituteFeeDetailsTable;


				TableViewCallService.addSvwRowClick(emptyTableRec, lsvwObject);

				$scope.instituteFeeDetailsShow = lsvwObject.tableShow;
				$scope.instituteFeeDetailscurIndex = lsvwObject.curIndex;
				$scope.instituteFeeDetailsTable = lsvwObject.tableObject;
				$scope.instituteFeeDetailsRecord = $scope.instituteFeeDetailsTable[$scope.instituteFeeDetailscurIndex];
                                   
			}
			}
			}
        $scope.fnSvwDeleteRow = function (tableName, $event) {
		if ($scope.svwAddDeteleDisable) {
			$event.preventDefault();
		} else {
			if (tableName == 'institutFeeDetails') {
				var lsvwObject = new Object();

				lsvwObject.tableShow = $scope.instituteFeeDetailsShow;
				lsvwObject.curIndex = $scope.instituteFeeDetailscurIndex;
				lsvwObject.tableObject = $scope.instituteFeeDetailsTable;


				TableViewCallService.deleteSvwRowClick(lsvwObject)
				$scope.instituteFeeDetailsShow = lsvwObject.tableShow;
				$scope.instituteFeeDetailscurIndex = lsvwObject.curIndex;
				$scope.instituteFeeDetailsTable = lsvwObject.tableObject;
				$scope.instituteFeeDetailsRecord = $scope.instituteFeeDetailsTable[$scope.instituteFeeDetailscurIndex];
                               	
                }
				}
				}
         $scope.fnSvwGetCurrentPage = function (tableName) {

		if (tableName == 'institutFeeDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.instituteFeeDetailsShow;
			lsvwObject.curIndex = $scope.instituteFeeDetailscurIndex;
			lsvwObject.tableObject = $scope.instituteFeeDetailsTable;
			return TableViewCallService.SvwGetCurrentPage(lsvwObject);

		}
		}
         $scope.fnSvwGetTotalPage = function (tableName) {

		if (tableName == 'institutFeeDetails') {
			var lsvwObject = new Object();

			lsvwObject.tableShow = $scope.instituteFeeDetailsShow;
			lsvwObject.curIndex = $scope.instituteFeeDetailscurIndex;
			lsvwObject.tableObject = $scope.instituteFeeDetailsTable;
			return TableViewCallService.SvwGetTotalPage(lsvwObject);


		}
		}       
            
   });
   
   
   
//InstituteFeePayment Invocation Starts

var InstituteFeePaymentkey = {
	paymentID: null,
        paymentDate:null
};
var Classkey = {
	class: null
};
var NotificationSummarykey = {
	notificationID: null
};
var InstituteAssignmentkey = {
	assignmentID: null
};
var InstituteFeeManagementkey = {
	feeID: null
};
var StudentAssignmentkey = {
	assignmentID: null,
        studentID:null
};
var StudentLeaveManagementkey = {
	studentID: null,
        from:null,
        to:null
};
var StudentFeeManagementkey = {
	feeID: null
};
var StudentPaymentkey = {
	paymentID: null,
        studentID:null,
        paymentDate:null
};
var StudentOtheractivitykey = {
	studentID: null,
        activityID:null
};
var TeacherLeaveManagementkey = {
	teacherID: null,
        from: null,
        to: null
};
var ClassMarkkey = {
	class: null,
	subjectID: null,
	exam: null
};
var ClassSoftSkillkey = {
	class: null,
	skillID: null,
	exam: null
};
var ClassAttendancekey = {
	standard: null,
	section: null,
	date: null
};
var ClassTimeTablekey = {
	standard: null,
	section: null
};
var ClassExamSchedulekey = {
	class: null,
	exam: null
};
var TeacherAttendancekey = {
	teacherID: null,
	date: null
};
var TeacherTimeTablekey = {
	teacherID: null
};
var TeacherProfilekey = {
	teacherID: null
};
var StudentProfilekey = {
	studentID: null
};
var UserProfilekey = {
	userID: null
};
var UserRolekey = {
	studentID: null,
	standard: null,
	section: null,
	exam: null
};
var StudentProgressCardkey = {
	roleID: null
};
var StudentSoftSkillkey = {
	studentID: null,
        exam: null
        
};

var StudentECircularkey = {
	studentID: null,
        circularID: null
        
};
var TeacherECircularkey = {
	teacherID: null,
        circularID: null
        
};

var HolidayMaintenanceSummarykey = {
	month: null,
	year: null
};
var ECircularSummarykey = {
	circularID: null
};
var GroupMapingSummarykey = {
	authStat: null,
	recordStat: null
};
var InstituteOtheractivitySummarykey = {
//	activityType: null,
//	activityLevel: null,
	activityID: null
};
var StudentAttendanceSummarykey = {
	studentID: null,
        date:null
};


var StudenTimeTablekey = {
	studentID: null
};
var StudentExamSchedulekey = {
	studentID: null
};

var StudentNotificationSummarykey ={
notificationID:null,
studentID:null,
date:null
};

var uhtuliak;
//InstituteFeePayment Invocation Ends


/*   
angular.module('Main').service('appServerCallService', function() {
   this.backendCall=function(appServerCallInput){
                                        console.log(appServerCallInput.method);
										//console.log(appServerCallService.request); 
                                        fn_print_appServerObjects(appServerCallInput.request);*/
/*$http({method: appServerCallInput.method, url: appServerCallInput.url, data :appServerCallInput.request,cache: appServerCallInput.cache}).
    then(function(response) {
      appServerCallInput.status = response.status;
      appServerCallInput.response = response.data;
    }, function(response) {
      appServerCallInput.response = response.data || 'Request failed';
      appServerCallInput.status = response.status;
  });*/
/*  appServerCallInput.response=SuccessTTTResponse;
   //appServerCallInput.response=errorSTTResponse;
   fn_print_appServerObjects(appServerCallInput.response);
                   }

}); */

function fnInvokeScreen(url) {
	var xhr = new XMLHttpRequest();

	xhr.open('GET', url);
	xhr.onreadystatechange = handler;
	xhr.responseType = 'blob';
	xhr.setRequestHeader('X-uhtuliak', uhtuliak);
	xhr.send();
}

function handler() {
	if (this.readyState === this.DONE) {
		if (this.status === 200) {
			// this.response is a Blob, because we set responseType above
			var data_url = URL.createObjectURL(this.response);
			document.querySelector('#frame').src = data_url;
			URL.revokeObjectURL(data_url);
		} //else {
		//console.error('no pdf :(');
		//}
	}
}

function fngetSrc(url) {
	var src = url + "?X-uhtuliak=" + uhtuliak+"&"+"nrb="+Institute.ID;
	return src;
}

function fnMainPage() {
	$('#Content').tab('show');
}

function fnMainPagewithError(error) {
	$('#Content').tab('show');
	fn_show_backend_exception(error);
}
function fnSettingswithError(error) {
	//$('#Content').tab('show');
	fn_show_backend_exception(error);
}
var selectBoxfromMain = new Array();
function fnSelectBoxmasterCalls()
{
		 
    selectBoxfromMain=['class','feeType','periodNumber','subject','examType','notificationTypes'];
    
    var selectBox={ClassMaster:[],FeeMaster:[],PeriodMaster:[],NotificationMaster:[],SubjectMaster:[],ExamMaster:[]};
      
    sessionStorage.setItem('selectBox',JSON.stringify(selectBox));
    
    //Institute={ClassMaster:[],FeeMaster:[],PeriodMaster:[],NotificationMaster:[],SubjectMaster:[],ExamMaster:[]};
    /*Institute.ClassMaster =new Array();   
    Institute.FeeMaster =new Array();   
    Institute.PeriodMaster =new Array();   
    Institute.NotificationMaster =new Array();   
    Institute.SubjectMaster =new Array();   
    Institute.ExamMaster =new Array(); */  
    fnGetSelectBoxdataforMain(selectBoxfromMain);
	return 'success'; 
    	
}



function fnselectboxMasterBgCall() {
  return new Promise(resolve => {
    setTimeout(() => {
      fnSelectBoxmasterCalls();
    }, 0);
  });
}

function fndashBoardBgCall() {
  return new Promise(resolve => {
    setTimeout(() => {
        var $scope = angular.element(document.getElementById('MainCtrl')).scope();
      fndashBoardmasterCalls($scope);
//      $scope.$apply();
    }, 0);
  });
}


async function asyncCall() {
  console.log('calling');
  var result = await fnselectboxMasterBgCall();
//  dashBoardAsyncCall();
  //console.log(result);
  // expected output: 'resolved'
}

async function dashBoardAsyncCall() {
  console.log('calling');
  var result = await fndashBoardBgCall();
  //console.log(result);
  // expected output: 'resolved'
}

var originalSize ;
var keypadUpMain = false;
var viewheight ;
    var viewwidth;
$(document).ready(function () {
//$('footer').hide();
viewheight = $(window).height();
viewwidth = $(window).width();
originalSize= $(window).width() + $(window).height();

if(/Android/.test(navigator.appVersion)) {

   window.addEventListener("resize", function() {
   if($(window).width() + $(window).height() <originalSize){
      //console.log("keyboard show up");
      
       if(document.activeElement.tagName=="INPUT" || document.activeElement.tagName=="TEXTAREA") {

       document.activeElement.scrollIntoView();

     }
    }else{
    
       
    var viewport = $("meta[name=viewport]");
    viewport.attr("content", "height=" + viewheight + "px, width=" + 
    viewwidth + "px, initial-scale=1.0");    
           
           
           
         //keypadUpMain = false;  
  $("html").css("height","100%");
  $("body").css("height","100%");
  $("header").removeClass("mainscreenHeader");
  $("header").addClass("mainscreenHeader");
  
  $("#appContent").removeClass("mainscreenContent");
  $("#appContent").addClass("mainscreenContent");
  
  $('#frameDiv').removeClass("embed-responsive");
  $('#frameDiv').addClass("embed-responsive");
       
  $('#frameDiv').removeClass("cohesive-embed-responsive");
  $('#frameDiv').addClass("cohesive-embed-responsive");
  $('#frameDiv').removeClass("embed-responsive-1by1");
  $('#frameDiv').addClass("embed-responsive-1by1");
  $('#frame').removeClass("embed-responsive-item");
  $('#frame').addClass("embed-responsive-item");
   
  $("#Frame").contents().find("html").css("height","100%");
  
  $("#Frame").contents().find("#SubScreenCtrl").css("height","100%");
  $("#Frame").contents().find("#SubScreenCtrl").css("min-height","100%");
  $("#Frame").contents().find("#SubScreenCtrl").css("max-height","100%");
  
  $("#Frame").contents().find("header").removeClass("subscreenHeader");
  $("#Frame").contents().find("header").addClass("subscreenHeader");

  $("#Frame").contents().find('#subscreenContent').removeClass("subscreenContent");
  $("#Frame").contents().find('#subscreenContent').addClass("subscreenContent");
  $("#Frame").contents().find("footer").removeClass("subscreenFooter");
  $("#Frame").contents().find("footer").addClass("subscreenFooter");
 // document.getElementById("subscreenHeader").scrollIntoView(); 
  //$('#subscreenContent1').addClass("subscreenContent");
 // $("footer").removeClass("subscreenFooter");
  //$("footer").addClass("subscreenFooter");
 
       
      //console.log("keyboard closed");
     // $(".copyright_link").css("position","fixed");  
    //}
    }
   }
   ) };

    
    dashBoardAsyncCall();
    asyncCall();
    
	
        fn_hide_spinner();
           
	uhtuliak = $("#uhtuliak").val();
	User.Id = $("#UIDServer").val();
	Institute.ID = $("#InstituteServer").val();
        Institute.Name = $("#InstituteNameServer").val();
        $("#iID").val(Institute.ID);
        $("#iName").val(Institute.Name);

	$('#MenuPills a[href="#Content"]').tab('show');

	var $scope = angular.element(document.getElementById('MainCtrl')).scope();
	selectBoxes = ['instituteId'];
	//fnSelectBoxDefault(selectBoxes);
	//This is to remove subScreen	

    $('#logOut').click(function (){
		
		window.parent.uhtuliak=null;
	    window.location.href="./jsp/Login.jsp";  
	});




	$('#MenuTab').click(function () {
           // window.scrollTo(0,0);
            $('body').scrollTop(0);
		$("#frame").attr("src", "");
		$("#MenuModel").modal({
			backdrop: true,
			keyboard: true,
			focus: true,
			show: true
		});
		$('#MenuModel').modal('handleUpdate');
		$('#MenuModel').on('shown.bs.modal', function () {
			$('#MenuModel').trigger('focus');

		});

		$('#MenuModel').on('hidden.bs.modal', function (e) {
			//$('#MenuModel a').modal('hide');
			$('#MenuModel').modal('dispose');
			$("#snackbar").empty();

		});
	});

	$('#chIns').click(function () {
		$("#frame").attr("src", "");
		$("#InstituteModal").modal({
			backdrop: true,
			keyboard: true,
			focus: true,
			show: true
		});
		$('#InstituteModal').modal('handleUpdate');
		$('#InstituteModal').on('shown.bs.modal', function () {
			$('#InstituteModal').trigger('focus');

		});

		$('#InstituteModal').on('hidden.bs.modal', function (e) {
			//$('#MenuModel a').modal('hide');
			$('#InstituteModal').modal('dispose');
			$("#snackbar").empty();

		});
	});
	
	
$('#changeInstituteOk').click(function()
{
   if ($("#iID").val()!=null && $("#iID").val()!='') 
   {  
Institute.ID = $("#iID").val();
Institute.Name = $("#iName").val();
//asyncCall();
   }
   else
   {
     $("#iID").val(Institute.ID); 
     $("#iName").val(Institute.Name);
   }   
});	

	$('#NotificationTab').click(function () {
            //window.scrollTo(0,0);
             $('body').scrollTop(0);
		$("#frame").attr("src", "");
	});

	$('#LogoTab').click(function () {
            $('body').scrollTop(0);
		$("#frame").attr("src", "");
                  fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                  $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame1").attr("src", fngetSrc("./jsp/SwamySchool.jsp"));
                
                
                
                
	});

	$('#SettingsTab').click(function () {
           $('body').scrollTop(0);
           
		$("#frame").attr("src", "");
	});

	$('#GiftTab').click(function () {
            window.scrollTo(0,0);
		$("#frame").attr("src", "");
	});

	//This is to hide the sub menu	
	$(".mainmenu").filter("a").mouseenter(function () {

		$(".submenu").filter(".show").collapse("hide");
		$(".submenu1").filter(".show").collapse("hide");
		$(".mainmenu1").filter(".show").collapse("hide");

	});


	//This is to hide the sub menu	
	$(".mainmenu1").filter("a").mouseenter(function () {

		$(".submenu1").filter(".show").collapse("hide");
	});


	//This is to call the Time tables screen 

	$("#ClassTimeTable").click(function () { //Click Event for Sub menu Time table/Class
		 fn_show_spinner();
                //To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src",fngetSrc("./jsp/ClassTimeTable.jsp"));
		//$("#frame").css('height', $(window).height()+'px');
	});
	$("#TeacherTimeTable").click(function () { //Click Event for Sub menu Time table/Teacher
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/TeacherTimeTable.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#ClassExamSchedule").click(function () { //Click Event for ClassExamSchedule
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/ClassExamSchedule.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#progressCardEntry").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/ClassMark.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});


        $("#softSkillEntry").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/ClassSoftSkill.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});


	$("#studProgressCard").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/StudentProgressCard.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
        $("#studSoftSkill").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/StudentSoftSkill.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
        $("#studECircular").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/StudentECircular.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
        $("#teachECircular").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/TeacherECircular.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
	$("#TeacherLM").click(function () {
                  fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/TeacherLeaveManagement.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#StudentLM").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		$("#frame").attr("src",fngetSrc("./jsp/StudentLeaveManagement.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#ClassFM").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		$("#frame").attr("src",fngetSrc("./jsp/InstituteFeeManagement.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#StudentFM").click(function () {
                  fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src",fngetSrc("./jsp/StudentFeeManagement.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#ClassAttendance").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		  $("#frame").attr("src",fngetSrc("./jsp/ClassAttendance.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#TeacherAttendance").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src",fngetSrc("./jsp/TeacherAttendance.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#studentAttendance").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
		 $("#frame").attr("src",fngetSrc("./jsp/StudentAttendance.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#teacherProfile").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src",fngetSrc("./jsp/TeacherProfile.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#studentProfile").click(function () {
		//To hide the content tab pane
		fn_show_spinner();
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		//To Show the child html page in the Iframe
		$('#MenuModel').modal('hide');
		$("#frame").attr("src", fngetSrc("./jsp/StudentProfile.jsp"));

		//$("#frame").attr("src", "StudentProfile.jsp");
		//$("#frame").css('height', $(window).height()+'px');  
	});
	
	
	$("#StudentNotification").click(function () {
		//To hide the content tab pane
		fn_show_spinner();
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		//To Show the child html page in the Iframe
		$('#MenuModel').modal('hide');
		$("#frame").attr("src", fngetSrc("./jsp/StudentNotification.jsp"));

		//$("#frame").attr("src", "StudentProfile.jsp");
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
        $("#StudentFee").click(function () {
		//To hide the content tab pane
		fn_show_spinner();
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		//To Show the child html page in the Iframe
		$('#MenuModel').modal('hide');
		$("#frame").attr("src", fngetSrc("./jsp/StudentFeeManagement.jsp"));

		//$("#frame").attr("src", "StudentProfile.jsp");
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
	$("#otherActivity").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/StudentOtherActivity.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#payRoll").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/PayRoll.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#ClassAssignment").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/InstituteAssignment.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
        
        $("#ClassEcircular").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/ECircular.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
	$("#StudAssignment").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentAssignment.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#TeacherCalender").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/TeacherCalender.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#StudentCalender").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentCalender.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	
	$("#accrossExam").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/MarkComparisonAccrossAllExam.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#accrossSection").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/MarkComparisonAccrossSection.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#examReport").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                  $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/ExamReportStatistics.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#notification").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/Notification.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#feePaymentDetail").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/InstitutePayment.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#userProfile").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/UserProfile.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#classesDetail").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/ClassLevelConfiguration.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#GLConfigurations").click(function () {
		//To hide the content tab pane
		fn_show_spinner();
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		//To Show the child html page in the Iframe
		//$("#frame").attr("src", "GeneralLevelConfigurations.html");
		//$("#frame").attr("src", "./jsp/GeneralLevelConfigurations.jsp");
		//fnInvokeScreen("./jsp/GeneralLevelConfigurations.jsp");
		$('#MenuModel').modal('hide');
                $('footer').hide();
		$("#frame").attr("src", fngetSrc("./jsp/GeneralLevelConfiguration.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        $("#classesSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/ClassSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
	$("#userRole").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/UserRole.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
//	$("#StudPayment").click(function () {
//                fn_show_spinner();
//		//To hide the content tab pane
//		$('#Content').removeClass('active');
//		$('#Content').removeClass('show');
//		$('#MenuTab').removeClass('active');
//		$('#MenuTab').removeClass('show');
//                 $('#MenuModel').modal('hide');
//		//To Show the child html page in the Iframe
//                 $("#frame").attr("src", fngetSrc("./jsp/StudentPayment.jsp"));
//		//$("#frame").css('height', $(window).height()+'px');  
//	});

        $("#StudentPayment").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentPayment.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#feePaymentSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/InstitutePaymentSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#notificationSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/NotificationSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#assignmentSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/InstituteAssignmentSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        $("#ecircularSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/ECircularSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#feeManagementSummary").click(function () {
                  fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                  $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/InstituteFeeManagementSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#StudAssignmentSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/StudentAssignmentSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#studentLeaveManagementSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentLeaveManagementSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#studentFMSummary").click(function () {
               fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentFeeManagementSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
//	$("#studPaymentSummary").click(function () {
//                fn_show_spinner();
//		//To hide the content tab pane
//		$('#Content').removeClass('active');
//		$('#Content').removeClass('show');
//		$('#MenuTab').removeClass('active');
//		$('#MenuTab').removeClass('show');
//                 $('#MenuModel').modal('hide');
//		//To Show the child html page in the Iframe
//                   $("#frame").attr("src", fngetSrc("./jsp/StudentPaymentSummary.jsp"));
//		//$("#frame").css('height', $(window).height()+'px');  
//	});
       $("#StudentPaymentSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentPaymentSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#OtherActivitySummary").click(function () {
              fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentOtherActivitySummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#TLMSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/TeacherLeaveManagementSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#MarkEntrySummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/ClassMarkSummary.jsp"));
	});
        
        $("#SoftSkillEntrySummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/ClassSoftSkillSummary.jsp"));
	});

	$("#ClassAttendanceSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/ClassAttendanceSummary.jsp"));
	});

	$("#ClassTimeTableSummary").click(function () {
               fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/ClassTimeTableSummary.jsp"));
	});

	$("#ClassExamScheduleSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/ClassExamScheduleSummary.jsp"));
	});
	$("#TeacherAttendanceSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/TeacherAttendanceSummary.jsp"));
	});

	$("#TeacherTimeTableSummary").click(function () {
               fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                  $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/TeacherTimeTableSummary.jsp"));
	});
	$("#TeacherProfileSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                  $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/TeacherProfileSummary.jsp"));
	});
	$("#StudentProfileSummary").click(function () {
		//To hide the content tab pane
		fn_show_spinner();
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		//To Show the child html page in the Iframe
		$('#MenuModel').modal('hide');
		$("#frame").attr("src", fngetSrc("./jsp/StudentProfileSummary.jsp"));
		//$("#frame").attr("src", "StudentProfileSummary.jsp");
	});
	$("#userProfileSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		$("#frame").attr("src", fngetSrc("./jsp/UserProfileSummary.jsp"));
		//To Show the child html page in the Iframe
	});
	$("#StudentProgressCardSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/StudentProgressCardSummary.jsp"));
	});
        
        
        $("#StudentSoftSkillSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/StudentSoftSkillSummary.jsp"));
	});
        
        $("#StudentECircularSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/StudentECircularSummary.jsp"));
	});
        $("#TeacherECircularSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/TeacherECircularSummary.jsp"));
	});
	$("#StudentNotificationSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/StudentNotificationSummary.jsp"));
	});
	$("#StudentFeeSummary").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/StudentFeeManagementSummary.jsp"));
	});
	$("#payRole").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/PayRoll.jsp"));
	});
	$("#userRoleSummary").click(function () {
               fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/UserRoleSummary.jsp"));
	});
	$("#studentReport").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/Student360DegreeReport.jsp"));
	});
        
        $("#FeeReport").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/FeeBusinessReport.jsp"));
	});
        $("#PaymentReport").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/PaymentBusinessReport.jsp"));
	});
        $("#NotificationReport").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/NotificationBusinessReport.jsp"));
	});
        
         $("#StudentDetailsReport").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/StudentDetailsReport.jsp"));
	});
        
        $("#TableReport").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/TableReport.jsp"));
	});
	$("#teacherReport").click(function () {
                  fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                  $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/Teacher360DegreeReport.jsp"));
	});
	$("#ClassReport").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                   $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/Class360DegreeReport.jsp"));
	});
	$("#SubsAvailReport").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/SubstituteReport.jsp"));
	});
	$("#HolidayMaintenance").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/HolidayMaintenance.jsp"));
	});
	$("#holidayMainSummary").click(function () {
                  fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/HolidayMaintenanceSummary.jsp"));
	});
	$("#Grouping").click(function () {
               fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                    $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                   $("#frame").attr("src", fngetSrc("./jsp/GroupMapping.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#GroupingSummary").click(function () {
               fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/GroupMappingSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#InsOtherActivity").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/InstituteOtherActivity.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#InsOtherActivitySummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/InstituteOtherActivitySummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#StudAttendanceSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                $("#frame").attr("src", fngetSrc("./jsp/StudentAttendanceSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#BatchConfiguration").click(function () {
                 fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/BatchConfiguration.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

	$("#BatchMonitor").click(function () {
                   fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
                $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                  $("#frame").attr("src", fngetSrc("./jsp/BatchMonitor.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
	$("#Batchrun").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/BatchRun.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});

            $("#StudExamSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentExamScheduleSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
         
           $("#StudExam").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentExamSchedule.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
           $("#StudTT").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentTimeTable.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
         $("#StudTTSummary").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/StudentTimeTableSummary.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
          $("#comparison").click(function () {
                fn_show_spinner();
		//To hide the content tab pane
		$('#Content').removeClass('active');
		$('#Content').removeClass('show');
		$('#MenuTab').removeClass('active');
		$('#MenuTab').removeClass('show');
		 $('#MenuModel').modal('hide');
		//To Show the child html page in the Iframe
                 $("#frame").attr("src", fngetSrc("./jsp/GradeComparison.jsp"));
		//$("#frame").css('height', $(window).height()+'px');  
	});
        
         $('.currency').blur(function()
                {
                    $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
                });

});