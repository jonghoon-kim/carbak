package com.chabak.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class Follow {

    private String followingId;
    private String followerId;

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
