//필드 글자 수 제한
//글자 수 제한할 obj(input 등),제한할 글자 수
function checkLengthValidate(obj, maxLen) {

    var strValue = obj.value;
    var strLen = strValue.length;
    var totalLen = 0;
    var len = 0;
    var oneChar = "";
    var str2 = "";

    for (var i = 0; i < strLen; i++) {

        totalLen++;
        // 입력한 문자 길이보다 넘치면 잘라내기 위해 저장
        if (totalLen <= maxLen) {
            len = i + 1;
        }
    }

    // 넘어가는 글자는 자른다.
    if (totalLen > maxLen) {
        alert(maxLen + "글자를 초과 입력 할 수 없습니다.");
        str2 = strValue.substr(0, len);
        obj.value = str2;
        checkLengthValidate(obj, maxLen);
    }

}

//Date 타입 데이터,구분자를 입력받아 문자열 형태로 출력
function getFormatDate(date,delimiter) {
    var year = date.getFullYear();              //yyyy
    var month = (1 + date.getMonth());          //M
    month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
    var day = date.getDate();                   //d
    day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
    return  year + delimiter + month + delimiter + day;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
}

//form에 input type="text" 반드시 1개일때 input이 비어있는지 체크(공통 사용 가능,form의 onsubmit에 사용)
//TODO:현재 reviewScript.js->common.js로 이동.수정에 따른 오류 확인 필요
function commonCheckInputNotEmpty(obj,errorMessage) {
    var inputInForm = $(obj).find('input[type=\"text\"]');
    if(inputInForm.length==0){
        console.log("commonCheckInputNotEmpty 사용시 해당하는 엘리먼트를 찾지 못했습니다.");
        return false;
    }
    else{ //해당하는 엘리먼트가 있으면
        var inputText = inputInForm.val();
        console.log("inputText:"+inputText);
        if(inputText == null || inputText == ""){
            if(arguments.length==2){
                alert(errorMessage);
            }
            else
                alert("내용을 입력하세요.");

            return false;
        }
    }
    return true;
}