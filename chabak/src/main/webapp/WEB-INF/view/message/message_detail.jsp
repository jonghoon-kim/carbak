<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <title>Message Home[id:${sessionScope.id}]</title>
</head>
<body>
<div>
    Message Home[id:${sessionScope.id}]
</div>
<div>

    <div class="message">
        <span>보낸사람:${message.sendId}</span>
        <span>제목:${message.title}</span>
        <span>내용:${message.content}</span>
        <button type="button" onclick="location.href='/message/delete?messageNo=${message.messageNo}&messageBox=${messageBox}'">삭제</button>
    </div>
</div>
</body>
</html>
