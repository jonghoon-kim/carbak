package com.chabak.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class ReviewDetail {
    private int reviewNo;
    private String id;
    private String title;
    private String sido;
    private String gugun;
    private String content;
    private String titleImageSrc;
    private int readCount;
    private int likeCount;
    private int replyCount;
    private Date regDate;
    private Date modifyDate;
    private String name; //Member name
    private String saveName; //Member saveName
    private String savePath; //Member savePath

}
