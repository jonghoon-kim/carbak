<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/recommendReview.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
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
<hr class="top_hr">
<br>
<br>
<div class="container">
    <div class="top">
        <h1>추천 리뷰</h1>
    </div>
    <c:forEach var="review" items="${recommendReviewList}">
    <div class="review">
        <div class="profile">
            <div class="thumbnail-wrapper">
                <div class="thumbnail">
                    <div class="centered">
                        <img src="${review.savePath}${review.saveName}" alt="profile image">
                    </div>
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
                            <button class="like-img"><img class="toggle-like-img" id="like-img${review.reviewNo}"
                                                          src="/img/community/heart2.png"
                                                          onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')"
                                                          alt="like heart full image"></button>
                        </c:when>
                        <c:otherwise>
                            <button class="like-img"><img class="toggle-like-img" id="like-img${review.reviewNo}"
                                                          src="/img/community/heart.png"
                                                          onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')"
                                                          alt="like heart empty image"></button>
                        </c:otherwise>
                    </c:choose>
                    <button class="comment-img"><img src="/img/community/comment.png"
                                                     onclick="location.href='/review/detail?reviewNo='+'${review.reviewNo}'+'#reply'"
                                                     alt="comment image"></button>
                </div>
            </div>
        </div>
    </div>
</div>
</c:forEach>
</div>
</body>
</html>