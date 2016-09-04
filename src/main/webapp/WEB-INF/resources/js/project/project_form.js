$(document).ready(function () {
    $('#guest').change(function(){
        if($(this).is(':checked')){
            $('#enableView').show();
        } else {
            $('#enableView').hide();
        }
    });
    
    $('#issue, #comment').change(function () {
        if (($('#issue').is(":checked")) | $('#comment').is(":checked")) {
            $('#guest').attr('disabled', true);
        } else {
            $('#guest').attr('disabled', false);
        }
    });
});

$(document).ready(function () {
    CKEDITOR.replace('editor1',
        {
            toolbar: 'Basic'
        });
});


