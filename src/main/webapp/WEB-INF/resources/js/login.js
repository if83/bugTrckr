$(document).ready(function () {
    if(window.location.href.indexOf("/?error")> -1){
        $("#loginFormModal").modal();
        $("#error").show();
    }else {
        $("#error").hide();
    }
    $("#loginform").validate({
        rules: {
            "username": {
                required: true,
                email: true,
                maxlength: 32
            },
            "password": {
                required: true,
                minlength: 5,
                maxlength: 15
            }
        },
        messages: {
            "username": {
                email: "Email isn't correct",
                required: "Please enter email",
                maxlength: "Email must be not longer than 32 characters"
            },
            "password": {
                required: "Please enter the password",
                minlength: "Password must be at least 5 characters long",
                maxlength: "Password must be not longer than 15 characters"
            }
        },
        errorElement : 'div',
        errorLabelContainer: '#msg'
    });
});