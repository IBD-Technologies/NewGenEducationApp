
$(document).ready(function() {

	screenStepper=[{screenName:'progressCard',
                    // stepper for student profile
                    stepper:[
                    defaultConfig,
                    queryConfig,
                    parentSignature
                           ] 
                      }];
	screenName="progressCard";
     createStepper('Default',1,1,3);
     
});