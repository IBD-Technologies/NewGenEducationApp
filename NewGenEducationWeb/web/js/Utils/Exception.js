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
	var i;
        var errMessage=error[0].errorMessage;
	/*for(i=0;i<error.length;i++)
	{
 	          //if (error.length>1)
		  // bodyHtml += (i+1) +".";
		  // bodyHtml += error[i].errorCode + " " +error[i].errorMessage;
		   //bodyHtml +=   "</p>";
                     errMessage = error[i].errorMessage;
               break;  
	}*/	   
	
    	swal({
        title: "Error",
        text: errMessage,
        type: "error",
        showCancelButton: false,
        confirmButtonColor: "#fb483a",
        confirmButtonText: "Ok",
        closeOnConfirm: true
    });

  
}	
function fn_show_backend_Information(error,type)
{
	
	var title,message,type,okbuttonColor;
	
	if(type =='S')
        {    
            title ='Success!';
            type ='success';
            okbuttonColor='#2b982b';
        }
    else if(type=='I')
    {
	   title ='Information!' 
           type ='info'
           okbuttonColor='#00b0e4';
       }  
	var i;
        
	/*for(i=0;i<error.length;i++)
	{
 	     //  bodyHtml +=   "<p>";
		   if (error.length>1)
		//   bodyHtml += (i+1) +".";
		   bodyHtml += error[i].errorMessage;
		   bodyHtml +=   "</p>";
	}*/	   
	message=error[0].errorMessage; 

        	swal({
        title: title,
        text: message,
        type: type,
        showCancelButton: false,
        confirmButtonColor: okbuttonColor,
        confirmButtonText: "Ok",
        closeOnConfirm: true
    });

    
}

function fn_show_InformationNew(error)
{
	var message;
	message=error[0].errorMessage;
		
	//var i;
	/*for(i=0;i<error.length;i++)
	{
            
// 	       bodyHtml +=   "<p>";
//		   if (error.length>1)
//		   bodyHtml += (i+1) +".";
//		   bodyHtml += error[i].errorMessage;
		   bodyHtml +=   "</p>";
	}*/	   
	
        	swal({
        title: 'Information',
        text: message,
        type: 'info',
        showCancelButton: false,
        confirmButtonColor: '#00b0e4',
        confirmButtonText: "Ok",
        closeOnConfirm: true
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








