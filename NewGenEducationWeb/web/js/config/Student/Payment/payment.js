
$(document).ready(function() {

	screenStepper=[{screenName:'payment',
                    // stepper for student profile
                    stepper:[
                    queryConfig
                           ] 
                      }];
	screenName="payment";
     createStepper('query',1,1,3);
     selectedOperation="query";
     
});