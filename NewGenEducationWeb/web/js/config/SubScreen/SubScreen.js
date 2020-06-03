var subScreen = false;
var selectBypassCount = 0;
var subScreenName;
var Instructions;
var app = angular.module('SubScreen', ['BackEnd', 'search']);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout, appServerCallService, searchCallService, SeacrchScopeTransfer) {
    //Screen Specific Scope Starts

    $scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
/* *********************************** Footer Button scopes ******************************/
    $scope.ShowScreenFooter = false;
    $scope.ShowPrevButton = false;
    $scope.ShowNextButton = false;
    $scope.ShowAuthButton = false;
    $scope.ShowRejectButton = false;
    $scope.ShowCancelButton = false;
    $scope.ShowDeleteButton = false;
    $scope.ShowSaveButton = false;
/************************************ Screen Operation and Current Step Tracking*******************/    
    $scope.currentOperation=""
    $scope.currentStep=""        
    $scope.parentMenuIcon=""
    $scope.parentMenuName=""
    $scope.activeSubMenuIcon=""
    $scope.activeSubMenu=""
});

$(document).ready(function () {
    subScreenName=window.parent.subScreenArgs.screenName;
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    $scope.parentMenuIcon=window.parent.subScreenArgs.parentMenuIcon
    $scope.parentMenuName=window.parent.subScreenArgs.parentMenuName
    $scope.activeSubMenuIcon=window.parent.subScreenArgs.activeSubMenuIcon
    $scope.activeSubMenu=window.parent.subScreenArgs.activeSubMenu
    $scope.$apply();
    loadjscssfile('Instructions'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
  
 
    $('#previous').click(function ()
    {
        previousClickHandler();
    }
    );


    $('#next').click(function ()
    {
        nextClickHandler();
    }
    );
    
   

});

function impleFunction(filename)
{
    if(filename=='AuthConfig.min.js')
    {
        
       screenStepper = [{screenName:subScreenName ,
            // stepper for student profile
            stepper: [
                defaultConfig,
                CreateConfig,
                QueryConfig,
                ModificationConfig,
                DeletionConfig,
                AuthConfig,
              ]
        }];
    
    screenName = subScreenName;
    createStepper('Default', 1, 1, 3);
     window.parent.fn_hide_parentspinner();
    }
   else if(filename=='Instructions.min.js')
    loadjscssfile('CommonTemplate'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
   else if(filename=='CommonTemplate.min.js')
    loadjscssfile('CreateConfig'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
   else if(filename=='CreateConfig.min.js')
        loadjscssfile('QueryConfig'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
   else if(filename=='QueryConfig.min.js')
        loadjscssfile('ModificationConfig'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
   else if(filename=='ModificationConfig.min.js') 
        loadjscssfile('DeletionConfig'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
   else if(filename=='DeletionConfig.min.js') 
     loadjscssfile('AuthConfig'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
}


function loadjscssfile(filename, filetype,parentMenu,subMenu,onLoadRequired){
    if (filetype=="js"){ //if filename is a external JavaScript file
        var fileref=document.createElement('script')
        fileref.setAttribute("type","text/javascript")
        fileref.setAttribute("src", 'js/config/'+parentMenu+'/'+subMenu+'/'+filename)
        if(onLoadRequired)
         fileref.onload=  function(){impleFunction(filename)};
    }
    else if (filetype=="css"){ //if filename is an external CSS file
        var fileref=document.createElement("link")
        fileref.setAttribute("rel", "stylesheet")
        fileref.setAttribute("type", "text/css")
        fileref.setAttribute("href", 'css/config/'+parentMenu+'/'+subMenu+'/'+filename)
        fileref.onload= function(){impleFunction(filename)};
       // fileref.onreadystatechange=implefunction();
    }
    if (typeof fileref!="undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)
}
