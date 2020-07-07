<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <title>슬기로운 차박생활</title>
    <link href="/css/community_write.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/smarteditor2/js/HuskyEZCreator.js"></script>
    <script type="text/javascript" src="/js/address_select.js" charset='UTF-8'></script>
    <script type="text/javascript" src="/js/reviewScript.js" charset='UTF-8'></script>

<body>
<!-- header -->
<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr">
<br><br>
<div class="container">
    <div class="top">
        <h1>커뮤니티 리뷰 쓰기</h1>
    </div>
    <form method="POST" action="/review/write" onsubmit="return checkReviewValidate()">
    <div class="second">


        <div class="set">

            <span class="title">제목</span><br>
            <input type="text" class="content" id="title" name="title" onkeyup="checkLengthValidate(this, 40)">



        </div>
        <div class="set">
            <span class="title">리뷰 지역???</span>
            <span class="title" style="color: red;">(필수 *)</span>
            <select class="content" name="sido" id="sido"></select>
            <select class="content" name="gugun" id="gugun"></select>
        </div>
        <div class="set">
            <span class="title">본문</span><br><br>
            <!-- editor -->
            <textarea class="form-control" id="smartEditor" name="content" rows="30" cols="110"
                      placeholder="리뷰를 작성해주세요!">
            </textarea>
            <script type="text/javascript">
                var oEditors = []; nhn.husky.EZCreator.createInIFrame({
                    oAppRef: oEditors,
                    elPlaceHolder: "smartEditor", //저는 textarea의 id와 똑같이 적어줬습니다.
                    sSkinURI : "/js/smarteditor2/SmartEditor2Skin.html", //경로를 꼭 맞춰주세요!
                    fCreator : "createSEditor2",
                    htParams : {
                        // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                        bUseToolbar : true,
                        // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                        bUseVerticalResizer : true,
                        // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                        bUseModeChanger : true,
                        fOnBeforeUnload : function(){

                        }
                    }
                });


            </script>
        </div>
    </div>
    <div class="bottom">
        <button type="submit">작성 완료</button>
        <button type="button" class="cancelButton" onclick="cancelFunction('/review')">취소</button>

    </div>

    </form>
</div>
<div class="footer">
    <img src="/img/footer/footer.png">
</div>
</body>

</html>