<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Message Home[id:${sessionScope.id}]</title>
    <link href="/css/message.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        function openWinMessageWrite(){
            window.open("/message/write", "쪽지 작성", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=no" );
        }
    </script>
</head>
<body>

<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr"><br>
<br>
<div class="container">
    <div class="top">
        <!-- 클릭 시 프로필 수정 페이지 이동-->
        <div class="thumbnail-wrapper">
            <div class="thumbnail">
                <div id="View_area" class="centered">
                    <!-- 방문 id가 세션 id랑 비교해서 같으면 나의 프로필 사진을 / 아니면 click id 프로필 선택 -->
                    <img src="${member.savePath}${member.saveName}">
                </div>
            </div>
        </div>
        <div class="button_menu">
            <div class="sessionId">
                <!-- 방문 id가 세션 id랑 비교해서 같으면 나의 id를 / 다르면 click id를 선택 -->
                <div class="pageOwnerId">${member.id}</div>
                <button class="updateMember" id="btn_updateMember" style="display: inline;"
                        onclick="location.href='/member/memberUpdate?id=${sessionScope.id}';">회원정보수정</button>
            </div>

            <div class="menu_btn">
                <div class="countMenu" style="color: transparent;">1</div>
                <button type="button" class="writeMessage" onclick="openWinMessageWrite()">쪽지쓰기</button>
            </div>
<%--            ${sendCount}--%>
            <div class="menu_btn">
                <div class="countMenu">${sendCount}</div>
                <button type="button" class="sendMessageBox"
                        onclick="location.href='/message/list?messageBox=send'">보낸쪽지함</button>
            </div>

            <div class="menu_btn">
                <div class="countMenu">${receiveCount}</div>
                <button type="button" class="receiveMessageBox"
                        onclick="location.href='/message/list?messageBox=receive'">받은쪽지함</button>
            </div>
            <div class="menu_btn">
                <div class="countMenu">${toMeCount}</div>
                <button type="button" class="toMeMessageBox"
                        onclick="location.href='/message/list?messageBox=toMe'">내게쓴쪽지함</button>
            </div>
        </div>
    </div>

    <div class="content">
        <div class="">
        <c:choose>
            <c:when test="${messageBox eq 'send'}"><span>보낸메시지함</span></c:when>
            <c:when test="${messageBox eq 'receive'}"><span>받은메시지함</span></c:when>
            <c:otherwise><span>내게쓴메시지함</span></c:otherwise>
        </c:choose>
        </div>
        <div class="listForm">
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


</div>
<div class="footer">
    <img src="/img/footer/footer.png">
</div>
</body>
</html>
