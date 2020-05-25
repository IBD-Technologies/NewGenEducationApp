
$(document).ready(function() {
screenStepper=[{
                       screenName:'videoAssignment',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="videoAssignment";
     createStepper('Default',1,1,3);
     
});
