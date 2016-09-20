$(document).ready(function () {
    var msg = $("#message").text();
    $('#modalChanges .modal-content').css('background-color','#DBFFD5');
    if(msg.length > 0){
        $('#modalChanges').find('.modal-body').html(msg);
        $('#modalChanges').modal('show');
        setTimeout(function(){$('#modalChanges').modal('hide')}, 3000);
    };
});