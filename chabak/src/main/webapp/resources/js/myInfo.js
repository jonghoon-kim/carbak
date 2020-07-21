//follow 관련 매서드 파일
function includeFollowMethod(jsFilePath){
    var js = document.createElement("script");

    js.type = "text/javascript";
    js.src = jsFilePath;

    document.body.appendChild(js);
}

includeFollowMethod("/js/follow_method.js");

function followList(id, option){//id: 세션아이디 or 다른이용자아이디
    $.ajax({
        type: "get",
        data: {"id": id,
                "option": option},
        datatype: "json",
        url: "followList",
        success : function(data) {
            printList(data, option, id);

            alert("myFollower test");

        }, error: function (data) {
            alert("error ---- #follower");
        }
    })
}