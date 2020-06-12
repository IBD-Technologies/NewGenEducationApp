var stepOne = 0;
var selectedOperation = '';
var currentStep = 1;
var startStep = 1;
var endStep = 3;
var screenName = '';

var screenStepper;
function stepOneSetAction(clicked_id) {
   /* if ($("#" + clicked_id + " .setAction").hasClass("hidden")) {
        $(".setAction").addClass("hidden");
        $("#" + clicked_id + " .setAction").removeClass("hidden");
        //$("#selectedOperation").html(clicked_id);
        selectedOperation = clicked_id;
        stepOne = 1;
    } else
    {
        $(".setAction").addClass("hidden");
        $("#selectedOperation").html("");
        stepOne = 0;
        selectedOperation = "";
    }*/
    
     $(".setAction").addClass("hidden");
        $("#" + clicked_id + " .setAction").removeClass("hidden");
        //$("#selectedOperation").html(clicked_id);
        selectedOperation = clicked_id;
        stepOne = 1;
    nextClickHandler();
}

function createStepper(operation, currentStep, startStep, endStep)
{
    //console.log(operation+currentStep+startStep+endStep);
    var stepNumber;
    var stepperLabel = "";
    var stepperTemplate = "";
    var stepperContent = "";
    var mobile = false;
    var x = window.matchMedia("(max-width: 700px)");
    /*if (x.matches) {
     var cols=Math.floor(12/3);
     mobile=true;
     }
     else*/
    var cols = Math.floor(12 / endStep);
    //var cols=endStep;

    for (var i = startStep; i <= endStep; i++) {
        stepNumber = i;
        stepperLabel = getStepperLabel(operation, stepNumber);
        /*'<div id="stepper'+stepNumber+' "class="bs-stepper">\n'+
         '<div class="bs-stepper-header" role="tablist">\n'+*/
        if (currentStep == i) {
            stepperTemplate = '<div class="col-xs-' + cols + ' col-sm-' + cols + ' col-md-' + cols + ' col-lg-' + cols + ' stepBottom' + ' ">\n' +
                    '<div class="flex step step' + stepNumber + ' active "data-target="#test-l-' + stepNumber + '">\n' +
                    '<button type="button" class="step-trigger" role="tab" id="stepper1trigger' + stepNumber + ' "aria-controls="test-l-' + stepNumber + ' ">\n' +
                    '<span class="bs-stepper-circle">' + stepNumber + '</span>\n' +
                    '</button>\n' +
                    '<div class="bs-stepper-line step' + stepNumber + ' active"></div>\n' +
                    '</div>\n' +
                    '</div>';
        } else if (i == endStep || (mobile && i == cols - 1)) {
            stepperTemplate = '<div class="col-xs-' + cols + ' col-sm-' + cols + ' col-md-' + cols + ' col-lg-' + cols + '">\n' +
                    '<div class="flex step step' + stepNumber + '"data-target="#test-l-' + stepNumber + '">\n' +
                    '<button type="button" class="step-trigger" role="tab" id="stepper1trigger' + stepNumber + ' "aria-controls="test-l-' + stepNumber + ' ">\n' +
                    '<span class="bs-stepper-circle">' + stepNumber + '</span>\n' +
                    '</button>\n' +
                    '</div>\n' +
                    '</div>';
        } else {
            stepperTemplate = '<div class="col-xs-' + cols + ' col-sm-' + cols + ' col-md-' + cols + ' col-lg-' + cols + '">\n' +
                    '<div class="flex step step' + stepNumber + ' "data-target="#test-l-' + stepNumber + '">\n' +
                    '<button type="button" class="step-trigger" role="tab" id="stepper1trigger' + stepNumber + ' "aria-controls="test-l-' + stepNumber + ' ">\n' +
                    '<span class="bs-stepper-circle">' + stepNumber + '</span>\n' +
                    '</button>\n' +
                    '<div class="bs-stepper-line step' + stepNumber + '"></div>\n' +
                    '</div>\n' +
                    '</div>';
        }
        stepperContent = getStepperContent(operation, stepNumber,endStep);
        $("#stepperHeader").append(stepperTemplate);
       /*$("#stepperContent").append(stepperContent);*/
       getSubScreenScope().addDynamicElement("stepperContent",'Append',stepperContent);
      
    }


    stepperAction(currentStep, startStep, endStep);
}

