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
    <script type="text/javascript" src="/js/pwUpdate.js" charset='UTF-8'></script>
    <script>
        $(document).ready(function () {
            var email = opener.document.getElementById("parentEmail").value;
            document.getElementById("email1").value = email;
        });

    </script>
</head>
<body>

<div class="pwUpdate_container">
    <div class="top">
        <h3>아이디 / 비밀번호 찾기</h3>
        <h4>비밀번호 변경</h4>
    </div>
    <div class="content">
        <input type="hidden" name="email" id="email1">
        <input type="password" name="password" id="pw" placeholder="변경 비밀번호"><br>
        <input type="password" name="passwordCheck" id="pwCheck" placeholder="변경 비밀번호 확인">
    </div>
    <div class="bottom">
        <button onclick="return pwCheckValue()">변경</button>
    </div>

</div>

</body>

</html>