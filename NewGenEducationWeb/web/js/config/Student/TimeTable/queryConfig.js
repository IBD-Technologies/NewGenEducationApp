var queryConfig={Operation:'query',
                                startStep : 1,
                                endStep:2,
                                step: 
                                [{stepNumber:1,stepperLabel:'',
                                triggerElement:'<div id="test-l-1" role="tabpanel" class="bs-stepper-pane active" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 1: Enter Filter Criteria</h2>\n'+
                                  '<br>\n'+
                                 
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter student name in the filter criteria </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  
                                  '<label for="StudentName">Student Name</label>\n'+
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

                                  '<label for="studentId">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="studentId" class="form-control" placeholder="Enter student id ">\n'+
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
                              {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Time Table detailed view</h2>\n'+
                                 '<br>\n'+
                                   '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please view the result for choosen Student Time Table details\n'+
                                    '</div>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  
                                  '<label for="StudentName">Student Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="StudentName" class="form-control autocomplete" readonly value="Issac">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="studentId">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="studentId" class="form-control" readonly value="Student42">\n'+
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

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option disabled value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                   '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option disabled value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="teacherId">Staff Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="teacherId" class="form-control autocomplete" readonly value="Teacher3">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"  readonly value="09:30">\n'+
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
                                  '<input type="text" class="form-control time12"  readonly value="10:15">\n'+
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

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option disabled value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                   '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option disabled value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="teacherId">Staff Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="teacherId" class="form-control autocomplete" readonly value="Teacher3">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"  readonly value="09:30">\n'+
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
                                  '<input type="text" class="form-control time12"  readonly value="10:15">\n'+
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

                                 '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option disabled value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                   '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option disabled value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="teacherId">Staff Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="teacherId" class="form-control autocomplete" readonly value="Teacher3">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"  readonly value="09:30">\n'+
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
                                  '<input type="text" class="form-control time12"  readonly value="10:15">\n'+
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

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option disabled value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                   '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option disabled value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="teacherId">Staff Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="teacherId" class="form-control autocomplete" readonly value="Teacher3">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"  readonly value="09:30">\n'+
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
                                  '<input type="text" class="form-control time12"  readonly value="10:15">\n'+
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

                                  '<label for="periodNumber">Period Number</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="1" id="periodNumber" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Period Number--</option>\n'+
                                  '<option selected value="1">1</option>\n'+
                                  '<option disabled value="2">2</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                   '<label for="Subject">Subject</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="Subject" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose Subject--</option>\n'+
                                  '<option selected value="1">Tamil</option>\n'+
                                  '<option disabled value="2">English</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="teacherId">Staff Name</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="teacherId" class="form-control autocomplete" readonly value="Teacher3">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">\n'+
                                  '<b>Start Time</b>\n'+
                                  '<div class="input-group">\n'+
                                  '<span class="input-group-addon">\n'+
                                  '<i class="material-icons">access_time</i>\n'+
                                  '</span>\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" class="form-control time12"  readonly value="09:30">\n'+
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
                                  '<input type="text" class="form-control time12"  readonly value="10:15">\n'+
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
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                '</div>'
                              }
                                ]
                              }