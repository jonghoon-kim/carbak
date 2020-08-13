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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/js/message_write.js" charset='UTF-8'></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        function useId(){
            alert(document.getElementById("searchText").value);
            opener.document.getElementById("receiveId").value = document.getElementById("searchText").value;
            window.close();
        }
    </script>
</head>
<body>
        <input type="text" name="searchText" id="searchText">
        <input type="button" value="사용하기" onclick="useId()">


</body>
</html>
