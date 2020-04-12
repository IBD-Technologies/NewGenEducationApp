/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module('search', []);
var searchCallInputType;
app.controller('TeacherNameSerach', fnTeacherNameSerach);
app.controller('StudentNamesearch', fnStudentNameSearch);
app.controller('StudentEndPointsearch', fnStudentEndPointSearch);
app.controller('InstituteNamesearch', fnInstituteNameSearch);
app.controller('ClassAssignmentsearch', fnClassAssignmentNameSearch);
app.controller('feeIDsearch', fnFeeIDSearch);
app.controller('paymentIDsearch', fnPaymentSearch);
app.controller('UserNamesearch', fnUserNameSearch);
app.controller('UserRoleSearch', fnUserRoleIDSearch);
app.controller('NotificationSearch', fnNotificationNameSearch);
app.controller('GroupIDSearch', fnGroupIDSearch);
app.controller('AssigneIDSearch', fnAssigneeSearch);
app.controller('OtherActivitySearch', fnOtheractivitySearch);
app.controller('BatchNameSearch', fnBatchNameSearch);
app.controller('InstituteUsersearch', fnInstituteUserSearch);
app.controller('CircularIdSearch', fnCircularIDSearch);

if(TEST=="YES"){

var searchURL="https://cohesivetest.ibdtechnologies.com/CohesiveGateway/";

}else{
    var searchURL="https://cohesive.ibdtechnologies.com/CohesiveGateway/";
}


function fnTeacherNameSerach($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchstaffName = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetTeacher = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
                
		if (MenuName == "UserProfile" && tabClick == "Teacher") {
			mainScope.teacherRoleMappingRecord.teacherName = Y.TeacherName;
			mainScope.teacherRoleMappingRecord.teacherID = Y.TeacherId;
		}
                else if (MenuName == "StudentTimeTable" && tabClick == "Monday") {
			mainScope.MondayRecord.teacherName = Y.TeacherName;
			mainScope.MondayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Tuesday") {
			mainScope.TuesdayRecord.teacherName = Y.TeacherName;
			mainScope.TuesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Wednesday") {
			mainScope.WednesdayRecord.teacherName = Y.TeacherName;
			mainScope.WednesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Thursday") {
			mainScope.ThursdayRecord.teacherName = Y.TeacherName;
			mainScope.ThursdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Friday") {
			mainScope.FridayRecord.teacherName = Y.TeacherName;
			mainScope.FridayRecord.teacherID = Y.TeacherId;
		}
                  else if (MenuName == "ClassTimeTable" && tabClick == "Monday") {
			mainScope.MondayRecord.teacherName = Y.TeacherName;
			mainScope.MondayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Tuesday") {
			mainScope.TuesdayRecord.teacherName = Y.TeacherName;
			mainScope.TuesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Wednesday") {
			mainScope.WednesdayRecord.teacherName = Y.TeacherName;
			mainScope.WednesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Thursday") {
			mainScope.ThursdayRecord.teacherName = Y.TeacherName;
			mainScope.ThursdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Friday") {
			mainScope.FridayRecord.teacherName = Y.TeacherName;
			mainScope.FridayRecord.teacherID = Y.TeacherId;
		}
            else {
			mainScope.teacherName = Y.TeacherName;
			mainScope.teacherID = Y.TeacherId;
		}


		//$('#search').empty();
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("TeacherNameSearchService", this.SearchstaffName, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function ()


	{
		//var filter=[];
		$('#TeacherSeacrhList').empty();
		$('#TeacherSeacrhList').removeClass("SearchBody");
		//this.filterList.length=0;
		/* if(this.SearchstaffName!=null && this.SearchstaffName!='')
						   {							   
							if(this.SearchstaffName.length!=0)   
						   { 
                             angular.forEach(Institute.TeacherMaster,function(value,key){
	                          if (this[key].TeacherName.includes($('#SearchstaffName').val()) || (this[key].TeacherId.includes($('#SearchstaffName').val())))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.TeacherMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#TeacherSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action " ng-click="fngetTeacher(Y)" ng-repeat="Y in filterList | orderBy:TeacherName" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.TeacherName}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.TeacherId}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		//mainScope.teacherName=Y.TeacherName;
		//mainScope.teacherID=Y.TeacherId;
		//$('#search').empty();
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#Srchstaffdiv');
					// var template1=angular.element('<input id="SearchstaffName" ng-change="fnFilter()" ng-Model="SearchstaffName" type="text" placeholder="Search Teacher Name" class="form-control">');
					var template1 = angular.element('<input id="SearchstaffName" ng-Model="SearchstaffName" type="text" placeholder="Search Teacher Name" class="form-control">'); //Integration changes
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
                                        
				});
                                
                        var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                        if (MenuName == "UserProfile" && tabClick == "Teacher") {
                          $childScope.SearchstaffName=mainScope.teacherRoleMappingRecord.TeacherName;
			//mainScope.teacherRoleMappingRecord.teacherName = Y.TeacherName;
			//mainScope.teacherRoleMappingRecord.teacherID = Y.TeacherId;
		       }
                        else if (MenuName == "StudentTimeTable" && tabClick == "Monday") {
                        $childScope.SearchstaffName=mainScope.MondayRecord.TeacherName;
			//mainScope.MondayRecord.teacherName = Y.TeacherName;
			//mainScope.MondayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Tuesday") {
                       $childScope.SearchstaffName=mainScope.TuesdayRecord.TeacherName;
			//mainScope.TuesdayRecord.teacherName = Y.TeacherName;
			//mainScope.TuesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Wednesday") {
                        $childScope.SearchstaffName=mainScope.WednesdayRecord.TeacherName;
			//mainScope.WednesdayRecord.teacherName = Y.TeacherName;
			//mainScope.WednesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Thursday") {
                      $childScope.SearchstaffName=mainScope.ThursdayRecord.TeacherName;
			//mainScope.ThursdayRecord.teacherName = Y.TeacherName;
			//mainScope.ThursdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "StudentTimeTable" && tabClick == "Friday") {
                      $childScope.SearchstaffName=mainScope.FridayRecord.TeacherName;
			//mainScope.FridayRecord.teacherName = Y.TeacherName;
			//mainScope.FridayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Monday") {
                        $childScope.SearchstaffName=mainScope.MondayRecord.TeacherName;
			//mainScope.MondayRecord.teacherName = Y.TeacherName;
			//mainScope.MondayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Tuesday") {
                       $childScope.SearchstaffName=mainScope.TuesdayRecord.TeacherName;
			//mainScope.TuesdayRecord.teacherName = Y.TeacherName;
			//mainScope.TuesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Wednesday") {
                        $childScope.SearchstaffName=mainScope.WednesdayRecord.TeacherName;
			//mainScope.WednesdayRecord.teacherName = Y.TeacherName;
			//mainScope.WednesdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Thursday") {
                      $childScope.SearchstaffName=mainScope.ThursdayRecord.TeacherName;
			//mainScope.ThursdayRecord.teacherName = Y.TeacherName;
			//mainScope.ThursdayRecord.teacherID = Y.TeacherId;
		}
                 else if (MenuName == "ClassTimeTable" && tabClick == "Friday") {
                      $childScope.SearchstaffName=mainScope.FridayRecord.TeacherName;
			//mainScope.FridayRecord.teacherName = Y.TeacherName;
			//mainScope.FridayRecord.teacherID = Y.TeacherId;
		}
                     
                    else {
			//mainScope.teacherName = Y.TeacherName;
			//mainScope.teacherID = Y.TeacherId;
                         $childScope.SearchstaffName=mainScope.teacherName;
		       }
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setTeacherSearchScope($childScope);

}


function fnStudentNameSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchstudName = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetStudent = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;

		if (MenuName == "UserProfile" && tabClick == "Parent") {
			mainScope.parentRoleMappingRecord.studentName = Y.StudentName;
			mainScope.parentRoleMappingRecord.studentID = Y.StudentId
		} else if(MenuName == "GroupMapping") {
//			mainScope.studentName = Y.StudentName;
//			mainScope.studentID = Y.StudentId;
                        mainScope.GroupMappingRecord.studentName = Y.StudentName;
			mainScope.GroupMappingRecord.studentID = Y.StudentId
		}else{
                    mainScope.studentName = Y.StudentName;
	            mainScope.studentID = Y.StudentId;
                }

		//$('#search').empty();
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnStuFilter = function () {
		fnSearchBackEnd("StudentSearchService", this.SearchstudName, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function ()


	{
		//var filter=[];
		$('#StudentSearchList').empty();
		$('#StudentSearchList').removeClass("SearchBody");
		/*this.filterList.length=0;
                           if(this.SearchstudName!=null && this.SearchstudName!='')
						   {							   
							if(this.SearchstudName.length!=0)   
						   { 
                             angular.forEach(Institute.StudentMaster,function(value,key){
	                          if (this[key].StudentName.includes($('#SearchstudName').val()) || (this[key].StudentId.includes($('#SearchstudName').val())))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.StudentMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#StudentSearchList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" ng-click="fngetStudent(Y)" ng-repeat="Y in filterList | orderBy:StudentName" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-4 SearchListColor"> {{Y.StudentName}}</span>' +
				'<span class="col-4 SearchListColor"> {{Y.StudentId}}  </span>' +
                                '<span class="col-4 SearchListColor"> {{Y.Standard+Y.Section}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		//$('#search').empty();
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#Srchstudfdiv');
					//var template2=angular.element('<input id="SearchstudName" ng-change="fnStuFilter()" ng-Model="SearchstudName" type="text" placeholder="Search Student Name" class="form-control">');
					var template2 = angular.element('<input id="SearchstudName" ng-Model="SearchstudName" type="text" placeholder="Search Student Name" class="form-control">'); //Integration changes
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
                        var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                        if (MenuName == "UserProfile" && tabClick == "Parent") {
                        $childScope.SearchstudName=mainScope.parentRoleMappingRecord.studentName ;
			//mainScope.parentRoleMappingRecord.studentName = Y.StudentName;
			//mainScope.parentRoleMappingRecord.studentID = Y.StudentId
          		} else {
                         $childScope.SearchstudName=mainScope.studentName;   
			//mainScope.studentName = Y.StudentName;
			//mainScope.studentID = Y.StudentId;
	         	}               
			break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template2 = angular.element('<button id="Searchbutton" type="button" ng-click="fnStuFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template2 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template2 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template2 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;

		};


	};


	//SeacrchScopeTransfer.setSearchScope($childScope);
	SeacrchScopeTransfer.setStuSearchScope($childScope);

}

function fnStudentEndPointSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchstudName = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetStudent = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;


                    mainScope.studentName = Y.StudentName;
	            mainScope.studentID = Y.StudentId;
                    mainScope.endPoint=Y.endPoint;

		//$('#search').empty();
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnStuFilter = function () {
		fnSearchBackEnd("StudentSearchService", this.SearchstudName, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function ()


	{
		//var filter=[];
		$('#StudentSearchList').empty();
		$('#StudentSearchList').removeClass("SearchBody");
		/*this.filterList.length=0;
                           if(this.SearchstudName!=null && this.SearchstudName!='')
						   {							   
							if(this.SearchstudName.length!=0)   
						   { 
                             angular.forEach(Institute.StudentMaster,function(value,key){
	                          if (this[key].StudentName.includes($('#SearchstudName').val()) || (this[key].StudentId.includes($('#SearchstudName').val())))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.StudentMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#StudentSearchList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" ng-click="fngetStudent(Y)" ng-repeat="Y in filterList | orderBy:StudentName" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
                                '<span class="col-6 SearchListColor"> {{Y.endPoint}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.StudentName}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.StudentId}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		//$('#search').empty();
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#Srchstudfdiv');
					//var template2=angular.element('<input id="SearchstudName" ng-change="fnStuFilter()" ng-Model="SearchstudName" type="text" placeholder="Search Student Name" class="form-control">');
					var template2 = angular.element('<input id="SearchstudName" ng-Model="SearchstudName" type="text" placeholder="Search Student Name" class="form-control">'); //Integration changes
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
                        var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                        if (MenuName == "UserProfile" && tabClick == "Parent") {
                        $childScope.SearchstudName=mainScope.parentRoleMappingRecord.studentName ;
			//mainScope.parentRoleMappingRecord.studentName = Y.StudentName;
			//mainScope.parentRoleMappingRecord.studentID = Y.StudentId
          		} else {
                         $childScope.SearchstudName=mainScope.studentName;   
			//mainScope.studentName = Y.StudentName;
			//mainScope.studentID = Y.StudentId;
	         	}               
			break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template2 = angular.element('<button id="Searchbutton" type="button" ng-click="fnStuFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template2 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template2 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template2 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template2);
					$compile(template2)($childScope);
				});
				break;

		};


	};


	//SeacrchScopeTransfer.setSearchScope($childScope);
	SeacrchScopeTransfer.setStuSearchScope($childScope);

}
function fnInstituteNameSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchInstituteName = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetInstitute = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
                 if(MenuName=='cohesiveMainPage' ||searchCallInputType=='InstituteChange'){
                     
		    $("#iID").val(Y.instituteID);
	            $("#iName").val(Y.instituteName);
		 var tempInstituteID=Institute.ID;
                Institute.ID = $("#iID").val();
                  Institute.Name = $("#iName").val();
                  
                  if(searchCallInputType=='InstituteChange'){
                  
                  if(tempInstituteID!=Institute.ID)
                  
                     asyncCall();
                 }
	  }
          else{
		if (MenuName == "UserProfile")

		{
			if (tabClick != null && tabClick == "Institute") {
				mainScope.instituteRoleMappingRecord.instituteName = Y.instituteName;
				mainScope.instituteRoleMappingRecord.instituteID = Y.instituteID;
			} else if (tabClick != null && tabClick == "Teacher") {
				mainScope.teacherRoleMappingRecord.instituteName = Y.instituteName;
				mainScope.teacherRoleMappingRecord.instituteID = Y.instituteID;
			}
                         else if (tabClick != null && tabClick == "Parent") {
				mainScope.parentRoleMappingTable.instituteName = Y.instituteName;
				mainScope.parentRoleMappingTable.instituteID = Y.instituteID;
			}
		} else {
			mainScope.instituteName = Y.instituteName;
			mainScope.instituteID = Y.instituteID;
		}
          }
		//$('#search').empty();
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
             $("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");
                if(MenuName=='cohesiveMainPage' ||searchCallInputType=='InstituteChange'){
		    $('#InstituteModal').modal('hide');
		  fn_Show_Information('FE-VAL-031','S');
	  }

	//}
    }
	$childScope.fnFilter = function () {
            
		fnSearchBackEnd("InstituteSearchService", this.SearchInstituteName, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function ()

	{
		//var filter=[];
		$('#InstituteSeacrhList').empty();
		$('#InstituteSeacrhList').removeClass("SearchBody");
		//  this.filterList.length=0;
		/*if(this.SearchInstituteName!=null && this.SearchInstituteName!='')
						   {							   
							if(this.SearchInstituteName.length!=0)   
						   { 
                             angular.forEach(window.parent.Institute.InstituteMaster,function(value,key){
	                          if (this[key].InstituteName.includes($('#SearchInstituteName').val()) || (this[key].InstituteId.includes($('#SearchInstituteName').val())))			                   {  			
	                             filter.push(this[key]);
                                }},window.parent.Institute.InstituteMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#InstituteSeacrhList');
			var template3 = angular.element('<a class="list-group-item list-group-item-action"  ng-click="fngetInstitute(Y)" ng-repeat="Y in filterList | orderBy:InstituteName" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.instituteName}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.instituteID}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template3);
			$compile(template3)($childScope);
			$elmnt.addClass("SearchBody");
			// $elmnt.css("overflow-y", "auto");


		});


		//} }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		//$('#search').empty();  
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchInstitutediv');
					//var template3=angular.element('<input id="SearchInstituteName" ng-change="fnFilter()" ng-Model="SearchInstituteName" type="text" placeholder="Search Institute Name" class="form-control">');
					var template3 = angular.element('<input id="SearchInstituteName" ng-Model="SearchInstituteName" type="text" placeholder="Search Institute Name" class="form-control">'); //Integration changes
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
                                
                                var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		
                 if(MenuName=='cohesiveMainPage' ||searchCallInputType=='InstituteChange'){
		    $childScope.SearchInstituteName=$("#iName").val();
			
	             }
                 else{
		    if (MenuName == "UserProfile")
                 	{
			if (tabClick != null && tabClick == "Institute") {
				$childScope.SearchInstituteName=mainScope.instituteRoleMappingRecord.instituteName ;
			} else if (tabClick != null && tabClick == "Teacher") {
				$childScope.SearchInstituteName=mainScope.teacherRoleMappingRecord.instituteName;
			}
                        else if (tabClick != null && tabClick == "Parent") {
				$childScope.SearchInstituteName=mainScope.parentRoleMappingRecord.instituteName;
			}
                         else if (tabClick != null && tabClick == "Class") {
				$childScope.SearchInstituteName=mainScope.studentClassRoleMappingRecord.instituteName;
			}
		       } else {
			$childScope.SearchInstituteName=mainScope.instituteName;
			
		            }

                                }          
                                
                         	break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template3 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template3 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template3 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template3 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setInstituteSearchScope($childScope);

}


function fnClassAssignmentNameSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchAssignmentKey = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetAssignment = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		mainScope.assignmentDescription = Y.assignmentDescription;
		mainScope.assignmentID = Y.assignmentID;
		//$('#search').empty();  
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnAssFilter = function ()

	{
		fnSearchBackEnd("ClassAssignmentSearchService", this.SearchAssignmentKey, $childScope);
	}
	$childScope.fnPostFilter = function ()


	{
		// var filter=[];
		$('#AssignmentSeacrhList').empty();
		$('#AssignmentSeacrhList').removeClass("SearchBody");

		// this.filterList.length=0;
		/* if(this.SearchAssignmentKey!=null && this.SearchAssignmentKey!='')
						   {							   
							if(this.SearchAssignmentKey.length!=0)   
						   { 
                             angular.forEach(Institute.AssignmentMaster,function(value,key){
	                         // if (this[key].AssignmentDescription.includes($('#SearchAssignmentDescription').val()))
							if (this[key].AssignmentId.includes($('#SearchAssignmentKey').val()) || (this[key].AssignmentDescription.includes($('#SearchAssignmentKey').val()) ))	 
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.AssignmentMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#AssignmentSeacrhList');
			var template3 = angular.element('<a class="list-group-item list-group-item-action" style="color:#228272" ng-click="fngetAssignment(Y)" ng-repeat="Y in filterList | orderBy:AssignmentId" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.assignmentDescription}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.assignmentID}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template3);
			$compile(template3)($childScope);
			$elmnt.addClass("SearchBody");
		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		//$('#search').empty();  
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	}

	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchAssignmentdiv');
					// var template3=angular.element('<input id="SearchAssignmentKey" ng-change="fnAssFilter()" ng-Model="SearchAssignmentKey" type="text" placeholder="Search Assignment" class="form-control">');
					var template3 = angular.element('<input id="SearchAssignmentKey" ng-Model="SearchAssignmentKey" type="text" placeholder="Search Assignment Name" class="form-control">'); //Integration changes
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});  
                              var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                              $childScope.SearchAssignmentKey=mainScope.assignmentID;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template3 = angular.element('<button id="Searchbutton" type="button" ng-click="fnAssFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template3 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template3 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template3 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;

		};


	};


	//SeacrchScopeTransfer.setSearchScope($childScope);
	SeacrchScopeTransfer.setAssSearchScope($childScope);

}


function fnFeeIDSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchFeeID = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetFee = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                
		mainScope.searchShow = false;
		mainScope.feeID = Y.feeID;
		mainScope.feeDescription = Y.feeDescription;
                
                if (MenuName == "InstitutePayment")

		{
//                    mainScope.amount=Y.amount;
                    $('#feeAmount').val(Y.amount);
                    $('#feeAmount').formatCurrency({ colorize:true,region: 'en-IN' })
		    mainScope.amount=$('#feeAmount').val();
                }
                
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("FeeIDSearchService", this.SearchFeeID, $childScope);
	}
	$childScope.fnPostFilter = function ()


	{
		// var filter=[];
		$('#FeeIDSeacrhList').empty();
		$('#FeeIDSeacrhList').removeClass("SearchBody");
		/*this.filterList.length=0;
                           if(this.SearchFeeID!=null && this.SearchFeeID!='')
						   {							   
							if(this.SearchFeeID.length!=0)   
						   { 
                             angular.forEach(Institute.FeeIDMaster,function(value,key){
	                          if (this[key].FeeId.includes($('#SearchFeeID').val()) || (this[key].FeeType.includes($('#SearchFeeID').val())))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.FeeIDMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#FeeIDSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" ng-click="fngetFee(Y)" ng-repeat="Y in filterList | orderBy:FeeId" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-4 SearchListColor"> {{Y.feeID}}  </span>' +
                                '<span class="col-4 SearchListColor"> {{Y.amount}}  </span>' +
				'<span class="col-4 SearchListColor"> {{Y.feeDescription}}  </span>' +
				 '</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchFeediv');
					// var template1=angular.element('<input id="SearchFeeID" ng-change="fnFilter()" ng-Model="SearchFeeID" type="text" placeholder="Search Fee ID" class="form-control">');
					var template1 = angular.element('<input id="SearchFeeID" ng-Model="SearchFeeID" type="text" placeholder="Search Fee ID" class="form-control">'); //Integration changes

					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                 var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchFeeID=mainScope.feeID;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setSearchScope($childScope);

}


function fnPaymentSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchPaymentID = null;
	$childScope.filterList = filter;
	$childScope.fngetPayment = function (Y) {
		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
                
                
		mainScope.paymentID = Y.paymentID;
                mainScope.paymentDate = Y.paymentDate;
                
                
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("PaymentSearchService", this.SearchPaymentID, $childScope);
	}
	$childScope.fnPostFilter = function ()


	{
		//var filter=[];
		$('#PaymentIDSeacrhList').empty();
		$('#PaymentIDSeacrhList').removeClass("SearchBody");

		/*this.filterList.length=0;
                           if(this.SearchPaymentID!=null && this.SearchPaymentID!='')
						   {							   
							if(this.SearchPaymentID.length!=0)   
						   { 
                             angular.forEach(Institute.PaymentIDMaster,function(value,key){
	                          if (this[key].PaymentId.includes($('#SearchPaymentID').val()))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.PaymentIDMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#PaymentIDSeacrhList');
			var template1 = angular.element(
                                 '<a class="list-group-item list-group-item-action">' +
				'<div class="row">' +
				'<span class="col-3 SearchListColor">PaymentID  </span>' +
                                '<span class="col-3 SearchListColor">Date  </span>' +
                                '<span class="col-3 SearchListColor">StudentID  </span>' +
                                '<span class="col-3 SearchListColor">Amount  </span>' +
				'</div>' +
				'</a>'+
                               '<a class="list-group-item list-group-item-action"  ng-click="fngetPayment(Y)" ng-repeat="Y in filterList | orderBy:PaymentDate" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-3 SearchListColor"> {{Y.paymentID}}  </span>' +
                                '<span class="col-3 SearchListColor"> {{Y.paymentDate}}  </span>' +
                                '<span class="col-3 SearchListColor"> {{Y.studentID}}  </span>' +
                                '<span class="col-3 SearchListColor"> {{Y.amount}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		//$('#search').empty(); //Integration changes
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchPaymentdiv');
					// var template1=angular.element('<input id="SearchPaymentID" ng-change="fnFilter()" ng-Model="SearchPaymentID" type="text" placeholder="Search Payment ID" class="form-control">');
					var template1 = angular.element('<input id="SearchPaymentID" ng-Model="SearchPaymentID" type="text" placeholder="Please search by entering the payment date in the format dd-MM-yyyy" class="form-control">'); //Integration changes
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                 var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchPaymentID=mainScope.paymentID;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	//SeacrchScopeTransfer.setSearchScope($childScope);
	SeacrchScopeTransfer.setPaymentSearchScope($childScope);

}


function fnUserNameSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchUserName = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetUser = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		mainScope.userName = Y.userName;
		mainScope.userID = Y.userID;
		//$('#search').empty(); //Integration changes
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("UserSearchService", this.SearchInstituteName, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function ()

	{
		//var filter=[];
		$('#UserSeacrhList').empty();
		$('#UserSeacrhList').removeClass("SearchBody");
		/* this.filterList.length=0;
                           if(this.SearchUserName!=null && this.SearchUserName!='')
						   {							   
							if(this.SearchUserName.length!=0)   
						   { 
                             angular.forEach(Institute.UserMaster,function(value,key){
	                          if (this[key].UserName.includes($('#SearchUserName').val()) || (this[key].UserId.includes($('#SearchUserName').val())))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.UserMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#UserSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" style="color:#228272" ng-click="fngetUser(Y)" ng-repeat="Y in filterList | orderBy:UserName" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.userName}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.userID}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


	}

$childScope.fnClose = function ()

{

	this.filterList.length = 0;
	var mainScope = SeacrchScopeTransfer.getMainScope($scope);
	mainScope.searchShow = false;
	//$('#search').empty(); //Integration changes
	$('#searchHeader').empty(); //Integration changes					 
	$('#searchBody').empty(); //Integration changes					 };
	$("#subscreenHeader").removeClass("SearchHeader");
	$('#subscreenContent').removeClass("SearchBody");
	$("#subscreenHeader").addClass("subscreenHeader");
	$('#subscreenContent').addClass("subscreenContent");


};


$childScope.addElement = function (i) {

	switch (i) {
		case 1:
			$timeout(function () {
				var $elmnt = $('#SrchUserdiv');
				//var template1=angular.element('<input id="SearchUserName" ng-change="fnFilter()" ng-Model="SearchUserName" type="text" placeholder="Search User Name" class="form-control">');
				var template1 = angular.element('<input id="SearchUserName" ng-Model="SearchUserName" type="text" placeholder="Search User Name" class="form-control">'); //Integration changes


				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
                         var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                         $childScope.SearchUserName=mainScope.userName;
			break;
		case 2:
			$timeout(function () {
				var $elmnt = $('#Searchdiv');
				var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;
		case 3:
			$timeout(function () {
				var $elmnt = $('#Searchbutton');
				var template1 = angular.element('<i class="fas fa-search"></i>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;
		case 4:
			$timeout(function () {
				var $elmnt = $('#Searchdiv');
				var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;
		case 5:
			$timeout(function () {
				var $elmnt = $('#SearchClose');
				var template1 = angular.element('<i class="fas fa-window-close"></i>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;

	};


};


SeacrchScopeTransfer.setUserNameSearchScope($childScope);

};


function fnUserRoleIDSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchUserRole = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetUserRole = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		if (MenuName == "UserProfile" && tabClick == "Parent") {
			mainScope.parentRoleMappingRecord.roleID = Y.roleID;
		} else if (MenuName == "UserProfile" && tabClick == "Class") {
			mainScope.studentClassRoleMappingRecord.roleID = Y.roleID;
		} else if (MenuName == "UserProfile" && tabClick == "Teacher") {
			mainScope.teacherRoleMappingRecord.roleID = Y.roleID;
		} else if (MenuName == "UserProfile" && tabClick == "Institute") {
			mainScope.instituteRoleMappingRecord.roleID = Y.roleID;
		} else {
//			mainScope.roleID = Y.RoleId;
//			mainScope.roleDescription = Y.RoleDescription;
                        mainScope.roleID = Y.roleID;
			mainScope.roleDescription = Y.roleDescription;
		}


		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnFilter = function (){
	fnSearchBackEnd("UserRoleSearchService", this.SearchUserRole, $childScope);
}
$childScope.fnPostFilter = function ()

{
	// var filter=[];
	$('#UserRoleSeacrhList').empty();
	$('#UserRoleSeacrhList').removeClass("SearchBody");
	/*this.filterList.length=0;
                           if(this.SearchUserRole!=null && this.SearchUserRole!='')
						   {							   
							if(this.SearchUserRole.length!=0)   
						   { 
                             angular.forEach(Institute.RoleMaster,function(value,key){
	                          if (this[key].RoleId.includes($('#SearchUserRole').val()) || (this[key].RoleDescription.includes($('#SearchUserRole').val())))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.RoleMaster);
							    this.filterList=filter;*/

	$timeout(function () {
		var $elmnt = $('#UserRoleSeacrhList');
		var template1 = angular.element('<a class="list-group-item list-group-item-action" style="color:#228272" ng-click="fngetUserRole(Y)" ng-repeat="Y in filterList | orderBy:RoleId" id="SearchAnchor{{$index}}">' +
			'<div class="row">' +
			'<span class="col-6 SearchListColor"> {{Y.roleID}}</span>' +
			'<span class="col-6 SearchListColor"> {{Y.roleDescription}}  </span>' +
			'</div>' +
			'</a>');
		$elmnt.prepend(template1);
		$compile(template1)($childScope);
		$elmnt.addClass("SearchBody");

	});


	// } }
};

$childScope.fnClose = function ()

{

	this.filterList.length = 0;
	var mainScope = SeacrchScopeTransfer.getMainScope($scope);
	mainScope.searchShow = false;
	$('#searchHeader').empty(); //Integration changes
	$('#searchBody').empty();
	$("#subscreenHeader").removeClass("SearchHeader");
	$('#subscreenContent').removeClass("SearchBody");
	$("#subscreenHeader").addClass("subscreenHeader");
	$('#subscreenContent').addClass("subscreenContent");


};


$childScope.addElement = function (i) {

	switch (i) {
		case 1:
			$timeout(function () {
				var $elmnt = $('#SrchUserRolediv');
				//var template1=angular.element('<input id="SearchUserRole" ng-change="fnFilter()" ng-Model="SearchUserRole" type="text" placeholder="Search Role Id" class="form-control">');
				var template1 = angular.element('<input id="SearchUserRole" ng-Model="SearchUserRole" type="text" placeholder="Search Roll ID " class="form-control">'); //Integration changes
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
                         var mainScope = SeacrchScopeTransfer.getMainScope($scope);        
                 if (MenuName == "UserProfile" && tabClick == "Parent") {
                     $childScope.SearchUserRole=mainScope.parentRoleMappingRecord.roleID ;
		 // mainScope.parentRoleMappingRecord.roleID = Y.roleID;
		} else if (MenuName == "UserProfile" && tabClick == "Class") {
		//mainScope.studentClassRoleMappingRecord.roleID = Y.roleID;
                  $childScope.SearchUserRole=mainScope.studentClassRoleMappingRecord.roleID ;
		} else if (MenuName == "UserProfile" && tabClick == "Teacher") {
		//mainScope.teacherRoleMappingRecord.roleID = Y.roleID;
                 $childScope.SearchUserRole=mainScope.teacherRoleMappingRecord.roleID ;
		} else if (MenuName == "UserProfile" && tabClick == "Institute") {
		//mainScope.instituteRoleMappingRecord.roleID = Y.roleID;
                 $childScope.SearchUserRole=mainScope.instituteRoleMappingRecord.roleID ;
		} else {
		      //mainScope.roleID = Y.RoleId;
		      //mainScope.roleDescription = Y.RoleDescription;
                      // mainScope.roleID = Y.roleID;
		      //mainScope.roleDescription = Y.roleDescription;
                       $childScope.SearchUserRole=mainScope.roleID;
		}
                         
                         
                        // $childScope.SearchUserRole=mainScope.roleID;
			break;
		case 2:
			$timeout(function () {
				var $elmnt = $('#Searchdiv');
				var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;
		case 3:
			$timeout(function () {
				var $elmnt = $('#Searchbutton');
				var template1 = angular.element('<i class="fas fa-search"></i>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;
		case 4:
			$timeout(function () {
				var $elmnt = $('#Searchdiv');
				var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;
		case 5:
			$timeout(function () {
				var $elmnt = $('#SearchClose');
				var template1 = angular.element('<i class="fas fa-window-close"></i>');
				$elmnt.prepend(template1);
				$compile(template1)($childScope);
			});
			break;

	};


};


SeacrchScopeTransfer.setSearchScope($childScope);

}
function fnNotificationNameSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchNotificationName = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetNotification = function (Y) {
		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		mainScope.notificationType = Y.notificationType;
		mainScope.notificationID = Y.notificationID;
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("NotificationSearchService", this.SearchNotificationName, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function ()


	{
		//var filter=[];
		$('#NotificationSeacrhList').empty();
		$('#NotificationSeacrhList').removeClass("SearchBody");
		//this.filterList.length=0;
		/*if(this.SearchNotificationName!=null && this.SearchNotificationName!='')
						   {							   
							if(this.SearchNotificationName.length!=0)   
						   { 
                             angular.forEach(Institute.NotificationSearchMaster,function(value,key){
	                          if (this[key].NotifificationType.includes($('#SearchNotificationName').val()) || this[key].NotifificationID.includes($('#SearchNotificationName').val()))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.NotificationSearchMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#NotificationSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" ng-click="fngetNotification(Y)" ng-repeat="Y in filterList | orderBy:NotifificationType" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.notificationType}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.notificationID}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");
		});


		//} }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchNotificationdiv');
					// var template1=angular.element('<input id="SearchNotificationName" ng-change="fnFilter()" ng-Model="SearchNotificationName" type="text" placeholder="Search Notification Type or ID" class="form-control">');
					var template1 = angular.element('<input id="SearchNotificationName" ng-Model="SearchNotificationName" type="text" placeholder="Search Notification" class="form-control">'); //Integration changes
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                 var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchNotificationName=mainScope.notificationID;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setSearchScope($childScope);

}

function fnGroupIDSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchGroupId = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetGrouping = function (Y) {
		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
                
                if (MenuName == "Notification" ) {
                    mainScope.assignee = Y.groupID;
		 // mainScope.parentRoleMappingRecord.roleID = Y.roleID;
		}else{
                
		mainScope.groupID = Y.groupID;
		mainScope.groupDescription = Y.groupDescription;
                
               }
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("GroupIDSearchService", this.SearchGroupId, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function () {
		//var filter=[];
		$('#GroupingSeacrhList').empty();
		$('#GroupingSeacrhList').removeClass("SearchBody");
		/* this.filterList.length=0;
                           if(this.SearchGroupId!=null && this.SearchGroupId!='')
						   {							   
							if(this.SearchGroupId.length!=0)   
						   { 
                             angular.forEach(Institute.GroupMappingMaster,function(value,key){
	                          if (this[key].GroupId.includes($('#SearchGroupId').val()) || this[key].GroupDescription.includes($('#SearchGroupId').val()))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.GroupMappingMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#GroupingSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" ng-click="fngetGrouping(Y)" ng-repeat="Y in filterList | orderBy:GroupId" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.groupID}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.groupDescription}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchGroupdiv');
					//var template1=angular.element('<input id="SearchGroupId" ng-change="fnFilter()" ng-Model="SearchGroupId" type="text" placeholder="Search Group ID" class="form-control">');
					var template1 = angular.element('<input id="SearchGroupId" ng-Model="SearchGroupId" type="text" placeholder="Search Group ID" class="form-control">'); //Integration changes
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                  var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchGroupId=mainScope.groupID;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setGroupingSearchScope($childScope);

}


function fnCircularIDSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchCircularId = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetCircularing = function (Y) {
		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
                
//                if (MenuName == "Notification" ) {
//                    mainScope.assignee = Y.groupID;
//		 // mainScope.parentRoleMappingRecord.roleID = Y.roleID;
//		}else{
                
		mainScope.circularID = Y.ECircularID;
		mainScope.circularDescription = Y.ECircularDescription;
                
//               }
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("ECircularSearchService", this.SearchCircularId, $childScope);
		/*if(this.filterList!=null && this.filterList.length>0)
		 this.fnPostFilter();*/
	}
	$childScope.fnPostFilter = function () {
		//var filter=[];
		$('#CircularingSeacrhList').empty();
		$('#CircularingSeacrhList').removeClass("SearchBody");
		/* this.filterList.length=0;
                           if(this.SearchCircularId!=null && this.SearchCircularId!='')
						   {							   
							if(this.SearchCircularId.length!=0)   
						   { 
                             angular.forEach(Institute.CircularMappingMaster,function(value,key){
	                          if (this[key].CircularId.includes($('#SearchCircularId').val()) || this[key].CircularDescription.includes($('#SearchCircularId').val()))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.CircularMappingMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#CircularingSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" ng-click="fngetCircularing(Y)" ng-repeat="Y in filterList | orderBy:CircularId" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.ECircularID}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.ECircularDescription}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchCirculardiv');
					//var template1=angular.element('<input id="SearchCircularId" ng-change="fnFilter()" ng-Model="SearchCircularId" type="text" placeholder="Search Circular ID" class="form-control">');
					var template1 = angular.element('<input id="SearchCircularId" ng-Model="SearchCircularId" type="text" placeholder="Search Circular ID" class="form-control">'); //Integration changes
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                  var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchCircularId=mainScope.groupID;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setCircularingSearchScope($childScope);

}







function fnAssigneeSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchAssigneeId = null;
	$childScope.filterList = filter;
	$childScope.fngetAssignee = function (Y) {
		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		mainScope.assignee = Y.AssigneeId;
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("AssigneeSearchService", this.SearchAssigneeId, $childScope);
	}
	$childScope.fnPostFilter = function ()


	{
		// var filter=[];
		$('#AssigneeSeacrhList').empty();
		$('#AssigneeSeacrhList').removeClass("SearchBody");
		/*this.filterList.length=0;
                           if(this.SearchAssigneeId!=null && this.SearchAssigneeId!='')
						   {							   
							if(this.SearchAssigneeId.length!=0)   
						   { 
                             angular.forEach(Institute.AssigneeMaster,function(value,key){
	                          if (this[key].AssigneeId.includes($('#SearchAssigneeId').val()))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.AssigneeMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#AssigneeSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" style="color:#228272" ng-click="fngetAssignee(Y)" ng-repeat="Y in filterList | orderBy:AssigneeId" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.assigneeID}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchAssigneediv');
					//var template1=angular.element('<input id="SearchAssigneeId" ng-change="fnFilter()" ng-Model="SearchAssigneeId" type="text" placeholder="Search AssigneeId ID" class="form-control">');
					var template1 = angular.element('<input id="SearchAssigneeId" ng-Model="SearchAssigneeId" type="text" placeholder="Search AssigneeId " class="form-control">'); //Integration changes
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                 var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchAssigneeId=mainScope.AssigneeId;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	//SeacrchScopeTransfer.setSearchScope($childScope);
	SeacrchScopeTransfer.setAssigneeSearchScope($childScope);

}


function fnOtheractivitySearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchActivityID = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetActivity = function (Y) {
		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		mainScope.activityID = Y.activityID;
		mainScope.activityName = Y.activityName;
                
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("OtherActivitySearchService", this.SearchActivityID, $childScope);
	}
	$childScope.fnPostFilter = function ()


	{
		//var filter=[];
		$('#OtherActivitySeacrhList').empty();
		$('#OtherActivitySeacrhList').removeClass("SearchBody");
		/*this.filterList.length=0;
                           if(this.SearchActivityID!=null && this.SearchActivityID!='')
						   {							   
							if(this.SearchActivityID.length!=0)   
						   { 
                             angular.forEach(Institute.OtherActivityIdMaster,function(value,key){
	                          if (this[key].ActivityId.includes($('#SearchActivityID').val()) || this[key].ActivityType.includes($('#SearchActivityID').val()))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.OtherActivityIdMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#OtherActivitySeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" style="color:blue" ng-click="fngetActivity(Y)" ng-repeat="Y in filterList | orderBy:ActivityType" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.activityID}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.activityName}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		//} }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchActivitydiv');
					//  var template1=angular.element('<input id="SearchActivityID" ng-change="fnFilter()" ng-Model="SearchActivityID" type="text" placeholder="Search Activity ID" class="form-control">');
					var template1 = angular.element('<input id="SearchActivityID" ng-Model="SearchActivityID" type="text" placeholder="Search Activity ID" class="form-control">'); //Integration changes
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                 var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchActivityID=mainScope.activityID;
                                
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setOtherActivitySearchScope($childScope);

}

function fnBatchNameSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchBatchNameKey = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetBatchName = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		mainScope.batchName = Y.BatchName;
		mainScope.batchDescription = Y.BatchDescription;
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnFilter = function (){
	//$childScope.fnStuFilter = function () {
		fnSearchBackEnd("BatchSearchService", this.SearchBatchNameKey, $childScope);
	}
	$childScope.fnPostFilter = function ()


	{
		var filter = [];
		$('#BatchNameSeacrhList').empty();
		$('#BatchNameSeacrhList').removeClass("SearchBody");
		/*  this.filterList.length=0;
                           if(this.SearchBatchNameKey!=null && this.SearchBatchNameKey!='')
						   {							   
							if(this.SearchBatchNameKey.length!=0)   
						   { 
                             angular.forEach(Institute.BatchNameMaster,function(value,key){
	                         // if (this[key].AssignmentDescription.includes($('#SearchAssignmentDescription').val()))
							if (this[key].BatchName.includes($('#SearchBatchNameKey').val()) || (this[key].BatchDescription.includes($('#SearchBatchNameKey').val()) ))	 
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.BatchNameMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#BatchNameSeacrhList');
			var template3 = angular.element('<a class="list-group-item list-group-item-action" style="color:#228272" ng-click="fngetBatchName(Y)" ng-repeat="Y in filterList | orderBy:BatchName" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.batchDescription}}</span>' +
				'<span class="col-6 SearchListColor"> {{Y.batchName}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template3);
			$compile(template3)($childScope);
			$elmnt.addClass("SearchBody");
		});

		//} }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");


	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchBatchNamediv');
					//var template3=angular.element('<input id="SearchBatchNameKey" ng-change="fnFilter()" ng-Model="SearchBatchNameKey" type="text" placeholder="Search Batch Name" class="form-control">');
					var template3 = angular.element('<input id="SearchBatchNameKey" ng-Model="SearchBatchNameKey" type="text" placeholder="Search Batch  Name" class="form-control">'); //Integration changes
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
                                 var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchBatchNameKey=mainScope.batchName;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template3 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template3 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template3 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template3 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template3);
					$compile(template3)($childScope);
				});
				break;

		};


	};


	//SeacrchScopeTransfer.setSearchScope($childScope);
	SeacrchScopeTransfer.setBatchNameScope($childScope);

}






function fnInstituteUserSearch($scope, $compile, $timeout, SeacrchScopeTransfer) {
	var filter = [];
	$childScope = $scope;
	$childScope.SearchUserInstitute = null;
	$childScope.filterList = filter;
	//$childScope.searchShow=false;
	$childScope.fngetUserInstitute = function (Y) {
		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
//		mainScope.instituteID = Y.instituteId;
//		mainScope.instituteName = Y.instituteName;
                
                if (MenuName == "UserProfile")

		{
			if (tabClick != null && tabClick == "Institute") {
				mainScope.instituteRoleMappingRecord.instituteName = Y.instituteName;
				mainScope.instituteRoleMappingRecord.instituteID = Y.instituteID;
			} else if (tabClick != null && tabClick == "Teacher") {
				mainScope.teacherRoleMappingRecord.instituteName = Y.instituteName;
				mainScope.teacherRoleMappingRecord.instituteID = Y.instituteID;
			}
                         else if (tabClick != null && tabClick == "Parent") {
				mainScope.parentRoleMappingRecord.instituteName = Y.instituteName;
				mainScope.parentRoleMappingRecord.instituteID = Y.instituteID;
			}
                        
                        else if (tabClick != null && tabClick == "Class") {
				mainScope.studentClassRoleMappingRecord.instituteName = Y.instituteName;
				mainScope.studentClassRoleMappingRecord.instituteID = Y.instituteID;
			}else if (tabClick != null && tabClick == "General") {
				mainScope.instituteName = Y.instituteName;
		        	mainScope.instituteID = Y.instituteID;
			}
		} else {
			mainScope.instituteName = Y.instituteName;
			mainScope.instituteID = Y.instituteID;
		}
                
		$('#searchHeader').empty(); //Integration changes
		$('#searchBody').empty();
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	}


	$childScope.fnFilter = function () {
		fnSearchBackEnd("InstituteUserSearchService", this.SearchUserInstitute, $childScope);
	}
	$childScope.fnPostFilter = function ()


	{
		// var filter=[];
		$('#InstituteUserSeacrhList').empty();
		$('#InstituteUserSeacrhList').removeClass("SearchBody");
		/*this.filterList.length=0;
                           if(this.SearchFeeID!=null && this.SearchFeeID!='')
						   {							   
							if(this.SearchFeeID.length!=0)   
						   { 
                             angular.forEach(Institute.FeeIDMaster,function(value,key){
	                          if (this[key].FeeId.includes($('#SearchFeeID').val()) || (this[key].FeeType.includes($('#SearchFeeID').val())))
			                   {  			
	                             filter.push(this[key]);
                                }},Institute.FeeIDMaster);
							    this.filterList=filter;*/

		$timeout(function () {
			var $elmnt = $('#InstituteUserSeacrhList');
			var template1 = angular.element('<a class="list-group-item list-group-item-action" ng-click="fngetUserInstitute(Y)" ng-repeat="Y in filterList | orderBy:InstituteId" id="SearchAnchor{{$index}}">' +
				'<div class="row">' +
				'<span class="col-6 SearchListColor"> {{Y.instituteID}}  </span>' +
				'<span class="col-6 SearchListColor"> {{Y.instituteName}}  </span>' +
				'</div>' +
				'</a>');
			$elmnt.prepend(template1);
			$compile(template1)($childScope);
			$elmnt.addClass("SearchBody");

		});


		// } }
	};

	$childScope.fnClose = function ()

	{

		this.filterList.length = 0;
		//this.searchShow=false;
		var mainScope = SeacrchScopeTransfer.getMainScope($scope);
		mainScope.searchShow = false;
		$('#searchHeader').empty(); //Integration changes					 
		$('#searchBody').empty(); //Integration changes					 };
		$("#subscreenHeader").removeClass("SearchHeader");
		$('#subscreenContent').removeClass("SearchBody");
		$("#subscreenHeader").addClass("subscreenHeader");
		$('#subscreenContent').addClass("subscreenContent");

	};


	$childScope.addElement = function (i) {

		switch (i) {
			case 1:
				$timeout(function () {
					var $elmnt = $('#SrchInstituteUseriv');
					// var template1=angular.element('<input id="SearchFeeID" ng-change="fnFilter()" ng-Model="SearchFeeID" type="text" placeholder="Search Fee ID" class="form-control">');
					var template1 = angular.element('<input id="SearchUserInstitute" ng-Model="SearchUserInstitute" type="text" placeholder="Search Institute ID" class="form-control">'); //Integration changes

					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
                                 var mainScope = SeacrchScopeTransfer.getMainScope($scope);
                                 $childScope.SearchUserInstitute=mainScope.instituteName;
				break;
			case 2:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="Searchbutton" type="button" ng-click="fnFilter()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 3:
				$timeout(function () {
					var $elmnt = $('#Searchbutton');
					var template1 = angular.element('<i class="fas fa-search"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 4:
				$timeout(function () {
					var $elmnt = $('#Searchdiv');
					var template1 = angular.element('<button id="SearchClose" type="button" ng-click="fnClose()" class="btn btn-primary"></button>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;
			case 5:
				$timeout(function () {
					var $elmnt = $('#SearchClose');
					var template1 = angular.element('<i class="fas fa-window-close"></i>');
					$elmnt.prepend(template1);
					$compile(template1)($childScope);
				});
				break;

		};


	};


	SeacrchScopeTransfer.setInstituteUserScope($childScope);

}

app.service('searchCallService', function ($compile, $timeout, SeacrchScopeTransfer) {
	this.searchLaunch = function (searchCallInput) {
                searchCallInputType  = searchCallInput.searchType;    
		searchCallInput.mainScope.searchShow = true;
		switch (searchCallInput.searchType) {
			case 'Teacher':
				var headerTemplate =
					// var template='<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="Srchstaffdiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n'; //Integeration Changes
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="TeacherSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
			case 'Student':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="Srchstudfdiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n'; //Integeration Changes
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="StudentSearchList">\n' +
					'</div>\n' +
					'</div>\n';
				break;

			case 'Institute':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+ Integration changes
					'<div class=" cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchInstitutediv" class=" input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					//'</div>\n', //Integration changes
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="InstituteSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
                                case 'InstituteUser':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+ Integration changes
					'<div class=" cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchInstituteUseriv" class=" input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					//'</div>\n', //Integration changes
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="InstituteUserSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
                        case 'InstituteChange':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+ Integration changes
					'<div class=" cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchInstitutediv" class=" input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					//'</div>\n', //Integration changes
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="InstituteSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
                                       
                                
			case 'Assignment':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchAssignmentdiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="AssignmentSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;

			case 'Fee':
				var headerTemplate =
					//  var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchFeediv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' ;
					var bodyTemplate = '<div class="cohesive_border">\n' +
						'<div class="list-group" id="FeeIDSeacrhList">\n' +
						'</div>\n' +
						'</div>\n';
				break;

			case 'Payment':

				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchPaymentdiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="PaymentIDSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;

			case 'UserName':
				var headerTemplate =
					//  var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchUserdiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="UserSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
			case 'UserRole':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchUserRolediv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="UserRoleSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
			case 'Notification':
				var headerTemplate =
					// var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchNotificationdiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="NotificationSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
			case 'Group':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchGroupdiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="GroupingSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
                        case 'Circular':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchCirculardiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="CircularingSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;        
			case 'Assignee':
				var headerTemplate =
					//var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchAssigneediv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="AssigneeSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
			case 'Activity':
				var headerTemplate =
					// var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchActivitydiv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="OtherActivitySeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;
			case 'Batch':
				var headerTemplate =
					// var template= '<div class="cohesive_border">\n'+
					'<div class="cohesive_topmargin">\n' +
					'<div class ="row">\n' +
					'<div class="col-12">\n' +
					'<div id="SrchBatchNamediv" class="input-group">\n' +
					'<div id="Searchdiv" class="input-group-append">\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n' +
					'</div>\n';
				var bodyTemplate = '<div class="cohesive_border">\n' +
					'<div class="list-group" id="BatchNameSeacrhList">\n' +
					'</div>\n' +
					'</div>\n';
				break;

		}
		//$('#search').append(template); //Integration changes
		$("#subscreenHeader").removeClass("subscreenHeader");
		$('#subscreenContent').removeClass("subscreenContent");
		$("#subscreenHeader").addClass("SearchHeader");
		$('#subscreenContent').addClass("SearchBody");


		$('#searchHeader').append(headerTemplate);
		$('#searchBody').append(bodyTemplate);

		if (searchCallInput.searchType == 'Student') {
			$childScope = SeacrchScopeTransfer.getStuSearchScope();
		} else if (searchCallInput.searchType == 'Assignment') {
			$childScope = SeacrchScopeTransfer.getAssSearchScope();
		} else if (searchCallInput.searchType == 'Payment') {
			$childScope = SeacrchScopeTransfer.getPaymentSearchScope();
		} else if (searchCallInput.searchType == 'Institute') {
			$childScope = SeacrchScopeTransfer.getInstituteSearchScope();
		} else if (searchCallInput.searchType == 'InstituteChange') {
			$childScope = SeacrchScopeTransfer.getInstituteSearchScope();
		}
                 else if (searchCallInput.searchType == 'Teacher') {
			$childScope = SeacrchScopeTransfer.getTeacherSearchScope();
		} else if (searchCallInput.searchType == 'UserName') {
			$childScope = SeacrchScopeTransfer.getUserNameSearchScope();
		} else if (searchCallInput.searchType == 'Group') {
			$childScope = SeacrchScopeTransfer.getGroupingSearchScope();
		} else if (searchCallInput.searchType == 'Assignee') {
			$childScope = SeacrchScopeTransfer.getAssigneeSearchScope();
		} else if (searchCallInput.searchType == 'Activity') {
			$childScope = SeacrchScopeTransfer.getOtherActivitySearchScope();
		} else if (searchCallInput.searchType == 'Batch') {
			$childScope = SeacrchScopeTransfer.getBatchNameSearchScope();
		}
                else if (searchCallInput.searchType == 'InstituteUser') {
			$childScope = SeacrchScopeTransfer.getInstitueUserSearchScope();
		}else if (searchCallInput.searchType == 'Circular') {
			$childScope = SeacrchScopeTransfer.getCircularingSearchScope();
		}
                else {
			$childScope = SeacrchScopeTransfer.getSearchScope();
		}

		$childScope.addElement(1);
		$childScope.addElement(4);
		$childScope.addElement(5);
		$childScope.addElement(2);
		$childScope.addElement(3);

		$childScope.filterList.length = 0;
	}
});


app.factory('SeacrchScopeTransfer', function () {

	var searchScope = {};
	var searchStuScope = {};
	var searchAssScope = {};
	var searchPaymentScope = {};
	var mainScope = {};
	return {
		getSearchScope: function () {
			return searchScope;
		},
		setSearchScope: function (inScope) {
			searchScope = inScope;
		},
		setStuSearchScope: function (inScope) {
			searchStuScope = inScope;
		},
		setAssSearchScope: function (inScope) {
			searchAssScope = inScope;
		},
		setPaymentSearchScope: function (inScope) {
			searchPaymentScope = inScope;
		},
		setInstituteSearchScope: function (inScope) {
			searchInstituteScope = inScope;
		},
		setTeacherSearchScope: function (inScope) {
			searchTeacherScope = inScope;
		},
		setUserNameSearchScope: function (inScope) {
			searchUserNameScope = inScope;
		},
		setGroupingSearchScope: function (inScope) {
			searchGroupingScope = inScope;
		},
                setCircularingSearchScope: function (inScope) {
			searchCircularingScope = inScope;
		},
		setAssigneeSearchScope: function (inScope) {
			searchAssigneeScope = inScope;
		},
		setOtherActivitySearchScope: function (inScope) {
			searchOtherActivityScope = inScope;
		},
		setBatchNameScope: function (inScope) {
			searchBatchNameScope = inScope;
		},
                setInstituteUserScope: function (inScope) {
			searchInstituteUserScope = inScope;
		},
		getMainScope: function () {
			return mainScope;
		},
		setMainScope: function (inScope) {
			mainScope = inScope;
		},
		getStuSearchScope: function () {
			return searchStuScope;
		},
		getAssSearchScope: function () {
			return searchAssScope;
		},
		getPaymentSearchScope: function () {
			return searchPaymentScope;
		},
		getInstituteSearchScope: function () {
			return searchInstituteScope;
		},
		getTeacherSearchScope: function () {
			return searchTeacherScope;
		},
		getUserNameSearchScope: function () {
			return searchUserNameScope;
		},
		getGroupingSearchScope: function () {
			return searchGroupingScope;
		},
                getCircularingSearchScope: function () {
			return searchCircularingScope;
		},
		getAssigneeSearchScope: function () {
			return searchAssigneeScope;
		},
		getOtherActivitySearchScope: function () {
			return searchOtherActivityScope;
		},
		getBatchNameSearchScope: function () {
			return searchBatchNameScope;
		},
                getInstitueUserSearchScope: function () {
			return searchInstituteUserScope;
		},

	};
});

function fnSearchBackEnd(service, searchFilter, childScope) {

	var url;
	var request;
	switch (service) {
		case 'InstituteSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					instituteName: null,
					instituteID: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

			url = searchURL+ "Institute" + "/" + service;
			
                        if(MenuName=='cohesiveMainPage' || searchCallInputType=='InstituteChange'){
                            request = fnDirectFrameRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
                        }
                        else
                        {   
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
                        }
                        break;
                        
                        
                        case 'InstituteUserSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					instituteName: null,
					instituteID: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
                        url = searchURL + "Institute" + "/" + service;
			request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;
		case 'TeacherNameSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					teacherName: null,
					teacherID: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
                        url = searchURL + "Institute" + "/" + service;
			request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;


		case 'StudentSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					studentName: null,
					studentID: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;


		case 'ClassAssignmentSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					assignmentDescription: null,
					assignmentID: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
                        url = searchURL + "Institute" + "/" + service;
			request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;


		case 'FeeIDSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					feeID: null,
					feeType: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
                        url = searchURL + "Institute" + "/" + service;
			request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;


		case 'PaymentSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					paymentID: null,
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
                        url = searchURL + "Institute" + "/" + service;
			request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;


		case 'UserSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					userName: null,
					userID: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
                        url = searchURL + "Institute" + "/" + service;
			request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;


		case 'UserRoleSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					roleID: null,
					roleDescription: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;

		case 'NotificationSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					notificationID: null,
					notificationType: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;


		case 'GroupIDSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					groupID: null,
					groupDescription: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;

		case 'AssigneeSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					assigneeID: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;

		case 'OtherActivitySearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					activityID: null,
					activityType: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;

		case 'BatchSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					batchName: null,
					batchDescription: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;
                       
                       case 'ECircularSearchService':
			var datamodel = {

				searchFilter: "",
				searchResults: [{
					ECircularID: null,
					ECircularDescription: null
				}]
			}

			if (searchFilter == null) {
				searchFilter = "";
			}
			datamodel.searchFilter = searchFilter;

//			url = "https://cohesive.ibdtechnologies.com/CohesiveGateway/" + "Institute" + "/" + service;
			url = searchURL + "Institute" + "/" + service;
                        request = fnframeRequest(service, "View", datamodel, [{
				entityName: "filter",
				entityValue: searchFilter
			}], {});
			break;

	}
	window.parent.fn_show_spinner();
	var config = {
		headers: {
			//"X-uhtuliak" : window.parent.uhtuliak
		}
	};
	$.ajax({
		//url: $('#imgUpld').attr('action'),

		url: url,
		type: 'PUT',
		//async: false,
		// enctype:'application/json',
		dataType: 'json',
		contentType: 'application/json',
		cache: false,
		data: JSON.stringify(request),
		processData: false,
		success: function (data) {
			window.parent.fn_hide_parentspinner();
			//console.log('form submitted.');
			if (data.header.status == "error") {
				for (i = 0; i < data.error.length; i++) {
					if (data.error[i].errorCode == 'BS_VAL_101') {
						var error = [{
							errorCode: "",
							errorMessage: ""
						}];
						error[0].errorCode = data.error[i].errorCode;
						error[0].errorMessage = data.error[i].errorMessage;

						window.parent.fnMainPagewithError(error);
						window.parent.$("#frame").attr('src', '');
						return;
					}

				}

				fnErrorResponseHandling(data.error);
				return null;
			} else {
				childScope.filterList = data.body.searchResults;
				childScope.fnPostFilter();

			}
			return null;
		},
		error: function (xhr) {
			window.parent.fn_hide_parentspinner();
			var error = [{
				errorCode: "",
				errorMessage: ""
			}];
			error[0].errorCode = xhr.status;
			error[0].errorMessage = xhr.responseText;

			fn_show_backend_exception(error);
			return null;
		}
	});

	return null;
}


	
