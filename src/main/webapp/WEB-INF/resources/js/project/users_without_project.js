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

    //value of main part of search url
    var url = $('#search').attr('action');
    $('a[data-toggle="tab"]').on("shown.bs.tab", function (e) {
        //value of id of chosen tab
        var id = $(e.target).attr("href");
        localStorage.setItem('selectedTab', id);
        //adding of specific part of search url in dependence of chosen tab
        if (id == "#usersInProject") {
            $('#search').attr('action', url + "/usersInProject_search");
        } else {
            $('#search').attr('action', url + "/allUsers_search");
        }
    });

    var selectedTab = localStorage.getItem('selectedTab');
    if (selectedTab != null) {
        $('a[data-toggle="tab"][href="' + selectedTab + '"]').tab('show');
    }
});