/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
$('#forgotPwd').click(function () {
		//$("#frame").attr("src", "");
		$("#ForgotPwdModal").modal({
			backdrop: true,
			keyboard: true,
			focus: true,
			show: true
		});
		$('#ForgotPwdModal').modal('handleUpdate');
		$('#ForgotPwdModal').on('shown.bs.modal', function () {
			$('#ForgotPwdModal').trigger('focus');
                 
		});

		$('#ForgotPwdModal').on('hidden.bs.modal', function (e) {
			//$('#MenuModel a').modal('hide');
			$('#ForgotPwdModal').modal('dispose');
			//$("#snackbar").empty();
                      var $scope = angular.element(document.getElementById('MainCtrl')).scope();
                   if(MenuName=="cohesiveMainPage")
                   { 
                    $scope.step3Show = true;
                    $scope.step4Show = false;
                   }
                   else
                   {
                    $scope.step1Show = true;  
                    $scope.step2Show = false;
                    $scope.step3Show = false;
                    $scope.step4Show = false;
                   }   
    $scope.mobile="";
    $scope.errMessage="";
    $scope.newConfirmPwd="";
    $scope.newPwd="";
    $scope.OTP="";
    $scope.errorShow=false;
    $scope.$apply();  

		});
                
	});

    
    
	
$("#step1Button").click(function()
{
    
    var $scope = angular.element(document.getElementById('MainCtrl')).scope();
    if($scope.mobile ==null ||$scope.mobile =='')
    {
        $scope.errorShow=true;
        $scope.errMessage= "Enter Mobile Number and then press continue"
        $scope.step1Show = true;
    $scope.step2Show = false;
    $scope.step3Show = false;
   // $scope.errorShow=false;
    $scope.step4Show = false;
    $scope.$apply();
    }
    else
    {
     parentOperation="ValidateMobile";
     fnHitBackend();   
    }    
     
    
});	

$("#step2Button").click(function()
{
    
   var $scope = angular.element(document.getElementById('MainCtrl')).scope();
    if($scope.OTP ==null ||$scope.OTP =='')
    {
        $scope.errorShow=true;
        $scope.errMessage= "Enter OTP received then press continue"
        $scope.step2Show = true;
    $scope.step1Show = false;
    $scope.step3Show = false;
   // $scope.errorShow=false;
   $scope.OTP="";
    $scope.step4Show = false;
    $scope.$apply();
    }
    else
    {
     parentOperation="ValidateOTP";
     fnHitBackend();   
    }   
});	


$("#resend").click(function()
{
    
   var $scope = angular.element(document.getElementById('MainCtrl')).scope();
//    if($scope.OTP ==null ||$scope.OTP =='')
//    {
        $scope.errorShow=true;
        $scope.errMessage= "Enter OTP received then press continue"
        $scope.step2Show = true;
    $scope.step1Show = false;
    $scope.step3Show = false;
   // $scope.errorShow=false;
   $scope.OTP="";
    $scope.step4Show = false;
    $scope.$apply();
//    }
//    else
//    {
     parentOperation="ValidateMobile";
     fnHitBackend();   
//    }   
});	



$("#step3Button").click(function()
{
    var $scope = angular.element(document.getElementById('MainCtrl')).scope();
    if($scope.newConfirmPwd ==null ||$scope.newConfirmPwd ==''||$scope.newPwd==''||$scope.newPwd==null)
    {
        $scope.errorShow=true;
        $scope.errMessage= "Enter New Password and Reenter new password again then press continue"
        if(MenuName=="cohesiveMainPage")
                   { 
                    $scope.step3Show = true;
                    $scope.step4Show = false;
                   }
                   else
                   {
        $scope.step3Show = true;
        $scope.step1Show = false;
        $scope.step2Show = false;
   // $scope.errorShow=false;
    $scope.step4Show = false;
                   }
    $scope.$apply();
    }else if($scope.newConfirmPwd!=$scope.newPwd){
        
         $scope.errorShow=true;
        $scope.errMessage= "Incorrect New Passwords,Please type Same password in both boxes"
        if(MenuName=="cohesiveMainPage")
                   { 
                    $scope.step3Show = true;
                    $scope.step4Show = false;
                   }
                   else
                   {
        $scope.step3Show = true;
        $scope.step1Show = false;
        $scope.step2Show = false;
   // $scope.errorShow=false;
    $scope.step4Show = false;
                   }
    $scope.$apply();
        
    }
    else
    {
        
     if(MenuName=="cohesiveMainPage")
                   {    
        
               parentOperation="ChangePwdFromMainPage";
     
        }else{
            parentOperation="ChangePwd";
        }
     fnHitBackend();   
    }
    
});

