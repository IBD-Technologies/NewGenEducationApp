var queryConfig={Operation:'query',
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose filter criteria for Class Exam Assessment which you want to query</p>\n'+
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

                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option disabled value="2">English</option>\n'+
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to view the result.</p>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Class</th>\n'+
                                                            '<th>Exam</th>\n'+
                                                            '<th>Subject</th>\n'+
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
                                                            '<td>English</td>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Time Table detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please view the result for choosen Class Exam Assessment details\n'+
                                    '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+

                                  '<label for="Class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Class--</option>\n'+
                                  '<option selected value="Male">IX/A</option>\n'+
                                  '<option disabled value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Class">Exam</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Exam--</option>\n'+
                                  '<option selected value="Male">Half</option>\n'+
                                  '<option disabled value="Male">Final</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option disabled value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<center><h4>Mark Entry</h4></center>\n'+

                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#Mark" data-toggle="tab">Mark Entry</a></li>\n'+
                                  '<li role="presentation"><a href="#Description" data-toggle="tab">Grade Description</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  '</ul>\n'+

                                  '<div class="tab-content">\n'+

// Mark start
'<div role="tabpanel" class="tab-pane fade in active" id="Mark">\n'+

'<div class="table-responsive">\n'+
'<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
'<thead>\n'+
'<tr>\n'+
'<th>Select Record</th>\n'+
'<th>Name</th>\n'+
'<th>Mark</th>\n'+
'<th>Teacher Feedback</th>\n'+
'<th>Grade</th>\n'+
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
'<td>Alan M</td>\n'+
'<td>89</td>\n'+
'<td>Good</td>\n'+
'<td>B</td>\n'+
'</tr>\n'+
'</tbody>\n'+
'</table>\n'+
'</div>\n'+   
'</div>\n'+
// Description start

'<div role="tabpanel" class="tab-pane fade" id="Description">\n'+

'<div class="table-responsive">\n'+
'<table id="student_data_table" class="table table-bordered table-striped table-hover dataTable">\n'+
'<thead>\n'+
'<tr>\n'+
'<th>Grade</th>\n'+
'<th>Mark Range</th>\n'+
'</tr>\n'+
'</thead>\n'+
'<tbody>\n'+
'<tr>\n'+
'<td>A</td>\n'+
'<td>91 - 100</td>\n'+
'</tr>\n'+
'<tr>\n'+
'<td>B</td>\n'+
'<td>81 - 90</td>\n'+
'</tr>\n'+
'<tr>\n'+
'<td>E</td>\n'+
'<td>51 - 60</td>\n'+
'</tr>\n'+
'<tr>\n'+
'<td>F</td>\n'+
'<td>0 - 50</td>\n'+
'</tr>\n'+
'<tr>\n'+
'<td>C</td>\n'+
'<td>71 - 80</td>\n'+
'</tr>\n'+
'<tr>\n'+
'<td>D</td>\n'+
'<td>61 - 70</td>\n'+
'</tr>\n'+
'</tbody>\n'+
'</table>\n'+
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