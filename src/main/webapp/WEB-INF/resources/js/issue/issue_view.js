$(document).ready(function () {
    var location = window.location.href;
    var submits = 0;

    //adds datepicker
    $( ".datepicker" ).datepicker({
        dateFormat: 'dd.mm.yy',
        changeMonth: true,
        changeYear: true
    });

    //activates work log tab if there was an attempt to save work log
    if($("#message").text().toLowerCase().indexOf("work log") > 0 || document.referrer.indexOf("worklog") > 0) {
        activateWorkLogTab();
    };

    //notification messages
    $("#modalChanges .modal-content").css("background-color","#DBFFD5");
    var msg = $("#message").text();
    if(msg.length > 0){
        $("#modalChanges").find(".modal-body").html(msg);
        $("#modalChanges").modal("show");
        setTimeout(function(){$("#modalChanges").modal("hide")}, 1500);
    };

    //css styles for work log paginating
    if(location.indexOf("?worklog_page=") > 0) {
        activateWorkLogTab();
    }

    //locks tabs when editing comment
    if(location.indexOf("comment") > 0 && location.indexOf("edit") > 0) {
        $(".workLogToggler").hide();

        $("#tab-worklog").click(function() {
            return false;
        });

        $("#tab-history").click(function() {
            return false;
        });
    };

    //styles for work log editing
    if(location.indexOf("worklog") > 0 && location.indexOf("edit") > 0) {

        $("#fixedNewCommentButton").hide();

        //locks tabs when editing worklog
        $("#tab-comments").click(function(){
            return false;
        });
        $("#tab-history").click(function(){
            return false;
        });

        //styles and behaviours of tabs elements
        $("#tab-comments").removeClass("active");
        $("#tabs-comments").removeClass("active");
        $("#tab-worklog").addClass("active");
        $("#tabs-worklog").addClass("in active");
        $("#workLogTable").hide();
        $("#workLogForm").removeClass("hidden").addClass("active");
        $(".workLogToggler").hide();
        $("#workLogCancelButton").removeClass("hidden").addClass("margin-top-30").click(function(event){
            event.preventDefault();
            window.location.href = document.referrer;
        });
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

    //css styles for comment editing
    if(location.indexOf("comment") > 0 && location.indexOf("edit") > 0) {
        //referes to comment form
        $("html,body").animate({scrollTop: $("#issueCommentForm").offset().top}, 1000);

        //lock tabs when editing comment
        $("#tab-worklog").click(function(){
            return false;
        });
        $("#tab-history").click(function(){
            return false;
        });

        $("#issueCommentCancelButton").removeClass("hidden").addClass("margin-top-30").click(function(event){
            event.preventDefault();
            window.location.href = document.referrer;
        });
    };

    //contains css classes to display work log tab
    function activateWorkLogTab() {
        $("#tab-comments").removeClass("active");
        $("#tabs-comments").removeClass("active");
        $("#tab-worklog").addClass("active");
        $("#tabs-worklog").addClass("in active");
    };

    //contains css classes to prevent inline text displaying
    $(".prevent-inline-displaying").css({ "word-wrap": "break-word" });

    $("#scrollButtons .fa-arrow-circle-down").click(function (event) {
        event.preventDefault();
        $("html,body").animate({scrollTop: $("#issueCommentForm").offset().top}, 1000);
    });

    $("#scrollButtons .fa-arrow-circle-up").click(function (event) {
        event.preventDefault();
        $("html,body").animate({scrollTop: $("#breadcrumbs").offset().top}, 1000);
    });

    //validate anonymousName input field
    $("#issueCommentForm").submit(function (event) {
        var anonym = $("#anonymousName").val().length;
        var text = $("#text").val().length;
        if($("#anonymousName").attr("type") != "hidden" ) {
            if (anonym < 8 || anonym > 32) {
                $("#anonymousNameDiv").addClass("has-error");
                $("#anonymousNameDiv .text-danger").removeClass("hidden");
                event.preventDefault();
            };
            if (text < 1) {
                $("#textDiv .text-danger").removeClass("hidden");
                event.preventDefault();
            };
        };
        if (text < 1) {
            $("#textDiv .text-danger").removeClass("hidden");
            event.preventDefault();
        };
    });
    $("#anonymousName").keyup(function (event) {
        $("#anonymousNameDiv").removeClass("has-error");
    });
    
    //validate workLog form
    $("#workLogForm").submit(function (event) {
        var start = $("#startDate").val().length;
        var end = $("#endDate").val().length;
        var amount = $("#amountOfTime").val().length;
        if (start == 0) {
            $("#startDateDiv").addClass("has-error");
            $("#startDateDiv .text-danger").removeClass("hidden");
            event.preventDefault();
        };
        if (end == 0) {
            $("#endDateDiv").addClass("has-error");
            $("#endDateDiv .text-danger").removeClass("hidden");
            event.preventDefault();
        };
        if (amount == 0) {
            $("#amountOfTimeDiv").addClass("has-error");
            $("#amountOfTimeDiv .text-danger").removeClass("hidden");
            event.preventDefault();
        };
    });

    $("#startDate").keyup(function (event) {
        $("#startDateDiv").removeClass("has-error");
    });
    $("#endDate").keyup(function (event) {
        $("#endDateDiv").removeClass("has-error");
    });
    $("#amountOfTime").keyup(function (event) {
        $("#amountOfTimeDiv").removeClass("has-error");
    });
    
    if($("#tabs-comments").height() > 800){
        $("#scrollButtons").removeClass("hidden");
    }
});