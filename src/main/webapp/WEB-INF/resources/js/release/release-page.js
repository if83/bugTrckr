$(document).ready(function () {

    // changing assignee of issue from dropdown in issues-table
    $(".users-dropdown li").on('click', function () {
        var selectedUserName = $(this).text();
        var selectedUserId = $(this).val();
        $(this).parents(".users-dropdown").find(".users-label").text(selectedUserName);
        var issueId = $(this).parents("tr").find("input[name='issueId']").val();
        var queryObj = {};
        queryObj.userId = selectedUserId;
        $.ajax({
            url: "/issue/" + issueId + "/changeAssignee",
            data: queryObj,
            type: 'POST'
        });
    });

    // changing status of issue from dropdown in issues-table
    $(".statuses-dropdown").on('click', 'li', function () {
        var selectedStatusName = $(this).text();
        $(this).parents(".statuses-dropdown").find(".statuses-label").text(selectedStatusName);
        var issueId = $(this).parents("tr").find("input[name='issueId']").val();
        var queryObj = {};
        queryObj.status = selectedStatusName;
        $.ajax({
            url: "/issue/" + issueId + "/changeStatus",
            data: queryObj,
            type: 'POST'
        });
    });

    $(".statuses-dropdown .statuses-label").on('click', function () {
        $(".statuses-dropdown .dropdown-menu").html('');
        var issueId = $(this).parents("tr").find("input[name='issueId']").val();
        var queryObj = {};
        queryObj.issueId = issueId;
        $.ajax({
            url: "/getAvaliableIssueStatuses",
            data: queryObj,
            type: 'POST',
            success: function (statuses) {
                $.each(statuses, function (index, value) {
                    var html = "<li value='" + value + "'><a href=''>" + value + "</a></li>";
                    $(".statuses-dropdown .dropdown-menu").append(html);
                });
            }
        });
    });

});


