$(document).ready(function(){

    // changing (or adding) labels to issue
    $("#labelsSelect2").select2({
        tags: true,
        createTag: function (tag) {
            return {
                id: tag.term,
                text: tag.term,
                // add indicator:
                isNew : true
            };
        }
    }).on("select2:select", function(e) {
        if(e.params.data.isNew){
            var queryObj = {};
            queryObj.labelName = e.params.data.text;
            $.ajax({
                url: '/saveNewLabel',
                type: 'POST',
                data: queryObj,
                success: function (labelId) {
                   $('option[value="'+e.params.data.text+'"]').replaceWith('<option selected="selected" value="'+labelId+'">'+e.params.data.text+'</option>');
                }
            });
        }
        return false;
    });

    // changing status of issue from dropdown in issues-form
    $("#status").on("change", function () {
        var queryObj = {};
        var selectedStatus = $(this).val();
        $(this).find('option').not(':selected').remove();
        queryObj.selectedStatus = selectedStatus;
        $.ajax({
            url: "/getAvaliableIssueStatuses",
            data: queryObj,
            type: 'POST',
            success: function (statuses) {
                $.each(statuses, function (index, value) {
                    var html = "<option value='" + value + "'>" + value + "</option>";
                    $("#status").append(html);
                });
            }
        });
    });

});