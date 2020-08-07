<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/community.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/common.js" charset='UTF-8'></script>
    <script type="text/javascript" src="/js/reviewScript.js" charset='UTF-8'></script>
    <script>
        //ajax 사용 후 페이지 이동 후 뒤로가기로 돌아왔을 때 변경내용(db)가 화면에 반영 안 되는 것을 고치기(뒤로 가기시 다시 페이지 로드)
        window.onpageshow = function(event) {
            //정렬타입 select 값 설정
            var selectSortType = $("#sortType");
            selectSortType.val("${sortType}");

            // var pageOwnerIdVar = $("#pageOwnerIdSaved").val();

            console.log("searchText:"+"${searchText}"+ " sortType:"+"${sortType}");
            if ( event.persisted || (window.performance && window.performance.navigation.type === 2)) {
                // Back Forward Cache로 브라우저가 로딩될 경우 혹은 브라우저 뒤로가기 했을 경우
                console.log("back");
                window.location.reload();
            }
        }

        function fn_paging(curPage) {
            ajaxReviewList('${sessionScope.id}',true,curPage);
        }

        //드롭다운 영역 클릭시 드롭다운 보이게
        function myFunction(reviewNo) {
            console.log("myFunction()");
            var dropdowns = document.getElementsByClassName("dropdown-content");

            var i;

            for (i = 0; i < dropdowns.length; i++) {
                var openDropdown = dropdowns[i];

                if (openDropdown.classList.contains('show')) {
                    openDropdown.classList.remove('show');
                }
            }
             document.getElementById("myDropdown"+reviewNo).classList.toggle("show");

        }

        //드롭다운 영역이 아닌 곳을 클릭하면 드롭다운 닫힘
        window.onclick = function (event) {
            if (!event.target.matches('.writer-id')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");

                var i;

                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];

                    if (openDropdown.classList.contains('show')) {
                        console.log("openList:" + openDropdown);
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }

        //쪽지 작성 팝업 띄움
        function openWinMessageWrite(receiveId,sessionId){
            if (sessionId == "" || sessionId == null) {
                askLogin();
            }else{
                window.open("/message/write?receiveId="+receiveId, "쪽지 작성", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
            }
        }

        function goMyPage(reviewId,sessionId) {
            if (sessionId == "" || sessionId == null) {
                askLogin();
            }else{
                location.href="/mypage/guestVisit?id="+reviewId;
            }

        }
    </script>
</head>
<body>
<!-- header -->
<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr"><br>
<br>
<div class="container">
    <div class="top">
        <h1>커뮤니티</h1>
    </div>
    <div class="search">
<%--        pageOwnerId 저장하는 hidden input--%>
        <input type = "hidden" id="pageOwnerIdSaved" value="${pageOwnerId}">
<%--    팔로워 리뷰 글 검색용 체크박스--%>
        <input type = "checkbox" id="isFollowerSearch" value="y" onchange="ajaxReviewList('${sessionScope.id}',false,'1')">
        <input type="text" class="search_text" placeholder=" 지역 검색" name="searchText" id="search_text" value="${searchText}">
        <%--        검색버튼 눌렀을 때 검색어 저장 input--%>
        <input type="hidden" name="search_text_saved" id="search_text_saved" value="${searchText}">
        <button type="button" class="search_but" onclick="ajaxReviewList('${sessionScope.id}',true,'${pagination.curPage}')"></button>
    </div>
    <!-- 글쓰기, 정렬 버튼 -->
    <div class="second">
        <div class="insert">
            <button type="submit" onclick="location.href='/review/writeForm'">글쓰기</button>
        </div>
        <div class="sort" onchange="ajaxReviewList('${sessionScope.id}',false,'${pagination.curPage}')">
            <select id="sortType" name="sortType">
                <option value="regDate">최신 순</option>
                <option value="likeCount">좋아요 순</option>
                <option value="replyCount">댓글 많은 순</option>
            </select>
        </div>
    </div>

    <%--리뷰글 원형 시작--%>
    <%--            원형 복사시 수정할 부분: #dummy-review(id),#writer-id(value),#review-img(src,onclick) #review-content(value)--%>
    <div class="review" id="dummy-review" style="display: none">
        <div class="profile">
            <div class="thumbnail-wrapper">
                <div class="thumbnail">
                    <div class="centered">
                        <img src="" alt="profile image">
                    </div>
                </div>
            </div>
            <div class="writer">
                <div class="dropdown">
                    <button class="dropbtn">
                         <span class="writer-id" onclick="">
                          </span>
                    </button>
                    <div class="dropdown-content" id="">
                        <a class="mypage" onclick="" target="_blank">마이페이지</a>
                        <a class="message" onclick="">쪽지 보내기</a>
                    </div>
                </div>
            </div>
            <div class="regDate">
            </div>
        </div>
        <div class="content">

            <div class="review-img">

                <img src=""
                     onclick="" alt="review image">
            </div>
            <div class="review-content">
                <div class="content-title">

                </div>
                <div class="content-icon">
                    <button class="like-img"><img class="toggle-like-img" src="/img/community/heart.png"
                                                  onclick="" alt="like heart empty image"></button>
                    <button class="comment-img"><img src="/img/community/comment.png" alt="comment image"></button>
                </div>
            </div>

        </div>
    </div>
    <%-- 리뷰글 원형 끝           --%>

    <%--    reviewListDiv 시작--%>
    <div id="reviewListDiv">
        <!-- 게시글 리스트 -->
        <c:forEach var="review" items="${reviewList}">
            <div class="review">
                <div class="profile">
                    <div class="thumbnail-wrapper">
                        <div class="thumbnail">
                            <div class="centered">
                                <img src="${review.savePath}${review.saveName}" alt="profile image">
                            </div>
                        </div>
                    </div>
                    <div class="writer">

                        <div class="dropdown">
                            <button class="dropbtn">
                                <span class="writer-id" onclick="myFunction('${review.reviewNo}')">
                                    ${review.id}
                                </span>
                           </button>
                            <div class="dropdown-content" id="myDropdown${review.reviewNo}">
                                <a onclick="goMyPage('${review.id}','${sessionScope.id}')" target="_blank">마이페이지</a>
                                <a onclick="openWinMessageWrite('${review.id}','${sessionScope.id}')">쪽지 보내기</a>
                            </div>
                        </div>
                    </div>
                    <div class="regDate">
                        ${review.regDate}
                    </div>
                </div>

                <div class="content">

                    <div class="review-img">

                        <img src="${review.titleImageSrc}"
                             onclick="location.href='/review/detail?reviewNo=${review.reviewNo}'" alt="review image">

                    </div>
                    <div class="review-content">
                        <div class="content-title">

                                <%--                    [강원도][동해시] 망상 오토 캠핑장--%>
                            [${review.sido}][${review.gugun}] ${review.title}
                        </div>
                        <div class="content-icon">
                            <c:choose>
                                <c:when test="${sessionScope.id != null and review.likeYn==1}">
                                    <button class="like-img"><img class="toggle-like-img" id="like-img${review.reviewNo}" src="/img/community/heart2.png"
                                                                  onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')" alt="like heart full image"></button>
                                </c:when>
                                <c:otherwise>
                                    <button class="like-img"><img class="toggle-like-img" id="like-img${review.reviewNo}" src="/img/community/heart.png"
                                                                  onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')" alt="like heart empty image"></button>
                                </c:otherwise>
                            </c:choose>

                            <button class="comment-img"><img src="/img/community/comment.png" onclick="location.href='/review/detail?reviewNo='+'${review.reviewNo}'+'#reply'"  alt="comment image"></button>
                        </div>
                    </div>

                </div>
            </div>
        </c:forEach>

    </div>
    <%--    reviewListDiv 끝--%>
    <!-- pagination{s} -->
    <a class="paginationBut1" id="curRange_ne_1" onclick="fn_paging(1)" style="display: none">[처음]</a>

    <a class="paginationBut1" id="curPage_ne_1" onclick="" style="display:none">[이전]</a>

    <span class="paginationBut1" id="i_eq_curPage" style="font-weight: bold; display: none;"><a onclick=""></a></span>

    <a class="paginationBut1" id="i_ne_curPage" style="display: none" onclick=""></a>

    <a class="paginationBut1" id="curPage_ne_pageCnt" style="display: none" onclick="">[다음]</a>

    <a class="paginationBut1" id="curRange_ne_rangeCnt" style="display: none" onclick="">[끝]</a>


    <div id="pagingDiv">
        <c:if test="${pagination.curRange ne 1 }">
            <a class="paginationBut" onclick="fn_paging(1)">[처음]</a>
        </c:if>
        <c:if test="${pagination.curPage ne 1}">
            <a class="paginationBut" onclick="fn_paging('${pagination.prevPage }')">[이전]</a>
        </c:if>
        <c:forEach var="pageNum" begin="${pagination.startPage }" end="${pagination.endPage }">
            <c:choose>
                <c:when test="${pageNum eq  pagination.curPage}">
                    <span style="font-weight: bold;"><a class="paginationBut" onclick="fn_paging('${pageNum }')">${pageNum }</a></span>
                </c:when>
                <c:otherwise>
                    <a class="paginationBut" onclick="fn_paging('${pageNum }')">${pageNum }</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${pagination.curPage ne pagination.pageCnt && pagination.pageCnt > 0}">
            <a class="paginationBut" onclick="fn_paging('${pagination.nextPage }')">[다음]</a>
        </c:if>
        <c:if test="${pagination.curRange ne pagination.rangeCnt && pagination.rangeCnt > 0}">
            <a class="paginationBut" onclick="fn_paging('${pagination.pageCnt }')">[끝]</a>
        </c:if>
<%--        <div>--%>
<%--            총 게시글 수 : ${pagination.listCnt } /    총 페이지 수 : ${pagination.pageCnt } / 현재 페이지 : ${pagination.curPage } / 현재 블럭 : ${pagination.curRange } / 총 블럭 수 : ${pagination.rangeCnt }--%>
<%--        </div>--%>
    </div>
    <!-- pagination{e} -->
</div>
<%--/container--%>
<div class="footer">
    <img src="/img/footer/footer.png">
</div>
</body>
</html>