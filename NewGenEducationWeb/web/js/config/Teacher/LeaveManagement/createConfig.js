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
                                  '<h2 class="font-bold col-blue">Step 2: Enter Data To create Teacher Leave Management</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter all fields to create Teacher Leave Management and press next </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                    

                                  '<label for="name">Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="name" class="form-control autocomplete" placeholder="Enter Teacher Name">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="id" class="form-control autocomplete" placeholder="Enter Teacher ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<label for="bs_datepicker_container">From</label>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="3"  type="text"  class="form-control" placeholder="Enter From Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<label for="time1">Time</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="time1" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose option--</option>\n'+
                                  '<option value="1">HalfDay-FN</option>\n'+
                                  '<option value="1">FullDay</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<label for="bs_datepicker_container">To</label>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="5"  type="text"  class="form-control" placeholder="Enter To Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<label for="time2">Time</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="6" id="time2" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose option--</option>\n'+
                                  '<option value="1">HalfDay-FN</option>\n'+
                                  '<option value="1">FullDay</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                   '<label for="leaveType">Leave Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose option--</option>\n'+
                                  '<option value="1">Sick</option>\n'+
                                  '<option value="1">Planned</option>\n'+
                                  '<option value="1">Casual</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="reason">Reason</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<textarea tabindex="8" rows="2" id="reason" class="form-control resize" placeholder="Enter reason for leave"></textarea>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="ApprovedStatus">Approved Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="9" id="ApprovedStatus" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose option--</option>\n'+
                                  '<option value="1">Authorized</option>\n'+
                                  '<option value="1">Unauthorized</option>\n'+
                                  '<option value="1">Rejected</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="Reamarks">Reamarks</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<textarea tabindex="10" rows="2" id="Reamarks" class="form-control resize LastItem" placeholder="Enter Reamarks"></textarea>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="11" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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