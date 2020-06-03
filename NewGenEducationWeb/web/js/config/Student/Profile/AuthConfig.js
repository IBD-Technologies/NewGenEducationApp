var AuthConfig = {Operation: 'authorization',
    startStep: 1,
    endStep: 4,
    step:
            [{stepNumber: 1, stepperLabel: 'Select Operation',
                    triggerElement: stepperOneTemplate},
                {stepNumber: 2, stepperLabel: 'Choose record for authorise/reject',
                    triggerElement:readOnlyTable
                },
                {stepNumber: 3, stepperLabel: 'Viewing the full profile details',
                    triggerElement: fullView
                           
                },
                {stepNumber: 4, stepperLabel: 'Submit',
                    triggerElement: getLaststepTemplate('authorization',4)
                }

            ]
}