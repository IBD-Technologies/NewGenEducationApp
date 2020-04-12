/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
   
/*function fn_character_validation(p_character_validation)
{
var filter = (/^[A-z@0-9]{10,15}$/g);
return filter.test(p_character_validation);
}
function fn_password_validation(p_password_validation)
{  
    var filter =(/^[0-9A-Za-z]{10,15}$/g);
    return filter.test(p_password_validation);
}
*/
function fngetDay(day)
{
   switch(day)
   {
     case 'Mon' :
       return 'Monday';
      break;
     case 'Tue' :
       return 'Tuesday';
      break; 	 
     case 'Wed' :
       return 'Wednesday';
      break;
     case 'Thu' :
       return 'Thursday';
      break; 	 
     case 'Fri' :
       return 'Friday';
      break; 	 
     case 'Sat' :
       return 'Saturday';
      break; 	 
   	 case 'Sun' :
       return 'Sunday';
      break; 
    }
}	

function fnStdSelectHandler($scope)
{
//var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	

try{
	                $scope.Class=Institute.StandardMaster;
					$scope.Class.forEach(function(value,index,array){
						                     

						                     //if(value.standard ==$('#standardID').find("option:selected").text())
												 if(value.standard ==$scope.standard)
											 { 
						                       $scope.ClassSection=value.section;
											   $scope.section = "Select option";
											   
											   throw "done";
											 }
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 }


function fnStdSectionHandler()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	

try{
	                $scope.Class=Institute.StandardMaster;
					$scope.Class.forEach(function(value,index,array){
						                     

						                     if(value.standard ==$('#standardID').find("option:selected").text())
											 { 
						                       $scope.ClassSection=value.section;
											   throw "done";
											 }
											 
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 }

function fn_check_access_rights()
{
    return true;
}

function fnStdSectionViewHandler()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	                $scope.Class=Institute.StandardMaster;
					$scope.Class.forEach(function(value,index,array){
						try {
						                     if(value.standard ==$scope.standard)
											 { 
						                       $scope.ClassSection=value.section;
											   if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }
											   throw "done";
											 }
											   }
								
				        catch(e){if(e!="done") throw e;}
				 });
}

function fileUpload(id,form,url,service){
    
//var form=$('#imgUpld')[0];
    var data = new FormData(form);
    data.append("user",window.parent.User.Id);
    data.append("institute",window.parent.Institute.ID);
    data.append("token",window.parent.nokotser);
    //data.append("service","GeneralLevelConfiguration");
    data.append("service",service);
     window.parent.fn_show_spinner();
    $.ajax({
      //url: $('#imgUpld').attr('action'),
      
      url:url,
      type: 'PUT',
      enctype:'multipart/form-data',
      cache:false,
      data : data,
      processData: false,
      contentType: false,
      success: function(xhr){
        //console.log('form submitted.');
        //if(xhr.status == '200')
        //{  //$("#logo").attr("src","/img/uploads/"+xhr.responseText);
             window.parent.fn_hide_parentspinner();
             if(xhr.includes("success"))
             {
                var res=xhr.split("~"); 
              
                  // src.fileName= res[2];  
                   //src.uploadID= res[1]; 
                   fnPostImageUpload(id,res[2],res[1]);
              } 
              else 
              {
                  var res=xhr.split("~");
                  var errCode = res[1];
                  var errMsg= res[2];
                  var error= [{
			errorCode: "",
			errorMessage: ""
		           }];
	                    error[0].errorCode=errCode;
                            error[0].errorMessage=errMsg;
 
                  if(errCode=='BS-VAL-101')
                       {
                         
                        window.parent.fnMainPagewithError(error); 
                         window.parent.$("#frame").attr('src', '');
                      return false;
                    }
                  else
                  {
                      fn_show_backend_exception(error);
                      return false;
                  }
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
                     
          fn_show_backend_exception(error);
                      return false;
      }
    });
    return true;
}





/*			 
function fnClassSelectHandler(selectedClass)
{
try{
	               // $scope.per=Institute.PeriodMaster.;
				   				Institute.PeriodMaster.forEach(function(value,index,array){
						                    
												 if(value.class ==selectedClass)
											 { 
						                       $scope.MondayRecord.period=value.class;
											   $scope.MondayRecord.periodNumber = "Select option";
											   $scope.TuesdayRecord.period=value.class;
											   $scope.TuesdayRecord.periodNumber = "Select option";
											   $scope.WednesdayRecord.period=value.class;
											   $scope.WednesdayRecord.periodNumber = "Select option";
											   $scope.ThursdayRecord.period=value.class;
											   $scope.ThursdayRecord.periodNumber = "Select option";
											   $scope.FridayRecord.periodNumber = "Select option";
							
											   throw "done";
											 }
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 }


function fnClassPeriodHandler()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	

try{
	                $scope.classes=Institute.ClassMaster;
					$scope.classes.forEach(function(value,index,array){
						                     

						                     if(value.class ==$('#MondayID').find("option:selected").text())
											 { 
						                       $scope.ClassPeriod=value.PeriodNumber;
											   throw "done";
											 }
											  if(value.class ==$('#TuesdayID').find("option:selected").text())
											 { 
						                       $scope.ClassPeriod=value.PeriodNumber;
											   throw "done";
											 }
											  if(value.class ==$('#WednesdayID').find("option:selected").text())
											 { 
						                       $scope.ClassPeriod=value.PeriodNumber;
											   throw "done";
											 }
											  if(value.class ==$('#ThursdayID').find("option:selected").text())
											 { 
						                       $scope.ClassPeriod=value.PeriodNumber;
											   throw "done";
											 }
											  if(value.class ==$('#FridayID').find("option:selected").text())
											 { 
						                       $scope.ClassPeriod=value.PeriodNumber;
											   throw "done";
											 }
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 }

function fnPeriodClassViewHandler()
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
	
	                $scope.classes=Institute.ClassMaster;
					$scope.classes.forEach(function(value,index,array){
						try {
						                     if(value.class ==$scope.class)
											 { 
						                       $scope.ClassPeriod=value.PeriodNumber;
											   if ($scope.operation !='View')
	                                            {
	                                         $scope.$apply();
	                                                }
											   throw "done";
											 }
											   }
								
				        catch(e){if(e!="done") throw e;}
				 });
}				 


*/





