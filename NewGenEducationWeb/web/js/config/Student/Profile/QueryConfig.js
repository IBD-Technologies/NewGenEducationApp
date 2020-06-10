var QueryConfig = {Operation: 'query',
    startStep: 1,
    endStep: 4,
    step:
            [{stepNumber: 1, stepperLabel: 'Select Operation',
                    triggerElement: stepperOneTemplate},
                {stepNumber: 2, stepperLabel: 'Search record for view',
                    triggerElement: searchFilter

                },
                {stepNumber: 3, stepperLabel: 'Choose record for Full detailed view',
                    triggerElement: readOnlyTable
                },
                {stepNumber: 4, stepperLabel: 'Full view of chosen record',
                    triggerElement: fullView

                }
            ]
};