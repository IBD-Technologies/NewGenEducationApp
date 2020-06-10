/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var MenuName = 'cohesiveMainPage';
var app = angular.module('Main', ['search', 'BackEnd']);
app.controller('MainCtrl', function ($scope, searchCallService, SeacrchScopeTransfer, appServerCallService) {
    $scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
    $scope.dashBoardShow = true;
});
var uhtuliak;
var menuIcon;
var screenIcon;
var menuName;
//var screenName;
var subScreenArgs={screenName:'',
                   parentMenuIcon:'',
                   activeSubMenuIcon:'',
                   parentMenuName:'',
                   activeSubMenu:''}
                   
                   
                   
function fngetSrc(url) {
    var src = url + "?X-uhtuliak=" + uhtuliak + "&" + "nrb=" + Institute.ID;
    return src;
}
$(document).ready(function () {

    uhtuliak = $("#uhtuliak").val();
    //User.Id = $("#UIDServer").val();
    //Institute.ID = $("#InstituteServer").val();
    //Institute.Name = $("#InstituteNameServer").val();
    //$("#iID").val(Institute.ID);
    //$("#iName").val(Institute.Name);
   sessionStorage.setItem('GLOBAL',
                          JSON.stringify(
                           {instituteID:$("#InstituteServer").val(),
                             userID:$("#UIDServer").val(),
                             instituteName:$("#InstituteNameServer").val()
                           }
                           ));
//$('#scrollTop').click(function(){topFunction()});
    
     $('#StudentProfile').click(function () {
        fn_show_spinner();
        subScreenArgs.activeSubMenu="Profile";
        subScreenArgs.activeSubMenuIcon="person";
        subScreenArgs.parentMenuIcon='school';
        subScreenArgs.parentMenuName='Student'
        subScreenArgs.screenName='StudentProfile'
        var $scope = angular.element(document.getElementById('MainCtrl')).scope();
        $scope.dashBoardShow = false;
        $scope.$apply();
       // $("#frame").attr("src", fngetSrc("./StudentProfile.min.jsp"));
        $("#frame").attr("src", fngetSrc("./SubScreen.min.jsp"));

    });
    
    

});

function launchMainScreen()
{
    var $scope = angular.element(document.getElementById('MainCtrl')).scope();
    $scope.dashBoardShow=true;
    $scope.$apply();
}