
$(document).ready(function() {
screenStepper=[{
                       screenName:'feePayment',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="feePayment";
     createStepper('Default',1,1,3);
     
});
