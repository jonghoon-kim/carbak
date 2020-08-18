//팔로우 리스트
function followList(clickedId, option){//option: "follower" 이거나 "following"
    $.ajax({
        type: "get",
        data: {"clickedId": clickedId,
            "option": option},
        datatype: "json",
        url: "followList",
        success : function(data) {
            printList(data, option, clickedId);

        }, error: function (data) {
            alert("error ---- #follower");
        }
    })
}
//팔로잉 삭제 매서드
function deleteFollowUser(clickedId, option){
    $.ajax({
        type: "get",
        data : {"clickedId": clickedId,
                "option": option},
        datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
        url: "deleteFollowUser",
        success : function(data) { // ajax가 controller로 부터 받는
            var sessionId = document.getElementById("sessionId").value;

            printList(data, option, sessionId);


        }, error: function (data) {
        }
    })

}

//리스트 출력 매서드
function printList(data, option, pageOwnerId){
    var sessionId = document.getElementById("sessionId").value;
    var HashMapList = data.HashMapList;
    var followCount = data.HashMapList.length;

    if(option == "following"){
        document.getElementById("followingCount").innerHTML=followCount;
    }
    else if(option == "follower"){
        document.getElementById("followerCount").innerHTML=followCount;
    }

    $('.listForm').empty();
    // HTMLframe 가져오는 매서드
    for (var listNum = 0; listNum < HashMapList.length; listNum++) { // 팔로워 프로필사진, 아이디 리스트로 출력
        var htmlFrame = $('#selectPosition').clone(true);
        $('.listForm').append(htmlFrame);
        var userProfileImage = "/profileImages/" +HashMapList[listNum].SAVENAME;
        var clickedId = HashMapList[listNum].ID;
        console.log(listNum +  ' : '+ HashMapList[listNum].ID);

        $('#imageId').attr('id', "imageId"+listNum);
        $('#userIdId').attr('onclick', "location.href='/mypage/guestVisit?id="+clickedId+"';");
        $('#image_btn').attr('onclick', "location.href='/mypage/guestVisit?id="+clickedId+"';");
        $('#userIdId').attr('id', "userIdId"+listNum);
        $('#image_btn').attr('id', "image_btn"+listNum);
        $('#buttonId').attr('id', "buttonId"+listNum);
        $('#selectPosition').attr('id', "selectPosition"+listNum);

        $('#imageId'+listNum).attr('src', userProfileImage);
        $('#userIdId'+listNum).text(clickedId);

        // 08-18 test  $('body').children('.red');

        $('body').children('.following_btn').attr('value', "${countFollower}");

        if(sessionId!= pageOwnerId) { // 다른 사용자 아이디일 경우
            btnFollowStatus(clickedId,listNum, option, pageOwnerId); // 조건 function 만들기 :: $('#buttonId'+i).text("팔로잉/팔로우");
        }else if(pageOwnerId == "") { // 마이페이지인 경우
            $('#buttonId'+listNum).attr('onclick', "deleteFollowUser("+"'"+clickedId+"','"+option+"')");
            $('#buttonId'+listNum).text("삭제");
        }else{
            $('#buttonId'+listNum).attr('onclick', "deleteFollowUser("+"'"+clickedId+"','"+option+"')");
            $('#buttonId'+listNum).text("삭제");
        }
        $('.listForm').append(htmlFrame);

        htmlFrame.show();
    }
}


function btnFollowStatus(clickedId, i, option, pageOwnerId){// i: 리스트 인덱스
    $.ajax ({
        type: "get",
        data : {"clickedId": clickedId,
            "option": option},
        datatype: "json",
        url: "btnFollowStatus",
        success : function(data) {
            var followerId = data.followerId;

            if (followerId == clickedId) {
                $('#buttonId' + i).text("팔로잉");
                $('#buttonId' + i).attr('onclick', "clickFollowingBtn("+"'"+clickedId+"','"+option+"','"+pageOwnerId+"')");
            }
            else if(data.sessionId == clickedId)
                $('#buttonId'+i).text("나");
            else{
                $('#buttonId' + i).text("팔로우");
                $('#buttonId' + i).attr('onclick', "clickFollowBtn("+"'"+clickedId+"','"+option+"','"+pageOwnerId+"')");
            }
        }, error: function (data) {
        }
    })
}

//팔로우 버튼 클릭시 팔로잉으로 변경
function clickFollowBtn(clickedId, option, pageOwnerId){
    $.ajax({
        type: "get",
        data : {"clickedId": clickedId,
            "option": option,
            "pageOwnerId": pageOwnerId},
        datatype: "json",
        url: "clickFollowBtn",
        success : function(data) {
            printList(data, option , pageOwnerId);

            // alert("clickFollowBtn success");
        }, error: function (data) {
        }
    })
}

