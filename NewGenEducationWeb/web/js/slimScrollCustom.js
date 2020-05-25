var slimScrolldone =false;
$(document).ready(function(){
$(".menu .list li").click(function(event){
        // alert();
        $(".menu .list li").filter('.active').removeClass("active");
        // $(this).addClass("active");
        var $el = $('.list');
               $(".menu .list li").filter('.active').removeClass("active");
        $(this).addClass("active");
             // var item = $('.menu .list li.active')[0];
             //    if (item) {
             //        var activeItemOffsetTop = item.offsetTop;
             //        // if (activeItemOffsetTop > 150)
             //         $el.slimscroll({ scrollTo: activeItemOffsetTop + 'px' });
             //    }

             var item =$('.menuHeader')[0];
             if (item) {
                    var activeItemOffsetTop = item.offsetTop;
                     //if (activeItemOffsetTop > 150)
                     $el.slimscroll({ scrollTo: activeItemOffsetTop + 'px' });
             }
                //slimScrolldone =true;
             event.preventDefault(); 

    });
});