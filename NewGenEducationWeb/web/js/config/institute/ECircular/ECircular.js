
$(document).ready(function() {
screenStepper=[{
                       screenName:'ECircular',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="ECircular";
     createStepper('Default',1,1,3);
     
});
