<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/login.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
<body>
<!-- header -->
<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr"><br>
<br>
<div class="container">
    <div class = "top">
        <h1>로그인</h1>
    </div>
    <div class="login">
        <form method="POST" action="/member/loginAction">
            <input type="text" class="login_id" name="id" placeholder="ID"> <BR><BR>
            <input type="password" class="login_pw" name="password" placeholder="PASSWORD"><bR><BR>
            <button type="submit" class="login_but"><span>LOGIN</span></button> <BR>
        </form>
    </div>
    <div class="bottom">
        <button id="id_pw_find_but" onclick="location.href='/member/idpw_find'">아이디/비밀번호 찾기</button>
        <button id="sign_up_but" onclick="location.href='/member/signup'">회원가입</button>
    </div>
</div>

<div class="footer">
    <img src="/img/footer/footer.png">
</div>
</body>

</html>