
$(document).ready(function() {
screenStepper=[{
                       screenName:'notification',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="notification";
     createStepper('Default',1,1,3);
     
});
