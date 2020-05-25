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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter Filter criteria and click Generate to generate Student Register report</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+


                                   '<label for="class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Select Class--</option>\n'+
                                  '<option value="Male">IX/A</option>\n'+
                                  '<option value="Male">IX/B</option>\n'+
                                  '<option value="Male">IX/C</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Status">Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="Status" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Select Status--</option>\n'+
                                  '<option value="Male">Studying</option>\n'+
                                  '<option value="Male">Studied</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">From Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4"  type="text"  class="form-control LastItem" placeholder="Enter Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">To Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="5"  type="text"  class="form-control LastItem" placeholder="Enter Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="6" class="waves-effect">Generate <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 2: Generated Student Register report</h2>\n'+
                                 '<br>\n'+
                                   '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully Generated the report.Please view the report\n'+
                                    '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<center><h4>Generated Report</h4></center>\n'+
                                  '<embed src="mockup.pdf" id="ReportPdf" type="application/pdf" />\n'+

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