$(document).ready(function () {

    //Validation of User edit form
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
        }
    });

    //text editor
    CKEDITOR.replace('editor1', {toolbar: 'Basic'});

    //Edit User
    $('#chooseProject').change(function(){
        //show fields of choosing user role and project
        if ($(this).is(':checked')){
            $('#role').show();
            $('#project').show();
        }else{
            $('#project').hide();
            $('#role').hide();
            $('#projectInput').val($('#projectDefault').html()).change();
            $('#roleInput').val($('#roleDefault').html()).change();
        }
    });
    
    if($('#roleInput').val() == 'ROLE_USER'){
        //set default value to user's project if user role is USER
        $('#projectInput').val(null).change();
    }
    else if($('#projectInput').val() == null){
        //set default value of user role if user's project is null
        $('#roleInput').val('ROLE_USER').change();
    }
});