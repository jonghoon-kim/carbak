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
        if(sessionId!= id) {
            followStatus(id, option);
            $('#buttonId').attr('onclick', "followStatus("+"'"+userId+"','"+option+"')");
            $('#buttonId'+i).text("팔로잉");
        }else {
            $('#buttonId').attr('onclick', "deleteFollowUser("+"'"+userId+"','"+option+"')");
            $('#buttonId'+i).text("삭제");
        }

        htmlFrame.show();
    }
}

function followStatus(id, option) {
    $.ajax ({
        type: "get",
        data : {"id": id,
            "option": option},
        datatype: "json",
        url: "followStatus",
        success : function(data) {
            printList(data, option , id);

            alert("following success");
        }, error: function (data) {
        }
    })

}

// 페이지 방문 매서드
function guestVisit(pageOwnerId){
    $.ajax({
        type: "get",
        data : {"pageOwnerId": pageOwnerId},
        datatype: "json",
        url: "guestVisit",
        success : function(data) {
            // printFollowing(data)

            alert("page move success");
        }, error: function (data) {
        }
    })
}
