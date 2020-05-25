var studentProfileModificationConfig = {Operation: 'modification',
    startStep: 1,
    endStep: 4,
    step:
            [{stepNumber: 1, stepperLabel: 'Select Operation',
                    triggerElement: stepperOneTemplate},
                {stepNumber: 2, stepperLabel: '',
                    triggerElement: '<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<br>\n' +
                            '<div class="header">\n' +
                            '<h6 class="font-bold col-blue">Current step 2: Search record for modification</h6>\n' +
                            '<div class="panel-group" id ="helpContainer" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h4 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#helpContainer" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h4>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            '<div class="panel-body">\n' +
                            '<ul>\n' +
                            '<li>Please find the record to Modify by entering the filter criteria</li>\n' +
                            '<li>You have to enter either student or class in the filter criteria.</li>\n' +
                            '<li>once enter the filter , please click Next in the footer</li>\n' +
                            '</ul>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>' +
                            '</div>' +
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
                            '<b for="authStatus">Auth status</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select id="authStatus" class="form-control form-line show-tick">\n' +
                            '<option value="">--Choose Auth status--</option>\n' +
                            '<option value="Male">Authorized</option>\n' +
                            '<option value="Male">Unauthorized</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="card searchResult">\n' +
                            '<div class="header" id="searchResultHeader">\n' +
                            '</div>\n' +
                            '<div class="body" id="searchResultBody">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            /* '<div class="fixedFooter">\n'+
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
                            '</div>\n' +
                            '</div>'
                },
                {stepNumber: 3, stepperLabel: '',
                    triggerElement: '<div id="test-l-3" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<div class="header">\n' +
                            '<h6 class="font-bold col-blue">Current step 3: Choose the record for modification</h6>\n' +
                            '<div class="panel-group" id ="helpContainer" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h4 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#helpContainer" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h4>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            'Please either double click or choose the record and press next button to Modify the student profile details\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                           
                            '<div class="body">\n' +
                            '<div class="table-responsive">\n' +
                            '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n' +
                            '<thead>\n' +
                            '<tr>\n' +
                            '<th>Select Record</th>\n' +
                            '<th>Student Name</th>\n' +
                            '<th>Student ID</th>\n' +
                            '<th>Student Class</th>\n' +
                            '</tr>\n' +
                            '</thead>\n' +
                            '<tbody>\n' +
                            '<tr>\n' +
                            '<td>\n' +
                            '<div class="form-group">\n' +
                            '<input tabindex="1" type="checkbox" id="basic_checkbox_1" />\n' +
                            '<b for="basic_checkbox_1"></b>\n' +
                            '</div>\n' +
                            '</td>\n' +
                            '<td>Junaid Tahir</td>\n' +
                            '<td>008</td>\n' +
                            '<td>BS Information Technology</td>\n' +
                            '</tr>\n' +
                            '<tr>\n' +
                            '<td>\n' +
                            '<div class="form-group">\n' +
                            '<input type="checkbox" id="basic_checkbox_2"  />\n' +
                            '<b for="basic_checkbox_2"></b>\n' +
                            '</div>\n' +
                            '</td>\n' +
                            '<td>Raj Kumar</td>\n' +
                            '<td>30</td>\n' +
                            '<td>BS Computer Science</td>\n' +
                            '</tr>\n' +
                            '</tbody>\n' +
                            '</table>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            /* '<div class="fixedFooter">\n'+
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
                             '</div>\n'+ */
                            '</div>\n' +
                            '</div>'
                },
                {stepNumber: 4, stepperLabel: '',
                    triggerElement: '<div id="test-l-4" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n' +
                            '<div>\n' +
                            '<h6 class="font-bold col-blue">Current step 4: Modify required student details</h6>\n' +
                            '<div class="panel-group" id ="helpContainer" role ="tablist" aria-multiselectable="true">\n' +
                            '<div class="panel panel-col-orange">\n' +
                            '<div class="panel-heading" role="tab" id="Help">\n' +
                            '<h4 class="panel-title">\n' +
                            '<a role="button" data-toggle="collapse" data-parent="#helpContainer" href="#collapseHelp" aria-expanded="false" aria-controls="collapseHelp">\n' +
                            '<i class="material-icons">help_outline</i>\n' +
                            '<span>Please click here for Help</span>\n' +
                            '</a>\n' +
                            '</h4>\n' +
                            '</div>\n' +
                            '<div id="collapseHelp" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Help">\n' +
                            '<div class="panel-body">\n' +
                            'Please click corresponding tabs to Modify student General, Family, Address and Other details respectively and then press submit.\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            
                            '<!-- Nav tabs -->\n' +
                            '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n' +
                            '<li role="presentation" class="active"><a href="#home" data-toggle="tab">General</a></li>\n' +
                            '<li role="presentation"><a href="#profile" data-toggle="tab">Family</a></li>\n' +
                            '<li role="presentation"><a href="#settings" data-toggle="tab">Address</a></li>\n' +
                            '<li role="presentation"><a href="#messages" data-toggle="tab">Others</a></li>\n' +
                            '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n' +
                            '</ul>\n' +
                            '<!-- Tab panes -->\n' +
                            '<div class="tab-content">\n' +
                            '<div role="tabpanel" class="tab-pane fade in active" id="home">\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="student_name">Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="student_name" class="form-control" value="junaid tahir" readonly >\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="id">ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="id" class="form-control" value="junaid tahir" readonly ">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b>Upload Photo</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="3" id="BSbtninfo" class="form-control" type="file" onchange="readURL(this);" placeholder="Upload student photo" /> \n' +
                            '</div>\n' +
                            '<img class="previewImage" id="ShowImage" src="images/user-lg.jpg" />\n' +
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
                            '<b for="student_dob">DOB</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line" id="bs_datepicker_container">\n' +
                            '<input type="text" disabled id="student_dob" class="form-control" value="09-10-1995" readonly >\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="student_gender">Gender</b>\n' +
                            '<div class="input-group input-group-lg">\n' +
                            '<select id="student_gender" class="form-control form-line show-tick">\n' +
                            '<option disabled value="">-- Select Gender --</option>\n' +
                            '<option selected value="Male">Male</option>\n' +
                            '<option disabled value="Femaile">Femaile</option>\n' +
                            '<option disabled value="Other">Other</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="student_note">Note</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<textarea id="student_note" rows="2" class="form-control resize" readonly>This student has extra reasoning skills and good attitude.</textarea>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div role="tabpanel" class="tab-pane fade" id="profile">\n' +
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
                            '<b for="family_name">Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="family_name" class="form-control" value="Abdul Majeed" readonly>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="family_relation">Relationship</b>\n' +
                            '<div class="input-group input-group-lg">\n' +
                            '<select id="family_relation" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Relationship --</option>\n' +
                            '<option selected value="">Father</option>\n' +
                            '<option disabled value="">Mother</option>\n' +
                            '<option disabled value="">Brother</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="family_occupation">Occupation</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="family_occupation" class="form-control" value="Former" readonly>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="BSbtninfo1">Upload Image</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input id="BSbtninfo1" class="form-control" type="file"   disabled /> \n' +
                            '<lable>user-lg.jpg</lable>\n' +
                            '</div>\n' +
                            '<img class="queryPreviewImage" id="" src="images/user-lg.jpg" />\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="family_email">Mail</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="family_email" class="form-control" value="some@example.com" readonly>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            
                            '<div class="col-md-6">\n' +
                            '<b for="family_contact">Contact Number</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="family_contact" class="form-control" value="+91 232 232323" readonly>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="form-group">\n' +
                            '<input type="checkbox" id="basic_checkbox_1" checked />\n' +
                            '<b for="basic_checkbox_1">Notification Required</b>\n' +
                            '</div>\n' +
                            '</div>\n' + // '</div>\n'+

                            '<div class="col-md-6">\n' +
                            '<b for="family_language">Language</b>\n' +
                            '<div class="input-group input-group-lg">\n' +
                            '<select id="family_language" class="form-control form-line show-tick">\n' +
                            '<option disabled value="">-- Select Language --</option>\n' +
                            '<option disabled value="english">English</option>\n' +
                            '<option selected value="hindi">Hindi</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div role="tabpanel" class="tab-pane fade" id="messages">\n' +
                            '<br>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="national_id">National ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="national_id" class="form-control" readonly value="34101-3954753-3">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="blood_group">Blood Group</b>\n' +
                            '<div class="input-group input-group-lg">\n' +
                            '<select id="blood_group" class="form-control form-line show-tick">\n' +
                            '<option disabled value="">-- Select Blood Group --</option>\n' +
                            '<option selected value="Male">A+</option>\n' +
                            '<option disabled value="Femaile">B+</option>\n' +
                            '<option disabled value="Other">A-</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="medical_details">Existing Medical details</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<textarea rows="2" id="medical_details" class="form-control resize" readonly>No medical details</textarea>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div role="tabpanel" class="tab-pane fade" id="settings">\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="address_1">Address Line 1</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="address_1" class="form-control" readonly value="Forest House, 4th Floor">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="address_2">Address Line 2</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="address_2" class="form-control" readonly value="Forest House, 4th Floor16-20 Clements Road">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="address_3">Address Line 3</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="address_3" class="form-control" readonly value="Ilford">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-md-6">\n' +
                            '<b for="address_4">Address Line 4</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="address_4" class="form-control" readonly value="IG1 1BA">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            // Audit card template for authorization
                            '<div role="tabpanel" class="tab-pane fade" id="Audit">\n' +
                            auditCardsTemplate +
                            '</div>\n' +
                            '</div>\n' +
                            /*'<div class="fixedFooter">\n'+
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
                             '</div>\n'+ */
                            '</div>\n' +
                            '</div>'
                }
            ]
}