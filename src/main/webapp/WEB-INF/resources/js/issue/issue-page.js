$(document).ready(function () {

    CKEDITOR.replace('editor1',
    {
        enterMode : CKEDITOR.ENTER_BR
    });

    // change (or add) labels to issue on issue-form
    $("#labelsSelect2").select2({
        tags: true,
        createTag: function (tag) {
            return {
                id: tag.term,
                text: tag.term,
                // add indicator:
                isNew: true
            };
        }
    }).on("select2:select", function (e) {
        if (e.params.data.isNew) {
            var queryObj = {};
            queryObj.labelName = e.params.data.text;
            $.ajax({
                url: '/saveNewLabel',
                type: 'POST',
                data: queryObj,
                success: function (labelId) {
                    var html = '<option selected="selected" value="' + labelId + '">' + e.params.data.text + '</option>';
                    $('option[value="' + e.params.data.text + '"]').replaceWith(html);
                }
            });
        }
        return false;
    });

    // change status of issue and load available statuses when open statuses dropdown on issue-form
    $("#status").on("show.bs.select", function () {
        var self = $(this);
        var queryObj = {};
        var selectedStatus = $(self).val();
        $(this).find('option').not(':selected').remove();
        queryObj.selectedStatus = selectedStatus;
        $.ajax({
            url: "/getAvailableIssueStatuses",
            data: queryObj,
            type: 'POST',
            success: function (statuses) {
                $.each(statuses, function (index, value) {
                    var html = "<option value='" + index + "'>" + value + "</option>";
                    $(self).append(html);
                    $(self).selectpicker('refresh');
                });
            }
        });
    });
});
