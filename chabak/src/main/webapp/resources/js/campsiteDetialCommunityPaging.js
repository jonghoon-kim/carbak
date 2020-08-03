//campsiteDetail 리뷰 페이징
function CommunityDetailPage(page){

    if(page==0){
        page=1;
    }
    var keywordpl = document.getElementById('campsitename');       //야영장 이름
    var keyword;
    if(keywordpl){
        keyword = keywordpl.value;
    }
    else {
        keyword = $(".blog_community_link").data("pl");
    }
    var startPageNo = (page * 5) - 4,
        endPageNo = page * 5;

    $.ajax({
        type : "POST",
        dataType : 'json',
        url : "/campsite/campsiteDetailCommunityPaging",
        data : {
            "keyword" : keyword,
            "crntpageNo" : page,
            "startPageNo" : startPageNo,
            "endPageNo" : endPageNo,
        },
        success : function(data) {
            var lstSize = data.lstSize;
            var reviewBoolean = data.reviewBoolean;
            var lstSelectCampsiteDetailReview = data.lstSelectCampsiteDetailReview;
            var keyword = data.keyword;
            var paging = data.paging;
            communityDetailChangePage(keyword, paging, lstSelectCampsiteDetailReview, reviewBoolean, lstSize);
        },
        error : function(data) {
            console.log("data failed : " + data);
            console.log("page"+page);
        }
    });

}

//campsiteDetail 리뷰 페이징 html 구축
function communityDetailChangePage(keyword, paging, lstSelectCampsiteDetailReview, reviewBoolean, lstSize) {
    $('.community_aticle').children('#blogUl').remove();
    var communityRow;
    var indexlstSize = lstSize-1;
    communityRow = "<ul id='blogUl'>"
    for (var i = 0; i < 5; i++) {
        var id, titleImageSrc, likeCount, title, content, regDate, reviewNo
        if(reviewBoolean=="true" && i<=indexlstSize){
            var dt = new Date(lstSelectCampsiteDetailReview[i].regDate);

            id =lstSelectCampsiteDetailReview[i].id,
                titleImageSrc = lstSelectCampsiteDetailReview[i].titleImageSrc,
                likeCount = lstSelectCampsiteDetailReview[i].likeCount,
                title = lstSelectCampsiteDetailReview[i].title,
                content = lstSelectCampsiteDetailReview[i].content,
                regDate = dt.toISOString().substring(0, 10),
                reviewNo = "http://localhost:8030/review/detail?reviewNo="+lstSelectCampsiteDetailReview[i].reviewNo;
        }
        else{
            id ="아이디 조회 불가",
                titleImageSrc="/resources/img/campsite/nullImage.png",
                likeCount ="조회 불가",
                title="제목 없음",
                content="내용 없음",
                regDate="날짜조회 불가",
                reviewNo="http://localhost:8030/review";
        }
        communityRow += "<li id='communityLi'>"
            + "<p class='community_best_id'>ID : " + id + "</p>"
            + "<div class='community_best_img'>"
            + "<img src="+titleImageSrc+">"
            + "</div>"
            + "<p class='community_best_views'>Likes :&nbsp;<span class='best_views_span'>"+ likeCount + "</span>"
            + "<p class='community_best_title'>" + title + "</p>"
            + "<div class='community_best_content'>" + content + "</div>"
            + "<p class='community_select_community'>"+ regDate +" &nbsp;"
            + "<a href="+reviewNo+" target='_blank'>자세히보기</a></p>"
            + "</li>"

    }
    communityRow += "</ul>"
    $('.community_aticle').append(communityRow);
    $('.community_aticle').children('.community_link').remove();

    var communityPageButton;

    paging.prevPageNo = paging.pageNo - 1;
    paging.nextPageNo=paging.pageNo+1;

    communityPageButton = "<div class='community_link'>"
        + "<button class='fas fa-angle-left' onClick='javascript:CommunityDetailPage("
        + paging.prevPageNo
        + ")'></button>";

    for (var i = paging.startPageNo; i <= paging.endPageNo; i++) {
        if(reviewBoolean=="true") {
            if (i == paging.pageNo) {
                communityPageButton += "<button class='fas fa-circle'><a href='javascript:CommunityDetailPage(" + i
                    + ")'>" + i + "</a></button> ";
            } else {
                communityPageButton += "<button class='far fa-circle'><a href='javascript:CommunityDetailPage(" + i
                    + ")'>" + i + "</a></button> ";
            }
        }
        else{
            communityPageButton += "<button class='fas fa-circle'><a href='javascript:CommunityDetailPage(" + 1
                + ")'>1</a></button> ";
        }
    }

    communityPageButton += "<button class='fas fa-angle-right' onClick='javascript:CommunityDetailPage("
        + paging.nextPageNo
        + ")'></button></div>";

    $('.community_aticle').append(communityPageButton);
}