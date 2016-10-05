$(document).ready(function () {

    // Text editor
    CKEDITOR.replace('editor1', {
        toolbar: 'Basic'
    });

    //Management of project's options
    if ($('#guest').is(':checked')) {
        $('#enableView').show();
    } else {
        $('#enableView').hide();
    }
    $('#guest').change(function () {
        if ($(this).is(':checked')) {
            $('#enableView').show();
            $('#issue').attr('disabled', false);
            $('#comment').attr('disabled', false);
        } else {
            $('#enableView').hide();
            $('#issue').attr('disabled', true);
            $('#comment').attr('disabled', true);
        }
    });

    //Validation of Project Form
    $.validator.setDefaults({
        errorClass: 'help-block',
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        }
    });
    $("#projectForm").validate({
        ignore: [],
        rules: {
            "title": {
                required: true,
                maxlength: 20
            },
            "description": {
                required: function () {
                    CKEDITOR.instances.editor1.updateElement();
                },
                maxlength: 10000
            },
        },
        messages: {
            "title": {
                required: "Please enter project's title",
                maxlength: "Project's title must be not longer than 20 characters"
            },
            "description": {
                required: "Please enter project's description",
                maxlength: "Project's description must be not longer than 10000 characters"
            },
        },
    });

    //cancel button of form
    $("#cancelBtn").click(function (event) {
        event.preventDefault();
        window.location.href = document.referrer;
    });
});