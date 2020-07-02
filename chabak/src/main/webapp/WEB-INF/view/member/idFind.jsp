<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/idpw_find.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/idpw_find.js" charset='UTF-8'></script>
    <script>
        $(document).ready(function () {
            var idVar = opener.document.getElementById("parentId").value;
            var nameVar = opener.document.getElementById("parentName").value;
            document.getElementById("idSpan").innerText = idVar;
            document.getElementById("nameSpan").innerText = nameVar;
        });

    </script>
<body>

<div class="idFind_container">
    <div class="top">
        <h3>아이디 / 비밀번호 찾기</h3>
    </div>
    <div class="content">
    <form name="idFindForm">
        <span id="nameSpan"></span>님의 아이디<br><br>
        <span id="idSpan"></span>
    </form>
    </div>
    <div class="bottom">
        <button onclick="window.open('_self').close(); window.opener.location='/member/login'">로그인 하러 가기</button>
    </div>
</div>
>
</body>

</html>