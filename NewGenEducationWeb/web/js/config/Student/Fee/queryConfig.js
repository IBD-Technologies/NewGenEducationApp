var queryConfig={Operation:'query',
                                startStep : 1,
                                endStep:3,
                                step: 
                                [
                                {stepNumber:1,stepperLabel:'',
                                triggerElement:'<div id="test-l-1" role="tabpanel" class="bs-stepper-pane active" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 1: Enter Filter Criteria</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must choose filter criteria for Student Fee which you want to query</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                 '<label for="StudentName">Name</label>\n'+
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

                                  '<label for="exam">Fee Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">Tution</option>\n'+
                                  '<option value="Male">Transport</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="exam">Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">Pending</option>\n'+
                                  '<option value="Male">Overdue</option>\n'+
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
                              {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Choose the record</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to view the result.</p>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                        '<th>Fee ID</th>\n'+
                                                        '<th>Due Date(DD/MM/YYYY)</th>\n'+
                                                        '<th>Fee Amount</th>\n'+
                                                        '<th>Paid Amount</th>\n'+
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
                                                            '<td>F19200012</td>\n'+
                                                            '<td>20-01-2020</td>\n'+
                                                            '<td>1000.0</td>\n'+
                                                            '<td>0.0</td>\n'+
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
                              {stepNumber:3,stepperLabel:'',
                                triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 3: Video Assignment detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please view the result for chosen Student Fee details\n'+
                                    '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
  '<li role="presentation" ><a href="#Amount" data-toggle="tab">Amount</a></li>\n'+
  '<li role="presentation" ><a href="#Dates" data-toggle="tab">Dates</a></li>\n'+
  '<li role="presentation" ><a href="#Others" data-toggle="tab">Others</a></li>\n'+
  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// general content start
    '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+

                                  '<label for="name">Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="name" class="form-control autocomplete" readonly value="Devi K">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="id" class="form-control autocomplete" readonly value="Student1">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="FeeID">Fee ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="FeeID" class="form-control autocomplete" readonly value="A13200001">\n'+
                                  '</div>\n'+
                                  '</div>\n'+                             

    '</div>\n'+
    // video content start
    '<div role="tabpanel" class="tab-pane fade" id="Amount">\n'+
                                 
                                 '<label for="amount">Amount</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="amount" class="form-control autocomplete" readonly value="1,000.00">\n'+
                                  '</div>\n'+
                                  '</div>\n'+  

                                  '<label for="totalAmount">Total Amount Paid</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="totalAmount" class="form-control autocomplete" readonly value="0.00">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="balanceAmount">Balance Amount</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="balanceAmount" class="form-control autocomplete" readonly value="1,000.00">\n'+
                                  '</div>\n'+
                                  '</div>\n'+   



    '</div>\n'+

    '<div role="tabpanel" class="tab-pane fade" id="Dates">\n'+
                                  '<label for="bs_datepicker_container">Due Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4"  type="text"  class="form-control LastItem" readonly value="18-02-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">Paid Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4"  type="text"  class="form-control LastItem" readonly value="18-02-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
      '</div>\n'+

      '<div role="tabpanel" class="tab-pane fade" id="Others">\n'+
                                  '<label for="Status">Status</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="Status" class="form-control autocomplete" readonly value="Student1">\n'+
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
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }