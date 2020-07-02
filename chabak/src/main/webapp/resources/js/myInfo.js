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
                    // HTMLframe 가져오는 매서드
                    for (var i = 0; i < HashMapList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
                        var htmlFrame = $('#selectPosition').clone(true);
                        $('.listUl').append(htmlFrame);

                        // $('.listUl').append($('#selectPosition').html($(data).find('#selectPosition').html())); 1개만 추가할 때
                        var userProfileImage = "/profileImages/" +HashMapList[i].SAVENAME;
                        var userId = "'"+HashMapList[i].ID+"'";

                        $('#imageId').attr('id', "imageId"+i);
                        $('#userIdId').attr('onclick', "guestVisit("+userId+")");
                        $('#userIdId').attr('id', "userIdId"+i);
                        $('#buttonId').attr('onclick', "deleteFollowerUser("+userId+")");
                        $('#buttonId').attr('id', "buttonId"+i);
                        $('#selectPosition').attr('id', "selectPosition"+i);

                        $('#imageId'+i).attr('src', userProfileImage);
                        $('#userIdId'+i).text(userId);
                        $('#buttonId'+i).text("삭제");

                        htmlFrame.show();
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
                // htmlframe 가져오는 매서드
                for (var i = 0; i < HashMapList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
                    var htmlFrame = $('#selectPosition').clone(true);
                    $('.listUl').append(htmlFrame);

                    var userProfileImage = "/profileImages/" +HashMapList[i].SAVENAME;
                    var userId = "'"+HashMapList[i].ID+"'";

                    $('#imageId').attr('id', "imageId"+i);
                    $('#userIdId').attr('onclick', "guestVisit("+userId+")");
                    $('#userIdId').attr('id', "userIdId"+i);
                    $('#buttonId').attr('onclick', "deleteFollowUser("+userId+")"); // follow_method.js에 포함된 삭제 매서드
                    $('#buttonId').attr('id', "buttonId"+i);
                    $('#selectPosition').attr('id', "selectPosition"+i);

                    //todo: follow 돼있으면 button text 팔로우 // unfollow 돼있으면 text 팔로잉으로 보이게하기
                    $('#imageId'+i).attr('src', userProfileImage);
                    $('#userIdId'+i).text(userId);
                    $('#buttonId'+i).text("삭제");

                    htmlFrame.show();
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