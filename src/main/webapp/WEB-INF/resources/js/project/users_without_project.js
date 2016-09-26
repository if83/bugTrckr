$(document).ready(function () {
    //Modal for adding Project Manager to project
    $('.addPMBtn').click(function (e) {
        e.preventDefault();
        $('#addingPM .addPMBtnConfirm').attr("href", $(this).attr("href"));
        $('#addingPM').find('.modal-body').html($(this).find('.addPMNotification').html());
        $('#addingPM').modal();
    });

    //Modal for adding User to project
    $('.userRoleBtn').click(function (e) {
        e.preventDefault();
        $('#userRoleForm').attr("action", $(this).attr("href"));
        $('#userRoleModal').modal();
    });
});