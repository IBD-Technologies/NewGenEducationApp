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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose the filter criteria for video Assignment which you want to Authorize/Reject</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="AssignmentType">Assignment Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="AssignmentType" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Assignment Type--</option>\n'+
                                  '<option value="Male">Homework</option>\n'+
                                  '<option value="Male">Term/Exam</option>\n'+
                                  '<option value="Male">Improvement</option>\n'+
                                  '<option value="Male">Punishment</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Subject--</option>\n'+
                                  '<option value="Male">Tamil</option>\n'+
                                  '<option value="Male">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Due Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="3"  type="text"  class="form-control LastItem" placeholder="Enter Due Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="authStatus">Auth status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="authStatus" class="form-control form-line show-tick">\n'+
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to Authorize/Reject the result.</p>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Subject</th>\n'+
                                                            '<th>Assignment Type</th>\n'+
                                                            '<th>Due Date (DD/MM/YYYY)</th>\n'+
                                                            '<th>Assignee</th>\n'+
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
                                                            '<td>Tamil</td>\n'+
                                                            '<td>H</td>\n'+
                                                            '<td>18-02-2020</td>\n'+
                                                            '<td>G19200003</td>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Video Assignment detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<p class="col-orange"><i class="material-icons">help_outline</i>Please view the details first and then press Authorize/Reject</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
  '<li role="presentation" ><a href="#assContent" data-toggle="tab">Content</a></li>\n'+
  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// general content start
    '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+

          '<label for="AssignmentID">Assignment ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="AssignmentID" class="form-control autocomplete" readonly value="A17200003">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="AssignmentType">Assignment Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="AssignmentType" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Assignment Type--</option>\n'+
                                  '<option selected value="Male">Homework</option>\n'+
                                  '<option disabled value="Male">Test</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Description">Description</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="Description" class="form-control autocomplete" readonly value="Home work assignment">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="Subject" class="form-control autocomplete" readonly value="Tamil">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="AssigneeGroup">Assignee Group</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="5" type="text" id="AssigneeGroup" class="form-control autocomplete" readonly value="G19200003">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<center><h4>Others</h4></center>\n'+

                                  '<label for="Comments">Comments</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="6" type="text" id="Comments" class="form-control autocomplete LastItem" readonly value="good detals">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Assignment Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="7"  type="text"  class="form-control LastItem" readonly value="18-02-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

    '</div>\n'+
    // video content start
    '<div role="tabpanel" class="tab-pane fade" id="assContent">\n'+
    '<label for="VideoURL">Video URL</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="VideoURL" class="form-control autocomplete" readonly value="https://www.youtube.com/embed/hW7DW9NIO9M">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                   '<center>\n'+
                                   '<div class="videoAssignment">\n'+
                                  '<iframe class="assIframe" src="https://www.youtube.com/embed/hW7DW9NIO9M">\n'+
                                  '</iframe>\n'+
                                  '</div>\n'+
                                  '</center>\n'+
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