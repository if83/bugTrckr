$(document).ready(function () {
    //add pop-up window after creating or updating issue
    var msg = $("#messageIssue").text();
    $('#modalChangesIssue .modal-content').css('background-color','#DBFFD5');
    if(msg.length > 0){
        $('#modalChangesIssue').find('.modal-body').html(msg);
        $('#modalChangesIssue').modal('show');
        setTimeout(function(){$('#modalChangesIssue').modal('hide')}, 1800);
    };

    //Modal for removing issue
    $('.removeIssueBtn').click(function (e) {
        e.preventDefault();
        $('#removeIssueModal').find('.modal-body').html($(this).find('.removeIssueNotification').html());
        $('#removeIssueModal .confirmIssueRemoval').attr("href", $(this).attr("href"));
        $('#removeIssueModal').modal();
    });

    //Modal for removing my issue
    $('.removeMyIssueBtn').click(function (e) {
        e.preventDefault();
        $('#removeMyIssueModal').find('.modal-body').html($(this).find('.removeMyIssueNotification').html());
        $('#removeMyIssueModal .confirmMyIssueRemoval').attr("href", $(this).attr("href"));
        $('#removeMyIssueModal').modal();
    });
});

