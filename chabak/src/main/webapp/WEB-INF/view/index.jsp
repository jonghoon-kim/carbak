<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="css/index.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="js/jquery.FadeWideBgImg.js"></script>
    <script type="text/javascript" src="/js/reviewScript.js" charset='UTF-8'></script>
    <script type="text/javascript">
        //배너 롤링

        (function ($) {
            jQuery(document).ready(function () {
                $('.slideshow').FadeWideBgImg({interval: 2000});

                convertK();
            });
        }(window.jQuery, window));

        function test() {
            document.querySelector("#test").style.backgroundImage = "url('http://www.nnj.kr/data/file/c_photo/2943355932_f8RqZtQW_1.jpg')";
        }

        //반복문을 돌며 조회수를 변환(1000이 넘으면 단위를 K로)
        function convertK() {

            var bestViewTags = $(".best_views_span");
            var readCount;
            $(bestViewTags).each(function (index, element) {
                readCount = parseInt($(element).text());
                console.log("index:" + index + " item:" + element + " readCount:" + readCount)
                var resultValue;
                if (readCount >= 1000) {
                    resultValue = (readCount / 1000).toFixed(1) + 'K';
                } else {
                    resultValue = readCount;
                }
                $(element).text("views " + resultValue);
            });

        }
    </script>
</head>
<body>

<%
    // 로그인한 회원 정보 담기
    String id = null;
    String name = null;

    // 세션이 존재하면 아이디값을 받아 관리
    if (session.getAttribute("id") != null) {
        id = (String) session.getAttribute("id");
        name = (String) session.getAttribute("name");
    }
%>

<!-- 배너 -->
<div style="width:100%;">
    <ul class="slideshow">
        <li><img src="img/index/banner_01.png" alt="배너 이미지_04"/></li>
        <li><img src="img/index/banner_02.png" alt="배너 이미지_05"/></li>
        <li><img src="img/index/banner_03.png" alt="배너 이미지_06"/></li>
    </ul>
</div>
<!-- 헤더(인클루드 적용) -->
<header>
    <!-- nav바 -->
    <div id="header_left_but">
        <div class="search_but">
            <button onclick="location.href ='/campsite'">Campsite Info</button>
        </div>
        <div class="community_but">
            <button onclick="location.href ='/review'">Community</button>
        </div>
    </div>

    <div id="header_right_but">
        <% if (id != null) {%>
        <%--<div><span class="user"> ${sessionScope.id}님, 환영합니다!</span></div>--%>
        <div class="info_but">
            <button onclick="location.href ='/mypage/myInfo'">&nbsp;MyPage</button>
        </div>
        <div class="logout_but">
            <button onclick="location.href ='/member/logout'">
                <span>${sessionScope.id}</span></button>
        </div>
        <% } else {%>
        <div class="info_but"> <!-- todo: mypage 입장 못하게 수정 할 것 -->
            <button onclick="location.href ='/mypage/myInfo'">MyPage</button>
        </div>
        <div class="login_but">
            <button onclick="location.href ='/member/login'">Login</button>
        </div>
        <% }%>
    </div>
    <div class="logo">
        <a href="/index"><img src="img/header/main_logo.png"></a>
    </div>
</header>

<!-- 타이틀 -->
<div class="title">
    <h1>한눈에 보고 한번에 떠나는 차박여행~</h1>
    <h2>국내에 있는 모든 차박야영지 검색 가능!</h2>
</div>

<!-- 검색창 -->
<div class="index_search">
    <form method="POST" action="/campsite" onsubmit="return commonCheckInputNotEmpty(this,'검색어를 입력하세요.')">
        <input type="text" class="index_search_text" placeholder=" 원하는 야영지를 검색해주세요." name="keyword">
        <button type="submit" class="index_search_but"></button>
    </form>
</div>

<hr class="none_hr">
<!--오토 스크롤 버튼-->
<div class="test_css">
    <span></span>
    <button class="auto_scroll" type="button" onclick="$('html, body').stop().animate( { scrollTop : '+=1050'} ); "><p
            class="blinking">Click!!</p>인기리뷰 바로가기
    </button>
</div>

<div class="review_content">

    <c:choose>
        <c:when test="${sessionScope.id != null && similarUsers != null}" >
            <h1>추천 Reviews 5</h1>
            <h2 class="h2Name">${sessionScope.name}님을 위한 추천리뷰 모아보기</h2>
            <hr>
<%--            <div class="community_link">--%>
<%--                <a href="/review/recommend">더보기</a>--%>
<%--            </div>--%>
            <form METHOD="post" action="/review/recommend">
                <input type="hidden" value="${similarUsers}" name="similarUsers">
                <div class="community_link">
                    <button class="more" type="submit">더보기</button>
                </div>
            </form>
        </c:when>
        <c:when test="${empty reviewList1}">
            <h1>Best Reviews 5</h1>
            <h2>인기리뷰 모아보기</h2>
            <hr>
            <div class="community_link">
                <a class="more" href="/review/">더보기</a>
            </div>
        </c:when>
        <c:otherwise>
            <h1>Best Reviews 5</h1>
            <h2>인기리뷰 모아보기</h2>
            <hr>
            <div class="community_link">
                <a class="more" href="/review/">더보기</a>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="best_review">
        <ul>
            <c:forEach var="review" items="${reviewList}" varStatus="loop">
                <li>
                    <p class="best_id">ID:${review.id}</p>
                    <div class="best_img">
                        <img src="${review.titleImageSrc}">
                    </div>
                    <p class="best_views"><span class="best_views_span">${review.readCount}</span>
                        <c:choose>
                            <c:when test="${sessionScope.id != null and review.likeYn==1}">
                                <button class="like-but">
                                    <img class="like-img" src="img/community/heart2.png"
                                         onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')">
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="like-but">
                                    <img class="like-img" src="img/community/heart.png"
                                         onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')">
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p class="best_title">${review.title}</p>
                    <div class="best_content">${review.content}</div>
                    <p class="select_community"><a href="/review/detail?reviewNo=${review.reviewNo}">자세히보기</a></p>
                </li>
            </c:forEach>
            <c:forEach begin="${reviewList.size()}" end="4">
                <li>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<div class="footer">
    <img src="img/footer/footer.png">
</div>
</body>
</html>