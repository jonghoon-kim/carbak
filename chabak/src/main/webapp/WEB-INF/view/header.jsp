<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/header.css" rel="stylesheet">
</head>
<body>
<%
    // 로그인한 회원 정보 담기
    String id = null;
    Boolean adminChk = null;
    // 세션이 존재하면 아이디값을 받아 관리
    if(session.getAttribute("id") != null) {
        id = (String)session.getAttribute("id");
        adminChk = (Boolean)session.getAttribute("adminChk");
    }
%>
<script>

</script>
<header>
    <!-- nav바 -->
    <div id="header_left_but">
    <% if (adminChk!=null && !adminChk) {%>
        <div class="search_but">
            <button onclick="location.href ='/admin'">Members</button>
        </div>
        <div class="community_but">
            <button onclick="location.href ='/review'">Community</button>
        </div>
    <%}  else {%>
        <div class="search_but">
            <button onclick="location.href ='/campsite'">Campsite Info</button>
        </div>
        <div class="community_but">
            <button onclick="location.href ='/review'">Community</button>
        </div>
        <%}%>
    </div>
    <div id="header_right_but">
        <% if (id != null) {%>
        <div class="info_but">
            <button onclick="location.href ='/mypage/myInfo'">&nbsp;MyPage</button>
        </div>
        <div class="logout_but">
            <button onclick="location.href ='/member/logout'">
                <span>${sessionScope.id}</span></button>
        </div>
        <% } else {%>
        <div class="info_but">
            <button onclick="location.href ='/mypage/myInfo'">&nbsp;MyPage</button>
        </div>
        <div class="login_but">
            <button onclick="location.href ='/member/login'">Login</button>
        </div>
        <% }%>
    </div>
    <div class="logo">
        <a href="/index"><img src="/img/header/main_logo.png"></a>
    </div>
</header>

</body>

</html>