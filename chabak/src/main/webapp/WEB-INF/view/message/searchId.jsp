<%--
  Created by IntelliJ IDEA.
  User: BIT
  Date: 2020-08-07
  Time: 오전 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/searchId.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/message_write.js" charset='UTF-8'></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/common.js" charset='UTF-8'></script>

    <script>
        function useId(){
            var inputValue = document.getElementById("searchText").value;
            if(inputValue == null || inputValue == ""){
                alert("사용할 아이디를 선택하세요.");
            }
            else{
                opener.document.getElementById("receiveId").value = document.getElementById("searchText").value;
                window.close();
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <div class="top">
            <h1>아이디 찾기</h1>
        </div>
        <div class="search_wrap">
            <div class="search">
                <input type="text" name="searchText" id="searchText">
                <button type="button" onclick="useId()">사용하기</button>
            </div>
        </div>
    </div>


</body>
</html>
