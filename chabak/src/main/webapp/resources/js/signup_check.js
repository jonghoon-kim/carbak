/* 아이디 중복 체크, 체크 여부 판별 */
var idChk;
var emailChk;

function idCheck() {
    var query = {id : $("#id").val()};
    var idcheckreg =/^[a-zA-Z0-9]{5,15}$/;

    $.ajax({
        url : "/member/idCheck",
        type : "post",
        data : query,
        success : function(data) {
            if(data == 1) {
                alert("아이디가 중복됩니다.");

                idChk = false;

            } else if( data== 0 ){
                if(idcheckreg.test($('#id').val())) {
                    alert("사용 가능한 아이디입니다.");
                    idChk = true;

                }else {
                    alert("아이디는 영어와 숫자의 조합이어야합니다.\n 영어로 시작하는 아이디, 길이는 5~15자");
                    idChk = false;

                }
            }

            console.log(idChk);
        }
    });  // ajax 끝
}

/* 이메일 중복 체크 */
function emailCheck() {
    var query = {email : $("#email").val()};
    var emailCheck = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    $.ajax({
        url : "/member/emailCheck",
        type : "post",
        data : query,
        success : function(data) {
            if(data == 1) {
                alert("이미 가입된 이메일 입니다.");
                emailChk = false;
            } else if( data== 0 ){
                if(emailCheck.test($('#email').val())) {
                    alert("이메일 인증 코드를 발송했습니다.");
                    window.open("sendEmail",'emailCheck', 'width=400, height=300, scrollbars = yes, resizable=yes')
                    emailChk = true;
                }else {
                    alert("이메일 형식에 맞게 입력해 주세요.\n ex)aa01@aa.aa");
                    emailChk = false;
                }
            }
            console.log(emailChk);
        }
    });  // ajax 끝
}

/* 이메일 전송 */
function sendMail(email) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if(xhttp.readyState == 4) {
            if (xhttp.status == 200)
                alert("메일을 정상적으로 보냈습니다.");
            else
                alert("올바른 메일 형식이 아닙니다.");
        }
    };
    xhttp.open("POST", '/member/sendMail', true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xhttp.send('email=' + email);
    return false;
}

/* 회원정보 체크*/
function checkValue() {
    /* 비밀번호, 이름, 이메일 정규식*/
    var pwCheck = /^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*#?&])[a-z\d$@$!%*#?&]{6,15}$/;
    var nameCheck = /^[가-힣]{2,}$/;
    var emailCheck = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    var id = document.getElementById("id").value;
    var pw = document.getElementById("password").value;
    var pw2 = document.getElementById("password2").value;
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;

    // console.log(idChk);
    if(!name) {
        alert("이름을 입력해 주세요.");
        return false;
    }
    if(!nameCheck.test(name)){
        alert("이름이 잘못 입력되었습니다.");
        return false;
    }
    if(idChk == false) {
        alert("아이디 중복 체크를 해주세요.");
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

    if(emailChk == false){
        alert("이메일 인증을 해주세요.");
        return false;
    }

    alert("회원가입이 완료되었습니다.");
}

