//follow 관련 매서드 파일
includeFollowMethod("follow_method.js");


//myInformation에서 팔로워를 click 했을 때 발생하는 event
$(document).ready(function() {
    $("#follower").click(function () {
            $.ajax({//controller로 보내는
                type: "get",
                datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
                url: "follower",
                success : function(data) { // 가져온데이타
                    var HashMapList = data.HashMapList;

                    $('.listUl').empty();

                    var length = HashMapList.length;
                    // htmlframe 가져오는 매서드
                    for (var i = 0; i < length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
                        console.log(i);

                        var test = $('#selectPosition').clone(true);
                        $('.listUl').append(test);

                        // $('.listUl').append($('#selectPosition').html($(data).find('#selectPosition').html()));

                        var userProfileImage = "/profileImages/" +HashMapList[i].SAVENAME;
                        var followerId = HashMapList[i].ID;

                        console.log(userProfileImage);
                        console.log(followerId);

                        $('#imageId').attr('id', "imageId"+i);
                        $('#userIdId').attr('id', "userIdId"+i);
                        $('#buttonId').attr('id', "buttonId"+i);
                        $('#selectPosition').attr('id', "selectPosition"+i);

                        $('#imageId'+i).attr('src', userProfileImage);
                        $('#userIdId'+i).text(followerId);
                        $('#buttonId'+i).text("삭제");

                        test.show();
                    }

                    alert("follower");

                }, error: function (data) {
                    alert("error ---- #follower");
                }
            })
    });
})

//myInformation에서 팔로잉을 click 했을 때 발생하는 event
$(document).ready(function() { // todo: 1) sessionId와 게시판홈 주인 id와 비교 2-1)같으면 팔로잉 삭제기능 보이게 2-2)아니면 유저만 보이게
    $("#following").click(function () {
        $.ajax({// ajax가 controller로 보내는
            type: "get",
            datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
            url: "following",
            success : function(data) { // ajax가 controller로 부터 받는
                var HashMapList = data.HashMapList;

                $('.listUl').empty();

                var length = HashMapList.length;
                // htmlframe 가져오는 매서드
                for (var i = 0; i < length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
                    console.log(i);

                    var test = $('#selectPosition').clone(true);
                    $('.listUl').append(test);

                    // $('.listUl').append($('#selectPosition').html($(data).find('#selectPosition').html()));

                    var userProfileImage = "/profileImages/" +HashMapList[i].SAVENAME;
                    var followerId = "'"+HashMapList[i].ID+"'";

                    console.log(userProfileImage);
                    console.log(followerId);

                    $('#imageId').attr('id', "imageId"+i);
                    $('#userIdId').attr('id', "userIdId"+i);
                    $('#buttonId').attr('id', "buttonId"+i);
                    $('#buttonId').attr('onclick', "deleteFollowing("+followerId+")");
                    $('#selectPosition').attr('id', "selectPosition"+i);

                    $('#imageId'+i).attr('src', userProfileImage);
                    $('#userIdId'+i).text(followerId);
                    $('#buttonId'+i).text("삭제");

                    test.show();
                }

                alert("following");
            }, error: function (data) {
                alert("error ---- #following");
            }
        })
    });
})

//follow 관련 매서드 파일
function includeFollowMethod(jsFilePath){
    var js = document.createElement("script");

    js.type = "text/javascript";
    js.src = jsFilePath;

    document.body.appendChild(js);
}

function visitHomeClickEvent(data) {
    return 0;
}

