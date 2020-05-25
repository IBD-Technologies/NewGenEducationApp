var deletionConfig={Operation:'deletion',
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose the Auth status for Fee which you want to query</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="paymentAmt">Payment Amount</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="paymentAmt" class="form-control autocomplete" placeholder="Enter Payment Amount">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="2"  type="text"  class="form-control LastItem" placeholder="Enter Date Of Birth in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="authStatus">Auth status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="authStatus" class="form-control form-line show-tick">\n'+
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
                                    '<a href="javascript:void(0);" tabindex="4" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                                            '<th>Student ID</th>\n'+
                                                            '<th>Student Name </th>\n'+
                                                            '<th>Payment Amount</th>\n'+
                                                            '<th>Payment Date</th>\n'+
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
                                                            '<td>Student20</td>\n'+
                                                            '<td>Niharika P</td>\n'+
                                                            '<td>1000.00</td>\n'+
                                                            '<td>28-04-2020</td>\n'+
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
                                    '<a href="javascript:void(0);" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Fee detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<p class="col-orange"><i class="material-icons">help_outline</i>Please view the detailed view for the fee payment and modify by clicking General and Details Tabs and press Delete.</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
  '<li role="presentation"><a href="#details" data-toggle="tab">Details</a></li>\n'+
  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// general content start
    '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+

        '<label for="paymentID">Payment ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="paymentID" class="form-control autocomplete" readonly value="P97200021">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="bs_datepicker_container">Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="2"  type="text"  class="form-control" readonly value="28-04-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="studentName">Student Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="studentName" class="form-control autocomplete" value="Niharika P">\n'+
                                  '</div>\n'+
                                  '</div>\n'+                                  

                                  '<label for="studentId">Student ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="studentId" class="form-control autocomplete" readonly value="Student20">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="amount">Payment Amount</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="5" type="text" id="amount" class="form-control autocomplete" readonly value=" 1,000.00">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="paymentMode">Payment Mode</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="6" id="paymentMode" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Fee Type--</option>\n'+
                                  '<option selected value="Male">Cash</option>\n'+
                                  '<option disabled value="Male">Transport</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  

                                  '<label for="balanceAmount">Balance Amount</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="7" type="text" id="balanceAmount" class="form-control autocomplete LastItem" readonly value="00.0">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

    '</div>\n'+
// details start
    '<div role="tabpanel" class="tab-pane fade" id="details">\n'+
      
      


                                 
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Description</th>\n'+
                                                            '<th>Due Date</th>\n'+
                                                            '<th>Due</th>\n'+
                                                            '<th>Payment Amount</th>\n'+
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
                                                           '<td>Admin Fee</td>\n'+
                                                            '<td>20-01-2020</td>\n'+
                                                            '<td>1,000.00</td>\n'+
                                                            '<td>1,000.00</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
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
                                    '<a href="javascript:void(0);" tabindex="8" class="waves-effect">Delete <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }