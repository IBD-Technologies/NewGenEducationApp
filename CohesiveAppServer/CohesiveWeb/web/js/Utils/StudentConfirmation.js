/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var MenuName;
var parentOperation;
var app= angular.module('operation', ["ngRoute"]);
 app.config(function($routeProvider){
	 $routeProvider
	 .when("/PostQueryOperation",{template:'<div id="PostQueryOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center">\n'+
                         '<li class="page-item"><a id="View" class="page-link page_style" ng-click ="fnView($event)" href="#!PostViewOperation">View<span class="badge badge-link cohesive_badge"><i class="fas fa-eye"></i></span></a></li>\n'+
                         '<li class="page-item"><a id="BackQuery" ng-click="fnBack($event)" class="page-link page_style" href="#!defaultOperation">Cancel<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                         
				  '</ul>\n'+
	 '</div>',controller:'operationCtl'})
	 .when("/PostViewOperation",{template:'<div id="PostViewOperation">\n'+
       			  ' <ul class="pagination pagination-sm pagination_background d-flex justify-content-center ">\n'+
                        //'<li class="page-item"><a id="Edit" ng-click ="fnEdit($event)" class="page-link page_style" href="#!PostTranOperation">Edit<span class="badge badge-link cohesive_badge"><i class="fas fa-pen-square"></i></span></a></li>\n'+
                        '<li class="page-item"><a id="Authorize" ng-click ="fnAuthorise($event)" class="page-link page_style" href="#!defaultOperation">Authorize<span class="badge badge-link cohesive_badge"><i class="fas fa-check-circle"></i></span></a></li>\n'+
                        //'<li class="page-item"><a id="Reject" ng-click ="fnReject($event)" class="page-link page_style" href="#!defaultOperation">Reject<span class="badge badge-link cohesive_badge"><i class="fas fa-times-circle"></i></span></a></li>\n'+
                        //'<li class="page-item"><a id ="Delete" ng-click ="fnDelete($event)" class="page-link page_style" href="#!PostTranOperation">Delete<span class="badge badge-link cohesive_badge"><i class="fas fa-trash"></i></span></a></li>\n'+
                        '<li class="page-item"><a id="BackQuery" ng-click="fnBack($event)" class="page-link page_style" href="#!defaultOperation">Cancel<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                   '</ul>\n'+
	 '</div>',controller:'operationCtl'})
     .when("/PostTranOperation",{template:'<div id="PostTranOperation">\n'+
			     '<ul class="pagination pagination_background justify-content-center ">\n'+
                    '<li class="page-item"><a id="Save" ng-click ="fnSave($event)" class="page-link page_style" href="#!defaultOperation">Save<span class="badge badge-link cohesive_badge"><i class="fas fa-save"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackTran" ng-click="fnBack($event)" class="page-link page_style" href="#!PostViewOperation">Cancel<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                     
				'</ul>\n'+
	 '</div>',controller:'operationCtl'})
	 .when("/PostCreateOperation",{template:'<div id="PostCreateOperation">\n'+
			     '<ul class="pagination pagination_background justify-content-center ">\n'+
                    '<li class="page-item"><a id="Save" ng-click ="fnSave($event)" class="page-link page_style" href="#!defaultOperation">Save<span class="badge badge-link cohesive_badge"><i class="fas fa-save"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackTran" ng-click="fnBack($event)" class="page-link page_style" href="#!defaultOperation">Cancel<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                     
				'</ul>\n'+
	 '</div>',controller:'operationCtl'})
	 
	 .when("/defaultOperation",{template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                  '<li class="page-item"><a id="Query"  ng-click ="fnQuery($event)" class="page-link page_style" href="#!PostQueryOperation">Query<span class="badge badge-link cohesive_badge"><i class="fas fa-question-circle"></i></span></a></li>\n'+
                  //'<li class="page-item"><a id="Create" ng-click ="fnCreate($event)" class="page-link page_style" href="#!PostCreateOperation">Create <span class="badge badge-link cohesive_badge"><i class="fas fa-plus-circle"></i></span></a></li>\n'+
				  '<li class="page-item"><a id="close" ng-click ="fnClose($event)" class="page-link page_style" href="#">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+
                  '</ul>\n'+
	 '</div>',controller:'operationCtl'})
	 
	 .otherwise({template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                  '<li class="page-item"><a id="Query"  ng-click ="fnQuery($event)" class="page-link page_style" href="#!PostQueryOperation">Query<span class="badge badge-link cohesive_badge"><i class="fas fa-question-circle"></i></span></a></li>\n'+
                  //'<li class="page-item"><a id="Create" ng-click ="fnCreate($event)" class="page-link page_style" href="#!PostCreateOperation">Create <span class="badge badge-link cohesive_badge"><i class="fas fa-plus-circle"></i></span></a></li>\n'+
				  '<li class="page-item"><a id="Create" ng-click ="fnClose($event)" class="page-link page_style" href="#">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+
                  
                  '</ul>\n'+
	 '</div>',controller:'operationCtl'});
 });		
 
