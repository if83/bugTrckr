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
        if ($(this).is(':checked')){
            $('#project').show();
            $('#role').show();
        }
        //hide fields of choosing user role and project and set default values
        else {
            $('#project').hide();
            $('#role').hide();
            $('#projectInput').val(null).change();
            $('#roleInput').val('ROLE_USER').change();
        }

    if($('#roleInput').val() == 'ROLE_USER'){
        //set default value to user's project if user role is USER
        $('#projectInput').val(null).change();
    }
    if($('#projectInput').val() == null){
        //set default value of user role if user's project is null
        $('#roleInput').val('ROLE_USER').change();
    }
});