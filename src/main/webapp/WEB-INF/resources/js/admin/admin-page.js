$(document).ready(function () {

    var userData;
    // Create a Deferred
    defer = $.Deferred();
    // Set object as a promise
    defer.promise(userData);

    $.get("/admin/rest", "json")
        .done(function (data) {
            userData = data;
            defer.resolve();
        });

    defer.done(function () {
        takeAllUsersOnce("#AllUsersTable", userData);
    });

    $("#AllUsersTable").css("width", "100%");

    $('.nav-tabs a[href="#detailsUserTab"]').tab('show');
    $('.nav-tabs a[href="#allUsersTab"]').tab('show');
    window.setTimeout(function () {
        $("#detailsUserTab").removeClass('active');
    }, 500);

});


function takeAllUsersOnce(tableID, userData) {
    var table = $(tableID).DataTable({
        'columnDefs': [
            {responsivePriority: 1, targets: [2, 3]},
            {responsivePriority: 2, targets: 3}
        ],
        'dom': 'Bfrtip',
        'buttons': [
            'colvis',
            'copy',
            'print',
            'csv'
        ],
        "data": userData,

        "columns": [
            {"data": 'id'},
            {"data": 'firstName'},
            {"data": 'lastName'},
            {"data": 'email'},
            {"data": 'role'},
            {"data": 'projectTitle'},
            {
                "data": 'deleted',
                "render": function (row, type, set) {
                    var clickfunc = ' onclick="CheckboxDeleteUserRecord(' + set.id + ', ' + set.deleted + ')"></div>';
                    if (set.deleted == 1)
                        return '<div class="checkbox"><input type="checkbox" checked' + clickfunc;
                    else
                        return '<div class="checkbox"><input type="checkbox"' + clickfunc;
                },
                className: "dt-body-center"
            },
            {
                "data": 'enabled',
                "render": function (row, type, set) {
                    var clickfunc = '  onclick="CheckboxEnabledUserRecord(' + set.id + ', ' + set.enabled + ')"></div>';
                    if (set.enabled == 1)
                        return '<div class="checkbox"><input type="checkbox" checked' + clickfunc;

                    else
                        return '<div class="checkbox"><input type="checkbox"' + clickfunc;
                },
                className: "dt-body-center"
            },
            {
                "data": 'id',
                "className": "center",
                "render": function (data) {
                    return '<div class="actionButtons"><a href="#" onclick="deleteUserRecord(' +
                        data +
                        ')"><i class="fa fa-trash fa-lg icon-table-u"></i></a>' +
                        '<a href="#" onclick="getUserRecord(' +
                        data +
                        ')"><i class="fa fa-eye fa-lg icon-table-u"></i></a></div>';
                }
            }
        ]
    });

}


function deleteUserRecord(id) {
    var table = $('#AllUsersTable').DataTable();

    var i = 0;
    table.rows().data().each(function (row) {
        if (row.id == id) {
            table.row(i).remove().draw();
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
    defer.promise(userData);

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

        $("#detailsUserTab8").attr("alt", userData.imageFilename);
        $("#detailsUserTab8").attr("src", "data:image/jpg;base64," + userData.imageData);

        $("#detailsUserTab9 h5").empty();
        $("#detailsUserTab9 h5").append(userData.deleted == 0 ? '<p class="text-success">present</p>' : '<p class="text-danger">deleted</p>');

        $("#detailsUserTab10 h5").empty();
        $("#detailsUserTab10 h5").append(userData.enabled == 1 ? '<p class="text-success">enabled</p>' : '<p class="text-danger">disabled</p>');

        $('.nav-tabs a[href="#detailsUserTab"]').tab('show');

    });
}

function CheckboxDeleteUserRecord(id, deleted) {
    var table = $('#AllUsersTable').DataTable();
    var i = 0;
    table.rows().data().each(function (row) {
        if (row.id == id) {
            row.deleted = (deleted == 1) ? 0 : 1;
        }
        i++;
    });

    $.ajax({
        url: "/admin/rest/deleted/" + id,
        type: 'PUT'
    });

}

function CheckboxEnabledUserRecord(id, enabled) {
    var table = $('#AllUsersTable').DataTable();
    var i = 0;
    table.rows().data().each(function (row) {
        if (row.id == id) {
            row.enabled = (enabled == 1) ? 0 : 1;
        }
        i++;
    });

    $.ajax({
        url: "/admin/rest/enabled/" + id,
        type: 'PUT'
    });

}



