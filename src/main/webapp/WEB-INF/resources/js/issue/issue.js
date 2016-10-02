$(document).ready(function () {
    //add pop-up window after creating or updating issue
    var msg = $("#messageIssue").text();
    $('#modalChangesIssue .modal-content').css('background-color','#DBFFD5');
    if(msg.length > 0){
        $('#modalChangesIssue').find('.modal-body').html(msg);
        $('#modalChangesIssue').modal('show');
        setTimeout(function(){$('#modalChangesIssue').modal('hide')}, 1800);
    };
});

