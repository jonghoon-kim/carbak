<%--
  Created by IntelliJ IDEA.
  User: bit
  Date: 2020-06-23
  Time: 오전 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    const request = require('request')
    const NAVER_CLIENT_ID     = 'ZASiEiikXllOHewfCz5D'
    const NAVER_CLIENT_SECRET = 'ICb4IEklgs'
    const option = {
        query  :'어반슬로우시티', //이미지 검색 텍스트
        start  :1, //검색 시작 위치
        display:5, //가져올 이미지 갯수
        sort   :'sim', //정렬 유형 (sim:유사도)
        filter :'all' //이미지 사이즈
    }

    request.get({
        uri:'https://openapi.naver.com/v1/search/image', //xml 요청 주소는 https://openapi.naver.com/v1/search/image.xml
        qs :option,
        headers:{
            'X-Naver-Client-Id':NAVER_CLIENT_ID,
            'X-Naver-Client-Secret':NAVER_CLIENT_SECRET
        }
    }, function(err, res, body) {
        let json = JSON.parse(body) //json으로 파싱
        console.log(json)
    })


</script>
</body>
</html>
