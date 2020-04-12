/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var confirmation=false;
var dialogRemarks;
 
$(document).ready(function(){
    
fn_Show_Excep = function(id,err_code,position)
{
   var idSelector = "#".concat(id); 
$(idSelector).popover  ();
//$(idSelector).attr("data-trigger", "focus");

$(idSelector).attr("data-content", fn_get_error_message(err_code));
$(idSelector).attr("data-placement",position);
//$(idSelector).attr("placement",position);

//$(idSelector).popover('toggleEnabled');
//$(idSelector).popover('toggle');
$(idSelector).popover('show');
$(idSelector).popover('enable');

};
 fn_hide_Exception = function(id)
{ 
    var idSelector = "#".concat(id);
    $(idSelector).popover('hide');
  //  $(idSelector).popover('disable');
    $("[id^='popover']").remove();

$(idSelector).removeAttr("data-trigger");
$(idSelector).removeAttr("data-content");
$(idSelector).removeAttr("data-placement");
$(idSelector).removeAttr("data-original-title");
$(idSelector).removeAttr("aria-describedby");


    //$(idSelector).popover('hide');   
} ;

/*$(idSelector).keypress(function(){
   fn_hide_Exception();
});*/
});

function fn_get_error_message(err_code)
{
for(i=0;i<Exception.length;i++)
{
	if(Exception[i].error_code==err_code)
		 return Exception[i].error_message;
	
}
}
function fn_get_error_type(err_code)
{
for(i=0;i<Exception.length;i++)
{
	if(Exception[i].error_code==err_code)
		 return Exception[i].error_type;
	
}

}

function fn_Show_Exception(err_code)
{
	//var error={ErrorCode:null,ErrorMessage:null};
	var errArray=[{errorCode:null,errorMessage:null}];
	errArray[0].errorCode =err_code;
	errArray[0].errorMessage=fn_get_error_message(err_code);
	fn_show_backend_exception(errArray);

}

function fn_Show_Exception_With_Param(err_code,errParam)
{
	//var error={ErrorCode:null,ErrorMessage:null};
	var errArray=[{errorCode:null,errorMessage:null}];
	errArray[0].errorCode =err_code;
	errArray[0].errorMessage=fn_get_error_message(err_code);
	errArray[0].ErrorType=fn_get_error_type(err_code);
	
	for (i=0;i<errParam.length;i++)
	{	
	 errArray[0].errorMessage= errArray[0].errorMessage.replace('$'+(i+1),errParam[i]);
	}
	if(errArray[0].ErrorType =='E')
	{	
	fn_show_backend_exception(errArray);
	return false;
	}
	else if(errArray[0].ErrorType =='I')
	{	
	 fn_show_InformationNew(errArray);
	  return true;
	}
    else if(errArray[0].ErrorType =='C')
	{	
	 fn_show_ConfirmationNew(errArray);
	 if(confirmation)
		return true;
	 else
	   return false;
	}
	

}

function fn_Show_Information(err_code,type)
{
	//var error={ErrorCode:null,ErrorMessage:null};
	var errArray=[{errorCode:null,errorMessage:null}];
	errArray[0].errorCode =err_code;
	errArray[0].errorMessage=fn_get_error_message(err_code);
	fn_show_backend_Information(errArray,type);
	return true;

}
function fn_show_confirmation(err_code,$scope,operation)
{
	//var error={ErrorCode:null,ErrorMessage:null};
	var errArray=[{errorCode:null,errorMessage:null}];
	errArray[0].errorCode =err_code;
	errArray[0].errorMessage=fn_get_error_message(err_code);
	return fn_show_backend_confirmation(errArray,$scope,operation);
	
}



function fn_Show_Information_With_Param(err_code,errParam,type)
{
	//var error={ErrorCode:null,ErrorMessage:null};
	var errArray=[{errorCode:null,errorMessage:null}];
	errArray[0].errorCode =err_code;
	errArray[0].errorMessage=fn_get_error_message(err_code);
	for (i=0;i<errParam.length;i++)
	{	
	 errArray[0].errorMessage= errArray[0].errorMessage.replace('$'+(i+1),errParam[i]);
	}
	fn_show_backend_Information(errArray,type);

}


