
$("#general").on('click',function(){
alert("tab clicked");
 $([document.documentElement, document.body]).animate({
        scrollTop: $(".nav-tabs").offset().top
    }, 2000);
});
	



