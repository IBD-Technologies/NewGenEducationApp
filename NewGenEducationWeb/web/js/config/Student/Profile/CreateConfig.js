var CreateConfig = {Operation: 'create',
    startStep: 1,
    endStep: 6,
    step:
            [
                {stepNumber: 1, stepperLabel: 'Select Operation',
                    triggerElement: stepperOneTemplate
                },
                {stepNumber: 2, stepperLabel: 'Enter General Details',
                    triggerElement: 
                           '<div class="col-sm-6">\n' +
                            '<b>Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable" ng-model="dataModel.studentName" tabindex="1" type="text" id="student_name" class="form-control" placeholder="Enter student name" >\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable" ng-model="dataModel.studentID" tabindex="2" type="text" id="student_id" class="form-control" placeholder="Enter student id ">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-12">\n' +
                            '<b>Upload Photo</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                             '<input ng-readonly="editable" data-buttonBefore="true" data-disabled="false" tabindex="3" id="BSbtninfo" type="file" class="form-control filestyle" data-buttonName="btn-primary" data-placeholder="Upload Student photo" /> \n' +
                             '<img class="previewImage" id="ShowImage" ng-src="dataModel.profileImgPath" />\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>Class</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select ng-disabled="editable" ng-model="dataModel.general.class" tabindex="4" id="student_class" class="form-control form-line show-tick">\n' +
                             '<option ng-repeat="x in selectMaster.ClassMaster" value="{{x.id}}">{{x.value}}</option>'+
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>DOB</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-disabled="editable" ng-model="dataModel.general.dob" tabindex="5" type="text"  class="datepicker form-control" placeholder="Enter Date Of Birth">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>Gender</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select ng-disabled="editable" ng-model="dataModel.general.gender" tabindex="6" id="student_gender" class="form-control form-line show-tick">\n' +
                            '<option ng-repeat="x in selectMaster.GenderMaster" value="{{x.id}}">{{x.value}}</option>\n'+
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>Note</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<textarea ng-readonly="editable" ng-model="dataModel.note" tabindex="7" id="student_note" rows="2" class="form-control resize LastItem" placeholder="Enter notes about student if you want"></textarea>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' 
             
                },
                {stepNumber: 3, stepperLabel: 'Enter Family Details',
                    triggerElement:
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
                            '<button ng-disabled="editable" type="button" class="btn btn-default font-bold waves-effect btn-pagination-color"><i class="material-icons font-bold">add</i></button>\n' +
                            '<button ng-disabled="editable" type="button" class="btn btn-default font-bold waves-effect btn-pagination-color"><i class="material-icons font-bold">remove</i></button>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            //'<br>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>Name</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable" ng-model="dataModel.family.memberName" tabindex="1" type="text" id="family_name" class="form-control" placeholder="Enter family member name">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b>Relationship</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select ng-disabled="editable" ng-model="dataModel.family.memberRelationship"  tabindex="2" id="family_relation" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Relationship --</option>\n' +
                            '<option ng-repeat="x in selectMaster.RelationshipMaster" value="{{x.id}}">{{x.value}}</option>\n'+
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-12">\n' +
                            '<b>Upload Photo</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                             '<input ng-readonly="editable" data-buttonBefore="true" data-disabled="false" tabindex="3" id="BSbtninfo" type="file" class="form-control filestyle" data-buttonName="btn-primary" data-placeholder="Upload Family Member photo" /> \n' +
                             '<img class="previewImage" id="ShowImage" ng-src=""dataModel.family.memberImgPath" />\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="family_occupation">Occupation</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable"  ng-model="dataModel.family.memberOccupation" tabindex="3" type="text" id="family_occupation" class="form-control" placeholder="Enter family member occupation">\n' +
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
                            '<input ng-readonly="editable"  ng-model="dataModel.family.memberEmailID"  type="text" class="form-control email" placeholder="Ex: example@example.com">\n' +
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
                            '<input ng-readonly="editable"  ng-model="dataModel.family.memberContactNo" type="text" class="form-control mobile-phone-number" placeholder="Ex: +00 (000) 000-00-00">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-disabled="editable" ng-model="dataModel.family.notificationRequired" type="checkbox" id="basic_checkbox_1" class="filled-in chk-col-teal" checked />\n' +
                            '<label for="basic_checkbox_1">Notification Required</label>\n' +
                            '</div>\n' +
                            '</div>\n' + // '</div>\n'+
                            '</div>\n' + // '</div>\n'+

                            '<div class="col-sm-6">\n' +
                            '<b for="family_language">Language</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select ng-diabled="editable" ng-model="dataModel.family.language" tabindex="8" id="family_language" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Language --</option>\n' +
                            '<option ng-repeat="x in selectMaster.LanguageMaster" value="{{x.id}}">{{x.value}}</option>\n'+
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' 
                },
                {stepNumber: 4, stepperLabel: 'Enter Address Details',
                    triggerElement: 
                            '<div class="col-sm-6">\n' +
                            '<b for="address_1">Address Line 1</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable"  ng-model="dataModel.address.addressLine1" tabindex="1" type="text" id="address_1" class="form-control" placeholder="Enter Address line 1">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_2">Address Line 2</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable"  ng-model="dataModel.address.addressLine2" tabindex="2" type="text" id="address_2" class="form-control" placeholder="Enter Address line 2">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_3">Address Line 3</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable"  ng-model="dataModel.address.addressLine3" tabindex="3" type="text" id="address_3" class="form-control" placeholder="Enter Address line 3">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_4">Address Line 4</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable"  ng-model="dataModel.address.addressLine4"  tabindex="4" type="text" id="address_4" class="form-control LastItem" placeholder="Enter Address line 4">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="address_4">Address Line 5</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable"  ng-model="dataModel.address.addressLine5"  tabindex="4" type="text" id="address_4" class="form-control LastItem" placeholder="Enter Address line 4">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' 
                    
                            },
                {stepNumber: 5, stepperLabel: 'Enter Other Details',
                    triggerElement:
                            '<div class="col-sm-6">\n' +
                            '<b for="national_id">National ID</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<input ng-readonly="editable"  ng-model="dataModel.nationalID" tabindex="1" type="text" id="national_id" class="form-control" placeholder="Enter National ID">\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="blood_group">Blood Group</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<select ng-disabled="editable"  ng-model="" tabindex="2" id="blood_group" class="form-control form-line show-tick">\n' +
                            '<option value="">-- Select Blood Group --</option>\n' +
                            '<option ng-repeat="x in selectMaster.LanguageMaster" value="{{x.id}}">{{x.value}}</option>\n'+
                            '</select>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '<div class="col-sm-6">\n' +
                            '<b for="medical_details">Existing Medical details</b>\n' +
                            '<div class="form-group">\n' +
                            '<div class="form-line">\n' +
                            '<textarea ng-readonly="editable"  ng-model="dataModel.existingMedicalDetails" tabindex="3" id="medical_details" rows="2" class="LastItem form-control resize" placeholder="Enter medical details if any"></textarea>\n' +
                            '</div>\n' +
                            '</div>\n' +
                            '</div>\n' 
                            
                },
                {stepNumber: 6, stepperLabel: 'Submit',
                    triggerElement:getLaststepTemplate('create',6) 
                }]
} ;

 