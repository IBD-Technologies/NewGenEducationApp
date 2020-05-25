var instituteDeletionConfig={Operation:'deletion',
                                startStep : 1,
                                endStep:3,
                                step: 
                                [{stepNumber:1,stepperLabel:'Select Operation',
                                triggerElement:stepperOneTemplate},
                                {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Enter Filter Criteria for deletion</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter either Institute name in the filter criteria </p>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 3: Institute detailed view</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please click corresponding tabs to view Institute General, Exam, Grade, Subject, Notification, Fee,  and Audit details respectively and press delete.</p>\n'+
                                  '</div>\n'+
                                  

                                  '<div class="body">\n'+
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
                                  '<input type="text" id="student_name" class="form-control" value="ABC School" readonly >\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="id">Institute ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="id" class="form-control" value="123" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+



                                  '<label for="BSbtninfo">Institute Logo</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input id="BSbtninfo" class="form-control" type="file" disabled value="abc.png" readonly  /> \n'+
                                  '<lable>abc.png</lable>\n'+
                                  '</div>\n'+
                                  
                                  '</div>\n'+




                                  '</div>\n'+

                                  // exam start
                                  '<div role="tabpanel" class="tab-pane fade" id="exam">\n'+
                                  '<center><h4>Exam Configuration</h4></center>\n'+
                                  '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
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
                                                            '<td>Half</td>\n'+
                                                            '<td>Half yearly examination</td>\n'+
                                                            '<td>அரையாண்டு தேர்வு</td>\n'+
                                                         '</tr>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+

                                                            '<div class="form-group">\n'+
                                '<input type="checkbox" id="basic_checkbox_2"  />\n'+
                                '<label for="basic_checkbox_2"></label>\n'+
                                 '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>Annual</td>\n'+
                                                            '<td>Annual examination</td>\n'+
                                                            '<td>முழு ஆண்டு தேர்வு</td>\n'+
                                                            
                                                        '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            '</div>\n'+ 

                                  '</div>\n'+
                                  
                                  // grade start
                                  '<div role="tabpanel" class="tab-pane fade" id="grade">\n'+
                                  '<center><h4>Grade Configuration</h4></center>\n'+
                                  '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
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
                                '<input type="checkbox" id="basic_checkbox_1" />\n'+
                                '<label for="basic_checkbox_1"></label>\n'+
                                 '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>91</td>\n'+
                                                            '<td>100</td>\n'+
                                                            '<td>A</td>\n'+
                                                         '</tr>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+

                                                            '<div class="form-group">\n'+
                                '<input type="checkbox" id="basic_checkbox_2"  />\n'+
                                '<label for="basic_checkbox_2"></label>\n'+
                                 '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>81</td>\n'+
                                                            '<td>90</td>\n'+
                                                            '<td>B</td>\n'+
                                                            
                                                        '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            '</div>\n'+ 

                                  '</div>\n'+
                                  // subject start
                                  '<div role="tabpanel" class="tab-pane fade" id="subject">\n'+
                                  '<center><h4>Subject Configuration</h4></center>\n'+
                                  '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>ID</th>\n'+
                                                            '<th>Name</th>\n'+
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
                                                            '<td>Tam</td>\n'+
                                                            '<td>Tamil</td>\n'+
                                                            '<td>தமிழ்</td>\n'+
                                                         '</tr>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+

                                                            '<div class="form-group">\n'+
                                '<input type="checkbox" id="basic_checkbox_2"  />\n'+
                                '<label for="basic_checkbox_2"></label>\n'+
                                 '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>Eng</td>\n'+
                                                            '<td>English</td>\n'+
                                                            '<td>ஆங்கிலம்</td>\n'+
                                                            
                                                        '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            '</div>\n'+ 
                                  
                                  '</div>\n'+

                                  // notification start
                                  '<div role="tabpanel" class="tab-pane fade" id="notification">\n'+
                                   '<center><h4>Notification Configuration</h4></center>\n'+

                                   '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
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
                                                            '<td>Homework</td>\n'+
                                                            '<td>Homework</td>\n'+
                                                            '<td>வீட்டுபாடம்</td>\n'+
                                                         '</tr>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+

                                                            '<div class="form-group">\n'+
                                '<input type="checkbox" id="basic_checkbox_2"  />\n'+
                                '<label for="basic_checkbox_2"></label>\n'+
                                 '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>Emergency</td>\n'+
                                                            '<td>Emergency</td>\n'+
                                                            '<td>அவசரம்</td>\n'+
                                                            
                                                        '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            '</div>\n'+ 
                                   
                                  '</div>\n'+

                                   // fee start
                                  '<div role="tabpanel" class="tab-pane fade" id="fee">\n'+
                                   
                                  '<center><h4>Fee Configuration</h4></center>\n'+
                                   '<div class="body">\n'+
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
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
                                                            '<td>Tution</td>\n'+
                                                            '<td>Tution fee</td>\n'+
                                                            '<td>கல்வி</td>\n'+
                                                         '</tr>\n'+
                                                        '<tr>\n'+
                                                            '<td>\n'+

                                                            '<div class="form-group">\n'+
                                '<input type="checkbox" id="basic_checkbox_2"  />\n'+
                                '<label for="basic_checkbox_2"></label>\n'+
                                 '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>Transport</td>\n'+
                                                            '<td>Transport fee</td>\n'+
                                                            '<td>போக்குவரத்து</td>\n'+
                                                            
                                                        '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+
                                            '</div>\n'+
                                   
                                  '</div>\n'+

                                  '<div role="tabpanel" class="tab-pane fade" id="Audit">\n'+
                                  
                                  auditCardsTemplate+
                                  '</div>\n'+




                                  '</div>\n'+
                                  '</div>\n'+


                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect">Delete <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }