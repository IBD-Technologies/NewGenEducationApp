var stepOne=0;
var selectedOperation='';
var currentStep =1;
var startStep =1;
var endStep =3;
var screenName ='';

var screenStepper;
function stepOneSetAction(clicked_id){
    if ($("#"+clicked_id+" .setAction").hasClass("hidden")){
        $(".setAction").addClass("hidden");
        $("#"+clicked_id+" .setAction").removeClass("hidden");
        //$("#selectedOperation").html(clicked_id);
        selectedOperation=clicked_id;
        stepOne=1;
    }
    else
    {  
        $(".setAction").addClass("hidden");
        $("#selectedOperation").html("");
        stepOne=0;
        selectedOperation="";
    }
    nextClickHandler();
}

function createStepper(operation,currentStep,startStep,endStep)
{
    //console.log(operation+currentStep+startStep+endStep);
    var stepNumber;
    var stepperLabel="";
    var stepperTemplate="";
    var stepperContent="";
    var mobile=false;
    var x = window.matchMedia("(max-width: 700px)")
    /*if (x.matches) {
      var cols=Math.floor(12/3);
      mobile=true;
    }
    else*/
    var cols=Math.floor(12/endStep); 
    //var cols=endStep;

                  for (var i = startStep; i <=endStep ; i++) {
                    stepNumber=i;
                     stepperLabel=getStepperLabel(operation,stepNumber);
                      /*'<div id="stepper'+stepNumber+' "class="bs-stepper">\n'+
                      '<div class="bs-stepper-header" role="tablist">\n'+*/
                      if (currentStep==i ) {
                        stepperTemplate='<div class="col-xs-'+cols+' col-sm-'+cols+' col-md-'+cols+' col-lg-'+cols+' stepBottom'+' ">\n'+
                        '<div class="flex step step'+stepNumber+' active "data-target="#test-l-'+stepNumber+'">\n'+
                          '<button type="button" class="step-trigger" role="tab" id="stepper1trigger'+stepNumber+' "aria-controls="test-l-'+stepNumber+' ">\n'+
                            '<span class="bs-stepper-circle">'+stepNumber+'</span>\n'+
                            
                        '</button>\n'+
                    '<div class="bs-stepper-line step'+stepNumber+' active"></div>\n'+
                    '</div>\n'+
                    
                    '</div>';
                }else if (i==endStep ||( mobile && i==cols-1)) {
                   stepperTemplate='<div class="col-xs-'+cols+' col-sm-'+cols+' col-md-'+cols+' col-lg-'+cols+'">\n'+
                   '<div class="flex step step'+stepNumber+'"data-target="#test-l-'+stepNumber+'">\n'+
                          '<button type="button" class="step-trigger" role="tab" id="stepper1trigger'+stepNumber+' "aria-controls="test-l-'+stepNumber+' ">\n'+
                            '<span class="bs-stepper-circle">'+stepNumber+'</span>\n'+
                            
                        '</button>\n'+
                    '</div>\n'+
                    '</div>';
                }
                else{
                    stepperTemplate='<div class="col-xs-'+cols+' col-sm-'+cols+' col-md-'+cols+' col-lg-'+cols+'">\n'+
                    '<div class="flex step step'+stepNumber+' "data-target="#test-l-'+stepNumber+'">\n'+
                          '<button type="button" class="step-trigger" role="tab" id="stepper1trigger'+stepNumber+' "aria-controls="test-l-'+stepNumber+' ">\n'+
                            '<span class="bs-stepper-circle">'+stepNumber+'</span>\n'+
                            
                        '</button>\n'+
                    '<div class="bs-stepper-line step'+stepNumber+'"></div>\n'+
                    '</div>\n'+
                    
                    '</div>';
                }
                    stepperContent=getStepperContent(operation,stepNumber);
                    $("#stepperHeader").append(stepperTemplate); 

                    $("#stepperContent").append(stepperContent);                  
                    //console.log(stepperContent);
                    }  
    
        
stepperAction(currentStep,startStep,endStep);
}

function getStepperLabel(operation,stepNumber){

    for (var i=0; i < screenStepper.length; i++) {
        if (screenStepper[i].screenName == screenName) {
            for (var j=0; j < screenStepper[i].stepper.length; j++) {
                if (screenStepper[i].stepper[j].Operation == operation) {
                  for (var k=0; k < screenStepper[i].stepper[j].step.length; k++) {
                    if (screenStepper[i].stepper[j].step[k].stepNumber==stepNumber) {
                        return screenStepper[i].stepper[j].step[k].stepperLabel;
                    }
                  }  
                }
            }
        }
    }

return "";
}
function getStepperContent(operation,stepNumber){
// console.log("operation  "+operation)
// console.log("step number  "+stepNumber);
    for (var i=0; i < screenStepper.length; i++) {
      // console.log("first :"+screenStepper[i].screenName);
      // console.log("screen name is"+screenName);
        if (screenStepper[i].screenName == screenName) {
            for (var j=0; j < screenStepper[i].stepper.length; j++) {
                if (screenStepper[i].stepper[j].Operation == operation) {
                  for (var k=0; k < screenStepper[i].stepper[j].step.length; k++) {
                    //console.log("before "+screenStepper[i].stepper[j].step[k].stepNumber+"   "+stepNumber);
                    if (screenStepper[i].stepper[j].step[k].stepNumber==stepNumber) {
                        //console.log("after "+screenStepper[i].stepper[j].step[k].stepNumber+"   "+stepNumber);
                        return screenStepper[i].stepper[j].step[k].triggerElement;
                    }
                  }  
                }
            }
        }
    }

return "";
}

