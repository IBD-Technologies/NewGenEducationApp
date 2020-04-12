/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var MenuName;
var parentOperation;

if(TEST=="YES"){
    
    var ReportSrc="https://cohesivetest.ibdtechnologies.com/CohesiveReportWeb/frameset?__report=";
    
}else{

var ReportSrc="https://cohesive.ibdtechnologies.com/CohesiveReportWeb/frameset?__report=";

}

var app= angular.module('ReportOperation', ["ngRoute"]);
 app.config(function($routeProvider,$sceDelegateProvider){
	 $routeProvider
	 .when("/PostViewOperation",{template:'<div id="PostViewOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center">\n'+
                         /*'<li class="page-item"><a id="Detail" class="page-link page_style" ng-click ="fnDetail($event)" href="#!defaultOperation">Detail<span class="badge badge-link cohesive_badge"><i class="fas fa-th-list"></i></span></a></li>\n'+*/
                         '<li class="page-item"><a id="BackQuery" ng-click="fnBack($event)" class="page-link page_style" href="#!defaultOperation">Back<span class="badge badge-link cohesive_badge"><i class="fas fa-backspace"></i></span></a></li>\n'+
                         
				  '</ul>\n'+
	 '</div>',controller:'ReportOperationCtl'})
	 
	 .when("/defaultOperation",{template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                   '<li class="page-item"><a id="View" class="page-link page_style" ng-click ="fnView($event)" href="#!PostViewOperation">Report<span class="badge badge-link cohesive_badge"><i class="fas fa-info-circle"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+
                   '</ul>\n'+
	 '</div>',controller:'ReportOperationCtl'})
	 
	 .otherwise({template:'<div id="defaultOperation">\n'+
			      '<ul class="pagination pagination_background justify-content-center ">\n'+
                    '<li class="page-item"><a id="View" class="page-link page_style" ng-click ="fnView($event)" href="#!PostViewOperation">Report<span class="badge badge-link cohesive_badge"><i class="fas fa-info-circle"></i></span></a></li>\n'+
                    '<li class="page-item"><a id="BackQuery" ng-click="fnClose($event)" class="page-link page_style" href="#!defaultOperation">Close<span class="badge badge-link cohesive_badge"><i class="fas fa-window-close"></i></span></a></li>\n'+    
				  '</ul>\n'+
				 
				  
	 '</div>',controller:'ReportOperationCtl'});
	 
 });
 
app.controller('ReportOperationCtl',function($scope, $location,$routeParams,$anchorScroll,OperationScopes)
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
  
  

$scope.fnBack=function($event)
{
//parentOperation=null;
$("#ReportFooter").show();
//$("#ReportFooter").hide();
$("#subscreenHeader").addClass("mb-3");
$("#subscreenContent").removeClass('reportScreenContent');
$("#subscreenContent").addClass('subscreenContent');
$('#reportDiv').removeClass("embed-responsive");
$('#reportDiv').removeClass("cohesive-embed-responsive");
$('#reportDiv').removeClass("embed-responsive-1by1");
$('#reportFrame').removeClass("embed-responsive-item");
$('#reportDiv').addClass("embed-responsive");
$('#reportDiv').addClass("cohesive-embed-responsive");
$('#reportDiv').addClass("embed-responsive-1by1");
$('#reportFrame').addClass("embed-responsive-item");

$("#reportFrame").attr("type","application/pdf");
$("#reportFrame").attr("src","");


var fn = window["fn"+MenuName+"Back"];
   if (typeof fn === "function") fn();
 if(parentOperation ==null || parentOperation =='undefined')
 {	 
    window.parent.$("#frame").attr('src', '');
}	}

$scope.fnView=function($event)
                    {
	parentOperation="Report";                  
OperationScopes.store('ReportOperationCtl', $scope);        
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
                                          
$scope.fnPostView=function()
{
$location.url('/PostViewOperation');
 $anchorScroll();
 // Select Box change starts
 //fnSelectBoxEventHandler(selectBoxes);
//Select Box change ends
window.parent.fn_hide_parentspinner();
  fn_Show_Information('FE-VAL-032','S');  
 
} 

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
					 
					 }  */
  

    
    
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

function fnShowReport(src)
{
 window.parent.fn_show_spinner();   
$("#ReportFooter").hide();
$("#subscreenHeader").removeClass("mb-3");
$("#subscreenContent").removeClass('subscreenContent');
$("#subscreenContent").addClass('reportScreenContent');
$('#reportDiv').removeClass("embed-responsive");
$('#reportDiv').removeClass("cohesive-embed-responsive");
$('#reportDiv').removeClass("embed-responsive-1by1");
$('#reportFrame').removeClass("embed-responsive-item");
$('#reportDiv').addClass("embed-responsive");
$('#reportDiv').addClass("cohesive-embed-responsive");
$('#reportDiv').addClass("embed-responsive-1by1");
$('#reportFrame').addClass("embed-responsive-item");

$("#reportFrame").attr("type","application/pdf");
$("#reportFrame").attr("src",src);

//$scope.mastershow=false;

//$scope.detailshow=true;


}



//});


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
