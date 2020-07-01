// 팔로잉 삭제 매서드
function deleteFollowing(followerId){
    console.log(followerId);

    $.ajax({// ajax가 controller로 보내는
        type: "get",
        data : {"followerId": followerId},
        datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
        url: "deleteFollowing",
        success : function(data) { // ajax가 controller로 부터 받는
            printFollowing(data);

            alert("delete success");
        }, error: function (data) {
        }
    })
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