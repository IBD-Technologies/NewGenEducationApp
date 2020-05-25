
$(document).ready(function() {
screenStepper=[{
                       screenName:'softSkill',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="softSkill";
     createStepper('Default',1,1,3);
     
});
