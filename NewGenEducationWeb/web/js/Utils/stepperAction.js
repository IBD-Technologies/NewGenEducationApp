var stepper
function stepperAction(currentStep, startStep, endStep) {


    var stepperEl = document.getElementById('stepper')

    // stepper = new Stepper(stepperEl);
    $('.next').bind('click', nextClickHandler);
    $('.previous').bind('click', previousClickHandler);

    $.AdminBSB.dropdownMenu.activate();
    $.AdminBSB.input.activate();
    $.AdminBSB.select.activate();
    $.AdminBSB.search.activate();
    $('#BSbtninfo').filestyle({
        buttonName: 'btn-info',
        buttonText: ' Select a File',
        buttonBefore: false
    });
    $('#BSbtninfo1').filestyle({
        buttonName: 'btn-info',
        buttonText: ' Select a File',
        buttonBefore: false
    });
    //Bootstrap datepicker plugin
    /*$('#bs_datepicker_container input').datepicker({
     autoclose: true,
     format: 'dd-mm-yyyy',
     container: '#bs_datepicker_container'
     });*/

    $('.datepicker').bootstrapMaterialDatePicker({
        // format: 'dddd DD MMMM YYYY',
        format: 'DD-MM-YYYY',
        clearButton: true,
        weekStart: 1,
        time: false,
        autoclose: true
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
// }
    //Masked Input ============================================================================================================================
    var $demoMaskedInput = $('.demo-masked-input');
    //Mobile Phone Number
    $demoMaskedInput.find('.mobile-phone-number').inputmask('+(99) 9999999999', {placeholder: '+(__) __________'});
    //Phone Number
    $demoMaskedInput.find('.phone-number').inputmask('+99 (999) 999-99-99', {placeholder: '+__ (___) ___-__-__'});
    //Email
    $demoMaskedInput.find('.email').inputmask({alias: "email"});
    //Dollar Money
    $demoMaskedInput.find('.money-dollar').inputmask('99,99 $', {placeholder: '__,__ $'});
    //Euro Money
    $demoMaskedInput.find('.money-euro').inputmask('99,99 €', {placeholder: '__,__ €'});

    /*$('.LastItem').first().focus(function () {
     console.log('Raj Iam Here');
     //window.scrollTo({bottom: -20, behavior: 'smooth'});
     $("html, body").animate({ scrollTop: $(document).height() }, "slow");
     });*/
    // $('#VideoURL').focusout(function(){
    //   $('.videoAssignment').css("display","block");
    // });
    $('.subScreenoperations').click(function (event)
    {
        stepOneSetAction(this.id)
    });
    
    $('#cancel').click(function ()
    {
        var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();
   
        for (i=$scope.currentStep;i>=1;i--)
        {
            previousClickHandler();
        }
    }  
    );
   $('.searchField').keyup(function()
    {    
        searchText =$('#'+this.id).val(); 
      if(this.id.includes('Student'))
      {
          
          SearchConfig={fieldID:this.id,headings:['Name','Id','Class'],
                                    apiResultCols:['StudentName','StudentId','Standard'],
                                    resultreduceFields:'StudentName~StudentId',/*Api Result column and search fields and reduce fields should have same name*/
                                    searchService:'StudentSearchService'};
                     }  
                     
                   launchSuggestion();  
                 }                    
                             
     );    
     
    
}
