$(document).ready(function () {

    var userData;
    // Create a Deferred
    defer = $.Deferred();
    // Set object as a promise
    defer.promise( userData );

    $.get("/admin/rest", "json")
        .done(function (data) {
            userData = data;
            defer.resolve();
            });

    defer.done(function () {
        takeAllUsersOnce("#AllUsersTable", userData);
        takeAllUsersOnce("#AdminManagerUsersTable", userData);
        takeAllUsersOnce("#otherStaffUsersTable", userData);
    });

    $("#AllUsersTable").css("width", "100%");
    $("#AdminManagerUsersTable").css("width", "100%");
    $("#otherStaffUsersTable").css("width", "100%");

    $('.nav-tabs a[href="#admin_managerUsersTab"]').tab('show');

});


function takeAllUsersOnce(tableID, userData) {
    var table = $(tableID).DataTable({
        'columnDefs': [
            { responsivePriority: 1, targets: [2, 3] },
            { responsivePriority: 2, targets: 3 }
        ],
        'dom': 'Bfrtip',
        'buttons': [
            'colvis',
            'copy',
            'print',
            'csv'
        ],
        "data":userData,

        "columns": [
            { "data": 'id' },
            { "data": 'firstName' },
            { "data": 'lastName' },
            { "data": 'email' },
            { "data": 'role' },
            { "data": 'projectTitle' },
            { "data": 'description' },
            {
                "data": 'id',
                "className": "center",
                "render": function (data) {
                    return  '<div class="actionButtons"><a href="#" onclick="deleteUserRecord(' +
                            data +
                            ')"><i class="fa fa-remove icon-table-u"></i></a>' +
                            '<a href="#" onclick="getUserRecord(' +
                            data +
                             ')"><i class="fa fa-eye icon-table-u"></i></a></div>';
                }
            }
        ]
    });

    if(tableID == "#AdminManagerUsersTable"){
        table.column(4).search("ADMIN|PROJECT_MANAGER", true, false).draw();
    }

    if(tableID == "#otherStaffUsersTable"){
        table.column(4).search("DEVELOPER|QA|USER|GUEST", true, false).draw();
    }
}


function deleteUserRecord(id) {
    var table1 = $('#AllUsersTable').DataTable();
    var table2 = $("#AdminManagerUsersTable").DataTable();
    var table3 = $("#otherStaffUsersTable").DataTable();

    var i = 0;
    table1.rows().data().each(function (row) {
        if(row.id == id) {
            table1.row(i).remove().draw();
        }
        i++;
    });

    var i = 0;
    table2.rows().data().each(function (row) {
        if(row.id == id) {
            table2.row(i).remove().draw();
        }
        i++;
    });

    var i = 0;
    table3.rows().data().each(function (row) {
        if(row.id == id) {
            table3.row(i).remove().draw();
        }
        i++;
    });

    $.ajax({
        url: "/admin/rest/" + id,
        type: 'DELETE'
    });
}

function getUserRecord(id) {
    var userData;
    // Create a Deferred
    defer = $.Deferred();
    // Set object as a promise
    defer.promise( userData );

    $.get("/admin/rest/" + id, "json")
        .done(function (data) {
            userData = data;
            defer.resolve();
        });

    defer.done(function () {
        $("#detailsUserTab1 h5").empty();
        $("#detailsUserTab1 h5").append(userData.id);

        $("#detailsUserTab2 h5").empty();
        $("#detailsUserTab2 h5").append(userData.firstName);

        $("#detailsUserTab3 h5").empty();
        $("#detailsUserTab3 h5").append(userData.lastName);

        $("#detailsUserTab4 h5").empty();
        $("#detailsUserTab4 h5").append(userData.email);

        $("#detailsUserTab5 h5").empty();
        $("#detailsUserTab5 h5").append(userData.role);

        $("#detailsUserTab6 h5").empty();
        $("#detailsUserTab6 h5").append(userData.description);

        $("#detailsUserTab7 h5").empty();
        $("#detailsUserTab7 h5").append(userData.projectTitle);

        $("#detailsUserTab8").attr( "alt", userData.imageFilename );
        $("#detailsUserTab8").attr("src", "data:image/jpg;base64," + userData.imageData);

        $('.nav-tabs a[href="#detailsUserTab"]').tab('show');

    });
}



