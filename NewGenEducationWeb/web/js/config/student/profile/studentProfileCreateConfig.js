var studentProfileCreateConfig = {Operation: 'create',
    startStep: 1,
    endStep: 6,
    step:
            [
                {stepNumber: 1, stepperLabel: 'Select Operation',
                    triggerElement: stepperOneTemplate
                },
                {stepNumber: 2, stepperLabel: 'Enter General Details',
                    triggerElement: '<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<div class="header">\n' +
                            '<h6 class="col-blue">Current step 2: Enter general details</h6>\n' +
                            '<div class="panel-group" id ="helpContainer" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h6 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#helpContainer" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h6>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            '<ul>\n' +
                            '<li>Please enter student general details then Press Next in the footer</li>\n' +
                            '<li>By clicking select file , you can upload the student photo, Please upload passport size images should not exceed 1MB. ' +
                            'only Image file extentions .jpeg,jpg,png,tiff,gif and bmp are supported ' +
                            '</ul>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                          
                           '<div class="col-md-6">\n' +
                            '<b>Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="1" type="text" id="student_name" class="form-control" placeholder="Enter student name" >\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="2" type="text" id="student_id" class="form-control" placeholder="Enter student id ">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-12">\n' +
                            '<b>Upload Photo</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="3" id="BSbtninfo" class="form-control" type="file" onchange="readURL(this);" placeholder="Upload student photo" /> \n' +
                            '</div>\n' +
                            '<img class="previewImage" id="ShowImage" src="#" />\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>Class</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select tabindex="4" id="student_class" class="form-control form-line show-tick">\n' +
                            '<option value="">--Choose Class--</option>\n' +
                            '<option value="Male">IX</option>\n' +
                            '<option value="Male">X</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>DOB</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line" id="bs_datepicker_container">\n' +
                            '<input tabindex="5" type="text"  class="form-control" placeholder="Enter Date Of Birth in dd-mm-yyyy format">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>Gender</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="input-group input-group-lg">\n' +
                            '<select tabindex="6" id="student_gender" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Gender --</option>\n' +
                            '<option value="Male">Male</option>\n' +
                            '<option value="Femaile">Femaile</option>\n' +
                            '<option value="Other">Other</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>Note</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<textarea tabindex="7" id="student_note" rows="2" class="form-control resize LastItem" placeholder="Enter notes about student if you want"></textarea>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            /*  '<div class="fixedFooter">\n'+
                             
                             '<ul class="pager">\n'+
                             
                             '<li class="next">\n'+
                             '<a href="javascript:void(0);" tabindex="8" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                             '</li>\n'+
                             '<li class="previous">\n'+
                             '<a href="javascript:void(0);"  class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                             '</li>\n'+
                             '</ul>\n'+
                             '</div>\n'+ */
                            '</div>'

                },
                {stepNumber: 3, stepperLabel: 'Enter Family Details',
                    triggerElement: '<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<div class="header">\n' +
                            '<h6 class="font-bold col-blue">Current step 4: Enter family details</h6>\n' +
                           '<div class="panel-group" id ="helpContainer" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h6 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h6>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            '<ul>\n' +
                            '<li>Please enter family details then press Next in the footer.</li> \n' +
                            '<li>You can add or remove family members by pressing <strong>+</strong> or <strong>-</strong>  button.</li>\n' +
                            '<li>Please select Notification required for whome Notifications about student should be sent</li>\n' +
                            '</ul>' +
                            '</div>\n' +
                            '</div>' +
                            '</div>' +
                            '</div>' +
                           
                            '<div class="header familHeader">\n' +
                            '<div class="row">\n' +
                            '<div class="col-sm-6 col-xs-6">\n' +
                            '<h2><button type="button" class="btn btn-default font-bold waves-effect">0</button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect">Of</button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect">1</button>\n' +
                            '</h2>\n' +
                            '</div>\n' +
                            '<div class="header-dropdown m-r--5">\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">chevron_right</i></button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">add</i></button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect"><i class="material-icons font-bold">remove</i></button>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<br>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="1" type="text" id="family_name" class="form-control" placeholder="Enter family member name">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>Relationship</b>\n' +
                            '<div class="input-group input-group-lg">\n' +
                            '<select tabindex="2" id="family_relation" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Relationship --</option>\n' +
                            '<option value="Male">Father</option>\n' +
                            '<option value="Femaile">Mother</option>\n' +
                            '<option value="Other">Brother</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="family_occupation">Occupation</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="3" type="text" id="family_occupation" class="form-control" placeholder="Enter family member occupation">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="BSbtninfo1">Upload Image</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="4" id="BSbtninfo1" class="form-control" type="file" onchange="readAnotherURL(this);" /> \n' +
                            '</div>\n' +
                            '<img class="previewImage" id="ShowAnotherImage" src="#" />\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="family_email">Mail</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="5" type="text" id="family_email" class="form-control" placeholder="Enter family member email">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="family_contact">Contact Number</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="6" type="text" id="family_contact" class="form-control" placeholder="Enter family member contact number">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<div class="form-group">\n' +
                            '<input tabindex="7" type="checkbox" id="notification" checked />\n' +
                            '<b for="notification">Notification Required</b>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="family_language">Language</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select tabindex="8" id="family_language" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Language --</option>\n' +
                            '<option value="english">English</option>\n' +
                            '<option value="hindi">Hindi</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n'

                            /* '<div class="fixedFooter">\n'+
                             '<nav>\n'+
                             '<ul class="pager">\n'+
                             '<li class="previous">\n'+
                             '<a href="javascript:void(0);"  class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                             '</li>\n'+
                             '<li class="next">\n'+
                             '<a href="javascript:void(0);" tabindex="9" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                             '</li>\n'+
                             '</ul>\n'+
                             '</nav>\n'+
                             
                             '</div>'*/
                },
                {stepNumber: 4, stepperLabel: 'Enter Address Details',
                    triggerElement: '<div id="test-l-4" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<div class="header">\n' +
                            '<h6 class="font-bold col-blue">Current step 4: Enter address details</h6>\n' +
                            '<div class="panel-group" id ="helpContainer" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h6 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#helpContainer" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h6>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            '<ul>\n' +
                            '<li>Please enter Address details and then press next button.</li>\n' +
                            '<li>You can Enter Postcode in Address Line4</li>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                           
                            '<div class="col-md-6">\n' +
                            '<b for="address_1">Address Line 1</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="1" type="text" id="address_1" class="form-control" placeholder="Enter Address line 1">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="address_2">Address Line 2</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="2" type="text" id="address_2" class="form-control" placeholder="Enter Address line 2">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="address_3">Address Line 3</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="3" type="text" id="address_3" class="form-control" placeholder="Enter Address line 3">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="address_4">Address Line 4</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="4" type="text" id="address_4" class="form-control LastItem" placeholder="Enter Address line 4">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            /*
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
                             '</div>\n'+ */
                            '</div>'},
                {stepNumber: 5, stepperLabel: 'Enter Other Details',
                    triggerElement: '<div id="test-l-5" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<div class="header">\n' +
                            '<h6 class="font-bold col-blue">Current step 5: Enter other details</h6>\n' +
                            '<div class="panel-group" id ="helpContainer" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h6 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#helpContainer" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h6>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            'Please enter other details and press Next in the Footer.\n' +
                            '</div>\n' +
                            '</div>' +
                            '</div>' +
                            '</div>\n' +
                           
                            '<div class="col-md-6">\n' +
                            '<b for="national_id">National ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="1" type="text" id="national_id" class="form-control" placeholder="Enter National ID">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="blood_group">Blood Group</b>\n' +
                            '<div class="input-group input-group-lg">\n' +
                            '<select tabindex="2" id="blood_group" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Blood Group --</option>\n' +
                            '<option value="Male">A+</option>\n' +
                            '<option value="Femaile">B+</option>\n' +
                            '<option value="Other">A-</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="medical_details">Existing Medical details</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<textarea tabindex="3" id="medical_details" rows="2" class="LastItem form-control resize" placeholder="Enter medical details if any"></textarea>\n' +
                            '</div>\n' +
                            '</div>\n'+
                            '</div>\n'

                            /*+
                             '<div class="fixedFooter">\n'+
                             '<nav>\n'+
                             '<ul class="pager">\n'+
                             '<li class="previous">\n'+
                             '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                             '</li>\n'+
                             '<li class="next">\n'+
                             '<a href="javascript:void(0);" tabindex="4" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
                             '</li>\n'+
                             '</ul>\n'+
                             '</nav>\n'+
                             '</div>\n'+
                             '</div>' */
                },
                {stepNumber: 6, stepperLabel: 'Submit',
                    triggerElement: '<div id="test-l-6" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<br>\n' +
                            '<br>\n' +
                            '<br>\n' +
                            '<br>\n' +
                            '<br>\n' +
                            '<div class="alert alert-warning submitForm">\n' +
                            '<strong>Well done!</strong> You successfully completed the steps. Press <strong>Submit</strong>.\n' +
                            '</div>\n' +
                            /* '<div class="fixedFooter">\n'+
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
                             '</div>\n'+ */
                            '</div>'
                }]
} 