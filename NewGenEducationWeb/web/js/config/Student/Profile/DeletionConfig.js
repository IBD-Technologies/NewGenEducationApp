var DeletionConfig = {Operation: 'deletion',
    startStep: 1,
    endStep: 5,
    step:
            [{stepNumber: 1, stepperLabel: 'Select Operation',
                    triggerElement: stepperOneTemplate},
                {stepNumber: 2, stepperLabel: 'Search record for deletion',
                    triggerElement:searchFilter
                },
                {stepNumber: 3, stepperLabel: 'Choose record for deletion',
                    triggerElement: readOnlyTable
                            
                },
                {stepNumber: 4, stepperLabel: 'Viweing the full profile Details',
                    triggerElement:fullView
                            
                },
                {stepNumber: 5, stepperLabel: 'Submit',
                    triggerElement: getLaststepTemplate('deletion',5)
                }

            ]
}