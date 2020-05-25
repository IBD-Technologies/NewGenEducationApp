
$(document).ready(function() {
screenStepper=[{
                       screenName:'extraCurricular',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="extraCurricular";
     createStepper('Default',1,1,3);
     
});
