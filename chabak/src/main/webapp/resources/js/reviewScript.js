function checkReviewValidate(){

    oEditors.getById["smartEditor"].exec("UPDATE_CONTENTS_FIELD", []);

    //this.contents = $('#smartEditor').val();

    if($("#title").val()==''){
        alert('제목을 입력하세요.');
        return false;
    }

    if($("#sido option:selected").val()=="시/도 선택" || $("#sido option:selected").val()==null){
        alert('시,도를 입력하세요.');
        return false;
    }
    if($("#gugun option:selected").val()=="구/군 선택" || $("#gugun option:selected").val()==null){
        alert('구,군을 입력하세요.');
        return false;
    }

    if($("#smartEditor").length==0)
        return false;

    return true;
}

//페이징 시 url String 설정(예: "/review"+getUrlString(curPage)    )
function getUrlString(searchText,sortType){

    var urlString="?";

    urlString += "sortType="+sortType;
    if(searchText!='' || searchText!=null || searchText!=""){

        urlString += "&searchText="+searchText;
    }

    console.log("getUrlString():"+urlString);
    return urlString;


}



// 리뷰 리스트를 ajax로 출력
function ajaxReviewList(sessionId,isSearchButton,curPage) {
    console.log("ajaxReviewList()");
    var searchText = $("#search_text").val();
    var pageOwnerIdVar = $("#pageOwnerIdSaved").val();
    var isFollowerSearchVar = $('input:checkbox[id="isFollowerSearch"]:checked').val();

    //검색 버튼 누른 경우만 searchText 갱신
    if(isSearchButton==true){
        $("#search_text_saved").val(searchText);
    }
    else{
        //검색 버튼 안 누른 경우(기존에 검색한 결과 그대로 사용)
        searchText = $("#search_text_saved").val();
        $("#search_text").val(searchText);
    }

    $.ajax({
        url : "/review/listAjax",
        type : "post",
        dataType:'json',
        data :{"sortType": $("#sortType option:selected").val(),//서버로 전송하는 데이터(정렬방식)
            "searchText": searchText,
            "pageOwnerId": pageOwnerIdVar,
            "curPage": curPage,
             "isFollowerSearch":isFollowerSearchVar}, //검색창의 텍스트값
        success : function(data) {

            var reviewListDiv = $("#reviewListDiv"); //리뷰가 추가되는 영역
            reviewListDiv.empty();  //리뷰 추가 영역 초기화

            var list = data["reviewList"];
            $.each(list, function() {
                var newReview = $("#dummy-review").clone(true);

                //            원형 복사시 수정할 부분: #dummy-review(id),  .writer-id(value),    .review-img img(src,onclick) .content-title(value)

                var reviewNo = this["reviewNo"];
                var reviewElementId = "review"+reviewNo;
                var writerId = this["id"];

                newReview.attr("id",reviewElementId);


                //작성자 영역&&드롭다운 영역

                var writer = newReview.find(".writer-id");  //작성자

                writer.html(this["id"]);  //작성자 설정
                writer.attr("onclick","myFunction("+ reviewNo +")");


                var dropdownContent = newReview.find(".dropdown-content");
                dropdownContent.attr("id","myDropdown"+reviewNo);
                dropdownContent.find(".mypage").attr("onclick","goMyPage('"+writerId+"','"+sessionId+"')");
                dropdownContent.find(".message").attr("onclick","openWinMessageWrite('"+writerId+"','"+sessionId+"')");

                //프로필 이미지
                var profileImg = newReview.find(".centered img");
                profileImg.attr("src",this["savePath"]+this["saveName"]);

                var reviewImg =  newReview.find(".review-img img")  //리뷰 이미지
                reviewImg.attr("src",this["titleImageSrc"]);  //리뷰 타이틀이미지 src

                var onclickLink =  "location.href='/review/detail?reviewNo="+ reviewNo+"'"; //리뷰 타이틀 이미지 링크
                reviewImg.attr("onclick",onclickLink);

                var title = newReview.find(".content-title");   //리뷰 타이틀
                title.text('['+this["sido"]+']'+'['+this["gugun"]+']'+' '+this["title"]);                      //리뷰 타이틀 설정 [sido][gugun][title]

                //등록일자 추가
                var regDate = newReview.find(".regDate");
                regDate.text(getFormatDate(new Date(this["regDate"]),"/"));

                //좋아요 토글될 이미지 선택
                var toggleImage = newReview.find(".toggle-like-img");
                //좋아요 토글될 이미지 id 설정
                toggleImage.attr("id","like-img"+reviewNo);

                //onclick 속성 추가(함수 실행)
                toggleImage.attr("onclick","ajaxReviewLikeToggle('"+reviewNo+"',this,'"+sessionId+"')");

                if(sessionId=="" || sessionId==null || this["likeYn"]==0){

                    toggleImage.attr("src","/img/community/heart.png");
                }
                else{
                    toggleImage.attr("src","/img/community/heart2.png");
                }

                var communityImg = newReview.find(".comment-img");

                var onclickLink2 = "location.href='/review/detail?reviewNo="+ reviewNo+"#reply'";

                communityImg.attr("onclick",onclickLink2);

                newReview.show();
                reviewListDiv.append(newReview);


            });
            var pagenation = data["pagination"];

            var curPageVar = pagenation["curPage"];
            var startPageVar = pagenation["startPage"];
            var endPageVar = pagenation["endPage"];
            var pageCntVar = pagenation["pageCnt"];
            var curRangeVar = pagenation["curRange"];
            var rangeCntVar = pagenation["rangeCnt"];
            var nextPageVar = pagenation["nextPage"];
            var prevPageVar = pagenation["prevPage"];

            console.log("nextPageVar:"+nextPageVar+"prevPageVar:"+prevPageVar);
            console.log("총 페이지 수 : "+pageCntVar+ "/ 현재 페이지 : "+ curPageVar + "/ 현재 블럭 : "+ curRangeVar + "/ 총 블럭 수 : "+ rangeCntVar);

            var pagingDiv = $("#pagingDiv");
            pagingDiv.empty();

            if(curRangeVar != 1){
                var tag = $("#curRange_ne_1").clone(true);
                tag.attr("style","display:inline-block");
                pagingDiv.append(tag);
            }
            if(curPageVar != 1){
                var tag = $("#curPage_ne_1").clone(true);
                tag.attr("onclick","fn_paging('"+ prevPageVar +"')");
                tag.attr("style","display:inline-block");
                pagingDiv.append(tag);
            }
            for(var i=startPageVar;i<= endPageVar;i++){
                if(i==curPageVar){
                    var span1 = $("#i_eq_curPage").clone(true);
                    var innerATag = span1.find("a");
                    innerATag.attr("onclick","fn_paging('"+ i +"')");
                    innerATag.text(i);

                    span1.attr("style","display:inline-block");
                    pagingDiv.append(span1);
                }
                else{
                    var aTag1 = $("#i_ne_curPage").clone(true);
                    aTag1.attr("onclick","fn_paging('"+ i +"')");
                    aTag1.text(i);

                    aTag1.attr("style","display:inline-block");
                    pagingDiv.append(aTag1);
                }
            }
            if(curPageVar!=pageCntVar && pageCntVar > 0){
                var aTag2 = $("#curPage_ne_pageCnt").clone(true);
                aTag2.attr("onclick","fn_paging('"+ nextPageVar +"')");

                aTag2.attr("style","display:inline-block");
                pagingDiv.append(aTag2);
            }
            if(curRangeVar != rangeCntVar && rangeCntVar > 0){
                var aTag3 = $("#curRange_ne_rangeCnt").clone(true);
                aTag3.attr("onclick","fn_paging('"+ pageCntVar +"')");

                aTag3.attr("style","display:inline-block");
                pagingDiv.append(aTag3);
            }


        },
        error:function(error){
            alert('error');
        }
    });  // ajax 끝

}

function cancelFunction(link) {
    var option = confirm("작성을 종료하고 나가시겠습니까?");
    if(option==true){
        location.href=link;

    }
}

function ajaxReviewLikeToggle(reviewNo,imgTag,sessionId){
    if(sessionId == "" || sessionId==null){
        var confirmYn = confirm("로그인이 필요한 서비스입니다.로그인 하시겠습니까?") ;
        if(confirmYn)
            location.href="/member/login";
    }
    else{
        $.ajax({
            url:"/reviewLike/toggleAjax",
            type : "post",
            data :{"reviewNo": reviewNo},
            success : function(data) {
                if(data==1){
                    $(imgTag).attr("src","/img/community/heart2.png");
                }
                else{
                    $(imgTag).attr("src","/img/community/heart.png");
                }
            },
            error:function(error){
                alert(error)
            }
        });
    }
}

//로그인 할지 물어보고 ok이면 로그인 페이지로 이동
function askLogin() {
    var confirmYn = confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?");
    if (confirmYn)
        location.href = "/member/login";
}