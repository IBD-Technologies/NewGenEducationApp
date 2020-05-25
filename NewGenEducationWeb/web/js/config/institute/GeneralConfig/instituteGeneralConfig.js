
$(document).ready(function() {
screenStepper=[{
                       screenName:'InstituteConfiguration',
                       stepper:[
                       defaultConfig,
                       instituteQueryConfig,
                       instituteModificationConfig,
                       instituteDeletionConfig,
                       instituteAuthConfig
                       ]
                     }];
	screenName="InstituteConfiguration";
     createStepper('Default',1,1,3);
});
