$(document).ready(function () {
    CKEDITOR.replace('editor1', {
        toolbar: 'Basic'
    });

    $("#projectForm").validate({
        ignore: [],
        rules: {
            "title": {
                required: true,
                maxlength: 100
            },
            "description": {
                required: true,
                maxlength: 10000
            },
        },
        messages: {
            "title": {
                required: "Please enter project's title",
                maxlength: "Project's title must be not longer than 100 characters"
            },
            "description": {
                required: "Please enter the project's description",
                maxlength: "Project's description must be not longer than 10000 characters"
            },
        },
    });

    if ($('#guest').is(':checked')){
        $('#enableView').show();
    }else {
        $('#enableView').hide();
    }
    
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

