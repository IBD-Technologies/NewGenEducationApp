/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*$(document).ready(function(){
    fn_snackbar_exception=function(id) {
        var idSelector='#'.concat(d)
     $(idSelector).snackbar();
     $(idSelector).snackbar('show');
}});*/
function fn_mandatory_check (){
    var snackbar_exception  =  document.getElementById("snackbar");
     snackbar_exception.className = "show";
    setTimeout(function(){  snackbar_exception.className =  snackbar_exception.className.replace("show", ""); }, 3000);
}
