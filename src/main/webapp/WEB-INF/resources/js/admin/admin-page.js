$(document).ready(function () {

    takeAllUsers("#AllUsersTable", "all");
    takeAllUsers("#AdminManagerUsersTable", "admin");
    takeAllUsers("#otherStaffUsersTable", "other");

    $("#AllUsersTable").css("width", "100%");
    $("#AdminManagerUsersTable").css("width", "100%");
    $("#otherStaffUsersTable").css("width", "100%");

});

function takeAllUsers(tableID, userParam) {
    $(tableID).dataTable({
        responsive: true,
        columnDefs: [
            { responsivePriority: 1, targets: [2, 3] },
            { responsivePriority: 2, targets: 3 }
        ],
        dom: 'Bfrtip',
        buttons: [
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
            { "data": 'description' }
        ]
    });
}

