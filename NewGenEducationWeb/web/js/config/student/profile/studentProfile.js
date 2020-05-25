var subScreen = false;
var selectBypassCount = 0;
var app = angular.module('SubScreen', ['BackEnd', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer) {
    //Screen Specific Scope Starts

    $scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
    $scope.ShowScreenFooter=false;  
    $scope.ShowPrevButton=false;  
    $scope.ShowNextButton=false;  
    
});

$(document).ready(function () {
    window.parent.fn_hide_parentspinner();   
    screenStepper = [{screenName: 'StudentProfile',
            // stepper for student profile
            stepper: [
                defaultConfig,
                studentProfileCreateConfig,
                studentProfileQueryConfig,
                studentProfileModificationConfig,
                studentProfileDeletionConfig,
                studentProfileAuthConfig
            ]
        }];
    screenName = "StudentProfile";
    createStepper('Default', 1, 1, 3);
    

$('#previous').click(function()
{
      previousClickHandler();      
}           
            );


$('#next').click(function()
{
       nextClickHandler();     
}           
            );

});