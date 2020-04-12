var checkCount=0;
function fngetSrc(url) {
	var src = url + "?X-uhtuliak=" + window.parent.uhtuliak+"&"+"nrb="+window.parent.Institute.ID;;
	return src;
}
function fninvokeDetailScreen(ScreenName,ScreenKey,$scope)
{
    window.parent.fn_show_spinner();
switch(ScreenName)
{
	case 'InstituteFeePayment':
	    window.parent.InstituteFeePaymentkey.paymentID=ScreenKey.paymentID;
            window.parent.InstituteFeePaymentkey.paymentDate=ScreenKey.paymentDate;
	   $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/InstitutePayment.jsp'));

	  break;
	  case 'NotificationSummary':
	    window.parent.NotificationSummarykey.notificationID=ScreenKey.notificationID;
	   $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/Notification.jsp'));

	  break;
          case 'StudentNotificationSummary':
	    window.parent.StudentNotificationSummarykey.notificationID=ScreenKey.notificationID;
            window.parent.StudentNotificationSummarykey.studentID=ScreenKey.studentID;
            window.parent.StudentNotificationSummarykey.date=ScreenKey.date;
	   $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentNotification.jsp'));

	  break;
          case 'Class':
	    window.parent.Classkey.class=ScreenKey.class;
		
	   $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/ClassLevelConfiguration.jsp'));

	  break;
	  case 'InstituteAssignment':
	    window.parent.InstituteAssignmentkey.assignmentID=ScreenKey.assignmentID;
	   $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/InstituteAssignment.jsp'));

	  break;
	   case 'InstituteFeeManagement':
	    window.parent.InstituteFeeManagementkey.feeID=ScreenKey.feeID;
	    $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/InstituteFeeManagement.jsp'));

	  break;
	  case 'StudentAssignment':
	    window.parent.StudentAssignmentkey.assignmentID=ScreenKey.assignmentID;
            window.parent.StudentAssignmentkey.studentID=ScreenKey.studentID;
	    $scope =null;
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentAssignment.jsp'));

	  break;
           case 'StudentECircular':
            window.parent.StudentECircularkey.studentID=ScreenKey.studentID;
            window.parent.StudentECircularkey.circularID=ScreenKey.circularID;
	    $scope =null;  
             window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentECircular.jsp'));
           break;
           case 'TeacherECircular':
            window.parent.TeacherECircularkey.teacherID=ScreenKey.teacherID;
            window.parent.TeacherECircularkey.circularID=ScreenKey.circularID;
	    $scope =null;  
             window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/TeacherECircular.jsp'));
           break;
	   case 'StudentLeaveManagement':
	    window.parent.StudentLeaveManagementkey.studentID=ScreenKey.studentID;
            window.parent.StudentLeaveManagementkey.from=ScreenKey.from;
            window.parent.StudentLeaveManagementkey.to=ScreenKey.to;
	    $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentLeaveManagement.jsp'));

	  break;
	   case 'StudentFeeManagement':
	    window.parent.StudentFeeManagementkey.feeID=ScreenKey.feeID;
            window.parent.StudentFeeManagementkey.studentID=ScreenKey.studentID;
	    $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentFeeManagement.jsp'));

	  break;
	    case 'StudentPayment':
	    window.parent.StudentPaymentkey.paymentID=ScreenKey.paymentID;
            window.parent.StudentPaymentkey.studentID=ScreenKey.studentID;
            window.parent.StudentPaymentkey.paymentDate=ScreenKey.paymentDate;
	    $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentPayment.jsp'));

	  break;
	 case 'StudentOtheractivity':
	    window.parent.StudentOtheractivitykey.studentID=ScreenKey.studentID;
            window.parent.StudentOtheractivitykey.activityID=ScreenKey.activityID;
	    $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentOtherActivity.jsp'));
	 break;
	  case 'TeacherLeaveManagement':
	    window.parent.TeacherLeaveManagementkey.teacherID=ScreenKey.teacherID;
            window.parent.TeacherLeaveManagementkey.from=ScreenKey.from;
            window.parent.TeacherLeaveManagementkey.to=ScreenKey.to;
	    $scope =null;
	   
     window.parent.$("#frame").attr('src', '');
     window.parent.$("#frame").attr('src', fngetSrc('/jsp/TeacherLeaveManagement.jsp'));

	  break;
	   case 'ClassMark':
	    window.parent.ClassMarkkey.class=ScreenKey.class;
	    window.parent.ClassMarkkey.subjectID=ScreenKey.subjectID;
	    window.parent.ClassMarkkey.exam=ScreenKey.exam;
	    
		$scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/ClassMark.jsp'));

	  break;
          
          case 'ClassSoftSkill':
	    window.parent.ClassSoftSkillkey.class=ScreenKey.class;
	    window.parent.ClassSoftSkillkey.skillID=ScreenKey.skillID;
	    window.parent.ClassSoftSkillkey.exam=ScreenKey.exam;
	    
		$scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/ClassSoftSkill.jsp'));

	  break;
          
	   case 'ClassAttendance':
//	    window.parent.ClassAttendancekey.standard=ScreenKey.standard;
//	    window.parent.ClassAttendancekey.section=ScreenKey.section;
            window.parent.ClassAttendancekey.class=ScreenKey.class;
	    window.parent.ClassAttendancekey.date=ScreenKey.date;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/ClassAttendance.jsp'));

	  break;
	    case 'ClassTimeTable':
	    //window.parent.ClassTimeTablekey.standard=ScreenKey.standard;
	    //window.parent.ClassTimeTablekey.section=ScreenKey.section;
             window.parent.ClassTimeTablekey.class=ScreenKey.class;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/ClassTimeTable.jsp'));
	  break;
	    case 'ClassExamSchedule':
	    window.parent.ClassExamSchedulekey.class=ScreenKey.class;
	    window.parent.ClassExamSchedulekey.exam=ScreenKey.exam;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/ClassExamSchedule.jsp'));
	  break;
	    case 'TeacherAttendance':
	    window.parent.TeacherAttendancekey.teacherID=ScreenKey.teacherID;
	    window.parent.TeacherAttendancekey.date=ScreenKey.date;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/TeacherAttendance.jsp'));
	  break;
	  case 'TeacherTimeTable':
	    window.parent.TeacherTimeTablekey.teacherID=ScreenKey.teacherID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/TeacherTimeTable.jsp'));
	  break;
	   case 'TeacherProfile':
	    window.parent.TeacherProfilekey.teacherID=ScreenKey.teacherID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/TeacherProfile.jsp'));
	  break;
	  case 'StudentProfile':
	    window.parent.StudentProfilekey.studentID=ScreenKey.studentID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentProfile.jsp'));
	  break;
	   case 'UserProfile':
	    window.parent.UserProfilekey.userID=ScreenKey.userID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/UserProfile.jsp'));
	  break;
	  case 'StudentProgressCard':
	    window.parent.StudentProgressCardkey.studentID=ScreenKey.studentID;
	    window.parent.StudentProgressCardkey.standard=ScreenKey.standard;
	    window.parent.StudentProgressCardkey.section=ScreenKey.section;
	    window.parent.StudentProgressCardkey.exam=ScreenKey.exam;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentProgressCard.jsp'));
	  break;
          case 'StudentSoftSkill':
	    window.parent.StudentSoftSkillkey.studentID=ScreenKey.studentID;
	    window.parent.StudentSoftSkillkey.standard=ScreenKey.standard;
	    window.parent.StudentSoftSkillkey.section=ScreenKey.section;
	    window.parent.StudentSoftSkillkey.exam=ScreenKey.exam;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentSoftSkill.jsp'));
	  break;
	   case 'UserRole':
	    window.parent.UserRolekey.roleID=ScreenKey.roleID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/UserRole.jsp'));
	  break;
	   case 'HolidayMaintaneceSummary':
	    window.parent.HolidayMaintenanceSummarykey.month=ScreenKey.month;
	    window.parent.HolidayMaintenanceSummarykey.year=ScreenKey.year;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/HolidayMaintenance.jsp'));
	  break;
          case 'ECircular':
	    window.parent.ECircularSummarykey.circularID=ScreenKey.circularID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/ECircular.jsp'));
	  break;
	   case 'GroupMaping':
	    window.parent.GroupMapingSummarykey.groupID=ScreenKey.groupID;
//	    window.parent.GroupMapingSummarykey.recordStat=ScreenKey.recordStat;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/GroupMapping.jsp'));
	  break;
	    case 'InstituteOtheractivity':
//	    window.parent.InstituteOtheractivitySummarykey.activityType=ScreenKey.activityType;
//	    window.parent.InstituteOtheractivitySummarykey.activityLevel=ScreenKey.activityLevel;
	    window.parent.InstituteOtheractivitySummarykey.activityID=ScreenKey.activityID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/InstituteOtherActivity.jsp'));
	  break;
	   case 'StudentAttendance':
	    window.parent.StudentAttendanceSummarykey.studentID=ScreenKey.studentID;
            window.parent.StudentAttendanceSummarykey.date=ScreenKey.date;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentAttendance.jsp'));
	  break;
           case 'StudentTimeTable':
	    window.parent.StudenTimeTablekey.studentID=ScreenKey.studentID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentTimeTable.jsp'));
	  break;
              case 'StudentExamSchedule':
	    window.parent.StudentExamSchedulekey.studentID=ScreenKey.studentID;
	    $scope =null;
        window.parent.$("#frame").attr('src', '');
        window.parent.$("#frame").attr('src', fngetSrc('/jsp/StudentExamSchedule.jsp'));
	  break;
	  
}	
}
 			
function fngetSelectedIndex(tableObject,$scope)
{
	$scope.selectedIndex=0;
if (tableObject != null && tableObject!=0)
		  { //var temp= mvwObject.tableObject.slice();
	        checkCount=0;

			 tableObject.forEach(fnCheck);
			 
			 function fnCheck(value,index,array){
				 				 
				if(value.checkBox ==true){
					 try{
						 //var checkCount=0;
						 checkCount =checkCount+1;
						 $scope.selectedIndex =index;
						 if(checkCount>1)
						   throw "done";
					   }
					
										   
				 catch(e){ 
					 
					 if(e!="done") 
					  throw e; 
				         }
				 
				}
			 }
			 if(checkCount>1)
			 {
			  fn_Show_Exception('FE-VAL-027');
			   return false;
			 }  
			 
			 if(checkCount==0)
			 {
			  fn_Show_Exception('FE-VAL-029');
			   return false;
			 }
			
			 }
	return true;		 
}		