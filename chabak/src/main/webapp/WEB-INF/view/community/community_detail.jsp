<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/community_detail.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js">
    </script>
    <script>
        $('document').ready(function () {
            initializeForm();
        });

        function like() {
            var img = document.getElementById("like-img");
            img.src = "/img/community/heart2.png"
        }


        //
        function initializeForm() {
            //토글 영역 초기화
            $(".reply-content").css('display', 'block');
            $(".reply-modify-content").css('display', 'none');

            //리리플 입력 폼 초기화(안 보이게)
            $(".re-reply-input").css('display', 'none');

        }
        function myFunction(flag, replyNo) {

            if (flag == 'review') {
                document.getElementById("myDropdown").classList.toggle("show");
            } else {
                document.getElementById("myDropdown" + replyNo).classList.toggle("show");


            }
        }

        window.onclick = function (event) {
            if (!event.target.matches('.dropbtn')) {

                var dropdowns = document.getElementsByClassName("dropdown-content");

                var i;

                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];

                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }


        //리플 수정
        function createModifyReplyForm(replyNo) {

            initializeForm();

            //리플 수정 모드
            $("#defaultReplyContent"+replyNo).css('display', 'none');
            $("#modifyReplyContent"+replyNo).css('display', 'block');
                //리플 수정,삭제 등의 버튼 안 보이게
            // $("#replyModifyBtn"+replyNo).css('display', 'none');
            // $("#replyDeleteBtn"+replyNo).css('display', 'none');
            // $("#replyReReplyBtn"+replyNo).css('display', 'none');
            //리플 등록 취소,등록 버튼 보이게
            // $("#replyCancelBtn"+replyNo).css('display', 'block');
            // $("#replySubmitBtn"+replyNo).css('display', 'block');


            //db에 설정된 $(reply.content}값
            var originalValue = $("#hiddenReplyContent"+replyNo).val()
            //댓글 수정폼의 input 값 재설정

            $("#reply-modify-input"+replyNo).val(originalValue);


        }

        //리플 수정 submit
        function submitModifyReplyForm(replyNo) {
            alert('submitModifyReplyForm')
            $("#modifyReplyForm"+replyNo).submit();

        }

        //대댓글 입력 폼 보이게
        function createReReplyBox(replyNo) {

            initializeForm();

            $("#re-reply-input" + replyNo).css('display', 'block');
        }

        //대댓글 등록
        function submitReReplyForm(replyNo) {
          $("#ReReplyForm"+replyNo).submit();

        }


    </script>

<body>
<!-- header -->
<hr>
<br>
<div class="container">
    <div class="top">
        <h1>커뮤니티 리뷰 상세보기</h1>
    </div>
    <!-- 상세 내용 -->
    <div class="second">
        <div class="profile">
            <div class="thumbnail-wrapper">
                <div class="thumbnail">
                    <div class="centered">
                    </div>
                </div>
            </div>
            <div class="writer">
                    <span class="writer-id">
                        ${review.id}
                    </span>
            </div>
            <div class="title">
                [${review.sido}][${review.gugun}] ${review.title}
            </div>

            <div class="content-icon">
                <span>${review.likeCount}+</span>
                <button class="like-img"><img id="like-img" src="/img/community/heart.png"
                                              onclick="like()"></button>
            </div>
            <div class="dropdown">
                <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                             onclick="myFunction('review',null)"></button>
                <div class="dropdown-content" id="myDropdown">
                    <a href="/review/modify?reviewNo=${review.reviewNo}">수정하기</a>
                    <a href="/review/delete?reviewNo=${review.reviewNo}">삭제하기</a>
                </div>
            </div>
        </div>
    </div>
    <!-- 게시글 내용 -->
    <div class="main">
        <div class="content">
            ${review.content}
        </div>
    </div>
    <!-- 댓글 -->
    <div class="reply">
        <div class="reply-input">
            <form method="POST" action="/reply/writeReply">
                <input type="hidden" name="reviewNo" value="${review.reviewNo}">
                <input type="hidden" name="id">
                <input type="text" placeholder="댓글 입력" name="content">
                <button type="submit">등록</button>
            </form>
        </div>
        <div class="reply-list">
            <c:forEach items="${replyList}" var="reply">
                <c:choose>
                    <c:when test="${reply.lv eq 0}">

                        <%--                 댓글 시작       --%>

                        <div class="reply-one" id="reply-one${reply.replyNo}">
                            <div class="thumbnail-wrapper">
                                <div class="reply-thumbnail">
                                    <div class="centered">
                                    </div>
                                </div>
                            </div>
                            <div class="writer">
                            <span class="writer-id">
                                    ${reply.id}
                            </span>
                            </div>
                            <div class="reply-date">
                                    ${reply.regDate}
                            </div>
                                <%--          댓글 드롭다운 시작--%>

                            <div class="dropdown">
                                <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                                             onclick="myFunction('reply',${reply.replyNo})"></button>
                                <div class="dropdown-content" id="myDropdown${reply.replyNo}">
                                    <a class="dropdown-mode-default" id="replyModifyBtn${reply.replyNo}" onclick="createModifyReplyForm(${reply.replyNo})">수정하기</a>
                                    <a class="dropdown-mode-default" id="replyDeleteBtn${reply.replyNo}" href="/reply/delete?replyNo=${reply.replyNo}">삭제하기</a>
                                    <a class="dropdown-mode-default" id="replyReReplyBtn${reply.replyNo}" onclick="createReReplyBox(${reply.replyNo})">댓글달기</a>

                                </div>
                            </div>
                                <%--                            댓글 드롭다운 끝  --%>


                                <%-- 두 영역이 토글되는 부분 시작                            --%>
                            <div class="reply-content" id="defaultReplyContent${reply.replyNo}" style="display: block">${reply.content}</div>