app.controller('operationCtl',function($scope, $location,$routeParams,$anchorScroll,OperationScopes)
{

$scope.$on('$routeChangeStart', function(event) {
    //$location.hash($routeParams.scrollTo);
    //$anchorScroll();  
	//console.log($location.path());
	//console.log(oldRoute);
	 //window.FontAwesomeConfig = { autoReplaceSvg: false };
         
	if (!($location.url()=='/PostTranOperation'||$location.url()=='/PostViewOperation'||$location.url()=='/PostQueryOperation'||$location.url()=='/defaultOperation'))
	event.preventDefault();
  });

$scope.fnClose=function($event)
{
//parentOperation=null;
var fn = window["fn"+MenuName+"Close"];
   if (typeof fn === "function") fn();
 
    window.parent.fnMainPage(); 
    window.parent.$("#frame").attr('src', '');
 	
}

$scope.fnBack=function($event)
{
//parentOperation=null;
var fn = window["fn"+MenuName+"Back"];
   if (typeof fn === "function") fn();
 
 if(parentOperation ==null || parentOperation =='undefined')
 {	 
    window.parent.$("#frame").attr('src', '');
 }
 else
 {	 
    // Select Box change starts
		//fnSelectBoxEventHandler(selectBoxes);
	//Select Box change ends
 }
	 
} 
$scope.fnQuery=function($event)
{
if (!fn_check_access_rights(MenuName,'Query'))
 {	 
	fn_Show_Exception('FE-VAL-006'); 
	$event.preventDefault(); //To Disallow the operation
 }
else
{	
   var fn = window["fn"+MenuName+"Query"];
   parentOperation="Query";
   if (typeof fn === "function") 
   {
	   fn();
   
    // Select Box change starts
		//fnSelectBoxEventHandler(selectBoxes);
	//Select Box change ends
   }
 
   }
   
   

}
$scope.fnView=function($event)
                    {
	             
OperationScopes.store('operationCtl', $scope);        
					  var fn = window["fn"+MenuName+"MandatoryCheck"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('View'))
	                     {
					      $event.preventDefault();
                           return;
						 }						   
                        }
		   var fn = window["fn"+MenuName+"DefaultandValidate"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('View'))
	                     {$event.preventDefault();
					     return;
						 }
						} 
                      
					  var fn = window["fn"+MenuName+"View"];
                      if (typeof fn === "function") 
                       {  
                         //if (!fn('View'))
                         fn('View');
						 $event.preventDefault();
                        
					      return;
						 
						   					       
						// Select Box change starts
							fnSelectBoxEventHandler(selectBoxes);
						//Select Box change ends
 
						} 
                      
            			 fn_Show_Information('FE-VAL-016','S');
					 
					 }  
  