function fn_show_backend_exception(error)
{
	//$("#snackbar").text(err_code + " " + error_msg);
	
	
	//setTimeout(function(){
		//$("#snackbar").text();
		  //                $("#snackbar").removeClass("show")} , 3000);
						  
	//$("#snackbar").addClass("show");					  	
	
	var innerHtml,bodyHtml;
	//$("#snackbar").removeClass("show");
	//$("#snackbar").empty("#snackbar");
/*	innerHtml ="<div id=\"errorAlert\" class=\"alert alert-danger fade show\" role=\"alert\">";
	
	innerHtml+="<div id=\"errButton\">";
	//innerHtml+="<strong>Error Message</Strong> Please check!"; 
    innerHtml+="<h6 class=\"alert-heading\" style=\"text-align:center\"><strong>Error Please Check!</strong>";	
	innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">";
	//innerHtml+="Close</button>";
	innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
	innerHtml+="</button>";
	innerHtml+="</h6>";
	innerHtml +="</div>";*/
	
	
    //innerHtml+="<button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\".bd-example-modal-sm\">Error Please Check!</button>";
	
	innerHtml="<div id =\"modal\" class=\"modal fade bd-example-modal-sm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">";
	innerHtml+="<div class=\"modal-dialog model-dialog-centered modal-sm\">";
	innerHtml+="<div class=\"modal-content\">";
	innerHtml+="<div class=\"modal-header\">";
    innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:red;text-align:center\">Error!</strong></h5>";
    innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">";
    innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
    innerHtml+="</button>";
	innerHtml+="</div>";
    innerHtml+="<div class=\"modal-body\">";
	   
      
	 
	 
	 
	bodyHtml ="<div>";
	
	/*innerHtml += "<table id=\"snackbarTable\" class=\"mb-0 table-striped table-bordered\">";
	innerHtml += "<thead>";
	innerHtml += "<tr>";
	innerHtml += "<th scope=\"col\">#</th>";
	//innerHtml += "<th scope=\"col\">#</th>";
	innerHtml += "<th scope=\"col\">Message</th>";
	innerHtml += "</tr>";
	innerHtml += "</thead>";
	
	
	innerHtml += "<tbody>";
	var i;
	for(i=0;i<error.length;i++)
	{
 	       innerHtml +=   "<tr>";
		   innerHtml += "<th scope=\"row\">" + (i+1) + "</td>" ;
	      /*innerHtml += "<td>" + error[i].ErrorCode + "</td>" ;*/
		 /* innerHtml += "<td>" + error[i].ErrorMessage + "</td>" ;
	innerHtml +=   "</tr>";
	}   
	    				
		innerHtml += "</tbody>";
		innerHtml += "</table>";*/
		
	var i;
	for(i=0;i<error.length;i++)
	{
 	       bodyHtml +=   "<p>";
		   if (error.length>1)
		   bodyHtml += (i+1) +".";
		   bodyHtml += error[i].errorCode + " " +error[i].errorMessage;
		   bodyHtml +=   "</p>";
	}	   
		   
	      /*innerHtml += "<td>" + error[i].ErrorCode + "</td>" ;*/
		 /* innerHtml += "<td>" + error[i].ErrorMessage + "</td>" ;
	innerHtml +=   "</tr>";
	}   
		
		
		
		
	/*
	innerHtml +="</div>";
	
	innerHtml+="<div id=\"errButton\">";
	    
	innerHtml+="<button type=\"button\" class=\"btn-danger btn-block\" data-dismiss=\"alert\" aria-label=\"Close\">";
	innerHtml+="Close</button>";
	//innerHtml+="<span aria-hidden=\"true\">&times;</span>";
	
	innerHtml+="</div>"; */
	
	bodyHtml+="</div>";
	
	innerHtml+=bodyHtml;
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	/*$("#errButton").css("padding","16px");
	$("#errButton").css("marginBottom","0");*/
	
	//$('#SubScreenCtrl').find('input, textarea, button, select,a,table').attr('disabled','disabled');
	//$("#SubScreenCtrl").addClass("divoverlay");
	$("#snackbar").append(innerHtml);
	
//$("#errorAlert").alert();
	//$("#snackbarTable").css("background-color","red");
	//$("#snackbarTable").css("marginBottom","0");
	//$("#snackbarTable").css("Z-index","0");
	
	//$("#snackbarTable>thead>th").css("border-color","white");
	//$("#snackbarTable>th").css("border-color","white");
	//$("#snackbarTable,thead.").css("border-color","white")
	//$("#snackbar").addClass("show");
	
/*$("#snackbarTable.table-bordered").css("border", "2px solid #dee2e6");
	$("#snackbarTable.table-bordered td").css("border", "2px solid #dee2e6");
	$("#snackbarTable.table-bordered th").css("border", "2px solid #dee2e6");*/
	
	
	
   // $("#snackbarTable.table-bordered").css("border", "1px solid");
	//$("#snackbarTable.table-bordered td").css("border", "1px solid");
	//$("#snackbarTable.table-bordered th").css("border", "1px solid");
	if($('#model1').lenghth>0)
	{
	 //$("#snackbar").append(innerHtml);	
	 $('#model1').modal('hide');
	}
	else 
	{
     $("#modal").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
     	$('#modal').modal('handleUpdate'); 	
	  }
	//$("#modal").modal('show');
	
	/*$('#modal').on('show.bs.modal', function (event) {
     
	 var modal=$(this);
	 modal.find('modal-body').empty();
	 modal.find('modal-body').append(bodyHtml);
	 });*/
	

	
	
	$('#modal').on('shown.bs.modal', function () {
    $('#modal').trigger('focus');
     });
	
$('#modal').on('hidden.bs.modal', function (e) {
  $('#modal').modal('dispose');
  $("#snackbar").empty();
	
  
});	
	//$("#snackbarTable.table-bordered").css("color", "white");
	//$("#snackbarTable.table-bordered td").css("color", "white");
	
	//$("#errorAlert").alert();	
//	$("#snackbar").focus();
	/*setTimeout(function(){
	$("#snackbar").text();
    $("#snackbar").removeClass("show");
	$("#snackbar").empty("#snackbar");
	} , 3000);*/
	}
