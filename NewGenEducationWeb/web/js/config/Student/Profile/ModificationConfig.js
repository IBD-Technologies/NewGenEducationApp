var ModificationConfig = {Operation: 'modification',
    startStep: 1,
    endStep: 5,
    step:
            [{stepNumber: 1, stepperLabel: 'Select Operation',
                    triggerElement: stepperOneTemplate},
                {stepNumber: 2, stepperLabel: 'Search record for modification',
                    triggerElement: searchFilter
                },
                {stepNumber: 3, stepperLabel: 'Choose the record for modification',
                    triggerElement: readOnlyTable
                },
                {stepNumber: 4, stepperLabel: 'Modify required student details',
                    triggerElement:fullView
                },
                {stepNumber: 5, stepperLabel: 'Submit',
                    triggerElement: getLaststepTemplate('modification',5)
                }

            ]
};