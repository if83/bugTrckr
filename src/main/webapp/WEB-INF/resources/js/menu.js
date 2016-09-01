$(document).ready(function () {

    $('#bs-example-navbar-collapse-1 > ul.nav li').removeClass('active');
    switch (window.location.pathname) {
        case "/users":
            $(".mainMenuUserItem").addClass('active');
            break;
        case "/admin":
            $(".mainMenuAdminItem").addClass('active');
            break;
        case "/about":
            $(".mainMenuAboutItem").addClass('active');
            break;
        case "/projects":
            $(".mainMenuProjectItem").addClass('active');
            break;
        case "/issue":
            $(".mainMenuIssueItem").addClass('active');
            break;
        default:
            $(".mainMenuHomeItem").addClass('active');
            break;
    }
})
