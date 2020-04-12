/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app = angular.module('BackEnd', []);
var nokotser;
var enroll=false;

angular.module('BackEnd').service('appServerCallService', function($http,$q) {
   this.backendCall=function(appServerCallInput){
                                        //console.log(appServerCallInput.method);
					var deferred = $q.defer();
                                        
                                        var config = {headers:  {
                                                      //"X-uhtuliak" : window.parent.uhtuliak
                                                       }};
                                                       
  $http({method: appServerCallInput.method, url: appServerCallInput.url, data :appServerCallInput.request,cache: appServerCallInput.cache,headers:config.headers}).
        then(function(response) {
          appServerCallInput.status = response.status;
          appServerCallInput.response = response.data;
          deferred.resolve(response);
          }, function(response) {
            
            
             deferred.reject(response);
          
      });
       return deferred.promise;
   };
   });
 

var header={msgID:null,
            service:null,
            operation:null,
			instituteID:null,
			userID:null,
                        key:null,
                        token:null,
                        source:null,
            businessEntity:[],
            status:null
};		
var request={header:{},
body:{},
audit:{},
error:{}
}; 
function fnDirectFrameReqHeader(service,operation,businessEntity)
{

   header.msgID="";
   header.source="CohesiveFrontEnd";
   header.service=service;
   if (operation=="Edit")
   {
    header.operation="Modify";   
   }   
   else
   {
       
       if(enroll){
           
           header.operation="Modify"; 
           enroll=false;
       }else{
       
           header.operation=operation;
           
        }    
   }
   header.businessEntity=businessEntity;
   if(MenuName=='cohesiveMainPage'|| searchCallInputType=='InstituteChange'){
   header.instituteID=Institute.ID;
   //header.instituteID='System';
   header.userID=User.Id;
   header.token=$('#nokotser').val();
   }
   else
   {
   header.instituteID=window.parent.Institute.ID;
   header.userID=window.parent.User.Id;
   header.token=window.parent.nokotser;  
   }
       
   header.status="";
   header.key="";
   //header.key=window.parent.uhtuliak;
   
   
	return header;
	}

function fnDirectFrameRequest(service,operation,dataModel,businessEntity,audit)
{
	
    request.header= fnDirectFrameReqHeader(service,operation,businessEntity);
	request.body=dataModel;
	request.error=null;
        request.audit=audit;
return request;
}

function fnframeRequest(service,operation,dataModel,businessEntity,audit)
{
	
    request.header= fnFrameReqHeader(service,operation,businessEntity);
	request.body=dataModel;
	request.error=null;
        request.audit=audit;
return request;
}
	
function fnFrameReqHeader(service,operation,businessEntity)
{

   header.msgID="";
   header.source="CohesiveFrontEnd";
   header.service=service;
   if (operation=="Edit")
   {
    header.operation="Modify";   
   }   
   else
   {
       
       if(enroll){
           
           header.operation="Modify"; 
           enroll=false;
       }else{
       
           header.operation=operation;
   
       }
   }
   
   header.businessEntity=businessEntity;
   header.status="";
   header.key="";
   if(service!='ChangePwd')
   {   
   header.instituteID=window.parent.Institute.ID;
   header.userID=window.parent.User.Id;
  
   //header.key=window.parent.uhtuliak;
   
   header.token=window.parent.nokotser;
   }
	return header;
	}
	
function fn_print_appServerObjects(request)
{	

console.log("Header:");
console.log("msgid:" + request.header.msgId);
console.log("Service:" + request.header.service);
console.log("Operation:" + request.header.operation);
console.log("Header Business Entity:");
 for(i=0;i<request.header.businessEntity.lenghth;i++)
 {
      console.log("Business Entity Index:" + i);
	  console.log("Business Entity Value:" + businessEntity[i]);
	  
   }	 
   
   var keys =[];
   keys= Object.keys(request.body);

 for(i=0;i<keys.length;i++)
 {	 
    console.log("Body Datat model Key  Index" +i);  
    console.log("Body Datat model Key  Value" +keys[i]); 
   }


   var values =[];
   values= Object.values(request.body);

 for(i=0;i<values.length;i++)
 {	 
    console.log("Body Datat model values  Index" +i);  
    console.log("Body Datat model values  Value" +values[i]);


 }


}

