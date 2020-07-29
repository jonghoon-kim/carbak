//campsite 리뷰 페이징
function CommunityPage(page){
    console.log("Test : " + page);
        if(page == 0){
            page=1;
        }
        var startPageNo = (page * 5) - 4,
        endPageNo = page * 5;
    $.ajax({
        type : "POST",
        dataType : 'json',
        url : "campsite/campsiteCommunityPaging",
        data : {
            "crntpageNo" : page,
            "startPageNo" : startPageNo,
            "endPageNo" : endPageNo,
        },
        success : function(data) {
            var lstSize = data.lstSize;
            var reviewBoolean = data.reviewBoolean;
            var lstSelectCampsiteReview = data.lstSelectCampsiteReview;
            var paging = data.paging;
            communityChangePage(paging, lstSelectCampsiteReview, reviewBoolean, lstSize);
        },
        error : function(data) {
            console.log("data failed : " + data);
            console.log("page"+page);
        }
    });

}

//campsite 리뷰 페이징 html 구축
function communityChangePage(paging, lstSelectCampsiteReview, reviewBoolean, lstSize) {
    $('.community_aticle').children('#blogUl').remove();
    var communityRow;
    var indexlstSize = lstSize-1;
    communityRow = "<ul id='blogUl'>"
    for (var i = 0; i < 5; i++) {
        var id, titleImageSrc, readCount, title, content, regDate, reviewNo
        if(reviewBoolean=="true" && i<=indexlstSize){
            var dt = new Date(lstSelectCampsiteReview[i].regDate);

                id =lstSelectCampsiteReview[i].id,
                titleImageSrc = lstSelectCampsiteReview[i].titleImageSrc,
                readCount = lstSelectCampsiteReview[i].readCount,
                title = lstSelectCampsiteReview[i].title,
                content = lstSelectCampsiteReview[i].content,
                regDate = dt.toISOString().substring(0, 10),
                reviewNo = "http://localhost:8030/review/detail?reviewNo="+lstSelectCampsiteReview[i].reviewNo;
        }
        else{
            id ="아이디 조회 불가",
            titleImageSrc="/resources/img/campsite/nullImage.png",
            readCount="조회 불가",
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
                + "<p class='community_best_views'>views :&nbsp;<span class='best_views_span'>"+ readCount + "</span>"
                + "<p class='community_best_title'>" + title + "</p>"
                + "<div class='community_best_content'>" + content + "</div>"
                + "<p class='community_select_community'>"+ regDate +"&nbsp;"
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
        + "<button class='fas fa-angle-left' onClick='javascript:CommunityPage("
        + paging.prevPageNo
        + ")'></button>";

    for (var i = paging.startPageNo; i <= paging.endPageNo; i++) {
        if (i == paging.pageNo) {
            communityPageButton += "<button class='fas fa-circle'><a href='javascript:CommunityPage(" + i
                + ")'>" + i + "</a></button> ";
        } else {
            communityPageButton += "<button class='far fa-circle'><a href='javascript:CommunityPage(" + i
                + ")'>" + i + "</a></button> ";
        }
    }

    communityPageButton += "<button class='fas fa-angle-right' onClick='javascript:CommunityPage("
        + paging.nextPageNo
        + ")'></button></div>";

    $('.community_aticle').append(communityPageButton);
}
