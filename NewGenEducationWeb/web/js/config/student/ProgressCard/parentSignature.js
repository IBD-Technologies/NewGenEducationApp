var parentSignature={Operation:'parentSignature',
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter name in the filter criteria for the Signature</p>\n'+
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


                                  '<label for="class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">IX/A</option>\n'+
                                  '<option value="Male">IX/B</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="exam">Exam</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="exam" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">Half</option>\n'+
                                  '<option value="Male">Annual</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="exam">Sign Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="exam" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">Yes</option>\n'+
                                  '<option value="Male">No</option>\n'+
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
                                                            '<th>Name</th>\n'+
                                                            '<th>ID</th>\n'+
                                                            '<th>Sign Status</th>\n'+
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
                                                            '<td>Sasi K</td>\n'+
                                                            '<td>Student4</td>\n'+
                                                            '<td>No</td>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Progress Card detailed view</h2>\n'+
                                  '<br>\n'+
                                  '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please click corresponding tabs to view Student General, Marks, RAnk, Grade and Audit details respectively and click Sign for Signature student progress card.\n'+
                                    '</div>\n'+
                                  
                                  '</div>\n'+
                                  

                                  '<div class="body">\n'+
                                  '<!-- Nav tabs -->\n'+
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
                                  '<li role="presentation"><a href="#Marks" data-toggle="tab">Marks</a></li>\n'+
                                  '<li role="presentation"><a href="#Rank" data-toggle="tab">Rank</a></li>\n'+
                                  '<li role="presentation"><a href="#Grade" data-toggle="tab">Grade</a></li>\n'+
                                 
                                  '</ul>\n'+

                                  '<!-- Tab panes -->\n'+
                                  '<div class="tab-content">\n'+

            // general tab
          '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+
                                  


                                  '<label for="name">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="name" class="form-control" value="Sasi K" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="name" class="form-control" value="Sasi K" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                   '<label for="exam">Exam</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="class" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Exam--</option>\n'+
                                  '<option disabled value="Male">Half</option>\n'+
                                  '<option disabled value="Male">Annual</option>\n'+
                                  '<option selected value="Male">Term3</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+


           '</div>\n'+

           // Marks tab
          '<div role="tabpanel" class="tab-pane fade" id="Marks">\n'+
                                  


                                 '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Subject</th>\n'+
                                                            '<th>Mark</th>\n'+
                                                            '<th>Grade</th>\n'+
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
                                                            '<td><b>Tamil</b></td>\n'+
                                                            '<td>76</td>\n'+
                                                            '<td>C</td>\n'+
                                                            '<td>None</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+

         '</div>\n'+
                                  
         //Rank start
        '<div role="tabpanel" class="tab-pane fade" id="Rank">\n'+
                     
                                  '<label for="totalMark">Total Mark</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="name" class="form-control" value="147" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="rank">Rank</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="rank" class="form-control" value="8" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+                           
        
        '</div>\n'+

      // Grade Description start
      '<div role="tabpanel" class="tab-pane fade" id="Grade">\n'+
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

              



                                  '</div>\n'+
                                  '</div>\n'+


                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                     '<li class="next">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect">Sign <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }