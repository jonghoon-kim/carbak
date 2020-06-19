// 스크립트 파일
console.log(getFollowerData);

$(document).ready(function() {
    //follower click event -> getted follower information
    $("#follower").click(function () {
        $.ajax({
            type: 'get',
            datatype: 'json',
            url: 'follower',
            data: {'follower': getFollowerData.FOLLOWERID}, // 수정할것
            success : function(data) {
                for(var i=0; )
            }, error: function (data) {

            }
        })
    });
})