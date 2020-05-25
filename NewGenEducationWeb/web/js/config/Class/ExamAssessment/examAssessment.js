
$(document).ready(function() {
screenStepper=[{
                       screenName:'examAssessment',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="examAssessment";
     createStepper('Default',1,1,3);
     
});
