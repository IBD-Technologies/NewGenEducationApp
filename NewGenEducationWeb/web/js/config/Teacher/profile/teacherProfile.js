
$(document).ready(function() {
	screenStepper=[{screenName:'teacherProfile',
                    // stepper for student profile
                    stepper:[
                    defaultConfig,
                    createConfig,
                    queryConfig,
                    modificationConfig,
                    deletionConfig,
                    authConfig
                           ] 
                      }];
	screenName="teacherProfile";
     createStepper('Default',1,1,3);
     
});