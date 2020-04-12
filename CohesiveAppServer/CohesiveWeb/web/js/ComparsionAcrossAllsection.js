/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//var passFaillclick = false;
var gradeShow=false;
var passFailShow=false;
var toppershow=false;
function gradeLoad()
{
    gradeShow=true;
    // e.preventDefault();
    var options = {
    
      title:{
        text: "Comparison of gradewise report across all the sctions in the standard for each exam.",
         fontSize:20
      },
       legend: {
		fontSize:25
	},
      	subtitles:[
		{
			text: "Half Yearly Exam"
		}
		],
      axisY: {
        title: "Percentage Of Mark",
         suffix: " %",
        labelFontColor: "blue",
        maximum: 100,
        interval:20
      },
      axisX: {
          labelFontColor: "blue",
        title: "Standard"
        
        
      },
      data: [
      {
        type: "column",
        showInLegend: true,
        legendText: "A Grade",
        color: "Blue",
        dataPoints: [
        { y: 25, label: "II A"},
        { y: 40, label: "II B"},
        { y: 60, label: "II C"},
         { y: 60, label: "II D"},
            { y: 23, label: "II E"},
               { y: 23, label: "II F"},
                  { y: 23, label: "II G"}
        ]
      },
      {
        type: "column",
        showInLegend: true,
        legendText: "B Grade",
        color: "Lightgreen",
        dataPoints: [
        { y: 10, label: "II A"},
        { y: 25, label: "II B"},
        { y: 23, label: "II C"},
           { y: 23, label: "II D"},
              { y: 23, label: "II E"},
              { y: 23, label: "II F"},
                  { y: 23, label: "II G"}
        ]
      },
      {
        type: "column",
        showInLegend: true,
        legendText: "C Grade",
        color: "Orange",
        dataPoints: [
        { y: 85, label: "II A"},
        { y: 28, label: "II B"},
        { y: 46, label: " II C"},
           { y: 23, label: "II D"},
              { y: 23, label: "II E"},
              { y: 23, label: "II F"},
                  { y: 23, label: "II G"}
        ]
      },
     {
        type: "column",
        showInLegend: true,
        legendText: "D Grade",
        color: "Violet",
        dataPoints: [
        { y: 46, label: "II A"},
        { y: 28, label: "II B"},
        { y: 85, label: " II C"},
           { y: 23, label: "II D"},
              { y: 23, label: "II E"},
              { y: 23, label: "II F"},
                  { y: 23, label: "II G"}
        ]
      }  
      ]
    };
$("#grade_performance_eachSection").CanvasJSChart(options);
};
function passfailLoad()
{
    passFailShow=true;
  CanvasJS.addColorSet("greenShades",
                [//colorSet Array

                "blue",
                "Orange",
                "lightgreen",
                "Yellow",
                "red",
                "violet"
                    ]);

   
//Better to construct options first and then pass it as a parameter
var options = {
    colorSet: "greenShades",
	title: {
		text: "Comparison of Pass/Fail report across all the sections in the standard for each exam." ,
                 fontSize:20
               
	},
         legend: {
		fontSize:15
	},
        	subtitles:[
		{
			text: "Half Yearly Exam"
		}
		],
                   axisY: {
        title: "Percentage Of Pass",
         suffix: " %",
        labelFontColor: "blue",
        maximum: 100,
        interval:20
      },
      axisX: {
          labelFontColor: "blue",
        title: "Standard"
        
        
      },
	data: [              
	{
		// Change type to "doughnut", "line", "splineArea", etc.
		  type: "column",
        fontSize:10,
        legendText: "Mathematics",
        showInLegend: true,
        dataPoints: [
        { y: 78, label: "II A"},
        { y: 71, label: "II B"},
         { y: 78, label: "II C "}
        ]
	},
      {
                    
        type: "column",
        legendText: "Physics",
        showInLegend: true,
        dataPoints: [
      { y: 71,abel: "II A"},
        { y: 76, label: "II B"},
         { y: 88, label: "II C "}
        ]
                 },
    {
        type: "column",
        legendText: "Chemistry",
        showInLegend: true,
        dataPoints: [
        { y: 78, label: "II A"},
        { y: 71, label: "II B"},
         { y: 78, label: "II C "}
        ]
      },
       {
        type: "column",
        legendText: "Computer",
        showInLegend: true,
        dataPoints: [
        { y: 71,abel: "II A"},
        { y: 76, label: "II B"},
         { y: 88, label: "II C "}
        ]
      },
      {
        type: "column",
        legendText: "English",
        showInLegend: true,
        dataPoints: [
       { y: 78, label: "II A"},
        { y: 71, label: "II B"},
         { y: 78, label: "II C "}
        ]
      },
          {
        type: "column",
        legendText: "Tamil",
        showInLegend: true,
        dataPoints: [
       { y: 69, label: "II A"},
        { y: 72, label: "II B"},
         { y: 78, label: "II C "}
        ]
      }
	]
};

$("#passFail").CanvasJSChart(options);
    //passFaillclick=true;
};
function topperLoad()
{ 
     toppershow=true;
    
    CanvasJS.addColorSet("greenShades",
                [//colorSet Array

                "blue",
                "Orange",
                "lightgreen",
                "Yellow",
                "red",
                "violet"
                ]);
var options = {
        colorSet: "greenShades",
      axisY: {
          title: "Percentage Of Mark",  
          minimum:0,
          maximum:100,
          interval: 20,
          labelFontColor: "blue",
          titleFontColor:"black",
          gridThickness: 1,
          suffix:"%"
        
      }, 
       axisX: {
        title: "Standard",
        labelFontColor: "blue",
        labelFontSize: 10,
        titleFontColor:"black",
        includeZero: true,
        gridThickness: 1 
        	  
        
       
        
      },
     
       title:{
        text: "Comparison oF Class Topper Mark For a Subject Across All The Sections In The Standard For Each Exam.",
        /* fontColor: "black"  */
         fontSize:18
      },
      legend: {
		fontSize: 13        
	},
       subtitles:[
  {text: "Half Yearly Exam"
  }
       ],
           data: [
      {
        type: "column",
        fontSize:10,
        legendText: "Mathematics",
        showInLegend: true,
        dataPoints: [
        { y: 78, label: "II A"},
        { y: 71, label: "II B"},
        { y: 88, label: "II C"}
        ]
      },
      {
        type: "column",
        legendText: "Physics",
        showInLegend: true,
        dataPoints: [
      { y: 88, label: "II A"},
        { y: 79, label: "II B"},
        { y: 55, label: "II C"}
        ]
      },
      {
        type: "column",
        legendText: "Chemistry",
        showInLegend: true,
        dataPoints: [
       { y: 65, label: "II A"},
        { y: 90, label: "II B"},
        { y: 69, label: "II C"}
        ]
      },
      {
        type: "column",
        legendText: "Computer",
        showInLegend: true,
        dataPoints: [
       { y:97, label: "II A"},
        { y: 69, label: "II B"},
        { y: 75, label: "II C"}
        ]
      },
       {
        type: "column",
        legendText: "English",
        showInLegend: true,
        dataPoints: [
       { y: 78, label: "II A"},
        { y: 61, label: "II B"},
        { y: 88, label: "II C"}
        ]
      },
       {
        type: "column",
        legendText: "Tamil",
        showInLegend: true,
        dataPoints: [
       { y: 65, label: "II A"},
        { y: 93, label: "II B"},
        { y: 78, label: "II C"}
        ]
      }
      ]
    };
    $("#topperMarkSubject").CanvasJSChart(options);
};


$(document).ready(function(){
    
     gradeLoad();
     $('#gradeTab').on("shown.bs.tab",function(){
     
      $('#gradeTab').off(); // to remove the binded event after the initial rendering
  });
    $('#passfailTab').on("shown.bs.tab",function(){
        if(!passFailShow){
      passfailLoad();
        }
      $('#passfailTab').off(); // to remove the binded event after the initial rendering
  });
  $('#subjectTopperTab').on("shown.bs.tab",function(){
        if(!toppershow){
    topperLoad();
        }
      $('#subjectTopperTab').off(); // to remove the binded event after the initial rendering
  });
  
    //gradeLoad();
    //passfailLoad();
    //$('#gradeTab').on('shown.bs.tab', function () {
    //gradeLoad();
   // $('#gradeTab').tab('show');
   //passfailLoad();
    //$('#gradeTab').off();
   // });
    
    //$('#passfailTab').on('shown.bs.tab', function (e) {
     //e.preventDefault();
    /* if(!passFaillclick)
     {
   //passfailLoad();
   
};
  $('#passfailTab').tab('show');
});


    $('#avghighTab').on('click', function (e) {
    // e.preventDefault();
     $('#avghighTab').tab('show');
    
});

/*$(document).ready(function(){
   
    
    
    $('#gradeTab').on('click', );
chart.render();   
  $(this).tab('show');
});
*/
    });


/*window.onload = function () {    
 CanvasJS.addColorSet("greenShades",
                [//colorSet Array

                "blue",
                "Orange",
                "lightgreen",
                "Yellow",
                "red",
                "violet"
                ]);

     var chart = new CanvasJS.Chart("topperMark_Subject",
   /* {  
        colorSet: "greenShades",
      axisY: {
          title: "Percentage Of Mark",  
          minimum:0,
          maximum:100,
          interval: 20,
          labelFontColor: "blue",
          titleFontColor:"black",
          gridThickness: 1,
          suffix:"%"
        
      }, 
       axisX: {
        title: "Standard",
        labelFontColor: "blue",
        labelFontSize: 10,
        titleFontColor:"black",
        includeZero: true,
        gridThickness: 1 
        	  
        
       
        
      },
       title:{
        text: "Comparison oF Class Topper Mark For a Subject Across All The Sections In The Standard For Each Exam.",*/
        /* fontColor: "black"  */
       /*  fontSize:15
      },
       subtitles:[
  {text: "Half Yearly Exam"
  }
       ],
           data: [
      {
        type: "bar",
        fontSize:10,
        legendText: "Mathematics",
        showInLegend: true,
        dataPoints: [
        { y: 78, label: "II A"},
        { y: 71, label: "II B"},
        { y: 88, label: "II C"}
        ]
      },
      {
        type: "bar",
        legendText: "Physics",
        showInLegend: true,
        dataPoints: [
      { y: 88, label: "II A"},
        { y: 79, label: "II B"},
        { y: 55, label: "II C"}
        ]
      },
      {
        type: "bar",
        legendText: "Chemistry",
        showInLegend: true,
        dataPoints: [
       { y: 65, label: "II A"},
        { y: 90, label: "II B"},
        { y: 69, label: "II C"}
        ]
      },
      {
        type: "bar",
        legendText: "Computer",
        showInLegend: true,
        dataPoints: [
       { y:97, label: "II A"},
        { y: 69, label: "II B"},
        { y: 75, label: "II C"}
        ]
      },
       {
        type: "bar",
        legendText: "English",
        showInLegend: true,
        dataPoints: [
       { y: 78, label: "II A"},
        { y: 61, label: "II B"},
        { y: 88, label: "II C"}
        ]
      },
       {
        type: "bar",
        legendText: "Tamil",
        showInLegend: true,
        dataPoints: [
       { y: 65, label: "II A"},
        { y: 93, label: "II B"},
        { y: 78, label: "II C"}
        ]
      }
      ]
    });
     chart.render();
      CanvasJS.addColorSet("greenShades",
                [//colorSet Array

                "blue",
                "Orange",
                "lightgreen",
                "Yellow",
                "red",
                "violet"
                ]);

     var chart = new CanvasJS.Chart("comparsion_pass_failreport",
    {  
        colorSet: "greenShades",
      axisY: {
          title: "Percentage Of Pass",  
          minimum:0,
          maximum:100,
          interval: 20,
          labelFontColor: "blue",
          titleFontColor:"black",
          gridThickness: 1,
          suffix:"%"
        
      }, 
       axisX: {
        title: "Standard",
        labelFontColor: "blue",
        labelFontSize: 10,
        titleFontColor:"black",
        includeZero: true,
        gridThickness: 1 
        	  
        
       
        
      },*/
       /*title:{
        text: "Comparison of Pass/Fail report across all the sections in the standard for each exam.",
        /* fontColor: "black"  */
       /*  fontSize:15
      },*/
          /* data: [
      {
        type: "bar",
        fontSize:10,
        legendText: "Mathematics",
        showInLegend: true,
        dataPoints: [
        { y: 78, label: "II A"},
        { y: 71, label: "II B"},
         { y: 78, label: "II C "}
        ]
      },
      {
        type: "bar",
        legendText: "Physics",
        showInLegend: true,
        dataPoints: [
      { y: 71,abel: "II A"},
        { y: 76, label: "II B"},
         { y: 88, label: "II C "}
        ]
      },
      {
        type: "bar",
        legendText: "Chemistry",
        showInLegend: true,
        dataPoints: [
        { y: 78, label: "II A"},
        { y: 71, label: "II B"},
         { y: 78, label: "II C "}
        ]
      },
      {
        type: "bar",
        legendText: "Computer",
        showInLegend: true,
        dataPoints: [
        { y: 71,abel: "II A"},
        { y: 76, label: "II B"},
         { y: 88, label: "II C "}
        ]
      },
       {
        type: "bar",
        legendText: "English",
        showInLegend: true,
        dataPoints: [
       { y: 78, label: "II A"},
        { y: 71, label: "II B"},
         { y: 78, label: "II C "}
        ]
      },
       {
        type: "bar",
        legendText: "Tamil",
        showInLegend: true,
        dataPoints: [
       { y: 69, label: "II A"},
        { y: 72, label: "II B"},
         { y: 78, label: "II C "}
        ]
      }
      ]
    });
    chart.render();
     
};*/