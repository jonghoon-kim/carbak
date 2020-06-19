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
</head>
<body>
<div id="wrap">
    <div class="container">
        <div class="top">
            <h3>이메일 인증</h3>
        </div>
        <div class="in-line">
            <input type="email" name="email" id="email" placeholder="이메일">
            <input type="button" onclick="return emailCheck()" value="인증">
        </div>
        <br>

    </div>
</body>