$scope.fnCreate=function($event)
{
if (!fn_check_access_rights(MenuName,'Create'))
 {	 
	fn_Show_Exception('FE-VAL-006'); 
	$event.preventDefault(); //To Disallow the operation
 }
else
{	
   var fn = window["fn"+MenuName+"Create"];
   parentOperation='Create';
   
   //var $scope1 = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(!fnDefaultandValidateAudit('Create',$scope,window.parent.User.Id))
	{ 		
	  $event.preventDefault();
	  return;
	}
	//$scope1.$apply();
   if (typeof fn === "function")
   {	   
	   if(!fn())
		 $event.preventDefault();  
	 // Select Box change starts
		//fnSelectBoxEventHandler(selectBoxes);
	//Select Box change ends
   }
 }
}
$scope.fnEdit=function($event)
{
  if (!fn_check_access_rights(MenuName,'Edit'))
   {	 
	fn_Show_Exception('FE-VAL-006'); 
	$event.preventDefault(); //To Disallow the operation
   }
  else
  {	
   var fn = window["fn"+MenuName+"Edit"];
   parentOperation='Edit';
   //var $scope1 = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(!fnDefaultandValidateAudit('Edit',$scope,window.parent.User.Id))
	{
         $event.preventDefault();
      return;
	}
	  //$scope1.$apply();	
   
   if (typeof fn === "function")
   {	   
	   if(!fn())
        $event.preventDefault();  
     // Select Box change starts
             //fnSelectBoxEventHandler(selectBoxes);
	//Select Box change ends
 
	}
   }
}
$scope.fnDelete=function($event)
{
//if (!confirmation)
//{ 	
  if (!fn_check_access_rights(MenuName,'Delete'))
   {	 
	fn_Show_Exception('FE-VAL-006'); 
	$event.preventDefault(); //To Disallow the operation
	return;
   }
  else
  {
  
   fndeleteConfirmation($scope);
   if(!confirmation)
   {
     $event.preventDefault();
    return;
   }
  }
	 }

$scope.fnDeleteAfterConfirmation=function()
{
	 var fn = window["fn"+MenuName+"Delete"];
      parentOperation='Delete';
   if (typeof fn === "function") 
	   if(fn())  
	   {
        if(!fnDefaultandValidateAudit('Delete',$scope,window.parent.User.Id))
	     {
           // $event.preventDefault();
	        return false;
	      }
        
	 var fn = window["fn"+MenuName+"Save"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn())
						 {//$event.preventDefault();
					      return false;
						 }
                         else
                         {
                             return true;
                         }
						  // Select Box change starts
                     	//fnSelectBoxEventHandler(selectBoxes);
	                        //Select Box change ends
 
						} 
      $location.url('/defaultOperation');
		$anchorScroll();
       $scope.$apply();
		 fn_Show_Information('FE-VAL-014','S');
		 return true;
		}
 
 }


$scope.fnAuthorise=function($event)
{
if (!fn_check_access_rights(MenuName,'Auth'))
 {	 
	fn_Show_Exception('FE-VAL-006'); 
	$event.preventDefault(); //To Disallow the operation
 }
else
{	
fnauthconfirmation($scope);
   if(!confirmation)
   {
     $event.preventDefault();
    return;
   }
	
	
			   
}
}
  
 $scope.fnAuthAfterConfirmation=function()
{
	 var fn = window["fn"+MenuName+"Auth"];
      parentOperation='Auth';
   if (typeof fn === "function") 
	   if(fn())  
	   {
        if(!fnDefaultandValidateAudit('Auth',$scope,window.parent.User.Id))
	     {
           // $event.preventDefault();
	        return false;
	      }
        
	 var fn = window["fn"+MenuName+"Save"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn())
						 {//$event.preventDefault();
					      return false;
						 }
                         else
                         {
                             return true;
                         }
						  // Select Box change starts
                         		//fnSelectBoxEventHandler(selectBoxes);
	                      //Select Box change ends
 
						} 
            
	
		
		$location.url('/defaultOperation');
		$anchorScroll();
       $scope.$apply();
		fn_Show_Information('FE-VAL-015','S');
		 
		 return true;
		}
 
 }
 

