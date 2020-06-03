/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getinsHeaderTemplate(label,operation,step)
{
var instructionHeaderTemplate= '</br>' +
                            '<div class="header">\n' +
                            '<h6 class="col-blue">Current step '+step+ ': ' +label+'</h6>\n' +
                            '<div class="panel-group" id ="helpContainer'+step+'" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h6 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#helpContainer'+step+'" href="#collapseHelp'+step+'" aria-expanded="false" aria-controls="collapseHelp'+step+'">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h6>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp'+step+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            '<ul>\n' +
                            getInstructionsTemplate(operation,step) +
                            '</ul>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' ;
                    
      return instructionHeaderTemplate;             
}
                    
                    
function getInstructionsTemplate(operation,step)
{
    var template='';
    var Ins=getInstructions(operation,step);
     for (var i = 0; i < Ins.length; i++) {
       template=  template+ '<li>'+Ins[i]+'</li>\n'
      }
 return template;   
}