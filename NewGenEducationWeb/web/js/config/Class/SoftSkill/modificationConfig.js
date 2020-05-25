var modificationConfig={Operation:'modification',
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose filter criteria for Class Exam Assessment which you want to modify</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Class--</option>\n'+
                                  '<option value="Male">IX/A</option>\n'+
                                  '<option value="Male">IX/B</option>\n'+
                                  '<option value="Male">IX/C</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="exam">Exam</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">Half</option>\n'+
                                  '<option value="Male">Annual</option>\n'+
                                  '<option value="Male">Term3</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Skill">Skill</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="Skill" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Skill--</option>\n'+
                                  '<option selected value="1">Leadership</option>\n'+
                                  '<option  value="2">Communication</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  
                                 
                                  '<label for="authStatus">Auth status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="authStatus" class="form-control form-line show-tick">\n'+
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
                                    '<a href="javascript:void(0);" tabindex="5" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to modify the result.</p>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Class</th>\n'+
                                                            '<th>Exam</th>\n'+
                                                            '<th>Skill</th>\n'+
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
                                                            '<td>IX/A</td>\n'+
                                                            '<td>Half</td>\n'+
                                                            '<td>Leadership</td>\n'+
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
                                    '<a href="javascript:void(0);" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Soft Skill Assessment detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<p class="col-orange"><i class="material-icons">help_outline</i>Please edit the details which you want to modify and press submit to modify the updated details for Class Soft Skill Assessment </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+

                                  '<label for="Class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Class--</option>\n'+
                                  '<option selected value="Male">IX/A</option>\n'+
                                  '<option  value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Class">Exam</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Exam--</option>\n'+
                                  '<option selected value="Male">Half</option>\n'+
                                  '<option  value="Male">Final</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Skill">Skill</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="Skill" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Leadership</option>\n'+
                                  '<option  value="2">Communication</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<center><h4>Skill Entries</h4></center>\n'+

                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#Mark" data-toggle="tab">Skill Entry</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  '</ul>\n'+

                                  '<div class="tab-content">\n'+

// Mark start
'<div role="tabpanel" class="tab-pane fade in active" id="Mark">\n'+
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


'<div class="table-responsive">\n'+
'<table id="student_data_table" class="table table-bordered table-striped table-hover  dataTable">\n'+
'<thead>\n'+
'<tr>\n'+
'<th>Select Record</th>\n'+
'<th>Name</th>\n'+
'<th>Category</th>\n'+
'<th>Teacher Feedback</th>\n'+
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
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Gayathri R" >\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="Skill" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Leadership</option>\n'+
                                  '<option value="2">Communication</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="very good">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
'</tr>\n'+
'</tbody>\n'+
'</table>\n'+
'</div>\n'+   
'</div>\n'+
// Description start


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