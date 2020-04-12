/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var MenuName;
var parentOperation;
var app= angular.module('Summaryoperation', ["ngRoute"]);
 app.config(function($routeProvider,$sceDelegateProvider){
	 $routeProvider
	 .when("/PostViewOperation",{template:'<div id="PostViewOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center">\n'+
                         '<li class="page-item"><a id="Detail" class="page-link page_style" ng-click ="fnDetail($event)" href="#!defaultOperation">Detail<span class="badge badge-link cohesive_badge"><i class="fas fa-th-list"></i></span></a></li>\n'+
                         '<li class="page-item"><a id="BackQuery" ng-click="fnBack($event)" class="page-link page_style" href="#!defaultOperation">Cancel<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                         
				  '</ul>\n'+
	 '</div>',controller:'SummaryoperationCtl'})
	 
	 .when("/defaultOperation",{template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                   '<li class="page-item"><a id="View" class="page-link page_style" ng-click ="fnView($event)" href="#!PostViewOperation">View<span class="badge badge-link cohesive_badge"><i class="fas fa-info-circle"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+
                   '</ul>\n'+
	 '</div>',controller:'SummaryoperationCtl'})
	 
	 .otherwise({template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                    '<li class="page-item"><a id="View" class="page-link page_style" ng-click ="fnView($event)" href="#!PostViewOperation">View<span class="badge badge-link cohesive_badge"><i class="fas fa-info-circle"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+
                       
				  '</ul>\n'+
				 
				  
	 '</div>',controller:'SummaryoperationCtl'});
	 
	  $sceDelegateProvider.resourceUrlWhitelist([
    // Allow same origin resource loads.
    'self',
	'file:///*.*'
    // Allow loading from our assets domain.  Notice the difference between * and **.
    //'http://srv*.assets.example.com/**'
  ]);

  // The blacklist overrides the whitelist so the open redirect here is blocked.
  //$sceDelegateProvider.resourceUrlBlacklist([
    //'//http://myapp.example.com/clickThru**'
  //]);
	 
 });
 
app.controller('SummaryoperationCtl',function($scope, $location,$routeParams,$anchorScroll,OperationScopes)
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
parentOperation=null;
var fn = window["fn"+MenuName+"Close"];
   if (typeof fn === "function") fn();
    window.parent.fnMainPage(); 
    window.parent.$("#frame").attr('src', '');
}
  
  

$scope.fnBack=function($event)
{
parentOperation=null;
var fn = window["fn"+MenuName+"Back"];
   if (typeof fn === "function") fn();
    //window.parent.$("#frame").attr('src', '');
}	

$scope.fnView=function($event)
                    {
                         parentOperation="SummaryQuery";   
OperationScopes.store('SummaryoperationCtl', $scope);        
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
						 {$event.preventDefault();
					      return;
						 }
						} 
                      
            			 fn_Show_Information('FE-VAL-016','S');
					 
					 }  
  

$scope.fnDetail=function($event)
                    {
	                  
OperationScopes.store('SummaryoperationCtl', $scope); 	
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
                         if (!fn('Detail'))
	                     {$event.preventDefault();
					     return;
						 }
						} 
                      
					  var fn = window["fn"+MenuName+"Detail"];
                      if (typeof fn === "function") 
                       {  
                         if (!fn('Detail'))
						 {$event.preventDefault();
					      return;
						 }
						} 
                      
            			// fn_Show_Information('FE-VAL-016','S');
					 
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
$scope.$apply();
	});
});

/*$('#AuditFooter').click(function(event)
{
var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
$scope.mastershow=false;
$scope.detailshow=false;
$scope.auditshow=true;
$scope.$apply();
	}
);

}
);*/
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
