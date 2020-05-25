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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter name in the filter criteria for the query</p>\n'+
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


                                  '<label for="bs_datepicker_container">From Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="3"  type="text"  class="form-control LastItem" placeholder="Enter From Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">To Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4"  type="text"  class="form-control LastItem" placeholder="Enter To Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="AuthStatus">Auth Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="5" id="AuthStatus" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose AuthStatus--</option>\n'+
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
                                    '<a href="javascript:void(0);" tabindex="6" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please click corresponding tabs to view Student General, Marks, RAnk, Grade and Audit details respectively.\n'+
                                    '</div>\n'+
                                  
                                  '</div>\n'+
                                  

                                  '<div class="body">\n'+
                                  '<!-- Nav tabs -->\n'+
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
                                  '<li role="presentation"><a href="#Others" data-toggle="tab">Others</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  '</ul>\n'+

                                  '<!-- Tab panes -->\n'+
                                  '<div class="tab-content">\n'+

            // general tab
          '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+
                                  


                                  '<label for="name">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="name" class="form-control" value="Shagambaree R" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="name" class="form-control" value="Student6" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="ActivityID">Activity ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="ActivityID" class="form-control autocomplete" readonly value="O23200006">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="ActivityDescription">Activity Description</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="ActivityDescription" class="form-control autocomplete" readonly value="Sports day - Athletic Competttion">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                   '<label for="ActivityType">Activity Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="ActivityType" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Activity Type--</option>\n'+
                                  '<option selected value="Male">Sports</option>\n'+
                                  '<option disabled value="Male">Cultures</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Level">Level</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="Level" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Level--</option>\n'+
                                  '<option selected value="Male">Internal</option>\n'+
                                  '<option disabled value="Male">State</option>\n'+
                                  '<option disabled value="Male">District</option>\n'+
                                  '<option disabled value="Male">International</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Participations">Participations</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="Participations" class="form-control autocomplete" readonly value="No">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Result">Result</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="Result" class="form-control autocomplete" readonly value="Sports day - Athletic Competttion">\n'+
                                  '</div>\n'+
                                  '</div>\n'+




           '</div>\n'+

           // Others tab
          '<div role="tabpanel" class="tab-pane fade" id="Others">\n'+
                                  '<label for="bs_datepicker_container">Event Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4"  type="text"  class="form-control LastItem" readonly value="25-01-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+ 

                                  '<label for="bs_datepicker_container">Participation Due Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4"  type="text"  class="form-control LastItem" readonly value="24-01-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+ 

                                  '<label for="bs_datepicker_container">Confirmation Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4"  type="text"  class="form-control LastItem" readonly value="24-01-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<label for="Venue">Venue</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="Venue" class="form-control autocomplete" readonly value="School Ground">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


         '</div>\n'+
                                  
       

     

                                 

                                  '<div role="tabpanel" class="tab-pane fade" id="Audit">\n'+
                                  
                                  auditCardsTemplate+
                                  '</div>\n'+




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