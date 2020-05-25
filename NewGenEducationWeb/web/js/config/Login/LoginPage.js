var app = angular.module('Main',['BackEnd']);
/*var User = {
	Id: ""
};*/
  
/*var Institute = {
	ID: "",
	Name: ""
        };*/
   
var MenuName="Login";
var parentOperation;
app.controller('MainCtrl', function($scope,appServerCallService) {
$scope.appServerCall = appServerCallService.backendCall;    
$scope.userName="";
$scope.loginPassword="";
$scope.eMsg="";
$scope.mobile="";
$scope.OTP="";
$scope.newPwd="";
$scope.newConfirmPwd="";
$scope.token="";
$scope.audit={};

$scope.errMessage="";
$scope.step1Show = true;
$scope.step2Show = false;
$scope.step3Show = false;
$scope.errorShow=false;
$scope.spinnerShow=false;
   });

$(document).ready(function(){
	fn_hide_spinner();
	
$('#sign_in').validate({
        highlight: function (input) {
            console.log(input);
            $(input).parents('.form-line').addClass('error');
        },
        unhighlight: function (input) {
            $(input).parents('.form-line').removeClass('error');
        },
        errorPlacement: function (error, element) {
            $(element).parents('.input-group').append(error);
        }
    });	
	

 if($("#errMessage").val()!='' && $("#errMessage").val()!=null)
{
    var error= [{
			errorCode: "",
			errorMessage: ""
		}];
        if($("#errMessage").val().includes("~"))
        {
        error[0].errorCode=$("#errMessage").val().split("~")[0];
        error[0].errorMessage=$("#errMessage").val().split("~")[1];
    }
    else
    {
     error[0].errorCode="WEB_PROCESS_EX";
     error[0].errorMessage=$("#errMessage").val();
                   
    }
     fnErrorResponseHandling(error);
    }  

$('#loginButton').click(function(event)
{   
   fn_show_spinner();
   $("#LoginForm").submit();
}
    
            );
    
$("#LoginForm").submit(function(event){
    
//fn_show_spinner();
 //setTimeout(,0);
//event.preventDefault();	
 
  }
 ); 
  
 /*$("#spinner").bind("ajaxStart", function() {
          fn_show_spinner();
    }).bind("ajaxStop", function() {
        fn_hide_spinner();
    }).bind("ajaxError", function() {
        fn_hide_spinner();
    });  */

});



function Login(event){
  /*  //if ($scope.userName == '' || $scope.userName == null) {
      if($("#wov").val() =='' || $("#wov").val() ==null) 
      {
				fn_Show_Exception_With_Param('FE-VAL-001', ['User Name']);
	event.preventDefault();			
        return false;
			}
			//if ($scope.loginPassword == '' || $scope.loginPassword == null) {
                        
         if($("#gls").val() =='' || $("#gls").val() ==null)    
         {
				fn_Show_Exception_With_Param('FE-VAL-001', ['PassWord']);
	            event.preventDefault();			
               return false;
			}*/
		//showAjaxLoaderMessage();	
			

return true;
}
/*function fnLoginpostBackendCall(response)
{
    window.location.href="/Cohesive/CohesiveMainPage.jsp";
   // if (response.header.status == 'success') {
	return true;
     //   }
}*/

