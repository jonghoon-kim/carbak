
$(document).ready(function() {
    //follower 정보 갖고오기
    $("#follower").click(function () {
        $.ajax({
            type: 'get',
            datatype: 'json',
            url: 'follower',
            data: {'datatype':'String'},
            success : function(data) {
                alert(data);
            }
        })
    });
})