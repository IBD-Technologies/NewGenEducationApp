
$(document).ready(function() {
	screenStepper=[{screenName:'userProfile',
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
	screenName="userProfile";
     createStepper('Default',1,1,3);
     
});