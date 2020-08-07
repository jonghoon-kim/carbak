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