/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getSelectedValue(master,value){
    
for(let item of master){
    if(value == item.id){
        if(item.id != null &&  item.id != '' ){
            return item.value;
        }
       
    }
}
return '';
}

var selectMaster = {
    ClassMaster: [],
    AuthStatusMaster: [{ value: "--Choose AuthStatus--", id: "" }, { value: "Authorised", id: "A" }, { value: "Unauthorised", id: "U" }, { value: "Rejected", id: "R" }],
    RecStatusMaster: [{ name: "Select option", value: "" }, { name: "Open", value: "O" }, { name: "Deleted", value: "D" }],
    PayMentMaster: [{ name: "Cash", value: "C" }, { name: "Cheque", value: "Q" }, { name: "NetBanking", value: "N" }, { name: "Others", value: "O" }],
    MediaCommunication: [{ name: "Mail", value: "M" }, { name: "SMS", value: "S" }, { name: "Both", value: "B" }],
    DayMaster: [{ name: "Monday", value: "Mon" }, { name: "Tuesday", value: "Tue" }, { name: "Wednesday", value: "Wed" }, { name: "Thursday", value: "Thu" }, { name: "Friday", value: "Fri" }, { name: "Saturday", value: "Sat" }, { name: "Sunday", value: "Sun" }],
    MonthMaster: [{ name: "January", value: "01" }, { name: "February", value: "02" }, { name: "March", value: "03" }, { name: "April", value: "04" }, { name: "May", value: "05" }, { name: "June", value: "06" }, { name: "July", value: "07" }, { name: "August", value: "08" }, { name: "September", value: "09" }, { name: "October", value: "10" }, { name: "November", value: "11" }, { name: "December", value: "12" }],
    FeatureMaster: ["Select option", "ALL", "ClassAttendance", "ClassExamSchedule", "ClassMark", "ClassStudentMapping", "ClassTimeTable", "ClassExamScheduleSummary", "ClassMarkSummary", "ClassTimeTableSummary", "ClassLevelConfiguration", "ECircular", "GeneralLevelConfiguration", "GroupMapping", "HolidayMaintenance", "InstituteAssignment", "InstituteFeeManagement", "InstituteOtherActivity", "InstitutePayment", "ManagementDashBoard", "Notification", "AssigneeSearchService", "AssignmentSearchService", "BatchSearchService", "FeeSearchService", "InstituteSearchService", "NotificationSearchService", "OtherActivitySearchService", "PaymentSearchService", "UserRoleSearchService", "StudentSearchService", "TeacherSearchService", "UserSearchService", "SelectBoxMaster", "StudentMaster", "InstituteAssignmentSummary", "InstituteOtherActivitySummary", "InstituteFeeManagementSummary", "InstitutePaymentSummary", "GroupMappingSummary", "HolidayMaintenanceSummary", "NotificationSummary", "TeacherMaster", "ParentDashBoard", "StudentECircular", "StudentAssignment", "StudentAttendance", "StudentCalender", "StudentExamSchedule", "StudentFeeManagement", "StudentLeaveManagement", "StudentNotification", "StudentOtherActivity", "StudentPayment", "StudentProfile", "StudentProgressCard", "StudentTimeTable", "StudentAssignmentSummary", "StudentAttendanceSummary", "StudentExamScheduleSummary", "StudentFeeManagementSummary", "StudentLeaveManagementSummary", "StudentPaymentManagementSummary", "StudentOtherActivitySummary", "StudentPaymentSummary", "StudentProfileSummary", "StudentProgressCardSummary", "StudentTimeTableSummary", "Payroll", "TeacherAttendance", "TeacherDashBoard", "TeacherCalender", "TeacherLeaveManagement", "TeacherProfile", "TeacherTimeTable", "TeacherAttendanceSummary", "TeacherLeaveManagementSummary", "TeacherProfileSummary", "TeacherTimeTableSummary", "UserProfileSummary", "UserRoleSummary", "UserRole", "UserProfile", "StudentSoftSkill", "TeacherECircular"],
    ProfileStatusMaster: [{ name: "Enable", value: "E" }, { name: "Disable", value: "D" }],
    StatusMaster: [{ name: "Completed", value: "C" }, { name: "Incomplete", value: "I" }],
    FeeStatus: [{ name: "--Choose Fee Status--", value: "" }, { name: "Pending", value: "P" }, { name: "OverDue", value: "O" }, { name: "Paid", value: "C" }],
    HourMaster: ["Hour", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"],
    MinMaster: ["Min", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"],
    AssignmentTypeMaster: [{ name: "Homework", value: "H" }, { name: "Term/Exam", value: "T" }, { name: "Punishment", value: "P" }, { name: "Improvement", value: "I" }],
    OtherActivityLevelMaster: [{ name: "State", value: "S" }, { name: "District", value: "D" }, { name: "International", value: "I" }, { name: "Internal", value: "E" }],
    YearMaster: ["2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"],
    NotificationFrequency: [{ name: "Daily", value: "D" }, { name: "Weekly", value: "W" }, { name: "FortNightly", value: "F" }, { name: "Monthly", value: "M" }, { name: "Quarterly", value: "Q" }, { name: "Instant", value: "I" }],
    NoonMaster: [{ name: "HalfDay-FN", value: "F" }, { name: "HalfDay-AN", value: "A" }, { name: "FullDay", value: "D" }],
    ParticipateMaster: [{ name: "Yes", value: "Y" }, { name: "No", value: "N" }],
    BatchMaster: [{ name: "Select option", value: "" }, { name: "Database", value: "D" }, { name: "Bussiness", value: "B" }, { name: "Report", value: "R" }],
    LeaveMaster: [{ name: "Sick", value: "S" }, { name: "Planned", value: "P" }, { name: "Casual", value: "C" }],
    LeaveMasterStatus: [{ name: "Pending", value: "U" }, { name: "Approved", value: "A" }, { name: "Rejected", value: "R" }],
    UserTypeMaster: [{ name: "Admin", value: "A" }, { name: "Teacher", value: "T" }, { name: "Parent", value: "P" }],
    DateMaster: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"],
    FeeMaster: [],
    PeriodMaster: ["Select option", "1", "2", "3", "4", "5", "6", "7", "8"],
    NotificationMaster: [],
    SubjectMaster: [],
    StandardMaster: [],
    ExamMaster: [],
    ActivityTypeMaster: [{ name: "Sports", value: "S" }, { name: "Culturals", value: "C" }],

    AttendanceNoonMaster: [{ name: "Forenoon", value: "F" }, { name: "AfterNoon", value: "A" }],

    AttendanceMaster: [{ name: "Period wise", value: "P" }, { name: "Day wise", value: "D" }, { name: "Noon wise", value: "N" }],

    GenderMaster: [{ value: "Male", id: "M" }, { value: "Female", id: "F" }, { value: "Others", id: "O" }],

    LanguageMaster: [{ value: "English", id: "EN" }, { value: "Tamil", id: "TN" }],

    SkillMaster: [{ name: "Communication", value: "1" }, { name: "Leadership", value: "2" }, { name: "Food Habit", value: "3" }, { name: "Being Social", value: "4" }, { name: "Team Work", value: "5" }, { name: "Discipline", value: "6" }, { name: "Dressing", value: "7" }],

    CategoryMaster: [{ name: "Outstanding", value: "1" }, { name: "Good", value: "2" }, { name: "Improvement Required", value: "3" }],

    CircularTypeMaster: [{ name: "Student", value: "S" }, { name: "Teacher", value: "T" }],

    StudentStatusMaster: [{ name: "--Choose StudentStatus--", value: "" }, { name: "Studying", value: "O" }, { name: "Studied", value: "D" }],

    RelationshipMaster: [{ value: "Father", id: "Father" }, { value: "Mother", id: "Mother" }, { value: "Grand Father", id: "Grand Father" }, { value: "Grand Mother", id: "Grand Mother" }, { value: "Guardian", id: "Guardian" }],

    BloodGroupMaster: [{ value: "A+", id: "A+" }, { value: "A-", id: "A-" }, { value: "B+", id: "B+" }, { value: "B-", id: "B-" }, { value: "O+", id: "O+" }, { value: "O-", id: "O-" }, { value: "AB+", id: "AB+" }, { value: "AB-", id: "AB-" }, { value: "A1B+", id: "A1B+" }, { value: "Others", id: "Others" }]

};

var selectBackendMaster = ['class', 'feeType', 'notificationTypes', 'subject', 'examType'];


async function fnPostSelectResponse   (data){
    var temp = await sessionStorage.getItem('selectBox');
    var selectBox;
    if (temp != null) {
        selectBox = await JSON.parse(temp);
    }
    else {
        selectBox = null;
    }

    if (selectBox == null) {
        selectBox = {
            ClassMaster: [],
            FeeMaster: [],
            NotificationMaster: [],
            PeriodMaster: [],
            SubjectMaster: [],
            ExamMaster: []

        };
    }
    let temData = [];

    switch (data.body.master) {
        case "class":
            for (let item of data.body.ClassMaster) {
                if (item != 'Select option' && item != '') {
                    temData.push({
                        value: item,
                        id:item
                    });
                }
                else{
                    temData.push({
                        value: '--Choose Class--',
                        id:''
                    });
                }
            }
            selectBox.ClassMaster = temData;
           selectMaster.ClassMaster = temData;
           break;
        case "feeType":
            for (let item of data.body.FeeMaster) {
                if (item != 'Select option' && item != '') {
                    temData.push({
                        value: item
                    });
                }
                else{
                    temData.push({
                        value: '--Choose Fee Type--',
                        id:''
                    });
            }
        }
            selectBox.FeeMaster = temData;
            selectMaster.FeeMaster = temData;
            break;
        case "notificationTypes":
            for (let item of data.body.NotificationMaster) {
                if (item != 'Select option' && item != '') {
                    temData.push({
                        value: item
                    });
                }
                 else{
                    temData.push({
                        value: '--Choose Notification Type--',
                        id:''
                    });
            }
            }
            selectBox.NotificationMaster = temData;
            selectMaster.NotificationMaster = temData;
            break;
        case "periodNumber":
            for (let item of data.body.PeriodMaster) {
                if (item != 'Select option' && item != '') {
                    temData.push({
                        value: item
                    });
                }
                 else{
                    temData.push({
                        value: '--Choose Period--',
                        id:''
                    });
            }
            }
            selectBox.PeriodMaster = temData;
            selectMaster.PeriodMaster = temData;
            break;
        case "subject":
            for (let item of data.body.SubjectMaster) {
                if (item != 'Select option' && item != '') {
                    temData.push({
                        value: item
                    });
                }
                 else{
                    temData.push({
                        value: '--Choose Subject--',
                        id:''
                    });
            }
            }
            selectBox.SubjectMaster = temData;
            selectMaster.SubjectMaster = temData;
            break;

        case "examType":
            for (let item of data.body.ExamMaster) {
                if (item != 'Select option' && item != '') {
                    temData.push({
                        value: item
                    });
                }
                 else{
                    temData.push({
                        value: '--Choose Exam Type--',
                        id:''
                    });
            }
            }
            selectBox.ExamMaster = temData;
            selectMaster.ExamMaster = temData;
            break;

    }

    await sessionStorage.setItem('selectBox', JSON.stringify(selectBox));
}




async function getSelectMaster () {
  try {
        var globalData = JSON.parse(await sessionStorage.getItem('GLOBAL'));
    }
    catch (err) {
        globalData = null;
    }


    var temp = await sessionStorage.getItem('selectBox');
    var selectBox;
    if (temp != null) {
        selectBox = JSON.parse(temp);
    }
    else {
        selectBox = null;
    }

    for (let value of selectBackendMaster) {
     
        var selectApiCall = true;
        switch (value) {
            case "class":
                if (selectBox != null && selectBox.ClassMaster.length > 0) {
                    selectApiCall = false;
                    selectMaster.ClassMaster = selectBox.ClassMaster;
                }
                break;
            case "feeType":
                if (selectBox != null && selectBox.FeeMaster.length > 0) {
                    selectApiCall = false;
                    selectMaster.FeeMaster = selectBox.FeeMaster;
                }

                break;
            case "notificationTypes":
                if (selectBox != null && selectBox.NotificationMaster.length > 0) {
                    selectApiCall = false;
                    selectMaster.NotificationMaster = selectBox.NotificationMaster;
                }

                break;
            case "periodNumber":
                if (selectBox != null && selectBox.PeriodMaster.length > 0) {
                    selectApiCall = false;
                    selectMaster.PeriodMaster = selectBox.PeriodMaster;
                }
                break;
            case "subject":
                if (selectBox != null && selectBox.SubjectMaster.length > 0) {
                    selectApiCall = false;
                    selectMaster.SubjectMaster = selectBox.SubjectMaster;
                }

                break;

            case "examType":
                if (selectBox != null && selectBox.ExamMaster.length > 0) {
                    selectApiCall = false;
                    selectMaster.ExamMaster = selectBox.ExamMaster;
                }

                break;
        }

        if (selectApiCall) {
            var resToken;
            var tempRst;
            var t;
            t = await sessionStorage.getItem('Rst');
            if (t != null) {
                tempRst = JSON.parse(t);
            }
            else {
                tempRst = null;
            }


            if (tempRst != null) {
                for (let item of tempRst) {
                    if (item.service == 'SelectBoxMaster') {
                        resToken = item.value;
                    }
                }
            }
            else {
                resToken = null;
            }


            if (resToken == null || resToken == '') {

                await callRequestToken(globalData, 'SelectBoxMaster');

                tempRst = JSON.parse(await sessionStorage.getItem('Rst'));

                for (let item of tempRst) {
                    if (item.service == 'SelectBoxMaster') {
                        resToken = item.value;
                    }

                }
}
            if (resToken != null && resToken != '') {
                var msgheader = {
                    msgID: "",
                    service: "SelectBoxMaster",
                    operation: "View",
                    instituteID: globalData.instituteID,
                    userID: globalData.userID,
                    key: "",
                    token: resToken,
                    source: "NewGenEducationMobile",
                    businessEntity: [],
                    status: ""
                };
                var request = {
                    header: msgheader,
                    body: {
                        master: value,
                        input: [{ entityName: "instituteID", entityValue: globalData.instituteID }]
                    },
                    audit: {},
                    error: {}
                };
$.ajax({
      url:getURL('Institute', 'SelectBoxMasterService'),
      type: 'PUT',
      cache:false,
      data : JSON.stringify(request),
      processData: false,
      contentType: 'application/json',
      dataType: 'json'
    }).done( function(response){
                 if (response.header.status == 'success') {
                     fnPostSelectResponse(response);
                 }
          }).fail(function(jqXHR, textStatus, errorThrown)
                    {
                     var error1= [{
                            errorCode: jqXHR.status,
                            errorMessage: jqXHR.responseText
                        }];
                      fn_show_backend_exception(error1);
                      return false;  
                      }); 

}

        }


    }

}
