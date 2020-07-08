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
    $('.listForm').empty();
    // HTMLframe 가져오는 매서드
    for (var i = 0; i < HashMapList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
        var htmlFrame = $('#selectPosition').clone(true);
        $('.listForm').append(htmlFrame);

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
            btnFollowStatus(userId,i, option, id); // 조건 function 만들기 :: $('#buttonId'+i).text("팔로잉/팔로우");
        }else { // 마이페이지인 경우
            $('#buttonId').attr('onclick', "deleteFollowUser("+"'"+userId+"','"+option+"')");
            $('#buttonId'+i).text("삭제");
        }
        htmlFrame.show();
    }
}

function btnFollowStatus(userId, i, option, id){
    $.ajax ({
        type: "get",
        data : {"userId": userId,
            "option": option},
        datatype: "json",
        url: "btnFollowStatus",
        success : function(data) {
            console.log("followerId : " + data.followerId);
            var followerId = data.followerId;
            console.log("userId : " + userId);
            console.log("data : " + data.followerId);
            console.log("sessionId : " + data.sessionId);

            if (followerId == userId) {
                $('#buttonId' + i).text("팔로잉");
                $('#buttonId' + i).attr('onclick', "clickFollowingBtn("+"'"+userId+"','"+option+"','"+id+"')");
            }
            else if(data.sessionId == userId)
                $('#buttonId'+i).text("나");
            else{
                $('#buttonId' + i).text("팔로우");
                $('#buttonId' + i).attr('onclick', "clickFollowBtn("+"'"+userId+"','"+option+"','"+id+"')");
            }
        }, error: function (data) {
        }
    })
}

//팔로우 버튼 클릭시 팔로잉으로 변경
function clickFollowBtn(followUserId, option, pageOwnerId){ // followUserId 클릭되는 아이디
    $.ajax({
        type: "get",
        data : {"followUserId": followUserId,
            "option": option,
            "pageOwnerId": pageOwnerId},
        datatype: "json",
        url: "clickFollowBtn",
        success : function(data) {
            printList(data, option , pageOwnerId);

            alert("clickFollowBtn success");
        }, error: function (data) {
        }
    })
}

//팔로잉 버튼 클릭시 팔로우로 변경
function clickFollowingBtn(followUserId, option, pageOwnerId){
    $.ajax({
        type: "get",
        data : {"followUserId": followUserId,
            "option": option,
            "pageOwnerId": pageOwnerId},
        datatype: "json",
        url: "clickFollowingBtn",
        success : function(data) {
            printList(data, option , pageOwnerId);

            alert("clickFollowBtn success");
        }, error: function (data) {
        }
    })
}

function btnProfileFollowStatus(userId){
    $.ajax ({
        type: "get",
        data : {"userId": userId},
        datatype: "json",
        url: "btnFollowStatus",
        success : function(data) {
            var followerId = data.followerId;
            console.log("userId : " + userId);

            if (followerId == userId) {
                $('#btn_profile_follow').text("팔로잉");
                $('#btn_profile_follow').attr('onclick', "clickProfileFollowing("+"'"+userId+"'"+")");
                $('#btn_profile_follow').attr('style', "display: inline;");
            }
            else if(data.sessionId == userId)
                $('#btn_profile_follow').attr('style', "display: none;");
            else{
                $('#btn_profile_follow').text("팔로우");
                $('#btn_profile_follow').attr('onclick', "clickProfileFollow("+"'"+userId+"'"+")");
                $('#btn_profile_follow').attr('style', "display: inline;");
            }
        }, error: function (data) {
        }
    })
}

//팔로우 버튼 클릭시 팔로잉으로 변경
function clickProfileFollow(userId){ // followUserId 클릭되는 아이디
    $.ajax({
        type: "get",
        data : {"userId": userId},
        datatype: "json",
        url: "clickProfileFollow",
        success : function(data) {
            $('#btn_profile_follow').text("팔로잉");
            $('#btn_profile_follow').attr('onclick', "clickProfileFollowing("+"'"+userId+"'"+")");
            alert("clickProfileFollow success");
        }, error: function (data) {
        }
    })
}

//팔로잉 버튼 클릭시 팔로우로 변경
function clickProfileFollowing(userId){
    $.ajax({
        type: "get",
        data : {"userId": userId},
        datatype: "json",
        url: "clickProfileFollowing",
        success : function(data) {
            $('#btn_profile_follow').text("팔로우");
            $('#btn_profile_follow').attr('onclick', "clickProfileFollow("+"'"+userId+"'"+")");
            alert("clickProfileFollowing success");
        }, error: function (data) {
        }
    })
}

$(document).ready(function(){
    var visitorId =  $("#pageOwnerId").find("button span").text();
    btnProfileFollowStatus(visitorId);
})
