/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//SessionObjects	

/*access = [{
		Menu: "TeacherTimeTable",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentTimeTable",
		Operation: {
			Query: true,
			Create: false,
			Edit: true,
			Delete: true,
			auth: true
		}
	},
	{
		Menu: "ClassTimeTable",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			auth: true
		}
	},
	{
		Menu: "ClassExamSchedule",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ClassMarkEntry",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentProgressCard",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "TeacherLeaveManagement",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentLeaveManagement",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentFeeManagement",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ClassFeeManagement",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "TeacherProfile",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentProfile",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ClassAttendance",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "TeacherAttendance",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentAttendance",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "OtherActivity",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "PayRoll",
		Operation: {
			Query: true,
			Create: false,
			Edit: false,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ClassAssignment",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentAssignment",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "TeacherCalender",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentCalender",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ComparisonAccrossAllExam",
		Operation: {
			Query: true,
			Create: false,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ComparisonAccrossAllSection",
		Operation: {
			Query: true,
			Create: false,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ExamReportStatistics",
		Operation: {
			Query: true,
			Create: false,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "Notification",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "InstituteFeePayment",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "UserProfile",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "GeneralLevelConfigurations",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "ClassLevelConfiguration",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "UserRole",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "StudentPayment",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	{
		Menu: "InstituteFeePaymentSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "NotificationSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "ClassAssignmentSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "ClassFeeManagementSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "StudentAssignmentSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "StudentleaveManagementSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "StudentFeeManagementSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "StudentPayMentSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "StudentOtherActivitySummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
		{
		Menu: "TeacherLeaveManagementSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "ClassMarkEntrySummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "ClassAttendanceSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "ClassTimeTableSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "ClassExamScheduleSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "TeacherAttendanceSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "TeacherTimeTableSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "TeacherProfileSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "StudentProfileSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "UserProfileSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},   
	{
	Menu: "StudentProgressCardSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "UserRoleSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "StudentReport",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "TeacherReport",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "ClassReport",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "SubstituteReport",
		Operation: {
			Query: true,
			Detail: true
		}
			
	},
	{
		Menu: "HolidayMaintanece",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
		{
		Menu: "HolidayMaintaneceSummary",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
		Menu: "GroupMapping",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	
	{
		Menu: "InstituteOtherActivity",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	
	
	
	{
		Menu: "BatchConfiguration",
		Operation: {
			Query: true,
			Create: true,
			Edit: true,
			Delete: true,
			Auth: true,
			Reject: true
		}
	},
	
	
	{
	Menu: "BatchMonitoring",
		Operation: {
			Query: true,
			Detail: true
		}
	},
	{
	Menu: "BatchRun",
		Operation: {
			View: true
			}
	},
	
	
];
*/
var User = {
	Id: ""
};

var Entity="";

