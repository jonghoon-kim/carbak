//쪽지 작성 팝업 띄움
//파라미터1 : receiveId(메시지 쓰려는 대상 id.없으면 null) 파라미터2 : 로그인 아이디(세션)
function openWinMessageWrite(receiveId,sessionId){
    if (sessionId == "" || sessionId == null) {
        askLogin();
    }else{
        if (receiveId == "" || receiveId == null){
            window.open("/message/write", "쪽지 작성", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
        }
        else
            window.open("/message/write?receiveId="+receiveId, "쪽지 작성", "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );
    }
}