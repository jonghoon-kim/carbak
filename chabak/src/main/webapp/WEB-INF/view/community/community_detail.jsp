<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/community_detail.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        function like() {
            var img = document.getElementById("like-img");
            img.src = "/img/community/heart2.png"
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
                        je309
                    </span>
            </div>
            <div class="title">
                [강원도][동해시] 망상 오토 캠핑장
            </div>
            <div class="content-icon">
                <span>999+</span>
                <button class="like-img"><img id="like-img" src="/img/community/heart.png"
                                              onclick="like()"></button>
            </div>
        </div>
    </div>
    <!-- 게시글 내용 -->
    <div class="main">
        <div class="content">

        </div>
    </div>

    <div class="reply-input">
        <input type="text" placeholder="댓글 입력">
        <button type="submit">등록</button>
    </div>

    <!-- 댓글 -->
    <div class="reply">
        <div class="reply-list">
            <div class="thumbnail-wrapper">
                <div class="reply-thumbnail">
                    <div class="centered">
                    </div>
                </div>
            </div>
            <div class="writer">
                    <span class="writer-id">
                        je309
                    </span>
            </div>
            <div class="reply-date">
                댓글 단 날짜
            </div>
            <div class="button">
                <button class="update">수정</button>
                <button class="delete">삭제</button>
            </div>
            <div class="reply-content">
                와! <br>
                너무 좋아요!
            </div>

            <!--대댓글 생성시-->
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
                        je309
                    </span>
                </div>
                <div class="reply-date">
                    댓글 단 날짜
                </div>
                <div class="button">
                    <button class="update">수정</button>
                    <button class="delete">삭제</button>
                </div>
                <div class="re-reply-content">
                    대박<br>
                    대박<br>
                </div>
            </div>
            <!--대댓글 생성시-->
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
                        je309
                    </span>
                </div>
                <div class="reply-date">
                    댓글 단 날짜
                </div>
                <div class="button">
                    <button class="update">수정</button>
                    <button class="delete">삭제</button>
                </div>
                <div class="re-reply-content">
                    대박<br>
                    대박<br>
                </div>
            </div>
            <!--대댓글 생성시-->
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
                        je309
                    </span>
                </div>
                <div class="reply-date">
                    댓글 단 날짜
                </div>
                <div class="button">
                    <button class="update">수정</button>
                    <button class="delete">삭제</button>
                </div>
                <div class="re-reply-content">
                    대박<br>
                    대박<br>
                </div>
            </div>
        </div>


    </div>

    <!-- 댓글 -->
    <div class="reply">
        <div class="reply-list">
            <div class="thumbnail-wrapper">
                <div class="reply-thumbnail">
                    <div class="centered">
                    </div>
                </div>
            </div>
            <div class="writer">
                    <span class="writer-id">
                        je309
                    </span>
            </div>
            <div class="reply-date">
                댓글 단 날짜
            </div>
            <div class="button">
                <button class="update">수정</button>
                <button class="delete">삭제</button>
            </div>
            <div class="reply-content">
                와! <br>
                너무 좋아요!
            </div>

            <!--대댓글 생성시-->
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
                        je309
                    </span>
                </div>
                <div class="reply-date">
                    댓글 단 날짜
                </div>
                <div class="button">
                    <button class="update">수정</button>
                    <button class="delete">삭제</button>
                </div>
                <div class="re-reply-content">
                    대박<br>
                    대박<br>
                </div>
                <div class="re-reply-content">
                    대박<br>
                    대박<br>
                </div>
            </div>
        </div>


    </div>
</div>
</div>

</div>
</div>
</body>

</html>