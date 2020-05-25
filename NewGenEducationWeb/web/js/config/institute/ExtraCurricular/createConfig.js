var createConfig={Operation:'create',
                                startStep : 1,
                                endStep:3,
                                step: 
                                [{stepNumber:1,stepperLabel:'Select Operation',
                                triggerElement:stepperOneTemplate},
                                {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Enter Data To craete Activity</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter all fields to create Activity and press next </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  
                                  '<label for="ActivityID">Activity ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="ActivityID" class="form-control autocomplete" placeholder="Enter Activity ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="ActivityDescription">Activity Description</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="ActivityDescription" class="form-control autocomplete" placeholder="Enter Activity Description">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="ActivityType">Activity Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="ActivityType" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Activity Type--</option>\n'+
                                  '<option value="Male">Sports</option>\n'+
                                  '<option value="Male">Cultures</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="AssigneeGroup">Assignee Group</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="AssigneeGroup" class="form-control autocomplete" placeholder="Enter Assignee Group">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<center><h4>Others</h4></center>\n'+

                                  '<label for="bs_datepicker_container">Event Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="5"  type="text"  class="form-control LastItem" placeholder="Enter Event Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Participation Due Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="6"  type="text"  class="form-control LastItem" placeholder="Enter Participation Due Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+ 

                                  '<label for="Level">Level</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="Level" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Level--</option>\n'+
                                  '<option value="Male">Internal</option>\n'+
                                  '<option value="Male">State</option>\n'+
                                  '<option value="Male">District</option>\n'+
                                  '<option value="Male">International</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Venue">Venue</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="8" type="text" id="Venue" class="form-control autocomplete LastItem" placeholder="Enter Venuesh">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  
                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="9" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                '</div>'
                              },
                                {stepNumber:3,stepperLabel:'',
                                triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
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