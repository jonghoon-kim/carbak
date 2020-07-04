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
    <script type="text/javascript" src="/js/email_certify.js" charset='UTF-8'></script>
</head>
<body>
<div id="wrap">
    <div class="container">
        <div class="top">
            <h3>이메일 인증 코드</h3>
        </div>

        <form method="post">
            <div class="in-line">
                <input type="text" name="code" id="code" placeholder="인증 코드">
                <input type="hidden" name="dice" id="dice" value="${dice}">
                <input type="button" onclick="email_certify()" value="인증">
            </div>
        </form>
        <br>

    </div>
</body>

