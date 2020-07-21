var emailChk = false;

function emailCheck() {
    var query = {email : $("#id_email").val()};

    $.ajax({
        url : "/member/emailCheck",
        type : "post",
        data : query,
        success : function(data) {
            if(data == 1) {
                alert("이메일 인증 번호를 전송하였습니다. \n인증 번호를 작성하고 확인해주십시오.");
                window.open("sendEmail?email="+encodeURI($("#id_email").val()),'emailCheck', 'top=200, left=500, width=400, height=300, scrollbars = yes, resizable=yes')
                emailChk = true;
            } else if( data== 0 ){
                alert("가입되지않은 이메일입니다.");
                emailChk = true;
            }
        }
    });  // ajax 끝
}
/* 아이디 찾기 - 회원정보 체크*/
function checkValue() {
    var name = document.getElementById("id_name").value;
    var email = document.getElementById("id_email").value;

    console.log(emailChk);

    if(!name) {
        alert("이름을 입력해 주세요.");
        return false;
    }
    if(!email) {
        alert("이메일을 입력하고 인증해주세요.");
        return false;
    }
    if(emailChk == false){
        alert("이메일 인증을 해주세요.");
        return false;
    }
    $.ajax({
        url : "idFindFlag",
        type : "post",
        data : {
            "name" : name,
            "email" : email
        },
        dataType : "json",
        success : function(data) {
            var result = data.id;
            console.log("result : " + result);
            document.getElementById("parentId").value = result;
            document.getElementById("parentName").value = name;
            window.open("idFind","idFindForm", 'top=200, left=500, width=450, height=350, scrollbars = yes, resizable=yes');
        }
    });  // ajax 끝
}

function checkValue2() {

    var name = document.getElementById("pw_name").value;
    var id = document.getElementById("pw_id").value;
    var email = document.getElementById("pw_email").value;

    console.log("----console");
    console.log(emailChk);

    if(!id) {
        alert("아이디를 입력해 주세요.");
        return false;
    }

    if(!name) {
        alert("이름을 입력해 주세요.");
        return false;
    }
    if(!email) {
        alert("이메일을 입력하고 인증해주세요.");
        return false;
    }

    if(emailChk == false){
        alert("이메일 인증을 해주세요.");
        return false;
    }

    $.ajax({
        url : "pwFindFlag",
        type : "post",
        data : {
            "name" : name,
            "id" : id,
            "email" : email
        },
        dataType : "json",
        success : function(data) {
            var result = data.id;

            //console.log("result : " + result);

            document.getElementById("parentEmail").value = email;
            window.open("pwUpdate", "pwUpdate", 'top=200, left=500, width=450, height=350, scrollbars = yes, resizable=yes');


        }
    });  // ajax 끝

}
function pwCheckValue() {
    var pwCheck = /^(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*#?&])[a-z\d$@$!%*#?&]{6,15}$/;

    var pw = document.getElementById("password").value;
    var pw2 = document.getElementById("passwordCheck").value;
    var email = document.getElementById("email").value;

    console.log("pwCheck");
    console.log(pw);

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
