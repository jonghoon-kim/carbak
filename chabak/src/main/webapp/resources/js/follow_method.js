//팔로잉 삭제 매서드
function deleteFollowUser(followUserId, option){
    $.ajax({// ajax가 controller로 보내는
        type: "get",
        data : {"followUserId": followUserId,
                "option": option},
        datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
        url: "deleteFollowUser",
        success : function(data) { // ajax가 controller로 부터 받는
            printList(data, option , followUserId);

            alert("delete following success");
        }, error: function (data) {
        }
    })
}

//팔로잉 리스트 출력 매서드
function printList(data, option, id){
    var sessionId = document.getElementById("sessionId").value;
    var HashMapList = data.HashMapList;

    console.log(HashMapList.length);
    $('.listUl').empty();
    // HTMLframe 가져오는 매서드
    for (var i = 0; i < HashMapList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
        var htmlFrame = $('#selectPosition').clone(true);
        $('.listUl').append(htmlFrame);

        var userProfileImage = "/profileImages/" +HashMapList[i].SAVENAME;
        var userId = HashMapList[i].ID;

        $('#imageId').attr('id', "imageId"+i);
        $('#userIdId').attr('onclick', "location.href='/mypage/guestVisit?id="+userId+"';");
        $('#userIdId').attr('id', "userIdId"+i);
        $('#buttonId').attr('id', "buttonId"+i);
        $('#selectPosition').attr('id', "selectPosition"+i);

        $('#imageId'+i).attr('src', userProfileImage);
        $('#userIdId'+i).text(userId);

        if(sessionId!= id) { // 다른 사용자 아이디일 경우
            // listFollowStatus(id, option);
            $('#buttonId').attr('onclick', "listFollowStatus("+"'"+userId+"','"+option+"')"); // 팔로워 클릭인지 팔로잉 클릭인지:option
            //todo: 팔로잉/ 팔로우 text 나오게 조건 설정
            decisionFollowStatus(userId,i); // 조건 function 만들기 :: $('#buttonId'+i).text("팔로잉/팔로우");

        }else { // 마이페이지인 경우
            $('#buttonId').attr('onclick', "deleteFollowUser("+"'"+userId+"','"+option+"')");
            $('#buttonId'+i).text("삭제");
        }

        htmlFrame.show();
    }
}

/*// 방문한 유저의 팔로잉 팔로워 상태를 보여주고 버튼 클릭시 팔로잉, 팔로잉 취소 event 실행
function listFollowStatus(id, option) {
    $.ajax ({
        type: "get",
        data : {"id": id,
            "option": option},
        datatype: "json",
        url: "listFollowStatus",
        success : function(data) {
            printList(data, option, id);

            alert("following success");
        }, error: function (data) {
        }
    })
}*/

function decisionFollowStatus(userId, i){
    $.ajax ({
        type: "get",
        data : {"userId": userId},
        datatype: "json",
        url: "decisionFollowStatus",
        success : function(data) {
            console.log("followerId" + data.followerId);
            var followerId = data.followerId;
            console.log("userId" + userId);

            if(followerId == userId)
                $('#buttonId'+i).text("팔로잉");
            else
                $('#buttonId'+i).text("팔로우");
        }, error: function (data) {
        }
    })
}
