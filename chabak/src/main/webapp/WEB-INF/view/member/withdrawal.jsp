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
    <form method="POST" onsubmit="passwordCheck(password)">
        <p class="withdrawal_msg">비밀번호를 입력해주세요</p><BR><BR>
        <input type="password" class="withdrawal_pw" name="password" placeholder="비밀번호"><bR><BR>
        <button type="submit" class="withdrawal"><span>회원탈퇴</span></button> <BR>
    </form>
</div>

</body>
</html>
