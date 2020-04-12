/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var spinner ="OFF";
function fn_show_spinner()
{
	var innerHtml,bodyHtml;
	innerHtml="<div id =\"spinnerModal\" class=\"modal fade bd-example-modal-sm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">";
	innerHtml+="<div class=\"modal-dialog model-dialog-centered modal-sm\">";
	innerHtml+="<div class=\"modal-content\">";
	innerHtml+="<div class=\"modal-header\">";
    innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:Green;text-align:center\">Request is in Progress!</strong></h5>";
    /*innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">";
    innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
    innerHtml+="</button>";*/
    innerHtml+="</div>";
    innerHtml+="<div class=\"modal-body\">";
        bodyHtml="<div class=\"d-flex justify-content-center\">";
        bodyHtml+="<div class=\"spinner-border text-primary\" role=\"status\">";
        bodyHtml+="<span class=\"sr-only\">Loading...</span>";
        bodyHtml+="</div>";
        bodyHtml+="</div>";
	
	innerHtml+=bodyHtml;
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	$("#spinner").append(innerHtml);
	
        $("#spinnerModal").modal({backdrop:'static',keyboard:false,focus:true,show:true});
        $('#spinnerModal').modal('handleUpdate'); 
    //spinner ="ON";
    $('#spinnerModal').on('shown.bs.modal', function () {
    $('#spinnerModal').trigger('focus');
    
     });
	
$('#spinnerModal').on('hidden.bs.modal', function (e) {
  $('#spinnerModal').modal('dispose');
  $("#spinner").empty();
	
  
});	

	
}	

function fn_hide_spinner()
{
$('#spinnerModal').modal('hide');
//$('#spinnerModal', window.parent.document).modal('hide');
spinner="OFF";
//$("*").bind("click"); 	
//$("[onclick]").attr("onclick");
 //$("*").on( 'click' ); 
}	

function fn_hide_parentspinner()
{
//window.parent.$('#spinnerModal').modal('hide');
//$('#spinnerModal', window.parent.document).modal('hide');

//$('#spinnerModal',parent.document).modal('dispose');
$("#spinner",parent.document).empty();
$(".modal-backdrop",parent.document).remove();


//var $body = $(window.frameElement).parents('body');
 //$body.find('#spinnerModal').modal('hide');


//$("*",parent.document).on( 'click' );
window.parent.spinner="OFF";
//$("*",parent.document).bind("click"); 	
//$("[onclick]",parent.document).attr("onclick");
}	

$("*",parent.document).click(function( event ) {
  
  if(window.parent.spinner =="ON")
    event.preventDefault();
  
});

$("*").click(function( event ) {
  
  if(spinner =="ON")
    event.preventDefault();
  
});
function fn_hide_Chpwdspinner()
{
//$("#spinnerFpwd").empty();
//$(".modal-backdrop").remove();
 var $scope = angular.element(document.getElementById('MainCtrl')).scope();
     $scope.spinnerShow=false;
     //$scope.$apply();   
}

function fn_show_Chpwdspinner()
{
    
     var $scope = angular.element(document.getElementById('MainCtrl')).scope();
     $scope.spinnerShow=true;
    $scope.$apply();
    /*
	var innerHtml,bodyHtml;
	innerHtml="<div id =\"spinnerModal\" class=\"modal fade bd-example-modal-sm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">";
	innerHtml+="<div class=\"modal-dialog model-dialog-centered modal-sm\">";
	innerHtml+="<div class=\"modal-content\">";
	innerHtml+="<div class=\"modal-header\">";
    innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:Green;text-align:center\">Request is in Progress!</strong></h5>";
    /*innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">";
    innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
    innerHtml+="</button>";*/
   /*
    innerHtml+="</div>";
    innerHtml+="<div class=\"modal-body\">";
        bodyHtml="<div class=\"d-flex justify-content-center\">";
        bodyHtml+="<div class=\"spinner-border text-primary\" role=\"status\">";
        bodyHtml+="<span class=\"sr-only\">Loading...</span>";
        bodyHtml+="</div>";
        bodyHtml+="</div>";
	
	innerHtml+=bodyHtml;
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	$("#spinnerFpwd").append(innerHtml);
	
        $("#spinnerModal").modal({backdrop:'static',keyboard:false,focus:true,show:true});
        $('#spinnerModal').modal('handleUpdate'); 
    //spinner ="ON";
    $('#spinnerModal').on('shown.bs.modal', function () {
    $('#spinnerModal').trigger('focus');
      var $scope = angular.element(document.getElementById('MainCtrl')).scope();
                   if(MenuName=="cohesiveMainPage")
                   { 
                    $scope.step3Show = false;
                    $scope.step4Show = false;
                   }
                   else
                   {
                    $scope.step1Show = false;  
                    $scope.step2Show = false;
                    $scope.step3Show = false;
                    $scope.step4Show = false;
                   }   
    $scope.$apply();      

     });
	
$('#spinnerModal').on('hidden.bs.modal', function (e) {
  $('#spinnerModal').modal('dispose');
  $("#spinnerFpwd").empty();
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
    
    $scope.$apply(); 
	
  
});*/	

	
}