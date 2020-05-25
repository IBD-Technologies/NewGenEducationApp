var parentSignature={Operation:'parentSignature',
                                startStep : 1,
                                endStep:4,
                                step: 
                                [{stepNumber:1,stepperLabel:'Select Operation',
                                triggerElement:stepperOneTemplate},
                                {stepNumber:2,stepperLabel:'',
                                triggerElement:'<div id="test-l-2" role="tabpanel" class="bs-stepper-pane" aria-labelledby="stepper1trigger2">\n'+
                                  '<br>\n'+
                                  '<div class="header block">\n'+
                                  '<h2 class="font-bold col-blue">Step 2: Enter Filter Criteria</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please must enter name in the filter criteria for the Signature</p>\n'+
                                  '</div>\n'+
                                  '<br>\n'+
                                  '<br>\n'+

                                  '<label for="StudentName">Student Name</label>\n'+
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


                                  '<label for="class">Class</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="2" id="class" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Exam--</option>\n'+
                                  '<option value="Male">IX/A</option>\n'+
                                  '<option value="Male">IX/B</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                  '<label for="Circularid">Circular ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="3" type="text" id="Circularid" class="form-control" >\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Description">Description</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input tabindex="4" type="text" id="Description" class="form-control" >\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="SignStatus">Sign Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="5" id="SignStatus" class="form-control form-line show-tick">\n'+
                                  '<option value="">--Choose Status--</option>\n'+
                                  '<option value="Male">Yes</option>\n'+
                                  '<option value="Male">No</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+

                                   '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" tabindex="6" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 3: Choose the record</h2>\n'+
                                  '<br>\n'+
                                  '<p class="col-orange"><i class="material-icons">help_outline</i>Please select the checkbox or double click on the row that you want to view the result.</p>\n'+
                                  '</div>\n'+
                                  
                                  
                                  '<div class="table-responsive">\n'+
                                                '<table id="student_data_table" class="table table-bordered table-striped table-hover js-basic-example dataTable">\n'+
                                                    '<thead>\n'+
                                                        '<tr>\n'+
                                                        '<th>Select Record</th>\n'+
                                                            '<th>Name</th>\n'+
                                                            '<th>ID</th>\n'+
                                                            '<th>Sign Status</th>\n'+
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
                                                            '<td>Sasi K</td>\n'+
                                                            '<td>Student4</td>\n'+
                                                            '<td>No</td>\n'+
                                                         '</tr>\n'+
                                                    '</tbody>\n'+
                                                '</table>\n'+
                                            '</div>\n'+

                                  '<div class="fixedFooter">\n'+
                                    '<nav>\n'+
                                    '<ul class="pager">\n'+
                                    '<li class="previous">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect"><span aria-hidden="true">←</span> Previous</a>\n'+
                                    '</li>\n'+
                                    '<li class="next">\n'+
                                    '<a href="javascript:void(0);" class="waves-effect">Next <span aria-hidden="true">→</span></a>\n'+
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
                                  '<h2 class="font-bold col-blue">Step 4: Progress Card detailed view</h2>\n'+
                                  '<br>\n'+
                                  '<div class="alert alert-warning ">\n'+
                                   '<strong><i class="material-icons">help_outline</i> Well done!</strong> You successfully completed the query.Please click corresponding tabs to view Student Ecircular details and click Sign for Signature student progress card.\n'+
                                    '</div>\n'+
                                  
                                  '</div>\n'+
                                  

                                 '<div class="body">\n'+
                                  '<!-- Nav tabs -->\n'+
                                  '<ul class="nav nav-tabs tab-col-blue-grey" role="tablist">\n'+
                                  '<li role="presentation" class="active"><a href="#general" data-toggle="tab">General</a></li>\n'+
                                  '<li role="presentation"><a href="#Circular" data-toggle="tab">Circular</a></li>\n'+
                                  '<li role="presentation"><a href="#Message" data-toggle="tab">Message</a></li>\n'+
                                  '<li role="presentation"><a href="#Audit" data-toggle="tab">Audit</a></li>\n'+
                                  '</ul>\n'+

                                  '<!-- Tab panes -->\n'+
                                  '<div class="tab-content">\n'+

            // general tab
          '<div role="tabpanel" class="tab-pane fade in active" id="general">\n'+
                                  


                                  '<label for="name">Name</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="name" class="form-control" value="ABC Matriculation School" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+


                                  '<label for="id">ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="name" class="form-control" value="Student2" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  '<label for="CircularID">Circular ID</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="CircularID" class="form-control" value="E79200012" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="Description">Description</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="Description" class="form-control" value="Ecircular" readonly ">\n'+
                                  '</div>\n'+
                                  '</div>\n'+
                                  
                                  '<label for="bs_datepicker_container">Date</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line" id="bs_datepicker_container">\n'+
                                  '<input tabindex="3"  type="text"  class="form-control LastItem" value="27-03-2020" readonly>\n'+
                                  '</div>\n'+
                                  '</div>\n'+

                                  '<label for="SignStatus">Sign Status</label>\n'+
                                  '<div class="input-group input-group-lg">\n'+
                                  '<select tabindex="3" id="SignStatus" class="form-control form-line show-tick">\n'+
                                  '<option detabled value="">--Choose Status--</option>\n'+
                                  '<option selected value="Male">Yes</option>\n'+
                                  '<option disabled value="Male">No</option>\n'+
                                  '</select>\n'+
                                  '</div>\n'+
           '</div>\n'+

          // video content start
    '<div role="tabpanel" class="tab-pane fade" id="Circular">\n'+
        '<label for="BSbtninfo">Upload Circular</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input disabled tabindex="3" id="BSbtninfo" class="form-control" type="file" onchange="uploadFile(this);" placeholder="Upload student photo" /> \n'+
                                  '</div>\n'+
                                  '<embed src="mockup.pdf" id="showPdf" type="application/pdf" />\n'+
                                  '</div>\n'+
    '</div>\n'+
                                  
         //Message start
        '<div role="tabpanel" class="tab-pane fade" id="Message">\n'+
                     
                                  '<label for="Message1">Message</label>\n'+
                                  '<div class="form-group">\n'+
                                  '<div class="form-line">\n'+
                                  '<input type="text" id="Message1" class="form-control" value="" readonly ">\n'+
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
                                    '<a href="javascript:void(0);" class="waves-effect">Sign <span aria-hidden="true">→</span></a>\n'+
                                    '</li>\n'+
                                    '</ul>\n'+
                                    '</nav>\n'+
                                    '</div>\n'+
                                    '</div>\n'+
                                  '</div>'
                                }
                                ]
                              }