<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Message Home[id:${sessionScope.id}]</title>
<script>
    function openWinMessageWrite(){
        window.open("/message/write", "쪽지 작성", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
    }
</script>
</head>
<body>

<%--<div id="header">--%>
<%--    <jsp:include page="/header"/>--%>
<%--</div>--%>
<%--<hr class="top_hr"><br>--%>
<%--<br>--%>

<div>
    Message Home[id:${sessionScope.id}]
</div>
<div>
    <div>
        <button type="button"><a onclick="openWinMessageWrite()">쪽지 쓰기</a> </button>
        <button type="button"><a href="/message/list?messageBox=send">보낸 쪽지함</a> </button>
        <button type="button"><a href="/message/list?messageBox=receive">받은 쪽지함</a> </button>
    </div>

    <c:choose>
        <c:when test='${messageBox eq "receive"}'>
            <span>받은 쪽지함</span>
        </c:when>
        <c:otherwise>
            <span>보낸 쪽지함</span>
        </c:otherwise>
    </c:choose>
    <div class="messageList">
        <c:forEach var="message" items="${messageList}">
            <div class="message">
                <span>보낸사람:${message.sendId}</span>
                <span>제목:${message.title}</span>
                <span>내용:<a href="/message/detail?messageNo=${message.messageNo}&messageBox=${messageBox}">${message.content}</a></span>
                <button type="button" onclick="location.href='/message/delete?messageNo=${message.messageNo}&messageBox=${messageBox}'">삭제</button>


            </div>
        </c:forEach>
    </div>
</div>
<div class="footer">
    <img src="/img/footer/footer.png">
</div>
</body>
</html>
