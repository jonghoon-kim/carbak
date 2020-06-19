function checkReviewValidate(){

    oEditors.getById["smartEditor"].exec("UPDATE_CONTENTS_FIELD", []);

    //this.contents = $('#smartEditor').val();

    if($("#title").val()==''){
        alert('제목을 입력하세요.');
        return false;
    }

    if($("#sido option:selected").val()=="시/도 선택" || $("#sido option:selected").val()==null){
        alert('시,도를 입력하세요.');
        return false;
    }
    if($("#gugun option:selected").val()=="구/군 선택" || $("#gugun option:selected").val()==null){
        alert('구,군을 입력하세요.');
        return false;
    }

    if($("#smartEditor").length==0)
        return false;

    return true;



}

//필드 글자 및 바이트제한
function checkLengthValidate(obj, maxByte) {

    var strValue = obj.value;
    var strLen = strValue.length;
    var totalByte = 0;
    var len = 0;
    var oneChar = "";
    var str2 = "";

    for (var i = 0; i < strLen; i++) {
        oneChar = strValue.charAt(i);
        if (escape(oneChar).length > 4) {
            totalByte += 2;
        } else {
            totalByte++;
        }

        // 입력한 문자 길이보다 넘치면 잘라내기 위해 저장
        if (totalByte <= maxByte) {
            len = i + 1;
        }
    }

    // 넘어가는 글자는 자른다.
    if (totalByte > maxByte) {
        alert(maxByte + "Byte를 초과 입력 할 수 없습니다.");
        str2 = strValue.substr(0, len);
        obj.value = str2;
        checkLengthValidate(obj, 20);
    }

}
function ts(a) {
    id = a;

}
function ajaxReviewList() {
    $.ajax({
        url : "/review/listAjax",
        type : "post",
        dataType:'json',
        data :{"id":id},
        success : function(data) {
            if(data == 1) {

                var ts = data;
                new(ts);
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
