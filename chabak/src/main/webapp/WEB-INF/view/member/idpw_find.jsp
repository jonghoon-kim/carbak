<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/resources/css/idpw_find.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
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

<body>
<!-- header -->
<hr><br>
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
                <input type="text" id="name" placeholder="이름"><BR><BR>
                <div class="in-line">
                    <input type="email" placeholder="이메일">
                    <input type="button" value="인증"></button>
                </div>
            </div>
            <button type="submit" class="find"><span>아이디 찾기</span></button> <BR>
        </div>
        <div id="tab-2" class="tab-content">
            <div class="content-input">
                <input type="text" id="id" placeholder="아이디"> <BR><BR>
                <input type="text" id="name" placeholder="이름"> <BR><BR>
                <div class="in-line">
                    <input type="email" placeholder="이메일">
                    <input type="button" value="인증"></button>
                </div>
            </div>
            <button type="submit" class="find"><span>비밀번호 찾기</span></button> <BR>
        </div>
    </div>

    <div class="bottom">
        <button id="login_but" onclick="location.href='login.html'">로그인</button>
        <button id="sign_up_but" onclick="location.href='signup.html'">회원가입</button>
    </div>
</div>
</body>

</html>