function fn_show_backend_Information(error,type)
{
	
	var innerHtml,bodyHtml;
	
	innerHtml="<div id =\"modal\" class=\"modal fade bd-example-modal-sm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">";
	innerHtml+="<div class=\"modal-dialog model-dialog-centered modal-sm\">";
	innerHtml+="<div class=\"modal-content\">";
	innerHtml+="<div class=\"modal-header\">";
	if(type =='S')
    innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:green;text-align:center\">Success!</strong></h5>";
    else if(type=='I')
	 innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:Orange;text-align:center\">Information!</strong></h5>";
   
	innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">";
    innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
    innerHtml+="</button>";
	innerHtml+="</div>";
    innerHtml+="<div class=\"modal-body\">";
	bodyHtml ="<div>";
		
	var i;
	for(i=0;i<error.length;i++)
	{
 	       bodyHtml +=   "<p>";
		   if (error.length>1)
		   bodyHtml += (i+1) +".";
		   bodyHtml += error[i].errorMessage;
		   bodyHtml +=   "</p>";
	}	   
		   
	
	bodyHtml+="</div>";
	
	innerHtml+=bodyHtml;
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	$("#snackbar").append(innerHtml);
	
	if($('#model1').lenghth>0)
	{
	 //$("#snackbar").append(innerHtml);	
	 $('#model1').modal('hide');
	}
	else 
	{
     $("#modal").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
     	$('#modal').modal('handleUpdate'); 	
	  }
	
	
	
	$('#modal').on('shown.bs.modal', function () {
    $('#modal').trigger('focus');
     });
	
$('#modal').on('hidden.bs.modal', function (e) {
  $('#modal').modal('dispose');
  $("#snackbar").empty();
	
  
});

	
	}

