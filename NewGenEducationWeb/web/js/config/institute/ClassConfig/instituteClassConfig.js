
$(document).ready(function() {
screenStepper=[{
                       screenName:'instituteClassConfig',
                       stepper:[
                       defaultConfig,
                       instituteClassCreateConfig,
                       instituteClassQueryConfig,
                       instituteClassModifyConfig,
                       instituteClassDeleteConfig,
                       instituteClassAuthConfig
                       ]
                     }];
	screenName="instituteClassConfig";
     createStepper('Default',1,1,3);
     
});
