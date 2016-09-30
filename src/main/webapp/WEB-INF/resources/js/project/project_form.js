$(document).ready(function () {

    // Text editor
    CKEDITOR.replace('editor1', {
        toolbar: 'Basic'
    });

    //Validation of Project Form
    $("#projectForm").validate({
        ignore: [],
        rules: {
            "title": {
                required: true,
                maxlength: 100
            },
            "description": {
                required: function()
                {
                    CKEDITOR.instances.editor1.updateElement();
                },
                maxlength: 10000
            },
        },
        messages: {
            "title": {
                required: "Please enter project's title",
                maxlength: "Project's title must be not longer than 100 characters"
            },
            "description": {
                required: "Please enter project's description",
                maxlength: "Project's description must be not longer than 10000 characters"
            },
        },
    });

    //Management of project's options
    $('#guest').change(function(){
       if ($(this).is(':checked')){
           $('#enableView').show();
           $('#issue').attr('disabled', false);
           $('#comment').attr('disabled', false);
       }else {
           $('#enableView').hide();
           $('#issue').attr('disabled', true);
           $('#comment').attr('disabled', true);
       }
    });
});