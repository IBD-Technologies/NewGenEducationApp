
$(document).ready(function() {
screenStepper=[{
                       screenName:'timetable',
                       stepper:[
                       defaultConfig,
                       createConfig,
                       queryConfig,
                       modificationConfig,
                       deletionConfig,
                       authConfig
                       ]
                     }];
	screenName="timetable";
     createStepper('Default',1,1,3);
     
});
