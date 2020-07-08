<!-- 팔로워, 팔로잉 유저 리스트 frame -->
<div class="userListAll" id="selectPosition" style="display: none">
    <div class="U_Img_Id_follow">
        <div class="U_Img_Id">
            <div class="U_Img">
                <a class="userHomeVisit" href="#guestVisit">
                    <img class="userImagePfile" id="imageId" draggable="false" src=" ">
                </a>
            </div>
            <div class="U_Id" id="userIdId" onclick=""><!-- get Id from DB-->
            </div>
        </div>
        <div class ="followSelector">
            <button class="selectFollowUser" id="buttonId" onclick=" "><!-- get Info from DB --></button>
        </div>
    </div>
    <input type="hidden" id="sessionId" value="${sessionScope.id}">
</div>
