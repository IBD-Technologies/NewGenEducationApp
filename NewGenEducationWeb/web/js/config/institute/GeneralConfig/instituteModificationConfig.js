var instituteModificationConfig={Operation:'modification',
                                startStep : 1,
                                endStep:3,
                                step: 
                                [{stepNumber:1,stepperLabel:'Select Operation',
                                triggerElement:stepperOneTemplate},
                                {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Enter Filter Criteria to modify institute</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter Institute name in the filter criteria </p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="StudentName">Institute Name</label>\n'+
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

                                  '<label for="studentId">Institute ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="studentId" class="form-control" placeholder="Enter student id ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<div class="container-fluid">\n'+
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
                                    // container fluid
                                   '</div>\n'+
                                '</div>'
                              },
                              {stepNumber:3,stepperLabel:'',
                                triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 3: Institute detailed view</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please click corresponding tabs to modify Institute General, Exam, Grade, Subject, Notification, Fee,  and Audit details respectively and press submit.</p>\n'+
                                  '</div>\n'+
                                  

                                  
                                  '<!-- Nav tabs -->\n'+
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
                                  '<li role="presentation"><a href="#exam" data-toggle="tab">Exam</a></li>\n'+
                                  '<li role="presentation"><a href="#grade" data-toggle="tab">Grade</a></li>\n'+
                                  '<li role="presentation"><a href="#subject" data-toggle="tab">Subject</a></li>\n'+
                                  '<li role="presentation"><a href="#notification" data-toggle="tab">Notification</a></li>\n'+
                                  '<li role="presentation"><a href="#fee" data-toggle="tab">Fee</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  '</ul>\n'+

                                  '<!-- Tab panes -->\n'+
                                  '<div class="tab-content">\n'+


                                  '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+
                                  
                                  '<center><img class="queryPreviewImage" id="ShowImage" src="images/abc.png" /></center>\n'+
                                  '<label for="student_name">Institute Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="student_name" class="form-control" value="ABC School"  >\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="id">Institute ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="id" class="form-control" value="123"  ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+



                                  '<label for="BSbtninfo">Institute Logo</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" id="BSbtninfo" class="form-control" type="file" value="abc.png"   /> \n'+
                                  '<lable>abc.png</lable>\n'+
                                  '</div>\n'+
                                  
                                  '</div>\n'+




                                  '</div>\n'+

                                  // exam start
                                  '<div role="tabpanel" class="tab-pane fade" id="exam">\n'+
                                  '<center><h4>Exam Configuration</h4></center>\n'+
                                  
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
                                                            '<th>Type</th>\n'+
                                                            '<th>Description</th>\n'+
                                                            '<th>Other Language Description</th>\n'+
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
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="HAlf">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Half yearly examination">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="அரையாண்டு தேர்வு" >\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                          

                                  '</div>\n'+
                                  
                                  // grade start
                                  '<div role="tabpanel" class="tab-pane fade" id="grade">\n'+
                                  '<center><h4>Grade Configuration</h4></center>\n'+
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
                                  // '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>From</th>\n'+
                                                            '<th>To</th>\n'+
                                                            '<th>Grade</th>\n'+
                                                        '</tr>\n'+
                                                    '</thead>\n'+
                                                    '<tbody>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_2" />\n'+
                                                            '<label for="basic_checkbox_2"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="91">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="100">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="A" >\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            // '</div>\n'+ 


                                  '</div>\n'+
                                  // subject start
                                  '<div role="tabpanel" class="tab-pane fade" id="subject">\n'+
                                  '<center><h4>Subject Configuration</h4></center>\n'+
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
                                  // '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>ID</th>\n'+
                                                            '<th>Name</th>\n'+
                                                            '<th>Other Language</th>\n'+
                                                        '</tr>\n'+
                                                    '</thead>\n'+
                                                    '<tbody>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_3" />\n'+
                                                            '<label for="basic_checkbox_3"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Tam">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Tamil">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="தமிழ்" >\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            // '</div>\n'+ 
                                  
                                  '</div>\n'+

                                  // notification start
                                  '<div role="tabpanel" class="tab-pane fade" id="notification">\n'+
                                   '<center><h4>Notification Configuration</h4></center>\n'+
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
                                  // '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Type</th>\n'+
                                                            '<th>Description</th>\n'+
                                                            '<th>Other Language Description</th>\n'+
                                                        '</tr>\n'+
                                                    '</thead>\n'+
                                                    '<tbody>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_4" />\n'+
                                                            '<label for="basic_checkbox_4"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Homework">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Homework">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="வீட்டுபாடம்" >\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            // '</div>\n'+ 
                                   
                                  '</div>\n'+

                                   // fee start
                                  '<div role="tabpanel" class="tab-pane fade" id="fee">\n'+
                                  '<center><h4>Fee Configuration</h4></center>\n'+
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
                                  // '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Type</th>\n'+
                                                            '<th>Description</th>\n'+
                                                            '<th>Other Language Description</th>\n'+
                                                        '</tr>\n'+
                                                    '</thead>\n'+
                                                    '<tbody>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<input type="checkbox" id="basic_checkbox_5" />\n'+
                                                            '<label for="basic_checkbox_5"></label>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Tution">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="Tution fee">\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>\n'+
                                                            '<div class="form-group">\n'+
                                                            '<div class="form-line">\n'+
                                                            '<input type="text" id="id" class="form-control" value="கல்வி" >\n'+
                                                            '</div>\n'+
                                                            '</div>\n'+
                                                            '</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            // '</div>\n'+ 
                                   
                                  '</div>\n'+

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
                                    '<a href="javascript:void(0);" tabindex="4" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }