
$(document).ready(function() {

	screenStepper=[{screenName:'StudentTimeTable',
                    // stepper for student profile
                    stepper:[
                    queryConfig
                           ] 
                      }];
	screenName="StudentTimeTable";
     createStepper('query',1,1,2);
     selectedOperation="query";
     
});