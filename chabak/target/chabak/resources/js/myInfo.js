// 스크립트 파일
$(document).ready(function() {
    //follower click event -> getted follower information
    $("#follower").click(function () {
        $.ajax({
            type: 'get',
            datatype: 'json',
            url: 'follower',
            data: {'follower': FOLLOWERID}, // 수정할것
            success : function(data) {
                alert(data);
            }, error: function (data) {

            }
        })
    });
})