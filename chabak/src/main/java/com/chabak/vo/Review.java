package com.chabak.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class Review {
    private int reviewNo;
    private String id;
    private String title;
    private String sido;
    private String gugun;
    private String content;
    private int readCount;

    public int getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getUploadKey() {
        return uploadKey;
    }

    public void setUploadKey(int uploadKey) {
        this.uploadKey = uploadKey;
    }

    public String getUploadYes() {
        return uploadYes;
    }

    public void setUploadYes(String uploadYes) {
        this.uploadYes = uploadYes;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    private int likeCount;
    private int uploadKey;
    private String uploadYes;
    private Date regDate;
    private Date modifyDate;

}