$scope.fnReject=function($event)
{
//if (!confirmation)
//{ 	
  if (!fn_check_access_rights(MenuName,'Reject'))
   {	 
	fn_Show_Exception('FE-VAL-006'); 
	$event.preventDefault(); //To Disallow the operation
	return;
   }
  else
  {
  
   fnrejectconfirmation($scope);
   if(!confirmation)
   {
     $event.preventDefault();
    return;
   }
  }
	 }
 
 
 $scope.fnRejectAfterConfirmation=function()
{
	 var fn = window["fn"+MenuName+"Reject"];
      parentOperation='Reject';
   if (typeof fn === "function") 
	   if(fn())  
	   {
        if(!fnDefaultandValidateAudit('Reject',$scope,window.parent.User.Id))
	     {
            //$event.preventDefault();
	        return false;
	      }
        
	 var fn = window["fn"+MenuName+"Save"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn())
						 {//$event.preventDefault();
					      return false;
			  			 }
                         else
                         {
                               return true;                  
                           }			  // Select Box change starts
							//fnSelectBoxEventHandler(selectBoxes);
						 //Select Box change ends
 
						} 
            
	
		
		$location.url('/defaultOperation');
		$anchorScroll();
                $scope.$apply();
		 fn_Show_Information('FE-VAL-017','S');
		 
		 return true;
		}
 
 }
 

 $scope.fnPostdetailLoad=function($event)
 {
	$location.url('/PostViewOperation');
	$anchorScroll();
	$scope.$apply();
	return true; 
	 
 }

$scope.fnSave=function($event)
                    {
					  
OperationScopes.store('operationCtl', $scope); 	
	                  if (parentOperation =='Edit' || parentOperation =='Create')
					  {	  
					  var fn = window["fn"+MenuName+"MandatoryCheck"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Save'))
	                     {
					      $event.preventDefault();
                           return;
						 }						   
                        }
					  var fn = window["fn"+MenuName+"DefaultandValidate"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Save'))
	                     {$event.preventDefault();
					     return;
						 }
						} 
                      }
					  var fn = window["fn"+MenuName+"Save"];
                      if (typeof fn === "function") 
                       {  
                         //if (!fn('Save'))
			   fn('Save');
                                 $event.preventDefault();
					      return;
						 
						 // Select Box change starts
							//fnSelectBoxEventHandler(selectBoxes);
						  //Select Box change ends
						  
						} 
            
                   switch(parentOperation)
				   {
                       case 'Create':			   
            			 fn_Show_Information('FE-VAL-012','S');
			             break;
                         case 'Edit':			   
            			 fn_Show_Information('FE-VAL-013','S');
			             break;
                         case 'Delete':			   
            			 fn_Show_Information('FE-VAL-014','S');
			             break;
				         case 'Auth':			   
            			 fn_Show_Information('FE-VAL-015','S');
			             break;
				         case 'Reject':
                                       
            			 fn_Show_Information('FE-VAL-017','S');
			             break;
				    
				   
				   }
						 
					 }  
                                         
$scope.fnPostView=function()
{
$location.url('/PostViewOperation');
 $anchorScroll();
 // Select Box change starts
 //fnSelectBoxEventHandler(selectBoxes);
//Select Box change ends
    fn_Show_Information('FE-VAL-016','S');  
 
}
 
$scope.fnPostSave=function()
{
    // Select Box change starts
     //fnSelectBoxEventHandler(selectBoxes);
    //Select Box change ends
               switch(parentOperation)
				   {
                       case 'Create':
                           $location.url('/defaultOperation');
		            $anchorScroll();
                            //$scope.$apply();
            			 fn_Show_Information('FE-VAL-012','S');
			             break;
                         case 'Edit':
                             $location.url('/defaultOperation');
		            $anchorScroll();
                            //$scope.$apply();
            			 fn_Show_Information('FE-VAL-013','S');
			             break;
                         case 'Delete':	
                            $location.url('/defaultOperation');
		            $anchorScroll();
                            //$scope.$apply();
		            fn_Show_Information('FE-VAL-014','S');
            			     break;
		          case 'Auth':
                            $location.url('/defaultOperation');
		            $anchorScroll();
                            //$scope.$apply();     
            	            fn_Show_Information('FE-VAL-015','S');
			             break;
		          case 'Reject':	
                            $location.url('/defaultOperation');
                            $anchorScroll();
                            //$scope.$apply();
		            fn_Show_Information('FE-VAL-017','S');
			             break;
				    
				   
				   } 
    
}
                                         
                                         
                                         
  
  });



