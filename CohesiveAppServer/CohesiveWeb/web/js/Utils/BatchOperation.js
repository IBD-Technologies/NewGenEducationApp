/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var MenuName;
var parentOperation;
var app= angular.module('BatchOperation', ["ngRoute"]);
 app.config(function($routeProvider,$sceDelegateProvider){
	 $routeProvider
	 .when("/PostViewOperation",{template:'<div id="PostViewOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center">\n'+
                         '<li class="page-item"><a id="Refresh" class="page-link page_style" ng-click ="fnDetail($event)" href="#!defaultOperation">Refresh<span class="badge badge-link cohesive_badge"><i class="fas fa-sync-alt"></i></span></a></li>\n'+
                         '<li class="page-item"><a id="BackQuery" ng-click="fnBack($event)" class="page-link page_style" href="#!defaultOperation">Back<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                         
				  '</ul>\n'+
	 '</div>',controller:'BatchOperationCtl'})
	 
	 .when("/defaultOperation",{template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                   '<li class="page-item"><a id="View" class="page-link page_style" ng-click ="fnView($event)" href="#!PostViewOperation">View<span class="badge badge-link cohesive_badge"><i class="fas fa-info-circle"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+
                   '</ul>\n'+
	 '</div>',controller:'BatchOperationCtl'})
	 
	 .otherwise({template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                    '<li class="page-item"><a id="View" class="page-link page_style" ng-click ="fnView($event)" href="#!PostViewOperation">View<span class="badge badge-link cohesive_badge"><i class="fas fa-info-circle"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+    
				  '</ul>\n'+
				 
				  
	 '</div>',controller:'BatchOperationCtl'});
	 
 });
 
 
app.controller('BatchOperationCtl',function($scope, $location,$routeParams,$anchorScroll,OperationScopes)
{

$scope.$on('$routeChangeStart', function(event) {
    //$location.hash($routeParams.scrollTo);
    //$anchorScroll();  
	//console.log($location.path());
	//console.log(oldRoute);
	 //window.FontAwesomeConfig = { autoReplaceSvg: false };
	if (!($location.url()=='/PostTranOperation'||$location.url()=='/PostViewOperation'||$location.url()=='/PostQueryOperation'||$location.url()=='/defaultOperation' || $location.url()=='/PostCreateOperation'))
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
 }}


$scope.fnView=function($event)
                    {
	                  
OperationScopes.store('BatchOperationCtl', $scope);        
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
						 }
						
                      
            			 fn_Show_Information('FE-VAL-016','S');
					 
					 }  
  
$scope.fnDetail=function($event)
                    {
	                  
OperationScopes.store('BatchOperationCtl', $scope); 	
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

});
  
$(document).ready(function(){

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
 var fn = window["fn"+MenuName+"DetailClick"];
   if (typeof fn === "function")
	 if (!fn($scope))
	 {event.preventDefault();
	   return;
	  }
$scope.$apply();

});

});
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
