package com.chabak.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class Message {
    private int messageNo; //pk 자동증가
    private String sendBoxDeletedYn; //보낸편지함 삭제여부 y/n : 보낸편지함,받은편지함 둘다 y이면 메시지 삭제
    private String receiveBoxDeletedYn; //받은편지함 삭제여부 y/n : 보낸편지함,받은편지함 둘다 y이면 메시지 삭제
    private String sendId; //보낸 유저 id
    private String receiveId; //받은 유저 id
    private String title; //메시지 제목
    private String content; //메시지 내용
    private Date regDate; //메시지 등록 일자
    private Date readDate; //보낸편지함에서 메시지 내용 읽은 일자
    private String readYn; //보낸편지함에서 메시지 내용 읽었는지 여부 y/n
}
