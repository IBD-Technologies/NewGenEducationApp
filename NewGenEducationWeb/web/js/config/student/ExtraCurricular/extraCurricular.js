
$(document).ready(function() {

	screenStepper=[{screenName:'extraCurricular',
                    // stepper for student profile
                    stepper:[
                    defaultConfig,
                    queryConfig,
                    enroll,
                    modificationConfig,
                    authConfig
                           ] 
                      }];
	screenName="extraCurricular";
     createStepper('Default',1,1,3);
     
});