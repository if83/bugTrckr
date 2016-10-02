$(document).ready(function () {

    CKEDITOR.replace('editor1',
        {
            toolbar: 'Basic'
        });
    
    //Create new User
    $('#chooseProject').change(function(){
        //show fields of choosing user role and project
        if ($(this).is(':checked')){
            $('#project').show();
            $('#role').show();
        }
        //hide fields of choosing user role and project and set default values
        else {
            $('#project').hide();
            $('#role').hide();
            $('#projectInput').val(null).change();
            $('#roleInput').val('ROLE_USER').change();
        }
    });
    
    alert($('#projectInput').val());
    alert($('#roleInput').val());
    
    if($('#roleInput').val() == 'ROLE_USER'){
        //set default value to user's project if user role is USER
        $('#projectInput').val(null).change();
    }
    else if($('#projectInput').val() == null){
        //set default value of user role if user's project is null
        $('#roleInput').val('ROLE_USER').change();
    }
});