$(document).ready(function () {

    CKEDITOR.replace('editor1',
        {
            toolbar: 'Basic'
        });
    
    $('#chooseProject').change(function(){
        if ($(this).is(':checked')){
            $('#role').show();
            $('#project').show();
        }else {
            $('#role').hide();
            $('#project').hide();
            $('#roleInput').find('.roleOption').val('ROLE_USER').change();
            $('#projectInput').find('.projectOption').val(null).change();
        }
    });
});