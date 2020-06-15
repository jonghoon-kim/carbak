<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>슬기로운 차박생활</title>
    <link href="/resources/css/index.css" rel="stylesheet">
    <link href="/resources/css/campsite.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/resources/js/address_select.js"></script>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script>
        /*
        function selectPlacesAndsearchPlaces(){
            var sido = document.getElementById('#sido').value;
            var guSelect = document.getElementById('guSelect').value;
            var selectP = document.getElementById('selectP').value;

            alert(sido);
            console.log(siSelect);
            console.log(guSelect);
            console.log(selectP);

        }
*/
    </script>
</head>
<body>
<!-- 헤더(인클루드 적용) -->
<header>
    <!-- nav바 -->
    <div id="header_left_but">
        <div class="search_but">
            <button onclick="location.href ='campsite.html'">Campsite Info</button>
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
        <a href="index.html"><img src="/resources/img/header/main_logo.png"></a>
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
        <form method="POST" action="/campsite/campsiteSearchPlaces">
            <div class="address_select">
                <select name="sido" id="sido"></select>
                <select name="gugun" id="gugun"></select>
                <input type="text" name="campsitename">
                <button type="submit">검색</button>
            </div>
        </form>

    </div>

    <!-- 지도 생성 -->
    <div id="map" style="width:570px;height:570px; margin-left:340px; float:left;"></div>

    <!-- 야영지정보 -->
    <div class="campsite_aticle">
        <h2>야영지 검색결과</h2>

        <!-- 야영지 선택 -->
        <div class="campsite_info">
            <ul>
                <li>
                    (1)
                    <p class="name">야영지명 :
                        <a href="campsite_select.html" onclick="#">충주 수주팔봉 캠핑 글램핑</a></p>
                    <p class="address">주소 : 충북 충주시 대소원면 문주리</p>
                </li>
                <li>
                    (2)
                    <p class="name">야영지명 :
                        <a href="#" onclick="#">충주 수주팔봉 캠핑 글램핑</a></p>
                    <p class="address">주소 : 충북 충주시 대소원면 문주리</p>
                </li>
                <li>
                    (3)
                    <p class="name">야영지명 :
                        <a href="#" onclick="#">충주 수주팔봉 캠핑 글램핑</a></p>
                    <p class="address">주소 : 충북 충주시 대소원면 문주리</p>
                </li>
                <li>
                    (4)
                    <p class="name">야영지명 :
                        <a href="#" onclick="#">충주 수주팔봉 캠핑 글램핑</a></p>
                    <p class="address">주소 : 충북 충주시 대소원면 문주리</p>
                </li>
                <li>
                    (5)
                    <p class="name">야영지명 :
                        <a href="#" onclick="#">충주 수주팔봉 캠핑 글램핑</a></p>
                    <p class="address">주소 : 충북 충주시 대소원면 문주리</p>
                </li>
            </ul>
        </div>

    </div>

    <hr class="top_hr">

    <!-- 커뮤니티리뷰 -->
    <div class="blog_aticle">
        <h2>커뮤니티 리뷰</h2>
        <ul>
            <li>
                <p class="best_id">ID:차박차박</p>
                <div class="best_img">
                    <img src="/resources/img/reviews/01.jpg">
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
        <img src="/resources/img/footer/footer.png">
    </div>
</div>

<!-- 카카오 MAP -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9613dfdd64d6afc0ba0dc90bfcd01cf3"></script>
<script>

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다
var positions = [
    {
        content: '<div>카카오</div>',
        latlng: new kakao.maps.LatLng(33.450705, 126.570677)
    },
    {
        content: '<div>생태연못</div>',
        latlng: new kakao.maps.LatLng(33.450936, 126.569477)
    },
    {
        content: '<div>텃밭</div>',
        latlng: new kakao.maps.LatLng(33.450879, 126.569940)
    },
    {
        content: '<div>근린공원</div>',
        latlng: new kakao.maps.LatLng(33.451393, 126.570738)
    }
];

for (var i = 0; i < positions.length; i ++) {
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: positions[i].latlng // 마커의 위치
    });

    // 마커에 표시할 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({
        content: positions[i].content // 인포윈도우에 표시할 내용
    });

    // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
    // 이벤트 리스너로는 클로저를 만들어 등록합니다
    // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}

</script>
</body>
</html>
