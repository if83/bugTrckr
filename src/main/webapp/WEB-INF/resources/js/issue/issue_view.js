$(document).ready(function () {

    $( ".datepicker" ).datepicker({
        dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true
    });

    var location = window.location.href;

    //notification messages
    $('#modalChanges .modal-content').css('background-color','#DBFFD5');
    var msg = $("#message").text();
    if(msg.length > 0){
        $('#modalChanges').find('.modal-body').html(msg);
        $('#modalChanges').modal('show');
        setTimeout(function(){$('#modalChanges').modal('hide')}, 3000);
    };
    
    //styles for work log paginating
    if(location.indexOf("?worklog_page=") > 0) {
        $("#tab-comments").removeClass("active");
        $("#tabs-comments").removeClass("active");
        $("#tab-worklog").addClass("active");
        $("#tabs-worklog").addClass("in active");
    }

    //block tabs when editing comment
    if(location.indexOf("comment") > 0 && location.indexOf("edit") > 0) {
        $(".workLogToggler").hide();
        $("#tab-worklog").click(function(){
            //say smth
            return false;
        });
        $("#tab-history").click(function(){
            //say smth
            return false;
        });
    }

    //styles for work log editing
    if(location.indexOf("worklog") > 0 && location.indexOf("edit") > 0) {
        //lock tabs when editing worklog
        $("#tab-comments").click(function(){
            //say smth
            return false;
        });
        $("#tab-history").click(function(){
            //say smth
            return false;
        });

        //styles
        $("#tab-comments").removeClass("active");
        $("#tabs-comments").removeClass("active");
        $("#tab-worklog").addClass("active");
        $("#tabs-worklog").addClass("in active");
        $("#workLogTable").hide();
        $("#workLogForm").removeClass("hidden").addClass("active");
        $(".workLogToggler").hide();
        $("#workLogCancelButton").removeClass("hidden").addClass("margin-top-30 btn-u").click(function(){
            history.back()});
        $("#workLogSubmitButton").removeClass("col-sm-offset-5");
        $("#workLogButtons").removeClass("col-sm-10 col-sm-offset-1").addClass("col-sm-5 col-sm-offset-4");
    }

    // work log list/form toggler
    $(".workLogToggler").click(function() {
        if ($("#workLogTable").hasClass("margin-top-10"))
            $("#workLogForm").fadeIn(),
                $("#workLogTable").fadeOut(),
                $("#workLogTable").removeClass("margin-top-10").addClass("hidden"),
                $("#workLogForm").removeClass("hidden").addClass("margin-top-10"),
                $(".workLogToggler").text("Back to list");
        else  $("#workLogTable").fadeIn(),
            $("#workLogTable").removeClass("hidden").addClass("margin-top-10"),
            $("#workLogForm").removeClass("margin-top-10").addClass("hidden"),
            $("#workLogForm").fadeOut(),
            $(".workLogToggler").text("Add entry");
    });

    $(".prevent-inline-displaying").css({ 'word-wrap': "break-word" });

});