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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose the filter criteria for Notification which you want to Authoriz/Reject.</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="NotificationType">Notification Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="NotificationType" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Notification Type--</option>\n'+
                                  '<option value="Male">Homework</option>\n'+
                                  '<option value="Male">Emergency</option>\n'+
                                  '<option value="Male">Disciplinary action</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Mode">Mode</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Mode" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Mode--</option>\n'+
                                  '<option value="Male">Mail</option>\n'+
                                  '<option value="Male">SMS</option>\n'+
                                  '<option value="Male">Both</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Creation Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="3"  type="text"  class="form-control LastItem" placeholder="Enter Creation Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to Authoriz/Reject.</p>\n'+
                                  '</div>\n'+
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>ID</th>\n'+
                                                            '<th>Type</th>\n'+
                                                            '<th>Creation Date</th>\n'+
                                                            '<th>Mode</th>\n'+
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
                                                            '<td>N19200018</td>\n'+
                                                            '<td>Homework</td>\n'+
                                                            '<td>10-03-2020</td>\n'+
                                                            '<td>Both</td>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Notification detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<p class="col-orange"><i class="material-icons">help_outline</i>Please view the Notification details first and then press Authoriz/Reject the Notification.</p>\n'+

                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
  '<li role="presentation"><a href="#details" data-toggle="tab">Test</a></li>\n'+
  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// general content start
    '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+

        '<label for="NotificationID">Notification ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="NotificationID" class="form-control autocomplete" readonly value="N19200018">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="NotificationType">Notification Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="NotificationType" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Notification Type--</option>\n'+
                                  '<option selected value="Male">Homework</option>\n'+
                                  '<option disabled value="Male">Event</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Assignee">Assignee</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="Assignee" class="form-control autocomplete" readonly value="G19200000">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Mode">Mode</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="Mode" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Mode--</option>\n'+
                                  '<option selected value="Male">Both</option>\n'+
                                  '<option disabled value="Male">One</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<center><h4>Message</h4></center>\n'+

                                  '<label for="InEnglish">In English</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="5" type="text" id="InEnglish" class="form-control autocomplete" readonly value="Read English Lesson -4">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="OtherLanguage">In Other Language</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="6" type="text" id="OtherLanguage" class="form-control autocomplete" readonly value="ஆங்கில பாடம் -3 ஐப் படியுங்கள்">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="bs_datepicker_container">Delivery Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="7"  type="text"  class="form-control LastItem" readonly value="20-01-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

    '</div>\n'+
// test start
    '<div role="tabpanel" class="tab-pane fade" id="details">\n'+
      
       '<label for="TestEmail">Test Email ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="TestEmail" class="form-control autocomplete" readonly value="some@example.com">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="TestMobile">Test Mobile No</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="TestMobile" class="form-control autocomplete" readonly value="+91 3434 34343">\n'+
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