function fncallBackend(service,operation,datamodel,businessEntity,$scope)
{
 if (service !="ChangePwd")
 window.parent.fn_show_spinner();
 else
  fn_show_Chpwdspinner();
    
if((service !="ChangePwd") &&(window.parent.uhtuliak=='undefined'||window.parent.uhtuliak ==null || window.parent.uhtuliak ==''))
       {
          window.location.href="./jsp/Login.jsp";      
       }

else
       {   
         if(MenuName=='cohesiveMainPage' && service =="ChangePwd")
         {
              var request = fnDirectFrameRequest(service,operation,datamodel,businessEntity,$scope.audit);
       
         }
         else
         {    
        var request = fnframeRequest(service,operation,datamodel,businessEntity,$scope.audit);
       }
       
       if(TEST=="YES"){
        
           var backendURL="https://cohesivetest.ibdtechnologies.com/CohesiveGateway/";
        
       }else{
           
           var backendURL="https://cohesive.ibdtechnologies.com/CohesiveGateway/";
       }
       
       
        if (service =="ChangePwd")
        var url=backendURL+"User"+"/"+service;
    
        else
        var url=backendURL+window.parent.Entity+"/"+service;
        
        var appServerCallInput={method:"put",url:"",request:{},cache:null,response:{}};
	//This is to reset snackbar
	//if (operation!='Delete' && operation!='Reject' && operation!='Auth' )
	//{
    /*if(service=='LoginServlet')
    {
        appServerCallInput.method="post";
    }*/
   if(operation!='Delete' && operation!='Reject' && operation!='Auth')
   {	   
	$("#snackbar").empty("#snackbar");
    $("#snackbar").removeClass("show");	
   }
	//}
    //This is to reset snackbar
        appServerCallInput.url= url;
	appServerCallInput.request = request;
	$scope.appServerCall(appServerCallInput).then(function(response)
            {
                if (service =="ChangePwd")
                {
                 fn_hide_Chpwdspinner();
                  appServerCallInput.status = response.status;
                  appServerCallInput.response = response.data;  
                  fnChangePwdpostBackendCall(appServerCallInput.response);
                  return true;
                 }
                
         window.parent.fn_hide_parentspinner();
                         appServerCallInput.status = response.status;
                         appServerCallInput.response = response.data;
                 if (appServerCallInput.response.header.status=="error")
                 {
                    for(i=0;i<appServerCallInput.response.error.length;i++)
	             {
 	               if(appServerCallInput.response.error[i].errorCode=='BS_VAL_101')
                       {
                         var error= [{
			errorCode: "",
			errorMessage: ""
		           }];
	                    error[0].errorCode=appServerCallInput.response.error[i].errorCode;
                            error[0].errorMessage=appServerCallInput.response.error[i].errorMessage;
                    if(MenuName=='cohesiveMainPage' ||searchCallInputType=='InstituteChange'){
                         window.parent.fnSettingswithError(error); 
                         window.parent.$("#frame").attr('src', '');
                    }else{
                        window.parent.fnMainPagewithError(error); 
                         window.parent.$("#frame").attr('src', '');
                      return;
                    }
                    } 
                      
                     }
                       if(operation=='Delete'||operation=='Auth'||operation=='Reject')
                       {   
                         var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
//                          $scope.audit=previousAuditScope;
                          $scope.audit= Object.assign({}, previousAuditScope);
                     }
                    fnErrorResponseHandling(appServerCallInput.response.error);
                    
                } 
                else
                 {
                     var fn = window["fn"+MenuName+"postBackendCall"];
                     if (typeof fn === "function") 
                     {  
                         fn(appServerCallInput.response);
                     } 
                      var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
                            if (operation!='Create-Default'&&operation!='Payment-Default')
							{
                             if(typeof parentOperation!=="undefined"&&operation=='View'){                               
                            if (parentOperation =="SummaryQuery")
                            {
                                $scope.OperationScopes.get('SummaryoperationCtl').fnPostView();
                            }            
			    else if (parentOperation =="Query")
                            {
                                $scope.OperationScopes.get('operationCtl').fnPostView();
                            }
                            else if (parentOperation =="Report")
                            {
                                $scope.OperationScopes.get('ReportOperationCtl').fnPostView();
                            } 
                            
                    }
//                    else if(operation=='View'){
//                        
//                        $scope.OperationScopes.get('operationCtl').fnPostView();
//                    }
                           else
                            {  
                                
                                if(parentOperation!='detailView'){
                                
                            $scope.OperationScopes.get('operationCtl').fnPostSave();
                                }
                           }
							}
                                                    
                }                
            }     
           
        ).catch(function(response){
            if(service!="ChangePwd")
            {    
            window.parent.fn_hide_parentspinner();
          }
          else
              fn_hide_Chpwdspinner();
          
                          appServerCallInput.response=    
                    {header: {
		msgID: "",
		service: "",
		operation: "",
		businessEntity: [""],
		status: "error"
	},
	body: {},
	error: [{
			ErrorCode: "",
			ErrorMessage: ""
		}]
	   
               
            };
            appServerCallInput.response.header.service=appServerCallInput.request.header.service;
            appServerCallInput.response.header.operation=appServerCallInput.request.header.operation;
           var start = response.data.indexOf("<body>") +6;
           var end = response.data.indexOf("</body>");
           var error=response.data.slice(start,end);
           
            if(appServerCallInput.request.header.service=='LoginServlet' && response.status=='403')
            { 
            
            appServerCallInput.response.error[0].errorCode=error.split("~")[0];
            appServerCallInput.response.error[0].errorMessage=error.split("~")[1];
            //appServerCallInput.response = response.data;
        }
            else
            {
            appServerCallInput.response.error[0].errorCode="WEB_PROCESS_EX";
            appServerCallInput.response.error[0].errorMessage=error;
                
            }      
              if(service!="ChangePwd")
            {       
                 fnErrorResponseHandling(appServerCallInput.response.error);
             }
             else
             {
                 fnChangePwdpostBackendCall(appServerCallInput.response);
             }
            });
               
    }
   /* if (appServerCallInput.response.header.status=="error")
             fnErrorResponseHandling(appServerCallInput.response.error);
    else    
    return appServerCallInput.response;  */  
 }



function fnresponseHandling(response)
{
	if (response.header.status=="error")
		fnErrorResponseHandling(response.error);
	else
		fnSucsRespHandling(response);
}

function fnErrorResponseHandling(error)
{
	fn_show_backend_exception(error);
}
	
function fnsetUhtuliak(val)
{
 uhtuliak=val;   
}

function fngetUhtuliak()
{
 return uhtuliak;   
}


//$("#detailsection").find("table").eachGTY(function()
//{
	//var TTV = document.getElementById($(this).attr("id"));
	//var TTV_Length = TTV.rows.length;
	//for (i = 0; i < TTV_Length; i++){

      //gets cells of current row  
      // var TTV_Cells = TTV.rows.item(i).cells;

       //gets amount of cells of current row
       //var TTV_cellLength = TTV_Cells.length;

       //loops through each cell in current row
       //for(var j = 0; j < TTV_cellLength; j++){

              // get your cell info here

         //     var cellVal = TTV_Cells.item(j).innerHTML;
           //   alert(cellVal);
	//   }
	//}
//}
//return TTV;// )
