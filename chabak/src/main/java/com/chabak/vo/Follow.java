package com.chabak.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class Follow {

    private String followingId;
    private String followerId;

    private String id;
    private String password;
    private String name;
    private String gender;
    private String sido;
    private String gugun;
    private String email;
    private String saveName;
    private String savePath;
    private Date regDate;

    public String getFollowingId() {
        return followingId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

}
