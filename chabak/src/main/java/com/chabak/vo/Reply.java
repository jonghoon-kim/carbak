package com.chabak.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class Reply {
    private int replyNo;
    private String id;
    private String parentId;
    private int parentReplyNo;
    private String content;
    private Date regDate;
    private Date modifyDate;
    private int reviewNo;
    private int groupNo;
    private int groupOrder;
    private int lv;

}
