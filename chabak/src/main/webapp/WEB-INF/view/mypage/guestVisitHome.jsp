<!--  todo: 다른 유저 게시판을 방문했을 때 나오는 화면 : 팔로잉 삭제기능 안보이기, 내정보 수정 안보이기, 게시판 수정 안보이기 -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <link href="/css/myInformation.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Oldenburg&display=swap" rel="stylesheet">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/myInfo.js" charset="UTF-8"></script>
    <meta charset="UTF-8">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <%--    session--%>
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
    <script type="text/javascript" src="/js/myInfo.js" charset="UTF-8"></script>
    <script>
        function getSession(){
            return '${sessionScope.id}';
        }
    </script>
    <hr class="top_hr">
    <br>
    <div class="container">
        <div class="top">
            <h1>MY PAGE</h1>
        </div>
        <div class="content">
            <!-- 클릭 시 프로필 수정 페이지 이동-->
            <div class="thumbnail-wrapper">
                <div class="thumbnail">
                    <div id="View_area" class="centered">
                        <img src="${sessionScope.profile}">
                    </div>
                </div>
                <div class="sessionId">
                    ${sessionScope.id}
                </div>
            </div>

            <%--
                        <c:forEach var="getFollowerId" items="${HashMapList}" varStatus="status">
                            ${items.FOLLOWERID}
                            ${items.FOLLOWINGID}

                        </c:forEach>
            --%>
            <div class="button_menu">
                <!--게시글 수 보이게, 클릭시 자기가 작성한 리뷰글 조회 + 게시글 수 추가-->
                <button type="button" id="">
                    게시글
                </button>
                <!--팔로워 수 보이게, 클릭시 팔로워 리뷰글 조회 + 팔로워 수 추가-->
                <button type="button" id="follower">
                    팔로워
                </button>
                <!--숫자 클릭시 팔로워한 id 조회 리스트(팔로워 수 1000단위 k표현 100000단위 m표현-->

                <!--팔로잉 수 보이게, 클릭시 팔로잉 리뷰글 조회 + 팔로잉 수 추가-->
                <img class="but_img" src="/img/mypage/following.png">

                <button type="button" id="following">
                    <span>팔로잉</span>
                </button>
                <!--숫자 클릭시 팔로잉한 id 조회 리스트(팔로워 수 1000단위 k표현 1000000단위 m표현-->

                <%--    <!-- 위에 버튼 클릭 시, 화면단에서 비동기 처리(ajax 활용) todo:input hidden -->
                    <ul id="result" class="mypage_board">
                        <li class="active"></li> <!-- 버튼 클릭시 결과가 나오는 라인 -->
                    </ul>--%>
            </div>
        </div>
    </div>

    <div class="listForm"> <!-- follower, following 리스트 출력 -->
        <ul class="listUl">
            follow test :
        </ul>
    </div>

    <div class="footer">
        <img src="/img/footer/footer.png">
    </div>
</div>

</body>
</html>
