/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function getLaststepTemplate(operation,stepNumber)
{
var lastStepTemplate='<div id="test-l-'+stepNumber+'" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<br>\n' +
                            '<div class="alert alert-warning submitForm">\n' +
                            '<strong>Well done!</strong> You successfully completed the steps. Press <strong>';
                     
    if(operation=='modification')        
                      lastStepTemplate=   lastStepTemplate+'Save</strong>.\n';
    else if(operation=='deletion')      
                      lastStepTemplate=   lastStepTemplate+'Delete</strong>.\n';
    else if(operation=='authorization')
                      lastStepTemplate=   lastStepTemplate+'Auth Or Reject</strong>.\n'; 
    else if(operation=='create')
                      lastStepTemplate=   lastStepTemplate+'Save</strong>.\n'; 
                           
    
    lastStepTemplate=lastStepTemplate +
                            '</div>\n' +
                            '<div class="col-sm-12">\n' +
                            '<b>Maker Remarks</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' ;
                    
              if(operation!='authorization')
                  lastStepTemplate=lastStepTemplate +
                            '<textarea tabindex="1" id="Maker_remarks" rows="2" class="form-control resize LastItem" placeholder="Please enter remarks if you want"></textarea>\n';
              else
                  lastStepTemplate=lastStepTemplate +
                          '<textarea tabindex="1" id="Checker_remarks" rows="2" class="form-control resize LastItem" placeholder="Please enter remarks if you want"></textarea>\n';
                    
               lastStepTemplate= lastStepTemplate +   
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>';
return  lastStepTemplate;                   
}