//팔로잉 버튼 클릭시 팔로우로 변경
function clickFollowingBtn(clickedId, option, pageOwnerId){
    $.ajax({
        type: "get",
        data : {"clickedId": clickedId, // 변수에 동사 사용 안하는게 좋다.
            "option": option,
            "pageOwnerId": pageOwnerId},
        datatype: "json",
        url: "clickFollowingBtn",
        success : function(data) {
            printList(data, option , pageOwnerId);

            // alert("clickFollowBtn success");
        }, error: function (data) {
        }
    })
}

function btnProfileFollowStatus(clickedId){
    $.ajax ({
        type: "get",
        data : {"clickedId": clickedId},
        datatype: "json",
        url: "btnFollowStatus",
        success : function(data) {
            var followerId = data.followerId;

            if (followerId == clickedId) {
                $('#btn_profile_follow').text("팔로잉");
                $('#btn_profile_follow').attr('onclick', "clickProfileFollowing("+"'"+clickedId+"'"+")");
                $('#btn_profile_follow').attr('style', "display: inline;");
                $('#btn_updateMember').attr('style', "display: none;");
            }
            else if(data.sessionId == clickedId){
                $('#btn_profile_follow').attr('style', "display: none;");
            }
            else{
                $('#btn_profile_follow').text("팔로우");
                $('#btn_profile_follow').attr('onclick', "clickProfileFollow("+"'"+clickedId+"'"+")");
                $('#btn_profile_follow').attr('style', "display: inline;");
                $('#btn_updateMember').attr('style', "display: none;");
            }
        }, error: function (data) {
        }
    })
}


$(document).ready(function(){
    var clickedId =  $("#pageOwnerId").find("div").text();
    btnProfileFollowStatus(clickedId);
})

//팔로우 버튼 클릭시 팔로잉으로 변경
function clickProfileFollow(clickedId){ // followUserId 클릭되는 아이디
    $.ajax({
        type: "get",
        data : {"clickedId": clickedId},
        datatype: "json",
        url: "clickProfileFollow",
        success : function(data) {
            $('#btn_profile_follow').text("팔로잉");
            $('#btn_profile_follow').attr('onclick', "clickProfileFollowing("+"'"+clickedId+"'"+")");
        }, error: function (data) {
        }
    })
}

//팔로잉 버튼 클릭시 팔로우로 변경
function clickProfileFollowing(clickedId){
    $.ajax({
        type: "get",
        data : {"clickedId": clickedId},
        datatype: "json",
        url: "clickProfileFollowing",
        success : function(data) {
            $('#btn_profile_follow').text("팔로우");
            $('#btn_profile_follow').attr('onclick', "clickProfileFollow("+"'"+clickedId+"'"+")");
        }, error: function (data) {
        }
    })
}

// 리뷰 리스트
function printReviewList(pageOwnerId) {
    $.ajax({
        type: "get",
        data : {"pageOwnerId": pageOwnerId},
        datatype: "json",
        url: "printReviewList",
        success : function(data) {
            var reviewList = data.reviewList;

            $('.listForm').empty();
            // HTMLframe 가져오는 매서드
            for (var i = 0; i < reviewList.length; i++) { // 팔로워 프로필사진, 아이디 리스트로 출력
                var htmlFrame = $('#reviewListId').clone(true);
                $('.listForm').append(htmlFrame);

                var reviewTitleImg = reviewList[i].titleImageSrc;
                var reviewNo = reviewList[i].reviewNo;

                $('#reviewListId').attr('id', "reviewListId"+i);
                $('#imgAppendPoint').attr('id', "imgAppendPoint"+i);
                $('#imgAppendPoint'+i).attr('src', reviewTitleImg);
                $('#reviewDetail').attr('id', "reviewDetail"+i);
                $('#reviewDetail'+i).attr('href', "/review/detail?reviewNo="+reviewNo);

                /*var htmlFrame = $('#reviewListId1').clone(true);
               var reviewTitleImg = reviewList[i].titleImageSrc;
                var reviewNo = reviewList[i].reviewNo;
                var reviewDetail = htmlFrame.find(".reviewImg a");
                var imgAppendPoint = reviewDetail.find("img");
                imgAppendPoint.attr('src', reviewTitleImg);
                reviewDetail.attr('href', "/review/detail?reviewNo="+"'"+reviewNo+"'");*/

                $('.listForm').append(htmlFrame);

                htmlFrame.show();
            }

        }, error: function (data) {
        }
    })
}
