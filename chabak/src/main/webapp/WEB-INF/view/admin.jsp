<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script type="text/javascript" src="js/adminPaging.js"></script>
    <link href="css/admin.css" rel="stylesheet">
</head>
<body>
<!-- header -->
<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr"><br>
<br>
<h2>회원 정보</h2>

<div class="admin_aticle">
    <table class="admin_table">
        <tr>
            <th>ID</th>
            <th>이름</th>
            <th>성별</th>
            <th>도/시</th>
            <th>구/군</th>
            <th>E-MAIL</th>
            <th>날짜</th>
            <th>DELETE</th>
        </tr>
        <c:set var="tie" value="${adminwBoolean}"/>
        <c:set var="lstSize" value="${lstSize}"/>
        <c:set var="adlstSize" value="${9-lstSize}"/>
        <c:forEach var="i" begin="0" end="${9-lstSize}" step="1">
            <c:choose>
                <c:when test="${tie eq 'true'}">
                    <c:forEach var="memberlist" items="${adminMemberlist}" begin="0" end="${lstSize}">
                        <form id="adminMemberDelete" name="adminMemberDelete" method="post">
                            <tr class="admin_tr">
                                <td width="14%">${memberlist.id}</td>
                                <td width="12%">${memberlist.name}</td>
                                <td width="10%">${memberlist.gender}</td>
                                <td width="8%">${memberlist.sido}</td>
                                <td width="8%">${memberlist.gugun}</td>
                                <td width="22%">${memberlist.email}</td>
                                <td width="12%">${memberlist.regDate}</td>
                                <td width="14%"><button onClick="javascript:adminMemberDel('${memberlist.id}')">삭제</button></td>
                            </tr>
                            <input type="hidden" id="deleteId" name="deleteId">
                        </form>
                    </c:forEach>
                    <c:set var="tie" value="false"/>
                </c:when>
                <c:otherwise>
                        <tr class="admin_tr">
                            <td width="14%">&nbsp;</td>
                            <td width="12%">&nbsp;</td>
                            <td width="10%">&nbsp;</td>
                            <td width="8%">&nbsp;</td>
                            <td width="8%">&nbsp;</td>
                            <td width="22%">&nbsp;</td>
                            <td width="12%">&nbsp;</td>
                            <td width="14%">&nbsp;</td>
                        </tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </table>
    <c:choose>
        <c:when test="${tie ne 'false'}">
        <!-- 페이지 버튼 -->
        <div class="admin_link">
            <button class='fas fa-angle-left' onClick="javascript:adminPage(${adminPaging.grStartPageNo})"></button>

            <c:forEach var="i" begin="${adminPaging.startPageNo}" end="${adminPaging.endPageNo}" step="1">
                <c:choose>
                    <c:when test="${i eq adminPaging.pageNo}">
                        <button class='fas fa-circle' onClick="javascript:adminPage(${i})">${i}</button>
                    </c:when>
                    <c:otherwise>
                        <button class='far fa-circle' onClick="javascript:adminPage(${i})">${i}</button>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <button class='fas fa-angle-right' onClick="javascript:adminPage(${adminPaging.pageNo}+1)"></button>
        </div>
        </c:when>
        <c:otherwise>
            <!-- 페이지 버튼 -->
            <div class="admin_link">
                <button class='fas fa-angle-left' onClick="javascript:adminPage(1)"></button>

                <button class='fas fa-circle' onClick="javascript:adminPage(1)">1</button>

                <button class='fas fa-angle-right' onClick="javascript:adminPage(1)"></button>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<div class="footer">
    <img src="img/footer/footer.png">
</div>
</body>
</html>
