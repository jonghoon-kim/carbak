package com.chabak.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class Review {
    private int reviewNo;
    private String id;
    private String title;
    private String state;
    private String city;
    private String content;
    private int readCount;
    private int likeCount;
    private int uploadKey;
    private String uploadYes;
    private Date regDate;
    private Date modifyDate;

}
