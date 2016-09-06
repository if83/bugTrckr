$(document).ready(function () {
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
    
    CKEDITOR.replace('editor1', {
            toolbar: 'Basic'
    });
});

