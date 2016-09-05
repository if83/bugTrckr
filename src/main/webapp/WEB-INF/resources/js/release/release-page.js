$(document).ready(function () {

    // changing assignee of issue from dropdown in issues-table
    $(".users-dropdown").on('click', 'li', function () {
        var selectedUserName = $(this).text();
        var queryObj = {};
        queryObj.issueId = $(this).parents("tr").find("input[name='issueId']").val();
        queryObj.data = $(this).val();
        queryObj.action = "changeAssignee";
        $(this).parents(".users-dropdown").find(".users-label").text(selectedUserName);
        $.ajax({
            url: "/issue/changeIssue",
            data: queryObj,
            type: 'POST'
        });
        $(".users-dropdown").removeClass("open");
        return false;
    });

    // changing status of issue from dropdown in issues-table
    $(".statuses-dropdown").on('click', 'li', function () {
        var selectedStatusName = $(this).text();
        var queryObj = {};
        queryObj.issueId = $(this).parents("tr").find("input[name='issueId']").val();
        queryObj.data = selectedStatusName;
        queryObj.action = "changeStatus";
        $(this).parents(".statuses-dropdown").find(".statuses-label").text(selectedStatusName);
        $.ajax({
            url: "/issue/changeIssue",
            data: queryObj,
            type: 'POST'
        });
        $(".statuses-dropdown").removeClass("open");
        return false;
    });

    // load avaliable statuses for dropdown-menu
    $(".statuses-dropdown").on('click', '.statuses-label', function () {
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


