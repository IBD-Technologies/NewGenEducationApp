/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

$('.indexLink').click(function(){
    window.parent.$("#frame").attr('src', '');
    var $scope = angular.element(window.parent.document.getElementById('MainCtrl')).scope();
        $scope.dashBoardShow=true;
        $scope.$apply();
});


});