var nokotser;
var Institute = {
	ID: "",
	Name: "",
	periodTimings: [{
			periodNumber: "I",
			timing: "09:30 10:15"
		},
		{
			periodNumber: "II",
			timing: "10:15 11:00"
		},
		{
			periodNumber: "III",
			timing: "11:00 11:45"
		},
		{
			periodNumber: "IV",
			timing: "11:45 12:30"
		},
		{
			periodNumber: "V",
			timing: "13:15 14:00"
		},
		{
			periodNumber: "VI",
			timing: "14:00 14:45"
		},
		{
			periodNumber: "VII",
			timing: "14:45 15:30"
		}
	],
	TeacherMaster: [{
			TeacherId: "2912",
			TeacherName: "Rajkumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Ramkumar",
			ShortName: "R.M"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rakkumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Vinujaa Sree",
			ShortName: "R.V"
		},
		{
			TeacherId: "2913",
			TeacherName: "Lalith Sai AyyaNadar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Raqkumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Pooja Sree",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Lukesh Sai",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rawkumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "SaiSree",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Lithwin Sai",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Anitha Rajkumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Muniswari",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "UdhayaKumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Velusamy",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "ShanmugaKani",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "BalaKrishnan",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Kavitha UdhayaKumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Raj5kumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Raj7kumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Raj9kumar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajk9umar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajk0umar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajku9mar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajku5mar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajkuma8r",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajkumtar",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajkumayr",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajkumayr",
			ShortName: "R.V"
		},
		{
			TeacherId: "2912",
			TeacherName: "Rajkumayr",
			ShortName: "R.V"
		}
	],
	
		StudentMaster: [{
			StudentId: "9513",
			StudentName: "Munish Kumar"
		},
		{
			StudentId: "9514",
			StudentName: "Issac Manoj kumar"
		},
		{
			StudentId: "9515",
			StudentName: "Raj Kumar"
		},
		{
			StudentId: "9516",
			StudentName: "Velusamy"
		},
		{
			StudentId: "9517",
			StudentName: "Lalith Sai"
		},
		{
			StudentId: "9513",
			StudentName: "Munish Kumar"
		},
		{
			StudentId: "9517",
			StudentName: "Priyanka"
		},
		{
			StudentId: "9518",
			StudentName: "Lukesh Sai"
		},
		{
			StudentId: "9519",
			StudentName: "Aravinth Kumar"
		},
		{
			StudentId: "9521",
			StudentName: "Munish Kumar"
		},
		{
			StudentId: "9523",
			StudentName: "Dafny "
		},
		{
			StudentId: "9525",
			StudentName: "Rathi"
		},
		{
			StudentId: "9529",
			StudentName: "Rathi Devi"
		},
		{
		     StudentId: "9526",
			StudentName: "Lavanya"
		},
		{
			StudentId: "9527",
			StudentName: "Munish"
		},
		{
			StudentId: "9525",
			StudentName: "Rathi"
		},
		{
			StudentId: "9525",
			StudentName: "Muniswari"
		},
		{
			StudentId: "9525",
			StudentName: "Raja"
		},
		
		{
			StudentId: "9527",
			StudentName: "Donald"
		},
		{
			StudentId: "9528",
			StudentName: "UdhayaKumar"
		},
		{
			StudentId: "9529",
			StudentName: "Pooja"
		},
		{   StudentId: "9540",
			StudentName: "Surya"
		},
		{   StudentId: "0000",
			StudentName: "All Student"
		}
	],
	InstituteMaster: [{
			InstituteId: "1828",
			InstituteName: "IBD Schools"
		},
		{
			InstituteId: "I015",
			InstituteName: "IBD Schools"
		},
                {
			InstituteId: "I016",
			InstituteName: "IBD Schools"
		},
                 {
			InstituteId: "9514",
			InstituteName: "karapettai Nadar Boys Higher Secondary School"
		},
		{
			InstituteId: "9513",
			InstituteName: "karapettai Nadar Girls Higher Secondary School"
		},
		{
			InstituteId: "9514",
			InstituteName: "kamaraj Higher Secondary School"
		},
		{
			InstituteId: "9514",
			InstituteName: "Amirtha Vidhyalayam CBSE School "
		},
		{
			InstituteId: "9514",
			InstituteName: "Vikasa International School "
		}
	],
	
	
	AssignmentMaster: [{
			AssignmentId: "A001",
			AssignmentDescription: "Practical Logic"
		},
		{
			AssignmentId: "A002",
			AssignmentDescription: "Input/ Output Unit Processing"
		},
		{
			AssignmentId: "A003",
			AssignmentDescription: "Singals system"
		},
		{
			AssignmentId: "A003",
			AssignmentDescription: "Grid Computing & Cloud Computing Difference"
		},
		{
			AssignmentId: "A004",
			AssignmentDescription: "Artifical Intelligence "
		},
		{
			AssignmentId: "A005",
			AssignmentDescription: "Man Power vs Soft Power "
		}
	],
	
	FeeIDMaster: [{
			FeeId: "F001",
			FeeType:"Term I"
		},
		{
			FeeId: "F002",
			FeeType:"Term II"
		},
		{
			FeeId: "F003",
			FeeType:"Term III"
		},
		{
			FeeId: "F004",
			FeeType:"Term II"
		},
		{
	    	FeeId: "F005",
			FeeType:"Term III"
		},
		{
			FeeId: "F006",
			FeeType:"Term I"
		}
	],
	
	PaymentIDMaster: [{
			PaymentId: "P001"
		},
		{
			PaymentId: "P002"
		},
		{
			PaymentId: "P003"
		},
		{
			PaymentId: "P004"
		},
		{
	    	PaymentId: "P005"
		},
		{
			PaymentId: "P006"
		},
		{
			PaymentId: "P007"
		}
	],
	
	
	UserMaster: [{
			UserId: "9513",
			UserName: "Munish Kumar"
		},
		{
			UserId: "9514",
			UserName: "Issac Manoj kumar"
		},
		{
			UserId: "9515",
			UserName: "Raj Kumar"
		},
		{
			UserId: "9516",
			UserName: "Velusamy"
		},
		{
			UserId: "9517",
			UserName: "Lalith Sai"
		},
		{
			UserId: "9513",
			UserName: "Munish Kumar"
		},
		{
			UserId: "9517",
			UserName: "Priyanka"
		},
		{
			UserId: "9518",
			UserName: "Lukesh Sai"
		},
		{
			UserId: "9519",
			UserName: "Aravinth Kumar"
		},
		{
			UserId: "9521",
			UserName: "Munish Kumar"
		},
		{
			UserId: "9523",
			UserName: "Dafny "
		},
		{
			UserId: "9525",
			UserName: "Rathi"
		},
		{
			UserId: "9529",
			UserName: "Rathi Devi"
		},
		{
		     UserId: "9526",
			UserName: "Lavanya"
		},
		{
			UserId: "9527",
			UserName: "Munish"
		},
		{
			UserId: "9525",
			UserName: "Rathi"
		},
		{
			UserId: "9525",
			UserName: "Muniswari"
		},
		{   UserId: "9540",
			UserName: "Surya"
		}
	],
	RoleMaster: [{
			RoleId: "R001",
			RoleDescription: "Authorise"
		},
		{
			RoleId: "R002",
			RoleDescription: "Parent Role"
		},
		{
			RoleId: "R003",
			RoleDescription: "Teacher Role"
		},
		{
			RoleId: "R004",
			RoleDescription: "Teacher Role"
		},
		{
			RoleId: "R005",
			RoleDescription: "Institute Role"
		},
		{    RoleId: "R006",
			RoleDescription: "Student"
		}
	],
	NotificationSearchMaster: [{
			NotifificationID: "N001",
			NotifificationType: "OtherActivity"
		},
		{
			NotifificationID: "N002",
			NotifificationType: "Emergency"
		},
		{
			NotifificationID: "N00",
			NotifificationType: "Exam"
		},
		{
			NotifificationID: "N005",
			NotifificationType: "Exam"
		},
		{
		    NotifificationID: "N006",
			NotifificationType: "Meeting"
		},
		{     NotifificationID: "N007",
			NotifificationType: "Emergency Meeting"
		}
	],
	
	GroupMappingMaster: [{
			GroupId: "G001",
			GroupDescription: "Student Group"
		},
		{
			GroupId: "G002",
			GroupDescription: "Nss Group"
		},
		{
			GroupId: "G00",
			GroupDescription: "Cricket Player Group"
		},
		{
			GroupId: "G005",
			GroupDescription: "Basket Ball Player Group"
		},
		{
		    GroupId: "G006",
			GroupDescription: "Volley Ball Player Group"
		},
		{     GroupId: "G007",
			GroupDescription: "Student Management Group"
		}
	],
	AssigneeMaster: [{
			AssigneeId: "A001"
			
		},
		{
			AssigneeId: "G002"
		},
		{
			AssigneeId: "A00"
		},
		{
			AssigneeId: "G005"
		},
		{
		    AssigneeId: "G006"
		},
		{     AssigneeId: "G007"
		}
	],
	
	
		OtherActivityIdMaster: [{
			ActivityId: "A001",
			ActivityType: "Activity"
			
		},
		{
			ActivityId: "A002",
			ActivityType: "Cricket"
		},
		{
			ActivityId: "A003",
			ActivityType: "FootBall"
		},
		{
			ActivityId: "A004",
			ActivityType: "Player"
		},
		{
		    ActivityId: "A004",
			ActivityType: "Kabadi"
		},
		{    activityId: "A005",
			activityType: "Player"
		}
	],
	
	
	
	BatchNameMaster: [{
			BatchName: "Assignment",
			BatchDescription: "Focus on Good"
		},
		{
			BatchName: "Assignment 2",
			BatchDescription: "Compare hardware and sotware"
		},
		{
			BatchName: "Assignment 3",
			BatchDescription: "Cloud Computing vs Sky Computing"
		},
		{
			BatchName: "Assignment 4",
			BatchDescription: "Focus on good"
		},
		{
			BatchName: "Assignment 5",
			BatchDescription: "Institute Role"
		}
	],
	
        ClassMaster:null,
	/*ClassMaster: ["Select option","I/A", "I/B", "I/C", "II/A", "II/B", "II/C", "III/A", "III/B", "III/C", "IV/A", "IV/B", "IV/C", "IV/D", "V/A", "V/B", "V/C",
		"V1/A", "V1/B", "V1/C", "V1/D", "VII/A", "VII/B", "VII/C", "VII/D", "VIII/A", "VIII/B", "VIII/C", "VIII/D",
		'IX/A', 'IX/B', 'IX/C', 'IX/D', 'IX/E', 'IX/F', 'IX/G', 'X/A', 'X/B', 'X/C', 'X/D', 'X/E', 'X/F', 'XI/A', 'XI/B', 'XI/C', 'XI/D', 'XII/A',
		'XII/B', 'XII/C', 'XII/D', 'XII/E', 'XII/F', 'XII/G'
	],*/
	//ClassMaster: ["Select option","X/A", "XII/A", "XI/A"],
	SubjectMaster: ["Select option","Mathematics", "Physics", "Chemistry", "Biology", "Computer", "Accounts", "Economics", "Tamil", "English", "zoology", "Social"],
	ExamMaster: ["Select option","Term I", "Quarterly", "Term II", "Half Yearly", "Annual"],
	LeaveMaster: ["Select option","Sick", "Planned", "Casual"],
	LeaveMasterStatus: ["Select option","Pending", "Approved", "Declined"],
	StandardMaster: [{standard:"Select option",section:["Select option"]},{
		standard: "I",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "II",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "III",
		section: ["Select option","A", "B", "C","D"]
	}, {
		standard: "IV",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "V",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "VI",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "VII",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "VIII",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "IX",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "X",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "XI",
		section: ["Select option","A", "B", "C"]
	}, {
		standard: "XII",
		section: ["Select option","A", "B", "C"]
	}],
	PayMentMaster: ["Select option","Cash", "Cheque", "NetBanking", "Others"],
	FeeMaster: ["Select option","Term I", "Term II", "Term III"],
	NotificationMaster: ["Select option","OtherActivity", "Emergency", "Exam", "Meeting"],
	NotificationFrequency: ["Select option","Daily", "Weekly", "FortNightly", "Monthly", "Quarterly"],
	MediaCommunication: ["Select option","Mail", "SMS"],
	DayMaster: ["Select option","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
	MonthMaster: ["Select option","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
	FeatureMaster: ["Select option","ClassTimeTable", "TeacherTimeTable", "TeacherProfile", "StudentProfile", "ClassAttendance", "TeacherAttendance", "StudentAttendance", "StudentAssignment", "ClassAssignment", "StudentCalender", "TeacherCalender", "ExamSchedule", "StudentProgressCard", "StudentProgressCardEntry", "StudentLeaveManagement", "TeacherLeaveManagement", "ClassFeeManagement", "StudentFeeManagement", "PaySlip", "Transport", "Notification", "Statistics", "OtherActivity", "Administration"],
	ProfileStatusMaster: ["Select option","Enable", "Disable"],
	StatusMaster: ["Select option","Completed", "Incomplete"],
	FeeStatus: ["Select option","Pending", "OverDue", "Paid"],
	AuthStatusMaster: ["Select option","Authorised", "Unauthorised", "Rejected"],
	RecStatusMaster: ["Select option","Open", "Deleted"],
	HourMaster: ["Hour","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"],
	MinMaster: ["Min","00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"],
	AssignmentTypeMaster:["Select option","Homework","Term/Exam","Punishment","Improvement"],
	OtherActivityLevelMaster:["Select option","State","District","International","Internal"],
	YearMaster:["Select option","2017","2018","2019","2020"],
	
	PeriodMaster:[{PeriodNumber:"Select option",StartTimeHour:"Hour",StartTimeMin:"Min",EndTimeHour:"Hour",EndTimeMin:"Min"},
	{PeriodNumber:"I",StartTimeHour:"09",StartTimeMin:"30",EndTimeHour:"10",EndTimeMin:"15"},
	{PeriodNumber:"II",StartTimeHour:"10",StartTimeMin:"15",EndTimeHour:"11",EndTimeMin:"00"},
	{PeriodNumber:"III",StartTimeHour:"11",StartTimeMin:"00",EndTimeHour:"11",EndTimeMin:"45"},
	{PeriodNumber:"IV",StartTimeHour:"11",StartTimeMin:"45",EndTimeHour:"12",EndTimeMin:"30"},
	{PeriodNumber:"V",StartTimeHour:"01",StartTimeMin:"15",EndTimeHour:"02",EndTimeMin:"00"},
	{PeriodNumber:"VI",StartTimeHour:"02",StartTimeMin:"00",EndTimeHour:"02",EndTimeMin:"45"},
	{PeriodNumber:"VII",StartTimeHour:"02",StartTimeMin:"45",EndTimeHour:"03",EndTimeMin:"30"},
	{PeriodNumber:"VIII",StartTimeHour:"03",StartTimeMin:"30",EndTimeHour:"03",EndTimeMin:"45"}],
	SectionMaster:["Select option","A","B","C","D","E"],  
	StandardMasterI:["Select option","I","II","III","IV","V","VI","VII","VIII"],
	NoonMaster:["Select option","Forenoon","AfterNoon","FullDay"],
	ParticipateMaster:["Select option","Yes","No"],
	BatchMaster:["Select option", "Database","Bussiness","Report"]
	
};