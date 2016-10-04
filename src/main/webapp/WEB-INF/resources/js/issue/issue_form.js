$(document).ready(function () {

    //Validation of Issue form
    $.validator.setDefaults({
        errorClass: 'help-block',
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        }
    });

    $("#issueForm").validate({
        rules: {
            "title": {
                required: true,
                maxlength: 32
            },
            "type": {
                required: true
            },
            "priority": {
                required: true
            },
            "projectRelease": {
                required: true
            },
            "project": {
                required: true
            },
            "assignee": {
                required: true
            },
            "estimateTime": {
                required: true
            },
            "description": {
                required: true,
                maxlength: 10000
            }
        },
        messages: {
            "title": {
                required: "Please enter the issue title",
                maxlength: "Issue title must be no longer than 32 characters"
            },
            "type": {
                required: "Please select issue type"
            },
            "priority": {
                required: "Please select issue priority"
            },
            "projectRelease": {
                required: "Please select the release"
            },
            "project": {
                required: "Please select the project"
            },
            "assignee": {
                required: "Please select an assignee"
            },
            "estimateTime": {
                required: "Please enter estimated time"
            },
            "description": {
                required: "Please enter description",
                maxlength: 10000
            }

        }
    });
});