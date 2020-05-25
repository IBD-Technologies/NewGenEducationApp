$(document).ready(function() {
screenStepper=[{
                       screenName:'holidayMaintenance',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="holidayMaintenance";
     createStepper('Default',1,1,3);
});