<%--이 input의 값을 가져와 수정폼의 값으로 넣기                             --%>
                            <input type="hidden" id="hiddenReplyContent${reply.replyNo}" value="${reply.content}">
                            <div class="reply-modify-content" id="modifyReplyContent${reply.replyNo}" style="display: none">
                                <form method="POST" action="/reply/modify" id="modifyReplyForm${reply.replyNo}">

                                    <input type="hidden" name="replyNo" value="${reply.replyNo}">
                                    <input type="hidden" name="id" value="${reply.id}">
                                    <input type="hidden" name="reviewNo" value="${reply.reviewNo}">
                                    <input class="reply-modify-input" id="reply-modify-input${reply.replyNo}" type="text" name="content">


                                </form>
                                <button onclick="submitModifyReplyForm(${reply.replyNo})">등록</button>
                                <button onclick="initializeForm()">취소</button>
                            </div>
                                <%--두 영역이 토글되는 부분 끝--%>


                                <%--          수정 버튼에 따라 변경됨 끝--%>

                        </div>
                        <%--          댓글 끝--%>

                        <%--       대댓글 폼 시작--%>
                        <div class="re-reply-input" id="re-reply-input${reply.replyNo}" style="display:none">
                            <form method="POST" action="/reply/writeReReply" id="ReReplyForm${reply.replyNo}">
                                <input type="hidden" name="reviewNo" value="${reply.reviewNo}">
                                <input type="hidden" name="replyNo" value="${reply.replyNo}">
                                <input type="hidden" name="groupNo" value="${reply.groupNo}">
                                <input type="hidden" name="groupOrder" value="${reply.groupOrder}">
                                <input type="hidden" name="lv" value="${reply.lv}">
                                <input type="hidden" name="id">
                                <input type="text" placeholder="댓글 입력" name="content">

                            </form>
                                <button onclick="submitReReplyForm(${reply.replyNo})">등록</button>
                                <button onclick="initializeForm()">취소</button>


                        </div>
                        <%--       대댓글 폼 끝--%>
                    </c:when>
                    <c:otherwise>


                        <!--대댓글 시작-->
                        <div class="reply-child" id="reply-one${reply.replyNo}">
                            <div class="re-reply">
                                <img src="/img/community/re_reply3.png">
                                <div class="thumbnail-wrapper">
                                    <div class="reply-thumbnail">
                                        <div class="centered">
                                        </div>
                                    </div>
                                </div>
                                <div class="writer">
                    <span class="writer-id">
                            ${reply.id}
                    </span>
                                </div>
                                <div class="reply-date">
                                        ${reply.regDate}
                                </div>

                                    <%--                              대댓글 드롭다운 시작--%>
                                <div class="dropdown">
                                    <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                                                 onclick="myFunction('reply',${reply.replyNo})"></button>
                                    <div class="dropdown-content" id="myDropdown${reply.replyNo}">
                                        <a class="dropdown-mode-default" id="replyModifyBtn${reply.replyNo}" onclick="createModifyReplyForm(${reply.replyNo})">수정하기</a>
                                        <a class="dropdown-mode-default" id="replyDeleteBtn${reply.replyNo}" href="/reply/delete?replyNo=${reply.replyNo}">삭제하기</a>
                                        <a class="dropdown-mode-default" id="replyReReplyBtn${reply.replyNo}" onclick="createReReplyBox(${reply.replyNo})">댓글달기</a>

                                    </div>
                                </div>
                                    <%--             대댓글 드롭다운 끝          --%>


                                    <%-- 두 영역이 토글되는 부분 시작                            --%>
                                <div class="reply-content" id="defaultReplyContent${reply.replyNo}" style="display: block">${reply.content}</div>
                                    <%--이 input의 값을 가져와 수정폼의 값으로 넣기                             --%>
                                <input type="hidden" id="hiddenReplyContent${reply.replyNo}" value="${reply.content}">
                                <div class="reply-modify-content" id="modifyReplyContent${reply.replyNo}" style="display: none">
                                    <form method="POST" action="/reply/modify" id="modifyReplyForm${reply.replyNo}">

                                        <input type="hidden" name="replyNo" value="${reply.replyNo}">
                                        <input type="hidden" name="id" value="${reply.id}">
                                        <input type="hidden" name="reviewNo" value="${reply.reviewNo}">
                                        <input class="reply-modify-input" id="reply-modify-input${reply.replyNo}" type="text" name="content">


                                    </form>
                                    <button onclick="submitModifyReplyForm(${reply.replyNo})">등록</button>
                                    <button onclick="initializeForm()">취소</button>
                                </div>
                                    <%--두 영역이 토글되는 부분 끝--%>


                        </div>


                        <%--       대댓글 폼 시작--%>
                        <div class="re-reply-input" id="re-reply-input${reply.replyNo}" style="display:none">
                            <form method="POST" action="/reply/writeReReply" id="ReReplyForm${reply.replyNo}">
                                <input type="hidden" name="reviewNo" value="${reply.reviewNo}">
                                <input type="hidden" name="replyNo" value="${reply.replyNo}">
                                <input type="hidden" name="groupNo" value="${reply.groupNo}">
                                <input type="hidden" name="groupOrder" value="${reply.groupOrder}">
                                <input type="hidden" name="lv" value="${reply.lv}">
                                <input type="hidden" name="id">
                                <input type="text" placeholder="댓글 입력" name="content">

                            </form>
                            <button onclick="submitReReplyForm(${reply.replyNo})">등록</button>
                            <button onclick="initializeForm()">취소</button>


                        </div>
                        <%--       대댓글 폼 끝--%>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </div>


    </div>
</div>
</div>

</div>
</div>
</body>

</html>