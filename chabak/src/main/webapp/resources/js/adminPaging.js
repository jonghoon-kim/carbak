//admin 회원관리 페이징
function adminPage(page){
    console.log("adminPage : " + page);
    if(page==0){
        page=1;
    }
    var startPageNo = (page * 10) - 9,
        endPageNo = page * 10;

        $.ajax({
            type : "POST",
            dataType : 'json',
            url : "/adminMembersPaging",
            data : {
                "crntpageNo" : page,
                "startPageNo" : startPageNo,
                "endPageNo" : endPageNo,
            },
            success : function(data) {
                var lstSize = data.lstSize;
                var adminwBoolean = data.adminwBoolean;
                var adminMemberlist = data.adminMemberlist;
                var paging = data.adminPaging;
                console.log("paging : " + paging);
                console.log("adminMemberlist : "+adminMemberlist);
                adminMemberChangePage(paging, adminMemberlist, adminwBoolean, lstSize);
            },
            error : function(data) {
                console.log("data failed : " + data);
                console.log("page"+page);
            }
        });
 }
//admin 회원 관리 페이징 html 구축
function adminMemberChangePage(paging, adminMemberlist, adminwBoolean, lstSize) {
    $('.admin_tr').children('td').remove();
    var adminRow;
    var indexlstSize = lstSize-1;
    for (var i = 0; i < 10; i++) {
        var id, name, gender, sido, gugun, email, regDate, del
        if(adminwBoolean=="true" && i<=indexlstSize){
            var dt = new Date(adminMemberlist[i].regDate);

            id =adminMemberlist[i].id,
                name = adminMemberlist[i].name,
                gender = adminMemberlist[i].gender,
                sido = adminMemberlist[i].sido,
                gugun = adminMemberlist[i].gugun,
                email = adminMemberlist[i].email,
                regDate = dt.toISOString().substring(0, 10),
                del = "<button >삭제</button>";
        }
        else{
            id =" ",
                name = " ",
                gender = " ",
                sido = " ",
                gugun = " ",
                email = " ",
                regDate = " ",
                del = " ";
        }
        adminRow += "<tr class='admin_tr'><td width='14%'>"+id+"</td>"
            + "<td width='12%'>"+name+"</td>"
            + "<td width='10%'>"+gender+"</td>"
            + "<td width='8%'>"+sido+"</td>"
            + "<td width='8%'>"+gugun+"</td>"
            + "<td width='22%'>"+email+"</td>"
            + "<td width='12%'>"+regDate+"</td>"
            + "<td width='14%'>"+del+"</td></tr>"

    }
    $('.admin_table').append(adminRow);
    $('.admin_aticle').children('.admin_link').remove();

    var adminMemberPageButton;
    console.log("adminwBoolean"+adminwBoolean);
    paging.prevPageNo = paging.pageNo - 1;
    paging.nextPageNo=paging.pageNo+1;

    adminMemberPageButton = "<div class='admin_link'>"
        + "<button class='fas fa-angle-left' onClick='javascript:adminPage("
        + paging.prevPageNo
        + ")'></button>";

    for (var i = paging.startPageNo; i <= paging.endPageNo; i++) {
        if(adminwBoolean=="true") {
            if (i == paging.pageNo) {
                adminMemberPageButton += " <button class='fas fa-circle' onClick='javascript:adminPage(" + i
                    + ")'>" + i + "</a></button> ";
            } else {
                adminMemberPageButton += " <button class='far fa-circle' onClick='javascript:adminPage(" + i
                    + ")'>" + i + "</a></button> ";
            }
        }
        else{
            adminMemberPageButton += " <button class='fas fa-circle' onClick='javascript:adminPage(" + 1
                + ")'>1</button> ";
        }
    }

    adminMemberPageButton += "<button class='fas fa-angle-right' onClick='javascript:adminPage("
        + paging.nextPageNo
        + ")'></button>";

    $('.admin_aticle').append(adminMemberPageButton);
}
