function pwCheckValue() {
    var pwCheck = /^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*#?&])[a-z\d$@$!%*#?&]{6,15}$/;

    var pw = document.getElementById("pw").value;
    var pw2 = document.getElementById("pwCheck").value;
    var email = document.getElementById("email1").value;

    console.log("pwCheck"+ pw);

    if(!pwCheck.test(pw)) {
        alert("비밀번호를 정확히 입력해 주세요. \n (영문 소문자, 숫자, 특수 문자를 하나 이상 포함, 길이는 6~15자)");
        return false;
    } else {
        if(pw != pw2) {
            alert("비밀번호 확인을 정확히 해 주세요.");
            return false;
        }
    }

    $.ajax({
        url : "/member/pwUpdateAction",
        type : "post",
        data : {
            "password" : pw,
            "email" : email
        },
        dataType : "json",
        success : function(data) {
            var result = data.result;
            console.log(result);
            alert("비밀번호가 변경되었습니다.\n 로그인 창으로 이동합니다.");

            window.open('_self').close();
            window.opener.location='/member/login';
        }
    });  // ajax 끝
}