
$(document).ready(function() {
screenStepper=[{
                       screenName:'instituteGroup',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="instituteGroup";
     createStepper('Default',1,1,3);
     
});
