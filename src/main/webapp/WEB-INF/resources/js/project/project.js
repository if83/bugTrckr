$(document).ready(function () {
    //notification messages
    $('#modalChanges .modal-content').css('background-color','#DBFFD5');
    var msg = $("#message").text();
    if(msg.length > 0){
        $('#modalChanges').find('.modal-body').html(msg);
        $('#modalChanges').modal('show');
        setTimeout(function(){$('#modalChanges').modal('hide')}, 3000);
    };

    //Modal for change of user's role
    $('.changeRoleBtn').click(function (e) {
        e.preventDefault();
        $('#changRoleModal .confirmChangeRole').attr("href", $(this).attr("href"));
        $('#changRoleModal').find('.modal-body').html($(this).find('.changeRoleBtn').html());
        $('#changRoleModal').modal();
    });

    //Modal for removing of user from project
    $('.removeUserBtn').click(function (e) {
        e.preventDefault();
        $('#removingOfUser').find('.modal-body').html($(this).find('.removeUserNotification').html());
        $('#removingOfUser .confirmRemove').attr("href", $(this).attr("href"));
        $('#removingOfUser').modal();
    });

    //Modal for removing of release from project
    $('.removeReleaseBtn').click(function (e) {
        e.preventDefault();
        $('#removingOfRelease').find('.modal-body').html($(this).find('.removeReleaseNotification').html());
        $('#removingOfRelease .confirmRemoveRelease').attr("href", $(this).attr("href"));
        $('#removingOfRelease').modal();
    });
});