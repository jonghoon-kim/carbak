<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>
<%--<%--%>
<%--    String userId;--%>
<%--    if (session.getAttribute("id")!=null){--%>
<%--        userId=(String)session.getAttribute("id");--%>
<%--    }else{--%>
<%--        userId="세션 값 없음.";--%>
<%--    }--%>
<%--%>--%>
<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/community_detail.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/reviewScript.js" charset='UTF-8'></script>
    <script type="text/javascript">
        //로그인 아이디 저장 변수

        $(document).ready(function () {

            initializeForm();
            // $("#focusId").focus();

        });
        $(window).bind("pageshow", function(event) {
            if ( event.originalEvent && event.originalEvent.persisted) {// BFCahe
                console.log("back");
                window.location.reload();
            }else{}//새로운페이지

        });


        function initializeForm() {
            console.log("initializeForm()")
            //토글 영역 초기화
            $(".reply-content").css('display', 'block');
            $(".reply-modify-content").css('display', 'none');

            //리리플 입력 폼 초기화(값 초기화,안 보이게)
            var rereplyInput = $(".re-reply-input");//리리플 폼 div
            //div 내부의 입력창 값 초기화
            rereplyInput.find('input[type="text"]').val("");
            rereplyInput.css('display', 'none');

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

        //리플 삭제시 하위 댓글 존재 확인(있으면 삭제 불가능)
        function checkChildReplyAjax(replyNo) {
            $.ajax({
                url : "/reply/checkChildReply",
                type : "post",
                data :{"replyNo": replyNo  },
                success : function(data) {

                    if(data > 0){
                        alert("하위 댓글이 존재할 경우 삭제할 수 없습니다.");
                    }
                    else{
                        location.href="/reply/delete?replyNo="+replyNo;
                    }

                },
                error:function(error){
                    alert('error');
                }
            });  // ajax 끝
        }


        //리플 수정
        function createModifyReplyForm(replyNo) {

            initializeForm();

            //리플 수정 모드
            $("#defaultReplyContent"+replyNo).css('display', 'none');
            $("#modifyReplyContent"+replyNo).css('display', 'block');


            //db에 설정된 $(reply.content}값
            var originalValue = $("#hiddenReplyContent"+replyNo).val()
            //댓글 수정폼의 input 값 재설정

            $("#reply-modify-input"+replyNo).val(originalValue);


        }

        //리플 수정 submit
        function submitModifyReplyForm(replyNo) {
            $("#modifyReplyForm"+replyNo).submit();

        }

        //대댓글 입력 폼 보이게
        function createReReplyBox(replyNo,sessionId) {
            if(sessionId !=null && sessionId!=""){
                initializeForm();

                $("#re-reply-input" + replyNo).css('display', 'block');
            }
            else{
                askLogin();
            }
        }

        //대댓글 등록
        function submitReReplyForm(replyNo) {
            $("#ReReplyForm"+replyNo).submit();

        }
        //Controller를
        //로그인 할지 물어보고 ok이면 로그인 페이지로 이동
        function askLogin(){
            var confirmYn = confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?") ;
            if(confirmYn)
                location.href="/member/login";
       }

        function ajaxReviewLikeToggle(reviewNo,imgTag,sessionId){

            if(sessionId == "" || sessionId==null){

                askLogin();
            }
            else{

                $.ajax({
                    url:"/reviewLike/toggleAjax",
                    type : "post",
                    data :{"reviewNo": reviewNo},
                    success : function(data) {
                        if(data==1){
                            $(imgTag).attr("src","/img/community/heart2.png");
                        }
                        else{
                            $(imgTag).attr("src","/img/community/heart.png");
                        }
                    },
                    error:function(error){
                        alert(error)

                    }
                });
            }
        }
    </script>

<body>
<!-- header -->
<hr><br>
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
                <button class="like-img">

                    <c:choose>
                        <c:when test="${sessionScope.id != null and likeYn==1}">
                            <img id="like-img" src="/img/community/heart2.png" onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')">
                        </c:when>
                        <c:otherwise>
                            <img id="like-img" src="/img/community/heart.png" onclick="ajaxReviewLikeToggle('${review.reviewNo}',this,'${sessionScope.id}')">
                        </c:otherwise>

                    </c:choose>

                </button>
            </div>

            <div class="dropdown">
                <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                             onclick="myFunction('review',null)"></button>
                <div class="dropdown-content" id="myDropdown">
                <c:if test="${sessionScope.id != null and sessionScope.id !='' and sessionScope.id == review.id}">
                    <a href="/review/modify?reviewNo=${review.reviewNo}">수정하기</a>
                    <a href="/review/delete?reviewNo=${review.reviewNo}">삭제하기</a>
                </c:if>
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

    <div class="reply-input">
        <form method="POST" action="/reply/writeReply">
            <input type="hidden" name="reviewNo" value="${review.reviewNo}">
            <input type="hidden" name="id">
            <input type="text" placeholder="댓글 입력" name="content" onkeyup="checkLengthValidate(this,100)" id="focusId">
            <button type="submit">등록</button>
        </form>
    </div>

    <c:forEach var="list" items="${replyList}">
        <c:if test="${list.groupOrder eq 0}">
    <!-- 댓글 -->
    <div class="reply" id="reply">
        <div class="reply-list">


            <div class="thumbnail-wrapper">
                <div class="reply-thumbnail">
                    <div class="centered">
                    </div>
                </div>
            </div>
            <div class="writer">
               <span class="writer-id">
                ${list.id}
               </span>
            </div>
            <div class="reply-date">
                ${list.regDate}
            </div>

            <div class="dropdown">
                <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                             onclick="myFunction('reply',${list.replyNo})"></button>
                <div class="dropdown-content" id="myDropdown${list.replyNo}">

                <c:if test="${sessionScope.id != null and sessionScope.id !='' and sessionScope.id == list.id}">
                    <a onclick="createModifyReplyForm(${list.replyNo})">수정하기</a>
                    <a onclick="checkChildReplyAjax(${list.replyNo})">삭제하기</a>
                </c:if>
                    <a onclick="createReReplyBox(${list.replyNo},'${sessionScope.id}')">댓글달기</a>

                </div>
            </div>


                <%-- 두 영역이 토글되는 부분 시작                            --%>
            <div class="reply-content" id="defaultReplyContent${list.replyNo}">${list.content}</div>
                <%--이 input의 값을 가져와 수정폼의 값으로 넣기                             --%>
            <input type="hidden" id="hiddenReplyContent${list.replyNo}" value="${list.content}">
            <div class="reply-modify-content" id="modifyReplyContent${list.replyNo}" style="display: none">
                <form method="POST" action="/reply/modify" id="modifyReplyForm${list.replyNo}">

                    <input type="hidden" name="replyNo" value="${list.replyNo}">
                    <input type="hidden" name="id" value="${list.id}">
                    <input type="hidden" name="reviewNo" value="${list.reviewNo}">
                    <input class="reply-modify-input" id="reply-modify-input${list.replyNo}" type="text" name="content"  onkeyup="checkLengthValidate(this,100)">


                </form>
                <button onclick="submitModifyReplyForm(${list.replyNo})">등록</button>
                <button onclick="initializeForm()">취소</button>
            </div>
                <%--두 영역이 토글되는 부분 끝--%>

                <%--       대댓글 폼 시작--%>
            <div class="re-reply-input" id="re-reply-input${list.replyNo}" style="display:none">
                <form method="POST" action="/reply/writeReReply" id="ReReplyForm${list.replyNo}">
                    <input type="hidden" name="reviewNo" value="${list.reviewNo}">
                    <input type="hidden" name="replyNo" value="${list.replyNo}">
                    <input type="hidden" name="groupNo" value="${list.groupNo}">
                    <input type="hidden" name="groupOrder" value="${list.groupOrder}">
                    <input type="hidden" name="lv" value="${list.lv}">
                    <input type="hidden" name="id">
                    <input type="hidden" name="parentId" value="${list.id}">
                    <input type="hidden" name="parentReplyNo" value="${list.replyNo}">
                    <input type="text" placeholder="댓글 입력" name="content"  onkeyup="checkLengthValidate(this,100)">

                </form>
                <button onclick="submitReReplyForm(${list.replyNo})">등록</button>
                <button onclick="initializeForm()">취소</button>


            </div>
                <%--       대댓글 폼 끝--%>

            <!--대댓글 생성시-->

            <c:forEach var="relist" items="${replyList}">

                <c:if test="${relist.groupOrder ne 0 && relist.groupNo eq list.groupNo}">

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
                        ${relist.id}
                    </span>
                </div>
                <div class="reply-date">
                    ${relist.regDate}
                </div>

                <div class="dropdown">
                    <button class="dropbtn"><img class="dropbtn" src="/img/community/menu.png"
                                                 onclick="myFunction('reply',${relist.replyNo})"></button>
                    <div class="dropdown-content" id="myDropdown${relist.replyNo}">

                        <c:if test="${sessionScope.id != null and sessionScope.id !='' and sessionScope.id == relist.id}">
                        <a onclick="createModifyReplyForm(${relist.replyNo})">수정하기</a>
                        <a onclick="checkChildReplyAjax(${relist.replyNo})">삭제하기</a>
                </c:if>
                        <a onclick="createReReplyBox(${relist.replyNo},'${sessionScope.id}')">댓글달기</a>

                    </div>
                </div>


                    <%-- 두 영역이 토글되는 부분 시작                            --%>
                <div class="reply-content" id="defaultReplyContent${relist.replyNo}"><span class="reply-parent-id"><a href="#">${relist.parentId}</a></span>${relist.content}</div>
                    <%--이 input의 값을 가져와 수정폼의 값으로 넣기                             --%>
                <input type="hidden" id="hiddenReplyContent${relist.replyNo}" value="${relist.content}">
                <div class="reply-modify-content" id="modifyReplyContent${relist.replyNo}" style="display: none">
                    <form method="POST" action="/reply/modify" id="modifyReplyForm${relist.replyNo}">

                        <input type="hidden" name="replyNo" value="${relist.replyNo}">
                        <input type="hidden" name="id" value="${relist.id}">
                        <input type="hidden" name="reviewNo" value="${relist.reviewNo}">
                        <input class="reply-modify-input" id="reply-modify-input${relist.replyNo}" type="text" name="content" onkeyup="checkLengthValidate(this,100)">


                    </form>
                    <button onclick="submitModifyReplyForm(${relist.replyNo})">등록</button>
                    <button onclick="initializeForm()">취소</button>
                </div>
                    <%--두 영역이 토글되는 부분 끝--%>
                    <%--       대댓글 폼 시작--%>
                <div class="re-reply-input" id="re-reply-input${relist.replyNo}" style="display:none">
                    <form method="POST" action="/reply/writeReReply" id="ReReplyForm${relist.replyNo}">
                        <input type="hidden" name="reviewNo" value="${relist.reviewNo}">
                        <input type="hidden" name="replyNo" value="${relist.replyNo}">
                        <input type="hidden" name="groupNo" value="${relist.groupNo}">
                        <input type="hidden" name="groupOrder" value="${relist.groupOrder}">
                        <input type="hidden" name="lv" value="${relist.lv}">
                        <input type="hidden" name="id">
                        <input type="hidden" name="parentId" value="${relist.id}">
                        <input type="hidden" name="parentReplyNo" value="${relist.replyNo}">
                        <input type="text" placeholder="댓글 입력" name="content"  onkeyup="checkLengthValidate(this,100)">

                    </form>
                    <button onclick="submitReReplyForm(${relist.replyNo})">등록</button>
                    <button onclick="initializeForm()">취소</button>


                </div>
                    <%--       대댓글 폼 끝--%>

            </div>

                </c:if>
            </c:forEach>



        </div>


    </div>

    </c:if>
    </c:forEach>




</div>
</div>

</div>
</div>
</body>

</html>