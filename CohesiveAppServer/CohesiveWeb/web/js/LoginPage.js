var app = angular.module('Main', ['BackEnd']);
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
/*$scope.Login = function(){
    if ($scope.userName == '' || $scope.userName == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['User Name']);
				return false;
			}
			if ($scope.loginPassword == '' || $scope.loginPassword == null) {
				fn_Show_Exception_With_Param('FE-VAL-001', ['PassWord']);
				return false
			}
			
   /* var emptyLogin = {
		wov: null,
		gls: null,
		error: {}
	};
        
    
    var dataModel = emptyLogin;
    dataModel.wov = $scope.userName;
    dataModel.gls=$scope.loginPassword;
   
     fncallBackend('LoginServlet', 'Submit', dataModel, [$scope.UserID], $scope);

    return true;
                        
    }*/
   });

$(document).ready(function(){

fn_hide_spinner();

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
    
$("#LoginForm").submit(function(){

fn_show_spinner();  
  }
          );   

});



function Login(event){
    //if ($scope.userName == '' || $scope.userName == null) {
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
			}

return true;
}
/*function fnLoginpostBackendCall(response)
{
    window.location.href="/Cohesive/CohesiveMainPage.jsp";
   // if (response.header.status == 'success') {
	return true;
     //   }
}*/
