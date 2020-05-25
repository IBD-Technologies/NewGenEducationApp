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

                                  '<label for="StudentName">Role ID</label>\n'+
                                  '<div class="form-group input-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" onkeyup="searchFilter(id);" id="StudentName" class="form-control autocomplete" placeholder="Enter student role id to search">\n'+
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
                                                            '<th>Role ID</th>\n'+
                                                            '<th>Description</th>\n'+
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
                                                            '<td>TEACHER_C</td>\n'+
                                                            '<td>Teacher Create Role</td>\n'+
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
                                  '<li role="presentation"><a href="#Role" data-toggle="tab">User Role</a></li>\n'+
                                  '<li role="presentation"><a href="#Description" data-toggle="tab">Description</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  
                                  
                                  '</ul>\n'+

                                  '<!-- Tab panes -->\n'+
                                  '<div class="tab-content">\n'+

                                  // General start
                                  '<div role="tabpanel" class="tab-pane fade in active" id="General">\n'+
                                  
                                   '<label for="instituteName">Institute Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="instituteName" class="form-control" readonly value="ABC Matriculation School">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="InstituteId">Institute ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="InstituteId" class="form-control" readonly value="I6200000">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="roleID">Role ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="roleID" class="form-control" readonly value="TEACHER_C">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="roleDesc">Role Description</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="roleDesc" class="form-control" readonly value="Teacher Create Role">\n'+
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


              // Role start
              '<div role="tabpanel" class="tab-pane fade" id="Role">\n'+

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


                                 
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Function ID</th>\n'+
                                                            '<th>C</th>\n'+
                                                            '<th>V</th>\n'+
                                                            '<th>M</th>\n'+
                                                            '<th>R</th>\n'+
                                                            '<th>D</th>\n'+
                                                            '<th>A</th>\n'+
                                                            '<th>AA</th>\n'+
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
                                                            '<td>\n'+
                                                            '<div class="input-group input-group-lg">\n'+
                                                            '<select tabindex="3" id="Subject" class="form-control form-line show-tick">\n'+
                                                            '<option value="">--Select Option--</option>\n'+
                                                            '<option value="1">All</option>\n'+
                                                            '<option value="1">ClassAttendance</option>\n'+
                                                            '</select>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_2" />\n'+
                                                            '<label for="basic_checkbox_2"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_3" />\n'+
                                                            '<label for="basic_checkbox_3"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_4" />\n'+
                                                            '<label for="basic_checkbox_4"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_5" />\n'+
                                                            '<label for="basic_checkbox_5"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" checked readonly id="basic_checkbox_6" />\n'+
                                                            '<label for="basic_checkbox_6"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_7" />\n'+
                                                            '<label for="basic_checkbox_7"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_8" />\n'+
                                                            '<label for="basic_checkbox_8"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+   


               '</div>\n'+

               // Description start
              '<div role="tabpanel" class="tab-pane fade" id="Description">\n'+
                                        '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                            '<th>Label</th>\n'+
                                                            '<th>Description</th>\n'+
                                                        '</tr>\n'+
                                                    '</thead>\n'+
                                                    '<tbody>\n'+
                                                        '<tr>\n'+
                                                            '<td>C</td>\n'+
                                                            '<td>Create</td>\n'+
                                                         '</tr>\n'+
                                                         '<tr>\n'+
                                                            '<td>M</td>\n'+
                                                            '<td>Modify</td>\n'+
                                                         '</tr>\n'+
                                                         '<tr>\n'+
                                                            '<td>D</td>\n'+
                                                            '<td>Delete</td>\n'+
                                                         '</tr>\n'+
                                                         '<tr>\n'+
                                                            '<td>V</td>\n'+
                                                            '<td>View</td>\n'+
                                                         '</tr>\n'+
                                                         '<tr>\n'+
                                                            '<td>AA</td>\n'+
                                                            '<td>Auto Authorization</td>\n'+
                                                         '</tr>\n'+
                                                         '<tr>\n'+
                                                            '<td>A</td>\n'+
                                                            '<td>Authorisation</td>\n'+
                                                         '</tr>\n'+
                                                         '<tr>\n'+
                                                            '<td>R</td>\n'+
                                                            '<td>Reject</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+


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