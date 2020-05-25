
$(document).ready(function() {
	screenStepper=[{screenName:'userRole',
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
	screenName="userRole";
     createStepper('Default',1,1,3);
     
});