/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var MenuName;
var parentOperation;
var app= angular.module('BatchRunOperation', ["ngRoute"]);
 app.config(function($routeProvider,$sceDelegateProvider){
	 $routeProvider
	 /*.when("/PostViewOperation",{template:'<div id="PostViewOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center">\n'+
                         '<li class="page-item"><a id="Refresh" class="page-link page_style" ng-click ="fnDetail($event)" href="#!defaultOperation">Refresh<span class="badge badge-link cohesive_badge"><i class="fas fa-sync-alt"></i></span></a></li>\n'+
                         '<li class="page-item"><a id="BackQuery" ng-click="fnBack($event)" class="page-link page_style" href="#!defaultOperation">Back<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                         
				  '</ul>\n'+
	 '</div>',controller:'BatchRunOperationCtl'})*/
	 
	 .when("/defaultOperation",{template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                   '<li class="page-item"><a id="Run" class="page-link page_style" ng-click ="fnBatchRun($event)" href="#!PostViewOperation">View<span class="badge badge-link cohesive_badge"><i class="fas fa-play"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+
                   '</ul>\n'+
	 '</div>',controller:'BatchRunOperationCtl'})
	 
	 .otherwise({template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                    '<li class="page-item"><a id="Run" class="page-link page_style" ng-click ="fnBatchRun($event)" href="#!PostViewOperation">Run<span class="badge badge-link cohesive_badge"><i class="fas fa-play"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+    
				  '</ul>\n'+
				 
				  
	 '</div>',controller:'BatchRunOperationCtl'});
	 
 });
 
 
app.controller('BatchRunOperationCtl',function($scope, $location,$routeParams,$anchorScroll,OperationScopes)
{

$scope.$on('$routeChangeStart', function(event) {
    //$location.hash($routeParams.scrollTo);
    //$anchorScroll();  
	//console.log($location.path());
	//console.log(oldRoute);
	 //window.FontAwesomeConfig = { autoReplaceSvg: false };
	if (!($location.url()=='/PostViewOperation'||$location.url()=='/defaultOperation'))
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
  
  

/*$scope.fnBack=function($event)
{
parentOperation=null;
var fn = window["fn"+MenuName+"Back"];
   if (typeof fn === "function") fn();
    //window.parent.$("#frame").attr('src', '');
}	*/



$scope.fnBatchRun=function($event)
{
					  var fn = window["fn"+MenuName+"MandatoryCheck"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Run'))
	                     {
					      $event.preventDefault();
                           return;
						 }						   
                        }
					  var fn = window["fn"+MenuName+"DefaultandValidate"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Run'))
	                     {$event.preventDefault();
					     return;
						 }
						} 	
	
	
if (!fn_check_access_rights(MenuName,'Run'))
 {	 
	fn_Show_Exception('FE-VAL-006'); 
	$event.preventDefault(); //To Disallow the operation
 }
else
{	
fnRunconfirmation($scope);
   if(!confirmation)
   {
     $event.preventDefault();
    return;
   }
	
	
			   
}
}
  
 $scope.fnRunAfterConfirmation=function()
{
	 var fn = window["fn"+MenuName+"Run"];
      parentOperation='Run';
   if (typeof fn === "function") 
	   if(fn())  
	   {
        if(!fnDefaultandValidateAudit('Run',$scope,User.Id))
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
 /*
$scope.fnAuthorise=function($event)
                    {
	                  
					  var fn = window["fn"+MenuName+"MandatoryCheck"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Auth'))
	                     {
					      $event.preventDefault();
                           return;
						 }						   
                        }
					  var fn = window["fn"+MenuName+"DefaultandValidate"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Auth'))
	                     {$event.preventDefault();
					     return;
						 }
						} 
                      
					 /* var fn = window["fn"+MenuName+"View"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Auth'))
						 {$event.preventDefault();
					      return;
						 }
						} */
                      
            			 /*fn_Show_Information('FE-VAL-030','E');
					 
					 }  */
  
/*$scope.fnDetail=function($event)
                    {
	                  
					  var fn = window["fn"+MenuName+"MandatoryCheck"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Detail'))
	                     {
					      $event.preventDefault();
                           return;
						 }						   
                        }
					  var fn = window["fn"+MenuName+"DefaultandValidate"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Refresh'))
	                     {$event.preventDefault();
					     return;
						 }
						} 
                      
					  var fn = window["fn"+MenuName+"Detail"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Refresh'))
						 {$event.preventDefault();
					      return;
						 }
						} 
                      
            			 //fn_Show_Information('FE-VAL-016','S');
					 
					 }  
					 */
});
  
  
/*$(document).ready(function(){

$('#masterFooter').click(function(event)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

$scope.mastershow=true;
$scope.detailshow=false;
//$scope.auditshow=false;
$scope.$apply();	 
	
}
);


$('#detailFooter').click(function(event)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
$scope.mastershow=false;
$scope.detailshow=true;
//$scope.auditshow=false;
$scope.$apply();
	});
});
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
