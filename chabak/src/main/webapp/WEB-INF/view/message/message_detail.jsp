<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>슬기로운 차박생활</title>
    <link href="/css/message_detail.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/message.js" charset='UTF-8'></script>

</head>
<body>

<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr"><br>
<br>
<div class="container">
    <div class="top">
        <div class="thumbnail-wrapper">
            <div class="thumbnail">
                <div id="View_area" class="centered">
                    <img src="${member.savePath}${member.saveName}">
                </div>
            </div>
        </div>
        <div class="pageOwnerId">
            ${sessionScope.name}님의 쪽지함
        </div>

    </div>
    <div class="button_menu">
        <ul class="writeMenu">
            <li class="writeMessgae" onclick="openWinMessageWrite(null,'${sessionScope.id}')">쪽지쓰기</li>
            <li class="" onclick="openWinMessageWrite('${sessionScope.id}','${sessionScope.id}')">내게쓰기</li>
        </ul>
        <ul class="sideMenu">
            <li class="receiveMessageBox" onclick="location.href='/message/list?messageBox=receive'">받은 쪽지함</li>
            <li class="sendMessageBox" onclick="location.href='/message/list?messageBox=send'">보낸 쪽지함</li>
            <li class="toMeMessageBox" onclick="location.href='/message/list?messageBox=toMe'">내게 쓴 쪽지함</li>
            <li class="updateMember" onclick="location.href='/mypage/myInfo'">마이페이지</li>
        </ul>
    </div>
    <div class="content">
        <div class="title">
            <h1>상세 보기</h1>
        </div>
        <div class="listForm">
            <div class="message">
                <table>
                    <tr>
                        <th class="sendId">보낸 사람</th>
                        <td> ${message.sendId}</td>
                        <th class="regDate">날짜</th>
                        <td>${message.regDate}</td>
                    </tr>
                    <tr>
                        <th class="title">제목</th>
                        <td colspan="3">${message.title}</td>
                    </tr>
                    <tr>
                        <th class="content">내용</th>
                        <td colspan="3" class="contentTd">${message.content}</td>
                    </tr>
                </table>

            </div>
        </div>
    </div>
</div>
<div class="footer">
    <img src="/img/footer/footer.png">
</div>
</body>
</html>
