<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <title>회원탈퇴</title>
</head>
<body>
<br>
<div class="withdrawal">
    <form method="POST">
        <p class="withdrawal_msg">비밀번호를 입력해주세요</p><BR><BR>
        <input type="password" class="withdrawal_pw" name="password" placeholder="비밀번호"><BR><BR>
        <input type="button" onclick="passwordCheck()" value="회원탈퇴"><BR>
        <button type="button" class="withdrawal" onclick="passwordCheck()"><span>회원탈퇴</span></button> <BR>
    </form>
</div>

<%--
<form method="post">
    <div class="in-line">
        <input type="text" name="code" id="code" placeholder="인증 코드">
        <input type="hidden" name="dice" id="dice" value="${dice}">
        <input type="button" onclick="email_certify()" value="인증">
    </div>
</form>
--%>

<script type="text/javascript" src="/js/withdrawal.js" charset='UTF-8'></script>
</body>
</html>
