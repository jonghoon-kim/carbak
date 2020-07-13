<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <!--<script type="text/javascript" src="js/3dRotaingButton.js"></script>-->
    <script type="text/javascript">
        //배너 롤링
        (function($){
            jQuery(document).ready(function(){
                $('.slideshow').FadeWideBgImg({interval:2000});
            });
        }(window.jQuery,window));

        function test(){
            document.querySelector("#test").style.backgroundImage="url('http://www.nnj.kr/data/file/c_photo/2943355932_f8RqZtQW_1.jpg')";
        }
        // ♥icon 클릭 이벤트
        var cnt=1;
        function imgToggle(){
            var like_empty_img = document.getElementById("like_empty_img");
            var like_full_img = document.getElementById("like_full_img");
            if(cnt%2==1){
                like_empty_img.src="img/community/heart2.png";
                like_full_img.src="img/community/heart.png";
            } else{
                like_empty_img.src="img/community/heart.png";
                like_full_img.src="img/community/heart2.png";
            }
            cnt++;
        }
    </script>
</head>
<body>
<%
    // 로그인한 회원 정보 담기
    String id = null;
    String name = null;

    // 세션이 존재하면 아이디값을 받아 관리
    if(session.getAttribute("id") != null) {
        id = (String)session.getAttribute("id");
        name = (String)session.getAttribute("name");
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
            <button onclick="location.href ='/campsite/campsite'">Campsite Info</button>
        </div>
        <div class="community_but">
            <button onclick="location.href ='community.html'">Community</button>
        </div>
    </div>

    <div id="header_right_but">
        <% if (id != null) {%>
        <%--<div><span class="user"> ${sessionScope.id}님, 환영합니다!</span></div>--%>
        <div class="info_but">
            <button onclick="location.href ='#'">&nbsp;MyPage</button>
        </div>
        <div class="logout_but">
            <button onclick="location.href ='/member/logout'">
                <span>${sessionScope.id}</span></button>
        </div>
        <% } else {%>
        <div class="info_but">
            <button onclick="location.href ='#'">&nbsp;MyPage</button>
        </div>
        <div class="login_but">
            <button onclick="location.href ='/member/login'">Login</button>
        </div>
        <% }%>
    </div>
    <div class="logo">
        <a href="index.html"><img src="img/header/main_logo.png"></a>
    </div>
</header>

<!-- 타이틀 -->
<div class="title">
    <h1>한눈에 보고 한번에 떠나는 차박여행~</h1>
    <h2>국내에 있는 모든 차박야영지 검색 가능!</h2>
</div>

<!-- 검색창 -->
<div class="index_search">
    <input type="text" class="index_search_text" placeholder=" 원하는 야영지를 검색해주세요.">
    <button type="submit" class="index_search_but"></button>
</div>

<hr class="none_hr">
<!--오토 스크롤 버튼-->
<div class="test_css">
    <span></span>
    <button class="auto_scroll" type="button" onclick="$('html, body').stop().animate( { scrollTop : '+=1050'} ); "><p class="blinking">Click!!</p>인기리뷰 바로가기</button>
</div>

<div class="review_content">
    <h1>Best Reviews 5</h1>
    <h2>인기리뷰 모아보기</h2>
    <hr>
    <div class="community_link">
        <a href="community.html">더보기</a>
    </div>
    <div class="best_review">
        <ul>
            <li>
                <p class="best_id">ID:차박차박</p>
                <div class="best_img">
                    <img src="img/reviews/01.jpg">
                </div>
                <p class="best_views">views 1.24k
                    <button class="like-but" onclick="imgToggle()">
                        <img class="like-img" id="like_empty_img" src="img/community/heart.png">
                        <img class="like-img" id="like_full_img" src="img/community/heart2.png">
                    </button>
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
    </div>
</div>
<div class="footer">
    <img src="img/footer/footer.png">
</div>
</body>
</html>