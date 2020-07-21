<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/signup.css" rel="stylesheet">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="/js/address_select.js" charset='UTF-8'></script>
    <!-- 프로필 사진 미리 보기 -->
    <script type="text/javascript" src="/js/profile_preview.js" charset='UTF-8'></script>
    <!-- 아이디 및 이메일 중복 체크, 유효성 검사 -->
    <script type="text/javascript" src="/js/signup_check.js" charset='UTF-8'></script>
</head>
<body>
<div id="wrap">

    <!-- header -->
    <div id="header">
        <jsp:include page="/header"/>
    </div>
    <hr class="top_hr"><br>
    <div class="container">
        <div class="top">
            <h1>회원가입</h1>
        </div>
        <form class="form" method="post" name="signupForm" id="signupForm" onsubmit="return checkValue()" enctype="multipart/form-data">
            <div class="sign-up">
                <div class="sign-up-content">
                    <div class="thumbnail-wrapper">
                        <div class="thumbnail">
                            <div id="View_area" class="centered">
                            </div>
                        </div>
                        <div class="filebox">
                            <label for="image">프로필 설정</label>
                            <input type="file" id="image" name="file"
                                   accept="image/*" onchange="previewImage(this, 'View_area');"/>
                            <input type="hidden" name="saveName"/>
                        </div>
                    </div>

                    <input type="text" name="name" id="name" placeholder="이름">
                    <div class="in-line">
                        <input type="text" name="id" id="id" placeholder="아이디">
                        <input type="button" onclick="return idCheck()" value="중복체크">
                    </div>
                    <br>
                    <input type="password" name="password" id="password" placeholder="비밀번호">
                    <input type="password" id="password2" placeholder="비밀번호 확인">
                    <div class="in-line">
                        <input type="email" name="email" id="email" placeholder="이메일">
                        <input type="button" onclick="return emailCheck()" value="인증">
                    </div>
                    <br>
                    <div class="radio-but">
                        <span>성별 선택 &nbsp;</span>
                        <label class="box-radio-input">
                            <input type="radio" name="gender" id="gender" value="m" checked="checked">
                            <span>남자</span>
                        </label>
                        <label class="box-radio-input">
                            <input type="radio" name="gender" id="gender1" value="f">
                            <span>여자</span>
                        </label>
                    </div>
                    <div class="address_select">
                        <span>선호 지역 선택</span> <br>
                        <select name="sido" id="sido"></select>
                        <select name="gugun" id="gugun"></select>
                    </div>
                </div>
            </div>
            <div class="bottom">
                <button id="sign_up_but" type="submit">회원가입</button>
            </div>
        </form>
    </div>
    <div class="footer">
        <img src="/img/footer/footer.png">
    </div>
</div>
</body>

</html>