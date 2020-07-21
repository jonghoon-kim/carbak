package com.chabak.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class Message {
    private int messageNo;
    private String sendId;
    private String receiveId;
    private String title;
    private String content;
    private Date regDate;
    private Date readDate;
    private String readYn;
}
