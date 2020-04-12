/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function()
{
   //fn_hide_parentspinner();   
//parent.$('#spinnerModal').modal('hide');
//parent.$('#spinnerModal').modal('toggle');

//window.parent.spinner="OFF";
window.parent.fn_hide_parentspinner();

    var error= [{
			ErrorCode: "",
			ErrorMessage: ""
		}];
   
   if($("#errMessage").val().includes("~"))
        {
        error[0].ErrorCode=$("#errMessage").val().split("~")[0];
        error[0].ErrorMessage=$("#errMessage").val().split("~")[1];
    } 
   if(error[0].ErrorCode=="BS_VAL_100")
     window.top.location.href="/jsp/Login.jsp";
   else if(error[0].ErrorCode=="BS_VAL_101")
     window.top.location.href="/jsp/Login.jsp";
   else
     fn_show_backend_exception(error);   
});

