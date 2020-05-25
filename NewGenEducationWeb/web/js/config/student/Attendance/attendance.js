
$(document).ready(function() {

	screenStepper=[{screenName:'studentAttendance',
                    // stepper for student profile
                    stepper:[
                    queryConfig
                           ] 
                      }];
	screenName="studentAttendance";
     createStepper('query',1,1,3);
     selectedOperation="query";
     
});