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
    <script type="text/javascript" src="/js/common.js" charset='UTF-8'></script>


</head>
<script>


</script>
<body>
    <div id="pop_wrap" class="bg_white pop_addr">
        <div class="pop_header">
            <h1>쪽지 주소록</h1>
        </div>
        <div class="pop_content">
            <div class="addr_list">
                <h3 id="listHeadLine">@@ 검색결과
                    <em class="cnt">1</em>
                </h3>
                <div class="search_bar">
                    <form name="addr_search" action="" onsubmit="">
                        <input type="text" id="addr_search" class="ipt">
                        <input type="submit" value="검색">
                    </form>
                </div>
                <div class="addr_list_cont">
                    <ul id="listUL">
                        <li>user1<input type="checkbox"></li>
                        <li>user2<input type="checkbox"></li>
                    </ul>
                </div>
                <div class="page_ctrl">
<%--                    페이징 영역--%>
                </div>
            </div>
            <div class="addr_toitem">
                <h3>받는 사람
                    <em class="cnt">0</em>
                    / 10
                </h3>
                <div class="addr_list_cont">
                    <ul id="toUL">

                    </ul>
                </div>
                <div class="btn_ctrl">
                    <a href="#" class="addr_remove"><-</a>
                    <a h
                </div>
            </div>
        </div>
    </div>
</body>
</html>
