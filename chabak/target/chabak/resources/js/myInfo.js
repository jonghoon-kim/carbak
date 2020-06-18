$(document).ready(function() {
    $("#follower").click(function () {
        $.ajax({
            type: 'post',
            datatype: 'json',
            url: 'follower',
            success : function(data) {
                alert(data);
            }
        })
    });
})