function fn_show_InformationNew(error)
{
	
	var innerHtml,bodyHtml;
	
	innerHtml="<div id =\"modal\" class=\"modal fade bd-example-modal-sm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">";
	innerHtml+="<div class=\"modal-dialog model-dialog-centered modal-sm\">";
	innerHtml+="<div class=\"modal-content\">";
	innerHtml+="<div class=\"modal-header\">";
	//if(type =='S')
    //innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:green;text-align:center\">Success!</strong></h5>";
    //else if(type=='I')
	 innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:Orange;text-align:center\">Information!</strong></h5>";
   
	innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">";
    innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
    innerHtml+="</button>";
	innerHtml+="</div>";
    innerHtml+="<div class=\"modal-body\">";
	bodyHtml ="<div>";
		
	var i;
	for(i=0;i<error.length;i++)
	{
 	       bodyHtml +=   "<p>";
		   if (error.length>1)
		   bodyHtml += (i+1) +".";
		   bodyHtml += error[i].errorMessage;
		   bodyHtml +=   "</p>";
	}	   
		   
	
	bodyHtml+="</div>";
	
	innerHtml+=bodyHtml;
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	$("#snackbar").append(innerHtml);
	
	if($('#model1').lenghth>0)
	{
	 //$("#snackbar").append(innerHtml);	
	 $('#model1').modal('hide');
	}
	else 
	{
     $("#modal").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
     	$('#modal').modal('handleUpdate'); 	
	  }
	
	
	
	$('#modal').on('shown.bs.modal', function () {
    $('#modal').trigger('focus');
     });
	
$('#modal').on('hidden.bs.modal', function (e) {
  $('#modal').modal('dispose');
  $("#snackbar").empty();
	
  
});

	
	}


function fn_show_backend_confirmation(error,$scope,operation)
{
	confirmation =false;
	dialogRemarks=null;
	var innerHtml,bodyHtml;
	
	innerHtml="<div id =\"modal1\" class=\"modal fade bd-example-modal-sm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">";
	innerHtml+="<div class=\"modal-dialog model-dialog-centered modal-sm\">";
	innerHtml+="<div class=\"modal-content\" style=\"color:#228272\">";
	innerHtml+="<div class=\"modal-header\">";
	//if(type =='S')
    innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:blue;text-align:center\">Confirmation!</strong></h5>";
    //else if(type=='I')
	 //innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:Orange;text-align:center\">Information!</strong></h5>";
   
	innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">";
    innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
    innerHtml+="</button>";
	innerHtml+="</div>";
    innerHtml+="<div class=\"modal-body\">";
	bodyHtml ="<div>";
		
	var i;
	for(i=0;i<error.length;i++)
	{
 	       bodyHtml +=   "<p>";
		   if (error.length>1)
		   bodyHtml += (i+1) +".";
		   bodyHtml += error[i].errorMessage;
		   bodyHtml +=   "</p>";
	}
   if(operation=='Delete' || operation=='Auth' || operation=='Reject')
   {
   // bodyHtml+="<label for=\"remarks\" class=\"col-3 col-form-label\">Remarks</label>";	   
	bodyHtml+="<input id=\"dialogMakerRemarks\" type=\"text\" placeholder=\"Enter Remarks\" class=\"form-control\">";  
   }
	bodyHtml+="</div>";
	
	innerHtml+=bodyHtml;
	innerHtml+="</div>";
	innerHtml+="<div class=\"modal-footer\">";
    innerHtml+="<button id=\"confirmYes\" type=\"button\" class=\"btn btn-info\" data-dismiss=\"modal\">Yes</button>";
    innerHtml+="<button id=\"confirmNo\" type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">No</button>";
    innerHtml+="</div>";
	
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	$("#snackbar").append(innerHtml);
	$("#modal1").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
	$('#modal1').modal('handleUpdate');
	
	
	$('#modal1').on('shown.bs.modal', function () {
    $('#modal1').trigger('focus');
     });
	
$('#modal1').on('hidden.bs.modal', function (e) {
  
  $('#modal1').modal('dispose');
  $("#modal1").remove();
	
  if($('#modal').length > 0)
  { 
	 $("#modal").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
     $('#modal').modal('handleUpdate'); 	
  } 
  //if (operation =='Delete')
	 //dialogRemarks =$('#dialogMakerRemarks').text;
  
});	

$('#confirmYes').click(function(){
 confirmation = true;
 // $location.path()='/PostTranOperation';
 // $anchorScroll();
if (operation =='Delete')
{
	dialogRemarks =$('#dialogMakerRemarks').val();
  	$scope.fnDeleteAfterConfirmation();
}
else if (operation =='Reject')
{	
dialogRemarks =$('#dialogMakerRemarks').val();
$scope.fnRejectAfterConfirmation();
}
else if (operation =='Auth')
{
dialogRemarks =$('#dialogMakerRemarks').val();
$scope.fnAuthAfterConfirmation();
}

});

$('#confirmNo').click(function(){confirmation = false;});


}

