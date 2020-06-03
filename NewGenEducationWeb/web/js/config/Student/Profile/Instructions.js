/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

Instructions = [{Operation: 'create',
        Steps: [
            {stepNumber: 2,
                instructions: [
                    'Please enter student general details then Press Next in the footer',
                    'By clicking select file , you can upload the student photo, Please upload passport size images should not exceed 1MB.',
                    'only Image file extentions .jpeg,jpg,png,tiff,gif and bmp are supported'
                ]
            },
            {
                stepNumber: 3,
                instructions: [
                    'Please enter family details then press Next in the footer.',
                    'You can add or remove family members by pressing <strong>+</strong> or <strong>-</strong>  button',
                    'You can see family member records one by one  by pressing  <strong><</strong> and  <strong><</strong> buttons',
                    'Please select Notification required for whome Notifications about student should be sent'

                ]
            },
            {stepNumber: 4,
                instructions: [
                    'Please enter Address details and then press next button',
                    'You can Enter Postcode in Address Line4'
                ]
            },
            {
                stepNumber: 5,
                instructions: [
                    'Please enter other details and press Next in the Footer.'
                ]
            },
            {
                stepNumber: 6,
                instructions: []
            }
        ]

    },

    {Operation: 'modification',
        Steps: [
            {stepNumber: 2,
                instructions: [
                    'Please find the record to Modify by entering the filter criteria',
                    'You have to enter either student or class in the filter criteria',
                    'once enter the filter , please click Next in the footer'
                ]
            },
            {
                stepNumber: 3,
                instructions: [
                    'Please either double click or choose the record and press next button to Modify the student profile details'
                ]
            },
            {stepNumber: 4,
                instructions: [
                    'Please click corresponding tabs to Modify student General, Family, Address and Other details respectively and then press Next.',
                    'You can add or remove family members by pressing <strong>+</strong> or <strong>-</strong>  button.',
                    'You can see family member records one by one  by pressing  <strong><</strong> and  <strong><</strong> buttons',
                    'Please select Notification required for whom Notifications about student should be sent'
                ]
            },
            {
                stepNumber: 5,
                instructions: [
                    'Please enter other details and press Next in the Footer.'
                ]
            },
            {
                stepNumber: 6,
                instructions: []
            }
        ]

    },
    {Operation: 'deletion',
        Steps: [
            {stepNumber: 2,
                instructions: [
                    'Please find the record to delete by entering the filter criteria',
                    'You have to enter either student or class in the filter criteria.',
                    'once enter the filter , please click Next in the footer'
                ]
            },
            {
                stepNumber: 3,
                instructions: [
                    'Please either double click or choose the record and press next button to view the full student profile details for  deletion'
                ]
            },
            {stepNumber: 4,
                instructions: [
                    'Please click corresponding tabs to view student General, Family, Address and Other details respectively and then press Next in the bottom'
                ]
            },
            {
                stepNumber: 5,
                instructions: [
                    'Please enter other details and press Next in the Footer.'
                ]
            },
            {
                stepNumber: 6,
                instructions: []
            }
        ]

    },

    {Operation: 'authorization',
        Steps: [
            {stepNumber: 2,
                instructions: [
                    'Please either double click or choose the record and press next button to view the student profile details for  authorization'

                ]
            },
            {
                stepNumber: 3,
                instructions: [
                    'Please click corresponding tabs to view student General, Family, Address and Other details respectively and then press Next Button'
                ]
            },
            {stepNumber: 4,
                instructions: [
                    'Please click corresponding tabs to Modify student General, Family, Address and Other details respectively and then press Next.',
                    'You can add or remove family members by pressing <strong>+</strong> or <strong>-</strong>  button.',
                    'You can see family member records one by one  by pressing  <strong><</strong> and  <strong><</strong> buttons',
                    'Please select Notification required for whom Notifications about student should be sent'
                ]
            },
            {
                stepNumber: 5,
                instructions: [
                    'Please enter other details and press Next in the Footer.'
                ]
            },
            {
                stepNumber: 6,
                instructions: []
            }
        ]

    },
    {Operation: 'query',
        Steps: [
            {stepNumber: 2,
                instructions: [
                    'Please find the record to Query by entering the filter criteria',
                    'You have to enter either student or class in the filter criteria',
                    'once enter the filter , please click Next in the footer'

                ]
            },
            {
                stepNumber: 3,
                instructions: [
                    'Please either double click or choose the record and press next button to view the student full profile details'
                ]
            },
            {stepNumber: 4,
                instructions: [
                    '<strong>Well done!</strong> You successfully completed the query.Please click corresponding tabs to view student General, Family, Address and Other details respectively.'
                ]
            },
            {
                stepNumber: 5,
                instructions: [
                ]
            },
            {
                stepNumber: 6,
                instructions: []
            }
        ]

    }






];
          