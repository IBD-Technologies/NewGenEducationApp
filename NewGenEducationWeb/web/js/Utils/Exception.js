/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var confirmation=false;
var dialogRemarks;
 

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
	// fn_show_ConfirmationNew(errArray);
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
	
    	swal({
        title: "Error",
        text: errMessage,
        type: "error",
        showCancelButton: false,
        confirmButtonColor: "#fb483a",
        confirmButtonText: "Ok",
        closeOnConfirm: true
    }, function(){
           if(error != null && (typeof error != undefined))
           {
             if(error[0].errorCode == 'BS_VAL_101' || error[0].errorCode == 'BS_VAL_103'||error[0].errorCode == 'BS_VAL_026' ){
               sessionStorage.removeItem('Rst');
               parentStateChange({
                   nokotser:''
               });
               window.parent.launchMainScreen();
                  }
            else if(error[0].errorCode == 'BS_VAL_100'){
                sessionStorage.removeItem('GLOBAL');
                sessionStorage.removeItem('Rst');
                window.location.href = "/Login.min.jsp";
                }
            else{
             return
               }
            }
         
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
