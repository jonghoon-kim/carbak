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
    private String titleImageSrc;
    private int readCount;
    private int likeCount;
    private Date regDate;
    private Date modifyDate;

}
