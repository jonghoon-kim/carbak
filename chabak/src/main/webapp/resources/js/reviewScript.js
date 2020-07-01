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



// 리뷰 리스트를 ajax로 출력
function ajaxReviewList(sessionId) {
    $.ajax({
        url : "/review/listAjax",
        type : "post",
        dataType:'json',
        data :{"sortType": $("#sortType option:selected").val(),//서버로 전송하는 데이터(정렬방식)
               "search_text":$(".search_text").val()   }, //검색창의 텍스트값
        success : function(data) {
            
            //받은 sessionId 값이 문자열이 아니므로(따옴표로 감싸이지 않았음) 따옴표 추가
            sessionId = "'"+sessionId+"'";
            
            var reviewListDiv = $("#reviewListDiv"); //리뷰가 추가되는 영역
            reviewListDiv.empty();  //리뷰 추가 영역 초기화
            
            $.each(data, function() {
                var newReview = $("#dummy-review").clone(true);

                //            원형 복사시 수정할 부분: #dummy-review(id),  .writer-id(value),    .review-img img(src,onclick) .content-title(value)

                var reviewNo = this["reviewNo"];
                var reviewId = "review"+reviewNo;

                newReview.attr("id",reviewId);


                var writer = newReview.find(".writer-id");  //작성자
                writer.html(this["id"]);  //작성자 설정

                var reviewImg =  newReview.find(".review-img img")  //리뷰 이미지
                reviewImg.attr("src",this["titleImageSrc"]);  //리뷰 타이틀이미지 src

                var onclickLink =  "location.href='/review/detail?reviewNo="+ reviewNo+"'"; //리뷰 타이틀 이미지 링크
                reviewImg.attr("onclick",onclickLink);

                var title = newReview.find(".content-title");   //리뷰 타이틀
                title.text('['+this["sido"]+']'+'['+this["gugun"]+']'+this["title"]);                      //리뷰 타이틀 설정 [sido][gugun][title]

                //좋아요 토글될 이미지 선택
                var toggleImage = newReview.find(".toggle-like-img");
                //좋아요 토글될 이미지 id 설정
                toggleImage.attr("id","like-img"+reviewNo);

                //onclick 속성 추가(함수 실행)
                toggleImage.attr("onclick","ajaxReviewLikeToggle('"+reviewNo+"',this,"+sessionId+")");

                console.log(sessionId);

                if(sessionId=="" || this["likeYn"]==0){

                    toggleImage.attr("src","/img/community/heart.png");
               }
                else{
                    toggleImage.attr("src","/img/community/heart2.png");
                }

                newReview.show();
                reviewListDiv.append(newReview);


            });

        },
        error:function(error){
            alert('error');
        }
    });  // ajax 끝

}

function cancelFunction(link) {
    var option = confirm("작성을 종료하고 나가시겠습니까?");
    if(option==true){
        location.href=link;

    }
}


// function beforeSearch(){
//     $("#searchSortType").val($("#sortType option:selected").val());  //정렬 select의 값을 search 폼 hidden input에 집어넣기,검색시 새로고침되므로 일단 넣는건 보류
// }