function fn_show_ConfirmationNew(error)
{
	confirmation =false;
	var innerHtml,bodyHtml;
	
	innerHtml="<div id =\"modal1\" class=\"modal fade bd-example-modal-sm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"mySmallModalLabel\" aria-hidden=\"true\">";
	innerHtml+="<div class=\"modal-dialog model-dialog-centered modal-sm\">";
	innerHtml+="<div class=\"modal-content\" style=\"color:#228272\">";
	innerHtml+="<div class=\"modal-header\">";
	//if(type =='S')
    innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:blue;text-align:center\">Confirmation!</strong></h5>";
    //else if(type=='I')
	 //innerHtml+="<h5 class=\"modal-title\" id=\"exampleModalLabel\" style=\"text-align:center\"><strong style=\"color:Orange;text-align:center\">Information!</strong></h5>";
   
	innerHtml+="<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">";
    innerHtml+="<span aria-hidden=\"true\"><strong>&times;</strong></span>";
    innerHtml+="</button>";
	innerHtml+="</div>";
    innerHtml+="<div class=\"modal-body\">";
	bodyHtml ="<div>";
		
	var i;
	for(i=0;i<error.length;i++)
	{
 	       bodyHtml +=   "<p>";
		   if (error.length>1)
		   bodyHtml += (i+1) +".";
		   bodyHtml += error[i].ErrorMessage;
		   bodyHtml +=   "</p>";
	}	   
		   
	
	bodyHtml+="</div>";
	
	innerHtml+=bodyHtml;
	innerHtml+="</div>";
	innerHtml+="<div class=\"modal-footer\">";
    innerHtml+="<button id=\"confirmYes\" type=\"button\" class=\"btn btn-info\" data-dismiss=\"modal\">Yes</button>";
    innerHtml+="<button id=\"confirmNo\" type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">No</button>";
    innerHtml+="</div>";
	
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	innerHtml+="</div>";
	
	$("#snackbar").append(innerHtml);
	$("#modal1").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
	$('#modal1').modal('handleUpdate');
	
	
	$('#modal1').on('shown.bs.modal', function () {
    $('#modal1').trigger('focus');
     });
	
$('#modal1').on('hidden.bs.modal', function (e) {
  
  $('#modal1').modal('dispose');
  $("#modal1").remove();
	
  if($('#modal').length > 0)
  { 
	 $("#modal").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
     $('#modal').modal('handleUpdate'); 	
  } 
  
});	

$('#confirmYes').click(function(){
 confirmation =true;
 });
 

$('#confirmNo').click(function(){
	confirmation= false;
 });

//$('#confirmNo').click(function(){confirmation = false;});


}








/*
$(document).ready(function(){
	
$('#modal1').on('shown.bs.modal', function () {
    $('#modal1').trigger('focus');
     });
		
	
$('#modal1').on('hidden.bs.modal', function (e) {
  
  $('#modal1').modal('dispose');
  $("#modal1").remove();
	
  if($('#modal').length > 0)
  { 
	 $("#modal").modal({backdrop:true,keyboard:true,focus:true,show:true}); 
     $('#modal').modal('handleUpdate'); 	
  } 
  
  
});	



});	*/