function getStepperLabel(operation, stepNumber) {

    for (var i = 0; i < screenStepper.length; i++) {
        if (screenStepper[i].screenName == screenName) {
            for (var j = 0; j < screenStepper[i].stepper.length; j++) {
                if (screenStepper[i].stepper[j].Operation == operation) {
                    for (var k = 0; k < screenStepper[i].stepper[j].step.length; k++) {
                        if (screenStepper[i].stepper[j].step[k].stepNumber == stepNumber) {
                            return screenStepper[i].stepper[j].step[k].stepperLabel;
                        }
                    }
                }
            }
        }
    }

    return("");
}
function getStepperContent(operation, stepNumber,endStep) {
// console.log("operation  "+operation)
// console.log("step number  "+stepNumber);
    for (var i = 0; i < screenStepper.length; i++) {
        // console.log("first :"+screenStepper[i].screenName);
        // console.log("screen name is"+screenName);
        if (screenStepper[i].screenName == screenName) {
            for (var j = 0; j < screenStepper[i].stepper.length; j++) {
                if (screenStepper[i].stepper[j].Operation == operation) {
                    for (var k = 0; k < screenStepper[i].stepper[j].step.length; k++) {
                        //console.log("before "+screenStepper[i].stepper[j].step[k].stepNumber+"   "+stepNumber);
                        if (screenStepper[i].stepper[j].step[k].stepNumber == stepNumber) {
                            //console.log("after "+screenStepper[i].stepper[j].step[k].stepNumber+"   "+stepNumber);
                           if ((stepNumber !=1 && stepNumber!=endStep) || operation=='query')
                           {   
                            var content ='<div id="test-l-'+stepNumber+'" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                                          getinsHeaderTemplate( getStepperLabel(operation, stepNumber),operation,stepNumber)+
                                          screenStepper[i].stepper[j].step[k].triggerElement +
                                          '</div>' ; 
                            return content;
                          }
                          else
                             return screenStepper[i].stepper[j].step[k].triggerElement; 
                        }
                    }
                }
            }
        }
    }

    return ("");
}

function cancelClickHandler()
{
    
}

