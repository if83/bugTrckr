$(document).ready(function () {
    //text editor
    CKEDITOR.replace('editor1',
        {
            toolbar: 'Basic'
        });

    //Edit User
    $('#chooseProject').change(function(){
        //show fields of choosing user role and project
        if ($(this).is(':checked')){
            $('#role').show();
            $('#project').show();
        }else{
            $('#project').hide();
            $('#role').hide();
            $('#projectInput').val($('#projectDefault').html()).change();
            $('#roleInput').val($('#roleDefault').html()).change();
        }
    });
    
    if($('#roleInput').val() == 'ROLE_USER'){
        //set default value to user's project if user role is USER
        $('#projectInput').val(null).change();
    }
    else if($('#projectInput').val() == null){
        //set default value of user role if user's project is null
        $('#roleInput').val('ROLE_USER').change();
    }

});