function fnDefaultandValidateAudit(operation,$scope1,userID)  
{  
  
   switch(operation)
   {
      case 'Query':
	     //return true;
		 break;
	  case 'Create':
	     //if ($scope1.audit ==null) 
	     //$scope1.audit = new Object();
	 
	     $scope1.audit.MakerID=userID;
		 $scope1.audit.AuthStat='Unauthorised';
		 $scope1.audit.MakerRemarks='';
		 $scope1.audit.CheckerRemarks='';
		 $scope1.audit.MakerDtStamp='';
		 $scope1.audit.CheckerDtStamp='';
		 $scope1.audit.CheckerID='';
		 $scope1.audit.Version="1";
		 $scope1.audit.RecordStat='Open';
		 //return true;
		 break;

      case 'Edit':
	     if($scope1.audit.RecordStat!='Open')
			{			
             fn_Show_Exception('FE-VAL-008');
			   return false;
		     }    
	  
	     if ($scope1.audit.AuthStat=='Unauthorised')
		 { 		 
		    if($scope1.audit.MakerID!=userID)
   			 {
               fn_Show_Exception('FE-VAL-007');
			   return false;
			 }
	 		else if($scope1.audit.RecordStat=='Open')
			   {
			     //$scope1.audit.Version=audit.Version;
		         //$scope1.audit.MakerID=audit.MakerID;
		         $scope1.audit.AuthStat='Unauthorised';
		         //$scope1.audit.MakerRemarks='';
		         $scope1.audit.CheckerRemarks='';
		         $scope1.audit.MakerDtStamp='';
		         $scope1.audit.CheckerDtStamp='';
		         $scope1.audit.CheckerID='';
		 
				 //audit.MakerRemarks=MakerRemarks;
		 		 $scope1.audit.RecordStat='Open';
			   return true;
			   }
            
		  }
		  else
		  {
                 $scope1.audit.Version=(parseInt($scope1.audit.Version)+1).toString();//Integration change
		         $scope1.audit.MakerID=userID;
		         $scope1.audit.AuthStat='Unauthorised';
				 $scope1.audit.MakerRemarks='';
		         $scope1.audit.CheckerRemarks='';
		 $scope1.audit.MakerDtStamp='';
		 $scope1.audit.CheckerDtStamp='';
		 $scope1.audit.CheckerID='';
		 
		         //audit.MakerRemarks=MakerRemarks;
		 		 $scope1.audit.RecordStat='Open';
				 return true;
			}	  

	    break;
		case 'Delete': 
         if ($scope1.audit.RecordStat=='Deleted')
		   { 		 
		     fn_Show_Exception('FE-VAL-008');
			   return false;
		   }  
		  
		 if ($scope1.audit.AuthStat=='Unauthorised')
		 { 		 
		    if($scope1.audit.MakerID!=userID)
   			 {
               fn_Show_Exception('FE-VAL-009');
			   return false;
			 }
	 		else 
			   {
			     //$scope1.audit.Version=audit.Version;
		         //$scope1.audit.MakerID=audit.MakerID;
		         $scope1.audit.AuthStat='Unauthorised';
				 //$scope1.audit.MakerRemarks='';
		 $scope1.audit.CheckerRemarks='';
		 //$scope1.audit.MakerDtStamp='';
		 $scope1.audit.CheckerDtStamp='';
		 $scope1.audit.CheckerID='';
		 
		         //audit.MakerRemarks=MakerRemarks;
		 		 $scope1.audit.RecordStat='Deleted';
				 $scope1.audit.MakerRemarks=dialogRemarks;
				 return true;
			   }
			 
		  }
          else
		  {
                 $scope1.audit.Version=(parseInt($scope1.audit.Version)+1).toString();
		         $scope1.audit.MakerID=userID;
		         $scope1.audit.AuthStat='Unauthorised';
		         //audit.MakerRemarks=MakerRemarks;
		 		 $scope1.audit.RecordStat='Deleted';
				 $scope1.audit.MakerRemarks='';
		 $scope1.audit.CheckerRemarks='';
		 $scope1.audit.MakerDtStamp='';
		 $scope1.audit.CheckerDtStamp='';
		 $scope1.audit.CheckerID='';
		 
				 return true;
			}	  
          
         break;
		 
        case 'Auth': 
        /* if (audit.RecordStat=='D')
		   { 		 
		     fn_Show_Exception('FE-VAL-008');
			   return false;
		   } */ 
		 
		 if ($scope1.audit.AuthStat!='Unauthorised')
		 {
            fn_Show_Exception('FE-VAL-011');
			   return false;
		   } 
		 if($scope1.audit.MakerID==userID)
   			 {
               fn_Show_Exception('FE-VAL-020');
			   return false;
			 }  
		   
         $scope1.audit.CheckerID=userID;
		 //$scope1.audit.MakerRemarks='';
		 //$scope1.audit.MakerRemarks=dialogRemarks;
		 $scope1.audit.CheckerRemarks=dialogRemarks;
		 //$scope1.audit.MakerDtStamp='';
		 $scope1.audit.CheckerDtStamp='';
		 //$scope1.audit.CheckerID='';
		 
		 $scope1.audit.AuthStat='Authorised';
		 break;
       case 'Reject':
      	   
         if ($scope1.audit.AuthStat!='Unauthorised')	
		 {
            fn_Show_Exception('FE-VAL-008');
			   return false;
		   } 
          if($scope1.audit.MakerID==userID)
   			 {
               fn_Show_Exception('FE-VAL-026');
			   return false;
			 }  
		         
		$scope1.audit.CheckerID=userID;
		 $scope1.audit.AuthStat='Rejected';
		 //$scope1.audit.MakerRemarks='';
		 $scope1.audit.CheckerRemarks=dialogRemarks;
		 //$scope1.audit.MakerDtStamp='';
		 $scope1.audit.CheckerDtStamp='';
		 //$scope1.audit.CheckerID='';
		 
		 break; 
       
   
   }
//$scope1.$apply();		  
return true;
}
 
