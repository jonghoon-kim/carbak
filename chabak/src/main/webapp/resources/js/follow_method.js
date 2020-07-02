// 언팔로잉(삭제) 매서드
function deleteFollowUser(followUserId){
    $.ajax({// ajax가 controller로 보내는
        type: "get",
        data : {"followUserId": followUserId},
        datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
        url: "deleteFollowUser",
        success : function(data) { // ajax가 controller로 부터 받는
            printFollowing(data)

            alert("delete following success");
        }, error: function (data) {
        }
    })
}

// 나를 팔로워한 유저 삭제 매서드
function deleteFollowerUser(followerUserId){
    $.ajax({// ajax가 controller로 보내는
        type: "get",
        data : {"followerUserId": followerUserId},
        datatype: "json",
        url: "deleteFollowerUser",
        success : function(data) {
            printFollower(data)

            alert("delete follower success");
        }, error: function (data) {
        }
    })
}

// 팔로잉 매서드
function followAddUser(followId){
    console.log(followId);

    $.ajax({// ajax가 controller로 보내는
        type: "get",
        data : {"followId": followId},
        datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
        url: "followAddUser",
        success : function(data) { // ajax가 controller로 부터 받는
            printFollowing(data)

            alert("delete success");
        }, error: function (data) {
        }
    })
}

// 페이지 방문 매서드
function guestVisit(pageOwnerId){
    $.ajax({// ajax가 controller로 보내는
        type: "get",
        data : {"pageOwnerId": pageOwnerId},
        datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
        url: "guestVisit",
        success : function(data) { // ajax가 controller로 부터 받는
            printFollowing(data)

            alert("page move success");
        }, error: function (data) {
        }
    })
}


//팔로잉 리스트 출력 매서드 todo: following, follower print 매서드 하나로 만들기
function printFollowing(data){
    var HashMapList = data.HashMapList;

    $('.listUl').empty();
    // htmlframe 가져오는 매서드
    for (var i = 0; i < HashMapList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
        var htmlFrame = $('#selectPosition').clone(true);
        $('.listUl').append(htmlFrame);

        var userProfileImage = "/profileImages/" +HashMapList[i].SAVENAME;
        var userId = "'"+HashMapList[i].ID+"'";


        $('#imageId').attr('id', "imageId"+i);
        $('#userIdId').attr('id', "userIdId"+i);
        $('#buttonId').attr('id', "buttonId"+i);
        $('#buttonId').attr('onclick', "deleteFollowing("+userId+")"); // follow_method.js에 포함된 삭제 매서드
        $('#selectPosition').attr('id', "selectPosition"+i);

        $('#imageId'+i).attr('src', userProfileImage);
        $('#userIdId'+i).text(userId);
        $('#buttonId'+i).text("삭제");
    }
}

//팔로잉 리스트 출력 매서드
function printFollower(data){
    var HashMapList = data.HashMapList;

    $('.listUl').empty();
    // htmlframe 가져오는 매서드
    for (var i = 0; i < HashMapList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
        var htmlFrame = $('#selectPosition').clone(true);
        $('.listUl').append(htmlFrame);

        var userProfileImage = "/profileImages/" +HashMapList[i].SAVENAME;
        var userId = "'"+HashMapList[i].ID+"'";


        $('#imageId').attr('id', "imageId"+i);
        $('#userIdId').attr('id', "userIdId"+i);
        $('#buttonId').attr('id', "buttonId"+i);
        $('#buttonId').attr('onclick', "deleteFollowerUser("+userId+")"); // follow_method.js에 포함된 삭제 매서드
        $('#selectPosition').attr('id', "selectPosition"+i);

        $('#imageId'+i).attr('src', userProfileImage);
        $('#userIdId'+i).text(userId);
        $('#buttonId'+i).text("삭제");
    }
}