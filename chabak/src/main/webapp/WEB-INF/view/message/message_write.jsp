<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>쪽지 보내기</title>
    <link href="/css/message_write.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<script>

    function beforeSubmit() {
        alert("쪽지가 작성되었습니다.");
        return true;
    }
    //스크립트로 폼전송 후 새창을 종료시키려 해도 동작하지 않음(새창 종료시 값이 전송 안됨)
    function submitFunction() {
        //var form = $("#writeMessageForm");
        //JQuery로 하니 안되는 듯?
        alert("쪽지가 작성되었습니다.");
        document.getElementById("writeMessageForm").submit();
    }
</script>
<body>
<div class="container">
    <div class="top">
        <h1>쪽지 쓰기</h1>
    </div>
    <div>
        <form action ="/message/write" method="POST" id="writeMessageForm" onsubmit="return beforeSubmit();">
            <input type="text" name="receiveId" placeholder="보낼 아이디를 입력하세요." value="${receiveId}">
            <input type="text" name="title" placeholder="제목을 입력하세요.">
            <textarea name="content" placeholder="내용을 입력하세요."></textarea>
            <input type="submit" value="작성완료">
        </form>
    </div>
</div>
</body>
</html>
