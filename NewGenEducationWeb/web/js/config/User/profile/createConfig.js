var createConfig={Operation:'create',
                                startStep:1,
                                endStep:6,
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

                                  '<label for="UserName">User Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="student_name" class="form-control" placeholder="Enter User name">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="UserId">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="UserId" class="form-control" placeholder="Enter User id ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Password">Password</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="password" id="Password" class="form-control" placeholder="Enter User Password ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="bs_datepicker_container">Expiry Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="4" type="text"  class="form-control" placeholder="Enter expiry Date in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                 

                                  '<label for="UserType">User Type</label>\n'+
                                  '<div class="form-group">\n'+
                                   '<div class="form-line">\n'+
                                  '<select tabindex="5" id="UserType" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose User Type--</option>\n'+
                                  '<option value="Male">Admin</option>\n'+
                                  '<option value="Male">Teacher</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="HomeInstitute">Home Institute</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="6" type="text" id="HomeInstitute" class="form-control" placeholder="Enter Home Institute ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="Status">Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="Status" class="form-control form-line show-tick">\n'+
                                  '<option value="">-- Select Status --</option>\n'+
                                  '<option value="Male">Enable</option>\n'+
                                  '<option value="Femaile">Disable</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Name">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="8" type="text" id="HomeInstitute" class="form-control" placeholder="Enter Name ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<div class="fixedFooter">\n'+
                                  '<ul class="pager">\n'+
                                  '<li class="next">\n'+
                                  '<a href="javascript:void(0);" tabindex="10" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                  '</li>\n'+
                                  '<li class="previous">\n'+
                                  '<a href="javascript:void(0);"  class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                  '</li>\n'+
                                  '</ul>\n'+
                                  '</div>\n'+
                                  '</div>'

                                  },
                                  {stepNumber:3,stepperLabel:'',
                                  triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<div class="header">\n'+
                                  '<h2 class="font-bold col-blue">Step 3: Enter Parent Details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please enter Parent Student Role Mapping details.</p>\n'+
                                  '</div>\n'+
                                  

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
                                  '<input tabindex="1" type="text" id="roleId" class="form-control" placeholder="Enter Role ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="studentName">Student Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="studentName" class="form-control" placeholder="Enter Student Name">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="studentId">Student ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="studentId" class="form-control" placeholder="Enter Student ID">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="InstituteName">Institute Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="InstituteName" class="form-control" placeholder="Enter Home Institute ">\n'+
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
                                  {stepNumber:4,stepperLabel:'Enter Address Details',
                                  triggerElement:'<div id="test-l-4" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<div class="header">\n'+
                                  '<h2 class="font-bold col-blue">Step 4: Enter Class Details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please enter Class Entity Access Role details.</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+

                                  
                                    '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="1" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>'},
                                    {stepNumber:5,stepperLabel:'Enter Teacher Details',
                                  triggerElement:'<div id="test-l-5" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                    '<div class="header">\n'+
                                  '<h2 class="font-bold col-blue">Step 5: Enter Teacher Details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please enter Teacher Entity Access Role details.</p>\n'+
                                  '</div>\n'+
                                   '<br>\n'+

                                    '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="1" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>'
                                  },
                                  {stepNumber:6,stepperLabel:'Submit',
                                  triggerElement:'<div id="test-l-6" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
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