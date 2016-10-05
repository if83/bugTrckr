$(document).ready(function () {

    //notification messages
    $('#modalChanges .modal-content').css('background-color', '#DBFFD5');
    var msg = $("#message").text();
    if (msg.length > 0) {
        $('#modalChanges').find('.modal-body').html(msg);
        $('#modalChanges').modal('show');
        setTimeout(function () {
            $('#modalChanges').modal('hide')
        }, 3000);
    }

    $('select').select2({
        placeholder: 'Roles',
        width: "200px"
    });

    //Modal for removing user
    $('.removeUserBtn').click(function (e) {
        e.preventDefault();
        $('#removingOfUser').find('.modal-body').html($(this).find('.removeUserNotification').html());
        $('#removingOfUser .confirmUserRelease').attr("href", $(this).attr("href"));
        $('#removingOfUser').modal();
    });

})