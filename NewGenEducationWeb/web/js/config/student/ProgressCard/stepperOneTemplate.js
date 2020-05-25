var stepperOneTemplate='<div id="test-l-1" role="tabpanel" class="bs-stepper-pane active" aria-labelledby="stepper1trigger1">\n'+
                '<div class="header block">\n'+
                '<h2 class="col-blue">Step 1: Select Operation</h2>\n'+
                '<br>\n'+
                                '<p class="col-orange"><i class="material-icons">help_outline</i>Please click the operation which you want to perform</p>\n'+ 
                          '</div>\n'+                  
                '<div class="">\n'+
                '<br>\n'+
                   '<div id="query" onclick="stepOneSetAction(this.id)" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n'+
                    '<div class="card  waves-effect waves-block iconCards">\n'+
                        '<div class="header bg-white">\n'+
                            '<h2>\n'+
                                '<img class="operationIcon" src="images/query.png"><font  style="margin-left: 10px;">Query</font>\n'+
                            '</h2>\n'+
                            '<ul class="header-dropdown m-r--5">\n'+
                               '<i class="material-icons setAction hidden">check</i>\n'+
                           '</ul>\n'+
                       '</div>\n'+
                   '</div>\n'+
               '</div>\n'+
               '<div id="parentSignature" onclick="stepOneSetAction(this.id)" class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n'+
                '<div  class="card  waves-effect waves-block iconCards">\n'+
                    '<div class="header bg-white" >\n'+
                        '<h2>\n'+
                            '<img class="operationIcon" src="images/modification.png"><font  style="margin-left: 10px;">Parent Signature</font>\n'+
                        '</h2>\n'+
                        '<ul class="header-dropdown m-r--5">\n'+
                           '<i class="material-icons setAction hidden">check</i>\n'+
                       '</ul>\n'+
                   '</div>\n'+
               '</div>\n'+
           '</div>\n'+
           
       
'</div>';