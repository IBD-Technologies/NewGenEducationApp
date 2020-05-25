
$(document).ready(function() {
screenStepper=[{
                       screenName:'examSchedule',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="examSchedule";
     createStepper('Default',1,1,3);
     
});
