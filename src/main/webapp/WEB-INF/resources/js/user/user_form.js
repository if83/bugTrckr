$(document).ready(function () {
    //Validation of User Form
    $.validator.setDefaults({
        errorClass: 'help-block',
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        }
    });

    $("#userForm").validate({
        rules: {
            "email": {
                required: true,
                email: true,
                maxlength: 32
            },
            "firstName": {
                required: true,
                maxlength: 20
            },
            "lastName": {
                required: true,
                maxlength: 20
            },
            "password": {
                required: true,
                minlength: 5,
                maxlength: 15
            },
            "confirmPassword": {
                required: true,
                equalTo: "#passwordInput"
            }
        },
        messages: {
            "email": {
                required: "Please enter email",
                maxlength: "Email must be not longer than 32 characters",
                email: "Please enter valid email"
            },
            "firstName": {
                required: "Please enter first name",
                maxlength: "First name must be not longer than 20 characters"
            },
            "lastName": {
                required: "Please enter last name ",
                maxlength: "Last name must be not longer than 20 characters"
            },
            "password": {
                required: "Please enter password",
                minlength: "Password must be at least 5 characters",
                maxlength: "Password must be not longer than 15 characters"
            },
            "confirmPassword": {
                required: "Please confirm password",
                equalTo: "Password doesn't match"
            }
        }
    });

    CKEDITOR.replace('editor1', {toolbar: 'Basic'});

    //Create new User
    //show fields of choosing user role and project
    $('#chooseProject').change(function () {
        //show fields of choosing user role and project
        if ($(this).is(':checked')) {
            $('#role').show();
            $('#project').show();
            $('#projectInput').val(0).change();
        } else {
            //hide fields of choosing user role and project and set default values
            $('#project').hide();
            $('#role').hide();
            $('#projectInput').val(0).change();
            $('#roleInput').val('ROLE_USER').change();
        }
    });

    //validation of input of user's role
    $('#projectInput').select().on("change", function () {
        $('#roleInput').prop('disabled', true);
        if ($('#projectInput').val() != 0) {
            $('.roleOptionUser').hide();
            $('#roleInput').val('ROLE_QA').change();
            $('#roleInput').prop('disabled', false);
        }
        if (($('#projectInput').val() == 0)) {
            ;
            $('.roleOptionUser').show();
            $('#roleInput').val('ROLE_USER').change();
            $('#roleInput').prop('disabled', true);
        }
    });

    //validation of input of user's project
    $(function () {
        $('#roleInput').select().on("change", function () {
            if ($('#projectInput').val() != 0 && $('#roleInput').val() == 'ROLE_USER') {
                $('#projectInput').val(0).change();
            }
        });
    });

    $('#confirmForm').click(function () {
        $('#roleInput').prop('disabled', false);
    });

    //cancel button of form
    $("#cancelBtn").click(function (event) {
        event.preventDefault();
        window.location.href = document.referrer;
    });
});