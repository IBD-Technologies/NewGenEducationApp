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
                                  '<h2 class="font-bold col-blue">Step 2: Enter Data To craete eCircular</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter all fields to create eCircular and press next </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  
                                  '<label for="CircularID">Circular ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="CircularID" class="form-control autocomplete" placeholder="Enter Circular ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Description">Description</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="Description" class="form-control autocomplete" placeholder="Enter eCircular Description">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="CircularType">Circular Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="CircularType" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Circular Type--</option>\n'+
                                  '<option value="Male">Student</option>\n'+
                                  '<option value="Male">Teacher</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  
                                  '<label for="GroupID">Group ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="GroupID" class="form-control autocomplete" placeholder="Enter Group ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="5"  type="text"  class="form-control LastItem" placeholder="Enter Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="message">Message</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="6" type="text" id="message" class="form-control autocomplete" placeholder="Enter Message">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                 
                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="7" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 3: Add eCircular</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter the Video assignment YouTube URL to embed here </p>\n'+
                                  '</div>\n'+

                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="BSbtninfo">Upload Circular</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" id="BSbtninfo" class="form-control" type="file" onchange="uploadFile(this);" placeholder="Upload student photo" /> \n'+
                                  '</div>\n'+
                                  '<embed src="mockup.pdf" id="embedPdf" type="application/pdf" />\n'+
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