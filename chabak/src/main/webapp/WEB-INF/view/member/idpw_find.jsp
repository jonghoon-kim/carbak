<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/idpw_find.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/idpw_find.js" charset='UTF-8'></script>
    <script>
        $(document).ready(function () {

            $('ul.tabs li').click(function () {
                var tab_id = $(this).attr('data-tab');

                $('ul.tabs li').removeClass('current');
                $('.tab-content').removeClass('current');

                $(this).addClass('current');
                $("#" + tab_id).addClass('current');
            })

        })
    </script>
</head>
<body>
<!--header-->
<div id="header">
    <jsp:include page="/header"/>
</div>

<hr class="top_hr"><br><br>
<div class="container">
    <div class="top">
        <h1>아이디 / 비밀번호 찾기</h1>
    </div>
    <!-- 탭 -->
    <div class="tab-wrap">

        <ul class="tabs">
            <li class="tab-link current" data-tab="tab-1">아이디 찾기</li>
            <li class="tab-link" data-tab="tab-2">비밀번호 찾기</li>
        </ul>
        <div id="tab-1" class="tab-content current">
            <div class="content-input">
                <!-- 아이디와 이름 값을 팝업(idFind)로 보내기 위한 hidden -->
                <input type="hidden" id="parentId">
                <input type="hidden" id="parentName">
                <input type="text" name="name" id="id_name" placeholder="이름"><BR><BR>
                <div class="in-line">
                    <input type="email" name="email" id="id_email" placeholder="이메일">
                    <input type="button" onclick="return emailCheck()" value="인증">
                </div>
            </div>
            <button class="find" onclick="checkValue()"><span>아이디 찾기</span></button> <BR>
        </div>
        <div id="tab-2" class="tab-content">

            <div class="content-input">
                <input type="text" name="id" id="pw_id" placeholder="아이디"> <BR><BR>
                <input type="text"
                       name="name" id="pw_name" placeholder="이름"> <BR><BR>
                <div class="in-line">
                    <input type="hidden" id="parentEmail">
                    <input type="email" name="email" id="pw_email" placeholder="이메일">
                    <input type="button" onclick="return emailCheck()" value="인증"></button>
                </div>
            </div>
            <button class="find" onclick="checkValue2()"><span>비밀번호 찾기</span></button> <BR>

        </div>
    </div>

    <div class="bottom">
        <button id="login_but" onclick="location.href='login.html'">로그인</button>
        <button id="sign_up_but" onclick="location.href='signup.html'">회원가입</button>
    </div>
</div>

<div class="footer">
    <img src="/img/footer/footer.png">
</div>
</body>

</html>