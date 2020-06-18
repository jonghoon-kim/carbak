<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>슬기로운 차박생활</title>
    <link href="css/index.css" rel="stylesheet">
    <link href="css/campsite.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="js/address_select.js" charset='euc-kr'></script>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>
<!-- 헤더(인클루드 적용) -->
<header>
    <!-- nav바 -->
    <div id="header_left_but">
        <div class="search_but">
            <button onclick="location.href ='campsite.jsp'">Campsite Info</button>
        </div>
        <div class="community_but">
            <button onclick="location.href ='community.jsp'">Community</button>
        </div>
    </div>

    <div id="header_right_but">
        <div class="info_but">
            <button onclick="location.href ='#'">&nbsp;MyPage</button>
        </div>
        <div class="login_but">
            <button onclick="location.href ='login.html'">Login</button>
        </div>
    </div>
    <div class="logo">
        <a href="index.html"><img src="img/header/main_logo.png"></a>
    </div>
</header>

<hr class="top_hr"><br>

<div class="container">

    <!-- 부제 -->
    <div class = "top">
        <h1>야영지 정보</h1>
    </div>
    <!-- 지역검색 -->
    <div class="campsite_section">

        <!-- 지역 선택-->
        <div class="address_select">
            <select name="sido" id="sido"></select>
            <select name="gugun" id="gugun"></select>
            <input type="text">
            <button>검색</button>
        </div>

        <!-- 지도 생성 -->
        <div id="map">
            <img src="img/campsite/map.png">
        </div>
    </div>

    <!-- 야영지정보 -->
    <div class="campsite_aticle">
        <h2>야영지 검색결과</h2>

        <!-- 야영지 선택 -->
        <div class="campsite_select">
            <ul>
                <li>
                    <p class="name">야영지명 :
                        <a href="#" onclick="#">충주 수주팔봉 캠핑 글램핑</a>
                    </p>
                    <p class="address">주소 : 충북 충주시 대소원면 문주리</p>
                    <p class="web-address">홈페이지 주소 : <a href="https://www.instagram.com/cheongye_sanjang/">https://www.instagram.com/cheongye_sanjang/</a></p>
                    <p class="info">야영지 정보 : 단체석, 주차, 예약, 무선 인터넷, 남/녀 화장실 구분</p>
                    <p class="content">소개글 : 기업, 단체 워크샵이 가능한 시설 (대형 바베큐장) 2018년 4월 신축 완공
                        <br>가족, 연인과 함께 서울 시내에 만들 수 있는 최고의 추억을 만들어 가세요~</p>
                </li>
            </ul>
        </div>

    </div>

    <hr class="top_hr">

    <!-- 커뮤니티 리뷰 -->
    <div class="community_aticle">
        <h2>커뮤니티 리뷰</h2>
        <ul>
            <li>
                <p class="best_id">ID:차박차박</p>
                <div class="best_img">
                    <img src="img/reviews/01.jpg">
                </div>
                </p>
                <p class="best_title">슬기로운 차박생활♥</p>
                <p class="best_content">이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며
                </p>
                <p class="select_community"><a href="community.html">자세히보기</a></p>
            </li>
            <li><img><p>영역 나누기2</p></li>
            <li><img><p>영역 나누기3</p></li>
            <li><img><p>영역 나누기4</p></li>
            <li><img><p>영역 나누기5</p></li>
        </ul>
        <!-- 페이지 버튼 -->
        <div class="community_link">
            <button class='fas fa-angle-left'></button>
            <button class='fas fa-circle'></button>
            <button class='far fa-circle'></button>
            <button class='far fa-circle'></button>
            <button class='fas fa-angle-right'></button>
        </div>
    </div>

    <hr class="top_hr">

    <!-- 블로그 -->
    <div class="blog_aticle">
        <h2>블로그</h2>
        <ul>
            <li>
                <p class="best_id">ID:차박차박</p>
                <div class="best_img">
                    <img src="img/reviews/01.jpg">
                </div>
                </p>
                <p class="best_title">슬기로운 차박생활♥</p>
                <p class="best_content">이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며
                </p>
                <p class="select_community"><a href="community.html">자세히보기</a></p>
            </li>
            <li><img><p>영역 나누기2</p></li>
            <li><img><p>영역 나누기3</p></li>
            <li><img><p>영역 나누기4</p></li>
            <li><img><p>영역 나누기5</p></li>
        </ul>
        <!-- 페이지 버튼 -->
        <div class="community_link">
            <button class='fas fa-angle-left'></button>
            <button class='fas fa-circle'></button>
            <button class='far fa-circle'></button>
            <button class='far fa-circle'></button>
            <button class='fas fa-angle-right'></button>
        </div>
    </div>

    <!--footer-->
    <div class="footer">
        <img src="img/footer/footer.png">
    </div>

</div>
</body>
</html>