function fndeleteConfirmation($scope)
{
	fn_show_confirmation('FE-VAL-018',$scope,'Delete')
	if(confirmation)
	  return true;
	else
      return false;     		
	//if (!confirmation)
	//return false;
//return true;	
}

function fnrejectconfirmation($scope,operation)
{
	fn_show_confirmation('FE-VAL-019',$scope,'Reject');
	//if (!confirmation)
	//return false;
return true;	
}

function fnauthconfirmation($scope,operation)
{
	fn_show_confirmation('FE-VAL-021',$scope,'Auth');
	//if (!confirmation)
	//return false;
return true;	
}

$(document).ready(function(){

$('#masterFooter').click(function(event)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

$scope.mastershow=true;
$scope.detailshow=false;
$scope.auditshow=false;
$scope.$apply();	 
	
}
);


$('#detailFooter').click(function(event)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
$scope.mastershow=false;
$scope.detailshow=true;
$scope.auditshow=false;
 var fn = window["fn"+MenuName+"DetailClick"];
   if (typeof fn === "function")
	 if (!fn($scope))
	 {event.preventDefault();
	   return;
	  }
$scope.$apply();

	}
);

$('#AuditFooter').click(function(event)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
$scope.mastershow=false;
$scope.detailshow=false;
$scope.auditshow=true;
$scope.$apply();
	}
);

}
);





 
 
 //angular.module('operation').service('operationService', function() {
  // this.backendCall=function(appServerCallInput){
	  			   	 
//--------This is to call the click event of  Operational icon Section------------ 
/*
$("#Query").click(function (event) { 

 if (!fn_check_access_rights('TeacherTimeTable','Query'))
 {	 
	fn_Show_ValidationException('FE-VAL-006'); 
	event.preventDefault(); //To Disallow the operation
 }
 else
 {
	 
    var $scope = angular.element(document.getElementById('TeacherTimeTableCtrl')).scope();
	$scope.teacherName = SuccessTTTResponse.body.teacherName;
    $scope.teacherID = SuccessTTTResponse.body.teacherID;
    $scope.timeTable=SuccessTTTResponse.body.timeTable;
	$scope.periodTimings=Institute.periodTimings;
    $scope.audit=SuccessTTTResponse.body.audit;
	$scope.$apply();
	
	
 }	
	
	
	}
 );
*/			   
app.factory('OperationScopes', function ($rootScope) {
    var mem = {};
 
    return {
        store: function (key, value) {
            mem[key] = value;
        },
        get: function (key) {
            return mem[key];
        }
    };
});	                  
