var authConfig={Operation:'authorization',
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose the filter criteria for extra curricular activity which you want to Authorize/Reject</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="ActivityType">Activity Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="ActivityType" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Activity Type--</option>\n'+
                                  '<option value="Male">Sports</option>\n'+
                                  '<option value="Male">Culture</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Level">Level</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Level" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Level--</option>\n'+
                                  '<option value="Male">State</option>\n'+
                                  '<option value="Male">District</option>\n'+
                                  '<option value="Male">International</option>\n'+
                                  '<option value="Male">Internal</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="authStatus">Auth status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="authStatus" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Auth status--</option>\n'+
                                  '<option value="Male">Authorized</option>\n'+
                                  '<option value="Male">Unauthorized</option>\n'+
                                  '<option value="Male">Rejected</option>\n'+
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to Authorize/Reject.</p>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Activity Description</th>\n'+
                                                            '<th>Activity Type</th>\n'+
                                                            '<th>Event Date (DD/MM/YYYY)</th>\n'+
                                                            '<th>Level</th>\n'+
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
                                                            '<td>Sports day - Athletic Competttion</td>\n'+
                                                            '<td>S</td>\n'+
                                                            '<td>25-01-2020</td>\n'+
                                                            '<td>E</td>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Activity detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<p class="col-orange"><i class="material-icons">help_outline</i>Please view the result first and then press Authorize/Reject for the activity Authorize/Reject.</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// general content start
    '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+

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

                                  '<label for="AssigneeGroup">Assignee Group</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="AssigneeGroup" class="form-control autocomplete" readonly value="G19200000">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<center><h4>Others</h4></center>\n'+

                                  '<label for="bs_datepicker_container">Event Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="5"  type="text"  class="form-control LastItem" readonly value="25-01-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Participation Due Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="6"  type="text"  class="form-control LastItem" readonly value="24-01-2020">\n'+
                                  '</div>\n'+
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

                                  '<label for="Venue">Venue</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="8" type="text" id="Venue" class="form-control autocomplete LastItem" readonly value="School Ground">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

    '</div>\n'+

  // Audit Start
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
                                    '<a href="javascript:void(0);" class="waves-effect">Authorize <span aria-hidden="true"></span></a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect buttonMargin">Reject <span aria-hidden="true"></span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }