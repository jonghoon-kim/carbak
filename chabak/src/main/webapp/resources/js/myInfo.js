// 스크립트 파일
// console.log(testOne);

$(document).ready(function() {
    //follower click event -> getted follower information
    $("#follower").click(function () {
        var followerId = $(this).attr('getFollowerId');
        console.log(followerId);
        // var json = {"followerId" : followerId};

        // for(var i=0;i<json.length();i++){
            $.ajax({
                type: "get",
                datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
                url: "follower",
                data: {"getFollowerId" : followerId}, // 수정할것
                contentType: "application/json",
                success : function(data) {
                    alert(JSON.stringify(data));
                }, error: function (data) {

                }
            })
    });
})