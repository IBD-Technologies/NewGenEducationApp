
$(document).ready(function() {

	screenStepper=[{screenName:'examSchedule',
                    // stepper for student profile
                    stepper:[
                    queryConfig
                           ] 
                      }];
	screenName="examSchedule";
     createStepper('query',1,1,2);
     selectedOperation="query";
     
});