// 스크립트 파일
// console.log(testOne);

$(document).ready(function() {
    //follower click event -> getted follower information
    $("#follower").click(function () {
            $.ajax({
                type: "post",
                datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
                url: "follower",
                // data: {"getFollowerId" : followerId}, // 수정할것
                success : function(data) {
                    alert(JSON.stringify(data));
                }, error: function (data) {

                }
            })
    });
})


function test() {
  var ts = document.getElementById("hi").value;

console.log("test : " + ts);
}