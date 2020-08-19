<!-- 팔로워, 팔로잉 유저 리스트 frame -->
<div class="userListAll" id="selectPosition" style="display: none">
    <%--    <div class="U_Img_Id_follow">--%>
    <div class="U_Img_Id">
        <div class="U_Img">
            <button type="button" class="imageBtn" id="image_btn" onclick="">
            <img class="userImagePfile" id="imageId" draggable="false" src=" ">
            </button>
        </div>
    </div>
    <div class="U_Id" id="userIdId" onclick=""><!-- get Id from DB-->
    </div>
    <div class="followSelector">
        <button class="selectFollowUser" id="buttonId" onclick=" "><!-- get Info from DB --></button>
    </div>
    <%--    </div>--%>
    <input type="hidden" id="sessionId" value="${sessionScope.id}">

</div>

<!-- 리뷰 리스트 frame -->
<div class="reviewList" id="reviewListId" style="display: none">
    <div class="reviewImg">
        <a class="reviewDetail" id="reviewDetail" href=" ">
            <img class="imgAppendPoint" id="imgAppendPoint" src="">
        </a>
    </div>
</div>
