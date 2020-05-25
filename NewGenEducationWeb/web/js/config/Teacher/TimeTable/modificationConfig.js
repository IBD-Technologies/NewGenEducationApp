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
                                 
                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="id" class="form-control autocomplete" placeholder="Enter Teacher ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
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
                                                            '<th>Name</th>\n'+
                                                            '<th>ID</th>\n'+
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
                                                            '<td>Teacher1</td>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Teacher Time Table detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<p class="col-orange"><i class="material-icons">help_outline</i>Please edit the details which you want to modify and press submit to modify the updated details for Teacher Time Table </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<label for="name">Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="name" class="form-control autocomplete"  value="Teacher4">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="id" class="form-control autocomplete"  value="Teacher4">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                 '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#Mon" data-toggle="tab">Mon</a></li>\n'+
  '<li role="presentation"><a href="#Tue" data-toggle="tab">Tue</a></li>\n'+
  '<li role="presentation"><a href="#Wed" data-toggle="tab">Wed</a></li>\n'+
  '<li role="presentation"><a href="#Thu" data-toggle="tab">Thu</a></li>\n'+
  '<li role="presentation"><a href="#Fri" data-toggle="tab">Fri</a></li>\n'+
  '<li role="presentation"><a href="#Audit" data-toggle="tab"><b>Audit</b></a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// Mon start
    '<div role="tabpanel" class="tab-pane fade in active" id="Mon">\n'+
                                 
                                  '<div class="header familHeader">\n'+
                                  '<div class="row">\n'+
                                  '<div class="col-sm-6 col-xs-6">\n'+
                                  '<h2><button type="button" class="btn btn-default font-bold waves-effect">0</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">Of</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">1</button>\n'+
                                  '</h2>\n'+
                                  '</div>\n'+
                                  '<div class="header-dropdown m-r--5">\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">chevron_right</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">add</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">remove</i></button>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+


                                  '<label for="Class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Fee Type--</option>\n'+
                                  '<option selected value="Male">IX/A</option>\n'+
                                  '<option  value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option  value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option  value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                 
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="09:30">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>End Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="10:15">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+            

    '</div>\n'+
// Tue start

    '<div role="tabpanel" class="tab-pane fade" id="Tue">\n'+
    
                                  '<div class="header familHeader">\n'+
                                  '<div class="row">\n'+
                                  '<div class="col-sm-6 col-xs-6">\n'+
                                  '<h2><button type="button" class="btn btn-default font-bold waves-effect">0</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">Of</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">1</button>\n'+
                                  '</h2>\n'+
                                  '</div>\n'+
                                  '<div class="header-dropdown m-r--5">\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">chevron_right</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">add</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">remove</i></button>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+


                                  '<label for="Class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Fee Type--</option>\n'+
                                  '<option selected value="Male">IX/A</option>\n'+
                                  '<option  value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option  value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option  value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                 
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="09:30">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>End Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="10:15">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

    '</div>\n'+
    // Wed start
    '<div role="tabpanel" class="tab-pane fade" id="Wed">\n'+
              '<div class="header familHeader">\n'+
                                  '<div class="row">\n'+
                                  '<div class="col-sm-6 col-xs-6">\n'+
                                  '<h2><button type="button" class="btn btn-default font-bold waves-effect">0</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">Of</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">1</button>\n'+
                                  '</h2>\n'+
                                  '</div>\n'+
                                  '<div class="header-dropdown m-r--5">\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">chevron_right</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">add</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">remove</i></button>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+


                                  '<label for="Class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Fee Type--</option>\n'+
                                  '<option selected value="Male">IX/A</option>\n'+
                                  '<option  value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option  value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option  value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                 
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="09:30">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>End Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="10:15">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

    '</div>\n'+
    // Thu start
    '<div role="tabpanel" class="tab-pane fade" id="Thu">\n'+
              '<div class="header familHeader">\n'+
                                  '<div class="row">\n'+
                                  '<div class="col-sm-6 col-xs-6">\n'+
                                  '<h2><button type="button" class="btn btn-default font-bold waves-effect">0</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">Of</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">1</button>\n'+
                                  '</h2>\n'+
                                  '</div>\n'+
                                  '<div class="header-dropdown m-r--5">\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">chevron_right</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">add</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">remove</i></button>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+


                                  '<label for="Class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Fee Type--</option>\n'+
                                  '<option selected value="Male">IX/A</option>\n'+
                                  '<option  value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option  value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option  value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                 
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="09:30">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>End Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="10:15">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

    '</div>\n'+
    // Fri start
    '<div role="tabpanel" class="tab-pane fade" id="Fri">\n'+
                                  '<div class="header familHeader">\n'+
                                  '<div class="row">\n'+
                                  '<div class="col-sm-6 col-xs-6">\n'+
                                  '<h2><button type="button" class="btn btn-default font-bold waves-effect">0</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">Of</button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect">1</button>\n'+
                                  '</h2>\n'+
                                  '</div>\n'+
                                  '<div class="header-dropdown m-r--5">\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">chevron_right</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">add</i></button>\n'+
                                  '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">remove</i></button>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+


                                  '<label for="Class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="Class" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Fee Type--</option>\n'+
                                  '<option selected value="Male">IX/A</option>\n'+
                                  '<option  value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option  value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option  value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                 
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="09:30">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>End Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"   value="10:15">\n'+
                                  '</div>\n'+
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
                                    '<a href="javascript:void(0);" tabindex="7" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }