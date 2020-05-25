var stepper
function stepperAction(currentStep,startStep,endStep){

    
     var stepperEl = document.getElementById('stepper')

      // stepper = new Stepper(stepperEl);
     $('.next').bind('click',nextClickHandler);
     $('.previous').bind('click',previousClickHandler);
      
    $.AdminBSB.dropdownMenu.activate();
    $.AdminBSB.input.activate();
    $.AdminBSB.select.activate();
    $.AdminBSB.search.activate();
    $('#BSbtninfo').filestyle({
				buttonName : 'btn-info',
                buttonText : ' Select a File',
                buttonBefore: false
			}); 
			$('#BSbtninfo1').filestyle({
				buttonName : 'btn-info',
                buttonText : ' Select a File',
                buttonBefore: false
			});   
             //Bootstrap datepicker plugin
    $('#bs_datepicker_container input').datepicker({
        autoclose: true,
        format: 'dd-mm-yyyy',
        container: '#bs_datepicker_container'
    });
     $('.js-basic-example').DataTable({
        responsive: true
    });
     $('.js-exportable').DataTable({
        dom: 'Bfrtip',
        responsive: true,
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]
    });
    $("#student_data_table tbody tr").on("dblclick", nextClickHandler);
    
//     $(".nav-tabs").click(function(){
  
//    $('html, body').animate({
//     scrollTop: ($('body').first().offset().top)
// },500) 
//   });
  
  //$('.class').scrollTop($('#div1')[0].scrollHeight);
   // $(function () {
    //Tooltip
    $('[data-toggle="tooltip"]').tooltip({
        container: 'body'
    });

    //Popover
    $('[data-toggle="popover"]').popover();
// })
  
  /*$('.LastItem').first().focus(function () {
    console.log('Raj Iam Here');
    //window.scrollTo({bottom: -20, behavior: 'smooth'});
   $("html, body").animate({ scrollTop: $(document).height() }, "slow");
  });*/
  // $('#VideoURL').focusout(function(){
  //   $('.videoAssignment').css("display","block");
  // });
  
  
}
