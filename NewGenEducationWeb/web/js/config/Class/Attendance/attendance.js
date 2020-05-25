
$(document).ready(function() {
screenStepper=[{
                       screenName:'attendance',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="attendance";
     createStepper('Default',1,1,3);
     
});