$("#step4Button").click(function()
{
    var $scope = angular.element(document.getElementById('MainCtrl')).scope();
    if(MenuName=="cohesiveMainPage")
                   { 
                    $scope.step3Show = false;
                    $scope.step4Show = true;
                   }
                   else
                   {
    
    
    $scope.step1Show = false;
    $scope.step2Show = false;
    $scope.step3Show = false;
    $scope.step4Show = true;
                   }
    $scope.errorShow=false;
    $scope.$apply();
});
});

function fnHitBackend()
{
    var emptyChangePwd = {
		mobile: '',
                newPwd:'',
                OTP:'',
                token:''
		/*audit: {},
		error: {}*/
	};
	var dataModel = emptyChangePwd;
        var $scope = angular.element(document.getElementById('MainCtrl')).scope();
        if($scope.mobile!=null || $scope.mobile!="")
	 dataModel.mobile = $scope.mobile;
        if($scope.OTP!=null || $scope.OTP!="")
            dataModel.OTP=$scope.OTP;
        if($scope.newPwd!=null || $scope.newPwd!="")
            dataModel.newPwd=$scope.newPwd;
         if($scope.token!=null || $scope.token!="")
            dataModel.token=$scope.token;
        
     var response = fncallBackend('ChangePwd', parentOperation, dataModel,[{entityName:"mobile",entityValue:$scope.mobile}],$scope);
	
    return true;
    
    }

function fnChangePwdpostBackendCall(response)
{

    var $scope = angular.element(document.getElementById('MainCtrl')).scope();
     if (response.header.status == 'success') {
      
        switch(parentOperation)
        {
         
            case "ValidateMobile":
                $scope.errorShow=false;
                $scope.errMessage= "";
                $scope.step1Show = false;
                $scope.step2Show = true;
                $scope.step3Show = false;
                $scope.step4Show = false;
                $scope.token=response.token;
              break;   
              
            case "ValidateOTP":
                $scope.errorShow=false;
                $scope.errMessage= "";
                $scope.step1Show = false;
                $scope.step3Show = true;
                $scope.step2Show = false;
                $scope.step4Show = false;
              break;
              
             case "ChangePwd":
                $scope.errorShow=false;
                $scope.errMessage= "";
                if(MenuName=="cohesiveMainPage")
                   { 
                    $scope.step3Show = false;
                    $scope.step4Show = true;
                   }
                   else
                   {
                
                
                $scope.step1Show = false;
                $scope.step3Show = false;
                $scope.step2Show = false;
                $scope.step4Show = true;
            }
              break;  
              
              
              case "ChangePwdFromMainPage":
                $scope.errorShow=false;
                $scope.errMessage= "";
           
                $scope.step1Show = false;
                $scope.step3Show = false;
                $scope.step2Show = false;
                $scope.step4Show = true;
              break;
              
        
       }
     }
     else{
                $scope.errorShow=true;
                if(response.error[0].errorCode=="WEB_PROCESS_EX")
                {
                    $scope.errMessage="There is Server Error,Please contact System Admin";
                }
                else
                $scope.errMessage=response.error[0].errorMessage ;
                
          switch(parentOperation)
        {
           case "ValidateMobile":
                $scope.step1Show = true;
                $scope.step2Show = false;
                $scope.step3Show = false;
                $scope.step4Show = false;
              break;   
            case "ValidateOTP":  
                $scope.step1Show = false;
                $scope.step3Show = false;
                $scope.step2Show = true;
                $scope.step4Show = false;
              break;
              
             case "ChangePwd":  
                 if(MenuName=="cohesiveMainPage")
                   { 
                    $scope.step3Show = true;
                    $scope.step4Show = false;
                   }
                   else
                   {
                 
                $scope.step1Show = false;
                $scope.step3Show = true;
                $scope.step2Show = false;
                $scope.step4Show = false;
            }
              break;  
        
            case "ChangePwdFromMainPage":  
           
                 
                $scope.step1Show = false;
                $scope.step3Show = true;
                $scope.step2Show = false;
                $scope.step4Show = false;
              break;  
       }
          
          }
   
}