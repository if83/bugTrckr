$(document).ready(function () {
    /*$("#tabs").tabs();*/

    $(".workLogButton").click(function() {
        if ($("#workLogTable").hasClass("margin-top-30"))
            $("#workLogForm").fadeIn(),
                $("#workLogTable").fadeOut(),
                $("#workLogTable").removeClass("margin-top-30").addClass("hidden"),
                $("#workLogForm").removeClass("hidden").addClass("margin-top-30"),
                $(".workLogButton").text("Back to list");
        else  $("#workLogTable").fadeIn(),
            $("#workLogTable").removeClass("hidden").addClass("margin-top-30"),
            $("#workLogForm").removeClass("margin-top-30").addClass("hidden"),
            $("#workLogForm").fadeOut(),
            $(".workLogButton").text("Add entry");
    });
    
    $(".fa-remove").click(function() {
        confirm("really?");
    });
});

