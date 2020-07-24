/* 회원정보 체크*/
function checkValue() {
    /* 비밀번호, 이름, 이메일 정규식*/
    var pwCheck = /^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*#?&])[a-z\d$@$!%*#?&]{6,15}$/;
    var nameCheck = /^[가-힣]{2,}$/;

    var pw = document.getElementById("password").value;
    var pw2 = document.getElementById("password2").value;
    var name = document.getElementById("name").value;

    // console.log(idChk);
    if(!name) {
        alert("이름을 입력해 주세요.");
        return false;
    }
    if(!nameCheck.test(name)){
        alert("이름이 잘못 입력되었습니다.");
        return false;
    }
    if(!pwCheck.test(pw)) {
        alert("비밀번호를 정확히 입력해 주세요. \n (영문 소문자, 숫자, 특수 문자를 하나 이상 포함, 길이는 6~15자)");
        return false;
    } else {
        if(pw != pw2) {
            alert("비밀번호 확인을 정확히 해 주세요.");
            return false;
        }
    }

    alert("정보 수정이 완료되었습니다.");
}