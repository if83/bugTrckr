$(document).ready(function () {

    takeAllUsers("#AllUsersTable", "all");
    takeAllUsers("#AdminManagerUsersTable", "admin");
    takeAllUsers("#otherStaffUsersTable", "other");

    $("#AllUsersTable").css("width", "100%");
    $("#AdminManagerUsersTable").css("width", "100%");
    $("#otherStaffUsersTable").css("width", "100%");

});

function takeAllUsers(tableID, userParam) {
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
        "ajax": {
            "dataType": 'json',
            "contentType": "application/json; charset=utf-8",
            "url":"/admin/rest/" + userParam,
            "dataSrc": ''
        },

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
                    return '<div class="actionButtons"><a href="#" onclick="deleteUserRecord(' + data + ')"><i class="fa fa-remove icon-table-u"></i></a></div>';
                }
            }
        ]
    });
}

function deleteUserRecord(id) {
    console.log("delete " + id);
    $.ajax({
        url: "/admin/rest/" + id,
        type: 'DELETE'
    });
}



