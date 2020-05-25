var instituteClassQueryConfig={Operation:'query',
                                startStep : 1,
                                endStep:4,
                                step: 
                                [{stepNumber:1,stepperLabel:'Select Operation',
                                triggerElement:stepperOneTemplate},
                                {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Enter Filter Criteria</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must select class name in the filter criteria which you want to query</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="studentClass">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="studentClass" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Class--</option>\n'+
                                  '<option value="1">BS Information Technology</option>\n'+
                                  '<option value="2">BE Information Technology</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="authStatus">Auth status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="authStatus" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Auth status--</option>\n'+
                                  '<option value="Male">Authorized</option>\n'+
                                  '<option value="Male">Unauthorized</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="3" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                '</div>'
                              },
                              {stepNumber:3,stepperLabel:'',
                                triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 3: Choose the record</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to view result.</p>\n'+
                                  '</div>\n'+
                                  
                                                                   
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Class</th>\n'+
                                                            '<th>Teacher ID</th>\n'+
                                                            '<th>Name</th>\n'+
                                                        '</tr>\n'+
                                                    '</thead>\n'+
                                                    '<tbody>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_1" />\n'+
                                                            '<label for="basic_checkbox_1"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>IX/B</td>\n'+
                                                            '<td>Teacher2</td>\n'+
                                                            '<td>Teacher2</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
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
                                    '</div>\n'+
                                  '</div>'
                                },
                              {stepNumber:4,stepperLabel:'',
                                triggerElement:'<div id="test-l-4" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 4: Institute detailed view</h2>\n'+
                                  '<br>\n'+
                                  '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please view detaled view for the choosen Institute..\n'+
                                    '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  '</ul>\n'+

                                  '<div class="tab-content">\n'+

// general content start
'<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+
'<label for="StudentName">Class</label>\n'+
'<div class="form-group input-group">\n'+
'<div class="form-line">\n'+
'<input type="text" id="StudentName" class="form-control autocomplete" readonly value="IX/B">\n'+
'</div>\n'+
'</div>\n'+

'<center><h4>General</h4></center>\n'+

'<label for="teacherName">Teacher Name</label>\n'+
'<div class="form-group input-group">\n'+
'<div class="form-line">\n'+
'<input type="text" id="teacherName" class="form-control autocomplete" readonly value="Teacher2">\n'+
'</div>\n'+
'</div>\n'+

'<label for="teacherId">Teacher ID</label>\n'+
'<div class="form-group input-group">\n'+
'<div class="form-line">\n'+
'<input type="text" id="teacherId" class="form-control autocomplete" readonly value="Teacher2">\n'+
'</div>\n'+
'</div>\n'+

'<label for="authStatus">Attendance</label>\n'+
'<div class="input-group input-group-lg">\n'+
'<select id="authStatus" class="form-control form-line show-tick">\n'+
'<option disabled value="">--Choose Auth status--</option>\n'+
'<option value="Male">Daywise</option>\n'+
'<option disabled value="Male">Weekly</option>\n'+
'</select>\n'+
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
'<input type="text" id="periodNumber" class="form-control autocomplete" readonly value="1">\n'+
'</div>\n'+
'</div>\n'+
'<br>\n'+

'<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
'<b>Start Time</b>\n'+
'<div class="input-group">\n'+
'<span class="input-group-addon">\n'+
'<i class="material-icons">access_time</i>\n'+
'</span>\n'+
'<div class="form-line">\n'+
'<input type="text" class="form-control time12" readonly value="09 : 30">\n'+
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
'<input type="text" class="form-control time12" readonly value="10 : 15">\n'+
'</div>\n'+
'</div>\n'+
'</div>\n'+

'<label for="noon">Noon</label>\n'+
'<div class="form-group input-group">\n'+
'<div class="form-line">\n'+
'<input type="text" id="noon" class="form-control autocomplete" readonly value="Forenoon">\n'+
'</div>\n'+
'</div>\n'+




'</div>\n'+
// Audit start

'<div role="tabpanel" class="tab-pane fade" id="Audit">\n'+
auditCardsTemplate+
'</div>\n'+


'</div>\n'+






                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }