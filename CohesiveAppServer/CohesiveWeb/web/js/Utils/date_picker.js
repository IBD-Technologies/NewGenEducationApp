/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
*/


function fnDatePickersetDefault(id,eventHanlder)
{
	 $("#"+id).datepicker({ dateFormat: "dd-mm-yy",defaultDate:0,showButtonPanel: true, 

onClose:eventHanlder,changeYear:true,changeMonth:true,yearRange:"-80:+50",
 beforeShow: function( input ) {
        setTimeout(function() {
            var buttonPane = $( input )
                .datepicker( "widget" )
                .find( ".ui-datepicker-buttonpane" );
            $( "<button>", {
                text: "Clear",
                click: function() {
                    $.datepicker._clearDate( input );
                }
            }).appendTo( buttonPane ).addClass("ui-datepicker-clear ui-state-default ui-priority-primary ui-corner-all");
        }, 1 );
    },
    onChangeMonthYear: function( year, month, instance ) {
        setTimeout(function() {
            var buttonPane = $( instance )
                .datepicker( "widget" )
                .find( ".ui-datepicker-buttonpane" );
            $( "<button>", {
                text: "Clear",
                click: function() {
                    $.datepicker._clearDate( instance.input );
                }
            }).appendTo( buttonPane ).addClass("ui-datepicker-clear ui-state-default ui-priority-primary ui-corner-all");
        }, 1 );
    }
}).focus(function (){$(this).blur()});

	 var CurrentDate = new Date();
	 if ($("#"+id).datepicker( "getDate") ==null)
	    $("#"+id).datepicker( "setDate", CurrentDate);
	  
	  $("#"+id+"Show").click(function(){
	  
	  $("#"+id).datepicker( "show" );
	  $('#ui-datepicker-div').css("z-index","99");
	  });
	
	
}
function fnCalendersetDefault(id)
{

    $("#"+id).datepicker({ dateFormat: "dd-mm-yy",changeYear:true,changeMonth:true,yearRange:"-80:+50"}).focus(function (){$(this).blur()});
	
}

function fnHolidayCalendersetDefault(id,month,year,eventHanlder)
{
    
    $("#"+id).datepicker({ dateFormat: "dd-mm-yy",defaultDate:new Date(year,parseInt(month)-1,1),
	selectOtherMonths: false,stepMonths: 0,beforeShowDay:setHolidays,onSelect:eventHanlder
});
$( "#from" ).datepicker( "option", "disabled", false );
//.focus(function (){$(this).blur()});
var CurrentDate = new Date(year,parseInt(month)-1,1);
 $("#"+id).datepicker( "setDate", CurrentDate);
 $("#"+id).datepicker( "show" );
// $("#"+id).datepicker( "minDate", -30);
 //$("#"+id).datepicker( "maxDate", "+1M");
 
}