function nextClickHandler(){

var maxstep;

if (selectedOperation!="")
{
    for (var i=0; i < screenStepper.length; i++) {
        if (screenStepper[i].screenName == screenName) {
            for (var j=0; j < screenStepper[i].stepper.length; j++) {
                if (screenStepper[i].stepper[j].Operation == selectedOperation) {
                     maxstep= screenStepper[i].stepper[j].endStep;                          
              }
    }
   }
  
  }
}
else
                     maxstep= 3;                          

if (currentStep==1)
{   
    
 $("#stepperHeader").empty();
 $("#stepperContent").empty();

createStepper(selectedOperation,currentStep,1,maxstep);
$('#test-l-'+currentStep).removeClass("active");
    $('.step'+currentStep).removeClass("active");
    $('.step'+currentStep).addClass("completed"); 

    currentStep++;
    $('.step'+currentStep).addClass("active");
    console.log("current step: "+currentStep);
  $('#test-l-'+currentStep).addClass("active");
}

else
    if (currentStep>1 && currentStep<maxstep)
 {
     $('#test-l-'+currentStep).removeClass("active");
     $('.step'+currentStep).removeClass("active");
     $('.step'+currentStep).addClass("completed");

     currentStep++;
     $('.step'+currentStep).addClass("active");
    $('#test-l-'+currentStep).addClass("active");
 }   
else if(maxstep==currentStep)
  {
    // if (maxStep==2)
    //  {
    //   $('#test-l-'+currentStep).removeClass("active");
    //  $('.step'+currentStep).removeClass("active");
    //  $('.step'+currentStep).addClass("completed");

    //  currentStep++;
    //  $('.step'+currentStep).addClass("active");
    // $('#test-l-'+currentStep).addClass("active");

    //  }


  }  
  // var href=$("#screenHeader");
  // // $(href).show();
  //  $('html, body').animate({
  //       scrollTop: $(href).offset().top + 'px'
  //   }, 'fast');
window.scrollTo(0, 0); 
if (currentStep==1)
    {
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.ShowScreenFooter=false; 
        
        $scope.$apply();
        
    }
    else if(currentStep==maxstep)
    {
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.ShowScreenFooter=true; 
        $scope.ShowPrevButton=true;  
        $scope.ShowNextButton=false;  
        $scope.$apply();
    }
     else
    {
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.ShowScreenFooter=true; 
        $scope.ShowPrevButton=true;  
        $scope.ShowNextButton=true;  
        $scope.$apply();
    }
  

};
function previousClickHandler(){

var maxstep;
if (selectedOperation!="")
{
   for (var i=0; i < screenStepper.length; i++) {
        if (screenStepper[i].screenName == screenName) {
            for (var j=0; j < screenStepper[i].stepper.length; j++) {
                if (screenStepper[i].stepper[j].Operation == selectedOperation) {
                     maxstep= screenStepper[i].stepper[j].endStep;                          
              }
    }
   }
  
  }

}
else
                     maxstep= 3;                          

if (currentStep==1)
{   
  // alert(selectedOperation);
    // $("#"+selectedOperation+" .setAction").removeClass("hidden");
}

else
    if (currentStep>1 && currentStep<=maxstep)
 {
     $('#test-l-'+currentStep).removeClass("active");
     $('.step'+currentStep).removeClass("active");
     //$('.step'+currentStep).addClass("completed");

     currentStep--;
     $('.step'+currentStep).addClass("active");
    $('#test-l-'+currentStep).addClass("active");
 }   
if (currentStep==1)
{   
  
    $("#"+selectedOperation+" .setAction").removeClass("hidden");
}
if (currentStep==1)
    {
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.ShowScreenFooter=false; 
        
        $scope.$apply();
        
    }
    else if(currentStep==maxstep)
    {
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.ShowScreenFooter=true; 
        $scope.ShowPrevButton=true;  
        $scope.ShowNextButton=false;  
        $scope.$apply();
    }
     else
    {
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
        $scope.ShowScreenFooter=true; 
        $scope.ShowPrevButton=true;  
        $scope.ShowNextButton=true;  
        $scope.$apply();
    }

window.scrollTo(0, 0); 
};

  function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
              $(".previewImage").css("display","block");
                $('#ShowImage')
                    .attr('src', e.target.result);
                    
            };

            reader.readAsDataURL(input.files[0]);
        }
    }
function readAnotherURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
              $(".previewImage").css("display","block");
                $('#ShowAnotherImage')
                    .attr('src', e.target.result);
                    
            };

            reader.readAsDataURL(input.files[0]);
        }
    }
    function uploadFile(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {

              $("#embedPdf").css("display","block");
                $('#embedPdf')
                    .attr('src', e.target.result);
                    
            };

            reader.readAsDataURL(input.files[0]);
        }
    }
     function reUploadFile(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
              $("#showPdf").css("display","block");
                $('#showPdf')
                    .attr('src', e.target.result);
                    
            };

            reader.readAsDataURL(input.files[0]);
        }
    }