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
                                  '<h2 class="font-bold col-blue">Step 2: Search record for modification</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter teacher name in the filter criteria </p>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 3: Choose the record for modification</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please either double click or choose the record and press next button to Modify the teacher profile details</p>\n'+
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
                                '<input tabindex="1" type="checkbox" id="basic_checkbox_1" />\n'+
                                '<label for="basic_checkbox_1"></label>\n'+
                                 '</div>\n'+
                                                            '</td>\n'+
                                                            '<td>Teacher1</td>\n'+
                                                            '<td>1</td>\n'+
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
                                    '<a href="javascript:void(0);" tabindex="2" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Modify required teacher details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please click corresponding tabs to Modify teacher General, Family, Address and Other details respectively and then press submit.</p>\n'+
                                  '</div>\n'+
                                  

                                  
                                  '<!-- Nav tabs -->\n'+
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#home" data-toggle="tab">General</a></li>\n'+
                                  '<li role="presentation"><a href="#profile" data-toggle="tab">Family</a></li>\n'+
                                  '<li role="presentation"><a href="#settings" data-toggle="tab">Address</a></li>\n'+
                                  '<li role="presentation"><a href="#messages" data-toggle="tab">Others</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  '</ul>\n'+

                                  '<!-- Tab panes -->\n'+
                                  '<div class="tab-content">\n'+
                                  // General details start
                                  '<div role="tabpanel" class="tab-pane fade in active" id="home">\n'+
                                  
                                  '<label for="student_name">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="student_name" class="form-control" value="junaid tahir"  >\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="id" class="form-control" value="junaid tahir"  ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+



                                  '<label for="BSbtninfo">Upload Photo</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" id="BSbtninfo" class="form-control" type="file" src="images/tyty.png"   /> \n'+
                                  '</div>\n'+
                                  '<img class="queryPreviewImage" id="ShowImage" src="images/user-lg.jpg" />\n'+
                                  '</div>\n'+

                                  '<label for="qualification">Qualification</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="id" class="form-control" value="B.Ed" >\n'+
                                  '</div>\n'+
                                  '</div>\n'+



                                  '<label for="teacherClass">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="5" id="teacherClass" class="form-control form-line show-tick">\n'+
                                  '<option  value="">--Choose Class--</option>\n'+
                                  '<option selected value="Male">BS Information Technology</option>\n'+
                                  '<option   value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+


                                  '<label for="student_dob">DOB</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="6" type="text"  id="student_dob" class="form-control" value="09-10-1995"  >\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="student_gender">Gender</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="student_gender" class="form-control form-line show-tick">\n'+
                                  '<option  value="">-- Select Gender --</option>\n'+
                                  '<option selected value="Male">Male</option>\n'+
                                  '<option  value="Femaile">Femaile</option>\n'+
                                  '<option  value="Other">Other</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+




                                  '</div>\n'+
                                  // family details
                                  '<div role="tabpanel" class="tab-pane fade" id="profile">\n'+
                                  
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

                                  '<label for="family_name">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="family_name" class="form-control" value="Abdul Majeed">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="family_relation">Relationship</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="family_relation" class="form-control form-line show-tick">\n'+
                                  '<option value="">-- Select Relationship --</option>\n'+
                                  '<option selected value="">Father</option>\n'+
                                  '<option  value="">Mother</option>\n'+
                                  '<option  value="">Brother</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="email">E-Mail</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="email" class="form-control" value="issacmanoj1997@gmail.com">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                      

                                  '<label for="family_contact">Contact Number</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="6" type="text" id="family_contact" class="form-control" value="6382206492">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '</div>\n'+
                                  
                                  // other details
                                  '<div role="tabpanel" class="tab-pane fade" id="messages">\n'+
                                  '<br>\n'+

                                  '<label for="contact">Contact Number</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="contact" class="form-control" value="6382206492">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="mail">Mail</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="mail" class="form-control" value="issacmanoj1997@gmail.com">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<label for="blood_group">Blood Group</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                        '<select tabindex="3" id="blood_group" class="form-control form-line show-tick">\n'+
                                        '<option  value="">-- Select Blood Group --</option>\n'+
                                        '<option selected value="Male">A+</option>\n'+
                                        '<option  value="Femaile">B+</option>\n'+
                                        '<option  value="Other">A-</option>\n'+
                                    '</select>\n'+
                                    '</div>\n'+

                                    '<label for="medical_details">Existing Medical details</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<textarea tabindex="4" rows="2"cid="medical_details" class="form-control resize LastItem">No medical details</textarea>\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '</div>\n'+
                                  // address details
                                  '<div role="tabpanel" class="tab-pane fade" id="settings">\n'+
                                   
                                   '<label for="address_1">Address Line 1</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="address_1" class="form-control" value="Forest House, 4th Floor">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="address_2">Address Line 2</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="address_2" class="form-control" value="Forest House, 4th Floor16-20 Clements Road">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="address_3">Address Line 3</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="address_3" class="form-control" value="Ilford">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="address_4">Address Line 4</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4"  type="text" id="address_4" class="form-control LastItem" value="IG1 1BA">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '</div>\n'+


                                  // Audit for modification
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
                                    '<a href="javascript:void(0);" tabindex="8" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }