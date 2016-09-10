$(document).ready(function(){

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
                success: function (result) {
                   $('option[value="'+e.params.data.text+'"]').replaceWith('<option selected="selected" value="'+result+'">'+e.params.data.text+'</option>');
                }
            });
        }
        return false;
    });

});