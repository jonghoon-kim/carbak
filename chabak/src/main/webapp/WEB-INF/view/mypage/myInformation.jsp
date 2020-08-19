<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <link href="/css/myInformation.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Oldenburg&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <%--    session   --%>
    <style>
        button {
            float: left;
        }
    </style>
</head>

<body>
<div class="wrap">
    <!-- header -->
    <div id="header">
        <jsp:include page="/header"/>
    </div>
    <hr class="top_hr">
    <br>
    <div class="container">
        <div class="top">
            <!-- 클릭 시 프로필 수정 페이지 이동-->
            <div class="thumbnail-wrapper">
                <div class="thumbnail">
                    <div id="View_area" class="centered">
                        <!-- 방문 id가 세션 id랑 비교해서 같으면 나의 프로필 사진을 / 아니면 click id 프로필 선택 -->
                        <c:choose>
                            <c:when test="${empty pageOwner.id}">
                                <img src="${member.savePath}${member.saveName}">
                            </c:when>
                            <c:when test="${sessionScope.id ne pageOwner.id}">
                                <img src="${pageOwner.savePath}${pageOwner.saveName}">
                            </c:when>
                            <c:otherwise>
                                <img src="${member.savePath}${member.saveName}"><!-- 다른사람 계정에서 내 아이디 클릭한 경우 -->
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <div class="button_menu">
                <div class="sessionId" id="pageOwnerId">
                    <!-- 방문 id가 세션 id랑 비교해서 같으면 나의 id를 / 다르면 click id를 선택 -->
                    <c:choose>
                        <c:when test="${empty pageOwner.id}">
                            <div class="pageOwnerId">${sessionScope.id}</div>
                            <button class="updateMember" id="btn_updateMember" style="display: inline;"
                                    onclick="location.href='/member/memberUpdate?id=${sessionScope.id}';">
                                회원정보수정
                            </button>
                            <button class="messageList" id="btn_messageList" style="display: inline;"
                                    onclick="location.href='/message/list';">
                                쪽지함
                            </button>
                        </c:when>
                        <c:when test="${sessionScope.id ne pageOwner.id}">
                            <div class="pageOwnerId">${pageOwner.id}</div>
                        </c:when>
                        <c:otherwise>
                            <div class="pageOwnerId">${sessionScope.id}</div>
                            <button class="updateMember" id="btn_updateMember" style="display: inline;"
                                    onclick="location.href='/member/memberUpdate?id=${sessionScope.id}';">
                                회원정보수정
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="profileFollowBtn">
                    <button id="btn_profile_follow" style="display: none;"></button>
                </div>

                <!--게시글 수 보이게, 클릭시 자기가 작성한 리뷰글 조회 + 게시글 수 추가-->
                <c:choose>
                    <c:when test="${empty pageOwner.id}">
                        <div class="review_btn">
                            <div class="countMenu">${countReview}</div>
                            <button type="button" class="reviewListId" onclick="printReviewList('${sessionScope.id}')">
                                게시글
                            </button>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.id ne pageOwner.id}">
                        <div class="review_btn">
                            <div class="countMenu">${countReview}</div>
                            <button type="button" class="reviewListId" onclick="printReviewList('${pageOwner.id}')">
                                게시글
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="review_btn">
                            <div class="countMenu">${countReview}</div>
                            <button type="button" class="reviewListId" onclick="printReviewList('${sessionScope.id}')">
                                게시글
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- 팔로워 버튼 본인 id인 경우, 방문자 id인 경우 -->
                <c:choose>
                    <c:when test="${empty pageOwner.id}">
                        <div class="follower_btn" >
                            <div class="countMenu" id="followerCount">${countFollower}</div>
                            <button type="button" class="follower"
                                    onclick="followList('${sessionScope.id}', 'follower')">팔로워
                            </button>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.id ne pageOwner.id}">
                        <div class="follower_btn">
                            <div class="countMenu" id="followerCount">${countFollower}</div>
                            <button type="button" class="follower" onclick="followList('${pageOwner.id}', 'follower')">
                                팔로워
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="follower_btn">
                            <div class="countMenu">${countFollower}</div>
                            <button type="button" class="follower"
                                    onclick="followList('${sessionScope.id}', 'follower')">팔로워
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- 팔로잉 버튼 본인 id인 경우, 방문자 id인 경우 -->
                <c:choose>
                    <c:when test="${empty pageOwner.id}">
                        <div class="following_btn">
                            <div class="countMenu" id="followingCount">${countFollowing}</div>
                            <button type="button" class="following"
                                    onclick="followList('${sessionScope.id}', 'following')">팔로잉
                            </button>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.id ne pageOwner.id}">
                        <div class="following_btn">
                            <div class="countMenu" id="followingCount">${countFollowing}</div>
                            <button type="button" class="following"
                                    onclick="followList('${pageOwner.id}', 'following')">팔로잉
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="following_btn">
                            <div class="countMenu" id="followingCount">${countFollowing}</div>
                            <button type="button" class="following"
                                    onclick="followList('${sessionScope.id}', 'following')">팔로잉
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="content">
            <div class="listForm" id="selectedPosition"> <!-- follower, following, review 리스트 출력 위치-->
                <c:forEach var="review" items="${reviewList}">
                    <!-- 리스트 frame point -->
                    <div class="reviewList" id="reviewListId">
                        <div class="reviewImg">
                            <a class="reviewDetail" id="reviewDetail" href="/review/detail?reviewNo=${review.reviewNo}">
                                <img class="imgAppendPoint" id="imgAppendPoint" src="${review.titleImageSrc}">
                            </a>
                            <span class="reviewImgHover"></span>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
    </div>

    <div class="footer">
        <img src="/img/footer/footer.png">
    </div>
</div>

<jsp:include page="/WEB-INF/view/mypage/myInfoFrame.jsp"/>
<script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="/js/myPage.js"></script>
</body>
</html>
