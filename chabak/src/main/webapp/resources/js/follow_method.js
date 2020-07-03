// 언팔로잉(삭제) 매서드
function deleteFollowUser(followUserId){
    $.ajax({// ajax가 controller로 보내는
        type: "get",
        data : {"followUserId": followUserId},
        datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
        url: "deleteFollowUser",
        success : function(data) { // ajax가 controller로 부터 받는
            printFollowing(data)

            alert("delete success");
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

//팔로잉 리스트 출력 매서드
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
        //todo: button text가 팔로잉이면 팔로잉 기능을, 팔로우면 언팔로우 기능을 구분해 놀 것
        $('#buttonId').attr('onclick', "deleteFollowing("+userId+")"); // follow_method.js에 포함된 삭제 매서드
        $('#selectPosition').attr('id', "selectPosition"+i);

        //todo: follow 돼있으면 button text 팔로우 // unfollow 돼있으면 text 팔로잉으로 보이게하기
        $('#imageId'+i).attr('src', userProfileImage);
        $('#userIdId'+i).text(userId);
        $('#buttonId'+i).text("삭제");
    }
}



// 팔로잉 리스트 출력 매서드
// function printFollowing(data){
//     console.log(data.HashMapList);
//     var HashMapList = data.HashMapList; // data.HashMapList.ID == HashMapList.ID
//     console.log(data);
//     $('.listUl').children().remove();
//     $('.listUl').text(' ');
//
//     // sessionStorage.setItem("sessionId","'"+<%=session.getAttribute("id")%>+"'" );
//     var sessionId= getSession();
//     console.log(sessionId);
//     for (var i = 0; i < HashMapList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
//         console.log(HashMapList[i]); // todo: delete button click -> btnDeleteFollowing event run
//         var followerId = "'"+ HashMapList[i].ID+"'";
//
//         var row = '<li class="followList" xmlns="http://www.w3.org/1999/html"><div><div><div>'
//             + '<img class="imageSizeSml" src="/profileImages/' + HashMapList[i].SAVENAME + '">'
//             + '</div><div><a src="/mypage?followerId="'+ followerId +'>'+followerId+'</a></div></div></div>'
//             + '<c:if test =\"' + followerId + ' != \''+ sessionId+'\'">'
//             + '<div><button class="deleteBtn" onclick="deleteFollowing(' + followerId + ')">삭제</button></div>'
//             + '</c:if>';
//         $('.listUl').append(row);
//     }
// }