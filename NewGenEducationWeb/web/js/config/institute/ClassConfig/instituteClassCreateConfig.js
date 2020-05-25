var instituteClassCreateConfig={Operation:'create',
                                startStep : 1,
                                endStep:3,
                                step: 
                                [{stepNumber:1,stepperLabel:'Select Operation',
                                triggerElement:stepperOneTemplate},
                                {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Enter Data</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter institute data to create Institute </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label tabindex="1" for="StudentName">Class</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="StudentName" class="form-control autocomplete" placeholder="Enter class">\n'+
                                  '</div>\n'+
                                 
                                  '</div>\n'+
                                  '<center><h4>General</h4></center>\n'+

                                    '<label for="teacherName">Teacher Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" tabindex="2" id="teacherName" class="form-control autocomplete" placeholder="Enter teacher name">\n'+
                                  '</div>\n'+

                                  '<label for="teacherId">Teacher ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" tabindex="3" id="teacherId" class="form-control autocomplete" placeholder="Enter teacher id">\n'+
                                  '</div>\n'+

                                  '<label for="Attendance">Attendance</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" tabindex="4" id="Attendance" class="form-control autocomplete" placeholder="Enter Attendance type">\n'+
                                  '</div>\n'+

                                  '<center><h4>Period</h4></center>\n'+

                                  

                                  '<div class="header familHeader">\n'+
                                  '<div class="row">\n'+
                                  '<div class="col-sm-6 col-xs-6">\n'+
                                  '<h2><button type="button" class="btn btn-default font-bold waves-effect">0</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">Of</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">1</button>\n'+
                                  '</h2>\n'+
                                  '</div>\n'+
                                  '<div class="header-dropdown m-r--5">\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">chevron_right</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">add</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">remove</i></button>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" tabindex="5" id="periodNumber" class="form-control autocomplete" placeholder="Enter periodNumber number">\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                        '<b>Start Time</b>\n'+
                                        '<div class="input-group">\n'+
                                            '<span class="input-group-addon">\n'+
                                                '<i class="material-icons">access_time</i>\n'+
                                            '</span>\n'+
                                            '<div class="form-line">\n'+
                                                '<input type="text" tabindex="6" class="form-control time12" placeholder="Ex: 11:59 pm">\n'+
                                            '</div>\n'+
                                        '</div>\n'+
                                    '</div>\n'+
                                    '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                        '<b>End Time</b>\n'+
                                        '<div class="input-group">\n'+
                                            '<span class="input-group-addon">\n'+
                                                '<i class="material-icons">access_time</i>\n'+
                                            '</span>\n'+
                                            '<div class="form-line">\n'+
                                                '<input tabindex="7" type="text" class="form-control time12" placeholder="Ex: 11:59 pm">\n'+
                                            '</div>\n'+
                                        '</div>\n'+
                                    '</div>\n'+

                                    '<label for="noon">Noon</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="8" type="text" id="noon" class="form-control autocomplete LastItem" placeholder="Enter periodNumber number">\n'+
                                  '</div>\n'+

                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="9" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                '</div>'
                              },
                              {stepNumber:3,stepperLabel:'',
                                triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<br>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  '<div class="alert alert-warning submitForm">\n'+
                                '<strong>Well done!</strong> You successfully completed the steps. Press <strong>Submit</strong>.\n'+
                                    '</div>\n'+
                                    '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+


                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                     '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }