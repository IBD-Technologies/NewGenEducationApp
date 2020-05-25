var modificationConfig={Operation:'modification',
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter filter criteria for Teacher Time Table which you want to modify</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="StudentName">Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" onkeyup="searchFilter(id);" id="StudentName" class="form-control autocomplete" placeholder="Enter teacher name to search">\n'+
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
                                 
                                  '<label for="bs_datepicker_container">From Date</label>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="2"  type="text"  class="form-control" placeholder="Enter From Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">To Date</label>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="3"  type="text"  class="form-control" placeholder="Enter To Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Status">Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="Status" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose option--</option>\n'+
                                  '<option value="1">Pending</option>\n'+
                                  '<option value="1">Approved</option>\n'+
                                  '<option value="1">Rejected</option>\n'+
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
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to modify the result.</p>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Name</th>\n'+
                                                            '<th>From (DD/MM/YYYY)</th>\n'+
                                                            '<th>To (DD/MM/YYYY)</th>\n'+
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
                                                            '<td>Teacher1</td>\n'+
                                                            '<td>20-01-2020</td>\n'+
                                                            '<td>21-01-2020</td>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Leave Management detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<p class="col-orange"><i class="material-icons">help_outline</i>Please click corresponding tabs to Modify Teacher Leave Management and then press submit.</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  

                                 '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#General" data-toggle="tab">General</a></li>\n'+
  '<li role="presentation"><a href="#Audit" data-toggle="tab"><b>Audit</b></a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// Mon start
    '<div role="tabpanel" class="tab-pane fade in active" id="General">\n'+
                                  '<label for="name">Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="name" class="form-control autocomplete"  value="Teacher1">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="id" class="form-control autocomplete"  value="Teacher1">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<label for="bs_datepicker_container">From</label>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="3"  type="text"  class="form-control"  value="20-01-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<label for="time1">Time</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="4" id="time1" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose option--</option>\n'+
                                  '<option selected value="1">HalfDay-FN</option>\n'+
                                  '<option  value="1">FullDay</option>\n'+
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
                                  '<input tabindex="5"  type="text"  class="form-control"  value="22-01-2020">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<label for="time2">Time</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="6" id="time2" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose option--</option>\n'+
                                  '<option selected value="1">HalfDay-FN</option>\n'+
                                  '<option  value="1">FullDay</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                   '<label for="leaveType">Leave Type</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose option--</option>\n'+
                                  '<option selected value="1">Sick</option>\n'+
                                  '<option  value="1">Planned</option>\n'+
                                  '<option  value="1">Casual</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="reason">Reason</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<textarea tabindex="8" rows="2" id="reason" class="form-control resize" >Sick Leave</textarea>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="ApprovedStatus">Approved Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="9" id="ApprovedStatus" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose option--</option>\n'+
                                  '<option selected value="1">Authorized</option>\n'+
                                  '<option  value="1">Unauthorized</option>\n'+
                                  '<option  value="1">Rejected</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="Reamarks">Reamarks</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<textarea tabindex="10" rows="2" id="Reamarks" class="form-control resize LastItem" >none</textarea>\n'+
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
                                    '<a href="javascript:void(0);" tabindex="11" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }