/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 */
var selectBoxes = new Array();
var responseShow = false;
var Institute = {ClassMaster:[],AuthStatusMaster: [{name:"Select option",value:""},{name:"Authorised",value:"A"},{name:"Unauthorised",value:"U"},{name:"Rejected",value:"R"}],
	RecStatusMaster: [{name:"Select option",value:""},{name:"Open",value:"O"},{name:"Deleted",value:"D"}],
        PayMentMaster: [{name:"Select option",value:""},{name:"Cash",value:"C"},{name:"Cheque",value:"Q"},{name:"NetBanking",value:"N"},{name:"Others",value:"O"}],
        MediaCommunication: [{name:"Select option",value:""},{name:"Mail",value:"M"},{name:"SMS",value:"S"},{name:"Both",value:"B"}],
	DayMaster: [{name:"Select option",value:""},{name:"Monday",value:"Mon"},{name:"Tuesday",value:"Tue"},{name:"Wednesday",value:"Wed"},{name:"Thursday",value:"Thu"},{name:"Friday",value:"Fri"},{name:"Saturday",value:"Sat"},{name:"Sunday",value:"Sun"}],
        MonthMaster: [{name:"Select option",value:""},{name:"January",value:"01"},{name:"February",value:"02"},{name:"March",value:"03"},{name:"April",value:"04"},{name:"May",value:"05"},{name:"June",value:"06"},{name:"July",value:"07"},{name:"August",value:"08"},{name:"September",value:"09"},{name:"October",value:"10"},{name:"November",value:"11"},{name:"December",value:"12"}],
//	FeatureMaster: ["Select option","ClassTimeTable", "TeacherTimeTable", "TeacherProfile", "StudentProfile", "ClassAttendance", "TeacherAttendance", "StudentAttendance", "StudentAssignment", "ClassAssignment", "StudentCalender", "TeacherCalender", "ExamSchedule", "StudentProgressCard", "StudentProgressCardEntry", "StudentLeaveManagement", "TeacherLeaveManagement", "ClassFeeManagement", "StudentFeeManagement", "PaySlip", "Transport", "Notification", "Statistics", "OtherActivity", "Administration"],
        FeatureMaster:["Select option","ALL","ClassAttendance","ClassExamSchedule","ClassMark","ClassStudentMapping","ClassTimeTable","ClassExamScheduleSummary","ClassMarkSummary","ClassTimeTableSummary","ClassLevelConfiguration","ECircular","GeneralLevelConfiguration","GroupMapping","HolidayMaintenance","InstituteAssignment","InstituteFeeManagement","InstituteOtherActivity","InstitutePayment","ManagementDashBoard","Notification","AssigneeSearchService","AssignmentSearchService","BatchSearchService","FeeSearchService","InstituteSearchService","NotificationSearchService","OtherActivitySearchService","PaymentSearchService","UserRoleSearchService","StudentSearchService","TeacherSearchService","UserSearchService","SelectBoxMaster","StudentMaster","InstituteAssignmentSummary","InstituteOtherActivitySummary","InstituteFeeManagementSummary","InstitutePaymentSummary","GroupMappingSummary","HolidayMaintenanceSummary","NotificationSummary","TeacherMaster","ParentDashBoard","StudentECircular","StudentAssignment","StudentAttendance","StudentCalender","StudentExamSchedule","StudentFeeManagement","StudentLeaveManagement","StudentNotification","StudentOtherActivity","StudentPayment","StudentProfile","StudentProgressCard","StudentTimeTable","StudentAssignmentSummary","StudentAttendanceSummary","StudentExamScheduleSummary","StudentFeeManagementSummary","StudentLeaveManagementSummary","StudentPaymentManagementSummary","StudentOtherActivitySummary","StudentPaymentSummary","StudentProfileSummary","StudentProgressCardSummary","StudentTimeTableSummary","Payroll","TeacherAttendance","TeacherDashBoard","TeacherCalender","TeacherLeaveManagement","TeacherProfile","TeacherTimeTable","TeacherAttendanceSummary","TeacherLeaveManagementSummary","TeacherProfileSummary","TeacherTimeTableSummary","UserProfileSummary","UserRoleSummary","UserRole","UserProfile","StudentSoftSkill","TeacherECircular"],
        ProfileStatusMaster: [{name:"Select option",value:""},{name:"Enable",value:"E"},{name:"Disable",value:"D"}],
	StatusMaster: [{name:"Select option",value:""},{name:"Completed",value:"C"},{name:"Incomplete",value:"I"}],
	FeeStatus: [{name:"Select option",value:""},{name:"Pending",value:"P"},{name:"OverDue",value:"O"},{name:"Paid",value:"C"}],
        HourMaster: ["Hour","00","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"],
	MinMaster: ["Min","00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"],
        AssignmentTypeMaster:[{name:"Select option",value:""},{name:"Homework",value:"H"},{name:"Term/Exam",value:"T"},{name:"Punishment",value:"P"},{name:"Improvement",value:"I"}],
	OtherActivityLevelMaster:[{name:"Select option",value:""},{name:"State",value:"S"},{name:"District",value:"D"},{name:"International",value:"I"},{name:"Internal",value:"E"}],
	YearMaster:["Select option","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029"],
        NotificationFrequency: [{name:"Select option",value:""},{name:"Daily",value:"D"},{name:"Weekly",value:"W"},{name:"FortNightly",value:"F"},{name:"Monthly",value:"M"},{name:"Quarterly",value:"Q"},{name:"Instant",value:"I"}],
        NoonMaster:[{name:"Select option",value:""},{name:"HalfDay-FN",value:"F"},{name:"HalfDay-AN",value:"A"},{name:"FullDay",value:"D"}],
        ParticipateMaster:[{name:"Select option",value:""},{name:"Yes",value:"Y"},{name:"No",value:"N"}],
        BatchMaster:[{name:"Select option",value:""},{name:"Database",value:"D"},{name:"Bussiness",value:"B"},{name:"Report",value:"R"}],
        LeaveMaster: [{name:"Select option",value:""},{name:"Sick",value:"S"},{name:"Planned",value:"P"},{name:"Casual",value:"C"}],
        LeaveMasterStatus: [{name:"Select option",value:""},{name:"Pending",value:"U"},{name:"Approved",value:"A"},{name:"Rejected",value:"R"}],
        UserTypeMaster: [{name:"Select option",value:""},{name:"Admin",value:"A"},{name:"Teacher",value:"T"},{name:"Parent",value:"P"}],
        DateMaster: ["Select option","01", "02", "03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"],
        FeeMaster:[],
        PeriodMaster:["Select option","1","2","3","4","5","6","7","8"],
        NotificationMaster:[],
        SubjectMaster:[],
        StandardMaster:[],
        ExamMaster:[],
	ActivityTypeMaster:[{name:"Select option",value:""},{name:"Sports",value:"S"},{name:"Culturals",value:"C"}],
	
        AttendanceNoonMaster:[{name:"Select option",value:""},{name:"Forenoon",value:"F"},{name:"AfterNoon",value:"A"}],
        
        AttendanceMaster:[{name:"Select option",value:""},{name:"Period wise",value:"P"},{name:"Day wise",value:"D"},{name:"Noon wise",value:"N"}],
        
        GenderMaster:[{name:"Select option",value:""},{name:"Male",value:"M"},{name:"Female",value:"F"},{name:"Others",value:"O"}],
        
        LanguageMaster:[{name:"Select option",value:""},{name:"English",value:"EN"},{name:"Tamil",value:"TN"}],
        
        SkillMaster:[{name:"Select option",value:""},{name:"Communication",value:"1"},{name:"Leadership",value:"2"},{name:"Food Habit",value:"3"},{name:"Being Social",value:"4"},{name:"Team Work",value:"5"},{name:"Discipline",value:"6"},{name:"Dressing",value:"7"}],
        
        CategoryMaster:[{name:"Select option",value:""},{name:"Outstanding",value:"1"},{name:"Good",value:"2"},{name:"Improvement Required",value:"3"}],

        CircularTypeMaster:[{name:"Select option",value:""},{name:"Student",value:"S"},{name:"Teacher",value:"T"}],
        
        StudentStatusMaster:[{name:"Select option",value:""},{name:"Studying",value:"O"},{name:"Studied",value:"D"}],
        
        RelationshipMaster:[{name:"Select option",value:""},{name:"Father",value:"Father"},{name:"Mother",value:"Mother"},{name:"Grand Father",value:"Grand Father"},{name:"Grand Mother",value:"Grand Mother"},{name:"Guardian",value:"Guardian"}],
        
        BloodGroupMaster:[{name:"Select option",value:""},{name:"A+",value:"A+"},{name:"A-",value:"A-"},{name:"B+",value:"B+"},{name:"B-",value:"B-"},{name:"O+",value:"O+"},{name:"O-",value:"O-"},{name:"AB+",value:"AB+"},{name:"AB-",value:"AB-"},{name:"A1B+",value:"A1B+"},{name:"Others",value:"Others"}],

};

function fnSelectBoxDefault(id) {
	id.forEach(fnselectDefault);

	function fnselectDefault(value, index, array) {
		$("#" + value).selectmenu().selectmenu("menuWidget").addClass("overflow");
		$("#" + value).selectmenu("option", "disabled", true);
		//$("#"+value).selectmenu("option","classes.ui-selectmenu-button.ui-button","customUI-button");
		var tempID = value;
		//if(!(tempID =='startTimeHour' ||tempID == 'startTimeMin' || tempID == 'endTimeHour' || tempID == 'endTimeMin')){
		$(".ui-selectmenu-button.ui-button").css('width', '1%');
		//}
		$("#" + value).selectmenu("option", "classes.ui-selectmenu-button", "custom-select");
		$("#" + value).selectmenu("option", "classes.ui-state-disabled", "selectDisabled");
		$("#" + value).selectmenu("option", "classes.ui-selectmenu-open", "form-control");
	}

	id.forEach(fnselectTimeDefault);

	function fnselectTimeDefault(value, index, array) {
		var tempID = value;
		if (tempID == 'startTimeHour' || tempID == 'startTimeMin' || tempID == 'endTimeHour' || tempID == 'endTimeMin') {
			$('#' + tempID + '-button').removeAttr('style');
		}
	}


	$('#feeTypeButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('feeType', $scope.feeTypereadOnly);
	});

	$('#paymentModeButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('paymentMode', $scope.feeTypereadOnly);
	});
	$('#startTimeHourButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('startTimeHour', $scope.startTimereadOnly);
	});

	$('#startTimeMinButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('startTimeMin', $scope.startTimereadOnly);
	});

	$('#endTimeHourButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('endTimeHour', $scope.endTimereadOnly);
	});
	$('#endTimeMinButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('endTimeMin', $scope.endTimereadOnly);
	});
	$('#subjectButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('subject', $scope.subjectNamereadOnly);
	});
	$('#examHallButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('examHall', $scope.examHallreadOnly);
	});
	$('#standardIDButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('standardID', $scope.standardreadOnly);
	});
	$('#SectionButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('Section', $scope.sectionreadOnly);
	});
	$('#examTypeButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('examType', $scope.examtypereadOnly);
	});
	$('#activityLevelButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('activityLevel', $scope.levelreadOnly);
	});
	$('#activityVenueButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('activityVenue', $scope.venuereadOnly);
	});
	$('#authStatusButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('authStatus', $scope.authStatreadOnly);
	});
	$('#recordStatusButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('recordStatus', $scope.recordStatreadOnly);
	});
	$('#studentStatusButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('studentStatus', $scope.statusreadOnly);
	});
	$('#SubjectNameButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('SubjectName', $scope.subjectNamereadOnly);
	});
	$('#assignmentTypeButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('assignmentType', $scope.assignmentTypereadOnly);
	});
	$('#StatusButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('Status', $scope.statusReadonly);
	});
	$('#leaveTypeButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('leaveType', $scope.typeReadOnly);
	});
	$('#periodNumberButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('periodNumber', $scope.periodNumberReadonly);
	});
	$('#classButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('class', $scope.classReadonly);
	});
	$('#notificationTypesButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('notificationTypes', $scope.notificationTypereadOnly);
	});
    $('#frequencyButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('frequency', $scope.notificationFrequencyreadOnly);
	});
	$('#notificationMonthButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('notificationMonth', $scope.monthreadOnly);
	});
    $('#notificationDaysButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('notificationDays', $scope.dayreadOnly);
	});
	$('#communicationButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('communication', $scope.mediaCommunicationreadOnly);
	});
	$('#HolidayYearButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('HolidayYear', $scope.yearreadOnly);
	});
	$('#StandardButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('Standard', $scope.standardreadOnly);
	});
	$('#sectionButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('section', $scope.sectionreadOnly);
	});
	$('#userStatusButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('userStatus', $scope.statusReadonly);
	});
	
	$('#foreNoonButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('fromNoon', $scope.fromReadOnly);
	});
	$('#toNoonButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('toNoon', $scope.toReadOnly);
	});
	$('#participateButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('participate', $scope.participatereadOnly);
	});
	$('#instituteIdButton').click(function () {
		var $scope = angular.element(document.getElementById('MainCtrl')).scope();
		fnSelectButtonClick('instituteId', $scope.instituteIdreadOnly);
	});
	$('#batchLayerButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('batchLayer', $scope.layerreadOnly);
	});
	
	$('#ExecutionFrequencyButton').click(function () {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		fnSelectButtonClick('ExecutionFrequency', $scope.executionFrequencyreadOnly);
	});
	
	$("#feeType").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('feeType');
		}
	});

	$("#paymentMode").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('paymentMode');
		}
	});
	$("#startTimeHour").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('startTimeHour');
		}
	});
	$("#startTimeMin").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('startTimeMin');
		}
	});
	$("#endTimeHour").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('endTimeHour');
		}
	});
	$("#endTimeMin").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('endTimeMin');
		}
	});
	$("#standardID").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('standardID');
		}
	});
	$("#Section").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('Section');
		}
	});
	$("#subject").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('subject');
		}
	});
	$("#examType").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('examType');
		}
	});
	$("#examHall").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('examHall');
		}
	});
	$("#activityLevel").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('activityLevel');
		}
	});
	$("#activityVenue").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('activityVenue');
		}
	});
	$("#authStatus").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('authStatus');
		}
	});
	$("#recordStatus").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('recordStatus');
		}
	});
	$("#studentStatus").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('studentStatus');
		}
	});
	$("#SubjectName").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('SubjectName');
		}
	});
	$("#assignmentType").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('assignmentType');
		}
	});
	$("#Status").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('Status');
		}
	});
	$("#leaveType").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('leaveType');
		}
	});
	$("#periodNumber").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('periodNumber');
		}
	});
	$("#class").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('class');
		}
	});
	$("#notificationTypes").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('notificationTypes');
		}
	});
	$("#frequency").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('frequency');
		}
	});
	$("#notificationDays").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('notificationDays');
		}
	});
	$("#notificationMonth").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('notificationMonth');
		}
	});
	$("#communication").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('communication');
		}
	});
	$("#userStatus").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('userStatus');
		}
	});
	$("#functionID").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('functionID');
		}
	});
	$("#HolidayYear").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('HolidayYear');
		}
	});
	$("#Standard").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('Standard');
		}
	});
	$("#section").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('section');
		}
	});
	$("#fromNoon").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('fromNoon');
		}
	});
	$("#toNoon").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('toNoon');
		}
	});
	$("#participate").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('participate');
		}
	});
	$("#instituteId").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('instituteId');
		}
	});
	
	$("#batchLayer").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('batchLayer');
		}
	});
	
	$("#ExecutionFrequency").selectmenu({
		select: function (event, ui) {
			fnSelectChooseEvent('ExecutionFrequency');
		}
	});

}

