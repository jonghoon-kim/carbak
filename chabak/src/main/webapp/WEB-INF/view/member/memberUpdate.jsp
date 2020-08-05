<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/memberUpdate.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/memberUpdate.js" charset='UTF-8'></script>
    <!-- 프로필 사진 미리 보기 -->
    <script type="text/javascript" src="/js/profile_preview.js" charset='UTF-8'></script>
    <!-- 아이디 및 이메일 중복 체크, 유효성 검사 -->
    <script type="text/javascript" src="/js/address_update.js" charset="UTF-8"></script>
    <script type="text/javascript" src="/js/memberUpdate.js" charset='UTF-8'></script>
    <script type="text/javascript" src="/js/withdrawal.js" charset='UTF-8'></script>
</head>
<body>
<!-- header -->
<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr">
<br>
<div class="container">
    <div class="top">
        <h1>회원정보 수정</h1>
    </div>

    <form class="form" method="post" onsubmit="return checkValue()"
          enctype="multipart/form-data">
        <div class="sign-up">
            <div class="sign-up-content">
                <div class="thumbnail-wrapper">
                    <div class="thumbnail">
                        <div id="View_area" class="centered">
                            <img src="${member.savePath}${member.saveName}">
                        </div>
                    </div>
                    <input type="hidden" name="savePath" value="${member.savePath}">
                    <input type="hidden" name="saveName" value="${member.saveName}">
                    <div class="filebox">
                        <label for="image">프로필 설정</label>
                        <input type="file" id="image" name="file"
                               accept="image/*" onchange="previewImage(this, 'View_area');"/>
                    </div>
                </div>

                <input type="text" name="name" id="name" placeholder="이름" value="${member.name}">
                <input type="text" id="id" placeholder="아이디" value="${member.id}" readonly>
                <br>
                <input type="password" name="password" id="password" placeholder="비밀번호" value="${member.password}">
                <input type="password" id="password2" placeholder="비밀번호 확인">
                <input type="email" name="email" id="email" placeholder="이메일" value="${member.email}" readonly>
                <br>
                <div class="radio-but">
                    <span>성별 선택 &nbsp;</span>
                    <c:choose>
                        <c:when test="${member.gender}==m">
                            <label class="box-radio-input">
                                <input type="radio" name="gender" value="m" checked="checked">
                                <span>남자</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="gender" value="f">
                                <span>여자</span>
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label class="box-radio-input">
                                <input type="radio" name="gender" value="m">
                                <span>남자</span>
                            </label>
                            <label class="box-radio-input">
                                <input type="radio" name="gender" value="f" checked="checked">
                                <span>여자</span>
                            </label>
                        </c:otherwise>
                    </c:choose>

                </div>

                <c:choose>
                    <c:when test="${empty member.gugun}">
                        <input type="hidden" id="selectSido" value="${member.sido}">
                        <input type="hidden" id="selectGugun" value="구/군 선택">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" id="selectSido" value="${member.sido}">
                        <input type="hidden" id="selectGugun" value="${member.gugun}">
                    </c:otherwise>
                </c:choose>

                <div class="address_select">
                    <span>선호 지역 재선택</span> <br>
                    <select name="sido" id="sido1"></select>
                    <select name="gugun" id="gugun1"></select>
                </div>
            </div>
        </div>
        <div class="bottom">
            <button id="update_but" type="submit">정보 수정 완료</button>
        </div>
    </form>
    <div class="moveWithdrawal">
        <button class="btn_withdrawal" onclick="window.open('withdrawal','_blank', 'top=200, left=500, width=400, height=300, scrollbars = yes, resizable=yes')">회원탈퇴</button>
    </div>
</div>
<div class="footer">
    <img src="/img/footer/footer.png">
</div>

</body>
</html>