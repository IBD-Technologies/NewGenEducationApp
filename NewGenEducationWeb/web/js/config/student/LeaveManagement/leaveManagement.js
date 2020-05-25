
$(document).ready(function() {
screenStepper=[{
                       screenName:'leaveManagement',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="leaveManagement";
     createStepper('Default',1,1,3);
     
});
