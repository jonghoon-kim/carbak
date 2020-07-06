/* 인증 코드 확인 */
function email_certify() {
    var query = $("#code").val();
    var dice = $("#dice").val();

    console.log("왔다." +query);

    $.ajax({
        url : "/member/email_certify",
        type : "post",
        data : {
            "query" :query,
            "dice" : dice
        },
        datatype : "json",
        success : function(data) {
            console.log(data);
            var result = data.result;
            console.log(result);
            if(result == 1) {
                alert("인증 번호가 일치하였습니다.");
                window.close();
            } else if( result== 0 ){
                alert("인증 번호가 일치하지 않습니다.\\n 인증 번호를 다시 입력해 주세요.'); history.go(-1);");
            }
        }
    });
}