
$(document).ready(function() {

	screenStepper=[{screenName:'StudentRegister',
                    // stepper for student profile
                    stepper:[
                    queryConfig
                           ] 
                      }];
	screenName="StudentRegister";
     createStepper('query',1,1,2);
     selectedOperation="query";
     
});