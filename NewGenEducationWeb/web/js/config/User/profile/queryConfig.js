var queryConfig={Operation:'query',
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
                                 
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter user name in the filter criteria </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="StudentName">User Name</label>\n'+
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


                                  '<label for="authStatus">Auth status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="authStatus" class="form-control form-line show-tick LastItem">\n'+
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
                                  '<h2 class="font-bold col-blue">Step 3: User profile results based on given filter criteria</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please either double click or choose the record and press next button to view the User full profile details</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<div class="body">\n'+
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
                                                            '<td>R.Anitha</td>\n'+
                                                            '<td>9566205415</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: User Profile detailed view</h2>\n'+
                                  '<br>\n'+
                                   '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please click corresponding tabs to view User Profile details.\n'+
                                    '</div>\n'+
                                  '</div>\n'+
                                  

                                  
                                  '<!-- Nav tabs -->\n'+
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#General" data-toggle="tab">General</a></li>\n'+
                                  '<li role="presentation"><a href="#Parent" data-toggle="tab">Parent</a></li>\n'+
                                  '<li role="presentation"><a href="#Class" data-toggle="tab">Class</a></li>\n'+
                                  '<li role="presentation"><a href="#Teacher" data-toggle="tab">Teacher</a></li>\n'+
                                  '<li role="presentation"><a href="#Institute" data-toggle="tab">Institute</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  
                                  
                                  '</ul>\n'+

                                  '<!-- Tab panes -->\n'+
                                  '<div class="tab-content">\n'+

                                  // General start
                                  '<div role="tabpanel" class="tab-pane fade in active" id="General">\n'+
                                  
                                      '<label for="UserName">User Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="student_name" class="form-control" readonly value="R.Anitha">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="UserId">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="UserId" class="form-control" readonly value="9566205415">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Password">Password</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="password" id="Password" class="form-control" readonly value="fhdjkffhjksdf">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="bs_datepicker_container">Expiry Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4" type="text"  class="form-control" readonly value="21-01-2090">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                 

                                  '<label for="UserType">User Type</label>\n'+
                                  '<div class="form-group">\n'+
                                   '<div class="form-line">\n'+
                                  '<select tabindex="5" id="UserType" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">--Choose User Type--</option>\n'+
                                  '<option selected value="Male">Admin</option>\n'+
                                  '<option disabled value="Male">Teacher</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="HomeInstitute">Home Institute</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="6" type="text" id="HomeInstitute" class="form-control" readonly value="ABC Matriculation School">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="Status">Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="Status" class="form-control form-line show-tick">\n'+
                                  '<option disabled value="">-- Select Status --</option>\n'+
                                  '<option selected value="Male">Enable</option>\n'+
                                  '<option disabled value="Femaile">Disable</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Name">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="8" type="text" id="HomeInstitute" class="form-control" readonly>\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '</div>\n'+
                                  // parent start
                                  '<div role="tabpanel" class="tab-pane fade" id="Parent">\n'+
                                      '<center><h4>Parent Student Role Mapping</h4></center>\n'+
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

                                  '<label for="roleId">Role ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="roleId" class="form-control" readonly value="PARENT">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="studentName">Student Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="studentName" class="form-control" readonly value="Student1">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="studentId">Student ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="studentId" class="form-control" readonly value="Student2">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="InstituteName">Institute Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="InstituteName" class="form-control" readonly value="ABC Matriculation School">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '</div>\n'+


                                  // Teacher start
                                  '<div role="tabpanel" class="tab-pane fade" id="Teacher">\n'+
                                  '<br>\n'+



                                  '</div>\n'+
                                  // Class start
                                  '<div role="tabpanel" class="tab-pane fade" id="Class">\n'+
                                   
                                  
                                  '</div>\n'+
                                  // Institute start
                                  '<div role="tabpanel" class="tab-pane fade" id="Institute">\n'+
                                   
                                  
                                  '</div>\n'+
                                  // Audit start
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