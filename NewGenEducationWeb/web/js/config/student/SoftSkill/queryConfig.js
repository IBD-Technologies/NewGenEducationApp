var queryConfig={Operation:'query',
                                startStep : 1,
                                endStep:2,
                                step: 
                                [
                                {stepNumber:1,stepperLabel:'',
                                triggerElement:'<div id="test-l-1" role="tabpanel" class="bs-stepper-pane active" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 1: Enter Filter Criteria</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose filter criteria for Student Soft Skill Assessment which you want to query</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                 '<label for="StudentName">Student Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" onkeyup="searchFilter(id);" id="StudentName" class="form-control autocomplete" placeholder="Enter student name to search">\n'+
                                  '</div>\n'+
                                 '<span class="input-group-addon">\n'+
                                            '<i class="material-icons">search</i>\n'+
                                        '</span>\n'+
                                  '</div>\n'+


                                  '<div class="card searchResult">\n'+
                                    '<div class="header" id="searchResultHeader">\n'+
                                    
                                    '</div>\n'+
                                      '<div class="body" id="searchResultBody">\n'+
                                      
                                      '</div>\n'+
                                    '</div>\n'+


                                 '<label for="studentId">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="studentId" class="form-control" placeholder="Enter student id ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="exam">Exam</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">Half</option>\n'+
                                  '<option value="Male">Annual</option>\n'+
                                  '<option value="Male">Term3</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+


                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="4" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                '</div>'
                              },
                              {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Soft Skill Assessment detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please view the result for choosen Student Soft Skill Assessment\n'+
                                    '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+

                                  '<label for="StudentName">Student Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="StudentName" class="form-control autocomplete" readonly value="Issac">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="studentId">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="studentId" class="form-control" readonly value="Student42">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="Class">Exam</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Exam--</option>\n'+
                                  '<option selected value="Male">Half</option>\n'+
                                  '<option disabled value="Male">Final</option>\n'+
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

'<div class="table-responsive">\n'+
'<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
'<thead>\n'+
'<tr>\n'+
'<th>Select Record</th>\n'+
'<th>Skill</th>\n'+
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
'<td><b>Leadership</b></td>\n'+
'<td>Good</td>\n'+
'<td>5 star</td>\n'+
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
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }