$(document).ready(function () {
    
    var location = window.location.href;
    
    //styles for work log paginating
    if(location.indexOf("?page=") > 0) {
        $("#tab-comments").removeClass("active");
        $("#tabs-comments").removeClass("active");
        $("#tab-worklog").addClass("active");
        $("#tabs-worklog").addClass("in active");
    }

    //styles for work log editing
    if(location.indexOf("worklog") > 0 && location.indexOf("edit") > 0) {
        $("#tab-comments").removeClass("active");
        $("#tabs-comments").removeClass("active");
        $("#tab-worklog").addClass("active");
        $("#tabs-worklog").addClass("in active");
    }

    // work log list/form toggler
    $(".workLogButton").click(function() {
        if ($("#workLogTable").hasClass("margin-top-10"))
            $("#workLogForm").fadeIn(),
                $("#workLogTable").fadeOut(),
                $("#workLogTable").removeClass("margin-top-10").addClass("hidden"),
                $("#workLogForm").removeClass("hidden").addClass("margin-top-10"),
                $(".workLogButton").text("Back to list");
        else  $("#workLogTable").fadeIn(),
            $("#workLogTable").removeClass("hidden").addClass("margin-top-10"),
            $("#workLogForm").removeClass("margin-top-10").addClass("hidden"),
            $("#workLogForm").fadeOut(),
            $(".workLogButton").text("Add entry");
    });

    //redirect message hide
    $("#redirectMessage").show(0).delay(2000).hide(1500);
    
});