function fnSelectButtonClick(id, readOnly) {
	if (readOnly)
		$("#" + id).selectmenu("option", "disabled", true);
	else {
		$("#" + id).selectmenu("option", "disabled", false);
		$("#" + id).selectmenu("open");

	}

}

function fnSelectChooseEvent(id) {
	if (!responseShow) {
		var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
		switch (id) {
			case 'feeType':
				$scope.feeType = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'paymentMode':
				$scope.paymentMode = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'startTimeHour':
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$scope.periodTimingsRecord.startTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$scope.SubjectschedulesRecord.startTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}/* else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$scope.MondayRecord.startTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$scope.TuesdayRecord.startTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$scope.WednesdayRecord.startTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$scope.ThursdayRecord.startTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$scope.FridayRecord.startTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}*/
				break;
			case 'startTimeMin':
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$scope.periodTimingsRecord.startTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$scope.SubjectschedulesRecord.startTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} /*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$scope.MondayRecord.startTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$scope.TuesdayRecord.startTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$scope.WednesdayRecord.startTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$scope.ThursdayRecord.startTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$scope.FridayRecord.startTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}*/
				break;
			case 'endTimeHour':
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$scope.periodTimingsRecord.endTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$scope.SubjectschedulesRecord.endTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} /*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$scope.MondayRecord.endTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$scope.TuesdayRecord.endTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$scope.WednesdayRecord.endTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$scope.ThursdayRecord.endTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$scope.FridayRecord.endTime.hour = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}*/
				break;
			case 'endTimeMin':
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$scope.periodTimingsRecord.endTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$scope.SubjectschedulesRecord.endTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} /*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$scope.MondayRecord.endTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$scope.TuesdayRecord.endTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$scope.WednesdayRecord.endTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$scope.ThursdayRecord.endTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$scope.FridayRecord.endTime.min = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}*/
				break;
			case 'standardID':
				$scope.standard = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				fnStdSelectHandler($scope);
				
				break;
			case 'Section':
				$scope.section = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'examType':
				$scope.exam = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'subject':
			     if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
				$scope.SubjectschedulesRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				/*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
				$scope.MondayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				 else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
				$scope.TuesdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				 else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
				$scope.WednesdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				  else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
				$scope.ThursdayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				  else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
				$scope.FridayRecord.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }*/
				 
				 var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);
				break;
			case 'examHall':
				$scope.SubjectschedulesRecord.hall = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'activityLevel':
				$scope.level = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'activityVenue':
				$scope.venue = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'authStatus':
				$scope.authStat = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'recordStatus':
				$scope.recordStat = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'studentStatus':
				$scope.status = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'SubjectName':
				$scope.subjectName = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'assignmentType':
				$scope.assignmentType = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'Status':
				$scope.status = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'leaveType':
				$scope.type = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			case 'periodNumber':
				/*if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$scope.MondayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$scope.TuesdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$scope.WednesdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$scope.ThursdayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$scope.FridayRecord.periodNumber = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}*/
				
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);				
				break;
			case 'class':
				/*if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$scope.MondayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$scope.TuesdayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$scope.WednesdayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$scope.ThursdayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$scope.FridayRecord.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}*/
				 if ($scope.general != null && $scope.general != '') {
					$scope.general.class = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				}
				var fn = window["fn"+MenuName+"SelectChooseEventHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
				case 'notificationTypes':
				$scope.notificationType = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'frequency':
				$scope.notificationFrequency = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'notificationDays':
				$scope.day = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break; 
				case 'notificationMonth':
				$scope.month = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'communication':
				$scope.mediaCommunication = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'userStatus':
				$scope.status = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'functionID':
				$scope.functionID = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
			    case 'HolidayYear':
				$scope.year = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'Standard':
				 if ($scope.GroupMappingRecord != null && $scope.GroupMappingRecord != '') {
				$scope.GroupMappingRecord.standard = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				 else if ($scope.studentClassRoleMappingRecord != null && $scope.studentClassRoleMappingRecord != '') {
				$scope.studentClassRoleMappingRecord.standard = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				break;
				case 'section':
				 if ($scope.GroupMappingRecord != null && $scope.GroupMappingRecord != '') {
				$scope.GroupMappingRecord.section = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				  else if ($scope.studentClassRoleMappingRecord != null && $scope.studentClassRoleMappingRecord != '') {
				$scope.studentClassRoleMappingRecord.section = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				 }
				break;
				case 'fromNoon':
				$scope.fromNoon = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'toNoon':
				$scope.toNoon = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'participate':
				$scope.participate = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'instituteId':
				$scope.instituteID = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'batchLayer':
				$scope.layer = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
				case 'ExecutionFrequency':
				$scope.executionFrequency = $('#' + id + '-button').children('span.ui-selectmenu-text').text();
				break;
		}
		// if (!responseShow)
		//{	
		$scope.$apply();
		if (id == 'standardID') {
			$('#Section').selectmenu('refresh', true);
		}
		else if (id=='class' && MenuName=='TeacherTimeTable') {
		$('#periodNumber').selectmenu('refresh', true);
		}
	} else {
		responseShow = false;
	}

}

function fnSelectRefresh(id) {

	var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();


	id.forEach(fnRefresh);

	function fnRefresh(value, index, array) {
		switch (value) {
			case 'feeType':
				responseShow = true;
				$('#' + value).val($scope.feeType);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'paymentMode':
				responseShow = true;
				$('#' + value).val($scope.paymentMode);
				//$('#'+id).selectmenu("value", $scope.paymentMode);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'startTimeHour':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} /*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}*/
				break;
			case 'startTimeMin':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				} /*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				}*/
				break;
			case 'endTimeHour':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} /*else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}*/
				break;
			case 'endTimeMin':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				} /*else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				}*/
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
			case 'examType':
				responseShow = true;
				$('#' + value).val($scope.exam);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'subject':
				responseShow = true;
				if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
				$('#' + value).val($scope.SubjectschedulesRecord.subjectName);
				$('#' + value).selectmenu('refresh', true);
				}
				/*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
				$('#' + value).val($scope.MondayRecord.subjectName);
				$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
				$('#' + value).val($scope.TuesdayRecord.subjectName);
				$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
				$('#' + value).val($scope.WednesdayRecord.subjectName);
				$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
				$('#' + value).val($scope.ThursdayRecord.subjectName);
				$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
				$('#' + value).val($scope.FridayRecord.subjectName);
				$('#' + value).selectmenu('refresh', true);
				}*/
				
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,id);	
				break;
			case 'examHall':
				responseShow = true;
				$('#' + value).val($scope.SubjectschedulesRecord.hall);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'activityLevel':
				responseShow = true;
				$('#' + value).val($scope.level);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'activityVenue':
				responseShow = true;
				$('#' + value).val($scope.venue);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'authStatus':
				responseShow = true;
				$('#' + value).val($scope.authStat);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'recordStatus':
				responseShow = true;
				$('#' + value).val($scope.recordStat);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'studentStatus':
				responseShow = true;
				$('#' + value).val($scope.status);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'SubjectName':
				responseShow = true;
				$('#' + value).val($scope.subjectName);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'assignmentType':
				responseShow = true;
				$('#' + value).val($scope.assignmentType);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'Status':
				responseShow = true;
				$('#' + value).val($scope.status);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'leaveType':
				responseShow = true;
				$('#' + value).val($scope.type);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'periodNumber':
				responseShow = true;
				/*if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				}*/
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,id);	
				break;
			case 'class':
				responseShow = true;
				/*if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				}*/
				 if ($scope.general != null && $scope.general != '') {
					$('#' + value).val($scope.general.class);
					$('#' + value).selectmenu('refresh', true);
				}
				var fn = window["fn"+MenuName+"SelectRefresh"];
                if (typeof fn === "function") fn($scope,id);	
				break;
				case 'notificationTypes':
				responseShow = true;
				$('#' + value).val($scope.notificationType);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'frequency':
				responseShow = true;
				$('#' + value).val($scope.notificationFrequency);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'notificationMonth':
				responseShow = true;
				$('#' + value).val($scope.month);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'notificationDays':
				responseShow = true;
				$('#' + value).val($scope.day);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'communication':
				responseShow = true;
				$('#' + value).val($scope.mediaCommunication);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'userStatus':
				responseShow = true;
				$('#' + value).val($scope.status);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'functionID':
				responseShow = true;
				$('#' + value).val($scope.functionID);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'HolidayYear':
				responseShow = true;
				$('#' + value).val($scope.year);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'Standard':
				responseShow = true;
				if ($scope.GroupMappingRecord != null && $scope.GroupMappingRecord != '') {
				$('#' + value).val($scope.GroupMappingRecord.standard);
				$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.studentClassRoleMappingRecord != null && $scope.studentClassRoleMappingRecord != '') {
				$('#' + value).val($scope.studentClassRoleMappingRecord.standard);
				$('#' + value).selectmenu('refresh', true);
				}
				break;
				case 'section':
				responseShow = true;
				if ($scope.GroupMappingRecord != null && $scope.GroupMappingRecord != '') {
				$('#' + value).val($scope.GroupMappingRecord.section);
				$('#' + value).selectmenu('refresh', true);
				}
			     else if ($scope.studentClassRoleMappingRecord != null && $scope.studentClassRoleMappingRecord != '') {
				$('#' + value).val($scope.studentClassRoleMappingRecord.section);
				$('#' + value).selectmenu('refresh', true);
				}
				break;
				case 'fromNoon':
				responseShow = true;
				$('#' + value).val($scope.fromNoon);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'toNoon':
				responseShow = true;
				$('#' + value).val($scope.toNoon);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'participate':
				responseShow = true;
				$('#' + value).val($scope.participate);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'instituteId':
				responseShow = true;
				$('#' + value).val($scope.instituteID);
				$('#' + value).selectmenu('refresh', true);
				break;
				
				case 'batchLayer':
				responseShow = true;
				$('#' + value).val($scope.layer);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'ExecutionFrequency':
				responseShow = true;
				$('#' + value).val($scope.executionFrequency);
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
			case 'feeType':
				responseShow = true;
				$('#' + value).val($scope.feeType);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'paymentMode':
				responseShow = true;
				$('#' + value).val($scope.paymentMode);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'startTimeHour':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				 /*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				 else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.startTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}*/
				break;
			case 'startTimeMin':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				/*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.startTime.min);
					$('#' + value).selectmenu('refresh', true);
				}*/
				break;
			case 'endTimeHour':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				/*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.endTime.hour);
					$('#' + value).selectmenu('refresh', true);
				}*/
				break;
			case 'endTimeMin':
				responseShow = true;
				if ($scope.periodTimingsRecord != null && $scope.periodTimingsRecord != '') {
					$('#' + value).val($scope.periodTimingsRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				/*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
					else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.endTime.min);
					$('#' + value).selectmenu('refresh', true);
				}*/
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
			case 'examType':
				responseShow = true;
				$('#' + value).val($scope.exam);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'subject':
				responseShow = true;
				if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.subjectName);
					$('#' + value).selectmenu('refresh', true);
				}
				/*else if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.subjectName);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.subjectName);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.subjectName);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.subjectName);
					$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.subjectName);
					$('#' + value).selectmenu('refresh', true);
				}*/
				
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
			case 'examHall':
				responseShow = true;
				if ($scope.SubjectschedulesRecord != null && $scope.SubjectschedulesRecord != '') {
					$('#' + value).val($scope.SubjectschedulesRecord.hall);
					$('#' + value).selectmenu('refresh', true);
				}
				break;
			case 'activityLevel':
				responseShow = true;
				$('#' + value).val($scope.level);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'activityVenue':
				responseShow = true;
				$('#' + value).val($scope.venue);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'authStatus':
				responseShow = true;
				$('#' + value).val($scope.authStat);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'recordStatus':
				responseShow = true;
				$('#' + value).val($scope.recordStat);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'studentStatus':
				responseShow = true;
				$('#' + value).val($scope.status);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'SubjectName':
				responseShow = true;
				$('#' + value).val($scope.subjectName);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'assignmentType':
				responseShow = true;
				$('#' + value).val($scope.assignmentType);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'Status':
				responseShow = true;
				$('#' + value).val($scope.status);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'leaveType':
				responseShow = true;
				$('#' + value).val($scope.type);
				$('#' + value).selectmenu('refresh', true);
				break;
			case 'periodNumber':
				responseShow = true;
				/*if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.periodNumber);
					$('#' + value).selectmenu('refresh', true);
				}*/
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
			case 'class':
				responseShow = true;
				/*if ($scope.MondayRecord != null && $scope.MondayRecord != '') {
					$('#' + value).val($scope.MondayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.TuesdayRecord != null && $scope.TuesdayRecord != '') {
					$('#' + value).val($scope.TuesdayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.WednesdayRecord != null && $scope.WednesdayRecord != '') {
					$('#' + value).val($scope.WednesdayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.ThursdayRecord != null && $scope.ThursdayRecord != '') {
					$('#' + value).val($scope.ThursdayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				} else if ($scope.FridayRecord != null && $scope.FridayRecord != '') {
					$('#' + value).val($scope.FridayRecord.class);
					$('#' + value).selectmenu('refresh', true);
				}*/
				 if ($scope.general != null && $scope.general != '') {
					$('#' + value).val($scope.general.class);
					$('#' + value).selectmenu('refresh', true);
				}
				var fn = window["fn"+MenuName+"ResponseHandler"];
                if (typeof fn === "function") fn($scope,id);	
				break;
				case 'notificationTypes':
				responseShow = true;
				$('#' + value).val($scope.notificationType);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'frequency':
				responseShow = true;
				$('#' + value).val($scope.notificationFrequency);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'notificationMonth':
				responseShow = true;
				$('#' + value).val($scope.month);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'notificationDays':
				responseShow = true;
				$('#' + value).val($scope.day);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'communication':
				responseShow = true;
				$('#' + value).val($scope.mediaCommunication);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'userStatus':
				responseShow = true;
				$('#' + value).val($scope.status);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'functionID':
				responseShow = true;
				$('#' + value).val($scope.functionID);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'HolidayYear':
				responseShow = true;
				$('#' + value).val($scope.year);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'Standard':
				responseShow = true;
				if ($scope.GroupMappingRecord != null && $scope.GroupMappingRecord != '') {
				$('#' + value).val($scope.GroupMappingRecord.standard);
				$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.studentClassRoleMappingRecord != null && $scope.studentClassRoleMappingRecord != '') {
				$('#' + value).val($scope.studentClassRoleMappingRecord.standard);
				$('#' + value).selectmenu('refresh', true);
				}
				break;
				case 'section':
				responseShow = true;
				if ($scope.GroupMappingRecord != null && $scope.GroupMappingRecord != '') {
				$('#' + value).val($scope.GroupMappingRecord.section);
				$('#' + value).selectmenu('refresh', true);
				}
				else if ($scope.studentClassRoleMappingRecord != null && $scope.studentClassRoleMappingRecord != '') {
				$('#' + value).val($scope.studentClassRoleMappingRecord.section);
				$('#' + value).selectmenu('refresh', true);
				}
				break;
				case 'fromNoon':
				responseShow = true;
				$('#' + value).val($scope.fromNoon);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'toNoon':
				responseShow = true;
				$('#' + value).val($scope.toNoon);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'participate':
				responseShow = true;
				$('#' + value).val($scope.participate);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'instituteId':
				responseShow = true;
				$('#' + value).val($scope.instituteID);
				$('#' + value).selectmenu('refresh', true);
				break;
				
				case 'batchLayer':
				responseShow = true;
				$('#' + value).val($scope.layer);
				$('#' + value).selectmenu('refresh', true);
				break;
				case 'ExecutionFrequency':
				responseShow = true;
				$('#' + value).val($scope.executionFrequency);
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
			case 'feeType':
				fnSelectEventHandle($scope.feeTypereadOnly, value);

				break;


			case 'paymentMode':
				fnSelectEventHandle($scope.paymentModereadOnly, value);
				break;

			case 'startTimeHour':
				fnSelectEventHandle($scope.startTimereadOnly, value);
				break;

			case 'startTimeMin':
				fnSelectEventHandle($scope.startTimereadOnly, value);
				break;

			case 'endTimeHour':
				fnSelectEventHandle($scope.endTimereadOnly, value);
				break;
			case 'endTimeMin':
				fnSelectEventHandle($scope.endTimereadOnly, value);
				break;
			case 'standardID':
				fnSelectEventHandle($scope.standardreadOnly, value);
				break;
			case 'Section':
				fnSelectEventHandle($scope.sectionreadOnly, value);
				break;
			case 'examType':
				fnSelectEventHandle($scope.examtypereadOnly, value);
				break;
			case 'subject':
				fnSelectEventHandle($scope.subjectNamereadOnly, value);
				break;
			case 'examHall':
				fnSelectEventHandle($scope.examHallreadOnly, value);
				break;
			case 'activityLevel':
				fnSelectEventHandle($scope.levelreadOnly, value);
				break;
			case 'activityVenue':
				fnSelectEventHandle($scope.venuereadOnly, value);
				break;
			case 'authStatus':
				fnSelectEventHandle($scope.authStatreadOnly, value);
				break;
			case 'recordStatus':
				fnSelectEventHandle($scope.recordStatreadOnly, value);
				break;
			case 'studentStatus':
				fnSelectEventHandle($scope.statusreadOnly, value);
				break;
			case 'SubjectName':
				fnSelectEventHandle($scope.subjectNamereadOnly, value);
				break;
			case 'assignmentType':
				fnSelectEventHandle($scope.assignmentTypereadOnly, value);
				break;
			case 'Status':
				fnSelectEventHandle($scope.statusReadonly, value);
				break;
			case 'leaveType':
				fnSelectEventHandle($scope.typeReadOnly, value);
				break;
			case 'periodNumber':
				fnSelectEventHandle($scope.periodNumberReadonly, value);
				break;
			case 'class':
				fnSelectEventHandle($scope.classReadonly, value);
				break;
				case 'notificationTypes':
				fnSelectEventHandle($scope.notificationTypereadOnly, value);
				break;
				case 'frequency':
				fnSelectEventHandle($scope.notificationFrequencyreadOnly, value);
				break;
				case 'notificationMonth':
				fnSelectEventHandle($scope.monthreadOnly, value);
				break;
				case 'notificationDays':
				fnSelectEventHandle($scope.dayreadOnly, value);
				break;
				case 'communication':
				fnSelectEventHandle($scope.mediaCommunicationreadOnly, value);
				break;
				case 'userStatus':
				fnSelectEventHandle($scope.statusReadonly, value);
				break;
				case 'functionID':
				fnSelectEventHandle($scope.functionIDreadOnly, value);
				break;
				case 'HolidayYear':
				fnSelectEventHandle($scope.yearreadOnly, value);
				break;
				case 'Standard':
				fnSelectEventHandle($scope.standardreadOnly, value);
				break;
				case 'section':
				fnSelectEventHandle($scope.sectionreadOnly, value);
				break;
				case 'section':
				fnSelectEventHandle($scope.sectionreadOnly, value);
				break;
				case 'fromNoon':
				fnSelectEventHandle($scope.fromReadOnly, value);
				break;
				case 'toNoon':
				fnSelectEventHandle($scope.toReadOnly, value);
				break;
				case 'participate':
				fnSelectEventHandle($scope.participatereadOnly, value);
				break;
				case 'instituteId':
				fnSelectEventHandle($scope.instituteIdreadOnly, value);
				break;
				case 'batchLayer':
				fnSelectEventHandle($scope.layerreadOnly, value);
				break;
				case 'ExecutionFrequency':
				fnSelectEventHandle($scope.executionFrequencyreadOnly, value);
				break;

		}

	}
}

