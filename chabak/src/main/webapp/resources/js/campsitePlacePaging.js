
function goPage(pageNo) {
        var nowPage = pageNo,           //페이지 값
            keyword = document.getElementById('campsitename').value,
            startPage = 1;              //초기 페이지 값 설정

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
                    var nowPageNo = data.nowPageNo,
                        blogInfo = data.blogInfo,
                        imageInfo = data.imageInfo;
                    console.log("page number : "+ data.nowPageNo);
                    console.log("blog Infomaiton : " + data.blogInfo);
                    console.log("image Infomation : " + data.imageInfo);
                    console.log("testestes : " + blogInfo[0].bloggername);
                    //var lstDlyRepLst = data.lstDlyRepLst;
                    //var paging = data.paging;
                    //changePage(lstDlyRepLst, paging, userId);
                    changePage(nowPageNo, blogInfo, imageInfo);
                },
                error : function(data, status, error) {
                    alert("page"+page);
                }
            });
}
function changePage(nowPageNo, blogInfo, imageInfo){


    var nowPageNo = parseInt(nowPageNo);
        prevPageNo = nowPageNo-5,
        nextPageNo = nowPageNo+5;
    if(prevPageNo<=1){
        prevPageNo = 1;
    }
    if(nowPageNo >= 17) {
        console.log("더이상 게시물을 보여줄 수 없습니다. : " + nowPageNo);
    }
    $('#blogUl').children().remove();
    $('#blogUl').text('');
    $('#blogLi').text('');
    $('.blog_best_id').text('');
    $('.blog_best_img').text('');
    $('.blog_best_title').text('');
    $('.blog_best_content').text('');
    $('.blog_select_community').text('');
    var row, postdate_year, postdate_month, postdate_day;

    for (var i = 0; i < 5; i++) {
        var postdate = blogInfo[i].postdate;
        postdate_year = postdate.substring(0,4);
        postdate_month = postdate.substring(4,6);
        postdate_day = postdate.substring(6,8);

        row = + "<ul id='blogUl'>"
            + "<li id='blogLi'>"
            + "<p class='blog_best_id'>ID : <a href="+blogInfo[i].bloggerlink+" target='_blank'>" + blogInfo[i].bloggername + "</a></p>"
            + "<div class='blog_best_img'>"
            + "<img src="+imageInfo[i].thumbnail+">"
            + "</div>"
            + "<p class='blog_best_title'>" + blogInfo[i].title + "</p>"
            + "<p class='blog_best_content'>" + blogInfo[i].description + "</p>"
            + "<p class='blog_select_community'>"+postdate_year+"-"+postdate_month+"-"+postdate_day+" "
            + "<a href="+blogInfo[i].link+" target='_blank'>자세히보기</a></p>"
            + "</li>"
            + "</ul>"
        if( blogInfo[i].bloggername =="undefined" || imageInfo[i].thumbnail =="undefined"){
            alert("undefined !!!");
        }
        else if( blogInfo[i].bloggername ==" " || imageInfo[i].thumbnail ==" "){
            alert("공백 !!");
        }
        else if( blogInfo[i].bloggername =="" || imageInfo[i].thumbnail ==""){
            alert("띄어쓰기 !!");
        }
        else {
            console.log("띄어 !!");
            $('#blogUl').append(row);
        }
    }

    $('.blog_community_link').children().remove();

    var blogPageButton;

    blogPageButton = "<button id='prevPageNo' class='fas fa-angle-left' onClick='javascript:goPage("
                     + prevPageNo
                     + ")'></button>";
    for(var i=1; i<=11; i+=5){
        if(i==nowPageNo){
            blogPageButton += "<button class='fas fa-circle' onClick='javascript:goPage("
                            +   i
                            +   ")'></button>";
            console.log("if : " +i);
        }
        else {
            blogPageButton += "<button class='far fa-circle' onClick='javascript:goPage("
                            +   i
                            +   ")'></button>";
            console.log("else : " +i);
        }
    }
    if(nextPageNo >= 17){
        alert("게시물이 더 이상 없습니다.");
    }
    else {
        blogPageButton += "<button id='nextPageNo' class='fas fa-angle-right' onClick='javascript:goPage("
            + nextPageNo
            + ")'></button>";
    }
    $('.blog_community_link').append(blogPageButton);
    console.log(i);
    console.log(prevPageNo);
    console.log(nowPageNo);
    console.log(nextPageNo);
}