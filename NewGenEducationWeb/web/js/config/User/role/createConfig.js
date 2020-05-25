var createConfig={Operation:'create',
                                startStep:1,
                                endStep:4,
                                step:
                                [
                                {stepNumber:1,stepperLabel:'Select Operation',
                                   triggerElement:stepperOneTemplate
                                   },
                                  {stepNumber:2,stepperLabel:'Enter General Details',
                                  triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="col-blue">Step 2: Enter General Details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please enter general details</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="instituteName">Institute Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="instituteName" class="form-control" placeholder="Enter institute Name">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="InstituteId">Institute ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="InstituteId" class="form-control" placeholder="Enter Institute id ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="roleID">Role ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="roleID" class="form-control" placeholder="Enter Role id ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="roleDesc">Role Description</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="roleDesc" class="form-control" placeholder="Enter Role Description ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<div class="fixedFooter">\n'+
                                  '<ul class="pager">\n'+
                                  '<li class="next">\n'+
                                  '<a href="javascript:void(0);" tabindex="5" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                  '</li>\n'+
                                  '<li class="previous">\n'+
                                  '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                  '</li>\n'+
                                  '</ul>\n'+
                                  '</div>\n'+
                                  '</div>'

                                  },
                                  {stepNumber:3,stepperLabel:'',
                                  triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<div class="header">\n'+
                                  '<h2 class="font-bold col-blue">Step 3: User Role Configurations</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please Configure User Role Configurations details.</p>\n'+
                                  '</div>\n'+
                                  

                                  '<center><h4>User Role Configurations</h4></center>\n'+
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
  '<li role="presentation" class="active"><a href="#Mark" data-toggle="tab">User Role</a></li>\n'+
  '<li role="presentation"><a href="#Description" data-toggle="tab">Description</a></li>\n'+
'</ul>\n'+

'<div class="tab-content">\n'+

// Forenoon start
    '<div role="tabpanel" class="tab-pane fade in active" id="Mark">\n'+
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
                                                            '<input type="checkbox" id="basic_checkbox_6" />\n'+
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
// Afternoon start

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


'</div>\n'+


                                  '<div class="fixedFooter">\n'+
                                  '<nav>\n'+
                                  '<ul class="pager">\n'+
                                  '<li class="previous">\n'+
                                  '<a href="javascript:void(0);"  class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                  '</li>\n'+
                                  '<li class="next">\n'+
                                  '<a href="javascript:void(0);" tabindex="5" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                  '</li>\n'+
                                  '</ul>\n'+
                                  '</nav>\n'+
                                 
                                  '</div>'
                                },
                                  {stepNumber:4,stepperLabel:'Submit',
                                  triggerElement:'<div id="test-l-4" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  '<br>\n'+
                                  '<div class="alert alert-warning submitForm">\n'+
                                '<strong>Well done!</strong> You successfully completed the steps. Press <strong>Submit</strong>.\n'+
                                    '</div>\n'+
                                    '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="1" class="waves-effect">Submit <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>'
                                  }]
                                } 