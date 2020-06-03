/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var fullView=
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
                            /* '<center><img class="queryPreviewImage" id="ShowImage" src="images/user-lg.jpg" /></center>\n' +*/
                            '<div class="col-sm-6">\n' +
                            '<b for="student_name">Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="student_name" class="form-control" value="junaid tahir" disabled >\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="id">ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="id" class="form-control" value="junaid tahir" disabled ">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-12">\n' +
                            '<b>Upload Photo</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input data-buttonBefore="true" data-disabled="true" tabindex="3" id="BSbtninfo" type="file" class="form-control filestyle" data-buttonName="btn-primary" data-placeholder="Upload student photo" /> \n' +
                             '<img class="previewImage" id="ShowImage" src="images/user-lg.jpg" />\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>Class</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line ">\n' +
                            '<select tabindex="4" id="student_class" class="form-control form-line show-tick" disabled>\n' +
                            '<option value="">--Choose Class--</option>\n' +
                            '<option value="Male">IX</option>\n' +
                            '<option value="Male">X</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>DOB</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="5" type="text"  class="datepicker form-control" value ="16-10-1987" placeholder="Enter Date Of Birth" readonly >\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="student_gender">Gender</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line ">\n' +
                            '<select id="student_gender" class="form-control form-line show-tick" disabled>\n' +
                            '<option  value="">-- Select Gender --</option>\n' +
                            '<option selected value="Male">Male</option>\n' +
                            '<option  value="Femaile">Femaile</option>\n' +
                            '<option  value="Other">Other</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="student_note">Note</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<textarea id="student_note" rows="2" class="form-control resize" disabled>This student has extra reasoning skills and good attitude.</textarea>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div role="tabpanel" class="tab-pane fade" id="profile">\n' +
                            '<div class="header familHeader">\n' +
                            '<div class="row">\n' +
                            '<div class="col-sm-6 col-xs-6">\n' +
                            '<h2><button type="button" class="btn btn-default font-bold waves-effect btn-pagination-color">0</button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect btn-pagination-color">Of</button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect btn-pagination-color">1</button>\n' +
                            '</h2>\n' +
                            '</div>\n' +
                            '<div class="header-dropdown m-r--5">\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect btn-pagination-color"><i class="material-icons font-bold col-bluegrey">chevron_left</i></button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect btn-pagination-color"><i class="material-icons font-bold">chevron_right</i></button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect btn-pagination-color"><i class="material-icons font-bold">add</i></button>\n' +
                            '<button type="button" class="btn btn-default font-bold waves-effect btn-pagination-color"><i class="material-icons font-bold">remove</i></button>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<br>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="family_name">Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="family_name" class="form-control" value="Abdul Majeed" disabled>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="family_relation">Relationship</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line ">\n' +
                            '<select id="family_relation" class="form-control form-line show-tick" disabled>\n' +
                            '<option value="">-- Select Relationship --</option>\n' +
                            '<option selected value="">Father</option>\n' +
                            '<option  value="">Mother</option>\n' +
                            '<option  value="">Brother</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="family_occupation">Occupation</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="family_occupation" class="form-control" value="Former" disabled>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6 demo-masked-input">\n' +
                            '<b for="family_email">Mail</b>\n' +
                            '<div class="input-group">\n' +
                            '<span class="input-group-addon">\n' +
                            '<i class="material-icons">email</i>\n' +
                            '</span>\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" class="form-control email" placeholder="Ex: example@example.com" value="rajkuamrvelusamy@gmail.com" disabled>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-12">\n' +
                            '<b for="BSbtninfo1">Upload photo</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input data-buttonBefore="true" data-disabled="true" tabindex="3" id="BSbtninfo" type="file" class="form-control filestyle" data-buttonName="btn-primary" data-placeholder="Upload Family Member photo" /> \n' +
                            '<img class="queryPreviewImage" id="" src="images/user-lg.jpg" />\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6 demo-masked-input">\n' +
                            '<b for="family_contact">Contact Number</b>\n' +
                            '<div class="input-group"> \n' +
                            '<span class="input-group-addon"> \n' +
                            ' <i class="material-icons">phone_iphone</i> \n' +
                            '</span> \n' +
                            '<div class="form-line">\n' +
                            '<input type="text" class="form-control mobile-phone-number" value ="9962281425" placeholder="Ex: +00 (000) 000-00-00" disabled>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="checkbox" id="basic_checkbox_1" class="filled-in chk-col-teal" checked  disabled/>\n' +
                            '<label for="basic_checkbox_1">Notification Required</label>\n' +
                            '</div>\n' +
                            '</div>\n' + // '</div>\n'+
                            '</div>\n' + // '</div>\n'+


                            '<div class="col-sm-6">\n' +
                            '<b for="family_language">Language</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line ">\n' +
                            '<select id="family_language" class="form-control form-line show-tick" disabled>\n' +
                            '<option value="">-- Select Language --</option>\n' +
                            '<option value="english">English</option>\n' +
                            '<option selected value="hindi">Hindi</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div role="tabpanel" class="tab-pane fade" id="messages">\n' +
                            '<br>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="national_id">National ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input type="text" id="national_id" class="form-control" disabled value="34101-3954753-3">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="blood_group">Blood Group</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line ">\n' +
                            '<select id="blood_group" class="form-control form-line show-tick" disabled>\n' +
                            '<option value="">-- Select Blood Group --</option>\n' +
                            '<option selected value="Male">A+</option>\n' +
                            '<option value="Femaile">B+</option>\n' +
                            '<option value="Other">A-</option>\n' +
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="medical_details">Existing Medical details</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<textarea rows="2" id="medical_details" class="form-control resize" disabled>No medical details</textarea>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div role="tabpanel" class="tab-pane fade" id="settings">\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_1">Address Line 1</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="address_1" class="form-control" disabled value="Forest House, 4th Floor">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_2">Address Line 2</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="address_2" class="form-control" disabled value="Forest House, 4th Floor16-20 Clements Road">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_3">Address Line 3</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="address_3" class="form-control" disabled value="Ilford">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_4">Address Line 4</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class=" form-line">\n' +
                            '<input type="text" id="address_4" class="form-control" disabled value="IG1 1BA">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            // Audit card template for authorization
                            '<div role="tabpanel" class="tab-pane fade" id="Audit">\n' +
                            auditCardsTemplate +
                            '</div>\n' +
                            '</div>\n' ;
var searchFilter=                            '<div class="col-sm-6">\n' +
                            '<b>Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="1" type="text" id="student_name" class="form-control" placeholder="Enter student name" >\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input tabindex="2" type="text" id="student_id" class="form-control" placeholder="Enter student id ">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
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
                            '<div class="col-sm-6">\n' +
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
                            '</div>\n' ;
var readOnlyTable= '<div class="body">\n' +
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
                            '<label for="basic_checkbox_1"></label>\n' +
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
                            '</div>\n' ;
                  
                 

