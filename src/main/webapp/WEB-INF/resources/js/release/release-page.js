$(document).ready(function () {

    // change assignee of issue from dropdown in issues table
    $('.users-dropdown').on('changed.bs.select', function (e) {
        var queryObj = {};
        var issueTitle = $(this).parents("tr").find("input[name='issueTitle']").val();
        queryObj.issueId = $(this).parents("tr").find("input[name='issueId']").val();
        queryObj.inputData = $(e.currentTarget).val();
        queryObj.action = "changeAssignee";
        // show popup about change of status
        $('#modalChangeIssue').find('.modal-body')
            .html("Assignee of <strong>" + issueTitle + "</strong> changed to <strong>"
                + $(this).find('option:selected').text() + "</strong>");
        $('#modalChangeIssue').modal('show');
        $.ajax({
            url: "/issue/changeIssue",
            data: queryObj,
            type: 'POST'
        });
    });

    // change status of issue from dropdown in issues-table
    $(".statuses-dropdown").on('changed.bs.select', function (e) {
        var queryObj = {};
        var selectedStatus = $(e.currentTarget).val();
        var issueTitle = $(this).parents("tr").find("input[name='issueTitle']").val();
        queryObj.issueId = $(this).parents("tr").find("input[name='issueId']").val();
        queryObj.inputData = selectedStatus;
        queryObj.action = "changeStatus";
        // show popup about change of status
        $('#modalChangeIssue').find('.modal-body')
            .html("Status of <strong>" + issueTitle + "</strong> changed to <strong>"
                + selectedStatus + "</strong>");
        $('#modalChangeIssue').modal('show');
        $.ajax({
            url: "/issue/changeIssue",
            data: queryObj,
            type: 'POST'
        });
    });

    // change status of issue and load avaliable statuses when open statuses dropdown in issues-table
    $(".statuses-dropdown").on("show.bs.select", function (e) {
        var self = $(this);
        var queryObj = {};
        var selectedStatus = $(e.currentTarget).val();
        $(self).find('option').not(':selected').remove();
        queryObj.selectedStatus = selectedStatus;
        $.ajax({
            url: "/getAvaliableIssueStatuses",
            data: queryObj,
            type: 'POST',
            success: function (statuses) {
                $.each(statuses, function (index, value) {
                    var html = "<option value='" + value + "'>" + value + "</option>";
                    $(self).append(html);
                    $(self).selectpicker('refresh');
                });
            }
        });
    });

    // hide popup of changing issue after some time
    $('#modalChangeIssue').on('show.bs.modal', function (event) {
        setTimeout(function(){
            $('#modalChangeIssue').modal('hide')
        }, 2000);
    })

});