$(document).ready(function () {

    //form login validation
    $('#loginform').validate({ // initialize the plugin
        invalidHandler: function (event, validator) {
            $("#loginform div.input-group").addClass("has-error");
        },
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 5
            }
        }
    });

});