async function nextClickHandler() {
   var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    
    if (await nextStepEventHandler($scope))
    {    

    var maxstep;
     
    if (selectedOperation != "")
    {
        for (var i = 0; i < screenStepper.length; i++) {
            if (screenStepper[i].screenName == screenName) {
                for (var j = 0; j < screenStepper[i].stepper.length; j++) {
                    if (screenStepper[i].stepper[j].Operation == selectedOperation) {
                        maxstep = screenStepper[i].stepper[j].endStep;
                    }
                }
            }

        }
    } else
        maxstep = 3;

    if (currentStep == 1)
    {

        $("#stepperHeader").empty();
        $("#stepperContent").empty();

        createStepper(selectedOperation, currentStep, 1, maxstep);
        $('#test-l-' + currentStep).removeClass("active");
        $('.step' + currentStep).removeClass("active");
        $('.step' + currentStep).addClass("completed");

        currentStep++;
        $('.step' + currentStep).addClass("active");
        console.log("current step: " + currentStep);
        $('#test-l-' + currentStep).addClass("active");
    } else
    if (currentStep > 1 && currentStep < maxstep)
    {
        $('#test-l-' + currentStep).removeClass("active");
        $('.step' + currentStep).removeClass("active");
        $('.step' + currentStep).addClass("completed");

        currentStep++;
        $('.step' + currentStep).addClass("active");
        $('#test-l-' + currentStep).addClass("active");
    } else if (maxstep == currentStep)
    {
       
    }
    if (selectedOperation == 'query' || selectedOperation == 'authorization' || selectedOperation == 'deletion')
    {
        if (currentStep == maxstep)
        {
            $('.focused').removeClass('focused');
        }
    }

    window.scrollTo(0, 0);
    if (currentStep == 1)
    {
        $scope.ShowScreenFooter = false;
        $scope.ShowAuthButton = false;
        $scope.ShowRejectButton = false;
        $scope.ShowCancelButton = false;
        $scope.ShowDeleteButton = false;
        $scope.ShowSaveButton = false;
        $scope.ShowPrevButton = false;
        $scope.ShowNextButton = false;


     
    } else if (currentStep == maxstep)
    {
        $scope.ShowScreenFooter = true;
        $scope.ShowPrevButton = true;

        if (selectedOperation == 'query')
        {
            $scope.ShowCancelButton = true;
            $scope.ShowAuthButton = false;
            $scope.ShowRejectButton = false;
            $scope.ShowDeleteButton = false;
            $scope.ShowSaveButton = false;
        } else if (selectedOperation == 'authorization')
        {
            $scope.ShowAuthButton = true;
            $scope.ShowRejectButton = true;
            $scope.ShowCancelButton = true;
            $scope.ShowDeleteButton = false;
            $scope.ShowSaveButton = false;
        } else if (selectedOperation == 'deletion')
        {
            $scope.ShowAuthButton = false;
            $scope.ShowRejectButton = false;
            $scope.ShowCancelButton = true;
            $scope.ShowDeleteButton = true;
            $scope.ShowSaveButton = false;
        } else
        {
            $scope.ShowAuthButton = false;
            $scope.ShowRejectButton = false;
            $scope.ShowCancelButton = true;
            $scope.ShowDeleteButton = false;
            $scope.ShowSaveButton = true;
        }


        $scope.ShowNextButton = false;
      } else
    {
        $scope.ShowScreenFooter = true;
        $scope.ShowPrevButton = true;
        $scope.ShowNextButton = true;
        $scope.ShowAuthButton = false;
        $scope.ShowRejectButton = false;
        $scope.ShowCancelButton = false;
        $scope.ShowDeleteButton = false;
        $scope.ShowSaveButton = false;
   //     $scope.$apply();
    }
 switch (selectedOperation)
    {
        case 'create':
            $scope.currentOperation = 'Create';
            break;
        case 'query':
            $scope.currentOperation = 'View';
            break;
        case 'deletion':
            $scope.currentOperation = 'Delete';
            break;
        case 'modification':
            $scope.currentOperation = 'Modify';
            break;
        case 'authorization':
            $scope.currentOperation = 'Auth';
    }
    $scope.currentStep = currentStep;


    $scope.$apply();
    }

if(skipStep)
nextClickHandler();

}
;
function previousClickHandler() {
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
    var maxstep;
    if (selectedOperation != "")
    {
        for (var i = 0; i < screenStepper.length; i++) {
            if (screenStepper[i].screenName == screenName) {
                for (var j = 0; j < screenStepper[i].stepper.length; j++) {
                    if (screenStepper[i].stepper[j].Operation == selectedOperation) {
                        maxstep = screenStepper[i].stepper[j].endStep;
                    }
                }
            }

        }

    } else
        maxstep = 3;

    if (currentStep == 1)
    {
        // alert(selectedOperation);
        // $("#"+selectedOperation+" .setAction").removeClass("hidden");
    } else
    if (currentStep > 1 && currentStep <= maxstep)
    {
        $('#test-l-' + currentStep).removeClass("active");
        $('.step' + currentStep).removeClass("active");
        $('.step'+currentStep).removeClass("completed");

        currentStep--;
        $('.step' + currentStep).addClass("active");
        $('#test-l-' + currentStep).addClass("active");
    }
    if (currentStep == 1)
    {

        $("#" + selectedOperation + " .setAction").removeClass("hidden");
    }
    if (currentStep == 1)
    {
        $scope.ShowScreenFooter = false;
        $scope.ShowAuthButton = false;
        $scope.ShowRejectButton = false;
        $scope.ShowCancelButton = false;
        $scope.ShowDeleteButton = false;
        $scope.ShowSaveButton = false;


    } else if (currentStep == maxstep)
    {
        $scope.ShowScreenFooter = true;
        $scope.ShowPrevButton = true;
        if (selectedOperation == 'query')
        {
            $scope.ShowCancelButton = true;
            $scope.ShowAuthButton = false;
            $scope.ShowRejectButton = false;
            $scope.ShowDeleteButton = false;
            $scope.ShowSaveButton = false;
        } else if (selectedOperation == 'authorization')
        {
            $scope.ShowAuthButton = true;
            $scope.ShowRejectButton = true;
            $scope.ShowCancelButton = true;
            $scope.ShowDeleteButton = false;
            $scope.ShowSaveButton = false;
        } else if (selectedOperation == 'deletion')
        {
            $scope.ShowAuthButton = false;
            $scope.ShowRejectButton = false;
            $scope.ShowCancelButton = true;
            $scope.ShowDeleteButton = true;
            $scope.ShowSaveButton = false;
        } else
        {
            $scope.ShowAuthButton = false;
            $scope.ShowRejectButton = false;
            $scope.ShowCancelButton = true;
            $scope.ShowDeleteButton = false;
            $scope.ShowSaveButton = true;
        }



        $scope.ShowNextButton = false;
        $scope.ShowAuthButton = false;
        $scope.ShowRejectButton = false;
        $scope.ShowCancelButton = false;
        $scope.ShowDeleteButton = false;
        $scope.ShowSaveButton = false;

    } else
    {
        $scope.ShowScreenFooter = true;
        $scope.ShowPrevButton = true;
        $scope.ShowNextButton = true;
        $scope.ShowAuthButton = false;
        $scope.ShowRejectButton = false;
        $scope.ShowCancelButton = false;
        $scope.ShowDeleteButton = false;
        $scope.ShowSaveButton = false;


    }


    switch (selectedOperation)
    {
        case 'create':
            $scope.currentOperation = 'Create';
            break;
        case 'query':
            $scope.currentOperation = 'View';
            break;
        case 'deletion':
            $scope.currentOperation = 'Delete';
            break;
        case 'modification':
            $scope.currentOperation = 'Modify';
            break;
        case 'authorization':
            $scope.currentOperation = 'Auth';
    }
    $scope.currentStep = currentStep;


    $scope.$apply();
    window.scrollTo(0, 0);
}
;
function getCurrentStep()
{
    return currentStep;
}
function getCurrentOperation()
{
    return selectedOperation;
}

function getInstructions(operation,stepNumber)
{
    for (var i = 0; i < Instructions.length; i++) {
        if (Instructions[i].Operation == operation) {
            for (var j = 0; j < Instructions[i].Steps.length; j++) {
                if (Instructions[i].Steps[j].stepNumber == stepNumber) {
                  //  for (var k = 0; k < screenStepper[i].stepper[j].step.length; k++) {
                        if (Instructions[i].Steps[j].stepNumber == stepNumber) {
                            return Instructions[i].Steps[j].instructions;
                        }
                   // }
                }
            }
        }
    }

    return([]);
}