function fnSelectEventHandle(readOnly, id) {
	if (readOnly) {
		fnSelectdisableEvent(id);
	} else {

		fnSelectenableEvent(id);
	}

}

function fnSelectdisableEvent(id) {
	$("#" + id).selectmenu("option", "disabled", true);
	$("#" + id + "-button").removeClass("selectEnabled");
	// $("#"+id).selectmenu( "option", "classes.ui-state-disabled","selectDisabled");      
	$("#" + id + '-button').addClass("selectDisabled");
}

function fnSelectenableEvent(id) {
	$("#" + id).selectmenu("option", "disabled", false);
	//$("#"+value).selectmenu( "option", "classes.selectDisabled",null);
	// $(.selectDisabled).css("backgroundColor","#FFFFF");
	$("#" + id + "-button").removeClass("selectDisabled");
	//$("#"+id).selectmenu( "enable" );
	//$("#"+id).selectmenu( "option", "classes.ui-button","selectEnabled");
	$("#" + id + "-button").addClass("selectEnabled");

}

function fnGetSelectBoxdata(id)
{
      var selectBox = JSON.parse(sessionStorage.getItem('selectBox'));
    Institute.ClassMaster=selectBox.ClassMaster;
    Institute.FeeMaster=selectBox.FeeMaster;
    Institute.NotificationMaster=selectBox.NotificationMaster;

    //Institute.PeriodMaster=selectBox.PeriodMaster;
    Institute.SubjectMaster=selectBox.SubjectMaster;
    Institute.ExamMaster=selectBox.ExamMaster;
      


    id.forEach(fngetSelectMaster);

	function fngetSelectMaster(value, index, array) {
	var msgheader={msgID:"",
            service:"SelectBoxMaster",
            operation:"View",
			instituteID:window.parent.Institute.ID,
			userID:window.parent.User.Id,
                        key:"",
                        token:window.parent.nokotser,
                        source:"CohesiveFrontEnd",
            businessEntity:[],
            status:""
};

var callbackend =false;

//if(value == "class" || value =="feeType" || value == "periodNumber"|| value == "notificationTypes"|| value == "subject"||  value == "examType"){
if(value == "class" || value =="feeType" || value == "notificationTypes"|| value == "subject"||  value == "examType"){
switch(value)
{
    case "class":
        if(Institute.ClassMaster.length>0)
        { 
            callbackend =false;
        }
        else{
           callbackend=true; 
        }
        
        var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };
                        break;
        case "feeType":    
        if(Institute.FeeMaster.length>0)
        { 
            callbackend =false;
        }
        else{
           callbackend=true; 
        } 
            
            
        var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };   
                     break;
        case "periodNumber":
            
         if(Institute.PeriodMaster.length>0)
        { 
            callbackend =false;
        }
        else{
           callbackend=true; 
        }     
        var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };    
                        break;
        case "notificationTypes":   
        if(Institute.NotificationMaster.length>0)
        { 
            callbackend =false;
        }
        else{
           callbackend=true; 
        }     
            
        var request={header:msgheader,
                     body:{master:value,
                    input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };     
                    break;
        case "subject":    
        if(Institute.SubjectMaster.length>0)
        { 
            callbackend =false;
        }
        else{
           callbackend=true; 
        }      
            
            
        var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
        break;
         case "standardID":
        var request={header:msgheader,
                     body:{master:value,
                   input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
        break;
           case "Section":
        var request={header:msgheader,
                     body:{master:value,
                    input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
        break;
           case "examType":
                 if(Institute.ExamMaster.length>0)
        { 
            callbackend =false;
        }
        else{
           callbackend=true; 
        }     
        var request={header:msgheader,
                     body:{master:value,
                    input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
        break;
}
 
 
 if(TEST=="YES"){
        
           var URLSelect="https://cohesivetest.ibdtechnologies.com/CohesiveGateway/Institute/SelectBoxMasterService";
        
       }else{
           
           var URLSelect="https://cohesive.ibdtechnologies.com/CohesiveGateway/Institute/SelectBoxMasterService";
       }
 
    if(callbackend==true)
    {
                $.ajax({
      //url: $('#imgUpld').attr('action'),
      
//      url:"https://cohesive.ibdtechnologies.com/CohesiveGateway/Institute/SelectBoxMasterService",
      url:URLSelect,
      type: 'PUT',
      //enctype:'application/json',
      cache:true,
      data : JSON.stringify(request),
      processData: false,
      contentType: "application/json;charset=utf-8",
      success: function(data){
        //console.log('form submitted.');
        //if(xhr.status == '200')
        //{  //$("#logo").attr("src","/img/uploads/"+xhr.responseText);
             //window.parent.fn_hide_parentspinner();
             if(data.header.status=="success")
             {
               // var res=xhr.split("~"); 
              
                  // src.fileName= res[2];  
                   //src.uploadID= res[1]; 
                   fnPostSelectResponse(data);
                    var fn = window["fn"+MenuName+"postSelectBoxMaster"];
                     if (typeof fn === "function") 
                     {  
                         fn();
                     } 
                   //fnPostImageUpload(id,res[2],res[1]);
              } 
              else 
              {
                  //var res=xhr.split("~");
                  
                  for(i=0;i<data.error.length;i++)
	             {
 	               if(data.error[i].errorCode=='BS_VAL_101')
                       {
                         var error= [{
			errorCode: "",
			errorMessage: ""
		           }];
	                    error[0].errorCode=data.error[i].errorCode;
                            error[0].errorMessage=data.error[i].errorMessage;
 
                        window.parent.fnMainPagewithError(error); 
                        window.parent.$("#frame").attr('src', '');
                      return;
                    } 
                      
                     }
                   window.parent.$("#frame").attr('src', '');
                   window.parent.fnMainPagewithError(data.error);
                   //fn_show_backend_exception(data.error);

                }
                  
                             
              //}    
                  
          return true; 
          },
      error:function(xhr)
      {
          window.parent.fn_hide_parentspinner();
          var error= [{
			errorCode: "",
			errorMessage: ""
		           }];
	                    error[0].errorCode=xhr.status;
                            error[0].errorMessage=xhr.responseText;
                     
          //fn_show_backend_exception(error);
         window.parent.$("#frame").attr('src', '');
         window.parent.fnMainPagewithError(error);
                   
          return false;
      }
    });
     
                }
      else
      {
       var fn = window["fn"+MenuName+"postSelectBoxMaster"];
                     if (typeof fn === "function") 
                     {  
                         fn();
                     }          
                
                }           
                
	}
        else
        {
           var fn = window["fn"+MenuName+"postSelectBoxMaster"];
                     if (typeof fn === "function") 
                     {  
                         fn();
                     }          
        }
    
}
}

function fnGetSelectBoxdataforMain(id)
{
    id.forEach(fngetSelectMaster);

	function fngetSelectMaster(value, index, array) {
	var msgheader={msgID:"",
            service:"SelectBoxMaster",
            operation:"View",
			instituteID:window.Institute.ID,
			userID:window.User.Id,
                        key:"",
                        token:$('#nokotser').val(),
                        source:"CohesiveFrontEnd",
            businessEntity:[],
            status:""
};



//if(value == "class" || value =="feeType" || value == "periodNumber"|| value == "notificationTypes"|| value == "subject"||  value == "standardID" || value == "Section"|| value == "examType"){
if(value == "class" || value =="feeType" ||value == "notificationTypes"|| value == "subject"||  value == "standardID" || value == "Section"|| value == "examType"){
switch(value)
{
    case "class":
       var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };
                        break;
        case "feeType":
        var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };  
                    break;
        case "periodNumber":
        var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };    
                        break;
        case "notificationTypes":
        var request={header:msgheader,
                     body:{master:value,
                    input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };     
                    break;
        case "subject":
        var request={header:msgheader,
                     body:{master:value,
                     input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
        break;
         case "standardID":
        var request={header:msgheader,
                     body:{master:value,
                   input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
        break;
           case "Section":
        var request={header:msgheader,
                     body:{master:value,
                    input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
        break;
           case "examType":
        var request={header:msgheader,
                     body:{master:value,
                    input:[{entityName:"instituteID",entityValue:window.parent.Institute.ID}]   
                         },
                     audit:{},
                     error:{}
                        };            
                        
            break;
}

           if(TEST=="YES"){
        
           var URLSelect="https://cohesivetest.ibdtechnologies.com/CohesiveGateway/Institute/SelectBoxMasterService";
        
       }else{
           
           var URLSelect="https://cohesive.ibdtechnologies.com/CohesiveGateway/Institute/SelectBoxMasterService";
       }

                $.ajax({
      //url: $('#imgUpld').attr('action'),
      
//      url:"https://cohesive.ibdtechnologies.com/CohesiveGateway/Institute/SelectBoxMasterService",
       url:URLSelect,
       type: 'PUT',
      //enctype:'application/json',
      cache:true,
      data : JSON.stringify(request),
      processData: false,
      contentType: "application/json;charset=utf-8",
      success: function(data){
        //console.log('form submitted.');
        //if(xhr.status == '200')
        //{  //$("#logo").attr("src","/img/uploads/"+xhr.responseText);
             //window.parent.fn_hide_parentspinner();
             if(data.header.status=="success")
             {
               // var res=xhr.split("~"); 
              
                  // src.fileName= res[2];  
                   //src.uploadID= res[1]; 
                   fnPostSelectResponse(data);
                    /*var fn = window["fn"+MenuName+"postSelectBoxMaster"];
                     if (typeof fn === "function") 
                     {  
                         fn();
                     }*/ 
                   //fnPostImageUpload(id,res[2],res[1]);
              } 
              else 
              {
                  //var res=xhr.split("~");
                  
                  for(i=0;i<data.error.length;i++)
	             {
 	               if(data.error[i].errorCode=='BS_VAL_101')
                       {
                         var error= [{
			errorCode: "",
			errorMessage: ""
		           }];
	                    error[0].errorCode=data.error[i].errorCode;
                            error[0].errorMessage=data.error[i].errorMessage;
 
                        window.parent.fnMainPagewithError(error); 
                        window.parent.$("#frame").attr('src', '');
                      return;
                    } 
                      
                     }
                   window.parent.$("#frame").attr('src', '');
                   window.parent.fnMainPagewithError(data.error);
                   //fn_show_backend_exception(data.error);

                }
                  
                             
              //}    
                  
          return true; 
          },
      error:function(xhr)
      {
          window.parent.fn_hide_parentspinner();
          var error= [{
			errorCode: "",
			errorMessage: ""
		           }];
	                    error[0].errorCode=xhr.status;
                            error[0].errorMessage=xhr.responseText;
                     
          //fn_show_backend_exception(error);
         window.parent.$("#frame").attr('src', '');
         window.parent.fnMainPagewithError(error);
                   
          return false;
      }
    });
                
                }           
                
	}
    
}



function fnPostSelectResponse(data)
{
    var selectBox = JSON.parse(sessionStorage.getItem('selectBox'));
    
    
switch(data.body.master)
{
    
    
    case "class":
    selectBox.ClassMaster=data.body.ClassMaster;
    break;
    case "feeType":
    selectBox.FeeMaster=data.body.FeeMaster;
    break;
    case "notificationTypes":
    selectBox.NotificationMaster=data.body.NotificationMaster;
    break;
    case "periodNumber":
    selectBox.PeriodMaster=data.body.PeriodMaster;
    break;
     case "subject":
    selectBox.SubjectMaster=data.body.SubjectMaster;
    break;
    case "standardID":
    selectBox.StandardMaster=data.body.StandardMaster;
    break;
     case "Section":
    selectBox.StandardMaster=data.body.StandardMaster;
    break;
     case "examType":
    selectBox.ExamMaster=data.body.ExamMaster;
    break;
        
        }       
        
   //var selectBox = JSON.parse(sessionStorage.getItem('selectBox'));  
   sessionStorage.setItem('selectBox',JSON.stringify(selectBox));
}
