<%--
  Created by IntelliJ IDEA.
  User: BIT
  Date: 2020-07-31
  Time: 오후 4:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script>
    function beforeSubmit(flag){
        document.getElementById("flag").value = flag;
        document.getElementById("myForm").submit();
        return true;

    }
</script>
<body>
<button type="button" onclick="beforeSubmit(1);">Member 생성</button>
<button type="button" onclick="beforeSubmit(2);">Review 생성</button>
<button type="button" onclick="beforeSubmit(3);">ReadCount 생성</button>
<button type="button" onclick="beforeSubmit(4);">ReviewLike 생성</button>
<br>
<form action = '/test' method="GET" id="myForm">
    <input type="text" name="numMember" placeholder="member 생성 수">
    <input type="text" name="numReview" placeholder="review 생성 수">
    <input type="text" name="ratioReviewLike" placeholder="좋아요 누르는 비율(double)">
    <input type="hidden" name="flag" id="flag">

</form>

</body>
</html>
