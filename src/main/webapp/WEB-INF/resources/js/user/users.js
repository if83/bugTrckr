$(document).ready(function () {
    //notification messages
    $('#modalChanges .modal-content').css('background-color','#DBFFD5');
    var msg = $("#message").text();
    if(msg.length > 0){
        $('#modalChanges').find('.modal-body').html(msg);
        $('#modalChanges').modal('show');
        setTimeout(function(){$('#modalChanges').modal('hide')}, 3000);
    }
});