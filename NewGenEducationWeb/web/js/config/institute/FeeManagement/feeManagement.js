
$(document).ready(function() {
screenStepper=[{
                       screenName:'feeManagement',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="feeManagement";
     createStepper('Default',1,1,3);
     
});
