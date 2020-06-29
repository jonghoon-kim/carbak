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

                    $('.content_info').children().remove();
                    $('.content_info').text(' ');
                    for (var i = 0; i < HashMapList.length; i++) {
                        console.log(HashMapList[i]);
                        var row =  i+1+'<br>보여주는 ID : '+HashMapList[i].ID+'<br>보여주는 savename : '+HashMapList[i].SAVENAME+'<br>';
                        $('.content_info').append("test : "+row);
                    }

                    alert("follower");

                }, error: function (data) {

                }
            })
    });
})

//myInformation에서 팔로잉을 click 했을 때 발생하는 event
$(document).ready(function() {
    $("#following").click(function () {
        $.ajax({// ajax가 controller로 보내는
            type: "get",
            datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
            url: "following",
            success : function(data) { // ajax가 controller로 부터 받는
                var test = data.HashMapList[0].ID;
                console.log("test : "+ test);

                var HashMapList = data.HashMapList; // data.HashMapList.ID == HashMapList.ID
                console.log(data);
                $('.content_info').children().remove();
                $('.content_info').text(' ');
                for (var i = 0; i < HashMapList.length; i++) {
                    console.log(HashMapList[i]); // todo: delete button click -> btnDeleteFollowing event run

                    var row =  (i+1)+'<br><a href="#">profile : '+HashMapList[i].SAVENAME+'<br>id : '+HashMapList[i].ID+'</a>'
                    + '<input type="button" value="삭제" id="btnDeleteFollowing"><br>';
                    $('.content_info').append("test : "+row);
                }

                alert("following");
            }, error: function (data) {

            }
        })
    });
})

$(document).ready(function () {
    $("btnDeleteFollowing").click(function(){
        var test = ;
        console.log(test);
        $.ajax({
            type: "post",
            datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
            url: "deleteFollowing",
            data: {"deleteId" : test},
            success : function (data) {


                alert("delete test success");

        }

        })
    })
})

//myInformation에서 팔로잉을 click 했을 때 발생하는 event
// $(document).ready(function() {
//     var deleteFollowUserId = test;
//     $("#deleteFollow").click(function () {
//         $.ajax({//받는
//             type: "post",
//             datatype: "json",   // ex) {"name":"age":"address"} 와 같은 형식
//             url: "following",
//             data: "deleteFollowUserId": deleteFollowUserId,
//             success : function(data) { // 보내는
//                 var test = data.HashMapList[0].ID;
//                 console.log("test : "+ test);
//
//                 var HashMapList = data.HashMapList;
//                 console.log(data);
//                 $('.content_info').children().remove();
//                 $('.content_info').text(' ');
//                 for (var i = 0; i < HashMapList.length; i++) {
//                     console.log(HashMapList[i]);
//                     var row =  i+1+'<br><a href="#">보여주는 ID : '+HashMapList[i].ID+'<br>보여주는 savename : '+HashMapList[i].SAVENAME+'</a>'
//                         + '<button onclick="test(HashMapList[i].ID)"></button><br>';
//                     $('.content_info').append("test : "+row);
//                 }
//
//                 alert("good");
//             }, error: function (data) {
//
//             }
//         })
//     });
// })