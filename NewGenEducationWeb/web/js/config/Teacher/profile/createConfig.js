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

                                  '<label for="teacherName">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="teacherName" class="form-control" placeholder="Enter teacher name">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="teacherId">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="teacherId" class="form-control" placeholder="Enter teacher id ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+



                                  '<label for="BSbtninfo">Upload Photo</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" id="BSbtninfo" class="form-control" type="file" onchange="readURL(this);"  /> \n'+
                                  '</div>\n'+
                                  '<img class="previewImage" id="ShowImage" src="#" />\n'+
                                  '</div>\n'+


                                  '<label for="Qualification">Qualification</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="Qualification" class="form-control" placeholder="Enter teacher Qualification">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="student_class">Class</label>\n'+
                                  '<div class="form-group">\n'+
                                   '<div class="form-line">\n'+
                                  '<select tabindex="5" id="student_class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Class--</option>\n'+
                                  '<option value="Male">IX</option>\n'+
                                  '<option value="Male">X</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="bs_datepicker_container">DOB</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="6" type="text"  class="form-control" placeholder="Enter Date Of Birth in dd-mm-yyyy format">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="student_gender">Gender</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="7" id="student_gender" class="form-control form-line show-tick LastItem">\n'+
                                  '<option value="">-- Select Gender --</option>\n'+
                                  '<option value="Male">Male</option>\n'+
                                  '<option value="Femaile">Femaile</option>\n'+
                                  '<option value="Other">Other</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  
                                  '<div class="fixedFooter">\n'+

                                  '<ul class="pager">\n'+
                                  
                                  '<li class="next">\n'+
                                  '<a href="javascript:void(0);" tabindex="8" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                                  '</li>\n'+
                                  '<li class="previous">\n'+
                                  '<a href="javascript:void(0);"  class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                  '</li>\n'+
                                  '</ul>\n'+
                                  '</div>\n'+
                                  '</div>'

                                  },
                                  {stepNumber:3,stepperLabel:'Enter Family Details',
                                  triggerElement:'<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<div class="header">\n'+
                                  '<h2 class="font-bold col-blue">Step 3: Enter Family Details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please enter family details. You can add or remove family members by pressing <strong>+</strong> or <strong>-</strong>  button.</p>\n'+
                                  '</div>\n'+
                                  


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
                                  '<input tabindex="1" type="text" id="family_name" class="form-control" placeholder="Enter family member name">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="family_relation">Relationship</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="family_relation" class="form-control form-line show-tick">\n'+
                                  '<option value="">-- Select Relationship --</option>\n'+
                                  '<option value="Male">Father</option>\n'+
                                  '<option value="Femaile">Mother</option>\n'+
                                  '<option value="Other">Brother</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  
                                  '<label for="family_email">Mail</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="family_email" class="form-control" placeholder="Enter family member email">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="family_contact">Contact Number</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="family_contact" class="form-control LastItem" placeholder="Enter family member contact number">\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Enter Address Details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please enter Address details.</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+

                                  '<label for="address_1">Address Line 1</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="address_1" class="form-control" placeholder="Enter Address line 1">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="address_2">Address Line 2</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="address_2" class="form-control" placeholder="Enter Address line 2">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="address_3">Address Line 3</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="address_3" class="form-control" placeholder="Enter Address line 3">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="address_4">Address Line 4</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="address_4" class="form-control LastItem" placeholder="Enter Address line 4">\n'+
                                  '</div>\n'+
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
                                    '</div>'},
                                    {stepNumber:5,stepperLabel:'Enter Other Details',
                                  triggerElement:'<div id="test-l-5" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                    '<div class="header">\n'+
                                  '<h2 class="font-bold col-blue">Step 5: Enter Other Details</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please enter other details.</p>\n'+
                                  '</div>\n'+
                                    
                                  '<br>\n'+

                                  '<label for="contact">Contact Number</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="1" type="text" id="contact" class="form-control" placeholder="Enter contact number">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Mail">Mail</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="2" type="text" id="Mail" class="form-control" placeholder="Enter contact number">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="blood_group">Blood Group</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                        '<select tabindex="3" id="blood_group" class="form-control form-line show-tick">\n'+
                                        '<option value="">-- Select Blood Group --</option>\n'+
                                        '<option value="Male">A+</option>\n'+
                                        '<option value="Femaile">B+</option>\n'+
                                        '<option value="Other">A-</option>\n'+
                                    '</select>\n'+
                                    '</div>\n'+

                                    '<label for="medical_details">Existing Medical details</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<textarea tabindex="4" id="medical_details" rows="2" class="LastItem form-control resize" placeholder="Enter medical details if any"></textarea>\n'+
                                  '</div>\n'+
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