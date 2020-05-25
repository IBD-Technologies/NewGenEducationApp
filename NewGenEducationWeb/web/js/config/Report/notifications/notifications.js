
$(document).ready(function() {

	screenStepper=[{screenName:'paymentReport',
                    // stepper for student profile
                    stepper:[
                    queryConfig
                           ] 
                      }];
	screenName="paymentReport";
     createStepper('query',1,1,2);
     selectedOperation="query";
     
});