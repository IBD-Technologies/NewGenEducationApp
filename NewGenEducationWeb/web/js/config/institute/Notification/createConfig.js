var createConfig={Operation:'create',
                                startStep : 1,
                                endStep:4,
                                step: 
                                [{stepNumber:1,stepperLabel:'Select Operation',
                                triggerElement:stepperOneTemplate},
                                {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Enter Data To craete Notification</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter all fields to create notification and press next </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  
                                  '<label for="NotificationID">Notification ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="NotificationID" class="form-control autocomplete" placeholder="Enter Notification ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="NotificationType">Notification Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="NotificationType" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Notification Type--</option>\n'+
                                  '<option value="Male">Homework</option>\n'+
                                  '<option value="Male">Event</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Assignee">Assignee</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="Assignee" class="form-control autocomplete" placeholder="Enter Assignee">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Mode">Mode</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="Mode" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Mode--</option>\n'+
                                  '<option value="Male">Both</option>\n'+
                                  '<option value="Male">One</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<center><h4>Message</h4></center>\n'+

                                  '<label for="InEnglish">In English</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="5" type="text" id="InEnglish" class="form-control autocomplete" placeholder="Enter message in English">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="OtherLanguage">In Other Language</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="6" type="text" id="OtherLanguage" class="form-control autocomplete" placeholder="Enter message In Other Language">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="bs_datepicker_container">Delivery Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="7"  type="text"  class="form-control LastItem" placeholder="Enter Delivery Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+                               

                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="8" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 3: Test Notification</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter Test Email ID or Test Mobile No fields to test notification and press submit </p>\n'+
                                  '</div>\n'+

                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="TestEmail">Test Email ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="TestEmail" class="form-control autocomplete" placeholder="Enter Email to test message">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="TestMobile">Test Mobile No</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="TestMobile" class="form-control autocomplete" placeholder="Enter Mobile to test message">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
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
                                {stepNumber:4,stepperLabel:'',
                                triggerElement:'<div id="test-l-4" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
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