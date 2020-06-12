var subScreen = false;
var selectBypassCount = 0;
var subScreenName;
var Instructions;
var app = angular.module('SubScreen',[]);
app.controller('SubScreenCtrl', function ($scope, $compile, $timeout) {
    //Screen Specific Scope Starts

   // $scope.appServerCall = appServerCallService.backendCall; //This is to reuse angular app server HTTP call service 
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
    $scope.currentOperation="";
    $scope.currentStep=1;  
    $scope.parentMenuIcon="";
    $scope.parentMenuName="";
    $scope.activeSubMenuIcon="";
    $scope.activeSubMenu="";

/********************summary detail filter and result data model*****************************/
$scope.summaryDataModel={
        filter: {
             },
        SummaryResult: []

                         };

$scope.emptySummaryDataModel={
                 filter:{},
                 summaryResult :[]};
             
             
/**************** audit detail data model*******************/
      $scope.auditDataModel=
      {
        MakerID: "",
        AuthStat: "",
        MakerRemarks: "",
        CheckerRemarks: "",
        MakerDtStamp: "",
        CheckerDtStamp: "",
        CheckerID: "",
        Version: "",
        RecordStat: "Open"
      };

      $scope.emptyAuditDataModel=
      {
        MakerID: "",
        AuthStat: "",
        MakerRemarks: "",
        CheckerRemarks: "",
        MakerDtStamp: "",
        CheckerDtStamp: "",
        CheckerID: "",
        Version: "",
        RecordStat: "Open"
      };
      
/****************************dataModel**************/
$scope.dataModel={};

/*************ReadOnly*****************/
$scope.editable= false;

/*****************tab state configuration*******/
      $scope.selectedTabIndex= 0;

/****************search state configuration*********/
      $scope.searchVisible= false;
      $scope.searchText='';
      $scope.searchIsLoading=false;
      
/******************** summaryResult state configuration *************************/
      $scope.summaryResult= [];
       
/***************************Primary cols*********************/    
    $scope.primaryKeyCols= [];

/******************** alert state configuration*****************/
      $scope.errorType= "";
      $scope.error= [{ errorCode: '', errorMessage: '', errorParam: '' }];
      $scope.showAlert= false;

/****************API call **********************/
      $scope.serviceName= '';
      $scope.serviceType= '';
      $scope.summaryService= '';
       $scope.summaryServiceType= '';

/**********************remarks state************/
      $scope.remarks= '';

/******************Response Handler********************/
$scope.responseDispatch=function(){};


/**********************SelectMaster*******************/
$scope.selectMaster=window.parent.selectMaster;

$scope.addDynamicElement=function(id,wheretoAdd,template)
{
    
$timeout(function () {		
    var $elmnt = $('#'+id);
    var template1 = angular.element(template); 
 
    switch(wheretoAdd)
    {
        case 'Before':
             $elmnt.prepend(template1);
             break;
         case 'After': 
           $elmnt.after(template1);
           break;
         case 'Append':
          $elmnt.append(template1);   
             
    }
    $compile(template1)(getSubScreenScope());
});
};

});
             
$(document).ready(function () {
    subScreenName=window.parent.subScreenArgs.screenName;
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    $scope.parentMenuIcon=window.parent.subScreenArgs.parentMenuIcon;
    $scope.parentMenuName=window.parent.subScreenArgs.parentMenuName;
    $scope.activeSubMenuIcon=window.parent.subScreenArgs.activeSubMenuIcon;
    $scope.activeSubMenu=window.parent.subScreenArgs.activeSubMenu;
    $scope.$apply();
    loadjscssfile('Instructions'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
  
 
    $('#previous').click(function ()
    {
        bottomTabClick='Previous'
        previousClickHandler();
    }
    );


    $('#next').click(function ()
    {
        bottomTabClick='Next';
        nextClickHandler();
    }
    );
    
 
});

function impleFunction(filename)
{
    if(filename=='Scope.min.js')
    {
        initScope();
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
  else if(filename=='AuthConfig.min.js') 
     loadjscssfile('Scope'+'.min.js','js',window.parent.subScreenArgs.parentMenuName,window.parent.subScreenArgs.activeSubMenu,true);
 
}


function loadjscssfile(filename, filetype,parentMenu,subMenu,onLoadRequired){
    if (filetype=="js"){ //if filename is a external JavaScript file
        var fileref=document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src", 'js/config/'+parentMenu+'/'+subMenu+'/'+filename);
        if(onLoadRequired)
         fileref.onload=  function(){impleFunction(filename)};
    }
    else if (filetype=="css"){ //if filename is an external CSS file
        var fileref=document.createElement("link");
        fileref.setAttribute("rel", "stylesheet");
        fileref.setAttribute("type", "text/css");
        fileref.setAttribute("href", 'css/config/'+parentMenu+'/'+subMenu+'/'+filename);
        fileref.onload= function(){impleFunction(filename)};
       // fileref.onreadystatechange=implefunction();
    }
    if (typeof fileref!="undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref);
}

function parentStateChange (object){
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    if(Object.keys(object).length>0) {
      for(var i=0;i< Object.keys(object).length;i++)
      {
       $scope[Object.keys(object)[i]]=object[Object.keys(object)[i]];
     }
     $scope.$apply();
  }
} 
function getSubScreenScope (){
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
     return $scope;
}
  
  
function mandatoryCheck(Operation, currentStep,$scope){
    switch (Operation) {
      case 'Create':
        switch (currentStep) {
          case 1: return true;
            break;
        default:
          createMandatoryCheck()
            break
        }

        break
      case 'Query':
        switch (currentStep) {
          case 1: 
              return true;
            break;
          default:
            if (!queryMandatoryCheck(this))
              return false;
        }

        break
      case 'Modification':
        switch (currentStep) {
          case 1: 
              return true;
            break;
          case 2:
            if (!queryMandatoryCheck(this))
              return false;
          case 4:
            if (!modificationMandatoryCheck(this))
              return false;
          default: 
          return true;
        }

        break;
      case 'Deletion':
        switch (currentStep) {
          case 1: 
              return true;
            break;
        case 2 :
            if (!queryMandatoryCheck(this))
              return false;
           break;
          default:
              return true;
        }

        break;
      case 'Authorisation':
        switch (currentStep) {
          case 1: 
              return true;
            break;
         case 2 :
            if (!queryMandatoryCheck(this))
              return false;
           break;
          default:
              return true;
        }
        break;
    }

    return true;
  }

