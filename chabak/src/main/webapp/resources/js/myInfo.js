// 스크립트 파일
// console.log(testOne);

//myInformation에서 팔로워를 click 했을 때 발생하는 event
$(document).ready(function() {
    $("#follower").click(function () {
        console.log();
            $.ajax({//controller로 보내는
                type: "get",
                datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
                url: "follower",
                success : function(data) { // 가져온데이타
                    var HashMapList = data.HashMapList;
                    console.log(data);

                    for (i = 0; i < HashMapList.length; i++) {
                        console.log(HashMapList[i]);
                    }

                    alert("ss");
                }, error: function (data) {

                }
            })
    });
})

//myInformation에서 팔로잉을 click 했을 때 발생하는 event
$(document).ready(function() {
    $("#following").click(function () {
        $.ajax({//받는
            type: "get",
            datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
            url: "following",
            success : function(data) { // 보내는
                var HashMapList = data.HashMapList;
                console.log(data);

                for (i = 0; i < HashMapList.length; i++) {
                    console.log(HashMapList[i]);
                }
                alert("good");
            }, error: function (data) {

            }
        })
    });
})
