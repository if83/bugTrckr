$(function () {
    $("#release").validate({
        rules: {
            "version": {
                required: true,
                maxlength: 32
            },
            "releaseStatus": {
                required: true
            }
        },
        messages: {
            "version": {
                required: "Please, enter the version of release",
                maxlength: "Version must be not longer than 32 characters"
            },
            "releaseStatus": {
                required: "Please, select the status"
            }
        }
    });
});