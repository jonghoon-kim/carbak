
function goPage(pageNo) {
        var nowPage = pageNo,           //페이지 값
            //keyword = document.getElementById('campsitename').value,
            startPage = 1;              //초기 페이지 값 설정
        var keywordpl = document.getElementById('campsitename');       //야영장 이름
        var keyword;
        if(keywordpl){
            keyword = keywordpl.value;
        }
        else {
            keyword = $(".blog_community_link").data("pl");
        }
        if(nowPage == null || nowPage =="" || nowPage == 'undefined' || nowPage ==" " || nowPage<=startPage){
            nowPage = 1;
        }
        else{
            nowPage = nowPage;
        }
            $.ajax({
                type : "POST",
                dataType : 'json',
                url : "blogPaging",
                data : {
                    "startPageNo" : nowPage,
                    "keyword" : keyword
                },
                success : function(data) {
                    var nowPageNo = parseInt(data.nowPageNo),
                        blogInfo = data.blogInfo,
                        imageInfo = data.imageInfo,
                        campsitekeyword = data.campsitekeyword;
                        pageBoolean = parseInt(data.pageBoolean);
                    changePage(nowPageNo, blogInfo, imageInfo, campsitekeyword, pageBoolean);
                },
                error : function(data, status, error) {
                    alert("page"+page);
                }
            });
}
function changePage(nowPageNo, blogInfo, imageInfo, campsitekeyword){


    var nowPageNo = nowPageNo;
        prevPageNo = nowPageNo-5,
        nextPageNo = nowPageNo+5;
    if(prevPageNo<=1){
        prevPageNo = 1;
    }
    $('.blog_aticle').children('#blogUl').remove();
    var row, postdate_year, postdate_month, postdate_day;
    row = "<ul id='blogUl'>"
    for (var i = 0; i < 5; i++) {
        var postdate = blogInfo[i].postdate;

        postdate_year = postdate.substring(0,4);
        postdate_month = postdate.substring(4,6);
        postdate_day = postdate.substring(6,8);
        console.log(i + " Testet123" + imageInfo[i].thumbnail);
        if(imageInfo[i].thumbnail == "undefined"){
            imageInfo[i].thumbnail="/resources/img/campsite/nullImage.png";
        }

        row += "<li id='blogLi'>"
            + "<p class='blog_best_id'>ID : <a href="+blogInfo[i].bloggerlink+" target='_blank'>" + blogInfo[i].bloggername + "</a></p>"
            + "<div class='blog_best_img'>"
            + "<img src="+imageInfo[i].thumbnail+">"
            + "</div>"
            + "<p class='blog_best_title'>" + blogInfo[i].title + "</p>"
            + "<p class='blog_best_content'>" + blogInfo[i].description + "</p>"
            + "<p class='blog_select_community'>"+postdate_year+"-"+postdate_month+"-"+postdate_day+" "
            + "<a href="+blogInfo[i].link+" target='_blank'>자세히보기</a></p>"
            + "</li>"

    }
    row += "</ul>"
    $('.blog_aticle').append(row);
    $('.blog_aticle').children('.blog_community_link').remove();

    var blogPageButton;

    blogPageButton = "<div class='blog_community_link' data-pl='"
                     + campsitekeyword
                     + "'><button id='prevPageNo' class='fas fa-angle-left' onClick='javascript:goPage("
                     + prevPageNo
                     + ")'></button>";
    if(pageBoolean==0){
        blogPageButton += "<button class='far fa-circle' onClick='javascript:goPage(1)'></button>&nbsp;"
        + "<button class='far fa-circle' onClick='javascript:goPage(6)'></button>&nbsp;"
        + "<button class='fas fa-circle' onClick='javascript:goPage(11)'></button>&nbsp;";
    }
    else {
        for (var i = 1; i <= 11; i += 5) {
            if (i == nowPageNo) {
                blogPageButton += "<button class='fas fa-circle' onClick='javascript:goPage("
                    + i
                    + ")'></button>&nbsp;";
                console.log("if : " + i);
            } else {
                blogPageButton += "<button class='far fa-circle' onClick='javascript:goPage("
                    + i
                    + ")'></button>&nbsp;";
                console.log("else : " + i);
            }
        }
    }
    if(pageBoolean == 0){
        alert("게시물이 더 이상 없습니다.");
        blogPageButton += "<button id='nextPageNo' class='fas fa-angle-right' onClick='javascript:goPage("
            + nextPageNo
            + ")'></button><div class='blogabout'>"
            + "<a href='https://search.naver.com/search.naver?where=post&sm=tab_jum&query="
            + campsitekeyword
            + "' target='_blank'>블로그내용 더보기</a></div></div>";
        console.log("testewrqr"+nextPageNo);
    }
    else {
        blogPageButton += "<button id='nextPageNo' class='fas fa-angle-right' onClick='javascript:goPage("
            + nextPageNo
            + ")'></button><div class='blogabout'>"
            + "<a href='https://search.naver.com/search.naver?where=post&sm=tab_jum&query="
            + campsitekeyword
            + "' target='_blank'>블로그내용 더보기</a></div></div>";
    }
    $('.blog_aticle').append(blogPageButton);
    console.log(i);
    console.log(prevPageNo);
    console.log(nowPageNo);
    console.log(nextPageNo);
}