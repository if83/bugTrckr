$(function () {

    CKEDITOR.replace('editor1', {
        toolbar: 'Basic'
    });
    
    $("#release").validate({
        rules: {
            "version": {
                required: true,
                maxlength: 32
            }
        },
        messages: {
            "version": {
                required: "Please, enter the version of release",
                maxlength: "Version must be not longer than 32 characters"
            }

        }
    });
});