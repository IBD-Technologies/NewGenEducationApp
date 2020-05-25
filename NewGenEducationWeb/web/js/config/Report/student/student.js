
$(document).ready(function() {

	screenStepper=[{screenName:'studentReport',
                    // stepper for student profile
                    stepper:[
                    queryConfig
                           ] 
                      }];
	screenName="studentReport";
     createStepper('query',1,1,2);
     selectedOperation="query";
     
});